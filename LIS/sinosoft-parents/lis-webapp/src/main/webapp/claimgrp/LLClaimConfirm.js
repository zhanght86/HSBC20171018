var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
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
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql1");
	mySql.addSubPara(rptNo);             
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
    //-----------------------0-------1----------2-----------3-----------4------------5--------6-------7-------8--------------9-------------------------------------------------------------10------------11---------------12----------------------------------------------------------------------------13------------14-----------15-----------16------------17----------18-------19-------------20
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,(case assigneetype when '0' then '业务员' when '1' then '客户' end),assigneecode,assigneename,(case assigneesex when '0' then '男' when '1' then '女' when '2' then '未知' end),assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),rgtState,accepteddate from llregister where "
                + "rgtno = '"+rptNo+"'";*/
//    alert("strSQL2= "+strSQL2);
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql2");
	mySql.addSubPara(rptNo);   
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //更新页面内容
//    fm.AccNo.value = AccNo[0][0];
//    fm.AccidentDate.value = AccNo[0][1];

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
    fm.RgtState.value = RptContent[0][21];
    fm.AcceptedDate.value = RptContent[0][22];
    showOneCodeName('llrgttype','rgtType','rgtTypeName');
    showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
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
    //fm.MedicalFeeInp.disabled=true;

    //设置按钮++++++++++++++++++++++++++++++++++++++++待添加

    //检索分案关联出险人信息(多条)
    var strSQL3 = "select CustomerNo,Name," 
	    +" (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
        + " Birthday,"
        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
        + " from LDPerson where "
        + "CustomerNo in("
        + "select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";
//prompt("检索分案关联出险人信息(多条)",strSQL3);
   // easyQueryVer3Modal(strSQL3, SubReportGrid);
  /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql3");
	mySql.addSubPara(rptNo);   */
   turnPage1.queryModal(strSQL3,SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    //查询审核结论
    queryAudit();
}

//审核结论查询
function queryAudit()
{
   /* var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql4");
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
        showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        showOneCodeName('llexamconclusion2','AuditConclusion','AuditConclusionName');
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

//选择审批结论
function choiseConfirmConclusionType()
{
    if (fm.DecisionSP.value == '1')
  {
        divLLConfirm2.style.display= "";
    }
    else
    {
        divLLConfirm2.style.display= "none";
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
//  fm.IsDead.value = "";
//  fm.IsDeadName.value = "";
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
        
        fm.SocialInsuFlag.value = SubReportGrid.getRowColData(i,6);
        
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql5");
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
//                alert(tClaim);
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
//    alert(strSQL2);
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql6");
	mySql.addSubPara(fm.RptNo.value);   
	mySql.addSubPara(fm.customerNo.value);  
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
    fm.AccidentDate.value =tSubReport[0][1];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult2();
//根据选中的出险人显示相关的理算结果
afterMatchDutyPayQuery();
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
        showOneCodeName('llpayconclusion2','GiveType','GiveTypeName');
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
    tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;
   /* mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql7");
	mySql.addSubPara(tClaimNo); */   
    arr = easyExecSql( tSql);
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }
    
    
    //查询LLClaimDutyKind，按照赔案理赔类型进行查找
  /*  tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and c.code=a.GetDutyKind),"
       +" a.TabFeeMoney,a.SelfAmnt,a.StandPay,a.SocPay,a.OthPay,a.RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/

	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql8");
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
    /*tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"' order by a.insuredno "*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql9");
	mySql.addSubPara(tClaimNo); 
    arr = easyExecSql( mySql.getString()  );
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }
    else
    {
        initPolDutyKindGrid();
    }
    

    //查询LLClaimDetail,查询保单理赔类型保项层面的信息
      initPolDutyCodeGrid();
    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where c.GetDutyKind = a.GetDutyKind and c.GetDutyCode = a.GetDutyCode),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0)," //交至日期 + 宽限期--+ a.GracePeriod
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion2' and e.code=a.GiveType),"
       +" a.GiveReason,"
       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and f.code=a.GiveReason),"
       +" a.GiveReasonDesc,a.SpecialRemark,"
       +" a.PrepaySum ,"//预付金额
       +" '',"
       +" a.RealPay,a.AdjReason,"
       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and g.code=a.AdjReason),"
       +" a.AdjRemark, "
       +" a.PrepayFlag,case a.PrepayFlag when '0' then '无预付' when '1' then '有预付' end,"
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"
       //+" and a.GiveType not in ('1')"
       +" and a.CustomerNo = '"+tCustNo+"'"
       ;
	/*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql10");
	mySql.addSubPara(tClaimNo); 
	mySql.addSubPara(tCustNo); */
    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
    else
    {
        initPolDutyCodeGrid();
    }    
}

function showScanInfo()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //赔案号  
    var tCustNo = fm.customerNo.value;     //客户号
    var tCustomerName = fm.customerName.value;     //客户姓名
    
  if (tCustNo == "")
  {
      alert("请选择出险人！");
      return;
  }
    var strUrl="SubScanClaimQuery.jsp?ClaimNo="+tClaimNo+"&CustomerNo="+tCustNo+"&CustomerName="+tCustomerName;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

function AddAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
}

function LoadAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseShowAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
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


//帐户处理(由yuejw于2005年7月11日添加)
function showLCInsureAccont()
{
  var rptNo = fm.RptNo.value;//赔案号
    var strUrl="LCInsureAccMain.jsp?RptNo="+rptNo+"";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"帐户处理");
}

