/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-07-04, 2006-12-05
 * @direction: �ŵ���ȫ�˹��˱����������ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();               //ȫ�ֱ���, ��ҳ������
var turnPageInsuredGrid = new turnPageClass();    //ȫ�ֱ���, �����˶���, ��ѯ�����ҳ
var turnPagePolGrid = new turnPageClass();        //ȫ�ֱ���, �ֵ����ֶ���, ��ѯ�����ҳ

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
            queryPolGrid();
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
            //nothing
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯ�ŵ��µķֵ��б�
 */
function queryInsuredGrid()
{
//    var QuerySQL = "select a.EdorNo, "
//                 +        "a.EdorType, "
//                 +        "a.ContNo, "
//                 +        "a.InsuredNo, "
//                 +        "a.InsuredName, "
//                 +        "concat(concat(a.InsuredSex , '-') , "
//                 +          "(select CodeName "
//                 +             "from LDCode "
//                 +            "where 1 = 1 "
//                 +              "and CodeType = 'sex' "
//                 +              "and Code = a.InsuredSex)), "
//                 +        "a.InsuredBirthday, "
//                 +        "concat(concat(a.InsuredIDType , '-' , "
//                 +          "(select CodeName "
//                 +             "from LDCode "
//                 +            "where 1 = 1 "
//                 +              "and CodeType = 'idtype' "
//                 +              "and Code = a.InsuredIDType)), "
//                 +        "a.InsuredIDNo, "
//                 +        "a.UWFlag "
//                 +   "from LPCont a "
//                 +  "where 1 = 1 "
//                 +    "and a.EdorNo in "
//                 +        "(select EdorNo "
//                 +           "from LPGrpEdorItem "
//                 +          "where 1 = 1 "
//                 +             getWherePart("EdorAcceptNo", "EdorAcceptNo")
//                 +        ") "
//                 +  getWherePart("a.GrpContNo", "GrpContNo")
//                 +    "and exists "
//                 +        "(select 'X' "
//                 +           "from LCInsured "
//                 +          "where 1 = 1 "
//                 +            "and ContNo = a.ContNo "
//                 +            "and InsuredNo = a.InsuredNo "
//                 +             getWherePart("ContNo", "ContNo_Srh")
//                 +             getWherePart("InsuredNo", "InsuredNo_Srh")
//                 +             getWherePart("CustomerSeqNo", "CustomerSeqNo_Srh")
//                 +             getWherePart("Name", "InsuredName_Srh", "like")
//                 +             getWherePart("Sex", "Sex_Srh")
//                 +             getWherePart("Birthday", "Birthday_Srh")
//                 +             getWherePart("IDType", "IDType_Srh")
//                 +             getWherePart("IDNo", "IDNo_Srh", "like", "0")
//                 +        ") "
//                 +      "and a.PolType <> '2' "
//                 +      "and a.PolType <> '3' "
//                 +    "order by a.ContNo asc, a.InsuredNo asc";
//    alert(QuerySQL);
    var QuerySQL = "";
    var sqlid1="GEdorManuUWInsuredSql1";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //ָ��ʹ�õ�properties�ļ���
    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(window.document.getElementsByName("EdorAcceptNo")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("GrpContNo")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("ContNo_Srh")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("InsuredNo_Srh")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("CustomerSeqNo_Srh")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("InsuredName_Srh")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("Sex_Srh")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("Birthday_Srh")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("IDType_Srh")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("IDNo_Srh")[0].value);//ָ������Ĳ���
    QuerySQL=mySql1.getString();    
    try
    {
        turnPageInsuredGrid.pageDivName = "divTurnPageInsuredGrid";
        turnPageInsuredGrid.queryModal(QuerySQL, InsuredGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ�ŵ��µķֵ���Ϣ�����쳣�� ");
        return;
    }
    //��ѯ�ֵ��б�ʧ���������Ϣ
    if (InsuredGrid.mulLineCount <= 0)
    {
        clearInsuredAndPol();
        resetContUW();
    }
}

/*============================================================================*/
//������鱨��
function showRReport()
{
    var cGrpContNo,cContNo,cPrtNo,pEdorNo,pEdorType;
    try{
       cGrpContNo = fm.GrpContNo.value;
     cContNo = fm.ContNo.value;
     cPrtNo = fm.PrtNo.value;
     pEdorNo = fm.EdorNo.value;
     pEdorType = fm.EdorType.value;
    }catch(ex){}
    if (cContNo == null || trim(cContNo) == "" || pEdorNo == null || trim(pEdorNo) == ""){
        alert("���Ȳ�ѯ��ѡ����Ҫ�����ķֵ��� ");
        return;
    }
  if (cContNo != "")
  {
    window.open("../uw/GrpUWManuRReportMain.jsp?GrpContNo="+cGrpContNo+"&ContNo1="+cContNo+"&PrtNo="+cPrtNo+"&EdorNo="+pEdorNo+"&EdorType="+pEdorType,"window1");
  }
}

//������ϸ��Ϣ
function showPolDetail()
{
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    try{
    cContNo=document.all("ContNo").value ;
    cPrtNo=document.all("PrtNo").value;
    cGrpContNo=document.all("GrpContNo").value;
    }
    catch(ex){}
    if (cContNo != "")
    {
        mSwitch.deleteVar("ContNo");
        mSwitch.addVar("ContNo", "", cContNo );
        mSwitch.updateVar("ContNo", "", cContNo);
        mSwitch.deleteVar("ProposalContNo");
        mSwitch.addVar("ProposalContNo", "", cContNo );
        mSwitch.updateVar("ProposalContNo", "", cContNo);
        mSwitch.deleteVar("PrtNo");
        mSwitch.addVar("PrtNo", "", cPrtNo );
        mSwitch.updateVar("PrtNo", "", cPrtNo);
        mSwitch.deleteVar("GrpContNo");
        mSwitch.addVar("GrpContNo", "", cGrpContNo );
        mSwitch.updateVar("GrpContNo", "", cGrpContNo);
        window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&checktype=2&ContType=2&Auditing=1&ProposalGrpContNo="+cGrpContNo+"&ContNo="+cContNo+"&NameFlag=0","window1");
    }
    else
    {
        alert("����ѡ�񱣵�!");
    }
}

/**
 * ����ֵ���Լ¼��
 */
function showSpec()
{
    try{
    var tContNo = fm.ContNo.value;
    var tPrtNo = fm.PrtNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tEdorType = fm.EdorType.value;
    var tMissionID = fm.MissionID.value;
    var tSubMissionID = fm.SubMissionID.value;
    var tPolNo = fm.PolNo.value;
  }catch (ex) {}
    if (tContNo == null || trim(tContNo) == "" || tEdorNo == null || trim(tEdorNo) == "" || tPolNo == null || trim(tPolNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ�����ķֵ���ѡ�����֣� ");
        return;
    }

  if (tContNo != ""&& tEdorNo !="" && tMissionID != "" )
  {
    var strTran = "ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID
                             +"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&PrtNo="+tPrtNo+"&PolNo="+tPolNo;
   OpenWindowNew("./EdorUWManuSpecMain.jsp?"+strTran, "���屣ȫ��Լ��Ϣ¼��", "left");
    //window.open("./EdorUWManuSpecMain.jsp?"+strTran,"status:no;help:0;close:0;dialogWidth:1024px;dialogHeight:768px");
  }
  else
  {
    alert("���ݴ������!");
  }
}

//�������¼��
function showHealth()
{
    var cContNo,cPrtNo,cGrpContNo,cInsuredNo,pEdorNo,pEdorType;
    try{
      cContNo = fm.ContNo.value;
      cPrtNo = fm.PrtNo.value;
      cGrpContNo = fm.GrpContNo.value;
      cInsuredNo = fm.InsuredNo.value;
      pEdorNo = fm.EdorNo.value;
      pEdorType = fm.EdorType.value;
  }catch(ex){}
    if (cContNo == null || trim(cContNo) == "" || pEdorNo == null || trim(pEdorNo) == ""){
        alert("���Ȳ�ѯ��ѡ����Ҫ�����ķֵ��� ");
        return;
    }
  if (cContNo != "")
  {
//        var checkSQL="select StateFlag from LOPRTManager where standbyflag3='"+cGrpContNo+"' and OtherNo='"+cContNo+"' and standbyflag1=(select insuredno from lcinsured where grpcontno='"+cGrpContNo+"' and contno='"+cContNo+"')";
        var checkSQL = "";
        var sqlid1="GEdorManuUWInsuredSql2";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //ָ��ʹ�õ�properties�ļ���
        mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        mySql1.addSubPara(cGrpContNo);//ָ������Ĳ���
        mySql1.addSubPara(cContNo);//ָ������Ĳ���
        mySql1.addSubPara(cGrpContNo);//ָ������Ĳ���
        mySql1.addSubPara(cContNo);//ָ������Ĳ���
        checkSQL=mySql1.getString();          
        turnPage.strQueryResult  = easyQueryVer3(checkSQL, 1, 0, 1);
  var arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
 if(arrSelected==null||arrSelected==2)
  {
      window.open("../uw/GrpUWManuHealthMain.jsp?ContNo1="+cContNo+"&PrtNo="+cPrtNo+"&GrpContNo="+cGrpContNo+"&InsuredNo="+cInsuredNo+"&EdorNo="+pEdorNo+"&EdorType="+pEdorType,"window1");
     }
    else
        if(arrSelected==0||arrSelected==1)
    {
        var showStr="�ú�ͬ�Ѿ����������֪ͨ�飬�����ٽ���¼��";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    }
}
  else
  {
    showInfo.close();
    alert("����ѡ�񱣵�!");
  }
}

/**
 * ��ѯ�ֵ��б�ʧ���������Ϣ
 */
function clearInsuredAndPol()
{
    try
    {
        document.getElementsByName("ContNo")[0].value = "";
        document.getElementsByName("AppntNo")[0].value = "";
        document.getElementsByName("AppntName")[0].value = "";
        document.getElementsByName("AppntSex")[0].value = "";
        document.getElementsByName("AppntSexName")[0].value = "";
        document.getElementsByName("InsuredNo")[0].value = "";
        document.getElementsByName("InsuredName")[0].value = "";
        document.getElementsByName("InsuredSex")[0].value = "";
        document.getElementsByName("InsuredSexName")[0].value = "";
        document.getElementsByName("InsuredBirthday")[0].value = "";
        document.getElementsByName("InsuredIDType")[0].value = "";
        document.getElementsByName("InsuredIDTypeName")[0].value = "";
        document.getElementsByName("InsuredIDNo")[0].value = "";
        PolGrid.clearData();
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * ��ѯѡ�еı��������Ϻ�����
 */
function queryInsuredAndPol()
{
    queryInsuredInfo();
    queryPolGrid();
}

/*============================================================================*/

function initPolInfo()
{
    var nSelNo;
    try
    {
        nSelNo = PolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var PolNo, UWState;
        try
        {
            PolNo = PolGrid.getRowColData(nSelNo, 3);
            //alert(PolNo);
            UWState = PolGrid.getRowColData(nSelNo, 11);
        }
        catch (ex) {}
        if (PolNo != null && PolNo != "")
        {
            try
            {
                document.getElementsByName("PolNo")[0].value = PolNo;
                document.getElementsByName("UWState")[0].value = UWState;
                showOneCodeName('gedorpoluwstate','UWState','UWStateName');
            }
            catch (ex) {}
            //�������ֺ˱����
//            QuerySQL = "select a.UWIdea "
//                     +   "from LPUWMaster a "
//                     +  "where 1 = 1 "
//                     +    "and a.EdorNo = '" + fm.EdorNo.value + "' and a.EdorType = '" + fm.EdorType.value + "' and a.PolNo = '" + fm.PolNo.value + "' "
            //alert(QuerySQL);
            var QuerySQL = "";
            var sqlid1="GEdorManuUWInsuredSql3";
            var mySql1=new SqlClass();
            mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //ָ��ʹ�õ�properties�ļ���
            mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
            mySql1.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
            mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
            mySql1.addSubPara(fm.PolNo.value);//ָ������Ĳ���
            QuerySQL=mySql1.getString();       
	                        
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("���棺��ѯ�ϴα�������ֺ˱���������쳣�� ");
                return;
            }
            if (arrResult != null)
            {
                try
                {
                    document.getElementsByName("UWIdea")[0].value = arrResult[0][0];
                }
                catch (ex) {}
            }
        }
    }
}

/**
 * ��ѯѡ�еı�����������Ϣ
 */
function queryInsuredInfo()
{
    var nSelNo;
    try
    {
        nSelNo = InsuredGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var sEdorNo, SEdorType, sContNo, sInsuredNo;
        try
        {
            sEdorNo = InsuredGrid.getRowColData(nSelNo, 1);
            SEdorType = InsuredGrid.getRowColData(nSelNo, 2);
            sContNo = InsuredGrid.getRowColData(nSelNo, 3);
            sInsuredNo = InsuredGrid.getRowColData(nSelNo, 4);
        }
        catch (ex) {}
        if (sInsuredNo != null && sInsuredNo != "")
        {
            try
            {
                document.getElementsByName("EdorNo")[0].value = sEdorNo;
                document.getElementsByName("EdorType")[0].value = SEdorType;
                document.getElementsByName("ContNo")[0].value = sContNo;
                document.getElementsByName("InsuredNo")[0].value = sInsuredNo;
            }
            catch (ex) {}
        }
        //��ѯ��ȡ��������������
        var  arrResult;
//        QuerySQL = "select a.AppntNo, "
//                 +        "a.AppntName, "
//                 +        "a.AppntSex, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where 1 = 1 "
//                 +            "and CodeType = 'sex' "
//                 +            "and Code = a.AppntSex), "
//                 +        "a.InsuredName, "
//                 +        "a.InsuredSex, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where 1 = 1 "
//                 +            "and CodeType = 'sex' "
//                 +            "and Code = a.InsuredSex), "
//                 +        "a.InsuredBirthday, "
//                 +        "a.InsuredIDType, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where 1 = 1 "
//                 +            "and CodeType = 'idtype' "
//                 +            "and Code = a.InsuredIDType), "
//                 +        "a.InsuredIDNo,"
//                 +        "(select prtno from lcinsured where contno = a.contno and insuredno = a.insuredno)"
//                 +   "from LPCont a "
//                 +  "where 1 = 1 "
//                 +  getWherePart("a.EdorNo", "EdorNo")
//                 +  getWherePart("a.ContNo", "ContNo")
//                 +    "and a.PolType <> '2' "
//                 +    "and a.PolType <> '3' ";
        //alert(QuerySQL);
        var QuerySQL = "";
        var sqlid1="GEdorManuUWInsuredSql4";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //ָ��ʹ�õ�properties�ļ���
        mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        mySql1.addSubPara(window.document.getElementsByName("EdorNo")[0].value);//ָ������Ĳ���
        mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
        QuerySQL=mySql1.getString();       
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ��ȡ������������Ϣ�����쳣�� ");
            return;
        }
        if (arrResult != null)
        {
            try
            {
                document.getElementsByName("AppntNo")[0].value = arrResult[0][0];
                document.getElementsByName("AppntName")[0].value = arrResult[0][1];
                document.getElementsByName("AppntSex")[0].value = arrResult[0][2];
                document.getElementsByName("AppntSexName")[0].value = arrResult[0][3];
                document.getElementsByName("InsuredName")[0].value = arrResult[0][4];
                document.getElementsByName("InsuredSex")[0].value = arrResult[0][5];
                document.getElementsByName("InsuredSexName")[0].value = arrResult[0][6];
                document.getElementsByName("InsuredBirthday")[0].value = arrResult[0][7];
                document.getElementsByName("InsuredIDType")[0].value = arrResult[0][8];
                document.getElementsByName("InsuredIDTypeName")[0].value = arrResult[0][9];
                document.getElementsByName("InsuredIDNo")[0].value = arrResult[0][10];
                document.getElementsByName("PrtNo")[0].value = arrResult[0][11];
            }
            catch (ex) {}
        }
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * ��ѯѡ�еı���������
 */
function queryPolGrid()
{
//    var QuerySQL = "select a.EdorNo, "
//                 +        "a.EdorType, "
//                 +        "a.PolNo, "
//                 +        "a.RiskCode, "
//                 +        "(select RiskName from LMRisk where RiskCode = a.RiskCode), "
//                 +        "a.Amnt, "
//                 +        "a.Mult, "
//                 +        "a.StandPrem, "
//                 +        "(select (case when sum(Prem) is not null then sum(Prem) else 0 end) "
//                 +           "from LPPrem "
//                 +          "where 1 = 1 "
//                 +            "and EdorNo = a.EdorNo "
//                 +            "and PolNo = a.PolNo "
//                 +            "and PayPlanType = '01'), "
//                 +        "(select (case when sum(Prem) is not null then sum(Prem) else 0 end) "
//                 +           "from LPPrem "
//                 +          "where 1 = 1 "
//                 +            "and EdorNo = a.EdorNo "
//                 +            "and PolNo = a.PolNo "
//                 +            "and PayPlanType = '02'), "
//                 +        "a.UWFlag "
//                 +   "from LPPol a "
//                 +  "where 1 = 1 "
//                 +    "and a.EdorNo in (select EdorNo from LPGrpEdorItem where 1 = 1 " + getWherePart("EdorAcceptNo", "EdorAcceptNo") + ") "
//                 +    "and PolNo = a.PolNo "
//                 +  getWherePart("a.ContNo", "ContNo")
//                 +    "and a.PolTypeFlag <> '2' "
//                 +    "and a.PolTypeFlag <> '3' "
//                 +  "order by a.PolNo asc, a.RiskCode asc";
    //alert(QuerySQL);
    var QuerySQL = "";
    var sqlid1="GEdorManuUWInsuredSql5";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //ָ��ʹ�õ�properties�ļ���
    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(window.document.getElementsByName("EdorAcceptNo")[0].value);//ָ������Ĳ���
    mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
    QuerySQL=mySql1.getString();      
    try
    {
        turnPagePolGrid.pageDivName = "divTurnPagePolGrid";
        turnPagePolGrid.queryModal(QuerySQL, PolGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ�ֵ�������Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ������Ա�Ƿ��������˱�Ȩ��
 */
function isEdorPopedom()
{
    var sOperator;
    try
    {
        sOperator = document.getElementsByName("LoginOperator")[0].value;
    }
    catch (ex) {}
    if (sOperator == null || trim(sOperator) == "")
    {
        alert("�޷���ȡ��ǰ��¼�û���Ϣ����鱣ȫ�˱�Ȩ��ʧ�ܣ� ");
        return false;
    }
    else
    {

        var  arrResult;
//        QuerySQL = "select EdorPopedom "
//                 +   "from LDUWUser "
//                 +  "where UserCode = '" + sOperator + "'";
        //alert(QuerySQL);
        var QuerySQL = "";
        var sqlid1="GEdorManuUWInsuredSql6";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //ָ��ʹ�õ�properties�ļ���
        mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        mySql1.addSubPara(sOperator);//ָ������Ĳ���
        QuerySQL=mySql1.getString();           
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ��ǰ��¼�û���ȫ�˱�Ȩ�޳����쳣�� ");
            return false;
        }
        if (arrResult == null || trim(arrResult[0][0]) != "1")
        {
            alert("�Բ�����û�б�ȫ�˱�Ȩ�ޣ� ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/**
 * �����˽�����֪��ѯ
 */
function queryHealthApprize()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ��ѯ�ķֵ��� ");
        return;
    }
    var sOpenWinURL = "EdorHealthImpartQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "�����˽�����֪��ѯ", "left");
}

/*============================================================================*/

/**
 * ������������ϲ�ѯ
 */
function queryBodyInspect()
{
    var sContNo, sInsuredNo;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "" || sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ��ѯ�ķֵ��� ");
        return;
    }
    var sOpenWinURL = "../uw/UWBeforeHealthQMain.jsp";
    var sParameters = "ContNo=" + sContNo + "&CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "������������ϲ�ѯ", "left");
}

/*============================================================================*/

/**
 * �����˱����ۼ���ʾ
 */
function queryAccumulateAmnt()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ��ѯ�ķֵ��� ");
        return;
    }
    var sOpenWinURL = "../uw/AmntAccumulateMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "�����˱����ۼ���ʾ", "left");
}

/*============================================================================*/

/**
 * �������ѳб�������ѯ
 */
function queryAlreadyProposal()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ��ѯ�ķֵ��� ");
        return;
    }
    var sOpenWinURL = "../uw/ProposalQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "�������ѳб�������ѯ", "left");
}

/*============================================================================*/

/**
 * ������δ�б�Ͷ������ѯ
 */
function queryUnProposalCont()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ��ѯ�ķֵ��� ");
        return;
    }
    var sOpenWinURL = "../uw/NotProposalQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "������δ�б�Ͷ������ѯ", "left");
}

/*============================================================================*/

/**
 * �����˼�����ȫ��Ϣ��ѯ
 */
function queryLastEdorTrack()
{
    var sEdorAcceptNo, sInsuredNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "" || sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ��ѯ�ķֵ��� ");
        return;
    }
    var sOpenWinURL = "EdorAgoQueryMain.jsp";
    var sParameters = "EdorAcceptNo=" + sEdorAcceptNo + "&CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "�����˼�����ȫ��Ϣ��ѯ", "left");
}

