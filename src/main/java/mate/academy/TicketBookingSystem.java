package mate.academy;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketBookingSystem {

    private Semaphore semaphore;
    private AtomicInteger totalSeats;

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = new AtomicInteger(totalSeats);
        semaphore = new Semaphore(totalSeats);
    }

    public BookingResult attemptBooking(String user) {
        BookingResult result;
        try {
            semaphore.acquire();
            if (totalSeats.get() > 0) {
                totalSeats.decrementAndGet();
                result = new BookingResult(user, true, "Booking successful.");
            } else {
                result = new BookingResult(user, false, "No seats available.");
            }
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
