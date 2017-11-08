/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-02-22
 * @direction: 保单状态查询主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();    //全局变量, 查询结果翻页, 必须有
var mySql = new SqlClass();
/*============================================================================*/

/**
 * 查询保单状态信息
 */
function queryContStateGrid()
{
    /*var QuerySQL = "select a.PolNo, "
                 +        "(select distinct RiskCode "
                 +           "from LMRisk "
                 +          "where 1 = 1 "
                 +            "and PolNo = a.PolNo "
                 +            "and RiskCode = "
                 +                "(select RiskCode "
                 +                   "from LCPol "
                 +                  "where PolNo = a.PolNo)) as RiskCode, "
                 +        "(select distinct RiskShortName "
                 +           "from LMRisk "
                 +          "where 1 = 1 "
                 +            "and PolNo = a.PolNo "
                 +            "and RiskCode = "
                 +                "(select RiskCode "
                 +                   "from LCPol "
                 +                  "where PolNo = a.PolNo)), "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'contstatetype' "
                 +            "and Code = a.StateType), "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and trim(CodeType) = lower('cont' || trim(a.StateType) || 'state') "
                 +            "and Code = a.State), "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and trim(CodeType) = lower('cont' || trim(a.StateType) || 'reason') "
                 +            "and Code = a.StateReason), "
                 +        "a.StartDate, "
                 +        "a.EndDate, "
                 +        "a.Remark "
                 +   "from LCContState a "
                 +  "where 1 = 1 "
                 +  getWherePart("a.ContNo", "ContNo")
                 +  "order by RiskCode, a.StateType, a.StartDate, a.MakeDate asc ";*/
    mySql = new SqlClass();
	mySql.setResourceName("sys.LCContQueryInputSql");
	mySql.setSqlId("LCContQuerySql1");
	mySql.addSubPara(fm.ContNo.value ); 
    try
    {
        turnPage.queryModal(mySql.getString(), ContStateGrid);
    }
    catch (ex)
    {
        alert("警告：查询批改项目信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 返回主界面
 */
function returnParent()
{
    top.close();
    try { top.opener.focus(); } catch (ex) {}
}

/*============================================================================*/


//<!-- JavaScript Document END -->
