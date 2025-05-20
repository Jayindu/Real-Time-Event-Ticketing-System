
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Configaration {
    static int option = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Welcome to Real Time Ticket Booking System");

            int option1;
            do {
                System.out.println("Enter 1 to Configure the System or Enter 0 to Exit the System - ");
                option1 = validateIntInput(scanner, 0, 1);

                if (option1 == 1) {

                    System.out.print("Enter Total Tickets - ");
                    int totalTickets = validatePositiveIntInput(scanner);

                    System.out.print("Enter Ticket Release Rate - ");
                    int ticketReleaseRate = validatePositiveIntInput(scanner);

                    System.out.print("Enter Ticket Buy Rate - ");
                    int ticketBuyRate = validatePositiveIntInput(scanner);

                    System.out.print("Enter Ticket Pool Maximum Capacity - ");
                    int maxTicketCapacity = validatePositiveIntInput(scanner);

                    System.out.print("Enter Number of Vendors - ");
                    int numberOfVendors = validatePositiveIntInput(scanner);

                    System.out.print("Enter Number of Customers - ");
                    int numberOfCustomers = validatePositiveIntInput(scanner);

                    TicketPool ticketPool = new TicketPool(maxTicketCapacity);

                    System.out.println("System Configuration is Successful");
                    System.out.println("Enter 1 to Start the System or Enter 0 to Exit the System / Reconfigure the System - ");
                    option = validateIntInput(scanner, 0, 1);

                    if (option == 1) {
                        Thread[] vendorThreads = new Thread[numberOfVendors];
                        for (int i = 0; i < numberOfVendors; i++) {
                            Vendor vendor = new Vendor(totalTickets / numberOfVendors, ticketReleaseRate, ticketPool);
                            vendorThreads[i] = new Thread(vendor, "Vendor ID-" + (i + 1));
                            vendorThreads[i].start();
                        }

                        Thread[] customerThreads = new Thread[numberOfCustomers];
                        for (int i = 0; i < numberOfCustomers; i++) {
                            Customer customer = new Customer(ticketPool, ticketBuyRate);
                            customerThreads[i] = new Thread(customer, "Customer ID-" + (i + 1));
                            customerThreads[i].start();
                        }

                        // Wait for all vendor threads to finish
                        for (Thread vendorThread : vendorThreads) {
                            try {
                                vendorThread.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Wait for all customer threads to finish
                        for (Thread customerThread : customerThreads) {
                            try {
                                customerThread.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        System.out.println("All tickets have been added and sold.");
                    }
                } else if (option1 == 0) {
                    System.out.println("System Exited");
                    exit(0);
                }
            } while (true); // Keeps the system running unless explicitly exited

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static int validateIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("Please enter  0 or 1: ", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static int validatePositiveIntInput(Scanner scanner) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input > 0) {
                    return input;
                } else {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid positive number: ");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}

