//�������ƣ�GroupUWAuto.js
//�����ܣ������Զ��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//var showInfo = "";
var mDebug="0";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.GroupUWModifySql";
//�Զ��˱��ύ��
function submitForm()
{
	var tSel = GrpGrid.getSelNo();
	var cGrpPolNo = "";
	var cGrpPrtNo = "";
	if( tSel != null && tSel != 0 )
	{
		cGrpPolNo = GrpGrid.getRowColData( tSel - 1, 1 );
	  cGrpPrtNo = GrpGrid.getRowColData( tSel - 1, 2 );
    }
	
	if( cGrpPolNo == null || cGrpPolNo == ""|| cGrpPrtNo == null || cGrpPrtNo == "" )
		alert("��ѡ��һ�ż���Ͷ�������ٽ����Զ��˱�����");
	else
	{
		window.open( "./GroupUWAutoDetailMain.jsp?ProposalGrpContNo=" + cGrpPolNo + "&PrtNo=" + cGrpPrtNo ,"",sFeatures);
	}
  
}

function SetSpecialFlag()
{
    var tSel = GrpGrid.getSelNo();
	var cGrpPolNo = "";
	var cGrpPrtNo = "";
	if( tSel != null && tSel != 0 )
	{
		cGrpPolNo = GrpGrid.getRowColData( tSel - 1, 1 );
	    cGrpPrtNo = GrpGrid.getRowColData( tSel - 1, 2 );
    }
	
	if( cGrpPolNo == null || cGrpPolNo == ""|| cGrpPrtNo == null || cGrpPrtNo == "" )
		alert("��ѡ��һ�ż���Ͷ�������ٽ������������־����");
	else
	{
		window.open( "./GroupUWSetSpecialFlag.jsp?GrpProposalNo=" + cGrpPolNo + "&GrpPrtNo=" + cGrpPrtNo ,"",sFeatures);
	}
  
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}           

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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,0,0,*";
  }
  else
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
	
  parent.fraMain.rows = "0,0,0,0,*";
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}

function easyQueryClick()
{
	// ��ʼ�����
	
	initGrpGrid();

	// ��дSQL���
	
	var strSQL = "";
        var strOperate = "like";
        if(comcode=="%")
	{/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid from lwmission where 1=1 "
				 + " and activityid = '0000002005' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','ProposalGrpContNo',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 + getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 + getWherePart('missionprop9','GrpNo',strOperate)
				 + getWherePart('missionprop7','Name',strOperate)
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%'";  //����Ȩ�޹�������					 
				 + "order by lwmission.missionprop2"
				 ;
				*/ 
		var sqlid1="GroupUWModifySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.ProposalGrpContNo.value); 
		mySql1.addSubPara(fm.ManageCom.value); 
		mySql1.addSubPara(fm.GrpNo.value); 
		mySql1.addSubPara(fm.Name.value); 
				 
			strSQL = mySql1.getString();	 
				 
				 
				 
				}else{
				/*
					strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid from lwmission where 1=1 "
				 + " and activityid = '0000002005' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','ProposalGrpContNo',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 + getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 + getWherePart('missionprop9','GrpNo',strOperate)
				 + getWherePart('missionprop7','Name',strOperate)
				 + " and LWMission.MissionProp4 like '" + comcode + "%%'"  //����Ȩ�޹�������					 
				 + "order by lwmission.missionprop2"
				 ;
				 */
				var sqlid2="GroupUWModifySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.ProposalGrpContNo.value); 
		mySql2.addSubPara(fm.ManageCom.value); 
		mySql2.addSubPara(fm.GrpNo.value); 
		mySql2.addSubPara(fm.Name.value); 	
		mySql2.addSubPara(comcode); 
		
			strSQL = mySql2.getString();
				}
			
      //alert(strSQL);
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û����Ҫ�Զ��˱����嵥��");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
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

function GrpUWModify(){
	//ֻ������λ��վ����
	var tLine=manageCom.length;
	if(tLine<4)
	{
		alert("ֻ������λ����Ļ�������ϵͳ������");
		return false;
	}
	
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
	  	fm.GrpContNo.value = cGrpProposalNo;
	  	fm.MissionID.value = GrpGrid.getRowColData(checkFlag - 1, 6);
	  	fm.SubMissionID.value = GrpGrid.getRowColData(checkFlag - 1, 7);
	  }
	  else {
	    alert("����ѡ��һ����Ч�ı�����Ϣ��"); 
	    return false;
	  }
	  if( cGrpPrtNo == null || trim(cGrpPrtNo)=='' || cGrpProposalNo == null || trim(cGrpProposalNo)== '' )
	  {
	    alert("����ѡ��һ����Ч�ı�����Ϣ��"); 
	    return false;
	  }
	 fm.ProposalGrpContNo.value = cGrpProposalNo ;
//	 fm.PrtNo.value = cGrpPrtNo ;
lockScreen('lkscreen');  
	 document.getElementById("fm").submit();
	 
}

/*********************************************************************
 *  ����Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	//showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	if( FlagStr == "Fail" )
	{             
		
		//showInfo=showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}
	else
	{ 
		// ˢ�²�ѯ���
		//showInfo=showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
		easyQueryClick();
		//easyQueryClick_noAlert();
	}
}


function easyQueryClick_noAlert()
{
	// ��ʼ�����
	
	initGrpGrid();

	// ��дSQL���
	var strSQL = "";
/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid from lwmission where 1=1 "
				 + " and activityid = '0000002005' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','ProposalGrpContNo')
				 + getWherePart('missionprop5','AgentCode')
				 + getWherePart('missionprop4','ManageCom')
				 + getWherePart('missionprop6','AgentGroup')
				 + getWherePart('missionprop9','GrpNo')
				 + "order by lwmission.missionprop2"
				 ;	 
				 */
		var sqlid3="GroupUWModifySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(fm.ProposalGrpContNo.value); 
		mySql3.addSubPara(fm.AgentCode.value); 
		mySql3.addSubPara(fm.ManageCom.value); 
		mySql3.addSubPara(fm.AgentGroup.value); 	
		mySql3.addSubPara(fm.GrpNo.value); 	
      //alert(strSQL);
	  turnPage.strQueryResult  = easyQueryVer3(mySql3.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert("û����Ҫ�Զ��˱����嵥��");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}
