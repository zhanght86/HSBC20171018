<%
//�������ƣ�PEdorTypeFMSubmit.jsp
//�����ܣ�
//�������ڣ�2005-05-16 09:49:22
//������:  zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����     

		
  /** ������� */
	CErrors tError = new CErrors();   
	String tRela  = "";                
	String FlagStr = "";
	String Content = "";
	String transact = "";
    String Result = "";
    

	//��ȫ������Ŀ��Ϣ
	String sEdorAcceptNo= request.getParameter("EdorAcceptNo");
	String sEdorType 	= request.getParameter("EdorType");	
	String sEdorNo 		= request.getParameter("EdorNo");
	String sContNo 		= request.getParameter("ContNo");
	String sPolNo 		= request.getParameter("PolNo");
	String sInsuredNo 	= request.getParameter("InsuredNo");
	
	LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
	tLPEdorItemSchema.setEdorAcceptNo(sEdorAcceptNo);
	tLPEdorItemSchema.setEdorType(sEdorType);
	tLPEdorItemSchema.setEdorNo(sEdorNo);
	tLPEdorItemSchema.setContNo(sContNo);
	tLPEdorItemSchema.setPolNo(sPolNo);
	tLPEdorItemSchema.setInsuredNo(sInsuredNo);
	
	//��ȫ�����Ϣ
	String sPayIntv 	= request.getParameter("PayIntv_new");	
	
	LPPolSchema tLPPolSchema = new LPPolSchema();
	tLPPolSchema.setPayIntv(sPayIntv);

	VData tVData = new VData();       

//	EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	try
	{		   
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		tVData.add(tLPPolSchema);
//		tEdorDetailUI.submitData(tVData, "EDORITEM|INPUT");
		tBusinessDelegate.submitData(tVData, "EDORITEM|INPUT" ,busiName);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
//		    tError = tEdorDetailUI.mErrors;
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
				Content ="����ɹ���";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

