//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""	// "1--¼�룬"2"--��ѯ
var mGrpFlag = ""; 	//���˼����־,"0"��ʾ����,"1"��ʾ����.
var cflag = "0";        //�ļ���¼��λ��
var sign=0;
var risksql;
var arrGrpRisk = null;
var mainRiskPolNo="";
var cMainRiskCode="";
var mSwitch = parent.VD.gVSwitch;
window.onfocus = myonfocus;
var hiddenBankInfo = "";
var parameter1="";
var parameter2="";
var nrPolNo;
/*********************************************************************
 *  ѡ�����ֺ�Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function intorisk()
{
	if(fm.all('RiskCode').value=="")
	 {
		alert("����ѡ������!");
		return;
	 }
	
	try	{
   //alert(cCodeName);//mark by yaory ��ʼ�����ֽ���
	  	//alert(fm.all('RiskCode').value);
if(fm.all('RiskCode').value!=null){
  getRiskInput(fm.all('RiskCode').value, "1");//LoadFlag��ҳ���ʼ����ʱ������
  //alert("ok");
  //alert(fm.RiskCode.value);
  if(fm.RiskCode.value=="00144000")
  {
  	//alert("ok");
   InsuredGrid.setRowColData(0, 6, "1");  	
  }
}
else
{
getRiskInput(parameter2.value, "1");//LoadFlag��ҳ���ʼ����ʱ������
		
}

}catch( ex ) {}
}


function afterCodeSelect( cCodeName, Field ) {
	//alert(cCodeName);
	//alert(LoadFlag);
	//alert("ok");
	////�����ð�Ŧ�¼���//////
	//alert(parent.VD.gVSwitch.getVar( "InsuredNo" ));
	parameter1=cCodeName;
	parameter2=Field;
	if(parameter1=="RiskCode" && LoadFlag==1)
	{
	//alert(cCodeName);
	//alert(Field);
	return;
}
    if(parameter1=="insured1"){
       for(i=0;i<BnfGrid.mulLineCount;i++)
       {
          var tInsuredNo = BnfGrid.getRowColData(i,10);
          var tNo = mSwitch.getVar( "InsuredNo" );
          if(tInsuredNo != tNo)
          {
             alert("��������Ϊ������"+tNo+"�µ�������Ϣ,��ѡ��ñ������ˣ�");
             BnfGrid.setRowColData(i,10,"");
             return false;
          }
       }
    }
	try	{
	  //����ѡ��
	
//	  try{
//	    if( type =="noScan" && cCodeName == 'RiskInd')//������ɨ���¼��
//		  {
//			  var strSql = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pa')=1 ";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
//	        //alert(strSql);
//	      var arrResult = easyExecSql(strSql);
//	       //alert(arrResult);
//	      if (arrResult == null) {
//	        alert("���� ["+ManageCom+"] ��Ȩ¼������ ["+Field.value+"] ��Ͷ����!");
//	        gotoInputPage();
//	        return ;
//	      }
//		    return;
//      }
//
//    }
//    catch(ex) {}

//	  try{
//	    //alert(cCodeName);
//	    if(cCodeName == 'RiskInd'){
//	      if (typeof(prtNo)!="undefined" && typeof(type)=="undefined" )//������ɨ���¼��
//	      {
//	        //alert(cCodeName);
//	        var strSql2 = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pz')=1 ";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
//	        //alert(strSql2);
//	        var arrResult2 = easyExecSql(strSql2);
//	        //alert(arrResult2);
//	        if (arrResult2 == null) {
//	          alert("���� ["+ManageCom+"] ��Ȩ¼������ ["+Field.value+"] ��Ͷ����!");
//	          gotoInputPage();
//	          return ;
//	        }
//	      }
//	      return;
//	    }
//    }
//    catch(ex){}
     
    //���ѷ�ʽѡ��Ĵ���
    try{
	    //alert(cCodeName);
	    if(cCodeName.substring(1,cCodeName.indexOf("-")) == 'PayIntv'){
 		 if (Field.value == "0") 
           {
         	  
         	  //2008-12-09 �͸��� ��ȡ������һ��!
         	  if(!(fm.all("InsuYear").value==null||fm.all("InsuYear").value==""))
         	  {
         		  fm.all('PayEndYear').value=fm.all('InsuYear').value;
       	  		  fm.all('PayEndYearFlag').value=fm.all('InsuYearFlag').value;	
       	  		  fm.all('PayEndYearName').value=fm.all('InsuYearName').value;
       	  		  fm.all("PayEndYear").readOnly = true;
         	  }
           }
           
           return;
	    }
	   }
    catch(ex){}
   //alert(cCodeName);//mark by yaory ��ʼ�����ֽ���
   //alert(LoadFlag);
    if( cCodeName == "RiskInd" || cCodeName == "RiskGrp" || cCodeName == "RiskCode" || cCodeName.substring(0,3)=="***" || cCodeName.substring(0,2)=="**" || cCodeName.substring(0,2)=="%%")
	  {
	  	//alert(LoadFlag);
	    var tProposalGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
	    //���ϼƻ���û��Ӧ��ʵʱ�Ĵ����ݿ��в�ѯ yaorywln
	    //alert(parent.VD.gVSwitch.getVar( "InsuredNo" ));
	    var continsuredno=parent.VD.gVSwitch.getVar( "InsuredNo" );
	    var contplansql = "select contplancode from lcinsured where grpcontno='"+tProposalGrpContNo+"' and insuredno='"+continsuredno+"'";
	    var tContPlanCode = easyExecSql(contplansql);
	    //var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
	    //alert("mainRiskPolNo:"+mainRiskPolNo);
	    if(mainRiskPolNo=="" && parent.VD.gVSwitch.getVar("mainRiskPolNo")==true){
	      mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
	    }
	    if(cCodeName=="RiskCode" || cCodeName.substring(0,2)=="**"){
	    	
	      if(!isSubRisk(Field.value)){
	    	  cMainRiskCode=Field.value;
	    	}
	    	
	      else if(isSubRisk(Field.value)&&mainRiskPolNo!=""){
	        var mainRiskSql="select riskcode from lcpol where polno='"+mainRiskPolNo+"'";
	        var arr = easyExecSql(mainRiskSql);
	        cMainRiskCode=arr[0];
	      }
	  	  //alert("mainriskcode:"+cMainRiskCode);
	  	  //alert("mainriskpolno:"+mainRiskPolNo);
	  	}
	  	//alert("mainRiskPolNo2:"+mainRiskPolNo);
	  	//alert("1");
	  	if(LoadFlag!=7 && LoadFlag!=2 )
	  	{
	  		//alert("2");
	  	  getRiskInput(Field.value, LoadFlag);//LoadFlag��ҳ���ʼ����ʱ������
	  	}
	  	else
	  	{
	  	  if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
	  		{
	  			//alert("no contplan");
	  		
	  				//���û�б��ռƻ�����,��ѯ��û��Ĭ��ֵ�������Ĭ��ֵҲ��Ҫ���ú�̨��ѯ
            
	  				var strsql = "select ContPlanCode from LCContPlan where ContPlanCode='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
	  			
	  				var defultContPlanCode=easyExecSql(strsql);
	  			
	  			
	  				if (defultContPlanCode!=null && defultContPlanCode[0][0]=='00')//add [] yaory
	  				{
	  				//alert("have default");
	  				queryRiskPlan( tProposalGrpContNo,Field.value,defultContPlanCode,cMainRiskCode );
	  				}
	  				else
	  				{
	  					getRiskInput(Field.value, LoadFlag);//LoadFlag��ҳ���ʼ����ʱ������
	  				}

	  		}
	  		else
	  		{
	  				//alert("have plan");
	  				queryRiskPlan( tProposalGrpContNo,Field.value,tContPlanCode,cMainRiskCode );
	  		}
	  	}
	  	//��ɨ���ͼƬ������һҳ
	    try {
	      goToPic(0);
	    }
	    catch(ex2){}
	  	  return;
	  }
	  //�Զ���д��������Ϣ
	  if (cCodeName == "customertype") {
	  	//alert(Field.value);
	    if (Field.value == "01") {
	  	  if(ContType!="2")
	  	  {
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, fm.all("AppntName").value);
          BnfGrid.setRowColData(index-1, 3, fm.all("AppntIDType").value);

          BnfGrid.setRowColData(index-1, 4, fm.all("AppntIDNo").value);
          BnfGrid.setRowColData(index-1, 5,parent.VD.gVSwitch.getVar( "RelationToAppnt"));

          BnfGrid.setRowColData(index-1, 8, fm.all("AppntHomeAddress").value);
          //hl
          BnfGrid.setRowColData(index-1, 9, fm.all("AppntNo").value);
          //alert("toubaoren:"+fm.all("AppntNo").value)

	  	  }
	  	  else
	  	  {
	  	    alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, "");
          BnfGrid.setRowColData(index-1, 3, "");
          BnfGrid.setRowColData(index-1, 4, "");
          BnfGrid.setRowColData(index-1, 5, "");
          BnfGrid.setRowColData(index-1, 8, "");
	  	  }
	  	}
	  	else if (Field.value == "02") {
	  	  var index = BnfGrid.mulLineCount;
        BnfGrid.setRowColData(index-1, 2, fm.all("Name").value);
        BnfGrid.setRowColData(index-1, 3, fm.all("IDType").value);
        BnfGrid.setRowColData(index-1, 4, fm.all("IDNo").value);
        BnfGrid.setRowColData(index-1, 5, "00");
        //BnfGrid.setRowColData(index-1, 8, fm.all("AppntHomeAddress").value);
        //hl
        BnfGrid.setRowColData(index-1, 8, fm.all("HomeAddress").value);
     
        BnfGrid.setRowColData(index-1, 9, fm.all("InsuredNo").value);
	//alert("4544564"+fm.all("InsuredNo").value);
	  	}
	  	return;
    }
   

	  	//�շѷ�ʽѡ��
    if (cCodeName == "PayLocation") {
	    if (Field.value != "0") {
	  	  if (hiddenBankInfo=="") hiddenBankInfo = DivLCKind.innerHTML;
	  	  fm.all("BankCode").className = "readonly";
	  	  fm.all("BankCode").readOnly = true;
	  	  fm.all("BankCode").tabIndex = -1;
	  	  fm.all("BankCode").ondblclick = "";

	  	  fm.all("AccName").className = "readonly";
	  	  fm.all("AccName").readOnly = true;
	  	  fm.all("AccName").tabIndex = -1;
	  	  fm.all("AccName").ondblclick = "";

	  	  fm.all("BankAccNo").className = "readonly";
	  	  fm.all("BankAccNo").readOnly = true;
	  	  fm.all("BankAccNo").tabIndex = -1;
	  	  fm.all("BankAccNo").ondblclick = "";
	  	}
	  	else{
	  	  if (hiddenBankInfo!="") DivLCKind.innerHTML = hiddenBankInfo;
        strSql = "select BankCode,BankAccNo,AccName from LCAppNT where AppNtNo = '" + fm.all('AppntCustomerNo').value + "' and contno='"+fm.all('ContNo').value+"'";
	  		var arrAppNtInfo = easyExecSql(strSql);
	  		//alert(strSql);
	  	  fm.all("BankCode").value = arrAppNtInfo[0][0];
	  	  fm.all("AccName").value = arrAppNtInfo[0][2];
	  	  fm.all("BankAccNo").value = arrAppNtInfo[0][1];
	  	  fm.all("PayLocation").value = "0";
	  	  fm.all("PayLocation").focus();
	  	}
	  	return;
    }
	  //���δ���ѡ��
	  if(cCodeName == "DutyCode"){
	    var index = DutyGrid.mulLineCount;
	    var strSql = "select dutyname from lmduty where dutycode='"+Field.value+"'";
	    var arrResult = easyExecSql(strSql);
	    var dutyname= arrResult[0].toString();
	    DutyGrid.setRowColData(index-1, 2, dutyname);
	    return;
	  }
	  //add by yaory
	  if(cCodeName == "insuredpeople")
	  {
	  	//alert("ok");
	  	var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 10, fm.all("CustomerNo").value);
          //BnfGrid.setRowColData(index-1, 11, fm.all("AppntIDType").value);
          //BnfGrid.setRowColData(index-1, 12, fm.all("AppntIDNo").value);
	  }
	  //alert(cCodeName);
	  if(cCodeName == "PolCalRule1"){  //��ʱδ��
	    if(Field.value=="1"){       //ͳһ����
	  	  divFloatRate.style.display="none";
	  	  divFloatRate2.style.display="";
	  	}
	  	else if(Field.value=="2"){  //Լ�������ۿ�
	  	  fm.all('FloatRate').value="";
	  	  divFloatRate.style.display="";
	  	  divFloatRate2.style.display="none";
	  	}
	  	else{
	  	divFloatRate.style.display="none";
	  	divFloatRate2.style.display="none";
	  	}
	  	return;
	  }
	  if(cCodeName=="PayEndYear"){
	    getOtherInfo(Field.value);
	  	return;
	  }
	  if(cCodeName=="GetYear"){
      getGetYearFlag(Field.value);
	  	return;
	  }
	  if(cCodeName=="PayRuleCode"){
	    fm.action="./ProposalQueryPayRule.jsp?AppFlag=0";
	    fm.submit();
	    //parent.fraSubmit.window.location ="./ProposalQueryPayRule.jsp?GrpContNo="
	    //		+ fm.all('GrpContNo').value+"&RiskCode="+fm.all('RiskCode').value
	    //		+"&InsuredNo="+fm.all('InsuredNo').value
	    //		+"&PayRuleCode="+fm.all('PayRuleCode').value
	    //		+"&JoinCompanyDate="+fm.all(JoinCompanyDate).value
	    //		+"&AppFlag=0";
	    return;
	  }
  }
	catch( ex ) {
	}

}
/*********************************************************************
 *  �ύǰ��У�顢����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function beforeSubmit()
{
	
  if( verifyInput() == false ) return false;
}

/*********************************************************************
 *  ����LoadFlag����һЩFlag����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function convertFlag( cFlag )
{
  //alert("cFlag:" + cFlag);
	if( cFlag == "1"|| cFlag == "99")		// ����Ͷ����ֱ��¼��
	{
		mCurOperateFlag = "1";
		mGrpFlag = "0";
		return;
	}
	if( cFlag == "2" || cFlag == "7" || cFlag == "8"|| cFlag == "9" || cFlag == "13"||cFlag == "14"||cFlag == "16"||cFlag=="23"||cFlag=="18")		// �����¸���Ͷ����¼��
	{
		mCurOperateFlag = "1";
		mGrpFlag = "1";
		return;
	}
	if( cFlag == "3"||cFlag == "6" )		// ����Ͷ������ϸ��ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "0";
		return;
	}
	if( cFlag == "4" )		// �����¸���Ͷ������ϸ��ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "1";
		return;
	}
	if( cFlag == "5"||cFlag=="25" )		// ����Ͷ�������˲�ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "3";
		return;
	}
	if(cFlag=="99"&&checktype=="1")
	{
		mGrpFlag = "0";
		return;
	}
	if(cFlag=="99"&&checktype=="2")
	{
		mGrpFlag = "1";
		return;
	}
}

/**
 * ��ȡ�ü���������������Ϣ
 */
function queryGrpPol(prtNo, riskCode) {
  var findFlag = false;

  //����ӡˢ�Ų��ҳ����е��������
  if (arrGrpRisk == null) {
    var strSql = "select GrpProposalNo, RiskCode from LCGrpPol where PrtNo = '" + prtNo + "'";
    arrGrpRisk  = easyExecSql(strSql);

    //ͨ���б�����������ҵ�����
    for (i=0; i<arrGrpRisk.length; i++) {
      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
             + arrGrpRisk[i][1] + "' and RiskVer = '2002'";
      var riskDescribe = easyExecSql(strSql);

      if (riskDescribe == "M") {
        top.mainRisk = arrGrpRisk[i][1];
        break;
      }
    }
  }
  //alert(arrGrpRisk);

  //��ȡѡ������ֺͼ���Ͷ��������
  for (i=0; i<arrGrpRisk.length; i++) {
    if (arrGrpRisk[i][1] == riskCode) {
      fm.all("RiskCode").value = arrGrpRisk[i][1];
      fm.all("GrpPolNo").value = arrGrpRisk[i][0];

      if (arrGrpRisk[i][1] == top.mainRisk) {
        //top.mainPolNo = "";
        mainRiskPolNo ="";
      }

      findFlag = true;
      break;
    }
  }

  if (arrGrpRisk.length > 1) {
    fm.all("RiskCode").className = "code";
    fm.all("RiskCode").readOnly = false;
  }
  else {
    fm.all("RiskCode").onclick = "";
  }

  return findFlag;
}

/**
 * �������֤�����ȡ��������
 */
function grpGetBirthdayByIdno() {
  var id = fm.all("IDNo").value;

  if (fm.all("IDType").value == "0") {
    if (id.length == 15) {
      id = id.substring(6, 12);
      id = "19" + id;
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      fm.all("Birthday").value = id;
    }
    else if (id.length == 18) {
      id = id.substring(6, 14);
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      fm.all("Birthday").value = id;
    }
  }
}

/**
 * У�������հ�����ϵ
 */
