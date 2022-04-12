import java.util.List;

import roster.Roster;
import roster.TimeSlot;

public class Main {

	private static Roster roster;
	
	public static void main(String args[]) {
		
		roster = new Roster();
		
		// Initiate the roster data
		setupData();
		// How much does Teresa earn?
		long teresaPay=0L;
		teresaPay= roster.calculateWeeklyPay("001", "Teresa", false, false);
		System.out.printf("Teresa's weekly pay is $%.2f%n", (teresaPay / 100.0f) );
		
		// What if Teresa wasn't paid overtime?
		teresaPay = roster.calculateWeeklyPay("001", "Teresa", false, true);
		System.out.printf("Ignoring overtime, Teresa's weekly pay would be $%.2f%n", (teresaPay / 100.0f) );
		
		// How much does Karen earn?
		long karenPay = roster.calculateWeeklyPay("002", "Karen", false, false);
		System.out.printf("Karen's weekly pay is $%.2f%n", (karenPay / 100.0f) );
		
		// How much does Paul earn?
		long paulPay = roster.calculateWeeklyPay("006", "Paul", false, false);
		System.out.printf("Paul's weekly pay is $%.2f%n", (paulPay / 100.0f) );
		
		// How much would Paul earn if paid a regular wage?
		paulPay = roster.calculateWeeklyPay("006", "Paul", true, false);
		System.out.printf("Paul's weekly pay, if he was not a trainee, would be $%.2f%n", (paulPay / 100.0f));
		
		
		System.out.println("---\nThe following timeslots have a trainee working without a manager:");
		List<TimeSlot> badShifts = roster.findUnsupportedTraineeTimeSlots();
		for(TimeSlot slot : badShifts) {
			System.out.println(slot.toString());
		}
		
		System.out.println("---\nThe following timeslots have no staff allocated:");
		badShifts = roster.findFreeTimeSlots();
		for(TimeSlot slot : badShifts) {
			System.out.println(slot.toString());
		}
		
	}
	
	public static void setupData() {
		
		
		// Add a manager
		roster.addStaff("001", "Teresa", true, false);
		
		// Add some regular staff
		roster.addStaff("002", "Karen", false, false);
		roster.addStaff("003", "Abdul", false, false);
		roster.addStaff("004", "Hans",  false, false);
		roster.addStaff("005", "Wendy", false, false);
		
		// Add a trainee
		roster.addStaff("006", "Paul", false, true);		
		
		
		String weekdays[] = new String[] {
				"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
			};
		
		// Set up some shifts
		for(String day : weekdays) {
			roster.allocateStaff("001", "Teresa", day, 	 "9:00 AM", "11:00 AM");
			roster.allocateStaff("001", "Teresa", day, 	 "3:00 PM",  "5:00 PM");
			
			roster.allocateStaff("002", "Karen", day, 	"11:00 AM",  "1:00 PM");
			roster.allocateStaff("002", "Karen", day, 	 "1:00 PM",  "3:00 PM");
			roster.allocateStaff("002", "Karen", day, 	 "3:00 PM",  "5:00 PM");
			
			roster.allocateStaff("003", "Abdul", day, 	 "9:00 AM", "11:00 AM");
			roster.allocateStaff("003", "Abdul", day, 	 "1:00 PM",  "3:00 PM");
		}
		
		roster.allocateStaff("004", "Hans",   "Saturday", 	"9:00 AM", "11:00 AM");
		roster.allocateStaff("006", "Paul",   "Saturday", 	"9:00 AM", "11:00 AM");
		
		roster.allocateStaff("001", "Teresa", "Saturday", 	"11:00 AM", "1:00 PM");
		roster.allocateStaff("006", "Paul",   "Saturday", 	"11:00 AM", "1:00 PM");
		
		roster.allocateStaff("005", "Wendy", "Sunday", "11:00 AM", "1:00 PM");
		
		// Remove some shifts
		roster.unallocateStaff("002", "Karen",  "Tuesday", "11:00 AM", "1:00 PM");
		roster.unallocateStaff("003", "Abdul",  "Friday",  "9:00 AM", "11:00 AM");
		roster.unallocateStaff("001", "Teresa", "Friday",  "9:00 AM", "11:00 AM");
	}
}
