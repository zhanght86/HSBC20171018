//               该文件中包含客户端需要处理的函数和事件
var arrDataSet;
var mDebug="0";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass(); 
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
  	document.getElementById("fm").submit();//提交
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

// 查询按钮
function easyQueryClick()
{	


	if(document.all('AgentCode').value == "" 
		&& document.all('ContNo').value == "" ){
		alert("合同号码、人员代码、请至少填写一项再进行查询");
		return;
	}
  if(!verifyInput()) 
  {
	 // alert(2);
  	return false;
  }
	// 初始化表格
	initPolGrid();
	// 书写SQL语句
	var strSQL = "";
	var DateSQL1 = "";
	var DateSQL2 = "";
	
	var StartDate = addDate('4',60,fm.StartDate.value); //按天加 
	var EndDate = addDate('4',60,fm.EndDate.value); //按天加 
	var sqlid="";
	//if(_DBT==DBO){
//		strSQL = "select a.otherno,(select name from ldperson where customerno = a.appntno),a.sumduepaymoney,"
//		       + " (select max(lastpaytodate) from ljspayperson where getnoticeno = a.getnoticeno) minpaytodate, "
//		       + " (select max(paycount) from ljspayperson where getnoticeno = a.getnoticeno), "
//		       + " (select codename from ldcode  where codetype = 'salechnl' and code = (select salechnl from lccont where contno = a.otherno)), "
//		       + " (case (select distinct 1 from lrascription where contno = a.otherno) when 1 then '是' else '否' end), "
//		       + " (select agentcode from lccont where contno = a.otherno), "
//		       + " (case (select 1 from lrascription where contno = a.otherno and rownum = 1) when 1 then (select distinct agentnew from lrascription where contno = a.otherno and rownum = 1) else (select distinct agentcode from lradimascription where contno = a.otherno and rownum = 1)), "
//		       + " (select riskname from lmriskapp where riskcode=a.riskcode),a.getnoticeno, "
//		       + " (select polno from lcpol where contno=a.otherno and polno=mainpolno and rownum=1) from ljspay a where 1=1 "
//		       + " and a.othernotype = '2' "
//		       + getWherePart( 'managecom','MngCom','like' )
//		       + " and not exists (select 1 from lccontstate ls where ls.contno = a.otherno and StateType = 'PayPrem' and State = '1' and enddate is null) "
//		       + " and exists (select 1 from lccontstate ls2 where ls2.contno = a.otherno and StateType = 'Available' and State = '0' and enddate is null) "
//		       + " and exists (select 1 from lcpol where contno = a.otherno and polno = mainpolno and appflag = '1') "
//		       + " and exists (select 1 from ljspayperson where paytype = 'ZC' and getnoticeno=a.getnoticeno)";
	  	
		sqlid="TempFeeInvoiceInputSql0";
		
	//}else if(_DBT==DBM){
//		strSQL = "select a.otherno,(select name from ldperson where customerno = a.appntno),a.sumduepaymoney,"
//		       + " (select max(lastpaytodate) from ljspayperson where getnoticeno = a.getnoticeno) minpaytodate, "
//		       + " (select max(paycount) from ljspayperson where getnoticeno = a.getnoticeno), "
//		       + " (select codename from ldcode  where codetype = 'salechnl' and code = (select salechnl from lccont where contno = a.otherno)), "
//		       + " (case (select distinct 1 from lrascription where contno = a.otherno) when 1 then '是' else '否' end), "
//		       + " (select agentcode from lccont where contno = a.otherno), "
//		       + " (case (select 1 from lrascription where contno = a.otherno limit 0,1) when 1 then (select distinct agentnew from lrascription where contno = a.otherno limit 0,1) else (select distinct agentcode from lradimascription where contno = a.otherno limit 0,1) end), "
//		       + " (select riskname from lmriskapp where riskcode=a.riskcode),a.getnoticeno, "
//		       + " (select polno from lcpol where contno=a.otherno and polno=mainpolno limit 0,1) from ljspay a where 1=1 "
//		       + " and a.othernotype = '2' "
//		       + getWherePart( 'managecom','MngCom','like' )
//		       + " and not exists (select 1 from lccontstate ls where ls.contno = a.otherno and StateType = 'PayPrem' and State = '1' and enddate is null) "
//		       + " and exists (select 1 from lccontstate ls2 where ls2.contno = a.otherno and StateType = 'Available' and State = '0' and enddate is null) "
//		       + " and exists (select 1 from lcpol where contno = a.otherno and polno = mainpolno and appflag = '1') "
//		       + " and exists (select 1 from ljspayperson where paytype = 'ZC' and getnoticeno=a.getnoticeno)";
		
		//sqlid="TempFeeInvoiceInputSql1";
	//}
	
	       
//	  if(StartDate != "")
//	  {
//	  	strSQL = strSQL + " and paydate>='" + StartDate +"'";
//	  }
//	  
//	   if(EndDate != "")
//	  {
//	  	strSQL = strSQL + " and paydate<='" + EndDate +"'";
//	  }
//	
//		if(document.all('ContNo').value != "")
//	{
//		strSQL = strSQL + " and a.otherno = '" + document.all('ContNo').value + "'";
//	}       
//	
//  	if(document.all('AgentCode').value != "")
//	{
//			strSQL = strSQL + " and exists (select 1 from lcpol where contno=a.otherno " + getWherePart( 'AgentCode' )+ " and polno=mainpolno and appflag='1') ";
//	}       
//	
//	
//	strSQL = strSQL+" order by paydate,otherno,minpaytodate";
	    
	    
	var  MngCom0 = window.document.getElementsByName(trim("MngCom"))[0].value;
	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.TempFeeInvoiceInputSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid);//指定使用的Sql的id
	mySql0.addSubPara(MngCom0);//指定传入的参数
	mySql0.addSubPara(StartDate);//指定传入的参数
	mySql0.addSubPara(EndDate);//指定传入的参数
	mySql0.addSubPara(document.all('ContNo').value);//指定传入的参数
	mySql0.addSubPara(AgentCode0);//指定传入的参数
	strSQL=mySql0.getString();
	 //alert(1);
	 //alert(strSQL);
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL); 
    
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=没有查询到符合条件的数据" ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0; 
  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);   
 
}

