<%
/***************************************************************
 * <p>ProName：PreCustomerManageSave.jsp</p>
 * <p>Title：准客户维护界面</p>
 * <p>Description：准客户维护界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LDPreGrpSchema"%>
<%@page import="com.sinosoft.lis.schema.LDPreGrpLinkInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LDPreGrpSalerSchema"%>
<%@page import="com.sinosoft.lis.vschema.LDPreGrpSalerSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tPreCustomerNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			tPreCustomerNo = request.getParameter("PreCustomerNo");
			String tPreCustomerName = request.getParameter("PreCustomerName");
			String tIDType = request.getParameter("IDType");
			String tIDNo = request.getParameter("IDNo");
			String tGrpNature = request.getParameter("GrpNature");
			String tBusiCategory = request.getParameter("BusiCategory");
			String tSumNumPeople = request.getParameter("SumNumPeople");
			String tPreSumPeople = request.getParameter("PreSumPeople");
			String tPreSumPrem = request.getParameter("PreSumPrem");
			String tUpCustomerNo = request.getParameter("UpCustomerNo");
			String tSaleChannel = request.getParameter("SaleChannel");
			String tProvinceCode = request.getParameter("ProvinceCode");
			String tCityCode = request.getParameter("CityCode");
			String tCountyCode = request.getParameter("CountyCode");
			String tDetailAddress = request.getParameter("DetailAddress");
			String tCustomerIntro = request.getParameter("CustomerIntro");
			
			String tLinkMan = request.getParameter("LinkMan");
			String tMobile = request.getParameter("Mobile");
			String tPhone = request.getParameter("Phone");
			String tDepart = request.getParameter("Depart");
			String tPost = request.getParameter("Post");
			String tEmail = request.getParameter("Email");
			System.out.println("555555555");
			LDPreGrpSalerSet tLDPreGrpSalerSet = new LDPreGrpSalerSet();
			String tGridNo[] = request.getParameterValues("SalerGridNo");
			if (tGridNo!=null && tGridNo.length>0) {
				
				String tSalerCode[] = request.getParameterValues("SalerGrid1");
				
				for (int i=0; i<tGridNo.length; i++) {
				
					if (tSalerCode[i]!=null && !"".equals(tSalerCode[i])) {
						
						LDPreGrpSalerSchema tLDPreGrpSalerSchema = new LDPreGrpSalerSchema();
						tLDPreGrpSalerSchema.setSalerCode(tSalerCode[i]);
						
						tLDPreGrpSalerSet.add(tLDPreGrpSalerSchema);
					}
				}
			}
			System.out.println("44444444");
			LDPreGrpSchema tLDPreGrpSchema = new LDPreGrpSchema();
			System.out.println("222222112222");
			tLDPreGrpSchema.setPreCustomerNo(tPreCustomerNo);
			tLDPreGrpSchema.setPreCustomerName(tPreCustomerName);
			tLDPreGrpSchema.setIDType(tIDType);
			tLDPreGrpSchema.setIDNo(tIDNo);
			tLDPreGrpSchema.setGrpNature(tGrpNature);
			System.out.println("222222332222");
			tLDPreGrpSchema.setBusiCategory(tBusiCategory);
			tLDPreGrpSchema.setSumNumPeople(tSumNumPeople);
			tLDPreGrpSchema.setPreSumPeople(tPreSumPeople);
			tLDPreGrpSchema.setPreSumPrem(tPreSumPrem);
			System.out.println("222222442222");
			tLDPreGrpSchema.setUpCustomerNo(tUpCustomerNo);
			tLDPreGrpSchema.setSaleChannel(tSaleChannel);
			tLDPreGrpSchema.setProvince(tProvinceCode);
			tLDPreGrpSchema.setCity(tCityCode);
			tLDPreGrpSchema.setCounty(tCountyCode);
			tLDPreGrpSchema.setDetailAddress(tDetailAddress);
			tLDPreGrpSchema.setCustomerIntro(tCustomerIntro);
			System.out.println("2222222");
			LDPreGrpLinkInfoSchema tLDPreGrpLinkInfoSchema = new LDPreGrpLinkInfoSchema();
			tLDPreGrpLinkInfoSchema.setPreCustomerNo(tPreCustomerNo);
			tLDPreGrpLinkInfoSchema.setLinkMan(tLinkMan);
			tLDPreGrpLinkInfoSchema.setMobile(tMobile);
			tLDPreGrpLinkInfoSchema.setPhone(tPhone);
			tLDPreGrpLinkInfoSchema.setDepart(tDepart);
			tLDPreGrpLinkInfoSchema.setPost(tPost);
			tLDPreGrpLinkInfoSchema.setEmail(tEmail);
			System.out.println("66666666");
			String tProvinceName = request.getParameter("ProvinceName");
			String tCityName = request.getParameter("CityName");
			String tCountyName = request.getParameter("CountyName");
			
			TransferData vTransferData = new TransferData();
			vTransferData.setNameAndValue("ProvinceName",tProvinceName);
			vTransferData.setNameAndValue("CityName",tCityName);
			vTransferData.setNameAndValue("CountyName",tCountyName);
			
			System.out.println("33333333");
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLDPreGrpSchema);
			tVData.add(tLDPreGrpLinkInfoSchema);
			tVData.add(tLDPreGrpSalerSet);
			tVData.add(vTransferData);
			System.out.println("1111111");
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "PreCustomerUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
				if ("INSERT".equals(tOperate)) {
					TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tPreCustomerNo =(String)tTransferData.getValueByName("PreCustomerNo");
				}
			}
			System.out.println("22222");
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tPreCustomerNo%>");
</script>
</html>
