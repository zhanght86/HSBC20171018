var showInfo;
var turnPage = new turnPageClass();
var handle;//延时句柄
var mySql = new SqlClass();

//单证回销
function showRgtMAffixList()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }	
    var tClmNo = fm.ClmNo.value;
    var tcustomerNo = fm.customerNo.value;
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
    //window.open(strUrl,"单证回销");
}

//立案单证补充
function showRgtAddMAffixList()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
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

	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	 
	// var tMissionID = fm.MissionID.value;
    // var tSubMissionID = fm.SubMissionID.value;
	// var strSQL=" select a.missionprop11 from lwmission a where a.processid='0000000005' "
    //           +" and a.activityid='0000005015' "
    //           +" and a.missionid='" + tMissionID + "' "
    //           +" and a.submissionid='" + tSubMissionID + "' ";
    var tClmNo=fm.ClmNo.value;   
    //alert("ClmNo"+tClmNo); 
    /* 
    var strSQL=" select a.FeeInputFlag from llregister a "
              +" where a.rgtno='"+ tClmNo +"' ";
    */          
     mySql = new SqlClass();
     mySql.setResourceName("claim.LLClaimRegisterInputSql");
	 mySql.setSqlId("LLClaimRegisterSql1");
	 mySql.addSubPara(tClmNo);
    //////prompt("进行保险责任匹配",strSQL);
    var arr = easyExecSql(mySql.getString() );
    if(arr[0][0]==1)
    {
    	alert("医疗单证外包录入还未完成,你不能进行匹配理算操作!");
    } 
    else
    {
        //alert("开始匹配并理算");
        mOperate="MATCH||INSERT";
        fm.hideOperate.value=mOperate;
        fm.action = "./LLClaimRegisterMatchCalSave.jsp";
        fm.target="fraSubmit";
        var  showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var  urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//        showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
        var name='提示';   //网页名称，可为空;  //wyc
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=250;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        document.getElementById("fm").submit(); //提交
        
    }
    
}

//点击保存立案结论时的外围处理函数,根据处理结论是立案通过还是其他做不同的处理
function firstSaveConclusionClick()
{
	//如有未回销的补充材料则不允许进行结论保存
	/*
	 var strSqlaff = " select 1 from llaffix where (SubFlag is null or SubFlag='1')"
         + " and rgtno = '" + fm.RptNo.value + "'";
         */
     mySql = new SqlClass();
     mySql.setResourceName("claim.LLClaimRegisterInputSql");
	 mySql.setSqlId("LLClaimRegisterSql2");
	 mySql.addSubPara(fm.RptNo.value);
	 
     var tExistAff = easyExecSql(mySql.getString());

     if (tExistAff != null && tExistAff != "")
     {
    	 alert("存在未回销的补充材料，不能进行结论保存！");
    	 return;
     }
 
	
    //必须先选择立案结论
    if (fm.RgtConclusion.value == null || fm.RgtConclusion.value == "")
    {
        alert("请选择立案结论!");
        return;
    }
    
    //必须先选择案件类型
    if (fm.rgtType.value == null || fm.rgtType.value == "")
    {
        alert("请选择案件类型!");
        return;
    }
    
	
	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	

    //首先进行非空字段检验
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == null || fm.NoRgtReason.value == ""))
    {
        alert("请填写不予立案原因!");
        return;
    }
    
    //必须点击案件标识
    if (fm.rgtType.value == null || fm.rgtType.value == "")
    {
        alert("请选择案件标识!");
        return;
    }
    
    //如果是立案通过则需先匹配并理算,否则直接执行结论保存处理逻辑
    if(fm.RgtConclusion.value == '01')
    {
    	showMatchDutyPay2();
    }
    else
    {
    	saveConclusionClick();
    }

}

/**=========================================================================
修改状态：开始
修改原因：在点击立案保存时调用的函数
修 改 人：zhangzheng 
修改日期：2008.12.13
=========================================================================
**/
//进行保险责任匹配
function showMatchDutyPay2()
{  

	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	 
	// var tMissionID = fm.MissionID.value;
	// var tSubMissionID = fm.SubMissionID.value;
	// var strSQL=" select a.missionprop11 from lwmission a where a.processid='0000000005' "
	//           +" and a.activityid='0000005015' "
	//           +" and a.missionid='" + tMissionID + "' "
	//           +" and a.submissionid='" + tSubMissionID + "' ";
	var tClmNo=fm.ClmNo.value;   
	//alert("ClmNo"+tClmNo); 
	/*
	var strSQL=" select a.FeeInputFlag from llregister a "
	          +" where a.rgtno='"+ tClmNo +"' ";
	          */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql3");
	mySql.addSubPara(tClmNo);
	//////prompt("进行保险责任匹配",strSQL);
	var arr = easyExecSql( mySql.getString() );
	if(arr[0][0]==1)
	{
		alert("医疗单证外包录入还未完成,你不能进行匹配理算操作!");
	} 
	else
	{
	    //alert("开始匹配并理算");
	    mOperate="MATCH||INSERT";
	    fm.hideOperate.value=mOperate;
	    fm.action = "./LLClaimRegisterMatchCalSave2.jsp";
	    fm.target="fraSubmit";
	    document.getElementById("fm").submit(); //提交
	    
	}	

}


//点击匹配并理算按钮后的动作
function afterMatchDutyPay2(FlagStr, content)
{
	//理算出错就提示,不再执行结论保存
    if (FlagStr == "FAIL" ||FlagStr == "Call")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        mOperate = '';
    }
    else if (FlagStr == "SUCC2")
    {
    	//理算成功且没有错误提示则无需显示
        afterMatchDutyPayQuery();
        saveConclusionClick();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        afterMatchDutyPayQuery();
        saveConclusionClick();
    }


}

