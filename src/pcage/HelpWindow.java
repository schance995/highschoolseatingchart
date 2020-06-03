package pcage;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpWindow extends Stage {
	private TextArea help = new TextArea("Summer Math Seating Program Help\n\n"
			+ "The purpose of this program is to sort students for the summer math program at Poolesville High School."
			+ "\n\nInput must be in the form of an Excel file (.xlsx) and contain one student's data per row. The spreadsheet must follow the specified format."
			+ "\n\nThe first row is not read by the program, so do not put a student's data in the first row."
			+ "\n\nThe first column is the session of the summer math program that the student will be attending."
			+ "Any values in this column besides those starting with 1 or 2 will be assumed to mean that that student is not attending either summer math sessions."
			+ "\n\nThe second is the student's full name in the format \"first, last\", where the first and last names are separated by a comma followed by a space."
			+ "\n\nThe third is the student's gender. Values in this column must start with M or F."
			+ "\n\nThe fourth is the student's magnet program. Values in this column must start with S, G, or H."
			+ "\n\nGender and Magnet Program values are not case sensitive."
			+ "\n\nThe fifth is the student's middle school. Roberto Clemente Middle School MUST be written as \"Roberto W. Clemente\"."
			+ "\n\nNo other columns will be used. Files not following these requirements will be rejected."
			+ "\n\nOnce you have selected or dropped in your file, select one of the two sessions for which you would like to sort. The program will then display sorted data in table form."
			+ "\n\nThis data can be reordered by category by clicking on the boxes at the top of the table and by dragging the columns."
			+ "\nIf you do not like the sorted output, you may resort the data by clicking the \"Sort\" button."
			+ "\n\nWhen you are finished, click \"Save\" to save your file."
			+ "\n\nQuestions? Contact Skylar Chan at schance995@gmail.com"
			+ "\n\nThis program was built by CTRL-S 2019."
			+ "\nProject Manager: Ashwini Thirukkonda"
			+ "\nSystems Analyst: Vincent Chim"
			+ "\nLead Tester: Edward Bian"
			+ "\nLead Programmer: Skylar Chan"
			+ "\nLast updated 6/2/2020 to JavaFX 11");
	public HelpWindow() {
		help.setEditable(false);
		help.setFont(new Font("Times New Roman",20));
		help.setWrapText(true);
		Scene helpScene = new Scene(help, 551, 400);
		setScene(helpScene);
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Help");
		show();
	}
	
}