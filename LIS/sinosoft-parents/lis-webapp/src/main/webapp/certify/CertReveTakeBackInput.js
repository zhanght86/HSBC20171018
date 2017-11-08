//该文件中包含客户端需要处理的函数和事件
var showInfo;
var turnPage = new turnPageClass();
var tResourceName="certify.CertReveTakeBackInputSql";
//查询待批复的增领申请
function queryClick()
{
  initCertifyList();
  /*var strSql = "select a.applyno, a.certifycode, (select certifyname from lmcertifydes b where b.certifycode = a.certifycode),"
			 +" a.sendoutcom, a.receivecom, a.startno, a.endno, a.sumcount, a.reason "
			 +" from lzcardapp a where a.OperateFlag='2' and a.stateflag='1' "
			 + getWherePart('a.handler', 'handler')
			 + getWherePart('a.handledate', 'handledate')
			 + getWherePart('a.ApplyCom', 'ComCode', 'like')
			 +" order by a.certifycode, a.startno";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('Handler').value,document.all('HandleDate').value,document.all('ComCode').value]);
  turnPage.pageDivName = "divCertifyList";
  turnPage.queryModal(strSql, CertifyList);
  divCertifyList.style.display='';	  		 
  if (CertifyList.mulLineCount==0) {
 	 alert("没有查询到核销修订申请记录！");
     return false;
    }
}

//提交，保存按钮对应操作
function submitForm()
{
  CertifyList.delBlankLine("CertifyList");
  var nSelectedCount = CertifyList.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("请至少选择一个要修订确认的单证！ ");
    return false;
  }
  
  if (confirm("您确认修订确认操作吗?")){
	try {
		if( verifyInput() == true && CertifyList.checkValue("CertifyList") ) {
	  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
    } catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    alert("您取消了修订确认操作！");
  }	  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  fm.btnOp.disabled = false;  	
  if(FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }else { 
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;	    
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  queryClick();
}

//接收者是业务员时，弹出业务员的姓名和工号
var arrResult = new Array();
function queryAgent()
{
	var receivecom = document.all('ReceiveCom').value;
	if(receivecom != "" && receivecom.substring(0,1) == 'D')	 
	{
		if(trim(receivecom).length >= 11)
		{
			var cAgentCode = receivecom.substring(1,11);
			//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
			var strSql = wrapSql(tResourceName,"querysqldes2",[cAgentCode]);
	    	var arrResult = easyExecSql(strSql);
	    	if (arrResult != null) 
	      		alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
	    	else
				alert("编码为:["+ cAgentCode +"]的代理人不存在，请确认!");
		}
	}
}
