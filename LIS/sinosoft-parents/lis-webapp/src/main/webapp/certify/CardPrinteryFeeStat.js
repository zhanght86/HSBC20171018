//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CardPrinteryFeeStatSql";
//��֤״̬��ѯ
function certifyQuery()
{
  CardPrintInfoGrid.clearData();//����������������

  /*var strSQL = "select a.prtno, a.printery, a.certifycode, "
  		+" (select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+"  to_char(a.certifyprice, 'FM99990.0099'), to_char(a.sumcount), a.certifyprice * a.sumcount,  to_char(a.printdate,'YYYY-MM-DD') printdate"
		+" from lzcardprint a where 1 = 1 and a.state in ('1', '2')";
		
  if(fm.Printery.value!=null && fm.Printery.value!=""){		
    strSQL+=" and a.Printery like '%" + fm.Printery.value + "%'";
  }
		//+ getWherePart('a.Printery', 'Printery')
		
  strSQL+= getWherePart('a.certifycode', 'CertifyCode')		
		+ getWherePart('a.PlanType', 'PlanType')		
 		+ getWherePart('a.PrintDate', 'PrintDateB','>=')
 		+ getWherePart('a.PrintDate', 'PrintDateE','<=') 		
 		+" union all "
 		+"select '���ϼ�', '' printery, '' certifycode,  '', '', '', sum(b.certifyprice * b.sumcount), '' printdate"
		+" from lzcardprint b where 1 = 1 and b.state in ('1', '2')";
		
  if(fm.Printery.value!=null && fm.Printery.value!=""){			
    strSQL+=" and b.Printery like '%" + fm.Printery.value + "%'";
  }
  		
		//+ getWherePart('b.Printery', 'Printery')
  strSQL+= getWherePart('b.certifycode', 'CertifyCode')		
		+ getWherePart('b.PlanType', 'PlanType')		
 		+ getWherePart('b.PrintDate', 'PrintDateB','>=')
 		+ getWherePart('b.PrintDate', 'PrintDateE','<=')
 		+" order by printdate,printery,certifycode";*/
    var strSQL = wrapSql(tResourceName,"querysqldes1",[fm.Printery.value,fm.CertifyCode.value,fm.PlanType.value,fm.PrintDateB.value,fm.PrintDateE.value
                                       ,fm.Printery.value,fm.CertifyCode.value,fm.PlanType.value,fm.PrintDateB.value,fm.PrintDateE.value]);
	
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
	//alert("��ѯ���ļ�¼������"+CardSendOutInfoGrid.mulLineCount);
   	if(CardPrintInfoGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardPrintInfoGrid','turnPage');	
}

