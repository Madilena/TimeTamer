
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
	static Stage stage;
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
	static Map<String, Integer> goalMapWithWorkTimeInts = new HashMap<>();
	static List<Integer> listWorkTime = new ArrayList<>();
	static List<Integer> listBreakTime = new ArrayList<>();
	static List<Tomato> tomatoes = new ArrayList<>();

	public static void main(String[] args) throws IOException, InterruptedException {
		// Gui gui = new Gui();

		printWelcomeMsg();
		System.out.println("The time at this instant is " + time.timeAtThisInstant());
		System.out.println("How many tomatos would you like?");
		numberOfGoals = keyboardReader.nextInt();

		executeQuestions(numberOfGoals);

		changeAGoal();
		// Application.launch(Gui.class, args);

		System.exit(0);
	}

	public static void executeQuestions(int numberOfTomatoes) throws IOException, InterruptedException {
		LocalTime startTimeForThisIteration = null;
		LocalTime startTimeForNextIteration = null;

		for (int i = 1; i < numberOfTomatoes + 1; i++) {
			if (i == 1) {
				startTimeForNextIteration = time.timeAtThisInstant();
			}

			System.out.println("How long do you want work tomato #" + i + " (in min)?");
			while (!keyboardReader.hasNextInt()) {
				keyboardReader.next();
				System.out.print("Please enter an integer: ");
			}
			workTimeBlock = keyboardReader.nextInt();

			System.out.println("How long do you want break tomato #" + i + " (in min)?");
			while (!keyboardReader.hasNextInt()) {
				keyboardReader.next();
				System.out.print("Please enter an integer: ");
			}
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
				startTimeForThisIteration = startTimeForNextIteration;

				LocalTime workEndTime = time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock);

				LocalTime breakEndTime = time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock,
						breakTimeBlock);

				Tomato tom = new Tomato(i, goal, workTimeBlock, breakTimeBlock, startTimeForThisIteration, workEndTime,
						breakEndTime);
				tomatoes.add(tom);

				System.out.println("You will finish work at:\n" + workEndTime + "\nand you will finish your break at:\n"
						+ breakEndTime);

				addIntsToList(listWorkTime, tom.getWorkTime());
				addIntsToList(listBreakTime, tom.getBreakTime());

				// notificationBasedOnOS("Your work tomato for: " + goal + " is ketchupped",
				// time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock));
				//
				// notificationBasedOnOS("Your break tomato for: " + goal + " is ketchupped.",
				// time
				// .addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock,
				// breakTimeBlock));

				startTimeForNextIteration = breakEndTime;

				addGoalAndTimesToMap(goalMap,
						"Tomato " + i + ": \"" + tom.getGoal() + "\" starts at " + startTimeForThisIteration
								+ " and finishes at " + startTimeForNextIteration,
						tom.getWorkTime(), tom.getBreakTime());

				addGoalAndSingleTimeToMap(goalMapWithWorkTimeInts, tom.getGoal(), tom.getWorkTime());

			}

		}

		// adds up all the work times
		tomatoes.stream().map(x -> x.getWorkTime()).collect(Collectors.toList()).stream().reduce((x1, x2) -> x1 + x2)
				.ifPresent(p -> printWorkTime(p));

		// adds up all the break times
		tomatoes.stream().map(x -> x.getBreakTime()).collect(Collectors.toList()).stream().reduce((x1, x2) -> x1 + x2)
				.ifPresent(p -> printBreakTime(p));

		// prints out all the goals
		tomatoes.stream().forEach(x -> printTomatoGoal(x));

		// prints out all the work times
		tomatoes.stream().forEach(x -> printTomatoWorkTime(x));

		// prints out all the break times
		tomatoes.stream().forEach(x -> printTomatoBreakTime(x));

		// prints when all the tomatoes finish their work
		tomatoes.stream().forEach(x -> printWorkEndTime(x));

		// prints when all the tomatoes finish their break (when they are ketchupped)
		tomatoes.stream().forEach(x -> printBreakEndTime(x));

		goalMap.forEach((K, Y) -> {
			System.out.println(K + " " + Y);
		});

		Map<String, Integer> persimmons = filterByValue(goalMapWithWorkTimeInts, isGreaterThan.apply(25));
		Map<String, Integer> greenTomatos = filterByValue(goalMapWithWorkTimeInts, isLessThan.apply(25));
		Map<String, Integer> ripeTomatos = filterByValue(goalMapWithWorkTimeInts, isEqualTo.apply(25));

		System.out.println("Persimmons (goal time > 25 min):\n" + persimmons);
		System.out.println("Green Tomatos (goal time < 25 min):\n" + greenTomatos);
		System.out.println("Ripe Tomatos (goal time = 25 min):\n" + ripeTomatos);

	}

	public static void changeAGoal() {
		System.out.println("Do you need to change a goal?");
		acceptGoal = keyboardReader.nextLine();
		if (userAcceptsGoal(acceptGoal)) {
			System.out.println("What goal do you want to change");
			String oldGoal = keyboardReader.nextLine();
			System.out.println("What do you want to change it to");
			String newGoal = keyboardReader.nextLine();
			changeGoal(oldGoal, newGoal);
			tomatoes.stream().forEach(x -> printTomatoGoal(x));

		}

	}

	public static <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> predicate) {
		return map.entrySet().stream().filter(x -> predicate.test(x.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public static <K, V> Map<K, V> filterByKey(Map<K, V> map, Predicate<K> predicate) {
		return map.entrySet().stream().filter(x -> predicate.test(x.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public static void changeGoal(String oldGoal, String newGoal) {
		tomatoes.stream().filter(x -> x.getGoal().equals(oldGoal)).forEach(x -> x.setGoal(newGoal));
	}

	public static void changeWorkTime(int oldWorkTime, int newWorkTime) {
		tomatoes.stream().filter(x -> x.getWorkTime() == oldWorkTime).forEach(x -> x.setWorkTime(newWorkTime));
	}

	public static void changeBreakTime(int oldBreakTime, int newBreakTime) {
		tomatoes.stream().filter(x -> x.getWorkTime() == oldBreakTime).forEach(x -> x.setWorkTime(newBreakTime));
	}

	public static <V> Map<String, V> changeKeyInMap(Map<String, V> map, String keyText, String newText) {
		Map<String, V> newMap = filterByKey(map, matchString.apply(keyText));
		map.remove(keyText);
		newMap.put(newText, newMap.remove(keyText));
		map.putAll(newMap);
		return map;
	}

	public static String changeString(String oldText, String newText) {
		return oldText.replace(oldText, newText);
	}

	static Function<String, Predicate<String>> matchString = controlString -> candidate -> candidate
			.contains(controlString);

	static Function<Integer, Predicate<Integer>> isGreaterThan = greaterThanNum -> candidate -> candidate > greaterThanNum;
	static Function<Integer, Predicate<Integer>> isLessThan = lessThanNum -> candidate -> candidate < lessThanNum;
	static Function<Integer, Predicate<Integer>> isEqualTo = equalToNum -> candidate -> candidate == equalToNum;

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

	public static void printTomatoGoal(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + "'s goal is " + x.getGoal());
	}

	public static void printTomatoWorkTime(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + "'s work time is: " + x.getWorkTime());
	}

	public static void printTomatoBreakTime(Tomato x) {
		System.out.println("Tomato " + x.getTomatoNumber() + "'s break time is: " + x.getBreakTime());
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
