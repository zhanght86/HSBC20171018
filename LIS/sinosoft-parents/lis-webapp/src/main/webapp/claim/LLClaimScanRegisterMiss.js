//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();//for i18n

////�ύ��ɺ���������
//function afterSubmit( FlagStr, content )
//{
//    showInfo.close();
//    if (FlagStr == "Fail" )
//    {
//        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//    }
//    else
//    {
//        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//    }
//            //ֱ��ȡ��������ת����������
//        var i = LLClaimRegisterGrid.getSelNo();
//
//        if (i != '0')
//        {
//            i = i - 1;
//           	fm.Clmno.value = LLClaimRegisterGrid.getRowColData(i, 1);
//           	var tMissionID = LLClaimRegisterGrid.getRowColData(i, 8);
//        	var tSubMissionID = LLClaimRegisterGrid.getRowColData(i, 9);
//        	var tActivityID = LLClaimRegisterGrid.getRowColData(i, 10);
//           location.href="../claim/LLClaimScanRegisterAuditInput.jsp?claimNo="+fm.Clmno.value+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;;
//        }
//}

function returnparent()
{
  	var backstr=document.all("ContNo").value;
  	//alert(backstr+"backstr");
  	mSwitch.deleteVar("ContNo");
	mSwitch.addVar("ContNo", "", backstr);
	mSwitch.updateVar("ContNo", "", backstr);
  	location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}




// ��ʼ�����1
// 2013-05-15 ʹ���µ�mulline ����ע��
//function queryGrid()
//{
////for i18n
///*    var strSQL = "";          
//     strSQL = "Select Missionprop1, Missionprop9, Missionprop20, (select username from lduser where usercode=Missionprop11),"
//    	    + " (select bussno From es_doc_relation where docid In (select docid from es_doc_relation where doccode=Lwmission.missionprop1 and BussNoType='15' And Busstype = 'LP' And Subtype = 'CA001') And bussnotype='11'),"
//    	    + " (case (select 1 from LLRegisterIssue where IssueConclusion='01' And rgtno=lwmission.missionprop1) when 1 then 'ͨ��' else 'δͨ��' end),"
//    	    + " (select 1 from LLRegisterIssue where IssueConclusion='01' and rgtno=lwmission.missionprop1 and rownum=1),"
//    	    + " missionid,submissionid,activityid "
//    	    + " From Lwmission Where Processid = '0000000005' And Activityid = '0000005010' and defaultoperator is null"
//    	    + " and not exists (select 1 from LLRegisterIssue where RgtNo=lwmission.missionprop1 and IssueConclusion in ('02','03') and IssueReplyDate is null)"
//    	    + " and Missionprop20 like '" + fm.ManageCom.value + "%%'"
//    	    + getWherePart( 'missionprop1' ,'RptNo')
//    	    + getWherePart( 'Missionprop9' ,'ScanDate')
//    	    + getWherePart( 'Missionprop20' ,'ScanCom')
//    	    + getWherePart( 'Missionprop11' ,'Scaner')
//*/    
//	 mySql=new SqlClass();	 
//	 mySql.setResourceName("claim.LLClaimScanRegisterMissInputSql");
//	 mySql.setSqlId("LLClaimScanRegisterMissSql1");
//	 mySql.addSubPara(fm.ManageCom.value);
//	 mySql.addSubPara(fm.RptNo.value);
//	 mySql.addSubPara(fm.ScanDate.value);
//	 mySql.addSubPara(fm.ScanCom.value);
//	 mySql.addSubPara(fm.Scaner.value);	  
//       
//	if (!(fm.ApplyNo.value == "" || fm.ApplyNo.value == null))
//	{	//for i18n
//		//strSQL = strSQL + "and (select bussno From es_doc_relation where docid In (select docid from es_doc_relation where doccode=Lwmission.missionprop1 and BussNoType='15' And Busstype = 'LP' And Subtype = 'CA001') And bussnotype='11') = '" + fm.ApplyNo.value +"'";
//		 mySql=new SqlClass();	 
//		 mySql.setResourceName("claim.LLClaimScanRegisterMissInputSql");
//		 mySql.setSqlId("LLClaimScanRegisterMissSql2");
//		 mySql.addSubPara(fm.ManageCom.value);
//		 mySql.addSubPara(fm.RptNo.value);
//		 mySql.addSubPara(fm.ScanDate.value);
//		 mySql.addSubPara(fm.ScanCom.value);
//		 mySql.addSubPara(fm.Scaner.value);	
//		 mySql.addSubPara(fm.ApplyNo.value);	
//	}
//		
////    if (!(fm.CustomerName.value == "" || fm.CustomerName.value == null))
////    {
//// 	   strSQL = strSQL + getWherePart( 'missionprop4' ,'CustomerName','like')
////    }
//    
//      //for i18n���������ֱ�ӵ���sql��
//      //strSQL = strSQL + " order by missionprop1 ";
//     
//     turnPage.queryModal(mySql.getString(),LLClaimRegisterGrid);
//}

