package com.iycharge.server.admin.controller;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.admin.LogModule;
import com.iycharge.server.domain.entity.admin.LogType;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.ManagerService;
import com.iycharge.server.domain.service.MenuService;
import com.iycharge.server.domain.service.PermissionService;
import com.iycharge.server.domain.service.RoleService;

@Controller
@RequestMapping("/admin/system/roles")
public class RoleController {
	@Autowired
    private RoleService roleService;
	@Autowired
    private ManagerService managerService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private PermissionService permissionService;
	@Resource
	private ManagerLogService managerLogService;
	private static final String TEMPLATE_INDEX_FILE = "admin/system/role/index";
    private static final String TEMPLATE_ADD_FILE = "admin/system/role/add";
    private static final String TEMPLATE_EDIT_FILE = "admin/system/role/edit";
    @RequestMapping("/")
    public String index(Role role,Model model, @PageableDefault(direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        PageWrapper<Role> page = new PageWrapper<>(roleService.findAll(pageable), "/admin/system/roles/");
        model.addAttribute("page", page);
        return TEMPLATE_INDEX_FILE;
    }
    @RequestMapping("/add")
    public String add(HttpServletRequest request,Model model) {
    	HttpSession session =  request.getSession();
    	session.setAttribute("roleId", null);
    	session.setAttribute("roleflag", "roleflag");
        model.addAttribute("role", new Role());
        return "admin/system/role/add";
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpSession session,Model model,@ModelAttribute Role role, BindingResult result, RedirectAttributes redirectAttributes) {
    	 if (result.hasErrors()) {
             redirectAttributes.addFlashAttribute("failed", "添加角色时发生错误，请检查后重新尝试。");
             return "admin/system/role/add";
         }
    	 List<Menu> list = new ArrayList<Menu>();
    	 String empMenu = role.getTempMenu();
    	 if(StringUtils.isNotEmpty(empMenu)){
    		 String menu[] = empMenu.split(",");
    		 List<Long> ids = new ArrayList<Long>();
    		 for(String men:menu){
    			 ids.add(Long.valueOf(men));
    		 }
    		 list = menuService.findByIdIn(ids);
    		 role.setMenu(list);
    	 }
    	 /*List<Manager> listManager = new ArrayList<Manager>();
    	 String empmanager = role.getTempManager();
    	 if(StringUtils.isNotEmpty(empmanager)){
    		 String manager[] = empmanager.split(",");
    		 List<String> ids = new ArrayList<String>();
    		 for(String men:manager){
    			 ids.add(men);
    		 }
    		 listManager = managerService.findByLoginNameIn(ids);
    		 role.setManagers(listManager);
    	 }*/
    		List<Permission> listPermission = new ArrayList<Permission>();
    	   	String emppermission[]= role.getFormtemppermission();
    	   	if(emppermission!=null&&emppermission.length>0){
    	   		List<String> ids = new ArrayList<String>();
    	   		for(String men:emppermission){
    	   			 ids.add(men);
    	   		}
    	   		listPermission = permissionService.findByPkeyIn(ids);
    	   	}
    	   	listPermission.addAll(permissionService.findByClickIdIsNull());
	   		role.setPermissions(listPermission);
	   		roleService.save(role);
	   		
	   	    Manager manager = (Manager)session.getAttribute("user");
	   		ManagerLog log = new ManagerLog();
	    	log.setLoginName(manager.getLoginName());
	    	log.setRealName(manager.getRealname());
	    	log.setIp(request.getRemoteAddr());
	    	log.setLogTime(new Date());
	    	log.setLogModule(LogModule.ROLE.name());
	    	log.setLogType(LogType.ADD.name());
	    	log.setParams("添加角色"+role.getRoleName());
	    	log.setStatus(true);
    	    managerLogService.save(log);
         redirectAttributes.addFlashAttribute("success", MessageFormat.format("角色{0} 已经成功添加。", role.getRoleName()));
         return "redirect:/admin/system/roles/";
    }
    
    @RequestMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, HttpServletRequest request,Model model) {
    	HttpSession session =  request.getSession();
    	session.setAttribute("roleId", roleId);
    	session.setAttribute("roleflag", "roleflag");
    	Role role = roleService.findById(roleId);
    	/*List<Manager> ma = role.getManagers();
    	if(ma!=null&&ma.size()>0){
    		String str="";
    		for(Manager manager:ma){
    			str+=manager.getLoginName()+",";
    		}
    		str = str.substring(0, str.length() - 1);  
    		role.setTempManager(str);
    	}
    	role.setManagers(ma);*/
    	List<Menu> menu = role.getMenu();
    	if(menu!=null&&menu.size()>0){
    		String str="";
    		for(Menu me:menu){
    			str+=me.getId()+",";
    		}
    		str = str.substring(0, str.length() - 1);  
    		role.setTempMenu(str);
    	}
    	role.setMenu(menu);
    	List<Permission> permission = role.getPermissions();
    	if(permission!=null&&permission.size()>0){
    		String str="";
    		for(Permission me:permission){
    			if(StringUtils.isNotEmpty(me.getClickId())){
    				str+=me.getPkey()+",";
    			}
    		}
    		if(StringUtils.isNotEmpty(str)){
    			str = str.substring(0, str.length() - 1);  
    		}
    		role.setTemppermission(str);
    	}
    	role.setPermissions(permission);
    	session.setAttribute("rolesession", role);
        model.addAttribute("role", role);
        return TEMPLATE_EDIT_FILE;
    }
    
    
    @Transactional
    @RequestMapping(value = "/{roleId}", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpSession session,Model model,@PathVariable("roleId") Long roleId,
                         @Valid Role form, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "更新角色时发生错误，请检查后重新尝试。");
            return TEMPLATE_EDIT_FILE;
        }

        Role role = roleService.findById(roleId);
        role.setRoleName(form.getRoleName());
        role.setRoleDescr(form.getRoleDescr());
        
        List<Menu> list = new ArrayList<Menu>();
	   	 String empMenu = form.getTempMenu();
	   	 if(StringUtils.isNotEmpty(empMenu)){
	   		 String menu[] = empMenu.split(",");
	   		 List<Long> ids = new ArrayList<Long>();
	   		 for(String men:menu){
	   			 ids.add(Long.valueOf(men));
	   		 }
	   		 list = menuService.findByIdIn(ids);
	   		 role.setMenu(list);
	   	 }
	   	 /*List<Manager> listManager = new ArrayList<Manager>();
	   	 String empmanager = form.getTempManager();
	   	 if(StringUtils.isNotEmpty(empmanager)){
	   		 String manager[] = empmanager.split(",");
	   		 List<String> ids = new ArrayList<String>();
	   		 for(String men:manager){
	   			 ids.add(men);
	   		 }
	   		 listManager = managerService.findByLoginNameIn(ids);
	   		 role.setManagers(listManager);
	   	 }*/
	   	List<Permission> listPermission = new ArrayList<Permission>();
	   	String emppermission[]= form.getFormtemppermission();
	   	if(emppermission!=null&&emppermission.length>0){
	   		List<String> ids = new ArrayList<String>();
	   		for(String men:emppermission){
	   			 ids.add(men);
	   		}
	   		listPermission = permissionService.findByPkeyIn(ids);
	   	}
	   	listPermission.addAll(permissionService.findByClickIdIsNull());
   		role.setPermissions(listPermission);
	   	roleService.save(role);
	   	Manager manager = (Manager)session.getAttribute("user");
   		ManagerLog log = new ManagerLog();
    	log.setLoginName(manager.getLoginName());
    	log.setRealName(manager.getRealname());
    	log.setIp(request.getRemoteAddr());
    	log.setLogTime(new Date());
    	log.setLogModule(LogModule.ROLE.name());
    	log.setLogType(LogType.EDIT.name());
    	log.setParams("修改角色"+role.getRoleName());
    	log.setStatus(true);
	    managerLogService.save(log);
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("角色 {0} 更新成功。", role.getRoleName()));
        return "redirect:/admin/system/roles/";
    }
    
    /** 
     * @Description:把数组转换为一个用逗号分隔的字符串 ，以便于用in+String 查询 
     */  
    public static String converToString(String[] ig) {  
        String str = "";  
        if (ig != null && ig.length > 0) {  
            for (int i = 0; i < ig.length; i++) {  
                str += ig[i] + ",";  
            }  
        }  
        str = str.substring(0, str.length() - 1);  
        return str;  
    } 
}
