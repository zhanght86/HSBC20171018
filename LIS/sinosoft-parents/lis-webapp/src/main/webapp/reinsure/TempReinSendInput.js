	/*********************************************************************
 *  锟斤拷锟斤拷锟斤拷疲锟絉eInsureInput.js
 *  锟斤拷锟斤拷锟杰ｏ拷锟劫憋拷锟剿憋拷锟斤拷锟斤拷页锟斤拷
 *  锟斤拷锟斤拷锟斤拷锟节ｏ拷2007-03-21
 *  锟斤拷锟斤拷锟斤拷  锟斤拷zhangbin
 *  锟斤拷锟斤拷值锟斤拷  锟斤拷
 *  锟斤拷锟铰硷拷录锟斤拷  锟斤拷锟斤拷锟斤拷    锟斤拷锟斤拷锟斤拷锟斤拷     锟斤拷锟斤拷原锟斤拷/锟斤拷锟斤拷
 *********************************************************************
 */

var +--------------------------- ;
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
var temp = new Array();
var tSearchFlag="0";
var mOperate="";
var ImportPath;
var oldDataSet ; 
var InputFlag="0"; 


var mContPlanCode = "";
var mRiskCode = "";
var mDutyCode = "";
var mInsuredNo = "";
var mInsuredName = "";

window.onfocus=myonfocus;
/*********************************************************************
 *  锟斤拷询锟斤拷锟斤拷锟斤拷息
 *  锟斤拷锟斤拷  锟斤拷  锟斤拷
 *  锟斤拷锟斤拷值锟斤拷  锟斤拷
 *********************************************************************
 */
function assgnOperate(){
	if(!initSerialNo()){
		return false;
	}
	var opeFlag=fmImport.OpeFlag.value;
	if(opeFlag=="01"){ //锟斤拷锟秸核憋拷
		QueryRiskInfo();
		showRule();
	}
	else if(opeFlag=="03"){//锟斤拷全
		//QueryEdorInfo();
		//showRule();
	}
	else if(opeFlag=="04"){//锟劫憋拷锟斤拷锟斤拷
		QueryClaimInfo();
		showRule();
	}
	else if(opeFlag=="05"){//锟斤拷锟秸核憋拷
		QueryGrpNewInfo();
		showRule();
	}
	else if(opeFlag=="06"){
		QueryGrpEdorInfo(); 
		showRule();
	}
	else{
		myAlert(""+"非法操作类型"+"");
		return false;divSearchStag.style.display="none";
	}
}
//锟斤拷始锟斤拷锟斤拷锟?
function initSerialNo(){
	if(fmImport.SerialNo.value==null||fmImport.SerialNo.value==''){
		try
  	{
  		var opeFlag=fmImport.OpeFlag.value;
  		var opeData=fmImport.OpeData.value;
			if(opeFlag == "01")
			{ 
				//锟斤拷锟秸核憋拷
				var tSql = " select a.SerialNo from RIGrpState a where a.SerialNo = (select max(b.SerialNo) from RIGrpState b where b.ProposalContNo='"+opeData+"' and a.State = '02'  and b.Exetype='01' ) " ;
				var arr = easyExecSql(tSql);
				if(arr==null || arr==''){
					fmImport.SerialNo.value = '';
				}else{
					fmImport.SerialNo.value = arr[0];
				}
			}
			else if(opeFlag=="03")
			{
			}
			else if(opeFlag=="04")
			{
				//锟劫憋拷锟斤拷锟斤拷
				var tSql = " select a.SerialNo from RIGrpState a where a.SerialNo = (select max(b.SerialNo) from RIGrpState b where b.CaseNo='"+opeData+"' and a.State = '02'  and b.Exetype='04' ) " ;
				var arr = easyExecSql(tSql);
				if(arr==null || arr==''){
					fmImport.SerialNo.value = '';
				}else{
					fmImport.SerialNo.value = arr[0];
				}
			}
			else if(opeFlag=="05")
			{//锟斤拷锟秸核憋拷
			}
			else if(opeFlag=="06")
			{
			}
			else{
				myAlert(""+"非法操作类型"+"");
				return false;divSearchStag.style.display="none";
			}
  	}
  	catch(ex)
  	{
  	  myAlert(""+"初始化页面错误"+""); 
  	  return false ;
  	}
  }
	return true ;
}

function QueryRiskInfo(){
	divSearchStag.style.display="none";
	divGrpRule.style.display="none";
 	divLCDuty1.style.display="";
 	divLLClaim.style.display="none";
 	divLcConclusionXiala.style.display="none"; //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟窖★拷锟?
 	fm.AllConClusion.style.display="none";
 	var tContNo = fm.ContNo.value;
 	
 	//锟皆核斤拷锟斤拷询
 	if(fmImport.DeTailFlag.value=='1'){ //锟斤拷锟斤拷
		mSQL = " select x.A1,x.A2,x.A3,(select b.riskname from lmrisk b where b.riskcode=x.A3),'',x.A5 from ( select a.insuredno A1,a.insuredname A2,(select b.RiskCode from ripolindview b where b.polno=a.polno ) A3,a.uwerror A5 from lcuwerror a where exists (select * from lmuw b where b.uwcode = a.uwrulecode and trim(b.uwtype)='LF') and a.proposalcontno='"+fmImport.OpeData.value+"' and a.uwno= (select max(c.uwno) from lcuwerror c where exists (select * from lmuw b where b.uwcode = c.uwrulecode and trim(b.uwtype)='LF') and c.proposalcontno='"+fmImport.OpeData.value+"' ) order by a.polno,uwno) x "
 		;	
	}else{//锟斤拷锟斤拷
		mSQL = "  "
 		;
	}
 	turnPage.queryModal(mSQL,ReInsureGrid);
 	
	if(fmImport.DeTailFlag.value=='1'){ //锟斤拷锟斤拷
		mSQL = "select a.insuredname A1, a.riskcode A2,'000000' A3,a.Mult A4,a.Prem A5, a.Amnt A6,a.RiskAmnt A7, a.AccAmnt A8, a.ProposalNo A9, decode(b.State,'00','"+"自动分保"+"','01','"+"满足临分条件"+"','02','"+"待临分审核"+"','03','"+"临时分保"+"','04','"+"临分未实现"+"') A10, b.State A11, b.uwconclusion A12 from ripolindview a left join RIDutyState b on ( b.proposalgrpcontno = '000000' and a.proposalno = b.proposalno and b.dutycode = '000000') where a.ProposalContNo = '"+fmImport.OpeData.value+"'";
	}else{//锟斤拷锟斤拷
		mSQL = "select a.insuredname A1, a.riskcode A2,'000000' A3,a.Mult A4,a.Prem A5, a.Amnt A6,a.RiskAmnt A7, a.AccAmnt A8, a.ProposalNo A9, decode(b.State,'00','"+"自动分保"+"','01','"+"满足临分条件"+"','02','"+"待临分审核"+"','03','"+"临时分保"+"','04','"+"临分未实现"+"') A10, b.State A11, b.uwconclusion A12 from ripoldutyindview a left join RIDutyState b on ( b.proposalgrpcontno = '000000' and a.proposalno = b.proposalno and  b.dutycode = a.dutycode ) where a.ProposalContNo = '"+fmImport.OpeData.value+"'";
	}
	turnPage1.queryModal(mSQL, RiskInfoGrid);
}

