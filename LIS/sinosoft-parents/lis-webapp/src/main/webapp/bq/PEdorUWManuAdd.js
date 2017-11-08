//程序名称：PEdorUWManuAdd.js
//程序功能：保全人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var str2 = "";
var turnPage = new turnPageClass();
//var turnPageReason = new turnPageClass();
//var turnPage2 = new turnPageClass();
//var turnPageReason2 = new turnPageClass();
//var turnPageMission = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var str_sql = "",sql_id = "", my_sql = ""; 
//提交，保存按钮对应操作
function submitForm(tEdorNo,tMissionID)
{
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("未选择加费的保单!");
  	return;
  }
  
  if(fm.SubMissionID.value == "")
  {
  	alert("已录入核保通知书信息,但未打印,不容许录入新的核保通知书加费信息!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }

  var i = SpecGrid.mulLineCount ; 
  if(i==0)
  {
  	alert("未录入新的核保通知书加费信息!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }
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
  fm.action = "./PEdorUWManuAddChk.jsp";
  fm.submit(); //提交
}

//提交，保存按钮对应操作
function submitForm2(tEdorNo,tMissionID)
{
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("未选择加费的保单!");
  	return;
  }
  
  if(fm.SubMissionID.value == "")
  {
  	alert("已录入核保通知书信息,但未打印,不容许录入新的核保通知书加费信息!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }

  var i = Spec2Grid.mulLineCount ; 
  if(i==0)
  {
  	alert("未录入新的核保通知书加费信息!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }
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
  fm.action ="./PEdorUWManuModifyCBAddFeeChk.jsp";
  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="操作成功";  	
  	showInfo.close();
  	alert(showStr);
  	//parent.close();
  	
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
 	else {
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


function manuchkspecmain()
{
	fm.submit();
}

// 查询按钮
function initlist(tPolNo,tEdorNo,tEdorType)
{
	// 书写SQL语句
	k++;
	var strSQL = "";
	//strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
	//	+ " and polno = '"+tPolNo+"'";
//	strSQL = "select dutycode,PEdorFirstPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"'),PEdorEndPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"') from lpduty where "+k+" = "+k
//		   + " and polno = '"+tPolNo+"'"
//		   + " and edortype = '"+tEdorType+"'"
//		   + " and edorno = '"+tEdorNo+"'";
	sql_id = "PEdorUWManuAddSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	my_sql.addSubPara(k);//指定传入的参数
	my_sql.addSubPara(k);//指定传入的参数
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	my_sql.addSubPara(tEdorNo);//指定传入的参数
	strSQL = my_sql.getString();
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
    return str;
}


// 查询按钮
function initlist2(tPolNo,tEdorNo,tEdorType)
{
	// 书写SQL语句
	k++;
	var strSQL = "";
	//strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
		//+ " and polno = '"+tPolNo+"'";
//	strSQL = "select dutycode,PEdorFirstPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"'),PEdorEndPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"') from lpduty where "+k+" = "+k
//		   + " and polno = '"+tPolNo+"'"
//		   + " and edortype = '"+tEdorType+"'"
//		   + " and edorno = '"+tEdorNo+"'";
	sql_id = "PEdorUWManuAddSql2";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	my_sql.addSubPara(k);//指定传入的参数
	my_sql.addSubPara(k);//指定传入的参数
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	my_sql.addSubPara(tEdorNo);//指定传入的参数
	strSQL = my_sql.getString();
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
    return str;
}
//查询已经加费项目
function QueryGrid(tPolNo,tPolNo2,tEdorNo,tEdorType)
{
	// 初始化表格
	//initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
   //获取原加费信息
//	strSQL = "select dutycode,payplantype,paystartdate,payenddate,prem,suppriskscore from LPPrem where 1=1 "
//			 + " and PolNo ='"+tPolNo2+"'"
//			 + " and EdorNO = '" + tEdorNo + "'"
//			 + " and EdorType = '" + tEdorType+ "'"
//			 + " and payplancode like '000000%%'"
//			 + " and state = '2'";			
	sql_id = "PEdorUWManuAddSql3";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPolNo2);//指定传入的参数
	my_sql.addSubPara(tEdorNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = SpecGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
else
  SpecGrid.clearData();  


  //获取工作流子任务号
	var tMissionID = fm.MissionID.value;	
//	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + tEdorNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
//				 + " and LWMission.ActivityID = '0000000002'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";		
	var strsql = "";
	sql_id = "PEdorUWManuAddSql4";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tEdorNo);//指定传入的参数
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tMissionID);//指定传入的参数
	strsql = my_sql.getString();
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    fm.SubMissionID.value = "";
    return ;
  }  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  return true;	
}


//查询已经加费项目
function QueryGrid2(tPolNo,tPolNo2,tEdorNo,tEdorType)
{
	// 初始化表格
	//initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
   //获取原加费信息
//	strSQL = "select dutycode,payplantype,paystartdate,payenddate,prem ,payplancode,suppriskscore  from LPPrem where 1=1 "
//			 + " and PolNo ='"+tPolNo2+"'"
//			 + " and EdorNO = '" + tEdorNo + "'"
//			 + " and EdorType = '" + tEdorType+ "'"
//			 + " and payplancode like '000000%%'"
//			 + " and state <>'2'"
//			 + " order by payplancode";			
	sql_id = "PEdorUWManuAddSql5";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPolNo2);//指定传入的参数
	my_sql.addSubPara(tEdorNo);//指定传入的参数
	my_sql.addSubPara(tEdorType);//指定传入的参数
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = Spec2Grid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
else
{
	Spec2Grid.clearData();  
	alert("该保单无继往承保加费信息");
	return false;
}


  //获取工作流子任务号
	var tMissionID = fm.MissionID.value;	
//	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + tEdorNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
//				 + " and LWMission.ActivityID = '0000000002'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";	
	var strsql = "";
	sql_id = "PEdorUWManuAddSql6";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tEdorNo);//指定传入的参数
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tMissionID);//指定传入的参数
	strsql = my_sql.getString();
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    fm.SubMissionID.value = "";
    return ;
  }  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  return true;	
}

function QueryPolAddGrid(tPrtNo,tEdorNo)
{
	// 初始化表格
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
   //获取原保单信息
//    strSQL = "select LCPol.PolNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
//			 + "  PrtNo ='"+tPrtNo+"'"
//			 + "  order by polno";			
	sql_id = "PEdorUWManuAddSql7";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPrtNo);//指定传入的参数
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolAddGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}
	
