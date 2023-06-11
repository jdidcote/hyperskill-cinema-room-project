package cinema.repository;

import cinema.model.PurchasedSeat;
import cinema.model.ReturnedSeat;
import cinema.model.Seat;
import cinema.model.Seats;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Repository
public class SeatsRepository {
    private Seats seats;
    private List<PurchasedSeat> purchasedSeats;

    public SeatsRepository() {
        initialiseAvailableSeats(9, 9);
        purchasedSeats = new ArrayList<>();
    }

    private void initialiseAvailableSeats(int nRows, int nCols) {
        List<Seat> availableSeats = new ArrayList<>();
        for (int row = 1; row <= nRows; row++) {
            for (int col = 1; col <= nCols; col++) {
                availableSeats.add(
                        new Seat(row, col)
                );
            }
        }
        this.seats = new Seats(availableSeats);
    }

    public PurchasedSeat purchaseSeat(Seat requestedSeat) {
        seats.purchaseSeat(requestedSeat);
        PurchasedSeat purchasedSeat = new PurchasedSeat(String.valueOf(randomUUID()), requestedSeat);
        purchasedSeats.add(purchasedSeat);
        return purchasedSeat;
    }

    public boolean isSeatAvailable(Seat requestedSeat) {
        return seats.isSeatAvailable(requestedSeat);
    }

    public Seats getSeats() {
        return seats;
    }

    public boolean isReturnTokenValid(String token) {
        return purchasedSeats.stream()
                .anyMatch(purchasedSeat -> purchasedSeat.getToken().equals(token));
    }

    public ReturnedSeat refundSeat(String token) {
        PurchasedSeat purchasedSeatToRemove = purchasedSeats.stream()
                .filter(purchasedSeat -> purchasedSeat.getToken().equals(token))
                .findFirst()
                .get();
        purchasedSeats.remove(purchasedSeatToRemove);
        return new ReturnedSeat(purchasedSeatToRemove.getTicket());
    }

    public List<PurchasedSeat> getPurchasedSeats() {
        return purchasedSeats;
    }

}
