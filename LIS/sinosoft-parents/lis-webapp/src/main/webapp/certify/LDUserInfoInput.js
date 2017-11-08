//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.LDUserInfoInputSql";
//单证状态查询
function certifyQuery()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	initUserInfoGrid(); 	
	/*var strSQL="select a.comcode,(select name from ldcom where comcode=a.comcode), "
			+" a.usercode, a.username, a.validstartdate, a.validenddate "
			+" from lduser a where 1 = 1 "
			+" and exists (select 1 from ldusertomenugrp b where b.usercode = a.usercode "
			+" and exists (select 1 from ldmenugrptomenu c where c.menugrpcode = b.menugrpcode "
			+" and exists (select 1 from ldmenu d where d.nodecode = c.nodecode and d.nodename = '单证管理(新)'))) "
			+ getWherePart('a.comcode','ManageCom')
			+ getWherePart('a.validstartdate','validstartdate','<=')
			+ getWherePart('length(a.comcode)','grade')
			+" order by a.comcode, a.usercode" ;*/
	var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ManageCom').value,document.all('validstartdate').value,document.all('grade').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, UserInfoGrid);
   	//alert("查询到的记录行数："+UserInfoGrid.mulLineCount);
   	if(UserInfoGrid.mulLineCount==0)
   	{
   	    showInfo.close();
   		alert("没有查询信息！");
   		return false;
   	}
   	showInfo.close();
}

//[打印]按钮函数
function certifyPrint()
{
	//alert("查询到的记录行数："+UserInfoGrid.mulLineCount);
   	if(UserInfoGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'UserInfoGrid','turnPage');	
}
