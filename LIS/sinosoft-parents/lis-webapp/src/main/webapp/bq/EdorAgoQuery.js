/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.01
 * @date     : 2005-12-14, 2006-08-19, 2006-10-26
 * @direction: ��ȫ�˹��˱�Ͷ(��)���˼�����ȫ��ѯ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();                //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageEdorGrid = new turnPageClass();        //ȫ�ֱ���, ������ȫ������Ϣ��ѯ�����ҳ
var turnPagePrintGrid = new turnPageClass();       //ȫ�ֱ���, ������ȫ�����˱�֪ͨ����Ϣ��ѯ�����ҳ
var turnPageEdorItemGrid = new turnPageClass();    //ȫ�ֱ���, ������ȫ������Ŀ��Ϣ��ѯ�����ҳ
var turnPagePremGrid = new turnPageClass();        //ȫ�ֱ���, ������ȫ������Ŀ�ӷ���Ϣ��ѯ�����ҳ
var turnPageSpecGrid = new turnPageClass();        //ȫ�ֱ���, ������ȫ������Ŀ�ر�Լ����Ϣ��ѯ�����ҳ

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
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
}

/*============================================================================*/

/**
 * �ͻ���Ϣ��ѯ
 */
function queryCustomerInfo()
{
    var sCustomerNo;
    try
    {
        sCustomerNo = document.getElementsByName("CustomerNo")[0].value;
    }
    catch (ex) {}
    if (sCustomerNo == null || trim(sCustomerNo) == "")
    {
        alert("�޷���ȡ�ͻ��š���ѯ�ͻ���Ϣʧ�ܣ� ");
        return;
    }
    else
    {
        var QuerySQL, arrResult;
		
		var sqlid1="EdorAgoQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.EdorAgoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(sCustomerNo);//ָ������Ĳ���
	    QuerySQL=mySql1.getString();	
		
//        QuerySQL = "select Name "
//                 +   "from LDPerson "
//                 +  "where CustomerNo = '" + sCustomerNo + "'";
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ�ͻ���Ϣ�����쳣�� ");
            return;
        }
        if (arrResult != null)
        {
            try
            {
                document.getElementsByName("CustomerName")[0].value = arrResult[0][0];
            }
            catch (ex) {}
        }
    }
}

/*============================================================================*/

/**
 * ������ȫ������Ϣ��ѯ
 */
function queryCustomerEdor()
{
    var sCustomerNo, sCurEdorAcceptNo;
    try
    {
        sCustomerNo = document.getElementsByName("CustomerNo")[0].value;
        sCurEdorAcceptNo = document.getElementsByName("CurEdorAcceptNo")[0].value;
    }
    catch (ex) {}
    if (sCustomerNo == null || trim(sCustomerNo) == "" || sCurEdorAcceptNo == null || trim(sCurEdorAcceptNo) == "")
    {
        alert("�޷���ȡ�ͻ��š���ȫ����š���ѯ�ͻ�������ȫ������Ϣʧ�ܣ� ");
        return;
    }
    else
    {
		
		var sqlid2="EdorAgoQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.EdorAgoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(sCurEdorAcceptNo);//ָ������Ĳ���
		mySql2.addSubPara(sCustomerNo);//ָ������Ĳ���
	    var QuerySQL=mySql2.getString();	
		
//        var QuerySQL = "select a.EdorNo, "
//                     +        "a.ContNo, "
//                     +        "(select AppntName "
//                     +           "from LCAppnt "
//                     +          "where ContNo = a.ContNo), "
//                     +        "a.EdorAppDate, "
//                     +        "a.EdorValiDate, "
//                     +        "(select a.uwState || '-' || trim(CodeName) "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'edorcontuwstate' "
//                     +            "and trim(Code) = trim(a.uwState)), "
//                     +        "(select a.EdorState || '-' || trim(CodeName) "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'edorstate' "
//                     +            "and trim(Code) = trim(a.EdorState)), "
//                     +        "a.EdorAcceptNo "
//                     +   "from LPEdorMain a "
//                     +  "where 1 = 1 "
//                     +    "and a.EdorAcceptNo <> '" + sCurEdorAcceptNo + "' "
//                     +    "and a.ContNo in "
//                     +        "(select ContNo "
//                     +           "from LCCont "
//                     +          "where InsuredNo = '" + sCustomerNo + "') "
//                     +  "order by a.EdorNo asc, a.EdorAppDate asc ";
        //alert(QuerySQL);
        try
        {
            turnPageEdorGrid.pageDivName = "divTurnPageEdorGrid";
            turnPageEdorGrid.queryModal(QuerySQL, EdorGrid);
        }
        catch (ex)
        {
            alert("���棺��ѯ������ȫ������Ϣ�����쳣�� ");
            return;
        }
        if (EdorGrid.mulLineCount == 0)
        {
            alert("�ͻ� " + sCustomerNo + " û�м�����ȫ������Ϣ�� ");
        }
    }
}

