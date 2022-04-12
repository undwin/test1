package roster;

import java.util.ArrayList;
import java.util.List;

public class StaffMember {

	private String name;
	private String id;
	private boolean isManager = false;
	private boolean isTrainee = false;
	
	private int shiftCount = 0;
	private List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
	
		
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	
	public boolean isTrainee() {
		return isTrainee;
	}

	public void setTrainee(boolean isTrainee) {
		this.isTrainee = isTrainee;
	}
	
	public int getNumberOfShifts() {		
		return shiftCount;
	}
	
	public void incrementShiftCount() {
		this.shiftCount ++;
	}
	
	public void decrementShiftCount() {
		this.shiftCount --;
	}
	
	public List<TimeSlot> getTimeSlots() {
		return timeSlots;
	}
	
	public void setTimeSlots(List<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots;
	}

}
