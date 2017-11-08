
//程序名称：ManageIssueDoc.js
//程序功能：
//创建日期：2006-04-02
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦

function $(element) {
  if (typeof element == 'string')
    element = document.getElementById(element);
  return element;
}

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

//Click事件，当点击“查询”图片时触发该函数
function easyQueryClick()
{
	if(!verifyInput())return;
	var comcode = document.all('ManageCom').value;
	if(comcode == null || comcode == "")
	{
		alert("管理机构不允许为空！");
		return;
	}

	if($('ScanState').value=='1' && $('AcceptState').value=='0'){
		alert("不允许已扫描未签批！");
		return;
	}
	// 书写SQL语句
	var sqlstate="";
//	var strSQL="select missionprop1,missionprop5,(select min(makedate) from es_doc_main where doccode=missionprop1),missionprop3,makedate,maketime,"
//	+"missionprop4,missionprop2,(case activityid when '0000003001' then 'N' when '0000003002' then 'Y' end) from ";
	if($('ScanState').value==1)
	{
//		strSQL=strSQL+"lbmission ";
		sqlstate=sqlstate+"1";
	}
	else
	{
//		strSQL=strSQL+"lwmission ";
		sqlstate=sqlstate+"0";
	}
//	strSQL+="a where processid='0000000010'";
	if($('AcceptState').value=='0')
	{
//		strSQL=strSQL+" and activityid='0000003001' ";
		sqlstate=sqlstate+"0";
	}
	else if($('AcceptState').value=='1')
	{
//		strSQL=strSQL+" and activityid='0000003002' ";
		sqlstate=sqlstate+"1";
	}
	else
	{
//		strSQL=strSQL+" and activityid in ('0000003001','0000003002') ";
		sqlstate=sqlstate+"2";
	}
//	strSQL=strSQL + getWherePart('missionprop3','ApplyOperator')
//	+ getWherePart('missionprop1','BussNo')
//	+ getWherePart('missionProp2','Reason')
//	+ getWherePart('makedate','ApplyDate')
//	+"and exists(select * from es_doc_main where doccode=a.missionprop1 and subtype like 'UA%' and busstype='TB'"
//	+ getWherePart('makedate','StartDate','>=','0')
//	+ getWherePart('makedate','EndDate','<=','0')+")"
//	+ getWherePart('missionprop5','ManageCom', 'like');
	
	var sqlid="QueryOtherDocSql"+sqlstate;
	var mySql=new SqlClass();
	mySql.setResourceName("easyscan.ApplyOtherDocInputSql"); //指定使用的properties文件名
    mySql.setSqlId(sqlid);//指定使用的Sql的id
    mySql.addSubPara(fm.BussNo.value);//指定传入的参数
    mySql.addSubPara(fm.Reason.value);
    mySql.addSubPara(fm.ApplyDate.value);
    mySql.addSubPara(fm.StartDate.value);
	mySql.addSubPara(fm.EndDate.value);
	mySql.addSubPara(fm.ManageCom.value);
	strSQL=mySql.getString();
	
	turnPage.queryModal(strSQL, CodeGrid);    
	//var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	if($('ScanState').value==1){
		$('divReason').style.display= "none";
	}else{
		$('divReason').style.display= "";
	}
//	divReason.style.display= "";
}           

//扫描件维护申请
function saveApply()
{
	fm.fmtransact.value = "APPLY";
	submitForm(); //提交
}

//扫描件维护撤销
function undoApply()
{
	fm.fmtransact.value = "UNDO";
	submitForm(); //提交
}        

//隐藏保险计划变更结论
function HideChangeResult()
{
	divReason.style.display= "none";
	fm.Reason.value = "";
}

//提交，保存按钮对应操作
function submitForm()
{
	if(!verifyInput())
		return ;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	$('undoApplyBtn').disabled = true;
  document.getElementById("fm").submit(); //提交
//  document.all('Content').value = "";
}

//校验函数
/*function vertifyInput()
{
	var content = trim(document.all('Content').value);
	if(content == null || content == "")
	{
		alert("请录入申请原因！");
		return false;
	}
	return true;
}*/

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	$('undoApplyBtn').disabled = false;
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    easyQueryClick();
    //执行下一步操作
  }
}
