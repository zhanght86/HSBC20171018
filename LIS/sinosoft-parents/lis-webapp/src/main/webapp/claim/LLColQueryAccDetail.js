var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

// ��ѯ��ť
function InitQueryClick()
{
	/*var strSQL = "select  code,codename from LDcode where codetype='accidentcode' "
	           + getWherePart( 'code','ICDCodeQ');*/
	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLColQueryAccDetailInputSql");
		mySql.setSqlId("LLColQueryAccDetailSql1");
		mySql.addSubPara(fm.ICDCodeQ.value ); 
        if (fm.ICDNameQ.value != "" && fm.ICDNameQ.value != null)
	    {
		  // strSQL = strSQL + " and codename like '%%" + fm.ICDNameQ.value + "%%'"
		   mySql = new SqlClass();
			mySql.setResourceName("claim.LLColQueryAccDetailInputSql");
			mySql.setSqlId("LLColQueryAccDetailSql2");
			mySql.addSubPara(fm.ICDCodeQ.value ); 
			mySql.addSubPara(fm.ICDNameQ.value ); 
	    }
		//strSQL = strSQL + " order by code";
	//prompt("��ѯ����ϸ�ڴ��������ϸ�����Ƶ�sql",strSQL);
    turnPage.pageLineNum = 10;
	turnPage.queryModal(mySql.getString(), LLColQueryAccDetailGrid);
}

//�����LLColQueryAccDetailGrid���Ĵ�������
function LLColQueryAccDetailGridClick()
{
}

//��ӦRadioBox�ĵ���¼����
function returnParent()
{
    var i = LLColQueryAccDetailGrid.getSelNo();
    if (i != 0)
    {
        i = i - 1;
        var arr = new Array();
        arr[0] = LLColQueryAccDetailGrid.getRowColData(i,1); //����
        arr[1] = LLColQueryAccDetailGrid.getRowColData(i,2); //����
    }
    if (arr)
    {
//       top.opener.afterQueryLL(arr);
        top.opener.document.all(fm.HosCode.value).value = arr[0];
        top.opener.document.all(fm.HosName.value).value = arr[1];
    }
    top.close();
}