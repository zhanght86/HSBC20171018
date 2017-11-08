//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var eventType;

function queryClick()
{
	
	dataQuery();
	
}


function dataQuery()
{
//	if(!verifyInput())
//	{
//		return false;
//	}
//	var tSQL = "select a.nodestate from riwflog a where a.batchno='" + fm.BatchNo.value+"'";
//	var result = easyExecSql(tSQL);
//	if(result[0][0]=='00')
//	{
//		alert("该批次没有数据");
//		return false;
//	}
//	else 
//	if(result[0][0]=='99')
//	{
//		tSQL = "select a.contno,a.insuredno,a.insuredname,(case when a.ripreceptno is null then '' else a.ripreceptno end),(case when a.accamnt is null then 0 else a.accamnt end) from ripolrecordbake a where a.batchno='"+fm.BatchNo.value+"' ";
//	}else
//	{
//		tSQL = "select a.contno,a.insuredno,a.insuredname,(case when a.ripreceptno is null then '' else a.ripreceptno end),(case when a.accamnt is null then 0 else a.accamnt end) from ripolrecord a where a.batchno='"+fm.BatchNo.value+"' ";
//	}
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRDataSearchInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRDataSearchInputSql100");//指定使用的Sql的id
		/**
		mySql100.addSubPara(getWherePart("a.accumulatedefno","AccumulateDefNO"));//指定传入的参数
		mySql100.addSubPara(getWherePart("a.InsuredNo","InsuredNo"));//指定传入的参数
		mySql100.addSubPara(getWherePart("a.InsuredName","InsuredName","like"));//指定传入的参数
		mySql100.addSubPara(getWherePart("a.EventType","EventType"));//指定传入的参数
	*/
	    mySql100.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
		mySql100.addSubPara(fm.InsuredNo.value);//指定传入的参数
		mySql100.addSubPara(fm.InsuredName.value);//指定传入的参数
		mySql100.addSubPara(fm.EventType.value);//指定传入的参数
	var tSQL=mySql100.getString();
	
	/**
	var tSQL = "select a.contno,decode(EventType,'01','新单','02','续期','03','保全','04','理赔',''),a.insuredno,a.insuredname,(case when a.RIContNo is null then '' else a.RIContNo end),RIPreceptNo,accumulatedefno,riskamnt,decode(EventType,'03',riskamnt-PRERISKAMNT,0),(case when a.accamnt is null then 0 else a.accamnt end),decode(EventType,'04',CLMGETMONEY,0),GetDate,eventno from ripolrecordbake a where 1=1 ";
	tSQL = tSQL + getWherePart("a.accumulatedefno","AccumulateDefNO")
			+ getWherePart("a.InsuredNo","InsuredNo")
			+ getWherePart("a.InsuredName","InsuredName","like")
			+ getWherePart("a.EventType","EventType")
			+ " and rownum <= 2000  order by accumulatedefno,insuredno,GetDate ";
	*/
	result = easyExecSql(tSQL);
	if(result==null)
	{
		RIDataGrid.clearData();
		myAlert(""+"查询结果为空"+"");
		return false;
	}
	turnPage1.queryModal(tSQL,RIDataGrid);
}

//分保明细数据查询
function queryDetail()
{
	var num = RIDataGrid.getSelNo() ;

	var EventNo = RIDataGrid.getRowColData(num-1,13)
	
		var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.LRDataSearchInputSql"); //指定使用的properties文件名
			mySql101.setSqlId("LRDataSearchInputSql101");//指定使用的Sql的id
			mySql101.addSubPara(EventNo);//指定传入的参数
		var tSQL=mySql101.getString();
	//var tSQL = "select ContNo,AssociateCode,incomecompanyno,(select ComPanyName from RIComInfo where ComPanyNo =incomecompanyno ),AreaID,CessionRate,CessionAmount,(case when PremChang is null then 0 else PremChang end ),(case when CommChang is null then 0 else CommChang end ),(case when ReturnPay is null then 0 else ReturnPay end),RIDate from RIRecordTrace where EventNo = '"+EventNo+"' order by AreaID" ;
	
	turnPage2.queryModal(tSQL,RIDataDetailGrid);
}


function verifyInput1(){

	return true;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,Result){
	
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
	
}

function resetPage()
{
	fm.reset();
	RIDataGrid.clearData();

}

//明细分保数据下载
function DownLoadExcel()
{
	fm.OperateType.value="DOWNLOAD";
		
	fm.target="fraSubmit";
	fm.action="./LRDataSearchSave.jsp";
	fm.submit(); //提交

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
}

function afterCodeSelect( codeName, Field )
{
	if(codeName=="state")
	{
	}
}
