//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CertifyDesInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
  CardSendOutInfoGrid.clearData();//����������������

  var strSQL="";//��ѰSQL����ִ�
  /*strSQL="select a.certifycode, a.certifyname, a.certifyclass, min(a.makedate), "
  		+"(select max(b.makedate) from lmcertifydestrace b where b.certifycode = a.certifycode and b.state = '1')"
		+"from lmcertifydestrace a where 1=1 "
		+ getWherePart('a.certifycode', 'CertifyCode')		
		+ getWherePart('a.CertifyClass', 'CertifyClass')
		+ getWherePart('a.CertifyClass2', 'CertifyClass2')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
 		+" and exists (select 1	from lmcertifydes where certifycode = a.certifycode "
		+ getWherePart('a.State', 'State')
 		+") group by a.certifycode, a.certifyname, a.certifyclass order by a.certifyclass, a.certifycode";*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('CertifyClass').value,document.all('CertifyClass2').value
                                   ,document.all('MakeDateB').value,document.all('MakeDateE').value,document.all('State').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid);
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
