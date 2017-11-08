var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//单证费用调整
function showLLMedicalFeeAdj()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;     //客户号

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeAdjMain.jsp?RptNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//进行保险责任匹配
function showMatchDutyPay()
{
    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
 

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交
}

//匹配后的动作
function afterMatchDutyPay(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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


        mOperate = '';
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


    }
    afterMatchDutyPayQuery();
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
    /*tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
        +" '','','' "
        +" from LLClaim a  where 1=1 "
        +" and a.ClmNo = '"+tClaimNo+"'"
        ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql1");
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
    /*tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and c.code=a.GetDutyKind),"
       +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.SocPay,a.OthPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql2");
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
       +" a.StandPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql3");
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
    //+" to_char(b.PayEndDate)," //交至日期 + 宽限期--+ a.GracePeriod
      initPolDutyCodeGrid();
    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where c.GetDutyKind = a.GetDutyKind and c.GetDutyCode = a.GetDutyCode),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0),"
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
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
       +" a.dutycode,a.CustomerNo "
       +" , (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"   
       //+" and a.GiveType not in ('1')"
       +" and a.CustomerNo = '"+tCustNo+"'";
//    prompt("",tSql);
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql4");
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
    
     //查询LLClaimPolicy,查询保全项目信息      
     tSql = " select a.PolNo,(select RiskCode from LCPol where PolNo=a.PolNo),(select c.codename from ldcode c where c.codetype = 'edortypecode' and c.code=a.EdorType),"
     +" a.EdorValiDate,a.GetMoney "
     +" from LPEdorItem a where 1=1 and a.PolNo in(select PolNo from LLClaimPolicy where ClmNo = '"+tClaimNo+"') ";
 	/*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql5");
	mySql.addSubPara(tClaimNo);  */
    arr = easyExecSql( tSql  );
  
    if (arr)
    {		
        displayMultiline(arr,LPEdorItemGrid);
    }
    else
    {
        initLPEdorItemGrid();
    }
}

//打开发起呈报
function showBeginSubmit()
{

  var strUrl="LLSubmitApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

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

//录入医疗单证信息
function showLLMedicalFeeInp()
{
    var tSel = SubReportGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
        alert( "请先选择一条记录，再执行此操作!!!" );
    }
    else{
        var tClaimNo = fm.RptNo.value;         //赔案号
        var tCaseNo = fm.RptNo.value;          //分案号
        var tCustNo = fm.customerNo.value;          //客户号
    	 var strUrl="../claim/LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.AccidentDate.value+"&otherAccDate="+fm.AccidentDate.value;
    	    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
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

//发起二核
function showSecondUWInput()
{
    var strUrl="LLSecondUWMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value+"&InsuredName="+fm.customerName.value;    
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}

//二核处理
function SecondUWInput()
{
    var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;    
   // window.open(strUrl,"二核处理");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//[豁免处理]按钮<>
function showExempt()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var strUrl="LLClaimExemptMain.jsp?ClaimNo="+tClaimNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开发起调查
function showBeginInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    //var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql6");
	mySql.addSubPara(fm.RptNo.value); 
    var JustStateCount=easyExecSql(mySql.getString());
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("该案件已经发起调查，请不要重复调查!");
    		return;
    }       
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&custVip="+fm.SocialInsuFlag.value+"&initPhase=02";
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
    
    //判断该赔案是否存在调查
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql7");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        alert("该赔案还没有调查信息！");
        return;
    }
    var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"事故描述信息");
}

//打开呈报查询
function showQuerySubmit()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    
  //判断该赔案是否存在呈报
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql8");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
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

//保单挂起
function showLcContSuspend()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//立案查询
function showLLClaimRegisterQuery()
{
    window.open("LLClaimRegisterQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');    
//    window.open("LLClaimRegisterQueryInput.jsp","立案查询");    
}

//问题件回销
function showRgtMAffixList()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("先选择出险人！");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"问题件回销");
}

