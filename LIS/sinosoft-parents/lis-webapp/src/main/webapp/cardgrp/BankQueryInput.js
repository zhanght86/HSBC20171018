//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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

    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在AgentQuery.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}             

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = BankGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{			
			arrReturn = getQueryResult();
			top.opener.afterQuery6( arrReturn );			
		}
		catch(ex)
		{
			alert( "要返回的页面函数出错！");
		}
		top.close();
		
	}
}

function getQueryResult()
{
  var arrSelected = null; 
	tRow = BankGrid.getSelNo();
	if( tRow == 0 || tRow == null )
	{
	  return arrSelected;		
  }
  arrSelected = new Array();
  arrSelected[0] = new Array();  
  arrSelected[0]=BankGrid.getRowData(tRow-1);
  var tCount = BankGrid.mulLineCount;
  if(tCount>=2&&tRow!=10){
     //arrSelected[1]不知道为什么要添加到数组中 只有一条记录时 下面的语句会报错 没有删除 也许其他地方会用到
     //modify by liuqh 2008-12-11
     arrSelected[1]=BankGrid.getRowData(tRow);
  }
	return arrSelected;
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initBankGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var strOperate = "like";

	strSQL = "select BankCode,BankName from ldbank where 1=1 "	                   
	         + getWherePart('BankCode','BankCode',strOperate)
	         + getWherePart('BankName','BankName',strOperate);	      
     	
	turnPage.queryModal(strSQL,BankGrid,1,1);
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查询到满足条件的数据！");
    return false;
    }

}

