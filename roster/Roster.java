package roster;

import java.util.ArrayList;
import java.util.List;

public class Roster extends StaffContainer {
	
	
	private List<TimeSlot> timeslots;
	
	private long centsPerShiftRegular = 4000;
	private long centsPerShiftManager = 6000;
	private long centsPerShiftTrainee = 1850;
	
	public Roster() {
		
		timeslots = new ArrayList<>();
		
		timeslots.add(new TimeSlot("Monday",     "9:00 AM", "11:00 AM"));
		timeslots.add(new TimeSlot("Monday",    "11:00 AM",  "1:00 PM"));
		timeslots.add(new TimeSlot("Monday",     "1:00 PM",  "3:00 PM"));
		timeslots.add(new TimeSlot("Monday",     "3:00 PM",  "5:00 PM"));
        timeslots.add(new TimeSlot("Tuesday",    "9:00 AM", "11:00 AM"));
		timeslots.add(new TimeSlot("Tuesday",   "11:00 AM",  "1:00 PM"));
		timeslots.add(new TimeSlot("Tuesday",    "1:00 PM",  "3:00 PM"));
		timeslots.add(new TimeSlot("Tuesday",    "3:00 PM",  "5:00 PM"));
    	timeslots.add(new TimeSlot("Wednesday",  "9:00 AM", "11:00 AM"));
		timeslots.add(new TimeSlot("Wednesday", "11:00 AM",  "1:00 PM"));
		timeslots.add(new TimeSlot("Wednesday",  "1:00 PM",  "3:00 PM"));
		timeslots.add(new TimeSlot("Wednesday",  "3:00 PM",  "5:00 PM"));
        timeslots.add(new TimeSlot("Thursday",   "9:00 AM", "11:00 AM"));
		timeslots.add(new TimeSlot("Thursday",  "11:00 AM",  "1:00 PM"));
		timeslots.add(new TimeSlot("Thursday",   "1:00 PM",  "3:00 PM"));
		timeslots.add(new TimeSlot("Thursday",   "3:00 PM",  "5:00 PM"));
        timeslots.add(new TimeSlot("Friday",     "9:00 AM", "11:00 AM"));
		timeslots.add(new TimeSlot("Friday",    "11:00 AM",  "1:00 PM"));
		timeslots.add(new TimeSlot("Friday",     "1:00 PM",  "3:00 PM"));
		timeslots.add(new TimeSlot("Friday",     "3:00 PM",  "5:00 PM"));
		
		// Saturdays are worth "time and a half" - staff are paid 1.5 times
		// as much for working on a Saturday
		timeslots.add(new TimeSlot("Saturday",   "9:00 AM", "11:00 AM"));
		timeslots.add(new TimeSlot("Saturday",  "11:00 AM",  "1:00 PM"));
		
		// On the Sunday shift, staff are paid double.
		timeslots.add(new TimeSlot("Sunday",    "11:00 AM",  "1:00 PM"));
		
	}
	
	/**
	 * Allocate a staff member to a particular day and time.
	 * 
	 * @param staffId The staff member's id
	 * @param staffName The staff member's name
	 * @param day A day of the week, "Monday", "Tuesday" etc.
	 * @param start The start time. "9:00 AM", "11:00 PM", "1:00 PM", or "3:00 PM", except on Sat/Sun where 11AM is the latest and Sunday where 11AM is the earliest too.
	 * @param end The end time. "11:00 AM", "1:00 PM", "3:00 PM", or "5:00 PM", except on Sat/Sun where 1PM is the latest. 
	 * @return A message about whether the allocation succeeded
	 */
	public String allocateStaff(String staffId, String staffName, String day, String start, String end) {
		
		StaffMember theStaffMember = findStaff(staffId, staffName);
		if(theStaffMember == null) {
			return "No staff member found. Please check the spelling.";
		}
		
		TimeSlot temp = findTimeSlot(day, start, end);
		if(temp == null) {
			return "No matching timeslot. Please try again";
		}
		
		if(temp.allocatedStaff.contains(theStaffMember)) {
			return "That staff member is already allocated!";
		}
		
		// Also add to the staff member
		theStaffMember.getTimeSlots().add(temp);
		theStaffMember.incrementShiftCount();
		
		temp.allocatedStaff.add(theStaffMember);
		return "Success!";		
	}
	
