package com.nitish;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class helps plan the best route for a delivery driver considering
 * multiple restaurants and consumers. It calculates the total delivery time
 * for two scenarios and recommends the faster route.
 */
public class DeliveryPlanner
{
    /**
     * The location of the delivery driver (e.g., Koramangala, Bengaluru).
     */
    private final Location amanLocation;

    /**
     * The location of the first restaurant.
     */
    private final Location restaurant1Location;

    /**
     * The location of the second restaurant.
     */
    private final Location restaurant2Location;

    /**
     * The location of the first consumer.
     */
    private final Location consumer1Location;

    /**
     * The location of the second consumer.
     */
    private final Location consumer2Location;

    /**
     * The average preparation time at the first restaurant (in minutes).
     */
    private final double preparationTime1;

    /**
     * The average preparation time at the second restaurant (in minutes).
     */
    private final double preparationTime2;

    /**
     * The average travel speed of the delivery driver (in kilometers per hour).
     */
    private final double travelSpeed;

    /**
     * Constructor to initialize the delivery planner with location and time information.
     *
     * @param amanLocation        The location of the delivery driver.
     * @param restaurant1Location The location of the first restaurant.
     * @param restaurant2Location The location of the second restaurant.
     * @param consumer1Location   The location of the first consumer.
     * @param consumer2Location   The location of the second consumer.
     * @param preparationTime1    The average preparation time at the first restaurant (in minutes).
     * @param preparationTime2    The average preparation time at the second restaurant (in minutes).
     * @param travelSpeed         The average travel speed of the delivery driver (in kilometers per hour).
     */
    public DeliveryPlanner(Location amanLocation, Location restaurant1Location, Location restaurant2Location,
                           Location consumer1Location, Location consumer2Location, double preparationTime1,
                           double preparationTime2, double travelSpeed) {
        this.amanLocation = amanLocation;
        this.restaurant1Location = restaurant1Location;
        this.restaurant2Location = restaurant2Location;
        this.consumer1Location = consumer1Location;
        this.consumer2Location = consumer2Location;
        this.preparationTime1 = preparationTime1;
        this.preparationTime2 = preparationTime2;
        this.travelSpeed = travelSpeed;
    }

    /**
     * Finds the best route for delivery by comparing the total time taken in two scenarios:
     * 1. Deliver to consumer 1 first, then consumer 2.
     * 2. Deliver to consumer 2 first, then consumer 1.
     *
     * @return An object representing the recommended route with total time and delivery order.
     */
    public Route findBestRoute()
    {
        double scenario1Time = calculateTotalTime(amanLocation, restaurant1Location, consumer1Location, restaurant2Location, consumer2Location);
        double scenario2Time = calculateTotalTime(amanLocation, restaurant2Location, consumer2Location, restaurant1Location, consumer1Location);

        return scenario1Time < scenario2Time ?
                new Route(scenario1Time, "C1 first, then C2") :
                new Route(scenario2Time, "C2 first, then C1");
    }

    /**
     * Calculates the total delivery time for a given sequence of locations.
     *
     * @param from          The starting location (e.g., delivery driver's location).
     * @param restaurant    The location of the restaurant.
     * @param consumer1     The location of the first consumer.
     * @param restaurant2   The location of the second restaurant (if applicable).
     * @param consumer2     The location of the second consumer (if applicable).
     * @return The total time taken to complete the delivery, including travel and waiting times.
     */
    private double calculateTotalTime(Location from, Location restaurant, Location consumer1, Location restaurant2, Location consumer2)
    {
        double travelToRestaurant = calculateDistance(from, restaurant) / travelSpeed;
        double travelToConsumer1 = calculateDistance(restaurant, consumer1) / travelSpeed;
        double travelToRestaurant2 = calculateDistance(consumer1, restaurant2) / travelSpeed;
        double travelToConsumer2 = calculateDistance(restaurant2, consumer2) / travelSpeed;

        return travelToRestaurant + preparationTime1 + travelToConsumer1 + travelToRestaurant2 + preparationTime2 + travelToConsumer2;
    }

    /**
     * Calculates the distance between two locations using the Haversine formula.
     *
     * @param loc1 The first location.
     * @param loc2 The second location.
     * @return The distance between the two locations in kilometers.
     */
    public static double calculateDistance(Location loc1, Location loc2)
    {
        final int earthRadius = 6371; // Earth radius in kilometers

        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return earthRadius * c;
    }


    /**
     * Represents a geographical location with latitude and longitude coordinates.
     */
    public static class Location
    {

        /**
         * The latitude of the location in decimal degrees.
         */
        private final double latitude;

        /**
         * The longitude of the location in decimal degrees.
         */
        private final double longitude;

        /**
         * Constructs a new Location object with the given latitude and longitude.
         *
         * @param latitude  The latitude in decimal degrees.
         * @param longitude The longitude in decimal degrees.
         */
        public Location(double latitude, double longitude)
        {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /**
         * Gets the latitude of the location.
         *
         * @return The latitude in decimal degrees.
         */
        public double getLatitude() {
            return latitude;
        }

        /**
         * Gets the longitude of the location.
         *
         * @return The longitude in decimal degrees.
         */
        public double getLongitude() {
            return longitude;
        }
    }

    /**
     * Represents a delivery route with total time and delivery order details.
     */
    public static class Route
    {

        /**
         * The total time taken for the delivery route in minutes.
         */
        private final double totalTime;

        /**
         * The order in which consumers are visited (e.g., "C1 first, then C2").
         */
        private final String deliveryOrder;

        /**
         * Constructs a new Route object with the given total time and delivery order.
         *
         * @param totalTime   The total time taken for the delivery in minutes.
         * @param deliveryOrder The order in which consumers are visited.
         */
        public Route(double totalTime, String deliveryOrder) {
            this.totalTime = totalTime;
            this.deliveryOrder = deliveryOrder;
        }

        /**
         * Gets the total time taken for the delivery route.
         *
         * @return The total time in minutes.
         */
        public double getTotalTime() {
            return totalTime;
        }

        /**
         * Gets the delivery order for the route.
         *
         * @return The delivery order string (e.g., "C1 first, then C2").
         */
        public String getDeliveryOrder() {
            return deliveryOrder;
        }
    }


    public static void main(String[] args)
    {
        Location amanLocation = new Location(12.9716, 77.5946);
        Location restaurant1Location = new Location(12.9343, 77.6214);
        Location restaurant2Location = new Location(12.9321, 77.6101);
        Location consumer1Location = new Location(12.9352, 77.6245);
        Location consumer2Location = new Location(12.9279, 77.6271);
        double preparationTime1 = 15; // minutes
        double preparationTime2 = 20; // minutes
        double travelSpeed = 20; // km/hr

        DeliveryPlanner planner = new DeliveryPlanner(amanLocation, restaurant1Location, restaurant2Location,
                consumer1Location, consumer2Location, preparationTime1, preparationTime2, travelSpeed);

        Route bestRoute = planner.findBestRoute();
        System.out.println("Best Route: " + bestRoute.getDeliveryOrder());
        System.out.println("Total Time: " + BigDecimal.valueOf(bestRoute.getTotalTime()).setScale(2, RoundingMode.HALF_UP) + " hours");
    }
}