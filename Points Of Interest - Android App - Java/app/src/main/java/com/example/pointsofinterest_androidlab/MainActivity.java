package com.example.pointsofinterest_androidlab;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView poiList = findViewById(R.id.poiList);




        ArrayList<PointsOfInterest> pointsOfInterest = DataSourse.getPointsOfInterest();

        ArrayAdapter<PointsOfInterest> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pointsOfInterest);

        poiList.setAdapter(adapter);

        poiList.setOnItemClickListener((parent, view, position, id) -> {
            PointsOfInterest poi = pointsOfInterest.get(position);
            double latitude = poi.getLatitude();
            double longitude = poi.getLongitude();

            String name = poi.getName();
            String description = poi.getDescription();

            Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);

//            mapIntent.putExtra("userLatitude", userLatitude);
//            mapIntent.putExtra("userLongitude", userLongitude);

            mapIntent.putExtra("name", name);
            mapIntent.putExtra("description", description);
            mapIntent.putExtra("latitude", latitude);
            mapIntent.putExtra("longitude", longitude);

            startActivity(mapIntent);



        });

    }
}