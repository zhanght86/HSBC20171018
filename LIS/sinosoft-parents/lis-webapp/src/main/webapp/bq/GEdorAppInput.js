//�������ƣ�GEdorAppInput.js
//�����ܣ��ŵ���ȫ����


var showInfo;
var mDebug="1";
var aEdorFlag='0';
var mEdorType;
var turnPage = new turnPageClass();
var mflag = "";  //�������ͻ���ѯ��ʶ
var theFirstValue="";
var theSecondValue="";
var hasSaved = "";
var userEdorPopedom = "";  //��ǰ�û���ȫ����


//<!-- XinYQ added on 2006-04-07 : BGN -->
/*============================================================================*/

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���

 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var sTransAct;
    try { sTransAct = document.getElementsByName("fmtransact")[0].value; } catch (ex) {}
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
           	if (sTransAct == "INSERT||EDORAPPCONFIRM")
           	{
           	    MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
           	    //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
				var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=350;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
           	}
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
        switch (sTransAct)
        {
            case "INSERT||EDORAPP":
            		initForm();
            		break;
            case "INSERT||EDORAPPCONFIRM":
                initForm();
                if (confirm("ȷ�ϳɹ����Ƿ��ӡ���α�ȫ������?"))
        				{
	      				   fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
	      				   fm.target="f1print";
	      				   document.getElementById("fm").submit();
        				 }
        				 
        				 var QuerySQL, arrResult;
        				 
        				 var sqlid824105022="DSHomeContSql824105022";
var mySql824105022=new SqlClass();
mySql824105022.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824105022.setSqlId(sqlid824105022);//ָ��ʹ�õ�Sql��id
mySql824105022.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
QuerySQL=mySql824105022.getString();
        				 
//            		 QuerySQL = "select 'X' from LPEdorPrint2 where EdorNo = '" + fm.EdorNo.value + "'";
            
            			try
            			{
                		arrResult = easyExecSql(QuerySQL, 1, 0);
           				 }
           				 catch (ex)
           				 {
           				     alert("���棺��ѯ�����嵥��Ϣ�����쳣�� ");
           				     break;
           				 }
           				 if (arrResult != null)
           				 {
           				     if (confirm("�Ƿ��ӡ���α�ȫ����Ա�䶯�嵥?"))
        							{
                				fm.action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + fm.EdorNo.value + "&type=Bill";
                				fm.target = "_blank";
                				document.getElementById("fm").submit();
        							}
           				 }
               		 break;
            case "INSERT||EDORITEM":
                initGetPayForm();
                getEdorItemGrid();
                EdorItemGrid.selOneRow(1);
                gotoDetail();
                break;
            case "DELETE||EDORITEM":
                getEdorItemGrid();
                fm.EdorType.value = "";
                fm.EdorTypeName.value = "";
                fm.EdorTypeCal.value = "";
                fm.EdorTypeCalName.value= "";
                //alert(fm.DisplayType.value) ;
                break;
            case "DELETE||EDORITEM":
            	returnParent();
            	break;
            default:
                break;
        }
        try { top.opener.easyQueryClickSelf(); } catch (ex) {}
    }
    else if (DealFlag == "fail")
    {
        if (sTransAct == "INSERT||EDORAPPCONFIRM")
            initForm();
    }
}
//
function initReadonly()
{
	  initInpBox();
	
}
//�鿴���˷Ѻϼ�
function GetEdorMoney()
{
	
	//divFeeInfo.style.display=''; 
	var sqlid824105125="DSHomeContSql824105125";
var mySql824105125=new SqlClass();
mySql824105125.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824105125.setSqlId(sqlid824105125);//ָ��ʹ�õ�Sql��id
mySql824105125.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
var Inmoneysql=mySql824105125.getString();
	
//	var Inmoneysql = "select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno='"+document.all('EdorAcceptNo').value+"' and getflag='0'";
	var sqlid824105225="DSHomeContSql824105225";
var mySql824105225=new SqlClass();
mySql824105225.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824105225.setSqlId(sqlid824105225);//ָ��ʹ�õ�Sql��id
mySql824105225.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
var Outmoneysql=mySql824105225.getString();
	
//	var Outmoneysql="select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno='"+document.all('EdorAcceptNo').value+"' and getflag='1'" ;
  var InarrResult = easyExecSql(Inmoneysql);
  var OutarrResult = easyExecSql(Outmoneysql);
  try {
  	document.all('GetMoney').value = InarrResult[0][0]-OutarrResult[0][0];
  	} catch(ex) { };
  
}
//�����㷨��ʼ��
function getEdorCalCodeData()
{
	  var tOtherNo = document.all('OtherNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
    var tEdorType=document.all('EdorType').value;
    if(tEdorType==null || tEdorType=="")
    {
    	alert("����ѡ����������!!!") ;
    	fm.EdorType.className="warnno" ;
    	fm.EdorType.focus() ;
    }
    if (document.all('EdorType').value=="AA"||document.all('EdorType').value=="NI")
    {
    	document.all('EdorValidate').value=document.all('dayAfterCurrent').value;
    }
	//��ʼ������
	var sqlid824105742="DSHomeContSql824105742";
var mySql824105742=new SqlClass();
mySql824105742.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824105742.setSqlId(sqlid824105742);//ָ��ʹ�õ�Sql��id
mySql824105742.addSubPara(tOtherNo);//ָ������Ĳ���
mySql824105742.addSubPara(tEdorType);//ָ������Ĳ���
mySql824105742.addSubPara(tOtherNo);//ָ������Ĳ���
mySql824105742.addSubPara(tEdorType);//ָ������Ĳ���
mySql824105742.addSubPara(manageCom+"%%");//ָ������Ĳ���
var sql=mySql824105742.getString();
	
//	var sql="select distinct a.EdorTypeCal, b.CodeName from LMEdorTypeCal  a, LDCode b where 1 =1  and a.edortypecal=b.code and riskcode in (select riskcode from lcgrppol where grpcontno='"+tOtherNo+"') and a.edortype='"+tEdorType+"' and a.flag='1' and b.codetype='edortypecal' and a.managecom='86' union select distinct a.EdorTypeCal, b.CodeName from LMEdorTypeCal  a, LDCode b where 1 =1  and a.edortypecal=b.code and riskcode in (select riskcode from lcgrppol where grpcontno='"+tOtherNo+"') and a.edortype='"+tEdorType+"' and a.flag='1' and b.codetype='edortypecal' and managecom like '"+manageCom+"%%'";
    //alert(sql);
	//prompt('',sql);
    //fm.AppReasonName.value=sql;
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//var arrResult = easyExecSql(sql);
	
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    document.all("EdorTypeCal").CodeData=tCodeData;
}
/*============================================================================*/
//<!-- XinYQ added on 2006-04-07 : END -->


/*********************************************************************
 *  ҳ�����ʾ����
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initDiv()
{

    var EdorAcceptNo = document.all('EdorAcceptNo').value;
    var MissionID = document.all('MissionID').value
    var SubMissionID = document.all('SubMissionID').value

    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
    if(MissionID == null || MissionID == "")
    {
        alert("�����Ϊ�գ�");
        return;
    }
    if(SubMissionID == null || SubMissionID == "")
    {
        alert("�������Ϊ�գ�");
        return;
    }

    var strSQL;
    //��ѯ��ǰ�û���ȫ����
    var sqlid824110119="DSHomeContSql824110119";
var mySql824110119=new SqlClass();
mySql824110119.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824110119.setSqlId(sqlid824110119);//ָ��ʹ�õ�Sql��id
mySql824110119.addSubPara(fm.UserCode.value);//ָ������Ĳ���
strSQL=mySql824110119.getString();
    
//    strSQL =  " select trim(edorpopedom) from ldedoruser where trim(usercode) = '" + fm.UserCode.value + "' and usertype = '2' ";
    var urr = easyExecSql(strSQL);
    if ( urr )
    {
        urr[0][0]==null||urr[0][0]=='null'?'0':userEdorPopedom = urr[0][0];
    }

    //��ѯ��������ȫ����ȷ�Ͻڵ�
    var sqlid824110215="DSHomeContSql824110215";
var mySql824110215=new SqlClass();
mySql824110215.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824110215.setSqlId(sqlid824110215);//ָ��ʹ�õ�Sql��id
mySql824110215.addSubPara(EdorAcceptNo);//ָ������Ĳ���
strSQL=mySql824110215.getString();
    
//    strSQL =  " select OtherNo, OtherNoType, EdorAppName, AppType, EdorAppDate , EdorState,"
//            + " (select codename from ldcode a where trim(a.codetype) = 'edorstate' and trim(a.code) = trim(edorstate)),"
//            + " bankcode, bankaccno, accname, "
//            + " (select codename from ldcode b where trim(b.codetype) = 'gedornotype' and trim(b.code) = trim(OtherNoType)),"
//            + " (select codename from ldcode where trim(codetype) = 'edorapptype' and trim(code) = trim(AppType)),"
//            + " (select codename from ldcode where trim(codetype) = 'bank' and trim(code) = trim(bankcode)),"
//            + " paygetname, personid ,getform ,(select codename from ldcode where codetype = 'edorgetpayform' and code = getform) "
//            + " from LPEdorApp "
//            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";

    var brr = easyExecSql(strSQL);

    if ( brr )  //�Ѿ����뱣���
    {
        //alert("�Ѿ����뱣���");
        hasSaved = "Y";
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoType.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.EdorAppName.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.AppType.value     = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.EdorAppDate.value = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.PEdorState.value  = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.PEdorStateName.value  = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.BankCode.value    = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.BankAccNo.value   = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.AccName.value     = brr[0][9];
        brr[0][10]==null||brr[0][10]=='null'?'0':fm.OtherNoName.value   = brr[0][10];
        brr[0][11]==null||brr[0][11]=='null'?'0':fm.AppTypeName.value   = brr[0][11];
        brr[0][12]==null||brr[0][12]=='null'?'0':fm.BankName.value      = brr[0][12];
        brr[0][13]==null||brr[0][13]=='null'?'0':fm.PayGetName.value    = brr[0][13];
        brr[0][14]==null||brr[0][14]=='null'?'0':fm.PersonID.value      = brr[0][14];
        brr[0][15]==null||brr[0][15]=='null'?'0':fm.GetPayForm.value     = brr[0][15];
        brr[0][16]==null||brr[0][16]=='null'?'0':fm.GetPayFormName.value     = brr[0][16];

        fm.EdorAcceptNo_Read.value= fm.EdorAcceptNo.value;
        fm.OtherNo_Read.value     = fm.OtherNo.value;
        fm.OtherNoType_Read.value = fm.OtherNoName.value;
        fm.EdorAppName_Read.value = fm.EdorAppName.value;
        fm.AppType_Read.value     = fm.AppTypeName.value;
        fm.EdorAppDate_Read.value = fm.EdorAppDate.value;
        fm.PEdorStateName_Read.value = fm.PEdorStateName.value;
        //fm.PayGetName_Read.value = fm.PayGetName.value;
        //fm.PersonID_Read.value = fm.PersonID.value;
        //fm.BankCode_Read.value    = fm.BankName.value;
        //fm.BankAccNo_Read.value   = fm.BankAccNo.value;
        //fm.AccName_Read.value     = fm.AccName.value;

        divApplySaveWrite.style.display='none';     //��ȫ������Ϣ����
        divApplySaveRead.style.display='';          //��ȫ������Ϣֻ��
        divApplySaveBT.style.display='none';        //�������뱣�水ť

        if(fm.OtherNoType.value == "2")  //����ͻ���
        {
            displayCustomerInfo(fm.OtherNo.value);  //��ѯ�ͻ���ϸ��Ϣ
            getEdorItemGrid();                  //��ѯ��ȫ��Ŀ�б���ϸ��Ϣ
            divCustomer.style.display='';       //�ͻ���Ϣ
            divCont.style.display='none';       //������Ϣ
            divEdorItemInfo.style.display='';   //��ȫ��Ŀ��Ϣ

            if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp")
            {
                //��ȫ����
                divappconfirm.style.display=''; //����ȷ�ϰ�ť
                divApproveModify.style.display='none';//�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='none';  //������ϰ�ť
            }
            if(fm.LoadFlag.value == "approveModify")
            {
                //��ȫ�����޸�
                divappconfirm.style.display='none';//����ȷ�ϰ�ť
                divApproveModify.style.display='';//�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='none';  //������ϰ�ť
            }
            if(fm.LoadFlag.value == "edorTest")
            {
                //��ȫ����
                divappconfirm.style.display='none';    //����ȷ�ϰ�ť
                divApproveModify.style.display='none'; //�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //�����ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='';          //������ϰ�ť
            }
        }
        if(fm.OtherNoType.value == "4")  //���屣����
        {
            displayContInfo(fm.OtherNo.value);  //��ѯ������ϸ��Ϣ
            getEdorItemGrid();                      //��ѯ��ȫ��Ŀ�б���ϸ��Ϣ
            divCustomer.style.display='none';   //�ͻ���Ϣ
            divCont.style.display='';           //������Ϣ
            divEdorItemInfo.style.display='';   //��ȫ��Ŀ��Ϣ

            if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp")
            {
                //��ȫ����
                divappconfirm.style.display=''; //����ȷ�ϰ�ť
                divApproveModify.style.display='none';//�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='none';  //������ϰ�ť
            }
            if(fm.LoadFlag.value == "approveModify")
            {
                //��ȫ�����޸�
                divappconfirm.style.display='none';//����ȷ�ϰ�ť
                divApproveModify.style.display='';//�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='none';  //������ϰ�ť
            }
            if(fm.LoadFlag.value == "edorTest")
            {
                //��ȫ����
                divappconfirm.style.display='none';    //����ȷ�ϰ�ť
                divApproveModify.style.display='none'; //�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //�����ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='';          //������ϰ�ť
            }
        }

    }
    else  //��δ���뱣��
    {
        //alert("��δ���뱣��");
        hasSaved = "N";
        divApplySaveWrite.style.display='';     //��ȫ������Ϣ����
        divApplySaveRead.style.display='none';  //��ȫ������Ϣֻ��
        divApplySaveBT.style.display='';        //��ʾ���뱣�水ť
        divCustomer.style.display='none';       //�ͻ���Ϣ
        divCont.style.display='none';           //������Ϣ
        divEdorItemInfo.style.display='none';   //��ȫ��Ŀ��Ϣ
        divappconfirm.style.display='none';     //����ȷ�ϰ�ť
        
        divEdorTest.style.display='none';       //������ϰ�ť

        showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
    }
}

function EdorTestFinish()
{
    document.all("fmtransact").value = "DELETE||EDORTESTFINISH";

    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action= "./GEdorTestFinishSubmit.jsp";
    document.getElementById("fm").submit();
}

/*********************************************************************
 *  ��ѯ�ͻ���ϸ��Ϣ
 *  ����: ��ѯ�ͻ���ϸ��Ϣ
 *  ����  ��  CustomerNo
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayCustomerInfo(CustomerNo)
{
    var strSQL;
    
    var sqlid824110319="DSHomeContSql824110319";
var mySql824110319=new SqlClass();
mySql824110319.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824110319.setSqlId(sqlid824110319);//ָ��ʹ�õ�Sql��id
mySql824110319.addSubPara(CustomerNo);//ָ������Ĳ���
strSQL=mySql824110319.getString();

//    strSQL =  " select CustomerNo, GrpName, BusinessType, Peoples, Asset "
//            + " from LDGrp "
//            + " where customerno = '" + CustomerNo + "'";

    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.CustomerNo.value  = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.GrpName2.value    = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.BusinessType.value= brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.Peoples.value     = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.Asset.value       = brr[0][4];
    }
    else
    {
        clearCustomerInfo();
    }
}

//��տͻ���Ϣ
function clearCustomerInfo()
{
        fm.CustomerNo.value   = "";
        fm.GrpName2.value         = "";
        fm.BusinessType.value          = "";
        fm.Peoples.value     = "";
        fm.Asset.value       = "";
}
/*********************************************************************
 *  ��ѯ������ϸ��Ϣ
 *  ����: ��ѯ������ϸ��Ϣ
 *  ����  ��  ContNo
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayContInfo(ContNo)
{
    var strSQL;
    
    var sqlid824110406="DSHomeContSql824110406";
var mySql824110406=new SqlClass();
mySql824110406.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824110406.setSqlId(sqlid824110406);//ָ��ʹ�õ�Sql��id
mySql824110406.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql824110406.getString();

//    strSQL =  " select GrpContNo, GrpName, CValiDate, Peoples2, Prem, Amnt, "
//            + "(select linkman1 from lcgrpaddress a where 1 = 1 and a.customerno = appntno and a.addressno = b.addressno), agentcode "
//            + " from LCGrpCont b "
//            + " where  GrpContNo = '" + ContNo + "' ";

    var brr = easyExecSql(strSQL);
    if ( brr )
    {

        brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNoApp.value = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.GrpName.value   = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.CValiDate.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.Peoples2.value  = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.Prem.value      = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.Amnt.value      = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.LinkMan1.value  = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.AgentCode.value = brr[0][7];

    }
    else
    {
        clearContInfo();
    }
}

//��տͻ���Ϣ
function clearContInfo()
{
        fm.ContNoApp.value   = "";
        fm.GrpName.value   = "";
        fm.CValiDate.value = "";
        fm.Peoples2.value        = "";
        fm.Amnt.value        = "";
        fm.Prem.value   = "";
}


function initEdorType(cObjCode,cObjName)
{
    var tOtherNo = document.all('OtherNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
	var tLoadFlag = fm.LoadFlag.value;

    if (tOtherNoType=='4')//���屣��������
    {
        mEdorType = " 1 and AppObj=#G#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        mEdorType=mEdorType+" and riskcode in (select riskcode from lcgrppol where grpcontno=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest") {
        	mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    else if (tOtherNoType=='2')//����ͻ�������
    {
        mEdorType = " 1 and AppObj=#B#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        //mEdorType=mEdorType+" and riskcode in (select riskcode from lcpol where AppntNo=#"+tOtherNo+"# ";
        //mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest") {
        	mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    return showCodeList('EdorCode', [cObjCode,cObjName], [0,1], null, mEdorType, "1", 0, 206);
}

function actionKeyUp(cObjCode,cObjName)
{
    var tOtherNo = document.all('OtherNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
	var tLoadFlag = fm.LoadFlag.value;

    if (tOtherNoType=='4')//���屣��������
    {
        mEdorType = " 1 and AppObj=#G#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        mEdorType=mEdorType+" and riskcode in (select riskcode from lcgrppol where grpcontno=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest") {
        	mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    else if (tOtherNoType=='2')//����ͻ�������
    {
        mEdorType = " 1 and AppObj=#B#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        //mEdorType=mEdorType+" and riskcode in (select riskcode from lcpol where AppntNo=#"+tOtherNo+"# ";
        //mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest") {
        	mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    return showCodeListKey('EdorCode', [cObjCode,cObjName], [0,1], null, mEdorType, "1", 0, 206);
}

/*********************************************************************
 *  ѡ��������Ŀ��Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
    try {

        if( cCodeName == "EdorCode" )
        {
            var tOtherNoType = document.all('OtherNoType').value;
            var tAppObj;
            if (tOtherNoType=='4')//���屣��������
            {
                tAppObj = "G"
            }
            if (tOtherNoType=='2')//����ͻ�������
            {
                tAppObj = "B"
            }
            
            var sqlid824110550="DSHomeContSql824110550";
var mySql824110550=new SqlClass();
mySql824110550.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824110550.setSqlId(sqlid824110550);//ָ��ʹ�õ�Sql��id
mySql824110550.addSubPara(Field.value );//ָ������Ĳ���
mySql824110550.addSubPara(tAppObj);//ָ������Ĳ���
var strsql=mySql824110550.getString();
            
//            var strsql =  " select DisplayFlag from LMEdorItem "
//                        + " where EdorCode='" + Field.value + "' and AppObj='" + tAppObj + "'"; //

            var tDisplayType = easyExecSql(strsql);

            if (tDisplayType == null || tDisplayType == '')
            {
                alert("δ�鵽�ñ�ȫ��Ŀ��ʾ����");
                return;
            }
            if (tOtherNoType=='1')//���˿ͻ�������
            {
                tDisplayType = "1"
            }
            document.all("DisplayType").value = tDisplayType;
						fm.EdorTypeCal.value="";
						fm.EdorTypeCalName.value="";
/*=========DEL========zhangtao============2005-08-16=======BGN=======
            if (tDisplayType=='1')  //ֻ��ʾ����
            {
                divInsuredInfo.style.display='none';
                divPolInfo.style.display='none';
            }
            else if (tDisplayType=='2')  //��Ҫ��ʾ�ͻ�
            {
                getInsuredGrid(document.all('OtherNo').value);
                divPolInfo.style.display='none'
                divInsuredInfo.style.display='';
            }
            else if (tDisplayType=='3')  //��Ҫ��ʾ����
            {
                getPolGrid(document.all('OtherNo').value);
                divInsuredInfo.style.display='none';
                divPolInfo.style.display='';
            }
=========DEL========zhangtao============2005-08-16======END=======*/
        }

    }
    catch( ex )
    {
        alert(ex);
    }
}

