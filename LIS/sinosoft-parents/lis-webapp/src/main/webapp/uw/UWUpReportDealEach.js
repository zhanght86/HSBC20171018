//�������ƣ�Underwrite.js
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnConfirmPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var pflag = "1";  //�������� 1.���˵�

// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;

//�ύ�����水ť��Ӧ����
function submitForm(flag)
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   if(document.all('ReinsuredResult').value==null||trim(document.all('ReinsuredResult').value)=='')
  {
  	 alert("����¼���ٱ�����!");
  	return ;
  }
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  			
  var tReinsuDesc = fm.ReinsuDesc.value;                  //�˱�����
 
  var tReinsuRemark = fm.ReinsuRemark.value;                    
 

  
//  if(tReinsuDesc == "")
//   {
//   	showInfo.close();
//    alert("����¼��ԭ������!");
//  	return ;
//  }
//
//
//  if(tReinsuRemark == "")
//   {
//    showInfo.close();
//    alert("����¼���ٱ���ע!");
//    return ;
//  }
 
  fm.action = "./UWManuUpReportDealChk.jsp";
  fm.submit(); //�ύ
 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");    
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="�����ɹ���";  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //ִ����һ������
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit3( FlagStr, content )
{
	showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  

  if (FlagStr == "Fail" )
  {                 
    alert(content);
  }
  else
  { 
			showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
    //ִ����һ������
    	InitClick();
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  if (FlagStr == "Fail" )
  {                 
    showInfo.close();
	alert(content);
     //parent.close();
  }
  else
  { 
  	showInfo.close();
	alert(content);
	
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {                     
    alert(content);    
    	// ��ʼ�����
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
	divLCPol2.style.display= "none";
	divMain.style.display = "none";  	  
  }
  else
  {  
  		makeWorkFlow();
  }
}

function afterAddFeeApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {                     
    alert(content);    
    	// ��ʼ�����
 }
  else
  { 
  	var cPolNo=fm.ProposalNo.value;
	  var cMissionID =fm.MissionID.value; 
	  var cSubMissionID =fm.SubMissionID.value; 
	  var tSelNo = PolAddGrid.getSelNo()-1;
	  var cNo = PolAddGrid.getRowColData(tSelNo,1);	
	  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);	
	  var cType = PolAddGrid.getRowColData(tSelNo,7);	

	 if (cPrtNo != ""&& cNo !="" && cMissionID != "" )
	  {
	  	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&No="+cNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&Type="+cType,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes"); 
	  	showInfo.close();
	  }
	  else
	  {
	  	showInfo.close();
	  	alert("����ѡ�񱣵�!");
	  }
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

/*********************************************************************
 *  ѡ��˱����Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {
	
	    //alert("uwstate" + cCodeName + Field.value);
		if( cCodeName == "uwstate" ) {
			DoUWStateCodeSelect(Field.value);//loadFlag��ҳ���ʼ����ʱ������
		}
}



// ��Ͷ�������������ѯ
function ClaimGetQuery2()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolAddGrid.getRowColData(tSel - 1,2);				
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);										
	}	
}
// ���������ѯ
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cInsuredNo = fm.InsuredNo.value;				
		if (cInsuredNo == "")
		    return;
		  window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo);										
	}	
}

//������ϸ��Ϣ
function showPolDetail()
{
 
  var tContNo = fm.ProposalContNo.value;
  var cPrtNo = fm.PrtNo.value;

   var sqlid901101532="DSHomeContSql901101532";
var mySql901101532=new SqlClass();
mySql901101532.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901101532.setSqlId(sqlid901101532);//ָ��ʹ�õ�Sql��id
mySql901101532.addSubPara(tContNo);//ָ������Ĳ���
var strSQL=mySql901101532.getString();
   
//   var strSQL = "select salechnl,cardflag from lccont where prtno='"+tContNo+"'";
   var arrResult = easyExecSql(strSQL);
   //alert(arrResult[0][0]);
   var tSaleChnl = arrResult[0][0];
   var tCardFlag = arrResult[0][1];
   //tSaleChnl=1;
   //alert(tSaleChnl);
   //alert(tCardFlag);
   var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
  var sqlid901101629="DSHomeContSql901101629";
var mySql901101629=new SqlClass();
mySql901101629.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901101629.setSqlId(sqlid901101629);//ָ��ʹ�õ�Sql��id
mySql901101629.addSubPara(cPrtNo);//ָ������Ĳ���
var strSql2=mySql901101629.getString();

  
//  var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+cPrtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
		SubType = crrResult[0][0];
	}

  window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+tContNo+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}           


//ɨ�����ѯ
function ScanQuery()
{

	  var prtNo = fm.PrtNo.value;	
		
		if (prtNo == "") return;
		    
		window.open("../uw/ImageQueryMain.jsp?ContNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
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

var withdrawFlag = false;
//���������ѯ,add by Minim
function withdrawQueryClick() {
  withdrawFlag = true;
  easyQueryClick();
}

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQuery
 *  ����:��ѯ��ʾ�����Ǻ�ͬ����.��ʾ����:��ͬδ�����˹��˱�����״̬Ϊ���˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	// ��ʼ�����

	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
    divLCPol2.style.display= "none";
    divMain.style.display = "none";
	
	// ��дSQL���
	k++;
	var uwgradestatus = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //��������״̬ 
	var strSQL = "";
	if (uwgradestatus == "1")//��������
	{
		
		var sqlid901102058="DSHomeContSql901102058";
var mySql901102058=new SqlClass();
mySql901102058.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901102058.setSqlId(sqlid901102058);//ָ��ʹ�õ�Sql��id
mySql901102058.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql901102058.addSubPara(fm.QAppntName.value );//ָ������Ĳ���
mySql901102058.addSubPara(fm.QProposalNo.value);//ָ������Ĳ���
mySql901102058.addSubPara(fm.QManageCom.value);//ָ������Ĳ���
mySql901102058.addSubPara(mOperate);//ָ������Ĳ���
mySql901102058.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql901102058.getString();
		
//		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k				 	
//				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
//				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
//				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
//				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
//				 + getWherePart( 'LWMission.MissionProp2','QProposalNo')
//				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
//				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') = (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
//				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������	
	}
	else 
	  if (uwgradestatus == "2")//�¼�����
	  {
		
		var sqlid901103453="DSHomeContSql901103453";
var mySql901103453=new SqlClass();
mySql901103453.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901103453.setSqlId(sqlid901103453);//ָ��ʹ�õ�Sql��id
mySql901103453.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql901103453.addSubPara(fm.QAppntName.value );//ָ������Ĳ���
mySql901103453.addSubPara(fm.QProposalNo.value);//ָ������Ĳ���
mySql901103453.addSubPara(fm.QManageCom.value);//ָ������Ĳ���
mySql901103453.addSubPara(mOperate);//ָ������Ĳ���
mySql901103453.addSubPara(comcode="%%");//ָ������Ĳ���
strSQL=mySql901103453.getString();

		
//		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k				 	
//				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
//				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
//				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
//				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
//				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
//				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
//				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') > (select AppGrade from LCCUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
//				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������	
	   }
	   else //����+�¼�����
	   {
		
		var sqlid901103802="DSHomeContSql901103802";
var mySql901103802=new SqlClass();
mySql901103802.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901103802.setSqlId(sqlid901103802);//ָ��ʹ�õ�Sql��id
mySql901103802.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql901103802.addSubPara(fm.QAppntName.value );//ָ������Ĳ���
mySql901103802.addSubPara(fm.QProposalNo.value);//ָ������Ĳ���
mySql901103802.addSubPara(fm.QManageCom.value);//ָ������Ĳ���
mySql901103802.addSubPara(mOperate);//ָ������Ĳ���
mySql901103802.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql901103802.getString();
		
//		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k				 	
//				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
//				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
//				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
//				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
//				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
//				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
//				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') >= (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
//				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������	
	}
	
     //alert(strSQL);
     var tOperator = fm.QOperator.value;
	if(state == "1")
	{	
			
			var sqlid901155632="DSHomeContSql901155632";
var mySql901155632=new SqlClass();
mySql901155632.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901155632.setSqlId(sqlid901155632);//ָ��ʹ�õ�Sql��id
mySql901155632.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql901155632.addSubPara(fm.QAppntName.value );//ָ������Ĳ���
mySql901155632.addSubPara(fm.QProposalNo.value);//ָ������Ĳ���
mySql901155632.addSubPara(fm.QManageCom.value+"%%");//ָ������Ĳ���
mySql901155632.addSubPara(mOperate);//ָ������Ĳ���
mySql901155632.addSubPara(comcode);//ָ������Ĳ���
mySql901155632.addSubPara(tOperator);//ָ������Ĳ���
strSQL=mySql901155632.getString();
			
//			strSQL = strSQL + " and  LWMission.ActivityStatus = '1'"
//		      + " and ( LWMission.DefaultOperator is null or LWMission.DefaultOperator = '" + tOperator + "')"; 
	}
	if(state == "2")
	{
		var sqlid901160231="DSHomeContSql901160231";
var mySql901160231=new SqlClass();
mySql901160231.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901160231.setSqlId(sqlid901160231);//ָ��ʹ�õ�Sql��id
mySql901160231.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql901160231.addSubPara(fm.QAppntName.value );//ָ������Ĳ���
mySql901160231.addSubPara(fm.QProposalNo.value);//ָ������Ĳ���
mySql901160231.addSubPara(fm.QManageCom.value+"%%");//ָ������Ĳ���
mySql901160231.addSubPara(mOperate);//ָ������Ĳ���
mySql901160231.addSubPara(comcode);//ָ������Ĳ���
mySql901160231.addSubPara(tOperator);//ָ������Ĳ���
strSQL=mySql901160231.getString();
		
//		strSQL = strSQL + " and  LWMission.ActivityStatus = '3'"
//		      + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}
	if(state == "3")
	{
		var sqlid901160522="DSHomeContSql901160522";
var mySql901160522=new SqlClass();
mySql901160522.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901160522.setSqlId(sqlid901160522);//ָ��ʹ�õ�Sql��id
mySql901160522.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql901160522.addSubPara(fm.QAppntName.value );//ָ������Ĳ���
mySql901160522.addSubPara(fm.QProposalNo.value);//ָ������Ĳ���
mySql901160522.addSubPara(fm.QManageCom.value+"%%");//ָ������Ĳ���
mySql901160522.addSubPara(mOperate);//ָ������Ĳ���
mySql901160522.addSubPara(comcode);//ָ������Ĳ���
mySql901160522.addSubPara(tOperator);//ָ������Ĳ���
strSQL=mySql901160522.getString();
		
//		strSQL = strSQL + " and  LWMission.ActivityStatus = '2'"
//		    + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}
	
	
	if (withdrawFlag) {
	  //strSQL = strSQL + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0') ";
	  
	  var sqlid901161117="DSHomeContSql901161117";
var mySql901161117=new SqlClass();
mySql901161117.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901161117.setSqlId(sqlid901161117);//ָ��ʹ�õ�Sql��id
mySql901161117.addSubPara(mOperate);//ָ������Ĳ���
mySql901161117.addSubPara(manageCom+"%%");//ָ������Ĳ���
mySql901161117.addSubPara(fm.QOperator.value);//ָ������Ĳ���
strSQL=mySql901161117.getString();

	  
//	  strSQL = "select LCPol.ProposalNo,LCPol.PrtNo,LMRisk.RiskName,LCPol.AppntName,LCPol.InsuredName "
//           + " from LCPol,LCUWMaster,LMRisk where 10=10 "
//           + " and LCPol.AppFlag='0'  "
//           + " and LCPol.UWFlag not in ('1','2','a','4','9')  "
//           + " and LCPol.grppolno = '00000000000000000000' and LCPol.contno = '00000000000000000000' "
//           + " and LCPol.ProposalNo = LCPol.MainPolNo  and LCPol.ProposalNo= LCUWMaster.ProposalNo  "
//           + " and LCUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') "
//           + " and LCPol.ManageCom like '" + manageCom + "%%'"
//           + " and LMRisk.RiskCode = LCPol.RiskCode "
//           + getWherePart( 'LCUWMaster.Operator','QOperator')
//           + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0')";

	  withdrawFlag = false;
	}

	//execEasyQuery( strSQL );
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ���б��˱��ĸ��˵���");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  return true;
}

/*********************************************************************
 *  ִ��multline�ĵ���¼�
 *  ����:�˴����ɹ������ķ���졢�������˱�֪ͨ��Ƚ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryAddClick(parm1,parm2)
{
	// ��дSQL���
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //Ͷ��������״̬ 
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";
	
	if(document.all(parm1).all('InpPolGridSel').value == '1' ){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		tProposalNo = document.all(parm1).all('PolGrid2').value;
		tPNo = document.all(parm1).all('PolGrid1').value;
	}

	document.all('ContNo').value = document.all(parm1).all('PolGrid2').value;
	document.all('MissionID').value = document.all(parm1).all('PolGrid6').value;
	document.all('PrtNo').value = document.all(parm1).all('PolGrid1').value;
	document.all('SubMissionID').value = document.all(parm1).all('PolGrid7').value;
	var ContNo = document.all('ContNo').value;
	
	//alert("contno11="+document.all('ContNo').value);
	
	
	if(state == "1")
	{
		checkDouble(tProposalNo);		
	//���ɹ������½ڵ�
	}
	else
		{
			makeWorkFlow();
			}
	
	// ��ʼ�����
	initPolAddGrid();
	initPolBox();
	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	if (uwgrade == "1")
	{
		var sqlid901161550="DSHomeContSql901161550";
var mySql901161550=new SqlClass();
mySql901161550.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901161550.setSqlId(sqlid901161550);//ָ��ʹ�õ�Sql��id
mySql901161550.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901161550.getString();
		
//		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '����ͣ' end  from LCInsured where "+k+"="+k
//				  + " and LCInsured.Contno = '" + ContNo + "'";
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k				 	
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
	}
	else
	{
		var sqlid901161644="DSHomeContSql901161644";
var mySql901161644=new SqlClass();
mySql901161644.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901161644.setSqlId(sqlid901161644);//ָ��ʹ�õ�Sql��id
mySql901161644.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901161644.getString();
		
//		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '����ͣ' end from LCInsured where "+k+"="+k
//				  + " and LCInsured.Contno = '" + ContNo + "'";
	
	}
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ���б��˱��ĸ��˵���");
 		window.location.href("./ManuUWInput.jsp");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("��ѯeasyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );
  
  //alert("contno21="+document.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+document.all('PrtNo').value);
  	var arrSelected = new Array();

	var sqlid901161748="DSHomeContSql901161748";
var mySql901161748=new SqlClass();
mySql901161748.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901161748.setSqlId(sqlid901161748);//ָ��ʹ�õ�Sql��id
mySql901161748.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901161748.getString();
	
//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('ProposalContNo').value = arrSelected[0][0];
	document.all('PrtNo').value = arrSelected[0][1];
	document.all('ManageCom').value = arrSelected[0][2];
	document.all('SaleChnl').value = arrSelected[0][3];
	document.all('AgentCode').value = arrSelected[0][4];
	document.all('AgentGroup').value = arrSelected[0][5];
	document.all('AgentCode1').value = arrSelected[0][6];
	document.all('AgentCom').value = arrSelected[0][7];
	document.all('AgentType').value = arrSelected[0][8];
	document.all('ReMark').value = arrSelected[0][9];
	
	var sqlid901161837="DSHomeContSql901161837";
var mySql901161837=new SqlClass();
mySql901161837.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901161837.setSqlId(sqlid901161837);//ָ��ʹ�õ�Sql��id
mySql901161837.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901161837.getString();

	
//	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag from lcappnt,LDPerson where contno='"+ContNo+"'"
//		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('AppntName').value = arrSelected[0][0];
	document.all('AppntSex').value = arrSelected[0][1];
	document.all('AppntBirthday').value = arrSelected[0][2];
	document.all('AppntNo').value = arrSelected[0][3];
	document.all('AddressNo').value = arrSelected[0][4];
	document.all('VIPValue').value = arrSelected[0][5];
	document.all('BlacklistFlag').value = arrSelected[0][6];
	
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
		initPolGrid();
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			}// end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//����Ҫ�˹��˱�����
function checkDouble(tProposalNo)
{
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	fm.submit();
}	

//ѡ��Ҫ�˹��˱�����
function getPolGridCho()
{
	//setproposalno();
	var cSelNo = PolAddGrid.getSelNo()-1;
	
	codeSql = "code";
	fm.UWPopedomCode.value = "";
	fm.action = "./ManuUWCho.jsp";
	fm.submit();
}

/*********************************************************************
 *  ���ɹ������������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function makeWorkFlow()
{
	fm.action = "./ManuUWChk.jsp";
	fm.submit();
	}

function checkBackPol(ProposalNo) {
  
  var sqlid901161929="DSHomeContSql901161929";
var mySql901161929=new SqlClass();
mySql901161929.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901161929.setSqlId(sqlid901161929);//ָ��ʹ�õ�Sql��id
mySql901161929.addSubPara(ProposalNo);//ָ������Ĳ���
var strSql=mySql901161929.getString();
  
//  var strSql = "select * from LCApplyRecallPol where ProposalNo='" + ProposalNo + "' and InputState='0'";
  var arrResult = easyExecSql(strSql);
  //alert(arrResult);
  
  if (arrResult != null) {
    return false;
  }
  return true;
}

//  ��ʼ���˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ�������
function initFlag(  tlength )
{
	// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
      showPolDetailFlag =  new Array() ;
      showAppFlag = new Array() ;
      showHealthFlag = new Array() ;
      QuestQueryFlag = new Array() ;
    
     var i=0;
	  for( i = 0; i < tlength ; i++ )
		{
			showPolDetailFlag[i] = 0;
			showAppFlag[i] = 0;
			showHealthFlag[i] = 0;
			QuestQueryFlag[i] = 0;
		} 
	
	}
//�º˱�����
function manuchk()
{
	
	flag = document.all('UWState').value;
	var ProposalNo = document.all('ProposalNo').value;
	var MainPolNo = document.all('MainPolNoHide').value;
	
	if (trim(fm.UWState.value) == "") {
    alert("������¼��˱����ۣ�");
    return;
  }
   
	if (!checkBackPol(ProposalNo)) {
	  if (!confirm("��Ͷ�����г������룬�����´˽�����")) return;
	}
	
    if (trim(fm.UWState.value) == "6") {
      if(trim(fm.UWPopedomCode.value) !="")
         fm.UWOperater.value = fm.UWPopedomCode.value
      else 
         fm.UWOperater.value = operator;
}

    var tSelNo = PolAddGrid.getSelNo()-1;
        
	if(fm.State.value == "1"&&(fm.UWState.value == "1"||fm.UWState.value == "2"||fm.UWState.value =="4"||fm.UWState.value =="6"||fm.UWState.value =="9"||fm.UWState.value =="a")) {
      if( showPolDetailFlag[tSelNo] == 0 || showAppFlag[tSelNo] == 0 || showHealthFlag[tSelNo] == 0 || QuestQueryFlag[tSelNo] == 0 ){
         var tInfo = "";
         if(showPolDetailFlag[tSelNo] == 0)
            tInfo = tInfo + " [Ͷ������ϸ��Ϣ]";
         if(showAppFlag[tSelNo] == 0)   
            tInfo = tInfo + " [����Ͷ����Ϣ]";
         if( PolAddGrid.getRowColData(tSelNo,1,PolAddGrid) == PolAddGrid.getRowColData(tSelNo ,2,PolAddGrid)) {
         	if(showHealthFlag[tSelNo] == 0)
              tInfo = tInfo + " [�������¼��]";
         }
         if(QuestQueryFlag[tSelNo] == 0)
            tInfo = tInfo + " [�������ѯ]";
         if ( tInfo != "" ) {
         	tInfo = "����Ҫ��Ϣ:" + tInfo + " δ�鿴,�Ƿ�Ҫ�¸ú˱�����?";
         	if(!window.confirm( tInfo ))
         	    return;
             }
             
        } 
     }
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	fm.action = "./UWManuNormChk.jsp";
	fm.submit();

	if (flag != "b"&&ProposalNo == MainPolNo)
	{
		initInpBox();
   		initPolBox();
		initPolGrid();
		initPolAddGrid();
	}
}





//���ռƻ��������¼����ʾ
function showChangeResultView()
{
	fm.ChangeIdea.value = "";
	//fm.PolNoHide.value = fm.ProposalNo.value;  //��������
	divUWResult.style.display= "none";
	divChangeResult.style.display= "";	
}


//��ʾ���ռƻ��������
function showChangeResult()
{
	fm.uwState.value = "b";
	fm.UWIdea.value = fm.ChangeIdea.value;
	var cContNo = fm.ProposalContNo.value;
	
	cChangeResult = fm.UWIdea.value;
	
	if (cChangeResult == "")
	{
		alert("û��¼�����!");
	}
	else
	{
		var sqlid901162042="DSHomeContSql901162042";
var mySql901162042=new SqlClass();
mySql901162042.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901162042.setSqlId(sqlid901162042);//ָ��ʹ�õ�Sql��id
mySql901162042.addSubPara(cContNo);//ָ������Ĳ���
var strSql=mySql901162042.getString();
		
//		var strSql = "select * from LCIssuepol where ContNo='" + cContNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
       if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo )){
       	      HideChangeResult()
         	    return;
          }
       }
       
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
 
  	fm.action = "./UWManuNormChk.jsp";
  	fm.submit(); //�ύ
  }	
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";		
}

//���ر��ռƻ��������
function HideChangeResult()
{
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";			
}


function cancelchk()
{
	document.all('uwState').value = "";
	document.all('UWPopedomCode').value = "";
	document.all('UWIdea').value = "";
}

function setproposalno()
{
	var count = PolGrid.getSelNo();
	document.all('ProposalNo').value = PolGrid.getRowColData(count - 1,1,PolGrid);
}

//�����հ�ť���غ���
function hideAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "none";
	parent.fraInterface.divAddButton2.style.display= "none";
	parent.fraInterface.divAddButton3.style.display= "none";
	parent.fraInterface.divAddButton4.style.display= "none";
	parent.fraInterface.divAddButton5.style.display= "none";
	parent.fraInterface.divAddButton6.style.display= "none";
	parent.fraInterface.divAddButton7.style.display= "none";
	parent.fraInterface.divAddButton8.style.display= "none";
	parent.fraInterface.divAddButton9.style.display= "none";
	parent.fraInterface.divAddButton10.style.display= "none";
	//parent.fraInterface.fm.UWState.CodeData = "0|^4|ͨ�ڳб�^9|�����б�";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^4|ͨ��/�����б�^9|�����б�\" ondblclick= \"showCodeListEx('UWState1',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState1',[this,''],[0,1]);\">";
	

}

//��ʾ���ذ�ť
function showAddButton()
{	
	parent.fraInterface.divAddButton1.style.display= "";
	parent.fraInterface.divAddButton2.style.display= "";
	parent.fraInterface.divAddButton3.style.display= "";
	parent.fraInterface.divAddButton4.style.display= "";
	parent.fraInterface.divAddButton5.style.display= "";
	parent.fraInterface.divAddButton6.style.display= "";
	parent.fraInterface.divAddButton7.style.display= "";
	parent.fraInterface.divAddButton8.style.display= "";
	parent.fraInterface.divAddButton9.style.display= "";
	parent.fraInterface.divAddButton10.style.display= "";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^1|�ܱ�^2|����^4|ͨ��/�����б�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����\" ondblclick= \"showCodeListEx('UWState',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState',[this,''],[0,1]);\">";
	//parent.fraInterface.fm.UWState.CodeData = "0|^1|�ܱ�^2|����^4|ͨ�ڳб�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����";
}



function InitClick(){
	location.href  = "./ManuUWAll.jsp?type=1";
}
	
/*********************************************************************
 *  ���뱻������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */	
function showInsuredInfo()
{
	
	  var ContNo = fm.ContNo.value;
	  var tSelNo = PolAddGrid.getSelNo()-1;
	  var InsuredNo = PolAddGrid.getRowColData(tSelNo,1);
	  
	  //�����˽�����֪��ѯ��ť����
	  
	  var sqlid901162215="DSHomeContSql901162215";
var mySql901162215=new SqlClass();
mySql901162215.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901162215.setSqlId(sqlid901162215);//ָ��ʹ�õ�Sql��id
mySql901162215.addSubPara(ContNo);//ָ������Ĳ���
mySql901162215.addSubPara(InsuredNo);//ָ������Ĳ���
var sqlind=mySql901162215.getString();
	  
//    var sqlind = "select * from lccustomerimpart where contno = '"+ContNo+"' and impartver in ('101','A01','B01','C01','D01')"
//        + " and customernotype='1' and customerno = '"+InsuredNo+"' ";
       //prompt('',sql);
	   var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind==null){
        document.all('Button12').disabled='true';
      }
      else{
        document.all('Button12').disabled='';
      }
      //
      
      var sqlid901163019="DSHomeContSql901163019";
var mySql901163019=new SqlClass();
mySql901163019.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901163019.setSqlId(sqlid901163019);//ָ��ʹ�õ�Sql��id
mySql901163019.addSubPara(ContNo);//ָ������Ĳ���
mySql901163019.addSubPara(InsuredNo);//ָ������Ĳ���
mySql901163019.addSubPara(ContNo);//ָ������Ĳ���
mySql901163019.addSubPara(InsuredNo);//ָ������Ĳ���
mySql901163019.addSubPara(ContNo);//ָ������Ĳ���
mySql901163019.addSubPara(InsuredNo);//ָ������Ĳ���
mySql901163019.addSubPara(ContNo);//ָ������Ĳ���
mySql901163019.addSubPara(InsuredNo);//ָ������Ĳ���
sqlind=mySql901163019.getString();
      
//      sqlind = "select 1 from lcpol where contno<> '"+ContNo+"' " 
//			  + " and (appntno = '"+InsuredNo+"' "
//			  + " or insuredno = '"+InsuredNo+"' ) "
//			  + " and conttype='1' and appflag in ('1','4') "
//			  + " union "
//			  + " select 1 from lcpol where contno<> '"+ContNo+"' "
//			  + " and insuredno = '"+InsuredNo+"' "
//			  + " and conttype='1' and appflag ='0' "
//			  + " union "
//			  + " select 1 from lcpol where contno<> '"+ContNo+"' "
//			  + " and (appntno = '"+InsuredNo+"' or insuredno = '"+InsuredNo+"') "
//			  + " and conttype='2' ";
       //prompt('',sqlind);
	   var arrind1 = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind1==null){
        document.all('indButton2').disabled='true';
      }
      else{
        document.all('indButton2').disabled='';
      }
	  
	  //���㱻���˷��ձ���������� 2008-11-25 add ln
	//alert(InsuredNo); 
    var tSumAmnt =0;
    
    var sqlid901163420="DSHomeContSql901163420";
var mySql901163420=new SqlClass();
mySql901163420.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901163420.setSqlId(sqlid901163420);//ָ��ʹ�õ�Sql��id
mySql901163420.addSubPara(InsuredNo);//ָ������Ĳ���
var strSQL=mySql901163420.getString();

    
//    var strSQL =  "SELECT healthyamnt2('" + InsuredNo +"','1','1') from dual ";
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('InsuredSumLifeAmnt').value=arr1[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
	}
	 
	 var sqlid901163717="DSHomeContSql901163717";
var mySql901163717=new SqlClass();
mySql901163717.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901163717.setSqlId(sqlid901163717);//ָ��ʹ�õ�Sql��id
mySql901163717.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql901163717.getString();
	 
//	 strSQL =  "SELECT healthyamnt2('" + InsuredNo +"','2','1') from dual ";
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('InsuredSumHealthAmnt').value=arr2[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
	}
	
	 var sqlid901163823="DSHomeContSql901163823";
var mySql901163823=new SqlClass();
mySql901163823.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901163823.setSqlId(sqlid901163823);//ָ��ʹ�õ�Sql��id
mySql901163823.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql901163823.getString();
	 
//	 strSQL =  "SELECT healthyamnt2('" + InsuredNo +"','3','1') from dual ";
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('InsuredMedicalAmnt').value=arr3[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
	var sqlid901163924="DSHomeContSql901163924";
var mySql901163924=new SqlClass();
mySql901163924.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901163924.setSqlId(sqlid901163924);//ָ��ʹ�õ�Sql��id
mySql901163924.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql901163924.getString();
	
//	strSQL =  "SELECT healthyamnt2('" + InsuredNo +"','4','1') from dual ";
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('InsuredAccidentAmnt').value=arr4[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
	}
	document.all('InsuredSumAmnt').value=tSumAmnt;
	
    var sqlid901164130="DSHomeContSql901164130";
var mySql901164130=new SqlClass();
mySql901164130.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164130.setSqlId(sqlid901164130);//ָ��ʹ�õ�Sql��id
mySql901164130.addSubPara(InsuredNo);//ָ������Ĳ���
mySql901164130.addSubPara(InsuredNo);//ָ������Ĳ���
mySql901164130.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql901164130.getString();

    
    //�ۼƱ��� �����������Ͳ����ڽ�����   
//    strSQL =  "SELECT decode(sum(a_Prem),'','x',sum(a_Prem)) FROM"
//          + "(select (case"
//          + " when s_PayIntv = '1' then"
//          + " s_Prem*0.09"
//          + " when s_PayIntv = '3' then"
//          + " s_Prem*0.27"
//          + " when s_PayIntv = '6' then"
//          + " s_Prem*0.52"
//          + " when s_PayIntv = '12' then"
//          + " s_Prem"
//          + " end) a_Prem"          
//          + " FROM (select distinct payintv as s_PayIntv,"
//          + " sum(prem) as s_Prem"
//          + " from lcpol c where polno in(select polno "
//	          + " from lcpol a"
//	          + " where a.insuredno = '"+InsuredNo+"'"
//	          + " or (a.appntno = '"+InsuredNo+"' and a.riskcode in ('00115000','00115001'))"
//	          + " union"
//	          + " select b.polno"
//	          + " from lcinsuredrelated b"
//	          + " where b.customerno = '"+InsuredNo+"')"
//          + " and c.payintv in ('1', '3','6','12')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'" 
//          + " and not exists (select 'X'"
//	          + " from lccont"
//	          + " where ContNo = c.contno"
//	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by payintv))";
    //prompt('',strSQL);
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	       //alert(arr5[0][0]);    
	       if(arr5[0][0]!='x')
	       	  document.all('InsuredSumPrem').value=arr5[0][0];
	    }
	  
	  var arrReturn = new Array();
//		var tSql1 = "select InsuredNo,name,sex,birthday,NativePlace,RelationToMainInsured,RelationToAppnt,OccupationCode,OccupationType from LCInsured t where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and insuredno ='"+InsuredNo+"'"
//							;
    
    var sqlid901164403="DSHomeContSql901164403";
var mySql901164403=new SqlClass();
mySql901164403.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164403.setSqlId(sqlid901164403);//ָ��ʹ�õ�Sql��id
mySql901164403.addSubPara(ContNo);//ָ������Ĳ���
mySql901164403.addSubPara(InsuredNo);//ָ������Ĳ���
var tSql1=mySql901164403.getString();
    
//    var tSql1 = " select InsuredNo,"
//              + " name,"
//              + " case sex"
//              + " when '0' then"
//              + " '��'"
//              + " when '1' then"
//              + " 'Ů'"
//              + " else"
//              + " '����'"
//              + " end case,"
//              + " birthday,"
//              + " (select ldcode.codename"
//              + " from ldcode"
//              + " where ldcode.codetype = 'nativeplace'"
//              + " and ldcode.code = t.NativePlace),"
//              + " (select ldcode.codename"
//              + " from ldcode"
//              + " where ldcode.codetype = 'relation'"
//              + " and ldcode.code = t.RelationToMainInsured),"
//              + " (select ldcode.codename"
//              + " from ldcode"
//              + " where ldcode.codetype = 'relation'"
//              + " and ldcode.code = t.RelationToAppnt),"
//              + " (select occupationname"
//              + " from ldoccupation"
//              + " where occupationcode = t.OccupationCode),"
//              + " trim(OccupationType) || '��',"
//              + " (select codename"
//              + " from ldcode"
//              + " where codetype = 'idtype'"
//              + " and code = t.idtype),"
//              + " t.idno"
//              + " from LCInsured t"
//              + " where 1 = 1"
//              + " and contno = '" + ContNo + "'"
//              + " and insuredno = '" + InsuredNo + "'";


							
	   
//  alert(tSql1);
	turnPage.strQueryResult  = easyQueryVer3(tSql1, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��������Ϣ��ѯʧ��!");
    return "";
  }
  
  arrResult = decodeEasyQueryResult(turnPage.strQueryResult);	

  document.all('InsuredNo').value = arrResult[0][0];
  document.all('Name').value = arrResult[0][1];
  document.all('Sex').value = arrResult[0][2];
  document.all('InsuredAppAge').value = arrResult[0][3];
  document.all('NativePlace').value = arrResult[0][4];
  document.all('RelationToMainInsured').value = arrResult[0][5];
   
  document.all('RelationToAppnt').value = arrResult[0][6];
  document.all('OccupationCode').value = arrResult[0][7];
  document.all('OccupationType').value = arrResult[0][8];
  document.all('InsuredIDType').value = arrResult[0][9];
  document.all('InsuredIDNo').value = arrResult[0][10];
  
	


	 var sqlid901164508="DSHomeContSql901164508";
var mySql901164508=new SqlClass();
mySql901164508.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164508.setSqlId(sqlid901164508);//ָ��ʹ�õ�Sql��id
mySql901164508.addSubPara(ContNo);//ָ������Ĳ���
mySql901164508.addSubPara(InsuredNo);//ָ������Ĳ���
var tSql=mySql901164508.getString();
	 
//	 var tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and insuredno ='"+InsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode"
//							;
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��������Ϣ��ѯʧ��!");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = RiskGrid ;    

  //����SQL���
  turnPage.strQuerySql     = tSql ; 

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;  					
}

