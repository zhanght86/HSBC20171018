//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
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
//		alert("������û������");
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
		mySql100.setResourceName("reinsure.LRDataSearchInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRDataSearchInputSql100");//ָ��ʹ�õ�Sql��id
		/**
		mySql100.addSubPara(getWherePart("a.accumulatedefno","AccumulateDefNO"));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart("a.InsuredNo","InsuredNo"));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart("a.InsuredName","InsuredName","like"));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart("a.EventType","EventType"));//ָ������Ĳ���
	*/
	    mySql100.addSubPara(fm.AccumulateDefNO.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.InsuredName.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.EventType.value);//ָ������Ĳ���
	var tSQL=mySql100.getString();
	
	/**
	var tSQL = "select a.contno,decode(EventType,'01','�µ�','02','����','03','��ȫ','04','����',''),a.insuredno,a.insuredname,(case when a.RIContNo is null then '' else a.RIContNo end),RIPreceptNo,accumulatedefno,riskamnt,decode(EventType,'03',riskamnt-PRERISKAMNT,0),(case when a.accamnt is null then 0 else a.accamnt end),decode(EventType,'04',CLMGETMONEY,0),GetDate,eventno from ripolrecordbake a where 1=1 ";
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
		myAlert(""+"��ѯ���Ϊ��"+"");
		return false;
	}
	turnPage1.queryModal(tSQL,RIDataGrid);
}

//�ֱ���ϸ���ݲ�ѯ
function queryDetail()
{
	var num = RIDataGrid.getSelNo() ;

	var EventNo = RIDataGrid.getRowColData(num-1,13)
	
		var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.LRDataSearchInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql101.setSqlId("LRDataSearchInputSql101");//ָ��ʹ�õ�Sql��id
			mySql101.addSubPara(EventNo);//ָ������Ĳ���
		var tSQL=mySql101.getString();
	//var tSQL = "select ContNo,AssociateCode,incomecompanyno,(select ComPanyName from RIComInfo where ComPanyNo =incomecompanyno ),AreaID,CessionRate,CessionAmount,(case when PremChang is null then 0 else PremChang end ),(case when CommChang is null then 0 else CommChang end ),(case when ReturnPay is null then 0 else ReturnPay end),RIDate from RIRecordTrace where EventNo = '"+EventNo+"' order by AreaID" ;
	
	turnPage2.queryModal(tSQL,RIDataDetailGrid);
}


function verifyInput1(){

	return true;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
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

//��ϸ�ֱ���������
function DownLoadExcel()
{
	fm.OperateType.value="DOWNLOAD";
		
	fm.target="fraSubmit";
	fm.action="./LRDataSearchSave.jsp";
	fm.submit(); //�ύ

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
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
