package xun.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JAVA_Test_couldD {

	public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
//        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();// 获取当前时间 
        System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）


        
		File logpath = new File("C://WebProjectLog");
		logpath.mkdir();
		
		
		File logfile = new File("C://WebProjectLog//"+sdf.format(date)+".txt");
        if(!logfile.exists()) {
        	try {
        		System.out.println(logfile.toString());
				logfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        StringBuffer buf = new StringBuffer();
        	try (
        			FileInputStream fis	= new FileInputStream(logfile);
        			InputStreamReader isr =  new InputStreamReader(fis,"UTF-8");
        			BufferedReader br = new BufferedReader(isr);
        			){
        		String line = "";
        		while ((line = br.readLine()) != null) { 
//        			System.out.println(line); 
        			buf.append(line);
        			buf.append(System.getProperty("line.separator"));
        			} 				
        		System.out.println("讀取完畢");
			} catch (Exception e) {
				e.printStackTrace();
			}
        	try (
        			FileOutputStream fos = new FileOutputStream(logfile);
        			PrintWriter pw = new PrintWriter(fos);        			
        			){
        		String newcontent = "測試中文  plz success22223";
        		buf.append(newcontent);
//        		System.out.println(buf.toString());
        		pw.write(buf.toString());
        		pw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}


