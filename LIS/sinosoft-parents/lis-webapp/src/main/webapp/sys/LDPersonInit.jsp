<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
//�������ƣ�LDPersonInit.jsp
//�����ܣ��ͻ����������ͻ���
//�������ڣ�2005-06-20
//������  ��wangyan
//���¼�¼��������    ��������     ����ԭ��/����
%>

<%
   //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   

    style=" text-align: left"('Transact').value='';
  }
  catch(ex)
  {
    alert("��LDPersonInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initInfoBox()
{ 
  try
  {
    style=" text-align: left"('Name').value = '';
    style=" text-align: left"('AppntSex').value = '';
    style=" text-align: left"('AppntSexName').value = '';
    style=" text-align: left"('Birthday').value = '';
    style=" text-align: left"('AppntIDType').value = '';
    style=" text-align: left"('AppntIDTypeName').value = '';
    style=" text-align: left"('IDNo').value = '';
    style=" text-align: left"('Password').value = '';
    style=" text-align: left"('AppntNativePlace').value = '';
    style=" text-align: left"('AppntNativePlaceName').value = '';
    style=" text-align: left"('AppntNationality').value = '';
    style=" text-align: left"('AppntNationalityName').value = '';
    style=" text-align: left"('RgtAddress').value = '';
    style=" text-align: left"('AppntMarriage').value = '';
    style=" text-align: left"('AppntMarriageName').value = '';
    style=" text-align: left"('MarriageDate').value = '';
    style=" text-align: left"('HealthState').value = '';
    style=" text-align: left"('HealthStateName').value = '';
    style=" text-align: left"('Stature').value = '';
    style=" text-align: left"('Avoirdupois').value = '';
    style=" text-align: left"('DegreeState').value = '';
    style=" text-align: left"('DegreeStateName').value = '';
    style=" text-align: left"('CreditGrade').value = '';
    style=" text-align: left"('CreditGradeName').value = '';
    style=" text-align: left"('OthIDType').value = '';
    style=" text-align: left"('OthIDTypeName').value = '';
    style=" text-align: left"('OthIDNo').value = '';
    style=" text-align: left"('ICNo').value = '';
    style=" text-align: left"('GrpNo').value = '';
    style=" text-align: left"('JoinCompanyDate').value = '';
    style=" text-align: left"('StartWorkDate').value = '';
    style=" text-align: left"('Position').value = '';
    style=" text-align: left"('Salary').value = '';
    style=" text-align: left"('OccupationTypeState').value = '';
    style=" text-align: left"('OccupationCodeState').value = '';
    style=" text-align: left"('WorkType').value = '';
    style=" text-align: left"('PluralityType').value = '';
    style=" text-align: left"('DeathDate').value = '';
    style=" text-align: left"('SmokeFlag').value = '';
    style=" text-align: left"('SmokeFlagName').value = '';
    style=" text-align: left"('listFlag').value = '';
    style=" text-align: left"('listFlagName').value = '';
    style=" text-align: left"('ProtertyState').value = '';
    style=" text-align: left"('ProtertyStateName').value = '';
    style=" text-align: left"('Remark').value = '';
    style=" text-align: left"('State').value = '';
    style=" text-align: left"('VIPValueState').value = '';
    style=" text-align: left"('VIPValueStateName').value = '';
  }
  catch(ex)
  {
    alert("��LDPersonInputInit.jsp-->InitInforBox�����з����쳣:��ʼ���������!");
  }      
}


function initAddressBox()
{ 
  try
  {
   
    style=" text-align: left"('ZipCode').value = '';
   
    style=" text-align: left"('HomeAddress').value = '';
   
    style=" text-align: left"('CompanyAddress').value = '';
    style=" text-align: left"('CompanyPhone').value = '';
    style=" text-align: left"('CompanyFax').value = '';
    style=" text-align: left"('Mobile').value = '';
    style=" text-align: left"('EMail').value = '';
    
  }
  catch(ex)
  {
    alert("��LDPersonInputInit.jsp-->InitAddressBox�����з����쳣:��ʼ���������!");
  }      
}


function initAccountBox()
{ 
  try
  {                                   
    style=" text-align: left"('AccounteFlag').value = '';
    style=" text-align: left"('AccounteFlagName').value = '';
    style=" text-align: left"('BankCodeState').value = '';
    style=" text-align: left"('BankCodeStateName').value = '';
    style=" text-align: left"('GetBankAccNo').value = '';
    style=" text-align: left"('AccName').value = '';
  }
  catch(ex)
  {
    alert("��LDPersonInputInit.jsp-->InitAccountBox�����з����쳣:��ʼ���������!");
  }      
}

                                     

function initForm()
{
  try
  {
    //initInpBox();
   
  }
  catch(re)
  {
    alert("LDPersonInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

