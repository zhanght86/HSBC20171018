//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet;
var showInfo;
var mDebug="0";
var manageCom;
var turnPage = new turnPageClass();       
var sqlresourcename = "app.ReGroupPolPrintSql";
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
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
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

//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //�����˱���	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
var sqlid1="ReGroupPolPrintSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(cAgentCode);
    
    var arrResult = easyExecSql(mySql1.getString());

  //  alert(arrResult);
    if (arrResult != null) 
    {
			alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
	}	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
function queryAgent2()
{
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value; 
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
 
var sqlid2="ReGroupPolPrintSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(cAgentCode);
    
 
    var arrResult = easyExecSql(mySql2.getString());
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpPolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initGrpPolGrid();
	
	var strManageComWhere = " AND ManageCom LIKE '" + manageCom + "%' ";
	
	if( fm.ManageCom.value != '' ) {
		strManageComWhere += " AND ManageCom LIKE '" + fm.ManageCom.value + "%' ";
	}
	var strRiskCodeWhere=" and 1=1 ";
	if( fm.RiskCode.value != '' ) {
		strRiskCodeWhere += " AND  exists (select RiskCode from lcgrppol a where   a.GrpContNo=b.GrpContNo  and  riskcode='"+fm.RiskCode.value+"'  and  a.RiskCode in  ( select RiskCode from LMRiskApp where SubRiskFlag = 'M' and NotPrintPol = '0'))";                                                   
	}
	var strRiskVersionWhere=" and 2=2 ";
	//alert( fm.RiskVersion.value);
	if( fm.RiskVersion.value != '' ) {
		strRiskVersionWhere += " AND  exists  (select 'X' from lcgrppol a   where a.GrpContNo=b.GrpContNo and  a.riskversion='" + fm.RiskVersion.value + "' ) ";
	}
	var strContNoSQL="";
	//	if((document.all('GrpContNo').value == null || document.all('GrpContNo').value == "") 
  //  && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
  //  )
  // {
  // 	
  // 	   strContNoSQL=" and b.signdate<=sysdate and b.signdate>=(sysdate-30)";
  // 	}
  
	
	// ��дSQL���
	var strSQL = "";
	var addStr3 = " and 3=3 ";
	strSQL = "select GrpContNo,PrtNo,(select min(RiskCode) from  lcgrppol a   where a.GrpContNo=b.GrpContNo and a.riskcode in ( select riskcode from LMRiskApp where SubRiskFlag = 'M' )),GrpName,CValiDate from LCGrpCont  b where 1=1 "
				 + "and AppFlag in ('1','4') and PrintCount = 1 "
				 + " and  EXISTS (select 1 from lcgrppol where grpcontno = b.grpcontno  and riskcode in (select riskcode from LMRiskApp where NotPrintPol = '0'))"
				 + getWherePart( 'GrpContNo' )
				 + getWherePart( 'PrtNo' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'AgentGroup' )
				 + strRiskCodeWhere
				 + strRiskVersionWhere
				 + getWherePart( 'GrpName' )
				 + strContNoSQL
				 + strManageComWhere;
if( fm.GrpName.value != '' ) {
//	strSQL +=" AND GrpName LIKE '%" + fm.GrpName.value + "%' ";
	addStr3 =" AND GrpName LIKE '%" + fm.GrpName.value + "%' ";
}
//strSQL +="order by ManageCom,AgentGroup,AgentCode";

var sqlid7="ReGroupPolPrintSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(fm.GrpContNo.value);
mySql7.addSubPara(fm.PrtNo.value);
mySql7.addSubPara(fm.AgentCode.value);
mySql7.addSubPara(fm.AgentGroup.value);
mySql7.addSubPara(strRiskCodeWhere);
mySql7.addSubPara(strRiskVersionWhere);
mySql7.addSubPara(fm.GrpName.value);
mySql7.addSubPara(strContNoSQL);
mySql7.addSubPara(strManageComWhere);
mySql7.addSubPara(addStr3);
	strSQL = mySql7.getString();		 
			 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
		alert("δ��ѯ���������������ݣ�");
		return false;
	}

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 30 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = GrpPolGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initGrpPolGrid();
		//HZM �����޸�
		GrpPolGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpPolGrid.loadMulLine(GrpPolGrid.arraySave);		
		//HZM �����޸�		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpPolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		GrpPolGrid.delBlankLine();
	} // end of if
}


//�ύ�����水ť��Ӧ����
function printGroupPol()
{
  var i = 0;
  var flag = 0;
	flag = 0;

	for( i = 0; i < GrpPolGrid.mulLineCount; i++ ) {
		if( GrpPolGrid.getChkNo(i) == true ) {
			flag = 1;
			break;
		}
	}
	
	if( flag == 0 ) {
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
  showSubmitFrame(mDebug);


	document.getElementById("fm").submit();
	
	if(fmSave.fmtransact.value == "")
	{fmSave.fmtransact.value = "PRINT";
	document.getElementById("fmSave").submit();}
else{
	document.getElementById("fmSave").submit();
	
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	
  }
  easyQueryClick();
}
function dataConfirm(rr)
{
	if(rr.checked == true)
     {if( confirm("�Ƿ����������ݣ����������ݵ����ȷ����������㡰ȡ������"))
	         {
	         	rr.checked = true;
	         	fmSave.fmtransact.value = "CONFIRM";	         
	         }
	    else {
		       rr.checked = false;
		       fmSave.fmtransact.value = "PRINT";		       
	    }
	  }
	else{
	    rr.checked = false;
	    fmSave.fmtransact.value = "PRINT";		  
	    }  
}

function initRiskVersion()
{	
	//var  tSQL="select distinct Riskver from LMRiskApp  where   RiskProp in ('G','A','B','D') order by riskver";

var sqlid9="ReGroupPolPrintSql9";
var mySql9=new SqlClass();
mySql9.setResourceName(sqlresourcename);
mySql9.setSqlId(sqlid9);
mySql9.addSubPara("1");

	  var strResult = easyQueryVer3(mySql9.getString());
  //alert(strResult);
  if (strResult) {
    document.all("RiskVersion").CodeData = strResult;    
  }
 }