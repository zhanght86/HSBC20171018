//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CardTakeBackInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
  if(verifyInput() == false)
  {
	return;
  }
  CardSendOutInfoGrid.clearData();//����������������

  var ManageCom = fm.ManageCom.value;
  var strSQL= "";//��ѰSQL����ִ�
  /*strSQL="select a.certifycode, "
  		+" (select certifyname from lmcertifydes where '1235121562000' = '1235121562000' and certifycode = a.certifycode),"
		+" a.startno, a.endno, a.SumCount, a.makedate, "
		+" (case substr(a.sendoutcom, 0, 1) "
		+" when 'D' then "
		+" (select max(makedate) from lzcardtrack where certifycode = a.certifycode and startno <= a.startno and endno>=a.startno and receivecom = a.sendoutcom)+ "
        +" (select validate1 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype not in (2, 3, 6, 7) and agentcode = substr(a.sendoutcom, 2, 20)) "
		+" union select validate2 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype in (2, 3, 6, 7) and agentcode = substr(a.sendoutcom, 2, 20))) "
		+" end)-1 maxdate, "//��Чʹ����=���Ź켣�������+��֤������Чʹ������
		+" case a.stateflag when '4' then '�Զ�����' when '5' then '�ֹ�����' when '6' then 'ʹ������' when '7' then 'ͣ������' when '10' then '��ʧ' else '�Ƿ�״̬' end," 
		+" a.receivecom,"
		+" (case substr(a.receivecom, 0, 1)"
		+" when 'A' then (select name from ldcom where comcode = substr(a.receivecom, 2, 10))"
		+" when 'B' then (case substr(a.receivecom, length(a.receivecom) - 1, length(a.receivecom)) when '01' then '����ҵ��' when '02' then '���б��ղ�' when '03' then '��Ԫ���۲�' when '04' then '������Ŀ��' when '05' then '���Ѳ�' when '06' then '��ѵ��' when '07' then '�г���' when '08' then '�ͻ�����(�ֹ�˾Ϊ��Ӫ��)' when '09' then '����'  when '10' then 'ҵ�����' end) "
		+" when 'D' then (select name from laagent where agentcode = substr(a.receivecom, 2, 10)) else 'δ֪' end)"
		+" from lzcardB a where 1 = 1 and a.stateflag in ('4','5','6','7','10')"
		//+" and (a.receivecom like 'A'||'"+ManageCom+"%' or a.receivecom like 'B'||'"+ManageCom+"%')"		
		+" and ((a.sendoutcom like 'A'||'"+ManageCom+"%' or a.sendoutcom like 'B'||'"+ManageCom+"%')"
		+" or ( exists (select 1 from laagent where agentcode = substr(a.sendoutcom, 2) and (managecom like '"+ManageCom+"%'))))"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.sendoutcom', 'sendoutcom')
		+ getWherePart('a.receivecom', 'receivecom')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
	 	+" and exists (select 1	from lmcertifydes b where b.certifyclass in ('D','B') and b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+")" ;*/
  var whereflag1='';
  var whereflag2='';
  if(fm.INinvalidate.value != ''){
    if(fm.INinvalidate.value == 'Y'){
        /*strSQL+="and (a.sendoutcom like 'A%' "
    		+" or a.sendoutcom like 'B%' "
    		+" or exists (select 1 from lmcertifydes where certifycode = a.certifycode and havevalidate='N') "
			+" or a.makedate <= (select max(makedate) from lzcardtrack where certifycode = a.certifycode and startno <= a.startno and endno>=a.startno and receivecom = a.sendoutcom)+ "
			+" (select validate1 from lmcertifydes where certifycode = a.certifycode and exists	(select 1 from laagent where branchtype not in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))"
			+" union select validate2 from lmcertifydes where certifycode = a.certifycode	and exists (select 1 from laagent where branchtype in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))))";
      //strSQL+=" and (a.makedate <= a.invalidate or a.invalidate is null)";*/
        whereflag1='1';
    }else if(fm.INinvalidate.value == 'N'){
         /*strSQL+="and (a.sendoutcom like 'D%' "
    		+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode and havevalidate='Y') "
			+" and a.makedate > (select max(makedate) from lzcardtrack where certifycode = a.certifycode and startno <= a.startno and endno>=a.startno and receivecom = a.sendoutcom)+ "
			+" (select validate1 from lmcertifydes where certifycode = a.certifycode and exists	(select 1 from laagent where branchtype not in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))"
			+" union select validate2 from lmcertifydes where certifycode = a.certifycode	and exists (select 1 from laagent where branchtype in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))))";
      //strSQL+=" and a.makedate > a.invalidate ";*/
         whereflag2='1';
    }  
  }	
	//strSQL+=" order by a.makedate, a.certifycode, a.startno";
	strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,ManageCom,document.all('CertifyCode').value,document.all('sendoutcom').value
	                                   ,document.all('receivecom').value,document.all('MakeDateB').value,document.all('MakeDateE').value
	                                   ,document.all('CertifyClass').value,document.all('CertifyClass2').value,whereflag1,whereflag2]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid, 1);
   	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	
   	//prompt("",strSQL);
   	
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
