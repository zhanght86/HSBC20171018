var strOperate = "like";
var turnPage = new turnPageClass();

var turnPage2 = new turnPageClass();
var showInfo;
var mSwitch = parent.VD.gVSwitch;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

function GoToAppnt(){
	var i = 0;
    var checkFlag = 0;
  	var state = "0";
  
  	var selno=OPolGrid.getSelNo();//alert("selno:"+selno)
	if (selno ==0||selno==null)
	{
	      alert("��ѡ��һ����¼��");
	      return;
	}
  
  	//if (checkFlag) { 
    	var	ContNo = OPolGrid.getRowColData(selno - 1, 1); 	
    	var prtNo = OPolGrid.getRowColData(selno - 1, 2); 
    	var RiskCode =OPolGrid.getRowColData(selno-1,3);
    	var AppntName = OPolGrid.getRowColData(selno - 1, 4);
    	var InsuredName = OPolGrid.getRowColData(selno - 1, 5);
    	var ScanMakeDate = OPolGrid.getRowColData(selno - 1, 7);
    	var AppntNo = OPolGrid.getRowColData(selno - 1, 8);
    	var InsuredNo = OPolGrid.getRowColData(selno - 1, 9);
	  	var NoType = type;
	  
    	var strReturn="1";
    	//sFeatures = "";
    	window.open("./CustomerMergeMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&AppntName="+AppntName+"&AppntNo="+AppntNo+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&CustomerType=A","",sFeatures);        
    //}
}

function GoToInsured(){
	var i = 0;
    var checkFlag = 0;
  	var state = "0";
  
  	var selno=OPolGrid.getSelNo();//alert("selno:"+selno)
	if (selno ==0||selno==null)
	{
	      alert("��ѡ��һ����¼��");
	      return;
	}
  
  	//if (checkFlag) { 
    	var	ContNo = OPolGrid.getRowColData(selno - 1, 1); 	
    	var prtNo = OPolGrid.getRowColData(selno - 1, 2); 
    	var RiskCode =OPolGrid.getRowColData(selno-1,3);
    	var AppntName = OPolGrid.getRowColData(selno - 1, 4);
    	var InsuredName = OPolGrid.getRowColData(selno - 1, 5);
    	var ScanMakeDate = OPolGrid.getRowColData(selno - 1, 7);
    	var AppntNo = OPolGrid.getRowColData(selno - 1, 8);
    	var InsuredNo = OPolGrid.getRowColData(selno - 1, 9);
	  	var NoType = type;
	  	//alert("missionid=="+fm.MissionID.value+"&&submissionid=="+fm.SubMissionID.value);
    	var strReturn="1";
    	sFeatures = "";//alert("InsuredNo: "+InsuredNo);
    	window.open("./CustomerMergeMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&InsuredNo="+InsuredNo+"&InsuredName="+InsuredName+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&CustomerType=I","",sFeatures);        
    //}
}
//������봥���ĺ���
/*function ApplyUW()
{

	var selno = ClientListGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}
	//alert(selno);
	fm.MissionID.value = ClientListGrid.getRowColData(selno, 12);//alert(fm.MissionID.value);
	fm.SubMissionID.value = ClientListGrid.getRowColData(selno, 13);//alert(fm.SubMissionID.value);
	fm.ActivityID.value = ClientListGrid.getRowColData(selno, 14);//alert(fm.ActivityID.value);
	
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
lockScreen('lkscreen');  
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //�ύ
	displayMissionInfo1();
}

function displayMissionInfo1(){
	var selno=ClientListGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}  
	var tMissionID = ClientListGrid.getRowColData(selno, 12);
	var tSubMissionID= ClientListGrid.getRowColData(selno, 13);
	var ActivityID = ClientListGrid.getRowColData(selno, 14);
	var polNo = ClientListGrid.getRowColData(selno, 1);
	var prtNo = ClientListGrid.getRowColData(selno, 2);
	var NoType = "1";

   //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
	
  var cContNo = prtNo;
  var cPrtNo = prtNo;
  //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    var SubType="";
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	//�ж��Ƿ�Ϊ���ʱ�ͨ  ��������ʱ�ͨ BankFlag=3 subtype='TB1003'
    	//���������һ��
    	var CheckSql = "select 1 from lccont where prtno='"+polNo+"' and salechnl='03' and selltype='08'";
    	var arr = easyExecSql(CheckSql);//prompt("",CheckSql);
        if(!arr){
        	BankFlag="1";
        }else {
	    	BankFlag="3";
	    	SubType = "TB1003";
        }
    }else{
    	BankFlag="1";
    }
    
	var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	if(SubType=='')
	{
		SubType = 'TB1001';
	}
	
	//alert(BankFlag);
	easyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=a&prtNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}


//�����ѯ�����ĺ���
function easyQueryClick()
{
	initClientListGrid();//��ʼ�����
	if(trim(fm.ManageCom.value)=="")
	{
		//alert("��½�������룺"+manageCom);
		fm.ManageCom.value=manageCom;
	}
	// ��дSQL���
	var strSQL = "";
		
		strSQL = "select missionprop1,missionprop2,(select max(riskcode) from lcpol where polno =mainpolno and prtno =a.missionprop1),"
				 + " (select appntname from lcappnt where contno = a.missionprop1),"
				 + " (select max(name) from lcinsured where contno = a.missionprop1),"
				 + " (select max(FirstTrialDate) from lccont where contno = a.missionprop1) FirstTrialDate, "
				 + " (select min(makedate) from es_doc_main where doccode=a.missionprop1 and subtype='UA001') a"
				 + ",missionprop7,missionprop8,missionprop9,"
				 + " missionprop10,missionid,SubMissionID,ActivityID from lwmission a where 1=1 "
				 + " and activityid = '0000001404' and processid = '0000000003'"
				 + getWherePart('missionprop2','PrtNo',strOperate)
				 + getWherePart('missionprop3','RiskCode',strOperate)
				 + getWherePart('missionprop9','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','ScanMakeDate',strOperate)
				 + getWherePart('missionprop10','AgentCode',strOperate)
				 //+ " and missionprop9 like '" + comcode + "%%'"  //����Ȩ�޹�������
				 + " and defaultoperator is null "
				 ;
				 
				 if(fm.FirstTrialDate.value != "")
				 {
				 	   strSQL =strSQL+ " and exists(select 1 from lccont where contno = a.missionprop1 and FirstTrialDate = '"+fm.FirstTrialDate.value+"') "
				 }
				 if(fm.ScanMakeDate.value!=null&&fm.ScanMakeDate.value!=""){
				   strSQL =strSQL+" and exists (select 1 from es_doc_main where doccode =a.missionprop1 "
				    			 +getWherePart('makedate','ScanMakeDate',strOperate)
				                 +") order by FirstTrialDate,MissionProp6,(select maketime from es_doc_main where subtype = 'UA001' and doccode = a.missionprop2) ";
				 }else{
				   strSQL =strSQL+" order by FirstTrialDate,MissionProp6,(select maketime from es_doc_main where subtype = 'UA001' and doccode = a.missionprop2) ";
				 }
				 	 //alert(strSQL);
//				 	 prompt("",strSQL);
	
	turnPage.queryModal(strSQL, ClientListGrid);
	return true;
}

function easyQueryClickSelf(){
		initOPolGrid();//��ʼ�����
	if(trim(fm.ManageCom.value)=="")
	{
		//alert("��½�������룺"+manageCom);
		fm.ManageCom.value=manageCom;
	}
	
	var strSQL = "";
		
		strSQL = "select missionprop1,missionprop2,(select max(riskcode) from lcpol where polno =mainpolno and prtno =a.missionprop1),"
				 + " (select appntname from lcappnt where contno = a.missionprop1),"
				 + " (select max(name) from lcinsured where contno = a.missionprop1),"
				 + " (select max(FirstTrialDate) from lccont where contno = a.missionprop1) FirstTrialDate, "
				 + " (select min(makedate) from es_doc_main where doccode=a.missionprop1 and subtype='UA001') a,"
				 + " missionprop7,missionprop8,missionprop9,"
				 + " missionprop10,missionid,SubMissionID,ActivityID from lwmission a where 1=1 "
				 + " and activityid = '0000001404' and processid = '0000000003'"
				 + " and missionprop9 like '" + comcode + "%%'"  //����Ȩ�޹�������
				 + getWherePart('missionprop2','PrtNo1',strOperate)
				 + getWherePart('missionprop3','RiskCode1',strOperate)
				 + getWherePart('missionprop9','ManageCom1',strOperate)
				 //+ getWherePart('missionprop6','ScanMakeDate1',strOperate)
				 + getWherePart('missionprop10','AgentCode1',strOperate)

				 + " and defaultoperator='"+operator+"'"
				 ;
				 
				 if(fm.FirstTrialDate1.value != "")
				 {
				 	strSQL =strSQL+ " and exists(select 1 from lccont where contno = a.missionprop1 and FirstTrialDate = '"+fm.FirstTrialDate1.value+"') "
				 }
				 if(fm.ScanMakeDate1.value!=null&&fm.ScanMakeDate1.value!=""){
				   strSQL =strSQL+" and exists (select 1 from es_doc_main where doccode =a.missionprop1 "
				    			 +getWherePart('makedate','ScanMakeDate1',strOperate)
				                 +") order by FirstTrialDate,MissionProp6,(select maketime from es_doc_main where subtype = 'UA001' and doccode = a.missionprop2) ";
				 }else{
				   strSQL =strSQL+"order by FirstTrialDate,MissionProp6,(select maketime from es_doc_main where subtype = 'UA001'  and doccode = a.missionprop2 ) " ;
				 }
				 	 //alert(strSQL);
//		prompt("",strSQL);
	
	turnPage2.queryModal(strSQL, OPolGrid);
	return true;
}
function afterSubmit( FlagStr, content )
{
//alert("lixang");
	//alert(133);
	unlockScreen('lkscreen');  
	showInfo.close();
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		easyQueryClick();// ˢ��ҳ��
		easyQueryClickSelf();// ˢ��ҳ��
	}
	else
	{
		easyQueryClick();// ˢ��ҳ��
		easyQueryClickSelf();// ˢ��ҳ��
	}

}

function afterSubmit2( FlagStr, content )
{
		showInfo.close();
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		easyQueryClick();// ˢ��ҳ��
		easyQueryClickSelf();// ˢ��ҳ��
		easyQueryClick();// ˢ��ҳ��
		easyQueryClickSelf();// ˢ��ҳ��
}

function UnionConfirm(){

	var selno=OPolGrid.getSelNo();//alert("selno:"+selno)
	if (selno ==0||selno==null)
	{
	      alert("��ѡ��һ����¼��");
	      return;
	}
	//for (i=0; i<OPolGrid.mulLineCount; i++) {
    //	if (OPolGrid.getSelNo(i)) { 
   //   		selno = OPolGrid.getSelNo();
    //  	break;
    //	}
  	//}
	
	var	ContNo = OPolGrid.getRowColData(selno - 1, 1); 	
    var prtNo = OPolGrid.getRowColData(selno - 1, 2); 
    var RiskCode =OPolGrid.getRowColData(selno-1,3);
    var AppntName = OPolGrid.getRowColData(selno - 1, 4);
    var InsuredName = OPolGrid.getRowColData(selno - 1, 5);
    var ScanMakeDate = OPolGrid.getRowColData(selno - 1, 7);
    var AppntNo = OPolGrid.getRowColData(selno - 1, 8);
    var InsuredNo = OPolGrid.getRowColData(selno - 1, 9);
    var MissionId = OPolGrid.getRowColData(selno - 1, 12);
    var SubMission = OPolGrid.getRowColData(selno - 1, 13);
    var ActivityId = OPolGrid.getRowColData(selno - 1, 14);
    
    var AgentCode;

    var agentSql="select Agentcode from lccont where contno='"+ContNo+"' "
    //+"and appntno='"+AppntNo+"' and insuredno='"+InsuredNo+"'";
    var brr = easyExecSql(agentSql);//prompt("",agentSql);
    if(!brr){
    	alert("��ѯ������ʧ��");
    	return;
    }
    AgentCode=brr[0][0];
    
    fm.AgentCode.value=AgentCode;
	fm.WorkFlowFlag.value="0000001404";
	fm.AppntName.value=AppntName;
	fm.InsuredName.value=InsuredName;
	fm.AppntNo.value=AppntNo;
	fm.ContNo.value=ContNo;
	fm.PrtNo.value=prtNo;
	fm.PrtNo.value=prtNo;
	fm.managecom.value=manageCom;
	fm.MissionID.value=MissionId;
	fm.SubMissionID.value=SubMission;
	fm.ActivityID.value=ActivityId;
    
    //alert(fm.MissionID.value);
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
	fm.action = "./UnionConfirmSave.jsp";
	document.getElementById("fm").submit(); //�ύ
}
*/
/*function displayMissionInfo(){
	var selno=OPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���ͻ��ϲ���Ͷ������");
	      return;
	}  
	
	//fm.MissionID.value = OPolGrid.getRowColData(selno, 11);//alert(fm.MissionID.value);
	//fm.SubMissionID.value = OPolGrid.getRowColData(selno, 12);//alert(fm.SubMissionID.value);
	//fm.ActivityID.value = OPolGrid.getRowColData(selno, 13);//alert(fm.ActivityID.value);



	var tMissionID = OPolGrid.getRowColData(selno, 12);
	var tSubMissionID= OPolGrid.getRowColData(selno, 13);
	var ActivityID = OPolGrid.getRowColData(selno, 14);
	var polNo = OPolGrid.getRowColData(selno, 1);
	var prtNo = OPolGrid.getRowColData(selno, 2);
	var NoType = "1";

   //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
	//alert(mSwitch.getVar("ApprovePolNo"));
//	var strSql1="";
//	strSql1="select case lmriskapp.riskprop"
//		+" when 'I' then '1'"
//		+" when 'G' then '2'"
//		+" when 'Y' then '3'"
//		+" when 'T' then '5'"
//		+" when 'C' then '1'"
//		+" when 'B' then '2'"
//		+" else '1' end"
//		+" from lmriskapp where riskcode in "
//		+"(select riskcode from lcpol where polno = mainpolno and contno = '"+polNo+"')";
//	//prompt("strSql1",strSql1);
//	var arrResult1 = easyExecSql(strSql1);
//	if (arrResult1==null) 
//	{
//	alert("��ѯͶ����������Ϣʧ�ܣ�");
//	return false;
//	}
//    var BankFlag = arrResult1[0][0];//�øñ�־�жϣ����벻ͬ��ҳ��
  
  var cContNo = prtNo;
  var cPrtNo = prtNo;
  //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    var SubType="";
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	//�ж��Ƿ�Ϊ���ʱ�ͨ  ��������ʱ�ͨ BankFlag=3 subtype='TB1003'
    	//���������һ��
    	var CheckSql = "select 1 from lccont where prtno='"+polNo+"' and salechnl='03' and selltype='08'";
    	var arr = easyExecSql(CheckSql);//prompt("",CheckSql);
        if(!arr){
        	BankFlag="1";
        }else {
	    	BankFlag="3";
	    	SubType = "TB1003";
        }
    }else{
    	BankFlag="1";
    }
    
	var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	if(crrResult!=null&&crrResult!=""&&crrResult!="null")
	{
	SubType = crrResult[0][0];
	}
	if(SubType=='')
	{
		SubType = 'TB1001';
	}
	
//	alert("BankFlag = "+BankFlag);
//	alert("SubType = "+SubType);
	easyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=a&prtNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}
*/

// modify by lzf 2013-03-19
function ApplyUW()
{

	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}
	//alert(selno);
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 12);//alert(fm.MissionID.value);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);//alert(fm.SubMissionID.value);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 15);//alert(fm.ActivityID.value);
	
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
	displayMissionInfo1();
}

