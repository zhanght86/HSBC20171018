<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%
	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	String tAction = "";
	String tEdorNo = "";
	String tOperate = "";
	String tEdorType="";
	String tContNo = "";
	tContNo = request.getParameter("ContNo");
	tEdorNo = request.getParameter("EdorNo");
	tEdorType = request.getParameter("EdorType");
	tAction = request.getParameter("fmAction");
	tOperate = tAction;
	loggerDebug("BQUWConfirmSave","动作是:" + tAction);
	loggerDebug("BQUWConfirmSave","save....111111");
	try {
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");
		VData tVData = new VData();
		String tGridNum[] = request.getParameterValues("BnfGridNo");
		String tCustomerIdea[] = request.getParameterValues("GrpGrid9");
		String ttCustomerIdea = tCustomerIdea[0];
		
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();
		tLPCUWMasterSchema.setEdorNo(tEdorNo);
		tLPCUWMasterSchema.setEdorType(tEdorType);
		tLPCUWMasterSchema.setContNo(tContNo);
		tLPCUWMasterSchema.setCustomerIdea(ttCustomerIdea);

		tVData.addElement(tLPCUWMasterSchema);
		tVData.addElement(tG);
		//tVData.addElement(tTransferData);

		// 数据传输
		BQUWConfirmUI tBQUWConfirmUI = new BQUWConfirmUI();
		if (!tBQUWConfirmUI.submitData(tVData, tOperate)) {
			tError = tBQUWConfirmUI.mErrors;
			Content = tError.getFirstError();
			FlagStr = "Fail";
			loggerDebug("BQUWConfirmSave","Content:" + Content);
		} else {
			Content = "保存成功!";
			FlagStr = "Succ";
			loggerDebug("BQUWConfirmSave","Content:" + Content);
			tVData.clear();
			tVData = tBQUWConfirmUI.getResult();
		}
	} catch (Exception e1) {
		Content = " 保存失败，原因是: " + e1.toString().trim();
		FlagStr = "Fail";
		loggerDebug("BQUWConfirmSave",Content);
	}
%>

<html>
<script language="javascript">
              try
              {
                parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
              }  catch(ex) {
                //alert("after Save but happen err:" + ex);
              }
              </script>

</html>

