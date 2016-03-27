package data.daos;

import java.util.Calendar;
import java.util.List;

import data.entities.Court;
import data.entities.Training;
import data.entities.User;

public interface TrainingDaoExtended {

	public boolean createTraining(Calendar startDate, User trainer, Court court);
	public boolean deleteTraining(Training training);
	public boolean addStudent(Training training, User student);
	public boolean deleteStudent(Training training, User student);
	
}
