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
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  loggerDebug("TempFinFeeSave","开始执行Save页面");
  
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getValue("GI"));
  
  TransferData mTransferData = new TransferData();
  
  LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
  LJTempFeeSchema tLJTempFeeSchema ;
  LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
  LJTempFeeClassSchema tLJTempFeeClassSchema;
  
  FinFeeUI mFinFeeUI = new FinFeeUI();
  
  CErrors tError = null;
  String mOperateType ="INSERT";
  
  loggerDebug("TempFinFeeSave","操作的类型是"+mOperateType);
  String FlagStr = "";
  String Content = "";
  String mDescType = "";	//将操作标志的英文转换成汉字的形式
  //公共信息
  String mManageCom				= request.getParameter("ManageCom");
  String mOperator 	 			= request.getParameter("Operator"	);
  
//  //财务交费信息
//	String[] mTempNum				=	request.getParameterValues("FinFeeGridNo"	); 
//	String[] mPayMode				=	request.getParameterValues("FinFeeGrid1"	); //交费方式
//  String[] mChequeNo			=	request.getParameterValues("FinFeeGrid5"	); //支票号
//  
//  loggerDebug("TempFinFeeSave"," zb ================= : "+ mChequeNo[0]);
//  
//  String[] mChequeDate  	=	request.getParameterValues("FinFeeGrid6"	); //支票日期
//  ////支票开户银行
//  String[] mBankCode    	=	request.getParameterValues("FinFeeGrid7"	); //银行编码
//  String[] mBankAccNo   	=	request.getParameterValues("FinFeeGrid9"	); //银行账号
//  String[] mAccName     	=	request.getParameterValues("FinFeeGrid10"	); //户名
//  ////收费银行
//  String[] mInBankCode  	=	request.getParameterValues("FinFeeGrid11"	);	//收款银行
//  String[] mInBankAccNo 	=	request.getParameterValues("FinFeeGrid12"	);	//收款银行账号
//  String[] mInAccName   	=	request.getParameterValues("FinFeeGrid13"	);	//收款银行户名
//  String[] mPayUserName		=	request.getParameterValues("FinFeeGrid19"	);  //交费人姓名
//  
//	//业务交费信息
//  String[] mNum						=	request.getParameterValues("TempToGridNo"	); 
//  String[] mGetNoticeNo 	=	request.getParameterValues("TempToGrid1"	); 	//应收号码
//  String[] mTempFeeType 	=	request.getParameterValues("TempToGrid18"	); 	//交费类型
//  String[] mPayDate 			=	request.getParameterValues("TempToGrid3"	); 	//交费日期
//  String[] mPayMoney 			=	request.getParameterValues("TempToGrid4"	); 	//交费金额
//  String[] mCurrency 			=	request.getParameterValues("TempToGrid5"	); 	//币种
//  
//  String[] mAgentGroup 		=	request.getParameterValues("TempToGrid9"	); 	//代理人组别
//  String[] mAgentCode			=	request.getParameterValues("TempToGrid10"	); 	//代理人编码
//  String[] mOtherNo 			=	request.getParameterValues("TempToGrid11"	); 	//业务号码
//  String[] mOtherNoType		=	request.getParameterValues("TempToGrid12"	); 	//业务号码
//  String[] mAPPntName 		=	request.getParameterValues("TempToGrid16"	); 	//投保人
//  String[] mPolicyCom 		=	request.getParameterValues("TempToGrid7"	); 	//管理机构
//  String[] mTempFeeNo 		=	request.getParameterValues("TempToGrid20"	); 	//暂收费号
//	
//  LJTempFeeClassSchema tLJTempFeeClassSchema;
//  
//  if(mOperateType.equals("INSERT")){
//  	loggerDebug("TempFinFeeSave","开始执行INSERT页面");
//  	
//	LJTempFeeSchema tLJTempFeeSchema ;
//		if(mNum!=null){
//			int tLength = mNum.length;
//			String tLimit ;
//			String tNo 		; 
//			String serNo 	; 
//  		for(int i = 0 ;i < tLength ;i++){
//  			tLimit = PubFun.getNoLimit(mPolicyCom[i]);
//  			if("".equals(mTempFeeNo[i])||mTempFeeNo[i]==null){
//  				tNo = "TS"+PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// 产生通知书号即暂交费号
//  			}else{
//  				tNo = mTempFeeNo[i];
//  			}
//  			serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
//  			
//  			//财务收费
//  			tLJTempFeeClassSchema = new LJTempFeeClassSchema();
//  			tLJTempFeeClassSchema.setTempFeeNo(		tNo							);
//  			tLJTempFeeClassSchema.setPayMode(	 		mPayMode[0]			);
//		  	tLJTempFeeClassSchema.setChequeNo( 		mChequeNo[0]		); //票据号码
//		  	tLJTempFeeClassSchema.setPayMoney(	 	mPayMoney[i]		); 
//		  	tLJTempFeeClassSchema.setCurrency(	  mCurrency[i]		);
//		  	tLJTempFeeClassSchema.setPayDate(			mPayDate[0]			);
//		  	tLJTempFeeClassSchema.setChequeDate(	mChequeDate[0]	);
//		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);
//		  	tLJTempFeeClassSchema.setConfFlag(		"0"							);
//		  	tLJTempFeeClassSchema.setSerialNo(		serNo						);
//		  	tLJTempFeeClassSchema.setBankCode(		mBankCode[0]		);
//		  	tLJTempFeeClassSchema.setBankAccNo(		mBankAccNo[0]		);
//		  	tLJTempFeeClassSchema.setAccName(			mAccName[0]			);     
//		  	tLJTempFeeClassSchema.setInBankCode(	mInBankCode[0]	);     
//		  	tLJTempFeeClassSchema.setInBankAccNo(	mInBankAccNo[0]	);     
//		  	tLJTempFeeClassSchema.setInAccName(		mInAccName[0]		);     
//		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);     
//		  	tLJTempFeeClassSchema.setManageCom(		mManageCom			);     
//		  	tLJTempFeeClassSchema.setPolicyCom(		mPolicyCom[i]		);     
//		  	tLJTempFeeClassSchema.setOperator(		mOperator				);     
//		  	tLJTempFeeClassSchema.setOtherNo(			mOtherNo[i]			);     
//		  	tLJTempFeeClassSchema.setOtherNoType(	mOtherNoType[i]	);     
//		  	                                       
//		  	tLJTempFeeClassSchema.setPayUserName(	mPayUserName[0]	);     
//		  	                                       
//		  	mLJTempFeeClassSet.add(tLJTempFeeClassSchema);               
//		  	
//  			//财务收费
//  			tLJTempFeeSchema = new LJTempFeeSchema();
//  			tLJTempFeeSchema.setTempFeeNo(		tNo									);
//  			tLJTempFeeSchema.setTempFeeType(	mTempFeeType[i]			);
//  			tLJTempFeeSchema.setRiskCode(	 		"000000"						);
//  			tLJTempFeeSchema.setOtherNo(			mOtherNo[i]					);
//  			tLJTempFeeSchema.setOtherNoType(	mOtherNoType[i]			);
//  			tLJTempFeeSchema.setPayMoney(			mPayMoney[i]				);
//  			tLJTempFeeSchema.setPayDate(			mPayDate[i]					);
//  			tLJTempFeeSchema.setEnterAccDate(	mPayDate[0]					);
//  			tLJTempFeeSchema.setSerialNo(			serNo								);
//  			tLJTempFeeSchema.setConfFlag(			"0"									);
//  			tLJTempFeeClassSchema.setSerialNo(serNo								);
//  			tLJTempFeeSchema.setManageCom(		mManageCom					);
//  			tLJTempFeeSchema.setPolicyCom(		mPolicyCom[i]				);
//  			tLJTempFeeSchema.setAPPntName(		mAPPntName[i]				);
//  			tLJTempFeeSchema.setAgentGroup(		mAgentGroup[i]			);
//  			tLJTempFeeSchema.setAgentCode(		mAgentCode[i]				);
//  			tLJTempFeeSchema.setCurrency(			mCurrency[i]				);
//  			tLJTempFeeSchema.setRemark(				mGetNoticeNo[i]			);
//  			
//  			mLJTempFeeSet.add(tLJTempFeeSchema);
//  		}
//  	}
//	}

  if(mOperateType.equals("INSERT")){
			loggerDebug("TempFinFeeSave","开始执行INSERT页面");
		//总表数据
			String[] zTempFeeNum			=	request.getParameterValues("TTempToGridNo"); 
			String[] zTempFeeNO				=	request.getParameterValues("TTempToGrid1"	);		
			String[] zTempFeeType		  =	request.getParameterValues("TTempToGrid2"	);
			String[] zPayDate				  =	request.getParameterValues("TTempToGrid3"	);
			String[] zPayMoney				=	request.getParameterValues("TTempToGrid4"	);
			String[] zEnterAccDate		=	request.getParameterValues("TTempToGrid5"	);
			String[] zPolicyCom				=	request.getParameterValues("TTempToGrid6"	);
			String[] zRiskCode				=	request.getParameterValues("TTempToGrid7"	);
			String[] zAgentGroup			=	request.getParameterValues("TTempToGrid8"	);
			String[] zAgentCode				=	request.getParameterValues("TTempToGrid9"	);
			String[] zOtherNo				  =	request.getParameterValues("TTempToGrid10");
			String[] zOtherNoType			=	request.getParameterValues("TTempToGrid11");
			String[] zPayIntv				  =	request.getParameterValues("TTempToGrid12");
			String[] zPayYear				  =	request.getParameterValues("TTempToGrid13");
			String[] zPayYearFlag			=	request.getParameterValues("TTempToGrid14");
			String[] zAPPntName				=	request.getParameterValues("TTempToGrid15");
			String[] zReMark				  =	request.getParameterValues("TTempToGrid16");
			String[] zGetNoticeNo		  =	request.getParameterValues("TTempToGrid17");
			String[] zCurrency			  =	request.getParameterValues("TTempToGrid18");		
			String tLimit="" ;
			String tTempNo="" ; 
			String serNo="" 	; 
		  serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);	
			if(zTempFeeNum!=null){
					int tLength = zTempFeeNum.length; 
		  		for(int i = 0 ;i < tLength ;i++){		  			
		  			tLJTempFeeSchema = new LJTempFeeSchema();
		  			tLJTempFeeSchema.setTempFeeNo(		zTempFeeNO[i]							);
		  			tLJTempFeeSchema.setTempFeeType(	zTempFeeType[i]			);
		  			tLJTempFeeSchema.setRiskCode(	 		"000000"						);
		  			tLJTempFeeSchema.setOtherNo(			zOtherNo[i]					);
		  			tLJTempFeeSchema.setOtherNoType(	zOtherNoType[i]			);
		  			tLJTempFeeSchema.setPayMoney(			zPayMoney[i]				);
		  			tLJTempFeeSchema.setPayDate(			zPayDate[i]					);
		  			tLJTempFeeSchema.setEnterAccDate(	zEnterAccDate[i]		);
		  			tLJTempFeeSchema.setSerialNo(			serNo								);
		  			tLJTempFeeSchema.setConfFlag(			"0"									);
		  			tLJTempFeeSchema.setManageCom(		zPolicyCom[i]				);
		  			tLJTempFeeSchema.setPolicyCom(		zPolicyCom[i]				);
		  			tLJTempFeeSchema.setAPPntName(		zAPPntName[i]				);
		  			tLJTempFeeSchema.setAgentGroup(		zAgentGroup[i]			);
		  			tLJTempFeeSchema.setAgentCode(		zAgentCode[i]				);
		  			tLJTempFeeSchema.setCurrency(			zCurrency[i]				);
		  			tLJTempFeeSchema.setRemark(				zGetNoticeNo[i]			);
		  			
		  			mLJTempFeeSet.add(tLJTempFeeSchema);  			
		  		}	
			}
		
			//分类表数据
			String[] sTempFeeClassNum			=	request.getParameterValues("TTempClassToGridNo"	);  
			String[] sTempFeeNO				=	request.getParameterValues("TTempClassToGrid1"	);
			String[] sPayMode		  =	request.getParameterValues("TTempClassToGrid2"	);
			String[] sPayDate				  =	request.getParameterValues("TTempClassToGrid3"	);
			String[] sPayMoney				=	request.getParameterValues("TTempClassToGrid4"	);
			String[] sCurrency		=	request.getParameterValues("TTempClassToGrid5"	);
			//String[] sPolicyCom				=	request.getParameterValues("TTempClassToGrid6"	);
			String[] sEnterAccDate				=	request.getParameterValues("TTempClassToGrid7"	);
			String[] sPolicyCom			=	request.getParameterValues("TTempClassToGrid8"	);
			String[] sChequeNo				=	request.getParameterValues("TTempClassToGrid9"	);
			String[] sChequeDate				  =	request.getParameterValues("TTempClassToGrid10"	);
			String[] sBankName			=	request.getParameterValues("TTempClassToGrid11"	);
			String[] sBankAccNo				  =	request.getParameterValues("TTempClassToGrid12"	);
			String[] sAccName				  =	request.getParameterValues("TTempClassToGrid13"	);
			String[] sInBankCode			=	request.getParameterValues("TTempClassToGrid14"	);
			String[] sInBankAccNo				=	request.getParameterValues("TTempClassToGrid15"	);
			String[] sInAccName				  =	request.getParameterValues("TTempClassToGrid16"	);
			//String[] zPayIntv		  =	request.getParameterValues("TTempClassToGrid17"	);
			//String[] sCurrency			  =	request.getParameterValues("TTempClassToGrid18"	);
			//String[] sPayYearFlag			=	request.getParameterValues("TTempClassToGrid19"	);
			//String[] sAPPntName				=	request.getParameterValues("TTempClassToGrid20"	);
			//String[] sReMark				  =	request.getParameterValues("TTempClassToGrid21"	);
			String[] sOtherNo		  =	request.getParameterValues("TTempClassToGrid22"	);
			String[] sOtherNoType			  =	request.getParameterValues("TTempClassToGrid23"	);
			String[] sPayUserName			  =	request.getParameterValues("TTempClassToGrid24"	);
			if(sTempFeeClassNum!=null){
				int tLength = sTempFeeClassNum.length; 
				for(int j = 0 ;j < tLength ;j++){
				    serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		  			tLimit = PubFun.getNoLimit(sPolicyCom[j]);
		  			tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		  			tLJTempFeeClassSchema.setTempFeeNo(		sTempFeeNO[j]							);
		  			tLJTempFeeClassSchema.setPayMode(	 		sPayMode[j]			);
		  			if(sChequeNo[j] == null || sChequeNo[j] == "")
		  			{
		  			   sChequeNo[j] = "000000";
		  			}
				  	tLJTempFeeClassSchema.setChequeNo( 		sChequeNo[j]		); //票据号码
				  	tLJTempFeeClassSchema.setPayMoney(	 	sPayMoney[j]		); 
				  	tLJTempFeeClassSchema.setCurrency(	  sCurrency[j]		);
				  	tLJTempFeeClassSchema.setPayDate(			sPayDate[j]			);
				  	tLJTempFeeClassSchema.setChequeDate(	sChequeDate[j]	);
				  	tLJTempFeeClassSchema.setEnterAccDate(sEnterAccDate[j]);
				  	tLJTempFeeClassSchema.setConfFlag(		"0"							);
				  	tLJTempFeeClassSchema.setSerialNo(		serNo						);
				  	tLJTempFeeClassSchema.setBankName(		sBankName[j]		);
				  	tLJTempFeeClassSchema.setBankAccNo(		sBankAccNo[j]		);
				  	if(sBankName[j] != null && !sBankName[j].equals(""))
				  	{
				  	 				  	tLJTempFeeClassSchema.setBankCode(	"000000"	);
				  	}
				  	tLJTempFeeClassSchema.setAccName(			sAccName[j]			);     
				  	tLJTempFeeClassSchema.setInBankCode(	sInBankCode[j]	);     
				  	tLJTempFeeClassSchema.setInBankAccNo(	sInBankAccNo[j]	);     
				  	tLJTempFeeClassSchema.setInAccName(		sInAccName[j]		);     
				  	tLJTempFeeClassSchema.setEnterAccDate(sEnterAccDate[j]);     
				  	tLJTempFeeClassSchema.setManageCom(		sPolicyCom[j]		);     
				  	tLJTempFeeClassSchema.setPolicyCom(		sPolicyCom[j]		);     
//				  	tLJTempFeeClassSchema.setOperator(		mOperator			);     
				  	tLJTempFeeClassSchema.setOtherNo(			sOtherNo[j]			);     
				  	tLJTempFeeClassSchema.setOtherNoType(	sOtherNoType[j]	);     		  	                                       
				  	tLJTempFeeClassSchema.setPayUserName(	sPayUserName[j]	);     
				  	                                       
				  	mLJTempFeeClassSet.add(tLJTempFeeClassSchema);  		
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
    if(!mFinFeeUI.submitData(	tVData,mOperateType	)){
   	}	
  }catch(Exception ex){
    Content = mDescType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  String tFinFeeNo = "";
  if (FlagStr==""){
    tError = mFinFeeUI.mErrors;
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
