//�������ƣ�WFEdorConfirm.js
//�����ܣ���ȫ������-��ȫȷ��
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mFlag = "0";
var sqlresourcename = "bq.WFEdorConfirmInputSql";

/*********************************************************************
 *  ��ѯ�����������
 *  ����:��ѯ�����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickAll()
{
	// ��ʼ�����
	initAllGrid();

	// ��дSQL���
	var strSQL = "";
/*	
	strSQL =  " select missionprop1, missionprop2, " 
			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
			+ " missionprop11, "
			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
			+ " createoperator, a.makedate, missionid, submissionid, activityid, "
			+ " (select codename from ldcode where code=c.uwstate and codetype='edorcontuw'),"
			+ " c.uwstate ,"
			+ " (select decode(count(*),0,'ͬ��','��ͬ��') from lpcuwmaster d4  where d4.edoracceptno=b.edoracceptno and customeridea='1'),b.operator,c.edorappdate,(select count(*) from ldcalendar where commondate > c.edorappdate and commondate <= '"+curDay+"' and workdateflag = 'Y')"
			+ " from lwmission a,lpedorapp b,lpedormain c where 1=1  and a.missionprop1 = b.edoracceptno and b.edoracceptno=c.edoracceptno "
			+ " and activityid = '0000000009' "  //��������ȫ-��ȫȷ��
			+ " and processid = '0000000000' "
			+ " and defaultoperator is null "
			+ getWherePart('missionprop1', 'EdorAcceptNoSearch')
			+ getWherePart('missionprop2', 'OtherNo')
			+ getWherePart('missionprop3', 'OtherNoType')	
			+ getWherePart('missionprop4', 'EdorAppName')
			+ getWherePart('missionprop5', 'AppType')
			+ getWherePart('missionprop7', 'ManageCom','like')	
			+ getWherePart('a.MakeDate', 'MakeDate')		
			+ " and missionprop7 like '" + manageCom + "%%'"				
			+ " order by c.edorappdate,a.MakeDate, a.MakeTime ";
*/	
	var sqlid1="WFEdorConfirmInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(curDay);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAcceptNoSearch'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNoType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAppName'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('AppType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('MakeDate'))[0].value);
	mySql1.addSubPara(manageCom);
	strSQL=mySql1.getString();
		
	turnPage.queryModal(strSQL, AllGrid);// mySql.getString()�Ϳ��Ի�ö�Ӧ��SQL��
	HighlightAllRow();
	return true;
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,17);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,19);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightAllRow(){
		for(var i=0; i<AllGrid.mulLineCount; i++){
  	var days = AllGrid.getRowColData(i,17);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,19);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickSelf()
{
	// ��ʼ�����
	//alert("a");
	initSelfGrid();
	

	// ��дSQL���
	var strSQL = "";
/*	
	strSQL =  " select missionprop1, missionprop2, " 
			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
			+ " missionprop11, "
			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
			+ " missionprop7, "
			+ " a.createoperator, a.makedate, a.missionid, submissionid, activityid ,(select codename from ldcode where code=c.uwstate and codetype='edorcontuw'), "
			+ " c.uwstate,b.EdorAppName,(select paytodate from lcpol where contno=b.otherno and polno=mainpolno and rownum=1), "
			//+ " (select appntno from lccont where contno=b.otherno) Ͷ���� ,"
			//+ " (select InsuredNo from lccont where contno=b.otherno) ��һ������ ,"
			//+ " ''  �ڶ������� ,'' ����������,(select edortype from lpedoritem c where c.edoracceptno=b.edoracceptno and rownum=1), "
			//+ " to_char(sysdate,'yyyy-mm-dd') ת������,'' ���ظ�����,'' VIP�ͻ�,'' �Ǽ�ҵ��Ա,"
			+ " (select decode(count(*),0,'ͬ��','��ͬ��') from lpcuwmaster d4  where d4.edoracceptno=b.edoracceptno and customeridea='1'),"
			+ " (select count(*)  from lpcuwmaster d4  where d4.edoracceptno=b.edoracceptno and customeridea='1'), c.edorappdate,(select count(*) from ldcalendar where commondate > c.edorappdate and commondate <= '"+curDay+"' and workdateflag = 'Y')"
			// modify by jiaqiangli 2009-04-29 ȡlpedormain�ĺ˱�����
			+ " from lwmission a,lpedorapp b,lpedormain c where 1=1  and a.missionprop1 = b.edoracceptno and b.edoracceptno=c.edoracceptno "
			+ " and activityid = '0000000009' "  //��������ȫ-��ȫȷ��
			+ " and processid = '0000000000' "
			+ " and defaultoperator ='" + operator + "'"
			+ " order by a.MakeDate, a.MakeTime ";
*/	
	var sqlid2="WFEdorConfirmInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(curDay);//ָ������Ĳ���
	mySql2.addSubPara(operator);
	strSQL=mySql2.getString();
	
	turnPage2.queryModal(strSQL, SelfGrid);
	HighlightSelfRow();
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
	
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	mFlag = "1";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	
	
	var uWstate = PrivateWorkPoolGrid.getRowColData(selno,18);
	
	//add by jiaqiangli 2009-04-03 ��ȫȷ������
	var tCustomerIdea = PrivateWorkPoolGrid.getRowColData(selno,10);
	// uWstate Ϊ��ͬ�˱�����
	//	Edorappstate	1	��ͬ��
	//	Edorappstate	3	�ܱ�
	//	Edorappstate	9	ͬ��
	if (document.all("AppUWState").value != null && document.all("AppUWState").value != '9') {
		alert("ֻ�б�ȫ�˱�����Ϊͬ��Ĳſ����ύ��ȫȷ�ϣ�");
		return;
	}
	else {
		// �˷�֧�жϿ��ܲ����ڣ��˹��˱����ܻ�У��ס����Ϊ�˱���������Ǽ���
		if (uWstate != null && uWstate == '1') {
			alert("��ȫ�˱�����Ϊͬ�⵫��ͬ�˱�����Ϊ�ܱ��Ĳ������ύ��ȫȷ�ϣ�");
			return;
		}
		// �жϺ�ͬ�˱�����
		if (uWstate != null && uWstate == '4') {
			if (tCustomerIdea != null && tCustomerIdea == '1') {
				alert("ֻ�пͻ����Ϊͬ��Ĳſ����ύ��ȫȷ�ϣ�");
				return;
			}
		}
	}
	//add by jiaqiangli 2009-04-03 ��ȫȷ������ 

	if(uWstate=='1')
	{
		alert("�˱�����Ϊ�ܱ���������ȫȷ�ϣ�ֻ�ܲ�����ȫ��ֹ��");
		return;
	}
	
	if(!checkprint()) return;

	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	lockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	
	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
}
 /*
function GoToBusiDeal()
{
	
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	mFlag = "1";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	var tActivityID = SelfGrid.getRowColData(selno, 11);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	
	
	var uWstate = SelfGrid.getRowColData(selno,13);
	
	//add by jiaqiangli 2009-04-03 ��ȫȷ������
	var tCustomerIdea = SelfGrid.getRowColData(selno,17);
	// uWstate Ϊ��ͬ�˱�����
	//	Edorappstate	1	��ͬ��
	//	Edorappstate	3	�ܱ�
	//	Edorappstate	9	ͬ��
	if (document.all("AppUWState").value != null && document.all("AppUWState").value != '9') {
		alert("ֻ�б�ȫ�˱�����Ϊͬ��Ĳſ����ύ��ȫȷ�ϣ�");
		return;
	}
	else {
		// �˷�֧�жϿ��ܲ����ڣ��˹��˱����ܻ�У��ס����Ϊ�˱���������Ǽ���
		if (uWstate != null && uWstate == '1') {
			alert("��ȫ�˱�����Ϊͬ�⵫��ͬ�˱�����Ϊ�ܱ��Ĳ������ύ��ȫȷ�ϣ�");
			return;
		}
		// �жϺ�ͬ�˱�����
		if (uWstate != null && uWstate == '4') {
			if (tCustomerIdea != null && tCustomerIdea == '1') {
				alert("ֻ�пͻ����Ϊͬ��Ĳſ����ύ��ȫȷ�ϣ�");
				return;
			}
		}
	}
	//add by jiaqiangli 2009-04-03 ��ȫȷ������ 

	if(uWstate=='1')
	{
		alert("�˱�����Ϊ�ܱ���������ȫȷ�ϣ�ֻ�ܲ�����ȫ��ֹ��");
		return;
	}
	
	if(!checkprint()) return;

	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	lockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	
	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
}
*/
function submitUW()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	
	mFlag = "0";
	var tCustomerIdea = PrivateWorkPoolGrid.getRowColData(selno,10)
//	if(tCustomerIdea=='0')
//	{
//		alert("�ͻ����Ϊͬ�⣬�������ٴ��ύ�˱�");
//		return;
//	}
  //alert(tCustomerIdea);

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	
	var uWstate = PrivateWorkPoolGrid.getRowColData(selno,18);
	//add by jiaqiangli 2009-04-03 ����¼��ǿ���˹��˱�ԭ����ж�
	if (document.all("MMUWReason").value == null || trim(document.all("MMUWReason").value) == '') {
		alert("��¼��ǿ���˹��˱�ԭ��");
		return;
	} 
	
//	edorappstate	1	��ͬ��
//	Edorappstate	3	�ܱ�
//	Edorappstate	9	ͬ��
	//modify by jiaqiangli 2009-03-24 ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�
//	if (document.all("AppUWState").value != null && document.all("AppUWState").value == '1') {
//		alert("ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�!");
//		return;
//	}
//	else if (document.all("AppUWState").value == '9') {
//		if (uWstate != null && uWstate != '' && uWstate != '4') {
//			alert("ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�!");
//			return;	
//		}
//	}
	
	if (document.all("AppUWState").value != null && document.all("AppUWState").value != '1' && document.all("AppUWState").value != '4') {
		alert("ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�!");
		return;
	}
	
	
	
	//tCustomerIdea='1' ��Ҫǿ���˹��˱� 
	//����û�����˹��˱���Ϊ��׼�壬��ȫ�˱�����Ӧ��Ϊͬ�⣬��������ǲ�����ǿ�ƺ˱���
	tCustomerIdea='1';
	
	//1Ϊ��ͬ��
//	else {
//		alert("ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�!");
//		return;
//	}
//	if(uWstate=='9')
//	{
//		alert("�˱�����Ϊ��׼�壬�������ٴ��ύ�˱���");
//		return;
//	}
//	if(uWstate=='2')
//	{
//		alert("�˱�����Ϊά��ԭ�������������ٴ��ύ�˱���");
//		return;
//	}
//	if(uWstate=='1')
//	{
//		alert("�˱�����Ϊ�ܱ����������ٴ��ύ�˱���");
//		return;
//	}

	//alert(SelfGrid.colCount);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.OtherNo1.value       = PrivateWorkPoolGrid.getRowColData(selno, 2);
	fm.OtherNoType1.value   = PrivateWorkPoolGrid.getRowColData(selno, 11);
	fm.EdorAppName1.value   = PrivateWorkPoolGrid.getRowColData(selno, 14);
	fm.Apptype1.value       = PrivateWorkPoolGrid.getRowColData(selno, 15);
	fm.ManageCom1.value     = PrivateWorkPoolGrid.getRowColData(selno, 16);
	fm.AppntName1.value     = PrivateWorkPoolGrid.getRowColData(selno, 4);
	fm.PaytoDate1.value     = PrivateWorkPoolGrid.getRowColData(selno, 17);
	//fm.CustomerNo1.value    = SelfGrid.getRowColData(selno, 16);
	//fm.InsuredName1.value   = SelfGrid.getRowColData(selno, 17);
	//fm.InsuredName2.value   = SelfGrid.getRowColData(selno, 18);
	//fm.InsuredName3.value   = SelfGrid.getRowColData(selno, 19);
	//fm.EdorType1.value      = SelfGrid.getRowColData(selno, 20);
	//fm.theCurrentDate1.value= SelfGrid.getRowColData(selno, 21);
	//fm.BackDate1.value      = SelfGrid.getRowColData(selno, 22);
	//fm.VIP1.value           = SelfGrid.getRowColData(selno, 23);
	//fm.StarAgent1.value     = SelfGrid.getRowColData(selno, 24);
	
	
	fm.uWstate1.value       = uWstate;
	fm.CustomerIdea.value   = tCustomerIdea;
	
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	fm.ActivityID.value	= tActivityID;
	
	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||SUBMITUW";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
}
/*
function submitUW()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	
	mFlag = "0";
	var tCustomerIdea = SelfGrid.getRowColData(selno,17)
//	if(tCustomerIdea=='0')
//	{
//		alert("�ͻ����Ϊͬ�⣬�������ٴ��ύ�˱�");
//		return;
//	}
  //alert(tCustomerIdea);

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	
	var uWstate = SelfGrid.getRowColData(selno,13);
	
	//add by jiaqiangli 2009-04-03 ����¼��ǿ���˹��˱�ԭ����ж�
	if (document.all("MMUWReason").value == null || trim(document.all("MMUWReason").value) == '') {
		alert("��¼��ǿ���˹��˱�ԭ��");
		return;
	} 
	
//	edorappstate	1	��ͬ��
//	Edorappstate	3	�ܱ�
//	Edorappstate	9	ͬ��
	//modify by jiaqiangli 2009-03-24 ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�
	if (document.all("AppUWState").value != null && document.all("AppUWState").value == '1') {
		alert("ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�!");
		return;
	}
	else if (document.all("AppUWState").value == '9') {
		if (uWstate != null && uWstate != '' && uWstate != '4') {
			alert("ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�!");
			return;	
		}
	}
	
	//tCustomerIdea='1' ��Ҫǿ���˹��˱� 
	//����û�����˹��˱���Ϊ��׼�壬��ȫ�˱�����Ӧ��Ϊͬ�⣬��������ǲ�����ǿ�ƺ˱���
	tCustomerIdea='1';
	
	//1Ϊ��ͬ��
//	else {
//		alert("ֻ�б�ȫ�˱�����Ϊ�ܱ���ͬ�⵫��ͬ�˱�����Ϊ�α�׼��Ŀ���ǿ���ύ�˱�!");
//		return;
//	}
//	if(uWstate=='9')
//	{
//		alert("�˱�����Ϊ��׼�壬�������ٴ��ύ�˱���");
//		return;
//	}
//	if(uWstate=='2')
//	{
//		alert("�˱�����Ϊά��ԭ�������������ٴ��ύ�˱���");
//		return;
//	}
//	if(uWstate=='1')
//	{
//		alert("�˱�����Ϊ�ܱ����������ٴ��ύ�˱���");
//		return;
//	}

	//alert(SelfGrid.colCount);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.OtherNo1.value       = SelfGrid.getRowColData(selno, 2);
	fm.OtherNoType1.value   = SelfGrid.getRowColData(selno, 3);
	fm.EdorAppName1.value   = SelfGrid.getRowColData(selno, 14);
	fm.Apptype1.value       = SelfGrid.getRowColData(selno, 5);
	fm.ManageCom1.value     = SelfGrid.getRowColData(selno, 6);
	fm.AppntName1.value     = SelfGrid.getRowColData(selno, 4);
	fm.PaytoDate1.value     = SelfGrid.getRowColData(selno, 15);
	//fm.CustomerNo1.value    = SelfGrid.getRowColData(selno, 16);
	//fm.InsuredName1.value   = SelfGrid.getRowColData(selno, 17);
	//fm.InsuredName2.value   = SelfGrid.getRowColData(selno, 18);
	//fm.InsuredName3.value   = SelfGrid.getRowColData(selno, 19);
	//fm.EdorType1.value      = SelfGrid.getRowColData(selno, 20);
	//fm.theCurrentDate1.value= SelfGrid.getRowColData(selno, 21);
	//fm.BackDate1.value      = SelfGrid.getRowColData(selno, 22);
	//fm.VIP1.value           = SelfGrid.getRowColData(selno, 23);
	//fm.StarAgent1.value     = SelfGrid.getRowColData(selno, 24);
	
	
	fm.uWstate1.value       = uWstate;
	fm.CustomerIdea.value   = tCustomerIdea;
	
	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  
	document.all('fmtransact').value = "INSERT||SUBMITUW";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
}
*/
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
	mFlag = "0";
	//begin zbx 20110512
	fm.EdorAcceptNo.value	= PublicWorkPoolGrid.getRowColData(selno, 1);
	//end zbx 20110512
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 22);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 23);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 25);
	fm.ApplyOperator.value=PublicWorkPoolGrid.getRowColData(selno, 11);  //��ȫ������
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("ѡ������Ϊ�գ�");
	      return;
	}
	if(fm.ApplyOperator.value!=operator) 
	{
		  alert("��ȫȷ�����������˱�����ͬһ�ˣ���ѡ���������������в���");
	      return false;
	
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	
	fm.action = "../bq/MissionApply.jsp?Confirm=1";
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
	
	mFlag = "0";
	//begin zbx 20110512
	fm.EdorAcceptNo.value	= AllGrid.getRowColData(selno, 1);
	//end zbx 20110512
	fm.MissionID.value = AllGrid.getRowColData(selno, 9);
	fm.SubMissionID.value = AllGrid.getRowColData(selno, 10);
	fm.ActivityID.value = AllGrid.getRowColData(selno, 11);
	fm.ApplyOperator.value=AllGrid.getRowColData(selno, 15);  //��ȫ������
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("ѡ������Ϊ�գ�");
	      return;
	}
	if(fm.ApplyOperator.value!=fm.Operator.value) 
	{
		  alert("��ȫȷ�����������˱�����ͬһ�ˣ���ѡ���������������в���");
	      return false;
	
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	
	fm.action = "../bq/MissionApply.jsp?Confirm=1";
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
	
	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	unlockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	
  //alert(11);
	if (FlagStr == "Succ" ||FlagStr=='' || FlagStr==null )	
	{
	  if(mFlag == "1")
	  {
	  var iEdorAcceptNo = fm.EdorAcceptNo.value;
//    var rSQL = "select EdorType from lpedoritem where  EdorAcceptNo = '"+iEdorAcceptNo+"'";
    
    	var rSQL = "";
    	var sqlid11="WFEdorConfirmInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(iEdorAcceptNo);//ָ������Ĳ���
		rSQL = mySql11.getString();
    
    var rResult;
    rResult = easyExecSql(rSQL, 1, 0,"","",1);
    if(rResult[0][0]!='RB'){
	  		  	//  alert(mFlag);
	     if (window.confirm("�Ƿ��ӡ���α�ȫ������?"))
         {
	         fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
	         fm.target="f1print";
	         document.getElementById("fm").submit();
         }
    }

     }
	 else
	 {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
		
	 }
	  	//ˢ�²�ѯ���
	  	 // alert(2);
		//easyQueryClickAll();
		jQuery("#publicSearch").click();
		//easyQueryClickSelf();	
		jQuery("#privateSearch").click();
		checkPrintNotice();
		//add by jiaqiangli 2009-04-14 ���Ӵ�ӡ�ֽ��ֵ��ʾ
		checkPrintCashValueTable();		
	}
    else
    {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }

}

