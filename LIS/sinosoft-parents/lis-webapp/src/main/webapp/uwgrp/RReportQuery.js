//程序名称：RReportQuery.js
//程序功能：生存调查报告查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //问题件操作位置 1.核保
var sqlresourcename = "uwgrp.RReportQuerySql";
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    showInfo.close();
    
  }
  else
  { 
 // showInfo.close();
	var showStr="操作成功";
  alert(showStr);
 // parent.close();
    //执行下一步操作
  }
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
  else 
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}
         

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

// 查询按钮
function easyQueryClick(tContNo)
{
	var strsql = "";
	

		
        if(tContNo != "")
        {/*
		strsql = "select contno,name,operator,makedate,replyoperator,replydate,replyflag,prtseq,RReportReason from lcrreport where 1=1 "
		        + " and contno = '"+tContNo+"'" ;
		 */    	
	var sqlid1="RReportQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查到该客户的生存调查信息！");
	initQuestGrid();
    return true;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
}

// 查询按钮
function easyQueryChoClick(tContNo)
{	
  
	
	var tContNo = fm.ContNo.value;

	var tSelNo = QuestGrid.getSelNo()-1;

	var tPrtSeq = QuestGrid.getRowColData(tSelNo,8);
	
		if (tContNo != " ")
	{
		//strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and prtseq = '"+tPrtSeq+"'";
		
		var sqlid2="RReportQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
		mySql2.addSubPara(tPrtSeq);
		
		turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
		fm.Contente.value = strSQL;
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
    return "";
    
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);    
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = RReportGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}


	//查询SQL，返回结果字符串
	/*
  	var strsql= "select Contente,ReplyContente,operator,makedate,modifydate,Rreportfee from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and PrtSeq = '"+tPrtSeq+"'"; 	
	*/	
		var sqlid3="RReportQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);
		mySql3.addSubPara(tPrtSeq);
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(mySql3.getString());
       
       if(arrReturn!=null)
       {
        
		fm.all('Contente').value = arrReturn[0][0];
		fm.all('ReplyContente').value = arrReturn[0][1];
		fm.all('Operator').value = arrReturn[0][2];
		fm.all('MakeDate').value = arrReturn[0][3];
		fm.all('ReplyDate').value = arrReturn[0][4];
		fm.all('RReportFee').value = arrReturn[0][5];
	}
  
  return true;
}


function initReport()
{
  
	var i,j,m,n;
	var returnstr;
  var tPrtSeq = fm.all('PrtSeq').value ;
	//alert(tPrtSeq);
	//查询SQL，返回结果字符串
	/*
 var strSql = " select ContNo,Name,Operator,MakeDate,ReplyOperator,ReplyDate,ReplyFlag from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
  */
  var sqlid4="RReportQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tPrtSeq);
  
   turnPage.queryModal(mySql4.getString(), QuestGrid);
   
 //  strSql ="select RReportItemCode,RReportItemName from lcrreportitem where  prtseq = '"+tPrtSeq+"'";
  
  var sqlid5="RReportQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tPrtSeq);
		
   turnPage.queryModal(mySql5.getString(), RReportGrid);
   /*
   strSql = "select Name,CustomerNo from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
    */
    var sqlid6="RReportQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tPrtSeq);
    
    arrResult = easyExecSql(mySql6.getString(),1,0);
   
    fm.all('CustomerName').value = arrResult[0][0];
	  fm.all('CustomerNo').value = arrResult[0][1];
   

}



function QueryCustomerList(tContNo)
{
	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	//strsql = "select appntno, appntname from lcappnt where contno = '" + tContNo+"'";
	
	var sqlid7="RReportQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);  
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][1] + "|" + turnPage.arrDataCacheSet[i][0];
		}
	}
	//strsql = "select insuredno, name from lcinsured where contno = '" + tContNo + "' and not insuredno in (select appntno from lcappnt where contno = '" + tContNo + "')";
	
	var sqlid8="RReportQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(tContNo);
		mySql8.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);  
	if (turnPage.strQueryResult != null && turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		n = turnPage.arrDataCacheSet.length;
		for (i = 0; i < n; i++)
		{
			j = i + m + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][1] + "|" + turnPage.arrDataCacheSet[i][0];
		}
	}
//	alert ("tcodedata : " + tCodeData);

	return tCodeData;
}

function showSelectRecord(tContNo)
{
	easyQueryClick(tContNo, fm.all("CustomerNo").value);
}

function afterCodeSelect(cCodeName, Field)
{
	if (cCodeName == "CustomerName")
	{
		showSelectRecord(fm.all("ContNo").value);
	}
}

/*********************************************************************
 *  生调信息保存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function saveRReport()
{
	var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  
  fm.action= "./RReportQuerySave.jsp";
  fm.submit(); //提交
   showInfo.close();
}

function easyQueryClickItem()
{
	var tContNo = fm.ContNo.value;
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
 
	
		if (tContNo != " ")
	{
		//strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')";
	
		var sqlid9="RReportQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(tContNo);
		mySql9.addSubPara(tMissionID);
		mySql9.addSubPara(tSubMissionID);
	
		turnPage.strQueryResult  = easyQueryVer3(mySql9.getString(), 1, 1, 1);  
		
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
    return "";
    
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);    
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = RReportGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}


	//查询SQL，返回结果字符串
	/*
  	var strsql= "select Contente from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')"; 	
	*/	
		var sqlid10="RReportQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(tContNo);
		mySql10.addSubPara(tMissionID);
		mySql10.addSubPara(tSubMissionID);
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(mySql10.getString());
       
       if(arrReturn!=null)
       {
        
		fm.all('Contente').value = arrReturn[0][0];	
}

}