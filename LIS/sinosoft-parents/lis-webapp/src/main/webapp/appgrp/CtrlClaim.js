//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var levelflag="";
var showInfo;
var arrDataSet;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var QueryResult="";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere="";
var tSearch = 0;
window.onfocus=myonfocus;

function alertme()
{
	if(document.all('ObserveDate').value==""||document.all('ObserveDate').value=="-1")
	{
		if ((document.all('Exempt').value==""||document.all('Exempt').value=="-1")&&(document.all('InhosExempt').value==""||document.all('InhosExempt').value=="-1")&&(document.all('ClinicExempt').value==""||document.all('ClinicExempt').value=="-1")&&(document.all('ClinicClaimRate').value==""||document.all('ClinicClaimRate').value=="-1")&&(document.all('InhosClaimRate').value==""||document.all('InhosClaimRate').value=="-1")&&(document.all('UnifyClaimRate').value==""||document.all('UnifyClaimRate').value=="-1"))
	  {
	       	  alert("����дͳһ������ͳһ�����������סԺ������������סԺ�⸶�����������⸶����");
	       	  return false;
	  }
	}
	else
	{
	   if(document.all('RiskCode').value=='110'||document.all('RiskCode').value=='106')
	   {

	       if ((document.all('Exempt').value!=""&&document.all('Exempt').value!="-1")&&(document.all('UnifyClaimRate').value==""||document.all('UnifyClaimRate').value=="-1"))
         {  
         	  alert("��Ϊ��д��ͳһ��������дͳһ�⸶������������ע���Ƿ��Ѿ���סԺ������������סԺ�⸶�����������⸶����ɾȥ");
	       	  return false;  	  
         }
         
         if ((document.all('UnifyClaimRate').value!=""&&document.all('UnifyClaimRate').value!="-1")&&(document.all('Exempt').value==""||document.all('Exempt').value=="-1"))
         {
         	  alert("��Ϊ��д��ͳһ�⸶����������дͳһ����������ע���Ƿ��Ѿ���סԺ������������סԺ�⸶�����������⸶����ɾȥ");
	       	  return false;  	  
         }
         
         if ((document.all('InhosExempt').value!=""&&document.all('InhosExempt').value!="-1")&&(document.all('ClinicExempt').value==""||document.all('ClinicExempt').value=="-1"||document.all('ClinicClaimRate').value==""||document.all('ClinicClaimRate').value=="-1"||document.all('InhosClaimRate').value==""||document.all('InhosClaimRate').value=="-1"))
         {
         	  alert("��Ϊ��д��סԺ��������д�������������⸶������סԺ�⸶������������ע���Ƿ��Ѿ���ͳһ������ͳһ�⸶����ɾȥ");
	       	  return false;  	  
         }
         if ((document.all('ClinicExempt').value!=""&&document.all('ClinicExempt').value!="-1")&&(document.all('InhosExempt').value==""||document.all('InhosExempt').value=="-1"||document.all('ClinicClaimRate').value==""||document.all('ClinicClaimRate').value=="-1"||document.all('InhosClaimRate').value==""||document.all('InhosClaimRate').value=="-1"))
         {
         	  alert("��Ϊ��д��������������дסԺ���������⸶������סԺ�⸶������������ע���Ƿ��Ѿ���ͳһ������ͳһ�⸶����ɾȥ");
	       	  return false;  	  
         }
         if ((document.all('ClinicClaimRate').value!=""&&document.all('ClinicClaimRate').value!="-1")&&(document.all('InhosExempt').value==""||document.all('InhosExempt').value=="-1"||document.all('ClinicClaimRate').value==""||document.all('ClinicClaimRate').value=="-1"||document.all('InhosClaimRate').value==""||document.all('InhosClaimRate').value=="-1"))
         {
         	  alert("��Ϊ��д�������⸶����������дסԺ���������⸶������סԺ�⸶������");
	       	  return false;  	  
         }
         if ((document.all('InhosClaimRate').value!=""&&document.all('InhosClaimRate').value!="-1")&&(document.all('InhosExempt').value==""||document.all('InhosExempt').value=="-1"||document.all('ClinicClaimRate').value==""||document.all('ClinicClaimRate').value=="-1"||document.all('InhosClaimRate').value==""||document.all('InhosClaimRate').value=="-1"))
         {
         	  alert("��Ϊ��д��סԺ�⸶����������дסԺ���������⸶������סԺ�⸶������");
	       	  return false;  	  
         }
     }
  }
  return true;
}

