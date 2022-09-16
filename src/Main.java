import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;


public class Main {
	@SuppressWarnings("resource")
	public static void main(String args[]) throws IOException {
		try (Scanner console = new Scanner(System.in)) {
			File nonAdminDir = new File("Non-Admins");
			File adminDir = new File("Admins");
			File empIDList = new File(nonAdminDir,"EmployeeIDList.txt");
			File adminIDList = new File(adminDir, "AdminIDList.txt");
			File f;
			ArrayList<Employee> nonAdmin = new ArrayList<Employee>();
			ArrayList<Employee> admin = new ArrayList<Employee>();
			
			Employee e = new Employee();
			Scanner scan;
			int id;
			int choice;
			int input;
			boolean validEmp = false;
			boolean validAdmin = false;
			String timeStamp;
			String empFileName;
			String adminFileName;
			String date = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")); 
			
			nonAdminDir.mkdir();
			adminDir.mkdir();
			empIDList.createNewFile();
			adminIDList.createNewFile();
			
			//Existing Employee File Check & Adding Employee to nonAdmin ArrayList
			scan = new Scanner(empIDList);
			while (scan.hasNextInt()) {
				id = scan.nextInt();
				empFileName = "Employee" + id + ".txt"; 
				f = new File(nonAdminDir, empFileName);
				f.createNewFile();
				e = new Employee(id, false, false, false, f);
				nonAdmin.add(e);
			}
			
			//Existing Admin File Check & Adding Admin to admin ArrayList
			scan = new Scanner(adminIDList);
			while (scan.hasNextInt()) {
				id = scan.nextInt();
				adminFileName = "Admin" + id + ".txt";
				f = new File(adminDir, adminFileName);
				f.createNewFile();
				e = new Employee(id, false, false, false, f);
				admin.add(e);
			}


			choice = startMenu();
			while(choice != 0) {
				switch(choice) {
				// EMPLOYEE (NON-ADMIN) LOGIN
				case 1:
					input = getInt(console,"Enter Your EmployeeID [0 to Exit]: ", "\nEmployeeIDs are a 4-Digit number. Try Again!\n");
					if (!(input == 0)){
						for (Employee i : nonAdmin){
							if (input == i.getEmpNum()) {
								validEmp = true;
								e = i;
								e.getEmpFile().createNewFile();
								break;		   
							}
						}
						if (validEmp == false) {
							System.out.println("\nInvalid ID! \n");
							break;
						} else 
					
						if (validEmp = true) 
						{
							choice = empHomeMenu(e);
							while(choice != 0) {
								String pattern = "hh:mm:ss a.";
								String time;
								switch(choice) {

								//START SHIFT
								case 1:
									if (e.hasShiftStarted()) {
										System.out.println("Shift already started.");
									} else {
										time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
										timeStamp = "Shift Started --> " + time;
										System.out.println(timeStamp);
										e.setShiftStatus(true);
										e.writeToEmpFile("\n" + date + "\n\n\t" + timeStamp);
									}
									break;

								//END SHIFT
								case 2:
									if (!e.hasShiftStarted()) {
										System.out.println("Cannot record shift - No Shift Started.");
									} else if (e.hasBreakStarted()) {
										System.out.println("Please end active break before ending shift.");	
									} else if (e.hasLunchStarted()) {
										System.out.println("Please end active lunch before ending shift.");
									} else {
										time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
										timeStamp = "Shift Ended --> " + time + "\n";
										System.out.println(timeStamp);
										e.writeToEmpFile("\t" + timeStamp); 
										e.setShiftStatus(false);
									}
									break; 

								//START BREAK
								case 3:
									if (!e.hasShiftStarted()) {
										System.out.println("Cannot record break - No Active Shift.");
									} else if (e.hasLunchStarted()) {
										System.out.println("Cannot record a break during an active lunch.");
									} else if (e.hasBreakStarted()) {
										System.out.println("Break already started.");
									} else {
										time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
										timeStamp = "Break Started --> " + time;
										System.out.println(timeStamp);
										e.setBreakStatus(true);
										e.writeToEmpFile("\t" + timeStamp);
									}
									break;

								//END BREAK
								case 4:
									if (!e.hasShiftStarted()) {
										System.out.println("Cannot record break - No Active Shift.");
									} else if (!e.hasBreakStarted()) {
										System.out.println("Cannot record break - No Break Started.");
									} else {
										time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
										timeStamp = "Break Ended --> " + time;
										System.out.println(timeStamp);
										e.setBreakStatus(false);
										e.writeToEmpFile("\t" + timeStamp);
									}
									break;

								//START LUNCH
								case 5:
									if (!e.hasShiftStarted()) {
										System.out.println("Cannot record lunch - No Active Shift.");
									} else if (e.hasBreakStarted()) {
										System.out.println("Cannot record a lunch during an active break.");
									} else if (e.hasLunchStarted()) {
										System.out.println("Lunch already started.");
									} else {
										time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
										timeStamp = "Lunch Started --> " + time;
										System.out.println(timeStamp);
										e.setLunchStatus(true);
										e.writeToEmpFile("\t" + timeStamp);
									}
									break;

								//END LUNCH
								case 6:
									if (!e.hasShiftStarted()) {
										System.out.println("Cannot record lunch - No Active Shift.");
									} else if (!e.hasLunchStarted()) {
										System.out.println("No Lunch Started");
									} else {
										time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
										timeStamp = "Lunch Ended --> " + time;
										System.out.println(timeStamp);
										e.setLunchStatus(false);
										e.writeToEmpFile("\t" + timeStamp);
									}
									break;

								//SHIFT DATA
								case 7:
									System.out.println("Your Shift Data\n" + "----------------------------------");
									Scanner sc = new Scanner(e.getEmpFile());
									if (e.getEmpFile().length() == 0) {
										System.out.println("No Shift Data Available.");
									} else  {
										while(sc.hasNextLine()) {
											System.out.println(sc.nextLine());
										}
									}
									break;
								}// close empHomeMenu switch
								choice = empHomeMenu(e);
							} //close homeMenu while loop

						} else {
							System.out.println("Invalid Employee ID.");
						}
					}
					System.out.println("\nReturned to Start.\n");
					break;

				//ADMINISTRATOR LOGIN
				case 2:
					input = getInt(console,"Enter Your AdminID [0 to Exit]: ", "\nAdminIDs are a 4-Digit number. Try Again!\n");
					if (!(input == 0)){
						for (Employee i : admin){
							if (input == i.getEmpNum()) {
								validAdmin = true;
								e = i;
								e.getEmpFile().createNewFile();
								break;		   
							}
						}
						if (validAdmin == false) {
							System.out.println("\nInvalid ID! \n");
							break;
						}
						if (validAdmin = true) {
							choice = adminHomeMenu(e);
							while(choice != 0) {
								String pattern = "hh:mm:ss a";
								String time;
								switch(choice) {

								//START SHIFT
								case 1:
									time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
									timeStamp = "Shift Started --> " + time;
									System.out.println(timeStamp);
									e.setShiftStatus(true);
									e.writeToEmpFile(date + "\n\t" + timeStamp);
									break;

								//END SHIFT
								case 2:
									time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
									timeStamp = "Shift Ended --> " + time + "\n";
									System.out.println(timeStamp);
									e.writeToEmpFile("\t" + timeStamp);
									e.setShiftStatus(false);
									break; 

								//START BREAK
								case 3:
									time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
									timeStamp = "Break Started --> " + time;
									System.out.println(timeStamp);
									e.setBreakStatus(true);
									e.writeToEmpFile("\t" + timeStamp);
									break;

								//END BREAK
								case 4:
									time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
									timeStamp = "Break Ended --> " + time;
									System.out.println(timeStamp);
									e.setBreakStatus(false);
									e.writeToEmpFile("\t" + timeStamp);
									break;

								//START LUNCH
								case 5:
									time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
									timeStamp = "Lunch Started --> " + time;
									System.out.println(timeStamp);
									e.setLunchStatus(true);
									e.writeToEmpFile("\t" + timeStamp);
									break;

								//END LUNCH
								case 6:
									time = LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
									timeStamp = "Lunch Ended --> " + time;
									System.out.println(timeStamp);
									e.setLunchStatus(false);
									e.writeToEmpFile("\t" + timeStamp);
									break;

								//ALL EMPLOYEE SHIFT DATA
								case 7:
									
									for (Employee i : nonAdmin){
										Employee x = i;
										System.out.println("Employee " + x.getEmpNum() + " Shift Data\n" + "----------------------------------");
										Scanner sc = new Scanner(x.getEmpFile());
										if (x.getEmpFile().length() == 0) {
											System.out.println("No Employee Shift Data Available.");
										} else {
											while(sc.hasNextLine()) {
												System.out.println(sc.nextLine());
											}
										}	   
									}
									break;
								}// close adminHomeMenu switch
								choice = adminHomeMenu(e);
							} //close admminHomeMenu while loop

						} else {
							System.out.println("Invalid AdminID.");
						}
					}
					System.out.println("\nReturned to Start.\n");
					break;

				//REGISTER NEW EMPLOYEE
				case 3:  
					boolean validID = true;
					String answer = getYesNo(console,"Are you registering as an Administrator? [Yes/No/0 to Exit]: ");
					if (answer.equalsIgnoreCase("No")) { // ADD NON ADMIN EMPLOYEE
						do {
							id = (int) (Math.random()*(9999 - 1000) + 1000);
							for (Employee i : nonAdmin){
								if (id == i.getEmpNum()) {
									validID = false;
									break;		   
								}
							}	
						} while (!validID);
						
						// Create Employee File for New Employee - placed in "Non-Admins" Folder
						empFileName = "Employee" + id + ".txt";
						f = new File (nonAdminDir, empFileName);
						e = new Employee(id, false, false, false, f);
						e.addToIDList(e.getEmpNum(), empIDList);
						e.getEmpFile().createNewFile();
						nonAdmin.add(e);
						System.out.println("\n*** Your EmployeeID is " + id + " ***\n");
					} else if (answer.equalsIgnoreCase("Yes")) { // ADD ADMIN EMPLOYEE
						do {
							id = (int) (Math.random()*(9999 - 1000) + 1000);
							for (Employee i : admin){
								if (id == i.getEmpNum()) {
									validID = false;
									break;		   
								}
							}	
						} while (!validID);
						
						// Create Admin File for New Admin - placed in "Admins" Folder
						empFileName = "Admin" + id + ".txt";
						f = new File (adminDir, empFileName);
						e = new Employee(id, false, false, false, f);
						e.addToIDList(e.getEmpNum(), adminIDList);
						e.getEmpFile().createNewFile();
						admin.add(e);
						System.out.println("\n*** Your AdminID is " + id + " ***\n");
					} else if (answer.equals("0")) {
						System.out.println("\nReturned to Start.\n");
					}
					break;
				} //close startMenu switch
				choice = startMenu();
			} //close startMenu while loop
		}// end of try
		System.out.println("App Exited.");
	}


/// Methods \\\
	public static int startMenu() {
		int option;
		Scanner input = new Scanner(System.in);

		do {

			System.out.println("Welcome to Time Clock App!");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.format("%10s %25s %15s %15s", "(1) Employee Login", "(2) Administrator Login", "(3) Register ", "(0) Exit App\n\n");
			option = getInt(input, "Please enter the number of your option: ", "\nEnter an option from 0 to 3.\n");
			System.out.println();
		} while (option < 0 || option > 3);
		return option;
	}

