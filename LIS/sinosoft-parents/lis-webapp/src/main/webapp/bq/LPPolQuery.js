//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  initLPPolGrid();
  document.all('fmtransact').value = "QUERY||POL";
 // showSubmitFrame(mDebug);
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Result )
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
    // 书写SQL语句
			 // alert(Result);                    
		 
	//查询SQL，返回结果字符串
	  turnPage.strQueryResult  = Result;  
	  
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) 
	  {
	    alert("查询失败！");
	  }
	  else
	  {
	  		//查询成功则拆分字符串，返回二维数组
	  	var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
	  	turnPage.arrDataCacheSet= chooseArray(tArr,[1,5,20,21,11,31]);
	  	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	  	turnPage.pageDisplayGrid = LPPolGrid;    
	  
	  	//设置查询起始位置
	 	 turnPage.pageIndex       = 0;  
	  
	 	 //在查询结果数组中取出符合页面显示大小设置的数组
	  	var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  
	  	//调用MULTILINE对象显示查询结果
	  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
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
  	alert("在LPPolQuery.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

// 查询按钮
function easyQueryClick()
{
	var EdorState;
	
	// 初始化表格
	initLPPolGrid();
	var tReturn = parseManageComLimit();

	// 书写SQL语句
	var strSQL = "";
	
	var sqlid818151019="DSHomeContSql818151019";
var mySql818151019=new SqlClass();
mySql818151019.setResourceName("bq.LPPolQueryInputSql");//指定使用的properties文件名
mySql818151019.setSqlId(sqlid818151019);//指定使用的Sql的id
mySql818151019.addSubPara(fm.PolNo.value);//指定传入的参数
mySql818151019.addSubPara(tReturn);//指定传入的参数
strSQL=mySql818151019.getString();
	
//	strSQL = "select PolNo,ContNo,GrpPolNo,ProposalNo,AppntName,CValiDate from LPPol where appFlag = '1'"+" and"+tReturn
//				 +" "+ getWherePart( 'PolNo' );
					 
	//查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) 
	  {
	    alert("查询失败！");
	  }
	  else
	  {
	  	//查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	  turnPage.pageDisplayGrid = LPPolGrid;    
	          
	  //保存SQL语句
	  turnPage.strQuerySql     = strSQL; 
	  
	  //设置查询起始位置
	  turnPage.pageIndex       = 0;  
	  
	  //在查询结果数组中取出符合页面显示大小设置的数组
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  
	  //调用MULTILINE对象显示查询结果
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}

function returnParent()
{
    tSel=LPPolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
    {
//			alert("pp");
			top.close();
		}
    else
    {
    	var tPolNo=LPPolGrid.getRowColData(tSel-1,1);
    	try{
    	   		top.opener.document.all('PolNo').value = tPolNo;
				}catch(e){
					}
	
		top.opener.afterQuery();
		top.close();

    }
}

function getPolNo()
{
	 tSel=LPPolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
			top.close();
    else
    {
    	tCol=1;
   		tPolNo = LPPolGrid.getRowColData(tSel-1,tCol);
   		top.opener.document.all('PolNo').value = tPolNo;
   	}
}