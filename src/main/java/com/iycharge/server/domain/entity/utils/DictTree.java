package com.iycharge.server.domain.entity.utils;

import java.util.ArrayList;
import java.util.List;

import com.iycharge.server.domain.entity.dict.DictData;
/**
 * 用于前台构造级联效果
 * @author uwayxs
 *
 */
public class DictTree {
	private String dictValue;
	private String descr;
	private String dictKey;
	private List<DictTree> childs;
	private DictTree parent;
	
	
	/**
	 * 构造树
	 * @return
	 */
	public static List<DictTree> toDictTreeByDictData(List<DictData> dds){
		return toDictTree(dds,initList(dds.size()));
	}
	/**
	 * 构造树
	 * @return
	 */
	private static List<DictTree> toDictTree(List<DictData> dds,List<DictTree> dts){
		for(int i = 0 ; i < dds.size() ; i ++){
			DictData dd = dds.get(i);
			DictTree dt = dts.get(i);
			setBean(dd, dt);
			if(dd.getParent()!=null){
				DictTree parent = new DictTree();
				setBean(dd.getParent(), parent);
				dt.setParent(parent);
			}
			if(dd.getDictDataList()!=null&&dd.getDictDataList().size()!=0){
				List<DictTree> childs = initList(dd.getDictDataList().size());
				dt.setChilds(toDictTree(dd.getDictDataList(), childs));
			}
		}
		return dts;
	}
	/**
	 * 设置DictTree属性
	 * @param dd
	 * @param dt
	 */
	private static void setBean(DictData dd , DictTree dt){
		dt.setDescr(dd.getDescr());
		dt.setDictKey(dd.getDictKey());
		dt.setDictValue(dd.getDictValue());
	}
	
	/**
	 * 根据长度初始化DictTree
	 * @param size
	 * @return
	 */
	private static List<DictTree> initList(int size){
		List<DictTree> lst = new ArrayList<>();
		for(int i = 0 ; i < size ; i++){
			DictTree tmp = new DictTree();
			lst.add(tmp);
		}
		return lst;
	}

	
	
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDictKey() {
		return dictKey;
	}
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}
	public List<DictTree> getChilds() {
		return childs;
	}
	public void setChilds(List<DictTree> childs) {
		this.childs = childs;
	}
	public DictTree getParent() {
		return parent;
	}
	public void setParent(DictTree parent) {
		this.parent = parent;
	}
	
}
