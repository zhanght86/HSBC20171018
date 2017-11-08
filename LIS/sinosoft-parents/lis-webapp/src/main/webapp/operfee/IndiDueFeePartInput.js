var showInfo;
var mDebug="0";
var showInfo;
var turnPage = new turnPageClass();

var tResourceName="operfee.IndiDueFeePartInputSql";
var tResourceSQL1="IndiDueFeePartInputSql1";
var tResourceSQL2="IndiDueFeePartInputSql2";
var tResourceSQL3="IndiDueFeePartInputSql3";
var tResourceSQL4="IndiDueFeePartInputSql4"; 
var tResourceSQL5="IndiDueFeePartInputSql5";
var tResourceSQL6="IndiDueFeePartInputSql6"; 

function PartSingle()
{	
	//if(!beforeSubmit())
	//return false;
	var i = 0;
  	var showStr="批量催收进行中，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./IndiDueFeePartInputQuery.jsp";
	document.getElementById("fm").submit();
}

function beforeSubmit()
{
  if (fm.ManageCom.value=="")
  	{
  		alert("请选择收费机构");
  		fm.ManageCom.focus();
  		return false;
  	}
  if((fm.ManageCom.value).length<=4)
  	{
  		alert("机构过大，请选择四位以下机构。");
  		fm.ManageCom.focus();
  		return false;
  	}
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   //var tSql = "select count(distinct contno) from lcpol "  
   //         + " where 1=1 "
   //         //+ " and paytodate between '"+fm.StartDate.value+"' and '"+fm.EndDate.value+"'"
   //         + " and payintv>'0'"
   //         + " and appflag='1'"
   //         + " and managecom like '"+fm.ManageCom.value+"%%' ";
   var tSql  = wrapSql(tResourceName,tResourceSQL1,[fm.ManageCom.value]); 	   
   if (fm.ContNo.value!="") 
   {
   	  //tSql = tSql + 	 " and contno='"+fm.ContNo.value+"'";	   
      tSql = tSql + wrapSql(tResourceName,tResourceSQL2,[fm.ContNo.value]); 		   
   }        
   if (fm.RiskCode.value!="")
   {
   	  // //tSql = tSql + 	  " and riskcode='"+fm.RiskCode.value+"'";	   
     	tSql = tSql +wrapSql(tResourceName,tResourceSQL3,[fm.RiskCode.value]); 		   
   }   
   if (fm.AgentCode.value!="")
   {
   	// tSql = tSql +  " and AgentCode='"+fm.AgentCode.value+"'";	    
   	  tSql = tSql + wrapSql(tResourceName,tResourceSQL4,[fm.AgentCode.value]); 	   
   }      
   if (fm.SecPayMode.value!="")
   {
   	//// tSql = tSql +   " and exists(select 1 from lccont where contno=lcpol.contno and paylocation'"+fm.SecPayMode.value+"')";	      		    
   	  tSql = tSql +wrapSql(tResourceName,tResourceSQL5,[fm.ManageCom.value]); 	 
   }   
   if (fm.ContType.value!="")
   {
   	  //// tSql = tSql +   " and salechnl='"+fm.ContType.value+"'";	   
   		 tSql = tSql +wrapSql(tResourceName,tResourceSQL6,[fm.ContType.value]); 	    
   }
   
   var varity=easyExecSql(tSql);
   showInfo.close();
	 if(varity!=null && varity!=0){
	 	if(confirm("查询到"+varity+"条数据，确认要批量催收吗？"))
	    {
	    	document.all("magan").disabled = true;
	    	return true;
      	}else
      	{
  	    	return false;
      	}
    }else
   	{
   		alert("没有查询到需要催收保单");
   		return false;
   	}
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;      
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   }	
}

function getTime(strtime,endtime)
{
	var StartTime = strtime;
	var EndTime = endtime;
}

function showRecord(strRecord)
{
  //保存查询结果字符串
  turnPage.strQueryResult  = strRecord;
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
   
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray = new Array(1,20);
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配

  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);

  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = ContGrid;             
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  //调用MULTILINE对象显示查询结果

  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}

function afterSubmit1( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    resetForm();    
  }
}

function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("在LJSPayPersonInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

function easyQueryClick()
{  
	if(fm.ContNo.value == "" && document.fm.StartDate.value == ""){
		window.alert("您没有设定催收条件,请选择保单号或时间段!");
		document.fm.EndDate.focus;
		return false;
	}

	if(fm.EndDate.value != "" &&document.fm.StartDate.value == ""){
		window.alert("您没有设定时间段,请设定时间段!");
		document.fm.EndDate.focus;
		return false;
	}

  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
	fm.action = "./IndiDueFeeQueryDate.jsp";
	document.getElementById("fm").submit();
}

function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  }
}

function afterQuery( FlagStr, content )
{
  showInfo.close();
	
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
}

function queryAgent()
{
	if( verifyInput2() == false ) return false;
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
	
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
      } 
	}	
}
