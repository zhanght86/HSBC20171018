<%
//�������ƣ�PEdorTypePUSubmit.jsp
//�����ܣ� 
//�������ڣ�2005-07-07
//������  ��Nicholas
//�޸�ʱ�䣺2005-07
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %>

<%
  //����������Ϣ
  TransferData tTransferData = new TransferData();
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
  //����Ҫִ�еĶ�����
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  String transact = "";
  Double tInterest = new Double(0.0);
  
  //transact = request.getParameter("fmtransact");
  transact = "INSERT||MAIN";
  //System.out.println("---transact: " + transact);  
  
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
  //���˱���������Ϣ
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	
	//�������ִ���
	tTransferData.setNameAndValue("InsuYear",request.getParameter("InsuYear"));
	tTransferData.setNameAndValue("GetDutyKind",request.getParameter("GetDutyKind"));

  try 
  {
    // ׼���������� VData
    VData tVData = new VData();  
  	
    //������˱�����Ϣ(��ȫ)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
		 tVData.add(tTransferData);
    
//     boolean tag = tEdorDetailUI.submitData(tVData,transact);
     boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);
  } 
  catch(Exception ex) 
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "") 
  {
//  	tError = tEdorDetailUI.mErrors;
  	tError = tBusinessDelegate.getCErrors(); 
  	if (!tError.needDealError()) 
  	{        
  		FlagStr = "Success";                  
      Content = "����ɹ���";
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

