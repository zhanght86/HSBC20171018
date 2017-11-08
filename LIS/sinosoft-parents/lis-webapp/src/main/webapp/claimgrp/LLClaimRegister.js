var showInfo;
var turnPage = new turnPageClass();

//单证回销
function showRgtMAffixList()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"单证回销");
}

//立案单证补充
function showRgtAddMAffixList()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtAddMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=RgtAdd";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"单证补充");
}

/**=========================================================================
    修改状态：开始
    修改原因：以下立案结论保存后，进行匹配，计算，确认等函数功能区
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
    fm.target="fraSubmit";
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
    var arr = new Array;

    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    //查询LLClaim，整个赔案的金额
    tSql = " select a.realpay,b.BeAdjSum "
         + " from LLClaim a,LLRegister b  where 1=1 "
         + " and a.ClmNo = b.RgtNo and a.ClmNo = '"+tClaimNo+"'"

    arr = easyExecSql( tSql );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.standpay.value  = arr[0][0];
        arr[0][1]==null||arr[0][1]=='null'?'0':fm.adjpay.value  = arr[0][1];
        //fm.adjpay.value = fm.standpay.value;
        if (fm.adjpay.value == "0")
        {
            fm.adjpay.value = fm.standpay.value;
        }
    }

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
    ////to_char(b.PayEndDate,'yyyy-mm-dd')+a.GracePeriod," //交至日期 + 宽限期--+ a.GracePeriod
    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where trim(c.GetDutyKind) = trim(a.GetDutyKind) and trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0),"
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.RealPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and trim(e.code)=trim(a.GiveType)),"
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
       +" a.DutyCode "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"
       +" and a.GetDutyCode = b.GetDutyCode"
       +" and a.ClmNo = '"+tClaimNo+"'"

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
    修改原因：以上立案结论保存后，进行匹配，计算，确认等函数功能区
    修 改 人：续涛
    修改日期：2005.06.01
   =========================================================================
**/

//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
  var tCustomerNo = fm.customerNo.value;
  if (tCustomerNo == "")
  {
      alert("请选择出险人！");
      return;
  }
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//既往赔案查询
function showOldInsuredCase()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
  var tCustomerNo = fm.customerNo.value;
  if (tCustomerNo == "")
  {
      alert("请选择出险人！");
      return;
  }
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查看调查
function showQueryInq()
{
  var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    //---------------------------------------
    //判断该赔案是否存在调查
    //---------------------------------------
    var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";
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

//打开呈报查询
function showQuerySubmit()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
  //---------------------------------------
  //判断该赔案是否存在呈报
  //---------------------------------------
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

//打开事故描述信息
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=02";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"事故描述信息");
}

//报案查询
function showLLReportQuery()
{
//    window.open("LLReportQueryInput.jsp","报案查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open("LLReportQueryInput.jsp","");
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
    var strUrl="LLLcContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;//被保人客户号==出险人客户号
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
//    fm.IsDead.value = "";
    fm.cureDesc.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
        fm.claimType[j].checked = false;
    }
    //设置可操作按钮
    fm.addbutton.disabled=false;
    fm.QueryCont2.disabled = false;
    fm.QueryCont3.disabled = false;
}

//由事件查询返回
function afterQueryLL2(tAccNo,tOccurReason,tAccDesc,tAccidentSite)
{
  //得到返回值
    fm.AccNo.value = tAccNo;
    fm.occurReason.value = tOccurReason;
    fm.AccDesc.value = tAccDesc;
    fm.AccDescDisabled.value = tAccDesc;
    fm.AccidentSite.value = tAccidentSite;
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
}

