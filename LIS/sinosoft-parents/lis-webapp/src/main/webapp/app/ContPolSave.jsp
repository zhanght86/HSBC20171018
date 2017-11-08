<%--
    保存集体保单信息 2004-11-16 wzw
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.text.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%	         
	
  String FlagStr="";      //操作结果
  String Content = "";    //控制台信息
  String tAction = "";    //操作类型：delete update insert
  String tOperate = "";   //操作代码
  String mLoadFlag="";
  String Flag="";         //用于判断业务员佣金比例有无空值
  //团体告知信息
  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //告知版别
	String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //告知编码
	String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //告知内容
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4"); 
  //既往告知
  String tImpartNum2[] = request.getParameterValues("HistoryImpartGridNo");
	String tInsuYear[] = request.getParameterValues("HistoryImpartGrid1");
	String tInsuYearFlag[] = request.getParameterValues("HistoryImpartGrid2");            //告知版别
	String tInsuContent[] = request.getParameterValues("HistoryImpartGrid3");           //告知编码
	String tRate[] = request.getParameterValues("HistoryImpartGrid4");        //告知内容
	String tEnsureContent[] = request.getParameterValues("HistoryImpartGrid5");
	String tPeoples[] = request.getParameterValues("HistoryImpartGrid6");	 
	String tRecompensePeoples[] = request.getParameterValues("HistoryImpartGrid7"); 
	String tOccurMoney[] = request.getParameterValues("HistoryImpartGrid8"); 
	String tRecompenseMoney[] = request.getParameterValues("HistoryImpartGrid9"); 
	String tPendingMoney[] = request.getParameterValues("HistoryImpartGrid10");
	String tSerialNo1[] = request.getParameterValues("HistoryImpartGrid11");  
	//严重疾病情况告知
	String tImpartNum3[] = request.getParameterValues("DiseaseGridNo");
	String tOcurTime[] = request.getParameterValues("DiseaseGrid1");
	String tDiseaseName[] = request.getParameterValues("DiseaseGrid2");            //告知版别
	String tDiseasePepoles[] = request.getParameterValues("DiseaseGrid3");           //告知编码
	String tCureMoney[] = request.getParameterValues("DiseaseGrid4");        //告知内容
	String tRemark[] = request.getParameterValues("DiseaseGrid5");
	String tSerialNo2[] = request.getParameterValues("DiseaseGrid6"); 
	//客户服务需求
	String tServInfoGridNum[] = request.getParameterValues("ServInfoGridNo");
	String tServKind[] = request.getParameterValues("ServInfoGrid1");
	loggerDebug("ContPolSave","客户服务类型 :"+tServKind);
	String tServDetail[] = request.getParameterValues("ServInfoGrid2");            //告知版别
	String tServChoose[] = request.getParameterValues("ServInfoGrid3");           //告知编码
	String tServRemark[] = request.getParameterValues("ServInfoGrid4"); 

  VData tVData = new VData();
  LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();      //集体保单
  LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();   //团单投保人
  LDGrpSchema tLDGrpSchema   = new LDGrpSchema();                //团体客户
  LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); //团体客户地址
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();	//客户告知
  LCHistoryImpartSet tLCHistoryImpartSet = new LCHistoryImpartSet(); //既往告知          //既往告知
  LCDiseaseImpartSet tLCDiseaseImpartSet = new LCDiseaseImpartSet(); //严重疾病告知		
  LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
  LACommisionDetailSchema mLACommisionDetailSchema = new LACommisionDetailSchema();

  //客户服务信息
  LCGrpServInfoSet tLCGrpServInfoSet = new LCGrpServInfoSet(); 
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  tAction = request.getParameter( "fmAction" );
  //集体保单信息  LCGrpCont
	tLCGrpContSchema.setProposalGrpContNo(request.getParameter("ProposalGrpContNo"));  //集体投保单号码
	tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));                  //印刷号码
	tLCGrpContSchema.setManageCom(request.getParameter("ManageCom"));          //管理机构
	tLCGrpContSchema.setSaleChnl("2");            //销售渠道


	tLCGrpContSchema.setConferNo(request.getParameter("ConferNo"));            //服务协议书号
	tLCGrpContSchema.setSignReportNo(request.getParameter("SignReportNo"));    //签报件
	tLCGrpContSchema.setReportNo(request.getParameter("ReportNo"));            //呈报件号
	tLCGrpContSchema.setAgentConferNo(request.getParameter("AgentConferNo"));  //代理协议书号
	
	
	tLCGrpContSchema.setAgentCom(request.getParameter("AgentCom"));            //代理机构
	tLCGrpContSchema.setAgentType(request.getParameter("AgentType"));           //代理机构分类
	tLCGrpContSchema.setAgentCode(request.getParameter("AgentCode"));          //代理人编码
	tLCGrpContSchema.setAgentGroup(request.getParameter("AgentGroup"));        //代理人组别
	tLCGrpContSchema.setAgentCode1(request.getParameter("AgentCode1"));        //联合代理人代码
	tLCGrpContSchema.setGrpSpec(request.getParameter("GrpSpec"));              //集体特约
	tLCGrpContSchema.setAppntNo(request.getParameter("GrpNo"));                //客户号码
	tLCGrpContSchema.setAddressNo(request.getParameter("GrpAddressNo"));       //地址号码
	tLCGrpContSchema.setGrpName(request.getParameter("GrpName"));              //单位名称
	tLCGrpContSchema.setGetFlag(request.getParameter("GetFlag"));              //付款方式
	tLCGrpContSchema.setBankCode(request.getParameter("BankCode"));            //银行编码
	tLCGrpContSchema.setBankAccNo(request.getParameter("BankAccNo"));          //银行帐号
	tLCGrpContSchema.setCurrency(request.getParameter("Currency"));            //币别
	tLCGrpContSchema.setCValiDate(request.getParameter("CValiDate"));          //保单生效日期
	tLCGrpContSchema.setPolApplyDate(request.getParameter("PolApplyDate"));    //保单投保日期
	tLCGrpContSchema.setOutPayFlag(request.getParameter("OutPayFlag"));        //溢交处理方式
	tLCGrpContSchema.setEnterKind(request.getParameter("EnterKind"));          //参保形式
	tLCGrpContSchema.setAmntGrade(request.getParameter("AmntGrade"));          //保额等级
	tLCGrpContSchema.setPeoples3(request.getParameter("Peoples"));						 //单位可投保人数
  tLCGrpContSchema.setOnWorkPeoples(request.getParameter("OnWorkPeoples"));		//单位可投保人数	    
  tLCGrpContSchema.setOffWorkPeoples(request.getParameter("OffWorkPeoples"));		//单位可投保人数	    
  tLCGrpContSchema.setOtherPeoples(request.getParameter("OtherPeoples"));		//单位可投保人数
  tLCGrpContSchema.setRelaPeoples(request.getParameter("RelaPeoples"));		//连带被保人人数
  tLCGrpContSchema.setRelaMatePeoples(request.getParameter("RelaMatePeoples"));		//配偶人数	    
  tLCGrpContSchema.setRelaYoungPeoples(request.getParameter("RelaYoungPeoples"));		//子女人数
  tLCGrpContSchema.setRelaOtherPeoples(request.getParameter("RelaOtherPeoples"));		//连带其他人员数	    
	    
	    
	tLCGrpContSchema.setGrpNature(request.getParameter("GrpNature"));         //单位性质
	tLCGrpContSchema.setBusinessType(request.getParameter("BusinessType"));   //行业类别
	tLCGrpContSchema.setPeoples(request.getParameter("Peoples"));             //总人数
	tLCGrpContSchema.setRgtMoney(request.getParameter("RgtMoney"));           //注册资本
	tLCGrpContSchema.setAsset(request.getParameter("Asset"));                 //资产总额
	tLCGrpContSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //净资产收益率
	tLCGrpContSchema.setMainBussiness(request.getParameter("MainBussiness")); //主营业务
	tLCGrpContSchema.setCorporation(request.getParameter("Corporation"));     //法人
	tLCGrpContSchema.setComAera(request.getParameter("ComAera"));             //机构分布区域
	tLCGrpContSchema.setPhone(request.getParameter("Phone"));             		//总机
	tLCGrpContSchema.setFax(request.getParameter("Fax"));             				//传真
	tLCGrpContSchema.setFoundDate(request.getParameter("FoundDate"));         //成立时间
	tLCGrpContSchema.setRemark(request.getParameter("Remark"));		
	
	tLCGrpContSchema.setAgentCom(request.getParameter("MediAgentCom")); //中介机构
	tLCGrpContSchema.setHandlerName(request.getParameter("MediAgentCode")); //中介机构业务员
			
	//团单投保人信息  LCGrpAppnt
	tLCGrpAppntSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));     //集体投保单号码
	tLCGrpAppntSchema.setCustomerNo(request.getParameter("GrpNo"));            //客户号码
	tLCGrpAppntSchema.setPrtNo(request.getParameter("PrtNo"));                 //印刷号码
	tLCGrpAppntSchema.setName(request.getParameter("GrpName"));
	tLCGrpAppntSchema.setPostalAddress(request.getParameter("GrpAddress"));
	tLCGrpAppntSchema.setZipCode(request.getParameter("GrpZipCode"));
	tLCGrpAppntSchema.setAddressNo(request.getParameter("GrpAddressNo"));
	tLCGrpAppntSchema.setPhone(request.getParameter("Phone"));
	//团体客户信息  LDGrp
	tLDGrpSchema.setCustomerNo(request.getParameter("GrpNo"));            //客户号码
	tLDGrpSchema.setGrpName(request.getParameter("GrpName"));             //单位名称
	tLDGrpSchema.setGrpNature(request.getParameter("GrpNature"));         //单位性质
	tLDGrpSchema.setBusinessType(request.getParameter("BusinessType"));   //行业类别
	tLDGrpSchema.setPeoples(request.getParameter("Peoples"));             //总人数
	tLDGrpSchema.setOnWorkPeoples(request.getParameter("AppntOnWorkPeoples"));             //总人数
	tLDGrpSchema.setOffWorkPeoples(request.getParameter("AppntOffWorkPeoples"));             //总人数
	tLDGrpSchema.setOtherPeoples(request.getParameter("AppntOtherPeoples"));             //总人数	    	    	    
	tLDGrpSchema.setRgtMoney(request.getParameter("RgtMoney"));           //注册资本
	tLDGrpSchema.setAsset(request.getParameter("Asset"));                 //资产总额
	tLDGrpSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //净资产收益率
	tLDGrpSchema.setMainBussiness(request.getParameter("MainBussiness")); //主营业务
	tLDGrpSchema.setCorporation(request.getParameter("Corporation"));     //法人
	tLDGrpSchema.setComAera(request.getParameter("ComAera"));             //机构分布区域
	tLDGrpSchema.setPhone(request.getParameter("Phone"));             //总机
	tLDGrpSchema.setFax(request.getParameter("Fax"));             //传真
	tLDGrpSchema.setFoundDate(request.getParameter("FoundDate"));             //成立时间
	tLDGrpSchema.setVIPValue(request.getParameter("AppntVIPValue"));
	tLDGrpSchema.setSocialInsuNo(request.getParameter("BankAccNo1"));
	//团体客户地址  LCGrpAddress	    
	tLCGrpAddressSchema.setCustomerNo(request.getParameter("GrpNo"));            //客户号码
	tLCGrpAddressSchema.setAddressNo(request.getParameter("GrpAddressNo"));      //地址号码
	tLCGrpAddressSchema.setGrpAddress(request.getParameter("GrpAddress"));       //单位地址
	loggerDebug("ContPolSave","*******************"+request.getParameter("GrpAddress"));
	tLCGrpAddressSchema.setGrpZipCode(request.getParameter("GrpZipCode"));       //单位邮编
	//保险联系人一
	tLCGrpAddressSchema.setLinkMan1(request.getParameter("LinkMan1"));
	tLCGrpAddressSchema.setDepartment1(request.getParameter("Department1"));
	tLCGrpAddressSchema.setHeadShip1(request.getParameter("HeadShip1"));
	tLCGrpAddressSchema.setPhone1(request.getParameter("Phone1"));
	tLCGrpAddressSchema.setE_Mail1(request.getParameter("E_Mail1"));
	tLCGrpAddressSchema.setFax1(request.getParameter("Fax1"));
	//保险联系人二
	tLCGrpAddressSchema.setLinkMan2(request.getParameter("LinkMan2"));
	tLCGrpAddressSchema.setDepartment2(request.getParameter("Department2"));
	tLCGrpAddressSchema.setHeadShip2(request.getParameter("HeadShip2"));
	tLCGrpAddressSchema.setPhone2(request.getParameter("Phone2"));
	tLCGrpAddressSchema.setE_Mail2(request.getParameter("E_Mail2"));
	tLCGrpAddressSchema.setFax2(request.getParameter("Fax2"));
	
  
  //如果有多业务员的情况，需要录入多业务员信息。
  String multiAgentFlag = request.getParameter("multiagentflag");
  loggerDebug("ContPolSave","#######multiAgentFlag=="+multiAgentFlag);
  loggerDebug("ContPolSave","#######ProposalGrpContNo=="+request.getParameter("ProposalGrpContNo"));
  if(multiAgentFlag!=null && multiAgentFlag.equals("true")){
    //先取得主业务员的信息
    
    String mainAgentCode = request.getParameter("AgentCode");
    //取得其他业务员的信息
    String tMultAgentNum[] = request.getParameterValues("MultiAgentGridNo"); 
    String tMultAgentCode[] = request.getParameterValues("MultiAgentGrid1"); 
    String tMultBusiRate[] = request.getParameterValues("MultiAgentGrid5"); 
    String tMultAgentGroup[] = request.getParameterValues("MultiAgentGrid6"); 
    
    int agentCount = 0;
    String polType = "0";
    double tBusiRate = 0;
    //用于控制小数精度
    DecimalFormat df = new DecimalFormat("0.00");
    
    if(tMultAgentNum!=null){
      agentCount = tMultAgentNum.length;

    }
    
    for(int i=0; i<agentCount; i++){

      LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
      tLACommisionDetailSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));
      tLACommisionDetailSchema.setAgentCode(tMultAgentCode[i]); 
      if (tMultAgentCode[i] == null || "".equals(tMultAgentCode[i])){
         Flag="false";
         Content = " 保存失败，原因是: 业务员编码不能为空！";
         break;
      }  
      tLACommisionDetailSchema.setBusiRate(tMultBusiRate[i]);
      loggerDebug("ContPolSave",tMultBusiRate[i]);
      if(tMultBusiRate[i].equals(""))
        {
        	 Flag="false";
        	 Content = " 保存失败，原因是: 业务员佣金比例不能为空！";
           break;
      	}
      tLACommisionDetailSchema.setAgentGroup(tMultAgentGroup[i]);
      tLACommisionDetailSchema.setPolType("0");
      tLACommisionDetailSchema.setMakeDate(request.getParameter("AppntMakeDate"));  
      tLACommisionDetailSchema.setMakeTime(request.getParameter("AppntMakeTime"));  

