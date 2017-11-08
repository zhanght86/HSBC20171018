//程序名称：LLDealUWsecond.js
//程序功能：二核结论
//创建日期：2003-03-27 15:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
var mySql = new SqlClass();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
// 未完成的二次核保合同单列表 
function initQueryLLUnfinishedContGrid()
{
	var tCaseNo = fm.CaseNo.value;	
	/*var strSQL = "select t.caseno,t.batno,t.contno,t.appntno,t.appntname,"
			 + " case t.state when '0' then '处理中' when '1' then '完成' end, "
			 + " case t.claimrelflag when '0' then '相关' when '1' then '无关' end,"
			 + " t.healthimpartno1,t.healthimpartno2,t.noimpartdesc,t.remark1,t.passflag,t.uwidea,"
			 + " (select r.username from lduser r where r.usercode in (select w.defaultoperator from lwmission w where w.missionprop1=t.caseno and activityid = '0000005505')),"
			 + " t.makedate"
			 + " from llcuwbatch t where 1=1 and t.state = '0'" 
			 + getWherePart('t.caseno' ,'CaseNo')
			 //+ " and exists (select 1 from lwmission where 1 = 1 and activityid = '0000005035' and processid = '0000000005'"
			 //+ " and (DefaultOperator = '" + fm.Operator.value + "' or MissionProp19 = '" + fm.Operator.value + "') and missionprop8 = '1')"
	         + " order by t.batno,t.contno";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql1");
	mySql.addSubPara(fm.CaseNo.value ); 	 
	//turnPage1.pageLineNum=5;
	turnPage1.queryModal(mySql.getString(), LLUnfinishedContGrid);
}

// 已经完成的二次核保合同单列表 
function initQueryLLContGrid()
{
	var tInsuredNo = fm.InsuredNo.value;
	var tCaseNo = fm.CaseNo.value;
	/*var strSQL = "select t.caseno,t.batno,t.contno,t.appntno,t.appntname"
		 + ",case t.state when '0' then '处理中' when '1' then '完成' end "
		 + ",case t.claimrelflag when '0' then '相关' when '1' then '无关' end"
		 + ",t.healthimpartno1,t.healthimpartno2,t.noimpartdesc,t.remark1,t.passflag,t.uwidea, "
		 + " (select r.username from lduser r where r.usercode=t.operator), "
		 + " t.makedate,case t.ineffectflag when '0' then '未处理' when '1' then '生效' when '2' then '不生效' when '3' then '撤消' end,InsuredNo,InsuredName "
		 + " from llcuwbatch t where 1=1 and t.state = '1' "  
         + getWherePart('t.caseno' ,'CaseNo')
         //+ " and exists (select 1 from lwmission where 1 = 1 and activityid = '0000005035' and processid = '0000000005'"
		 //+ " and (DefaultOperator = '" + fm.Operator.value + "' or MissionProp19 = '" + fm.Operator.value + "') and missionprop8 = '1')"
         + " order by t.batno,t.contno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql2");
	mySql.addSubPara(fm.CaseNo.value ); 
	//turnPage.pageLineNum=5;
	//turnPage.queryModal(strSQL, LLContGrid);
	turnPage2.queryModal(mySql.getString(), LLContGrid);
}

/**
 * 2009-01-19 zhangzheng
 * 初始化时查询工作流节点,为发放核保通知书准备数据
 */
function initLWMission()
{
	/*var tLWMissionSQL = " select MissionID,SubMissionID from lwmission "
        + " where 1 = 1 and missionprop1 = '"+document.all('CaseNo').value+"'";*/
        mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql3");
	mySql.addSubPara(document.all('CaseNo').value); 
	var tResult = easyExecSql(mySql.getString());
	if(tResult!=null)
	{
		document.all('MissionID').value = tResult[0][0];
		document.all('SubMissionID').value = tResult[0][1];
	}
}


function LLContGridClick()
{
	clearPage();
	//显示 发起时输入的合同信息
	var tSel = LLContGrid.getSelNo()-1;	
	fm.HealthImpartNo1.value = LLContGrid.getRowColData(tSel,8);	//投保书健康告知栏询问号
	fm.HealthImpartNo2.value = LLContGrid.getRowColData(tSel,9);	//体检健康告知栏询问号
	fm.NoImpartDesc.value    = LLContGrid.getRowColData(tSel,10);	//对应未告知情况
	fm.Remark1.value         = LLContGrid.getRowColData(tSel,11);	//出险人目前健康状况介绍
	
	fm.CaseNo.value			 = LLContGrid.getRowColData(tSel,1);	//赔案号
	fm.BatNo.value     		 = LLContGrid.getRowColData(tSel,2);	//批次号
	fm.ContNo.value   		 = LLContGrid.getRowColData(tSel,3);	//合同号码
	
	var tPassFlag = LLContGrid.getRowColData(tSel,16);

	//  查询 该合同该批次的核保结论
	/*var strBatContSql="select t.caseno,t.batno,t.contno,t.uwno,t.passflag,t.uwidea,t.operator,t.modifydate from llcuwsub t where 1=1"
			+" and t.caseno='"+ fm.CaseNo.value +"' "
			+" and t.batno='"+ fm.BatNo.value +"'"
			+" and t.contno='"+ fm.ContNo.value +"'"
			+" order by t.uwno desc"*/
	        mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql4");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.BatNo.value);
	mySql.addSubPara(fm.ContNo.value);
//	prompt("查询 该合同该批次的核保结论",strBatContSql);
	var arr=easyExecSql(mySql.getString());	
	if(arr){
		fm.PassFlag.value = arr[0][4];	//合同核保结论
		fm.UWIdea.value   = arr[0][5];	//合同核保意见
		showOneCodeName('lluwsign','PassFlag','PassFlagname');   
	 	fm.UWOperator.value = arr[0][6];	//核保人
		fm.UWDate.value     = arr[0][7];	//核保日期
	}
	
	//查询该合同最近的 核保结果（结论、意见）
	/*var strContSql="select passflag,uwidea,operator,modifydate from llcuwmaster where 1=1 "
			+" and caseno='"+ fm.CaseNo.value +"' "
			+" and contno='"+ fm.ContNo.value +"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql5");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.ContNo.value);
	
	var arrCont=easyExecSql(mySql.getString());	
    if(arrCont==null)
	{
	 	fm.uwContState.value="";	
		fm.uwContIdea.value="";	
	}
	else
	{
	 	fm.uwContState.value  = arrCont[0][0];	//最近合同核保结论
	 	fm.uwContState1.value = arrCont[0][0];	//	 	
		fm.uwContIdea.value   = arrCont[0][1];	//	最近合同核保意见
		showOneCodeName('lluwsign','uwContState','uwContStatename');  
		fm.UWContOperator.value = arrCont[0][2];	//核保人
		fm.UWContDate.value     = arrCont[0][3];	//核保日期
    }
    
	//[查询 该合同下的险种核保信息]
	/*var strSQL="select a.caseno,a.contno,a.proposalcontno,a.polno,"
	            +" c.riskcode,c.riskname,a.uwno,a.passflag,(select codename from "
	            +" ldcode where codetype='claimuwstate' and code=a.passflag),a.uwidea"
				+" from lluwmaster a,lcpol b,lmrisk c where 1=1 "
				+" and a.polno=b.polno and b.riskcode=c.riskcode"
				+" and a.caseno='"+fm.CaseNo.value+"' "
				+" and a.contno='"+fm.ContNo.value+"' "
				+" order by a.caseno,a.contno,a.polno";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql6");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.ContNo.value);
	var arr=easyExecSql(mySql.getString());	
    if(arr==null)
    {
    	LLPolGrid.clearData();
    }
	else
	{
		displayMultiline(arr, LLPolGrid);
	}
	
	//var tReasonSql = "select Remark2 from llcuwbatch where CaseNo = '"+fm.CaseNo.value+"' and batno='"+fm.BatNo.value+"'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql7");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.BatNo.value);
	var tReason = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(tReason){
		fm.UWRisk2.value = tReason[0][0];	//返回理赔原因
	}
}

function clearPage()
{
 	fm.uwContState.value="";
 	fm.uwContStatename.value="";	
	fm.uwContIdea.value="";	
	fm.UWRiskState.value="";
	fm.UWRiskStateName.value="";
	fm.UWRiskIdea.value="";
	fm.HealthImpartNo1.value="";
	fm.HealthImpartNo2.value="";
	fm.NoImpartDesc.value="";
	fm.Remark1.value="";
	fm.PassFlag.value="";
	fm.PassFlagname.value="";
	fm.UWIdea.value="";
	fm.UWOperator.value="";
	fm.UWDate.value="";
	fm.UWContOperator.value="";
	fm.UWContDate.value="";	
}

/**=========================================================================
    增加生效按钮
    修改人：张星
    修改日期：2005.11.08
   =========================================================================
**/
function Cvalidate()
{
	lockScreen('Pol');
	var tSelNo = LLContGrid.getSelNo();
	if(tSelNo < 1)
	{
		alert("请选择一条保单信息");
		unlockScreen('Pol');
		return;
	}

	if(!confirm("您确定要做[生效]处理吗？"))
	{
		unlockScreen('Pol');
		return;
	}
    
    //校验是否发放通知书
    if(!checkTakeBackAllNotice())
    {
    	unlockScreen('Pol');
    	return false;
    }
    
    fm.CaseNo.valueo     = LLContGrid.getRowColData(tSelNo-1,1);//赔案号	
	fm.BatNo.value      = LLContGrid.getRowColData(tSelNo-1,2);//批次号	
    fm.ContNo.value     = LLContGrid.getRowColData(tSelNo-1,3);//合同号码       
    fm.InEffectFlag.value = "1";//生效标志X不起作用，1生效，2不生效，3撤消
    
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator=1";
	document.getElementById("fm").submit(); 
}

/**=========================================================================
    增加不生效按钮
    修改人：张星
    修改日期：2005.11.08
   =========================================================================
**/
function NotCvalidate()
{
	lockScreen('Pol');    	
	var tSelNo = LLContGrid.getSelNo();
    if(tSelNo < 1)
    {
		alert("请选择一条保单信息")
		return;
    }

	if(!confirm("您确定要做[不生效]处理吗？"))
	{
		unlockScreen('Pol');
		return;
	}
    
	//校验是否发放通知书
    if(!checkTakeBackAllNotice())
    {
    	unlockScreen('Pol');
    	return false;
    }
    
    fm.CaseNo.value = LLContGrid.getRowColData(tSelNo-1,1);//赔案号	
	fm.BatNo.value  = LLContGrid.getRowColData(tSelNo-1,2);//批次号	
	fm.ContNo.value = LLContGrid.getRowColData(tSelNo-1,3);//合同号码 		
	fm.InEffectFlag.value = "2";//生效标志:X不起作用，1生效，2不生效，3撤消
	
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	
	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator=1";
	document.getElementById("fm").submit();
}

//发起二核
function showSecondUWInput()
{
	var tSelNo = LLContGrid.getSelNo();
    if(tSelNo < 1)
    {
		alert("请选择一条保单信息")
		return;
    }
    fm.CaseNo.value      = LLContGrid.getRowColData(tSelNo-1,1);//赔案号
    fm.InsuredNo.value   = LLContGrid.getRowColData(tSelNo-1,17);//赔案号
    fm.InsuredName.value = LLContGrid.getRowColData(tSelNo-1,18);//赔案号
	//if(!KillTwoWindows(fm.CaseNo.value,'30'))
	//{
	//	return false;
	//}	
	
	//检查是否存在二核的加费信息，如有必须核销
    if (!checkLjspay(fm.CaseNo.value))
    {
        return;
    }

	var strUrl="LLSecondUWMain.jsp?CaseNo="+fm.CaseNo.value+"&InsuredNo="+fm.InsuredNo.value+"&InsuredName="+fm.InsuredName.value+"&MissionID="+fm.MissionID.value+"&RptorName= ";    
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{	
	unlockScreen('Pol');
	showInfo.close();
	if( FlagStr == "Fail" )
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
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initForm();
	}
}

//险种核保结论具体信息----结论、核保意见
function LLPolGridClick()
{
	fm.UWRiskState.value="";
	fm.UWRiskStateName.value="";
	fm.UWRiskIdea.value="";
	var tSel = LLPolGrid.getSelNo()-1;	
	fm.UWRiskState.value=LLPolGrid.getRowColData(tSel,8);
	fm.UWRiskIdea.value=LLPolGrid.getRowColData(tSel,10);	//险种核保意见
	showOneCodeName('uwstate','UWRiskState','UWRiskStateName');   	
}

function turnback1()
{
	if(fm.Flag.value == "Y"){
		location.href="LLDealUWInput.jsp";
	}else{
		top.close();
	}
}

/**=========================================================================
    修改状态：开始
    修改原因：添加“加费承保查询”按钮
    修 改 人：万泽辉
    修改日期：2005.11.02
   =========================================================================
**/
function LLUWAddFeeQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
     
	     var tClmNo     = LLUnfinishedContGrid.getRowColData(row-1,1);//赔案号
	     var tBatNo     = LLUnfinishedContGrid.getRowColData(row-1,2);//批次号
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//合同号码
	     var tInsuredNo = LLUnfinishedContGrid.getRowColData(row-1,4);//投保人号码
         var tQueryFlag = 1;//0非查询，1查询 	
         var tUrl = "../claimnb/LLUWAddFeeMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo1     = LLContGrid.getRowColData(row1-1,1);//赔案号
	     var tBatNo1     = LLContGrid.getRowColData(row1-1,2);//批次号
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//合同号码
	     var tInsuredNo1 = LLContGrid.getRowColData(row1-1,4);//投保人号码
         var tQueryFlag1 = 1;//0非查询，1查询 	
         var tUrl1 = "../claimnb/LLUWAddFeeMain.jsp?ClmNo="+tClmNo1+"&ContNo="+tContNo1+"&BatNo="+tBatNo1+"&InsuredNo="+tInsuredNo1+"&QueryFlag="+tQueryFlag1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("请选择一条！");  
}

/**=========================================================================
    修改状态：开始
    修改原因：添加“特约承保查询”按钮
    修 改 人：万泽辉
    修改日期：2005.11.25
   =========================================================================
**/
function LLUWSpecQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
     
	     var tClmNo     = LLUnfinishedContGrid.getRowColData(row-1,1);//赔案号
	     var tBatNo     = LLUnfinishedContGrid.getRowColData(row-1,2);//批次号
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//合同号码
	     var tInsuredNo = LLUnfinishedContGrid.getRowColData(row-1,4);//投保人号码
         var tQueryFlag = 1;//0非查询，1查询 	
         var tUrl = "../claimnb/LLUWSpecMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo1     = LLContGrid.getRowColData(row1-1,1);//赔案号
	     var tBatNo1     = LLContGrid.getRowColData(row1-1,2);//批次号
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//合同号码
	     var tInsuredNo1 = LLContGrid.getRowColData(row1-1,4);//投保人号码
         var tQueryFlag1 = 1;//0非查询，1查询 	
         var tUrl1 = "../claimnb/LLUWSpecMain.jsp?ClmNo="+tClmNo1+"&ContNo="+tContNo1+"&BatNo="+tBatNo1+"&InsuredNo="+tInsuredNo1+"&QueryFlag="+tQueryFlag1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("请选择一条！");  
}

