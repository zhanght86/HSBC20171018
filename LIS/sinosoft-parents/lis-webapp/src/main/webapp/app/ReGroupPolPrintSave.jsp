<%
//�������ƣ�ReGroupPolPrintSave.jsp
//�����ܣ�
//�������ڣ�2002-11-26
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
	
	String handleFunction(HttpSession session, HttpServletRequest request) {
		int nIndex = 0;
		//String tLCGrpContNos[] = request.getParameterValues("GrpPolGridNo");
		String tLCGrpContNos[] = request.getParameterValues("GrpPolGrid1");
		String tChecks[] = request.getParameterValues("InpGrpPolGridChk");
		String strOperation = request.getParameter("fmtransact");

		GlobalInput globalInput = new GlobalInput();
		
		if( (GlobalInput)session.getValue("GI") == null ) {
			return "��ҳ��ʱ������û�в���Ա��Ϣ�������µ�¼";
		} else {
			globalInput.setSchema((GlobalInput)session.getValue("GI"));
		}
		
		if( tLCGrpContNos == null ) {
			return "û��������Ҫ�Ĵ�ӡ����";
		}

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		//ReLCGrpContF1PUI tReLCGrpContF1PUI = new ReLCGrpContF1PUI();
		String busiName="f1printReLCGrpContF1PUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		for(nIndex = 0; nIndex < tChecks.length; nIndex++ ) {
			// If this line isn't selected, continue
			if( !tChecks[nIndex].equals("1") ) {
			  continue;
			}
			
			if( tLCGrpContNos[nIndex] == null || tLCGrpContNos[nIndex].equals("") ) {
			  return "�����뱣���ŵ���Ϣ";
			}

			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
			
			tLCGrpContSchema.setGrpContNo( tLCGrpContNos[nIndex] );
			
			tLCGrpContSet.add(tLCGrpContSchema);
		}
		
		// Prepare data for submiting
		VData vData = new VData();
		
		vData.addElement(tLCGrpContSet);
		vData.add(globalInput);
	
		try {
			if( !tBusinessDelegate.submitData(vData, strOperation,busiName) ) {
		   	if ( tBusinessDelegate.getCErrors().needDealError() ) {
		   		return tBusinessDelegate.getCErrors().getFirstError();
			  } else {
			  	return "����ʧ�ܣ�����û����ϸ��ԭ��";
				}
			} 			
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	  return "";
	}
%>

<%
	String FlagStr = "";
	String Content = "";
	
	try {

		Content = handleFunction(session, request);		
		if( Content.equals("") ) {		
			FlagStr = "Succ";
			Content = "�ύ����ɹ�";

		} else {
			FlagStr = "Fail";
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