	/**
	 * Remove a staff member from a particular day and time
	 * @param staffId The staff member's id
	 * @param staffName The staff member's name
	 * @param day A day of the week, "Monday", "Tuesday" etc.
	 * @param start The start time. "9:00 AM", "11:00 PM", "1:00 PM", or "3:00 PM", except on Saturday where 11AM is the latest
	 * @param end The end time. "11:00 AM", "1:00 PM", "3:00 PM", or "5:00 PM", except on Saturday where 1PM is the latest. 
	 * @return A message about whether the removal succeeded
	 */
	public String unallocateStaff(String staffId, String staffName, String day, String start, String end) {
		StaffMember theStaffMember = findStaff(staffId, staffName);
		if(theStaffMember == null) {
			return "No staff member found. Please check the spelling.";
		}
		
		TimeSlot temp = findTimeSlot(day, start, end);
		if(temp == null) {
			return "No matching timeslot. Please try again";
		}
		
		if(! temp.allocatedStaff.contains(theStaffMember)) {
			return "That staff member is not allocated to this timeslot!";
		}
		
		temp.allocatedStaff.remove(theStaffMember);
		
		// Also remove from the staff member
		theStaffMember.getTimeSlots().remove(temp);
		theStaffMember.decrementShiftCount();
		
		return "Success!";		
	}
	
	
	/**
	 * Check if a timeslot has any trainees
	 * @param ts A TimeSlot
	 * @return True if the timeslot contains trainees
	 */
	public boolean hasTrainees(TimeSlot ts) {
		for(StaffMember s : ts.allocatedStaff) {
			if(s.isTrainee()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if a timeslot has any managers
	 * @param ts A TimeSlot
	 * @return True if the timeslot contains managers
	 */
	public boolean hasManagers(TimeSlot ts) {
		for(StaffMember s : ts.allocatedStaff) {
			if(s.isManager()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * It's not ok for trainees to be working without a manager
	 * @return
	 */
	public List<TimeSlot> findUnsupportedTraineeTimeSlots() {
		List<TimeSlot> slots = new ArrayList<TimeSlot>();
		
		for(TimeSlot s : timeslots) {
			
			// If it has traineers, and does not have managers:
			if(this.hasTrainees(s) && !this.hasManagers(s)) {
				slots.add(s);
			}
		}
		
		return slots;
	}

	
	
	/**
	 * Find the TimeSlot object for a particular day, start time, and end time
	 * @param day A day of the week, "Monday", "Tuesday" etc.
	 * @param start The start time. "9:00 AM", "11:00 PM", "1:00 PM", or "3:00 PM", except on Saturday where 11AM is the latest
	 * @param end The end time. "11:00 AM", "1:00 PM", "3:00 PM", or "5:00 PM", except on Saturday where 1PM is the latest.
	 * @return A TimeSlot, or null
	 */
	public TimeSlot findTimeSlot(String day, String start, String end) {
		for(TimeSlot t : timeslots ) {
			if(t.day == day && t.startTime == start && t.endTime == end) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Find all the TimeSlots that don't have anybody allocated to them
	 * @return A list of TimeSlots with nobody allocated to work on them.
	 */
	public List<TimeSlot> findFreeTimeSlots() {
		List<TimeSlot> freeSlots = new ArrayList<TimeSlot>();
		
		for(TimeSlot t : timeslots ) {
			if(t.allocatedStaff.isEmpty()) {
				freeSlots.add(t);
			}
		}
		
		return freeSlots;		
	}
	
	/**
	 * Calculate weekly pay for a given staff member in this roster, in cents
	 * @param staffId The staff member's ID
	 * @param staffName The staff member's name
	 * @param calculateAsRegular If true, don't use special trainee/manager rates
	 * @param ignoreOvertime If true, don't calculate special weekend rates
	 * @return Weekly pay in cents
	 */
	public long calculateWeeklyPay(String staffId, String staffName, boolean calculateAsRegular, boolean ignoreOvertime) {
		long payRate;
		long timeSlotCount;
		String day;
		
		long weeklyPay = 0;
		long overtime;
		long shiftPay;
		
		for(TimeSlot t : timeslots) {
			for(StaffMember s: t.allocatedStaff) {
				if(s.getName() == staffName && s.getId() == staffId) {
					
					// Figure out what the pay rate is for this staff member
					if(s.isTrainee()) {
						payRate = centsPerShiftTrainee;
					} else if(s.isManager()) {
						payRate = centsPerShiftManager;
					} else {
						payRate = centsPerShiftRegular;
					}
					
					// Can ignore manager/trainee rates
					if(calculateAsRegular) {
						payRate = centsPerShiftRegular;
					}
					
					switch(t.day) {
					case "Saturday":
						// 1.5 times the usual wage. Use bit-shift to divide by 2
						overtime = (payRate >> 1);						
						shiftPay = payRate + overtime;
						break;
					case "Sunday":
						// 2 times the usual wage
						overtime = payRate;						
						shiftPay = payRate + overtime;
						break;
					default:
						shiftPay = payRate;
					}
					
					if(ignoreOvertime) {
						shiftPay = payRate;
					}
					
					weeklyPay += shiftPay;
					
				}
			}
		}
		
		return weeklyPay;
	}
	
}