// ��ʼ�����2
//function querySelfGrid()
//{
////for i18n
///*
//    var strSQL = "";
//	strSQL="Select missionprop1,'δ����','0',"
//	      + "(Case (Select 1 From Llregisterissue Where Issueconclusion = '01' And Rgtno = Lwmission.Missionprop1 And Rownum=1) When 1 Then 'ͨ��' Else 'δͨ��' End),"
//	      + "(Select 1 From Llregisterissue Where Issueconclusion = '01' And Rgtno = Lwmission.Missionprop1 And Rownum=1),"
//	      + " Missionprop9,Missionprop20,(Select Username From Lduser Where Usercode=MissionProp11),'','',"
//	      + " (select bussno From es_doc_relation where docid In (select docid from es_doc_relation where doccode=lwmission.missionprop1 and BussNoType='15' And Busstype = 'LP' And Subtype = 'CA001') And bussnotype='11'),"
//	      + " missionid,submissionid,activityid,''"
//	      + " From lwmission where Processid = '0000000005' And activityid='0000005010' And Defaultoperator = '" + fm.Operator.value + "'"
//	      + " and not exists (select 1 from LLRegisterIssue where RgtNo=lwmission.missionprop1 and IssueConclusion in ('02','03') and IssueReplyDate is null)"
//  	      + " and Missionprop20 like '" + fm.ManageCom.value + "%%'"
//	      + " Union select missionprop1,'������','1','ͨ��',1,"
//          + "(Select Missionprop9 From lbmission where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1 And Rownum=1),"
//          + "(Select Missionprop20 From lbmission where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1),"
//          + "(Select Username From Lduser Where Usercode In (Select MissionProp11 From lbmission where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1)),"
//          + " MissionProp3,MissionProp4,(select bussno From es_doc_relation where docid In (select docid from es_doc_relation where doccode=lwmission.missionprop1 and BussNoType='15' And Busstype = 'LP' And Subtype = 'CA001') And bussnotype='11'),"
//          + " missionid,submissionid,activityid,MissionProp21 "
//          + " From lwmission where Processid = '0000000005' And activityid='0000005015' And Defaultoperator = '" + fm.Operator.value + "'"
//          + " And Exists (Select 1 From lbmission Where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1)"
//          + " order By missionprop1";
//   */       
//    //prompt("������ʼ�����ѯ",strSQL);
//     mySql=new SqlClass();	 
//	 mySql.setResourceName("claim.LLClaimScanRegisterMissInputSql");
//	 mySql.setSqlId("LLClaimScanRegisterMissSql3");
//	 mySql.addSubPara(fm.Operator.value);
//	 mySql.addSubPara(fm.ManageCom.value);
//	 mySql.addSubPara(fm.Operator.value); 
//	 
//    turnPage2.queryModal(mySql.getString(),SelfLLClaimRegisterGrid);
//    HighlightByRow();
//}

////���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
//function HighlightByRow(){
//    for(var i=0; i<SelfLLClaimRegisterGrid.mulLineCount; i++){
//    	var accepteddate = SelfLLClaimRegisterGrid.getRowColData(i,15); //��������    	
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
//    	   {  
//    		   SelfLLClaimRegisterGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//[����]��ť
//function Apply()
//{
//	var selno = LLClaimRegisterGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��Ҫ���봦��ı�����");
//	      return;
//	}
//
//	fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
//	fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 9);
//	fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 10);
//
//	fm.action = "./LLClaimScanRegisterMissSave.jsp";
//	submitForm(); //�ύ
//}
//
//
//function SelfLLClaimRegisterGridClick()
//{
//	HighlightByRow();
//	var selno = SelfLLClaimRegisterGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ������İ�����");
//	      return;
//	}
//	fm.Clmno.value = SelfLLClaimRegisterGrid.getRowColData(selno, 1);
//	var RegisterExist = SelfLLClaimRegisterGrid.getRowColData(selno, 3);
//	var IssueConclusion = SelfLLClaimRegisterGrid.getRowColData(selno, 5);
//	var tMissionID = SelfLLClaimRegisterGrid.getRowColData(selno,12);
//  var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(selno,13);
//  var tActivityID = SelfLLClaimRegisterGrid.getRowColData(selno,14);
//      
//	//for i18n
//	//var strSQL = "select 1 from LLRegisterIssue where RgtNo='"+fm.Clmno.value+"' and IssueConclusion in ('02','03') and IssueReplyDate is null";
//	 mySql=new SqlClass();	 
//	 mySql.setResourceName("claim.LLClaimScanRegisterMissInputSql");
//	 mySql.setSqlId("LLClaimScanRegisterMissSql4");
//	 mySql.addSubPara(fm.Clmno.value);
//
//	var RegisterIssueExist = easyExecSql(mySql.getString());
//	
//	if(RegisterIssueExist != null && RegisterIssueExist != "")
//	{
//		alert("�˰�������δ�����Ĳ�����ϻ��������");
//		return false;
//	}
//	
//	if (IssueConclusion == '1')
//	{
//		if(RegisterExist == '0')
//		{
//			window.open("./LLClaimScanRegisterMain.jsp?claimNo="+fm.Clmno.value+"&isNew=0&prtNo="+fm.Clmno.value+"&SubType=CA001&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
//			//location.href="../claim/LLClaimScanRegisterInput.jsp?claimNo="+fm.Clmno.value+"&isNew=0&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
//		}
//		else
//		{
//			window.open("./LLClaimScanRegisterMain.jsp?claimNo="+fm.Clmno.value+"&isNew=1&prtNo="+fm.Clmno.value+"&SubType=CA001&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
//		//	window.open("./LLClaimScanRegisterInput.jsp?claimNo="+fm.Clmno.value+"&isNew=1&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID);
//		//	location.href="../claim/LLClaimScanRegisterInput.jsp?claimNo="+fm.Clmno.value+"&isNew=1&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
//		} 
//	}
//	else
//	{
////		  var strUrl="LLClaimScanRegisterAuditMain.jsp?claimNo="+fm.Clmno.value+"&CustomerNo="+CustomerNo;
////		  window.open(strUrl,"������ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//		  
//		  location.href="../claim/LLClaimScanRegisterAuditInput.jsp?claimNo="+fm.Clmno.value+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;;
//		  //		  var strUrl="LLClaimScanRegisterInput.jsp?claimNo="+document.all('Clmno').value+"&IssueConclusion="+IssueConclusion;
////		  window.open(strUrl,"������ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//		  
//	}
//}




