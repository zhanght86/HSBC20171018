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
	if(vertifyForm("UPDATE") == false)
		return ;
	var fiscalyearEnd = document.all('FiscalYear').value + "-12-31";
	//if(getCurrentDate() <= fiscalyearEnd)
	//{
	//	alert("δ��������ĩ����������м��㣡");
	//	return;
	//}
	
	var State = "";
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼��" );
		return ;
	}
	else
    State = PolGrid.getRowColData(tSel - 1,7);

	if (State != "0")
	{
		alert("��״̬�µĲ������ò������޸ģ�");
		return;
	}
		
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
	document.all("fmtransact").value="UPDATE";
	document.all('GrpContNo').value = PolGrid.getRowColData(tSel - 1,1);
	//return;
  var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
  document.getElementById("fm").submit(); //�ύ
}

//�����������ŵ�����
function saveAll()
{
	var state = document.all('State').value;
	if(state != null && state != "")
	{
		alert("�ֺ�״̬���������ܽ��б��������");
		return;
	}
	
	if(vertifyForm("INSERT") == false)
		return ;
	
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
	document.all("fmtransact").value="INSERT||ALL";

  var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
  document.getElementById("fm").submit(); //�ύ
}

//�����������ŵ�����
function updateAll()
{
	var state = document.all('State').value;
	if(state != "0")
	{
		alert("�ֺ�״̬���������ܽ��б��������");
		return;
	}

	if(vertifyForm("UPDATE") == false)
		return ;
	
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
	document.all("fmtransact").value="UPDATE||ALL";

  var showStr="�����޸����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
  document.getElementById("fm").submit(); //�ύ
}

function deleteParm()
{
	if(document.all('State').value!='0')
	{
		alert("�����ڴ�����״̬������ɾ����");	
		return ;
	}
	
  var i = 0;
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
 
	document.all("update").disabled=true;
	document.all("insAll").disabled=true;
	document.all("updAll").disabled=true;
  document.all('fmtransact').value='DELETE'; //�ύ
  document.getElementById("fm").submit();
}

function vertifyForm(Operate)
{
	var acturate = document.all('ActuRate').value;
	var ensurate = document.all('EnsuRateDefault').value;
	var assignrate = document.all('AssignRate').value;
	
	if(acturate == null || acturate == "")
	{
		alert("��¼��ʵ��Ͷ�������ʣ�");
		return false;
	}

	if(ensurate == null || ensurate == "")
	{
		alert("��¼��Ĭ�ϱ�֤�������ʣ�");
		return false;
	}

	if(Operate == "UPDATE" && (assignrate == null || assignrate == ""))
	{
		alert("��¼��ֺ������");
		return false;
	}

	return true;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("update").disabled=false;
  document.all("insAll").disabled=false;
	document.all("updAll").disabled=false;
  
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
//  easyQueryClick();
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
	var fiscalYear = document.all('FiscalYear').value;
	if(fiscalYear == null || fiscalYear == "")
	{
		alert("��¼������ȣ�");
		return;
	}

	initPolGrid();
	
	var addSql = "";
	var strSQL = "";
	var endyear = fiscalYear + "-12-31";
	if(document.all('State').value != null && document.all('State').value != ""){
		var sqlid831104344="DSHomeContSql831104344";
var mySql831104344=new SqlClass();
mySql831104344.setResourceName("uwgrp.GrpCalBonusPrePareSql");//ָ��ʹ�õ�properties�ļ���
mySql831104344.setSqlId(sqlid831104344);//ָ��ʹ�õ�Sql��id
mySql831104344.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831104344.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831104344.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831104344.addSubPara(manageCom);//ָ������Ĳ���
mySql831104344.addSubPara(endyear);//ָ������Ĳ���
mySql831104344.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831104344.addSubPara(document.all('State').value);//ָ������Ĳ���
mySql831104344.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql831104344.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
mySql831104344.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831104344.addSubPara(fm.EDate.value);//ָ������Ĳ���
strSQL=mySql831104344.getString();
	}
		
	else{
		var sqlid831105228="DSHomeContSql831105228";
var mySql831105228=new SqlClass();
mySql831105228.setResourceName("uwgrp.GrpCalBonusPrePareSql");//ָ��ʹ�õ�properties�ļ���
mySql831105228.setSqlId(sqlid831105228);//ָ��ʹ�õ�Sql��id
mySql831105228.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831105228.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831105228.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831105228.addSubPara(manageCom);//ָ������Ĳ���
mySql831105228.addSubPara(endyear);//ָ������Ĳ���
mySql831105228.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831105228.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql831105228.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
mySql831105228.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831105228.addSubPara(fm.EDate.value);//ָ������Ĳ���
strSQL=mySql831105228.getString();
	}
	k++;
	
	
	
	
//	if(document.all('State').value != null && document.all('State').value != "")
//		addSql = " and exists(select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = "+fiscalYear+" and computestate = '"+document.all('State').value+"') ";
//	else
//		addSql = " and not exists(select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = "+fiscalYear+") ";
//		
//	k++;
//	// ��дSQL���
//	var endyear = fiscalYear + "-12-31";
//	var strSQL = "select GrpContNo,CValiDate,GrpName,"
//						 + "nvl((select acturate from lobonusgrppolparm where grppolno = a.grppolno and FiscalYear = '" + fiscalYear + "'),0), "
//						 + "nvl((select ensuratedefault from lobonusgrppolparm where grppolno = a.grppolno and FiscalYear = '" + fiscalYear + "'),0), "
//						 + "bonusrate, "
//						 + "(select computestate from lobonusgrppolparm where grppolno = a.grppolno and FiscalYear = '" + fiscalYear + "') "
//						 + "from LCGrpPol a where "+k+" = "+k+" "
//						 + "and AppFlag = '1' and ManageCom like '" + manageCom + "%%' "
//						 + "and CValiDate <='" + endyear + "' " //and (PayEndDate >='" + endyear + "' or PayEndDate is null) "
//						 + " and exists (select 1 from lcinsureacc where grpcontno = a.grpcontno and state not in ('1','4'))"
//						 + getWherePart( 'grpcontno','GrpContNo')
//						 + getWherePart( 'riskcode','RiskCode')
//						 + getWherePart('cvalidate','BDate','>=','0')
//						 + getWherePart('cvalidate','EDate','<=','0')
//						 + addSql 
//						 + "order by GrpContNo,CValiDate";	

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