//点击匹配并理算按钮后的动作
function afterMatchDutyPay(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    /*
    tSql = " select a.realpay,b.BeAdjSum "
         + " from LLClaim a,LLRegister b  where 1=1 "
         + " and a.ClmNo = b.RgtNo and a.ClmNo = '"+tClaimNo+"'"
         */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql4");
	mySql.addSubPara(tClaimNo);

    //prompt("查询整个赔案的金额更新调整金额:",tSql);
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.standpay.value  = arr[0][0];
        arr[0][1]==null||arr[0][1]=='null'?'0':fm.adjpay.value  = arr[0][1];
//        fm.adjpay.value = fm.standpay.value;
        if (fm.adjpay.value < fm.standpay.value)
        {
            fm.adjpay.value = fm.standpay.value;
        }
    }

    //查询整个赔案的金额
    /*
    tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'";
     */  
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql5");
	mySql.addSubPara(tClaimNo);
    //prompt("查询整个赔案的金额更新赔案sql:",tSql);
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
    /*
    tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyKind)),"
       +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
     */  
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql6");
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
    /*
    tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"
     */  
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql7");
	mySql.addSubPara(tClaimNo);

    arr = easyExecSql( mySql.getString());
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
       */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql8");
	mySql.addSubPara(tClaimNo); 
    //prompt("查询保项给付金额sql:",tSql);
    arr = easyExecSql( mySql.getString());
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
	  if(!KillTwoWindows(fm.RptNo.value,'20'))
	  {
	  	  return false;
	  }
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
    var strUrl="LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.MedicalAccidentDate.value+"&otherAccDate="+fm.OtherAccidentDate.value;
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
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//查看调查
function showQueryInq()
{
//	var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
  //Modifyed by niuzj,2005-12-23
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
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
	  /*
    var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";
      */          
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql9");
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

//打开呈报查询
function showQuerySubmit()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }	
  //Modifyed by niuzj,2005-12-23
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
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
	/*
    var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";
     */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql10");
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
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
     //Modifyed by niuzj,2005-12-23
	   var tCustomerNo = fm.customerNo.value;
	   if (tCustomerNo == "")
	   {
	      alert("请选择出险人！");
	      return;
	   }
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
    
    //2008-11-25 去掉VIP标志的显示
