package com.test.teravin.latihanteravin.model.api.view;

/**
 * Created by Dedi on 12/4/2016.
 * Happy Coding
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.test.teravin.latihanteravin.R;

import java.util.List;

/**
 * Created by mSobhy on 7/22/15 from Gist https://gist.github.com/mSobhy90/cf7fa98803a0d7716a4a#file-abstractrecyclerviewfooteradapter-java
 */
public abstract class LoadMoreRecycler<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int VISIBLE_THRESHOLD = 1;

    private int ITEM_VIEW_TYPE_HEADER = 0;
    private int ITEM_VIEW_TYPE_BASIC = 1;
    private int ITEM_VIEW_TYPE_FOOTER = 2;
    private int ITEM_VIEW_TYPE_SUGGESTION = 3;
    public int ITEM_VIEW_TYPE_FOOTER_REKOMENDASI = 4;//untuk content komunitas yang melebar/saat ganjil/tidak grid 2
    public int ITEM_VIEW_TYPE_ACTIVITIES_SUGGESTION = 5;//untuk suggestion/saran berdasarkan aktifitas

    private List<T> dataSet;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean loading = true;
    public ProgressViewHolder progressViewHolder;
    private boolean enableLoadMore = true;

    public LoadMoreRecycler(final RecyclerView recyclerView, List<T> dataSet,
                            final OnLoadMoreListener onLoadMoreListener,
                            final GridLayoutManager grid, final LinearLayoutManager linear) {

        this.dataSet = dataSet;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            if (grid != null) {
                grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {

                        /** pada dasarnya getSpanSize returnnya default 1 (selain 1 or spanCount baklan crash)
                         *  dan selalu diingat return ini juga berdasarkan {@link GridLayoutManager#setSpanCount(int)}
                         *
                         *  jika return 1 maka grid yang ditampilkan menjadi per kolom berdasarkan setspanCount
                         *  jika return 2 maka width nya akan fill_parent
                         *  **/

                        if (getItemViewType(position) == ITEM_VIEW_TYPE_HEADER) {
                            return 2;
                        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_SUGGESTION) {
                            return 2;
                        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER) {
                            return 2;
                        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER_REKOMENDASI) {
                            return 2;
                        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_ACTIVITIES_SUGGESTION) {
                            return 2;
                        } else {
                            return 1;
                        }

                    }
                });
            }

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recycler, int dx, int dy) {

                    /*Utils.logLong("position visibleItemCount", "=> " + visibleItemCount);
                    Utils.logLong("position firstVisibleItem", "=> " + firstVisibleItem);
                    Utils.logLong("position totalItemCount", "=> " + totalItemCount);*/

                    if (!enableLoadMore) {
                        return;
                    }

                    if (linear != null) {/* jika recycler view pakai linear layout manager */

                        totalItemCount = linear.getItemCount();
                        visibleItemCount = linear.getChildCount();
                        firstVisibleItem = linear.findFirstVisibleItemPosition();

                        if (loading && (totalItemCount - visibleItemCount)
                                <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                            addItem(null);
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = false;
                        }

                    } else if (grid != null) {/* jika recycler view pakai grid layout manager */

                        visibleItemCount = grid.getChildCount();
                        totalItemCount = grid.getItemCount();
                        firstVisibleItem = grid.findFirstVisibleItemPosition();
                        boolean isLoad = (visibleItemCount + firstVisibleItem) == totalItemCount;

                        if (loading && isLoad) {
                            addItem(null);
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = false;
                        }

                    }
                }
            });

        }
    }

    public int getPostionScroll() {
        return visibleItemCount;
    }

    public int getFirstVisibleItem() {
        return firstVisibleItem;
    }

    public void resetItems(@NonNull List<T> newDataSet) {
        loading = true;
        firstVisibleItem = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
        previousTotal = 0;

        dataSet.clear();
        addItems(newDataSet);
    }

    public void showLoadmore() {
        addItem(null);
        loading = false;
        enableLoadMore = true;
    }

    public void stopLoading() {// menghilangkan icon loading
        if (!loading) {
            removeItem(null);
            loading = true;
        }
    }

    public void setEnableLoadMore(boolean enable) {
        if (enable) {
            loading = true;
            enableLoadMore = true;
        } else {
            loading = false;
            enableLoadMore = false;
        }
    }

    public void addItems(@NonNull List<T> newDataSetItems) {
        dataSet.addAll(newDataSetItems);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (!dataSet.contains(item)) {
            dataSet.add(item);
            notifyItemInserted(dataSet.size() - 1);
        } else {
            dataSet.add(item);
            notifyDataSetChanged();
        }
    }


    public void removeItem(T item) {
        int indexOfItem = dataSet.indexOf(item);
        if (indexOfItem != -1) {
            this.dataSet.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    public T getItem(int index) {
        if (dataSet != null && dataSet.get(index) != null) {
            return dataSet.get(index);
        } else {
            throw new IllegalArgumentException("Item with index " + index + " doesn't exist, dataSet is " + dataSet);
        }
    }

    public List<T> getDataSet() {
        return dataSet;
    }

    public void clearData() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (dataSet.get(position) != null) {
            return ITEM_VIEW_TYPE_BASIC;
        } else if (dataSet.get(position) == null) {
            return ITEM_VIEW_TYPE_FOOTER;
        } else {
            return 99;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            return onCreateBasicItemViewHolder(parent, viewType);
        } else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        } else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {

        if (getItemViewType(position) == ITEM_VIEW_TYPE_BASIC) {
            onBindBasicItemView(genericHolder, position);
        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER) {
            onBindFooterView(genericHolder, position);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position);

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        //noinspection ConstantConditions
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_more_recycler, parent, false);
        progressViewHolder = new ProgressViewHolder(v);
        return progressViewHolder;
    }

    public void onBindFooterView(RecyclerView.ViewHolder genericHolder, int position) {
        ((ProgressViewHolder) genericHolder).progressBar.setIndeterminate(true);
        if (!loading) {
            ((ProgressViewHolder) genericHolder).progressBar.setVisibility(View.VISIBLE);
            ((ProgressViewHolder) genericHolder).text_error.setVisibility(View.GONE);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;
        public TextView text_error;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.load_more_progressBar);
            text_error = (TextView) v.findViewById(R.id.text_error);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


}