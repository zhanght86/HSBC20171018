var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

// ��ѯ��ť
function InitQueryClick()
{
	/*var strSQL = "select ConsultNo,HospitalCode,HospitalName,"
	           + " (select d.codename from ldcode d where d.code=HosAtti and d.codetype='llhosgrade'),"
	           + " (case ConFlag when '0' then '����' when '1' then '�Ƕ���' else '' end), "
	           + " (case AppFlag when '0' then '������' when '1' then '������' else '' end), "
	           + " (case HosState when '0' then '��Ч' when '1' then '��ͣ' when '2' then '��ֹ' else '' end), "
	           + " MngCom from LLCommendHospital where 1=1 "
	           + getWherePart( 'HosAtti','HospitalGradeQ')
			   + getWherePart( 'HospitalCode','HospitalCodeQ');*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryHospitalInputSql");
	mySql.setSqlId("LLColQueryHospitalSql1");
	mySql.addSubPara(fm.HospitalGradeQ.value ); 	
	mySql.addSubPara(fm.HospitalCodeQ.value ); 	
	mySql.addSubPara(fm.HospitalMngcomQ.value ); 	
   if (fm.HospitalNameQ.value != "" && fm.HospitalNameQ.value != null)
   {
   	
		//strSQL = strSQL + " and HospitalName like '%%" + fm.HospitalNameQ.value + "%%'"
			mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryHospitalInputSql");
	mySql.setSqlId("LLColQueryHospitalSql2");
	mySql.addSubPara(fm.HospitalGradeQ.value ); 	
	mySql.addSubPara(fm.HospitalCodeQ.value ); 	
	mySql.addSubPara(fm.HospitalNameQ.value ); 	  
    }
   //if (fm.HospitalMngcomQ.value != "" && fm.HospitalMngcomQ.value != null)
   //{
		//strSQL = strSQL + " and Mngcom like '" + fm.HospitalMngcomQ.value + "%%'"
    //}    
		/*strSQL = strSQL + " order by HospitalCode desc";*/
    //prompt("��ѯ����ҽԺ��sql",strSQL);
    turnPage.pageLineNum = 10;
	turnPage.queryModal(mySql.getString(), LLColQueryHospitalGrid);
}
//��ʼ��ҽԺ������� Modify by zhaorx 2006-05-23
function InitHospitalMngcomQ()
{
	if(fm.tComCode.value.length <= 4)
	{
		fm.HospitalMngcomQ.value = fm.tComCode.value
	}
	else
	{
		fm.HospitalMngcomQ.value = fm.tComCode.value.substring(0,4)
	}
}
//�����LLColQueryHospitalGrid���Ĵ�������
function LLColQueryHospitalGridClick()
{
}

//��ӦRadioBox�ĵ���¼����
function returnParent()
{
    var i = LLColQueryHospitalGrid.getSelNo();
    if (i != 0)
    {
        i = i - 1;
        var arr = new Array();
        arr[0] = LLColQueryHospitalGrid.getRowColData(i,2); //ҽԺ����
        arr[1] = LLColQueryHospitalGrid.getRowColData(i,3); //ҽԺ����
//        arr[2] = LLColQueryHospitalGrid.getRowColData(i,4); //ҽԺ�ȼ�
    }
    if (arr)
    {
//       top.opener.afterQueryLL(arr);
        top.opener.document.all(fm.HosCode.value).value = arr[0];
        top.opener.document.all(fm.HosName.value).value = arr[1];
    }
    top.close();
}