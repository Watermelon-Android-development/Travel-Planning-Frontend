package com.example.travelplan.ui.map;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.*;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.example.travelplan.R;
import com.example.travelplan.ShortestRouteAlgorithm;
import com.example.travelplan.TravelDatabaseHelper;
import com.example.travelplan.databinding.FragmentMapBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MapFragment extends Fragment implements AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, AMap.OnMapClickListener {
    public static final double CB_LONGITUDE = 120.738274;
    public static final double CB_LATITUDE = 31.272761;

    private FragmentMapBinding binding;
    private TravelDatabaseHelper travelDatabaseHelper;
    private MapView mapView = null;
    private List<TravelDatabaseHelper.Site> data;
    private List<TravelDatabaseHelper.Plan> plandata;
    View infoWindow = null;
    private UiSettings uiSettings;
    private AMap aMap;
    private Marker clickMaker;
    View root;
    private SharedPreferences sp;
    private List<Integer> icon_list = List.of(R.drawable.num_1, R.drawable.num_2, R.drawable.num_3, R.drawable.num_4,
            R.drawable.num_5, R.drawable.num_6, R.drawable.num_7, R.drawable.num_8,
            R.drawable.num_9, R.drawable.num_10, R.drawable.num_11, R.drawable.num_12, R.drawable.num_13);
    private RecyclerView recyclerView;
    private display_window_Adapter display_Adapter;
    private Boolean radio_btn_check = true;
    private List<Integer> route;
    private String route_name;
    private Boolean check_routetitle_result = true;

    private static double longitude, latitude;

    public static void setLocation(double longitude, double latitude) {
        MapFragment.longitude = longitude;
        MapFragment.latitude = latitude;
        Log.d("jing", String.valueOf(longitude));
        Log.d("wei", String.valueOf(latitude));
    }


    class getAllLocs extends AsyncTask<Void, Void, Boolean> {

        // method 1???onPreExecute??????
        // effect???execute before thread
        @Override
        protected void onPreExecute() {

            if (isFristRun()) {
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.click_on_image);
                showDialog(getContext(), bitmap);
            }
            ;

        }

        // method 2???doInBackground??????
        // effect???accept input parameter, fetch data, return result
        @Override
        protected Boolean doInBackground(Void... voids) {


            data = travelDatabaseHelper.getAllFavoriteSites();
            aMap.clear();
            Log.e("sp_before_sort", "sp: " + sp.getString("route", ""));
            String location_string = sp.getString("route", "");
            List<String> location_list = new ArrayList<String>(); //single string to string list
            location_list.addAll(Arrays.asList(location_string.split(",")));

//            location_list.add("??");
            if (location_list.contains("")) {
                location_list.remove("");
            }
            List<Integer> int_list = new ArrayList<Integer>(); //string list to int list
            for (int i = 0; i < location_list.size(); i++) {
                int id = Integer.parseInt(location_list.get(i));
                int_list.add(id);
            }
            Log.e(" int_list", ": " + int_list);

            List<Integer> int_list_2 = new ArrayList<Integer>(); //string list to int list
            for (int i = 0; i < data.size(); i++) {
                int_list_2.add((data.get(i).getId()));
            }
            Log.e(" int_list_2", ": " + int_list_2);
            int_list.retainAll(int_list_2);
            Log.e("updated_int_list", ": " + int_list);

            ArrayList<TravelDatabaseHelper.Site> sites_for_sort = new ArrayList<TravelDatabaseHelper.Site>();
            for (int i = 0; i < int_list.size(); i++) {
                int num = int_list.get(i);
                int get_index = int_list_2.indexOf(num);
                Log.e(" get_inedx", ": " + get_index);
                sites_for_sort.add(data.get(get_index));
                Log.e(" sites_for_sort", "id: " + data.get(get_index).getId());
            }

            Log.e("sites_for_sort_content", "content: " + sites_for_sort);
            List<Integer> site_sorted;
            double[] myLocation = new double[]{longitude, latitude};
            site_sorted = ShortestRouteAlgorithm.getShortestRoute(myLocation, sites_for_sort);
            Log.e("site_sorted_content", "content: " + site_sorted);
            for (int i = 0; i < data.size(); i++) {
                Double lat = data.get(i).getyCoor(); //latitude
                Double lng = data.get(i).getxCoor(); //longitude
                LatLng latLng3 = new LatLng(lat, lng);
                //Marker
                MarkerOptions options = new MarkerOptions();
                options.position(latLng3);//location
                String imageID = String.valueOf(data.get(i).getImgID());
                if (site_sorted.contains(data.get(i).getId())) {
                    options.icon(BitmapDescriptorFactory.fromResource(icon_list.get(site_sorted.indexOf(data.get(i).getId()))));
                    options.draggable(false);
                } else {
                    options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue));
                    options.draggable(true);
                }
                options.title(imageID + "," + data.get(i).getId());//imageID + id
                options.snippet(data.get(i).getName());
                aMap.addMarker(options);
            }

            ArrayList<LatLng> latLngList = new ArrayList<LatLng>();
            for (int i = 0; i < site_sorted.size(); i++) {
                latLngList.add(new LatLng(data.get(int_list_2.indexOf(site_sorted.get(i))).getyCoor(), data.get(int_list_2.indexOf(site_sorted.get(i))).getxCoor()));
            }

            String final_route = "";
            for (int i = 0; i < site_sorted.size(); i++) {
                final_route += site_sorted.get(i) + ",";
            }

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("route", final_route);
            editor.apply();
            Log.e("final_route_content", "content: " + final_route);

            PolylineOptions pl = new PolylineOptions()
                    .addAll(latLngList)
                    .width(5)
                    .setDottedLine(false);
            aMap.addPolyline(pl);


            return true;
        }


        //         method 4???onPostExecute??????
