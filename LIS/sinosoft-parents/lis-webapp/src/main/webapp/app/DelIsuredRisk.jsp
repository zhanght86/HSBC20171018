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
  <%@page import="com.sinosoft.service.*"%>
<%

  TransferData tTransferData = new TransferData(); 
//  ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  String busiName="tContInsuredUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
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
  String loadflag=request.getParameter("LoadFlag");      
         loggerDebug("DelIsuredRisk","line 43 loadflag="+loadflag);                    
        tTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
        tTransferData.setNameAndValue("PolNo",request.getParameter("polno"));             
        tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));          
        try
         {
            // ׼���������� VData
             VData tVData = new VData();
             tVData.add(tTransferData);
             tVData.add(tGI);
                 loggerDebug("DelIsuredRisk","asdasdasdasd"+fmAction);  
                 loggerDebug("DelIsuredRisk","InsuredNo=="+request.getParameter("InsuredNo"));
                 loggerDebug("DelIsuredRisk","polno=="+request.getParameter("polno")); 
                 loggerDebug("DelIsuredRisk","ContNo=="+request.getParameter("ContNo")); 
//            if ( tContInsuredUI.submitData(tVData,fmAction))
            if ( tBusinessDelegate.submitData(tVData, fmAction,busiName))
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
  if ("".equals(FlagStr))
  {
//    tError = tContInsuredUI.mErrors;
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
	<%
	   loggerDebug("DelIsuredRisk",request.getParameter("LoadFlag"));
	   
	  if ("2".equals(request.getParameter("LoadFlag")))
	   {
	%>                                       
<script language="javascript">
	 parent.fraInterface.fm.action="./GrpContInsuredSave.jsp";	
    
</script>

	<%
	}
	  else
	   {
	%>
	
  <script language="javascript">
  parent.fraInterface.fm.action="./ContInsuredSave.jsp";
  </script>
  <%
	}
	%>
<html>
  	<script language="javascript">
	parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
  </script>
 </html>	
	

