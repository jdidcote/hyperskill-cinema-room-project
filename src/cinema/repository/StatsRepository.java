package cinema.repository;

import org.springframework.stereotype.Repository;

@Repository
public class StatsRepository {
    int currentIncome;
    int numberOfAvailableSeats;
    int numberOfPurchasedTickets;

    public StatsRepository() {
        this.currentIncome = 0;
        this.numberOfAvailableSeats = 0;
        this.numberOfPurchasedTickets = 0;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }
}
