<%
//�������ƣ�LDUWUserInput.jsp
//�����ܣ�
//�������ڣ�2005-01-24 18:15:01
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
   document.all('HospitalCode').disabled=false;    
   //document.all('deletebutton').disabled=true;              
    document.all('HospitalCode').value = "";
    document.all('HospitalName').value = "";
    document.all('HospitalGrade').value = "";
    document.all('ManageCom').value = "";
    document.all('ValidityDate').value = "";
    document.all('Address').value = "";
    document.all('HospitalGradeName').value = "";
    document.all('ManageComName').value = "";
     document.all('HosStateName').value = "";
      document.all('HosState').value = "";
       document.all('Remark').value = "";
  }
  catch(ex)
  {
    alert("��LDUWUserInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LDUWUserInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("LDUWUserInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


</script>