//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase)
{
  //--------------------------------------------Beg
  //置空相关表单
  //--------------------------------------------
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
    //--------------------------------------------End

  //--------------------------------------------Beg
  //显示隐藏区域
  //--------------------------------------------
  spanText1.style.display = "none";
  spanText2.style.display = "";
    spanText3.style.display = "none";
  spanText4.style.display = "";
  //--------------------------------------------End

    //取得数据
    var i = "";
    if (tPhase == 0)
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != 0)
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,4));
        fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
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
//                alert(tClaim);
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;

                    //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2 from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
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
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult2();
    afterMatchDutyPayQuery();
}

//查询立案分案信息,tPhase=0为初始化时调用
function SubReportGridClick2(tPhase)
{
  //------------------------------------------**Beg
  //置空相关表单
  //------------------------------------------**
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
    //------------------------------------------**End

    //--------------------------------------------Beg
  //显示隐藏区域
  //--------------------------------------------
  spanText1.style.display = "none";
  spanText2.style.display = "";
    spanText3.style.display = "none";
  spanText4.style.display = "";
  //--------------------------------------------End

    //取得数据
    var i = "";
    if (tPhase == 0)
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != 0)
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,4));
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
//                alert(tClaim);
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;

                    //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
    //-------------------------1----------2-----------3---------4--------5-------6--------7-----------8-----------9------------10-------------------------------------------11
    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,AffixConclusion,(case AffixConclusion when '0' then '否' when '1' then '是' end) from LLCase where 1=1 "
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
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
//        alert(fm.AccDescDisabled.value);
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.IsAllReady.value = tSubReport[0][9];
    fm.IsAllReadyName.value = tSubReport[0][10];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult2();
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//单证齐全标志
}

//选中SubReportGrid响应事件,tPhase=1为选择行时调用
function allSubReportGridClick(tPhase)
{
    if(fm.isRegisterExist.value == "true")
    {
        SubReportGridClick2(tPhase);
    }
    else
    {
        SubReportGridClick(tPhase);
    }
}

//参数为出生年月,如1980-5-9
function getAge(birth)
{
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}

//
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
    fm.target="fraSubmit";
    fm.submit(); //提交
    tSaveFlag ="0";

}

//提交前的校验、计算
function beforeSubmit()
{
    //获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//事故日期
    var AccReason = fm.occurReason.value;//出险原因
    var AccDate2 = fm.AccidentDate2.value;//出险日期
    var AccDesc = fm.accidentDetail.value;//出险细节
    var ClaimType = new Array;
    //理赔类型
    for(var j=0;j<fm.claimType.length;j++)
    {
        if(fm.claimType[j].checked == true)
        {
            ClaimType[j] = fm.claimType[j].value;
        }
    }
//    alert("ClaimType = "+ClaimType);
    //取得年月日信息
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    //----------------------------------------------------------BEG2005-7-12 16:45
    //增加申请人姓名和关系的非空校验
    //----------------------------------------------------------
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("请输入申请人姓名！");
        return false;
    }
    if (fm.Relation.value == "" || fm.Relation.value == null)
    {
        alert("请输入申请人与事故人关系！");
        return false;
    }
//    if (fm.AssigneeName.value == "" || fm.AssigneeName.value == null)
//    {
//        alert("请输入受托人姓名！");
//        return false;
//    }
//    if (fm.AssigneePhone.value == "" || fm.AssigneePhone.value == null)
//    {
//        alert("请输入受托人电话！");
//        return false;
//    }
//    if (fm.AssigneeAddr.value == "" || fm.AssigneeAddr.value == null)
//    {
//        alert("请输入受托人地址！");
//        return false;
//    }
//    if (fm.AssigneeZip.value == "" || fm.AssigneeZip.value == null)
//    {
//        alert("请输入受托人邮编！");
//        return false;
//    }
//    else
//    {
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("邮编错误！");
        return false;
    }
