//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
var turnPage3 = new turnPageClass(); 

//�˵���ѯ
function billQuery()
{
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRBsnsBillUWInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRBsnsBillUWInputSql100");//ָ��ʹ�õ�Sql��id
		/**
		mySql100.addSubPara(getWherePart('BillNo','BatchNO'));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart('StartDate','StartDate', ' > '));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart('EndDate','EndDate', ' < '));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart('RIContNo','ContNo'));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart('IncomeCompanyNo','RIcomCode'));//ָ������Ĳ���
		*/
		mySql100.addSubPara(fm.BatchNO.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.StartDate.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.EndDate.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.RIcomCode.value);//ָ������Ĳ���
	var strSQL=mySql100.getString();
	/**
	var strSQL = "select BillNo,BillingCycle,BillTimes,RIContNo,IncomeCompanyNo,StartDate,EndDate,decode(State,'01','�˵�����','02','�˵����','03','�˵��޸�','04','�˵�ȷ��'),State from RIBsnsBillBatch where 1=1 "
		+getWherePart('BillNo','BatchNO')
		+getWherePart('StartDate','StartDate', ' > ')
		+getWherePart('EndDate','EndDate', ' < ')
		+getWherePart('RIContNo','ContNo')
		+getWherePart('IncomeCompanyNo','RIcomCode')
		;
	*/
	turnPage2.queryModal(strSQL,BatchListGrid);
}

//�˵���ϸ��ѯ

function queryDetial()
{
	var num = BatchListGrid.getSelNo();
	var billNo = BatchListGrid.getRowColData(num-1,1);
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRBsnsBillUWInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRBsnsBillUWInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(billNo);//ָ������Ĳ���
	var strSQL=mySql101.getString();
	/**
	var strSQL = "select BillNo,Details,(select codename from ldcode where code = Details and codetype = 'ridebcre'),sum(decode(DebCre,'D',Summoney,0)) Deb,sum(decode(DebCre,'C',Summoney,0)) Cre  from RIBsnsBillDetails where BillNo = '"+billNo
	+"'  group by BillNo,Details  order by Details " ;
	*/
	turnPage3.queryModal(strSQL,BillDetailGrid);

}


//�˵��޸�
function billUpdate()
{
   //RIBsnsBillBatch.State=03 ���Խ����޸�
   //��ǰ̨�� ��ʹ� ��ֳ�������¼������SerialNo�����ֵ +1
   //LRBsnsBillUWSave.jsp
	fm.OperateType.value = "billupdate" ;
	var num = BatchListGrid.getSelNo() ;
	
	if(num==0)
	{
		myAlert(""+"����ѡ��Ҫ�޸ĵ��˵���"+"");
		return false;
	}
	try
	{
		var i = 0;
		var showStr=""+"����ͳ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
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

//�ֱ���ϸ�������� 
function billDown()
{
	var num = BatchListGrid.getSelNo() ;
	if(num==0)
	{
		myAlert(""+"����ѡ���˵����ݣ�"+"");
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
	fm.submit(); //�ύ
}

//���۱���

function SaveConclusion()
{
	//RIBsnsBillBatch.State=02 �ſ����½��� ,�����۱��浽 RIBsnsBillBatch.State=02
	//LRBsnsBillUWSave.jsp	
	fm.OperateType.value = "conclusion" ;
	var num = AuditBillListGrid.getSelNo() ;
	
	if(num==0)
	{
		myAlert(""+"����ѡ���������ݣ�"+"");
		return false;
	}
	if(fm.RIUWReport.value==null || fm.RIUWReport.value=="")
	{
		myAlert(""+"��ѡ����˽��ۣ�"+"");
		return false;
	}
	try
	{
		var i = 0;
		var showStr=""+"����ͳ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
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

//������˵���ѯ
function queryAuditList()
{
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRBsnsBillUWInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql102.setSqlId("LRBsnsBillUWInputSql102");//ָ��ʹ�õ�Sql��id
		mySql102.addSubPara("1");
	var strSQL=mySql102.getString();
	//var strSQL = "select BillNo,BillingCycle,BillTimes,RIContNo,IncomeCompanyNo,StartDate,EndDate,decode(State,'01','�˵�����','02','�˵����','03','�˵��޸�','04','�˵�ȷ��'),State from RIBsnsBillBatch where State in('01','02','03') " ;
	turnPage.queryModal(strSQL,AuditBillListGrid);
}

//��  ��
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
	ResetForm();
}


function afterCodeSelect( cCodeName, Field )
{

}
