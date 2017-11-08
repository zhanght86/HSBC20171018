/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2005-11-03, 2005-12-03, 2006-02-08, 2006-11-14
 * @direction: 代收代付查询主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();             //全局变量, 查询结果翻页, 必须有
var turnPageProxyGrid = new turnPageClass();    //全局变量, 代收代付查询结果翻页
var mySql = new SqlClass();
/*============================================================================*/

/*
 * 查询代收代付纪录
 */
function queryProxyGrid()
{
    //查询变量声明
    var sOtherNo, sEdorAcceptNo, sEdorNo;
    try
    {
        sOtherNo = document.getElementsByName("OtherNo")[0].value;
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sEdorNo = document.getElementsByName("EdorNo")[0].value;
    }
    catch (ex)
    {
        alert("警告：无法获取保全受理号、批单号。查询代收代付纪录失败！ ");
        return;
    }
    //检查查询条件
    if (sOtherNo == "" && sEdorAcceptNo == "" && sEdorNo == "")
    {
        alert("请至少输入一个查询条件！ ");
        return;
    }
    //SQL查询语句
   /* var QuerySQL = "select b.EdorAcceptNo, "
               +        "b.EdorType, "
               +        "a.PayMoney, "
               +        "b.EdorValidate, "
               +        "a.BankCode, "    //select BankName from LDBank where BankCode = a.BankCode
               +        "a.AccNo, "
               +        "a.AccName, "
               +        "a.BankDealDate, "
               +        "decode(instr((select c.agentpaysuccflag from ldbank c where c.bankcode=a.bankcode  ) ,a.banksuccflag||';'), 0, '失败', '成功'), "
               +        "case  "
               +          "when instr((select c.agentpaysuccflag from ldbank c where c.bankcode=a.bankcode  ) ,a.banksuccflag||';')>0 then "
               +           "'' "
               +          "else "
               +           "(select CodeName "
               +              "from LDCode1 "
               +             "where CodeType = 'bankerror' "
               +               "and Code = a.BankCode "
               +               "and trim(Code1) = a.BankSuccFlag) "
               +        "end "
               +   "from LYReturnFromBankB a, LPEdorItem b "
               +  "where a.PolNo = b.EdorAcceptNo "
               +    "and a.NoType = '10' "
               +     getWherePart("b.ContNo", "OtherNo")
               +     getWherePart("b.EdorAcceptNo", "EdorAcceptNo")
               +     getWherePart("b.EdorNo", "EdorNo")
               +  "order by b.EdorAcceptNo asc, a.BankDealDate asc";*/
    //alert(QuerySQL);
   mySql = new SqlClass();
	mySql.setResourceName("sys.ProxyIncomePayQuerySql");
	mySql.setSqlId("ProxyIncomePayQuerySql1");
	mySql.addSubPara(fm.OtherNo.value ); 
	mySql.addSubPara(fm.EdorAcceptNo.value ); 
	mySql.addSubPara(fm.EdorNo.value ); 
    try
    {
        turnPageProxyGrid.pageDivName = "divTurnPageProxyGrid";
        turnPageProxyGrid.queryModal(mySql.getString(), ProxyGrid);
    }
    catch (ex)
    {
        alert("警告：查询代收代付信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 关闭本窗口, 返回父界面
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