function QueryEdorInfo()
{
	divSearchStag.style.display="none";
	divGrpRule.style.display="none";
	divLCDuty1.style.display="";
 	divLLClaim.style.display="none";
 	divLcConclusionXiala.style.display="none";//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟窖★拷锟?
 	fm.AllConClusion.style.display="none";
 	//锟斤拷询edorno锟斤拷锟斤拷锟斤拷锟桔计凤拷锟斤拷锟侥憋拷全
 	if(fmImport.DeTailFlag.value=='1')
 	{ //锟斤拷锟斤拷
		mSQL ="select a.insuredname,a.riskcode,b.dutycode,a.Mult,b.Amnt,b.Prem,(select case (select state from RIUWTask where uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' when '02' then '"+"办结"+"' end from dual), a.polno,b.edorno,b.edortype,"
	  	+ " (select state from RIUWTask where uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) from lppol a ,lpduty b where a.polno=b.polno and a.edorno=b.edorno and a.edortype=b.edortype "
		+ " and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode) and a.edorno ='"+fmImport.OpeData.value+"' "
		;
	}
	else
	{
		//锟斤拷锟斤拷
//		mSQL ="select a.insuredname,a.riskcode,b.dutycode,a.Mult,b.Amnt,b.Prem,(select case (select state from RIUWTask where uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) when '00' then '锟斤拷馗锟? when '01' then '锟窖回革拷' when '02' then '锟斤拷锟? end from dual), a.polno,b.edorno,b.edortype,"
//		+ " (select state from RIUWTask where uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) from lppol a ,lpduty b where a.polno=b.polno and a.edorno=b.edorno and a.edortype=b.edortype "
//		+ " and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode) and a.edorno ='"+fmImport.OpeData.value+"' "
//		;
	}
	turnPage1.queryModal(mSQL, RiskInfoGrid);
}

function QueryClaimInfo(){
	var mSQL = "";
	divSearchStag.style.display="none";
	divGrpRule.style.display="none";
	divLCDuty1.style.display="none";
 	divLLClaim.style.display="";
 	fm.AllConClusion.style.display="none";
 	fm.lcAllConClusion.style.display="none";
 	divLcConclusionXiala.style.display=""; //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟窖★拷锟?
 	divConclusionXiala.style.display="none";
 	
 	//锟皆核斤拷锟斤拷询
 	if(fmImport.DeTailFlag.value=='1'){ //锟斤拷锟斤拷
		mSQL = " select  b.InsuredNo  , b.InsuredName ,a.riskcode,(select b.riskname from lmrisk b where b.RiskCode=a.riskcode ),'',a.uwerror from RIGUWError a left join Riclaimriskdetailview b on( b.caseno = a.AuditCode and b.riskcode=a.riskcode)where a.CalItemType='14' and a.AuditCode='"+fmImport.OpeData.value+"' "
 		;	
	}else{//锟斤拷锟斤拷
		mSQL = " select  b.InsuredNo  , b.InsuredName ,a.riskcode,(select b.riskname from lmrisk b where b.RiskCode=a.riskcode ),'',a.uwerror from RIGUWError a left join Riclaimriskdetailview b on( b.caseno = a.AuditCode and b.riskcode=a.riskcode)where a.CalItemType='14' and a.AuditCode='"+fmImport.OpeData.value+"' "
 		;
	}
 	turnPage.queryModal(mSQL,ReInsureGrid);
 	
 	if(fmImport.DeTailFlag.value=='1'){ //锟斤拷锟斤拷
		mSQL = "select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,decode(x.A9, '01', '"+"通知限额"+"', '02', '"+"参与限额"+"','03', '"+"非正常理赔"+"',''),x.A9 from ( select a.InsuredName A1, a.InsuredNo A2,'000000' A3, a.RiskCode A4, '000000' A5, sum(a.ClmGetMoney) A6, a.CaseNo A7,(select c.state from RIGrpState c where c.SerialNo=(select max(SerialNo) from RIGrpState d where a.CaseNo = d.caseno)) A8, (select b.state from RIClaimState b where a.CaseNo = b.caseno and  a.RiskCode = b.RIskCode) A9 from riclaimriskdetailview a where  a.caseno = '"+fmImport.OpeData.value+"' group by a.RiskCode,a.InsuredName,a.InsuredNo,a.CaseNo) x";
	}else{//锟斤拷锟斤拷
		mSQL = "select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,x.A7,x.A8,decode(x.A9, '01', '"+"通知限额"+"', '02', '"+"参与限额"+"','03', '"+"非正常理赔"+"',''),x.A9 from ( select a.InsuredName A1, a.InsuredNo A2,a.ContNo A3, a.RiskCode A4, '000000' A5, a.ClmGetMoney A6, a.CaseNo A7,(select c.state from RIGrpState c where c.SerialNo=(select max(SerialNo) from RIGrpState d where a.CaseNo = d.caseno)) A8, (select b.state from RIClaimState b where a.CaseNo = b.caseno and  a.RiskCode = b.RIskCode) A9 from RIClaimDutyDetailView a where  a.caseno = '"+fmImport.OpeData.value+"' ) x";
	}
	turnPage1.queryModal(mSQL,ClaimInfoGrid);
}

function QueryGrpNewInfo(){
	divIndRule.style.display="none";
	divGrpUWResult.style.display="";
 	divIndUWResult.style.display="";
 	fm.AllConClusion.style.display="";
 	
 	var tSQL="select proposalgrpcontno from lcgrpcont where trim(grpcontno)='"+fm.GrpContNo.value+"'";
	var strArray=easyExecSql(tSQL);
	if(strArray!=null){
		fmImport.OpeData.value=strArray[0][0];
	}else{
		myAlert(""+"页面初始化失败，没有得到保单信息"+"");
		return false;
	}
	if(fmImport.DeTailFlag.value=='1'){ //锟斤拷锟斤拷
		mSQL = " select X.a1,X.a2,X.a3,(select RIContName from RIBarGainInfo where RIContNo = (select max(RIContNo) from RIPrecept where RIPreceptNo=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where a.GrpContNo='"+fmImport.OpeData.value+"' and a.riskcode='000000' ) X "
  	;
		turnPage3.queryModal(mSQL,GrpUWResultGrid);
		mSQL =" select X.a1,X.a2,X.a3,b1,b2,b3,b4,b5,b6,(select RIContName from RIBarGainInfo where RIContNo = (select max(RIContNo) from RIPrecept where RIPreceptNo=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select contno from lcpol where trim(ProposalNo) = trim(a.ProposalNo)) b1,a.ProposalNo b2,a.InsuredName b3,a.InsuredNo b4,a.RiskCode b5,a.DutyCode b6,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where a.GrpContNo='"+fmImport.OpeData.value+"' and a.riskcode <> '000000' ) X "
  	;
		turnPage4.queryModal(mSQL,IndUWResultGrid);
	}else{//锟斤拷锟斤拷
		mSQL = " select X.a1,X.a2,X.a3,(select RIContName from RIBarGainInfo where RIContNo = (select max(RIContNo) from RIPrecept where RIPreceptNo=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where a.GrpContNo='"+fmImport.OpeData.value+"' and a.riskcode='000000' ) X "
  	;
		turnPage3.queryModal(mSQL,GrpUWResultGrid);
		mSQL =" select X.a1,X.a2,X.a3,b1,b2,b3,b4,b5,b6,(select RIContName from RIBarGainInfo where RIContNo = (select max(RIContNo) from RIPrecept where RIPreceptNo=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select contno from lcpol where trim(ProposalNo) = trim(a.ProposalNo)) b1,a.ProposalNo b2,a.InsuredName b3,a.InsuredNo b4,a.RiskCode b5,a.DutyCode b6,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where a.GrpContNo='"+fmImport.OpeData.value+"' and a.riskcode <> '000000' ) X "
  	;
		turnPage4.queryModal(mSQL,IndUWResultGrid);
	}
}

