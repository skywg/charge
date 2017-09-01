;(function($){  
    $.fn.extend({
    	"Table":function(divId,id,row,col,headList,content){  
        // var row = 16;
        var table = $("<table class='re' id="+id+">"),
        // 设置表头
        setHead = function(){
        	for(var n = 0;n<col;n++){
				var th = $("<th>"+headList[n]+"</th>");
				$(th).appendTo(table);
			}
		},
		// 设置内容
		setContent = function(){	
			var td=new Array();
			// $.each(content, function(i, item){
			// 	$.each(item,function(j,val){
			// 		td.push(val);								
			// 	});				
			// });	
			for(var y=0;y<row;y++){
				var tr = $("<tr>");		
				$(tr).appendTo(table);
				for(var x=0+(col*y);x<col*(y+1);x++){
					$("<td>"+" "+"</td>").appendTo(tr);
				}					
				$("</tr>").appendTo(table);
				// 设置序号
				for(var i =0;i<row;i++){
					$("#re tr:eq("+i+") td:eq(0)").text(i+1);
				}
			}

		};

		table.appendTo($(divId));
		
		$("<thead>").appendTo(table);
		setHead();
		$("</thead>").appendTo(table);
		$("<tbody>").appendTo(table);
		setContent();
		$("</tbody>").appendTo(table);
		$(divId).append("</table>");
		// 样式
		$(".re th:eq(0),#bank th:eq(0)").css({"width":"50px","text-align":"center"});
		$("#re tr td:last-child()").css({"width":"50px","margin":"0 auto","vertical-align": "middle"})
		$(".re tr td:first-child()").css({"background":"linear-gradient(to bottom, #f9f9f9 , #f1f1f1)","text-align":"center"})
		
    }//table结束  
	});//fn结束
}(jQuery));
  