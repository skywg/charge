package com.iycharge.server.admin.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.service.PermissionService;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//1步
        registry.addInterceptor(new HandlerInterceptor() {
            
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
            	
                HttpSession session =  request.getSession();
                String uri = request.getRequestURI();
                if("uri".equals("/share/pages/show")){
                	return true;
                }else{
                	 Manager user = (Manager)session.getAttribute("user");
                     if(session.getAttribute("user") == null) {
                         response.sendRedirect("/login");
                         return false;
                     }else{
                     	if(user.getLoginName().equals("admin")){
                     		return true;
                     	}else{
                     		if(!uri.equals("/admin/")&&!uri.equals("/admin/logout")
                     				&&!uri.equals("/admin/managers/editPass")
                     				&&!uri.contains("/api")&&!uri.contains("/hooks")
                     				&&!uri.contains("/admin/system/logs")&&!uri.contains("/app")
                     				&&!uri.contains("/admin/draw")&&!uri.contains("/oauth")
                     				&&!uri.contains("/ajax")&&!uri.contains("/auth")
                     				&&!uri.contains("/admin/ajax/devices/map")&&!uri.contains("/400")
                     				&&!uri.contains("/login")&&!uri.contains("/500")
                     				&&!uri.contains("/css")){
                             	List<Permission> listAll = new ArrayList<Permission>();
                             	List<Role> list = user.getRoles();
                             	if(list!=null&&list.size()>0){
                             		for(Role role:list){
                             			listAll.addAll(role.getPermissions());
                             		}
                             	}
                             	boolean flag = false;
                         		if(listAll!=null&&listAll.size()>0){
                         			for(Permission per:listAll){
                         				if(per.getUri().contains("*")){
                         		    		String us[] = per.getUri().split("\\*");
                         		    		//判断前缀访问路径
                         		    		if(uri.contains(us[0])){
                         		    			String usn[] = uri.split("/");
                         		    			String uris[] = per.getUri().split("/");
                         		    			//判断格式
                         		    			if(usn.length==uris.length){
                         		    				flag = true;
                                 					break;
                         		    			}
                         		    		}
                         		    	}else if(uri.equals(per.getUri())){
                         					flag = true;
                         					break;
                         				}
                         			}
                         		}
                         		if(flag){
                         			return true;
                         		}else{
                                     return false;
                         		}
                         	}else{
                         		return true;
                         	}
                     	}
                     }
                }
            }
            
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    ModelAndView modelAndView) throws Exception {
                    /*if(response.getStatus() == 404) {
                        modelAndView.setViewName("/errorpage/404");  
                    } else if (response.getStatus() == 500) {
                        modelAndView.setViewName("/errorpage/500");  
                    }*/
            }
            
            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                    throws Exception {
            }
        }).excludePathPatterns(
        		"/login", 
                "/auth",
                "/api/**",
                "/app/**",
                "/components/**",
                "/css/**",
                "/images/**",
                "/js/**",
                "/ajax/**",
                "/hooks/**",
                "/oauth/**",
                "/admin/draw",
                "/500",
                "/404",
                "/auth/error",
                "/share/pages/**"
        );
    }

    //2步
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/login").setViewName("admin/login");
    }
}
