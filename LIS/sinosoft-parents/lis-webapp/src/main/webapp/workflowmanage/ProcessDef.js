//               该文件中包含客户端需要处理的函数和事件
/*******************************************************************************
 * <p>Title       : 任务分配</p>
 * <p>Description : 用来将系统中已经申请的任务分配给其它员工</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量


//******************************************************************

function ReleaseClick(){
   var row = WorkFlowGrid.getSelNo()	//获得选中的Radio行号  
    var rows = row-1;
    var Date = new Array; //定义一数组     
    Date = WorkFlowGrid.getRowData(rows)  //取出指定的某一行的值
    var priorityid = Date[0];
    var busitype1= Date[2];
    var version = Date[6]
    var busitype = encodeURI(encodeURI(busitype1));
    var tempdate ="&OperFlag="+"RE||Condition"+"&ProcessID="+priorityid+"&Busitype="+busitype+"&Version="+version;
    var urli = "ProcessRe.jsp?OperFlag=RE||Condation";
    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
    AjaxRequestObj.open("POST", urli, true);
    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    AjaxRequestObj.send(tempdate); 
    AjaxRequestObj.onreadystatechange = function(){
     if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);
		  query();                
                 if(showStr==("发布成功!")){
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
               }else{
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " 发布失败! " ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
             }
          }
      }


}

function query()
{
	var arr=new Array();
	var strSQL = "";
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ProcessDefSql");
    mySql.setSqlId("ProcessDefSql1");
    mySql.addSubPara(fm.ProcessID.value);   	
	mySql.addSubPara(fm.BusiType.value); 		
    initWorkFlowGrid();
	turnPage.queryModal(mySql.getString(), WorkFlowGrid);
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if(typeof(showInfo)=="object"){showInfo.close();if(typeof(showInfo.parent)=="object" && typeof(showInfo.parent) != "unknown"){showInfo.parent.focus();if(typeof(showInfo.parent.parent)=="object" && typeof(showInfo.parent.parent) != "unknown"){showInfo.parent.parent.blur();}}}

  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    query();
  }
}
var dialogURL="";
function newClick()
{
//	  dialogURL="../wfpic/workflow.jsp?action=new&flowId=''" ;  
//	  showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	  dialogURL="../workflow/workflow.jsp?action=new&flowId=''" ; 
	  window.open(dialogURL);
}
function updateClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要修改的流程！");
	      return;
	}	
  var processId =WorkFlowGrid.getRowColData(selno,1);  
  var version =WorkFlowGrid.getRowColData(selno,7);

//  dialogURL="../wfpic/workflow.jsp?action=update&flowId="+processId+"&Version="+version;  
//	showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	dialogURL="../workflow/workflow.jsp?action=update&flowId="+processId+"&Version="+version;  
	  window.open(dialogURL);
}
function copyClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要复制的流程！");
	      return;
	}	
  var processId =WorkFlowGrid.getRowColData(selno,1);  
  var version =WorkFlowGrid.getRowColData(selno,7);
  //dialogURL="../wfpic/workflow.jsp?action=copy&flowId="+processId+"&Version="+version;  
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	dialogURL="../workflow/workflow.jsp?action=copy&flowId="+processId+"&Version="+version;
	window.open(dialogURL);

}
function deleteClick()
{
 	   var selno = WorkFlowGrid.getSelNo()-1;
	   if (selno <0)
	   {
	      alert("请选择要删除的流程！");
	      return;
	   }	
     var processId =WorkFlowGrid.getRowColData(selno,1);  
     var version =WorkFlowGrid.getRowColData(selno,7); 
     var showStr="正在删除流程定制信息，请您稍候并且不要修改屏幕上的值或链接其他页面";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	 //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	 var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     fm.action="./ProcessSave.jsp?hideOperate=DELETE||MAIN&ProcessID="+processId+"&Version="+version; 
     document.getElementById("fm").submit();
}


function queryClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要查看的流程！");
	      return;
	}	
	//zhaojiawei
  var processId =WorkFlowGrid.getRowColData(selno,1); 
  var version =WorkFlowGrid.getRowColData(selno,7);   

  //dialogURL="../wfpic/workflow.jsp?action=query&flowId="+processId+"&Version="+version;
  dialogURL="../workflow/workflow.jsp?action=query&flowId="+processId+"&Version="+version;
  window.open(dialogURL);
  //showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
}

function checkBusiType()
{
	if (document.all('BusiType').value==""||document.all('BusiType').value==null)
	{
		alert("请先选择一个业务类型")
		return false;
	}
}