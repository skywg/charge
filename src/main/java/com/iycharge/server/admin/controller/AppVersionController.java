package com.iycharge.server.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.admin.AppType;
import com.iycharge.server.domain.entity.admin.AppVersion;
import com.iycharge.server.domain.entity.admin.AppVersionQueryParam;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.service.AppVersionService;


/**
 * app版本管理
 * 
 * @author sxiao
 */
@Controller
@RequestMapping("/admin/system/version/")
public class AppVersionController {

	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private Environment env;
	//设置查询条件
	private static AppVersionQueryParam staticQueryParam = null;
	
	private static final String FILE_SAVEPATH=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"
			+ File.separator+"static"+File.separator+"app"+File.separator+"files"+File.separator;
	private static final Log logger = LogFactory.getLog(AppVersionController.class);
	
	
	@InitBinder
    public void InitBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) {
        /*SimpleDateFormat dateFormat = new
        SimpleDateFormat(getText("date.format", request.getLocale()));
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        dateFormat.setLenient(false);
       binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
                dateFormat, true));*/
    }
	
	@ModelAttribute("types")
    public List<DictData> types() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.APP_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
	
	@RequestMapping("/")
    public String searchAll(AppVersionQueryParam queryParam ,Model model,@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 15) Pageable pageable,String flag) {	    			
		 PageWrapper<AppVersion> page = null;
		 if(flag!=null&&staticQueryParam!=null){
			 page = new PageWrapper<>(appVersionService.findAllSearch(staticQueryParam, new AppVersion(), pageable),
						"/admin/system/version/search");
			 model.addAttribute("queryParam",staticQueryParam);
		 }else{
			 page = new PageWrapper<>(appVersionService.findAll(pageable), "/admin/system/version/");
			 model.addAttribute("queryParam",new AppVersionQueryParam());
			 staticQueryParam = null;
		 }
		 model.addAttribute("page", page); 
        return "admin/system/version/index";
    }
	
	@RequestMapping(value="/search")
	public String search(AppVersionQueryParam queryParam,Model model,  @ModelAttribute AppVersion appVersion,
			@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
			PageWrapper<AppVersion> page = new PageWrapper<>(appVersionService.findAllSearch(queryParam, appVersion, pageable),
					"/admin/system/version/search");
			model.addAttribute("page", page);
			model.addAttribute("queryParam", queryParam);
			staticQueryParam = queryParam;
		return "admin/system/version/index";
	}
	/**
	 *新增 
	 */

	@RequestMapping(value="/add")
	public String add(Model model,HttpSession session,AppVersionQueryParam queryParam) {
		AppVersion appVersion = new AppVersion();
    	Manager manager = (Manager)session.getAttribute("user");
    	appVersion.setAdminName(manager.getRealname());
		model.addAttribute("appVersion",appVersion);
		model.addAttribute("queryParam", queryParam);
		return "admin/system/version/add";
	}
	/**
	 * 增加version 
	 */
    @Transactional
    @RequestMapping(value="/addversion", method=RequestMethod.POST) 
    public String addversion( @ModelAttribute AppVersion appVersion,
    		@RequestParam("file")MultipartFile file,HttpSession session,String actionCode){
    	Manager manager = (Manager)session.getAttribute("user");
    	String appType = appVersion.getAppType().getTitle();
    	//文件全名
    	String name = file.getOriginalFilename();
    	//文件名
    	String fileName= name.substring(name.lastIndexOf(File.separator)+1);
    	//文件保存全路径
    	String save_path = FILE_SAVEPATH+appType.toLowerCase()+File.separator+fileName;
    	saveFile(file, save_path); 
    	appVersion.setCreatedAt(new Date());
    	appVersion.setAppFile(fileName);
    	appVersion.setAdminName(manager.getRealname());
    	if(actionCode != null && actionCode.equals("upgrade")){
    		appVersion.setUpdateFlat(true);
    	}
    	appVersionService.save(appVersion);
    	return "redirect:/admin/system/version/?flag=true";
    }  
	
	/**
	 *查看 
	 */
	@RequestMapping(value="/check/{id}")
	public String check(Model model,  @PathVariable("id") Long id) {
		AppVersion appVersion = appVersionService.findById(id);
		model.addAttribute("appVersion", appVersion);
		return "admin/system/version/check";
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,  @PathVariable("id") Long id) {
		AppVersion appVersion = appVersionService.findById(id);
		model.addAttribute("appVersion", appVersion);
		return "admin/system/version/edit";
	}
	/**
	 * 更新
	 */
	@Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id,
    		@Valid AppVersion form,@RequestParam("file")MultipartFile file) {
    	AppVersion appVersion = appVersionService.findById(id);
    	String appType = appVersion.getAppType().getTitle();
    	//文件全名
    	String name = file.getOriginalFilename();
    	//文件名
    	String fileName= name.substring(name.lastIndexOf("\\")+1);
    	//文件保存全路径
    	String save_path = FILE_SAVEPATH+appType.toLowerCase()+"\\"+fileName;
    	saveFile(file, save_path);
    	setBean(form, appVersion);
    	appVersionService.save(appVersion);
    	return "redirect:/admin/system/version/?flag=true";        	        
    }
	/**
	 *升级 
	 */

	@RequestMapping(value="/upgrade/{id}")
	public String upgrade(Model model ,@PathVariable("id") Long id) {
		AppVersion appVersion = appVersionService.findById(id);		
		appVersion.setUpdateFlat(true);	
		appVersionService.save(appVersion);
		return "redirect:/admin/system/version/?flag=true";
	}
	
	private void saveFile(MultipartFile file , String path){
   	 if (file!=null&&file.getOriginalFilename().trim()!="") {    
		 try {
			File fs = new File(path);
			if(fs.exists()){
				fs.delete();
			}
			FileCopyUtils.copy(file.getBytes(), fs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	}

 
	private void setBean(AppVersion form,AppVersion appVersion){
		appVersion.setTitle(form.getTitle());
		appVersion.setVersion(form.getVersion());
		appVersion.setAppFile(form.getAppFile());
		appVersion.setAppType(form.getAppType());
		appVersion.setForceUpdate(form.isForceUpdate());
		appVersion.setUpdateDescr(form.getUpdateDescr());
		appVersion.setRemark(form.getRemark());
	}
}
