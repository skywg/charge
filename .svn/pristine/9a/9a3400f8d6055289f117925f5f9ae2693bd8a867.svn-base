package com.iycharge.server.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.admin.LogModule;
import com.iycharge.server.domain.entity.admin.LogType;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.entity.admin.RoleBean;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.ManagerService;
import com.iycharge.server.domain.service.RoleService;



/**
 * 系统用户管理
 * @author bwang
 */
@Controller
@RequestMapping("/admin/managers")
public class ManagerController {
    
    @Autowired
    private ManagerService managerService;
	@Autowired
    private RoleService roleService;
	@Resource
	private ManagerLogService managerLogService;
    private final static String EDIT_PASSWORD_FIELD="admin/system/manager/modifyPassword";
    
    @ModelAttribute("roleslist")
    public List<Role> findRoleListAll() {
		return roleService.findListAll();
    }
    /**
     * 列表展示
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request,Manager manager,@PageableDefault(sort = "loginName", direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model
    		) {
    	 HttpSession session = request.getSession();
    	 session.removeAttribute("sessionManager");
    	PageWrapper<Manager> page = new PageWrapper<>(managerService.findAll(pageable), "/admin/managers/");
        List<Role> lists = roleService.findListAll();
        List<Manager> list = page.getContent();
        if(list!=null&&list.size()>0){
        	for(int i =0;i<list.size();i++){
   			 	List<RoleBean> roleBeanList = new ArrayList<RoleBean>();
        		if(lists!=null&&lists.size()>0){
        			for(Role role:lists){
        				List<Manager> ll = role.getManagers();
        				boolean ff = false;
        				if(ll!=null&&ll.size()>0){
        					for(Manager m:ll){
        						if(list.get(i).getLoginName().equals(m.getLoginName())){
        							ff = true;
        							break;
        						}
        					}
        				}
        				RoleBean roleBean = new RoleBean();
    					roleBean.setFlag(ff);
    					roleBean.setRoleId(role.getRoleId());
    					roleBean.setRoleName(role.getRoleName());
    					roleBeanList.add(roleBean);
        			}
        			list.get(i).setRoleBeanList(roleBeanList);
        		}
        	}
        }
        model.addAttribute("page", page);
        model.addAttribute("manager", manager);
        return "admin/system/manager/index";
    }
    
    
    /**
     * 根据登陆名查找用户信息
     * @param model
     * @param loginName
     * @param pageable
     * @return
     */
    @RequestMapping("/search")
    public String search(HttpServletRequest request,Manager manager,Model model, String loginName, Pageable pageable) {
    	HttpSession session = request.getSession();
        if("1".equals(manager.getFlag())){
        	session.setAttribute("sessionManager", manager);
        }else{
        	Manager ch = (Manager)session.getAttribute("sessionManager");
       	 if(ch!=null){
       		manager = ch;
       	 }
        }     
    	PageWrapper<Manager> page = new PageWrapper<>(managerService.findByLoginName(manager.getLoginName(),pageable), "/admin/managers/search?loginName="+loginName);
    	 List<Role> lists = roleService.findListAll();
         List<Manager> list = page.getContent();
         if(list!=null&&list.size()>0){
         	for(int i =0;i<list.size();i++){
    			 	List<RoleBean> roleBeanList = new ArrayList<RoleBean>();
         		if(lists!=null&&lists.size()>0){
         			for(Role role:lists){
         				List<Manager> ll = role.getManagers();
         				if(ll!=null&&ll.size()>0){
         					boolean ff = false;
         					for(Manager m:ll){
         						if(list.get(i).getLoginName().equals(m.getLoginName())){
         							ff = true;
         							break;
         						}
         					}
         					RoleBean roleBean = new RoleBean();
         					roleBean.setFlag(ff);
         					roleBean.setRoleId(role.getRoleId());
         					roleBean.setRoleName(role.getRoleName());
         					roleBeanList.add(roleBean);
         				}
         			}
         			list.get(i).setRoleBeanList(roleBeanList);
         		}
         	}
         }
         model.addAttribute("manager", manager);
    	 model.addAttribute("page", page);
         return "admin/system/manager/index";
    }
    
    
    /**
     * 用户进入添加界面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
    	model.addAttribute("manager", new Manager());
    	model.addAttribute("action", "add");
        return "admin/system/manager/add";
    }
    
    /**
     * 保存新建用户信息
     * @param account
     * @param result
     * @param redirectAttributes
     * @return
     */
    @Transactional
    @RequestMapping(value = "/managerAdd", method = RequestMethod.POST)
    public String save(Model mode ,HttpServletRequest request, HttpSession session,@ModelAttribute Manager manager, RedirectAttributes redirectAttributes) {
        Manager form=managerService.findByLoginName(manager.getLoginName());
        if(form!=null){
        	List<Role> list = new ArrayList<Role>();
    	   	String[] roles = manager.getRole();
    	   	if(roles!=null&&roles.length>0){
    	   		for(String role:roles){
    	   			Role r = roleService.findById(Long.valueOf(role));
    	   			r.setFlag(true);
    	   			list.add(r);
    	   		}
    	   		manager.setRoles(list);
    	   	}
        	mode.addAttribute("manager",manager);
        	mode.addAttribute("action","add");
        	mode.addAttribute("message", "添加新用户时登录名重复，请检查后重新尝试。");
            return "admin/system/manager/add";
        }
        
        String md5Pas=DigestUtils.md5DigestAsHex(manager.getPassword().getBytes());
        manager.setPassword(md5Pas);
        manager.setRegisterTime(new Date());
        List<Role> list = new ArrayList<Role>();
	   	String[] roles = manager.getRole();
	   	if(roles!=null&&roles.length>0){
	   		for(String role:roles){
	   			Role r = roleService.findById(Long.valueOf(role));
	   			list.add(r);
	   		}
	   		manager.setRoles(list);
	   	}
        managerService.save(manager);
    	ManagerLog log = new ManagerLog();
    	log.setLoginName(manager.getLoginName());
    	log.setRealName(manager.getRealname());
    	log.setIp(request.getRemoteAddr());
    	log.setLogTime(new Date());
    	log.setLogModule(LogModule.MANAGER.name());
    	log.setLogType(LogType.ADD.name());
    	log.setParams("添加用户"+manager.getLoginName());
    	log.setStatus(true);
    	managerLogService.save(log);
        redirectAttributes.addFlashAttribute("message", MessageFormat.format("{0}用户 已经成功添加。", manager.getLoginName()));
        return "redirect:/admin/managers/search";
    }
    