function QueryGrpEdorInfo(){
	divIndRule.style.display="none";
	divGrpUWResult.style.display="";
 	divIndUWResult.style.display="";
 	fm.AllConClusion.style.display="";
 	
	var tSQL="select proposalgrpcontno from lcgrpcont where trim(grpcontno)='"+fm.GrpContNo.value+"'";
	var strArray=easyExecSql(tSQL);
	if(strArray!=null){
		fmImport.OpeData.value=strArray[0][0];
	}else{
		myAlert(""+"页面初始化失败，没有得到保单信息"+"");
		return false;
	}
	
	if(fmImport.DeTailFlag.value=='1'){ //锟斤拷锟斤拷
		mSQL = " select X.a1,X.a2,X.a3,(select RIContName from RIBarGainInfo where trim(RIContNo) = (select max(trim(RIContNo)) from RIPrecept where trim(RIPreceptNo)=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where trim(a.GrpContNo)='"+fmImport.OpeData.value+"' and a.riskcode='000000' ) X "
  	;
		turnPage3.queryModal(mSQL,GrpUWResultGrid);
	
		mSQL =" select X.a1,X.a2,X.a3,b1,b2,b3,b4,b5,b6,(select RIContName from RIBarGainInfo where RIContNo = (select max(RIContNo) from RIPrecept where RIPreceptNo=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select contno from lcpol where ProposalNo = a.ProposalNo) b1,a.ProposalNo b2,a.InsuredName b3,a.InsuredNo b4,a.RiskCode b5,a.DutyCode b6,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where a.GrpContNo='"+fmImport.OpeData.value+"' and a.riskcode <> '000000' ) X "
  	;
	turnPage4.queryModal(mSQL,IndUWResultGrid);
	}else{//锟斤拷锟斤拷
		mSQL = " select X.a1,X.a2,X.a3,(select RIContName from RIBarGainInfo where trim(RIContNo) = (select max(trim(RIContNo)) from RIPrecept where trim(RIPreceptNo)=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where a.GrpContNo='"+fmImport.OpeData.value+"' and a.riskcode='000000' ) X "
  	;
		turnPage3.queryModal(mSQL,GrpUWResultGrid);
	
		mSQL =" select X.a1,X.a2,X.a3,b1,b2,b3,b4,b5,b6,(select RIContName from RIBarGainInfo where RIContNo = (select max(RIContNo) from RIPrecept where RIPreceptNo=X.a4)),X.a4,X.a5,X.a6 from (select (select PrtNo from lcgrpcont where grpcontno=a.GrpContNo) a1,grpcontno a2,(case ContPlanCode when '000000' then '' else ContPlanCode end ) a3,(select contno from lcpol where ProposalNo = a.ProposalNo) b1,a.ProposalNo b2,a.InsuredName b3,a.InsuredNo b4,a.RiskCode b5,a.DutyCode b6,(select RIPreceptNo from RILMUW where RuleCode=a.UWRuleCode) a4,(select RuleName from RILMUW where RuleCode=a.UWRuleCode) a5,UWError a6 from RIGUWError a where a.GrpContNo='"+fmImport.OpeData.value+"' and a.riskcode <> '000000' ) X "
  	;
	}
}
/**锟斤拷询锟斤拷锟轿憋拷锟斤拷锟斤拷息*/
function SearchRecord(){
	var mSQL =" select a.prtno,a.GrpContNo,(select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno)),a.contno,a.proposalno,insuredname,insuredno,riskcode,dutycode,(select case state when '00' then '"+"合同分保"+"' when '01' then '"+"满足临分条件"+"' when '02' then '"+"待审核"+"' when '03' then '"+"临分"+"' when '04' then '"+"临分未实现"+"' else '"+"不知名状态"+"' end from RIDutyState where proposalno=a.proposalno and dutycode=b.dutycode),(select state from RIDutyState where proposalno=a.proposalno and dutycode=b.dutycode) from lcpol a , lcduty b where a.polno=b.polno and trim(a.grpcontno)='"+ fm.GrpContNo.value +"' and b.dutycode in (select distinct associatedcode from RIAccumulateRDCode)"
	+ getWherePart("a.riskcode","RiskCode")
	+ getWherePart("b.DutyCode","DutyCode")
	+ getWherePart("a.insuredno","InsuredNo")
	+ getWherePart("a.insuredName","InsuredName","like")
  ;
  if(fm.ContPlanCode.value!=""&&fm.ContPlanCode.value!=null){
  	mSQL = mSQL + " and exists (select contplancode from lcinsured where insuredno=a.insuredno and contno=a.contno and contplancode='"+fm.ContPlanCode.value+"')";
  }
  if(fm.TempUWConclusionConf.value!=""&&fm.TempUWConclusionConf.value!=null){
  	mSQL = mSQL + " and exists (select * from RIDutyState where ProposalGrpContNo="+fmImport.OpeData.value+" and ProposalNo=a.ProposalNo and DutyCode=b.DutyCode and State='"+fm.TempUWConclusionConf.value+"') ";
  }
  mSQL = mSQL + " order by a.insuredno,a.riskcode,b.dutycode ";
  mContPlanCode = fm.ContPlanCode.value ;
	mRiskCode = fm.RiskCode.value ;
	mDutyCode = fm.DutyCode.value ;
	mInsuredNo = fm.InsuredNo.value ;
	mInsuredName = fm.InsuredName.value ;
  tSearchFlag = "1"; //锟斤拷锟窖诧拷询锟斤拷锟?
	turnPage5.queryModal(mSQL,SearchResultGrid);
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
	fm.TempUWConclusionConf.value = "";
	fm.TempUWConclusionConfName.value = "";
	
	tSearchFlag ="0"; //锟斤拷锟斤拷巡锟窖拷锟斤拷
	SearchResultGrid.clearData();
}

