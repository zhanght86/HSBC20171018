//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
var turnPage3 = new turnPageClass(); 

//账单查询
function billQuery()
{
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRBsnsBillUWInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRBsnsBillUWInputSql100");//指定使用的Sql的id
		/**
		mySql100.addSubPara(getWherePart('BillNo','BatchNO'));//指定传入的参数
		mySql100.addSubPara(getWherePart('StartDate','StartDate', ' > '));//指定传入的参数
		mySql100.addSubPara(getWherePart('EndDate','EndDate', ' < '));//指定传入的参数
		mySql100.addSubPara(getWherePart('RIContNo','ContNo'));//指定传入的参数
		mySql100.addSubPara(getWherePart('IncomeCompanyNo','RIcomCode'));//指定传入的参数
		*/
		mySql100.addSubPara(fm.BatchNO.value);//指定传入的参数
		mySql100.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql100.addSubPara(fm.EndDate.value);//指定传入的参数
		mySql100.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql100.addSubPara(fm.RIcomCode.value);//指定传入的参数
	var strSQL=mySql100.getString();
	/**
	var strSQL = "select BillNo,BillingCycle,BillTimes,RIContNo,IncomeCompanyNo,StartDate,EndDate,decode(State,'01','账单计算','02','账单审核','03','账单修改','04','账单确认'),State from RIBsnsBillBatch where 1=1 "
		+getWherePart('BillNo','BatchNO')
		+getWherePart('StartDate','StartDate', ' > ')
		+getWherePart('EndDate','EndDate', ' < ')
		+getWherePart('RIContNo','ContNo')
		+getWherePart('IncomeCompanyNo','RIcomCode')
		;
	*/
	turnPage2.queryModal(strSQL,BatchListGrid);
}

//账单明细查询

function queryDetial()
{
	var num = BatchListGrid.getSelNo();
	var billNo = BatchListGrid.getRowColData(num-1,1);
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRBsnsBillUWInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRBsnsBillUWInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(billNo);//指定传入的参数
	var strSQL=mySql101.getString();
	/**
	var strSQL = "select BillNo,Details,(select codename from ldcode where code = Details and codetype = 'ridebcre'),sum(decode(DebCre,'D',Summoney,0)) Deb,sum(decode(DebCre,'C',Summoney,0)) Cre  from RIBsnsBillDetails where BillNo = '"+billNo
	+"'  group by BillNo,Details  order by Details " ;
	*/
	turnPage3.queryModal(strSQL,BillDetailGrid);

}


//账单修改
function billUpdate()
{
   //RIBsnsBillBatch.State=03 可以进行修改
   //将前台的 借和贷 差分成两条记录，并将SerialNo的最大值 +1
   //LRBsnsBillUWSave.jsp
	fm.OperateType.value = "billupdate" ;
	var num = BatchListGrid.getSelNo() ;
	
	if(num==0)
	{
		myAlert(""+"请先选择要修改的账单！"+"");
		return false;
	}
	try
	{
		var i = 0;
		var showStr=""+"正在统计数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		fm.action = "LRBsnsBillUWSave.jsp"
		fm.submit();
	}	
	catch(ex) 
	{
	  	showInfo.close();
	  	myAlert(ex);
	}
	ResetForm();  
}

//分保明细数据下载 
function billDown()
{
	var num = BatchListGrid.getSelNo() ;
	if(num==0)
	{
		myAlert(""+"请先选择账单数据！"+"");
		return false;
	}
	var billNo = BatchListGrid.getRowColData(num-1,1);
	var contNo = BatchListGrid.getRowColData(num-1,4);
	var comNo = BatchListGrid.getRowColData(num-1,5);
	var sdate = BatchListGrid.getRowColData(num-1,6);
	var edate = BatchListGrid.getRowColData(num-1,7);
	
	//alert(billNo);
	fm.jkbillno.value = billNo ;
	fm.jkcontno.value = contNo ;
	fm.jkcomno.value = comNo ;
	fm.jksdate.value = sdate ;
	fm.jkedate.value = edate ;
	//alert(fm.jkbillno.value);
	fm.action="./CheckExcel.jsp";
	fm.target="fraSubmit";
	fm.submit(); //提交
}

//结论保存

function SaveConclusion()
{
	//RIBsnsBillBatch.State=02 才可以下结论 ,将结论保存到 RIBsnsBillBatch.State=02
	//LRBsnsBillUWSave.jsp	
	fm.OperateType.value = "conclusion" ;
	var num = AuditBillListGrid.getSelNo() ;
	
	if(num==0)
	{
		myAlert(""+"请先选择待审核数据！"+"");
		return false;
	}
	if(fm.RIUWReport.value==null || fm.RIUWReport.value=="")
	{
		myAlert(""+"请选择审核结论！"+"");
		return false;
	}
	try
	{
		var i = 0;
		var showStr=""+"正在统计数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		fm.action = "LRBsnsBillUWSave.jsp"
		fm.submit();
	}	
	catch(ex) 
	{
	  	showInfo.close();
	  	myAlert(ex);
	}
	ResetForm();
}

//待审核账单查询
function queryAuditList()
{
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRBsnsBillUWInputSql"); //指定使用的properties文件名
		mySql102.setSqlId("LRBsnsBillUWInputSql102");//指定使用的Sql的id
		mySql102.addSubPara("1");
	var strSQL=mySql102.getString();
	//var strSQL = "select BillNo,BillingCycle,BillTimes,RIContNo,IncomeCompanyNo,StartDate,EndDate,decode(State,'01','账单计算','02','账单审核','03','账单修改','04','账单确认'),State from RIBsnsBillBatch where State in('01','02','03') " ;
	turnPage.queryModal(strSQL,AuditBillListGrid);
}

//重  置
function ResetForm()
{
	fm.StartDate.value='';
	fm.EndDate.value='';
	fm.ContNo.value='';
	fm.ContName.value='';
	fm.RIcomCode.value='';
	fm.RIcomName.value='';
	fm.OperateType.value = '' ;
	fm.RIUWReport.value = "";
	fm.RIUWReportName.value="";

}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content)
{
	showInfo.close();
	if (FlagStr == "Fail" ) 
	{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  
	} 
	else 
	{ 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	ResetForm();
}


function afterCodeSelect( cCodeName, Field )
{

}
