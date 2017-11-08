
var showInfo;  
var turnPage = new turnPageClass(); 
var turnPageMain = new turnPageClass(); 
var turnPagePgs = new turnPageClass(); 
var mySql = new SqlClass();

/**
 * 查询核心逻辑
 */
function SubTypeQuery()
{

    //查询核心SQL
    var strSQL = "";
    mySql = new SqlClass();
		mySql.setResourceName("sys.LDScanDef");
		mySql.setSqlId("LDScanDef1");
		mySql.addSubPara(fm.SubType1.value ); 
		strSQL = mySql.getString()
		turnPageMain.queryModal(strSQL, ScanGrid); 	
		var rowNum=ScanGrid.mulLineCount;
  	
		if(rowNum<1)
		{
			alert("没有查询到影像件类型");
		} 
}

function addSubType(){
  if (!document.all('SubType').value) {
	  alert('请填写影像件类型!');
	  document.all('SubType').focus;
	  return false;
  }
  if (!document.all('SubTypeName').value) {
	  alert('请填写影像件类型名称!');
	  document.all('SubTypeName').focus;
	  return false;
  }
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.oper.value="am";
  document.getElementById("fm").submit(); 
}

function defScanPosition(){
	if (ScanGrid.getSelNo()==0){
	  alert("请选择一个影像件类型!");
	  return;
	}
	var subType = ScanGrid.getRowColData(ScanGrid.getSelNo()-1,1);
	window.open("./ScanPositionDef.jsp?subType="+subType,subType , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    $('#divNewSubType').hide();
		SubTypeQuery();
  }
}