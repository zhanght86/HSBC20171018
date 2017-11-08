//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();//for i18n

////提交完成后所作操作
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
//            //直接取得数据跳转到立案界面
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




// 初始化表格1
// 2013-05-15 使用新的mulline 所以注掉
//function queryGrid()
//{
////for i18n
///*    var strSQL = "";          
//     strSQL = "Select Missionprop1, Missionprop9, Missionprop20, (select username from lduser where usercode=Missionprop11),"
//    	    + " (select bussno From es_doc_relation where docid In (select docid from es_doc_relation where doccode=Lwmission.missionprop1 and BussNoType='15' And Busstype = 'LP' And Subtype = 'CA001') And bussnotype='11'),"
//    	    + " (case (select 1 from LLRegisterIssue where IssueConclusion='01' And rgtno=lwmission.missionprop1) when 1 then '通过' else '未通过' end),"
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
//      //for i18n，以下这句分别加到各sql中
//      //strSQL = strSQL + " order by missionprop1 ";
//     
//     turnPage.queryModal(mySql.getString(),LLClaimRegisterGrid);
//}

// 初始化表格2
//function querySelfGrid()
//{
////for i18n
///*
//    var strSQL = "";
//	strSQL="Select missionprop1,'未立案','0',"
//	      + "(Case (Select 1 From Llregisterissue Where Issueconclusion = '01' And Rgtno = Lwmission.Missionprop1 And Rownum=1) When 1 Then '通过' Else '未通过' End),"
//	      + "(Select 1 From Llregisterissue Where Issueconclusion = '01' And Rgtno = Lwmission.Missionprop1 And Rownum=1),"
//	      + " Missionprop9,Missionprop20,(Select Username From Lduser Where Usercode=MissionProp11),'','',"
//	      + " (select bussno From es_doc_relation where docid In (select docid from es_doc_relation where doccode=lwmission.missionprop1 and BussNoType='15' And Busstype = 'LP' And Subtype = 'CA001') And bussnotype='11'),"
//	      + " missionid,submissionid,activityid,''"
//	      + " From lwmission where Processid = '0000000005' And activityid='0000005010' And Defaultoperator = '" + fm.Operator.value + "'"
//	      + " and not exists (select 1 from LLRegisterIssue where RgtNo=lwmission.missionprop1 and IssueConclusion in ('02','03') and IssueReplyDate is null)"
//  	      + " and Missionprop20 like '" + fm.ManageCom.value + "%%'"
//	      + " Union select missionprop1,'已立案','1','通过',1,"
//          + "(Select Missionprop9 From lbmission where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1 And Rownum=1),"
//          + "(Select Missionprop20 From lbmission where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1),"
//          + "(Select Username From Lduser Where Usercode In (Select MissionProp11 From lbmission where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1)),"
//          + " MissionProp3,MissionProp4,(select bussno From es_doc_relation where docid In (select docid from es_doc_relation where doccode=lwmission.missionprop1 and BussNoType='15' And Busstype = 'LP' And Subtype = 'CA001') And bussnotype='11'),"
//          + " missionid,submissionid,activityid,MissionProp21 "
//          + " From lwmission where Processid = '0000000005' And activityid='0000005015' And Defaultoperator = '" + fm.Operator.value + "'"
//          + " And Exists (Select 1 From lbmission Where Processid = '0000000005' And activityid='0000005010' And missionprop1=lwmission.missionprop1)"
//          + " order By missionprop1";
//   */       
//    //prompt("立案初始界面查询",strSQL);
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

////若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
//function HighlightByRow(){
//    for(var i=0; i<SelfLLClaimRegisterGrid.mulLineCount; i++){
//    	var accepteddate = SelfLLClaimRegisterGrid.getRowColData(i,15); //受理日期    	
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
//    	   {  
//    		   SelfLLClaimRegisterGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//[申请]按钮
//function Apply()
//{
//	var selno = LLClaimRegisterGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("请选择要申请处理的报案！");
//	      return;
//	}
//
//	fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
//	fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 9);
//	fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 10);
//
//	fm.action = "./LLClaimScanRegisterMissSave.jsp";
//	submitForm(); //提交
//}
//
//
//function SelfLLClaimRegisterGridClick()
//{
//	HighlightByRow();
//	var selno = SelfLLClaimRegisterGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("请选择申请的案件！");
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
//		alert("此案件存在未回销的补充材料或问题件！");
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
////		  window.open(strUrl,"报案查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//		  
//		  location.href="../claim/LLClaimScanRegisterAuditInput.jsp?claimNo="+fm.Clmno.value+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;;
//		  //		  var strUrl="LLClaimScanRegisterInput.jsp?claimNo="+document.all('Clmno').value+"&IssueConclusion="+IssueConclusion;
////		  window.open(strUrl,"报案查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//		  
//	}
//}




//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//modify by lzf 213-05-15
function Apply()
{
	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请处理的报案！");
	      return;
	}

	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 14);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 16);

	fm.action = "./LLClaimScanRegisterMissSave.jsp";
	submitForm(); //提交
}


function SelfLLClaimRegisterGridClick1()
{
	HighlightByRow();
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择申请的案件！");
	      return;
	}
	fm.Clmno.value = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var RegisterExist = PrivateWorkPoolGrid.getRowColData(selno, 17);//是否立案标志
	var IssueConclusion = PrivateWorkPoolGrid.getRowColData(selno, 16);//初审结论标志
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
		alert("此案件存在未回销的补充材料或问题件！");
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

//若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightByRow(){
    for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
    	var accepteddate = PrivateWorkPoolGrid.getRowColData(i,4); //受理日期    	
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {  
    		   PrivateWorkPoolGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

//提交完成后所作操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
            //直接取得数据跳转到立案界面
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

//有扫页面的点击进入立案页面
function SelfLLClaimRegisterGridClick()
{
	var selno = ScanerClaimPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择申请的案件！");
	      return;
	}
	fm.Clmno.value = ScanerClaimPoolGrid.getRowColData(selno, 1);
	var tMissionID = ScanerClaimPoolGrid.getRowColData(selno,17);
    var tSubMissionID = ScanerClaimPoolGrid.getRowColData(selno,18);
    var tActivityID = ScanerClaimPoolGrid.getRowColData(selno,20);  

    window.open("./LLClaimScanRegisterMain.jsp?claimNo="+fm.Clmno.value+"&isNew=1&prtNo="+fm.Clmno.value+"&SubType=CA001&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");		
}


//end by lzf