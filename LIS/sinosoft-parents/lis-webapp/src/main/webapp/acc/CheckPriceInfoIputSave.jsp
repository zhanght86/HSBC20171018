<%
//�������ƣ�LOAccUnitPriceSave.jsp
//�����ܣ�
//�������ڣ�2002-08-19
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
   <%@page import="com.sinosoft.lis.acc.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%

  //������Ϣ������У�鴦��
  //�������
  String DoBatch="";

  String tBmCert = "";
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String transact = "";

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  LOAccUnitPriceSchema tLOAccUnitPriceSchema   = new LOAccUnitPriceSchema();
  //LOAccUnitPriceUI tLOAccUnitPriceUI   = new LOAccUnitPriceUI();
  //  LOAccUnitPriceSet tLOAccUnitPriceSet=new LOAccUnitPriceSet();
     GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getAttribute("GI");
  //�������
  CErrors tError =new CErrors();

  DoBatch= request.getParameter("DoBatch");
  if( DoBatch.equals("OK"))
  {
     //DealInsuAcc tDealInsuAcc=new DealInsuAcc(tGI);
     String busiName="DealInsuAcc";
	 BusinessDelegate iBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
	 VData tVData=new VData();
	 TransferData tTransferData = new TransferData();
	 tTransferData.setNameAndValue("DealDate",request.getParameter("DealDate"));
	 tTransferData.setNameAndValue("NextPriceDate",request.getParameter("NextPriceDate"));
     tVData.add(tTransferData);
     tVData.add(tGI);
     try
     {
    	 if(!iBusinessDelegate.submitData(tVData,"DoBatch",busiName))
	   //if (!tDealInsuAcc.dealInsuAcc(request.getParameter("DealDate"),request.getParameter("NextPriceDate")))
	   {
       	 FlagStr="fail";
	     Content ="������ʧ��";
	     //tError = tDealInsuAcc.mErrors;
	     tError = iBusinessDelegate.getCErrors();
    	 if (tError.needDealError())
    	 {
    		 Content = Content+"��ԭ���ǣ�"+ tError.getFirstError();
    	 }
	   }else{
	     FlagStr="Succ";
	     Content ="���������";
	   }
     }
     catch(Exception ex)
     {
       Content = Content+"ԭ����:" + ex.toString();
       FlagStr = "Fail";
     }
  }else{

	if(FlagStr=="")
	{
	    //tLOAccUnitPriceSchema.setRiskCode(request.getParameter("RiskCode"));
	
	    tLOAccUnitPriceSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
	
	    tLOAccUnitPriceSchema.setStartDate(request.getParameter("StartDate"));
	
	    tLOAccUnitPriceSchema.setEndDate(request.getParameter("EndDate"));
	
	    tLOAccUnitPriceSchema.setInvestFlag(request.getParameter("InvestFlag"));
	
	    tLOAccUnitPriceSchema.setSRateDate(request.getParameter("SRateDate"));
	
	    tLOAccUnitPriceSchema.setARateDate(request.getParameter("ARateDate"));
	
	    tLOAccUnitPriceSchema.setUnitPriceBuy(request.getParameter("UnitPriceBuy"));
	
	    tLOAccUnitPriceSchema.setUnitPriceSell(request.getParameter("UnitPriceSell"));
	
	    tLOAccUnitPriceSchema.setRedeemRate(request.getParameter("RedeemRate"));
	
	    tLOAccUnitPriceSchema.setState(request.getParameter("State"));
	
	    tLOAccUnitPriceSchema.setRedeemMoney(request.getParameter("RedeemMoney"));
	
	    tLOAccUnitPriceSchema.setOperator(tGI.Operator);
	
	
	    transact=request.getParameter("Transact");
	
	    String busiName="LOAccUnitPriceUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  try
		  {
		    // ׼���������� VData
		
		    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		    VData tVData=new VData();
		    tVData.add(tLOAccUnitPriceSchema);
		    tVData.add(tGI);
		    loggerDebug("CheckPriceInfoIputSave","+_+......"+transact);
		    //tLOAccUnitPriceUI.submitData(tVData,transact);
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
		    //tError = tLOAccUnitPriceUI.mErrors;
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {
		      if(transact.equals("INSERT"))
		      {
		        transact="¼��";
		      }
		      if(transact.equals("CONFIRM"))
		      {
		        transact="��Ч";
		      }
		      if(transact.equals("DELETE"))
		      {
		        transact="ɾ��";
		      }
		      Content = transact+"�ɹ�";
		      FlagStr = "Succ";
		    }
		    else
		    {
		      Content = transact+" ʧ�ܣ�ԭ����:" + tError.getFirstError();
		      FlagStr = "Fail";
		    }
		  }
	}else{
	}

}
  //��Ӹ���Ԥ����
%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<script language="javascript">
	
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//alert("Flag-01");
	//
	//alert("Flag-02");
	
</script>
</html>

