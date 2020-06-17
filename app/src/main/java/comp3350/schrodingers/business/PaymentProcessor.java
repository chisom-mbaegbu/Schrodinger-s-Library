package comp3350.schrodingers.business;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comp3350.schrodingers.business.cardExceptions.CardCvvRequired;
import comp3350.schrodingers.business.cardExceptions.CardDateRequiredException;
import comp3350.schrodingers.business.cardExceptions.CardException;
import comp3350.schrodingers.business.cardExceptions.CardInvalidCvvException;
import comp3350.schrodingers.business.cardExceptions.CardInvalidDateException;
import comp3350.schrodingers.business.cardExceptions.CardInvalidNumberException;
import comp3350.schrodingers.business.cardExceptions.CardNameRequiredException;
import comp3350.schrodingers.business.cardExceptions.CardNumberRequiredException;
import comp3350.schrodingers.objects.User.Billing;

// Class - used to validate payment info
public class PaymentProcessor {

    // Method - validate correct credit card syntax
    public boolean validateCard(Billing card) throws CardException {

        // Holds information present on credit card
        String number = "" + card.getCardNumber();
        String date = card.getExpiry();
        String cvv = "" + card.getCvv();
        String name = card.getFullName();

        // Check for correct credit card length
        if (number.length() == 0)
            throw new CardNumberRequiredException();
        else if (number.length() < 16)
            throw new CardInvalidNumberException();

        // Check for correct date format
        if (date.length() == 0)
            throw new CardDateRequiredException();
        else {
            String regex = "^(0[1-9]|1[0-2])\\/\\d{2}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(date);
            if (!matcher.matches())
                throw new CardInvalidDateException();
            else {
                String[] checkDate = date.split("/");
                int month = Integer.parseInt(checkDate[0]);
                int year = Integer.parseInt(checkDate[1]) + 2000;
                Calendar cal = Calendar.getInstance();
                if (year < cal.get(Calendar.YEAR) || (year == cal.get(Calendar.YEAR) && month <= cal.get(Calendar.MONTH)))
                    throw new CardInvalidDateException();
                else if (year > cal.get(Calendar.YEAR) + 20)
                    throw new CardInvalidDateException();
            }
        }

        // Check for correct CVV length
        if (cvv.length() == 0)
            throw new CardCvvRequired();
        else if (cvv.length() < 3)
            throw new CardInvalidCvvException();

        // Check that name for credit card has been entered
        if (name.length() == 0)
            throw new CardNameRequiredException();
        return true;
    }

}
