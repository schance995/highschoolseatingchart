package pcage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pcage.Program;
import pcage.SheetWriter;
import pcage.Student;
import pcage.StudentSorter;
import pcage.StudentTable;

public class SortedStudent_Scene
extends Scene {
    private ArrayList<Student> students;
    private ArrayList<ArrayList<Student>> output;
    private Button resort;
    private Button save;
    private Button Mon;
    private Button Tue;
    private Button Wed;
    private Button Thu;
    private Button Fri;
    private String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"};
    private StudentSorter sorter;
    private BorderPane display = new BorderPane();
    private StudentTable table;
    private int day = 0;
    private String session;

    public SortedStudent_Scene(BorderPane bp, ArrayList<Student> arr) {
        super(bp, 850.0, 600.0);
        session = arr.get(0).getSession();
        students = arr;
        resort = new Button("Sort");
        save = new Button("Save to Excel");
        Mon = new Button(days[0]);
        Tue = new Button(days[1]);
        Wed = new Button(days[2]);
        Thu = new Button(days[3]);
        Fri = new Button(days[4]);
        Button[] list = {resort, save, Mon,Tue,Wed,Thu,Fri};
		for(Button b:list) {
			b.setFont(new Font("Times New Roman",24));
			b.setOnAction(e->ButtonClicked(e));
		}
        Tooltip tooltip = new Tooltip("Click to sort the students again");
        this.resort.setTooltip(tooltip);
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10.0));
        hbox.setSpacing(8.0);
        hbox.getChildren().addAll(resort, save);
        hbox.setAlignment(Pos.CENTER);
        bp.setBottom(hbox);
        HBox hungrybox = new HBox();//Jiggs!
		hungrybox.setPadding(new Insets(10));
		hungrybox.setSpacing(8);
		hungrybox.getChildren().addAll(Mon,Tue,Wed,Thu,Fri);
		hungrybox.setAlignment(Pos.CENTER);
		display.setTop(hungrybox);
        table = new StudentTable();
        display.setCenter(table);
        bp.setCenter(display);
        int clemente = 0;
        int femaleNum =0 ;
        int femaleClemente = 0;
		for(Student s:students) {
			boolean both = true;
			if(s.getSchool().equals("Roberto W. Clemente")) {
				clemente++;
			} else {
				both = false;
			}
			if(s.getGender().equals("F")) {
				femaleNum++;
			} else {
				both = false;
			}
			if(both) {
				femaleClemente++;
			}
		}
		int clementeLimit;
		if(clemente<students.size()/2) {
			clementeLimit = 2;//default limit
		} else if(clemente>students.size()*3/4) {
			clementeLimit = 6;//i.e, no maximum
		} else {
			clementeLimit = 3;//a higher limit
		}//rerun this calculation, but only once.
		int groupNum = students.size()/4;
		if(students.size()%4==3) {
			groupNum++;
		}
		System.out.println("Details:\nSession: "+session+"\nNumber of students: "+students.size()+"\nNumber of groups: "+groupNum+"\nMax Clemente per group: "+clementeLimit);
		System.out.println("Number of females: "+femaleNum+"\nNumber of Clementes: "+clemente+"\nNumber of female Clementes: "+femaleClemente);
		sorter = new StudentSorter(students);
        output = sorter.sort();
		switchView("Mon");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Students Sorted");
		alert.setHeaderText("The file has been sorted.");
		alert.setContentText("Details:\nSession: "+session+"\nNumber of students: "+students.size()+"\nNumber of groups: "+groupNum+"\nMax Clemente per group: "+clementeLimit);
		alert.show();
    }

    public void ButtonClicked(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == resort) {
            try {
                output = sorter.sort();
                table.update(output.get(day));
            }
            catch (Exception var3_3) {}
        } else if (obj == save) {
            save();
        } else {
        	switchView(((Button)obj).getText());//which day is pressed?
        }
    }


    private void switchView(String button) {
    	//updates the day that is currently being viewed
		switch(button) {
		case "Mon":
			day=0;
			break;
		case "Tue":
			day=1;
			break;
		case "Wed":
			day=2;
			break;
		case "Thu":
			day=3;
			break;
		case "Fri":
			day=4;
			break;
		}
		//System.out.println(day);
		table.update(output.get(day));
		Button[] list = {Mon, Tue, Wed, Thu, Fri};
		for(int i=0; i<list.length; i++) {
			if(list[i].getText().equals(button)) {
				list[i].setStyle("-fx-background-color: yellow;");
			} else {
				list[i].setStyle(null);
			}
		}	
    }
    /**
	 * Saves the text in the output field to an Excel file
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void save() {
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Save Location");
        String initial = Program.getFilePath();//create the file
        initial = initial.substring(0, initial.lastIndexOf("\\")+1);
        fileChooser.setInitialDirectory(new File(initial)); 
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Microsoft Excel", "*.xlsx"));
        fileChooser.setInitialFileName("Summer Math Groups Session "+session);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
            	SheetWriter sw = new SheetWriter(file.getAbsolutePath());
    			for(int i=0;i<5;i++) {//for each day, read the table data and write it to the Excel file
    				switchView(days[i]);
    				ObservableList<TableColumn> temp = table.getColumns();
    				sw.addSheet(days[i]);
    				sw.addRow();
    				for(TableColumn tc:temp) {
    					sw.addCell(tc.getText());
    				}
    				for(int k=0; k<table.getItems().size(); k++) {//add a student's data
    					sw.addRow();
    					for(TableColumn col:temp) {
    						sw.addCell((String) col.getCellData(k));
    					}
    				}
    			}
    			sw.save();
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("File Saved!");
    			alert.setHeaderText("File Saved!");
    			alert.setContentText("The student list has been saved at "+file.getAbsolutePath());
    			System.out.println(file.getAbsolutePath());
    			alert.showAndWait();	
            } catch (IOException e) {
				//Pop up: can't save file!
				//e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("File Not Saved!");
				alert.setContentText("The file could not be saved.\nCheck the file "+file.getAbsolutePath()+" and see if it is open or used by another process.");
				alert.showAndWait();
            }
        }
	}
}