//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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


/*********************************************************************
 *  ����Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
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
  // ˢ�²�ѯ���
	easyQueryClick();
	easyQueryClickSelf();		
	}else
	{
	   returnParent(); 
	}
	
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// ���ݽ���Ͷ������ϸ��Ϣ
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpGrid.getSelNo();
	var i = 0;
	var checkFlag = 0;
	var cGrpProposalNo = "";
	var cGrpPrtNo = "";
	var cflag = "2";
	var cMissionID = "";
	var cSubMissionID = "";
	  for (i=0; i<GrpGrid.mulLineCount; i++) {
	    if (GrpGrid.getSelNo(i)) { 
	      checkFlag = GrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = GrpGrid.getRowColData(checkFlag - 1, 2);
	  	cMissionID = GrpGrid.getRowColData(checkFlag - 1, 6);
	  	cSubMissionID = GrpGrid.getRowColData(checkFlag - 1, 7);
	  	ActivityID = GrpGrid.getRowColData(checkFlag - 1, 8);
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 
	    return false;
	  }
	  if(trim(cGrpPrtNo)==''||trim(cGrpProposalNo)=='')
	  {
	    alert("����ѡ��һ����Ч������Ϣ��"); 
	    return false;
	  }
	  
	  
	 // var tsql="select subtype From es_doc_main where doccode='"+cGrpProposalNo+"' order by subtype";
 	//			var crr = easyExecSql(tsql);
 	//			var tsubtype="";
 	//			//alert(crr);
 	//			if(crr!=null)
 	//			{
 	//			if(crr[0][0]=="1000")
 	//			{
 	//			 tsubtype="TB1002";
 	//			}else{
 	//			 tsubtype="TB1003";
 	//			}
 	//		}else{
 	//		 tsubtype="TB1002";
 	//	}
 	var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+cGrpProposalNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=13&SubType="+SubType+"&prtNo="+cGrpProposalNo+"&polNo="+cGrpProposalNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType=2" ,"window1",sFeatures);
	
}



function InitreturnParent()
{
	var arrReturn = new Array();
	var tSel = SelfGrpGrid.getSelNo();
	var i = 0;
	var checkFlag = 0;
	var cGrpProposalNo = "";
	var cGrpPrtNo = "";
	var cflag = "2";
	var cMissionID = "";
	var cSubMissionID = "";
	  for (i=0; i<SelfGrpGrid.mulLineCount; i++) {
	    if (SelfGrpGrid.getSelNo(i)) { 
	      checkFlag = SelfGrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = SelfGrpGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = SelfGrpGrid.getRowColData(checkFlag - 1, 2);
	  	cMissionID = SelfGrpGrid.getRowColData(checkFlag - 1, 6);
	  	cSubMissionID = SelfGrpGrid.getRowColData(checkFlag - 1, 7);
	  	
	  	var ActivityID = SelfGrpGrid.getRowColData(checkFlag - 1, 8);
    	var NoType = "2";
	  	
	  	
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 
	    return false;
	  }
	  if(trim(cGrpPrtNo)==''||trim(cGrpProposalNo)=='')
	  {
	    alert("����ѡ��һ����Ч������Ϣ��"); 
	    return false;
	  }
	  
	  
	  // var tsql="select subtype From es_doc_main where doccode='"+cGrpProposalNo+"' order by subtype";
 		//		var crr = easyExecSql(tsql);
 		//		var tsubtype="";
 		//		//alert(crr);
 		//		if(crr!=null)
 		//		{
 		//		if(crr[0][0]=="1000")
 		//		{
 		//		 tsubtype="TB1002";
 	//			}else{
 	//			 tsubtype="TB1003";
 	//			}
 	//		}else{
 	//		 tsubtype="TB1002";
 	//	}
 	var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+cGrpProposalNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("./GroupPolApproveInfo.jsp?LoadFlag=13&SubType="+SubType+"&prtNo="+cGrpProposalNo+"&polNo="+cGrpProposalNo+"&GrpPrtNo="+cGrpPrtNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType,"window1",sFeatures);
	
}
function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// ��ѯ��ť
var queryBug = 1;
function easyQueryClick()
{
	// ��ʼ�����
	initGrpGrid();
	if (contNo==null||contNo=='')
		contNo = "";
	//alert(contNo);
	// ��дSQL���
	var strSQL = "";
	var strOperate="like";
	/*
	 strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate from LCGrpCont where 1=1"
	           +" and ApproveFlag=1"
	           + getWherePart( 'PrtNo' )
		   + getWherePart( 'ProposalGrpContNo','GrpProposalNo' )
		   + getWherePart( 'ManageCom' )
		   + getWherePart( 'AgentCode' )
		   + getWherePart( 'AgentGroup' )
		   + getWherePart( 'SaleChnl' );
	           + "order by makedate,maketime";
	           */
	           
	    if(comcode=="%")
	    {
	  strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
				 + " and activityid = '0000002002' "
				 + " and processid = '0000000004'"		
				 + " and defaultoperator is null "		 
				 + getWherePart('missionprop1','GrpProposalNo',strOperate)
				 //+ getWherePart('missionprop2','PrtNo',strOperate)
				 //+ getWherePart('missionprop3','SaleChnl',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 //+ getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%%'";  //����Ȩ�޹�������					 
				 + " order by lwmission.missionprop2"
				 ;
				}else{
				strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
				 + " and activityid = '0000002002' "
				 + " and processid = '0000000004'"		
				 + " and defaultoperator is null "		 
				 + getWherePart('missionprop1','GrpProposalNo',strOperate)
				 //+ getWherePart('missionprop2','PrtNo',strOperate)
				 //+ getWherePart('missionprop3','SaleChnl',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 //+ getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 + " and LWMission.MissionProp4 like '" + comcode + "%%'"  //����Ȩ�޹�������					 
				 + " order by lwmission.missionprop2"
				 ;	
					
				}
	turnPage.queryModal(strSQL, GrpGrid);

}