/*********************************************************************
 *  ��ѯ���������б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredGrid(tContNo)
{
    initInusredGrid();
    
    var sqlid824110706="DSHomeContSql824110706";
var mySql824110706=new SqlClass();
mySql824110706.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824110706.setSqlId(sqlid824110706);//ָ��ʹ�õ�Sql��id
mySql824110706.addSubPara(tContNo);//ָ������Ĳ���
mySql824110706.addSubPara(tContNo);//ָ������Ĳ���
var strSQL=mySql824110706.getString();

//    var strSQL =  " select distinct a.CustomerNo,a.Name,a.Sex, "
//                + " a.Birthday, a.IDType, a.IdNo,b.contno,b.grpcontno "
//                + " from LDPerson a,LCCont b "
//                + " where b.ContNo='" + tContNo + "'"
//                + " and a.CustomerNo in "
//                + " (select insuredno from LCInsured where ContNo='" + tContNo + "')";

    turnPage.queryModal(strSQL, InsuredGrid);

}
/*********************************************************************
 *  ��ѯ�����б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getPolGrid(tContNo)
{
    var AppDate = document.all('EdorItemAppDate').value;
    if (AppDate == null || AppDate =="")
    {
        AppDate = document.all('EdorAppDate').value;
    }
    initPolGrid();
    
    var sqlid824111159="DSHomeContSql824111159";
var mySql824111159=new SqlClass();
mySql824111159.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824111159.setSqlId(sqlid824111159);//ָ��ʹ�õ�Sql��id
mySql824111159.addSubPara(tContNo);//ָ������Ĳ���
mySql824111159.addSubPara(AppDate);//ָ������Ĳ���
mySql824111159.addSubPara(AppDate);//ָ������Ĳ���
mySql824111159.addSubPara(AppDate);//ָ������Ĳ���
var strSQL=mySql824111159.getString();
    
//    var strSQL = " select RiskCode, PolNo, InsuredNo, InsuredName,"
//                + " amnt, prem, "
//                + " (case (select distinct 1 from lcpol p1 where p1.proposalno = p.proposalno and p1.polno <> p.polno )  when 1 then (select cvalidate from lcpol p2 where p2.polno = p.proposalno and rownum = 1 ) else p.cvalidate end),"
//                + " PayToDate, ContNo, GrpContNo "
//                + " from LCPol p where ContNo='" + tContNo
//                + "'  and appflag = '1' "
//                + " and  (select count(*) from lccontstate s where statetype in('Terminate') and state = '1' and ((startdate <= '" + AppDate + "' and '" + AppDate + "' <= enddate )or(startdate <= '" + AppDate + "' and enddate is null ))and s.polno = p.polno) = 0";
//                + " order by makedate, maketime";
    //ȥ���Ѿ�ʧЧ�ı���
    //�������Զ���������ж�����¼��ֻ����ʾһ����������Ч����Ϊ���磬��������Ϊ����
    turnPage.queryModal(strSQL, PolGrid);

}

/*********************************************************************
 *  ��ӱ�ȫ��Ŀ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addEdorItem()
{
    if (document.all('EdorAcceptNo').value==null||document.all('EdorAcceptNo').value=="")
    {
        alert("���ȱ��汣ȫ��������ӱ�ȫ��Ŀ!");
        return false;
    }
    if (document.all('EdorType').value==null||document.all('EdorType').value=="")
    {
        alert("��ѡ����Ҫ��ӵı�ȫ��Ŀ!");
        fm.EdorType.className="warnno" ;
        fm.EdorType.focus() ;
        return false;
    }
    var sqlid824112201="DSHomeContSql824112201";
	var mySql824112201=new SqlClass();
	mySql824112201.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql824112201.setSqlId(sqlid824112201);//ָ��ʹ�õ�Sql��id
	mySql824112201.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
	mySql824112201.addSubPara(document.all('ContNoApp').value);//ָ������Ĳ���
	var strSql=mySql824112201.getString();
    
//    var strSql = "select count(*) from lmedortypecal where edortype='"+document.all('EdorType').value
//             +"' and riskcode in (select riskcode from lcgrppol where grpcontno='"
//             +document.all('ContNoApp').value+"' and appflag='1') and flag='1'";
	var arrResult = easyExecSql(strSql, 1, 0);
	if (arrResult!=null && arrResult != '0') 
    {
        	if (document.all('EdorTypeCal').value==null||trim(document.all('EdorTypeCal').value)=="")
        {
        	alert("��ѡ�������㷨!");
        	fm.EdorTypeCal.className="warnno" ;
          fm.EdorTypeCal.focus() ;
          return false;
        }
    }
    
    if (document.all('DisplayType').value == null ||
        document.all('DisplayType').value == "" )
    {
    	//alert("1111111111");
    	var sqlid824112337="DSHomeContSql824112337";
		var mySql824112337=new SqlClass();
		mySql824112337.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql824112337.setSqlId(sqlid824112337);//ָ��ʹ�õ�Sql��id
		mySql824112337.addSubPara(fm.EdorType.value);//ָ������Ĳ���
		strSql=mySql824112337.getString();
    	
//    	strSql =" select DisplayFlag from LMEdorItem "
//             + " where EdorCode='" + fm.EdorType.value + "' and AppObj='G'"; //
      //alert(strSql);
      var tDisplayType = easyExecSql(strSql,1,0);
      if (tDisplayType == null || tDisplayType == "")
      {
       alert("δ�鵽�ñ�ȫ��Ŀ��ʾ����");
       return;
      }
      
      document.all('DisplayType').value=tDisplayType ;
        //alert("��˫��ѡ����Ҫ��ӵı�ȫ��Ŀ!");
        //return false;
    }
    
    
/******************************************************
    if (document.all('DisplayType').value == '2')
    {
        var chkflag = false;

        for (i = 0; i < InsuredGrid.mulLineCount; i++)
        {
            if (InsuredGrid.getChkNo(i))
            {
                chkflag = true;
                break;
            }
        }
        if (!chkflag)
        {
            alert("��ѡ����Ҫ��ӱ�ȫ��Ŀ�Ŀͻ�!");
            return false;
        }
    }
    else if (document.all('DisplayType').value == '3')
    {
        var chkflag = false;

        for (i = 0; i < PolGrid.mulLineCount; i++)
        {
            if (PolGrid.getChkNo(i))
            {
                chkflag = true;
                break;
            }
        }
        if (!chkflag)
        {
            alert("��ѡ����Ҫ��ӱ�ȫ��Ŀ������!");
            return false;
        }
    }
******************************************************/
    else if (document.all('DisplayType').value == '1')
    {

    }
    else
    {
        //alert("��ȫ��Ŀ��ʾ�����ѯ����!");
    }
    if (document.all('EdorItemAppDate').value == null || document.all('EdorItemAppDate').value == "")
    {
        alert("�����������������");
        return;
    }
    
 //---������ɫ��ע�͵�
    //add by jiaqiangli 2009-11-02 ��ʱ�����±��շ���ʾ
