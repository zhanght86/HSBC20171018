//�������ƣ�BankDataQuery.js
//�����ܣ����������ݲ�ѯ
//�������ڣ�2004-10-20
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;

var turnPage = new turnPageClass();

//�򵥲�ѯ
function easyQuery()
{
	if (!verifyInput()==true) return false;
	var sDate = document.all('SendDate').value;
	if(fm.ContType.value == '1')
	{	
	// ��дSQL���
//	var strSQL = "select serialno,polno,AccName,AccNo,BankCode,paymoney,SendDate,bankdealdate,''"
//							+ ",case notype when '6' then '��������' "
//							+ "when '2' then '��������' "
//							+ "when '7' then '��������' "
//							+ "when '3' then '��������' "
//							+ "when '10' then '��ȫ' "
//							+ "end "
//							+ "from lySendToBank "
//							+ " where 1=1 and dealtype='S'"
//							+ getWherePart('serialno','serialno','like','0')
//							+ getWherePart('polno','PolNo','=','0')
//							+ getWherePart('accname','AccName','=','0')
//							+ getWherePart('comcode','ManageCom','like','0')
//							+ getWherePart('bankcode','BankCode','=','0')
//							+ getWherePart('SendDate','SendDate','=','0')
//							+ getWherePart('bankdealdate','BackDate','=','0')
//							+ getWherePart('notype','NoType','=','0')
//							+ getWherePart('accno','AccNo','=','0')
//							//+ " group by polno,accname,comcode,bankcode,senddate "
//							+ "order by 1,2";
	
  	var  serialno0 = window.document.getElementsByName(trim("serialno"))[0].value;
  	var  PolNo0 = window.document.getElementsByName(trim("PolNo"))[0].value;
  	var  AccName0 = window.document.getElementsByName(trim("AccName"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  BankCode0 = window.document.getElementsByName(trim("BankCode"))[0].value;
  	var  SendDate0 = window.document.getElementsByName(trim("SendDate"))[0].value;
  	var  BackDate0 = window.document.getElementsByName(trim("BackDate"))[0].value;
  	var  NoType0 = window.document.getElementsByName(trim("NoType"))[0].value;
  	var  AccNo0 = window.document.getElementsByName(trim("AccNo"))[0].value;
	var sqlid0="BankDataQuerySql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.BankDataQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(serialno0);//ָ������Ĳ���
	mySql0.addSubPara(PolNo0);//ָ������Ĳ���
	mySql0.addSubPara(AccName0);//ָ������Ĳ���
	mySql0.addSubPara(ManageCom0);//ָ������Ĳ���
	mySql0.addSubPara(BankCode0);//ָ������Ĳ���
	mySql0.addSubPara(SendDate0);//ָ������Ĳ���
	mySql0.addSubPara(BackDate0);//ָ������Ĳ���
	mySql0.addSubPara(NoType0);//ָ������Ĳ���
	mySql0.addSubPara(AccNo0);//ָ������Ĳ���
	var strSQL=mySql0.getString();
	
	
	 }
	else
		{
//			var strSQL = "select serialno,polno,AccName,AccNo,BankCode,paymoney,SendDate,bankdealdate"
//							+ ",case "
//							+ " when instr((select b.agentpaysuccflag from ldbank b where b.bankcode=a.bankcode  ) ,concat(a.banksuccflag,';'))>0  then '�ɹ�' else "
//							+ "(select codename from ldcode1 where trim(code)=trim(BankCode) "+
//							"			 and trim(code1)=trim(banksuccflag) and codetype='bankerror')"
//							+ " end"
//							+ ",case notype when '6' then '��������' "
//							+ "when '2' then '��������' "
//							+ "when '7' then '��������' "
//							+ "when '3' then '��������' "
//							+ "when '10' then '��ȫ' "
//							+ "end "
//							+ "from lyreturnfromBankb a	 "
//							+ " where 1=1 and dealtype='S'"
//							+ getWherePart('serialno','serialno','like','0')
//							+ getWherePart('polno','PolNo','=','0')
//							+ getWherePart('accname','AccName','=','0')
//							+ getWherePart('comcode','ManageCom','like','0')
//							+ getWherePart('bankcode','BankCode','=','0')
//							+ getWherePart('SendDate','SendDate','=','0')
//							+ getWherePart('bankdealdate','BackDate','=','0')
//							+ getWherePart('notype','NoType','=','0')
//							+ getWherePart('accno','AccNo','=','0')
//							//+ " group by polno,accname,comcode,bankcode,senddate "
//							+ "order by 1";
			
		  	var  serialno1 = window.document.getElementsByName(trim("serialno"))[0].value;
		  	var  PolNo1 = window.document.getElementsByName(trim("PolNo"))[0].value;
		  	var  AccName1 = window.document.getElementsByName(trim("AccName"))[0].value;
		  	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
		  	var  BankCode1 = window.document.getElementsByName(trim("BankCode"))[0].value;
		  	var  SendDate1 = window.document.getElementsByName(trim("SendDate"))[0].value;
		  	var  BackDate1 = window.document.getElementsByName(trim("BackDate"))[0].value;
		  	var  NoType1 = window.document.getElementsByName(trim("NoType"))[0].value;
		  	var  AccNo1 = window.document.getElementsByName(trim("AccNo"))[0].value;
			var sqlid1="BankDataQuerySql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("f1print.BankDataQuerySql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(serialno1);//ָ������Ĳ���
			mySql1.addSubPara(PolNo1);//ָ������Ĳ���
			mySql1.addSubPara(AccName1);//ָ������Ĳ���
			mySql1.addSubPara(ManageCom1);//ָ������Ĳ���
			mySql1.addSubPara(BankCode1);//ָ������Ĳ���
			mySql1.addSubPara(SendDate1);//ָ������Ĳ���
			mySql1.addSubPara(BackDate1);//ָ������Ĳ���
			mySql1.addSubPara(NoType1);//ָ������Ĳ���
			mySql1.addSubPara(AccNo1);//ָ������Ĳ���
			var strSQL=mySql1.getString();
			
			}
	//alert(strSQL);
	turnPage.queryModal(strSQL, CodeGrid);
	//arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

	if(CodeGrid.mulLineCount ==0)
	{
		alert("û�з�������������");
	}
}

//ȷ����������
function verifyInput()
{
	
	if(fm.ManageCom.value =="")
	{
				alert("��ѡ��������");
				fm.ManageCom.focus();
				return false;
	}
	if (fm.BankCode.value == "")
	{
				alert("��ѡ�����д���");
				fm.BankCode.focus();
				return false;
	}
	
	//�������ڡ��������ڸ�Ϊ����¼��ʱ��Σ���¼���ʱ�䷶Χ���ܳ���1���£�

 //   2�����û���������������������κš��˺š������ű���¼�������κ�һ����
	var booleanDate = "1"

	if(fm.SendDate.value !="" || fm.BackDate.value !="")
	{
		  if(fm.SendDate.value !="" && fm.BackDate.value !="")
		  if(dateDiff(fm.SendDate.value,fm.BackDate.value,"M") >1)
			{
			alert("���������뷵�����ڵļ�����ܴ���һ���£���ȷ��");
		  return false;
		  }
		  booleanDate = "2";
	}
	
	
	if (booleanDate == "1" && fm.serialno.value=="" && fm.AccNo.value=="" && fm.PolNo.value=="")
	{
				alert("����û��¼��ʱ�䣬�������κš��˺š������ű���¼�������κ�һ��");
				fm.BankCode.focus();
				return false;
	}
	
	
	
	if(fm.ContType.value=="")
	{
				alert("��ѡ������״̬��");
				fm.ContType.focus();
				return false;
	}
	return true;
}