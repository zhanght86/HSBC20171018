//
//�������ƣ�BankDataQuery.js
//�����ܣ����������ݲ�ѯ
//�������ڣ�2004-10-20
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();

//��ѯ��������
function easyQuery()
{
	// ��дSQL���
	var strSQL ="";
	if(_DBT==_DBO){
//		 strSQL = "select polno,(select trim(prtno) from lcpol where contno = a.polno and rownum=1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '�ۿ�ɹ�' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobank a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " union all "
//			 + "select polno,(select trim(prtno) from lcpol where contno = a.polno and rownum=1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '�ۿ�ɹ�' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobankb a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " order by senddate ";
		 
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		 
		  var sqlid1="BankDataQueryDetailSql1";
		  var mySql1=new SqlClass();
		  mySql1.setResourceName("bank.BankDataQueryDetailSql");
		  mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
		  mySql1.addSubPara(GetNoticeNo);//ָ���������
		  mySql1.addSubPara(PolNo);//ָ���������
		  mySql1.addSubPara(GetNoticeNo);//ָ���������
		  mySql1.addSubPara(PolNo);//ָ���������
		  var strSQL = mySql1.getString();
		 
	}else if(_DBT==_DBM){
//		 strSQL = "select polno,(select trim(prtno) from lcpol where contno = a.polno limit 1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '�ۿ�ɹ�' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobank a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " union all "
//			 + "select polno,(select trim(prtno) from lcpol where contno = a.polno limit 1),senddate,serialno,"
//			 + "(case banksuccflag when '0000' then '�ۿ�ɹ�' else ( select codename  from ldcode1  where codetype = 'bankerror' and code = bankcode and code1 = banksuccflag) end) "
//			 + "from lysendtobankb a "
//			 + "where 1 = 1 "
//			 + getWherePart('paycode','GetNoticeNo')
//			 + getWherePart('polno','PolNo')
//			 + " order by senddate ";
		 
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		  var  GetNoticeNo = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		  var  PolNo = window.document.getElementsByName(trim("PolNo"))[0].value;
		 
		  var sqlid2="BankDataQueryDetailSql2";
		  var mySql2=new SqlClass();
		  mySql2.setResourceName("bank.BankDataQueryDetailSql");
		  mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
		  mySql2.addSubPara(GetNoticeNo);//ָ���������
		  mySql2.addSubPara(PolNo);//ָ���������
		  mySql2.addSubPara(GetNoticeNo);//ָ���������
		  mySql2.addSubPara(PolNo);//ָ���������
		  var strSQL = mySql2.getString();
		 
	}
	
	//prompt("",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
}

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyClose()
{
	top.close();
	}