//    if (document.all('EdorType').value=="NI"
//    		||document.all('EdorType').value=="CT"
//    		||document.all('EdorType').value=="PT"
//    		||document.all('EdorType').value=="ZT"
//    		||document.all('EdorType').value=="IC")
//    {
//    var sqlid824112435="DSHomeContSql824112435";
//	var mySql824112435=new SqlClass();
//	mySql824112435.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
//	mySql824112435.setSqlId(sqlid824112435);//ָ��ʹ�õ�Sql��id
//	mySql824112435.addSubPara(document.all('ContNoApp').value);//ָ������Ĳ���
//	var QuerySQL=mySql824112435.getString();
//    
////    var QuerySQL="select nvl(polapplydate,cvalidate) from lcgrpcont where grpcontno = '" + document.all('ContNoApp').value + "' ";
//		var arr = easyExecSql(QuerySQL, 1, 0, 1);
//		
//    	var tPolApplyDate = arr[0][0];
//    	//alert("polapplydate"+tPolApplyDate);
//    	if(compareDate(tPolApplyDate,"2009-10-01")==1)
//    	{
//    		 alert(document.all('ContNoApp').value+"���ź�ͬΪ2009��10��1���Ժ�б�"+"(�ŵ���Ͷ������Ϊ"+tPolApplyDate+")��Ӧ�������¼��㷽�����������Ա���ܹ�˾�ͷ������ձ�ȫ�������Ա��ͨ���ٽ�����Ӧϵͳ������");
//    	}
//       
//    }
    //add by jiaqiangli 2009-11-02 ��ʱ�����±��շ���ʾ
    
    //alert("XXXXXXXXXXXXXXX");
    //return;
    //return;
    document.all('fmtransact').value="INSERT||EDORITEM";
    document.all('Transact').value="GRPEDORITEM";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    fm.action = "../bq/GEdorAppItemSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit();

}

