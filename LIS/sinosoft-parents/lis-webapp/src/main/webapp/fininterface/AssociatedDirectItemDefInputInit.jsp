 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ڣ�2008-09-16
//������  ��Fanxin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                    

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  { 	
    document.all('ColumnID').value = '';
    document.all('SourceTableID').value = 'FIAboriginalData';
    document.all('SourceColumnID').value = '';              
    document.all('ReMark').value = '';                                             
  	document.all('ColumnID1').value = '';
		document.all('updatebutton').disabled = true;		
		document.all('deletebutton').disabled = true;  	       
  }
  catch(ex)
  {
    alert("��AssociatedDirectItemDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��AssociatedDirectItemDefInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("��AssociatedDirectItemDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}



</script>
