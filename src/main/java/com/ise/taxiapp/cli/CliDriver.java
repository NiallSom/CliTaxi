package com.ise.taxiapp.cli;

import com.ise.taxiapp.entities.Driver;
import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;
import com.ise.taxiapp.entities.User;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Region;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Point;

import java.util.Scanner;

import static com.ise.taxiapp.cli.Util.*;

public class CliDriver {
    private User user;
    private Scanner scanner;
    private Region region;

    public static void main(String[] args) throws InterruptedException {
        new CliDriver().run();
    }

    /**
     * Inits the region and user, and runs the program loop
     */
    public void run() throws InterruptedException {
        scanner = new Scanner(System.in);
        clearScreen();
        System.out.println("Welcome to taxi app!");
        System.out.print("Please enter your username: ");
        String username = scanner.next();
        user = new User(username);
        user.setCurrentLocation(new Point(5, 5));
        region = initRegion();
        Taxi taxi = new Taxi("123", new Driver("John", "456"), Fare.STANDARD_FARE);
        taxi.setLocation(new Point(0, 0));
        region.insertTaxi(taxi);

        String continuePrompt = """
                Would you like to book a taxi?
                (0) No
                (1) Yes""";
        // Loop until the user no longer wishes to book a taxi
        while (promptInput(continuePrompt, 1, scanner) == 1) {
            Location destination = chooseDestination();
            Fare fare = chooseFare();
            callTaxi(destination, fare);
        }
        displayText("""
                Thanks for using TaxiApp
                Be sure to leave a rating!""");
    }

    /**
     * Prompts the user to choose a destination to travel to.
     *
     * @return The location to travel to
     */
    public Location chooseDestination() {
        int destinationIndex = promptInput("""
                Where to?
                Enter a grid index from 0-99:""", 99, scanner);
        return Point.fromIndex(destinationIndex, ((Grid) region).getWidth());
    }

    /**
     * Prompts the user to select what type of taxi they want.
     *
     * @return The Fare the user wants to travel with
     */
    public Fare chooseFare() {
        String farePrompt = """
                What type of ride are you looking for?
                (0) Standard
                (1) Express
                (2) XL""";
        return switch (promptInput(farePrompt, 2, scanner)) {
            case 0 -> Fare.STANDARD_FARE;
            case 1 -> Fare.EXPRESS_FARE;
            case 2 -> Fare.EXTRA_LARGE_FARE;
            default -> null; // unreachable, promptInput only allows valid inputs
        };
    }

    /**
     * Calls a taxi.
     * Once the taxi arrives, it will take the user to their chosen destination
     * and charge their bank account.
     *
     * @param destination The destination to travel to
     * @param fare        The fare to charge the user
     */
    public void callTaxi(Location destination, Fare fare) throws InterruptedException {
        clearScreen();
        Taxi taxi;
        // Keep looping until a taxi is available within 10km of the user
        int radius = 10;
        while ((taxi = region.callTaxi(user.getCurrentLocation(), fare, radius)) == null) {
            System.out.println("Searching for a taxi...");
            //noinspection BusyWait
            Thread.sleep(3000);
        }

        taxi.setDestination(user.getCurrentLocation());
        clearScreen();
        System.out.printf("""
                Taxi found!
                Driver %s is on the way.
                He should arrive in %d minutes%n""", taxi.getDriver(), taxi.timeToDestination());
        Thread.sleep(taxi.timeToDestination() * 1000);
        taxi.driveToDestination();

        displayText("Taxi has arrived! Hop in!");
        taxi.setUser(user);
        taxi.setDestination(destination);
        clearScreen();
        System.out.printf("""
                Driving to your destination: %s
                We should be there in %d mins%n""", destination, taxi.timeToDestination());
        Thread.sleep(taxi.timeToDestination() * 1000);
        taxi.driveToDestination();

        double charge = taxi.calculateCharge();
        user.charge(charge);
        displayText("""
                We have arrived!
                Total charge: %.2f.
                This has been charged to your account.
                New account balance: %s.%n""", charge, user.getBalance());

        int rating = promptInput("How would you rate your ride 0-5?", 5, scanner);
        taxi.getDriver().rate(rating);
        taxi.markAsAvailable();
    }

    public Region initRegion() {
        return new Grid(10, 10);
    }
}
