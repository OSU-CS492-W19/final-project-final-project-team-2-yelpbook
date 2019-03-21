package com.example.android.yelpsearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;




import com.example.android.yelpsearch.R;
import com.example.android.yelpsearch.data.YelpRest;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class YelpSearchAdapter extends RecyclerView.Adapter<YelpSearchAdapter.SearchResultViewHolder> {
    private List<YelpRest> mRepos;
    OnSearchItemClickListener mSeachItemClickListener;

    public interface OnSearchItemClickListener {
        void onSearchItemClick(YelpRest repo);
    }

    YelpSearchAdapter(OnSearchItemClickListener searchItemClickListener) {
        mSeachItemClickListener = searchItemClickListener;
    }

    public void updateSearchResults(List<YelpRest> repos) {
        mRepos = repos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mRepos != null) {
            return mRepos.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        for( int  i = 0; i < mRepos.size() -1; i ++){
            for(int j = 0; j < mRepos.size() -1; j++){
                if(mRepos.get(j).price != null && mRepos.get(j+1).price !=null){
                if(mRepos.get(j).price.length() > mRepos.get(j+1).price.length()) {
                    Collections.swap(mRepos, j, j + 1);
                    }
                }else if(mRepos.get(j).price == null){
                    Collections.swap(mRepos,j,j+1);
                }
            }
        }
        holder.bind(mRepos.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mSearchResultRestTV;
        private TextView mSearchResultAddrtTV;
        private ImageView mRestImgIV;
        private TextView mPrice;


        public SearchResultViewHolder(View itemView) {
            super(itemView);
            mSearchResultRestTV = itemView.findViewById(R.id.tv_search_result_rest);
            mSearchResultAddrtTV = itemView.findViewById(R.id.tv_search_result_address);
            mRestImgIV = itemView.findViewById(R.id.iv_rest_img);
            mPrice = itemView.findViewById(R.id.tv_price);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YelpRest searchResult = mRepos.get(getAdapterPosition());
                    mSeachItemClickListener.onSearchItemClick(searchResult);
                }
            });
        }

        public void bind(YelpRest repo) {
            mSearchResultRestTV.setText(repo.name );
            mSearchResultAddrtTV.setText(repo.location_address + ", " + repo.location_city);
            mPrice.setText("Price: " + repo.price);

            Glide.with(mRestImgIV.getContext())
                    .load(repo.img_url)
                    .apply(new RequestOptions().override(720, 720))
                    .into(mRestImgIV);


        }
    }
}