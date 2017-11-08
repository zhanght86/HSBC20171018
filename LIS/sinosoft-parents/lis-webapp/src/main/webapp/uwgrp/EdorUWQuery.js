//***********************************************
//程序名称：UWQuery.js
//程序功能：核保查询
//创建日期：2005-06-27 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.EdorUWQuerySql";

/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


/*********************************************************************
 *  查询合同信息 
 *  参数  ：  无
 *  返回值：  无
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

//险种保全核保轨迹查询
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   //alert("PolNo="+tPolNo);	
	 window.open("./EdorRiskTraceMain.jsp?ContNo="+ContNo+"&PolNo="+tPolNo,"window1",sFeatures);
	
}


//合同保全核保轨迹查询
function QueryContTrace(){

	window.open("./EdorContTraceMain.jsp?ContNo="+ContNo,"window1",sFeatures); 
	
} 