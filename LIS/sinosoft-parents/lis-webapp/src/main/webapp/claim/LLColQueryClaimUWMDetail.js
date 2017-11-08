var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//查询案件核赔履历表
function queryLLClaimUWMDetailGrid()
{
	/*var strSQl = "select clmno,clmuwno,examper,mngcom,makedate from llclaimuwmdetail where 1=1"
			   + " and clmno='" + fm.ClmNo.value + "' order by (clmuwno/1) desc";*/
	mySql = new SqlClass();
mySql.setResourceName("claim.LLColQueryClaimUWMDetailInputSql");
mySql.setSqlId("LLColQueryClaimUWMDetailSql1");
mySql.addSubPara(fm.ClmNo.value ); 		   
	//prompt("初始化查询案件核赔履历表",strSQl);
    turnPage.queryModal(mySql.getString(), LLClaimUWMDetailGrid);
}


//查询 审核 、审批详细信息
function LLClaimUWMDetailGridClick()
{
	document.all('DivLLClaimUWMDetailInfo').style.display="";

    var i = LLClaimUWMDetailGrid.getSelNo() - 1;
    var tClmNo=LLClaimUWMDetailGrid.getRowColData(i,1);//赔案号
    var tClmUWNo=LLClaimUWMDetailGrid.getRowColData(i,2);//核赔次数

    //审核详细信息
   /* var strAuditSQl = "select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc,(select b.username from lduser b where b.usercode = llclaimuwmdetail.AuditPer),AuditDate from llclaimuwmdetail where 1=1"
                    + " and clmno='" + tClmNo + "'"
                	+ " and clmuwno='" + tClmUWNo + "'"*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryClaimUWMDetailInputSql");
	mySql.setSqlId("LLColQueryClaimUWMDetailSql2");
	mySql.addSubPara(tClmNo); 	
	mySql.addSubPara(tClmUWNo); 	
    //prompt("审核结论查询",strAuditSQl);
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
        //显示隐藏层
        choiseConclusionType();
    }
    
    
    //审批详细信息
    /*var strExamSQl = "select ExamConclusion,ExamIdea,ExamNoPassReason,ExamNoPassDesc,(select b.username from lduser b where b.usercode = llclaimuwmdetail.ExamPer),ExamDate from llclaimuwmdetail where 1=1"
                   + " and clmno='" + tClmNo + "'"
                   + " and clmuwno='" + tClmUWNo + "'"*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryClaimUWMDetailInputSql");
	mySql.setSqlId("LLColQueryClaimUWMDetailSql3");
	mySql.addSubPara(tClmNo); 	
	mySql.addSubPara(tClmUWNo); 
    var tConfirm = easyExecSql(mySql.getString());
    //prompt("审批结论查询",strExamSQl);
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
        //显示隐藏层
        choiseQueryConclusionType();
    }
}

//选择审核结论
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

//选择审批结论
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

//清除界面上的数据
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