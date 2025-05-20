public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerRetrivelRate;

    public Customer(TicketPool ticketPool, int customerRetrivelRate) {
        this.ticketPool = ticketPool;
        this.customerRetrivelRate = customerRetrivelRate;
    }

    @Override
    public void run() {
        while (true) {
            Ticket ticket = ticketPool.buyTicket(); // Call method to buyTickets
            if (ticket == null) {
                break;
            }

            try {
                Thread.sleep(customerRetrivelRate * 1000); // Retieving delay
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}