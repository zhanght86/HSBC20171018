
<%
	//程序名称：PEdorTypeIOSubmit.jsp
	//程序功能：
	//创建日期：2005-5-17 11:49上午
	//创建人  ：lizhuo
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LCContDB"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.service.*" %> 

<%
	//接收信息，并作校验处理。
	System.out.println("-----PEdorTypeIOSubmit.jsp---");
	LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//	EdorDetailUI tEdorDetailUI = new EdorDetailUI();
//	GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 String GbusiName="GEdorDetailUI";
	 BusinessDelegate GBusinessDelegate=BusinessDelegate.getBusinessDelegate();

	CErrors tError = new CErrors();
	String FlagStr = "";
	String Content = "";
	String[] Result = new String[4];

	String transact = request.getParameter("fmtransact");
	String AppObj = request.getParameter("AppObj");

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//个人保单批改信息
	System.out.println("---------------------------------------");
	System.out.println("EdorAcceptNo:" + request.getParameter("EdorAcceptNo"));
	System.out.println("ContNo:" + request.getParameter("ContNo"));
	System.out.println("EdorNo:" + request.getParameter("EdorNo"));
	System.out.println("EdorType:" + request.getParameter("EdorType"));
	System.out.println("InsuredNo:" + request.getParameter("InsuredNo"));
	System.out.println("OccupationType:" + request.getParameter("OccupationType"));
	System.out.println("OccupationCode:" + request.getParameter("OccupationCode"));
	System.out.println("Occupation:" + request.getParameter("Occupation"));
	System.out.println("----------------------------------------");

	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
	tLPEdorItemSchema.setPolNo("000000");
	tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
	tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
	tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
	tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

	tLPInsuredSchema.setContNo(request.getParameter("ContNo"));
	tLPInsuredSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPInsuredSchema.setEdorType(request.getParameter("EdorType"));
	tLPInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
	tLPInsuredSchema.setOccupationType(request.getParameter("OccupationType"));
	tLPInsuredSchema.setOccupationCode(request.getParameter("OccupationCode"));
	tLPInsuredSchema.setWorkType(request.getParameter("Occupation"));
	//System.out.println("========================"+tLPEdorItemSchema.encode());
	//System.out.println("========================"+tLPInsuredSchema.encode());

	String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartGrid1"); //告知版别
	String tImpartCode[] = request.getParameterValues("ImpartGrid2"); //告知编码
	String tImpartContent[] = request.getParameterValues("ImpartGrid3"); //告知内容
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4"); //填写内容
	LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
	LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
	
	//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
	LCContDB tLCContDB = new LCContDB();
	tLCContDB.setContNo(request.getParameter("ContNo"));
	tLCContDB.getInfo();
	//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
	
	int ImpartCount = 0;
	if (tImpartNum != null)
		ImpartCount = tImpartNum.length;
	
	System.out.println(ImpartCount);
	if (ImpartCount != 0) {
		for (int i = 0; i < ImpartCount; i++) {
			if (tImpartCode[i] == null || tImpartCode[i].equals("") || tImpartContent[i] == null
					|| tImpartContent[i].equals("") || tImpartParamModle[i] == null
					|| tImpartParamModle[i].equals("") || tImpartVer[i] == null || tImpartVer[i].equals("")) {
			}
			else {
				tLCCustomerImpartSchema = new LCCustomerImpartSchema();
				//tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
				//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
				tLCCustomerImpartSchema.setProposalContNo(tLCContDB.getPrtNo());
				//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
				tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
				tLCCustomerImpartSchema.setCustomerNo(request.getParameter("InsuredNo"));
				tLCCustomerImpartSchema.setCustomerNoType("1");
				tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
				tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
				tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
				tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]);
				tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
		}
		System.out.println("end set schema 告知信息..."+ImpartCount);
	}

	try {
		// 准备传输数据 VData
		VData tVData = new VData();

		//保存个人保单信息(保全)
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemSchema);
		tVData.addElement(tLPInsuredSchema);
		tVData.addElement(tLCCustomerImpartSet);

		if ("G".equalsIgnoreCase(AppObj)) {
			LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
			tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
			tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
			tVData.addElement(tLPGrpEdorItemSchema);
//			tGEdorDetailUI.submitData(tVData, transact);
			GBusinessDelegate.submitData(tVData, transact ,GbusiName);
		}
		else {
//			tEdorDetailUI.submitData(tVData, transact);
			tBusinessDelegate.submitData(tVData, transact ,busiName);
		}
	}
	catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		if ("G".equals(AppObj))
//			tError = tGEdorDetailUI.mErrors;
			tError = GBusinessDelegate.getCErrors(); 
		else
//			tError = tEdorDetailUI.mErrors;
			tError = tBusinessDelegate.getCErrors(); 

		if (!tError.needDealError()) {
			Content = " 保存成功";
			FlagStr = "Success";
		}
		else {
			Content = " 保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>

<html>
<head>
<script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