//发出问题件
function showRgtAddMAffixList()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("先选择出险人！");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtAddMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=RgtAdd";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//由客户查询（LLLdPersonQuery.js）返回单条记录时调用
function afterQueryLL(arr)
{
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    fm.IsVip.value = arr[4];
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

//由查询（LLClaimRegisterQuery.js）返回时调用
function afterQueryLL2(arr)
{
//select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,
//       operator,accidentsite,accidentdate,accidentreason,RgtConclusion,ClmState, from llregister  
    //直接获取立案查询返回
    fm.RptNo.value = arr[0];
    fm.RptorName.value = arr[1];
    fm.RptorPhone.value = arr[2];
    fm.RptorAddress.value = arr[3];
    fm.Relation.value = arr[4];
    fm.Operator.value = arr[5];
    fm.AccidentSite.value = arr[6];
    fm.RptDate.value = arr[7];
    fm.occurReason.value = arr[8];
    fm.RgtConclusion.value = arr[9];
    fm.CaseState.value = arr[10];    
    fm.MngCom.value = arr[11];    //alert(425);
    showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
//    showOneCodeName('RptMode','RptMode','RptModeName');//报案方式(?????)    
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    
    //查询获得事件号
    var LLAccident = new Array;
    /*var strSQL1 = "select AccNo,AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+arr[0]+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql9");
	mySql.addSubPara(arr[0]); 
//    alert(LLAccident);
    LLAccident = easyExecSql(mySql.getString());
    fm.AccNo.value = LLAccident[0][0];
    fm.AccidentDate.value = LLAccident[0][1];
    
    //查询获得出险人信息
    initSubReportGrid();
   /* var strSQL2 = " select CustomerNo,Name,"
        + " Sex,"
        + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
        + " Birthday,"
        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
        + " from LDPerson where "
        + " CustomerNo in("
        + " select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
//    alert(strSQL2);     
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql10");
	mySql.addSubPara(rptNo);             
    easyQueryVer3Modal(mySql.getString(),SubReportGrid);    
    
    //设置可操作域
    fm.AuditIdea.disabled = false;
    fm.AuditConclusion.disabled = false;
    fm.SpecialRemark.disabled = false;
    //fm.ConclusionSave.disabled = false;
    //fm.ConclusionUp.disabled = false;
    fm.ConclusionBack.disabled = false;
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
        fm.SexName.value = SubReportGrid.getRowColData(i,3);
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
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql11");
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
    //--------------------------1----------2---------3-----------4---------5------6-----7-------------8----------9----------10---------11
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,(case EditFlag when '0' then '否' when '1' then '是' end),(case AffixConclusion when '0' then '否' when '1' then '是' end) from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql12");
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
    fm.IsModify.value = tSubReport[0][9];
    fm.IsAllReady.value = tSubReport[0][10];
    fm.IsModifyName.value = tSubReport[0][11];
    fm.IsAllReadyName.value = tSubReport[0][12];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult2();
//    showOneCodeName('IsAllReady','IsModify','IsModifyName');//重要信息修改标志
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//单证齐全标志

//根据选中的出险人显示相关的理算结果
afterMatchDutyPayQuery();
}

//选中PolDutyCodeGrid响应事件
function PolDutyCodeGridClick()
{
    //alert("ttttt");
    //清空表单    
    fm.GiveType.value = "";//赔付结论
    fm.RealPay.value = "";
    fm.AdjReason.value = "";//调整原因
    fm.AdjReasonName.value = "";//
    fm.AdjRemark.value = "";//调整备注
    fm.GiveReasonDesc.value = "";//拒付依据
    fm.GiveReason.value = "";//拒付原因代码
    fm.SpecialRemark.value = "";//特殊备注
    //设置按钮
    fm.addUpdate.disabled = false;//添加修改
    //得到mulline信息
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.PolNo.value = PolDutyCodeGrid.getRowColData(i,1);//保单号
        //金述海,20070418注..
        //var psql="select polstate from lcpol where polno='"+fm.PolNo.value+"' ";
        //var tpolstate=new Array;
        //tpolstate=easyExecSql(psql); 
        //fm.polstate.value = tpolstate;
        //showOneCodeName('polstate','polstate','polstateName');        
        //jinsh20070410
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,13);//赔付结论
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);//调整金额
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,22);//调整原因
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,23);//调整原因
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

//对保项增加修改
function AddUpdate()
{
	if(fm.AdjReason.value == "00"||fm.AdjReason.value == "14"){
    	if(fm.AdjRemark.value == null || fm.AdjRemark.value =="null" || fm.AdjRemark.value == ""){
    		alert("调整原因为通融给付或协议给付时，必须录入调整备注!");
    		return false;
    	}
    }
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("请先选择要处理的保项信息,再执行此操作！");
        return;
    }
    
    if(fm.GiveType.value==null || fm.GiveType.value=='')
    {
    	alert ("请您选择保项赔付结论!");
    	return false;
    }
    //金述海20070419修改,原因:调整原因改为必录项.....
//    if(fm.AdjReason.value=="")
//    {
//    	alert ("请您选择调整原因!");
//    }
    
    if(fm.GiveType.value=='0')
    {
        //金述海20070419修改,原因:调整原因改为必录项.....
        if(fm.AdjReason.value==null || fm.AdjReason.value=="")
        {
        	alert ("请您选择调整原因!");
        	return false;
        }
    }
    
    if(fm.GiveType.value=='1')
    {
        if(fm.GiveReason.value ==null || fm.GiveReason.value =="")
        {
        	alert ("请您选择拒付原因!");
        	return false;
        }
        
        if(fm.GiveReasonDesc.value ==null || fm.GiveReasonDesc.value =="")
        {
        	alert ("请您录入拒付依据!");
        	return false;
        }
    }
       
   
    if(checkAdjMoney()==false)
    {
    	return false;
    }

    
    //让同一赔案号下的赔付结论都是一样的....金述海20070418修改
    for(var j =0;j<PolDutyCodeGrid.mulLineCount;j++)
    {
    	PolDutyCodeGrid.setRowColData(j,13,fm.GiveType.value);//赔付结论
    }
    
    
    
    //修改完成.......
    var i = PolDutyCodeGrid.getSelNo() - 1;//得到焦点行

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//赔付结论
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveReason.value);//拒付原因代码
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReasonDesc.value);//拒付依据
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//特殊备注
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//调整原因
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//调整备注
    
    if(fm.GiveType.value == 1)
    {
    	PolDutyCodeGrid.setRowColData(i,14,"拒付");//赔付结论名称
    }
    
    if(fm.GiveType.value == 0)
    {
    	PolDutyCodeGrid.setRowColData(i,14,"给付");//赔付结论名称
    }

//    if(fm.GiveType.value == 0)
//    {
//    	//让同一赔案号下的赔付结论都是一样的....金述海20070418修改
//    	for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
//    	{
//    		PolDutyCodeGrid.setRowColData(k,14,"给付");//赔付结论名称
//    	}
//    }
    //金述海20070417修改原因:以前只有两种赔付结论 
    //------------------------------------jinsh20070515--------------------------------------//
		/*if(fm.GiveType.value == 2)
		{
			//让同一赔案号下的赔付结论都是一样的....金述海20070418修改
    	for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
    	{
    		PolDutyCodeGrid.setRowColData(k,14,"协议赔付");//赔付结论名称
    	}
		}
		if(fm.GiveType.value == 3)
		{
			//让同一赔案号下的赔付结论都是一样的....金述海20070418修改
			for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
			{
			    PolDutyCodeGrid.setRowColData(k,14,"通融赔付");//赔付结论名称
			}
		}*/
		//------------------------------------jinsh20070515--------------------------------------//
//    if(fm.GiveType.value == 1)
//    {
//    	//让同一赔案号下的赔付结论都是一样的....金述海20070418修改
//			for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
//			{
//			    PolDutyCodeGrid.setRowColData(k,14,"拒付");//赔付结论名称
//			}
//    }

    
    fm.saveUpdate.disabled = false;//保存修改
}

//对保项修改保存到后台
function SaveUpdate()
{
	
	 var tClaimNo = fm.RptNo.value;
	  
	//校验保单级赔付金与本此赔付金之和不能大于账户金额
	//tSQL = "select distinct polno from llclaimpolicy where clmno='"+tClaimNo+"' and riskcode in (select riskcode from lmrisk where insuaccflag='Y')";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql13");
	mySql.addSubPara(tClaimNo);      
	arr = easyExecSql(mySql.getString());

	//tSQL1 = "select count (distinct polno) from llclaimdetail where clmno='"+tClaimNo+"' and riskcode in (select riskcode from lmrisk where insuaccflag='Y')";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql14");
	mySql.addSubPara(tClaimNo);       
	arr1 = easyExecSql(mySql.getString());


 if(arr1[0][0] > 0)
 {
	for (var t =0;t<arr1[0];t++)
	 {

	var j=0;

	for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
	 {
    var tPolNo=PolDutyCodeGrid.getRowColData(k,1);

    var tPolNo1=arr[t][0];

	     if (tPolNo == tPolNo1)
	        {
	    	     	var tRealM  = parseFloat(PolDutyCodeGrid.getRowColData(k,21));//调整金额
	    	 			j=j+tRealM;
	        }
	       
	  }
	  


	// var strSQL2 = "select sum(money) from lcinsureacctrace where polno='"+arr[t]+"'";
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql15");
	mySql.addSubPara(arr[t]);  
	 arr2 = easyExecSql(mySql.getString());


	 //var strSQL3 = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b where a.ClmNo = b.ClmNo and a.ClmState in ('50') and a.ClmNo <>'"+tClaimNo+"' and b.GiveType != '1' and b.PolNo ='"+arr[t]+"'";
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql16");
	mySql.addSubPara(tClaimNo);  
	mySql.addSubPara(arr[t]);  
	 arr3 = easyExecSql(mySql.getString());

	 var p = pointTwo(j) + pointTwo(arr3[0][0]);
	 var u=arr2[0];

	 var intev = (pointTwo(p))-(pointTwo(u));

	if(intev>0)
	    {
		    alert("调整金额与既往给付金之和超过帐户金额，请调整!");
			  return false;
	    }

	 }
	
 }
 

    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
}

//公共提交方法
function submitForm()
{
    //提交数据
    //showInfo.close();
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
//    fm.action = './LLClaimAuditSave.jsp';
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";    
}

//提交后操作,返回
function afterSubmit1( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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

//提交后操作,不返回
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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

        afterMatchDutyPayQuery();
    }
    tSaveFlag ="0";
}

//提交后操作,重新查询
function afterSubmit3( FlagStr, content , AuditConclusion)
{
    showInfo.close();
    if (FlagStr == "Fail" )
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


        queryRegister();
    }
    if(AuditConclusion != '4' && AuditConclusion != '5'){
       //goToBack();
    }
    tSaveFlag ="0";
    fm.conclusionSave1.disabled = false;
}

//返回按钮
function goToBack()
{
    location.href="LLClaimAuditMissInput.jsp";
}

