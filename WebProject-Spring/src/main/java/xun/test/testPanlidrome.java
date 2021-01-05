package xun.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testPanlidrome {
//    List<Integer> list = new ArrayList<Integer>();
//    List<Integer> list2 = new ArrayList<Integer>();
//    list.add(1);
//    list.add(2);
//    list.add(3);
//    
//    
//    list2.add(1);
//    list2.add(2);
//    list2.add(3);
//    System.out.println(list);
////    Collections.reverse(list);
//    System.out.println(list2);
//    System.out.println(list==list2);

    public boolean isPalindrome(int x) {
        if(x<10){
            return false;
        }
        
        String tint = String.valueOf(x);
        
        int size = tint.length();
        Integer count = 0;
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> rlist = new ArrayList<Integer>();
        for(int i = 0; i < size+1; i++ ){
            list.add(Integer.parseInt(tint.substring(i,i+1)));
            rlist.add(Integer.parseInt(tint.substring(i,i+1)));
        }
        Collections.reverse(rlist);
        if(size%2==0){
            count = size / 2 ;
        }else{
            count = ( size - 1 )/2;
        }
        
        for (int i = 0; i <count; i++) {
            if(!list.get(i).equals(rlist.get(i)))
               return false;
        }
        return true;
        
    }
}
