var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ѯ��������������
function queryLLClaimUWMDetailGrid()
{
	/*var strSQl = "select clmno,clmuwno,examper,mngcom,makedate from llclaimuwmdetail where 1=1"
			   + " and clmno='" + fm.ClmNo.value + "' order by (clmuwno/1) desc";*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryClaimUWMDetailInputSql");
	mySql.setSqlId("LLColQueryClaimUWMDetailSql1");
	mySql.addSubPara(fm.ClmNo.value ); 
     turnPage.queryModal(mySql.getString(), LLClaimUWMDetailGrid);
}


//��ѯ ��� ��������ϸ��Ϣ
function LLClaimUWMDetailGridClick()
{
	document.all('DivLLClaimUWMDetailInfo').style.display="";

    var i = LLClaimUWMDetailGrid.getSelNo() - 1;
    var tClmNo=LLClaimUWMDetailGrid.getRowColData(i,1);//�ⰸ��
    var tClmUWNo=LLClaimUWMDetailGrid.getRowColData(i,2);//�������

    //�����ϸ��Ϣ
   /* var strAuditSQl = "select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc,(select b.username from lduser b where b.usercode = llclaimuwmdetail.AuditPer),AuditDate from llclaimuwmdetail where 1=1"
                    + " and clmno='" + tClmNo + "'"
                	+ " and clmuwno='" + tClmUWNo + "' "*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryClaimUWMDetailInputSql");
	mySql.setSqlId("LLColQueryClaimUWMDetailSql2");
	mySql.addSubPara(tClmNo ); 
	mySql.addSubPara(tClmUWNo ); 
    var tAudit = easyExecSql(mySql.getString());

    if (tAudit != null)
    {
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        fm.ProtestReason.value = tAudit[0][3];
        fm.ProtestReasonDesc.value = tAudit[0][4];
        fm.AuditPer.value = tAudit[0][5];
        fm.AuditDate.value = tAudit[0][6];
        showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        if (fm.AuditConclusion.value != '')
        {
            spanAudit.style.display = '';
        }
        //��ʾ���ز�
        choiseConclusionType();
    }
    
    
    //������ϸ��Ϣ
   /* var strExamSQl = "select ExamConclusion,ExamIdea,ExamNoPassReason,ExamNoPassDesc,(select b.username from lduser b where b.usercode = llclaimuwmdetail.ExamPer),ExamDate from llclaimuwmdetail where 1=1"
                   + " and clmno='" + tClmNo + "'"
                   + " and clmuwno='" + tClmUWNo + "' "*/
  	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryClaimUWMDetailInputSql");
	mySql.setSqlId("LLColQueryClaimUWMDetailSql3");
	mySql.addSubPara(tClmNo ); 
	mySql.addSubPara(tClmUWNo ); 
    var tConfirm = easyExecSql(mySql.getString());

    if (tConfirm != null)
    {
        fm.DecisionSP.value = tConfirm[0][0];
        fm.RemarkSP.value = tConfirm[0][1];
        fm.ExamNoPassReason.value = tConfirm[0][2];
        fm.ExamNoPassDesc.value = tConfirm[0][3];
        fm.ExamPer.value = tConfirm[0][4];
        fm.ExamDate.value = tConfirm[0][5];
        showOneCodeName('llexamconclusion','DecisionSP','DecisionSPName');
        showOneCodeName('llexamnopassreason','ExamNoPassReason','ExamNoPassReasonName');
        if (fm.DecisionSP.value != '')
        {
            spanConfirm.style.display = '';
        }
        //��ʾ���ز�
        choiseQueryConclusionType();
    }
}

//ѡ����˽���
function choiseConclusionType()
{
    if (fm.AuditConclusion.value == '1')
	{
        divLLAudit2.style.display= "";
    }
    else
    {
        divLLAudit2.style.display= "none";
    }
}

//ѡ����������
function choiseQueryConclusionType()
{
    if (fm.DecisionSP.value == '1')
	{
        divLLQuery2.style.display= "";
    }
    else
    {
        divLLQuery2.style.display= "none";
    }
}

//��������ϵ�����
function clearPageData()
{
	fm.AuditConclusion.value="";
	fm.AuditNoPassReason.value="";
	fm.AuditNoPassDesc.value="";
	fm.AuditIdea.value="";
	fm.AuditLevel.value="";
	fm.AuditPer.value="";
	fm.AuditDate.value="";

	fm.ExamNoPassReason.value="";
	fm.ExamNoPassDesc.value="";
	fm.ExamConclusion.value="";
	fm.ExamIdea.value="";
//	fm.ExamLevel.value="";
	fm.ExamPer.value="";
	fm.ExamDate.value="";
	fm.SpecialRemark.value="";
}