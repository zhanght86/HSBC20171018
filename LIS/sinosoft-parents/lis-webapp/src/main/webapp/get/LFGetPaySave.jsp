<%
//�������ƣ�LJSGetDrawInput.jsp
//�����ܣ�
//�������ڣ�2002-07-19 11:48:25
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //������Ϣ������У�鴦��



  CErrors tError = new CErrors();
  String Content="";
  String FlagStr="";
  String tPrtSeq = "";
  String transact = request.getParameter("fmtransact");
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
 
if("GETMONEY".equals(transact)){
	
  String tGrpContNo = request.getParameter("GrpContNoBak");
  LFGetPayNewBL tLFGetPayNewBL = new LFGetPayNewBL();
  try
  {
	  tLFGetPayNewBL.setGlobalInput(tGlobalInput);
	  tLFGetPayNewBL.setAppName(request.getParameter("LFAppName"));
	  tLFGetPayNewBL.dealLFGet(tGrpContNo);
	  tPrtSeq = tLFGetPayNewBL.getPrtSeq();
      tError = tLFGetPayNewBL.mErrors;
      }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
}else if("UPDATEPAYMODE".equals(transact)){
	String tContNo = request.getParameter("ContNo");
	
	String tPayMode = request.getParameter("PayMode"); //������ʽ ��ӦBNF��Remark
	String tDrawer = request.getParameter("Drawer"); //��ȡ������,��ӦBNF��Name
	String tIDNo =  request.getParameter("DrawerIDNo"); //��ȡ�˵����֤��,��ӦBNF��IDNo
	String tBankCode = request.getParameter("BankCode"); //��ȡ�˵Ŀ������б���,��ӦBNF��BankCode
	String tBankAccNo = request.getParameter("BankAccNo"); //��ȡ�˵Ŀ��������˻�,��ӦBNF��BankAccNo
	String tAccName = request.getParameter("AccName"); //��ȡ�˵Ŀ������л���,��ӦBNF��AccName
	if(tBankCode==null){
		tBankCode = "";
	}
	if(tBankAccNo==null){
		tBankAccNo = "";
	}
	if(tAccName==null){
		tAccName = "";
	}
	String tSql = "Update LjsGet set "
				+ "PayMode = '"+tPayMode+"', Drawer = '"+tDrawer+"', "
				+ "BankAccNo = '"+tBankAccNo+"', AccName = '"+tAccName+"' ,"
				+ "DrawerID = '"+tIDNo+"', BankCode = '"+tBankCode+"' Where OtherNo = '"+tContNo+"' and OtherNoType = '2' and GetDate <= now()";
	 MMap tMap = new MMap();
     tMap.put(tSql, "UPDATE");
     VData tVData = new VData();
     tVData.add(tMap);
     //�����ύ
     PubSubmit tSubmit = new PubSubmit();
     if (!tSubmit.submitData(tVData, "")) {
     	Content = " ����ʧ�ܣ�ԭ����:" + tSubmit.mErrors.getFirstError();
     	FlagStr = "Fail";   	
     }
}
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
 {
 	
   if (!tError.needDealError())
   {
     Content = " �����ɹ�";
   	 FlagStr = "Succ";
    }
   else
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tPrtSeq%>");
</script>
</html>

