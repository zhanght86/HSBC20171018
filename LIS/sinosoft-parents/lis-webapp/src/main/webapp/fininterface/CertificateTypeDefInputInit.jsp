<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//创建日期：2008-08-11
//创建人  ：ZhongYan
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                    

<script language="JavaScript">

// 输入框的初始化（单记录部分）
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
    alert("在CertificateTypeDefInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在CertificateTypeDefInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("在CertificateTypeDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}



</script>
