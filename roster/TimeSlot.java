package roster;

import java.util.*;

public class TimeSlot {

	public String day;
	public String startTime;
	public String endTime;
	
	public List<StaffMember> allocatedStaff;
		
	public TimeSlot(String d, String s, String e) {
		day = d;
		startTime = s;
		endTime = e;
		allocatedStaff = new ArrayList<>();
	}

	@Override
	public String toString() {
		return String.format("%s: \t%s - %s", day, startTime, endTime);
	}
}