function TempConclusionAll(){
	if(fmImport.OpeFlag.value=='01'){
		
	}else if(fmImport.OpeFlag.value=='05'||fmImport.OpeFlag.value=='06'){
		if(!verify1()){
			return false;
		}
		if (!confirm(""+"要对所有查询结果下核保临分结论吗？"+"")){
  	  return false;
		}
		if(!VerifySearch()){
			myAlert(""+"查询条件已经修改，请重新查询后再下结论！"+"");
			return false;
		}
		fm.action = "./TempUWConclusionSave.jsp?CONOPETYPE=01&UWFLAG=1&ProposalGrpContNo="+fmImport.OpeData.value;
  	fm.submit();
  }
}

//锟斤拷选锟叫斤拷锟斤拷陆锟斤拷锟?
function TempConclusionSel(){

//ContType:'1'-锟斤拷锟斤拷锟斤拷'2'-锟脚碉拷  CONOPETYPE:'01'-全锟斤拷锟铰斤拷,'02'-选锟叫碉拷锟铰斤拷锟斤拷   UWFLAG:'1'-锟剿憋拷锟劫分斤拷锟桔ｏ拷'2' 锟劫憋拷锟斤拷锟斤拷锟斤拷锟?;'0'-锟劫憋拷锟劫分斤拷锟斤拷   OPEDATA:IF(ContType=='1') THEN ProposalContNo ELSE ProposalGrpContNo
	if(!verify1()){
		return false;
	}

	if(fmImport.OpeFlag.value=='01'){
		if(isEndVerify()){ 
			myAlert(""+"该任务已审核完毕，不能再修改临分核保结论!"+"");
			return false;
		}
		var Count=RiskInfoGrid.mulLineCount;
		var chkFlag=false;
		for (i=0;i<Count;i++){
			if(RiskInfoGrid.getChkNo(i)==true){
				if(RiskInfoGrid.getRowColData(i,11)=='03'){
					myAlert(''+"修改"+'"临分""+"状态的审核结论必须先在临分保单关联页面中取消临分保单关联，请点击[临时分保]按钮。"+"');
					return false;
				}
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"请选中关联查询结果"+"");
			return false;
		}
		fm.action = "./TempUWConclusionSave.jsp?ContType=1&CONOPETYPE=02&UWFLAG=1&OpeData="+fmImport.OpeData.value;
	}else if(fmImport.OpeFlag.value=='04'){ //锟斤拷锟斤拷锟斤拷锟?锟叫讹拷

		if(isEndVerify()){ 
			myAlert(""+"该任务已审核完毕，不能再修改核赔结论!"+"");
			return false;
		}
		var Count=ClaimInfoGrid.mulLineCount;
		var chkFlag=false;
		for (i=0;i<Count;i++){
			if(ClaimInfoGrid.getChkNo(i)==true){											
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"请选中关联查询结果"+"");
			return false;
		}
		fm.action = "./TempUWConclusionSave.jsp?ContType=1&CONOPETYPE=02&UWFLAG=2&OpeData="+fmImport.OpeData.value;
	}else if(fmImport.OpeFlag.value=='05'||fmImport.OpeFlag.value=='06'){
		var Count=SearchResultGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(SearchResultGrid.getChkNo(i)==true){
				chkFlag=true;
				if(SearchResultGrid.getRowColData(i,11)=="03"){
					myAlert(""+"不能对已临分的保单下再保临分结论"+"");
					return false;
				}
			}
		}
		if (chkFlag==false){
			myAlert(""+"请选中关联查询结果"+"");
			return false;
		}
		fm.action = "./TempUWConclusionSave.jsp?ContType=2&CONOPETYPE=02&UWFLAG=1&OpeData="+fmImport.OpeData.value;
	}else{
		myAlert(""+"无效操作类型"+"");
		return false;
	}
	fm.submit();
}

function verify1(){
	//锟叫讹拷锟斤拷锟斤拷锟角凤拷锟窖撅拷锟斤拷锟?
	if(!isEnd()){
		return false;
	}
	//锟叫讹拷锟角凤拷锟斤拷选锟斤拷锟剿核憋拷锟劫分斤拷锟斤拷锟斤拷锟斤拷 锟斤拷 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟?
	if(fmImport.OpeFlag.value=='04'){
		if(fm.lcTempUWConclusion.value==null||fm.lcTempUWConclusion.value==""){
			myAlert(""+"请选择核赔结论类型！"+"");
			return false;
		}
	}else if(fm.TempUWConclusion.value==null||fm.TempUWConclusion.value==""){
		myAlert(""+"请选择核保临分结论类型！"+"");
		return false;
	}
	if(fmImport.OpeFlag.value=='01'){
		//锟斤拷锟斤拷锟铰碉拷锟斤拷锟斤拷丫锟斤拷锟饺凤拷虾锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟劫斤拷锟斤拷锟劫分斤拷锟桔碉拷锟睫革拷锟斤拷
		var tsql = "";
		var arr;
		var opeData = fmImport.OpeData.value;
		var serialNo = fmImport.SerialNo.value;                                     
		if(serialNo==null||serialNo==''){ //锟斤拷锟絊erialNo为锟斤拷说锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟杰讹拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟劫分斤拷锟桔碉拷锟斤拷锟斤拷锟斤拷锟斤拷薷锟?
			var Count=RiskInfoGrid.mulLineCount;
			var chkFlag=false;
			for (i=0;i<Count;i++){
				if(RiskInfoGrid.getChkNo(i)==true){
					if(RiskInfoGrid.getRowColData(i,11)=='03'||RiskInfoGrid.getRowColData(i,11)=='00'||RiskInfoGrid.getRowColData(i,11)=='04'){
						myAlert(''+"修改已经下临分结论的保单必须先在临分保单关联页面中取消临分保单关联。"+'');
						return false;
					}
					chkFlag=true;
				}
			}
		}else{
			//锟斤拷锟斤拷锟絊erialNo说锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷说谋锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟叫ｏ拷锟斤拷锟斤拷校锟斤拷
		}
	}else if(fmImport.OpeFlag.value=='04'){

			
	}else if(fmImport.OpeFlag.value=='05'||fmImport.OpeFlag.value=='06'){
		if(tSearchFlag!="1"){
			myAlert(""+"请查询后再下临分结论"+"");
			return false;
		}	
	}else{
		myAlert(""+"无效操作类型"+"");
		return false;
	}
	return true;
}

