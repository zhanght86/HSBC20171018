//               该文件中包含客户端需要处理的函数和事件
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
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
  }
}

// 查询按钮
function easyQueryClick()
{	
  //submitForm();
  if(!verifyInput())  
  {
  	return false;
  }
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
//	
//	
//	strSQL = "select PayNo,IncomeNo,IncomeType,SumActuPayMoney,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay where 1=1 "			 
//				 + getWherePart( 'PayNo' )
//				 + getWherePart( 'IncomeNo' )
//				 + getWherePart( 'IncomeType' )
//				 + getWherePart( 'PayDate' )				 
//				 + getWherePart( 'AgentCode' )
				 
		  
				 
	if(document.all('IncomeType').value=='1')
				 + " and PayNo not in (select otherno from LOPRTManager2 where code='33' and StateFlag='1')"
	else if(document.all('IncomeType').value=='2')
				 + " and PayNo not in (select otherno from LOPRTManager2 where code='32'  and StateFlag='1')"
	
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
//		strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		 
		 var  PayNo = window.document.getElementsByName(trim("PayNo"))[0].value;	
	     var  IncomeNo = window.document.getElementsByName(trim("IncomeNo"))[0].value;
	     var  IncomeType = window.document.getElementsByName(trim("IncomeType"))[0].value;
	     var  PayDate = window.document.getElementsByName(trim("PayDate"))[0].value;
	     var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
	     var sqlid1="PrintAutoDJInputSql1";
		  var mySql1=new SqlClass();
		  mySql1.setResourceName("autopay.PrintAutoDJInputSql");
		  mySql1.setSqlId(sqlid1);//指定使用SQL的id
		  mySql1.addSubPara(PayNo);//指定传入参数
		  mySql1.addSubPara(IncomeNo);//指定传入参数
		  mySql1.addSubPara(IncomeType);//指定传入参数
		  mySql1.addSubPara(PayDate);//指定传入参数
		  mySql1.addSubPara(AgentCode);//指定传入参数
		  mySql1.addSubPara(managecomvalue);
		  strSQL = mySql1.getString();
		
		
	}	
	else
	{
//		strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' );
		
		 var  PayNo = window.document.getElementsByName(trim("PayNo"))[0].value;	
	     var  IncomeNo = window.document.getElementsByName(trim("IncomeNo"))[0].value;
	     var  IncomeType = window.document.getElementsByName(trim("IncomeType"))[0].value;
	     var  PayDate = window.document.getElementsByName(trim("PayDate"))[0].value;
	     var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
	     var sqlid3="PrintAutoDJInputSql3";
		  var mySql3=new SqlClass();
		  mySql3.setResourceName("autopay.PrintAutoDJInputSql");
		  mySql3.setSqlId(sqlid3);//指定使用SQL的id
		  mySql3.addSubPara(PayNo);//指定传入参数
		  mySql3.addSubPara(IncomeNo);//指定传入参数
		  mySql3.addSubPara(IncomeType);//指定传入参数
		  mySql3.addSubPara(PayDate);//指定传入参数
		  mySql3.addSubPara(AgentCode);//指定传入参数
		  mySql3.addSubPara(window.document.getElementsByName(trim("MngCom"))[0].value);
		  strSQL = mySql3.getString();
		
		
	}		
//	 strSQL = strSQL + "order by IncomeNo,PayDate,ConfDate";
	 
	 
	 
	 
	 
	 
//	alert(strSQL);
	
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
	var iHeight=250;     //弹出窗口的高度; 
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
		alert( "请先选择一条记录，再点击费用明细按钮。" );
	else
	{
		arrReturn = getQueryResult();
	  var cPayNo = PolGrid.getRowColData(tSel - 1,1);		
		if (cPayNo == "")
		    return;
		    
		}  
	fm.PayNo.value = cPayNo;
	
	fm.IncomeType.value = arrReturn[0][2];
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
	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
		
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


function QueryAutoDJ()
{
	
  if(!verifyInput()) 
  {
  	return false;
  }
  initPolGrid();
//  var strSQL = "";
//  strSQL = " select LOLoan.PolNo,(select t.tempfeeno from ljtempfeeclass t where t.chequeno=LOLoan.Actugetno ),LOLoan.LoanDate,LOLoan.LeaveMoney "
//  			 + " from LOLoan,LOPRTManager where LOLoan.contno = LOPRTManager.OtherNo "
//  			 + " and LOPRTManager.remark = LOLoan.EdorNo"
//  			 + " and LOPRTManager.ManageCom like '"+fm.Station.value+"%%'"
//  			 + " and LOPRTManager.code = '41' and LOPRTManager.StateFlag = '0'"
//  			 + " and LOLoan.LoanType = '1' "
//  			 + getWherePart( 'LOLoan.PayOffFlag','TRFlag','=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','StartDate','>=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','EndDate','<=');
  
  var  TRFlag = window.document.getElementsByName(trim("TRFlag"))[0].value;
  var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value;
  var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
  
  
  
  var sqlid2="PrintAutoDJInputSql2";
  var mySql2=new SqlClass();
  mySql2.setResourceName("autopay.PrintAutoDJInputSql");
  mySql2.setSqlId(sqlid2);//指定使用SQL的id
  mySql2.addSubPara(fm.Station.value);//指定传入参数
  mySql2.addSubPara(TRFlag);//指定传入参数
  mySql2.addSubPara(StartDate);//指定传入参数
  mySql2.addSubPara(EndDate);//指定传入参数
  
  var strSQL = mySql2.getString();

  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
 		if (!turnPage.strQueryResult) 
 		{
    	alert("在该管理机构下没有满足条件的自动垫交信息记录");
    	return "";
  	}
  
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	turnPage.pageDisplayGrid = PolGrid;    
  	turnPage.strQuerySql     = strSQL; 
  	turnPage.pageIndex       = 0;  
  	var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  	return true;

 }
 
function PrintAutoDJ()
{
	tRow=PolGrid.getSelNo();
  if (tRow==0)
  {
  	alert("请您先进行选择");
    return;
  }
  
  tEdorNo = PolGrid.getRowColData(tRow-1,2);
  fm.Date.value= PolGrid.getRowColData(tRow-1,3);
//  alert("借款日期是"+fm.Date.value);
   
  fm.action="./AutoDJPrint.jsp?EdorNo="+tEdorNo;
  fm.target="f1print";
//  showInfo.close();
  document.getElementById("fm").submit();
}
//打印所有清单的函数
function PrintQD()
{
	fm.action="./AutoDJAllPrint.jsp";
  fm.target="f1print";
  document.getElementById("fm").submit();
}	