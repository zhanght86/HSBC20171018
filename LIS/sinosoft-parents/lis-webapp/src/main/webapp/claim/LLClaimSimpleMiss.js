//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();

////�ύ��ɺ���������
//function afterSubmit( FlagStr, content )
//{
//    showInfo.close();
//    if (FlagStr == "Fail" )
//    {
//        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//    }
//    else
//    {
//        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//        //ֱ��ȡ��������ת����������
//        var i = LLClaimSimpleGrid.getSelNo();
//        if (i != '0')
//        {
//            i = i - 1;
//            var tClmNo = LLClaimSimpleGrid.getRowColData(i,1);
//            var tClmState = LLClaimSimpleGrid.getRowColData(i,2);
//            var tMissionID = LLClaimSimpleGrid.getRowColData(i,7);
//            var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,8);
//            location.href="LLClaimSimpleInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
//        }
//    }
//    tSaveFlag ="0";
//}

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

// ��ʼ�����1
//function queryGrid()
//{
//    initLLClaimSimpleGrid();
//    var strSQL = "";/*
//	strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
//		   + " (select rgtdate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
//           + " and activityid = '0000005065'"
//           + " and processid = '0000000005'"
//           + " and DefaultOperator is null "
//           + getWherePart( 'missionprop1' ,'RptNo')
//           + getWherePart( 'missionprop2' ,'ClmState')
//           + getWherePart( 'missionprop3' ,'CustomerNo')
//           + getWherePart( 'missionprop4' ,'CustomerName','like')
//           + getWherePart( 'missionprop5' ,'customerSex')
//           + " and missionprop20 like '" + fm.ManageCom.value + "%%'"
//           + " and exists (select 'X' from llclaimuser where simpleflag='1' and usercode='"+fm.Operator.value+"')";*/
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimSimpleMissInputSql");
//	mySql.setSqlId("LLClaimSimpleMissSql1");
//	mySql.addSubPara(fm.RptNo.value );
//	mySql.addSubPara( fm.ClmState.value);
//	mySql.addSubPara( fm.CustomerNo.value);
//	mySql.addSubPara(fm.CustomerName.value );
//	mySql.addSubPara(fm.customerSex.value );
//	mySql.addSubPara(fm.ManageCom.value );
//	mySql.addSubPara( fm.Operator.value);
//	
//    //��������
//	if(!(fm.RgtDate.value==null||fm.RgtDate.value==""))
//	{
//	//	strSQL=strSQL+" and exists(select 1 from llregister where rgtno=missionprop1 and rgtdate='"+fm.RgtDate.value+"') ";
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimSimpleMissInputSql");
//	mySql.setSqlId("LLClaimSimpleMissSql2");
//	mySql.addSubPara(fm.RptNo.value );
//	mySql.addSubPara( fm.ClmState.value);
//	mySql.addSubPara( fm.CustomerNo.value);
//	mySql.addSubPara(fm.CustomerName.value );
//	mySql.addSubPara(fm.customerSex.value );
//	mySql.addSubPara(fm.ManageCom.value );
//	mySql.addSubPara( fm.Operator.value);		
//	mySql.addSubPara( fm.RgtDate.value);		
//	}
//	
//	//strSQL=strSQL+" order by missionprop1";
//           
//    //prompt("--���װ�������ز�ѯ:",strSQL);
//    turnPage.queryModal(mySql.getString(),LLClaimSimpleGrid);
//}
//
//// ��ʼ�����2
//function querySelfGrid()
//{
//    initSelfLLClaimSimpleGrid();
//    var strSQL = "";/*
//	strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
//		   + " (select rgtdate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
//           + " and activityid = '0000005065'"
//           + " and processid = '0000000005'"
//           + " and DefaultOperator = '" + fm.Operator.value
//           + "' order by missionprop1";*/
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimSimpleMissInputSql");
//	mySql.setSqlId("LLClaimSimpleMissSql3");
//	mySql.addSubPara(fm.Operator.value );
//	
//	//prompt("--���װ���������в�ѯ:",strSQL);
//    turnPage2.queryModal(mySql.getString(),SelfLLClaimSimpleGrid);
//}

//LLClaimSimpleGrid����¼�
function LLClaimSimpleGridClick()
{
}

////SelfLLClaimSimpleGrid����¼�
//function SelfLLClaimSimpleGridClick()
//{
//    var i = SelfLLClaimSimpleGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = SelfLLClaimSimpleGrid.getRowColData(i,1);
//        var tClmState = SelfLLClaimSimpleGrid.getRowColData(i,2);
//        var tMissionID = SelfLLClaimSimpleGrid.getRowColData(i,7);
//        var tSubMissionID = SelfLLClaimSimpleGrid.getRowColData(i,8);
//        location.href="LLClaimSimpleInputMain.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
//    }
//}

////[����]��ť
//function ApplyClaim()
//{
//	var selno = LLClaimSimpleGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��Ҫ���봦��ı�����");
//	      return;
//	}
//	fm.MissionID.value = LLClaimSimpleGrid.getRowColData(selno, 7);
//	fm.SubMissionID.value = LLClaimSimpleGrid.getRowColData(selno, 8);
//	fm.ActivityID.value = LLClaimSimpleGrid.getRowColData(selno, 9);
//	
//	fm.action = "./LLReportMissSave.jsp";
//	submitForm(); //�ύ
//}

//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//modify by lzf 
//[����]��ť
function ApplyClaim()
{
	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦��ı�����");
	      return;
	}
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 14);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 16);
	
	fm.action = "./LLReportMissSave.jsp";
	submitForm(); //�ύ
}
 
//SelfLLClaimSimpleGrid����¼�
function SelfLLClaimSimpleGridClick()
{
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tClmState = PrivateWorkPoolGrid.getRowColData(i,2);
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,13);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,14);
        var tActivityID = PrivateWorkPoolGrid.getRowColData(i,16);
        location.href="LLClaimSimpleInputMain.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
    }
}

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
        //ֱ��ȡ��������ת����������
        var i = PublicWorkPoolGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = PublicWorkPoolGrid.getRowColData(i,1);
            var tClmState = PublicWorkPoolGrid.getRowColData(i,2);
            var tMissionID = PublicWorkPoolGrid.getRowColData(i,13);
            var tSubMissionID = PublicWorkPoolGrid.getRowColData(i,14);
            var tActivityID = PublicWorkPoolGrid.getRowColData(i,16);
            location.href="LLClaimSimpleInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
        }
    }
    tSaveFlag ="0";
}
//end lzf