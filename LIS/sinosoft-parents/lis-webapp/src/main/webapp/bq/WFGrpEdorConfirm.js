//�������ƣ�WFEdorConfirm.js
//�����ܣ���ȫ������-��ȫȷ��
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mFlag = "0";
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
	
	var sqlid1="DSHomeContSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.WFGrpEdorConfirmInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(manageCom+"%%");//ָ������Ĳ���
	mySql1.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.OtherNoType.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorAppName.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.AppType.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
	
	mySql1.addSubPara(fm.EdorAcceptNoSearch.value);//ָ������Ĳ���
	strSQL=mySql1.getString();
	
//	strSQL =  " select missionprop1, missionprop2, " 
//			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//			+ " missionprop11, "
//			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//			+ " createoperator, makedate, missionid, submissionid, activityid "
//			+ " from lwmission where 1=1  "
//			+ " and activityid = '0000008009' "  //��������ȫ-��ȫȷ��
//			+ " and processid = '0000000000' "
//			+ " and defaultoperator is null "
//			+ getWherePart('missionprop1', 'EdorAcceptNoSearch')
//			+ getWherePart('missionprop2', 'OtherNo')
//			+ getWherePart('missionprop3', 'OtherNoType')	
//			+ getWherePart('missionprop4', 'EdorAppName')
//			+ getWherePart('missionprop5', 'AppType')
//			+ getWherePart('missionprop7', 'ManageCom')	
//			+ getWherePart('MakeDate', 'MakeDate')		
//			+ " and missionprop7 like '" + manageCom + "%%'"				
//			+ " order by MakeDate, MakeTime ";
		
	turnPage.queryModal(strSQL, AllGrid);
	
	return true;
}

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
	initSelfGrid();

	// ��дSQL���
	var strSQL = "";
	
	var sqlid2="DSHomeContSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.WFGrpEdorConfirmInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(operator);//ָ������Ĳ���
	strSQL=mySql2.getString();
	
//	strSQL =  " select missionprop1, missionprop2, " 
//			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//			+ " missionprop11, "
//			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//			+ " createoperator, makedate, missionid, submissionid, activityid "
//			+ " from lwmission where 1=1  "
//			+ " and activityid = '0000008009' "  //��������ȫ-��ȫȷ��
//			+ " and processid = '0000000000' "
//			+ " and defaultoperator ='" + operator + "'"
//			+ " order by MakeDate, MakeTime ";
	
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
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;

	lockScreen('DivLockScreen');
	
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
	fm.action="./WFGrpEdorConfirmSave.jsp";
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

	var selno = AllGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}
	
	mFlag = "0";

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
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
	     
	unlockScreen('DivLockScreen');
	
	if (FlagStr == "Succ")	
	{
	  if(mFlag == "1")
	  {
	     if (window.confirm("��ȫȷ�ϳɹ����Ƿ��ӡ���α�ȫ������?"))
         {
	         fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
	         fm.target="f1print";
	         document.getElementById("fm").submit();
         }
       }
       var QuerySQL, arrResult;
       
        var sqlid3="DSHomeContSql3";
				var mySql3=new SqlClass();
				mySql3.setResourceName("bq.WFGrpEdorConfirmInputSql");//ָ��ʹ�õ�properties�ļ���
				mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
				mySql3.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
				QuerySQL=mySql3.getString();
       
//       QuerySQL = "select edorno from LPEdorPrint2 where EdorNo in (select edorno from lpgrpedoritem where edoracceptno = '" + fm.EdorAcceptNo.value + "')";
       
       try
       {
       		arrResult = easyExecSql(QuerySQL, 1, 0);
       }
        catch (ex){
            alert("���棺��ѯ�����嵥��Ϣ�����쳣�� ");
        }
        if (arrResult != null)
        {
          if (confirm("�Ƿ��ӡ���α�ȫ����Ա�䶯�嵥?"))
       		{
       			fm.action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + arrResult[0][0] + "&type=Bill";
       			fm.target = "_blank";
       			document.getElementById("fm").submit();
       		}
        }
	  	//ˢ�²�ѯ���
		easyQueryClickAll();
		easyQueryClickSelf();		
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

/*********************************************************************
 *  �򿪹��������±��鿴ҳ��
 *  ����: �򿪹��������±��鿴ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
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
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueSave.jsp";
	document.getElementById("fm").submit();
}