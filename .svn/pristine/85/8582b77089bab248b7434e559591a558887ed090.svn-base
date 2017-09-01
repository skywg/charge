package com.iycharge.server.domain.common.utils;

import java.util.Iterator;
import java.util.List;

import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.admin.MenuTreeBean;
import com.iycharge.server.domain.entity.admin.StateBean;

public class ChildNode {
	 public static void getChildrenNode(List<Menu> list,List<MenuTreeBean> newlist,List<Menu> mlist ){
	    	if(newlist!=null&&newlist.size()>0){
	    		Iterator<MenuTreeBean> it = newlist.iterator();
	    		while(it.hasNext()){
	    			MenuTreeBean entity = it.next();
	    			if(list!=null&&list.size()>0){
	    				Iterator<Menu> itchild = list.iterator();
	    	    		while(itchild.hasNext()){
	    	    			Menu itchildentity = itchild.next();
	    	    			if(Long.valueOf(entity.getTags())==itchildentity.getParentId()){
	    	    				MenuTreeBean menuTreeBean = new MenuTreeBean();
	    	        			menuTreeBean.setTags(String.valueOf(itchildentity.getId()));
	    	        			menuTreeBean.setText(itchildentity.getMenuName());
	            				if(mlist!=null&&mlist.size()>0){
	            					for(Menu me:mlist){
	        	    					if(itchildentity.getId()==me.getId()){
	        	    						StateBean statebean = new StateBean();
	        	    						statebean.setChecked(true);
	        	    						menuTreeBean.setState(statebean);
	        	    						break;
	        	    					}
	        	    				}
	            				}
	    	    				entity.getNodes().add(menuTreeBean);
	    	    				itchild.remove();
	    	    			}
	    	    		}
	    	    		getChildrenNode(list,entity.getNodes(),mlist);
	        		}
	    		}
	    	}
	    }
}
