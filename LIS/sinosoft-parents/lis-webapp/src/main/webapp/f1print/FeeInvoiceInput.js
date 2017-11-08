//            该文件中包含客户端需要处理的函数和事件
var arrDataSet;
var mDebug="0";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass(); 
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
  //var i = 0;
  //var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
  //{
  //  alert("操作控制数据丢失！");
  //}
  //showSubmitFrame(mDebug);
  //alert (verifyInput());
  if(verifyInput()) 
  {
  	document.getElementById("fm").submit(); //提交
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

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //执行下一步操作
  }
}

// 查询按钮
function easyQueryClick()
{	
	if(document.all('PayDate').value == "" 
		&& document.all('PayNo').value == "" 
		&& document.all('IncomeNo').value == "" 
		&& document.all('AgentCode').value == ""
		&& document.all('GetNoticeNo').value == ""){
		alert("交费日期、交费收据号、实收编码、代理人编码、合同号码中请至少填写一项再进行查询");
		return;
	}
  //submitForm();
  if(!verifyInput()) 
  {
  	return false;
  }
  if(!(document.all('IncomeNo').value == "" || document.all('IncomeNo').value == null))
  {
  	if((document.all('IncomeType').value == "" || document.all('IncomeType').value == null))
  	{
  		alert("请选择合同号码类型");
  		return false;
  		}
  	}
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	
	//zy 2009-08-12 10:12 调整总金额的显示，只显示金额为正常保费的;由于保全团单新增保费都会在ljapayperson记录，所以对于保全的不进行团个的区分
//	strSQL = "select GetNoticeNo,PayNo,IncomeNo,IncomeType,(case incometype when '1' then (select sum(SumActuPayMoney) from ljapaygrp where payno=a.payno and paytype='ZC') else (select sum(SumActuPayMoney) from ljapayperson where payno=a.payno and paytype='ZC' ) end) ,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay a where 1=1  and IncomeType in ('1','2') "			 
//				 + getWherePart( 'PayNo' )
//				 + getWherePart( 'IncomeNo' )
//				 + getWherePart( 'IncomeType' )
//				 + getWherePart( 'PayDate' )				 
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'GetNoticeNo' )
	var IncomeType1 ="";
	var IncomeType2 ="";
	if(document.all('IncomeType').value=='1')
//				 + " and PayNo not in (select otherno from LOPRTManager2 where code='33' and StateFlag='1')"
				 IncomeType1="1";
	else if(document.all('IncomeType').value=='2')
//				 + " and PayNo not in (select otherno from LOPRTManager2 where code='32'  and StateFlag='1')"
				 IncomeType2="1";
	var MngCom1="";
	var MngCom2="";
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
//		strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		MngCom1=managecomvalue;
	}	
	else
	{
//		strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' );
		MngCom2=window.document.getElementsByName(trim("MngCom"))[0].value;
	}		
//	 strSQL = strSQL + "order by IncomeNo,PayDate,ConfDate";
	//prompt('',strSQL);
	
	  	var  PayNo0 = window.document.getElementsByName(trim("PayNo"))[0].value;
	  	var  IncomeNo0 = window.document.getElementsByName(trim("IncomeNo"))[0].value;
	  	var  IncomeType0 = window.document.getElementsByName(trim("IncomeType"))[0].value;
	  	var  PayDate0 = window.document.getElementsByName(trim("PayDate"))[0].value;
	  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	  	var  GetNoticeNo0 = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		var sqlid0="FeeInvoiceInputSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("f1print.FeeInvoiceInputSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(PayNo0);//指定传入的参数
		mySql0.addSubPara(IncomeNo0);//指定传入的参数
		mySql0.addSubPara(IncomeType0);//指定传入的参数
		mySql0.addSubPara(PayDate0);//指定传入的参数
		mySql0.addSubPara(AgentCode0);//指定传入的参数
		mySql0.addSubPara(GetNoticeNo0);//指定传入的参数
		mySql0.addSubPara(IncomeType1);//指定传入的参数
		mySql0.addSubPara(IncomeType2);//指定传入的参数
		mySql0.addSubPara(MngCom1);//指定传入的参数
		mySql0.addSubPara(MngCom2);//指定传入的参数
		strSQL=mySql0.getString();
	 
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL); 
    
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    //alert("查询失败！");
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

