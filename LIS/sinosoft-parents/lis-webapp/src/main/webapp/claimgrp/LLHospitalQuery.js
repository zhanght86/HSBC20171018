//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage=new turnPageClass();
var mySql = new SqlClass();
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  initHospitalGrid();
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
//    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//	  var name='提示';   //网页名称，可为空; 
//	  var iWidth=550;      //弹出窗口的宽度; 
//	  var iHeight=250;     //弹出窗口的高度; 
//	  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
//	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
//	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//
//	  showInfo.focus();

	  //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}


// 查询按钮
function easyQueryClick()
{
   // 初始化表格
   initHospitalGrid();
   
   // 书写SQL语句
   /*var strSQL = "";
   strSQL = "select * from LDPerson where 1=1 "
             + getWherePart( 'CustomerNo' )
             + getWherePart( 'Name' )
             + getWherePart( 'Birthday' )
             + getWherePart( 'IDType' )
             + getWherePart( 'IDNo' );*/
//alert(strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLHospitalQuerySql");
	mySql.setSqlId("LLHospitalQuerySql1");
	mySql.addSubPara(fm.CustomerNo.value ); 
	mySql.addSubPara(fm.Name.value ); 
	mySql.addSubPara(fm.Birthday.value ); 
	mySql.addSubPara(fm.IDType.value ); 
	mySql.addSubPara(fm.IDNo.value ); 
   execEasyQuery( mySql.getString() );
}

//选择页面上查询的字段对应于"select *"中的位置
function getSelArray()
{
   var arrSel = new Array();
   
   arrSel[0] = 0;
   arrSel[1] = 2;
   arrSel[2] = 3;
   arrSel[3] = 4;
   arrSel[4] = 16;
   arrSel[5] = 18;

   return arrSel;
}

function displayEasyResult( arrQueryResult )
{
   var i, j, m, n;
   var arrSelected = new Array();
   var arrResult = new Array();

   if( arrQueryResult == null )
      alert( "没有找到相关的数据!" );
   else
   {
      // 初始化表格
      initHospitalGrid();
      HospitalGrid.recordNo = (currPageIndex - 1) * MAXSCREENLINES;
      HospitalGrid.loadMulLine(HospitalGrid.arraySave);

      arrGrid = arrQueryResult;
      // 转换选出的数组
      arrSelected = getSelArray();
      arrResult = chooseArray( arrQueryResult, arrSelected );
      // 显示查询结果
      n = arrResult.length;
      for( i = 0; i < n; i++ )
      {
         m = arrResult[i].length;
         for( j = 0; j < m; j++ )
         {
            HospitalGrid.setRowColData( i, j+1, arrResult[i][j] );
         } // end of for
      } // end of for
      //alert("result:"+arrResult);
      
      //HospitalGrid.delBlankLine();
   } // end of if
}

// 数据返回父窗口
function returnParent()
{
   var arrReturn = new Array();
   var tSel = HospitalGrid.getSelNo();
   
   if( tSel == 0 || tSel == null )
      alert( "请先选择一条记录，再点击返回按钮。" );
   else
   {
      try
      {
         arrReturn = getQueryResult();
         top.opener.afterLLRegister2( arrReturn );
      }
      catch(ex)
      {
         alert( "没有发现父窗口的afterQuery接口。" + ex );
      }
      top.close();
   }
}

function getQueryResult()
{
   var arrSelected = null;
   tRow = HospitalGrid.getSelNo();
   //alert("111" + tRow);
   //edit by guo xiang at 2004-9-13 17:54
   //if( tRow == 0 || tRow == null || arrDataSet == null )
   if( tRow == 0 || tRow == null )
       return arrSelected;
   
   arrSelected = new Array();
   
   //设置需要返回的数组
   //edit by guo xiang at 2004-9-13 17:54
   arrSelected[0] = new Array();
   arrSelected[0] = HospitalGrid.getRowData(tRow-1);
   //arrSelected[0] = arrDataSet[tRow-1];
   
   return arrSelected;
}
function afterQuery()
{

  // var strSQL = " select HospitalCode,HospitalName from LLCommendHospital where HospitalName like '%%"+ fm.HospitalName.value +"%%'";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLHospitalQuerySql");
	mySql.setSqlId("LLHospitalQuerySql2");
	mySql.addSubPara(fm.HospitalName.value );  
   turnPage.queryModal(mySql.getString(), HospitalGrid,1,1);
}
