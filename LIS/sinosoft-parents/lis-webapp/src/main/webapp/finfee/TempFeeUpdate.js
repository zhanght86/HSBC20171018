//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var confirmFlag=false;

function CheckTempfeeno()
{
	
	//将查询表调整为ljtempfee zy 2008-10-16 10:32
	//var strSQL = "select count(*) from ljtempfee where otherno='"+trim(document.all('OtherNo').value)+"'";
    var strSQL = wrapSql1("LJTempFee1",[document.all('OtherNo').value]);
    var arrResult = easyExecSql(strSQL, 1, 0);
    if (arrResult != null)
    {
        try
        {
        	if (parseInt(arrResult[0][0]) > 1)
        	{
        		return false;	
        	}
            
        }
        catch (ex)
        {
            alert("查询保单险种信息失败！ ");
        }
    } //arrResult != null
	return true;

}


//提交，保存按钮对应操作
function submitForm()
{
	
	if(document.all('TempFeeNo').value==null||document.all('TempFeeNo').value=="")
	
	{
    if(!CheckTempfeeno())
    {
    	alert("此两张投保单有多个暂收据号，请输入一个暂收据号！");
      return false;	
    }
    
  }
	
  if((document.all('TempFeeNo').value==null||document.all('TempFeeNo').value=="")&&(document.all('OtherNo').value==null||document.all('OtherNo').value==""))
  {
    alert("请输入暂收据号或投保单号！");
    return false;	
  }
  
  if(beforeSubmit())
  {
  	document.all('Query').disabled=true;
   	var i = 0;
   	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
   	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name="提示";
   	var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();   
   	TempGrid.clearData("TempGrid");
   	TempClassToGrid.clearData("TempClassToGrid");
   	//fm.target="_blank";
   	fm.Opt.value="QUERY";
   	document.getElementById("fm").submit(); //提交
	showInfo.close();
   }
}
function submitForm1()
{

	document.all('Update').disabled=true;
 	var i = 0;
   	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
   	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name="提示";
   	var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();   
    for(var c=0;c<TempClassToGrid.mulLineCount;c++){
    	if(TempClassToGrid.getRowColData(c,2)=="2" || TempClassToGrid.getRowColData(c,2)=="3" || TempClassToGrid.getRowColData(c,2)=="4"){
    		if(TempClassToGrid.getRowColData(c,7).length>0 && TempClassToGrid.getRowColData(c,8).length>0){
    			if(!checkBankAccNo(TempClassToGrid.getRowColData(c,7),TempClassToGrid.getRowColData(c,8))){
    				return;
    			}
    		}else{
    			if(TempClassToGrid.getRowColData(c,2)=="4"){//收费方式为银行转账时银行编码与银行账号为必录项
    				alert("请录入银行编码和银行账号");
    				return;		
    			}
    		}
    	}
    }
	 // document.getElementById("fm").submit(); //提交
   fm.Opt.value="SAVE";
   document.getElementById("fm").submit(); //提交
  
}

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
    //if(!verifyInput()) return false;
    return true;
}           

function returnParent()
{
    top.close();

}

//显示数据的函数，和easyQuery及MulLine 一起使用
function showRecord(strRecord)
{

  //保存查询结果字符串
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray = new Array(0,2,6,7,4,3,38);

  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = TempGrid;             
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}
function showRecord1(strRecord)
{

  //保存查询结果字符串
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray = new Array(0,1,3,5,2,8,13,14,15,28,29,32,33);

  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = TempClassToGrid;             
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}

function clearFormData()
{  
  initTempClassGrid();
  initTempGrid();
  initInpBox();
  Frame1.style.display="none";
  Frame2.style.display="none";               
  TempGrid.lock();
}

function clearshowInfo()
{  
  
  showInfo.close();
}


/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql1(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("finfee.TempFeeUpdateInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}
