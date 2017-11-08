var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
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

/**=========================================================================
修改状态：开始
修改原因：打印单证
修 改 人：续涛
修改日期：2005.07.28
修改：2005.08.22，打印单证时在前台判断《是否存在或者需要补打》，查询流水号，传入后台，
=========================================================================
**/
function showPrtAffix()
{
	var row = SubReportGrid.getSelNo();	
	if(row < 1) {
	    alert("请选中一行记录！");
	    return;
	}
	var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
	fm.customerNo.value = CustomerNo;
	/*var tAffixSql = "select * from LLReportAffix where rptno='"+fm.RptNo.value
	+"' and customerno='"+CustomerNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql1");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(CustomerNo); 
	var arrAffix = easyExecSql(mySql.getString());
	if(!arrAffix){
		alert("没有需要打印的索赔资料！");
		return false;
	}
	fm.action = '../claim/LLPRTCertificatePutOutSave.jsp';  
	if(beforePrtCheck(fm.ClmNo.value, CustomerNo, "PCT002")==false)
	{
	  return;
	} 
	//  fm.target = "../f1print";
	//  document.getElementById("fm").submit();
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
}

/**=========================================================================
    修改状态：开始
    修改原因：判断帐户是否归属
    修 改 人：P.D
    修改日期：2006.07.12
   =========================================================================
**/
//进行保险责任匹配
function showMatchDutyPay()
{
    /*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";*/
   	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql2");
	mySql.addSubPara(fm.GrpContNo.value); 
	
    var tCount = easyExecSql(mySql.getString());
    if(tCount > 0){
       if(confirm("您确定已经做了归属?"))
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

      }else{
        alert("请到保全做归属!");
        return false;
      }
    }else{

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
    /*tSql = " select a.realpay,b.BeAdjSum "
         + " from LLClaim a,LLRegister b  where 1=1 "
         + " and a.ClmNo = b.RgtNo and a.ClmNo = '"+tClaimNo+"'"*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql3");
	mySql.addSubPara(tClaimNo); 
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.standpay.value  = arr[0][0];
        arr[0][1]==null||arr[0][1]=='null'?'0':fm.adjpay.value  = arr[0][1];
        if (fm.adjpay.value == "0")
        {
            fm.adjpay.value = fm.standpay.value;
        }
    }

    //查询整个赔案的金额
   /* tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql4");
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
        +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and c.code=a.GetDutyKind),"
        +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.SocPay,a.OthPay,RealPay "
        +" from LLClaimDutyKind a  where 1=1 "
        +" and a.ClmNo = '"+tClaimNo+"'"*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql5");
	mySql.addSubPara(tClaimNo); 
    //prompt("查询LLClaimDutyKind，按照赔案理赔类型进行查找",tSql);
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
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql6");
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
    ////to_char(b.PayEndDate,'yyyy-mm-dd')+a.GracePeriod," //交至日期 + 宽限期--+ a.GracePeriod
     initPolDutyCodeGrid();
/*
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
       +" and a.CustomerNo = '"+tCustNo+"'"
*/
    /*tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
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
       +" a.dutycode,a.CustomerNo,a.GrpContNo,a.ContNo "
       +" , (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"   
       //+" and a.GiveType not in ('1')"
       +" and a.CustomerNo = '"+tCustNo+"'"
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql7");
	mySql.addSubPara(tClaimNo); 
	mySql.addSubPara(tCustNo); 
    //prompt("查询保项金额",tSql);
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
    else
    {
        initPolDutyCodeGrid();
    }
  
      
    //查询LLClaimPolicy,查询保全项目信息      
/*     tSql = " select a.PolNo,(select RiskCode from LCPol where PolNo=a.PolNo),(select c.codename from ldcode c where c.codetype = 'edortypecode' and c.code=a.EdorType),"
     +" a.EdorValiDate,a.GetMoney "
     +" from LPEdorItem a where 1=1 and a.PolNo in(select PolNo from LLClaimPolicy where ClmNo = '"+tClaimNo+"') ";
    arr = easyExecSql( tSql );
  
    if (arr)
    {
        displayMultiline(arr,LPEdorItemGrid);
    }
    else
    {
        initLPEdorItemGrid();
    }
*/

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
    var tRiskCode = '';
    //设置按钮
    fm.addUpdate.disabled = false;//添加修改
    //得到mulline信息
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.PolNo.value = PolDutyCodeGrid.getRowColData(i,1);//保单号
        /*var psql="select polstate from lcpol where polno='"+fm.PolNo.value+"' ";
        var tpolstate=new Array;
        tpolstate=easyExecSql(psql); 
        fm.polstate.value = tpolstate;
        showOneCodeName('polstate','polstate','polstateName');    */
        
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,14);//赔付结论
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,23);//调整原因
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,24);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,25);//调整备注
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,16);//拒付原因代码
        fm.GiveReasonDesc.value = PolDutyCodeGrid.getRowColData(i,18);//拒付依据
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,19);//特殊备注
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveReason','GiveReasonName');
        tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//险种编码
    }
    //帐户型险种判断，是的不允许修改理算金额
    //var sql1 = " select insuaccflag From lmrisk where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql8");
	mySql.addSubPara(tRiskCode); 
    var tInsuaccFlag = easyExecSql(mySql.getString());
    //长险险种判断，是的不允许修改理算金额
   // var sql2 = " select riskperiod from lmriskapp where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql9");
	mySql.addSubPara(tRiskCode); 
    var tRiskPeriod = easyExecSql(mySql.getString());
    //显示隐藏层
    if(tInsuaccFlag != 'Y' && tRiskPeriod != 'L'){
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
    }else{
    divBaseUnit5.style.display= "none";
    }
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
    else if (fm.GiveType.value == '2'||fm.GiveType.value == '3')
    {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";        
    }
    
}

//对保项增加修改
function AddUpdate()
{
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("请先选择要处理的保项信息,再执行此操作！");
        return;
    }       
    
    checkAdjMoney();//检查保项金额调整

    
    var i = PolDutyCodeGrid.getSelNo() - 1;//得到焦点行
    //alert(385);
    PolDutyCodeGrid.setRowColData(i,14,fm.GiveType.value);//赔付结论
    PolDutyCodeGrid.setRowColData(i,16,fm.GiveReason.value);//拒付原因代码
    PolDutyCodeGrid.setRowColData(i,18,fm.GiveReasonDesc.value);//拒付依据
    PolDutyCodeGrid.setRowColData(i,19,fm.SpecialRemark.value);//特殊备注
    PolDutyCodeGrid.setRowColData(i,22,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReason.value);//调整原因
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,25,fm.AdjRemark.value);//调整备注
    if(fm.GiveType.value == 0){
    PolDutyCodeGrid.setRowColData(i,15,"给付");//赔付结论名称
    }else if(fm.GiveType.value == 1){
    PolDutyCodeGrid.setRowColData(i,15,"拒付");//赔付结论名称
    }else if(fm.GiveType.value == 2){
    PolDutyCodeGrid.setRowColData(i,15,"通融赔付");//赔付结论名称
    }else if(fm.GiveType.value == 3){
    PolDutyCodeGrid.setRowColData(i,15,"协议赔付");//赔付结论名称
    }
    //alert(403);
    fm.saveUpdate.disabled = false;//保存修改
}

//对保项修改保存到后台
function SaveUpdate()
{
   /*   
   if (fm.GiveType.value==2||fm.GiveType.value==3)
    {      
      var tsql="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('tOperator').value+"' ";
      var tappndmax=new Array;
      tappndmax=easyExecSql(tsql);
      
      if (tappndmax<document.all('RealPay').value) 
      {
      	 alert("您的通融、协议赔付权限不够！");
      	 return false;
      }     	
    }else if (fm.GiveType.value==1)
    {
      var tsql="select checklevel from llclaimuser where usercode='"+document.all('tOperator').value+"' ";	
      var tchecklevel=new Array;
      tchecklevel=easyExecSql(tsql);
      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
      {
        alert("您没有拒付权限！");	
        return false;
      }    
    }
    */
    //在复核时校验     
    
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
    fm.saveUpdate.disabled = true;//保存修改
}

