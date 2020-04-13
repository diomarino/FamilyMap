package com.marinoo.familymap.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.cmodel.Settings;
import com.marinoo.familymap.model.Event;
import com.marinoo.familymap.model.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private Context mainActivity;
    private SupportMapFragment supportMapFragment;
    private GoogleMap myGoogleMap;
    private Event mapsCenteredEvent;
    private  View thisView;
    private boolean markerClicked = false;
    private boolean isEventActivity = false;
    private ArrayList<Polyline> spousePolyLine = new ArrayList<>();
    private ArrayList<Polyline> lifeStoryLine = new ArrayList<>();
    private ArrayList<Polyline> familyTreeLine = new ArrayList<>();
    private ArrayList<Marker> mapMarkers = new ArrayList<>();

    public MapFragment(Context in) {
        this.mainActivity = in;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (isEventActivity) {
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!Settings.getInstance().isShowSpouseLines() && !spousePolyLine.isEmpty()) {

            for (Polyline polyline : spousePolyLine) {
                polyline.remove();
            }
        }

        if (!Settings.getInstance().isShowLifeStoryLines() && !lifeStoryLine.isEmpty()) {

            for (Polyline polyline : lifeStoryLine) {
                polyline.remove();
            }
        }

        if (!Settings.getInstance().isShowFamilyTreeLines() && !familyTreeLine.isEmpty()) {

            for (Polyline polyline : familyTreeLine) {
                polyline.remove();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.map_fragment, container, false);
        thisView = v;
        v.setClickable(true);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        LinearLayout eventDescriptor = v.findViewById(R.id.mapFragmentDescriptor);
        eventDescriptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PersonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("selectedPersonID", mapsCenteredEvent.getPersonID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        myGoogleMap = googleMap;
        makeMarkers(thisView);
        myGoogleMap.setOnMarkerClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onMarkerClick(Marker marker) {

        markerClicked = true;
        Log.i("Click", "Marker clicked");
        mapsCenteredEvent = (Event) marker.getTag();
        clickMarker(thisView);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void makeMarkers(View v) {

        FamilyTree familyTree = FamilyTree.getInstance();
        Map<String, Event> events = familyTree.getEventMap();
        Person loggedInUser = familyTree.getLoggedInUser();

        for (Map.Entry<String, Event> entry : events.entrySet()) {
            Event currentEvent = entry.getValue();
            int currentIndex = FamilyTree.getInstance().getAllEventTypes().indexOf(currentEvent.getEventType().toLowerCase());

            if (currentIndex > 7) {

                while (currentIndex > 7) {

                    currentIndex -= 8;
                }
                int colorIndex = currentIndex;
                Float colorHex = FamilyTree.getInstance().getColorByIndex(colorIndex);

                LatLng latLng = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(colorHex));
                Marker marker = myGoogleMap.addMarker(markerOptions);
                marker.setTag(currentEvent);
                mapMarkers.add(marker);

            } else {

                Float colorHex = FamilyTree.getInstance().getColorByIndex(currentIndex);

                LatLng latLng = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(colorHex));
                Marker marker = myGoogleMap.addMarker(markerOptions);
                marker.setTag(currentEvent);
                mapMarkers.add(marker);
            }

            if (isEventActivity) {

                if (currentEvent.getEventID().equals(mapsCenteredEvent.getEventID())) {

                    markerClicked = true;
                    LatLng centerEvent = new LatLng(mapsCenteredEvent.getLatitude(), mapsCenteredEvent.getLongitude());
                    myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(centerEvent));
                    clickMarker(v);
                }

            } else {

                if (currentEvent.getEventType().toLowerCase().equals("birth")) {

                    Float colorHex = FamilyTree.getInstance().getColorByIndex(currentIndex);
                    LatLng latLng = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(colorHex));
                    Marker marker = myGoogleMap.addMarker(markerOptions);
                    marker.setTag(currentEvent);
                    mapMarkers.add(marker);

                    if (currentEvent.getPersonID().equals(loggedInUser.getPersonID())) {
                        LatLng centerEvent = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());
                        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(centerEvent));
                        mapsCenteredEvent = currentEvent;
                        clickMarker(v);
                    }
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("SetTextI18n")
    private void clickMarker(View v) {

        if (markerClicked) {
            FamilyTree familyTree = FamilyTree.getInstance();
            Map<String, Person> peopleMap = familyTree.getPeopleMap();
            Person selectedPerson = peopleMap.get(mapsCenteredEvent.getPersonID());

            ImageView icon = v.findViewById(R.id.genderIcon);
            assert selectedPerson != null;
            if (selectedPerson.getGender().equals("f")) {
                Drawable femaleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.femaleColor).sizeDp(50);
                icon.setImageDrawable(femaleIcon);
            } else {
                Drawable maleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.maleColor).sizeDp(50);
                icon.setImageDrawable(maleIcon);
            }

            TextView personDetails = v.findViewById(R.id.personFullName);
            TextView eventDetails = v.findViewById(R.id.eventDetails);
            personDetails.setText(selectedPerson.getFirstName() + " " + selectedPerson.getLastName());
            eventDetails.setText(mapsCenteredEvent.getEventType().toUpperCase() + ": " + mapsCenteredEvent.getCity() +
                    ", " + mapsCenteredEvent.getCountry() + " (" + mapsCenteredEvent.getYear() + ")");

            if (Settings.getInstance().isShowSpouseLines()) {

                if (!selectedPerson.getSpouseID().equals(null) || !selectedPerson.getSpouseID().equals("")) {
                    makeSpouseLine(selectedPerson, mapsCenteredEvent);
                }
            }

            if (Settings.getInstance().isShowLifeStoryLines()) {
                makeLifeStoryLine(selectedPerson);
            }

            if (Settings.getInstance().isShowFamilyTreeLines()) {
                makeFamilyTreeLine(selectedPerson, mapsCenteredEvent);
            }

        } else {

            TextView details = v.findViewById(R.id.eventDetails);
            details.setText("Click on marker to see event details");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuSearch) {

            Intent intentSearch = new Intent(getActivity(), SearchActivity.class);
            startActivity(intentSearch);
            return true;

        } else if (item.getItemId() == R.id.menuSetting) {

            Intent intentSetting = new Intent(getActivity(), SettingActivity.class);
            startActivity(intentSetting);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeSpouseLine(Person selectedPerson, Event selectedEvent) {

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(selectedEvent.getLatitude(), selectedEvent.getLongitude()));

        Person spouse = FamilyTree.getInstance().getPeopleMap().get(selectedPerson.getSpouseID());
        ArrayList<Event> spouseEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(spouse.getPersonID());
        boolean addedLine = false;

        for (Event event : spouseEvents) {

            if (event.getEventType().toLowerCase().equals("birth")) {
                polylineOptions.add(new LatLng(event.getLatitude(), event.getLongitude()));
                addedLine = true;
            }
        }

        if (!addedLine) {
            polylineOptions.add(new LatLng(spouseEvents.get(0).getLatitude(), spouseEvents.get(0).getLongitude()));
        }

        polylineOptions.color(Color.MAGENTA).width(14);
        Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
        spousePolyLine.add(polyline);
    }

    private void makeLifeStoryLine(Person selectedPerson) {

        ArrayList<Event> lifeStoryEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(selectedPerson.getPersonID());
        PolylineOptions polylineOptions = new PolylineOptions();
        int initialWidth = 14;

        for (int i = 0; i < lifeStoryEvents.size(); i++) {

            polylineOptions.add(new LatLng(lifeStoryEvents.get(i).getLatitude(), lifeStoryEvents.get(i).getLongitude()));
            polylineOptions.color(Color.BLUE).width(initialWidth);
            Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
            lifeStoryLine.add(polyline);
            initialWidth/=2;
        }
    }

    private void makeFamilyTreeLine(Person selectedPerson, Event selectedEvent) {

        Set<Person> parentSet = FamilyTree.getInstance().getParentSet(selectedPerson);
        for (Person parent : parentSet) {

            if (parent != null) {

                ArrayList<Event> parentEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(parent.getPersonID());
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(selectedEvent.getLatitude(), selectedEvent.getLongitude()));
                polylineOptions.add(new LatLng(parentEvents.get(0).getLatitude(), parentEvents.get(0).getLongitude()));
                polylineOptions.color(Color.DKGRAY).width(14);
                Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
                familyTreeLine.add(polyline);
                makeAncestorsFamilyTreeLine(parent, 14);
            }
        }
    }

    private void makeAncestorsFamilyTreeLine(Person person, float lineWidth) {

        Set<Person> parentSet = FamilyTree.getInstance().getParentSet(person);
        lineWidth = lineWidth / 2;
        for (Person parent : parentSet) {

            if (parent != null) {

                ArrayList<Event> parentEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(parent.getPersonID());
                ArrayList<Event> personEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(person.getPersonID());

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(personEvents.get(0).getLatitude(), personEvents.get(0).getLongitude()));
                polylineOptions.add(new LatLng(parentEvents.get(0).getLatitude(), parentEvents.get(0).getLongitude()));
                polylineOptions.color(Color.DKGRAY).width(lineWidth);
                Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
                familyTreeLine.add(polyline);
                makeAncestorsFamilyTreeLine(parent, lineWidth);
            }
        }

        if (Collections.frequency(parentSet, null) == parentSet.size()) {
        }
    }



    public boolean isEventActivity() {
        return isEventActivity;
    }

    public void setEventActivity(boolean eventActivity) {
        isEventActivity = eventActivity;
    }

    public Event getMapsCenteredEvent() {
        return mapsCenteredEvent;
    }

    public void setMapsCenteredEvent(Event mapsCenteredEvent) {
        this.mapsCenteredEvent = mapsCenteredEvent;
    }

    public boolean isMarkerClicked() {
        return markerClicked;
    }

    public void setMarkerClicked(boolean markerClicked) {
        this.markerClicked = markerClicked;
    }
}
