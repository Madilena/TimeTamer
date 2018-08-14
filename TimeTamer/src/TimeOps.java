import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.StringJoiner;

public class TimeOps {

	public LocalTime timeAtThisInstant() {
		LocalTime localTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
		return localTime;
	}
	
	public LocalDateTime todaysDate() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
	}

	public LocalTime addWorkTimeBlockToStartTime(LocalTime startTime, int workMinToAdd) {
		return startTime.plusMinutes(workMinToAdd);
	}

	public LocalTime addBreakTimeBlockToWorkTimeBlock(LocalTime startTime, int workMinToAdd, int breakMinToAdd) {
		return addWorkTimeBlockToStartTime(startTime, workMinToAdd).plusMinutes(breakMinToAdd);
	}

	public LocalTime addPauseElapsedTimeToObseleteTomatoEndTime(LocalTime pauseStartTime, LocalTime pauseEndTime,
			LocalTime tomatoEndTimeToAdjust) {
		LocalTime elapsedTime = findPauseElapsedTime(pauseStartTime, pauseEndTime);
		LocalTime newTime = tomatoEndTimeToAdjust.plus(elapsedTime.getHour(), ChronoUnit.HOURS)
				.minus(elapsedTime.getMinute(), ChronoUnit.MINUTES).minus(elapsedTime.getSecond(), ChronoUnit.SECONDS);
		return newTime;
	}

	public LocalTime findPauseElapsedTime(LocalTime pauseStartTime, LocalTime pauseEndTime) {
		LocalTime timeElapsed = pauseEndTime.minus(pauseStartTime.getHour(), ChronoUnit.HOURS)
				.minus(pauseStartTime.getMinute(), ChronoUnit.MINUTES)
				.minus(pauseStartTime.getSecond(), ChronoUnit.SECONDS);
		return timeElapsed;
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
