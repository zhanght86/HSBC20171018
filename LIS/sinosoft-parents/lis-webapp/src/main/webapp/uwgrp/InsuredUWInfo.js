/*********************************************************************
 *  程序名称：InsuredUWInfo.js
 *  程序功能：人工核保被保人信息页面函数
 *  创建日期：2005-01-06 11:10:36
 *  创建人  ：HYQ
 *  返回值：  无
 *  更新记录：  更新人    更新日期     更新原因/内容
 *********************************************************************
 */

var arrResult1 = new Array();
var arrResult2 = new Array();
var arrResult3 = new Array();
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var temp = new Array();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.InsuredUWInfoSql";
/*********************************************************************
 *  查询核保被保人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryInsuredInfo()
{
	
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();
	var arrReturn3 = new Array();
	/*
	var tSql1 = "select InsuredNo,name,sex,birthday,NativePlace,RelationToMainInsured,RelationToAppnt,OccupationCode,OccupationType from LCInsured t where 1=1"
							+ " and contno='"+ContNo+"'"
							+ " and insuredno ='"+InsuredNo+"'"
							;
							
							
	*/						
		var sqlid1="InsuredUWInfoSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ContNo);
			mySql1.addSubPara(InsuredNo);
//  alert(tSql1);
	turnPage1.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage1.strQueryResult) {
    alert("被保人信息查询失败!");
    return "";
  }
  /*
  var tSql2 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
                            + " and ImpartParamName = 'Stature'"
                            + " and contno='"+ContNo+"'"
							+ " and customerno ='"+InsuredNo+"'"
							;
		*/					
		var sqlid2="InsuredUWInfoSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(ContNo);
		mySql2.addSubPara(InsuredNo);
		var tSql2 =	mySql2.getString();			
//	alert(tSql2);					
							fm.all('Stature').value = tSql2;
 /*
 var tSql3 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
                            + " and ImpartParamName = 'Avoirdupois'"
                            + " and contno='"+ContNo+"'"
							+ " and customerno ='"+InsuredNo+"'"
							;
	*/						
		var sqlid3="InsuredUWInfoSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(ContNo);
		mySql3.addSubPara(InsuredNo);
		var tSql3 =	mySql3.getString();	
// alert(tSql);	
							

  //查询成功则拆分字符串，返回二维数组
  arrResult1 = decodeEasyQueryResult(turnPage1.strQueryResult);	

  fm.all('InsuredNo').value = arrResult1[0][0];
  fm.all('Name').value = arrResult1[0][1];
  fm.all('Sex').value = arrResult1[0][2];
  fm.all('InsuredAppAge').value = calAge(arrResult1[0][3]);
  fm.all('NativePlace').value = arrResult1[0][4];
  fm.all('TheNativePlace').value = arrResult1[0][4];
  fm.all('RelationToMainInsured').value = arrResult1[0][5];
  fm.all('RelationToAppnt').value = arrResult1[0][6];
  fm.all('TheRelationToAppnt').value = arrResult1[0][6];
  fm.all('OccupationCode').value = arrResult1[0][7];
  fm.all('OccupationType').value = arrResult1[0][8];
  
  turnPage2.strQueryResult  = easyQueryVer3(tSql2, 1, 0, 1);  
  arrResult2 = decodeEasyQueryResult(turnPage2.strQueryResult);	
 
   if (!turnPage2.strQueryResult)
   { 
     
   	 fm.all('Stature').value = ' ';
   }
   else
   {
  fm.all('Stature').value = arrResult2[0][0];
}

  turnPage3.strQueryResult  = easyQueryVer3(tSql3, 1, 0, 1);  
  arrResult3 = decodeEasyQueryResult(turnPage3.strQueryResult);
  if(!turnPage3.strQueryResult)
  {
  	fm.all('Avoirdupois').value = ' ';
}
else
{
  fm.all('Avoirdupois').value = arrResult3[0][0];
}

  if(turnPage2.strQueryResult)
  {
  	fm.all("BMI").value = arrResult3[0][0]/((arrResult2[0][0]/100)*(arrResult2[0][0]/100));
  }
	else
	{
		fm.all("BMI").value = "0";
	}
	
	
  if(arrResult1[0][5]=="00")
  {  	    
  	 MainInsured.style.display = "";
  	 MainInsuredInput.style.display = "";
  	 
  }
  else
	{
	
  	 MainAppnt.style.display = "";
  	 MainAppntInput.style.display = "";
	}
	
  return true;
 
}

