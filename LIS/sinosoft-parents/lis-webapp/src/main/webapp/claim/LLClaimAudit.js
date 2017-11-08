var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var tTLFlag="0";

//单证费用调整
function showLLMedicalFeeAdj()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;     //客户号

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeAdjMain.jsp?RptNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//进行保险责任匹配
function showMatchDutyPay2(tAccNo)
{  
	fm.AccNo.value=tAccNo;
	//alert(fm.AccNo.value);
	
	if(!KillTwoWindows(fm.ClmNo.value,'30'))
	{
	  	  return false;
	}	 

	//alert("开始匹配并理算");
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
    	afterMatchDutyPayQuery();
    }
}

//初始化时进行保险责任匹配
function initShowMatchDutyPay()
{  	
	if(!KillTwoWindows(fm.ClmNo.value,'30'))
	{
	  	  return false;
	}	 
	
    //判断是否有预付或回退,有则初始化重新理算,没有则不重新理算
   /* var strSql = "select k from ("
    			+ "select 1 k from llprepayclaim where "
                + "clmno = '" + fm.RptNo.value + "'"
                + " union "
                + " select 1 k from llcaseback where "
                + "clmno = '" + fm.RptNo.value + "')"*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql1");
	mySql.addSubPara(fm.RptNo.value );  
	mySql.addSubPara(fm.RptNo.value );       
    //prompt("判断案件是否存在预付或回退:",strSql);
    
    var tFlag = easyExecSql(mySql.getString());

    if (!(tFlag == null||tFlag == ""))
    {
    	//判断是否已经进行了受益人分配,有则不进行重新理算！
    	//strSql=" select 1 from llbnf where clmno='"+fm.RptNo.value+"' and bnfkind='A'";
    	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql2");
		mySql.addSubPara(fm.RptNo.value );  

    	tFlag = easyExecSql(mySql.getString());

    	//为空标志没有记录
    	if(tFlag == null||tFlag == "")
    	{
        	//alert("tFlag:"+tFlag);
        	fm.hideOperate.value="MATCH||INSERT";
        	fm.action = "./LLClaimRegisterInitMatchCalSave.jsp";
        	fm.target="fraSubmit";
        	document.getElementById("fm").submit(); //提交
    	}

    }
    else
    {
    	//直接查询理算结果
        afterMatchDutyPayQuery();
    }

}


//点击匹配并理算按钮后的动作
function afterInitMatchDutyPay(FlagStr, content)
{
	//理算出错就提示,不再执行结论保存

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
    }

    afterMatchDutyPayQuery();

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
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
	  //对投连险是否进行保单结算进行判断
	  /*var tSql=" select count(*) from lmrisktoacc c, lmriskinsuacc r "
  		  + " where r.insuaccno = c.insuaccno and r.acckind = '2' "
  			+ " and c.riskcode in (select riskcode from LLClaimPolicy where "
             + " caseno = '" + fm.RptNo.value + "')";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql68");
	mySql.addSubPara(fm.RptNo.value );
  	var count=easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.RptNo.value );
			var countLP=easyExecSql(mySql.getString());
			if(!countLP)
			{
				alert("请先进行结算!");
				return;
			}
			var ifGoon=false;//是否进行了计价 默认为没进行
			if(countLP[0][0]=='2'&&countLP[0][1])
			{
				ifGoon=true;
			}
			if(!ifGoon)
			{
				alert("请等待计价处理完成!");
				return;
			}
		}
		
    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
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
    var arr;

    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    //查询整个赔案的金额
   /* tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql3");
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
       +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,a.RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql4");
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql5");
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
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
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
       +" a.dutycode,a.customerno"
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"   
       +" and a.GiveType not in ('2')"
       ;*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql6");
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

//录入医疗单证信息
function showLLMedicalFeeInp()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
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
    修改原因：以上审核阶段，进行匹配，计算，确认等函数功能区
    修 改 人：续涛
    修改日期：2005.06.01
   =========================================================================
**/

//发起二核
function showSecondUWInput()
{
//增加立案后15天不能发起二核的校验 （暂定为15天）
//	var tClmNo = fm.RptNo.value;
//	var tJudgeSql = "select 1 from dual where sysdate-(select makedate from llregister a where a.rgtno='"
//					+ tClmNo +"')>=15";
//	var whetherUW = easyExecSql( tJudgeSql );
//	if(whetherUW){
//		if(whetherUW=="1"){
//			alert("立案日期到当前日期已超过15天，不能发起二核！");
//			return false;
//		}
//	}
	var tClmNo = fm.RptNo.value;
    /*var tJudgeSql = "select count(*) from LLCUWBatch where "
        + " caseno = '" + tClmNo + "'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql7");
		mySql.addSubPara(tClmNo); 
    var CUWCount = easyExecSql(mySql.getString());
    
    if (CUWCount > 0){
    	alert("已发起过二核，请到二核结论处理中再次提起二核！");
        return false;
      }
	
	
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
//	  //检查是否存在二核的加费信息，如有必须核销(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//     {
//        return;//此处调用公有方法
//     }
	  
      //alert(fm.customerName.value);
      //alert(fm.RptorName.value);
    
	  var strUrl="LLSecondUWMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value+"&InsuredName="+fm.customerName.value+"&RptorName="+fm.RptorName.value+"&MissionID="+fm.MissionID.value;    
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
//      window.open(strUrl,"发起二核");
}

//二核处理
function SecondUWInput()
{   
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	 
	  var tFlag = "N";
	  
	  var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value+"&Flag="+tFlag;    
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
//      window.open(strUrl,"二核处理");
}
//[豁免处理]按钮<>
function showExempt()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    var tClaimNo = fm.RptNo.value;         //赔案号
    var strUrl="LLClaimExemptMain.jsp?ClaimNo="+tClaimNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开发起调查
function showBeginInq()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&initPhase=02";//+"&custVip="+fm.IsVip.value;
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
  /*  var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql8");
		mySql.addSubPara( fm.RptNo.value ); 
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
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql9");
		mySql.addSubPara( fm.RptNo.value ); 
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("该赔案还没有呈报信息！");
    	  return;
    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open(strUrl,"呈报查询","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
//    if(SubReportGrid.getSelNo() <= 0)
//    {
//        alert("先选择出险人！");
//        return;
//    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
//Modifyed by niuzj,2005-12-23
	if (tcustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}    
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"问题件回销");
}

//发出问题件
function showRgtAddMAffixList()
{
//    if(SubReportGrid.getSelNo() <= 0)
//    {
//        alert("先选择出险人！");
//        return;
//    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
//Modifyed by niuzj,2005-12-23
	if (tcustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}      
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
    showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
//    showOneCodeName('RptMode','RptMode','RptModeName');//报案方式(?????)  	
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    
    //查询获得事件号
    var LLAccident = new Array;
   /* var strSQL1 = "select AccNo,AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+arr[0]+"')";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql10");
		mySql.addSubPara( arr[0]); 
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
                         + " select CustomerNo from LLSubReport where SubRptNo = '"+ arr[0] +"')";*/
 		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql11");
		mySql.addSubPara( arr[0]); 
//    alert(strSQL2);                 
    easyQueryVer3Modal(mySql.getString(),SubReportGrid);    
    
    //设置可操作域
    fm.AuditIdea.disabled = false;
    fm.AuditConclusion.disabled = false;
    fm.ConclusionSave.disabled = false;
   
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
	//fm.IsVip.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.MedicalAccidentDate.value = "";
	fm.OtherAccidentDate.value = "";
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
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
       // fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql12");
		mySql.addSubPara( fm.RptNo.value); 
		mySql.addSubPara( fm.customerNo.value); 
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
    //--------------------------1----------2---------3-----------4---------5------6-----7-------------8----------9----------10---------11
    /*var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,"
    	        + " (case EditFlag when '0' then '否' when '1' then '是' end),"
    	        + " (case AffixConclusion when '0' then '否' when '1' then '是' end),MedAccDate from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
 	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql13");
		mySql.addSubPara( fm.RptNo.value); 
		mySql.addSubPara( fm.customerNo.value); 
//    alert(strSQL2);
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
    fm.IsModify.value = tSubReport[0][9];
    fm.IsAllReady.value = tSubReport[0][10];
    fm.IsModifyName.value = tSubReport[0][11];
    fm.IsAllReadyName.value = tSubReport[0][12];
    fm.MedicalAccidentDate.value = tSubReport[0][13];
    //showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('accidentcode','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');
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
    fm.GiveReasonDesc.value = "";//拒付依据
    fm.GiveReason.value = "";//拒付原因代码

    //设置按钮
    fm.addUpdate.disabled = false;//添加修改
    //得到mulline信息
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,14);//赔付结论
        fm.Currency.value = PolDutyCodeGrid.getRowColData(i,9);
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,23);//调整原因
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,24);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,25);//调整备注
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,16);//拒付原因代码
        fm.GiveReasonDesc.value = PolDutyCodeGrid.getRowColData(i,18);//拒付依据
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
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("请先选择要处理的保项信息,再执行此操作！");
        return;
    }
    var i = PolDutyCodeGrid.getSelNo() - 1;//得到焦点行
    
    //拒付时,要填写拒付原因
    if (fm.GiveType.value == "1" && fm.GiveReason.value == "")//拒付
    {
        alert("请填写拒付原因！");
        return;
    }
    //调整金额时，调整原因不能为空 Modify by zhaorx 2006-05-15
    if(fm.GiveType.value == "0" && fm.AdjReason.value == "")
    {
    	alert("请填写调整原因！");
    	return;
    }
    
    if(checkAdjMoney()==false)
    {
    	return false;
    }
    
    //增加修改
    PolDutyCodeGrid.setRowColData(i,14,fm.GiveType.value);//赔付结论
    PolDutyCodeGrid.setRowColData(i,16,fm.GiveReason.value);//拒付原因代码
    PolDutyCodeGrid.setRowColData(i,18,fm.GiveReasonDesc.value);//拒付依据
    PolDutyCodeGrid.setRowColData(i,22,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReason.value);//调整原因
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,25,fm.AdjRemark.value);//调整备注
    
    
    fm.saveUpdate.disabled = false;//保存修改
}

//对保项修改保存到后台
function SaveUpdate()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    fm.saveUpdate.disabled = true;//保存修改后，按钮按下 Modify by zhaorx 2006-05-15
    submitForm();
}

//公共提交方法
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";    
}

//转向预付管理后操作,返回
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
        
        fm.whetherPrePay.value="";
        fm.whetherPrePayName.value="";
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
        
        location.href="../claim/LLClaimPrepayMain.jsp?ClmNo="+fm.ClmNo.value+"&CustomerNo="+fm.customerNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&ActivityID="+fm.ActivityID.value;
        
        //goToBack();
    }
    
  

}

//提交后操作,返回
function afterSubmit1( FlagStr, content )
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

//提交后操作,不返回
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
        queryRegister();
    }
    tSaveFlag ="0";
}



//返回按钮
function goToBack()
{
	
  if (fm.RgtObjNo.value!=null && fm.RgtObjNo.value!="")
  { 
    location.href="../claimgrp/LLGrpClaimAuditMissInput.jsp";
  }    
  else
  {
  	location.href="LLClaimAuditMissInput.jsp";
  }	

}


//审核结论保存
function ConclusionSaveClick()
{	
	//zy 增加案件类型的校验
	if(!(fm.ModifyRgtState.value=="11" || fm.ModifyRgtState.value=="12" ||fm.ModifyRgtState.value=="14" ))
	{
		alert("案件标识不符合要求，请核实");
		return ;
	}
//如存在未处理的二核，给予系统提示，但不影响审核结论保存
  /*var strSqlCuw = "select count(*) from LLCUWBatch where "
              + " caseno = '" + fm.RptNo.value + "' and InEffectFlag='0'" ;*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql14");
		mySql.addSubPara( fm.RptNo.value); 
  var tCountcuw = easyExecSql(mySql.getString());

  if (tCountcuw>0)
    {     
	  if(!confirm("请注意，有未作处理的二核结论，您确认进行结论保存"))
       {
          return false;
        }
    }

	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
	  //对投连险是否进行保单结算进行判断
  	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql68");
	mySql.addSubPara(fm.RptNo.value );
  	var count=easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			/*tSql="select state,dealdate,makedate||maketime,(select max(makedate||maketime) from llclaim where clmno=AccAlterNo) from LOPolAfterDeal where busytype='CL' and AccAlterNo='"+fm.ClmNo.value
							+"' and AccAlterType='4' order by state desc";*/
		    mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.ClmNo.value );
			var countLP=easyExecSql(mySql.getString());
			if(!countLP)
			{
				alert("请先进行结算!");
				return;
			}
			var ifGoon=false;//是否进行了计价 默认为没进行
			if(countLP[0][0]=='2'&&countLP[0][1])
			{
				ifGoon=true;
			}
			if(!ifGoon)
			{
				alert("请等待计价处理完成!");
				return;
			}
			//确认是否重新进行了理算：如果结算信息晚于理算信息则没有重新进行理算
			if(countLP[0][2]>=countLP[0][3])
			{
				alert("请重新进行理算!");
				return;
			}
		}
		
	//判断表单输入是否为空
	if (fm.AuditConclusion.value == "" || fm.AuditConclusion.value == null)
	{
		alert("请先填写审核结论!");
        return;
	}
    else
    {
    	   //拒付时,要填写拒付原因，by niuzj,2005-11-22
        if (fm.AuditConclusion.value == "1" && fm.ProtestReason.value == "")//拒付
        {
           alert("请填写拒付原因！");
           return;
        }
        
        if (fm.AuditConclusion.value == "1")
        {
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql15");
		mySql.addSubPara( fm.RptNo.value); 
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                //alert(tGivetype.length);
                for (var i=0; i<tGivetype.length; i++)
                {
//                    alert(tGivetype[i])
                    if (tGivetype[i] != "1")
                    {
                        alert("保项不全部为拒付,不能下全部拒付结论!");
                        return;
                    }
                }
            }
        }
        else if (fm.AuditConclusion.value == "0")
        {
            var tGivetype = new Array;
            /*var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
           mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql16");
		mySql.addSubPara( fm.RptNo.value); 
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGivetype.length; i++)
                {
//                    alert(tGivetype[i])
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
    if (fm.AuditConclusion.value == "0" || fm.AuditConclusion.value == "1")
    {
        //调查、呈报、问题件和二核校验,保单结算、合同处理
        if (!checkPre())
        {
            return;
        } 
        //如果只有豁免不用判断受益人
       /* var tSQL = "select  nvl(count(*),0) from llclaimdutykind where 1=1 and substr(getdutykind,2,2)='09'"
                 + getWherePart( 'ClmNo','ClmNo' );*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql17");
		mySql.addSubPara( fm.ClmNo.value); 
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind == 0||tDutyKind>1)
        {
//            //如果下给付结论,校验受益人分配
//            if (fm.AuditConclusion.value == "0")
//            {
                /*var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql18");
		mySql.addSubPara( fm.ClmNo.value); 
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
                else
                {
                	alert("受益人分配没有完成!");
                    return;
                }
//            }
        }
    }
    
//===周磊===针对有的赔案下多张保单有的给付有的解除情况通过不了校验，故去掉（如果只是为了防止给付又退费的情况没有必要）=======beg===2006-5-22 15:04
//    //如果是给付结论则判断是否有解除合同操作
//    if (fm.AuditConclusion.value == "0")
//    {
//        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
//                    + getWherePart( 'ClmNo','ClmNo' );
//        var tCount = easyExecSql(strSql5);
//        if (tCount > 0)
//        {
//            alert("已进行解除合同处理，不能下给付结论！");
//            return;
//        }
//    }
//===周磊===针对有的赔案下多张保单有的给付有的解除情况通过不了校验，故去掉（如果只是为了防止给付又退费的情况没有必要）=======end===2006-5-22 15:04
    
    //如果有豁免保项,给出提示,要求进行豁免处理
    if (fm.AuditConclusion.value == "0")
    {
       /* var tSqlExe = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 +" and clmno = '" + fm.RptNo.value + "'"
                 +" and getdutykind in ('109','209') "
                 ;*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql19");
		mySql.addSubPara( fm.RptNo.value); 
        var tDutyExe = easyExecSql(mySql.getString());
        if (tDutyExe != null)
        {
        	alert("匹配出来的保项有豁免信息,记得进行豁免处理!!!");  
        }
    }
    
	if (fm.AuditIdea.value == "" || fm.AuditIdea.value == null)
	{
		alert("请先填写审核意见!");
        return;
	}
    
    // 审核意见超过700字符时，提示！2006-01-01 小赵
    if(fm.AuditIdea.value.length >=700)
    {
    	alert("审核意见不能超过700个字符，请您重新填写！");
    	return;
    }
    //案件标识要求必录 Modify by zhaorx 2006-08-15
    if(fm.ModifyRgtState.value ==""||fm.ModifyRgtState.value==null)
    {
    	alert("请您填写案件标识！");
    	return;
    }
    
//检查是否存在二核的加费信息，如有必须核销(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//此处调用公有方法
//    }
    
    //两年抗辩期的校验:如果为长期险且生效日超过出险日期2年（含2年），则给予提示信息
    if (!checkAccDate(fm.RptNo.value))
    {
        return;
    }    
    
    //提交
    fm.action="./LLClaimAuditConclusionSave.jsp"
    fm.fmtransact.value = "INSERT";
    submitForm();
}

//两年抗辩期的校验:如果为长期险且生效日超过出险日期2年（含2年），则给予提示信息
function checkAccDate(tRptNo)
{	
	var flag=false;//是否超过两年标示
	var tContno="";//超过两年的保单号
	
	var tAccDate;//个险的出险日期：如果其他出险日期为空则获取医疗出险日期
    //var strQSql = "select AccDate, MedAccDate from LLCase where CaseNo ='"+tRptNo+"' ";
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql20");
		mySql.addSubPara(tRptNo); 
    var tDate = easyExecSql(mySql.getString());
    if (tDate == null)
    {
        alert("未查询到出险日期!");
        return true;
    }
    else if (tDate[0][0]==null || tDate[0][0]=="")
    {
        tAccDate=tDate[0][1];
    }else{
    	tAccDate=tDate[0][0];
    }

   /* var strQSql = "select cvalidate,contno from lccont a where a.contno in "
    			+" (select distinct b.contno from LLClaimPolicy b where b.clmno = '"+tRptNo+"')";
   */ mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql21");
		mySql.addSubPara(tRptNo); 
    var tCvalidate = easyExecSql(mySql.getString());//个险保单对应生效日期
    if (tCvalidate == null)
    {
        alert("未查询到此案件对应保单的生效日期!");
        return true;
    }
	for(var i=0;i<tCvalidate.length;i++){
		//alert("tCvalidate[i]:"+tCvalidate[i]);
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
   /* var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql22");
		mySql.addSubPara(fm.RptNo.value); 
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
                alert("问题件回销没有完成!");
                return false;
            }
        }
    }
    //调查
    /*var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql23");
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql24");
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
    //二核
//    var strSql4 = "select InEffectFlag from LLCUWBatch where "
//                + " caseno = '" + fm.RptNo.value + "'"
//                //+ " and InEffectFlag not in ('1','2') and state = '1'"
//                ;
//    var tState = easyExecSql(strSql4);
    
//    if (tState)
//    {
//        for (var i = 0; i < tState.length; i++)
//        {
//            if (tState[i] == '0')
//            {
//                alert("发起的二核没有做是否生效处理!");
//                return false;
//            }
//        }
//检查是否存在二核的加费信息，如有必须核销(by zl 2006-1-6 11:40)
//        if (!checkLjspay(fm.RptNo.value))
//        {
//            return false;//此处调用公有方法
//        }
//    }
    
    //保单结算\合同处理是否完成
    /*var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql25");
		mySql.addSubPara(fm.RptNo.value); 
    var tFlag = easyExecSql(mySql.getString());
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
    return true;
}

//审核结论确认
function AuditConfirmClick()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
	  //对投连险是否进行保单结算进行判断
	  mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql68");
	mySql.addSubPara(fm.RptNo.value );
  	var count=easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.RptNo.value );
		  	var countLP=easyExecSql(mySql.getString());
			if(!countLP)
			{
				alert("请先进行结算!");
				return;
			}
			var ifGoon=false;//是否进行了计价 默认为没进行
			if(countLP[0][0]=='2'&&countLP[0][1])
			{
				ifGoon=true;
			}
			if(!ifGoon)
			{
				alert("请等待计价处理完成!");
				return;
			}
			//确认是否重新进行了理算：如果结算信息晚于理算信息则没有重新进行理算
			if(countLP[0][2]>=countLP[0][3])
			{
				alert("请重新进行理算!");
				return;
			}
		}
	  
	  
    //判断审核结论
    /*var strSql = "select AuditConclusion from LLClaimUWMain where "
               + "clmno = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql26");
		mySql.addSubPara(fm.RptNo.value); 
    var tGivetype = easyExecSql(mySql.getString());
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
          /*  var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql27");
		mySql.addSubPara(fm.RptNo.value); 
            tGtype = easyExecSql(mySql.getString());
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
          /*  var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
             mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql28");
		mySql.addSubPara(fm.RptNo.value); 
            tGtype = easyExecSql(mySql.getString());
            if (tGtype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] == "0")
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
        /*var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' );*/
         mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql29");
		mySql.addSubPara(fm.ClmNo.value); 
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind != '09')
        {
//            //如果下给付结论,校验受益人分配
//            if (tGivetype == "0")
//            {
               /* var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql30");
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
                /*var tBalanceMoney =0.00;
                var tBnfMoney=0.00;
                var tBalanceMoneySql = "select nvl(sum(pay),0) from LLBalance where clmno='"+document.all('RptNo').value+"'";
                var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"'";
                 tBalanceMoney = easyExecSql(tBalanceMoneySql);
                 tBnfMoney = easyExecSql(tBnfMoneySql);
                if(Arithmetic(tBalanceMoney,'-',tBnfMoney,2)!=0)
								{
								  alert("理赔清算金额与受益人分配总金额不一致，请核实是否存在未分配的精算金额！")
								  return false;
								}*/
								//2009-9-29 18:30 zy 调整校验规则的暂时方案
								//var tFeetype ="select distinct feeoperationtype from llbnf where clmno='"+document.all('RptNo').value+"'";
								mySql = new SqlClass();
								mySql.setResourceName("claim.LLClaimAuditInputSql");
								mySql.setSqlId("LLClaimAuditSql31");
								mySql.addSubPara(document.all('RptNo').value); 
								var tFeetypeValue = easyExecSql(mySql.getString());
								for (var t=0;t<tFeetypeValue.length;t++)
								{
									var tBalanceMoney =0.00;
                	var tBnfMoney=0.00;
									if(tFeetypeValue[t]=='A' || tFeetypeValue[t]=='B')
									 {
									 	//var tBalanceMoneySql = "select nvl(sum(pay),0) from LLBalance where clmno='"+document.all('RptNo').value+"' and feeoperationtype ='A'";
						                mySql = new SqlClass();
										mySql.setResourceName("claim.LLClaimAuditInputSql");
										mySql.setSqlId("LLClaimAuditSql32");
										mySql.addSubPara(document.all('RptNo').value); 
										tBalanceMoney = easyExecSql(mySql.getString());
						               //var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"' and feeoperationtype  in ('A','B')";
						               mySql = new SqlClass();
										mySql.setResourceName("claim.LLClaimAuditInputSql");
										mySql.setSqlId("LLClaimAuditSql33");
										mySql.addSubPara(document.all('RptNo').value); 
						               tBnfMoney = easyExecSql(mySql.getString());
				              if(Arithmetic(tBalanceMoney,'-',tBnfMoney,2)!=0)
											{
											  alert("理赔清算金额与受益人分配总金额不一致，请核实是否存在未分配的精算金额！")
											  return false;
											}
									 }
									else
										{
											//var tBalanceMoneySql = "select nvl(sum(pay),0) from LLBalance where clmno='"+document.all('RptNo').value+"' and feeoperationtype not in ('A','B')";
					                 		mySql = new SqlClass();
					                 		mySql.setResourceName("claim.LLClaimAuditInputSql");
											mySql.setSqlId("LLClaimAuditSql35");
											mySql.addSubPara(document.all('RptNo').value); 
							                 tBalanceMoney = easyExecSql(mySql.getString());
							                 
								             // var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"' and feeoperationtype not in ('A','B')";
								              mySql = new SqlClass();
											mySql.setResourceName("claim.LLClaimAuditInputSql");
											mySql.setSqlId("LLClaimAuditSql36");
											mySql.addSubPara(document.all('RptNo').value); 
				            
						               tBnfMoney = easyExecSql(mySql.getString());
						              if(Arithmetic(tBalanceMoney,'-',tBnfMoney,2)!=0)
											{
											  alert("理赔清算金额与受益人分配总金额不一致，请核实是否存在未分配的精算金额！")
											  return false;
											}

										}
									}
//            }
        }
    }
    
