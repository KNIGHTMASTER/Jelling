package com.zisal.jelling;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tripoin.tripoin_rest.bgp.impl.BGPLocation;
import com.tripoin.tripoin_rest.dto.request.location.DTORequestLocation;
import com.tripoin.tripoin_rest.dto.response.location.DTOResponseLocation;
import com.tripoin.tripoin_rest.post.IGenericPost;
import com.tripoin.tripoin_rest.util.TRIPOINHUDProgressDialog;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

public class MainMap extends ABaseMap implements IGenericPost<List<DTOResponseLocation>>{

    private List<LatLng> points;
    private MarkerOptions markerOptions;

    protected void setUpMap() {
        prepareData();
    }

    void prepareData(){
        final TRIPOINHUDProgressDialog tripoinhudProgressDialog = TRIPOINHUDProgressDialog.show(
                MainMap.this,
                "",
                false,
                null);
        new BGPLocation(this) {
            @Override
            public DTORequestLocation getDtoRequestLocation() {
                DTORequestLocation dtoRequestLocation = new DTORequestLocation();
                dtoRequestLocation.setUserId(BigInteger.ONE);
                return dtoRequestLocation;
            }

            @Override
            public Context getContext() {
                return MainMap.this;
            }

            @Override
            public Dialog getProgressDialog() {
                return tripoinhudProgressDialog;
            }
        }.execute();
    }

    void prepareLocations(List<LatLng> locations){
        points = new ArrayList<>();
        for(LatLng latLng : locations){
            points.add(latLng);
        }
        /*LatLng location1 = new LatLng(-6.2517924, 106.761604);
        LatLng location2 = new LatLng(-6.2923574, 106.818375);
        LatLng location3 = new LatLng(-6.3189363, 106.88693);
        LatLng location4 = new LatLng(-6.3189363, 106.88693);
        LatLng location5 = new LatLng(-6.45665216445923, 106.886962890625);

        points.add(location1);
        points.add(location2);
        points.add(location3);
        points.add(location4);
        points.add(location5);*/
    }

    void prepareMarkers(List<String> locationNames){
        int counter = 0;
        for(String ln : locationNames){
            markerOptions = new MarkerOptions();
            markerOptions.position(points.get(counter));
            markerOptions.title(ln);
            markerOptions.snippet("Latitude:" + points.get(counter).latitude + "," + "Longitude:" + points.get(counter).longitude);
            mMap.addMarker(markerOptions);
            counter++;
        }
    }

    @Override
    public void onPostSuccess(List<DTOResponseLocation> dtoResponseLocations) {
        List<LatLng> locations = new ArrayList<>();
        List<String> locationNames = new ArrayList<>();
        if(dtoResponseLocations != null){
            for(DTOResponseLocation dtoResponseLocation : dtoResponseLocations){
                Log.d("RESPONSE DATA", dtoResponseLocation.toString());
                locations.add(new LatLng(
                        Double.parseDouble(dtoResponseLocation.getLatitude()),
                        Double.parseDouble(dtoResponseLocation.getLongitude()))
                );
                locationNames.add(dtoResponseLocation.getName());
            }
        }
        prepareLocations(locations);
        prepareMarkers(locationNames);
        prepareMap();
    }

    void prepareMap(){
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(3);
        polylineOptions.addAll(points);
        mMap.addPolyline(polylineOptions);

        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(points.get(points.size() - 1))
                        .bearing(0)
                        .zoom(mMap.getCameraPosition().zoom)
                        .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onPostFailure(RetrofitError retrofitError) {
        Log.e("ERROR", retrofitError.getLocalizedMessage());
    }
}
