//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var k=0;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
/*********************************************************************
 *  ִ�д��µ����˵���EasyQuery
 *  ����:��ѯ��ʾ���������ձ���.��ʾ����:���ջ���һ������δ�µ�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	k++;
	// ��ʼ�����
	initGrpGrid();
	// ��дSQL���
	var strSQL = "";
	var srtOperate ="like";

	//strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate from LCGrpCont where 1=1 "
	//			 +" and ApproveFlag=0 "
	//			 + getWherePart( "PrtNo",'PrtNo',srtOperate )
	//			 + getWherePart( 'ProposalGrpContNo','GrpProposalNo',srtOperate )
	//			 + getWherePart( 'ManageCom','ManageCom',srtOperate )
	//			 + getWherePart( 'AgentCode','AgentCode',srtOperate )
	//			 + getWherePart( 'AgentGroup','AgentGroup',srtOperate )
	//			 + getWherePart( 'SaleChnl','SaleChnl',srtOperate );
	//			 + "order by makedate,maketime";
if(comcode=="%")
{
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.lastoperator from lwmission where 1=1 "
				 + " and activityid = '0000002001' "
				 + " and processid = '0000000004'"
				 + " and defaultoperator is null "
				 + getWherePart('missionprop1','GrpProposalNo',srtOperate)
				 //+ getWherePart('missionprop2','PrtNo',srtOperate)
				 //+ getWherePart('missionprop3','SaleChnl',srtOperate)
				 //+ getWherePart('missionprop5','AgentCode',srtOperate)
				 + getWherePart('missionprop4','ManageCom',srtOperate)
				 //+ getWherePart('missionprop6','AgentGroup',srtOperate)
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%'";  //����Ȩ�޹�������					 
				 + " order by lwmission.missionprop2"						//����ӡˢ������
				 ;	
}else{
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.lastoperator from lwmission where 1=1 "
				 + " and activityid = '0000002001' "
				 + " and processid = '0000000004'"
				 + " and defaultoperator is null "
				 + getWherePart('missionprop1','GrpProposalNo',srtOperate)
				 //+ getWherePart('missionprop2','PrtNo',srtOperate)
				 //+ getWherePart('missionprop3','SaleChnl',srtOperate)
				 //+ getWherePart('missionprop5','AgentCode',srtOperate)
				 + getWherePart('missionprop4','ManageCom',srtOperate)
				 //+ getWherePart('missionprop6','AgentGroup',srtOperate)
				 + " and LWMission.MissionProp4 like '" + comcode + "%%'" //����Ȩ�޹�������					 
				 + " order by lwmission.missionprop2"						//����ӡˢ������
				 ;	
				 
		}		 
				 //alert(	strSQL);		 	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}

function easyQueryClickSelf()
{
	k++;
	// ��ʼ�����
	initSelfGrpGrid();
	// ��дSQL���
	var strSQL = "";

	strSQL = "select decode(A.x, 0, '�ѵ������',1, '�ѻظ����������','��δ�ظ������'),A.y,A.z,A.m,A.n,A.o,A.p,A.q,A.r,A.s,A.t,A.u from (select case when (select count(*) from lcgrpissuepol where grpcontno=a.missionprop2 )=0 then 0 "
	        +" when (select count(*) from lcgrpissuepol where grpcontno=a.missionprop2 and replyresult is null )=0 then 1 else 2 end x,"
	             + "a.missionprop4 y,a.missionprop2 z,a.missionprop1 m,a.missionprop3 n,a.missionprop7 o,a.indate p,"
	             + "a.missionprop8 q,a.missionid r,a.submissionid s,a.activityid t,a.lastoperator u from lwmission a where 1=1 "
				 + " and activityid = '0000002001' "
				 + " and processid = '0000000004'"
				 + " and defaultoperator ='" + operator + "'"
				 + getWherePart('missionprop1','GrpProposalNoSelf')
				 //+ getWherePart('missionprop2','PrtNo')
				 //+ getWherePart('missionprop3','SaleChnl')
				 //+ getWherePart('missionprop5','AgentCode')
				 + getWherePart('missionprop4','ManageComSelf')
				 //+ getWherePart('missionprop6','AgentGroup')
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%'";  //����Ȩ�޹�������					 
				 + " ) A order by A.x desc"						//����ӡˢ������
				 ;		
				 //alert("ok");	
	turnPage2.queryModal(strSQL, SelfGrpGrid);
	return true;
}
/*********************************************************************
 *  ��ʾEasyQuery�Ľ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{       
		// ��ʼ�����
		initGrpGrid();
		//HZM �����޸�
		GrpGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpGrid.loadMulLine(GrpGrid.arraySave);		
		//HZM �����޸�		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}
function showApproveDetail() { 
	//alert("ok");
  if (GrpGrid.getSelNo() == 0) {
    alert("����ѡ��һ��������Ϣ��");
    return false;
  } 
  var polNo = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 1);
  var prtNo = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 2);
  var cMissionID=GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 6);
  var cSubMissionID = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 7);
  var ActivityID = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 8);
  
/*var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='�б�¼��' or CreatePos='�б�����') and (PolState='1002' or PolState='1003')";
  var arrResult = easyExecSql(strSql);
  if (arrResult!=null && arrResult[0][1]!=Operator) {
    alert("��ӡˢ�ŵ�Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
    return;
  }
  //������ӡˢ��
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=�б�����&PolState=1003&Action=INSERT";
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
*/
  //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);

	
	mSwitch.deleteVar("GrpContNo");
	mSwitch.addVar("GrpContNo", "", polNo);
	mSwitch.updateVar("GrpContNo", "", polNo);
  
  //var tsql="select subtype From es_doc_main where doccode='"+polNo+"' order by subtype";
  //				var crr = easyExecSql(tsql);
 //				var ttype="";
 //				//alert(crr);
 //				if(crr!=null)
 //				{
 //				if(crr[0][0]=="1000")
 //				{
 //				 ttype="TB1002";
 //				}else{
 //				 ttype="TB1003";
 //				}
 //			}else{
 //			ttype="TB1002";
 //		}
 var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
 				//alert(ttype);
  easyScanWin = window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=4&SubType="+SubType+"&prtNo="+polNo+"&polNo="+polNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType=2","", "status=no,resizable=yes,scrollbars=yes ");    
  
}

