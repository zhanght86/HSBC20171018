/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-03-01
 * @direction: 补发打印查询主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();    //全局变量, 查询结果翻页, 必须有
var mySql = new SqlClass();
/*============================================================================*/

/**
 * 转换 null 或 "null" 为 ""
 */
function NullToEmpty(sParam)
{
    if (sParam == null || sParam == "null") sParam = "";
    return sParam;
}

/*============================================================================*/

/**
 * 查询补发打印信息
 */
function queryReissuePrintGrid()
{
    var sEdorAcceptNo, sContNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sContNo = document.getElementsByName("ContNo")[0].value;
    }
    catch (ex) { return; }
    if ((sEdorAcceptNo == null || trim(sEdorAcceptNo) == "") && (sContNo == null || trim(sContNo) == ""))
    {
        alert("警告：必须至少指定一个条件，保全受理号或合同号！ ");
        return;
    }
    /*var QuerySQL = "select distinct a.EdorAcceptNo, "
                 +        "a.EdorNo, "
                 +        "b.GetMoney, "
                 +        "b.MakeDate, "
                 +        "b.ApproveDate, "
                 +        "a.EdorValiDate, "
                 +        "c.MakeDate, "
                 +        "c.MakeTime, "
                 +        "c.Operator "
                 +   "from LPEdorItem a, LPEDorApp b, LDContinvoiceMap c "
                 +  "where 1 = 1 "
                 +  getWherePart("a.EdorAcceptNo", "EdorAcceptNo")
                 +  getWherePart("a.ContNo", "ContNo")
                 +    "and b.EdorAcceptNo = a.EdorAcceptNo "
                 +    "and c.ContNo = a.ContNo "
                 +    "and c.OperType = '4' "
                 +  "order by a.EdorAcceptNo asc, c.MakeDate asc";*/
   mySql = new SqlClass();
	mySql.setResourceName("sys.ReissuePrintQueryInputSql");
	mySql.setSqlId("ReissuePrintQuerySql1");
	mySql.addSubPara(fm.EdorAcceptNo.value ); 
	mySql.addSubPara(fm.ContNo.value ); 
    try
    {
        turnPage.queryModal(mySql.getString(), ReissuePrintGrid);
    }
    catch (ex)
    {
        alert("警告：查询补发打印信息出现异常！ ");
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
