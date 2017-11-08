 //该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//初始化查询--------------查询当前赔案在打印管理表的单证信息   
function showCertiPrtControl()
{
	/*var strSQL="select a.prtseq,a.code,b.prtname,a.managecom,a.reqcom,a.reqoperator,a.prttype,a.stateflag"
			+" from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode and a.stateflag='3' "
			+" and a.othernotype='05' and a.otherno='"+fm.ClmNo.value+"'"
			+" order by a.code";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimCertiPrtControlInputSql");
	mySql.setSqlId("LLClaimCertiPrtControlSql1");
	mySql.addSubPara(fm.ClmNo.value ); 
	this.pageLineNum=10;		
	turnPage.queryModal(mySql.getString(),ClaimCertiGrid);
}

//[保存按钮]
function showCertiPrtControlSave()
{    
	var row = ClaimCertiGrid.mulLineCount;
	var i = 0;
	for(var m=0;m<row;m++ )
	{
		if(ClaimCertiGrid.getChkNo(m))
		{
		  i=i+1;
		}
	}
	if(i==0)
	{
		alert("请选择至少一行数据才能保存！");
		return false;
	}
	fm.action="LLClaimCertiPrtControlSave.jsp";
	SubmitForm();  
}

//
function SubmitForm()
{
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit();  
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
        showCertiPrtControl(); 
    }

}