//检查保项金额调整
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo()-1;
    if (i != '0')
    {

        //i = i - 1;
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,33));//个单合同号
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//险种编码
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,29));//责任编码
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,22));//调整金额
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,10));//保额
        var tGrpContNo= parseFloat(PolDutyCodeGrid.getRowColData(i,31));//团体保单号
        var tGetDutyKind = parseFloat(PolDutyCodeGrid.getRowColData(i,2));
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));
        var tAdjM     = parseFloat(fm.RealPay.value);
        var tContPlanCode = "0";
        var tDeadTopvalFlag = "";

       //判断津贴型不做限制
       /*var strSQL4 = " select deadtopvalueflag from lmdutygetclm where "
                   + " getdutycode='"+tGetDutyCode+"' and getdutykind='"+tGetDutyKind+"'"*/
       mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql10");
	mySql.addSubPara(tGetDutyCode); 
	mySql.addSubPara(tGetDutyKind); 
       var arr4= easyExecSql(mySql.getString());
          if(arr4 != null){
          tDeadTopvalFlag = arr4[0][0];
             }

        if(tRiskCode == '140' || tDeadTopvalFlag == 'N'){
          /*
         var strSQL3 = " select contplancode from lcinsured  where contno = '"+tContNo+"'"
         var arr3= easyExecSql(strSQL3);
            if(arr3 != null){
            tContPlanCode = arr3[0][0];
               }
         var strSQL = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '2' and trim(a.ContNo) = '"+tGrpContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr= easyExecSql(strSQL);
            if(arr != null){
            tAmnt = arr[0][0];
               }else{
         var strSQL2 = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '1' and trim(a.ContNo) = '"+tContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr2= easyExecSql(strSQL2);
             if(arr2 != null){
            tAmnt = arr2[0][0];
                 }else{
           alert("未查询到140险种的赔付限额！");
           fm.RealPay.value = 0;
           return;
                  }
               }
             */
             tAmnt=1000000;
            }
        if (tAmnt < tAdjM)
        {
            alert("调整金额不能大于保额!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,13);
            return;
        } else if(tAdjM < 0){
            alert("调整金额不能小于零!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,13);
            return;
        }

    }
    if (fm.GiveType.value=="")
    {
    	alert("请输入赔付结论!");
    	return;
    }else if (fm.GiveType.value=="0" || fm.GiveType.value=="2" || fm.GiveType.value=="3")
    {
       if (fm.RealPay.value=="")
       {
       	alert("请输入调整金额!");
       	return;
       }    
       if (fm.AdjReason.value=="")
       {
       	alert("请输入调整原因!");
       	return;
       }      	
    }else if (fm.GiveType.value=="1")
     {
        if (fm.GiveReason.value=="")
        {
        	alert("请输入拒付原因!");
        	return;
        }         	
     }else
     {
     		alert("请输入正确的赔付结论代码!");
        return;     		
      }
    if(fm.AdjReason.value == "00"||fm.AdjReason.value == "14"){
    	if(fm.AdjRemark.value == null || fm.AdjRemark.value =="null" || fm.AdjRemark.value == ""){
    		alert("调整原因为通融给付或协议给付时，必须录入调整备注!");
    		return false;
    	}
    }
    
}

