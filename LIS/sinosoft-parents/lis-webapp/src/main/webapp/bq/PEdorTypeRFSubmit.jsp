<%
//�������ƣ�PEdorTypeRFSubmit.jsp
//�����ܣ�
//�������ڣ�2005-07-09
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
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %>
<html>
<script language="javascript">
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
  int nIndex;
  transact = "INSERT||MAIN";
 
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
  //���˱���������Ϣ
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 

	tTransferData.setNameAndValue("PayOffFlag", request.getParameter("PayOffFlag"));
		
	LOLoanSet tLOLoanSet = new LOLoanSet();
	String tChk[] = request.getParameterValues("InpLoanGridChk"); 
	String tEdorNo[] = request.getParameterValues("LoanGrid1"); //����������
    String tLoanDate[] = request.getParameterValues("LoanGrid2"); //��������
	String tPayOffDate[] = request.getParameterValues("LoanGrid3"); //��������
    String tCurSumLoan[] = request.getParameterValues("LoanGrid4"); //��ǰ����
    String tCurSumLoanIntrest[] = request.getParameterValues("LoanGrid5"); //��ǰ������Ϣ
    String tShoudLoanMoeny[] = request.getParameterValues("LoanGrid7"); //���λ���
    String tPolNo[] = request.getParameterValues("LoanGrid8"); //���ձ�����
    String tCurrency[] = request.getParameterValues("LoanGrid9"); //�������
    System.out.println(""+tChk.length);
    for(nIndex = 0; nIndex < tChk.length; nIndex++ ) 
    {
      if( !tChk[nIndex].equals("1") ) 
      {
        continue;
      }   
      LOLoanSchema tLOLoanSchema = new LOLoanSchema();
      
      tLOLoanSchema.setContNo(request.getParameter("ContNo"));
      tLOLoanSchema.setPolNo(tPolNo[nIndex]);
      tLOLoanSchema.setCurrency(tCurrency[nIndex]);
      tLOLoanSchema.setEdorNo(tEdorNo[nIndex]);
      if (tShoudLoanMoeny[nIndex] != null)
      {

    	tLOLoanSchema.setSumMoney(tShoudLoanMoeny[nIndex]);   	
    	//��Ž�ֹ���ν�����Ϣ
    	tLOLoanSchema.setPreInterest(tCurSumLoanIntrest[nIndex]);
    	//��Ž�����
    	tLOLoanSchema.setLeaveMoney(Double.parseDouble(tCurSumLoanIntrest[nIndex])+Double.parseDouble(tCurSumLoan[nIndex])-Double.parseDouble(tShoudLoanMoeny[nIndex]));
        tLOLoanSet.add(tLOLoanSchema);
      }  

    }
    try 
     {
    // ׼���������� VData
    VData tVData = new VData();  
  	
    //������˱�����Ϣ(��ȫ)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
		 tVData.add(tLOLoanSet);
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
