//程序名称：LLInqDistribute.js
//程序功能：调查任务分配
//创建日期：2003-03-27 15:10:36
//创建人  ：WHN
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;
var mySql = new SqlClass();
//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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

//查询当前调查人所在机构
function qurycomcode()
{
	/*var strSQL="select usercode,username,comcode from lduser "
			+"where usercode ='"+document.all('Operator').value+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqDistributeInputSql");
	mySql.setSqlId("LLInqDistributeSql1");
	mySql.addSubPara(document.all('Operator').value ); 
	var arr=easyExecSql(mySql.getString());
	document.all('tComCode').value =arr[0][2]+"%";//用于根据机构查询调查人		
//	alert(document.all('tComCode').value);
}

// 查询按钮
//function InqApplyGridQueryClick()
//{	
//	divInqPer.style.display = 'none';
//	/*var strSQL = "";
//	strSQL = "select MISSIONPROP1,MISSIONPROP2,MISSIONPROP3,MISSIONPROP4,MISSIONPROP5, "
//	         +" (select codename from ldcode where codetype='llinitphase' and trim(code)=missionprop6), "
//	         +" MISSIONPROP7,MISSIONPROP8,MissionID,SubMissionID,ActivityID,MISSIONPROP6, "
//	         +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 " 		
//           + " and (activityid = '0000005125' or activityid='0000009125')"
//           + " and processid in ('0000000005','0000000009')"
//           + getWherePart("MISSIONPROP1", "ClmNo")
//           + getWherePart("MISSIONPROP4", "CustomerNo")     
//           + getWherePart("MISSIONPROP5", "CustomerName")                   
//           + " and missionprop8 like '" + document.all('ComCode').value+"%'" //机构编码
//           + " and createoperator='"+document.all('Operator').value+"'"
//           + " order by AcceptedDate,missionprop1";	*/
//    mySql = new SqlClass();
//	mySql.setResourceName("claim.LLInqDistributeInputSql");
//	mySql.setSqlId("LLInqDistributeSql2");
//	mySql.addSubPara(fm.ClmNo.value ); 
//	mySql.addSubPara(fm.CustomerNo.value ); 
//	mySql.addSubPara(fm.CustomerName.value ); 
//	mySql.addSubPara(document.all('ComCode').value ); 
//	mySql.addSubPara(document.all('Operator').value ); 
//    turnPage.pageLineNum=10;    //每页5条
//    turnPage.queryModal(mySql.getString(),InqApplyGrid);//prompt("",strSQL);
//    HighlightByRow();
//}

//若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
//function HighlightByRow(){
//    for(var i=0; i<InqApplyGrid.mulLineCount; i++){
//    	var accepteddate = InqApplyGrid.getRowColData(i,13); //受理日期
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
//    	   {
//    		   InqApplyGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}


//[表格单选钮响应函数]
//function InqApplyGridSelClick()
//{
//  	var i = InqApplyGrid.getSelNo()-1;
//	fm.tClmNo.value = InqApplyGrid.getRowColData(i,1);
//	fm.tInqNo.value = InqApplyGrid.getRowColData(i,2); 
//	fm.tCustomerNo.value = InqApplyGrid.getRowColData(i,4); 
//	fm.tCustomerName.value = InqApplyGrid.getRowColData(i,5); 
//	fm.tInitDept.value = InqApplyGrid.getRowColData(i,8);    
//	fm.MissionID.value = InqApplyGrid.getRowColData(i,9);    
//	fm.SubMissionID.value = InqApplyGrid.getRowColData(i,10);    
//	fm.ActivityID.value = InqApplyGrid.getRowColData(i,11);            
//	divInqPer.style.display = '';
//	
//	//2009-04-22 zhangzheng
//	if(fm.ActivityID.value=='0000009125')
//	{
//		fm.claimUserTable.value='llgrpclaimuser';
//	}
//	else
//	{
//		fm.claimUserTable.value='llclaimuser';
//	}
//	
//	//调查内容信息的查询 2006-01-13 ZHaoRx
//	fm.SavedCustomerName.value = "";
//	fm.SavedBatNo.value        = "";
//	fm.SavedMoreInq.value      = "";
////	fm.SavedVIPFlag.value      = "";
//	fm.SavedInqReason.value    = "";
//	fm.SavedInqOrg.value       = "";
//	fm.SavedInqItem.value      = "";
//	fm.SavedInqDesc.value      = "";
//	/*var tInqSavedSQL = " select CustomerName,BatNo,"
//	                 + " case LocFlag when '0' then '否' when '1' then '是' else '' end,"
//	                 + " case VIPFlag when '0' then '否' when '1' then '是' else '' end,"
//	                 + " InqRCode,InqDept,InqItem,InqDesc from llinqapply "
//	                 + " where clmno='"+fm.tClmNo.value+"' and inqno='"+fm.tInqNo.value+"'";*/
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLInqDistributeInputSql");
//	mySql.setSqlId("LLInqDistributeSql3");
//	mySql.addSubPara(fm.tClmNo.value ); 
//	mySql.addSubPara(fm.tInqNo.value ); 
//	//prompt("调查内容信息的查询SQL",tInqSavedSQL);                 
//	var tSavedRuselt = new Array;
//	tSavedRuselt = easyExecSql(mySql.getString());
//	fm.SavedCustomerName.value = tSavedRuselt[0][0];
//	fm.SavedBatNo.value        = tSavedRuselt[0][1];
//	fm.SavedMoreInq.value      = tSavedRuselt[0][2];
////	fm.SavedVIPFlag.value      = tSavedRuselt[0][3];
//	fm.SavedInqReason.value    = tSavedRuselt[0][4];
//	fm.SavedInqOrg.value       = tSavedRuselt[0][5];  
//	fm.SavedInqItem.value      = tSavedRuselt[0][6]; 
//	fm.SavedInqDesc.value      = tSavedRuselt[0][7]; 
//	showOneCodeName('llinqreason','SavedInqReason','SavedInqReasonName');   
//	showOneCodeName('stati','SavedInqOrg','SavedInqOrgName');       
//	
//	
//}
//
////[指定确认]按钮对应操作
//function Designate()
//{
//  var i = InqApplyGrid.getSelNo();
//  if (i<1)
//  {
//  	alert("请选择一条调查申请记录！");
//  	return;
//  }	
//  if(fm.InqPer.value=="" ||fm.InqPer.value==null)
//  {
//  	alert("请选择调查人！");
//  	return;
//  }
//  fm.fmtransact.value="update";
//  fm.action = './LLInqDistributeSave.jsp';
//  submitForm(); //提交
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
}