/*********************************************************************
 *  ����ϵͳ��
 *  ����:�˴����ɹ������ķ���졢�������˱�֪ͨ��Ƚ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initUW()
{
	// ��дSQL���
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //Ͷ��������״̬ 
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";

	document.all('ContNo').value = ContNo;
	document.all('MissionID').value = MissionID;
	document.all('PrtNo').value = PrtNo;
	document.all('SubMissionID').value = SubMissionID;
   // makeWorkFlow();
    
	// ��ʼ�����
	initPolAddGrid();
	initPolBox();
	//initSendTrace();

	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";

	if (uwgrade == "1")
	{
		var sqlid901164609="DSHomeContSql901164609";
var mySql901164609=new SqlClass();
mySql901164609.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164609.setSqlId(sqlid901164609);//ָ��ʹ�õ�Sql��id
mySql901164609.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901164609.getString();
		
//		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
//				  + " and LCInsured.Contno = '" + ContNo + "'"
//				  + " order by LCInsured.SequenceNo "
//				  ;
				
	}
	else
	{
		var sqlid901164655="DSHomeContSql901164655";
var mySql901164655=new SqlClass();
mySql901164655.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164655.setSqlId(sqlid901164655);//ָ��ʹ�õ�Sql��id
mySql901164655.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901164655.getString();
		
//		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
//				  + " and LCInsured.Contno = '" + ContNo + "'"
//				  + " order by LCInsured.SequenceNo "
//				  ;
	
	}

	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��δͨ������Լ�˱��ĸ��˺�ͬ����");
    return;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("��ѯeasyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );
  
  //alert("contno21="+document.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+document.all('PrtNo').value);
  	var arrSelected = new Array();

	
	var sqlid901164746="DSHomeContSql901164746";
var mySql901164746=new SqlClass();
mySql901164746.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164746.setSqlId(sqlid901164746);//ָ��ʹ�õ�Sql��id
mySql901164746.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901164746.getString();
	
//	strSQL = "select ProposalContNo,PrtNo,ManageCom,(select codename from ldcode where codetype='salechnl' and code=SaleChnl),AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('ProposalContNo').value = arrSelected[0][0];
	document.all('PrtNo').value = arrSelected[0][1];
	document.all('ManageCom').value = arrSelected[0][2];
	document.all('SaleChnl').value = arrSelected[0][3];
	document.all('AgentCode').value = arrSelected[0][4];
	document.all('AgentGroup').value = arrSelected[0][5];
	document.all('AgentCode1').value = arrSelected[0][6];
	document.all('AgentCom').value = arrSelected[0][7];
	document.all('AgentType').value = arrSelected[0][8];
	
	
//	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag,ldperson.OccupationCode,ldperson.OccupationType,lcappnt.NativePlace from lcappnt,LDPerson where contno='"+ContNo+"'"
//		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
		
	
	var sqlid901164830="DSHomeContSql901164830";
var mySql901164830=new SqlClass();
mySql901164830.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164830.setSqlId(sqlid901164830);//ָ��ʹ�õ�Sql��id
mySql901164830.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901164830.getString();

	
//	strSQL = " select AppntName,"
//         + " case AppntSex"
//         + " when '0' then"
//         + "  '��'"
//         + " when '1' then"
//         + "  'Ů'"
//         + " else"
//         + "  'δ֪'"
//         + " end case,"
//         + " AppntBirthday,"
//         + " AppntNo,"
//         + " AddressNo,"
//         + " case VIPValue"
//         + " when '1' then"
//         + " '��'"
//         + " else"
//         + " '��'"
//         + " end case,"
//         + " case BlacklistFlag"
//         + " when '1' then"
//         + " '��'"
//         + " else"
//         + " '��'"
//         + " end case,"
//         + " (select ldoccupation.occupationname"
//         + " from ldoccupation"
//         + " where occupationcode = ldperson.OccupationCode),"
//         + " trim(ldperson.OccupationType) || '��',"
//         + " (select codename"
//         + " from ldcode"
//         + " where codetype = 'nativeplace'"
//         + " and code = lcappnt.NativePlace),"
//         + " (select codename"
//         + " from ldcode"
//         + " where codetype = 'idtype'"
//         + " and code = lcappnt.idtype),"
//         + " lcappnt.idno"
//         + " from lcappnt, LDPerson"
//         + " where contno = '"+ContNo+"'"
//         + " and LDPerson.CustomerNo = LCAppnt.AppntNo";
		
		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('AppntName').value = arrSelected[0][0];
	document.all('AppntSex').value = arrSelected[0][1];
	document.all('AppntBirthday').value = arrSelected[0][2];
	document.all('AppntNo').value = arrSelected[0][3];
	document.all('AddressNo').value = arrSelected[0][4];
	document.all('VIPValue').value = arrSelected[0][5];
	document.all('BlacklistFlag').value = arrSelected[0][6];

	document.all('AppntOccupationCode').value = arrSelected[0][7];
	document.all('AppntOccupationType').value = arrSelected[0][8];
	document.all('AppntNativePlace').value = arrSelected[0][9];
	document.all('AppntIDType').value = arrSelected[0][10];
	document.all('AppntIDNo').value = arrSelected[0][11];

	var sqlid901164916="DSHomeContSql901164916";
var mySql901164916=new SqlClass();
mySql901164916.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901164916.setSqlId(sqlid901164916);//ָ��ʹ�õ�Sql��id
mySql901164916.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql901164916.getString();
	
//	strSQL = "select ReportRemark,nvl(ReinsuredResult,' '),nvl(ReinsuDesc,'ԭ������'),nvl(ReinsuRemark,' ') from LCReinsureReport where ContNo ='"+ContNo+"'";
		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
		document.all('ReportRemark').value = arrSelected[0][0];
		document.all('ReinsuredResult').value = arrSelected[0][1];
	  document.all('ReinsuDesc').value = arrSelected[0][2];
	  document.all('ReinsuRemark').value = arrSelected[0][3];

	 var tSumAmnt =0;
	 
	 var sqlid901165003="DSHomeContSql901165003";
var mySql901165003=new SqlClass();
mySql901165003.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901165003.setSqlId(sqlid901165003);//ָ��ʹ�õ�Sql��id
mySql901165003.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql901165003.getString();

//    strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','1','1') from dual ";
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('AppntSumLifeAmnt').value=arr1[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
	}
	 
	 var sqlid901165210="DSHomeContSql901165210";
var mySql901165210=new SqlClass();
mySql901165210.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901165210.setSqlId(sqlid901165210);//ָ��ʹ�õ�Sql��id
mySql901165210.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql901165210.getString();
	 
//	 strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','2','1') from dual ";
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('AppntSumHealthAmnt').value=arr2[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
	}
	
	 var sqlid901165921="DSHomeContSql901165921";
var mySql901165921=new SqlClass();
mySql901165921.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901165921.setSqlId(sqlid901165921);//ָ��ʹ�õ�Sql��id
mySql901165921.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql901165921.getString();
	 
//	 strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','3','1') from dual ";
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('AppntMedicalAmnt').value=arr3[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
	var sqlid901170020="DSHomeContSql901170020";
var mySql901170020=new SqlClass();
mySql901170020.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901170020.setSqlId(sqlid901170020);//ָ��ʹ�õ�Sql��id
mySql901170020.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql901170020.getString();

	
//	strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','4','1') from dual ";
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('AppntAccidentAmnt').value=arr4[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
	}
	
    document.all('AppntSumAmnt').value=tSumAmnt;
    
    var sqlid901170224="DSHomeContSql901170224";
var mySql901170224=new SqlClass();
mySql901170224.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901170224.setSqlId(sqlid901170224);//ָ��ʹ�õ�Sql��id
mySql901170224.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
mySql901170224.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
mySql901170224.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql901170224.getString();

    
    //�ۼƱ��� �����������Ͳ����ڽ�����   
//    strSQL =  "SELECT decode(sum(a_Prem),'','x',sum(a_Prem)) FROM"
//          + "(select (case"
//          + " when s_PayIntv = '1' then"
//          + " s_Prem*0.09"
//          + " when s_PayIntv = '3' then"
//          + " s_Prem*0.27"
//          + " when s_PayIntv = '6' then"
//          + " s_Prem*0.52"
//          + " when s_PayIntv = '12' then"
//          + " s_Prem"
//          + " end) a_Prem"          
//          + " FROM (select distinct payintv as s_PayIntv,"
//          + " sum(prem) as s_Prem"
//          + " from lcpol c where polno in(select polno "
//	          + " from lcpol a"
//	          + " where a.insuredno = '"+document.all('AppntNo').value+"'"
//	          + " or (a.appntno = '"+document.all('AppntNo').value+"' and a.riskcode in ('00115000','00115001'))"
//	          + " union"
//	          + " select b.polno"
//	          + " from lcinsuredrelated b"
//	          + " where b.customerno = '"+document.all('AppntNo').value+"')"
//          + " and c.payintv in ('1', '3','6','12')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'" 
//          + " and not exists (select 'X'"
//	          + " from lccont"
//	          + " where ContNo = c.contno"
//	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by payintv))";
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	      // alert(arr5[0][0]);  
	      if(arr5[0][0]!='x')  
	          document.all('SumPrem').value=arr5[0][0];
	    }  
	    
    //���ư�ť����
    ctrlButtonDisabled(ContNo);
    document.all('Button12').disabled='true';
    document.all('indButton2').disabled='true';
	
  return true;
}

/*********************************************************************
 *  ��ʼ���Ƿ��ϱ��˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initSendTrace()
{
	var tSql = "";

	var sqlid901170618="DSHomeContSql901170618";
var mySql901170618=new SqlClass();
mySql901170618.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901170618.setSqlId(sqlid901170618);//ָ��ʹ�õ�Sql��id
mySql901170618.addSubPara(ContNo);//ָ������Ĳ���
mySql901170618.addSubPara(ContNo);//ָ������Ĳ���
tSql=mySql901170618.getString();
	
//	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
//					+ " and otherno = '"+ContNo+"'"
//					+ " and othernotype = '1'"
//					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+ContNo+"')" 
//					;
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {
		document.all('SendFlag').value =arrSelected[0][0];
		document.all('SendUWFlag').value =arrSelected[0][1];
		document.all('SendUWIdea').value =arrSelected[0][2];
		DivLCSendTrance.style.display="";
	}
    
    divUWSave.style.display = "";
    divUWAgree.style.display = "none";
    
   
}

/*********************************************************************
 *  ��֪����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ImpartToICD()
{
  var cContNo = fm.ContNo.value;//PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);
  var PrtNo = fm.PrtNo.value;
  
  if (cContNo!="" && PrtNo!="") {
  	window.open("../uw/ImaprtToICDMain.jsp?ContNo="+cContNo+"&PrtNo="+PrtNo, "window1");
  }
  else {
  	alert("����ѡ�񱣵�!");
  }	
}
//�������֪ͨ��
function SendIssue()
{
  cContNo = fm.ContNo.value;
  
  if (cContNo != "")
  {  	
	//manuchk();
	 var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
     showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	  var cMissionID =fm.MissionID.value; 

	  var cPrtNo = fm.PrtNo.value;

	  fm.PrtNoHide.value = cPrtNo ;
	  
	  var sqlid901170738="DSHomeContSql901170738";
var mySql901170738=new SqlClass();
mySql901170738.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901170738.setSqlId(sqlid901170738);//ָ��ʹ�õ�Sql��id
mySql901170738.addSubPara(cContNo);//ָ������Ĳ���
strsql=mySql901170738.getString();

	  
//	  strsql = "select * from lcissuepol where contno = '" +cContNo+ "' and backobjtype = '3' and (state = '0' or state is null)";
		 				 
	  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µ������֪ͨ��");
        fm.SubNoticeMissionID.value = "";
        return ;
    }
    
    var sqlid901170842="DSHomeContSql901170842";
var mySql901170842=new SqlClass();
mySql901170842.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901170842.setSqlId(sqlid901170842);//ָ��ʹ�õ�Sql��id
mySql901170842.addSubPara(cContNo);//ָ������Ĳ���
mySql901170842.addSubPara(cPrtNo);//ָ������Ĳ���
strsql=mySql901170842.getString();

    
//    strsql = "select submissionid from lwmission where missionprop2 = '" +cContNo+ "'and missionprop1 = '"+cPrtNo+"' and activityid = '0000001022'";
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1); 
    
        if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µ������֪ͨ��");
        fm.SubNoticeMissionID.value = "";
        return ;
    }
    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    fm.action = 'UWManuSendIssueCHK.jsp';
    fm.submit(); 
  }
  else
  {  	
  	alert("����ѡ�񱣵�!");
  }
}
//���������
function QuestBack()
{
		cContNo = fm.ContNo.value;  //��������

  if (cContNo != "")
  {	
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;	
	window.open("./UWManuQuestQMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2");
  }
  else
  {  	
  	alert("����ѡ�񱣵�!");  	
  }
}

/*********************************************************************
 *  ��ʾ�˱����𡢺˱�ʦ
 *  add by xutao 2005-04-13
 *  
 *********************************************************************
 */