//      tBusiRate = tBusiRate + Double.parseDouble(tMultBusiRate[i]);
      
      tLACommisionDetailSet.add(tLACommisionDetailSchema);

    }

    loggerDebug("ContPolSave","--tBusiRate==" + tBusiRate);
    
  }
    
    if(Flag.equals("false"))
    {    	
		FlagStr = "Fail";
    } 
   
   else{  //存入主业务员的信息
  
      mLACommisionDetailSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));
      mLACommisionDetailSchema.setAgentCode(request.getParameter("AgentCode"));
      mLACommisionDetailSchema.setAgentGroup(request.getParameter("AgentGroup"));
      mLACommisionDetailSchema.setBusiRate(1);
      mLACommisionDetailSchema.setPolType("0");
      mLACommisionDetailSchema.setMakeDate(request.getParameter("MakeDate"));  
      mLACommisionDetailSchema.setMakeTime(request.getParameter("MakeTime"));  

	///////////////////////////////////////////////////////////////
	
	//客户告知
	int ImpartCount = 0;
	if (tImpartNum != null) ImpartCount = tImpartNum.length;
	    
	for (int i = 0; i < ImpartCount; i++)	
	{
	LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
	tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
		
		tLCCustomerImpartSchema.setCustomerNo(tLDGrpSchema.getCustomerNo());
		tLCCustomerImpartSchema.setCustomerNoType("0");
		tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
		tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
		tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
	}
	//既往情况告知
	int ImpartCont2 = 0;
	if (tImpartNum2 != null) ImpartCont2 = tImpartNum2.length;
					//loggerDebug("ContPolSave","三大法阿飞诉讼"+ImpartCont2);    
				for (int i = 0; i < ImpartCont2; i++)	
	{	    				//loggerDebug("ContPolSave","三大法阿飞诉讼琐琐碎碎ssss");    
	LCHistoryImpartSchema tLCHistoryImpartSchema = new LCHistoryImpartSchema();
	  //tLCHistoryImpartSchema.setPrtNo(request.getParameter("PrtNo"));
		//tLCHistoryImpartSchema.setGrpContNo(request.getParameter("GrpContNo"));
		//tLCHistoryImpartSchema.setSerialNo(request.getParameter("SerialNo"));

		tLCHistoryImpartSchema.setInsuYear(tInsuYear[i]);
		loggerDebug("ContPolSave","gjgjhgjj"+tInsuYear[i]);                                                            
		tLCHistoryImpartSchema.setInsuYearFlag(tInsuYearFlag[i]);                                                             
		tLCHistoryImpartSchema.setInsuContent(tInsuContent[i]);                                                             
		tLCHistoryImpartSchema.setRate(tRate[i]) ;                                                             
		tLCHistoryImpartSchema.setEnsureContent(tEnsureContent[i]) ;                                                             
		tLCHistoryImpartSchema.setPeoples(tPeoples[i]) ;                                                             
		tLCHistoryImpartSchema.setRecompensePeoples(tRecompensePeoples[i]) ;                                                     
		tLCHistoryImpartSchema.setOccurMoney(tOccurMoney[i]) ;                                                             
		tLCHistoryImpartSchema.setRecompenseMoney(tRecompenseMoney[i]) ;                                                         
		tLCHistoryImpartSchema.setPendingMoney(tPendingMoney[i]) ;
		tLCHistoryImpartSchema.setSerialNo(tSerialNo1[i]) ;				                                                       
		
		tLCHistoryImpartSet.add(tLCHistoryImpartSchema);
	}
	//严重疾病情况告知
	int ImpartCont3 = 0;
	if (tImpartNum3 != null) ImpartCont3 = tImpartNum3.length;
	 
	for (int i = 0; i < ImpartCont3; i++)	
	{  //loggerDebug("ContPolSave","三大法阿飞诉讼"+tOcurTime[i]);  
	  LCDiseaseImpartSchema tLCDiseaseImpartSchema = new LCDiseaseImpartSchema();
 		tLCDiseaseImpartSchema.setOcurTime(tOcurTime[i]);                                                             
		tLCDiseaseImpartSchema.setDiseaseName(tDiseaseName[i]);                                                             
		tLCDiseaseImpartSchema.setDiseasePepoles(tDiseasePepoles[i]) ;                                                             
		tLCDiseaseImpartSchema.setCureMoney(tCureMoney[i]) ;                                                             
		tLCDiseaseImpartSchema.setRemark(tRemark[i]) ; 
		tLCDiseaseImpartSchema.setSerialNo(tSerialNo2[i]) ;	                                                            
		
		tLCDiseaseImpartSet.add(tLCDiseaseImpartSchema);
	}
	int m = 0;
	if (tServKind != null) m = tServKind.length;
	 loggerDebug("ContPolSave","----开始客户服务信息----");
	 loggerDebug("ContPolSave","Mulline 行数 ："+m);
	for (int i = 0; i < m; i++)	
	{  
	   LCGrpServInfoSchema tLCGrpServInfoSchema = new LCGrpServInfoSchema();
 		//存放Mulline中的数据
 		tLCGrpServInfoSchema.setServKind(tServKind[i]);                                                             
		tLCGrpServInfoSchema.setServDetail(tServDetail[i]);                                                             
		tLCGrpServInfoSchema.setServChoose(tServChoose[i]) ;                                                             
		tLCGrpServInfoSchema.setServRemark(tServRemark[i]) ;  
		//从页面上获取的数据
		tLCGrpServInfoSchema.setPrtNo(request.getParameter("PrtNo"));
		//其他数据从后台获取
		tLCGrpServInfoSet.add(tLCGrpServInfoSchema);
	}
  loggerDebug("ContPolSave","end setSchema:");
  // 准备传输数据 VData
  tVData.add( tLCGrpContSchema );
  tVData.add( tLCGrpAppntSchema );
  tVData.add( tLDGrpSchema );
  tVData.add( tLCGrpAddressSchema );
  tVData.add( tLCCustomerImpartSet );   //团体告知信息
  tVData.add( tLCHistoryImpartSet );			//既往告知
  tVData.add( tLCDiseaseImpartSet );			//严重疾病告知
  loggerDebug("ContPolSave",";lajsdkjfas;jkdf");
  tVData.add( tLCGrpServInfoSet );
  loggerDebug("ContPolSave","tVData"+tVData);
	tVData.add(tLACommisionDetailSet); 
	tVData.add(mLACommisionDetailSchema);      
  tVData.add( tG );
  
  loggerDebug("ContPolSave","tLCGrpContSchema:"+tLCGrpContSchema.encode());    
  loggerDebug("ContPolSave","tLCGrpAppntSchema:"+tLCGrpAppntSchema.encode());    
  loggerDebug("ContPolSave","tLDGrpSchema:"+tLDGrpSchema.encode());
  loggerDebug("ContPolSave","tLCGrpAddressSchema:"+tLCGrpAddressSchema.encode());    
  loggerDebug("ContPolSave","tLCCustomerImpartSet:"+tLCCustomerImpartSet.encode());    
  loggerDebug("ContPolSave","tLCHistoryImpartSet:"+tLCHistoryImpartSet.encode());
  loggerDebug("ContPolSave","tLACommisionDetailSet:"+tLACommisionDetailSet.encode());    
  loggerDebug("ContPolSave","mLACommisionDetailSchema:"+mLACommisionDetailSchema.encode());	
  //传递非SCHEMA信息
  TransferData tTransferData = new TransferData();
  String SavePolType="";
  String BQFlag=request.getParameter("BQFlag");
  if(BQFlag==null) SavePolType="0";
  else if(BQFlag.equals("")) SavePolType="0";
  else  SavePolType=BQFlag;
  
  //传递LoadFlag
  mLoadFlag=request.getParameter("LoadFlag"); 
  tTransferData.setNameAndValue("LoadFlag",mLoadFlag);
  loggerDebug("ContPolSave","LoadFlag is : " + request.getParameter("LoadFlag"));

  
  tTransferData.setNameAndValue("SavePolType",SavePolType); //保全保存标记，默认为0，标识非保全
  loggerDebug("ContPolSave","SavePolType，BQ is 2，other is 0 : " + request.getParameter("BQFlag"));
  tVData.addElement(tTransferData);
  
  
  
	if( tAction.equals( "INSERT" )) tOperate = "INSERT||GROUPPOL";
	if( tAction.equals( "UPDATE" )) tOperate = "UPDATE||GROUPPOL";
	if( tAction.equals( "DELETE" )) tOperate = "DELETE||GROUPPOL";
	//GroupContUI tGroupContUI = new GroupContUI();
	String busiName="cardgrpGroupContUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )
	{
		Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " 保存成功! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tBusinessDelegate.getResult();

		// 显示
		if(( tAction.equals( "INSERT" ))||( tAction.equals( "UPDATE" )))
		{
			// 保单信息
			LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema(); 
			mLCGrpContSchema.setSchema(( LCGrpContSchema )tVData.getObjectByObjectName( "LCGrpContSchema", 0 ));
			%>
			   <script language="javascript">
			    	parent.fraInterface.document.all("ProposalGrpContNo").value = "<%=mLCGrpContSchema.getProposalGrpContNo()%>";
			    	parent.fraInterface.document.all("GrpContNo").value = "<%=mLCGrpContSchema.getGrpContNo()%>";
			     parent.fraInterface.document.all("GrpNo").value = "<%=mLCGrpContSchema.getAppntNo()%>";
			     parent.fraInterface.document.all("GrpAddressNo").value = "<%=mLCGrpContSchema.getAddressNo()%>"; 
			         	
			   </script>
			<%		
		}
		else
		{
			%>
			   <script language="javascript">
			     parent.fraInterface.emptyFormElements();
			    </script>
			<%
		
		}
	}
        loggerDebug("ContPolSave","Content:"+Content);	
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

