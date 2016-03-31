package api;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.daos.AuthorizationDao;
import data.daos.CourtDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Authorization;
import data.entities.Court;
import data.entities.Role;
import data.entities.Training;
import data.entities.User;
import business.api.Uris;
import business.wrapper.TrainingWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingResourceFunctionalTesting {

	private RestService restService = new RestService();
	private static boolean isInitialized = false;
	
	@Autowired
    private UserDao userDao;

    @Autowired
    private TrainingDao trainingDao;
    
    @Autowired
    private CourtDao courtDao;
    
    @Autowired
    private AuthorizationDao authorizationDao;
    
    private Calendar dateForTesting() {
    	Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 15);
        date.set(Calendar.HOUR_OF_DAY, 1);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

	@Before
	public void setUp() {
		if (isInitialized) return;
		Calendar date = (Calendar) dateForTesting().clone();
		User trainer = new User("user_trainer", "user_trainer@gmail.com", "p", Calendar.getInstance());
        userDao.save(trainer);
        authorizationDao.save(new Authorization(trainer, Role.TRAINER));
		for (int i = 0; i < 5; i++) {
            User user = new User("u2" + (i), "u2" + (i) + "@gmail.com", "p", Calendar.getInstance());
            userDao.save(user);
            authorizationDao.save(new Authorization(user, Role.PLAYER));
            
            trainingDao.save(new Training(courtDao.findOne(i+1), date, trainer, 
            		Collections.<User>emptyList()));
        }
		for (int id = 100; id < 105; id++) {
            courtDao.save(new Court(id));
        }
		isInitialized = true;
		
	}
	
	@Test
	public void testCreateTraining() {
		int initialTrainings = trainingDao.findAll().size();
		String token = restService.loginTrainer();
		
		User trainer = userDao.findByUsernameOrEmail("user_trainer");
		List<Training> trainings = trainingDao.findAll();
		int maxId = 0;
		for (Training t:trainings) {
			if (t.getId()>maxId) maxId = t.getId();
		}
			
		TrainingWrapper trainingWrapper = new TrainingWrapper(maxId+1, courtDao.findOne(1), (Calendar) dateForTesting().clone(), trainer);
		new RestBuilder<Object>(this.restService.URL).path(Uris.TRAININGS).body(trainingWrapper).basicAuth(token, "").post().build();
		
		assertEquals(trainingDao.findAll().size(), initialTrainings + 1);
		
	}
	
	@Test
	public void testDeleteTraining() { //fails
		int initialTrainings = trainingDao.findAll().size();
		String token = restService.loginTrainer();
		Training training = trainingDao.findAll().get(2);
		int trainingId = training.getId();
				
		new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS + "/" + trainingId).basicAuth(token, "").delete().build();
		assertEquals(trainingDao.findAll().size(), initialTrainings - 1);	
	}
	
	@Test
	public void testGetTraining() {
		Training training = trainingDao.findAll().get(0);
		int trainingId = training.getId();
		String token = restService.loginTrainer();
		TrainingWrapper response = new RestBuilder<TrainingWrapper>(this.restService.URL).path(Uris.TRAININGS).pathId(trainingId).basicAuth(token, "").clazz(TrainingWrapper.class).get().build();
		
		assertTrue(response instanceof TrainingWrapper);
		assertEquals(training.getCourt(), response.getCourt());
		assertEquals(training.getStartDate().MILLISECOND, response.getStartDate().MILLISECOND);
		
	}
	
	@Test
	public void testGetTrainings() {
		String token = restService.loginTrainer();
		List<TrainingWrapper> response = Arrays.asList(new RestBuilder<TrainingWrapper[]>(this.restService.URL).path(Uris.TRAININGS).clazz(TrainingWrapper[].class).basicAuth(token, "").get().build());		
		assertEquals(response.size(), trainingDao.findAll().size());
	}
	
	@Test
	public void testAddStudent() { //fails
		String token = restService.loginTrainer();
		Training training = trainingDao.findAll().get(0);
		int trainingId = training.getId();
		
		User user = userDao.findAll().get(0);

		String studentUsername = user.getUsername();
		System.out.println(Uris.TRAININGS + "/" + trainingId + Uris.STUDENT);
		
		new RestBuilder<String>(this.restService.URL).path(Uris.TRAININGS + "/" + trainingId + Uris.STUDENT + "/").clazz(String.class).body(studentUsername).basicAuth(token, "").post().build();
	}
	
	@Test
	public void deleteStudent() { //fails
		String token = restService.loginTrainer();
		User user = userDao.findAll().get(0);
		int userId = user.getId();
		
		Training training = trainingDao.findAll().get(0);
		training.addStudent(user);
		int trainingId = training.getId();
		
		new RestBuilder<String>(this.restService.URL).path(Uris.TRAININGS).pathId(trainingId).path(Uris.STUDENT).pathId(userId).clazz(String.class).basicAuth(token, "").delete().build();
	}
	
}
