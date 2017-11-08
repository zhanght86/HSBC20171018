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
//modify by niuzj,2005-10-20,在赔案打印中有的需要补打控制,有的不需要控制
function showPrtAffix()
{
	var tcode=fm.PrtCode.value;//单证号
	var tclmno=fm.ClmNo.value;//赔案号
	var tcustno=fm.CustNo.value;//客户号
	
	//《对于可以批打得单证》根据 客户号 和 赔案号 从打印管理表查询 打印流水号
//	var strSql="select prtseq,otherno,code from loprtmanager where 1=1 "
//				+" and otherno='"+tclmno+"'"
//				+" and code='"+tcode+"'";
	
	var sqlid1="LLClaimEndCaseAffixPrtSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("claim.LLClaimEndCaseAffixPrtSql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(tclmno);//指定传入参数
	 mySql1.addSubPara(tcode);//指定传入参数
	 var strSql = mySql1.getString();
	
	var arrLoprt = easyExecSql(strSql);
	if(arrLoprt !=null)
	{
		var tprtseq=arrLoprt[0][0];//单证流水号
		fm.PrtSeq.value=arrLoprt[0][0];	
	}	
		
  //	alert(tcode);		
	//单证签收清单----传入 赔案号、客户号-----在线打印,不需要补打控制
  if(tcode=="PCT004" || tcode=="PCT017" || tcode=="PCT018" || tcode=="PCT019")
  {
  	 if(tcode=="PCT004")
	   {
	   	fm.action = './LLPRTCertificateSignforSave.jsp';
	   }
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
//          	fm.CustomerNo.value = LLInqApplyGrid.getRowColData(i,4);
		  }
		  fm.action = './LLPRTInteInqTaskSave.jsp';
	    }
/*	  else if(tcode=="PCT020")
	  	{
	  		tsel = LLInqFeeGrid.getSelNo()-1;
	  		if(tsel<0)
	  		{
	  			alert("请选择一个调查申请！");
	  			return;
	  		}
	  	else
	  		{fm.ClmNo3.value = LLInqFeeGrid.getRowColData(tsel,1);
	  		 fm.InqNo.value = LLInqFeeGrid.getRowColData(tsel,2);
	  		}
	  		fm.action = './LLPRTInteInqPaySave.jsp';
	  	}
	  	*/
	    //赔案审核报告---传入 赔案号、客户号-----在线打印
	    else if(tcode=="PCT019")
	    {
	    	fm.action = './LLPRTClaimAuditiReportSave.jsp';
	    }
	    	fm.target = "../f1print";
	      fm.submit();
   }
   else  //需要补打控制
   {
   	   //********************以上打印为在线打印，以下可以批打《打印管理表》**********************
   	   //鉴定提示通知----传入流水号
   	   if(tcode=="PCT001")
   	   {
   	   	fm.action = './LLPRTAppraisalSave.jsp';
   	   }
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
   
       //单证补打时要进行校验,niuzj,2005-10-20
       if(beforePrtCheck(fm.ClmNo.value,fm.PrtCode.value)==false)
       {
        	return;
       }
       
	      //fm.target = "../f1print";
	      //fm.submit();
    }
}

/**********************************
//niuzj,2005-10-20
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
//    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
//			+" and t.otherno='"+tclmno+"' "
//			+" and trim(t.code)='"+tcode+"' ";
    
     var sqlid2="LLClaimEndCaseAffixPrtSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("claim.LLClaimEndCaseAffixPrtSql");
	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
	 mySql2.addSubPara(tclmno);//指定传入参数
	 mySql2.addSubPara(tcode);//指定传入参数
	 var strSql = mySql2.getString();
    
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
	 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
//	 			window.open(strUrl,"单证补打");
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}


function showDIVInqGrid()
{
	var tcode = fm.PrtCode.value;
	if(tcode=="PCT017" || tcode=="PCT018")
	{
//		var strSql="select clmno,inqno,batno,customerno,inqrcode ,initdept,inqdept,inqper,inqstartdate,inqenddate from llinqapply where 1=1"
//				+" and clmno = '"+ fm.ClaimNo.value +"' "
//				+" order by clmno,inqno";
		
		 var ClaimNo3 = fm.ClaimNo.value;
		 var sqlid3="LLClaimEndCaseAffixPrtSql3";
		 var mySql3=new SqlClass();
		 mySql3.setResourceName("claim.LLClaimEndCaseAffixPrtSql");
		 mySql3.setSqlId(sqlid3);//指定使用SQL的id
		 mySql3.addSubPara(ClaimNo3);//指定传入参数
		 var strSql = mySql3.getString();
		
		var arr = easyExecSql(strSql);
		if(arr==null){alert("该赔案没有任何调查申请"); return;}
		displayMultiline(arr,LLInqApplyGrid);
		divLLInqApplyGrid.style.display ="";//调查申请信息
		divLLInqFeeGrid.style.display ="none";
	}
   /* else if(tcode=="PCT020")
	{
        var strSql = "select clmno,payee,sum(feesum) from llinqfee where clmno = '" + fm.ClaimNo.value +"' "
                     + " group by payee,clmno"; 
		var arr = easyExecSql(strSql);
		if(arr==null){alert("该赔案没有任何查勘费用"); return;}
		displayMultiline(arr,LLInqFeeGrid);
		divLLInqFeeGrid.style.display ="";
		divLLInqApplyGrid.style.display ="none";
		
	}
	*/
	else
	{
		divLLInqApplyGrid.style.display ="none";
		divLLInqFeeGrid.style.display ="none";
	}
}
function LAAQSuggestShiftClick()
{
    var i = LLInqFeeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.Payee.value  = LLInqFeeGrid.getRowColData(i,2);//查勘人
                  
    }
}