//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//modify by lzf 213-05-15
function Apply()
{
	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦��ı�����");
	      return;
	}

	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 14);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 16);

	fm.action = "./LLClaimScanRegisterMissSave.jsp";
	submitForm(); //�ύ
}


function SelfLLClaimRegisterGridClick1()
{
	HighlightByRow();
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ������İ�����");
	      return;
	}
	fm.Clmno.value = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var RegisterExist = PrivateWorkPoolGrid.getRowColData(selno, 17);//�Ƿ�������־
	var IssueConclusion = PrivateWorkPoolGrid.getRowColData(selno, 16);//������۱�־
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno,18);
  var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno,19);
  var tActivityID = PrivateWorkPoolGrid.getRowColData(selno,21);
      
	//for i18n
	//var strSQL = "select 1 from LLRegisterIssue where RgtNo='"+fm.Clmno.value+"' and IssueConclusion in ('02','03') and IssueReplyDate is null";
	 mySql=new SqlClass();	 
	 mySql.setResourceName("claim.LLClaimScanRegisterMissInputSql");
	 mySql.setSqlId("LLClaimScanRegisterMissSql4");
	 mySql.addSubPara(fm.Clmno.value);

	var RegisterIssueExist = easyExecSql(mySql.getString());
	
	if(RegisterIssueExist != null && RegisterIssueExist != "")
	{
		alert("�˰�������δ�����Ĳ�����ϻ��������");
		return false;
	}
	
	if (IssueConclusion == '1')
	{
		if(RegisterExist == '0')
		{
			window.open("./LLClaimScanRegisterMain.jsp?claimNo="+fm.Clmno.value+"&isNew=0&prtNo="+fm.Clmno.value+"&SubType=CA001&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		}
		else
		{
			window.open("./LLClaimScanRegisterMain.jsp?claimNo="+fm.Clmno.value+"&isNew=1&prtNo="+fm.Clmno.value+"&SubType=CA001&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		} 
	}
	else
	{
		  location.href="../claim/LLClaimScanRegisterAuditInput.jsp?claimNo="+fm.Clmno.value+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;;
	}
}

//���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightByRow(){
    for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
    	var accepteddate = PrivateWorkPoolGrid.getRowColData(i,4); //��������    	
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {  
    		   PrivateWorkPoolGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
            //ֱ��ȡ��������ת����������
        var i = PublicWorkPoolGrid.getSelNo();

        if (i != '0')
        {
            i = i - 1;
           	fm.Clmno.value = PublicWorkPoolGrid.getRowColData(i, 1);
           	var tMissionID = PublicWorkPoolGrid.getRowColData(i, 13);
        	var tSubMissionID = PublicWorkPoolGrid.getRowColData(i, 14);
        	var tActivityID = PublicWorkPoolGrid.getRowColData(i, 16);
           location.href="../claim/LLClaimScanRegisterAuditInput.jsp?claimNo="+fm.Clmno.value+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;;
        }
        jQuery("#privateSearch").click();
        jQuery("#publicSearch").click();
}

//��ɨҳ��ĵ����������ҳ��
function SelfLLClaimRegisterGridClick()
{
	var selno = ScanerClaimPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ������İ�����");
	      return;
	}
	fm.Clmno.value = ScanerClaimPoolGrid.getRowColData(selno, 1);
	var tMissionID = ScanerClaimPoolGrid.getRowColData(selno,17);
    var tSubMissionID = ScanerClaimPoolGrid.getRowColData(selno,18);
    var tActivityID = ScanerClaimPoolGrid.getRowColData(selno,20);  

    window.open("./LLClaimScanRegisterMain.jsp?claimNo="+fm.Clmno.value+"&isNew=1&prtNo="+fm.Clmno.value+"&SubType=CA001&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");		
}


//end by lzf