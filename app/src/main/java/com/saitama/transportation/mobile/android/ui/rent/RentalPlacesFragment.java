package com.saitama.transportation.mobile.android.ui.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.saitama.transportation.mobile.android.R;
import com.saitama.transportation.mobile.android.event.PlacesLoadEvent;
import com.saitama.transportation.mobile.android.server.ServerErrorResolver;
import com.saitama.transportation.mobile.android.server.entity.Location;
import com.saitama.transportation.mobile.android.server.entity.RentalPlace;
import com.saitama.transportation.mobile.android.ui.base.BaseFragment;
import com.saitama.transportation.mobile.android.ui.payment.PaymentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sharezzorama on 10/26/16.
 * The fragment for displaying all available rental places
 */

public class RentalPlacesFragment extends BaseFragment {
    private final static String KEY_ARG_PLACES = "RentalPlacesFragment.KEY_ARG_PLACES";
    public static final float MAP_ZOOM_DEFAULT = 11;
    public static final String TAG_DIALOG_PLACE_INFO = "RentalPlacesFragment.TAG_DIALOG_PLACE_INFO";
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private List<RentalPlace> mPlaces;
    private PlaceInfoDialog mPlaceInfoDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPlaces = (List<RentalPlace>) savedInstanceState.getSerializable(KEY_ARG_PLACES);
        }

        Fragment fragmentById = getFragmentManager().findFragmentByTag(TAG_DIALOG_PLACE_INFO);
        if (fragmentById != null) {
            mPlaceInfoDialog = (PlaceInfoDialog) fragmentById;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rental_places, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        RentalPlace byPosition = findByPosition(marker.getPosition());
                        if (byPosition != null) {
                            if (mPlaceInfoDialog == null) {
                                mPlaceInfoDialog = PlaceInfoDialog.newInstance(byPosition);
                            }
                            mPlaceInfoDialog.setTargetFragment(RentalPlacesFragment.this, 0);
                            mPlaceInfoDialog.show(getFragmentManager(), TAG_DIALOG_PLACE_INFO);
                        }
                        return false;
                    }
                });
                if (mPlaces == null) {
                    getCoreService().getPlaceManager().loadPlaces();
                } else {
                    markPlaces(googleMap);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlaces == null) {
            getCoreService().getPlaceManager().loadPlaces();
        } else {
            markPlaces(mGoogleMap);
        }
    }

    /**
     * Sets markers to the map according the place coordinates
     * @param googleMap
     */
    private void markPlaces(GoogleMap googleMap) {
        if (googleMap == null || mPlaces == null || mPlaces.isEmpty()) {
            return;
        }

        for (int i = 0; i < mPlaces.size(); i++) {
            RentalPlace place = mPlaces.get(i);
            LatLng coordinates = new LatLng(place.getLocation().getLat(), place.getLocation().getLng());
            googleMap.addMarker(new MarkerOptions().position(coordinates).title(place.getName()));
            if (i == 0) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, MAP_ZOOM_DEFAULT));
            }
        }
        mMapView.onResume();
    }

    /**
     * Stars payment activity
     * @param place
     */
    public void rent(RentalPlace place) {
        Intent intent = new Intent(getContext(), PaymentActivity.class);
        startActivity(intent);
    }

    /**
     * Finds the rental place by coordinates
     * @param latLng
     * @return
     */
    private RentalPlace findByPosition(LatLng latLng) {
        for (RentalPlace rentalPlace : mPlaces) {
            Location location = rentalPlace.getLocation();
            if (location.getLat() == latLng.latitude && location.getLng() == latLng.longitude) {
                return rentalPlace;
            }
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_ARG_PLACES, (Serializable) mPlaces);
        super.onSaveInstanceState(outState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlacesLoadEvent event) {
        if (event.isOk()) {
            mPlaces = event.getData();
            markPlaces(mGoogleMap);
        } else {
            String errorMessage = ServerErrorResolver.resolveError(event.getException());
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
