<%
//�������ƣ�LDExOtherRateInit.jsp
//�����ܣ�
//�������ڣ�2009-10-13 9:42:43
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
    document.all('CurrCodeName').value = '';
    document.all('Per').value = '';
    document.all('DestCurrCode').value = '';
    document.all('DestCodeName').value = '';
    document.all('ExchRate').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';

  }
  catch(ex)
  {
    alert("��LDExOtherRateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("LDExOtherRateInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