//    }
    //----------------------------------------------------------end
    //1 检查出险人、信息
    if (checkInput1() == false)
    {
        return false;
    }
    //2-1 检查出险日期
    if (AccDate2 == null || AccDate2 == '')
    {
        alert("出险日期不能为空！");
        return false;
    }
    else
    {
        if (AccYear2 > mNowYear)
        {
            alert("出险日期不能晚于当前日期");
            return false;
        }
        else if (AccYear2 == mNowYear)
        {
            if (AccMonth2 > mNowMonth)
            {

                alert("事故日期不能晚于当前日期");
                return false;
            }
            else if (AccMonth2 == mNowMonth && AccDay2 > mNowDay)
            {
                alert("出险日期不能晚于当前日期");
                return false;
            }
        }
    }
    //2-2 检查出险日期
    if (AccYear > AccYear2 )
    {
        alert("事故日期不能晚于出险日期");
        return false;
    }
    else if (AccYear == AccYear2)
    {
        if (AccMonth > AccMonth2)
        {

            alert("事故日期不能晚于出险日期");
            return false;
        }
        else if (AccMonth == AccMonth2 && AccDay > AccDay2)
        {
            alert("事故日期不能晚于出险日期");
            return false;
        }
    }
    //3 检查出险原因
    if (AccReason == null || AccReason == '')
    {
        alert("出险原因不能为空！");
        return false;
    }
    //4 检查出险细节
    if ((AccDesc == null || AccDesc == '') && fm.occurReason.value == '1')
    {
        alert("出险原因为意外,出险细节不能为空！");
        return false;
    }
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //添加出险原因为疾病时，事故日期等于出险日期
    //---------------------------------------------------------------------------------**
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("出险原因为疾病时，事故日期必须等于出险日期！");
        return false;
    }
    //---------------------------------------------------------------------------------**End

    //5 检查理赔类型
    if (ClaimType == null || ClaimType == '')
    {
        alert("理赔类型不能为空！");
        return false;
    }
    //---------------------------------------------*Beg*2005-6-13 20:26
    //添加理赔类型中选中"医疗"必须选择医院的判断
    //---------------------------------------------*
    /*
    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
    {
        alert("请选择医院！");
        return false;
    }
    */
    //---------------------------------------------*End
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //添加理赔类型中选中豁免,必须选中死亡或高残之一的判断
    //---------------------------------------------------------
    if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false)
    {
        alert("选中豁免,必须选中死亡或高残之一!");
        return false;
    }
    //---------------------------------------------------------End
    return true;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
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


        fm.isReportExist.value = "true";
        fm.isRegisterExist.value = "true";

        notDisabledButton();
        fm.RgtConclusionName.value = "";
        queryRegister();
        //设置可操作按钮
        if(fm.isNew.value == '0')
        {
            operateButton21.style.display="";
            operateButton22.style.display="none";
            fm.QueryPerson.disabled = false;
            fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
            operateButton21.style.display="none";
            operateButton22.style.display="";
            fm.QueryPerson.disabled = true;
            fm.QueryReport.disabled = true;
        }
    }
    tSaveFlag ="0";
}

//立案确认返回后执行的操作
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

        goToBack();
    }
    tSaveFlag ="0";
}

