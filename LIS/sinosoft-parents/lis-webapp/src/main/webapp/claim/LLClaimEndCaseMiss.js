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
        //ֱ��ȡ��������ת����������
        var i = LLClaimEndCaseGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimEndCaseGrid.getRowColData(i,1);
            var tClmState = LLClaimEndCaseGrid.getRowColData(i,2);
            var tMissionID = LLClaimEndCaseGrid.getRowColData(i,8);
            var tSubMissionID = LLClaimEndCaseGrid.getRowColData(i,9);
            location.href="LLClaimEndCaseInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
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

// ��ʼ�����1
function queryGrid()
{
    initLLClaimEndCaseGrid();
    var strSQL = "";
	/*strSQL = "select missionprop1,(case missionprop2 when '40' then '����' when '50' then '�᰸' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
		   + "(select rgtdate from llregister where rgtno=missionprop1),(select endcasedate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20,"
	       + "(select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 " 		
           + " and activityid = '0000005075'"
           + " and processid = '0000000005'"
           + " and DefaultOperator is null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           //+ getWherePart( 'missionprop6' ,'AccidentDate')
           + " and missionprop20 like '" + fm.ManageCom.value + "%'"
           + " and missionprop8 = '1'"*/
           //+ " order by missionprop1";
	
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimEndCaseMissInputSql");
	mySql.setSqlId("LLClaimEndCaseMissSql1");
	mySql.addSubPara( fm.RptNo.value);
	mySql.addSubPara( fm.ClmState.value);
	mySql.addSubPara( fm.CustomerNo.value);
	mySql.addSubPara( fm.CustomerName.value);
	mySql.addSubPara( fm.customerSex.value);
	mySql.addSubPara( fm.ManageCom.value);
           
    //��������
	if(!(fm.RgtDate.value==null||fm.RgtDate.value==""))
	{
		//strSQL=strSQL+" and exists(select 1 from llregister where rgtno=missionprop1 and rgtdate='"+fm.RgtDate.value+"') ";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseMissInputSql");
		mySql.setSqlId("LLClaimEndCaseMissSql2");
		mySql.addSubPara( fm.RptNo.value);
		mySql.addSubPara( fm.ClmState.value);
		mySql.addSubPara( fm.CustomerNo.value);
		mySql.addSubPara( fm.CustomerName.value);
		mySql.addSubPara( fm.customerSex.value);
		mySql.addSubPara( fm.ManageCom.value);
		mySql.addSubPara( fm.RgtDate.value);
	}
	
	//ǩ��ͳһ����
	if(!(fm.EndCaseDate.value==null||fm.EndCaseDate.value==""))
	{
		//strSQL=strSQL+" and exists(select 1 from llregister where rgtno=missionprop1 and endcasedate='"+fm.EndCaseDate.value+"') ";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseMissInputSql");
		mySql.setSqlId("LLClaimEndCaseMissSql3");
		mySql.addSubPara( fm.RptNo.value);
		mySql.addSubPara( fm.ClmState.value);
		mySql.addSubPara( fm.CustomerNo.value);
		mySql.addSubPara( fm.CustomerName.value);
		mySql.addSubPara( fm.customerSex.value);
		mySql.addSubPara( fm.ManageCom.value);
		mySql.addSubPara( fm.RgtDate.value);
		mySql.addSubPara( fm.EndCaseDate.value);
	}
	
	//strSQL=strSQL+" order by AcceptedDate,missionprop1";
//    alert(strSQL);
//    prompt("�᰸��ʼ����ѯ",strSQL);
    turnPage.queryModal(mySql.getString(),LLClaimEndCaseGrid);
    HighlightByRow();
}

//LLClaimEndCaseGrid���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightByRow(){
    for(var i=0; i<LLClaimEndCaseGrid.mulLineCount; i++){
    	var accepteddate = LLClaimEndCaseGrid.getRowColData(i,13); //��������
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {
    		   LLClaimEndCaseGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

// ��ʼ�����2
function querySelfGrid()
{
    initSelfLLClaimEndCaseGrid();
    var strSQL = "";/*
	strSQL = "select missionprop1,(case missionprop2 when '60' then '���' when '50' then '�᰸' end),missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
		   + " (select rgtdate from llregister where rgtno=missionprop1),(select endcasedate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20,"
		   + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
           + " and activityid = '0000005075'"
           + " and processid = '0000000005'"
           + " and DefaultOperator = '" + fm.Operator.value + "'"
           + " and missionprop8 = '1'"
           + " and missionprop20 like '" + fm.ManageCom.value + "%'"
           + " order by AcceptedDate,missionprop1 ";*/
    	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseMissInputSql");
		mySql.setSqlId("LLClaimEndCaseMissSql4");
		mySql.addSubPara( fm.Operator.value);
		mySql.addSubPara( fm.ManageCom.value);
//    alert(strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLClaimEndCaseGrid);
    HighlightSelfByRow();
}

//SelfLLClaimEndCaseGrid���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightSelfByRow(){
    for(var i=0; i<SelfLLClaimEndCaseGrid.mulLineCount; i++){
    	var accepteddate = SelfLLClaimEndCaseGrid.getRowColData(i,13); //��������
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {
    		   SelfLLClaimEndCaseGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}


//LLClaimEndCaseGrid����¼�
function LLClaimEndCaseGridClick()
{
    HighlightByRow();
}

//SelfLLClaimEndCaseGrid����¼�
function SelfLLClaimEndCaseGridClick()
{ 
	HighlightSelfByRow();
    var i = SelfLLClaimEndCaseGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLClaimEndCaseGrid.getRowColData(i,1);
        var tClmState = SelfLLClaimEndCaseGrid.getRowColData(i,2);
        var tMissionID = SelfLLClaimEndCaseGrid.getRowColData(i,8);
        var tSubMissionID = SelfLLClaimEndCaseGrid.getRowColData(i,9);
        location.href="LLClaimEndCaseInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    }
}

//[����]��ť
function ApplyClaim()
{
	var selno = LLClaimEndCaseGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦��ı�����");
	      return;
	}
	fm.MissionID.value = LLClaimEndCaseGrid.getRowColData(selno, 8);
	fm.SubMissionID.value = LLClaimEndCaseGrid.getRowColData(selno, 9);
	fm.ActivityID.value = LLClaimEndCaseGrid.getRowColData(selno, 10);
	
	fm.action = "./LLReportMissSave.jsp";
	submitForm(); //�ύ
}

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