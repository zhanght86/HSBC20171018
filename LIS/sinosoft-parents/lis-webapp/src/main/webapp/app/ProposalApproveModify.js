
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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

function easyQueryClick() {
	var strOperate="like";
	var addStr2 = " and 2=2 ";
	var addStr3 = " and 3=3 ";
	
	var preCom=document.all('PreCom').value;//���Ȼ���
    var preComSQL=" and 1=1 ";
    if(preCom != null && preCom != "")
    {
    	 preComSQL=" and exists (select '1' from ldcom where lwmission.MissionProp5=ldcom.comcode  and ldcom.comareatype1='1') ";
    	 
    	 if(preCom=='b')
    		preComSQL=" and exists (select '1' from ldcom where lwmission.MissionProp5=ldcom.comcode  and (ldcom.comareatype1<>'1' or ldcom.comareatype1 is null)) ";
    }

		 var sqlid1="ProposalApproveModifySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(preComSQL);//ָ������Ĳ���
		mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.State.value);//ָ������Ĳ���
		mySql1.addSubPara(comcode);//ָ������Ĳ���
//	    strSql=mySql1.getString();	

//    strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,'',lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop10||' '||lwmission.missionprop11 ,lwmission.missionprop5,lwmission.missionprop6,lwmission.missionprop7,decode(lwmission.missionprop9,'1','������ѻظ�','2','�����δ�ظ�') from lwmission where 1=1"
//   			 + " and activityid = '0000001002'"
//   			 + " and processid = '0000000003'" 
//   			 + " and defaultoperator is null "
//   			+ " "+preComSQL+"  "
//			 + getWherePart('lwmission.MissionProp1','ContNo',strOperate)
//			 + getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//			 + getWherePart('lwmission.MissionProp5','ManageCom',strOperate)  	
//			 + getWherePart('lwmission.MissionProp6','AgentCode',strOperate)  
//			 + getWherePart('lwmission.missionprop10','MakeDate',strOperate)  
//			 + getWherePart('lwmission.MissionProp9','State')  
//			 + " and lwmission.MissionProp5 like '" + comcode + "%%'";  //����Ȩ�޹�������		 
			 if(document.all("SaleChnl").value!=""){
			 	addStr2 = " and exists(select 'x' from lccont where lccont.contno = lwmission.missionprop1 and lccont.salechnl='"+document.all("SaleChnl").value+"')";
			 	
			 }
			 if(fm.BackObj.value != "")
			 {
			 	addStr3 = " and exists(select 1 from lcissuepol,ldcode where lwmission.missionprop1 = contno and codetype = 'backobj' and code  = '"+fm.BackObj.value+"'  and backobjtype = comcode and standbyflag2 = othersign )" ;	
			 }
			 
//   		 strSql = strSql + " order by lwmission.missionprop10,lwmission.missionprop11 ";
	mySql1.addSubPara(addStr2);//ָ������Ĳ���
	mySql1.addSubPara(addStr3);//ָ������Ĳ���
	strSql=mySql1.getString();
	turnPage.queryModal(strSql, PolGrid);
}

function initQuerySelf() {
	var strOperate="like";
	var addStr4 = " and 4=4 ";
			 var sqlid2="ProposalApproveModifySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(operator);//ָ������Ĳ���
		mySql2.addSubPara(fm.ContNo1.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.PrtNo1.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ManageCom1.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.MakeDate1.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.State1.value);//ָ������Ĳ���
		mySql2.addSubPara(comcode);//ָ������Ĳ���
//	    strSql=mySql2.getString();	
	
//     	strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,'',lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop10||' '||lwmission.missionprop11,lwmission.missionprop5,lwmission.missionprop6,lwmission.missionprop7, decode(lwmission.missionprop9,'1','������ѻظ�','2','�����δ�ظ�') from lwmission where 1=1"
//   			 + " and activityid = '0000001002'"
//   			 + " and processid = '0000000003'" 
//   			 + " and defaultoperator ='" + operator + "'"
//   			 + getWherePart('lwmission.MissionProp1','ContNo1',strOperate)
//			 	 + getWherePart('lwmission.MissionProp2','PrtNo1',strOperate)
//			   + getWherePart('lwmission.MissionProp5','ManageCom1',strOperate)  	
//			   + getWherePart('lwmission.missionprop10','MakeDate1',strOperate)  
//			   + getWherePart('lwmission.MissionProp9','State1')  
//			   + " and lwmission.MissionProp5 like '" + comcode + "%%'" ; //����Ȩ�޹�������	
			   if(fm.BackObj1.value != "")
			   {
			 	 addStr4 = " and exists(select 1 from lcissuepol where lwmission.missionprop1 = contno and backobjtype = '"+fm.BackObj1.value+"') ";
			   }	 
//   			   strSql = strSql + " order by lwmission.missionprop10,lwmission.missionprop11 ";
  //prompt('',strSql);
	mySql2.addSubPara(addStr4);//ָ������Ĳ���
	strSql=mySql2.getString();	
	turnPage2.queryModal(strSql, SelfPolGrid);
}
var mSwitch = parent.VD.gVSwitch;


