var showInfo;
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
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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


        //更新列表信息
        LLClaimQuery();
        fm.addSave.disabled = true;
    }
    
	unlockScreen('lp002screen');
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
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

//[保存]按钮对应操作
function saveClick()
{
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("请选择需要处理的赔案!");
    	  return;
    }
	lockScreen('lp002screen');
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimEndCaseSave.jsp';
    submitForm();
}

//[结案确认]按钮对应操作
function EndSaveClick()
{
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("请选择需要处理的赔案!");
    	  return;
    }
    if (fm.ClmState.value != "60")
    {
        alert("请先做结案保存!");
        return;
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
   /* var strSQL = "select clmno,clmstate,givetype,clmuwer,endcasedate "
               + " from llclaim "
               + " where clmno = '"+fm.ClaimNo.value+"' "
               + " order by clmno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql1");
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
}


//与赔案相关的客户信息列表
function LLReCustomerGridQuery()
{
	var rptno=fm.ClmNo.value;
//	var strSQL = "select customerno,name,sex,birthday,vipvalue from ldperson where "
//	        + "customerno in("
//	        + "select customerno from llsubreport where subrptno = '"+ rptno +"')";
	
   /* var strSQL = "select CustomerNo,Name," 
	    	   + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
	    	   + " Birthday,"
	    	   + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
	    	   + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
	    	   + " from LDPerson where "
	    	   + " CustomerNo in( select CustomerNo from LLCase where CaseNo = '"+ rptno +"')";*/
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql2");
	mySql.addSubPara(rptno); 
	//prompt("strSQL",strSQL);
	easyQueryVer3Modal(mySql.getString(), LLReCustomerGrid);
}

//返回按钮
function goToBack()
{
    location.href="LLClaimEndCaseMissInput.jsp";
}

//[打印单证]按钮
function SinglePrtPrint()
{
    if(fm.PrtCode.value=="")
    {
    	alert("请选择一种单证!");
    	return;
    }
    if(fm.PrtCode.value=="PCT013")
    {
		fm.action="LLClaimEndCasePrintContSave.jsp";
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
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql3");
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
	 	if(tstateflag=="0")
	 	{
//			fm.action = './LLPRTCertificatePutOutSave.jsp';   //
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
   /* var strSql = " select nvl(count(1),0) from ljaget a where a.enteraccdate is null "
               + " and a.otherno = '" + fm.ClmNo.value + "' and operstate='0' ";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql4");
	mySql.addSubPara(fm.ClmNo.value);
    //prompt(" 财务实付查询",strSql);
    var tConfDate = easyExecSql(mySql.getString());
    if (tConfDate == "0")
    {
        fm.confGetMoney.value = "否";
    }
    else
    {
        fm.confGetMoney.value = "是";
    }
}