//录入医疗单证信息
function showLLMedicalFeeInp()
{

    //var tSel = SubReportGrid.getSelNo();
    //var tClaimNo = SubReportGrid.getRowColData(tSel - 1,8);         //赔案号
    //var tCaseNo = SubReportGrid.getRowColData(tSel - 1,2);          //分案号
    //var tCustNo = SubReportGrid.getRowColData(tSel - 1,1);          //客户号
    //if( tSel == 0 || tSel == null ){
//        alert( "请先选择一条客户记录，再执行此操作!" );
//        return false;
//    }
//else{
//        window.open("LLMedicalFeeInpInput.jsp?clamNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&custName="+tCustName+"&custSex="+tCustSex,"医疗单证信息");
//    }

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
    var strUrl="../claim/LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.AccidentDate.value+"&otherAccDate="+fm.AccidentDate.value+"&GrpFlag=1";
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
//  var tCustomerNo = fm.customerNo.value;
//  if (tCustomerNo == "")
//  {
//      alert("请选择出险人！");
//      return;
//  }
//    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
	var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tRgtNo = fm.RptNo.value;
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tRgtNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//卡单信息查询
function showCard(){
    var strUrl="../card/CardContInput.jsp?flag=1";//flag = 1 卡单查询里的Button都不显示
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查看调查
function showQueryInq()
{
//  var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//    if(fm.RptNo.value == "" || fm.RptNo.value == null)
//    {
//        alert("先选择赔案！");
//        return;
//    }
    //---------------------------------------
    //判断该赔案是否存在调查
    //---------------------------------------
   /* var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
                mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql11");
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

//打开呈报查询
function showQuerySubmit()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//    if(fm.RptNo.value == "" || fm.RptNo.value == null)
//    {
//        alert("先选择赔案！");
//        return;
//    }
  //---------------------------------------
  //判断该赔案是否存在呈报
  //---------------------------------------
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql12");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
        alert("该赔案还没有呈报信息！");
        return;
    }
    var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
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
}

//报案查询
function showLLReportQuery()
{
    //window.open("LLReportQueryInput.jsp","");
    window.open("LLReportQueryInput.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
    fm.customerAge.value = arr[4];
    //fm.SocialInsuFlag.value = arr[4];
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
    fm.deletebutton.disabled=false;
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
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,5));
        fm.SocialInsuFlag.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql13");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
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
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;

                    //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        //fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2,HospitalName,(select codename from ldcode where codetype='accidentDetail' and code=AccidentDetail) from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
 	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql14");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tSubReport = easyExecSql(mySql.getString());
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
    fm.TreatAreaName.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];
//    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院

//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult2();
    afterMatchDutyPayQuery();
    var claimNum = new Array;
	/*var strSQL3 = "select  count(1) from  llregister where clmstate in ('50','60','70') and rgtno in "
					+"(select rgtno from llcase where 1=1 "
					+ getWherePart( 'CustomerNo','customerNo' )
					+")";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql15");
	mySql.addSubPara(fm.customerNo.value); 
	claimNum = easyExecSql(mySql.getString());
	if(tSubReport!=null){
		fm.claimNum.value = claimNum[0][0];
	}
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
    //------------------------------------------**End

    //--------------------------------------------Beg
  //显示隐藏区域
  //--------------------------------------------
  spanText1.style.display = "none";
  spanText2.style.display = "";
  spanText3.style.display = "";
  spanText4.style.display = "none";
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
        //alert("fm.customerName.value:"+fm.customerName.value)
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,5));
        fm.SocialInsuFlag.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql16");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    /*var strSQL1 = "select ReasonCode from LLReportReason where "
                + " RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql17");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tClaimType = easyExecSql(mySql.getString());
    if(tClaimType == null){
        alert("理赔类型为空，请检查此记录的有效性！");
        return;
        }
    }
    if(tClaimType!= '')
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
            for (var l=0;l<tClaimType.length;l++)
            {
                var tClaim = tClaimType[l].toString();
                tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;
                    //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        //fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
    //-------------------------1----------2-----------3---------4--------5-------6--------7-----------8-----------9------------10-------------------------------------------11
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,AffixConclusion,(case AffixConclusion when '0' then '否' when '1' then '是' end),standpay,HospitalName,(select codename from ldcode where codetype='accidentcode' and code=AccidentDetail) from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql18");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tSubReport = easyExecSql(mySql.getString());//prompt("",strSQL2);
if(tSubReport!=null){
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.AccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.IsAllReady.value = tSubReport[0][9];
    fm.IsAllReadyName.value = tSubReport[0][10];
    fm.standpay.value = tSubReport[0][11];
    fm.TreatAreaName.value = tSubReport[0][12];
    fm.accidentDetailName.value = tSubReport[0][13];
    //alert("fm.accidentDetailName.value:"+fm.accidentDetailName.value);
}
	var claimNum = new Array;
	/*var strSQL3 = "select  count(1) from  llregister where clmstate in ('50','60','70') and rgtno in "
					+"(select rgtno from llcase where 1=1 "
					+ getWherePart( 'CustomerNo','customerNo' )
					+")";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql19");
	mySql.addSubPara(fm.customerNo.value); 
	claimNum = easyExecSql(mySql.getString());
	if(tSubReport!=null){
		fm.claimNum.value = claimNum[0][0];
	}
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
   // showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
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
//根据选中的出险人显示相关的理算结果
    afterMatchDutyPayQuery();
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
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";

}

//提交前的校验、计算
function beforeSubmit()
{
	
	/////////////////////////////////////////////////////////////////////////
	// * 增加出险人的出现原因必须一致的校验  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	//var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql20");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tCustomerNo); 
	var occurReasonResult = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("出险人的出险原因必须保持一致！");
			return false;
		}
	}
    //判断团体客户号
    if(fm.GrpCustomerNo.value == ''){
     alert("团体客户号不能为空!");
     return false;
    }
    //判断团体保单号
    if(fm.GrpContNo.value == ''){
     alert("团体保单号不能为空!");
     return false;
    }
    //判断投保单位
    if(fm.GrpName.value=='')
    {
    	alert("请输入单位名称!");
    	return false;
    }
  //判断受理日期
    if (fm.AcceptedDate.value == "" || fm.AcceptedDate.value == null)
    {
        alert("请输入受理日期！");
        return false;
    }
    
    
    //获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccReason = fm.occurReason.value;//出险原因
    var AccDate = fm.AccidentDate.value;//事故日期
    var AccDate2 = fm.AccidentDate.value;//出险日期
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
/*
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("出险原因为疾病时，事故日期必须等于出险日期！");
        return false;
    }
*/
    //---------------------------------------------------------------------------------**End

    //5 检查理赔类型
    if (ClaimType == null || ClaimType == '')
    {
        alert("理赔类型不能为空！");
        return false;
    }
    //6 检查医院代码
    if (fm.hospital.value == '' && fm.occurReason.value == '2')
    {
        alert("出险原因为疾病,治疗医院代码不能为空！");
        return false;
    }
    //---------------------------------------------*Beg*2005-6-13 20:26
    //添加理赔类型中选中"医疗"必须选择医院的判断
    //---------------------------------------------*
//    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
//    {
//        alert("请选择医院！");
//        return false;
//    }
    //---------------------------------------------*End
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //添加理赔类型中选中豁免,必须选中死亡或高残之一的判断
    //---------------------------------------------------------
    //if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false)
    //{
        //alert("选中豁免,必须选中死亡或高残之一!");
        //return false;
    //}
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
//删除返回后调的方法
if(fm.fmtransact.value == "DELETE"){
    queryReport();
//    queryProposer();
}else{
    queryRegister();
}
      //设置可操作按钮
        if(fm.isNew.value == '0')
        {
            operateButton21.style.display="";
            operateButton23.style.display="";
            operateButton22.style.display="none";
            fm.QueryPerson.disabled = false;
            //fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
            operateButton21.style.display="";
            operateButton23.style.display="";
            operateButton22.style.display="";
            fm.QueryPerson.disabled = false;
            fm.addbutton.disabled = true;
            //fm.QueryReport.disabled = true;
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

//提交后操作,不返回
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

        afterMatchDutyPayQuery();
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
    
    //检查是否有扫描件 liuyu-20070827
   /* var ssql="select count(*) from es_doc_main where doccode='"+fm.RptNo.value+"' ";
    var scancount=easyExecSql(ssql);
    if (scancount==0)
    {
        alert("该赔案下没有扫描件信息，不能立案确认！");
        return;    	
    }*/ 
          
    //校验赔付金额与理算金额是否相等  liuyu-2008-2-29
    //var accSql="select nvl(sum(pay),0) from llbalance where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql21");
	mySql.addSubPara(fm.RptNo.value); 
	
	var accMoney=easyExecSql(mySql.getString());
    //var polSql="select nvl(sum(realpay),0) from llclaimpolicy where clmno='"+fm.RptNo.value+"' ";
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql22");
	mySql.addSubPara(fm.RptNo.value); 

    var polMoney=easyExecSql(mySql.getString());
     
    if (parseFloat(accMoney)!=parseFloat(polMoney))
    {
        alert("该赔案赔付金额与理算金额不等，不能立案确认！请重新理算后再试！");
        return;      	
    }           

    //-------------------------------------------------------------------BEG2005-8-9 8:58
    //检查立案结论、匹配计算、单证齐全
    //-------------------------------------------------------------------
   /* var sql1 = " select RgtConclusion from llregister where "
            + " RgtNo = '" + fm.RptNo.value + "'"*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql23");
	mySql.addSubPara(fm.RptNo.value); 
    var tRgtConclusion = easyExecSql(mySql.getString());
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
           /* var sql2 = "select count(1) from LLClaimDetail where"
                     + " ClmNo = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql24");
			mySql.addSubPara(fm.RptNo.value); 
            var tDutyKind = easyExecSql(mySql.getString());
            if (tDutyKind == 0)
            {
                alert("请先进行匹配并理算!");
                return;
            }
            
            //var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
           	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql25");
			mySql.addSubPara(fm.RptNo.value); 
            var tGiveType=easyExecSql(mySql.getString());
            
            if (tGiveType.length>1)
            {     
               alert('赔付结论不统一！请修改后再立案确认！');	
               return;
            }              
             
            //检查单证齐全标志
            //alert(fm.IsAllReady.value);
            if (fm.IsAllReady.value != '1')
            {
            	//alert(fm.IsAllReady.value);
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
                //var tResult = new Array;
                //var sql = " select affixconclusion from llcase where "
                //         + " caseno = '" + fm.RptNo.value + "'"
                //tResult = easyExecSql(sql);
                //if (tResult != null)
                //{
                //    for (var i=0; i < tResult.length; i++)
                //    {
                //        if (tResult[i] != "0")
                //        {
//                            if (confirm("单证不齐全,是否立案!"))
//                            {
//                                yesORno = 1;
//                            }
//                            else
//                            {
//                                yesORno = 0;
//                                return;
//                            }
                //            alert("单证不齐全,不能立案通过!");
                //            return;
                //        }
                //        else
                //        {
                //            yesORno = 1;
                //        }
                //    }
                // }
                
                if (fm.IsAllReady.value != "1")
                {
//                    if (confirm("单证不齐全,是否立案!"))
//                    {
//                        yesORno = 1;
//                    }
//                    else
//                    {
//                        yesORno = 0;
//                        return;
//                    }
                    alert("单证不齐全,不能立案通过!");
                    return;
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
/*
    if (tRgtConclusion == '01')
    {
        var tGivetype = new Array;
        var strSql = "select givetype from LLClaimDetail where "
                   + "clmno = '" + fm.RptNo.value + "'";
        tGivetype = easyExecSql(strSql);
        var tYes = 0;
        if (tGivetype != null)
        {
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
*/
    //-------------------------------------------------------------------END

    //是否提交
    //if (yesORno == 1)
    //{
        fm.RgtConclusion.value = tRgtConclusion;
        fm.action = './LLClaimRegisterConfirmSave.jsp';
        submitForm();
    //}
}

//[保存]按钮对应操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit())
    {
//        var row = SubReportGrid.getSelNo();
//        if(row < 1)
//        {
//            alert("请选中一行记录！");
//            return;
//        }
        
    	      //var tsql="select riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' ";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql26");
			mySql.addSubPara(fm.GrpContNo.value); 
            var tsub=new Array;
            tsub=easyExecSql(mySql.getString());
        
           if (tsub != null)
           {
             for (var i=0; i < tsub.length; i++)
             {
              //alert("tsub[i]:"+tsub[i]);        

          	  //var accSql="select risktype6 from lmriskapp where riskcode='"+tsub[i]+"' ";
	           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql27");
			mySql.addSubPara(tsub[i]);  
	            var tRiskType6 = easyExecSql(mySql.getString());	
	                       //prompt("",accSql);
             if(tRiskType6!="8")   //目前有307的团单下只有307一个险种,risktype6='8'代表307类险种
              {
              if(TempCustomer() == false && showDate() == false)
                {
                alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
                return false;
                }
              
              }
             }
           }
    	 
        /*update by wood，已在报案界面做了限制，这里不用再限制了。
          增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增
         *2006-03-06 P。D
         
         
        var StrSQL = "select count(*) from llcase a, llregister b where a.caseno = b.rgtno and b.grpcontno='"+fm.GrpContNo.value+"' and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70')";
        var arr= easyExecSql(StrSQL);
        if(arr > 0){
           alert("该出险人有未结案件，请结案后在做新增！");
           return false;
        }
        
        */
        /*对保单号和客户号进行校验
         *2007-02-13 L。Y 
                 
        var StrSQL = "select count(*) from lcgrpcont where appntno='"+fm.GrpCustomerNo.value+"' and grpcontno='"+fm.GrpContNo.value+"' ";
        var arr= easyExecSql(StrSQL);
        if(arr == 0){
           alert("该团体客户号与团体保单号不匹配，请修改后再操作！");
           return false;
        }
        */
        
        /*================================================================
         * 修改原因：增加对做完生存给付（趸领）的被保险人的判断不允许理赔
         * 修 改 人：P.D
         * 修改日期：2006-8-16
         *=================================================================
         */
        /*var SqlPol = " select count(*) From lcpol where polstate = '6'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql28");
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value);  
        if(fm.Polno.value != ''){
           // SqlPol += " and riskcode = '"+fm.Polno.value+"'";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql29");
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value);  
			mySql.addSubPara(fm.Polno.value);  
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
           alert("该出险人已经做了生存给付，不允许做新增！");
           return false;
         }
        //增加长险对保全的判断，保全未确认和退保的不允许理赔 2006-8-14
        //var SqlBq = "select  count(*) from LPEdorItem where insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql30");
			mySql.addSubPara(fm.customerNo.value); 
        var tEdorState = easyExecSql(mySql.getString());
       /* var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";*/
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql31");
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.customerNo.value);  
			
        var tcount = easyExecSql(mySql.getString());

        if(tEdorState > 0 && tcount > 0 ){
           alert("该出险人有未确认的保全或已经退保，请确认后在做新增！");
           return false;
        }
         
      /* var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
           strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
           strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";*/
        	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql32");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value);  
       if (fm.GrpCustomerNo.value!=null)
          {
            //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql33");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value);  
          }
       if (fm.GrpContNo.value!=null)
          {
           // strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql34");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value);  
          }
       var arrbq= easyExecSql(mySql.getString());
       if ( arrbq != null )
          {
           alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");         
          }
/*
        //分红判断
        var SQLbonusflag = "select count(*) from lmriskapp where  bonusflag = '1' ";
        if(fm.Polno.value != '')
        {
            SQLbonusflag += " and riskcode = '"+fm.Polno.value+"'";
        }else{
            SQLbonusflag += " and riskcode in (select polno From lcpol where grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"')";
        }
        var tBonusFlag = easyExecSql(SQLbonusflag);
        if (tBonusFlag > 0)//有需要分红的险种
        {//-----------------1

        var tAgetDate = fm.AccidentDate.value.substring(0,4);
        var SQLCount = "select count(*) from lobonuspol where agetdate like '"+tAgetDate+"%%' and  polno in "
                     + " (select polno From lcpol where grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"' )";
        var tCount = easyExecSql(SQLCount);
        if (tCount < 0)
        {
            alert("请先做分红处理再来理赔！");
            return false;
        }

        }
*/
        //判断区分新增、保存立案、还是保存出险人        
        //if (fm.isReportExist.value == "false")
        //{
            fm.fmtransact.value = "INSERT";
        //}
        //else
        //{
        //    fm.fmtransact.value = "insert||customer";
        //}
        //alert(fm.fmtransact.value);
        fm.action = './LLClaimRegisterSave.jsp';
        submitForm();

    }
}

//[删除]按钮对应操作

function deleteClick()
{
  var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
     fm.fmtransact.value = "DELETE";
     fm.action = './LLClaimRegisterSave.jsp';
     submitForm();
}

//[保存]按钮对应操作
function saveConclusionClick()
{
    //首先进行非空字段检验
    if(fm.RgtConclusion.value == ''){
        alert("请填写立案结论！");
        return false;
    }

        //查询是否进行预估金额录入
    /*var sql2 = "select count(1) from LLStandPayInfo where"
                 + " CaseNo = '" + fm.RptNo.value + "'";
    var tDutyKind = easyExecSql(sql2);
        if (tDutyKind == 0)
        {
            alert("请先进行预估金额录入!");
            return;
        }*/

      //查询索赔资料是否回销
    var tResult = new Array;
    /*var sql = " select affixconclusion from llcase where "
            + " caseno = '" + fm.RptNo.value + "'"*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql35");
			mySql.addSubPara(fm.RptNo.value); 
			
   tResult = easyExecSql(mySql.getString());//prompt("",sql);
   if (tResult != null)
     {
      for (var i=0; i < tResult.length; i++)
         {
          if (tResult[i] != '1')
             {
              alert("索赔资料不齐全,不能结论保存!");
              return;
              }
         }
     }

    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == '' || fm.NoRgtReason.value == null))
    {
        alert("请填写不予立案原因!");
        return;
    }
    
    if (fm.RgtConclusion.value == '03' && (fm.DeferRgtReason.value == '' || fm.DeferRgtReason.value == null))
    {
        alert("请填写延迟立案原因!");
        return;
    }    
/*
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
*/
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave.jsp?DeferRgtRemark='+fm.DeferRgtRemark.value+'&RgtFlag=1';
    submitForm();
}

//[保存]按钮对应操作
//jixf 20051212
function saveConclusionClick1()
{
    //首先进行非空字段检验
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == '' || fm.NoRgtReason.value == null))
    {
        alert("请填写不予立案原因!");
        return;
    }
    if (fm.RgtConclusion.value == '01')
    {
        //查询是否进行过匹配计算
       /* var sql2 = "select count(1) from LLClaimDetail where"
                 + " ClmNo = '" + fm.RptNo.value + "'";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql36");
			mySql.addSubPara(fm.RptNo.value); 
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind == 0)
        {
            alert("请先进行匹配并理算!");
            return;
        }
    }
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave1.jsp';
    submitForm();
}






//出险人信息修改
function updateClick()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    if (confirm("您确实想修改该记录吗？"))
    {
        
        if (beforeSubmit())
        {
            //var tsql="select riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' ";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql37");
			mySql.addSubPara(fm.GrpContNo.value); 
            var tsub=new Array;
            tsub=easyExecSql(mySql.getString());
        
           if (tsub != null)
           {
             for (var i=0; i < tsub.length; i++)
             {
              //alert("tsub[i]:"+tsub[i]);        

          	  //var accSql="select risktype6 from lmriskapp where riskcode='"+fm.Polno.value+"' ";
	           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql38");
			mySql.addSubPara(fm.GrpContNo.value); 
	            var tRiskType6 = easyExecSql(mySql.getString());	
	                       
             if(tRiskType6!="8")   //目前有307的团单下只有307一个险种,risktype6='8'代表307类险种
              {
              if(TempCustomer() == false && showDate() == false)
                {
                alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
                return false;
                }
              
              }
             }
           }
           /* //jixf add 20051213
            var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
            strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
             if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
             {
               strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
             }
            if (fm.GrpContNo.value!=null)
            {
              strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
            }
            
             var arr= easyExecSql(strSQL);
             if ( arr == null )
             {
               alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
               return false;
             } */
             
            /*var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql39");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
             if (fm.GrpCustomerNo.value!=null)
             {
               //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
               mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql40");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value); 
             }
            if (fm.GrpContNo.value!=null)
            {
              //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
              mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql41");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");         
             }

                      
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
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
                mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql42");
			mySql.addSubPara(rptNo); 
			
    var AccNo = easyExecSql(mySql.getString());
    //检索报案号及其他报案信息(一条)
    /*var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass,GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode from LLReport where "
                + "RptNo = '"+rptNo+"'";*/
                mySql = new SqlClass();
     mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql43");
			mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
//    prompt("",strSQL2);
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

    fm.GrpContNo.value = RptContent[0][12];
    fm.GrpCustomerNo.value = RptContent[0][13];
    fm.Peoples.value = RptContent[0][14];
    fm.GrpName.value = RptContent[0][15];
    fm.Polno.value = RptContent[0][16];
}
    showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院

    //---------------------------------------------------*
    //更新页面权限
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;
    fm.QueryPerson.disabled=false;
    //fm.QueryReport.disabled=true;
    fm.claimType.disabled=true;

    //检索分案关联出险人信息(多条)
   /* var strSQL4 = "select count(*) from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
                mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql44");
			mySql.addSubPara(rptNo); 
    var tCount = easyExecSql(mySql.getString());
    if( tCount > '0' )
    {
	   /* var strSQL3 = "select CustomerNo,Name,Sex,(case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,Birthday,"
	    			+ " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
	    			+ "(case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志"
	    			+" from ldperson where "
	                + "CustomerNo in("
	                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
	                mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql45");
			mySql.addSubPara(rptNo); 
	    //prompt("strSQL3",strSQL3);
	    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
	    
	    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
	    {
	        allSubReportGridClick(0);
	    }
  }

}

//申请人查询,当案件为非死亡类时申请人为出险人
function queryProposer()
{
    /*var strSQL = "select count(1) from LLReportReason a where "
                + "a.rpno = '" + fm.RptNo.value + "'"
                + "and substr(a.reasoncode,2,2) = '02'";*/
     mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql46");
	mySql.addSubPara(fm.RptNo.value ); 
    var tCount = easyExecSql(mySql.getString());
    //没有死亡理赔类型
    if (tCount == "0")
    {
       /* var strSQL2 = "select a.phone,a.postaladdress from LCAddress a where "
                    + "a.customerno = '" + fm.customerNo.value + "'";*/
        mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql47");
	mySql.addSubPara(fm.customerNo.value ); 
        var tLCAddress = easyExecSql(mySql.getString());
        //fm.RptorName.value = fm.customerName.value;
     if(tLCAddress!=null){
//        fm.RptorPhone.value = tLCAddress[0][0];
//        fm.RptorAddress.value = tLCAddress[0][1];
//        fm.Relation.value = "GX01";
       }
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
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql48");
	mySql.addSubPara(rptNo );             
    var AccNo = easyExecSql(mySql.getString());
    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21-----------------------------------------------------22--------23------24------25------26--------27----------28------
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode,clmstate,DeferRgtReason,AcceptedDate from llregister where "
                + "rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql49");
	mySql.addSubPara(rptNo );  
    var RptContent = new Array();
    RptContent = easyExecSql(mySql.getString());