/*********************************************************************
 *  查询核保被保人险种信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryRiskInfo()
{
	var tSql = "";
	/*
  tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
							+ " and contno='"+ContNo+"'"
							+ " and insuredno ='"+InsuredNo+"'"
							+ " and lcpol.riskcode = lmrisk.riskcode"
							;
							*/
		var sqlid4="InsuredUWInfoSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(ContNo);
		mySql4.addSubPara(InsuredNo);					
						
	turnPage.strQueryResult  = easyQueryVer3(mySql4.getString(), 1, 0, 1);  
	if(!turnPage.strQueryResult)
	{
	/*
		var aSql = " select * from LCInsuredRelated where 1=1 "
		          + " and polno in (select polno from lcpol where contno = '"+ContNo+"')";
	*/	
	
	var sqlid5="InsuredUWInfoSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(ContNo);

	
		 turnPage1.strQueryResult  = easyQueryVer3(mySql5.getString(), 1, 0, 1); 
		 if(!turnPage1.strQueryResult)
		 {
		 /*
		 	 tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
							+ " and contno='"+ContNo+"'"
							+ " and standbyflag1 ='"+InsuredNo+"'"
							+ " and lcpol.riskcode = lmrisk.riskcode"
							;
							*/
			var sqlid6="InsuredUWInfoSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(ContNo);	
		mySql6.addSubPara(InsuredNo);	
		tSql =mySql6.getString();
		}
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);      
	}
	

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("被保人信息查询失败!");
    return "";
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = RiskGrid ;    

  //保存SQL语句
  turnPage.strQuerySql     = tSql ; 

  //设置查询起始位置
  turnPage.pageIndex       = 0;  

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  if(arrDataSet.length!=0)
  {
	  for(i=0;i<arrDataSet.length;i++)
	  {
	  	 temp[i] = arrDataSet[i][2];
	  }
  }
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;  					
}

/*********************************************************************
 *  体检资料查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showHealthQ()
{

  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

  
  var cContNo = fm.ContNo.value;

  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  
  if (cContNo!= ""  )
  {     
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  	
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}       

/*********************************************************************
 *  生存调查报告查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function RReportQuery()
{
  cContNo = fm.ContNo.value;  //保单号码

  //var tSel = PolAddGrid.getSelNo();
  //if (tSel == 0 || tSel == null)
  //{
  // 	alert("请先选择保单!");  	
	//return;
  //}
  //var cNo = PolAddGrid.getRowColData(tSel - 1, 1);

  
  if (cContNo != "")
  {			
	window.open("./RReportQueryMain.jsp?ContNo="+cContNo,"",sFeatures);
  }
  else
  {  	
  	alert("请先选择保单!");  	
  }	
}

/*********************************************************************
 *  既往投保信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showApp(cindex)
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

  var cInsureNo = fm.InsuredNo.value;
  window.open("../uwgrp/UWAppMain.jsp?ContNo="+ContNo+"&CustomerNo="+cInsureNo+"&type=2","",sFeatures);
  	showInfo.close();
}         

/*********************************************************************
 *  体检资料录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showHealth()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}           

/*********************************************************************
 *  生存调查报告
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showRReport()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

  cProposalNo=fm.ContNo.value;
  alert(cProposalNo);
  var cMissionID =fm.MissionID.value; 
  var cSubMissionID =fm.SubMissionID.value; 
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);	
  if (cProposalNo != ""  && cMissionID != "")
  {
  	window.open("./UWManuRReportMain.jsp?ContNo="+cProposalNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window1",sFeatures);  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}         
   
/*********************************************************************
 *  显示已经录入的疾病信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showDisDesb()
{
	for(i=0;i<temp.length;i++)
	{
	/*
	var tSql = "select t.disresult,t.ICDCode,t.DisDesb,a.RiskGrade,a.ObservedTime,a.State,a.uwresult from LCDiseaseResult t,LDUWAddFee a where 1=1"
							+ " and t.contno='"+ContNo+"'"
							+ " and t.customerno = '"+InsuredNo+"'"
							+ " and t.ICDCode = a.ICDCode order by t.disresult";
*/
var sqlid7="InsuredUWInfoSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(ContNo);	
		mySql7.addSubPara(InsuredNo);

	turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  if(i==0)
  {
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  }
 else
 	 {
 	 	 
 		 l = turnPage.arrDataCacheSet[0].length;
 		 for(j=0;j<decodeEasyQueryResult(turnPage.strQueryResult).length;j++)
 		 {  
 		 	  turnPage.arrDataCacheSet[j][l] = decodeEasyQueryResult(turnPage.strQueryResult)[j][6];
 		 }
 	 }
  }
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = DiseaseGrid ;    

  //保存SQL语句
  turnPage.strQuerySql     = tSql ; 

  //设置查询起始位置
  turnPage.pageIndex       = 0;  

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;  			
}

