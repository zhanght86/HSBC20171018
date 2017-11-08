//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
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

//提交，保存按钮对应操作
function submitForm()
{
if(verifyInput()) 
  {
  	 if(fm.ManageCom.value==null || fm.ManageCom.value=="" )
	  {
	  		 alert("管理机构不能为空");
	  		 return false;
	  }  
   if(fm.ManageGrade.value==null || fm.ManageGrade.value=="" )
	  {
	  		 alert("机构级别不能为空");
	  		 return false;
	  } 
	  if(fm.ManageCom.value.length>fm.ManageGrade.value)
	  {
	  		 alert("你选的管理机构和级别不匹配，请重新选择");
	  		 return false;
	  }
	   
	  if(dateDiff(document.all('StartDay').value,document.all('EndDay').value,"D")>10)
    {
      if(!confirm("统计时间过长，系统可能会很慢，是否继续"))
       {
        		return;
       }
    }	
  	var i = 0;
	  var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  //fm.hideOperate.value=mOperate;
	  //if (fm.hideOperate.value=="")
	  //{
	  //  alert("操作控制数据丢失！");
	  //}
	  //showSubmitFrame(mDebug);

	  document.getElementById("fm").submit(); //提交
	  showInfo.close();
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

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //执行下一步操作
  }
}

//打印保单交接清单
function fnBillPrint()
{
	fm.action="../f1print/LCPolBillF1PSave.jsp";
	fm.target="f1print";
	fm.fmtransact.value="PRINT";
	submitForm();	
}


//打印保单交接清单
function fnBillPrintVIP()
{
	fm.action="../f1print/LCPolBillF1PSave.jsp";
	fm.target="f1print";
	fm.fmtransact.value="PRINT||VIP";
	submitForm();	
}


//打印重新打印保单交接清单
function fnBillPrintRe()
{
	fm.action="../f1print/LCPolBillF1PSave.jsp";
	fm.target="f1print";
	fm.fmtransact.value="PRINT||RE";
	submitForm();	
}

//保单补打
function fnBillPrintLR()
{
	fm.action="../f1print/LCPolBillF1PSave.jsp";
	fm.target="f1print";
	fm.fmtransact.value="PRINT||LR";
	submitForm();	
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





//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
    mOperate="DELETE||MAIN";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("您取消了删除操作！");
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

function queryCom()
{
	showInfo = window.open("../certify/AgentTrussQuery.html");
	}
	
	
	function afterQuery(arrResult)
{
  if(arrResult!=null)
  { 	
	    fm.AgentGroup.value = arrResult[0][3];
	    
	}
}

//查询代理人的方式
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码	
//		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
   
		var sqlid1="LCPolBillInputSql0";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.LCPolBillInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(cAgentCode);//指定传入的参数
		var strSql=mySql1.getString();
		
		var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) 
    {
			fm.AgentCode.value = arrResult[0][0];
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			fm.AgentCode.value="";
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
