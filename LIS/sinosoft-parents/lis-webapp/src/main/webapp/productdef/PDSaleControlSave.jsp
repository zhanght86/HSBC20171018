<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDSaleControlSave.jsp
//程序功能：险种销售控制定义
//创建日期：2009-3-17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>    
<%
 //接收信息，并作校验处理。
 //输入参数
 String flag=request.getParameter("flag");
 

 //PDSaleControlBL pDSaleControlBL = new PDSaleControlBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 String transact = "";
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 LMRiskComCtrlSchema lMRiskComCtrlSchema=null;
 PD_LMRiskComCtrlSchema pd_LMRiskComCtrlSchema=null;
 if("0".equals(flag)){
	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	 operator = request.getParameter("operator");
	 
	 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));

	 String values[] = request.getParameterValues("Mulline9Grid4");
	 java.util.ArrayList list = new java.util.ArrayList();
	 String flag1="0";
	 String[] tablenames = {"PD_LMRiskComCtrl","PD_LDRiskRule"};
		for(int j=0;j<tablenames.length;j++){
	 		TransferData transferData = new TransferData();
			list.clear();
			String tablename = tablenames[j];
		 for(int i = 0; i < values.length; i++){ 
			 if(values[i]==""||values[i]==null){
			 	flag1="1";
		 	 }
			 if(!("PD_LDRiskRule".equals(tablename)&&i==4)){			 
		     	list.add(values[i]);
			 }
		 }
	 if(flag1=="1"){
	 	Content = " "+"处理失败，原因是:"+" "+"没有录入完整信息"+"";
	  	FlagStr = "Fail";
	 }else{
		 	transferData.setNameAndValue("list",list);
			transferData.setNameAndValue("tableName",tablename);
			transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
			transferData.setNameAndValue("pageFlag",flag);
		
		 try{
			  // 准备传输数据 VData
			  VData tVData = new VData();
			  tVData.add(tG);
			  tVData.add(transferData);
			  //pDSaleControlBL.submitData(tVData,operator);
			  String busiName="PDSaleControlBL";
			  
			  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  //String tDiscountCode = "";
			  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
				  	VData rVData = tBusinessDelegate.getResult();
			    	Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
			  		FlagStr = "Fail";
			  }else {
			    	Content = " "+"处理成功!"+" ";
			  		FlagStr = "Succ";
			  }
		 }catch(Exception ex){
			  Content = ""+"保存失败，原因是:"+"" + ex.toString();
			  FlagStr = "Fail";
		 }
	 }
	 
 	}
 }else{
	 
		TransferData transferData = new TransferData();

 		lMRiskComCtrlSchema = new LMRiskComCtrlSchema();
 		lMRiskComCtrlSchema.setRiskCode(request.getParameter("ReRiskCode"));
 		lMRiskComCtrlSchema.setSaleChnl(request.getParameter("ReSaleChnl"));
 		lMRiskComCtrlSchema.setManageComGrp(request.getParameter("ReManageComGrp"));
 		lMRiskComCtrlSchema.setCurrency(request.getParameter("ReCurrency"));
 		lMRiskComCtrlSchema.setLISStartDate(request.getParameter("LISStartDate"));
 		lMRiskComCtrlSchema.setLISEndDate(request.getParameter("LISEndDate"));
 		lMRiskComCtrlSchema.setStartDate(request.getParameter("PPLStartDate"));
 		lMRiskComCtrlSchema.setEndDate(request.getParameter("PPLEndDate"));
 		lMRiskComCtrlSchema.setOperator(tG.Operator);
 		
 		pd_LMRiskComCtrlSchema = new PD_LMRiskComCtrlSchema();
		pd_LMRiskComCtrlSchema.setRiskCode(request.getParameter("ReRiskCode"));
		pd_LMRiskComCtrlSchema.setSaleChnl(request.getParameter("ReSaleChnl"));
		pd_LMRiskComCtrlSchema.setManageComGrp(request.getParameter("ReManageComGrp"));
		pd_LMRiskComCtrlSchema.setCurrency(request.getParameter("ReCurrency"));
		pd_LMRiskComCtrlSchema.setLISStartDate(request.getParameter("LISStartDate"));
		pd_LMRiskComCtrlSchema.setLISEndDate(request.getParameter("LISEndDate"));
		pd_LMRiskComCtrlSchema.setStartDate(request.getParameter("PPLStartDate"));
		pd_LMRiskComCtrlSchema.setEndDate(request.getParameter("PPLEndDate"));
		pd_LMRiskComCtrlSchema.setOperator(tG.Operator);
 		
		transact = request.getParameter("Transact");
	String busiName = "PDSaleControlBL";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	transferData.setNameAndValue("pageFlag",flag);
	try {
		VData tVData = new VData();
		tVData.add(lMRiskComCtrlSchema);
		tVData.add(pd_LMRiskComCtrlSchema);
		tVData.add(tG);
		tVData.add(transferData);
		tBusinessDelegate.submitData(tVData, transact, "PDSaleControlBL");
	} catch (Exception ex) {
		Content = transact + ""+"失败，原因是:"+"" + ex.toString();
		FlagStr = "Fail";
	}

	if ("".equals(FlagStr)) {
		try {
			tError = tBusinessDelegate.getCErrors();

			if (!tError.needDealError()) {
		if (transact.equals("INSERT")) {
			transact = ""+"录入"+"";
		}
		if (transact.equals("UPDATE")) {
			transact = ""+"修改"+"";
		}
		if (transact.equals("DELETE")) {
			transact = ""+"删除"+"";
		}
		Content = transact + ""+"成功"+"";
		FlagStr = "Succ";
			} else {
		Content = transact + " "+"失败，原因是:"+"" + tError.getFirstError();
		FlagStr = "Fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 }
 %>                      
	<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

