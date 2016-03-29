package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Court;
import data.entities.Training;

@Controller
public class TrainingController {
	
	private TrainingDao trainingDao;
	private UserDao userDao;
	
	@Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
	
	@Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
	
	private TrainingWrapper trainingWrapperFromTraining(Training training) {
		return new TrainingWrapper(training.getCourt(), training.getStartDate(), training.getTrainer());
		
	}
	
	public boolean createTraining(TrainingWrapper trainingWrapper) {
		return trainingDao.createTraining(
				trainingWrapper.getStartDate(), 
				userDao.findByUsernameOrEmail(trainingWrapper.getTrainer().getUsername()), 
				trainingWrapper.getCourt()
				);
	}
	
	public boolean deleteTraining(int trainingId) {
		return trainingDao.deleteTraining(trainingDao.getOne(trainingId));
		
	}
	
	public boolean addStudent(String username, int trainingId) {
		return trainingDao.addStudent(
				trainingDao.getOne(trainingId), 
				userDao.findByUsernameOrEmail(username)
				);
	}
	
	public boolean deleteStudent(String username, int trainingId) {
		return trainingDao.deleteStudent(
				trainingDao.getOne(trainingId), 
				userDao.findByUsernameOrEmail(username)
				);
	}
	
	public TrainingWrapper getTraining(int trainingId) {
	       return trainingWrapperFromTraining(trainingDao.findOne(trainingId));
    }
	
	public List<TrainingWrapper> getTrainings() {
       List<Training> trainings = trainingDao.findAll();
       List<TrainingWrapper> trainingWrappers = new ArrayList<TrainingWrapper>();
       for (Training t:trainings) {
    	   trainingWrappers.add(trainingWrapperFromTraining(t));
       }
       return trainingWrappers;
    }

}
