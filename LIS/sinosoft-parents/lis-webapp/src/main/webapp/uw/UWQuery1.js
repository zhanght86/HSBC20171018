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
    	mySql1.setResourceName("uw.UWQuery1"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(ProposalContNo);//ָ������Ĳ���
    	mySql1.addSubPara(ProposalContNo);//ָ������Ĳ���
    	strSQL=mySql1.getString();
    	
        var arrResult=easyExecSql(strSQL);
        //alert(arrResult);   
        if (ContType == "2")
        {
            try{document.all('ContNo').value= arrResult[0][3];}catch(ex){}; //���嵥��ʾ����Ͷ������
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
	mySql2.setResourceName("uw.UWQuery1"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(ProposalContNo);//ָ������Ĳ���
	mySql2.addSubPara(ProposalContNo);//ָ������Ĳ���
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
	mySql3.setResourceName("uw.UWQuery1"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(ProposalContNo);//ָ������Ĳ���
	mySql3.addSubPara(tInsuredNo);//ָ������Ĳ���
	var aSQL=mySql3.getString();
	
	turnPage.queryModal(aSQL, RiskGrid);
} 

//���ֺ˱��켣��ѯ
function showRiskResult(){
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   //alert("PolNo="+tPolNo);	
	 window.open("./RiskTraceMain.jsp?ContNo="+document.all('ContNo').value+"&PolNo="+tPolNo,"window1");
	
}


//��ͬ�˱��켣��ѯ
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