/*============================================================================*/

/**
 * ������ȫ�����˱�֪ͨ���ѯ��������Ŀ��Ϣ��ѯ
 */
function queryUWPrintEdorItem()
{
    queryEdorUWNotice();
    queryEdorItem();
}

/*============================================================================*/

/**
 * ������ȫ�����˱�֪ͨ���ѯ
 */
function queryEdorUWNotice()
{
    var nSelNo, sEdorNo;
    try
    {
        nSelNo = EdorGrid.getSelNo() - 1;
        sEdorNo = EdorGrid.getRowColData(nSelNo, 1);
    }
    catch (ex) {}
    if (sEdorNo == null || trim(sEdorNo) == "")
    {
        alert("�޷���ȡ�����š���ѯ������ȫ������Ŀ�˱�֪ͨ��ʧ�ܣ� ");
        return;
    }
    else
    {
		
		var sqlid3="EdorAgoQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("bq.EdorAgoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(sEdorNo);//ָ������Ĳ���
	    var QuerySQL=mySql3.getString();	
		
//        var QuerySQL = "select a.PrtSeq, "
//                     +        "a.OtherNo, "
//                     +        "trim(a.Code) || '-' || "
//                     +        "(select CodeName "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'bquwnotice' "
//                     +            "and Code = a.Code), "
//                     +        "a.ManageCom, "
//                     +        "(select Name "
//                     +           "from LDCom "
//                     +          "where ComCode = a.ManageCom), "
//                     +        "a.ReqOperator, "
//                     +        "a.MakeDate "
//                     +   "from LOPrtManager a "
//                     +  "where 1 = 1 "
//                     +    "and StandByFlag1 = '" + sEdorNo + "' "
//                     +    "and exists "
//                     +        "(select 'X' "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +           "and CodeType = 'bquwnotice' "
//                     +           "and Code = a.Code) "
//                     +  "order by a.PrtSeq asc";
        try
        {
            turnPagePrintGrid.pageDivName = "divTurnPagePrintGrid";
            turnPagePrintGrid.queryModal(QuerySQL, PrintGrid);
        }
        catch (ex)
        {
            alert("���棺��ѯ�ͻ�������ȫ������Ŀ��Ϣ�����쳣�� ");
            return;
        }
    } //sEdorNo != null
}

/*============================================================================*/

/**
 * ������ȫ������Ŀ��Ϣ��ѯ
 */
