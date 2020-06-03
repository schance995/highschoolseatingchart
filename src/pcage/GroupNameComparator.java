package pcage;

import java.util.Comparator;
/**
 * Sorts strings of format XY, where X is a numeric char and Y is an alphabetical char.
 * X takes precedence before Y in ordering. Otherwise normal ordering rules apply.
 * @author Skylar Chan
 */
public class GroupNameComparator implements Comparator<String> {

	public int compare(String s1, String s2) {
		//a crazy long regex expression to split alphabetical and numeric characters
		String[] parts1 = s1.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
		String[] parts2 = s2.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
		Integer a = Integer.parseInt(parts1[0]);
		Integer b = Integer.parseInt(parts2[0]);
		if(a.equals(b)) {
			return parts1[1].compareTo(parts2[1]);
		}
		return a.compareTo(b);
	}
	
}