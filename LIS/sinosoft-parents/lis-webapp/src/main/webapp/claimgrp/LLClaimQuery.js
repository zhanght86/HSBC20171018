var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//发起二核
function showSecondUWInput()
{
	  var strUrl="SecondUWInput.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;
    //window.open(strUrl,"发起二核");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//由客户查询（LLLdPersonQuery.js）返回单条记录时调用
function afterQueryLL(arr)
{
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    //fm.IsVip.value = arr[4];
    showOneCodeName('sex','customerSex','SexName');//性别
    //初始化录入域
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.AccidentDate2.value = "";
    fm.accidentDetail.value = "";
    fm.IsDead.value = "";
    fm.cureDesc.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  fm.claimType[j].checked = false;
    }
}

//立案查询
function queryRegister()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    //检索事件号、意外事故发生日期(一条)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql1");
	mySql.addSubPara(rptNo);             
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
    /*var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,(case assigneetype when '0' then '业务员' when '1' then '客户' end),assigneecode,assigneename,(case assigneesex when '0' then '男' when '1' then '女' when '2' then '未知' end),assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),clmstate from llregister where "
                + "rgtno = '"+rptNo+"'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql2");
	mySql.addSubPara(rptNo);                
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.AccidentSite.value = RptContent[0][5];
    fm.RptDate.value = RptContent[0][6];
    fm.Operator.value = RptContent[0][7];
    fm.MngCom.value = RptContent[0][8];
    fm.AssigneeType.value = RptContent[0][9];
    fm.AssigneeCode.value = RptContent[0][10];
    fm.AssigneeName.value = RptContent[0][11];
    fm.AssigneeSex.value = RptContent[0][12];
    fm.AssigneePhone.value = RptContent[0][13];
    fm.AssigneeAddr.value = RptContent[0][14];
    fm.AssigneeZip.value = RptContent[0][15];
    fm.occurReason.value = RptContent[0][16];
    fm.RgtConclusion.value = RptContent[0][17];
    fm.rgtType.value = RptContent[0][18];
    fm.RgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
    fm.ClmState.value = RptContent[0][21];
    showOneCodeName('llrgttype','rgtType','rgtTypeName');
    showOneCodeName('llclaimstate','ClmState','ClmStateName');
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论

    //****************************************************
    //更新页面权限
    //****************************************************
    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;
    fm.Remark.disabled=true;

    //设置按钮++++++++++++++++++++++++++++++++++++++++待添加

    //检索分案关联出险人信息(多条)
   /* var strSQL3 = "select CustomerNo,Name,Sex,Birthday,"
    	        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
    	        +" from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql3");
	mySql.addSubPara(rptNo);                
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    if (fm.RgtConclusion.value == '01')
    {
        divPart2.style.display = '';
        //查询审核结论
        queryAudit();
        //查询审批结论
        queryConfirm();
        //查询结案信息
        queryEndCase();
        //查询财务实付
        queryConfDate();
    }
}

//审核结论查询
function queryAudit()
{
   /* var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc,(select b.username from llclaimuser b where b.usercode = LLClaimUWMain.AuditPer),AuditDate from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql4");
	mySql.addSubPara(fm.ClmNo.value);              
//    alert("strSql= "+strSql);
    var tAudit = easyExecSql(mySql.getString());
//    alert(tAudit);
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

//审批结论查询
function queryConfirm()
{
    /*var strSql = " select ExamConclusion,ExamIdea,ExamNoPassReason,ExamNoPassDesc,(select b.username from llclaimuser b where b.usercode = LLClaimUWMain.ExamPer),ExamDate from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "'";*/
//    alert("strSql= "+strSql);
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql5");
	mySql.addSubPara(fm.ClmNo.value);  
    var tConfirm = easyExecSql(mySql.getString());
//    alert(tConfirm);
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

