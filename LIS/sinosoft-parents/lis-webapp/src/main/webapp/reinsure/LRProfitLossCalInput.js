var showInfo;

var turnPage = new turnPageClass(); 
window.onfocus=myonfocus;

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

//��˽��۱���
function SaveConclusion()
{
	//�����۴洢�� RIProLossResult.StandbyString1
	fm.OperateType.value="conclusion";
	var num = LossUWListGrid.getSelNo() ;
	
	if(num==0)
	{
		myAlert(""+"����ѡ���������ݣ�"+"");
		return false;
	}
	if(fm.RILossUWReport.value==null || fm.RILossUWReport.value=="")
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
		fm.action = "./LRProfitLossCalSave.jsp"
		fm.submit();
		//showInfo.close();
	}	
	catch(ex) 
	{
	  	showInfo.close();
	  	myAlert(ex);
	}	
}


//����˲�ѯ
function queryAuditTask()
{
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRProfitLossCalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRProfitLossCalInputSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara("1");
	var strSQL=mySql100.getString();
	
	
	/**
	var strSQL = "select a.SerialNo,a.RIContNo,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),"
		+ " a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode) x,"
		+ " a.CalYear,a.ProLosAmnt,decode(StandbyString1,'01','�����','02','����޸�','03','���ȷ��'),StandbyString1 from RIProLossResult a where StandbyString1 ='01' "
		;		
	strSQL = strSQL +" order by a.SerialNo desc ";
	*/
	var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	turnPage.queryModal(strSQL,LossUWListGrid)

}

//�ύ�����水ť��Ӧ����  ,Ӧ��Ӷ�����
function submitForm()
{
	
	//������ȷ���򲻿�������
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRProfitLossCalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRProfitLossCalInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(fm.CalYear.value);//ָ������Ĳ���
		mySql101.addSubPara(fm.RIcomCode.value);//ָ������Ĳ���
		mySql101.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	var strSql=mySql101.getString();
	/**
	var strSql = " select '1' from RIProLossResult where CalYear='"+fm.CalYear.value
		+"'  and ReComCode ='"+fm.RIcomCode.value+"' and RIContNo='"+fm.ContNo.value+"' and StandbyString1 ='03' " ;
	*/
	var strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
	if(strQueryResult)
	{
		myAlert(""+"�Ѿ����ȷ�ϣ����������㣡��"+"");
		return false;
	}
	fm.OperateType.value="CALCULATE";
	try 
	{
		if( verifyInput() == true &&IncomeGrid.checkValue("IncomeGrid")) 
		{
			if (veriryInput3()==true)
			{
			  	var i = 0;
			  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
					
				fm.action="LRProfitLossCalSave.jsp";
			  	fm.submit(); //�ύ
		  	}
		  	else
		  	{
			}
		}
	} catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}