//审核结论保存
function ConclusionSaveClick()
{  
  if(!(confirm("确认保存理赔结论？")==true)){
    return false;
  }
  
  if(fm.AuditIdea.value==null||fm.AuditIdea.value==""){
	  alert("请填写审核意见");
	  return false;
  }
  
  //var tclmSql="select clmstate from llclaim where clmno='"+fm.RptNo.value+"' ";
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql17");
	mySql.addSubPara(fm.RptNo.value);   
  var ttclmstate=easyExecSql(mySql.getString());
  if (ttclmstate=="60"||ttclmstate=="70"||ttclmstate=="80")
  {
    alert("该赔案已经结案！");
     return;
  }   
   
     //查询是否进行过匹配计算 2009-08-04 9:08
 /* var Detailsql = "select count(1) from LLClaimDetail where"
           + " ClmNo = '" + fm.RptNo.value + "'";*/
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql18");
	mySql.addSubPara(fm.RptNo.value);   
  var tDetailDutyKind = easyExecSql(mySql.getString());
  if (tDetailDutyKind == 0)
  {
      alert("请先进行匹配并理算!");
      return;
  }
   
  //判断表单输入是否为空
  if (fm.AuditConclusion.value == "" || fm.AuditConclusion.value == null)
  { 
    alert("请先填写审核结论!");
    return;
  } 
  else {        
      if (fm.AuditConclusion.value == "1")
        {            
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql19");
			mySql.addSubPara(fm.RptNo.value);   
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] != "1")
                    {                      
                        alert("保项不全部为拒付,不能下全部拒付结论!");
                        return;
                    }
                }
            }
            
            if (fm.ProtestReason.value == "" || fm.ProtestReason.value == null)
            { 
              alert("请先选择拒付原因!");
              return;
            } 
            
            if (fm.ProtestReasonDesc.value == "" || fm.ProtestReasonDesc.value == null)
            { 
              alert("请先填写拒付依据!");
              return;
            } 
        }
  else if (fm.AuditConclusion.value == "0"||fm.AuditConclusion.value == "4")
        {//---------------jinsh--20070524--如果该案件处在调查过程中不准结案---------------//
        	//var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
        	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql20");
			mySql.addSubPara(fm.RptNo.value);   
        	var JustStateCount=easyExecSql(mySql.getString());
        	if(parseInt(JustStateCount)>0)
        	{      				
        		alert("该案件处在调查过程中请稍后再作结案!");
        		return;
        	}
        //---------------jinsh--20070524--如果该案件处在调查过程中不准结案---------------//
           
            var tGivetype = new Array;
            /*var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql21");
			mySql.addSubPara(fm.RptNo.value);  
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] == "0" || tGivetype[i] == "2" || tGivetype[i] == "3")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {
                    showInfo.close();                
                    alert("保项全部为拒付,不能下给付结论!");
                    return;
                }
            }
        }
    }
    

    //如果是给付或拒付结论,提交前检验
    //alert("fm.AuditConclusion.value:"+fm.AuditConclusion.value);
  	if (!(fm.AuditConclusion.value ==null||fm.AuditConclusion.value==""))
    {
  		if(fm.AuditConclusion.value == "0" || fm.AuditConclusion.value == "1" || fm.AuditConclusion.value == "4")
  		{
  	        //调查、呈报、问题件和二核校验,保单结算、合同处理
  	        if (!checkPre())
  	        {
  	            return;
  	        } 
  	        
  	        //如果只有豁免不用判断受益人
  	       /* var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
  	                 + getWherePart( 'ClmNo','ClmNo' );*/
  	        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql22");
			mySql.addSubPara(fm.ClmNo.value);  
  	        var tDutyKind = easyExecSql(mySql.getString());
  	        if (tDutyKind != '09')
  	        {
  	            //如果下给付结论,校验受益人分配
  	            if (fm.AuditConclusion.value == "0"|| fm.AuditConclusion.value == "4"|| fm.AuditConclusion.value == "5")
  	            {
  	               /* var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
  	                            + getWherePart( 'ClmNo','ClmNo' );*/
  	                mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
					mySql.setSqlId("LLClaimAuditSql23");
					mySql.addSubPara(fm.ClmNo.value);  
  	                var tBNF = easyExecSql(mySql.getString());                
  	                if (tBNF)
  	                {
  	                    for (var i = 0; i < tBNF.length; i++)
  	                    {
  	                        if (tBNF[i] == '0')
  	                        {
  	                            alert("受益人分配没有完成!");
  	                            return;
  	                        }
  	                    }
  	                }
  	            }
  	        }
  		}

    }

    
    //如果是给付结论则判断是否有解除合同操作
/*
    if (fm.AuditConclusion.value == "0")
    {
        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
                    + getWherePart( 'ClmNo','ClmNo' );
        var tCount = easyExecSql(strSql5);
        if (tCount > 0)
        {
            alert("已进行解除合同处理，不能下给付结论！");
            return;
        }
    }
*/

   //判断4位数机构不允许上报审批管理
//    if (fm.AuditConclusion.value == "4" && document.allManageCom.value.length == 4 )
//    {        
//        alert("请用2位机构上报审批管理！");
//        return false;
//    }
    
    /**
     * zhangzheng 2009-02-10 
     * MS团险理赔权限如何管理还没有确定,暂时封住这段校验;
     */
    
    //理赔人员权限判断
