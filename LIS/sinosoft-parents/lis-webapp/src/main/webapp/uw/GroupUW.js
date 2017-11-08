//程序名称：GroupUW.js
//程序功能：集体人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容


/*********************************************************************
 *  //该文件中包含客户端需要处理的函数和事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var mSwitch = parent.VD.gVSwitch;
var isClosed = false;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


/*********************************************************************
 *  提交对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
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
  //showSubmitFrame(mDebug);
  fmQuery.submit(); //提交
  alert("submit");
}



/*********************************************************************
 *  提交后操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
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
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        //执行下一步操作
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        
        fmQuery.all('GrpContNo').value = GrpContNo;
        	
        initGrpGrid();    
    	initGrpPolFeeGrid();    
        querygrp();
        makeWorkFlow();
        if(isClosed == true)
        {
            top.close();
        }
    }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}



/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
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


//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}


/*********************************************************************
 *  查询团体单下个人单既往投保信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showApp()
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

  var cProposalNo=fmQuery.ProposalNo.value;
  var cInsureNo = fmQuery.InsuredNo.value;
  if(cProposalNo==""||cProposalNo==null|| cInsureNo==""||cInsureNo==null)
  {
  	showInfo.close();
  	alert("请选择个人保单,后查看其信息!");
  	return ;
  	}
  //showModalDialog("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  window.open("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window1",sFeatures);
  showInfo.close();
}


/*********************************************************************
 *  查询团体单下个人单以往核保记录
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showOldUWSub()
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

  var cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==""||cProposalNo==null)
  {
  	showInfo.close();
  	alert("请选择个人保单,后查看其信息!");
  	return ;
  	}
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  showInfo.close();
}


/*********************************************************************
 *  查询团体单下个人单核保记录
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showNewUWSub()
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

  var cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==""||cProposalNo==null)
  {
  	showInfo.close();
  	alert("请先选择个人保单,后查看其信息!");
  	return ;
  	}
  window.open("./UWErrMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  showInfo.close();
}


/*********************************************************************
 *  查询团体单核保记录
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showGNewUWSub()
{
  var cGrpContNo=fmQuery.GrpContNo.value;
  if(cGrpContNo==""||cGrpContNo==null)
  {
  	alert("请先选择一个团体投保单!");
  	return ;
  	}
  window.open("./UWGErrMain.jsp?GrpContNo="+cGrpContNo,"window1",sFeatures);
}



/*********************************************************************
 *  查询团体下的附属于各团单的个人保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showPolDetail()
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

  cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==null||cProposalNo=="")
  {
  	showInfo.close();
  	alert("请先选择个人保单,后查看其信息!");

  }
  else{
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );
  window.open("../app/ProposalMain.jsp?LoadFlag=4","",sFeatures);
  showInfo.close();}
}


/*********************************************************************
 *  查询团体下个人保单体检资料
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

  var cProposalNo=fmQuery.ProposalNo.value;
  if (cProposalNo != "")
  {
  	window.open("./UWManuHealthMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择个人保单,后查看其信息!");
  }
}


/*********************************************************************
 *  对团体单下的个人加费承保
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

  var cProposalNo=fmQuery.ProposalNo.value;
  tUWIdea = fmQuery.all('UWIdea').value;
  if (cProposalNo != "")
  {
  	window.open("./UWGSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag=2&UWIdea="+tUWIdea,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择一个个人投保单!");
  }
}


/*********************************************************************
 *  对团体单特约承保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showGSpec()
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

  var cProposalNo=fmQuery.GrpProposalContNo.value;
  var tUWIdea = fmQuery.GUWIdea.value;
  if (cProposalNo != "")
  {
  	window.open("./UWGrpSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag=1&UWIdea="+tUWIdea,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择一个团体保单!");
  }
}


/*********************************************************************
 *  对团体主险投保单的个人体检资料查询(团体单体检件只容许录入到主险个单上)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showHealthQ()
{
  var cProposalNo=fmQuery.ProposalNo.value;
  var cGrpProposalContNo=fmQuery.GrpProposalContNo.value;
  var cGrpMainProposalNo=fmQuery.GrpMainProposalNo.value;

  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

  if (cProposalNo != "" && cGrpProposalContNo == cGrpMainProposalNo)
  {
  	window.open("./UWManuHealthQMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);

  }
  else
  {

  	alert("请先选择一个团体主险投保单下的个人投保单!");
  }
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
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

  //initPolGrid();
  fmQuery.submit(); //提交
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
}

//Click事件，当点击“责任信息”按钮时触发该函数
function showDuty()
{
  //下面增加相应的代码
  //showModalDialog("./ProposalDuty.jsp",window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=13cm");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open ("./ProposalDuty.jsp",name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

}

//Click事件，当点击“暂交费信息”按钮时触发该函数
function showFee()
{
  //下面增加相应的代码
  //showModalDialog("./ProposalFee.jsp",window,"status:no;help:0;close:0;dialogWidth=16cm;dialogHeight=8cm");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open ("./ProposalFee.jsp",name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

}


/*********************************************************************
 *  显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
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


/*********************************************************************
 *  查询团体单下主附团体投保单单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function querygrp()
{
	// 初始化表格
   initPolBox();
	 initGrpGrid();

	// 书写SQL语句
	var str = "";
	var PrtNo = fmQuery.all('PrtNoHide').value;
	var mOperate = fmQuery.all('Operator').value;

	var strsql = "";

		var sqlid1="GroupUWSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(GrpContNo);//指定传入的参数
		mySql1.addSubPara(GrpContNo);//指定传入的参数
	    strsql=mySql1.getString();	


//	strsql = "select GrpContNo,PrtNo,GrpName,RiskCode,"
//	       + "(select riskshortname from lmrisk where riskcode=a.riskcode),"
//	       + "payintv,peoples2,amnt,cvalidate," 
//	       + "(select max(enddate) from lcpol where grpcontno='" + GrpContNo + "' and grppolno=a.grppolno),"
//	       + "GrpPolNo,ManageCom,UWFlag from LCGrpPol a "
//	       + "where 1=1 and GrpContNo='"+GrpContNo+"'";

	 //execEasyQuery( strSQL );
	//查询SQL，返回结果字符串
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult) {
    alert("没有符合条件集体单！");
    return "";
    }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = GrpGrid;

  //保存SQL语句
  turnPage.strQuerySql = strsql;

  //设置查询起始位置
  turnPage.pageIndex = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

//alert(1)
	var arrSelected = new Array();
	
		var sqlid2="GroupUWSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(GrpContNo);//指定传入的参数
	    var strSQL=mySql2.getString();	
	
//	var strSQL = "select GrpContNo,PrtNo,ManageCom,SaleChnl,AgentCom,AgentCode,AgentGroup,AppntNo,VIPValue,BlacklistFlag,lcgrpcont.GrpName from lcgrpcont,LDGrp where grpcontno='"+GrpContNo+"'"
//		+" and lcgrpcont.AppntNo = LDGrp.CustomerNo " ;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
if (turnPage.strQueryResult) {	
	fmQuery.all('GrpContNo').value = arrSelected[0][0];
	fmQuery.all('PrtNo').value = arrSelected[0][1];
	fmQuery.all('ManageCom').value = arrSelected[0][2];
	fmQuery.all('SaleChnl').value = arrSelected[0][3];
	fmQuery.all('AgentCom').value = arrSelected[0][4];
	fmQuery.all('AgentCode').value = arrSelected[0][5];
	fmQuery.all('AgentGroup').value = arrSelected[0][6];

	fmQuery.all('AppntNo').value = arrSelected[0][7];
	fmQuery.all('VIPValue').value = arrSelected[0][8];
	fmQuery.all('BlacklistFlag').value = arrSelected[0][9];
	fmQuery.all('AppntName').value = arrSelected[0][10];
}
	initSendTrace();
	//ctrlButtonDisabled(GrpContNo);
	
  return true;
}

/********************************************************************
*生产团体下发通知书工作流节点
*参数  ：无
*返回值：无
***********************************************************************
*/

	function makeWorkFlow()
{

	fmQuery.MissionID.value = MissionID;

	fmQuery.SubMissionID.value = SubMissionID;

	fmQuery.action = "./GrpManuUWChk.jsp";
	fmQuery.submit();

	}