/*********************************************************************
 *  ���뱣ȫ��Ŀ��ϸҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function gotoDetail()
{
    var tSelNo = EdorItemGrid.getSelNo()-1;
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫ�������Ŀ��");
        return false;
    }

    detailEdorType();

}

/*********************************************************************
 *  ɾ��δ¼�뱣ȫ��Ŀ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delEdorItem()
{
    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
    var tSelNo = EdorItemGrid.getSelNo()-1;
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫɾ������Ŀ��");
        return false;
    }

    document.all('Transact').value = "DELETE||EDORITEM";
    fm.fmtransact.value = "DELETE||EDORITEM";
    document.all("DelFlag").value="GrpEdorItem";

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./PEdorAppCancelSubmit.jsp";
    document.getElementById("fm").submit();

}

/*********************************************************************
 *  MulLine��RadioBox����¼�����ʾ��Ŀ��ϸ��ť
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getEdorItemDetail()
{
    var tSelNo = EdorItemGrid.getSelNo()-1;
    
    fm.EdorNo.value =       EdorItemGrid.getRowColData(tSelNo, 2);
    fm.EdorType.value =     EdorItemGrid.getRowColData(tSelNo, 21);
    fm.EdorTypeCal.value =     EdorItemGrid.getRowColData(tSelNo, 4);
    fm.EdorTypeName.value = EdorItemGrid.getRowColData(tSelNo, 24);
    fm.ContNo.value =       EdorItemGrid.getRowColData(tSelNo, 7);   
    fm.InsuredNo.value =    EdorItemGrid.getRowColData(tSelNo, 8);
    fm.PolNo.value =        EdorItemGrid.getRowColData(tSelNo, 9);
    fm.EdorItemAppDate.value= EdorItemGrid.getRowColData(tSelNo, 10);
    fm.EdorValiDate.value = EdorItemGrid.getRowColData(tSelNo, 11);
    fm.AppReasonName.value =EdorItemGrid.getRowColData(tSelNo, 12);
    fm.AppReason.value =    EdorItemGrid.getRowColData(tSelNo, 13);
    fm.MakeDate.value =     EdorItemGrid.getRowColData(tSelNo, 15);
    fm.MakeTime.value =     EdorItemGrid.getRowColData(tSelNo, 16);
    fm.EdorItemState.value =EdorItemGrid.getRowColData(tSelNo, 20);
    
    fm.EdorTypeCalName.value = EdorItemGrid.getRowColData(tSelNo, 25);
    fm.CustomerNoBak.value = fm.InsuredNo.value;
    fm.ContNoBak.value = fm.ContNo.value; 
   
}
/*********************************************************************
 *  ��ȫ����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function edorAppConfirm()
{

//========add========liuxiaosong========2006-10-27======start=======
  var grpcontno=fm.ContNoApp.value;//���屣����
  
  	var sqlid824112624="DSHomeContSql824112624";
var mySql824112624=new SqlClass();
mySql824112624.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824112624.setSqlId(sqlid824112624);//ָ��ʹ�õ�Sql��id
mySql824112624.addSubPara(grpcontno);//ָ������Ĳ���
var QuerySQL=mySql824112624.getString();
		  
//    var QuerySQL="select prtno from lcgrpcont where grpcontno = '" + grpcontno + "'";
    var brr = easyExecSql(QuerySQL, 1, 0,"","",1);
    var PrtNo = brr[0][0];//ӡˢ��prtNo
    fm.PrtNo.value=(PrtNo);

		var sqlid824112716="DSHomeContSql824112716";
var mySql824112716=new SqlClass();
mySql824112716.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824112716.setSqlId(sqlid824112716);//ָ��ʹ�õ�Sql��id
mySql824112716.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
QuerySQL=mySql824112716.getString();
		
//		QuerySQL="select distinct 1 from ljsgetendorse where endorsementno = '" + fm.EdorNo.value + "'";
		var arr = easyExecSql(QuerySQL, 1, 0, 1);
		var tMFlag = "0";
		if(arr!=null)
		{
			tMFlag = arr[0][0];
		}
		//alert(tMFlag);
		//return;
//========add========liuxiaosong========2006-10-27======end=======
    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
    document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";

        if(fm.GetPayForm.value == null || fm.GetPayForm.value == "")
        {
           alert("�Բ�������û��¼���ո��ѷ�ʽ!");
           return;
        }
        
        if(fm.GetPayForm.value=="1" &&tMFlag =="1")
        {
        	if(!confirm("�ո��ѷ�ʽΪ�ֽ�!ȷ������ȷ�ϣ�")){
                return;
              }
      	}
        if(fm.GetPayForm.value=="4" || fm.GetPayForm.value=="7")
        {
            //���л��������֧��
            if(fm.BankCode.value == null || fm.BankCode.value == ""
             || fm.BankAccNo.value == null || fm.BankAccNo.value == ""
             || fm.AccName.value == null || fm.AccName.value == "")
          {
                if(!confirm("�ո�����Ϣ¼�벻����!ȷ������ȷ�ϣ�")){
                return;
              }
          }

        }
        if(fm.PayGetName.value == null || fm.PayGetName.value == "")
        {
            alert("��¼�벹�˷���ȡ��!");
            return;
        }


        var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

        fm.action= "./GEdorAcptAppConfirmSubmit.jsp";
        document.getElementById("fm").submit();
   
}


/*********************************************************************
 *  ��ȫ�����޸�ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ApproveModifyConfirm()
{
    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
    document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";

        var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

        fm.action= "./GEdorAcptAppConfirmSubmit.jsp";
        document.getElementById("fm").submit();

}

/*********************************************************************
 *  ��ȫ���뱣��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function applySave()
{
    var tOtherNo = document.all('OtherNo').value.trim();
    
    
    var tOtherNoType = document.all('OtherNoType').value;
        if(tOtherNoType == null || tOtherNoType == "")
        {
            alert("������������ͣ�");
            return;
        }
        if(tOtherNo == null || tOtherNo == "")
        {
            if(tOtherNoType == "2")
            {
                alert("����������ͻ����룡");
                return;
            }
            if(tOtherNoType == "4")
            {
                alert("���������屣�����룡");
                return;
            }
        }
        if(tOtherNoType == "2")
        {
            displayCustomerInfo(tOtherNo);
            if(fm.CustomerNo.value == null || fm.CustomerNo.value == "")
            {
                alert("����ͻ��Ų�����!");
                return;
            }
            if (fm.EdorAppName.value == null || fm.EdorAppName.value == "")
       		 {
            	if (fm.LoadFlag.value != "edorTest")
           		 {
                	alert("��¼������������!");
               	 return;
            	}
        	}
        }
        if(tOtherNoType == "4")
        {
            displayContInfo(tOtherNo);
            if(fm.ContNoApp.value == null || fm.ContNoApp.value == "")
            {
                alert("���屣���Ų�����!");
                return;
            }
        }

    fm.fmtransact.value = "INSERT||EDORAPP";

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  ��ѯ��ȫ��Ŀ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getEdorItemGrid()
{
    initEdorItemGrid();
    var tEdorAcceptNo = document.all('EdorAcceptNo').value;
    var tOtherNoType = document.all('OtherNoType').value;

    if(tEdorAcceptNo!=null)
    {

        if (tOtherNoType=='4') //���屣��������
        {
            //var strSQL =  " select EdorAcceptNo, EdorNo, "
            //            + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype) and appobj in ('G', 'A') ), "
            //            + " DisplayType, "
            //            + " GrpContNo, '', '', '', EdorAppDate, EdorValiDate, "
            //            + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, "
            //            + " GetMoney, MakeDate, MakeTime, ModifyDate, Operator, "
            //            + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"
            //            + "EdorState,EdorType "
            //            + " from LPGrpEdorItem "
            //            + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
            //            + " order by makedate asc, maketime asc";
                        //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";
                        
          var sqlid824112917="DSHomeContSql824112917";
var mySql824112917=new SqlClass();
mySql824112917.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824112917.setSqlId(sqlid824112917);//ָ��ʹ�õ�Sql��id
mySql824112917.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
var strSQL=mySql824112917.getString();
          
//          var strSQL =  " select EdorAcceptNo, EdorNo, " 
//	        			+ " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype) and appobj in ('G', 'A')) , " 
//	        			+ "EdorTypeCal,"
//	        			+ " DisplayType, "
//	                    + " GrpContNo, '', '', '', EdorAppDate, EdorValiDate, "
//	                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, " 
//	                    + " GetMoney, MakeDate, MakeTime, ModifyDate, Operator, "
//	                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))," 
//	                    + "EdorState,EdorType,edorreasoncode,edorreason,"
//	                    + "(select edorname from lmedoritem where edorcode=lpgrpedoritem.edortype and appobj='G'),"
//	                    + "(select codename from ldcode where codetype='edortypecal' and code=lpgrpedoritem.edortypecal)" 
//	                    + " from LPGrpEdorItem " 
//	                    + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
//	                    + " order by makedate asc, maketime asc";
	                    //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";              

            //turnPage.queryModal(strSQL, EdorItemGrid);
            arrResult = easyExecSql(strSQL);
            if (arrResult)
            {
                displayMultiline(arrResult,EdorItemGrid);
            }
        }
        else if (tOtherNoType=='3') //����ͻ�������
        {
            var sqlid824112959="DSHomeContSql824112959";
var mySql824112959=new SqlClass();
mySql824112959.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824112959.setSqlId(sqlid824112959);//ָ��ʹ�õ�Sql��id
mySql824112959.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
var strSQL=mySql824112959.getString();

            
//            var strSQL =  " select distinct EdorAcceptNo, '', "
//                        + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), "
//                        + " DisplayType, '', '000000', InsuredNo, PolNo, EdorAppDate, EdorValiDate, "
//                        + " (select CodeName from LDCode where codetype = 'edorappreason' and trim(code) = trim(appreason)), "
//                        + " appreason, '', MakeDate, MakeTime, '', Operator, "
//                        + " (select CodeName from LDCode where codetype = 'edorstate' and trim(code) = trim(edorstate)),"
//                        + " EdorState, EdorType "
//                        + " from LPEdorItem "
//                        + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
//                        + " order by makedate asc, maketime asc";
                        //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";

            //turnPage.queryModal(strSQL, EdorItemGrid);
            arrResult = easyExecSql(strSQL);
            if (arrResult)
            {
                displayMultiline(arrResult,EdorItemGrid);
            }
        }
        showDivGetPayForm();//��ʾ�ո��ѷ�ʽ��Ϣ
    }
}

/*********************************************************************
 *  �س�ʵ�ֲ�ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function QueryOnKeyDown()
{
    if (hasSaved == "Y")
    {
        return;
    }

        var keycode;
        if(mflag == "0" || mflag == "1")
        {
            keycode ="13";
            mflag = "";
        }
        else
        {
            keycode = event.keyCode;
        }

    //�س���ascii����13
    if(keycode=="13")
    {
        var tOtherNoType = fm.OtherNoType.value;
        var tOtherNo = fm.OtherNo.value;

        if(tOtherNoType == null || tOtherNoType == "")
        {
            alert("������������ͣ�");
            return;
        }
        if(tOtherNo == null || tOtherNo == "")
        {
            if(tOtherNoType == "2")
            {
                alert("����������ͻ����룡");
                return;
            }
            if(tOtherNoType == "4")
            {
                alert("���������屣�����룡");
                return;
            }
        }
        if(tOtherNoType == "2")
        {
            displayCustomerInfo(tOtherNo);
            divCustomer.style.display='';       //�ͻ���Ϣ
            divCont.style.display='none';       //������Ϣ
            if(fm.CustomerNo.value == null || fm.CustomerNo.value == "")
            {
                alert("����ͻ��Ų�����!");
            }
        }
        if(tOtherNoType == "4")
        {
            displayContInfo(tOtherNo);
            divCustomer.style.display='none';   //�ͻ���Ϣ
            divCont.style.display='';           //������Ϣ
            if(fm.ContNoApp.value == null || fm.ContNoApp.value == "")
            {
                alert("���屣���Ų�����!");
            }
        }
    }
}

/*********************************************************************
 *  �ͻ���ѯ
 *  ����: �ͻ���ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function customerQuery()
{
  mflag = "1";
    var showInfo = window.open( "../sys/LDPersonQueryNew.html","LDPersonQueryNew",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    showInfo.focus();
}

/*********************************************************************
 *  ������ѯ
 *  ����: ������ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function contQuery()
{
    mflag = "0";
    var showInfo = window.open("../sys/AllProposalQueryMain.jsp","AllProposalQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    showInfo.focus();
}
/*********************************************************************
 *  ���屣����ѯ
 *  ����: ���屣����ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 * Add By QianLy on 2006-10-18
 *********************************************************************
 */
