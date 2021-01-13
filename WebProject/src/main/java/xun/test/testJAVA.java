package xun.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.w3c.dom.stylesheets.LinkStyle;

public class testJAVA {
		private String name;
		private Integer id;
		public testJAVA(String n,Integer i) {
			name = n;
			id = i;
		}
		public Integer getId() {
			return id;
		}
	public static void main(String[] args) {
//		UUID uuid = UUID.randomUUID();
//		System.out.println(uuid);
		testJAVA s1 = new testJAVA("sa",1);
		testJAVA s2 = new testJAVA("ss",2);
		List<Object> list = new ArrayList<Object>();
		
		list.add(s1);
		list.add(s2);
		System.out.println(list.size());
		System.out.println(list.get(list.size()-1));
		testJAVA s3 = (testJAVA) list.get(list.size()-1);
		System.out.println(s3.getId());
	}
	
}
