//程序名称：TaskService.js
//程序功能：
//创建日期：2004-12-15 
//创建人  ：ZhangRong
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var tMaxProcessValue = 0 ;
var tCurrentTitle = "";
//var mSwitch = parent.VD.gVSwitch;
var mySql = new SqlClass();
var k = 0;
/*********************************************************************
 *  提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{
	//var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	if( mAction == "")
		return;

	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	document.getElementById("fm").submit(); //提交
}

function startEngine()
{
	mAction = "START";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

function stopEngine()
{
	mAction = "STOP";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}


/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	try{
		showInfo.close();
	}
	catch(e)
	{
		}
	if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{ 
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	mAction = ""; 
	//queryTaskPlanInfo();
	//queryTaskInfo();
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}


function initAllTabs()
{
	//获取需要初始化的tab
	lockPage("正在获取批处理引擎的标签...........");
  jQuery.post(
  	"./InitTaskTab.jsp",
  	//params,
  	function(data) {
  		unLockPage();
  		lockPage("正在加载批处理引擎的标签...........");
  		$('#initProcessBar').show();
			TabsMapArray = data;
			if(TabsMapArray!=null)
			{
			var tMax = TabsMapArray.length;
			var tCurrent = 0;
			for(i=0;i<TabsMapArray.length;i++)
			{
				tCurrent = i;
				var tPercent = ((tCurrent+1)*100)/tMax;
				tMaxProcessValue = tPercent;				
				dealProcessBar();
			 	var tTaskTabID = TabsMapArray[i].TaskTabID ;
    		var tTaskTabName = TabsMapArray[i].TaskTabName ;
    		var tType = TabsMapArray[i].Type ;
    		var tOtherProperty = TabsMapArray[i].OtherProperty ;
    		var tTabSrc = TabsMapArray[i].TabSrc ;
    		var tNote = TabsMapArray[i].Note ;
    		var tIFrameID = TabsMapArray[i].IFrameID ;
    	
    	 var content = "<div id="+tTaskTabID+"><iframe id=\""+tIFrameID+"\" "+tOtherProperty+" src=\""+tTabSrc+"\"></iframe>";
				$('#TaskTab').tabs('add',{
					id:tTaskTabID,
			    title:tTaskTabName,
			    content:content,
			    fit:true,
			    closable:false
		    });
		    
		    $('#TaskTab').tabs('select',TabsMapArray[0].TaskTabName);
			}		
			
				$('#TaskTab').tabs({ 
						onSelect:function(title){ 
				 			refresh_tab();
				} 
			});
		}

		},"json" 
	);
}

/*
	初始化button,只有拥有任务运行管理标签页的用户才可以操作启动和停止引擎按钮.
*/
function initButton()
{
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceMissionSql");
	mySql.setSqlId("TaskServiceMissionSql1");  
	mySql.addSubPara(mOperator); 
	//mySql.addSubPara('002'); 
	var ttemp = easyExecSql(mySql.getString());
	//alert(ttemp);
	if(ttemp!=null&&ttemp=='1')
	{
		$('#startButton').removeAttr("disabled");
		$('#stopButton').removeAttr("disabled");
	}
}

function dealProcessBar()
{
	var value = $('#initProcessBar').progressbar('getValue');
			if (value < tMaxProcessValue){
				value += Math.floor(Math.random() * 15);
				$('#initProcessBar').progressbar('setValue', value);
				setTimeout(arguments.callee, 200);
			}
			if(value==100)
			{
				$('#initProcessBar').hide();
				unLockPage();
			}
}

function refresh_tab()
{
			var refresh_tab = $('#TaskTab').tabs('getSelected');
			if(refresh_tab && refresh_tab.find('iframe').length > 0){
					var _refresh_ifram = refresh_tab.find('iframe')[0];
					
					//只有iframe 的refreshFlag 属性设置为1时才做刷新
					if(_refresh_ifram.refreshFlag!=null&&_refresh_ifram.refreshFlag=='1')
					{
						lockPage('正在刷新,请稍候...');
						var _Src = _refresh_ifram.src;
						var refresh_url = _Src;
						_refresh_ifram.contentWindow.location.href=refresh_url;
						unLockPage();	
					}
			}
}