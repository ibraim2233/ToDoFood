package com.example.todofood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.GeoObjectCollection;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.BoundingBox;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateSource;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.map.VisibleRegionUtils;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.mapkit.search.SuggestItem;
import com.yandex.mapkit.search.SuggestOptions;
import com.yandex.mapkit.search.SuggestSession;
import com.yandex.mapkit.search.SuggestType;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements UserLocationObjectListener, SuggestSession.SuggestListener, Session.SearchListener, CameraListener {
    private MapView mapview;
    private UserLocationLayer userLocationLayer;

    private final int RESULT_NUMBER_LIMIT = 5;

    private SearchManager searchManager;
    private Session searchSession;
    private SuggestSession suggestSession;
    private ListView suggestResultView;
    private ArrayAdapter resultAdapter;
    private List<String> suggestResult;
    SharedPreferences sPref;
    EditText queryEdit;
    private static final int REQUEST_LOCATION = 123;
    private final Point CENTER = new Point(55.75, 37.62);
    private final double BOX_SIZE = 0.2;
    private final BoundingBox BOUNDING_BOX = new BoundingBox(
            new Point(CENTER.getLatitude() - BOX_SIZE, CENTER.getLongitude() - BOX_SIZE),
            new Point(CENTER.getLatitude() + BOX_SIZE, CENTER.getLongitude() + BOX_SIZE));
    private final SuggestOptions SEARCH_OPTIONS =  new SuggestOptions().setSuggestTypes(
            SuggestType.GEO.value |
            SuggestType.BIZ.value |
            SuggestType.TRANSIT.value);

    private void submitQuery(String query) {
        searchSession = searchManager.submit(
                query,
                VisibleRegionUtils.toPolygon(mapview.getMap().getVisibleRegion()),
                new SearchOptions(),
                this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey("ef2505a6-a3e6-40e0-8d18-606524f64034");
        MapKitFactory.initialize(this);
        SearchFactory.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            mapOpen();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
            REQUEST_LOCATION);
        }


        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);
        suggestSession = searchManager.createSuggestSession();
        queryEdit = findViewById(R.id.search_map);
        suggestResultView = (ListView)findViewById(R.id.suggest_result);
        suggestResult = new ArrayList<>();
        resultAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                suggestResult);
        suggestResultView.setAdapter(resultAdapter);

        queryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                requestSuggest(editable.toString());
            }
        });

        // Укажите имя activity вместо map.
        mapview = findViewById(R.id.mapview);
        //mapview.getMap().setRotateGesturesEnabled(false);
        mapview.getMap().move(
                new CameraPosition(new Point(44.952116, 34.102411), 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        // Симферополь, надо сделать при открытии переход к месту расположения пользователя !!!


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    mapOpen();
                } else {
                    // permission denied
                }
                return;
        }
    }
    @Override
    protected void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }






public void OnClickNext(View view){
        Intent intent = new Intent(MapsActivity.this,MainActivity.class);
        intent.putExtra("Activity","Maps");
        sPref = getSharedPreferences("Data",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Adress", queryEdit.getText().toString());
        ed.apply();
        startActivity(intent);
        finish();
}
    public void onBackPressed() {
        suggestResult.clear();
        resultAdapter.notifyDataSetChanged();
        suggestResultView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onObjectAdded(UserLocationView userLocationView) {

        userLocationLayer.setAnchor(
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.5)),
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.83)));
/*        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.ic_pin));*/
       // userLocationLayer.resetAnchor();
        //CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();

/*        pinIcon.setIcon(
                "icon",
                ImageProvider.fromResource(this, R.drawable.map_pin),
                new IconStyle().setAnchor(new PointF(0f, 0f))
                        .setRotationType(RotationType.NO_ROTATION)
                        .setZIndex(0f)
                        .setScale(1f)
        );*/
/*
        pinIcon.setIcon(
                "pin",
                ImageProvider.fromResource(this, R.drawable.ic_ruble),
                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(1f)
                        .setScale(0.5f)
        );

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);*/
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }


    @Override
    public void onResponse(@NonNull List<SuggestItem> suggest) {
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        suggestResult.clear();
        for (int i = 0; i < Math.min(RESULT_NUMBER_LIMIT, suggest.size()); i++) {
            suggestResult.add(suggest.get(i).getDisplayText());
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK="+suggest.get(i));
        }
        resultAdapter.notifyDataSetChanged();
        suggestResultView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onError(@NonNull Error error) {
        String errorMessage = "Error1"/*getString(R.string.unknown_error_message)*/;
        if (error instanceof RemoteError) {
            errorMessage ="Error2" /*getString(R.string.remote_error_message)*/;
        } else if (error instanceof NetworkError) {
            errorMessage ="Error3" /*getString(R.string.network_error_message)*/;
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void requestSuggest(String query) {
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLSS");
        //suggestResultView.setVisibility(View.INVISIBLE);
        suggestResult.clear();
        suggestSession.suggest(query, BOUNDING_BOX, SEARCH_OPTIONS, this);
    }
    /////////////////////////////////////////////////////////search
    @Override
    public void onSearchResponse(@NonNull Response response) {
        MapObjectCollection mapObjects = mapview.getMap().getMapObjects();
        mapObjects.clear();

        for (GeoObjectCollection.Item searchResult : response.getCollection().getChildren()) {
            Point resultLocation = searchResult.getObj().getGeometry().get(0).getPoint();

            if (resultLocation != null) {
                mapview.getMap().move(
                        new CameraPosition(resultLocation, 15.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0),
                        null);
                mapObjects.addPlacemark(
                        resultLocation,ImageProvider.fromResource(this, R.drawable.map_pin));
                break;
            }

        }
    }

    @Override
    public void onSearchError(@NonNull Error error) {
        String errorMessage = ("unknown_error_message");
        if (error instanceof RemoteError) {
            errorMessage = ("remote_error_message");
        } else if (error instanceof NetworkError) {
            errorMessage = ("network_error_message");
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateSource cameraUpdateSource, boolean finished) {
        userLocationLayer.resetAnchor();
        if (finished) {
            //submitQuery(queryEdit.getText().toString());
        }
    }

    public void mapOpen(){
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapview.getMapWindow());
        CameraPosition userpoint =userLocationLayer.cameraPosition();
        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS============"+userLocationLayer.cameraPosition());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);

/*        mapview.getMap().move(
                new CameraPosition(new Point(0, 0), 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);*/
        ///////////////////////////////////search
        mapview.getMap().addCameraListener(this);

        queryEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    submitQuery(queryEdit.getText().toString());


                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //Hide:
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    //Show
                    //imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                    suggestResult.clear();
                    resultAdapter.notifyDataSetChanged();
                    suggestResultView.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });
        //submitQuery(queryEdit.getText().toString());
        suggestResultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
//                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                //                      Toast.LENGTH_SHORT).show();
                queryEdit.setText(((TextView) itemClicked).getText());
                queryEdit.setSelection(queryEdit.getText().length());

            }
        });
    }
}