function displayMissionInfo1(){
	var selno=PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}  
	var tMissionID = PublicWorkPoolGrid.getRowColData(selno, 12);
	var tSubMissionID= PublicWorkPoolGrid.getRowColData(selno, 13);
	var ActivityID = PublicWorkPoolGrid.getRowColData(selno, 15);
	var polNo = PublicWorkPoolGrid.getRowColData(selno, 1);
	var prtNo = PublicWorkPoolGrid.getRowColData(selno, 2);
	var NoType = "1";

   //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
	
  var cContNo = prtNo;
  var cPrtNo = prtNo;
  //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    var SubType="";
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	//�ж��Ƿ�Ϊ���ʱ�ͨ  ��������ʱ�ͨ BankFlag=3 subtype='TB1003'
    	//���������һ��
//    	var CheckSql = "select 1 from lccont where prtno='"+polNo+"' and salechnl='03' and selltype='08'";
    	
    	var sqlid0="CustomerAssociateSql0";
    	var mySql0=new SqlClass();
    	mySql0.setResourceName("app.CustomerAssociateSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
    	mySql0.addSubPara(polNo);//ָ������Ĳ���
    	var CheckSql=mySql0.getString();
    	
    	var arr = easyExecSql(CheckSql);//prompt("",CheckSql);
        if(!arr){
        	BankFlag="1";
        }else {
	    	BankFlag="3";
	    	SubType = "TB1003";
        }
    }else{
    	BankFlag="1";
    }
    
