/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04, 1.05
 * @date     : 2005-12-02, 2006-02-10, 2006-02-22, 2006-05-12, 2006-06-06, 2006-11-14
 * @direction: �ۺϲ�ѯ������ȡ��ѯ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                       //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();                 //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageLiveGetGrid = new turnPageClass();      //ȫ�ֱ���, ������ȡ���в�ѯ�����ҳ
var turnPageBankProxyGrid = new turnPageClass();    //ȫ�ֱ���, ���մ������в�ѯ�����ҳ
var mySql = new SqlClass();
/*============================================================================*/

/**
 * ��ѯ�ͻ������ʻ�����ȡ��ʽ��Ϣ
 */
function queryBankInfo()
{
    var QuerySQL, arrResult;
   /* QuerySQL = "select a.AppntName, "
             +        "a.InsuredName, "
             +        "a.GetForm, "
             +        "(select CodeName "
             +           "from LDCode "
             +          "where 1 = 1 "
             +            "and CodeType = 'getlocation' "
             +            "and Code = a.GetForm), "
             +        "a.GetBankCode, "
             +        "(select BankName "
             +           "from LDBank "
             +          "where BankCode = a.GetBankCode), "
             +        "a.GetBankAccNo, "
             +        "a.GetAccName "
             +   "from LCPol a "
             +  "where 1 = 1 "
             +     getWherePart("a.ContNo", "ContNo")
             +     getWherePart("a.PolNo", "PolNo")
             +    "and a.PolNo = a.MainPolNo";*/
    //alert(QuerySQL);
    mySql = new SqlClass();
	mySql.setResourceName("sys.LiveGetQueryInputSql");
	mySql.setSqlId("LiveGetQuerySql1");
	mySql.addSubPara(fm.ContNo.value ); 
	mySql.addSubPara(fm.PolNo.value ); 
    try
    {
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ͻ������ʻ�����ȡ��ʽ��Ϣ�����쳣�� ")
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("AppntName")[0].value = arrResult[0][0];
            document.getElementsByName("InsuredName")[0].value = arrResult[0][1];
            document.getElementsByName("GetForm")[0].value = arrResult[0][2];
            document.getElementsByName("GetFormName")[0].value = arrResult[0][3];
            document.getElementsByName("GetBank")[0].value = arrResult[0][4];
            document.getElementsByName("GetBankName")[0].value = arrResult[0][5];
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][6];
            document.getElementsByName("GetAccName")[0].value = arrResult[0][7];
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯ�ͻ�������ȡ��¼
 */
function queryLiveGetGrid()
{
    var QuerySQL, arrResult;
    /*QuerySQL = "select * "
             +   "from ( "
             +         "select a.ActuGetNo, "
             +                "a.DutyCode, "
             +                "a.GetDutyCode, "
             +                "(select GetDutyName "
             +                   "from LMDutyGet "
             +                  "where GetDutyCode = a.GetDutyCode), "
             +                "a.GetMoney, "
             +                "a.GetDate as GetDate, "
             +                "a.MakeDate, "
             +                "a.EnterAccDate, "
             +                "a.CurGetToDate, "
             +                "(decode(a.RReportFlag, '0', '��', '1', '��', '��')) "
             +          "from LJAGetDraw a "
             +         "where 1 = 1 "
             +            getWherePart("a.ContNo", "ContNo")
             +            getWherePart("a.PolNo", "PolNo")
             +         "union "
             +        "select '', "
             +                "b.DutyCode, "
             +                "b.GetDutyCode, "
             +                "(select GetDutyName "
             +                   "from LMDutyGet "
             +                  "where GetDutyCode = b.GetDutyCode), "
             +                "b.GetMoney, "
             +                "b.GetDate as GetDate, "
             +                "b.ConfDate, "
             +                "b.EnterAccDate, "
             +                "b.CurGetToDate, "
             +                "(decode(b.RReportFlag, '0', '��', '1', '��', '��')) "
             +          "from LJSGetDraw b "
             +         "where 1 = 1 "
             +            getWherePart("b.ContNo", "ContNo")
             +            getWherePart("b.PolNo", "PolNo")
             +         ") "
             +  "order by GetDate desc";*/
    //alert(QuerySQL);
    mySql = new SqlClass();
	mySql.setResourceName("sys.LiveGetQueryInputSql");
	mySql.setSqlId("LiveGetQuerySql2");
	mySql.addSubPara(fm.ContNo.value ); 
	mySql.addSubPara(fm.PolNo.value ); 
	mySql.addSubPara(fm.ContNo.value ); 
	mySql.addSubPara(fm.PolNo.value ); 
    try
    {
        turnPageLiveGetGrid.pageDivName = "divTurnPageLiveGetGrid";
        turnPageLiveGetGrid.queryModal(mySql.getString(), LiveGetGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ͻ�������ȡ��¼��Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ��ѯ��ȡ���մ�����¼
 */
function queryBankProxyGrid()
{
    var nSelNo;
    try
    {
        nSelNo = LiveGetGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var sActuGetNo, sGetDutyCode;
        try
        {
            sActuGetNo = LiveGetGrid.getRowColData(nSelNo, 1);
            sGetDutyCode = LiveGetGrid.getRowColData(nSelNo, 3);
        }
        catch (ex) {}
        if (sActuGetNo == null || trim(sActuGetNo) == "")
        {
            try
            {
                BankProxyGrid.clearData();
            }
            catch (ex) {}
        }
        else
        {
           /* var QuerySQL = "select b.GetNoticeNo, "
                         +        "a.BankCode, "    //a.BankName or select BankName from LDBank where BankCode = a.BankCode
                         +        "a.AccNo, "
                         +        "a.AccName, "
                         +        "a.BankDealDate, "
                         +        "decode(trim(a.BankSuccFlag), '0000', '�ɹ�', 'ʧ��'), "
                         +        "case trim(a.BankSuccFlag) "
                         +          "when '0000' then "
                         +           "'' "
                         +          "else "
                         +           "(select CodeName "
                         +              "from LDCode1 "
                         +             "where CodeType = 'bankerror' "
                         +               "and Code = a.BankCode "
                         +               "and trim(Code1) = a.BankSuccFlag) "
                         +        "end "
                         +   "from LYReturnFromBankB a, LJAGetDraw b "
                         +  "where 1 = 1 "
                         +    "and a.PayCode = b.ActuGetNo "
                         +    "and b.ActuGetNo = '" + sActuGetNo + "' "
                         +    "and b.GetDutyCode = '" + sGetDutyCode + "' "
                         +  "order by a.BankDealDate asc";*/
            //alert(QuerySQL);
           mySql = new SqlClass();
			mySql.setResourceName("sys.LiveGetQueryInputSql");
			mySql.setSqlId("LiveGetQuerySql3");
			mySql.addSubPara(sActuGetNo); 
			mySql.addSubPara(sGetDutyCode);  
            try
            {
                turnPageBankProxyGrid.pageDivName = "divTurnPageBankProxyGrid";
                turnPageBankProxyGrid.queryModal(mySql.getString(), BankProxyGrid);
            }
            catch (ex)
            {
                alert("���棺��ѯ��ȡ���մ�����Ϣ�����쳣�� ");
                return;
            }
        }
    } //nSelNo >= 0
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
