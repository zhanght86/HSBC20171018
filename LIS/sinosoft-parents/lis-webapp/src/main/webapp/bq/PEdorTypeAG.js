//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var theFirstValue="";
var theSecondValue="";
var str_sql = "",sql_id = "", my_sql = "";
//<!-- XinYQ added on 2005-12-08 : BGN -->
/*============================================================================*/

/**
 * showCodeList �Ļص�����, �����ʻ���Ϣ¼������ʾ������
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "GetLocation")
    {
        try
        {
            if (oCodeListField.value == "0")
                document.all("BankInfo").style.display = "";
            else
                document.all("BankInfo").style.display = "none";
        }
        catch (ex)
        {
            alert("���棺�����ʻ���Ϣ¼����ʾ/���س����쳣�� ");
        }
    } //CodeListType == LiveInquiryResult
}

/*============================================================================*/

/**
 * ��ѯ����������Ϣ
 */
function queryPolInfo()
{
    var QuerySQL, arrResult;
//    QuerySQL = "select a.RiskCode, "
//             +        "(select RiskName "
//             +           "from LMRisk "
//             +          "where RiskCode = a.RiskCode), "
//             +        "a.CValiDate, "
//             +        "a.PayToDate, "
//             +        "a.Prem "
//             +   "from LCPol a "
//             +  "where 1 = 1 "
//             +  getWherePart("a.ContNo", "ContNo")
//             +  getWherePart("a.PolNo", "PolNo");
    //alert(QuerySQL);
    sql_id = "PEdorTypeAGSql1";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    my_sql.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
    my_sql.addSubPara(window.document.getElementsByName("PolNo")[0].value);//ָ������Ĳ���
    QuerySQL = my_sql.getString();
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ����������Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
            document.getElementsByName("RiskName")[0].value = arrResult[0][1];
            document.getElementsByName("CValiDate")[0].value = arrResult[0][2];
            document.getElementsByName("PayToDate")[0].value = arrResult[0][3];
            document.getElementsByName("Prem")[0].value = arrResult[0][4];
        }
        catch (ex) {}
    } //arrResult != null
}

/*============================================================================*/

function saveEdorTypeAG()
{
    //<!-- XinYQ added on 2005-12-19 : END -->
    if (!verifyInput2()) return;
    if (!checkBankInfo()) return;
    if (!checkAnnuityRisk())  return;
    //<!-- XinYQ added on 2005-12-19 : END -->


    //alert('��δ���ӵ���̨����......');
    if (PolGrid.mulLineCount == 0)
    {
        alert("Ŀǰ��û����ȡ��Ŀ�� ");
        return;
    }
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
	
    document.forms(0).action = "PEdorTypeAGSubmit.jsp";
    document.forms(0).submit();
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
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            top.opener.getEdorItemGrid();
            queryBackFee();
        }
        catch (ex) {}
    }
}


//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
    try
    {
        initForm();
    }
    catch (ex) {}
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
    alert("query click");
      //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}
//---------------------------
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if( cDebug == "1" )
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}
function showBankDiv()
{
    if(document.all('GoonGetMethod').value=='0')
    {
        BankInfo.style.display = "";
        //document.all('Bank').value="";
        //document.all('Account').value="";
        //document.all('AccName').value="";
    }
    if(document.all('GoonGetMethod').value=='1' || document.all('GoonGetMethod').value=='2' )
    {
        BankInfo.style.display = "none";

    }
}


//<!-- XinYQ modified on 2005-11-08 : BGN -->
/*============================================================================*/

/*
 * ������ȡ��ʽ-����ת��-�����˻���Ϣ��ѯ
 */
