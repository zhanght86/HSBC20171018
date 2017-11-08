//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();
var tResourceName="bank.RenewBankConfirmSql";
var tResourceSQL1="RenewBankConfirmSql1";
var tResourceSQL2="RenewBankConfirmSql2";
var tResourceSQL3="RenewBankConfirmSql3";
var tResourceSQL4="RenewBankConfirmSql4"; 

//简单查询
function easyQueryClick()
{
	// 初始化表格
	initCodeGrid();

	var prtno = document.all('PrtNo').value;
	var	addsql = "";
	if(prtno != null && trim(prtno) != "")
		//addsql = " and exists (select 1 from lccont where contno = a.otherno and prtno = '" + prtno + "') ";
		 addsql = wrapSql(tResourceName,tResourceSQL1,[prtno]); 
	
	var salechnl = document.all('SaleChnl').value;
	if(salechnl != null && trim(salechnl) != "")
		//addsql += " and exists (select 1 from lccont where contno = a.otherno and salechnl = '" + salechnl + "') ";
		addsql = wrapSql(tResourceName,tResourceSQL2,[salechnl]); 
		
	// 书写SQL语句
//	var strSQL = "select (select salechnl from lccont where contno = a.otherno),"
//						 + "(select prtno from lccont where contno = a.otherno),"
//						 + "otherno,trim(accname),"
//						 + "(select riskcode from lcpol where contno=a.otherno and polno=mainpolno and appflag='1'),"
//						 + "startpaydate,bankaccno,getnoticeno,managecom,agentcom "
//						 + "from ljspay a "
//						 + "where othernotype = '2' and bankaccno is not null and bankonthewayflag <> '1' "
//						 + "and paytypeflag = '1' "
//						 + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') "
//						 + "and not exists(select 1 from lyrenewbankinfo where getnoticeno = a.getnoticeno and paytodate = a.startpaydate and state = '0') "
//						 + addsql 
//						 + getWherePart('otherno', 'ContNo') 
//						 + getWherePart('managecom', 'ManageCom','like','0') 
//						 + getWherePart('accname', 'AppntName') 
//						 + getWherePart('agentcom', 'AgentCom') 
//						 + getWherePart('startpaydate','StartDate','>=','0')
//						 + getWherePart('startpaydate','EndDate','<=','0') 
//						 + " order by startpaydate,managecom,1 ";
	strSQL = wrapSql(tResourceName,tResourceSQL3,[addsql,fm.ContNo.value,fm.ManageCom.value,fm.AppntName.value,fm.AgentCom.value,fm.StartDate.value,fm.EndDate.value]); 
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败，请确认：\n①该保单是否为转帐件；\n②该保单是否已催收；\n③该保单是否已终止；");
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

//提交，保存按钮对应操作
function submitForm()
{
	var tChk = CodeGrid.getChkCount();	
	//alert(tChk);
	if( tChk == null || tChk <= 0 )
	{
		alert( "请先选择记录，再点击转账确认按钮。" );
		return false;
	}else{        
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
  		document.all('confirm').disabled = true;
  		document.getElementById("fm").submit(); //提交
     }
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
		//var strSql = "select AgentCom,Name from LACom where AgentCom='" + cAgentCom +"'";
		var strSQL = wrapSql(tResourceName,tResourceSQL4,[cAgentCom]); 
	
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


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	document.all('confirm').disabled = false;
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
  
	easyQueryClick();
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
