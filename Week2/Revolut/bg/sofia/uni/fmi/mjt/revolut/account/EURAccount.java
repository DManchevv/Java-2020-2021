package bg.sofia.uni.fmi.mjt.revolut.account;

public class EURAccount extends Account{
    String currency;

    public EURAccount(String IBAN) {
        super(IBAN);
        this.currency = "EUR";
    }

    public EURAccount(String IBAN, double amount){
        super(IBAN, amount);
        this.currency = "EUR";
    }

    @Override
    public String getCurrency() {
        return currency;
    }
}
