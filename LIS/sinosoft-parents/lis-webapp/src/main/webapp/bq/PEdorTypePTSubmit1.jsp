<%
//�������ƣ�PEdorTypePTSubmit.jsp
//�����ܣ�
//�������ڣ�2005-07-3 16:49:22
//������  ��lizhuo
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.schema.*"%>
 <%@page import="com.sinosoft.lis.vschema.*"%>
 <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %> 

<%
    //������Ϣ������У�鴦��
    //�������
    //����������Ϣ
    System.out.println("-----PT submit---");
    GlobalInput tG = new GlobalInput();
    LPPolSchema tLPPolSchema   = new LPPolSchema();
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
//    EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
//    GEdorDetailUI tGEdorDetailUI   = new GEdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 String GbusiName="GEdorDetailUI";
	 BusinessDelegate GBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
    CErrors tError = new CErrors();
    //����Ҫִ�еĶ�������ӣ��޸�
 
    String tRela  = "";                
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String Result = "Check";
    String AppObj = "";
    
  
    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    transact = request.getParameter("fmtransact");	  
	tG = (GlobalInput)session.getValue("GI");
	AppObj = request.getParameter("AppObj");	
  
    if(!FlagStr.equals("Fail"))
    {
    	//����������Ϣ
	  	tLPEdorItemSchema.setPolNo("000000");
	  	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
    	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	  	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	  	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	  	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	  	tLPEdorItemSchema.setStandbyFlag2(request.getParameter("RelationToAppnt"));	
    	try
    	{
        	// ׼���������� VData  
  	    	VData tVData = new VData(); 	
	      	//������˱�����Ϣ(��ȫ)	
	 	    tVData.addElement(tG);
	 	    tVData.addElement(tLPEdorItemSchema);	 	    
	 	    System.out.println(" tLPEdorItemSchema ========================>"+tLPEdorItemSchema.encode()); 	    
	 	    if("G".equals(AppObj))
      		{
      		  	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
      		  	tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
      		  	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
      		  	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
      		  	tVData.addElement(tLPGrpEdorItemSchema);
      		  	System.out.println("Group submitData");
//      		tGEdorDetailUI.submitData(tVData, "OnlyCheck");
      		  	GBusinessDelegate.submitData(tVData, "OnlyCheck" ,GbusiName);   
      		}
      		else
      		{
    	  		System.out.println("Person submitData");
//    	  		tEdorDetailUI.submitData(tVData, "OnlyCheck");
    	  		tBusinessDelegate.submitData(tVData, "OnlyCheck" ,busiName);	
    	  	}
	   	}
	   	catch(Exception ex)
	   	{
		    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
        	FlagStr = "Fail";
	   	}
	}			
    
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr=="")
    {
        if("G".equals(AppObj))
//            tError = tGEdorDetailUI.mErrors;
            tError = GBusinessDelegate.getCErrors(); 
        else
//        	tError = tEdorDetailUI.mErrors;
        	tError = tBusinessDelegate.getCErrors(); 
        if (!tError.needDealError())
        {     
           	Content = " ����ɹ�";
    	    FlagStr = "Success";
        }
        else                                                                           
        {
    	    Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	    FlagStr = "Fail";
        }
    }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>

