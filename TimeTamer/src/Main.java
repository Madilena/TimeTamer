
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
import java.util.Timer;
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
	static TimeOps time = new TimeOps();
	static LinkedHashMap<String, List<String>> goalMap = new LinkedHashMap<>();
	static Map<String, Integer> goalMapWithWorkTimeInts = new HashMap<>();
	// static List<Tomato> tomatoes = new ArrayList<>();

	public static void main(String[] args) throws IOException, InterruptedException {

		printStartMsg();

		numberOfGoals = keyboardReader.nextInt();

		executeQuestions(numberOfGoals);
		changeAGoal();
		changeAWorkTime();
		// createWorkEndNotificationForEachTomato();
		// createBreakEndNotificationForEachTomato();

		// Application.launch(Gui.class, args);
		WriteExcel.createExcelSheet();
		System.exit(0);
	}

	public static void scheduleTheGui(String[] args) {
		Timer timer = new Timer();
		// timer.schedule(Application.launch(Gui.class, args), date);
	}

	public static void executeQuestions(int numberOfTomatoes) throws IOException, InterruptedException {
		LocalTime startTimeForThisIteration = null;
		LocalTime startTimeForNextIteration = null;

		for (int i = 1; i < numberOfTomatoes + 1; i++) {
			if (i == 1) {
				startTimeForNextIteration = time.timeAtThisInstant();
			}

			System.out.println("How long do you want work tomato #" + i + " (in min)?");
			expectInt();
			workTimeBlock = keyboardReader.nextInt();

			System.out.println("How long do you want break tomato #" + i + " (in min)?");
			expectInt();
			breakTimeBlock = keyboardReader.nextInt();
			keyboardReader.nextLine();

			System.out.println("What is your goal for tomato #" + i + " ?");
			goal = keyboardReader.nextLine();

			System.out.println("\nPlease confirm or deny with Yes or No:\nYou want to work for " + workTimeBlock
					+ " min and have a " + breakTimeBlock + " min break.\nYour goal is: " + goal);
			acceptGoal = keyboardReader.nextLine();

			if (!userResponse(acceptGoal)) {
				printStartIterationOverMsg();
				i = i - 1;
			}

			if (userResponse(acceptGoal)) {
				startTimeForThisIteration = startTimeForNextIteration;

				LocalTime workEndTime = time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock);

				LocalTime breakEndTime = time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock,
						breakTimeBlock);

				// creates a new tomato object based on user input
				Tomato tom = new Tomato(i, goal, workTimeBlock, breakTimeBlock, startTimeForThisIteration, workEndTime,
						breakEndTime);

				TomatoOps.tomatoes.add(tom);

				printEndTimesMsg(tom);

				createTomatoMaps(tom);

				startTimeForNextIteration = breakEndTime;
			}

		}
		printTomatoMaps();
		TomatoOps.tomatoStats();
	}

	public static void createTomatoMaps(Tomato tom) {
		addGoalAndTimesToMap(
				goalMap, "Tomato " + tom.getTomatoNumber() + ": \"" + tom.getGoal() + "\" starts at "
						+ tom.getTomatoStartTime() + " and finishes at " + tom.getBreakEndTime(),
				tom.getWorkTime(), tom.getBreakTime());

		addGoalAndSingleTimeToMap(goalMapWithWorkTimeInts, tom.getGoal(), tom.getWorkTime());
	}

	public static void createWorkEndNotificationForEachTomato() {
		TomatoOps.tomatoes.stream().forEach(x -> {
			try {
				notifyWorkEndTime(x);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public static void createBreakEndNotificationForEachTomato() {
		TomatoOps.tomatoes.stream().forEach(x -> {
			try {
				notifyBreakEndTime(x);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public static void notifyWorkEndTime(Tomato tom) throws IOException, InterruptedException {
		notificationBasedOnOS(
				"Tomato #" + tom.getTomatoNumber() + " with goal: " + tom.getGoal() + "...is WORK ketchupped",
				tom.getWorkEndTime());
	}

	public static void notifyBreakEndTime(Tomato tom) throws IOException, InterruptedException {
		notificationBasedOnOS(
				"Tomato #" + tom.getTomatoNumber() + " with goal: " + tom.getGoal() + "...is BREAK ketchupped",
				tom.getBreakEndTime());
	}

	public static void changeAGoal() {
		System.out.println("Do you need to change a goal?");
		acceptGoal = keyboardReader.nextLine();
		if (userResponse(acceptGoal)) {
			System.out.println("What goal do you want to change?");
			String oldGoal = keyboardReader.nextLine();
			System.out.println("What do you want to change it to?");
			String newGoal = keyboardReader.nextLine();
			TomatoOps.changeGoal(oldGoal, newGoal);
			TomatoOps.tomatoes.stream().forEach(x -> TomatoOps.printTomatoGoal(x));
		}
	}

	public static void changeAWorkTime() {
		System.out.println("Do you need to change a work time?");
		acceptGoal = keyboardReader.nextLine();
		if (userResponse(acceptGoal)) {
			System.out.println("What tomato number needs its work time changed?");
			expectInt();
			int num = keyboardReader.nextInt();
			filterTomatoByNumber(TomatoOps.tomatoes, isEqualTo.apply(num));
			System.out.println("What work time does it need?");
			expectInt();
			int newWorkMin = keyboardReader.nextInt();
			TomatoOps.changeWorkTimeOfSpecificTomato(num, newWorkMin);
			TomatoOps.tomatoes.stream().forEach(x -> TomatoOps.printTomatoWorkTime(x));
		}
	}

	public static Tomato filterTomatoByGoal(List<Tomato> tom, Predicate<String> predicate) {
		return tom.stream().filter(x -> predicate.test(x.getGoal())).findFirst().get();
	}

	public static Tomato filterTomatoByNumber(List<Tomato> tom, Predicate<Integer> predicate) {
		return tom.stream().filter(x -> predicate.test(x.getTomatoNumber())).findFirst().get();
	}

	public static <K, V> Map<K, V> filterMapByValue(Map<K, V> map, Predicate<V> predicate) {
		return map.entrySet().stream().filter(x -> predicate.test(x.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public static <K, V> Map<K, V> filterMapByKey(Map<K, V> map, Predicate<K> predicate) {
		return map.entrySet().stream().filter(x -> predicate.test(x.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public static void deletePriorNotifyJobsAndCreateNewJobsAfterPause(LocalTime pauseStartTime, LocalTime pauseEndTime)
			throws IOException {
		deleteAllAtNotifcations();
		TomatoOps.changeTomatoEndTimeAfterPause(pauseStartTime, pauseEndTime);
		createWorkEndNotificationForEachTomato();
		createBreakEndNotificationForEachTomato();
	}

	public static <V> Map<String, V> changeKeyInMap(Map<String, V> map, String keyText, String newText) {
		Map<String, V> newMap = filterMapByKey(map, matchString.apply(keyText));
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

	static Function<Integer, Predicate<Integer>> isGreaterThan = controlNum -> candidate -> candidate > controlNum;
	static Function<Integer, Predicate<Integer>> isLessThan = controlNum -> candidate -> candidate < controlNum;
	static Function<Integer, Predicate<Integer>> isEqualTo = controlNum -> candidate -> candidate == controlNum;

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

	public static void printEndTimesMsg(Tomato tom) {
		System.out.println("You will finish work at:\n" + tom.getWorkEndTime()
				+ "\nand you will finish your break at:\n" + tom.getBreakEndTime());
	}

	public static void printTomatoMaps() {
		goalMap.forEach((K, Y) -> {
			System.out.println(K + " " + Y);
		});

		// filters map by work time values that are greater than 25 minutes
		Map<String, Integer> persimmons = filterMapByValue(goalMapWithWorkTimeInts, isGreaterThan.apply(25));

		// filters map by work time values that are less than 25 minutes
		Map<String, Integer> greenTomatos = filterMapByValue(goalMapWithWorkTimeInts, isLessThan.apply(25));

		// filters map by work time values that are equal to 25 minutes
		Map<String, Integer> ripeTomatos = filterMapByValue(goalMapWithWorkTimeInts, isEqualTo.apply(25));

		System.out.println("Persimmons (goal time > 25 min):\n" + persimmons);
		System.out.println("Green Tomatos (goal time < 25 min):\n" + greenTomatos);
		System.out.println("Ripe Tomatos (goal time = 25 min):\n" + ripeTomatos);
	}

	public static boolean userClickedPauseButton() {
		// TODO: need to build gui so that clicking button action returns boolean val
		return true;
	}

	public static void deleteAllAtNotifcations() throws IOException {
		// if (userClickedPauseButton()) {
		ProcessBuilder pr = new ProcessBuilder();
		pr.directory();
		pr.command("/bin/bash", "-c", " for i in `atq | awk '{print $1}'`;do atrm $i;done");
		pr.start();
		// }
	}

	public static boolean userResponse(String yesOrNo) {
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

	public static void notificationAfterPause(String message, LocalTime timeMsgIsDisplayed)
			throws IOException, InterruptedException {
		// LocalTime

		notificationBasedOnOS(message, timeMsgIsDisplayed);
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

	public static void expectInt() {
		while (!keyboardReader.hasNextInt()) {
			keyboardReader.next();
			enterIntPls();
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

	public static void printStartMsg() {
		System.out.println("Don't let your dreams be dreams! Let's plan out your goals for today!\n");
		System.out.println("The time at this instant is " + time.timeAtThisInstant());
		System.out.println("How many tomatoes would you like?");
	}

	public static void printStartIterationOverMsg() {
		System.out.println("\nDo today what others won't. Do tomorrow what other's can't. Let's start over!");
	}

	public static void enterIntPls() {
		System.out.println("\nPlease enter an integer value for minutes: ");
	}

	public static String windowsNotification(String msg) {
		return OS;
	}

	public static String convertLocalTimeToString(LocalTime time) {
		return time.toString();
	}

}
