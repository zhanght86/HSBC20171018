/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-03-01
 * @direction: ������ӡ��ѯ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();    //ȫ�ֱ���, ��ѯ�����ҳ, ������
var mySql = new SqlClass();
/*============================================================================*/

/**
 * ת�� null �� "null" Ϊ ""
 */
function NullToEmpty(sParam)
{
    if (sParam == null || sParam == "null") sParam = "";
    return sParam;
}

/*============================================================================*/

/**
 * ��ѯ������ӡ��Ϣ
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
        alert("���棺��������ָ��һ����������ȫ����Ż��ͬ�ţ� ");
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
        alert("���棺��ѯ������ӡ��Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ����������
 */
function returnParent()
{
    top.close();
    try { top.opener.focus(); } catch (ex) {}
}

/*============================================================================*/


//<!-- JavaScript Document END -->
