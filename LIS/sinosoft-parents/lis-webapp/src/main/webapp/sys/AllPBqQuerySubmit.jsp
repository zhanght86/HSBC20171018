<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�AllPBqQuerySubmit.jsp
//�����ܣ�
//�������ڣ�2003-1-20
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
  LCPolSchema tLCPolSchema = new LCPolSchema();
  //AllPBqQueryUI tAllPBqQueryUI = new AllPBqQueryUI();
  String busiName="sysAllPBqQueryUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //�������
  String FlagStr = "";
  String Content = "";
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("AllPBqQuerySubmit","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
   String Operator  = tGI.Operator ;  //�����½����Ա�˺�
   String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
  
  CErrors tError = null;
  String tBmCert = "";
  
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String transact=request.getParameter("Transact");
loggerDebug("AllPBqQuerySubmit","transact:"+transact); 

    tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorMainSchema.setPolNo(request.getParameter("PolNo"));        
    tLPEdorMainSchema.setEdorType(request.getParameter("EdorType"));
    tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
    tLPEdorMainSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tLPEdorMainSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
    tLPEdorMainSchema.setManageCom(request.getParameter("ManageCom"));
        
 try
  {
  // ׼���������� VData
   VData tVData = new VData();
   tVData.addElement(tGI);
   tVData.addElement(tLPEdorMainSchema);
   tVData.addElement(tLCPolSchema);
    
   //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  //tAllPBqQueryUI.submitData(tVData,transact);
  if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
	   Content = "����ɹ�";
	   FlagStr = "Succ";				
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
    //tError = tAllPBqQueryUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
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
   }
} //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

