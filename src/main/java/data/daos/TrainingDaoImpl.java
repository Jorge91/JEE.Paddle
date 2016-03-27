package data.daos;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import data.entities.Court;
import data.entities.Reserve;
import data.entities.Training;
import data.entities.User;

public class TrainingDaoImpl implements TrainingDaoExtended {

	@Autowired
	private TrainingDao trainingDao;
	
	@Autowired
    private ReserveDao reserveDao;
    
	
	@Override
	public boolean createTraining(Calendar startDate, User trainer, Court court) {
		Reserve reserve = reserveDao.findByCourtAndDate(court, startDate);
		if (reserve != null) {;
			return false;
		} else {
			Training training = new Training(
					court, startDate, trainer, Collections.<User>emptyList());
			trainingDao.save(training);
			Reserve newReserve = new Reserve(court, trainer, startDate);
			reserveDao.save(newReserve);
			return true;
		}	
	}
	
	@Override
	public boolean deleteTraining(Training training) {
		trainingDao.delete(training);
		Reserve reserve = reserveDao.findByCourtAndDate(training.getCourt(), training.getStartDate());
		reserveDao.delete(reserve);
		return false;
	}

	@Override
	public boolean addStudent(Training training, User student) {
		boolean result = training.addStudent(student);
		if (result) {
			trainingDao.save(training);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean deleteStudent(Training training, User student) {
		training.deleteStudent(student);
		trainingDao.save(training);
		return true;
	}


}
