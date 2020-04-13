package com.marinoo.familymap.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.model.Event;
import com.marinoo.familymap.model.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private String selectedPersonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        selectedPersonID = getIntent().getExtras().getString("selectedPersonID");
        setContentView(R.layout.activity_person);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Person Details");
        setPersonActivityTextViews();

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        List<Event> selectedPersonEvents = FamilyTree.getInstance().getPeoplesEventsMap().get(selectedPersonID);
        List<Person> selectedPersonFamilyMembers = getSelectedPersonFamilyMembers();

        expandableListView.setAdapter(new ExpandableListAdapter(this, selectedPersonEvents, selectedPersonFamilyMembers));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int EVENTS_GROUP_POSITION = 0;
        private static  final int FAMILY_MEMBERS_GROUP_POSITION = 1;

        private final List<Event> events;
        private final List<Person> familyMembers;
        private Context context;

        ExpandableListAdapter(Context context, List<Event> events, List<Person> familyMembers) {
            this.context = context;
            this.events = events;
            this.familyMembers = familyMembers;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    return events.size();
                case FAMILY_MEMBERS_GROUP_POSITION:
                    return familyMembers.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    return getString(R.string.lifeEventsTitle);
                case FAMILY_MEMBERS_GROUP_POSITION:
                    return getString(R.string.familyTitle);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    return events.get(childPosition);
                case  FAMILY_MEMBERS_GROUP_POSITION:
                    return familyMembers.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_person_details_group, parent, false);
            }

            TextView title = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    title.setText(R.string.lifeEventsTitle);
                    break;
                case FAMILY_MEMBERS_GROUP_POSITION:
                    title.setText(R.string.familyTitle);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            View itemView;

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.life_events_item, parent, false);
                    initializeLifeEventsView(itemView, childPosition);
                    break;
                case FAMILY_MEMBERS_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.family_members_item, parent, false);
                    initializeFamilyMembersView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        @SuppressLint("SetTextI18n")
        private void initializeLifeEventsView(View lifeEventsView, int childPosition) {

            final Event event = events.get(childPosition);
            Person person = FamilyTree.getInstance().getPeopleMap().get(event.getPersonID());

            ImageView marker = lifeEventsView.findViewById(R.id.markerIcon);
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

            TextView eventDetails = lifeEventsView.findViewById(R.id.event);
            eventDetails.setText(event.getEventType().toUpperCase() + ": " + event.getCity() +
                    ", " + event.getCountry() + " (" + event.getYear() + ")");

            TextView personName = lifeEventsView.findViewById(R.id.eventsPersonName);
            assert person != null;
            personName.setText(person.getFirstName() + " " + person.getLastName());

            lifeEventsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedEventID", event.getEventID());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


        }

        @SuppressLint("SetTextI18n")
        private void initializeFamilyMembersView(View familyMembersView, int childPosition) {

            final Person selectedPerson = FamilyTree.getInstance().getPeopleMap().get(selectedPersonID);
            final Person familyMember = familyMembers.get(childPosition);

            ImageView gender = familyMembersView.findViewById(R.id.genderIcon2);
            if (familyMember.getGender().equals("f")) {
                Drawable femaleIcon = new IconDrawable(context, FontAwesomeIcons.fa_female).colorRes(R.color.femaleColor).sizeDp(50);
                gender.setImageDrawable(femaleIcon);
            } else {
                Drawable maleIcon = new IconDrawable(context, FontAwesomeIcons.fa_male).colorRes(R.color.maleColor).sizeDp(50);
                gender.setImageDrawable(maleIcon);
            }

            TextView familyFullName = familyMembersView.findViewById(R.id.familyFullName);
            familyFullName.setText(familyMember.getFirstName() + " " + familyMember.getLastName());

            TextView familyMemberRelationship = familyMembersView.findViewById(R.id.familyRelationship);
            if (selectedPerson.getFatherID().equals(familyMember.getPersonID())) {
                familyMemberRelationship.setText("Father");

            } else if (selectedPerson.getMotherID().equals(familyMember.getPersonID())) {
                familyMemberRelationship.setText("Mother");

            } else if (selectedPerson.getSpouseID().equals(familyMember.getPersonID())) {
                familyMemberRelationship.setText("Spouse");

            } else if (selectedPerson.getPersonID().equals(familyMember.getFatherID()) || selectedPerson.getPersonID().equals(familyMember.getMotherID())) {
                familyMemberRelationship.setText("Child");
            }

            familyMembersView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PersonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedPersonID", familyMember.getPersonID());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setPersonActivityTextViews() {

        TextView firstName = findViewById(R.id.pActivityFirstName);
        TextView lastName = findViewById(R.id.pActivityLastName);
        TextView gender = findViewById(R.id.pActivityGender);
        Person selectedPerson;
        selectedPerson = FamilyTree.getInstance().getPeopleMap().get(selectedPersonID);

        assert selectedPerson != null;
        firstName.setText(selectedPerson.getFirstName());
        lastName.setText(selectedPerson.getLastName());

        if (selectedPerson.getGender().equals("f")) {
            gender.setText("Female");

        } else {
            gender.setText("Male");
        }
    }

    private ArrayList<Person> getSelectedPersonFamilyMembers() {

        ArrayList<Person> familyMembers = new ArrayList<>();
        Person selectedPerson = FamilyTree.getInstance().getPeopleMap().get(selectedPersonID);

        if (FamilyTree.getInstance().getPeopleMap().get(selectedPerson.getFatherID()) != null) {
            familyMembers.add(FamilyTree.getInstance().getPeopleMap().get(selectedPerson.getFatherID()));
        }

        if (FamilyTree.getInstance().getPeopleMap().get(selectedPerson.getMotherID()) != null) {
            familyMembers.add(FamilyTree.getInstance().getPeopleMap().get(selectedPerson.getMotherID()));
        }

        if (FamilyTree.getInstance().getPeopleMap().get(selectedPerson.getSpouseID()) != null) {
            familyMembers.add(FamilyTree.getInstance().getPeopleMap().get(selectedPerson.getSpouseID()));
        }

        if (FamilyTree.getInstance().getChildrenMap().get(selectedPersonID) != null) {
            ArrayList<Person> children = FamilyTree.getInstance().getChildrenMap().get(selectedPersonID);
            familyMembers.addAll(children);
        }

        return  familyMembers;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent);
        }
        return true;
    }

    @SuppressLint("ResourceType")
    private IconDrawable getColoredMarkerIcon(Float hex) {

        if (hex.equals(FamilyTree.getInstance().getColorByIndex(0))) {
            return new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.azure).sizeDp(50);

        } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(1))) {
            return  new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.blue).sizeDp(50);

        } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(2))) {
            return new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.orange).sizeDp(50);

        } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(3))) {
            return new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.rose).sizeDp(50);

        } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(4))) {
            return new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.cyan).sizeDp(50);

        } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(5))) {
            return new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.green).sizeDp(50);

        } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(6))) {
            return new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.magenta).sizeDp(50);

        } else if (hex.equals(FamilyTree.getInstance().getColorByIndex(7))) {
            return new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.red).sizeDp(50);
        }

        return null;
    }
}