function queryBankInfo()
{
    var QuerySQL, arrResult;
//    QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
//             +   "from LPPol "
//             +  "where 1 = 1 "
//             +  getWherePart("EdorType", "EdorType")
//             +  getWherePart("EdorNo", "EdorNo")
//             +  getWherePart("ContNo", "ContNo")
//             +  getWherePart("PolNo", "PolNo");
    sql_id = "PEdorTypeAGSql2";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    my_sql.addSubPara(window.document.getElementsByName("EdorType")[0].value);//ָ������Ĳ���
    my_sql.addSubPara(window.document.getElementsByName("EdorNo")[0].value);//ָ������Ĳ���
    my_sql.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
    my_sql.addSubPara(window.document.getElementsByName("PolNo")[0].value);//ָ������Ĳ���
    QuerySQL = my_sql.getString();
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ͻ������˻���Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null && trim(arrResult[0][0]) != "")
    {
        try
        {
            //��ȡ��ʽ
            document.getElementsByName("GoonGetMethod")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GoonGetMethod','GoonGetMethodName');
            //��������
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //�����ʻ�
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //�ʻ���
            if (trim(arrResult[0][3]) != "")
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            else
                document.getElementsByName("GetAccName")[0].value = document.getElementsByName("InsuredName")[0].value;
        }
        catch (ex)
        {
            alert("���󣺲�ѯ�ͻ������˻���Ϣ�ɹ�, ����ʾ��ҳ��ʧ�ܣ� ");
            return;
        }
    } //arrResult[0][1]) != ""
    //��ѯ LCPol
    else
    {
//        QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
//                 +   "from LCPol "
//                 +  "where 1 = 1 "
//                 +  getWherePart("ContNo", "ContNo")
//                 +  getWherePart("PolNo", "PolNo");
        sql_id = "PEdorTypeAGSql3";
        my_sql = new SqlClass();
        my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
        my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
        my_sql.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
        my_sql.addSubPara(window.document.getElementsByName("PolNo")[0].value);//ָ������Ĳ���
        QuerySQL = my_sql.getString();
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ�ͻ������˻���Ϣ�����쳣�� ");
            return;
        }
        try
        {
            //��ȡ��ʽ
            document.getElementsByName("GoonGetMethod")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GoonGetMethod','GoonGetMethodName');
            //��������
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //�����ʻ�
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //�ʻ���
            if (trim(arrResult[0][3]) != "")
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            else
                document.getElementsByName("GetAccName")[0].value = document.getElementsByName("InsuredName")[0].value;
        }
        catch (ex)
        {
            alert("���󣺲�ѯ�ͻ������˻���Ϣ�ɹ�, ����ʾ��ҳ��ʧ�ܣ� ");
            return;
        }
    } //arrResult[0][1]) == ""
    //�����ʾ
    if (arrResult != null && trim(arrResult[0][0]) == "0")
        try { document.all("BankInfo").style.display = ""; } catch (ex) {}
    else
        try { document.all("BankInfo").style.display = "none"; } catch (ex) {}
}

/*============================================================================*/

/*
 * ������ȡ��ʽ-����ת��-�����˻���Ϣ��¼У��
 */
