//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
 // var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 // showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");   
  PrtEdor(); //提交
}
function submitFormold()
{
  var i = 0;
 // var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 // showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");   
  PrtEdorold(); //提交
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
	var iHeight=350;     //弹出窗口的高度; 
	var iHeight=350;     //弹出窗口的高度; 
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
	var EdorState;	

	if(fm.ContNo.value == "" && fm.EdorNo.value == "" && fm.EdorAcceptNo.value == "")
	{
	    alert("在线打印前，请输入保单号，保全受理号或批单号！\n或者到保全批量打印页面进行打印！");
	    return;
	}
		
	// 初始化表格
	initPEdorMainGrid();
	var tReturn = parseManageComLimit();

	// 书写SQL语句
	var strSQL = "";
//	strSQL = " select distinct EdorConfNo,     "           
//								+" otherno,       "            
//								+" a.edorappname, "            
//								+" a.EdorAppDate, "            
//								+" a.modifytime,  "            
//								+" a.edoracceptno,"            
//								+" b.edorno       "            
//                +" from LPEdorApp a,lpedoritem b  "          
//                +" where a.edoracceptno=b.edoracceptno "   
//                +" and exists (select 'Y' "                 
//								+"	from lpedorprint c "      
//								+" where c.prttimes > 0 "     
//								+"	 and c.edorno = b.edorno) "
//								 +" and a.ManageCom LIKE '" + comcode.substring(1,6) + "%%'" //登陆机构权限控制
//	              +" and a.EdorState in ('0', '6') "   
//					      + getWherePart( 'a.otherno','ContNo' )
//				        + getWherePart( 'a.EdorConfNo','EdorNo' )
//				        + getWherePart('a.EdorAcceptNo','EdorAcceptNo')
//				        + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	              +" order by a.EdorAppDate,a.modifytime";
	
	var sqlid1 = "RePrtEndorseSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("bq.RePrtEndorseSql"); // 指定使用的properties文件名
	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
	mySql1.addSubPara(comcode.substring(1,6));// 指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);// 指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorNo'))[0].value);// 指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);// 指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);// 指定传入的参数
	strSQL = mySql1.getString();
	
//查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) 
	  {
	    alert("无此批单或该批单不在补打队列中！");
	  }
	  else
	  {
	  	//查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	  turnPage.pageDisplayGrid = PEdorMainGrid;    
	          
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
		var tSel = PEdorMainGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "请先选择一条记录，再查看" );
		else
		{
			if (PEdorMainGrid.getRowColData(tSel-1,1)==null||PEdorMainGrid.getRowColData(tSel-1,1)=='')
			{
				alert("请单击查询按钮!");
				return;
			}
			//alert(top.opener.document.all('EdorState').value);
			if (top.opener.document.all('EdorState').value =='2') 
			{
				arrReturn = getQueryResult();
				document.all('EdorNo').value = arrReturn[0][6];
				document.all('PolNo').value=arrReturn[0][1];
			
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
function PrtEdor(type)
{
	var arrReturn = new Array();
				
		var tSel = PEdorMainGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "请先选择一条记录，再点击查看按钮。" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorNo').value = arrReturn[6];
			document.all('ContNo').value=arrReturn[1];	
			/*parent.fraInterface.*/fm.action = "../f1print/EndorsementF1PJ1.jsp?type="+type;
			/*parent.fraInterface.*/fm.target="f1print";		 	
			// showSubmitFrame(mDebug);
			document.getElementById("fm").submit();
			/*parent.fraInterface.*/fm.action = "./PEdorQueryOut.jsp";
			/*parent.fraInterface.*/fm.target="fraSubmit";
		}
}
function PrtEdorold(type)
{
	var arrReturn = new Array();
				
		var tSel = PEdorMainGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "请先选择一条记录，再点击查看按钮。" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorNo').value = arrReturn[7];
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
				
		//var tSel = PEdorMainGrid.getSelNo();
	
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
	var tSel = PEdorMainGrid.getSelNo();
	
	
		
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
	tRow = PEdorMainGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	arrSelected = PEdorMainGrid.getRowData(tRow - 1);
//  alert(arrSelected);
/*	
	tRow = 10 * turnPage.pageIndex + tRow; //10代表multiline的行数
	//设置需要返回的数组
	arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//arrSelected[0] = arrGrid[tRow-1];
	//alert(arrSelected[0][0]);
*/	
	return arrSelected;
}

function print(){
	var selno = PEdorMainGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	//document.all('EdorAcceptNo').value = PEdorMainGrid.getRowColData(selno, 6);
	var EdorNo = PEdorMainGrid.getRowColData(selno, 7);
	var tType = "Endorsement";
	fm.action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + EdorNo+ "&type=" + tType;
  fm.target="f1print";
  document.getElementById("fm").submit();
}
//初始化管理机构，最长截取六位
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
	  //  var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(1,6)+"%%'";
	    var sqlid2 = "RePrtEndorseSql2";
		var mySql2 = new SqlClass();
		mySql2.setResourceName("bq.RePrtEndorseSql"); // 指定使用的properties文件名
		mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
		mySql2.addSubPara(comcode.substring(1,6));// 指定传入的参数
		var strSQL = mySql2.getString();
	    
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);  
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
        	for( i = 0;i < n; i++)
        	{
  	        	m = tTurnPage.arrDataCacheSet[i].length;
        		if (m > 0)
  		        {
  		        	for( j = 0; j< m; j++)
  			        {
  		 		        if (i == 0 && j == 0)
  			        	{
  				        	returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
  			        	}
  			        	if (i == 0 && j > 0)
  			        	{
  					        returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
  				        }
  			         	if (i > 0 && j == 0)
  				        {
  					        returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
  				        }
  			        	if (i > 0 && j > 0)
  				        {
  					        returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
  				        }
     		        }
  		        }
  	            else
  	            {
  		          	alert("查询失败!!");
  			        return "";
  		        }
             }
         }
         else
         {
	         alert("查询失败!");
	         return "";
         }
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
	}
	
}