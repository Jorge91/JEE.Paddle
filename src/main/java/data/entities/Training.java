package data.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Training {
	
	@Override
	public String toString() {
		return "Training [id=" + id + ", court=" + court + ", startDate="
				+ startDate + ", finishDate=" + finishDate + ", trainer="
				+ trainer + ", students=" + students + "]";
	}

	public final int MAX_STUDENTS = 4;
	public final int CLASS_DURATION_HOURS = 1;
	
	@Id
    @GeneratedValue
    private int id;
	
	@ManyToOne
	@JoinColumn
	private Court court;
	
	private Calendar startDate;
	private Calendar finishDate;
	
	@ManyToOne
	@JoinColumn
	private User trainer;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn
	private List<User> students;

	public Training() {
		
	}
	
	public Training(Court court, Calendar startDate, User trainer, List<User> students) {
		this.court = court;
		this.startDate = startDate;
		this.finishDate = startDate;
		this.finishDate.add(Calendar.HOUR, CLASS_DURATION_HOURS);
		this.trainer = trainer;
		this.students = students;
	}
	
	public boolean addStudent(User user) {
		if ((this.students.size() >= MAX_STUDENTS)||(this.students.contains(user))) {
			return false;
		} else {
			this.students.add(user);
			return true;
		}
	}
	
	public void deleteStudent(User user) {
		if (this.students.contains(user)) {
			this.students.remove(user);
		}
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

	public Calendar getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Calendar finishDate) {
		this.finishDate = finishDate;
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

	public int getId() {
		return id;
	}
	
	
	
	

}
