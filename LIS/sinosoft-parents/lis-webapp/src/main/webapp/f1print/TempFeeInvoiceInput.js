//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet;
var mDebug="0";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass(); 
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
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

//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(verifyInput()) 
  {
  	document.getElementById("fm").submit();//�ύ
  }
  
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}

// ��ѯ��ť
function easyQueryClick()
{	


	if(document.all('AgentCode').value == "" 
		&& document.all('ContNo').value == "" ){
		alert("��ͬ���롢��Ա���롢��������дһ���ٽ��в�ѯ");
		return;
	}
  if(!verifyInput()) 
  {
	 // alert(2);
  	return false;
  }
	// ��ʼ�����
	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	var DateSQL1 = "";
	var DateSQL2 = "";
	
	var StartDate = addDate('4',60,fm.StartDate.value); //����� 
	var EndDate = addDate('4',60,fm.EndDate.value); //����� 
	var sqlid="";
	//if(_DBT==DBO){
//		strSQL = "select a.otherno,(select name from ldperson where customerno = a.appntno),a.sumduepaymoney,"
//		       + " (select max(lastpaytodate) from ljspayperson where getnoticeno = a.getnoticeno) minpaytodate, "
//		       + " (select max(paycount) from ljspayperson where getnoticeno = a.getnoticeno), "
//		       + " (select codename from ldcode  where codetype = 'salechnl' and code = (select salechnl from lccont where contno = a.otherno)), "
//		       + " (case (select distinct 1 from lrascription where contno = a.otherno) when 1 then '��' else '��' end), "
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
//		       + " (case (select distinct 1 from lrascription where contno = a.otherno) when 1 then '��' else '��' end), "
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
	mySql0.setResourceName("f1print.TempFeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(MngCom0);//ָ������Ĳ���
	mySql0.addSubPara(StartDate);//ָ������Ĳ���
	mySql0.addSubPara(EndDate);//ָ������Ĳ���
	mySql0.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	mySql0.addSubPara(AgentCode0);//ָ������Ĳ���
	strSQL=mySql0.getString();
	 //alert(1);
	 //alert(strSQL);
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL); 
    
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=û�в�ѯ����������������" ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);   
 
}

//��Ʊ��ӡ
function Print()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
			alert( "����ѡ��һ����¼���ٵ����ӡ��ť��" );
			return;
	}
	else
	{
	  var cGetNoticeNo = PolGrid.getRowColData(tSel - 1,11);		
		if (cGetNoticeNo == ""){
				alert("��Ч��Ӧ����Ϣ");
		    return;
		  }
	} 
	
//var tSql = "select count(1) from (select 1 from ljspayperson where GetNoticeNo='" + cGetNoticeNo +"' and paytype='ZC' group by riskcode)";
	
	var sqlid2="TempFeeInvoiceInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("f1print.TempFeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(cGetNoticeNo);//ָ������Ĳ���
	var tSql=mySql2.getString();

	turnPage1.strQueryResult  = easyQueryVer3(tSql, 1, 1, 1);  
	if (turnPage1.strQueryResult) 
  {	
//  	var tnSql = "select 1 from dual Where (select Count(Distinct riskcode) from ljspayperson where GetNoticeNo='" + cGetNoticeNo +"' and paytype='ZC')>7";
	
  	var sqlid3="TempFeeInvoiceInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("f1print.TempFeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(cGetNoticeNo);//ָ������Ĳ���
	var tnSql=mySql3.getString();
  	
  	turnPage1 = new turnPageClass(); 
  	turnPage1.strQueryResult  = easyQueryVer3(tnSql, 1, 1, 1);

			if (turnPage1.strQueryResult) {
				alert("�ѳ���7���շ���Ŀ��ֻ�ܴ�ӡ��ǰ7��");	
			}
	}
  else
  {
		alert("û��Ӧ����Ϣ����ȷ��!");
		return;
  }


//����1000Ԫ��Ҫȷ��	
	var aMoney = PolGrid.getRowColData(tSel - 1,3);		 
	if(aMoney>=1000 && confirm("Ӧ�ս���1000Ԫ���Ƿ������ӡ?")){
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
	//������Ҫ���ص�����
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}




//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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


//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  
}

function queryAgentAll(comcode)
{
  if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //��������	
//		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
	  	var sqlid4="TempFeeInvoiceInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("f1print.TempFeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(cAgentCode);//ָ������Ĳ���
		var strSql=mySql4.getString();
		
		var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult == null) 
    	{
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    	}
    else
    	{
//    	  var nSql = "select name from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";
    	  
	  	  	var sqlid5="TempFeeInvoiceInputSql5";
			var mySql5=new SqlClass();
			mySql5.setResourceName("f1print.TempFeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
			mySql5.addSubPara(trim(fm.AgentCode.value));//ָ������Ĳ���
			var nSql=mySql5.getString();
    	  
    	  arrResult = easyExecSql(nSql);
    	  fm.AgentName.value=arrResult;
    	}
	}
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}