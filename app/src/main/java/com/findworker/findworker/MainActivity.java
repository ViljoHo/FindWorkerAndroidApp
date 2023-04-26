package com.findworker.findworker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button searchButton;
    private ListView resultsView;

    BottomNavigationView bottomNavigationView;
    String[] locationItems = {"Oulu", "Helsinki", "Tampere"};
    String[] workCategories = {"Ikkunanpesu", "Pihatyöt", "Imurointi"};

    String[] sort = {"Hinta laskeva", "Hinta nouseva", "Paras arvostelu"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeSearch();
            }
        });

        autoCompleteTextView = findViewById(R.id.autoCompTextloc);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item, locationItems);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView = findViewById(R.id.autoCompTextwc);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item, workCategories);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView = findViewById(R.id.autoCompTextsort);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item, sort);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this,"item " + locationItem, Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navHome);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navHome:
                        return true;

                    case R.id.navProfile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navAdd:
                        startActivity(new Intent(getApplicationContext(),AddActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private void makeSearch() {

        /*TextView results = (TextView) findViewById(R.id.results);

        String url = "http://10.0.2.2:8000/backend/search/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                results.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                results.setText("error");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);*/

        final TextView textView = (TextView) findViewById(R.id.results);
        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8000/backend/search/";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try {
                            showResults(response);
                            textView.setText(response.get(0).toString());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);


    }

    private void showResults(JSONArray response) throws JSONException {
        resultsView = (ListView) findViewById(R.id.resultsview);

        ArrayList<Object> results = new ArrayList<Object>();

        //Convert JSONArray to Arraylist

        if (response != null) {
            for (int i=0; i<response.length(); i++) {
                results.add(response.get(i));
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, results);

        resultsView.setAdapter(arrayAdapter);



    }

}