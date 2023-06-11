package cinema.controller;

import cinema.dto.SeatDTO;
import cinema.exceptions.CustomErrorMessage;
import cinema.exceptions.InvalidReturnTokenError;
import cinema.model.*;
import cinema.repository.StatsRepository;
import cinema.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SeatsController {

    private final SeatsService seatsService;
    private final StatsRepository statsRepository;

    @Autowired
    public SeatsController(SeatsService seatsService, StatsRepository statsRepository) {
        this.seatsService = seatsService;
        this.statsRepository = statsRepository;
    }

    @GetMapping("/seats")
    public Seats getSeats() {
        return seatsService.getSeats();
    }

    @GetMapping("/purchased-seats")
    public List<PurchasedSeat> getPurchasedSeats() {
        return seatsService.getPurchasedSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody SeatDTO requestedSeatDTO) throws ResponseStatusException {
        Seat requestedSeat = SeatDTO.convertDTOToSeat(requestedSeatDTO);

        if (Seat.isSeatOutOfBounds(requestedSeat)) {
            return ResponseEntity.badRequest().body(
                    new CustomErrorMessage("The number of a row or a column is out of bounds!")
            );
        }

        boolean isAvailable = seatsService.isSeatAvailable(requestedSeat);

        if (isAvailable) {
            PurchasedSeat purchasedSeat =seatsService.purchaseSeat(requestedSeat);
            return ResponseEntity.ok().body(purchasedSeat);
        } else {
            return ResponseEntity.badRequest().body(new CustomErrorMessage("The ticket has been already purchased!"));
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> refundSeat(@RequestBody RefundRequest refundRequest) {
        try {
            ReturnedSeat returnedSeat = seatsService.refundSeat(refundRequest.token());
            return ResponseEntity.ok().body(returnedSeat);
        } catch (InvalidReturnTokenError e) {
            return ResponseEntity.badRequest().body(new CustomErrorMessage("Wrong token!"));
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false) String password) {

        ResponseEntity<CustomErrorMessage> response401 = ResponseEntity.status(401)
                .body(new CustomErrorMessage("The password is wrong!"));

        String correctPassword = "super_secret";

        if (password == null) {
            return response401;
        }

        if (!password.equals(correctPassword)) {
            return response401;
        }

        return ResponseEntity.ok().body(statsRepository);
    }
}