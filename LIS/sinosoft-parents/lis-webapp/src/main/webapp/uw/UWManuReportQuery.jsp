<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuReportQuery.jsp
//�����ܣ��˹��˱��˱�����¼��
//�������ڣ�
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//�������
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  String tReport = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) {
    out.println("session has expired");
    return;
  }

  //У�鴦��
  //���ݴ����

  //������Ϣ
  // Ͷ�����б�

  LCUWReportSchema tLCUWReportSchema = new LCUWReportSchema(); //����һ��Schema
  LCUWReportSet tLCUWReportSet = new LCUWReportSet(); //����һ��Set

  String tContNo = request.getParameter("ContNo");
  String tOperator = request.getParameter("Operator");
  String tContent = request.getParameter("Content");

  loggerDebug("UWManuReportQuery","ContNo:"+tContNo);    //��Ҫ��ʾ�Ĳ���
  loggerDebug("UWManuReportQuery","UWOperator:"+tOperator);
  loggerDebug("UWManuReportQuery","Contente:"+tContent);

  boolean flag = true;


  tLCUWReportSchema.setContNo(tContNo); //��t...Schema��ֵ
  tLCUWReportSchema.setUWOperator(tOperator);

  //����DB����֤��ѯ����
  LCUWReportDB tLCUWReportDB=new LCUWReportDB(); //��xxxΪ����������
  tLCUWReportDB.setSchema(tLCUWReportSchema); // txxxSchema �е����Ը�����Ҫ�Ѿ���ֵ
  tLCUWReportSet=tLCUWReportDB.query();    //����txxxSchema�в�Ϊ�յ����Բ�ѯ������xxxSet����
  if(tLCUWReportDB.mErrors.needDealError())
  {
    Content = "��ѯʧ��!";
    flag = false;
  }
  else
  {
    if(tLCUWReportSet.size() > 0)
    {
      tLCUWReportSchema=tLCUWReportSet.get(1);
      flag = true;
    }
  }

  if (flag == true)
  {      // ׼���������� VData
    VData tVData = new VData();
    tVData.add( tLCUWReportSet);
    tVData.add( tLCUWReportSchema);
    tVData.add( tContent);
    tVData.add( tG );

    // ���ݴ���
    UWReportUI tUWReportUI   = new UWReportUI();
    if (tUWReportUI.submitData(tVData,"QUERY") == false)
    {
      int n = tUWReportUI.mErrors.getErrorCount();
      for (int i = 0; i < n; i++)
        Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tUWReportUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
    }
    else
    {
    	tVData = new VData();
    	tVData = tUWReportUI.getResult();
    	tReport = (String)tVData.getObjectByObjectName("String",0);
    	if (tReport == null)
    	{
    		tReport = " ";
    	}
    	
    	String Path = application.getRealPath("config//Conversion.config");
	tReport = StrTool.Conversion(tReport,Path);   	
    	loggerDebug("UWManuReportQuery","content111:"+tReport);
%>
<html>
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
				<script language="javascript">
					<!--var treport = Conversion("<%=tReport%>");-->
					<!--parent.fraInterface.fm.Content.value= treport;-->
					parent.fraInterface.fm.Content.value=Conversion("<%=tReport%>");
				</script>
</html>
<%
    }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
      tError = tUWReportUI.mErrors;
      if (!tError.needDealError())
      {
        Content = " �˹��˱��ɹ�! ";
        FlagStr = "Succ";
      }
      else
      {
        Content = " �˹��˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
        FlagStr = "Fail";


      }
    }
  }

%>







