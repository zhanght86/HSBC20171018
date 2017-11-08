//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var DealWithNam ;


//批量计算
function StatisticData()
{
	try
	{
		if(verifyInput()&& verifyInput2())
		{	
			var i = 0;
			var showStr=""+"正在统计数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			fm.action = "LRBsnsBillSave.jsp"
			fm.submit();
			showInfo.close();
		}
	}	
	catch(ex) 
	{
	  	showInfo.close( );
	  	myAlert(ex);
   }
}

function verifyInput1()
{ 
	var mStaTerm=fm.StartDate.value;
	var year = mStaTerm.substr(0,4);
	var season = mStaTerm.substr(5);
	if(!isInteger(year)||mStaTerm.length>6||parseFloat(season)>4)
	{
	    myAlert(""+"统计期间时出现错误!"+"");
	    return  false;
	}
	return true;
}  

//重  置
function ResetForm()
{
	fm.StartDate.value='';
	fm.EndDate.value='';
	fm.ContNo.value='';
	fm.ContName.value='';
	fm.RIcomCode.value='';
	fm.RIcomName.value='';
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content)
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
		if (fm.OperateType.value=="DELETE")
		{
			ResetForm();
		}
	}
}
function afterCodeSelect( cCodeName, Field )
{

}