function submitForm()
{ 	if (!beforeSubmit())
	{
			return false;
	}
	//return ;
	/*
	if (document.all('DutyCode').value=="")
	{
		  alert("�������α���Ϊ�ղ����Խ�����Ӳ�������ѡ�������α�����룡");
		  return false;
    }
    */
 /* 
 	if (!alertme())
  {
  	 return false;
  }
  */
  document.all('mOperate').value = "INSERT||MAIN";
  if (document.all('mOperate').value == "INSERT||MAIN")
	{
		if (!confirm('����'+document.all('GrpContNo').value+'�µ�����'+document.all('RiskCode').value+'����������Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ���'))
		{
			return false;
		}
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//���³�ʼ����ѯ����
	document.getElementById("fm").submit(); //�ύ
}

function getaddresscodedata()
{
	//��ʼ������
	var sql;
	if(fm.ContPlanCode.value!="")
	{
	 sql="select b.riskcode,a.riskname from lmrisk a,lccontplanrisk b where b.grpcontno='"+fm.GrpContNo.value+"' and b.contplancode='"+fm.ContPlanCode.value+"' and b.riskcode=a.riskcode";
}else{
sql="select riskcode,riskname from lmrisk where riskcode in (select riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"')";

}
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
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
    
    document.all("RiskCode").CodeData=tCodeData;
	
}

//������һ��
function returnparent()
{
  	//location.href="ContPolInput.jsp?polNo="+GrpContNo+"&scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&LoadFlag="+ LoadFlag + "&GrpContNo=" + GrpContNo;
	  //return;
	  top.close();
}

//�����ύ��ɾ����
function DelContClick(){
	//�˷����õ���������Ҫ-1����
	var line = CtrlClaimCodeGrid.getSelNo();
	if (line == 0)
	{
		alert("��ѡ��Ҫɾ����������ƣ�");
		document.all('DutyCode').value = "";
		return false;
	}
	else
	{
		document.all('DutyCode').value = CtrlClaimCodeGrid.getRowColData(line-1,1);
	}
	document.all('mOperate').value = "DELETE||MAIN";
	document.all('levelflag').value = "1";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//���³�ʼ����ѯ����
	document.getElementById("fm").submit(); //�ύ
	
}

//�����ύ���޸ģ�
function UptContClick(){
	document.all('mOperate').value = "UPDATE||MAIN";
	document.all('levelflag').value = "1";
	if (!beforeSubmit()){
		return false;
	}
	 if (!alertme())
  {
  	return false;
  }
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//���³�ʼ����ѯ����
	document.getElementById("fm").submit(); //�ύ
}

//����У��
function beforeSubmit(){
	if (document.all('GrpContNo').value == ""){
			alert("���������屣����!");
			document.all('GrpContNo').focus();
			return false;
		}
			if (document.all('CtrlProp').value == ""){
			alert("�������������!");
			document.all('CtrlProp').focus();
			return false;
		}
		
	if (document.all('mOperate').value == "UPDATE||MAIN"){
		if (document.all('GrpContNo').value == ""){
			alert("���������屣���ţ�");
			document.all('GrpContNo').focus();
			return false;
		}
	}
	if (document.all('mOperate').value == "DELETE||MAIN"){
	   if (CtrlClaimCodeGrid.mulLineCount == 0 && DutyCtrllevelCodeGrid.mulLineCount == 0){
		    alert("������������!");
		       return false;
	    }
	   var tCtrlBatchNo = document.all('CtrlBatchNo').value ;
	   if(tCtrlBatchNo==null||tCtrlBatchNo=='')
	   {
	   	 alert("��ѡ��һ����¼��ɾ��!");
		       return false;
	   }
	}
	
	if(!verifyInput())
	{
		return false;
	}
	
	var lineCount = CtrlClaimCodeGrid.mulLineCount;

	//���Ҫ��ֵ��ϢУ��
	return true;
}

function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
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
	else{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initCtrlClaimCodeGrid();
	    tSearch = 0;
	    QueryCount = 0;
	    
	    if(document.all('levelflag').value=='1')
	    {
	    	QueryForm();
	    	
	    }
	    
	    
	}
	
	initForm();
	
	document.all('mOperate').value = "";
	document.all('levelflag').value = "";


}

