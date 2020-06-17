package comp3350.schrodingers.presentation;


import android.app.Activity;

import comp3350.schrodingers.business.cardExceptions.CardCvvRequired;
import comp3350.schrodingers.business.cardExceptions.CardDateRequiredException;
import comp3350.schrodingers.business.cardExceptions.CardException;
import comp3350.schrodingers.business.cardExceptions.CardInvalidCvvException;
import comp3350.schrodingers.business.cardExceptions.CardInvalidDateException;
import comp3350.schrodingers.business.cardExceptions.CardInvalidNumberException;
import comp3350.schrodingers.business.cardExceptions.CardNameRequiredException;
import comp3350.schrodingers.business.cardExceptions.CardNumberRequiredException;

public class HandleCardExceptions {
    private CardException cardExcep;
    public HandleCardExceptions(CardException u){
        cardExcep = u;
        decideOutput();
    }
    private void decideOutput(){
        if(cardExcep instanceof CardNumberRequiredException)
            cardExcep.setMessage("Card Number required!");
        else if(cardExcep instanceof CardInvalidNumberException)
            cardExcep.setMessage("Invalid card number!");
        else if(cardExcep instanceof CardDateRequiredException)
            cardExcep.setMessage("Expiry date required!");
        else if(cardExcep instanceof CardInvalidDateException)
            cardExcep.setMessage("Invalid date!\nPlease write Date in the correct format: \'01/99\'");
        else if(cardExcep instanceof CardInvalidCvvException)
            cardExcep.setMessage("Cvv invalid");
        else if(cardExcep instanceof CardCvvRequired)
            cardExcep.setMessage("CVV required!");
        else if(cardExcep instanceof CardNameRequiredException)
            cardExcep.setMessage("Name in Card required");
    }
    public String toString(){return cardExcep.toString();}

    public void showMessage(Activity act){
        Messages.warning(act, cardExcep.toString());
    }


}