//结案信息查询
function queryEndCase()
{
    /*var strSql = " select a.realpay,(select b.username from llclaimuser b where b.usercode = a.operator),a.endcasedate from llclaim a where "
               + " ClmNo = '" + fm.ClmNo.value + "'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql6");
	mySql.addSubPara(fm.ClmNo.value);              
//    alert("strSql= "+strSql);
    var tEndCase = easyExecSql(mySql.getString());
    if (tEndCase != null)
    {
        fm.ClmEndRealPay.value = "￥" + tEndCase[0][0];
        fm.ClmEndPer.value = tEndCase[0][1];
        fm.ClmEndDate.value = tEndCase[0][2];

        if (fm.ClmEndDate.value != '')
        {
            spanEndCase.style.display = '';
        }
    }
}

//财务实付查询
function queryConfDate()
{
    /*var strSql = " select count(1) from ljaget a where a.enteraccdate is null "
               + " and a.otherno = '" + fm.ClmNo.value + "' and operstate='0' ";*/
//    alert("strSql= "+strSql);
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql7");
	mySql.addSubPara(fm.ClmNo.value);  
    var tConfDate = easyExecSql(mySql.getString());
    if (tConfDate == "0")
    {
        fm.confGetMoney.value = "是";
    }
    else
    {
        fm.confGetMoney.value = "否";
    }
}

//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase)
{
	//********************************************Beg
	//置空相关表单
	//********************************************
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	fm.SocialInsuFlag.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.AccidentDate2.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
	//理赔类型置空
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //********************************************End

    //取得数据
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,4));
        fm.SocialInsuFlag.value = SubReportGrid.getRowColData(i,5);
        fm.SocialInsuFlagName.value = SubReportGrid.getRowColData(i,6);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql8");
	mySql.addSubPara(fm.RptNo.value);   
	mySql.addSubPara(fm.customerNo.value);              
//    alert(tClaimType);
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    	  alert("理赔类型为空，请检查此记录的有效性！");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
        	  for (var l=0;l<tClaimType.length;l++)
        	  {
        	  	  var tClaim = tClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
            	  }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2 from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql9");
	mySql.addSubPara(fm.RptNo.value);   
	mySql.addSubPara(fm.customerNo.value);              
//    alert(strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult2();
}

//选中PolDutyCodeGrid响应事件
function PolDutyCodeGridClick()
{
    //清空表单
    fm.GiveType.value = "";//赔付结论
    fm.RealPay.value = "";
    fm.AdjReason.value = "";//调整原因
    fm.AdjReasonName.value = "";//
    fm.AdjRemark.value = "";//调整备注
    fm.GiveReasonDesc.value = "";//拒付依据
    fm.GiveReason.value = "";//拒付原因代码
    fm.SpecialRemark.value = "";//特殊备注
    //得到mulline信息
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,13);//赔付结论
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,22);//调整原因
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,23);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,24);//调整备注
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,15);//拒付原因代码
        fm.GiveReasonDesc.value = PolDutyCodeGrid.getRowColData(i,17);//拒付依据
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,18);//特殊备注
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveReason','GiveReasonName');
    }
    //显示隐藏层
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
}

//选择赔付结论
function choiseGiveTypeType()
{
    if (fm.GiveType.value == '0')
	{
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
    }
    else if (fm.GiveType.value == '1')
    {
        divGiveTypeUnit1.style.display= "none";
        divGiveTypeUnit2.style.display= "";
    }
}

