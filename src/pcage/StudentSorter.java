package pcage;
/**
 * An object to sort the Students from the input file.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StudentSorter {
	private ArrayList<Student> myStudents = new ArrayList<Student>();//temp list for sorting
	private ArrayList<Student> blacklist = new ArrayList<Student>();//another temp for storing exclusions
	private ArrayList<Student> students;//reference in program
	private int exceptions = 0;
	public StudentSorter(ArrayList<Student> s) {
		students = s;
	}
	
	public ArrayList<ArrayList<Student>> sort() {
		blacklist.clear();
		for(Student stew:students) {
			blacklist.add(new Student(stew));
		}
		blacklist.trimToSize();
		ArrayList<ArrayList<Student>> out = new ArrayList<ArrayList<Student>>(5);//make an array to store the days
		
		ArrayList<Group> groups = null;
		for(int i=0;i<5;i++) {
			System.out.println("Day "+i);
			int currentDay=i;
			int tries=0;
			boolean valid = false;
			while(!valid) {
				groups = singleDaySort(currentDay);//sort each day individually
				boolean good = true;
				for(Group g:groups) {
					if(!g.isValid()) {
						good = false;
					}
				}
				//System.out.println(good);
				if(good) {
					ArrayList<Student> today = new ArrayList<Student>();
					for(Student s:myStudents) {
						today.add(new Student(s));
					}
					today.trimToSize();
					out.add(today);
					for(int k=0; k<blacklist.size(); k++) {
						blacklist.get(k).addExclude(myStudents.get(k));
					}
					valid=true;
				} else {
					tries++;
				}
			}
			System.out.println(tries+" tries");
		}
		/*for(Group g:groups) {
			System.out.println(g.isValid());
		}
		for(ArrayList<Student> s:out) {
			System.out.println(s);
		}*/
		//System.out.println(exceptions);
		return out;
	}
	
	/**
	 * Sorts this Sorter's Students according to Mr. Gordon's requirements.
	 * @return an ArrayList of Students with their seats. Does not sort the students in order of their group, as that is managed by the StudentTable.
	 */	
	public ArrayList<Group> singleDaySort(int currentDay) {//for one day
		myStudents.clear();//clear all the students first
		for(Student stew:blacklist) {
			stew.setLocation("");
			myStudents.add(new Student(stew));//create new students so that the original students aren't accidentally copied
		}
		myStudents.trimToSize();
		ArrayList<Group> groups = new ArrayList<Group>();
		try {
			//set clemente limit
			int clemente = 0;
			for(Student s:myStudents) {
				if(s.getSchool().equals("Roberto W. Clemente")) {
					clemente++;
				}
			}
			int clementeLimit;
			if(clemente<myStudents.size()/2) {
				clementeLimit = 2;//default limit
			} else if(clemente>myStudents.size()*3/4) {
				clementeLimit = 6;//i.e, no maximum
			} else {
				clementeLimit = 3;//a higher limit
			}
			//System.out.println("clementeLimit = " + clementeLimit);
			
			//Create appropriate number of Groups, each of a size of four
			for(int group = 0; group<myStudents.size()/4; group++) {//groups are zero indexed!!
				groups.add(new Group(4,clementeLimit,group));
				//System.out.println("Group "+group);
			}
			int lastWholeGroupIndex = groups.size()-1;
			//deal with leftover students
			int leftover = myStudents.size()%4;
			if(leftover==3) {
				groups.add(new Group(3,clementeLimit,groups.size()+1));
				//System.out.println("New group of 3");
				lastWholeGroupIndex++;
			} else {
				//if(leftover==0)
					//System.out.println("wow, no leftovers!");
				for(int i=1; i<=leftover; i++) {
					groups.get(groups.size()-i).setSize(5);
					lastWholeGroupIndex--;
					//System.out.println("Extra group "+i);
				}
			}
			//System.out.println("Group Size "+groups.size());
			//for(Group g:groups) {
				//System.out.println(g.getMaxSize());
			//}

			//split students by gender, shuffle them, and add them back together
			ArrayList<Student> boys = new ArrayList<Student>(), girls = new ArrayList<Student>();
			for(Student s:myStudents) {
				if(s.getGender().equals("boy")) {
					boys.add(s);
				} else {
					girls.add(s);
				}
			}
			Collections.shuffle(boys);
			Collections.shuffle(girls);
			myStudents.clear();
			myStudents.addAll(girls);
			myStudents.addAll(boys);
			
			//sort the students. Currently only works for one day.
			//for(Student s:students) {
				//System.out.println(s);
			//}
			for(Student s:myStudents) {
				//find the next placeable group
				int x = 0;
				while(x<groups.size()&&!groups.get(x).isOpen(s,currentDay)) {//must refine this so that students get placed correctly!
					//System.out.println("Group "+x+"isn't open for "+s);
					x++;
				}
				//give them an id based on their position
				if(x>=groups.size()) {
					x = 0;
					while(x<groups.size()&&groups.get(x).getMaxSize()>=groups.get(x).getSize()) {
						x++;
					}
				}
				if(x>=groups.size()) {
					x=groups.size()-1;
				}//in case x overflows, a student will just be added to the last group
				groups.get(x).add(s);
				//System.out.println(g);
				//drop the student in
			}
			//shuffle the groups and reorder them
			shuffleArray(groups, lastWholeGroupIndex);
			int id=1;
			for(Group g:groups) {
				g.setGroupNum(id++);
				//System.out.println(g.getClemente());
			}
		} catch (Exception e) {
			/*
			e.printStackTrace();
			exceptions++;
			for(Group g:groups) {
				System.out.println(g);
			}*/
		}
		return groups;
	}
	
	private void shuffleArray(ArrayList<Group> groups, int last) {
		Random rnd = ThreadLocalRandom.current();
	    for (int i = last; i > 0; i--) {
	    	int index = rnd.nextInt(i + 1);
	    	Group g = groups.get(index);
	    	groups.set(index, groups.get(i));
	    	groups.set(i, g);
	    }
	}
}