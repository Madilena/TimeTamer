
public class Tomato {

	private int tomatoNumber;
	private String goal;
	private int workTime;
	private int breakTime;

	public Tomato(int tomNum, String goal, int workTime, int breakTime) {
		this.tomatoNumber= tomNum;
		this.goal = goal;
		this.workTime = workTime;
		this.breakTime = breakTime;
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

}
