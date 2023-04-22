package com.findworker.findworker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button searchButton;

    String[] locationItems = {"Oulu", "Helsinki", "Tampere"};
    String[] workCategories = {"ikkunanpesu", "pihatyöt", "imuroonti"};

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

        //testaus
    }

    private void makeSearch() {
        TextView results = findViewById(R.id.results);

        String url = "http://localhost:8000/backend/search/";

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
        requestQueue.add(jsonObjectRequest);



    }



    //morocdd
}