var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

// ��ѯ��ť
function InitQueryClick()
{
	/*var strSQL = "select defocode,defoname,DefoType,(case DefoType when '1' then '���˲еȼ�(7��34��)' when '2' then '���˲еȼ�' end),defograde,DefoGradeName,DefoRate from LLParaDeformity where 1=1 "
	           + getWherePart( 'DefoType','DefoType')
	           + getWherePart( 'DefoGrade','DefoGrade');*/
				mySql = new SqlClass();
				mySql.setResourceName("claim.LLColQueryDefoInputSql");
				mySql.setSqlId("LLColQueryDefoSql1");
				mySql.addSubPara(fm.DefoType.value ); 
				mySql.addSubPara(fm.DefoGrade.value ); 
			   if (fm.DefoCodeName.value != "" && fm.DefoCodeName.value != null)
			   {
				mySql = new SqlClass();
				mySql.setResourceName("claim.LLColQueryDefoInputSql");
				mySql.setSqlId("LLColQueryDefoSql1");
				mySql.addSubPara(fm.DefoType.value ); 
				mySql.addSubPara(fm.DefoGrade.value ); 	
				mySql.addSubPara(fm.DefoCodeName.value ); 	  
		//strSQL = strSQL + " and DefoName like '%%" + fm.DefoCodeName.value + "%%'"
			   }
		//strSQL = strSQL + " order by defocode";
//	alert(strSQL);
    turnPage.pageLineNum = 10;
	turnPage.queryModal(mySql.getString(), LLColQueryDefoGrid);
}

//�����LLColQueryDefoGrid���Ĵ�������
function LLColQueryDefoGridClick()
{
}

//��ӦRadioBox�ĵ���¼����
function returnParent()
{
    var i = LLColQueryDefoGrid.getSelNo();
    if (i != 0)
    {
        i = i - 1;
        var arr = new Array();
        arr[0] = LLColQueryDefoGrid.getRowColData(i,1); //����
        arr[1] = LLColQueryDefoGrid.getRowColData(i,2); //����
        arr[2] = LLColQueryDefoGrid.getRowColData(i,3); //�м�����
        arr[3] = LLColQueryDefoGrid.getRowColData(i,4); 
        arr[4] = LLColQueryDefoGrid.getRowColData(i,5); //�˲м���
        arr[5] = LLColQueryDefoGrid.getRowColData(i,6);
        arr[6] = LLColQueryDefoGrid.getRowColData(i,7); //��������
    }
    if (arr)
    {
//       top.opener.afterQueryLL(arr);
        top.opener.document.all(fm.HosCode.value).value = arr[0];
        top.opener.document.all(fm.HosName.value).value = arr[1];
        top.opener.document.all('DefoType').value = arr[2];
        top.opener.document.all('DefoTypeName').value = arr[3];
        top.opener.document.all('DefoGrade').value = arr[4];
        top.opener.document.all('DefoGradeName').value = arr[5];
        top.opener.document.all('DeformityRate').value = arr[6];
        top.opener.document.all('AppDeformityRate').value = arr[6];
        top.opener.document.all('RealRate').value = arr[6];
    }
    top.close();
}