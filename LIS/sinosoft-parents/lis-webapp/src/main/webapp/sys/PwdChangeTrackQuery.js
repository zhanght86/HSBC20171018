/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-03-20
 * @direction: ��ȫ���������޸Ĺ켣��ѯ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();                //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPagePwdTrackGrid = new turnPageClass();    //ȫ�ֱ���, �޸Ĺ켣��ѯ�����ҳ
var mySql = new SqlClass();
/*============================================================================*/

/**
 * ��ѯ����״̬��Ϣ
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
        alert("���棺��ѯ���������޸Ĺ켣��Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ����������
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
