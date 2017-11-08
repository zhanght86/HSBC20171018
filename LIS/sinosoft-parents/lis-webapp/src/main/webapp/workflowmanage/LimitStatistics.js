/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */
//该文件中包含客户端需要处理的函数和事件

var mDebug = "0";
var mOperate = "";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var QueryResult = "";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere = "";
//window.onfocus=myonfocus;
var mySql = new SqlClass();
function checkBusiType()
{
    if (document.all('BusiType').value == "" || document.all('BusiType').value == null)
    {
        alert("请先选择一个业务类型")
        return false;
    }
}
function afterCodeSelect(cCodeName, Field)
{
    if (cCodeName == "busitype")
    {
        fm.ActivityID.value = "";
        fm.ActivityName.value = "";
    }
    if (cCodeName == "station")
    {
        fm.UserCode.value = "";
        fm.UserName.value = "";
    }
}

function easyQueryClick()
{
//    if (verifyInput2() == false)
//    {
//        return;
//    }
    
  mySql = new SqlClass();
  mySql.setResourceName("workflow.LimitStatisticsSql");
  mySql.setSqlId("LimitStatisticsSql1");
  mySql.addSubPara(fm.BusiType.value);   	
	mySql.addSubPara(fm.ActivityID.value);      
  mySql.addSubPara(fm.BusiNo.value);
    
    turnPage.queryModal(mySql.getString(), MonitorGrid);
}
