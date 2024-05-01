package main;
/**
 * This is the Candidate class. 
 * @author Luis J. Cruz
 */
public class Candidate {
	private String[] info;
	private int ID;
	private boolean active;
	private String name;
	/**
	 * Creates a Candidate from the line. The line will have the format ID#,candidate_name .
	 * @param line String with candidate ID# and name.
	**/
	public Candidate(String line) {
		this.info=line.split(",");
		this.ID=Integer.parseInt(info[0]);
		this.active=true;
		this.name=info[1];
	}
	/**
	 * @return The candidates ID. 
	 **/
	public int getId() {	
		return ID;
	}
	/**
	 * @return True if the candidate is active
	**/
	public boolean isActive() {
		return active;
	}
	/**
	 * @return The candidates name
	**/
	public String getName() {
		return name;
	}
	/**
	 * Set the status of the candidate. True if active. False if inactive.
	 * @param status the status of the candidate
	**/
	public void setStatus(boolean status) {
		this.active=status;
	} 
}
