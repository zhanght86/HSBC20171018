<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorOverDueSave.jsp
//�����ܣ���������ȫ��ȫ�ֶ���ֹ
//�������ڣ�2005-12-19 20:02:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
	String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);   			
	mTransferData.setNameAndValue("EdorState", "d");  
	VData tVData = new VData();       
	tVData.addElement(tG);
	tVData.add(mTransferData);
    String busiName="PEdorOverDueBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

    
	try
	{		   
		if(!tBusinessDelegate.submitData(tVData,"",busiName))
		{
			tError = tBusinessDelegate.getCErrors();
	    	Content = "��ȫ��ֹʧ�ܣ�ԭ����:" + tError.getFirstError();
	    	FlagStr = "Fail";
		}
	}
	catch(Exception ex)
	{
	      Content = "��ȫ��ֹʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    { 
		    	Content = "��ȫ��ֹ�ɹ�!";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "��ȫ��ֹʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	} 
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 