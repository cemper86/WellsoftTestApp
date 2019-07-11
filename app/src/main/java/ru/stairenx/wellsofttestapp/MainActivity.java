package ru.stairenx.wellsofttestapp;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.stairenx.wellsofttestapp.item.CoordinatItem;
import ru.stairenx.wellsofttestapp.server.ConnectAPI;
import ru.stairenx.wellsofttestapp.server.ConstantsAPI;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    private String token;
    private List<CoordinatItem> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        token = getIntent().getExtras().getString("token");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        ConnectAPI.getSegmentsRoute(getApplicationContext(), token, new ConnectAPI.ResponseServer() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                data = getCoordinates(jsonResponse);
                PolylineOptions line = new PolylineOptions();
                for(int i=0;i<data.size();i++){
                    CoordinatItem item = data.get(i);
                    line.add(new LatLng(item.getLat(),item.getLng()));
                }
                googleMap.addPolyline(line);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(data.get(0).getLat(),data.get(0).getLng()),10));
            }

            @Override
            public void onFailure(JSONObject jsonResponse) {
                Toast.makeText(MainActivity.this, "Ошибка вставки заголовка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<CoordinatItem> getCoordinates(JSONObject obj){
        List<CoordinatItem> data = new ArrayList<>();
        try {
            JSONArray array = obj.getJSONArray(ConstantsAPI.JSON_ARRAY_ROUTE_SEGMENTS);
            JSONObject o = array.getJSONObject(0);
            JSONArray a = o.getJSONArray(ConstantsAPI.JSON_ARRAY_FROM_BASE_COORDINATES);
            for(int i=0;i<a.length();i++){
                JSONObject object = a.getJSONObject(i);
                if (!object.isNull(ConstantsAPI.JSON_LONGITUDE)&&!object.isNull(ConstantsAPI.JSON_LATITUDE)){
                    CoordinatItem item = new CoordinatItem(object.getDouble(ConstantsAPI.JSON_LONGITUDE),object.getDouble(ConstantsAPI.JSON_LATITUDE));
                    data.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
