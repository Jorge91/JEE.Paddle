package web;

public class TrainingForm {
	private int court;
	private String startDate;
	private String trainerName;
	
	public TrainingForm() {}
	
	public TrainingForm(int court, String startDate, String trainerName) {
		super();
		this.court = court;
		this.startDate = startDate;
		this.trainerName = trainerName;
	}
	public int getCourt() {
		return court;
	}
	public void setCourt(int court) {
		this.court = court;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	@Override
	public String toString() {
		return "TrainingForm [courtId=" + court + ", startDate=" + startDate
				+ ", trainerName=" + trainerName + "]";
	}
	
	
	
}
