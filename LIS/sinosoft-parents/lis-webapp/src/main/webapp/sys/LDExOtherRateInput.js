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
	if(document.all('EndDate').value!=null&&document.all('EndDate').value!="")
	{
		if(compareDate(document.all('StartDate').value,document.all('EndDate').value)==1)
		{
			alert("结束日期不能早于开始日期，请修改后重新提交！");
			return false;
		}
	}
	if (!verifyInput())
    return false;
  if (!beforeSubmit())
    return false;
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    fm.hideOperate.value="INSERT||MAIN";
  }
  document.getElementById("fm").submit(); //提交
  fm.hideOperate.value="";
  mOperate="";
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在LDExOtherRateInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作
  if (mOperate == "INSERT||MAIN")
  {
  	if (!verifyInput())
  	  return false;
  }
  return true;
}           


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
  else 
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}


//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  if (!verifyInput())
    return false;
  mOperate="INSERT||MAIN";
  submitForm();
  clearData();//清空数据
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  
  //下面增加相应的代码
  if (confirm("您确实想修改该记录吗?"))
  {
    mOperate="UPDATE||MAIN";
    submitForm();
    clearData();//清空数据
  }
  else
  {
    mOperate="";
    alert("您取消了修改操作！");
  }
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  showInfo=window.open("./LDExOtherRateQuery.html");
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  if (!verifyInput())
    return false;
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
    mOperate="DELETE||MAIN";  
    submitForm();
    clearData();//清空数据
  }
  else
  {
    mOperate="";
    alert("您取消了删除操作！");
  }
}           

//清空数据
function  clearData(){
	document.all("CurrCode").value="";
	document.all("CurrCodeName").value="";
	document.all("Per").value="";
	document.all("DestCurrCode").value="";
	document.all("ExchRate").value="";
	document.all("ContPlanCode").value="";
	document.all("StartDate").value="";
	document.all("EndDate").value="";
	document.all("DestCodeName").value="";
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
function afterQuery(arrQueryResult)
{	
	var arrResult = new Array();
	
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('CurrCode').value = arrResult[0];  
		document.all('Per').value = arrResult[1];                                              
		document.all('DestCurrCode').value = arrResult[2];                                              
		document.all('ExchRate').value = arrResult[3];   
    document.all('StartDate').value = arrResult[4];
    document.all('EndDate').value = arrResult[5];                                           
    emptyUndefined();                                                                                                                                                                                                                                   	
 	}
 	
}