//    //如果是给付结论则判断是否有解除合同操作
//    if (tGivetype == "0")
//    {
//        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
//                    + getWherePart( 'ClmNo','ClmNo' );
//        var tCount = easyExecSql(strSql5);
//        if (tCount > 0)
//        {
//            alert("已进行解除合同处理，不能下给付结论！");
//            return;
//        }
//    }
    
//检查是否存在二核的加费信息，如有必须核销(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//此处调用公有方法
//    }
    
            
    //提交
    fm.action="./LLClaimAuditSave.jsp"
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
    /*var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql37");
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
       /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator) from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql38");
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
      		  fm.Operator.value = RptContent[0][10];
      		  showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
      		  showOneCodeName('RptMode','RptMode','RptModeName');//报案方式      		  
      		  showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
      		  showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//      		  showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志

      		  //更新页面权限
      		  fm.Remark.disabled=true;

            //检索分案关联出险人信息(多条)
           /* var strSQL3 = " select CustomerNo,Name,"
                                 + " Sex,"
                                 + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA," 
                                 + " Birthday,"
                                 + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                                 + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                                 + " from LDPerson where "
                                 + " CustomerNo in("
                                 + " select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";
		*/mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql39");
		mySql.addSubPara(RptContent[0][0]); 
		
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
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
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql40");
		mySql.addSubPara(rptNo); 
    var AccNo = easyExecSql(mySql.getString());
    
    //prompt("",strSQL1);

    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-------------------------------------------------------13----------------------------------------14---------15-----------16------------17------------18---------19
    /*var strSQL2 =" select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate, "
                +" (select username from llclaimuser where usercode = llregister.Operator),mngcom, "
                +" (case assigneetype when '0' then '业务员' when '1' then '客户' end),assigneecode,assigneename, "
                +" (case assigneesex when '0' then '男' when '1' then '女' when '2' then '未知' end), "
                +" assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass, "
                +" (case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end), "
                +" (case getmode when '1' then '一次统一给付' when '2' then '按年金方式领取' when '3' then '分期支付' end), "
                +" accepteddate,ApplyDate,Rgtantmobile,postcode,(select ReAffixDate from LLAffix where rgtno=llregister.rgtno and rownum=1) "
                +" from llregister where 1=1 "
                +" and rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql41");
		mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
    
    //prompt("",strSQL2);

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
    //fm.rgtType.value = RptContent[0][18];
    fm.RgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
    fm.GetMode.value = RptContent[0][21]; 
    fm.AcceptedDate.value = RptContent[0][22]; 
    fm.ApplyDate.value = RptContent[0][23];
    fm.RptorMoPhone.value = RptContent[0][24];
    fm.AppntZipCode.value = RptContent[0][25];
    fm.AddAffixAccDate.value = RptContent[0][26];
    //showOneCodeName('llrgttype','rgtType','rgtTypeTypeName');
    showOneCodeName('relation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论
    fm.ModifyRgtState.value = RptContent[0][18];//案件类型 Modify by zhaorx 2006-04-17  
    showOneCodeName('llrgttype','ModifyRgtState','ModifyRgtStateName');
    

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
   /* var strSQL3 = " select CustomerNo,Name,"
                 + " Sex,"
                 + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                 + " Birthday,"
                 + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                 + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                 + " from LDPerson where "
                 + "CustomerNo in("
                 + "select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql42");
		mySql.addSubPara(rptNo); 
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    
    //查询赔案标志
    QueryClaimFlag();
    
    //查询审核结论
    queryAudit();
}

//审核结论查询
function queryAudit()
{
   /* var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "' and checktype='0'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql43");
		mySql.addSubPara(fm.ClmNo.value);
    //prompt("查询审核结论sql",strSql);
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
        showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        //显示隐藏层
        choiseConclusionType();
        fm.printAuditRpt.disabled = false;
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
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//	  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-23
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
	  var rptNo = fm.RptNo.value;//赔案号
	
	  //Added by niuzj,2005-11-07
	  //加入一个参数,用于控制"重点信息修改"页面上的"修改确认"按钮
	  var tIsShow=0;  //为0时该按钮能使用,为1时该按钮不能使用
	  
    var strUrl="LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo+"&IsShow="+tIsShow;
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
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//	  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-23
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

	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
	
	var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&flag='1'";
//	var strUrl="LLLClaimQueryInput.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&flag='1'";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//受益人分配
function showBnf()
{

	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }
	  
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql44");
		mySql.addSubPara(tClaimNo);
    var arr = easyExecSql( mySql.getString() );    
    var tSumDutyKind = arr[0][0];


    //查询保项层面医疗类理赔类型的金额
    /*tSql = " select nvl(sum(a.RealPay),0) from LLClaimDetail a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       +" and a.GiveType in ('0') "       
       ;*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql45");
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
    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+document.all('customerNo').value;
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
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	/*======================================================
	 * 修改原因：添加二核是否完毕校验
	 * 修改人  ：万泽辉
	 * 修改时间：2005/12/16 15:40
	 ======================================================
	 */
