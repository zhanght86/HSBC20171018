var turnPage = new turnPageClass();
var sqlresourcename = "bq.PEdorTypePMInputSql";

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

/*********************************************************************
 *  ���ݽɷ����޵�ֵ���ýɷѷ�ʽ(�ɷѼ��)
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function setPayIntv()
{
    if (fm.PayYears_new.value == "0")
    {
        fm.PayIntv.value = "0";
    }
}

/*********************************************************************
 *  ��ȫ�������
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function saveEdorTypeFM()
{
	if (document.all('PayIntv_new').value == null || document.all('PayIntv_new').value == '') { 
		alert("����������Ľ��Ѽ��Ϊ�꽻!");
		return;
	}
    fm.fmtransact.value = "EDORITEM|INPUT";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "../bq/PEdorTypePMSubmit.jsp";
    fm.submit(); //�ύ
}

/*********************************************************************
 *  ��ȫ���ȡ��
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function edorTypeFMCancel()
{
    fm.PayYears_new.value = fm.PayYears_old.value;
    fm.PayYears_newName.value = "";

}

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
        try
        {
            top.opener.getEdorItemGrid();
            queryBackFee();
        }
        catch (ex) {
        alert("ex"+ex);
        }
    }
}

/*********************************************************************
 *  ��ʼ���ɷ�����ѡ���б�
 *  ����: ��ʼ���ɷ�����ѡ���б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPayEndYear(cObjCode,cObjName)
{
	//comment by jiaqiangli 2008-12-18 ����payintv>0�������յ�lcpol.payintvӦ�����
	var CodeData = "0|^12|�꽻";
//        var CodeData = "0|";
//        strSQL = " select ParamsCode, ParamsName from LMRiskParamsDef " +
//                 " where Paramstype = 'payendyear' and riskcode = '" + fm.RiskCode.value + "'" +
//                 " union " +
//                 " select '1000','����' from dual " +
//                 " where ('0' in (select ParamsCode  from LMRiskParamsDef " +
//                 " where Paramstype = 'payintv' and riskcode = '" + fm.RiskCode.value + "'))";
//        var crr = easyExecSql(strSQL);
//        if ( crr )
//        {
//            var i;
//            for (i = 0; i < crr.length; i++)
//            {
//
//                CodeData += "^" + crr[i][0] + "|" + crr[i][1];
//            }
//        }

    cObjCode.setAttribute('CodeData',CodeData);
    showCodeListEx('111',[cObjCode,cObjName],[0,1],'','','',1);
}

function QueryEdorInfo()
{

    var tEdortype=top.opener.document.all('EdorType').value;
//    var strSQL="select distinct a.edorname from  lmedoritem a where a.edorcode ='"+tEdortype+"' and appobj='I'";
    
    var strSQL = "";
	var sqlid1="PEdorTypePMInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdortype);//ָ������Ĳ���
	strSQL=mySql1.getString();
    
    var prr = easyExecSql(strSQL);

    if ( prr )  //P���б���������ݣ���ʾ��������
    {
        prr[0][0]==null||prr[0][0]=='null'?'0':fm.EdorTypeName.value = prr[0][0];
    }
}

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

function displayPolGrid()
{
    var tSelNo = PolGrid.getSelNo()-1;

    var tPolNo =  PolGrid.getRowColData(tSelNo, 1);

    getPolInfo(tPolNo);


}

function getPolGrid()
{
    var strSQL = " ";
/*    strSQL =  " select polno, riskcode , "
            + " (select RiskshortName from LMRisk where LMRisk.RiskCode = p2.RiskCode),"
            + " appntname, insuredname, InsuredAppAge, prem,payintv,cvalidate,paytodate "
            + " from lcpol p2 "
            + " where appflag = '1' and payintv > 0 and payintv <> 12 and ContNo = '"+fm.ContNo.value+"' ";
*/
	var sqlid2="PEdorTypePMInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql2.getString();
    
    var drrResult = easyExecSql(strSQL);
    if (drrResult)
    {
        displayMultiline(drrResult, PolGrid);
    }

    if (PolGrid.mulLineCount >= 1)
    {
        divMultiPol.style.display='';
    }
}