//    prompt("321",strSQL2);
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
    fm.GrpContNo.value = RptContent[0][21];
    fm.GrpCustomerNo.value = RptContent[0][22];
    fm.Peoples.value = RptContent[0][23];
    fm.GrpName.value = RptContent[0][24];
    fm.Polno.value = RptContent[0][25];
    fm.clmState.value = RptContent[0][26];
    fm.DeferRgtReason.value=RptContent[0][27];   
    fm.AcceptedDate.value = RptContent[0][28];
}
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//不立案原因
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//受托人性别
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//受托人类型

//取到 团体预估金额 
    getGrpstandpay();

    //---------------------------------------------------*
    //更新页面权限
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;
    fm.QueryPerson.disabled=false;
    //fm.QueryReport.disabled=true;
    fm.claimType.disabled=true;

    //设置按钮
    if (fm.RgtConclusion.value == "" || fm.RgtConclusion.value == null)
    {
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        //fm.printPassRgt.disabled = true;
        fm.printNoRgt.disabled = true;
        //fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = true;
    }
    else
    {
        fm.MedicalFeeInp.disabled = false;
    }

    //检索分案关联出险人信息(多条)   
   /* var strSQL6 = "select count(*) from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql50");
	mySql.addSubPara(rptNo ); 
    var tCount = easyExecSql(mySql.getString());    
  if( tCount > '0' ){  	
   /* var strSQL3 = "select CustomerNo,Name,Sex,(case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,Birthday,"
    			+ "(nvl(SocialInsuFlag,0)) as SocialInsuFlag,"
    			+ "(case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志  from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from llcase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql51");
	mySql.addSubPara(rptNo ); 
    //prompt("strSQL3",strSQL3);

    turnPage2.queryModal(mySql.getString(),SubReportGrid);

    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
  }  
    
    /*var strSQL5 = "select reasoncode from Llappclaimreason where "
                + "RgtNo = '"+rptNo+"'";*/
   	mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql52");
	mySql.addSubPara(rptNo ); 
    var ReasonCode = easyExecSql(mySql.getString());
    
    if ( ReasonCode!=null&&ReasonCode!="")
    {      
      fm.occurReason.value=	ReasonCode[0][0].substring(0,1);      
      showCodeName('occurReason','occurReason','ReasonName');
    }    
    
    //根据立案结论显示隐藏层
    showDIV();
    //根据立案结论判断是否查询匹配理算信息
    if (fm.RgtConclusion.value == '01')
  {
      afterMatchDutyPayQuery();
        /*var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";*/
        mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql53");
	mySql.addSubPara(rptNo ); 
        var tRgtState = easyExecSql(mySql.getString());
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
  }
}

