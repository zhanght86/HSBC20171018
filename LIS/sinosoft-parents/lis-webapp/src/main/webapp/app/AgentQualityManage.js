//�������ƣ�UWCustomerQuality.js
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
function queryClick()
{
	// ��ʼ�����
	//initQuestGrid();
	//initContent();
	
	// ��дSQL���
//  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad" +
//  				" where PrtNo='" + PrtNo 
//             + "' and ContNo='" + ContNo 
//             + "' and OperatePos='" + OperatePos + "'"
//             + getWherePart('Operator', 'Operator')
//             + getWherePart('MakeDate', 'MakeDate');
  
  	var  Operator = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  MakeDate = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid1="AgentQualityManageSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(PrtNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(OperatePos);//ָ������Ĳ���
	mySql1.addSubPara(Operator);//ָ������Ĳ���
	mySql1.addSubPara(MakeDate);//ָ������Ĳ���
	var strSql=mySql1.getString();
	
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function InsertClick() {
  if (document.all('QualityState').value == "1" &&document.all('ReasonType').value == "")
    {
       alert("��ѡ��ԭ�����");
       return false;
    }
  
  fm.PrtNo.value = PrtNo;
  fm.ContNo.value = ContNo;
//  fm.OperatePos.value = OperatePos; 
  
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  easyQueryClick();
}

function initData(){
//  var Sql= "select managecom,name,employdate,sex,'0',idno,agentcode from laagent where agentcode in (select agentcode from lcpol where contno='"+ContNo+"')";
  
	var sqlid1="AgentQualityManageSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	var Sql=mySql1.getString();

  
  var arrResult = easyExecSql(Sql);
    try{document.all('ManageCom').value= arrResult[0][0];}catch(ex){};         
    try{document.all('Name').value= arrResult[0][1];}catch(ex){};          
    try{document.all('EmployDate').value= arrResult[0][2];}catch(ex){};     
    try{document.all('sex').value= arrResult[0][3];}catch(ex){};            
    try{document.all('IDType').value= arrResult[0][4];}catch(ex){};              
    try{document.all('IDNumber').value= arrResult[0][5];}catch(ex){};   
    try{document.all('agent').value=arrResult[0][6]}catch(ex){};
    try{document.all('agentname').value=arrResult[0][1]}catch(ex){};
}


function easyQueryClick(){
    var strOperate = "like";
  /*  var tSql = "select agentcode ||'--'||name,agentcode,managecom,qualityflag,"
              +"(select codename from ldcode where code =a.unusualtype and codetype = 'unusualtype'),"
              +"operator,makedate from laagentqualityrecord a where 1=1 "
              +getWherePart('AgentCode','AgentCode')
              +getWherePart('QualityFlag','QualityFlag')
              +getWherePart('managecom','ManageCom',strOperate)
              +getWherePart('makedate','MakeDate');
    */
    var tSql = "select concat(concat(a.agentcode , '--'),b.name),a.agentcode,a.managecom,a.blacklisflag, "
             + " (select codename from ldcode where code = a.reasontype and codetype = 'reasontype'), "
             + " a.reason,b.name,b.idtype,b.idno "
             + " from latree a,laagent b "
             + " where a.agentcode=b.agentcode and "
             + " exists (select '1' from laagentqualityrecord where agentcode = a.agentcode "
             +getWherePart('makedate','InputDate')
             + " ) "
             +getWherePart('a.AgentCode','AgentCode')
             +getWherePart('a.blacklisflag','QualityFlag')
             +getWherePart('a.managecom','ManageCom',strOperate)
             //+getWherePart('b.idtype','IDType') 
             //+getWherePart('b.idno','IDNumber')
             ;
    
//    var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
//    var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
//    var  QualityFlag = window.document.getElementsByName(trim("QualityFlag"))[0].value;
//    var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
//	var sqlid1="AgentQualityManageSql2";
//	var mySql1=new SqlClass();
//	mySql1.setResourceName("app.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
//	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
//	mySql1.addSubPara(InputDate);//ָ������Ĳ���
//	mySql1.addSubPara(AgentCode);//ָ������Ĳ���
//	mySql1.addSubPara(QualityFlag);//ָ������Ĳ���
//	mySql1.addSubPara(ManageCom);//ָ������Ĳ���
//	var tSql=mySql1.getString();
    
       	initAgentQualityGrid();
				initAgentGrid();
       turnPage1.queryModal(tSql, AgentGrid);
}

function showAgentQuality(){
   
   fm.ReasonType.disabled = false;
   fm.QualityStateName.value = "";
   fm.ReasonTypeName.value = "";
   var tSelNo = AgentGrid.getSelNo()-1;
   var tAgentCode = AgentGrid.getRowColData(tSelNo,2);
  /* var tSql= " select a.name,a.managecom,a.idtype,a.idno,b.blacklisflag,b.reasontype,b.reason "
            +" from laagentqualityrecord a,latree b where a.agentcode  = b.agentcode and a.agentcode ='"+tAgentCode+"'";
   var arrResult = easyExecSql(tSql);
   if(arrResult != null)
   {
      fm.AgentCodeSel.value = tAgentCode;
      fm.NameSel.value = arrResult[0][0];
      fm.ManageComSel.value = arrResult[0][1];
      fm.IDTypeSel.value = arrResult[0][2];
      fm.IDNumberSel.value = arrResult[0][3];
      fm.QualityState.value = arrResult[0][4];
      fm.ReasonType.value = arrResult[0][5];
      fm.Reason.value = arrResult[0][6];
   }*/
   		fm.AgentCodeSel.value = tAgentCode;
      fm.NameSel.value = AgentGrid.getRowColData(tSelNo,7);
      fm.ManageComSel.value = AgentGrid.getRowColData(tSelNo,3);
      fm.IDTypeSel.value = AgentGrid.getRowColData(tSelNo,8);
      fm.IDNumberSel.value = AgentGrid.getRowColData(tSelNo,9);
      fm.QualityState.value = AgentGrid.getRowColData(tSelNo,4);
      fm.ReasonTypeName.value = AgentGrid.getRowColData(tSelNo,5);
      fm.Reason.value = AgentGrid.getRowColData(tSelNo,6);
//     var tSql = "select concat(concat(agentcode ,'--'),name),agentcode,managecom,qualityflag,"
//              +"(select codename from ldcode where code =a.unusualtype and codetype = 'unusualtype'),"
//              +"contno,operator,makedate from laagentqualityrecord a where 1=1 "
//              + " and agentcode='"+tAgentCode+"' "
//              +getWherePart('a.makedate','InputDate');
     
	    var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
	 	var sqlid1="AgentQualityManageSql3";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("app.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	 	mySql1.addSubPara(tAgentCode);//ָ������Ĳ���
	 	mySql1.addSubPara(InputDate);//ָ������Ĳ���
	 	var tSql=mySql1.getString();
     
        turnPage.queryModal(tSql, AgentQualityGrid);
}

function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="agent"){
 	//alert(fm.customer.value);
// 	var strSQL="select managecom,name,employdate,sex,'0',idno from laagent where " 
// 				+ "trim(agentcode)=trim('"+fm.agent.value+"')";
 	
 	var agent = trim(fm.agent.value);
 	var sqlid1="AgentQualityManageSql4";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("app.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
 	mySql1.addSubPara(agent);//ָ������Ĳ���
 	var tSql=mySql1.getString();
 	
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);                         
   try{document.all('ManageCom').value= arrResult[0][0];}catch(ex){};         
   try{document.all('Name').value= arrResult[0][1];}catch(ex){};          
   try{document.all('EmployDate').value= arrResult[0][2];}catch(ex){};     
   try{document.all('sex').value= arrResult[0][3];}catch(ex){};            
   try{document.all('IDType').value= arrResult[0][4];}catch(ex){};              
   try{document.all('IDNumber').value= arrResult[0][5];}catch(ex){};                
   //try{document.all('BlacklistFlagNo').value= arrResult[0][31];}catch(ex){};
  
  
   //try{   
    // if(arrResult[0][31]==0) document.all('BlacklistFlagName').value="����";  	
     //    else
     //    document.all('BlacklistFlagName').value="������";   
     // }
      
   //catch(ex){};       
  
   //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};        
   return;       
 }
   if(cCodeName =="QualityState")
   {
     if(document.all('QualityState').value == "0")
     {
       //�쳣��𲻿�ѡ
       fm.ReasonType.value = "";
       fm.ReasonType.disabled = true;
     }else
       
       fm.ReasonType.disabled = false;
     return;
   }
// if(cCodeName=="paymode"){
//   	if(document.all('PayMode').value=="4"){
//   	  //divLCAccount1.style.display="";
//    }
//    else
//    {
//   	  //divLCAccount1.style.display="none";
//      //alert("accountImg===");	
//    }
// }
 //alert("aaa");
// afterCodeSelect2( cCodeName, Field );
}

function queryAgent()
{
	
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
//	var strSql = "select AgentCode,Name from LAAgent where " 
//				+ "AgentCode='" + cAgentCode +"'";
	
 	var sqlid1="AgentQualityManageSql5";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("app.AgentQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
 	mySql1.addSubPara(cAgentCode);//ָ������Ĳ���
 	var tSql=mySql1.getString();
	
	var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
      } 
	}
}


//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.ManageCom.value = arrResult[0][2];
  //	fm.Name.value = arrResult[0][3];
  //	fm.IDType.value = arrResult[0][11];
  	//fm.IDNumber.value = arrResult[0][12];
  }
  
}


function returnParent(){
  top.close();
	
}