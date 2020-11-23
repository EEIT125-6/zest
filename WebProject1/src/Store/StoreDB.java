package Store;

import java.io.Serializable;

public class StoreDB implements Serializable {

	private static String[] stname;	 
	private static String[]	sclass;
	private static String[] saddress;
	
	
	public static String getStname(int ID) {
		return stname[ID];
	}
	public static void setStname(String[] stname) {
		StoreDB.stname = stname;
	}
	public static String getSclass(int ID) {
		return sclass[ID];
	}

	public static void setSclass(String[] sclass) {
		StoreDB.sclass = sclass;
	}
	public static String getSaddress(int ID) {
		return saddress[ID];
	}
	public static void setSaddress(String[] saddress) {
		StoreDB.saddress = saddress;
	}
	
	public static int size() {
	       return sclass.length;
	}
	
	
}
