package cardoso.guilherme.graphql;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cardoso.guilherme.graphql.api.FeedQuery;

/**
 * Created by guilherme on 27/04/18.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    List<FeedQuery.Feed> links;

    public FeedAdapter() {
        super();
        links = new ArrayList<>();
        notifyDataSetChanged();
    }

    public FeedAdapter(List<FeedQuery.Feed> links) {
        super();
        this.links = links;
        notifyDataSetChanged();
    }

    public void updateList(List<FeedQuery.Feed> links) {
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
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        FeedQuery.Feed feed = links.get(position);
        holder.url.setText(feed.url());
        holder.description.setText(feed.description());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView url;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            url = itemView.findViewById(R.id.item_url);
            description = itemView.findViewById(R.id.item_description);
        }
    }
}
