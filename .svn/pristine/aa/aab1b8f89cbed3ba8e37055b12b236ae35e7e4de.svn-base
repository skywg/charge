package com.iycharge.server.admin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.common.utils.ChildNode;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.admin.MenuTreeBean;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.entity.admin.StateBean;
import com.iycharge.server.domain.service.ManagerService;
import com.iycharge.server.domain.service.MenuService;
import com.iycharge.server.domain.service.PermissionService;
import com.iycharge.server.domain.service.RoleService;

@Controller
@RequestMapping("/admin/menu")
public class DashboardMenuController {
	@Autowired
	private ManagerService managerService;
	@Autowired
	private MenuService menuService;
	@Autowired
    private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
    /**
     * 按钮权限控制
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/buttonId")
    @ResponseBody  
    public List<String> buttonId(HttpServletRequest request,Model model) {
    	HttpSession session =  request.getSession();	
    	Manager manager = (Manager)session.getAttribute("user");
    	return EntityUtil.buttonId.get(manager.getLoginName());
    }
   
    /**
     * 获取菜单option
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/menuData")
    @ResponseBody  
    public List<Menu> menuData(HttpServletRequest request,Model model) {
    	HttpSession session =  request.getSession();	
    	Manager manager = (Manager)session.getAttribute("user");
    	return EntityUtil.menu.get(manager.getLoginName());
    }
    /**
     * 暂时未用到获取菜单树
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/menuDataTree")
    @ResponseBody  
    public List<Menu> menuDataTree(HttpServletRequest request,Model model) {
    	//第一层节点
    	HttpSession session =  request.getSession();
    	List<Menu> newlist = new ArrayList<Menu>();
    	Manager manager = (Manager)session.getAttribute("user");
    	manager = managerService.findByLoginName(manager.getLoginName());
    	List<Role> list = manager.getRoles();
    	if(list!=null&&list.size()>0){
    		Iterator<Role> itrole = list.iterator();
        	while(itrole.hasNext()){
        		Role role = itrole.next();
        		List<Menu> listmenu = role.getMenu();
        		//第一层节点
        		if(listmenu!=null&&listmenu.size()>0){
        			Iterator<Menu> it = listmenu.iterator();
                	while(it.hasNext()){
                		Menu entity = it.next();
                		if(entity.getParentId()==0){
                			newlist.add(entity);
                			it.remove();
                		}
                	}
        		}
        		if(newlist!=null&&newlist.size()>0){
        			getChildren(listmenu,newlist);
        		}
        	}
    	}
    	return newlist;
    }
    
    public void getChildren(List<Menu> list,List<Menu> newlist){
    	if(newlist!=null&&newlist.size()>0){
    		Iterator<Menu> it = newlist.iterator();
    		while(it.hasNext()){
    			Menu entity = it.next();
    			if(list!=null&&list.size()>0){
    				Iterator<Menu> itchild = list.iterator();
    	    		while(itchild.hasNext()){
    	    			Menu itchildentity = itchild.next();
    	    			if(entity.getId()==itchildentity.getParentId()){
    	    				entity.getChildren().add(itchildentity);
    	    				itchild.remove();
    	    			}
    	    		}
    	    		getChildren(list,entity.getChildren());
        		}
    		}
    	}
    }
    /**
     * 按钮树
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("permissionTree")
    @ResponseBody  
    public List<MenuTreeBean> permissionTree(HttpServletRequest request,Model model) {
    	List<MenuTreeBean> newlist = new ArrayList<MenuTreeBean>();
    	List<Permission> listpermission = permissionService.findListAll();
    	List<Menu> listmenu = menuService.findListAll();
    	HttpSession session =  request.getSession();
    	/*Object roleId = session.getAttribute("roleId");
    	if(roleId!=null){
    		Role ro = roleService.findById((Long)roleId);
    		List<Permission> li = ro.getPermissions();
    		if(listmenu!=null&&listmenu.size()>0){
    			Iterator<Menu> it = listmenu.iterator();
    			while(it.hasNext()){
    				Menu m = it.next();
    				MenuTreeBean menuTreeBean = new MenuTreeBean();
    				if(m.getParentId()==0){
    					it.remove();
    					continue;
    				}else{
	        			menuTreeBean.setText(m.getMenuName());
    				}
    				List<MenuTreeBean> persion = new ArrayList<MenuTreeBean>();
    				if(listpermission!=null&&listpermission.size()>0){
    					Iterator<Permission> itpersion = listpermission.iterator();
    					while(itpersion.hasNext()){
    						Permission p = itpersion.next();
    						if(StringUtils.isNotEmpty(p.getClickId())){
    							if(m.getId()==p.getMenu().getId()){
        							MenuTreeBean bean = new MenuTreeBean();
        							bean.setTags(p.getPkey());
        							bean.setText(p.getpName());
        							if(li!=null&&li.size()>0){
        								Iterator<Permission> pr = li.iterator();
        								boolean flag = false;
        								while(pr.hasNext()){
        									Permission ps = pr.next();
        									if(p.getPkey().equals(ps.getPkey())){
        										StateBean statebean = new StateBean();
        			    						statebean.setChecked(true);
        			    						bean.setState(statebean);
        			    						flag=true;
        			    						break;
        									}
        								}
        								if(flag){
        									StateBean statebeans = new StateBean();
                							statebeans.setChecked(true);
                							menuTreeBean.setState(statebeans);
        								}
        							}
        							persion.add(bean);
        						}
    						}
    					}
    					if(persion!=null&&persion.size()>0){
    						menuTreeBean.getNodes().addAll(persion);
    					}
    				}
    				if(persion!=null&&persion.size()>0){
    					newlist.add(menuTreeBean);
					}
    			}
    		}
    	}else{
    	
    	}*/
    		if(listmenu!=null&&listmenu.size()>0){
    			Iterator<Menu> it = listmenu.iterator();
    			while(it.hasNext()){
    				Menu m = it.next();
    				MenuTreeBean menuTreeBean = new MenuTreeBean();
    				if(m.getParentId()==0){
    					it.remove();
    					continue;
    				}else{
	        			menuTreeBean.setText(m.getMenuName());
    				}
    				List<MenuTreeBean> persion = new ArrayList<MenuTreeBean>();
    				if(listpermission!=null&&listpermission.size()>0){
    					Iterator<Permission> itpersion = listpermission.iterator();
    					while(itpersion.hasNext()){
    						Permission p = itpersion.next();
    						if(StringUtils.isNotEmpty(p.getClickId())){
    							if(m.getId()==p.getMenu().getId()){
        							MenuTreeBean bean = new MenuTreeBean();
        							bean.setTags(p.getPkey());
        							bean.setText(p.getpName());
        							persion.add(bean);
        						}
    						}
    					}
    					if(persion!=null&&persion.size()>0){
    						menuTreeBean.getNodes().addAll(persion);
    					}
    				}
    				if(persion!=null&&persion.size()>0){
    					newlist.add(menuTreeBean);
					}
    			}
    		}
    	session.setAttribute("roleflag", null);
    	return newlist;
    }
    /**
     * 用户树
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("managerTree")
    @ResponseBody  
    public List<MenuTreeBean> managerTree(HttpServletRequest request,Model model) {
    	List<MenuTreeBean> newlist = new ArrayList<MenuTreeBean>();
    	List<Manager> listmenu = managerService.findAll();
    	HttpSession session =  request.getSession();
    	Object roleId = session.getAttribute("roleId");
    	
    	if(roleId!=null){
    		Role ro = roleService.findById((Long)roleId);
    		List<Manager> list = ro.getManagers();
    		//第一层节点
    		if(listmenu!=null&&listmenu.size()>0){
    			Iterator<Manager> it = listmenu.iterator();
            	while(it.hasNext()){
            		Manager entity = it.next();
            		MenuTreeBean menuTreeBean = new MenuTreeBean();
        			menuTreeBean.setTags(String.valueOf(entity.getLoginName()));
        			menuTreeBean.setText(entity.getRealname());
            		for(Manager ma:list){
            			if(!"admin".equals(entity.getLoginName())){
            				if(ma.getLoginName().equals(entity.getLoginName())){
                				StateBean statebean = new StateBean();
        						statebean.setChecked(true);
        						menuTreeBean.setState(statebean);
        						break;
                			}
            			}
            		}
            		if(!"admin".equals(entity.getLoginName())){
            			newlist.add(menuTreeBean);
            		}
            	}
    		}
    	}else{
    		//第一层节点
    		if(listmenu!=null&&listmenu.size()>0){
    			Iterator<Manager> it = listmenu.iterator();
            	while(it.hasNext()){
            		Manager entity = it.next();
            		if(!"admin".equals(entity.getLoginName())){
            			MenuTreeBean menuTreeBean = new MenuTreeBean();
            			menuTreeBean.setTags(String.valueOf(entity.getLoginName()));
            			menuTreeBean.setText(entity.getRealname());
            			newlist.add(menuTreeBean);
            		}
            	}
    		}
    	}
    	session.setAttribute("roleflag", null);
    	return newlist;
    }
    /**
     * 菜单树
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("meunTree")
    @ResponseBody  
    public List<MenuTreeBean> meunTree(HttpServletRequest request,Model model) {
    	HttpSession session =  request.getSession();
    	List<MenuTreeBean> newlist = new ArrayList<MenuTreeBean>();
    	List<Menu> mlist = new ArrayList<Menu>();
    	//Manager manager = (Manager)session.getAttribute("user");
    	Object roleId = session.getAttribute("roleId");
    	Role ro = new Role();
    	if(roleId!=null){
    		ro = (Role)session.getAttribute("rolesession");
    		//ro = roleService.findById((Long)roleId);
    		//manager = managerService.findByLoginName(manager.getLoginName());
    		List<Menu> listmenu = menuService.findListAll();
    		if(listmenu!=null&&listmenu.size()>0){
    			Iterator<Menu>it = listmenu.iterator();
    			while(it.hasNext()){
    				Menu menu = it.next();
    				if(menu.getParentId()==0){
            			MenuTreeBean menuTreeBean = new MenuTreeBean();
            			menuTreeBean.setTags(String.valueOf(menu.getId()));
            			menuTreeBean.setText(menu.getMenuName());
            			if(ro!=null){
            				mlist = ro.getMenu();
            				if(mlist!=null&&mlist.size()>0){
            					for(Menu me:mlist){
        	    					if(menu.getId()==me.getId()){
        	    						StateBean statebean = new StateBean();
        	    						statebean.setChecked(true);
        	    						menuTreeBean.setState(statebean);
        	    						break;
        	    					}
        	    				}
            				}
            			}
            			it.remove();
            			newlist.add(menuTreeBean);
            		}
    			}
    		}
        	if(newlist!=null&&newlist.size()>0){
    			ChildNode.getChildrenNode(listmenu,newlist,mlist);
    		}
    	}else{
        	List<Menu> listmenu = menuService.findListAll();
    		//第一层节点
    		if(listmenu!=null&&listmenu.size()>0){
    			Iterator<Menu> it = listmenu.iterator();
            	while(it.hasNext()){
            		Menu entity = it.next();
            		if(entity.getParentId()==0){
            			MenuTreeBean menuTreeBean = new MenuTreeBean();
            			menuTreeBean.setTags(String.valueOf(entity.getId()));
            			menuTreeBean.setText(entity.getMenuName());
            			newlist.add(menuTreeBean);
            			it.remove();
            		}
            	}
    		}
    		if(newlist!=null&&newlist.size()>0){
    			ChildNode.getChildrenNode(listmenu,newlist,new ArrayList<Menu>());
    		}
    	}
    	session.setAttribute("roleflag", null);
    	return newlist;
    }
    
}
