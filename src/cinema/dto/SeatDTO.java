package cinema.dto;

import cinema.model.Seat;

public class SeatDTO {
    int row;
    int column;
    int price;

    public SeatDTO() {
    }

    public SeatDTO(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public static SeatDTO convertSeatToDTO(Seat seat) {
        SeatDTO seatDTO = new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice());
        return seatDTO;
    }

    public static Seat convertDTOToSeat(SeatDTO seatDTO) {
        Seat seat = new Seat(seatDTO.getRow(), seatDTO.getColumn());
        return seat;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
