
<%
	//�������ƣ�LLClaimInqBillInit.jsp
	//�����ܣ���������δ����鰸���嵥
	//�������ڣ�2009-04-16
	//������  ��mw
	//���¼�¼��
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%

%>
<script language="JavaScript">

//����ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('MngCom').value = nullToEmpty("<%= tG.ManageCom %>");
    showOneCodeName("station","MngCom","MngComName"); 
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
}

function initForm()
{
	try
	{
		initParam();
	}
	catch(re)
	{
	  alert("��LLClaimNoRgtBillInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}
</script>
