<%
//�������ƣ�CountPrintSave.jsp
//�����ܣ�
//�������ڣ�2003-10-25
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
	InputStream ins = null;
	LCPolSet tLCPolSet = null;
	String handleFunction(HttpSession session, HttpServletRequest request) {
	int nIndex = 0;
	String tLCPolGrids[] = request.getParameterValues("PolGridNo");
	String tLCPolNos[] = request.getParameterValues("PolGrid1");	
	String tRadio[] = request.getParameterValues("InpPolGridSel");  

	GlobalInput globalInput = new GlobalInput();
	
	if( (GlobalInput)session.getValue("GI") == null ) {
		return "��ҳ��ʱ������û�в���Ա��Ϣ�������µ�¼";
	} else {
		globalInput.setSchema((GlobalInput)session.getValue("GI"));
	}
	
	
		
	if( tLCPolGrids == null ) {
		return "û��������Ҫ�Ĵ�ӡ����";
	}
	tLCPolSet = null;
	tLCPolSet = new LCPolSet();
//	tLCPolSet.clear();

	//LCPolF1PUI tLCPolF1PUI = new LCPolF1PUI();
	
	 String busiName="f1printLCPolF1PUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	
	for(nIndex = 0; nIndex < tRadio.length; nIndex++ )
        {
		// If this line isn't selected, continue
		loggerDebug("CountPrintSave",tRadio[nIndex]);
		if( tRadio[nIndex].equals("0") ) {
		  continue;
		}
		
		if( tLCPolNos[nIndex] == null || tLCPolNos[nIndex].equals("") ) {
		  return "�����뱣���ŵ���Ϣ";
		}

		LCPolSchema tLCPolSchema = new LCPolSchema();
		loggerDebug("CountPrintSave","�����ţ�"+tLCPolNos[nIndex]);
		tLCPolSchema.setPolNo( tLCPolNos[nIndex] );

		tLCPolSet.add(tLCPolSchema);
	}
	
	
	
	VData vData = new VData();
	vData.addElement(tLCPolSet);
	vData.add(globalInput);
	
	VData mResult=new VData();
	try
        {
		if( !tBusinessDelegate.submitData(vData, "PRINTEX",busiName) )
	        {
	   	      if ( tBusinessDelegate.getCErrors().needDealError() )
	   	      {
	   		  return tBusinessDelegate.getCErrors().getFirstError();
		      } 
		      else
		      {
		  	  return "����ʧ�ܣ�����û����ϸ��ԭ��";
		      }
		      
		      
		} else {
			// mResult = tLCPolF1PUI.getResult();
			ins = (InputStream)(tBusinessDelegate.getResult().get(0));
		}
	} 
	catch (Exception ex)
         {
		ex.printStackTrace();
		return ex.getMessage();
	}
	
      
       return ""; 	  
  }
%>
<%  
	String Content = "";
	String FlagStr = "";
	
	//String result = "";
	
	Content = handleFunction(session, request);
	
	loggerDebug("CountPrintSave","Err : ");
	loggerDebug("CountPrintSave",Content);
	
	// InputStream ins =(InputStream)mResult.get(0);
	if (ins==null)
        {
	  loggerDebug("CountPrintSave","null");
	  FlagStr = "Fail";
	  //result="û����صĴ�ӡ����";
	}else{
	session.setAttribute("PrintStream",ins);
	session.putValue("PolNo_PrintEx",tLCPolSet.get(1).getPolNo());
	
	//add by yt 20040426,���ּ���Ͷ�����ظ���ӡ������,�˴������PrintNo����Ϊ�գ�������ܳ���������ӡҳ���е�Sessionû����գ����º����operPrintTable.jsp���жϳ������⡣
	//session.putValue("PrintNo",null );
	//add by Minim at 2004-05-27, PrintNo���sessionֵ�����ҳ���ظ�ʹ�ã���������ɳ�ͻ
	session.putValue("PrintNoEx", "EX");
	//�����ӡ���ӻ����״�֤���ܣ�saveҳ�����ӳ����ʶEasyPrintFlag=1
	session.putValue("EasyPrintFlag","1");
	FlagStr = "Succ";
	//result = "�����ɹ����";
	response.sendRedirect("GetF1Print.jsp");
	}
%>

<html>
<script language="javascript">
//	if("<%=FlagStr%>"=="Fail"){
//	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
//}
alert('<%=Content%>');
window.close();
</script>
</html>
       
                     

