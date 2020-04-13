package com.marinoo.familymap.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.model.Person;
import com.marinoo.familymap.req.LoginReq;
import com.marinoo.familymap.req.RegisterReq;
import com.marinoo.familymap.res.EventRes;
import com.marinoo.familymap.res.LoginRes;
import com.marinoo.familymap.res.RegisterRes;
import com.marinoo.familymap.ui.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class AllEventsTask extends AsyncTask<Void, Void, EventRes> {

    private Person user;
    private String serverHost;
    private String serverPort;
    private String authToken;
    private EventRes response;
    private Fragment fragment;
    private Context mainActivity;

    public AllEventsTask(Person user, Context in, Fragment fragment, String reqHost, String reqPort, String authToken) {
        this.user = user;
        this.serverHost = reqHost;
        this.serverPort = reqPort;
        this.authToken = authToken;
        this.fragment = fragment;
        this.mainActivity = in;
    }


    @Override
    protected EventRes doInBackground(Void... voids) {

        try {
            ServerProxy proxy = new ServerProxy();
            URL allEventsUrl = new URL("http://" + serverHost + ":" + serverPort + "/event");
            response = proxy.allEvents(allEventsUrl, authToken);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Malformed URL");
        }

        return response;
    }

    @Override
    protected void onPostExecute(EventRes eventRes) {

        if (eventRes.isSuccess()) {
            FamilyTree familyTree = FamilyTree.getInstance();
            familyTree.setLoggedInUserEvents(eventRes.getData());

            Toast.makeText(fragment.getContext(), "Welcome " +
                    user.getFirstName() + " " + user.getLastName() + "!", Toast.LENGTH_SHORT).show();

            MainActivity main = (MainActivity) mainActivity;
            main.onLoginSuccess();

        } else {
            Toast.makeText(fragment.getContext() , "Failed to retrieve data!", Toast.LENGTH_SHORT).show();
        }
    }
}
