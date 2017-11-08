<%
//程序名称：TempFinFeeSave.jsp
//程序功能：
//创建日期：2009-11-14
//创建人  ：张斌
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.finfee.*"%>
	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  loggerDebug("CustomerYFSave","开始执行Save页面");
  
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getValue("GI"));
  
  TransferData mTransferData = new TransferData();
  
  LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
  LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
  
  //FinFeeUI mFinFeeUI = new FinFeeUI();
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  
  loggerDebug("CustomerYFSave","操作的类型是"+mOperateType);
  String FlagStr = "";
  String Content = "";
  String mDescType = "";	//将操作标志的英文转换成汉字的形式
  //公共信息
  String mManageCom				= request.getParameter("ManageCom");
  String mOperator 	 			= request.getParameter("Operator"	);
  String ActuGetNo5 = request.getParameter("ActuGetNo5"	);
  //财务交费信息
	String[] mTempNum				=	request.getParameterValues("FinFeeGridNo"	); 
	String[] mPayMode				=	request.getParameterValues("FinFeeGrid1"	); //交费方式
  String[] mChequeNo			=	request.getParameterValues("FinFeeGrid5"	); //支票号
  String[] mChequeDate  	=	request.getParameterValues("FinFeeGrid6"	); //支票日期
  ////支票开户银行
  String[] mBankCode    	=	request.getParameterValues("FinFeeGrid7"	);
  String[] mBankAccNo   	=	request.getParameterValues("FinFeeGrid9"	);
  String[] mAccName     	=	request.getParameterValues("FinFeeGrid10"	);
  ////收费银行
  String[] mInBankCode  	=	request.getParameterValues("FinFeeGrid11"	);
  String[] mInBankAccNo 	=	request.getParameterValues("FinFeeGrid12"	);
  String[] mInAccName   	=	request.getParameterValues("FinFeeGrid13"	);
  
  String[] mTempFeeNo			=	request.getParameterValues("FinFeeGrid18"	);
  String[] mPayUserName		=	request.getParameterValues("FinFeeGrid19"	);
  
  //转账
  //String[] mGetNoticeNo  	=	request.getParameterValues("FinFeeGrid17"	);
	//String[] mBenefiPerson=	request.getParameterValues("FinFeeGrid"	); //受益人
	
	//业务交费信息
  String[] mNum						=	request.getParameterValues("TempToGridNo"	); 
  String[] mGetNoticeNo 	=	request.getParameterValues("TempToGrid1"	); 		//应收号码
  String[] mTempFeeType 	=	request.getParameterValues("TempToGrid18"	); 		//交费类型
  String[] mPayDate 			=	request.getParameterValues("TempToGrid3"	); 		//交费日期
  String[] mPayMoney 			=	request.getParameterValues("TempToGrid4"	); 		//交费金额
  String[] mCurrency 			=	request.getParameterValues("TempToGrid5"	); 		//币种
  
  String[] mAgentGroup 		=	request.getParameterValues("TempToGrid9"	); 		//代理人组别
  String[] mAgentCode			=	request.getParameterValues("TempToGrid10"	); 		//代理人编码
  String[] mOtherNo 			=	request.getParameterValues("TempToGrid11"	); 		//业务号码
  String[] mOtherNoType		=	request.getParameterValues("TempToGrid12"	); 		//业务号码
  String[] mAPPntName 		=	request.getParameterValues("TempToGrid16"	); 		//投保人
  String[] mPolicyCom 		=	request.getParameterValues("TempToGrid7"	); 		//管理机构
	
  LJTempFeeClassSchema tLJTempFeeClassSchema;
  
  if(mOperateType.equals("INSERT")){
  	loggerDebug("CustomerYFSave","开始执行INSERT页面");
  	
	LJTempFeeSchema tLJTempFeeSchema ;
		if(mNum!=null){
			int tLength = mNum.length;
			String tLimit ;
			String tNo 		; 
			String serNo 	; 
  		for(int i = 0 ;i < tLength ;i++){
  			tLimit = PubFun.getNoLimit(mPolicyCom[i]);
  			loggerDebug("CustomerYFSave","11111111111111111111111111  mTempFeeNo: "+mTempFeeNo[0]);
  			if("".equals(mTempFeeNo[0])||mTempFeeNo[0]==null){
  				
  				tNo = "TS"+PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// 产生通知书号即暂交费号
  				loggerDebug("CustomerYFSave","aaaaaaaaaaaaaaaaaaaaaaaaaaaaa : "+tNo);
  			}else{
  				tNo = mTempFeeNo[0];
  				loggerDebug("CustomerYFSave","bbbbbbbbbbbbbbbbbbbbbbbbbbbbb : "+tNo);
  			}
  			
  			serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
  			
  			//财务收费
  			tLJTempFeeClassSchema = new LJTempFeeClassSchema();
  			tLJTempFeeClassSchema.setTempFeeNo(		tNo							);
  			tLJTempFeeClassSchema.setPayMode(	 		mPayMode[0]			);
		  	tLJTempFeeClassSchema.setChequeNo( 		mChequeNo[0]		); //票据号码
		  	tLJTempFeeClassSchema.setPayMoney(	 	mPayMoney[i]		); 
		  	tLJTempFeeClassSchema.setCurrency(	  mCurrency[i]		);
		  	tLJTempFeeClassSchema.setPayDate(			mPayDate[0]			);
		  	tLJTempFeeClassSchema.setChequeDate(	mChequeDate[0]	);
		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);
		  	tLJTempFeeClassSchema.setConfFlag(		"0"							);
		  	tLJTempFeeClassSchema.setSerialNo(		serNo						);
		  	tLJTempFeeClassSchema.setBankCode(		mBankCode[0]		);
		  	tLJTempFeeClassSchema.setBankAccNo(		mBankAccNo[0]		);
		  	tLJTempFeeClassSchema.setAccName(			mAccName[0]			);
		  	tLJTempFeeClassSchema.setInBankCode(	mInBankCode[0]	);
		  	tLJTempFeeClassSchema.setInBankAccNo(	mInBankAccNo[0]	);
		  	tLJTempFeeClassSchema.setInAccName(		mInAccName[0]		);
		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);
		  	tLJTempFeeClassSchema.setManageCom(		mManageCom			);
		  	tLJTempFeeClassSchema.setPolicyCom(		mPolicyCom[i]		);
		  	tLJTempFeeClassSchema.setOperator(		mOperator				);
		  	tLJTempFeeClassSchema.setOtherNo(			mOtherNo[i]			);
		  	tLJTempFeeClassSchema.setOtherNoType(	mOtherNoType[i]	);
		  	
		 // 	tLJTempFeeClassSchema.setPayUserName(	mPayUserName[0]	);
		  	
		  	mLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		  	
  			//财务收费
  			tLJTempFeeSchema = new LJTempFeeSchema();
  			tLJTempFeeSchema.setTempFeeNo(		tNo									);
  			tLJTempFeeSchema.setTempFeeType(	mTempFeeType[i]			);
  			tLJTempFeeSchema.setRiskCode(	 		"000000"						);
  			tLJTempFeeSchema.setOtherNo(			mOtherNo[i]					);
  			tLJTempFeeSchema.setOtherNoType(	mOtherNoType[i]			);
  			tLJTempFeeSchema.setPayMoney(			mPayMoney[i]				);
  			tLJTempFeeSchema.setPayDate(			mPayDate[i]					);
  			tLJTempFeeSchema.setEnterAccDate(	mPayDate[0]					);
  			tLJTempFeeSchema.setSerialNo(			serNo								);
  			tLJTempFeeSchema.setConfFlag(			"0"									);
  			tLJTempFeeClassSchema.setSerialNo(serNo								);
  			tLJTempFeeSchema.setManageCom(		mManageCom					);
  			tLJTempFeeSchema.setPolicyCom(		mPolicyCom[i]				);
  			tLJTempFeeSchema.setAPPntName(		mAPPntName[i]				);
  			tLJTempFeeSchema.setAgentGroup(		mAgentGroup[i]			);
  			tLJTempFeeSchema.setAgentCode(		mAgentCode[i]				);
  			tLJTempFeeSchema.setCurrency(			mCurrency[i]				);
  			tLJTempFeeSchema.setRemark(				mGetNoticeNo[i]			);
  			
  			mLJTempFeeSet.add(tLJTempFeeSchema);
  		}
  	}
	}
	
  if(mOperateType.equals("INSERT")){
    mDescType = "财务收费";
  }
  if(mOperateType.equals("UPDATE")){
    mDescType = "修改匹配信息成功";
  }
  if(mOperateType.equals("DELETE")){
    mDescType = "删除财务交费信息";
  }
  if(mOperateType.equals("QUERY")){
    mDescType = "查询财务交费信息";
  }
  
  
  
		
		
		
  VData tVData = new VData();
  try{
  	tVData.addElement(		globalInput					);
  	tVData.addElement(		mTransferData				);
    tVData.addElement(		mLJTempFeeClassSet	);
    tVData.addElement(		mLJTempFeeSet				);
    
    LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setActuGetNo(ActuGetNo5);
		tVData.addElement(tLJAGetSchema);
    

    if(tBusinessDelegate.submitData(tVData, mOperateType,"FinFeeUI"))
    {
    }	
  }catch(Exception ex){
    Content = mDescType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  String tFinFeeNo = "";
  if (FlagStr==""){
    //tError = mFinFeeUI.mErrors;
    tError = tBusinessDelegate.getCErrors(); 
    if (!tError.needDealError()){
      Content = mDescType+"成功";    
    	FlagStr = "Succ";
    }
    else{
    	Content = mDescType+" 失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
