//程序名称：CutBonus.js
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var k = 0;
var turnPage = new turnPageClass();

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

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	k++;
	var fiscalYear = document.all('FiscalYear').value;
	if(fiscalYear == null || fiscalYear == "")
	{
		alert("请录入分红年度！");
		return;
	}
	
	var addSql = "";
	var strSQL  = "";
	var state = document.all('State').value;
	if(state != null && state != ""){
		var sqlid831111547="DSHomeContSql831111547";
var mySql831111547=new SqlClass();
mySql831111547.setResourceName("uwgrp.GrpCalBonusViewSql");//指定使用的properties文件名
mySql831111547.setSqlId(sqlid831111547);//指定使用的Sql的id
mySql831111547.addSubPara(fiscalYear);//指定传入的参数
mySql831111547.addSubPara(fiscalYear);//指定传入的参数
mySql831111547.addSubPara(fiscalYear);//指定传入的参数
mySql831111547.addSubPara(fiscalYear);//指定传入的参数
mySql831111547.addSubPara(state);//指定传入的参数
mySql831111547.addSubPara(fiscalYear+"-12-31");//指定传入的参数
mySql831111547.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831111547.addSubPara(fm.RiskCode.value);//指定传入的参数
mySql831111547.addSubPara(fm.BDate.value);//指定传入的参数
mySql831111547.addSubPara(fm.EDate.value);//指定传入的参数
strSQL=mySql831111547.getString();
	}else{
		var sqlid831112355="DSHomeContSql831112355";
var mySql831112355=new SqlClass();
mySql831112355.setResourceName("uwgrp.GrpCalBonusViewSql");//指定使用的properties文件名
mySql831112355.setSqlId(sqlid831112355);//指定使用的Sql的id
mySql831112355.addSubPara(fiscalYear);//指定传入的参数
mySql831112355.addSubPara(fiscalYear);//指定传入的参数
mySql831112355.addSubPara(fiscalYear);//指定传入的参数
mySql831112355.addSubPara(fiscalYear+"-12-31");//指定传入的参数
mySql831112355.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831112355.addSubPara(fm.RiskCode.value);//指定传入的参数
mySql831112355.addSubPara(fm.BDate.value);//指定传入的参数
mySql831112355.addSubPara(fm.EDate.value);//指定传入的参数
strSQL=mySql831112355.getString();
	}
	
	
	
	
//	if(state != null && state != "")
//		addSql = " and exists (select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = '"+fiscalYear+"' and computestate = '"+state+"') ";
//	//若分红状态为空，则查询出所有符合条件的记录
//	//else
//		//addSql = " and not exists (select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = '"+fiscalYear+"') ";
//	
//	// 书写SQL语句
//		var strSQL = "select "+ fiscalYear+",grppolno,cvalidate,"
//							 + "(select assignrate from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = '"+fiscalYear+"'),"
//							 + "(select computestate from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = '"+fiscalYear+"') "
//							 + "from lcgrppol a "
//							 + "where "+k+" = " + k + addSql
//							 + "and cvalidate <= '" + fiscalYear + "-12-31' "
//							 + getWherePart( 'a.GrpContNo','GrpContNo')
//							 + getWherePart( 'a.RiskCode','RiskCode')
//							 + getWherePart('a.cvalidate','BDate','>=','0')
//							 + getWherePart('a.cvalidate','EDate','<=','0')
//							 + " order by grppolno ";

	//alert(strSQL);
	//查询SQL，返回结果字符串
	turnPage.queryModal(strSQL, PolGrid);    
}

function easyPrint()
{
	easyQueryPrint(2,'PolGrid','turnPage');
}

function viewErrLog()
{
	window.open("./ViewErrLogBonusAssign.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");		
}

function queryGrpDetail()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录。" );
		return;
	}
	else
	{
		var tGrpPolNo = PolGrid.getRowColData(tSel-1,2);
		var tFiscalYear = document.all('FiscalYear').value;
		var tRiskCode = document.all('RiskCode').value;
		window.open("./GrpPartBonusViewMain.jsp?GrpPolNo=" + tGrpPolNo + "&FiscalYear=" + tFiscalYear + "&RiskCode=" + tRiskCode,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}	
}
function calSum()
{
	if(document.all("GrpPolNo").value==null||document.all("FiscalYear").value==null)
	{
		alert("请录入团单号和会计年度");	
		return ;
	}
	if(document.all("GrpPolNo").value.length<20||document.all("FiscalYear").value.length<4)
	{
		alert("请录入正确的团单号和会计年度");	
		return ;
	}
	
	
	var sqlid831112719="DSHomeContSql831112719";
var mySql831112719=new SqlClass();
mySql831112719.setResourceName("uwgrp.GrpCalBonusViewSql");//指定使用的properties文件名
mySql831112719.setSqlId(sqlid831112719);//指定使用的Sql的id
mySql831112719.addSubPara(fm.GrpPolNo.value);//指定传入的参数
mySql831112719.addSubPara(fm.FiscalYear.value);//指定传入的参数
var strSQL=mySql831112719.getString();
	
//	var strSQL = "select sum(BonusMoney) from LOBonusGrpPol where "+k+" = "+k+" "
//						 + getWherePart( 'GrpPolNo','GrpPolNo')
//						 + getWherePart( 'FiscalYear','FiscalYear');

  //turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  var calsum=easyExecSql(strSQL, 1, 0, 1);
	if(calsum==null)
	{
      alert("没有找到相关数据！");
      return "";
	}
	document.all("sumBonus").value=calsum[0][0];
}
