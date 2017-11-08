//该文件中包含客户端需要处理的函数和事件

//程序名称：RIAccDistill.js
//程序功能：分出责任定义-数据采集
//创建日期：2011/6/16
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();


//方案查询按钮
function button100( )
{

}

//关联方案按钮
function button101( )
{

}


//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作 
}

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

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}

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