//提交后操作,服务器数据返回后执行的操作
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
    tSaveFlag ="0";
   // InqApplyGridQueryClick();//更新列表
    jQuery("#publicSearch").click();
}
//modify lzf
function InqApplyGridSelClick()
{
  	var i = PublicWorkPoolGrid.getSelNo()-1;
	fm.tClmNo.value = PublicWorkPoolGrid.getRowColData(i,1);
	fm.tInqNo.value = PublicWorkPoolGrid.getRowColData(i,2); 
	fm.tCustomerNo.value = PublicWorkPoolGrid.getRowColData(i,3); 
	fm.tCustomerName.value = PublicWorkPoolGrid.getRowColData(i,4); 
	fm.tInitDept.value = PublicWorkPoolGrid.getRowColData(i,6);    
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(i,14);    
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(i,15);    
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(i,17);            
	divInqPer.style.display = '';
	
	//2009-04-22 zhangzheng
	if(fm.ActivityID.value=='0000009125')
	{
		fm.claimUserTable.value='llgrpclaimuser';
	}
	else
	{
		fm.claimUserTable.value='llclaimuser';
	}
	
	//调查内容信息的查询 2006-01-13 ZHaoRx
	fm.SavedCustomerName.value = "";
	fm.SavedBatNo.value        = "";
	fm.SavedMoreInq.value      = "";
//	fm.SavedVIPFlag.value      = "";
	fm.SavedInqReason.value    = "";
	fm.SavedInqOrg.value       = "";
	fm.SavedInqItem.value      = "";
	fm.SavedInqDesc.value      = "";
	/*var tInqSavedSQL = " select CustomerName,BatNo,"
	                 + " case LocFlag when '0' then '否' when '1' then '是' else '' end,"
	                 + " case VIPFlag when '0' then '否' when '1' then '是' else '' end,"
	                 + " InqRCode,InqDept,InqItem,InqDesc from llinqapply "
	                 + " where clmno='"+fm.tClmNo.value+"' and inqno='"+fm.tInqNo.value+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqDistributeInputSql");
	mySql.setSqlId("LLInqDistributeSql3");
	mySql.addSubPara(fm.tClmNo.value ); 
	mySql.addSubPara(fm.tInqNo.value ); 
	//prompt("调查内容信息的查询SQL",tInqSavedSQL);                 
	var tSavedRuselt = new Array;
	tSavedRuselt = easyExecSql(mySql.getString());
	fm.SavedCustomerName.value = tSavedRuselt[0][0];
	fm.SavedBatNo.value        = tSavedRuselt[0][1];
	fm.SavedMoreInq.value      = tSavedRuselt[0][2];
//	fm.SavedVIPFlag.value      = tSavedRuselt[0][3];
	fm.SavedInqReason.value    = tSavedRuselt[0][4];
	fm.SavedInqOrg.value       = tSavedRuselt[0][5];  
	fm.SavedInqItem.value      = tSavedRuselt[0][6]; 
	fm.SavedInqDesc.value      = tSavedRuselt[0][7]; 
	showOneCodeName('llinqreason','SavedInqReason','SavedInqReasonName');   
	showOneCodeName('stati','SavedInqOrg','SavedInqOrgName');       
	
	
}

//[指定确认]按钮对应操作
function Designate()
{
  var i = PublicWorkPoolGrid.getSelNo();
  if (i<1)
  {
  	alert("请选择一条调查申请记录！");
  	return;
  }	
  if(fm.InqPer.value=="" ||fm.InqPer.value==null)
  {
  	alert("请选择调查人！");
  	return;
  }
  fm.fmtransact.value="update";
  fm.action = './LLInqDistributeSave.jsp';
  submitForm(); //提交
}




