//�������ƣ�FinFeeSure.js
//�����ܣ�����ȷ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();
var tResourceName = "customer.CustomerQuerySql";
var tResourceSQL1="CustomerQuerySql1";

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	initCustomerGrid();
	document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		alert(content); 
	}
	else
	{ 	
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		alert(content);
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



// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initCustomerGrid();
	k++;


	// ��дSQL���
	var strSQL = "";
	var strSQL2 = "";
//	strSQL = "select a.insuaccno,a.customerno,b.name,a.contno,a.operationno,a.operationtype,(case a.operationtype when '1' then '�µ�' when '2' then '����' when '3' then '��ȫ' when '4' then '�ⰸ' else '��ȡ' end),(case a.opertype when '1' then '�շ�' when '2' then 'Ӧ��ת��'"
//+" when '3' then '�˻�ת��'  when '4' then 'ҵ��ʹ��'  when '5' then '�˷�'  when '6' then '�˻�ת��'  when '7' then '����ָ��' else '' end"
//+"),a.otherno,a.money,getcurrname(a.currency),a.sequence,case a.state when '00' then 'δʹ��' when '01' then '��ʹ��'when '02' then '����ȡ'when '03' then '����' end "
//+",case a.dcflag when 'D' then '��' else '��' end,((case a.paymode when '1' then '�����ֽ�' when '2' then '�ֽ�֧Ʊ' when '3' then 'ת��֧Ʊ' when '4' then '���й���' when '5' then 'Ӧ��ת��' when '6' then 'POS�տ�' when '7' then '������������' when '8' then '���ƾ֤' when 'A' then '����ͨ' end)),a.makedate,a.maketime  from ficustomeracctrace a,ldperson b where b.customerno=a.customerno ";
  if (fm.InsuAccNo.value!="")
  {
  	 //strSQL = strSQL + " and a.insuaccno='"+ fm.InsuAccNo.value+"'";
  	  strSQL2 = strSQL2 + " and a.insuaccno='"+ fm.InsuAccNo.value+"'";
  }
  if (fm.CustomerNo.value!="")
  {
  	 //strSQL = strSQL + " and a.CustomerNo='"+ fm.CustomerNo.value+"'";
  	 strSQL2 = strSQL2 + " and a.CustomerNo='"+ fm.CustomerNo.value+"'";
  }
  if (fm.CustomerName.value!="")
  {
  	 //strSQL = strSQL + " and b.name='"+ fm.CustomerName.value+"'";
  	 strSQL2 = strSQL2 + " and b.name='"+ fm.CustomerName.value+"'";
  }
    if (fm.OperationNo.value!="")
  {
  	 //strSQL = strSQL + " and a.OperationNo='"+ fm.OperationNo.value+"'";
  	  strSQL2 = strSQL2 + " and a.OperationNo='"+ fm.OperationNo.value+"'";
  }
    if (fm.OtherNo.value!="")
  {
  	 //strSQL = strSQL + " and a.OtherNo='"+ fm.OtherNo.value+"'";
  	 strSQL2 = strSQL2 + " and a.OtherNo='"+ fm.OtherNo.value+"'";
  }		 
  //strSQL = strSQL + " order by a.makedate desc,a.maketime desc";
  strSQL = wrapSql(tResourceName,tResourceSQL1,[strSQL2]); 
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����ݣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	 
				 
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CustomerGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  return true;
}

//����ȷ��
function autochk()
{

	var haveCheck = false;
	for (i=0; i<CustomerGrid.mulLineCount; i++) 
	{
		if (CustomerGrid.getChkNo(i)) 
		{
			haveCheck = true;
			break;
		}
	}

	if (haveCheck) {
		var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		//showSubmitFrame(mDebug);
		fm.action="./CustomerQueryChk.jsp";
		document.getElementById("fm").submit(); //�ύ
	}
	else 
	{
		alert("����ѡ��һ���ݽ�����Ϣ����ѡ����д򹳣�");
	}
}