	public static int empHomeMenu(Employee e) {
		int option;
		Scanner input = new Scanner(System.in);

		do {
			System.out.println("\nHi Employee " + e.getEmpNum() + "!");
			System.out.println("~~~~~~~~~~~~~~~~~");
			System.out.format("%40s %30s %30s %35s", "(1) Start Shift", "(3) Start Break", "(5) Start Lunch", "(7) Your Shift Data\n\n");
			System.out.format("%38s %30s %30s %28s", "(2) End Shift", "(4) End Break", "(6) End Lunch", "(0) Logout\n\n");
			option = getInt(input, "Please enter the number of your option: ", "\nEnter an option from 0 to 7.\n");
			System.out.println();
		} while (option < 0 || option > 7);
		return option;
	}

	public static int adminHomeMenu(Employee e) {
		int option;
		Scanner input = new Scanner(System.in);

		do {
			System.out.println("\nHi Administrator " + e.getEmpNum() + "!");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
			System.out.format("%40s %30s %30s %43s", "(1) Start Shift", "(3) Start Break", "(5) Start Lunch", "(7) Employee's Shift Report\n\n");
			System.out.format("%38s %30s %30s %28s", "(2) End Shift", "(4) End Break", "(6) End Lunch", "(0) Logout\n\n");
			option = getInt(input, "Please enter the number of your option: ", "\nEnter an option from 0 to 7\n");
			System.out.println();
		} while (option < 0 || option > 7);
		return option;
	}
	
	public static int employeeSummaryMenu(Employee e) {
		int option;
		Scanner input = new Scanner(System.in);

		do {
			System.out.println();
			System.out.format("%40s %30s %30s", "(1) See All Employee Shift Data", "(2) View One Employee's Shift Data", "(0) Exit\n");
			option = getInt(input, "Please enter the number of your option: ", "\nEnter an option from 0 to 7\n");
			System.out.println();
		} while (option < 0 || option > 7);
		return option;
	}


	// Method to return a valid integer - tests for type errors 
	public static int getInt(Scanner input, String prompt,  String error) {
		System.out.print(prompt);
		while (!input.hasNextInt()) {
			input.next();
			System.out.println(error);
			System.out.print(prompt); 
		}
		return input.nextInt();
	}
	
	// Method to return a yes or no response - O to exit
	public static String getYesNo(Scanner input, String prompt) {
		System.out.print(prompt);
		String in = input.next();
		while (!(in.equalsIgnoreCase("Yes") || (in.equalsIgnoreCase("No")))) {
			if (in.equals("0")) {
				return in;
			} else {
				System.out.println("\nPlease enter Yes or No (0 to Exit).\n");
				System.out.print(prompt);
				in = input.next();
			}
		}
		return in;
	}
	


}