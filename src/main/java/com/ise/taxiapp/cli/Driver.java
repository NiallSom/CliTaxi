package com.ise.taxiapp.cli;

import com.ise.taxiapp.UI;
import com.ise.taxiapp.entities.User;
import com.ise.taxiapp.entities.fare.Fare;
import com.ise.taxiapp.entities.fare.FareType;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;

import java.util.Scanner;

import static com.ise.taxiapp.cli.Util.promptInput;

public class Driver implements UI {
    User user;
    Scanner scanner;

    public static void main(String[] args) {
        new Driver().run();
    }

    public void run() {
        System.out.println("Welcome to taxi app!");
        System.out.print("Please enter your username: ");
        scanner = new Scanner(System.in);
        String username = scanner.next();
        user = new User(username);
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
        System.out.println("Looking for driver...");
        System.out.println("Where to?");
        System.out.print("Enter a grid index from 1-100: ");
        int destinationIndex = scanner.nextInt();
        Location destination = new Point(destinationIndex);
        String farePrompt = """
                What type of ride are you looking for?
                (0) Standard
                (1) Express
                (2) XL""";
        Fare fare = switch (promptInput(farePrompt, 2, scanner)) {
            case 0 -> new Fare(FareType.STANDARDFARE);
            case 1 -> new Fare(FareType.EXPRESSFARE);
            case 2 -> new Fare(FareType.EXTRALARGEFARE);
            default -> null; // unreachable, promptInput only allows valid inputs
        };
        user.callTaxi(destination, fare);
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
    public void rateTaxi(com.ise.taxiapp.entities.Driver driver) {
        boolean option = false;
        int rating = promptInput("""
            How would you rate your ride?
            Rate from 0-5""", 5, scanner);
    }
}
