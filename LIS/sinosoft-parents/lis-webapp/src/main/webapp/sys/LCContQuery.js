/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-02-22
 * @direction: ����״̬��ѯ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();    //ȫ�ֱ���, ��ѯ�����ҳ, ������
var mySql = new SqlClass();
/*============================================================================*/

/**
 * ��ѯ����״̬��Ϣ
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
        alert("���棺��ѯ������Ŀ��Ϣ�����쳣�� ");
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
