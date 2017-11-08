
//               该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//turnPage.showTurnPageDiv = 0;
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
//提交，保存按钮对应操作
function submitForm()
{
	//lockPart("NativeConfig","正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
	lockPage("正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
	if(beforeSubmit())
	{
		document.all('fmtransact').value='INSERT||MAIN';
    var i = 0;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    document.getElementById("fm").submit(); //提交
  }
  else
  {
  	//unlockPart("NativeConfig");
  	unLockPage();
  }
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	//unlockPart("NativeConfig");
	unLockPage();
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
    initForm();
    //执行下一步操作
  }
}
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
  if(!verifyInput2())
     return false;
  return true;
} 
          



function queryMaxNoGrid()
{
	var sqlid1="ConfigLDMaxNoInputSql1";
	var mySql1=new SqlClass();
	
	mySql1.setResourceName("config.ConfigLDMaxNoInputSql"); //指定使用的properties文件名
  mySql1.setSqlId(sqlid1);//指定使用的Sql的id
  mySql1.addSubPara('1');//指定传入的参数
	var aSQL=mySql1.getString();
	
  turnPage.queryModal(aSQL, MaxNoGrid);
}               
        
function showRuleDetail()
{
	var tSelNo = MaxNoGrid.getSelNo();
	
	if(tSelNo!=-1)
	{
		var tNoCode=MaxNoGrid.getRowColData(tSelNo-1,1);
		document.all('NoCode').value = tNoCode;
		var tNoName=MaxNoGrid.getRowColData(tSelNo-1,2);
		document.all('NoName').value = tNoName;
		var tShowRule=MaxNoGrid.getRowColData(tSelNo-1,3);
		document.all('ShowRule').value = tShowRule;
		var tStartDate=MaxNoGrid.getRowColData(tSelNo-1,4);
		document.all('StartDate').value = tStartDate;
		var tEndDate=MaxNoGrid.getRowColData(tSelNo-1,5);
		document.all('EndDate').value = tEndDate;
	}
}


function configRule()
{
	var tNoCode = document.all('NoCode').value;
	if(tNoCode==null||tNoCode=='')
	{
		alert('请先保存规则之后再配置!');
		return false;
		
	}
	var tStartDate = document.all('StartDate').value;
	var urlStr = "./ConfigLDMaxNoRuleMain.jsp?NoCode="+tNoCode+"&LimitType=0"+"&StartDate="+tStartDate;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:800px;dialogHeight:450px;scroll:no");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=800;      //弹出窗口的宽度; 
	var iHeight=450;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

function configRuleLimit()
{
	var tNoCode = document.all('NoCode').value;
	if(tNoCode==null||tNoCode=='')
	{
		alert('请先保存规则之后再配置!');
		return false;
		
	}
	var tStartDate = document.all('StartDate').value;
	var urlStr = "./ConfigLDMaxNoRuleMain.jsp?NoCode="+tNoCode+"&LimitType=1"+"&StartDate="+tStartDate;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:800px;dialogHeight:450px;scroll:no");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=800;      //弹出窗口的宽度; 
	var iHeight=450;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

function query()
{
	var sqlid2="ConfigLDMaxNoInputSql2";
	var mySql2=new SqlClass();
	
	mySql2.setResourceName("config.ConfigLDMaxNoInputSql"); //指定使用的properties文件名
  mySql2.setSqlId(sqlid2);//指定使用的Sql的id
  mySql2.addSubPara('1');//指定传入的参数
  mySql2.addSubPara(document.all('NoCodeQuery').value);//指定传入的参数
  mySql2.addSubPara(document.all('NoNameQuery').value);//指定传入的参数
  
	var aSQL=mySql2.getString();
	
  turnPage.queryModal(aSQL, MaxNoGrid);
}

$(function(){
		$('#w').window('close');
});