function getPolGridCho()
{
  /*var tSelNo = PolAddGrid.getSelNo()-1;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = fm.PolNo.value;
  var cEdorNo = fm.EdorNo.value ; 	
  var cEdorType = fm.EdorType.value ; 	
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" && cEdorType != "")
  {
  	str = initlist(cPolNo2,cEdorNo,cEdorType);
    initUWSpecGrid(str);
    QueryGrid(cPolNo,cPolNo2,cEdorNo,cEdorType);	
    QueryAddReason(cPolNo,cEdorNo);
  }	*/
}

function QueryBQFee()
{
var tSelNo = PolAddGrid.getSelNo()-1;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = fm.PolNo.value;
  var cEdorNo = fm.EdorNo.value ; 	
  var cEdorType = fm.EdorType.value ; 	
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" && cEdorType != "")
  {
  	showDiv(divUWSpec1,"false");
    showDiv(divUWSpec2,"false");
	str2="";
  	str2 = initlist(cPolNo2,cEdorNo,cEdorType);
    initUWSpecGrid(str2);
    if(QueryGrid(cPolNo,cPolNo2,cEdorNo,cEdorType))	
    { 
    QueryAddReason(cPolNo,cEdorNo);
    showDiv(divUWSpec1,"true");
    showDiv(divUWSpec2,"false");
   }
  }

}

function QueryCBFee()
{
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = fm.PolNo.value;
  var cEdorNo = fm.EdorNo.value ; 	
  var cEdorType = fm.EdorType.value ; 	
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" && cEdorType != "")
  {
  	//showDiv(divUWSpec1,"false");
    //showDiv(divUWSpec2,"false");
	str2="";
  	str2 = initlist2(cPolNo2,cEdorNo,cEdorType);
    initUWSpec2Grid(str2);
    if(QueryGrid2(cPolNo,cPolNo2,cEdorNo,cEdorType))	
    { 
    QueryAddReason2(cPolNo,cEdorNo);
    showDiv(divUWSpec2,"true");
    showDiv(divUWSpec1,"false");
   }

  }	
}

//查询已经录入加费特约原因
function QueryAddReason(tPolNo,tEdorNo)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select addpremreason from LPUWMasterMain where 1=1 "
//			 + " and polno = '"+tPolNo+"'"
//			 + " and edorno = '" + tEdorNo +"'";
	sql_id = "PEdorUWManuAddSql8";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPolNo);//指定传入的参数
	my_sql.addSubPara(tEdorNo);//指定传入的参数
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	fm.AddReason.value = "";
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.AddReason.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

//查询已经录入加费特约原因
function QueryAddReason2(tPolNo,tEdorNo)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select addpremreason from LCUWMaster where 1=1 "
//			 + " and polno = '"+tPolNo+"'";
			 //+ " and edorno = '" + tEdorNo +"'";
	sql_id = "PEdorUWManuAddSql9";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tPolNo);//指定传入的参数
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	fm.AddReason.value = "";
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.AddReason2.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

