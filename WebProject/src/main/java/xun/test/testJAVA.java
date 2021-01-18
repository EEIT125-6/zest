package xun.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
		public testJAVA() {
			// TODO Auto-generated constructor stub
		}
		public Integer getId() {
			return id;
		}
		
	@Override
		public String toString() {
			return "testJAVA [name=" + name + ", id=" + id + "]";
		}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		UUID uuid = UUID.randomUUID();
//		System.out.println(uuid);
		testJAVA s1 = new testJAVA("sa",1);
		testJAVA s2 = new testJAVA("ss",2);
		testJAVA ss3 = new testJAVA("sb",3);
		testJAVA s4 = new testJAVA("s4", 0);
		List<testJAVA> list = new ArrayList<testJAVA>();
		
		list.add(s2);
		list.add(ss3);
		list.add(s1);
		list.add(s4);
		System.out.println(list);
		Collections.sort(list, new TestJAVAComparator());
		
		System.out.println(list);
		System.out.println(list.size());
		System.out.println(list.get(list.size()-1));
		testJAVA s3 = (testJAVA) list.get(list.size()-1);
		System.out.println(s3.getId());
		String s="2-21";
		System.out.println(s.replaceFirst("-", ""));
//		Integer sa = Integer.valueOf(s);
//		System.out.println(sa);
		Float qq = (float) 3.2;
		System.out.println(qq);
		String sss = "h g f r <!--";
		int sbm = 1;
		System.out.println(Integer.valueOf(sbm));
		System.out.println(sss.replace(" ",""));
		System.out.println(sss.indexOf("u"));
		System.out.println(sss.substring(sss.indexOf("g"),sss.indexOf("g")+3));
		if ((sss.substring(sss.indexOf("g"),sss.indexOf("g")+3).equals("g f"))){
			String ssd=sss.replace("<!--", "fuck");
			System.out.println(ssd);
		}
	}
	
}
@SuppressWarnings("rawtypes")
class TestJAVAComparator implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		testJAVA t1 = (testJAVA) o1;
		testJAVA t2 = (testJAVA) o2;
		if(t1.getId()>t2.getId()) {
			return 1;
		}else if(t1.getId()==t2.getId()) {
			return 0;
		}else {				
			return -1;
		}
	}
}