//    if (fm.AuditConclusion.value == "0")
//    {
//	    var tClaimType1 ;
//	    var tClaimType2 ;
//	    var tRealpay ;
//	    var tGiveType ;
//	    var tappndmax ;
//	    var tappndmax1 ;
//	    var tappndmax2 ;
//	
//	    var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
//	    tGiveType=easyExecSql(txsql);
//	    
//	    if (tGiveType.length>1)
//	    {
//	        showInfo.close();
//	        fm.conclusionSave1.disabled=false;        
//	       alert('赔付结论不统一！请回退到立案审核来修改！');	
//	       return;
//	    }
//    
//     if(tGiveType == '0' && tGiveType.length==1 )     
//     {
//	     //01.查询该赔案的最大理赔值     
//	     var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//	     var tcustomerno=new Array();
//	     tcustomerno=easyExecSql(csql);
//	     for (var i=0;i<tcustomerno.length;i++)
//	     {         
//	         var strSql00 = " select sum(realpay) from llclaimpolicy "
//	                      + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
//	         
//	         var tSubReport = new Array;
//	         tSubReport = easyExecSql(strSql00);    
//	         var   tRealpay1 = tSubReport[0][0];
//	         //alert ("寿险赔付:"+tRealpay1);
//	         //var tInsuredno1 = tSubReport[0][1];
//	         
//	          if(tRealpay1==null || tRealpay1 == "")
//	             {
//	               //  alert("未查询到该赔案的赔付金额！");
//	              //   return false;
//	              tRealpay1=0;       
//	             }
//	          else
//	           	{
//	           		//alert("tClaimType1");
//	           	   tClaimType1='10';
//	           	}
//	         
//	         var strSql01 = " select sum(realpay) from llclaimpolicy "
//	                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";                  
//	         
//	         var tSubReport1 = new Array;
//	         tSubReport1 = easyExecSql(strSql01);
//	         var   tRealpay2 = tSubReport1[0][0];
//             //alert ("健康险赔付:"+tRealpay2);
//             // var tInsuredno2 = tSubReport[0][1];
//             if(tRealpay2==null || tRealpay2 == "")
//                 {
//             //         alert("未查询到该赔案的赔付金额！");
//             //         return;
//                    tRealpay2=0;           
//             }
//             else
//             {
//               	   	tClaimType2='30';
//             }
//               
//         //02.查询该最大理赔值的客户的出险类型
//              /*var strSql02 = "select distinct reasoncode from LLAppClaimReason where caseno = '"+fm.ClmNo.value+"' and customerno = '"+fm.customerNo.value+"'";
//              var tSubReport2 = new Array;
//              tSubReport2 = easyExecSql(strSql02);
//                if(tSubReport2 == null ){
//                     alert("未查询到该赔案的出险类型！");
//                     return;
//                    }
//              for (var i= 0;i < tSubReport2.length ; i++ )
//              {
//                  var tReasonCode = tSubReport2[i][0].substring(1,3);
//                  if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
//                       tClaimType = 10;//寿险
//                       break;
//                      }else{
//                       tClaimType = 30;//健康险
//                      }
//              }*/
//         //03.1查询理赔人员的相关类型的理赔权限
//         //0301.1查询复核人员的理赔权限
//                 var strSql0301 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0301 = easyExecSql(strSql0301);
//                 if (tBasemax0301 == null || tBasemax0301 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax0301 = 0; //为查询到的默认为0
//                 }
//         //0302.1查询立案人员的理赔权限
//                 var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0302 = easyExecSql(strSql0302);
//                 if (tBasemax0302 == null || tBasemax0302 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax0302 = 0; //为查询到的默认为0
//                 }
//         //0303.1立案人员和审核人员权限判断
//                 tBasemax0301 = tBasemax0301*1;
//                 tBasemax0302 = tBasemax0302*1;
//                 if(tBasemax0301 > tBasemax0302){
//                   tBasemax1 = tBasemax0301;
//                 }else{
//                   tBasemax1 = tBasemax0302;
//                 }
//         
//         //03.2查询理赔人员的相关类型的理赔权限 
//         //0301.2查询复核人员的理赔权限        
//                 var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03011 = easyExecSql(strSql03011);
//                 if (tBasemax03011 == null || tBasemax03011 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax03011 = 0; //为查询到的默认为0
//                 }
//         //0302.2查询立案人员的理赔权限
//                 var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03022 = easyExecSql(strSql03022);
//                 if (tBasemax03022== null || tBasemax03022 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax03022 = 0; //为查询到的默认为0
//                 }
//         //0303.2立案人员和审核人员权限判断
//                 tBasemax03011 = tBasemax03011*1;
//                 tBasemax03022 = tBasemax03022*1;
//                 if(tBasemax03011 > tBasemax03022){
//                   tBasemax2 = tBasemax03011;
//                 }else{
//                   tBasemax2 = tBasemax03022;
//                 }
//                 
//         //04.权限判断
//                 tBasemax1 = tBasemax1*1;
//                 tBasemax2 = tBasemax2*1;
//         
//               		tRealpay1 = tRealpay1*1;
//               		tRealpay2 = tRealpay2*1;      	                    
//                        
//                 //alert ("寿险最高赔付:"+tBasemax1);
//                           
//                 //alert ("健康险最高赔付:"+tBasemax2);
//              if(tRealpay1 > tBasemax1 || tRealpay2 > tBasemax2)
//               {
//            	  //05.超权限上报和审批管理判断
//            	  //0501.查询该机构最高理赔权限,同时跟用户表相关联userstate 0 有效 1失效
//                 var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//                 var tChecklevel  = easyExecSql(strSql0501);
//                 //0502.查询该机构最高权限的赔付金额
//                 var strSql0502 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType1+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var strSql05022 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType2+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var tBasemax3  = easyExecSql(strSql0502);
//                 var tBasemax4  = easyExecSql(strSql05022);
//                 tBasemax3 = tBasemax3*1;
//                 tBasemax4 = tBasemax4*1;
//                 
//                 if(tRealpay1 > tBasemax3 || tRealpay2 > tBasemax4)
//                   {
//                	 //控件权限设定
//                    fm.ConclusionSave.disabled = false;
//                    fm.ConclusionUp.disabled = true;
//                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.conclusionSave1.disabled=false;                     
//                     alert("您的权限不足！请将该案件上报到审批管理！");
//                     return;
//                   }                  
//                   else{
//                	   //控件权限设定 
//                    fm.ConclusionSave.disabled = true;
//                    fm.ConclusionUp.disabled = false;
//                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.conclusionSave1.disabled=false;                         
//                     alert("您的权限不足！请将该案件超权限上报！");
//                     return;
//                   }
//                   
//               }
//     }  //for customerno
//   }else if ((tGiveType == '2'||tGiveType == '3') && tGiveType.length== 1 )
//    {            
//      var tsql1="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('tOperator').value+"' "; //复核人员
//      tappndmax1=easyExecSql(tsql1);
//      var tsql2="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('Operator').value+"' ";  //立案人员
//      tappndmax2=easyExecSql(tsql2);     
//      if (parseFloat(tappndmax2)>parseFloat(tappndmax1))
//        {      	   
//      	    tappndmax=tappndmax2;      	    
//      	}else
//      		{
//      			tappndmax=tappndmax1;  
//      		}          	
//       var csql2="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//       var xcustomerno=new Array();
//       xcustomerno=easyExecSql(csql2);
//       for (var k=0;k<xcustomerno.length;k++)
//       {         		
//         var maxsql="select sum(realpay) from llclaimpolicy where clmno = '"+fm.RptNo.value+"' and insuredno='"+xcustomerno[k]+"' ";
//         tRealpay=easyExecSql(maxsql);      
//         if (parseFloat(tappndmax)<parseFloat(tRealpay)) 
//         { 
//            var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//            var tChecklevel  = easyExecSql(strSql0501);
//            
//            var strSql0502 = "select distinct appendmax from LLClaimPopedom where Popedomlevel = '"+tChecklevel+"'";                 
//            var tBasemax3  = easyExecSql(strSql0502);                 
//            tBasemax3 = tBasemax3*1;                 
//            
//            if(tRealpay > tBasemax3)
//              {
//               fm.ConclusionSave.disabled = false;
//               fm.ConclusionUp.disabled = true;
//               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.conclusionSave1.disabled=false;                     
//                alert("您的通融、协议赔付权限不够！请将该案件上报到审批管理！");
//                return;
//              }                  
//              else{         
//               fm.ConclusionSave.disabled = true;
//               fm.ConclusionUp.disabled = false;
//               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.conclusionSave1.disabled=false;                         
//                alert("您的通融、协议赔付权限不足！请将该案件超权限上报！");
//                return;
//              }         	        	 
//          } 
//       }    	
//    }else if(tGiveType == '1' && tGiveType.length==1 )         
//    {
//      var tsql="select checklevel from llclaimuser where usercode='"+document.all('tOperator').value+"' ";	//复核人员
//      var tchecklevel=new Array;
//      tchecklevel=easyExecSql(tsql);
//      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
//      {
//          var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//          var tChecklevel  = easyExecSql(strSql0501);              
//          
//          if (tChecklevel=="A"||tChecklevel=="B1"||tChecklevel=="B2"||tChecklevel=="B3")
//            {
//             fm.ConclusionSave.disabled = false;
//             fm.ConclusionUp.disabled = true;
//             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.conclusionSave1.disabled=false;                     
//              alert("您的拒付权限不够！请将该案件上报到审批管理！");
//              return;
//            }                  
//            else{         
//             fm.ConclusionSave.disabled = true;
//             fm.ConclusionUp.disabled = false;
//             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.conclusionSave1.disabled=false;                         
//              alert("您的拒付权限不足！请将该案件超权限上报！");
//              return;
//            }          
//
//      }                
//    }
// } //fm.AuditConclusion.value == "0"

