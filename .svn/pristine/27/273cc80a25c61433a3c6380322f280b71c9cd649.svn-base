package com.iycharge.server.admin.controller;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.api.util.Result;
import com.iycharge.server.ccu.service.ParamSettingService;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerBean;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.price.ParamSetting;
import com.iycharge.server.domain.entity.price.ParamSettingResult;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.OperatorService;
import com.iycharge.server.domain.service.ParamSettingResultService;
import com.iycharge.server.domain.service.ParamSettingSService;
import com.iycharge.server.domain.service.ParamTemplateService;
import com.iycharge.server.domain.service.StationService;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/paramSettings/")
public class ParamSettingController {

	@Autowired
	private ParamSettingSService paramSettingService;
	@Autowired
	private ParamSettingService paramSettingServices;
	@Autowired
	private ParamTemplateService paramTemplateService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private StationService stationService;
	@Autowired
	private ParamSettingResultService paramSettingResultService;
	@Autowired
	private ChargerService chargerService;
	@Resource
	private ManagerLogService managerLogService;

	// Template file path
	private static final String TEMPLATE_INDEX_FILE = "admin/paramSetting/index";
	private static final String TEMPLATE_ADD_FILE = "admin/paramSetting/add";
	private static final String TEMPLATE_EDIT_FILE = "admin/paramSetting/edit";
	private static final String TEMPLATE_CHECK_FILE = "admin/paramSetting/check";

