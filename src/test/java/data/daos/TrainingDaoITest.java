package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Authorization;
import data.entities.Court;
import data.entities.Reserve;
import data.entities.Role;
import data.entities.Training;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingDaoITest {
	
	@Autowired
    private TrainingDao trainingDao;

    @Autowired
    private DaosService daosService;
    
    @Autowired
    private CourtDao courtDao;
    
    @Autowired
    private ReserveDao reserveDao;
    
    @Autowired
    private UserDao userDao;
	
	@Autowired
    private AuthorizationDao authorizationDao;
	
	@Test
	public void testFindByStartDate() {
		
		Calendar dateNow = Calendar.getInstance();
		dateNow.add(Calendar.DAY_OF_YEAR, 1);
		dateNow.set(Calendar.HOUR_OF_DAY, 9);
		dateNow.set(Calendar.MINUTE, 0);
		dateNow.set(Calendar.SECOND, 0);
		dateNow.set(Calendar.MILLISECOND, 0);
		dateNow.add(Calendar.MINUTE, -10);
		
		Calendar dateFinish = (Calendar) dateNow.clone();
		dateFinish.add(Calendar.HOUR_OF_DAY, 1);
		
		assertEquals(trainingDao.findAll().size(), trainingDao.findByDatesBetween(dateNow, dateFinish).size());
		
		Calendar newDateFinish = (Calendar) dateNow.clone();
		newDateFinish.add(Calendar.HOUR_OF_DAY, 1);
		
		assertEquals(0, trainingDao.findByDatesBetween(dateFinish, newDateFinish).size());
	}
	
	@Test
	public void testCreateTraining() {
		int initialData = trainingDao.findAll().size();
		assertEquals(trainingDao.findAll().size(), initialData);
		
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_YEAR, 28);
        startDate.set(Calendar.HOUR_OF_DAY, 0);
		
        User u1 = new User("testCreateTraining", "testCreateTraining" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(u1);
		trainingDao.createTraining(startDate, u1, courtDao.findOne(1));
		
		assertNotEquals((double) trainingDao.findAll().size(), (double) initialData, 0.0);
	}
	
	@Test
	public void testCreateTrainingWhenCourtIsReserved() {
		int initialData = trainingDao.findAll().size();
		
		assertEquals(trainingDao.findAll().size(), initialData);
		
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_YEAR, 12);
        startDate.set(Calendar.HOUR_OF_DAY, 19);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Court court = courtDao.findOne(1);
        User u1 = new User("WhenCourtIsReserved", "WhenCourtIsReserved" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(u1);
        
        reserveDao.save(new Reserve(court, u1, startDate));
		trainingDao.createTraining(startDate, u1, court);
		
		assertEquals(trainingDao.findAll().size(), initialData);
	}
	
	@Test
	public void testCreateTrainingWhenTrainingIsSet() {
		
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_YEAR, 12);
        startDate.set(Calendar.HOUR_OF_DAY, 19);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Court court = courtDao.findOne(1);
        User u1 = new User("WhenTrainingIsSet", "WhenTrainingIsSet" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(u1);
        
		trainingDao.createTraining(startDate, u1, court);
		
		int initialData = trainingDao.findAll().size();
		assertEquals(trainingDao.findAll().size(), initialData);
		
		trainingDao.createTraining(startDate, u1, court);
		assertEquals(trainingDao.findAll().size(), initialData);
	}
	
	@Test
	public void testAddStudent() {
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_YEAR, 3);
        startDate.set(Calendar.HOUR_OF_DAY, 19);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Court court = courtDao.findOne(1);
        
        User u1 = new User("testAddStudent", "testAddStudent" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(u1);
        
		trainingDao.createTraining(startDate, u1, court);
		
		Training training = trainingDao.findAll().get(0);
		
		assertEquals(0, training.getStudents().size());

		trainingDao.addStudent(training, u1);
		
		assertEquals(1, training.getStudents().size());
		
	}
	
	@Test
	public void testDeleteStudent() {
		Training training = trainingDao.findAll().get(0);
		int initial = training.getStudents().size();
		assertEquals(initial, training.getStudents().size());
		
		User user = new User("testDeleteStudent", "testDeleteStudent" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(user);
        authorizationDao.save(new Authorization(user, Role.PLAYER));
		trainingDao.addStudent(training, user);	
		assertEquals(initial + 1, training.getStudents().size());
		
		trainingDao.deleteStudent(training, user);	
		assertEquals(initial, training.getStudents().size());
		
	}
	
	@Test
	public void testDeleteTraining() {
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_YEAR, 26);
        startDate.set(Calendar.HOUR_OF_DAY, 13);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Court court = courtDao.findOne(1);
        
        User u1 = new User("testDeleteTraining", "testDeleteTraining" + "@gmail.com", "p", Calendar.getInstance());
        userDao.save(u1);
        authorizationDao.save(new Authorization(u1, Role.TRAINER));
		
        trainingDao.createTraining(startDate, u1, court);
        
        int initialData = trainingDao.findAll().size();
		assertEquals(trainingDao.findAll().size(), initialData);
        
		Training training = trainingDao.findByDatesBetween(startDate, startDate).get(0);
		trainingDao.delete(training);
		
		assertNotEquals((double) trainingDao.findAll().size(), (double) initialData, 0.0);
	}
	
	

}
