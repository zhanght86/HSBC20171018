//程序名称：FinFeeSure.js
//程序功能：到帐确认
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
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
  initPolGrid();
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
    
    //showInfo.close(); 
    alert(content); 
  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    alert(content);
    
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();  
    
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
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
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	k++;

	if(document.all("PayMode").value=="4" || document.all("PayMode").value=="A" ||document.all("PayMode").value=="B")
	{
		alert("到帐确认不能选择该种交费方式，请重新输入");
		document.all("PayMode").focus();
		return;
	}
	// 书写SQL语句
	var strSQL = "";
//	strSQL = "select TempFeeNo,ChequeNo,PayMode,PayMoney,AppntName,MakeDate from LJTempFeeClass where "+k+" = "+k+" "
//				 + " and EnterAccDate is null and confmakedate is null  "
//				 + " and ManageCom like '" + fm.comcode.value + "%'"
//				 //团体银行转账不需要作发盘回盘，是保险公司人员去银行查询款是否到账，然后到系统里作到账确认。
//				// + " and paymode <> '4'"
//				//只处理转账支票的到账导入
//				 + " and paymode='3' "
//				 + getWherePart( 'AppntName')
//				 + getWherePart( 'TempFeeNo')
//				 + getWherePart( 'PayMode')
//				 + getWherePart( 'ChequeNo');
//				// + getWherePart( 'ManageCom','ManageCom','like');
//	if(fm.ManageCom.value!="")
//	{
//		strSQL = strSQL + " and ManageCom like '"+fm.ManageCom	.value+"%'";
//	}
//  if (fm.StartDay.value!="")
//  {
//  	 strSQL = strSQL + " and makedate>='"+fm.StartDay.value+"'";
//  }
//  if (fm.EndDay.value!="")
//  {
//  	 strSQL = strSQL + " and makedate<='"+fm.EndDay.value+"'";
//  }	
  
    var  AppntName1 = window.document.getElementsByName(trim("AppntName"))[0].value;
	var  TempFeeNo1 = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
	var  PayMode1 = window.document.getElementsByName(trim("PayMode"))[0].value;
	var  ChequeNo1 = window.document.getElementsByName(trim("ChequeNo"))[0].value;
     var sqlid1="FinFeeSureSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.FinFeeSureSql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(k);//指定传入参数
	 mySql1.addSubPara(k);//指定传入参数
	 mySql1.addSubPara(fm.comcode.value);//指定传入参数
	 mySql1.addSubPara(AppntName1);//指定传入参数
	 mySql1.addSubPara(TempFeeNo1);//指定传入参数
	 mySql1.addSubPara(PayMode1);//指定传入参数
	 mySql1.addSubPara(ChequeNo1);//指定传入参数
	 mySql1.addSubPara(fm.ManageCom.value);//指定传入参数
	 mySql1.addSubPara(fm.StartDay.value);//指定传入参数
	 mySql1.addSubPara(fm.EndDay.value);//指定传入参数
	 strSQL = mySql1.getString();
	 
  
  
 // strSQL=wrapSql1('LJTempFeeClass1',
 // 							[fm.AppntName.value,fm.TempFeeNo.value,fm.PayMode.value,
 // 							 fm.ChequeNo.value,fm.ManageCom.value,fm.StartDay.value,
 // 							 fm.comcode.value,fm.EndDay.value]);
  
  //prompt("",strSQL);			 
	//查询金额合计
 // var strSql = wrapSql1('LJTempFeeClass2',
 // 							[fm.AppntName.value,fm.TempFeeNo.value,fm.PayMode.value,
 // 							 fm.ChequeNo.value,fm.ManageCom.value,fm.StartDay.value,
 // 							 fm.comcode.value,fm.EndDay.value]);
  							 
  						
	var strSql = "";
//	strSql = "select sum(PayMoney) from LJTempFeeClass where "+k+" = "+k+" "
//				 + " and EnterAccDate is null and confmakedate is null  "
//				 + " and ManageCom like '" + fm.comcode.value + "%'"
//				 //团体银行转账不需要作发盘回盘，是保险公司人员去银行查询款是否到账，然后到系统里作到账确认。
//				// + " and paymode <> '4'"
//				//只处理转账支票的到账导入
//				 + " and paymode='3' "
//				 + getWherePart( 'AppntName')
//				 + getWherePart( 'TempFeeNo')
//				 + getWherePart( 'PayMode')
//				 + getWherePart( 'ChequeNo');
//				// + getWherePart( 'ManageCom','ManageCom','like');
//	if(fm.ManageCom.value!="")
//	{
//		strSql = strSql + " and ManageCom like '"+fm.ManageCom	.value+"%'";
//	}
//  if (fm.StartDay.value!="")
//  {
//	  strSql = strSql + " and makedate>='"+fm.StartDay.value+"'";
//  }
//  if (fm.EndDay.value!="")
//  {
//	  strSql = strSql + " and makedate<='"+fm.EndDay.value+"'";
//  }	
  
    var  AppntName2 = window.document.getElementsByName(trim("AppntName"))[0].value;
	var  TempFeeNo2 = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
	var  PayMode2= window.document.getElementsByName(trim("PayMode"))[0].value;
	var  ChequeNo2 = window.document.getElementsByName(trim("ChequeNo"))[0].value;
     var sqlid2="FinFeeSureSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.FinFeeSureSql");
	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
	 mySql2.addSubPara(k);//指定传入参数
	 mySql2.addSubPara(k);//指定传入参数
	 mySql2.addSubPara(fm.comcode.value);//指定传入参数
	 mySql2.addSubPara(AppntName2);//指定传入参数
	 mySql2.addSubPara(TempFeeNo2);//指定传入参数
	 mySql2.addSubPara(PayMode2);//指定传入参数
	 mySql2.addSubPara(ChequeNo2);//指定传入参数
	 mySql2.addSubPara(fm.ManageCom.value);//指定传入参数
	 mySql2.addSubPara(fm.StartDay.value);//指定传入参数
	 mySql2.addSubPara(fm.EndDay.value);//指定传入参数
	 strSql = mySql2.getString();
  
  var arrResult = easyExecSql(strSql);
  //fm.sumMoney.value = arrResult;
  if(arrResult!="null" && arrResult!=null && arrResult!="")
  {
  	fm.sumMoney.value = arrResult;
  }
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有未到帐数据！");
    fm.sumMoney.value = "";
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	 
				 
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  return true;
}

function autochk()
{
  var haveCheck = false;
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getChkNo(i)) {
      haveCheck = true;
      break;
    }
  }
  
  if (haveCheck) {
	  var i = 0;
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	  var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    fm.action="./FinFeeSureChk.jsp";
    fm.submit(); //提交
  }
  else {
    alert("请先选择一条暂交费信息，在选择框中打钩！");
  }
}

function Notchk()
{
  var NotCheck = false;
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getChkNo(i)) {
      NotCheck = true;
      break;
    }
  }
if (NotCheck) {
    var showStr="正在传送数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    fm.action="./FinFeeNoSureChk.jsp";
    //showSubmitFrame(mDebug);
    fm.submit(); //提交
  }
  else {
    alert("请先选择一条暂交费信息，在选择框中打钩！");
  }
}


function insertCurrentDate() {
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getChkNo(i)) {
      PolGrid.setRowColData(i, 7, currentDate);
    }
  }
}


/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql1(sqlId,param){
	var mysql=new SqlClass();
	
	mysql.setResourceName("finfee.FinFeePayInputSql");
	mysql.setSqlId(sqlId);
	
	for(var i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}