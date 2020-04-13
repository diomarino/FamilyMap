package com.marinoo.familymap.req;


import com.marinoo.familymap.model.Event;
import com.marinoo.familymap.model.Person;
import com.marinoo.familymap.model.User;


/**
 * Make request to clear all data and load them.
 */
public class LoadReq {
    /**
     * Array of users to be created.
     */
    private User[] users;

    /**
     * Array of persons that has family history information.
     */
    private Person[] persons;

    /**
     * Array of events that has family history events.
     */
    private Event[] events;

    public LoadReq() {
        this.users = new User[200];
        this.persons = new Person[200];
        this.events = new Event[200];
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