/*********************************************************************
 *  理赔给付查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function ClaimGetQuery()
{
	  var cInsuredNo = fm.InsuredNo.value;				
		if (cInsuredNo == "")
		    return;
		window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo,"",sFeatures);										

}

/*********************************************************************
 *  加费承保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function showAdd()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var cInsuredNo = fm.InsuredNo.value;
 
	window.open("./UWManuAddMain.jsp?ContNo="+ContNo+"&InsuredNo="+cInsuredNo,"window1",sFeatures); 

}

/*********************************************************************
 *  特约承保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function showSpec()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

  if (ContNo != ""&& PrtNo !="" && MissionID != "" )
  { 	
  	window.open("./UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1",sFeatures);  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("数据传输错误!");
  }
}

/*********************************************************************
 *  返回
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function InitClick(){
	window.close();
}

/*********************************************************************
 *  提交，保存按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function submitForm(FlagStr)
{
	if(FlagStr == 1)
	{
		var tSelNo = RiskGrid.getSelNo();
 		if(tSelNo<=0)
 		{
 			alert("请选择险种保单！");
 			return;
 		}
  	fm.all('PolNo').value = RiskGrid.getRowColData(tSelNo - 1,1);	
  	fm.all('flag').value = "risk";
	}
	if(FlagStr == 2)
	{
		fm.all('flag').value = "Insured";
	}
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
 	 
  fm.action = "./InsuredUWInfoChk.jsp";
  fm.submit(); //提交
}

/*********************************************************************
 *  提交后操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function afterSubmit( FlagStr, content )
{
	var flag = fm.all('flag').value;
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
  	if(flag=="Insured")
    {
  		parent.close();
  	}
    else
  	{
  		showInsuredResult();
  	}
    //执行下一步操作
  }
}

/*********************************************************************
 *  取消
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cancelchk()
{
	fm.all('uwstate').value = "";
	fm.all('UWIdea').value = "";
}

/*********************************************************************
 *  返回
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function InitClick(){
	parent.close();
}

/*********************************************************************
 *  暂停被保人，向后台提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function StopInsured()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

	fm.flag.value = "StopInsured";
	
	fm.action = "./InsuredUWInfoChk.jsp";
  fm.submit(); //提交
}


/*********************************************************************
 *  初始化上报
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initSendTrace()
{
	if(SendFlag =="1")
	{
		//divUWButton1.style.display = "none";
//		divUWButton2.style.display = "none";
	}
}

/*********************************************************************
 *  计算核保结论
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function calRiskResult(arrSelected)
{
	var tResult="S";
	var arrSelected =new Array();
	var dFlag = 0 ;
	var pFlag = 0 ;
	var lFlag = 0 ;
	var eFlag = 0 ;
	var rFlag = 0 ;

	for(i=0;i<arrSelected.length;i++)
	{
		if(arrSelected[i]=="D")
		{
			dFlag = 1;
		}
		if(arrSelected[i]=="P")
		{
			pFlag = 1;
		}
		if(arrSelected[i]=="R")
		{
			rFlag = 1;
		}		
		if(arrSelected[i]=="E")
		{
			eFlag = 1;
		}			
		if(arrSelected[i]=="L")
		{
			lFlag = 1;
		}	
	}
	if(dFlag == 1)
	{
		tResult="D";
	}
  else
  {
  	if(pFlag == 1)
  	{
  		tResult="P";
  	}
  	else
  	{
  		if(rFlag == 1)
  		{
  			tResult="R";
  		}
  		else
  		{
  			  	if(eFlag == 1)
  					{
  						tResult="E";
  					}
  					else
  					{
  						  	if(lFlag == 1)
  								{
  									tResult="L";
  								}
  								else
  								{
  									tResult="S";
  								}
  					} 
  		}  		  
  	}         
  }
  	
  return tResult;     
}             
              
/*********************************************************************
 *  得出被保人建议结论
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */              
function showInsuredResult()
{
	var arrSelected = new Array();
	var arrResult = new Array();
/*
	strSQL = "select sugpassflag from lcuwmaster where 1=1 "
						+" and contno ='"+ContNo+"'"
						;
						*/
		var sqlid8="InsuredUWInfoSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(ContNo);	

						
	turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);  
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);

	//判断是否查询成功
  if (turnPage.strQueryResult) { 
		for(j=0;j<arrResult.length;j++)
		{
			arrSelected[j] = arrResult[j][0];
		}
		//fm.SugIndUWFlag.value = calRiskResult(arrSelected);
	}	
}        

//查询体检结果
function queryHealthReportResult(){
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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

  var cContNo=fm.ContNo.value;
  var cInsuredNo = fm.InsuredNo.value;
 
 
	window.open("../uwgrp/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1","",sFeatures);
 
showInfo.close();	
}


//查询生调结果
function queryRReportResult(){
  alert();
}

//保额累计
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}


//已承保查询
function queryProposal(){
	
	window.open("./ProposalQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}


//未承保查询
function queryNotProposal(){
	
	window.open("./NotProposalQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}

//既往理赔查询
function queryClaim(){
	
	window.open("./ClaimQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}

//既往保全查询
function queryEdor(){
	
	window.open("./EdorQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}


//查询个人下险种核保结论
function showRiskResult(){
	//alert("here!");
  var arrResult = new Array();
  //alert(RiskGrid.getSelNo());
 // var aSQL = "select passflag,UWIdea from lcuwmaster where ProposalNo='"+RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1)+"'";
 
 var sqlid9="InsuredUWInfoSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1));	
 
  //alert(aSQL);
  arrResult = easyExecSql(mySql9.getString());
	if(arrResult!=null){
		fm.all('uwstate').value=arrResult[0][0];
		fm.all('UWIdea').value=arrResult[0][1];
		return;
	}
	return;
}

//健康告知查询
function queryHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}  