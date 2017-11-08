//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var showInfo;
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var k = 0;

var tResourceName="operfee.IndiDueFeeCancelSql";
var tResourceSQL1="IndiDueFeeCancelSql1";
var tResourceSQL2="IndiDueFeeCancelSql2";
var tResourceSQL3="IndiDueFeeCancelSql3";
var tResourceSQL4="IndiDueFeeCancelSql4"; 

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	if(document.all('ContNo2').value==""&&document.all('PrtNo').value=="")
	{
	  alert("保单号和印刷号不能同时为空！");
      return ;
	}
	/*var strSQL = "select ContNo,ProposalContNo,AppntName,InsuredName from LCCont where 1=1"
				 + " and AppFlag='1'"	//承保
				 + " and conttype = '1'"	//1-个人总投保单,2-集体总单
				 //+ " and PayIntv>0"					 			 
				 //+ " and grpContno = '00000000000000000000'"
				 + " and exists (select 1 from ljspay where otherno=LCCont.ContNo)"
				 + getWherePart( 'ContNo','ContNo2' )
				 + getWherePart( 'PrtNo' )
				 + " and ManageCom like '" + ComCode + "%%'"
				 + " order by contno"*/
	
	//var strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.ContNo2.value,fm.PrtNo.value,ComCode]); 
	var sqlid="IndiDueFeeCancelSql1";
		var mySql31=new SqlClass();
		 var sql31 = "";
		// alert('1');
	if(document.all('ContNo2').value!=null&&document.all('ContNo2').value!='')
	{
		sqlid="IndiDueFeeCancelSql1";
		mySql31.setResourceName("operfee.IndiDueFeeCancelSql"); //指定使用的properties文件名
		mySql31.setSqlId(sqlid);//指定使用的Sql的id
		mySql31.addSubPara(document.all('ContNo2').value);//指定传入的参数
		mySql31.addSubPara(ComCode);//指定传入的参数
	    sql31=mySql31.getString();
	}
	else
	{
		sqlid="IndiDueFeeCancelSql2";
		mySql31.setResourceName("operfee.IndiDueFeeCancelSql"); //指定使用的properties文件名
		mySql31.setSqlId(sqlid);//指定使用的Sql的id
		mySql31.addSubPara(document.all('PrtNo').value);//指定传入的参数
		mySql31.addSubPara(ComCode);//指定传入的参数
	    sql31=mySql31.getString();
	}
	turnPage.strQueryResult  = easyQueryVer3(sql31, 1, 0, 1);  
  turnPage.queryModal(sql31,PolGrid);
  /*
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有符合条件的个人单！");
    return ;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;              
  //保存SQL语句
  turnPage.strQuerySql     = strSQL;   
  //设置查询起始位置
  turnPage.pageIndex       = 0;    
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  */
  return true;
}

function submitForm()
{
	if(beforeSubmit())
	{  
  		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    	document.getElementById("fm").submit();
    }	
}

//提交前的校验、计算  
function beforeSubmit()
{
	var tSelNo = PolGrid.getSelNo();
	if( tSelNo == 0 || tSelNo == null )
	{
		alert( "请先选择一条记录，再点击催收撤销按钮。" );
		return false;
	}else{
		fm.ContNo.value = PolGrid.getRowColData(tSelNo-1,1); 
	}
    return true;
}  

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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
    resetForm();
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
  	alert("在IndiDueFeeInputCancel.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false");
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

function PersonMulti()
{
	var StartDate=fmMulti.StartDate.value;
	var EndDate=fmMulti.EndDate.value;
	if(StartDate==null||StartDate==""||EndDate==null||EndDate=="")
	{
	  alert("必须录入起止日期");
	  return false;	
	}
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fmMulti").submit();	
}

function SpecPersonMulti()
{
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  fmMulti.spec.value = "1";
  
  document.getElementById("fmMulti").submit();	  
}

function easyQueryAddClick()
{
	var tSelNo = PolGrid.getSelNo()-1;
	fm.ContNo.value = PolGrid.getRowColData(tSelNo,1);	
}
