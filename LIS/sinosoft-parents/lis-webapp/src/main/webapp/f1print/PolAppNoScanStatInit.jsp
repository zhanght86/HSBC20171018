<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
	// ������ѯ����
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('AppReportType').value = '';
    document.all('AppManageType').value = '';
  //  document.all('AppSalechnl').value = '';
    
  }
  catch(ex)
  {
    alert("��PolAppStatInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("PolAppStatInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>