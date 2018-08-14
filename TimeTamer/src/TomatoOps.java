import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TomatoOps extends Tomato {
	static TimeOps time = new TimeOps();
	public static List<Tomato> tomatoes = new ArrayList<>();

	public TomatoOps(int tomNum, String goal, int workTime, int breakTime, LocalTime tomatoStartTime,
			LocalTime workEndTime, LocalTime breakEndTime) {
		super(tomNum, goal, workTime, breakTime, tomatoStartTime, workEndTime, breakEndTime);
		// TODO Auto-generated constructor stub
	}

	public static void tomatoStats() {
		// adds up all the work time intervals
		tomatoes.stream().map(x -> x.getWorkTime()).collect(Collectors.toList()).stream().reduce((x1, x2) -> x1 + x2)
				.ifPresent(p -> printWorkTime(p));

		// adds up all the break time intervals
		tomatoes.stream().map(x -> x.getBreakTime()).collect(Collectors.toList()).stream().reduce((x1, x2) -> x1 + x2)
				.ifPresent(p -> printBreakTime(p));

		// prints out all the goals
		tomatoes.stream().forEach(x -> printTomatoGoal(x));

		// prints out all the work time intervals
		tomatoes.stream().forEach(x -> printTomatoWorkTime(x));

		// prints out all the break time intervals
		tomatoes.stream().forEach(x -> printTomatoBreakTime(x));

		// prints out when all the tomatoes start their work
		tomatoes.stream().forEach(x -> printTomatoStartTime(x));

		// prints out when all the tomatoes finish their work
		tomatoes.stream().forEach(x -> printWorkEndTime(x));

		// prints out when all the tomatoes finish their break (when they are ketchupped)
		tomatoes.stream().forEach(x -> printBreakEndTime(x));

	}
 
	public static void changeGoal(String oldGoal, String newGoal) {
		tomatoes.stream().filter(x -> x.getGoal().equals(oldGoal)).forEach(x -> x.setGoal(newGoal));
	}

	public static void changeWorkTime(int oldWorkTime, int newWorkTime) {
		tomatoes.stream().filter(x -> x.getWorkTime() == oldWorkTime).forEach(x -> x.setWorkTime(newWorkTime));
	}
	
	public static void changeWorkTimeOfSpecificTomato(int tomNum, int newWorkTime) {
		tomatoes.stream().filter(x -> x.getTomatoNumber() == tomNum).forEach(x -> x.setWorkTime(newWorkTime));
	}
	
	public static void changeBreakTimeOfSpecificTomato(int tomNum, int newWorkTime) {
		tomatoes.stream().filter(x -> x.getTomatoNumber() == tomNum).forEach(x -> x.setBreakTime(newWorkTime));
	}

	public static void changeBreakTime(int oldBreakTime, int newBreakTime) {
		tomatoes.stream().filter(x -> x.getWorkTime() == oldBreakTime).forEach(x -> x.setWorkTime(newBreakTime));
	}

	public static void changeTomatoEndTimeAfterPause(LocalTime pauseStartTime, LocalTime pauseEndTime) {
		tomatoes.stream().map(x -> x.getBreakEndTime()).collect(Collectors.toList()).stream()
				.forEachOrdered(x -> time.addPauseElapsedTimeToObseleteTomatoEndTime(pauseStartTime, pauseEndTime, x));
	}

	public static void printTomatoGoal(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + "'s goal is " + x.getGoal());
	}

	public static void printTomatoWorkTime(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + "'s work time is: " + x.getWorkTime());
	}

	public static void printTomatoBreakTime(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + "'s break time is: " + x.getBreakTime());
	}

	public static void printTomatoStartTime(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + "'s start time is: " + x.getTomatoStartTime());
	}

	public static void printWorkEndTime(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + " will finish its work time at " + x.getWorkEndTime());
	}

	public static void printBreakEndTime(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + " will finish its break time at " + x.getBreakEndTime());
	}

	public static void printWorkTime(int min) {
		System.out.println("Your total work time will be: " + min + " min");
	}

	public static void printBreakTime(int min) {
		System.out.println("Your total break time will be: " + min + " min");
	}
}
