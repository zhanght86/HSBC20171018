//该文件中包含客户端需要处理的函数和事件
var showInfo;

//提交，保存按钮对应操作
function submitForm()
{
    fm.action = "./PDPrtProblemListSave.jsp";
  	fm.target = "f1print";  
  	fm.submit(); //提交
 		
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {     
  	
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
}
