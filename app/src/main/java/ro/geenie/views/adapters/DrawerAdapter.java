package ro.geenie.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ro.geenie.models.DrawerItem;
import ro.geenie.R;

/**
 * Created by loopiezlol on 08.02.2015.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
    List<DrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;

    public DrawerAdapter(Context context, List<DrawerItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_row, parent, false);

        DrawerViewHolder holder = new DrawerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        DrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.icon.setImageResource(current.getIconId());


    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.drawerListText);
            icon = (ImageView) itemView.findViewById(R.id.drawerListIcon);


        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

        }
    }
}
