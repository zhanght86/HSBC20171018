<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：NALAAgentSave.jsp
//程序功能：
//创建日期：2002-08-16 15:12:33
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.encrypt.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  LAAgentSchema tLAAgentSchema   = new LAAgentSchema();
  LATreeSchema tLATreeSchema = new LATreeSchema();
  LAWarrantorSet tLAWarrantorSet = new LAWarrantorSet();
  //NAALAAgentUI tLAAgent   = new NAALAAgentUI();

  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tIsManager = request.getParameter("hideIsManager");
  String tRearStr = "";
  String tRela  = "";
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  loggerDebug("NALAAgentSave","begin agent schema...");
  //取得代理人信息(增加加密信息1111)
  	LisIDEA tLisIdea = new LisIDEA();
  	tLAAgentSchema.setPassword(tLisIdea.encryptString("1111"));
  	
    tLAAgentSchema.setAgentCode(request.getParameter("AgentCode"));
    //modify by tongmeng 2005-06-03
    //去掉展业机构外部编码前后空格
    String tBranchAttr=request.getParameter("BranchCode");
    tLAAgentSchema.setAgentGroup(tBranchAttr.trim()); //暂存显式代码
    tLAAgentSchema.setBranchCode(request.getParameter("hideBranchCode"));
    tLAAgentSchema.setManageCom(request.getParameter("ManageCom"));
    tLAAgentSchema.setBranchType(request.getParameter("BranchType"));
    tLAAgentSchema.setName(request.getParameter("Name"));
    loggerDebug("NALAAgentSave",request.getParameter("BranchCode"));
    loggerDebug("NALAAgentSave",request.getParameter("hideBranchCode"));
    //tLAAgentSchema.setEntryNo(request.getParameter("EntryNo"));
    tLAAgentSchema.setSex(request.getParameter("Sex"));
    tLAAgentSchema.setBirthday(request.getParameter("Birthday"));
    tLAAgentSchema.setIDType(request.getParameter("IdType"));
    tLAAgentSchema.setIDNo(request.getParameter("IDNo"));
    tLAAgentSchema.setNativePlace(request.getParameter("NativePlace"));
    tLAAgentSchema.setNationality(request.getParameter("Nationality"));
    //tLAAgentSchema.setSource(request.getParameter("Source"));
    //tLAAgentSchema.setBloodType(request.getParameter("BloodType"));
    //tLAAgentSchema.setMarriage(request.getParameter("Marriage"));
    //tLAAgentSchema.setMarriageDate(request.getParameter("MarriageDate"));
    tLAAgentSchema.setPolityVisage(request.getParameter("PolityVisage"));
    tLAAgentSchema.setRgtAddress(request.getParameter("RgtAddress"));
    tLAAgentSchema.setDegree(request.getParameter("Degree"));
    tLAAgentSchema.setGraduateSchool(request.getParameter("GraduateSchool"));
    //tLAAgentSchema.setCreditGrade(request.getParameter("CreditGrade"));
    //tLAAgentSchema.setHomeAddressCode(request.getParameter("HomeAddressCode"));
    tLAAgentSchema.setSpeciality(request.getParameter("Speciality"));
    //tLAAgentSchema.setForeignLevel(request.getParameter("ForeignLevel"));
    tLAAgentSchema.setPostTitle(request.getParameter("PostTitle"));
    tLAAgentSchema.setHomeAddress(request.getParameter("HomeAddress"));
    //tLAAgentSchema.setPostalAddress(request.getParameter("PostalAddress"));
    tLAAgentSchema.setZipCode(request.getParameter("ZipCode"));
    tLAAgentSchema.setPhone(request.getParameter("Phone"));
    tLAAgentSchema.setBP(request.getParameter("BP"));
    tLAAgentSchema.setMobile(request.getParameter("Mobile"));
    tLAAgentSchema.setEMail(request.getParameter("EMail"));
    //tLAAgentSchema.setWorkAge(request.getParameter("WorkAge"));
    tLAAgentSchema.setOldCom(request.getParameter("OldCom"));
    tLAAgentSchema.setOldOccupation(request.getParameter("OldOccupation"));
    tLAAgentSchema.setHeadShip(request.getParameter("HeadShip"));
    //tLAAgentSchema.setRecommendAgent(request.getParameter("RecommendAgent"));
    //tLAAgentSchema.setBusiness(request.getParameter("Business"));
    //tLAAgentSchema.setSaleQuaf(request.getParameter("SaleQuaf"));
    tLAAgentSchema.setQuafNo(request.getParameter("QuafNo"));
    //tLAAgentSchema.setQuafStartDate(request.getParameter("QuafStartDate"));
    tLAAgentSchema.setQuafEndDate(request.getParameter("QuafEndDate"));
    tLAAgentSchema.setDevNo1(request.getParameter("DevNo1"));
    //tLAAgentSchema.setDevNo2(request.getParameter("DevNo2"));
    //tLAAgentSchema.setRetainContNo(request.getParameter("RetainContNo"));
    //tLAAgentSchema.setAgentKind(request.getParameter("AgentKind"));
    tLAAgentSchema.setDevGrade(request.getParameter("DevGrade"));
    tLAAgentSchema.setInsideFlag("1");  //外勤
    //tLAAgentSchema.setFullTimeFlag(request.getParameter("FullTimeFlag"));
    //tLAAgentSchema.setNoWorkFlag(request.getParameter("NoWorkFlag"));
    tLAAgentSchema.setTrainDate(request.getParameter("TrainDate"));
    tLAAgentSchema.setTrainPeriods(request.getParameter("TrainPeriods"));
    tLAAgentSchema.setEmployDate(request.getParameter("EmployDate"));
