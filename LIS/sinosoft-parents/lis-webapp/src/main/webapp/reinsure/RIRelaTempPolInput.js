/*********************************************************************
 *  �������ƣ�TempReInAnswerInput.js
 *  �����ܣ��ٱ��ظ�
 *  �������ڣ�2006-11-30 
 *  ������  ��zhang bin
 *  ����ֵ��  ��
 *  ���¼�¼��  ������    ��������     ����ԭ��/����
 *********************************************************************
 */

var arrResult1 = new Array();
var arrResult2 = new Array();
var arrResult3 = new Array();
var arrResult4 = new Array();
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var temp = new Array();
var mOperate="";
var ImportPath;
var oldDataSet ; 
var InputFlag="0"; 
var tSearchFlag="0";
var mAppFlag = "";
window.onfocus=myonfocus;

var mContPlanCode = "";
var mRiskCode = "";
var mDutyCode = "";
var mInsuredNo = "";
var mInsuredName = "";

/**
* ��ʾ���͹������ٷ���������
*/
function showTempGrp(){
	var typeRadio="";
	for(var i = 0; i <fm.StateRadio.length; i++)
	{
		if(fm.StateRadio[i].checked)
		{
			typeRadio=fm.StateRadio[i].value;
			break;
		}
	}
	if(fm.OperateType.value=='01'){//�����µ�
		if(typeRadio=='1'){ //03-�ٷִ���
			divRelaDel.style.display='';
			divTable1.style.display='';
		}else if(typeRadio=='2'){ //04-�������
			divRelaDel.style.display='none';
			divTable1.style.display='none';
		}else{
			myAlert(""+"��֪���Ĵ�������"+"");
			return false;
		}
		var mSQL = "";
		mSQL = " select x.X1,x.X2,x.X3,(case x.X4 when '01' then '"+"�ܹ�˾���"+"' when '02' then '"+"�ٱ����"+"' when '03' then '"+"�ٷִ���"+"' when '04' then '"+"������"+"' end),x.X4, (case x.X5 when '1' then '"+"��ǩ��"+"' else '"+"δǩ��"+"' end),x.X5,x.X6 from ( select (select max(b.PrtNo) from lccont b where trim(b.ProposalContNo)=trim(a.ProposalContNo)) X1,(select max(b.ProposalContNo) from lccont b where trim(b.ProposalContNo)=trim(a.ProposalContNo)) X2, (select max(b.ContNo) from lccont b where trim(b.ProposalContNo)=trim(a.ProposalContNo)) X3, a.State X4, (select appflag from lccont where trim(ProposalContNo)=trim(a.ProposalContNo)) X5,a.serialno X6 From RIGrpState a where 1=1  and ProposalGrpContNo='000000'  and a.exetype='01'  ";
		if(typeRadio=='1'){ //03-�ٷִ���
			mSQL = mSQL + " and a.state='03' ";
		}
		if(typeRadio=='2'){ //04-�������
			mSQL = mSQL + " and a.state='04' ";
		}
		mSQL = mSQL + " ) x order by X6 desc " ;
		turnPage1.queryModal(mSQL, GrpTempInsuListGrid); 
	}else{ //�����µ�
		if(typeRadio=='1'){ //03-�ٷִ���
			
		}else if(typeRadio=='2'){ //04-�������
			
		}else{
			myAlert(""+"��֪���Ĵ�������"+"");
			return false;
		}
		var mSQL = "";
		mSQL = " select x.X1,x.X2,x.X3,(case x.X4 when '01' then '"+"�ܹ�˾���"+"' when '02' then '"+"�ٱ����"+"' when '03' then '"+"�ٷִ���"+"' when '04' then '"+"������"+"' end),x.X4,(case x.X5 when '1' then '"+"��ǩ��"+"' else '"+"δǩ��"+"' end),x.X5,x.X6 from (select (select PrtNo from lcgrpcont where trim(ProposalGrpContNo)=trim(a.ProposalGrpContNo)) X1,a.ProposalGrpContNo X2,(select GrpContNo from lcgrpcont where trim(a.proposalgrpcontno)=trim(proposalgrpcontno)) X3,a.State X4,(select appflag from lcgrpcont where trim(ProposalGrpContNo)=trim(a.ProposalGrpContNo)) X5,a.serialno X6 From RIGrpState a where 1=1 and a.ProposalContNo='000000'  and a.exetype='05'  " ;
		if(typeRadio=='1'){
			mSQL = mSQL + " and a.state='03' ";
		}
		if(typeRadio=='2'){
			mSQL = mSQL + " and a.state='04' ";
		}
		mSQL = mSQL + " ) x order by X6 desc " ; 
		turnPage1.queryModal(mSQL, GrpTempInsuListGrid); 
	}
	SearchResultGrid.clearData();
	RelaListGrid.clearData();
	IndTempListGrid.clearData();
	IndRelaListGrid.clearData();
}


