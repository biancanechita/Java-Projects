package com.company.cities;

public class DistanceCalculator {
    /**
     * @param o1 com.company.myWorld.City object
     * @param o2 com.company.myWorld.City object
     * @return the distance between two cities in the world
     */
    public double displayDistances(City o1, City o2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        double lon1 = Math.toRadians(o1.getLongitude());
        double lon2 = Math.toRadians(o2.getLongitude());
        double lat1 = Math.toRadians(o1.getLatitude());
        double lat2 = Math.toRadians(o2.getLatitude());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }
}
