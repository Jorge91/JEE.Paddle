package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Authorization;
import data.entities.Role;
import data.entities.Token;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class UserDaoITest {

	@Autowired
    private TokenDao tokenDao;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private DaosService daosService;

    @Test
    public void testCreate() {
        assertTrue(userDao.count() >= 8);
    }

    @Test
    public void testFindDistinctByUsernameOrEmail() {
    	User u1 = new User("FindDistinctByUsernameOrEmail", "FindDistinctByUsernameOrEmail" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(u1);
        
        assertEquals(u1, userDao.findByUsernameOrEmail(u1.getUsername()));
        assertEquals(u1, userDao.findByUsernameOrEmail(u1.getEmail()));
        assertNull(userDao.findByUsernameOrEmail("kk"));
    }

    @Test
    public void testFindByTokenValue() {
    	User u1 = new User("FindByTokenValue", "FindByTokenValue" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(u1);
        
        Token token = new Token(u1);
        Calendar finishDate = Calendar.getInstance();
        finishDate.add(Calendar.HOUR, 1);
        token.setExpirationDate(finishDate);
        tokenDao.save(token);

        assertEquals(u1, userDao.findByTokenValue(token.getValue()));
        assertNull(userDao.findByTokenValue("kk"));
    }
    

    @Test
    public void testFindByTokenValueInvalid() {
    	User user = new User("testFindByTokenValueInvalid", "testFindByTokenValueInvalid" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(user);
        authorizationDao.save(new Authorization(user, Role.PLAYER));
  
        Token token = new Token(user);
        Calendar finishDate = Calendar.getInstance();
        finishDate.add(Calendar.HOUR, -5);
        token.setExpirationDate(finishDate);
        tokenDao.save(token);
        
        assertNull(userDao.findByTokenValue(token.getValue()));
        
    }
    
    
}
