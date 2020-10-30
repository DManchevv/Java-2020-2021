package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;

public class VirtualPermanentCard implements Card {
    private String type;
    private LocalDate expirationDate;
    private boolean blocked;
    private String number;
    private int pin;
    private int incorrectPinCount;

    public VirtualPermanentCard(String number, int pin, LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        this.type = "VIRTUALPERMANENT";
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
        if(this.pin != pin) return false;
        else return true;
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
