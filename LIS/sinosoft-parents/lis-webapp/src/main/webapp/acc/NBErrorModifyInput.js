//               该文件中包含客户端需要处理的函数和事件
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();

var turnPage1 = new turnPageClass();


var turnPage2 = new turnPageClass();
var myCheckInsuAccNo = "";
var myCheckDate = "";
var sqlresourcename = "acc.NBErrorModifyInputSql";

//提交，保存按钮对应操作
function submitFrom()
{

  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  lockScreen('submit');
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   document.all('Transact').value ="INSERT";
   document.getElementById("fm").submit(); //提交 
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
{
//  alert(FlagStr);
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
  	//parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   

    //执行下一步操作
  }
  resetForm();
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
  	alert("toulian.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 


//提交前的校验、计算  
function beforeSubmit()
{
    return true;
}           

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  } else {
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
        
function afterQuery(arrQueryResult)
{
 

}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  unlockScreen('submit');
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
  	//parent.fraInterface.initForm();
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
//    
  }
  initForm();
}

function easyQueryClick()
{
	// 初始化表格
    initPolGrid();
    initOldPlan();
    initNewPlan();

	// 书写SQL语句
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("NBErrorModifyInputSql0");
	    mySql.addSubPara(fm.QureyPrtNo.value);
	    mySql.addSubPara(fm.QureyContNo.value);

	turnPage.queryModal(mySql.getString(), PolGrid);
	if(PolGrid.mulLineCount <= 0){
		alert("没有符合条件的数据！");
		return false;
	}
}


function getPolDetail(parm1,parm2)
{
	var selectRowNum = parm1.replace(/spanPolGrid/g,"");

	var PolNo = document.all('PolGrid1'+'r'+selectRowNum).value;
    var RiskCode = document.all('PolGrid2'+'r'+selectRowNum).value;
	
   // var PolNo=document.all(parm1).all('PolGrid1').value;
//    var RiskCode=document.all(parm1).all('PolGrid2').value;
    document.all('PolNo').value=PolNo;
    document.all('RiskCode').value=RiskCode;
    initOldPlan();
    initNewPlan();
    
    	// 书写SQL语句
	 var mySql1=new SqlClass();
	    mySql1.setResourceName(sqlresourcename);
	    mySql1.setSqlId("NBErrorModifyInputSql1");
	    mySql1.addSubPara(PolNo);
	   

	turnPage1.queryModal(mySql1.getString(), OldPlanGrid);
	if(OldPlanGrid.mulLineCount <= 0){
		alert("没有符合条件的数据！");
		return false;
	}
	
	 var mySql2=new SqlClass();
	    mySql2.setResourceName(sqlresourcename);
	    mySql2.setSqlId("NBErrorModifyInputSql2");
	    mySql2.addSubPara(PolNo);
	   

	turnPage2.queryModal(mySql2.getString(), NewPlanGrid);
	
}