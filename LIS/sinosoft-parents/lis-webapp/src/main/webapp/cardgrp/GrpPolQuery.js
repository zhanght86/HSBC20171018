//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
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
  		parent.fraMain.rows = "0,0,0,82,*";
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
		var cPrtNo = GrpPolGrid.getRowColData( tSel - 1, 2 );
		var cGrpContNo=GrpPolGrid.getRowColData( tSel-1, 1 );
		
		try
		{
			window.open("../sys/GrpPolDetailQueryMain.jsp?PrtNo=" + cPrtNo + "&GrpContNo=" + cGrpContNo+"&flag=1","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
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
	var strSQL ="";
		
	/*var strSQL = "select GrpContNo,PrtNo,GrpName,CValiDate,Peoples2,Prem,Amnt,SignCom,decode(appflag ,'1','��Ч','4','ʧЧ','����') from LCGrpCont b where 1=1 "
    				 + " and AppFlag in ('1', '4')  and cardflag='1'"
    				 + getWherePart( 'PrtNo' )
    				 + getWherePart( 'GrpContNo' )
    				 //+ getWherePart( 'appntno','CustomerNo' )
    				 + getWherePart( 'ManageCom','ManageCom','like' )
    				 + getWherePart( 'AgentCode' )
    				 + getWherePart( 'AgentGroup' )
    				 //+ getWherePart( 'GrpName','GrpName','like' )
    				;*/
    mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql1");
	mySql.addSubPara(fm.PrtNo.value ); 	
	mySql.addSubPara(fm.GrpContNo.value ); 	
	mySql.addSubPara(fm.ManageCom.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 	
	mySql.addSubPara(fm.AgentGroup.value ); 	
	mySql.addSubPara(comCode ); 			
	if(document.all('RiskCode').value!=null&&document.all('RiskCode').value!="")
	{
	//	strSQL = strSQL+"and exists (select 1 from lcgrppol a where riskcode='"+document.all('RiskCode').value+"' and b.grpcontno=a.grpcontno) "
		mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql2");
	mySql.addSubPara(fm.PrtNo.value ); 	
	mySql.addSubPara(fm.GrpContNo.value ); 	
	mySql.addSubPara(fm.ManageCom.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 	
	mySql.addSubPara(fm.AgentGroup.value ); 
	mySql.addSubPara(document.all('RiskCode').value); 
	mySql.addSubPara(comCode ); 		
	}
    				 	
  if (tDisplay!="4") {
	//  strSQL = strSQL + " and specflag = '0' ";
	  mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql3");
	mySql.addSubPara(fm.PrtNo.value ); 	
	mySql.addSubPara(fm.GrpContNo.value ); 	
	mySql.addSubPara(fm.ManageCom.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 	
	mySql.addSubPara(fm.AgentGroup.value ); 
	mySql.addSubPara(document.all('RiskCode').value); 
	mySql.addSubPara(comCode ); 	
	}
	
 /* strSQL = strSQL + " and ManageCom like '" + comCode + "%%'"
    				      + " Order by grpContNo desc";*/
  
 turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = GrpPolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
	
}


// ���ݷ��ظ�����
function returnParentBQ()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResultBQ();
			//alert(arrReturn);
			
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
			//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		
	}
}

function getQueryResultBQ()
{
	var arrSelected = null;
	var tRow = GrpPolGrid.getSelNo();
	//alert(tRow);
	if (tRow==0 || tRow==null) return arrSelected;
  
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = GrpPolGrid.getRowData(tRow-1);
	//alert(arrSelected[0][0]);
	//tRow = 10 * turnPage.pageIndex + tRow; //10����multiline������
	//������Ҫ���ص�����
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//������Ҫ���ص�����
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
}

function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	//if(document.all('ManageCom').value==""){
	//	 alert("����¼����������Ϣ��");
	//	 return;
	//}
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   /*	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	         + "and a.AgentCode = b.AgentCode and a.branchtype in ('1','4') and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);*/
    mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql4");
	mySql.addSubPara(cAgentCode );  
    
    var arrResult = easyExecSql(mySql.getString());
    //alert(arrResult);
    if (arrResult != null) 
    {
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	fm.AgentGroup.value = arrResult[0][1];
  	  //fm.AgentName.value = arrResult[0][3];
      fm.ManageCom.value = arrResult[0][2];
     
      //if(fm.AgentManageCom.value != fm.ManageCom.value)
      //{
      //   	
    	//    //fm.ManageCom.value = fm.AgentManageCom.value;
    	//    //fm.ManageComName.value = fm.AgentManageComName.value;
    	//    //showCodeName('comcode','ManageCom','AgentManageComName');  //���뺺��
    	//   
      //}
      //showContCodeName();
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
    }
   
	}
}

function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	fm.AgentGroup.value = arrResult[0][1];
  	  //fm.AgentName.value = arrResult[0][3];
      //fm.ManageCom.value = arrResult[0][2];

  	//showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}