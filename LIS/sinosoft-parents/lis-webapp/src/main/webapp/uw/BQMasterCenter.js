//�������ƣ�BQMasterCenter.js
//�����ܣ���������
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var cflag = "4";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uw.BQMasterCenterSql";

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
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass(); 
var queryBug = 1;
function initQuery() {
	// ��ʼ�����
	//initPolGrid();
	 
	// ��дSQL���
/*	var strSql = "select ProposalNo,PrtNo,RiskCode,AppntName,InsuredName from LCPol where " + ++queryBug + "=" + queryBug
    				 + " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pi')=1 "
    				 + " and AppFlag='0' "                 //���˱���
    				 + " and GrpPolNo='" + grpPolNo + "'"  //���嵥�ţ�����Ϊ20��0
    				 + " and ContNo='" + contNo + "'"      //���ݺţ�����Ϊ20��0
    				 + " and ManageCom like '" + comcode + "%%'"
    				 + " and uwflag <> '0'"
    				 //+ " and uwflag = '7'"
    				 + " and approveflag = '2'"
    				 //+ " and polno in (select proposalno from lcuwmaster where printflag in ('1','2'))"
    				 + " and PolNo in (select distinct proposalno from lcissuepol where ReplyResult is null and backobjtype = '4')";
*/
/*		var strSql = " select missionprop8,missionprop9,missionprop2,missionprop3,missionid,submissionid,missionprop4, "
							 + " (select nvl(edorappdate,'') from lpedoritem where edorno = lwmission.missionprop8),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edorno = lwmission.missionprop8) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
							 + " from lwmission where activityid='0000000332' and processid='0000000000' ";
							 + " and defaultoperator is null";
  //alert(strSql);
*/  	
  	var strSql = "";
  	var sqlid1="BQMasterCenterSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(curDay);//ָ������Ĳ���
	strSql=mySql1.getString();
  
	turnPage.queryModal(strSql, PolGrid);// mySql.getString()�Ϳ��Ի�ö�Ӧ��SQL��
}

var mSwitch = parent.VD.gVSwitch;
function modifyClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cPolNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	
    urlStr = "./ProposalMain.jsp?LoadFlag=3";
    window.open(urlStr,"",sFeatures);
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}

function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cProposalNo = "";
	  var cMissionId = "";
	  var cSubMissionId = "";
	  
	  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
	    if (SelfPolGrid.getSelNo(i)) { 
	      checkFlag = SelfPolGrid.getSelNo();
	      break;
	    }
	  }
	  
	  //QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag=5&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020
	  if (checkFlag > 0) { 
	  	cProposalNo = SelfPolGrid.getRowColData(checkFlag - 1, 3); 	
	  	cMissionId  = SelfPolGrid.getRowColData(checkFlag - 1, 5); 	
	  	cSubMissionId = SelfPolGrid.getRowColData(checkFlag - 1, 6);	  	
	  	cActivityID = SelfPolGrid.getRowColData(checkFlag - 1, 7);	  	
	  	var cEdorNo  = SelfPolGrid.getRowColData(checkFlag - 1, 1); 	
	  	var cEdorType = SelfPolGrid.getRowColData(checkFlag - 1, 2);	
	  	window.open("../uw/BQQuestQueryMain.jsp?ActivityID="+cActivityID +"&EdorNo="+cEdorNo+"&EdorType="+cEdorType+"&ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020","window1");
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 	    
	  }	
	  HighlightSelfRow();
	
}

//����  ѯ����ť��������������ѯ���������Ĺ����ض���
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	var strOperate="like";
	// ��дSQL���
	var strSql = "";               
