<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSendPrintInit.jsp
//�����ܣ��ʹ�ӡ����
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tProposalNo = "";
  String tOtherNoType = "";
  String tCode = "";
  tProposalNo = request.getParameter("ProposalNo2");
  tOtherNoType = request.getParameter("OtherNoType2");
  tCode = request.getParameter("Code2");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">                                

function initForm(tProposalNo,tOtherNoType,tCode)
{
  try
  {
	initHide(tProposalNo,tOtherNoType,tCode);
	submitForm();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initHide(tProposalNo,tOtherNoType,tCode)
{
	fm.all('ProposalNoHide').value=tProposalNo;
	fm.all('OtherNoType').value=tOtherNoType;
	fm.all('Code').value= tCode;	
}

</script>


