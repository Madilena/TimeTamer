
import com.sun.javafx.runtime.async.AsyncOperationListener;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Gui   {
//	private static String PAUSE = "Pause";
//	String newline = "\n";
//	JFXPanel panel = new JFXPanel();
//
//
//	public void createAndShowGUI(Stage stage) {
//		// System.out.println(javafx.scene.text.Font.getFamilies());
//
//		Font fancyFont = new Font("Ubuntu Light", 16);
//		GridPane grid = new GridPane();
//		grid.setAlignment(Pos.CENTER);
//		grid.setHgap(10);
//		grid.setVgap(10);
//		grid.setPadding(new Insets(25, 25, 25, 25));
//
//		final Float[] values = new Float[] { -1.0f, 0f, 0.6f, 1.0f };
//		final Label[] labels = new Label[values.length];
//		final ProgressBar[] pbs = new ProgressBar[values.length];
//		final ProgressIndicator[] pins = new ProgressIndicator[values.length];
//		final HBox hbs[] = new HBox[values.length];
//
//		Group root = new Group();
//		Scene scene = new Scene(root, 300, 150);
//		scene.getStylesheets().add("progresssample/Style.css");
//		stage.setScene(scene);
//		stage.setTitle("Progress Controls");
//
//		for (int i = 0; i < values.length; i++) {
//			final Label label = labels[i] = new Label();
//			label.setText("progress:" + values[i]);
//
//			final ProgressBar pb = pbs[i] = new ProgressBar();
//			pb.setProgress(values[i]);
//
//			final ProgressIndicator pin = pins[i] = new ProgressIndicator();
//			pin.setProgress(values[i]);
//			final HBox hb = hbs[i] = new HBox();
//			hb.setSpacing(5);
//			hb.setAlignment(Pos.CENTER);
//			hb.getChildren().addAll(label, pb, pin);
//		}
//
//		final VBox vb = new VBox();
//		vb.setSpacing(5);
//		vb.getChildren().addAll(hbs);
//		scene.setRoot(vb);
//		stage.show();
//		Application.launch();
//		// primaryStage.setScene(scene);
//
////		doInBackground();
////		JTextField txtInput = new JTextField("enter values here", 20);
////		txtInput.setFont(fancyFont);
////		String input = txtInput.getText();
////		txtInput.addActionListener(this);
////		txtInput.setColumns(20);
////		
////		JTextArea txtArea = new JTextArea(5, 5);
////		txtArea.setSize(50, 50);
////		txtArea.setFont(fancyFont);
////		txtArea.setWrapStyleWord(true);
////		txtArea.setText("Values go here");
////		
////		JFrame f = new JFrame("Time Tamer");
////		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		f.setSize(300,300);
////		f.setVisible(true);
////		// f.add(txtInput);
////		f.add(txtArea);
//// 
////		
////		JButton pauseButton = new javax.swing.JButton();
////		f.add(pauseButton);
//
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		primaryStage.setTitle("My First JavaFX App");
//
//		Label label = new Label("Hello World, JavaFX !");
//		Scene scene = new Scene(label, 400, 200);
//		primaryStage.setScene(scene);
//
//		primaryStage.show();
//
//	}

}