    @Transactional
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(HttpServletRequest request,@ModelAttribute Manager manager, RedirectAttributes redirectAttributes) {
        Manager form=managerService.findByLoginName(manager.getLoginName());
        if(form!=null){
        	 String md5Pas=DigestUtils.md5DigestAsHex(manager.getPassword().getBytes());
        	 form.setPassword(md5Pas);
        	 form.setRegisterTime(new Date());
             managerService.save(form);
             ManagerLog log = new ManagerLog();
         	log.setLoginName(manager.getLoginName());
         	log.setRealName(manager.getRealname());
         	log.setIp(request.getRemoteAddr());
         	log.setLogTime(new Date());
         	log.setLogModule(LogModule.MANAGER.name());
         	log.setLogType(LogType.EDIT.name());
         	log.setParams("密码重置用户"+manager.getLoginName());
         	log.setStatus(true);
         	managerLogService.save(log);
             redirectAttributes.addFlashAttribute("message", MessageFormat.format("{0}用户 已经成功添加。", manager.getLoginName()));
        }
        return "redirect:/admin/managers/search";
    }
   
    @RequestMapping("/editStatus/{loginName}")
    public String editStatus(HttpServletRequest request, HttpSession session,@PathVariable("loginName") String loginName, RedirectAttributes redirectAttributes) {
        Manager form=managerService.findByLoginName(loginName);
        if(form!=null){
        	 if(form.getStatus()){
        		form.setStatus(false);
        	 }else{
        		 form.setStatus(true);
        	 }
        	 form.setRegisterTime(new Date());
             managerService.save(form);
             Manager manager = (Manager)session.getAttribute("user");
	     		ManagerLog log = new ManagerLog();
	     	 	log.setLoginName(manager.getLoginName());
	     	 	log.setRealName(manager.getRealname());
	     	 	log.setIp(request.getRemoteAddr());
	     	 	log.setLogTime(new Date());
	     	 	log.setLogModule(LogModule.MANAGER.name());
	     	 	if(form.getStatus()){
	     	 		log.setLogType(LogType.ENABLE.name());
	     	 		log.setParams("启用用户"+form.getRealname());
	     	 	}else{
	     	 		log.setLogType(LogType.DISABLE.name());
	     	 		log.setParams("禁用用户"+form.getRealname());
	     	 	}
	     	 	log.setStatus(true);
	     	    managerLogService.save(log);
             redirectAttributes.addFlashAttribute("message", MessageFormat.format("{0}用户 已经修改。", form.getLoginName()));
        }
        return "redirect:/admin/managers/search";
    }
    
