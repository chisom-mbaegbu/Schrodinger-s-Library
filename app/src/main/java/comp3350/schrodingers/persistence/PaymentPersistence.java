package comp3350.schrodingers.persistence;

import comp3350.schrodingers.objects.User.Billing;

// Interface - provides skeleton for books persistence
public interface PaymentPersistence {
    void addCreditCard(Billing creditCard, String email);
    void updateCreditCard(Billing creditCard, String email);
    Billing getCard();
    Billing getUserCard(String email);
    Billing findCard(long number);
}