/*********************************************************************
 *  查询团体单下自动核保未通过的个人单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryPol()
{
	var GrpProposalContNo = fmQuery.all('GrpProposalContNo').value;
	var mOperate = fmQuery.all('Operator').value;
	var strsql = "";

	// 初始化表格
	showDiv(divPerson,"false");
	initPolBox();
	//initPolGrid();
	// 书写SQL语句

		var sqlid3="GroupUWSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(GrpProposalContNo);//指定传入的参数
	    strsql=mySql3.getString();	


//	strsql = "select LCPol.ProposalNo,LCPol.AppntName,LCPol.RiskCode,LCPol.RiskVersion,LCPol.InsuredName,LCPol.ManageCom from LCPol where 1=1 "
//			  + "and LCPol.GrpPolNo = '"+GrpProposalContNo+"'"
// 	    	  + " and LCPol.AppFlag='0'"
//         	  + " and LCPol.UWFlag in ('3','5','6','8','7')"         //自动核保待人工核保
//			  + " and LCPol.contno = '00000000000000000000'"
//			  + " order by LCPol.makedate ";

 	//alert(strsql);
    //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 1, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("此单下个人单核保均已全部通过！");
    return "";
  }

  showDiv(divPerson,"true");
  //个人体检资料录入只针对团体主险投保单下的个人单
  if(GrpProposalContNo == fmQuery.all('GrpMainProposalNo').value)
  {
  	showDiv(divBoth,"true");
  	showDiv(divAddFee,"false");
  }
  else
  {
  	showDiv(divBoth,"false");
  	showDiv(divAddFee,"true");
  }
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strsql;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}


/*********************************************************************
 *  查询集体下未过个人单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getGrpPolGridCho()
{
	fmQuery.PolTypeHide.value = '2';
	fmQuery.submit();
}



/*********************************************************************
 *  选择要人工核保保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolGridCho()
{
	fmQuery.PolTypeHide.value = '1';
	fmQuery.submit();
}


/*********************************************************************
 *  个人单核保确认
 *  UWState:(0 未核保 1拒保 2延期 3条件承保 4通融 5自动 6待上级 7问题件 8核保通知书 9正常 a撤单 b保险计划变更)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function manuchk()
{
	var cProposalNo=fmQuery.ProposalNo.value;
	var flag = fmQuery.all('UWState').value;
	var tUWIdea = fmQuery.all('UWIdea').value;

	if (flag == "0"||flag == "1"||flag == "4"||flag == "9")
	{
	   //showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		fmQuery.action = "./UWManuNormChk.jsp";
		fmQuery.submit();
	}
	if (flag == "")
	{
		alert("选择核保结论！")
	}

	initPolBox();
	//initPolGrid();
}


/*********************************************************************
 *  团体整单核保确认
 *  (0 未核保 1拒保 2延期 3条件承保 4通融 5自动 6待上级 7问题件 8核保通知书 9正常 a撤单 b保险计划变更)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function gmanuchk(flag)
{
	
    var tUWIdea = fmQuery.UWIdea.value;
    var tUpReport = fmQuery.uwUpReport.value                //上报标志
    if(flag==1)
    {
        fmQuery.all('YesOrNo').value = "Y";
    }
    if(flag ==2)
    {
        fmQuery.all('YesOrNo').value = "N";
    }
    if(flag == 0)
    {
        isClosed = true;
    }
	var cGrpContNo=fmQuery.GrpContNo.value;

	if( cGrpContNo == "" || cGrpContNo == null )
	{
		alert("请先选择一个团体投保单!");
	    cancelchk();
		return ;
	}
    if(tUpReport == "")
    {
        alert("请先选择核保流向!");
        return ;
    }

//    if(tUWIdea == "")
//     {
//      alert("请先录入承保核保意见!");
//      return ;
//    }

    k++;

		var sqlid4="GroupUWSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(k);//指定传入的参数
		mySql4.addSubPara(k);//指定传入的参数
		mySql4.addSubPara(MissionID);//指定传入的参数
	    var strsql =mySql4.getString();	

//    var strsql = "select submissionid from lwmission where "+k+"="+k
//                +" and activityid = '0000002010'"
//    						+" and missionid = '"+MissionID+"'"
//    						;

    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnPage.strQueryResult) {
    	alert("未查到工作流SubMissionID");
    	fmQuery.SubMissionID.value = "";
    	return;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
    fmQuery.SubMissionID.value = turnPage.arrDataCacheSet[0][0];

	//操作功能实现
	fmQuery.WorkFlowFlag.value = "0000002010";
	fmQuery.MissionID.value = MissionID;		
		
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fmQuery.action = "./UWConfirm.jsp";
  	fmQuery.submit(); //提交
	
}




/*********************************************************************
 *  个单问题件录入(目前只是预留,功能已实现,只是未在用户界面上未显示录入按钮)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function QuestInput()
{
	var cProposalNo = fmQuery.ProposalNo.value;

	if(cProposalNo == "")
	{
		alert("请先选择一条个人单记录！");
	}
	else
	{
		window.open("./GrpQuestInputMain.jsp?GrpProposalContNo="+cProposalNo+"&ProposalNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	}

}


/*********************************************************************
 *  团体问题件录入(团体下的问题件录入原则是:个人单问题件全部发到该团体单下主险团体单上,不发到相应的个单上(考虑到团体单自身业务特点而设计))
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GrpQuestInput()
{
	var cGrpProposalContNo = fmQuery.GrpProposalContNo.value;  //团体保单号码
	if(cGrpProposalContNo==""||cGrpProposalContNo==null)
	{
  		alert("请先选择一个团体主险投保单!");
  		return ;
    }
    if(cGrpProposalContNo != fmQuery.GrpMainProposalNo.value)
	{
  		alert("请先选择一个团体主险投保单!");
  		return ;
    }
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./GrpQuestInputMain.jsp?GrpProposalContNo="+cGrpProposalContNo+"&ProposalNo="+cGrpProposalContNo+"&Flag="+cflag,"window2",sFeatures);

}


/*********************************************************************
 *  个单问题件查询(目前只是预留,功能已实现,只是未在用户界面上未显示查询按钮)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function QuestQuery()
{
	var cProposalNo = fmQuery.ProposalNo.value;//个单投保单号
	if(cProposalNo==""||cProposalNo==null)
	{
  		alert("请先选择一个个人投保单!");
  		return ;
    }
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestQueryMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
}


/*********************************************************************
 *  团单问题查询(团体下的问题件发放原则是:个人单问题件全部发到该团体单下主险团体单上,不发到相应的个单上(考虑到团体单自身业务特点而设计))
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GrpQuestQuery()
{
	
	var cGrpContNo = fmQuery.all('GrpContNo').value;  //团单投保单号码
	//alert(cGrpContNo);
	if(cGrpContNo==""||cGrpContNo==null)
	{
  		alert("请先选择一个团体主险投保单!");
  		return ;
    }
 
	window.open("./GrpQuestQueryMain.jsp?ProposalNo1="+cGrpContNo+"&Flag="+cflag,sFeatures);
}


/*********************************************************************
 *  对团体单发加费核保通知书
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 /*
function SendNotice()
{
  var cGrpProposalContNo = fmQuery.GrpProposalContNo.value;
  fmQuery.GUWState.value = "8";
  if (cGrpProposalContNo != "" && cGrpProposalContNo == fmQuery.GrpMainProposalNo.value)
  {
	gmanuchk();
	cancelchk();
  }
  else
  {
  	alert("请先选择团体单下的主险投保单!");
  	cancelchk();
  }
}


*/
/*********************************************************************
 *  对团体单发送问题件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function SendQuest()
{
  var cGrpProposalContNo = fmQuery.GrpMainProposalNo.value;
  fmQuery.GUWState.value = "7";
  if (cGrpProposalContNo != "" && cGrpProposalContNo==fmQuery.GrpMainProposalNo.value)
  {
	gmanuchk();
	//cancelchk();
  }
  else
  {
  	alert("请先选择团体单下的主险投保单!");
  	cancelchk();
  }
}


/*********************************************************************
 *  点击取消按钮,初始化界面各隐藏变量
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cancelchk()
{
	fmQuery.all('GUWState').value = "";
	fmQuery.all('GUWIdea').value = "";
}


/*********************************************************************
 *  返回到自动核保查询主界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function GoBack()
 {
 	location.href  = "./ManuUWAll.jsp?type=2";
 	
 }
function temp()
{
	alert("此功能尚缺！");
}
/*********************************************************************
 *  点击扫描件查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ScanQuery2()
{
	var arrReturn = new Array();

	var prtNo=fmQuery.all("PrtNo").value;
	if(prtNo==""||prtNo==null)
	{
	  	alert("请先选择一个团体投保单!");
  		return ;
  	}
	window.open("../uw/ImageQueryMainGrp.jsp?ContNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
}

function GrpContQuery(cGrpContNo){
	
	window.open("./GroupContMain.jsp?GrpContNo=" + cGrpContNo,"GroupContQuery",sFeatures);
}
/*********************************************************************
 *  进入团体合同
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function showGrpCont()
{
     var cGrpContNo=fmQuery.GrpContNo.value;
    //var newWindow=window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1"); 
   // window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo+"&GrpPrtNo="+cGrpContNo+"&SubType=TB1002","window1");
    window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo+"&prtNo="+PrtNo+"&SubType=TB1002","window1",sFeatures);
}


//团单既往询价信息
 function showAskApp()
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

  cGrpContNo=fmQuery.GrpContNo.value;
  cAppntNo = fmQuery.AppntNo.value;
  
  if (cGrpContNo != "")
  {
  	
  	window.open("../askapp/AskUWAppMain.jsp?GrpContNo1="+cGrpContNo+"&AppntNo1="+cAppntNo,"window2",sFeatures);
  	showInfo.close();
  }
  
}             

/*********************************************************************
 *  集体体检通知书
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function checkBody()
{
	  cGrpContNo=fmQuery.GrpContNo.value;
	
	  
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
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendBodyCheckChk.jsp";
	  	
	    fmQuery.submit();
	   
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("请先选择保单!");
      }
      
  	
}
 
/*********************************************************************
 *  契调通知书
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 
function RReport()
{
	 cGrpContNo=fmQuery.GrpContNo.value;
	
	  
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
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendRReportChk.jsp?GrpContNo="+cGrpContNo;
	  	
	    fmQuery.submit();
	   
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("请先选择保单!");
      }
  	
}


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

  
  var cContNo = fmQuery.GrpContNo.value;
  

  var cPrtNo = fmQuery.PrtNo.value;
 
  if (cContNo != "")
  {
  	
  	window.open("./GrpUWRReportMain.jsp?ContNo1="+cContNo+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
      
 
}



/*********************************************************************
 *  既往保障信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function showHistoryCont()
{
		cGrpContNo=fmQuery.GrpContNo.value;
		window.open("../uw/GrpUWHistoryContMain.jsp?GrpContNo="+cGrpContNo,"window2",sFeatures);
}
/*********************************************************************
 *  承保计划变更
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function showChangePlan()
{
  var cpolNo = fmQuery.GrpContNo.value;
  //var cType = "ChangePlan";
  

  window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=23&polNo="+cpolNo, "","status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);  	  
}


/*********************************************************************
 *  保险计划变更结论录入显示
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function showChangeResultView()
{
	//fmQuery.ChangeIdea.value = "";
	//fm.PolNoHide.value = fm.ProposalNo.value;  //保单号码
	//divUWResult.style.display= "none";
	var cGrpContNo = fmQuery.GrpContNo.value;
	//divChangeResult.style.display= "";
	window.open("./UWGChangeResultMain.jsp?GrpContNo=" + cGrpContNo + "&MissionID=" + MissionID,"",sFeatures);	
}

//发首期交费通知书
function SendFirstNotice()
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

  cProposalNo=fmQuery.GrpContNo.value;
 
  cOtherNoType="01"; //其他号码类型
  cCode="57";        //单据类型
  
  if (cProposalNo != "")
  {
  	//showModalDialog("./GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open ("./GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}



//发核保结论通知书
function GrpSendNotice()
{
	
		var sqlid5="GroupUWSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(GrpContNo);//指定传入的参数
	    var tSQL =mySql5.getString();	
	
//	var tSQL = "select 'X' from lcgrppol where grpcontno='" + GrpContNo
//	           + "' and (uwflag='5' or uwflag='z')";
	turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1); 
	 //判断是否查询成功
    if (turnPage.strQueryResult) {
    	
     	alert("不容许发放核保结论通知书,原因是:还未下险种核保结论.");
        return ;
    }
    
			var sqlid6="GroupUWSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(PrtNo);//指定传入的参数
		mySql6.addSubPara(MissionID);//指定传入的参数
	    var strsql =mySql6.getString();	
	
//    var strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + PrtNo +"'"
//				
//				 + " and LWMission.ActivityID = '0000002101'"
//				 + " and LWMission.ProcessID = '0000000004'"
//				 + " and LWMission.MissionID = '" +MissionID +"'";
		
  
  	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);   
  	
    //判断是否查询成功
    if (!turnPage.strQueryResult) {
    	
     	alert("不容许发放新的核保结论通知书,原因可能是:1.已发核保通知书,但未打印.");
        fmQuery.SubMissionID.value = "";
        return ;
    }
   
    var arrSelected = new Array();
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    fmQuery.SubMissionID.value = arrSelected[0][0];
  
    
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


  cProposalNo=fmQuery.GrpContNo.value;
 
  if (cProposalNo != "")
  {
  	fmQuery.action = "./UWSendNoticeChk.jsp";
  	fmQuery.submit();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
		  
}


//问题件录入
function QuestInput()
{
	cContNo = fmQuery.ContNo.value;  //保单号码
	
		var sqlid7="GroupUWSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(cContNo);//指定传入的参数
	    var strSql =mySql7.getString();	
	
//	var strSql = "select * from LCcUWMaster where ContNo='" + cContNo + "' and ChangePolFlag ='1'";
    //alert(strSql);
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      var tInfo = "承保计划变更未回复,确认要录入新的问题件?";
   if(!window.confirm( tInfo )){ 
          return;
        }
      }    
	window.open("./QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window1",sFeatures);

}

/*********************************************************************
 *  初始化是否上报核保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initSendTrace()
{

	var tSql = "";

		var sqlid8="GroupUWSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySq8.addSubPara(GrpContNo);//指定传入的参数
		mySql8.addSubPara(GrpContNo);//指定传入的参数
	   tSql=mySql8.getString();	

//	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
//					+ " and otherno = '"+GrpContNo+"'"
//					+ " and othernotype = '2'"
//					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+GrpContNo+"')" 
//					;
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

  //判断是否查询成功
  if (turnPage.strQueryResult) {
		fmQuery.all('SendFlag').value =arrSelected[0][0];
		fmQuery.all('SendUWFlag').value =arrSelected[0][1];
		fmQuery.all('SendUWIdea').value =arrSelected[0][2];

		DivLCSendTrance.style.display="";
	}

	if(fmQuery.all('SendFlag').value == '0')
	{
		divUWSave.style.display = "";
		divUWAgree.style.display = "none";
	}	
	else if(fmQuery.all('SendFlag').value == '1')
	{
		divUWAgree.style.display = "";
		divUWSave.style.display = "none";
		tSql = "select passflag,uwidea from lcgcuwmaster where 1=1 "
					+ " and grpcontno = '"+GrpContNo+"'" 
					;
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
		fmQuery.all('GUWState').value = arrSelected[0][0];
		fmQuery.all('GUWIdea').value = arrSelected[0][1];
	}
}
function getfee(parm1,parm2)
{
	var grppolno=fmQuery.all(parm1).all('GrpGrid11').value;
	var tAddPrem = "";
	
		var sqlid9="GroupUWSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(grppolno);//指定传入的参数
	   var strSQL=mySql9.getString();	
	
//	var strSQL="select sum(standprem) from lcpol where grppolno='"+grppolno+"'";
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

    //判断是否查询成功
    if (turnPage.strQueryResult) {
		tAddPrem =arrSelected[0][0];
	}	
    
		var sqlid10="GroupUWSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(tAddPrem);//指定传入的参数
		mySql10.addSubPara(tAddPrem);//指定传入的参数
		mySql10.addSubPara(fmQuery.GrpContNo.value);//指定传入的参数
		mySql10.addSubPara(grppolno);//指定传入的参数
	   strSQL=mySql10.getString();	
	
//    strSQL="select concat('￥',ltrim(to_char(nvl(sum(prem),0),'999999999999.99')))," +
//          "concat('￥',ltrim(to_char(" + tAddPrem + 
//         ",'999999999999.99'))),round(nvl(sum(prem)/" + tAddPrem +",0),2)"
//         + " from lcprem where grpcontno='" + fmQuery.GrpContNo.value
//         + "' and polno in (select polno from lcpol where grppolno='"+grppolno+"')";	
  	turnPage.queryModal(strSQL,GrpPolFeeGrid);
  	
  	if (GrpPolFeeGrid.mulineCount > 0){
		
		var sqlid11="GroupUWSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(grppolno);//指定传入的参数
	   var sSQL=mySql11.getString();	
		
//      	var sSQL = "select standbyflag2,standbyflag3 from lcgrppol where grppolno='" + grppolno + "'" ;
      	turnPage.strQueryResult  = easyQueryVer3(sSQL, 1, 0, 1);  
    	arrRate = decodeEasyQueryResult(turnPage.strQueryResult);
    	if (arrRate != null && arrRate.length > 0)
    	{
        	GrpPolFeeGrid.setRowColData(0,4,arrRate[0][0]);
        	GrpPolFeeGrid.setRowColData(0,5,arrRate[0][1]);
        }
    }
  	
		var sqlid12="GroupUWSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(grppolno);//指定传入的参数
	   var tSql=mySql12.getString();	
	
//  var tSql = "select passflag ,uwidea from lcguwmaster where grppolno = '"+grppolno+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		if (arrSelected != null && arrSelected.length > 0){
		
		    fmQuery.all('GUWState').value = arrSelected[0][0];
		    fmQuery.all('GUWIdea').value = arrSelected[0][1];
		}
  
}


function showNotePad()
{

//	var selno = SelfGrpGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("请选择一条任务");
//	      return;
//	}
	
//var MissionID = SelfGrpGrid.getRowColData(selno, 4);
  //var MissionID = MissionID;
 
  
//var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
 // var SubMissionID = fm.all.SubMissionID.value;
 
  
//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
//	var ActivityID = fm.all.ActivityID.value;
	
	
//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
//  var PrtNo = fm.all.PrtNo2.value;

  
//	var NoType = fm.all.NoType.value;

	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}

/*********************************************************************
 *  初始化代码框
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInfo()
{
		showOneCodeName('comcode','ManageCom','ManageComName');
		showOneCodeName('agenttype','SaleChnl','SaleChnlName');
		showOneCodeName('comcode','AgentCom','AgentComName');
		showOneCodeName('vipvalue','VIPValue','VIPValueName');
		showOneCodeName('blacklistflag','BlacklistFlag','BlacklistFlagName');
		showOneCodeName('uwupreport','SendFlag','SendFlagName');
		showOneCodeName('contuwstate','SendUWFlag','SendUWFlagName');
}

/*********************************************************************
 *  对集体保单险种下结论
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function grpPolCommit()
 {
 	 
	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	if (GrpPolFeeGrid.getRowColData(0,4) == "null" 
	     || GrpPolFeeGrid.getRowColData(0,4) == ""
	     || GrpPolFeeGrid.getRowColData(0,4) == null)
	{
	    alert("管理费用率为空！");
	}
	
	if (GrpPolFeeGrid.getRowColData(0,5) == "null" 
	     || GrpPolFeeGrid.getRowColData(0,5) == ""
	     || GrpPolFeeGrid.getRowColData(0,5) == null)
	{
	    alert("销售费用率为空！");
	}
	
	if (GrpPolFeeGrid.getRowColData(0,4) != "null" && 
        GrpPolFeeGrid.getRowColData(0,4)!= ""){
    	if (isNumeric(GrpPolFeeGrid.getRowColData(0,4)) == false)
    	{
    	    alert("请输入正确的管理费用率！");
    	    return;
    	}
        else if (parseFloat(GrpPolFeeGrid.getRowColData(0,4)) == 0){
            alert("管理费用率为0！");
        }
    }
    if (GrpPolFeeGrid.getRowColData(0,5) != "null" && 
        GrpPolFeeGrid.getRowColData(0,5)!= ""){
    	if (isNumeric(GrpPolFeeGrid.getRowColData(0,5)) == false)
    	{
    	    alert("请输入正确的销售费用率！");
    	    return;
    	}
    	else if (parseFloat(GrpPolFeeGrid.getRowColData(0,5)) == 0){
            alert("销售费用率为0！");
        }
    }	
   
	
  cProposalNo=GrpGrid.getRowColData(selno,11);
  fmQuery.GrpPolNo.value = cProposalNo;
  
  var flag = fmQuery.all('GUWState').value;
	var tUWIdea = fmQuery.all('GUWIdea').value;
  
  	if (flag == null || flag == "")
	{
		alert("请先选择核保结论!");
	    cancelchk();
		return ;
	}
	
	if (flag == "1")
	{
	    if (confirm("确认要下拒保结论吗？") == false)
	    {
	        return;
	    }
	}
	//var strSQL="select UWFlag from LCGrpPol where PrtNo='"+PrtNo+"'";
	//var tflag = easyExecSql(strSQL);
	
	if(flag=='z')
	{
		alert("核保结论不能为z!");
	    cancelchk();
		return ;
	}
     
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
  if (cProposalNo != "")
  {
  	fmQuery.action = "./GrpPolCommitChk.jsp";
  	fmQuery.submit();
  }
 
}
function queryProposal(){
	  
	var cContNo = fmQuery.all('GrpContNo').value;
	
		var sqlid13="GroupUWSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(cContNo);//指定传入的参数
	   var strsql=mySql13.getString();	
	
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	
	if(arr!=null){
		fmQuery.all('AppntNo').value=arr[0][0];
	}
	//alert(fmQuery.all('AppntNo').value);
    window.open("./GroupProposalQueryMain.jsp?AppntNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);

}
function queryNotProposal(){
  
  var cContNo = fmQuery.all('GrpContNo').value;
  
  		var sqlid14="GroupUWSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(cContNo);//指定传入的参数
	   var strsql=mySql14.getString();	
  
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	if(arr!=null){
		fmQuery.all('AppntNo').value=arr[0][0];
	}
	//alert(fmQuery.all('AppntNo').value);

	window.open("./GroupNotProposalQueryMain.jsp?AppntNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);

}

function grpAddFee()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var tGrpContNo = fmQuery.all("GrpContNo").value;
 
  //var tContNo = fmQuery.all("ContNo").value;
  //var tPolNo = fmQuery.all("PolNo").value;
  if (tGrpContNo != "")
  {
	window.open("./GrpUWAddFeeMain.jsp?GrpContNo=" + tGrpContNo ,"window1",sFeatures);
  }
  else
  {
  alert("请先选择保单!");
  }
}
/*********************************************************************
 *  查询集体保单生调结论
 *  参数  ：  GrpContNo
 *  返回值：  无
 *********************************************************************
 */
 function showReport()
 {
 	var tGrpContNo = fmQuery.all("GrpContNo").value;
 	if (tGrpContNo != "")
  {
	window.open("./GrpRReportResultMain1.jsp?GrpContNo=" + tGrpContNo ,"window1",sFeatures);
  }
  else
  {
  alert("请先选择保单!");
  }
 }
 /*********************************************************************
 *  查询集体体检生调结论
 *  参数  ：  GrpContNo
 *  返回值：  无
 *********************************************************************
 */
 function showPE()
 {
 	var tGrpContNo = fmQuery.all("GrpContNo").value;
 	if (tGrpContNo != "")
  {
	window.open("./GrpPEResultMain.jsp?GrpContNo=" + tGrpContNo ,"window1",sFeatures);
  }
  else
  {
  alert("请先选择保单!");
  }
 }
 
 //风险要素费率调整
 
 function grpRiskElement()
 {
 	//add by yaory
 	var selno = GrpGrid.getSelNo()-1;
 	if (selno <0)
    {
	      alert("请选择集体险种单");
	      return;
	  }
 	var tGrpContNo = fmQuery.all("GrpContNo").value;
 	var riskcode=GrpGrid.getRowColData(selno, 4);
 	var tGrpPolNo = GrpGrid.getRowColData(selno, 11);
 	var tPrtNo = GrpGrid.getRowColData(selno, 2);
 	if (tGrpContNo != "")
  {
	window.open("./GrpRiskElenemtMain.jsp?GrpContNo="+tGrpContNo+"&riskcode="+riskcode,"window1",sFeatures);
  }
  else
  {
  alert("请先选择保单!");
  }
 }
 
 /*********************************************************************
 *  团单浮动费率调整
 *  参数  ：  GrpPolNo
 *  返回值：  无
 *********************************************************************
 */
 function grpFloatRate()
 {
   	var selno = GrpGrid.getSelNo()-1;
  	if (selno <0)
    {
	      alert("请选择集体险种单");
	      return;
	  }
	
   var tGrpPolNo=GrpGrid.getRowColData(selno,11);
    	if (tGrpPolNo != "")
  {
	window.open("./GrpFloatRateMain.jsp?GrpPolNo=" + tGrpPolNo ,"window1",sFeatures);
  }
   
 }
 
 /*********************************************************************
 *  返回复核节点
 *  参数  ：  无
 *  返回值：  无
 *  修改人：Gaoht
 *  时  间：2005-10-12
 *********************************************************************
 */