function easyQueryClickSelf()
{
	// ��ʼ�����
	initSelfGrpGrid();
	if (contNo==null||contNo=='')
		contNo = "";

	var strSQL = "";

	  strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
				 + " and activityid = '0000002002' "
				 + " and processid = '0000000004'"	
				 + " and defaultoperator ='" + operator + "'"			 
				 + getWherePart('missionprop1','GrpProposalNoSelf')
				 //+ getWherePart('missionprop2','PrtNo')
				 //+ getWherePart('missionprop3','SaleChnl')
				 //+ getWherePart('missionprop5','AgentCode')
				 //+ getWherePart('missionprop4','ManageCom')
				 //+ getWherePart('missionprop6','AgentGroup')
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%%'";  //����Ȩ�޹�������					 
				 + " order by lwmission.missionprop2"
				 ;

	turnPage2.queryModal(strSQL, SelfGrpGrid);
}

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
			//GrpGrid.addOne();
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
			  GrpGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		//GrpGrid.delBlankLine();
	} // end of if
}

/*********************************************************************
 *  ���嵥���������ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cGrpProposalNo = "";
	  var cflag = "2";
	  
	  for (i=0; i<GrpGrid.mulLineCount; i++) {
	    if (GrpGrid.getSelNo(i)) { 
	      checkFlag = GrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpGrid.getRowColData(checkFlag - 1, 1);
	  }
	  else {
	    alert("����ѡ��һ�����ձ�����Ϣ��"); 
	    return false;
	  }
	  if(cGrpProposalNo==null||trim(cGrpProposalNo)=='')
	  {
	    alert("����ѡ��һ����Ч������Ϣ��"); 
	    return false;
	  }
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("../uwgrp/QuestQueryMain.jsp?ProposalNo1="+cGrpProposalNo+"&Flag="+cflag,"",sFeatures);
	
}


/*********************************************************************
 *  �����޸���ɺ�ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ModifyMakeSure(){
	  var i = 0;
	  var checkFlag = 0;
	  var cGrpProposalNo = "";
	  var cGrpPrtNo="";
	  var cflag = "2";
	  
	  for (i=0; i<GrpGrid.mulLineCount; i++) {
	    if (GrpGrid.getSelNo(i)) { 
	      checkFlag = GrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = GrpGrid.getRowColData(checkFlag - 1, 2);
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 
	    return false;
	  }
	  if(trim(cGrpPrtNo)==''||trim(cGrpProposalNo)=='')
	  {
	    alert("����ѡ��һ����Ч������Ϣ��"); 
	    return false;
	  }
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("./GrpApproveModifyMakeSure.jsp?GrpProposalNo="+trim(cGrpProposalNo)+"&GrpPrtNo="+trim(cGrpPrtNo),"window1");
	
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
	document.getElementById("fm").submit();//�ύ
	//showInfo.close();
	//returnParent();
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