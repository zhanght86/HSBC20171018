var showInfo;
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();

//[返回]按钮
function returnParent()
{
	top.close();
}

//[打印]按钮----根据不同的单证号调用不同的Save页面
function showPrtAffix()
{
	var tcode=fm.PrtCode.value;//单证号
	var tclmno=fm.ClmNo.value;//赔案号
	var tcustno=fm.CustNo.value;//客户号
	
	//《对于可以批打得单证》根据 客户号 和 赔案号 从打印管理表查询 打印流水号
	var strSql="select prtseq,otherno,code from loprtmanager where 1=1 "
				+" and otherno='"+tclmno+"'"
				+" and code='"+tcode+"'";
	var arrLoprt = easyExecSql(strSql);
	if(arrLoprt !=null)
	{
		var tprtseq=arrLoprt[0][0];//单证流水号
		fm.PrtSeq.value=arrLoprt[0][0];	
	}	
		
//	alert(tcode);		
	//单证签收清单----传入 赔案号、客户号-----在线打印
	if(tcode=="PCT004"){fm.action = './LLPRTCertificateSignforSave.jsp';}
	//调查报告通知书--
	else if(tcode=="PCT017")
	{
		tsel=LLInqApplyGrid.getSelNo()-1;
		if(tsel<0)
		{
			alert("请选择一个调查申请！");
			return;
		}
		else
		{
			fm.ClmNo3.value = LLInqApplyGrid.getRowColData(tsel,1);
        	fm.InqNo.value = LLInqApplyGrid.getRowColData(tsel,2);
		}
		fm.action = './LLPRTInteInqReportSave.jsp';
	}
	//调查任务通知书
	else if(tcode=="PCT018")
	{
		tsel=LLInqApplyGrid.getSelNo()-1;
		if(tsel<0)
		{
			alert("请选择一个调查申请！");
			return;
		}
		else
		{
			fm.ClmNo3.value = LLInqApplyGrid.getRowColData(tsel,1);
        	fm.InqNo.value = LLInqApplyGrid.getRowColData(tsel,2);
//        	fm.CustomerNo.value = LLInqApplyGrid.getRowColData(i,4);
		}
		fm.action = './LLPRTInteInqTaskSave.jsp';
	}
	//赔案审核报告---传入 赔案号、客户号-----在线打印
	else if(tcode=="PCT019"){fm.action = './LLPRTClaimAuditiReportSave.jsp';}
	//********************以上打印为在线打印，以下可以批打《打印管理表》**********************
	//鉴定提示通知----传入流水号
	else if(tcode=="PCT001"){fm.action = './LLPRTAppraisalSave.jsp';}
	//理赔单证通知书----传入流水号
	else if(tcode=="PCT002"){fm.action = './LLPRTCertificatePutOutSave.jsp';}
	//单证补充通知单[问题件]----传入流水号	
	else if(tcode=="PCT003"){fm.action = './LLPRTCertificateRenewSave.jsp';}
	//补缴保费通知书	----传入流水号
	else if(tcode=="PCT008"){fm.action = './LLPRTPatchFeeSave.jsp';}
	//理赔决定通知书－拒付[不予立案]----传入流水号
	else if(tcode=="PCT007"){fm.action = './LLPRTProtestNoRegisterSave.jsp';}
	//批单-合同处理批注	----传入流水号
	else if(tcode=="PCT013"){fm.action = './LLClaimEndCasePrintContSave.jsp';}
	//其他结案阶段打印的单证----传入流水号	
	else {fm.action = './LLClaimEndCaseParaPrint.jsp';}
//		return;
	fm.target = "../f1print";
	fm.submit();
}


function showDIVInqGrid()
{
	var tcode = fm.PrtCode.value;
	if(tcode=="PCT017" || tcode=="PCT018")
	{
		var strSql="select clmno,inqno,batno,customerno,inqrcode ,initdept,inqdept,inqper,inqstartdate,inqenddate from llinqapply where 1=1"
				+" and clmno= '"+ fm.ClaimNo.value +"' "
				+" order by clmno,inqno"
		var arr = easyExecSql(strSql);
		if(arr==null){alert("该赔案没有任何调查申请"); return;}
		displayMultiline(arr,LLInqApplyGrid);
		divLLInqApplyGrid.style.display ="";//调查申请信息
	}
	else
	{
		divLLInqApplyGrid.style.display ="none";
	}
}