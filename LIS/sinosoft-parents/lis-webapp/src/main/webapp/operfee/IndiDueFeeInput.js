//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var showInfo;
var flag;
var mSwitch = parent.VD.gVSwitch;
var CurrentTime;
var SubTime;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
//提交，保存按钮对应操作

var tResourceName="operfee.IndiDueFeeInputSql";
var tResourceSQL1="IndiDueFeeInputSql1";
var tResourceSQL2="IndiDueFeeInputSql2";
var tResourceSQL3="IndiDueFeeInputSql3";
var tResourceSQL4="IndiDueFeeInputSql4"; 

function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}	
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  	  
	}	
	if(document.all('AgentCode').value != "")	 {
		var cAgentCode = fm.AgentCode.value;  //保单号码	
		//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
		var strSql = wrapSql(tResourceName,tResourceSQL4,[cAgentCode]); 
    	var arrResult = easyExecSql(strSql);
    	if (arrResult != null) {
      		alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
      	} 
	}	
}

function submitForm()
{
  //提交前的检验
  if(beforeSubmit())
  {  	  
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
   
  document.getElementById("fm").submit(); //提交 
  }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
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
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
    //执行下一步操作
    resetForm();
    
  }
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    resetForm();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");       
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
  	alert("在LJSPayPersonInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false");     
}
 
//提交前的校验、计算  
function beforeSubmit()
{
	var tSel = ContGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录，再点击催收按钮。" );
		return false;
	}else
	{
        document.all('ContNo1').value = ContGrid.getRowColData(tSel-1,1);;
    }
    return true;
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

function PersonMulti()
{
	var StartDate=fmMulti.StartDate.value;
	var EndDate=fmMulti.EndDate.value;
	if(StartDate==null||StartDate==""||EndDate==null||EndDate=="")
	{
	  alert("必须录入起止日期");
	  return false;	
	}

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fmMulti").submit();	
}

function SpecPersonMulti()
{
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  fmMulti.spec.value = "1";  
  document.getElementById("fmMulti").submit();	  
}

function PersonSingle()
{
	if(beforeSubmit())
    {  
    	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		fm.action = "./IndiDueFeeQuery.jsp?DealType=" + DealType;
		showInfo.focus();
    	document.getElementById("fm").submit();
    }  
}

function easyQueryAddClick()
{
	 
    var arrReturn = new Array();
	var tSel = ContGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录，再点击返回按钮。" );
	}	
	else
	{
		var tRow = ContGrid.getSelNo() - 1;	        
        var tContNo=ContGrid.getRowColData(tRow,1);  
	    //var strSQL = "select PolNo,PrtNo,RiskCode,RiskVersion,prem,AppntName,InsuredName,PaytoDate from LCPol where "
	    //           +"paytodate<payenddate "
	    //           +" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)"
	    //           +" and (StopFlag='0' or StopFlag is null)"	                        
			//	   +" and AppFlag='1'"
			//	   +" and grppolno = '00000000000000000000'"
			//	   +" and managecom like '"+managecom+"%%'"
			//	   +" and payintv>0 "
			//	 //+" and paytodate>='" + CurrentTime + "' and paytodate<='" + SubTime +"'"			 
	    //           +" and ContNo='"+tContNo+"'"
      //             +" union "
      //             +" select PolNo, PrtNo, RiskCode, RiskVersion, prem, AppntName, InsuredName,PaytoDate from LCPol where"
      //             +" rnewflag='-1' and AppFlag = '1'"
      //             +" and ContNo='"+tContNo+"'";
     
        var strSQL = wrapSql(tResourceName,tResourceSQL1,[managecom,tContNo,tContNo]); 
       turnPage2.queryModal(strSQL, PolGrid); 
     } 	
}

function getTime(StartTime,tSubTime)
	{
	   CurrentTime = StartTime;
	   SubTime = tSubTime; 
	}

// 查询按钮
function easyQueryClick()
{  
	
	if((fm.ContNo.value == "" || fm.ContNo.value == null)&&(fm.PrtNo.value == "" || fm.PrtNo.value == null))
	{
		alert("单张催收请输入保单号或者印刷号");
		return;
	}	
	if(!rnewuw_check())
	{
		alert("该单处于续保二核且无核保结论，不可催收");
		return;
	}
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

	fm.action = "./IndiDueFeeQueryDate.jsp?DealType=" + DealType;
	document.getElementById("fm").submit();
}
//保单是否处于二核状态查询
function rnewuw_check()
{
	var check_str="";
	if(fm.PrtNo.value == "" || fm.PrtNo.value == null)
	{
	  //check_str = " select count(b.contno)  from LCRnewStateHistory a,lccont b where a.contno = b.contno and a.state ='2' and b.contno='"+fm.ContNo.value+"'";
	  check_str =  wrapSql(tResourceName,tResourceSQL2,[fm.ContNo.value]); 
	}
	else
	{
		//check_str = " select count(b.contno)  from LCRnewStateHistory a,lccont b where a.contno = b.contno and a.state ='2' and b.prtno='"+fm.PrtNo.value+"'";
	  check_str =  wrapSql(tResourceName,tResourceSQL3,[fm.PrtNo.value]); 
	}
	if(easyExecSql(check_str, 1, 1, 1)==0)
	{
		return true;
	}
	else
	{
		return false;
	}
	
}


function showRecord(strRecord)
{
	//prompt("",strRecord);
	turnPage.useSimulation   = 1;
	turnPage.strQueryResult  = strRecord;  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有符合条件的保单！");
    return ;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = ContGrid;              
  //保存SQL语句
  var strSQL="";
  turnPage.strQuerySql     = strSQL;   
  //设置查询起始位置
  turnPage.pageIndex       = 0;    
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  /*
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  */
  //必须将所有数据设置为一个数据块
  turnPage.dataBlockNum = turnPage.queryAllRecordCount;
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}

function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  }
}
function afterQuery( FlagStr, content )
{
  showInfo.close();
	 
  if (FlagStr == "Fail" )
  {             
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
  else
  { 
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
}
