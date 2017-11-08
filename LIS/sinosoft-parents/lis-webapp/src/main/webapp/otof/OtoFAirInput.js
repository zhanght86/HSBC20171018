//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}
}

//航意险凭证提取
function SubmitFormAir()
{
		var bdate = fm.all('Bdate').value;
		var edate = fm.all('Edate').value;
		var now = getCurrentDate('-');
		if(bdate == null || bdate == "" || edate == null || edate == "")
		{
			alert("请录入起止日期！");
			return ;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}
		if(dateDiff(bdate,now,"D")<=2 ||dateDiff(edate,now,"D")<=2)
		{
			alert("请提取两天前的数据，避免与自动提数冲突");
			return ;
			}
	    fm.Opt.value="Air";
    fm.action = "./OtoFAirSave.jsp";
    fm.all('distill').disabled = true;
    fm.submit();

}

