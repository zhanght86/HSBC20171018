var showInfo;
var turnPage = new turnPageClass();

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

/**=========================================================================
    修改状态：开始
    修改原因：以下审核阶段，进行匹配，计算，确认等函数功能区
    修 改 人：续涛
    修改日期：2005.06.01
   =========================================================================
**/

//进行保险责任匹配
function showMatchDutyPay()
{
    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();


    
    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.submit(); //提交
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
    tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;
    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }
    
    //查询LLClaimDutyKind，按照赔案理赔类型进行查找
    tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyKind)),"
       +" a.TabFeeMoney,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"


    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
    }
    else
    {
        initDutyKindGrid();
    }        
  
    //查询LLClaimPolicy,查询保单理赔类型层面的信息
    tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"

    arr = easyExecSql( tSql );
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
    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where trim(c.GetDutyKind) = trim(a.GetDutyKind) and trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0),"
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
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
       +" a.dutycode "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"   
       +" and a.GiveType not in ('1')" 

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

//录入医疗单证信息
function showLLMedicalFeeInp()
{

    //var tSel = SubReportGrid.getSelNo();
    //var tClaimNo = SubReportGrid.getRowColData(tSel - 1,8);         //赔案号
    //var tCaseNo = SubReportGrid.getRowColData(tSel - 1,2);          //分案号
    //var tCustNo = SubReportGrid.getRowColData(tSel - 1,1);          //客户号
//    if( tSel == 0 || tSel == null ){
//        alert( "请先选择一条记录，再执行此操作!!!" );
//    }else{
//        window.open("LLMedicalFeeInpInput.jsp?clamNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&custName="+tCustName+"&custSex="+tCustSex,"医疗单证信息");
//    }

    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate1="+fm.AccidentDate.value+"&accDate2="+fm.AccidentDate2.value;
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

/**=========================================================================
    修改状态：结束
    修改原因：以上审核阶段，进行匹配，计算，确认等函数功能区
    修 改 人：续涛
    修改日期：2005.06.01
   =========================================================================
**/

//发起二核
function showSecondUWInput()
{
	  var strUrl="LLSecondUWMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;    
    window.open(strUrl,"");
}

//二核处理
function SecondUWInput()
{
	  var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;    
    window.open(strUrl,"");
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
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&custVip="+fm.IsVip.value+"&initPhase=02";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"发起调查");
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
    var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";
    var tCount = easyExecSql(strSQL);
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
	//***************************************
	//判断该赔案是否存在呈报
	//***************************************
    var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";
    var tCount = easyExecSql(strSQL);
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("该赔案还没有呈报信息！");
    	  return;
    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"呈报查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open(strUrl,"");
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
    var strUrl="LLLcContSuspendMain.jsp?InsuredNo="+tCustomerNo;
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
    fm.MngCom.value = arr[11];    
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
//    showOneCodeName('RptMode','RptMode','RptModeName');//报案方式(?????)  	
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    
    //查询获得事件号
    var LLAccident = new Array;
    var strSQL1 = "select AccNo,AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+arr[0]+"')";
//    alert(LLAccident);
    LLAccident = easyExecSql(strSQL1);
    fm.AccNo.value = LLAccident[0][0];
    fm.AccidentDate.value = LLAccident[0][1];
    
    //查询获得出险人信息
    initSubReportGrid();
    var strSQL2 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ arr[0] +"')";
//    alert(strSQL2);                 
    easyQueryVer3Modal(strSQL2,SubReportGrid);    
    
    //设置可操作域
    fm.AuditIdea.disabled = false;
    fm.AuditConclusion.disabled = false;
    fm.ConclusionSave.disabled = false;
    fm.SpecialRemark.disabled = false;
   
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
	fm.IsVip.value = "";
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
        fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";
//    alert(tClaimType);
    tClaimType = easyExecSql(strSQL1);
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
    //--------------------------1----------2---------3-----------4---------5------6-----7-------------8----------9----------10---------11
    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,(case EditFlag when '0' then '否' when '1' then '是' end),(case AffixConclusion when '0' then '否' when '1' then '是' end) from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
//    alert(strSQL2);
    tSubReport = easyExecSql(strSQL2);
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
    fm.GiveTypeDesc.value = "";//拒付原因代码
    fm.GiveReason.value = "";//拒付依据
    fm.SpecialRemark.value = "";//特殊备注
    //设置按钮
    fm.addUpdate.disabled = false;//添加修改
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
        fm.GiveTypeDesc.value = PolDutyCodeGrid.getRowColData(i,15);//拒付原因代码
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,17);//拒付依据
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,18);//特殊备注
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveTypeDesc','GiveTypeDescName');
    }
    //显示隐藏层
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
}

//申诉信息保存
function AppealSave()
{
    fm.action = './LLAppealInpSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
}

//赔案处理
function ClaimDeal()
{
    //隐藏申诉信息
    divAll1.style.display = "none";
    divAll2.style.display = "";
    
    queryRegister();
    afterMatchDutyPayQuery();
}

//对保项增加修改
function AddUpdate()
{
    var i = PolDutyCodeGrid.getSelNo() - 1;//得到焦点行

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//赔付结论
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveTypeDesc.value);//拒付原因代码
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReason.value);//拒付依据
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//特殊备注
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//调整原因
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//调整备注
    
    fm.saveUpdate.disabled = false;//保存修改
}

//对保项修改保存到后台
function SaveUpdate()
{
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
}

