package comp3350.schrodingers.business;

import comp3350.schrodingers.business.cardExceptions.CardException;
import comp3350.schrodingers.objects.User.Billing;
import comp3350.schrodingers.persistence.PaymentPersistence;

// Class - facilitates accessing payment info from DB
public class AccessPaymentInfo {

    // Store payment access to DB and relevant/current credit card number
    private PaymentPersistence payPersistence;
    private AccessUserInfo userInfo = null;

    // Constructor - inject DB access
    public AccessPaymentInfo(PaymentPersistence persistence) {
        this.payPersistence = persistence;
    }

    //Injectable for tests
    public AccessPaymentInfo(PaymentPersistence persistence, AccessUserInfo u) {
        this.payPersistence = persistence;
        userInfo = u;
    }

    // Method - return relevant/current credit card from DB
    public Billing getUserCard(String email) {
        return payPersistence.getUserCard(email);
    }

    public Billing getCard(){
        if(userInfo == null)
            userInfo = Services.getUserInfoAccess();
        return payPersistence.getUserCard(userInfo.getUser().getEmail());
    }

    // Method - insert credit card into DB
    public void insertCard(Billing card, String email) throws CardException {
        PaymentProcessor p = new PaymentProcessor();
        p.validateCard(card);
        if (payPersistence.getCard().noCardNo())
            payPersistence.addCreditCard(card, email);
        else updateCard(card, email);
    }

    // Method - update credit card stored in DB
    public void updateCard(Billing card, String email) {
        payPersistence.updateCreditCard(card, email);
    }


}
