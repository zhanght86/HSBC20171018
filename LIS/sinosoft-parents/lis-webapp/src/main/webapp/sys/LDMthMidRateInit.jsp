<%
//�������ƣ�LDMthMidRateInit.jsp
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
    document.all('CurrCodeName').value = '';
    document.all('Per').value = '';
    document.all('DestCode').value = '';
    document.all('DestCodeName').value = '';
    document.all('Average').value = '';
    document.all('ValidYear').value = '';
    document.all('ValidMonth').value = '';

  }
  catch(ex)
  {
    alert("��LDMthMidRateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("LDMthMidRateInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