    /**
     * 用户进入密码修改页面
     * 
     * @return
     */
    @RequestMapping("/editPass")
    public String editPass() {
        return EDIT_PASSWORD_FIELD;
    }
    
    
    @ResponseBody
    @RequestMapping(value="/modifyPwd",method=RequestMethod.POST)
    public void editPass(@RequestParam("newPass")   String newPass,
			    		@RequestParam("secondPass")  String secondPass,
			    		@RequestParam("pwd")  String pwd,
    			        HttpSession session,HttpServletResponse response
    					  ) throws IOException {
        Gson gson=new Gson();
    	PrintWriter out=response.getWriter();
    	//设置返回数据的字符编码格式
    	response.setCharacterEncoding("utf-8");
    	Map<String, String> map=new HashMap<String, String>();
    	Manager manager=(Manager) session.getAttribute("user");
    	//获得原密码
    	String oldPwd=manager.getPassword();
    	//输入的密码进行加密后对比
    	 String md5Pas=DigestUtils.md5DigestAsHex(pwd.getBytes());
    	 //新密码加密
    	 String md5NewPas=DigestUtils.md5DigestAsHex(newPass.getBytes());
    	 String patternStr="((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}"; 
    	  boolean result = Pattern.matches(patternStr, newPass); 
    	
    		 if(!oldPwd.equals(md5Pas)){
    	    	  map.put("message","你输入的密码与原密码不匹配，请重试！" );
    	    	  out.print(gson.toJson(map));
    	      }else if(!result){
    	    	  map.put("message","你输入的新密码格式不规范，请重试！" );
    	       	  out.print(gson.toJson(map));
    	      }else if(md5NewPas.equals(oldPwd)){
    	    	  map.put("message","你输入的新密码与旧密码相同，请重试！" );
    	    	  out.print(gson.toJson(map));
    	      }else if(!newPass.equals(secondPass)){
    	    	  map.put("message","你两次输入的新密码不相同，请重试！" );
    	    	  out.print(gson.toJson(map));
    	      }else{
    	    	  manager.setPassword(md5NewPas);
    	    	  managerService.save(manager);
    	    	  map.put("message","success");
    	    	  out.print(gson.toJson(map));
    	      }
    	 
	     out.flush();
	     out.close();
    }
    
    
    /**
     * 删除用户
     * @param loginName
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping("/del/{loginName}")
    public String del(HttpServletRequest request, HttpSession session,@PathVariable("loginName") String loginName) {
    	Manager entity = managerService.findByLoginName(loginName);
    	if(null!=entity){
    		managerService.del(loginName);
        }
    	Manager manager = (Manager)session.getAttribute("user");
    	ManagerLog log = new ManagerLog();
    	log.setLoginName(manager.getLoginName());
    	log.setRealName(manager.getRealname());
    	log.setIp(request.getRemoteAddr());
    	log.setLogTime(new Date());
    	log.setLogModule(LogModule.MANAGER.name());
    	log.setLogType(LogType.DELETE.name());
    	log.setParams("删除用户"+manager.getLoginName());
    	log.setStatus(true);
    	managerLogService.save(log);
    	return "redirect:/admin/managers/search";
    }
    //更新manager信息
    @RequestMapping("/edit_ajax")
    @ResponseBody
    public Map<String,String> edit_ajax(Manager form,HttpServletRequest request){
    	Manager manager = managerService.findByLoginName(form.getLoginName());
    	Map<String,String> data = new HashMap<>();
    	manager.setEmail(form.getEmail());
    	manager.setRealname(form.getRealname());
    	manager.setTelephone(form.getTelephone());
    	try{
    		manager = managerService.save(manager);
    	}catch(Exception e){
    		data.put("errMsg", "更新信息失败");
    		return data;
    	}
    	data.put("sucMsg", "更新成功!");
    	data.put("realName", form.getRealname()!=null?form.getRealname():"");
    	data.put("email", form.getEmail()!=null?form.getEmail():"");
    	data.put("phone", form.getTelephone()!=null?form.getTelephone():"");
    	return data;
    	
    }
    
    
    /**
     * 用户退出查看页面
     * @param loginName
     * @return
     */
    @RequestMapping("/exit")
    public String editPwd() {
        return "redirect:/admin/managers/search";
    }
    
    
    /**
     * 进入查看的页面
     * @param loginName
     * @param model
     * @return
     */
    @RequestMapping("/check/{loginName}")
    public String check(@PathVariable("loginName") String loginName, Model model) {
    	Manager entity = managerService.findByLoginName(loginName);
    	model.addAttribute("manager",entity);
    	return "admin/system/manager/check";
    }
    /**
     * 进入编辑的页面
     * @param loginName
     * @param model
     * @return
     */
    @RequestMapping("/edit/{loginName}")
    public String edit(@PathVariable("loginName") String loginName, Model model) {
    	Manager entity = managerService.findByLoginName(loginName);
    	List<Role> listRoles = roleService.findListAll();
    	if(entity!=null){
       	   //获取用户角色
           List<Manager> listmanager = new ArrayList<Manager>();
           listmanager.add(entity);
           List<Role> list = roleService.findByManagers(listmanager);
    		for(Role re:listRoles){
        		if(list!=null&&list.size()>0){
        			for(Role role:list){
        				if(re.getRoleId()==role.getRoleId()){
        					re.setFlag(true);
        					break;
        				}
        			}
        		}
    		}
    	}
    	entity.setRoles(listRoles);
    	model.addAttribute("manager",entity);
    	model.addAttribute("action", "edit");
    	return "admin/system/manager/edit";
    }
    