/*
        //如果有豁免保项,给出提示,要求进行豁免处理
    if (fm.AuditConclusion.value == "0")
    {
        var tSqlExe = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 +" and clmno = '" + fm.RptNo.value + "'"
                 +" and getdutykind in ('109','209') "
                 ;
        var tDutyExe = easyExecSql(tSqlExe);
        if (tDutyExe == '09')
        {
            alert("匹配出来的保项有豁免信息,请确认是否进行了豁免处理!!!");
            alert("匹配出来的保项有豁免信息,请确认是否进行了豁免处理!!!");   
        }
    }
*/
    
    //两年抗辩期的校验:如果为长期险且生效日超过出险日期2年（含2年），则给予提示信息
    if (!checkAccDate(fm.RptNo.value))
    {
        return;
    }    

    fm.conclusionSave1.disabled=true;
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

  
    //提交
    fm.action="./LLClaimAuditConclusionSave.jsp"
    fm.fmtransact.value = "INSERT";
    showInfo.close();
    submitForm();
    
}

//两年抗辩期的校验:如果为长期险且生效日超过出险日期2年（含2年），则给予提示信息
function checkAccDate(tRptNo)
{	
	var flag=false;//是否超过两年标示
	var tContno="";//超过两年的保单号
	
	var tAccDate;//团险的出险日期:LLAccident.AccDate
    /*var strQSql = "select AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+tRptNo+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql24");
	mySql.addSubPara(tRptNo);  
    var tDate = easyExecSql(mySql.getString());
    if (tDate == null)
    {
        alert("未查询到出险日期!");
        return true;
    }else{
    	tAccDate=tDate[0][0];
	}

    /*var strQSql = "select cvalidate,contno from lccont a where a.contno in "
    			+" (select distinct b.contno from LLClaimPolicy b where b.clmno = '"+tRptNo+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql25");
	mySql.addSubPara(tRptNo);  
    var tCvalidate = easyExecSql(mySql.getString());//团单中个人保单对应生效日期
    if (tCvalidate == null)
    {
        alert("未查询到此案件对应保单的生效日期!");
        return true;
    }
	for(var i=0;i<tCvalidate.length;i++){
		//alert("tCvalidate["+i+"]:"+tCvalidate[i][0]);
		//alert("tAccDate:"+tAccDate);
		if(dateDiff(tCvalidate[i][0]+"",tAccDate,'M') >= 24){	//保单生效日与出险日期相比，如果大于或等于两年(24月)
			tContno+="["+tCvalidate[i][1]+"]";
			flag=true;
		}
	}

	if(flag){
		if(!confirm("请注意：该案件涉及保单，保单号"+tContno+"，生效日至出险日期已达到或超过两年，适用不可抗辩。")){
			return false;
		}
	}
	
	return true;
}

//受益人分配前确认问题件\调查\呈报\二核是否完毕,保单结算\合同处理
function checkPre()
{
//    alert("开始");
    //问题件
    /*var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql26");
	mySql.addSubPara(fm.RptNo.value);  
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
//                alert("问题件回销没有完成!");
//                return false;
            }
        }
    }
    //调查
    /*var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql27");
	mySql.addSubPara(fm.RptNo.value);  
    var tFiniFlag = easyExecSql(mySql.getString());
    
    if (tFiniFlag)
    {
        for (var i = 0; i < tFiniFlag.length; i++)
        {
            if (tFiniFlag[i] != '1')
            {
                alert("发起的调查没有完成!");
                return false;
            }
        }
    }
    //呈报

    /*var strSql2 = "select SubState from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql28");
	mySql.addSubPara(fm.RptNo.value);  
    var tSubState = easyExecSql(mySql.getString());
    
    if (tSubState)
    {
        for (var i = 0; i < tSubState.length; i++)
        {
            if (tSubState[i] != '1')
            {
                alert("发起的呈报没有完成!");
                return false;
            }
        }
    }

    //二核=============================================================目前尚待完善
/*
    //保单结算\合同处理是否完成
    var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";
    var tFlag = easyExecSql(strSql3);
//    alert(tFlag);
    if (tFlag == null)
    {
        alert("未进行保单结算和合同处理操作!");
        return;
    }
    if (tFlag[0][0] != '1')
    {
        alert("未进行保单结算操作!");
        return;
    }
    if (tFlag[0][1] != '1')
    {
        alert("未进行合同处理操作!");
        return;
    }
*/
    return true;
}

//审核结论确认
function AuditConfirmClick()
{
    //判断审核结论
    var mngcom = new Array;
    var strSql = "select AuditConclusion from LLClaimUWMain where "
               + "clmno = '" + fm.RptNo.value + "'";//
//               +" and ExamConclusion is null";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql29");
	mySql.addSubPara(fm.RptNo.value);  */
    var tt=easyExecSql(strSql);
    if(!tt){
    	alert("本次审核尚未保存审核结论！");
        return false;
    }
    var tGivetype = tt[0][0];
    var pGivetype = tt[0][0];
    
    if (tGivetype == null)
    {
        alert("请先保存审核结论！");
        return;
    }
    else
    {    	     
		if (tGivetype == '5')
    	{    	         	     
    	    var tsql="select mngcom,operator from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
    	   /*   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql30");
	mySql.addSubPara(fm.RptNo.value);  */
    	     mngcom = easyExecSql(tsql);
    	     var tmanagecom=mngcom[0][0];   	      	  
    	     var toperator=mngcom[0][1];    	        	      	  
    	      if(tmanagecom==null||tmanagecom=="")
    	      {
    	      		alert("查找立案机构失败!");
    	      		return;
    	      }
    	      if(toperator==null||toperator=="")
    	      {
    	      		alert("查找立案操作员失败!");
    	      		return;
    	      } 	
		}
        else if (tGivetype == "1")
        {
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql31");
			mySql.addSubPara(fm.RptNo.value);  
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] != "1")
                    {
                        alert("保项不全部为拒付,不能下全部拒付结论!");
                        return;
                    }
                }
            }
        }
        else if (tGivetype == "0")
        {
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql32");
			mySql.addSubPara(fm.RptNo.value);  
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] == "0")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {
                    alert("保项全部为拒付,不能下给付结论!");
                    return;
                }
            }
        }
    }
    
    //如果是给付或拒付结论,提交前检验
    if (tGivetype == "0" || tGivetype == "1")
    {
        //调查、呈报、问题件和二核校验,保单结算、合同处理
        if (!checkPre())
        {
            return;
        } 
        
        //只有豁免时不需要做受益人分配
/*
        var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' );
        var tDutyKind = easyExecSql(tSQL);
        if (tDutyKind != '09')
        {
            //如果下给付结论,校验受益人分配
            if (tGivetype == "0")
            {
                var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );
                var tBNF = easyExecSql(strSql4);
                
                if (tBNF)
                {
                    for (var i = 0; i < tBNF.length; i++)
                    {
                        if (tBNF[i] == '0')
                        {
                            alert("受益人分配没有完成!");
                            return;
                        }
                    }
                }
            }
        }
*/
    }
    

    //如果是给付结论则判断是否有解除合同操作
/*
    if (tGivetype == "0")
    {
        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
                    + getWherePart( 'ClmNo','ClmNo' );
        var tCount = easyExecSql(strSql5);
        if (tCount > 0)
        {
            alert("已进行解除合同处理，不能下给付结论！");
            return;
        }
    }
*/

    //提交
    fm.action="./LLClaimAuditSave.jsp?pAuditConclusion="+pGivetype+"&mngNo="+tmanagecom+"&operator="+toperator+"&mRgtState="+fm.rgtType.value;
    submitForm();
}

//报案查询
function IsReportExist()
{
    //检查出险人、信息
    if (!checkInput1())
    {
        reture;
    }
    queryReport();

}

