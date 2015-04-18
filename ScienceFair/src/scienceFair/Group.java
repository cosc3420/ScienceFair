package scienceFair;
//Stores Judge data

import java.util.*;

public class Group
{
	//maybe an array of Judge instead
	//private Judge j1, j2, j3; //Stores the judges.
	
	public static final int NUMBER_OF_JUDGES = 3;
	private Judge []judges;
	private int judgeCount;//tracks number of judges assigned
	
	//maybe an array of Projects instead
	//private Project p1, p2, p3, p4, p5, p6;
	
	public static final int MAXIMUM_PROJECTS = 6;
	private Project []projects;
	int projCount;//tracks number of projects assigned
	
	private int sub; //stores project subject
	private int num; //stores group number
	private int numOfProjects; //stores the actual number of projects in case it is fewer
								//than 6

	//default constructor
	public Group()
	{
		//define group constructor
		judges = new Judge[NUMBER_OF_JUDGES];
		projects = new Project[MAXIMUM_PROJECTS];
		
		sub = -1;
		num = -1;
		numOfProjects = MAXIMUM_PROJECTS;
		
		judgeCount = 0;
		projCount = 0;
		
	}
	
	public Group(int subject, int groupNum, int projNum){
		
		sub = subject;
		num = groupNum;
		numOfProjects = projNum;
		judges = new Judge[NUMBER_OF_JUDGES];
		projects = new Project[numOfProjects];
		
		judgeCount = 0;
		projCount = 0;
		
	}

	public Judge getJudge(int judgeNum) {
		return judges[judgeNum];
	}
	
	public Judge getJ1() {
		return judges[0];
	}

	public Judge getJ2() {
		return judges[1];
	}

	public Judge getJ3() {
		return judges[2];
	}

	public Project getProject(int projectNum) {
		return projects[projectNum];
	}
	
	public Project getP1() {
		return projects[0];
	}

	public Project getP2() {
		return projects[1];
	}

	public Project getP3() {
		return projects[2];
	}

	public Project getP4() {
		return projects[3];
	}

	public Project getP5() {
		return projects[4];
	}

	public Project getP6() {
		return projects[5];
	}

	public int getSub() {
		return sub;
	}

	public int getNum() {
		return num;
	}

	public void setJudge(int judgeNum, Judge j1) {
		this.judges[judgeNum] = j1;
		judgeCount++;
	}
	
	public void setJ1(Judge j1) {
		this.judges[0] = j1;
		judgeCount++;
	}

	public void setJ2(Judge j2) {
		this.judges[1] = j2;
		judgeCount++;
	}

	public void setJ3(Judge j3) {
		this.judges[2] = j3;
		judgeCount++;
	}

	public void setProject(int projectNum, Project p1) {
		this.projects[projectNum] = p1;
		projCount++;
	}

	
	public void setP1(Project p1) {
		this.projects[0] = p1;
		projCount++;
	}

	public void setP2(Project p2) {
		this.projects[1] = p2;
		projCount++;
	}

	public void setP3(Project p3) {
		this.projects[2] = p3;
		projCount++;
	}

	public void setP4(Project p4) {
		this.projects[3] = p4;
		projCount++;
	}

	public void setP5(Project p5) {
		this.projects[4] = p5;
		projCount++;
	}

	public void setP6(Project p6) {
		this.projects[5] = p6;
		projCount++;
	}

	public void setSub(int sub) {
		this.sub = sub;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public int getJudgeCount(){
		return this.judgeCount;
	}
	
	public int getProjCount(){
		return this.projCount;
	}
	
	public String toString(){
		
		String s = "Group number " + num + ". ";
		String subj = "Subject is " + sub + ". ";
		String s1 = "Judge 1 = " + judges[0].getName() + ". ";
		String s2 = "Judge 2 = " + judges[1].getName() + ". ";
		String s3 = "Judge 3 = " + judges[2].getName() + ". ";
		
		return s + subj + s1 + s2 + s3;
		
		
	}
}