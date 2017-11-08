//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var tDisplay;
var turnPage = new turnPageClass();
var turnPageP = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


// 保单查询
function initQueryGo()
{
//var  strsql="select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where appntno='"+customerNo+"'";
  var   mySql1=new SqlClass();
		mySql1.setResourceName("sys.PersonPolSql"); //指定使用的properties文件名
		mySql1.setSqlId("PersonPolSql1");//指定使用的Sql的id
		mySql1.addSubPara(customerNo);//指定传入的参数
   var  strsql= mySql1.getString();
  
   try{
   turnPageP.queryModal(strsql, PolGrid);
   }
   catch(ex){
   alert(ex);
   }
}


// 点击跳转保单详细页面
function clickQuery()
{
var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细按钮。" );
    else
    {
        var ContNo = PolGrid.getRowColData(tSel - 1,1);
        if (ContNo == "")
            return;
            window.open("PolDetailQueryMain.jsp?ContNo="+ContNo+"&IsCancelPolFlag=0");
    }
}