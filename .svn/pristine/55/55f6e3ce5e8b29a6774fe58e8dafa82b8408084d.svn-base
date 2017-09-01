package com.iycharge.server.domain.entity.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileDownload {

    /**
     * @param fileName
     * @param res
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void Download(String fileName,
            HttpServletResponse res)
            throws FileNotFoundException, IOException {

        String fileContentType = "application/vnd-ms-excel";
        String fileDownloadType = "attachment";

        long totalsize = 0;
        // 取得要传输的文件，实际应用是可以将文件路径以参数的形式传入
        File f = new File(fileName);
        // 取文件长度
        long filelength = f.length();
        byte[] b = new byte[1024];

        // 设置文件输出流
        FileInputStream fin = new FileInputStream(f);
        DataInputStream in = new DataInputStream(fin);

        int pos = fileName.lastIndexOf(java.io.File.separator);
        String fn = new String(fileName.substring(pos + 1));

        // 设置相应头信息，让下载的文件显示保存信息
        res.setContentType(fileContentType);
        res.setHeader("Content-Disposition", fileDownloadType + ";filename=\""
                + fn + "\"");
        // 确定长度
        String filesize = Long.toString(filelength);
        // 设置输出文件的长度
        res.setHeader("Content-Length", filesize);
        // 取得输出流
        ServletOutputStream servletOut = res.getOutputStream();
        // 发送文件数据，每次1024字节，最后一次单独计算
        while (totalsize < filelength) {
            totalsize += 1024;
            if (totalsize > filelength) {
                // 最后一次传送的字节数
                byte[] leftpart = new byte[1024 - (int) (totalsize - filelength)];
                // 读入字节数组
                in.readFully(leftpart);
                // 写入输出流
                servletOut.write(leftpart);
            } else {
                // 读入1024个字节到字节数组 b
                in.readFully(b);
                // 写和输出流
                servletOut.write(b);
            }
        }
        servletOut.close();
    }

    /**
     * @param fileName
     * @param fileDownloadType
     * @param res
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void Download(String fileName, String fileDownloadType,
            HttpServletResponse res)
            throws FileNotFoundException, IOException {

        String fileContentType = null;

        if (fileName.endsWith(".doc")) {
            fileContentType = "application/msword";
        } else if (fileName.endsWith(".pdf")) {
            fileContentType = "application/pdf";
        } else if (fileName.endsWith(".xls")) {
            fileContentType = "application/vnd-ms-excel";
        } else if (fileName.endsWith(".txt")) {
            fileContentType = "text/plain";
        } else {
            fileContentType = "application/octet-stream";
        }

        long totalsize = 0;
        // 取得要传输的文件，实际应用是可以将文件路径以参数的形式传入
        File f = new File(fileName);
        // 取文件长度
        long filelength = f.length();
        byte[] b = new byte[1024];

        // 设置文件输出流
        FileInputStream fin = new FileInputStream(f);
        DataInputStream in = new DataInputStream(fin);

        int pos = fileName.lastIndexOf(java.io.File.separator);
        String fn = new String(fileName.substring(pos + 1).getBytes("gb2312"),
                "ISO8859-1");

        // 设置相应头信息，让下载的文件显示保存信息
        res.setContentType(fileContentType);
        res.setHeader("Content-Disposition", fileDownloadType + ";filename=\""
                + fn + "\"");
        // 确定长度
        String filesize = Long.toString(filelength);
        // 设置输出文件的长度
        res.setHeader("Content-Length", filesize);
        // 取得输出流
        ServletOutputStream servletOut = res.getOutputStream();
        // 发送文件数据，每次1024字节，最后一次单独计算
        while (totalsize < filelength) {
            totalsize += 1024;
            if (totalsize > filelength) {
                // 最后一次传送的字节数
                byte[] leftpart = new byte[1024 - (int) (totalsize - filelength)];
                // 读入字节数组
                in.readFully(leftpart);
                // 写入输出流
                servletOut.write(leftpart);
            } else {
                // 读入1024个字节到字节数组 b
                in.readFully(b);
                // 写和输出流
                servletOut.write(b);
            }
        }
        servletOut.close();
    }
}