//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 

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
  if(cDebug=="1") {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else {
    parent.fraMain.rows = "0,0,82,82,*";
  }
}

// 查询按钮
function easyQueryClick()
{          
   // 初始化表格
   
   initEdorPrintGrid();
   	
   // 书写SQL语句  先查找个单
   var strSQL = "";
   var strSQL1 = "";	
//   strSQL = "select LPEdorItem.EdorAppNo,LPEdorItem.EdorNo,LPEdorItem.ContNo,LPEdorItem.EdorType from LPEdorItem  where  LPEdorItem.EdorNo in (select EdorNo from lpedorprint) "			 
//				 + getWherePart( 'LPEdorItem.EdorNo','EdorNo' )
//				 + getWherePart( 'LPEdorItem.ContNo','ContNo' )
//				 + getWherePart( 'LPEdorItem.EdorAppNo','EdorAppNo' )
//                 + getWherePart( 'LPEdorItem.EdorType','EdorType' )
//				 +" union all  ";
   
    var  EdorNo1 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  EdorAppNo1 = window.document.getElementsByName(trim("EdorAppNo"))[0].value;
	var  EdorType1 = window.document.getElementsByName(trim("EdorType"))[0].value;
	 var sqlid1="ChangeEdorPrintSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("bq.ChangeEdorPrintSql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(EdorNo1);//指定传入参数
	 mySql1.addSubPara(ContNo1);//指定传入参数
	 mySql1.addSubPara(EdorAppNo1);//指定传入参数
	 mySql1.addSubPara(EdorType1);//指定传入参数
	 strSQL = mySql1.getString();
   
				 
//   strSQL1 = "select LPGrpEdorItem.EdorAppNo,LPGrpEdorItem.EdorNo,LPGrpEdorItem.GrpContNo,LPGrpEdorItem.EdorType from LPGrpEdorItem  where  LPGrpEdorItem.EdorNo in (select EdorNo from lpedorprint)  "			 
//				 + getWherePart( 'LPGrpEdorItem.EdorNo','EdorNo' )
//				 + getWherePart( 'LPGrpEdorItem.GrpContNo','ContNo' )
//				 + getWherePart( 'LPGrpEdorItem.EdorAppNo','EdorAppNo' )
//				 + getWherePart( 'LPGrpEdorItem.EdorType','EdorType' )
//				 + " ";
	 
    var  EdorNo2 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  EdorAppNo2 = window.document.getElementsByName(trim("EdorAppNo"))[0].value;
	var  EdorType2 = window.document.getElementsByName(trim("EdorType"))[0].value;
	 var sqlid2="ChangeEdorPrintSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("bq.ChangeEdorPrintSql");
	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
	 mySql2.addSubPara(EdorNo2);//指定传入参数
	 mySql2.addSubPara(ContNo2);//指定传入参数
	 mySql2.addSubPara(EdorAppNo2);//指定传入参数
	 mySql2.addSubPara(EdorType2);//指定传入参数
	 strSQL1 = mySql2.getString();			 
   
    strSQL = strSQL + strSQL1;
        
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
    
    if (!turnPage.strQueryResult) {
            return false;	
    }
  

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = EdorPrintGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

function getQueryResult()
{
  var arrSelected = null;
  tRow = EdorPrintGrid.getSelNo();
  if( tRow == 0 || tRow == null || arrGrid == null )
      return arrSelected;
  arrSelected = new Array();
  //设置需要返回的数组
  arrSelected[0] = EdorPrintGrid.getRowData(tRow-1);
	
  return arrSelected;
}


function getbqPrintToXML()
{
    var arrReturn = new Array();
    //var tSel = EdorPrintGrid.getSelNo();
    if( document.all('EdorNo1').value == "" || document.all('FileName').value =="" )
        alert( "请批单号码和输入文件名，再点击导出数据按钮。" );
    else
    {
        //arrReturn = getQueryResult();
      	//document.all('PolNo').value = EdorPrintGrid.getRowColData(tSel-1,1);
      	//document.all('EdorAppNo').value=EdorPrintGrid.getRowColData(tSel-1,2);	
              //document.all('EdorNo').value = EdorPrintGrid.getRowColData(tSel-1,3);
      	document.all("Transact").value = "PrintToXML"
			
        var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.submit();
    }
}

function setXMLTobqPrint()
{
    var arrReturn = new Array();
    //var tSel = EdorPrintGrid.getSelNo();
    if( document.all('FileName').value == "" || document.all('EdorNo1').value =="" )
        alert( "请输入文件名和批单号码，再点击导入数据按钮。" );
    else
    {
      document.all("Transact").value = "XMLToPrint"
		
      var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
      fm.submit();
    }
}

function afterSubmit( FlagStr, content, result )
{
  showInfo.close();
  
  if (result!="") {
    result = Conversion(result);
    fm.EdorXml.value = result;
  }
  else {
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
    }
    
    fm.EdorXml.value = "";
  }

}

//单击MultiLine的单选按钮时操作
function reportDetailClick(parm1, parm2) {	
  document.all('EdorNo1').value = EdorPrintGrid.getRowColData(EdorPrintGrid.getSelNo()-1, 2);
  document.all("Transact").value = "DisplayToXML"
  
  //showSubmitFrame("0");
  var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit();
}

function setXMLTobqPrint2()
{
    var arrReturn = new Array();
    if( document.all('EdorXml').value == "" || document.all('EdorNo1').value =="" )
        alert( "请输入文件名和批单号码，再点击导入数据按钮。" );
    else
    {
      document.all("Transact").value = "DisplayXMLToPrint"
		
      var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
      fm.submit();
    }
}