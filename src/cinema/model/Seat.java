package cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    int row;
    int column;
    int price;
    @JsonIgnore
    boolean purchased;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.purchased = false;
        setPrice();
    }

    public static boolean isSeatOutOfBounds(Seat seat) {
        boolean rowOutOfBounds = seat.getRow() > 9 || seat.getRow() < 0;
        boolean columnOutOfBounds = seat.getColumn() > 9 || seat.getColumn() < 0;
        return rowOutOfBounds || columnOutOfBounds;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", price=" + price +
                ", purchased=" + purchased +
                '}';
    }

    private void setPrice() {
        this.price = row <= 4 ? 10 : 8;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

}
