var turnPage=new turnPageClass();
function easyQuery()
{	
	var tGrpContNo = fm.GrpContNo.value;
//	var tSQL = "select insuredId,insuredName,errorinfo from lcgrpimportlog"
//	         + " where grpcontno='" + tGrpContNo 
//	         + "' and batchno='" + fm.BatchNo.value 
//	         + "' and errorstate='1'"
//	         + " order by length(trim(insuredId)),trim(insuredId)";
	
	
	var sqlid1="DiskApplyErrorSql0";
	var mySql1=new SqlClass();	
	mySql1.setResourceName("app.DiskApplyErrorSql"); //ָ��ʹ�õ�properties�ļ�   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id	
	mySql1.addSubPara(tGrpContNo);//ָ������Ĳ���	
	mySql1.addSubPara(fm.BatchNo.value);//ָ������Ĳ���	
	var tSQL=mySql1.getString();
	turnPage.queryModal(tSQL, ImportErrorGrid);
	
	if (ImportErrorGrid.mulLineCount == 0){
	    divOtherError.style.display = "";
	}
}

//[��ӡ]��ť����
function errorPrint()
{    
	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(ImportErrorGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�����");
   		return false;
   	}
	easyQueryPrint(2,"ImportErrorGrid","turnPage");	
}
