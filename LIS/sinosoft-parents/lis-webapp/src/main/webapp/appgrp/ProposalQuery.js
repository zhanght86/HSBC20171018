//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "appgrp.ProposalQuerySql";

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
	var tSel = PolGrid.getSelNo();
	
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
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var queryBug = 1;
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	var strOperate="like";
	// ��дSQL���
	/*
	var strSql = "select l.ContNo,l.PrtNo,l.AppntName,l.Operator,l.MakeDate,l.ApproveCode,l.ApproveDate,l.UWOperator,l.UWDate,l.UWFlag,(select codename from ldcode where UWFlag = code and codetype='contuwstate'),l.State from LCCont l where 1=1 and appflag<>'1'"
	//var strSql = "select l.ContNo,l.PrtNo,l.AppntName,l.Operator,l.MakeDate,l.ApproveCode,l.ApproveDate,l.UWOperator,l.UWDate,l.UWFlag,2.codename,l.State from LCCont l,ldcode 2 where 1=1 and appflag<>'1' and trim(1.uwflag)=trim(2.code) and 2.codetype='contuwstate'"
    				+" and grpcontno='00000000000000000000'"
    				+ getWherePart( 'l.ContNo','ContNo',strOperate)
    				+ getWherePart( 'l.PrtNo','PrtNo',strOperate)
    				+ getWherePart( 'l.ManageCom','ManageCom' , strOperate)
    				+getWherePart( 'l.AppntName' ,'AppntName',strOperate )
    				+ getWherePart( 'l.InsuredName','InsuredName',strOperate )
                                + getWherePart( 'l.InsuredSex','InsuredSex',strOperate )
    				+getWherePart( 'l.UWFlag','uwState',strOperate )
    				+ getWherePart( 'l.AgentCode','AgentCode',strOperate )
    				+ getWherePart( 'l.AgentGroup','AgentGroup',strOperate)    				 
    				//alert(strSql);
    				
    	*/			
    				var sqlid1="ProposalQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.ContNo.value);
		mySql1.addSubPara(fm.PrtNo.value);
		mySql1.addSubPara(fm.ManageCom.value);
		mySql1.addSubPara(fm.AppntName.value);
		mySql1.addSubPara(fm.InsuredName.value);
		mySql1.addSubPara(fm.InsuredSex.value);
		mySql1.addSubPara(fm.uwState.value);
		mySql1.addSubPara(fm.AgentCode.value);
		mySql1.addSubPara(fm.AgentGroup.value);
    				
    				
    				
    			turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޲�ѯ�����");
    return "";
  }
	turnPage.queryModal(strSql, PolGrid);
}

var mSwitch = parent.VD.gVSwitch;


function queryDetailClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cProposalContNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
  	var cprtno = PolGrid.getRowColData(checkFlag - 1, 2); 	
//  	mSwitch.deleteVar( "fm.value('PrtNo')" );
  	
//  	mSwitch.addVar( "fm.value('PrtNo')", "", cPrtNo);
  	 
    var strSql="";
   // strSql="select salechnl from lccont where contno='"+cprtno+"'";
    
    var sqlid2="ProposalQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(cprtno);

    
    arrResult = easyExecSql(mySql2.getString());
    var BankFlag = arrResult[0][0]; 
  	 
   // urlStr = "./ProposalEasyScan.jsp?LoadFlag=6&ContNo="+cProposalContNo+"&prtNo="+cprtno+"&BankFlag="+BankFlag ,"status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";   
  
    window.open("./ProposalEasyScan.jsp?LoadFlag=6&ContNo="+cProposalContNo+"&prtNo="+cprtno+"&BankFlag="+BankFlag ,"","status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}


function queryMARiskClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cMainPolNo = PolGrid.getRowColData(tSel - 1,2);	
	    var cPrtNo = PolGrid.getRowColData(tSel - 1,3);			

		
		if (cMainPolNo == "")
		    return;
		    
		  window.open("./MainOddRiskQueryMain.jsp?MainPolNo=" + cMainPolNo + "&PrtNo=" + cPrtNo,"",sFeatures);										
	}
}

//ɨ�����ѯ
function ScanQuery() {
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				
		
		if (prtNo == "") return;
		    
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		window.open("../sys/ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
	}	     
}


//����������ѯ
function OperationQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var PrtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (PrtNo == "" || ContNo == "") return;
			
	window.open("../sys/RecordQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo,""sFeatures);	
  }										
} 