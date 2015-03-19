package ro.geenie.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.gc.materialdesign.widgets.SnackBar;

import java.util.Calendar;
import java.util.List;

import ro.geenie.R;
import ro.geenie.models.Assignment;
import ro.geenie.views.activities.AssignmentsActivity;

/**
 * Created by loopiezlol on 04.03.2015.
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    OnItemClickListener mItemClickListener;
    private Context context;
    private List<Assignment> assignments;
    private int itemLayout;

    public AssignmentAdapter(Context context, List<Assignment> assignments, int itemLayout) {
        this.context = context;
        this.assignments = assignments;
        this.itemLayout = itemLayout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Assignment assignment = assignments.get(position);
        holder.cardTitle.setText(assignment.getTitle());

        int click = assignment.getClick();
        final Calendar calendar = assignment.getCalendar();
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

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBar snackBar = new SnackBar((AssignmentsActivity) context, "Surely remove " + holder.cardTitle.getText() + "?", "Indeed", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assignments.remove(position);
                        ((AssignmentsActivity) context).changeVisibility();
                        notifyDataSetChanged();
                    }
                });
                snackBar.show();
            }
        });


        if (assignment.getTag().equals("Matematica")) {
            holder.tagColor.setBackgroundColor(Color.RED);
        }


    }


    @Override
    public int getItemCount() {
        return assignments.size();
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
        public ImageButton removeButton;
        public FrameLayout tagColor;


        public ViewHolder(final View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view);
            cardTitle = (TextView) itemView.findViewById(R.id.assignment_card_title);
            itemView.setOnClickListener(this);
            alarmSwitch = (Switch) itemView.findViewById(R.id.alarmswitch);
            removeButton = (ImageButton) itemView.findViewById(R.id.remove_button);
            tagColor = (FrameLayout) itemView.findViewById(R.id.tag_color);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }


    }


}