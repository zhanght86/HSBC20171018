<%
//�������ƣ�PEdorTypeLNSubmit.jsp
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

  //�ܻ�����
  Double tempDouble = new Double(request.getParameter("MaxLoan"));
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("Currency",request.getParameter("Currency"));
  try 
  {
    // ׼���������� VData
    VData tVData = new VData();  
  	
    //������˱�����Ϣ(��ȫ)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
		 tVData.add(tempDouble);
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
//  		if (tEdorDetailUI.getResult()!=null && tEdorDetailUI.getResult().size()>0)
  		if (tBusinessDelegate.getResult()!=null && tBusinessDelegate.getResult().size()>0)
  	  {
  	  	VData tResult = new VData();
//  	  	tResult = tEdorDetailUI.getResult();
  	  	tResult = tBusinessDelegate.getResult();
  		  if (tResult==null || tResult.size()<=0) 
  		  {
  				FlagStr = "Fail"; 
  				Content = "��������ɣ���δ��÷�����Ϣ�������±��棡";
  		  }
  		  else
  		  {
  		  	tInterest = (Double)tResult.getObjectByObjectName("Double", 0);
  		  	//tInterest = (Double)tResult.get(0);
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=String.valueOf(tInterest.doubleValue())%>");
</script>
</html>

