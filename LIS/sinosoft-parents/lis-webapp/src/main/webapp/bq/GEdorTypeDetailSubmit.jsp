<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�PGrpEdorTypeDetailSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	
	String busiName="GEdorDetailUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//������Ϣ������У�鴦��
	String FlagStr = "";
	String Content = "";
	String transact = "";
	String Result="";
  
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
	LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
	LPGrpEdorItemSchema tLPGrpEdorItemSchema   = new LPGrpEdorItemSchema();
	//������Ŀ������Ϣ
	tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	//��������������Ϣ
	LPEdorItemSet tLPEdorItemSet   = new LPEdorItemSet();
	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
	tLPEdorItemSet.add(tLPEdorItemSchema);
   
	transact=request.getParameter("Transact");      // Ӧ�ü��ϰɣ���
	
	try
	{
		// ׼���������� VData
		VData tVData = new VData();  
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemSet);
		tVData.addElement(tLPGrpEdorItemSchema);

		//������˱�����Ϣ(��ȫ)	
		tBusinessDelegate.submitData(tVData, transact,busiName);
	}
	catch(Exception ex)
	{
		Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}			
	
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr=="")
	{
		CErrors tError = new CErrors(); 
		tError =tBusinessDelegate.getCErrors();

		if (!tError.needDealError())
		{
		  Content = " ����ɹ�";
			FlagStr = "Success";
			
			if (transact.equals("QUERY||MAIN")||transact.equals("QUERY||DETAIL"))
			{
				if (tBusinessDelegate.getResult()!=null&&tBusinessDelegate.getResult().size()>0)
				{
					Result = (String)tBusinessDelegate.getResult().get(0);
					
					if (Result==null||Result.trim().equals(""))	
					{
						FlagStr = "Fail";
						Content = "�ύʧ��!!";
					}
				}
			}
		}
		else
		{
			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}

%>                      
<html>
<script language="javascript">
	
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>");
</script>
</html>

