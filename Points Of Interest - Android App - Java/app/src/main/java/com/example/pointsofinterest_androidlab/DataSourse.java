package com.example.pointsofinterest_androidlab;

import java.util.ArrayList;

public class DataSourse {

    public static ArrayList<PointsOfInterest> getPointsOfInterest() {
        ArrayList<PointsOfInterest> pointsOfInterest = new ArrayList<>();
        pointsOfInterest.add(new PointsOfInterest("White Tower", "The most commonly known monument in Thessaloniki", 22.94840381679073, 40.6265004727695));
        pointsOfInterest.add(new PointsOfInterest("Aristotelous Square", "The main city square of Thessaloniki", 22.952859474679055, 40.63271316090153));
        pointsOfInterest.add(new PointsOfInterest("Rotunda of Galerius", "A massive round building in the city center",22.951763226754775 , 40.63339156143344));
        pointsOfInterest.add(new PointsOfInterest("Arch of Galerius", "A 4th-century monument in the city of Thessaloniki", 22.951763226754775, 40.63226599902461));
        pointsOfInterest.add(new PointsOfInterest("Eptapirgio", "The old town of Thessaloniki", 22.961801468617196, 40.644704997947436));
        return pointsOfInterest;
    }

}
