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
	
	var  Operator = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  MakeDate = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid1="HospitalQualityManageSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.HospitalQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
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
 
  if(!verifyInput())
  {
  	return false;
  }
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
  initAll();
  easyQueryClick();
}

function initData(){
//  var Sql= "select managecom,name,employdate,sex,'0',idno,agentcode from laagent where agentcode in (select agentcode from lcpol where contno='"+ContNo+"')";
	
	var sqlid2="HospitalQualityManageSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.HospitalQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(ContNo);//ָ������Ĳ���
	var Sql=mySql2.getString();	
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
//  
//    var tSql = "select hospitcode,hospitalname,mngcom,(case when BlackListFlag is not null then BlackListFlag else '0' end),(case when HosState is not null then HosState else '0' end),remark,operator,ReasonType,makedate "
//             + " from ldhospital where 1=1 "
//             +getWherePart('hospitcode','HospitalCode1')
//             +getWherePart('ValidityDate','InputDate')
//             +getWherePart('HospitalName','HospitalName1','like')
//             +getWherePart('mngcom','ManageCom1','like')
//             ;
    
    var  Operator = window.document.getElementsByName(trim("HospitalCode1"))[0].value;
  	var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
  	var  HospitalName1 = window.document.getElementsByName(trim("HospitalName1"))[0].value;
  	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom1"))[0].value;
	var sqlid3="HospitalQualityManageSql2";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.HospitalQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(Operator);//ָ������Ĳ���
	mySql3.addSubPara(InputDate);//ָ������Ĳ���
	mySql3.addSubPara(HospitalName1);//ָ������Ĳ���
	mySql3.addSubPara(ManageCom1);//ָ������Ĳ���
	var tSql=mySql3.getString();
     if(document.all('FQualityFlag').value!=null&&document.all('FQualityFlag').value!="")
     {
     	  if(document.all('FQualityFlag').value=='0')
     	  {
     	  	tSql = tSql + " and (BlackListFlag is null or BlackListFlag='0') " ;
     	  }
     	  else if(document.all('FQualityFlag').value=='1')
     	  {
     	  	tSql = tSql + " and BlackListFlag='1' " ;
     	  }
     	  	
     }
   	initHospitalGrid();
    turnPage1.queryModal(tSql, HospitalGrid);
}

function showHospitalDetail(){
   

   var tSelNo = HospitalGrid.getSelNo()-1;
   //��ʼ�����ҽԺ�����Ϣ
   fm.HospitalCode.value = HospitalGrid.getRowColData(tSelNo,1);
   fm.HospitalName.value = HospitalGrid.getRowColData(tSelNo,2);
   fm.ManageCom.value = HospitalGrid.getRowColData(tSelNo,3);
   fm.QualityFlag.value = HospitalGrid.getRowColData(tSelNo,4);
   fm.QualityFlagType.value = HospitalGrid.getRowColData(tSelNo,8);
   fm.Reason.value = HospitalGrid.getRowColData(tSelNo,6);
  // var sql = "select shortname from ldcom where comcode='"+fm.ManageCom.value+"' ";
   var sqlid4="HospitalQualityManageSql3";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.HospitalQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
	var sql=mySql4.getString();
    var arrResult = easyExecSql(sql);
    if(arrResult!=null)
    {
    	fm.ManageComName.value = arrResult[0][0];
    	}
   quickGetName('hospitalqaflag',fm.QualityFlag,fm.QualityFlagName);
   quickGetName('hospitalqaflagsub',fm.QualityFlagType,fm.QualityFlagTypeName);

   //var tSQL_Sub = "select from ";
     
   
}
/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult)
{
	var arrResult = new Array();
	if( arrQueryResult != null )
	{
		
//		  var strSql = "select hospitcode,a.hospitalname,mngcom,a.validitydate,BlackListFlag "         		
//         		 		 + " from  LDHospital a where 1=1 "
//         		     + " and hospitcode = '"+arrQueryResult[0][0]+"'";
		  
	
			var sqlid5="HospitalQualityManageSql4";
			var mySql5=new SqlClass();
			mySql5.setResourceName("app.HospitalQualityManageSql"); //ָ��ʹ�õ�properties�ļ���
			mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
			mySql5.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
			var strSql=mySql5.getString();
		   
	 	arrResult = easyExecSql(strSql);
	 
	  document.all('HospitalCode1').value = arrResult[0][0];
    document.all('HospitalName1').value = arrResult[0][1];
    document.all('ManageCom1').value = arrResult[0][2];
    document.all('InputDate').value = arrResult[0][3]; 
     document.all('FQualityFlag').value = arrResult[0][4]; 
   }
   easyQueryClick();
} 

function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="agent"){
 	//alert(fm.customer.value);
 	var strSQL="select managecom,name,employdate,sex,'0',idno from laagent where trim(agentcode)=trim('"+fm.agent.value+"')";
 	
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
    	arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
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
function showHospital()
{
	
		showInfo = window.open( "../config/LDHospitalDetailQuery.html" );
	
}



function returnParent(){
  top.close();
	
}

function getClickHospital()
{
  	var tTempMain = document.all('QualityFlag').value;
  	if(tTempMain==null||trim(tTempMain)=='')
  	{
  		alert('������Ʒ��״̬!');
  		return false;
  	}
		sql_temp = " 1 and othersign=#"+tTempMain+"#   ";
   //alert(tManageCom.length);
  // alert(sql_temp);
  //showCodeList('hospitalqaflagsub',[this,QualityFlagTypeName],[0,1])
   showCodeList('hospitalqaflagsub',[document.all('QualityFlagType'),document.all('QualityFlagTypeName')],[0,1],null,sql_temp,"1",1);
}

function getClickUpHospital()
{
  	var tTempMain = document.all('QualityFlag').value;
  	if(tTempMain==null||trim(tTempMain)=='')
  	{
  		//alert('������Ʒ��״̬!');
  		return false;
  	}
	sql_temp = " 1 and othersign=#"+tTempMain+"#   ";
	showCodeListKey('hospitalqaflagsub',[document.all('QualityFlagType'),document.all('QualityFlagTypeName')],[0,1],null,sql_temp,"1",1);
}


var cacheWin=null;
function quickGetName(strCode, showObjc, showObjn) {
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
	var arrCode = null;	//�������
	var strCodeValue = "";	//����ֵ
	var cacheFlag = false;	//�ڴ��������ݱ�־
	var showObjn;
	var showObjc;
	//��������FORM


	//��������������ݲ�Ϊ�գ��Ų�ѯ���������κβ���
	if (showObjc.value != "")
	{
		//Ѱ��������
		if(cacheWin==null)
		{
			 cacheWin = searchMainWindow(this);
			if (cacheWin == false) { cacheWin = this; }
		}
		cacheFlag == false;
		
		{
			urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
			sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
			//�������ݿ����CODE��ѯ�����ز�ѯ�����strQueryResult
			//strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=150;      //�������ڵĿ��; 
			var iHeight=1;     //�������ڵĸ߶�; 
			var iTop = 1; //��ô��ڵĴ�ֱλ�� 
			var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
			strQueryResult = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			strQueryResult.focus();
		}
		//��ֳ�����
		try {
			if (!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("ҳ��ȱ������EasyQueryVer3.js");
				}

			for (i=0; i<arrCode.length; i++)
			{
				if (showObjc.value == arrCode[i][0])
				{
					showObjn.value = arrCode[i][1];
					break;
				}
			}
		}
	}
		catch(ex)
		{}
}
}
