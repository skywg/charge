package com.iycharge.server.admin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iycharge.server.domain.entity.review.Review;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.StationService;


@Controller
@RequestMapping("/share/pages")
public class ShareController {
   
	@Autowired
	private StationService stationService;
   @RequestMapping("/show")
   public String returnPage(Model model , Long stationId,String positionX , String positionY){
	   Station station = stationService.findById(stationId);
	   if(station==null){
		   station = new Station();
	   }
	   Set<Review> reviews = station.getReviews();
	   double review = getAvg(reviews);
	   model.addAttribute("station",station);
	   model.addAttribute("sname",station.getName());
	   model.addAttribute("review",review);
	   model.addAttribute("reviews",station.getReviews());
	   model.addAttribute("positionX",positionX);
	   model.addAttribute("positionY",positionY);
	   return "share/shareStation";
   }
   private double getAvg(Set<Review> reviews){
	   if(reviews==null||reviews.size()<1){
		   return 0;
	   }
	   double avg = 0;
	   BigDecimal count = new BigDecimal(0);
	   Iterator<Review> iter = reviews.iterator();
	   while(iter.hasNext()){
		   Review tmp = iter.next();
		   BigDecimal bd = tmp.getRating().getTotal();
		   count=count.add(bd);
	   }
	   avg = count.divide(new BigDecimal(reviews.size()),2,BigDecimal.ROUND_HALF_UP).doubleValue();
	   return avg;
   }
   /*private float forShow(Double num){
	   String strNum = num.toString();
	   float forShow = 0;
	   int intScore = Integer.parseInt(strNum.substring(0, strNum.indexOf(".")));
	   float floatScore = Float.parseFloat("0."+strNum.substring(strNum.indexOf(".")+1));
	   if(floatScore<0.25){
		   forShow = intScore;
	   }else{
		   
	   }
   }*/
}
