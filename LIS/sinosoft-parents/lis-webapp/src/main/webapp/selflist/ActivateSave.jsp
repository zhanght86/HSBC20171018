<%
 loggerDebug("ActivateSave","Start ActivateSave.jsp Submit...1");
//�������ƣ�ActivateInput.jsp
//�����ܣ����������
//�������ڣ�2008-03-05 
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.lis.ebusiness.*"%>
  <%@page import="java.util.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

  //������Ϣ������У�鴦��
  //�������
  //�������

  String mInputNo=request.getParameter("CertifyNo"); //����
  String mPassword = request.getParameter("Password"); //����
  String Content="";
  String FlagStr="";
  CErrors tError = null;
  
  loggerDebug("ActivateSave","��ʼ���л�ȡ���ݵĲ���������");
 
  //���������ǲ��룬��ѯ������
  loggerDebug("ActivateSave","ҳ��¼��Ŀ�����"+mInputNo);
  loggerDebug("ActivateSave","ҳ��¼���������"+mPassword);

  VData tResult=new VData();
  //�������ύ����̨�������п��ź�����У��,��������Vector��
  try
  {
	    //���������ͣ��������������Ա��ӵ�������
	    Vector testVector=new Vector();
	    testVector.add("Check");
    	testVector.add(mInputNo);
    	testVector.add(mPassword);
	    
    	//�������
    	ActivateBL tActivateBL=new ActivateBL();
    	tResult=(VData)tActivateBL.submitData(testVector);
    	
    	loggerDebug("ActivateSave","���صı�ʶλ:"+tResult.get(0));
    	loggerDebug("ActivateSave","���ص���ʾ��Ϣ:"+tResult.get(1));
  }
  catch(Exception ex)
  {
       Content = "У��ʧ�ܣ�ԭ����:" + ex.toString();
       FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    if (tResult.get(0).equals("success"))
    {
        Content = (String)tResult.get(1);
    	FlagStr = "Succ";
    }
    else
    {
    	Content = (String)tResult.get(1);
    	FlagStr = "Fail";
    }
  }
   loggerDebug("ActivateSave","Flag : " + FlagStr + " -- Content : " + Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

