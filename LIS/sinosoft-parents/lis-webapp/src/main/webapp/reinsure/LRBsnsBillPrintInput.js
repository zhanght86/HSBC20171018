//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); 


//������˵���ѯ
function queryBillPrint()
{
	var strSQL = "select BillNo,BillingCycle,BillTimes,RIContNo,IncomeCompanyNo,StartDate,EndDate,decode(State,'01','"+"�˵�����"+"','02','"+"�˵����"+"','03','"+"�˵��޸�"+"','04','"+"�˵�ȷ��"+"'),State from RIBsnsBillBatch where State='04' " ;
	
	var mySql1=new SqlClass();
		mySql1.setResourceName("reinsure.LRBsnsBillPrintInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("LRBsnsBillPrintInputSql001");//ָ��ʹ�õ�Sql��id
	    mySql1.addSubPara('1');//ָ������Ĳ���
	strSQL=mySql1.getString();	
	
	turnPage.queryModal(strSQL,BillPrintListGrid);
}

// �˵���ӡ
function billPrint()
{
	try
	{
		if(verifyInput()==true)
		{	
			if(verifyInput2()==true)
			{
				//var i = 0;
				//var showStr="����ͳ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
				//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				fm.target = "importCessData"; 
				//fm.action = "LRBsnsTabSave_antail.jsp"
				fm.action = "LRBsnsBillPrintSave.jsp"
				fm.submit();
				//showInfo.close();
			}
		}
	}	
	catch(ex) 
	{
	  	//showInfo.close( );
	  	myAlert(ex);
   }
}

function verifyInput1()
{ 
	var mStaTerm=fm.StartDate.value;
	var year = mStaTerm.substr(0,4);
	var season = mStaTerm.substr(5);
	if(!isInteger(year)||mStaTerm.length>6||parseFloat(season)>4)
	{
	    myAlert(""+"ͳ���ڼ�ʱ���ִ���!"+"");
	    return  false;
	}
	return true;
}  


//��  ��
function ResetForm()
{
		fm.StartDate.value='';
		fm.EndDate.value='';
		fm.ContNo.value='';
		fm.ContName.value='';
		fm.AccountType.value='';
		fm.AccountTypeName.value='';
		fm.RIcomCode.value='';
		fm.RIcomName.value='';
		fm.TempType.value='';
		fm.TempTypeName.value='';
}

function afterCodeSelect( cCodeName, Field )
{

}
