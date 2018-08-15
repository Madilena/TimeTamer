
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gui extends Application {
	private static String PAUSE = "Pause";
	String newline = "\n";
	JFXPanel panel = new JFXPanel();
	static Scanner keyboardReader = new Scanner(System.in);
	TimeOps time = new TimeOps();

	public void createAndShowGUI(int min) {

		// System.out.println(javafx.scene.text.Font.getFamilies());
		Stage stage = new Stage();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		ProgressBar progress = new ProgressBar();
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(progress.progressProperty(), 0)),
				new KeyFrame(Duration.minutes(min), e -> {
					// do anything you need here on completion...
					System.out.println("Minute over");
				}, new KeyValue(progress.progressProperty(), min)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		StackPane root = new StackPane(progress);
		// Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Tomato Time Tamer");
		stage.show();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	public Text text(String value) {
		Text title = new Text();
		title.setText(value);
		title.setFont(Font.font("Ubuntu Light", 20));
		return title;
	}



	public void start(Stage primaryStage) throws Exception {
//		scheduleTheGui();
//		Label label = new Label("Progress Bar Coming Soon!");

	}

//	public void executeTask() {
//		 Platform.runLater(new Runnable() {
//		      
//	
//	TimerTask task = new TimerTask() {
//		//Platform.runLater(new Runnable(){
//		public void run() {
//			TomatoOps.tomatoes.stream().forEach(x -> createAndShowGUI(x.getWorkTime()));
//			  System.exit(0);
//		}
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}}}
//	 ;)}
//
//	public void scheduleTheGui() {
//
//		Timer timer = new Timer();
//		List<Long> startTimesAsLongs = TomatoOps.tomatoes.stream()
//				.map(x -> x.getTomatoStartTime().getLong(ChronoField.MINUTE_OF_HOUR)).collect(Collectors.toList());
//
//		startTimesAsLongs.stream().forEach(x -> timer.schedule(task, x));
//	}

}