//	var strSql2="select missionprop5 from lbmission where activityid in (select activityid from lwactivity where functionid = '10010002') and missionprop1='"+polNo+"'";
	
	var sqlid1="CustomerAssociateSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.CustomerAssociateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(polNo);//ָ������Ĳ���
	var strSql2=mySql1.getString();
	
	var crrResult = easyExecSql(strSql2);
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	if(SubType=='')
	{
		SubType = 'TB1001';
	}
	
	//alert(BankFlag);
	easyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=a&prtNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}


function afterSubmit( FlagStr, content )
{

	unlockScreen('lkscreen');  
	showInfo.close();
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
		jQuery("#privateSearch").click();// ˢ��ҳ��
		jQuery("#publicSearch").click();// ˢ��ҳ��
	}
	else
	{
		jQuery("#publicSearch").click();// ˢ��ҳ��
		jQuery("#privateSearch").click();// ˢ��ҳ��
	}

}

function afterSubmit2( FlagStr, content )
{
		showInfo.close();
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		jQuery("#privateSearch").click();;// ˢ��ҳ��
		jQuery("#publicSearch").click();// ˢ��ҳ��
		
}

function UnionConfirm(){

	var selno=PrivateWorkPoolGrid.getSelNo();//alert("selno:"+selno)
	if (selno ==0||selno==null)
	{
	      alert("��ѡ��һ����¼��");
	      return;
	}
	var	ContNo = PrivateWorkPoolGrid.getRowColData(selno - 1, 1); 	
    var prtNo = PrivateWorkPoolGrid.getRowColData(selno - 1, 2); 
    var RiskCode =PrivateWorkPoolGrid.getRowColData(selno-1,3);
    var AppntName = PrivateWorkPoolGrid.getRowColData(selno - 1, 4);
    var InsuredName = PrivateWorkPoolGrid.getRowColData(selno - 1, 5);
    var ScanMakeDate = PrivateWorkPoolGrid.getRowColData(selno - 1, 6);
    var AppntNo = PrivateWorkPoolGrid.getRowColData(selno - 1, 7);
    var InsuredNo = PrivateWorkPoolGrid.getRowColData(selno - 1, 8);
    var MissionId = PrivateWorkPoolGrid.getRowColData(selno - 1, 12);
    var SubMission = PrivateWorkPoolGrid.getRowColData(selno - 1, 13);
    var ActivityId = PrivateWorkPoolGrid.getRowColData(selno - 1, 15);
    
    var AgentCode;

//    var agentSql="select Agentcode from lccont where contno='"+ContNo+"' "
    //+"and appntno='"+AppntNo+"' and insuredno='"+InsuredNo+"'";
    
	var sqlid2="CustomerAssociateSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.CustomerAssociateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(ContNo);//ָ������Ĳ���
	var agentSql=mySql2.getString();
    
    var brr = easyExecSql(agentSql);//prompt("",agentSql);
    if(!brr){
    	alert("��ѯ������ʧ��");
    	return;
    }
    AgentCode=brr[0][0];
    
    fm.AgentCode.value=AgentCode;
	fm.WorkFlowFlag.value="0000001404";
	fm.AppntName.value=AppntName;
	fm.InsuredName.value=InsuredName;
	fm.AppntNo.value=AppntNo;
	fm.ContNo.value=ContNo;
	fm.PrtNo.value=prtNo;
	fm.PrtNo.value=prtNo;
	fm.managecom.value=manageCom;
	fm.MissionID.value=MissionId;
	fm.SubMissionID.value=SubMission;
	fm.ActivityID.value=ActivityId;
    
    //alert(fm.MissionID.value);
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./UnionConfirmSave.jsp";
	document.getElementById("fm").submit(); //�ύ
}

