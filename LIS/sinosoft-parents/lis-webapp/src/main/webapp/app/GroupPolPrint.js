var showInfo;
var mDebug="0";
var manageCom;
var turnPage = new turnPageClass();
var arrDataSet;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "app.GroupPolPrintSql";

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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else{
		parent.fraMain.rows = "0,0,0,0,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpContGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpContGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// ��ѯ��ť
function easyQueryClick(){
	// ��ʼ�����
	initGrpContGrid();
	
	if(document.all('ManageCom').value=="" || document.all('ManageCom').value==null )
	{
		alert("�����������Ϊ��");
		return;
	}	
	//������������ѯ����
	var strManageComWhere = " AND ManageCom LIKE '" + manageCom + "%%' ";
	if( fm.ManageCom.value != '' ) {
		strManageComWhere += " AND ManageCom LIKE '" + fm.ManageCom.value + "%%' ";
	}
	if( fm.BranchGroup.value != '' ) {
		strManageComWhere += " AND AgentGroup IN ( SELECT AgentGroup FROM LABranchGroup WHERE BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
	}
	
	var strRiskCodeWhere = " and 1=1 ";
	//alert(document.all('RiskCode').value );
	if(document.all('RiskCode').value != null && document.all('RiskCode').value != "" )
	{
		strRiskCodeWhere +=  " and EXISTS (select 1 from lcgrppol where grpcontno = A.grpcontno  and riskcode= '"+document.all('RiskCode').value +"') "
	}
	
		var strContNoSQL="";
	//	if((document.all('GrpContNo').value == null || document.all('GrpContNo').value == "") ������ʱ���������Ŀ���
  //  && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
  //  )
  // {
  // 	
  // 	   strContNoSQL=" and a.signdate<=sysdate and a.signdate>=(sysdate-30)";
  // 	}
   	//	alert(document.all('GrpContNo').value);
	//alert(strContNoSQL);
	// ��дSQL���
	var strSQL = "";
/*
	strSQL = "SELECT GrpContNo,PrtNo,Prem,GrpName,(select count(1) from lcinsured where grpcontno = A.grpcontno),signdate FROM LCGrpCont A"
		+ " WHERE AppFlag in ('1','4') and ( PrintCount < 1 OR PrintCount =10 )"
		 + " and  EXISTS (select 1 from lcgrppol where grpcontno = A.grpcontno  and riskcode in (select riskcode from LMRiskApp where NotPrintPol = '0'))"
//				 + " and ContNo='" + contNo + "' "
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'GrpContNo' )
		+ getWherePart( 'AgentCode' )
//		+ getWherePart( 'CValiDate' )
		+ getWherePart( 'SaleChnl' )
//				 + getWherePart( 'RiskCode' )
		+ strManageComWhere
		+ strRiskCodeWhere
//		+ strContNoSQL
 	  + " AND NOT EXISTS ( SELECT GrpPolNo FROM LCGrpPol WHERE A.PrtNo = PrtNo AND AppFlag = '0' ) "
		+ " order by CValiDate asc,signdate asc";
		//document.all('BranchGroup').value = strSQL;
*/
var sqlid5="GroupPolPrintSql5";
var mySql5=new SqlClass();
mySql5.setResourceName(sqlresourcename);
mySql5.setSqlId(sqlid5);
mySql5.addSubPara(fm.PrtNo.value);
mySql5.addSubPara(fm.GrpContNo.value);	
mySql5.addSubPara(fm.AgentCode.value);	
mySql5.addSubPara(fm.SaleChnl.value);		
mySql5.addSubPara(strManageComWhere);	
mySql5.addSubPara(strRiskCodeWhere);		
	
var strSQL = mySql5.getString();


	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("δ��ѯ���������������ݣ�");
		return false;
	}
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 10 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = GrpContGrid;
	//����SQL���
	turnPage.strQuerySql     = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


//�ύ�����水ť��Ӧ����
function printGroupPol()
{
	
		if(document.all('ManageCom').value==86)
     {
    	 alert("�ܹ�˾�����Խ����ŵ���ӡ���뷵��");
    	 return false;
     }
	var i = 0;
	var flag = 0;
	flag = 0;
	//�ж��Ƿ���ѡ���ӡ����
	for( i = 0; i < GrpContGrid.mulLineCount; i++ )
	{
		if( GrpContGrid.getChkNo(i) == true )
		{
			flag = 1;
			break;
		}
	}
	//���û�д�ӡ���ݣ���ʾ�û�ѡ��
	if( flag == 0 )
	{
		alert("����ѡ��һ����¼���ٴ�ӡ����");
		return false;
	}
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	//disable��ӡ��ť����ֹ�û��ظ��ύ
	document.all("printButton").disabled=true;
	document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	//���۴�ӡ�����Σ������¼����ӡ��ť
	document.all("printButton").disabled=false;
	if (FlagStr == "Fail" )
	{
		//���ʧ�ܣ��򷵻ش�����Ϣ
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
		//����ύ�ɹ�����ִ�в�ѯ����
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQueryClick();
	}
	//document.all("divErrorMInfo").style.display = "";
}

function queryBranch()
{
	showInfo = window.open("../certify/AgentTrussQuery.html","",sFeatures);
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
	if(arrResult!=null)
	{
		fm.BranchGroup.value = arrResult[0][3];
	}
}

// ��ѯ��ť
function easyQueryErrClick()
{

	// ��ʼ�����
	initErrorGrid();
	//�ж����Ƿ��Ǽ�ͥ�����߶�����
	
 /*
  var strSQL="select contno,Errmsg,makedate,maketime from LDSysErrLog where 1=1 "
  + getWherePart( 'GrpContNo','ErrorContNo' )
  + getWherePart( 'MakeDate' );
	*/
var sqlid7="GroupPolPrintSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(fm.ErrorContNo.value)	
mySql7.addSubPara(fm.MakeDate.value)
	
	turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
		alert("δ��ѯ���������������ݣ�");
		return ;
	}

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 30 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = ErrorGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}