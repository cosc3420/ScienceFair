package scienceFair;
//Stores Judge data

import java.util.*;

public class Judge
{
	private static final int NUMBER_OF_SUBJECTS = 6;
	
	private String name;
	private boolean sub0, sub1, sub2, sub3, sub4, sub5; //stores subjects judge will judge
	private boolean[] subject;//different way of doing above task
	
	
	private int subs; //stores amount of subjects judge will judge

	public Judge()
	{
		name = new String("");
		sub0=false;
		sub1=false;
		sub2=false;
		sub3=false;
		sub4=false;
		sub5=false;
		subs=0;
		
		subject = new boolean[NUMBER_OF_SUBJECTS];
		for(int i = 0; i < NUMBER_OF_SUBJECTS; i++)
			subject[i] = false;
	}

	public Judge(String n)
	{
		name = new String(n);
		sub0=false;
		sub1=false;
		sub2=false;
		sub3=false;
		sub4=false;
		sub5=false;
		subs=0;
		
		subject = new boolean[NUMBER_OF_SUBJECTS];
		for(int i = 0; i < NUMBER_OF_SUBJECTS; i++)
			subject[i] = false;
	}

	public void setName(String n)
	{
		name=n;
	}
	
	public String getName(){
		return name;
	}

	//Behavioral/Social Science
	public void doesSub0()
	{
		subject[0] = true;
		//sub0=true;
		subs++;
	}

	//Chemistry
	public void doesSub1()
	{
		subject[1] = true;
		//sub1=true;
		subs++;
	}

	//Earth/Space Science
	public void doesSub2()
	{
		subject[2] = true;
		//sub2=true;
		subs++;
	}

	//Environmental Science
	public void doesSub3()
	{
		subject[3] = true;
		//sub3=true;
		subs++;
	}

	//Life Science
	public void doesSub4()
	{
		subject[4] = true;
		//sub4=true;
		subs++;
	}

	//Mathematics/Physics
	public void doesSub5()
	{
		subject[5] = true;
		//sub5=true;
		subs++;
	}
	
	public void doesSub(int subjectNum){
		subject[subjectNum] = true;
		subs++;
	}
	
	public String toString(){
		
		String result =  "Name is " + name;
		result = result + " number of subjects to judge is " + subs;
		return result;
	}

	public boolean judgesSubject(int subjectNum) {
		return this.subject[subjectNum];
	}

	public void setSubject(boolean[] subject) {
		this.subject = subject;
	}


}