//立案确认
function RegisterConfirm()
{
    var yesORno = 0;
    if (fm.RptNo.value == "")
    {
         alert("赔案号为空！");
         return;
    }
    //-------------------------------------------------------------------BEG2005-8-9 8:58
    //检查立案结论、匹配计算、单证齐全
    //-------------------------------------------------------------------
    var sql1 = " select RgtConclusion from llregister where "
            + " RgtNo = '" + fm.RptNo.value + "'"
    var tRgtConclusion = easyExecSql(sql1);
    if (tRgtConclusion == null || tRgtConclusion == "")
    {
        alert("请先保存立案结论!");
        return;
    }
    else
    {
        if (tRgtConclusion == "01")
        {
            //查询是否进行过匹配计算
            var sql2 = "select count(1) from LLClaimDetail where"
                     + " ClmNo = '" + fm.RptNo.value + "'";
            var tDutyKind = easyExecSql(sql2);
            if (tDutyKind == 0)
            {
                alert("请先进行匹配并理算!");
                return;
            }
            //检查单证齐全标志
            if (fm.IsAllReady.value != "1")
            {
//                if (confirm("单证不齐全,是否立案!"))
//                {
//                    yesORno = 1;
//                }
//                else
//                {
//                    yesORno = 0;
//                    return;
//                }
                alert("单证不齐全,不能立案通过!");
                return;
            }
            else
            {
                var tResult = new Array;
                var sql = " select affixconclusion from llcase where "
                         + " caseno = '" + fm.RptNo.value + "'"
                tResult = easyExecSql(sql);
                if (tResult != null)
                {
                    for (var i=0; i < tResult.length; i++)
                    {
                        if (tResult[0][i] == "0")
                        {
//                            if (confirm("单证不齐全,是否立案!"))
//                            {
//                                yesORno = 1;
//                            }
//                            else
//                            {
//                                yesORno = 0;
//                                return;
//                            }
                            alert("单证不齐全,不能立案通过!");
                            return;
                        }
                        else
                        {
                            yesORno = 1;
                        }
                    }
                }
            }
        }
        else
        {
            yesORno = 1;
        }
    }
    //-------------------------------------------------------------------END

    //-------------------------------------------------------------------BEG
    //检查保项结论，全部不给时不能立案
    //-------------------------------------------------------------------
    if (tRgtConclusion == '01')
    {
        var tGivetype = new Array;
        var strSql = "select givetype from LLClaimDetail where "
                   + "clmno = '" + fm.RptNo.value + "'";
        tGivetype = easyExecSql(strSql);
        var tYes = 0;
        if (tGivetype != null)
        {
            //alert(tGivetype.length);
            for (var i=0; i<tGivetype.length; i++)
            {
                if (tGivetype[i] == "0")
                {
                    tYes = 1;
                }
            }
        }
        if (tYes == 0)
        {
            alert("保项全部为不给时不能立案通过!");
            return;
        }
    }
    //-------------------------------------------------------------------END

    //是否提交
    if (yesORno == 1)
    {
        fm.RgtConclusion.value = tRgtConclusion;
        fm.action = './LLClaimRegisterConfirmSave.jsp';
        submitForm();
    }
}

//[保存]按钮对应操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit())
    {
        if (fm.RgtClass.value == "" || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "1";
        }

        //判断区分新增、保存立案、还是保存出险人
        if (fm.isReportExist.value == "false")
        {
            fm.fmtransact.value = "insert||first";
        }
        else
        {
            fm.fmtransact.value = "insert||customer";
        }
        fm.action = './LLClaimRegisterSave.jsp';
        submitForm();
    }
}

//[保存]按钮对应操作
function saveConclusionClick()
{
	alert('11');
    //首先进行非空字段检验
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == '' || fm.NoRgtReason.value == null))
    {
        alert("请填写不予立案原因!");
        return;
    }
    if (fm.RgtConclusion.value == '01')
    {
        //查询是否进行过匹配计算
        var sql2 = "select count(1) from LLClaimDetail where"
                 + " ClmNo = '" + fm.RptNo.value + "'";
        var tDutyKind = easyExecSql(sql2);
        if (tDutyKind == 0)
        {
            alert("请先进行匹配并理算!");
            return;
        }
    }
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave.jsp';
    submitForm();
}

//出险人信息修改
function updateClick()
{
    if (confirm("您确实想修改该记录吗？"))
    {
        if (beforeSubmit())
        {
            fm.fmtransact.value = "update";
            fm.action = './LLClaimRegisterSave.jsp';
            submitForm();
        }
    }
}

//出险人查询
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryInput.jsp");

}

//新增立案
function addClick()
{
    resetForm();

}


//出险原因判断
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        fm.accidentDetail.disabled = true;
    }
}

//报案查询
function IsReportExist()
{
    //检查出险人、信息
    if (!checkInput1())
    {
        return;
    }
    queryReport();

}

