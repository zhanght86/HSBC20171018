//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
	var strSQL = "";
	strSQL = "select a.ricontno,a.RIContName,"
	+" a.conttype,'',"
	+" a.risktype,'',"
	+" a.reinsurancetype,decode(a.reinsurancetype,'01','"+"成数分保"+"','02','"+"溢额分保"+"','03','"+"成数溢额分保"+"','"+"不知名类型"+"'),"
	+" a.cvalidate,a.enddate from RIBarGainInfo a where 1=1 "
	if(fm.RIContName.value!=null&&fm.RIContName.value!=""){
		strSQL = strSQL + " and a.RIContName like '%%"+fm.RIContName.value+"%%'" ;
	}
	
	strSQL = strSQL +" order by RIContNo";
	var arrResult = easyExecSql(strSQL);
	
	turnPage.queryModal(strSQL, ReContGrid);
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

  //showInfo.close();
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  }
  else
  {
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
  	myAlert(""+"在Proposal.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
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

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  myAlert("update click");
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
	myAlert("query click");
	//查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  myAlert("delete click");
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
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData()
{
	try{
		var tRow=ReContGrid.getSelNo();
  	if (tRow==0)
  	{
  		myAlert(""+"请您先进行选择!"+"");
  		return;
  	}
  	
		top.opener.fm.all('ModRIContNo').value 					=ReContGrid.getRowColData(tRow-1,1);
  	top.opener.fm.all('ModRIContName').value 				=ReContGrid.getRowColData(tRow-1,2);
  	top.close(); 
	}catch(ex){
		myAlert(""+"返回页面时出现错误"+"");
		return false;
	}
}

function ClosePage()
{
	top.close();
}