/**=========================================================================
    修改状态：开始
    修改原因：添加“核保通知书查询”按钮
    修 改 人：万泽辉
    修改日期：2005.12.05
   =========================================================================
**/
function LLUWNoticeQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
     
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//合同号码
	     var tUrl = "../claimnb/LLUWNoticeQueryMain.jsp?ContNo="+tContNo;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//合同号码
	     var tUrl1 = "../claimnb/LLUWNoticeQueryMain.jsp?ContNo="+tContNo1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("请选择一条！");  
}


/**=========================================================================
    修改状态：开始
    修改原因：添加“加费应收信息查询”按钮
    修 改 人：万泽辉
    修改日期：2006.01.05
   =========================================================================
**/
function LLUWLJSPayQuery()
{
	 var row  = LLUnfinishedContGrid.getSelNo();
     if(row >= 1)
     {
         var tClmNo     = LLUnfinishedContGrid.getRowColData(row-1,1);//赔案号
	     var tContNo    = LLUnfinishedContGrid.getRowColData(row-1,3);//合同号码
	     var tUrl = "../claimnb/LLUWLJSPayQueryMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo;
         window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
     var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo1     = LLContGrid.getRowColData(row1-1,1);//赔案号
	     var tContNo1    = LLContGrid.getRowColData(row1-1,3);//合同号码
	     var tUrl1 = "../claimnb/LLUWLJSPayQueryMain.jsp?ClmNo="+tClmNo1+"&ContNo="+tContNo1;
         window.open(tUrl1,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
     if(row < 1 && row1 < 1)
         alert("请选择一条！");  
}

/**=========================================================================
    修改原因：案件回退涉及的应收记录的核销
    修 改 人：万泽辉
    修改日期：2006-01-05 17:42
   =========================================================================
**/
function CaseFeeCancel()
{
	 if(!confirm("客户交纳了暂交费后，执行此操作将把核保加费的应收信息转成实收信息。您确定要执行此操作吗？"))
     {
   	    return;
     }
	 var row1 = LLContGrid.getSelNo();
     if(row1 >= 1)
     {
	     var tClmNo = LLContGrid.getRowColData(row1-1,1);//赔案号
	     fm.action  = "./LLCaseBackFeeSave.jsp?ClmNo="+tClmNo;
         submitForm();
     }
     else
     {
     	 alert("请选择一条记录！");  
     }
}

/**=========================================================================
    修改原因：添加公共提交方法
    修 改 人：万泽辉
    修改日期：2006-01-05 17:42
   =========================================================================
**/
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
    document.getElementById("fm").submit(); //提交
}

/**=========================================================================
    修改原因：核保加费核销
    修改人  ：万泽辉
    修改日期：2005.11.08
   =========================================================================
**/
function AddFeeCancel()
{	
	var tSelNo = LLContGrid.getSelNo();
	if(tSelNo < 1)
	{
		alert("请选择一条保单信息！")
		return;
	}	
    /* if(!confirm("客户如果不交费，执行操作将直接删除核保加费的信息，此操作不可恢复。您确定要执行此操作吗？"))
    {
   	    return;
    } */
    var tCaseNo     = LLContGrid.getRowColData(tSelNo-1,1);//赔案号	
	var tBatNo      = LLContGrid.getRowColData(tSelNo-1,2);//批次号	
    var tContNo     = LLContGrid.getRowColData(tSelNo-1,3);//合同号码 
    fm.CaseNo.value = tCaseNo;
    fm.ContNo.value = tContNo;
    fm.BatNo.value  = tBatNo;
  
    //生效标志
    fm.InEffectFlag.value = "X";//X不起作用，1生效，2不生效，3撤消
    var mOperator = "AddFeeCancel";
        
	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator="+mOperator;
    submitForm();
    
}

/**=========================================================================
    增加撤消按钮
    修改人：续涛
    修改日期：20065.02.10
   =========================================================================
**/
function UWConclusionCancel()
{
   	  if(!confirm("您确定要做[撤消]处理吗，如果选择了该操作，核保结论将不生效，加费数据将不生成!!!"))
   	  {
   	    return;
      }
    
      //校验是否发放通知书
      if(!checkTakeBackAllNotice())
      {
    	return false;
      }
      
	  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  var tSelNo = LLContGrid.getSelNo();
	  if(tSelNo < 1)
	  {
			alert("请选择一条保单信息")
			return;
	  }	
	  var tCaseNo     = LLContGrid.getRowColData(tSelNo-1,1);//赔案号	
	  var tBatNo      = LLContGrid.getRowColData(tSelNo-1,2);//批次号	
	  var tContNo     = LLContGrid.getRowColData(tSelNo-1,3);//合同号码 
	  fm.CaseNo.value = tCaseNo;
	  fm.ContNo.value = tContNo;
	  fm.BatNo.value  = tBatNo;
	  
	  //生效标志
	  fm.InEffectFlag.value = "3";//X不起作用，1生效，2不生效，3撤消
	 
	  if (tCaseNo != "" && tContNo != "" )
	  {	 	
		  	fm.action = "../claimnb/LLUWResultCvalidateSave.jsp?Operator=1";
		    document.getElementById("fm").submit();
	  }
}
/**
 * 2009-01-22 zhangzheng
 * 校验是否发放通知书
 */
function checkSendAllNotice()
{
	/*var tcheckSQL = " select activitystatus from lwmission "
        + " where  missionprop1 = '"+document.all('CaseNo').value+"' and activityid='0000005505' ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql8");
	mySql.addSubPara(document.all('CaseNo').value); 

	//prompt("校验是否发放通知书sql",tcheckSQL);
	var tResult = easyExecSql(mySql.getString());
	if(tResult!=null&&tResult[0]==3)
	{
		alert("案件还有没回收的通知书,不能完成二核处理!");
		return false
	}
	
	return true;
}

/**
 * 2009-01-15 zhangzheng 
 * 发放核保通知书
 */
function SendAllNotice(){
	var tSelNo = LLContGrid.getSelNo();
	if(tSelNo < 1)
	{
		alert("请选择一条已完成核保的保单信息")
		return;
	}	
	var tCaseNo     = LLContGrid.getRowColData(tSelNo-1,1);//赔案号	
	var tBatNo      = LLContGrid.getRowColData(tSelNo-1,2);//批次号	
	var tContNo     = LLContGrid.getRowColData(tSelNo-1,3);//合同号码 
	//var tCheckSql = "select 1 from llcuwmaster where caseno='"+tCaseNo+"' and batno='"+tBatNo+"' and contno='"+tContNo+"' and passflag='1'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql9");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tBatNo); 
	mySql.addSubPara(tContNo); 
	var arrNUW=easyExecSql(mySql.getString());//prompt("",tSql);
	if(arrNUW){
		alert("本保单下了拒保结论，无法发送核保通知书");
		return false;
	}
	//var tCheckSql2 = "select 1 from llcuwmaster where caseno='"+tCaseNo+"' and batno='"+tBatNo+"' and contno='"+tContNo+"' and passflag='5'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql10");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tBatNo); 
	mySql.addSubPara(tContNo); 
	var arrNUW2=easyExecSql(mySql.getString());//prompt("",tSql);
	if(arrNUW2){
		alert("本保单未下保单级核保结论，无法发送核保通知书");
		return false;
	}
	//var tCheckSql3 = "select 1 from llcuwbatch where caseno='"+tCaseNo+"' and batno='"+tBatNo+"' and contno='"+tContNo+"' and ineffectflag!='0'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql11");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tBatNo); 
	mySql.addSubPara(tContNo); 
	var arrNUW3=easyExecSql(mySql.getString());
	//prompt("",tCheckSql3); 
	if(arrNUW3){
		alert("本保单已经做过生效、不生效或撤销处理，无法发送核保通知书");
		return false;
	}
	/*var tcheckSQL = " select activitystatus from lwmission "
        + " where  missionprop1 = '"+document.all('CaseNo').value+"' and activityid='0000005505' and missionprop2='"+tBatNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql12");
	mySql.addSubPara(document.all('CaseNo').value); 
	mySql.addSubPara(tBatNo); 
	
	//prompt("校验是否发放通知书sql",tcheckSQL);
	var tResult = easyExecSql(mySql.getString());
	if(tResult!=null&&tResult[0][0]==3)
	{
//		alert("通知书已经发送，无法再次发送!");
//		return false
	}
	//	alert(fm.ContNo.value);return false;
    //window.open("../claim/LLDealUWsecondSendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&EdorNo="+fm.EdorNo.value+"&EdorType="+tEdorType[0][0],"window1");
	//window.open("../claim/LLDealUWsecondSendAllNoticeMain.jsp?CaseNo="+document.all('CaseNo').value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value);
	  window.open("../uw/LLSendAllNoticeMain.jsp?MissionID="+fm.MissionID.value+"&ClmNo="+document.all('CaseNo').value+"&BatNo="+fm.BatNo.value+"&ContNo="+fm.ContNo.value,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");

}

/**
 * 05-12  增加校验 同一批次下的所有合同必须都没有已发送并未回收的通知书才能做生效、不生效、撤销处理
 * */
function beforeSubmit(){
	/*var tMissionSql = "select 1 from lwmission where missionprop1='"
		+ fm.CaseNo.value
		+"' and  activitystatus not in('1','2') and activityid='0000005505' and MissionProp2='"
		+ fm.BatNo.value
		+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLDealUWsecondInputSql");
	mySql.setSqlId("LLDealUWsecondSql13");
	mySql.addSubPara(fm.CaseNo.value); 
	mySql.addSubPara(fm.BatNo.value); 
	var arrMission=easyExecSql(mySql.getString());//prompt("",tSql);
	if(arrMission){
		alert("本次核保尚有保单处于通知书未回收状态，不能对本次核保的");
	}
}

/**
 * 校验是否回收通知书
 */
function checkTakeBackAllNotice()
{
//	var tcheckSQL = " select 'y' from lwmission "
//        + " where  missionprop1 = '"+document.all('CaseNo').value+"' and activityid "
//        + "in('0000005531','0000005533','0000005551','0000005553','0000005571','0000005573','0000005541','0000005544','0000005543','0000005534') ";
	
	sql_id = "LLDealUWsecondSql14";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLDealUWsecondInputSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(document.all('CaseNo').value);//指定传入的参数
	my_sql.addSubPara(document.all('BatNo').value);//指定传入的参数
	my_sql.addSubPara(document.all('ContNo').value);
	str_sql = my_sql.getString();
	var tResult = easyExecSql(str_sql);
	if(tResult!=null&&tResult[0][0]=='y')
	{
		alert("案件还有没回收的通知书,不能完成二核处理!");
		return false
	}
	
	return true;
}