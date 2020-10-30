package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;

public class PhysicalCard implements Card {

    private final String type;
    private final LocalDate expirationDate;
    private boolean blocked;
    private String number;
    private final int pin;
    private int incorrectPinCount;

    public PhysicalCard(String number, int pin, LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        this.type = "PHYSICAL";
        this.blocked = false;
        this.number = number;
        this.pin = pin;
        this.incorrectPinCount = 0;

    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public boolean checkPin(int pin) {
        return this.pin == pin;
    }

    @Override
    public void block() {
        this.blocked = true;
    }

    public int getIncorrectPinCount() {
        return incorrectPinCount;
    }

    public void setIncorrectPinCount(int incorrectPinCount) {
        this.incorrectPinCount = incorrectPinCount;
    }

    public void incrementIncorrectPinCount(){
        this.incorrectPinCount++;
    }

    public String getNumber() {
        return number;
    }
}