//add by jiaqiangli 2009-03-17 xs��Ҫ��ʾ��ӡ�ֽ��ֵ��
 function checkPrintCashValueTable() {
 var tEdorAcceptNo=fm.EdorAcceptNo.value;
 // add by jiaqiangli 2009-04-19
 if (tEdorAcceptNo != null && tEdorAcceptNo != '') {
 // ���˵����Ŀǰֻ�����ĸ�
   	var sql = "" ;
//   	var sql = " select 1 from dual where exists (select 'X' from lpedoritem " +" where edoracceptno = '" + tEdorAcceptNo + "' and edortype in ( 'PA','FM','PT','XS'))";
    
    var sqlid3="WFEdorConfirmInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
	sql = mySql3.getString();
    
    var sNeedGetFlag =  easyExecSql(sql, 1, 0);
    if (sNeedGetFlag == null) sNeedGetFlag = "";
    if (sNeedGetFlag=="1") {
          alert("��ʾ�����ӡ�µ��ֽ��ֵ��");
          document.all("divCashValue").style.display = "";
    }
  }	
}
 
function PrintCashValueTable() {
	 var tEdorAcceptNo=fm.EdorAcceptNo.value;
	   	var sql = "" ;
//	   	var sql = " select contno from lpedoritem " +" where edoracceptno = '" + tEdorAcceptNo + "' and edortype in ( 'PA','FM','PT','XS')";
	    
	    var sqlid4="WFEdorConfirmInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		sql = mySql4.getString();
		
	    var sContNo =  easyExecSql(sql, 1, 0);
	    if (sContNo == null) sContNo = "";
	    
	    fm.ContNo.value = sContNo;
	    fm.action = "./CashValuePrintSave.jsp";
	    fm.target = "f1print";
	    document.getElementById("fm").submit();
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
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
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
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
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

/*********************************************************************
 *  �ֶ���ֹ����
 *  ����: �ֶ���ֹ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function doOverDue()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;

	var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}
 /*
function doOverDue()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;

	var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}


function doJB()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	if(!confirm("ȷ��Ҫ���оܱ�����ô��")){
		
		return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tContNo = SelfGrid.getRowColData(selno, 2);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	var tActivityID = SelfGrid.getRowColData(selno, 11);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID; 
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	fm.ContNo.value	= tContNo;

	var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueAndNewItem.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}
*/
function doJB()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	if(!confirm("ȷ��Ҫ���оܱ�����ô��")){
		
		return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tContNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID; 
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	fm.ContNo.value	= tContNo;

	var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueAndNewItem.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}
 function checkPrintNotice()
{
	  
	  var tEdorAcceptNo=fm.EdorAcceptNo.value;
	  var sql = ""; 
//   	var sql = " select 1 from dual where exists (select 'X' from LJaGet " +
//   	" where othernotype = '10' and otherno = '" + tEdorAcceptNo + 
//   	"' and sumgetmoney <> 0)";

   	    //begin zbx 20110512
   	    if(tEdorAcceptNo==null || tEdorAcceptNo=="")
   	    {
   	    	return ;
   	    }
   	    //end zbx 20110512
   	    
   		var sqlid5="WFEdorConfirmInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		sql = mySql5.getString();
		
    var sNeedGetFlag =  easyExecSql(sql, 1, 0);
    if (sNeedGetFlag == null) sNeedGetFlag = "";
    if (sNeedGetFlag=="1")
    {
          document.all("divGetNotice").style.display = "";
          alert("���ӡ����֪ͨ�飡");
    }	
}

function GetNotice()
{
    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var strSQL = "";
 //   var strSQL = "select prtseq from loprtmanager where code = 'BQ32' and otherno = '"+tEdorAcceptNo+"'";
    var sResult;
    
   		var sqlid6="WFEdorConfirmInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		strSQL = mySql6.getString();
		
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("��ѯ����֪ͨ����Ϣʧ�ܣ�");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp";
    fm.target = "f1print";
    document.getElementById("fm").submit();
}

function PayNotice()
{
    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var strSQL = "";
//    var strSQL = "select prtseq from loprtmanager where code = 'BQ31' and otherno = '"+tEdorAcceptNo+"'";
    var sResult;
    
    	var sqlid7="WFEdorConfirmInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		strSQL = mySql7.getString();
		
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("��ѯ�շ�֪ͨ����Ϣʧ�ܣ�");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp";
    fm.target = "f1print";
    document.getElementById("fm").submit();
}
/**
 * �ж��Ƿ���ʾ����֪ͨ��
 * XinYQ rewrote on 2007-05-18
 */
 function checkPayNotice()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	//��ѯ��ȫ�˱����
	initUWIdea();
	
    var QuerySQL, arrResult;
/*    QuerySQL = "select nvl(SumDuePayMoney, 0) "
             +   "from LJSPay  a "
             +  "where 1 = 1 "
             +     getWherePart("OtherNo", "EdorAcceptNo")
             +    " and OtherNoType = '10' "
             +    " and not exists (select 'X' from ljtempfee b where b.otherno='"+tEdorAcceptNo+"' and a.otherno=b.otherno)";
*/
    //alert(QuerySQL);
    try
    {
    	var sqlid8="WFEdorConfirmInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);//ָ������Ĳ���
		mySql8.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		QuerySQL = mySql8.getString();
		
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ���˷���Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null && arrResult[0][0] != "")
    {
        try
        {
            var sGetMoney = arrResult[0][0];
            var fGetMoney = parseFloat(sGetMoney);
            if (fGetMoney > 0)
            {
                document.all("divPayNotice").style.display = "block";
            } 
        }
        catch (ex) {}
    } //arrResult != null
    else
    {
    	document.all("divPayNotice").style.display = "none";
    }
    
    HighlightSelfRow();
}
 /*
function checkPayNotice()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	//��ѯ��ȫ�˱����
	initUWIdea();
	
    var QuerySQL, arrResult;
/*    QuerySQL = "select nvl(SumDuePayMoney, 0) "
             +   "from LJSPay  a "
             +  "where 1 = 1 "
             +     getWherePart("OtherNo", "EdorAcceptNo")
             +    " and OtherNoType = '10' "
             +    " and not exists (select 'X' from ljtempfee b where b.otherno='"+tEdorAcceptNo+"' and a.otherno=b.otherno)";
*/
    //alert(QuerySQL);
    /*
    try
    {
    	var sqlid8="WFEdorConfirmInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
		mySql8.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		QuerySQL = mySql8.getString();
		
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ���˷���Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null && arrResult[0][0] != "")
    {
        try
        {
            var sGetMoney = arrResult[0][0];
            var fGetMoney = parseFloat(sGetMoney);
            if (fGetMoney > 0)
            {
                document.all("divPayNotice").style.display = "block";
            } 
        }
        catch (ex) {}
    } //arrResult != null
    else
    {
    	document.all("divPayNotice").style.display = "none";
    }
    
    HighlightSelfRow();
}*/

function initUWIdea(){
	var tSql="";
//	var tSql="select State,(select codename from ldcode where codetype='edorappstate' and code=state),UWIdea from LPAppUWMasterMain where EdorAcceptNo='"+fm.EdorAcceptNo.value+"'";
	
		var sqlid9="WFEdorConfirmInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
		tSql = mySql9.getString();
	
	var brr = easyExecSql(tSql);

    if ( brr )
    {
        fm.AppUWState.value = brr[0][0];
        fm.AppUWStateName.value = brr[0][1];
        fm.AppUWIdea.value = brr[0][2];
    }
}

function checkprint(){
	var tSql="";
//	var tSql="select nvl(count(*),'0') from LOPRTManager where otherno=(select contno from LPEdorMain where EdorAcceptNo='"+fm.EdorAcceptNo.value+"') and code='BQ84' and stateflag='0'";
	
		var sqlid10="WFEdorConfirmInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
		tSql = mySql10.getString();
	
	var brr = easyExecSql(tSql);

    if ( brr[0][0]>0 )
    {
        alert("��������δ��ӡ�ľܱ�֪ͨ�飬���ȴ�ӡ�ٽ���ȷ�ϣ�");
        return false;
    }
    
    return true;
}
