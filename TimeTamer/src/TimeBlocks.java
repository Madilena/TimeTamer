import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.StringJoiner;

public class TimeBlocks {


	public LocalTime timeAtThisInstant() {
		LocalTime localTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
		return localTime;
	}
	
	public LocalTime addWorkTimeBlockToStartTime(LocalTime startTime, int workMinToAdd) {
		return startTime.plusMinutes(workMinToAdd);
	}
	
	public LocalTime addBreakTimeBlockToWorkTimeBlock(LocalTime startTime, int workMinToAdd, int breakMinToAdd) {
		return addWorkTimeBlockToStartTime(startTime, workMinToAdd).plusMinutes(breakMinToAdd);
	}

	public LocalTime makeStringALocalTime(int timeAsInt) {
		String time = convertMinToLocalTimeFormat(timeAsInt);
		LocalTime newTime = LocalTime.parse(time);
		return newTime;
	}

	public String convertMinToLocalTimeFormat(int minAsInt) {
		String min = convertIntToString(minAsInt);
		StringJoiner sj = new StringJoiner("", "00:", ":00");
		sj.add(min);
		return sj.toString();
	}

	public String convertIntToString(int number) {
		return Integer.toString(number);
	}
}
