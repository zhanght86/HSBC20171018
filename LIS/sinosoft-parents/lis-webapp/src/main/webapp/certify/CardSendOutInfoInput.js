//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CardSendOutInfoInputSql";
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
  /*strSQL="select a.certifycode,(select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+" a.startno, a.endno, a.sumcount, a.makedate, a.sendoutcom, a.receivecom,"
		+" (case substr(a.receivecom, 0, 1)"
		+" when 'A' then (select name from ldcom where comcode = substr(a.receivecom, 2, 10))"
		+" when 'B' then (case substr(a.receivecom,length(a.receivecom)-1,length(a.receivecom)) when '01' then '����ҵ��' when '02' then '���б��ղ�' when '03' then '��Ԫ���۲�' when '04' then '������Ŀ��' when '05' then '���Ѳ�' when '06' then '��ѵ��' when '07' then '�г���' when '08' then '�ͻ�����(�ֹ�˾Ϊ��Ӫ��)' when '09' then '����'  when '10' then 'ҵ�����' end )"
		+" when 'D' then (select name from laagent where agentcode = substr(a.receivecom, 2, 10))"
		+" else	'δ֪' end),agentcom,(select name from lacom where agentcom=a.agentcom)"
		+" from lzcardtrack a where 1 = 1 and (a.stateflag = '2' or a.stateflag = '3')"
		+" and (a.sendoutcom like 'A'||'" + ManageCom + "%' or a.sendoutcom like 'B'||'" + ManageCom + "%')"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.sendoutcom', 'sendoutcom')
		+ getWherePart('a.receivecom', 'receivecom')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
	 	+" and exists (select *	from lmcertifydes b where b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+") order by a.makedate, a.sendoutcom, a.receivecom, a.certifycode, a.startno" ;*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,document.all('CertifyCode').value,document.all('sendoutcom').value
                                   ,document.all('receivecom').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                   ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid, 1);
   	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
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

