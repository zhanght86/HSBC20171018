//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();

//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
	{
		parent.fraMain.rows = "0,0,50,82,*";
	}
	else
	{
		parent.fraMain.rows = "0,0,0,72,*";
	}
}

function returnparent()
  {
  	var backstr=document.all("ContNo").value;
  	//alert(backstr+"backstr");
  	mSwitch.deleteVar("ContNo");
	mSwitch.addVar("ContNo", "", backstr);
	mSwitch.updateVar("ContNo", "", backstr);
  	location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}

function showNotePad()
{
    alert("������")
    return;
	var selno = SelfPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}

	var MissionID = SelfPolGrid.getRowColData(selno, 6);
	var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
	var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}
	var NoType = "1";

	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

//��ʾ����ҳ��
function newReport()
{
    location.href="LLReportInput.jsp?isNew=0";
}

// ��ʼ�����1
function queryGrid()
{
    initLLReportGrid();
    var strSQL = "";
    /*
    strSQL = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,RptDate,MngCom,AccidentReason from LLReport where 1=1 "
           + getWherePart( 'RptNo' )
           + getWherePart( 'RptorName' )
           + getWherePart( 'RptorPhone' )
           + getWherePart( 'RptorAddress' )
           + getWherePart( 'Relation' )
           + getWherePart( 'RptMode' )
           + " and Operator <> " + fm.Operator.value
           + " and missionprop20 like '" + fm.ManageCom.value + "'"
           + " order by RptNo";
    */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportMissInputSql");
	mySql.setSqlId("LLReportMissSql1");
	mySql.addSubPara( fm.RptNo.value); 
	mySql.addSubPara( fm.RptorName.value);     
	mySql.addSubPara( fm.RptorPhone.value);     
	mySql.addSubPara( fm.RptorAddress.value);     
	mySql.addSubPara( fm.Relation.value);     
	mySql.addSubPara( fm.RptMode.value);     
	mySql.addSubPara( fm.Operator.value );     
	mySql.addSubPara( fm.ManageCom.value);           
//    alert(strSQL);
    turnPage.queryModal(mySql.getString(),LLReportGrid);
}

//=======Ӧ����ת������̫��Ҫ���޸�========zl=======2006-1-4 13:16====================================================BEG
//// ��ʼ�����2
//function querySelfGrid()
//{
//    initSelfLLReportGrid();
//    var strSQL = "";
//	strSQL = "select missionprop1,missionprop2,makedate,missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid from lwmission where 1=1 "
//           + " and activityid = '0000005005' "
//           + " and processid = '0000000005'"
//           + " and DefaultOperator = '" + fm.Operator.value
//           + "' order by missionprop1";
////    alert(strSQL);
//    turnPage2.queryModal(strSQL,SelfLLReportGrid);
//}

