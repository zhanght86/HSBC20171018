<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
	// ������ѯ����
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
     document.all('AppManageType').value = '';
    document.all('HQReportType').value = 'HQ02';    
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
