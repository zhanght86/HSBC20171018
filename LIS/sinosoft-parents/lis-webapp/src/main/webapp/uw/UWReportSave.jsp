<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//WorkFlowNotePadSave.jsp
//�����ܣ�
//�������ڣ�2005-04-22 14:49:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strReportCont = request.getParameter("Content");
    String strContNo = request.getParameter("ContNo");
    String strNoType = request.getParameter("NoType");
    String strOperatePos = request.getParameter("OperatePos");
    // �޸����ֵط���ֵΪ"null"�ַ�����������
    if("null".equals(strOperatePos)){
    	strOperatePos = "";
    }
    String strBussFlagSave = request.getParameter("BussFlagSave");
  	String strClmNo = request.getParameter("ClmNo");
    
    
    LCUWReportSchema tLCUWReportSchema = new LCUWReportSchema();
    if(strClmNo==null||"".equals(strClmNo)||"null".equals(strClmNo)){
    	tLCUWReportSchema.setOtherNo(strContNo);
    }else{
    	tLCUWReportSchema.setOtherNo(strClmNo);
    }
    tLCUWReportSchema.setOtherNoType(strNoType); 
    tLCUWReportSchema.setOperatePos(strOperatePos);
    tLCUWReportSchema.setReportCont(strReportCont);
    
    LCContSchema tLCContSchema = new LCContSchema();
    tLCContSchema.setContNo(strContNo);
    tLCContSchema.setBussFlag(strBussFlagSave); //2009-1-7 ln add --��ҵ���ر��
     
    //UWReportInputUI tUWReportInputUI = new UWReportInputUI();
    
	try
	{	
		tVData.add(tG);		
		tVData.add(tLCUWReportSchema);   
		tVData.add(tLCContSchema); 
		//tUWReportInputUI.submitData(tVData, "REPORT|INSERT");
		String busiName="tbUWReportInputUI";
	  	String mDescType="����";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(tVData,"REPORT|INSERT",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
					Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
			   }
			   else
			   {
					Content = mDescType+"ʧ��";
					FlagStr = "Fail";				
			   }
		  }
		  else
		  {
		     	Content = mDescType+"�ɹ�! ";
		      	FlagStr = "Succ";  
		  }
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      loggerDebug("UWReportSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	/*if ("".equals(FlagStr))
	{
		    tError = tUWReportInputUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		      Content ="����ɹ���";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}  */ 
  %>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
