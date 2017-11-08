//程序名称：UWManuGrpSpec.js
//程序功能：人工核保条件承保
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保

//提交，保存按钮对应操作
function submitForm()
{
	
	if(fm.SpecReason.value==null||fm.SpecReason.value=="")
	{
		 alert("特约原因不能为空");	   		  
		 return false;
	}
	if(fm.SpecReason.value.length>400)
	{
		 alert("特约原因长度不能超过200个汉字");	   		  
		 return false;
	}	
	if(fm.Remark.value==null||fm.Remark.value=="")
	{
		alert("特约内容不能为空");
		return false;
		
	}
	if(fm.Remark.value.length>400)
	{
		alert("特约内容长度不能超过200个汉字");
		return false;
		
	}	
	fm.Operate.value="INSERT||GrpUWSpec";
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,content)
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
  }
  else
  { 
	  var showStr="操作成功";  	
  	showInfo.close();
  	alert(showStr);
    //执行下一步操作
  }
}


function QueryGrpSpecGrid()
{
  var strSQL = "select grpcontno,prtno,grpname from lcgrpcont where grpcontno='"+fm.GrpContNo.value+"'";
  turnPage.queryModal(strSQL,UWSpecGrid);
  getGrpSpec();
  return true;	
}

//团体特约查询
function getGrpSpec()
{
  var strSQL = "select a.specreason,b.speccontent from LCGCUWMaster a, LCCGrpSpec b where a.grpcontno=b.grpcontno and a.grpcontno='"+fm.GrpContNo.value+"' ";
  var arrResult=new Array();
  arrResult=easyExecSql(strSQL);
  if(arrResult!=null)
  {
    fm.Remark.value=arrResult[0][1];
    fm.SpecReason.value=arrResult[0][0];
  }
}
//特约信息删除
function deleteSpec()
{
	fm.Operate.value="DELETE||GrpUWSpec";
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit(); //提交
}