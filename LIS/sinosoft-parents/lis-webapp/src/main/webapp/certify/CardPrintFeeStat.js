//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CardPrintFeeStatSql";
//��֤״̬��ѯ
function certifyQuery()
{
  if(verifyInput() == false)
  {
	return;
  }

  initCardPrintInfoGrid();    
  fm.ASendOutCom.value='A'+fm.SendOutCom.value;
  fm.AReceiveCom.value='A'+fm.ReceiveCom.value;
  //alert(fm.ASendOutCom.value);
  //alert(fm.AReceiveCom.value);
  
  /* 
    FM �����ո�, 9999999.0099������С��������������Ϊ7λ��С�����ұ�����2λ�����4λ�����ڵ�5λ������������ 
    Select TO_CHAR(123.0233,'FM9999999.0099') FROM DUAL
*/  
    /*var strSQLDetail = "select c.SendOutCom, c.ReceiveCom, c.certifycode,"
  		+"(select certifyname from lmcertifydes where certifycode = c.certifycode) certifyname, "
  		+"to_char((select certifyprice from lmcertifydes where certifycode = c.certifycode), 'FM999999999999990.0099') certifyprice,"
  		//+"to_char(sum(c.sumcount), '999999')," zy 2009-11-6 17:14 ֱ��ȡ����
  		+"round(sum(c.sumcount)), "
  		+"nvl(sum(c.sumcount)*(select certifyprice from lmcertifydes where certifycode = c.certifycode),0.00), "
  		+"'"+fm.makedateB.value+"','"+fm.makedateE.value+"'"
  		+" from ("  		
 		+"select a.SendOutCom, a.ReceiveCom, a.certifycode, a.sumcount "
		+" from lzcardtrack a where 1=1 and a.stateflag in ('2','3') "
		+ getWherePart('a.SendOutCom', 'ASendOutCom')
		+ getWherePart('a.ReceiveCom', 'AReceiveCom')	
 		+ getWherePart('a.makedate', 'makedateB','>=')
 		+ getWherePart('a.makedate', 'makedateE','<=')
 		+ " union all "
 		+ " select a.SendOutCom, a.ReceiveCom, a.certifycode, a.sumcount"
 		+ " from lzcardtrack a where a.stateflag='1' and a.operateflag='0'"
 		+ " and not exists(select distinct 1 from lzcardtrack d where d.certifycode=a.certifycode and d.startno=a.startno and d.endno=a.endno"
        + " and d.sendoutcom=a.sendoutcom and d.receivecom=a.receivecom and d.stateflag='2')"
		+ getWherePart('a.SendOutCom', 'ASendOutCom')
		+ getWherePart('a.ReceiveCom', 'AReceiveCom')	
 		+ getWherePart('a.makedate', 'makedateB','>=')
 		+ getWherePart('a.makedate', 'makedateE','<=')
 		+ " union all "
 		+"select b.SendOutCom, b.ReceiveCom, b.certifycode, -b.sumcount "
		+" from lzcardtrack b where 1=1 and b.stateflag in ('2','3') "
		+ getWherePart('b.SendOutCom', 'AReceiveCom')
		+ getWherePart('b.ReceiveCom', 'ASendOutCom')	
 		+ getWherePart('b.makedate', 'makedateB','>=')
 		+ getWherePart('b.makedate', 'makedateE','<=')	
 		+ " union all "
 		+ " select b.SendOutCom, b.ReceiveCom, b.certifycode, -b.sumcount"
 		+ " from lzcardtrack b where b.stateflag='1' and b.operateflag='0'"
 		+ " and not exists(select distinct 1 from lzcardtrack d where d.certifycode=b.certifycode and d.startno=b.startno and d.endno=b.endno"
        + " and d.sendoutcom=b.sendoutcom and d.receivecom=b.receivecom and d.stateflag='2')"
		+ getWherePart('b.SendOutCom', 'AReceiveCom')
		+ getWherePart('b.ReceiveCom', 'ASendOutCom')	
 		+ getWherePart('b.makedate', 'makedateB','>=')
 		+ getWherePart('b.makedate', 'makedateE','<=')	
 		+ ") c group by SendOutCom, ReceiveCom, certifycode ";
 		
    var strSQLSum ="select '���ϼ�','','','','',0, nvl(sum(c.money),0),'"+fm.makedateB.value+"','"+fm.makedateE.value+"'"
		+" from (select a.sumcount*(select certifyprice from lmcertifydes where certifycode = a.certifycode) money"
		+" from lzcardtrack a where 1 = 1 and a.stateflag in ('2','3') "
		+ getWherePart('a.SendOutCom', 'ASendOutCom')
		+ getWherePart('a.ReceiveCom', 'AReceiveCom')	
 		+ getWherePart('a.makedate', 'makedateB','>=')
 		+ getWherePart('a.makedate', 'makedateE','<=')
 		+ " union all "
 		+ " select a.sumcount*(select certifyprice from lmcertifydes where certifycode = a.certifycode) money"
 		+ " from lzcardtrack a where a.stateflag='1' and a.operateflag='0'"
 		+ " and not exists(select distinct 1 from lzcardtrack d where d.certifycode=a.certifycode and d.startno=a.startno and d.endno=a.endno"
        + " and d.sendoutcom=a.sendoutcom and d.receivecom=a.receivecom and d.stateflag='2')"
		+ getWherePart('a.SendOutCom', 'ASendOutCom')
		+ getWherePart('a.ReceiveCom', 'AReceiveCom')	
 		+ getWherePart('a.makedate', 'makedateB','>=')
 		+ getWherePart('a.makedate', 'makedateE','<=')
		+" union all"
		+" select -b.sumcount*(select certifyprice from lmcertifydes where certifycode = b.certifycode) money"
		+" from lzcardtrack b  where 1 = 1 and b.stateflag in ('2','3') "
		+ getWherePart('b.SendOutCom', 'AReceiveCom')
		+ getWherePart('b.ReceiveCom', 'ASendOutCom')	
 		+ getWherePart('b.makedate', 'makedateB','>=')
 		+ getWherePart('b.makedate', 'makedateE','<=')
 		+ " union all "
 		+ " select -b.sumcount*(select certifyprice from lmcertifydes where certifycode = b.certifycode) money"
 		+ " from lzcardtrack b where b.stateflag='1' and b.operateflag='0'"
 		+ " and not exists(select distinct 1 from lzcardtrack d where d.certifycode=b.certifycode and d.startno=b.startno and d.endno=b.endno"
        + " and d.sendoutcom=b.sendoutcom and d.receivecom=b.receivecom and d.stateflag='2')"
		+ getWherePart('b.SendOutCom', 'AReceiveCom')
		+ getWherePart('b.ReceiveCom', 'ASendOutCom')	
 		+ getWherePart('b.makedate', 'makedateB','>=')
 		+ getWherePart('b.makedate', 'makedateE','<=')	
		+" ) c"		
	
	var strSQL = strSQLDetail + " union all " + strSQLSum ;*/
 var strSQL = wrapSql(tResourceName,"querysqldes1",[fm.makedateB.value,fm.makedateE.value,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value
                                      ,fm.makedateE.value
                                      ,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value,fm.makedateE.value
                                      ,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value,fm.makedateE.value
                                      ,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value,fm.makedateE.value
                                      ,fm.makedateB.value,fm.makedateE.value,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value
                                      ,fm.makedateE.value
                                      ,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value,fm.makedateE.value
                                      ,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value,fm.makedateE.value
                                      ,fm.ASendOutCom.value,fm.AReceiveCom.value,fm.makedateB.value,fm.makedateE.value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardPrintInfoGrid);
   	//prompt("strSQL:",strSQL);
   	if(CardPrintInfoGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
}

//[��ӡ]��ť����
function certifyPrint()
{
	//alert("��ѯ���ļ�¼������"+CardSendOutInfoGrid.mulLineCount);
   	if(CardPrintInfoGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardPrintInfoGrid','turnPage');	
}

