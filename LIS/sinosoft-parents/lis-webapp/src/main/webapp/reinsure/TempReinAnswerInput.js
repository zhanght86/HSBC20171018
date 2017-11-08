/*********************************************************************
 *  程序名称：TempReInAnswerInput.js
 *  程序功能：再保回复
 *  创建日期：2006-11-30 
 *  创建人  ：zhang bin
 *  返回值：  无
 *  更新记录：  更新人    更新日期     更新原因/内容
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
window.onfocus=myonfocus;

//选择业务类型
function showTask(){
	if(fmInfo.ContType.value=="1"){ //个险
		if(fm.AuditType.value=="01"){
			QueryIndRiskTask();
			initRiskInfoGrid();
		}
		if(fm.AuditType.value=="03"){
			QueryIndEdorTask();
			initRiskInfoGrid();
		}
		if(fm.AuditType.value=="04"){
			QueryIndClaimTask();
			initRiskInfoGrid();
		}
	}else{ //团险
		if(fm.AuditType.value=="01"){
			QueryGrpRiskTask();
		}
		if(fm.AuditType.value=="03"){
			QueryGrpEdorTask();
		}
		if(fm.AuditType.value=="04"){
			QueryGrpClaimTask();
		}
	}
}

/**
* 显示发送过来的临分申请
*/
//个险新单
function QueryIndRiskTask(){
	var mSQL="";
	initTaskLiskGrid();
	if(fmInfo.DeTailFlag.value=='1'){ //险种
		mSQL = " select (select distinct b.PrtNo from ripolindview b where a.proposalcontno=b.proposalcontno),a.proposalcontno,decode(a.State,'00','"+"待回复"+"','01','"+"已回复"+"','02','"+"已办结"+"','"+"未知类型"+"'),a.uwno from RIUWTask a where (a.ProposalGrpContNo)='000000' and a.audittype='01' and a.State<>'02' Order by a.SerialNo ";
	}else{//责任
		mSQL = " select (select distinct b.PrtNo from ripoldutyindview b where a.proposalcontno=b.proposalcontno),a.polno,decode(a.State,'00','"+"待回复"+"','01','"+"已回复"+"','02','"+"已办结"+"','"+"未知类型"+"'),a.uwno from RIUWTask a where (a.ProposalGrpContNo)='000000' and a.audittype='01' and a.State<>'02' Order by a.SerialNo ";
	}
	turnPage.queryModal(mSQL,TaskLiskGrid);
}
//个险保全
function QueryIndEdorTask(){
	initTaskLiskGrid();
	var mSQL = "  ";
	turnPage.queryModal(mSQL,TaskLiskGrid);
}
//个险理赔
function QueryIndClaimTask(){
	var mSQL = "";
	initTaskLiskGrid();
	if(fmInfo.DeTailFlag.value=='1'){ //险种
		mSQL = "select (select distinct b.PrtNo from ripolindview b where b.contno=(select distinct c.contno from Riclaimriskdetailview c where c.CaseNo=a.auditcode and rownum = 1)),a.proposalcontno, decode(a.State, '00','"+"待回复"+"','01','"+"已回复"+"','02','"+"已办结"+"', '"+"未知类型"+"'), a.uwno,(select c.caseno from RIGrpState c where c.serialno= a.serialno),a.SerialNo from RIUWTask a where (a.ProposalGrpContNo) = '000000' and a.audittype='04' and (a.State) <> '02' Order by a.SerialNo";
	}else{ //责任
		mSQL = "select (select distinct b.PrtNo from ripolindview b where b.contno=(select distinct c.contno from Riclaimriskdetailview c where c.CaseNo=a.auditcode and rownum = 1)), a.proposalcontno, decode(a.State, '00','"+"待回复"+"','01','"+"已回复"+"','02','"+"已办结"+"', '"+"未知类型"+"'), a.uwno,(select c.caseno from RIGrpState c where c.serialno= a.serialno),a.SerialNo from RIUWTask a where (a.ProposalGrpContNo) = '000000' and a.audittype='04' and (a.State) <> '02' Order by a.SerialNo ";
	}

	turnPage.queryModal(mSQL,TaskLiskGrid);
}

