<%
//**************************************************************************************************
//Name��LLCaseBackInputSave.jsp
//Function��������������Ϣ�����ύ
//Author��wanzh
//Date: 2005-10-21 14:33
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    if(tG == null) 
    {
        loggerDebug("LLCaseBackInputSave","��¼��Ϣû�л�ȡ!!!");
       return;
     } 
     loggerDebug("LLCaseBackInputSave","��ͬ�Զ�����");
    //׼������������Ϣ
    
    String tBackType=request.getParameter("BackTypeQ");
    String busiName="LLCaseBackUI";
    
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("BackNo", request.getParameter("BackNo")); //���˺�
      
    LLCaseBackSchema tLLCaseBackSchema = new LLCaseBackSchema();
    tLLCaseBackSchema.setClmNo(request.getParameter("ClmNo"));
    tLLCaseBackSchema.setBackDesc(request.getParameter("BackDesc"));
    tLLCaseBackSchema.setRemark(request.getParameter("Remark"));
    tLLCaseBackSchema.setBackReason(request.getParameter("BackReason"));
    tLLCaseBackSchema.setBackType(tBackType); //�������� 0-�Ѳ����������,1-��ǩ��δ�����������,2-Ԥ������
    tLLCaseBackSchema.setNewGiveType(request.getParameter("NewGiveType")); //����������
    tLLCaseBackSchema.setApplyUser(tG.Operator); //������
    tLLCaseBackSchema.setCheckBackPreFlag(request.getParameter("whetherBackPre")); //�Ƿ�ͬʱ����Ԥ����Ϣ��־
    
    int mFlag = Integer.parseInt(request.getParameter("Flag"));
    
   
    try
    {    
        if( mFlag == 1)
        {
           // ׼���������� VData
           VData tVData = new VData();
           tVData.add( tG );        
           tVData.add( tLLCaseBackSchema );    
           tVData.add( mTransferData );  
//           LLCaseBackUI tLLCaseBackUI = new LLCaseBackUI();
//        
//           if (!tLLCaseBackUI.submitData1(tVData,""))
//           {
//                Content = " �����ύʧ�ܣ�ԭ����: " + tLLCaseBackUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//           } else {
//                Content = " ����ɹ�! ";
//                FlagStr = "Succ";
//           }
//           int n = tLLCaseBackUI.mErrors.getErrorCount();
//           for (int i = 1; i < n; i++)
//           {
//                Content = Content + "ԭ����: " + tLLCaseBackUI.mErrors.getError(i).errorMessage;
//                FlagStr = "Fail";
//           } 

		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,"1",busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLCaseBackInputSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content ="�����ύʧ��,ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "�����ύʧ��";
				   FlagStr = "Fail";				
				} 
		}
		else {
                Content = " ����ɹ�! ";
                FlagStr = "Succ";
        }

        }
        if( mFlag == 2 )
        {
           tLLCaseBackSchema.setBackNo(request.getParameter("BackNo"));
           // ׼���������� VData
           VData tVData = new VData();
           tVData.add( tG ); 
                  
           tVData.add( tLLCaseBackSchema );    
           tVData.add( mTransferData );  
//           LLCaseBackUI tLLCaseBackUI = new LLCaseBackUI();
//        
//           if (!tLLCaseBackUI.submitData(tVData,""))
//           {
//                Content = " �����ύʧ�ܣ�ԭ����: " + tLLCaseBackUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//           } else {
//                Content = " ����ɹ�! ";
//                FlagStr = "Succ";
//           }
//           int n = tLLCaseBackUI.mErrors.getErrorCount();
//           for (int i = 1; i < n; i++)
//           {
//                Content = Content + "ԭ����: " + tLLCaseBackUI.mErrors.getError(i).errorMessage;
//                FlagStr = "Fail";
//           } 
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
			
			if(!tBusinessDelegate1.submitData(tVData,"2",busiName))
			    {    
			        if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			        { 
			        	int n = tBusinessDelegate1.getCErrors().getErrorCount();
				        for (int i = 0; i < n; i++)
				        {
				            //loggerDebug("LLCaseBackInputSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
				            Content ="�����ύʧ��,ԭ����: " + tBusinessDelegate1.getCErrors().getError(i).errorMessage;
				        }
			       		FlagStr = "Fail";				   
					}
					else
					{
					   Content = "�����ύʧ��";
					   FlagStr = "Fail";				
					} 
			}
			else {
	                Content = " ����ɹ�! ";
	                FlagStr = "Succ";
	        }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }
   
%>

<html>
<script language="javascript">
    
var mFlag = <%=mFlag%>;
var mBackType = <%=tBackType%>;

if(mBackType == 1 && mFlag == 1)
{
	 parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>"); //��ǩ��û�в������������Լ����ڸý������,�����������Ҫ�������շ�ȥ����,���Է������������н��Ŵ���ǰ����������
}
else
{
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
}
</script>
</html>
