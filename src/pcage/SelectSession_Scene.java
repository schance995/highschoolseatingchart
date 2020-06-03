package pcage;
/**
 * Represents the scene where the user chooses to sort either the first or second session group.
 */
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SelectSession_Scene extends Scene {
	Button sesh1, sesh2;
	Label filePath;
	/**
	 * Represents the students listed in the Excel file. This list's owner is the running Program object. 
	 */
	ArrayList<Student> students;
	/**
	 * Creates the scene where the user chooses which session to sort.
	 * @param bp The border pane that this scene is to be attached to
	 * @param arr The reference to the running Program object's student list
	 */
	public SelectSession_Scene(BorderPane bp, ArrayList<Student> arr) {
		super(bp,551,400);
		students = arr;
		sesh1 = new Button("Sort session 1");
		sesh2 = new Button("Sort session 2");
		sesh1.setFont(new Font("Times New Roman",24));
		sesh2.setFont(new Font("Times New Roman",24));
		filePath = new Label(Program.getFilePath());
		filePath.setFont(new Font("Times New Roman",24));
		sesh1.setOnAction(e -> ButtonClicked(e));
        sesh2.setOnAction(e -> ButtonClicked(e));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().addAll(sesh1,sesh2);
        vbox.setAlignment(Pos.CENTER);
        bp.setCenter(vbox);
	}
	
	public void ButtonClicked(ActionEvent e) {
		Object obj = e.getSource();
		char session = 0;
		if(obj==sesh1) {
			session='1';
		} else if(obj==sesh2) {
			session='2';
		}
		int i=0;
		while(i<students.size()) {
			if(students.get(i).getSession().charAt(0)!=session) {
				students.remove(i);
			} else {
				i++;
			}
		}
		students.trimToSize(); 
		Program.switchScene(students);
	}
	
}