<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLInqCourseSave.jsp
//�����ܣ������ύ���������Ϣ
//�������ڣ�2005-6-22
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.claim.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //�������
	CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
	GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");
  	if(tG == null) 
  	{
		  loggerDebug("LLInqCourseSave","session has expired");
		return;
	}
    //������Ϣ
    loggerDebug("LLInqCourseSave","Start Save...");	
    //������̱� 
	LLInqCourseSchema tLLInqCourseSchema = new LLInqCourseSchema(); 
	tLLInqCourseSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
	tLLInqCourseSchema.setInqNo(request.getParameter("InqNo")); //�������
	//tLLInqCourseSchema.setCouNo("");	//���������ϵͳ����
	tLLInqCourseSchema.setInqDate(request.getParameter("InqDate")); //��������
	tLLInqCourseSchema.setInqMode(request.getParameter("InqMode")); //���鷽ʽ
	tLLInqCourseSchema.setInqSite(request.getParameter("InqSite")); //����ص�
	tLLInqCourseSchema.setInqByPer(request.getParameter("InqByPer")); //��������
	tLLInqCourseSchema.setInqDept(request.getParameter("InqDept")); //�������
	tLLInqCourseSchema.setInqPer1(request.getParameter("InqPer1")); //��һ������
	tLLInqCourseSchema.setInqPer2(request.getParameter("InqPer2")); //�ڶ�������
	tLLInqCourseSchema.setInqCourse(request.getParameter("InqCourse")); //�������
	tLLInqCourseSchema.setRemark(request.getParameter("InqCourseRemark")); //������̱�ע	 

    //����MulLine��������ݼ���---------������̵�֤��Ϣ
    LLInqCertificateSet tLLInqCertificateSet = new LLInqCertificateSet();
    String tGridNo[] = request.getParameterValues("LLInqCertificateGridNo");  //�õ�����е�����ֵ
    if(tGridNo==null)
    {
    	loggerDebug("LLInqCourseSave","������̵�֤��ĿCount====0");
    }	
    else
    {
    	int Count = tGridNo.length; //�õ����ܵ��ļ�¼��
    	loggerDebug("LLInqCourseSave","������̵�֤��ĿCount====0="+Count);
    	String tGrid1[] = request.getParameterValues("LLInqCertificateGrid1"); //�õ���1�У���֤����
	    String tGrid2[] = request.getParameterValues("LLInqCertificateGrid2"); //�õ���2�У���֤����
	    String tGrid3[] = request.getParameterValues("LLInqCertificateGrid3"); //�õ���3�У�ԭ����־
	    String tGrid4[] = request.getParameterValues("LLInqCertificateGrid4"); //�õ���4�У�����
	    String tGrid5[] = request.getParameterValues("LLInqCertificateGrid5"); //�õ���5�У���ע��Ϣ
        for(int index=0;index < Count;index++)
		{
			LLInqCertificateSchema tLLInqCertificateSchema=new LLInqCertificateSchema();
			tLLInqCertificateSchema.setClmNo(request.getParameter("ClmNo"));/** �ⰸ�� */
			tLLInqCertificateSchema.setInqNo(request.getParameter("InqNo"));/** ������� */
			//tLLInqCertificateSchema.setCouNo("");/** �������---���������ϵͳ���� */
			//tLLInqCertificateSchema.setCerNo("");/** ��֤���---��֤�����ϵͳ����*/
			tLLInqCertificateSchema.setCerType(tGrid1[index]);/** ��֤���� */
			tLLInqCertificateSchema.setCerName(tGrid2[index]);/** ��֤���� */
			tLLInqCertificateSchema.setOriFlag(tGrid3[index]);/** ԭ����־ */
			tLLInqCertificateSchema.setCerCount(tGrid4[index]);/** ���� */
			tLLInqCertificateSchema.setRemark(tGrid5[index]);/** ��ע */
			tLLInqCertificateSet.add(tLLInqCertificateSchema);
		}
    }

    
    // ׼���������� VData
    VData tVData = new VData();	
    tVData.add(tG);
    tVData.add(tLLInqCourseSchema);
    tVData.add(tLLInqCertificateSet);
    
    // ���ݴ���
//    LLInqCourseUI tLLInqCourseUI   = new LLInqCourseUI();
//	if (!tLLInqCourseUI.submitData(tVData,""))
//	{
//     	Content = " ����ʧ�ܣ�ԭ����: " + tLLInqCourseUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//	} 
	String busiName="LLInqCourseUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
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

   //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
   if (FlagStr == "Fail")
   {
    	//tError = tLLInqCourseUI.mErrors;
    	tError = tBusinessDelegate.getCErrors();
    	if (!tError.needDealError())
    	{                          
    		Content = " ����ɹ�! ";
    		FlagStr = "Succ";
    	}
    	else                                                                           
    	{
    		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    		FlagStr = "Fail";
    	}
    }
    //��Ӹ���Ԥ����
    loggerDebug("LLInqCourseSave","Content:"+Content);
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

