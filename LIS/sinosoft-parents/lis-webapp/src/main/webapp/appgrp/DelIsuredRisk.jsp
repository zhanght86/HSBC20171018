<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�DelIsuredRisk.jsp
//�����ܣ�
//�������ڣ�2002-02-05 08:49:52
//������  ��yuanaq
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
   <%@page import="com.sinosoft.service.*" %>

<%

  TransferData tTransferData = new TransferData(); 
String busiName="tbContInsuredUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 // ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  //�������
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  loggerDebug("DelIsuredRisk","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("DelIsuredRisk","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
  CErrors tError = null;
  String tBmCert = "";
  String fmAction=request.getParameter("fmAction");

        tTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
        tTransferData.setNameAndValue("PolNo",request.getParameter("polno"));             
        try
         {
            // ׼���������� VData
             VData tVData = new VData();
             tVData.add(tTransferData);
             tVData.add(tGI);
                 loggerDebug("DelIsuredRisk","asdasdasdasd"+fmAction);  
                 loggerDebug("DelIsuredRisk","InsuredNo=="+request.getParameter("InsuredNo"));
                 loggerDebug("DelIsuredRisk","polno=="+request.getParameter("polno")); 
            if ( tBusinessDelegate.submitData(tVData,fmAction,busiName))
            {

                            %>
		            <%		      
		        if (fmAction.equals("DELETE||INSUREDRISK"))
		        {
		    	    
		            %>
		            <SCRIPT language="javascript">
                            parent.fraInterface.PolGrid.delRadioTrueLine("PolGrid");                    
		            </SCRIPT>
		            <%
		        }
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
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content ="����ɾ���ɹ���";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "ɾ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  loggerDebug("DelIsuredRisk","FlagStr:"+FlagStr+"Content:"+Content);
  
} //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.fm.action="./ContInsuredSave.jsp";
	parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>