/*function modifyClick() {
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
  	
    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
    var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);        
    var tmissionid=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 5);     //missionid
    var tsubmissionid=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 6);  //submissionid
    var ActivityID = fm.ActivityID.value;
	  var NoType = "1";
	  var tSplitPrtNo = prtNo.substring(0,4);
 //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
	
		 var sqlid3="ProposalApproveModifySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(prtNo);//ָ������Ĳ���
	     var strSql3=mySql3.getString();	
	
//    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
    var crrResult = easyExecSql(strSql3);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
    
    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}*/

/*function InitmodifyClick() {
	//alert(178);
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
    if (SelfPolGrid.getSelNo(i)) { 
      checkFlag = SelfPolGrid.getSelNo();
      break;
    }
  }
  //alert(1);
  if (checkFlag) {
  
  
  	var cPolNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  
    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
    var prtNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 2);  
  
    var tmissionid=SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 5);     //missionid
  
    var tsubmissionid=SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 6);  //submissionid
    
    var ActivityID = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 7);
	  
	  var NoType = "1";
	  
	  var strSql="";
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
    //var 
    //strSql = "select case lmriskapp.riskprop"
    //        +" when 'I' then '1' "
    //        +" when 'D' then '1' "
	//        +" when 'G' then '2'"
	//        +" when 'Y' then '3'"
	//        +" when 'T' then '5'"
    //       + " end"
    //       + " from lmriskapp"
    //       + " where riskcode in (select riskcode"
    //       + " from lcpol"
    //       + " where polno = mainpolno"
    //       + " and prtno = '"+prtNo+"')"    
    //prompt("��һ��",strSql);       
    //arrResult = easyExecSql(strSql);
    //if(arrResult==null){
    //    strSql = " select * from ("
    //           + " select case missionprop5"
    //           + " when '05' then '3'"
    //           + " when '12' then '3'"
    //           + " when '13' then '5'"
    //           + " else '1'"
    //           + " end"
    //           + " from lbmission"
    //           + " where missionprop1 = '"+prtNo+"'"
    //           + " and activityid = '0000001099'"
    //           + " union"
    //           + " select case missionprop5"
    //           + " when 'TB05' then '3'"
    //           + " when 'TB12' then '3'"
    //           + " when 'TB06' then '5'"
    //           + " else '1'"
    //           + " end"
    //           + " from lbmission"
    //           + " where missionprop1 = '"+prtNo+"'"
    //           + " and activityid = '0000001098'"
    //           + ") where rownum=1";prompt("",strSql); 
    //    arrResult = easyExecSql(strSql);         
    //}
    //var BankFlag = arrResult[0][0];
    
    
    //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
    var BankFlag = "";
    var tSplitPrtNo = prtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
    //alert("BankFlag=="+BankFlag);
    
			 var sqlid4="ProposalApproveModifySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(prtNo);//ָ������Ĳ���
	     var strSql2=mySql4.getString();	
	
//    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
     
    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3" , "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}*/

//�������ѯ
function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cProposalNo = "";
	  var cflag = "2";
	  
	  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
	    if (SelfPolGrid.getSelNo(i)) { 
	      checkFlag = SelfPolGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cProposalNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 
	    //alert(cProposalNo);
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 
	    return false;
	  }
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
}

/*function ApplyUW()
{
    
    //У���Ƿ�ɲ��� �Ƿ������������
     for (i=0; i<PolGrid.mulLineCount; i++) {
	    if (PolGrid.getSelNo(i)) { 
	      checkFlag = PolGrid.getSelNo();
	      if(PolGrid.getRowColData(checkFlag -1,12)=="�����������")
	      {
	         alert("ӡˢ��Ϊ��"+PolGrid.getRowColData(checkFlag -1,1)+"�ı�������ҵ��Ա������������������������룡");
	         return false;
	      }
	    }
	  }
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}


	fm.MissionID.value = PolGrid.getRowColData(selno, 5);
	fm.SubMissionID.value = PolGrid.getRowColData(selno, 6);
	fm.ActivityID.value = PolGrid.getRowColData(selno, 7);
  
	//09-06-06  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
	
		var sqlid5="ProposalApproveModifySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(fm.MissionID.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
	     var tOperatorSql=mySql5.getString();	
	
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001002'"
//					+ " and defaultoperator is not null";
	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("�����ѱ�������Ա���뵽���˹����أ�");
		easyQueryClick();
		initQuerySelf();
		return false;
	}
	
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

lockScreen('lkscreen');  
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //�ύ
	

}

function afterSubmit( FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  

	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		// ˢ��ҳ��
		easyQueryClick();
		initQuerySelf();
	}
	else
	{	
		modifyClick();	//����������޸�ҳ��
		// ˢ��ҳ��
		easyQueryClick();
		initQuerySelf();
	}
}

function showNotePad()
{

	var selno = SelfPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = SelfPolGrid.getRowColData(selno, 5);
	var SubMissionID = SelfPolGrid.getRowColData(selno, 6);
	var ActivityID = SelfPolGrid.getRowColData(selno, 7);
	var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}*/

