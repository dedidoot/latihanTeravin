package com.test.teravin.latihanteravin.model.api.pojo;

/**
 * Created by Dedi on 24/08/2017.
 * Happy coding, buddy!
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;

}