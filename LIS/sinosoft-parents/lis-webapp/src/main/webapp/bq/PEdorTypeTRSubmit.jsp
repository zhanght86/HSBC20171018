<%
//�������ƣ�PEdorTypeTRSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Minim
//�޸���  ��Nicholas

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
  LCPolSchema tLCPolSchema = new LCPolSchema();
  LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
  LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
  //����Ҫִ�еĶ�������ӣ��޸�
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result = "";
  String loanResult = "";
  
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
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	tLPEdorItemSchema.setStandbyFlag1(request.getParameter("SReLoan")); 
	tLPEdorItemSchema.setStandbyFlag2(request.getParameter("AReLoan")); 
	
  
  //����û�ѡ�����ȡ��Ϣ�������ڱ�LJSGetDrawɾ����Щ��Ϣ��Ӧ�ļ�¼
  String tLoanDate[] =request.getParameterValues("LoanGrid1"); //�潻����
  String tPreSumMoney[] =request.getParameterValues("LoanGrid2"); //�潻����
  String tPreInterest[] =request.getParameterValues("LoanGrid3"); //�潻��Ϣ
  String tSumMoney[] =request.getParameterValues("LoanGrid4"); //�潻����
  String tInterest[] =request.getParameterValues("LoanGrid5"); //�潻��Ϣ
  String tLoanNo[] =request.getParameterValues("LoanGrid6"); //ԭ������
  String tPolNo[] =request.getParameterValues("LoanGrid7"); //���ֺ�
  String tCurrency[] =request.getParameterValues("LoanGrid8"); //���ֺ�
  String tChk[] = request.getParameterValues("InpLoanGridChk");

	try{
	  for (int i=0;i<tChk.length;i++)
	  { 
	     if (tChk[i].equals("1"))
        {	
    	tLPReturnLoanSchema = new LPReturnLoanSchema();
    	tLPReturnLoanSchema.setContNo(request.getParameter("ContNo"));
    	tLPReturnLoanSchema.setPolNo(tPolNo[i]);
    	tLPReturnLoanSchema.setLoanDate(tLoanDate[i]);

    	//���λ���ı�Ϣ��
    	tLPReturnLoanSchema.setLeaveMoney(Double.parseDouble(tInterest[i])+Double.parseDouble(tSumMoney[i]));
    	//�����
    	tLPReturnLoanSchema.setReturnMoney(Double.parseDouble(tSumMoney[i]));
    	//������Ϣ
    	tLPReturnLoanSchema.setReturnInterest(tInterest[i]);
    	tLPReturnLoanSchema.setLoanNo(tLoanNo[i]);
    	tLPReturnLoanSchema.setCurrency(tCurrency[i]);
    	
        tLPReturnLoanSet.add(tLPReturnLoanSchema);
        }
	  }
  }
  catch (Exception e)
	{
		System.out.println(e.toString());
	}

  //�ܻ�����
  //Double tempDouble = new Double(request.getParameter("AllShouldPay"));

  try 
  {
    // ׼���������� VData
    VData tVData = new VData();  
  	
    //������˱�����Ϣ(��ȫ)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
	   tVData.add(tLPReturnLoanSet);    
//     tEdorDetailUI.submitData(tVData,transact);
     tBusinessDelegate.submitData(tVData, transact ,busiName);

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
      Content = "����ɹ���";
  		FlagStr = "Success";


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
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>", "<%=loanResult%>");
</script>
</html>