    /**
     * 保存编辑页面
     * @param model
     * @param loginName
     * @param form
     * @param result
     * @param redirectAttributes
     * @return
     */
    
    @Transactional
    @RequestMapping(value = "/ed/{loginName}", method = RequestMethod.POST)
    public String update(HttpServletRequest request,
    						HttpSession session,
    		             @PathVariable("loginName") String loginName,
                         @Valid Manager form) {
        Manager manager = managerService.findByLoginName(loginName);
                 Date date=manager.getRegisterTime();
                 form.setRegisterTime(date);
                 String pwd=manager.getPassword();
                 String md5Pas=DigestUtils.md5DigestAsHex(form.getPassword().getBytes());
                 if(!form.getPassword().equals(pwd)){
                	  form.setPassword(md5Pas);
                 }
                form.setLastLoginTime(new Date());
                List<Role> list = new ArrayList<Role>();
                String []ros = form.getRole();
          	   	if(ros!=null&&ros.length>0){
          	   		 for(String role:ros){
          	   			 Role r = roleService.findById(Long.valueOf(role));
          	   			 list.add(r);
          	   		 }
          	   	form.setRoles(list);
          	   	}
        		 managerService.save(form);
        		 Manager managers = (Manager)session.getAttribute("user");
    	    	ManagerLog log = new ManagerLog();
    	    	log.setLoginName(managers.getLoginName());
    	    	log.setRealName(managers.getRealname());
    	    	log.setIp(request.getRemoteAddr());
    	    	log.setLogTime(new Date());
    	    	log.setLogModule(LogModule.MANAGER.name());
    	    	log.setLogType(LogType.EDIT.name());
    	    	log.setParams("修改用户"+manager.getLoginName());
    	    	log.setStatus(true);
        	    managerLogService.save(log);
                return "redirect:/admin/managers/search";
        	
        
    }
    
    
    @Transactional
    @RequestMapping(value = "/loadRole/{loginName}", method = RequestMethod.POST)
    public String loadRole(
    		             @PathVariable("loginName") String loginName,
                         @Valid Manager form) {
    			Manager manager = managerService.findByLoginName(loginName);
                List<Role> list = new ArrayList<Role>();
                String []ros = form.getRole();
          	   	if(ros!=null&&ros.length>0){
          	   		 for(String role:ros){
          	   			 Role r = roleService.findById(Long.valueOf(role));
          	   			 list.add(r);
          	   		 }
          	   		 manager.setRoles(list);
          	   	}
        		 managerService.save(manager);
                return "redirect:/admin/managers/search";
        	
        
    }
    
  
    }