function showContDetail(){
      var tPrtNo = null;
      var tGrpContNo = document.all('OtherNo').value;
      
      var sqlid824113106="DSHomeContSql824113106";
var mySql824113106=new SqlClass();
mySql824113106.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824113106.setSqlId(sqlid824113106);//ָ��ʹ�õ�Sql��id
mySql824113106.addSubPara(tGrpContNo);//ָ������Ĳ���
var sql=mySql824113106.getString();
      
//      var sql = "select prtno from lcgrpcont where 1 = 1 and appflag = '1' and grpcontno ='"+tGrpContNo+"'";
      var brr = easyExecSql(sql);
      if(brr)
      {
        brr[0][0]==null||brr[0][0]=='null'?'0':tPrtNo = brr[0][0];
    }
      var showInfo = window.open("../sys/GrpPolDetailQueryMain.jsp?GrpContNo="+tGrpContNo+"&PrtNo="+tPrtNo,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    showInfo.focus();
}

/*********************************************************************
 *  ���屣�������嵥��ѯ
 *  ����: �����嵥��ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 * Add By QianLy on 2006-10-18
 *********************************************************************
 */
function showNameList(){
      var tGrpContNo = document.all('OtherNo').value;
      var showInfo = window.open("../sys/GrpPolSingleQueryMain.jsp?GrpContNo="+tGrpContNo,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    showInfo.focus();
}
/*********************************************************************
 * Add By QianLy on 2006-10-18
 * Ϊ�˼��ݼ��屣����ѯҳ��
 *********************************************************************
 */
 function easyQueryClick(){
}

function afterQuery( arrQueryResult ) {

    if( arrQueryResult != null ) {
        if(mflag == "1") {
           try { document.all('OtherNo').value = arrQueryResult[0][0]; } catch(ex) { };
           try { document.all('OtherNoType').value = "1"; } catch(ex) { };
        }
      else{
         try { document.all('OtherNo').value = arrQueryResult[0][0]; } catch(ex) { };
           try { document.all('OtherNoType').value = "3"; } catch(ex) { };
      }

        QueryOnKeyDown();
    }

}

function ContStateQuery()
{
	var sqlid824113213="DSHomeContSql824113213";
	var mySql824113213=new SqlClass();
		mySql824113213.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql824113213.setSqlId(sqlid824113213);//ָ��ʹ�õ�Sql��id
		mySql824113213.addSubPara(document.all('ContNoApp').value);//ָ������Ĳ���
	var strSql=mySql824113213.getString();
   
//   strSql = "select statetype,state from lccontstate where contno='"+document.all('ContNoApp').value+"' and (statetype='Lost' or statetype='Loan'or statetype='BankLoan')";
   var arrResult = easyExecSql(strSql, 1, 0);
   if (arrResult == null)
   {
       //alert("û����Ӧ�ı�����Ϣ");
   }
   else
   {

    //document.all('Available').value = arrResult[0][2];
    //document.all('Terminate').value = arrResult[0][1];
     // document.all('Lost').value = arrResult[0][1];

    //document.all('PayPrem').value = arrResult[0][3];
    //  document.all('Loan').value = arrResult[1][1];
    //  document.all('BankLoan').value = arrResult[2][1];
    //document.all('RPU').value = arrResult[0][6];
   }
}

/*********************************************************************
 *  ����
 *  ����: ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function returnParent()
{
    //top.opener.initSelfGrid();
    try
    {
        top.close();
        //add by jiaqiangli 2009-03-27 �ŵ���ȫ����
        top.opener.easyQueryClickSelf();
        top.opener.focus();
    }
    catch (ex) {}
}
/*********************************************************************
 *  �����ո��ѷ�ʽ��Ϣ����
 *  ����: �����ո��ѷ�ʽ��Ϣ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDivGetPayForm()
{
    if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp" || fm.LoadFlag.value == "approveModify")
    {
        var nCountNo = EdorItemGrid.mulLineCount;
        if(nCountNo > 0)
        {
          //ֻ������˱�ȫ��Ŀʱ���ո���¼�������Ż����
          divGetPayFormInfo.style.display='';      //�ո��ѷ�ʽ��Ϣ
          initGetPayForm();                      //��ʼ���ո��ѷ�ʽ��Ϣ
        }
        else
        {
            divGetPayFormInfo.style.display='none';   //�ո��ѷ�ʽ��Ϣ
        }

    }
    else
    {
        divGetPayFormInfo.style.display='none';   //�����ո��ѷ�ʽ��Ϣ
    }
}
/*********************************************************************
 *  ��ʼ���ո��ѷ�ʽ��Ϣ
 *  ����: ��ʼ���ո��ѷ�ʽ��Ϣ����ÿ�γ�ʼ��������fm.GetPayForm�õ�����ʱ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initGetPayForm()
{
   var tGetPayForm = fm.GetPayForm.value;
   if(tGetPayForm == null || tGetPayForm == "")
   {
      fm.GetPayForm.value = "1";        // Ĭ��Ϊ�ֽ�ʽ
      fm.GetPayFormName.value = "�ֽ�";
   }
   if(tGetPayForm == "4"||tGetPayForm == "7")//���л���,����֧��
   {
      divBankInfo.style.display='';
      if(fm.BankCode.value == null || fm.BankCode.value == "")
      {
          //�����δ¼������е���Ϣ����ȡĬ��ֵ
          if(fm.OtherNoType.value == "2")
          {
              //�ͻ�����
              fm.BankCode.value = "";
              fm.BankName.value = "";
              fm.BankAccNo.value = "";
              fm.AccName.value = "";
          }
          else
          {
            var strsql = "";

            //ȡͶ����λ�ʺ�
            var sqlid824113313="DSHomeContSql824113313";
var mySql824113313=new SqlClass();
mySql824113313.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824113313.setSqlId(sqlid824113313);//ָ��ʹ�õ�Sql��id
mySql824113313.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
strsql=mySql824113313.getString();
            
//            strsql = "select bankcode,(select bankname from ldbank where bankcode = a.bankcode and trim(ComCode) = substr(a.ManageCom, 0, 6)),bankaccno,accname from lcgrpcont a where grpcontno = '"+fm.OtherNo.value+"'";

            var brr = easyExecSql(strsql);
            if(brr)
            {
               brr[0][0]==null||brr[0][0]=='null'?'0':fm.BankCode.value  = brr[0][0];
               brr[0][1]==null||brr[0][1]=='null'?'0':fm.BankName.value = brr[0][1];
               brr[0][2]==null||brr[0][2]=='null'?'0':fm.BankAccNo.value = brr[0][2];
               brr[0][3]==null||brr[0][3]=='null'?'0':fm.AccName.value = brr[0][3];
            }
          }
      }
   }
   else
   {
      divBankInfo.style.display='none';
      fm.BankCode.value = "";
      fm.BankName.value = "";
      fm.BankAccNo.value = "";
      fm.AccName.value = "";
   }
//===add===zhangtao====2007-01-29============�����ո�����ȡ��Ĭ��ȡֵ=========BGN=============
   //�����ȫ����״̬�Ѿ�����ȷ�ϣ������ٸ���Ĭ��ֵ
     if (fm.PEdorState.value != 2)
     {
           fm.PayGetName.value = fm.GrpName.value;
           fm.PersonID.value  = '';
           var AGFlag = 0;
           for (i = 0; i < EdorItemGrid.mulLineCount; i++)
           {
               if (EdorItemGrid.getRowColData(i, 20) == 'AG')
               {
                   AGFlag = 1;
                   break;
               }
           }
           if (AGFlag == 1) //
           {
                    fm.PayGetName.value = fm.EdorAppName.value;
                    fm.PersonID.value  = '';
                    
                  var sqlid824113414="DSHomeContSql824113414";
var mySql824113414=new SqlClass();
mySql824113414.setResourceName("bq.GEdorAppInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824113414.setSqlId(sqlid824113414);//ָ��ʹ�õ�Sql��id
mySql824113414.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
var sql=mySql824113414.getString();
                  
//                  var sql = " select i.name, i.idno from lpedoritem e, lcinsured i where e.contno = i.contno and e.insuredno = i.insuredno and edoracceptno = '" + fm.EdorAcceptNo.value + "' and edortype = 'AG' ";
                  var brr = easyExecSql(sql);
                  if(brr)
                  {
                        if (brr.length == 1) //������ȡ
                        {
                        brr[0][0]==null||brr[0][0]=='null'?'0':fm.PayGetName.value  = brr[0][0];
                        brr[0][0]==null||brr[0][0]=='null'?'0':fm.PersonID.value    = brr[0][1];
                        }
                }
           }
        }
//===add===zhangtao====2007-01-29============�����ո�����ȡ��Ĭ��ֵ=========END=============

}

function toManyAcc()
{
	var tEdorType = document.all('EdorType').value;
	//alert(tEdorType);
	if(tEdorType == null || tEdorType == "")
	{
		alert("��ѡ��ȫ��ϸ����ӱ�ȫ��Ŀ!");
		return;
	}
	if(tEdorType == "AZ" || tEdorType == "AT"|| tEdorType == "AX")
	{
		window.open("./SubAccPay.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  
	}else {
		alert("�ñ�ȫ��Ŀ����¼����˻�������Ϣ");
		return;
	}
  
}

function UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("./EdorGrpUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function ApproveQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("./EdorGrpApproveQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}