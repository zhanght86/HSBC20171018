//�������ƣ�CutBonus.js
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var k = 0;
var turnPage = new turnPageClass();

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

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	k++;
	var fiscalYear = document.all('FiscalYear').value;
	if(fiscalYear == null || fiscalYear == "")
	{
		alert("��¼��ֺ���ȣ�");
		return;
	}
	
	var addSql = "";
	var strSQL  = "";
	var state = document.all('State').value;
	if(state != null && state != ""){
		var sqlid831111547="DSHomeContSql831111547";
var mySql831111547=new SqlClass();
mySql831111547.setResourceName("uwgrp.GrpCalBonusViewSql");//ָ��ʹ�õ�properties�ļ���
mySql831111547.setSqlId(sqlid831111547);//ָ��ʹ�õ�Sql��id
mySql831111547.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831111547.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831111547.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831111547.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831111547.addSubPara(state);//ָ������Ĳ���
mySql831111547.addSubPara(fiscalYear+"-12-31");//ָ������Ĳ���
mySql831111547.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql831111547.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
mySql831111547.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831111547.addSubPara(fm.EDate.value);//ָ������Ĳ���
strSQL=mySql831111547.getString();
	}else{
		var sqlid831112355="DSHomeContSql831112355";
var mySql831112355=new SqlClass();
mySql831112355.setResourceName("uwgrp.GrpCalBonusViewSql");//ָ��ʹ�õ�properties�ļ���
mySql831112355.setSqlId(sqlid831112355);//ָ��ʹ�õ�Sql��id
mySql831112355.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831112355.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831112355.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831112355.addSubPara(fiscalYear+"-12-31");//ָ������Ĳ���
mySql831112355.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql831112355.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
mySql831112355.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831112355.addSubPara(fm.EDate.value);//ָ������Ĳ���
strSQL=mySql831112355.getString();
	}
	
	
	
	
//	if(state != null && state != "")
//		addSql = " and exists (select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = '"+fiscalYear+"' and computestate = '"+state+"') ";
//	//���ֺ�״̬Ϊ�գ����ѯ�����з��������ļ�¼
//	//else
//		//addSql = " and not exists (select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = '"+fiscalYear+"') ";
//	
//	// ��дSQL���
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
	//��ѯSQL�����ؽ���ַ���
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
		alert( "����ѡ��һ����¼��" );
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
		alert("��¼���ŵ��źͻ�����");	
		return ;
	}
	if(document.all("GrpPolNo").value.length<20||document.all("FiscalYear").value.length<4)
	{
		alert("��¼����ȷ���ŵ��źͻ�����");	
		return ;
	}
	
	
	var sqlid831112719="DSHomeContSql831112719";
var mySql831112719=new SqlClass();
mySql831112719.setResourceName("uwgrp.GrpCalBonusViewSql");//ָ��ʹ�õ�properties�ļ���
mySql831112719.setSqlId(sqlid831112719);//ָ��ʹ�õ�Sql��id
mySql831112719.addSubPara(fm.GrpPolNo.value);//ָ������Ĳ���
mySql831112719.addSubPara(fm.FiscalYear.value);//ָ������Ĳ���
var strSQL=mySql831112719.getString();
	
//	var strSQL = "select sum(BonusMoney) from LOBonusGrpPol where "+k+" = "+k+" "
//						 + getWherePart( 'GrpPolNo','GrpPolNo')
//						 + getWherePart( 'FiscalYear','FiscalYear');

  //turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  var calsum=easyExecSql(strSQL, 1, 0, 1);
	if(calsum==null)
	{
      alert("û���ҵ�������ݣ�");
      return "";
	}
	document.all("sumBonus").value=calsum[0][0];
}