function returnApprove()
{

//    var tSql = " select * from lwmission where  1=1 "
//             + " and activityid in ('0000002004') "
//             + " and missionid = '"+fm.MissionID.value+"'"
//             + " and submissionid = '"+fm.SubMissionID.value+"'";

//    var arr = easyExecSql( tSql );
   
//    if (arr) 
//    {
//         alert("还有通知书没有回复, 不允许返回复核")   
//         return ;   
//    }
    
    var i = 0;
    var showStr="正在传送数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();


    fmQuery.action = "../uw/GrpUWReturnApproveSave.jsp";
    fmQuery.submit(); //提交

}

/*********************************************************************
 *  执行返回复核节点后的动作
 *  参数  ：  无
 *  返回值：  无
 *  修改人：续涛
 *  时  间：2005-10-12
 *********************************************************************
 */
function afterReturnApprove(FlagStr,content)
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
       showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

       showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	   showInfo.focus();
    }
}


//*****************add by haopan********************//
function ctrlButtonDisabled(GrpContNo)
{
	  var tSQL = "";
  	var arrResult;
  	var arrButtonAndSQL = new Array;
  	
	  arrButtonAndSQL[0] = new Array;
	  arrButtonAndSQL[0][0] = "uwButton1";
	  arrButtonAndSQL[0][1] = "团体扫描件查询";
	  arrButtonAndSQL[0][2] = "select * from  es_doc_relation e where e.bussno='"+GrpContNo+"'";
	  	  
	  arrButtonAndSQL[1] = new Array;
	  arrButtonAndSQL[1][0] = "uwButton2";
	  arrButtonAndSQL[1][1] = "团体自动核保信息";
	  arrButtonAndSQL[1][2] = "";
	  
	  arrButtonAndSQL[2] = new Array;
	  arrButtonAndSQL[2][0] = "uwButton3";
	  arrButtonAndSQL[2][1] = "团体保单明细";
	  arrButtonAndSQL[2][2] = "";
	  
	  arrButtonAndSQL[3] = new Array;
	  arrButtonAndSQL[3][0] = "uwButton4";
	  arrButtonAndSQL[3][1] = "个人自动核保信息";
	  arrButtonAndSQL[3][2] = "";
	  
	  arrButtonAndSQL[4] = new Array;
	  arrButtonAndSQL[4][0] = "uwButton5";
	  arrButtonAndSQL[4][1] = "团体已承保保单查询";
	  arrButtonAndSQL[4][2] = "select * from lcgrppol a,lcgrpcont b where a.grpcontno=b.grpcontno and a.customerno='"+fmQuery.all('AppntNo').value+"'  and a.appflag in ('1','4')";
	  
	  arrButtonAndSQL[5] = new Array;
	  arrButtonAndSQL[5][0] = "uwButton6";
	  arrButtonAndSQL[5][1] = "团体未承保保单查询";
	  arrButtonAndSQL[5][2] = "select * from lcgrppol a,lcgrpcont b where a.grpcontno=b.grpcontno and a.customerno='"+fmQuery.all('AppntNo').value+"'  and a.appflag = '0'";
	  
	  arrButtonAndSQL[6] = new Array;
	  arrButtonAndSQL[6][0] = "uwButton7";
	  arrButtonAndSQL[6][1] = "团体保单问题件查询";
	  arrButtonAndSQL[6][2] = "select * from lcgrpissuepol where grpcontno='"+GrpContNo+"' and rownum=1";
	
		arrButtonAndSQL[7] = new Array;
	  arrButtonAndSQL[7][0] = "uwButton8";
	  arrButtonAndSQL[7][1] = "发核保结论通知书";
	  arrButtonAndSQL[7][2] = "";
	  
	  arrButtonAndSQL[8] = new Array;
	  arrButtonAndSQL[8][0] = "uwButton9";
	  arrButtonAndSQL[8][1] = "修改事项索要材料录入";
	  arrButtonAndSQL[8][2] = "";
	
		arrButtonAndSQL[9] = new Array;
	  arrButtonAndSQL[9][0] = "uwButton10";
	  arrButtonAndSQL[9][1] = "团体生调结论查询";
	  arrButtonAndSQL[9][2] = "select * from  lcrreport where grpcontno='"+GrpContNo+"' and rownum=1";
	  
	  arrButtonAndSQL[10] = new Array;
	  arrButtonAndSQL[10][0] = "uwButton11";
	  arrButtonAndSQL[10][1] = "团体体检结论查询";
	  arrButtonAndSQL[10][2] = "select * from lcpenotice where grpcontno='"+GrpContNo+"' and rownum=1";
	
		arrButtonAndSQL[11] = new Array;
	  arrButtonAndSQL[11][0] = "uwButton12";
	  arrButtonAndSQL[11][1] = "风险要素评估";
	  arrButtonAndSQL[11][2] = "";
	
		arrButtonAndSQL[12] = new Array;
	  arrButtonAndSQL[12][0] = "uwButton13";
	  arrButtonAndSQL[12][1] = "浮动费率调整";
	  arrButtonAndSQL[12][2] = "";
	  
	  arrButtonAndSQL[13] = new Array;
	  arrButtonAndSQL[13][0] = "uwButton14";
	  arrButtonAndSQL[13][1] = "团体既往保全查询";
	  arrButtonAndSQL[13][2] = "select * from lpgrpedoritem p where '"+fmQuery.all('AppntNo').value+"' in  (select appntno from lpgrpcont where grpcontno = p.grpcontno) and edorstate = '0'";
	  
	  arrButtonAndSQL[14] = new Array;
	  arrButtonAndSQL[14][0] = "uwButton15";
	  arrButtonAndSQL[14][1] = "团体既往理赔查询";
	  arrButtonAndSQL[14][2] = "select * from llgrpregister g,lcgrpcont c where g.rgtobjno=c.grpcontno and c.appntno='"+fmQuery.all('AppntNo').value+"' "; 

	  
	  arrButtonAndSQL[15] = new Array;
	  arrButtonAndSQL[15][0] = "uwButton16";
	  arrButtonAndSQL[15][1] = "通知书查询";
	  arrButtonAndSQL[15][2] = "select * from loprtmanager where otherno='"+GrpContNo+"'or standbyflag1='"+GrpContNo+"' or standbyflag2='"+GrpContNo+"'";
	
	  arrButtonAndSQL[16] = new Array;
	  arrButtonAndSQL[16][0] = "uwButton17";
	  arrButtonAndSQL[16][1] = "暂交费信息查询";
	  arrButtonAndSQL[16][2] = "select * from ljtempfee where otherno='"+GrpContNo+"' and othernotype='4'";
		
		for(var i=0; i<arrButtonAndSQL.length; i++)
			{
				if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!="")
				{
					//alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
					arrResult = easyExecSql(arrButtonAndSQL[i][2]);
				
					if(arrResult!=null)
					{
					
						eval("fmQuery.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
					}
					else
					{
							eval("fmQuery.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
					}
				}
				else
				{
					continue;
				}	
			}
	}

//查询记事本
function checkNotePad(PrtNo){

  		var sqlid15="GroupUWSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql15.setSqlId(sqlid15);//指定使用的Sql的id
		mySql15.addSubPara(PrtNo);//指定传入的参数
	   var strSQL=mySql15.getString();	

//	var strSQL="select count(*) from LWNotePad where otherno='"+PrtNo+"'";
	
	var arrResult = easyExecSql(strSQL);


	eval("fmQuery.all('NotePadButton').value='记事本查看 （共"+arrResult[0][0]+"条）'");
	
}

//既往保全查询
function queryEdor(){

	window.open("../bq/GEdorAgoQueryInput.jsp?CustomerNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);
	
}

//既往理赔查询
function queryClaim(){

	window.open("./ClaimGrpQueryInput.jsp?CustomerNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);
}

//通知书查询
function queryNotice()
{
	window.open("./QueryNotice.jsp?GrpContNo="+fmQuery.all('GrpContNo').value,"window1",sFeatures);	
}
//*************add end****************//

//变更团单生效日
function GrpChgCvalidate(){
    var cGrpContNo=fmQuery.GrpContNo.value;
    if(cGrpContNo==""||cGrpContNo==null)
    {
        alert("请先选择一个团体投保单!");
        return ;
    }
    window.open("./UWGChgCvalidateMain.jsp?GrpContNo="+cGrpContNo,"window1",sFeatures);
    
}

//暂交费查询
function showTempFee()
{
	var arrReturn = new Array();
	var tSel = GrpGrid.getSelNo();
	
	
	   var cGrpContNo = +fmQuery.all('GrpContNo').value; 
	   
	   if (cGrpContNo == "")
		    return;
		 
		var sqlid16="GroupUWSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		mySql16.addSubPara(cGrpContNo);//指定传入的参数
	   var strSQL=mySql16.getString();	
		 
		 
//		 var strSQL="select AppntNo from LCGrpCont where GrpContNo='"+cGrpContNo+"'";
		 var tAppntNo = easyExecSql(strSQL);
		   
		var sqlid17="GroupUWSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(cGrpContNo);//指定传入的参数
	   var strSQL=mySql17.getString();	
		   
//		 var strSQL="select APPntName from LJTempFee where OtherNo='"+cGrpContNo+"'group by APPntName";
		 var tAppntName = easyExecSql(strSQL);
		  
		  window.open("../uw/ShowTempFee.jsp?Prtno=" + cGrpContNo + " &AppntNo=" + tAppntNo + " &AppntName= " + tAppntName,"window1",sFeatures);
	
}


//判断是否是产品组合险种
function isContPlan()
{
	
		var sqlid18="GroupUWSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		mySql18.addSubPara(GrpContNo);//指定传入的参数
	   var sql=mySql18.getString();	
	
//	var sql="select contplancode from lccontplan where 1=1 and plantype='6' and grpcontno='"+GrpContNo+"'";
	var arrResult=new Array();
	arrResult=easyExecSql(sql);
	if(arrResult!=null)
  {
  	divLCGrpPol.style.display="none";
  	divGrpPolUWResult.style.display="none";
  	fmQuery.all('uwButton13').disabled='true';
  	fmQuery.all('uwButton12').disabled='true';
		divGrpContPlan.style.display="";
		divGrpPlanUWResult.style.display="";
  	return true;
  }
else
	{
		divLCGrpPol.style.display="";
		divGrpPolUWResult.style.display="";
		divGrpContPlan.style.display="none";
		divGrpPlanUWResult.style.display="none";
		return false;
	}
}


//产品组合查询
function queryContPlan()
{
	
		var sqlid19="GroupUWSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		mySql19.addSubPara(fmQuery.all('GrpContNo').value);//指定传入的参数
		mySql19.addSubPara( fmQuery.all('GrpContNo').value);//指定传入的参数
		mySql19.addSubPara( fmQuery.all('GrpContNo').value);//指定传入的参数
		
		mySql19.addSubPara(fmQuery.all('GrpContNo').value);//指定传入的参数
		mySql19.addSubPara(fmQuery.all('GrpContNo').value);//指定传入的参数
	   strSql=mySql19.getString();
	
//	 strSql=" select /*+RULE*/ a.contplancode, a.contplanname,'0',a.peoples2,"
//        +"(select nvl(sum(i.insuredpeoples),0) from lcinsured i "
//        +"where i.grpcontno = '"
//        +fmQuery.all('GrpContNo').value + "' and i.contplancode=a.contplancode)" 
//        +" as peoples,"
//        +"'',"
//        +"(select nvl(sum(prem),0) from lcprem where grpcontno='" + fmQuery.all('GrpContNo').value
//        +"' and contno in (select contno from lcinsured where grpcontno='" 
//        + fmQuery.all('GrpContNo').value +"' and contplancode=a.contplancode)),"
//        +"round((select nvl(sum(i.insuredpeoples),0) from lcinsured i "
//        +"where i.grpcontno = '"
//        +fmQuery.all('GrpContNo').value + "' and i.contplancode=a.contplancode)/decode(a.peoples2,0,1,a.peoples2),2) "
//        +"from lccontplan a Where a.plantype='6' and a.grpcontno = '"
//        +fmQuery.all('GrpContNo').value+"' order by a.contplancode" ;
        
      	turnPage2.queryModal(strSql,PlanGrid);
	
	
	var arrSelected = new Array();
	
			var sqlid20="GroupUWSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		mySql20.addSubPara(GrpContNo);//指定传入的参数
	   var strSQL=mySql20.getString();
	
//	var strSQL = "select GrpContNo,PrtNo,ManageCom,SaleChnl,AgentCom,AgentCode,AgentGroup,AppntNo,VIPValue,BlacklistFlag,lcgrpcont.GrpName from lcgrpcont,LDGrp where grpcontno='"+GrpContNo+"'"
//		+" and lcgrpcont.AppntNo = LDGrp.CustomerNo " ;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if (turnPage.strQueryResult)
  {	
	 fmQuery.all('GrpContNo').value = arrSelected[0][0];
	 fmQuery.all('PrtNo').value = arrSelected[0][1];
	 fmQuery.all('ManageCom').value = arrSelected[0][2];
	 fmQuery.all('SaleChnl').value = arrSelected[0][3];
	 fmQuery.all('AgentCom').value = arrSelected[0][4];
	 fmQuery.all('AgentCode').value = arrSelected[0][5];
	 fmQuery.all('AgentGroup').value = arrSelected[0][6];

	 fmQuery.all('AppntNo').value = arrSelected[0][7];
	 fmQuery.all('VIPValue').value = arrSelected[0][8];
	 fmQuery.all('BlacklistFlag').value = arrSelected[0][9];
	 fmQuery.all('AppntName').value = arrSelected[0][10];
  }
	initSendTrace();
}

//产品组合责任明细查询
function getPlanRiskDetail(parm1,parm2)
{
    var tContPlanCode=fmQuery.all(parm1).all('PlanGrid1').value;
	
		var sqlid21="GroupUWSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(fmQuery.all('PrtNo').value );//指定传入的参数
		mySql21.addSubPara(fmQuery.all('PrtNo').value);//指定传入的参数
		mySql21.addSubPara(tContPlanCode);//指定传入的参数
		
		mySql21.addSubPara(fmQuery.all('PrtNo').value);//指定传入的参数
		mySql21.addSubPara(tContPlanCode);//指定传入的参数
		mySql21.addSubPara(tContPlanCode);//指定传入的参数
		
		mySql21.addSubPara(fmQuery.PrtNo.value);//指定传入的参数
		mySql21.addSubPara(fmQuery.PrtNo.value );//指定传入的参数
		mySql21.addSubPara(tContPlanCode);//指定传入的参数
	   var tSQL=mySql21.getString();
	
//    var tSQL = "select distinct a.riskcode,'',b.dutycode,e.dutyname," 
//             + "(select nvl(sum(prem),0) from lcprem "
//             + "where grpcontno='" + fmQuery.all('PrtNo').value 
//             + "' and dutycode=b.dutycode and contno in "
//             + "(select contno from lcinsured where grpcontno='" 
//             + fmQuery.all('PrtNo').value +"' and contplancode='" 
//             + tContPlanCode +"')),"
//             + "(select nvl(sum(amnt),0) from lcduty "
//             + "where dutycode=b.dutycode and contno in "
//             + "(select contno from lcinsured where grpcontno='" 
//             + fmQuery.all('PrtNo').value +"' and contplancode='" 
//             + tContPlanCode +"'))"
//             + " from lccontplanrisk a,lccontplandutyparam b,"
//             + "lmriskduty d,lmduty e"
//             + " where d.riskcode=a.riskcode"
//             + " and b.dutycode=d.dutycode and e.dutycode=b.dutycode"
//             + " and a.contplancode='" + tContPlanCode 
//             + "' and a.plantype='6' and a.proposalgrpcontno='" 
//             + fmQuery.PrtNo.value + "' and b.grpcontno='" + fmQuery.PrtNo.value 
//             + "' and b.plantype='6' and b.contplancode='" + tContPlanCode + "'";
    turnPage3.queryModal(tSQL, PlanRiskGrid);
    //查询产品组合核保结论信息
       
	   		var sqlid22="GroupUWSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(fmQuery.PrtNo.value );//指定传入的参数
		mySql22.addSubPara(tContPlanCode);//指定传入的参数
		
	   var tSql=mySql22.getString();
	   
//    var tSql = "select passflag ,uwidea from lcgplanuwmaster where grpcontno = '"+fmQuery.PrtNo.value
//              +"' and contplancode='"+tContPlanCode+"' and plantype='6'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		if (arrSelected != null && arrSelected.length > 0)
		{
		
		    fmQuery.all('GPlanUWState').value = arrSelected[0][0];
		    fmQuery.all('GPlanUWIdea').value = arrSelected[0][1];
		}
		
		//产品组合特约信息
		
		var sqlid23="GroupUWSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("uw.GroupUWSql"); //指定使用的properties文件名
		mySql23.setSqlId(sqlid23);//指定使用的Sql的id
		mySql23.addSubPara(fmQuery.PrtNo.value );//指定传入的参数
		mySql23.addSubPara(tContPlanCode);//指定传入的参数
		
	   tSql=mySql23.getString();
		
//		tSql ="select remark2 from lccontplan where grpcontno='"+fmQuery.PrtNo.value+"' and contplancode='"+tContPlanCode+"' and plantype='6'";
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
	 if (arrSelected != null && arrSelected.length > 0)
	  {
		    fmQuery.all('PlanSpecContent').value = arrSelected[0][0];
		}
}
//产品组合核保结论保存
function grpPlanCommit()
{
	
	var selno = PlanGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
  var cContPlanCode=PlanGrid.getRowColData(selno,1);
  fmQuery.ContPlanCode.value = cContPlanCode;
  
  var flag = fmQuery.all('GPlanUWState').value;
	var tUWIdea = fmQuery.all('GPlanUWIdea').value;
  
  	if (flag == null || flag == "")
	{
		alert("请先选择核保结论!");
	    cancelchk();
		return ;
	}
	
	if (flag == "1")
	{
	    if (confirm("确认要下拒保结论吗？") == false)
	    {
	        return;
	    }
	}
	if(flag=='z')
	{
		alert("核保结论不能为z!");
	    cancelchk();
		return ;
	}
     
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
    if (cContPlanCode != "")
  {
  	  fmQuery.action = "./GrpPlanUWManuNormGChk.jsp";
  	  fmQuery.submit();
  }
}

/*********************************************************************
 *  
 *  团单保单特别约定录入
 *  
 *********************************************************************/
 function grpSpecInput()
 {
 	  
 	 var cGrpContNo=fmQuery.GrpContNo.value;
   window.open("../uw/UWManuGrpSpecInputMain.jsp?GrpContNo=" + cGrpContNo,"window1",sFeatures);
 }