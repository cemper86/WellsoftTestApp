package ru.stairenx.wellsofttestapp.server;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import ru.stairenx.wellsofttestapp.item.LoginItem;

public class ConnectAPI {


    public static void queryLogin(Context context, LoginItem item, final ResponseServer responseServer){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams rp = new RequestParams();
        rp.add(ConstantsAPI.JSON_GRANT_TYPE,ConstantsAPI.JSON_PASSWORD);
        rp.add(ConstantsAPI.JSON_USERNAME,item.getLogin());
        rp.add(ConstantsAPI.JSON_PASSWORD,item.getPassword());
        JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.isNull(ConstantsAPI.JSON_ERROR)) responseServer.onSuccess(response);
                else responseServer.onFailure(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseServer.onFailure(errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("---",responseString);
            }
        };
        client.post(context, ConstantsAPI.getLink(ConstantsAPI.AUTH),null,rp,ConstantsAPI.CONTENT_TYPE,responseHandler);
    }

    public static void getSegmentsRoute(Context context, String token,final ResponseServer responseServer){
        AsyncHttpClient client = new AsyncHttpClient();
        JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("Id")!=0) responseServer.onSuccess(response);
                else responseServer.onFailure(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseServer.onFailure(errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("---",responseString);
            }
        };
        client.addHeader("Authorization","bearer " +token);
        client.get(context, ConstantsAPI.getLink(ConstantsAPI.TRUCK_ROUTE),responseHandler);
    }

    public interface ResponseServer{
        void onSuccess(JSONObject jsonResponse);
        void onFailure(JSONObject jsonResponse);
    }
}
