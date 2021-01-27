package xun.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class javaInterview {
//xun19960903
//07210903
	public static void main(String[] args) {
//		List<Integer> list = new ArrayList<Integer>();
//		Integer y,z,x;
//		z=1;
//		for(int i = 1 ; i < 13 ; i++) {
//			if (list.size()>1) {
////				x = list.get(list.size()-1);
////				y = list.get(list.size()-2);
////				z=x+y;
//				list.add(list.get(list.size()-1)+list.get(list.size()-2));
//			}
//			else {
//				list.add(z);				
//			}
//		}
//		System.out.println(list);
//		----------------------------------------
		Integer n = 3;
		n++;
		for(int i = 1 ; i < n ; i++) {
			for(int j = i; j < n-1 ;j++) {
				System.out.print(" ");
			}
			for(int z = 0 ; z < i ;z++) {
				System.out.print("\\");
			}
			System.out.print(" ");
			for(int z = 0 ; z < i  ;z++) {
				System.out.print("/");
			}
			System.out.println("");
		}
		
		for(int i = 1 ; i < n ; i++) {
			for(int j = 1; j < i ;j++) {
				System.out.print(" ");
			}
			for(int z = n ; z > i ;z--) {
				System.out.print("/");
			}
			System.out.print(" ");
			for(int z = n ; z > i  ;z--) {
				System.out.print("\\");
			}
			System.out.println("");
		}
	}
}
