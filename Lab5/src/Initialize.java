import java.util.*;
/**
 * The Initialize class sets up the initialization system that operates through the terminal,
 * which configures the program to the election as desired by the administrator.
 * After, it opens the window that end users will see on voting machines.
 * What has been done:
 * - The initialization system 
 * - Everything database related
 * - Everything arithmetic related
 * - Everything algorithm related
 * - The program design
 * What needs to be done:
 * - Code to prompt for and accept the password after each vote
 * - Code to accept user button inputs
 * - Code to accept user vote data
 * - Code to bind JavaFX objects to their relevant variables
 * - About 4 FXML files (don't forget that the after-vote screen should have two buttons; one to prepare a new vote, and one to proceed to results)
 * - Code to traverse between the JavaFX files
 * - Making this whole program an executable file
 * @author William Paul Baker
 */
public class Initialize {
	int numCandidates;
	String password;
	String instructions;
	String after;
	ArrayList<Candidate> candidates;
	public static void main(String[] args) {
		Initialize i = new Initialize();
		int numCandidates = 0;
		String password;
		String instructions;
		String after;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to the R.E.D initalization system.");
		System.out.println("For security purposes, please make sure that only the voting booths are connected to the administration terminal, that the voting booths are not connected to anything else, that all devices have not been and will not be tampered with, and that all unused ports are disabled.");
		while(true) {
			System.out.print("Enter the unique password you will use to finalize a vote and see results:");
			if(sc.hasNextLine()) {
				password = sc.nextLine();
				break;
			}
			System.out.println("ERROR: No password input.");
		}
		i.setPassword(password);
		while(true) {
			System.out.print("Enter the instructions you want end users to see; for example, \"Rank candidates in order of your preference.\"");
			if(sc.hasNextLine()) {
				instructions = sc.nextLine();
				break;
			}
		}
		i.setInstructions(instructions);
		while(true) {
			System.out.print("Enter the message you want end users to see after they have voted; for example, \"Your vote has been accepted. Please allow an administrator to reconfigure the machine.\"");
			if(sc.hasNextLine()) {
				after = sc.nextLine();
				break;
			}
		}
		i.setAfter(after);
		System.out.print("Enter the number of candidates:");
		while(true) {
			if(sc.hasNextInt())
				numCandidates = sc.nextInt();
			if(numCandidates>0)
				break;
			System.out.println("ERROR: Invalid number of candidates.");
		}
		i.setNumCandidates(numCandidates);
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		for(int j=0; j < i.getNumCandidates(); ++j) {
			System.out.print("Enter the name of candidate " + i+1 + ":");
			if(sc.hasNextLine()) {
				Candidate candidate = new Candidate(sc.nextLine(), i.getNumCandidates());
				candidates.add(candidate);
			}
			else {
				System.out.println("ERROR: Invalid candidate name.");
				--j;
			}
		}
		i.setCandidates(candidates);
		sc.close();
		ArrayList<Voter> votes = new ArrayList<Voter>();
		// call up the FXML
	}
	//The below function should be called when a voter submits their vote. 
	public void makeVoter(ArrayList<Candidate> candidates, ArrayList<Voter> votes) {
		ArrayList<Integer> rankingdummy = new ArrayList<Integer>();
		for(int j=0; j < candidates.size(); j++) {
			rankingdummy.add(0);
		}
		Voter voter = new Voter(candidates, rankingdummy);
		votes.add(voter);
	}
	//The below function should be called when an administrator hits the results button.
	public void resultsSetup(ArrayList<Candidate> candidates, ArrayList<Voter> votes) {
		for (Voter v: votes)
			for(int j=0; j < candidates.size(); j++)
				if(v.ranking.get(j)==1)
					candidates.get(j).totVotes++;
	}
	//The below function should be called when an administrator hits the next round button.
	public void resultsTabulate(ArrayList<Candidate> candidates, ArrayList<Voter> votes) {
		for(Voter w: votes) {
			int lowestCand = lowestCand(candidates);
			candidates.get(lowestCand).eliminated = true;
			for(int k=0; k < candidates.size(); k++) {
				if(candidates.get(k).eliminated == true) {
						for(int r=2; r < candidates.size(); r++) {
							boolean brake = false;
							for(int s=0; s < candidates.size(); s++) {
								if(w.ranking.get(s) == r
										&& candidates.get(s).eliminated == false) {
									brake = true;
									candidates.get(s).totVotes++;
									break;
								}
							}
							if(brake)
								break;
						}
					candidates.get(k).totVotes=-1;
				}
			}
		}
	}
	public int lowestCand(ArrayList<Candidate> candidates) {
		int lowestCand=0;
		for(int i=0; i < candidates.size(); i++)
			if(candidates.get(i).totVotes < candidates.get(lowestCand).totVotes
					&& candidates.get(i).eliminated == false)
				lowestCand = i;
		return lowestCand;
	}
	public void setNumCandidates(int numCandidates) {
		this.numCandidates = numCandidates;
	}
	public int getNumCandidates() {
		return this.numCandidates;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public String getInstructions() {
		return this.instructions;
	}
	public void setAfter(String after) {
		this.after = after;
	}
	public String getAfter() {
		return this.after;
	}
	public void setCandidates(ArrayList<Candidate> candidates) {
		this.candidates = candidates; 
	}
	public ArrayList<Candidate> getCandidates() {
		return this.candidates;
	}
}