//��ѡ���������¼�
function ShowCtrlClaim(){
  var	i = CtrlClaimCodeGrid.getSelNo();
if(i!=0){
		i = i-1;
		//�����ͬ��
		//ְҵ����
		
		initInpBox();
		
		fm.OccupationType.value = CtrlClaimCodeGrid.getRowColData(i,2);
		fm.CtrlProp.value = CtrlClaimCodeGrid.getRowColData(i,3);
		fm.ContPlanCode.value=CtrlClaimCodeGrid.getRowColData(i,4);
		fm.RiskCode.value=CtrlClaimCodeGrid.getRowColData(i,5);
		fm.DutyCode.value=CtrlClaimCodeGrid.getRowColData(i,6);
		fm.GetDutyCode.value=CtrlClaimCodeGrid.getRowColData(i,7);
		fm.CtrlBatchNo.value=CtrlClaimCodeGrid.getRowColData(i,9);
		//fm.ExamLimit.value=CtrlClaimCodeGrid.getRowColData(i,10);
		//fm.SingleExamLimit.value=CtrlClaimCodeGrid.getRowColData(i,7);
		fm.ObserveDate.value=CtrlClaimCodeGrid.getRowColData(i,10);
		fm.Exempt.value=CtrlClaimCodeGrid.getRowColData(i,11);
		fm.ExemptDate.value=CtrlClaimCodeGrid.getRowColData(i,12);
		fm.TotalLimit.value=CtrlClaimCodeGrid.getRowColData(i,13);
		fm.ClaimRate.value=CtrlClaimCodeGrid.getRowColData(i,14);
		fm.StartPay.value=CtrlClaimCodeGrid.getRowColData(i,15);
		fm.EndPay.value=CtrlClaimCodeGrid.getRowColData(i,16);
		fm.Remark.value=CtrlClaimCodeGrid.getRowColData(i,17);
		showOneCodeNameRefresh('OccupationType','OccupationType','OccupationTypeName');
		showOneCodeNameRefresh('CtrlProp','CtrlProp','CtrlPropName');
		showOneCodeNameRefresh('lldutyctrlplan','ContPlanCode','ContPlanCodeName');
		showOneCodeNameRefresh('RiskCode','RiskCode','AddressNoName');
		//showOneCodeNameRefresh('griskduty','DutyCode','DutyCodeName');
		var tSQL = "select dutyname from lmduty where dutycode='"+fm.DutyCode.value+"' ";
		var tArr = easyExecSql(tSQL); 
		if(tArr!=null)
		{
			fm.DutyCodeName.value = tArr[0][0];
		}
		var tSQL1 = "select getdutyname from lmdutyget where getdutycode='"+fm.GetDutyCode.value+"' ";
		var tArr1 = easyExecSql(tSQL1); 
		if(tArr1!=null)
		{
			fm.GetDutyCodeName.value = tArr1[0][0];
		}
		
		//showOneCodeNameRefresh('griskgetduty','GetDutyCode','GetDutyCodeName');
}

}

function ShowDutyCtrllevel(){
	var	i = DutyCtrllevelCodeGrid.getSelNo();
	if(i!=0){
		i = i-1;
		fm.SerialNo.value = DutyCtrllevelCodeGrid.getRowColData(i,0);
		fm.DutyCode2.value = DutyCtrllevelCodeGrid.getRowColData(i,1);
		fm.DownLimit.value = DutyCtrllevelCodeGrid.getRowColData(i,2);
		fm.UpLimit.value=DutyCtrllevelCodeGrid.getRowColData(i,3);
		fm.Rate.value=DutyCtrllevelCodeGrid.getRowColData(i,4);
		}
}
	