//         effect???accept result, show result on UI
        @Override
        protected void onPostExecute(Boolean success) {

        }

        // method 5???onCancelled()
        // effect???cancel async task
        @Override
        protected void onCancelled() {
        }
    }

    //the method for the "only show chosen sites" map display status
    class getpart extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {

        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            data = travelDatabaseHelper.getAllFavoriteSites();
            aMap.clear();

            //deal with the chosen sites
            String location_string = sp.getString("route", "");
            List<String> location_list = new ArrayList<String>(); //single string to string list
            location_list.addAll(Arrays.asList(location_string.split(",")));
            if (location_list.contains("")) {
                location_list.remove("");
            }
            List<Integer> int_list = new ArrayList<Integer>(); //string list to int list
            for (int i = 0; i < location_list.size(); i++) {
                int id = Integer.parseInt(location_list.get(i));
                int_list.add(id);
            }
            List<Integer> int_list_2 = new ArrayList<Integer>(); //string list to int list
            for (int i = 0; i < data.size(); i++) {
                int_list_2.add((data.get(i).getId()));
            }
            int_list.retainAll(int_list_2);


            //use algorithm to generate the route
            ArrayList<TravelDatabaseHelper.Site> sites_for_sort = new ArrayList<TravelDatabaseHelper.Site>();
            for (int i = 0; i < int_list.size(); i++) {
                int num = int_list.get(i);
                int get_index = int_list_2.indexOf(num);
                sites_for_sort.add(data.get(get_index));
            }
            List<Integer> site_sorted;
            double[] myLocation = new double[]{longitude, latitude};
            site_sorted = ShortestRouteAlgorithm.getShortestRoute(myLocation, sites_for_sort);


            //draw markers in the map
            for (int i = 0; i < data.size(); i++) {
                if (site_sorted.contains(data.get(i).getId())) {
                    Double lat = data.get(i).getyCoor(); //latitude
                    Double lng = data.get(i).getxCoor(); //longitude
                    LatLng latLng3 = new LatLng(lat, lng);
                    MarkerOptions options = new MarkerOptions();
                    options.position(latLng3);

                    String imageID = String.valueOf(data.get(i).getImgID());
                    options.icon(BitmapDescriptorFactory.fromResource(icon_list.get(site_sorted.indexOf(data.get(i).getId()))));
                    options.draggable(false);
                    options.title(imageID + "," + data.get(i).getId());
                    options.snippet(data.get(i).getName());
                    aMap.addMarker(options);
                } else {
                }
            }


            //draw the lines between markers in the map
            ArrayList<LatLng> latLngList = new ArrayList<LatLng>();
            for (int i = 0; i < site_sorted.size(); i++) {
                latLngList.add(new LatLng(data.get(int_list_2.indexOf(site_sorted.get(i))).getyCoor(), data.get(int_list_2.indexOf(site_sorted.get(i))).getxCoor()));
            }
            PolylineOptions pl = new PolylineOptions()
                    .addAll(latLngList)
                    .width(5)
                    .setDottedLine(false);
            aMap.addPolyline(pl);


            //save the route into sp SharedPreferences
            String final_route = "";
            for (int i = 0; i < site_sorted.size(); i++) {
                final_route += site_sorted.get(i) + ",";
            }
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("route", final_route);
            editor.apply();


            return true;

        }


        @Override
        protected void onPostExecute(Boolean success) {

        }


        @Override
        protected void onCancelled() {
        }
    }

    //the render method for the display window
    class display_window extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            data = travelDatabaseHelper.getAllSites();

            //get the route data and deal with it
            String route = sp.getString("route", "");
            List<String> route_stlist = new ArrayList<String>();
            route_stlist.addAll(Arrays.asList(route.split(",")));
            List<Integer> route_inlist = new ArrayList<Integer>();
            if (route_stlist.contains("")) {
                route_stlist.remove("");
            }
            for (int i = 0; i < route_stlist.size(); i++) {
                int index = Integer.parseInt(route_stlist.get(i));
                route_inlist.add(index);

            }

            //combined with the adapter to render the display window
            display_Adapter = new display_window_Adapter(data, getContext(), route_inlist);


            return true;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            recyclerView.setAdapter(display_Adapter);

        }


        @Override
        protected void onCancelled() {


        }
    }

    //the method for check the route(plan) name that user input
    //and then deal with it according the check result
    class check_routetitle extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            plandata = travelDatabaseHelper.getAllPlans();
            if (plandata.size() != 0) {
                for (int i = 0; i < plandata.size(); i++) {
                    String plan_name = plandata.get(i).getTitle();
                    if (route_name.equals(plan_name)) {
                        check_routetitle_result = false; //The plan name already exists
                        break;
                    } else {
                    }
                }
            } else {

            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            //the plan name already exists
            if (!check_routetitle_result) {
                Toast toast = makeText(getContext(), "Failure: Duplicated plan title, please try another name!", LENGTH_LONG);
                showMyToast(toast, 2 * 1000);

            }
            //the plan name is new
            else {
                //execute the save_route()
                new save_route().execute();


                Toast toast = makeText(getContext(), "Success: Plan saved to planlist!", LENGTH_SHORT);
                showMyToast(toast, 2 * 1000);

                //clear sp SharedPreferences
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                //according the map status to refresh the map
                if (radio_btn_check) {
                    aMap.clear();
                    new getAllLocs().execute();
                } else {
                    aMap.clear();
                    new getpart().execute();
                }

                new display_window().execute();
            }
        }


        @Override
        protected void onCancelled() {


        }
    }

    //the method for save the route(plan) into the database
    class save_route extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            travelDatabaseHelper.insertPlan(route, route_name);
            return true;
        }



        @Override
        protected void onPostExecute(Boolean success) {


        }


        @Override
        protected void onCancelled() {


        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //set up a "Personal" SharedPreferences
        sp = getActivity().getSharedPreferences("Personal", MODE_PRIVATE);

        //database
        travelDatabaseHelper = new TravelDatabaseHelper(this.getActivity());

        //set map
        MapViewModel mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mapView = (MapView) root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            setMapAttribute();
        }

        //the initial map status is "show all sites", so this method executes
        new getAllLocs().execute();



        //the Adapter setting for the display window
        recyclerView = root.findViewById(R.id.display_window);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);


        //the route button click event
        TextView rout_btn = root.findViewById(R.id.route_btn);
        rout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //disshow the display window
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);

                }
                //show the display window
                else {
                    //if users don't choose any sites, the display window won't show
                    if (sp.getString("route", "").isEmpty() || sp.getString("route", "").equals("")) {
                        Toast toast = makeText(getContext(), "please add places you want to visit", LENGTH_LONG);
                        showMyToast(toast, 2 * 1000);
                    }
                    else {
                        new display_window().execute();
                    }
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }
        });


        //the radiobutton click event
        RadioGroup radiogroup = (RadioGroup) root.findViewById(R.id.radio_group);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedid) {
                RadioButton rb = radioGroup.findViewById(checkedid);
                String checked_rb = (String) rb.getText();
                if (checked_rb.equals("show all sides")) {
                    new getAllLocs().execute();
                    radio_btn_check = true;

                } else {
                    radio_btn_check = false;
                    new getpart().execute();

                }

            }
        });

        //save button click event
        TextView save_btn = root.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText input = new EditText(getContext());
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Input your plan name");
                builder.setView(input).setNegativeButton("cancel", null);
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //users don't choose any sites
                        if (sp.getString("route", "").isEmpty() || sp.getString("route", "").equals("")) {
                            Toast toast = makeText(getContext(), "please add places you want to visit", LENGTH_LONG);
                            showMyToast(toast, 2 * 1000);
                        }
                        //users choose some sites
                        else {
                            String input_text = input.getText().toString();
                            //input text is not empty
                            if (input_text != null && !input_text.isEmpty()) {
                                String route_str = sp.getString("route", "");

                                List<String> route_str_list = new ArrayList<String>();
                                route_str_list.addAll(Arrays.asList(route_str.split(",")));
                                if (route_str_list.contains("")) {
                                    route_str_list.remove("");
                                }
                                route = new ArrayList<Integer>();
                                for (int j = 0; j < route_str_list.size(); j++) {
                                    int index = Integer.parseInt(route_str_list.get(j));
                                    route.add(index);
                                }
                                route_name = input_text;

                                //check and save the route(plan)
                                new check_routetitle().execute();

                            }
                            //input text is empty
                            else {
                                Toast toast = makeText(getContext(), "please input something!", LENGTH_LONG);
                                showMyToast(toast, 2 * 1000);
                            }
                        }


                    }
                });

                builder.show();


            }
        });

        return root;
    }


    private void setMapAttribute() {

        LatLng latLng = new LatLng(31.288477, 120.617335);//construct a location; location can be searched on https://lbs.amap.com/tools/picker
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11)); //zoom rate
        aMap.setMapLanguage(AMap.ENGLISH);
        //Set the default zoom level
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12)); //zoom rate
        //Hidden bottom right zoom button
        uiSettings.setZoomControlsEnabled(true);
        //Set the marker click event listener
        aMap.setOnMarkerClickListener(this);
        //Set InfoWindow click event listener
        aMap.setOnInfoWindowClickListener(this);
        //Set the custom information window
        aMap.setInfoWindowAdapter(this);
        //Set map click event listener
        aMap.setOnMapClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        clickMaker = marker;
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }


        return true;


    }


    private void showDialog(Context context, Bitmap bitmap) {
        Dialog dia = new Dialog(context);
        dia.setContentView(R.layout.dialog);
        ImageView imageView = (ImageView) dia.findViewById(R.id.ivdialog);
        imageView.setImageBitmap(bitmap);
        dia.show();
        dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = 40;
        dia.onWindowAttributesChanged(lp);
    }

    private boolean isFristRun() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(
                "share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {

            editor.putBoolean("isFirstRun", false);
            editor.commit();
            return true;
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.e("i click here!", "hhhhhhhhh");
        if (marker.isDraggable()) {
            Log.e("sp_before", "sp: " + sp.getString("route", ""));
            Log.e("Test", "blue to red; ");
            String id = marker.getTitle().split(",")[1];  //get infoWin ID
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_red));
            marker.setDraggable(false);
            Toast toast = makeText(this.getContext(), "added to route", LENGTH_LONG);
            showMyToast(toast, 2 * 1000);

            SharedPreferences.Editor editor = sp.edit();
            String location_string = sp.getString("route", "");
            List<String> location_list = new ArrayList<String>();
            location_list.addAll(Arrays.asList(location_string.split(",")));
            if (location_list.contains("")) {
                location_list.remove("");
            }

            int len = location_list.size();
            boolean temp = true;
            for (int i = 0; i < len; i++) {
                if (location_list.get(i).equals(id)) {
                    temp = false;
                }
            }
            if (temp) {
                editor.putString("route", sp.getString("route", "") + id + ",");
                editor.apply();

            }


            Log.e("final_sp", "sp: " + sp.getString("route", ""));


        } else {
            Log.e("sp_before", "sp: " + sp.getString("route", ""));
            Log.e("Test", "red to blue");
            String id = marker.getTitle().split(",")[1];
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue));
            marker.setDraggable(true);
            Toast toast = makeText(this.getContext(), "remove from route", LENGTH_LONG);
            showMyToast(toast, 2 * 1000);

            SharedPreferences.Editor editor = sp.edit();
            String location_string = sp.getString("route", "");
            List<String> location_list = new ArrayList<String>(); //content in sp
            location_list.addAll(Arrays.asList(location_string.split(",")));
            if (location_list.contains("")) {
                location_list.remove("");
            }
            int len = location_list.size();
            int index = -23;
            for (int i = 0; i < len; i++) {
                if (location_list.get(i).equals(id)) {
                    index = i;
                }
            }
            if (index > -23) {
                location_list.remove(index);
            }
            int len_af = location_list.size();
            String final_list = "";
            for (int i = 0; i < len_af; i++) {
                final_list += location_list.get(i) + ",";
            }
            editor.putString("route", final_list);
            editor.apply();
            Log.e("final_sp", "sp: " + sp.getString("route", ""));


        }

        //the map status is "show all sites"
        if (radio_btn_check) {
            new getAllLocs().execute();
        }
        //the map status is "only show chosen sites"
        else {
            new getpart().execute();
        }
        //refresh the display window
        new display_window().execute();
    }

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    /**
     * Listens for custom window infoWindow event callbacks
     */
    @Override
    public View getInfoWindow(Marker marker) {

        infoWindow = LayoutInflater.from(this.getContext()).inflate(R.layout.inforwindow_layout, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * Customize the infoWindow window
     */
    private void render(Marker marker, View infoWindow) {
        TextView content = infoWindow.findViewById(R.id.info_window_content);
        ImageView imageView = infoWindow.findViewById(R.id.iv_1);

        int imageID = Integer.parseInt(marker.getTitle().split(",")[0]);

        imageView.setImageResource(imageID);
        content.setText(marker.getSnippet());
    }

    /**
     * The background and borders of the entire InfoWindow cannot be modified. null is returned
     */
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * Map click event
     * Click on the map area to hide the currently displayed form
     */
    @Override
    public void onMapClick(LatLng latLng) {
        //Check whether the marker information window is displayed
        if (clickMaker != null && clickMaker.isInfoWindowShown()) {
            clickMaker.hideInfoWindow();
        }

    }




    /**
     * set map language
     *
     * @param language AMap.CHINESE, AMap.ENGLISH
     * @since 5.5.0
     */
    public void setMapLanguage(String language) {
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        mapView.onSaveInstanceState(outstate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //When the activity executes onResume, run mMapView.onResume () to redraw the load map
        mapView.onResume();
    }

    @Override
    public void onPause(

    ) {
        super.onPause();
        //When the activity executes onPause, run mMapView.onPause () to pause the load map
        mapView.onPause();
    }


}