function queryEdorItem()
{
    var nSelNo, sEdorNo, sContNo;
    try
    {
        nSelNo = EdorGrid.getSelNo() - 1;
        sEdorNo = EdorGrid.getRowColData(nSelNo, 1);
        sContNo = EdorGrid.getRowColData(nSelNo, 2);
    }
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "" || sEdorNo == null || trim(sEdorNo) == "")
    {
        alert("�޷���ȡ��ͬ�š������š���ѯ�ͻ�������ȫ������Ŀ��Ϣʧ�ܣ� ");
        return;
    }
    else
    {
		
		var sqlid4="EdorAgoQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("bq.EdorAgoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(sContNo);//ָ������Ĳ���
		mySql4.addSubPara(sEdorNo);//ָ������Ĳ���
	    var QuerySQL=mySql4.getString();	
		
//        var QuerySQL = "select a.EdorNo, "
//                     +        "a.ContNo, "
//                     +        "(select distinct EdorCode || '-' || EdorName "
//                     +        "   from LMEdorItem "
//                     +        "  where EdorCode = a.EdorType), "
//                     +        "a.InsuredNo, "
//                     +        "a.PolNo, "
//                     +        "a.EdorAppDate, "
//                     +        "a.EdorValiDate, "
//                     +        "(select a.uwFlag || '-' || trim(CodeName) "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'edoruwstate' "
//                     +            "and trim(Code) = trim(a.uwFlag)), "
//                     +        "a.EdorState "
//                     +   "from LPEdorItem a "
//                     +  "where 1 = 1 "
//                     +    "and a.ContNo = '" + sContNo + "' "
//                     +    "and a.EdorNo = '" + sEdorNo + "' "
//                     +  "order by a.EdorNo asc, a.EdorAppDate asc";
        //alert(QuerySQL);
        try
        {
            turnPageEdorItemGrid.pageDivName = "divTurnPageEdorItemGrid";
            turnPageEdorItemGrid.queryModal(QuerySQL, EdorItemGrid);
        }
        catch (ex)
        {
            alert("���棺��ѯ�ͻ�������ȫ������Ŀ��Ϣ�����쳣�� ");
            return;
        }
        try
        {
            document.all("divPrintEdorItemLayer").style.display = "";
            document.all("divPremSpecLayer").style.display = "none";
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ȡѡ�е�������Ŀ��Ϣ�ı�ȫ��ϸ��Ҫ��Ϣ���ӷ���Ϣ���ر�Լ����Ϣ
 */
function queryEdorPremSpec()
{
    queryEdorItemDetail();
    queryEdorItemPrem();
    queryEdorItemSpec();
    try
    {
        document.all("divPremSpecLayer").style.display = "";
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * ��ȡѡ�е�������Ŀ��Ϣ�ı�Ҫ��Ϣ, ����ȫ��ϸ��ѯʹ��
 */
function queryEdorItemDetail()
{
    var nSelNo;
    try { nSelNo = EdorItemGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo >= 0)
    {
        var sEdorNo;
        try { sEdorNo = EdorItemGrid.getRowColData(nSelNo, 1); } catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
            return;
        else
        {
            var QuerySQL, arrResult;
			
				var sqlid5="EdorAgoQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("bq.EdorAgoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(sEdorNo);//ָ������Ĳ���
	    QuerySQL=mySql5.getString();	
			
//            QuerySQL = "select EdorAcceptNo, "
//                     +        "EdorNo, "
//                     +        "EdorType, "
//                     +        "ContNo, "
//                     +        "EdorAppDate, "
//                     +        "EdorValiDate "
//                     +  "from LPEdorItem "
//                     + "where EdorNo = '" + sEdorNo + "' "
//                     + "order by EdorAcceptNo asc, EdorNo asc";
            //alert(QuerySQL);
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("���棺��ѯ��ȫ������Ŀ��Ϣ�����쳣�� ");
                return;
            }
            if (arrResult != null)
            {
                try
                {
                    document.getElementsByName("EdorAcceptNo")[0].value = arrResult[0][0];
                    document.getElementsByName("EdorNo")[0].value = arrResult[0][1];
                    document.getElementsByName("EdorType")[0].value = arrResult[0][2];
                    document.getElementsByName("ContNo")[0].value = arrResult[0][3];
                    document.getElementsByName("EdorItemAppDate")[0].value = arrResult[0][4];
                    document.getElementsByName("EdorValiDate")[0].value = arrResult[0][5];
                }
                catch (ex) {}
            } //arrResult != null
        } //sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * ��ȡѡ�е�������Ŀ��Ϣ�ļӷ���Ϣ
 */
function queryEdorItemPrem()
{
    var nSelNo;
    try { nSelNo = EdorItemGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo >= 0)
    {
        var sEdorNo, sContNo, sEdorState;
        try
        {
            sEdorNo = EdorItemGrid.getRowColData(nSelNo, 1);
            sContNo = EdorItemGrid.getRowColData(nSelNo, 2);
            sEdorState = EdorItemGrid.getRowColData(nSelNo, 9);
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "" || sContNo == null || trim(sContNo) == "")
        {
            alert("�޷���ȡ�����š������š���ѯ�ӷ���Ϣʧ�ܣ� ");
            return;
        }
        else
        {
			
		var sqlid6="EdorAgoQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("bq.EdorAgoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	    var QuerySQL =mySql6.getString();	
			
//            var QuerySQL = "select PolNo, "
//                         +        "DutyCode, "
//                         +        "(case PayPlanType "
//                         +          "when '0' then "
//                         +           "'�����ӷ�' "
//                         +          "when '01' then "
//                         +           "'�����ӷ�' "
//                         +          "when '02' then "
//                         +           "'ְҵ�ӷ�' "
//                         +          "when '03' then "
//                         +           "'��Ч�����ӷ�' "
//                         +          "when '04' then "
//                         +           "'��Чְҵ�ӷ�' "
//                         +          "else "
//                         +           "'' "
//                         +        "end), "
//                         +        "SuppRiskScore, "
//                         +        "SecInsuAddPoint, "
//                         +        "(case AddFeeDirect "
//                         +          "when '01' then "
//                         +           "'Ͷ����' "
//                         +          "when '02' then "
//                         +           "'������' "
//                         +          "when '03' then "
//                         +           "'�౻������' "
//                         +          "when '04' then "
//                         +           "'�ڶ���������' "
//                         +          "else "
//                         +           "'' "
//                         +        "end), "
//                         +        "Prem, "
//                         +        "PayStartDate, "
//                         +        "PayToDate, "
//                         +        "PayEndDate ";
            //��������״̬ѡ��CP��
            if (sEdorState == "0" || sEdorState == "6")
            {
                QuerySQL +=  "from LCPrem "
                         +  "where 1 = 1 "
                         +    "and ContNo = '" + sContNo + "' ";
            }
            else
            {
                QuerySQL +=  "from LPPrem "
                         +  "where 1 = 1 "
                         +    "and EdorNo = '" + trim(sEdorNo) + "' ";
            }
            //ȥ����������
                QuerySQL +=   "and PayPlanType <> '0' "
                         +  "order by PolNo asc ";
            //alert(QuerySQL);
            try
            {
                turnPagePremGrid.pageDivName = "divTurnPagePremGrid";
                turnPagePremGrid.queryModal(QuerySQL, PremGrid);
            }
            catch (ex)
            {
                alert("���棺��ѯѡ�е�������Ŀ��Ϣ�ļӷ���Ϣ�����쳣�� ");
                return;
            }
        }
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * ��ȡѡ�е�������Ŀ��Ϣ���ر�Լ����Ϣ
 */
function queryEdorItemSpec()
{
    var nSelNo;
    try { nSelNo = EdorItemGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo >= 0)
    {
        var sEdorNo;
        try { sEdorNo = EdorItemGrid.getRowColData(nSelNo, 1); } catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
            return;
        else
        {
			
		var sqlid7="EdorAgoQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("bq.EdorAgoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(sEdorNo);//ָ������Ĳ���
		mySql7.addSubPara(sEdorNo);//ָ������Ĳ���
	    var QuerySQL =mySql7.getString();	
			
//            var QuerySQL = "select a.EdorNo, "
//                         +        "a.ContNo, "
//                         +        "a.PolNo, "
//                         +        "a.SpecContent, "
//                         +        "a.MakeDate "
//                         +   "from LPSpec a "
//                         +  "where 1 = 1 "
//                         +    "and a.EdorNo = '" + sEdorNo + "' "
//                         +    "and a.SerialNo = "
//                         +        "(select max(SerialNo) "
//                         +           "from LPSpec "
//                         +          "where EdorNo = '" + sEdorNo + "') "
//                         +  "order by a.EdorNo asc, a.MakeDate ";
            //alert(QuerySQL);
            try
            {
                turnPageSpecGrid.pageDivName = "divTurnPageSpecGrid";
                turnPageSpecGrid.queryModal(QuerySQL, SpecGrid);
            }
            catch (ex)
            {
                alert("���棺��ѯѡ�е�������Ŀ��Ϣ���ر�Լ����Ϣ�����쳣�� ");
                return;
            }
        } //sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * Ӱ�����ϲ�ѯ
 */
function showImage()
{
    var nSelNo;
    try { nSelNo = EdorGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo < 0)
    {
        alert("����ѡ��һ�������� ");
        return;
    }
    else
    {
        var sEdorAcceptNo;
        try
        {
            sEdorAcceptNo = EdorGrid.getRowColData(nSelNo, 8);
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("�޷���ȡ��ȫ����š���ѯӰ������ʧ�ܣ� ");
            return;
        }
        else
        {
           var tUrl="../bq/ImageQueryMain.jsp?BussNo="+sEdorAcceptNo+"&BussType=BQ";
           OpenWindowNew(tUrl,"��ȫɨ��Ӱ��","left");
//                var sPrtNo = arrResult[0][0];
//                QuerySQL = "select a.CodeAlias "
//                         +   "from LDCode a, es_doc_relation b "
//                         +  "where 1 = 1 "
//                         +    "and a.CodeType = 'bqscan' "
//                         +    "and a.Code = b.SubType "
//                         +    "and b.BussNo = '" + sEdorAcceptNo + "' "
//                         +    "and b.BussType = 'BQ'";
//                //alert(QuerySQL);
//                try
//                {
//                    arrResult = easyExecSql(QuerySQL, 1, 0);
//                }
//                catch (ex)
//                {
//                    alert("���棺��ѯ��ȫɨ����ҵ�����ͱ�������쳣�� ");
//                    return;
//                }
//                if (arrResult == null)
//                {
//                    alert("��ѯ��ȫɨ����ҵ�����ͱ���ʧ�ܣ� ");
//                    return;
//                }
//                else
//                {
//                    var sOpenWinURL = "EdorScan.jsp";
//                    var sParameters = "ScanFlag=1&prtNo=" + sPrtNo + "&EdorAcceptNo=" + sEdorAcceptNo + "&SubType=" + sSubType;
//                    OpenWindowNew(sOpenWinURL + "?" + sParameters, "������ȫӰ�����ϲ�ѯ", "left");
//                } //arrResult != null
        } //sEdorAcceptNo != null
    } //nSelNo > 0
}

/*============================================================================*/

/**
 * ��ȫ�˱��ջ��ѯ
 */
function EdorUWQuery()
{
    var nSelNo;
    try { nSelNo = EdorGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo < 0)
    {
        alert("����ѡ��һ�������� ");
        return;
    }
    else
    {
        var sEdorNo, sContNo;
        try
        {
            sEdorNo = EdorGrid.getRowColData(nSelNo, 1);
            sContNo = EdorGrid.getRowColData(nSelNo, 2);
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "" || sContNo == null || trim(sContNo) == "")
        {
            alert("�޷���ȡ��ͬ�š������š���ѯ��ȫ�˱��ջ�ʧ�ܣ� ");
            return;
        }
        else
        {
            var sOpenWinURL = "EdorUWQueryMain.jsp";
            var sParameters = "ContNo=" + sContNo + "&EdorNo=" + sEdorNo;
            OpenWindowNew(sOpenWinURL + "?" + sParameters, "������ȫ�˱��ջ��ѯ", "left");
        } //sContNo != null && sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * ������ȫ�����˱�֪ͨ���ӡ
 */
function printEdorUWNotice()
{
    var nSelNo;
    try { nSelNo = PrintGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo < 0)
    {
        alert("����ѡ��һ���˱�֪ͨ�飡 ");
        return;
    }
    else
    {
        var sPrtSeq, sManageCom, sOperator;
        try
        {
            sPrtSeq = PrintGrid.getRowColData(nSelNo, 1);
            sManageCom = PrintGrid.getRowColData(nSelNo, 4);
            sOperator = PrintGrid.getRowColData(nSelNo, 6);
        }
        catch (ex) {}
        if (sPrtSeq == null || trim(sPrtSeq) == "")
        {
            alert("�޷���ȡ��ӡ��ˮ�š���ӡ��ȫ�˱�֪ͨ��ʧ�ܣ� ");
            return;
        }
        else
        {
            var sOpenWinURL = "EdorAgoQueryUWNoticePrint.jsp";
            var sParameters = "PrtSeq=" + sPrtSeq + "&ManageCom=" + sManageCom + "&Operator=" + sOperator;
            OpenWindowNew(sOpenWinURL + "?" + sParameters, "������ȫ�˱�֪ͨ���ӡ", "left");
        } //sContNo != null && sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * ��ȫ��ϸ��ѯ
 */
function showEdorItemDetail()
{
    if (EdorItemGrid == null)
    {
        alert("����ѡ��һ�������� ");
        return;
    }
    else
    {
        var nSelNo;
        try
        {
            nSelNo = EdorItemGrid.getSelNo() - 1;
        }
        catch (ex) {}
        if (nSelNo < 0)
        {
            alert("����ѡ��һ��������Ŀ�� ");
            return;
        }
        else
        {
            var sEdorType, sEdorState;
            try
            {
                sEdorType = document.getElementsByName("EdorType")[0].value;
                sEdorState = EdorItemGrid.getRowColData(nSelNo, 9);
            }
            catch (ex) {}
            if (sEdorType == null || trim(sEdorType) == "")
            {
                alert("���棺�޷���ȡѡ��������Ŀ������������Ϣ�� ");
                return;
            }
            else
            {
                var sOpenWinURL;
                if (sEdorState != null && trim(sEdorState) == "0")
                {
                    sOpenWinURL = "../bqs/PEdorType" + sEdorType + ".jsp";
                }
                else
                {
                    sOpenWinURL = "../bq/PEdorType" + sEdorType + ".jsp";
                }
                OpenWindowNew(sOpenWinURL, "������ȫ��Ŀ��ϸ��ѯ", "left");
            } //sEdorType != null
        } //nSelNo > 0
    } //EdorItemGrid != null
}

/*============================================================================*/

/**
 *  ����������
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