function checkBankInfo()
{
    var sGetForm, sGetBankCode, sGetBankAccNo, sGetAccName;
    try
    {
        sGetForm = document.getElementsByName("GoonGetMethod")[0].value;
        sGetBankCode = document.getElementsByName("GetBankCode")[0].value;
        sGetBankAccNo = document.getElementsByName("GetBankAccNo")[0].value;
        sGetAccName = document.getElementsByName("GetAccName")[0].value;
    }
    catch (ex)
    {
        alert("���棺�޷���ȡ������ȡ��ʽ�������˻���Ϣ�� ");
        return false;
    }
    if (sGetForm == "0")
    {
        if (sGetBankCode == null || trim(sGetBankCode) == "" || sGetBankAccNo == null || trim(sGetBankAccNo) == "" || sGetAccName == null || trim(sGetAccName) == "")
        {
            alert("������ȡ��ʽΪ����ת��, ����¼�뿪�����С������ʻ����ʻ����� ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/*
 * ����ձ���¼��������ȡ��ʽ
 */
function checkAnnuityRisk()
{
    var CanBeBlank = true;
    var sGetForm;
    try
    {
        sGetForm = document.getElementsByName("GoonGetMethod")[0].value;
    }
    catch (ex) {}
    if (sGetForm == null || trim(sGetForm) == "")
    {
        var nMulLineCount;
        try
        {
            nMulLineCount = PolGrid.mulLineCount;
        }
        catch (ex) {}
        if (nMulLineCount == null || nMulLineCount <= 0)
        {
            //alert("û����ȡ��Ŀ�� ");
            return false;
        }
        else
        {
            var sContNo = document.getElementsByName("ContNo")[0].value;
            for (var i = 0; i < nMulLineCount; i++)
            {
                var sGetDutyType = PolGrid.getRowColData(i, 7);
                if (sGetDutyType == "1")    //�����
                {
                    //���������޸�ΪУ�����ķ����һ��
                    var sGetDutyCode = PolGrid.getRowColData(i, 6);
//                    var QuerySQL = "select 'X' "
//                                 +   "from LCGet "
//                                 +  "where ContNo = '" + sContNo + "' "
//                                 +    "and GetIntv <> 0 "
//                                 +    "and GetToDate < add_months(GetEndDate, 11) "
//                                 +    "and GetDutyCode = '" + sGetDutyCode + "'";
                    var QuerySQL = "";
                    sql_id = "PEdorTypeAGSql4";
                    my_sql = new SqlClass();
                    my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
                    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
                    my_sql.addSubPara(sContNo);//ָ������Ĳ���
                    my_sql.addSubPara(sGetDutyCode);//ָ������Ĳ���
                    QuerySQL = my_sql.getString();
                    var arrResult;
                    try { arrResult = easyExecSql(QuerySQL, 1, 0); }
                    catch (ex)
                    {
                        alert("���棺��ѯ�����Ƿ��������쳣�� ");
                        return false;
                    }
                    if (arrResult != null)
                    {
                        CanBeBlank = false;
                        break;
                    }
                } //sGetDutyType == "1"
            } //loop for
        } //nMulLineCount > 0
    } //sGetForm == null
    if (!CanBeBlank)
    {
        try { document.getElementsByName("GoonGetMethod")[0].className = "warnno" } catch (ex) {}
        alert("�����һ�ڵ�������¼��������ȡ��ʽ�� ");
        return false;
    }
    //��������ڽ���߲�������յ����һ��
    try { document.getElementsByName("GoonGetMethod")[0].className = "codeno" } catch (ex) {}
    return true;
}

/*============================================================================*/
//<!-- XinYQ modified on 2005-11-08 : END -->


//��֤�ı��������������ֵ�Ƿ����
function confirmSecondInput1(aObject,aEvent){
    if(aEvent=="onkeyup"){
      var theKey = window.event.keyCode;
      if(theKey=="13"){
        if(theFirstValue!=""){
        theSecondValue = aObject.value;
          if(theSecondValue==""){
            alert("���ٴ�¼�룡");
            aObject.value="";
            aObject.focus();
            return;
          }
          if(theSecondValue==theFirstValue){
            aObject.value = theSecondValue;
            return;
          }
          else{
            alert("����¼����������������¼�룡");
            theFirstValue="";
            theSecondValue="";
            aObject.value="";
            aObject.focus();
            return;
          }
        }
      else{
        theFirstValue = aObject.value;
        if(theFirstValue==""){
            theSecondValue="";
          alert("��¼�����ݣ�");
          return;
        }
        aObject.value="";
        aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
      //alert("theFirstValue="+theFirstValue);
      if(theFirstValue!=""){
        theSecondValue = aObject.value;
        if(theSecondValue==""){
            alert("���ٴ�¼�룡");
          aObject.value="";
          aObject.focus();
          return;
        }
        if(theSecondValue==theFirstValue){
          aObject.value = theSecondValue;
          theSecondValue="";
          theFirstValue="";
          return;
        }
        else{
          alert("����¼����������������¼�룡");
          theFirstValue="";
          theSecondValue="";
          aObject.value="";
          aObject.focus();
          return;
        }
      }
    else{
        theFirstValue = aObject.value;
        theSecondValue="";
        if(theFirstValue==""){
        //alert("aa");
          return;
        }
        aObject.value="";
        aObject.focus();
      return;
    }
  }
}
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}

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

//�����ȡ��ʽ��������������ʻ���Ϣ����
//function formatGetModeAbout()
//{
//  var tSql="SELECT GetForm,GetBankCode,GetBankAccNo,GetAccName"
//              + " FROM ((select '1' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//                           + " from LPPol"
//                           + " where EdorNo='" + document.all('EdorNo').value + "' and EdorType='" + document.all('EdorType').value + "' and PolNo='" + document.all('PolNo').value + "')"
//                          + " union"
//                          + " (select '2' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//                           + " from LCPol"
//                           + " where PolNo='" + document.all('PolNo').value + "'))"
//              + " WHERE rownum=1"
//              + " ORDER BY flag";
//  //alert(tSql);
//  var arrResult=easyExecSql(tSql,1,0);
//  try{document.all('GoonGetMethod').value= arrResult[0][0];}catch(ex){};
//  showOneCodeName('getlocation','GoonGetMethod','GoonGetMethodName');
//  try{document.all('GetBankCode').value= arrResult[0][1];}catch(ex){};
//  showOneCodeName('bank','GetBankCode','BankName');
//  try{document.all('GetBankAccNo').value= arrResult[0][2];}catch(ex){};
//  try{document.all('GetAccName').value= arrResult[0][3];}catch(ex){};
//  if (document.all('GoonGetMethod').value == '0')
//  {
//      BankInfo.style.display = "";
//  }
//  else
//  {
//      BankInfo.style.display = "none";
//  }
//}

function QueryEdorInfo()
{
    var tEdortype=top.opener.document.all('EdorType').value;
    var strSQL;
    if(tEdortype!=null || tEdortype !='')
    {
//       strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
       sql_id = "PEdorTypeAGSql5";
       my_sql = new SqlClass();
       my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
       my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
       my_sql.addSubPara(tEdortype);//ָ������Ĳ���
       strSQL = my_sql.getString();
    }
    else
    {
        alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
    }
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //ְҵ���
}

function Edorquery()
{
   try{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }

    var ButtonFlag2 = top.opener.top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag2!=null && ButtonFlag2=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
  }catch(ex){};
}

function queryCustomerInfo()
{
    var tContNo=document.all('ContNo').value;
    var strSQL=""
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
//      strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
//                            +"CONTNO='"+tContNo+"'";
      sql_id = "PEdorTypeAGSql6";
      my_sql = new SqlClass();
      my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
      my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
      my_sql.addSubPara(tContNo);//ָ������Ĳ���
      strSQL = my_sql.getString();
    //alert("-----------"+strSQL+"------------");
    }
    else
    {
        alert('û�пͻ���Ϣ��');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
    if(!turnPage.strQueryResult)
    {
        alert("��ѯʧ��");
    }
    else
    {
        arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
        try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //Ͷ��������
        try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //Ͷ����֤������
        try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //Ͷ����֤������
        try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //����������
        try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //������֤������
        try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //������֤������

        showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
        showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
    }
}

function queryPolGrid()
{
    //��ʾ���ϱ�׼����ȡ����Ϣ
//    var strSql="SELECT distinct (select distinct GetDutyName from LMDutyGetAlive where GetDutyCode=b.GetDutyCode and GetDutyKind=b.GetDutyKind),"
//                    + " (select count(*) + 1 from LJAGetDraw where PolNo=b.PolNo and DutyCode=b.DutyCode and GetDutyKind=b.GetDutyKind and GetDutyCode=b.GetDutyCode),"
//                    + " b.GetMoney,b.GetNoticeNo,b.DutyCode,b.GetDutyCode,b.GetDutyKind,b.RiskCode,"
//                    + " (case when (select (case GetType1 when '0' then '���ڽ�' when '1' then '���' else 'δ֪' end) from LMDutyGet where GetDutyCode=b.GetDutyCode) is not null then (select (case GetType1 when '0' then '���ڽ�' when '1' then '���' else 'δ֪' end) from LMDutyGet where GetDutyCode=b.GetDutyCode) else '�޼�¼' end)"
//            + " FROM LJSGetDraw b"
//            + " WHERE b.PolNo='" + document.all('PolNo').value + "'"
//            + " and (b.RReportFlag='1' or b.ComeFlag='1')"
//            + " and b.GetDate<=to_date('" + document.all('EdorValiDate').value + "','YYYY-MM-DD')"
//            //+ " and not exists(select 'X' from LCPol where PolNo=b.PolNo and GetForm='1')"
//                        + " and not exists(select 'A' from LCContState where ContNo=b.ContNo and StateType='Loan' and State='1' and EndDate is null and StartDate<=b.GetDate)"
//                        + " and not exists(select 'B' from LCContState where PolNo='" + document.all('PolNo').value + "' and StateType='PayPrem' and State='1' and EndDate is null and StartDate<=b.GetDate)";
    var strSql="";
    sql_id = "PEdorTypeAGSql7";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    my_sql.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
    my_sql.addSubPara(document.all('EdorValiDate').value);//ָ������Ĳ���
    my_sql.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
    strSql = my_sql.getString();
    //alert(strSql);
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult)
    {
        return false;
    }
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid = PolGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL;
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    //������ȡ���
    var sumGetMoney = 0; //��ȡ���ĺ�
    for (var i = 0; i < PolGrid.mulLineCount; i++)
    {
        sumGetMoney = sumGetMoney + eval(PolGrid.getRowColData(i, 3));
    }
    document.all('MoneyAdd').value = sumGetMoney;
}

function queryBonusGrid()
{
    //��ʾ���ϱ�׼����ȡ����Ϣ
//    var strSql="SELECT  b.GetDate,b.BonusYear,b.GetMoney,'����'"
//            + " FROM ljabonusget b"
//            + " WHERE b.otherno='" + document.all('PolNo').value + "'"
//            + " and b.GetDate<=to_date('" + document.all('EdorValiDate').value + "','YYYY-MM-DD')"
//            + " and Enteraccdate is null and Confdate is null and state='0'";
    //alert(strSql);
    var strSql="";
    sql_id = "PEdorTypeAGSql8";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //ָ��ʹ�õ�properties�ļ���
    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    my_sql.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
    my_sql.addSubPara(document.all('EdorValiDate').value);//ָ������Ĳ���
    strSql = my_sql.getString();
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult)
    {
        return false;
    }
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid = BonusGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL;
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

    //������ȡ���
    var sumGetBonusMoney = 0; //��ȡ���ĺ�
    for (var i = 0; i < BonusGrid.mulLineCount; i++)
    {
            sumGetBonusMoney = sumGetBonusMoney + eval(BonusGrid.getRowColData(i, 3));
    }
    document.all('MoneyAdd').value = parseFloat(document.all('MoneyAdd').value) + sumGetBonusMoney;
    if (BonusGrid.mulLineCount > 0)
    {
        try
        {
            document.all("BonusInfo").style.display = "";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("BonusInfo").style.display = "none";
        }
        catch (ex) {}
    }
}
