package ir.makapps.hami;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import java.util.List;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.utils.Utils;
import ir.map.sdk_map.MapirStyle;
import ir.map.sdk_map.maps.MapView;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;

public class MapActivity extends BaseActivity {
    Bundle instance;
    MapboxMap map;
    Style mapStyle;
    MapView mapView;
    FloatingActionButton currentLocation;
    LatLng samplePoint = new LatLng(35.732521, 51.422575);
    int sampleZoom = 50;
    LatLng markerPosition, comeFromEditingAddressPosition;
    Double lat, lon, latEditedAddress, lonEditedAddress;
    LatLng selectedLatLon;
    Button btnConfirmMap;
    ConstraintLayout mainParent;
    public CallBack callBack;

    public interface CallBack {
        void sendLocation(String lat,String lng);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Context context = getApplicationContext();
        instance = savedInstanceState;

    }



    @Override
    public void bindViews() {
        mainParent = findViewById(R.id.mainParent);
        mapView = findViewById(R.id.map_view);
        currentLocation = findViewById(R.id.current_location);
        btnConfirmMap = findViewById(R.id.btnConfirmMap);
        mapView.onCreate(instance);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                map = mapboxMap;
                manageCameraPosition();
                map.setStyle(new Style.Builder().fromUri(MapirStyle.MAIN_MOBILE_VECTOR_STYLE), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;
                        reverseGeocode(mapboxMap.getCameraPosition());
                        enableLocationComponent();
                        mapboxMap.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {

                                reverseGeocode(mapboxMap.getCameraPosition());

                            }
                        });

                    }
                });


                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        return false;
                    }
                });
            }
        });

        btnConfirmMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lat = markerPosition.getLatitude();
                lon = markerPosition.getLongitude();
                selectedLatLon = new LatLng(lat, lon);
                Utils.showSnackbar(mainParent, "موقعیت آدرس شما ثبت شد", "green");
                sendLocation(selectedLatLon);
                finish();
            }

            public void sendLocation(LatLng latLang) {
                String lat = String.valueOf(selectedLatLon.getLatitude());
                String lan = String.valueOf(selectedLatLon.getLongitude());
                Utils.insertToHawk("lat",lat);
                Utils.insertToHawk("lan",lan);

            }

            private void sendGeoToInsertFragment(LatLng latLang) {
                Bundle bundle = new Bundle();
                bundle.putString("lat", String.valueOf(latLang.getLatitude()));
                bundle.putString("long", String.valueOf(latLang.getLongitude()));
                Fragment fragment;
//                fragment = MainActivity.getFragmentManager().findFragmentById(R.id.)
            }


        });


        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableLocationComponent();

            }
        });
    }


    @Override
    public void injectDagger() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }



    private void manageCameraPosition() {

        if (comeFromEditingAddressPosition != null) {

            setCameraInMap(map, comeFromEditingAddressPosition);

        } else {
            setCameraInMap(map, samplePoint);
        }
    }

    private void reverseGeocode(CameraPosition cameraPosition) {

        markerPosition = cameraPosition.target;

    }

    private void setCameraInMap(MapboxMap map, LatLng latLng) {

        map.setCameraPosition(
                new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(15)
                        .build());


    }


    private void moveCameraWithoutOption() {
        LatLng samplePoint = new LatLng(35.732521, 51.422575);
        map.easeCamera(CameraUpdateFactory.newLatLng(samplePoint));
    }

    private void addSymbolToMap() {
        mapStyle.addImage("sample_image_id", getResources().getDrawable(R.drawable.mapbox_marker_icon_default));
// create symbol manager object
        SymbolManager sampleSymbolManager = new SymbolManager(mapView, map, mapStyle);
        sampleSymbolManager.addClickListener(new OnSymbolClickListener() {
            @Override
            public void onAnnotationClick(Symbol symbol) {
                Toast.makeText(MapActivity.this, "This is CLICK_EVENT", Toast.LENGTH_SHORT).show();
            }
        });
        sampleSymbolManager.addLongClickListener(new OnSymbolLongClickListener() {
            @Override
            public void onAnnotationLongClick(Symbol symbol) {
                Toast.makeText(MapActivity.this, "This is LONG_CLICK_EVENT", Toast.LENGTH_SHORT).show();
            }
        });
// set non-data-driven properties, such as:
        sampleSymbolManager.setIconAllowOverlap(true);
        sampleSymbolManager.setIconRotationAlignment(ICON_ROTATION_ALIGNMENT_VIEWPORT);
// Add symbol at specified lat/lon
        SymbolOptions sampleSymbolOptions = new SymbolOptions();
        sampleSymbolOptions.withLatLng(samplePoint);
        sampleSymbolOptions.withIconImage(String.valueOf(R.drawable.ic_add_note));
        sampleSymbolOptions.withIconSize(1.0f);
// save created Symbol Object for later access
        Symbol sampleSymbol = sampleSymbolManager.create(sampleSymbolOptions);
    }


    private void zoomToSpecificLocation() {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(samplePoint, sampleZoom));
    }

    private void enableLocationComponent() {
        mapStyle.addImage("sample_image_id", getResources().getDrawable(R.drawable.mapbox_marker_icon_default));
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(MapActivity.this)) {
// Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(MapActivity.this)
                    .elevation(5)
                    .accuracyAlpha(.10f)
                    .accuracyColor(Color.RED)
                    .build();
// Get an instance of the component
            LocationComponent locationComponent = map.getLocationComponent();
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(MapActivity.this, mapStyle)
                            .useDefaultLocationEngine(true)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();


// Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
// Enable to make component visible
            if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationComponent.setLocationComponentEnabled(true);
// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
// Add the location icon click listener
            reverseGeocode(map.getCameraPosition());
            locationComponent.addOnLocationClickListener(new OnLocationClickListener() {
                @Override
                public void onLocationComponentClick() {

                    Toast.makeText(MapActivity.this, "شما اینجا هستید", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            PermissionsManager permissionsManager = new PermissionsManager(new PermissionsListener() {
                @Override
                public void onExplanationNeeded(List<String> permissionsToExplain) {
                }

                @Override
                public void onPermissionResult(boolean granted) {
                    if (granted)
                        enableLocationComponent();
                    else
                        Toast.makeText(MapActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
            });
            permissionsManager.requestLocationPermissions(MapActivity.this);
        }
    }
}
