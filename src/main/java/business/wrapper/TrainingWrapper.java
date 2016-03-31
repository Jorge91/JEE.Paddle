package business.wrapper;

import java.util.Calendar;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import data.entities.Court;
import data.entities.User;

public class TrainingWrapper {
	
	private int trainingId;
	private Court court;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar startDate;
	private User trainer;
	private List<User> students;
	
	public TrainingWrapper() {
		
	}
	
	public TrainingWrapper(int trainingId, Court court, Calendar startDate, User trainer) {
		super();
		this.court = court;
		this.startDate = startDate;
		this.trainer = trainer;
		this.trainingId = trainingId;
	}
	public Court getCourt() {
		return court;
	}
	public void setCourt(Court court) {
		this.court = court;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public User getTrainer() {
		return trainer;
	}
	public void setTrainer(User trainer) {
		this.trainer = trainer;
	}
	public List<User> getStudents() {
		return students;
	}
	public void setStudents(List<User> students) {
		this.students = students;
	}

	public int getTrainingId() {
		return trainingId;
	}


	@Override
	public String toString() {
		return "Training [court=" + court + ", startDate=" + startDate
				+ ", trainer=" + trainer + ", students=" + students + "]";
	}
	
	
	

}