// modify by lzf 2013-03-18
function ApplyUW()
{
    
    //У���Ƿ�ɲ��� �Ƿ������������
     for (i=0; i<PublicWorkPoolGrid.mulLineCount; i++) {
	    if (PublicWorkPoolGrid.getSelNo(i)) { 
	      checkFlag = PublicWorkPoolGrid.getSelNo();
	      if(PublicWorkPoolGrid.getRowColData(checkFlag -1,12)=="�����������")
	      {
	         alert("ӡˢ��Ϊ��"+PublicWorkPoolGrid.getRowColData(checkFlag -1,1)+"�ı�������ҵ��Ա������������������������룡");
	         return false;
	      }
	    }
	  }
	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}

   
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 12);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 15);
  
	//09-06-06  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
	
		var sqlid5="ProposalApproveModifySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(fm.MissionID.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
	     var tOperatorSql=mySql5.getString();	
	
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001002'"
//					+ " and defaultoperator is not null";
	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("�����ѱ�������Ա���뵽���˹����أ�");
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
		return false;
	}
	
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
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //�ύ
	

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
		// ˢ��ҳ��
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}
	else
	{	
		modifyClick();	//����������޸�ҳ��
		// ˢ��ҳ��
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}
}

function modifyClick() {
	  var i = 0;
	  var checkFlag = 0;
	  
	  for (i=0; i<PublicWorkPoolGrid.mulLineCount; i++) {
	    if (PublicWorkPoolGrid.getSelNo(i)) { 
	      checkFlag = PublicWorkPoolGrid.getSelNo();
	      break;
	    }
	  }
	  
	  if (checkFlag) { 
	  
	  	var cPolNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 1); 	
	  	mSwitch.deleteVar( "PolNo" );
	  	mSwitch.addVar( "PolNo", "", cPolNo );
	  	
	    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
	    var prtNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 1);        
	    var tmissionid=PublicWorkPoolGrid.getRowColData(checkFlag- 1, 12);     //missionid
	    var tsubmissionid=PublicWorkPoolGrid.getRowColData(checkFlag - 1, 13);  //submissionid
	    var ActivityID = PublicWorkPoolGrid.getRowColData(checkFlag- 1, 15);
		  var NoType = "1";
		  var tSplitPrtNo = prtNo.substring(0,4);
	 //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
	    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
	    	BankFlag="3";
	    }else{
	    	BankFlag="1";
	    }
		
			 var sqlid3="ProposalApproveModifySql3";
			var mySql3=new SqlClass();
			mySql3.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(prtNo);//ָ������Ĳ���
		     var strSql3=mySql3.getString();	
		
//	    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
	    var crrResult = easyExecSql(strSql3);
	    var SubType="";
	    if(crrResult!=null){
	      SubType = crrResult[0][0];
	    }
	    
	    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 
	  }
	}

function InitmodifyClick() {
	//alert(178);
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PrivateWorkPoolGrid.mulLineCount; i++) {
    if (PrivateWorkPoolGrid.getSelNo(i)) { 
      checkFlag = PrivateWorkPoolGrid.getSelNo();
      break;
    }
  }
  //alert(1);
  if (checkFlag) {
  	var cPolNo = PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  
    var prtNo = PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 1);  
  
    var tmissionid=PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 12);     //missionid
  
    var tsubmissionid=PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 13);  //submissionid
    
    var ActivityID = PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 15);
	  
	  var NoType = "1";
	  
	  var strSql="";    
    
    //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
    var BankFlag = "";
    var tSplitPrtNo = prtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
 		var sqlid4="ProposalApproveModifySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(prtNo);//ָ������Ĳ���
	     var strSql2=mySql4.getString();	
	
//    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
     
    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3" , "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}

function showNotePad()
{

	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 12);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 13);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 15);
	var PrtNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}

function queryAgent()
{
  if(document.all('AgentCode').value == "")	{	 
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //��������
     // alert("cAgentCode=="+cAgentCode);
     if(cAgentCode.length!=8){
    	return;
      }	 
	}
}
function afterQuery2(arrResult)
{ 
  if(arrResult!=null)
  {
  PublicWorkPoolQueryGrid.setRowColData(0, 4, arrResult[0][0]);  
  }
}
//end by lzf 2013-03-18
/*function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	//if(document.all('ManageCom').value==""){
	//	 alert("����¼����������Ϣ��");
	//	 return;
	//}
	//tongmeng 2007-09-13 modify
	//��ѯ���д�����
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
		var sqlid6="ProposalApproveModifySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalApproveModifySql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSQL=mySql6.getString();	
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode " 
//	         + " and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL);
    //alert(arrResult);
    if (arrResult != null) 
    {
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
//  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
     
      //if(fm.AgentManageCom.value != fm.ManageCom.value)
      //{
      //   	
    	//    fm.ManageCom.value = fm.AgentManageCom.value;
    	//    fm.ManageComName.value = fm.AgentManageComName.value;
    	//    //showCodeName('comcode','ManageCom','AgentManageComName');  //���뺺��
    	//   
      //}
      //showContCodeName();
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
    }
   
	}
}

function afterQuery2(arrResult)
{
  alert("444444"+arrResult);
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
  	 // fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];

  	//showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}*/