	@ModelAttribute("types")
	public List<DictData> types() {
		List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.PARAM_TYPE);
		return lst != null ? lst : (new ArrayList<DictData>());
	}

	/**
	 * 所属运营商
	 * 
	 * @return
	 */
	@ModelAttribute("operators")
	public List<Operator> findOperatorListAll() {
		List<Operator> list = operatorService.findListAll();
		if (list != null && list.size() > 0) {
			for (Operator operator : list) {
				String name = operator.getName();
				String code = operator.getCode();
				String codeAndName = name + "(" + code + ")";
				operator.setCodeAndName(codeAndName);
			}
		}
		return list;
	}

	/**
	 * 所属充电桩
	 * 
	 * @return
	 */
	// @ModelAttribute("chargers")
	public List<Charger> findChargerListAll(String id) {
		List<Charger> list = new ArrayList<Charger>();
		if (StringUtils.isNotEmpty(id)) {
			list = chargerService.findAll();
			String ids[] = id.split(",");
			if (list != null && list.size() > 0) {
				Iterator<Charger> it = list.iterator();
				while (it.hasNext()) {
					Charger ch = it.next();
					String name = ch.getName();
					String code = ch.getCode();
					String codeAndName = name + "(" + code + ")";
					ch.setCodeAndName(codeAndName);
					for (String idd : ids) {
						if (String.valueOf(ch.getId()).equals(idd)) {
							it.remove();
							break;
						}
					}
				}
			}
		} else {
			list = chargerService.findAll();
			if (list != null && list.size() > 0) {
				Iterator<Charger> it = list.iterator();
				while (it.hasNext()) {
					Charger ch = it.next();
					String name = ch.getName();
					String code = ch.getCode();
					String codeAndName = name + "(" + code + ")";
					ch.setCodeAndName(codeAndName);
				}
			}
		}
		return list;
	}

	// @ModelAttribute("chargersY")
	public List<Charger> findChargerListAllY(String id) {
		List<Charger> lists = new ArrayList<Charger>();
		if (StringUtils.isNotEmpty(id)) {
			List<Charger> list = chargerService.findAll();
			String ids[] = id.split(",");
			if (list != null && list.size() > 0) {
				for (Charger ch : list) {
					String name = ch.getName();
					String code = ch.getCode();
					String codeAndName = name + "(" + code + ")";
					ch.setCodeAndName(codeAndName);
					for (String idd : ids) {
						if (String.valueOf(ch.getId()).equals(idd)) {
							lists.add(ch);
							break;
						}
					}
				}
			}
		}
		return lists;
	}

	/**
	 * 所属充电站
	 * 
	 * @return
	 */
	@ModelAttribute("stations")
	public List<Station> findStationListAll() {
		List<Station> list = stationService.findListAll();
		if (list != null && list.size() > 0) {
			for (Station station : list) {
				String name = station.getName();
				String code = station.getCode();
				String codeAndName = name + "(" + code + ")";
				station.setCodeAndName(codeAndName);
			}
		}
		return list;
	}

	@RequestMapping("paramTemplate")
	@ResponseBody
	public Result<List<ParamTemplate>> findPrice(String type) {
		List<ParamTemplate> paramTemplates = paramTemplateService.findByType(type);
		List<ParamTemplate> paramTemplates2 = new ArrayList<>();
		for (ParamTemplate paramTemplate : paramTemplates) {
			if (paramTemplate.getStatus().equals("VALID")) {
				paramTemplates2.add(paramTemplate);
			}
		}
		Result<List<ParamTemplate>> result = new Result<>();
		result.setMsg("ok");
		result.setData(paramTemplates2);
		return result;
	}
	@RequestMapping("/")
	public String index(HttpServletRequest request,Model model, @ModelAttribute ParamSetting paramSetting,
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
		 HttpSession session = request.getSession();
    	 session.removeAttribute("sessionParamSetting");
		PageWrapper<ParamSetting> page = new PageWrapper<>(paramSettingService.findAll(pageable),
				"/admin/paramSettings/");
		List<ParamSetting> list = page.getContent();
		if (list != null && list.size() > 0) {
			List<Charger> listCharger = chargerService.findAll();
			for (ParamSetting pa : list) {
				if ("true".equals(pa.getSendFlag())) {
					pa.setSendFlag("已下发");
				} else if ("false".equals(pa.getSendFlag())) {
					pa.setSendFlag("未下发");
				}
				String chargersname = "";
				String chargersIds = pa.getChargerIds();
				String ids[] = chargersIds.split(",");
				if (list != null && list.size() > 0) {
					for (Charger ch : listCharger) {
						for (String idd : ids) {
							if (String.valueOf(ch.getId()).equals(idd)) {
								chargersname += ch.getName() + ",";
								break;
							}
						}
					}
					if (StringUtils.isNotEmpty(chargersname)) {
						chargersname = chargersname.substring(0, chargersname.length() - 1);
					}
				}
				pa.setChargersName(chargersname);
				List<ParamSettingResult> li = pa.getSettingResult();
				if (li != null) {
					for (ParamSettingResult ps : li) {
						if (ps.isResult()) {
							ps.setResultName("下发成功");
						} else {
							ps.setResultName("下发失败");
						}
					}
				}
			}
		}
		model.addAttribute("paramSetting", paramSetting);
		model.addAttribute("page", page);
		return TEMPLATE_INDEX_FILE;
	}

	@RequestMapping("/findListAllStation")
	@ResponseBody
	public List<ChargerBean>  findListAllStation(@ModelAttribute Charger charger) {
		/*String []fields = {"city","district","province","delStatus","code","name"};*/
		String []fields = {"code","name","delStatus","stationcheckboxs"};
		List<Station> li = new ArrayList<Station>();
		if(StringUtils.isNotEmpty(charger.getStationcheckboxs())){
			String arr[] = charger.getStationcheckboxs().split(",");
			for(String s:arr){
				li.add(stationService.findById(Long.valueOf(s)));
			}
		}
		charger.setLstation(li);
		List<Charger> list = chargerService.findSearch(fields, charger);
		List<ChargerBean> newlist = new ArrayList<ChargerBean>();
		if(list.size()>0&&list!=null){
			for(Charger st :list){
				ChargerBean chargerbean = new ChargerBean();
				chargerbean.setId(st.getId());
				chargerbean.setChargerType(st.getType());
				chargerbean.setCode(st.getCode());
				chargerbean.setName(st.getName());
				chargerbean.setStationName(st.getStation().getName());
				chargerbean.setOperatorName(st.getOperator().getName());
				newlist.add(chargerbean);
			}
		}
		return newlist;
	}
	
	@RequestMapping("/search")
	public String search(HttpServletRequest request,Model model, @ModelAttribute ParamSetting paramSetting,
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
		HttpSession session = request.getSession();
        if("1".equals(paramSetting.getFlag())){
        	session.setAttribute("sessionParamSetting", paramSetting);
        }else{
        	ParamSetting ch = (ParamSetting)session.getAttribute("sessionParamSetting");
       	 if(ch!=null){
       		paramSetting = ch;
       	 }
        }
		String field[] = { "paramType", "sendFlag", "delStatus" };
		String key = "";
		for (String fieldName : field) {
			Object o = ReflectField.getFieldValueByName(fieldName, paramSetting);
			if (o != null && !"".equals(o)) {
				key += fieldName + "=" + o.toString() + "&";
			}
		}
		if (!StringUtils.isEmpty(key)) {
			key = key.substring(0, key.length() - 1);
		}
		PageWrapper<ParamSetting> page = new PageWrapper<>(
				paramSettingService.findAllSearch(field, paramSetting, pageable), "/admin/paramSettings/search?" + key);
		List<ParamSetting> list = page.getContent();
		if (list != null && list.size() > 0) {
			List<Charger> listCharger = chargerService.findAll();
			for (ParamSetting pa : list) {
				if ("true".equals(pa.getSendFlag())) {
					pa.setSendFlag("已下发");
				} else if ("false".equals(pa.getSendFlag())) {
					pa.setSendFlag("未下发");
				}
				String chargersname = "";
				String chargersIds = pa.getChargerIds();
				String ids[] = chargersIds.split(",");
				if (list != null && list.size() > 0) {
					for (Charger ch : listCharger) {
						for (String idd : ids) {
							if (String.valueOf(ch.getId()).equals(idd)) {
								chargersname += ch.getName() + ",";
								break;
							}
						}
					}
					if (StringUtils.isNotEmpty(chargersname)) {
						chargersname = chargersname.substring(0, chargersname.length() - 1);
					}
				}
				pa.setChargersName(chargersname);
				List<ParamSettingResult> li = pa.getSettingResult();
				if (li != null) {
					for (ParamSettingResult ps : li) {
						if (ps.isResult()) {
							ps.setResultName("下发成功");
						} else {
							ps.setResultName("下发失败");
						}
					}
				}
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("paramSetting", paramSetting);
		return TEMPLATE_INDEX_FILE;
	}

	@RequestMapping("/add")
	public String add(Model model) {
		List<Charger> list = chargerService.findAll();
		List<Charger> y = findChargerListAllY("");
		List<Charger> n = findChargerListAll("");
		String hiddenchargerNocheck = "";
		if (list != null && list.size() > 0) {
			for (Charger cc : list) {
				hiddenchargerNocheck += cc.getId() + ",";
			}
		}
		ParamSetting pp = new ParamSetting();
		pp.setHiddenchargerNocheck(hiddenchargerNocheck);
		pp.setChargers(n);
		pp.setChargersY(y);
		model.addAttribute("paramSetting", pp);
		return TEMPLATE_ADD_FILE;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(HttpSession session, Model model, @ModelAttribute ParamSetting paramSetting,
			BindingResult result, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String name=paramSetting.getName();
		if(name!=null&&name.endsWith(",")){
			name = name.substring(0, name.indexOf(","));
		}
		paramSetting.setName(name);
		ManagerLog log = Utils.setLog(request, "PARAMSETTING", "ADD", "添加"+paramSetting.getName());
		ManagerLog log1 = Utils.setLog(request, "PARAMSETTING", "ISSUED", "下发"+paramSetting.getName());
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("failed", "添加设备时时发生错误，请检查后重新尝试。");
			log.setStatus(false);
			managerLogService.save(log);
			return TEMPLATE_ADD_FILE;
		}
		if (!"".equals(paramSetting.getHiddenchargerCheck())) {
			paramSetting.setChargerIds(paramSetting.getHiddenchargerCheck());
		}
		if ("add".equals(paramSetting.getActionType())) {
			// paramSetting.setEffectiveTime(new Date());
			paramSetting.setSendFlag("false");
			paramSettingService.save(paramSetting);
			log.setStatus(true);
			managerLogService.save(log);
		} else if ("send".equals(paramSetting.getActionType())) {
			// 下发
			// to do
			try {
				String chargerIds = paramSetting.getChargerIds();
				Set<String> set = new HashSet<String>();
				if (!"".equals(chargerIds)) {
					String ids[] = chargerIds.split(",");
					for (String id : ids) {
						set.add(id);
					}
				}
				paramSettingServices.setCharegerParam(paramSetting.getEffectiveTime(), paramSetting.getParamTemplate(),
						set);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Manager manager = (Manager) session.getAttribute("user");
			// 基本数据保存
			paramSetting.setSendFlag("true");
			paramSetting.setSendTime(new Date());
			paramSetting.setSender(manager.getRealname());
			ParamSetting pa = paramSettingService.save(paramSetting);
			saveParamSettingResult(pa);
			log1.setStatus(true);
			managerLogService.save(log1);
		}
		redirectAttributes.addFlashAttribute("success", MessageFormat.format("设备 {0} 已经成功添加。", paramSetting.getName()));
		return "redirect:/admin/paramSettings/search";
	}

	public void saveParamSettingResult(ParamSetting pa) {
		// 结果保存
		List<Charger> cl = findChargerListAllY(pa.getChargerIds());
		if (CollectionUtils.isNotEmpty(cl)) {
			for (Charger charger : cl) {
				ParamSettingResult paramSettingResult = new ParamSettingResult();
				paramSettingResult.setParamSetting(pa);
				paramSettingResult.setCreatedAt(new Date());
				paramSettingResult.setCharger(charger);
				paramSettingResult.setResult(false);
				paramSettingResult.setStationName(charger.getStation().getName());
				paramSettingResultService.save(paramSettingResult);
			}
		}
	}

	@RequestMapping("/edit/{paramSettingId}")
	public String edit(@PathVariable("paramSettingId") Long paramSettingId, Model model) {
		ParamSetting p = paramSettingService.findById(paramSettingId);
		ParamTemplate paramTemplate    =p.getParamTemplate();
		List<Charger> a = findChargerListAll(p.getChargerIds());
		p.setChargers(a);
		String hiddenchargerNocheck = "";
		String hiddenchargerCheck = "";
		String chargersName = "";
		if (a != null) {
			for (Charger ch : a) {
				hiddenchargerNocheck += String.valueOf(ch.getId()) + ",";
			}
		}
		p.setHiddenchargerNocheck(hiddenchargerNocheck);
		List<Charger> n = findChargerListAll("");
		List<Charger> ay = findChargerListAllY(p.getChargerIds());
		if(n!=null){
			for(Charger charger:n){
				for (Charger ch : ay) {
					if(charger.getId()==ch.getId()){
						charger.setChecked("true");
						break;
					}
				}
			}
		}
		p.setChargers(n);
		if (ay != null) {
			for (Charger ch : ay) {
				hiddenchargerCheck += String.valueOf(ch.getId()) + ",";
				chargersName+=ch.getName()+",";
			}
		}
		if(StringUtils.isNotEmpty(hiddenchargerCheck)){
			hiddenchargerCheck = hiddenchargerCheck.substring(0, hiddenchargerCheck.length()-1);
		}
		if(StringUtils.isNotEmpty(chargersName)){
			chargersName = chargersName.substring(0, chargersName.length()-1);
		}
		p.setChargersName(chargersName);
		p.setHiddenchargerCheck(hiddenchargerCheck);
		model.addAttribute("paramSetting", p);
		model.addAttribute("paramTemplate", paramTemplate);
		return TEMPLATE_EDIT_FILE;
	}

	@RequestMapping("/send/{paramSettingId}/{id}/{createdAt}/{paramTemplateId}/{chargerIds}")
	public String send(HttpSession session, @PathVariable("paramSettingId") long paramSettingId,
			@PathVariable("id") long id, @PathVariable("createdAt") String createdAt,
			@PathVariable("paramTemplateId") long paramTemplateId, @PathVariable("chargerIds") String chargerIds,
			Model model,HttpServletRequest request) {
		ParamSettingResult ps = paramSettingResultService.findById(id);
		ParamSetting p = paramSettingService.findById(paramSettingId);
		ManagerLog log = Utils.setLog(request, "PARAMSETTING", "ISSUED", "下发"+p.getName());
		if (null != ps) {
			// 下发
			try {
				Set<String> set = new HashSet<String>();
				if (!"".equals(chargerIds)) {
					String ids[] = chargerIds.split(",");
					for (String chId : ids) {
						/*Charger charger = EntityUtil.getCharger(Long.parseLong(chId));
						if (charger != null) {
							set.add(charger.getCode());
						}*/
					    set.add(chId);
					}
				}
				ParamTemplate paramTemplate = paramTemplateService.findById(paramTemplateId);
				if (null != paramTemplate) {
					// Date time = VeDate.strToDateLong(effectiveTime);
					paramSettingServices.setCharegerParam(p.getEffectiveTime(), paramTemplate, set);
					p.setSendTime(new Date());
					paramSettingService.save(p);
					log.setStatus(true);
					managerLogService.save(log);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 下发时间
		ps.setCreatedAt(new Date());
		ps.setResult(false);
		paramSettingResultService.save(ps);
		// 保存基本数据
		Manager user = (Manager) session.getAttribute("user");
		p.setSender(user.getRealname());
		// p.setSendTime(new Date());
		paramSettingService.save(p);
		return "redirect:/admin/paramSettings/search";
	}

	@RequestMapping("/check/{paramSettingId}")
	public String check(@PathVariable("paramSettingId") Long paramSettingId, Model model) {
		ParamSetting p = paramSettingService.findById(paramSettingId);
		List<Charger> a = findChargerListAll(p.getChargerIds());
		p.setChargers(a);
		String hiddenchargerNocheck = "";
		String hiddenchargerCheck = "";
		String chargersName="";
		if (a != null) {
			for (Charger ch : a) {
				hiddenchargerNocheck += String.valueOf(ch.getId()) + ",";
			}
		}
		p.setHiddenchargerNocheck(hiddenchargerNocheck);
		List<Charger> ay = findChargerListAllY(p.getChargerIds());
		p.setChargersY(ay);
		if (ay != null) {
			for (Charger ch : ay) {
				hiddenchargerCheck += String.valueOf(ch.getId()) + ",";
				chargersName+=ch.getName()+",";
			}
		}
		if(StringUtils.isNotEmpty(chargersName)){
			chargersName = chargersName.substring(0, chargersName.length()-1);
		}
		p.setChargersName(chargersName);
		p.setHiddenchargerCheck(hiddenchargerCheck);
		model.addAttribute("paramSetting", p);
		return TEMPLATE_CHECK_FILE;
	}

	@RequestMapping("/del/{paramSettingId}")
	public String del(@PathVariable("paramSettingId") Long paramSettingId, Model model,HttpServletRequest request) {
		ParamSetting entity = paramSettingService.findById(paramSettingId);
		if (null != entity) {
			entity.setDelStatus("del");
			paramSettingService.delParamSetting(entity);
			ManagerLog log = Utils.setLog(request, "PARAMSETTING", "DELETE", "删除"+entity.getName());
			log.setStatus(true);
			managerLogService.save(log);
		}
		return "redirect:/admin/paramSettings/search";
	}

	@RequestMapping(value = "/{paramSettingId}", method = RequestMethod.POST)
	public String update(HttpSession session, Model model, @PathVariable("paramSettingId") Long paramSettingId,
			@Valid ParamSetting form, BindingResult result, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String name=form.getName();
		if(name!=null&&name.endsWith(",")){
			name = name.substring(0, name.indexOf(","));
		}
		form.setName(name);
		ParamSetting paramSetting = paramSettingService.findById(paramSettingId);
		ManagerLog log = Utils.setLog(request, "PARAMSETTING", "EDIT", "修改"+paramSetting.getName());
		ManagerLog log1 = Utils.setLog(request, "PARAMSETTING", "ISSUED", "下发"+paramSetting.getName());
		tranBean(paramSetting, form);
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("failed", "更新电桩参数设置时发生错误，请检查后重新尝试。");
			log.setStatus(false);
			managerLogService.save(log);
			return TEMPLATE_EDIT_FILE;
		}
		if ("add".equals(paramSetting.getActionType())) {
			// paramSetting.setEffectiveTime(new Date());
			paramSetting.setSendFlag("false");
			paramSettingService.save(paramSetting);
			log.setStatus(true);
			managerLogService.save(log);
		} else if ("send".equals(paramSetting.getActionType())) {
			// 下发
			// to do
			try {
				String chargerIds = paramSetting.getChargerIds();
				Set<String> set = new HashSet<String>();
				if (!"".equals(chargerIds)) {
					String ids[] = chargerIds.split(",");
					for (String id : ids) {
						set.add(id);
					}
				}
				paramSettingServices.setCharegerParam(paramSetting.getEffectiveTime(), paramSetting.getParamTemplate(),
						set);
				paramSetting.setSendTime(new Date());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Manager user = (Manager) session.getAttribute("user");
			// 基本数据保存
			paramSetting.setSendFlag("true");
			// paramSetting.setEffectiveTime(new Date());
			// paramSetting.setSendTime(new Date());
			paramSetting.setSender(user.getRealname());
			// paramSetting.setSendTime(new Date());
			ParamSetting pa = paramSettingService.save(paramSetting);
			// saveParamSettingResult
			saveParamSettingResult(pa);
			log1.setStatus(true);
			managerLogService.save(log1);
		}
		redirectAttributes.addFlashAttribute("success",
				MessageFormat.format("电桩参数设置{0} 更新成功。", paramSetting.getName()));
		return "redirect:/admin/paramSettings/search";
	}

	@RequestMapping("/sendParam/{paramSettingId}")
	public String sendParam(HttpSession session, @PathVariable("paramSettingId") Long paramSettingId, Model model,HttpServletRequest request) {
		ParamSetting paramSetting = paramSettingService.findById(paramSettingId);
		ManagerLog log = Utils.setLog(request, "PARAMSETTING", "ISSUED", "下发"+paramSetting.getName());
		if (paramSetting != null) {
			// 下发
			// to do
			try {
				String chargerIds = paramSetting.getChargerIds();
				Set<String> set = new HashSet<String>();
				if (!"".equals(chargerIds)) {
					String ids[] = chargerIds.split(",");
					for (String id : ids) {
						set.add(id);
					}
				}
				paramSettingServices.setCharegerParam(paramSetting.getEffectiveTime(), paramSetting.getParamTemplate(),
						set);
				paramSetting.setSendTime(new Date());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Manager user = (Manager) session.getAttribute("user");
			// 基本数据保存
			paramSetting.setSendFlag("true");
			// paramSetting.setEffectiveTime(new Date());
			// paramSetting.setSendTime(new Date());
			paramSetting.setSender(user.getRealname());
			ParamSetting pa = paramSettingService.save(paramSetting);
			// saveParamSettingResult
			saveParamSettingResult(pa);
			log.setStatus(true);
			managerLogService.save(log);
		}
		return "redirect:/admin/paramSettings/search";
	}

	public void tranBean(ParamSetting paramSetting, ParamSetting form) {
		paramSetting.setName(form.getName());
		paramSetting.setChargerIds(form.getChargerIds());
		paramSetting.setParamType(form.getParamType());
		paramSetting.setParamTemplate(form.getParamTemplate());
		paramSetting.setRemark(form.getRemark());
		paramSetting.setChargerIds(form.getHiddenchargerCheck());
		paramSetting.setEffectiveTime(form.getEffectiveTime());
		paramSetting.setActionType(form.getActionType());

	}
}
