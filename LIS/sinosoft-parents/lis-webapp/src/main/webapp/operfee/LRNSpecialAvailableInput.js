/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2005-11-29, 2005-12-03, 2006-02-15, 2006-02-28, 2006-03-23
 * @direction: �������⸴Ч¼�����ű�
/*============================================================================*/

var showInfo;                          //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();    //ȫ�ֱ���, ��ѯ�����ҳ, ������
var isThisSaved = false;               //ȫ�ֱ���, ����Ƿ��ѳɹ������

/*============================================================================*/
/**
 * ��ѯʧЧ��ͬ��Ϣ�����ֶ���
 */
function getAvailableInfoGrid()
{
    try
    {
        queryAvailableInfo();
        queryAvailableGrid();
    }
    catch (ex) {}
    if (AvailableGrid.mulLineCount > 0) //�����ѯ�����Ϊ��,������һ�α���
    {
        isThisSaved = false;
    }
}
/*============================================================================*/
/**
 * ��ѯʧЧ��ͬ��Ϣ
 */
function queryAvailableInfo()
{
    var sContNo = document.getElementsByName("ContNo_srh")[0].value; 
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("��������Ҫ�������⸴Ч�ĺ�ͬ���Բ�ѯ�� ");
        return;
    }
    var QuerySQL, arrResult;
    //QuerySQL = "select  a.ContNo, a.PayToDate x, "
    //		 + " (select  StartDate from LCContState where PolNo = a.PolNo "
    //         + " and StateType ='Available' and State = '1' and EndDate is null and contno='"+sContNo+"' and statereason in ('01','02','03','04')), "
    //         + " (select  max(EnterAccDate) from LJAPayPerson  where ContNo = a.ContNo and contno='"+sContNo+"') "
    //         + " from LCPol a "
    //         + " where polno=mainpolno and appflag='1' and conttype='1' and payintv > 0 " //1- ���ճ���ʧЧ
    //         + getWherePart("a.ContNo", "ContNo_srh")
    //         + getWherePart("a.ManageCom", "ManageCom", "like")
    //         + " and exists "
    //         + " (select 'X' from LCContState where contno = a.contno and polno=a.polno and StateType ='Available' "
    //         +" and State = '1' and EndDate is null and statereason in ('01','02','03','04') ) "
    //         //����У�飬����lccont.appflag='1'���ܽ��и�Ч
    //         +" and exists(select 1 from lccont where contno=a.contno and appflag='1')"
    //         +" union "
    //         + " select * from ("
    //         + "select distinct a.ContNo, a.PayToDate x, "
    //		 + " (select  StartDate from LCContState where  PolNo = a.PolNo "
    //         + " and StateType ='Terminate' and State = '1' and EndDate is null and contno='"+sContNo+"' and statereason in ('01','07')), "
    //         + " (select  max(EnterAccDate) from LJAPayPerson  where ContNo = a.ContNo and contno='"+sContNo+"') "
    //         + " from LCPol a "
    //         + " where polno=mainpolno and appflag='4' and conttype='1' and payintv = 0 " //4- ����������ֹ
    //         + getWherePart("a.ContNo", "ContNo_srh")
    //         + getWherePart("a.ManageCom", "ManageCom", "like")
    //         +" and not exists(select 1 from lcpol b where b.contno=a.contno and b.polno=b.mainpolno and b.appflag='1') "
    //         + " and exists "
    //         + " (select 'X' from LCContState where contno = a.contno and polno=a.polno and StateType ='Terminate' "
    //         +" and State = '1' and EndDate is null and statereason in ('01','07')) "
    //         + " order by x desc )where rownum <= 1 ";       
    
    
    QuerySQL=wrapSql('LCPol1',[sContNo,sContNo,sContNo,document.all("ManageCom").value,
    													 sContNo,sContNo,sContNo,document.all("ManageCom").value]);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }catch (ex)
    {
        alert("���棺��ѯʧЧ��ͬ��Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("ContNo")[0].value = arrResult[0][0];
            document.getElementsByName("LastPayToDate")[0].value = arrResult[0][1];
            document.getElementsByName("UnAvailableDate")[0].value = arrResult[0][2];
            document.getElementsByName("LastEnterAccDate")[0].value = arrResult[0][3];
        }
        catch (ex) {}
    }
    else
    {
        alert("û�з���������ʧЧ��ͬ��¼�� ");
      	return;
    }
   // alert("�����������"+document.all('LastPayToDate').value);
}
/*============================================================================*/

/**
 * ��ѯʧЧ���ֶ���
 */
