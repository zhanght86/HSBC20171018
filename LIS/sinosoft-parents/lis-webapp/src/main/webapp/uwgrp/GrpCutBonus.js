//�������ƣ�CutBonus.js
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var tRiskCode = document.all('RiskCode').value;
	var tFiscalYear = document.all('FiscalYear').value;
	var tGrpContNo = document.all('GrpContNo').value;
	if(tRiskCode == null || tRiskCode == "" || tFiscalYear == null || tFiscalYear == "" || tGrpContNo == null ||tGrpContNo=="")
	{
		alert("������,���ִ�������屣���Ų�����Ϊ�գ�");
		return;
	}	
	
  var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  document.all("btnAssign").disabled=true;
  document.all("btnAssignPart").disabled=true;
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("btnAssign").disabled=false;
  document.all("btnAssignPart").disabled=false;

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

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	k++;
	
	var tRiskCode = document.all('RiskCode').value;
	var tFiscalYear = document.all('FiscalYear').value;
	var tGrpContNo = document.all('GrpContNo').value;
	if(tRiskCode == null || tRiskCode == "" || tFiscalYear == null || tFiscalYear == "" || tGrpContNo == null ||tGrpContNo=="")
	{
		alert("������,���ִ�������屣���Ų�����Ϊ�գ�");
		return;
	}	
	
	
	// ��дSQL���
	var sqlid831144103="DSHomeContSql831144103";
var mySql831144103=new SqlClass();
mySql831144103.setResourceName("uwgrp.GrpCutBonusSql");//ָ��ʹ�õ�properties�ļ���
mySql831144103.setSqlId(sqlid831144103);//ָ��ʹ�õ�Sql��id
mySql831144103.addSubPara(manageCom+"%%");//ָ������Ĳ���
mySql831144103.addSubPara(fm.ComputeState.value);//ָ������Ĳ���
mySql831144103.addSubPara(fm.FiscalYear.value);//ָ������Ĳ���
mySql831144103.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql831144103.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
mySql831144103.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831144103.addSubPara(fm.EDate.value);//ָ������Ĳ���
var strSQL=mySql831144103.getString();
	
	
//	var strSQL = "select a.GrpContNo,a.GrpName,b.FiscalYear,a.RiskCode,a.CValiDate,b.AssignRate,b.ComputeState "
//						 + "from LCGrpPol a,LOBonusGrpPolParm b where "+k+" = "+k+" "
//						 + "and a.GrpPolNo = b.GrpPolNo and a.AppFlag = '1' and b.computestate in ('1','3','4') "
//						 + "and a.ManageCom like '" + manageCom + "%%' "
//						 + getWherePart('b.ComputeState','ComputeState')
//						 + getWherePart('b.FiscalYear','FiscalYear')
//						 + getWherePart('a.GrpContNo','GrpContNo')
//						 + getWherePart('a.RiskCode','RiskCode')
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
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
		}
	}
}

function assignPartGrp()
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
		var tFiscalYear = PolGrid.getRowColData(tSel-1,3);
		var tRiskCode = document.all('RiskCode').value;
		window.open("./GrpPolBonusQueryMain.jsp?FiscalYear=" + tFiscalYear +"&GrpContNo=" + tGrpContNo + "&RiskCode=" + tRiskCode,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}	
}

function viewErrLog()
{
	window.open("./ViewErrLogGrpBonusAssign.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");		
}