//公共提交方法
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
//    fm.action = './LLAppealSave.jsp';
    fm.submit(); //提交
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
function afterSubmit3( FlagStr, content )
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
    tSaveFlag ="0";
}

//提交后操作,重新查询
function afterSubmit4( FlagStr, content )
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


        
        queryAppeal();
    }
    tSaveFlag ="0";
}

//返回按钮
function goToBack()
{
    location.href="LLAppealMissInput.jsp";
}

//审核结论保存
function ConclusionSaveClick()
{
	//判断表单输入是否为空
	if (fm.AuditConclusion.value == "" || fm.AuditConclusion.value == null)
	{
		alert("请先填写审核结论!");
        return;
	}
    else
    {
        if (fm.AuditConclusion.value == "1")
        {
            var tGivetype = new Array;
            var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";
            tGivetype = easyExecSql(strSql);
            if (tGivetype != null)
            {
                //alert(tGivetype.length);
                for (var i=0; i<tGivetype.length; i++)
                {
                    //alert(tGivetype[0][i])
                    if (tGivetype[0][i] != "1")
                    {
                        alert("保项不全部为拒付!");
                        return;
                    }
                }
            }
        }
    }
    //提交
    fm.action="./LLAppealConclusionSave.jsp"
    fm.fmtransact.value = "INSERT";
    submitForm();
}

//结论确认
function ConfirmClick()
{
    //提交
    fm.action="./LLAppealSave.jsp"
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
    var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";
                
//    alert("strSQL1= "+strSQL1);
    AccNo = decodeEasyQueryResult(easyQueryVer3(strSQL1));
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
        var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";
//        alert("strSQL2= "+strSQL2);
        RptContent = decodeEasyQueryResult(easyQueryVer3(strSQL2));
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
      		  fm.Operator.value = RptContent[0][10];
      		  showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
      		  showOneCodeName('RptMode','RptMode','RptModeName');//报案方式      		  
      		  showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
      		  showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//      		  showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志
      		  //更新页面权限
//      		  fm.AccidentDate.readonly = true;
//      		  fm.occurReason.disabled=true;
//      		  fm.accidentDetail.disabled=true;
//      		  fm.claimType.disabled=true;
      		  fm.Remark.disabled=true;

            //检索分案关联出险人信息(多条)
            var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                        + "CustomerNo in("
                        + "select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";
//            alert("strSQL3= "+strSQL3);
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
//            alert("personInfo= "+personInfo);
            easyQueryVer3Modal(strSQL3, SubReportGrid);
        }//end else
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
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '" + rptNo + "'";
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(strSQL1);
//    alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
    var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end) from llregister where "
                + "rgtno = '" + rptNo + "'";
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(strSQL2);
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
    showOneCodeName('llrgttype','rgtType','rgtTypeTypeName');
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
    var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";
//    alert("strSQL3= "+strSQL3);
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
    var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "'";
//    alert("strSql= "+strSql);
    var tAudit = easyExecSql(strSql);
//    alert(tAudit);
    if (tAudit != null)
    {
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        fm.ProtestReason.value = tAudit[0][3];
        fm.ProtestReasonDesc.value = tAudit[0][4];
        showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        //显示隐藏层
        choiseConclusionType();
    }
}

//申诉信息查询
function queryAppeal()
{
    //-------------------------1----------2---------3---------4----------5--------6------7-----8---------9-------10---------11--------12-----13----14-------15
    var strSql = " select AppealType,AppealState,WaitDate,AppealName,AppealSex,Address,Phone,Mobile,AppealMode,PostCode,Relation,ReturnMode,IDNo,IDType,AppealRDesc,(case AppealState when '0' then '未回复' when '1' then '已回复' end ) from LLAppeal where "
               + " AppealNo = '" + fm.ClmNo.value + "'";
//    alert(strSql);
    var tAppeal = easyExecSql(strSql);
//    alert(tAppeal);
    if (tAppeal != null)
    {
        fm.AppealType.value = tAppeal[0][0];
        fm.AppealState.value = tAppeal[0][1];
        fm.WaitDate.value = tAppeal[0][2];
        fm.AppealName.value = tAppeal[0][3];
        fm.AppealSex.value = tAppeal[0][4];
        fm.Address.value = tAppeal[0][5];
        fm.Phone.value = tAppeal[0][6];
        fm.Mobile.value = tAppeal[0][7];
//        fm.AppealMode.value = tAppeal[0][8];
        fm.PostCode.value = tAppeal[0][9];
        fm.Relation2.value = tAppeal[0][10];
//        fm.ReturnMode.value = tAppeal[0][11];
        fm.IDNo.value = tAppeal[0][12];
        fm.IDType.value = tAppeal[0][13];
        fm.AppealRDesc.value = tAppeal[0][14];
        fm.AppealStateName.value = tAppeal[0][15];
        
        showOneCodeName('llappealflag','AppealType','AppealTypeName');  //申诉类型 
        showOneCodeName('sex','AppealSex','AppealSexName');  //申诉人性别
        showOneCodeName('llrgrelation','Relation2','Relation2Name');  //与被保人关系
        showOneCodeName('llidtype','IDType','IDTypeName');  //申请人证件类型
    }
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
    var strUrl="LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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

//受益人分配
function showBnf()
{
	var rptNo = fm.RptNo.value;//赔案号
    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"出险人分配");
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
    var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";
    var tICDName = easyExecSql(strSql);
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}