/*
	 strSql = " select missionprop8,missionprop9,missionprop2,missionprop3,missionid,submissionid,ActivityID, "
							 + " (select nvl(edorappdate,'') from lpedoritem where edorno = lwmission.missionprop8),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edorno = lwmission.missionprop8) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
							 + " from lwmission where activityid='0000000332' and processid='0000000000' "
							 + " and defaultoperator is null"
							 + " and exists "
						           +" (select 1 from lpcont where ContNo=missionprop2 and edorno=missionprop8"
						           + getWherePart('ManageCom','ComCode',strOperate)
						           + getWherePart('ManageCom','ManageCom','like')
						           +" )"		
               +  getWherePart('MissionProp2','ContNo');   			
 */
   	var sqlid2="BQMasterCenterSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(curDay);//ָ������Ĳ���
	mySql2.addSubPara(fm.ComCode.value);//ָ������Ĳ���
	mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
	mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSql=mySql2.getString();
	
  turnPage.queryModal(strSql, PolGrid); 
  HighlightAllRow();
}

//����  �롿��ť������������������ӹ��������뵽���˶���
function ApplyUW()
{

	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}
	fm.MissionID.value = PolGrid.getRowColData(selno, 5);
	fm.SubMissionID.value = PolGrid.getRowColData(selno, 6);
	fm.ActivityID.value = PolGrid.getRowColData(selno, 7);
	fm.hiddenContNo.value =  PolGrid.getRowColData(selno, 3);
	fm.EdorNo.value= PolGrid.getRowColData(selno, 1); 	
	fm.EdorType.value= PolGrid.getRowColData(selno, 2);
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
	//fm.action = "BQMasterCenterChk.jsp";
	fm.action = "../bq/MissionApply.jsp";
	fm.submit(); //�ύ
}



/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	
	//���ӡˢ�ŵ�����
  /*var contNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=��ȫ��������&PolState=0332&Action=DELETE";
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
*/	
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
		return;
	}
  // ˢ�²�ѯ���
	easyQueryClickSelf();		
	easyQueryClick();
	//���´���
		  var cProposalNo = fm.hiddenContNo.value;
	  	var cMissionId  = fm.MissionID.value; 	
	  	var cSubMissionId = fm.SubMissionID.value; 	
	  	var cEdorNo = fm.EdorNo.value;
	  	var cEdorType = fm.EdorType.value;
	  	window.open("../uw/BQQuestQueryMain.jsp?EdorNo="+cEdorNo+"&EdorType="+cEdorType+"&ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020","window1");
}

function HighlightAllRow(){
		for(var i=0; i<PolGrid.mulLineCount; i++){
  	var days = PolGrid.getRowColData(i,9);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<SelfPolGrid.mulLineCount; i++){
  	var days = SelfPolGrid.getRowColData(i,9);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfPolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}


//����ѯ  �����˹��� ���С�
function easyQueryClickSelf()
{

	initSelfPolGrid();
	var strOperate="like";
/*	                             
   	var strSql = " select missionprop8,missionprop9,missionprop2,missionprop3,missionid,submissionid,missionprop4, "
							 + " (select nvl(edorappdate,'') from lpedoritem where edorno = lwmission.missionprop8),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edorno = lwmission.missionprop8) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
							 + " from lwmission where activityid='0000000332' and processid='0000000000' "
			 + " and defaultoperator ='" + Operator + "' "
			 + " and exists "
						           +" (select 1 from lpcont where ContNo=missionprop2 and edorno=missionprop8 "
						           + getWherePart('ManageCom','ComCode',strOperate)
						           + getWherePart('ManageCom','ManageCom1','like')
						           +" ) "		
             +  getWherePart('MissionProp2','ContNo1')  
			 + " order by modifydate asc,modifytime asc";		 	
*/
   	var strSql = "";
   	var sqlid3="BQMasterCenterSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(curDay);//ָ������Ĳ���
	mySql3.addSubPara(Operator);//ָ������Ĳ���
	mySql3.addSubPara(fm.ComCode.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.ManageCom1.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.ContNo1.value);//ָ������Ĳ���
	strSql=mySql3.getString();

	turnPage2.queryModal(strSql, SelfPolGrid); 
	HighlightSelfRow();
}