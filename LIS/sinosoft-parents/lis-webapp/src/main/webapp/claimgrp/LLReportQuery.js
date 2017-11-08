//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
        alert("在LLLdPersonQuery.js-->resetForm函数中发生异常:初始化界面错误!");
    }
} 

//取消按钮对应操作
function cancelForm()
{
  
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作  
}           

// 查询按钮
function easyQueryClick()
{
    // 初始化表格
    initLLReportGrid();
    var strSQL = "";
//    select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator from LLReport where
    strSQL = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,RptDate,MngCom,AccidentReason from LLReport where 1=1 "
           + getWherePart( 'RptNo' )
           + getWherePart( 'RptorName' )
           + getWherePart( 'RptorPhone' )
           + getWherePart( 'RptorAddress' )
           + getWherePart( 'Relation' )
           + getWherePart( 'RptMode' )
           + " order by RptNo";
//    alert(strSQL);
    turnPage.queryModal(strSQL,LLReportGrid);

}

//////选择页面上查询的字段对应于"select"中的位置
////function getSelArray()
////{
////    var arrSel = new Array();  
////    arrSel[0] = 0;
////    arrSel[1] = 1;
////    arrSel[2] = 2;
////    arrSel[3] = 3;
////    arrSel[4] = 4;
////    arrSel[5] = 5;
//////    arrSel[6] = 6;
////    return arrSel;  
////}
////
//////返回查询结果，填充表格
////function displayEasyResult( arrQueryResult )
////{
////    var i, j, m, n;
////    var arrSelected = new Array();
////    var arrResult = new Array();
////
////    if( arrQueryResult == null )
////        alert( "没有找到相关的数据!" );
////    else
////    {
////        // 初始化表格
////        initLLReportGrid();
////        LLReportGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
////        LLReportGrid.loadMulLine(LLReportGrid.arraySave);
////        arrGrid = arrQueryResult;
////        // 转换选出的数组
////        arrSelected = getSelArray();
////        arrResult = chooseArray( arrQueryResult, arrSelected );
////        // 显示查询结果
////        n = arrResult.length;
////        for( i = 0; i < n; i++ )
////        {
////            m = arrResult[i].length;
////            for( j = 0; j < m; j++ )
////            {
////                LLReportGrid.setRowColData( i, j+1, arrResult[i][j] );
////            }
////        }
////    //LLReportGrid.delBlankLine();
////    }
////}

////对应checkBox的多记录返回
//function returnParent()
//{
//  var len = LLReportGrid.mulLineCount-1;
//  var arr = new Array();
//
//  for ( i=0;i<len;i++)
//  {
//    if (LLReportGrid.getChkNo(i)==true)
//    {
//      
//      var alen = arr.length;
//      arr[arr.length] = new Array();
//      arr[alen][0] = LLReportGrid.getRowColData(i,1);
//      arr[alen][1] = LLReportGrid.getRowColData(i,2);    
//    }    
//  }
//  alert(arr);  
//  if ( arr.length>0)
//  {
//    top.opener.afterQueryLL(arr);
//    top.close();
//  }
//}

//对应RadioBox的单记录返回
function returnParent()
{
    var i = LLReportGrid.getSelNo();
    if (i != 0)
    {      
        i = i - 1;
        var arr = new Array();
        arr[0] = LLReportGrid.getRowColData(i,1);
        arr[1] = LLReportGrid.getRowColData(i,2);  
        arr[2] = LLReportGrid.getRowColData(i,3);  
        arr[3] = LLReportGrid.getRowColData(i,4);
        arr[4] = LLReportGrid.getRowColData(i,5);
        arr[5] = LLReportGrid.getRowColData(i,6);
        arr[6] = LLReportGrid.getRowColData(i,7);  
        arr[7] = LLReportGrid.getRowColData(i,8);
        arr[8] = LLReportGrid.getRowColData(i,9);
        arr[9] = LLReportGrid.getRowColData(i,10);
    }
    if (arr)
    {
       top.opener.afterQueryLL2(arr);
       top.close();
    }
}
//
////参数为出生年月,如1980-5-9 
//function getAge(birth)
//{
//    var now = new Date();
//    var nowYear = now.getFullYear();
//    var oneYear = birth.substring(0,4);
//    var age = nowYear - oneYear;
//    if (age <= 0)
//    {
//        age = 0
//    }
//    return age;
//}