//发票打印
function Print()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
			alert( "请先选择一条记录，再点击打印按钮。" );
			return;
	}
	else
	{
	  var cGetNoticeNo = PolGrid.getRowColData(tSel - 1,11);		
		if (cGetNoticeNo == ""){
				alert("无效的应付信息");
		    return;
		  }
	} 
	
//var tSql = "select count(1) from (select 1 from ljspayperson where GetNoticeNo='" + cGetNoticeNo +"' and paytype='ZC' group by riskcode)";
	
	var sqlid2="TempFeeInvoiceInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("f1print.TempFeeInvoiceInputSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(cGetNoticeNo);//指定传入的参数
	var tSql=mySql2.getString();

	turnPage1.strQueryResult  = easyQueryVer3(tSql, 1, 1, 1);  
	if (turnPage1.strQueryResult) 
  {	
//  	var tnSql = "select 1 from dual Where (select Count(Distinct riskcode) from ljspayperson where GetNoticeNo='" + cGetNoticeNo +"' and paytype='ZC')>7";
	
  	var sqlid3="TempFeeInvoiceInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("f1print.TempFeeInvoiceInputSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(cGetNoticeNo);//指定传入的参数
	var tnSql=mySql3.getString();
  	
  	turnPage1 = new turnPageClass(); 
  	turnPage1.strQueryResult  = easyQueryVer3(tnSql, 1, 1, 1);

			if (turnPage1.strQueryResult) {
				alert("已超过7条收费项目，只能打印出前7条");	
			}
	}
  else
  {
		alert("没有应付信息，请确认!");
		return;
  }


//大于1000元需要确认	
	var aMoney = PolGrid.getRowColData(tSel - 1,3);		 
	if(aMoney>=1000 && confirm("应收金额超过1000元，是否继续打印?")){
  		fm.action="TempFeeInvoiceF1PSave.jsp";
		  fm.target="f1print";
		  submitForm();
	}
	else if(aMoney<1000){
			fm.action="TempFeeInvoiceF1PSave.jsp";
		  fm.target="f1print";
		  submitForm();
		}	

}


function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
		return arrSelected;

	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
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


//查询代理人的方式
function queryAgent(comcode)
{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  
}

function queryAgentAll(comcode)
{
  if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码	
//		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
	  	var sqlid4="TempFeeInvoiceInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("f1print.TempFeeInvoiceInputSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(cAgentCode);//指定传入的参数
		var strSql=mySql4.getString();
		
		var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult == null) 
    	{
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    	}
    else
    	{
//    	  var nSql = "select name from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";
    	  
	  	  	var sqlid5="TempFeeInvoiceInputSql5";
			var mySql5=new SqlClass();
			mySql5.setResourceName("f1print.TempFeeInvoiceInputSql"); //指定使用的properties文件名
			mySql5.setSqlId(sqlid5);//指定使用的Sql的id
			mySql5.addSubPara(trim(fm.AgentCode.value));//指定传入的参数
			var nSql=mySql5.getString();
    	  
    	  arrResult = easyExecSql(nSql);
    	  fm.AgentName.value=arrResult;
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