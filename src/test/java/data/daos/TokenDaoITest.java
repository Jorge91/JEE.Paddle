package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Token;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TokenDaoITest {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private DaosService daosService;
    
    @Autowired
    private UserDao userDao;
	
	@Autowired
    private AuthorizationDao authorizationDao;

    @Test
    public void testFindByUser() {
        Token token = (Token) daosService.getMap().get("tu1");
        User user = (User) daosService.getMap().get("u4");
        assertEquals(token, tokenDao.findByUser(token.getUser()));
        assertNull(tokenDao.findByUser(user));
    }
    
    @Test
    public void testDeleteExpiredTokens() {
    	int initial = (int) tokenDao.count();
    	assertEquals(initial, tokenDao.count());
    	
    	User user = (User) daosService.getMap().get("u4");
    	
    	Token token = new Token(user);
        Calendar finishDate = Calendar.getInstance();
        finishDate.add(Calendar.HOUR, -5);
        token.setExpirationDate(finishDate);
        tokenDao.save(token);
        assertEquals(initial + 1, tokenDao.count());

        List<Token> allTokens = tokenDao.findAll();
        int counter = 0;
		for (Token t:allTokens) {	
			if (t.isTokenExpired()) counter++;
		}	
        
		tokenDao.deleteExpiredTokens();
		
		assertEquals(initial + 1 - counter, tokenDao.count());
    }

}
