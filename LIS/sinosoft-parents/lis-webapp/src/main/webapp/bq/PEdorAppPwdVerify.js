/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-11-06
 * @direction: ��ȫ�������������ĿУ�鱣���������ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                     //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();               //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageContPwdGrid = new turnPageClass();    //ȫ�ֱ���, �����켣��ѯ�����ҳ

/*============================================================================*/

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            //MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            verifyPwdSucc();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ������������������ʾ��Ҫ��������ı���
 */
function queryContPwdGrid()
{
    var sOtherNo, sOtherNoType, sEdorItemAppDate, sAppType;
    try
    {
        sOtherNo = document.getElementsByName("OtherNo")[0].value;
        sOtherNoType = document.getElementsByName("OtherNoType")[0].value;
        sEdorItemAppDate = document.getElementsByName("EdorItemAppDate")[0].value;
        sAppType = document.getElementsByName("AppType")[0].value;
    }
    catch (ex) {}
    if (sOtherNo == null || sOtherNo.trim() == "" || sOtherNoType == null || sOtherNoType.trim() == "" || sEdorItemAppDate == null || sEdorItemAppDate.trim() == "")
    {
        alert("�޷���ȡ��ȫ������Ϣ����ѯ����������Ϣʧ�ܣ� ");
        verifyPwdFail();
        return;
    }
//    var QuerySQL = "select a.ContNo, "
//                 +        "a.AppntNo, "
//                 +        "a.AppntName, "
//                 +        "a.InsuredNo, "
//                 +        "a.InsuredName, "
//                 +        "a.CValiDate, "
//                 +        "'' "
//                 +   "from LCCont a "
//                 +  "where 1 = 1 "
//                 +    "and a.Password is not null ";
    var QuerySQL = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
    
    if (sOtherNoType == "1")
    {
//        QuerySQL +=    "and ((a.AppFlag = '1' and not exists "
//                 +         "(select 'X' "
//                 +             "from LCContState "
//                 +            "where 1 = 1 "
//                 +              "and ContNo = a.ContNo "
//                 +              "and (PolNo = '000000' or "
//                 +                   "PolNo = (select PolNo "
//                 +                              "from LCPol "
//                 +                             "where 1 = 1 "
//                 +                               "and ContNo = a.ContNo "
//                 +                               "and PolNo = MainPolNo)) "
//                 +              "and StateType = 'Available' "
//                 +              "and State = '1' "
//                 +              "and (('" + sEdorItemAppDate + "' >= StartDate and '" + sEdorItemAppDate + "' <= EndDate) or "
//                 +                  "('" + sEdorItemAppDate + "' >= StartDate and EndDate is null)))) or "
//                 +        "(a.AppFlag = '4' and exists "
//                 +         "(select 'X' "
//                 +             "from LCContState "
//                 +            "where 1 = 1 "
//                 +              "and ContNo = a.ContNo "
//                 +              "and (PolNo = '000000' or "
//                 +                   "PolNo = (select PolNo "
//                 +                              "from LCPol "
//                 +                             "where 1 = 1 "
//                 +                               "and ContNo = a.ContNo "
//                 +                               "and PolNo = MainPolNo)) "
//                 +              "and StateType = 'Terminate' "
//                 +              "and State = '1' "
//                 +              "and (('" + sEdorItemAppDate + "' >= StartDate and '" + sEdorItemAppDate + "' <= EndDate) or "
//                 +                  "('" + sEdorItemAppDate + "' >= StartDate and EndDate is null)) "
//                 +              "and StateReason in ('01', '05', '06', '09')))) "
//                 +  "and a.ContNo in "
//                 +      "(select ContNo from LCAppnt where AppntNo = '" + sOtherNo + "' "
//                 +      "union "
//                 +      "select ContNo from LCInsured where InsuredNo = '" + sOtherNo + "') ";
        
        sql_id = "PEdorAppPwdVerifySql1";
        my_sql = new SqlClass();
        my_sql.setResourceName("bq.PEdorAppPwdVerifySql"); //ָ��ʹ�õ�properties�ļ���
        my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
        my_sql.addSubPara(sEdorItemAppDate);//ָ������Ĳ���
        my_sql.addSubPara(sEdorItemAppDate);//ָ������Ĳ���
        my_sql.addSubPara(sEdorItemAppDate);//ָ������Ĳ���
        my_sql.addSubPara(sEdorItemAppDate);//ָ������Ĳ���
        my_sql.addSubPara(sEdorItemAppDate);//ָ������Ĳ���
        my_sql.addSubPara(sEdorItemAppDate);//ָ������Ĳ���
        my_sql.addSubPara(sOtherNo);//ָ������Ĳ���
        my_sql.addSubPara(sOtherNo);//ָ������Ĳ���
    }
    else if (sOtherNoType == "3")
    {
//        QuerySQL += "and a.ContNo = '" + sOtherNo + "' ";
        sql_id = "PEdorAppPwdVerifySql2";
        my_sql = new SqlClass();
        my_sql.setResourceName("bq.PEdorAppPwdVerifySql"); //ָ��ʹ�õ�properties�ļ���
        my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
        my_sql.addSubPara(sOtherNo);//ָ������Ĳ���
    }
    var tQuerySQL = "";
    if (sAppType != null && sAppType != "6" && sAppType != "7")
    {
        var tQuerySQL = "and a.CustomGetPolDate <= '" + sEdorItemAppDate + "'";
       
    }
    //alert(QuerySQL);
    my_sql.addSubPara(tQuerySQL);//ָ������Ĳ���
    QuerySQL = my_sql.getString();
    
    try
    {
        turnPageContPwdGrid.pageLineNum = 100;
        turnPageContPwdGrid.pageDivName = "divTurnPageContPwdGrid";
        turnPageContPwdGrid.queryModal(QuerySQL, ContPwdGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��Ҫ��������ı�����Ϣ�����쳣�� ");
    }
}

/*============================================================================*/

/**
 * ������������ý���
 */
function focusContPwdGrid()
{
    if (ContPwdGrid.mulLineCount > 0)
    {
        try
        {
            ContPwdGrid.setFocus(0, 7);
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * У�鱣������¼����
 */
function verifyContPwd()
{
    if (!ContPwdGrid.checkValue2())
    {
        return;
    }
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms(0).action = "PEdorAppPwdVerifySave.jsp";
    document.forms(0).submit();
}

/*============================================================================*/

/**
 * У��ɹ�����������
 */
function verifyPwdSucc()
{
    try
    {
        top.returnValue = true;
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * У��ʧ�ܷ���������
 */
function verifyPwdFail()
{
    try
    {
        top.returnValue = false;
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/*============================================================================*/


//<!-- JavaScript Document END -->