//    if (request.getParameter("AgentGrade").compareTo("A02")>=0)
//      tLAAgentSchema.setInDueFormDate(request.getParameter("EmployDate"));
    //tLAAgentSchema.setOutWorkDate(request.getParameter("OutWorkDate"));
    //tLAAgentSchema.setApprover(request.getParameter("Approver"));
    //tLAAgentSchema.setApproveDate(request.getParameter("ApproveDate"));
    tLAAgentSchema.setAssuMoney(request.getParameter("AssuMoney"));
    tLAAgentSchema.setAgentState(request.getParameter("AgentState"));
    tLAAgentSchema.setQualiPassFlag(request.getParameter("QualiPassFlag"));
    //tLAAgentSchema.setSmokeFlag(request.getParameter("SmokeFlag"));
    tLAAgentSchema.setBankCode(request.getParameter("BankCode"));
    tLAAgentSchema.setBankAccNo(request.getParameter("BankAccNo"));
    tLAAgentSchema.setRemark(request.getParameter("Remark"));
    tLAAgentSchema.setOperator(request.getParameter("Operator"));
    //取得行政信息--在bl中设置职级及系列
    tLATreeSchema.setAgentCode(request.getParameter("AgentCode"));
    tLATreeSchema.setManageCom(request.getParameter("ManageCom"));
    //tLATreeSchema.setAgentGroup(request.getParameter("hideAgentGroup"));
    tLATreeSchema.setIntroAgency(request.getParameter("IntroAgency").trim());
    tLATreeSchema.setUpAgent(request.getParameter("UpAgent"));
    tLATreeSchema.setAgentSeries(request.getParameter("AgentSeries"));
    tLATreeSchema.setAgentGrade(request.getParameter("AgentGrade"));
    
    //增加育成关系的四个字段,这样后台就不用拆分了.
    
       TransferData tTransferData=new TransferData();
     
     //组育成人
    String agentcode1=request.getParameter("RearAgent");
     //部育成人 
      String agentcode2=request.getParameter("RearDepartAgent");
     //区育成人  
      String agentcode3=request.getParameter("RearSuperintAgent");
     //总监育成人  
      String agentcode4=request.getParameter("RearAreaSuperintAgent");
      
      tTransferData.setNameAndValue("RearAgent",agentcode1);
      tTransferData.setNameAndValue("RearDepartAgent",agentcode2);
      tTransferData.setNameAndValue("RearSuperintAgent",agentcode3);
      tTransferData.setNameAndValue("RearAreaSuperintAgent",agentcode4);
    //tongmeng 2006-04-26 add
    //增加新职级编码,入司职级
    tLATreeSchema.setAgentGrade1(request.getParameter("AgentGrade1"));
    tLATreeSchema.setEmployGrade(request.getParameter("AgentGrade"));
    loggerDebug("NALAAgentSave","$$$AgentGrade:"+request.getParameter("AgentGrade"));
    
    if (!tOperate.equals("DELETE||MAIN") && tLATreeSchema.getAgentGrade().compareTo("A03")>0)
    {  
      if (!request.getParameter("RearAgent").equals(""))
      {
        tRearStr = request.getParameter("RearAgent").trim();
        tLATreeSchema.setEduManager(request.getParameter("RearAgent").trim()); 
        loggerDebug("NALAAgentSave","aa"+request.getParameter("RearAgent").trim()+"aa"); 
      }
      if (tLATreeSchema.getAgentGrade().compareTo("A05")>0)
      {
        tRearStr += ":";
        tLATreeSchema.setEduManager("");
        if (!request.getParameter("RearDepartAgent").equals(""))
        {
          tRearStr += request.getParameter("RearDepartAgent").trim(); 
          tLATreeSchema.setEduManager(request.getParameter("RearDepartAgent").trim());  
        }
        if (tLATreeSchema.getAgentGrade().compareTo("A07")>0)
        { 
           tRearStr += ":";
           tLATreeSchema.setEduManager("");
           if (!request.getParameter("RearSuperintAgent").equals(""))
           {
              tRearStr += request.getParameter("RearSuperintAgent").trim(); 
              tLATreeSchema.setEduManager(request.getParameter("RearSuperintAgent").trim());  
           }           
	   if (tLATreeSchema.getAgentGrade().compareTo("A08")>0)
	   { 
	      tRearStr += ":";
	      tLATreeSchema.setEduManager("");
	      if (!request.getParameter("RearAreaSuperintAgent").equals(""))
	      {
	          tRearStr += request.getParameter("RearAreaSuperintAgent").trim(); 
	          tLATreeSchema.setEduManager(request.getParameter("RearAreaSuperintAgent").trim());  
	      }
	   }
        }
      }
      loggerDebug("NALAAgentSave","育成链："+tRearStr);
    }
    tLATreeSchema.setAscriptSeries(tRearStr);  
  //取得担保人信息
  int lineCount = 0;
  String arrCount[] = request.getParameterValues("WarrantorGridNo");
  String tCautionerName[] = request.getParameterValues("WarrantorGrid1");
  String tCautionerSex[] = request.getParameterValues("WarrantorGrid2");
  String tCautionerID[] = request.getParameterValues("WarrantorGrid3");
  String tCautionerCom[] = request.getParameterValues("WarrantorGrid4");
  String tHomeAddress[] = request.getParameterValues("WarrantorGrid5");
  String tCautionerMobile[] = request.getParameterValues("WarrantorGrid6");
  String tZipCode[] = request.getParameterValues("WarrantorGrid7");
  String tPhone[] = request.getParameterValues("WarrantorGrid8");
  String tRelation[] = request.getParameterValues("WarrantorGrid9");
  lineCount = arrCount.length; //行数
  LAWarrantorSchema tLAWarrantorSchema;
  for(int i=0;i<lineCount;i++)
  {
    tLAWarrantorSchema = new LAWarrantorSchema();
    tLAWarrantorSchema.setAgentCode(request.getParameter("AgentCode").trim());    
    //tLAWarrantorSchema.setSerialNo(i+1);    
    tLAWarrantorSchema.setCautionerName(tCautionerName[i]);
    tLAWarrantorSchema.setCautionerSex(tCautionerSex[i]);
    tLAWarrantorSchema.setCautionerID(tCautionerID[i]);
    tLAWarrantorSchema.setCautionerCom(tCautionerCom[i]);
    tLAWarrantorSchema.setHomeAddress(tHomeAddress[i]);
    tLAWarrantorSchema.setMobile(tCautionerMobile[i]);
    tLAWarrantorSchema.setZipCode(tZipCode[i]);
    tLAWarrantorSchema.setPhone(tPhone[i]);
    tLAWarrantorSchema.setRelation(tRelation[i]);
    tLAWarrantorSet.add(tLAWarrantorSchema);
    loggerDebug("NALAAgentSave","for:"+tCautionerName[i]);
  }
  loggerDebug("NALAAgentSave","end 担保人信息...");

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
  
  tVData.add(tG);
  tVData.add(tIsManager);
  tVData.addElement(tLAAgentSchema);
  tVData.addElement(tLATreeSchema);
  tVData.addElement(tLAWarrantorSet);
  tVData.addElement(tTransferData);
  
  /*try
  { 
    loggerDebug("NALAAgentSave","this will save the data!!!");
    tLAAgent.submitData(tVData,tOperate);
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  if (!FlagStr.equals("Fail"))
  {
    tError = tLAAgent.mErrors;
    if (!tError.needDealError())
    {*/
    String busiName="NAALAAgentUI";
    String mDescType="保存";
  	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  	  if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
  	  {    
  	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
  	       { 
  				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
  				FlagStr = "Fail";
  		   }
  		   else
  		   {
  				Content = mDescType+"失败";
  				FlagStr = "Fail";				
  		   }
  	  }
  	  else
  	  {
        if (tOperate.equals("INSERT||MAIN"))
        {
           tVData.clear();
           //tVData = tLAAgent.getResult();
           tVData = tBusinessDelegate.getResult();
           tLAAgentSchema.setSchema((LAAgentSchema)tVData.getObjectByObjectName("LAAgentSchema",0));
        }
    	Content = " 保存成功! ";
    	FlagStr = "Succ";
  	  }
   /* }
    else
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }*/
 
  //添加各种预处理

%>
<html>
<script language="javascript">
        parent.fraInterface.document.all('AgentCode').value = '<%=tLAAgentSchema.getAgentCode()%>';
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

