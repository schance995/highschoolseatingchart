package pcage;
/**
 * This is the Summer Math Seating program for our client Mr. Gordon. It sorts freshman going to the summer magnet math session based a number of client-requested criteria to promote inclusivity.
 * @author CTRLS-Team
 */
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Program extends Application {
    
    private static BorderPane currentPane;
    private static Scene currentScene;
    private static Stage stage;
    private static String filePath;
    private static int sceneNum = 0;
    private ArrayList<Student> students = new ArrayList<Student>();
	
	public void start(Stage s) {
		//long time = System.nanoTime();
	    stage = s;
	    stage.setTitle("Summer Math Seating Program");
		stage.getIcons().add(new Image("CTRL-S logo.png"));
		switchScene(students);
		stage.show();
		//long time = System.nanoTime();
		//time = System.nanoTime() - time;
		//System.out.println(time);
	}
	
	/**
	 * Displays the next scene of the Program window. Also passes the reference to the list of students that is to be sorted.
	 * @param arr The reference of the list of students to be sorted
	 */
	public static void switchScene(ArrayList<Student> arr) {
		//long time = System.nanoTime();
		sceneNum++;
		currentPane = new BorderPane();
		switch(sceneNum) {
		case(1):
			currentScene = new FileDrop_Scene(currentPane, arr);
			break;
		case(2):
			currentScene = new SelectSession_Scene(currentPane, arr);
			break;
		case(3):
			currentScene = new SortedStudent_Scene(currentPane, arr);
			break;
		}
		currentScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getButton()==MouseButton.SECONDARY){
                    //popup a help window
            		HelpWindow hp = new HelpWindow();
            	}
            }
        });
		stage.setScene(currentScene);
		//time = System.nanoTime() - time;
		//System.out.println(time);
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }
	
	public static void setFilePath(String fp) {
		filePath = fp;
	}
	public static String getFilePath() {
		return filePath;
	}
	
}