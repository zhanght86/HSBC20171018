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
  	//document.all('CertificateID').readOnly=false;    	
    //document.all('CertificateID').value = '';   	
    //document.all('CertificateName').value = '';  	 
    //document.all('DetailIndexID').value = '';
    //document.all('DetailIndexName').value = '';
    //document.all('AcquisitionType').value = '';                                 
    //document.all('Remark').value = ''; 
  	//document.all('VersionNo').readOnly=false;       
  	//document.all('VersionState').readOnly=false;
  	  	
		document.all('updatebutton').disabled = true;		
		document.all('deletebutton').disabled = true;  	       
  }
  catch(ex)
  {
    alert("��CertificateTypeDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��CertificateTypeDefInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("��CertificateTypeDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}



</script>
