package com.iycharge.server.admin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.api.http.HttpRequest;
import com.iycharge.server.api.util.Result;
import com.iycharge.server.domain.common.utils.OpenCSV;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;

import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.event.EventCodeQueryParam;
import com.iycharge.server.domain.entity.event.EventQueryParam;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationBean;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelUtil;
import com.iycharge.server.domain.service.AccountService;

import com.iycharge.server.domain.service.StationService;

import com.iycharge.server.report.entity.AcDcDataBean;
import com.iycharge.server.report.entity.ChargingData;
import com.iycharge.server.report.entity.DataBean;
import com.iycharge.server.report.entity.EventData;
import com.iycharge.server.report.entity.RecordData;
import com.iycharge.server.report.entity.TranDataBean;
import com.iycharge.server.report.entity.UserBillData;
import com.iycharge.server.report.entity.UserData;
import com.iycharge.server.report.entity.UserDataBean;
import com.iycharge.server.report.schedule.service.ChargingDataService;
import com.iycharge.server.report.schedule.service.EventDataService;
import com.iycharge.server.report.schedule.service.RecordDataService;
import com.iycharge.server.report.schedule.service.UserBillDataService;
import com.iycharge.server.report.schedule.service.UserDataService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/reports/")
public class ReportController {
	@Autowired
	private UserDataService userCountDataService;
	@Autowired
	private ChargingDataService chargingDataService;
	@Autowired
	private EventDataService eventDataService;
	@Autowired
	private RecordDataService recordDataService;

	@Autowired
	private UserBillDataService userBillDataService;
	@Autowired
	private AccountService accountService;
	
	private static Map<String,String> staticQueryMap = null;
	
	@ResponseBody
	@RequestMapping("/accountDataCount/account/check")
	public List<Map<String, String>> checkAccount(String realName,String phone,String type){
		Map<String,String> map = new HashMap<>();
		map.put("realName", realName);
		map.put("phone", phone);
		map.put("inputAccountType", type);
		Account account = new Account();
		List<Account>  accounts =accountService.findByCondition(map, account);
		List<Map<String, String>> list =new ArrayList<>();
		for (Account account2 : accounts) {
			Map<String, String> map2 =new HashMap<>();
			map2.put("realName", account2.getRealName());
			map2.put("phone", account2.getPhone());
			map2.put("nickname", account2.getNickname());
			map2.put("email", account2.getEmail());
			list.add(map2);
		}
		
		return list;
	}
	
