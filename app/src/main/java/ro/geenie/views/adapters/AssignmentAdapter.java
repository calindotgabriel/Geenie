package ro.geenie.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import ro.geenie.R;
import ro.geenie.models.AssignmentItem;
import ro.geenie.views.activities.AssignmentsActivity;

/**
 * Created by loopiezlol on 04.03.2015.
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    OnItemClickListener mItemClickListener;
    private Context context;
    private List<AssignmentItem> assignmentItems;
    private int itemLayout;

    public AssignmentAdapter(Context context, List<AssignmentItem> assignments, int itemLayout) {
        this.context = context;
        this.assignmentItems = assignments;
        this.itemLayout = itemLayout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AssignmentItem assignmentItem = assignmentItems.get(position);
        holder.cardTitle.setText(assignmentItem.getTitle());

        int click = assignmentItem.getClick();
        final Calendar calendar = assignmentItem.getCalendar();
        if (click == 0) {
            holder.alarmSwitch.setChecked(false);
        } else {

            holder.alarmSwitch.setChecked(true);
        }

        holder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((AssignmentsActivity) context).startAlarm(position, holder.cardTitle.getText().toString(), calendar);
                } else {
                    ((AssignmentsActivity) context).cancelAlarm(position);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return assignmentItems.size();
    }


    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView cardTitle;
        public CardView card;
        public Switch alarmSwitch;


        public ViewHolder(final View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view);
            cardTitle = (TextView) itemView.findViewById(R.id.assignment_card_title);
            itemView.setOnClickListener(this);
            alarmSwitch = (Switch) itemView.findViewById(R.id.alarmswitch);


        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }


    }


}