// ��ʼ�����2
function querySelfGrid()
{
    initSelfLLReportGrid();
    var strSQL = "";
//	strSQL = "select missionprop1,missionprop2,MissionProp6,missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid from lwmission where 1=1 "
//           + " and activityid = '0000005005' "
//           + " and processid = '0000000005'"
//           + getWherePart( 'missionprop1' ,'RptNo')
//           + getWherePart( 'missionprop2' ,'RptorName','like')
//           + getWherePart( 'missionprop4' ,'CustomerName','like');
	/*
	strSQL = "select rptno,(Select RptorName From LLReport where rptno=a.rptno),(Select RptDate From llreport where rptno=a.rptno),"
	       + "(Select CustomerNo from LDPerson where CustomerNo In (select CustomerNo from LLSubReport where SubRptNo = a.rptno) And Rownum=1),"
	       + "(Select Name from LDPerson where CustomerNo In (select CustomerNo from LLSubReport where SubRptNo = a.rptno) And Rownum=1),"
	       + "(Select (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) from LDPerson where CustomerNo In (select CustomerNo from LLSubReport where SubRptNo = a.rptno) And Rownum=1),"
	       + "(Select LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and LLCaseRela.CaseNo = a.rptno) from LLReportlog a where 1=1 "
	       + getWherePart( 'rptno' ,'RptNo');
	 */
	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportMissInputSql");
	mySql.setSqlId("LLReportMissSql2");
	mySql.addSubPara( fm.RptNo.value); 
	mySql.addSubPara( fm.Operator.value); 

	      
	 if (fm.RptorName.value != "")
	 {
	//	 strSQL = strSQL + " and (Select RptorName From LLReport where rptno=a.rptno) like '%" + fm.RptorName.value + "%'";
	  mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportMissInputSql");
	mySql.setSqlId("LLReportMissSql3");
	mySql.addSubPara( fm.RptNo.value);
	mySql.addSubPara( fm.Operator.value); 
	mySql.addSubPara( fm.RptorName.value);  
	 }
	 if (fm.CustomerName.value != "")
	 {
	//	 strSQL = strSQL + " and (Select Name from LDPerson where CustomerNo In (select CustomerNo from LLSubReport where SubRptNo = a.rptno) And Rownum=1) like '%" + fm.CustomerName.value + "%'";
	  mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportMissInputSql");
	mySql.setSqlId("LLReportMissSql4");
	mySql.addSubPara( fm.RptNo.value);
	mySql.addSubPara( fm.Operator.value); 
	mySql.addSubPara( fm.RptorName.value); 
	mySql.addSubPara( fm.CustomerName.value); 
	 }
	 
    if (fm.RptStartDate.value != "")
    {
        //strSQL = strSQL + " and (Select LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and LLCaseRela.CaseNo = a.rptno) >= '" + fm.RptStartDate.value + "'"
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportMissInputSql");
	mySql.setSqlId("LLReportMissSql5");
	mySql.addSubPara( fm.RptNo.value);
	mySql.addSubPara( fm.Operator.value); 
	mySql.addSubPara( fm.RptorName.value); 
	mySql.addSubPara( fm.CustomerName.value); 
	mySql.addSubPara( fm.RptStartDate.value); 
    }
    if (fm.RptEndDate.value != "")
    {
      //  strSQL = strSQL + " and (Select LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and LLCaseRela.CaseNo = a.rptno) <= '" + fm.RptEndDate.value + "'"
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportMissInputSql");
	mySql.setSqlId("LLReportMissSql6");
	mySql.addSubPara( fm.RptNo.value);
	mySql.addSubPara( fm.Operator.value); 
	mySql.addSubPara( fm.RptorName.value); 
	mySql.addSubPara( fm.CustomerName.value); 
	mySql.addSubPara( fm.RptStartDate.value);
//�ഫ��һ������--Edit by ningyc 20110831 
	//mySql.addSubPara( fm.RptStartDate.value); 
	mySql.addSubPara( fm.RptEndDate.value); 
    }
    //���������ӵ����������
    /*
    strSQL = strSQL
           + " and a.Operator = '" + fm.Operator.value + "'"
           + " order by a.rptno";
           */
    
    //prompt("�����׶γ�ʼ��ʱ�ӹ����ز�ѯ�������е�sql��",strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLReportGrid);
}
//=======Ӧ����ת������̫��Ҫ���޸�========zl=======2006-1-4 13:16====================================================END


//LLReportGrid����¼�
function LLReportGridClick()
{
    //ȡ���ⰸ��
    var i = LLReportGrid.getSelNo();
    var tClmNo = "";
    if (i != '0')
    {
        i = i - 1;
        tClmNo = LLReportGrid.getRowColData(i,1);
    }
    var tUrl = "LLReportInput.jsp?claimNo="+tClmNo+"&isNew=2";
    location.href=tUrl;
}

//SelfLLReportGrid����¼�
function SelfLLReportGridClick()
{
    var i = SelfLLReportGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLReportGrid.getRowColData(i,1);
        var tMissionID = SelfLLReportGrid.getRowColData(i,8);
        var tSubMissionID = SelfLLReportGrid.getRowColData(i,9);
        location.href="LLReportInput.jsp?claimNo="+tClmNo+"&isNew=1&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    }
}

