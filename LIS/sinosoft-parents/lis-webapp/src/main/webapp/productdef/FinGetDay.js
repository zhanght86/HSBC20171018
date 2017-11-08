//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
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

//提交，保存按钮对应操作
function submitForm()
{
	if (verifyInput())
	{
	  var i = 0;
	  var showStr=""+"正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  //fm.hideOperate.value=mOperate;
	  //if (fm.hideOperate.value=="")
	  //{
	  //  alert("操作控制数据丢失！");
	  //}
	  //showSubmitFrame(mDebug);

	  fm.submit(); //提交
	}
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  fm.button1.disabled=false; 
  fm.button2.disabled=false; 
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {

var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
  	//parent.fraInterface.initForm();
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //执行下一步操作
  }
}

//保费收入明细
function printPay(flag)
{
	fm.action="../f1print/FinGetDaySave.jsp";
	if(flag=='finget'){
		fm.fmtransact.value="PRINT";
		fm.button1.disabled=true; 
	}else{
		fm.fmtransact.value="CONFIRM";
		fm.button2.disabled=true; 
	}
	submitForm();
}



//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}





//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if (confirm(""+"您确实想删除该记录吗?"+""))
  {
    mOperate="DELETE||MAIN";
    submitForm();
  }
  else
  {
    mOperate="";
    myAlert(""+"您取消了删除操作！"+"");
  }
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

function downAfterSubmit(cfilePath) {
	showInfo.close();
  fm.button1.disabled=false; 
  fm.button2.disabled=false; 

    //执行下一步操作
	fileUrl.href = cfilePath; 
  fileUrl.click();
}
