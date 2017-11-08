//程序名称：UWAppG.js
//程序功能：既往投保信息查询
//创建日期：2002-06-19 11:10:36
//创建人  ： WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var tPOLNO = "";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var cflag = "1" //操作位置　1.以往投保信息查询

//提交，保存按钮对应操作
function submitForm()
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

  //showSubmitFrame(mDebug);
  fm.submit(); //提交
  alert("submit");
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
  }
  else
  { 
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

//既往投保信息
/*function showApp()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  cProposalNo=fm.ProposalNoHide.value;
  cInsureNo = fm.InsuredNoHide.value;
  
  if (cProposalNo != "")
  {
  	//showModalDialog("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	window.open("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window2");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");  	
  }
}                   
*/
//以往核保记录
/*function showOldUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  var tSelNo = PolGrid.getSelNo();
  var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	

  //cProposalNo=fm.ProposalNoHide.value;
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window2");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");  	
  }
}

//当前核保记录
function showNewUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  var tSelNo = PolGrid.getSelNo();
  var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	
  		
  //cProposalNo=fm.ProposalNoHide.value;
  if (cProposalNo != "")
  {
  	window.open("./UWErrMain.jsp?ProposalNo1="+cProposalNo,"window2");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");  
  }
}                      
       

//保单明细信息
function showPolDetail()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  var cPolNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
 
  if (cPolNo != "")
  {
  	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	mSwitch.updateVar("PolNo", "", cPolNo);
		
	var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 3);
  	window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+prtNo);
  	//showInfo.close();
  
  }
  else
  {  	
  	alert("请先选择保单!");	
  }

}

//体检资料查询
function showHealthQ()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  var tSelNo = PolGrid.getSelNo();
  var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	

  //cProposalNo=fm.ProposalNoHide.value;
  if (cProposalNo != "")
  {
  	window.open("./UWManuHealthQMain.jsp?ProposalNo1="+cProposalNo,"window2");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}           

//核保报告书
function showReport()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  var tSelNo = PolGrid.getSelNo();
  var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	

  //cProposalNo=fm.ProposalNoHide.value;
  if (cProposalNo != "")
  {
  	//window.open("./UWManuReportMain.jsp?ProposalNo1="+cProposalNo+"&flag="+cflag,"window2");  	
  	window.open("UWQueryOldSubReportMain.jsp?PolNo="+cProposalNo,"window2");
 	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}                    
*/    

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




//选择要人工核保保单
function queryUWSub()
{
	//showSubmitFrame(mDebug);
	fm.submit();
}

// 查询按钮
function easyQueryClick()
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;
	var proposalno = fm.all('ProposalNoHide').value;
	var insureno = fm.all('InsureNoHide').value;
	if (proposalno == null || proposalno == "")
	{
		strSQL = "select LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case substr(LCPol.polstate,1,2) when '' then '99' end,LCPol.OccupationType,LCUWMaster.healthflag,LCUWMaster.specflag,LCUWMaster.passflag ,LCPol.ClaimTimes from LCPol,LCUWMaster,LMRisk where 1=1 "
				 + " and LCPol.insuredno ='"+insureno+"'"
				 + " and LCPol.polno <> '"+proposalno+"'"
				 + " and LCPol.polno = LCUWMaster.polno"
				 + " and LMRisk.riskcode = lcpol.riskcode"
				 + " and LCPol.uwcode is not null"
				 + " union"
				 + " select LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case substr(LCPol.polstate,1,2) when '' then '99' end,LCPol.OccupationType,'0','0','0',LCPol.ClaimTimes from LCPol,LMRisk where 1=1 "
				 + " and LCPol.insuredno ='"+insureno+"'"
				 + " and LCPol.polno <> '"+proposalno+"'"
				 + " and LCPol.uwcode is null"
				 + " and LMRisk.riskcode = lcpol.riskcode" 
				 + " order by 1";				 	 
	}
	else
	{
		var cContNo = fm.all("ContNo").value;
		strSQL = "select LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case substr(LCPol.polstate,1,2) when '' then '99' end,LCPol.OccupationType,LCUWMaster.healthflag,LCUWMaster.specflag,LCUWMaster.passflag ,LCPol.ClaimTimes from LCPol,LCUWMaster,LMRisk where 1=1 "
				 + " and LCPol.insuredno ='"+insureno+"'"
				 + " and LCPol.contno <> '"+cContNo+"'"
				 + " and LCPol.polno = LCUWMaster.polno"
				 + " and LMRisk.riskcode = lcpol.riskcode"
				 + " and LCPol.uwcode is not null"
				 + " union"
				 + " select LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case substr(LCPol.polstate,1,2) when '' then '99' end,LCPol.OccupationType,'0','0','0',LCPol.ClaimTimes from LCPol,LMRisk where 1=1 "
				 + " and LCPol.insuredno ='"+insureno+"'"
				 + " and LCPol.contno <> '"+cContNo+"'"
				 + " and LCPol.uwcode is null"
				 + " and LMRisk.riskcode = lcpol.riskcode" 
				 + " order by 1";				 	 
	}
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("无既往投保信息！");
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
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;

	
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	alert(1);
	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		alert("result:"+arrResult);
	} // end of if
}

function ChoClick(parm1,parm2)
{
	if(fm.all(parm1).all('InpPolGridSel').value == '1' )
	{
	//当前行第1列的值设为：选中
   		fm.ProposalNoHide.value = fm.all(parm1).all('PolGrid2').value;
  	}

}



