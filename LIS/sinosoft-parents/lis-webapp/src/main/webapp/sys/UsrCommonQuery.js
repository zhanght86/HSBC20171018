//程序名称：UsrCommonQuery.js
//程序功能：系统用户信息查询
//创建日期：2005-11-30
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
var tTurnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql=new SqlClass();
// 数据返回父窗口
function returnParent()
{
    var tSel = UsrGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击返回按钮。 " );
    else
    {
        try
        {

            top.opener.QueryUserInfo(UsrGrid.getRowColData(tSel-1, 1));
            top.close();
            top.focus;
        }
        catch (ex){}
    }
}

// 查询按钮
function easyQueryClick()
{
	  mySql=new SqlClass();

 mySql.setResourceName("sys.UsrCommonQuery");
 mySql.setSqlId("UsrCommonQuerySql1");
 mySql.addSubPara(fm.UsrCode.value);
 mySql.addSubPara(fm.UsrName.value);
 mySql.addSubPara(fm.ManageCom.value);
 mySql.addSubPara(fm.EdorPopedom.value);
 mySql.addSubPara(fm.GEdorPopedom.value);

turnPage.queryModal(mySql.getString(), UsrGrid); 

}

function initManageCom()
{
    if(managecom !=null && managecom != "")
    {
        var i,j,m,n;
        var returnstr;
        mySql=new SqlClass();

 mySql.setResourceName("sys.UsrCommonQuery");
 mySql.setSqlId("UsrCommonQuerySql2");
 mySql.addSubPara(managecom);
 
        
        tTurnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
            for( i = 0;i < n; i++)
            {
                m = tTurnPage.arrDataCacheSet[i].length;
                if (m > 0)
                {
                    for( j = 0; j< m; j++)
                    {
                        if (i == 0 && j == 0)
                        {
                            returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i == 0 && j > 0)
                        {
                            returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j == 0)
                        {
                            returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j > 0)
                        {
                            returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
                        }
                    }
                }
                else
                {
                    alert("查询失败!!");
                    return "";
                }
             }
         }
         else
         {
             alert("查询失败!");
             return "";
         }
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
    }

}
