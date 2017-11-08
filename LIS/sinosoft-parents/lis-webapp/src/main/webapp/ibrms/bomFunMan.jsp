<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//更新记录： delete
	//更新人:  
	//更新日期:  
	//更新原因/内容：。
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.menumang.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="java.sql.*"%>

<%@page import="com.sinosoft.ibrms.*"%>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="./bomMain.js"></SCRIPT>
<head>
<%@include file="../common/jsp/UsrCheck.jsp"%>
</head>
<body>
<%
//-----------------BOM操作-----------------------
	loggerDebug("bomFunMan","start jsp");

	String FlagStr = "Fail";
	String actionStr = request.getParameter("Action");
	loggerDebug("bomFunMan","$$$actionStr:" + actionStr);
	
	int action = -1;	
	if (actionStr.compareTo("INSERT") == 0)
		action = 0;
	if (actionStr.compareTo("DELETE") == 0)
		action = 1;
	
	switch (action) {
		case 0 : //insert
		case 1:			
			String tRadio2[] = request.getParameterValues("InpQueryGrpGridSel");
			if (tRadio2 == null) {
				loggerDebug("bomFunMan","tRadio is null");
				FlagStr = "Fail";
			}
			if (tRadio2 != null) {
				loggerDebug("bomFunMan","tRadio is not null");
				int index = 0;
				for (; index < tRadio2.length; index++) {
					if (tRadio2[index].equals("1"))
						break;
				}
				if (index == tRadio2.length) {
					loggerDebug("bomFunMan","没有选中对象!");
				} else {
					String tNodeCode2[] = request
							.getParameterValues("QueryGrpGrid1");
					String eName = tNodeCode2[index];

					loggerDebug("bomFunMan","jsp--eName:" + eName);
					String tNodeCode3[] = request
							.getParameterValues("QueryGrpGrid2");
					String cName = tNodeCode3[index];

					loggerDebug("bomFunMan","jsp--cName:" + cName);
				
					LRBOMSchema tLRBOMSchema = new LRBOMSchema();
					tLRBOMSchema.setName(eName);
					tLRBOMSchema.setCNName(cName);
					LDBomUI tLDBomUI = new LDBomUI();				
					VData tData = new VData();
					tData.add(tLRBOMSchema);
					if (tLDBomUI.submitData(tData, "DELETE")) {
						FlagStr = "success";
					} else {
						FlagStr = "fail";
					}				
				}
			}
			break;
	}
	
	//-----------------词条操作-----------------------
	String itemactionStr = request.getParameter("itemAction");
	loggerDebug("bomFunMan","itemactionStr:"+itemactionStr);
	
	int itemaction = -1;
	if (itemactionStr.compareTo("INSERT") == 0)
		itemaction = 0;
	if (itemactionStr.compareTo("DELETE") == 0)
		itemaction = 1;
	
	switch (itemaction) {		
		case 0 : //insert
		
		case 1 :			
			String tRadio2[] = request.getParameterValues("InpItemGridSel");
			if (tRadio2 == null) {
				loggerDebug("bomFunMan","tRadio is null");
				FlagStr = "Fail";
			}
			if (tRadio2 != null) {
				loggerDebug("bomFunMan","tRadio is not null");
				int index = 0;
				for (; index < tRadio2.length; index++) {
					if (tRadio2[index].equals("1"))
						break;
				}
				if (index == tRadio2.length) {
					loggerDebug("bomFunMan","没有选中对象!");
				} else {
					String tNodeCode2[] = request
							.getParameterValues("ItemGrid1");
					String eName = tNodeCode2[index];
					loggerDebug("bomFunMan","eName"+eName);

					loggerDebug("bomFunMan","jsp--eName:" + eName);
					String tNodeCode3[] = request
							.getParameterValues("ItemGrid2");
					String bName = tNodeCode3[index];

					loggerDebug("bomFunMan","jsp--cName:" + bName);
				
					LRBOMItemSchema tLRBOMItemSchema = new LRBOMItemSchema();
					tLRBOMItemSchema.setName(eName);
					tLRBOMItemSchema.setBOMName(bName);
					LDItemUI tLDItemUI = new LDItemUI();				
					VData tData = new VData();
					tData.add(tLRBOMItemSchema);					
					
					if (tLDItemUI.submitData(tData, "DELETE")) {
						FlagStr = "success";
					} else {
						FlagStr = "fail";
					}				
				}
			}
			break;
	}
	loggerDebug("bomFunMan","FlagStr : " + FlagStr);
	loggerDebug("bomFunMan","end of jsp");
%>

<script>
	parent.fraInterface.afterSubmit("<%=FlagStr%>");	
</script>

</body>

