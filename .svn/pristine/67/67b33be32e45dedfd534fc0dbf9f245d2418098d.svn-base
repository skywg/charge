package com.iycharge.server.domain.common.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import au.com.bytecode.opencsv.CSVWriter;

public class OpenCSV {

	    public static byte[] CSVWriter( List<Object> object,String fields[], String header[]){
	    	String files = System.getProperty("user.dir");
	        File fileChild = new File(files);
	        File parentFile = fileChild.getParentFile();
	        String path = parentFile.getPath();
	    	String dir = path+File.separator+"analyse";
	    	System.out.println("-----------------------"+dir+"------------------------");
	    	System.out.println("-----------------------"+dir+"------------------------");
	    	File file =new File(dir);    
	    	if  (!file .exists()  && !file .isDirectory())      
	    	{       
	    	    System.out.println("//不存在");  
	    	    file .mkdir();   
	    	}else{
	    		NewDelCopyFile.delAllFile(dir);
	    	}
	        File csv = new File(dir, System.currentTimeMillis()+".csv");
	        System.out.println("-----------------------"+csv.getAbsolutePath()+"------------------------");
	        System.out.println("-----------------------"+csv.getName()+"------------------------");
	        boolean flag = false;
	        if (!csv.exists())
	        {
	        	try {
					csv.createNewFile();
				} catch (IOException e) {
					flag = true;
				}
	        }
	        List<String[]> newlist = new ArrayList<String[]>();
	        if(object!=null&&object.size()>0){
	        	Iterator it = object.iterator();
	        	while(it.hasNext()){
	        		Object obj = it.next();
	        		List<String> fieldlist = new ArrayList<String>();
	        		for(String fieldName:fields){
	        			Object o = ReflectField.getFieldValueByName(fieldName, obj);
	        			String value = o.toString();
	        			/*if(fieldName.equals("time")){
	        				value=tranTime(value);
	        			}*/
	        			fieldlist.add(value);
	        			
	        		}
	        		newlist.add(fieldlist.toArray(new String[fieldlist.size()]));
	        	}
	        }
			try {
				 CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(csv),"GBK"),CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
				 writer.writeNext(header);
		         writer.writeAll(newlist);
		         writer.flush();
		         writer.close();
			} catch (UnsupportedEncodingException e) {
				flag = true;
			} catch (FileNotFoundException e) {
				flag = true;
			} catch (IOException e) {
				flag = true;
			}
			if(!flag){
				return File2byte(csv);
			}else{
				return null;
			}
	    }
	    
	    public static String tranTime(String time){
	    	String times[] = time.split("-");
			if (times.length==1) {
				return times[0]+"年";
			} else if (times.length==2) {
				return times[0]+"年"+times[1]+"月";
			} else if (times.length==3) {
				return times[0]+"年"+times[1]+"月"+times[2]+"日";
			}
			return null;
		}
	    
	    public static byte[] File2byte(File file)  
	    {  
	        byte[] buffer = null;  
	        try  
	        {  
	            FileInputStream fis = new FileInputStream(file);  
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	            byte[] b = new byte[1024];  
	            int n;  
	            while ((n = fis.read(b)) != -1)  
	            {  
	                bos.write(b, 0, n);  
	            }  
	            fis.close();  
	            bos.close();  
	            buffer = bos.toByteArray();  
	        }  
	        catch (FileNotFoundException e)  
	        {  
	            e.printStackTrace();  
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace();  
	        }  
	        return buffer;  
	    }  
	  
	    public static void byte2File(byte[] buf, String filePath, String fileName)  
	    {  
	        BufferedOutputStream bos = null;  
	        FileOutputStream fos = null;  
	        File file = null;  
	        try  
	        {  
	            File dir = new File(filePath);  
	            if (!dir.exists() && dir.isDirectory())  
	            {  
	                dir.mkdirs();  
	            }  
	            file = new File(filePath + File.separator + fileName);  
	            fos = new FileOutputStream(file);  
	            bos = new BufferedOutputStream(fos);  
	            bos.write(buf);  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	        finally  
	        {  
	            if (bos != null)  
	            {  
	                try  
	                {  
	                    bos.close();  
	                }  
	                catch (IOException e)  
	                {  
	                    e.printStackTrace();  
	                }  
	            }  
	            if (fos != null)  
	            {  
	                try  
	                {  
	                    fos.close();  
	                }  
	                catch (IOException e)  
	                {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  
	    
	}
