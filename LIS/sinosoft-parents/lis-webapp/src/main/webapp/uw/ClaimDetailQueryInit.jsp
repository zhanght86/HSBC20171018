<%
//�������ƣ�ClaimDetailQueryInit.jsp
//�����ܣ�����������ϸ��ѯ
//�������ڣ�2005-10-08 09:10:36
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
                   

 <SCRIPT src="../common/javascript/Common.js"></SCRIPT>

 <script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
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
		initQuery();
		queryRegister();
//		afterQueryLL();
		ClaimType();
		Register();
		queryAudit();
		queryConfirm();
		queryEndCase();
		queryConfDate();
		
		//LLCollectorGrid();
  }
  catch(re)
  {
    alert("ClaimDetailQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


</script>





