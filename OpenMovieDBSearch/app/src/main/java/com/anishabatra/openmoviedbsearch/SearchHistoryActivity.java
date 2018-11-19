package com.anishabatra.openmoviedbsearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SearchHistoryActivity extends AppCompatActivity {

    SQLiteDatabase mydatabase;

    private void showHistory() {
        Cursor resultSet = mydatabase.rawQuery("Select * from Titles",null);
        resultSet.moveToFirst();

        while (resultSet.isAfterLast() == false) {
            String movieName = resultSet.getString(resultSet.getColumnIndex("Name"));
            Log.i("DB MovieTitle ", movieName);
            resultSet.moveToNext();
        }
    }

    public void btnBackToSearch_Click(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        mydatabase = openOrCreateDatabase("Movie",MODE_PRIVATE,null);

        showHistory();
    }
}
