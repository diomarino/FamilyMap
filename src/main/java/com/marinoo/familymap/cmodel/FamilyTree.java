package com.marinoo.familymap.cmodel;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.marinoo.familymap.model.Event;
import com.marinoo.familymap.model.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FamilyTree {

    private static FamilyTree instance;
    private Person loggedInUser;
    private boolean loggedIn;
    private String authToken;
    private Set<Person> paternalAncestors;
    private Set<Person> maternalAncestors;
    private Map<String, Person> peopleMap;
    private Map<String, Event> eventMap;
    private Map<String, ArrayList<Event>> peoplesEventsMap;
    private ArrayList<String> loggedInUserEventTypes;
    private ArrayList<String> femaleAncestorsEventTypes;
    private ArrayList<String> maleAncestorsEventTypes;
    private ArrayList<String> allEventTypes;
    private Map<String, ArrayList<Person>> childrenMap;
    private ArrayList<Float> colorValues;

    public static FamilyTree getInstance() {
        if (instance == null) {
            instance = new FamilyTree();
        }

        return instance;
    }

    private FamilyTree() {
        loggedInUser = new Person();
        loggedIn = false;
        authToken = null;
        paternalAncestors = new HashSet<>();
        maternalAncestors = new HashSet<>();
        peopleMap = new HashMap<>();
        eventMap = new HashMap<>();
        peoplesEventsMap = new HashMap<>();
        loggedInUserEventTypes = new ArrayList<>();
        femaleAncestorsEventTypes = new ArrayList<>();
        maleAncestorsEventTypes = new ArrayList<>();
        allEventTypes = new ArrayList<>();
        childrenMap = new HashMap<>();
        colorValues = new ArrayList<>();
    }

    public void setLoggedInUserPeople(Person[] loggedInUserPeople) {

        for (Person person : loggedInUserPeople) {
            peopleMap.put(person.getPersonID(), person);
        }
        separatePeople();
    }

    public void setLoggedInUserEvents(Event[] loggedInUserEvents) {

        for (Event event: loggedInUserEvents) {

            eventMap.put(event.getEventID(), event);
            makePeoplesEventMap(event);
        }
        /*makeLoggedInUserEventTypes();
        makeFemaleAncestorsEventTypes();
        makeMaleAncestorsEventTypes();*/
        makeAllEventsType();
        initializeColorTypes();
    }

    private void separatePeople() {

        Person usersMother = peopleMap.get(loggedInUser.getMotherID());
        Person usersFather = peopleMap.get(loggedInUser.getFatherID());
        determineSide(usersMother, true);
        determineSide(usersFather, false);
        groupChildren();
    }

    private void determineSide(Person person, boolean isMaternal) {

        if (isMaternal) {
            maternalAncestors.add(person);
        } else {
            paternalAncestors.add(person);
        }

        if (person.getFatherID() == null || person.getFatherID().equals("")) {
            return;
        } else {

            if (isMaternal) {
                Person personMother = peopleMap.get(person.getMotherID());
                Person personFather = peopleMap.get(person.getFatherID());
                determineSide(personMother, true);
                determineSide(personFather, true);

            } else {

                Person personMother = peopleMap.get(person.getMotherID());
                Person personFather = peopleMap.get(person.getFatherID());
                determineSide(personMother, false);
                determineSide(personFather, false);
            }
        }
    }

    private void groupChildren() {

        for (Map.Entry<String, Person> parentEntry : peopleMap.entrySet()) {

            String parentID = parentEntry.getKey();
            ArrayList<Person> children = new ArrayList<>();

            for (Map.Entry<String, Person> childEntry : peopleMap.entrySet()) {

                if (parentID.equals(childEntry.getValue().getFatherID()) || parentID.equals(childEntry.getValue().getMotherID())) {
                    children.add(childEntry.getValue());
                }
            }
            childrenMap.put(parentID, children);
        }
    }

    private void makePeoplesEventMap(Event eventToAdd) {

        if (peoplesEventsMap.containsKey(eventToAdd.getPersonID())) {

            ArrayList<Event> currentEventList = peoplesEventsMap.get(eventToAdd.getPersonID());

            if (eventToAdd.getYear() < currentEventList.get(0).getYear()) {
                currentEventList.add(0, eventToAdd);

            } else if (eventToAdd.getYear() > currentEventList.get(currentEventList.size() - 1).getYear()) {
                currentEventList.add(eventToAdd);

            } else {

                if (currentEventList.size() == 1) {

                    if (eventToAdd.getYear() > currentEventList.get(0).getYear()) {

                        currentEventList.add(eventToAdd);

                    } else {

                        currentEventList.add(0, eventToAdd);
                    }

                } else {

                    for (int i = 0; i < currentEventList.size(); i++) {

                        Event currentEvent = currentEventList.get(i);
                        if (eventToAdd.getYear() > currentEvent.getYear() && !(eventToAdd.getYear() > currentEventList.get(i + 1).getYear())
                                && !currentEventList.contains(eventToAdd)) {

                            currentEventList.add(i + 1, eventToAdd);

                        } else {

                            if (!currentEventList.contains(eventToAdd)) {

                                currentEventList.add(eventToAdd);
                            }
                        }
                    }
                }
            }

            peoplesEventsMap.put(eventToAdd.getPersonID(), currentEventList);

        } else {

            ArrayList<Event> eventList = new ArrayList<>();
            eventList.add(eventToAdd);
            peoplesEventsMap.put(eventToAdd.getPersonID(), eventList);
        }
    }

    private void makeAllEventsType() {

        for (Map.Entry<String, ArrayList<Event>> entry: peoplesEventsMap.entrySet()) {

            ArrayList<Event> currentEvents = entry.getValue();

            for (int i = 0; i < currentEvents.size(); i++) {

                if (!allEventTypes.contains(currentEvents.get(i).getEventType().toLowerCase())) {
                    allEventTypes.add(currentEvents.get(i).getEventType().toLowerCase());
                }
            }
        }
    }

    private void makeLoggedInUserEventTypes() {

        ArrayList<Event> loggedInUserEvents = peoplesEventsMap.get(loggedInUser.getPersonID());
        for (int i = 0; i < loggedInUserEvents.size(); i++) {
            loggedInUserEventTypes.add(loggedInUserEvents.get(i).getEventType().toLowerCase());
        }
        allEventTypes.addAll(loggedInUserEventTypes);
    }

    private void makeFemaleAncestorsEventTypes() {

        ArrayList<Event> usersMotherEvents = peoplesEventsMap.get(loggedInUser.getMotherID());
        for (int i = 0; i < usersMotherEvents.size(); i++) {
            femaleAncestorsEventTypes.add(usersMotherEvents.get(i).getEventType().toLowerCase());
        }

        for (String eventType : femaleAncestorsEventTypes) {

            if (!allEventTypes.contains(eventType)) {
                allEventTypes.add(eventType);
            }
        }
    }

    private void makeMaleAncestorsEventTypes() {

        ArrayList<Event> usersFatherEvents = peoplesEventsMap.get(loggedInUser.getFatherID());
        for (int i = 0; i < usersFatherEvents.size(); i++) {
            maleAncestorsEventTypes.add(usersFatherEvents.get(i).getEventType().toLowerCase());
        }
        for (String eventType : maleAncestorsEventTypes) {

            if (!allEventTypes.contains(eventType)) {
                allEventTypes.add(eventType);
            }
        }
    }

    public Person getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Person loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Set<Person> getPaternalAncestors() {
        return paternalAncestors;
    }

    public void setPaternalAncestors(Set<Person> paternalAncestors) {
        this.paternalAncestors = paternalAncestors;
    }

    public Set<Person> getMaternalAncestors() {
        return maternalAncestors;
    }

    public void setMaternalAncestors(Set<Person> maternalAncestors) {
        this.maternalAncestors = maternalAncestors;
    }

    public Map<String, Person> getPeopleMap() {
        return peopleMap;
    }

    public void setPeopleMap(Map<String, Person> peopleMap) {
        this.peopleMap = peopleMap;
    }

    public Map<String, Event> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, Event> eventMap) {
        this.eventMap = eventMap;
    }

    public Map<String, ArrayList<Event>> getPeoplesEventsMap() {
        return peoplesEventsMap;
    }

    public void setPeoplesEventsMap(Map<String, ArrayList<Event>> peoplesEventsMap) {
        this.peoplesEventsMap = peoplesEventsMap;
    }

    public ArrayList<String> getLoggedInUserEventTypes() {
        return loggedInUserEventTypes;
    }

    public void setLoggedInUserEventTypes(ArrayList<String> loggedInUserEventTypes) {
        this.loggedInUserEventTypes = loggedInUserEventTypes;
    }

    public ArrayList<String> getFemaleAncestorsEventTypes() {
        return femaleAncestorsEventTypes;
    }

    public void setFemaleAncestorsEventTypes(ArrayList<String> femaleAncestorsEventTypes) {
        this.femaleAncestorsEventTypes = femaleAncestorsEventTypes;
    }

    public ArrayList<String> getMaleAncestorsEventTypes() {
        return maleAncestorsEventTypes;
    }

    public void setMaleAncestorsEventTypes(ArrayList<String> maleAncestorsEventTypes) {
        this.maleAncestorsEventTypes = maleAncestorsEventTypes;
    }

    public Map<String, ArrayList<Person>> getChildrenMap() {
        return childrenMap;
    }

    public void setChildrenMap(Map<String, ArrayList<Person>> childrenMap) {
        this.childrenMap = childrenMap;
    }

    private void initializeColorTypes() {

        colorValues.add(BitmapDescriptorFactory.HUE_AZURE);
        colorValues.add(BitmapDescriptorFactory.HUE_BLUE);
        colorValues.add(BitmapDescriptorFactory.HUE_ORANGE);
        colorValues.add(BitmapDescriptorFactory.HUE_ROSE);
        colorValues.add(BitmapDescriptorFactory.HUE_CYAN);
        colorValues.add(BitmapDescriptorFactory.HUE_GREEN);
        colorValues.add(BitmapDescriptorFactory.HUE_MAGENTA);
        colorValues.add(BitmapDescriptorFactory.HUE_RED);
    }

    public Float getColorByIndex(int index) {
        return colorValues.get(index);
    }

    public ArrayList<String> getAllEventTypes() {
        return allEventTypes;
    }

    public void setAllEventTypes(ArrayList<String> allEventTypes) {
        this.allEventTypes = allEventTypes;
    }

    public ArrayList<Person> getPeople() {
        ArrayList<Person> people = new ArrayList<>();

        for (Map.Entry<String, Person> entry : peopleMap.entrySet()) {
            people.add(entry.getValue());
        }

        return people;
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();

        for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
            events.add(entry.getValue());
        }

        return events;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Set<Person> getParentSet(Person person) {

        Set<Person> parentSet = new HashSet<>();
        Person mother = peopleMap.get(person.getMotherID());
        Person father = peopleMap.get(person.getFatherID());

        parentSet.add(mother);
        parentSet.add(father);

        return parentSet;
    }
}
