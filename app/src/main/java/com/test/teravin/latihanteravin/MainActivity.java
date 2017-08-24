package com.test.teravin.latihanteravin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.test.teravin.latihanteravin.adapter.AdapterPopular;
import com.test.teravin.latihanteravin.model.api.EndpointRequest;
import com.test.teravin.latihanteravin.model.api.RestClient;
import com.test.teravin.latihanteravin.model.api.pojo.Example;
import com.test.teravin.latihanteravin.model.api.pojo.PopularDB;
import com.test.teravin.latihanteravin.model.api.pojo.Result;
import com.test.teravin.latihanteravin.model.api.view.LoadMoreRecycler;
import com.test.teravin.latihanteravin.model.api.view.MainLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public EndpointRequest endpointRequest;
    public static String API_KEY = "f7b67d9afdb3c971d4419fa4cb667fbf";
    public static String EN = "en-US";
    private int PAGE = 1;
    private MainLayout mainLayout;
    private AdapterPopular adapter;
    private List<Result> dataSet = new ArrayList<>();
    private RealmResults<PopularDB> popularDBs;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        mainLayout = (MainLayout) findViewById(R.id.main_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainLayout.list.setLayoutManager(layoutManager);
        adapter = new AdapterPopular(mainLayout.list, dataSet, new LoadMoreRecycler.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        }, null, layoutManager);
        mainLayout.list.setAdapter(adapter);

        endpointRequest = RestClient.createService(EndpointRequest.class);
        endpointRequest.getDataPopular(API_KEY, EN, PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Example>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Example example) {
                        Log.i("data", "Example " + example.results.get(0).originalTitle);
                        saveDataLocal(example.results);

                    }
                });

    }

    private void saveDataLocal(List<Result> results) {
        for (int i = 0; i < results.size(); i++) {
            realm.beginTransaction();
            PopularDB popularDB = realm.createObject(PopularDB.class);
            popularDB.setId(results.get(i).id);
            popularDB.setTitle(results.get(i).title);
            popularDB.setOriginalTitle(results.get(i).originalTitle);
            popularDB.setAdult(results.get(i).adult);
            popularDB.setPopularity(results.get(i).popularity);
            popularDB.setGenreIds(results.get(i).genreIds);
            popularDB.setOverview(results.get(i).overview);
            popularDB.setReleaseDate(results.get(i).releaseDate);
            realm.copyToRealm(popularDB);
            realm.commitTransaction();
        }
    }


}