//锟叫讹拷锟斤拷锟斤拷锟角凤拷锟斤拷
function isEnd(){
	var tsql="";
	if(fmImport.OpeFlag.value=='01'){
		tsql = " select state from RIUWTask a where a.serialno = (select max(b.Serialno) from RIUWTASK b where b.proposalgrpcontno='000000' and b.proposalcontno='"+fmImport.OpeData.value+"' and AuditType='01') "
		;
	}else if(fmImport.OpeFlag.value=='03'){
		tsql = " select state from RIUWTask a where a.serialno = (select max(b.Serialno) from RIUWTASK b where b.proposalgrpcontno='000000' and b.proposalcontno='"+fmImport.OpeData.value+"' and AuditType='03') "
		;
	}else if(fmImport.OpeFlag.value=='04'){
	    tsql = "select state from RIUWTask a where a.serialno = (select max(b.Serialno) from RIGrpState b where b.caseno = '"+fmImport.OpeData.value+"' and Exetype = '04')  and AuditType = '04'";
	
	}else if(fmImport.OpeFlag.value=='05'||fmImport.OpeFlag.value=='06'){
		tsql = " select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where proposalgrpcontno='"+fmImport.OpeData.value+"' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditType='05' and AuditAffixCode='000000') and proposalgrpcontno='"+fmImport.OpeData.value+"' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditType='05' and AuditAffixCode='000000' "
		;
	}else{;
		myAlert(""+"无效操作类型"+"");
		return false;
	}
	var arr=easyExecSql(tsql);	
	if(arr=='00'||arr=='01'){
    	myAlert(""+"该任务未办结，不能操作!"+"");
    	return false;
	}
	return true;
}

function isEndVerify(){// 锟角凤拷锟斤拷锟斤拷锟斤拷
	var tsql="";

	if(fmImport.OpeFlag.value=='01'){
		tsql = " select state from RIGrpState a where a.serialno = (select max(b.Serialno) from RIGrpState b where b.proposalgrpcontno='000000' and b.proposalcontno='"+fmImport.OpeData.value+"' and Exetype='01') ";
		var arr=easyExecSql(tsql);	
		if(arr=='03'){   	
    		return true;
		}
	}else if(fmImport.OpeFlag.value=='04'){
		tsql = " select state from RIGrpState a where a.serialno = (select max(b.Serialno) from RIGrpState b where b.proposalgrpcontno='000000' and b.caseno = '"+fmImport.OpeData.value+"' and Exetype = '04')";
		var arr=easyExecSql(tsql);	
		if(arr=='03'){  	
    		return true;
		}
	}  
	return false;
}

function VerifySearch(){
  if(mContPlanCode != fm.ContPlanCode.value) return false;
	if(mRiskCode != fm.RiskCode.value) return false;
	if(mDutyCode != fm.DutyCode.value) return false;
	if(mInsuredNo != fm.InsuredNo.value) return false;
	if(mInsuredName != fm.InsuredName.value) return false;
	
	return true;
}

/**
*锟斤拷锟斤拷锟劫憋拷锟斤拷锟斤拷
*/
function AutoReInsure(){
	var showStr=""+"正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	var polNo;
	var dutyCode;
	var edorNo;
	var edorType;
	var caseNo;
	fm.RunRuleFlag.value="1";
	var tOpeData=fmImport.OpeData.value;
	var tOpeFlag=fmImport.OpeFlag.value;
	var tSel="";
	if(tOpeFlag=="01"){
		tSel=RiskInfoGrid.getSelNo();
		if(tSel==0){
			myAlert(""+"请选择责任信息"+"");
			return false;
		}
		dutyCode=RiskInfoGrid.getRowColData(tSel - 1,3);
		polNo = RiskInfoGrid.getRowColData(tSel - 1,8);
	}
	if(tOpeFlag=="03"){
		tSel=RiskInfoGrid.getSelNo();
		if(tSel==0){
			myAlert(""+"请选择责任信息"+"");
			return false;
		}
		dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
		polNo 	= RiskInfoGrid.getRowColData(tSel-1,8);
		edorNo	= RiskInfoGrid.getRowColData(tSel-1,9);
		edorType=RiskInfoGrid.getRowColData(tSel-1,10);
	}
	//if(tOpeFlag=="04"){
	//	tSel=ClaimInfoGrid.getSelNo();
	//	if(tSel==0){
	//		alert("锟斤拷选锟斤拷锟斤拷锟斤拷锟斤拷息");
	//		return false;
	//	}
	//	dutyCode=ClaimInfoGrid.getRowColData(tSel-1,3);
	//	polNo		=ClaimInfoGrid.getRowColData(tSel - 1,6); 
	//	caseNo	=ClaimInfoGrid.getRowColData(tSel - 1,7); 
	//}
	fm.action = "./AutoReInsureChk.jsp?OpeData="+tOpeData+"&OpeFlag="+tOpeFlag+"&PolNo="+polNo+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&DutyCode="+dutyCode;
	fm.submit();
}

function SendUWReInsure(){
	var checkFlag="";
	var	polNo = "";
	var dutyCode="";
	var tsql="";
	if(fmImport.OpeFlag.value=="01"){
		if(isEndVerify()){ 
			myAlert(""+"该任务已审核完毕，不能再发送审核意见!"+"");
			return false;
		}
//		if(!VerifyContState()){
//			return false;
//		}
		
		tsql =" select state from RIUWTask a where a.serialno = (select max(b.Serialno) from RIUWTASK b where b.proposalcontno='"+fmImport.OpeData.value+"' and AuditType='01')"
		;
	}else if(fmImport.OpeFlag.value=="03"){
		checkFlag=RiskInfoGrid.getSelNo();
		if(checkFlag==null||checkFlag==""){
			myAlert(""+"请选择责任信息"+"");
			return false;
		}
		dutyCode=RiskInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = RiskInfoGrid.getRowColData(checkFlag - 1,8);
		edorNo= RiskInfoGrid.getRowColData(checkFlag - 1,9);
		edorType=RiskInfoGrid.getRowColData(checkFlag - 1,10);
		tsql ="select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where polno='" 
		+ polNo + "' and trim(dutycode)='"+dutyCode+"' and trim(AuditCode)='"+edorNo+"' and AuditAffixCode='"+edorType+"' and AuditType='03') and polno='" +polNo 
		+ "' and trim(dutycode)='"+dutyCode+"' and trim(AuditCode)='"+edorNo+"' and AuditAffixCode='"+edorType+"' and AuditType='03'" 
		;
	}else if(fmImport.OpeFlag.value=="04"){ //锟斤拷锟斤拷 锟斤拷锟斤拷
//		if(!VerifyContState()){
//			return false;
//		}
		if(isEndVerify()){  
			myAlert(""+"该任务已审核完毕，不能再发送审核意见!"+"");
			return false;
		}
		tsql ="select state from RIUWTask a where a.serialno = (select max(b.Serialno) from RIGrpState b where b.caseno = '"+fmImport.OpeData.value+"' and Exetype = '04')  and AuditType = '04'";
		
	}else if(fmImport.OpeFlag.value=="05"||fmImport.OpeFlag.value=="06"){
		if(!VerifyContState()){
			return false;
		}
		tsql = " select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where proposalgrpcontno='"+fmImport.OpeData.value+"' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditType='05' and AuditAffixCode='000000') and  proposalgrpcontno='"+fmImport.OpeData.value+"' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditType='05' and AuditAffixCode='000000' "
		;
	}
	ImportFile = fmImport.all('FileName').value;	
  var tRemark = fm.Remark.value;
	var arr=easyExecSql(tsql);
	if(arr=='00'||arr=='01'){
   		myAlert(""+"该任务未办结，不能发送再保审核!"+"");
    	return false;
	}
  ReInsureUpload();
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
    +---------------------------=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fmImport.action = "./UpLodeSave.jsp";
    fmImport.submit();
}

