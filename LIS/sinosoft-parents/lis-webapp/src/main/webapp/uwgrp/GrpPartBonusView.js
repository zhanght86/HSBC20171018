var showInfo;
var turnPage = new turnPageClass(); 

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initBonusGrid();
	
	var fiscalYear = document.all('FiscalYear').value;
	var grppolNo = document.all('GrpPolNo').value;
	if(fiscalYear == null || fiscalYear == "" || grppolNo == null || grppolNo == "")
	{
		alert("����ҳ��Ļ����Ⱥ��ŵ���Ϊ�գ��޷���ѯ��");
		return;
	}
	
	var addSql = "";
	var strSQL  = "";
	if(document.all('ComputeState').value != null && document.all('ComputeState').value != ""){
		var sqlid831142101="DSHomeContSql831142101";
var mySql831142101=new SqlClass();
mySql831142101.setResourceName("uwgrp.GrpPartBonusViewSql");//ָ��ʹ�õ�properties�ļ���
mySql831142101.setSqlId(sqlid831142101);//ָ��ʹ�õ�Sql��id
mySql831142101.addSubPara(grppolNo);//ָ������Ĳ���
mySql831142101.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831142101.addSubPara(grppolNo);//ָ������Ĳ���
mySql831142101.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831142101.addSubPara(grppolNo);//ָ������Ĳ���
mySql831142101.addSubPara(grppolNo);//ָ������Ĳ���
mySql831142101.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831142101.addSubPara(document.all('ComputeState').value);//ָ������Ĳ���
mySql831142101.addSubPara(fm.PolNo.value);//ָ������Ĳ���
mySql831142101.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831142101.addSubPara(fm.EDate.value);//ָ������Ĳ���
strSQL=mySql831142101.getString();

	}else{
		var sqlid831143015="DSHomeContSql831143015";
var mySql831143015=new SqlClass();
mySql831143015.setResourceName("uwgrp.GrpPartBonusViewSql");//ָ��ʹ�õ�properties�ļ���
mySql831143015.setSqlId(sqlid831143015);//ָ��ʹ�õ�Sql��id
mySql831143015.addSubPara(grppolNo);//ָ������Ĳ���
mySql831143015.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831143015.addSubPara(grppolNo);//ָ������Ĳ���
mySql831143015.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831143015.addSubPara(grppolNo);//ָ������Ĳ���
mySql831143015.addSubPara(fm.PolNo.value);//ָ������Ĳ���
mySql831143015.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831143015.addSubPara(fm.EDate.value);//ָ������Ĳ���
strSQL=mySql831143015.getString();
	}
	
//			addSql = " and decode((select bonusflag from lobonusgrppol where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"' and polno = a.polno),null,'0','0','3','1','4') = '"+document.all('ComputeState').value+"' ";
//
//	// ��дSQL���
//	var strSQL = "select grppolno,polno,cvalidate,"
//						 + "(select assignrate from lobonusgrppolparm where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"'),"
//						 + "decode((select distinct bonusflag from lobonusgrppol where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"' and polno = a.polno),null,'0','0','3','1','4') "
//						 + "from lcpol a "
//						 + "where grppolno = '" + grppolNo + "' and a.appflag = '1' "
//						 + addSql
//						 + getWherePart('PolNo')
//						 + getWherePart('cvalidate','BDate','>=','0')
//						 + getWherePart('cvalidate','EDate','<=','0')
//						 + " order by polno "
//						 ;
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
	turnPage.queryModal(strSQL, BonusGrid);    
}

function easyPrint()
{
	easyQueryPrint(2,'BonusGrid','turnPage');
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}
