package com.marinoo.familymap.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;

import com.marinoo.familymap.R;
import com.marinoo.familymap.communication.LoginTask;
import com.marinoo.familymap.communication.RegisterTask;
import com.marinoo.familymap.req.LoginReq;
import com.marinoo.familymap.req.RegisterReq;

public class LoginFragment extends Fragment {
    private LoginReq loginReq;
    private RegisterReq registerReq;
    private EditText reqServerHost;
    private EditText reqServerPort;
    private EditText reqUsername;
    private EditText reqPassword;
    private EditText reqFirstName;
    private EditText reqLastName;
    private EditText reqEmail;
    private RadioGroup reqGender;
    private Button reqLogin;
    private Button reqRegister;
    private Context mainActivity;

    /**
     * Default constructor
     */
    public LoginFragment(Context in) {
        this.mainActivity = in;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginReq = new LoginReq();
        registerReq = new RegisterReq();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, parent, false);

        reqRegister = (Button) v.findViewById(R.id.registerButton);
        reqRegister.setEnabled(false);
        reqLogin = (Button) v.findViewById(R.id.loginButton);
        reqLogin.setEnabled(false);

        reqServerHost = (EditText) v.findViewById(R.id.serverHost);
        reqServerHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginReq.setServerHost(s.toString());
                registerReq.setServerHost(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLoginButton();
                validateRegisterButton();
            }
        });

        reqServerPort = (EditText) v.findViewById(R.id.serverPort);
        reqServerPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginReq.setServerPort(s.toString());
                registerReq.setServerPort(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLoginButton();
                validateRegisterButton();
            }
        });

        reqUsername = (EditText) v.findViewById(R.id.username);
        reqUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginReq.setUserName(s.toString());
                registerReq.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLoginButton();
                validateRegisterButton();
            }
        });

        reqPassword = (EditText) v.findViewById(R.id.password);
        reqPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginReq.setPassword(s.toString());
                registerReq.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLoginButton();
                validateRegisterButton();
            }
        });

        reqFirstName = (EditText) v.findViewById(R.id.firstName);
        reqFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerReq.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateRegisterButton();
            }
        });

        reqLastName = (EditText) v.findViewById(R.id.lastName);
        reqLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerReq.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateRegisterButton();
            }
        });

        reqEmail = v.findViewById(R.id.email);
        reqEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerReq.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateRegisterButton();
            }
        });

        registerReq.setGender("m");
        reqGender = v.findViewById(R.id.genderGroup);
        reqGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.maleButton) {
                    registerReq.setGender("m");
                } else {
                    registerReq.setGender("f");
                }

                validateRegisterButton();
            }
        });

        reqLogin = (Button) v.findViewById(R.id.loginButton);
        reqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOnClick();
            }
        });

        reqRegister = (Button) v.findViewById(R.id.registerButton);
        reqRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerOnClick();
            }
        });

        return v;
    }

    /**
     * Check if req is login by checking empty edit text fields
     * If not empty set to true
     */
    private void validateLoginButton() {
        String currentServerHost = reqServerHost.getText().toString();
        String currentServerPort = reqServerPort.getText().toString();
        String currentUsername = reqUsername.getText().toString();
        String currentPassword = reqPassword.getText().toString();

        if (currentServerHost.equals("") || currentServerPort.equals("") || currentUsername.equals("") ||
                currentPassword.equals("")) {
            reqLogin.setEnabled(false);
        } else {
            reqLogin.setEnabled(true);
        }
    }

    /**
     * Check if req is register by checking the values of edit text fields
     * If not empty set to true
     */
    private void validateRegisterButton() {
        String currentServerHost = reqServerHost.getText().toString();
        String currentServerPort = reqServerPort.getText().toString();
        String currentUsername = reqUsername.getText().toString();
        String currentPassword = reqPassword.getText().toString();
        String currentFirstName = reqFirstName.getText().toString();
        String currentLastName = reqLastName.getText().toString();
        String currentEmail = reqEmail.getText().toString();

        if (currentServerHost.equals("") || currentServerPort.equals("") || currentUsername.equals("") ||
                currentPassword.equals("") || currentFirstName.equals("") || currentLastName.equals("")
        || currentEmail.equals("")) {
            reqRegister.setEnabled(false);
        } else {
            reqRegister.setEnabled(true);
        }
    }

    /**
     * Make login task and pass in the login req
     */
    private void loginOnClick() {
        LoginTask loginTask = new LoginTask(this, mainActivity);
        loginTask.execute(loginReq);
    }

    /**
     * Make register task and pass in the register req
     */
    private void registerOnClick() {
        RegisterTask registerTask = new RegisterTask(this, mainActivity);
        registerTask.execute(registerReq);
    }

}
