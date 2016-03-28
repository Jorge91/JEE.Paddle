package data.daos;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Authorization;
import data.entities.Role;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class AuthorizationDaoITest {

    @Autowired
    private AuthorizationDao authorizationDao;
    
    @Autowired
    private TrainingDao trainingDao;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private DaosService daosService;

    @Test
    public void testFindRoleByUser() {
    	User user = new User("testDeleteStudent", "testDeleteStudent" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(user);
        authorizationDao.save(new Authorization(user, Role.PLAYER));
        List<Role> roles = authorizationDao.findRoleByUser(user);
        
        assertEquals(1, roles.size());
        assertEquals(Role.PLAYER, roles.get(0));
    }

}
