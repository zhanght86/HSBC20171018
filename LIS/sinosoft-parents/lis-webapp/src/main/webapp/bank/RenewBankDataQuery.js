//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();

var tResourceName="bank.RenewBankDataQuerySql";
var tResourceSQL1="RenewBankDataQuerySql1";
var tResourceSQL2="RenewBankDataQuerySql2";
var tResourceSQL3="RenewBankDataQuerySql3";
var tResourceSQL4="RenewBankDataQuerySql4"; 

//简单查询
function easyQueryClick()
{
	// 初始化表格
	initCodeGrid();

	var prtno = document.all('PrtNo').value;
	var	addsql="";
	if(prtno != null && trim(prtno) != "")
		//addsql = " and exists(select 1 from lccont where contno = a.otherno and prtno = '" + prtno + "') ";
		addsql = wrapSql(tResourceName,tResourceSQL1,[prtno]); 
	// 书写SQL语句
	//var strSQL = "select (select prtno from lccont where contno = a.otherno),"
	//					 +"a.otherno,trim(a.accname),"
	//					 + "(select riskcode from lcpol where contno=a.otherno and polno=mainpolno and appflag='1'),"
	//					 + "a.startpaydate,"
	//					 + "(decode(bankonthewayflag,'1','3',decode((select state from lyrenewbankinfo where serialno = b.serialno and getnoticeno = b.getnoticeno and contno = b.contno),'0','2','1'))),"
	//					 + "b.confirmoperator,b.makedate,"
	//					 + "(select undooperator from lyrenewbankinfo where serialno = b.serialno and getnoticeno = b.getnoticeno and contno = b.contno and state = '1'),"
	//					 + "(select modifydate from lyrenewbankinfo where serialno = b.serialno and getnoticeno = b.getnoticeno and contno = b.contno and state = '1') "
	//					 + "from ljspay a ,lyrenewbankinfo b "
	//					 + "where a.getnoticeno = b.getnoticeno(+) "
	//					 + "and a.otherno = b.contno(+) "
	//					 + "and a.othernotype = '2' "
	//					 + "and a.bankaccno is not null "
	//					 + "and a.paytypeflag = '1' "
	//					 //+ "and a.riskcode in (select riskcode from lmriskrnew) "
	//					 + addsql 
	//					 + getWherePart('a.otherno', 'ContNo') 
	//					 + getWherePart('a.managecom', 'ManageCom','like','0') 
	//					 + getWherePart('a.accname', 'AppntName') 
	//					 + getWherePart('a.startpaydate','StartDate','>=','0')
	//					 + getWherePart('a.startpaydate','EndDate','<=','0') 
	//					 + " order by 1,2,8 ";
	//alert(strSQL);
	var strSQL = wrapSql(tResourceName,tResourceSQL2,[addsql,fm.ContNo.value,fm.ManageCom.value,fm.AppntName.value,fm.StartDate.value,fm.EndDate.value]); 
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有查到数据：\n请确认转帐状态，本界面只能查询已作转帐确认但未报盘的有效续保保单！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CodeGrid;    
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function queryAgentCom()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}	
    if(document.all('AgentCom').value == "")	{  
	  var newWindow = window.open("../sys/AgentComQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentComQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  	  
	}	
	if(document.all('AgentCom').value != "")	 {
		var cAgentCom = fm.AgentCom.value;  //保单号码	
		var strSql = "select AgentCom,Name from LACom where AgentCom='" + cAgentCom +"'";
    	var arrResult = easyExecSql(strSql);
    	if (arrResult != null) {
      		alert("查询结果:代理机构编码:["+arrResult[0][0]+"],代理机构名称:["+arrResult[0][1]+"]");
      	}else{
      		alert("没有查询到此代理机构信息!");
      	}  
	}	
}

function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCom.value = arrResult[0][0];
  }
}

//提交，保存按钮对应操作
function submitForm()
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
	document.all('undo').disabled = true;
  document.getElementById("fm").submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	document.all('undo').disabled = false;
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