//	if(!checkSecondClaim())
//	{
//		 return;
//	}
	 
//检查是否存在二核的加费信息，如有必须核销(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//此处调用公有方法
//    }
	 
    var strUrl="LLClaimContDealMain.jsp?ClmNo="+fm.ClmNo.value;
    strUrl= strUrl + "&CustNo=" + fm.customerNo.value;
    strUrl= strUrl + "&ConType="+fm.RgtClass.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//保单结算
function showPolDeal()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
//检查是否存在二核的加费信息，如有必须核销(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//此处调用公有方法
//    }
    
    //QC5117BUG,by niuzj,2005-12-09
   // var strSql = " select count(1) from llclaim t where t.clmno='"+fm.RptNo.value+"' ";	
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql46");
		mySql.addSubPara(fm.RptNo.value);
    var tCount = easyExecSql(mySql.getString());
    if(tCount == 0)
    {
    	alert("没有匹配理算信息,不允许进行保单结算操作！");
    	return;
    }
    else
    {
      var strUrl="LLClaimPolDealMain.jsp?ClmNo="+fm.ClmNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}

//查询出险结果
function queryResult(tCode,tName)
{
   /* var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql47");
		mySql.addSubPara(document.all(tCode).value);
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//检查保项金额调整,只能调小
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tRealM = parseFloat(PolDutyCodeGrid.getRowColData(i,10));
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//险种编码
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));//责任编码
        var tAdjM = parseFloat(fm.RealPay.value);
        var tPolno = PolDutyCodeGrid.getRowColData(i,1);//险种保单号PolNo
        //var strPSQL = "select riskamnt from lcpol where polno='"+tPolno+"'";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql48");
		mySql.addSubPara(tPolno);
        var arrp= easyExecSql(mySql.getString());
        
        if(arrp[0][0]== null){
        	alert("风险保额不存在");
        }
        
        //var strSQL = "select 1 from lmrisksort where riskcode='"+tRiskCode+"' and risksorttype='26' and risksortvalue='"+tGetDutyCode+"'";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql49");
		mySql.addSubPara(tRiskCode);
		mySql.addSubPara(tGetDutyCode);
        //prompt("校验调整保项是否是津贴型:",strSQL);
        var arr= easyExecSql(mySql.getString());
        
        //放开对津贴型险种的限制
        if(arr == null){
            if (arrp[0][0] < tAdjM)
            {
                alert("调整金额不能大于风险保额!");
                fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
                return false;
            }
        }
  
    }
    
    return true;
}

//打印赔案审核报告----2005-08-12添加
function LLPRTClaimAuditiReport()
{
//	alert("打印赔案审核报告");
	fm.action="LLPRTClaimAuditiReportSave.jsp";
	fm.target = "../f1print";
	document.getElementById("fm").submit();
}


//补缴保费通知书----2005-08-16添加
//修改,niuzj 20050921,原SQL语句存在错误trim(t.code),先改为trim(code)
function LLPRTPatchFeePrint()
{
    /*var strSql = " select count(1) from loprtmanager where 1=1 "
                + " and otherno='" + fm.ClmNo.value + "'"
                + " and trim(code)='PCT008'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql50");
		mySql.addSubPara(fm.ClmNo.value);
		
    var tCount = easyExecSql(mySql.getString());
    
    if (tCount == 0)
    {
        alert("没有可打印的数据!");
        return;
    }
    fm.action="LLPRTPatchFeeSave.jsp";
//	fm.target = "../f1print";
//	document.getElementById("fm").submit();
    if(beforePrtCheck(fm.ClmNo.value,"PCT008")=="false")
    {
    	return;
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
   /* var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";*/
	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql51");
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
		fm.PrtSeq.value=arrLoprt[0][0];
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
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}

