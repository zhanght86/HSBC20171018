var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.PostMidplatCertifyQuerySql";
// ��ѯ��ť
function easyQueryClick()
{
	initCardInfo();
	
	//У���¼�����¼��
	if(verifyInput() == false)
	{
		return;
	}

	if(fm.StateFlag.value!=null && fm.StateFlag.value=="3"){  // ���Ų�ѯlzcard
	/*	var strSQL="select (select managecom from laagent where agentcode = substr(a.receivecom, 2)) managecom, "
			 +"substr(a.receivecom, 2), a.startno, a.endno, a.sumcount, a.makedate, '',  "
       		 +"(case a.stateflag when '3' then '�ѷ���δ����' else '�Ƿ�' end) "
			 +"from lzcard a where 1=1 and a.receivecom like 'D%'"
			+ getWherePart('a.CertifyCode', 'CertifyCode2')
			+ getWherePart('a.StateFlag', 'StateFlag')
			+ getWherePart('a.MakeDate', 'MakeDateB', '>=')
			+ getWherePart('a.MakeDate', 'MakeDateE', '<=')
			+ getWherePart('a.StartNo', 'StartNo', '>=')
			+ getWherePart('a.startno', 'EndNo', '<=');*/
		
		mySql = new SqlClass();
		mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
		mySql.setSqlId("PostMidplatCertifyQuerySql1");
		mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
		if(fm.Agentcode.value!=null && fm.Agentcode.value!=""){
			/*strSQL+=" and a.receivecom=concat('D','"+fm.Agentcode.value+"')";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql2");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(Agentcode); 
		}
		if(fm.ManageCom.value!=null && fm.ManageCom.value!=""){
			//strSQL+="and exists (select 1 from laagent where agentcode = substr(a.receivecom, 2) and managecom like '"+fm.ManageCom.value+"%')";
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql3");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("Agentcode"))[0].value); 
			mySql.addSubPara(ManageCom); 
		}
			/*strSQL+="order by a.makedate,a.startno";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql4");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
	}else{	// ���������ϡ���ʧ�����ٲ�ѯlzcardB
		/*var strSQL="select (select managecom from laagent where agentcode = substr(a.sendoutcom, 2)) managecom, "
			 +"substr(a.sendoutcom, 2), a.startno, a.endno, a.sumcount, '', a.makedate, "
       		 +"(case a.stateflag when '4' then '�Զ�����' when '5' then '�ֹ�����' when '6' then 'ʹ������' when '7' then 'ͣ������' when '10' then '��ʧ' when '11' then '����' else '�Ƿ�' end) "
			 +"from lzcardB a where 1=1 and a.sendoutcom like 'D%'"
			+ getWherePart('a.CertifyCode', 'CertifyCode2')
			+ getWherePart('a.StateFlag', 'StateFlag')
			+ getWherePart('a.MakeDate', 'MakeDateB', '>=')
			+ getWherePart('a.MakeDate', 'MakeDateE', '<=')
			+ getWherePart('a.StartNo', 'StartNo', '>=')
			+ getWherePart('a.startno', 'EndNo', '<=');*/
		mySql = new SqlClass();
		mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
		mySql.setSqlId("PostMidplatCertifyQuerySql5");
		mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
		if(fm.Agentcode.value!=null && fm.Agentcode.value!=""){
			/*strSQL+=" and a.sendoutcom=concat('D','"+fm.Agentcode.value+"')";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql6");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(Agentcode); 
		}
		if(fm.ManageCom.value!=null && fm.ManageCom.value!=""){
			/*strSQL+="and exists (select 1 from laagent where agentcode = substr(a.sendoutcom, 2) and managecom like '"+fm.ManageCom.value+"%')";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql7");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(ManageCom); 
		}
			/*strSQL+="order by a.makedate,a.startno";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql8");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
	}			 

   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(mySql.getString(), CardInfo, 1);
   	if(CardInfo.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
}


//��ӡ��ѯ���
function easyPrint()
{
   	if(CardInfo.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardInfo','turnPage');
}