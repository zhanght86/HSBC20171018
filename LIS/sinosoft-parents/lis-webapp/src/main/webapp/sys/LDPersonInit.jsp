<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
//程序名称：LDPersonInit.jsp
//程序功能：客户管理（新增客户）
//创建日期：2005-06-20
//创建人  ：wangyan
//更新记录：更新人    更新日期     更新原因/内容
%>

<%
   //添加页面控件的初始化。
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
    alert("在LDPersonInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LDPersonInputInit.jsp-->InitInforBox函数中发生异常:初始化界面错误!");
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
    alert("在LDPersonInputInit.jsp-->InitAddressBox函数中发生异常:初始化界面错误!");
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
    alert("在LDPersonInputInit.jsp-->InitAccountBox函数中发生异常:初始化界面错误!");
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
    alert("LDPersonInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

