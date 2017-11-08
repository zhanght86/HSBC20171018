/*********************************************************************
 *  �������ƣ�RIUWTaskQueryInput.js
 *  �����ܣ��ٱ���������ѯ
 *  �������ڣ� 
 *  ������  ��
 *  ����ֵ��  ��
 *  ���¼�¼��  ������    ��������     ����ԭ��/����
 *********************************************************************
 */
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();

var ClmNo;    //�����
var PrtNo;    //ӡˢ����
var ContNo    //��ͬ��

var ContType; //�������� 1-���� 2-����
var AuditType;//ҵ������ 01 �µ� 03 ��ȫ 04 ����
var ImportPath;
window.onfocus=myonfocus;

//����
function ResetForm(){
	initInpBox();
}
//�����ѯ�������б�
function SearchRecord(){

	ContType = fm.ContType.value;
	AuditType= fm.AuditType.value;
	if(ContType=="1"){ //����
		if(AuditType=="01"){
		//�µ�
			QueryIndRiskTask();
		}
		if(AuditType=="03"){
		//��ȫ
			QueryIndEdorTask();
		}
		if(AuditType=="04"){
		//����
			QueryIndClaimTask();
		}
	}
}

/**
* ��ʾ�����ѯ�б�
* �����µ�
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
	
	if(fm.DeTailFlag.value=='1'){ //����
		mSQL = "select (select distinct b.PrtNo from ripolindview b where a.proposalcontno = b.proposalcontno), a.proposalcontno, decode(a.State,'00','"+"���ظ�"+"', '01','"+"�ѻظ�"+"','02', '"+"�Ѱ��"+"','"+"δ֪����"+"'), a.uwno from RIUWTask a where a.serialno in (select c.serialno from RIGrpState c where c.ProposalGrpContNo = '000000' and c.exetype<>'04'";

		if(PrtNo!=="" && PrtNo!==null){
			mSQL = mSQL+ " and c.ProposalContNo ='"+PrtNo+"'";
	    }
	    if(nbStateType!=""&&nbStateType!=null){
	    	mSQL += " and c.state = '"+nbStateType+"'";
	    }
	    mSQL = mSQL+ ")";
	    
	}else{//����
		mSQL = "select (select distinct b.PrtNo from RIPolDutyIndView b where a.proposalcontno = b.proposalcontno), a.polno, decode(a.State,'00','"+"���ظ�"+"', '01','"+"�ѻظ�"+"','02', '"+"�Ѱ��"+"','"+"δ֪����"+"'), a.uwno from RIUWTask a where a.serialno in (select c.serialno from RIGrpState c where c.ProposalGrpContNo = '000000' and c.exetype<>'04'";

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
* ��ʾ�����ѯ�б�
* ���ձ�ȫ
*/

function QueryIndEdorTask(){
    var mSQL = "";
//--------------------------------------------------
//  ��ʼ��ҳ��
	TaskLiskGrid.clearData();
	RiskInfoGrid.clearData();
	AuditInfoGrid.clearData();
	ReInsureAuditGrid.clearData();
	fm.SendAnswerRemark.value="";
//--------------------------------------------------
	
	
	turnPage.queryModal(mSQL,TaskLiskGrid);
}

/**
* ��ʾ�����ѯ�б�
* ��������
*/

