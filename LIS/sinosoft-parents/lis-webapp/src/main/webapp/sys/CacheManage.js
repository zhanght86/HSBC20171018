$(document).ready(initCacheInfo);

function initCacheInfo(){
	getCacheGrid();
}


function getCacheGrid(){
	  var params = { 
			oper:'1'//初始化所有Cache
		};
    var dWidth = $(document.body).width()*0.80;
    $('#cacheGrid').datagrid({
			   			    title:'缓存域信息',
			   			    width:dWidth,
			   			    url:'CacheManageQuery.jsp',
			   			    dataType:'json', 
									sortName: 'cacheName',
									sortOrder: 'asc',
									remoteSort: false,
									idField:'cacheName',
									queryParams:params,	
			   			    columns:[[			
				 			        {title:'缓存域名称',field:'cacheName',width:dWidth*0.18,align:'center'},
				 			        {title:'可缓存最大元素数量',field:'maxElements',width:dWidth*0.21,align:'center'},
				 			        {title:'已缓存元素数量',field:'size',width:dWidth*0.21,align:'center'},
				 			        {title:'缓存占用空间（MB）',field:'cacheSize',width:dWidth*0.187,align:'center'},
				 			        {title:'操作',field:'opt',width:dWidth*0.18,align:'center',
				 			        	formatter:function(value,row){
													return '<a href="javascript:cleanCache(\''+row.cacheName+'\')">重置缓存</a>';
												}
											}
			   			    ]],
			   			    rownumbers:true,
									singleSelect:true,
									striped: true,
									nowrap: true,
									loadMsg:'数据加载中请稍后……', 
			   			    pagination:false,
			   			    animate:true
		});
}


function cleanCache(cacheName){

		if(!confirm("确认要重置该缓存数据？")){
			return;
		}
		lockPage('正在重置缓存数据，请稍候!');
		var params = { 
			oper:'2',
			cacheName:cacheName
		};
		$.post(
			'CacheManageQuery.jsp',
			params,
			function(data){
				if(data.msg=="succ"){
					alert("重置成功");
					getCacheGrid();
				}else{
					alert("重置失败");
				}
				unLockPage();
			},'json');
}

function getTimeForURL()
{
  var now = new Date();
  var year = now.getYear();
  var month = now.getMonth() + 1;
  var date = now.getDate();
  var hours = now.getHours();
  var minutes = now.getMinutes();
  var seconds = now.getSeconds();
  
  
  var timeValue = "";
  timeValue += year ;
  timeValue += ((month < 10) ? "0" : "") + month ;
  timeValue += ((date < 10) ? "0" : "") +date ;

  timeValue += hours;
  timeValue += ((minutes < 10) ? "0" : "") + minutes;
  timeValue += ((seconds < 10) ? "0" : "") + seconds;
  return timeValue;
}

function lockPage(msg){ 
		var loadMsg = "Loading........";
		if(msg != null && msg != ""){
				loadMsg = msg;
		}
		var dheight = 0;
		var dWidth = 0;
	 	var sh=document.body.scrollHeight;
 		var ch =document.body.clientHeight;	
 		if(sh > ch){
 			dheight=sh;
 		}else{
 			dheight=ch;
 		}
 		var sw = document.body.scrollWidth;
 		var cw =document.body.clientWidth;
		if(sw >cw ){
			dWidth=sw; 
		}else{
			dWidth=cw;
		}
	var body = $(document.body);
	$("<div class=\"datagrid-mask\"></div>").css(
			{display:"block",
			 width:dWidth,
			 height:dheight
			}).appendTo(body);
	$("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(body).css(
			{display:"block",
			 left:(dWidth-$("div.datagrid-mask-msg",body).outerWidth())/2,
			 top:(dheight-$("div.datagrid-mask-msg",body).outerHeight())/2
			 });                    
}


function unLockPage()
{ 
		$(document.body).find("div.datagrid-mask-msg").remove();
		$(document.body).find("div.datagrid-mask").remove();
}
