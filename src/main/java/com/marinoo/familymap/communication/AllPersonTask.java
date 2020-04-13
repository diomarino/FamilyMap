package com.marinoo.familymap.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.model.Person;
import com.marinoo.familymap.req.LoginReq;
import com.marinoo.familymap.req.RegisterReq;
import com.marinoo.familymap.res.LoginRes;
import com.marinoo.familymap.res.PersonRes;
import com.marinoo.familymap.res.RegisterRes;

import java.net.MalformedURLException;
import java.net.URL;

public class AllPersonTask extends AsyncTask<Void, Void, PersonRes> {

    private String serverHost;
    private String serverPort;
    private String authToken;
    private PersonRes response;
    private Context mainActivity;
    private  Fragment fragment;


    public AllPersonTask(Context in, Fragment fragment, LoginReq req, LoginRes res) {
        this.serverHost = req.getServerHost();
        this.serverPort = req.getServerPort();
        this.authToken = res.getAuthToken();
        this.fragment = fragment;
        this.mainActivity = in;
    }

    public AllPersonTask(Context in, Fragment fragment, RegisterReq registerReq, RegisterRes registerRes) {
        this.serverHost = registerReq.getServerHost();
        this.serverPort = registerReq.getServerPort();
        this.authToken = registerRes.getAuthToken();
        this.fragment = fragment;
        this.mainActivity = in;
    }

    @Override
    protected PersonRes doInBackground(Void... voids) {

        try {
            ServerProxy proxy = new ServerProxy();
            URL allPersonUrl = new URL("http://" + serverHost + ":" + serverPort + "/person");
            response = proxy.allPerson(allPersonUrl, authToken);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Malformed URL");
        }

        return response;
    }

    @Override
    protected void onPostExecute(PersonRes personRes) {

        Person user = personRes.getData()[0];

        if (personRes.isSuccess()) {

            FamilyTree familyTree = FamilyTree.getInstance();
            familyTree.setLoggedInUser(user);
            familyTree.setLoggedInUserPeople(personRes.getData());

            AllEventsTask allEventsTask = new AllEventsTask(user, mainActivity, fragment, serverHost, serverPort, authToken);
            allEventsTask.execute();

        } else {
            Toast.makeText(fragment.getContext() , "Failed to retrieve data!", Toast.LENGTH_SHORT).show();
        }
    }
}
