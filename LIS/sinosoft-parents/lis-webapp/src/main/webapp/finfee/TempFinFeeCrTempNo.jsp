<%
//�������ƣ�TempFinFeeCrTempNo.jsp
//�����ܣ�
//�������ڣ�2010-04-15
//������  ����ΰ
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
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.finfee.*"%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  
  loggerDebug("TempFinFeeCrTempNo","��ʼִ��Saveҳ��");
  String tLimit="" ;
  String ManageCom = "";
  ManageCom = request.getParameter("ManageCom");
  tLimit = PubFun.getNoLimit(ManageCom);
  String tTempNo = "TS"+PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// ϵͳ�������վݺ�

%>
<html>
<script language="javascript">
	parent.fraInterface.AfterCreateTempNo("<%=tTempNo%>");
</script>
</html>
