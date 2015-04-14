package scienceFair;
//Stores Judge data

import java.util.*;

public class Project
{
	private int pnum; //stores project number
	private int sub; //stores subject

	public Project()
	{
		pnum=0;
		sub=-1;
	}

	public Project(int n)
	{
		pnum=n;
		sub=-1;
	}

	public void setNumber(int n)
	{
		pnum=n;
	}

	//Behavioral/Social Science
	public void doesSub0()
	{
		sub=0;
	}

	//Chemistry
	public void doesSub1()
	{
		sub=1;
	}

	//Earth/Space Science
	public void doesSub2()
	{
		sub=2;
	}

	//Environmental Science
	public void doesSub3()
	{
		sub=3;
	}

	//Life Science
	public void doesSub4()
	{
		sub=4;
	}

	//Mathematics/Physics
	public void doesSub5()
	{
		sub=5;
	}
	
	public String toString(){
		
		String result = "Project number is " + Integer.toString(pnum);
		result = result + " subject is " + Integer.toString(sub);
		return result;
	}
}