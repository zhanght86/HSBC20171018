/*********************************************************************
 *  程序名称：RIUWTaskQueryInput.js
 *  程序功能：再保审核任务查询
 *  创建日期： 
 *  创建人  ：
 *  返回值：  无
 *  更新记录：  更新人    更新日期     更新原因/内容
 *********************************************************************
 */
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();

var ClmNo;    //理赔号
var PrtNo;    //印刷号码
var ContNo    //合同号

var ContType; //保单类型 1-个险 2-团险
var AuditType;//业务类型 01 新单 03 保全 04 理赔
var ImportPath;
window.onfocus=myonfocus;

//重置
function ResetForm(){
	initInpBox();
}
//任务查询（任务列表）
function SearchRecord(){

	ContType = fm.ContType.value;
	AuditType= fm.AuditType.value;
	if(ContType=="1"){ //个险
		if(AuditType=="01"){
		//新单
			QueryIndRiskTask();
		}
		if(AuditType=="03"){
		//保全
			QueryIndEdorTask();
		}
		if(AuditType=="04"){
		//理赔
			QueryIndClaimTask();
		}
	}
}

/**
* 显示任务查询列表
* 个险新单
*/

function QueryIndRiskTask(){
	var PrtNo = fm.nbPrtNo.value;
	var ContNo =fm.ContNo.value;
	var nbStateType = fm.nbStateType.value;
	var mSQL="";

	TaskLiskGrid.clearData();
	RiskInfoGrid.clearData();
	AuditInfoGrid.clearData();
	ReInsureAuditGrid.clearData();
	fm.SendAnswerRemark.value="";
	
	if(fm.DeTailFlag.value=='1'){ //险种
		mSQL = "select (select distinct b.PrtNo from ripolindview b where a.proposalcontno = b.proposalcontno), a.proposalcontno, decode(a.State,'00','"+"待回复"+"', '01','"+"已回复"+"','02', '"+"已办结"+"','"+"未知类型"+"'), a.uwno from RIUWTask a where a.serialno in (select c.serialno from RIGrpState c where c.ProposalGrpContNo = '000000' and c.exetype<>'04'";

		if(PrtNo!=="" && PrtNo!==null){
			mSQL = mSQL+ " and c.ProposalContNo ='"+PrtNo+"'";
	    }
	    if(nbStateType!=""&&nbStateType!=null){
	    	mSQL += " and c.state = '"+nbStateType+"'";
	    }
	    mSQL = mSQL+ ")";
	    
	}else{//责任
		mSQL = "select (select distinct b.PrtNo from RIPolDutyIndView b where a.proposalcontno = b.proposalcontno), a.polno, decode(a.State,'00','"+"待回复"+"', '01','"+"已回复"+"','02', '"+"已办结"+"','"+"未知类型"+"'), a.uwno from RIUWTask a where a.serialno in (select c.serialno from RIGrpState c where c.ProposalGrpContNo = '000000' and c.exetype<>'04'";

		if(PrtNo!=="" && PrtNo!==null){
			mSQL = mSQL+ " and c.ProposalContNo ='"+PrtNo+"'";
	    }
	    if(nbStateType!=""&&nbStateType!=null){
	    	mSQL += " and c.state = '"+nbStateType+"'";
	    }
	    mSQL = mSQL+ ")";
	}

	turnPage.queryModal(mSQL,TaskLiskGrid);
}
/**
* 显示任务查询列表
* 个险保全
*/

function QueryIndEdorTask(){
    var mSQL = "";
//--------------------------------------------------
//  初始化页面
	TaskLiskGrid.clearData();
	RiskInfoGrid.clearData();
	AuditInfoGrid.clearData();
	ReInsureAuditGrid.clearData();
	fm.SendAnswerRemark.value="";
//--------------------------------------------------
	
	
	turnPage.queryModal(mSQL,TaskLiskGrid);
}

/**
* 显示任务查询列表
* 个险理赔
*/

