 <%
//�������ƣ�IndiFinFeeUrgeGetInit.jsp
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
 document.all('TempFeeNo').value='';
  }
  catch(ex)
  {
    alert("��FinVerifyInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("FinVerifyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>
