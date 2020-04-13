package com.marinoo.familymap.communication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.req.RegisterReq;
import com.marinoo.familymap.res.RegisterRes;

import java.net.MalformedURLException;
import java.net.URL;

public class RegisterTask extends AsyncTask<RegisterReq, Void, RegisterRes> {

    private RegisterReq request;
    private RegisterRes response;
    private Fragment registerFragment;
    private Context mainActivity;

    public RegisterTask(Fragment fragment, Context in) {
        this.registerFragment = fragment;
        this.mainActivity = in;
    }

    @Override
    protected RegisterRes doInBackground(RegisterReq... registerReqs) {

        request = registerReqs[0];

        try {
            ServerProxy proxy = new ServerProxy();
            URL registerUrl = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
            response = proxy.register(registerUrl, request);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Malformed URL");
        }
        return response;
    }

    @Override
    protected void onPostExecute(RegisterRes registerRes) {

        if (registerRes.isSuccess()) {
            FamilyTree.getInstance().setAuthToken(registerRes.getAuthToken());
            AllPersonTask allPersonTask = new AllPersonTask(mainActivity, registerFragment, request, registerRes);
            allPersonTask.execute();
        } else {
            Toast.makeText(registerFragment.getContext(), "Register Failed" + "\n" +
                    "Username taken/wrong password!", Toast.LENGTH_LONG).show();
        }
    }

}
