/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2005-11-03, 2005-12-03, 2006-02-08, 2006-11-14
 * @direction: ���մ�����ѯ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();             //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageProxyGrid = new turnPageClass();    //ȫ�ֱ���, ���մ�����ѯ�����ҳ
var mySql = new SqlClass();
/*============================================================================*/

/*
 * ��ѯ���մ�����¼
 */
function queryProxyGrid()
{
    //��ѯ��������
    var sOtherNo, sEdorAcceptNo, sEdorNo;
    try
    {
        sOtherNo = document.getElementsByName("OtherNo")[0].value;
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sEdorNo = document.getElementsByName("EdorNo")[0].value;
    }
    catch (ex)
    {
        alert("���棺�޷���ȡ��ȫ����š������š���ѯ���մ�����¼ʧ�ܣ� ");
        return;
    }
    //����ѯ����
    if (sOtherNo == "" && sEdorAcceptNo == "" && sEdorNo == "")
    {
        alert("����������һ����ѯ������ ");
        return;
    }
    //SQL��ѯ���
   /* var QuerySQL = "select b.EdorAcceptNo, "
               +        "b.EdorType, "
               +        "a.PayMoney, "
               +        "b.EdorValidate, "
               +        "a.BankCode, "    //select BankName from LDBank where BankCode = a.BankCode
               +        "a.AccNo, "
               +        "a.AccName, "
               +        "a.BankDealDate, "
               +        "decode(instr((select c.agentpaysuccflag from ldbank c where c.bankcode=a.bankcode  ) ,a.banksuccflag||';'), 0, 'ʧ��', '�ɹ�'), "
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
        alert("���棺��ѯ���մ�����Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * �رձ�����, ���ظ�����
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
