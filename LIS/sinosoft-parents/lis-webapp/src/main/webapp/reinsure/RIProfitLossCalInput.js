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
//��ѯ
function queryClick()
{
  fm.OperateType.value="QUERY";
  window.open("./FrameProfitLossQuery.jsp?Serial=");
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
//�ύ�����水ť��Ӧ����  ,Ӧ��Ӷ�����
function submitForm()
{
	
	//������ȷ���򲻿�������
	var strSql = " select '1' from RIProLossResult where CalYear='"+fm.CalYear.value
		+"'  and ReComCode ='"+fm.RIcomCode.value+"' and RIContNo='"+fm.ContNo.value+"' and StandbyString1 ='03' " ;
	
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
					
				fm.action="RIProfitLossCalSave.jsp";
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
		if(IncomeGrid.getRowColData(i,7)=="01")
		{
			if(IncomeGrid.getRowColData(i,5)!=IncomeGrid.getRowColData(i,6))
			{
				myAlert(""+"���������"+""+(i+1)+""+"��"+"'"+IncomeGrid.getRowColData(i,3)+"'"+"�ѱ��޸�"+","+"ԭֵΪ:"+" "+IncomeGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	lineNum=PayoutGrid.mulLineCount;
	for(var i=0;i<lineNum;i++)
	{
		if(PayoutGrid.getRowColData(i,7)=="01")
		{
			if(PayoutGrid.getRowColData(i,5)!=PayoutGrid.getRowColData(i,6))
			{
				myAlert(""+"֧�����"+""+(i+1)+""+"������ֵ�ѱ��޸�"+","+"ԭֵΪ:"+" "+PayoutGrid.getRowColData(i,5));
				return false;
			}
		}
	}
	
	return true;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ��� SDFSFDSFSFDdfsfsfsdfsdf
function afterSubmit(FlagStr,content,calResult)
{
	if("param"!=calResult)
	{
		showInfo.close();	
	}

	if (FlagStr == "Fail") 
	{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		fm.CalResult.value=calResult;
	} 
	else 
	{ 
		if("param"==calResult)
		{
			afterInit();
		}
		else
		{
			fm.CalResult.value=calResult;
			//content="����ɹ���";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
			
			showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		}
		
	}
	
	if("param"!=calResult)
	{
		fm.OperateType.value = "";
		resetForm();	
	}
	

}

//��ʼ������
function initParameter()
{
	var year = fm.all('CalYear').value
	
	if( verifyInput1() == false)
	{
		return false;
	} 
	fm.action="./LRProfitLossParamSave.jsp";
	fm.submit(); //�ύ	
}

function afterInit()
{
	var recomCode=fm.all('RIcomCode').value;
	var strSQL="select decode (a.InOutType,'01','"+"����"+"','"+"֧��"+"'),a.factorcode,a.factorname,decode(b.inputtype,'01','"+"ϵͳ����"+"','"+"�ֹ�¼��"+"'),c.Factorvalue,c.Factorvalue,b.inputtype,d.Serialno from RIProfitLossDef a,RIProLossRela b,RIProLossValue c,RIProLossResult d "
				+ " where a.FactorCode = b.FactorCode and c.Serialno = d.Serialno and b.recomcode = c.Recomcode and b.ricontno = c.Ricontno and b.Factorcode = c.Factorcode and d.recomcode='"+recomCode+"' and d.ricontno='"+fm.ContNo.value+"' and d.Calyear = '"+fm.CalYear.value+"' order by a.InOutType,b.inputtype";
	turnPage.queryModal(strSQL,IncomeGrid);	
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
	var strSQL = "select a.SerialNo,a.RIContNo,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),"
		+ " a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode) x,"
		+ " a.CalYear,a.ProLosAmnt,decode(StandbyString1,'01','"+"�����"+"','02','"+"����޸�"+"','03','"+"���ȷ��"+"'),StandbyString1 from RIProLossResult a where StandbyString1 ='01' "
		;
	strSQL = strSQL +" order by a.SerialNo desc ";
	var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	turnPage.queryModal(strSQL,LossUWListGrid)

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
  	myAlert(""+"��LRProfitLossCalInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit(){
  //��Ӳ���	
}   

function afterCodeSelect(cCodeName, Field )
{

}