//个单发票打印
function PPrint()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		{
			alert( "请先选择一条记录，再点击打印按钮。" );
			return;
		}
	else
	{
		arrReturn = getQueryResult();
	  var cPayNo = PolGrid.getRowColData(tSel - 1,2);		
		if (cPayNo == ""){
				alert("无效的缴费信息");
		    return;
		  }
		}  
	
	fm.PayNo.value = cPayNo;
//	alert(cPayNo);
//	var tSql = "select 1 from dual ";
	var tSqlid = "1";
		fm.IncomeType.value = arrReturn[0][3];
	if(fm.IncomeType.value==2){
//		tSql = "select count(1) from ljapayperson where payno='" + cPayNo +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
		tSqlid = "2";
	}
	if(fm.IncomeType.value==1){
//		tSql = "select count(1) from ljapaygrp where payno='" + cPayNo +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";	
		tSqlid = "3";
	}
	
	var tSql ="";
	if(fm.IncomeType.value==2 || fm.IncomeType.value==1) {
		
		var sqlid1="FeeInvoiceInputSql"+tSqlid;
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.FeeInvoiceInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(cPayNo);//指定传入的参数
		tSql=mySql1.getString();
	} else {
		var sqlid1="FeeInvoiceInputSql"+tSqlid;
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.FeeInvoiceInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		tSql=mySql1.getString();
	}
	
	var tResult = easyExecSql(tSql);
  if (tResult != null) 
  {
			if(tResult[0][0]>4){
				alert("共有"+tResult[0][0]+"条收费项目，因发票大小限制中只能打印出前四4条");	
			}
		
  }
  else
  {
		alert("没有缴费信息，请确认!");
		return;
  }
// zy 2009-08-11 9:08 如果余额金额大于0，则系统给予提示
 var yMoney = 0;
// var ySql = "select '0' from dual ";
 var ySqlid ="4";
 if(fm.IncomeType.value==2)
 {
// 		ySql ="select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney) else 0 end) from ljapayperson   where payno='" + cPayNo +"' and paytype='YET' ";
 		ySqlid ="5";
 }
 if(fm.IncomeType.value==1){
//		ySql = "select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney) else 0 end) from ljapaygrp where payno='" + cPayNo +"'  and paytype='YET' ";
		ySqlid ="6";
}
 
	var ySql ="";
	if(fm.IncomeType.value==2 || fm.IncomeType.value==1) {
		
		var sqlid5="FeeInvoiceInputSql"+ySqlid;
		var mySql5=new SqlClass();
		mySql5.setResourceName("f1print.FeeInvoiceInputSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(cPayNo);//指定传入的参数
		ySql=mySql5.getString();
	} else {
		var sqlid4="FeeInvoiceInputSql"+ySqlid;
		var mySql4=new SqlClass();
		mySql4.setResourceName("f1print.FeeInvoiceInputSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		ySql=mySql4.getString();
	}
 
   //prompt("",ySql);
  yMoney= easyExecSql(ySql);
  if(yMoney!=null){
  	if(yMoney>0)
  	{
  		alert("该张保单存在溢交金额，发票不予体现，请开收据或请做退费处理！");
  	}
		if(fm.IncomeType.value==2)
		{
		  fm.action="PFeeInvoiceF1PSave.jsp";
		  fm.target="f1print";
		  fm.fmtransact.value="CONFIRM";
		  submitForm();
		}
	  else if (fm.IncomeType.value==1)
	   {
		 fm.action="GFeeInvoiceF1PSave.jsp";
	     fm.target="f1print";
	     fm.fmtransact.value="CONFIRM";
	     submitForm();
		}
		else
			{
				alert("暂不支持该类型保单的发票打印，请核实");
				return ;
			}
		}
}

//集体发票打印
function GPrint()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击费用明细按钮。" );
	else
	{
	    var cPayNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPayNo == "")
		    return;
		    
		}  
	
	fm.PayNo.value = cPayNo;
  fm.action="GFeeInvoiceF1PSave.jsp";
  fm.target="f1print";
  fm.fmtransact.value="PRINT";
  submitForm();
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
    
		var sqlid7="FeeInvoiceInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("f1print.FeeInvoiceInputSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(cAgentCode);//指定传入的参数
		var strSql=mySql7.getString();
		
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