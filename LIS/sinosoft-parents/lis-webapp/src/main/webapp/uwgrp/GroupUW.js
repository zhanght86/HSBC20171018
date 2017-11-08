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
window.onfocus=myonfocus;
var mDebug="0";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var mSwitch = parent.VD.gVSwitch;
var tBonusFlag="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
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
  document.getElementById("fmQuery").submit(); //提交
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
	unlockScreen('lkscreen');  
	showInfo.close();
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

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //执行下一步操作
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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==null||cProposalNo=="")
  {
  	showInfo.close();
  	alert("请先选择个人保单,后查看其信息!");

  }
  else{
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );
  window.open("../appgrp/ProposalMain.jsp?LoadFlag=4","",sFeatures);
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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cProposalNo=fmQuery.ProposalNo.value;
  tUWIdea = fmQuery.UWIdea.value;
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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  //initPolGrid();
  document.getElementById("fmQuery").submit(); //提交
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
  showModalDialog("./ProposalDuty.jsp",window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=13cm");

}

//Click事件，当点击“暂交费信息”按钮时触发该函数
function showFee()
{
  //下面增加相应的代码
  showModalDialog("./ProposalFee.jsp",window,"status:no;help:0;close:0;dialogWidth=16cm;dialogHeight=8cm");

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
	var PrtNo = fmQuery.PrtNoHide.value;
	var mOperate = fmQuery.Operator.value;

	var strsql = "";
//	strsql = "select LCGrpPol.GrpProposalContNo,LCGrpPol.prtNo,LCGrpPol.GrpName,LCGrpPol.RiskCode,LCGrpPol.RiskVersion,LCGrpPol.ManageCom from LCGrpPol,LCGUWMaster where 1=1 "
//			 	 + " and LCGrpPol.AppFlag='0'"
//				 + " and LCGrpPol.ApproveFlag in('2','9') "
//				 + " and LCGrpPol.UWFlag in ('2','3','5','6','8','7')"
//				 + " and LCGrpPol.contno = '00000000000000000000'"				          //自动核保待人工核保
//				 + " and LCGrpPol.PrtNo = '"+PrtNo+"'"
//				 + " and LCGrpPol.GrpPolNo = LCGUWMaster.GrpPolNo"
//				 + " and (LCGUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') or LCGUWMaster.appgrade is null)"
//				 + " order by LCGrpPol.makedate ,LCGrpPol.maketime" ;
	strsql = "select GrpContNo,PrtNo,GrpName,RiskCode,UWFlag,ManageCom,GrpPolNo from LCGrpPol  where 1=1 and GrpContNo='"+GrpContNo+"'";

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
	var strSQL = "select GrpContNo,PrtNo,ManageCom,agenttype,AgentCom,AgentCode,AgentGroup,AppntNo,VIPValue,BlacklistFlag,lcgrpcont.GrpName from lcgrpcont,LDGrp where grpcontno='"+GrpContNo+"'"
		+" and lcgrpcont.AppntNo = LDGrp.CustomerNo " ;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
if (turnPage.strQueryResult) {	
	fmQuery.GrpContNo.value = arrSelected[0][0];
	fmQuery.PrtNo.value = arrSelected[0][1];
	fmQuery.ManageCom.value = arrSelected[0][2];
	fmQuery.SaleChnl.value = arrSelected[0][3];
	fmQuery.AgentCom.value = arrSelected[0][4];
	fmQuery.AgentCode.value = arrSelected[0][5];
	fmQuery.AgentGroup.value = arrSelected[0][6];

	fmQuery.AppntNo.value = arrSelected[0][7];
	fmQuery.VIPValue.value = arrSelected[0][8];
	fmQuery.BlacklistFlag.value = arrSelected[0][9];
	fmQuery.AppntName.value = arrSelected[0][10];
}
	initSendTrace();
	
	
  return true;
}
function getcodedata()
{

	var sql="  select code,codename from ldcode where codetype='guwstate'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    fmQuery.all("GUWState").CodeData=tCodeData;
	
}
function getcodedata1()
{

	var sql="  select code,codename from ldcode where codetype='cooperate'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
   // fmQuery.all("cooperate").CodeData=tCodeData;
	
}

