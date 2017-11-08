//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var turnPage = new turnPageClass();

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
  //首先检验录入框
  if (!verifyInput()) return false; 
	
	var fiscalyear = document.all('FiscalYear').value ;
	//tongmeng 2007-11-21 modify
	//去除对帐户当前金额为0不做结息的条件
	// 书写SQL语句
	var strSQL = "select a.GrpContNo,a.GrpName,a.managecom,a.cvalidate,a.prtno " 
						 + "from lcgrppol a " 
						 + "where appflag = '1' "
						 + "and exists (select 1 from lcinsureacc where grpcontno = a.grpcontno and state not in ('1','4') "
						 //+ " and insuaccbala > 0 "
						 + " and baladate >= '"+(fiscalyear-1)+"-12-31' and baladate < '"+fiscalyear+"-12-31') "
						 + getWherePart('a.GrpContNo','GrpContNo') 
						 + getWherePart('a.riskcode','RiskCode')
						 + getWherePart('a.cvalidate','BDate','>=','0')
						 + getWherePart('a.cvalidate','EDate','<=','0')
						 + " order by grpcontno";
	//alert(strSQL);
	turnPage.queryModal(strSQL, PolGrid);   
}

//提交，保存按钮对应操作
function appConfirm()
{
	//校验是否已经过了待计算的会计年度末，否则不允许计算
	var tFiscalEnd = document.all('FiscalYear').value;
	var nowDate = new Date();
	if(nowDate.getYear() > tFiscalEnd || (nowDate.getYear() == tFiscalEnd && nowDate.getMonth()+1 == "12" && nowDate.getDate() == "31"))
	{
	  var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	
		//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
		document.all('calculate').disabled = true;
		fm.action = "./GrpInterestSave.jsp";
		document.getElementById("fm").submit();
	}
	else
	{
		alert("未到该会计年度末的结息时间，不允许操作！");
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	document.all('calculate').disabled = false;
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    //执行下一步操作
  }
}

function viewErrLog()
{
		window.open("./ViewErrLogFiscalLXMain.jsp");
}