function QueryIndClaimTask(){
	ClmNo = fm.ClmNo.value; 
	PrtNo = fm.nbPrtNo.value;
	var lcStateType = fm.lcStateType.value;
	
	var mSQL = "";
//--------------------------------------------------
//  初始化页面	
	TaskLiskGrid.clearData();
	RiskInfoGrid.clearData();
	AuditInfoGrid.clearData();
	ReInsureAuditGrid.clearData();
	fm.SendAnswerRemark.value="";
//---------------------------------------------------	
	if(fm.DeTailFlag.value=='1'){ //险种
		mSQL = "select (select distinct b.PrtNo from ripolindview b where b.contno=(select distinct c.contno from Riclaimriskdetailview c where c.CaseNo=a.auditcode and rownum = 1 )), a.proposalcontno, decode(a.State, '00','"+"待回复"+"','01', '"+"已回复"+"', '02', '"+"已办结"+"', '"+"未知类型"+"'), a.uwno, c.caseno from RIUWTask a, RIGrpState c where a.audittype = '04' and a.serialno = c.serialno and a.ProposalGrpContNo = '000000'";

		if(ClmNo!=="" && ClmNo!==null){
			mSQL+=" and c.caseno ='"+ClmNo+"'";
	    }
	    if(PrtNo!="" && PrtNo!=null){
	    	mSQL+=" and a.ProposalContNo ='"+PrtNo+"'";
	    }
	    if(lcStateType!="" && lcStateType!=null){
	    	mSQL+="and a.SerialNo in(select SerialNo from RIGrpState where state='"+lcStateType+"')";
	    }
	}else{
		mSQL = "select (select distinct b.PrtNo from RIPolDutyIndView b where b.contno=(select distinct c.contno from Riclaimriskdetailview c where c.CaseNo=a.auditcode and rownum = 1)), a.polno, decode(a.State, '00','"+"待回复"+"','01', '"+"已回复"+"', '02', '"+"已办结"+"', '"+"未知类型"+"'), a.uwno, c.caseno from RIUWTask a, RIGrpState c where a.audittype = '04' and a.serialno = c.serialno and a.ProposalGrpContNo = '000000'";

		if(ClmNo!=="" && ClmNo!==null){
			mSQL+=" and c.caseno ='"+ClmNo+"'";
	    }
	    if(PrtNo!="" && PrtNo!=null){
	    	mSQL+=" and a.ProposalContNo ='"+PrtNo+"'";
	    }
	    if(lcStateType!="" && lcStateType!=null){
	    	mSQL+="and a.SerialNo in(select SerialNo from RIGrpState where state='"+lcStateType+"')";
	    }	
	}
	turnPage.queryModal(mSQL,TaskLiskGrid);
}

//=================================================================================
/**
* 查询任务下的信息
*/
function QueryTaskInfo(){
	if(fm.ContType.value=="1"){//个险
	     //新单
		if(fm.AuditType.value=="01"){
			RiskInfoGrid.clearData();
			AuditInfoGrid.clearData();
			fm.SendAnswerRemark.value="";

			QueryIndRiskTaskInfo();
		}
		 //保全
		if(fm.AuditType.value=="03"){
			QueryIndEdorTaskInfo();
		}
		 //理赔
		if(fm.AuditType.value=="04"){
			RiskInfoGrid.clearData();
			AuditInfoGrid.clearData();
			fm.SendAnswerRemark.value="";
					
			QueryIndClaimTaskInfo();
		}
	}else{ //团险
	
	}
}

//查询任务下的个险新单信息
function QueryIndRiskTaskInfo(){
	try{
		var tSel = TaskLiskGrid.getSelNo();
		var mSQL = "";
		//核保/核赔结果列表 
			
		if(fm.DeTailFlag.value=='1'){
		    mSQL = "select x.A1,x.A2,x.A3,'000000',(select b.riskname from lmrisk b where b.riskcode=x.A3),x.A5 from ( select a.insuredno A1,a.insuredname A2,(select b.RiskCode from ripolindview b where b.polno=a.polno ) A3,a.uwerror A5 from lcuwerror a where exists (select * from lmuw b where b.uwcode = a.uwrulecode and trim(b.uwtype)='LF') and a.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' and a.uwno= (select max(c.uwno) from lcuwerror c where exists (select * from lmuw b where b.uwcode = c.uwrulecode and trim(b.uwtype)='LF') and c.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' ) order by a.polno,uwno) x ";
		}else{
			mSQL = "";
		}		
		turnPage1.queryModal(mSQL,AuditInfoGrid);
		
		//任务信息
		RiskInfoGrid.clearData();
		if(fm.DeTailFlag.value=='1'){ //险种
			mSQL = "select a.insuredno A1, a.Insuredname A2,a.RiskCode A3,'000000' A4, a.Amnt A5, a.RiskAmnt A6, a.AccAmnt A7, decode(b.State,'00','"+"自动分保"+"','01','"+"满足临分条件"+"','02','"+"待临分审核"+"','03','"+"临时分保"+"','04','"+"临分未实现"+"')A8, b.State A9 from ripolindview a left join RIDutyState b on( b.proposalno = a.proposalno and b.proposalgrpcontno = '000000' and b.dutycode = '000000') where a.prtNo = '"+TaskLiskGrid.getRowColData(tSel-1,1)+"'";
		}else{ //责任
			mSQL = "select x.A1,x.A2, x.A3,x.A4,x.A5,x.A6,x.A7,(case when x.A8 = '00' then '"+"自动分保"+"' when x.A8 = '01' then' "+"满足临分条件"+"' when x.A8 = '02' then '"+"待临分审核"+"' when x.A8 = '03' then '"+"临时分保"+"' when x.A8 = '04' then '"+"临分未实现"+"' end),x.A8 from (select a.insuredno A1, a.Insuredname A2,a.RiskCode A3,a.dutycode A4,a.Amnt A5, a.RiskAmnt A6, a.AccAmnt A7, (select trim(b.State) from RIDutyState b where trim(b.proposalno) = trim(a.proposalno) and trim(b.proposalgrpcontno) = '000000' ) A8 from RIPolDutyIndView a where a.prtNo = '"+TaskLiskGrid.getRowColData(tSel-1,1)+"') x";
		}		
		turnPage2.queryModal(mSQL,RiskInfoGrid); 
		
		//显示个险新单发送回复记录（再保审核任务）
		QueryReInsureAudit(); 
	}catch(ex){
	    myAlert(ex);
    }
}