function veriryInput3()
{
	var lineNum=IncomeGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(IncomeGrid.getRowColData(i,3)=="01")
		{
			if(IncomeGrid.getRowColData(i,4)!=IncomeGrid.getRowColData(i,5))
			{
				myAlert(""+"���������"+""+(i+1)+""+"��"+"'"+IncomeGrid.getRowColData(i,2)+"'"+"�ѱ��޸�"+","+"ԭֵΪ:"+" "+IncomeGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	lineNum=PayoutGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(PayoutGrid.getRowColData(i,3)=="01")
		{
			if(PayoutGrid.getRowColData(i,4)!=PayoutGrid.getRowColData(i,5))
			{
				myAlert(""+"֧�����"+""+(i+1)+""+"������ֵ�ѱ��޸�"+","+"ԭֵΪ:"+" "+PayoutGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	
	return true;
}

function queryClick()
{
  fm.OperateType.value="QUERY";
  window.open("./FrameProfitLossQuery.jsp?Serial=");
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr,content,calResult)
{
  showInfo.close();
  if (FlagStr == "Fail") {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  fm.CalResult.value=calResult;
  } else { 
	  //content="����ɹ���";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  //resetForm();
	  fm.CalResult.value=calResult;
  }
  fm.OperateType.value = "";
  resetForm();
}

function initParameter()
{
	var year = fm.all('CalYear').value ;
	
	if( verifyInput1() == false)
	{
		return false;
	} 
	var recomCode=fm.all('RIcomCode').value;
	
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRProfitLossCalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql102.setSqlId("LRProfitLossCalInputSql102");//ָ��ʹ�õ�Sql��id
		mySql102.addSubPara(recomCode);//ָ������Ĳ���
		mySql102.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	var strSQL=mySql102.getString();
	
	/**
	var strSQL="select a.factorcode,factorname,inputtype from RIProfitLossDef a,RIProLossRela b "
	+ " where a.FactorCode=b.FactorCode and b.recomcode='"+recomCode+"' and b.ricontno='"+fm.ContNo.value+"' order by inputtype"
	;
	*/
	turnPage.queryModal(strSQL,IncomeGrid);
	if(veriryInput4()==false)
	{
		return false;
	}
	var startdate = year + "-01-01";
	var enddate = year + "-12-31";
	var mySql103=new SqlClass();
		mySql103.setResourceName("reinsure.LRProfitLossCalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql103.setSqlId("LRProfitLossCalInputSql103");//ָ��ʹ�õ�Sql��id
		mySql103.addSubPara(year);//ָ������Ĳ���
		mySql103.addSubPara(year);//ָ������Ĳ���
		mySql103.addSubPara(fm.RIcomCode.value);//ָ������Ĳ���
		mySql103.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql103.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	var strSQL=mySql103.getString();
	
	/**
	var strSQL=" select nvl(sum(b.PremChang),0),nvl(sum(b.CommChang),0),nvl(sum(b.ReturnPay),0) from RIPolRecordBake a,rirecordtrace b where a.batchno=b.batchno and a.EventNo=b.EventNo  and a.EventType in('01','02','03') and a.batchno in (select batchno from riwflog where startdate>='"+year+"-01-01' and enddate <='"+year+"-12-31' and nodestate='99') and b.incomecompanyno='"+fm.RIcomCode.value
		+"' and b.RIPreceptNo in (select RIPreceptNo from RIPrecept where RIContNo='"
		+fm.ContNo.value+"') and a.riskcode in (select distinct AssociatedCode from RIAccumulateRDCode c, RIAccumulateDef d where c.AccumulateDefNO = d.AccumulateDefNO and d.DeTailFlag = '01' and c.accumulatedefno in (select accumulatedefno FROM RIPrecept where RIContno = '"+fm.ContNo.value+"')) "
	;
	*/

	
	var strResult1=easyExecSql(strSQL);
	
	var lineNum=IncomeGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(strResult1==null)
		{
			if(IncomeGrid.getRowColData(i,3)=="01")
			{
				IncomeGrid.setRowColData(i,4,"0");
				continue;
			}
		}
		if(IncomeGrid.getRowColData(i,1)=="CessPrem")
		{
			IncomeGrid.setRowColData(i,4,strResult1[0][0]+"");
			IncomeGrid.setRowColData(i,5,strResult1[0][0]+"");
		}
		if(IncomeGrid.getRowColData(i,1)=="ReturnComm")
		{
			IncomeGrid.setRowColData(i,4,strResult1[0][1]+"");
			IncomeGrid.setRowColData(i,5,strResult1[0][1]+"");
		}
		if(IncomeGrid.getRowColData(i,1)=="ReturnPay")
		{
			IncomeGrid.setRowColData(i,4,strResult1[0][2]+"");
			IncomeGrid.setRowColData(i,5,strResult1[0][2]+"");
		}
	}
}

function verifyInput1()
{
	if(fm.CalYear.value==""||fm.CalYear.value==null){
		myAlert(""+"��Ȳ���Ϊ��"+"");
		return false;
	}
	if(!isNumeric(fm.CalYear.value)){
		myAlert(""+"��ȸ�ʽ����"+"");
		return false;
	}
	if(fm.RIcomCode.value==""||fm.RIcomCode.value==null){
		myAlert(""+"�ֱ���˾����Ϊ��"+"");
		return false;
	}
	if(fm.ContNo.value==""||fm.ContNo.value==null){
		myAlert(""+"��ͬ���Ʋ���Ϊ��"+"");
		return false;
	}
	return true;
}

function isNumeric(strValue)
{
  var NUM="0123456789";
  var i;
  if(strValue==null ||strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false
  }
  if(strValue.indexOf(".")!=strValue.lastIndexOf(".")) return false;
  return true;
}

function veriryInput4()
{
	
	return true;
}

function afterQuery()
{
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try{
	  initForm();
	  fm.RILossUWReport.value = "";
	  fm.RILossUWReportName.value = "";
  }
  catch(re){
  	myAlert(""+"��CertifySendOutInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit(){
  //��Ӳ���	
}   

function afterCodeSelect(cCodeName, Field )
{

}