//查询团体预估金额
function getGrpstandpay(){
   // var strSQL3 = "select count(StandPay),sum(StandPay) from LLStandPayInfo  where caseno = '"+fm.RptNo.value+"'";
   mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql54");
	mySql.addSubPara(fm.RptNo.value); 
    var Grpstandpay = easyExecSql(mySql.getString());
    if(Grpstandpay[0][0] > 0){
    fm.Grpstandpay.value = Grpstandpay[0][1];
    }
  }

//查询业务员
function queryAgent()
{
    if(fm.AssigneeCode.value != "")
    {
       /* var strSQL = "select t.name,t.sex,t.phone,t.homeaddress,t.zipcode from laagent t where "
                    + "t.agentcode = '"+fm.AssigneeCode.value+"'";*/
        mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql55");
	mySql.addSubPara(fm.AssigneeCode.value); 
        var tAgent = easyExecSql(mySql.getString());
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
        fm.SocialInsuFlag.value = "";
        fm.AccidentDate.value = "";
        fm.occurReason.value = "";
        fm.ReasonName.value = "";
        fm.hospital.value = "";
        fm.TreatAreaName.value = "";
        fm.AccidentDate2.value = "";
        fm.accidentDetail.value = "";
//        fm.IsDead.value = "";
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
    location.href="LLGrpClaimRegisterMissInput.jsp";
    if(fm.isClaimState.value == '1')
    {
     location.href="LLStateQueryInput.jsp";	
    }
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
        spanConclusion3.style.display="none";
        spanConclusion4.style.display="none";
        fm.dutySet.disabled = false;
        fm.medicalFeeCal.disabled = false;
        fm.printNoRgt.disabled = true;
        fm.printDelayRgt.disabled = false;
        fm.printPassRgt.disabled = false;
        fm.MedicalFeeInp.disabled = false;
  }
  else if (fm.RgtConclusion.value == '02')
  {
      //显示不予立案原因层
        spanConclusion1.style.display="none";
        spanConclusion2.style.display="";
        spanConclusion3.style.display="none";
        spanConclusion4.style.display="none";
        fm.printNoRgt.disabled = false;
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = false;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = true;
  }
    else if (fm.RgtConclusion.value == '03')
    {
        spanConclusion1.style.display="none";
        spanConclusion2.style.display="none";
        spanConclusion3.style.display="";
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = false;
        fm.MedicalFeeInp.disabled = true;
        
        //var dSql="select DeferRgtRemark from llregister where rgtno='"+fm.ClmNo.value+"' "; 
        mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql56");
		mySql.addSubPara(fm.ClmNo.value); 
        var tDeferRgtRemark=easyExecSql(mySql.getString());
        
        if (tDeferRgtRemark!=null&&tDeferRgtRemark!="")
        {    	
        	spanConclusion4.style.display="";   //显示延迟立案后的延迟立案备注
        	fm.DeferRgtRemark.value=tDeferRgtRemark;
        }          
    }
    //var aSql="select auditidea from llclaimuwmain where clmno='"+fm.ClmNo.value+"' "; 
   mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql57");
		mySql.addSubPara(fm.ClmNo.value); 
    var tauditidea=easyExecSql(mySql.getString());
    
    if (tauditidea!=null&&tauditidea!="")
    {    	
    	spanLLClaimAudit.style.display=""; //显示回退后的审核意见
    	fm.AuditIdea.value=tauditidea;
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
   /* var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";*/
   mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql58");
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.customerNo.value);  
    var tAccNo = easyExecSql(mySql.getString());
    if (tAccNo == null)
    {
        alert("没有相关事件！");
        return;
    }
    //打开事件查询窗口
  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//出险人查询
function showInsPerQuery()
{
    var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+fm.GrpContNo.value+"&GrpCustomerNo="+fm.GrpCustomerNo.value+"&GrpName="+fm.GrpName.value+"&ManageCom="+document.allManageCom.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//查询出险结果2
function queryResult2()
{
    /*var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
    mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql59");
		mySql.addSubPara(fm.AccResult2.value);
		
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

//打印单证签收清单,立案通过(01)时才能使用-----在线打印，不用判断
function prtPassRgt()
{
    //检查结论是否为立案通过,否则不能打印
    /*var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql60");
		mySql.addSubPara(fm.RptNo.value);
    var tResult = easyExecSql(mySql.getString());
    if (tResult == null)
    {
        alert("请先保存立案结论!");
        return;
    }
    if(fm.IsAllReady.value!="1"){
    	alert("单证不齐全，无法打印！");
    	return;
    }

    //以下填写打印提交内容
    fm.action = './LLPRTCertificateSignforSave.jsp';
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}

//打印不予立案通知书,不予立案(02)时才能使用
function prtNoRgt()
{
    //检查结论是否为不予立案,否则不能打印
   /* var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql60");
		mySql.addSubPara(fm.RptNo.value);           
    var tResult = easyExecSql(mySql.getString());
    if (tResult != '02')
    {
        alert("请先保存立案结论!");
        return;
    }
    //以下填写打印提交内容
    fm.action = './LLPRTProtestNoRegisterSave.jsp';
    
    if(beforePrtCheck(fm.ClmNo.value,"","PCT007")==false)
    {
      return;
    }
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();
}

//打印补充单证通知书,延迟立案(03)时才能使用
function prtDelayRgt()
{
    //检查结论是否为延迟立案,否则不能打印
//    var strSQL = "select rgtconclusion from llregister where 1=1 "
//               + " and rgtno = '" + fm.RptNo.value + "'";
//    var tResult = easyExecSql(strSQL);
//    if (tResult != '03')
//    {
//        alert("请先保存立案结论!");
//        return;
//    }
    
	var row = SubReportGrid.getSelNo();	
	if(row < 1) {
	    alert("请选中一行记录！");
	    return;
	}
	
	var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
	fm.customerNo.value = CustomerNo;
	
    //以下填写打印提交内容
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,CustomerNo,"PCT003")==false)
    {
      return;
    }

    fm.target = "../f1print";
    document.getElementById("fm").submit();
    
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
    
	var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
	fm.customerNo.value = CustomerNo;

    fm.action = './LLPRTAppraisalSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,CustomerNo,"PCT001")==false)
    {
      return;
    }
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();

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
//打印前检验（），需要传入（单证号码、赔案号）--------2005-08-22添加
***********************************/
function beforePrtCheck(clmno,CustomerNo,code)
{
  //查询单证流水号，对应其它号码（赔案号）、单据类型、打印方式、打印状态、补打标志
     var tclmno=trim(clmno);
     var tCustomerNo =trim(CustomerNo);
     var tcode =trim(code);
     if(tclmno=="" ||tcode=="")
     {
       alert("传入的赔案号或单证类型（号码）为空");
       return false;
     }
     
     var strSql="";
     
     if(tCustomerNo==null||tCustomerNo=="")
     {
    	 /*strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
    	        +" and t.otherno='" + tclmno + "'"
    	        +" and trim(t.code)='" + tcode + "'";*/
   		mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql61");
		mySql.addSubPara(tclmno);  
		mySql.addSubPara(tcode);  
     }
     else
     {
       	/* strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
 	        +" and t.otherno='" + tclmno + "'"
 	        +" and trim(t.code)='" + tcode + "'"
 	        +" and t.standbyflag4='" + tCustomerNo + "'";*/
 	     mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql62");
		mySql.addSubPara(tclmno);  
		mySql.addSubPara(tcode);  
		mySql.addSubPara(tCustomerNo);  
	 
     }
    
    
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
    fm.PrtSeq.value=arrLoprt[0][0];//单证流水号
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
//         window.open(strUrl,"单证补打");
         window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
         return false;
       }
      else
      {
    	  return false;
      }
    }
  }
}