function showUpDIV(){

    if (fm.uwUpReport.value == '1' || fm.uwUpReport.value == '3') 
    {
        //��ʾ
        //divUWup.style.display="";

    }
    else if (fm.uwUpReport.value == '2' || fm.uwUpReport.value == '4') 
    {  
    }

}
/*********************************************************************
 *  ��ʾ���շ���Ϣ
 *  add by heyq 2005-04-13
 *  
 *********************************************************************
 */
function showTempFee()
{
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+fm.PrtNo.value+"&AppntNo="+document.all('AppntNo').value+"&AppntName="+document.all('AppntName').value,"window11");
}

function SendAllNotice()
{

	window.open("./SendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1");
}

//��ѯ�����
function queryHealthReportResult(){
	var tContNo = fm.ContNo.value;
	var tFlag = "2";

 window.open("./UWManuHealthQMain.jsp?ContNo="+tContNo+"&Flag="+tFlag,"window1");
}


//��ѯ�������
function queryRReportResult(){
	
	var tContNo = fm.ContNo.value;
	var tPrtNo = fm.PrtNo.value;
	var tFlag = "1";

 window.open("./RReportQueryMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&Flag="+tFlag,"window1");
  
}

//�����ۼ�
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1");
}

//�ѳб���ѯ
function queryProposal(){
	
	window.open("./ProposalQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1");
}


