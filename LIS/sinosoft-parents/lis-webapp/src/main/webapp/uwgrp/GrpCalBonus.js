//�������ƣ�CutBonus.js
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var k = 0;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if(!vertifyForm())
		return;
	
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

	document.all("btnSubmit").disabled=true;
  document.all("btnForward").disabled=true;
	document.all("opertype").value = 'CALC||ALL';

  document.getElementById("fm").submit(); //�ύ
}

function vertifyForm()
{
	var tRiskCode = document.all('RiskCode').value;
	var tFiscalYear = document.all('FiscalYear').value;
	var tGrpContNo = document.all('GrpContNo').value;
	if(tRiskCode == null || tRiskCode == "" || tFiscalYear == null || tFiscalYear == "" || tGrpContNo == null ||tGrpContNo=="")
	{
		alert("������,���ִ�������屣���Ų�����Ϊ�գ�");
		return false;
	}

//	var tState = document.all('ComputeState').value;
//	if(tState == null || tState == "")
//	{
//		alert("�ֺ�״̬��Ϣ������Ϊ�գ�");
//		return false;
//	}
	return true;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("btnSubmit").disabled=false;
  document.all("btnForward").disabled=false;

  if (FlagStr == "Fail" )
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  else
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  

  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
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

function getGrppolInfo(){
   var tSelNo = PolGrid.getSelNo();
   var GrpContNo =PolGrid.getRowColData(tSelNo-1,1);
   fm.GrpContNo1.value = GrpContNo;
}

// ��ѯ��ť
function easyQueryClick()
{
	if(!vertifyForm())
		return;
	
	// ��ʼ�����
	initPolGrid();
	k++;
	// ��дSQL���
	
	var sqlid831100116="DSHomeContSql831100116";
var mySql831100116=new SqlClass();
mySql831100116.setResourceName("uwgrp.GrpCalBonusSql");//ָ��ʹ�õ�properties�ļ���
mySql831100116.setSqlId(sqlid831100116);//ָ��ʹ�õ�Sql��id
mySql831100116.addSubPara(manageCom+"%%");//ָ������Ĳ���
mySql831100116.addSubPara(fm.FiscalYear.value);//ָ������Ĳ���
mySql831100116.addSubPara(fm.ComputeState.value);//ָ������Ĳ���
mySql831100116.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql831100116.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
mySql831100116.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831100116.addSubPara(fm.EDate.value);//ָ������Ĳ���
var strSQL=mySql831100116.getString();

	
//	var strSQL = "select a.GrpContNo,a.GrpName,a.CValiDate,b.ActuRate,b.EnsuRateDefault,b.AssignRate "
//						 + "from LCGrpPol a,LOBonusGrpPolParm b where "+k+" = "+k+" "
//						 + "and a.GrpPolNo = b.GrpPolNo and a.AppFlag = '1' and b.computestate in ('0','3','4') "
//						 + "and a.ManageCom like '" + manageCom + "%%' "
//						 + getWherePart( 'b.FiscalYear','FiscalYear')
//						 + getWherePart( 'b.ComputeState','ComputeState')
//						 + getWherePart( 'a.GrpContNo','GrpContNo')
//						 + getWherePart( 'a.RiskCode','RiskCode')
//						 + getWherePart('a.cvalidate','BDate','>=','0')
//						 + getWherePart('a.cvalidate','EDate','<=','0')
//						 + " order by a.CValiDate";	
	
	//alert(strSQL);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û���ҵ�������ݣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initPolGrid();
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

function viewErrLog()
{
	window.open("./ViewErrLogGrpBonusAssign.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function calGrpPart()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	else
	{
		var tGrpContNo = PolGrid.getRowColData(tSel-1,1);
		var tFiscalYear = document.all('FiscalYear').value;
		var tRiskCode = document.all('RiskCode').value;
		window.open("./GrpCalPartBonusMain.jsp?GrpContNo=" + tGrpContNo + "&FiscalYear=" + tFiscalYear + "&RiskCode=" + tRiskCode,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}	
}