package pcage;
import java.util.ArrayList;

/**
 * Represents a Group of students.
 * @author Skylar Chan
 */
import pcage.Student;

public class Group {
    private int clementeCurrent = 0;
    private int clementeLimit;
    private int femaleCurrent = 0;
    private int maxSize;
    private int groupNum;
    private ArrayList<Student> students;

    public Group(int size, int cLim, int group) {
        students = new ArrayList<Student>(size);
        maxSize = size;
        clementeLimit = cLim;
        groupNum = group;
    }
    /**
	 * Sets the size of this Group object. Also empties this Group.
	 * @param s The number of students that this Group can hold. 
	 */
    public void setSize(int s) {
        maxSize = s;
        students = new ArrayList<Student>(maxSize);
    }

    /**
	 * Returns true if the specified Student can sit in this specified Group. If there are no seats left in this Group, this Student is a Clemente student and the Clemente limit has been reached, or the Student has sat with any of the other students in this Group, returns false.
	 * @param s The Student who could be seated in this Group
	 * @return
	 */
    public boolean isOpen(Student s, int day) {
        if (students.size() >= maxSize) {
            return false;
        }
        if (s.getSchool().equals("Roberto W. Clemente") && clementeCurrent >= clementeLimit) {
            return false;
        }
        //either mathematically set the day number or have user input
        if(day<4||s.getGender()=="M") {
        	for(Student stew:students) {
    			if(stew.hasSatWith(s)||s.hasSatWith(stew)) {return false;}
    		}
        }
        return true;
    }

    /**
	 * @Return a string representation of this group. 
	 */
	public String toString() {
		String out="Group size: "+students.size()+"\t";
		for(Student s:students) {
			out+=s+"\t";
		}
		return out;
	}

	/**
	 * Adds the specified Student to this Group, updating the group's current size and Clemente and female count if applicable. Independent from the isOpen method.
	 * @param s
	 */
	public void add(Student s) {
		if(s.getSchool().equals("Roberto W. Clemente")) {
			clementeCurrent++;
			//System.out.println("Added Clemente!");
		}
		if(s.getGender().equals("F")) {
			femaleCurrent++;
		}
		for(int i=0; i<students.size(); i++) {
			Student stew = students.get(i);
			//stew.addExclude(s);
			//s.addExclude(stew);
		}
		students.add(s);
	}

	/**
	 * Sets this Groups number to the specified integer, and gives a seat to each Student in the group.
	 * @param gn The number to be assigned to this Group.
	 */
	public void setGroupNum(int gn) {
		groupNum = gn;
		for(int i=0; i<maxSize; i++) {
			students.get(i).setLocation(groupNum+Character.toString ((char) (i+65)));//group num plus capital letter
		}
}

	public int getMaxSize() {
		return maxSize;
	}
	
	public int getSize() {
		return students.size(); 
	}
	
	public String getClemente() {//debug purposes
		return clementeCurrent+", limit "+clementeLimit;
	}
	/**
	 * Returns true if the Clemente and female conditions are satisfied
	 * @return
	 */
	public boolean isValid() {
		return clementeCurrent<=clementeLimit&&femaleCurrent!=1;/*
		if(clementeCurrent<=clementeLimit&&femaleCurrent!=1) {
			for(int i=students.size()-1; i>=0; i--) {
				for(int k=i-1; k>=0; k--) {
					if(students.get(i).hasSatWith(students.get(k))||students.get(k).hasSatWith(students.get(i))) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
		//*/
}
	public void addExclusions() {
		for(int i=students.size()-1; i>=0; i--) {
			for(int k=i-1; k>=0; k--) {
				students.get(i).addExclude(students.get(k));
				students.get(k).addExclude(students.get(i));
			}
		}
	}
	public void clear() {//i probably forgot to reset the vars here. oops.
		students.clear();
		clementeCurrent=0;
		femaleCurrent=0;	
	}
	public int getFemales() {
		// TODO Auto-generated method stub
		return femaleCurrent;
	}
}

