package com.iycharge.server.domain.entity.utils.outputExcel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TemplateFileUtil {
	public static InputStream getTemplates(String tempName) throws FileNotFoundException {
		 Resource resource = new ClassPathResource("templates/admin/excel"+tempName); 
		 try {
			 //String path = resource.getFile().getAbsolutePath();
			//System.err.println("path:" + resource.getFile().getAbsolutePath());
			return resource.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// resource.getInputStream()
		return null;
        //return new FileInputStream(ResourceUtils.getFile("classpath:templates/admin/excel"+tempName));
    }
}