	@RequestMapping(value="/accountDataCount/search", method=RequestMethod.POST)
    public String query(@ModelAttribute UserBillData userBillData,HttpServletRequest request, Model model, @PageableDefault(sort = "month", direction = Sort.Direction.ASC, size = 15) Pageable pageable) {
		Map<String, String> queryParam = new HashMap<>();
		String datetype =request.getParameter("datetype");
		String count =request.getParameter("count");
		queryParam.put("accounts", count);
		queryParam.put("datetype", datetype);
		if ("year".equals(datetype)) {
			String yearstartdate =request.getParameter("yearstartdate");
			String yearenddate =request.getParameter("yearenddate");
			queryParam.put("start", yearstartdate);
			queryParam.put("end", yearenddate);
			
		}else if ("month".equals(datetype)) {
			String monthstartdate =request.getParameter("monthstartdate");
			String monthenddate =request.getParameter("monthenddate");
			queryParam.put("start", monthstartdate);
			queryParam.put("end", monthenddate);
			
			
		}else if ("day".equals(datetype)) {
			String daystartdate =request.getParameter("daystartdate");
			String dayenddate =request.getParameter("dayenddate");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}else if("week".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			String daystartdate = VeDate.dateToStr(calendar.getTime());
			String dayenddate = VeDate.dateToStr(date);
			queryParam.put("datetype", "day");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}else if("halfyear".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.MONTH, -6);
			String daystartdate = VeDate.dateToStr(calendar.getTime());
			String dayenddate = VeDate.dateToStr(date);
			queryParam.put("datetype", "day");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}else if("fiveyear".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.YEAR, -5);
			String daystartdate = VeDate.dateToStr(calendar.getTime());
			String dayenddate = VeDate.dateToStr(date);
			queryParam.put("datetype", "day");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}		
			PageWrapper<UserBillData> page = new PageWrapper<>(userBillDataService.find(queryParam, pageable), "/admin/reports/accountDataCount/search");
	        model.addAttribute("page", page);
	        model.addAttribute("queryParam", queryParam);
	        model.addAttribute("userBillData",userBillData);
	        staticQueryMap = queryParam;
	        return "admin/report/account";
	}
	
	@ModelAttribute("accounts")
	public List<Account> findAccountListAll() {
		List<Account> list = accountService.findByAccountType("COMPANY");		
		return list;
	}
	
	
	@RequestMapping("/accountDataCount")
	public String  accountDataCount(Model model,@PageableDefault(sort = "month", direction = Sort.Direction.ASC, size=15) Pageable pageable,String flag){
		if(flag!=null&&staticQueryMap!=null){
			PageWrapper<UserBillData> page = new PageWrapper<>(userBillDataService.find(staticQueryMap, pageable), "/admin/reports/accountDataCount/search");
			String datetype = staticQueryMap.get("datetype");
			UserBillData userBillData = new UserBillData();
			if ("year".equals(datetype)) {
				userBillData.setYearstartdate((staticQueryMap.get("start")));
				userBillData.setYearstartdate((staticQueryMap.get("end")));
			}else if ("month".equals(datetype)) {
				userBillData.setMonthstartdate((staticQueryMap.get("start")));
				userBillData.setMonthenddate((staticQueryMap.get("end")));
			}else if ("day".equals(datetype)) {
				userBillData.setDaystartdate(staticQueryMap.get("start"));
				userBillData.setDayenddate(staticQueryMap.get("end"));
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				String daystartdate = VeDate.dateToStr(calendar.getTime());
				String dayenddate = VeDate.dateToStr(date);
				userBillData.setDaystartdate(daystartdate);
				userBillData.setDayenddate(dayenddate);
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				String daystartdate = VeDate.dateToStr(calendar.getTime());
				String dayenddate = VeDate.dateToStr(date);
				userBillData.setDaystartdate(daystartdate);
				userBillData.setDayenddate(dayenddate);
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				String daystartdate = VeDate.dateToStr(calendar.getTime());
				String dayenddate = VeDate.dateToStr(date);
				userBillData.setDaystartdate(daystartdate);
				userBillData.setDayenddate(dayenddate);
			}
			model.addAttribute("page", page);
	        model.addAttribute("queryParam", staticQueryMap);
	       if("week".equals(datetype)){
	    	   userBillData.setDatetype("day");
			}else if("halfyear".equals(datetype)){
				userBillData.setDatetype("day");
			}else if("fiveyear".equals(datetype)){
				userBillData.setDatetype("day");
			}else{
				userBillData.setDatetype(staticQueryMap.get("datetype"));
			}
	        model.addAttribute("userBillData",userBillData);
	        return "admin/report/account";
		}else{
			PageWrapper<UserBillData> page = new PageWrapper<>(userBillDataService.findAll(pageable), "/admin/reports/accountDataCount");
			 Map<String, String> queryParam = new HashMap<>();
			 queryParam.put("accounts", "");
			 queryParam.put("datetype", "day");
			 Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			String daystartdate = VeDate.dateToStr(calendar.getTime());
			String dayenddate = VeDate.dateToStr(date);
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
			 model.addAttribute("queryParam", queryParam);
			 model.addAttribute("page", page); 
			 staticQueryMap = null;
		}
		return "admin/report/account";
		
	}
	@RequestMapping(value="/accountDataCount/check/{id}")
	public String check(Model model,  @PathVariable("id") Long id) {
		UserBillData userBillData =userBillDataService.findById(id);
		Account account  =userBillData.getAccount();
		Date month1 =userBillData.getMonth();
		Calendar cal=Calendar.getInstance();
		cal.setTime(month1);
		cal.add(Calendar.MONTH, -1);
		Date month =cal.getTime();
		
		UserBillData userBillData2 =userBillDataService.findByAccountAndMonth(account, month);
		if (userBillData2==null) {
			model.addAttribute("totalElectricity","0" );
		}else {
			model.addAttribute("totalElectricity",userBillData2.getTotalElectricity());
		}
		model.addAttribute("userBillData",userBillData );
		return "admin/report/accountcheck";
	}

	@Autowired
	private StationService stationService;

	@RequestMapping("")
	public String index() {
		return "admin/report/index";
	}

	@RequestMapping("/user")
	public String indexuser(Model model) {
		return "admin/report/user";
	}
	
	@RequestMapping("/findListAllStation")
	@ResponseBody
	public List<StationBean>  findListAllStation(@ModelAttribute Station station) {
		/*String []fields = {"city","district","province","delStatus","code","name"};*/
		String []fields = {"city","district","province","delStatus"};
		/*if("1".equals(station.getStationType())){
			station.setName(station.getCodeAndName());
		}else if("2".equals(station.getStationType())){
			station.setCode(station.getCodeAndName());
		}*/
		station.setName(station.getCodeAndName());
		station.setCode(station.getCodeAndName());
		List<Station> list = stationService.findSearch(fields, station);
		List<StationBean> newlist = new ArrayList<StationBean>();
		if(list.size()>0&&list!=null){
			for(Station st :list){
				StationBean stationbean = new StationBean();
				stationbean.setId(st.getId());
				stationbean.setCity(st.getCity());
				stationbean.setCode(st.getCode());
				stationbean.setDistrict(st.getDistrict());
				stationbean.setName(st.getName());
				stationbean.setOperatorName(st.getOperator().getName());
				stationbean.setProvince(st.getProvince());
				newlist.add(stationbean);
			}
		}
		return newlist;
	}

	public Date getStartDate(String startdate, String datetype) {
		if(StringUtils.isNotEmpty(startdate)){
			if ("year".equals(datetype)) {
				startdate = startdate + "-01-01";
			} else if ("month".equals(datetype)) {
				startdate = startdate + "-01";
			} else if ("day".equals(datetype)) {

			}
			if (startdate != null) {
				return VeDate.strToDate(startdate);
			}
		}
		return null;
	}

	public Date getEndDate(String enddate, String datetype) {
		if(StringUtils.isNotEmpty(enddate)){
			if ("year".equals(datetype)) {
				enddate = enddate + "-12-31";
			} else if ("month".equals(datetype)) {
				enddate = enddate + "-01";
				Date end = VeDate.strToDate(enddate);
				Calendar calendar = Calendar.getInstance();
				// 设置时间,当前时间不用设置
				calendar.setTime(end);
				// 设置日期为本月最大日期
				calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				enddate = format.format(calendar.getTime());
			} else if ("day".equals(datetype)) {

			}
			if (enddate != null) {
				return VeDate.strToDate(enddate);
			}
		}
		return null;
	}

	public List<String> getTimeLine(String datetype, String startDate, String enddate) {
		List<String> ld = new ArrayList<String>();
		if ("year".equals(datetype)) {
			try {
				Date d1 = new SimpleDateFormat("yyyy").parse(startDate);// 定义起始日期
				Date d2 = new SimpleDateFormat("yyyy").parse(enddate);// 定义结束日期
				Calendar dd = Calendar.getInstance();// 定义日期实例
				dd.setTime(d1);// 设置日期起始时间
				while (dd.getTime().before(d2) || dd.getTime().equals(d2)) {// 判断是否到结束日期
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					String str = sdf.format(dd.getTime());
					//System.out.println(str);// 输出日期结果
					dd.add(Calendar.YEAR, 1);// 进行当前日期月份加1
					ld.add(str);
				}
			} catch (Exception e) {
			}
		}
		if ("month".equals(datetype)) {
			try {
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(startDate);// 定义起始日期
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(enddate);// 定义结束日期
				Calendar dd = Calendar.getInstance();// 定义日期实例
				dd.setTime(d1);// 设置日期起始时间
				while (dd.getTime().before(d2) || dd.getTime().equals(d2)) {// 判断是否到结束日期
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					String str = sdf.format(dd.getTime());
					//System.out.println(str);// 输出日期结果
					dd.add(Calendar.MONTH, 1);// 进行当前日期月份加1
					ld.add(str);
				}
			} catch (Exception e) {
			}
		}
		if ("day".equals(datetype)) {
			try {
				Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);// 定义起始日期
				Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);// 定义结束日期
				Calendar dd = Calendar.getInstance();// 定义日期实例
				dd.setTime(d1);// 设置日期起始时间
				while (dd.getTime().before(d2) || dd.getTime().equals(d2)) {// 判断是否到结束日期
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String str = sdf.format(dd.getTime());
					//System.out.println(str);// 输出日期结果
					dd.add(Calendar.DATE, 1);// 进行当前日期月份加1
					ld.add(str);
				}
			} catch (Exception e) {
			}
		}
		return ld;
	}

	@RequestMapping("/exportchargingDataCount")
	public void exportchargingDataCount(HttpServletResponse response,@ModelAttribute ChargingData chargingData, Model model) throws IOException {
		String startdate = null;
		String enddate = null;
		String datetype = chargingData.getDatetype();
		String type = chargingData.getType();
		String counttype = chargingData.getCounttype();
		if(datetype==null&&type==null&&counttype==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
			type="all";
			counttype="money";
		}else{
			if ("year".equals(datetype)) {
				// 年
				startdate = chargingData.getYearstartdate();
				enddate = chargingData.getYearenddate();
			} else if ("month".equals(datetype)) {
				// 月
				startdate = chargingData.getMonthstartdate();
				enddate = chargingData.getMonthenddate();
			} else if ("day".equals(datetype)) {
				// 天
				startdate = chargingData.getDaystartdate();
				enddate = chargingData.getDayenddate();
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}
		}
		
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		if(start!=null&&end!=null){
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			List listst  = new ArrayList();
			if(StringUtils.isNotEmpty(chargingData.getCheckboxstation())){
				listst = chargingDataService.findBeanSearch(chargingData.getCheckboxstation(),type, counttype, datetype, st, ed);
			}else{
				listst = chargingDataService.findBean(type, counttype, datetype, st, ed);
			}
			if(listst!=null){
				List<Station> liSt = stationService.findListAll();
				if(liSt!=null){
					for(Object object:listst){
						DataBean da = (DataBean)object;
						for(Station station:liSt){
							if(!"".equals(da.getTypevalue())){
								if(Long.valueOf(da.getTypevalue()).equals(station.getId())){
									da.setTypevalue(station.getName());
									break;
								} 
							}
						}
					}
				}
			}
			//List listst  = chargingDataService.findBean(type, counttype, datetype, st, ed);
			String header[]={"时间","维度","交流","直流","APP","刷卡","总数"};
			String fields[]={"time","typevalue","acvalue","dcvalue","appvalue","cardvalue","countnums"};
			byte[] bytes = OpenCSV.CSVWriter(listst, fields, header);
/*	        response.setContentType("application/x-msdownload");
	        response.setHeader("Content-Disposition", "attachment;filename=财务增长趋势.xls");*/
			response.setHeader("Content-Type","application/force-download");   
	        response.setHeader("Content-Type","application/vnd.ms-excel");   
	        response.setHeader("Content-disposition", "attachment; filename=" + new String("充电消费趋势.csv".getBytes("gb2312"),"ISO8859-1"));// 设定输出文件头  
	        response.setContentLength(bytes.length);
	        response.getOutputStream().write(bytes);
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
		}
	}
	
	@RequestMapping("/chargingDataCount")
	public String chargingDataCount(@ModelAttribute ChargingData chargingData, Model model) {
		String startdate = null;
		String enddate = null;
		String datetype = chargingData.getDatetype();
		String type = chargingData.getType();
		String counttype = chargingData.getCounttype();
		if(datetype==null&&type==null&&counttype==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
			type="all";
			counttype="money";
		}else{
			if ("year".equals(datetype)) {
				// 年
				startdate = chargingData.getYearstartdate();
				enddate = chargingData.getYearenddate();
			} else if ("month".equals(datetype)) {
				// 月
				startdate = chargingData.getMonthstartdate();
				enddate = chargingData.getMonthenddate();
			} else if ("day".equals(datetype)) {
				// 天
				startdate = chargingData.getDaystartdate();
				enddate = chargingData.getDayenddate();
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}
		}
		
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		List<String> listtime = getTimeLine(datetype, startdate, enddate);
		List<String> newlisttime =new ArrayList<String>();
		Map map = new HashMap();
		String v[] = {""};
		map.put("legend", v);
		map.put("xAxis", v);
		map.put("series", v);
		map.put("list", null);
		List<DataBean> list = null;
		List<String> listst = null;
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			List<Station> liSt = stationService.findListAll();
			if (listtime != null && listtime.size() > 0) {
				if(StringUtils.isNotEmpty(chargingData.getCheckboxstation())){
					list = chargingDataService.findBeanSearch(chargingData.getCheckboxstation(),type, counttype, datetype, st, ed);
				}else{
					list = chargingDataService.findBean(type, counttype, datetype, st, ed);
				}
				if("station_id".equals(type)){
					if(list!=null){
						if(liSt!=null){
							for(DataBean da:list){
								for(Station station:liSt){
									if(Long.valueOf(da.getTypevalue()).equals(station.getId())){
										da.setTypevalue(station.getName());
										break;
									} 
								}
							}
						}
					}
				}
				map.put("list", list);
				if ("all".equals(type)) {
					List<String> li = new ArrayList<String>();
					for (String value : listtime) {
						if (list != null && list.size() > 0) {
							Iterator<DataBean> it = list.iterator();
							while (it.hasNext()) {
								DataBean dataBean = it.next();
								if (value.equals(dataBean.getTime())) {
									li.add(String.valueOf(dataBean.getCountnums()));
									newlisttime.add(value);
									break;
								}
							}
						}
					}
					TranDataBean chargingDataBean = new TranDataBean();
					chargingDataBean.setData(li);
					chargingDataBean.setName("");
					List<TranDataBean> series = new ArrayList<TranDataBean>();
					series.add(chargingDataBean);
					map.put("xAxis", newlisttime);
					map.put("series", series);
				} else {
					if(StringUtils.isNotEmpty(chargingData.getCheckboxstation())){
						listst = chargingDataService.findTypeSearch(chargingData.getCheckboxstation(),type,"r_charging_data", datetype, st, ed);
					}else{
						listst = chargingDataService.findType(type,"r_charging_data", datetype, st, ed);
					}
					List<String> ll = new ArrayList<String>();
					if (listst != null && listst.size() > 0) {
						if("station_id".equals(type)){
							if(liSt!=null){
								for(String da:listst){
									for(Station station:liSt){
										if(Long.valueOf(da).equals(station.getId())){
											ll.add(station.getName());
											break;
										} 
									}
								}
								listst = ll;
							}
						}
						List<TranDataBean> series = new ArrayList<TranDataBean>();
						Iterator<String> it = listst.iterator();
						while (it.hasNext()) {
							List<String> li = new ArrayList<String>();
							String values = it.next();
							TranDataBean chargingDataBean = new TranDataBean();
							chargingDataBean.setName(values);
							for (String value : listtime) {
								if (list != null && list.size() > 0) {
									Iterator<DataBean> its = list.iterator();
									//boolean flag = false;
									while (its.hasNext()) {
										DataBean dataBean = its.next();
										if (value.equals(dataBean.getTime())
												&&values.equals(dataBean.getTypevalue())) {
											li.add(String.valueOf(dataBean.getCountnums()));
											newlisttime.add(value);
											//flag = true;											
											break;
										}
									}
									/*if(!flag){
										li.add("0");
									}*/
								}
							}
							chargingDataBean.setData(li);
							series.add(chargingDataBean);
						}
						map.put("xAxis", newlisttime);
						map.put("series", series);
						map.put("legend", listst);
					}
				}
			}
		}
		map.put("acvalue", 0);
		map.put("dcvalue", 0);
		map.put("appvalue", 0);
		map.put("cardvalue", 0);
		List<AcDcDataBean> listacdc = null;
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			listacdc = chargingDataService.findAcDc(type, counttype, datetype, st, ed);
			if(listacdc!=null&&listacdc.size()>0){
				AcDcDataBean acDcDataBean = listacdc.get(0);
				if(acDcDataBean.getAcvalue()==null){
					map.put("acvalue", 0);
				}else{
					map.put("acvalue", acDcDataBean.getAcvalue());
				}
				if(acDcDataBean.getDcvalue()==null){
					map.put("dcvalue", 0);
				}else{
					map.put("dcvalue", acDcDataBean.getDcvalue());
				}
				if(acDcDataBean.getAppvalue()==null){
					map.put("appvalue", 0);
				}else{
					map.put("appvalue", acDcDataBean.getAppvalue());
				}
				if(acDcDataBean.getCardvalue()==null){
					map.put("cardvalue", 0);
				}else{
					map.put("cardvalue", acDcDataBean.getCardvalue());
				}
			}
		}
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			model.addAttribute("tip", "");
		}else{
			model.addAttribute("tip", "时间必须!");
		}
		List<Station> liststation = stationService.findListAll();
		String charging = chargingData.getCheckboxstation();
		if(StringUtils.isNotEmpty(charging)){
			String ch[] = charging.split(",");
			if(liststation!=null&&liststation.size()>0){
				for(Station st:liststation){
					for(String value:ch){
						if(String.valueOf(st.getId()).equals(value)){
							st.setChecked("true");
							break;
						}
					}
				}
			}
		}
		map.put("maps", liststation);
		model.addAttribute("chargingData", chargingData);
		model.addAttribute("map", map);
		return "admin/report/charging";
	}
	
	@RequestMapping("/exportuserCount")
	public void exportuserCount(HttpServletResponse response,@ModelAttribute UserData userdata, Model model) throws IOException {
		String startdate = null;
		String enddate = null;
		String datetype = userdata.getDatetype();
		if(datetype==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
		}else{
			if ("year".equals(datetype)) {
				// 年
				startdate = userdata.getYearstartdate();
				enddate = userdata.getYearenddate();
			} else if ("month".equals(datetype)) {
				// 月
				startdate = userdata.getMonthstartdate();
				enddate = userdata.getMonthenddate();
			} else if ("day".equals(datetype)) {
				// 天
				startdate = userdata.getDaystartdate();
				enddate = userdata.getDayenddate();
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}
		}
		
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		List<UserData> list = null;
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			list = userCountDataService.findByDayBetween(start, end);
			List<String> ld = getTimeLine(datetype, startdate, enddate);
			List listpage = new ArrayList<UserDataBean>();
			List<Integer> its = new ArrayList<Integer>();
			if (ld != null && ld.size() > 0) {
				for (String value : ld) {
					UserDataBean userDataBean = new UserDataBean();
					if (list != null && list.size() > 0) {
						Iterator<UserData> it = list.iterator();
						int grnum = 0;
						int qynum = 0;
						int num = 0;
						boolean flag = false;
						while (it.hasNext()) {
							UserData ud = it.next();
							if ("year".equals(datetype)) {
								// 年
								SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
								String stryear = yearsdf.format(ud.getDay());
								if (stryear.equals(value)) {
									num += ud.getNewCNum() + ud.getNewPNum();
									grnum += ud.getNewPNum();
									qynum += ud.getNewCNum();
									flag = true;
								}
							} else if ("month".equals(datetype)) {
								// 月
								SimpleDateFormat monthsdf = new SimpleDateFormat("yyyy-MM");
								String strmonth = monthsdf.format(ud.getDay());
								if (strmonth.equals(value)) {
									num += ud.getNewCNum() + ud.getNewPNum();
									grnum += ud.getNewPNum();
									qynum += ud.getNewCNum();
									flag = true;
								}
							} else if ("day".equals(datetype)) {
								// 天
								SimpleDateFormat sdfday = new SimpleDateFormat("yyyy-MM-dd");
								String strday = sdfday.format(ud.getDay());
								if (strday.equals(value)) {
									num += ud.getNewCNum() + ud.getNewPNum();
									grnum += ud.getNewPNum();
									qynum += ud.getNewCNum();
									flag = true;
								}
							}
						}
						if(flag){
							its.add(num);
							userDataBean.setTime(value);
							userDataBean.setGrnum(grnum);
							userDataBean.setQynum(qynum);
							userDataBean.setTotal(num);
							listpage.add(userDataBean);
						}
					}
				}
				String header[]={"时间","企业会员","个人会员","总数"};
				String fields[]={"time","qynum","grnum","total"};
				byte[] bytes = OpenCSV.CSVWriter(listpage, fields, header);
	/*	        response.setContentType("application/x-msdownload");
		        response.setHeader("Content-Disposition", "attachment;filename=财务增长趋势.xls");*/
				response.setHeader("Content-Type","application/force-download");   
		        response.setHeader("Content-Type","application/vnd.ms-excel");   
		        response.setHeader("Content-disposition", "attachment; filename=" + new String("用户增长趋势.csv".getBytes("gb2312"),"ISO8859-1"));// 设定输出文件头  
		        response.setContentLength(bytes.length);
		        response.getOutputStream().write(bytes);
		        response.getOutputStream().flush();
		        response.getOutputStream().close();
			}
		}
	}
	
	@RequestMapping("/userCount")
	public String userCount(@ModelAttribute UserData userdata, Model model) {
		String startdate = null;
		String enddate = null;
		String datetype = userdata.getDatetype();
		if(datetype==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
		}else{
			if ("year".equals(datetype)) {
				// 年
				startdate = userdata.getYearstartdate();
				enddate = userdata.getYearenddate();
			} else if ("month".equals(datetype)) {
				// 月
				startdate = userdata.getMonthstartdate();
				enddate = userdata.getMonthenddate();
			} else if ("day".equals(datetype)) {
				// 天
				startdate = userdata.getDaystartdate();
				enddate = userdata.getDayenddate();
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}
		}
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		List<UserData> list = null;
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			list = userCountDataService.findByDayBetween(start, end);
		}
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		int newpnum = 0;
		int newcnum = 0;
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> mc = new HashMap<String, Object>();
		if (list != null && list.size() > 0) {
			Iterator<UserData> it = list.iterator();
			while (it.hasNext()) {
				UserData ud = it.next();
				// 个人
				newpnum += ud.getNewPNum();
				// 企业
				newcnum += ud.getNewCNum();
			}
		}
		mp.put("name", "个人");
		mp.put("value", newpnum);
		mc.put("name", "企业");
		mc.put("value", newcnum);
		maplist.add(mp);
		maplist.add(mc);

		List<String> ld = getTimeLine(datetype, startdate, enddate);
		List<String> newlisttime =new ArrayList<String>();
		List<UserDataBean> listpage = new ArrayList<UserDataBean>();
		List<Integer> its = new ArrayList<Integer>();
		if (ld != null && ld.size() > 0) {
			for (String value : ld) {
				UserDataBean userDataBean = new UserDataBean();
				if (list != null && list.size() > 0) {
					Iterator<UserData> it = list.iterator();
					int grnum = 0;
					int qynum = 0;
					int num = 0;
					boolean flag = false;
					while (it.hasNext()) {
						UserData ud = it.next();
						if ("year".equals(datetype)) {
							// 年
							SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
							String stryear = yearsdf.format(ud.getDay());
							if (stryear.equals(value)) {
								num += ud.getNewCNum() + ud.getNewPNum();
								grnum += ud.getNewPNum();
								qynum += ud.getNewCNum();
								flag = true;
							}
						} else if ("month".equals(datetype)) {
							// 月
							SimpleDateFormat monthsdf = new SimpleDateFormat("yyyy-MM");
							String strmonth = monthsdf.format(ud.getDay());
							if (strmonth.equals(value)) {
								num += ud.getNewCNum() + ud.getNewPNum();
								grnum += ud.getNewPNum();
								qynum += ud.getNewCNum();
								flag = true;
							}
						} else if ("day".equals(datetype)) {
							// 天
							SimpleDateFormat sdfday = new SimpleDateFormat("yyyy-MM-dd");
							String strday = sdfday.format(ud.getDay());
							if (strday.equals(value)) {
								num += ud.getNewCNum() + ud.getNewPNum();
								grnum += ud.getNewPNum();
								qynum += ud.getNewCNum();
								flag = true;
							}
						}
					}
					if(flag){
						newlisttime.add(value);
						its.add(num);
						userDataBean.setTime(value);
						userDataBean.setGrnum(grnum);
						userDataBean.setQynum(qynum);
						userDataBean.setTotal(num);
						listpage.add(userDataBean);
					}
				}
			}
		}
		Map ma = new HashMap();
		ma.put("ld", newlisttime);
		ma.put("its", its);
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			model.addAttribute("tip", "");
		}else{
			model.addAttribute("tip", "时间必须!");
		}
		// 列表展示
		model.addAttribute("userdata", userdata);
		model.addAttribute("listpage", listpage);
		model.addAttribute("ma", ma);
		model.addAttribute("maplist", maplist);
		return "admin/report/user";
	}

	@RequestMapping("/exporteventDataCount")
	public void exporteventDataCount(HttpServletResponse response,@ModelAttribute EventData eventData, Model model) throws IOException {
		String startdate = null;
		String enddate = null;
		String datetype = eventData.getDatetype();
		String type = eventData.getType();
		if(datetype==null&&type==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
			type="all";
		}else{
			if ("year".equals(datetype)) {
				// 年
				startdate = eventData.getYearstartdate();
				enddate = eventData.getYearenddate();
			} else if ("month".equals(datetype)) {
				// 月
				startdate = eventData.getMonthstartdate();
				enddate = eventData.getMonthenddate();
			} else if ("day".equals(datetype)) {
				// 天
				startdate = eventData.getDaystartdate();
				enddate = eventData.getDayenddate();
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}
		}
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		if(start!=null&&end!=null){
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			List listst = eventDataService.findEventNum(type, datetype, st, ed);
			if(listst!=null){
				List<Station> liSt = stationService.findListAll();
				if(liSt!=null){
					for(Object object:listst){
						DataBean da = (DataBean)object;
						for(Station station:liSt){
							if(Long.valueOf(da.getTypevalue()).equals(station.getId())){
								da.setTypevalue(station.getName());
								break;
							} 
						}
					}
				}
			}
			String header[]={"时间","维度","告警数"};
			String fields[]={"time","typevalue","countnums"};
			byte[] bytes = OpenCSV.CSVWriter(listst, fields, header);
/*	        response.setContentType("application/x-msdownload");
	        response.setHeader("Content-Disposition", "attachment;filename=财务增长趋势.xls");*/
			response.setHeader("Content-Type","application/force-download");   
	        response.setHeader("Content-Type","application/vnd.ms-excel");   
	        response.setHeader("Content-disposition", "attachment; filename=" + new String("告警分类趋势.csv".getBytes("gb2312"),"ISO8859-1"));// 设定输出文件头  
	        response.setContentLength(bytes.length);
	        response.getOutputStream().write(bytes);
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
		}
	}
	@RequestMapping("/eventDataCount")
	public String eventDataCount(@ModelAttribute EventData eventData, Model model) {
		String startdate = null;
		String enddate = null;
		String datetype = eventData.getDatetype();
		String type = eventData.getType();
		if(datetype==null&&type==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
			type="all";
		}else{
			if ("year".equals(datetype)) {
				// 年
				startdate = eventData.getYearstartdate();
				enddate = eventData.getYearenddate();
			} else if ("month".equals(datetype)) {
				// 月
				startdate = eventData.getMonthstartdate();
				enddate = eventData.getMonthenddate();
			} else if ("day".equals(datetype)) {
				// 天
				startdate = eventData.getDaystartdate();
				enddate = eventData.getDayenddate();
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}
		}
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		List<String> listtime = getTimeLine(datetype, startdate, enddate);
		List<String> newlisttime =new ArrayList<String>();
		Map map = new HashMap();
		String v[] = {""};
		map.put("legend", v);
		map.put("xAxis", v);
		map.put("series", v);
		map.put("list", null);
		List<DataBean> list = null;
		List<String> listst = null;
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			List<Station> liSt = stationService.findListAll();
			if (listtime != null && listtime.size() > 0) {
				list = eventDataService.findEventNum(type, datetype, st, ed);
				if("station_id".equals(type)){
					if(list!=null){
						if(liSt!=null){
							for(DataBean da:list){
								for(Station station:liSt){
									if(Long.valueOf(da.getTypevalue()).equals(station.getId())){
										da.setTypevalue(station.getName());
										break;
									} 
								}
							}
						}
					}
				}
				map.put("list", list);
				if ("all".equals(type)) {
					List<String> li = new ArrayList<String>();
					for (String value : listtime) {
						if (list != null && list.size() > 0) {
							Iterator<DataBean> it = list.iterator();
							while (it.hasNext()) {
								DataBean dataBean = it.next();
								if (value.equals(dataBean.getTime())) {
									li.add(String.valueOf(dataBean.getCountnums()));
									newlisttime.add(value);
									break;
								}
							}
						}
					}
					TranDataBean chargingDataBean = new TranDataBean();
					chargingDataBean.setData(li);
					chargingDataBean.setName("");
					List<TranDataBean> series = new ArrayList<TranDataBean>();
					series.add(chargingDataBean);
					map.put("xAxis", newlisttime);
					map.put("series", series);
				} else {
					listst = chargingDataService.findType(type,"r_event_data", datetype, st, ed);
					List<String> ll = new ArrayList<String>();
					if (listst != null && listst.size() > 0) {
						if("station_id".equals(type)){
							if(liSt!=null){
								for(String da:listst){
									for(Station station:liSt){
										if(Long.valueOf(da).equals(station.getId())){
											ll.add(station.getName());
											break;
										} 
									}
								}
								listst = ll;
							}
						}
						List<TranDataBean> series = new ArrayList<TranDataBean>();
						Iterator<String> it = listst.iterator();
						while (it.hasNext()) {
							List<String> li = new ArrayList<String>();
							String values = it.next();
							TranDataBean chargingDataBean = new TranDataBean();
							chargingDataBean.setName(values);
							for (String value : listtime) {
								if (list != null && list.size() > 0) {
									Iterator<DataBean> its = list.iterator();
									//boolean flag = false;
									while (its.hasNext()) {
										DataBean dataBean = its.next();
										if (value.equals(dataBean.getTime())
												&&values.equals(dataBean.getTypevalue())) {
											li.add(String.valueOf(dataBean.getCountnums()));
											newlisttime.add(value);
											break;
										}
									}
								}
							}
							chargingDataBean.setData(li);
							series.add(chargingDataBean);
						}
						map.put("xAxis", newlisttime);
						map.put("series", series);
						map.put("legend", listst);
					}
				}
			}
		}
		List<Map> maplist = new ArrayList<Map>();
		Set<String> dotime = new HashSet<String>();
		List<String> eventtypename = new ArrayList<String>();
		

		List<Map> maplistlevel = new ArrayList<Map>();
		Set<String> dotimelevel = new HashSet<String>();
		List<String> eventtypenamelevel = new ArrayList<String>();
		
		List<DictData> typedic = EntityUtil.getDictDatas(CategoryConstant.EVENT_TYPE);
		List<DictData> leveldic = EntityUtil.getDictDatas(CategoryConstant.EVENT_LEVEL);
		List<DataBean> listtype = null;
		List<DataBean> listlevel = null;
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			listtype = eventDataService.findType("event_type_num", "event_type", datetype,"r_event_data", st, ed);
			if(listtype!=null&&listtype.size()>0){
		    	if(typedic!=null&&typedic.size()>0){
		    		for(DictData dictData:typedic){
		    			String value  =dictData.getDictValue();
    					eventtypename.add(dictData.getDescr());
    					Map series = new HashMap();
    					series.put("name", dictData.getDescr());
    					series.put("type", "bar");
    					List<String> dolist = new ArrayList<String>();
		    			for(DataBean dataBean :listtype){
		    				if(value.equals(dataBean.getTypevalue())){
								dotime.add(dataBean.getTime());
								dolist.add(dataBean.getCountnums());
		    				}
		    			}
		    			series.put("data", dolist);
		    			maplist.add(series);
		    		}
		    	}
			}
			listlevel = eventDataService.findType("event_level_num", "event_level", datetype,"r_event_data", st, ed);
			if(listlevel!=null&&listlevel.size()>0){
		    	if(leveldic!=null&&leveldic.size()>0){
		    		for(DictData dictData:leveldic){
		    			String value  =dictData.getDictValue();
		    			eventtypenamelevel.add(dictData.getDescr());
    					Map series = new HashMap();
    					series.put("name", dictData.getDescr());
    					series.put("type", "bar");
    					List<String> dolist = new ArrayList<String>();
		    			for(DataBean dataBean :listlevel){
		    				if(value.equals(dataBean.getTypevalue())){
    							dotimelevel.add(dataBean.getTime());
    							dolist.add(dataBean.getCountnums());
		    				}
		    			}
		    			series.put("data", dolist);
		    			maplistlevel.add(series);
		    		}
		    	}
			}
		}
		if (StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
			model.addAttribute("tip", "");
		}else{
			model.addAttribute("tip", "时间必须!");
		}
		map.put("xAxistype", dotime);
		map.put("seriestype", maplist);
		map.put("legendtype", eventtypename);
		map.put("xAxislevel", dotimelevel);
		map.put("serieslevel", maplistlevel);
		map.put("legendlevel", eventtypenamelevel);
		model.addAttribute("eventData", eventData);
		model.addAttribute("map", map);
		return "admin/report/alarm";
	}
	@RequestMapping("/recordDataCount")
	public String recordDataCount(@ModelAttribute RecordData recordData, Model model) {
		String startdate = null;
		String enddate = null;
		String datetype = recordData.getDatetype();
		if(datetype==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
		}else{
			if ("year".equals(datetype)) {
				// 年
				startdate = recordData.getYearstartdate();
				enddate = recordData.getYearenddate();
			} else if ("month".equals(datetype)) {
				// 月
				startdate = recordData.getMonthstartdate();
				enddate = recordData.getMonthenddate();
			} else if ("day".equals(datetype)) {
				// 天
				startdate = recordData.getDaystartdate();
				enddate = recordData.getDayenddate();
			}else if("week".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -7);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("halfyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.MONTH, -6);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}else if("fiveyear".equals(datetype)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.YEAR, -5);
				startdate = VeDate.dateToStr(calendar.getTime());
				enddate = VeDate.dateToStr(date);
				datetype = "day";
			}
		}
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		List<String> time = new ArrayList<String>();
		List<String> arraytime = new ArrayList<String>();
		List<String> arraypersoncostdata = new ArrayList<String>();
		List<String> arraycompanycostdata = new ArrayList<String>();
		List<String> arraypersonrecorddata = new ArrayList<String>();
		List<String> arraycompanyrecorddata = new ArrayList<String>();
		Map maplist = new HashMap();
		String v[] = {""};
		maplist.put("xAxis", v);
		maplist.put("series", v);
		maplist.put("personcost", v);
		maplist.put("companycost", v);
		maplist.put("personrecord", v);
		maplist.put("companyrecord", v);
		maplist.put("list", null);
		List<RecordData> listst =null;
		List<String> listtime = getTimeLine(datetype, startdate, enddate);
		if(start!=null&&end!=null){
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			listst = recordDataService.findMoneyCount(datetype, st, ed);
			maplist.put("list", listst);
			if (listst != null && listst.size() > 0) {
				List<TranDataBean> series = new ArrayList<TranDataBean>();
				if(listtime!=null&&listtime.size()>0){
					TranDataBean tranDataBeancz = new TranDataBean();
					List<String> datacz = new ArrayList<String>();
					List<String> arraydatacz = new ArrayList<String>();
					tranDataBeancz.setName("充值");
					
					TranDataBean tranDataBeanxf = new TranDataBean();
					List<String> dataxf = new ArrayList<String>();
					List<String> arraydataxf = new ArrayList<String>();
					tranDataBeanxf.setName("消费");
					Iterator<RecordData> it = listst.iterator();
					
					List<String> personcostdata = new ArrayList<String>();
					List<String> companycostdata = new ArrayList<String>();
					
					List<String> personrecorddata = new ArrayList<String>();
					List<String> companyrecorddata = new ArrayList<String>();
					while(it.hasNext()){
						RecordData record = it.next();
						dataxf.add(record.getCostTotal().toString());
						datacz.add(record.getRecordTotal().toString());
						personcostdata.add(record.getPersonCost().toString());
						companycostdata.add(record.getCompanyCost().toString());
						personrecorddata.add(record.getPersonRecord().toString());
						companyrecorddata.add(record.getCompanyRecord().toString());
						time.add(record.getTime());
					}
					for(String value:listtime){
						for(int i=0;i<time.size();i++){
							if(value.equals(time.get(i))){
								arraytime.add(value);
								arraydataxf.add(dataxf.get(i));
								arraydatacz.add(datacz.get(i));
								arraypersoncostdata.add(personcostdata.get(i));
								arraycompanycostdata.add(companycostdata.get(i));
								arraypersonrecorddata.add(personrecorddata.get(i));
								arraycompanyrecorddata.add(companyrecorddata.get(i));
							}
						}
					}
					tranDataBeancz.setData(arraydatacz);
					tranDataBeanxf.setData(arraydataxf);
					series.add(tranDataBeancz);
					series.add(tranDataBeanxf);
					maplist.put("xAxis", arraytime);
					maplist.put("series", series);
				}
			}
		}
		maplist.put("personcost", arraypersoncostdata);
		maplist.put("companycost", arraycompanycostdata);
		maplist.put("personrecord", arraypersonrecorddata);
		maplist.put("companyrecord", arraycompanyrecorddata);
		model.addAttribute("data", listst);
		model.addAttribute("recordData", recordData);
		model.addAttribute("map", maplist);
		return "admin/report/finance";
	}
	
	@RequestMapping("/exportrecordDataCount")
	public void exportrecordDataCount(HttpServletResponse response,@ModelAttribute RecordData recordData, Model model) throws IOException {
		String startdate = null;
		String enddate = null;
		String datetype = recordData.getDatetype();
		if(datetype==null){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
		}
		if ("year".equals(datetype)) {
			// 年
			startdate = recordData.getYearstartdate();
			enddate = recordData.getYearenddate();
		} else if ("month".equals(datetype)) {
			// 月
			startdate = recordData.getMonthstartdate();
			enddate = recordData.getMonthenddate();
		} else if ("day".equals(datetype)) {
			// 天
			startdate = recordData.getDaystartdate();
			enddate = recordData.getDayenddate();
		}else if("week".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
		}else if("halfyear".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.MONTH, -6);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
		}else if("fiveyear".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.YEAR, -5);
			startdate = VeDate.dateToStr(calendar.getTime());
			enddate = VeDate.dateToStr(date);
			datetype = "day";
		}
		Date start = getStartDate(startdate, datetype);
		Date end = getEndDate(enddate, datetype);
		if(start!=null&&end!=null){
			String st = VeDate.dateToStr(start);
			String ed = VeDate.dateToStr(end);
			List listst = recordDataService.findMoneyCount(datetype, st, ed);
			String header[]={"时间","企业充值","个人充值","充值总数","企业消费","个人消费","消费总数"};
			String fields[]={"time","companyRecord","personRecord","recordTotal","companyCost","personCost","costTotal"};
			byte[] bytes = OpenCSV.CSVWriter(listst, fields, header);
/*	        response.setContentType("application/x-msdownload");
	        response.setHeader("Content-Disposition", "attachment;filename=财务增长趋势.xls");*/
			response.setHeader("Content-Type","application/force-download");   
	        response.setHeader("Content-Type","application/vnd.ms-excel");   
	        response.setHeader("Content-disposition", "attachment; filename=" + new String("财务增长趋势.csv".getBytes("gb2312"),"ISO8859-1"));// 设定输出文件头  
	        response.setContentLength(bytes.length);
	        response.getOutputStream().write(bytes);
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
		}
	}
	
	@RequestMapping("/exportaccountDataCount")
	public ResponseEntity<InputStreamResource> exportaccountDataCount(HttpServletResponse response,HttpServletRequest request,@ModelAttribute UserBillData userBillData, Model model) throws IOException {
		Map<String, String> queryParam = new HashMap<>();
		String datetype = userBillData.getDatetype();
		String count =request.getParameter("count");
		queryParam.put("accounts", count);
		queryParam.put("datetype", datetype);
		String startdate = null;
		String enddate = null;
		if ("year".equals(datetype)) {
			String yearstartdate =request.getParameter("yearstartdate");
			String yearenddate =request.getParameter("yearenddate");
			queryParam.put("start", yearstartdate);
			queryParam.put("end", yearenddate);
			
		}else if ("month".equals(datetype)) {
			String monthstartdate =request.getParameter("monthstartdate");
			String monthenddate =request.getParameter("monthenddate");
			queryParam.put("start", monthstartdate);
			queryParam.put("end", monthenddate);
			
			
		}else if ("day".equals(datetype)) {
			String daystartdate =request.getParameter("daystartdate");
			String dayenddate =request.getParameter("dayenddate");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}else if("week".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			String daystartdate = VeDate.dateToStr(calendar.getTime());
			String dayenddate = VeDate.dateToStr(date);
			queryParam.put("datetype", "day");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}else if("halfyear".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.MONTH, -6);
			String daystartdate = VeDate.dateToStr(calendar.getTime());
			String dayenddate = VeDate.dateToStr(date);
			queryParam.put("datetype", "day");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}else if("fiveyear".equals(datetype)){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.YEAR, -5);
			String daystartdate = VeDate.dateToStr(calendar.getTime());
			String dayenddate = VeDate.dateToStr(date);
			queryParam.put("datetype", "day");
			queryParam.put("start", daystartdate);
			queryParam.put("end", dayenddate);	
		}		
		   List<UserBillData> accounts = userBillDataService.findByCondition(queryParam, userBillData);
	       ExcelUtil eu = ExcelUtil.getInstance();
	       String files = System.getProperty("user.dir");
	       File fileChild = new File(files);
	       File parentFile = fileChild.getParentFile();
	       String path = parentFile.getPath()+File.separator+"excel"+File.separator+"userBill"+Utils.dateChangeToNumber(new Date())+".xls";
	       File file = new File(path);
	       if(!file.exists()){
	    	   file.getParentFile().mkdirs();
	    	   try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       }
	       Map<String, String> map = new HashMap<>();
	       map.put("title", "会员消费列表");
	       map.put("total", accounts.size()+" 条");
	       map.put("date", Utils.getDate());
	       OutputStream os=null;
	       try {
				os = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	       eu.exportObj2ExcelByTemplate(map, "/userbill-info-template.xls", os, accounts, UserBillData.class, true);
	       FileSystemResource outfile = new FileSystemResource(path);  
	       HttpHeaders headers = new HttpHeaders();  
	       headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	       headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", outfile.getFilename()));  
	       headers.add("Pragma", "no-cache");  
	       headers.add("Expires", "0");  
	       try {
		  
		 
			return ResponseEntity  
			           .ok()  
			           .headers(headers)  
			           .contentLength(outfile.contentLength())  
			           .contentType(MediaType.parseMediaType("application/vnd-ms-excel"))  
			           .body(new InputStreamResource(outfile.getInputStream()));
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	       return null;
	}
	
	@RequestMapping("/charging")
	public String recordData() {
		return "admin/report/charging";
	}
}
