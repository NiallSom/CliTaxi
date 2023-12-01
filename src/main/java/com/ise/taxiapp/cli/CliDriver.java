package com.ise.taxiapp.cli;

import com.ise.taxiapp.UI;
import com.ise.taxiapp.entities.Driver;
import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.User;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;
import com.ise.taxiapp.nav.Region;

import java.util.Scanner;

import static com.ise.taxiapp.cli.Util.promptInput;

public class CliDriver implements UI {
    private User user;
    private Scanner scanner;
    private Region region;

    public static void main(String[] args) {
        new CliDriver().run();
    }

    public void run() {
        System.out.println("Welcome to taxi app!");
        System.out.print("Please enter your username: ");
        scanner = new Scanner(System.in);
        String username = scanner.next();
        user = new User(username);
        region = initRegion();
        String continuePrompt = """
                Would you like to book a taxi?
                (0) Yes
                (1) No""";
        while (promptInput(continuePrompt, 1, scanner) == 0) {
            callTaxi();
        }
        System.out.println("""
                Thanks for using TaxiApp
                Be sure to leave a rating!""");
    }

    public void callTaxi() {
        int destinationIndex = promptInput("""
                Where to?
                Enter a grid index from 0-99:""", 99, scanner);
        Location destination = Point.fromIndex(destinationIndex, ((Grid) region).getWidth());
        String farePrompt = """
                What type of ride are you looking for?
                (0) Standard
                (1) Express
                (2) XL""";
        Fare fare = switch (promptInput(farePrompt, 2, scanner)) {
            case 0 -> Fare.STANDARD_FARE;
            case 1 -> Fare.EXPRESS_FARE;
            case 2 -> Fare.EXTRA_LARGE_FARE;
            default -> null; // unreachable, promptInput only allows valid inputs
        };
        user.callTaxi(destination, fare);
    }

    public Region initRegion() {
        return new Grid(10, 10);
    }

    @Override
    public void informOnRoute() {
        System.out.println("Taxi on the way!");
    }

    @Override
    public void informArrived() {
        System.out.println("The taxi has arrived!");
    }

    @Override
    public void informCharge(double charge) {
        System.out.printf("The charge was %.2f", charge);
    }

    @Override
    public void informTripComplete() {
        System.out.println("Trip complete!");
    }

    @Override
    public void rateTaxi(Driver driver) {
        boolean option = false;
        int rating = promptInput("""
                How would you rate your ride?
                Rate from 0-5""", 5, scanner);
    }
}
