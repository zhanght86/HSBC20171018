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
  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
             + getWherePart('Operator', 'Operator')
             + getWherePart('MakeDate', 'MakeDate');
	
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(fm.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = fm.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function InsertClick() {
/*  if (fm.all('CQualityFlag').value == "1" &&fm.all('CReasonType').value == "")
    {
       alert("��ѡ��ԭ�����");
       return false;
    }
  */
  
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
  fm.submit(); //�ύ
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
  initAll();
  easyQueryClick();
}

function initData(){
  var Sql= "select managecom,name,employdate,sex,'0',idno,agentcode from laagent where agentcode in (select agentcode from lcpol where contno='"+ContNo+"')";
    var arrResult = easyExecSql(Sql);
    try{fm.all('ManageCom').value= arrResult[0][0];}catch(ex){};         
    try{fm.all('Name').value= arrResult[0][1];}catch(ex){};          
    try{fm.all('EmployDate').value= arrResult[0][2];}catch(ex){};     
    try{fm.all('sex').value= arrResult[0][3];}catch(ex){};            
    try{fm.all('IDType').value= arrResult[0][4];}catch(ex){};              
    try{fm.all('IDNumber').value= arrResult[0][5];}catch(ex){};   
    try{fm.all('agent').value=arrResult[0][6]}catch(ex){};
    try{fm.all('agentname').value=arrResult[0][1]}catch(ex){};
}


function easyQueryClick(){
   /* var tSql = " select name,customerno,qualityflag,remark,operator,makedate"
              +" from lacustomerqualityrecord  where 1=1 "
              +getWherePart('CustomerNo','CustomerNo')
              +getWherePart('QualityFlag','QualityFlag')
              +getWherePart('birthday','Birthday')
              +getWherePart('makedate','StartDate',">=")
              +getWherePart('makedate','EndDate',"<=");
    */
    var tSql = "select a.grpname,a.customerno,nvl(a.blacklistflag,'��'),a.remark,a.operator,a.makedate "
             + " from ldgrp a where 1=1  "
             +getWherePart('CustomerNo','CustomerNo')
             +getWherePart('GrpName','Name')
             +getWherePart('BlackListFlag','QualityFlag')
             +getWherePart('GrpNature','GrpNature')
             +" order by a.blacklistflag"
             ;
   	initCustomerQualityGrid();
		initCustomerGrid();
    turnPage1.queryModal(tSql, CustomerGrid);
   
}

function showCustomerQuality(){
   
  // fm.CReasonType.disabled = false;
   fm.GrpCQualityFlagName.value = "";
   //fm.CReasonTypeName.value = "";
   var tSelNo = CustomerGrid.getSelNo()-1;
   var tCustomerNo = CustomerGrid.getRowColData(tSelNo,2);
   fm.CustomerNoSel.value = tCustomerNo;
   fm.NameSel.value = CustomerGrid.getRowColData(tSelNo,1);
   //fm.CQualityFlag.value = CustomerGrid.getRowColData(tSelNo,3);
   fm.Reason.value = CustomerGrid.getRowColData(tSelNo,4);
   
 /*  var tSql= "select a.name,a.customerno,a.blacklistflag,a.remark from ldperson a where customerno='"+tCustomerNo+"'";
 */
   var tSql = " select name,customerno,qualityflag,remark,contno,operator,makedate"
            + " from lacustomerqualityrecord  where 1=1 "
            + " and customerno='"+tCustomerNo+"'";
   turnPage.queryModal(tSql, CustomerQualityGrid);
}

function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="agent"){
 	//alert(fm.customer.value);
 	var strSQL="select managecom,name,employdate,sex,'0',idno from laagent where trim(agentcode)=trim('"+fm.agent.value+"')";
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);                         
   try{fm.all('ManageCom').value= arrResult[0][0];}catch(ex){};         
   try{fm.all('Name').value= arrResult[0][1];}catch(ex){};          
   try{fm.all('EmployDate').value= arrResult[0][2];}catch(ex){};     
   try{fm.all('sex').value= arrResult[0][3];}catch(ex){};            
   try{fm.all('IDType').value= arrResult[0][4];}catch(ex){};              
   try{fm.all('IDNumber').value= arrResult[0][5];}catch(ex){};                
   //try{fm.all('BlacklistFlagNo').value= arrResult[0][31];}catch(ex){};
  
  
   //try{   
    // if(arrResult[0][31]==0) fm.all('BlacklistFlagName').value="����";  	
     //    else
     //    fm.all('BlacklistFlagName').value="������";   
     // }
      
   //catch(ex){};       
  
   //try{fm.all('Remark').value= arrResult[0][33];}catch(ex){};        
   return;       
 }
   if(cCodeName =="QualityState")
   {
     if(fm.all('QualityState').value == "0")
     {
       //�쳣��𲻿�ѡ
   //    fm.CReasonType.value = "";
      // fm.CReasonType.disabled = true;
     }else
       
      // fm.CReasonType.disabled = false;
     return;
   }
// if(cCodeName=="paymode"){
//   	if(fm.all('PayMode').value=="4"){
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

/*********************************************************************
 *  Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryCustomer()
{
    if (fm.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
    	arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + fm.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
          alert("δ�鵽��������Ϣ");
          displayInsuredInfo(new Array());
          emptyUndefined();
        }
        else
        {
          //displayAppnt(arrResult[0]);
          displayInsuredInfo(arrResult[0]);
        }
        
    }
}

/*********************************************************************
 *  Ͷ���˲�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function showAppnt1()
function showCustomer()
{
	
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=Customer" );
	
}


//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{  
   if(arrResult!=null){
      fm.CustomerNo.value = arrResult[0][0];
      fm.Name.value = arrResult[0][1];
      fm.Sex.value = arrResult[0][2];
      fm.Birthday.value = arrResult[0][3];
      fm.IDType.value = arrResult[0][4];
      fm.IDNumber.value = arrResult[0][5];
      
   }  
}

function showAppnt()
{
 if (fm.all("CustomerNo").value == "" ) { 
 	showAppnt1();
 }
 else
 {
 	arrResult = easyExecSql("select b.CustomerNo,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='" + fm.all("GrpNo").value + "'", 1, 0);			
	 if (arrResult == null) {
            alert("δ�鵽Ͷ����λ��Ϣ");
	  } 
	  else {
	    displayAddress(arrResult[0]);
	  }
 }
}

function showAppnt1()
{
		showInfo = window.open( "../sys/GroupMain.html" );
} 

function afterQuery4(arrResult)
{	
  if(arrResult!=null)
  {
		fm.CustomerNo.value = arrResult[0][0];
		fm.Name.value = arrResult[0][1];
		//fm.Asset.value = arrResult[0][2];
		fm.GrpNature.value = arrResult[0][3];
		//fm.BusinessType.value = arrResult[0][4];
		//fm.Peoples.value = arrResult[0][5];
		//fm.Fax.value = arrResult[0][6];
		fm.QualityFlag.value = arrResult[0][8];
  }

}


function returnParent(){
  top.close();
	
}