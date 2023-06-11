package cinema.model;

import java.util.List;
import java.util.Optional;

public class Seats {
    private int totalRows;
    private int totalColumns;
    private List<Seat> availableSeats;

    public Seats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
        calculateAndSetTotals();
    }

    private void calculateAndSetTotals() {
        this.totalRows = availableSeats
                .stream()
                .mapToInt(Seat::getRow)
                .max()
                .orElse(0);

        this.totalColumns = availableSeats
                .stream()
                .mapToInt(Seat::getColumn)
                .max()
                .orElse(0);
    }

    public boolean isSeatAvailable(Seat requestedSeat) {

        Optional<Boolean> isSeatPurchased = availableSeats.stream()
                .filter(seat -> seat.getRow() == requestedSeat.getRow() && seat.getColumn() == requestedSeat.getColumn())
                .map(Seat::isPurchased)
                .findFirst();

        if (isSeatPurchased.isPresent()) {
            return !isSeatPurchased.get();
        } else {
            throw new IllegalArgumentException("Invalid seat row/column");
        }
    }

    private void changeSeatPurchasedState(Seat newSeat, boolean newPurchaseState) {
        availableSeats.stream()
                .filter(seat -> seat.getRow() == newSeat.getRow() &&
                        seat.getColumn() == newSeat.getColumn())
                .findFirst()
                .ifPresent(seat -> {
                            seat.setPurchased(true);
                        }
                );
    }

    public void purchaseSeat(Seat newSeat) {
        changeSeatPurchasedState(newSeat, true);
    }

    public void refundSeat(Seat newSeat) {
        changeSeatPurchasedState(newSeat, false);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

}
