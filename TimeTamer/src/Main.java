
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main {
	static int numberOfGoals;
	static int workTimeBlock;
	static int breakTimeBlock;
	static String goal;
	static String nextGoal;
	static String acceptGoal;
	static Scanner keyboardReader = new Scanner(System.in);
	private static String OS = System.getProperty("os.name").toLowerCase();
	static TimeBlocks time = new TimeBlocks();
	static LinkedHashMap<String, List<String>> goalMap = new LinkedHashMap<>();
	static Map<String, Integer> goalMapWithTimeInts = new HashMap<>();
	static List<Integer> listWorkTime = new ArrayList<>();
	static List<Integer> listBreakTime = new ArrayList<>();

	public static void main(String[] args) throws IOException, InterruptedException {
		// Application.launch(Gui.class, args);
		printWelcomeMsg();
		System.out.println("The time at this instant is " + time.timeAtThisInstant());
		System.out.println("How many tomatos would you like?");
		numberOfGoals = keyboardReader.nextInt();

		executeQuestions(numberOfGoals);

		System.exit(0);
	}

	public static void executeQuestions(int numberOfTomatoes) throws IOException, InterruptedException {

		LocalTime startTimeForNextIteration = null;

		for (int i = 1; i < numberOfTomatoes + 1; i++) {
			if (i == 1) {
				startTimeForNextIteration = time.timeAtThisInstant();
			}

			System.out.println("How long do you want work tomato #" + i + " (in min)?");
			workTimeBlock = keyboardReader.nextInt();

			System.out.println("How long do you want break tomato #" + i + " (in min)?");
			breakTimeBlock = keyboardReader.nextInt();
			keyboardReader.nextLine();

			System.out.println("What is your goal for tomato #" + i + " ?");
			goal = keyboardReader.nextLine();

			System.out.println("\nPlease confirm or deny with Yes or No:\nYou want to work for " + workTimeBlock
					+ " min and have a " + breakTimeBlock + " min break.\nYour goal is: " + goal);
			acceptGoal = keyboardReader.nextLine();

			if (!userAcceptsGoal(acceptGoal)) {
				System.out.println("\nDo today what others won't. Do tomorrow what other's can't. Let's start over!");
				i = i - 1;
			}

			if (userAcceptsGoal(acceptGoal)) {
				System.out.println("You will finish work at:\n"
						+ time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock)
						+ "\nand you will finish your break at:\n" + time.addBreakTimeBlockToWorkTimeBlock(
								startTimeForNextIteration, workTimeBlock, breakTimeBlock));

				addIntsToList(listWorkTime, workTimeBlock);
				addIntsToList(listBreakTime, breakTimeBlock);

				notificationBasedOnOS("Your work tomato for: " + goal + " is ketchupped",
						time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock));

				notificationBasedOnOS("Your break tomato for: " + goal + " is ketchupped.", time
						.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock, breakTimeBlock));

				startTimeForNextIteration = time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration,
						workTimeBlock, breakTimeBlock);

				addGoalAndTimesToMap(goalMap,
						"Tomato " + i + ": \"" + goal + "\" finishes at " + startTimeForNextIteration, workTimeBlock,
						breakTimeBlock);
			}
			
			addGoalAndSingleTimeToMap(goalMapWithTimeInts, goal, workTimeBlock);

		}
		
		listWorkTime.stream().reduce((x1, x2) -> x1 + x2).ifPresent(p -> printWorkTime(p));
		listBreakTime.stream().reduce((x1, x2) -> x1 + x2).ifPresent(p -> printBreakTime(p));

		goalMap.forEach((K, Y) -> {
			System.out.println(K + " " + Y);
		});

