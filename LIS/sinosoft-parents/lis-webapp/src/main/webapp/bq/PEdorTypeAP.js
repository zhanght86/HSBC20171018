/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-11-05, 2006-03-11, 2006-08-25
 * @purpose  : ��ȫ�����Ե����롢��ֹ���ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;    //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeAPInputSql";
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
            //initForm();
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
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ���ݲ�������(¼����ѯ)����������ť�Ƿ���ʾ
 */
function EdorQuery()
{
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯ������Ϣ
 */
function queryPolInfo()
{
    var strSQL="" ;
    initPolGrid() ; 
/*    strSQL = "select insuredno,insuredname,appntno,appntname,b.riskname,prem,amnt,mult,polno "
             +        " from lcpol a,lmrisk b where a.riskcode=b.riskcode and a.appflag='1'"
             +        " and contno='"+fm.ContNo.value+"' order by polno" ;
    //2008-7-29 turn to display multiline ..
*/    
	var sqlid1="PEdorTypeAPInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql1.getString();
    
    turnPage.queryModal(strSQL, PolGrid);
    //try
    //{
    //    arrResult = easyExecSql(QuerySQL, 1, 0);
    //}
    //catch (ex)
    //{
    //    alert("���棺��ѯ������Ϣ�����쳣�� ");
    //    return;
    //}
    //if (arrResult != null)
    //{
    //    try
    //    {
    //        document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
    //        document.getElementsByName("RiskName")[0].value = arrResult[0][1];
    //        document.getElementsByName("AppntName")[0].value = arrResult[0][2];
    //        document.getElementsByName("InsuredNo")[0].value = arrResult[0][3];
    //        document.getElementsByName("InsuredName")[0].value = arrResult[0][4];
    //        document.getElementsByName("InsuredAppAge")[0].value = arrResult[0][5];
    //    }
    //    catch (ex) {}
    //} //arrResult != null
}

/*============================================================================*/

/**
 * ��ѯ�����Զ��潻��Ϣ
 */
function queryAutoPayFlag()
{
    var QueryOldSQL, arrResult, QueryNewSQL, arrNewResult;
/*    QueryOldSQL = "select AutoPayFlag "
             +   "from lCCont "
             +  "where 1 = 1 "
             +     getWherePart("ContNo", "ContNo");
             //+     getWherePart("PolNo", "PolNo");
*/    
    var sqlid2="PEdorTypeAPInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QueryOldSQL=mySql2.getString();
    
/*    QueryNewSQL =  "select AutoPayFlag "
             +   "from LPCont "
             +  "where 1 = 1 "
             +     getWherePart("ContNo", "ContNo")
             //+     getWherePart("PolNo", "PolNo")
             +		 getWherePart("EdorNo","EdorNo");
    //alert(QuerySQL);
*/    
    var sqlid3="PEdorTypeAPInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.EdorNo.value);
	QueryNewSQL=mySql3.getString();
    
    try
    {
        arrResult = easyExecSql(QueryOldSQL, 1, 0);
        arrNewResult = easyExecSql(QueryNewSQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�����Զ��潻��Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
        	            	  //alert(arrResult[0][0]);
            switch (arrResult[0][0])
            {
                case "0":
                    //ԭ�潻��Ϣ
                    document.getElementsByName("AutoPayFlag_Old")[0].value = 0;
                    showOneCodeName('AutoPayType', 'AutoPayFlag_Old', 'AutoPayFlagName_Old');
                    //�潻���
                    if(arrNewResult != null)
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = arrNewResult[0][0];
                    }else
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = 0;
                    }
                    
                    showOneCodeName('AutoPayType', 'AutoPayFlag', 'AutoPayFlagName');
                    //document.getElementsByName("AutoPayFlag")[0].ondblclick = "showCodeList('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].onkeyup = "showCodeListKey('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].readOnly = true;
                    break;
                case "1":
                    //ԭ�潻��Ϣ
                    document.getElementsByName("AutoPayFlag_Old")[0].value = 1;
                    showOneCodeName('AutoPayType', 'AutoPayFlag_Old', 'AutoPayFlagName_Old');
                    //�潻���
                    if(arrNewResult != null)
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = arrNewResult[0][0];
                    }else
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = 1;
                    }
                    showOneCodeName('AutoPayType', 'AutoPayFlag', 'AutoPayFlagName');
                    //document.getElementsByName("AutoPayFlag")[0].ondblclick = "showCodeList('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].onkeyup = "showCodeListKey('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].readOnly = true;
                    break;
                default:
                    //Ϊ�պ�0��Ϊ���Ե�
                    document.getElementsByName("AutoPayFlag_Old")[0].value = "0";
                    showOneCodeName('AutoPayType', 'AutoPayFlag_Old', 'AutoPayFlagName_Old');
                    break;
            }
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * �ύ��ȫ��Ŀ���
 */
function saveEdorTypeAP()
{
		var tAutoPayType_Old = document.getElementsByName("AutoPayFlag_Old")[0].value;
		var tAutoPayType = document.getElementsByName("AutoPayFlag")[0].value;
		//alert(tAutoPayType_Old);
		//alert(tAutoPayType);
		if(tAutoPayType_Old == tAutoPayType){
			alert("����Ե淽ʽ�����ĺ��ٱ��棬�����������뵥������");
			return;
			}
		//return;
    if (!verifyInput2())    return;
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
    document.forms(0).action = "PEdorTypeAPSubmit.jsp";
    document.forms(0).submit();
}

/*============================================================================*/

/**
 * ���±��鿴
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo;
    try
    {
        sMissionID = top.opener.document.getElementsByName("MissionID")[0].value;
        sSubMissionID = top.opener.document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = top.opener.document.getElementsByName("OtherNo")[0].value;
    }
    catch (ex) {}
    if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "" || sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("���棺�޷���ȡ����������ڵ�����š��鿴���±�ʧ�ܣ� ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID="+ sSubMissionID + "&ActivityID=0000000003&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "���������±��鿴", "left");
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
