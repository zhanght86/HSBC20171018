<%
//��������:��ȫ�����ӡ
//�����ܣ�
//�������ڣ�2003-11-17
//������  ��guoxiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    String busiName="f1printBQCheckUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
    loggerDebug("BQF1Print","��ʼִ�д�ӡ����");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    //��������
    String Code = request.getParameter("Code");
    loggerDebug("BQF1Print","�������ʹ���:"+Code);
    //ʱ��
    String mDay[]=new String[2];
    mDay[0]=request.getParameter("StartTime");
    mDay[1]=request.getParameter("EndTime");
    //����
    String gi="";
    gi=request.getParameter("StationCode");
    //��ȫ��Ŀ
    String bqcode="";
    bqcode=request.getParameter("bqcode");
    //����
    String strOperation ="PRINTPAY";
    //����Ա����
     String opt="";
     opt=request.getParameter("opt");
    
    //
    String tAppOperator=request.getParameter("AppOperator");
    String tConfOperator=request.getParameter("ConfOperator");
    VData tVData = new VData();
    VData mResult = new VData();
    tVData.addElement(mDay);
    tVData.addElement(gi);
    tVData.addElement(bqcode);
    tVData.addElement(Code);
    tVData.addElement(opt);
    tVData.addElement(tAppOperator);
    tVData.addElement(tConfOperator);
    tVData.addElement(tG);

    CError cError = new CError( );
    CErrors mErrors = new CErrors();
    XmlExport txmlExport = new XmlExport();
    
    
    if(!tBusinessDelegate.submitData(tVData,strOperation,busiName)){
      mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
      cError.moduleName = "BQF1Print";
      cError.functionName = "submitData";
      cError.errorMessage = "tBQCheckUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
      mErrors.addOneError(cError);
    }
    mResult = tBusinessDelegate.getResult();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null){
       loggerDebug("BQF1Print","nullû����ȷ�Ĵ�ӡ���ݣ�����");
    }else{
       session.putValue("PrintStream", txmlExport.getInputStream());
       loggerDebug("BQF1Print","put session value");
       response.sendRedirect("../f1print/GetF1Print.jsp");
    }

%> 
<html>
  <script language="javascript">
    top.opener.focus();
    top.close();
   </script>
</html>

