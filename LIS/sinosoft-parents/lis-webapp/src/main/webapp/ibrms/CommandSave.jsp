<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�CommandSave.jsp
	//�����ܣ����,�޸�,ɾ�������
	//�������ڣ� 2008-9-17 
	//������  �� 
	//���¼�¼��
	//
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//-----------------�\�������-----------------------
	loggerDebug("CommandSave","start jsp");
	String FlagStr = "";
	CErrors tError = null;
	String Content = "";
	String transact = request.getParameter("Transact");
	loggerDebug("CommandSave",transact + ":���������");

	int action = -1;
	if (transact.compareTo("INSERT") == 0)
		action = 0;
	if (transact.compareTo("UPDATE") == 0)
		action = 1;
	if (transact.compareTo("DELETE") == 0)
		action = 2;

	VData tVData = new VData();
	LRCommandSchema tLRCommandSchema = new LRCommandSchema();
	if (action == 0 || action == 1) {
		String Name = request.getParameter("Name").trim();
		String display = request.getParameter("display");
		String implenmation = request.getParameter("implenmation");
		String valid = request.getParameter("Valid");
		String commandType = request.getParameter("commandType");
		String resulttype = request.getParameter("resulttype");
		String paratype = request.getParameter("paratype");
		String paraNum = request.getParameter("ParaNum");
		String Description = request.getParameter("Description");
		
		String CommType = request.getParameter("CommType");
		String CommDetail = request.getParameter("CommDetail");
		
		tLRCommandSchema.setName(Name);
		tLRCommandSchema.setDisplay(display);
		tLRCommandSchema.setImplenmation(implenmation);
		tLRCommandSchema.setValid(valid);
		tLRCommandSchema.setCommandType(commandType);
		tLRCommandSchema.setResultType(resulttype);
		tLRCommandSchema.setParaType(paratype);
		tLRCommandSchema.setParaNum(paraNum);
		tLRCommandSchema.setDescription(Description);
		
		tLRCommandSchema.setCommType(CommType);
		tLRCommandSchema.setCommDetail(CommDetail);
		//tLRCommandSchema  Schema���� ��������Ӳ���������
	}
	loggerDebug("CommandSave","action:" + action);
	
	String busiName="CommandUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	switch (action) {
		case 0 : //insert
			tVData.clear();
			tVData.add(tLRCommandSchema);
			//CommandUI tCommandUI = new CommandUI();
			//if (tCommandUI.submitData(tVData, transact)) {
			if(tBusinessDelegate.submitData(tVData, transact,busiName))
			{

				Content = transact + " �ɹ�";
				FlagStr = "Succ";
			} else {
				tError = tBusinessDelegate.getCErrors();
				loggerDebug("CommandSave","ʧ��");
				Content = transact + " ʧ�ܣ�ԭ����:"
						+ tError.getFirstError();
				FlagStr = "Fail";
			}
			loggerDebug("CommandSave","FlagStr:" + FlagStr);
			break;
		case 1 ://update
			tVData.clear();
			tVData.add(tLRCommandSchema);
			//CommandUI mCommandUI = new CommandUI();
			//if (mCommandUI.submitData(tVData, transact)) {
			if(tBusinessDelegate.submitData(tVData, transact,busiName))
			{
				Content = transact + " �ɹ�";
				FlagStr = "Succ";
			} else {
				
				tError = tBusinessDelegate.getCErrors();
				loggerDebug("CommandSave","ʧ��");
				Content = transact + " ʧ�ܣ�ԭ����:"
						+ tError.getFirstError();
				FlagStr = "Fail";
			}
			loggerDebug("CommandSave","FlagStr:" + FlagStr);
			break;
		case 2 : //delete		
			String tRadio2[] = request
					.getParameterValues("InpQueryGrpGridSel");
			if (tRadio2 == null) {
				loggerDebug("CommandSave","tRadio is null");
				FlagStr = "Fail";
			}
			if (tRadio2 != null) {
				loggerDebug("CommandSave","tRadio is not null");
				int index = 0;
				for (; index < tRadio2.length; index++) {
					if (tRadio2[index].equals("1"))
						break;
				}
				if (index == tRadio2.length) {
					loggerDebug("CommandSave","û��ѡ�ж���!");
				} else {
					String tNodeCode2[] = request
							.getParameterValues("QueryGrpGrid1");
					String Name = tNodeCode2[index];

					loggerDebug("CommandSave","jsp--Name:" + Name);

					LRCommandSchema mLRCommandSchema = new LRCommandSchema();
					mLRCommandSchema.setName(Name);
					//CommandUI rCommandUI = new CommandUI();
					VData tData = new VData();
					tData.add(mLRCommandSchema);
					//if (rCommandUI.submitData(tData, transact)) {
					if(tBusinessDelegate.submitData(tData, transact,busiName))
					{
						FlagStr = "success";
						Content = "ɾ��������ɹ�";
					} else {
						
						tError = tBusinessDelegate.getCErrors();
						FlagStr = "fail";
						Content = "ɾ�������ʧ��";
					}
				}
			}
			break;
	}
%>

<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