//https://www.mkyong.com/java8/java-8-streams-map-examples/
		Map<String, Integer> persimmons = filterByValue(goalMapWithTimeInts, m -> m > 25);
		Map<String, Integer> greenTomatos = filterByValue(goalMapWithTimeInts, m -> m < 25);
		Map<String, Integer> ripeTomatos = filterByValue(goalMapWithTimeInts, m -> m == 25);
		System.out.println("Persimmons (> 25 min):\n" + persimmons);
		System.out.println("Green Tomatos (< 25 min):\n" + greenTomatos);
		System.out.println("Ripe Tomatos (25 min):\n" + ripeTomatos);

	}

	public static <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> predicate) {
		return map.entrySet().stream().filter(x -> predicate.test(x.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	static Function<Integer, Predicate<Integer>> isGreaterThan = (Integer pivot) -> {
		Predicate<Integer> isGreaterThanPivot = (Integer candidate) -> {
			return candidate > pivot;
		};
		return isGreaterThanPivot;
	};

	public static void addGoalAndTimesToMap(Map<String, List<String>> map, String goal, Integer workTime,
			Integer breakTime) {
		List<String> listOfWorkAndBreakTimes = new ArrayList<>();
		listOfWorkAndBreakTimes.add("work mins: " + Integer.toString(workTime));
		listOfWorkAndBreakTimes.add("break mins: " + Integer.toString(+breakTime));
		map.put(goal, listOfWorkAndBreakTimes);
	}

	public static void addGoalAndSingleTimeToMap(Map<String, Integer> map, String goal, Integer time) {
		map.put(goal, time);
	}

	public static void printGoalMap(Map<String, List<String>> map) {
		System.out.println("Your goal itinerary:\n" + map);
	}

	public static void addIntsToList(List<Integer> list, Integer num) {
		list.add(num);
	}

	public static void printWorkTime(int min) {
		System.out.println("Your total work time will be: " + min + " min");
	}

	public static void printBreakTime(int min) {
		System.out.println("Your total break time will be: " + min + " min");
	}

	public boolean userClickedPauseButton() {
		// TODO: need to build gui so that clicking button action returns boolean val
		return true;
	}

	public void deleteAllAtNotifcations() throws IOException {
		if (userClickedPauseButton()) {
			ProcessBuilder pr = new ProcessBuilder();
			pr.directory();
			pr.command("/bin/bash", "-c", " for i in `atq | awk '{print $1}'`;do atrm $i;done");
			pr.start();
		}
	}

	public static boolean userAcceptsGoal(String yesOrNo) {
		String userResponse = removeSpecialChar(yesOrNo.trim());
		boolean userAcceptsContract = false;

		if (userResponse.matches("^y.*$")) {
			userAcceptsContract = true;
		} else if (userResponse.equalsIgnoreCase("sure")) {
			userAcceptsContract = true;
		} else if (userResponse.equalsIgnoreCase("affirmative")) {
			userAcceptsContract = true;
		} else if (userResponse.equalsIgnoreCase("10-4")) {
			userAcceptsContract = true;
		} else if (userResponse.equalsIgnoreCase("okeydokey")) {
			userAcceptsContract = true;
		} else if (userResponse.equalsIgnoreCase("fine")) {
			userAcceptsContract = true;
		} else if (userResponse.equalsIgnoreCase("but I already did something today")) {
			userAcceptsContract = true;
		} else {
			userAcceptsContract = false;
		}
		return userAcceptsContract;
	}

	public static void notificationBasedOnOS(String message, LocalTime timeMsgIsDisplayed)
			throws IOException, InterruptedException {

		if (isUnix()) {
			ProcessBuilder pr = new ProcessBuilder();
			pr.directory();
			pr.command("/bin/bash", "-c", "echo 'notify-send -i face-wink \"" + message + "\"; spd-say \"" + message
					+ "\" -r -15' | at " + timeMsgIsDisplayed + "");
			pr.start();

		} else if (isWindows()) {

			System.out.println("The desktop notification program, as of 8/8/18, does not work on Windows");
			Runtime run = Runtime.getRuntime();
			Process process = run.exec("msg \"%username%\" \"" + message + "\"");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			writer.write("msg \"%username%\" \"" + message + "\"");
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
			System.out.println("msg \"%username%\" \"" + message + "\"");

		}

	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isWindows() {
		return (OS.startsWith("win"));
	}

	public static String removeSpecialChar(String msg) {
		String result = msg.replaceAll("[-+.^:,!]", "");
		return result;
	}

	public static void printWelcomeMsg() {
		System.out.println("Don't let your dreams be dreams! Let's plan out your goals for today!\n");
	}

	public static String windowsNotification(String msg) {
		return OS;
	}

	public static String convertLocalTimeToString(LocalTime time) {
		return time.toString();
	}

}
