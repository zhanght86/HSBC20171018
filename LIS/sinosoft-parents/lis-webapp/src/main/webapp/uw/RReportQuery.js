//程序名称：RReportQuery.js
//程序功能：生存调查报告查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人：ln    更新日期：2008-10-24   更新原因/内容：根据新核保要求进行修改

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //问题件操作位置 1.核保
var str_sql = "",sql_id = "", my_sql = ""; 

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
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
    showInfo.close();
    
  }
  else
  { 
 // showInfo.close();
	var showStr="操作成功";
  alert(showStr);
 // parent.close();
    //执行下一步操作
    easyQueryClick(tContNo);
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
        {
//		strsql = "select (select prtno from lccont where contno=l.contno),remark,operator,makedate,replyoperator,"
//		        + " (select username from lduser where usercode=ReplyOperator),"
//		        + " replydate,(case replyflag when '0' then '未回复' when '' then '未回复' else '已回复' end),prtseq,(select codename from ldcode where codetype ='rreportreason' and code=l.RReportReason) from lcrreport l where 1=1 "
//		        + " and contno = '"+tContNo+"'" ;
		sql_id = "RReportQuerySql11";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(tContNo);//指定传入的参数
		strsql = my_sql.getString();     	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
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

	var tPrtSeq = QuestGrid.getRowColData(tSelNo,9);
	
	//查询SQL，返回结果字符串
	
//  	var strsql= "select Contente,ReplyContente,remark,makedate,replydate,Rreportfee,replyoperator from LCRReport where 1=1"	
//				+ " and ContNo = '"+tContNo+"'"
//				+ " and PrtSeq = '"+tPrtSeq+"'"; 
  	var strsql="";
  	sql_id = "RReportQuerySql12";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tPrtSeq);//指定传入的参数
	strsql = my_sql.getString();     	
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        
		document.all('Contente').value = arrReturn[0][0];
		document.all('ReplyContente').value = arrReturn[0][1];
		document.all('Operator').value = arrReturn[0][2];
		document.all('MakeDate').value = arrReturn[0][3];
		document.all('ReplyDate').value = arrReturn[0][4];
		document.all('RReportFee').value = arrReturn[0][5];
		document.all('PrtSeq').value=tPrtSeq;
		
		if(QueryFlag == '1')//人工核保界面
		{
			if(arrReturn[0][2]!=null || arrReturn[0][2]!="")
				document.all('Assess').disabled = false;
			   
			document.all('saveresult').disabled = false;
	    }
		else//查询界面
		{
			divRReportButton.style.display = "none";
//			var strsql1= "select 1 from LCScoreRReport where 1=1 and ContNo = '"+tContNo+"' and customerno='"+arrReturn[0][6]+"'"; 	
			var strsql1="";
		  	sql_id = "RReportQuerySql13";
			my_sql = new SqlClass();
			my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(tContNo);//指定传入的参数
			my_sql.addSubPara(arrReturn[0][6]);//指定传入的参数
			strsql1 = my_sql.getString();     	
			var arrReturn1 = new Array();
		    arrReturn1 = easyExecSql(strsql1);       
       		if(arrReturn1!=null)
       		{
       			document.all('Assess').disabled = false;
       		}			
	    }	    

	}
  
  return true;
}


function initReport()
{
  
	var i,j,m,n;
	var returnstr;
	var strSql="";
  var tPrtSeq = document.all('PrtSeq').value ;
	//alert(tPrtSeq);
	//查询SQL，返回结果字符串
// var strSql = " select ContNo,Name,Operator,MakeDate,ReplyOperator,ReplyDate,ReplyFlag from lcRReport where 1=1 "
//          + " and PrtSeq = '"+tPrtSeq+"'";
 
	sql_id = "RReportQuerySql14";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPrtSeq);//指定传入的参数
	strSql = my_sql.getString();     	
   turnPage.queryModal(strSql, QuestGrid);
   
//   strSql ="select RReportItemCode,RReportItemName,Remark from lcrreportitem where  prtseq = '"+tPrtSeq+"'";
    sql_id = "RReportQuerySql15";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPrtSeq);//指定传入的参数
	strSql = my_sql.getString();     	
   turnPage.queryModal(strSql, RReportGrid);
   
//   strSql = "select Name,CustomerNo from lcRReport where 1=1 "
//          + " and PrtSeq = '"+tPrtSeq+"'";
    sql_id = "RReportQuerySql16";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPrtSeq);//指定传入的参数
	strSql = my_sql.getString();     	
    arrResult = easyExecSql(strSql,1,0);
   
    document.all('CustomerName').value = arrResult[0][0];
	  document.all('CustomerNo').value = arrResult[0][1];
   

}



function QueryCustomerList(tContNo)
{
	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
//	strsql = "select appntno, appntname from lcappnt where contno = '" + tContNo+"'";
	sql_id = "RReportQuerySql17";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	strsql = my_sql.getString();     	
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
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
//	strsql = "select insuredno, name from lcinsured where contno = '" + tContNo + "' and not insuredno in (select appntno from lcappnt where contno = '" + tContNo + "')";
	sql_id = "RReportQuerySql18";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tContNo);//指定传入的参数
	strsql = my_sql.getString();     	
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
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
	easyQueryClick(tContNo, document.all("CustomerNo").value);
}

function afterCodeSelect(cCodeName, Field)
{
	if (cCodeName == "CustomerName")
	{
		showSelectRecord(document.all("ContNo").value);
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
  var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();
  
  fm.action= "./RReportQuerySave.jsp";
  document.getElementById("fm").submit(); //提交
   showInfo.close();
}

//ln 2008-10-24 add
function soreRReport()
{
	var type = "2";
	if(QueryFlag == '1')//人工核保界面
	{
		type = "1";
	}
	window.open("./UWscoreRReportMain.jsp?ContNo="+fm.ContNo.value+"&PrtSeq="+fm.PrtSeq.value+"&Type="+type,"window5");

}

function easyQueryClickItem()
{
	var tContNo = fm.ContNo.value;
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
 
	
		if (tContNo != " ")
	{
//		strSQL = "select RReportItemCode,RReportItemName,Remark from lcrreportitem where contno = '"+tContNo+"' and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')";
		var strSQL = "";
		sql_id = "RReportQuerySql19";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(tContNo);//指定传入的参数
		my_sql.addSubPara(tMissionID);//指定传入的参数
		my_sql.addSubPara(tSubMissionID);//指定传入的参数
		strSQL = my_sql.getString();     	
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
		
  
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
	
//  	var strsql= "select Contente from LCRReport where 1=1"	
//				+ " and ContNo = '"+tContNo+"'"
//				+ " and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')"; 	
  	var strsql = "";
	sql_id = "RReportQuerySql20";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportQuerySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tMissionID);//指定传入的参数
	my_sql.addSubPara(tSubMissionID);//指定传入的参数
	strsql = my_sql.getString();     		
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        
		  document.all('Contente').value = arrReturn[0][0];	
		}

}