function displayMissionInfo(){
	var selno=PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���ͻ��ϲ���Ͷ������");
	      return;
	}  
	
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 12);
	var tSubMissionID= PrivateWorkPoolGrid.getRowColData(selno, 13);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 15);
	var polNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var prtNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
	var NoType = "1";
	//alert("ActivityID---->"+ActivityID);

   //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
  var cContNo = prtNo;
  var cPrtNo = prtNo;
  //ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    var SubType="";
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	//�ж��Ƿ�Ϊ���ʱ�ͨ  ��������ʱ�ͨ BankFlag=3 subtype='TB1003'
    	//���������һ��
//    	var CheckSql = "select 1 from lccont where prtno='"+polNo+"' and salechnl='03' and selltype='08'";
    	
    	var sqlid3="CustomerAssociateSql3";
    	var mySql3=new SqlClass();
    	mySql3.setResourceName("app.CustomerAssociateSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
    	mySql3.addSubPara(polNo);//ָ������Ĳ���
    	var CheckSql=mySql3.getString();
    	
    	var arr = easyExecSql(CheckSql);//prompt("",CheckSql);
        if(!arr){
        	BankFlag="1";
        }else {
	    	BankFlag="3";
	    	SubType = "TB1003";
        }
    }else{
    	BankFlag="1";
    }
    
//	var strSql2="select missionprop5 from lbmission where activityid in (select activityid from lwactivity where functionid = '10010002') and missionprop1='"+polNo+"'";
	
	var sqlid4="CustomerAssociateSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.CustomerAssociateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(polNo);//ָ������Ĳ���
	var strSql2=mySql4.getString();
	
	var crrResult = easyExecSql(strSql2);
	if(crrResult!=null&&crrResult!=""&&crrResult!="null")
	{
	SubType = crrResult[0][0];
	}
	if(SubType=='')
	{
		SubType = 'TB1001';
	}
	
//	alert("BankFlag = "+BankFlag);
//	alert("SubType = "+SubType);
	easyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=a&prtNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}


