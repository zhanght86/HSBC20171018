//***********************************************
//�������ƣ�UWQuery.js
//�����ܣ��˱���ѯ
//�������ڣ�2005-06-27 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.UWQuerySql";

/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


/*********************************************************************
 *  ��ѯ��ͬ��Ϣ 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function querycont( cCodeName, Field ) 
{
 if(ProposalContNo!==""){
 	
 	//var strSQL="select * from LCCont where ProposalContNo='"+ ProposalContNo+ "'";
 	
 	var sqlid1="UWQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ProposalContNo);
 	
 	//alert(strSQL);
   var arrResult=easyExecSql(mySql1.getString());
   //alert(arrResult);                         
   try{fm.all('ContNo').value= arrResult[0][2];}catch(ex){};         
   try{fm.all('ManageCom').value= arrResult[0][9];}catch(ex){};          
   try{fm.all('SaleChnl').value= arrResult[0][16];}catch(ex){};     
   try{fm.all('AgentCode').value= arrResult[0][12];}catch(ex){};            
   try{fm.all('Remark').value= arrResult[0][48];}catch(ex){};            
   return;       
 }	
}

 
function queryinsured(){ 
  //alert(ContNo); 
  //var aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where PrtNo='"+ ProposalContNo+ "'";  
  
  
 	var sqlid2="UWQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(ProposalContNo);
  
  //alert(aSQL);
  turnPage.queryModal(mySql2.getString(), PolAddGrid);
}

function  showRiskInfo(){
	
	var tSelNo = PolAddGrid.getSelNo()-1;
  var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);	
  //alert(tInsuredNo);  
  /*
	var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
							+ " and PrtNo='"+ProposalContNo+"'"
							+ " and insuredno ='"+tInsuredNo+"'"
							+ " and lcpol.riskcode = lmrisk.riskcode";
	*/
	var sqlid3="UWQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(ProposalContNo);
		mySql3.addSubPara(tInsuredNo);
	
	//alert(aSQL);						
	turnPage.queryModal(mySql3.getString(), RiskGrid);
} 

//���ֺ˱��켣��ѯ
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   //alert("PolNo="+tPolNo);	
	 window.open("./RiskTraceMain.jsp?ContNo="+fm.all('ContNo').value+"&PolNo="+tPolNo,"window1",sFeatures);
	
}


//��ͬ�˱��켣��ѯ
function QueryContTrace(){

	window.open("./ContTraceMain.jsp?ContNo="+fm.all('ContNo').value,"window1",sFeatures); 
	
} 