package data.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import data.entities.Token;
import data.entities.User;

public class TokenTest {

    @Test
    public void testTokenUser() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        assertTrue(token.getValue().length() > 20);
    }
    
    @Test
    public void testTokenHasExpired() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        Calendar expiredDate = Calendar.getInstance();
        expiredDate.add(Calendar.HOUR, -2);
        token.setExpirationDate(expiredDate);
       
        assertTrue(token.isTokenExpired());
    }
    
    @Test
    public void testTokenValid() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        Calendar expiredDate = Calendar.getInstance();
        expiredDate.add(Calendar.MINUTE, 30);
        token.setExpirationDate(expiredDate);
        
        assertFalse(token.isTokenExpired());
    }

}
