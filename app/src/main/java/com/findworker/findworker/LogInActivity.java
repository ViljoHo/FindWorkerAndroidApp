package com.findworker.findworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {

    public interface IsRegisteredCallBack {
        void onSuccess(Boolean b);
        void onError(String error);
    }

    private Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInButton = findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainAppActivity(view);
            }
        });
    }

    public void goToMainAppActivity(View view) {
        //Getting users input

        EditText emailInput = (EditText)findViewById(R.id.editTextTextPersonName);
        EditText passwordInput = (EditText)findViewById(R.id.editTextTextPassword);

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();


        final boolean[] isRegistered = new boolean[1];
        checkUser(email, password, new IsRegisteredCallBack() {
            @Override
            public void onSuccess(Boolean b) {
                isRegistered[0] = b;

                if(isRegistered[0]) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);

                    startActivity(intent);

                }
                else {

                    // inflate the layout of the popup window
                    LayoutInflater layoutInflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_window, null);

                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 20);

                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onError(String error) {
                System.out.println(error);

            }
        });


    }

    private void checkUser(String username, String password, IsRegisteredCallBack callBack) {


        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://findworkerbackend.herokuapp.com/backend/login/";

        HashMap<String, String> paramsHasMap = new HashMap<String, String>();

        //creating params json object
        paramsHasMap.put("username", username);
        paramsHasMap.put("password", password);

        JSONObject params =  new JSONObject(paramsHasMap);


        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        try {
                            if (response.has("logged_in")) {
                                if (response.getBoolean("logged_in")){
                                    callBack.onSuccess(true);
                                }
                            }



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onSuccess(false);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }




}