//判断查询输入窗口的案件类型是否是回车，
//如果是回车调用查询客户函数

function QueryOnKeyDown(at)
{
   var keycode = event.keyCode;
   //回车的ascii码是13
   if(keycode=="13"||keycode=="9")
   {
     if(at == '1'){
      ClientQuery1();
      }else if(at == '2'){
      ClientQuery2();
      }else if(at == '3'){
      ClientQuery3();
      }else if(at == '4'){
      ClientQuery4();
      }
   }
}
//出险细节信息查询
function ClientQuery1()
{

   if ( fm.accidentDetailName.value == "")
   {
      alert("请输入查询条件");
      return false;
   }
   //var strSQL = " select ICDCode, ICDName from LDDisease where icdlevel <= 1 and ASCII(icdcode) >= 86 and ICDName like '%%"+fm.accidentDetailName.value+"%%' order by ICDCode";
   mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql63");
		mySql.addSubPara(fm.accidentDetailName.value);  
		
   var arr= easyExecSql(mySql.getString());
   if ( arr == null )
   {
     alert("没有符合条件的数据！");
     fm.accidentDetail.value = "";
     fm.accidentDetailName.value = "";
     return false;
   }
      try
      {
       //如果查询出的数据是一条，显示在页面，如果是多条数据填
       //出一个页面，显示在mulline中
       if(arr.length==1)
       {
     fm.accidentDetail.value = arr[0][0];
     fm.accidentDetailName.value = arr[0][1];
       }else{
        var varSrc = "&accidentDetailName=" + fm.accidentDetailName.value;
        showInfo = window.open("./LLMainAccidentDetailQuery.jsp?Interface= LLAccidentDetailQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
       }

      }catch(ex)
      {
            alert("错误:"+ ex.message);
      }
//showOneCodeName('idtype','tIDType','tIDTypeName');
}

function afterLLRegister(arr)
{
     fm.accidentDetail.value = arr[0][0];
     fm.accidentDetailName.value = arr[0][1];
}

//医院信息查询
function ClientQuery2()
{

   if ( fm.TreatAreaName.value == "")
   {
      alert("请输入查询条件");
      return false;
   }
  // var strSQL = " select HospitalCode,HospitalName from LLCommendHospital where HospitalName like '%%"+ fm.TreatAreaName.value +"%%'";
  mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql64");
		mySql.addSubPara(fm.TreatAreaName.value);  
   var arr= easyExecSql(mySql.getString());
   if ( arr == null )
   {
     alert("没有符合条件的数据！");
     fm.hospital.value = "";
     fm.TreatAreaName.value = "";
     return false;
   }
      try
      {
       //如果查询出的数据是一条，显示在页面，如果是多条数据填
       //出一个页面，显示在mulline中
       if(arr.length==1)
       {
     fm.hospital.value = arr[0][0];
     fm.TreatAreaName.value = arr[0][1];
       }else{
        var varSrc = "&HospitalName=" + fm.TreatAreaName.value;
        showInfo = window.open("./LLMainHospitalQuery.jsp?Interface= LLHospitalQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
       }

      }catch(ex)
      {
            alert("错误:"+ ex.message);
      }
//showOneCodeName('idtype','tIDType','tIDTypeName');
}

function afterLLRegister2(arr)
{
     fm.hospital.value = arr[0][0];
     fm.TreatAreaName.value = arr[0][1];
}

//团体信息查询
function ClientQuery3()
{
   if(document.all('GrpCustomerNo').value!=null && document.all('GrpCustomerNo').value!='' ||
      (document.all('GrpContNo').value!=null && document.all('GrpContNo').value!='') ||
      (document.all('GrpName').value!=null && document.all('GrpName').value!='') )
   {
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a " +
      " where a.grpcontno = g.grpcontno and g.appflag in ('1','4') "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql65");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(document.all('AllManageCom').value);  
      if(document.all('GrpName').value!=''){
         //strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql66");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(document.all('AllManageCom').value); 
		mySql.addSubPara(document.all('GrpName').value); 
      }
      //增加对机构的判断 登陆机构只能处理本机构的单子 总机构可以处理分机构的单子 2006-02-18 P.D
     // strSQL += " and g.managecom like '"+document.all('AllManageCom').value+"%%'";
      arrResult=easyExecSql(mySql.getString());
       if(arrResult != null)//-----------------1
       {
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             
             //jixf del 20060614 对于卡单数据这个字段都是0，做卡单理赔有可能做不了
             //if(document.all('Peoples').value == null ||
             //   document.all('Peoples').value == "0")
             //{
             //   alert("投保人数为空！");
             //   return;
             //}
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

      }
       }//------------------1
      else{//---------------1
      //取到处理机构代码 2006-02-18 P.D
      //var SQLEx = "select ExecuteCom From lcgeneral where grpcontno='"+document.all('GrpContNo').value+"'";
      mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql67");
		mySql.addSubPara(document.all('GrpContNo').value);  
		
      var tExecuteCom = new Array();
      var tExecuteCom = easyExecSql(mySql.getString());
      if(tExecuteCom != null){//--------------2
      for(var i = 0;i<tExecuteCom.length;i++){//-------------2.1
      //判断登陆机构是否是保单指定机构
      if(tExecuteCom[i][0] == document.all('AllManageCom').value){//-------2.2
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a ,lcgeneral b " +
      " where a.grpcontno = g.grpcontno and g.grpcontno = b.grpcontno and g.appflag in ('1','4') "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
      	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql68");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(tExecuteCom[i][0]);  
      if(document.all('GrpName').value!=''){
         //strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql69");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(tExecuteCom[i][0]);  
		mySql.addSubPara(document.all('GrpName').value);  
      }
        // strSQL += " and b.ExecuteCom = '"+tExecuteCom[i][0]+"'";
      var arrResult = new Array();
      arrResult = easyExecSql(mySql.getString());
   if(arrResult != null){
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("投保人数为空！");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
       } else {
         alert("没有符合条件的数据！");
         document.all('GrpCustomerNo').value = "";
         document.all('GrpName').value = "";
         document.all('GrpContNo').value = "";
         document.all('Peoples').value = "";
         return;
       }
     }//-------2.2
    }//-------------2.1
   }//------------------2
       else
       {//----------------3
         //**********yaory 查询卡单如果没有结果，在报错 卡单理赔要求输入保单号
        /* var tsql="select AppntName from LZNoNameCard where grpcontno='"+document.all('GrpContNo').value+"'"
                 +" and managecom like '"+document.all('AllManageCom').value+"%%'";*/
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql70");
		mySql.addSubPara(document.all('GrpContNo').value);  
		mySql.addSubPara(document.all('AllManageCom').value);  
		
         arrResult=easyExecSql(mySql.getString());
         if(arrResult==null)
         {
         alert("没有符合条件的数据！");
         document.all('GrpCustomerNo').value = "";
         document.all('GrpName').value = "";
         document.all('GrpContNo').value = "";
         document.all('Peoples').value = "";
         return;
        }else{
        try{document.all('GrpCustomerNo').value="000000"}catch(ex){alert(ex.message+"GrpCustomerNo")}
        try{document.all('GrpName').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpName")}
        //查询人数
      //  tsql="select count(*) from lcinsured where grpcontno='"+document.all('GrpContNo').value+"'";
          mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql71");
		mySql.addSubPara(document.all('GrpContNo').value);  
		 
         arrResult=easyExecSql(mySql.getString());
         if(arrResult!=null)
         {
           try{document.all('Peoples').value=arrResult[0][0]}catch(ex){alert(ex.message+"Peoples")}
         }
      }
    }//----------------3
  }//------------------1
   }
   else
   {
      alert("请输入查询条件");
   }
}

//出险细节信息查询
function ClientQuery4()
{

   if ( fm.AccResult2Name.value == "")
   {
      alert("请输入查询条件");
      return false;
   }
   var strSQL;
   if (document.all('occurReason').value==null||document.all('occurReason').value=="")
   {
      alert("请选择出险原因！");      
      return false;	
   }
   if (document.all('occurReason').value=='1')
   {
      //	strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 1 and ASCII(icdcode) < 86 and ASCII(icdcode) > 82 and ICDName like '%%"+fm.AccResult2Name.value+"%%' order by ICDCode";
   		 mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql72");
		mySql.addSubPara(fm.AccResult2Name.value);  
   }else 
   {
      //	strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 1 and ASCII(icdcode) < 83 and ICDName like '%%"+fm.AccResult2Name.value+"%%' order by ICDCode";
   		 mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql73");
		mySql.addSubPara(fm.AccResult2Name.value);  
   }
   var arr= easyExecSql(mySql.getString());
   if ( arr == null )
   {
     alert("没有符合条件的数据！");
     fm.AccResult2.value = "";
     fm.AccResult2Name.value = "";
     return false;
   }
      try
      {
       //如果查询出的数据是一条，显示在页面，如果是多条数据填
       //出一个页面，显示在mulline中
       if(arr.length==1)
       {
         fm.AccResult2.value = arr[0][0];
         fm.AccResult2Name.value = arr[0][1];
       }else{
        var tAccResult2Name = fm.AccResult2Name.value;
        var toccurReason = fm.occurReason.value;
       
        //var varSrc = "&AccResultName="+tAccResult2Name+"&occurReason="+toccurReason;
        //showInfo = window.open("./LLMainAccidentDetailQuery.jsp?Interface= LLAccidentDetailQuery.jsp"+varSrc,"LLAccidentDetailQuery",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');

        //location.href="LLAccidentDetailQuery.jsp?AccResult2Name="+AccResult2Name+"&occurReason="+occurReason;
        
        var strUrl="LLAccidentDetailQuery.jsp?AccResultName="+tAccResult2Name+"&occurReason="+toccurReason;
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

       }

      }catch(ex)
      {
            alert("错误:"+ ex.message);
      }
//showOneCodeName('idtype','tIDType','tIDTypeName');
}
function afterLLRegister4(arr)
{
     fm.AccResult2.value = arr[0][0];
     fm.AccResult2Name.value = arr[0][1];
}

function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}


//打开发起调查
function showBeginInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    //var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000005125','0000005145','0000005165','0000005175') and missionprop1='"+fm.RptNo.value+"'";
    	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql74");
		mySql.addSubPara(fm.RptNo.value);  
    var JustStateCount=easyExecSql(mySql.getString());
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("该案件已经发起调查，请不要重复调查!");
    		return;
    }    
    var strUrl="../claim/LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&custVip="+fm.IsVip.value+"&initPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//预估金额信息录入
