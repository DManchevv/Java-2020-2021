package bg.sofia.uni.fmi.mjt.revolut.account;

public class BGNAccount extends Account {
    String currency;

    public BGNAccount(String IBAN) {
        super(IBAN);
        this.currency = "BGN";
    }

    public BGNAccount(String IBAN, double amount){
        super(IBAN, amount);
        this.currency = "BGN";
    }
    @Override
    public String getCurrency(){
        return currency;
    }
}
