<%
//**************************************************************************************************
//�ļ����ƣ�LLPrtagainInit.jsp
//�ļ����ܣ���֤����ԭ��¼��ҳ���ʼ��
//�����ˣ�yuejw
//��������: 2005-08-21
//ҳ������: �����ڲ���֤ʱ¼�벹��ԭ��
//**************************************************************************************************
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
	fm.all('PrtSeq').value =nullToEmpty("<%=tPrtSeq%>");
	fm.all('prtType1').value =nullToEmpty("<%=tPrtType%>");
	fm.all('customerNo').value =nullToEmpty("<%=tCustomerNo%>");
	fm.all('RgtClass').value =nullToEmpty("<%=tRgtClass%>");
	//alert("prtType1 =: "+fm.all('prtType1').value);
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

function initForm()
{
    try
    {
    	initParam();
    	initLoprtQuery();
    }
    catch(re)
    {
        alert("��LLPrtagainInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
</script>