function getPolInfo(PolNo)
{

    var strSQL;
    //��ѯP������
/*    strSQL =  " select polno, appntno, appntname, "
                + " prem, amnt,"
                + " riskcode , "
                + " (select RiskName from LMRisk where LMRisk.RiskCode = p.RiskCode),"
                + "insuredname, InsuredAppAge, mult "
            + " from lppol p "
            + " where polno = '"+PolNo+"' and edorno = '" + fm.EdorNo.value + "'"
            + " and edortype = '" + fm.EdorType.value + "'";
*/    
    var sqlid3="PEdorTypePMInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(PolNo);//ָ������Ĳ���
	mySql3.addSubPara(fm.EdorNo.value);
	mySql3.addSubPara(fm.EdorType.value);
	strSQL=mySql3.getString();
    
    var prr = easyExecSql(strSQL);

    if ( prr )  //P���б���������ݣ���ʾ��������
    {
	    prr[0][0]==null||prr[0][0]=='null'?'0':fm.PolNo_old.value       = prr[0][0];
            prr[0][2]==null||prr[0][2]=='null'?'0':fm.AppntName_old.value   = prr[0][2];
            prr[0][3]==null||prr[0][3]=='null'?'0':fm.Prem_old.value        = prr[0][3];
            prr[0][4]==null||prr[0][4]=='null'?'0':fm.Amnt_old.value        = prr[0][4];
            prr[0][5]==null||prr[0][5]=='null'?'0':fm.RiskCode_old.value        = prr[0][5];
            prr[0][6]==null||prr[0][6]=='null'?'0':fm.RiskName_old.value     = prr[0][6];
            prr[0][7]==null||prr[0][7]=='null'?'0':fm.InsuredName_old.value  = prr[0][7];
            prr[0][8]==null||prr[0][8]=='null'?'0':fm.InsuredAppAge_old.value= prr[0][8];
            prr[0][9]==null||prr[0][9]=='null'?'0':fm.Mult_old.value        = prr[0][9];

    }
    else
    {
        //��ѯC������
/*        strSQL =  " select polno, appntno, appntname, "
                + " prem, amnt,"
                + " riskcode , "
                + " (select RiskName from LMRisk where LMRisk.RiskCode = p.RiskCode),"
                + "insuredname, InsuredAppAge, mult "
                + " from lcpol p "
                + " where appflag = '1' and payintv > 0 and payintv <> 12 and polno = '"+PolNo+"' and ContNo = '"+fm.ContNo.value+"' ";
*/        
    var sqlid4="PEdorTypePMInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(PolNo);//ָ������Ĳ���
	mySql4.addSubPara(fm.ContNo.value);
	strSQL=mySql4.getString();
        
        var crr = easyExecSql(strSQL);
        if ( crr )
        {
            crr[0][0]==null||crr[0][0]=='null'?'0':fm.PolNo_old.value       = crr[0][0];
            crr[0][2]==null||crr[0][2]=='null'?'0':fm.AppntName_old.value   = crr[0][2];
            crr[0][3]==null||crr[0][3]=='null'?'0':fm.Prem_old.value        = crr[0][3];
            crr[0][4]==null||crr[0][4]=='null'?'0':fm.Amnt_old.value        = crr[0][4];
            crr[0][5]==null||crr[0][5]=='null'?'0':fm.RiskCode_old.value        = crr[0][5];
            crr[0][6]==null||crr[0][6]=='null'?'0':fm.RiskName_old.value     = crr[0][6];
            crr[0][7]==null||crr[0][7]=='null'?'0':fm.InsuredName_old.value  = crr[0][7];
            crr[0][8]==null||crr[0][8]=='null'?'0':fm.InsuredAppAge_old.value= crr[0][8];
            crr[0][9]==null||crr[0][9]=='null'?'0':fm.Mult_old.value        = crr[0][9];
        }
        else
        {
            alert("���ֱ�����Ϣ��ѯʧ��!");
            return;
        }
    }

    //��ѯP������
/*    strSQL =  " select payintv,decode(payintv,12,'�꽻','���꽻'),prem from lppol p "
            + " where edorno = '" + fm.EdorNo.value + "'"
            + " and edortype = '" + fm.EdorType.value + "'"
            + " and polno = '" + PolNo + "'";
*/    
    var sqlid5="PEdorTypePMInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql5.addSubPara(fm.EdorType.value);
	mySql5.addSubPara(PolNo);
	strSQL=mySql5.getString();
    
    var nrr = easyExecSql(strSQL);
    if ( nrr )  //�Ѿ���������
    {
        nrr[0][0]==null||nrr[0][0]=='null'?'0':fm.PayIntv_new.value     = nrr[0][0];
        nrr[0][1]==null||nrr[0][1]=='null'?'':fm.PayIntv_newName.value        = nrr[0][1];
    }
    //initPayEndYear(fm.PayIntv_new,fm.PayIntv_newName);
}

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