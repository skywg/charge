package com.iycharge.server.admin.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.review.Review;
import com.iycharge.server.domain.entity.review.ReviewRating;
import com.iycharge.server.domain.service.ReviewService;

@Controller
@RequestMapping("/admin/reviews")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	private static Review staticReview = null;
	@RequestMapping("/")
    public String index(Review review,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable, Model model,String flag) {
		if(flag!=null&&staticReview!=null){
			String fields[] = {"updatedAt","name","realName","differentiate","transientStatus"};
	    	String key="";
	    	for(String fieldName:fields){
	    		if ("updatedAt".equals(fieldName)) {
					if (staticReview.getStartAt()!= null && staticReview.getUpdatedAt() != null) {
						List<Date> date = new ArrayList<Date>();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(staticReview.getUpdatedAt());
						date.add(staticReview.getStartAt());
						date.add(calendar.getTime());
						SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
						String startAt =sdf.format(date.get(0));
						String updatedAt =sdf.format(date.get(1));
						key+="startAt"+"="+startAt+"&"+"updatedAt"+"="+updatedAt+"&";
					}
				} else if("realName".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName, staticReview.getAccount());
					if (o!= null && !"".equals(o)) {
						key+="account.realName"+"="+o.toString()+"&";
					}
				}else if("name".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName, staticReview.getStation());
					if (o != null && !"".equals(o)) {
						key+="station.name"+"="+o.toString()+"&";
					}
				}else if("differentiate".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,staticReview);
					if (o != null && !"".equals(o)) {
						key+="differentiate"+"="+o.toString()+"&";
					}
				}else if("transientStatus".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,staticReview);
					if (o != null && !"".equals(o)) {
						key+="transientStatus"+"="+o.toString()+"&";
					}
				}
			}
	    	if(!StringUtils.isEmpty(key)){
	    		key = key.substring(0,key.length()-1);
	    	}
	    	PageWrapper<Review> page = new PageWrapper<>(reviewService.findAllSearch(fields,staticReview,pageable), "/admin/reviews/research?"+key);
	    	model.addAttribute("page", page);
	        model.addAttribute("reviewValue", staticReview);
		}else{
			PageWrapper<Review> page = new PageWrapper<>(reviewService.findAll(pageable), "/admin/reviews/");
			model.addAttribute("page", page);
		    model.addAttribute("reviewValue", review);
		    staticReview = null;
		}
		
		return "admin/review/index";
	}
	@RequestMapping("/verifyY/{id}")
    public String verifyY(Model model ,@PathVariable("id") Long id) {
    	Review review = reviewService.findById(id);		
    	review.setStatus(Byte.parseByte("1"));;	
    	reviewService.save(review);
		return "redirect:/admin/reviews/?flag=true";
	}
    @RequestMapping("/verifyN/{id}")
    public String verifyN(Model model ,@PathVariable("id") Long id) {
    	Review review = reviewService.findById(id);		
    	review.setStatus(Byte.parseByte("0"));;	
    	reviewService.save(review);
		return "redirect:/admin/reviews/?flag=true";
	}
    @RequestMapping("/addReview")
    public String addReview(Model model , Long id,String content) {
   	Review review = reviewService.findById(id);
   	Review review2 =new Review();
    	ReviewRating rating =new ReviewRating();
    	rating.setTotal(BigDecimal.valueOf(0));
    	review2.setContent(content);
    	review2.setDifferentiate("0");
    	review2.setStatus(Byte.parseByte("1"));
    	review2.setParent(review);
    	reviewService.save(review2);
    	List<Review> reviews =review.getReplies();
    	reviews.add(review2);
    	return "redirect:/admin/reviews/?flag=true";
	}
    @RequestMapping("/research")
    public String search(Model model, @ModelAttribute Review review,@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
    	String fields[] = {"updatedAt","name","realName","differentiate","transientStatus"};
    	String key="";
    	for(String fieldName:fields){
    		if ("updatedAt".equals(fieldName)) {
				if (review.getStartAt()!= null && review.getUpdatedAt() != null) {
					List<Date> date = new ArrayList<Date>();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(review.getUpdatedAt());
					date.add(review.getStartAt());
					date.add(calendar.getTime());
					SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
					String startAt =sdf.format(date.get(0));
					String updatedAt =sdf.format(date.get(1));
					key+="startAt"+"="+startAt+"&"+"updatedAt"+"="+updatedAt+"&";
				}
			} else if("realName".equals(fieldName)){
				Object o = ReflectField.getFieldValueByName(fieldName, review.getAccount());
				if (o!= null && !"".equals(o)) {
					key+="account.realName"+"="+o.toString()+"&";
				}
			}else if("name".equals(fieldName)){
				Object o = ReflectField.getFieldValueByName(fieldName, review.getStation());
				if (o != null && !"".equals(o)) {
					key+="station.name"+"="+o.toString()+"&";
				}
			}else if("differentiate".equals(fieldName)){
				Object o = ReflectField.getFieldValueByName(fieldName,review);
				if (o != null && !"".equals(o)) {
					key+="differentiate"+"="+o.toString()+"&";
				}
			}else if("transientStatus".equals(fieldName)){
				Object o = ReflectField.getFieldValueByName(fieldName,review);
				if (o != null && !"".equals(o)) {
					key+="transientStatus"+"="+o.toString()+"&";
				}
			}
		}
    	if(!StringUtils.isEmpty(key)){
    		key = key.substring(0,key.length()-1);
    	}
    	PageWrapper<Review> page = new PageWrapper<>(reviewService.findAllSearch(fields,review,pageable), "/admin/reviews/research?"+key);
    	staticReview = review;
    	model.addAttribute("page", page);
        model.addAttribute("reviewValue", review);
        return "admin/review/index";
    }
    	
  /*  @RequestMapping("/check/{id}")
    public String checkContent(Model model ,@PathVariable("id") Long id) {
    	Review review = reviewService.findById(id);		
    	List<Review> reviews = review.getReplies();
    	model.addAttribute("reviews", reviews);
		return "redirect:/admin/reviews/";
	}*/
}

