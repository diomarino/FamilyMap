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
import com.marinoo.familymap.cmodel.Filter;
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
    private View thisView;
    private boolean markerClicked = false;
    private boolean isEventActivity = false;
    private boolean initialTreeLine = true;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onResume() {
        super.onResume();

        remakeMarkers();

        if ((!Settings.getInstance().isShowSpouseLines() && !spousePolyLine.isEmpty())) {

            for (Polyline polyline : spousePolyLine) {
                polyline.remove();
            }

        } else {

            if ((Filter.getInstance().isShowFemaleEvents() && !Filter.getInstance().isShowMaleEvents()) ||
                    (Filter.getInstance().isShowMaleEvents() && !Filter.getInstance().isShowFemaleEvents())) {

                for (Polyline polyline : spousePolyLine) {
                    polyline.remove();
                }
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

        if (mapsCenteredEvent != null) {
            clickMarker(thisView);
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

        thisView = v;
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

                    mapMarkers = Filter.getInstance().getMapMarkers();
                    initialTreeLine = false;
                    remakeMarkers();

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

        if(initialTreeLine) {
            Filter.getInstance().setMapMarkers(mapMarkers);
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

                    if (Filter.getInstance().isShowMaleEvents() && Filter.getInstance().isShowFemaleEvents()) {
                        makeSpouseLine(selectedPerson, mapsCenteredEvent);
                    }
                }
            }

            if (Settings.getInstance().isShowLifeStoryLines()) {
                makeLifeStoryLine(selectedPerson);
            }

            if (Settings.getInstance().isShowFamilyTreeLines() && initialTreeLine) {

                initialTreeLine = false;
                makeFamilyTreeLine(selectedPerson, mapsCenteredEvent);

            } else if (!Settings.getInstance().isShowFamilyTreeLines()) {

            } else {

                for (Polyline polyline : familyTreeLine) {
                    polyline.remove();
                }

                remakeFamilyTreeLine(selectedPerson, mapsCenteredEvent);
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

        for (int i = 0; i < lifeStoryEvents.size(); i++) {

            Event event = lifeStoryEvents.get(i);
            polylineOptions.add(new LatLng(event.getLatitude(), event.getLongitude()));
            polylineOptions.color(Color.BLUE).width(14);
            Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
            lifeStoryLine.add(polyline);

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
                polylineOptions.color(Color.DKGRAY).width(16);
                Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
                familyTreeLine.add(polyline);
                makeAncestorsFamilyTreeLine(parent, 16);
            }
        }
    }

    private void makeAncestorsFamilyTreeLine(Person person, int lineWidth) {

        Set<Person> parentSet = FamilyTree.getInstance().getParentSet(person);
        lineWidth /= 2;
        if (lineWidth == 0) {
            lineWidth = 3;
        }

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

    private void remakeMarkers() {

        for (Marker marker : mapMarkers) {
            Event event = (Event) marker.getTag();
            Person person = FamilyTree.getInstance().getPeopleMap().get(event.getPersonID());

            if (Filter.getInstance().isShowMotherSide() && Filter.getInstance().isShowFatherSide() &&
                    Filter.getInstance().isShowFemaleEvents() && Filter.getInstance().isShowMaleEvents()) {
                marker.setVisible(true);

            } else if (Filter.getInstance().isShowMotherSide() && !Filter.getInstance().isShowFatherSide()) {
                checkMaternalFilter(marker);

            } else if (Filter.getInstance().isShowFatherSide() && !Filter.getInstance().isShowMotherSide()) {
                checkPaternalFilter(marker);

            } else {

                if (person.getPersonID().equals(FamilyTree.getInstance().getLoggedInUser().getPersonID()) ||
                        person.getPersonID().equals(FamilyTree.getInstance().getLoggedInUser().getSpouseID())) {

                    marker.setVisible(true);

                } else {

                    marker.setVisible(false);
                }
            }

            if (marker.isVisible()) {

                if (Filter.getInstance().isShowFemaleEvents() && Filter.getInstance().isShowMaleEvents()) {
                    marker.setVisible(true);

                } else if (Filter.getInstance().isShowFemaleEvents() && !Filter.getInstance().isShowMaleEvents()) {

                    if (!person.getGender().equals("f")) {
                        marker.setVisible(false);
                    }

                } else if (Filter.getInstance().isShowMaleEvents() && !Filter.getInstance().isShowFemaleEvents()) {

                    if (!person.getGender().equals("m")) {
                        marker.setVisible(false);
                    }

                } else {
                    marker.setVisible(false);
                }
            }
        }
    }

    private void checkMaternalFilter(Marker marker) {

        Event event = (Event) marker.getTag();
        Person person = FamilyTree.getInstance().getPeopleMap().get(event.getPersonID());
        Set<Person> maternalSet = FamilyTree.getInstance().getMaternalAncestors();


        if (person.getPersonID().equals(FamilyTree.getInstance().getLoggedInUser().getPersonID()) ||
                person.getPersonID().equals(FamilyTree.getInstance().getLoggedInUser().getSpouseID())
            || maternalSet.contains(person)) {

            marker.setVisible(true);

        } else {
            marker.setVisible(false);
        }
    }

    private void checkPaternalFilter(Marker marker) {

        Event event = (Event) marker.getTag();
        Person person = FamilyTree.getInstance().getPeopleMap().get(event.getPersonID());
        Set<Person> paternalSet = FamilyTree.getInstance().getPaternalAncestors();


        if (person.getPersonID().equals(FamilyTree.getInstance().getLoggedInUser().getPersonID()) ||
                person.getPersonID().equals(FamilyTree.getInstance().getLoggedInUser().getSpouseID())
                || paternalSet.contains(person)) {

            marker.setVisible(true);

        } else {
            marker.setVisible(false);
        }
    }

    private void remakeFamilyTreeLine(Person person, Event selectedEvent) {

        Set<Person> parentSet = FamilyTree.getInstance().getParentSet(person);
        for (Person parent : parentSet) {

            if (parent != null) {

                ArrayList<Event> parentEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(parent.getPersonID());
                PolylineOptions polylineOptions = new PolylineOptions();
                for (Marker parentMarker : mapMarkers) {

                    if (parentMarker.isVisible()) {

                        Event parentEvent = (Event) parentMarker.getTag();

                        if (parentEvent.getEventID().equals(parentEvents.get(0).getEventID())) {

                            polylineOptions.add(new LatLng(selectedEvent.getLatitude(), selectedEvent.getLongitude()));
                            polylineOptions.add(new LatLng(parentEvents.get(0).getLatitude(), parentEvents.get(0).getLongitude()));
                            polylineOptions.color(Color.DKGRAY).width(18);
                            Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
                            familyTreeLine.add(polyline);
                            remakeAncestorsFamilyTreeLine(parent, 18);
                        }
                    }
                }
            }
        }
    }


    private void remakeAncestorsFamilyTreeLine(Person person, int lineWidth) {

        lineWidth /= 2;
        if (lineWidth == 0) {
            lineWidth = 3;
        }

        Set<Person> parentSet = FamilyTree.getInstance().getParentSet(person);
        for (Person parent : parentSet) {

            if (parent != null) {

                ArrayList<Event> parentEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(parent.getPersonID());
                ArrayList<Event> personEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(person.getPersonID());
                PolylineOptions polylineOptions = new PolylineOptions();

                for (Marker marker : mapMarkers) {

                    Event event = (Event) marker.getTag();

                    if (marker.isVisible()) {

                        if (event.getEventID().equals(personEvents.get(0).getEventID())) {
                            polylineOptions.add(new LatLng(personEvents.get(0).getLatitude(), personEvents.get(0).getLongitude()));

                        } else if (event.getEventID().equals(parentEvents.get(0).getEventID())) {
                            polylineOptions.add(new LatLng(parentEvents.get(0).getLatitude(), parentEvents.get(0).getLongitude()));
                        }
                    }
                }
                polylineOptions.color(Color.DKGRAY).width(lineWidth);
                Polyline polyline = myGoogleMap.addPolyline(polylineOptions);
                familyTreeLine.add(polyline);
                remakeAncestorsFamilyTreeLine(parent, lineWidth);
            }
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
