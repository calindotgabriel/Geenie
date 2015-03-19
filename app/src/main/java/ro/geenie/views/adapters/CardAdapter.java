package ro.geenie.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.geenie.R;
import ro.geenie.models.Item;
import ro.geenie.models.ItemInterface;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    OnItemClickListener mItemClickListener;
    private Context context;
    private List<Item> items;

    public CardAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemLayout = R.layout.dash_card;
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemInterface item = items.get(position);

        holder.cardName.setText(item.getTitle());
        holder.cardText.setText(item.getSubtitle());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView card;
        public TextView cardName;
        public TextView cardText;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view);
            cardName = (TextView) itemView.findViewById(R.id.dash_card_name);
            cardText = (TextView) itemView.findViewById(R.id.dash_card_text);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }


    }


}