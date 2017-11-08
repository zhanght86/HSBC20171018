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
//	var tSql1 = "select InsuredNo,name,sex,birthday,NativePlace,RelationToMainInsured,RelationToAppnt,OccupationCode,OccupationType from LCInsured t where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and insuredno ='"+InsuredNo+"'"
//							;
//  alert(tSql1);
	   var mySql=new SqlClass();
		 mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
		 mySql.setSqlId("InsuredUWInfoInputSql1");//指定使用的Sql的id
		 mySql.addSubPara(ContNo);
		 mySql.addSubPara(InsuredNo);
		 var tSql1 = mySql.getString();
	turnPage1.strQueryResult  = easyQueryVer3(tSql1, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage1.strQueryResult) {
    alert("被保人信息查询失败!");
    return "";
  }
//  var tSql2 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
//                            + " and ImpartParamName = 'Stature'"
//                            + " and contno='"+ContNo+"'"
//							+ " and customerno ='"+InsuredNo+"'"
//							;
//	alert(tSql2);			
  
   mySql=new SqlClass();
	 mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
	 mySql.setSqlId("InsuredUWInfoInputSql2");//指定使用的Sql的id
	 mySql.addSubPara(ContNo);
	 mySql.addSubPara(InsuredNo);
	 var tSql2 = mySql.getString();
  
  //此处 赋值  什么用途 ？？？ 后面重新赋值，此处作何用？？
