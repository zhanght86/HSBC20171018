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
 	
    	var sqlid1="";
        if (ContType == "2")
        {
            //strSQL="select * from LCCont where ContNo='"+ ProposalContNo+ "'";
        	sqlid1="UWQuery11";
        }
        else
        {
//            strSQL="select * from LCCont where ProposalContNo='"+ ProposalContNo+ "'";
            sqlid1="UWQuery12";
        }

    	var mySql1=new SqlClass();
    	mySql1.setResourceName("uw.UWQuery1"); //指定使用的properties文件名
    	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    	mySql1.addSubPara(ProposalContNo);//指定传入的参数
    	mySql1.addSubPara(ProposalContNo);//指定传入的参数
    	strSQL=mySql1.getString();
    	
        var arrResult=easyExecSql(strSQL);
        //alert(arrResult);   
        if (ContType == "2")
        {
            try{document.all('ContNo').value= arrResult[0][3];}catch(ex){}; //集体单显示集体投保单号
        }         
        else
        {
            try{document.all('ContNo').value= arrResult[0][2];}catch(ex){};    
        }        
             
        try{document.all('ManageCom').value= arrResult[0][9];}catch(ex){};          
        try{document.all('SaleChnl').value= arrResult[0][16];}catch(ex){};     
        try{document.all('AgentCode').value= arrResult[0][12];}catch(ex){};            
        try{document.all('Remark').value= arrResult[0][48];}catch(ex){};  
                  
        return;       
    }	
}

 
function queryinsured(){ 
  //alert(ContNo); 
    var aSQL;
    
    var sqlid2="";
    if (ContType == "2")
    {
//        aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where ContNo='"+ ProposalContNo+ "'";  
        sqlid3="UWQuery13";
    }
    else
    {
//        aSQL="select InsuredNo,Name,IDType,IDNo from LCInsured where PrtNo='"+ ProposalContNo+ "'";  
        sqlid3="UWQuery14";
    }
  
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWQuery1"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(ProposalContNo);//指定传入的参数
	mySql2.addSubPara(ProposalContNo);//指定传入的参数
	aSQL=mySql2.getString();
	
    turnPage.queryModal(aSQL, PolAddGrid);
}

function  showRiskInfo(){
	
	var tSelNo = PolAddGrid.getSelNo()-1;
    var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);	
    //alert(tInsuredNo);
    var sqlid3="";
    if (ContType == "2")
    {
//        var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and ContNo='"+ProposalContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode";
        sqlid3="UWQuery15";
    }
    else
    {
//        var aSQL="select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and PrtNo='"+ProposalContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode";
        sqlid3="UWQuery16";
    }	
	 
    var mySql3=new SqlClass();
	mySql3.setResourceName("uw.UWQuery1"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(ProposalContNo);//指定传入的参数
	mySql3.addSubPara(tInsuredNo);//指定传入的参数
	var aSQL=mySql3.getString();
	
	turnPage.queryModal(aSQL, RiskGrid);
} 

//险种核保轨迹查询
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   //alert("PolNo="+tPolNo);	
	 window.open("./RiskTraceMain.jsp?ContNo="+document.all('ContNo').value+"&PolNo="+tPolNo,"window1");
	
}


//合同核保轨迹查询
function QueryContTrace(){
	
	if (ContType == "2")
    {
        window.open("./ContTraceMain.jsp?ContNo="+ProposalContNo,"window1"); 
    }         
    else
    {
        window.open("./ContTraceMain.jsp?ContNo="+document.all('ContNo').value,"window1");  
    }      
	
} 