package com.marinoo.familymap.communication;

import android.util.Log;

import com.marinoo.familymap.req.LoginReq;
import com.marinoo.familymap.req.RegisterReq;
import com.marinoo.familymap.res.EventRes;
import com.marinoo.familymap.res.LoginRes;
import com.marinoo.familymap.res.PersonRes;
import com.marinoo.familymap.res.RegisterRes;
import com.marinoo.familymap.serializer.Decoder;
import com.marinoo.familymap.serializer.Encoder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServerProxy {

    public LoginRes login(URL webUrl, LoginReq loginReq) {

        try {
            HttpURLConnection webProxyConnection = (HttpURLConnection) webUrl.openConnection();
            webProxyConnection.setRequestMethod("POST");
            webProxyConnection.addRequestProperty("Content-Type", "application/json");
            webProxyConnection.setDoOutput(true);
            String json = Encoder.serialize(loginReq);
            OutputStream reqBody = webProxyConnection.getOutputStream();
            writeRequest(json, reqBody);
            webProxyConnection.connect();

            if (webProxyConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(webProxyConnection.getInputStream());
                LoginRes response;
                response = Decoder.deserialize(reader, LoginRes.class);
                reader.close();
                return response;
            } else {
                LoginRes response = new LoginRes();
                response.setSuccess(false);
                response.setMessage(webProxyConnection.getResponseMessage());
                return response;
            }

        } catch (Exception e) {
            Log.e("ClientRequest", e.getMessage(), e);
        }
        return null;
    }

    public RegisterRes register(URL webUrl, RegisterReq registerReq) {

        try {
            HttpURLConnection webProxyConnection = (HttpURLConnection) webUrl.openConnection();
            webProxyConnection.setRequestMethod("POST");
            webProxyConnection.addRequestProperty("Content-Type", "application/json");
            webProxyConnection.setDoOutput(true);
            String json = Encoder.serialize(registerReq);
            OutputStream reqBody = webProxyConnection.getOutputStream();
            writeRequest(json, reqBody);
            webProxyConnection.connect();

            if (webProxyConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(webProxyConnection.getInputStream());
                RegisterRes response;
                response = Decoder.deserialize(reader, RegisterRes.class);
                reader.close();
                return response;
            } else {
                RegisterRes response = new RegisterRes();
                response.setSuccess(false);
                response.setMessage(webProxyConnection.getResponseMessage());
                return response;
            }

        } catch (Exception e) {
            Log.e("ClientRequest", e.getMessage(), e);
        }
        return null;
    }

    public PersonRes person(URL webUrl, String authToken) {
        PersonRes response;

        try {
            HttpURLConnection webProxyConnection = (HttpURLConnection) webUrl.openConnection();
            webProxyConnection.setRequestMethod("GET");
            webProxyConnection.addRequestProperty("Authorization", authToken);
            webProxyConnection.addRequestProperty("Content-Type", "application/json");
            webProxyConnection.setDoOutput(false);
            webProxyConnection.connect();

            if (webProxyConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(webProxyConnection.getInputStream());
                response = Decoder.deserialize(reader, PersonRes.class);
                reader.close();
                return response;

            } else {
                response = new PersonRes();
                response.setSuccess(false);
                response.setMessage(webProxyConnection.getResponseMessage());
                return response;
            }

        } catch (Exception e) {
            Log.e("ClientRequest", e.getMessage(), e);
        }

        return null;
    }

    public PersonRes allPerson(URL webUrl, String authToken) {
        PersonRes response;

        try {
            HttpURLConnection webProxyConnection = (HttpURLConnection) webUrl.openConnection();
            webProxyConnection.setRequestMethod("GET");
            webProxyConnection.addRequestProperty("Authorization", authToken);
            webProxyConnection.addRequestProperty("Content-Type", "application/json");
            webProxyConnection.setDoOutput(false);
            webProxyConnection.connect();

            if (webProxyConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(webProxyConnection.getInputStream());
                response = Decoder.deserialize(reader, PersonRes.class);
                reader.close();
                return response;

            } else {
                response = new PersonRes();
                response.setSuccess(false);
                response.setMessage(webProxyConnection.getResponseMessage());
                return response;
            }

        } catch (Exception e) {
            Log.e("ClientRequest", e.getMessage(), e);
        }

        return null;
    }

    public EventRes allEvents(URL webUrl, String authToken) {
        EventRes response;

        try {
            HttpURLConnection webProxyConnection = (HttpURLConnection) webUrl.openConnection();
            webProxyConnection.setRequestMethod("GET");
            webProxyConnection.addRequestProperty("Authorization", authToken);
            webProxyConnection.addRequestProperty("Content-Type", "application/json");
            webProxyConnection.setDoOutput(false);
            webProxyConnection.connect();

            if (webProxyConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(webProxyConnection.getInputStream());
                response = Decoder.deserialize(reader, EventRes.class);
                reader.close();
                return response;

            } else {
                response = new EventRes();
                response.setSuccess(false);
                response.setMessage(webProxyConnection.getResponseMessage());
                return response;
            }

        } catch (Exception e) {
            Log.e("ClientRequest", e.getMessage(), e);
        }

        return null;
    }

    private void writeRequest(String json, OutputStream os) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(os);
        writer.write(json);
        writer.flush();
    }
}
