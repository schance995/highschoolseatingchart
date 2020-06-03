package pcage;
/**The table where sorted students are viewed before saving.
 * @author Skylar Chan
 */
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StudentTable extends TableView {
	//These are the columns of student data in the table view
	TableColumn firstNameCol = new TableColumn("First Name");
	TableColumn lastNameCol = new TableColumn("Last Name");
	TableColumn seatCol = new TableColumn("Seat");
	TableColumn genCol = new TableColumn("Gender");
	TableColumn schoolCol = new TableColumn("Middle School");
	TableColumn houseCol = new TableColumn("Program");
	

	public StudentTable() {
		//set columns
		//property value factory: <Object, field type>("name of field")
		setEditable(false);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Student,String>("first"));
		firstNameCol.setStyle("-fx-font-size:16;");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Student,String>("last"));
		lastNameCol.setStyle("-fx-font-size:16;");
		seatCol.setCellValueFactory(new PropertyValueFactory<Student,String>("location"));
		seatCol.setStyle("-fx-font-size:16;");
		seatCol.setComparator(new GroupNameComparator());
		genCol.setCellValueFactory(new PropertyValueFactory<Student,String>("gender"));
		genCol.setStyle("-fx-font-size:16;");
		schoolCol.setCellValueFactory(new PropertyValueFactory<Student,String>("school"));
		schoolCol.setStyle("-fx-font-size:16;");
		houseCol.setCellValueFactory(new PropertyValueFactory<Student,String>("program"));
		houseCol.setStyle("-fx-font-size:16;");
		getColumns().addAll(lastNameCol, firstNameCol, seatCol, genCol, schoolCol, houseCol);
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getButton()==MouseButton.SECONDARY){
                    //popup a help window
            		HelpWindow hp = new HelpWindow();
            	}
            }
        });
	}
	
	/**
	 * Updates this Table's contents with the specified list of Students.
	 * @param students The list of students to be viewed.
	 */
	
	//update the data based on the arraylist of students, preserving orientation and sorting arrangement of previous chart
	public void update(ArrayList<Student> students) {
		TableColumn sortcolumn = null;
        SortType st = null;
        if (!getSortOrder().isEmpty()) {
            sortcolumn = (TableColumn) getSortOrder().get(0);
            st = sortcolumn.getSortType();
        }
		ObservableList<Student> data = FXCollections.observableArrayList();
		for(Student s:students) {
			data.add(s);
		}
		setItems(data);
		if (sortcolumn!=null) {
            getSortOrder().add(sortcolumn);
            sortcolumn.setSortType(st);
            sortcolumn.setSortable(true); // This performs a sort
        }
	}
	
	
}