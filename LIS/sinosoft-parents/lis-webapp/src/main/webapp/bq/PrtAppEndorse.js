//程序名称：PrtAppEndorse.js
//程序功能：
//创建日期：2005-03-03 
//创建人  ：FanX
//更新记录：  更新人    更新日期     更新原因/内容
//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{  
  PrtEdor(); //提交
}


function prtCashValue() {
  PrtEdor("CashValue"); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
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
	alert("query click");
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
	
	//add by jiaqiangli 2009-06-08
	if((fm.EdorAcceptNo.value == ""||fm.EdorAcceptNo.value ==null) && (fm.OtherNo.value == ""||fm.OtherNo.value ==null))
  {
			alert( "条件不能为空,请输入受理号或者保单号" );
			return false;
	}			
	// 初始化表格
	initPEdorAppGrid();
	var tReturn = parseManageComLimit();

	// 书写SQL语句
	var strSQL = "";
/*	strSQL = "select EdorAcceptNo, OtherNo,EdorAppName,codename,EdorAppDate from LPEdorApp,ldcode where 1=1 and trim(code) = trim(EdorState) and trim(codetype) = 'edorstate' and edorstate <> '0' and edorstate <> 'b' and exists (select 'A' from lpedoritem a,lpedorprint b where a.edorno = b.edorno and a.edoracceptno = LPEdorApp.edoracceptno)"//+" and"+tReturn
	                         +" and OtherNoType in ('1','3')"
				 +" "+ getWherePart( 'OtherNo' )
				 +" "+ getWherePart( 'OtherNoType')
				 +" " + getWherePart('EdorAcceptNo')
	                         + " order by EdorAppDate";*/
	 //alert(strSQL);
	 var sqlid = 'PrtAppEndorseSql1';
	 var mySql1 = new SqlClass();
		mySql1.setResourceName("bq.PrtAppEndorseSql"); // 指定使用的properties文件名
		mySql1.addSubPara(window.document.getElementsByName(trim('OtherNo'))[0].value);// 指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('OtherNoType'))[0].value);// 指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);// 指定传入的参数
		mySql1.setSqlId(sqlid);// 指定使用的Sql的id
		strSQL = mySql1.getString();			 
				 
          //查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
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
	  turnPage.pageDisplayGrid = PEdorAppGrid;    
	          
	  //保存SQL语句
	  turnPage.strQuerySql     = strSQL; 
	  
	  //设置查询起始位置
	  turnPage.pageIndex       = 0;  
	  
	  //在查询结果数组中取出符合页面显示大小设置的数组
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  arrGrid = arrDataSet;
	  //调用MULTILINE对象显示查询结果
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}


//查询明细信息
function detailEdor()
	{
		var arrReturn = new Array();
		var tSel = PEdorAppGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "请先选择一条记录，再查看" );
		else
		{
			if (PEdorAppGrid.getRowColData(tSel-1,1)==null||PEdorAppGrid.getRowColData(tSel-1,1)=='')
			{
				alert("请单击查询按钮!");
				return;
			}
			//alert(top.opener.document.all('EdorState').value);
			if (top.opener.document.all('EdorState').value =='2') 
			{
				arrReturn = getQueryResult();
				document.all('EdorAcceptNo').value = arrReturn[0][0];
				detailEdorType();
			}
			else
			{
				alert("还没有申请确认！");
				top.close();
			}
		}
	}
	//打印批单
function PrtEdor(printType)
{
	var arrReturn = new Array();
				
		var tSel = PEdorAppGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "请先选择一条记录，再点击查看按钮。" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorAcceptNo').value = arrReturn[0];
			fm.action = "../f1print/AppEndorsementF1PJ1.jsp?type="+printType;
			fm.target="f1print";		 	
			document.getElementById("fm").submit();
		}
}
function PrtEdorold(type)
{
	var arrReturn = new Array();
				
		var tSel = PEdorAppGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "请先选择一条记录，再点击查看按钮。" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorNo').value = arrReturn[0];
			document.all('ContNo').value=arrReturn[1];	
			/*parent.fraInterface.*/fm.action = "../f1print/EndorsementF1P.jsp?type="+type;
			/*parent.fraInterface.*/fm.target="f1print";		 	
			// showSubmitFrame(mDebug);
			document.getElementById("fm").submit();
			/*parent.fraInterface.*/fm.action = "./PEdorQueryOut.jsp";
			/*parent.fraInterface.*/fm.target="fraSubmit";
		}
}

function changeEdorPrint()
{
	//var arrReturn = new Array();
				
		//var tSel = PEdorAppGrid.getSelNo();
	
		//if( tSel == 0 || tSel == null )
			//alert( "请先选择一条记录，再点击查看按钮。" );
		//else
		//{
			//arrReturn = getQueryResult();
			//document.all('EdorNo').value = arrReturn[0];
			//document.all('ContNo').value=arrReturn[1];	
			/*parent.fraInterface.*/fm.action = "./ChangeEdorPrint.jsp";
			/*parent.fraInterface.*/fm.target="f1print"; 
			//document.getElementById("fm").submit();
			/*parent.fraInterface.*/fm.action = "./PEdorQueryOut.jsp";
			/*parent.fraInterface.*/fm.target="fraSubmit";
		//}
		window.open("./ChangeEdorPrint.jsp");
}
// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PEdorAppGrid.getSelNo();
	
	
		
	if( tSel == 0 || tSel == null )
		top.close();
		//alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				//alert( "没有发现父窗口的afterQuery接口。" + ex );
			}
			top.close();
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PEdorAppGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	arrSelected = PEdorAppGrid.getRowData(tRow - 1);	
	return arrSelected;
}
