import java.io.*;

public class Employee {

	private int empNum;
	private boolean shiftStarted;
	private boolean breakStarted;
	private boolean lunchStarted;
	private File empFile;

	
	// default constructor
	public Employee() { 
		empNum = 0;
		shiftStarted  = false;
		breakStarted  = false;
		lunchStarted  = false;
	}
	
	// alternate constructor
	public Employee(int num, Boolean shiftStart, Boolean breakStart,  Boolean lunchStart, File  f){
		empNum = num;
		shiftStarted  = shiftStart;
		breakStarted  = breakStart;
		lunchStarted  = lunchStart;
		empFile = f;
	}

	/// GETTERS \\\

	public int getEmpNum() { 
		return empNum; 
	} 
	
	public boolean hasShiftStarted() {
		return shiftStarted;
	}
	
	public boolean hasBreakStarted() {
		return breakStarted;
	}
	
	public boolean hasLunchStarted() {
		return lunchStarted;
	}
	
	
	public File getEmpFile() { 
		return empFile; 
	}
	
	/// SETTERS \\\

	public void setEmpNum(int empNum) {
		this.empNum = empNum;
	} 
	
	public void setShiftStatus(Boolean shiftStart) {
		this.shiftStarted = shiftStart;
	}
	
	public void setBreakStatus(Boolean breakStart) {
		this.breakStarted = breakStart;
	}
	
	public void setLunchStatus(Boolean lunchStart) {
		this.lunchStarted = lunchStart;
	}
	
	public void setEmpFile(String f) {
		this.empFile = new File(f);
	}
	

//// Other Methods \\\
	
	public void writeToEmpFile(String s) throws IOException {
		FileWriter fw = new FileWriter(empFile, true);
		if(shiftStarted) {
			fw.write(s + "\n");
		}
		fw.close();
	}
	
	public void addToIDList(int s, File f) throws IOException {
		FileWriter fw = new FileWriter(f, true);
		fw.write("\n" + s);
		fw.close();
	}
	
//	public String toString() {
//		return "Employee Number: " + empNum + "\nShift Start?: " + hasShiftStarted() + "\nBreak Start?: " + hasBreakStarted() + "\nLunch Start?: " + hasLunchStarted() ;
//	} 
//	
//	public boolean equals(Object o) { 
//		if (o instanceof Employee){
//			Employee otherEmp = (Employee) o;
//			return  empNum == otherEmp.empNum && shiftStarted == otherEmp.shiftStarted 
//					&& breakStarted == otherEmp.breakStarted && lunchStarted == otherEmp.lunchStarted && empFile.equals(otherEmp.empFile) ;
//		} else {
//			return false;
//		}
//	}


//	/// COPY METHODS ///
//	public Employee getCopy() { 
//		return new Employee(getEmpNum(), hasShiftStarted(), hasBreakStarted(), hasLunchStarted(), getEmpFile());
//	} 
//
//	public void copy(Employee e) { 
//		empNum = e.empNum;
//		shiftStarted = e.shiftStarted;
//		breakStarted = e.breakStarted;  
//		lunchStarted = e.lunchStarted;
//		empFile = e.empFile;
//	} 


}
