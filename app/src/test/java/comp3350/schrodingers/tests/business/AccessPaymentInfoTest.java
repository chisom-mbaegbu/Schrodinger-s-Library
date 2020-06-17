package comp3350.schrodingers.tests.business;
import org.junit.Before;
import org.junit.Test;

import comp3350.schrodingers.business.AccessPaymentInfo;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.objects.User.Billing;
import comp3350.schrodingers.persistence.PaymentPersistence;
import comp3350.schrodingers.persistence.UsersPersistence;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessPaymentInfoTest {
    private AccessPaymentInfo accessPayInfo;
    private PaymentPersistence paymentPersistence;
    private AccessUserInfo user;

    @Before
    public void setUp(){
        paymentPersistence = mock(PaymentPersistence.class);
        user = new AccessUserInfo(mock(UsersPersistence.class));
        accessPayInfo = new AccessPaymentInfo(paymentPersistence, user);
    }

    @Test
    public void testGetCard(){
        final Billing card = new Billing();
        System.out.println("\nStarting test AccessPaymentInfo");
        UserBuilder builder = new UserBuilder();
        User toAdd = builder.id(1).name("Zune").email("zunenebula@gmail.com").password("1234").buildUser();
        when(user.getUser()).thenReturn(toAdd);
        when(paymentPersistence.getUserCard(toAdd.getEmail())).thenReturn(card);

        final Billing c = accessPayInfo.getCard();
        assertTrue("\tno cards in DB, should be empty", c.noCardNo());
        verify(paymentPersistence).getUserCard(toAdd.getEmail());
        System.out.println("\nFinished test AccessPaymentInfo");
    }
}
