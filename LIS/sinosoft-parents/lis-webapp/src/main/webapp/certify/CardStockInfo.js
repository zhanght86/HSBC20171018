//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ��� 
var tResourceName="certify.CardStockInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
  if(fm.ManageCom.value==null ||��fm.ManageCom.value==''){
    alert("��¼�롾�����������");
    return false;
  }
  initCardSendOutInfoGrid();
  
  if(fm.ManageCom.value!=null && fm.ManageCom.value!=""){
    fm.hidManageCom.value = "A"+fm.ManageCom.value;
  }
  if(fm.Grade.value!=null && fm.Grade.value!=""){
    fm.hidGrade.value = (parseInt(fm.Grade.value)+1)+"";
  }
  //alert(fm.hidManageCom.value);
  //alert(fm.hidGrade.value);

  var ManageCom = fm.ManageCom.value;
  var strSQL="";//��ѰSQL����ִ�
  /*strSQL="select a.receivecom, (select name from ldcom where comcode = substr(a.receivecom, 2)), "
		+" (select certifyclass from lmcertifydes where certifycode = a.certifycode) certifyclass, "
		+" a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode), "
		+" a.startno, a.endno, a.sumcount, '' "
		+" from lzcard a "
		+" where a.stateflag in ('2','3') "
		+ getWherePart('a.receivecom', 'hidManageCom', 'like')
		+ getWherePart('length(a.receivecom)', 'hidGrade', '=', '1')	
		+ getWherePart('a.certifycode', 'CertifyCode')
 		+ getWherePart('a.MakeDate', 'MakeDateB','>=')
 		+ getWherePart('a.MakeDate', 'MakeDateE','<=')
	 	+" and exists (select 1	from lmcertifydes b where b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+") order by certifyclass, a.certifycode, a.receivecom, a.startno" ;*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('hidManageCom').value,document.all('hidGrade').value
                                   ,document.all('CertifyCode').value,document.all('MakeDateB').value,document.all('MakeDateE').value
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