function getcodedata2()
{

	var sql="  select code,codename from ldcode where codetype='riskgrade'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    fmQuery.all("riskgrade").CodeData=tCodeData;
	
}

function getcodedata3()
{

	var sql="  select code,codename from ldcode where codetype='appcontract'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    //fmQuery.all("appcontract").CodeData=tCodeData;
	
}

function getcodedata4()
{

	var sql="  select code,codename from ldcode where codetype='uwupreport'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    fmQuery.all("uwUpReport").CodeData=tCodeData;
	
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
	document.getElementById("fmQuery").submit();

	}


/*********************************************************************
 *  查询团体单下自动核保未通过的个人单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryPol()
{
	var GrpProposalContNo = fmQuery.GrpProposalContNo.value;
	var mOperate = fmQuery.Operator.value;
	var strsql = "";

	// 初始化表格
	showDiv(divPerson,"false");
	initPolBox();
	//initPolGrid();
	// 书写SQL语句

	strsql = "select LCPol.ProposalNo,LCPol.AppntName,LCPol.RiskCode,LCPol.RiskVersion,LCPol.InsuredName,LCPol.ManageCom from LCPol where 1=1 "
			  + "and LCPol.GrpPolNo = '"+GrpProposalContNo+"'"
 	    	  + " and LCPol.AppFlag='0'"
         	  + " and LCPol.UWFlag in ('3','5','6','8','7')"         //自动核保待人工核保
			  + " and LCPol.contno = '00000000000000000000'"
			  + " order by LCPol.makedate ";

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
  if(GrpProposalContNo == fmQuery.GrpMainProposalNo.value)
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
	document.getElementById("fmQuery").submit();
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
	document.getElementById("fmQuery").submit();
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
	var flag = fmQuery.UWState.value;
	var tUWIdea = fmQuery.UWIdea.value;

	if (flag == "0"||flag == "1"||flag == "4"||flag == "9")
	{
	   //showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		fmQuery.action = "./UWManuNormChk.jsp";
		document.getElementById("fmQuery").submit();
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
	
	//  var tt = GrpGrid.getSelNo()-1;
	//if (tt <0)
	//{
	//	    
	//      alert("请先确认是否所有的团单险种的结论已下！");
	//      
	//      return;
	//}
	var tUWIdea = fmQuery.UWIdea.value;
  var tUpReport = fmQuery.uwUpReport.value;                //上报标志
	if(tUpReport!="1" && tUpReport!="4"&&tUpReport!="0")
     {
     	
      alert("请先选择核保流向!");
    	return ;
     }else{
       if(tUpReport=="4"){
      //校验流向的逻辑正确性 如果第一次核保 则不允许返回下级
        var tSql = "select missionprop11 from lwmission where activityid='0000002004' and missionprop1 = '"+fmQuery.GrpContNo.value+"' ";
        var tReportType =easyExecSql(tSql);
        if(tReportType!="1"){
           //此保单不为上报类型 所以不能返回下级
           alert("在此之前没有上报,无法返回下级！");
           return false;
          }
        }
     }
    if(tUpReport=="0")
    {
    	if(fmQuery.riskgrade.value=="")
    	{
    		alert("请选择风险级别!");
    		return;
    	}
    	
    	//if(fmQuery.appcontract.value=="")
    	//{
    	//	alert("请选择是否有附加协议!");
    	//	return;
    	//}
    }
  //  if(tUWIdea == "")
 //    {
      
  //    //alert("请先录入承保核保意见!");
  //    return ;
  //  }
	 if(checkQuestion()==false) return false; 
	 if(checkUWGrade()==false) return false;
	 if(flag==1)
  {
  	fmQuery.YesOrNo.value = "Y";
  }
  if(flag ==2)
  {
  	fmQuery.YesOrNo.value = "N";
  }
	var cGrpContNo=fmQuery.GrpContNo.value;
  //var flag = fmQuery.GUWState.value;
  
	//var tUWIdea = fmQuery.GUWIdea.value;
	//alert("flag:"+flag);

	if( cGrpContNo == "" || cGrpContNo == null )
	{
		alert("请先选择一个团体投保单!");
	    cancelchk();
		return ;
	}

    k++;

    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000002010'"
    						+" and missionid = '"+MissionID+"'"
    						;

    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnPage.strQueryResult) {
    	alert("未查到工作流SubMissionID");
    	 fmQuery.SubMissionID.value = "";
    	return;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
    fmQuery.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
    
  //判断是否发了体检通知书，如果发了是否已经回复
    //if(check(cGrpContNo)=="2")
    //{
    //	alert("还有体检通知书未回复！");
    //	return;
    //}
    //if(check(cGrpContNo)=="1")
    //{
    //	alert("还有体检通知书未打印！");
    //	return;
    //}
	//判断是否做过个别分红操作
	  
	  //checkPerFH();
	  if(tBonusFlag=="1")
	  {
	  	alert("该投保单下存在个别分红的险种，请在整单确认前对该险种个别分红！");
	  	return;
	  }

		fmQuery.WorkFlowFlag.value = "0000002010";
		fmQuery.MissionID.value = MissionID;
		
		//fmQuery.SubMissionID.value = SubMissionID;
		//showModalDialog("./UWGrpManuNormMain.jsp?GrpContNo="+cGrpContNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
		 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
		lockScreen('lkscreen');  
		fmQuery.action = "./UWConfirm.jsp";
  	document.getElementById("fmQuery").submit(); //提交
	
}

function checkQuestion(){
   var strSql="	select count(1) from lcgrpissuepol	where 1 = 1"
             +" and ProposalGrpContNo = '"+GrpContNo+"' and BackObjType = '1' "
             +" and ReplyMan is null and ReplyResult is null";
   var tCount = easyExecSql(strSql);
   if (tCount>0){
       alert("还有未回复的问题件！请点击[问题件录入完毕],进入问题件修改岗回复问题件");
       return false;
   }
   return true;
}

function checkUWGrade(){
  var tMMSql="select max(code),min(code) from ldcode where codetype = 'uwgrppopedom'";
  var arrResult = easyExecSql(tMMSql);
  var tMaxGrade = arrResult[0][0];
  var tMinGrade = arrResult[0][1];
  var tUSerSql = " select uwpopedom from lduwuser where usercode = '"+operator+"' and uwtype ='2'";
  var tUserGrade = easyExecSql(tUSerSql);
  //alert("tMaxGrade:"+tMaxGrade+"tMinGrade:"+tMinGrade+"tUserGrade:"+tUserGrade);
  var tUWUpReport = fmQuery.uwUpReport.value;
  if(tUWUpReport=="1"){
     //上报
     if(tUserGrade==tMaxGrade){
        if(!confirm("您已经是最高级别核保师！是否继续上报？")) return false;
     }
  }else if (tUWUpReport=="4"){
     if(tUserGrade==tMinGrade){
        if(!confirm("您是最低级别核保师！是否继续返回下级？")) return false;
     }
  }
  return true;
}
/*********************************************************************
 *  校验体检通知书是否回复
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function check(grpcontno)
{
	var strSql = "select distinct 1 From loprtmanager where otherno='"+grpcontno+"'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var notice = easyExecSql(strSql);//判断是否发体检通知书
	if(notice==null)
	{
		return 0;//没有发放通知书
	}
	//alert(1);
	//alert(notice);
  strSql = "select distinct 1 From loprtmanager where otherno='"+grpcontno+"' and stateflag='0'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var num = easyExecSql(strSql);//已经发放通知书但没有打印
	if(num!=null)
	{
		return 1;
	}
	//alert(2);
	//alert(num);
	strSql = "select count(*) From loprtmanager where StandbyFlag3='"+grpcontno+"'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var printno = easyExecSql(strSql);//已经打印
	strSql = "select count(*) From lcpenoticeresult where grpcontno='"+grpcontno+"'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var result = easyExecSql(strSql);//已经回复
	//alert(3);
	//alert(result);
	//alert(printno);
	if(result[0][0]==printno[0][0])
	{
		return 0;//没有全部回复
	}else{
	//alert(4);
	//alert(result);
	//alert(printno);
	return 2;//正常
}
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
	var cGrpProposalContNo = fmQuery.GrpContNo.value;  //团体保单号码	
	if(cGrpProposalContNo==""||cGrpProposalContNo==null)
	{
  		alert("请先选择一个团体投保单!");
  		return ;
         }
	showInfo=window.open("../appgrp/GrpQuestInputMain.jsp?GrpContNo="+cGrpProposalContNo+"&Flag=9","",sFeatures);
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
	
	var cGrpContNo = fmQuery.GrpContNo.value;  //团单投保单号码
	//alert(cGrpContNo);
	if(cGrpContNo==""||cGrpContNo==null)
	{
  		alert("请先选择一个团体主险投保单!");
  		return ;
    }
 
	window.open("./GrpQuestQueryMain.jsp?ProposalNo1="+cGrpContNo+"&Flag="+cflag,"",sFeatures);
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
	fmQuery.GUWState.value = "";
	fmQuery.GUWIdea.value = "";
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

	var prtNo=fmQuery.PrtNo.value;
	
	if(prtNo==""||prtNo==null)
	{
	  	alert("请先选择一个团体投保单!");
  		return ;
  	}
  	
  	
  	//var tsql="select subtype From es_doc_main where doccode='"+prtNo+"' order by subtype";
 	//			var crr = easyExecSql(tsql);
 	//			var tsubtype="";
 	//			//alert(crr);
 	//			if(crr!=null)
 	//			{
 	//			if(crr[0][0]=="1000")
 	//			{
 	//			 tsubtype="TB1002";
 	//			}else{
 	//			 tsubtype="TB1003";
 	//			}
 	//		}else{
 	//		 tsubtype="TB1002";
 	//	}
 	var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+prtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	window.open("../sys/ProposalEasyScan.jsp?BussType=TB&BussNoType=12&SubType="+SubType+"&prtNo="+prtNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
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

function GrpContReasonQuery(cGrpContNo){
	 
	window.open("./GroupUWReason.jsp?GrpContNo=" + cGrpContNo,"",sFeatures); 
}

function showGrpCont()
{
     var cGrpContNo=fmQuery.GrpContNo.value;
    //var newWindow=window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1"); 
    window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&prtNo="+cGrpContNo+"&polNo="+cGrpContNo+"&GrpContNo="+cGrpContNo,"window1",sFeatures);
}


//团单既往询价信息
 function showAskApp()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
      //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	  	var name='提示';   //网页名称，可为空; 
	  	var iWidth=550;      //弹出窗口的宽度; 
	  	var iHeight=250;     //弹出窗口的高度; 
	  	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	  	showInfo.focus();
	  	//[end]
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendBodyCheckChk.jsp";
	  	
	    document.getElementById("fmQuery").submit();
	   
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
      //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	  	var name='提示';   //网页名称，可为空; 
	  	var iWidth=550;      //弹出窗口的宽度; 
	  	var iHeight=250;     //弹出窗口的高度; 
	  	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	  	showInfo.focus();
	  	//[end]
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendRReportChk.jsp?GrpContNo="+cGrpContNo;
	  	
	    document.getElementById("fmQuery").submit();
	   
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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  
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
		window.open("../uwgrp/GrpUWHistoryContMain.jsp?GrpContNo="+cGrpContNo,"window2",sFeatures);
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
  

  window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=23&polNo="+cpolNo, "",sFeatures);  	  
}


/*********************************************************************
 *  保险计划变更结论录入显示
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function showChangeResultView()
{
	fmQuery.ChangeIdea.value = "";
	//fm.PolNoHide.value = fm.ProposalNo.value;  //保单号码
	//divUWResult.style.display= "none";
	divChangeResult.style.display= "";	
}


//显示保险计划变更结论
function showChangeResult()
{

	var cContNo = fmQuery.GrpContNo.value;
	
	cChangeResult = fmQuery.ChangeIdea.value;
	
	if (cChangeResult == "")
	{
		alert("没有录入结论!");
	}
	else
	{
		
       
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
 
  	fmQuery.action = "./GrpUWChangPlanChk.jsp";
  	document.getElementById("fmQuery").submit(); //提交
  }	

}


//隐藏保险计划变更结论<img src="">
function HideChangeResult()
{
	//divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	//fmQuery.uwstate.value = "";
	//fmQuery.UWIdea.value = "";
	//fmQuery.UWPopedomCode.value = "";			
}


//发首期交费通知书
function SendFirstNotice()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fmQuery.GrpContNo.value;
 
  cOtherNoType="01"; //其他号码类型
  cCode="57";        //单据类型
  
  if (cProposalNo != "")
  {
  	showModalDialog("./GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
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
	
	  var strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp1 = '" + PrtNo +"'"
				
				 + " and LWMission.ActivityID = '0000002101'"
				 + " and LWMission.ProcessID = '0000000004'"
				 + " and LWMission.MissionID = '" +MissionID +"'";
		
  
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
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fmQuery.GrpContNo.value;
 
  if (cProposalNo != "")
  {
  	fmQuery.action = "./UWSendNoticeChk.jsp";
  	document.getElementById("fmQuery").submit();
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
	
	var strSql = "select * from LCcUWMaster where ContNo='" + cContNo + "' and ChangePolFlag ='1'";
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

	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
					+ " and otherno = '"+GrpContNo+"'"
					+ " and othernotype = '2'"
					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+GrpContNo+"')" 
					;
					
					//alert(tSql);
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

  //判断是否查询成功
  if (turnPage.strQueryResult) {
		fmQuery.SendFlag.value =arrSelected[0][0];
		fmQuery.SendUWFlag.value =arrSelected[0][1];
		fmQuery.SendUWIdea.value =arrSelected[0][2];

		DivLCSendTrance.style.display="";
	}

	if(fmQuery.SendFlag.value == '0')
	{
		divUWSave.style.display = "";
		divUWAgree.style.display = "none";
	}	
	else if(fmQuery.SendFlag.value == '1')
	{
		//divUWAgree.style.display = "";
		//divUWSave.style.display = "none";
		tSql = "select passflag,uwidea from lcgcuwmaster where 1=1 "
					+ " and grpcontno = '"+GrpContNo+"'" 
					;
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
		fmQuery.GUWState.value = arrSelected[0][0];
		fmQuery.GUWIdea.value = arrSelected[0][1];
	}
}
/*
  分保按纽
*/

