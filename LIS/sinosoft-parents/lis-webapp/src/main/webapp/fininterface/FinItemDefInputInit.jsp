<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ڣ�2008-08-11
//������  ��ZhongYan
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
  	//document.all('FinItemID').readOnly=false;    	
    document.all('FinItemID').value = '';   	
    document.all('FinItemName').value = '';  	 
    document.all('FinItemType').value = '';
    document.all('ItemMainCode').value = '';
    document.all('DealMode').value = '1';                                 
  	//document.all('VersionNo').readOnly=false;       
  	//document.all('VersionState').readOnly=false;
  	  	
		document.all('updatebutton').disabled = true;		
		document.all('deletebutton').disabled = true;  	       
  }
  catch(ex)
  {
    alert("��FinItemDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��FinItemDefInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("FinItemDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}



</script>