function QueryForm(){
	
	  if (document.all("GrpContNo").value==null||document.all("GrpContNo").value=="")
	  {
	  	alert("���������屣���ţ�");
	  	return false;
	  }
	  
	  
	  //var Result = easyExecSql("select grpcontno from lcgrpcont where grpcontno='" + document.all("GrpContNo").value + "'", 1, 0);
	  initCtrlClaimCodeGrid();
	  //initDutyCtrllevelCodeGrid();
	 // var tContPlanCode=document.all("ContPlanCode").value;
	  //alert(tContPlanCode);
	  /*if (tContPlanCode==null||tContPlanCode=="")
	  {
	  	tContPlanCode="0";
	  }*/
    var strSQL="";
  /*  if (document.all("InsuredNo").value!="" && document.all("InsuredNo").value!= null){
    	strSQL = "select DutyCode,(case to_char(ObserveDate) when '-1' then '' else to_char(ObserveDate) end),(case to_char(Exempt) when '-1' then '' else to_char(Exempt) end),(case to_char(InhosExempt) when '-1' then '' else to_char(InhosExempt) end),(case to_char(ClinicExempt) when '-1' then '' else to_char(ClinicExempt) end),(case to_char(ExemptDate) when '-1' then '' else to_char(ExemptDate) end),(case to_char(InhosLimit) when '-1' then '' else to_char(InhosLimit) end),(case to_char(ClinicClaimRate) when '-1' then '' else to_char(ClinicClaimRate) end),(case to_char(InhosClaimRate) when '-1' then '' else to_char(InhosClaimRate) end),(case to_char(UnifyClaimRate) when '-1' then '' else to_char(UnifyClaimRate) end),(case to_char(TotalLimit) when '-1' then '' else to_char(TotalLimit) end) from LLDutyCtrl where GrpContNo='"	+Result[0][0] +"' and ContPlanCode='"+ tContPlanCode 
    	+ "' and contno in (select contno from lcpol where Insuredno='" + document.all("InsuredNo").value + "')";
    }
    else{
	   strSQL = "select DutyCode,(case to_char(ObserveDate) when '-1' then '' else to_char(ObserveDate) end),(case to_char(Exempt) when '-1' then '' else to_char(Exempt) end),(case to_char(InhosExempt) when '-1' then '' else to_char(InhosExempt) end),(case to_char(ClinicExempt) when '-1' then '' else to_char(ClinicExempt) end),(case to_char(ExemptDate) when '-1' then '' else to_char(ExemptDate) end),(case to_char(InhosLimit) when '-1' then '' else to_char(InhosLimit) end),(case to_char(ClinicClaimRate) when '-1' then '' else to_char(ClinicClaimRate) end),(case to_char(InhosClaimRate) when '-1' then '' else to_char(InhosClaimRate) end),(case to_char(UnifyClaimRate) when '-1' then '' else to_char(UnifyClaimRate) end),(case to_char(TotalLimit) when '-1' then '' else to_char(TotalLimit) end)��from LLDutyCtrl where GrpContNo='"	+Result[0][0] +"' and ContPlanCode='"+ tContPlanCode+"' and riskCode ='"+document.all("RiskCode").value+"'";
	  }	  
	*/  
	  strSQL = "select grpcontno,(case occupationtype when '*' then '' else occupationtype end),"
	         + " (case ctrlprop when '*' then '' else ctrlprop end), "
	         + " (case contplancode when '*' then '' else contplancode end), "
	         + " (case riskcode when '*' then '' else riskcode end), "
	         + " (case dutycode when '*' then '' else dutycode end), "
	         + " (case getdutycode when '*' then '' else getdutycode end), "
	         + " ctrllevel,ctrlbatchno, "
	         + " (case ObserveDate when -1 then '' else to_char(ObserveDate) end), "
	         + " (case Exempt when -1 then '' else to_char(Exempt) end), "
	         + " (case ExemptDate when -1 then '' else to_char(ExemptDate) end), "
	         + " (case TotalLimit when -1 then '' else to_char(TotalLimit) end), "
	         + " (case ClaimRate when -1 then '' else to_char(ClaimRate) end), "
	         + " (case StartPay when -1 then '' else to_char(StartPay) end), "
	         + " (case EndPay when -1 then '' else to_char(EndPay) end),Remark "
	         
	         + " from lldutyctrl "
	         + " where 1=1 "
	         //+ getWherePart('grpcontno', 'GrpContNo')
	         + getWherePart('occupationtype', 'OccupationType')
	         + getWherePart('ctrlprop', 'CtrlProp')
	         + getWherePart('contplancode', 'ContPlanCode')
	         + getWherePart('riskcode', 'RiskCode')
	         + getWherePart('dutycode', 'DutyCode')
	         + getWherePart('getdutycode', 'GetDutyCode');
	         if(document.all('GrpContNo').value!=null&&document.all('GrpContNo').value!=""){
	           strSQL=strSQL+" and grpcontno =(select grpcontno from lcgrpcont where grpcontno ='"+fm.GrpContNo.value+"')";
	         }
	  
	  turnPage.queryModal(strSQL, CtrlClaimCodeGrid); 
}

