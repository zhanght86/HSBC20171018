//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); 
var showInfo;
var mDebug="0";
var arrDataSet = new Array(); 
// 查询按钮
function easyQueryClick()
{
	
//	alert("here");
	// 初始化表格
	initCodeGrid();
	
	// 书写SQL语句
	var strSQL = "";
	
//	strSQL = "select codetype,code,codename,CodeAlias,ComCode,OtherSign from ldcode where 1 = 1"
//					+ getWherePart('CodeType')
//					+ getWherePart('Code')
//					+ getWherePart('CodeName')
//					+ getWherePart('CodeAlias ')
//					+ getWherePart('ComCode')
//					+ getWherePart('OtherSign')
//	alert(strSQL);
					
		var sqlid1="OLDCodeQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.OLDCodeQuerySql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CodeType"))[0].value);//指定传入参数
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Code"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CodeName"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CodeAlias"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("ComCode"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("OtherSign"))[0].value);
	 	strSQL = mySql1.getString();
				
					
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
//  alert(turnPage.strQueryResult);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = CodeGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  //var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
 // var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

//  initPolGrid();
  //showSubmitFrame(mDebug);
  easyQueryClick();
  //fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  }
  else
  { 
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
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
  	alert("在OLDCodeQuery.js-->resetForm函数中发生异常:初始化界面错误!");
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
  alert("update click");
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
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

function returnParent()
{
var arrResult = new Array();
	var tSel = CodeGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				//alert(tSel);
				//alert(CodeGrid.colCount);
				for (i=1;i<CodeGrid.colCount;i++)
			        {
                                 arrResult[i-1]=CodeGrid.getRowColData(tSel-1,i);
			        }
			        //alert(arrResult);
		 	        top.opener.afterQuery( arrResult );
				
			}
			catch(ex)
			{
				alert( "没有发现父窗口的afterQuery接口。" + ex );
			}
			top.close();
		
	}
}

function selectItem(spanID,parm2)
{
}