//δ�б���ѯ
function queryNotProposal(){
	
	window.open("./NotProposalQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1");
}

//���������ѯ
function queryClaim(){
	
	window.open("./ClaimQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1");
}

//������ȫ��ѯ
function queryEdor(){
	
	window.open("./EdorQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1");
}

function UWUpReport()
{
	window.open("./UWManuUpReportMain.jsp?ContNo="+document.all('ContNo').value,"window1");
}


//����������ѯ
function OperationQuery()
{
	    var ContNo = fm.ContNo.value;
	    var PrtNo = fm.ContNo.value;
		
		if (PrtNo == "" || ContNo == "") return;
			
	window.open("../sys/RecordQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&ContType=1");	
} 

//�˱���ѯ
function UWQuery()
{
	var PrtNo = fm.ContNo.value;
			
	if (PrtNo == "")
	{
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	}
	window.open("../uw/UWQueryMain.jsp?ContNo="+PrtNo+"&ContType=1");											
}

//������ϲ�ѯ
function showBeforeHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;


	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1");

showInfo.close();

}

//������֪��ѯ
function queryHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1");
}

//�����˽�����֪��ѯ
function queryHealthImpart2(){
	
	if(document.all('InsuredNo').value==""){
	  alert("��ѡ�񱻱��ˣ�");
	  return;	
	}

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}  

