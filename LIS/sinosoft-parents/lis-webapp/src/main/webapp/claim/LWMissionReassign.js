var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
//�����û���ѯ������Ϣ���û����� ���û��������������룩
function afterQuery( arrQueryResult ) 
{ 
    var arrResult = new Array();
    if( arrQueryResult != null ) 
    {
        arrResult = arrQueryResult;
		fm.DesignateOperator.value = arrResult[0];
		fm.ComCode.value = arrResult[1];
	}
}

//[��ѯ����]��ť
function LWMissionQueryClick()
{
	//fm.DefaultOperator.value = "";
	 document.all('DefaultOperator').value="";
	 document.all('DesignateOperator').value="";
	//fm.DesignateOperator.value = "";	
//	var strSQL = "select missionprop1,"
//	           + " (case ActivityID when '0000005005' then '����' when '0000005015' then '����' when '0000005025' then 'Ԥ��' when '0000005035' then '���' when '0000005055' then '����' when '0000005065' then '���װ���' when '0000005075' then '�᰸' when '0000005105' then '�ʱ�' when '0000005125' then '�������' when '0000005145' then '�������' when '0000005165' then '�����������' when '0000005175' then '�����ⰸ����' "
//	           +" when '0000009005' then '���ձ���' when '0000009015' then '��������' when '0000009025' then '����Ԥ��' when '0000009035' then '�������' when '0000009055' then '��������' when '0000009065' then '���ռ��װ���' when '0000009075' then '���ս᰸' when '0000009105' then '�����ϱ�' when '0000009125' then '���յ������' when '0000009145' then '���յ������' when '0000009165' then '���յ����������' when '0000009175' then '���յ����ⰸ����' "
//	           +" when '0000005505' then '�������' end),"
//	           + " missionprop3,missionprop4,"
//	           + " missionprop20,"
//	           + " missionprop6,"
//	           + " defaultoperator,"
//               + " (select username from llclaimuser where usercode = defaultoperator),"
//               + " missionid,submissionid,activityid,'' "
//               + " from lwmission where 1=1 "
//               + " and processid in ('0000000005','0000000009','6100300004','6100301011') "  
//               + " and (case when (select comcode from llclaimuser where usercode = lwmission.defaultoperator) is not null then (select comcode from llclaimuser where usercode = lwmission.defaultoperator) else missionprop20 end) like '" + document.all('ComCode').value + "%%'"
//    		   + getWherePart( 'missionprop7','ClmNOQ')
//    		   + getWherePart( 'missionprop1','ClmNOQ1')
//    		   + getWherePart( 'defaultoperator','OperatorQ');
	 //document.all('ActivityIDQ').value="";
	if(document.all('ActivityIDQ').value==null||document.all('ActivityIDQ').value=="")
	{
		if(document.all('ClmNOQ').value == null || document.all('ClmNOQ').value== "")
		{
//		  strSQL=strSQL+" union all  "
//					+ "select missionprop1,'��ǰɨ��',  missionprop3,missionprop4, missionprop20,"
//	        + " missionprop6,defaultoperator, (select username from llclaimuser where usercode = defaultoperator),"
//	        + " missionid,submissionid,activityid,missionprop9  from lwmission where  activityid='0000005010'  "
//	        + " and (case when (select comcode from llclaimuser where usercode = lwmission.defaultoperator) is not null then (select comcode from llclaimuser where usercode = lwmission.defaultoperator) else missionprop20 end) like '" + document.all('ComCode').value + "%%'"
//	  		  + getWherePart( 'missionprop1','ClmNOQ1')
//	  		  + getWherePart( 'defaultoperator','OperatorQ');
		  
		    sql_id = "LWMissionReassignSql11";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
	   }
		}
	
	else
	{
		if(fm.ActivityIDQ.value=="001"){
//			strSQL=strSQL+" and activityid in ('0000009005','0000005005') ";
			sql_id = "LWMissionReassignSql12";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="002"){
//			strSQL=strSQL+" and activityid in ('0000009015','0000005015') ";
			sql_id = "LWMissionReassignSql13";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="003"){
//			strSQL=strSQL+" and activityid in ('0000009025','0000005025') ";
			sql_id = "LWMissionReassignSql14";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="004"){
			/*strSQL=strSQL+" and activityid in ('0000009035','0000005035') ";*/
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql");
			my_sql.setSqlId("LWMissionReassignSql6");
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="005"){
//			strSQL=strSQL+" and activityid in ('0000009055','0000005055') ";
			sql_id = "LWMissionReassignSql16";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="006"){
//			strSQL=strSQL+" and activityid in ('0000009065','0000005065') ";
			sql_id = "LWMissionReassignSql17";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="007"){
//			strSQL=strSQL+" and activityid in ('0000009075','0000005075') ";
			sql_id = "LWMissionReassignSql18";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
			str_sql = my_sql.getString();
		}
	}
	
	//ֻ�����ǰɨ��
	if(fm.ActivityIDQ.value=="008")
	{
//		strSQL= "select missionprop1,'��ǰɨ��',  missionprop3,missionprop4, missionprop20,"
//	        + " missionprop6,defaultoperator, (select username from llclaimuser where usercode = defaultoperator),"
//          + " missionid,submissionid,activityid,missionprop9  from lwmission where  activityid='0000005010'  "
//          + " and (case when (select comcode from llclaimuser where usercode = lwmission.defaultoperator) is not null then (select comcode from llclaimuser where usercode = lwmission.defaultoperator) else missionprop20 end) like '" + document.all('ComCode').value + "%%'"
//    		  + getWherePart( 'missionprop1','ClmNOQ1')
//    		  + getWherePart( 'defaultoperator','OperatorQ');
		sql_id = "LWMissionReassignSql19";
		my_sql = new SqlClass();
		my_sql.setResourceName("claim.LWMissionReassignInputSql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
				  
   }
	var arrResult = easyExecSql(str_sql);//prompt("",strSQL);
	if(arrResult==null ||arrResult=="")
	{
		LWMissionGrid.clearData();
		alert("û�в�ѯ���κ������¼������������������");
		document.all('ActivityIDQ').value="";
		document.all('ActivityIDQName').value="";
		document.all('ClmNOQ').value="";
		document.all('OperatorQ').value="";
		
//		fm.ActivityIDQ.value="";
//		fm.ActivityIDQName.value="";		
//		fm.ClmNOQ.value="";
//		fm.OperatorQ.value="";
		return;
	}
    else
   	{
		turnPage.queryModal(str_sql, LWMissionGrid);  
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
	fm.OComCode.value=LWMissionGrid.getRowColData(tRow-1,5);//ԭ��������
}

//[��ѯ�����û�]��ť
function LLClaimUserQueryClick()
{
	//var tRow = LWMissionGrid.getSelNo();
	//if( tRow < 1||tRow == null )
	//{
	//	alert( "����ѡ��һ����¼!" );
	//	return;
	//}
	var strUrl="LLClaimUserQueryMain.jsp";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//[ָ��ȷ��]��ť
function DesignateConfirmClick()
{
	//var tRow = LWMissionGrid.getSelNo();
	//if( tRow < 1||tRow == null )
	//{
	//	alert( "����ѡ��һ����¼!" );
	//	return;
	//}
//	if(fm.DesignateOperator.value=="" ||fm.DesignateOperator.value==null)
//	{
//		alert( "��ѡ�����Ա��" );
//		return;		
//	}
	//alert("fm.ComCode.value: "+fm.ComCode.value);
	//alert("fm.OComCode.value: "+fm.OComCode.value);
	//if(!(fm.ComCode.value==fm.OComCode.value)){
	//	alert("��ѡ��Ĳ���Ա��ԭ����Ա����ͬһ����!")
	//	return;
	//}
	fm.fmtransact.value="User||UPDATE";
	fm.action="LWMissionReassignSave.jsp";
	submitForm();
}

//ȷ�Ϻ�ˢ��ҳ��(Ĭ�ϲ�����)
function Renewpage()
{
	//var tRow = LWMissionGrid.getSelNo()-1;
	//LWMissionGrid.setRowColData(tRow,8,fm.DesignateOperator.value);
	//fm.DefaultOperator.value=fm.DesignateOperator.value;
}

//�����ύ����
function submitForm()
{
    //�ύ����
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

//Ԥ�������ύ�����,����
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
    Renewpage();
}
