package com.anishabatra.openmoviedbsearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText editTextMovieName;
    String movieName = "";
    SQLiteDatabase mydatabase;

    public void btnSearch_Click(View view) {
        movieName = editTextMovieName.getText().toString();
        Log.i("Movie name", movieName);

        if(movieName.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Movie Name is empty");
            builder.setTitle("Error");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            builder.create();
            builder.show();
        } else {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://www.omdbapi.com/?i=tt3896198&apikey=9e288e47&t=" + movieName;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //editTextMovieName.setText("Response is: "+ response.substring(0,500));
                            Log.i("JSON Response", response);
                            getMovieTitle(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("JSON Response", "That didn't work");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    public void getMovieTitle(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String movieTitle = jsonObject.getString("Title");
            Log.i("Response MovieTitle", movieTitle);
            saveToDataBase(movieTitle);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void saveToDataBase(String title) {
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Titles(Name VARCHAR);");
        mydatabase.execSQL("INSERT INTO Titles VALUES('" + title + "');");
    }

    public void btnShowHistory_Click(View view) {
        Cursor resultSet = mydatabase.rawQuery("Select * from Titles",null);
        resultSet.moveToFirst();

        while (resultSet.isAfterLast() == false) {
            String movieName = resultSet.getString(resultSet.getColumnIndex("Name"));
            Log.i("DB MovieTitle ", movieName);
            resultSet.moveToNext();
        }

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovieName = findViewById(R.id.editTextMovieName);
        mydatabase = openOrCreateDatabase("Movie",MODE_PRIVATE,null);
    }
}