//受益人分配
function showBnf()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    
    var rptNo = fm.RptNo.value;//赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号
    
    if (tCustNo == "")
    {
    	alert("请选择出险人！");
    	return;
    }
    
    var strUrl="../claimgrp/LLColQueryBnfInput.jsp?claimNo="+rptNo+"&BnfKind=A&InsuredNo="+document.all('customerNo').value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

   // var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+document.all('customerNo').value;
   // window.open(strUrl,"出险人分配",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
 
//录入医疗单证信息
function showLLMedicalFeeInp()
{
    
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号
    
    if (tCustNo == "")
    {
    	alert("请选择出险人！");
    	return;
    }
    
    var strUrl="../claim/LLColQueryMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.AccidentDate2.value+"&otherAccDate="+fm.AccidentDate2.value+"&GrpFlag=1";    
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

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
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"";
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
   /* var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql11");
	mySql.addSubPara(fm.RptNo.value); 
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
   /* var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql12");
	mySql.addSubPara(fm.RptNo.value); 
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
    fm.action = './LLClaimConfirmSave.jsp';
  //增加分公司上报流程的处理，上报并没进行工作流流转，只是重新确定审批人，由总公司进行审批
    if(fm.DecisionSP.value == '2')
    {
       //alert("程序流转到上报流程");
       fm.fmtransact.value = "UPREPORT";
       fm.action = './LLClaimConfirmReportBackSave.jsp';
    }
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//返回按钮
function goToBack()
{
    location.href="LLClaimConfirmMissInput.jsp";
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
    fm.ConfirmSave.disabled=false;
    tSaveFlag ="0";
}

//审批结论确认
function ConfirmSaveClick()
{
  //判断表单输入是否为空
  if (fm.DecisionSP.value == "" || fm.DecisionSP.value == null)
  {
    alert("请先填写审批结论!");
        return;
  }
  if (fm.RemarkSP.value == "" || fm.RemarkSP.value == null)
  {
	  alert("请填写审批意见");
	  return;
  }
  
	//---------------jinsh--20070524--如果该案件处在调查过程中不准结案---------------//
	if(fm.DecisionSP.value == "0")
	{
			//var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','00000095175') and missionprop1='"+fm.RptNo.value+"'";
		  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql13");
	mySql.addSubPara(fm.RptNo.value); 
		  var JustStateCount=easyExecSql(mySql.getString());
		  if(parseInt(JustStateCount)>0)
		  {
		        alert("该案件处在调查过程中请稍后再作结案!");
		        return;
		  }
    	//var tclmSql="select clmstate from llclaim where clmno='"+fm.RptNo.value+"' ";
    	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql14");
	mySql.addSubPara(fm.RptNo.value); 
    	var ttclmstate=easyExecSql(mySql.getString());
    	if (ttclmstate=="60"||ttclmstate=="70"||ttclmstate=="80")
    	{
    		alert("该赔案已经结案！");
    	  return;
    	}		  
    	

  }
	
	if(fm.DecisionSP.value == "2")
	{
    	//var tPopedomSql="select count(1) from LLGrpClaimUser where JobCategory='TA' and UserCode='"+document.all('UserCode').value+"'";
    	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql15");
	mySql.addSubPara(document.all('UserCode').value); 
    	var PopedomCount=easyExecSql(mySql.getString());
    	if(parseInt(PopedomCount)>0)
		{
		        alert("当前用户已具有最高权限,不能再上报!");
		        return;
		 }
	}
	//---------------jinsh--20070524--如果该案件处在调查过程中不准结案---------------//
  //负数案件校验
  var tSubReport = new Array;
  //var strSQL2 = "select realpay,mngcom from llclaim where 1=1"+ getWherePart( 'ClmNo','RptNo' ) ;
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql16");
	mySql.addSubPara(fm.RptNo.value); 
  var tSubReport = easyExecSql(mySql.getString());
  var tMoney = parseInt(tSubReport[0][0]);
  fm.ManageComAll.value = tSubReport[0][1];

    //通过时判断是否为负数案件
    if (fm.DecisionSP.value == "0")
    {
        if (fm.AuditConclusion.value == '0' && tMoney < 0)
        {
          alert("此赔案赔付金额为负数,不允许通过!");
          fm.isReportExist.value = "true";
          return;
        }
    }
    
    //审批不通过时必须录入不通过原因和依据
    if (fm.DecisionSP.value == "1")
    {
        if (fm.ExamNoPassReason.value == null || fm.ExamNoPassReason.value == '')
        {
      	  	alert("请填写不通过原因");
      	  	return;
        }
        
        if (fm.ExamNoPassDesc.value == null ||  fm.ExamNoPassDesc.value == '')
        {
      	  	alert("请填写不通过依据");
      	  	return;
        }
    }

    fm.ConfirmSave.disabled = true;
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



   //理赔人员权限判断
/*
    if (fm.DecisionSP.value == "0")
    {
        var strSql6 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('UserCode').value +"' and b.claimtype = '30'"
        var basemax = easyExecSql(strSql6);
        if (basemax == '' || basemax == null)
        {
            alert("未查询到您的理赔权限！");
            return;
        }
        var strSql7 = "select realpay from llclaim where 1=1 "
                    + getWherePart( 'ClmNo','RptNo' );
        var realpay = easyExecSql(strSql7);
        if(realpay == '' || realpay == null)
        {
            alert("未查询到该赔案的赔付金额！");
            return;
        }
        basemax = basemax*1;
        realpay = realpay*1;
        if(realpay > basemax){
            alert("您的权限不足！");
            return;
        }
    }
*/
    //提交
    fm.isReportExist.value = "false";
    fm.fmtransact.value = "UPDATE";
    showInfo.close();
    submitForm();
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
	mySql.setResourceName("claimgrp.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql17");
	mySql.addSubPara(fm.AccResult2.value); 
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
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
