//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();

//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();


        //ֱ��ȡ��������ת����������
        var i = LLClaimRegisterGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimRegisterGrid.getRowColData(i,1);
            var tClmState = LLClaimRegisterGrid.getRowColData(i,2);
            tClmState = tClmState.substring(0,2);
            var tMissionID = LLClaimRegisterGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimRegisterGrid.getRowColData(i,8);
            location.href="LLClaimRegisterInput.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
        }
    }
    tSaveFlag ="0";
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
    location.href="LLClaimRegisterInput.jsp?isNew=0";
}

// ��ʼ�����1
function queryGrid()
{
    initLLClaimRegisterGrid();
    var strSQL = "";
    //----------------������------------------------------------------------------------����״̬-------------------------------------------�����˱���---����������---�������Ա�---��������
	strSQL = "select missionprop1,(case missionprop2 when '10' then '10-�ѱ���' when '20' then '20-������' when '25' then '20-�ӳ�����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
           + " and activityid = '0000005015'"
           + " and processid = '0000000005'"
           + " and DefaultOperator is null "
           + " and missionprop5 is not null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
//           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           //��������������������ȫ����Χ�ڲ���
           if (fm.CustomerName.value == "" || fm.CustomerName.value == null)
           {
               strSQL = strSQL + " and missionprop20 like '" + fm.ManageCom.value + "'"
           }
           else
           {
               strSQL = strSQL + getWherePart( 'missionprop4' ,'CustomerName','like')
           }
    strSQL = strSQL + " order by missionprop2,missionid";
//    alert(strSQL);
    turnPage.queryModal(strSQL,LLClaimRegisterGrid);
}

// ��ʼ�����2
function querySelfGrid()
{
    initSelfLLClaimRegisterGrid();
    var strSQL = "";
	strSQL = "select missionprop1,(case missionprop2 when '10' then '10-�ѱ���' when '20' then '20-������' when '25' then '20-�ӳ�����' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
           + " and activityid = '0000005015'"
           + " and processid = '0000000005'"
           + " and missionprop5 is not null "
           + " and DefaultOperator = '" + fm.Operator.value
           + "' order by missionprop2,missionid";
//    alert(strSQL);
    turnPage2.queryModal(strSQL,SelfLLClaimRegisterGrid);
}

//LLClaimRegisterGrid����¼�
function LLClaimRegisterGridClick()
{
}

//SelfLLClaimRegisterGrid����¼�
function SelfLLClaimRegisterGridClick()
{
    var i = SelfLLClaimRegisterGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
        var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
        tClmState = tClmState.substring(0,2);
        var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
        var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
        location.href="LLClaimRegisterInput.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    }
}

//[����]��ť
function ApplyClaim()
{
	var selno = LLClaimRegisterGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦��ı�����");
	      return;
	}
	fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
	fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);
	
	fm.action = "./LLReportMissSave.jsp";
	submitForm(); //�ύ
}

//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
 

    fm.submit(); //�ύ
    tSaveFlag ="0";
}