function queryAvailableGrid()
{
    var sContNo, sUnAvailableDate;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
        sUnAvailableDate = document.getElementsByName("UnAvailableDate")[0].value;
    }
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "" || sUnAvailableDate == null || trim(sUnAvailableDate) == "")
    {
        return;
    }
    //var QuerySQL = "select a.RiskCode, "
    //             + "(select distinct RiskName from LMRisk where RiskCode = a.RiskCode), "
    //             + "a.InsuredNo, a.InsuredName, a.CValiDate, "
    //             + "case a.AppFlag when '1' then 'ʧЧ' else 'ʧЧ��ֹ' end, "
    //             + "(select codename from ldcode c,lccontstate b where 1 = 1 and c.codetype = 'contavailablereason' "
    //             +" and b.statereason=c.code and b.statetype ='Available' and b.polno=a.polno  and b.state='1' and b.statereason in ('01','02','03','04') and b.enddate is null "
    //             +"  union "
    //             +" select codename from ldcode c,lccontstate b where 1 = 1 and c.codetype = 'contterminatereason' "
    //             +" and b.statereason=c.code and b.statetype ='Terminate' and b.polno=a.polno  and b.state='1' and b.statereason in ('01','07') and b.enddate is null )"
    //             + "from LCPol a  where 1 = 1 and a.appflag in ('1','4','9') and a.paytodate='"+document.all('LastPayToDate').value+"'"
    //             +  getWherePart("a.ContNo", "ContNo")
    //             +  "order by a.riskcode,a.cvalidate asc";  
    
    	var QuerySQL = wrapSql('LCPol2',[document.all('LastPayToDate').value,sContNo]);     
    try
    {
        turnPage.queryModal(QuerySQL, AvailableGrid);
    }           
    catch (ex)
    {
        alert("���棺��ѯʧЧ������Ϣ�����쳣�� ");
        return;
    }
    //var reason_SQL =  "select codename from lcpol a,ldcode c,lccontstate b where a.polno=a.mainpolno and a.polno=b.polno  "
    //           +" and b.statereason=c.code and  c.codetype = 'contavailablereason'  and b.statetype ='Available'"
    //           +" and b.state='1' and b.statereason in ('01','02','03','04') and b.enddate is null "
    //           +  getWherePart("a.ContNo", "ContNo")
    //           + " union  "           
    //           +" select codename from lcpol a,ldcode c,lccontstate b where a.polno=a.mainpolno and a.polno=b.polno  "
    //           +" and b.statereason=c.code and  c.codetype = 'contterminatereason'  and b.statetype ='Terminate' "
    //           +" and b.state='1' and b.statereason in ('01','07')   and b.enddate is null "
    //           +  getWherePart("a.ContNo", "ContNo")
    //           ;
    //prompt("",reason_SQL);         
    
     var reason_SQL = wrapSql('LDCode1',[sContNo,sContNo]);  
     
    document.all('InvalidReason').value = easyExecSql(reason_SQL, 1, 1, 1); 
    //alert("ʧЧԭ��"+document.all('InvalidReason').value); 
}
/*============================================================================*/
/**
 * �������⸴Ч���
 */
function saveRevalidateCont()
{
    if (!isHaveQueried()) return;
    if (isHaveSaved()) return;
    if (!verifyInput2()) return;
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

    document.forms(0).action = "LRNSpecialAvailableSave.jsp";
    document.forms(0).submit();
}
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
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        isThisSaved = true;
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

/**
 * �ۺϲ�ѯ
 * add by guanwei 2006-3-22
 */
function gotoMultipleQuery()
{
    var sContNo;
    try { sContNo = document.getElementsByName("ContNo_srh")[0].value; } catch (ex) {}
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("����������Ҫ�����ۺϲ�ѯ�ĺ�ͬ�ţ� ");
        return;
    }
    else
    {
        window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + trim(sContNo) +"&IsCancelPolFlag=0");
    }
}

/*============================================================================*/

/**
 * ����Ƿ��Ѿ���ѯ��
 */
function isHaveQueried()
{
    var sContNo;
    try { sContNo = document.getElementsByName("ContNo")[0].value; } 
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("���������ͬ�Ž��в�ѯ�� ");
        return false;
    }
    return true;
}

/*============================================================================*/

/**
 * ����Ƿ��Ѿ������
 */
function isHaveSaved()
{
    if (isThisSaved)
    {
        alert("���β����ѳɹ�������������²�ѯ�� ");
        return true;
    }
    return false;
}
/*============================================================================*/

/**
 * ����ɹ�֮���������ʾ������
 */
function clearShowedCont()
{
    try
    {
        document.getElementsByName("ContNo")[0].value = "";
        document.getElementsByName("LastPayToDate")[0].value = "";
        document.getElementsByName("UnAvailableDate")[0].value = "";
        document.getElementsByName("LastEnterAccDate")[0].value = "";
    }
    catch (ex) {}
}

/*============================================================================*/

//<!-- JavaScript Document END -->
/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.LRNSpecialAvailableInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}