//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();
var tResourceName="bank.RenewBankConfirmSql";
var tResourceSQL1="RenewBankConfirmSql1";
var tResourceSQL2="RenewBankConfirmSql2";
var tResourceSQL3="RenewBankConfirmSql3";
var tResourceSQL4="RenewBankConfirmSql4"; 

//�򵥲�ѯ
function easyQueryClick()
{
	// ��ʼ�����
	initCodeGrid();

	var prtno = document.all('PrtNo').value;
	var	addsql = "";
	if(prtno != null && trim(prtno) != "")
		//addsql = " and exists (select 1 from lccont where contno = a.otherno and prtno = '" + prtno + "') ";
		 addsql = wrapSql(tResourceName,tResourceSQL1,[prtno]); 
	
	var salechnl = document.all('SaleChnl').value;
	if(salechnl != null && trim(salechnl) != "")
		//addsql += " and exists (select 1 from lccont where contno = a.otherno and salechnl = '" + salechnl + "') ";
		addsql = wrapSql(tResourceName,tResourceSQL2,[salechnl]); 
		
	// ��дSQL���
//	var strSQL = "select (select salechnl from lccont where contno = a.otherno),"
//						 + "(select prtno from lccont where contno = a.otherno),"
//						 + "otherno,trim(accname),"
//						 + "(select riskcode from lcpol where contno=a.otherno and polno=mainpolno and appflag='1'),"
//						 + "startpaydate,bankaccno,getnoticeno,managecom,agentcom "
//						 + "from ljspay a "
//						 + "where othernotype = '2' and bankaccno is not null and bankonthewayflag <> '1' "
//						 + "and paytypeflag = '1' "
//						 + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') "
//						 + "and not exists(select 1 from lyrenewbankinfo where getnoticeno = a.getnoticeno and paytodate = a.startpaydate and state = '0') "
//						 + addsql 
//						 + getWherePart('otherno', 'ContNo') 
//						 + getWherePart('managecom', 'ManageCom','like','0') 
//						 + getWherePart('accname', 'AppntName') 
//						 + getWherePart('agentcom', 'AgentCom') 
//						 + getWherePart('startpaydate','StartDate','>=','0')
//						 + getWherePart('startpaydate','EndDate','<=','0') 
//						 + " order by startpaydate,managecom,1 ";
	strSQL = wrapSql(tResourceName,tResourceSQL3,[addsql,fm.ContNo.value,fm.ManageCom.value,fm.AppntName.value,fm.AgentCom.value,fm.StartDate.value,fm.EndDate.value]); 
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ���ȷ�ϣ�\n�ٸñ����Ƿ�Ϊת�ʼ���\n�ڸñ����Ƿ��Ѵ��գ�\n�۸ñ����Ƿ�����ֹ��");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CodeGrid;    
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var tChk = CodeGrid.getChkCount();	
	//alert(tChk);
	if( tChk == null || tChk <= 0 )
	{
		alert( "����ѡ���¼���ٵ��ת��ȷ�ϰ�ť��" );
		return false;
	}else{        
		var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  		//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   	
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  		document.all('confirm').disabled = true;
  		document.getElementById("fm").submit(); //�ύ
     }
}

function queryAgentCom()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}	
    if(document.all('AgentCom').value == "")	{  
	  var newWindow = window.open("../sys/AgentComQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentComQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  	  
	}	
	if(document.all('AgentCom').value != "")	 {
		var cAgentCom = fm.AgentCom.value;  //��������	
		//var strSql = "select AgentCom,Name from LACom where AgentCom='" + cAgentCom +"'";
		var strSQL = wrapSql(tResourceName,tResourceSQL4,[cAgentCom]); 
	
    	var arrResult = easyExecSql(strSql);
    	if (arrResult != null) {
      		alert("��ѯ���:�����������:["+arrResult[0][0]+"],�����������:["+arrResult[0][1]+"]");
      	}else{
      		alert("û�в�ѯ���˴��������Ϣ!");
      	} 
	}	
}

function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCom.value = arrResult[0][0];
  }
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	document.all('confirm').disabled = false;
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
  
	easyQueryClick();
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
