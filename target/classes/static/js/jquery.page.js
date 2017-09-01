;(function($){
	$.fn.extend({
		"PaginBar":function(divId,table,show_per_page){
			divId = $("#"+divId+""),tableId = $("."+table+""); 
			var number_of_items = tableId.find("tr").length;
			var number_of_pages = Math.ceil(number_of_items/show_per_page);
			var index_of_pages = number_of_pages-1;
			var current_link = 0;
			var navigation_html = '<a class="previous_link" href="javascript:go_to_page(0);go_first();"><div id="first_page">首页</div></a><a class="previous_link" href="javascript:previous();"><div>上一页</div></a>';
			navigation_html += '<span class="page_span">第</span>'
			navigation_html += '<input type="text" class="num_box" longdesc="' + current_link +'" value="'+(current_link + 1)+'">' 
			navigation_html += '<span class="page_span">页  总 ' + number_of_pages +'页</span>'
			navigation_html += '<a class="next_link" href="javascript:void(0)"><div>下一页</div></a><a class="next_link" href="javascript:go_to_page(' + index_of_pages +');go_last();"><div id="last_page">尾页</div></a>';
			
			previous = function(){
				current_link=$(".num_box").val();
				new_page = current_link-2;
				if(new_page>=0){
					go_to_page(new_page);
					current_link = current_link-1;
					$(".num_box").val(parseInt($(".num_box").val())-1)	
				}
			},
			next =function(){
				current_link=$(".num_box").val();
				new_page = current_link;
				if(new_page<number_of_pages){
					go_to_page(new_page);
					current_link = current_link+1;
					$(".num_box").val(parseInt($(".num_box").val())+1)	
				}
			},
			go_to_page = function(page_num){
				start_from = page_num * show_per_page;
				end_on = start_from + show_per_page;
				$(tableId).find("tr").css('display', 'none').slice(start_from, end_on).css({
					"display": "table-row",
					"width":"100%;"
				});
				$('.page_link[longdesc=' + page_num +']').addClass('active_page').siblings('.active_page').removeClass('active_page');
				current_link = page_num;
				// $('#current_page').val(page_num);
			},
			go_first = function(){
				$(".num_box").val(1);
			},
			go_last = function(){
				$(".num_box").val(number_of_pages);
			};
			
			$(divId).html(navigation_html);
			// $('#page_navigation .page_link:first').addClass('active_page');
			$(tableId).find("tr").css({'display':'none'});
			$(tableId).find("tr").slice(0, show_per_page).css({
				"display": "table-row",
				"width":"100%;"});	
			
			$(".previous_link div,.next_link div").css({
				"display":"inline-block"
			});
			$("#first_page").css({"background-position":"0 3px"});
			$(".next_link").click(function(){next();})
			$(document).ready(function(){
			$(".num_box").on({
				"keydown":function(e){
					var key = e.which;
					var will_num = $(this).val()-1;
					var max_page = number_of_pages-1;
					// alert(max_page)
					if(key == 13) {
						e.preventDefault();
						if(will_num>max_page){
							go_to_page(max_page);
							$(this).val(max_page+1)
						}	
						else{go_to_page(will_num)};
					}
				},
				"click":function(){
					$(this).css({"border":"1px solid #8bade4"})
				},
				"blur":function(){
					$(this).css({"border":"1px solid #cccccc"})
				}
			})	
		})
		} //主函数
	});//fn
}(jQuery));