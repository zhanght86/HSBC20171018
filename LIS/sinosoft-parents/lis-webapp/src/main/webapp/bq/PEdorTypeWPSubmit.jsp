<%
//�������ƣ�PEdorTypeMRSubmit.jsp
//�����ܣ�
//�������ڣ�2005-04-13 16:49:22
//������  ��liuzhao
//���¼�¼��  ������    ��������     ����ԭ��/����
// modify by LH 2008-10-30
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    //������Ϣ������У�鴦��
    System.out.println("-----Detail submit---");                 
    String FlagStr = "";
    String Content = "";
    String transact = "";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    transact=(String)request.getParameter("fmtransact");
    CErrors tErrors = new CErrors();          
    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema(); 
    //������˱�����Ϣ(��ȫ)	
     tLPEdorItemSchema.setPolNo("000000");
	 tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	 tLPEdorItemSchema.setInsuredNo("000000");
	 tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
     tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
	 tLPEdorItemSchema.setEdorReason(request.getParameter("AppReason"));
	 tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
     tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	 tLPEdorItemSchema.setEdorType(request.getParameter("EdorType")); 
	 tLPEdorItemSchema.setGetMoney(request.getParameter("AddPrem")); //׷�ӱ��ѽ��
    // ׼���������� VData
    VData tVData = new VData();  
    tVData.addElement(tG);	 	
    tVData.addElement(tLPEdorItemSchema);	
    //ɾ����¼����Ҫ����У�����
    if(transact.equals("EDORITEM|DELETE"))
    {
        try
        {
            PEdorWPDetailBL tPEdorWPDetailBL = new PEdorWPDetailBL();
           if (!tPEdorWPDetailBL.submitData(tVData,transact))
           {
               tErrors.copyAllErrors(tPEdorWPDetailBL.mErrors);
           }
        }catch(Exception ex)
        {
            FlagStr = "Fail";
            Content = "����ʧ�ܣ�ԭ����:";
        }
    }
    else
    {
       
       try
        {
    	   PEdorWPDetailBL tPEdorWPDetailBL = new PEdorWPDetailBL();
           if (!tPEdorWPDetailBL.submitData(tVData,""))
           {
               tErrors.copyAllErrors(tPEdorWPDetailBL.mErrors);
           }
        }catch(Exception ex)
        {
            FlagStr = "Fail";
            Content = "����ʧ�ܣ�ԭ����:";
        }
    }
  //ҳ�淴����Ϣ
  if (!tErrors.needDealError())
  {
      FlagStr = "Success";
      Content = "�����ɹ���";
  }
  else
  {
      FlagStr = "Fail";
      Content = "����ʧ�ܣ�ԭ���ǣ�" + tErrors.getLastError();
  }
  tErrors = null;
  
%>        
              
<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
