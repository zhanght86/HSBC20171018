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
	RIProLossValueSet mRIProLossValueSet = new RIProLossValueSet();
 	RIProLossResultSchema mRIProLossResultSchema = new RIProLossResultSchema();
	//LRProfitLossCalBL mLRProfitLossCalBL = new LRProfitLossCalBL();
  	BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	System.out.println("������������"+mOperateType);
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
	System.out.println("��ʼ���л�ȡ���ݵĲ���! ");
	
	//ӯ��Ӷ�����
	if("conclusion".equals(mOperateType))
	{
	    String tRadio[] = request.getParameterValues("InpLossUWListGridSel");  
	    String tSerialNo [] = request.getParameterValues("LossUWListGrid1"); //���
	    String tReComCode[] = request.getParameterValues("LossUWListGrid4"); //�ٱ���˾
	    String tRIContNo[] = request.getParameterValues("LossUWListGrid2"); //�ٱ���ͬ	 
	    String tCalYear [] = request.getParameterValues("LossUWListGrid6"); //�������
	    
	    String tConclusion = request.getParameter("RILossUWReport");
	    
	    //������ʽ=�� Inp+MulLine������+Sel�� 
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				String mSerialNo = tSerialNo[index]; //���к�
				String mRIContNo=tRIContNo[index]; //��ͬ����
				String mReComCode=tReComCode[index]; //�ֱ���˾
				String mCalYear = tCalYear[index]; //���
				
				System.out.println("���к�::"+mSerialNo);
				System.out.println("��ͬ���::"+mRIContNo);
				System.out.println("�ֱ���˾::"+mReComCode); 
				System.out.println("���::"+mCalYear);

				mRIProLossResultSchema.setSerialNo( mSerialNo);
				mRIProLossResultSchema.setCalYear(mCalYear);
				mRIProLossResultSchema.setReComCode(mReComCode);
				mRIProLossResultSchema.setRIContNo(mRIContNo);
				mRIProLossResultSchema.setStandbyString1(tConclusion);
			}
		}		
		mDescType = ""+"ӯ��Ӷ����˲����ɹ�!"+"";
	}
	else
	{
		mRIProLossResultSchema.setSerialNo( request.getParameter("SerialNo"	));
		mRIProLossResultSchema.setCalYear(	request.getParameter("CalYear"	));
		mRIProLossResultSchema.setReComCode(request.getParameter("RIcomCode"));
		mRIProLossResultSchema.setRIContNo( request.getParameter("ContNo"		));
		mRIProLossResultSchema.setStartDate(request.getParameter("StartDate"));
		mRIProLossResultSchema.setEndDate(	request.getParameter("EndDate"	));		
	}

	String[] incomNum					=request.getParameterValues("IncomeGridNo");
	String[] incomFactorCode	=request.getParameterValues("IncomeGrid1");
	String[] incomFactorName	=request.getParameterValues("IncomeGrid2");
	String[] incomInOutType		=request.getParameterValues("IncomeGrid3");
	String[] incomFactorValue	=request.getParameterValues("IncomeGrid4");
  
	if(incomNum!=null)
	{
	  	RIProLossValueSchema tRIProLossValueSchema;
	  	for(int i=0;i<incomNum.length;i++)
	  	{
	  		tRIProLossValueSchema=new RIProLossValueSchema();
	  			
	  	    tRIProLossValueSchema.setReComCode(	request.getParameter("RIcomCode") );
	  	    tRIProLossValueSchema.setRIContNo(	request.getParameter("ContNo"));
	  	    tRIProLossValueSchema.setSerialNo(	""	);
	  	    tRIProLossValueSchema.setFactorCode(	incomFactorCode[i]);
	  	    tRIProLossValueSchema.setFactorName(	incomFactorName[i]	);
			System.out.println(" cccccc: "+incomFactorName[i]);
	  	    tRIProLossValueSchema.setInOutType(		"01"	);
	  	    tRIProLossValueSchema.setFactorValue(	incomFactorValue[i]	);
	  	    mRIProLossValueSet.add(tRIProLossValueSchema);
	  	}
	}	
	if(mOperateType.equals("CALCULATE"))
	{
		mDescType = ""+"���������Ѽ������"+"";
	}
	if(mOperateType.equals("UPDATE"))
	{
		mDescType = ""+"�޸��ٱ���ͬ��Ϣ"+"";
	}
	if(mOperateType.equals("DELETE"))
	{
		mDescType = ""+"ɾ���ٱ���ͬ"+"";
	}
	if(mOperateType.equals("QUERY"))
	{
		mDescType = ""+"��ѯ�ٱ���ͬ"+"";
	}
	VData tVData = new VData(); 
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
	double result = 0;
	if (FlagStr=="")
	{
	    tError = blBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {
	    	System.out.println("����ɹ�!");
	    	TransferData sTransferData = (TransferData)blBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
			result = (Double)sTransferData.getValueByName("Double");
	      	Content = mDescType+""+"�ɹ���"+""+" "+"ӯ��Ӷ��"+""+result;
	    	FlagStr = "Succ";
	    	if("conclusion".equals(mOperateType))
	    	{
	    		Content = mDescType;
	    	}
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>"); 
</script>
</html>