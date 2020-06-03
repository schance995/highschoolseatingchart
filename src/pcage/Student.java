package pcage;
/**
 * An object to represent a student coming into the Summer Math sessions.
 * @author Skylar
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;

public class Student {
	 private SimpleStringProperty session, first, last, gender, program, school, seat;//simple string property needed to be compatible with table
	 private ArrayList<Student> exclude = new ArrayList<Student>();//all these are data specified in the student class
	 //exclude is the list of students that this student has sat next to
	/** Constructs a student from an array list of Strings.
	 * If this data is invalid, the constructor throws an exception to be caught by the client class.
	 */
	Student(ArrayList<String> arr) throws Exception {
		
		String sess = arr.get(0);//assumes that sessions not 1 or 2 are not showing up
		if ((sess.charAt(0) != '1') && (sess.charAt(0) != '2')){
			sess = "None";
		} else {
			sess = Character.toString((sess.charAt(0)));
		}
		session = new SimpleStringProperty(sess);
		
		String[] parts = arr.get(1).split(", ");
		last = new SimpleStringProperty(parts[0]);
		String[] fir = parts[1].split(" ");
		first = new SimpleStringProperty(fir[0]);
		
		String gen = arr.get(2);//only F,M,f,m allowed
		gender = new SimpleStringProperty(gen);
		if (Character.toLowerCase(gen.charAt(0)) == 'f') {
			gender.set("F");
		} else if(Character.toLowerCase(gen.charAt(0)) == 'm') {
			gender.set("M");
		} else {
			throw new Exception();
		}
			
		String pro = arr.get(3);//Only S,G,H,s,g,h allowed
		program = new SimpleStringProperty();
		if (Character.toLowerCase(pro.charAt(0)) == 's') {
			program.set("SMCS");
		} else if (Character.toLowerCase(pro.charAt(0)) == 'h') {
			program.set("Humanities");
		} else if (Character.toLowerCase(pro.charAt(0)) == 'g') {
			program.set("Global");
		} else {
			throw new Exception();
		}
		
		school = new SimpleStringProperty();
		school.set(arr.get(4));//Roberto W. Clemente must be a precise spelling
		
		seat = new SimpleStringProperty();
	}
	
	public Student(Student other) {//copies fields except for exclusion
		this.session = new SimpleStringProperty(""+other.session.get());
		this.first = new SimpleStringProperty(""+other.first.get());
		this.last = new SimpleStringProperty(""+other.last.get());
		this.gender = new SimpleStringProperty(""+other.gender.get());
		this.program = new SimpleStringProperty(""+other.program.get());
		this.school = new SimpleStringProperty(""+other.school.get());
		this.seat = new SimpleStringProperty(""+other.seat.get());
		for(Student s:other.exclude) {
			this.addExclude(s);
		}
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Student&&obj!=null) {
			Student other = (Student) obj;
			return this.getFirst().equals(other.getFirst())&&this.getLast().equals(other.getLast())&&this.getSchool().equals(other.getSchool())&&this.getProgram().equals(other.getProgram())&&this.getGender().equals(other.getGender());
		}
		return false;
	}

	public String getSession() {return session.get();}
	public String getFirst() {return first.get();}
	public String getLast() {return last.get();}
	public String getGender() {	return gender.get();}
	public String getProgram() {return program.get();}
	public String getSchool() {return school.get();}
	public void setLocation(String s) {seat.set(s);}
	public void addExclude(Student other) {
		exclude.add(other);
	}
	public void clearExclude() {exclude.clear();}

	public String getLocation() {return seat.get();}
	/**
	 * Returns a String representation of a student.
	 * @return A String in the format session, first last, seat, gender, program, school 
	 */
	public String toString() {return session.get()+","+first.get()+" "+last.get()+","+seat.get()+","+gender.get()+","+program.get()+","+school.get();}
	
	/**
	 * Returns true if the specified Student has previously sat with this Student.
	 * @param other the Student that is to be checked with the Student
	 * @return
	 */
	public boolean hasSatWith(Student other) {
		for(Student s:exclude) {
			if(s.equals(other)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Student> getExclude() {
		return exclude;
	}
	
}