//检查出险人、信息
function checkInput1()
{
  //获取表单域信息
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//意外事故发生日期
    //取得年月日信息        
    var now = new Date();
    var nowYear = now.getFullYear();
    var nowMonth = now.getMonth() + 1;
    var nowDay = now.getDate();
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10); 
    //检查出险人信息
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("请先选择出险人！");
        return false;
    }
    //检查意外事故发生日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入意外事故发生日期！");
        return false;
    }  
    else
    {
        if (AccYear > nowYear)
        {
            alert("意外事故发生日期不能晚于当前日期");
            return false;  
        }
        else if (AccYear == nowYear)
        {
            if (AccMonth > nowMonth)
            {
            
                alert("意外事故发生日期不能晚于当前日期AccMonth > nowMonth");
                return false;
            }
            else if (AccMonth == nowMonth && AccDay > nowDay)
            {
                alert("意外事故发生日期不能晚于当前日期other");
                return false;        
            }
        }
    }
    return true;
}

//报案查询
function queryReport()
{
    var AccNo;
    var RptContent = new Array();
    var queryResult = new Array();
    //检索事件号(一条)
   /* var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql33");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value);             
//    alert("strSQL1= "+strSQL1);
    AccNo = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//    alert("AccNo= "+AccNo);
    if (AccNo == null )
    {
        fm.isReportExist.value = "false";
        alert("报案不存在!");
        return
    }
    else
    {
        //检索报案号及其他报案信息(一条)
        /*var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql34");
			mySql.addSubPara(AccNo); 
			mySql.addSubPara(fm.customerNo.value);                
//        alert("strSQL2= "+strSQL2);
        RptContent = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//        alert("RptContent= "+RptContent);
        if (RptContent == null)
        {
            fm.isReportExist.value = "false";
            alert("报案不存在!");
            return;
        }
        else
        {
            fm.isReportExist.value = "true";
            //更新页面内容
            fm.AccNo.value = AccNo;
            fm.RptNo.value = RptContent[0][0];
            fm.RptorName.value = RptContent[0][1];
            fm.RptorPhone.value = RptContent[0][2];
            fm.RptorAddress.value = RptContent[0][3];
            fm.Relation.value = RptContent[0][4];
            fm.RptMode.value = RptContent[0][5];
            fm.AccidentSite.value = RptContent[0][6];
            fm.occurReason.value = RptContent[0][7];
            fm.RptDate.value = RptContent[0][8];
            fm.MngCom.value = RptContent[0][9];
            fm.Operator.value = RptContent[0][10];//alert(1576);
            showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
            showOneCodeName('RptMode','RptMode','RptModeName');//报案方式            
            showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
            showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//            showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志
            //更新页面权限
//            fm.AccidentDate.readonly = true;
//            fm.occurReason.disabled=true;
//            fm.accidentDetail.disabled=true;
//            fm.claimType.disabled=true;
            fm.Remark.disabled=true;

            //检索分案关联出险人信息(多条)
           /* var strSQL3 = "select CustomerNo,Name," 
            	+ " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                + " Birthday,"
                + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                + " from LDPerson where "
                        + "CustomerNo in("
                        + "select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";*/
            //prompt("检索分案关联出险人信息(多条)",strSQL3);
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
//            alert("personInfo= "+personInfo);
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql35");
			mySql.addSubPara( RptContent[0][0]);  
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
        }//end else
    }
}

//立案查询
function queryRegister()
{
    var rptNo = fm.ClmNo.value;
//    alert("rptno="+rptNo);
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    
    //检索事件号、意外事故发生日期(一条)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql36");
			mySql.addSubPara( rptNo);  
    //alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
    //alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
    //-----------------------0-------1----------2-----------3-----------4------------5--------6-------7-------8--------------9-------------------------------------------------------------10------------11---------------12----------------------------------------------------------------------------13------------14-----------15-----------16------------17----------18-------19-------------20
    /*var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,(case assigneetype when '0' then '业务员' when '1' then '客户' end),assigneecode,assigneename,(case assigneesex when '0' then '男' when '1' then '女' when '2' then '未知' end),assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),accepteddate from llregister where "
                + "rgtno = '"+rptNo+"'";*/
     mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql37");
			mySql.addSubPara( rptNo);  
    //alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    //alert("RptContent= "+RptContent);
    //更新页面内容
     //alert("end!");
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
   if(RptContent!=null)
   {
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];//alert(1640);
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
    fm.AcceptedDate.value = RptContent[0][21];
 //   alert("have content!");
  }//alert(1659);
    showOneCodeName('llrgttype','rgtType','rgtTypeTypeName');//案件类型
    showOneCodeName('relation','Relation','RelationName');//alert(1661);//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
   
    //****************************************************
    //更新页面权限
    //****************************************************
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    fm.Remark.disabled=true;
//当需要复核时，设置审核确认按钮可见，否则直接结案。
    //if(fm.AuditConclusion.value == '4'){//审批管理
    ////fm.ConclusionSave.disabled = false;
    //}else{
    //fm.ConclusionSave.disabled = true;
    //}
   if( fm.AuditConclusion.value == '5'){//案件回退
    fm.ConclusionBack.disabled = false;
    }else{
    fm.ConclusionBack.disabled = true;
    }
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
    /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql38");
			mySql.addSubPara( rptNo);  */
    //prompt("检索分案关联出险人信息(多条)",strSQL3);
    easyQueryVer3Modal(strSQL3, SubReportGrid);
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
    /*var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "' ";*/
               //+"and ExamConclusion is null";
//    alert("strSql= "+strSql);
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql39");
			mySql.addSubPara( fm.ClmNo.value);  
    var tAudit = easyExecSql(mySql.getString());
    //alert(tAudit);
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
        //fm.printAuditRpt.disabled = false;
    }
  //  alert("query audit!");
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

//重点信息修改
function editImpInfo()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
  	var rptNo = fm.RptNo.value;//赔案号
  	var tIsShow=0;  //为0时该按钮能使用,为1时该按钮不能使用
    var strUrl="LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo;
    //var strUrl="../claim/LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo+"&IsShow="+tIsShow;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//选择赔付结论
function choiseGiveTypeType()
{
    if (fm.GiveType.value == '0')
    {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";
    }
    else if (fm.GiveType.value == '1')
    {
        divGiveTypeUnit1.style.display= "none";
        divGiveTypeUnit2.style.display= "";
        divGiveTypeUnit3.style.display= "none";
    }
//------------------------------------jinsh20070515--------------------------------------//    
    //else if (fm.GiveType.value == '2'||fm.GiveType.value == '3')
    //{
        //divGiveTypeUnit1.style.display= "";
        //divGiveTypeUnit2.style.display= "none";
        //divGiveTypeUnit3.style.display= "";
    //}
    choiseAdjReasonType();
}
//------------------------------------jinsh20070515--------------------------------------//
function choiseAdjReasonType()
{
	 if(fm.AdjReason.value == '14'||fm.AdjReason.value == '15')
    {        
        divGiveTypeUnit3.style.display= "none";
    }else
    	{
    		 divGiveTypeUnit3.style.display= "none";
    	}
}
//------------------------------------jinsh20070515--------------------------------------//
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
    //fm.conclusionSave1.disabled = false;
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
    var strUrl="../claim/LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    
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
    var tClmNo = fm.RptNo.value;
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tClmNo;
    //var strUrl="../claim/LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&flag=''";
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
    	 /**=========================================================================BEG
        修改状态：
        修改原因：在受益人分配前进行医疗类理赔金额和保项层面汇总金额的校验
        修 改 人：续涛
        修改日期：2005.08.11
       =========================================================================**/
    var tSql ;
    var tClaimNo = fm.RptNo.value;
    
    //查询医疗类理赔类型的金额
   /* tSql = " select nvl(sum(a.RealPay),0) from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       ;*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql40");
			mySql.addSubPara(tClaimNo);     
    var arr = easyExecSql( mySql.getString() );    
    var tSumDutyKind = arr[0][0];


    //查询保项层面医疗类理赔类型的金额
   /* tSql = " select nvl(sum(a.RealPay),0) from LLClaimDetail a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       +" and a.GiveType not in ('1') "       
       ;*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql41");
			mySql.addSubPara(tClaimNo);      
    var brr = easyExecSql( mySql.getString() );    
    
    var tSumDutyCode = brr[0][0];
            
    if (tSumDutyKind != tSumDutyCode)
    {
        alert("提示:医疗类赔付总金额为:["+tSumDutyKind+"],而保项赔付总金额为:["+tSumDutyCode+"],请先进行保项层面金额调整,以便进行保额冲减!");
        return;
    }
    /**=========================================================================END**/
    
    //调查、呈报、问题件和二核校验,保单结算、合同处理
    if (!checkPre())
    {
        return;
    } 
    
    var rptNo = fm.RptNo.value;//赔案号
    var strUrl="../claim/LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+fm.customerNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"");
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

