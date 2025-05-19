package com.Ticketing.System.Real.Time.Ticketing.System.threads;

import com.Ticketing.System.Real.Time.Ticketing.System.entity.Ticket;
import com.Ticketing.System.Real.Time.Ticketing.System.service.TicketPool;
import com.Ticketing.System.Real.Time.Ticketing.System.service.impl.TicketPoolImpl;

public class CustomerThread implements Runnable {

    private final TicketPool ticketPool;
    private final int retrievalRate;  // Rate of ticket purchase in seconds
    private TicketPoolImpl poolImpl;
    public CustomerThread(TicketPool ticketPool, int retrievalRate, TicketPoolImpl poolImpl) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.poolImpl = poolImpl;
    }

    @Override
    public void run() {
        try {
            while (poolImpl.isRunning() && !Thread.interrupted()) {
                if (!poolImpl.isRunning() || Thread.interrupted()) {
                    break;
                }
                synchronized (ticketPool) {
                    Ticket ticket = ticketPool.buyTicket();
                    System.out.println(Thread.currentThread().getName() + " purchased a ticket: ");
                }
                Thread.sleep(retrievalRate * 1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " has stopped gracefully.");
        }
    }
}