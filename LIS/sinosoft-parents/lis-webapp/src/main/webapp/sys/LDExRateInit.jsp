<%
//�������ƣ�LDExRateInit.jsp
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
    document.all('ExchBid').value = '';
    document.all('ExchOffer').value = '';
    document.all('CashBid').value = '';
    document.all('CashOffer').value = '';
    document.all('Middle').value = '';
    document.all('MakeDate').value = '';
    document.all('MakeTime').value = '';
    document.all('EndDate').value = '';
    document.all('EndTime').value = '';

  }
  catch(ex)
  {
    alert("��LDExRateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("LDExRateInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
