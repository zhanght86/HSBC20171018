/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2005-12-03, 2006-02-15
 * @direction: �������⸴Ч��ѯ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                          //ȫ�ֱ���, ��������ʾ����, ������
var turnPage = new turnPageClass();    //ȫ�ֱ���, ��ҳ������

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
			var iHeight=350;     //�������ڵĸ߶�; 
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
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
}

/*============================================================================*/

/**
 * �������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
 */
function clearEmptyCode(objCodeList, objCodeListName)
{
    var sCodeList = document.getElementsByName(objCodeList.name)[0].value;
    if (sCodeList == null || sCodeList == "")
    {
        try { document.getElementsByName(objCodeListName.name)[0].value = ""; } catch (ex) {}
    }
}

/*============================================================================*/


//ת����У�����ڸ�ʽ
function formatDate(ObjTextInput)
{
    var ObjTextValue;
    var sErrorMsg = "�Բ���, ����������ڸ�ʽ���� \n\n���ڸ�ʽ������ yyyymmdd �� yyyy-mm-dd ��ʽ��";
    try
    {
        ObjTextValue = document.getElementsByName(ObjTextInput.name)[0].value;
        ObjTextValue = ObjTextValue.replace(/^\s*|\s*$/g, "");
    }
    catch (ex)
    {
        return;
    }
    if (ObjTextValue.length == 0)
        return;
    else if (ObjTextValue.length == 8)
    {
        if (ObjTextValue.indexOf('-') == -1)
        {
            var nYear  = ObjTextValue.substring(0, 4);
            var nMonth = ObjTextValue.substring(4, 6);
            var nDay   = ObjTextValue.substring(6, 8);
            if (nYear == "0000" || nMonth == "00" || nDay == "00")
            {
                alert(sErrorMsg);
                document.getElementsByName(ObjTextInput.name)[0].value = "";
                return;
            }
            else
                document.getElementsByName(ObjTextInput.name)[0].value = nYear + "-" + nMonth + "-" + nDay;
        }
    } //ObjTextValue.length == 8
    else if (ObjTextValue.length == 10)
    {
        var nYear  = ObjTextValue.substring(0, 4);
        var nMonth = ObjTextValue.substring(5, 7);
        var nDay   = ObjTextValue.substring(8, 10);
        if (nYear == "0000" || nMonth == "00" || nDay == "00")
        {
            alert(sErrorMsg);
            document.getElementsByName(ObjTextInput.name)[0].value = "";
            return;
        }
    } //ObjTextValue.length == 10
    else
    {
        alert(sErrorMsg);
        document.getElementsByName(ObjTextInput.name)[0].value = "";
        return;
    } //ObjTextValue.length != 8 && ObjTextValue.length != 10
}

/*============================================================================*/

/**
 * ��ѯ���⸴Ч��ͬ��Ϣ�͹켣
 */
function queryRevalidateTrack()
{
    //��������У��
    if (!verifyInput2()) return;
    //��鸽������
    var sStartDate, sEndDate;
    try
    {
        sStartDate = document.getElementsByName("StartDate")[0].value;
        sEndDate = document.getElementsByName("EndDate")[0].value;
    }
    catch (ex) {}
    //ƴдSQL���
    //var QuerySQL = "select a.ContNo, "
    //            +        "a.RevalidateTimes, "
    //            +        "a.InvalidReason , "
    //            +        "trim(a.ManageCom) || '-' || "
    //            +        "(select Name from LDCom where ComCode = a.ManageCom), "
    //            +        "a.Remark, "
    //            +        "a.Operator, "
    //            +        "a.MakeDate, "
    //            +        "a.MakeTime "
    //            +   "from LPRevalidateTrack a "
    //            +  "where 1 = 1 "
    //            +  getWherePart("a.ContNo", "ContNo")
    //            +  getWherePart("a.ManageCom", "ManageCom", "like") + " "
    //            +  getWherePart("a.InvalidReason", "InvalidReason")
    //            +  getWherePart("a.Operator", "Operator");
    var QuerySQL = wrapSql('LPRevalidateTrack1',[fm.ContNo.value,fm.ManageCom.value,fm.InvalidReason.value,fm.Operator.value,trim(sStartDate),trim(sEndDate)]);
    
    //if (sStartDate != null && trim(sStartDate) != "")
    //    QuerySQL = QuerySQL + "and a.MakeDate >= '" + trim(sStartDate) + "' ";
    //if (sEndDate != null && trim(sEndDate) != "")
    //    QuerySQL = QuerySQL + "and a.MakeDate <= '" + trim(sEndDate) + "' ";
    //QuerySQL = QuerySQL + "order by a.ContNo, a.RevalidateTimes asc";
    try
    {
        turnPage.queryModal(QuerySQL, QueryGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��Ч��ͬ��Ϣ�����쳣�� ");
        return;
    }
    if (QueryGrid.mulLineCount == 0) alert("û�з��������ĸ�Ч��ͬ��¼�� ");
}

/*============================================================================*/

/**
 * ��ӡ��ѯ�������⸴Ч��ͬ��Ϣ�͹켣
 */
function printRevalidateTrack()
{
    //if (QueryGrid.mulLineCount == 0)
    //{
    //    alert("û����Ҫ��ӡ�ĸ�Ч��ͬ��¼�����Ȳ�ѯ�� ");
    //    return;
    //}
    document.forms(0).action = "LRNSpecialAvailableQueryPrint.jsp";
    document.forms(0).submit();
}

/*============================================================================*/

/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.LRNSpecialAvailableQueryInput");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}



//<!-- JavaScript Document END -->
