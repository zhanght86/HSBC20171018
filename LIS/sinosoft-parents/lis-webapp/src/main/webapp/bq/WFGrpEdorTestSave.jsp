<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorNoscanAppSave.jsp
//�����ܣ���ȫ��ɨ�����봴����ʼ�ڵ�
//�������ڣ�2005-04-27 15:59:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

   String busiName="EdorWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 
  //�������
  String FlagStr = "";
  String Content = ""; 
  		     
  String sOperate = "8888888882";  //������ʼ�ڵ��֧����
  String rEdorAcceptNo = "";
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
   
  //EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI(); 
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);
		tBusinessDelegate.submitData(tVData, sOperate,busiName);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          

				//��ȡ���ؽ��
				VData mResult;
				VData mINResult;
				TransferData rTransferData;	
							
				try
				{
					mResult = tBusinessDelegate.getResult();

                	

                	rTransferData = (TransferData) 
                		mResult.getObjectByObjectName("TransferData", 0);

                	rEdorAcceptNo = (String)
						rTransferData.getValueByName("EdorAcceptNo");	
				}
				catch(Exception ex)
				{
		    		Content = "����ɹ�,��ȡ���ؽ��ʧ�ܣ�ԭ����:" + ex.toString();
		    		FlagStr = "Fail";
				}
				
				Content ="����ɹ�����ȫ����ţ�" + rEdorAcceptNo;
		    	FlagStr = "Succ";						
 	
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
%>
   
<html>
<script language="javascript">
	parent.fraInterface.fm.EdorAcceptNo.value= "<%=rEdorAcceptNo%>";
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 