//�������б���ѡ���ѯ��Ϣ
function QueryPolInfo(){
	if(fm.OperateType.value=='01'){
		tSel=GrpTempInsuListGrid.getSelNo();
		fm.OperateNo.value = GrpTempInsuListGrid.getRowColData(tSel-1,2);
		fm.ContNo.value = GrpTempInsuListGrid.getRowColData(tSel-1,3);
		fm.SerialNo.value = GrpTempInsuListGrid.getRowColData(tSel-1,8);
		
		if(fm.DeTailFlag.value=='1'){ //������
			var strSQL = " select (select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno) A1, a.riskcode A2,'000000' A3, a.Mult A4, a.Prem A5, a.Amnt A6, a.RiskAmnt A7, a.AccAmnt A8, a.ProposalNo A9,decode(b.State ,'00','"+"�Զ��ֱ�"+"','01','"+"�����ٷ�����"+"','02','"+"���ٷ����"+"','03','"+"�ٷ�"+"','04','"+"�ٷ�δʵ��"+"')A10, b.State A11, b.uwconclusion  A12, a.insuredno A13 from ripolindview a , RIDutyState b  where b.proposalgrpcontno='000000' and b.dutycode = '000000' and b.state = '02' and b.proposalno=a.proposalno and a.ProposalContNo='"+fm.OperateNo.value+"' ";
			turnPage4.queryModal(strSQL, IndTempListGrid);
			//��ʾ�ѹ��������α���
			strSQL = " select (select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno) A1, a.riskcode A2, '000000' A3,a.Mult A4, a.Prem A5,a.Amnt A6, a.RiskAmnt A7,a.AccAmnt A8, a.ProposalNo A9, decode(b.State ,'00','"+"�Զ��ֱ�"+"','01','"+"�����ٷ�����"+"','02','"+"���ٷ����"+"','03','"+"�ٷ�"+"','04','"+"�ٷ�δʵ��"+"')A10, b.State A11, b.uwconclusion A12, a.insuredno A13,(select c.RIPreceptNo from RITempContLink c where c.dutycode = '000000' and c.ProposalNo = a.ProposalNo) A14 from ripolindview a,RIDutyState b where  b.ProposalNo = a.proposalNo and b.DutyCode = '000000' and b.state = '03' and a.ContNo = '"+fm.ContNo.value+"' ";
			turnPage5.queryModal(strSQL,IndRelaListGrid);
			
		}
		else{ //������
//			strSQL = " select a.contNo from LCCont a where a.proposalcontno='"+fm.OperateNo.value+"'";
//			var arrResult = easyExecSql(strSQL);
//			fm.ContNo.value = arrResult;
//			
//			var strSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,x.A9,(case when x.A10='00' then '�Զ��ֱ�' when x.A10='01' then '�����ٷ�����' when x.A10='02' then '���ٷ����' when x.A10='03' then '�ٷ�' when x.A10='04' then '�ٷ�δʵ��' end),x.A10,x.A11,x.A12 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Mult A4,a.Prem A5,a.Amnt A6,a.RiskAmnt A7,a.AccAmnt A8,a.ProposalNo A9,(select trim(b.State) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)=trim(a.dutycode) ) A10,(select trim(b.uwconclusion) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)=trim(a.dutycode)) A11,a.insuredno A12 from ripoldutyindview a where trim(a.ProposalContNo)='"+fm.OperateNo.value+"' and exists (select * from ridutystate b where trim(b.ProposalNo)=trim(a.proposalNo) and trim(b.DutyCode)=trim(a.dutycode) and trim(b.state)='02')) x "
//				;
//			turnPage4.queryModal(strSQL, IndTempListGrid);
//			
//			//��ʾ�ѹ��������α���
//			strSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,x.A9,(case when x.A10='00' then '�Զ��ֱ�' when x.A10='01' then '�����ٷ�����' when x.A10='02' then '���ٷ����' when x.A10='03' then '�ٷ�' when x.A10='04' then '�ٷ�δʵ��' end),x.A10,x.A11,x.A12,x.A13 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Mult A4,a.Prem A5,a.Amnt A6,a.RiskAmnt A7,a.AccAmnt A8,a.ProposalNo A9,(select trim(b.State) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A10,(select trim(b.uwconclusion) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A11,a.insuredno A12,(select b.RIPreceptNo from RITempContLink b where b.dutycode=a.dutycode and b.ProposalNo=a.ProposalNo) A13 from ripoldutyindview a where trim(a.ContNo)='"+fm.ContNo.value+"' and exists (select * from ridutystate b where trim(b.ProposalNo)=trim(a.proposalNo) and trim(b.DutyCode)=trim(a.dutycode) and trim(b.state)='03')) x ";
//			turnPage5.queryModal(strSQL,IndRelaListGrid);
			strSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,x.A9,(case when x.A10='00' then '"+"�Զ��ֱ�"+"' when x.A10='01' then '"+"�����ٷ�����"+"' when x.A10='02' then '"+"���ٷ����"+"' when x.A10='03' then '"+"�ٷ�"+"' when x.A10='04' then '"+"�ٷ�δʵ��"+"' end),x.A10,x.A11,x.A12,x.A13 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Mult A4,a.Prem A5,a.Amnt A6,a.RiskAmnt A7,a.AccAmnt A8,a.ProposalNo A9,(select trim(b.State) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A10,(select trim(b.uwconclusion) from RIDutyState b where trim(b.proposalno)=trim(a.proposalno) and trim(b.proposalgrpcontno)='000000' and trim(b.dutycode)= trim(a.dutycode)) A11,a.insuredno A12,(select b.RIPreceptNo from RITempContLink b where b.dutycode=a.dutycode and b.ProposalNo=a.ProposalNo) A13 from ripoldutyindview a where trim(a.polno)='"+fm.OperateNo.value+"' and exists (select * from ridutystate b where trim(b.ProposalNo)=trim(a.proposalNo) and trim(b.DutyCode)=trim(a.dutycode) and trim(b.state)='03')) x ";
			turnPage5.queryModal(strSQL,IndRelaListGrid);		
		}
	}else if(fm.OperateType.value=='05'){
		strSQL = " select a.GrpContNo from LCGrpCont a where a.proposalgrpcontno='"+fm.OperateNo.value+"'";
		var arrResult = easyExecSql(strSQL);
		fm.ContNo.value = arrResult;
		
		for(var i=0;i<GrpTempInsuListGrid.mulLineCount;i++){
			if(trim(GrpTempInsuListGrid.getRowColData(i,2))==trim(fm.ContNo.value)){
				tSel=i+1;
				break;
			}
		}
	}else{
		tSel=GrpTempInsuListGrid.getSelNo();
		if(tSel==0){
			return false;
		}
	}
	if(fm.OperateType.value==null||fm.OperateType.value==""||fm.OperateType.value=="null"){
		var tGrpContNo=GrpTempInsuListGrid.getRowColData(tSel-1,3);
		var tProPosalGrpNo=GrpTempInsuListGrid.getRowColData(tSel-1,2);
		
		fm.GrpContNo.value=tGrpContNo;
		fm.ContNo.value=tProPosalGrpNo;
		//����״̬
		mAppFlag = GrpTempInsuListGrid.getRowColData(tSel-1,7);
	}else if(fm.OperateType.value=="05"){
		var strSQL = " select a.grpcontno,a.appflag from lcgrpcont a where a.ProPosalGrpContNo='"+fm.ContNo.value+"' ";
		var arrResult = easyExecSql(strSQL);
		fm.GrpContNo.value=arrResult[0][0];
		//����״̬
		mAppFlag = arrResult[0][1];
	}
}

