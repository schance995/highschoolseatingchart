package pcage;
/**
 * Represents the scene where the user drops in the Excel file to be read.
 * @author Skylar Chan
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pcage.Program;
import pcage.SheetReader;
import pcage.Student;

public class FileDrop_Scene extends Scene {
    Label label = new Label("Welcome to the Summer Math Seating Program!\nRight click any time for help.\n\nPlease drop in the Excel file you wish to sort.");
    /**
	 * Represents the button to be clicked to advance to the next scene.
	 */
    Button click = new Button("If this is the correct\nspreadsheet, click here");
    Button select = new Button("Or select it here");
    ArrayList<Student> students = new ArrayList<Student>();
    
    /**
	 * Creates the scene where the user drops in the Excel file to be read.
	 * @param bp The border pane that this scene is to be attached to
	 * @param arr The reference to the running Program object's student list
	 */	

    public FileDrop_Scene(BorderPane bp, ArrayList<Student> arr) {
        super(bp, 551, 400);
        label.setTextAlignment(TextAlignment.CENTER);
        students = arr;
        BorderPane myPane = new BorderPane();
        label.setFont(new Font("Times New Roman", 40.0));
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        myPane.setTop(label);
        click.setFont(new Font("Times New Roman", 20.0));
        click.setTextAlignment(TextAlignment.CENTER);
        click.setOnAction(e ->ButtonClicked(e));
        click.setVisible(false);
        select.setFont(new Font("Times New Roman", 20.0));
        select.setTextAlignment(TextAlignment.CENTER);
        select.setOnAction(e ->ButtonClicked(e));
        addEvents();
        bp.setTop(myPane);
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(16);
        vbox.getChildren().addAll(select, click);
        vbox.setAlignment(Pos.CENTER);
        myPane.setCenter(vbox);
    }

    public void ButtonClicked(ActionEvent e) {
        if (e.getSource() == click) {
            Program.switchScene(students);
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select your Excel file");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Microsoft Excel","*.xlsx"));
            this.readAndCheck(fileChooser.showOpenDialog(new Stage()));
        }
    }

	private void addEvents() {
        setOnDragOver(new EventHandler<DragEvent>(){
        	@Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        setOnDragDropped(new EventHandler<DragEvent>(){
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    File file = db.getFiles().get(0);
                    readAndCheck(file);
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    private void readAndCheck(File file) {
        try {
            String filePath = file.getAbsolutePath();
            if (!isExcel(filePath)) {
                popUpError("The file you dropped in is not an Excel file!");
            } else {
                int row = scan(filePath);//try and read the file, saving students along the way
                if (row < 0) {//if success, the file worked
                    students.trimToSize();
                    label.setText("File selected: " + filePath + "\nYou may drop in a different Excel file");
                    label.setFont(new Font("Times New Roman", 24.0));
                    click.setVisible(true);//next scene
                    Program.setFilePath(filePath);
                } else {
                    click.setVisible(false);
                    label.setText("Please drop in the Excel file you wish to sort");
                    label.setFont(new Font("Times New Roman", 40.0));
                    popUpError("Incorrect format at row " + row);
                }
            }
        }
        catch (NullPointerException n) {
            //popUpError("You didn't select your file!");
        }
    }

    private boolean isExcel(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0 && fileName.substring(fileName.lastIndexOf(".") + 1).equals("xlsx")) {
            return true;
        }
        return false;
    }

    private int scan(String filePath) {
        SheetReader sr = null;
        try {
            sr = new SheetReader(filePath);
        }
        catch (IOException e1) {
            //The file is guaranteed to exist since the file path is checked before this 
        }
        int row = 1;
        try {
            sr.next();//ignore first row since it's not a student
        }
        catch (Exception e) {
            return row;
        }
        students.clear();
        while(sr.hasNext()) {//read the spreadsheet
            ++row;
            ArrayList<String> tmp = sr.next();
            if (!tmp.isEmpty()) 
	            try {
	                Student s = new Student(tmp);
	                students.add(s);
	            }
	            catch (Exception e) {
	                return row;//incorrect format
	            }
        }
        return -1;
    }

    private void popUpError(String text) {
        Alert alert = new Alert(AlertType.ERROR, text);
        alert.setHeaderText(null);
        alert.show();
    }

}

