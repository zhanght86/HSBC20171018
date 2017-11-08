//***********************************************
//程序名称：AppntChk.js
//程序功能：投保人查重
//创建日期：2002-11-23 17:06:57
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var cflag = "4";
var sqlresourcename = "cardgrp.AppntChkSql";
/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit(FlagStr,content)
{
  //alert(FlagStr);
	if (FlagStr == "Fail" )
	{             
	  showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

	}
	if (FlagStr == "Succ")
	{
	  showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.button5.disabled='';
		if (fm.SureFlag.value == "Union")
		{
			initForm();	
		}

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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}


// 查询按钮

function initQuery() 
{
	//fm.action = "./AppntChkQuery.jsp";
	//fm.submit();	
	//查询相关数据
	//var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
  //                + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;
  //arrResult = easyExecSql(sqlstr,1,0);
  //alert(grpPolNo);
  //alert(contNo);
  //alert(operator);
  //alert(tFlag);
  //alert(tInsuredNo);
  //alert(tInsuredNo);
  //var sql="select customerno,name,idtype,idno,othidtype,othidno,sex,birthday From ldperson where customerno='"+tInsuredNo+"'"; 
  
  var mysql1= new SqlClass();
    var sqlid1 = "AppntChkSql1";
	mysql1.setResourceName(sqlresourcename);
	mySql1.setSqlId(sqlid1);
	mysql1.addSubPara(tInsuredNo);	
	
  arrResult = easyExecSql(mysql1.getString(),1,0);
  if (arrResult == null) {  
  	alert("没有查询到相应的客户信息"); 
  	return;
  }else{
  //OPolGrid.addOne("OPolGrid");                                                  
  OPolGrid.setRowColData(0, 1, arrResult[0][0]); 
  OPolGrid.setRowColData(0, 2, arrResult[0][1]);       
  OPolGrid.setRowColData(0, 3, arrResult[0][2]);
  OPolGrid.setRowColData(0, 4, arrResult[0][3]);
  OPolGrid.setRowColData(0, 5, arrResult[0][4]);
  OPolGrid.setRowColData(0, 6, arrResult[0][5]);            
}
  //var sqlstr="select * from ldperson where Name='"+arrResult[0][1]+"' and Sex='"+arrResult[0][6]+"' and Birthday='"+arrResult[0][7]+"' and CustomerNo<>'"+arrResult[0][0]+"'"
  //              + " union select * from ldperson where IDType = '"+arrResult[0][2]+"' and IDType is not null and IDNo = '"+arrResult[0][3]+"' and IDNo is not null and CustomerNo<>'"+arrResult[0][0]+"'" ;
 
 var mysql2= new SqlClass();
    var sqlid2 = "AppntChkSql2";
	mysql2.setResourceName(sqlresourcename);
	mySql2.setSqlId(sqlid2);
	mysql2.addSubPara(arrResult[0][1]);	
	mysql2.addSubPara(arrResult[0][6]);	
	mysql2.addSubPara(arrResult[0][7]);	
	mysql2.addSubPara(arrResult[0][0]);	
	mysql2.addSubPara(arrResult[0][2]);	
	mysql2.addSubPara(arrResult[0][3]);	
	mysql2.addSubPara(arrResult[0][0]);	
 
  arrResult = easyExecSql(sqlid2.getString(),1,0);
  //alert(arrResult.length);返回几行记录
  var i=arrResult.length;
  for(j=0;j<i;j++)
  {
  PolGrid.addOne("PolGrid");  
  PolGrid.setRowColData(j, 1, arrResult[j][0]); 
  PolGrid.setRowColData(j, 2, arrResult[j][1]);       
  PolGrid.setRowColData(j, 3, arrResult[j][4]);
  PolGrid.setRowColData(j, 4, arrResult[j][5]);
  PolGrid.setRowColData(j, 5, arrResult[j][17]);
  PolGrid.setRowColData(j, 6, arrResult[j][18]);   
     
  }
}

function sendCustomerUnionIssue()
{
  
	divCustomerUnionIssue.style.display='';
	fm.button4.disabled='';	
}
function sendCustomerUnionIssue1()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
		alert( "请先选择相同的投保人" );
		return;
	}	