//�����˲�ѯ�����
function queryHealthReportResult2(){
	if(document.all('InsuredNo').value==""){
	  alert("��ѡ�񱻱��ˣ�");
	  return;	
	}
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  var cContNo=fm.ContNo.value;
  var cInsuredNo = fm.InsuredNo.value;
 
 
	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1");
 
showInfo.close();	
}

//�����˱����ۼ�
function amntAccumulate2(){
	if(document.all('InsuredNo').value==""){
	  alert("��ѡ�񱻱��ˣ�");
	  return;	
	}
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//�������ѳб���ѯ
function queryProposal2(){
	if(document.all('InsuredNo').value==""){
	  alert("��ѡ�񱻱��ˣ�");
	  return;	
	}
	
	window.open("./ProposalQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}


//������δ�б���ѯ
function queryNotProposal2(){
	if(document.all('InsuredNo').value==""){
	  alert("��ѡ�񱻱��ˣ�");
	  return;	
	}
	
	window.open("./NotProposalQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//�����˼��������ѯ
function queryClaim2(){
	if(document.all('InsuredNo').value==""){
	  alert("��ѡ�񱻱��ˣ�");
	  return;	
	}
	
	window.open("./ClaimQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//�����˼�����ȫ��ѯ
function queryEdor2(){
	if(document.all('InsuredNo').value==""){
	  alert("��ѡ�񱻱��ˣ�");
	  return;	
	}
	
	window.open("./EdorQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}



//�����˹��˱����水ť��������ʾ
function ctrlButtonDisabled(tContNo){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;

  arrButtonAndSQL[0] = new Array;
  arrButtonAndSQL[0][0] = "Button1";
  arrButtonAndSQL[0][1] = "Ͷ������ϸ��ѯ";
  arrButtonAndSQL[0][2] = "";

  arrButtonAndSQL[1] = new Array;
  arrButtonAndSQL[1][0] = "Button2";
  arrButtonAndSQL[1][1] = "Ӱ�����ϲ�ѯ";
  
  var sqlid901171102="DSHomeContSql901171102";
var mySql901171102=new SqlClass();
mySql901171102.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901171102.setSqlId(sqlid901171102);//ָ��ʹ�õ�Sql��id
mySql901171102.addSubPara(tContNo);//ָ������Ĳ���
var strSQL1=mySql901171102.getString();
  
  arrButtonAndSQL[1][2] = strSQL1;

  arrButtonAndSQL[2] = new Array;
  arrButtonAndSQL[2][0] = "Button3";
  arrButtonAndSQL[2][1] = "����������ѯ";
  arrButtonAndSQL[2][2] = "";

  arrButtonAndSQL[3] = new Array;
  arrButtonAndSQL[3][0] = "Button4";
  arrButtonAndSQL[3][1] = "�˱����۲�ѯ";
  
  var sqlid901171251="DSHomeContSql901171251";
var mySql901171251=new SqlClass();
mySql901171251.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901171251.setSqlId(sqlid901171251);//ָ��ʹ�õ�Sql��id
mySql901171251.addSubPara(tContNo);//ָ������Ĳ���
mySql901171251.addSubPara(tContNo);//ָ������Ĳ���
var strSQL3=mySql901171251.getString();
  
  arrButtonAndSQL[3][2] = strSQL3;

  arrButtonAndSQL[4] = new Array;
  arrButtonAndSQL[4][0] = "Button5";
  arrButtonAndSQL[4][1] = "Ͷ���˽�����֪��ѯ";
  
  var sqlid901171434="DSHomeContSql901171434";
var mySql901171434=new SqlClass();
mySql901171434.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901171434.setSqlId(sqlid901171434);//ָ��ʹ�õ�Sql��id
mySql901171434.addSubPara(tContNo);//ָ������Ĳ���
mySql901171434.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
var strSQL4=mySql901171434.getString();
  
  arrButtonAndSQL[4][2] = strSQL4;
  
  /*arrButtonAndSQL[5] = new Array;
  arrButtonAndSQL[5][0] = "Button6";
  arrButtonAndSQL[5][1] = "Ͷ����������ϲ�ѯ";
  arrButtonAndSQL[5][2] = "select * from lcpenotice where customerno in (select appntno from lcappnt where contno='"+tContNo+"')";

  arrButtonAndSQL[6] = new Array;
  arrButtonAndSQL[6][0] = "Button7";
  arrButtonAndSQL[6][1] = "Ͷ���˱����ۼ�";
 // arrButtonAndSQL[6][2] = "select 1 from lcpol a where a.polno in (select i.polno from lcpol i where i.InsuredNo = (select appntno from lcappnt where contno = '"+tContNo+"') or (i.appntno = (select appntno from lcappnt where contno = '"+tContNo+"') and i.riskcode in ('00115000')) union select polno from lcinsuredrelated where lcinsuredrelated.customerno = (select appntno from lcappnt where contno = '"+tContNo+"')) and a.uwflag not in ('1', '2', 'a') and not exists (select 'X' from lccont where ContNo = a.contno and uwflag in ('1', '2', 'a')) and rownum = 1";
  arrButtonAndSQL[6][2] = "select 1 from lcpol a where a.polno in (select i.polno from lcpol i where i.InsuredNo = (select appntno from lcappnt where contno = '"+tContNo+"') or (i.appntno = (select appntno from lcappnt where contno = '"+tContNo+"') and i.riskcode in ('121301'))) and a.uwflag not in ('1', '2', 'a') and not exists (select 'X' from lccont where ContNo = a.contno and uwflag in ('1', '2', 'a')) ";

  arrButtonAndSQL[7] = new Array;
  arrButtonAndSQL[7][0] = "Button8";
  arrButtonAndSQL[7][1] = "Ͷ�����ѳб�������ѯ";
  arrButtonAndSQL[7][2] = "select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('1', '4') and a.salechnl in ('1', '3', '01', '03') and b.insuredno in (select appntno from lcappnt where contno = '"+tContNo+"')";

  arrButtonAndSQL[8] = new Array;
  arrButtonAndSQL[8][0] = "Button9";
  arrButtonAndSQL[8][1] = "Ͷ����δ�б�������ѯ";
  arrButtonAndSQL[8][2] = "select * from lccont a, lcinsured b where a.salechnl in ('1', '3', '01', '03') and a.contno = b.contno   and a.appflag = '0' and b.insuredno in (select appntno from lcappnt where contno='"+tContNo+"')";

  arrButtonAndSQL[9] = new Array;
  arrButtonAndSQL[9][0] = "Button10";
  arrButtonAndSQL[9][1] = "Ͷ���˼�����ȫ��ѯ";
  arrButtonAndSQL[9][2] = "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno = (select appntno from lcappnt where contno='"+tContNo+"'))";

  arrButtonAndSQL[10] = new Array;
  arrButtonAndSQL[10][0] = "Button11";
  arrButtonAndSQL[10][1] = "Ͷ���˼��������ѯ";
  arrButtonAndSQL[10][2] = "select 1 from llregister a, llcase b where a.rgtno = b.caseno and b.CustomerNo in (select appntno from lcappnt where contno='"+tContNo+"') union select 1 from llreport a, llsubreport b, ldperson c where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' and b.CustomerNo in (select appntno from lcappnt where contno='"+tContNo+"')";

  arrButtonAndSQL[5] = new Array;
  arrButtonAndSQL[5][0] = "Button12";
  arrButtonAndSQL[5][1] = "�����˽�����֪��ѯ";
  arrButtonAndSQL[5][2] = "select * from dual where 1=2 ";

  arrButtonAndSQL[12] = new Array;
  arrButtonAndSQL[12][0] = "Button13";
  arrButtonAndSQL[12][1] = "������������ϲ�ѯ";
  arrButtonAndSQL[12][2] = "select * from lcpenotice where customerno in (select insuredno from lcinsured where contno='"+tContNo+"') ";

  arrButtonAndSQL[13] = new Array;
  arrButtonAndSQL[13][0] = "Button14";
  arrButtonAndSQL[13][1] = "�����˱����ۼ���ʾ��Ϣ";
  arrButtonAndSQL[13][2] = "";

  arrButtonAndSQL[14] = new Array;
  arrButtonAndSQL[14][0] = "Button15";
  arrButtonAndSQL[14][1] = "�������ѳб�������ѯ";
  arrButtonAndSQL[14][2] = "select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('1', '4') and a.salechnl in ('1', '3', '01', '03') and b.insuredno in (select insuredno from lcinsured where contno = '"+tContNo+"')";

  arrButtonAndSQL[15] = new Array;
  arrButtonAndSQL[15][0] = "Button16";
  arrButtonAndSQL[15][1] = "������δ�б�Ͷ������ѯ";
  arrButtonAndSQL[15][2] = "";

  arrButtonAndSQL[16] = new Array;
  arrButtonAndSQL[16][0] = "Button17";
  arrButtonAndSQL[16][1] = "�����˼�����ȫ��Ϣ��ѯ";
  arrButtonAndSQL[16][2] = "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno in (select insuredno from lcinsured where contno='"+tContNo+"'))";

  arrButtonAndSQL[17] = new Array;
  arrButtonAndSQL[17][0] = "Button18";
  arrButtonAndSQL[17][1] = "�����˼���������Ϣ��ѯ";
  arrButtonAndSQL[17][2] = "select 1 from llregister a, llcase b where a.rgtno = b.caseno and b.CustomerNo in (select insuredno from lcinsured where contno='"+tContNo+"') union select 1 from llreport a, llsubreport b, ldperson c where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' and b.CustomerNo in (select insuredno from lcinsured where contno='"+tContNo+"')";
*/
  arrButtonAndSQL[5] = new Array;
  arrButtonAndSQL[5][0] = "Button19";
  arrButtonAndSQL[5][1] = "Ӱ����������";
  
  var sqlid901171533="DSHomeContSql901171533";
var mySql901171533=new SqlClass();
mySql901171533.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901171533.setSqlId(sqlid901171533);//ָ��ʹ�õ�Sql��id
mySql901171533.addSubPara(tContNo);//ָ������Ĳ���
var strSQL5=mySql901171533.getString();
  
  arrButtonAndSQL[5][2] = strSQL5;
	
  arrButtonAndSQL[6] = new Array;
  arrButtonAndSQL[6][0] = "uwButton30";
  arrButtonAndSQL[6][1] = "Ͷ���˼���Ͷ�����ϲ�ѯ";
  
  var sqlid901171954="DSHomeContSql901171954";
var mySql901171954=new SqlClass();
mySql901171954.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901171954.setSqlId(sqlid901171954);//ָ��ʹ�õ�Sql��id
mySql901171954.addSubPara(tContNo);//ָ������Ĳ���
mySql901171954.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
mySql901171954.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
mySql901171954.addSubPara(tContNo);//ָ������Ĳ���
mySql901171954.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
mySql901171954.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
mySql901171954.addSubPara(tContNo);//ָ������Ĳ���
mySql901171954.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
mySql901171954.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
var strSQL6=mySql901171954.getString();

	arrButtonAndSQL[6][2] = strSQL6;

  
//  arrButtonAndSQL[6][2] = "select 1 from lcpol where contno<> '"+tContNo+"' " 
//						  + " and (appntno = '"+fm.AppntNo.value+"' or insuredno = '"+fm.AppntNo.value+"') "
//						  + " and conttype='1' and appflag in ('1','4') "
//						  + " union "
//						  + " select 1 from lcpol where contno<> '"+tContNo+"' "
//						  + " and (appntno = '"+fm.AppntNo.value+"' or insuredno = '"+fm.AppntNo.value+"') "
//						  + " and conttype='1' and appflag ='0' "
//						  + " union "
//						  + " select 1 from lcpol where contno<> '"+tContNo+"' "
//						  + " and (appntno = '"+fm.AppntNo.value+"' or insuredno = '"+fm.AppntNo.value+"') "
//						  + " and conttype='2' ";

/**
  //Ӱ�����ϲ�ѯ
  tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
  arrResult = easyExecSql(tSQL);
  if(arrResult!=null){
    document.all("Button4").disabled="";
  }
  else{
    document.all("Button4").disabled="true";
  }

  //�������Ϣ��ѯ
  tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
  arrResult = easyExecSql(tSQL);
  if(arrResult!=null){
    document.all("Button4").disabled="";
  }
  else{
    document.all("Button4").disabled="true";
  }
  **/

  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
    	//alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");
      }
    }
    else{
      continue;
    }
  }




}



function showNotePad()
{
	var ActivityID = "0000001140";
    var PrtNo = document.all.PrtNo.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}

	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

function imageDownload(){
    var prtNo = fm.PrtNo.value;

      if (prtNo == "") return;

      window.open("../uw/ImageDownloadMain.jsp?PrtNo="+prtNo);

}

function querytrace(){
		var sqlid901172321="DSHomeContSql901172321";
var mySql901172321=new SqlClass();
mySql901172321.setResourceName("uw.UWUpReportDealEachInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901172321.setSqlId(sqlid901172321);//ָ��ʹ�õ�Sql��id
mySql901172321.addSubPara(document.all.PrtNo.value);//ָ������Ĳ���
var SQL=mySql901172321.getString();

	
//    var SQL = "select t.reportremark,(select d.codename from ldcode d where d.codetype = 'uqreportstate' and d.code = t.reinsuredresult),t.reinsudesc,t.reinsuremark,t.makedate,t.usercode,t.modifydate,t.dealusercode from lcreinsurereporttrace t where t.contno = '"+document.all.PrtNo.value+"' order by t.makedate";
    turnPage.queryModal(SQL, TraceGrid);
   

}

//����Ͷ����Ϣ
function showApp(cindex)
{
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   var cContNo=fm.ContNo.value;
   var cAppntNo = fm.AppntNo.value;

 if (cindex == 1)
	  window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1");
 else  if (cindex == 2)
 	 window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+document.all('InsuredNo').value+"&type=2");
 else
	  window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo);

showInfo.close();

}









