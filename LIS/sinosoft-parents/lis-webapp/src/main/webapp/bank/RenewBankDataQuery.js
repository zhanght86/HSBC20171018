//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();

var tResourceName="bank.RenewBankDataQuerySql";
var tResourceSQL1="RenewBankDataQuerySql1";
var tResourceSQL2="RenewBankDataQuerySql2";
var tResourceSQL3="RenewBankDataQuerySql3";
var tResourceSQL4="RenewBankDataQuerySql4"; 

//�򵥲�ѯ
function easyQueryClick()
{
	// ��ʼ�����
	initCodeGrid();

	var prtno = document.all('PrtNo').value;
	var	addsql="";
	if(prtno != null && trim(prtno) != "")
		//addsql = " and exists(select 1 from lccont where contno = a.otherno and prtno = '" + prtno + "') ";
		addsql = wrapSql(tResourceName,tResourceSQL1,[prtno]); 
	// ��дSQL���
	//var strSQL = "select (select prtno from lccont where contno = a.otherno),"
	//					 +"a.otherno,trim(a.accname),"
	//					 + "(select riskcode from lcpol where contno=a.otherno and polno=mainpolno and appflag='1'),"
	//					 + "a.startpaydate,"
	//					 + "(decode(bankonthewayflag,'1','3',decode((select state from lyrenewbankinfo where serialno = b.serialno and getnoticeno = b.getnoticeno and contno = b.contno),'0','2','1'))),"
	//					 + "b.confirmoperator,b.makedate,"
	//					 + "(select undooperator from lyrenewbankinfo where serialno = b.serialno and getnoticeno = b.getnoticeno and contno = b.contno and state = '1'),"
	//					 + "(select modifydate from lyrenewbankinfo where serialno = b.serialno and getnoticeno = b.getnoticeno and contno = b.contno and state = '1') "
	//					 + "from ljspay a ,lyrenewbankinfo b "
	//					 + "where a.getnoticeno = b.getnoticeno(+) "
	//					 + "and a.otherno = b.contno(+) "
	//					 + "and a.othernotype = '2' "
	//					 + "and a.bankaccno is not null "
	//					 + "and a.paytypeflag = '1' "
	//					 //+ "and a.riskcode in (select riskcode from lmriskrnew) "
	//					 + addsql 
	//					 + getWherePart('a.otherno', 'ContNo') 
	//					 + getWherePart('a.managecom', 'ManageCom','like','0') 
	//					 + getWherePart('a.accname', 'AppntName') 
	//					 + getWherePart('a.startpaydate','StartDate','>=','0')
	//					 + getWherePart('a.startpaydate','EndDate','<=','0') 
	//					 + " order by 1,2,8 ";
	//alert(strSQL);
	var strSQL = wrapSql(tResourceName,tResourceSQL2,[addsql,fm.ContNo.value,fm.ManageCom.value,fm.AppntName.value,fm.StartDate.value,fm.EndDate.value]); 
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�в鵽���ݣ�\n��ȷ��ת��״̬��������ֻ�ܲ�ѯ����ת��ȷ�ϵ�δ���̵���Ч����������");
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
		var strSql = "select AgentCom,Name from LACom where AgentCom='" + cAgentCom +"'";
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

//�ύ�����水ť��Ӧ����
function submitForm()
{
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
	document.all('undo').disabled = true;
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	document.all('undo').disabled = false;
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