function DownLoad(){
	var checkFlag;
	checkFlag = ReInsureAuditGrid.getSelNo();
	if(checkFlag==null||checkFlag==""){
		myAlert(""+"请选择发送或审核任务"+"");
		return false;
	}
  var FilePath = ReInsureAuditGrid.getRowColData(checkFlag - 1,5);
  if (FilePath==""){
  myAlert(""+"没有附件"+","+"不能进行下载操作！"+"")	
  return false;
  }
  fmInfo.action = "./DownLoadSave.jsp?FilePath="+FilePath;
  fmInfo.submit();
}
/**
* 锟斤拷锟斤拷锟斤拷
*/
function ReInsureOver(){
	var checkFlag;
	var edorNo;
	var edorType;
	var caseNo;
	var	State;
	var opeFlag=fmImport.OpeFlag.value;
	var opeData=fmImport.OpeData.value;
	var serialNo=fmImport.SerialNo.value;
	//锟斤拷锟斤拷校锟斤拷
	if(!VerifyContState()){
		return false;
	}
	//锟斤拷锟叫ｏ拷锟?
	if(!VerifyOver()){
		return false;
	}
	if(opeFlag=='04'){
		caseNo = opeData ;
		var Count=ClaimInfoGrid.mulLineCount;		
		if(Count>0){
		   opeData = ClaimInfoGrid.getRowColData(0,3);
		}
	}
	var overFlag="TRUE"; //锟斤拷锟斤拷志
	fm.action = "./SendUWReInsureChk.jsp?OverFlag="+overFlag+"&OpeFlag="+opeFlag+"&OpeData="+opeData+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&SerialNo="+serialNo;
	fm.submit();
}

//锟斤拷锟斤拷校锟斤拷
function VerifyContState(){
	var tsql = "";
	var arr;
	var opeData = fmImport.OpeData.value;
	
	if(fmImport.OpeFlag.value=="01"){
		if(fmImport.SerialNo.value==null||fmImport.SerialNo.value==''){
			
		}else{
			tsql = " select State from RIGrpState where trim(SerialNo)='"+fmImport.SerialNo.value+"' ";
			;
			arr=easyExecSql(tsql);
			if(arr=='03'||arr=='04'){
				myAlert(""+"审核完毕状态，不能操作"+"");
  		  return false;
			}
		}
		
	}else if(fmImport.OpeFlag.value=="03"){
		
	}else if(fmImport.OpeFlag.value=="04"){
		if(fmImport.SerialNo.value==null||fmImport.SerialNo.value==''){
			
		}else{
			tsql = " select State from RIGrpState where trim(SerialNo)='"+fmImport.SerialNo.value+"' ";
			arr=easyExecSql(tsql);
			if(arr=='03'||arr=='04'){
				myAlert(""+"审核完毕状态，不能操作"+"");
  		 	    return false;
			}
		}
		
	}else if(fmImport.OpeFlag.value=="05"||fmImport.OpeFlag.value=="06"){
		
	}
	return true;
}

/**
* 锟斤拷锟叫ｏ拷锟?
*/
function VerifyOver(){
	var opeFlag=fmImport.OpeFlag.value;
	if(opeFlag=="01"){
		var tSql = " select state from RIUWTask b where b.serialno=(select max(a.serialno) from RIUWTask a where a.proposalgrpcontno='000000' and a.proposalcontno='"+ fmImport.OpeData.value +"' and a.AuditType='01') " ;
		var arr=easyExecSql(tSql);	
		if(arr==null){
			myAlert(""+"没有新发起的临分申请"+"");
			return false;
		}
		State = arr[0];
		if(State==null||State==''||State=='02'){
			myAlert(""+"没有新发起的临分申请"+"");
	  	return false;
		}
		if (State!='01'){
	  	myAlert(""+"该任务未回复"+","+"不能进行办结"+"");
	  	return false;
	  }
	}else if(opeFlag=="03"){
		
	}else if(opeFlag=="04"){
		var tSql = " select state from RIUWTask b where b.serialno = (select max(a.serialno) from RIUWTask a where a.proposalgrpcontno = '000000' and a.auditcode = '"+ fmImport.OpeData.value +"' and a.AuditType = '04')" ;
		var arr=easyExecSql(tSql);	
		if(arr==null){
			myAlert(""+"没有新发起的核赔申请"+"");
			return false;
		}
		State = arr[0];
		if(State==null||State==''||State=='02'){
			myAlert(""+"没有新发起的核赔申请"+"");
	  		return false;
		}
		if (State!='01'){
	  		myAlert(""+"该任务未回复"+","+"不能进行办结"+"");
	  	return false;
	  }
	}else if(opeFlag=="05"||opeFlag=="06"){
		opeFlag="05";
		var tSql = " select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where proposalgrpcontno='"+fmImport.OpeData.value+"' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditType='05' and AuditAffixCode='000000') and proposalgrpcontno='"+fmImport.OpeData.value+"' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditType='05' and AuditAffixCode='000000' " ;
		var arr=easyExecSql(tSql);	
		if(arr==null){
			myAlert(""+"没有新发起的临分申请"+"");
			return false;
		}
		State = arr[0];
		if(State==null||State==''||State=='02'){
			myAlert(""+"没有新发起的临分申请"+"");
	  	return false;
		}
		if (State!='01'){
	  	myAlert(""+"该任务未回复"+","+"不能进行办结"+"");
	  	return false;
	  }
	}else{
		myAlert(""+"非法类型"+"");
		return false;
	}
	return true;
}
 		
function afterSubmit( FlagStr, content,result )
{
	if(+---------------------------!=null){
  	+---------------------------.close();
	}
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    if(result!=null&&result!=''){
    	fmImport.SerialNo.value = result ;
    }
    if(fm.RunRuleFlag.value!="1"){
    	assgnOperate();
    }else{
    	fm.RunRuleFlag.value="";
    }
    SearchResultGrid.clearData();
  }
}

function UpLodeReInsure(FilePath,FileName)
{
	var checkFlag;
	var edorNo;
	var edorType;
	var caseNo;
	var opeData=fmImport.OpeData.value;
	var opeFlag=fmImport.OpeFlag.value;
	var autoAnswer=fmImport.AutoAnswer.value;//0:系统锟皆讹拷锟截革拷 1:锟斤拷锟斤拷锟斤拷 
	if(autoAnswer!='0' && autoAnswer!='1')
	{
		myAlert(""+"请选择处理类型！"+"");
		return false;
	}
	if(opeFlag=="01")
	{
				
	}
	else if(opeFlag=="03")
	{ 
		edorNo="";
		edorType="";
	}
	else if(opeFlag=="04")
	{
		caseNo=opeData;
		var Count=ClaimInfoGrid.mulLineCount;		
		if(Count>0)
		{
		   opeData = ClaimInfoGrid.getRowColData(0,3);
		}
	}
	else if(opeFlag=="06")
	{
		opeFlag="05";
	}
	if(InputFlag=="0"){
		fm.Remark.value="";
	}
	var overFlag = "FALSE"; //锟斤拷锟斤拷志,FALSE:未锟斤拷幔琓RUE锟斤拷锟斤拷幔現ALSE1:锟斤拷锟斤拷锟斤拷 
	
	if(autoAnswer=='0')
	{
		fm.action = "./AutoSendSave.jsp?OverFlag="+overFlag+"&OpeFlag="+opeFlag+"&FilePath="+FilePath+"&FileName="+FileName+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&OpeData="+opeData; 
	}
	else
	{
		fm.action = "./SendUWReInsureChk.jsp?OverFlag="+overFlag+"&OpeFlag="+opeFlag+"&FilePath="+FilePath+"&FileName="+FileName+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&OpeData="+opeData; 
	}

	fm.submit();
}