//团险新单
function QueryGrpRiskTask(){
	initTaskLiskGrid();
	var mSQL = " select (select distinct b.PrtNo from ripoldutygrpview b where trim(a.ProposalGrpContNo)=trim(b.ProposalGrpContNo)),(select distinct b.proposalgrpcontno from ripoldutygrpview b where trim(a.ProposalGrpContNo)=trim(b.ProposalGrpContNo)),decode(a.State,'00','"+"待回复"+"','01','"+"已回复"+"','02','"+"已办结"+"','"+"未知类型"+"'),a.uwno from RIUWTask a where (a.ProposalContNo)='000000' and (a.State)<>'02' Order by a.SerialNo ";
	turnPage.queryModal(mSQL,TaskLiskGrid);
}
//团险保全
function QueryGrpEdorTask(){
	initTaskLiskGrid();
	var mSQL = "  ";
	turnPage.queryModal(mSQL,TaskLiskGrid);
}
//团险理赔
function QueryGrpClaimTask(){
	initTaskLiskGrid();
	var mSQL = "  ";
	turnPage.queryModal(mSQL,TaskLiskGrid);
}

//查询任务下的相信信息
function QueryTaskInfo(){
	if(fmInfo.ContType.value=="1"){
		if(fm.AuditType.value=="01"){
			QueryIndRiskTaskInfo();
		}
		if(fm.AuditType.value=="03"){
			QueryIndEdorTaskInfo();
		}
		if(fm.AuditType.value=="04"){
			QueryIndClaimTaskInfo();
		}
	}else{
		if(fm.AuditType.value=="01"){
			QueryGrpRiskTaskInfo();
		}
		if(fm.AuditType.value=="03"){
			QueryGrpEdorTaskInfo();
		}
		if(fm.AuditType.value=="04"){
			QueryGrpClaimTaskInfo();
		}
	}
}
//查询个险新单相信信息
function QueryIndRiskTaskInfo(){
	try{
		var tSel = TaskLiskGrid.getSelNo();
		
		var mSQL ="";
		//自核结果查询
 		if(fmInfo.DeTailFlag.value=='1'){ //险种
			mSQL = " select x.A1,x.A2,x.A3,(select b.riskname from lmrisk b where b.riskcode=x.A3),'',x.A5 from ( select a.insuredno A1,a.insuredname A2,(select b.RiskCode from ripolindview b where b.polno=a.polno ) A3,a.uwerror A5 from lcuwerror a where exists (select 'X' from lmuw b where b.uwcode = a.uwrulecode and b.uwtype='LF') and a.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' and a.uwno= (select max(c.uwno) from lcuwerror c where exists (select 'X' from lmuw b where b.uwcode = c.uwrulecode and b.uwtype='LF') and c.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' ) order by a.polno,uwno) x "
 			;	
		}else{//责任
			mSQL = "  "
 			;
		}
		
		turnPage1.queryModal(mSQL,AuditInfoGrid);
		if(fmInfo.DeTailFlag.value='1'){ //险种
			mSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,decode(x.A7,'00' , '"+"自动分保"+"' ,'01' , '"+"满足临分条件"+"' ,'02' , '"+"待临分审核"+"' ,'03' , '"+"临时分保"+"' ,'04' , '"+"临分未实现"+"' ),x.A7 from (select a.insuredname A1,a.riskcode A2,'000000' A3,a.Amnt A4,a.RiskAmnt A5,a.AccAmnt A6,(select b.State from RIDutyState b where b.proposalno=a.proposalno and b.proposalgrpcontno='000000' and b.dutycode= '000000') A7 from ripolindview a where a.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' and exists (select 'X' from RIDutyState b where b.proposalno=a.ProposalNo and b.DutyCode='000000' and b.state='02')) x ";
		}else{ //责任
			mSQL = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,decode( x.A7='00' , '"+"自动分保"+"' ,'01' , '"+"满足临分条件"+"' ,'02' , '"+"待临分审核"+"' ,'03' , '"+"临时分保"+"' ,'04' , '"+"临分未实现"+"'),x.A7 from (select a.insuredname A1,a.riskcode A2,a.dutycode A3,a.Amnt A4,a.RiskAmnt A5,a.AccAmnt A6 from ripoldutyindview a where a.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' and exists (select 'X' from RIDutyState b where b.proposalno=a.ProposalNo and b.DutyCode=a.Dutycode and b.state='02')) x ";
		}
		turnPage2.queryModal(mSQL,RiskInfoGrid);
		//显示个险新单发送回复记录
		QueryReInsureAudit(); 
	}
   catch(ex){
     myAlert(ex);
   }
}
//查询个险保全详细信息
function QueryIndEdorTaskInfo(){
	//显示个险保全发送回复信息
	QueryEdorAudit(); 
}
//查询个险理赔详细信息
function QueryIndClaimTaskInfo(){
	try{
		var tSel = TaskLiskGrid.getSelNo();
		//  核保/核赔结果列表 
		//alert(fmInfo.DeTailFlag.value);
 		if(fmInfo.DeTailFlag.value=='1'){ //险种
			mSQL = " select (select b.InsuredNo from Riclaimriskdetailview b where b.caseno = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"' and b.riskcode=a.riskcode),(select b.InsuredName from Riclaimriskdetailview b where b.caseno = a.AuditCode and b.riskcode=a.riskcode),a.riskcode,(select b.riskname from lmrisk b where b.RiskCode=a.riskcode ),'',a.uwerror from RIGUWError a where a.CalItemType='14' and a.AuditCode='"+TaskLiskGrid.getRowColData(tSel-1,5)+"' "
 			;	
		}else{//责任
			mSQL = " select (select b.InsuredNo from Riclaimriskdetailview b where b.caseno = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"' and b.riskcode=a.riskcode),(select b.InsuredName from Riclaimriskdetailview b where b.caseno = a.AuditCode and b.riskcode=a.riskcode),a.riskcode,(select b.riskname from lmrisk b where b.RiskCode=a.riskcode ),'',a.uwerror from RIGUWError a where a.CalItemType='14' and a.AuditCode='"+TaskLiskGrid.getRowColData(tSel-1,5)+"' "
 			;
		}
 		turnPage1.queryModal(mSQL,AuditInfoGrid);

		//  任务信息 
		if(fmInfo.DeTailFlag.value='1'){ //险种
			mSQL = " select x.A1, x.A2, x.A3, x.A4, x.A5, x.A6, decode( x.A7 , '01' , '"+"通知限额"+"' ,'02' ,'"+"参与限额"+"' ,'03' ,'"+"非正常理赔"+"'), x.A7 from (select a.InsuredName A1, a.RiskCode A2, '000000' A3, (select c.Amnt from RIPolIndView c where c.PolNo = a.PolNo) A4, a.CaseNo A5,a.ClmGetMoney A6,(select trim(b.State)from RIClaimState b where b.caseno = a.CaseNo and b.riskcode = a.RiskCode and b.dutycode = '000000') A7 from RIClaimRiskDetailView a where a.CaseNo = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"' and exists (select 'X' from RIClaimState b where b.caseno = a.CaseNo and b.riskcode = a.RiskCode and b.DutyCode = '000000' )) x";
		}else{ //责任
			mSQL = " select x.A1, x.A2, x.A3, x.A4, x.A5, x.A6, decode( x.A7 , '01' , '"+"通知限额"+"' , '02' ,'"+"参与限额"+"','03', '"+"非正常理赔"+"'), x.A7 from (select a.InsuredName A1, a.RiskCode A2, a.DutyCode A3, (select c.Amnt from RIPolIndView c where c.PolNo = a.PolNo) A4, a.CaseNo A5,a.ClmGetMoney A6,(select trim(b.State)from RIClaimState b where b.caseno) = a.CaseNo and b.riskcode = '000000' and b.dutycode = a.DutyCode) A7 from RIClaimDutyDetailView a where a.CaseNo = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"' and exists (select 'X' from RIClaimState b where b.caseno = a.CaseNo and b.riskcode = '000000' and b.DutyCode = a.DutyCode )) x";
		}
		turnPage2.queryModal(mSQL,RiskInfoGrid);
		//显示核赔发送回复信息
		QueryClaimAudit();
	}catch(ex){
      myAlert(ex);
   }
  
}
//查询团险新单详细信息
function QueryGrpRiskTaskInfo(){
	//显示团险新单发送回复信息
	QueryGrpNewAudit();
}
//查询团险保全详细信息
function QueryGrpEdorTaskInfo(){
	//显示团险保全发送回复信息
	QueryGrpNewAudit();
}
//查询团险理赔详细信息
function QueryGrpClaimTaskInfo(){
	//显示核赔发送回复信息
	QueryClaimAudit();  
}

/**
*
*/
function QueryGrpNewInfo(){
	
	divGrpUWResult.style.display="";
 	divIndUWResult.style.display="";
 	
 	var tSel = GrpTempInsuListGrid.getSelNo();
 	var tProposalGrpContNo = GrpTempInsuListGrid.getRowColData(tSel-1,2);
 	var tGrpContNo = GrpTempInsuListGrid.getRowColData(tSel-1,3);
  mSQL = " select X.a1,X.a2,X.a3,(select RIContName from RIBarGainInfo where trim(RIContNo) = (select max(trim(RIContNo)) from RIPrecept where trim(RIPreceptNo)=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where trim(grpcontno)=trim(a.GrpContNo)) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select RIPreceptNo from RILMUW where trim(RuleCode)=trim(a.UWRuleCode)) a4,(select RuleName from RILMUW where trim(RuleCode)=trim(a.UWRuleCode)) a5,UWError a6 from RIGUWError a where trim(a.GrpContNo)='"+tGrpContNo+"' and trim(a.riskcode)='000000' ) X "
  ;
	turnPage3.queryModal(mSQL,GrpUWResultGrid);
	mSQL =" select X.a1,X.a2,X.a3,b1,b2,b3,b4,b5,b6,(select RIContName from RIBarGainInfo where trim(RIContNo) = (select max(trim(RIContNo)) from RIPrecept where trim(RIPreceptNo)=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where trim(grpcontno)=trim(a.GrpContNo)) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select contno from lcpol where trim(ProposalNo) = trim(a.ProposalNo)) b1,a.ProposalNo b2,a.InsuredName b3,a.InsuredNo b4,a.RiskCode b5,a.DutyCode b6,(select RIPreceptNo from RILMUW where trim(RuleCode)=trim(a.UWRuleCode)) a4,(select RuleName from RILMUW where trim(RuleCode)=trim(a.UWRuleCode)) a5,UWError a6 from RIGUWError a where trim(a.GrpContNo)='"+tGrpContNo+"' and a.riskcode <> '000000' ) X " 
  ;
	turnPage5.queryModal(mSQL,IndUWResultGrid);
	showTaskInfo();
	fm.GrpContNo.value=tGrpContNo;
	fm.ProposalGrpContNo.value=tProposalGrpContNo;
	ResetForm();
	fm.Remark.value=""+"在此录入回复意见"+""; 
	InputFlag="0";
}

/**
	*查询核保发起任务
	*/
 function QueryRiskInfo()
 { 	
 		divRiskInfo.style.display="";
 		divEdorInfo.style.display="none";
 		divClaimInfo.style.display="none";
	  mSQL =" select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)), a.dutycode, (select amnt from lcduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode)) , (select distinct prem from lcduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode)), (case state when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' when '02' then '"+"办结"+"' end),a.polno,a.uwno,a.state  "
	  +" from RIUWTask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from RIUWIdea where AuditType='01' group by polno,dutycode,auditcode,AuditAffixCode) b "
 		+" where trim(a.polno)=trim(b.pno) and trim(a.dutycode)=trim(b.dcode) and trim(a.auditcode)=trim(b.acode) and trim(a.auditaffixcode)=trim(b.aacode) and trim(a.uwno)=trim(b.maxno) and trim(a.audittype)='01'"
 		;
 		if(fmInfo.OpeFlag.value=="1"){
 			mSQL=mSQL+"  and trim(a.polno)='"+fmInfo.ProposalNo.value+"' and trim(a.dutycode)='"+fmInfo.DutyCode.value+"' ";
 		}
 		mSQL = mSQL + " order by a.ModifyDate desc ";
		turnPage1.queryModal(mSQL, RiskInfoGrid);
 }

function QueryEdorInfo(){
 		divRiskInfo.style.display="none";
 		divEdorInfo.style.display="";
 		divClaimInfo.style.display="none";
		mSQL="select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)), a.dutycode, a.auditcode ,AuditAffixCode ,(select amnt from lpduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(edorno)=trim(auditcode) and trim(edortype)=trim(AuditAffixCode)), (select distinct prem from lpduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(edorno)=trim(auditcode) and trim(edortype)=trim(AuditAffixCode)),(case state when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' when '02' then '"+"办结"+"' end),a.polno,a.uwno,a.state "
 		+ " from RIUWTask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from RIUWIdea where trim(AuditType)='03' group by polno,dutycode,auditcode,AuditAffixCode) b "
 		+ " where trim(a.polno)=trim(b.pno) and trim(a.dutycode)=trim(b.dcode) and trim(a.auditcode)=trim(b.acode) and trim(a.auditaffixcode)=trim(b.aacode) and trim(a.uwno)=trim(b.maxno) "
		+ " and trim(AuditType)='03' order by ModifyDate desc "
		turnPage2.queryModal(mSQL,EdorInfoGrid);
}

function QueryClaimInfo(){
	divRiskInfo.style.display="none";
 	divEdorInfo.style.display="none";
 	divClaimInfo.style.display="";
	mSQL="select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)),a.dutycode,a.auditcode ,(select sum(realpay) from llclaim where trim(caseno)=trim(a.auditcode) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) group by caseno,polno,dutycode),(case state when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' when '02' then '"+"办结"+"' end),a.polno,a.uwno,a.state "
	+ " from RIUWTask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from RIUWIdea where trim(AuditType)='04' group by polno,dutycode,auditcode,AuditAffixCode) b "
 	+ " where trim(a.polno)=trim(b.pno) and trim(a.dutycode)=trim(b.dcode) and trim(a.auditcode)=trim(b.acode) and trim(a.auditaffixcode)=trim(b.aacode) and trim(a.uwno)=trim(b.maxno) "
	+ " and trim(AuditType)='04' order by ModifyDate desc "
	;
	turnPage3.queryModal(mSQL,ClaimInfoGrid);
}
 /**
	*查询发送回复信息
	*/
 function QueryAnswerIdea()
 {
 	var tSel=ReInsureAuditGrid.getSelNo();
 	var saFlag=ReInsureAuditGrid.getRowColData(tSel-1,9);
 	if(saFlag=="1") //如果‘核保发送’
 	{
	 	var mSql="select uwidea from RIUWIdea where trim(polno)='"+ReInsureAuditGrid.getRowColData(tSel-1,8)+"'"
	 	+" and UWNo="+ReInsureAuditGrid.getRowColData(tSel-1,3)+""
	 	var arrResult=easyExecSql(mSql);
	 	if(arrResult!=null)
	 	{
	 		fm.SendAnswerRemark.value=arrResult[0];
	 	}
	}else
	{
		var mSql="select uwidea from RIAnwserIdea where trim(polno)='"+ReInsureAuditGrid.getRowColData(tSel-1,8)+"'"
	 	+" and UWNo="+ReInsureAuditGrid.getRowColData(tSel-1,3)+""
	 	var arrResult=easyExecSql(mSql);
	 	if(arrResult!=null)
	 	{
	 		fm.SendAnswerRemark.value=arrResult[0];
	 	}
	}
 }

function SendUWReInsure()
{
	var tsql;
	var arr;
	if(fm.AuditType.value==""||fm.AuditType.value==null){
		myAlert(""+"请选择再保审核类型！"+"");
		return false;
	}
	
	if(fmInfo.ContType.value=='1'){ //个险
		if(fm.AuditType.value=="01"){
			var tSel = TaskLiskGrid.getSelNo();
			if(tSel==null||tSel==0)
			{
				myAlert(""+"请选择险种保单信息!"+"");
				return false;
			}
			if(fmInfo.DeTailFlag.value=='1'){ //险种
				tSql = " select max(appflag) from ripolindview a  where a.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' ";
			}else{//责任
				tSql = " select max(appflag) from ripoldutyindview a  where a.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' ";
			}
			
			
			arr=easyExecSql(tSql); 
			if(arr==null){
				myAlert(""+"没有查询到保单信息"+"");
				return false;
			}
			//if(arr!="0"){
			//	alert("保单已签单，不能再保回复");
			//	return false;
			//}
			tsql ="select State from RIUWTASK b where b.serialno=(select max(a.SerialNo) from RIUWTask a where a.ProposalGrpContNo='000000' and a.ProposalContNo='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' and a.AuditType='01' )";
			
			arr=easyExecSql(tsql); 
			if(arr=='01'||arr=='02') //如果是已回复状态 
			{
	  	  myAlert(""+"该任务为已回复状态，不能再回复核保申请!"+"");
	  	   return false;
			} 
		}else if(fm.AuditType.value=="03"){
			
		}else if(fm.AuditType.value=="04"){ //核赔回复
			var tSel = TaskLiskGrid.getSelNo();
			if(tSel==null||tSel==0){
				myAlert(""+"请选择要回复的任务!"+"");
				return false;
			}
			if(fmInfo.DeTailFlag.value=='1'){ //险种
				tSql = " select caseno from RIClaimRiskDetailView a  where a.caseno='"+TaskLiskGrid.getRowColData(tSel-1,5)+"' ";
			}else{//责任
				tSql = " select caseno from RIClaimDutyDetailView a  where a.caseno='"+TaskLiskGrid.getRowColData(tSel-1,5)+"' ";
			}			
			arr=easyExecSql(tSql); 
			if(arr==null){
				myAlert(""+"没有查询到核赔信息"+"");
				return false;
			}
			tsql ="select State from RIUWTASK b where b.serialno= '"+TaskLiskGrid.getRowColData(tSel-1,6)+"'";
			arr=easyExecSql(tsql); 
			if(arr=='01'||arr=='02'){ //如果是已回复状态 			
	  	    	myAlert(""+"该任务已回复，不能再回复核保申请!"+"");
	  	    	return false;
			} 
		}	
	}else{ //团险 
		if(fm.AuditType.value=="01"){
			var tSel = GrpTempInsuListGrid.getSelNo();	
 			var tGrpContNo = GrpTempInsuListGrid.getRowColData(tSel-1,2);
			tsql =" select state from RIUWTask where uwno=(select max(uwno) from RIUWIdea where trim(ProposalGrpContNo)='"+tGrpContNo+"' and trim(polno)='000000' and trim(dutycode)='000000' and trim(AuditCode)='000000' and trim(AuditAffixCode)='000000' and trim(AuditType)='05') and trim(ProposalGrpContNo)='"+tGrpContNo+"' and trim(polno)='000000' and trim(dutycode)='000000' and trim(AuditCode)='000000' and AuditAffixCode='000000' and AuditType='05' ";
			arr=easyExecSql(tsql);	
			if(arr=='01'||arr=='02'){
		    myAlert(""+"该任务为已回复状态，不能再回复核保申请!"+"");
		     return false;
			} 
		}
  	var tRemark = fm.Remark.value; //再保回复意见
	}
  ReInsureUpload();
}

function SearchRecord(){
	if(fm.GrpContNo.value==null||fm.GrpContNo.value==""){
		myAlert(" "+"请在团体临分申请列表中选择保单！"+" ");
		return false;
	}
	var mSQL =" select a.prtno,a.GrpContNo,(select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and trim(contno)=trim(a.contno)),a.contno,a.proposalno,insuredname,insuredno,riskcode,dutycode,(select case state when '00' then '"+"自动分保"+"' when '01' then '"+"满足临分条件"+"' when '02' then '"+"待审核"+"' when '03' then '"+"临分"+"' when '04' then '"+"临分未实现"+"' else '"+"不知名状态"+"' end from RIDutyState where trim(proposalno)=trim(a.proposalno) and trim(dutycode)=trim(b.dutycode)) from lcpol a , lcduty b where trim(a.polno)=trim(b.polno) "
	+ " and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"+ fm.ProposalGrpContNo.value +"') "
	+ " and exists (select * from ridutystate where trim(proposalno)=trim(a.proposalno) and trim(dutycode)=trim(b.dutycode) and state='02') "
	+ getWherePart("a.riskcode","RiskCode")
	+ getWherePart("b.DutyCode","DutyCode")
	+ getWherePart("a.insuredno","InsuredNo")
	+ getWherePart("a.insuredName","InsuredName","like")
  ;
  if(fm.ContPlanCode.value!=""&&fm.ContPlanCode.value!=null){
  	mSQL = mSQL + " and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and trim(contno)=trim(a.contno) and trim(contplancode)='"+fm.ContPlanCode.value+"')";
  }
  //if(fm.TempUWConclusionConf.value!=""&&fm.TempUWConclusionConf.value!=null){
  //	mSQL = mSQL + " and exists (select * from RIDutyState where trim(ProposalGrpContNo)=trim(a.GrpContNo) and trim(ProposalNo)=trim(a.ProposalNo) and trim(DutyCode)=trim(b.DutyCode) and State='"+fm.TempUWConclusionConf.value+"') ";
  //}
  
  mContPlanCode = fm.ContPlanCode.value ;
	mRiskCode = fm.RiskCode.value ;
	mDutyCode = fm.DutyCode.value ;
	mInsuredNo = fm.InsuredNo.value ;
	mInsuredName = fm.InsuredName.value ;
  tSearchFlag = "1"; //置已查询标记
	turnPage8.queryModal(mSQL,SearchResultGrid);
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
	tSearchFlag ="0"; //清除已查询标记
	SearchResultGrid.clearData();
}

function ReInsureUpload() {
  var i = 0;
  var tImportFile = fmImport.all('FileName').value;
  if ( tImportFile.indexOf("\\")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("\\")+1);
  if ( tImportFile.indexOf("/")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("/")+1);
  if ( tImportFile.indexOf("_")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("_"));
  if ( tImportFile.indexOf(".")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("."));
    var showStr=""+"正在上载资料……"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fmImport.action = "./ReInsuUpLodeSave.jsp"; 
    fmImport.submit();
}

function DownLoad(){
	 tSel = ReInsureAuditGrid.getSelNo();
	 if(tSel==0||tSel==null){
			myAlert(""+"请先选择再保审核任务信息！"+"");
			return false;
	 }
	 
   var FilePath = ReInsureAuditGrid.getRowColData(tSel - 1, 5);  
   if (FilePath==""||FilePath==null){
	   myAlert(""+"没有附件"+","+"不能进行下载操作！"+"");
	   return false;
   }   
   
   //var showStr="正在下载资料……";
   fmImport.action = "./DownLoadSave.jsp?FilePath="+FilePath;
   fmImport.submit();
}
//保存回复信息
function UpLodeReInsure(FilePath,FileName){
	var checkFlag;
	var opeData = "";
	var grpContNo = "";
	var edorNo = "";
	var edorType = "";
	var caseNo = "";
	var remark = "";
	var opeFlag=fm.AuditType.value;
	var tSel = TaskLiskGrid.getSelNo();
	if(tSel==null||tSel==0)
	{
		myAlert(""+"请选择险种保单信息!"+"");
		return false;
	}
	if(opeFlag=="01"){ 
		opeData=TaskLiskGrid.getRowColData(tSel-1,2);
	}
	if(opeFlag=="03"){
		checkFlag=EdorInfoGrid.getSelNo();
		dutyCode=EdorInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = EdorInfoGrid.getRowColData(checkFlag - 1,9);
		edorNo= EdorInfoGrid.getRowColData(checkFlag - 1,4);
		edorType=EdorInfoGrid.getRowColData(checkFlag - 1,5);
	}
	if(opeFlag=="04"){
		opeData=TaskLiskGrid.getRowColData(tSel-1,6); //任务序号
		caseNo = TaskLiskGrid.getRowColData(tSel-1,5);
	}
	if(opeFlag=="05"){
		checkFlag = GrpTempInsuListGrid.getSelNo();	
 		grpContNo = GrpTempInsuListGrid.getRowColData(checkFlag-1,2);
	}
	if(InputFlag=="0"){
		fm.Remark.value="";
	}
	remark=fm.Remark.value;
	fmImport.action = "./SendReInsureChk.jsp?OpeFlag="+opeFlag+"&FilePath="+FilePath+"&FileName="+FileName+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&OpeData="+opeData+"&Remark="+remark;
	fmImport.submit();
}

function ReInsureOver(){
	for (i=0; i<RiskInfoGrid.mulLineCount; i++){
    if (RiskInfoGrid.getSelNo(i)){
      checkFlag = RiskInfoGrid.getSelNo();
      break;
    }
  }
  var	State = RiskInfoGrid.getRowColData(checkFlag - 1, 6);
  if (State!=''+"已回复"+''){
  	myAlert(""+"该险种处于"+""+State+""+"状态不能进行办结"+"");
  }
}
 		
function afterSubmit(FlagStr, content ){
  showInfo.close();
  if (FlagStr == "Fail" ){             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else{ 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
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

function recoverData(){
}

function showAnswerIdea()
{
	var checkFlag=ReInsureAuditGrid.getSelNo();
	fmInfo.SendAnswerRemark.value= ReInsureAuditGrid.getRowColData(checkFlag - 1,3);
}

function showTaskInfo(){
	fmInfo.SendAnswerRemark.value="";
	
	if(fmInfo.OpeFlag.value=="1"){
		fm.AuditType.value="01";
	}
	var opeFlag=fm.AuditType.value;
	if(opeFlag=="01"){
		QueryReInsureAudit(); //新单个单发送回复信息
	}
	if(opeFlag=="03"){
		QueryEdorAudit(); //保全个单发送回复信息
	}
	if(opeFlag=="04"){
		QueryClaimAudit();  //临分发送回复信息
	}
	if(opeFlag=="05"){
		QueryGrpNewAudit(); //团体发送回复信息
	}
}

/**
*查询新单任务发起与回复信息
*/
function  QueryReInsureAudit(){
	var tSel = TaskLiskGrid.getSelNo();	
 	var tOpeData = TaskLiskGrid.getRowColData(tSel-1,2);
	mSql = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,decode(x.A7,'00','"+"待回复"+"','01','"+"已回复"+"','02','"+"办结"+"',null,'"+"办结"+"','"+"未知类型"+"') from( select a.uwno A1,a.UWOperator A2,a.UWIdea A3,a.ModifyDate A4,a.AdjunctPath A5,'"+"核保发送"+"' A6, (select State from RIUWTask b where serialno = a.serialno) A7  from RIUWIdea a where a.serialno=(select max(b.serialno) from RIUWTask b where b.AuditType='01' and b.Proposalgrpcontno='000000' and b.proposalcontno='"+tOpeData+"') "
  + " union all " 
  + " select a.UWNo A1,a.ReInsurer A2,a.UWIdea A3,a.ModifyDate A4,a.AdjunctPath A5,'"+"再保回复"+"' A6, (select State from RIUWTask b where serialno = a.serialno) A7  from RIAnwserIdea a where  a.serialno=(select max(b.serialno) from RIUWTask b where b.AuditType='01' and b.Proposalgrpcontno='000000' and b.proposalcontno='"+tOpeData+"') ) x order by x.A1 desc,x.A6 "
  ;
	turnPage6.queryModal(mSql,ReInsureAuditGrid);
}
 
function QueryEdorAudit(){
	var tSel = TaskLiskGrid.getSelNo();	
 	var tOpeData = TaskLiskGrid.getRowColData(tSel-1,2);
	mSql = " select * from( "
		+ " select a.uwno,a.UWOperator,a.UWIdea ,a.ModifyDate,a.AdjunctPath,'"+"核保发送"+"', "
		+ " (select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where ProposalContNo=a.ProposalContNo and a.AuditType=AuditType and a.UWNo=UWNo)"
		+ " from RIUWIdea a where "
		+ " a.serialno=(select max(b.serialno) from RIUWTask b where b.AuditType='03' and b.Proposalgrpcontno='000000' and b.proposalcontno='"+tOpeData+"')"
		+ " union all "
		+ " select a.UWNo,a.ReInsurer,a.UWIdea,a.ModifyDate,a.AdjunctPath,'"+"再保回复"+"',"
		+ " (select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where ProposalContNo=a.ProposalContNo and a.AuditType=AuditType and a.UWNo=UWNo)"
		+ " from RIAnwserIdea a where "
		+ " a.serialno=(select max(b.serialno) from RIUWTask b where b.AuditType='03' and b.Proposalgrpcontno='000000' and b.proposalcontno='"+tOpeData+"')"
		+ " ) order by uwno desc "
		;
	turnPage6.queryModal(mSql,ReInsureAuditGrid);
}

function QueryClaimAudit(){

	var mSql = "";
	var checkFlag = TaskLiskGrid.getSelNo();
	var serilaNo = TaskLiskGrid.getRowColData(checkFlag-1,6);
	
	mSql = "select * from ( select a.UWNo, a.UWOperator, a.UWIdea, a.ModifyDate, a.AdjunctPath, '"+"核赔发送"+"', (select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask  b where b.serialno= a.serialno) from RIUWIdea a  where a.serialno = '"+serilaNo+"' union all select a.UWNo, a.ReInsurer, a.UWIdea, a.ModifyDate,  a.AdjunctPath, '"+"再保回复"+"', (select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask b where b.serialno= a.serialno) from RIAnwserIdea a  where a.serialno = '"+serilaNo+"') order by uwno desc";
	turnPage6.queryModal(mSql,ReInsureAuditGrid);
}

/**
*查询团单
*/
function QueryGrpNewAudit(){
	var tSel = GrpTempInsuListGrid.getSelNo();	
 	var tGrpContNo = GrpTempInsuListGrid.getRowColData(tSel-1,2);
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'"+"核保发送"+"',(select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and a.AuditType=AuditType and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and trim(AuditCode)=trim(AuditCode) and trim(AuditAffixCode)=trim(AuditAffixCode) and a.UWNo=UWNo) "
	+ " from RIUWIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and trim(a.AuditType)='05' and trim(a.PolNo)='000000' and trim(a.dutycode)='000000' and trim(AuditCode)='000000' and trim(AuditAffixCode)='000000' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'"+"再保回复"+"',(select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and trim(a.AuditType)=trim(AuditType) and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and AuditCode=AuditCode and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) "
	+ " from RIAnwserIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and a.AuditType='05' and trim(a.PolNo)='000000' and trim(a.dutycode)='000000'  and trim(AuditCode)='000000' and AuditAffixCode='000000') order by uwno desc"
	;	
	turnPage6.queryModal(mSql,ReInsureAuditGrid);
}

function TempCessConclusion(){
	var tsql;
	var arr;
	if(fm.AuditType.value==""||fm.AuditType.value==null){
		myAlert(""+"请选择再保审核类型！"+"");
		return false;
	}
	if(fm.AuditType.value=="01"){
		tSel = RiskInfoGrid.getSelNo();
		polNo=RiskInfoGrid.getRowColData(tSel-1,7);
		dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
		if(tSel==0||tSel==null)
		{
			myAlert(""+"请选择险种保单信息！"+"");
			return false;
		}
		tsql="select appflag from lcpol where proposalno='"+polNo+"'";
		arr=easyExecSql(tsql);
		if(arr[0]==null){
			myAlert(""+"没有此保单信息"+"");
			return false;
		}
		if(arr[0]!='1'){
			myAlert(""+"未签单保单不能下临分结论"+"");
			return false;
		}
		fm.action = "./CessTempConclusionSave.jsp?PolNo="+polNo+"&DutyCode="+dutyCode;
		fm.submit();
	}
}

function initTempCess(){
  if(fmInfo.ContType.value=='1'){
  	divInd1.style.display='';
  	divGrpInfo.style.display='none';
  }else{
  	divInd1.style.display='none';
  	divGrpInfo.style.display='';
  }
  
}
	
function afterCodeSelect(cCodeName,Field){
	//选择了处理
	if( cCodeName == "audittype"){
		showTask();
	}
}