//匹配后的查询
function afterMatchDutyPayQuery()
{
    var tSql;
    var arr;

    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号


    //查询整个赔案的金额
  /*  tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql10");
	mySql.addSubPara(tClaimNo);          
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }


    //查询LLClaimDutyKind，按照赔案理赔类型进行查找
   /* tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyKind)),"
       +" a.TabFeeMoney,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,a.RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql11");
	mySql.addSubPara(tClaimNo);   

    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
    }
    else
    {
        initDutyKindGrid();
    }


    //查询LLClaimPolicy,查询保单理赔类型层面的信息
   /* tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql12");
	mySql.addSubPara(tClaimNo);  
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }
    else
    {
        initPolDutyKindGrid();
    }
    

    //查询LLClaimDetail,查询保单理赔类型保项层面的信息
  /*  tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where trim(c.GetDutyKind) = trim(a.GetDutyKind) and trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0)," //交至日期 + 宽限期--+ a.GracePeriod
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and trim(e.code)=trim(a.GiveType)),"
       +" a.GiveReason,"
       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and trim(f.code)=trim(a.GiveReason)),"
       +" a.GiveReasonDesc,a.SpecialRemark,"
       +" a.PrepaySum ,"//预付金额
       +" '',"
       +" a.RealPay,a.AdjReason,"
       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and trim(g.code)=trim(a.AdjReason)),"
       +" a.AdjRemark, "
       +" a.PrepayFlag,case a.PrepayFlag when '0' then '无预付' when '1' then '有预付' end,"
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"
       +" and a.GetDutyCode = b.GetDutyCode"
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GiveType not in ('1')"
       ;*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql13");
	mySql.addSubPara(tClaimNo);  
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
    else
    {
        initPolDutyCodeGrid();
    }
}

//二核处理
function SecondUWInput()
{
	  var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;
    //window.open(strUrl,"二核处理");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//[豁免处理]按钮<>
function showExempt()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var strUrl="LLColQueryExemptInput.jsp?ClaimNo="+tClaimNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//合同处理(由xutao于2005年7月14日添加)
function showContDeal()
{
    var strUrl="LLColQueryContDealMain.jsp?ClmNo="+fm.ClmNo.value;
    strUrl= strUrl + "&CustNo=" + fm.customerNo.value;
    strUrl= strUrl + "&ConType="+fm.RgtClass.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//保单结算
function showPolDeal()
{
    var strUrl="LLColQueryPolDealInput.jsp?ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//受益人分配
function showBnf()
{
    var tSel = SubReportGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
        alert( "请先选择一条记录，再执行此操作!!!" );
    }
    else{ 
    	var rptNo = fm.RptNo.value;//赔案号
        var strUrl="../claim/LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+fm.customerNo.value+"&Flag=1";
       window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }

}

//帐户处理(由yuejw于2005年7月11日添加)
function showLCInsureAccont()
{
	var rptNo = fm.RptNo.value;//赔案号
    var strUrl="LCInsureAccMain.jsp?RptNo="+rptNo+"";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"帐户处理");
}

//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//既往赔案查询
function showOldInsuredCase()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//计算医疗单证信息
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查看调查
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
	  //***************************************
	  //判断该赔案是否存在调查
	  //***************************************
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql14");
	mySql.addSubPara(fm.RptNo.value );  
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("该赔案还没有调查信息！");
    	  return;
    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"查看调查");
}

//打开事故描述信息
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=03";
//    window.open(strUrl,"事故描述信息",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"事故描述信息");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//打开呈报查询
function showQuerySubmit()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
	//***************************************
	//判断该赔案是否存在呈报
	//***************************************
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql15");
	mySql.addSubPara(fm.RptNo.value );  
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("该赔案还没有呈报信息！");
    	  return;
    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"呈报查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"呈报查询");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//数据提交
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    // showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT"
    fm.action = './LLClaimQuerySave.jsp';
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//返回按钮
function goToBack()
{
//    if (fm.Phase.value == '0')
//    {
//        location.href = "LLClaimQueryMissInput.jsp"; //综合查询调用
//    }
    if (fm.Phase.value == '0')
    {
        top.close();  //结案调用
    }
    else
    {
        location.href = "LLLClaimQueryMain.jsp?AppntNo=" + fm.Phase.value; //既往赔案查询调用
    }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        goToBack();
    }
    tSaveFlag ="0";
}

//出险人查询
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryInput.jsp");

}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//查询出险结果2
function queryResult2()
{
   /* var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql16");
	mySql.addSubPara(fm.AccResult2.value );  
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

//案件核赔履历表--查询 2005-08-16添加
function LLQueryUWMDetailClick()
{
//	alert("案件核赔履历查询");
    var strUrl="LLColQueryClaimUWMDetailMain.jsp?ClmNo="+fm.ClmNo.value;
//    window.open(strUrl,"案件核赔履历查询");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//影像查询---------------2005-08-14添加
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打印赔案号条形码
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}

//报案查询
function showReport()
{
    var strUrl="LLClaimQueryReportInput.jsp?claimNo="+document.all('ClmNo').value+"&phase=1";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//录入医疗单证信息
function showLLMedicalFeeInp()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;     //客户号
    
    var strUrl="LLColQueryMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate1="+fm.AccidentDate.value+"&accDate2="+fm.AccidentDate2.value;    
    window.open("LLColQueryMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate1="+fm.AccidentDate.value+"&accDate2="+fm.AccidentDate2.value,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
  
//计算医疗单证信息
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;     //客户号

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}