function checkRiskRelation(tPolNo, cRiskCode) {
  // �����¸���Ͷ����
  if (mGrpFlag == "1") {
    var strSql = "select RiskCode from LCGrpPol where GrpProposalNo = '" + tPolNo
               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
    return true;
  }
  else {
    var strSql = "select RiskCode from LCPol where PolNo = '" + tPolNo
               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
  }

  return easyQueryVer3(strSql);
}

/**
 * �����ص�����ѡ�����
 */
function gotoInputPage() {
  // �����¸���Ͷ����
  if (mGrpFlag == "1") {
    //top.fraInterface.window.location = "../cardgrp/ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
    top.fraInterface.window.location = "../cardgrp/ProposalInput.jsp?LoadFlag=" + LoadFlag;
  }
  // ������ɨ���Ͷ����
  else if (typeof(prtNo)!="undefined" && typeof(type)=="undefined") {
    top.fraInterface.window.location = "../cardgrp/ProposalInput.jsp?prtNo=" + prtNo;
  }
  // ������ɨ���Ͷ����
  else {
    top.fraInterface.window.location = "../cardgrp/ProposalOnlyInput.jsp?type=noScan";
  }
}

function showSaleChnl() {
  showCodeList('SaleChnl',[this]);
}
<!--add by yaory-2005-7-11-->
function addRisk()
{
//alert(fm.RiskCode.value);
 parent.fraInterface.window.location.href="ProposalInput.jsp?LoadFlag=1&ContType=1&scantype=null&MissionID=00000000000000008139&riskcode="+fm.RiskCode.value;
 return;
}
function addAppRisk()
{
alert("ok");
}
<!--end add-->
/*********************************************************************
 *  ���ݲ�ͬ������,��ȡ��ͬ�Ĵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getRiskInput(cRiskCode, cFlag) {
  //alert(cFlag);
  //����18-����������
	var tPolNo = "";
  //alert("cFlag=="+cFlag);
	convertFlag(cFlag);
  //alert("mGrpFlag=="+mGrpFlag);

	var urlStr = "";
	// �״ν��뼯���¸���¼��
//var	rSql = "select risksortvalue from lmrisksort where risksorttype='00' and riskcode='"+cRiskCode+"'";
//var RiskInterface = easyExecSql(rSql);
//alert(RiskInterface+ ".jsp?riskcode="+ cRiskCode);
	if( mGrpFlag == "0" )		// ����Ͷ����
		urlStr = "../riskinput/Risk" + cRiskCode+ ".jsp?riskcode="+ cRiskCode;
	if( mGrpFlag == "1" )		// �����¸���Ͷ����
		urlStr = "../riskgrp/Risk" + cRiskCode+ ".jsp?riskcode="+ cRiskCode;
	
	if( mGrpFlag == "3" )		// ����Ͷ��������
		urlStr = "../riskinput/Risk" + cRiskCode+ ".jsp?riskcode="+ cRiskCode;
//alert(urlStr);
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//��ȡ���ֵĽ�������
	//showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800;resizable=1");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


    //���ղ�ͬ��LoadFlag���в�ͬ�Ĵ���
    //�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/
 // alert("LoadFlag=="+LoadFlag);
  if(LoadFlag=="1"){
  //alert(cRiskCode);
  ////У���ǲ������� add by yaory
//  strSql = "select subriskflag from lmriskapp where riskcode='"+cRiskCode+"'";
//    var mark = easyExecSql(strSql);
//    //alert(mark);

  ////end
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true"); //rollback by yaory
//    if(mark=='S'){
//    showDiv(divInputYaory2Button, "true");}
//    if(mark=='M'){
//    showDiv(divInputYaoryButton, "true");}
//   ///////////add by yaory for query how many records if 0 then button-ADDapp is unsee.
//   strSql = "select * from lmriskrela where riskcode='"+cRiskCode+"'";
//    var queryAppRisk = easyExecSql(strSql);
//    //alert(queryAppRisk);
//    if(queryAppRisk==null)
//    {
//    fm.riskbutton31.style.display='none';
//    fm.riskbutton32.style.display='none';
//    }

  }

	if (LoadFlag == "2"||LoadFlag == "18"){//yaory need modify
		var aGrpContNo=parent.VD.gVSwitch.getVar( "GrpContNo" );
		if(isSubRisk(cRiskCode)){
		  fm.all('MainPolNo').value=mainRiskPolNo;
		}
		strSql = "select PayIntv from LCGrpPol where RiskCode = '" + cRiskCode + "' and GrpContNo ='"+aGrpContNo+"'";
    var PayIntv = easyExecSql(strSql);
    //alert(PayIntv);
    try{
      fm.PayIntv.value=PayIntv;
    }
    catch(ex){
    	//alert("no");
    }
    showDiv(inputButton, "true");
//    showDiv(divInputContButton, "false");
    showDiv(divGrpContButton, "true");
    showDiv(inputQuest, "false");
    //alert();
    getContInput();//need modify yaory ��ȡ��ͬ��Ϣ proposalinit.jsp
    
	}


	if (LoadFlag == "3") {
	  fm.all("SaleChnl").readOnly = false;
    fm.all("SaleChnl").className = "code";
    fm.all("SaleChnl").ondblclick= showSaleChnl;
    showDiv(inputButton, "true");
    divApproveModifyContButton.style.display="";
	}

  if(LoadFlag=="4"){
    showDiv(inputQuest, "true");
  }
  if(LoadFlag=="5"){
    showDiv(inputQuest, "true");
  }

  if(LoadFlag=="6"){
    //showDiv(inputButton, "true");
    //showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="7"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
    fm.all("riskbutton2").style.display="none";
  }

  if(LoadFlag=="8"){
  	//alert("LoadFlag:"+LoadFlag);
  	//alert(parent.VD.gVSwitch.getVar( "ContNo" ));
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");    
    var aContNo=parent.VD.gVSwitch.getVar( "ContNo" );
		strSql = "select PolNo,appflag from LCPol where riskcode ='"+ cRiskCode +"' and Contno='"+ aContNo +"'";
    arrResult = easyExecSql(strSql);
    var appflag;
    //alert("appflag="+arrResult);
    if(arrResult != "null" && arrResult != null ){    	
       appflag = arrResult[0][1];
       nrPolNo = arrResult[0][0];
    }    
    if(appflag=="1"){
    	alert("����������Ч!");
    	inputButton.style.display = "none";
			modifyButton.style.display = "none";			
    }else if(appflag=="2"){
    	inputButton.style.display = "none";
			modifyButton.style.display = "";
    }else{
      inputButton.style.display = "";
			modifyButton.style.display = "none";
    }
    fm.all("riskbutton2").style.display="none";
  }

  if(LoadFlag=="9"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="10"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="13"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
    fm.all("riskbutton2").style.display = "none";
  }

  if(LoadFlag=="14"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
 }

  if(LoadFlag=="16"){
    //showDiv(inputButton, "true");
    //showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="23"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="25"){
    showDiv(inputButton, "true");
    showDiv(divUWContButton, "true");
  }

	if (LoadFlag == "99")
	{
    showDiv(inputButton, "false");
    showDiv(inputQuest, "false");
		showDiv(autoMoveButton, "true");
	}

  try {
    //��ʼ��������Ϣ
	  emptyForm();

    //�������󵥷ſ���������ֻ�������ƣ�by Minim at 2003-11-24
    fm.all("SaleChnl").readOnly = false;
    fm.all("SaleChnl").className = "code";
    fm.all("SaleChnl").ondblclick= showSaleChnl;
    //alert(PolTypeFlag);
    fm.all("PolTypeFlag").value=PolTypeFlag;
    //��ɨ���¼��
    if (typeof(type)!="undefined" && type=="noScan") {
//      fm.all("PrtNo").readOnly = false;
//      fm.all("PrtNo").className = "common";

      //ͨ���б�����������ҵ�����
      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
             + cRiskCode + "' and RiskVer = '2002'";
      var riskDescribe = easyExecSql(strSql);

//      if (riskDescribe == "M") {
//        top.mainPolNo = "";
//      }

    }
    if(scantype=="scan") {
      //fm.all("PrtNo").value = prtNo;
      fm.all("RiskCode").value=cRiskCode;
      var sql=" select riskname from lmriskapp where riskcode='"+cRiskCode+"'" ;
      var riskcodname = easyExecSql(sql);
      fm.all("RiskCodeName").value = riskcodname;
      setFocus();
    }
    getContInput();//yaory
    getContInputnew();//yaory
    //alert("ok");
  }
  catch(e){}

	//�������ֺ�ӡˢ����Ϣ
	fm.all("RiskCode").value = cRiskCode;
  //alert(fm.RiskCode.value);
	//�������ƶ���ӡˢ�ţ��Է����涯¼��
	//fm.all("PrtNo").focus();

  //��ʼ���������������
  try {  	
    prtNo = fm.all("PrtNo").value;
    //alert("prtNo=="+prtNo);
  	var riskType = prtNo.substring(2, 4);
  	if (riskType == "11") {
  		fm.all("SaleChnl").value = "02";
  	}
  	else if (riskType == "12") {
  		fm.all("SaleChnl").value = "01";
  	}
  	else if (riskType == "15") {
  		fm.all("SaleChnl").value = "03";
  	}
  	else if (riskType == "16") {
  	  fm.all("SaleChnl").value = "02";
  	  fm.all("SaleChnl").readOnly = false;
      fm.all("SaleChnl").className = "code";
      fm.all("SaleChnl").ondblclick= showSaleChnl;
    }
  }
  catch(e) {}
// alert(cRiskCode);
// if (!(typeof(top.type)!="undefined" && (top.type=="ChangePlan" || top.type=="SubChangePlan"))) {
//   //���Ƿ�ָ����Ч������¼��ʱʧЧ
//   fm.all("SpecifyValiDate").readOnly = true;
//   fm.all("SpecifyValiDate").className = "readOnly";
//   fm.all("SpecifyValiDate").ondblclick = "";
//   fm.all("SpecifyValiDate").onkeyup = "";
//   //fm.all("SpecifyValiDate").value = "N";
//   fm.all("SpecifyValiDate").tabIndex = -1;
// }
//alert(cRiskCode);
  //alert("mCurOperateFlag:"+mCurOperateFlag);
  if(mainRiskPolNo==""){
    mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
  }

  //alert("main 5:"+mCurOperateFlag);
  if( mCurOperateFlag == "1" ) {             // ¼��
		// �����¸���Ͷ����
		//alert("mGrpFlag:"+mGrpFlag);
		if( mGrpFlag == "1" )	{
			getGrpPolInfo(); //yaory                      // ���뼯�岿����Ϣ
			//getPayRuleInfo();  //hl

			//֧�ּ����¸��ˣ�¼�����֤�Ŵ�����������
			fm.all("IDNo").onblur = grpGetBirthdayByIdno;
			//��ʱ��ȥ��ȥ���������ť,�涯����ʱ����   hl
//			if(LoadFlag!="99")
//			inputQuest.style.display = "none";

			// ��ȡ�ü���������������Ϣ
			//alert("judging if the RiskCode input has been input in group info.");
			//if (!queryGrpPol(fm.all("PrtNo").value, cRiskCode))	{
			//  alert("�����ŵ�û��¼��������֣������¸��˲�����¼�룡");
			//  fm.all("RiskCode").value="";
			//  //alert("now go to the new location- ProposalGrpInput.jsp");
			//  top.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
			//  //alert("top.location has been altered");
			//  return false; //hezy
			//}
		}
		//if(initDealForSpecRisk(cRiskCode)==false)//���������ڳ�ʼ��ʱ�����⴦��-houzm���
		//{
			//alert("���֣�"+cRiskCode+"��ʼ��ʱ���⴦��ʧ�ܣ�");
			//return false;
		//}

    //alert(cRiskCode);

		//���������ڳ�ʼ��ʱ�����⴦����չ-guoxiang add at 2004-9-6 16:33
    if(initDealForSpecRiskEx(cRiskCode)==false)//yaory
		{
			alert("���֣�"+cRiskCode+"��ʼ��ʱ���⴦��ʧ�ܣ�");
			return false;
		}

		//alert("getRiskInput.isSubRisk begin...");
		//alert("cRiskCode:"+cRiskCode);
		try{
		  fm.all('SelPolNo').value=parent.VD.gVSwitch.getVar("PolNo");
		  //alert("kjlkdsjal"+fm.all('SelPolNo').value);
		  if (fm.all('SelPolNo').value=='false')
		  {
		  	fm.all('SelPolNo').value='';
		  	if(LoadFlag=="8")
		  	{
		  		fm.all('SelPolNo').value=nrPolNo;
		  	}
		  }
		  if(fm.all('SelPolNo').value!=''){ //��������
		    var tSql="select riskcode from lcpol where polno='"+fm.all('SelPolNo').value+"'";
		    var arr=easyExecSql(tSql);
		    if(arr[0]!=cRiskCode){
		      fm.all('SelPolNo').value='';
		    }
		  }
	  }
	  catch(ex) {}
		//alert("selPolNo:"+fm.all('SelPolNo').value);
		if( isSubRisk( cRiskCode ) == true&&fm.all('SelPolNo').value=="" ) {   // ����
		  //��������������գ�����ջ�������ձ�����
		  if (cFlag != "8" && cFlag != "2") {
			  //top.mainPolNo = ""; //hezy add
			  mainRiskPolNo = "";
			}
			//edit by yaory tPolNo = getMainRiskNo(cRiskCode);   //����¼�븽�յĴ���,�õ����ձ�������
			//alert("tPolNo:"+tPolNo);
//edit by yaory			if (!checkRiskRelation(tPolNo, cRiskCode)) {
//			  alert("�����հ�����ϵ������������պŲ��ܴ���������գ�");
//			  gotoInputPage();
//			  //top.mainPolNo = "";
//			  mainRiskPolNo = "";
//			  return false;
//			}
//-----------------------------------------------------------------------
         if(cRiskCode=='121301'||cRiskCode=='321601')//��ʼ������ĸ�����Ϣ--houzm���--�ɵ������Ϊһ������
         {
         	//	if(cRiskCode=='121301')
         	//	{
					//			if (!initPrivateRiskInfo121301(tPolNo))
					//			{
					//			  gotoInputPage();
					//			  return false;
					//			}
					//	}
					//	if(cRiskCode=='321601')
      		//	{
					//			if (!initPrivateRiskInfo321601(tPolNo))
					//			{
					//			  gotoInputPage();
					//			  return false;
					//			}
					//	}
         }
         else
         {
         		////��ʼ��������Ϣ
						//if (!initPrivateRiskInfo(tPolNo))
						//{
						//  gotoInputPage();
						//  return false;
						//}
         }

//			try	{}	catch(ex1) { alert( "��ʼ�����ֳ���" + ex1 );	}
		} // end of ����if

	  //alert("ManageCom=="+ManageCom);
	  
	  //tongmeng 2009-03-23 modify
	  //���ñ�����Ч��
	  //֧���ŵ��¸�����Ч�յ�ָ��.
	  var tCValidate = parent.VD.gVSwitch.getVar("CValiDate");
    var tContCvaliDate = parent.VD.gVSwitch.getVar("ContCValiDate");
    
  //  alert("tContCvaliDate:"+tContCvaliDate+":tCValidate"+tCValidate)
    if(tContCvaliDate!=null&&tContCvaliDate!='')
    {
    	try{fm.all('CValiDate').value=tContCvaliDate;}catch(ex){};
    }
    else
    {
    	try{fm.all('CValiDate').value=parent.VD.gVSwitch.getVar("CValiDate");}catch(ex){};
    }
		return false;

	} // end of ¼��if


	mCurOperateFlag = "";
	mGrpFlag = "";
	
	return;
}

/*********************************************************************
 *  �жϸ������Ƿ��Ǹ���,������ȷ���ȿ���������,�ֿ��������յĴ���
 *  ����  ��  ���ִ���
 *  ����ֵ��  ��
 *********************************************************************
 */
function isSubRisk(cRiskCode) {
  //alert(cRiskCode);
  if (cRiskCode=="")
  {
   return false;
  }
  var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);

	if(arrQueryResult[0] == "S")    //��Ҫת�ɴ�д
		return true;
	if(arrQueryResult[0] == "M")
		return false;

	if (arrQueryResult[0].toUpperCase() == "A")
	{
		if (confirm("�����ּȿ���������,�ֿ����Ǹ���!ѡ��ȷ����������¼��,ѡ��ȡ�����븽��¼��"))
			return false;
		else
			return true;
  }
	return false;
}

/*********************************************************************
 *  ����¼�븽�յĴ���,�õ����ձ�������
 *  ����  ��  ���ִ���
 *  ����ֵ��  ��
 *********************************************************************
 */
function getMainRiskNo(cRiskCode) {
	var urlStr = "../cardgrp/MainRiskNoInput.jsp";
	var tPolNo="";
  //alert("mainRiskPolNo:"+mainRiskPolNo);
  if (typeof(mainRiskPolNo)!="undefined" && mainRiskPolNo!="") {
    //tPolNo = top.mainPolNo;
    tPolNo = mainRiskPolNo;
  }
  else{
    //tPolNo = window.showModalDialog(urlStr,tPolNo,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	tPolNo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	tPolNo.focus();
    //top.mainPolNo = tPolNo;
    mainRiskPolNo = tPolNo;
  }

	return tPolNo;
}

