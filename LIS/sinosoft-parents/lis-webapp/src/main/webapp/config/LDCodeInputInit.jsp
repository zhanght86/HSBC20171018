<%
//�������ƣ�LDCodeInput.jsp
//�����ܣ�
//�������ڣ�2005-01-26 13:18:17
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            
<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('CodeType').value = "";
    document.all('Code').value = "";
    document.all('CodeName').value = "";
    document.all('CodeAlias').value = "";
    document.all('ComCode').value = "";
    document.all('OtherSign').value = "";
  }
  catch(ex)
  {
    alert("��LDCodeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
  }
  catch(ex)
  {
    alert("��LDCodeInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
  }
  catch(re)
  {
    alert("LDCodeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