function QueryIndClaimTask(){
	ClmNo = fm.ClmNo.value; 
	PrtNo = fm.nbPrtNo.value;
	var lcStateType = fm.lcStateType.value;
	
	var mSQL = "";
//--------------------------------------------------
//  ��ʼ��ҳ��	
	TaskLiskGrid.clearData();
	RiskInfoGrid.clearData();
	AuditInfoGrid.clearData();
	ReInsureAuditGrid.clearData();
	fm.SendAnswerRemark.value="";
//---------------------------------------------------	
	if(fm.DeTailFlag.value=='1'){ //����
		mSQL = "select (select distinct b.PrtNo from ripolindview b where b.contno=(select distinct c.contno from Riclaimriskdetailview c where c.CaseNo=a.auditcode and rownum = 1 )), a.proposalcontno, decode(a.State, '00','"+"���ظ�"+"','01', '"+"�ѻظ�"+"', '02', '"+"�Ѱ��"+"', '"+"δ֪����"+"'), a.uwno, c.caseno from RIUWTask a, RIGrpState c where a.audittype = '04' and a.serialno = c.serialno and a.ProposalGrpContNo = '000000'";

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
		mSQL = "select (select distinct b.PrtNo from RIPolDutyIndView b where b.contno=(select distinct c.contno from Riclaimriskdetailview c where c.CaseNo=a.auditcode and rownum = 1)), a.polno, decode(a.State, '00','"+"���ظ�"+"','01', '"+"�ѻظ�"+"', '02', '"+"�Ѱ��"+"', '"+"δ֪����"+"'), a.uwno, c.caseno from RIUWTask a, RIGrpState c where a.audittype = '04' and a.serialno = c.serialno and a.ProposalGrpContNo = '000000'";

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
* ��ѯ�����µ���Ϣ
*/
function QueryTaskInfo(){
	if(fm.ContType.value=="1"){//����
	     //�µ�
		if(fm.AuditType.value=="01"){
			RiskInfoGrid.clearData();
			AuditInfoGrid.clearData();
			fm.SendAnswerRemark.value="";

			QueryIndRiskTaskInfo();
		}
		 //��ȫ
		if(fm.AuditType.value=="03"){
			QueryIndEdorTaskInfo();
		}
		 //����
		if(fm.AuditType.value=="04"){
			RiskInfoGrid.clearData();
			AuditInfoGrid.clearData();
			fm.SendAnswerRemark.value="";
					
			QueryIndClaimTaskInfo();
		}
	}else{ //����
	
	}
}

//��ѯ�����µĸ����µ���Ϣ
function QueryIndRiskTaskInfo(){
	try{
		var tSel = TaskLiskGrid.getSelNo();
		var mSQL = "";
		//�˱�/�������б� 
			
		if(fm.DeTailFlag.value=='1'){
		    mSQL = "select x.A1,x.A2,x.A3,'000000',(select b.riskname from lmrisk b where b.riskcode=x.A3),x.A5 from ( select a.insuredno A1,a.insuredname A2,(select b.RiskCode from ripolindview b where b.polno=a.polno ) A3,a.uwerror A5 from lcuwerror a where exists (select * from lmuw b where b.uwcode = a.uwrulecode and trim(b.uwtype)='LF') and a.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' and a.uwno= (select max(c.uwno) from lcuwerror c where exists (select * from lmuw b where b.uwcode = c.uwrulecode and trim(b.uwtype)='LF') and c.proposalcontno='"+TaskLiskGrid.getRowColData(tSel-1,2)+"' ) order by a.polno,uwno) x ";
		}else{
			mSQL = "";
		}		
		turnPage1.queryModal(mSQL,AuditInfoGrid);
		
		//������Ϣ
		RiskInfoGrid.clearData();
		if(fm.DeTailFlag.value=='1'){ //����
			mSQL = "select a.insuredno A1, a.Insuredname A2,a.RiskCode A3,'000000' A4, a.Amnt A5, a.RiskAmnt A6, a.AccAmnt A7, decode(b.State,'00','"+"�Զ��ֱ�"+"','01','"+"�����ٷ�����"+"','02','"+"���ٷ����"+"','03','"+"��ʱ�ֱ�"+"','04','"+"�ٷ�δʵ��"+"')A8, b.State A9 from ripolindview a left join RIDutyState b on( b.proposalno = a.proposalno and b.proposalgrpcontno = '000000' and b.dutycode = '000000') where a.prtNo = '"+TaskLiskGrid.getRowColData(tSel-1,1)+"'";
		}else{ //����
			mSQL = "select x.A1,x.A2, x.A3,x.A4,x.A5,x.A6,x.A7,(case when x.A8 = '00' then '"+"�Զ��ֱ�"+"' when x.A8 = '01' then' "+"�����ٷ�����"+"' when x.A8 = '02' then '"+"���ٷ����"+"' when x.A8 = '03' then '"+"��ʱ�ֱ�"+"' when x.A8 = '04' then '"+"�ٷ�δʵ��"+"' end),x.A8 from (select a.insuredno A1, a.Insuredname A2,a.RiskCode A3,a.dutycode A4,a.Amnt A5, a.RiskAmnt A6, a.AccAmnt A7, (select trim(b.State) from RIDutyState b where trim(b.proposalno) = trim(a.proposalno) and trim(b.proposalgrpcontno) = '000000' ) A8 from RIPolDutyIndView a where a.prtNo = '"+TaskLiskGrid.getRowColData(tSel-1,1)+"') x";
		}		
		turnPage2.queryModal(mSQL,RiskInfoGrid); 
		
		//��ʾ�����µ����ͻظ���¼���ٱ��������
		QueryReInsureAudit(); 
	}catch(ex){
	    myAlert(ex);
    }
}

//��ѯ�����µĸ���������Ϣ
function QueryIndClaimTaskInfo(){
	try{
		var tSel = TaskLiskGrid.getSelNo();

		var mSQL = "";
		//�˱�/�������б� 
		AuditInfoGrid.clearData();
		
		if(fm.DeTailFlag.value=='1'){ //����
			mSQL = "select a.insuredno, a.insuredname,a.riskcode, '000000',(select b.riskname from lmrisk b where b.riskcode=a.riskcode), a.uwerror from RIGUWError a where  a.CalItemType = '14' and a.auditcode='"+TaskLiskGrid.getRowColData(tSel-1,5)+"'";		
		}else{
			mSQL = "select a.insuredno, a.insuredname,'000000', a.DutyCode,'', a.uwerror from RIGUWError a where  a.CalItemType = '14' and a.auditcode='"+TaskLiskGrid.getRowColData(tSel-1,5)+"'";
		}
		turnPage1.queryModal(mSQL,AuditInfoGrid);
		
		//������Ϣ
		RiskInfoGrid.clearData();
		
		if(fm.DeTailFlag.value=='1'){ //����
			mSQL = "select x.A1, x.A2, x.A3, x.A4, x.A5, x.A6,x.A7,decode( x.A8 , '01' , '"+"֪ͨ�޶�"+"' , '02' , '"+"�����޶�"+"' , '03' , '"+"����������"+"' ), x.A8 from (select a.insuredno A1, a.Insuredname A2,a.RiskCode A3,'000000' A4,(select b.Amnt from RIPolIndView b where b.PolNo=a.PolNo) A5, a.CaseNo A6,a.ClmGetMoney A7,(select c.State from RIClaimState c where c.CaseNo = a.CaseNo and c.riskcode = a.RiskCode and c.dutycode = '000000') A8 from RIClaimRiskDetailView a where a.caseno = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"')x";
		}else{ //����
			mSQL = "select x.A1, x.A2, x.A3, x.A4, x.A5, x.A6,x.A7,(case when x.A8 = '01' then '"+"֪ͨ�޶�"+"' when x.A8 = '02' then '"+"�����޶�"+"' when x.A8 = '03' then '"+"����������"+"' end), x.A8 from (select a.insuredno A1, a.Insuredname A2,'000000' A3,a.dutycode A4,(select b.Amnt from RIPolIndView b where b.PolNo=a.PolNo) A5, a.CaseNo A6,a.ClmGetMoney A7,(select trim(c.State) from RIClaimState c where c.CaseNo = a.CaseNo and c.riskcode = a.RiskCode and trim(c.dutycode) = '000000') A8 from RIClaimDutyDetailView a where a.caseno = '"+TaskLiskGrid.getRowColData(tSel-1,5)+"')x";
		}		
		turnPage2.queryModal(mSQL,RiskInfoGrid); 

		//��ʾ�����µ����ͻظ���¼���ٱ��������
		QueryClaimAudit();
	}catch(ex){
	    myAlert(ex);
    }
}

//====================================================================================
/**
*��ѯ�µ���������ظ���Ϣ
*/
function  QueryReInsureAudit(){
	
	ReInsureAuditGrid.clearData();
	var mSql="";
	var tSel = TaskLiskGrid.getSelNo();	
 	var tOpeData = TaskLiskGrid.getRowColData(tSel-1,2);
	mSql = "select x.A1,x.A2,x.A3,x.A4,x.A5, x.A6,decode(x.A7, '00','"+"���ظ�"+"', '01', '"+"�ѻظ�"+"','02','"+"�Ѱ��"+"',null,'"+"���"+"','"+"δ֪����"+"'),x.A8 from (select a.proposalgrpcontno A1, a.uwno A2,a.UWOperator A3, a.ModifyDate A4, a.AdjunctPath A5,'"+"�˱�����"+"' A6,(select State from RIUWTask where ProposalContNo = a.ProposalContNo and a.AuditType = AuditType and a.UWNo = UWNo) A7, a.UWIdea A8 from RIUWIdea a where a.serialno = (select max(b.serialno) from RIUWTask b where b.AuditType = '01' and b.Proposalgrpcontno = '000000' and b.proposalcontno = '"+tOpeData+"')";
	mSql+= " union all ";
	mSql+= "select a.proposalgrpcontno A1, a.UWNo A2, a.ReInsurer A3, a.ModifyDate A4, a.AdjunctPath A5, '"+"�ٱ��ظ�"+"' A6, (select State  from RIUWTask where ProposalContNo = a.ProposalContNo and a.AuditType = AuditType and a.UWNo = UWNo) A7, a.UWIdea A8 from RIAnwserIdea a  where a.serialno = (select max(b.serialno) from RIUWTask b  where b.AuditType = '01' and b.Proposalgrpcontno = '000000' and b.proposalcontno = '"+tOpeData+"')) x order by x.A2 ";
	
	turnPage3.queryModal(mSql,ReInsureAuditGrid);
}

/**
*��ѯ������������ظ���Ϣ
*/
function QueryClaimAudit(){
	var tSel = TaskLiskGrid.getSelNo();	
 	var proposalcontno = TaskLiskGrid.getRowColData(tSel-1,2);
 	var clmNo = TaskLiskGrid.getRowColData(tSel-1,5);
	ReInsureAuditGrid.clearData();
	var mSql = "select x.A1,x.A2,x.A3,x.A4,x.A5, x.A6,decode(x.A7, '00','"+"���ظ�"+"', '01', '"+"�ѻظ�"+"','02','"+"�Ѱ��"+"',null,'"+"���"+"','"+"δ֪����"+"'),x.A8 from ";
	    mSql +=" (select a.proposalgrpcontno A1,a.UWNo A2,a.UWOperator A3,a.ModifyDate A4, a.AdjunctPath A5,'"+"�˱�����"+"' A6,(select State  from RIUWTask where SerialNo = a.serialno) A7, a.UWIdea A8 from RIUWIdea a where a.serialno = (select max(b.serialno) from RIUWTask b  where b.AuditType = '04' and b.Proposalgrpcontno = '000000' and trim(b.AuditCode) = '"+clmNo+"')" ;
	    mSql +=" union all " ;
	    mSql +=" select a.proposalgrpcontno A1, a.UWNo A2, a.ReInsurer A3,a.ModifyDate A4, a.AdjunctPath A5,'"+"�ٱ��ظ�"+"' A6,(select State  from RIUWTask where SerialNo = a.serialno) A7, a.UWIdea A8 from RIAnwserIdea a where a.serialno = (select max(b.serialno) from RIUWTask b  where b.AuditType = '04' and b.Proposalgrpcontno = '000000' and trim(b.AuditCode) = '"+clmNo+"'))x order by x.A2 desc";
	turnPage3.queryModal(mSql,ReInsureAuditGrid);
}


//=============================================================================
/**
*��ʾ������ظ���Ϣ
*/
function showAnswerIdea(){
	var checkFlag=ReInsureAuditGrid.getSelNo();
	fm.SendAnswerRemark.value= ReInsureAuditGrid.getRowColData(checkFlag - 1,8);
}

//����
function DownLoad(){
	tSel = ReInsureAuditGrid.getSelNo();
	if(tSel=='0'||tSel==null){
		myAlert(""+"����ѡ���ٱ����������Ϣ��"+"");
		return false;
	}
	 
	var FilePath = ReInsureAuditGrid.getRowColData(tSel - 1, 5);  
	if (FilePath==""||FilePath==null){
	 myAlert(""+"û�и���"+","+"���ܽ������ز�����"+"");
	 return false;
	}     
   //var showStr="�����������ݡ���";
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