/*********************************************************************
 *  ��ʼ��������Ϣ
 *  ����  ��  Ͷ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPrivateRiskInfo(cPolNo) {
	if(cPolNo=="") {
		alert("û�����ձ�����,���ܽ��и�����¼��!");
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
		return false
	}

	var arrLCPol = new Array();
	var arrQueryResult = null;
	// ��������Ϣ����
	var sql = "select * from lcpol where polno='" + cPolNo + "' "
			+ "and riskcode in "
			+ "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

	arrQueryResult = easyExecSql( sql , 1, 0);

	if (arrQueryResult == null)	{
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

		//top.mainPolNo = "";
		mainRiskPolNo = "";

		alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
		return false
	}

	arrLCPol = arrQueryResult[0];
	displayPol( arrLCPol );

	fm.all("MainPolNo").value = cPolNo;
	var tAR;

  	//Ͷ������Ϣ
  	if (arrLCPol[6]=="2") {     //����Ͷ������Ϣ
  	  arrQueryResult = null;
  	  //arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[28]+"'", 1, 0);
  	  arrQueryResult = easyExecSql("select * from lcgrpappnt where grpcontno='"+arrLCPol[0]+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
  	  tAR = arrQueryResult[0];
  	  displayPolAppntGrp(tAR);
  	} else {                     //����Ͷ������Ϣ
  	  arrQueryResult = null;
  	  arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
  	  tAR = arrQueryResult[0];
  	  displayPolAppnt(tAR);
  	}

	// ��������Ϣ����
	if (arrLCPol[21] == arrLCPol[28]) {
	  fm.all("SamePersonFlag").checked = true;
		parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.fm.all("CustomerNo").value = arrLCPol[21];
	}
	//else {
		arrQueryResult = null;
		arrQueryResult = easyExecSql("select * from lcinsured where contno='"+arrLCPol[2]+"'"+" and insuredno='"+arrLCPol[21]+"'", 1, 0);
		tAR = arrQueryResult[0];
		displayPolInsured(tAR);
	//}
	return true;
}

/*********************************************************************
 *  У��Ͷ����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function verifyProposal() {
  var passVerify = true;

  //��ȫ����������
  if (LoadFlag == "8") {
    var newCValidate = fm.all('CValiDate').value;
    newCValidate = mSwitch.getVar('CValiDate'); 
    fm.all('CValiDate').value = newCValidate;
    /*
    if (oldCValidate.substring(4) != newCValidate.substring(4)) {
      alert("ָ������Ч���ڱ�����������Ч���ڵ������Ӧ��");
      return false;
    }*/

    //���������������ղ�����ô����
    //if (oldCValidate == newCValidate) {
    //  alert("�������������չ��򣬲���ָ��Ϊ������Ч����");
    //  return false;
    //}

    if (!confirm("��������Ч����Ϊ:(" + newCValidate + ")")) {
      return false;
    }
  }

  if(fm.AppntRelationToInsured.value=="00"){
    if(fm.AppntCustomerNo.value!= fm.CustomerNo.value){
      alert("Ͷ�����뱻���˹�ϵΪ���ˣ����ͻ��Ų�һ��");
      return false;
    }
  }
   if(needVerifyRiskcode()==true)
   {
		//if(verifyInput() == false) passVerify = false;

		BnfGrid.delBlankLine();

		if(BnfGrid.checkValue("BnfGrid") == false) passVerify = false;

		 //У�鵥֤�Ƿ񷢷Ÿ�ҵ��Ա
		 if (!verifyPrtNo(fm.all("PrtNo").value)) passVerify = false;
	}
	try {
	  var strChkIdNo = "";

		  //��������Ա�У�����֤��
		  if (fm.all("AppntIDType").value == "0")
		    strChkIdNo = chkIdNo(fm.all("AppntIDNo").value, fm.all("AppntBirthday").value, fm.all("AppntSex").value);
		  if (fm.all("IDType").value == "0")
		    strChkIdNo = chkIdNo(fm.all("IDNo").value, fm.all("Birthday").value, fm.all("Sex").value);

		  if (strChkIdNo != "") {
		    alert(strChkIdNo);
		    passVerify = false;


	  }

	  //У��ְҵ��ְҵ����
//	  var arrCode = new Array();
//	  arrCode = verifyCode("ְҵ�����֣�", fm.all("AppntWorkType").value, "code:OccupationCode", 1);
//	  if (arrCode!=true && fm.all("AppntOccupationCode").value!=arrCode[0]) {
//	    alert("Ͷ����ְҵ��ְҵ���벻ƥ�䣡");
//	    passVerify = false;
//	  }
//	  arrCode = verifyCode("ְҵ�����֣�", fm.all("WorkType").value, "code:OccupationCode", 1);
//	  if (arrCode!=true && fm.all("OccupationCode").value!=arrCode[0]) {
//	    alert("������ְҵ��ְҵ���벻ƥ�䣡");
//	    passVerify = false;
//	  }

	  //У���������
	  var i;
	  var sumLiveBnf = new Array();
	  var sumDeadBnf = new Array();
	  for (i=0; i<BnfGrid.mulLineCount; i++)
	  {
	    if (BnfGrid.getRowColData(i, 7)==null||BnfGrid.getRowColData(i, 7)=='')
	    {
	        BnfGrid.setRowColData(i, 7,"1");
	    }
	    if (BnfGrid.getRowColData(i, 6)==null||BnfGrid.getRowColData(i, 6)=='')
	    {
	        BnfGrid.setRowColData(i, 6,"1");
	    }
	    if (BnfGrid.getRowColData(i, 1) == "0")
	    {
	      if (typeof(sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 6))]) == "undefined")
	        sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 6))] = 0;
	      sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 6))] = sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 6))] + parseFloat(BnfGrid.getRowColData(i, 7));
	    }
	    else if (BnfGrid.getRowColData(i, 1) == "1")
	    {
	      if (typeof(sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 6))]) == "undefined")
	        sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 6))] = 0;
	      sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 6))] = sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 6))] + parseFloat(BnfGrid.getRowColData(i, 7));
	    }

	  }
   //alert(fm.RiskCode.value);
   var tempcode=fm.RiskCode.value;//add by yaory ����������ֶ�Ϳ��Դ���1
   
	  for (i=0; i<sumLiveBnf.length; i++)
	  {
  	    if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
  	    {
  	        alert("��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i]+ " ������100%�������ύ��");
  	       
  	        passVerify = false;
  	        return false; 
  	    }
  	    else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
  	    {
  	        alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i] + " ��С��100%");
  	        
  	        passVerify = false;
  	        return false; 
  	    }
	  }
	//yaory-200509

	  for (i=0; i<sumDeadBnf.length; i++)
	  {
  	    if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1)
  	    {
  	      alert("��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ������100%�������ύ��");
  	      passVerify = false;
  	      return false; 
  	    }
  	    else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1)
  	    {
  	        alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ��С��100%");
  	        passVerify = false;
  	        return false; 
  	    }
	  }


	  if (trim(fm.BankCode.value)=="0101")
	  {
        if (trim(fm.BankAccNo.value).length!=19 || !isInteger(trim(fm.BankAccNo.value)))
        {
            alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��\n����ͻ���д������һ�����������");
            passVerify = false;
        }
      }

    //У��ͻ��Ƿ�����
    if (fm.AppntCustomerNo.value!="" && isDeath(fm.AppntCustomerNo.value)) {
      alert("Ͷ�����Ѿ�������");
      passVerify = false;
    }

    if (fm.CustomerNo.value!="" && isDeath(fm.CustomerNo.value)) {
      alert("�������Ѿ�������");
      passVerify = false;
    }
	}
	catch(e) {}

	if (!passVerify) {
	  if (!confirm("Ͷ����¼������д����Ƿ�������棿")) return false;
	  else return true;
	}
}

//У��ͻ��Ƿ�����
function isDeath(CustomerNo) {
  var strSql = "select DeathDate from LDPerson where CustomerNo='" + CustomerNo + "'";
  var arrResult = easyExecSql(strSql);

  if (arrResult == ""||arrResult == null) return false;
  else return true;
}
function dospecialrisk()
{
	//alert(fm.CustomerNo.value);
	//alert(fm.RiskCode.value);
	//alert(prtNo);
//var code=fm.RiskCode.value;
//��ѯ������˳���
var	rSql = "select sequenceno from lcinsured where contno='"+prtNo+"' and insuredno='"+fm.CustomerNo.value+"'";
var seque = easyExecSql(rSql);
//�ж������ǲ���00150000
rSql="select riskcode from lcpol where contno='"+prtNo+"'  and polno=mainpolno";
var code= easyExecSql(rSql);
if(seque!=null && seque=="1" && code=="00150000")
{
	return 1;
}else{
   return 0;
}
}

/*
 *У�鲻�ǰ��������������Ƿ�¼���˱��ѻ��Ǳ��� ���򱣴��ᱨ��ָ�����
 *
 */
/* 
 function checkRiskSale(){
     var tRiskCode = fm.all('RiskCode').value;
     //alert(tRiskCode);
     var tMultSql = "select max(amntflag) from lmduty where dutycode in (select dutycode from lmriskduty where riskcode = '"+tRiskCode+"')";
     var tAmntFlag = easyExecSql(tMultSql);
     //alert("tAmntFlag:"+tAmntFlag);
     if(tAmntFlag =="2"){}else{
        //1.���������� 2.���������� 3.����������
        var tDutySql ="select 1 from lmriskapp where risktype6 in ('6','1') and riskcode = '"+tRiskCode+"'";
        var tCheck = easyExecSql(tDutySql);
        //alert("tCheck:"+tCheck);
        if(tCheck =="1"){}else{
           //�����ֲ��ǰ��������� ���ұ�����Ǳ��Ѳ���ͨ��mulline¼��� ��ʱ��У�鱣����Ƿ�Ϊ��
           var tPrem = fm.Prem.value;
           var tAmnt = fm.Amnt.value;
           var tFloatRate = fm.FloatRate.value;
           var tCalRule = fm.CalRule.value;
           //alert("tCalRule:"+tCalRule);
           if(tCalRule=="0"||tCalRule==""){
             //������ ����Ѳ���ͬʱΪ�� ���ʱ������1
             if(tPrem==""&&tAmnt==""){
                alert("�����ʱ�����Ѳ���ͬʱΪ�գ�");
                return false;
              }
             if(tFloatRate!=""&&tFloatRate !="1"){
                alert("�����ʵķ��ʱ���Ϊ1��");
                fm.FloatRate.value = "1";
                return false;
             }
            }else if (tCalRule =="1"){
              //Լ������  ����=����*��������
              if(tPrem==""||tAmnt==""||tFloatRate==""){
                alert("Լ��������Ҫ¼�뱣�ѡ������������,���ұ���=����*�������� ��");
                return false;
              }else {
               var tCalPrem=tAmnt*tFloatRate;
               if(tCalPrem!=tPrem){
                 alert("Լ������¼��ı���"+tPrem+"���������ı���"+tCalPrem+"����,������¼�룡");
                 return false;
               }
              }
             }else if (tCalRule=="2"){
               //�������ۿ� ֻ¼�뱣���������
               if(tPrem==""){
                  if(tAmnt==""||tFloatRate==""){
                    alert("���������ۿ���Ҫ¼�뱣��͸������ʣ�");
                    return false;
                  }
                }else{
                    alert("�������ۿ۲���¼�뱣��!");
                    return false;
                 }
               }else if(tCalRule=="3"){
                 //�ܹ�¼�뱣����ѡ�����ʱ�Ŷ����ּ������������У��
                 if(tFloatRate==""){
   	 		       if(tAmnt==""||tPrem==""){
   	 		          alert("Լ�����ѱ�����Ҫ¼�뱣�ѡ����");
   	 		          return false;
   	 		       }
   	 		      }else{
   			        alert("Լ�����ѱ����¼�븡�����ʣ�");
   	 		       return false;
   			      }
               }else {
                  var tSql = "select 1 from ldcode where codetype='calrule' and code ='"+tCalRule+"'";
                  var tFlag = easyExecSql(tSql);
                  if(tFlag=="1"){}else{
                    alert("��¼��ļ�����򲻺Ϸ�����û��������");
                    return false;
                  }
               }
           }
        }
        return true;
     }
 */

/*********************************************************************
 *  �������Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm() {
   //if(checkRiskSale()==false) return false;
	 //if(dospecialrisk()==1)//add by yaory ����00150000
	    //{
	    	//alert("����00150000�ĸ�����ֻ�ܸ��ӵ��ڶ������ˣ�");
	    	//return;
	 //   }       
  	var index = BnfGrid.mulLineCount;
  	var i;
  	for(i=0;i<index;i++)    
  	{
	    if (BnfGrid.getRowColData(i, 9) == "A") {
	  	  if(ContType!="2")
	  	  {
          
          BnfGrid.setRowColData(i, 2, fm.all("AppntName").value);
          BnfGrid.setRowColData(i, 3, fm.all("AppntIDType").value);
          
          BnfGrid.setRowColData(i, 4, fm.all("AppntIDNo").value);
          BnfGrid.setRowColData(i, 5,parent.VD.gVSwitch.getVar( "RelationToAppnt"));

          BnfGrid.setRowColData(i, 8, fm.all("AppntHomeAddress").value);
          //hl
          //BnfGrid.setRowColData(i, 9, fm.all("AppntNo").value);

	  	  }
	  	  else
	  	  {
	  	    alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")
          
          BnfGrid.setRowColData(i, 2, "");
          BnfGrid.setRowColData(i, 3, "");
          BnfGrid.setRowColData(i, 4, "");
          BnfGrid.setRowColData(i, 5, "");
          BnfGrid.setRowColData(i, 8, "");
	  	  }
	  	}
	  	else if (BnfGrid.getRowColData(i,9)== "I") {
	  	 
        BnfGrid.setRowColData(i, 2, fm.all("Name").value);
        BnfGrid.setRowColData(i, 3, fm.all("IDType").value);
        BnfGrid.setRowColData(i, 4, fm.all("IDNo").value);
        BnfGrid.setRowColData(i, 5, "00");
        //BnfGrid.setRowColData(index-1, 8, fm.all("AppntHomeAddress").value);
        //hl
        BnfGrid.setRowColData(i, 8, fm.all("HomeAddress").value);
        //BnfGrid.setRowColData(i, 9, fm.all("InsuredNo").value);
	//alert("fm.all("InsuredNo")"+fm.all("InsuredNo").value);
	  	}
	}
	
  if(!chkDutyNo()){                  //У��CheckBox��ֻ����ͬʱѡ��һ��������
																			 //create by malong 2005-7-13
	   return false;
	}
	
	//if(!chkMult() || !checkMult())      //�жϷ����Ƿ�Ϊ�ջ����� chkMult�����ڶ���������,checkMult�����ڵ���������,
	//																		//create by malong 2005-7-8
  //{
  //	 //	alert("here!");
  //	 return false;
  //}
  //alert("no");
  //alert(fm.RiskCode.value);
//  if(fm.RiskCode.value!='00332000')
//  {
// if(!chkDuty()){                  //У��280��ͬ����ѡ������
//																			 //create by zhangyang 2005-7-29
//	   return false;
//	}
//}
	//alert("ok");
	if(sign != 0)
	{
	   alert("�벻Ҫ�ظ����!");
	   return;
	}

	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

  var verifyGrade = "1";


  //������������������⴦������
  //modify by malong 2005-7-8
 try {

 	 if(specDealByRisk()==false)
 	  return;

 	}
  	 catch(e){}
 if (fm.RiskCode.value =='221703')
   	{	
		//alert(fm.all('Amnt').value/fm.all('Mult').value);
		
   		//if(fm.all('Amnt').value/fm.all('Mult').value == 30  || fm.all('Amnt').value/fm.all('Mult').value == 50)
   		if(Math.round(fm.all('Amnt').value/fm.all('Mult').value) == fm.all('Amnt').value/fm.all('Mult').value  )
   		{
   			//if(fm.all('Amnt').value/fm.all('Mult').value == 30  || fm.all('Amnt').value/fm.all('Mult').value == 50)
   			//{
			if( !confirm("��¼��ı������ܱ���,���ݱ���Ϊ"+fm.all('Amnt').value/fm.all('Mult').value+ "Ԫ,�Ƿ񱣴�!" ))
			return false;
   		   // }else{
   		   //  	alert("���ݱ����30Ԫ��50Ԫ,����!");
		   //	return false;
   		   // }
   		    
   		}
   		else{
   			alert("�����30Ԫ��50Ԫ��������,����!");
			return false;
   		}
  	 } 
  //����������ڣ�����գ������֤��ȡ
  //try {checkBirthday(); } catch(e){}

  	// У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
  //try { verifySamePerson(); } catch(e) {}

      	// У��¼������
    if( verifyProposal() == false ) return;

	if (trim(fm.all('ProposalNo').value) != "") {
	  alert("��Ͷ�������Ѿ����ڣ��������ٴ������������½���¼����棡");
	  return false;
	}

	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if (LoadFlag=="1") {
		mAction = "INSERTPERSON";
	}
	else {
		mAction = "INSERTGROUP";
	}
  try{
  	fm.CustomerNo.value=tInsuredNo;
  	
  }catch(Ex){}
  	
	fm.all( 'fmAction' ).value = mAction;
	fm.action="../cardgrp/ProposalSave.jsp"

	//Ϊ��ȫ���ӣ�add by Minim
	if (LoadFlag=="7" || LoadFlag=="8") {
	  fm.action="../cardgrp/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType+"&EdorTypeCal="+EdorTypeCal;
	  //fm.all("BQFlag").value=BQFlag;
	}

	//Ϊ���������������ӣ�add by Minim  yaory ����������ôʹ�� 
	if (LoadFlag=="9") {
	  fm.action="../cardgrp/ProposalSave.jsp?BQFlag=4&MasterPolNo=" + parent.VD.gVSwitch.getVar('MasterPolNo');
	  //alert(fm.action);return;
	}
	sign = 1;
	//alert("payIntv:"+fm.all('PayIntv').value);
  //beforeSubmit();
  //alert(LoadFlag);
  //return;
  //if(LoadFlag=="18")
  //{
  //	fm.BQFlag.value="4";
  //}
  if (LoadFlag=="18") {//�������������¼
  	
  }
	fm.submit(); //�ύ
	sign = 0;
}

/**
 * ǿ�ƽ������
 */