function tt(cGrpContNo)
{

	showInfo=window.open("./distributeMain.jsp?GrpContNo=" + cGrpContNo,"Groupdistribute",sFeatures);
}


function getfee(parm1,parm2)
{
	var grppolno=fmQuery.all(parm1).all('GrpGrid7').value;
         var strSQL="select  Case When sum(prem) Is Null Then 0 Else sum(prem) End,Case When  round(sum(prem/floatrate),0) Is Null Then 0 Else round(sum(prem/floatrate),2) End,Case When round(sum(prem)/sum(prem/floatrate),4) Is Null Then 0 Else round(sum(prem)/sum(prem/floatrate),4) End , max(currname) from lcduty,ldcurrency "
         + "where polno in (select polno from lcpol where grppolno='"+grppolno+"') and currcode = (select currency from lcpol where 1 = 1 and polno = lcduty.polno)  ";	
  	turnPage.queryModal(strSQL,GrpPolFeeGrid);
  	
  var tSql = "select passflag ,uwidea from lcguwmaster where grppolno = '"+grppolno+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
		fmQuery.GUWState.value = arrSelected[0][0];
		fmQuery.GUWIdea.value = arrSelected[0][1];
  
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
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}

/*********************************************************************
 *  初始化代码框
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInfo1()
{
		showOneCodeName('comcode','ManageCom','ManageComName');
		showOneCodeName('agenttype','SaleChnl','SaleChnlName');
		showOneCodeName('comcode','AgentCom','AgentComName');
		showOneCodeName('vipvalue','VIPValue','VIPValueName');
		showOneCodeName('blacklistflag','BlacklistFlag','BlacklistFlagName');
		showOneCodeName('uwupreport','SendFlag','SendFlagName');
		showOneCodeName('contuwstate','SendUWFlag','SendUWFlagName');
		//alert(GrpContNo);
		var arrQueryResult = easyExecSql("select ConferNo,SignReportNo,AgentConferNo from lcgrpcont where grpcontno='" + GrpContNo + "'", 1, 0);
		if(arrQueryResult!=null)
		{
			//fmQuery.cooperate.value=arrQueryResult[0][0];
			fmQuery.riskgrade.value=arrQueryResult[0][1];
			//fmQuery.appcontract.value=arrQueryResult[0][2];
			
		}

}


//既往投保及赔付情况查询
function UWPastInfo(tGrpContNo){
     window.open("./UWPastInfoMain.jsp?GrpContNo="+tGrpContNo,"window1",sFeatures);
}
/*********************************************************************
 *  对集体保单险种下结论
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function grpPolCommit()
 {
 	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
		    showInfo.close();
	      alert("请选择一条任务");
	      
	      return;
	}
	
  cProposalNo=GrpGrid.getRowColData(selno,7);
  fmQuery.temriskcode.value=GrpGrid.getRowColData(selno,4);
  fmQuery.GrpPolNo.value = cProposalNo;
  
  var flag = fmQuery.GUWState.value;
	var tUWIdea = fmQuery.GUWIdea.value;
	if(tUWIdea=="")
	{
		showInfo.close();
		alert("请输入团体险种单核保意见!");
		return;
	}
  
  	if (flag == null || flag == "")
	{
		alert("请先选择核保结论!");
	    cancelchk();
		return ;
	}
	

  if (cProposalNo != "")
  {
  	fmQuery.action = "./GrpPolCommitChk.jsp";
  	document.getElementById("fmQuery").submit();
  }
 
}
function queryProposal(){
	  
	var cContNo = fmQuery.GrpContNo.value;
	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	
	if(arr!=null){
		fmQuery.AppntNo.value=arr[0][0];
	}
	//alert(fmQuery.AppntNo.value);
    window.open("./GroupProposalQueryMain.jsp?AppntNo="+fmQuery.AppntNo.value,"window1",sFeatures);

}
function queryNotProposal(){
  
  var cContNo = fmQuery.GrpContNo.value;
	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	if(arr!=null){
		fmQuery.AppntNo.value=arr[0][0];
	}
	//alert(fmQuery.AppntNo.value);

	window.open("./GroupNotProposalQueryMain.jsp?AppntNo="+fmQuery.AppntNo.value,"window1",sFeatures);

}

function grpAddFee()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var tGrpContNo = fmQuery.GrpContNo.value;
 
  //var tContNo = fmQuery.ContNo.value;
  //var tPolNo = fmQuery.PolNo.value;
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
 	var tGrpContNo = fmQuery.GrpContNo.value;
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
 	var tGrpContNo = fmQuery.GrpContNo.value;
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
 	var tGrpContNo = fmQuery.GrpContNo.value;
 	var riskcode=GrpGrid.getRowColData(selno, 4);
 	if (tGrpContNo != "")
  {
	window.open("./GrpRiskElenemtMain.jsp?GrpContNo="+tGrpContNo+"&riskcode="+riskcode  ,"window1",sFeatures);
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
	
   var tGrpPolNo=GrpGrid.getRowColData(selno,7);
    	if (tGrpPolNo != "")
  {
	window.open("./GrpFloatRateMain.jsp?GrpPolNo=" + tGrpPolNo ,"window1",sFeatures);
  }
   
 }
 /*********************************************************************
 *  团单核保问题件的录入完毕
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function GrpQuestInputConfirm()
{
	var StrSQL = "select submissionid from lwmission where 1=1 "
								+ " and missionid = '"+ MissionID +"'"
								+ " and activityid = '0000002007'"
								;
	turnPage.strQueryResult  = easyQueryVer3(StrSQL, 1, 0, 1);
	  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("工作流节点生成失败！");
    return "";
  }
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	
	fmQuery.WorkFlowFlag.value = "0000002007";
	fmQuery.SubMissionID.value = arrSelected[0][0];
	
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	lockScreen('lkscreen');  
	fmQuery.action = "./GrpQuestInputConfirm.jsp";
  document.getElementById("fmQuery").submit(); //提交
}
//手续费录入
function pp(cGrpContNo)
{
	
	showInfo=window.open("./FeeMain.jsp?GrpContNo=" + cGrpContNo,"Feedistribute",sFeatures);
}
//保障计划查询
function showContPlan()
{
	//alert(GrpContNo);
	window.open("../appgrp/ContPlan.jsp?GrpContNo="+GrpContNo+"&LoadFlag=11","",sFeatures);
}

function showManaFee()
{
	showInfo=window.open("./ContFee.jsp?GrpContNo="+GrpContNo+"&LoadFlag=11","",sFeatures);
}
function showBonus()
{
	var AppntNo=fmQuery.AppntNo.value;
	showInfo=window.open("./GrpBonus.jsp?GrpContNo="+GrpContNo+"&LoadFlag="+AppntNo+"","",sFeatures);
}

function showSpecInfo()
{
	window.open("./SpecInp.jsp?GrpContNo="+GrpContNo+"&LoadFlag=11","",sFeatures);
}

function ManageFeeCal()
{
	
var strUrl = "../appgrp/CalMangeFeeMain.jsp?grpcontno="+ fmQuery.GrpContNo.value
   showInfo=window.open(strUrl,"管理费计算","width=600, height=250, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}


function showRealFee()
{
	window.open("./RealFeeInp.jsp?GrpContNo="+GrpContNo+"","管理费金额","width=300, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}
//如果有个别分红的险种，提示做个别分红处理
function fenHong()
{
   var arr=new Array();
   strSQL="select riskcode from lcgrppol where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
   arr = easyExecSql( strSQL );
   if(arr)
   {
  	   alert("该投保单下存在个别分红的险种，请在整单确认时对该险种个别分红！");

 //	     fmQuery.scan.disabled=true;
 // 	   fmQuery.autoCheck.disabled=true;
 // 	   fmQuery.grpDetails.disabled=true;
 // 	   fmQuery.perAutoRea.disabled=true;
 // 	   fmQuery.perAutoCheck.disabled=true;
 // 	   fmQuery.grpPolReason.disabled=true;
 // 	   fmQuery.perAutoInfo.disabled=true;
 // 	   
 // 	   fmQuery.PE.disabled=true;
 // 	   fmQuery.RealFee.disabled=true;
 // 	   fmQuery.grpManageFee.disabled=true;
 // 	   fmQuery.SpecInfo.disabled=true;
 // 	   
 // 	   fmQuery.grpQuestionInput.disabled=true;
 // 	   fmQuery.questInputConfirm.disabled=true;
 // 	   fmQuery.distriContract.disabled=true;
 // 	   fmQuery.FeeContract.disabled=true;
 // 	   fmQuery.manageFeeQuery.disabled=true;
 // 	  
 // 	   fmQuery.ok.disabled=true;
 // 	   fmQuery.isOk.disabled=true;
 // 	   fmQuery.isNotOk.disabled=true;
 // 	   fmQuery.grpPolUtility.disabled=true;     	   
   }
   
}

//校验是否做过个别分红
function checkPerFH()
{
	 
	 var arr=new Array();
	 strSQL="select riskcode from lcgrppol where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
   arr = easyExecSql( strSQL );
   if(arr!=null)
   {
  	   strSQL="select riskcode from LCGrpBonus where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
       arr = easyExecSql( strSQL );
       var tflag=easyExecSql( "select * from lcgrpbonussub where grpcontno='"+GrpContNo+"'" );

       if(arr==null && tflag==null)
       {
  	     tBonusFlag="1";
  	   }else
  	   {
  	   	 tBonusFlag="0";
  	   } 	   
   }  
}
//是否显示个别分红按钮
function checkPerFHButton()
{
	 
	 var arr=new Array();
	 strSQL="select riskcode from lcgrppol where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
   arr = easyExecSql( strSQL );
   if(arr!=null)
   {	   
  	  //fmQuery.perFH.disabled=false;
  	  //fmQuery.FeeContract.disabled=false; 
  	  //fmQuery.distriContract.disabled=false;    	   
  	  //fmQuery.SpecInfo.disabled=false;    	   
  	  fmQuery.perAutoRea.disabled=false;    	   
   }else
   	{
   	  //fmQuery.perFH.disabled=true;     	   	
  	  //fmQuery.FeeContract.disabled=true;     	   
  	  //fmQuery.distriContract.disabled=true;    	   
  	  //fmQuery.SpecInfo.disabled=true;    	
  	  //fmQuery.perAutoRea1.disabled=true;    	   
   	}  
}

//强制转入再保险工作流    add by sujd 2007-08-17
function ReLife(cGrpContNo)
{
	//把确认按钮取消
	fmQuery.ok.disabled="true";
	fmQuery.grpPolUtility.disabled="true";

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fmQuery.action = "./RelifeSubmitSave.jsp?GrpContNo="+cGrpContNo;
  document.getElementById("fmQuery").submit(); //提交
}

//控制人工核保界面按钮的明暗显示
function ctrlButtonDisabled(tContNo,LoadFlag){

 //修改为在Function里直接执行函数，返回结果集合(二维数组:第一列为按钮名称，第二列为disabled属性)。
    try
    {
	    var tarrStr = easyExecSql("select ctrlGrpSigUWButton('"+tContNo+"','"+LoadFlag+"') from dual");
	  // prompt('',tarrStr);
	    if(tarrStr!=null)
	    {
	    	for(var i=0; i<tarrStr.length; i++)
	    	{
	    		//alert(tarrStr[i][0]+":"+tarrStr[i][1]);
	    		try {
	    			if(tarrStr[i][1]==0)
	    			{
	    			//	prompt('',"fm.all('"+tarrStr[i][0]+"').disabled=true");
	    				eval("fmQuery.all('"+tarrStr[i][0]+"').disabled=true ");
	    				
	    			}
	    			else
	    			{  				
	    		//		prompt('111',"fm.all('"+tarrStr[i][0]+"').disabled=true");
	    				eval("fmQuery.all('"+tarrStr[i][0]+"').disabled=false");
	    			}
	    		} 
	    		catch(e) {
	    			//alert(e);
	    			}
	    		 
	    	}
	    }
    }
    catch(ex)
    {
    
    }     	
}