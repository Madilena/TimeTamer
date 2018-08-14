import java.time.LocalTime;

public class Tomato {

	private int tomatoNumber;
	private String goal;
	private int workTime;
	private int breakTime;
	private LocalTime tomatoStartTime;
	private LocalTime workEndTime;
	private LocalTime breakEndTime;

	public Tomato(int tomNum, String goal, int workTime, int breakTime, LocalTime tomatoStartTime, LocalTime workEndTime, LocalTime breakEndTime) {
		this.tomatoNumber= tomNum;
		this.goal = goal;
		this.workTime = workTime;
		this.breakTime = breakTime;
		this.tomatoStartTime = tomatoStartTime;
		this.workEndTime = workEndTime;
		this.breakEndTime = breakEndTime;
	}

	public int getTomatoNumber() {
		return tomatoNumber;
	}

	public void setTomatoNumber(int tomatoNumber) {
		this.tomatoNumber = tomatoNumber;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public int getWorkTime() {
		return workTime;
	}

	public void setWorkTime(int workMin) {
		this.workTime = workMin;
	}

	public int getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(int breakMin) {
		this.breakTime = breakMin;
	}

	public LocalTime getTomatoStartTime() {
		return tomatoStartTime;
	}

	public void setTomatoStartTime(LocalTime tomatoStartTime) {
		this.tomatoStartTime = tomatoStartTime;
	}

	public LocalTime getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(LocalTime workEndTime) {
		this.workEndTime = workEndTime;
	}

	public LocalTime getBreakEndTime() {
		return breakEndTime;
	}

	public void setBreakEndTime(LocalTime breakEndTime) {
		this.breakEndTime = breakEndTime;
	}

}
