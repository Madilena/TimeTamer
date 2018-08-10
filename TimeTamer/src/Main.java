
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	static List<Integer> listWorkTime =  new ArrayList<>();
	static List<Integer> listBreakTime =  new ArrayList<>();
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// Application.launch(Gui.class, args);
		printWelcomeMsg();
		System.out.println("the time at this instant is " + time.timeAtThisInstant());
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
				System.out.println("\nDo today what others won't. Do tomorrow what other's can't. Let's do this!");
			}
			
			System.out.println("You will finish work at:\n"
					+ time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock)
					+ "\nand you will finish your break at:\n"
					+ time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock, breakTimeBlock));

			addIntsToList(listWorkTime ,workTimeBlock);
			addIntsToList(listBreakTime ,breakTimeBlock);
			
			notificationBasedOnOS("Your work tomato for: " + goal + " is ketchupped",
					time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock));

			notificationBasedOnOS("Your break tomato for: " + goal + " is ketchupped.",
					time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock, breakTimeBlock));
			
			startTimeForNextIteration = time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock,
					breakTimeBlock);
			
		}
		
		listWorkTime.stream().reduce((x1,x2)->x1+x2).ifPresent(p->printWorkTime(p));
		listBreakTime.stream().reduce((x1,x2)->x1+x2).ifPresent(p->printWorkTime(p));
	}
	
	public static List<Integer> listOfWorkMin(int workMin){
		List<Integer> workMinList = new ArrayList<>();
		workMinList.add(workMin);
		return workMinList;
	}
	
	public static void addIntsToList (List<Integer>list, Integer num) {
		list.add(num);
	}
	
	public static void printWorkTime(int min) {
		System.out.println("Your total work time will be: "+min+ " min");
	}
	
	public static void printBreakTime(int min) {
		System.out.println("Your total break time will be: "+min+ " min");
	}
	
	public boolean userClickedPauseButton() {
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
