package cinema.service;

import cinema.exceptions.InvalidReturnTokenError;
import cinema.model.*;
import cinema.repository.SeatsRepository;
import cinema.repository.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {

    private final SeatsRepository seatsRepository;
    private final StatsRepository statsRepository;

    @Autowired
    public SeatsService(SeatsRepository seatsRepository, StatsRepository statsRepository) {
        this.seatsRepository = seatsRepository;
        this.statsRepository = statsRepository;
        this.statsRepository.setNumberOfAvailableSeats(
                seatsRepository.getSeats().getAvailableSeats().size()
        );
    }

    public Seats getSeats() {
        return seatsRepository.getSeats();
    }

    public List<PurchasedSeat> getPurchasedSeats() {
        return seatsRepository.getPurchasedSeats();
    }

    public PurchasedSeat purchaseSeat(Seat requestedSeat) {
        PurchasedSeat purchasedSeat = seatsRepository.purchaseSeat(requestedSeat);
        statsRepository.setCurrentIncome(
                statsRepository.getCurrentIncome() + requestedSeat.getPrice()
        );
        statsRepository.setNumberOfPurchasedTickets(
                statsRepository.getNumberOfPurchasedTickets() + 1
        );
        statsRepository.setNumberOfAvailableSeats(
                statsRepository.getNumberOfAvailableSeats() - 1
        );
        return purchasedSeat;
    }

    public boolean isSeatAvailable(Seat requestedSeat) {
        return seatsRepository.isSeatAvailable(requestedSeat);
    }

    public ReturnedSeat refundSeat(String token) throws InvalidReturnTokenError {
        if (!seatsRepository.isReturnTokenValid(token)) {
            throw new InvalidReturnTokenError("Invalid token");
        }

        ReturnedSeat refundedSeat = seatsRepository.refundSeat(token);

        statsRepository.setCurrentIncome(
                statsRepository.getCurrentIncome() -
                        refundedSeat.returnedTicket().getPrice()
        );
        statsRepository.setNumberOfPurchasedTickets(
                statsRepository.getNumberOfPurchasedTickets() - 1
        );
        statsRepository.setNumberOfAvailableSeats(
                statsRepository.getNumberOfAvailableSeats() + 1
        );
        return refundedSeat;
    }

}
