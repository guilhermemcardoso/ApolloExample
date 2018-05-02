package cardoso.guilherme.graphql;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cardoso.guilherme.graphql.api.GetFeedQuery;

/**
 * Created by guilherme on 27/04/18.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    List<GetFeedQuery.Feed> links;
    Context mContext;

    public FeedAdapter(Context context) {
        super();
        this.mContext = context;
        links = new ArrayList<>();
        notifyDataSetChanged();
    }

    public FeedAdapter(List<GetFeedQuery.Feed> links) {
        super();
        this.links = links;
        notifyDataSetChanged();
    }

    public void updateList(List<GetFeedQuery.Feed> links) {
        this.links = links;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, final int position) {
        GetFeedQuery.Feed feed = links.get(position);
        holder.url.setText(feed.url());
        holder.description.setText(feed.description());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LinkActivity.class);
                intent.putExtra("link_id", links.get(position).id());
                intent.putExtra("link_url", links.get(position).url());
                intent.putExtra("link_description", links.get(position).description());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        TextView url;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.item_layout);
            url = itemView.findViewById(R.id.item_url);
            description = itemView.findViewById(R.id.item_description);
        }
    }
}
