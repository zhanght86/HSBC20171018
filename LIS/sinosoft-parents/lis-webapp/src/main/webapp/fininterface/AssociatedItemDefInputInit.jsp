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
  	//document.all('AssociatedID').readOnly=false;    	
    document.all('AssociatedID').value = '';   	
    document.all('AssociatedName').value = '';  	 
    document.all('ColumnID').value = '';
    document.all('SourceTableID').value = 'FIAboriginalData';
    document.all('SourceColumnID').value = '';        
    //document.all('columnidName').value = '';        
    document.all('TransFlag').value = '';
    document.all('TransSQL').value = '';      
    document.all('TransClass').value = '';
    document.all('ReMark').value = '';                                             
  	//document.all('VersionNo').readOnly=false;       
  	//document.all('VersionState').readOnly=false;
  	  	
		document.all('updatebutton').disabled = true;		
		document.all('deletebutton').disabled = true;  	       
  }
  catch(ex)
  {
    alert("��AssociatedItemDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��AssociatedItemDefInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("��AssociatedItemDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}



</script>