//ҳ���ʼ����Ϣ
function QueryPagePolInfo(){
	var  strSQL="select b.Contno A1,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno) A2, b.RiskCode A3,c.dutycode A4,c.Prem A5,c.Amnt A6,c.RiskAmnt A7,a.ProposalNo A8,decode(a.State,'02','"+"���ٷ����"+"','03','"+"�ٷ�"+"','04','"+"�ٷִ������"+"') A9,b.insuredNo A10 from RIDutyState a, lcpol b, lcduty c where a.Proposalno = b.polno and a.Dutycode = c.Dutycode and a.Proposalno = c.polno and a.State = '02'and  a.proposalno ='"+fm.RIPolno.value+"' ";
	turnPage4.queryModal(strSQL,IndTempListGrid);
		strSQL="select b.Contno A1,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno) A2, b.RiskCode A3,c.dutycode A4,c.Prem A5,c.Amnt A6,c.RiskAmnt A7,a.ProposalNo A8,decode(a.State,'02','"+"���ٷ����"+"','03','"+"�ٷ�"+"','04','"+"�ٷִ������"+"') A9,''A10,(select RIPreceptNo from  RITempContLink where Proposalno=a.Proposalno and Dutycode =a.Dutycode )A11 from RIDutyState a, lcpol b, lcduty c where a.Proposalno = b.polno and a.Dutycode = c.Dutycode and a.Proposalno = c.polno and a.State = '03'and  a.proposalno ='"+fm.RIPolno.value+"'";
	turnPage5.queryModal(strSQL,IndRelaListGrid);
}

