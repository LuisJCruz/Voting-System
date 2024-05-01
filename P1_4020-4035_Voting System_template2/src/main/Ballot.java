package main;

import data_structures.ArrayList;
/**
 * This is the Ballot class. 
 * @author Luis J. Cruz
 */
import interfaces.List;
public class Ballot {

	private List<Candidate> candidates;
	private String[] ballotlist;
	private int ballotNum;
	/**
	 * Creates a Ballot from the line. The line will have the format BallotID#,[CandidateID#:Rank].
	 * @param line String with the ballot ID# and votes.
	 * @param candidates List of the election candidates.
	**/
	public Ballot(String line, List<Candidate> candidates) {
		this.ballotlist = line.split(",");
		this.candidates = candidates;
		this.ballotNum=Integer.parseInt(ballotlist[0]);
		
	}
	/**
	 * @return The Ballot info in list form
	**/
	public String[] getBallotList() {
		return ballotlist;
	}
	/**
	 * @return The ballots ID#.
	**/
	public int getBallotNum() {
		return ballotNum;
	}
	/**
	 * Takes the candidates ID# and returns the rank they have on the ballot. 
	 * @return The candidates Rank on the current ballot.
	 * @param candidateID The candidates ID#.
	**/
	public int getRankByCandidate(int candidateID) {
		for (int i = 1; i < ballotlist.length; i++) {
			String[] vote = ballotlist[i].split(":");
			int currentID = Integer.parseInt(vote[0]);
			if (currentID==candidateID) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Takes a Rank and returns the the ID# of the candidate in that rank on that ballot. 
	 * @return The candidates Rank on the current ballot.
	 * @param rank The Rank of the wanted candidate.
	**/
	public int getCandidateByRank(int rank) {
		for (int i = 1; i < ballotlist.length; i++) {
			String[] vote = ballotlist[i].split(":");
			int currentID = Integer.parseInt(vote[0]);
			if (i==rank) {
				return currentID;
			}
		}
		return -1;
	}
	/**
	 * Takes the candidates ID# and eliminates the candidate from the ballot. 
	 * It then promotes the rank of the candidates before them.  
	 * @return true when successful.
	 * @param candidateId The candidates ID#.
	**/
	public boolean eliminate(int candidateId) {
		List<String> results = new ArrayList<String>();
		results.add(ballotlist[0]);
		boolean removed = false;
		for (int i = 1; i < ballotlist.length; i++) {
			String[] vote = ballotlist[i].split(":");
			if(Integer.parseInt(vote[0])!=candidateId&&!removed) {
				results.add(ballotlist[i]);
			}
			else {
				if(removed==true) {
					vote[1]=String.valueOf(Integer.parseInt(vote[0])-1);
					String newVote = vote[0]+":"+vote[1];
					ballotlist[i]=newVote;
					results.add(ballotlist[i]);
				}
				else if(i!=ballotlist.length-1) {
						removed=true;
						for (Candidate candidate : candidates) {
							if (candidate.getId()==candidateId) {
								candidates.remove(candidate);
								candidate.setStatus(false);
								break;
							}
								
						}
				}
			}
		}
		//sets the corresponding ranks
        String newString="";
        for (int i=0;i<results.size();i++) {
        	if (i!=results.size()-1)
        		newString+=results.get(i)+",";
        	else {newString+=results.get(i);}
        }
        ballotlist = newString.split(",");
		return true;
	}
	/**
	 * @return An integer that indicates if the ballot is: 0 – valid, 1 – blank or 2 - invalid. 
	**/
	public int getBallotType() {
		for (int i = 1; i < ballotlist.length; i++) {
			String[] vote = ballotlist[i].split(":");
			int currentID = Integer.parseInt(vote[0]);
			int currentRank = Integer.parseInt(vote[1]);
			//this for loop checks if a ID in the ballot repeats
			for (int j = 1; j < ballotlist.length; j++) {
				if((i!=j&&currentID==(Integer.parseInt(((ballotlist[j].split(":"))[0]))))) {
					return 2;
				}
			}
			if(currentRank>candidates.size()||currentRank!=i||i>candidates.size()) {
				return 2;
			}
		}
		if (ballotlist.length==1) {return 1;}
		return 0;
	}
}