//							document.all('Stature').value = tSql2;
// var tSql3 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
//                            + " and ImpartParamName = 'Avoirdupois'"
//                            + " and contno='"+ContNo+"'"
//							+ " and customerno ='"+InsuredNo+"'"
//							;
// alert(tSql);	
 mySql=new SqlClass();
 mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
 mySql.setSqlId("InsuredUWInfoInputSql3");//指定使用的Sql的id
 mySql.addSubPara(ContNo);
 mySql.addSubPara(InsuredNo);
 var tSql3 = mySql.getString();					

  //查询成功则拆分字符串，返回二维数组
  arrResult1 = decodeEasyQueryResult(turnPage1.strQueryResult);	

  document.all('InsuredNo').value = arrResult1[0][0];
  document.all('Name').value = arrResult1[0][1];
  document.all('Sex').value = arrResult1[0][2];
  quickGetName('Sex',fm.Sex,fm.SexName);
  document.all('InsuredAppAge').value = calAge(arrResult1[0][3]);
  document.all('NativePlace').value = arrResult1[0][4];
  quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);
  document.all('TheNativePlace').value = arrResult1[0][4];
  document.all('RelationToMainInsured').value = arrResult1[0][5];
  quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
  document.all('RelationToAppnt').value = arrResult1[0][6];
  quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
  document.all('TheRelationToAppnt').value = arrResult1[0][6];
  document.all('OccupationCode').value = arrResult1[0][7];
  quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
  document.all('OccupationType').value = arrResult1[0][8];
  quickGetName('OccupationType',fm.OccupationType,fm.OccupationTypeName);
  
  turnPage2.strQueryResult  = easyQueryVer3(tSql2, 1, 0, 1);  
  arrResult2 = decodeEasyQueryResult(turnPage2.strQueryResult);	
 
   if (!turnPage2.strQueryResult)
   { 
     
   	 document.all('Stature').value = ' ';
   }
   else
   {
  document.all('Stature').value = arrResult2[0][0];
}

  turnPage3.strQueryResult  = easyQueryVer3(tSql3, 1, 0, 1);  
  arrResult3 = decodeEasyQueryResult(turnPage3.strQueryResult);
  if(!turnPage3.strQueryResult)
  {
  	document.all('Avoirdupois').value = ' ';
}
else
{
  document.all('Avoirdupois').value = arrResult3[0][0];
}

  if(turnPage2.strQueryResult)
  {
  	document.all("BMI").value = arrResult3[0][0]/((arrResult2[0][0]/100)*(arrResult2[0][0]/100));
  }
	else
	{
		document.all("BMI").value = "0";
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
	
  //查询被保人的身高、体重，有则显示
  
//  var strSql = "select case impartparamname"
//         + " when 'stature' then"
//         + " concat(impartparam , 'cm')"
//         + " when 'avoirdupois' then"
//         + " concat(impartparam , 'kg')"
//         + " end"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and customernotype = '1'"
//         + " and impartcode = '000'"
//         + " and impartver = '02'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+ContNo+"'"
//         + " and customerno='"+InsuredNo+"'"
//         + " order by impartparamno ";
  
  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql4");//指定使用的Sql的id
  mySql.addSubPara(ContNo);
  mySql.addSubPara(InsuredNo);
  var strSql = mySql.getString();		
  var arr=easyExecSql(strSql);
	if(arr!=null){
		document.all('Stature').value=arr[0][0];
		document.all('Weight').value=arr[1][0];
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
//  tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and insuredno ='"+InsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode"
//							;
 var mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql5");//指定使用的Sql的id
  mySql.addSubPara(ContNo);
  mySql.addSubPara(InsuredNo);
  tSql = mySql.getString();							
						
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	if(!turnPage.strQueryResult)
	{
	
//		var aSql = " select * from LCInsuredRelated where 1=1 "
//		          + " and polno in (select polno from lcpol where contno = '"+ContNo+"')";
		  mySql=new SqlClass();
		  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
		  mySql.setSqlId("InsuredUWInfoInputSql6");//指定使用的Sql的id
		  mySql.addSubPara(ContNo);
		  var aSql = mySql.getString();		
	
		 turnPage1.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1); 
		 if(!turnPage1.strQueryResult)
		 {
		 
//		 	 tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and standbyflag1 ='"+InsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode"
//							;
			  mySql=new SqlClass();
			  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
			  mySql.setSqlId("InsuredUWInfoInputSql7");//指定使用的Sql的id
			  mySql.addSubPara(ContNo);
			  mySql.addSubPara(InsuredNo);
			  tSql= mySql.getString();					
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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  
  var cContNo = fm.ContNo.value;

  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  
  if (cContNo!= ""  )
  {     
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo+"&Flag=2","window1");
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
  var cPrtNo = fm.PrtNo.value;

  //var tSel = PolAddGrid.getSelNo();
  //if (tSel == 0 || tSel == null)
  //{
  // 	alert("请先选择保单!");  	
	//return;
  //}
  //var cNo = PolAddGrid.getRowColData(tSel - 1, 1);

  
  if (cContNo != "")
  {			
	window.open("./RReportQueryMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo);
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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cInsureNo = fm.InsuredNo.value;
  window.open("../uw/UWAppMain.jsp?ContNo="+ContNo+"&CustomerNo="+cInsureNo+"&type=2");
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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1");
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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ContNo.value;
  alert(cProposalNo);
  var cMissionID =fm.MissionID.value; 
  var cSubMissionID =fm.SubMissionID.value; 
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);	
  if (cProposalNo != ""  && cMissionID != "")
  {
  	window.open("./UWManuRReportMain.jsp?ContNo="+cProposalNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window1");  	
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
//	var tSql = "select t.disresult,t.ICDCode,t.DisDesb,a.RiskGrade,a.ObservedTime,a.State,a.uwresult from LCDiseaseResult t,LDUWAddFee a where 1=1"
//							+ " and t.contno='"+ContNo+"'"
//							+ " and t.customerno = '"+InsuredNo+"'"
//							+ " and t.ICDCode = a.ICDCode order by t.disresult";
	  var mySql=new SqlClass();
	  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
	  mySql.setSqlId("InsuredUWInfoInputSql8");//指定使用的Sql的id
	  mySql.addSubPara(ContNo);
	  mySql.addSubPara(InsuredNo);
	  var tSql= mySql.getString();	
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  

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
		window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo);										

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
 
	window.open("./UWManuAddMain.jsp?ContNo="+ContNo+"&InsuredNo="+cInsuredNo,"window1"); 

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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  if (ContNo != ""&& PrtNo !="" && MissionID != "" )
  { 	
  	window.open("./UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1");  	
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
 		var polno = RiskGrid.getRowColData(tSelNo - 1,1);
 		var mainpolno = RiskGrid.getRowColData(tSelNo - 1,2);
 		
 		//alert(polno+"------------"+mainpolno);
 	   //tongmeng 2007-10-30 modify
 	   //主险也可以撤单	
 //		if(polno==mainpolno)
 //	   {
// 			if(fm.uwstate.value=='a')
// 			 {
 //			 	alert("主险保单不能下撤保结论！");
// 			 	return;
// 			 	}
// 	   }
 	  if(document.all('uwstate').value=='d')
 	   {
 	  	if(document.all('amnt').value==''||document.all('amnt').value==null)
 	  	 {
 	  		alert("请录入限额保额！");
 	  		return;
 	  	 }
 	   }

  	document.all('PolNo').value = RiskGrid.getRowColData(tSelNo - 1,1);	
  	document.all('flag').value = "risk";
	}
	if(FlagStr == 2)
	{
		document.all('flag').value = "Insured";
	}
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 	 
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
	var flag = document.all('flag').value;
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
	document.all('uwstate').value = "";
	document.all('UWIdea').value = "";
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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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

//	var tSql = "select sugpassflag from lcuwmaster where 1=1 "
//						+" and contno ='"+ContNo+"'"
//						;
	  var mySql=new SqlClass();
	  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
	  mySql.setSqlId("InsuredUWInfoInputSql9");//指定使用的Sql的id
	  mySql.addSubPara(ContNo);
	  var tSql= mySql.getString();	
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cInsuredNo = fm.InsuredNo.value;
 
 
	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1");
 
showInfo.close();	
}


//查询生调结果
function queryRReportResult(){
  alert();
}

//保额累计
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}


//已承保查询
function queryProposal(){
	
	window.open("./ProposalQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}


//未承保查询
function queryNotProposal(){
	
	window.open("./NotProposalQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//既往理赔查询
function queryClaim(){
	
	window.open("./ClaimQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//既往保全查询
function queryEdor(){
	
	window.open("./EdorQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}


//查询个人下险种核保结论
function showRiskResult(){
	//alert("here!");
  var arrResult = new Array();
  //alert(RiskGrid.getSelNo());
//  var aSQL = "select passflag,UWIdea from lcuwmaster where ProposalNo='"+RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1)+"'";
  var mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql10");//指定使用的Sql的id
  mySql.addSubPara(RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1));
  var aSQL= mySql.getString();	
  //alert(aSQL);
  arrResult = easyExecSql(aSQL);
	if(arrResult!=null){
		document.all('uwstate').value=arrResult[0][0];
		document.all('UWIdea').value=arrResult[0][1];
		return;
	}
	return;
}

//健康告知查询
function queryHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}  


var cacheWin=null;

function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObjEx(strCode,showObjc,showObjn);
}


//控制人工核保界面按钮的明暗显示
function ctrlButtonDisabled(tContNo){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;
  
  arrButtonAndSQL[0] = new Array;
  arrButtonAndSQL[0][0] = "Button1";
  arrButtonAndSQL[0][1] = "被保人健康告知查询";
//  if(_DBT==_DBO){
//	  arrButtonAndSQL[0][2] = "select * from lccustomerimpart a where a.contno = '"+tContNo+"' and a.impartver = '02' and a.impartcode<>'000' and a.customernotype='1' and a.customerno in (select insuredno from lcinsured where contno = '"+tContNo+"') and rownum=1";
//  } else if(_DBT==_DBM){
//	  arrButtonAndSQL[0][2] = "select * from lccustomerimpart a where a.contno = '"+tContNo+"' and a.impartver = '02' and a.impartcode<>'000' and a.customernotype='1' and a.customerno in (select insuredno from lcinsured where contno = '"+tContNo+"') limit 1";
//  }
  var mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql11");//指定使用的Sql的id
  mySql.addSubPara(tContNo);
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[0][2]= mySql.getString();	
  arrButtonAndSQL[1] = new Array;
  arrButtonAndSQL[1][0] = "Button2";
  arrButtonAndSQL[1][1] = "被保人体检资料查询";
//  if(_DBT==_DBO){
//	  arrButtonAndSQL[1][2] = "select * from lcpenotice where customerno in (select insuredno from lcinsured where contno='"+tContNo+"') and rownum=1";
//  } else if(_DBT==_DBM){
//	  arrButtonAndSQL[1][2] = "select * from lcpenotice where customerno in (select insuredno from lcinsured where contno='"+tContNo+"') limit 1";
//  }
  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql12");//指定使用的Sql的id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[1][2]= mySql.getString();	
  
  arrButtonAndSQL[2] = new Array;
  arrButtonAndSQL[2][0] = "Button3";
  arrButtonAndSQL[2][1] = "被保人保额累计提示信息";
  arrButtonAndSQL[2][2] = "";

  arrButtonAndSQL[3] = new Array;
  arrButtonAndSQL[3][0] = "Button4";
  arrButtonAndSQL[3][1] = "被保人已承保保单查询";
//  arrButtonAndSQL[3][2] = "select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('1', '4') and a.salechnl in ('1', '3', '01', '03') and b.insuredno in (select insuredno from lcinsured where contno = '"+tContNo+"')";

  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql13");//指定使用的Sql的id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[3][2]= mySql.getString();	
  
  arrButtonAndSQL[4] = new Array;
  arrButtonAndSQL[4][0] = "Button5";
  arrButtonAndSQL[4][1] = "被保人未承保投保单查询";
  arrButtonAndSQL[4][2] = "";

  arrButtonAndSQL[5] = new Array;
  arrButtonAndSQL[5][0] = "Button6";
  arrButtonAndSQL[5][1] = "被保人既往保全信息查询";
//  arrButtonAndSQL[5][2] = "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno in (select insuredno from lcinsured where contno='"+tContNo+"'))";

  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql14");//指定使用的Sql的id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[5][2]= mySql.getString();	
  
  arrButtonAndSQL[6] = new Array;
  arrButtonAndSQL[6][0] = "Button7";
  arrButtonAndSQL[6][1] = "被保人既往理赔信息查询";
//  arrButtonAndSQL[6][2] = "select 1 from llregister a, llcase b where a.rgtno = b.caseno and b.CustomerNo in (select insuredno from lcinsured where contno='"+tContNo+"') union select 1 from llreport a, llsubreport b, ldperson c where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' and b.CustomerNo in (select insuredno from lcinsured where contno='"+tContNo+"')";

  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //指定使用的properties文件名
  mySql.setSqlId("InsuredUWInfoInputSql15");//指定使用的Sql的id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[6][2]= mySql.getString();	
  
  arrButtonAndSQL[7] = new Array;
  arrButtonAndSQL[7][0] = "Button8";
  arrButtonAndSQL[7][1] = "特约承保录入";
  arrButtonAndSQL[7][2] = "";

  arrButtonAndSQL[8] = new Array;
  arrButtonAndSQL[8][0] = "AddFee";
  arrButtonAndSQL[8][1] = "加费承保录入";
  arrButtonAndSQL[8][2] = "";


/**
  //影像资料查询
  tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
  arrResult = easyExecSql(tSQL);
  if(arrResult!=null){
    document.all("uwButton4").disabled="";	
  }
  else{
    document.all("uwButton4").disabled="true";	
  }
  
  //问题件信息查询
  tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
  arrResult = easyExecSql(tSQL);
  if(arrResult!=null){
    document.all("uwButton4").disabled="";	
  }
  else{
    document.all("uwButton4").disabled="true";	
  }
  **/
  
  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
    	//alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
      }
    }
    else{
      continue;
    }	
  }
    

  
  	
}


function afterCodeSelect( cCodeName, Field )
{
 
	var tCode = document.all('uwstate').value;
	
	if(tCode=="d")
	{
		divamnt.style.display = '';
		queryamnt();

	}
else
	{
		divamnt.style.display = 'none';
		document.all('amnt').value = "";

	}

}

function queryamnt()
{
	var tSQL = "";
	
	}