/*============================================================================*/

/**
 * �����˼���������Ϣ��ѯ
 */
function queryLastClaimTrack()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("���Ȳ�ѯ��ѡ����Ҫ��ѯ�ķֵ��� ");
        return;
    }
    var sOpenWinURL = "../uw/ClaimQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "�����˼���������Ϣ��ѯ", "left");
}

/*============================================================================*/

/*********************************************************************
 *  �ӷ�¼��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAdd()
{
//  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var tContNo,tEdorNo,tMissionID,tSubMissionID,tInsuredNo,tEdorAcceptNo;
  try{
     tContNo = fm.ContNo.value;
     tEdorNo = fm.EdorNo.value;
     tMissionID = fm.MissionID.value;
     tSubMissionID = fm.SubMissionID.value;
     tInsuredNo = fm.InsuredNo.value;
     tEdorAcceptNo = fm.EdorAcceptNo.value;
  }catch(ex){}
    if (tContNo == null || trim(tContNo) == "" || tEdorNo == null || trim(tEdorNo) == ""){
        alert("���Ȳ�ѯ��ѡ����Ҫ�����ķֵ��� ");
        return;
    }
  var strTran = "ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID
               +"&EdorNo="+tEdorNo+"&InsuredNo="+tInsuredNo+"&EdorAcceptNo="+tEdorAcceptNo;

  var newWindow = window.open("./EdorUWManuAddMain.jsp?"+strTran,"EdorUWManuAdd", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
/*============================================================================*/

