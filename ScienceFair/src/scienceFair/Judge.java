package scienceFair;
//Stores Judge data

import java.util.*;

public class Judge
{
	private String name;
	private boolean sub0, sub1, sub2, sub3, sub4, sub5; //stores subjects judge will judge
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
	}

	public void setName(String n)
	{
		name=n;
	}

	//Behavioral/Social Science
	public void doesSub0()
	{
		sub0=true;
		subs++;
	}

	//Chemistry
	public void doesSub1()
	{
		sub1=true;
		subs++;
	}

	//Earth/Space Science
	public void doesSub2()
	{
		sub2=true;
		subs++;
	}

	//Environmental Science
	public void doesSub3()
	{
		sub3=true;
		subs++;
	}

	//Life Science
	public void doesSub4()
	{
		sub4=true;
		subs++;
	}

	//Mathematics/Physics
	public void doesSub5()
	{
		sub5=true;
		subs++;
	}
	
	public String toString(){
		
		String result =  "Name is " + name;
		result = result + " number of subjects to judge is " + subs;
		return result;
	}
}