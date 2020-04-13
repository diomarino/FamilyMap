package com.marinoo.familymap.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.req.LoginReq;
import com.marinoo.familymap.res.LoginRes;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginTask extends AsyncTask<LoginReq, Void, LoginRes> {

    private LoginRes response;
    private LoginReq request;
    private Fragment loginFragment;
    private Context mainActivity;

    public LoginTask(Fragment fragment, Context in) {
        this.loginFragment = fragment;
        this.mainActivity = in;
    }

    @Override
    protected LoginRes doInBackground(LoginReq... loginReqs) {

        request = loginReqs[0];

        try {
            ServerProxy proxy = new ServerProxy();
            URL loginUrl = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/login");
            response = proxy.login(loginUrl, request);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Malformed URL");
        }

        return response;
    }

    @Override
    protected void onPostExecute(LoginRes loginRes) {

        if (loginRes.isSuccess()) {
            FamilyTree.getInstance().setAuthToken(loginRes.getAuthToken());
            AllPersonTask allPersonTask = new AllPersonTask(mainActivity, loginFragment, request, loginRes);
            allPersonTask.execute();
        } else {
            Toast.makeText(loginFragment.getContext(), "Login failed!" + "\n" + "Credentials are invalid",
                    Toast.LENGTH_LONG).show();
        }
    }

}
