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
	var showStr="正在进行卡号密码校验，请您稍候并且不要修改屏幕上的值或链接其他页面!";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交
}


function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
     //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	 var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
       //通过校验则转向客户具体信息录入界面
       if(fm.CertifyNo.value.substring(2,3)=="7"){
    	   window.location.href="./NewSelfProposalInput.jsp?CertifyNo="+fm.CertifyNo.value;
       }else{
	       window.location.href="./SelfProposalInput.jsp?CertifyNo="+fm.CertifyNo.value;
       }
  }
}



//重置操作:重新初始化元素initForm()
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

//校验输入
function vertifyInput()
{
  //alert("***111");
  
  if((fm.CertifyNo.value==null)||(fm.CertifyNo.value==""))
  {
    alert("卡号不能为空！！！");
    return false;
  }
  
  //alert("***222");
  
  if(fm.CertifyNo.value.length!=20)
  {
    alert("卡号不是20位！！！");
    return false;
  }
  
  //alert("***333");
  if((fm.Password.value==null)||(fm.Password.value==""))
  {
     alert("密码不能为空！！！");
     return false;
  }
  
  //alert("***444");
  
  if(fm.Password.value.length!=8)
  {
    alert("密码不是8位！！！");
    return false;
  }
  
  
  return;
}

