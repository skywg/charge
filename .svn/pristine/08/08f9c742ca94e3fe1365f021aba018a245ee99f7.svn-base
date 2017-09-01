package com.iycharge.server.domain.common.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyPath {
    public static String getProjectPath() {
 
       java.net.URL url = MyPath.class .getProtectionDomain().getCodeSource().getLocation();
       String filePath = null ;
       try {
           filePath = java.net.URLDecoder.decode (url.getPath(), "utf-8");
           System.out.println("filePath="+filePath);
       } catch (Exception e) {
           e.printStackTrace();
       }
   // if (filePath.endsWith(".jar"))
    //filePath = filePath.substring(0, filePath.lastIndexOf("/"));
    //filePath = filePath.substring(0, filePath.lastIndexOf(File.separator)+1);
    //filePath = filePath.substring(0, filePath.lastIndexOf("/")).substring(0, filePath.lastIndexOf("/") + 1);
    //java.io.File file = new java.io.File(filePath);
    //filePath = file.getAbsolutePath();
    System.out.println("filePath="+filePath);
    return filePath;
}
    
    public static String getRealPath() {
       String realPath = MyPath.class .getClassLoader().getResource("").getFile();
//       realPath = realPath.substring(0, realPath.lastIndexOf("/"));
//       realPath = realPath.substring(0, realPath.lastIndexOf("/")+1);
       System.out.println(realPath);
       java.io.File file = new java.io.File(realPath);
       realPath = file.getPath();
//       realPath = file.getAbsolutePath();
       System.out.println(realPath);
       String re[] ={};
       try {
           realPath = java.net.URLDecoder.decode (realPath, "utf-8");
//           re = realPath.split(File.separator+"file");
//           System.out.println(re);
//           System.out.println(re[0]);
       } catch (Exception e) {
           e.printStackTrace();
       }
 
       return realPath;
    }
 
    public static String getAppPath(Class<?> cls){ 
       //检查用户传入的参数是否为空 
        if (cls==null )  
        throw new java.lang.IllegalArgumentException("参数不能为空！"); 
 
        ClassLoader loader=cls.getClassLoader(); 
        //获得类的全名，包括包名 
        String clsName=cls.getName();
        //此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库 
        if (clsName.startsWith("java.")||clsName.startsWith("javax.")) {
        throw new java.lang.IllegalArgumentException("不要传送系统类！");
        }
        //将类的class文件全名改为路径形式
        String clsPath= clsName.replace(".", "/")+".class"; 
 
        //调用ClassLoader的getResource方法，传入包含路径信息的类文件名 
        java.net.URL url =loader.getResource(clsPath); 
        //从URL对象中获取路径信息 
        String realPath=url.getPath(); 
        //去掉路径信息中的协议名"file:" 
        int pos=realPath.indexOf("file:"); 
        if (pos>-1) {
        realPath=realPath.substring(pos+5); 
        }
        //去掉路径信息最后包含类文件信息的部分，得到类所在的路径 
        pos=realPath.indexOf(clsPath); 
        realPath=realPath.substring(0,pos-1); 
        //如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名 
        if (realPath.endsWith("!")) {
        realPath=realPath.substring(0,realPath.lastIndexOf("/"));
        }
        java.io.File file = new java.io.File(realPath);
        realPath = file.getAbsolutePath();
 
        try { 
        realPath=java.net.URLDecoder.decode (realPath,"utf-8"); 
        }catch (Exception e){
        throw new RuntimeException(e);
        } 
        return realPath; 
    }//getAppPath定义结束 
//    public static void main(String[] args) {
//    	
//    	//System.out.println(MyPath.class.getResource(""));
//    	//System.out.println(getProjectPath());
//    	//System.out.println(getRealPath());
//    	
//    	// 得到本地的缺省格式
//        DecimalFormat df1 = new DecimalFormat("0.00");
//        System.out.println(df1.format(123.56123)+"%");
//        // 得到德国的格式
//        Locale.setDefault(Locale.GERMAN);
//        DecimalFormat df2 = new DecimalFormat("####.000");
//        System.out.println(df2.format(1234.56));
//	}
}
