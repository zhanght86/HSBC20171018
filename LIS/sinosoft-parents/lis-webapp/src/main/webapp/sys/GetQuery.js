//               该文件中包含客户端需要处理的函数和事件
var turnPageGetGrid = new turnPageClass();
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var tDisplay;
var turnPage = new turnPageClass();

var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

 
function initQueryGo()
{
    //var strsql="select AccName,SumGetMoney,Drawer,DrawerID,BankCode,BankAccNo from ljaget where AppntNo='"+customerNo+"'";
    var mySql1=new SqlClass();
		mySql1.setResourceName("sys.GetQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId("GetQuerySql1");//指定使用的Sql的id
		mySql1.addSubPara(customerNo);//指定传入的参数
	var strsql= mySql1.getString();
    
    try
    {
        //turnPageCustomerGrid.pageDivName = "divTurnPageCustomerGrid";
        turnPageGetGrid.queryModal(strsql, GetGrid);
        
    }
    catch (ex) {
    alert(ex);
    }
}
