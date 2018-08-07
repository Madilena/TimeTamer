
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.StringJoiner;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DaemonExecutor;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

public class Main {
	static int numberOfGoals;
	static int workTimeBlock;
	static int breakTimeBlock;
	static String goal;
	static String acceptGoal;
	static Scanner keyboardReader = new Scanner(System.in);

	public static void main(String[] args) throws IOException, InterruptedException {

		printWelcomeMsg();

		System.out.println("How many work-break blocks will you do today?");
		numberOfGoals = keyboardReader.nextInt();
		executeQuestions(numberOfGoals);
		
		System.exit(0);
	}

	public static void executeQuestions(int numberOfThingsToDo) throws IOException, InterruptedException {
		TimeBlocks time = new TimeBlocks();
		LocalTime startTimeForNextIteration = null;

		for (int i = 0; i < numberOfThingsToDo; i++) {
			if (i == 0) {
				startTimeForNextIteration = time.timeAtThisInstant();
			}
			System.out.println("How long do you want work time block number " + i + " in min?");
			workTimeBlock = keyboardReader.nextInt();

			System.out.println("How long do you want your break timeblock number " + i + " in min?");
			breakTimeBlock = keyboardReader.nextInt();
			keyboardReader.nextLine();

			System.out.println("What is your goal for the work time block number " + i + "?");
			goal = keyboardReader.nextLine();

			System.out.println("\nPlease confirm or deny with Yes or No:\nYou want to work for " + workTimeBlock
					+ " min and have a " + breakTimeBlock + " min break.\nYour goal is: " + goal);
			acceptGoal = keyboardReader.nextLine();

			if (!userAcceptsGoal(acceptGoal)) {
				System.out.println("\nDo today what others won't. Do tomorrow what other's can't. Let's do this!");
			}
			startTimeForNextIteration = time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock,
					breakTimeBlock);
			System.out.println("You will finish work at:\n"
					+ time.addWorkTimeBlockToStartTime(startTimeForNextIteration, workTimeBlock)
					+ "\nand you will finish your work and break at:\n"
					+ time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock, breakTimeBlock));
		
			notificationBasedOnOS("Done! Your upcoming task is: "+goal,time.addBreakTimeBlockToWorkTimeBlock(startTimeForNextIteration, workTimeBlock, breakTimeBlock));
		}
	}

	public static boolean userAcceptsGoal(String yesOrNo) {
		String userResponse = removeSpecialChar(yesOrNo.trim());
		boolean userAcceptsContract = false;

		if (userResponse.matches("^y.*$")) {
			userAcceptsContract = true;
		} else if (userResponse.equalsIgnoreCase("sure")) {
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

	public static void notificationBasedOnOS(String message, LocalTime timeMsgIsDisplayed) throws IOException, InterruptedException {
		ProcessBuilder pr = new ProcessBuilder();
		String javaHome = System.getProperty("java.home");
		String userDir = System.getProperty("user.dir");
		Runtime run = Runtime.getRuntime();
		if (isWindows()) {
			Process process = run.exec("msg \"%username%\" \"" + message + "\"");

		    BufferedWriter writer = new BufferedWriter(
		            new OutputStreamWriter(process.getOutputStream()));
		    writer.write("msg \"%username%\" \"" + message + "\"");
		    writer.close();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(
		            process.getInputStream()));
		    
		    String line;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		    reader.close();
			System.out.println("msg \"%username%\" \"" + message + "\"");

		} else if (isUnix()) {
//			pr.command(unixNotification(message, timeMsgIsDisplayed));
//			Process process = pr.start();
//			process.wait();
			
			CommandLine commandLine = new CommandLine(unixNotification(message, timeMsgIsDisplayed));
			System.out.println("the command is:"+commandLine);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(0);
			int exitValue = executor.execute(commandLine);
		//	getProcEnvironment()
		}
		
		
	}

	private static String OS = System.getProperty("os.name").toLowerCase();

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

	public static String unixNotification(String msg, LocalTime timeMsgIsDisplayed) {
		String time = convertLocalTimeToString(timeMsgIsDisplayed);
		String PREFIX = "echo '";
		String TIME_SUFFIX = "' | at " + time + "";
		String ICON_TEXT_MSG = "notify-send -i face-wink \"" + msg + "\"";
		String SPEECH_ALERT = "spd-say \"" + msg + "\"";
String message = "echo 'notify-send -i face-wink \"" + msg + "\"; spd-say \"" + msg + "\"' | at " + time + "";
		StringJoiner joiner = new StringJoiner("; ", PREFIX, TIME_SUFFIX);
		joiner.add(ICON_TEXT_MSG).add(SPEECH_ALERT);
		//System.out.print(joiner.toString());
		//return joiner.toString();
return message;
	}
	
	public static String windowsNotification(String msg) {
		return OS;
		
	}

	public static String convertLocalTimeToString(LocalTime time) {
		return time.toString();
	}

}