//合同处理(由xutao于2005年7月14日添加)
function showContDeal()
{
    var strUrl="LLClaimContDealMain.jsp?ClmNo="+fm.ClmNo.value;
    strUrl= strUrl + "&CustNo=" + fm.customerNo.value;
    strUrl= strUrl + "&ConType="+fm.RgtClass.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//保单结算
function showPolDeal()
{
    var strUrl="LLClaimPolDealMain.jsp?ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查询出险结果2
function queryResult2()
{
    /*var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql42");
			mySql.addSubPara(fm.AccResult2.value);   
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

//检查保项金额调整,只能调小
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,1));//保单号
        var tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//险种编码
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,28));//责任编码
        var tGetDutyCode = PolDutyCodeGrid.getRowColData(i,4);//责任编码
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,21));//调整金额
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,9));//保额
        var tAdjM     = parseFloat(fm.RealPay.value);
        
        var tPolNo   = PolDutyCodeGrid.getRowColData(i,1);//险种保单号
       
        var strPSQL = "select riskamnt from lcpol where polno='"+tPolNo+"'";
       /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql43");
			mySql.addSubPara(tPolNo);   */
        var arrp= easyExecSql(strPSQL);
        
        if(arrp[0][0]== null){
        	alert("风险保额不存在");
        }
        var strSQL = "select 1 from lmrisksort where riskcode='"+tRiskCode+"' and risksorttype='26' and risksortvalue='"+tGetDutyCode+"'";
        //prompt("校验调整保项是否是津贴型:",strSQL);
        /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql44");
			mySql.addSubPara(tRiskCode);   
				mySql.addSubPara(tGetDutyCode);  */
        var arr= easyExecSql(strSQL);
        
       var strSQL1 = "select InsuAccFlag from lmrisk where riskcode='"+tRiskCode+"'";
		/*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql45");
			mySql.addSubPara(tRiskCode);    */
        var arr1= easyExecSql(strSQL1);

        
        if(fm.GiveType.value!='1')
        {
        //放开对津贴型险种的限制
        if(arr == null){
        	//对非帐户型险种用风险保额进行校验
        	if (arr1[0][0] != 'Y')
        	{

        		if (arrp[0][0] < tAdjM)
        			{
        				alert("调整金额不能大于风险保额!");
        				fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
        				return false;
        			} 
        	}
            else if(tAdjM < 0){
                alert("调整金额不能小于零!");
                fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
                return false;
            }
        }
        else
        {
        	return true;
        }
        }
        else
        {
        	return true;
        }

    }
}

//打印赔案审核报告----2005-08-12添加
function LLPRTClaimAuditiReport()
{
//  alert("打印赔案审核报告");
  fm.action="LLPRTClaimAuditiReportSave.jsp";
  fm.target = "../f1print";
  document.getElementById("fm").submit();
}


//补缴保费通知书----2005-08-16添加
function LLPRTPatchFeePrint()
{
   /* var strSql = " select count(1) from loprtmanager where 1=1 "
                + " and otherno='" + fm.ClmNo.value + "'"
                + " and t.code='PCT008'";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql46");
			mySql.addSubPara( fm.ClmNo.value );  
    var tCount = easyExecSql(mySql.getString());
    
    if (tCount == 0)
    {
        alert("没有可打印的数据!");
        return;
    }
    fm.action="LLPRTPatchFeeSave.jsp";
//  fm.target = "../f1print";
//  document.getElementById("fm").submit();
    if(beforePrtCheck(fm.ClmNo.value,"PCT008")=="false")
    {
      return;
    } 

}

//案件核赔履历表--查询 2005-08-16添加
function LLQueryUWMDetailClick()
{
//  alert("案件核赔履历查询");
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

/**********************************
//打印前检验（），需要传入参数（单证号码、赔案号）--------2005-08-22添加
***********************************/
function beforePrtCheck(clmno,code)
{
  //查询单证流水号，对应其它号码（赔案号）、单据类型、打印方式、打印状态、补打标志
     var tclmno=trim(clmno);
     var tcode =trim(code);
     if(tclmno=="" ||tcode=="")
     {
       alert("传入的赔案号或单证类型（号码）为空");
       return false;
     }
  /*  var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
      +" and t.otherno='"+tclmno+"' "
      +" and t.code='"+tcode+"' ";*/
  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql47");
			mySql.addSubPara( tclmno);  
			mySql.addSubPara( tcode);  
  var arrLoprt = easyExecSql(mySql.getString());
  if(arrLoprt==null)
  {
    alert("没有找到该单证的流水号");
    return false;
  }  
  else 
  {
    var tprtseq=arrLoprt[0][0];//单证流水号
    var totherno=arrLoprt[0][1];//对应其它号码（赔案号）
    var tcode=arrLoprt[0][2];//单据类型
    var tprttype=arrLoprt[0][3];//打印方式
    var tstateflag=arrLoprt[0][4];//打印状态
    var tpatchflag=arrLoprt[0][5];//补打标志
    fm.PrtSeq.value=arrLoprt[0][0];
    //存在但未打印过，直接进入打印页面
     if(tstateflag=="0")
     {
//      fm.action = './LLPRTCertificatePutOutSave.jsp';   //
      fm.target = "../f1print";
      document.getElementById("fm").submit();
     }
    else
    {
      //存在但已经打印过，tstateflag[打印状态：1 -- 完成、2 -- 打印的单据已经回复、3 -- 表示已经发催办通知书]
      if(confirm("该单证已经打印完成，你确实要补打吗？"))
       {
         //进入补打原因录入页面
         var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
         window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
    }
  } 
}

//超权限上报提交方法
function ConclusionUpClick(){
    fm.AuditConclusion.value = 6 ;//超权限上报
    ConclusionSaveClick();//直接保存审核结论
}

//上报到审批管理提交方法
function ConclusionSaveClick2(){
//    fm.AuditConclusion.value = 4 ;//审批管理
//    ConclusionSaveClick();//先保存审核结论
//if(fm.AuditConclusion.value == 4){
     //查询是否进行过匹配计算 2009-08-04 9:08
  var Detailsql = "select count(1) from LLClaimDetail where"
           + " ClmNo = '" + fm.RptNo.value + "'";
   /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql48");
			mySql.addSubPara( fm.RptNo.value);  */ 
  var tDetailDutyKind = easyExecSql(Detailsql);
  if (tDetailDutyKind == 0)
  {
      alert("请先进行匹配并理算!");
      return;
  }
		
	//判断审核结论
    var strSql = "select AuditConclusion from LLClaimUWMain where "
               + "clmno = '" + fm.RptNo.value + "'";
     /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql49");
			mySql.addSubPara( fm.RptNo.value);    */       
    var tGivetype = easyExecSql(strSql);
    if (tGivetype == null)
    {
        alert("请先保存审核结论！");
        return;
    }
    else
    {
        if (tGivetype == "1")
        {
            var tGtype = new Array;
            var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";
             /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql50");
			mySql.addSubPara( fm.RptNo.value); */ 
            tGtype = easyExecSql(strSql);
            if (tGtype != null)
            {
                //alert(tGtype.length);
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] != "1")
                    {
                        alert("保项不全部为拒付,不能下全部拒付结论!");
                        return;
                    }
                }
            }
        }
        else if (tGtype == "0")
        {
            var tGtype = new Array;
            var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";
           /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql51");
			mySql.addSubPara( fm.RptNo.value);  */
            tGtype = easyExecSql(strSql);
            if (tGtype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] == "0"||tGtype == "4")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {
                    alert("保项全部为拒付,不能下给付结论!");
                    return;
                }
            }
        }
    }
    
    //如果是给付或拒付结论,提交前检验
    if (tGivetype == "0" || tGivetype == "1"||tGivetype == "4")
    {
        //调查、呈报、问题件和二核校验,保单结算、合同处理
        if (!checkPre())
        {
            return;
        } 
        
        //只有豁免时不需要做受益人分配
        var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' );
         /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql52");
			mySql.addSubPara( fm.ClmNo.value);  */
        var tDutyKind = easyExecSql(tSQL);
        if (tDutyKind != '09')
        {
//            //如果下给付结论,校验受益人分配
//            if (tGivetype == "0")
//            {
                var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );
                /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql53");
			mySql.addSubPara( fm.ClmNo.value);  */
                var tBNF = easyExecSql(strSql4);
                
                if (tBNF)
                {
                    for (var i = 0; i < tBNF.length; i++)
                    {
                        if (tBNF[i] == '0')
                        {
                            alert("受益人分配还没有完成!");
                            return;
                        }
                    }
                }
//            }
        }
    }

		/**
		 * 2009-02-10 zhangzheng  
		 * MS团险权限管理未定,暂时封住这段校验
		 */
