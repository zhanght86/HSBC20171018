<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：DirectContInsuredSave.jsp
//程序功能：直销险种录入被保人信息保存
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//接收信息，并作校验处理。
	//输入参数
	LCContSchema tLCContSchema = new LCContSchema();
	LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
	LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
	LCAddressSchema tLCAddressSchema = new LCAddressSchema();
	LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
	LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
	LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
	TransferData tTransferData = new TransferData(); 
	//ContInsuredUI tContInsuredUI   = new ContInsuredUI();
	String busiName="tbContInsuredUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//输出参数
	String FlagStr = "";
	String Content = "";
   
	GlobalInput tGI = new GlobalInput();  
	tGI=(GlobalInput)session.getValue("GI");   
	loggerDebug("DirectContInsuredSave","tGI"+tGI);
	if(tGI==null)
	{
		loggerDebug("DirectContInsuredSave","页面失效,请重新登陆");   
		FlagStr = "Fail";        
		Content = "页面失效,请重新登陆";  
	}
	else //页面有效
	{
		CErrors tError = null;
		String tBmCert = "";
		//后面要执行的动作：添加，修改，删除
		String fmAction=request.getParameter("fmAction");
		loggerDebug("DirectContInsuredSave","fmAction:"+fmAction); 
		tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));  
		tLCContSchema.setContNo(request.getParameter("ContNo")); 
		tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
		tLCContSchema.setManageCom(request.getParameter("ManageCom"));
		
		tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
		tmLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured"));
		tmLCInsuredSchema.setRelationToAppnt(request.getParameter("RelationToAppnt"));
		tmLCInsuredSchema.setContNo(request.getParameter("ContNo"));
		tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
		tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
		tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
		tmLCInsuredSchema.setJoinCompanyDate(request.getParameter("JoinCompanyDate"));
		tmLCInsuredSchema.setInsuredPeoples(request.getParameter("InsuredPeoples"));
		tmLCInsuredSchema.setSalary(request.getParameter("Salary"));
		tmLCInsuredSchema.setBankCode(request.getParameter("BankCode"));
		tmLCInsuredSchema.setBankAccNo(request.getParameter("BankAccNo"));                
		tmLCInsuredSchema.setAccName(request.getParameter("AccName"));
		tmLCInsuredSchema.setLicenseType(request.getParameter("LicenseType"));
		
		tLDPersonSchema.setCustomerNo(request.getParameter("InsuredNo"));
		tLDPersonSchema.setName(request.getParameter("InsuredName"));
		tLDPersonSchema.setSex(request.getParameter("InsuredSex"));      
		tLDPersonSchema.setBirthday(request.getParameter("InsuredBirthday"));
		tLDPersonSchema.setIDType(request.getParameter("InsuredIDType"));
		tLDPersonSchema.setIDNo(request.getParameter("InsuredIDNo"));
		tLDPersonSchema.setNativePlace(request.getParameter("InsuredNativePlace"));
		tLDPersonSchema.setNationality(request.getParameter("InsuredNationality"));
		tLDPersonSchema.setMarriage(request.getParameter("InsuredMarriage"));
		tLDPersonSchema.setMarriageDate(request.getParameter("InsuredMarriageDate"));
		tLDPersonSchema.setOccupationType(request.getParameter("InsuredOccupationType"));
		tLDPersonSchema.setOccupationCode(request.getParameter("InsuredOccupationCode"));
		tLDPersonSchema.setLicenseType(request.getParameter("InsuredLicenseType"));

		tLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
		if(request.getParameter("InsuredAddressNo")!=null && !request.getParameter("InsuredAddressNo").equals(""))
		{
			tLCAddressSchema.setAddressNo(request.getParameter("InsuredAddressNo"));  
		}
		tLCAddressSchema.setPostalAddress(request.getParameter("InsuredPostalAddress"));
		tLCAddressSchema.setZipCode(request.getParameter("InsuredZipCode"));
		tLCAddressSchema.setPhone(request.getParameter("InsuredPhone"));
		tLCAddressSchema.setFax(request.getParameter("InsuredFax"));          
		tLCAddressSchema.setMobile(request.getParameter("InsuredMobile"));        
		tLCAddressSchema.setEMail(request.getParameter("InsuredEMail"));
		tLCAddressSchema.setHomeAddress(request.getParameter("InsuredHomeAddress"));        
		tLCAddressSchema.setHomeZipCode(request.getParameter("InsuredHomeZipCode"));
		tLCAddressSchema.setHomePhone(request.getParameter("InsuredHomePhone"));
		tLCAddressSchema.setHomeFax(request.getParameter("InsuredHomeFax"));        
		tLCAddressSchema.setCompanyPhone(request.getParameter("InsuredGrpPhone"));
		tLCAddressSchema.setCompanyAddress(request.getParameter("InsuredGrpAddress"));
		tLCAddressSchema.setCompanyZipCode(request.getParameter("InsuredGrpZipCode"));
		tLCAddressSchema.setCompanyFax(request.getParameter("InsuredGrpFax"));        
		tLCAddressSchema.setGrpName(request.getParameter("InsuredGrpName"));        
		tLCAddressSchema.setProvince(request.getParameter("InsuredProvince"));
		tLCAddressSchema.setCity(request.getParameter("InsuredCity"));
		tLCAddressSchema.setCounty(request.getParameter("InsuredDistrict"));

		String SavePolType="";
		String BQFlag=request.getParameter("BQFlag");
		if(BQFlag==null) SavePolType="0";
		else if(BQFlag.equals("")) SavePolType="0";
		else  SavePolType=BQFlag;   

		tTransferData.setNameAndValue("SavePolType",SavePolType); //保全保存标记，默认为0，标识非保全  
		tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
		tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //家庭单标记 
		tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //团单，个人单标记
		tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //无名单标记
		tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //被保险人人数
		tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //被保险人年龄
		tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //内部客户号
		loggerDebug("DirectContInsuredSave","fmAction=="+fmAction);

		if (fmAction.equals("UPDATE||CONTINSURED")||fmAction.equals("DELETE||CONTINSURED"))
		{
			String tRadio[] = request.getParameterValues("InpInsuredGridSel");
    		String tInsuredNo[] = request.getParameterValues("InsuredGrid1");
			if (tRadio!=null)
			{
				for (int index=0; index< tRadio.length;index++)
				{
					if(tRadio[index].equals("1"))
					{
						tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
						tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
						tOLDLCInsuredDB.setInsuredNo(tInsuredNo[index]);						
					}
				}
				if (tOLDLCInsuredDB.getInsuredNo()==null)
				{
					tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
					tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
					tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
				}
			}
			else
			{
				tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
				tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
				tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
			}
			loggerDebug("DirectContInsuredSave","原来的被保人号码==="+tOLDLCInsuredDB.getInsuredNo());
		}
		String tImpartNum[] = request.getParameterValues("ImpartInsuredGridNo");
		String tImpartVer[] = request.getParameterValues("ImpartInsuredGrid1");            //告知版别
		String tImpartCode[] = request.getParameterValues("ImpartInsuredGrid2");           //告知编码
		String tImpartContent[] = request.getParameterValues("ImpartInsuredGrid3");        //告知内容
		String tImpartParamModle[] = request.getParameterValues("ImpartInsuredGrid4");        //填写内容
		int ImpartCount = 0;
		if (tImpartNum != null) ImpartCount = tImpartNum.length;
		for (int i = 0; i < ImpartCount; i++)	
		{
			LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
			tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ProposalContNo"));
			tLCCustomerImpartSchema.setCustomerNoType("1");
			tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
			tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
			tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
			tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]);
			tLCCustomerImpartSchema.setPatchNo("0");
			tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		}
          
		try
		{
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tLCContSchema);
			tVData.add(tLDPersonSchema);
			tVData.add(tLCCustomerImpartSet);
//			tVData.add(tLCCustomerImpartDetailSet);             
			tVData.add(tmLCInsuredSchema);
			tVData.add(tLCAddressSchema);
			tVData.add(tOLDLCInsuredDB);   
			tVData.add(tTransferData);
			tVData.add(tGI);

			//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
			if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
			{
			Content ="操作成功！";
			FlagStr = "Succ";
			}
		}
		catch(Exception ex)
		{
		Content = "操作失败，原因是:" + ex.toString();
		FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr=="")
		{
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError())
			{                          
			Content ="操作成功！";
			FlagStr = "Succ";
			}
			else                                                                           
			{
			Content = "操作失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
			}
		}
		loggerDebug("DirectContInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
	}

%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>


