package com.ise.taxiapp.cli;

import com.ise.taxiapp.entities.*;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static com.ise.taxiapp.cli.AsciiColours.RED;
import static com.ise.taxiapp.cli.AsciiColours.RESET;
import static com.ise.taxiapp.cli.Util.*;

public class CliDriver {
    public static final String BOX = "☒";
    public static final String USER_COlOUR = RED;
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
        createSingleTaxi();
        String continuePrompt = """
                Would you like to book a taxi?
                (0) No
                (1) Yes""";
        // Loop until the user no longer wishes to book a taxi
        while (promptInput(continuePrompt, 1, scanner) == 1) {
            grid.setLocation(user, random.nextInt(grid.getWidth() * grid.getWidth()));
            Location destination = chooseDestination();
            Fare fare = chooseFare();
            callTaxi(destination, fare);
        }
        clearScreen();
        System.out.println("Thank you for rating TaxiApp!");
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
        return Point.fromIndex(destinationIndex, grid.getWidth());
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
    @SuppressWarnings("BusyWait")
    public void callTaxi(Location destination, Fare fare) throws InterruptedException {
        Taxi taxi;
        // Keep looping until a taxi is available within 10km of the user
        int radius = 4;
        do {
            clearScreen();
            printGrid();
            System.out.println("Searching for a taxi...");
            grid.getTaxiList().forEach(grid::moveTaxiRandomly);
            Thread.sleep(1000);
        } while ((taxi = grid.callTaxi(user.getLocation(), fare, radius)) == null);

        taxi.setStatus(TaxiStatus.EN_ROUTE);
        driveTo(taxi, user.getLocation());

        clearScreen();
        printGrid();
        System.out.println("Taxi has arrived! Hop in!");
        promptEnter();
        taxi.setStatus(TaxiStatus.BUSY);
        taxi.setUser(user);
        Point userPoint = (Point) user.getLocation();
        grid.get(userPoint.x(), userPoint.y()).getObjects().remove(user);
        driveTo(taxi, destination);

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


    public Grid initRegion() {
        return new Grid(10, 10);
    }

    /**
     * This method will populate the taxis in region linked list with random taxi data stored in taxiData.csv
     */
    public void populateRegionWithTaxis() throws IOException {
        // Todo make this work with jar files
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/ise/taxiapp/taxiData.csv"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] lines = line.split(",");
            Taxi taxi = new Taxi(lines[1], new Driver(lines[0], lines[2]), Fare.valueOf(lines[4]));
            grid.setLocation(taxi, Integer.parseInt(lines[5]));
            grid.insertTaxi(taxi);
        }
    }


    private void createSingleTaxi() {
        Taxi t = new Taxi("123", new Driver("John", "456"), Fare.STANDARD_FARE);
        grid.getTaxiList().add(t);
        grid.setLocation(t, 5, 6);
    }

    public void printGrid() {
        System.out.printf("""
                    Key:
                    %s☒: User
                    %s☒: Standard Taxi
                    %s☒: Express Taxi
                    %s☒: XL Taxi
                """, USER_COlOUR, Fare.STANDARD_FARE.getColour(), Fare.EXPRESS_FARE.getColour(), Fare.EXPRESS_FARE.getColour());

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Point point = grid.get(x, y);
                String colour = "";
                if (point.getObjects().isEmpty()) {
                    colour = RESET;
                } else {
                    for (Object o : point.getObjects()) {
                        if (o instanceof User) {
                            colour = USER_COlOUR;
                            break;
                        } else if (o instanceof Taxi taxi) {
                            colour = taxi.getFare().getColour();
                        } else if (colour.isEmpty()) {
                            colour = RESET;
                        }
                    }
                }
                System.out.printf("%s%s%s ", colour, BOX, RESET);
            }
            System.out.println(RESET);
        }
    }

    public void driveTowards(Taxi taxi, Point p) {
        Point taxiPoint = (Point) taxi.getLocation();
        int newX = taxiPoint.x();
        int newY = taxiPoint.y();
        if (newX < p.x()) newX++;
        else if (newX > p.x()) newX--;
        else if (newY < p.y()) newY++;
        else if (newY > p.y()) newY--;
        grid.setLocation(taxi, newX, newY);
    }

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
                    He should arrive in %d minutes%n""", taxi.getDriver(), location, taxi.timeToDestination());
            Thread.sleep(1000);
        }
        taxi.driveToDestination();
    }
}
