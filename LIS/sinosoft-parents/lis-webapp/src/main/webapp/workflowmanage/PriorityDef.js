//               该文件中包含客户端需要处理的函数和事件
/*******************************************************************************
 * <p>Title       : 优先级别管理</p>
 * <p>Description : 用来将定义优先级别内容</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2012-5-25
 ******************************************************************************/
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量 
var turnPage1 = new turnPageClass();
function Addto(){
    var rows = WorkFlowGridP.getSelNo();
    if (rows == 0){
    alert("请选择指定行。谢谢！");
    return false;
    }else{
    var row = WorkFlowGridP.getSelNo()	//获得选中的Radio行号  
    var rows = row-1;
    var Date = new Array; //定义一数组     
    Date = WorkFlowGridP.getRowData(rows)  //取出指定的某一行的值
    var priorityid = Date[5];
    var version = Date[6];
    var activityid = Date[1];
    var sqls1 = Date[3];   
    var sqls = encodeURI(encodeURI(sqls1));
    var tempdate ="&OperFlag="+"INSERT||Condition"+"&ProcessID="+fma.ProcessID.value+"&ActivityID="+activityid+"&PriorityID="+priorityid+"&Sqls="+sqls+"&Version="+version;
    var urli = "PriorityDefSave.jsp?OperFlag=INSERT||Condation";
    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
    AjaxRequestObj.open("POST", urli, true);
    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    AjaxRequestObj.send(tempdate); 
    AjaxRequestObj.onreadystatechange = function(){
     if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);                
                 if(showStr==("添加成功!")){
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
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " 添加失败! " ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
                 alert("流程号，流程版本为必填字段")
             }
          }
      }
   }
}
function search(){
	var arr=new Array();
	  var strSQL = "Select c.priorityname, b.color,b.colorid,c.range,c.priorityid from lwpriority c left join lwprioritycolor b on c.colorid = b.colorid  ";
	  initWorkGridPa();
	  turnPage1.queryModal(strSQL, WorkGridPa); 
}
function updatepriority(){
    var rows = WorkGridPa.getSelNo()
    if (rows == 0){
    alert("请选择指定行。谢谢！");
    return false;
    }else{
	    var row = WorkGridPa.getSelNo()	//获得选中的Radio行号  
	    var rows = row-1;
	    var Date = new Array; //定义一数组    
	    Date = WorkGridPa.getRowData(rows)  //取出指定的某一行的值
	    var priorityid = Date[4];
	    var colorid = Date[2];
	    var range = Date[3]; 
	    var priorityname1 = Date[0]
	    var priorityname = encodeURI(encodeURI(priorityname1));
	    var tempdate ="&OperFlag="+"UPDATE||PRIORITY"+"&priorityid="+priorityid+"&colorid="+colorid+"&range="+range+"&priorityname="+priorityname;
	    var urli = "PriorityDefSave.jsp?OperFlag=UPDATE||PRIORITY";
	    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
	    AjaxRequestObj.open("POST", urli, true);
	    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    AjaxRequestObj.send(tempdate); 
	    AjaxRequestObj.onreadystatechange = function(){
	     if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
	                 var result =  AjaxRequestObj.responseText;
	                 var showStr = result.substr(1,5);                
	                 if(showStr==("更新成功!")){
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
	                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " 更新失败! " ;
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
}
function updateclick(){
    var rows = WorkFlowGridP.getSelNo()
    if (rows == 0){
    alert("请选择指定行。谢谢！");
    return false;
    }else{
var row = WorkFlowGridP.getSelNo()	//获得选中的Radio行号  
    var rows = row-1;
    var Date = new Array; //定义一数组     
    Date = WorkFlowGridP.getRowData(rows)  //取出指定的某一行的值
    var priorityid = Date[5];
    var activityid = Date[1];
    var version = Date[6];
    var sqls1 = Date[3];   
    var sqls = encodeURI(encodeURI(sqls1));
    var tempdate ="&OperFlag="+"UPDATE||Condition"+"&ProcessID="+fma.ProcessID.value+"&ActivityID="+activityid+"&PriorityID="+priorityid+"&Sqls="+sqls+"&Version="+version;
    var urli = "PriorityDefSave.jsp?OperFlag=UPDATE||Condation";
    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
    AjaxRequestObj.open("POST", urli, true);
    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    AjaxRequestObj.send(tempdate); 
    AjaxRequestObj.onreadystatechange = function(){
    if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);        
                 if(showStr==("修改成功!")){
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
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + "修改失败!" ;
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
  }
}
function deleteClick(){
    var rows = WorkFlowGridP.getSelNo()
    if (rows == 0){
    alert("请选择指定行。谢谢！");
    return false;
    }else{
        var row = WorkFlowGridP.getSelNo()	//获得选中的Radio行号  
        var rows = row-1;
        var Date = new Array; //定义一数组     
        Date = WorkFlowGridP.getRowData(rows)  //取出指定的某一行的值
        var priorityid = Date[5];
        var activityid = Date[1];
        var sqls = Date[3];   
        var version = Date[6];
        var tempdate ="&OperFlag="+"DELETE||Condition"+"&ProcessID="+fma.ProcessID.value+"&ActivityID="+activityid+"&PriorityID="+priorityid+"&Sqls="+sqls+"&Version="+version;
        var urli = "PriorityDefSave.jsp?OperFlag=DELETE||Condation";
        var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
        AjaxRequestObj.open("POST", urli, true);
        AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        AjaxRequestObj.send(tempdate); 
        AjaxRequestObj.onreadystatechange = function(){
        if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);
                 if(showStr==("删除成功!")){
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
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " 删除成功! " ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
          }
                 query();
          }
       }   
    }       
}
function query()
{ 
  checkprocess();
  var arr=new Array();
  var strSQL = "Select t.priorityname, d.activityid,(select activityname from lwactivity where activityid = d.activityid) ,d.prioritysql ,d.processid,t.priorityid,d.version from lwpriority t join lwprioritysql d on t.priorityid = d.priorityid  where processid = '"+fma.ProcessID.value+"'";
  initWorkFlowGridP();
  turnPage.queryModal(strSQL, WorkFlowGridP); 
}
function checkprocess()
{
	if (document.all('ProcessID').value==""||document.all('ProcessID').value==null)
	{
		alert("过程名称不能为空，请选择一个流程")
		return false;
	}
}
function checkBusiType()
{
	if (document.all('BusiType').value==""||document.all('BusiType').value==null)
	{
		alert("请先选择一个业务类型")
		return false;
	}
}
function getProcessName(p1,p2) 
{
	return showCodeList('prioritysql',[document.all(p1).all('WorkFlowGridP2'),document.all(p1).all('WorkFlowGridP3')],[0,1],null,document.all("ProcessID").value,'ProcessID',1);
}