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
var sqlresourcename = "uwgrp.EdorUWQuerySql";

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
 //if(ContNo!==""){
 	//alert(ContNo);
 	//var strSQL="select * from LCCont where ContNo='"+ ContNo+ "'";
 	//alert(strSQL);
  // var arrResult=easyExecSql(strSQL);
   //alert(arrResult);                         
  // try{fm.all('ContNo').value= arrResult[0][1];}catch(ex){};         
  // try{fm.all('ManageCom').value= arrResult[0][9];}catch(ex){};          
  // try{fm.all('SaleChnl').value= arrResult[0][16];}catch(ex){};     
  // try{fm.all('AgentCode').value= arrResult[0][12];}catch(ex){};            
  // try{fm.all('Remark').value= arrResult[0][48];}catch(ex){};            
  // return;       
 //}	
}

 
function queryinsured(){ 
  //alert(ContNo); 
  //var aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where ContNo='"+ ContNo+ "'";  
 
 		var sqlid1="EdorUWQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ContNo);
 
  //alert(aSQL);
  turnPage.queryModal(mySql1.getString(), PolAddGrid);
}

function  showRiskInfo(){
	
	var tSelNo = PolAddGrid.getSelNo()-1;
  var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);	
  //alert(tInsuredNo);
  /*  
	var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
							+ " and contno='"+ContNo+"'"
							+ " and insuredno ='"+tInsuredNo+"'"
							+ " and lcpol.riskcode = lmrisk.riskcode";
		*/
		var sqlid2="EdorUWQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(ContNo);
		mySql2.addSubPara(tInsuredNo);
		
							
	turnPage.queryModal(mySql2.getString(), RiskGrid);
} 

//���ֱ�ȫ�˱��켣��ѯ
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   //alert("PolNo="+tPolNo);	
	 window.open("./EdorRiskTraceMain.jsp?ContNo="+ContNo+"&PolNo="+tPolNo,"window1",sFeatures);
	
}


//��ͬ��ȫ�˱��켣��ѯ
function QueryContTrace(){

	window.open("./EdorContTraceMain.jsp?ContNo="+ContNo,"window1",sFeatures); 
	
} 