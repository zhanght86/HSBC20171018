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
//  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
//             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
//             + getWherePart('Operator', 'Operator')
//             + getWherePart('MakeDate', 'MakeDate');
  
	var  Operator0 = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  MakeDate0 = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid0="CustomerQualityManageSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("app.CustomerQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(PrtNo);//ָ������Ĳ���
	mySql0.addSubPara(ContNo);//ָ������Ĳ���
	mySql0.addSubPara(OperatePos);//ָ������Ĳ���
	mySql0.addSubPara(Operator0);//ָ������Ĳ���
	mySql0.addSubPara(MakeDate0);//ָ������Ĳ���
	var strSql=mySql0.getString();
	
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
/*  if (document.all('CQualityFlag').value == "1" &&document.all('CReasonType').value == "")
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
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  initAll();
  easyQueryClick();
}

function initData(){
//  var Sql= "select managecom,name,employdate,sex,'0',idno,agentcode from laagent where agentcode in (select agentcode from lcpol where contno='"+ContNo+"')";
    
	var sqlid1="CustomerQualityManageSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.CustomerQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
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
   /* var tSql = " select name,customerno,qualityflag,remark,operator,makedate"
              +" from lacustomerqualityrecord  where 1=1 "
              +getWherePart('CustomerNo','CustomerNo')
              +getWherePart('QualityFlag','QualityFlag')
              +getWherePart('birthday','Birthday')
              +getWherePart('makedate','StartDate',">=")
              +getWherePart('makedate','EndDate',"<=");
    */
//    var tSql = "select a.name,a.customerno,a.blacklistflag,a.remark,a.operator,a.modifydate "
//             + " from ldperson a where exists  "
//             + " (select '1' from lacustomerqualityrecord where customerno=a.customerno "
//             +getWherePart('makedate','StartDate','>=')
//             +getWherePart('makedate','EndDate','<=')
//             + " ) "
//             +getWherePart('CustomerNo','CustomerNo')
//             +getWherePart('blacklistflag','QualityFlag')
//             +getWherePart('birthday','Birthday')
//             ;
    
  	var  StartDate2 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate2 = window.document.getElementsByName(trim("EndDate"))[0].value;
  	var  CustomerNo2 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
  	var  QualityFlag2 = window.document.getElementsByName(trim("QualityFlag"))[0].value;
  	var  Birthday2 = window.document.getElementsByName(trim("Birthday"))[0].value;
	var sqlid2="CustomerQualityManageSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.CustomerQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(StartDate2);//ָ������Ĳ���
	mySql2.addSubPara(EndDate2);//ָ������Ĳ���
	mySql2.addSubPara(CustomerNo2);//ָ������Ĳ���
	mySql2.addSubPara(QualityFlag2);//ָ������Ĳ���
	mySql2.addSubPara(Birthday2);//ָ������Ĳ���
	var tSql=mySql2.getString();
    
   	initCustomerQualityGrid();
		initCustomerGrid();
    turnPage1.queryModal(tSql, CustomerGrid);
   
}

function showCustomerQuality(){
   
  // fm.CReasonType.disabled = false;
   fm.CQualityFlagName.value = "";
   //fm.CReasonTypeName.value = "";
   var tSelNo = CustomerGrid.getSelNo()-1;
   var tCustomerNo = CustomerGrid.getRowColData(tSelNo,2);
   fm.CustomerNoSel.value = tCustomerNo;
   fm.NameSel.value = CustomerGrid.getRowColData(tSelNo,1);
   fm.CQualityFlag.value = CustomerGrid.getRowColData(tSelNo,3);
   fm.Reason.value = CustomerGrid.getRowColData(tSelNo,4);
   
 /*  var tSql= "select a.name,a.customerno,a.blacklistflag,a.remark from ldperson a where customerno='"+tCustomerNo+"'";
 */
//   var tSql = " select name,customerno,qualityflag,remark,contno,operator,makedate"
//            + " from lacustomerqualityrecord  where 1=1 "
//            + " and customerno='"+tCustomerNo+"'"
//            +getWherePart('makedate','StartDate','>=')
//            +getWherePart('makedate','EndDate','<=');
   
 	var  StartDate3 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate3 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var sqlid3="CustomerQualityManageSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.CustomerQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tCustomerNo);//ָ������Ĳ���
	mySql3.addSubPara(StartDate3);//ָ������Ĳ���
	mySql3.addSubPara(EndDate3);//ָ������Ĳ���
	var tSql=mySql3.getString();
   
   turnPage.queryModal(tSql, CustomerQualityGrid);
}

function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="agent"){
 	//alert(fm.customer.value);
// 	var strSQL="select managecom,name,employdate,sex,'0',idno from laagent where trim(agentcode)=trim('"+fm.agent.value+"')";
 	
	var sqlid4="CustomerQualityManageSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.CustomerQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.agent.value);//ָ������Ĳ���
	var strSQL=mySql4.getString();
 	
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
   //    fm.CReasonType.value = "";
      // fm.CReasonType.disabled = true;
     }else
       
      // fm.CReasonType.disabled = false;
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

/*********************************************************************
 *  Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryCustomer()
{
    if (document.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
//    	arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        
    	var sqlid5="CustomerQualityManageSql5";
    	var mySql5=new SqlClass();
    	mySql5.setResourceName("app.CustomerQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
    	mySql5.addSubPara(document.all("InsuredNo").value);//ָ������Ĳ���
    	var strSql5=mySql5.getString();
    	arrResult = easyExecSql(strSql5, 1, 0);
    			
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
     // fm.Name.value = arrResult[0][1];
    //  fm.Sex.value = arrResult[0][2];
      fm.Birthday.value = arrResult[0][3];
    //  fm.IDType.value = arrResult[0][4];
    //  fm.IDNumber.value = arrResult[0][5];
      
   }  
}



function returnParent(){
  top.close();
	
}