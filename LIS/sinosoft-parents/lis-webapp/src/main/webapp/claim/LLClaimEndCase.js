var showInfo;
var printflag=false;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//待处理
function zhoulei()
{
	alert("开发中!");
}

//提交数据
function submitForm()
{
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
//    showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT"
    fm.submit(); //提交
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lp001screen');
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
        //更新列表信息
        LLClaimQuery();

        //fm.confGetMoney.value='已付';
    }
        
    if (fm.ClmState.value == "60" && fm.EndCaseDate.value != "")
    {
        fm.addSave.disabled = true;
    }
    else
    {
        fm.addSave.disabled = false;
    }
    
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
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

//[保存]按钮对应操作
function saveClick()
{
	//如存在未处理的二核，给予系统提示，但不影响结案保存
	  /*var strSqlCuw = "select count(*) from LLCUWBatch where "
	              + " caseno = '" + fm.ClmNo.value + "' and InEffectFlag='0'" ;*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql1");
		mySql.addSubPara(fm.ClmNo.value ); 
	  var tCountcuw = easyExecSql(mySql.getString());

	  if (tCountcuw>0)
	    {     
		  if(!confirm("请注意，有未作处理的二核结论，您确认进行结案保存"))
	       {
	          return false;
	       }
	    }

	  if(!KillTwoWindows(fm.ClmNo.value,'50'))
	  {
	  	  return false;
	  }	
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("请选择需要处理的赔案!");
    	  return;
    }
    
	if(fm.opYZLX.value==""){
		alert("请选择是否计算延滞利息");
		return;
	}
	
	lockScreen('lp001screen');
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimEndCaseSave.jsp';
    submitForm();
}

//[保存]计算延迟利息按钮
function YZLX_compute()
{
	if (fm.ClmNo.value=="")
	{
		alert("请作赔案查询");
		return;
	}
	if(fm.opYZLX.value=="N")
	fm.fmtransact.value="YZLX_delete";
	else if(fm.opYZLX.value=="Y")
	fm.fmtransact.value="YZLX_compute";
	else{
		alert("请选择是否计算延滞利息");
		return false;
		}
	fm.btnYZLX.disabled=true;
		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";

	fm.action="./LLClaimYZLX.jsp";
	fm.target = "fraSubmit";
	submitForm();
	
}

//[结案确认]按钮对应操作
function EndSaveClick()
{
	if(!KillTwoWindows(fm.ClmNo.value,'60'))
	{
	  	  return false;
	}
	
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("请选择需要处理的赔案!");
    	  return;
    }
    if (fm.ClmState.value != "60" && fm.EndCaseDate.value == "")
    {
        alert("请先做结案保存!");
        return;
    }
    
    //给付确认前必须打印理赔给付批注或拒付通知书 	打印状态：0都可用，1在线已打，2批打已打，3批打不打,4批打未开放
   /* var strSql="select distinct t.stateflag from loprtmanager t where code in('PCT010','PCT006') "
		+" and t.otherno='"+fm.ClmNo.value+"' ";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql2");
		mySql.addSubPara(fm.ClmNo.value ); 
    //prompt("给付确认前必须打印理赔给付批注或拒付通知书",strSql);
    var arrLoprt = easyExecSql(mySql.getString());
    if(arrLoprt==null)
    {
    	alert("没有找到该单证的流水号");
    	return false;
    }	
    else 
    {
    	for(var i=0;i<arrLoprt[0].length;i++)
    	{
    		if(arrLoprt[0][i]!='1')
    		{
    			printflag=true;
    			break;
    		}
    	}
    }
    
    
    //校验必须打印理赔给付批注和合同处理批注
    if(printflag ==true)
    {
        alert("请先打印通知书!");
        return false;
    }   

    
//    //财务是否实付
//    var strSql = " select count(1) from ljaget a where a.enteraccdate is null "
//               + " and a.otherno = '" + fm.ClmNo.value + "'";
//    var tConfDate = easyExecSql(strSql);
//    if (tConfDate != "0")
//    {
//        alert("理赔金未实付完成,不能立案确认!");
//        return;
//    }
    fm.fmtransact.value = "";
    fm.action = './LLClaimEndCaseConfirmSave.jsp';
    fm.target="fraSubmit"
    submitForm();
}

//LLClaimGrid响应事件
function LLClaimGridClick()
{
	var i = LLClaimGrid.getSelNo();
    if (i != 0)
    {
        i = i - 1;
        fm.ClmNo.value = LLClaimGrid.getRowColData(i,1);
        fm.GetDutyKind.value = LLClaimGrid.getRowColData(i,2);
//        fm.ClmState.value = LLClaimGrid.getRowColData(i,3);
        fm.ClmState.value = '10';
        fm.StandPay.value = LLClaimGrid.getRowColData(i,4);
        fm.BeforePay.value = LLClaimGrid.getRowColData(i,5);
        fm.RealPay.value = LLClaimGrid.getRowColData(i,6);
        fm.GiveType.value = LLClaimGrid.getRowColData(i,7);
        fm.GiveTypeDesc.value = LLClaimGrid.getRowColData(i,8);
        fm.ClmUWer.value = LLClaimGrid.getRowColData(i,9);
        fm.DeclineNo.value = LLClaimGrid.getRowColData(i,10);
        fm.EndCaseDate.value = LLClaimGrid.getRowColData(i,11);
        fm.CasePayType.value = LLClaimGrid.getRowColData(i,12);
        fm.CheckType.value = LLClaimGrid.getRowColData(i,13);
        showOneCodeName('llclaimstate','ClmState','ClmStateName');
    }
}

//赔案查询
function LLClaimQuery()
{
    //---------------------1-------2---------3-------4----------5--
    /*var strSQL = "select clmno,clmstate,givetype,(select username from llclaimuser where usercode = llclaim.clmuwer),endcasedate "
               + " from llclaim "
               + " where clmno = '"+fm.ClaimNo.value+"' "
               + " order by clmno";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql3");
		mySql.addSubPara(fm.ClaimNo.value ); 
    var tClaim = easyExecSql(mySql.getString());
//    alert(tClaim);
    if (tClaim != null)
    {
        fm.ClmNo.value = tClaim[0][0];
        fm.ClmState.value = tClaim[0][1];
        fm.GiveType.value = tClaim[0][2];
        fm.ClmUWer.value = tClaim[0][3];
        fm.EndCaseDate.value = tClaim[0][4];
        showOneCodeName('llclaimstate','ClmState','ClmStateName');
        showOneCodeName('llclaimconclusion','GiveType','GiveTypeName');
    }
    
    if (fm.ClmState.value == "60" && fm.EndCaseDate.value != "")
    {
        fm.addSave.disabled = true;
    }
    
    //查询实付标志
    queryConfDate();
    
    //查询保项理算金额
    afterMatchDutyPayQuery();
    
    //查询是否计算延迟利息
    afterClaimYZLXBLQuery();
}


//与赔案相关的客户信息列表
function LLReCustomerGridQuery()
{
	var rptno=fm.ClmNo.value;
	/*var strSQL = " select customerno,name,"
	                   + " sex,"
	                   + " (case trim(Sex) when '0' then '男' when '1' then '女' else '不详' end) as SexNA,"
	                   + " birthday,"
                       + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                       + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
	                   + " from ldperson where "
	                   + " customerno in("
	                   + " select customerno from llsubreport where subrptno = '"+ rptno +"')";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql4");
		mySql.addSubPara(rptno ); 
//	alert("strSQL= "+strSQL);
	easyQueryVer3Modal(mySql.getString(), LLReCustomerGrid);
}

//返回按钮
function goToBack()
{
	
	if (fm.RgtObjNo.value!=null && fm.RgtObjNo.value!="")
  { 
    location.href="../claimgrp/LLGrpClaimEndCaseMissInput.jsp";
  }    
  else
  {
  	location.href="LLClaimEndCaseMissInput.jsp";
  }	

}

//[打印单证]按钮
function SinglePrtPrint()
{
	printflag=false;
	
	fm.prtType.value = fm.PrtCode.value;
    //如果没有结案保存，则不允许打印单证，by niuzj,2005-12-09
    var tClmState=fm.ClmState.value;
    if(tClmState != '60')	
    {
    	alert("没有结案保存，不允许打印单证！");
    	return false;
    }
    
    if(fm.PrtCode.value=="")
    {
    	alert("请选择一种单证!");
    	return;
    }
    if(fm.PrtCode.value=="PCT013")
    {
		fm.action="LLClaimEndCasePrintContSave.jsp";
    }
    else if(fm.PrtCode.value=="PCT005" || fm.PrtCode.value=="PCT006")
    {
        fm.action="LLClaimEndCaseProtestPrint.jsp";
    }
  	else
  	{
  		fm.action="LLClaimEndCaseParaPrint.jsp";
  	}
    fm.fmtransact.value="SinglePrt||Print";
    if(beforePrtCheck(fm.ClmNo.value,fm.PrtCode.value)==false)
    {
    	return;
    }
//    fm.target = "../f1print";
//	fm.submit();
}

//[批单打印]按钮-----“批单”打印（PrtSubType----B）
function BatchPrtPrintB()
{
	printflag=false;
	var tsel = LLReCustomerGrid.getSelNo()-1; 
    if(tsel<0)
    {
    	alert("请选择一条客户记录!");
    	return;
    }
     fm.CustomerNo.value=LLReCustomerGrid.getRowColData(tsel,1);
//    fm.fmtransact.value="BatchPrtB||Print";
//    fm.action="LLEndCaseParaPrint.jsp";
////    fm.target = "../f1print";
//    submitForm();    
}

//[清单打印]按钮-----“清单”打印（PrtSubType----C）
function BatchPrtPrintC()
{
	printflag=false;
	var tsel = LLReCustomerGrid.getSelNo()-1; 
    if(tsel<0)
    {
    	alert("请选择一条客户记录!");
    	return;
    }
    fm.CustomerNo.value=LLReCustomerGrid.getRowColData(tsel,1);    
//    fm.fmtransact.value="BatchPrtC||Print";
//    fm.action="LLEndCaseParaPrint.jsp";
////    fm.target = "../f1print";
}

//影像查询---------------2005-08-14添加
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+fm.all('ClmNo').value+"&subtype=LP1001";
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
//打印前检验（），需要传入参数（单证号码、赔案号）--------2005-08-22添加
***********************************/
function beforePrtCheck(clmno,code)
{
	//查询单证流水号，对应其它号码（赔案号）、单据类型、打印方式、打印状态、补打标志
	printflag=false;
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
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql5");
		mySql.addSubPara(tclmno ); 
		mySql.addSubPara(tcode ); 
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
			fm.submit();
	 	}
		else
		{
			//存在但已经打印过
			if(confirm("该单证已经打印完成，你确实要补打吗？"))
	 		{
	 			//进入补打原因录入页面
	 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value+"&prtType="+fm.prtType.value;
//	 			window.open(strUrl,"单证补打");
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}

//赔案查询
function queryClick()
{
    if (fm.ClaimNo.value == '')
    {
        alert("赔案号为空!");
        return;
    }
    var strUrl="LLClaimQueryInput.jsp?claimNo="+fm.ClaimNo.value+"&phase=0";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//财务实付查询
function queryConfDate()
{
    //查询所有实付纪录
   /* var strSql = " select count(1) from ljaget a where a.enteraccdate is null "
               + " and a.otherno = '" + fm.ClmNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql6");
		mySql.addSubPara(fm.ClmNo.value ); 
		
    var tConfDate = easyExecSql(mySql.getString());
    var tValue = "";
    if (tConfDate == "0")
    {
        tValue = "是";
    }
    else
    {
        tValue = "否";
    }
    
    //在关闭等状态时实付表无纪录
    /*var strSql2 = " select count(1) from ljaget a where 1=1 "
                + " and a.otherno = '" + fm.ClmNo.value + "'";*/
      mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql7");
		mySql.addSubPara(fm.ClmNo.value ); 
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        tValue = "否";
    }
    
    //fm.confGetMoney.value = tValue;
}

//单证批量打印控制
function showPrtControl()
{
	printflag=false;
    var tClmNo = fm.ClmNo.value;
	/*var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql8");
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
	var tsel = LLReCustomerGrid.getSelNo()-1;
	if(tsel<0)
  {
  	alert("请选择一条客户记录!");
  	return;
  }
	fm.CustomerNo.value=LLReCustomerGrid.getRowColData(tsel,1);
	var tcustomerNo=fm.CustomerNo.value;  //出险人代码
	var tIsShow = 1;               //[保存]按钮能否显示,0-不显示,1-显示
  var tRgtObj = 1;               //个险团险标志,1-个险,2-团险
	
	//用弹出页时,最好写一个Main页面
	var strUrl="LLClaimReciInfoMain.jsp?ClmNo="+tClmNo+"&IsShow="+tIsShow+"&RgtObj="+tRgtObj+"&CustomerNo="+tcustomerNo;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
	//location.href="LLClaimReciInfoInput.jsp?claimNo="+tClmNo; 
}


function afterCodeSelect(){
	if(fm.opYZLX.value=="N"){
		fm.btnYZLX.value="取消延滞利息";
		fm.btnYZLX.disabled=false;
		}
	else if(fm.opYZLX.value=="Y"){
		fm.btnYZLX.value="计算延滞利息";
		fm.btnYZLX.disabled=true;
		}
	else{
		fm.opYZLX.value="N";
		fm.btnYZLX.value="取消延滞利息";
		fm.btnYZLX.disabled=false;
//		alert("请选择是否计算延滞利息");
//		fm.btnYZLX.disabled=false;
		
//		return false;
		}
	fm.btnYZLX.disabled=false;
}

//初始化查询保项金额
//匹配后的查询
function afterMatchDutyPayQuery()
{
    var tSql;
    var arr = new Array;

    var tClaimNo = fm.ClmNo.value;         //赔案号
        

    //查询LLClaimDetail,查询保单理赔类型保项层面的信息
    ////to_char(b.PayEndDate,'yyyy-mm-dd')+a.GracePeriod," //交至日期 + 宽限期--+ a.GracePeriod
    /*tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
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
       +" and a.ClmNo = '"+tClaimNo+"'"   */
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql9");
		mySql.addSubPara(tClaimNo);   
    //prompt("查询保项给付金额sql:",tSql);
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


//查询是否计算延迟利息
function afterClaimYZLXBLQuery()
{
  var tSql;
  var arr = new Array;

  var tClaimNo = fm.ClmNo.value;         //赔案号
      
  //tSql = " select 1 from ljsgetclaim where otherno='"+tClaimNo+"' and feefinatype='YCLX'";   
 	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql10");
		mySql.addSubPara(tClaimNo);       
  //prompt("查询是否已经计算延迟利息sql:",tSql);
  arr = easyExecSql( mySql.getString() );
  if (arr)
  {
      fm.opYZLX.value='Y';
      fm.opYZLXName.value='是';
  }
 
}