//检查出险人、信息
function checkInput1()
{
    //获取表单域信息
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//事故日期
    //取得年月日信息
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    //检查出险人信息
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("请先选择出险人！");
        return false;
    }
    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入事故日期！");
        return false;
    }
    else
    {
        if (AccYear > mNowYear)
        {
            alert("事故日期不能晚于当前日期!");
            return false;
        }
        else if (AccYear == mNowYear)
        {
            if (AccMonth > mNowMonth)
            {

                alert("事故日期不能晚于当前日期!");
                return false;
            }
            else if (AccMonth == mNowMonth && AccDay > mNowDay)
            {
                alert("事故日期不能晚于当前日期!");
                return false;
            }
        }
    }
    return true;
}

//报案查询
function queryReport()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    //检索事件号(一条)
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(strSQL1);
//    alert("AccNo= "+AccNo);
    //检索报案号及其他报案信息(一条)
    var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass from LLReport where "
                + "RptNo = '"+rptNo+"'";
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(strSQL2);
//    alert("RptContent= "+RptContent);
    //更新页面内容
if(AccNo!=null){
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
}
if(RptContent!=null){
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
//    fm.RptMode.value = RptContent[0][5];
    fm.AccidentSite.value = RptContent[0][6];
    fm.occurReason.value = RptContent[0][7];
    fm.RptDate.value = RptContent[0][8];
    fm.MngCom.value = RptContent[0][9];
    fm.Operator.value = RptContent[0][10];
}
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
//    showOneCodeName('RptMode','RptMode','RptModeName');//报案方式
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志

    //---------------------------------------------------*
    //更新页面权限
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;

    fm.QueryPerson.disabled=true;
    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

    //设置按钮++++++++++++++++++++++++++++++++++++++++待添加

    //检索分案关联出险人信息(多条)
    var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(strSQL3, SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
}

//申请人查询,当案件为非死亡类时申请人为出险人
function queryProposer()
{
    var strSQL = "select count(1) from LLReportReason a where "
                + "a.rpno = '" + fm.RptNo.value + "'"
                + "and substr(a.reasoncode,2,2) = '02'";
    var tCount = easyExecSql(strSQL);
    //没有死亡理赔类型
    if (tCount == "0")
    {
        var strSQL2 = "select a.phone,a.postaladdress from LCAddress a where "
                    + "a.customerno = '" + fm.customerNo.value + "'";
        var tLCAddress = easyExecSql(strSQL2);
        fm.RptorName.value = fm.customerName.value;
        fm.RptorPhone.value = tLCAddress[0][0];
        fm.RptorAddress.value = tLCAddress[0][1];
        fm.Relation.value = "GX01";
        showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
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
    //检索事件号、事故日期(一条)
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(strSQL1);
//    alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
    var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end) from llregister where "
                + "rgtno = '"+rptNo+"'";
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(strSQL2);
//    alert("RptContent= "+RptContent);
    //更新页面内容
if(AccNo!=null){
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
}
if(RptContent!=null){
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
    fm.NoRgtReason.value = RptContent[0][18];
    fm.RgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
}
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//不立案原因
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//受托人性别
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//受托人类型

    //---------------------------------------------------*
    //更新页面权限
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;

    fm.QueryPerson.disabled=true;
    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

    //设置按钮
    if (fm.RgtConclusion.value == "" || fm.RgtConclusion.value == null)
    {
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printPassRgt.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }

    //检索分案关联出险人信息(多条)
    var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from llcase where CaseNo = '"+ rptNo +"')";
    easyQueryVer3Modal(strSQL3, SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    //根据立案结论显示隐藏层
    showDIV();
    //根据立案结论判断是否查询匹配理算信息
    if (fm.RgtConclusion.value == '01')
  {
      afterMatchDutyPayQuery();
        var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";
        var tRgtState = easyExecSql(strSQL4);
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
  }
}

