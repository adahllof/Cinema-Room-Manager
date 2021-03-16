package cinema;

import java.util.Scanner;

public class Cinema {
    private final char FREE_SEAT = 'S';
    private final int numberOfRows; /* should be 0-9 */
    private final int numberOfSeatsInRows; /* should be 0-9 */
    private final String name;
    private char[][] seats;
    Scanner scanner = new Scanner(System.in);

    public Cinema(String name, int numberOfRows, int numberOfSeatsInRows) {
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.numberOfSeatsInRows = numberOfSeatsInRows;
        initSeats(numberOfRows, numberOfSeatsInRows);
    }

    public Cinema() {
        this.name = "Cinema";
        this.numberOfRows = 7;
        this.numberOfSeatsInRows = 8;
        initSeats(this.numberOfRows, this.numberOfSeatsInRows);
    }

    private void initSeats(int numberOfRows, int numberOfSeatsInRows) {
        seats = new char[numberOfRows][numberOfSeatsInRows];
        for (int row = 1; row <= numberOfRows; row++) {
            for (int seat = 1; seat <= numberOfSeatsInRows; seat++) {
                seats[row-1][seat-1] = FREE_SEAT;
            }
        }
    }
    public void print() {
        System.out.println(name + ":");
        System.out.print(" "); /* 2 spaces */
        for (int i = 1; i <= numberOfSeatsInRows; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int row = 1; row <= numberOfRows; row++) {
            System.out.print(row);
            for (int seat = 1; seat <= numberOfSeatsInRows; seat++) {
                System.out.print(" " + seats[row-1][seat-1]); /*Indices start with 0*/
            }
            System.out.println();
        }

    }
    private static int totalIncome(int rows, int seats) {
        int totalNumberOfSeats;
        if (rows <= 9) {
            totalNumberOfSeats = rows * seats;
        } else {
            totalNumberOfSeats = 9 * seats;
        }
        if (totalNumberOfSeats <= 0) {
            return 0;
        } else if (totalNumberOfSeats <= 60) {
            return totalNumberOfSeats * 10;
        }else if (rows % 2 == 0) {
            return (totalNumberOfSeats / 2) * 18;
        } else {
            return seats * ((rows / 2 ) * 10 + (rows / 2 + 1) * 8);
        }
    }

    private int numberOfSeats() {
        return numberOfSeatsInRows * numberOfRows;
    }

    public int ticketPrice(int row) {
        if (row < 1 ) {
            return -1;
        } else if (row > numberOfRows) {
            return -1;
        } else if (numberOfSeats() <= 60) {
            return 10;
        } else if (row <= numberOfRows / 2) {
            return 10;
        } else {
            return 8;
        }
    }

    private void bookSeat() {
        if (purchasedTickets() >= numberOfSeats()) {
            System.out.println("All tickets have been sold!");
        } else {
            boolean exists;
            boolean ready;
            int[] seat;
            do {
                seat = readSeat();
                if (seat[0] < 1 || seat[0] > numberOfRows) {
                    exists = false;
                } else if (seat[1] < 1 || seat[1] > numberOfSeatsInRows) {
                    exists = false;
                } else {
                    exists = true;
                }
                if (exists) {
                    if (seats[seat[0] - 1][seat[1] - 1] == 'S') {
                        seats[seat[0] - 1][seat[1] - 1] = 'B';
                        System.out.println("Ticket price: " + "$" + ticketPrice(seat[0]));
                        ready = true;
                    } else {
                        ready = false;
                        System.out.println("That ticket has already been purchased!");
                    }
                } else {
                    ready = false;
                    System.out.println("Wrong input!");
                }
            } while (!ready);

        }
    }

    private int getMenuChoice() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return scanner.nextInt();
    }
    private int[] readSeat() {
        int[] seat = new int[2];
        System.out.println("Enter a row number:");
        seat[0] =scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        seat[1] = scanner.nextInt();
        return seat;
    }



    private void statistics() {
        System.out.print("Number of purchased tickets: ");
        System.out.println(purchasedTickets());
        System.out.print("Percentage: ");
        System.out.print(String.format("%.2f", percentageSold()) );
        System.out.println("%");
        System.out.print("Current income: $");
        System.out.println(currentIncome());
        System.out.print("Total income: $");
        System.out.println(totalIncome(numberOfRows, numberOfSeatsInRows));
    }

    private double percentageSold() {
        return 100.0 * purchasedTickets() / numberOfSeats();
    }

    private int purchasedTickets() {
        int count = 0;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfSeatsInRows; j++) {
                if (seats[i][j] == 'B') count++;
            }
        }
        return count;
    }

    private int currentIncome() {
        int sum = 0;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfSeatsInRows; j++) {
                if (seats[i][j] == 'B') {
                   sum += ticketPrice(i + 1);
                };
            }
        }
        return sum;
    }

    private void executeMenuChoice(int menuChoice) {
        switch (menuChoice) {
            case 1: print();
                    break;

            case 2: bookSeat();
                    break;

            case 3: statistics();
                    break;

            case 0: break;

            default:
                System.out.println("Unrecognised command!");
        }
    }





    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        Cinema myCinema = new Cinema("Cinema", rows, seats);
        int choice;
        do {
            choice = myCinema.getMenuChoice();
            System.out.println();
            myCinema.executeMenuChoice(choice);
        } while (choice != 0);
    }
}