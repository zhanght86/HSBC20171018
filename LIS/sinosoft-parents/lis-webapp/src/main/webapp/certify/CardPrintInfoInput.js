//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CardPrintInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
  CardPrintInfoGrid.clearData();//����������������
  if (fm.ManageCom.value==null || fm.ManageCom.value=='')
  {
    alert("������ӡˢ������");
    return false;
  }
  var strSQL="";//��ѰSQL����ִ�
  /*strSQL="select a.prtno,a.plantype,a.certifycode,"
	+" (select certifyname from lmcertifydes where certifycode=a.certifycode),"
	+" (select unit from lmcertifydes where certifycode=a.certifycode),"
	+" a.certifyprice, a.startno, a.endno, a.sumcount, a.printery, a.maxdate, a.printdate,"
	+" decode(a.State, '1', '��ӡˢ', '2', '�����') State"
	+" from lzcardprint a where 1=1 and a.state in ('1','2') " 
	+ getWherePart('a.ManageCom', 'ManageCom', 'like')
	+ getWherePart('a.PlanType', 'PlanType')
	+ getWherePart('a.State', 'State')
	+ getWherePart('a.certifycode', 'CertifyCode') 		
 	+ getWherePart('a.printdate', 'MakeDateB','>=')
 	+ getWherePart('a.printdate', 'MakeDateE','<=')
 	+ getWherePart('a.PrintMan', 'Operator') 
 	+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 	+ getWherePart('certifyclass', 'CertifyClass') 
 	+ getWherePart('CertifyClass2', 'CertifyClass2') + ")"  
 	+" order by a.printdate, a.plantype, a.certifycode";*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ManageCom').value,document.all('PlanType').value,document.all('State').value
                                   ,document.all('CertifyCode').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                   ,document.all('Operator').value,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardPrintInfoGrid);
   	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(CardPrintInfoGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
}

//[��ӡ]��ť����
function certifyPrint()
{
	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(CardInfoGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardInfoGrid','turnPage');	
}
