<%
//�������ƣ�OLDCodeQuery.js
//�����ܣ�
//�������ڣ�2002-08-16 17:44:48
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  LDCodeSchema tLDCodeSchema   = new LDCodeSchema();

  OLDCodeUI tOLDCodeQueryUI   = new OLDCodeUI();
  //��ȡSession�е�ȫ����
	GlobalInput tG = new GlobalInput();

	tG.Operator = "Admin";
	tG.ComCode  = "001";
  session.putValue("GI",tG);

  tG=(GlobalInput)session.getValue("GI");

    tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
    tLDCodeSchema.setCode(request.getParameter("Code"));
    tLDCodeSchema.setCodeName(request.getParameter("CodeName"));
    tLDCodeSchema.setCodeAlias(request.getParameter("CodeAlias"));
    tLDCodeSchema.setComCode(request.getParameter("ComCode"));
    tLDCodeSchema.setOtherSign(request.getParameter("OtherSign"));



  // ׼���������� VData
  VData tVData = new VData();

	tVData.addElement(tLDCodeSchema);
	tVData.add(tG);

  FlagStr="";
  // ���ݴ���
	if (!tOLDCodeQueryUI.submitData(tVData,"QUERY||MAIN"))
	{
      Content = " ��ѯʧ�ܣ�ԭ����: " +  tError.getFirstError();
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tOLDCodeQueryUI.getResult();
		
		// ��ʾ
		LDCodeSet mLDCodeSet = new LDCodeSet();
		mLDCodeSet.set((LDCodeSet)tVData.getObjectByObjectName("LDCodeSet",0));
		int n = mLDCodeSet.size();
		LDCodeSchema mLDCodeSchema;
		for (int i = 1; i <= n; i++)
		{
		  	mLDCodeSchema = mLDCodeSet.get(i);
		   	%>
		   	<script language="javascript">
        parent.fraInterface.CodeGrid.addOne("CodeGrid")
parent.fraInterface.fm.CodeGrid1[<%=i-1%>].value="<%=mLDCodeSchema.getCodeType()%>";
parent.fraInterface.fm.CodeGrid2[<%=i-1%>].value="<%=mLDCodeSchema.getCode()%>";
parent.fraInterface.fm.CodeGrid3[<%=i-1%>].value="<%=mLDCodeSchema.getCodeName()%>";

			</script>
			<%
		} // end of for
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (!FlagStr.equals("Fail"))
  {
    	Content = " ��ѯ�ɹ�! ";
    	FlagStr = "Succ";
  }

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

