<%
//�������ƣ�LABranchGroupInit.jsp
//�����ܣ�
//�������ڣ�
//������  ��CrtHtml���򴴽�
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
    document.all('AgentGroup').value = '';
    document.all('Name').value = '';
    document.all('ManageCom').value = '';
    document.all('UpBranch').value = '';
    document.all('BranchAttr').value = '';
    //document.all('BranchType').value = '';
    document.all('BranchLevel').value = '';
    document.all('BranchManager').value = '';
    document.all('BranchManagerName').value = '';
    //document.all('BranchAddressCode').value = '';
    document.all('BranchAddress').value = '';
    document.all('BranchPhone').value = '';
    document.all('BranchFax').value = '';
    document.all('BranchZipcode').value = '';
    document.all('FoundDate').value = '';
    document.all('EndDate').value = '';
    document.all('EndFlag').value = '';
    //document.all('CertifyFlag').value = '';
    document.all('FieldFlag').value = '';
    document.all('UpBranchAttr').value = '';
    document.all('Operator').value = '';
    
    if (top.opener == null)
    {

      document.all('BranchType').value = getBranchType();
    }else
    {
      document.all('BranchType').value = top.opener.fm.BranchType.value;
    }

  }
  catch(ex)
  {
    alert("��LABranchGroupInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��LABranchGroupInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("LABranchGroupInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
