<%
//�������ƣ�YBTFinfeeInit.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%> 

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('FeeSum').value = '';
    document.all('FileName').value = '';
  }
  catch(ex)
  {
    alert("��YBTFinfeeInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("YBTFinfeeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
