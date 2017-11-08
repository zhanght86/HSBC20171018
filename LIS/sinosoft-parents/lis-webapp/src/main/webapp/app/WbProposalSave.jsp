<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ProposalSave.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	//输出参数
	CErrors tError = null;
	String FlagStr = "Succ";
	String Content = "";
	String PrtNo = request.getParameter("PrtNo");
	//09-12-17  增加tCheckMultFlag 如果界面的份数录入的不是整数  则直接返回提示份数必须是整数！   1-是整数   2-不是整数需返回错误提示
	String tCheckMultFlag="1";
    loggerDebug("WbProposalSave","Start Save"+request.getParameter("PrtNo")+"...");
    
	VData tResult = new VData();
    GlobalInput tG = new GlobalInput();	
    TransferData tTransferData = new TransferData();
	tG = ( GlobalInput )session.getValue( "GI" );
	loggerDebug("WbProposalSave","tG: "+tG);
	LCPolSchema tLCPolSchema = new LCPolSchema(); 
	SSRS tRiskInfoSSRS = null;
	String tProposalNo="";
	LCAppntIndSchema tLCAppntIndSchema = new LCAppntIndSchema();
	LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();       
    VData InsuredResults = new VData();
    
    String tsamePersonFlag = ""; //投保人与被保人是否同一人
	try
	{
		String fmAction = request.getParameter("fmAction");
	 	loggerDebug("WbProposalSave","fmAction:"+fmAction);
	 	String tDealType=request.getParameter("DealType");
	 	loggerDebug("WbProposalSave","tDealType:"+tDealType);
	 	if(fmAction.equals("INSERT"))
	 	{
	 		int tInsuredNum = Integer.parseInt(request.getParameter("InsuredNum"));
			String tMainRiskNum[] = new String[3];
			loggerDebug("WbProposalSave","被保人数目："+tInsuredNum+"...");
			for(int i=1;i<=tInsuredNum ; i++)
			{
				tMainRiskNum[i-1] = request.getParameter("MainRiskNum"+i);
				loggerDebug("WbProposalSave","第"+i+"被保人主险数目："+tMainRiskNum[i-1]+"...");
			}	    
		    
		    
		  //处理保单表
		  tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
		  tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
		  tLCPolSchema.setSaleChnl(request.getParameter("SaleChnl"));
	      tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
		  tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
		  tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
		  tLCPolSchema.setRemark(request.getParameter("Remark"));
		  tLCPolSchema.setPolApplyDate(request.getParameter("PolApplyDate"));
		  tLCPolSchema.setCValiDate(request.getParameter("PolApplyDate"));
		  tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));
		  tLCPolSchema.setPayIntv(request.getParameter("PayIntv"));
		  tLCPolSchema.setAutoPayFlag(request.getParameter("AutoPayFlag"));
		  tLCPolSchema.setRnewFlag(request.getParameter("RnewFlag"));
		  loggerDebug("WbProposalSave","tLCPolSchema:"+tLCPolSchema.encode());
		  
		  //首期
		  tTransferData.setNameAndValue("NewPayMode", StrTool.cTrim(request.getParameter("NewPayMode"))); //
		  tTransferData.setNameAndValue("NewBankCode", StrTool.cTrim(request.getParameter("NewBankCode"))); //
	      tTransferData.setNameAndValue("NewAccName", StrTool.cTrim(request.getParameter("NewAccName"))); //
	      tTransferData.setNameAndValue("NewBankAccNo", StrTool.cTrim(request.getParameter("NewBankAccNo"))); //
	      //续期
	      tTransferData.setNameAndValue("PayLocation", StrTool.cTrim(request.getParameter("PayLocation"))); //
	 	  tTransferData.setNameAndValue("BankCode", StrTool.cTrim(request.getParameter("BankCode"))); //
	      tTransferData.setNameAndValue("AccName", StrTool.cTrim(request.getParameter("AccName"))); //
	      tTransferData.setNameAndValue("BankAccNo", StrTool.cTrim(request.getParameter("BankAccNo"))); //
	      tTransferData.setNameAndValue("OutPayFlag", StrTool.cTrim(request.getParameter("OutPayFlag"))); //
	      tTransferData.setNameAndValue("GetPolMode", StrTool.cTrim(request.getParameter("GetPolMode"))); //
	      tTransferData.setNameAndValue("ChiefBankCode","");
	      tTransferData.setNameAndValue("AgentName",StrTool.cTrim(request.getParameter("AgentName")));
	      tTransferData.setNameAndValue("SignName",StrTool.cTrim(request.getParameter("SignName")));	
	      tTransferData.setNameAndValue("SignName",StrTool.cTrim(request.getParameter("SignName")));
	      //增加[初审日期] -2010-03-19 - hanbin 
	      tTransferData.setNameAndValue("FirstTrialDate",StrTool.cTrim(request.getParameter("FirstTrialDate")));
	      tTransferData.setNameAndValue("XQremindflag",StrTool.cTrim(request.getParameter("XQremindFlag")));
	 	  tTransferData.setNameAndValue("DealType",tDealType);	 	  
	 	  
	    //投保人信息
	 	  tLCAppntIndSchema.setCustomerNo(request.getParameter("AppntCustomerNo"));  //客户号
		  tLCAppntIndSchema.setName(request.getParameter("AppntName"));              //姓名 
		  tLCAppntIndSchema.setSex(request.getParameter("AppntSex"));                //性别
		  tLCAppntIndSchema.setBirthday(request.getParameter("AppntBirthday"));      //出生日期                                                                            //年龄，不提交 
		  tLCAppntIndSchema.setIDType(request.getParameter("AppntIDType"));          //证件类型
		  tLCAppntIndSchema.setIDNo(request.getParameter("AppntIDNo"));              //证件号码 
		  tLCAppntIndSchema.setNativePlace(request.getParameter("AppntNativePlace")); //国籍
		  tLCAppntIndSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));  //户口所在地
		  tLCAppntIndSchema.setMarriage(request.getParameter("AppntMarriage"));      //婚姻状况
		  //tLCAppntIndSchema.setNationality(request.getParameter("AppntNationality")); //民族
		  tLCAppntIndSchema.setRelationToInsured(request.getParameter("AppntRelationToInsured")); //与被保险人关系
		  
		  if(tLCAppntIndSchema.getRelationToInsured().equals("00"))
			  tsamePersonFlag = "1";
		  
		  tLCAppntIndSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));         //联系地址
		  tLCAppntIndSchema.setZipCode(request.getParameter("AppntZipCode"));        //邮政编码
		  tLCAppntIndSchema.setPhone(request.getParameter("AppntPhone"));            //首选回访电话 
		  tLCAppntIndSchema.setPhone2(request.getParameter("AppntPhone2"));          //其他联系电话
		  //tLCAppntIndSchema.setMobile(request.getParameter("AppntMobile"));          //移动电话
		  tLCAppntIndSchema.setEMail(request.getParameter("AppntEMail"));            //电子邮箱
		  tLCAppntIndSchema.setHomeAddress(request.getParameter("AppntHomeAddress")); //住址
		  tLCAppntIndSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode")); //住址邮政编码
		  tLCAppntIndSchema.setGrpName(request.getParameter("AppntGrpName"));        //工作单位
		  tLCAppntIndSchema.setWorkType(request.getParameter("AppntWorkType"));      //职业（工种）
		  tLCAppntIndSchema.setPluralityType(request.getParameter("AppntPluralityType"));        //兼职（工种）
		  tLCAppntIndSchema.setOccupationType(request.getParameter("AppntOccupationType"));      //职业类别
		  tLCAppntIndSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));      //职业代码
		  tTransferData.setNameAndValue("AppntSocialInsuFlag",StrTool.cTrim(request.getParameter("AppntSocialInsuFlag")));
	 	  tTransferData.setNameAndValue("AppntIDEndDate",StrTool.cTrim(request.getParameter("AppntIDEndDate")));
		  loggerDebug("WbProposalSave","tLCAppntIndSchema:"+tLCAppntIndSchema.encode());
		  
		//告知信息******************************************************************************
		/**
		//***************************************************************************************/	  		
		  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
		  String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //告知版别
		  String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //告知编码
		  String tImpartContent1[] = request.getParameterValues("ImpartGrid3");        //告知内容
		  String tImpartContent[] = request.getParameterValues("ImpartGrid4");        //填写内容
		  String tCustomerNoType[] = request.getParameterValues("ImpartGrid5");       //告知客户类型
		  String tCustomerToInsured[] = request.getParameterValues("ImpartGrid6");           //客户内部号码（被保人）
		  String tImpartFlag[] = request.getParameterValues("ImpartGrid7"); //告知是否有效（均为有效）
		  
		  int ImpartCount = 0;
		  if (tImpartNum != null) ImpartCount = tImpartNum.length;
		  
		  for (int i = 0; i < ImpartCount; i++)
		  {
		  	if(!tCustomerNoType[i].trim().equals("0")&&!tCustomerNoType[i].trim().equals("1")
	        		&& !tCustomerNoType[i].trim().equals("2"))
	        {
	          loggerDebug("WbProposalSave","***无需导入。。。。");
	          continue;
	        }
		  
		  	LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		  	loggerDebug("WbProposalSave","***LCCustomerImpart->IMPARTFLAG "+tImpartFlag[i]);	        
		  	
//	  		if( tImpartCustomerNoType[i].trim().equals( "A" ))
//	  			tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "AppntCustomerNo" ));
//	  		if( tImpartCustomerNoType[i].trim().equals( "I" ))
//	  			tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "CustomerNo" ));
	    
		  	tLCCustomerImpartSchema.setCustomerNoType( tCustomerNoType[i] );
		  	tLCCustomerImpartSchema.setCustomerNo(tCustomerToInsured[i]);
		  	tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
		  	tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		  	tLCCustomerImpartSchema.setImpartContent(tImpartContent1[i]);
		  	tLCCustomerImpartSchema.setImpartParamModle(tImpartContent[i]);	  	
		  	loggerDebug("WbProposalSave","tLCCustomerImpartSchema:"+tLCCustomerImpartSchema.encode());
		  	
		  	tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		  }
		  
		  /**
		  //***************************************************************************************
		  //***************************************************************************************/
		  
		   //***************************************************************************************
		  //***************************************************************************************/
		  tResult.add(tTransferData);
	      tResult.add(tLCPolSchema);
	      tResult.add(tLCAppntIndSchema);
	      tResult.add(tLCCustomerImpartSet);
		  
		  //***************************************************************************************
		  //***************************************************************************************/
		  
		  //***************************************************************************************
		  //***************************************************************************************/
		  //被保险人相关信息	
		  LCInsuredSchema tLCInsuredSchema ;
		  TransferData tTransferData1 ;
		  LCPolSet tLCPolSet1;
		  VData tRiskBasicInfoMainSet;
		  LCInsuredSet tLCInsuredSet ;
		  VData tTransferDataSet1 ;   //被保人下校验信息集合2
		  VData tTransferDataSet ;   //被保人下校验信息集合1
		  VData  tInsuredRelaSet ;
		  for (int i = 0; i < tInsuredNum; i++)
		  {
			  int m =i+1;
			  tLCInsuredSchema = new LCInsuredSchema();                 				  
	  		  tTransferData1 = new TransferData();
	  		  tTransferDataSet1 = new VData();
	  		  tTransferDataSet = new VData();
	  		  tLCInsuredSet = new LCInsuredSet(); 	
	  		  tRiskBasicInfoMainSet = new VData();
	  		  tLCPolSet1 = new LCPolSet();
	  		  tInsuredRelaSet = new VData();
	  		  
		  		if (((!"".equals(request.getParameter("RelationToAppnt1"))
						 && "00".equals(request.getParameter("RelationToAppnt1")) && tInsuredNum>1) //对于多被保人
						 ||(!tsamePersonFlag.equals("") && tsamePersonFlag.equals("1") && tInsuredNum==1) //对于非多被保人
						 || (request.getParameter("SamePersonFlag1") != null)) && m==1)
		        {
		        	 //如果被保人和投保人的关系为本人，则表明投保人和被保人为同一个人--对于多被保人适用
		             tTransferData1.setNameAndValue("samePersonFlag", "1"); //被保人同投保人标志	             
		        }
		  		else
		  			tTransferData1.setNameAndValue("samePersonFlag", "0");
		    
			  	tLCInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"+m));  //客户号
				  tLCInsuredSchema.setName(request.getParameter("Name"+m));              //姓名 
				  tLCInsuredSchema.setSex(request.getParameter("Sex"+m));                //性别
				  tLCInsuredSchema.setBirthday(request.getParameter("Birthday"+m));      //出生日期 
				  tLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured"+m));
	              tLCInsuredSchema.setRelationToAppnt(request.getParameter("RelationToAppnt"+m));
				  tLCInsuredSchema.setIDType(request.getParameter("IDType"+m));          //证件类型
				  tLCInsuredSchema.setIDNo(request.getParameter("IDNo"+m));              //证件号码 
				  tLCInsuredSchema.setIDExpDate(request.getParameter("IDEndDate"+m));              //证件号码
				  tLCInsuredSchema.setNativePlace(request.getParameter("NativePlace"+m)); //国籍
				  tLCInsuredSchema.setRgtAddress(request.getParameter("RgtAddress"+m));  //户口所在地
				  tLCInsuredSchema.setMarriage(request.getParameter("Marriage"+m));      //婚姻状况
				  tLCInsuredSchema.setSocialInsuFlag(request.getParameter("SocialInsuFlag"+m));
	              
	              tTransferData1.setNameAndValue("HomeAddress", StrTool.cTrim(request.getParameter("HomeAddress"+m))); //
	              tTransferData1.setNameAndValue("HomeZipCode", StrTool.cTrim(request.getParameter("HomeZipCode"+m))); //
	              tTransferData1.setNameAndValue("Phone", StrTool.cTrim(request.getParameter("Phone"+m))); //
	              tTransferData1.setNameAndValue("Phone2", StrTool.cTrim(request.getParameter("Phone2"+m))); //
	              tTransferData1.setNameAndValue("GrpName", StrTool.cTrim(request.getParameter("GrpName"+m))); //
	              tTransferData1.setNameAndValue("EMail", StrTool.cTrim(request.getParameter("EMail"+m)));
				  //tLCInsuredSchema.setPostalAddress(request.getParameter("PostalAddress"));         //联系地址
				  //tLCInsuredSchema.setZipCode(request.getParameter("ZipCode"));        //邮政编码 
				  //tLCInsuredSchema.setHomeAddress(request.getParameter("HomeAddress")); //住址
				  //tLCInsuredSchema.setHomeZipCode(request.getParameter("HomeZipCode")); //住址邮政编码
				  //tLCInsuredSchema.setPhone(request.getParameter("Phone"));            //联系电话（1） 
				  //tLCInsuredSchema.setPhone2(request.getParameter("Phone2"));          //联系电话（2）
				  //tLCInsuredSchema.setGrpName(request.getParameter("GrpName"));        //工作单位	
				  
			  	  tLCInsuredSchema.setWorkType(request.getParameter("WorkType"+m));      //职业（工种）
				  tLCInsuredSchema.setPluralityType(request.getParameter("PluralityType"+m));         //兼职（工种）
				  tLCInsuredSchema.setOccupationType(request.getParameter("OccupationType"+m));       //职业类别
				  tLCInsuredSchema.setOccupationCode(request.getParameter("OccupationCode"+m));       //职业代码
				  if( tInsuredNum == 1 )
					  tLCInsuredSchema.setSequenceNo("-1"); //非多被保人
				  else
				 	  tLCInsuredSchema.setSequenceNo(request.getParameter("SequenceNo"+m));
			  loggerDebug("WbProposalSave","tLCInsuredSchema:"+tLCInsuredSchema.encode());
				  
			  tTransferDataSet.add(tTransferData1);  		  
	          tLCInsuredSet.add(tLCInsuredSchema);
	          
	        //生存保险金、年金、红利处理方式信息
	        String tDealTypeNum[] = request.getParameterValues("DealType"+m+"GridNo");
			  String tInsuredNo[] = request.getParameterValues("DealType"+m+"Grid1");            //
			  String tMainRiskNo[] = request.getParameterValues("DealType"+m+"Grid2");           //
			  String tGetYear[] = request.getParameterValues("DealType"+m+"Grid3");        //
			  String tGetYearFlag[] = request.getParameterValues("DealType"+m+"Grid4");        //
			  String tGetYears[] = request.getParameterValues("DealType"+m+"Grid5");       //
			  String tGetDutyKind[] = request.getParameterValues("DealType"+m+"Grid6");           //
			  String tLiveGetMode[] = request.getParameterValues("DealType"+m+"Grid7"); //
			  String tBonusGetMode[] = request.getParameterValues("DealType"+m+"Grid8");
			  
			  int DealTypeCount = 0;
			  if (tDealTypeNum != null) DealTypeCount = tDealTypeNum.length;
			  
			  for (int j = 0; j < DealTypeCount; j++)
			  {
				  tTransferData1 = new TransferData();
				  LCPolSchema tLCPolSchema1 = new LCPolSchema();			  
		//      		tLCPolSchema1.setSchema(tLCPolSchema); //加入单个生存保险金、年金、红利处理方式信息
		      		tLCPolSchema1.setInsuredNo(tMainRiskNo[j]); //多主险序号                   		      		
		      	    try
		      	    {
		      	    	tLCPolSchema1.setGetYear(StrTool.cTrim(tGetYear[j]));
		      	    }
		      	    catch(Exception ex)
		      	    {
		      	        loggerDebug("WbProposalSave","****tLCPolSchema.setGetYear("+StrTool.cTrim(tGetYear[j])+") 发生异常");
		      	    }
		      	    tLCPolSchema1.setGetYearFlag(StrTool.cTrim(tGetYearFlag[j]));
		      	    
		      	    //需要校验的信息
		      	    String sGetYears = tGetYears[j];
		      	    if(StrTool.cTrim(sGetYears).indexOf("年")!=-1) //如果包含单位，则截取
		      	    {
		      	    	sGetYears = sGetYears.substring(0,sGetYears.indexOf("年"));
		      	    }
		      	    else
		      	    {
		      	    	sGetYears = StrTool.cTrim(sGetYears);
		      	    }
		      	    loggerDebug("WbProposalSave","***sGetYears: "+sGetYears);
		
		      	    tTransferData1.setNameAndValue("GetYears",sGetYears);
		      	    tTransferData1.setNameAndValue("GetDutyKind",StrTool.cTrim(tGetDutyKind[j]));
		      	    
		      	    tLCPolSchema1.setLiveGetMode(StrTool.cTrim(tLiveGetMode[j]));
		      	    tLCPolSchema1.setBonusGetMode(StrTool.cTrim(tBonusGetMode[j]));                       	    
		      	    tTransferDataSet1.add(tTransferData1);
		      	    tLCPolSet1.add(tLCPolSchema1);
			  }
			  
			//险种信息
			for (int j = 0; j < Integer.parseInt(tMainRiskNum[i]); j++)
			{
				int n =j+1;
				VData tRiskBasicInfoSet = new VData();
				  String tRiskNum[]   = request.getParameterValues("Risk"+m+n+"GridNo");
				  String tRiskCode[]   = request.getParameterValues("Risk"+m+n+"Grid3");
				  String tRiskName[]   = request.getParameterValues("Risk"+m+n+"Grid4");
				  String tRiskAmnt[]  = request.getParameterValues("Risk"+m+n+"Grid5");
				  String tRiskMult[]     = request.getParameterValues("Risk"+m+n+"Grid6");	 			  
				  String tRiskInsuYear[]   = request.getParameterValues("Risk"+m+n+"Grid7");
				  String tRiskInsuYearFlag[] = request.getParameterValues("Risk"+m+n+"Grid8");
				  String tRiskPayEndYear[]     = request.getParameterValues("Risk"+m+n+"Grid9");
				  String tRiskPayEndYearFlag[] = request.getParameterValues("Risk"+m+n+"Grid10");
				  String tRiskPrem[]   = request.getParameterValues("Risk"+m+n+"Grid11");
				  String tRiskAddPrem[]   = request.getParameterValues("Risk"+m+n+"Grid12");
				  String tRiskInputPrem[]   = request.getParameterValues("Risk"+m+n+"Grid13");
				  
				  int RiskCount = 0;
				  if (tRiskNum != null) 
				  		RiskCount = tRiskNum.length;
				  
				  for (int k = 0; k < RiskCount; k++)
				  {
				  	RiskBasicInfo tRiskBasicInfo = new RiskBasicInfo();
				  	tRiskBasicInfo.setRiskNo(request.getParameter("MainRiskNo"+m+n));
				  	tRiskBasicInfo.setRiskCode(tRiskCode[k]);
				  	tRiskBasicInfo.setRiskName(tRiskName[k]);
				  	tRiskBasicInfo.setAmnt(tRiskAmnt[k]);
				  	tRiskBasicInfo.setPrem(tRiskPrem[k]);
				  	
				  	tRiskBasicInfo.setAddPrem(tRiskAddPrem[k]);
				  	tRiskBasicInfo.setInputPrem(tRiskInputPrem[k]);
				  	try{
				  		tRiskBasicInfo.setMult(tRiskMult[k]);
				  	}catch(Exception e){
				  		e.printStackTrace();
				  		Content = "份数必须录入整数！";
				  		FlagStr="Fail";
				  		tCheckMultFlag = "2";
				  		
				  	}
				  	//对于卡单做特殊处理（卡单险种以141开头，如141601）
				  	if(tRiskCode[k]!=null && tRiskCode[k].startsWith("141"))
				  	{
				  	  tRiskBasicInfo.setPayEndYear(1);
				  	  tRiskBasicInfo.setPayEndYearFlag("Y");
				  	  tRiskBasicInfo.setInsuYear(1);
				  	  tRiskBasicInfo.setInsuYearFlag("Y");
				  	}
				  else
				  	{
				  	  tRiskBasicInfo.setPayEndYear(tRiskPayEndYear[k]);
				  	  tRiskBasicInfo.setPayEndYearFlag(tRiskPayEndYearFlag[k]);
				  	  tRiskBasicInfo.setInsuYear(tRiskInsuYear[k]);
				  	  tRiskBasicInfo.setInsuYearFlag(tRiskInsuYearFlag[k]);
				  	}
				  	loggerDebug("WbProposalSave","tRiskBasicInfo:"+tRiskBasicInfo.encode());
				  	
				  	tRiskBasicInfoSet.add(tRiskBasicInfo);
				  }
				  tRiskBasicInfoMainSet.add(tRiskBasicInfoSet);
				  
			}
			
			//受益人信息
			LCBnfSet tLCBnfSet = new LCBnfSet();
			
			String tBnfNum[] = request.getParameterValues("Bnf"+m+"GridNo");
			  String tBInsuredNo[] = request.getParameterValues("Bnf"+m+"Grid1");            //
			  String tBMainRiskNo[] = request.getParameterValues("Bnf"+m+"Grid2");           //
			  String tBnfType[] = request.getParameterValues("Bnf"+m+"Grid3");        //
			  String tName[] = request.getParameterValues("Bnf"+m+"Grid4");        //
			  String tIDType[] = request.getParameterValues("Bnf"+m+"Grid5");       //
			  String tIDNo[] = request.getParameterValues("Bnf"+m+"Grid6");           //
			  String tBnfRelationToInsured[] = request.getParameterValues("Bnf"+m+"Grid7"); //
			  String tBnfLot[] = request.getParameterValues("Bnf"+m+"Grid8");
			  String tBnfGrade[] = request.getParameterValues("Bnf"+m+"Grid9"); //
			  String tAddress[] = request.getParameterValues("Bnf"+m+"Grid10");
			  String tIDExpDate[] = request.getParameterValues("Bnf"+m+"Grid11");
			  int BnfCount = 0;
			  if (tBnfNum != null) BnfCount = tBnfNum.length;
			  
			  for (int j = 0; j < BnfCount; j++)
			  {
				  if( tName[j] == null || tName[j].equals( "" )) break;
					
				  	LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				  	tLCBnfSchema.setBnfType(tBnfType[j]);
				  	tLCBnfSchema.setName(tName[j]);
				  	tLCBnfSchema.setIDType(tIDType[j]);
				  	tLCBnfSchema.setIDNo(tIDNo[j]);
				  	tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[j]);
				  	tLCBnfSchema.setBnfLot(tBnfLot[j]);
				  	tLCBnfSchema.setBnfGrade(tBnfGrade[j]);
				  	tLCBnfSchema.setPostalAddress(tAddress[j]);
				  	tLCBnfSchema.setBnfNo(tBMainRiskNo[j]);
				  	tLCBnfSchema.setIDExpDate(tIDExpDate[j]);
				  	
				  	loggerDebug("WbProposalSave","tLCBnfSchema:"+tLCBnfSchema.encode());
				  	
				  	tLCBnfSet.add(tLCBnfSchema);
			  }	
			  
			  tInsuredRelaSet.add(tTransferDataSet);
	          tInsuredRelaSet.add(tTransferDataSet1);
	          tInsuredRelaSet.add(tLCInsuredSet);
	          tInsuredRelaSet.add(tLCBnfSet);
	          tInsuredRelaSet.add(tLCPolSet1);                    
	          tInsuredRelaSet.add(tRiskBasicInfoMainSet);
			  
	          InsuredResults.add(tInsuredRelaSet);  
	          
	        //---------------------print------------------------------------------//
	          if(tLCInsuredSet !=null)
	          {
	            loggerDebug("WbProposalSave","***LCInsuredSet: "+tLCInsuredSet.encode());
	          }

	          if(tLCBnfSet !=null)
	          {
	            loggerDebug("WbProposalSave","***LCBnfSet: "+tLCBnfSet.encode());
	          }
	          
	          if(tLCPolSet1 !=null)
	          {
	            loggerDebug("WbProposalSave","***LCPolSet1（生存等处理方式信息）: "+tLCPolSet1.encode());
	          }          
	          //-------------------------------------------------------------------//
			
		  } 	  
		  
		  tResult.add(InsuredResults);
		  tResult.add(tG); 
	 	}
		//如果份数符合要求才调用BL
		if("1".equals(tCheckMultFlag)){
 	  //*************************
 	  //*******************************************************
 	  //******************************************************************************************* 	  
 	  
 	  String BussNoType = request.getParameter("BussNoType");
 	  loggerDebug("WbProposalSave","BussNoType:"+BussNoType);	  	  
 	 PubConcurrencyLock mPubLock = new PubConcurrencyLock();
 	 try{
	     //加入并发控制，同一个清洁件在同一时间只能处理一次	     
	     if (!mPubLock.lock(PrtNo, "LC0031")) {
	    	 FlagStr="Fail";
	 	  	if(mPubLock.mErrors.needDealError())
	 	  	{
	 	  		Content="投保单（印刷号："+PrtNo+")正在处理中，请稍后再试！";
	 	  	}
	 	  	loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		 }
	     else{
	     		if(BussNoType==null || BussNoType.equals(""))
	     	     	  loggerDebug("WbProposalSave","BussNoType数据传输错误！");
	     	 	  else if(BussNoType.equals("TB"))
	     	 	  {
	     	 		 BPODealInputDataBL tBPODealInputDataBL = new BPODealInputDataBL(); 
	     	 		 if(fmAction.equals("INSERT"))
	     	 	 	  {
	     	 	 	  	loggerDebug("WbProposalSave","fmAction:"+fmAction);
	     	 	 	 	ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "该投保单已经保存，不允许再次保存，请重新进入录入界面！";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tBPODealInputDataBL.DealOneRightPol(tResult,1);
		     	 	 	  	if(!aaa)
		     	 	 	  	{
		     	 	 	  		FlagStr="Fail";
		     	 	 	  		if(tBPODealInputDataBL.mErrors.needDealError())
		     	 	 	  		{
		     	 	 	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
		     	 	 	  		}
		     	 	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		     	 	 	  		
		     	 	 	  		loggerDebug("WbProposalSave","***开始撤销已经导入的数据..." );
		     	 	 	  	  	if(!tBPODealInputDataBL.redoInputedPol(PrtNo))
			     	 	        {
			     	 	          loggerDebug("WbProposalSave","***撤销已经导入的数据失败"+ PrtNo +" : "+tBPODealInputDataBL.mErrors.getLastError());
			     	 	        }
			     	 	        else
			     	 	      	{
			     	 	      	 loggerDebug("WbProposalSave","***撤销已经导入的数据完毕..." );
			     	 	      	}
		     	 	 	  	 }
		     	 	 	  	 loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
	    		     	}	     	 	 	  	
	     	 	 	  } 
	     	 		 else if(fmAction.equals("DELETE"))
	     	 	 	  { 
	     	 			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "该异常件已经保存，不能删除！";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tBPODealInputDataBL.updateBPOState(PrtNo,tDealType,"2",tG); 	 	        
	     		 	 	  	if(!aaa)
	     		 	 	  	{
	     		 	 	  		FlagStr="Fail";
	     		 	 	  		if(tBPODealInputDataBL.mErrors.needDealError())
	     		 	 	  		{
	     		 	 	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
	     		 	 	  		}
	     		 	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
	     		 	 	  	}	
	     		 	 	   loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);	
	    		     	}	     	 	 	        
	     	 	 	  }
	     	 		 else if(fmAction.equals("DELETECONT"))
	     		 	  {
	     	 			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist==null || tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "该投保单尚未保存，不允许删除录入信息！";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		ExeSQL tExe1 = new ExeSQL();
		    		     	String tExist1=tExe1.getOneValue("select 1 from lwmission where missionprop1='"+PrtNo+"' and activityid in ('0000001090','0000001091')");
		    		     	loggerDebug("WbProposalSave","tExist1:"+tExist1);
		    		     	if(tExist1==null || tExist1.equals(""))
		    		     	{		     		
		    			 	   Content = "该投保单已经处理完毕，不允许删除录入信息！";
		    			 	   FlagStr="Fail";
		    		     	}
		    		     	else
		    		     	{
		    		     		boolean aaa=tBPODealInputDataBL.redoInputedCont(PrtNo,tDealType,"0",tG);
			     		 	  	if(!aaa)
			     		 	  	{
			     		 	  		FlagStr="Fail";
			     		 	  		if(tBPODealInputDataBL.mErrors.needDealError())
			     		 	  		{
			     		 	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
			     		 	  		}
			     		 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
			     		 	  	}
			     		 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
		    		     	} 		    		     		
	    		     	}	     		 	    
	     		 	  }
	     	 	  }
	     	 	  else if(BussNoType.equals("JM"))
	     	 	  {
	     	 		 JMBPODealInputDataBL tJMBPODealInputDataBL = new JMBPODealInputDataBL();
	     	  		 if(fmAction.equals("INSERT"))
	     	  	 	  {	     	  			
	     	  	 	  	loggerDebug("WbProposalSave","fmAction:"+fmAction);
		     	  	 	ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "该投保单已经保存，不允许再次保存，请重新进入录入界面！";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tJMBPODealInputDataBL.DealOneRightPol(tResult,1);
		     	  	 	  	if(!aaa)
		     	  	 	  	{
		     	  	 	  		FlagStr="Fail";
		     	  	 	  		if(tJMBPODealInputDataBL.mErrors.needDealError())
		     	  	 	  		{
		     	  	 	  			Content=tJMBPODealInputDataBL.mErrors.getFirstError();
		     	  	 	  		}
		     	  	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		     	  	 	  		
		     	  	 	  		loggerDebug("WbProposalSave","***开始撤销已经导入的数据..." );
		     	  	 	  	  if(!tJMBPODealInputDataBL.redoInputedPol(PrtNo))
			     	  	        {
			     	  	          loggerDebug("WbProposalSave","***撤销已经导入的数据失败"+ PrtNo +" : "+tJMBPODealInputDataBL.mErrors.getLastError());
			     	  	        }
			     	  	       else
			     	  	      	{
			     	  	      	 loggerDebug("WbProposalSave","***撤销已经导入的数据完毕..." );
			     	  	      	}
		     	  	 	  	 }
		     	  	 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
	    		     	}	     	  	 	  	
	     	  	 	  } 
	     	  		 else if(fmAction.equals("DELETE"))
	     	  	 	  {
	     	  			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "该异常件已经保存，不能删除！";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tJMBPODealInputDataBL.updateBPOState(PrtNo,tDealType,"2",tG);
		     	  	 	  	if(!aaa)
		     	  	 	  	{
		     	  	 	  		FlagStr="Fail";
		     	  	 	  		if(tJMBPODealInputDataBL.mErrors.needDealError())
		     	  	 	  		{
		     	  	 	  			Content=tJMBPODealInputDataBL.mErrors.getFirstError();
		     	  	 	  		}
		     	  	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		     	  	 	  	}
		     	  	 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
	    		     	}	     	  	 	    
	     	  	 	  }
	     	  		else if(fmAction.equals("DELETECONT"))
	     		 	  {
	     	  			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist==null || tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "该投保单尚未保存，不允许删除录入信息！";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		ExeSQL tExe1 = new ExeSQL();
		    		     	String tExist1=tExe1.getOneValue("select 1 from lwmission where missionprop1='"+PrtNo+"' and activityid in ('0000001090','0000001091')");
		    		     	loggerDebug("WbProposalSave","tExist1:"+tExist1);
		    		     	if(tExist1==null || tExist1.equals(""))
		    		     	{		     		
		    			 	   Content = "该投保单已经处理完毕，不允许删除录入信息！";
		    			 	   FlagStr="Fail";
		    		     	}
		    		     	else
		    		     	{
		    		     		boolean aaa=tJMBPODealInputDataBL.redoInputedCont(PrtNo,tDealType,"0",tG);
			     		 	  	if(!aaa)
			     		 	  	{
			     		 	  		FlagStr="Fail";
			     		 	  		if(tJMBPODealInputDataBL.mErrors.needDealError())
			     		 	  		{
			     		 	  			Content=tJMBPODealInputDataBL.mErrors.getFirstError();
			     		 	  		}
			     		 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
			     		 	  	}
			     		 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
		    		     	}	    		     		
	    		     	} 	     		 	    
	     		 	  }
	     	  	  } 
	     }
 	 }
 	catch(Exception e)
	{
 		FlagStr="Fail";
 	    Content = "处理时发生异常:e.toString()";
	}
	finally
	{
		mPubLock.unLock();
	}
	}
	  if(FlagStr.equals("Succ")){
    
	  } // end of if
	  //FlagStr="Succ";
	  loggerDebug("WbProposalSave",Content);
	  
	  //替换掉错误信息的特殊字符
	  Content = Content.replace('\n', ' ');
	  
	  while (Content.indexOf("\"") != -1) 
	  {
      		Content = Content.replace('\"', ' ');
      		loggerDebug("WbProposalSave",Content);
	  }
    
    
    	loggerDebug("WbProposalSave",Content);
    	loggerDebug("WbProposalSave",FlagStr); 
    	
    	ExeSQL tExe = new ExeSQL();
    	tProposalNo=tExe.getOneValue("select contno from lccont where prtno='"+PrtNo+"' ");
    	if(tProposalNo==null)
    	{
    		tProposalNo="";
    	}
    	loggerDebug("WbProposalSave",tProposalNo);
    	
     }
     catch(Exception ex)
     {
      FlagStr="Fail";
      Content = "处理时发生异常！";
     }
    %>    

                  
<html>
<script language="javascript">
	try 
	{
		parent.fraInterface.fm.all('ProposalNo').value='<%=tProposalNo%>';
		parent.fraInterface.fm.all('save').disabled=true;
		parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
	}	catch(ex) { 
		//alert("after Save but happen err:" + ex);
	}
</script>
 
</html>

