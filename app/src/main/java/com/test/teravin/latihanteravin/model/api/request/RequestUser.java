package com.test.teravin.latihanteravin.model.api.request;

import com.test.teravin.latihanteravin.model.api.EndpointRequest;
import com.test.teravin.latihanteravin.model.api.RestClient;


/**
 * Created by Dedi on 24/08/2017.
 * Happy coding, buddy!
 */

public class RequestUser {

    private EndpointRequest endpointRequest;

    public RequestUser(boolean isResultString) {
        endpointRequest = RestClient.createService(EndpointRequest.class);
    }


    public void loginUser(final String string_input, final String password) {
        /*endpointRequest.loginUser(BuildConfig.Accept, string_input, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        RxBus.publish(new PojoResultLogin("error"));
                    }

                    @Override
                    public void onNext(String s) {
                        RxBus.publish(new PojoResultLogin(s));
                    }
                });*/
    }

}
