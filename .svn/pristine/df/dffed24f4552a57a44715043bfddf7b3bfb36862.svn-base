package com.iycharge.server.admin.controller;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.service.MenuService;
import com.iycharge.server.domain.service.PermissionService;
import com.iycharge.server.domain.service.RoleService;

@Controller
@RequestMapping("/admin/system/permissions")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	@Autowired
    private RoleService roleService;
	@Autowired
	private MenuService menuService;
	 // Template file path
    private static final String TEMPLATE_INDEX_FILE = "admin/system/permission/index";
    private static final String TEMPLATE_ADD_FILE = "admin/system/permission/add";
    private static final String TEMPLATE_EDIT_FILE = "admin/system/permission/edit";
    private static final String TEMPLATE_CHECK_FILE = "admin/system/permission/check";
    
    @ModelAttribute("menus")
    public List<Menu> findMenuListAll() {
        return menuService.findListAll();
    }
    
	@RequestMapping("/")
    public String index(Model model, @PageableDefault(direction = Sort.Direction.DESC, size=15) Pageable pageable){
        PageWrapper<Permission> page = new PageWrapper<>(permissionService.findAll(pageable), "/admin/system/permissions/");
        model.addAttribute("page", page);
        return TEMPLATE_INDEX_FILE;
    }
	
	/*@ModelAttribute("roleslist")
    public List<Role> findRoleListAll() {
		return roleRepository.findAll();
    }*/
	
	@RequestMapping("/add")
    public String add(Model model) {
		List<Role> list = roleService.findListAll();
		Permission permission = new Permission();
		permission.setRoles(list);
        model.addAttribute("permission", permission);
        return "admin/system/permission/add";
    }
	
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(Model model,@ModelAttribute Permission permission, BindingResult result, RedirectAttributes redirectAttributes) {
    	 if (result.hasErrors()) {
             redirectAttributes.addFlashAttribute("failed", "添加权限时发生错误，请检查后重新尝试。");
             return "admin/system/permission/add";
         }
    	 List<Role> list = new ArrayList<Role>();
    	 String[] roles = permission.getRole();
    	 if(roles!=null&&roles.length>0){
    		 for(String role:roles){
    			 Role r = roleService.findById(Long.valueOf(role));
    			 list.add(r);
    		 }
    		 permission.setRoles(list);
    	 }
    	 permissionService.save(permission);
         redirectAttributes.addFlashAttribute("success", MessageFormat.format("权限{0} 已经成功添加。", permission.getpName()));
         return "redirect:/admin/system/permissions/";
    }
    
    @RequestMapping("/edit/{pkey}")
    public String edit(@PathVariable("pkey") String pkey, Model model) {
    	//List<Role> listRoles = roleService.findListAll();
    	Permission permission = permissionService.findByPkey(pkey);
    	//List<Role> list = permission.getRoles();
    	/*if(permission!=null){
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
    	}*/
    	//permission.setRoles(listRoles);
        model.addAttribute("permission", permission);
        return TEMPLATE_EDIT_FILE;
    }
    
    @Transactional
    @RequestMapping(value = "/{pkey}", method = RequestMethod.POST)
    public String update(Model model,@PathVariable("pkey") String pkey,
                         @Valid Permission form, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "更新权限时发生错误，请检查后重新尝试。");
            return TEMPLATE_EDIT_FILE;
        }

        Permission permission = permissionService.findByPkey(pkey);
        permission.setClickId(form.getClickId());
       // permission.setParentKey(form.getpDescr());
        permission.setpDescr(form.getpDescr());
        permission.setPkey(form.getPkey());
        permission.setpName(form.getpName());
        permission.setMenu(form.getMenu());
       // List<Role> list = new ArrayList<Role>();
       /* String []ros = form.getRole();
	   	if(ros!=null&&ros.length>0){
	   		 for(String role:ros){
	   			 Role r = roleService.findById(Long.valueOf(role));
	   			 list.add(r);
	   		 }
	   		 permission.setRoles(list);
	   	}*/
        //permission.setRoles(form.getRoles());
        //permission.setRole(form.getRole());
        permission.setUri(form.getUri());
        permissionService.save(permission);
        
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("权限 {0} 更新成功。", permission.getpName()));
        return "redirect:/admin/system/permissions/";
    }
}
