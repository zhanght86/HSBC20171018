<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
	// ������ѯ����
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
     document.all('AppSalechnl').value = '';
    document.all('HQReportType').value = 'HQ01';    
  }
  catch(ex)
  {
    alert("HospitalQualityQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("HospitalQualityQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>