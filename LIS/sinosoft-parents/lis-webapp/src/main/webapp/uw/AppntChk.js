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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
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
		var iHeight=250;     //弹出窗口的高度; 
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
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

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
	fm.LoadFlag.value = LoadFlag;
	
	fm.action = "./AppntChkQuery.jsp";
	fm.submit();	
}

function sendCustomerUnionIssue()
{
  var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录。" );
  return ;
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
	
	fm.action =  "./AppntChkUnionQuestInputSave.jsp";
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
	//fm.CustomerName.value = CustomerName;
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

//	strSql =" select cont from ldcodemod where codetype = 'Question' and code = '99' ";
	
	 var sqlid1="AppntChkSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.AppntChkSql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 strSql = mySql1.getString();
	  
	var brr = easyExecSql(strSql );
	if ( brr )
	{
		brr[0][0]==null||brr[0][0]=='null'?'0':fm.CUIContent.value  = brr[0][0];		
	}	
	
	divCustomerUnionIssue.style.display = "";
	divBtCustomerUnion.style.display = "none";
	divBtCustomerUnionSendIssue.style.display = "";
	divSendNotice.style.display = "";

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