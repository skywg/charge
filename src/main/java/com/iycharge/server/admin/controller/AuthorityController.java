package com.iycharge.server.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.web.CreateImageCode;
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

/**
 *  
 * @author bwang
 */
@Controller
public class AuthorityController {
    @Autowired
    private ManagerService managerService;
	@Autowired
    private RoleService roleService;
	@Autowired
    private PermissionService permissionService;
	@Autowired
	private MenuService menuService;
	@Resource
	private ManagerLogService managerLogService;
	
    @RequestMapping("/login")
    public String index(Model model,HttpServletRequest request,HttpServletResponse response,@ModelAttribute("message") String message,HttpSession session) throws IOException {
/*	 	DrawImage drawImage = new DrawImage();
    	DrawBean drawBean= drawImage.drawFunction("n");
    	model.addAttribute("drawBean", drawBean);
    	session.setAttribute("drawBean", drawBean);*/
    	return "admin/login";
    }
    
    
    @RequestMapping("/admin/draw")
    @ResponseBody
    public void draw(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	/*DrawImage drawImage = new DrawImage();
    	DrawBean drawBean= drawImage.drawFunction("n");
    	session.setAttribute("drawBean", drawBean);
    	return drawBean.getImage();*/
        
        //new DrawImage().drawFunction(request, response, "n");
        
        CreateImageCode.getCode(request, response);        
    }
    
    @RequestMapping(value="/auth", method=RequestMethod.POST)
    public String login(HttpSession session, HttpServletRequest request,
            @RequestParam("username") String username, 
            @RequestParam("password") String password,
            @RequestParam("yzm") String yzm,
            RedirectAttributes redirectAttributes) {
        Date now = new Date();
        
    	if(!StringUtils.hasText(username)||!StringUtils.hasText(password)) {
            redirectAttributes.addFlashAttribute("message", "用户名密码不能为空！");
            return "redirect:/login";
        }  
    	
    	//记录用户退出日志
        ManagerLog log = new ManagerLog();
        log.setLoginName(username);
        log.setIp(request.getRemoteAddr());
        log.setLogTime(now);
        log.setLogModule("PUBLIC");
        log.setLogType("LOGIN");
        log.setParams("登录");
        
        Manager manager = managerService.findByLoginName(username);
        if(manager == null || !manager.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))
        		||!username.equals(manager.getLoginName())) {
            //log.setParams("登录失败，用户名和密码不匹配！");
            log.setStatus(false);
            managerLogService.save(log);
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误！");
            return "redirect:/login";
        }else{
        	if(!manager.getStatus()){
        		log.setStatus(false);
                managerLogService.save(log);
                redirectAttributes.addFlashAttribute("username", username);
                redirectAttributes.addFlashAttribute("message", "用户已经被禁用！");
                return "redirect:/login";
        	}
        } 
        
        log.setRealName(manager.getRealname());
        
        String checkcode = (String)session.getAttribute("checkcode");
     	if(!checkcode.equalsIgnoreCase(yzm)){
     	     //log.setParams("登录失败，验证码不正确！");
     	     log.setStatus(false);
     	     managerLogService.save(log);
     	     redirectAttributes.addFlashAttribute("username", username);
     		 redirectAttributes.addFlashAttribute("message", "验证码不正确！");
             return "redirect:/login";
     	}
     	
        manager.setLastLoginIP(request.getRemoteAddr());
        manager.setLastLoginTime(now);
        managerService.save(manager);
        log.setStatus(true);
        managerLogService.save(log);
        //获取用户角色
        List<Manager> listmanager = new ArrayList<Manager>();
        List<Permission> listAlls = new ArrayList<Permission>();
        listmanager.add(manager);
        List<Role> list = roleService.findByManagers(listmanager);
    	manager.setRoles(list);
    	if(list!=null&&list.size()>0){
    		for(Role role:list){
    			List<Permission> listAll = new ArrayList<Permission>();
    			List<Role> temprole = new ArrayList<Role>();
    			temprole.add(role);
    			listAll = permissionService.findByRoles(temprole);
    			role.setPermissions(listAll);
    			listAlls.addAll(listAll);
    		}
    	}
    	//菜单
    	EntityUtil.menu.put(manager.getLoginName(), menuData(manager.getLoginName()));
    	List<String> listclickId = new ArrayList<String>();
    	if(listAlls!=null&&listAlls.size()>0){
			for(Permission per:listAlls){
				if(!StringUtils.isEmpty(per.getClickId())){
					listclickId.add(per.getClickId());
				}
			}
		}
    	//按钮
    	EntityUtil.buttonId.put(manager.getLoginName(), listclickId);
        session.setAttribute("user", manager);
        return "redirect:/admin/";
    }
    
    @RequestMapping("/admin/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
    	Manager manager = (Manager)session.getAttribute("user");
    	//菜单
    	if(EntityUtil.menu!=null&&EntityUtil.menu.size()>0){
    		EntityUtil.menu.remove(manager.getLoginName());
    	}
    	//buttonId
    	if(EntityUtil.buttonId!=null&&EntityUtil.buttonId.size()>0){
    		EntityUtil.buttonId.remove(manager.getLoginName());
    	}
    	
    	//记录用户退出日志
    	ManagerLog log = new ManagerLog();
    	log.setLoginName(manager.getLoginName());
    	log.setRealName(manager.getRealname());
    	log.setIp(request.getRemoteAddr());
    	log.setLogTime(new Date());
    	log.setLogModule("PUBLIC");
    	log.setLogType("LOGOUT");
    	log.setParams("退出");
    	log.setStatus(true);
    	managerLogService.save(log);
    	
        session.removeAttribute("user");
        
        return "admin/login";
    }
    
    public List<Menu> menuData(String loginName) {
    	//第一层节点
    	List<Menu> newlist = new ArrayList<Menu>();
    	if("admin".equals(loginName)){
    		List<Menu> listmenu = menuService.findListAll();
    		if(listmenu!=null&&listmenu.size()>0){
    			Iterator<Menu> it = listmenu.iterator();
            	while(it.hasNext()){
            		Menu entity = it.next();
            		newlist.add(entity);
            	}
    		}
    	}else{
    		Manager manager = managerService.findByLoginName(loginName);
        	List<Role> list = manager.getRoles();
        	if(list!=null&&list.size()>0){
        		Iterator<Role> itrole = list.iterator();
            	while(itrole.hasNext()){
            		Role role = itrole.next();
            		List<Menu> listmenu = role.getMenu();
            		if(listmenu!=null&&listmenu.size()>0){
            			Iterator<Menu> it = listmenu.iterator();
                    	while(it.hasNext()){
                    		Menu entity = it.next();
                    		newlist.add(entity);
                    	}
            		}
            	}
        	}
    	}
    	return newlist;
    }
}
