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
	<%@page import="com.sinosoft.lis.vschema.*"%>
	<%@page import="com.sinosoft.lis.db.*"%>
	<%@page import="com.sinosoft.lis.vdb.*"%>
	<%@page import="com.sinosoft.lis.sys.*"%>
	<%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.reinsure.*"%>
	<%@page contentType="text/html;charset=GBK" %>
	<%@page import="com.sinosoft.service.*" %>
<%	
	GlobalInput globalInput = new GlobalInput();
	CErrors tError = null;	
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
	
	String FlagStr = "";
	String Content = "";
	TransferData tTransferData = new TransferData();
	VData tVData = new VData();
	
	RIBsnsBillBatchSchema tRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
	RIBsnsBillDetailsSet tRIBsnsBillDetailsSet = new RIBsnsBillDetailsSet();
	//LRBsnsBillUWUI mLRBsnsBillUWUI = new LRBsnsBillUWUI();
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
	//��װǰ̨���ݣ�������̨	
	//=======================
	String mOperateType = request.getParameter("OperateType");


    
	//�ֱ���ϸ��������,���̨�� �˵�����
	if("download".equals(mOperateType))
	{
		String tRadio[] = request.getParameterValues("InpBatchListGridSel");  
	    String tBillNo [] = request.getParameterValues("BatchListGrid1"); //�˵����
	    String mBillNo = "";
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				mBillNo = tBillNo[index]; //�˵����
				System.out.println("�˵����::"+mBillNo);
				tRIBsnsBillBatchSchema.setBillNo(mBillNo);
			}
		}		
		
	}
	//�˵��޸�  ,�����˵���ϸ����
	if("billupdate".equals(mOperateType))
	{			
		String tRadio[] = request.getParameterValues("InpBatchListGridSel");  
	    String tBillNo [] = request.getParameterValues("BatchListGrid1"); //�˵����
	    String mBillNo = "";
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				mBillNo = tBillNo[index]; //�˵����
				System.out.println("�˵����::"+mBillNo);
				tRIBsnsBillBatchSchema.setBillNo(mBillNo);
			}
		}
	    
		String strNumber[] = request.getParameterValues("BillUpdateGridNo");
		String tDetails[]   = request.getParameterValues("BillUpdateGrid1");
		String tDetailsName[]   = request.getParameterValues("BillUpdateGrid2");
		String tDebSummoney[]   = request.getParameterValues("BillUpdateGrid3");
		String tCreSummoney[]   = request.getParameterValues("BillUpdateGrid4");
		
		RIBsnsBillDetailsSchema debRIBsnsBillDetailsSchema = null;
		RIBsnsBillDetailsSchema creRIBsnsBillDetailsSchema = null;
		for(int i = 0 ;i < strNumber.length ;i++)
		{
			debRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
			debRIBsnsBillDetailsSchema.setBillNo(mBillNo);
			debRIBsnsBillDetailsSchema.setDebCre("D");
			debRIBsnsBillDetailsSchema.setDetails(tDetails[i]);
			debRIBsnsBillDetailsSchema.setDetailsName(tDetailsName[i]);
			debRIBsnsBillDetailsSchema.setSummoney(tDebSummoney[i]);
			debRIBsnsBillDetailsSchema.setMakeDate(PubFun.getCurrentDate());
			debRIBsnsBillDetailsSchema.setMakeTime(PubFun.getCurrentTime());
			debRIBsnsBillDetailsSchema.setModifyDate(PubFun.getCurrentDate());
			debRIBsnsBillDetailsSchema.setModifyTime(PubFun.getCurrentTime());
			debRIBsnsBillDetailsSchema.setOperator(globalInput.Operator);
			debRIBsnsBillDetailsSchema.setManageCom(globalInput.ComCode);
			
			creRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
			creRIBsnsBillDetailsSchema.setBillNo(mBillNo);
			creRIBsnsBillDetailsSchema.setDebCre("C");
			creRIBsnsBillDetailsSchema.setDetails(tDetails[i]);
			creRIBsnsBillDetailsSchema.setDetailsName(tDetailsName[i]);
			creRIBsnsBillDetailsSchema.setSummoney(tCreSummoney[i]);
			creRIBsnsBillDetailsSchema.setMakeDate(PubFun.getCurrentDate());
			creRIBsnsBillDetailsSchema.setMakeTime(PubFun.getCurrentTime());
			creRIBsnsBillDetailsSchema.setModifyDate(PubFun.getCurrentDate());
			creRIBsnsBillDetailsSchema.setModifyTime(PubFun.getCurrentTime());
			creRIBsnsBillDetailsSchema.setOperator(globalInput.Operator);
			creRIBsnsBillDetailsSchema.setManageCom(globalInput.ComCode);
			
			if(debRIBsnsBillDetailsSchema.getSummoney()!=0)
			{
				tRIBsnsBillDetailsSet.add(debRIBsnsBillDetailsSchema);
			}
			if(creRIBsnsBillDetailsSchema.getSummoney()!=0)
			{
				tRIBsnsBillDetailsSet.add(creRIBsnsBillDetailsSchema);
			}
		}
		
	}	
	//�˵���˽��۱���
	if("conclusion".equals(mOperateType))
	{
	    String conclusion = request.getParameter("RIUWReport"); //���۱���
		String tRadio[] = request.getParameterValues("InpAuditBillListGridSel");  
	    String tBillNo [] = request.getParameterValues("AuditBillListGrid1"); //�˵����
	    
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				String mBillNo = tBillNo[index]; //�˵����
				System.out.println("�˵����::"+mBillNo);
				tRIBsnsBillBatchSchema.setBillNo(mBillNo);
				tRIBsnsBillBatchSchema.setState(conclusion);
			}
		}
	}
	//=====================	
	try
	{
	  	tTransferData.setNameAndValue("","");
	  	
	  	tVData.addElement(tTransferData);
	  	tVData.addElement(globalInput);
	    tVData.addElement(tRIBsnsBillBatchSchema);
	    tVData.addElement(tRIBsnsBillDetailsSet);
	    
	    tBusinessDelegate.submitData(tVData,mOperateType,"LRBsnsBillUWUI");
	}
	catch(Exception ex)
	{
		Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
		FlagStr = "Fail";
	}
	
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr=="")
	{
	    tError = tBusinessDelegate.getCErrors();
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