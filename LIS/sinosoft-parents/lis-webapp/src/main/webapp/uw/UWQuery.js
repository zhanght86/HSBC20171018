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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

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
 if(ProposalContNo!==""){
 	if(tContType=="1")
 	{ 	
 	 var sqlid826112553="DSHomeContSql826112553";
var mySql826112553=new SqlClass();
mySql826112553.setResourceName("uw.UWQueryInputSql");//指定使用的properties文件名
mySql826112553.setSqlId(sqlid826112553);//指定使用的Sql的id
mySql826112553.addSubPara(ProposalContNo);//指定传入的参数
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
mySql826112641.setResourceName("uw.UWQueryInputSql");//指定使用的properties文件名
mySql826112641.setSqlId(sqlid826112641);//指定使用的Sql的id
mySql826112641.addSubPara(ProposalContNo);//指定传入的参数
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
mySql826112736.setResourceName("uw.UWQueryInputSql");//指定使用的properties文件名
mySql826112736.setSqlId(sqlid826112736);//指定使用的Sql的id
mySql826112736.addSubPara(ProposalContNo);//指定传入的参数
var aSQL=mySql826112736.getString();
  
//  var aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where contno='"+ ProposalContNo+ "'";
}  
else if(tContType=="2")
	{
		var sqlid826112823="DSHomeContSql826112823";
var mySql826112823=new SqlClass();
mySql826112823.setResourceName("uw.UWQueryInputSql");//指定使用的properties文件名
mySql826112823.setSqlId(sqlid826112823);//指定使用的Sql的id
mySql826112823.addSubPara(ProposalContNo);//指定传入的参数
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
mySql826113119.setResourceName("uw.UWQueryInputSql");//指定使用的properties文件名
mySql826113119.setSqlId(sqlid826113119);//指定使用的Sql的id
mySql826113119.addSubPara(ProposalContNo);//指定传入的参数
mySql826113119.addSubPara(tInsuredNo);//指定传入的参数
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
mySql826113506.setResourceName("uw.UWQueryInputSql");//指定使用的properties文件名
mySql826113506.setSqlId(sqlid826113506);//指定使用的Sql的id
mySql826113506.addSubPara(ProposalContNo);//指定传入的参数
mySql826113506.addSubPara(tInsuredNo);//指定传入的参数
var aSQL=mySql826113506.getString();
		
//		var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and grpcontno='"+ProposalContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode";
		}
	//alert(aSQL);						
	turnPage.queryModal(aSQL, RiskGrid);
} 

//险种核保轨迹查询
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   var proposalno = RiskGrid.getRowColData(tSelNo,11);   
   //alert("PolNo="+tPolNo);	
  // var tSQL = "select ";
	 window.open("./RiskTraceMain.jsp?ContNo="+document.all('ContNo').value+"&PolNo="+tPolNo+"&ProposalNo="+proposalno+"&ContType=1","",sFeatures);
	
}


//合同核保轨迹查询
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