//查询任务下的个险理赔信息
function QueryIndClaimTaskInfo(){
	try{
		var tSel = TaskLiskGrid.getSelNo();

		var mSQL = "";
		//核保/核赔结果列表 
		AuditInfoGrid.clearData();
		
		if(fm.DeTailFlag.value=='1'){ //险种
			mSQL = "select a.insuredno, a.insuredname,a.riskcode, '000000',(select b.riskname from lmrisk b where b.riskcode=a.riskcode), a.uwerror from RIGUWError a where  a.CalItemType = '14' and a.auditcode='"+TaskLiskGrid.getRowColData(tSel-1,5)+"'";		
		}else{
			mSQL = "select a.insuredno, a.insuredname,'000000', a.DutyCode,'', a.uwerror from RIGUWError a where  a.CalItemType = '14' and a.auditcode='"+TaskLiskGrid.getRowColData(tSel-1,5)+"'";
		}
		turnPage1.queryModal(mSQL,AuditInfoGrid);
		
		//任务信息
		RiskInfoGrid.clearData();
		
		if(fm.DeTailFlag.value=='1'){ //险种
			mSQL = "select x.A1, x.A2, x.A3, x.A4, x.A5, x.A6,x.A7,decode( x.A8 , '01' , '"+"通知限额"+"' , '02' , '"+"参与限额"+"' , '03' , '"+"非正常理赔"+"' ), x.A8 from (select a.insuredno A1, a.Insuredname A2,a.RiskCode A3,'000000' A4,(select b.Amnt from RIPolIndView b where b.PolNo=a.PolNo) A5, a.CaseNo A6,a.ClmGetMoney A7,(select c.State from RIClaimState c where c.CaseNo = a.CaseNo and c.riskcode = a.RiskCode and c.dutycode = '000000') A8 from RIClaimRiskDetailView a where a.caseno = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"')x";
		}else{ //责任
			mSQL = "select x.A1, x.A2, x.A3, x.A4, x.A5, x.A6,x.A7,(case when x.A8 = '01' then '"+"通知限额"+"' when x.A8 = '02' then '"+"参与限额"+"' when x.A8 = '03' then '"+"非正常理赔"+"' end), x.A8 from (select a.insuredno A1, a.Insuredname A2,'000000' A3,a.dutycode A4,(select b.Amnt from RIPolIndView b where b.PolNo=a.PolNo) A5, a.CaseNo A6,a.ClmGetMoney A7,(select trim(c.State) from RIClaimState c where c.CaseNo = a.CaseNo and c.riskcode = a.RiskCode and trim(c.dutycode) = '000000') A8 from RIClaimDutyDetailView a where a.caseno = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"')x";
		}		
		turnPage2.queryModal(mSQL,RiskInfoGrid); 

		//显示个险新单发送回复记录（再保审核任务）
		QueryClaimAudit();
	}catch(ex){
	    myAlert(ex);
    }
}

