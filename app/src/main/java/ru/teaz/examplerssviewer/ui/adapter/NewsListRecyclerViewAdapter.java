package ru.teaz.examplerssviewer.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.teaz.examplerssviewer.R;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.data.utils.PreferencesUtils;

public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<NewsListRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
//    private List<News> mNewsList = new ArrayList<>();
    private SortedList<News> mNewsList;
    private OnNewsClickListener mListener;
    PreferencesUtils mPreferencesUtils;

    public interface OnNewsClickListener {
        void onNewsClick(News news);
    }

    public NewsListRecyclerViewAdapter(@NonNull Context context) {
        mPreferencesUtils = new PreferencesUtils(context);
        mContext = context;
        mNewsList = new SortedList<>(News.class, new SortedList.Callback<News>() {
            @Override
            public int compare(News o1, News o2) {
                return o2.getPubDate().compareTo(o1.getPubDate());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(News oldItem, News newItem) {
                return oldItem.getLink().equals(newItem.getLink());
            }

            @Override
            public boolean areItemsTheSame(News item1, News item2) {
                return item1.getId() == item2.getId();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.cv_root)
        CardView cvRoot;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.iv_image)
        ImageView ivImage;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }

    }

    public void addData(@NonNull List<News> newsList) {
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    public void markAllRead() {
        for (int i = 0; i < mNewsList.size(); i++) {
            mNewsList.get(i).setRead(true);
        }
        notifyDataSetChanged();
    }

    public void setOnNewsClickListener(@NonNull OnNewsClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final News news = mNewsList.get(position);
        holder.tvTitle.setText(news.getTitle());
        if (!news.isRead()) {
            holder.tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            holder.tvTitle.setTypeface(Typeface.DEFAULT);
        }
        final String imageUrl = news.getUrlImage();
        if (imageUrl != null) {
            Glide.with(mContext)
                    .load(imageUrl)
                    .fitCenter()
                    .into(holder.ivImage);
        } else {
            Drawable defaultDrawable = null;
            switch (news.getSource()) {
                case LENTA:
                    defaultDrawable = ContextCompat.getDrawable(mContext, R.drawable.logo_lenta);
                    break;
                case GAZETA:
                    defaultDrawable = ContextCompat.getDrawable(mContext, R.drawable.logo_gazeta);
                    break;
            }
            if (defaultDrawable != null) {
                holder.ivImage.setImageDrawable(defaultDrawable);
            }
        }

        holder.cvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    holder.tvTitle.setTypeface(Typeface.DEFAULT);
                    mListener.onNewsClick(news);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
