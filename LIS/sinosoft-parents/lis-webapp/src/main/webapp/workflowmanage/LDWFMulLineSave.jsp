<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
/**
 * Created by IntelliJ IDEA��
 * User: jiyt
 * Date: 2012-6-25
 * Time: 9:42:15
 */
%><!--�û�У����-->
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
    BusinessDelegate tBusinessDelegate; 
    TransferData tTransferData = new TransferData();
    /**
     * ��ǰ̨��ȡһЩ�ֶ�
     */
    String OperFlag = request.getParameter("OperFlag");
    if ("Insert".equalsIgnoreCase(OperFlag) || "Update".equalsIgnoreCase(OperFlag) || "Delete".equalsIgnoreCase(OperFlag))
    {
    	String tFunctionID=request.getParameter("FunctionID");
    	String tBusiNessTable=request.getParameter("BusiNessTable");
    	String tFieldCode=request.getParameter("FieldCode");
    	String tColWidth=request.getParameter("ColWidth");
    	String tColSerialNo = request.getParameter("ColSerialNo");
    	String tIsShow = request.getParameter("IsShow");
 //   	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@  tFunctionID "+tFunctionID+"tBusiNessTable"+tBusiNessTable+"tFieldCode"+tFieldCode+"tColWidth"+tColWidth+"tColSerialNo"+tColSerialNo+"tIsShow"+tIsShow);
    	tTransferData.setNameAndValue("FunctionID", tFunctionID);
    	tTransferData.setNameAndValue("BusiNessTable", tBusiNessTable);
    	tTransferData.setNameAndValue("FieldCode", tFieldCode);
    	tTransferData.setNameAndValue("ColWidth", tColWidth);
    	tTransferData.setNameAndValue("ColSerialNo", tColSerialNo);
    	tTransferData.setNameAndValue("IsShow", tIsShow);
    }
    /**
     * ��ȡҳ�� session
     * **/
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    /**
     * ��ʼ����ʼʵ��,���������ݵķ�װ,�����е���
     * */
 
    VData mVData = new VData();
    mVData.add(tG);
    mVData.add(tTransferData);
    /**
     * ������ɺ��ҳ����н���
     * */
    String FlagStr="";      //�������
    String Content = "";    //����̨��Ϣ
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(mVData, OperFlag, "LdwfMulLineUI"))
  	{
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getFirstError();
	   	FlagStr = "Fail";
	}
	else
	{
	  	Content = " ����ɹ�! ";
		FlagStr = "Succ";
    }

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
