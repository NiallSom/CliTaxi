package com.ise.taxiapp.cli;

import com.ise.taxiapp.entities.Driver;
import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;
import com.ise.taxiapp.entities.User;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;
import com.ise.taxiapp.nav.Region;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static com.ise.taxiapp.cli.Util.*;

public class CliDriver {
    private User user;
    private Scanner scanner;
    private Region region;

    public static void main(String[] args) throws InterruptedException, IOException {
        new CliDriver().run();
    }

    /**
     * Inits the region and user, and runs the program loop
     */
    public void run() throws InterruptedException, IOException {

        scanner = new Scanner(System.in);
        clearScreen();
        System.out.println("Welcome to taxi app!");
        System.out.print("Please enter your username: ");
        String username = scanner.next();
        user = new User(username);
        user.setCurrentLocation(new Point(5, 5));
        region = initRegion();
        populateRegionWithTaxis();
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
     * This method will populate the taxis in region linked list with random taxi data stored in taxiData.csv
     */
    public void populateRegionWithTaxis() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/ise/taxiapp/taxiData.csv"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] lines = line.split(",");
            Taxi taxi = new Taxi(lines[1], new Driver(lines[0], lines[2]), Fare.valueOf(lines[4]));
            taxi.setLocation(
                    Point.fromIndex(
                            Integer.parseInt(lines[5]),
                            ((Grid) region).getWidth())
            );
            region.insertTaxi(taxi);
        }
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
        int radius = 10; // keep at 1 or a low number, increment by 1 if not found - implement in different branch
        while ((taxi = region.callTaxi(user.getCurrentLocation(), fare, radius)) == null) {
            System.out.println("Searching for a taxi...");
            //noinspection BusyWait
            // Add user to the queue - observer design pattern implementation
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

        int rating = promptInput("How would you rate your ride 0-5?", 5, scanner); // feel as if this should be in another method
        taxi.getDriver().rate(rating);
        taxi.markAsAvailable();
    }

    public Region initRegion() {
        return new Grid(10, 10);
    }
}