//查询业务员
function queryAgent()
{
    if(fm.AssigneeCode.value != "")
    {
        var strSQL = "select t.name,t.sex,t.phone,t.homeaddress,t.zipcode from laagent t where "
                    + "t.agentcode = '"+fm.AssigneeCode.value+"'";
        var tAgent = easyExecSql(strSQL);
        if (tAgent)
        {
            fm.AssigneeName.value = tAgent[0][0];
            fm.AssigneeSex.value = tAgent[0][1];
            fm.AssigneePhone.value = tAgent[0][2];
            fm.AssigneeAddr.value = tAgent[0][3];
            fm.AssigneeZip.value = tAgent[0][4];
            showOneCodeName('sex','AssigneeSex','AssigneeSexName');
        }
    }
}

//设置界面上的所有按钮为disabled
function disabledButton()
{
    var elementsNum = 0;//FORM中的元素数
    //遍历FORM中的所有ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
        if (fm.elements[elementsNum].type == "button")
        {
            fm.elements[elementsNum].disabled = true;
        }
    }
}

//设置界面上的所有按钮为not disabled
function notDisabledButton()
{
    var elementsNum = 0;//FORM中的元素数
    //遍历FORM中的所有ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
        if (fm.elements[elementsNum].type == "button")
        {
            fm.elements[elementsNum].disabled = false;
        }
    }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
    try
    {
        initForm();
        fm.AccNo.value = "";
         fm.RptNo.value = "";
        fm.RptorName.value = "";
        fm.RptorPhone.value = "";
        fm.RptorAddress.value = "";
        fm.Relation.value = "";
        fm.RelationName.value = "";
        fm.RptMode.value = "";
        fm.RptModeName.value = "";
        fm.AccidentSite.value = "";
        fm.occurReason.value = "";
        fm.RptDate.value = "";
         fm.MngCom.value = "";
         fm.Operator.value = "";
        fm.ClmState.value = "";

        fm.customerName.value = "";
        fm.customerAge.value = "";
         fm.customerSex.value = "";
         fm.SexName.value = "";
        fm.IsVip.value = "";
        fm.AccidentDate.value = "";
         fm.occurReason.value = "";
         fm.ReasonName.value = "";
        fm.hospital.value = "";
        fm.TreatAreaName.value = "";
        fm.AccidentDate2.value = "";
         fm.accidentDetail.value = "";
//         fm.IsDead.value = "";
         fm.claimType.value = "";
         fm.cureDesc.value = "";
         fm.Remark.value = "";

        //理赔类型置空
        for (var j = 0;j < fm.claimType.length; j++)
        {
            fm.claimType[j].checked = false;
        }
    }
    catch(re)
    {
        alert("在LLClaimRegister.js-->resetForm函数中发生异常:初始化界面错误!");
    }
}

//返回按钮
function goToBack()
{
    location.href="LLClaimRegisterMissInput.jsp";
}

//---------------------------------------------------------------------------------------*
//功能：理赔类型逻辑判断
//处理：1 死亡、高残、医疗三者只可选一
//      2 选择死亡或高残时，默认选中豁免
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
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[1].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "09" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//            {
//                alert("选取死亡、高残必须选择豁免!");
//                fm.claimType[4].checked = true;
//                return;
//            }
        case "00" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[5].checked = false;
//                return;
//            }
        default :
            return;
    }
}

//显示隐藏层
function showDIV()
{
  if (fm.RgtConclusion.value == '01')
  {
      //显示计算层
        spanConclusion1.style.display="";
        spanConclusion2.style.display="none";
        fm.dutySet.disabled = false;
        fm.medicalFeeCal.disabled = false;
        fm.printNoRgt.disabled = true;
        fm.printDelayRgt.disabled = true;
        fm.printPassRgt.disabled = false;
  }
  else if (fm.RgtConclusion.value == '02')
  {
      //显示不予立案原因层
      spanConclusion1.style.display="none";
      spanConclusion2.style.display="";
      fm.printNoRgt.disabled = false;
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = false;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = true;
  }
    else if (fm.RgtConclusion.value == '03')
    {
        spanConclusion1.style.display="none";
      spanConclusion2.style.display="none";
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = false;
    }
}