//打印补充单证通知书
function prtDelayRgt()
{
   /* var strSql = " select count(1) from loprtmanager where 1=1 "
                + " and otherno='" + fm.ClmNo.value + "'"
                + " and trim(code)='PCT003'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql52");
		mySql.addSubPara(fm.ClmNo.value);
		
    var tCount = easyExecSql(mySql.getString());
    
    if (tCount == 0)
    {
        alert("没有可打印的数据!");
        return;
    }
    //以下填写打印提交内容
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT003")=="false")
    {
    	return;
    }
}

//单证批量打印控制
function showPrtControl()
{
    var tClmNo = fm.ClmNo.value;
	/*var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";*/
	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql53");
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

//收件人信息录入,Added by niuzj,2005-10-27
function showReciInfoInp()
{
	var tClmNo = fm.ClmNo.value;   //赔案号
	var tcustomerNo = fm.customerNo.value;  //出险人代码
	var tIsShow = 1;               //[保存]按钮能否显示,0-不显示,1-显示
  var tRgtObj = 1;               //个险团险标志,1-个险,2-团险
	
	//用弹出页时,最好写一个Main页面
	var strUrl="LLClaimReciInfoMain.jsp?ClmNo="+tClmNo+"&IsShow="+tIsShow+"&RgtObj="+tRgtObj+"&CustomerNo="+tcustomerNo;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
	//location.href="LLClaimReciInfoInput.jsp?claimNo="+tClmNo; 
}

//初始化二核结论按钮,如果二核回复，该按钮变亮;如果二核未回复,该按钮变灰
function initSecondUWResult()
{
	var tClmNo = fm.ClmNo.value;   //赔案号	
	//var strSQL = "select distinct 1 from llcuwbatch where caseno = '"+tClmNo+"' and state = '0'";
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql54");
		mySql.addSubPara(tClmNo);
	var tCount = easyExecSql(mySql.getString());
	
  //如果二核没有回复,Button"二核结论"应该灰掉
	if(tCount == "1")
	{
      fm.SecondUWResult.disabled = "true";
  }
  
	/*var strSQL1 = "select distinct 1 from llcuwbatch where caseno = '"+tClmNo+"'"
	            + " and InEffectFlag <> '1' and  InEffectFlag <> '2' and state = '1'";*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql55");
		mySql.addSubPara(tClmNo);       
	var tCount1 = easyExecSql(mySql.getString());   

	if(tCount1 == "1")    
	{
		  fm.SecondUW.disabled = "true";
	}   
}

//查询治疗医院，Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
	  /*var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";*/
	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql56");
		mySql.addSubPara(document.all(tCode).value);       
	  var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

/*================================================================================
 * 修改原因：在合同处理前确认二核是否完毕
 * 修改人  ：万泽辉
 * 修改时间：2005/12/16/ 15:35
 *
 *================================================================================
 */

//取消此校验，程序中不调用此函数
function checkSecondClaim()
{

    //二核
   /* var strSql4 = "select InEffectFlag from LLCUWBatch where "
                + " caseno = '" + fm.RptNo.value + "'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql57");
		mySql.addSubPara(fm.RptNo.value);   
    var tState = easyExecSql(mySql.getString());
    
    if (tState)
    {

        for (var i = 0; i < tState.length; i++)
        {
            if (tState[i] == '0')
            {
                alert("发起的二核没有做是否生效处理!");
                return false;
            }
        }
    }
    
    return true;
}

/**
 * 2008-12-27
 * zhangzheng
 * 如果已经进行保单结算,合同处理,受益人分配,豁免处理则不能再从审核转向预付管理
 * 如果没有进行上述操作则也必须将发起的调查,呈报,单证处理完!
 * @return
 */
function checkPrePay()
{
	//问题件
    /*var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql58");
		mySql.addSubPara(fm.RptNo.value);   
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
                alert("问题件回销没有完成,不能转向预付管理!");
                return false;
            }
        }
    }
    
    //调查
   /* var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql59");
		mySql.addSubPara(fm.RptNo.value);   
    var tFiniFlag = easyExecSql(mySql.getString());
    //alert("tFiniFlag:"+tFiniFlag);
    
    if (tFiniFlag)
    {
        for (var i = 0; i < tFiniFlag.length; i++)
        {
            if (tFiniFlag[i] != '1')
            {
                alert("发起的调查没有完成,不能转向预付管理!");
                return false;
            }
        }
    }
    
    //呈报
   /* var strSql2 = "select SubState from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql60");
		mySql.addSubPara(fm.RptNo.value);   
    var tSubState = easyExecSql(mySql.getString());
    
    if (tSubState)
    {
        for (var i = 0; i < tSubState.length; i++)
        {
            if (tSubState[i] != '1')
            {
                alert("发起的呈报没有完成,不能转向预付管理!");
                return false;
            }
        }
    }
    
    
    //二核
   /* var strSql4 = "select InEffectFlag from LLCUWBatch where "
                + " caseno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql61");
		mySql.addSubPara(fm.RptNo.value);   
    var tState = easyExecSql(mySql.getString());
    
    if (tState)
    {
        alert("已发起了二核,不能转向预付管理!");
        return false;
    }
    
    
    //豁免
   /* var strSql4 = "select count(*) from LLExempt where "
                + " ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql62");
		mySql.addSubPara(fm.RptNo.value);   
    var tExempt = easyExecSql(mySql.getString());
    
    //alert("tExempt:"+tExempt);
    if (tExempt>0)
    {
        alert("已进行了豁免处理,不能转向预付管理!");
        return false;
    }
    
    //保单结算\合同处理是否完成
   /* var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql63");
		mySql.addSubPara(fm.RptNo.value);   
    var tFlag = easyExecSql(mySql.getString());

    if (tFlag)
    {
        if (tFlag[0][0] == '1')
        {
            alert("已进行保单结算操作,不能转向预付管理!");
            return false;
        }
        if (tFlag[0][1] == '1')
        {
            alert("已进行合同处理操作,不能转向预付管理!");
            return false;
        }
    }
    
    //校验受益人分配
    /*var tSQL = "select  nvl(count(*),0) from llclaimdutykind where 1=1 and substr(getdutykind,2,2)='09'"
             + getWherePart( 'ClmNo','ClmNo' );*/
  	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql64");
		mySql.addSubPara(fm.ClmNo.value);  
    var tDutyKind = easyExecSql(mySql.getString());
    //prompt("校验受益人分配:",tSQL);

    if (tDutyKind == 0||tDutyKind>1)
    {

         /*var strSql4 = "select bnfkind from llbnf where 1=1 and  bnfkind='A' "
                        + getWherePart( 'ClmNo','ClmNo' );*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql65");
		mySql.addSubPara(fm.ClmNo.value);  
         //prompt("校验是否进行了受益人分配:",strSql4);
         
         var tBNF = easyExecSql(mySql.getString());

         if (tBNF)
         {
             alert("已进行受益人分配,不能转向预付管理!");
             return false;
        }
    }
    else
    {
        alert("理赔类型只有豁免,不能转向预付管理!");
        return false;
    }
    //zy 2009-10-23 16:38 增加预付只能做一次的校验
    //var ySql ="select count(*) from ljagetclaim where otherno='"+fm.RptNo.value+"' and feeoperationtype='B' ";
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql66");
		mySql.addSubPara(fm.RptNo.value); 
    var bCount = easyExecSql(mySql.getString());
    if(bCount>=1)
    {
    	alert("该案件已做一次预付操作，不能再次预付，请核实");
    	return false;
    }
    
    return true;
}



//2008-12-27 zhangzheng 双击下拉框后的响应函数
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//是否预付
    if(cCodeName=="whetherpreconclusion"){
    	
    	if(fm.whetherPrePay.value=='01'){
    		
    		//alert("选择的是预付!");
    		
            if (!confirm("是否要对案件执行预付操作?"))
            {
                return;
            }
    		
            //调查,呈报,问题件,二核校验,保单结算,合同处理,受益人分配,豁免处理
            if (!checkPrePay())
            {
                return;
            } 
             
            location.href="../claim/LLClaimPrepayAuditMain.jsp?ClmNo="+fm.ClmNo.value+"&CustomerNo="+fm.customerNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&ActivityID="+fm.ActivityID.value+"&AuditPopedom="+document.all('AuditPopedom').value+"&RgtClass=1&RgtObj=1";
    	}
    	else{
    		
    		//alert("选择的是审核!");
    	}
    	
        return true;
    }
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
	}
	else
	{
	    document.all('MedicalAccidentDate').value="";
	    document.all('MedicalAccidentDate').disabled=true;
	}
	
	//当只存在医疗类型时,其他出险日期不准许录入
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
}

function clearAcc()//投连结算
{
	//如果没有进行理算，先进行理算
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql67");
	mySql.addSubPara(fm.RptNo.value);
    var tStateDuty = easyExecSql(mySql.getString());
    if (tStateDuty<1)
    {
    	showMatchDutyPay();//进行理算
    }
    
    //如果有其他未计价的则等待计价后才能结算
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql70");
		mySql.addSubPara(fm.RptNo.value);
    var count = easyExecSql(mySql.getString());
		if(count>0)
		{
			alert("有其他计价需要处理，请稍后再进行结算!");
			return;
		}

        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql68");
		mySql.addSubPara(fm.RptNo.value);
    var count = easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			//判断是否已经进行过结算
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.RptNo.value);
			var countLP=easyExecSql(mySql.getString());
			if(countLP)
			{			
				if(countLP[0][0]=='2'&&countLP[0][1])
				{
					alert("您已经进行过结算了!");
					return;
				}
				else
				{
					alert("您已经提交交易，请等待计价!");
					return;
				}			
			}
		}

	if(tTLFlag!="1")
	{
		alert("参与理算的没有投连险,不需要结算!");
		return;
	}

	mOperate="INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimClearAccSave.jsp";
    document.getElementById("fm").submit(); //提交	
}
//Benefit Breakdown
function benefit()
{
	
   var strUrl="LLBenefitMain.jsp?CustomerNo="+fm.customerNo.value+"";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

//Claim Check
function claimCheck()
{
	//alert(1);
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql71");
	mySql.addSubPara(fm.RptNo.value);
	var countLP=easyExecSql(mySql.getString());
	if(countLP!=null){
		
		var Contno = countLP[0][0];
	}else{
		myAlert("没有查询到保单号，请先进行匹配理算！");
		return;
	}
    var strUrl="LLClaimCheckMain.jsp?CustomerNo='"+fm.customerNo.value+"'&ContNo='"+Contno+"'&RgtNo='"+fm.ClmNo.value+"'";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

