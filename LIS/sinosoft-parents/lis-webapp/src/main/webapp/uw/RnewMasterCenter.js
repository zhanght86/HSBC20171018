//�������ƣ�BQMasterCenter.js
//�����ܣ���������
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var cflag = "4";


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
//		var strSql = " select missionprop8,missionprop9,missionprop2,missionprop3,missionid,submissionid,missionprop4 "
//							 + " from lwmission where activityid='0000000332' and processid='0000000000' ";
//							 + " and defaultoperator is null";
							    var  sqlid1="RnewMasterCenterSql0";
						 	 	var  mySql1=new SqlClass();
						 	 	mySql1.setResourceName("uw.RnewMasterCenterSql"); //ָ��ʹ�õ�properties�ļ���
						 	 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
						 	    var strSql2=mySql1.getString();
  //alert(strSql);
	turnPage.queryModal(strSql, PolGrid);
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
    window.open(urlStr);
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
	  	cProposalNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 	
	  	cMissionId  = SelfPolGrid.getRowColData(checkFlag - 1, 3); 	
	  	cSubMissionId = SelfPolGrid.getRowColData(checkFlag - 1, 4);	  	
	  	window.open("../uw/RnewQuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000007015","window1");
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 	    
	  }	
	
}

//����  ѯ����ť��������������ѯ���������Ĺ����ض���
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	var strOperate="like";
	// ��дSQL���
	var strSql = "";               

//	 strSql = " select missionprop2,missionprop3,missionid,submissionid,ActivityID "
//							 + " from lwmission where activityid=(select activityid from lwactivity where functionId = '10047015') and exists (select 1 from Lwprocess a  where  a.Processid=Processid and a.Busitype='1004') "
//							 + " and defaultoperator is null"
//							 + " and exists "
//						           +" (select 1 from lccont where ContNo=missionprop2 "
//						           + getWherePart('ManageCom','ComCode',strOperate)
//						           + getWherePart('ManageCom','ManageCom','like')
//						           +" )"		
//               +  getWherePart('MissionProp2','ContNo');
	    
	    var  ComCode = window.document.getElementsByName(trim("ComCode"))[0].value;
        var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
        var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
	    var  sqlid2="RnewMasterCenterSql1";
	 	var  mySql2=new SqlClass();
	 	mySql2.setResourceName("uw.RnewMasterCenterSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	 	mySql2.addSubPara(ComCode);//ָ������Ĳ���
	 	mySql2.addSubPara(ManageCom);//ָ������Ĳ���
	 	mySql2.addSubPara(ContNo);//ָ������Ĳ���
	    var strSql=mySql2.getString();
  turnPage.queryModal(strSql, PolGrid); 
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
	fm.MissionID.value = PolGrid.getRowColData(selno, 3);
	fm.SubMissionID.value = PolGrid.getRowColData(selno, 4);
	fm.ActivityID.value = PolGrid.getRowColData(selno, 5);
	fm.hiddenContNo.value =  PolGrid.getRowColData(selno, 1);
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	//fm.action = "BQMasterCenterChk.jsp";
	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //�ύ
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
	  //	var cEdorNo = fm.EdorNo.value;
	  //	var cEdorType = fm.EdorType.value;
	  //	window.open("../uw/BQQuestQueryMain.jsp?EdorNo="+cEdorNo+"&EdorType="+cEdorType+"&ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020","window1");
}




//����ѯ  �����˹��� ���С�
function easyQueryClickSelf()
{

	initSelfPolGrid();
//	var strOperate="like";
	                             
//   	var strSql = " select missionprop2,missionprop3,missionid,submissionid,missionprop4 "
//							 + " from lwmission where activityid=(select activityid from lwactivity where functionId = '10047015') and exists (select 1 from Lwprocess a  where  a.Processid=Processid and a.Busitype='1004') "
//			 + " and defaultoperator ='" + Operator + "' "
//			 + " and exists "
//						           +" (select 1 from lccont where ContNo=missionprop2  "
//						           + getWherePart('ManageCom','ComCode',strOperate)
//						           + getWherePart('ManageCom','ManageCom1','like')
//						           +" ) "		
//             +  getWherePart('MissionProp2','ContNo1')  
//			 + " order by modifydate asc,modifytime asc";
   	
   	var  ComCode = window.document.getElementsByName(trim("ComCode"))[0].value;
    var  ManageCom1 = window.document.getElementsByName(trim("ManageCom1"))[0].value;
    var  ContNo1 = window.document.getElementsByName(trim("ContNo1"))[0].value;
    var  sqlid3="RnewMasterCenterSql2";
 	var  mySql3=new SqlClass();
 	mySql3.setResourceName("uw.RnewMasterCenterSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
 	mySql3.addSubPara(Operator);//ָ������Ĳ���
 	mySql3.addSubPara(ComCode);//ָ������Ĳ���
 	mySql3.addSubPara(ManageCom1);//ָ������Ĳ���
 	mySql3.addSubPara(ContNo1);//ָ������Ĳ���
    var strSql=mySql3.getString();
	turnPage2.queryModal(strSql, SelfPolGrid); 
}