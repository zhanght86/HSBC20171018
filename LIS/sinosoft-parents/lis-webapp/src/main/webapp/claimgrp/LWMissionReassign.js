var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//�����û���ѯ������Ϣ���û����� ���û��������������룩
function afterQuery( arrQueryResult ) 
{ 
    var arrResult = new Array();
    if( arrQueryResult != null ) 
    {
        arrResult = arrQueryResult;
		fm.DesignateOperator.value = arrResult[0];
	}
}

//[��ѯ����]��ť
function LWMissionQueryClick()
{
	fm.DefaultOperator.value="";
	fm.DesignateOperator.value="";	
	var strSQL="select missionprop1,missionprop2,missionprop3,missionprop4,(case missionprop5 when '0' then '0-��' when '1' then '1-Ů' end),missionprop6,"
			+"(case missionprop7 when '1' then '1-����' when '2' then '2-����' when '3' then '3-����' end),"
			+ "defaultoperator,missionid,submissionid,activityid from lwmission where 1=1 "
			+ " and processid = '0000000005' "
			+ " and missionprop20 like '" +document.all('ComCode').value+ "%%'"
			+ getWherePart( 'ActivityID','ActivityIDQ')
			+ getWherePart( 'missionprop1','ClmNOQ')
			+ getWherePart( 'defaultoperator','OperatorQ')				 
			+ " order by missionid";
	var arrResult = easyExecSql(strSQL);
	if(arrResult==null ||arrResult=="")
	{
		LWMissionGrid.clearData();
		alert("û�в�ѯ���κ������¼������������������");
		fm.ActivityIDQ.value="";
		fm.ActivityIDQName.value="";		
		fm.ClmNOQ.value="";
		fm.OperatorQ.value="";
		return;
	}		
   else
   	{
//   		displayMultiline(arrResult,LWMissionGrid);
		turnPage.queryModal(strSQL, LWMissionGrid);  
   	}
}

//LWMissionGrid�ĵ�ѡť��Ӧ����
function LWMissionGridClick()
{
	var tRow = LWMissionGrid.getSelNo();
	fm.MissionID.value=LWMissionGrid.getRowColData(tRow-1,9);
	fm.SubMissionID.value=LWMissionGrid.getRowColData(tRow-1,10);
	fm.ActivityID.value=LWMissionGrid.getRowColData(tRow-1,11);
	fm.DefaultOperator.value=LWMissionGrid.getRowColData(tRow-1,8);
}

//[��ѯ�����û�]��ť
function LLClaimUserQueryClick()
{
	var tRow = LWMissionGrid.getSelNo();
	if( tRow < 1||tRow == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}
	var strUrl="LLClaimUserQueryMain.jsp";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//[ָ��ȷ��]��ť
function DesignateConfirmClick()
{
	var tRow = LWMissionGrid.getSelNo();
	if( tRow < 1||tRow == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}
//	if(fm.DesignateOperator.value=="" ||fm.DesignateOperator.value==null)
//	{
//		alert( "��ѡ�����Ա��" );
//		return;		
//	}
	fm.fmtransact.value="User||UPDATE";
	fm.action="LWMissionReassignSave.jsp";
	submitForm();
}

//ȷ�Ϻ�ˢ��ҳ��(Ĭ�ϲ�����)
function Renewpage()
{
	var tRow = LWMissionGrid.getSelNo()-1;
	LWMissionGrid.setRowColData(tRow,8,fm.DesignateOperator.value);
	fm.DefaultOperator.value=fm.DesignateOperator.value;
}

//�����ύ����
function submitForm()
{
    //�ύ����
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

//Ԥ�������ύ�����,����
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

    }
    tSaveFlag ="0";
    Renewpage();
}
