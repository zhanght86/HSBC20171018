//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
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
		mySql1.setResourceName("sys.GetQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("GetQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
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
