//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CertifyTrackInfoInputSql";
//��֤�켣��ѯ
function certifyTrackQuery()
{
    if(verifyInput() == false)
    {
	  return;
    }	
	initCardInfoGrid();
	
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
	var strSQL="";//��ѰSQL����ִ�
	/*strSQL="select * from (select t.certifycode,"
	    +" (select lmcertifydes.certifyname from lmcertifydes where lmcertifydes.certifycode=t.certifycode),"
		+" t.sendoutcom, t.receivecom, t.startno, t.endno, t.sumcount,"
		+" (case t.stateflag when '1' then '�����' when '2' then '�����' when '3' then '�ѷ���δ����' when '4' then '�Զ�����' when '5' then '�ֹ�����' when '6' then 'ʹ������' when '7' then 'ͣ������' when '8' then '����' when '9' then '��ʧ' when '10' then '��ʧ' when '11' then '����' else '' end),"
		+" t.handler, t.operator,  t.modifydate , t.modifytime "
		+" from lzcardtrack t where 1=1 "
		+ getWherePart('t.CertifyCode','CertifyCode')
		+ getWherePart('t.StartNo','EndNo','<=')
		+ getWherePart('t.EndNo','StartNo','>=')			
		+ getWherePart('t.StateFlag','StateFlag')		
		+ getWherePart('t.SendOutCom','SendOutCom')
		+ getWherePart('t.ReceiveCom','ReceiveCom')
		+ getWherePart('t.Operator','Operator')
		+ getWherePart('t.Handler','Handler')	
		+ getWherePart('t.MakeDate','MakeDateB','>=')
		+ getWherePart('t.MakeDate','MakeDateE','<=')
		+" union "
		+" select t.certifycode,"
	    +" (select lmcertifydes.certifyname from lmcertifydes where lmcertifydes.certifycode=t.certifycode),"
		+" t.sendoutcom, t.receivecom, t.startno, t.endno, t.sumcount,"
		+" (case t.stateflag when '1' then '�����' when '2' then '�����' when '3' then '�ѷ���δ����' when '4' then '�Զ�����' when '5' then '�ֹ�����' when '6' then 'ʹ������' when '7' then 'ͣ������' when '8' then '����' when '9' then '��ʧ' when '10' then '��ʧ' when '11' then '����' else '' end),"
		+" t.handler, t.operator,  t.modifydate , t.modifytime "
		+" from lzcardtrackB t where 1=1 "
		+ getWherePart('t.CertifyCode','CertifyCode')
		+ getWherePart('t.StateFlag','StateFlag')		
		+ getWherePart('t.SendOutCom','SendOutCom')
		+ getWherePart('t.ReceiveCom','ReceiveCom')
		+ getWherePart('t.Operator','Operator')
		+ getWherePart('t.Handler','Handler')
		+ getWherePart('t.StartNo','EndNo','<=')
		+ getWherePart('t.EndNo','StartNo','>=')		
		+ getWherePart('t.MakeDate','MakeDateB','>=')
		+ getWherePart('t.MakeDate','MakeDateE','<=')
		+") order by modifydate, modifytime" ;*/
	strSQL = wrapSql(tResourceName,"querysqldes4",[document.all('CertifyCode').value,document.all('EndNo').value,document.all('StartNo').value,document.all('StateFlag').value
	                                 ,document.all('SendOutCom').value,document.all('ReceiveCom').value,document.all('Operator').value,document.all('Handler').value
	                                 ,document.all('MakeDateB').value,document.all('MakeDateE').value
	                                 ,document.all('CertifyCode').value,document.all('StateFlag').value,document.all('SendOutCom').value,document.all('ReceiveCom').value
	                                 ,document.all('Operator').value,document.all('Handler').value,document.all('EndNo').value,document.all('StartNo').value
	                                 ,document.all('MakeDateB').value,document.all('MakeDateE').value]);
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardInfoGrid, 1);
   	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	//prompt("strSQL:",strSQL);
   	if(CardInfoGrid.mulLineCount==0)
   	{
   		showInfo.close();
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
   showInfo.close();
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
   	
   	//alert(turnPage.queryAllRecordCount);
   	
   	if(turnPage.queryAllRecordCount>100000)
   	{
   		alert("һ�δ�ӡ�����ݳ���100000��,�뾫ȷ��ѯ������");
   		return false;
   	}
   	
   	
	easyQueryPrint(2,'CardInfoGrid','turnPage');	
}
