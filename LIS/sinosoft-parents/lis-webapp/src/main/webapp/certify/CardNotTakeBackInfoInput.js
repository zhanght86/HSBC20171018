//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CardNotTakeBackInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
  if(verifyInput() == false)
  {
	return;
  }
  CardSendOutInfoGrid.clearData();//����������������

    var ManageCom = fm.ManageCom.value;
    var strSQL="";//��ѰSQL����ִ�
    /*strSQL="select a.certifycode, "
  		+" (select certifyname from lmcertifydes where '1235121562000' = '1235121562000' and certifycode = a.certifycode),"
		+" a.startno, a.endno, a.SumCount, a.modifydate, "
		+" (case substr(a.receivecom, 0, 1) "
		+" when 'D' then "
		+" a.makedate +(select validate1 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype not in (2, 3, 6, 7) and agentcode = substr(a.receivecom, 2, 20)) "
		+" union select validate2 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype in (2, 3, 6, 7) and agentcode = substr(a.receivecom, 2, 20))) "
		+" end)-1 maxdate, " //��Чʹ����=��������+��֤������Чʹ������				
		+" case a.stateflag when '3' then '�ѷ���δ���� ' when '8' then '����' when '9' then '��ʧ' else '�Ƿ�״̬' end," 
		+" a.receivecom,"
		+" (case substr(a.receivecom, 0, 1)"
		+" when 'A' then (select name from ldcom where comcode = substr(a.receivecom, 2, 10))"
		+" when 'B' then (case substr(a.receivecom, length(a.receivecom) - 1, length(a.receivecom)) when '01' then '����ҵ��' when '02' then '���б��ղ�' when '03' then '��Ԫ���۲�' when '04' then '������Ŀ��' when '05' then '���Ѳ�' when '06' then '��ѵ��' when '07' then '�г���' when '08' then '�ͻ�����(�ֹ�˾Ϊ��Ӫ��)' when '09' then '����'  when '10' then 'ҵ�����' end) "
		+" when 'D' then (select name from laagent where agentcode = substr(a.receivecom, 2, 10)) else 'δ֪' end),a.agentcom,(select name from lacom where agentcom=a.agentcom)"
		+" from (select * from lzcard union all  select * from lzcardb) a "
		+" where 1 = 1 and a.stateflag in ('3','8','9')"
		+" and ((a.receivecom like 'A'||'"+ManageCom+"%' or a.receivecom like 'B'||'"+ManageCom+"%')"
		+" or ( exists (select 1 from laagent where agentcode = substr(a.receivecom, 2) and (managecom like '"+ManageCom+"%'))))"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.StateFlag', 'StateFlagN')
		+ getWherePart('a.sendoutcom', 'sendoutcom')
		+ getWherePart('a.receivecom', 'receivecom')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
	 	+" and exists (select *	from lmcertifydes b where b.certifyclass in ('D','B') and b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+") order by a.makedate, a.certifycode, a.startno" ;*/
  strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,ManageCom,document.all('CertifyCode').value,document.all('StateFlagN').value
                                   ,document.all('sendoutcom').value,document.all('receivecom').value,document.all('MakeDateB').value
                                   ,document.all('MakeDateE').value,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	 /**turnPage.queryModal(strSQL, CardSendOutInfoGrid, 1);
   	//prompt("strSQL��",strSQL);
  if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}**/
   	
   	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�в�ѯ���κε�֤��Ϣ��");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CardSendOutInfoGrid;
          
  //����SQL���
  turnPage.strQuerySql = strSQL;
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  MulLinereflash();
}

//[��ӡ]��ť����
function certifyPrint()
{
	//alert("��ѯ���ļ�¼������"+CardSendOutInfoGrid.mulLineCount);
   	if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
   	
   	
   	//alert(turnPage.queryAllRecordCount);
   	
   	if(turnPage.queryAllRecordCount>100000)
   	{
   		alert("һ�δ�ӡ�����ݳ���100000��,�뾫ȷ��ѯ������");
   		return false;
   	}

	easyQueryPrint(2,'CardSendOutInfoGrid','turnPage');	
}

//��ȡ�Ǹ��˴��������ڻ������롢����
function getAgentCom()
{
	var receivecom = document.all('ReceiveCom').value;
	if(receivecom!=null && receivecom != "" && receivecom.substring(0,1) == 'D')	 
	{
		/*var strSQL="select a.agentcom,(select name from lacom where agentcom=a.agentcom) "
		+" from lacomtoagent a where a.relatype='1' "//���� 3
		+" and a.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select b.agentcom,b.name "
		+" from lacom b where b.comtoagentflag='1' "//���� 6
		+" and b.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select c.agentcom,(select name from lacom where agentcom=c.agentcom)  "
		+" from lacomtoagentnew c where c.relatype='2' "//�н� 7
		+" and c.agentcode='" + receivecom.substring(1, 12) + "'";*/
		var strSQL = wrapSql(tResourceName,"querysqldes2",[receivecom.substring(1, 12),receivecom.substring(1, 12),receivecom.substring(1, 12)]);
		
		if(easyQueryVer3(strSQL)!=false){
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = easyQueryVer3(strSQL); 	
		}else{
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = "";
		}
	}
}

