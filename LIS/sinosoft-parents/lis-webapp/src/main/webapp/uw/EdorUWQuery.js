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
//  var aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where ContNo='"+ ContNo+ "'";  
  
     var sqlid1="EdorUWQuerySql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.EdorUWQuerySql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(ContNo);//ָ���������
	 var aSQL = mySql1.getString();
	 
  //alert(aSQL);
  turnPage.queryModal(aSQL, PolAddGrid);
}

function  showRiskInfo(){
	
	var tSelNo = PolAddGrid.getSelNo()-1;
  var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);	
  //alert(tInsuredNo);  
//	var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode";
	
	 var sqlid2="EdorUWQuerySql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.EdorUWQuerySql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(ContNo);//ָ���������
	 mySql2.addSubPara(tInsuredNo);//ָ���������
	 var aSQL = mySql2.getString();
							
	turnPage.queryModal(aSQL, RiskGrid);
} 

//���ֱ�ȫ�˱��켣��ѯ
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   //alert("PolNo="+tPolNo);	
	 window.open("./EdorRiskTraceMain.jsp?ContNo="+ContNo+"&PolNo="+tPolNo,"window1");
	
}


//��ͬ��ȫ�˱��켣��ѯ
function QueryContTrace(){

	window.open("./EdorContTraceMain.jsp?ContNo="+ContNo,"window1"); 
	
} 