//		var tManageCom = fm.ManageCom.value.substring(0,2);
//		var strSql0501 = " select checklevel,username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
//		var tSubReport2 = new Array;
//		    tSubReport2 = easyExecSql(strSql0501);
//		if(tSubReport2 != null)
//		{
//		    var tChecklevel = tSubReport2[0][0];
//		    var tUserName   = tSubReport2[0][1];
//		    var tUserCode   = tSubReport2[0][2];
//		}else{
//		    alert("未查询到该分机构的最高级别理赔人员!");
//		    return false;
//		} 
//		if(tUserCode != fm.tOperator.value)
//		{
//        fm.ConclusionSave.disabled = true;
//        fm.ConclusionUp.disabled = false;
//        fm.ConclusionBack.disabled = true;                    
//        fm.conclusionSave1.disabled=false;  		    
//		    alert("请本分公司最高理赔级别, "+tUserName+" 登录把案件上报！请先进行超权限上报！");
//		    return false;
//		}		
    
  var strSql5 = "select RealPay from LLClaim where "
        + "clmno = '" + fm.RptNo.value + "'";
  /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql54");
			mySql.addSubPara( fm.RptNo.value);  */
  var tRealPay = easyExecSql(strSql5);
  if (tRealPay == null)
   {
     alert("赔案信息缺失，LLClaim无赔付金额！");
     return;
   }
  else
  {
	  fm.adjpay.value=tRealPay;
  }

    AuditConfirmClick();//再进行工作流操作
//}else{
    //alert("请先保存审核结论!");
    //return false;
//}

}



//---------------------------------------------------------------------------------------*
//功能：理赔类型逻辑判断
//处理：1 死亡、高残、医疗三者只可选一
//    2 选择死亡或高残时，默认选中豁免
//---------------------------------------------------------------------------------------*
function callClaimType(ttype)
{
  switch (ttype)
  {
      case "02" : //死亡
          if (fm.claimType[0].checked == true)
          {
              fm.claimType[4].checked = true;
          }
//          if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//          {
//              alert("死亡、高残、医疗三者只可选一!");
//              fm.claimType[0].checked = false;
//              return;
//          }
//          fm.claimType[4].checked = true;
      case "03" :
          if (fm.claimType[1].checked == true)
          {
              fm.claimType[4].checked = true;
          }
//          if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//          {
//              alert("死亡、高残、医疗三者只可选一!");
//              fm.claimType[1].checked = false;
//              return;
//          }
//          fm.claimType[4].checked = true;
      case "09" :
//          if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//          {
//              alert("选取死亡、高残必须选择豁免!");
//              fm.claimType[4].checked = true;
//              return;
//          }
      case "00" :
//          if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//          {
//              alert("死亡、高残、医疗三者只可选一!");
//              fm.claimType[5].checked = false;
//              return;
//          }
      default :
          return;
  }
}



//进行保险责任匹配
function showMatchDutyPay2(tAccNo)
{  
	fm.AccNo.value=tAccNo;

	fm.hideOperate.value="MATCH||INSERT";

	fm.action = "./LLClaimRegisterMatchCalSave2.jsp";
	fm.target="fraSubmit";
	document.getElementById("fm").submit(); //提交
}

//点击匹配并理算按钮后的动作
function afterMatchDutyPay2(FlagStr, content)
{
	//理算出错就提示,不再执行结论保存
    if (FlagStr == "FAIL" )
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
    	afterMatchDutyPayQuery();
    }
}


//2009-04-22 zhangzheng 双击下拉框后的响应函数
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//审核结论
    if(cCodeName=="llexamconclusion2"){
    	
    	//当给付原因不为拒付时,清空拒付原因和拒付依据
    	if(fm.AuditConclusion.value !='1'){
    		
       		fm.ProtestReason.value='';
    		fm.ProtestReasonName.value='';
    		fm.ProtestReasonDesc.value='';	
    		fm.SpecialRemark1.value='';
    	}
    	else
    	{
    		//清空特殊备注
    		fm.SpecialRemark1.value='';
    	}
        return true;
    }
}

//zy 2009-07-28 17:43 获取康福产品保单的险种信息
function getLLEdorTypeCA()
{

	// var tAccRiskCodeSql="select distinct riskcode,grpcontno from lcgrppol where grpcontno in (select grpcontno from llclaimpolicy where clmno='"+fm.RptNo.value+"') and riskcode='211901' and grpname like 'MS人寿保险股份有限公司%'";
	 //prompt("",tAccRiskCodeSql);
  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql55");
			mySql.addSubPara( fm.RptNo.value);  
   var arr=easyExecSql(mySql.getString());
   if (arr)
   {
   	var tAccRiskCode = arr[0][0];
   	var GrpContNo= arr[0][1];
   	 if(tAccRiskCode=="211901")
	   {
	   	fm.AccRiskCode.value=tAccRiskCode;
	   }
	   else
	   {
	   	fm.AccRiskCode.value="";   
	   }
	   if(GrpContNo==null || GrpContNo=="")
	   {
	   	alert("没有相应的团单号，请核实");
	   	return ;
	   	}
	   	else
	   		fm.GrpContNo.value=GrpContNo;

    }


}

//zy 2009-07-28 14:58 账户资金调整
function ctrllcinsureacc()
{
	
    var strUrl="./LLGrpClaimEdorTypeCAMain.jsp?GrpContNo="+fm.GrpContNo.value+"&RptNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}