//    fm.IsVip.value = arr[4];
    //if(arr[4]==null || arr[4]=="" || arr[4]==0)
    //{
     	//fm.VIPValueName.value ="否";
     	//fm.IsVip.value = "0";
    //}
    //else
    //{
     	//fm.IsVip.value = arr[4];
     	//fm.VIPValueName.value ="是";
    //}
    showOneCodeName('sex','customerSex','SexName');//性别
    //初始化录入域
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.OtherAccidentDate.value = "";
    fm.MedicalAccidentDate.value = "";
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
function afterQueryLL2(tArr)
{
	//得到返回值
    fm.AccNo.value = tArr[0];//事件号
    fm.AccDesc.value = tArr[2];//事故描述
    fm.AccidentSite.value = tArr[3];//事故地点
    
    fm.occurReason.value = tArr[4];//出险原因编码
    fm.ReasonName.value = tArr[5];//出险原因名称
    
    fm.accidentDetail.value = tArr[6];//意外细节编码
    fm.accidentDetailName.value = tArr[7];//意外细节名称
    
    fm.AccResult1.value = tArr[8];//出险结果1编码
    fm.AccResult1Name.value = tArr[9];//出险结果1名称
    
    fm.AccResult2.value = tArr[10];//出险结果2编码
    fm.AccResult2Name.value = tArr[11];//出险结果2名称
    
    fm.Remark.value = tArr[12];//备注信息
    

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
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.OtherAccidentDate.value = "";
	fm.MedicalAccidentDate.value = "";
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
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    /*
    var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";
                */
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql11");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    ////prompt("SubReportGridClick:立案-查询理赔类型:",strSQL1);
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
                	  
                	  //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
//                	  if (tClaim == '01')
//                	  {
//                	      fm.MedCertForPrt.disabled = false;
//                	  }
            	  }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
    /*
    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2,medaccdate ,(select  codename from LDcode where  codetype='accidentcode'  and code=AccidentDetail) from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
     */           
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql12");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    ////prompt("SubReportGridClick:立案-查询分案表信息",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.MedicalAccidentDate.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
//    queryHospital('hospital','TreatAreaName');
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
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.OtherAccidentDate.value = "";
	fm.MedicalAccidentDate.value = "";
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

    //------------------------------------------**End

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
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    /*
    var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql13");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    ////prompt("查询立案分案信息-获得理赔类型*****",strSQL1);
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
                	  
                	  //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
//                	  if (tClaim == '01')
//                	  {
//                	      fm.MedCertForPrt.disabled = false;
//                	  }
            	  }
            }
        }
    }

    
    //查询分案表信息
    var tSubReport = new Array;
    //-------------------------1----------2-----------3---------4--------5-------6--------7-----------8-----------9------------10-------------------------------------------11
   /*
    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,AffixConclusion,(case AffixConclusion when '0' then '否' when '1' then '是' end),medaccdate,(select  codename from LDcode where  codetype='accidentcode'  and code=AccidentDetail)  from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql14");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    //prompt("查询立案分案信息-查询分案表信息:",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.IsAllReady.value = tSubReport[0][9];
    fm.IsAllReadyName.value = tSubReport[0][10];
    fm.MedicalAccidentDate.value = tSubReport[0][11];
    fm.accidentDetailName.value = tSubReport[0][12];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
//    queryHospital('hospital','TreatAreaName');
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//单证齐全标志
}

//选中SubReportGrid响应事件,tPhase=1为选择行时调用
function allSubReportGridClick(tPhase)
{
    if(fm.isRegisterExist.value == "true")
    {
        //alert("SubReportGridClick2");
        SubReportGridClick2(tPhase);
    }
    else
    {
        //alert("SubReportGridClick");
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

//校验邮编位数
function checkziplength(zipIdNo)
{
	if (zipIdNo.length!=6)
	 {
	   alert("申请人邮编位数错误");
	 }
}

//校验邮编位数
function checkRptorMolength(moIdNo)
{
	if (moIdNo.length!=11)
	 {
	   alert("申请人手机号码位数错误");
	 }
}

//提交前的校验、计算
function beforeSubmit()
{
	//获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccReason = fm.occurReason.value;//出险原因
    var AccDesc = fm.accidentDetail.value;//出险细节
    
    
    //----------------------------------------------------------BEG2005-7-12 16:45
    //增加申请人姓名和关系的非空校验
    //----------------------------------------------------------
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("请输入申请人姓名！");
        return false;
    }
    //增加申请人电话,地址校验 Add by zhaorx 2006-03-16
    if (fm.RptorPhone.value == "" || fm.RptorPhone.value == null)
    {
        alert("请输入申请人电话！");
        return false;
    }    
    if (fm.RptorAddress.value == "" || fm.RptorAddress.value == null)
    {
        alert("请输入申请人详细地址！");
        return false;
    }    
    if (fm.AppntZipCode.value == "" || fm.AppntZipCode.value == null)
    {
        alert("请输入申请人邮编！");
        return false;
    }  
    if (fm.Relation.value == "" || fm.Relation.value == null)
    {
        alert("请输入申请人与事故人关系！");
        return false;
    }
    if (fm.AcceptedDate.value == "" || fm.AcceptedDate.value == null)
    {
        alert("请输入交接日期！");
        return false;
    }
   
     
    //var strSQL = "select rgtdate from llregister where rgtno = '" + fm.RptNo.value + "'";
//    mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimRegisterInputSql");
//	mySql.setSqlId("LLClaimRegisterSql15");
//	mySql.addSubPara(fm.RptNo.value); 
//
//    //查询立案日期
//    var tRptdate = easyExecSql(mySql.getString());
//
//    if (tRptdate != null && tRptdate != "")
//    {
//    	if (dateDiff(tRptdate[0][0],fm.AcceptedDate.value,'D') > 0)
//		{
//			alert("交接日期需小于或等于立案号产生日期!");
//			return false; 
//		}  
//    }
//    else
//    {
//    	if (dateDiff(mCurrentDate,fm.AcceptedDate.value,'D') > 0)
//    		{
//    			alert("立案日期还未生成，交接日期需小于或等于当前日期!");
//    			return false; 
//    		} 
//    }
    if (fm.ApplyDate.value == "" || fm.ApplyDate.value == null)
    {
        alert("请输入客户申请日期！");
        return false;
    }
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("邮编错误！");
        return false;
    }
    //----------------------------------------------------------end
    
    //1 检查出险人、信息
    if (checkInput1() == false)
    {
    	  return false;
    }
    
    var claimType=0;//选择理赔类型数量

    
    //理赔类型
    for(var j=0;j<fm.claimType.length;j++)
    {   	     	  
    	  if(fm.claimType[j].checked == true)
    	  {
    		  claimType++;
          }
    }
    
    //alert("claimType:"+claimType);
    
    //5 检查理赔类型
    if (claimType==0)
    {
        alert("理赔类型不能为空！");
        return false;
    }
    
    
    //当存在医疗理赔类型时需要校验医疗出险日期
    if(fm.claimType[5].checked == true)
    {
		//校验医疗出险日期和事故日期
		if (checkDate(fm.AccidentDate.value,fm.MedicalAccidentDate.value,"医疗出险日期") == false)
		{
		     return false;
		}
    }
    
    //当存在多于一种理赔类型或存在非医疗的理赔类型时校验其他出险日期
    if((claimType>1)||((fm.claimType[5].checked == false)&&claimType==1))
    {
        //校验其他出险日期和事故日期
		if (checkDate(fm.AccidentDate.value,fm.OtherAccidentDate.value,"其他出险日期") == false)
		{
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
    //Modify by zhaorx 2006-07-04
//    if (fm.occurReason.value == '1' && (fm.AccResult1.value ==""  || fm.AccResult1.value == null || fm.AccResult2.value == "" || fm.AccResult2.value == null))
//    {
//        alert("出险原因为意外时，出险结果1，出险结果2都不能为空！");
//        return false;
//    }    
    //-----------------------------------------------------------**Beg*2005-7-12 16:27
    //添加出险原因为疾病时，且理赔类型为医疗时，事故日期等于医疗出险日期
    //-----------------------------------------------------------**
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.MedicalAccidentDate.value)&& fm.claimType[5].checked == true)
    {
        alert("出险原因为疾病时，事故日期必须等于医疗出险日期！");
        return false;
    }
    //Modify by zhaorx 2006-07-04
//    if(fm.occurReason.value == "2" && (fm.AccResult1.value =="" || fm.AccResult1.value==null))
//    {
//        alert("出险原因为疾病时，出险结果1不能为空！");
//        return false;
//    }

    //-----------------------------------------------------------**Beg*2007-02-05 15:02
    //添加出险结果1,出险结果2为必录项
    //-----------------------------------------------------------**
  
    if(fm.AccResult1.value ==""  || fm.AccResult1.value == null)
    {
        alert("出险结果1不能为空！");
        return false;
    }   
    if(fm.AccResult2.value == "" || fm.AccResult2.value == null)
    {
        alert("出险结果2不能为空！");
        return false;
    }  
    //-----------------------------------------------------------**End

    //5 检查理赔类型
    if (claimType==0)
    {
        alert("理赔类型不能为空！");
        return false;
    }
    
    //----------------------------------------------------------*Beg*2005-6-13 20:26
    //添加理赔类型中选中"医疗"必须选择医院的判断
    //----------------------------------------------------------*
    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
    {
        alert("请选择医院！");
        return false;
    }
    //---------------------------------------------------------*End
    
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //添加理赔类型中选中豁免,必须选中死亡或高残或重大疾病之一
    //---------------------------------------------------------
    if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false && fm.claimType[2].checked == false)
    {
        alert("选中豁免,必须选中死亡或高残或重大疾病之一！");
        return false;
    }
    //---------------------------------------------------------End

    var tAccDate="";//比较日期,取小的那个日期作为比较日期
    
    //当都不为空时,取日期小的那个日期作为比较日期
    if((!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value==""))&&(!(fm.OtherAccidentDate.value==null||fm.OtherAccidentDate.value.value=="")))
    {
    	
    	//取两个日期中较小的那个
        if (dateDiff(fm.OtherAccidentDate.value,fm.MedicalAccidentDate.value,'D') > 0)
        {
        	tAccDate=fm.OtherAccidentDate.value;
        }
    	else
    	{
    		tAccDate=fm.MedicalAccidentDate.value;
    	}
    }
    else if(!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value=="")){
    	
    	tAccDate=fm.MedicalAccidentDate.value;
    }
    else
    {
    	tAccDate=fm.OtherAccidentDate.value;
    }

    
    //alert("tAccDate:"+tAccDate);

    //---------------------------------------------------------Beg*2005-12-28 16:22
    //出险原因为意外,只要存在一个出险日期在事故日期180天后，即提示
    //---------------------------------------------------------
    if(fm.occurReason.value == '1')
    {
    	if (dateDiff(fm.AccidentDate.value,tAccDate,'D') > 180)
        {
            alert("出险日期在事故日期180天后!");
        }
    }

    
    //---------------------------------------------------------Beg*2005-6-27 13:59
    //添加未在出险后十日内报案的判断,医疗出险日期和其他出险日期哪个早就用哪个跟事故日期比
    //---------------------------------------------------------
    if (dateDiff(fm.AccidentDate.value,mCurrentDate,'D') > 10)
    {
        alert("未在出险后十日内报案!"); 
    }
    
    
    //---------------------------------------------------------Beg*2005-11-30 11:01
    //按张领导的邮件,对于死亡案件这样处理：
    //1.死亡日期后的出险日期或事故日期不能报案处理；
    //2.死亡日期当天或之前的出险日期或事故日期在报案时，需要给出提示。
    //---------------------------------------------------------
    //var strSQL = "select deathdate from LDPerson where CustomerNo = '" + fm.customerNo.value + "'";
    mySql = new SqlClass();
mySql.setResourceName("claim.LLClaimRegisterInputSql");
mySql.setSqlId("LLClaimRegisterSql53");
mySql.addSubPara(fm.customerNo.value );
    ////prompt("查询客户是否身故sql",strSQL);
    var tDeathDate = easyExecSql(mySql.getString());
    if (tDeathDate != null && tDeathDate != "")
    {
        if (dateDiff(tAccDate,tDeathDate[0][0],'D') < 0)
        {
            alert("客户"+fm.customerName.value+"已被确认于"+tDeathDate+"身故，此日期以后的赔案不予受理！");
            return;
        }
        else
        {
            if (!confirm("客户"+fm.customerName.value+"已被确认于"+tDeathDate+"身故，是否继续进行？"))
            {
                return;
            }
        }
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //alert("fm.fmtransact.value = "+fm.fmtransact.value);
        
        operateButton21.style.display="none";
        operateButton22.style.display="";      
        

        document.all('MedicalAccidentDate').disabled=false;
        document.all('OtherAccidentDate').disabled=false;
        
        if(fm.fmtransact.value=="update")
        {
           //alert("queryRegister");
           queryRegister();
        }
        else
        {
           //alert("queryNewRegister");
           queryNewRegister();
        }

        //设置可操作按钮
        if(fm.isNew.value == '0')
        {
            fm.QueryPerson.disabled = false;
            fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
            
            fm.QueryPerson.disabled = true;
            fm.QueryReport.disabled = true;
        }
        

        
 
    }
    tSaveFlag ="0";
    
}


//保存立案结论执行的操作
function afterSubmit3( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.isReportExist.value = "true";
        fm.isRegisterExist.value = "true";
        
        operateButton21.style.display="none";
        operateButton22.style.display="";
        
        document.all('MedicalAccidentDate').disabled=false;
        document.all('OtherAccidentDate').disabled=false;
      
        notDisabledButton();
        fm.RgtConclusionName.value = "";
        queryConclusionSaveRegister();
        //设置可操作按钮
        if(fm.isNew.value == '0')
        {
            fm.QueryPerson.disabled = false;
            fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	
	
    var yesORno = 0;
    if (fm.RptNo.value == "")
    {
   	    alert("赔案号为空！");
   	    return;
    }
    //-------------------------------------------------------------------BEG2005-8-9 8:58
    //检查立案结论、匹配计算、单证齐全
    //-------------------------------------------------------------------
    /*
    var sql1 = " select RgtConclusion from llregister where "
            + " RgtNo = '" + fm.RptNo.value + "'"
            */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql16");
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
        	//2008-12-16 zhangzheng
        	//取消了在立案阶段的匹配并理算,而是在结论保存时自动在后台匹配并理算,所以这里要防止理算不出金额无法进行立案确认的问题，封住必须这个校验

            //var sql2 = "select count(1) from LLClaimDetail where"
             //        + " ClmNo = '" + fm.RptNo.value + "'";
            //var tDutyKind = easyExecSql(sql2);
            //if (tDutyKind == 0)
            //{
                //alert("请先进行匹配并理算!");
                //return;
            //}
            
            //2008-12-16 只有提交了需要客户提交的单证资料才进行必须回销才能通过的校验
            /*
            var sql2 = " select * from LLAffix where "
                + " RgtNo = '" + fm.RptNo.value + "'"
                */
            mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimRegisterInputSql");
			mySql.setSqlId("LLClaimRegisterSql17");
			mySql.addSubPara(fm.RptNo.value); 
            //prompt("查询是否需要客户提交的单证资料sql",sql2);
            var tResult2 = new Array;
            tResult2 = easyExecSql(mySql.getString());
            //alert(tResult2);
            if (tResult2 != null)
            {
                
                //检查单证齐全标志
                if (fm.IsAllReady.value != "0")
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
                else
                {
                    var tResult = new Array;
                    /*
                    var sql = " select affixconclusion from llcase where "
                             + " caseno = '" + fm.RptNo.value + "'"
                    */         
                    mySql = new SqlClass();
					mySql.setResourceName("claim.LLClaimRegisterInputSql");
					mySql.setSqlId("LLClaimRegisterSql18");
					mySql.addSubPara(fm.RptNo.value); 
                    tResult = easyExecSql(mySql.getString());
                    if (tResult != null)
                    {
                        for (var i=0; i < tResult.length; i++)
                        {
                            if (tResult[0][i] == "1")
                            {
//                                if (confirm("单证不齐全,是否立案!"))
//                                {
//                                    yesORno = 1;
//                                }
//                                else
//                                {
//                                    yesORno = 0;
//                                    return;
//                                }
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
        /*
        var strSql = "select givetype from LLClaimDetail where "
                   + "clmno = '" + fm.RptNo.value + "'";
                   */
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql19");
		mySql.addSubPara(fm.RptNo.value); 
        tGivetype = easyExecSql(mySql.getString());
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
        else
        {
        	//当理算不出金额时则不校验保项的给付问题,自动通过
        	 tYes = 1;
        }
        if (tYes == 0)
        {
            alert("保项全部为不给时不能立案通过!");
            return;
        }
    }
    
    //2008-12-13 zhangzheng 不予立案时必须要打印不予立案通知书
    if (tRgtConclusion == '02')
    {
 	    if(checkPrtNotice(fm.ClmNo.value,"PCT007")==false)
 	    {
 	    	alert("尚未打印不予立案通知书，不能进行立案确认!");
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


/**********************************
//立案确认前必须打印不予立案通知书
***********************************/
function checkPrtNotice(tclmno,tcode){
	
	if(tclmno=="" ||tcode=="")
   	{
   		alert("传入的赔案号或单证类型（号码）为空");
   		return false;
   	}
   	/*
    var strSql="select t.stateflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";
			*/
	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql20");
	mySql.addSubPara(tclmno); 
	mySql.addSubPara(tcode); 
    //prompt("判断单证是否为未打印sql",strSql);
	var arrLoprt = easyExecSql(mySql.getString());
	if(arrLoprt==null)
	{
		alert("没有找到该单证的流水号");
		return false;
	}	
	else 
	{
		var tstateflag=arrLoprt[0][0];//打印状态
		if(tstateflag!="1")
		{
			return false;
		}

	}
	
	return true;
}

//[保存]按钮对应操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit())
    {
    	var CustomerNo = fm.customerNo.value;
    	
    	
    	//查询是否存在死亡报案
    	//var tRptSql = "select * from llsubreport,llreportreason"
    				//+ " where llsubreport.subrptno=llreportreason.rpno and"
    				//+ " llsubreport.customerno=llreportreason.customerno and"
    				//+ " llsubreport.customerno='"+CustomerNo+"' and reasoncode in('102','202')" 
    			    //+ " and subrptno!='"+fm.RptNo.value+"'";
    	/*
    	var tRptSql = "select * from llcase,LLAppClaimReason"
    	+	" where LLCase.caseno=LLAppClaimReason.CaseNo and"
		+ " LLCase.customerno=LLAppClaimReason.CustomerNo and"
		+ " LLCase.customerno='"+CustomerNo+"' and reasoncode in('102','202')";
		*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql21");
		mySql.addSubPara(CustomerNo); 
		
    	
    	//prompt("查询是否存在死亡立案",tRptSql);
    	
    	var arrRpt = easyExecSql(mySql.getString());
    	if(arrRpt){
    		alert("该客户已存在死亡报案或立案，请注意");
    		//return false;
    	}
    	
        if (fm.RgtClass.value == "" || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "1";
        }
 
        if (fm.isReportExist.value == "true")
        {
            fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        
        
        fm.action = './LLClaimRegisterSave.jsp';

        submitForm();
    }
}

//[保存]按钮对应操作
function saveConclusionClick()
{


    //必须先选择立案结论
    if (fm.RgtConclusion.value == null || fm.RgtConclusion.value == "")
    {
        alert("请选择立案结论!");
        return;
    }
    
	
	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	

    //首先进行非空字段检验
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == null || fm.NoRgtReason.value == ""))
    {
        alert("请填写不予立案原因!");
        return;
    }
    
    //必须点击案件标识
    if (fm.rgtType.value == null || fm.rgtType.value == "")
    {
        alert("请选择案件类型!");
        return;
    }
    
    //对不予立案的要求发起的调查，呈报都完成 Add by zhaorx 2006-03-08
    if(fm.RgtConclusion.value == '02')
    {
	    //调查是否完成校验
	    /*
	    var strSql1 = " select FiniFlag from LLInqConclusion where "
	                + " clmno = '" + fm.RptNo.value + "'";
	                */
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql22");
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
	    //呈报是否完成校验
	    /*
	    var strSql2 = " select SubState from LLSubmitApply where "
	                + " clmno = '" + fm.RptNo.value + "'";
	                */
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql23");
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
    }
    

    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave.jsp';
    submitForm();


}


//提交
function submitForm()
{

	//提交数据
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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


//出险人信息修改
function updateClick()
{
	  if(!KillTwoWindows(fm.RptNo.value,'20'))
	  {
	  	  return false;
	  }	
    if (confirm("您确实想修改该记录吗？"))
    {
        if (beforeSubmit())
        {
            fm.fmtransact.value = "UPDATE";
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
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("事故日期不能晚于当前日期!");
            return false; 
        }  
    }
    
    return true;
}

//报案查询
function queryReport()
{
    //信息校验
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    
    //检索事件号(一条)
    /*
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql24");
	mySql.addSubPara(rptNo); 
    ////prompt("queryReport:检索事件号(一条)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //检索报案号及其他报案信息(一条)
    /*
    var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator),RgtClass from LLReport where "
                + "RptNo = '"+rptNo+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql25");
	mySql.addSubPara(rptNo); 
    ////prompt("queryReport:选择一条立案记录:检索报案号及其他报案信息",strSQL2);
    var RptContent = easyExecSql(mySql.getString());

    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];

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
    
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
//    showOneCodeName('RptMode','RptMode','RptModeName');//报案方式
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志

    //更新页面权限
    fm.AccidentDate.readonly = true;

    fm.QueryPerson.disabled=true;
    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

    //设置按钮++++++++++++++++++++++++++++++++++++++++待添加

    //检索分案关联出险人信息(多条)
    /*
    var strSQL3 = " select CustomerNo,Name,"
                + " Sex,"
                + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                + " Birthday,"
                + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                + " from LDPerson where "
                + " CustomerNo in("
                + " select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql26");
	mySql.addSubPara(rptNo); 
	
    ////prompt("queryReport:立案-检索分案关联出险人信息(多条)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //查询赔案标志
    QueryClaimFlag();
}

//申请人查询,当案件为非死亡类时申请人为出险人
function queryProposer()
{
/*
    var strSQL = "select count(1) from LLReportReason a where "
                + "a.rpno = '" + fm.RptNo.value + "'"
                + "and substr(a.reasoncode,2,2) = '02'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql27");
	mySql.addSubPara(fm.RptNo.value); 
    //////prompt("选择一条立案记录查询申请人",strSQL);
    var tCount = easyExecSql(mySql.getString());
    //没有死亡理赔类型
    if (tCount == "0")
    {
        //var strSQL2 = "select a.phone,a.postaladdress from LCAddress a where "
                    //+ "a.customerno = '" + fm.customerNo.value + "'";
        //var tLCAddress = easyExecSql(strSQL2);
        //fm.RptorName.value = fm.customerName.value;
        //fm.RptorPhone.value = tLCAddress[0][0];
        //fm.RptorAddress.value = tLCAddress[0][1];
        ///fm.Relation.value = "00";
        showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
    } 
}



//执行完立案页面中结论保存按钮后的操作
function queryConclusionSaveRegister()
{
    //信息校验
    //alert("*********************");
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    
    //检索事件号、事故日期(一条)
    /*
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo  "
                + "and exists(select 1 from llreport where rptno=llcaserela.caseno and RptNo= '"+rptNo+"')";
    */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql28");
	mySql.addSubPara(rptNo); 
    ////prompt("queryConclusionSaveRegister:执行完立案页面中结论保存按钮后的操作-检索事件号、事故日期(一条)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
    /*
    var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),AcceptedDate,ApplyDate,Rgtantmobile,postcode from llregister  "
                + "where RgtNo='"+rptNo+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql29");
	mySql.addSubPara(rptNo); 
    ////prompt("queryConclusionSaveRegister:执行完立案页面中结论保存按钮后的操作-检索立案号及其他立案信息(一条)",strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    

    //更新页面内容
    if(AccNo){
    	fm.AccNo.value = AccNo[0][0];
    	fm.AccidentDate.value = AccNo[0][1];
    	fm.BaccDate.value = AccNo[0][1];
    }

    fm.RptNo.value = RptContent[0][0];
    //alert("RptNo改变后:"+fm.RptNo.value);
    fm.ClmNo.value = RptContent[0][0];
    //alert("ClmNo改变后:"+fm.ClmNo.value);
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
    fm.AcceptedDate.value = RptContent[0][21];
    fm.ApplyDate.value = RptContent[0][22];
    fm.RptorMoPhone.value = RptContent[0][23];
    fm.AppntZipCode.value = RptContent[0][24];
    
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//不立案原因
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//受托人性别
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//受托人类型

    //更新页面权限
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
//        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;              
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }

    //检索分案关联出险人信息(多条)
    /*
    var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from llcase where CaseNo = '"+ RptContent[0][0] +"')";
    */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql30");
	mySql.addSubPara(RptContent[0][0]); 
    ////prompt("queryConclusionSaveRegister:执行完立案页面中结论保存按钮后的操作-检索分案关联出险人信息(多条)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //根据立案结论显示隐藏层
    showDIV();
    
    //查询赔案标志
    QueryClaimFlag();
    
    //根据立案结论判断是否查询匹配理算信息
    if (fm.RgtConclusion.value == '01')
	{
	    afterMatchDutyPayQuery();
	    /*
        var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";
                    */
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql31");
		mySql.addSubPara(rptNo); 
        var tRgtState = easyExecSql(mySql.getString());
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
	}
	
	
}



//替换完报案号的立案查询
function queryNewRegister()
{
    //信息校验
    //alert("*********************");
    var RptNo = fm.RptNo.value;
    //alert("rptNo",rptNo);
    if(RptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    
    //检索事件号、事故日期(一条)
    /*
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo  "
                + "and llcaserela.caseno= '"+RptNo+"'";
                */
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql32");
	mySql.addSubPara(RptNo); 
    //prompt("queryNewRegister:替换完报案号的立案查询-检索事件号、事故日期(一条)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
    /*
    var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),AcceptedDate,ApplyDate,Rgtantmobile,postcode from llregister  "
                + "where RgtNo='"+RptNo+"'";
                */
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql33");
	mySql.addSubPara(RptNo); 
    //prompt("queryNewRegister:替换完报案号的立案查询-检索立案号及其他立案信息(一条)",strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    //prompt("strSQL2",strSQL2);
    

    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    //alert("RptNo改变后:"+fm.RptNo.value);
    fm.ClmNo.value = RptContent[0][0];
    //alert("ClmNo改变后:"+fm.ClmNo.value);
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
    fm.AcceptedDate.value = RptContent[0][21];
    fm.ApplyDate.value = RptContent[0][22];
    fm.RptorMoPhone.value = RptContent[0][23];
    fm.AppntZipCode.value = RptContent[0][24];
    
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//不立案原因
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//受托人性别
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//受托人类型

    //更新页面权限
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
//        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;              
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }

    //检索分案关联出险人信息(多条)
    /*
    var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from llcase where CaseNo = '"+ RptContent[0][0] +"')";
    */
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterInputSql");
	mySql.setSqlId("LLClaimRegisterSql34");
	mySql.addSubPara(RptContent[0][0] ); 
    //prompt("queryNewRegister:替换完报案号的立案查询-检索分案关联出险人信息(多条)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //根据立案结论显示隐藏层
    showDIV();
    
    //查询赔案标志
    QueryClaimFlag();
    
    //根据立案结论判断是否查询匹配理算信息
    if (fm.RgtConclusion.value == '01')
	{
	    afterMatchDutyPayQuery();
	    /*
        var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";
                    */
         mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql35");
		mySql.addSubPara(rptNo); 
        var tRgtState = easyExecSql(mySql.getString());
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
	}
	
}


//初始化的立案查询
function queryRegister()
{
    //信息校验
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    
    //alert("初始化的立案查询");

    
    //检索事件号、事故日期(一条)
    /*
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
                */
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql36");
		mySql.addSubPara(rptNo); 
    ////prompt("queryRegister:初始化的立案查询-检索事件号、事故日期(一条)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
   /*
    var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),AcceptedDate,applydate,Rgtantmobile,postcode from llregister "
                + "where rgtno= '"+rptNo+"'";
                */
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql37");
		mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
    ////prompt("queryRegister:初始化的立案查询-检索立案号及其他立案信息(一条)",strSQL2);
    

    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];

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
    fm.AcceptedDate.value = RptContent[0][21];
    fm.ApplyDate.value = RptContent[0][22];
    fm.RptorMoPhone.value = RptContent[0][23];
    fm.AppntZipCode.value = RptContent[0][24];
    

    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//不立案原因
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//受托人性别
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//受托人类型

    //更新页面权限
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
//        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;              
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }


    //检索分案关联出险人信息(多条)
    /*
    var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from llcase where CaseNo = '"+ rptNo +"')";
    */
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql38");
		mySql.addSubPara(rptNo); 
    //prompt("queryRegister:初始化的立案查询-检索分案关联出险人信息(多条)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //根据立案结论显示隐藏层
    showDIV();

    
    //查询赔案标志
    QueryClaimFlag();

    
    //根据立案结论判断是否查询匹配理算信息
    if (fm.RgtConclusion.value == '01')
	{
	    afterMatchDutyPayQuery();
	    /*
        var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";
          */          
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql39");
		mySql.addSubPara(rptNo); 
        var tRgtState = easyExecSql(mySql.getString());
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
    /*
        var strSQL = "select t.name,t.sex,t.phone,t.homeaddress,t.zipcode from laagent t where "
                    + "t.agentcode = '"+fm.AssigneeCode.value+"'";
                    */
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql40");
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
		    fm.AcceptedDate.value = "";
		    fm.ApplyDate.value = "";

		    fm.customerName.value = "";
		    fm.customerAge.value = "";
	 	    fm.customerSex.value = "";
	 	    fm.SexName.value = "";
		    //fm.IsVip.value = "";
		    //fm.VIPValueName.value = "";
		    fm.AccidentDate.value = "";
	 	    fm.occurReason.value = "";
	 	    fm.ReasonName.value = "";
		    fm.hospital.value = "";
		    fm.TreatAreaName.value = "";
		    fm.OtherAccidentDate.value = "";
		    fm.MedicalAccidentDate.value = "";
	 	    fm.accidentDetail.value = "";
//	 	    fm.IsDead.value = "";
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
        case "04" :
        	if (fm.claimType[2].checked == true)
        	{
        		fm.claimType[4].checked = true;
        	}
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
        	
        	getChangeDate();
        
            return;
    }
}

/**
 * 2008-11-18
 * zhangzheng
 * 根据选择的理赔类型决定医疗出险日期和其他出险日期是否可以录入
 * 
**/
function getChangeDate()
{
	var flag=false;//控制是否准许录入其他出险日期标志,默认不准许录入
	
	//理赔类型包含医疗时,准许录入医疗出险日期
	if(fm.claimType[5].checked == true)
	{
	    document.all('MedicalAccidentDate').disabled=false;
	    document.all('OtherAccidentDate').disabled=false; // add wyc for 累加器
	}
	else
	{
	    document.all('MedicalAccidentDate').value="";
	    document.all('MedicalAccidentDate').disabled=true;
	}
	
	//当只存在医疗类型时,其他出险日期不准许录入
	/*  // for 累加器 wyc modify
	var ClaimType = new Array;
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  if((fm.claimType[j].checked == true)&&(j!=5))
    	  {
    		  flag=true;
    	  }
    }
    
    if(flag==true)
    {
    	document.all('OtherAccidentDate').disabled=false;
    }
    else
    {
	    document.all('OtherAccidentDate').value="";
    	document.all('OtherAccidentDate').disabled=true;
    }
   */
}

//显示隐藏层
function showDIV()
{
	if (fm.RgtConclusion.value == '01')
	{
	    //显示计算层
        //spanConclusion1.style.display="";
        spanConclusion2.style.display="none";
        fm.dutySet.disabled = false;
        fm.medicalFeeCal.disabled = false;
        fm.printNoRgt.disabled = true;
        //fm.printDelayRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        
        //fm.medicalFeeCal.disabled = true;//单证计算信息
   		//fm.printPassRgt.disabled = true;//打印单证签收清单
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
        //fm.printDelayRgt.disabled = true;
        
        //fm.printPassRgt.disabled = true;//打印单证签收清单
	}
    else if (fm.RgtConclusion.value == '03')
    {
        spanConclusion1.style.display="none";
	    spanConclusion2.style.display="none";
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        //fm.printDelayRgt.disabled = false;
        
        //fm.printPassRgt.disabled = true;//打印单证签收清单
        //fm.printDelayRgt.disabled = true;//打印补充单证通知书
    }
}

function queryLLReport()
{
    //信息校验
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    
    var strUrl="LLClaimReportQueryMain.jsp?claimNo="+document.all('ClmNo').value+"&customerNo="+fm.customerNo.value+"&Flag=common";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    
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
    /*
    var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";
                */
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql41");
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.customerNo.value); 
    var tAccNo = easyExecSql(mySql.getString());
//    alert(tAccNo);
    if (tAccNo == null)
    {
        alert("没有相关事件！");
        return;
    }
    //打开事件查询窗口
//	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
//	alert(strUrl);
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

//查询出险结果
function queryResult(tCode,tName)
{
/*
    var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";
               */
      mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql42");
		mySql.addSubPara( document.all(tCode).value); 
		
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//打印单证签收清单,立案通过(01)时才能使用-----在线打印，不用判断
function prtPassRgt()
{
    //检查结论是否为立案通过,否则不能打印
    /*
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
               */
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql43");
		mySql.addSubPara(fm.RptNo.value); 
    var tResult = easyExecSql(mySql.getString());
    if (tResult == null)
    {
        alert("请先保存立案结论!");
        return;
    }

    //增加单证是否回销的校验
    /*
    var tCertSql = " select affixno,affixcode,affixname,subflag,"
				+ "(case needflag when '0' then '0-是' when '1' then '1-否' end),"
				+ "readycount,shortcount,property "
				+ "from llaffix where '1' = '1' "
				+ "and 1 = 1 "
				+ "and rgtno = '"+fm.RptNo.value+"' "
				+ "and customerno = '"+fm.customerNo.value+"' "
				+ "and (subflag is null or subflag = '1')";
				*/
	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql44");
		mySql.addSubPara(fm.RptNo.value); 
		mySql.addSubPara(fm.customerNo.value); 
    var tArrCert = easyExecSql(mySql.getString());//prompt("",);
    if(tArrCert){
    	alert("有单证尚未回销！");
    	return false;
    }
    
    //以下填写打印提交内容
    fm.action = './LLPRTCertificateSignforSave.jsp';   
    fm.target = "../f1print";
    document.getElementById("fm").submit(); 
}

//打印不予立案通知书,不予立案(02)时才能使用
function prtNoRgt()
{
/*
    //检查结论是否为不予立案,否则不能打印
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
               */
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql45");
		mySql.addSubPara(fm.RptNo.value); 

    var tResult = easyExecSql(mySql.getString());
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
//    document.getElementById("fm").submit();
}

//打印补充单证通知书,延迟立案(03)时才能使用
function prtDelayRgt()
{
    //检查结论是否为延迟立案,否则不能打印
    /*
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
               */
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql46");
		mySql.addSubPara(fm.RptNo.value); 
    var tResult = easyExecSql(mySql.getString());
    if (tResult != '03')
    {
//        alert("请先保存立案结论!");
//        return;
    }
    //以下填写打印提交内容
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT003")=="false")
    {
    	return;
    }
   
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();
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
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
    
    fm.action = './LLPRTAppraisalSave.jsp';   
    if(beforePrtCheck(fm.ClmNo.value,"PCT001")=="false")
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
   	/*
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";
			*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql47");
		mySql.addSubPara(tclmno); 
		mySql.addSubPara(tcode); 
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
	 	if(tstateflag!="1")
	 	{
//			fm.action = './LLPRTCertificatePutOutSave.jsp';   //
			fm.target = "../f1print";
			document.getElementById("fm").submit();
	 	}
		else
		{
			//存在但已经打印过
			if(confirm("该单证已经打印完成，你确实要补打吗？"))
	 		{
	 			//进入补打原因录入页面
	 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
//	 			window.open(strUrl,"单证补打");
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}

//医院模糊查询
function showHospital(tCode,tName)
{
	var strUrl="LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//出险细节查询
function showAccDetail(tCode,tName)
{
	var strUrl="LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//单证批量打印控制
function showPrtControl()
{
    var tClmNo = fm.RptNo.value;
    /*
	var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";
			*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql48");
		mySql.addSubPara(tClmNo); 
	var arrCerti = easyExecSql(mySql.getString());
	if(arrCerti==null || arrCerti[0][0]=="0")
	{
		alert("没有需要进行批打控制的单证");
		return false;
	}	
    var strUrl="LLClaimCertiPrtControlMain.jsp?ClmNo="+tClmNo;
//    window.open(strUrl,"单证打印控制");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}


//收件人信息录入,Added by niuzj,2005-10-24
function showReciInfoInp()
{
	var tClmNo = fm.RptNo.value;   //赔案号
	var tcustomerNo=fm.customerNo.value;  //出险人代码
	var tIsShow = 1;               //[保存]按钮能否显示,0-不显示,1-显示
  var tRgtObj = 1;               //个险团险标志,1-个险,2-团险
	
	//用弹出页时,最好写一个Main页面
	var strUrl="LLClaimReciInfoMain.jsp?ClmNo="+tClmNo+"&IsShow="+tIsShow+"&RgtObj="+tRgtObj+"&CustomerNo="+tcustomerNo;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
	//location.href="LLClaimReciInfoInput.jsp?claimNo="+tClmNo; 
}

//发起外包录入
function showWaiBao()
{
	  if(!KillTwoWindows(fm.RptNo.value,'20'))
	  {
	  	  return false;
	  }	
	var tClmNo = fm.ClmNo.value;
  var tClmState = fm.clmState.value;
  tClmState = tClmState.substring(0,2);
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
  var tActivityID = "0000005015";
  
  //如果llregister表中FeeInputFlag字段的值为0或2,都可以发起外包录入
  /*
  var strSQL=" select a.FeeInputFlag from llregister a "
            +" where a.rgtno='"+ tClmNo +"' ";
            */
  	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql49");
		mySql.addSubPara(tClmNo);
  var arr = easyExecSql( mySql.getString() );
  if(arr[0][0]==1)  //如果值为1,则不允许发起
  {
    alert("医疗单证外包录入还未完成,你不能再次发起外包录入!");
  } 
  else
  {
	  fm.action="LLClaimFaQiWaiBaoSave.jsp?ClmNo="+tClmNo+"&ClmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	  submitForm();
  }
}

/**=========================================================================
    修改状态：开始
    修改原因：如果出险人和报案人是同一个人的话，那么报案人的信息从出险人的
              信息中得到
    修 改 人：万泽辉
    修改日期：2005.11.15
   =========================================================================
**/
function getRptorInfo()
{
   var tCustomerNo = fm.customerNo.value ;
   var tRelation = fm.Relation.value;
   var tRelationName = fm.RelationName.value;
   if(tRelation == "00"|| tRelationName == "本人")
   {
      if( tCustomerNo != null  && tCustomerNo != "")
      {
          //var strSQL = "select postaladdress,phone from lcaddress where customerno ='"+tCustomerNo+"'";
          mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql50");
		mySql.addSubPara(tCustomerNo);
          var strQueryResult = easyExecSql(mySql.getString());
          if(strQueryResult==null || strQueryResult=="")
          {
          	return;
          }
          else
          {
            fm.RptorAddress.value = strQueryResult[0][0];
            fm.RptorPhone.value = strQueryResult[0][1];
            fm.RptorName.value = fm.customerName.value;
          }
      }else{
          return;
      }
   }
   
 //修改原因：如果“报案人和出险人关系”为“GX02-保单服务人员”时，那么报案人的信息取保单服务人员的信息
   //修 改 人：zhangzheng 
   //修改时间：2008-12-16
   //MS没有保单服务人员,去掉这段逻辑
   
   //else if(tRelation == "GX02" || tRelationName == "保单服务人员")
   //{
   	 //if( tCustomerNo != null  && tCustomerNo != "")
   	 //{
   	 	   //var strSQL =" select b.name, b.homeaddress, b.phone from laagent b " 
           //         +" where 1=1 "
           //         +" and b.agentcode in (select distinct trim(a.agentcode) from lccont a where a.insuredno = '"+tCustomerNo+"') ";
         //var arr = easyExecSql(strSQL);
         // if(arr==null || arr=="")
         // {
         // 	return;
         // }
         // else
         // {
         //    fm.RptorName.value = arr[0][0];
         //    fm.RptorAddress.value = arr[0][1];
         //    fm.RptorPhone.value = arr[0][2];
         // }
         
   	// }
   	// else
   	 //{
   	 	// return;
   	 //}
   //}
   else{
       return;
   }
}

//查询治疗医院，Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
/*
	  var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";
	             */
	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql51");
		mySql.addSubPara(document.all(tCode).value);
	  var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


/*
 * 日期校验,校验两个日期都不能晚于当前日期，且第2个日期不能早于第1个日期
 * Date1,传入的第一个日期,此处为事故日期
 * Date2 传入的第二个日期,此处根据情况为医疗出险日期或其他出险日期
 * tDateName 传入的日期名称，用于组成弹出的提示语言
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var AccDate  =  tDate1;//事故日期
    var AccDate2 =  tDate2;//出险日期日期
   
    //alert("AccDate:"+AccDate);
    //alert("AccDate2:"+AccDate2);
    //alert("tDateName:"+tDateName);
       
    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入事故日期！");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("事故日期不能晚于当前日期!");
            return false; 
        }
    }
    
    //验证事故下有两个或以上的处理中赔案--------BEG
    if (fm.BaccDate.value == null || fm.BaccDate.value == '')//Modify by zhaorx 2006-07-31
    {
    	fm.BaccDate.value = AccDate; //新增报案时，不校验事故日期
    }    
    
    var OAccDate = fm.BaccDate.value;//原事故日期Modify by zhaorx 2006-07-04
   
    if (OAccDate != AccDate)
    {   
    /* 
        var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"
        */
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql52");
		mySql.addSubPara(fm.AccNo.value);
        //prompt("验证事故下有两个或以上的处理中赔案",strSQL);
        
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("事故下有两个或以上的处理中赔案，不允许修改事故日期！");
            fm.AccidentDate.value = OAccDate;
            return false;
        }
    }
    

    //校验出险日期
    if (AccDate2 == null || AccDate2 == '')
    {
        alert(tDateName+"不能为空！");
        return false;
    }
    else
    {
       	//比较出险日期和当前日期
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert(tDateName+"不能晚于当前日期!");
            return false; 
        }

        //比较出险日期和事故日期       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert(tDateName+"不能早于事故日期!");
            return false; 
        }

    }
    
    return true;
}
