<%
//�������ƣ�NBCListInit.jsp
//�����ܣ�
//�������ڣ�2003-05-14 15:39:06
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	//String strOperator = globalInput.Operator;
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('StartDay').value = '';
    document.all('EndDay').value = '';
    document.all('SaleChnl').value = '';
  }
  catch(ex)
  {
    alert("��NBCListInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                                      

function initForm()
{
  try
  {
    initInpBox();        
  }
  catch(re)
  {
    alert("NBCListInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
