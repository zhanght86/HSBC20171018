<%
//�������ƣ�PEdorTypeRCSubmit.jsp
//�����ܣ����˱�ȫ-�����ղ�����ȡ
//�������ڣ�2007-05��24
//������  ��ZengYG
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Date"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %> 

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ

	LPPersonSchema tLPPersonSchema   = new LPPersonSchema();
	LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
 	PEdorOPDetailBL tPEdorOPDetailBL = new PEdorOPDetailBL();
//	 String busiNameOP="tPEdorOPDetailBL";
//	 BusinessDelegate tBusinessDelegateOP=BusinessDelegate.getBusinessDelegate();
//	EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	CErrors tError = null;
	//����Ҫִ�еĶ�������ӣ��޸�

  String InsuAccBala = "";
  String CanGetMoney = "";
  String WorkNoteFee = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";
	String Result="";
  try
	{
	   transact = request.getParameter("fmtransact");
	   GlobalInput tG = new GlobalInput();
	   tG = (GlobalInput)session.getValue("GI");

	   //���˱���������Ϣ
	   tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	   tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	   tLPEdorItemSchema.setStandbyFlag3(request.getParameter("Remark"));


	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("GetMoney",request.getParameter("GetMoney"));
	   tTransferData.setNameAndValue("InsuAccBala",request.getParameter("InsuAccBala"));

	   // ׼���������� VData
     VData tVData = new VData();
     tVData.addElement(tG);
	   if("OP||QUERY".equals(transact))
	   {
	       tVData.addElement(tLPEdorItemSchema);
	       tPEdorOPDetailBL.submitData(tVData,transact);
//	       tBusinessDelegateOP.submitData(tVData,transact,busiNameOP);
	       tError = tPEdorOPDetailBL.getErrors();
//	       tError = tBusinessDelegateOP.getCErrors();
	   }
	   else if("OP||MAIN".equals(transact))
     {
         tVData.addElement(tLPEdorItemSchema);
         tVData.addElement(tTransferData);
//         tEdorDetailUI.submitData(tVData,transact);
         tBusinessDelegate.submitData(tVData, transact ,busiName);
//         tError = tEdorDetailUI.getErrors();
         tError = tBusinessDelegate.getCErrors(); 
     }
  }
  catch(Exception ex)
  {
	   Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
     FlagStr = "Fail";
  }
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
	    if (!tError.needDealError())
	    {
   			 Content = " ����ɹ�!<br>";
		     FlagStr = "Success";
		     if("OP||QUERY".equals(transact))
		     {

			        TransferData tTransferData = tPEdorOPDetailBL.getTransferResult();
				      if (tTransferData !=null)
				      {
				          InsuAccBala = (String)tTransferData.getValueByName("InsuAccBala");
				          CanGetMoney = (String)tTransferData.getValueByName("CanGetMoney");
				          WorkNoteFee = (String)tTransferData.getValueByName("WorkNoteFee");
				      }
		     }else
		     	{
		     	    TransferData tTransferData = tPEdorOPDetailBL.getTransferResult();
				      if (tTransferData !=null)
				      {
				          WorkNoteFee = (String)tTransferData.getValueByName("WorkNoteFee");
				      }
		     	}
	    }
	    else
	    {
   			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    FlagStr = "Fail";
	    }
    }
    //��Ӹ���Ԥ����

%>
<!-- XInYQ modified on 2005-12-22 : �ύ֮���ٴβ�ѯ : BGN -->
<html>
    <script language="JavaScript">
        try
        {


            if('<%=transact%>' =="OP||QUERY")
            {   
            	  if('<%=FlagStr%>' =="Fail")
            	  {
            	  	  parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>', '<%=Result%>');
            	  }
            	  else
            	  {
            	  	  parent.fraInterface.afterCalData('<%=InsuAccBala%>', '<%=CanGetMoney%>', '<%=WorkNoteFee%>');
            	  }
            }
            else
        	  {
        	  	  parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>', '<%=Result%>');
                parent.fraInterface.queryBackFee();
                
            }
        }
        catch (ex)
        {
        }
    </script>
</html>
<!-- XInYQ modified on 2005-12-22 : �ύ֮���ٴβ�ѯ : END -->
