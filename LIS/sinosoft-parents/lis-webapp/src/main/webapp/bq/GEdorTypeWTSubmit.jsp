<%
//�������ƣ�GEdorTypeWTSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
 <%@page import="com.sinosoft.service.*" %>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  
  String busiName="bqgrpGrpEdorWTDetailUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  LPGrpEdorMainSchema tLPGrpEdorMainSchema   = new LPGrpEdorMainSchema();
  LPPolSet tLPPolSet = new LPPolSet();
  LPPolSchema tLPPolSchema = new LPPolSchema();

 
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
 //loggerDebug("GEdorTypeWTSubmit","-----"+tG.Operator);
  //�������
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
    
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String Result ="";
  String transact = "";
  String mContType = "";
   
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");

	transact = request.getParameter("fmtransact");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String grpContNo = request.getParameter("GrpContNo");
	//alert(grpContNo);
	String getMoney = request.getParameter("GetMoney");
	if(getMoney==null||"".equals(getMoney)){
	    getMoney="0";
	}
	
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	tLPGrpEdorItemSchema.setEdorNo(edorNo);
	tLPGrpEdorItemSchema.setGrpContNo(grpContNo);
	tLPGrpEdorItemSchema.setEdorType(edorType);
	tLPGrpEdorItemSchema.setGetMoney(getMoney);
        // ׼���������� VData		
	VData tVData = new VData();
	tVData.add(tGlobalInput);
	tVData.add(tLPGrpEdorItemSchema);
 

if (!tBusinessDelegate.submitData(tVData, "",busiName))
	{
	 loggerDebug("EGEdorTypeWTSubmit.jsp","===========11111111111111111111111111=============");
		VData rVData = tBusinessDelegate.getResult();
		Content = transact + "ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
		FlagStr = "Fail";
	}
	else 
	{
		 loggerDebug("GEdorTypeWTSubmit.jsp","===========2342423423424333333333333333333333=============");
		Content = "����ɹ�";
		FlagStr = "Success";
	} 
  //��Ӹ���Ԥ����
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","");
</script>
</html>

