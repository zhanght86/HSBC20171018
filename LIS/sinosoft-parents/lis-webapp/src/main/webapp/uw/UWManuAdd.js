//程序名称：PEdorUWManuAdd.js
//程序功能：保全人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件               

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var spanObj;


//提交，保存按钮对应操作
function submitForm()
{
  //alert("FFFFFFFFFFFFFFFf");
  var i = 0;
  var cPolNo= fm.PolNo2.value ;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("未选择加费的投保单!");
  	return;
  }

  if(fm.SubMissionID.value == "")
  {
  	alert("已录入核保通知书信息,但未打印,不容许录入新的核保通知书加费信息!");
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
  	return;
  }

  var i = SpecGrid.mulLineCount ;
  if(i==0)
  {
  	alert("未录入新的核保通知书加费信息!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
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
  fm.action= "./UWManuAddChk.jsp";
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
 
	showInfo.close();
  if (FlagStr == "Fail" )
  {
  	
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
    alert(content);

    //parent.close();
  }
  else
  {
	  var showStr="操作成功";
  	showInfo.close();
  	//alert(showStr);
  	//parent.close();
    //执行下一步操作
  }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
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
  initForm('',tContNo12, '',tsubmissionId12, tInsuredNo12);
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
function initlist(tPolNo)
{
	// 书写SQL语句
	k++;
	var strSQL = "";
	
	    var sqlid1="UWManuAddSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(k);//指定传入的参数
		mySql1.addSubPara(k);//指定传入的参数
		mySql1.addSubPara(tPolNo);//指定传入的参数
	    strSQL=mySql1.getString();	
	
//	strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
//		+ " and polno = '"+tPolNo+"'";
    str  = easyQueryVer3(strSQL, 1, 0, 1);
    return str;

}

//查询已经加费项目
function QueryGrid(tPolNo,tPolNo2)
{
	// 初始化表格
	//initPolGrid();

	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;
   //获取原加费信息
   
	    var sqlid2="UWManuAddSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(tPolNo2);//指定传入的参数
	    strSQL=mySql2.getString();	
   
//	strSQL = "select dutycode,payplantype,paystartdate,payenddate,suppriskscore,SecInsuAddPoint,AddFeeDirect,prem from LCPrem where 1=1 "
//			 + " and PolNo ='"+tPolNo2+"'"
//			 + " and payplancode like '000000%%'";
			// + " and state = '1'";

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
  var arrDataSet  = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


  //获取工作流子任务号
/*	var tMissionID = fm.MissionID.value;
	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
				 + " and LWMission.ActivityID = '0000000102'"
				 + " and LWMission.ProcessID = '0000000001'"
				 + " and LWMission.MissionID = '" +tMissionID +"'";
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    fm.SubMissionID.value = "";
    return ;
  }  */
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];

  return true;
}

function QueryPolAddGrid(tContNo,tInsuredNo)
{
	// 初始化表格
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;

   //获取原保单信息
   
   	    var sqlid3="UWManuAddSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(tContNo);//指定传入的参数
	    strSQL=mySql3.getString();	
   
//    strSQL = "select LCPol.PolNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName,LCPol.standprem from LCPol where "
//			 + "  ContNo =(select contno from lccont where prtno = '"+tContNo+"')"
//			 + "  order by polno";

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
  var tSelNo = PolAddGrid.getSelNo()-1;
  tRow = tSelNo;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = PolAddGrid.getRowColData(tSelNo,1);
  //alert(cPolNo);
  var tRiskCode = PolAddGrid.getRowColData(tSelNo,4);
  fm.PolNo.value = cPolNo;  
  fm.PolNo2.value = cPolNo2 ;
  fm.RiskCode.value = tRiskCode;

  if(cPolNo != null && cPolNo != "" )
  {
  	str = initlist(cPolNo2);
    initUWSpecGrid(str);
    QueryGrid(cPolNo,cPolNo2);
    QueryAddReason(cPolNo);
    document.all('spanSpecGrid').all('SpecGridSel').checked="true";

  }
}


//查询已经录入加费特约原因
function QueryAddReason(tPolNo)
{

	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;
	
		    var sqlid4="UWManuAddSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(tPolNo);//指定传入的参数
	    strSQL=mySql4.getString();	
	
//	strSQL = "select addpremreason from LCUWMaster where 1=1 "
//			 + " and polno = '"+tPolNo+"'"

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

//初始化加费对象
function initAddObj(span)
{
	spanObj = span;
	var tSelNo = PolAddGrid.getSelNo()-1;
  var tRiskCode = PolAddGrid.getRowColData(tSelNo,4);
	var tDutyCode = document.all( spanObj ).all( 'SpecGrid1' ).value;//加费类型
	var tAddFeeType = document.all(spanObj).all('SpecGrid2').value;//加费原因

  if(tAddFeeType = '01')
  {
  	
		var sqlid5="UWManuAddSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(tRiskCode);//指定传入的参数
		mySql5.addSubPara(tDutyCode);//指定传入的参数
	    var srtSql=mySql5.getString();	
	
//	var srtSql = "select AddFeeObject from LMDutyPayAddFee where 1=1 "
//	           + " and riskcode = '"+tRiskCode+"'"
//	           + " and DutyCode = '"+tDutyCode+"'"
//	           + " and AddFeeType = '01'";

	turnPage.strQueryResult  = easyQueryVer3(srtSql, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  document.all( spanObj ).all( 'SpecGrid1' ).value = turnPage.arrDataCacheSet[0][0];

}
else
	{
		document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
	}
    return true;

}



/*********************************************************************
 *  Click事件，当双击“加费金额”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showUWSpec( span)
{
	spanObj = span;

	// 书写SQL语句
	var strSQL = "";
	var tRiskCode="";
	var tInsuredSex="";
	var tInsuredAppAge="";
	var tSuppRiskCore="";
	var tAddFeeKind="";
	var tPayEndYear="";
    //校验录入信息的完整性
	if(document.all( spanObj ).all( 'SpecGrid1' ).value == ""){
		alert("请录入加费类型信息！");
		return;
	}

	if(document.all( spanObj ).all( 'SpecGrid2' ).value == ""){
		alert("请录入加费原因信息！");
		return;
	}
	else
	tAddFeeKind=document.all( spanObj ).all( 'SpecGrid2' ).value;

 	if(document.all( spanObj ).all( 'SpecGrid5' ).value==""){
		alert("请录入加费评点信息！");
		return;
	}
	else
	tSuppRiskCore=document.all( spanObj ).all( 'SpecGrid5' ).value;


//此处对职业加费处理还没有确定。
 alert("tAddFeeKind:"+tAddFeeKind)
   //alert("riskcode:"+riskcode)
  // alert("polno:"+polno)
   alert("tSuppRiskCore:"+tSuppRiskCore)
   if(tAddFeeKind=="1"||tAddFeeKind=="3")
   {

   	//准备加费评点要素
   //alert(PolAddGrid.getRowColData(tRow,1));

		var sqlid6="UWManuAddSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tAddFeeKind);//指定传入的参数
		mySql6.addSubPara(tSuppRiskCore);//指定传入的参数
		mySql6.addSubPara(PolAddGrid.getRowColData(tRow,1));//指定传入的参数
	    strSQL=mySql6.getString();	

//	strSQL = "select AddFeeAMNT("+tAddFeeKind+",riskcode,polno,"+tSuppRiskCore+") from LCpol where polno='"+PolAddGrid.getRowColData(tRow,1)+"'";

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult)
    {
  	alert("保单加费评点计算失败！");
    return "";
    }

    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    document.all( spanObj ).all( 'SpecGrid6' ).value = turnPage.arrDataCacheSet[0][0];
    return true;
   }
   else
  	{
    alert("未定义职业加费评点！");
    return "";
    }

}

function CalHealthAddFee(span)
{
	spanObj = span;

  var tSelNo = PolAddGrid.getSelNo()-1;
  var tRiskCode = PolAddGrid.getRowColData(tSelNo,4);
  var tPolNo = PolAddGrid.getRowColData(tSelNo,1);
  var tMainPolNo = PolAddGrid.getRowColData(tSelNo,2);
  //var tPrem = PolAddGrid.getRowColData(tSelNo,8);
  //查询该责任下的保准保费
  
  		var sqlid7="UWManuAddSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(tPolNo);//指定传入的参数
		mySql7.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);//指定传入的参数
		mySql7.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);//指定传入的参数
	    var tSql=mySql7.getString();	
  
//  var tSql = " select sum(standprem) from lcprem where 1 = 1 "
//           + " and polno = '"+tPolNo+"' and dutycode='"+document.all( spanObj ).all( 'SpecGrid1' ).value+"' "
//           + " and payplancode in (select payplancode from lmdutypayrela where dutycode='"+document.all( spanObj ).all( 'SpecGrid1' ).value+"')";

  turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);

  if (!turnPage.strQueryResult)
   {
    alert("查询该责任下的保准保费金额失败")
    return ;
    }

  else
  	{
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    var tPrem = turnPage.arrDataCacheSet[0][0];
  	}

  var tSuppRiskScore = document.all( spanObj ).all( 'SpecGrid5' ).value;
  //如果该险种是附加险，判断该附加险又没有加费算法。
  
    		var sqlid8="UWManuAddSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(tRiskCode);//指定传入的参数
		mySql8.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);//指定传入的参数
	    var tSql=mySql8.getString();	
  
//  var tSql = " select * from LMDutyPayAddFee where 1=1 "
//           + " and riskcode = '"+tRiskCode+"'"
//           + " and dutycode = '"+document.all( spanObj ).all( 'SpecGrid1' ).value+"'";

 turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);

//  alert(tRiskCode);
  if((tPolNo != tMainPolNo || tRiskCode=="00325000") && !(turnPage.strQueryResult))
  {

    		var sqlid9="UWManuAddSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("uw.UWManuAddSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(tOperator);//指定传入的参数
	    var tSql=mySql9.getString();	


//  	var tSql = " select addpoint from lduwuser where 1 = 1 "
//  	         + " and usercode='"+tOperator+"' and uwtype='1'";
   turnPage.strQueryResult = easyQueryVer3(tSql, 1, 1, 1);
  //判断是否查询成功

  if (!turnPage.strQueryResult)
   {
    alert("查询附加险加费评点权限有问题")
    return ;
    }
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    var RiskScore = turnPage.arrDataCacheSet[0][0];

    if(parseFloat(tSuppRiskScore) > parseFloat(RiskScore) )
    {
    	alert("加费评点过大，超过该核保是的核保权限")
    	return;
    }
  else
  	{

  	var tAddPrem = tSuppRiskScore/100*tPrem;
  	document.all( spanObj ).all( 'SpecGrid8' ).value = tAddPrem;
    }

  }

else
	{
	document.all('DutyCode').value = document.all( spanObj ).all( 'SpecGrid1' ).value;
	document.all('AddFeeType').value = document.all( spanObj ).all( 'SpecGrid2' ).value;
  document.all('SuppRiskScore').value = document.all( spanObj ).all( 'SpecGrid5' ).value;
	document.all('SecondScore').value = document.all( spanObj ).all( 'SpecGrid6' ).value;
	document.all('AddFeeObject').value = document.all( spanObj ).all( 'SpecGrid7' ).value;

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

  fm.action= "./UWCalHealthAddFeeChk.jsp";
  fm.submit(); //提交
}
}

//created by guanwei
function deleteData()
{ 
	var tSelNo = SpecGrid.getSelNo()-1;
	
  var tDutycode = SpecGrid.getRowColData(tSelNo,1);
  document.all('DutyCode').value = tDutycode;
  document.all('AddFeeType').value = SpecGrid.getRowColData(tSelNo,2);
  if(tDutycode =='')
  {
  	alert("缺乏相关数据，请确认");
  	return false;
  }
  
	//initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
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
    fm.action = "./UWManuAddDelSave.jsp";
    fm.submit();
  
  //var cPolNo= fm.PolNo2.value ;   
  //alert(cPolNo);
  
}