//事件查询
//2005-8-1 16:08 修改:去掉出险原因的查询条件来定位事件
function queryLLAccident()
{
  //非空检查
  if (checkInput1() == false)
    {
        return;
    }
//    if (fm.occurReason.value == "")
//    {
//        alert("出险原因为空！");
//        return;
//    }
    //查找事件号
    var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";
    var tAccNo = easyExecSql(strSQL);
//    alert(tAccNo);
    if (tAccNo == null)
    {
        alert("没有相关事件！");
        return;
    }
    //打开事件查询窗口
//  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
//  alert(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"相关事件");
}

//出险人查询
function showInsPerQuery()
{
    window.open("LLLdPersonQueryMain.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryMain.jsp");
}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
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

//打印单证签收清单,立案通过(01)时才能使用-----在线打印，不用判断
function prtPassRgt()
{
    //检查结论是否为立案通过,否则不能打印
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
    var tResult = easyExecSql(strSQL);
    if (tResult == null)
    {
        alert("请先保存立案结论!");
        return;
    }

    //以下填写打印提交内容
    fm.action = './LLPRTCertificateSignforSave.jsp';
    fm.target = "../f1print";
    fm.submit();
}

//打印不予立案通知书,不予立案(02)时才能使用
function prtNoRgt()
{
    //检查结论是否为不予立案,否则不能打印
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
    var tResult = easyExecSql(strSQL);
    if (tResult != '02')
    {
        alert("请先保存立案结论!");
        return;
    }
    //以下填写打印提交内容
    fm.action = './LLPRTProtestNoRegisterSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT007")=="false")
    {
      return;
    }
//    fm.target = "../f1print";
//    fm.submit();
}

//打印补充单证通知书,延迟立案(03)时才能使用
function prtDelayRgt()
{
    //检查结论是否为延迟立案,否则不能打印
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
    var tResult = easyExecSql(strSQL);
    if (tResult != '03')
    {
        alert("请先保存立案结论!");
        return;
    }
    //以下填写打印提交内容
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT003")=="false")
    {
      return;
    }

//    fm.target = "../f1print";
//    fm.submit();
}

//检查预估金额调整,只能大不能小
function checkAdjPay()
{
    var tStandM = parseFloat(fm.standpay.value);
    var tAdjM = parseFloat(fm.adjpay.value);
    if (tStandM > tAdjM)
    {
        alert("调整金额不能小于预估金额!");
        fm.adjpay.value = fm.standpay.value;
        return;
    }
}

//打印伤残鉴定通知书
function PrintMedCert()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }

    fm.action = './LLPRTAppraisalSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT001")=="false")
    {
      return;
    }
//    fm.target = "../f1print";
//    fm.submit();

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
    fm.submit();
}


/**********************************
//打印前检验（），需要传入（单证号码、赔案号）--------2005-08-22添加
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
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
      +" and t.otherno='"+tclmno+"' "
      +" and trim(t.code)='"+tcode+"' ";
  var arrLoprt = easyExecSql(strSql);
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
    fm.PrtSeq.value=arrLoprt[0][0];//单证流水号
    //存在但未打印过，直接进入打印页面
     if(tstateflag=="0")
     {
//      fm.action = './LLPRTCertificatePutOutSave.jsp';   //
      fm.target = "../f1print";
      fm.submit();
     }
    else
    {
      //存在但已经打印过，tstateflag[打印状态：1 -- 完成、2 -- 打印的单据已经回复、3 -- 表示已经发催办通知书]
      if(confirm("该单证已经打印完成，你确实要补打吗？"))
       {
         //进入补打原因录入页面
         var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
//         window.open(strUrl,"单证补打");
         window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
    }
  }
}