function operStandPayInfo()
{
   var tSel = SubReportGrid.getSelNo();
/*
   if( tSel == 0 || tSel == null ){
        alert( "请在出险人信息中选择一条记录" );
        return false;
     }
else {
     customerNo = SubReportGrid.getRowColData(tSel-1 ,1);
    }
*/
     var varSrc ="";
     var CaseNo = fm.RptNo.value;//
     var customerName = fm.customerName.value;
     var customerNo=fm.customerNo.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo+"&customerName="+customerName+"&customerNo="+customerNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//无名转有名保险期间判断
function TempCustomer(){
//        var StrSQL = "select grpcontno From lctempcustomer where startdate <= '"+fm.AccidentDate.value+"' and paytodate >= '"+fm.AccidentDate.value+"' and grpcontno = '"+fm.GrpContNo.value+"'";
//        var arr= easyExecSql(StrSQL);prompt("",StrSQL);
//        if(arr != null ){
        return true;
//        }else{
//        return false;
//        }
}
//判断保险期间
function showDate(){
        //jixf add 20051213
       // var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
       // strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
        mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql75");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);  
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
        // strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql76");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);  
		mySql.addSubPara(fm.GrpCustomerNo.value); 
		 
         }
        if (fm.GrpContNo.value!=null)
        {
        //strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
        mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql77");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);
		mySql.addSubPara(fm.GrpCustomerNo.value); 
		mySql.addSubPara(fm.GrpContNo.value);   
        }
        
         var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}