function unLockTable() {
  if (fm.PrtNo.value == "") {
    alert("��Ҫ��дӡˢ�ţ�");
    return;
  }

  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + fm.PrtNo.value + "&CreatePos=�б�¼��&PolState=1002&Action=DELETE";
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
   //alert(mCurOperateFlag);
	try {
		if(showInfo!=null)
			showInfo.close();
		}
	catch(e)
  {

	}
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
        //add by yaory
//        fm.riskbutton31.disabled='';
//        fm.riskbutton32.disabled='';
        //alert("loadflag:"+LoadFlag);
    if (fm.ContType.value == "1" && cflag=="1")
    {
  		  if (confirm("����ɹ���\nҪ�����ӡˢ�ŵ��������ø�����Ա�ܲ�����")) {
  		    unLockTable();
  		  }
	  }
	  else {
		  try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
		  //alert("loadflag:"+LoadFlag);
		  if(LoadFlag == '3'){
		    inputQuestButton.disabled = false;
                    //////add by yaory
//                    fm.riskbutton31.disabled=true;
//                    fm.riskbutton32.disabled=true;
		  }
	    content = "����ɹ���";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	  //fm.all('AppendRiskCode').focus();
        //////add by yaory
//        fm.riskbutton31.focus();
//        fm.riskbutton32.focus();
  	  if (mAction=="CONFIRM") {
        window.location.href("./ContPolInput.jsp");
		  }
    }
     if(LoadFlag == 1)
      {
      	top.close();
     	}
		//��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
		//try { if (top.mainPolNo == "") top.mainPolNo = fm.all("ProposalNo").value } catch(e) {}
		//try { if (mainRiskPolNo == "") mainRiskPolNo = fm.all("ProposalNo").value } catch(e) {alert("err");}
	}

	//�б��ƻ������������ֹ�ĺ�������
	if (mAction=="DELETE") {
	  if (typeof(top.type)!="undefined" && top.type=="SubChangePlan") {
	    var tProposalNo = fm.all('ProposalNo').value;
    	var tPrtNo = fm.all('PrtNo').value;
    	var tRiskCode = fm.all('RiskCode').value;

  		parent.fraTitle.window.location = "./ChangePlanSubWithdraw.jsp?polNo=" + tProposalNo + "&prtNo=" + tPrtNo + "&riskCode=" + tRiskCode;
  	}

    returnparent();
	}
	mAction = "";
}

function afterSubmita( FlagStr, content )
{
   //alert(mCurOperateFlag);
	try {
		if(showInfo!=null)
			showInfo.close();
		}
	catch(e)
  {

	}
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
        //add by yaory
//        fm.riskbutton31.disabled='';
//        fm.riskbutton32.disabled='';
        //alert("loadflag:"+LoadFlag);
    if (fm.ContType.value == "1" && cflag=="1")
    {
  		  if (confirm("����ɹ���\nҪ�����ӡˢ�ŵ��������ø�����Ա�ܲ�����")) {
  		    unLockTable();
  		  }
	  }
	  else {
		  try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
		  //alert("loadflag:"+LoadFlag);
		  if(LoadFlag == '3'){
		    inputQuestButton.disabled = false;
                    //////add by yaory
//                    fm.riskbutton31.disabled=true;
//                    fm.riskbutton32.disabled=true;
		  }
	    content = "����ɹ���";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	  //fm.all('AppendRiskCode').focus();
        //////add by yaory
//        fm.riskbutton31.focus();
//        fm.riskbutton32.focus();
  	  if (mAction=="CONFIRM") {
        window.location.href("./ContPolInput.jsp");
		  }
    }

		//��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
		//try { if (top.mainPolNo == "") top.mainPolNo = fm.all("ProposalNo").value } catch(e) {}
		//try { if (mainRiskPolNo == "") mainRiskPolNo = fm.all("ProposalNo").value } catch(e) {alert("err");}
	}

	//�б��ƻ������������ֹ�ĺ�������
	if (mAction=="DELETE") {
	  if (typeof(top.type)!="undefined" && top.type=="SubChangePlan") {
	    var tProposalNo = fm.all('ProposalNo').value;
    	var tPrtNo = fm.all('PrtNo').value;
    	var tRiskCode = fm.all('RiskCode').value;

  		parent.fraTitle.window.location = "./ChangePlanSubWithdraw.jsp?polNo=" + tProposalNo + "&prtNo=" + tPrtNo + "&riskCode=" + tRiskCode;
  	}

    returnparent();
	}
	mAction = "";
}

/*********************************************************************
 *  "����"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetForm()
{
	try	{
		//initForm();
		var tRiskCode = fm.RiskCode.value;
		var prtNo = fm.PrtNo.value;

		emptyForm();

		fm.RiskCode.value = tRiskCode;
		fm.PrtNo.value = prtNo;

		if (LoadFlag == "2"||LoadFlag == "18") {
		  getGrpPolInfo();
		}
	}
	catch(re)	{
		alert("��ProposalInput.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

/*********************************************************************
 *  "ȡ��"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelForm()
{
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	//if( cDebug == "1" )
		//parent.fraMain.rows = "0,0,50,82,*";
	//else
		//parent.fraMain.rows = "0,0,80,72,*";
}

/*********************************************************************
 *  Click�¼������������ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addClick()
{
	//����������Ӧ�Ĵ���
	//showDiv( operateButton, "false" );
	//showDiv( inputButton, "true" );
}

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick() {
	if( mOperate == 0 )	{
		mOperate = 1;

		cGrpPolNo = fm.all( 'GrpPolNo' ).value;
		cContNo = fm.all( 'ContNo' ).value;
		window.open("./ProposalQueryMain.jsp?GrpPolNo=" + cGrpPolNo + "&ContNo=" + cContNo);
	}
}

/*********************************************************************
 *  Click�¼�����������޸ġ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function updateClick()
{
    //if(checkRiskSale()==false) return false;
	//alert("ok");
	var index = BnfGrid.mulLineCount;
  	var i;
  	for(i=0;i<index;i++)    
  	{
	    if (BnfGrid.getRowColData(i, 9) == "A") 
	    {
	  	  if(ContType!="2")
	  	  {
          
		          BnfGrid.setRowColData(i, 2, fm.all("AppntName").value);
		          BnfGrid.setRowColData(i, 3, fm.all("AppntIDType").value);
          
		          BnfGrid.setRowColData(i, 4, fm.all("AppntIDNo").value);
		          BnfGrid.setRowColData(i, 5,parent.VD.gVSwitch.getVar( "RelationToAppnt"));
		
		          BnfGrid.setRowColData(i, 8, fm.all("AppntHomeAddress").value);
		          //hl
		          //BnfGrid.setRowColData(i, 9, fm.all("AppntNo").value);

	  	  }
	  	  else
	  	  {
	  	    	  alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")
          
		          BnfGrid.setRowColData(i, 2, "");
		          BnfGrid.setRowColData(i, 3, "");
		          BnfGrid.setRowColData(i, 4, "");
		          BnfGrid.setRowColData(i, 5, "");
		          BnfGrid.setRowColData(i, 8, "");
	  	  }
	    }
	   else if (BnfGrid.getRowColData(i,9)== "I") 
	    {
	  	 
		          BnfGrid.setRowColData(i, 2, fm.all("Name").value);
		          BnfGrid.setRowColData(i, 3, fm.all("IDType").value);
		          BnfGrid.setRowColData(i, 4, fm.all("IDNo").value);
		          BnfGrid.setRowColData(i, 5, "00");       
                 	  BnfGrid.setRowColData(i, 8, fm.all("HomeAddress").value);
        		  
        		  //BnfGrid.setRowColData(i, 9, fm.all("InsuredNo").value);
			  
	     }
	  	
        }
   if(!chkDutyNo()){                  //У��CheckBox��ֻ����ͬʱѡ��һ��������
																			 //create by malong 2005-7-13
	   return false;
	}       
	var tProposalNo = "";
	tProposalNo = fm.all( 'ProposalNo' ).value;

	if( tProposalNo == null || tProposalNo == "" )
		alert( "������Ͷ������ѯ�������ٽ����޸�!" );
	else {
		// У��¼������
		if (fm.all('DivLCInsured').style.display == "none") {
      for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
    	  if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
    	    fm.elements[elementsNum].verify = "";
    	  }
    	}
    }

		if( verifyProposal() == false ) return;
		  	 if(chkMult()==false)  return;
		// У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
    try { verifySamePerson(); } catch(e) {}

		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )	{
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			if (LoadFlag=="1"||LoadFlag=="3") {
				mAction = "UPDATEPERSON";
			}
			else {
				mAction = "UPDATEGROUP";
			}

			fm.all( 'fmAction' ).value = mAction;
      //alert(mAction);
			//�б��ƻ����(����Ͷ����״̬���䣺����״̬���˱�״̬)
			if (typeof(window.ChangePlanSub) == "object") fm.all('fmAction').value = "ChangePlan" + fm.all('fmAction').value;
			//�޸ĸ�������(����Ͷ����״̬���䣺����״̬���˱�״̬,���ñȳб��ƻ������һ����޸ĸ������ʣ�ΪȨ�޿���)
			if(LoadFlag=="10") fm.all('fmAction').value = "ChangePlan" + fm.all('fmAction').value;
			//yaoryif(LoadFlag=="3") fm.all('fmAction').value = "Modify" + fm.all('fmAction').value;
			//inputQuestButton.disabled = false;
			if (LoadFlag=="7" || LoadFlag=="8") {
	  		fm.action="../cardgrp/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType+"&EdorTypeCal="+EdorTypeCal;
	  		//fm.all("BQFlag").value=BQFlag;
			}
			fm.CustomerNo.value=tInsuredNo;
			
			fm.submit(); //�ύ
		}

		try {
		  if (typeof(top.opener.modifyClick) == "object") top.opener.initQuery();
		}
		catch(e) {
		}
	}
}

/*********************************************************************
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick() {
	var tProposalNo = fm.all('ProposalNo').value;

	if(tProposalNo==null || tProposalNo=="") {
		alert( "������Ͷ������ѯ�������ٽ���ɾ��!" );
	}
	else {
		var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )	{
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			fm.all( 'fmAction' ).value = mAction;
			fm.submit(); //�ύ
		}
	}
}

/*********************************************************************
 *  Click�¼����������ѡ�����Ρ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function chooseDuty()
{
	cRiskCode = fm.RiskCode.value;
	cRiskVersion = fm.RiskVersion.value

	if( cRiskCode == "" || cRiskVersion == "" )
	{
		alert( "��������¼�����ֺ����ְ汾�����޸ĸ�Ͷ�����������" );
		return false
	}

	showInfo = window.open("./ChooseDutyInput.jsp?RiskCode="+cRiskCode+"&RiskVersion="+cRiskVersion);
	return true
}

/*********************************************************************
 *  Click�¼������������ѯ������Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDuty()
{
	//����������Ӧ�Ĵ���
	cPolNo = fm.ProposalNo.value;
	if( cPolNo == "" )
	{
		alert( "�������ȱ���Ͷ�������ܲ鿴��Ͷ�����������" );
		return false
	}

	var showStr = "���ڲ�ѯ���ݣ������Ժ�......";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	//showModalDialog( "./ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
	var urlStr1 = "./ProposalDuty.jsp?PolNo="+cPolNo;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (urlStr1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
	showInfo.close();
}

/*********************************************************************
 *  Click�¼���������������ݽ�����Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalNo.value;
	var prtNo = fm.PrtNo.value;

	if( cPolNo == "" )
	{
		alert( "�������ȱ���Ͷ�������ܽ����ݽ�����Ϣ���֡�" );
		return false
	}

	showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL&prtNo=" + prtNo );
}

/*********************************************************************
 *  Click�¼�����˫����Ͷ���˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppnt()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonMain.html" );
	}
}

/*********************************************************************
 *  Click�¼�����˫���������˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsured()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonMain.html" );
	}
}

/*********************************************************************
 *  Click�¼�����˫�������������˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubInsured( span, arrPara )
{
	if( mOperate == 0 )
	{
		mOperate = 4;
		spanObj = span;
		showInfo = window.open( "../sys/LDPersonMain.html" );
	}
}

/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPol(cArr)
{
	try
	{
	/*
	    try { fm.all('PrtNo').value = cArr[6]; } catch(ex) { };
	    try { fm.all('ManageCom').value = cArr[12]; } catch(ex) { };
	    try { fm.all('SaleChnl').value = cArr[15]; } catch(ex) { };
	    try { fm.all('AgentCom').value = cArr[13]; } catch(ex) { };
	    try { fm.all('AgentType').value = cArr[14]; } catch(ex) { };
	    try { fm.all('AgentCode').value = cArr[87]; } catch(ex) { };
	    try { fm.all('AgentGroup').value = cArr[88]; } catch(ex) { };
	    //try { fm.all('Handler').value = cArr[82]; } catch(ex) { };
	    //try { fm.all('AgentCode1').value = cArr[89]; } catch(ex) { };
	    try { fm.all('Remark').value = cArr[90]; } catch(ex) { };

	    try { fm.all('ContNo').value = cArr[1]; } catch(ex) { };

	    //try { fm.all('Amnt').value = cArr[43]; } catch(ex) { };
	    try { fm.all('CValiDate').value = cArr[29]; } catch(ex) { };
	    try { fm.all('PolApplyDate').value = cArr[128]; } catch(ex) { };
	    try { fm.all('HealthCheckFlag').value = cArr[72]; } catch(ex) { };
	    try { fm.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
	    try { fm.all('PayLocation').value = cArr[59]; } catch(ex) { };
	    try { fm.all('BankCode').value = cArr[102]; } catch(ex) { };
	    try { fm.all('BankAccNo').value = cArr[103]; } catch(ex) { };
	    try { fm.all('AccName').value = cArr[118]; } catch(ex) { };
	    try { fm.all('LiveGetMode').value = cArr[98]; } catch(ex) { };
	    try { fm.all('BonusGetMode').value = cArr[100]; } catch(ex) { };
	    try { fm.all('AutoPayFlag').value = cArr[65]; } catch(ex) { };
	    try { fm.all('InterestDifFlag').value = cArr[66]; } catch(ex) { };

	    try { fm.all('InsuYear').value = cArr[111]; } catch(ex) { };
	    try { fm.all('InsuYearFlag').value = cArr[110]; } catch(ex) { };
	    try { fm.all('PolTypeFlag').value = cArr[69]; } catch(ex) { };
	    try { fm.all('InsuredPeoples').value = cArr[24]; } catch(ex) { };
	    try { fm.all('InsuredAppAge').value = cArr[22]; } catch(ex) { };


	    try { fm.all('StandbyFlag1').value = cArr[78]; } catch(ex) { };
	    try { fm.all('StandbyFlag2').value = cArr[79]; } catch(ex) { };
	    try { fm.all('StandbyFlag3').value = cArr[80]; } catch(ex) { };
	*/
	    try { fm.all('PrtNo').value = cArr[5]; } catch(ex) { };
	    try { fm.all('ManageCom').value = cArr[13]; } catch(ex) { };
	    try { fm.all('SaleChnl').value = cArr[19]; } catch(ex) { };
	    try { fm.all('AgentCom').value = cArr[14]; } catch(ex) { };
	    try { fm.all('AgentType').value = cArr[15]; } catch(ex) { };
	    try { fm.all('AgentCode').value = cArr[16]; } catch(ex) { };
	    try { fm.all('AgentGroup').value = cArr[17]; } catch(ex) { };
	    try { fm.all('Handler').value = cArr[20]; } catch(ex) { };
	    try { fm.all('AgentCode1').value = cArr[18]; } catch(ex) { };
	    try { fm.all('Remark').value = cArr[92]; } catch(ex) { };

	    try { fm.all('ContNo').value = cArr[2]; } catch(ex) { };

	    try { fm.all('CValiDate').value = cArr[30]; } catch(ex) { };
	    try { fm.all('PolApplyDate').value = cArr[101]; } catch(ex) { };
	    try { fm.all('HealthCheckFlag').value = cArr[81]; } catch(ex) { };
	    //try { fm.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
	    try { fm.all('PayLocation').value = cArr[51]; } catch(ex) { };
	    //try { fm.all('BankCode').value = cArr[102]; } catch(ex) { };
	    //try { fm.all('BankAccNo').value = cArr[103]; } catch(ex) { };
	    //try { fm.all('AccName').value = cArr[118]; } catch(ex) { };
	    try { fm.all('LiveGetMode').value = cArr[86]; } catch(ex) { };
	    try { fm.all('BonusGetMode').value = cArr[88]; } catch(ex) { };
	    try { fm.all('AutoPayFlag').value = cArr[77]; } catch(ex) { };
	    try { fm.all('InterestDifFlag').value = cArr[78]; } catch(ex) { };

	    try { fm.all('InsuYear').value = cArr[45]; } catch(ex) { };
	    try { fm.all('InsuYearFlag').value = cArr[44]; } catch(ex) { };
	    try { fm.all('PolTypeFlag').value = cArr[7]; } catch(ex) { };
	    try { fm.all('InsuredPeoples').value = cArr[26]; } catch(ex) { };
	    try { fm.all('InsuredAppAge').value = cArr[25]; } catch(ex) { };


	    try { fm.all('StandbyFlag1').value = cArr[104]; } catch(ex) { };
	    try { fm.all('StandbyFlag2').value = cArr[105]; } catch(ex) { };
	    try { fm.all('StandbyFlag3').value = cArr[106]; } catch(ex) { };

	} catch(ex) {
	  alert("displayPol err:" + ex + "\ndata is:" + cArr);
	}
}

