package com.ise.taxiapp.cli;

import com.ise.taxiapp.entities.*;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static com.ise.taxiapp.cli.AsciiColours.*;
import static com.ise.taxiapp.cli.Util.*;


/**
 * Handles all CLI graphics
 */
public class CliDriver {
    public static final String BOX = "■";
    public static final String USER_COlOUR = RED;
    public static final String DESTINATION_COLOUR = PURPLE;
    public static final String RATING_TEXT = """
            How would you rate your ride 0-5?
            (0)☆☆☆☆☆
            (1)★☆☆☆☆
            (2)★★☆☆☆
            (3)★★★☆☆
            (4)★★★★☆
            (5)★★★★★""";
    private final Random random = new Random();
    private User user;
    private Scanner scanner;
    private Grid grid;

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
        grid = initRegion();
        populateRegionWithTaxis();
        String continuePrompt = """
                Would you like to book a taxi?
                (0) No
                (1) Yes""";
        // Loop until the user no longer wishes to book a taxi
        while (promptInput(continuePrompt, 1, scanner) == 1) {
            // Send the user to a random location
            grid.setLocation(user, random.nextInt(grid.getWidth() * grid.getWidth()));
            Destination destination = chooseDestination();
            Fare fare = chooseFare();
            Taxi taxi = callTaxi(fare);
            driveToDestination(taxi, destination);
        }
        clearScreen();
        System.out.println("Thank you for using TaxiApp!");
    }

    /**
     * Prompts the user to choose a destination to travel to.
     *
     * @return The location to travel to
     */
    public Destination chooseDestination() {
        int destinationIndex = promptInput("""
                Where to?
                Enter a grid index from 0-99:""", 99, scanner);
        Destination destination = new Destination(Point.fromIndex(destinationIndex, grid.getWidth()));
        grid.setLocation(destination, (Point) destination.getLocation());
        return destination;
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
     * Calls a taxi, returning it once it has arrived
     *
     * @param fare The fare to charge the user
     * @return The taxi that has arrived to transport the user
     */
    @SuppressWarnings("BusyWait")
    public Taxi callTaxi(Fare fare) throws InterruptedException {
        Taxi taxi;
        // Keep looping until a taxi is available within <radius> km of the user
        int radius = 4;
        do {
            clearScreen();
            printGrid();
            System.out.println("Searching for a taxi...");
            // Move that taxis randomly once every frame
            grid.getTaxiList().forEach(grid::moveTaxiRandomly);
            Thread.sleep(1000);
        } while ((taxi = grid.callTaxi(user.getLocation(), fare, radius)) == null);

        // This taxi should not be considered when searching for a lift until this ride commences
        taxi.setStatus(TaxiStatus.EN_ROUTE);
        driveTo(taxi, user.getLocation());

        clearScreen();
        printGrid();
        System.out.println("Taxi has arrived! Hop in!");
        promptEnter();
        return taxi;
    }

    private void driveToDestination(Taxi taxi, Destination destination) throws InterruptedException {
        taxi.setStatus(TaxiStatus.BUSY);
        taxi.setUser(user);
        // Remove the user from the map
        driveTo(taxi, destination.getLocation());

        // Charge calculated based on distance travelled and fare applied
        double charge = taxi.calculateCharge();
        user.charge(charge);
        displayText("""
                We have arrived!
                Total charge: $%.2f.
                This has been charged to your account.
                New account balance: $%.2f.%n""", charge, user.getBalance());

        int rating = promptInput(RATING_TEXT, 5, scanner);
        taxi.getDriver().rate(rating);
        taxi.markAsAvailable();
    }

    /**
     * Initialises the map size to a width and height of 10
     *
     * @return New Grid on which taxis may be placed
     */
    public Grid initRegion() {
        return new Grid(10, 10);
    }

    /**
     * This method will populate the taxis in region linked list with random taxi data stored in taxiData.csv
     */
    public void populateRegionWithTaxis() throws IOException {
        InputStream stream = CliDriver.class.getClassLoader().getResourceAsStream("taxiData.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lines = line.split(",");
            Taxi taxi = new Taxi(lines[1], new Driver(lines[0], lines[2]), Fare.valueOf(lines[4]));
            grid.setLocation(taxi, Integer.parseInt(lines[5]));
            grid.insertTaxi(taxi);
        }
        reader.close();
    }

    /**
     * Places one taxi on the grid.
     */
    @SuppressWarnings("unused")
    private void populateWithSingleTaxi() {
        Taxi t = new Taxi("123", new Driver("John", "456"), Fare.STANDARD_FARE);
        grid.getTaxiList().add(t);
        grid.setLocation(t, 5, 6);
    }

    /**
     * Prints the grid. Taxis, users and destinations all have unique colours
     */
    public void printGrid() {
        System.out.printf("""
                    Key:
                    %s☒: User
                    %s☒: Standard Taxi
                    %s☒: Express Taxi
                    %s☒: XL Taxi
                """, USER_COlOUR, Fare.STANDARD_FARE.getColour(), Fare.EXPRESS_FARE.getColour(), Fare.EXTRA_LARGE_FARE.getColour());

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Point point = grid.get(x, y);
                String colour = calculateColour(point);
                System.out.printf("%s%s%s ", colour, BOX, RESET);
            }
            System.out.println(RESET);
        }
    }

    /**
     * Calculates the colour that should be displayed on a given point,
     * based on the contents of that point.
     * The colour is returned as a String in ASCII terminal representation,
     * e.g. BLACK = "u001B[30m"
     *
     * @param point The point to calculate the colour for.
     * @return ASCII colour value
     */
    private String calculateColour(Point point) {
        String colour = "";
        // In Java 21 this can be replaced by pattern matching
        for (Locatable l : point.getObjects()) {
            if (l instanceof User) {
                colour = USER_COlOUR;
                break;
            } else if (l instanceof Taxi taxi) {
                colour = taxi.getFare().getColour();
            } else if (l instanceof Destination && colour.isEmpty()) {
                colour = DESTINATION_COLOUR;
            }
            // If no colour is defined for the given object, do not colour it
        }
        if (colour.isEmpty()) {
            Point p = (Point) user.getLocation();
            colour = ((point.x() == p.x()) || (point.y() == p.y())) ? YELLOW : BLACK;
        }
        return colour;
    }

    /**
     * Moves the given taxi one square towards the given point.
     * The taxi will travel horizontally first, then vertically.
     *
     * @param taxi  The taxi to be moved
     * @param point The point to travel towards
     */
    public void driveTowards(Taxi taxi, Point point) {
        Point taxiPoint = (Point) taxi.getLocation();
        int newX = taxiPoint.x();
        int newY = taxiPoint.y();
        if (newX < point.x()) newX++;
        else if (newX > point.x()) newX--;
        else if (newY < point.y()) newY++;
        else if (newY > point.y()) newY--;
        grid.setLocation(taxi, newX, newY);
        if (taxi.getUser() != null) {
            grid.setLocation(taxi.getUser(), newX, newY);
        }
    }


    /**
     * Pathfinds the given taxi to the given point.
     * Uses {@link #driveTowards(Taxi, Point)} to move the taxi one point at a time.
     *
     * @param taxi     The taxi to be moved
     * @param location The point where the taxi will end up
     * @throws InterruptedException Throws if the loop is interrupted during the thread sleep cycle
     */
    @SuppressWarnings("BusyWait")
    private void driveTo(Taxi taxi, Location location) throws InterruptedException {
        taxi.setDestination(location);
        while (!taxi.getLocation().equals(location)) {
            grid.getTaxiList().forEach(grid::moveTaxiRandomly);
            clearScreen();
            driveTowards(taxi, (Point) location);
            printGrid();
            System.out.printf("""
                    Driver %s travelling to %s
                    They should arrive in %d minutes%n""", taxi.getDriver(), location, taxi.timeToDestination());
            Thread.sleep(1000);
        }
        taxi.driveToDestination();
    }
}