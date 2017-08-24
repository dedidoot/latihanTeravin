package com.test.teravin.latihanteravin.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.test.teravin.latihanteravin.model.api.pojo.Result;
import com.test.teravin.latihanteravin.model.api.view.LoadMoreRecycler;

import java.util.List;

/**
 * Created by Dedi on 24/08/2017.
 * Happy coding, buddy!
 */


public class AdapterPopular extends LoadMoreRecycler<Result> {

    public AdapterPopular(RecyclerView recyclerView, List<Result> dataSet, OnLoadMoreListener onLoadMoreListener, GridLayoutManager grid, LinearLayoutManager linear) {
        super(recyclerView, dataSet, onLoadMoreListener, grid, linear);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        super.onBindViewHolder(genericHolder, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {

    }
}