//增加判断是否已经添加过问题件--团单好象可以不判断，然后创建一个工作流节点
//var sqlstr="select * from lcgrpissuepol where grpcontno='"+contNo+"' and issuetype='99'" ;
// arrResult = easyExecSql(sqlstr,1,0);
// if(arrResult!=null)
// {
// 	alert("您已经发过问题件！");
// 	return;
//}
  
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
	
	//fm.action =  "./AppntChkUnionQuestInputSave.jsp";
	//alert(contNo);
	//alert(fm.CUIContent.value);
	//alert(fm.CustomerNo_OLD.value);
	//alert(fm.CustomerName.value);
	//alert(fm.CustomerNo_NEW.value);
	fm.ProposalNo.value=contNo;
	fm.submit();
}
function sendCustomerUnionIssue2()
{
	fm.action =  "./AppntChkUnionQuestInputSave2.jsp";
	fm.ProposalNo.value=contNo;
	fm.submit();
}
function customerUnion()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
		alert( "请先选择相同的投保人" );
		return;
	}	

//	divCustomerUnion.style.display="";
	
	var CustomerNo_OLD = OPolGrid.getRowColData(0, 1);
	var CustomerNo_NEW = PolGrid.getRowColData(selno, 1);
	var CustomerName = PolGrid.getRowColData(selno, 2);
	
	fm.CustomerNo_OLD.value = CustomerNo_OLD;
	fm.CustomerNo_NEW.value = CustomerNo_NEW;
	fm.CustomerName.value = CustomerName;
}

function customerUnionSubmit()
{

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
	
	fm.SureFlag.value = "Union";
	
	fm.action =  "./AppntChkCustomerUnionSave.jsp";
	fm.submit();	
}

function showCustomerUnionIssueSend()
{ 

	//strSql =" select cont from lis.ldcodemod where codetype = 'Question' and code = '99' ";
	var mysql3= new SqlClass();
    var sqlid3 = "AppntChkSql3";
	mysql3.setResourceName(sqlresourcename);
	mySql3.setSqlId(sqlid3);
	mysql3.addSubPara("1");	
	  
	var brr = easyExecSql(mysql3.getString() );
	if ( brr )
	{
		brr[0][0]==null||brr[0][0]=='null'?'0':fm.CUIContent.value  = brr[0][0];		
	}	
	
	divCustomerUnionIssue.style.display = "";
	divBtCustomerUnion.style.display = "none";
	divBtCustomerUnionSendIssue.style.display = "";

}

function showCustomerUnion()
{
	divCustomerUnionIssue.style.display = "none";
	divBtCustomerUnion.style.display = "";
	divBtCustomerUnionSendIssue.style.display = "none";	
}
function exchangeCustomerNo()
{
	var exchangeValue="";
	for(i = 0; i < fm.exchangeRadio.length; i++)
	{   
		if(fm.exchangeRadio[i].checked)
		{ 
			exchangeValue = fm.exchangeRadio[i].value;
			break;                         
		}
	}		

	if(exchangeValue == "1")
	{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
			alert( "请先选择相同的投保人" );
			return;
		}	
			
		var CustomerNo_OLD = OPolGrid.getRowColData(0, 1);
		var CustomerNo_NEW = PolGrid.getRowColData(selno, 1);
		var CustomerName = PolGrid.getRowColData(selno, 2);
		
		fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_NEW;
		
	}
	if(exchangeValue == "-1")
	{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
			alert( "请先选择相同的投保人" );
			return;
		}	
			
		var CustomerNo_NEW = OPolGrid.getRowColData(0, 1);
		var CustomerNo_OLD = PolGrid.getRowColData(selno, 1);
		var CustomerName = PolGrid.getRowColData(selno, 2);
		
		fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_NEW;
			
	}
}

function sendNotice()
{
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
		
	
	fm.action =  "./AppntNoticeSave.jsp";
	fm.submit();	
	
}