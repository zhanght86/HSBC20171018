<%
//�������ƣ�
//�����ܣ��ͻ����ϱ��
//�������ڣ�2008-12-5 12:49����
//������  ��pst
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%
    
%>                            

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {  
    //Edorquery();
    document.all('CustomerNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value; 
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeBBInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

                                       

function initForm()
{
  try
  {
   
    initInpBox();  
    initInpRole();  
    initInpCustomerInfo();
    initInpPCustomerInfo();
  }
  catch(re)
  {
    alert("PEdorTypeBBInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


</script>