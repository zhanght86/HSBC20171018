var showInfo;
window.onfocus=myonfocus;
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  { 
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

function submitForm()
{

	if(vertifyInput() == false)
	{
		  return;
	}
	var i = 0;
	var showStr="���ڽ��п�������У�飬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��!";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
}


function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
     //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
       //ͨ��У����ת��ͻ�������Ϣ¼�����
       if(fm.CertifyNo.value.substring(2,3)=="7"){
    	   window.location.href="./NewSelfProposalInput.jsp?CertifyNo="+fm.CertifyNo.value;
       }else{
	       window.location.href="./SelfProposalInput.jsp?CertifyNo="+fm.CertifyNo.value;
       }
  }
}



//���ò���:���³�ʼ��Ԫ��initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("ActivateInput.js-->resetForm!");
  }
} 


//frmSubmit
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  }	else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}

function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

//У������
function vertifyInput()
{
  //alert("***111");
  
  if((fm.CertifyNo.value==null)||(fm.CertifyNo.value==""))
  {
    alert("���Ų���Ϊ�գ�����");
    return false;
  }
  
  //alert("***222");
  
  if(fm.CertifyNo.value.length!=20)
  {
    alert("���Ų���20λ������");
    return false;
  }
  
  //alert("***333");
  if((fm.Password.value==null)||(fm.Password.value==""))
  {
     alert("���벻��Ϊ�գ�����");
     return false;
  }
  
  //alert("***444");
  
  if(fm.Password.value.length!=8)
  {
    alert("���벻��8λ������");
    return false;
  }
  
  
  return;
}