/*********************************************************************
 *  �ѱ����е�Ͷ������Ϣ��ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppnt(cArr)
{
	// ��LCAppntInd��ȡ����
	try { fm.all('AppntCustomerNo').value = cArr[1]; } catch(ex) { };
	try { fm.all('AppntRelationToInsured').value = cArr[4]; } catch(ex) { };
	try { fm.all('AppntPassword').value = cArr[5]; } catch(ex) { };
	try { fm.all('AppntName').value = cArr[6]; } catch(ex) { };
	try { fm.all('AppntSex').value = cArr[7]; } catch(ex) { };
	try { fm.all('AppntBirthday').value = cArr[8]; } catch(ex) { };
	try { fm.all('AppntNativePlace').value = cArr[9]; } catch(ex) { };
	try { fm.all('AppntNationality').value = cArr[10]; } catch(ex) { };
	try { fm.all('AppntMarriage').value = cArr[11]; } catch(ex) { };
	try { fm.all('AppntMarriageDate').value = cArr[12]; } catch(ex) { };
	try { fm.all('AppntOccupationType').value = cArr[13]; } catch(ex) { };
	try { fm.all('AppntStartWorkDate').value = cArr[14]; } catch(ex) { };
	try { fm.all('AppntSalary').value = cArr[15]; } catch(ex) { };
	try { fm.all('AppntHealth').value = cArr[16]; } catch(ex) { };
	try { fm.all('AppntStature').value = cArr[17]; } catch(ex) { };
	try { fm.all('AppntAvoirdupois').value = cArr[18]; } catch(ex) { };
	try { fm.all('AppntCreditGrade').value = cArr[19]; } catch(ex) { };
	try { fm.all('AppntIDType').value = cArr[20]; } catch(ex) { };
	try { fm.all('AppntProterty').value = cArr[21]; } catch(ex) { };
	try { fm.all('AppntIDNo').value = cArr[22]; } catch(ex) { };
	try { fm.all('AppntOthIDType').value = cArr[23]; } catch(ex) { };
	try { fm.all('AppntOthIDNo').value = cArr[24]; } catch(ex) { };
	try { fm.all('AppntICNo').value = cArr[25]; } catch(ex) { };
	try { fm.all('AppntHomeAddressCode').value = cArr[26]; } catch(ex) { };
	try { fm.all('AppntHomeAddress').value = cArr[27]; } catch(ex) { };
	try { fm.all('AppntPostalAddress').value = cArr[28]; } catch(ex) { };
	try { fm.all('AppntZipCode').value = cArr[29]; } catch(ex) { };
	try { fm.all('AppntPhone').value = cArr[30]; } catch(ex) { };
	try { fm.all('AppntBP').value = cArr[31]; } catch(ex) { };
	try { fm.all('AppntMobile').value = cArr[32]; } catch(ex) { };
	try { fm.all('AppntEMail').value = cArr[33]; } catch(ex) { };
	try { fm.all('AppntJoinCompanyDate').value = cArr[34]; } catch(ex) { };
	try { fm.all('AppntPosition').value = cArr[35]; } catch(ex) { };
	try { fm.all('AppntGrpNo').value = cArr[36]; } catch(ex) { };
	try { fm.all('AppntGrpName').value = cArr[37]; } catch(ex) { };
	try { fm.all('AppntGrpPhone').value = cArr[38]; } catch(ex) { };
	try { fm.all('AppntGrpAddressCode').value = cArr[39]; } catch(ex) { };
	try { fm.all('AppntGrpAddress').value = cArr[40]; } catch(ex) { };
	try { fm.all('AppntDeathDate').value = cArr[41]; } catch(ex) { };
	try { fm.all('AppntRemark').value = cArr[42]; } catch(ex) { };
	try { fm.all('AppntState').value = cArr[43]; } catch(ex) { };
	try { fm.all('AppntWorkType').value = cArr[46]; } catch(ex) { };
	try { fm.all('AppntPluralityType').value = cArr[47]; } catch(ex) { };
	try { fm.all('AppntOccupationCode').value = cArr[48]; } catch(ex) { };
	try { fm.all('AppntDegree').value = cArr[49]; } catch(ex) { };
	try { fm.all('AppntGrpZipCode').value = cArr[50]; } catch(ex) { };
	try { fm.all('AppntSmokeFlag').value = cArr[51]; } catch(ex) { };
	try { fm.all('AppntRgtAddress').value = cArr[52]; } catch(ex) { };
	try { fm.all('AppntHomeZipCode').value = cArr[53]; } catch(ex) { };
	try { fm.all('AppntPhone2').value = cArr[54]; } catch(ex) { };

}

/*********************************************************************
 *  �ѱ����е�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppntGrp( cArr )
{

	/*
	// ��LCAppntGrp��ȡ����
	try { fm.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
	try { fm.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
	try { fm.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
	try { fm.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
	try { fm.all('AppntPassword').value = cArr[4]; } catch(ex) { };
	try { fm.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
	try { fm.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
	try { fm.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
	try { fm.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
	try { fm.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
	try { fm.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
	try { fm.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
	try { fm.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
	try { fm.all('AppntAsset').value = cArr[13]; } catch(ex) { };
	try { fm.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
	try { fm.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
	try { fm.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
	try { fm.all('AppntComAera').value = cArr[17]; } catch(ex) { };
	try { fm.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
	try { fm.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
	try { fm.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
	try { fm.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
	try { fm.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
	try { fm.all('AppntFax1').value = cArr[23]; } catch(ex) { };
	try { fm.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
	try { fm.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
	try { fm.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
	try { fm.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
	try { fm.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
	try { fm.all('AppntFax2').value = cArr[29]; } catch(ex) { };
	try { fm.all('AppntFax').value = cArr[30]; } catch(ex) { };
	try { fm.all('AppntPhone').value = cArr[31]; } catch(ex) { };
	try { fm.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
	try { fm.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
	try { fm.all('AppntEMail').value = cArr[34]; } catch(ex) { };
	try { fm.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
	try { fm.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
	try { fm.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
	try { fm.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
	try { fm.all('AppntState').value = cArr[39]; } catch(ex) { };
	try { fm.all('AppntRemark').value = cArr[40]; } catch(ex) { };
	try { fm.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
	try { fm.all('AppntOperator').value = cArr[42]; } catch(ex) { };
	try { fm.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
	try { fm.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
	try { fm.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
	try { fm.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
	try { fm.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
	try { fm.all('AppntPK').value = cArr[48]; } catch(ex) { };
	try { fm.all('AppntfDate').value = cArr[49]; } catch(ex) { };
	try { fm.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
	*/
	try { fm.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
	try { fm.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
	try { fm.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
	try { fm.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
	try { fm.all('AppntPassword').value = cArr[4]; } catch(ex) { };
	try { fm.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
	try { fm.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
	try { fm.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
	try { fm.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
	try { fm.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
	try { fm.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
	try { fm.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
	try { fm.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
	try { fm.all('AppntAsset').value = cArr[13]; } catch(ex) { };
	try { fm.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
	try { fm.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
	try { fm.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
	try { fm.all('AppntComAera').value = cArr[17]; } catch(ex) { };
	try { fm.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
	try { fm.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
	try { fm.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
	try { fm.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
	try { fm.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
	try { fm.all('AppntFax1').value = cArr[23]; } catch(ex) { };
	try { fm.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
	try { fm.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
	try { fm.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
	try { fm.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
	try { fm.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
	try { fm.all('AppntFax2').value = cArr[29]; } catch(ex) { };
	try { fm.all('AppntFax').value = cArr[30]; } catch(ex) { };
	try { fm.all('AppntPhone').value = cArr[31]; } catch(ex) { };
	try { fm.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
	try { fm.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
	try { fm.all('AppntEMail').value = cArr[34]; } catch(ex) { };
	try { fm.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
	try { fm.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
	try { fm.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
	try { fm.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
	try { fm.all('AppntState').value = cArr[39]; } catch(ex) { };
	try { fm.all('AppntRemark').value = cArr[40]; } catch(ex) { };
	try { fm.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
	try { fm.all('AppntOperator').value = cArr[42]; } catch(ex) { };
	try { fm.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
	try { fm.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
	try { fm.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
	try { fm.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
	try { fm.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
	try { fm.all('AppntPK').value = cArr[48]; } catch(ex) { };
	try { fm.all('AppntfDate').value = cArr[49]; } catch(ex) { };
	try { fm.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
}

/*********************************************************************
 *  �ѱ����еı�����������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolInsured(cArr)
{
	// ��LCInsured��ȡ����
	try { fm.all('ContNo').value=cArr[1];} catch(ex){};
	//alert("contno mm:"+fm.all('ContNo').value);
	try { fm.all('CustomerNo').value = cArr[2]; } catch(ex) { };
	try { fm.all('SequenceNo').value = cArr[11]; } catch(ex) { };
	//try { fm.all('InsuredGrade').value = cArr[3]; } catch(ex) { };
	try { fm.all('RelationToInsured').value = cArr[8]; } catch(ex) { };
	//try { fm.all('Password').value = cArr[5]; } catch(ex) { };
	try { fm.all('Name').value = cArr[12]; } catch(ex) { };
	try { fm.all('Sex').value = cArr[13]; } catch(ex) { };
	try { fm.all('Birthday').value = cArr[14]; } catch(ex) { };
	try { fm.all('NativePlace').value = cArr[17]; } catch(ex) { };
	try { fm.all('Nationality').value = cArr[18]; } catch(ex) { };
	try { fm.all('Marriage').value = cArr[20]; } catch(ex) { };
	try { fm.all('MarriageDate').value = cArr[21]; } catch(ex) { };
	try { fm.all('OccupationType').value = cArr[34]; } catch(ex) { };
	try { fm.all('StartWorkDate').value = cArr[31]; } catch(ex) { };
	try { fm.all('Salary').value = cArr[33]; } catch(ex) { };
	try { fm.all('Health').value = cArr[22]; } catch(ex) { };
	try { fm.all('Stature').value = cArr[23]; } catch(ex) { };
	try { fm.all('Avoirdupois').value = cArr[24]; } catch(ex) { };
	try { fm.all('CreditGrade').value = cArr[26]; } catch(ex) { };
	try { fm.all('IDType').value = cArr[15]; } catch(ex) { };
	//try { fm.all('Proterty').value = cArr[21]; } catch(ex) { };
	try { fm.all('IDNo').value = cArr[16]; } catch(ex) { };
	//try { fm.all('OthIDType').value = cArr[23]; } catch(ex) { };
	//try { fm.all('OthIDNo').value = cArr[24]; } catch(ex) { };
	//try { fm.all('ICNo').value = cArr[25]; } catch(ex) { };
	//try { fm.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
	//try { fm.all('HomeAddress').value = cArr[27]; } catch(ex) { };
	//try { fm.all('PostalAddress').value = cArr[28]; } catch(ex) { };
	//try { fm.all('ZipCode').value = cArr[29]; } catch(ex) { };
	//try { fm.all('Phone').value = cArr[30]; } catch(ex) { };
	//try { fm.all('BP').value = cArr[31]; } catch(ex) { };
	//try { fm.all('Mobile').value = cArr[32]; } catch(ex) { };
	//try { fm.all('EMail').value = cArr[33]; } catch(ex) { };
	//try { fm.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
	//try { fm.all('Position').value = cArr[35]; } catch(ex) { };
	//try { fm.all('GrpNo').value = cArr[4]; } catch(ex) { };
	//try { fm.all('GrpName').value = cArr[37]; } catch(ex) { };
	//try { fm.all('GrpPhone').value = cArr[38]; } catch(ex) { };
	//try { fm.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
	//try { fm.all('GrpAddress').value = cArr[40]; } catch(ex) { };
	//try { fm.all('DeathDate').value = cArr[41]; } catch(ex) { };
	//try { fm.all('State').value = cArr[43]; } catch(ex) { };
	try { fm.all('WorkType').value = cArr[36]; } catch(ex) { };
	try { fm.all('PluralityType').value = cArr[37]; } catch(ex) { };
	try { fm.all('OccupationCode').value = cArr[35]; } catch(ex) { };
	try { fm.all('Degree').value = cArr[25]; } catch(ex) { };
	//try { fm.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
	try { fm.all('SmokeFlag').value = cArr[38]; } catch(ex) { };
	try { fm.all('RgtAddress').value = cArr[19]; } catch(ex) { };
	//try { fm.all('HomeZipCode').value = cArr[53]; } catch(ex) { };
	//try { fm.all('Phone2').value = cArr[54]; } catch(ex) { };
	return;

}

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�����������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displaySubInsured()
{
	fm.all( spanObj ).all( 'SubInsuredGrid1' ).value = arrResult[0][0];
	fm.all( spanObj ).all( 'SubInsuredGrid2' ).value = arrResult[0][2];
	fm.all( spanObj ).all( 'SubInsuredGrid3' ).value = arrResult[0][3];
	fm.all( spanObj ).all( 'SubInsuredGrid4' ).value = arrResult[0][4];
}

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( mOperate == 1 )	{           // ��ѯ������ϸ
			//alert("abc");
			var tPolNo = arrResult[0][0];

			// ��ѯ������ϸ
			queryPolDetail( tPolNo );
		}

		if( mOperate == 2 ) {		// Ͷ������Ϣ
			arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			   displayAppnt(arrResult[0]);
			}

	  }
		if( mOperate == 3 )	{		// ����������Ϣ
			arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽����������Ϣ");
			} else {
			   displayInsured(arrResult[0]);
			}

	  }
		if( mOperate == 4 )	{		// ������������Ϣ
			displaySubInsured(arrResult[0]);
	  }
	}
	mOperate = 0;		// �ָ���̬

	emptyUndefined();
}

/*********************************************************************
 *  ���ݲ�ѯ���ص���Ϣ��ѯͶ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPolDetail( cPolNo )
{
	emptyForm();
	//alert("ccc");
	//var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
	parent.fraTitle.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
}


/*********************************************************************
 *  ���ݲ�ѯ���ص���Ϣ��ѯ���ֵı��ռƻ���ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryRiskPlan( tProposalGrpContNo,tRiskCode,tContPlanCode,tMainRiskCode )
{

	emptyForm();
	//var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//alert("./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
	//																		+ tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode);
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;

	parent.fraSubmit.window.location = "./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
																			+ tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode+"&MainRiskCode="+tMainRiskCode;
}
/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";
}

function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//*************************************************************
//�����˿ͻ��Ų�ѯ��Ť�¼�
function queryInsuredNo() {
  if (fm.all("CustomerNo").value == "") {
    showInsured1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  }  else {
      arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + fm.all("CustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("δ�鵽����������Ϣ");
      displayInsured(new Array());
      emptyUndefined();
    } else {

      displayInsured(arrResult[0]);
    }
  }
}

//*************************************************************
//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() {
  if (fm.all("AppntCustomerNo").value == "" && LoadFlag == "1") {
    showAppnt1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  } else {
    arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + fm.all("AppntCustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("δ�鵽Ͷ������Ϣ");
      displayAppnt(new Array());
      emptyUndefined();
    } else {
      displayAppnt(arrResult[0]);
    }
  }
}

//*************************************************************
//Ͷ�����뱻������ͬѡ����¼�
function isSamePerson() {
  //��Ӧδѡͬһ�ˣ��ִ򹳵����
  if (fm.AppntRelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) {
    fm.all('DivLCInsured').style.display = "";
    fm.SamePersonFlag.checked = false;
    alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
  }
  //��Ӧ��ͬһ�ˣ��ִ򹳵����
  else if (fm.SamePersonFlag.checked == true) {
    fm.all('DivLCInsured').style.display = "none";
  }
  //��Ӧ��ѡͬһ�˵����
  else if (fm.SamePersonFlag.checked == false) {
    fm.all('DivLCInsured').style.display = "";
  }

  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
	      if (fm.all('DivLCInsured').style.display == "none") {
	        fm.all(insuredName).value = fm.elements[elementsNum].value;
	      }
	      else {
	        fm.all(insuredName).value = "";
	      }
	    }
	    catch (ex) {}
	  }
	}

}

//*************************************************************
//����ʱУ��Ͷ�����뱻������ͬѡ����¼�
function verifySamePerson() {
  if (fm.SamePersonFlag.checked == true) {
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
  	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
  	    try {
  	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
  	      if (fm.all('DivLCInsured').style.display == "none") {
  	        fm.all(insuredName).value = fm.elements[elementsNum].value;
  	      }
  	      else {
  	        fm.all(insuredName).value = "";
  	      }
  	    }
  	    catch (ex) {}
  	  }
	  }
  }
  else if (fm.SamePersonFlag.checked == false) {

  }

}


/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
	// ��LDPerson��ȡ����
try{fm.all('AppntCustomerNo').value= arrResult[0][0]; }catch(ex){};
try{fm.all('AppntName').value= arrResult[0][1]; }catch(ex){};
try{fm.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
try{fm.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
try{fm.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
try{fm.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
try{fm.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
try{fm.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
try{fm.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
try{fm.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};
try{fm.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
try{fm.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
try{fm.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
try{fm.all('AppntStature').value= arrResult[0][13];}catch(ex){};
try{fm.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
try{fm.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
try{fm.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
try{fm.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
try{fm.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
try{fm.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
try{fm.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
try{fm.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
try{fm.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
try{fm.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
try{fm.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
try{fm.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
try{fm.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
try{fm.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
try{fm.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
try{fm.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
try{fm.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
try{fm.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
try{fm.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
try{fm.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
try{fm.all('AppntState').value= arrResult[0][34];}catch(ex){};
try{fm.all('AppntOperator').value= arrResult[0][35];}catch(ex){};
try{fm.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};
try{fm.all('AppntMakeTime').value= arrResult[0][37];}catch(ex){};
try{fm.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};
try{fm.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};
try{fm.all('AppntGrpName').value= arrResult[0][40];}catch(ex){};
try{fm.all('AppntHomeAddress').value= arrResult[0][41];}catch(ex){};
try{fm.all('AppntHomeZipCode').value= arrResult[0][42];}catch(ex){};
try{fm.all('AppntPhone').value= arrResult[0][43];}catch(ex){};
try{fm.all('AppntPhone2').value= arrResult[0][44];}catch(ex){};
}

/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAddress1()
{
try {fm.all('GrpNo').value= arrResult[0][0]; } catch(ex) { };
try {fm.all('GrpCustomerNo').value= arrResult[0][0]; } catch(ex) { };
try {fm.all('AddressNo').value= arrResult[0][1]; } catch(ex) { };
try {fm.all('AppGrpAddress').value= arrResult[0][2]; } catch(ex) { };
try {fm.all('AppGrpZipCode').value= arrResult[0][3]; } catch(ex) { };
try {fm.all('LinkMan1').value= arrResult[0][4]; } catch(ex) { };
try {fm.all('Department1').value= arrResult[0][5]; } catch(ex) { };
try {fm.all('HeadShip1').value= arrResult[0][6]; } catch(ex) { };
try {fm.all('GrpPhone1').value= arrResult[0][7]; } catch(ex) { };
try {fm.all('E_Mail1').value= arrResult[0][8]; } catch(ex) { };
try {fm.all('Fax1').value= arrResult[0][9]; } catch(ex) { };
try {fm.all('LinkMan2').value= arrResult[0][10]; } catch(ex) { };
try {fm.all('Department2').value= arrResult[0][11]; } catch(ex) { };
try {fm.all('HeadShip2').value= arrResult[0][12]; } catch(ex) { };
try {fm.all('GrpPhone2').value= arrResult[0][13]; } catch(ex) { };
try {fm.all('E_Mail2').value= arrResult[0][14]; } catch(ex) { };
try {fm.all('Fax2').value= arrResult[0][15]; } catch(ex) { };
try {fm.all('Operator').value= arrResult[0][16]; } catch(ex) { };
try {fm.all('MakeDate').value= arrResult[0][17]; } catch(ex) { };
try {fm.all('MakeTime').value= arrResult[0][18]; } catch(ex) { };
try {fm.all('ModifyDate').value= arrResult[0][19]; } catch(ex) { };
try {fm.all('ModifyTime').value= arrResult[0][20]; } catch(ex) { };
//������ldgrp��
try {fm.all('BusinessType').value= arrResult[0][22];} catch(ex) { };
try {fm.all('GrpNature').value= arrResult[0][23]; } catch(ex) { };
try {fm.all('Peoples').value= arrResult[0][24]; } catch(ex) { };
try {fm.all('RgtMoney').value= arrResult[0][25]; } catch(ex) { };
try {fm.all('Asset').value= arrResult[0][26]; } catch(ex) { };
try {fm.all('NetProfitRate').value= arrResult[0][27];} catch(ex) { };
try {fm.all('MainBussiness').value= arrResult[0][28];} catch(ex) { };
try {fm.all('Corporation').value= arrResult[0][29];  } catch(ex) { };
try {fm.all('ComAera').value= arrResult[0][30]; } catch(ex) { };
try {fm.all('Fax').value= arrResult[0][31]; } catch(ex) { };
try {fm.all('Phone').value= arrResult[0][32]; } catch(ex) { };
try {fm.all('FoundDate').value= arrResult[0][33]; } catch(ex) { };
try {fm.all('AppGrpNo').value= arrResult[0][34]; } catch(ex) { };
try {fm.all('AppGrpName').value= arrResult[0][35]; } catch(ex) { };
}
function displayAppntGrp( cArr )
{
	// ��LDGrp��ȡ����
	try { fm.all('AppGrpNo').value = cArr[0]; } catch(ex) { };
	try { fm.all('Password').value = cArr[1]; } catch(ex) { };
	try { fm.all('AppGrpName').value = cArr[2]; } catch(ex) { };
	try { fm.all('GrpAddressCode').value = cArr[3]; } catch(ex) { };
	try { fm.all('AppGrpAddress').value = cArr[4]; } catch(ex) { };
	try { fm.all('AppGrpZipCode').value = cArr[5]; } catch(ex) { };
	try { fm.all('BusinessType').value = cArr[6]; } catch(ex) { };
	try { fm.all('GrpNature').value = cArr[7]; } catch(ex) { };
	try { fm.all('Peoples').value = cArr[8]; } catch(ex) { };
	try { fm.all('RgtMoney').value = cArr[9]; } catch(ex) { };
	try { fm.all('Asset').value = cArr[10]; } catch(ex) { };
	try { fm.all('NetProfitRate').value = cArr[11]; } catch(ex) { };
	try { fm.all('MainBussiness').value = cArr[12]; } catch(ex) { };
	try { fm.all('Corporation').value = cArr[13]; } catch(ex) { };
	try { fm.all('ComAera').value = cArr[14]; } catch(ex) { };
	try { fm.all('LinkMan1').value = cArr[15]; } catch(ex) { };
	try { fm.all('Department1').value = cArr[16]; } catch(ex) { };
	try { fm.all('HeadShip1').value = cArr[17]; } catch(ex) { };
	try { fm.all('Phone1').value = cArr[18]; } catch(ex) { };
	try { fm.all('E_Mail1').value = cArr[19]; } catch(ex) { };
	try { fm.all('Fax1').value = cArr[20]; } catch(ex) { };
	try { fm.all('LinkMan2').value = cArr[21]; } catch(ex) { };
	try { fm.all('Department2').value = cArr[22]; } catch(ex) { };
	try { fm.all('HeadShip2').value = cArr[23]; } catch(ex) { };
	try { fm.all('Phone2').value = cArr[24]; } catch(ex) { };
	try { fm.all('E_Mail2').value = cArr[25]; } catch(ex) { };
	try { fm.all('Fax2').value = cArr[26]; } catch(ex) { };
	try { fm.all('Fax').value = cArr[27]; } catch(ex) { };
	try { fm.all('Phone').value = cArr[28]; } catch(ex) { };
	try { fm.all('GetFlag').value = cArr[29]; } catch(ex) { };
	try { fm.all('Satrap').value = cArr[30]; } catch(ex) { };
	try { fm.all('EMail').value = cArr[31]; } catch(ex) { };
	try { fm.all('FoundDate').value = cArr[32]; } catch(ex) { };
	try { fm.all('BankAccNo').value = cArr[33]; } catch(ex) { };
	try { fm.all('BankCode').value = cArr[34]; } catch(ex) { };
	try { fm.all('GrpGroupNo').value = cArr[35]; } catch(ex) { };
	try { fm.all('State').value = cArr[36]; } catch(ex) { };
}

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayInsured()
{
	// ��LDPerson��ȡ����
    try{fm.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{fm.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{fm.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{fm.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{fm.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{fm.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    try{fm.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{fm.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{fm.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{fm.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{fm.all('AddressNo').value=arrResult[0][10];}catch(ex){};
    try{fm.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{fm.all('Name').value=arrResult[0][12];}catch(ex){};
    try{fm.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{fm.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{fm.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{fm.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{fm.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{fm.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{fm.all('RgtAddress').value=arrResult[0][19];}catch(ex){};
    try{fm.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{fm.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{fm.all('Health').value=arrResult[0][22];}catch(ex){};
    try{fm.all('Stature').value=arrResult[0][23];}catch(ex){};
    try{fm.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{fm.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{fm.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{fm.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{fm.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{fm.all('AccName').value=arrResult[0][29];}catch(ex){};
    try{fm.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{fm.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{fm.all('Position').value=arrResult[0][32];}catch(ex){};
    try{fm.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{fm.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{fm.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{fm.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{fm.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{fm.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{fm.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{fm.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{fm.all('InsuredStat').value=arrResult[0][41];}catch(ex){};
    try{fm.all('MakeDate').value=arrResult[0][42];}catch(ex){};
    try{fm.all('MakeTime').value=arrResult[0][43];}catch(ex){};
    try{fm.all('ModifyDate').value=arrResult[0][44];}catch(ex){};
    try{fm.all('ModifyTime').value=arrResult[0][45];}catch(ex){};
    try{fm.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{fm.all('HomeAddress').value= arrResult[0][47];}catch(ex){};
    try{fm.all('HomeZipCode').value= arrResult[0][48];}catch(ex){};
    try{fm.all('Phone').value= arrResult[0][49];}catch(ex){};
    try{fm.all('Phone2').value= arrResult[0][50];}catch(ex){};
    //alert("joindate:"+fm.all('JoinCompanyDate').value);
    //alert("grpcontno:"+fm.all('GrpContNo').value);
}

//*********************************************************************
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}

//*********************************************************************
function showInsured1()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}

function isSamePersonQuery() {
  fm.SamePersonFlag.checked = true;
  //divSamePerson.style.display = "none";
  DivLCInsured.style.display = "none";
}

//�����¼��
function QuestInput()
{ 
	cContNo = fm.all("ContNo").value;  //��������
	//alert("cContNo:"+cContNo)
		if(LoadFlag=="2"||LoadFlag=="4"){
			if(mSwitch.getVar( "ProposalGrpContNo" )==""){
			  alert("���޼����ͬͶ�����ţ����ȱ���!");
		        }
		        else{
			window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
	                }
	        }
	        else{
	                if(cContNo == ""){
		           alert("���޺�ͬͶ�����ţ����ȱ���!");
	                 }
	                 else
	                {
		        window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1");
	                }
	        }
}
function QuestQuery()
{

   cContNo = fm.all("ContNo").value;  //��������

   if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13"){
	if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
	{
  		alert("����ѡ��һ����������Ͷ����!");
  		return ;
        }
        else{
	        window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
        }
   }
   else{
        if(cContNo == ""){
	       alert("���޺�ͬͶ�����ţ����ȱ���!");
	 }
	else{
               window.open("../uwgrp/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1");
        }
   }
}
//��ʾͶ��������
function showAppntAge() {
  var age = calAge(fm.all("AppntBirthday").value);
  var today = new Date();

  fm.all("AppntBirthday").title = "Ͷ���˵����� " + today.toLocaleString()
                                + " \n������Ϊ��" + age + " ��!";
}

//��ʾ����������
function showAge() {
  var age = calAge(fm.all("Birthday").value);
  var today = new Date();

  fm.all("Birthday").title = "�����˵����� " + today.toLocaleString()
                           + " \n������Ϊ��" + age + " ��!";
}

//����Ͷ���˳������ڣ�����գ������֤���У�������֤ȡ��ȡ�������ؿո�;
function checkBirthday()
{
	try{
		  var strBrithday = "";
		  if(trim(fm.all("AppntBirthday").value)==""||fm.all("AppntBirthday").value==null)
		  {
		  	if (trim(fm.all("AppntIDType").value) == "0")
		  	 {
		  	   strBrithday=	getBirthdatByIdNo(fm.all("AppntIDNo").value);
		  	   if(strBrithday=="") passVerify=false;

	           fm.all("AppntBirthday").value= strBrithday;
		  	 }
	      }
	 }
	 catch(e)
	 {

	 }
}

//У��¼��������Ƿ���ҪУ�飬�����Ҫ����true,���򷵻�false
function needVerifyRiskcode()
{

  try {
  	   var riskcode=fm.all("RiskCode").value;

       var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyRiskcode'";
       var tResult = easyExecSql(tSql, 1, 1, 1);
       var strRiskcode = tResult[0][0];
       var strValue=strRiskcode.split("/");
       var i=0;
	   while(i<strValue.length)
	   {
	   	if(riskcode==strValue[i])
	   	{
           return false;
	   	}
	   	i++;
	   }
  	 }
  	catch(e)
  	 {}

  	return true;


}


/*********************************************************************
 *  ��ʼ������ĸ�����Ϣ-121301���������ֵĳ�ʼ����һ��
 *  ����  ��  Ͷ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPrivateRiskInfo121301(cPolNo) {
	if(cPolNo=="") {
		alert("û�����ձ�����,���ܽ��и�����¼��!");
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
		return false
	}

	var arrLCPol = new Array();
	var arrQueryResult = null;
	// ��������Ϣ����
	var sql = "select * from lcpol where polno='" + cPolNo + "' "
			+ "and riskcode in "
			+ "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

	arrQueryResult = easyExecSql( sql , 1, 0);

	if (arrQueryResult == null)	{
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

		//top.mainPolNo = "";
		mainRiskPolNo = "";

		alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
		return false
	}

	arrLCPol = arrQueryResult[0];
	displayPol( arrLCPol );
	displayPolSpec( arrLCPol );//��ʼ������Ҫ��ı�����Ϣ

	fm.all("MainPolNo").value = cPolNo;
	var tAR;

    //����Ͷ������Ϣ
  	  arrQueryResult = null;
  	  arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
  	  tAR = arrQueryResult[0];
  	  displayPolAppnt(tAR);
  	  try { fm.all('AppntRelationToInsured').value = '00'; } catch(ex) { };
	  try { fm.all("SamePersonFlag").checked = true; } catch(ex) { };
	  try {isSamePerson();} catch(ex) { };
	  try { fm.all("SamePersonFlag").disabled=true} catch(ex) { };


	// ��������Ϣ����
	//	arrQueryResult = null;
  	// 	arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
  	// 	tAR = arrQueryResult[0];
	//	displayPolInsuredSpec(tAR);


	return true;
}



/*********************************************************************
 *  �ѱ��������е�������ʾ�������������Ϣ��ʾ����-121301,
 *  ����  ��  ��������Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolSpec(cArr)
{
	try
	{

	    try { fm.all('PayEndYear').value = cArr[109]; } catch(ex) { };
	    try { fm.all('PayEndYearFlag').value = cArr[108]; } catch(ex) { };
	    try { fm.all('PayIntv').value = cArr[57]; } catch(ex) { };
	    try { fm.all('Amnt').value = cArr[39]; } catch(ex) { };	    //���յı��Ѽ����յı���

	} catch(ex) {
	  alert("displayPolSpec err:" + ex + "\ndata is:" + cArr);
	}
}



/*********************************************************************
 *  ��ʼ������ĸ�����Ϣ-321601���������ֵĳ�ʼ����һ��
 *  ����  ��  Ͷ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPrivateRiskInfo321601(cPolNo) {
	if(cPolNo=="") {
		alert("û�����ձ�����,���ܽ��и�����¼��!");
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
		return false
	}

	var arrLCPol = new Array();
	var arrQueryResult = null;
	// ��������Ϣ����
	var sql = "select * from lcpol where polno='" + cPolNo + "' "
			+ "and riskcode in "
			+ "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

	arrQueryResult = easyExecSql( sql , 1, 0);

	if (arrQueryResult == null)	{
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

		//top.mainPolNo = "";
		fm.all('mainRiskPolNo').value = "";

		alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
		return false
	}

	arrLCPol = arrQueryResult[0];
	displayPol( arrLCPol );

	//��ʼ������Ҫ��ı�����Ϣ--//���յı��Ѽ����յı���(ȡ���ձ��Ѻ�500000֮��Сֵ)
    try
    {
    	 if(arrLCPol[39]<500000)
    	   fm.all('Amnt').value = arrLCPol[39];
    	 else
    	   fm.all('Amnt').value = 500000;
    }
     catch(ex) { alert(ex);}


	fm.all("MainPolNo").value = cPolNo;
	var tAR;

  	//Ͷ������Ϣ
  	if (arrLCPol[28]=="2") {     //����Ͷ������Ϣ
  	  arrQueryResult = null;
  	  arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[26]+"'", 1, 0);
  	  tAR = arrQueryResult[0];
  	  displayPolAppntGrp(tAR);
  	} else {                     //����Ͷ������Ϣ
  	  arrQueryResult = null;
  	  arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
  	  tAR = arrQueryResult[0];
  	  displayPolAppnt(tAR);
  	}

	// ��������Ϣ����
	if (arrLCPol[18] == arrLCPol[26]) {
	  fm.all("SamePersonFlag").checked = true;
		parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.fm.all("CustomerNo").value = arrLCPol[18];
	}
	//else {
		arrQueryResult = null;
		arrQueryResult = easyExecSql("select * from lcinsured where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[18]+"'", 1, 0);
		tAR = arrQueryResult[0];
		displayPolInsured(tAR);
	//}

	return true;
}


/*********************************************************************
 *  �������ִ������������е�Ͷ����������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolInsuredSpec(cArr)
{
		// ��LCAppntInd��ȡ����
	try { fm.all('CustomerNo').value = cArr[1]; } catch(ex) { };
	try { fm.all('Password').value = cArr[5]; } catch(ex) { };
	try { fm.all('Name').value = cArr[6]; } catch(ex) { };
	try { fm.all('Sex').value = cArr[7]; } catch(ex) { };
	try { fm.all('Birthday').value = cArr[8]; } catch(ex) { };
	try { fm.all('NativePlace').value = cArr[9]; } catch(ex) { };
	try { fm.all('Nationality').value = cArr[10]; } catch(ex) { };
	try { fm.all('Marriage').value = cArr[11]; } catch(ex) { };
	try { fm.all('MarriageDate').value = cArr[12]; } catch(ex) { };
	try { fm.all('OccupationType').value = cArr[13]; } catch(ex) { };
	try { fm.all('StartWorkDate').value = cArr[14]; } catch(ex) { };
	try { fm.all('Salary').value = cArr[15]; } catch(ex) { };
	try { fm.all('Health').value = cArr[16]; } catch(ex) { };
	try { fm.all('Stature').value = cArr[17]; } catch(ex) { };
	try { fm.all('Avoirdupois').value = cArr[18]; } catch(ex) { };
	try { fm.all('CreditGrade').value = cArr[19]; } catch(ex) { };
	try { fm.all('IDType').value = cArr[20]; } catch(ex) { };
	try { fm.all('Proterty').value = cArr[21]; } catch(ex) { };
	try { fm.all('IDNo').value = cArr[22]; } catch(ex) { };
	try { fm.all('OthIDType').value = cArr[23]; } catch(ex) { };
	try { fm.all('OthIDNo').value = cArr[24]; } catch(ex) { };
	try { fm.all('ICNo').value = cArr[25]; } catch(ex) { };
	try { fm.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
	try { fm.all('HomeAddress').value = cArr[27]; } catch(ex) { };
	try { fm.all('PostalAddress').value = cArr[28]; } catch(ex) { };
	try { fm.all('ZipCode').value = cArr[29]; } catch(ex) { };
	try { fm.all('Phone').value = cArr[30]; } catch(ex) { };
	try { fm.all('BP').value = cArr[31]; } catch(ex) { };
	try { fm.all('Mobile').value = cArr[32]; } catch(ex) { };
	try { fm.all('EMail').value = cArr[33]; } catch(ex) { };
	//try { fm.all('BankCode').value = cArr[34]; } catch(ex) { };
	//try { fm.all('BankAccNo').value = cArr[35]; } catch(ex) { };
	try { fm.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
	try { fm.all('Position').value = cArr[35]; } catch(ex) { };
	try { fm.all('GrpNo').value = cArr[36]; } catch(ex) { };
	try { fm.all('GrpName').value = cArr[37]; } catch(ex) { };
	try { fm.all('GrpPhone').value = cArr[38]; } catch(ex) { };
	try { fm.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
	try { fm.all('GrpAddress').value = cArr[40]; } catch(ex) { };
	try { fm.all('DeathDate').value = cArr[41]; } catch(ex) { };
	try { fm.all('State').value = cArr[43]; } catch(ex) { };
	try { fm.all('WorkType').value = cArr[46]; } catch(ex) { };
	try { fm.all('PluralityType').value = cArr[47]; } catch(ex) { };
	try { fm.all('OccupationCode').value = cArr[49]; } catch(ex) { };
	try { fm.all('Degree').value = cArr[48]; } catch(ex) { };
	try { fm.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
	try { fm.all('SmokeFlag').value = cArr[51]; } catch(ex) { };
	try { fm.all('RgtAddress').value = cArr[52]; } catch(ex) { };

}


///����ҳ�������ύʱ���������ֵ����⴦��
//function specDealByRisk()
//{
//	//�����ͬ�Ŀ�-�����ó���
//	if(fm.all('RiskCode').value=='311603')
//	{
//	   if(trim(fm.all("AppntBirthday").value)==""||fm.all("AppntBirthday").value==null)
//	   {
//	   	if (trim(fm.all("AppntIDType").value) != "0"||fm.all("AppntIDNo").value==null||trim(fm.all("AppntIDNo").value)=="")
//	   	{
//	   		fm.all("AppntBirthday").value='1970-1-1';
//	   	}
//	   }
//
//		try
//		{
//			  var strBrithday = "";
//			  if(trim(fm.all("Birthday").value)==""||fm.all("Birthday").value==null)
//			  {
//			  	if (trim(fm.all("IDType").value) == "0")
//			  	 {
//			  	   strBrithday=	getBirthdatByIdNo(fm.all("IDNo").value);
//			  	   if(strBrithday=="") passVerify=false;
//
//		           fm.all("Birthday").value= strBrithday;
//			  	 }
//		      }
//		 }
//		 catch(e)
//		 {
//		}
//		return true;
//	}
//	//�����������ҵ����ҽ����
//	if(fm.all('RiskCode').value=='211801')
//	{
//		//���Զ���������У��
//		var strChooseDuty="";
//		for(i=0;i<=8;i++)
//		{
//			if(DutyGrid.getChkNo(i)==true)
//			{
//				strChooseDuty=strChooseDuty+"1";
//				DutyGrid.setRowColData(i, 5, fm.all('PayEndYear').value);//��������
//				DutyGrid.setRowColData(i, 6, fm.all('PayEndYearFlag').value);//�������ڵ�λ
//				DutyGrid.setRowColData(i, 9, fm.all('InsuYear').value);//��������
//				DutyGrid.setRowColData(i, 10, fm.all('InsuYearFlag').value);//�������ڵ�λ
//				DutyGrid.setRowColData(i, 11, fm.all('PayIntv').value);//�ɷѷ�ʽ
//			}
//			else
//			{
//				strChooseDuty=strChooseDuty+"0";
//			}
//		}
//		//alert(strChooseDuty);
//		//fm.all('StandbyFlag1').value=strChooseDuty;
//		return true;
//	}
//	//�����MS��ҵ����Ա�������ƻ� add by guoxiang 2004-9-8 10:24
//	if(fm.all('RiskCode').value=='211701')
//	{
//		//���Զ���������У��
//		var strChooseDuty="";
//		for(i=0;i<=2;i++)
//		{
//			if(DutyGrid.getChkNo(i)==true)
//			{
//				strChooseDuty=strChooseDuty*1+1.0;
//                DutyGrid.setRowColData(i, 3, fm.all('Prem').value);//����
//				DutyGrid.setRowColData(i, 9, fm.all('InsuYear').value);//��������
//				DutyGrid.setRowColData(i, 10, fm.all('InsuYearFlag').value);//�������ڵ�λ
//				DutyGrid.setRowColData(i, 11, fm.all('PayIntv').value);//�ɷѷ�ʽ
//				DutyGrid.setRowColData(i, 12, fm.all('ManageFeeRate').value);//����ѱ���
//
//			}
//			else
//			{
//				strChooseDuty=strChooseDuty*1+0.0;
//			}
//		}
//		if(strChooseDuty>1){
//		   alert("��ҵ����Ա������ÿ�ű���ֻ����ѡ��һ�����Σ���ѡ������δ���Ϊ"+strChooseDuty+"�����޸ģ�����");
//		   return false;
//	    }
//		return true;
//	}
//
//	//����Ǹ��˳�����
//	if(fm.all('RiskCode').value=='112401')
//	{
//		if(fm.all('GetYear').value!=''&&fm.all('InsuYear').value!='')
//		{
//		  	if(fm.all('InsuYear').value=='A')
//		  	{
//		  		fm.all('InsuYear').value='88';
//		  		fm.all('InsuYearFlag').value='A';
//		  	}
//		  	else if(fm.all('InsuYear').value=='B')
//		  	{
//		  		fm.all('InsuYear').value=20+Number(fm.all('GetYear').value);
//		  		fm.all('InsuYearFlag').value='A';
//		  	}
//		  	else
//		  	{
//		  		alert("�����ڼ����ѡ��");
//		  		return false;
//
//		  	}
//		  	if(fm.all('PayIntv').value=='0')
//		  	{
//		  		fm.all('PayEndYear').value=fm.all('InsuYear').value;
//		  		fm.all('PayEndYearFlag').value=fm.all('InsuYearFlag').value;
//		  	}
//
//		}
//		return true;
//	}
//	//����Ǿ���������
//	if(fm.all('RiskCode').value=='241801')
//	{
//		try
//		{
//			var InsurCount = fm.all('StandbyFlag2').value;
//			if(InsurCount>4||InsurCount<0)
//			{
//				alert("�����������������ܳ���4��");
//				return false;
//			}
//			SubInsuredGrid.delBlankLine("SubInsuredGrid");
//			var rowNum=SubInsuredGrid.mulLineCount;
//			if(InsurCount!=rowNum)
//			{
//			    alert("���������������Ͷ��������������������Ϣ�����������ϣ� ");
//				return false;
//			}
//
//	    }
//	    catch(ex)
//	    {
//	      alert(ex);
//	      return false;
//	    }
//	    return true;
//	}
//
//
//}

//����ҳ���ʼ��ʱ���������ֵ����⴦��
function initDealForSpecRisk(cRiskCode)
{
  try{
	//�����211801
	if(cRiskCode=='211801')
	{
		DutyGrid.addOne();
		DutyGrid.setRowColData(0, 1, '610001');
		DutyGrid.setRowColData(0, 2, '��������1��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(1, 1, '610002');
		DutyGrid.setRowColData(1, 2, '��������2��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(2, 1, '610003');
		DutyGrid.setRowColData(2, 2, '��������3��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(3, 1, '610004');
		DutyGrid.setRowColData(3, 2, '��������4��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(4, 1, '610005');
		DutyGrid.setRowColData(4, 2, '��������5��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(5, 1, '610006');
		DutyGrid.setRowColData(5, 2, '��������6��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(6, 1, '610007');
		DutyGrid.setRowColData(6, 2, '��������7��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(7, 1, '610008');
		DutyGrid.setRowColData(7, 2, '��������8��');
		DutyGrid.addOne();
		DutyGrid.setRowColData(8, 1, '610009');
		DutyGrid.setRowColData(8, 2, 'ŮԱ����������');
		DutyGrid.lock();


	}

	//�������
	if(cRiskCode=='212401')
	{

		PremGrid.addOne();
		PremGrid.setRowColData(0, 1, '601001');
		PremGrid.setRowColData(0, 2, '601101');
		PremGrid.setRowColData(0, 3, '���彻��');
		PremGrid.addOne();
		PremGrid.setRowColData(1, 1, '601001');
		PremGrid.setRowColData(1, 2, '601102');
		PremGrid.setRowColData(1, 3, '���˽���');
		PremGrid.lock();

	}

    //��ҵ����
	if(cRiskCode=='211701')
	{
        var strSql = "select *  from lmdutypayrela where dutycode in  "
	               + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"')";
	    turnPage.queryModal(strSql, PremGrid);
	    PremGrid.lock;
    }

  }catch(ex) {}

}

/*********************************************************************
* ����ҳ���ʼ��ʱ���������ֵ����⴦����չ
*  add by guoxiang  at 2004-9-6 16:21
*  for update up function initDealForSpecRisk
*  not write function for every risk
*********************************************************************
 */
function initDealForSpecRiskEx(cRiskCode)
{
	try{
	    var strSql="";
	    //alert(cRiskCode);
	    var tSql="select risktype6 from lmriskapp where riskcode='"+cRiskCode+"'";
	    //alert(tSql);
	    var arrResult= easyExecSql(tSql, 1, 0);
	    if(arrResult==null){
	    	alert("δ�鵽����������Ϣ");
	    }
      
	    if(fm.all('inpNeedDutyGrid').value==1){
         initDutyGrid();  //�������ֳ�ʼ������¼���
        if(arrResult=="1"){//139,151
            var cPolTypeFlag=fm.PolTypeFlag.value;
        //    alert("cPolTypeFlag:"+cPolTypeFlag);
            var cDutyCode="";
            //if (cPolTypeFlag=="0"){
            //   cDutyCode=cRiskCode+"001";
            //}
            //if (cPolTypeFlag=="2"){
            //   cDutyCode=cRiskCode+"002";
            //}
            if (cPolTypeFlag=="1"){
         	     //cDutyCode=cRiskCode+"001";
         	     cPolTypeFlag="0";
            }
            strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "
	               + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and choflag!='B' and specflag='"+cPolTypeFlag+"')";            
            //strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode='"+cDutyCode+"'";
	       }else if(arrResult=="6"){//211801	������ҵ����ҽ�Ʊ���  
            var cPolTypeFlag=fm.PolTypeFlag.value;
        //    alert("cPolTypeFlag:"+cPolTypeFlag);
            var cDutyCode="";
            //if (cPolTypeFlag=="0"){
            //   cDutyCode=cRiskCode+"001";
            //}
            //if (cPolTypeFlag=="2"){
            //   cDutyCode=cRiskCode+"002";
            //}
            if (cPolTypeFlag=="1"){
         	     //cDutyCode=cRiskCode+"001";
         	     cPolTypeFlag="0";
            }
            strSql = "select dutycode,dutyname,'','','','','','','','','','','',''  from lmduty where dutycode in "
	               + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and choflag!='B' and specflag='"+cPolTypeFlag+"')";            
            //strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode='"+cDutyCode+"'";
	       }
        //else if(cRiskCode=="196"){
        //   var cPolTypeFlag=fm.PolTypeFlag.value;
        //   var cDutyCode="";
        //   if (cPolTypeFlag=="0"){
        //      cDutyCode="196001";
        //   }
        //   if (cPolTypeFlag=="2"){
        //      cDutyCode="196002";
        //   }
        //   if (cPolTypeFlag=="1"){
        //	     cDutyCode="196001";
        //   }
        //   strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode='"+cDutyCode+"'";
	      //}	       
         else if(arrResult=="5"){//805
            var cPolTypeFlag=fm.PolTypeFlag.value;
            var cDutyCode="";
            if (cPolTypeFlag=="0"||cPolTypeFlag=="1"){
               cDutyCode=cRiskCode+"001";
            }
            if (cPolTypeFlag=="2"){
         	     cDutyCode=cRiskCode+"003";
            }
            if (cPolTypeFlag=="3"){
         	     cDutyCode=cRiskCode+"004";
            }            
            strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode='"+cDutyCode+"'";
	       }	       
	       else if(arrResult=="4"){//188,198
            var cPolTypeFlag=fm.PolTypeFlag.value;
            var cDutyCode="";
            if (cPolTypeFlag=="0"||cPolTypeFlag=="1"){
               cDutyCode="('"+cRiskCode+"001','"+cRiskCode+"002','"+cRiskCode+"003')";
            }
            if (cPolTypeFlag=="3"){
               cDutyCode="('"+cRiskCode+"004')";
            }
            strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "+cDutyCode+"order by dutycode";
	       }
	       else if(arrResult=="3"){//162
	      
            var cPolTypeFlag=fm.PolTypeFlag.value;
            var cDutyCode="";
            if (cPolTypeFlag=="0"||cPolTypeFlag=="1"){
               cDutyCode="('"+cRiskCode+"001','"+cRiskCode+"002')";
            }
            if (cPolTypeFlag=="3"){
               cDutyCode="('"+cRiskCode+"004')";
            }
            if (cPolTypeFlag=="2"){
         	     cDutyCode="('"+cRiskCode+"003')";
            }
            strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "+cDutyCode+"order by dutycode";
	       }	   
	       else if(arrResult=="2"){//113
            var cPolTypeFlag=fm.PolTypeFlag.value;
            var cDutyCode="";
            if (cPolTypeFlag=="0"||cPolTypeFlag=="1"){
               cDutyCode="('"+cRiskCode+"001','"+cRiskCode+"002')";
            }
            if (cPolTypeFlag=="2"){
         	       cDutyCode="('"+cRiskCode+"003')";
            }
            strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "+cDutyCode+"order by dutycode";
	       }	           
         else {
            strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "
	               + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and choflag!='B')";
	       }
	    // alert("sql:"+strSql);
	     turnPage.queryModal(strSql, DutyGrid);
	     var cDutyCode="";
	     var tSql="";
	     for(var i=0;i<=DutyGrid.mulLineCount-1;i++){
	       cDutyCode=DutyGrid.getRowColData(i,1);
	       tSql="select choflag from lmriskduty where riskcode='"+cRiskCode+"' and dutycode='"+cDutyCode+"'";
	       var arrResult=easyExecSql(tSql,1,0);
	       //alert("ChoFlag:"+arrResult[0]);
	       if(arrResult[0]=="M"){
	       	 DutyGrid.checkBoxSel(i+1);
	       }
	     }
	     DutyGrid.lock;

        }
        //if(fm.all('inpNeedPremGrid').value==1){
        //alert(fm.all('RiskCode').value);
        //if(fm.all('RiskCode').value=="139"){
        	//alert(PolTypeFlag);
        	
        	//fm.all('inpNeedPremGrid').value="1";
        	//alert("ok");
        	//if(PolTypeFlag=="2")
        	//{
         // strSql = "select a.dutycode,a.payplancode,a.payplanname,'0','','','','','' from lmdutypayrela a where dutycode in  "
	         //      + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"')";
	              
	        //}else{
	          //  strSql = "select a.dutycode,a.payplancode,a.payplanname,'0','','','','','' from lmdutypayrela a where dutycode in  "
	              // + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"') and payplancode='139101'";
	                
	           // }
          //alert(strSql);
         // turnPage.queryModal(strSql, PremGrid);
        //  PremGrid.lock;

        //}
   //yaorywln���Ӷ�������Ϣ��ʼ��

  }catch(ex) {}

}
function queryAgent()
{
	if(fm.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
    if(fm.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //�����²�ѯҵ��Աbranchtype='2'
	  showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+"&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(fm.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������
	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+fm.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+fm.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentGroup.value = arrResult[0][1];
  }
}

function queryAgent2()
{
	if(fm.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(fm.all('AgentCode').value != "" && fm.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������
	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+fm.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}
  function returnparent()
  {
  	//edit by yaory
    if(LoadFlag=="99")
    {
    	//alert("ok");
    	location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&prtNo="+prtNo+"&scantype="+scantype+"&checktype="+checktype+"";
    	return;
    }
  	var backstr=fm.all("ContNo").value;
  	//alert(backstr+"backstr");
  	mSwitch.deleteVar("ContNo");
	  mSwitch.addVar("ContNo", "", backstr);
	  mSwitch.updateVar("ContNo", "", backstr);
//  	location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype;
  	//���ڸ��գ����շ��ص�ҳ�治ͬ�ڴ˽�������
  	//LoadFlag='1' ����
  	//LoadFlag='2' ����
  	//alert(ScanFlag);
  	//edit by yaory
    
  	//if(LoadFlag == "8" && EdorType =="NS"){
  	//  location.href="../bq/PEdorTypeNSInput.jsp";
    //  return;
    //}
    //alert(LoadFlag);
  	if(LoadFlag=="1")
  	{
  		if(BankFlag=="1")
  		  {
  		  	location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
  		else	
  	    {
  	  	location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType; 
        return;
        }
    }
  	if(LoadFlag=="2")
  	{
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
    	else
    		{  
    			//alert("ok");
       	  location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&PolTypeFlag="+PolTypeFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&ActivityID="+ActivityID+"&NoType="+NoType+"&tNameFlag="+NameType+"&display="+display+"&InsuredNo="+tInsuredNo+"&ContNo="+fm.ContNo.value; 
          return;
       }
    }
    if(LoadFlag=="13")
  	{
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
    	else
    		{  
    			//alert("ok");
       	  location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&PolTypeFlag="+PolTypeFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&ActivityID="+ActivityID+"&NoType="+NoType+"&tNameFlag="+NameType+"&display="+display; 
          return;
       }
    }
    if(LoadFlag=="16")
  	{
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
    	else
    		{  
    			//alert("ok");
       	  location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&PolTypeFlag="+PolTypeFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&ActivityID="+ActivityID+"&NoType="+NoType+"&tNameFlag="+NameType+"&display="+display; 
          return;
       }
    }
    if(LoadFlag=="7" || LoadFlag=="8")
  	{
  		//alert(BankFlag);
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInsuredInput.jsp?EdorTypeCal="+EdorTypeCal+"&LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&EdorTypeCal="+EdorTypeCal+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
    	else
    		{  
    			//alert("ok");
       	  location.href="ContInsuredInput.jsp?EdorTypeCal="+EdorTypeCal+"&LoadFlag="+LoadFlag+"&PolTypeFlag="+PolTypeFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&ActivityID="+ActivityID+"&NoType="+NoType+"&tNameFlag="+NameType+"&display="+display+"&InsuredNo="+tInsuredNo; 
          return;
       }
    }
    if(LoadFlag=="4")
  	{
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
    	else
    		{  
    			//alert("ok");
       	  location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&PolTypeFlag="+PolTypeFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&ActivityID="+ActivityID+"&NoType="+NoType+"&tNameFlag="+NameType+"&display="+display; 
          return;
       }
    }
    if(LoadFlag=="3")
    {
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
  		else
  			{
  	      location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
          return;
        }
    }
    if(LoadFlag=="5")
    {
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
  		else
  			{
  	      location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
          return;
        }
    }
    if(LoadFlag=="6")
    {
    	if(BankFlag=="1")
  		  {
  		  	location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
  		    return;  	
  			} 
  		else
  			{
  	      location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
          return;
        }
    }
    
}
//(GrpContNo,LoadFlag);//���ݼ����ͬ�Ų��������Ϣ
function getRiskByGrpPolNo(GrpContNo,LoadFlag)
{
    	//alert("1");
    	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	strsql = "select riskcode,riskname from lmriskapp where riskcode in (select riskcode from LCGrpPol where GrpContNo='"+GrpContNo+"')" ;
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
	}
	//alert ("tcodedata : " + tCodeData);

	return tCodeData;
}
function getRiskByGrpAll()
{
    	//alert("1");
    	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('G','A','B','D') order by RiskCode" ;
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
	}
	//alert ("tcodedata : " + tCodeData);

	return tCodeData;
}
function getRisk(){
    	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('I','A','C','D')"
           + " order by RiskCode";;
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
	}

	return tCodeData;



}

function getRiskByContPlan(GrpContNo,ContPlanCode){
	//alert(GrpContNo+":"+ContPlanCode);
    	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	strsql = "select riskcode,riskname from lmriskapp where riskcode in (select riskcode from LCGrpPol where GrpContNo='"+GrpContNo+"')";
	//alert(strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
	}
	//alert("tCodeData:"+tCodeData);
	return tCodeData;



}


 /*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag)
{


	//alert("wFlag in ProposalInput=="+wFlag);
	//alert("abcd=="+fm.all('ContNo').value);
	if (wFlag ==1 ) //¼�����ȷ��
	{
		if(fm.all('ContNo').value == "")
	  {
	    alert("��ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
	   	return;
	  }
	 // alert("aaa=="+fm.all('ContNo').value);
		if(ScanFlag=="1"){
		  fm.WorkFlowFlag.value = "0000001099";
		}
	  else{
		  fm.WorkFlowFlag.value = "0000001098";
	  }
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	//	alert("tMissionID=="+tMissionID);
	//	alert("tSubMissionID=="+tSubMissionID);
  }
  else if (wFlag ==2)//�������ȷ��
  {
  	if(fm.all('ProposalContNo').value == "")
	  {
	    alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
	   	return;
	  }

		fm.WorkFlowFlag.value = "0000001001";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	  else if (wFlag ==3)
  {
  	if(fm.all('ProposalContNo').value == "")
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001002";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;

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

	fm.action = "./InputConfirm.jsp";
  fm.submit(); //�ύ
}

//�˺�����Ŀ���ǣ���ѯ�ŵ����߸����µ�Ͷ������Ͷ����Ϣ
function getContInputnew(){
	//ȡ�ø���Ͷ���˵�������Ϣ
	if(fm.AppntCustomerNo.value!=""&&fm.AppntCustomerNo.value!="false"){
	  //arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo ='"+fm.AppntCustomerNo.value+"'",1,0);
         // arrResult=easyExecSql("select a.* from LDPerson a Where 1=1 and trim(a.CustomerNo) =trim('"+fm.AppntCustomerNo.value+"')");
    //alert("aaa=="+fm.all('RelationToAppnt').value);
    //alert(arrResult[0][5]);
    //displayAppnt(arrResult[0]);
    //alert("bbb=="+fm.all('AppntIDNo').value);
  }
	//ȡ��Ͷ����λ�� ������Ϣ
	if(fm.GrpContNo.value!=""&&fm.GrpContNo.value!="false"){
	  arrResult = easyExecSql("select a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.GrpName from LCGrpAddress a,LDGrp b where a.CustomerNo = b.CustomerNo and b.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + fm.GrpContNo.value + "')", 1, 0);
	  displayAddress1(arrResult[0]);
	}
	//ȡ�ñ�Ͷ���˵�������Ϣ
	if(fm.all('CustomerNo').value!=""&&fm.all('CustomerNo').value!="false"){
    var tcustomerNo=fm.all('CustomerNo').value;
    //alert("contno:"+fm.all('ContNo').value);
    var tContNo=fm.all('ContNo').value;
    arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.InsuredNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LCInsured a Left Join LCAddress b On b.CustomerNo =a.InsuredNo Where 1=1 and a.InsuredNo ='"+tcustomerNo+"' and a.ContNo='"+tContNo+"'",1,0);
    displayInsured(arrResult[0]);
  }
}

function GrpConfirm(ScanFlag){//ScanFlag
	//alert('ProposalGrpContNo:'+fm.all('ProposalGrpContNo').value);
	//alert('PrtNo:'+fm.all('PrtNo').value);
	//alert('MissionID:'+fm.all('MissionID').value);
	 var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );

	strSql = "select peoples2 from LCGrpCont 	where GrpContNo = '" + tGrpContNo + "'";
	var tPeoplesCount = easyExecSql(strSql);

	if(tPeoplesCount==null||tPeoplesCount[0][0]<=0){
		alert("����ʧ�ܣ�Ͷ��������Ϊ0��");
		return;
	}

	strSql = "select peoples2,riskcode from LCGrpPol 	where GrpContNo = '" + tGrpContNo + "'";
	tPeoplesCount = easyExecSql(strSql);
	//alert(tPeoplesCount);
	if(tPeoplesCount!=null)
	{
		for(var i=0;i<tPeoplesCount.length;i++)
		{
			if(tPeoplesCount[i][0]<=0)
			{
				alert("����ʧ�ܣ�����"+tPeoplesCount[i][1]+"��Ͷ��������Ϊ0��");
				return;
			}
		}
	}

	strSql = "select SaleChnl,AgentCode,AgentGroup,ManageCom,GrpName,CValiDate,PrtNo from LCGrpCont 	where GrpContNo = '"
	+ tGrpContNo + "'";

	var grpContInfo = easyExecSql(strSql);
	//alert(grpContInfo);
	var queryString = 'SaleChnl='+grpContInfo[0][0]+'&AgentCode='+grpContInfo[0][1]+'&AgentGroup='+grpContInfo[0][2]
		+'&ManageCom='+grpContInfo[0][3]+'&GrpName='+grpContInfo[0][4]+'&CValiDate='+grpContInfo[0][5];

	strSql = "	select missionID,SubMissionID from lwmission where 1=1 "
					+" and lwmission.processid = '0000000011'"
					+" and lwmission.activityid = '0000011098'"
					+" and lwmission.missionprop1 = '"+grpContInfo[0][6]+"'";
	var missionInfo = easyExecSql(strSql);
	queryString = queryString+"&MissionID="+missionInfo[0][0]+"&SubMissionID="+missionInfo[0][1];
	//alert(queryString);
	var tStr= "	select * from lwmission where 1=1 "
					+" and lwmission.processid = '0000000011'"
					+" and lwmission.activityid = '0000011001'"
					+" and lwmission.missionprop1 = '"+fm.all('ProposalGrpContNo').value+"'";
	turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
	if (turnPage.strQueryResult) {
	alert("���ŵ���ͬ�Ѿ��������棡");
	return;
	}
	var WorkFlowFlag = "";
	if(ScanFlag==0)
	{
		WorkFlowFlag = "0000011098";
	}
	if(ScanFlag==1)
	{
		 WorkFlowFlag = "0000011099";
	}
	queryString = queryString+"&WorkFlowFlag="+WorkFlowFlag;
	mAction = "CONFIRM";
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

	fm.action = "./GrpInputConfirm.jsp?FrameType=0&"+queryString;
	fm.submit(); //�ύ

}

/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm2(wFlag)
{
	//alert("wFlag=="+wFlag);
	if (wFlag ==1 ) //¼�����ȷ��
	{
    var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
		if (turnPage.strQueryResult) {
		  alert("�ú�ͬ�Ѿ��������棡");
		  return;
		}

		fm.WorkFlowFlag.value = "0000001098";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;			//¼�����
  }
  else if (wFlag ==2)//�������ȷ��
  {
  	if(fm.all('ProposalContNo').value == "")
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001001";					//�������
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
		approvefalg="2";
	}
	  else if (wFlag ==3)
  {
  	if(fm.all('ProposalContNo').value == "")
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001002";					//�����޸����
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else if(wFlag == 4)
	{
		 if(fm.all('ProposalContNo').value == "")
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001021";					//�����޸�
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;

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
	fm.action = "./InputConfirm.jsp";
  fm.submit(); //�ύ
}

/*********************************************************************
 *  У�����ֽ���ķ����Ƿ�Ϊ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  create by malong 2005-7-11
 *********************************************************************
 */
function checkMult(){
	var tSql="";
  tSql="select AmntFlag from lmduty a,lmriskduty b where b.riskcode='"+fm.all('RiskCode').value+"' and a.dutycode=b.dutycode";
  var arrResult=easyExecSql(tSql);

  if(arrResult[0]=="2" && fm.all('inpNeedDutyGrid').value == "0"){
 		if(fm.all('mult').value == "0")
 		{
 			alert('��������Ϊ��!');
 			fm.all('mult').value = "";
 			fm.all('mult').focus();
 			return false;
 		}
		else if(fm.all('mult').value== "")
		{
			alert('��������Ϊ��!');
			return false;
		}
		else if(!isInteger(fm.all('mult').value))
		{
				alert('��������Ϊ����!');
				fm.all('mult').value = "";
 				fm.all('mult').focus();
 				return false;
		}
	}
	return true;
}
/*********************************************************************
 *  У��280���ֽ���Ķ�ѡ���ε�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  create by zhangyang 2005-7-29
 *********************************************************************
 */
function chkDuty(){
	var tSql="";
  tSql="select RiskCode from lcpol where polno='"+fm.all('MainPolNo').value+"'";
  var arrResult=easyExecSql(tSql);

 if(arrResult=="00608000"|| arrResult=="00609000" || arrResult=="00613000"){
 	if(DutyGrid.getChkNo(1)){
      alert("�����ղ���ѡ�����ζ�!");
      	return false;	
    	}
   	}
  if(arrResult=="00615000"){
 		if(DutyGrid.getChkNo(0)){
      		alert("�����ղ���ѡ������һ!");
      		return false;	
    		} 	
    	}	
	return true;
}
function getRiskCodeNS(ContNo){
	 var i = 0;
	 var j = 0;
	 var m = 0;
	 var n = 0;
	 var strsql = "";
	 var tCodeData = "0|";
	 var iSql="";
   iSql="select a.relariskcode,m.riskname from lmriskrela a,lmrisk m,lcpol p where a.relariskcode = m.riskcode and a.riskcode=p.riskcode and p.mainpolno = p.polno and p.contno = '"+ContNo+"'";
   turnPage.strQueryResult = easyQueryVer3(iSql, 1, 0, 1);
	 if (turnPage.strQueryResult != "")
	 {
		 turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		 m = turnPage.arrDataCacheSet.length;
		 for (i = 0; i < m; i++)
		 {
			 j = i + 1;
			 tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		 }
	 }
	 //alert ("tcodedata : " + tCodeData);

	 return tCodeData;

}
function getRiskCodeNR(tGrpContNo,aContNo){
	 var i = 0;
	 var j = 0;
	 var m = 0;
	 var n = 0;
	 var strsql = "";
	 var tCodeData = "0|";
	 var iSql="";
	 iSql = "select riskcode, riskname from lmrisk where riskcode in (select riskcode from lcgrppol where grpcontno='" + tGrpContNo + "') and riskcode not in (select riskcode from lcpol where ContNo='" + aContNo + "' and appflag='1')";
   turnPage.strQueryResult = easyQueryVer3(iSql, 1, 0, 1);
	 if (turnPage.strQueryResult != "")
	 {
		 turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		 m = turnPage.arrDataCacheSet.length;
		 for (i = 0; i < m; i++)
		 {
			 j = i + 1;
			 tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		 }
	 }
	 //alert ("tcodedata : " + tCodeData);

	 return tCodeData;

}
function InsuYearData105(){
var tStandByFlag1="";
var tCodeData="";
 
tStandByFlag1=fm.all('StandByFlag1').value;
 
 if(tStandByFlag1=="4" ){
			tCodeData="1";
		 }
		  
	 fm.InsuYear.CodeData=tCodeData;
 	}