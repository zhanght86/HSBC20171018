/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-03-20
 * @direction: 保全保单密码修改轨迹查询主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();                //全局变量, 查询结果翻页, 必须有
var turnPagePwdTrackGrid = new turnPageClass();    //全局变量, 修改轨迹查询结果翻页
var mySql = new SqlClass();
/*============================================================================*/

/**
 * 查询保单状态信息
 */
function queryPwdTrackGrid()
{
  /*  var QuerySQL = "select ContNo, "
                 +        "AppntNo, "
                 +        "AppntName, "
                 +        "InsuredNo, "
                 +        "InsuredName, "
                 +        "ModifyDate, "
                 +        "ModifyTime, "
                 +        "Operator "
                 +   "from LPCont "
                 +  "where 1 = 1 "
                 +    "and EdorType = 'PW' "
                 +     getWherePart("ContNo", "ContNo")
                 +  "order by EdorNo asc, ModifyDate asc, ModifyTime asc";*/
    //alert(QuerySQL);
    mySql = new SqlClass();
	mySql.setResourceName("sys.PwdChangeTrackQueryInputSql");
	mySql.setSqlId("PwdChangeTrackQuerySql1");
	mySql.addSubPara(fm.ContNo.value ); 
    try
    {
        turnPagePwdTrackGrid.pageDivName = "divTurnPagePwdTrackGrid";
        turnPagePwdTrackGrid.queryModal(mySql.getString(), PwdTrackGrid);
    }
    catch (ex)
    {
        alert("警告：查询保单密码修改轨迹信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/*============================================================================*/


//<!-- JavaScript Document END -->
