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
	//lockPart("NativeConfig","正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
	lockPage("正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
	if(beforeSubmit())
	{
		document.all('fmtransact').value='INSERT||MAIN';
    var i = 0;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    document.getElementById("fm").submit(); //提交
  }
  else
  {
  	//unlockPart("NativeConfig");
  	unLockPage();
  }
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	//unlockPart("NativeConfig");
	unLockPage();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initForm();
    //执行下一步操作
  }
}
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
  if(!verifyInput())
     return false;
  return true;
} 
          



function queryCurrentCountry()
{
	var sqlid1="ConfigNativePlaceInputSql1";
	var mySql=new SqlClass();
	mySql.setResourceName("config.ConfigNativePlaceInputSql"); //指定使用的properties文件名
	mySql.setSqlId(sqlid1);//指定使用的Sql的id
	mySql.addSubPara('1');//指定传入的参数
	var strSql=mySql.getString();	
	var tValue = easyExecSql(strSql);  
	
	document.all('CurCountry').value = tValue[0][0]; 
	document.all('CurCountryName').value = tValue[0][1]; 
}               
        
