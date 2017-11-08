var showInfo;
var turnPage = new turnPageClass(); 

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initBonusGrid();
	
	var fiscalYear = document.all('FiscalYear').value;
	var grppolNo = document.all('GrpPolNo').value;
	if(fiscalYear == null || fiscalYear == "" || grppolNo == null || grppolNo == "")
	{
		alert("传入页面的会计年度和团单号为空，无法查询！");
		return;
	}
	
	var addSql = "";
	var strSQL  = "";
	if(document.all('ComputeState').value != null && document.all('ComputeState').value != ""){
		var sqlid831142101="DSHomeContSql831142101";
var mySql831142101=new SqlClass();
mySql831142101.setResourceName("uwgrp.GrpPartBonusViewSql");//指定使用的properties文件名
mySql831142101.setSqlId(sqlid831142101);//指定使用的Sql的id
mySql831142101.addSubPara(grppolNo);//指定传入的参数
mySql831142101.addSubPara(fiscalYear);//指定传入的参数
mySql831142101.addSubPara(grppolNo);//指定传入的参数
mySql831142101.addSubPara(fiscalYear);//指定传入的参数
mySql831142101.addSubPara(grppolNo);//指定传入的参数
mySql831142101.addSubPara(grppolNo);//指定传入的参数
mySql831142101.addSubPara(fiscalYear);//指定传入的参数
mySql831142101.addSubPara(document.all('ComputeState').value);//指定传入的参数
mySql831142101.addSubPara(fm.PolNo.value);//指定传入的参数
mySql831142101.addSubPara(fm.BDate.value);//指定传入的参数
mySql831142101.addSubPara(fm.EDate.value);//指定传入的参数
strSQL=mySql831142101.getString();

	}else{
		var sqlid831143015="DSHomeContSql831143015";
var mySql831143015=new SqlClass();
mySql831143015.setResourceName("uwgrp.GrpPartBonusViewSql");//指定使用的properties文件名
mySql831143015.setSqlId(sqlid831143015);//指定使用的Sql的id
mySql831143015.addSubPara(grppolNo);//指定传入的参数
mySql831143015.addSubPara(fiscalYear);//指定传入的参数
mySql831143015.addSubPara(grppolNo);//指定传入的参数
mySql831143015.addSubPara(fiscalYear);//指定传入的参数
mySql831143015.addSubPara(grppolNo);//指定传入的参数
mySql831143015.addSubPara(fm.PolNo.value);//指定传入的参数
mySql831143015.addSubPara(fm.BDate.value);//指定传入的参数
mySql831143015.addSubPara(fm.EDate.value);//指定传入的参数
strSQL=mySql831143015.getString();
	}
	
//			addSql = " and decode((select bonusflag from lobonusgrppol where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"' and polno = a.polno),null,'0','0','3','1','4') = '"+document.all('ComputeState').value+"' ";
//
//	// 书写SQL语句
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
	
	//查询SQL，返回结果字符串
	turnPage.queryModal(strSQL, BonusGrid);    
}

function easyPrint()
{
	easyQueryPrint(2,'BonusGrid','turnPage');
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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
