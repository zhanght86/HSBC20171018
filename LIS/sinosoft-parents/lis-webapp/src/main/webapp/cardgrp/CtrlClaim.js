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
	if(fm.all('ObserveDate').value==""||fm.all('ObserveDate').value=="-1")
	{
		if ((fm.all('Exempt').value==""||fm.all('Exempt').value=="-1")&&(fm.all('InhosExempt').value==""||fm.all('InhosExempt').value=="-1")&&(fm.all('ClinicExempt').value==""||fm.all('ClinicExempt').value=="-1")&&(fm.all('ClinicClaimRate').value==""||fm.all('ClinicClaimRate').value=="-1")&&(fm.all('InhosClaimRate').value==""||fm.all('InhosClaimRate').value=="-1")&&(fm.all('UnifyClaimRate').value==""||fm.all('UnifyClaimRate').value=="-1"))
	  {
	       	  alert("����дͳһ������ͳһ�����������סԺ������������סԺ�⸶�����������⸶����");
	       	  return false;
	  }
	}
	else
	{
	   if(fm.all('RiskCode').value=='110'||fm.all('RiskCode').value=='106')
	   {

	       if ((fm.all('Exempt').value!=""&&fm.all('Exempt').value!="-1")&&(fm.all('UnifyClaimRate').value==""||fm.all('UnifyClaimRate').value=="-1"))
         {  
         	  alert("��Ϊ��д��ͳһ��������дͳһ�⸶������������ע���Ƿ��Ѿ���סԺ������������סԺ�⸶�����������⸶����ɾȥ");
	       	  return false;  	  
         }
         
         if ((fm.all('UnifyClaimRate').value!=""&&fm.all('UnifyClaimRate').value!="-1")&&(fm.all('Exempt').value==""||fm.all('Exempt').value=="-1"))
         {
         	  alert("��Ϊ��д��ͳһ�⸶����������дͳһ����������ע���Ƿ��Ѿ���סԺ������������סԺ�⸶�����������⸶����ɾȥ");
	       	  return false;  	  
         }
         
         if ((fm.all('InhosExempt').value!=""&&fm.all('InhosExempt').value!="-1")&&(fm.all('ClinicExempt').value==""||fm.all('ClinicExempt').value=="-1"||fm.all('ClinicClaimRate').value==""||fm.all('ClinicClaimRate').value=="-1"||fm.all('InhosClaimRate').value==""||fm.all('InhosClaimRate').value=="-1"))
         {
         	  alert("��Ϊ��д��סԺ��������д�������������⸶������סԺ�⸶������������ע���Ƿ��Ѿ���ͳһ������ͳһ�⸶����ɾȥ");
	       	  return false;  	  
         }
         if ((fm.all('ClinicExempt').value!=""&&fm.all('ClinicExempt').value!="-1")&&(fm.all('InhosExempt').value==""||fm.all('InhosExempt').value=="-1"||fm.all('ClinicClaimRate').value==""||fm.all('ClinicClaimRate').value=="-1"||fm.all('InhosClaimRate').value==""||fm.all('InhosClaimRate').value=="-1"))
         {
         	  alert("��Ϊ��д��������������дסԺ���������⸶������סԺ�⸶������������ע���Ƿ��Ѿ���ͳһ������ͳһ�⸶����ɾȥ");
	       	  return false;  	  
         }
         if ((fm.all('ClinicClaimRate').value!=""&&fm.all('ClinicClaimRate').value!="-1")&&(fm.all('InhosExempt').value==""||fm.all('InhosExempt').value=="-1"||fm.all('ClinicClaimRate').value==""||fm.all('ClinicClaimRate').value=="-1"||fm.all('InhosClaimRate').value==""||fm.all('InhosClaimRate').value=="-1"))
         {
         	  alert("��Ϊ��д�������⸶����������дסԺ���������⸶������סԺ�⸶������");
	       	  return false;  	  
         }
         if ((fm.all('InhosClaimRate').value!=""&&fm.all('InhosClaimRate').value!="-1")&&(fm.all('InhosExempt').value==""||fm.all('InhosExempt').value=="-1"||fm.all('ClinicClaimRate').value==""||fm.all('ClinicClaimRate').value=="-1"||fm.all('InhosClaimRate').value==""||fm.all('InhosClaimRate').value=="-1"))
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
	if (fm.all('DutyCode').value=="")
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
  fm.all('mOperate').value = "INSERT||MAIN";
  if (fm.all('mOperate').value == "INSERT||MAIN")
	{
		if (!confirm('����'+fm.all('GrpContNo').value+'�µ�����'+fm.all('RiskCode').value+'����������Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ���'))
		{
			return false;
		}
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.submit(); //�ύ
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
    
    fm.all("RiskCode").CodeData=tCodeData;
	
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
		fm.all('DutyCode').value = "";
		return false;
	}
	else
	{
		fm.all('DutyCode').value = CtrlClaimCodeGrid.getRowColData(line-1,1);
	}
	fm.all('mOperate').value = "DELETE||MAIN";
	fm.all('levelflag').value = "1";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.submit(); //�ύ
	
}

//�����ύ���޸ģ�
function UptContClick(){
	fm.all('mOperate').value = "UPDATE||MAIN";
	fm.all('levelflag').value = "1";
	if (!beforeSubmit()){
		return false;
	}
	 if (!alertme())
  {
  	return false;
  }
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.submit(); //�ύ
}

//����У��
function beforeSubmit(){
	if (fm.all('GrpContNo').value == ""){
			alert("���������屣����!");
			fm.all('GrpContNo').focus();
			return false;
		}
			if (fm.all('CtrlProp').value == ""){
			alert("�������������!");
			fm.all('CtrlProp').focus();
			return false;
		}
		
	if (fm.all('mOperate').value == "UPDATE||MAIN"){
		if (fm.all('GrpContNo').value == ""){
			alert("���������屣���ţ�");
			fm.all('GrpContNo').focus();
			return false;
		}
	}
	if (fm.all('mOperate').value == "DELETE||MAIN"){
	   if (CtrlClaimCodeGrid.mulLineCount == 0 && DutyCtrllevelCodeGrid.mulLineCount == 0){
		    alert("������������!");
		       return false;
	    }
	   var tCtrlBatchNo = fm.all('CtrlBatchNo').value ;
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
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initCtrlClaimCodeGrid();
	    tSearch = 0;
	    QueryCount = 0;
	    
	    if(fm.all('levelflag').value=='1')
	    {
	    	QueryForm();
	    	
	    }
	    
	    
	}
	
	initForm();
	
	fm.all('mOperate').value = "";
	fm.all('levelflag').value = "";


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
	
	  if (fm.all("GrpContNo").value==null||fm.all("GrpContNo").value=="")
	  {
	  	alert("���������屣���ţ�");
	  	return false;
	  }
	  
	  
	  //var Result = easyExecSql("select grpcontno from lcgrpcont where grpcontno='" + fm.all("GrpContNo").value + "'", 1, 0);
	  initCtrlClaimCodeGrid();
	  //initDutyCtrllevelCodeGrid();
	 // var tContPlanCode=fm.all("ContPlanCode").value;
	  //alert(tContPlanCode);
	  /*if (tContPlanCode==null||tContPlanCode=="")
	  {
	  	tContPlanCode="0";
	  }*/
    var strSQL="";
  /*  if (fm.all("InsuredNo").value!="" && fm.all("InsuredNo").value!= null){
    	strSQL = "select DutyCode,(case to_char(ObserveDate) when '-1' then '' else to_char(ObserveDate) end),(case to_char(Exempt) when '-1' then '' else to_char(Exempt) end),(case to_char(InhosExempt) when '-1' then '' else to_char(InhosExempt) end),(case to_char(ClinicExempt) when '-1' then '' else to_char(ClinicExempt) end),(case to_char(ExemptDate) when '-1' then '' else to_char(ExemptDate) end),(case to_char(InhosLimit) when '-1' then '' else to_char(InhosLimit) end),(case to_char(ClinicClaimRate) when '-1' then '' else to_char(ClinicClaimRate) end),(case to_char(InhosClaimRate) when '-1' then '' else to_char(InhosClaimRate) end),(case to_char(UnifyClaimRate) when '-1' then '' else to_char(UnifyClaimRate) end),(case to_char(TotalLimit) when '-1' then '' else to_char(TotalLimit) end) from LLDutyCtrl where GrpContNo='"	+Result[0][0] +"' and ContPlanCode='"+ tContPlanCode 
    	+ "' and contno in (select contno from lcpol where Insuredno='" + fm.all("InsuredNo").value + "')";
    }
    else{
	   strSQL = "select DutyCode,(case to_char(ObserveDate) when '-1' then '' else to_char(ObserveDate) end),(case to_char(Exempt) when '-1' then '' else to_char(Exempt) end),(case to_char(InhosExempt) when '-1' then '' else to_char(InhosExempt) end),(case to_char(ClinicExempt) when '-1' then '' else to_char(ClinicExempt) end),(case to_char(ExemptDate) when '-1' then '' else to_char(ExemptDate) end),(case to_char(InhosLimit) when '-1' then '' else to_char(InhosLimit) end),(case to_char(ClinicClaimRate) when '-1' then '' else to_char(ClinicClaimRate) end),(case to_char(InhosClaimRate) when '-1' then '' else to_char(InhosClaimRate) end),(case to_char(UnifyClaimRate) when '-1' then '' else to_char(UnifyClaimRate) end),(case to_char(TotalLimit) when '-1' then '' else to_char(TotalLimit) end)��from LLDutyCtrl where GrpContNo='"	+Result[0][0] +"' and ContPlanCode='"+ tContPlanCode+"' and riskCode ='"+fm.all("RiskCode").value+"'";
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
	         + getWherePart('grpcontno', 'GrpContNo')
	         + getWherePart('occupationtype', 'OccupationType')
	         + getWherePart('ctrlprop', 'CtrlProp')
	         + getWherePart('contplancode', 'ContPlanCode')
	         + getWherePart('riskcode', 'RiskCode')
	         + getWherePart('dutycode', 'DutyCode')
	         + getWherePart('getdutycode', 'GetDutyCode');
	  
	  turnPage.queryModal(strSQL, CtrlClaimCodeGrid); 
}

