//�������ƣ�WFEdorAutoUW.js
//�����ܣ���ȫ������-�Զ��˱�
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

//var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
/*********************************************************************
 *  ��ѯ������������
 *  ����:��ѯ������������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 /*
function easyQueryClickAll()
{
	// ��ʼ������
	initAllGrid();

	// ��дSQL���
	var strSQL = "";
/*	
	strSQL =  " select missionprop1, missionprop2, " 
			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
			+ " missionprop11,"
			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
			+ " createoperator, makedate, missionid, submissionid, activityid, "
			+ " MissionProp3, MissionProp5, MissionProp7, MissionProp12 "
			+ " from lwmission where 1=1  "
			+ " and activityid = '0000000004' "  //��������ȫ-�Զ��˱�
			+ " and processid = '0000000000' "
			+ " and defaultoperator is null "
			+ getWherePart('missionprop1', 'EdorAcceptNo')
			+ getWherePart('missionprop2', 'OtherNo')
			+ getWherePart('missionprop3', 'OtherNoType')	
			+ getWherePart('missionprop4', 'EdorAppName')
			+ getWherePart('missionprop5', 'AppType')
			+ getWherePart('missionprop7', 'ManageCom','like')	
			+ getWherePart('MakeDate', 'MakeDate')
			+ " and missionprop7 like '" + manageCom + "%%'"	
			+ " order by MakeDate, MakeTime ";
*/	
/*	
	var sqlid1="WFEdorAutoUWSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.WFEdorAutoUWInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.OtherNo.value);
	mySql1.addSubPara(fm.OtherNoType.value);
	mySql1.addSubPara(fm.EdorAppName.value);
	mySql1.addSubPara(fm.AppType.value);
	mySql1.addSubPara(fm.ManageCom.value);
	mySql1.addSubPara(fm.MakeDate.value);
	mySql1.addSubPara(manageCom);
	strSQL=mySql1.getString();

	turnPage.queryModal(strSQL, AllGrid);// mySql.getString()�Ϳ��Ի�ö�Ӧ��SQL��
	
	return true;
}

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 /*
function easyQueryClickSelf()
{
	// ��ʼ������
	initSelfGrid();

	// ��дSQL���
	var strSQL = "";
/*	
	strSQL =  " select missionprop1, missionprop2, " 
			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
			+ " missionprop11, "
			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
			+ " createoperator, makedate, missionid, submissionid, activityid,"
			+ " MissionProp3, MissionProp5, MissionProp7, MissionProp12 "
			+ " from lwmission where 1=1  "
			+ " and activityid = '0000000004' "  //��������ȫ-�Զ��˱�
			+ " and processid = '0000000000' "
			+ " and defaultoperator ='" + operator + "'"
			+ " order by MakeDate, MakeTime ";
*/	
/*
	var sqlid2="WFEdorAutoUWSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.WFEdorAutoUWInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(operator);//ָ������Ĳ���
	strSQL=mySql2.getString();
	
	turnPage2.queryModal(strSQL, SelfGrid);
	
	return true;
}

/*********************************************************************
 *  ��ת�������ҵ����ҳ��
 *  ����: ��ת�������ҵ����ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function GoToBusiDeal()
{
	
	var checkAcount = 0;
	for (i = 0; i < PrivateWorkPoolGrid.mulLineCount; i++) 
	{
		if (PrivateWorkPoolGrid.getChkNo(i)) 
		{ 
	    	checkAcount++;
	    	break;
	    }
	}
	if (checkAcount < 1)
	{
		alert("������ѡ��һ��Ҫ����������");
		return; 
	}
	fm.MissionID.value = PrivateWorkPoolGrid.getRowColData(0, 15);
	fm.ActivityID.value = PrivateWorkPoolGrid.getRowColData(0, 18);
	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	  
	document.all('fmtransact').value = "INSERT||AUTOUWENDORSE";
	fm.action="./WFEdorAutoUWSave.jsp";
	document.getElementById("fm").submit();

}
 /*
function GoToBusiDeal()
{
	
	var checkAcount = 0;
	for (i = 0; i < SelfGrid.mulLineCount; i++) 
	{
		if (SelfGrid.getChkNo(i)) 
		{ 
	    	checkAcount++;
	    	break;
	    }
	}
	if (checkAcount < 1)
	{
		alert("������ѡ��һ��Ҫ����������");
		return; 
	}

	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  
	document.all('fmtransact').value = "INSERT||AUTOUWENDORSE";
	fm.action="./WFEdorAutoUWSave.jsp";
	document.getElementById("fm").submit();

}

/*********************************************************************
 *  ��������
 *  ����: ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function applyMission()
{

	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	//alert(PublicWorkPoolGrid.getRowData(selno));
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 15);
	//alert(PublicWorkPoolGrid.getRowColData(selno, 15));
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 16);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 18);
	
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("ѡ������Ϊ�գ�");
	      return;
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //�ύ
	
}
 /*
function applyMission()
{

	var selno = AllGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	
	fm.MissionID.value = AllGrid.getRowColData(selno, 9);
	fm.SubMissionID.value = AllGrid.getRowColData(selno, 10);
	fm.ActivityID.value = AllGrid.getRowColData(selno, 11);
	
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("ѡ������Ϊ�գ�");
	      return;
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  ��ִ̨����Ϸ�����Ϣ
 *  ����: ��ִ̨����Ϸ�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{ 
  
		showInfo.close();
	if (FlagStr == "Succ" )	
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  	//ˢ�²�ѯ���
		//easyQueryClickAll();
		//easyQueryClickSelf();	
		jQuery("#publicSearch").click();	
		jQuery("#privateSearch").click();	
		
	}
    else
    {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
}

/*********************************************************************
 *  �򿪹��������±��鿴ҳ��
 *  ����: �򿪹��������±��鿴ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function showNotePad()
{
	var selno;
	var checkAcount = 0;
	for (i = 0; i < PrivateWorkPoolGrid.mulLineCount; i++) 
	{
		if (PrivateWorkPoolGrid.getChkNo(i)) 
		{ 
	    	checkAcount++;
	    	selno = i;
	    }
	}
	if (checkAcount != 1)
	{
		alert("��ѡ��һ������");
		return; 
	}
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 15);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 16);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 18);
	var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}
 /*
function showNotePad()
{
	var selno;
	var checkAcount = 0;
	for (i = 0; i < SelfGrid.mulLineCount; i++) 
	{
		if (SelfGrid.getChkNo(i)) 
		{ 
	    	checkAcount++;
	    	selno = i;
	    }
	}
	if (checkAcount != 1)
	{
		alert("��ѡ��һ������");
		return; 
	}
	
	var MissionID = SelfGrid.getRowColData(selno, 9);
	var SubMissionID = SelfGrid.getRowColData(selno, 10);
	var ActivityID = SelfGrid.getRowColData(selno, 11);
	var OtherNo = SelfGrid.getRowColData(selno, 1);
	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}
*/