//导出该赔案下所有出险人既往赔付信息
function getLastCaseInfo(){
var CaseNo = fm.RptNo.value;
var GrpContNo = fm.GrpContNo.value
if(CaseNo == "" || CaseNo == null){
	alert("请先点保存！");
	return false;
}else{
	var row = SubReportGrid.getSelNo();
    if(row < 1) {
        alert("请选中一行记录！");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//alert("bb");
//  var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
// + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
// + "a.realpay from llclaimpolicy a,llcase b "
// +"where  a.clmno=b.caseno and a.insuredno=b.customerno  and b.rgtstate='60' and "
// +" a.insuredno in (select distinct customerno from llsubreport where subrptno='"
//+ CaseNo + "' ) and a.grpcontno = '" + GrpContNo + "'";
	/*var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
		 + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
		 + "a.realpay from llclaimpolicy a,llcase b "
		 +"where  a.clmno=b.caseno and a.insuredno=b.customerno "
		 //+" and b.rgtstate='60'"
		 +" and "
		 +" a.insuredno = '"+ tCustomerNo +"'"
	     +" and a.clmno <>'"+CaseNo+"'";
		 //+" and a.grpcontno = '" + GrpContNo + "'";*/
	//prompt("",tSQL);
	 mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql78");
		mySql.addSubPara(tCustomerNo);  
		mySql.addSubPara(CaseNo);  
	 
		  var arr = easyExecSql(mySql.getString());
		  if(!arr){
			  alert("未查询到既往赔案信息，无法导出");
			  return false;
		  }
fm.tSQL.value = tSQL;
  var Title="既往赔案信息查询";
	var SheetName="既往赔案信息查询";       
	var ColName = "赔案号@团体保单号@客户号@客户姓名@理赔类型@出险日期@结案日期@险种代码@应赔付金额@实际赔付金额";
	fm.action = "./PubCreateExcelSave.jsp?tSQl="+tSQL+"&Title=既往赔案信息查询"+"&SheetName="+Title+"&ColName="+ColName;
	fm.target="../claimgrp";		 	
	document.getElementById("fm").submit();
}   
}

function saveRgtMAffixListAll()
{
  
 
        /*strSQL="select affixno,affixcode,affixname,subflag,(case needflag when '0' then '0-是' when '1' then '1-否'  end ),readycount,shortcount,property from llaffix where 1=1 and"
               + " rgtno='"+fm.ClmNo.value+"' and"
     
             + " (subflag is null or subflag='1')";  */
      mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql79");
		mySql.addSubPara(fm.ClmNo.value);  
		  
      arr=easyExecSql(mySql.getString());
        if(arr!=null)
        {
          if (confirm("您确实想回销所有索赔资料？"))
           {
             fm.Operate.value="Rgt||UPDATE";
             fm.action="LLRgtMAffixListSaveAll.jsp";
             document.getElementById("fm").submit();
           }  
        }else{
      alert("没有要回销的索赔资料!");
      }
  
}

function afterSubmit4( FlagStr, content )
{
    //showInfo.close();
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

        returnParent();    //[返回]按钮   
    }
}

function LLQueryUWMDetailClick()
{
//  alert("案件核赔履历查询");
    var strUrl="LLColQueryClaimUWMDetailMain.jsp?ClmNo="+fm.ClmNo.value;
//    window.open(strUrl,"案件核赔履历查询");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//理赔责任控制
function ctrlclaimduty()
{
    var strUrl="../appgrp/CtrlClaimDuty.jsp?ProposalGrpContNo="+fm.GrpContNo.value+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag=2&LPFlag=1";
    //  window.open(strUrl,"案件核赔履历查询");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

    //showInfo = window.open();
    //parent.fraInterface.window.location = "../appgrp/CtrlClaimDuty.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;
}

function QueryRgtState(){
	var tmissionid=fm.MissionID.value;
	//var tRgtSql="select missionprop15 from lwmission where activityid='0000009015' and missionid='"+tmissionid+"'";
	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql80");
		mySql.addSubPara(tmissionid); 
	var tRgtState = easyExecSql(mySql.getString());
	fm.RgtState.value = tRgtState;
}

//出险细节查询
function showAccDetail(tCode,tName)
{//alert(3157);
	var strUrl="../claim/LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//医院模糊查询
function showHospital(tCode,tName)
{
	var strUrl="../claim/LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//zy 2009-07-28 17:43 获取康福产品保单的险种信息
function getLLEdorTypeCA()
{

	 //var tAccRiskCodeSql="select distinct riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' and riskcode='211901' and grpname like 'MS人寿保险股份有限公司%'";
	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql81");
		mySql.addSubPara(fm.GrpContNo.value); 
	 //prompt("",tAccRiskCodeSql);
   var tAccRiskCode=easyExecSql(mySql.getString());
   if(tAccRiskCode=="211901")
   {
   	fm.AccRiskCode.value=tAccRiskCode;
   }
   else
   {
   	fm.AccRiskCode.value="";   
   }

}

//zy 2009-07-28 14:58 账户资金调整
function ctrllcinsureacc()
{
	
    var strUrl="./LLGrpClaimEdorTypeCAMain.jsp?GrpContNo="+fm.GrpContNo.value+"&RptNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

function checkCurrency()
{
	if(fm.Currency.value!=null && fm.Currency.value!="")
    	{
	    	fm.RealPay.moneytype=fm.Currency.value;
    	}
}
