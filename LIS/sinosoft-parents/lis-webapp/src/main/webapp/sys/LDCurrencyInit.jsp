<%
//�������ƣ�LDCurrencyInit.jsp
//�����ܣ�
//�������ڣ�2009-10-12 19:29:43
//������  ��ZhanPeng���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('CurrCode').value = '';
    document.all('CurrName').value = '';
    document.all('Validation').value = '';
    document.all('CodeAlias').value = '';

  }
  catch(ex)
  {
    alert("��LDCurrencyInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("LDCurrencyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
