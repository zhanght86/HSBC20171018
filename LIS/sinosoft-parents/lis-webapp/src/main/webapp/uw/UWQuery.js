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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

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
 	if(tContType=="1")
 	{ 	
 	 var sqlid826112553="DSHomeContSql826112553";
var mySql826112553=new SqlClass();
mySql826112553.setResourceName("uw.UWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826112553.setSqlId(sqlid826112553);//ָ��ʹ�õ�Sql��id
mySql826112553.addSubPara(ProposalContNo);//ָ������Ĳ���
var strSQL=mySql826112553.getString();
 	 
// 	 var strSQL="select ProposalContNo,contno,managecom,salechnl,agentcode,remark from lccont where contno='"+ ProposalContNo+ "'";
   var arrResult=easyExecSql(strSQL);         
   try{document.all('ContNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('HideContNo').value= arrResult[0][1];}catch(ex){};                  
   try{document.all('ManageCom').value= arrResult[0][2];}catch(ex){};          
   try{document.all('SaleChnl').value= arrResult[0][3];}catch(ex){};     
   try{document.all('AgentCode').value= arrResult[0][4];}catch(ex){};            
   try{document.all('Remark').value= arrResult[0][5];}catch(ex){};            
   return;   
  }   
else if (tContType=="2")
	{
	 var sqlid826112641="DSHomeContSql826112641";
var mySql826112641=new SqlClass();
mySql826112641.setResourceName("uw.UWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826112641.setSqlId(sqlid826112641);//ָ��ʹ�õ�Sql��id
mySql826112641.addSubPara(ProposalContNo);//ָ������Ĳ���
var strSQL=mySql826112641.getString();
	 
//	 var strSQL="select * from lcgrpcont where grpcontno='"+ ProposalContNo+ "'";
   var arrResult=easyExecSql(strSQL);         
   try{document.all('ContNo').value= arrResult[0][2];}catch(ex){};         
   try{document.all('ManageCom').value= arrResult[0][4];}catch(ex){};          
   try{document.all('SaleChnl').value= arrResult[0][3];}catch(ex){};     
   try{document.all('AgentCode').value= arrResult[0][7];}catch(ex){};            
   try{document.all('Remark').value= arrResult[0][64];}catch(ex){};            
   return;   
		}
  
 }	
}

 
function queryinsured(){ 
  //alert(ContNo); 
  if(tContType=="1")
  {
  var sqlid826112736="DSHomeContSql826112736";
var mySql826112736=new SqlClass();
mySql826112736.setResourceName("uw.UWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826112736.setSqlId(sqlid826112736);//ָ��ʹ�õ�Sql��id
mySql826112736.addSubPara(ProposalContNo);//ָ������Ĳ���
var aSQL=mySql826112736.getString();
  
//  var aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where contno='"+ ProposalContNo+ "'";
}  
else if(tContType=="2")
	{
		var sqlid826112823="DSHomeContSql826112823";
var mySql826112823=new SqlClass();
mySql826112823.setResourceName("uw.UWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826112823.setSqlId(sqlid826112823);//ָ��ʹ�õ�Sql��id
mySql826112823.addSubPara(ProposalContNo);//ָ������Ĳ���
var aSQL=mySql826112823.getString();
		
//		var aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where grpcontno='"+ ProposalContNo+ "'";
		}
  //alert(aSQL);
  turnPage.queryModal(aSQL, PolAddGrid);
}

function  showRiskInfo(){
	
	var tSelNo = PolAddGrid.getSelNo()-1;
  var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);	
// alert(tContType);
  if(tContType=="1")
  {

	var sqlid826113119="DSHomeContSql826113119";
var mySql826113119=new SqlClass();
mySql826113119.setResourceName("uw.UWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826113119.setSqlId(sqlid826113119);//ָ��ʹ�õ�Sql��id
mySql826113119.addSubPara(ProposalContNo);//ָ������Ĳ���
mySql826113119.addSubPara(tInsuredNo);//ָ������Ĳ���
var aSQL=mySql826113119.getString();

	
//	var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears,lcpol.proposalno from LCPol,lmrisk where 1=1"
//							+ " and contno='"+ProposalContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode";
	}
   if(tContType=="2")
	{

		var sqlid826113506="DSHomeContSql826113506";
var mySql826113506=new SqlClass();
mySql826113506.setResourceName("uw.UWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826113506.setSqlId(sqlid826113506);//ָ��ʹ�õ�Sql��id
mySql826113506.addSubPara(ProposalContNo);//ָ������Ĳ���
mySql826113506.addSubPara(tInsuredNo);//ָ������Ĳ���
var aSQL=mySql826113506.getString();
		
//		var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and grpcontno='"+ProposalContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode";
		}
	//alert(aSQL);						
	turnPage.queryModal(aSQL, RiskGrid);
} 

//���ֺ˱��켣��ѯ
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   var proposalno = RiskGrid.getRowColData(tSelNo,11);   
   //alert("PolNo="+tPolNo);	
  // var tSQL = "select ";
	 window.open("./RiskTraceMain.jsp?ContNo="+document.all('ContNo').value+"&PolNo="+tPolNo+"&ProposalNo="+proposalno+"&ContType=1","",sFeatures);
	
}


//��ͬ�˱��켣��ѯ
function QueryContTrace(){

	if(tContType=="1")
  {
window.open("./ContTraceMain.jsp?ContNo="+document.all('HideContNo').value,"",sFeatures); 
	
	}
   if(tContType=="2")
	{
	window.open("./GrpContTraceMain.jsp?ContNo="+document.all('ContNo').value,"",sFeatures); 
	
		}
	
} 