function ResetForm(){
	fm.ContPlanCode.value = "";
	fm.RiskCode.value = "";
	fm.DutyCode.value = "";
	fm.ContPlanCodeName.value = "";
	fm.RiskCodeName.value = "";
	fm.DutyCodeName.value = "";
	fm.InsuredNo.value = "";
	fm.InsuredName.value = "";
	tSearchFlag ="0"; //����Ѳ�ѯ���
	SearchResultGrid.clearData();
}
		
function afterSubmit(FlagStr, content ){ 
  showInfo.close();
  if (FlagStr == "Fail" ){             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else{ 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    location.reload(true);
  }
}
   
function clearData(){ 
	if(InputFlag=="0"){
		fm.Remark.value=""; 
	}
	InputFlag="1"; 
}

function TempConclusionAll(){
	if(mAppFlag!="1"){
		myAlert(""+"����δǩ���������ٷֽ���"+"");
		return false;
	}
	if (!confirm(""+"Ҫ�����в�ѯ����������ٷֺ�ͬ��"+"")) 
	{
    return false;
	}
	if(!verify1()){
		return false;
	}
	if(!VerifySearch()){
		myAlert(""+"��ѯ�����Ѿ��޸ģ������²�ѯ�����½��ۣ�"+"");
		return false;
	}
	var showStr=""+"���ڱ�������......"+"";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'1'-�˱��ٷֽ��ۣ�'0'-�ٱ��ٷֽ���
	fm.action = "./RIRelaTempPolSave.jsp?CONOPETYPE=01";
  fm.submit();
}

function VerifySearch(){
  if(mContPlanCode != fm.ContPlanCode.value) return false;
	if(mRiskCode != fm.RiskCode.value) return false;
	if(mDutyCode != fm.DutyCode.value) return false;
	if(mInsuredNo != fm.InsuredNo.value) return false;
	if(mInsuredName != fm.InsuredName.value) return false;
	
	return true;
}

function TempConclusionSel(){
		var Count=IndTempListGrid.mulLineCount; 
		var chkFlag=false; 
		for (var i=0;i<Count;i++){
			if(IndTempListGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		
		if (chkFlag==false){
			myAlert(""+"��ѡ�б�����Ϣ"+"");
			return false;
		}
		var showStr=""+"���ڱ�������......"+""; 
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'1'-�˱��ٷֽ��ۣ�'0'-�ٱ��ٷֽ���
		fm.action = "./RIRelaTempPolSave.jsp?CONOPETYPE=02&ContType=1";
        fm.submit(); 
}

function verify1(){
	if(tSearchFlag!="1"){
		myAlert(""+"���ѯ���ٹ������α���"+"");
		return false;
	}
	mSQL = " select * from RITempContLink a where a.RIContNo='"+fm.RIContNo.value+"' and a.RIPreceptNo='"+fm.RIPreceptNo.value+"' and a.ProposalGrpContNo='"+fm.ContNo.value+"' and a.RelaType='"+fm.RelaMode.value+"' "
	+ getWherePart("a.ContPlanCode","ContPlanCode")
	+ getWherePart("a.riskcode","RiskCode")
	+ getWherePart("b.DutyCode","DutyCode")
	+ getWherePart("a.insuredno","InsuredNo");
	if(fm.InsuredName.value!=""&&fm.InsuredName.value!=null){
  	mSQL = mSQL + " and exists (select * from lcinsured where insuredno=a.insuredno and insuredName like '%"+fm.InsuredName.value+"%')";
  }
  var arrResult = easyExecSql(mSQL);
  if(arrResult!=""&&arrResult!=null){
  	myAlert(" "+"���ֻ�ȫ�������Ѿ��������ٷֺ�ͬ�����ܽ����ٹ���"+" ");
  	return false;
  }
  
	return true;
}

function verify2(){
	if(fm.ContNo.value==''||fm.ContNo.value==null){
		myAlert(""+"�����������ٷ������б���ѡ�����屣��!"+"");
		return false;
	}
	if(fm.ContPlanMode.value==''||fm.ContPlanMode.value==null){
		myAlert(""+"���ϼƻ�����Ϊ��"+"");
		return false;
	}
	if(fm.DutyCode1.value==''||fm.DutyCode1.value==null){
		myAlert(""+"���α��벻��Ϊ��"+"");
		return false;
	}
	return true;
}

function verify3(){
	if(fm.ContNo.value==''||fm.ContNo.value==null){
		myAlert(""+"�����������ٷ������б���ѡ�����屣��!"+"");
		return false;
	}
	if(fm.DutyCode1.value==''||fm.DutyCode1.value==null){
		myAlert(""+"���α��벻��Ϊ��"+"");
		return false;
	}
	return true;
}

/**
*��ѯ�ŵ�
*/
function QueryGrpNewAudit(){
	var tSel = GrpTempInsuListGrid.getSelNo();
 	var tGrpContNo = GrpTempInsuListGrid.getRowColData(tSel-1,2);
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'"+"�˱�����"+"',(select case State when '00' then '"+"���ظ�"+"' when '01' then '"+"�ѻظ�"+"' else '"+"���"+"' end from LCReInsurTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and trim(a.AuditType)=trim(AuditType) and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and trim(AuditCode)=trim(AuditCode) and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) "
	+ " from LCReInsurUWIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000' and AuditCode='000000' and AuditAffixCode='000000' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'"+"�ٱ��ظ�"+"',(select case State when '00' then '"+"���ظ�"+"' when '01' then '"+"�ѻظ�"+"' else '"+"���"+"' end from LCReInsurTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and a.AuditType=AuditType and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and AuditCode=AuditCode and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) " 
	+ " from LCReInsurIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000'  and AuditCode='000000' and AuditAffixCode='000000') order by uwno desc"
	;	
	turnPage6.queryModal(mSql,ReInsureAuditGrid);
}

function DeleteRelaList()
{
		//����
		var Count=IndRelaListGrid.mulLineCount; 
		var chkFlag=false; 
		for (var i=0;i<Count;i++){
			if(IndRelaListGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"���ڹ����б�ѡ��Ҫɾ���ļ�¼"+"");
			return false;
		}
		var showStr=""+"����ɾ������......"+"";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'1'-�˱��ٷֽ��ۣ�'0'-�ٱ��ٷֽ���
		fm.action = "./RIRelaTempPolSave.jsp?CONOPETYPE=03&ContType=1"; 
		fm.submit(); 
}

function DeleteRelaAll(){
		//����
		var showStr=""+"����ɾ������......"+"";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'1'-�˱��ٷֽ��ۣ�'0'-�ٱ��ٷֽ���
		fm.action = "./RIRelaTempPolSave.jsp?CONOPETYPE=04&ContType=1"; 
		fm.submit(); 
}

function afterCodeSelect( cCodeName, Field ) 
{
	if(Field.value=='01'){
		divButton1.style.display='';
		divSearch.style.display='';
		divContPlanCodeName.style.display='none';
		divDutyCodeName.style.display='none';
		fm.DutyCode1.style.display='none';
		fm.ContPlanMode.style.display='none';
		divRelaAll.style.display='';
		
	}else if(Field.value=='02'){
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='';
		fm.ContPlanMode.style.display='';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		divRelaAll.style.display='none';
		
	}else if(Field.value=='03'){
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='none';
		fm.ContPlanMode.style.display='none';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		divRelaAll.style.display='none';
	}
}
