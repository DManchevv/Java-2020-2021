package bg.sofia.uni.fmi.mjt.revolut;
import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;

import java.time.LocalDate;

public class Revolut implements RevolutAPI{

    private Account[] accounts;
    private Card[] cards;
    private LocalDate today = LocalDate.now();
    public Revolut(Account[] accounts, Card[] cards) {
        this.accounts = accounts;
        this.cards = cards;
    }

    @Override
    public boolean pay(Card card, int pin, double amount, String currency) {
        for(int i = 0; i < cards.length; i++){
            if (card.getNumber().equals(cards[i].getNumber())) break;
            if (i == cards.length - 1) return false;
        }
        if(today.isAfter(card.getExpirationDate())) return false;
        if(!card.checkPin(pin)) {
            card.incrementIncorrectPinCount();
            if(card.getIncorrectPinCount() == 3) card.block();
            return false;
        }
        else card.setIncorrectPinCount(0);
        if(card.isBlocked()) return false;
        if(!card.getType().equals("PHYSICAL")) return false;
        for(int i = 0; i < accounts.length; i++){
            if (amount <= accounts[i].getAmount() && accounts[i].getCurrency().equals(currency)) {
                accounts[i].deductAmount(amount);
                break;
            }
            if (i == accounts.length - 1) return false;
        }
        return true;
    }

    @Override
    public boolean payOnline(Card card, int pin, double amount, String currency, String shopURL) {
        if(card == null) return false;
        for(int i = 0; i < cards.length; i++){
            if (card.getNumber().equals(cards[i].getNumber())) break;
            if (i == cards.length - 1) return false;
        }
        if(today.isAfter(card.getExpirationDate())) return false;
        if(!card.checkPin(pin)) {
            card.incrementIncorrectPinCount();
            if(card.getIncorrectPinCount() == 3) card.block();
            return false;
        }
        else card.setIncorrectPinCount(0);
        if(card.isBlocked()) return false;
        String[] domain = shopURL.split("\\.");
        if(domain[domain.length - 1].equals("biz")){
            return false;
        }
        for(int i = 0; i < accounts.length; i++){
            if (amount <= accounts[i].getAmount() && accounts[i].getCurrency().equals(currency)) {
                accounts[i].deductAmount(amount);
                if (card.getType().equals("VIRTUALONETIME")) card.block();
                break;
            }
            if (i == accounts.length - 1) return false;
        }
        return true;
    }

    @Override
    public boolean addMoney(Account account, double amount) {
        if(account == null) return false;
        for(int i = 0; i < accounts.length; i++){
            if (account.getIBAN().equals(accounts[i].getIBAN())) break;
            if (i == accounts.length - 1) return false;
        }
        account.addAmount(amount);
        return true;
    }

    @Override
    public boolean transferMoney(Account from, Account to, double amount) {
        if(from == null || to == null) return false;
        for(int i = 0; i < accounts.length; i++){
            if (from.getIBAN().equals(accounts[i].getIBAN())) break;
            if (i == accounts.length - 1) return false;
        }
        for(int i = 0; i < accounts.length; i++){
            if (to.getIBAN().equals(accounts[i].getIBAN())) break;
            if (i == accounts.length - 1) return false;
        }
        if(!from.checkPositive(amount)) return false;
        if(from.getIBAN().equals(to.getIBAN())) return false;
        if(from.getCurrency().equals(to.getCurrency())){
            from.deductAmount(amount);
            to.addAmount(amount);
        }
        else if (from.getCurrency().equals("BGN")){
            from.deductAmount(amount);
            double convertedAmount = amount / 1.95583;
            to.addAmount(convertedAmount);
        }
        else {
            from.deductAmount(amount);
            double convertedAmount = amount * 1.95583;
            to.addAmount(convertedAmount);
        }
        return true;
    }

    @Override
    public double getTotalAmount() {
        double sum = 0;
        for (int i = 0; i < accounts.length; i++){
            if(accounts[i].getCurrency().equals("EUR")){
                sum += accounts[i].getAmount() * 1.95583;
            }
            else sum += accounts[i].getAmount();
        }
        return sum;
    }
}
