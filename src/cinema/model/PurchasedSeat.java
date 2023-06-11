package cinema.model;

public class PurchasedSeat {
    private final String token;
    private final Seat ticket;

    public PurchasedSeat(String token, Seat seat) {
        this.token = token;
        this.ticket = seat;
    }

    public String getToken() {
        return token;
    }

    public Seat getTicket() {
        return ticket;
    }
}
