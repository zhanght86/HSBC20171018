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
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
  String sOperate = "9999999992";  //������ʼ�ڵ��֧����
  String rEdorAcceptNo = "";
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  
		tVData.add(tG);		
		tVData.add(tTransferData);
    String busiName="EdorWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,sOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
				}
		}
		else
		{
				VData mResult;
				VData mINResult;
				TransferData rTransferData;	
						
					mResult = tBusinessDelegate.getResult();

          //mINResult = (VData)mResult.getObjectByObjectName("VData", 0);

         // rTransferData = (TransferData)mINResult.getObjectByObjectName("TransferData", 0);
          rTransferData = (TransferData)mResult.getObjectByObjectName("TransferData", 0);
          
          rEdorAcceptNo = (String)rTransferData.getValueByName("EdorAcceptNo");		
				  Content ="����ɹ�����ȫ����ţ�" + rEdorAcceptNo;
		    	FlagStr = "Succ";							
							
		}

%>
   
<html>
<script language="javascript">
	parent.fraInterface.fm.EdorAcceptNo.value= "<%=rEdorAcceptNo%>";
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 