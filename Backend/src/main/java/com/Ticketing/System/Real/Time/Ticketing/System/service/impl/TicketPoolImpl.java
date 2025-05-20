package com.Ticketing.System.Real.Time.Ticketing.System.service.impl;

import com.Ticketing.System.Real.Time.Ticketing.System.dto.ConfigurationDto;
import com.Ticketing.System.Real.Time.Ticketing.System.dto.TicketDto;
import com.Ticketing.System.Real.Time.Ticketing.System.entity.Ticket;
import com.Ticketing.System.Real.Time.Ticketing.System.repo.TicketRepository;
import com.Ticketing.System.Real.Time.Ticketing.System.service.TicketPool;
import com.Ticketing.System.Real.Time.Ticketing.System.threads.CustomerThread;
import com.Ticketing.System.Real.Time.Ticketing.System.threads.VendorThread;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class TicketPoolImpl implements TicketPool {

    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int numberOfVendors;
    private int numberOfCustomers;

    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final TicketRepository ticketRepository;

    private boolean running = false; // Flag to control the system state
    private int ticketsAdded = 0;    // Track how many tickets have been added
    private int ticketsPurchased = 0; // Track how many tickets have been purchased
    private boolean isComplete = false; // Ensure only one completion message
    private final List<Thread> threads = new ArrayList<>(); // List to keep track of running threads

    public TicketPoolImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public synchronized void configure(ConfigurationDto config) {
        this.maxTicketCapacity = config.getMaxTicketCapacity();
        this.totalTickets = config.getTotalTickets();
        this.ticketReleaseRate = config.getTicketReleaseRate();
        this.customerRetrievalRate = config.getCustomerRetrievalRate();
        this.numberOfVendors = config.getNumberOfVendors();
        this.numberOfCustomers = config.getNumberOfCustomers();


    }

    public synchronized void startSystem() {
        if (running) {
            return;
        }
        running = true;
        isComplete = false;

        ticketsAdded = 0;
        ticketsPurchased = 0;

        // Start vendor threads
        for (int i = 0; i < numberOfVendors; i++) {
            Thread vendorThread = new Thread(new VendorThread(this, totalTickets / numberOfVendors, ticketReleaseRate, this));
            vendorThread.setName("Vendor-" + i);
            threads.add(vendorThread);
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 0; i < numberOfCustomers; i++) {
            Thread customerThread = new Thread(new CustomerThread(this, customerRetrievalRate,this));
            customerThread.setName("Customer-" + i);
            threads.add(customerThread);
            customerThread.start();
        }
    }
    public synchronized void stopSystem() {
        if (!running) return; // Prevent multiple stops
        running = false;

        // Interrupt threads gracefully
        threads.forEach(thread -> {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        });
        threads.clear(); // Clear the thread list
        System.out.println("System has stopped all threads.");
    }
    public boolean isRunning() {
        return running;
    }
    @Override
    public synchronized void addTickets(TicketDto ticketDto) throws InterruptedException {
        for (int i = 0; i < ticketDto.getCount(); i++) {
            while (ticketQueue.size() >= maxTicketCapacity) {
                wait();
            }
            Ticket ticket = new Ticket();
            ticket.setEventName(ticketDto.getEventName());
            ticket.setPrice(ticketDto.getPrice());
            ticketQueue.add(ticket);
            ticketRepository.save(ticket);
            ticketsAdded++;
            notifyAll();

        }
    }

    @Override
    public synchronized Ticket buyTicket() throws InterruptedException {
        while (ticketQueue.isEmpty()) {
            wait();
        }
        Ticket ticket = ticketQueue.poll();
        ticketsPurchased++;
        notifyAll();

        if (ticketsAdded >= totalTickets && ticketsPurchased >= totalTickets) {
            completeSystem();
        }
        return ticket;
    }

    @Override
    public synchronized int getAvailableTickets() {
        return ticketQueue.size();
    }

    private synchronized void completeSystem() {
        if (!isComplete) {
            isComplete = true;
            System.out.println("All tickets have been added and purchased. System is now complete.");
            stopSystem(); // Stop all threads gracefully
        }
    }
}

