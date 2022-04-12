package roster;

import java.util.ArrayList;
import java.util.List;

public class StaffContainer {
	
	protected List<StaffMember> staff;
	
	public StaffContainer() {
		staff = new ArrayList<StaffMember>();
	}
	
	/**
	 * Add a new staff member to the StaffContainer
	 * @param id The new staff member's id
	 * @param name The new staff member's name 
	 */
	public void addStaff(String id, String name, boolean isManager, boolean isTrainee) {
		StaffMember x = new StaffMember();
		
		if(isManager && isTrainee) {
			throw new RuntimeException(name + " can't be both a manager and a trainee");
		}
		
		x.setId(id);
		x.setName(name);
		x.setManager(isManager);
		x.setTrainee(isTrainee);
		
		staff.add(x);
	}
	
	/**
	 * Find the StaffMember object for a particular id and name
	 * @param id The staff member's id
	 * @param name The staff member's name
	 * @return A StaffMember, or null
	 */
	public StaffMember findStaff(String id, String name) {
		for(StaffMember s : staff) {
			if((s.getId() == id) && (s.getName() == name)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Find the number of shifts a StaffMember has
	 * @param id The staff member's id
	 * @param name The staff member's name
	 * @return A count of shifts, or null
	 */
	public long staffShiftCount(String id, String name) {
		for(StaffMember s : staff) {
			if((s.getId() == id) && (s.getName() == name)) {
				return s.getNumberOfShifts();
			}
		}
		return 0;
	}
	

}
