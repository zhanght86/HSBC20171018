<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRProfitLossCalSave.jsp
//�����ܣ�
//�������ڣ�2007-03-14
//������  ���ű�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="com.sinosoft.utility.*"%>
	<%@page import="com.sinosoft.lis.schema.*"%>
	<%@page import="com.sinosoft.lis.vschema.*"%>
	<%@page import="com.sinosoft.lis.db.*"%>
	<%@page import="com.sinosoft.lis.vdb.*"%>
	<%@page import="com.sinosoft.lis.sys.*"%>
	<%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@page import="com.sinosoft.lis.reinsure.*"%>
 	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%
	System.out.println("��ʼִ��Saveҳ��");
  
	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
  
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	System.out.println("������������"+mOperateType);
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
	System.out.println("��ʼ���л�ȡ���ݵĲ���! ");
	RIProLossResultSchema mRIProLossResultSchema = new RIProLossResultSchema();
	RIProLossValueSet mRIProLossValueSet = new RIProLossValueSet();
	String[]incomNum = null;
	
	if(incomNum!=null)
	{
		for(int i=0;i<incomNum.length;i++)
		{
		}
	}
	if(mOperateType.equals("CALPARAM"))
	{
		mDescType = ""+"���������Ѳ�����ʼ��"+"";
	}
	
	VData tVData = new VData(); 
	//LRProfitLossCalBL mLRProfitLossCalBL = new LRProfitLossCalBL();
	BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	//try
	//{
		tVData.addElement(globalInput); 
		tVData.addElement(mRIProLossResultSchema); 
		tVData.addElement(mRIProLossValueSet); 
	//	mLRProfitLossCalBL.submitData(tVData,mOperateType); 
	//}
	//catch(Exception ex)
	//{
	//	Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
	//	FlagStr = "Fail";
	//}
  if(!blBusinessDelegate.submitData(tVData,mOperateType,"LRProfitLossCalBL"))
   {    
        if(blBusinessDelegate.getCErrors()!=null&&blBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"ʧ�ܣ�ԭ����:"+"" + blBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"����ʧ��"+"";
				   FlagStr = "Fail";				
				}
   }
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr=="")
	{
	    tError = blBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {
	    	System.out.println("����ɹ�!");
			Content = mDescType+""+"�ɹ���"+"" ;
	    	FlagStr = "Succ";
	    }
	    else
	    {
	    	Content = mDescType+" "+"ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>",""); 
</script>
</html>