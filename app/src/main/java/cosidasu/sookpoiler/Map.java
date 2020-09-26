package cosidasu.sookpoiler;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Map extends FragmentActivity implements OnMapReadyCallback {

    static final LatLng ms1 = new LatLng(37.545735, 126.963592);
    static final LatLng sh2 = new LatLng(37.546449, 126.964719);
    static final LatLng st3 = new LatLng(37.545492, 126.965057);
    static final LatLng hj4 =new LatLng(37.545416, 126.964579);
    static final LatLng jl5=new LatLng(37.546332, 126.963801);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap){

        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.setPadding(0, 0, 0, 100);

        //adding marker on google map
        googleMap.addMarker(new MarkerOptions()
                .position(ms1)
                .title("학생식당-B002호\n 정순옥 라운지-2층\n")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.addMarker(new MarkerOptions()
                .position(sh2)
                .title("브런치카페 - B006호\n보건의료센터의료공제회 - B009호\n보건의료센터 - B011호\n정양실(여) - B013호\n교직원식당 - B016호\n순헌락커라운지 - B023호")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        googleMap.addMarker(new MarkerOptions()
                .position(st3)
                .title("교보문고 - 101호, 102호, 103호\n여행사 - 106호\nSNOW문화공간 - 104호\n편의점 - 109호\n문구점 - 112호\n블루베리 - 114호")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        googleMap.addMarker(new MarkerOptions()
                .position(hj4)
                .title("주차장 - 전체")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        googleMap.addMarker(new MarkerOptions()
                .position(jl5)
                .title("중강당 - B101호\n모의법정 - B102호")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sh2, 18));

        // Setting a custom info window adapter for the google map
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {
                View v = null;
                try {

                    // Getting view from the layout file info_window_layout
                    v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);

                    // Getting reference to the TextView to set latitude
                    TextView addressTxt = (TextView) v.findViewById(R.id.addressTxt);
                    addressTxt.setText(arg0.getTitle());

                } catch (Exception ev) {
                    System.out.print(ev.getMessage());
                }

                return v;
            }
        });
    }

}