function showRule(){
	//ReInsureGrid.clearData();
	if(InputFlag!=0){
		fm.Remark.value="";
	}
	
	var opeFlag=fmImport.OpeFlag.value;
	var strSQL="";
	var tSel="";
	
	if(opeFlag=="01"){
		QueryReInsureAudit();
	}
	if(opeFlag=="03"){
		QueryEdorAudit();
	}
	if(opeFlag=="04"){
		QueryClaimAudit();
	}
	if(opeFlag=="05"){
		QueryGrpNewAudit();
	}
		if(opeFlag=="06"){
		QueryGrpEdorAudit();
	}
}

/**
*锟铰碉拷锟劫凤拷锟斤拷锟诫发锟斤拷/锟截革拷锟斤拷息
*/
function  QueryReInsureAudit(){
	mSql = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,decode(x.A7,'00','"+"待回复"+"','01','"+"已回复"+"','02','"+"办结"+"',null,'"+"办结"+"','"+"未知类型"+"') from( select a.uwno A1,a.UWOperator A2,a.UWIdea A3,a.ModifyDate A4,a.AdjunctPath A5,'"+"核保发送"+"' A6,(select State from RIUWTask b where serialno = a.serialno) A7 from RIUWIdea a where a.serialno=(select max(b.serialno) from RIUWTask b where b.AuditType='01' and b.Proposalgrpcontno='000000' and b.proposalcontno='"+fmImport.OpeData.value+"') "
  + " union all " 
  + " select a.UWNo A1,a.ReInsurer A2,a.UWIdea A3,a.ModifyDate A4,a.AdjunctPath A5,'"+"再保回复"+"' A6, (select State from RIUWTask b where serialno = a.serialno) A7 from RIAnwserIdea a where  a.serialno=(select max(b.serialno) from RIUWTask b where b.AuditType='01' and b.Proposalgrpcontno='000000' and b.proposalcontno='"+fmImport.OpeData.value+"') ) x order by x.A1 desc,x.A6 "
  ;
	turnPage2.queryModal(mSql,ReInsureAuditGrid);
}
 /**
 * 锟斤拷全锟劫凤拷锟斤拷锟诫发锟斤拷/锟截革拷锟斤拷息
 */
function QueryEdorAudit(){
	var checkFlag=RiskInfoGrid.getSelNo();
	var polNo= RiskInfoGrid.getRowColData(checkFlag - 1,8);
	var dutyCode= RiskInfoGrid.getRowColData(checkFlag - 1,3);
	var edorNo= RiskInfoGrid.getRowColData(checkFlag - 1,9);
	var edorType=RiskInfoGrid.getRowColData(checkFlag - 1,10);
	//mSql = "select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.UWOperator,a.UWIdea ,a.ModifyDate,a.AdjunctPath,'锟剿憋拷锟斤拷锟斤拷' "
	//+ " from RIUWIdea a where a.AuditType='03' and trim(a.PolNo)='"+polNo+"' and trim(a.dutycode)='"+dutyCode+"' and trim(a.AuditCode)='"+edorNo+"' and trim(a.AuditAffixCode)='"+edorType+"'"
	//+ " union all"
	//+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.ReInsurer,a.UWIdea,a.ModifyDate,a.AdjunctPath,'锟劫憋拷锟截革拷' "
	//+ " from RIAnwserIdea a where a.AuditType='03' and trim(a.PolNo)='"+polNo+"' and trim(a.dutycode)='"+dutyCode+"' and a.AuditCode='"+edorNo+"' and a.AuditAffixCode='"+edorType+"'"
	//;
	//turnPage2.queryModal(mSql,ReInsureAuditGrid);
}
/**
* 锟斤拷锟斤拷锟斤拷锟诫发锟斤拷/锟截革拷锟斤拷息
*/ 
function QueryClaimAudit(){

	mSql = " select x.A1,x.A2,x.A3,x.A4,x.A5,x.A6,decode(x.A7,'00','"+"待回复"+"','01','"+"已回复"+"','02','"+"办结"+"',null,'"+"办结"+"','"+"未知类型"+"') from( select a.uwno A1,a.UWOperator A2,a.UWIdea A3,a.ModifyDate A4,a.AdjunctPath A5,'"+"核赔发送"+"' A6,(select State from RIUWTask b where serialno = a.serialno) A7 from RIUWIdea a where a.serialno=(select max(b.serialno) from RIGrpState b where  b.Proposalgrpcontno='000000' and b.caseno='"+fmImport.OpeData.value+"') "
	+ " union all " 
	+ " select a.UWNo A1,a.ReInsurer A2,a.UWIdea A3,a.ModifyDate A4,a.AdjunctPath A5,'"+"再保回复"+"' A6, (select State from RIUWTask b where serialno = a.serialno) A7 from RIAnwserIdea a where  a.serialno=(select max(b.serialno) from RIGrpState b where b.Proposalgrpcontno='000000' and b.caseno='"+fmImport.OpeData.value+"') ) x order by x.A1 ,x.A6 ";
	
	turnPage2.queryModal(mSql,ReInsureAuditGrid);
	
}

/**
* 锟斤拷锟斤拷锟劫凤拷锟斤拷锟诫发锟斤拷/锟截革拷锟斤拷息
*/
function QueryGrpNewAudit(){
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'"+"核保发送"+"',(select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and a.AuditType=AuditType and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and trim(AuditCode)=trim(AuditCode) and trim(AuditAffixCode)=trim(AuditAffixCode) and a.UWNo=UWNo) "
	+ " from RIUWIdea a where trim(a.ProposalGrpContNo)='"+fmImport.OpeData.value+"' and a.AuditType='05' and trim(a.PolNo)='000000' and trim(a.dutycode)='000000' and AuditCode='000000' and AuditAffixCode='000000' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'"+"再保回复"+"',(select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and trim(a.AuditType)=trim(AuditType) and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and trim(AuditCode)=trim(AuditCode) and trim(AuditAffixCode)=trim(AuditAffixCode) and a.UWNo=UWNo) "
	+ " from RIAnwserIdea a where a.ProposalGrpContNo='"+fmImport.OpeData.value+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000'  and AuditCode='000000' and AuditAffixCode='000000') order by uwno desc"
	;
	turnPage2.queryModal(mSql,ReInsureAuditGrid);
}

function QueryGrpEdorAudit(){
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'"+"核保发送"+"',(select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and trim(a.AuditType)=trim(AuditType) and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and trim(AuditCode)=trim(AuditCode) and trim(AuditAffixCode)=trim(AuditAffixCode) and a.UWNo=UWNo) "
	+ " from RIUWIdea a where trim(a.ProposalGrpContNo)='"+fmImport.OpeData.value+"' and trim(a.AuditType)='05' and trim(a.PolNo)='000000' and trim(a.dutycode)='000000' and trim(AuditCode)='000000' and trim(AuditAffixCode)='000000' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'"+"再保回复"+"',(select case State when '00' then '"+"待回复"+"' when '01' then '"+"已回复"+"' else '"+"办结"+"' end from RIUWTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and a.AuditType=AuditType and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and AuditCode=AuditCode and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) "
	+ " from RIAnwserIdea a where a.ProposalGrpContNo='"+fmImport.OpeData.value+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000'  and AuditCode='000000' and AuditAffixCode='000000') order by uwno desc"
	;	
}

function +---------------------------mation(){
	tSel=ReInsureAuditGrid.getSelNo();
	fmInfo.RemarkInfo.value=ReInsureAuditGrid.getRowColData(tSel-1,3)+"";
}

/**锟斤拷锟斤拷锟斤拷锟斤拷锟铰硷拷锟斤拷
*/
function clearData(){ 
	if(InputFlag=="0"){
		fm.Remark.value=""; 
	}
	InputFlag="1"; 
}

//锟斤拷锟斤拷锟斤拷
function AuditEnd(){
	var tsql = "";
	var arr;
	if(fmImport.OpeFlag.value=="01"){//锟斤拷锟斤拷锟斤拷锟斤拷
		if(fmImport.SerialNo.value!=null&&fmImport.SerialNo.value!=''){
			tsql = " select State from RIGrpState where trim(SerialNo)='"+fmImport.SerialNo.value+"' ";
			;
			arr=easyExecSql(tsql);
			if(arr=='03'||arr=='04'){
				myAlert(""+"审核完毕状态，不能操作"+"");
  		  return false;
			}
			tsql = " select state from RIUWTask a where a.SerialNo='"+fmImport.SerialNo.value+"' "
			;
			arr=easyExecSql(tsql);	
			if(arr=='00'||arr=='01'){
  		  myAlert(""+"该任务未办结，不能置审核完毕状态!"+"");
  		  return false;
			}
		}else{//没锟斤拷锟斤拷锟?
			//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷欠锟斤拷小锟斤拷锟斤拷俜锟斤拷锟剿★拷锟斤拷锟斤拷锟街憋拷锟斤拷锟斤拷锟斤拷锟斤拷锟剿碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟?
			myAlert(" "+"请先发送审核任务"+" ");
			return false;
		}
		
		var overFlag="FALSE1"; //锟斤拷锟斤拷志
		var proposalContNo = fmImport.OpeData.value;
		var opeFlag = fmImport.OpeFlag.value;
		var uwConclusionInfo = fmInfo.UWConclusionInfo.value;
		var serialNo = fmImport.SerialNo.value;
		fm.action = "./SendUWReInsureChk.jsp?OverFlag="+overFlag+"&OpeData="+proposalContNo+"&OpeFlag="+opeFlag+"&UWConclusionInfo="+uwConclusionInfo+"&SerialNo="+serialNo ; 
		
	}else if(fmImport.OpeFlag.value=="03"){
		
	}else if(fmImport.OpeFlag.value=="04"){ //锟斤拷锟斤拷 锟斤拷锟斤拷锟斤拷
		if(fmImport.SerialNo.value!=null&&fmImport.SerialNo.value!=''){
			tsql = " select State from RIGrpState where trim(SerialNo)='"+fmImport.SerialNo.value+"' ";
			arr=easyExecSql(tsql);
			if(arr=='03'||arr=='04'){
				myAlert(""+"审核完毕状态，不能操作"+"");
  		  		return false;
			}
			tsql = " select state from RIUWTask a where a.SerialNo='"+fmImport.SerialNo.value+"' ";
			arr=easyExecSql(tsql);	
			if(arr=='00'||arr=='01'){
  		  		myAlert(""+"该任务未办结，不能置审核完毕状态!"+"");
  		  		return false;
			}
		}else{//没锟斤拷锟斤拷锟?锟斤拷锟斤拷锟斤拷
			myAlert(" "+"请先发送审核任务"+" ");
			return false;
		}
		
		var overFlag="FALSE1"; //锟斤拷锟斤拷志
		var caseNo = fmImport.OpeData.value;
		var proposalContNo="";
		var Count=ClaimInfoGrid.mulLineCount;	
		var opeFlag = fmImport.OpeFlag.value;
		var uwConclusionInfo = fmInfo.UWConclusionInfo.value;
		var serialNo = fmImport.SerialNo.value;
			
		if(Count>0){
		   proposalContNo = ClaimInfoGrid.getRowColData(0,3);
		}

		fm.action = "./SendUWReInsureChk.jsp?OverFlag="+overFlag+"&OpeData="+proposalContNo+"CaseNo"+caseNo+"&OpeFlag="+opeFlag+"&UWConclusionInfo="+uwConclusionInfo+"&SerialNo="+serialNo ; 
				
	}else if(fmImport.OpeFlag.value=="05"||fmImport.OpeFlag.value=="06"){
		tsql = " select State from RIGrpState where trim(SerialNo)='"+fmImport.SerialNo.value+"' ";
		;
		arr=easyExecSql(tsql);
		if(arr=='03'||arr=='04'){
			myAlert(""+"审核完毕状态，不能操作"+""); 
  	  return false;
		}
		var tsql = " select state from RIUWTask a where a.SerialNo='"+fmImport.SerialNo.value+"' "                                                                                                                                                                         
		;                                                                                                                                                                                                                                                                  
		var arr=easyExecSql(tsql);	                                                                                                                                                                                                                                       
		if(arr=='00'||arr=='01'){                                                                                                                                                                                                                                          
		  myAlert(""+"该任务未办结，不能置审核完毕状态!"+"");                                                                                                                                                                                                                      
		  return false;                                                                                                                                                                                                                                                    
		}                                                                                                                                                                                                                                                           
		var overFlag="FALSE1"; //锟斤拷锟斤拷志                                                                                                                                                                                                                                  
		var proposalGrpContNo = fmImport.OpeData.value;                                                                                                                                                                                                                    
		fm.action = "./SendUWReInsureChk.jsp?OverFlag="+overFlag+"&OpeData="+fm.OpeData.value+"&OpeFlag=10&ProposalContNo="+proposalContNo+"&ProposalGrpContNo="+proposalGrpContNo+"&UWConclusionInfo="+fmInfo.UWConclusionInfo.value+"&SerialNo="+fmImport.SerialNo.value;
	} 
	fm.submit();
	  
} 

function CloseWindow(){
	top.close();
}  