//====================================================================================
/**
*查询新单任务发起与回复信息
*/
function  QueryReInsureAudit(){
	
	ReInsureAuditGrid.clearData();
	var mSql="";
	var tSel = TaskLiskGrid.getSelNo();	
 	var tOpeData = TaskLiskGrid.getRowColData(tSel-1,2);
	mSql = "select x.A1,x.A2,x.A3,x.A4,x.A5, x.A6,decode(x.A7, '00','"+"待回复"+"', '01', '"+"已回复"+"','02','"+"已办结"+"',null,'"+"办结"+"','"+"未知类型"+"'),x.A8 from (select a.proposalgrpcontno A1, a.uwno A2,a.UWOperator A3, a.ModifyDate A4, a.AdjunctPath A5,'"+"核保发送"+"' A6,(select State from RIUWTask where ProposalContNo = a.ProposalContNo and a.AuditType = AuditType and a.UWNo = UWNo) A7, a.UWIdea A8 from RIUWIdea a where a.serialno = (select max(b.serialno) from RIUWTask b where b.AuditType = '01' and b.Proposalgrpcontno = '000000' and b.proposalcontno = '"+tOpeData+"')";
	mSql+= " union all ";
	mSql+= "select a.proposalgrpcontno A1, a.UWNo A2, a.ReInsurer A3, a.ModifyDate A4, a.AdjunctPath A5, '"+"再保回复"+"' A6, (select State  from RIUWTask where ProposalContNo = a.ProposalContNo and a.AuditType = AuditType and a.UWNo = UWNo) A7, a.UWIdea A8 from RIAnwserIdea a  where a.serialno = (select max(b.serialno) from RIUWTask b  where b.AuditType = '01' and b.Proposalgrpcontno = '000000' and b.proposalcontno = '"+tOpeData+"')) x order by x.A2 ";
	
	turnPage3.queryModal(mSql,ReInsureAuditGrid);
}

/**
*查询理赔任务发起与回复信息
*/
function QueryClaimAudit(){
	var tSel = TaskLiskGrid.getSelNo();	
 	var proposalcontno = TaskLiskGrid.getRowColData(tSel-1,2);
 	var clmNo = TaskLiskGrid.getRowColData(tSel-1,5);
	ReInsureAuditGrid.clearData();
	var mSql = "select x.A1,x.A2,x.A3,x.A4,x.A5, x.A6,decode(x.A7, '00','"+"待回复"+"', '01', '"+"已回复"+"','02','"+"已办结"+"',null,'"+"办结"+"','"+"未知类型"+"'),x.A8 from ";
	    mSql +=" (select a.proposalgrpcontno A1,a.UWNo A2,a.UWOperator A3,a.ModifyDate A4, a.AdjunctPath A5,'"+"核保发送"+"' A6,(select State  from RIUWTask where SerialNo = a.serialno) A7, a.UWIdea A8 from RIUWIdea a where a.serialno = (select max(b.serialno) from RIUWTask b  where b.AuditType = '04' and b.Proposalgrpcontno = '000000' and trim(b.AuditCode) = '"+clmNo+"')" ;
	    mSql +=" union all " ;
	    mSql +=" select a.proposalgrpcontno A1, a.UWNo A2, a.ReInsurer A3,a.ModifyDate A4, a.AdjunctPath A5,'"+"再保回复"+"' A6,(select State  from RIUWTask where SerialNo = a.serialno) A7, a.UWIdea A8 from RIAnwserIdea a where a.serialno = (select max(b.serialno) from RIUWTask b  where b.AuditType = '04' and b.Proposalgrpcontno = '000000' and trim(b.AuditCode) = '"+clmNo+"'))x order by x.A2 desc";
	turnPage3.queryModal(mSql,ReInsureAuditGrid);
}


//=============================================================================
/**
*显示发起与回复信息
*/
function showAnswerIdea(){
	var checkFlag=ReInsureAuditGrid.getSelNo();
	fm.SendAnswerRemark.value= ReInsureAuditGrid.getRowColData(checkFlag - 1,8);
}

//下载
function DownLoad(){
	tSel = ReInsureAuditGrid.getSelNo();
	if(tSel=='0'||tSel==null){
		myAlert(""+"请先选择再保审核任务信息！"+"");
		return false;
	}
	 
	var FilePath = ReInsureAuditGrid.getRowColData(tSel - 1, 5);  
	if (FilePath==""||FilePath==null){
	 myAlert(""+"没有附件"+","+"不能进行下载操作！"+"");
	 return false;
	}     
   //var showStr="正在下载数据……";
   fmImport.action = "./DownLoadSave.jsp?FilePath="+FilePath;
   fmImport.submit();
}

function afterCodeSelect( codeName, Field )
{
	TaskLiskGrid.clearData();
	RiskInfoGrid.clearData();
	AuditInfoGrid.clearData();
	ReInsureAuditGrid.clearData();
	fm.SendAnswerRemark.value="";
}