function InitshowApproveDetail() { 
  if (SelfGrpGrid.getSelNo() == 0) {
    alert("����ѡ��һ��������Ϣ��");
    return false;
  } 
  var polNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 4);
  var prtNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 3);
  var cMissionID=SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 9);
  var cSubMissionID = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 10);
  
  var ActivityID = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 11);
  var NoType = "2";
    
/*var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='�б�¼��' or CreatePos='�б�����') and (PolState='1002' or PolState='1003')";
  var arrResult = easyExecSql(strSql);
  if (arrResult!=null && arrResult[0][1]!=Operator) {
    alert("��ӡˢ�ŵ�Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
    return;
  }
  //������ӡˢ��
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=�б�����&PolState=1003&Action=INSERT";
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
*/
  //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);

	
	mSwitch.deleteVar("GrpContNo");
	mSwitch.addVar("GrpContNo", "", polNo);
	mSwitch.updateVar("GrpContNo", "", polNo);
  
  
  //var tsql="select subtype From es_doc_main where doccode='"+polNo+"' order by subtype";
 //				var crr = easyExecSql(tsql);
 //				var tsubtype="";
 //				//alert(crr);
 //				if(crr!=null)
 //				{
 //				if(crr[0][0]=="1000")
 //				{
 //				 tsubtype="TB1002";
 //				}else{
 //				 tsubtype="TB1003";
 //				}
 //			}else{
 //			 tsubtype="TB1002";
 //		}
 var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
 				
 				
//alert(polNo);
  easyScanWin = window.open("./GroupPolApproveInfo.jsp?ManageCom="+manageCom+"&SubType="+SubType+"&prtNo="+polNo+"&LoadFlag=4&polNo="+polNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType,"", "status=no,resizable=yes,scrollbars=yes ");    
  
}

function ApplyUW()
{
 //ֻ������λ��վ����
	var tLine=manageCom.length;
	if(tLine<4)
	{
		alert("ֻ������λ����Ļ�������ϵͳ������");
		return false;
	}
	
	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}

	fm.MissionID.value = GrpGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 7);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 8);
	
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockScreen('lkscreen');  
	fm.action = "../uwgrp/ManuUWAllChk.jsp";
	document.getElementById("fm").submit(); //�ύ
	//showInfo.close();
	//showApproveDetail();
}

function afterSubmit( FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  
	if (FlagStr == "Fail" )
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
	easyQueryClick();
	easyQueryClickSelf();
	}else
	  {
	     showApproveDetail();
	  }
	
  // ˢ�²�ѯ���
	//showApproveDetail();	
}

function showNotePad()
{

	var selno = SelfGrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = SelfGrpGrid.getRowColData(selno, 6);
	var SubMissionID = SelfGrpGrid.getRowColData(selno, 7);
	var ActivityID = SelfGrpGrid.getRowColData(selno, 8);
	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
	var NoType = "2";
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}