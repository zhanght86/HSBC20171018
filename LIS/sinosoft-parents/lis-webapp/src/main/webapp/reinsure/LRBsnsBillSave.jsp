<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRBsnsBillSave.jsp
//�����ܣ�
//�������ڣ�2007-02-28
//������  ��zhangbin
//���¼�¼��  ������: zhangbin ��������  2008-4-14   ����ԭ��/����
%>
<!--�û�У����-->
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="com.sinosoft.utility.*"%>
	<%@page import="com.sinosoft.lis.schema.*"%>
	<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
	<%@page import="com.sinosoft.lis.reinsure.*"%>
	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%	
	GlobalInput globalInput = new GlobalInput();
	CErrors tError = null;	
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
	
	System.out.println("========Operator===="+globalInput.Operator);
	System.out.println("========ComCode===="+globalInput.ComCode);
	
	String FlagStr = "";
	String Content = "";
	TransferData tTransferData = new TransferData();
	VData tVData = new VData();
	
	RIBsnsBillBatchSchema tRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
	//LRBsnsBillUI mLRBsnsBillUI = new LRBsnsBillUI();
	BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	String mStartDate = request.getParameter("StartDate");
	String mEndDate = request.getParameter("EndDate");
	String mContNo = request.getParameter("ContNo");	
	String mRIcomCode = request.getParameter("RIcomCode");

	//��װǰ̨���ݣ�������̨
	
	//=======================
	tRIBsnsBillBatchSchema.setStartDate(mStartDate);
	tRIBsnsBillBatchSchema.setEndDate(mEndDate);
	tRIBsnsBillBatchSchema.setRIContNo(mContNo) ;
	tRIBsnsBillBatchSchema.setIncomeCompanyNo(mRIcomCode) ;
	//=====================	
	
	//try
	//{
	  	tTransferData.setNameAndValue("","");
	  	
	  	tVData.addElement(tTransferData);
	  	tVData.addElement(globalInput);
	    tVData.addElement(tRIBsnsBillBatchSchema);
	    
	    //mLRBsnsBillUI.submitData(tVData," ");
	//}
	//catch(Exception ex)
	//{
	//	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	//	FlagStr = "Fail";
	//}
	if(!uiBusinessDelegate.submitData(tVData," ","LRBsnsBillUI"))
   {    
        if(uiBusinessDelegate.getCErrors()!=null&&uiBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = ""+"����ʧ�ܣ�ԭ����:"+"" + uiBusinessDelegate.getCErrors().getFirstError();
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
	    tError = uiBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {
			System.out.println("����ɹ�!");
			Content = ""+"�����ɹ�!"+"";
	    	FlagStr = "Succ";
	    
	    }
	    else
	    {
	    	Content = ""+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}
	
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); 
</script>
</html>