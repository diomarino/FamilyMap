package com.marinoo.familymap.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.model.Event;
import com.marinoo.familymap.model.Person;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 1;
    private List<Person> people = new ArrayList<>();
    private  List<Event> events = new ArrayList<>();
    private  RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Search");

        SearchView searchView = findViewById(R.id.searchViewBar);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, people, events);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        people = FamilyTree.getInstance().getPeople();
        events = FamilyTree.getInstance().getAllEvents();
        String userInput = newText.toLowerCase();
        List<Person> updatedPeople = new ArrayList<>();
        List<Event> updatedEvents = new ArrayList<>();

        for (Person person : people) {

            if (person.getFirstName().toLowerCase().contains(userInput) || person.getLastName().toLowerCase().contains(userInput)) {
                updatedPeople.add(person);
            }
        }

        recyclerViewAdapter.updateListPeople(updatedPeople);

        for (Event event : events) {

            if (event.getEventType().toLowerCase().contains(userInput) || event.getCountry().toLowerCase().contains(userInput) ||
            event.getCity().toLowerCase().contains(userInput)) {
                updatedEvents.add(event);
            }
        }

        recyclerViewAdapter.updateListEvents(updatedEvents);

        return true;
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private  List<Person> people;
        private  List<Event> events;
        private Context context;

        RecyclerViewAdapter(Context context, List<Person> people, List<Event> events) {
            this.people = people;
            this.events = events;
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            return position < people.size() ? PERSON_ITEM_VIEW_TYPE : EVENT_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view;

            if (viewType == PERSON_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.search_person_item, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.search_event_item, parent, false);
            }

            return new RecyclerViewHolder(context, view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

            if (position < people.size()) {
                holder.bind(people.get(position));

            } else {
                holder.bind(events.get(position - people.size()));
            }
        }

        @Override
        public int getItemCount() {
            return people.size() + events.size();
        }

        public void updateListPeople(List<Person> updatedPeople) {
            people = new ArrayList<>();
            people.addAll(updatedPeople);
            notifyDataSetChanged();
        }

        public void updateListEvents(List<Event> updatedEvents) {
            events = new ArrayList<>();
            events.addAll(updatedEvents);
            notifyDataSetChanged();
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;
        private  final TextView eventDetails;
        private final ImageView gender;
        private final ImageView marker;
        private final View view;

        private Context context;
        private final int viewType;
        private Event currentEvent;
        private Person currentPerson;

        RecyclerViewHolder(Context context, View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            this.context = context;
            this.view = itemView;

            itemView.setOnClickListener(this);

            if (viewType == PERSON_ITEM_VIEW_TYPE) {
                name = itemView.findViewById(R.id.searchPersonFullName);
                gender = itemView.findViewById(R.id.searchGenderIcon);
                marker = null;
                eventDetails = null;
            } else {
                name = itemView.findViewById(R.id.searchEventFullName);
                eventDetails = itemView.findViewById(R.id.searchEvent);
                marker = itemView.findViewById(R.id.markerIcon2);
                gender = null;
            }
        }

        @SuppressLint("SetTextI18n")
        private void bind(final Person person) {

            this.currentPerson = person;
            name.setText(person.getFirstName() + " " + person.getLastName());

            if (person.getGender() == "f") {
                Drawable femaleIcon = new IconDrawable(context, FontAwesomeIcons.fa_female).colorRes(R.color.femaleColor).sizeDp(50);
                gender.setImageDrawable(femaleIcon);
            } else {
                Drawable maleIcon = new IconDrawable(context, FontAwesomeIcons.fa_male).colorRes(R.color.maleColor).sizeDp(50);
                gender.setImageDrawable(maleIcon);
            }

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PersonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedPersonID", currentPerson.getPersonID());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        private void bind(Event event) {

            this.currentEvent = event;
            eventDetails.setText(event.getEventType().toUpperCase() + ": " + event.getCity() +
                    ", " + event.getCountry() + " (" + event.getYear() + ")");

            int currentIndex = FamilyTree.getInstance().getAllEventTypes().indexOf(event.getEventType().toLowerCase());
            if (currentIndex > 7) {

                while (currentIndex > 7) {

                    currentIndex -= 8;
                }

                int colorIndex = currentIndex;
                Float hexColor = FamilyTree.getInstance().getColorByIndex(colorIndex);
                Drawable markerIcon = getColoredMarkerIcon(hexColor);
                marker.setImageDrawable(markerIcon);

            } else {

                Float hexColor = FamilyTree.getInstance().getColorByIndex(currentIndex);
                Drawable markerIcon = getColoredMarkerIcon(hexColor);
                marker.setImageDrawable(markerIcon);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedEventID", currentEvent.getEventID());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        @SuppressLint("ResourceType")
        private IconDrawable getColoredMarkerIcon(Float hex) {

            if (hex.equals(FamilyTree.getInstance().getColorByIndex(0))) {
                return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.azure).sizeDp(50);

            } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(1))) {
                return  new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.blue).sizeDp(50);

            } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(2))) {
                return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.orange).sizeDp(50);

            } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(3))) {
                return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.rose).sizeDp(50);

            } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(4))) {
                return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.cyan).sizeDp(50);

            } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(5))) {
                return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.green).sizeDp(50);

            } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(6))) {
                return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.magenta).sizeDp(50);

            } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(7))) {
                return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.red).sizeDp(50);
            }

            return null;
        }
    }
}