/**
 * �����˱�ͨ���ŵ������зֵ�
 */
function batchUWPassAll()
{
    if (!isEdorPopedom())    return;
    if (confirm("�˲������ѱ��ŵ��µ����и�����Ϊ�˱�ͨ��״̬�� \n\nҪ������"))
    {
        var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        try
        {
            document.getElementsByName("EdorNo")[0].value = "";
            document.getElementsByName("EdorType")[0].value = "";
        }
        catch (ex) {}
        document.forms(0).action = "GEdorManuUWInsuredSave.jsp";
        document.forms(0).target = "fraSubmit";
        document.forms(0).submit();
    }
}

/*============================================================================*/

/**
 * ����Ƿ�ѡ����ĳ���ֵ����к˱�
 */
function isSelectOneToUW()
{
    var nSelNo;
    try
    {
        nSelNo = InsuredGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�˱��ĸ����� ");
        return false;
    }
    else
    {
        var sEdorNo, sEdorType;
        try
        {
            sEdorNo = InsuredGrid.getRowColData(nSelNo, 1);
            sEdorType = InsuredGrid.getRowColData(nSelNo, 2);
            if (sEdorNo == null || trim(sEdorNo) == "")
            {
                alert("�޷���ȡ��ѡ�����������š����Ÿ����˱�ʧ�ܣ� ");
                return false;
            }
            else
            {
                document.getElementsByName("EdorNo")[0].value = sEdorNo;
                document.getElementsByName("EdorType")[0].value = sEdorType;
            }
        }
        catch (ex) {}
    }
    return true;
}

/*============================================================================*/

/**
 * ����������ֺ˱�����
 */
function saveContUW()
{
    if (!isSelectOneToUW())    return;
    if (!verifyInput2())       return;
    if (!isEdorPopedom())      return;
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
    document.forms(0).action = "GEdorManuUWInsuredSave.jsp";
    document.forms(0).target = "fraSubmit";
    document.forms(0).submit();
}

/*============================================================================*/

/**
 * �������屣���˱�����
 */
function resetContUW()
{
    try
    {
        document.getElementsByName("UWState")[0].value = "";
        document.getElementsByName("UWStateName")[0].value = "";
        document.getElementsByName("UWIdea")[0].value = "";
    }
    catch (ex) {}
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
