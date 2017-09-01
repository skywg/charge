(function($){
	$.fn.slide=function(options){
		$.fn.slide.defaults={
			big_pic:null,	//大图框架
			min_pic:null,	//小图框架
			big_prev:null,	//大图左箭头
			big_next:null,	//大图右箭头
			min_prev:null,//小图左箭头
			min_next:null,//小图右箭头
			autoply:false,	//是否自动播放
			interTime:5000,	//图片自动切换间隔
			delayTime:800,	//切换一张图片时间
			order:0,	//当前显示的图片（从0开始）
			min_num:null, //小图显示数量
		};
		return this.each(function(){
			var opts = $.extend({},$.fn.slide.defaults,options);
			var slider=$(this);
			
			var picnum=slider.find(opts.big_pic).find("li").length;
			var picw=slider.find(opts.big_pic).find("li").outerWidth(true);
			var pich=slider.find(opts.big_pic).find("li").outerHeight(true);

			var picminnum=slider.find(opts.min_pic).find("li").length;	
			var picminw=slider.find(opts.min_pic).find("li").outerWidth(true);
			var picminh=slider.find(opts.min_pic).find("li").outerHeight(true);

			var picpopnum=slider.find(opts.pop_pic).find("li").length;		
			var picpopw=slider.find(opts.pop_pic).find("li").outerWidth(true);
			
			var pictime;
			var tpqhnum=0;
			var xtqhnum=0
			var popnum=0;

			slider.find(opts.big_pic).find("ul").width(picnum*picw);
			slider.find(opts.min_pic).find("ul").width(picminnum*picminw);
			slider.find(opts.pop_pic).find("ul").width(picpopnum*picpopw);

			//点击小图切换大图
			slider.find(opts.min_pic).find("li").click(function(){
				tpqhnum=xtqhnum=slider.find(opts.min_pic).find("li").index(this);
				show(tpqhnum);
				minshow(xtqhnum);
			}).eq(opts.order).trigger("click");

			if(opts.autoplay==true){
				//自动播放
				pictime=setInterval(function(){
					show(tpqhnum);
					minshow(tpqhnum);
					tpqhnum++;
					xtqhnum++;
					if(tpqhnum==picnum){tpqhnum=0};
					if(xtqhnum==picminnum){xtqhnum=0};
				},opts.interTime);

				//鼠标经过停止播放
				slider.hover(function(){
					clearInterval(pictime);
				},function(){
					pictime=setInterval(function(){
						show(tpqhnum);
						minshow(tpqhnum);
						tpqhnum++;
						xtqhnum++;
						if(tpqhnum==picnum){tpqhnum=0};
						if(xtqhnum==picminnum){xtqhnum=0};
					},opts.interTime);
				});
			}

			//小图左右切换
			slider.find(opts.min_prev).click(function(){
				if(tpqhnum==0){tpqhnum=picnum};
				if(xtqhnum==0){xtqhnum=picnum};
				xtqhnum--;
				tpqhnum--;
				show(tpqhnum);
				minshow(xtqhnum);
			});
			slider.find(opts.min_next).click(function(){
				if(tpqhnum==picnum-1){tpqhnum=-1};
				if(xtqhnum==picminnum-1){xtqhnum=-1};
				xtqhnum++;
				minshow(xtqhnum)
				tpqhnum++;
				show(tpqhnum);
			});

			//大图左右切换
			slider.find(opts.big_prev).click(function(){
				if(tpqhnum==0){tpqhnum=picnum};
				if(xtqhnum==0){xtqhnum=picnum};
				xtqhnum--;
				tpqhnum--;
				show(tpqhnum);
				minshow(xtqhnum);	
			});
			slider.find(opts.big_next).click(function(){
				if(tpqhnum==picnum-1){tpqhnum=-1};
				if(xtqhnum==picminnum-1){xtqhnum=-1};
				xtqhnum++;
				minshow(xtqhnum)
				tpqhnum++;
				show(tpqhnum);
			});

			//小图切换过程
			function minshow(xtqhnum){
				var mingdjl_num =xtqhnum-opts.min_num+2;
				var mingdjl_w=-mingdjl_num*picminw;
				var mingdjl_h=-mingdjl_num*picminh;
				if(picminnum>opts.min_num){
					if(xtqhnum<3){mingdjl_w=0;}
					if(xtqhnum==picminnum-1){mingdjl_w=-(mingdjl_num-1)*picminw;}
					slider.find(opts.min_pic).find('ul').stop().animate({'left':mingdjl_w},opts.delayTime);
				}			
			};

			//大图切换过程
			function show(tpqhnum){
				var gdjl_w=-tpqhnum*picw;
				var gdjl_h=-tpqhnum*pich;
				slider.find(opts.big_pic).find('ul').stop().animate({'left':gdjl_w},opts.delayTime);
				slider.find(opts.min_pic).find('li').eq(tpqhnum).addClass("on").siblings(this).removeClass("on");
			};		
		});
		
	};
})(jQuery)