//程序名称：WFEdorManuUW.js
//程序功能：保全工作流-人工核保
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

var sqlresourcename = "bq.WFEdorManuUWInputSql";

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            //MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
        	//ToBusiDeal();
           // easyQueryClickAll();
           // easyQueryClickSelf();
           jQuery("#publicSearch").click();
           jQuery("#privateSearch").click();
        }
        catch (ex) {}
    }
}
function privateWorkPoolQuery(){
	jQuery("#privateSearch").click();
}

/*********************************************************************
 *  查询工作流共享池
 *  描述:查询工作流共享池
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickAll()
{
	var InsuredName = fm.InsuredName.value;
	var tempSql0="";
	if(InsuredName!=null&&InsuredName!=""){
		tempSql0=" and exists (select 'X' from  lcinsured where contno=l.missionprop2 and name= '"+InsuredName+"') "
	}

	var AppntName = fm.AppntName.value;
	var tempSql1="";
	if(AppntName!=null&&AppntName!=""){
		tempSql1=" and exists (select 'X' from  lcappnt where contno=l.missionprop2 and appntname= '"+AppntName+"') "
	}

	var EdorType = fm.EdorType.value;
	var tempSql2="";
	if(EdorType!=null&&EdorType!=""){
		tempSql2=" and exists (select 'X' from  lpedoritem where contno=l.missionprop2 and edortype= '"+EdorType+"' and EdorAcceptNo=l.missionprop1) "
	}

    // 书写SQL语句
    var strSQL = "";
    var strSQLNum = "";
    var strSQLCommon = "";

/*    strSQL =" select (select edortype || '-' || "
			     +" (select edorname "
			     +"	from lmedoritem "
			     +"	where appobj in ('I','B')"
			     +"	and edorcode = p.edortype)"
			     +"	from lpedoritem p "
			     +"	 where p.edoracceptno = l.missionprop1), "
			     +" missionprop2,"
			     +" missionprop1, "
			     +" '', "//VIP客户 
			     +" '', "//星级业务员 保留待需求定下后再添加
			     +" (select name from lcinsured i where i.contno = l.missionprop2), "
			     +" l.makedate,"// --转核日期-
			     +" l.maketime,"// --转核时间-
			     +" l.missionprop9, "
			     +" l.missionprop10, "
			     +" l.missionprop4, "
			     //+" l.missionprop7, "
			     +" (select substr(lpcont.managecom,0,4) from lpcont where edorno=(select edorno from LPEdorMain where EdorAcceptNo=l.missionprop1) and contno=l.missionprop2), "
			     +" l.lastOperator, "
			     +" missionid, "
			     +" submissionid, "
			     +" ActivityID, "
			     +" ActivityStatus, "
			     + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = l.missionprop1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = l.missionprop1) and commondate <= '"+curDay+"' and workdateflag = 'Y') ";
*/
	var sqlid1="WFEdorManuUWInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(curDay);//指定传入的参数
	//strSQL +=  " " + mySql1.getString();

	 strSQLNum = " select (case when count(*) is not null then count(*) else 0 end ) ";  
     strSQLCommon = " from lwmission l where 1=1  "
           + " and activityid in(select activityid from lwactivity where functionid='10020004') "  //工作流保全-人工核保
           //+ " and processid = '0000000000' "
           + " and defaultoperator is null"
           + tempSql0
           + tempSql1
           + tempSql2
           + getWherePart('missionprop1', 'EdorAcceptNo')
           + getWherePart('missionprop2', 'OtherNo')
           + getWherePart('missionprop4', 'EdorAppName')
           //+ getWherePart('missionprop7', 'ManageCom','like')
           + getWherePart('makedate', 'theCurrentDate','<=')
           //+ getWherePart('missionprop9', 'BackDate','<=')
           + " and missionprop7 like '" + manageCom + "%%'"  ;















           
           if(fm.ManageCom.value !=null && fm.ManageCom.value !="")
			{
				strSQLCommon = strSQLCommon + " and exists (select 1 from lpcont where edorno=(select edorno from LPEdorMain where EdorAcceptNo=l.missionprop1) and contno=l.missionprop2 and substr(lpcont.managecom,1,4)='"+fm.ManageCom.value+"') ";						
			}
           if(fm.BackDate.value !=null && fm.BackDate.value !="")
			{
				strSQLCommon = strSQLCommon + " and (concat(concat(missionprop9,' '),missionprop10)) <=(concat(concat('"+fm.BackDate.value+"',' '),'"+fm.BackTime.value+"'))  ";						
			}
           if(fm.UWState.value ==null || fm.UWState.value =="")
			{
				strSQLCommon = strSQLCommon + " and missionprop18 in ('1','2','3','4') "
				     + " order by  (select edorappdate from lpedoritem where EdorAcceptNo = l.missionprop1), MakeDate, MakeTime ";						
			}
			else if(fm.UWState.value !=null && fm.UWState.value!="" )
			{
				if(fm.UWState.value =="2")
				    strSQLCommon = strSQLCommon + " and missionprop18 ='"+ fm.UWState.value +"' "			
					     + " order by  (select edorappdate from lpedoritem where EdorAcceptNo = l.missionprop1),missionprop9, missionprop10, MakeDate, MakeTime ";	
				else if(fm.UWState.value =="3")
				    strSQLCommon = strSQLCommon + " and missionprop18 ='"+ fm.UWState.value +"' "			
					     + " order by  (select edorappdate from lpedoritem where EdorAcceptNo = l.missionprop1),ModifyDate, ModifyTime, MakeDate, MakeTime ";	
				else
					strSQLCommon = strSQLCommon + " and missionprop18 ='"+ fm.UWState.value +"' "			
					     + " order by  (select edorappdate from lpedoritem where EdorAcceptNo = l.missionprop1),MakeDate, MakeTime ";				
			    
			}
	mySql1.addSubPara(strSQLCommon);//指定传入的参数
	strSQL = mySql1.getString();
	//strSQL = strSQL + strSQLCommon;	
    //prompt("",strSQL);
    //turnPage1.pageDivName = "divTurnPage1";
    turnPage1.queryModal(strSQL, AllGrid);
    
    //查询共享工作池中总共有多少单子
	       strSQLNum = strSQLNum + strSQLCommon;	   
		   var sum = easyExecSql(strSQLNum);
		   //alert(sum);
		   if(sum!=null && sum!="")
		       document.all('PolSum').value = sum;
		   else
		       document.all('PolSum').value = "0";
    //queryPolSum();
    HighlightAllRow();
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,19);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightAllRow(){
		for(var i=0; i<AllGrid.mulLineCount; i++){
  	var days = AllGrid.getRowColData(i,19);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,19);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightSelfRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,19);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
function queryPolSum()
{
	//查询共享工作池中总共有多少单子
    var sumsql = "";
/*		   var sumsql = "select count(*) from lwmission t where 1=1 "
				+ " and t.activityid = '0000000005' "  //工作流保全-人工核保
           		+ " and t.processid = '0000000000' "
				+ " and t.defaultoperator is null ";
*/				
	var sqlid3="WFEdorManuUWInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara("1");//指定传入的参数
	sumsql +=  " " + mySql3.getString();
				
			if(fm.UWState.value!=null&&fm.UWState.value!=""){
				sumsql=sumsql+ " and t.missionprop18 = '"+fm.UWState.value+"' ";
			}
			else
			{
				sumsql=sumsql+ " and t.missionprop18 in ('1','2','3','4') ";
			}
			//prompt('',sumsql);
		   var sum = easyExecSql(sumsql);
		   if(sum!=null && sum!="")
		       document.all('PolSum').value = sum;
		   else
		       document.all('PolSum').value = "0";
}

/*********************************************************************
 *  查询我的任务队列
 *  描述: 查询我的任务队列
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSelf()
{
    // 初始化表格
    //initSelfGrid();

	var InsuredName2 = fm.InsuredName2.value;
	var tempSql0="";
	if(InsuredName2!=null&&InsuredName2!=""){
		tempSql0=" and exists (select 'X' from  lcinsured where contno=l.missionprop2 and name= '"+InsuredName2+"') "
	}

	var AppntName2 = fm.AppntName2.value;
	var tempSql1="";
	if(AppntName2!=null&&AppntName2!=""){
		tempSql1=" and exists (select 'X' from  lcappnt where contno=l.missionprop2 and appntname= '"+AppntName2+"') "
	}

	var EdorType2 = fm.EdorType2.value;
	var tempSql2="";
	if(EdorType2!=null&&EdorType2!=""){
		tempSql2=" and exists (select 'X' from  lpedoritem where contno=l.missionprop2 and edortype= '"+EdorType2+"' and EdorAcceptNo=l.missionprop1) "
	}
	var tempSql3 = "";
   if(fm.ManageCom2.value !=null && fm.ManageCom2.value !="")
			{
				tempSql3 = " and exists (select 1 from lpcont where edorno=(select edorno from LPEdorMain where EdorAcceptNo=l.missionprop1) and contno=l.missionprop2 and substr(lpcont.managecom,1,4)='"+fm.ManageCom2.value+"') ";						
			}
	var tempSql4 = "";      
  if(fm.BackDate2.value !=null && fm.BackDate2.value !="")
	{
			tempSql4 =  " and (concat(concat(missionprop9,' '),missionprop10)) <=(concat(concat('"+fm.BackDate2.value+"',' '),'"+fm.BackTime2.value+"'))  ";						
	}
		var tempSql5 = ""; 
  if(fm.UWState2.value ==null || fm.UWState2.value =="")
			{
				tempSql5 =  " and missionprop18 in ('1','2','3','4') "
				     + " order by (select edorappdate from lpedoritem where EdorAcceptNo = l.missionprop1),MakeDate, MakeTime ";						
			}
			else if(fm.UWState2.value!=null && fm.UWState2.value!="")
			{
			    tempSql5= " and missionprop18 ='"+ fm.UWState2.value +"' "			
				     + " order by  (select edorappdate from lpedoritem where EdorAcceptNo = l.missionprop1),MakeDate, MakeTime ";						
			    
			}

    // 书写SQL语句
    var strSQL = "";

/*    strSQL =" select (select edortype || '-' || "
			     +" (select edorname "
			     +"	from lmedoritem "
			     +"	where appobj in ('I','B')"
			     +"	and edorcode = p.edortype)"
			     +"	from lpedoritem p "
			     +"	 where p.edoracceptno = l.missionprop1), "
			     +" missionprop2,"
			     +" missionprop1, "
			     +" '', "//VIP客户 
			     +" '', "//星级业务员
			     +" (select name from lcinsured i where i.contno = l.missionprop2), "
			     +" l.makedate,"// --转核日期
			     +" l.maketime,"// --转核时间
			     +" l.missionprop9, "
			     +" l.missionprop10, "
			     +" l.missionprop4, "
			     //+" l.missionprop7, "
			     +" (select substr(lpcont.managecom,0,4) from lpcont where edorno=(select edorno from LPEdorMain where EdorAcceptNo=l.missionprop1) and contno=l.missionprop2), "
			     +" l.lastOperator, "
			     +" missionid, "
			     +" submissionid, "
			     +" ActivityID, "
			     +" ActivityStatus, "
			     + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = l.missionprop1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = l.missionprop1) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
           + " from lwmission  l where 1=1  "
           + " and activityid = '0000000005' "  //工作流保全-人工核保
           + " and processid = '0000000000' "
           + " and defaultoperator ='" + operator + "'"
           + tempSql0
           + tempSql1
           + tempSql2
           + getWherePart('missionprop1', 'EdorAcceptNo2')
           + getWherePart('missionprop2', 'OtherNo2')
           + getWherePart('missionprop4', 'EdorAppName2')
           //+ getWherePart('missionprop7', 'ManageCom2','like')
           + getWherePart('makedate', 'theCurrentDate2','<=')
           //+ getWherePart('missionprop9', 'BackDate2','<=')
           + " and missionprop7 like '" + manageCom + "%%'"  ;
*/           
    var sqlid4="WFEdorManuUWInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(curDay);//指定传入的参数
	mySql4.addSubPara(operator);
	mySql4.addSubPara(tempSql0);
	mySql4.addSubPara(tempSql1);
	mySql4.addSubPara(tempSql2);
	mySql4.addSubPara(fm.EdorAcceptNo2.value);
	mySql4.addSubPara(fm.OtherNo2.value);
	mySql4.addSubPara(fm.EdorAppName2.value);
	mySql4.addSubPara(fm.theCurrentDate2.value);
	mySql4.addSubPara(manageCom);
	mySql4.addSubPara(tempSql3);
	mySql4.addSubPara(tempSql4);
	mySql4.addSubPara(tempSql5);
	strSQL =   mySql4.getString();        
           


    //prompt("",strSQL);
    //turnPage2.pageDivName = "divTurnPage2";
    turnPage2.queryModal(strSQL, SelfGrid);
    HighlightSelfRow();
    document.getElementsByName("NotePadButton")[0].value = " 记事本查看 ";
}

/*********************************************************************
 *  跳转到具体的业务处理页面
 *  描述: 跳转到具体的业务处理页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function GoToBusiDeal()
{
    var selno = PrivateWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }
    //alert(PrivateWorkPoolGrid.getRowData(selno));
	var tEdorType = PrivateWorkPoolGrid.getRowColData(selno,1);
    var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 3);
    var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
    var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 23);
    var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 25);
    var tActivityStatus = PrivateWorkPoolGrid.getRowColData(selno, 20);
    

    var varSrc="&EdorType="+tEdorType+"&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&ActivityStatus=" + tActivityStatus;
    //var varSrc="&EdorType="+tEdorType+ "&EdorAcceptNo=" + tEdorAcceptNo +  "&ActivityStatus=" + tActivityStatus;
    var newWindow = OpenWindowNew("../bq/EdorAppManuUWFrame.jsp?Interface=../bq/EdorAppManuUWInput.jsp" + varSrc,"保全申请人工核保","left");
	HighlightSelfRow();
}
 /*
function GoToBusiDeal()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }
	var tEdorType = SelfGrid.getRowColData(selno,1);
    var tEdorAcceptNo = SelfGrid.getRowColData(selno, 3);
    var tMissionID = SelfGrid.getRowColData(selno, 14);
    var tSubMissionID = SelfGrid.getRowColData(selno, 15);
    var tActivityID = SelfGrid.getRowColData(selno, 16);
    var tActivityStatus = SelfGrid.getRowColData(selno, 17);
    

    var varSrc="&EdorType="+tEdorType+"&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&ActivityStatus=" + tActivityStatus;
    var newWindow = OpenWindowNew("../bq/EdorAppManuUWFrame.jsp?Interface=../bq/EdorAppManuUWInput.jsp" + varSrc,"保全申请人工核保","left");
HighlightSelfRow();
}
*/
/*********************************************************************
 *  跳转到具体的业务处理页面
 *  描述: 跳转到具体的业务处理页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ToBusiDeal()
{
    var selno = AllGrid.getSelNo()-1;
	var tEdorType = AllGrid.getRowColData(selno,1);
    var tEdorAcceptNo = AllGrid.getRowColData(selno, 3);
    var tMissionID = AllGrid.getRowColData(selno, 14);
    var tSubMissionID = AllGrid.getRowColData(selno, 15);
    var tActivityID = AllGrid.getRowColData(selno, 16);
    var tActivityStatus = AllGrid.getRowColData(selno, 17);

    var varSrc="&EdorType="+tEdorType+"&ActivityID="+tActivityID+"&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&ActivityStatus=" + tActivityStatus;
    var newWindow = OpenWindowNew("../bq/EdorAppManuUWFrame.jsp?Interface=../bq/EdorAppManuUWInput.jsp" + varSrc,"保全申请人工核保","left");

}

/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function applyMission()
{
    //<!-- XinYQ added on 2005-11-09 : BGN -->
    //检查操作员是否具有申请核保权限
    var sOperator = document.getElementsByName("Operator")[0].value;
    //var strSQL = "select EdorPopedom from LdUWUser where UserCode='" + sOperator + "'";
    //try
    //{
    //    var arrResult = easyExecSql(strSQL, 1, 0);
    //}
    //catch (ex)
    //{
    //    alert("用户保全核保权限查询失败！ ");
    //    return;
    //}
    //if (arrResult == null || trim(arrResult[0][0]) != "1")
    //{
    //    alert("对不起, 您没有保全核保权限！ ");
    //    return;
    //}
    //<!-- XinYQ added on 2005-11-09 : END -->

    var selno = PublicWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要申请的任务！");
          return;
    }
    fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 22);
    fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 23);
    fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 25);
    
    var tEdorAcceptNo = PublicWorkPoolGrid.getRowColData(selno, 3);
    //增加客户层校验，如果是客户层保全，受理号拆分后必须所有任务都进行过自动核保才允许进行人工核保。
    if(!CustomerBQ_Check(tEdorAcceptNo))
    {
      alert("此保全为客户层保全，目前仍有子任务不符合人工核保条件，请联系信息技术部查询处理。");
      return;
    }

    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("选择任务为空！");
          return;
    }

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "../bq/MissionApply.jsp";
    document.getElementById("fm").submit(); //提交
    HighlightAllRow();
}
function CustomerBQ_Check( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
    var sqlid5="WFEdorManuUWInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(xEdorAcceptNo);//指定传入的参数
	sql1 = mySql5.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   if(CustomerBQ_flag>0) //说明是客户层保全
   {
     var sql2="";
/*     var sql2="select count(*) from lpconttempinfo a where a.edoracceptno "
     +" =(select edoracceptno from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"' ) "
     +" and state<>'2' ";
*/  
   
    var sqlid6="WFEdorManuUWInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(xEdorAcceptNo);//指定传入的参数
	sql2 = mySql6.getString();
     
     var all_flag= 0;
     all_flag= easyExecSql(sql2, 1, 1, 1); 
     if(all_flag>0)
     {
        return false;
     }
   }
   return true;
} 
 /*
function applyMission()
{
    //<!-- XinYQ added on 2005-11-09 : BGN -->
    //检查操作员是否具有申请核保权限
    var sOperator = document.getElementsByName("Operator")[0].value;
    //var strSQL = "select EdorPopedom from LdUWUser where UserCode='" + sOperator + "'";
    //try
    //{
    //    var arrResult = easyExecSql(strSQL, 1, 0);
    //}
    //catch (ex)
    //{
    //    alert("用户保全核保权限查询失败！ ");
    //    return;
    //}
    //if (arrResult == null || trim(arrResult[0][0]) != "1")
    //{
    //    alert("对不起, 您没有保全核保权限！ ");
    //    return;
    //}
    //<!-- XinYQ added on 2005-11-09 : END -->

    var selno = AllGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要申请的任务！");
          return;
    }

    fm.MissionID.value = AllGrid.getRowColData(selno, 14);
    fm.SubMissionID.value = AllGrid.getRowColData(selno, 15);
    fm.ActivityID.value = AllGrid.getRowColData(selno, 16);
    
    var tEdorAcceptNo = AllGrid.getRowColData(selno, 3);
    //增加客户层校验，如果是客户层保全，受理号拆分后必须所有任务都进行过自动核保才允许进行人工核保。
    if(!CustomerBQ_Check(tEdorAcceptNo))
    {
      alert("此保全为客户层保全，目前仍有子任务不符合人工核保条件，请联系信息技术部查询处理。");
      return;
    }

    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("选择任务为空！");
          return;
    }

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    fm.action = "../bq/MissionApply.jsp";
    document.getElementById("fm").submit(); //提交
    HighlightAllRow();
}

function CustomerBQ_Check( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
    var sqlid5="WFEdorManuUWInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(xEdorAcceptNo);//指定传入的参数
	sql1 = mySql5.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   if(CustomerBQ_flag>0) //说明是客户层保全
   {
     var sql2="";
/*     var sql2="select count(*) from lpconttempinfo a where a.edoracceptno "
     +" =(select edoracceptno from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"' ) "
     +" and state<>'2' ";
*/  
/*   
    var sqlid6="WFEdorManuUWInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(xEdorAcceptNo);//指定传入的参数
	sql2 = mySql6.getString();
     
     var all_flag= 0;
     all_flag= easyExecSql(sql2, 1, 1, 1); 
     if(all_flag>0)
     {
        return false;
     }
   }
   return true;
}

/*********************************************************************
 *  打开工作流记事本查看页面
 *  描述: 打开工作流记事本查看页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showNotePad()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }

    var MissionID = SelfGrid.getRowColData(selno, 14);
    var SubMissionID = SelfGrid.getRowColData(selno, 15);
    var ActivityID = SelfGrid.getRowColData(selno, 16);
    var OtherNo = SelfGrid.getRowColData(selno, 2);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}
function EdorDetailQuery()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }

    var tEdorAcceptNo = SelfGrid.getRowColData(selno,1);
    var tOtherNoType  = SelfGrid.getRowColData(selno, 3);

    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType="+tOtherNoType;
    var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");

}

//查询记事本
function checkNotePad()
{
    var nSelNo;
    try
    {
        nSelNo = SelfGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var sOtherNo;
        try
        {
            sOtherNo = SelfGrid.getRowColData(nSelNo, 1);
        }
        catch (ex) {}
        if (sOtherNo == null || sOtherNo.trim() == "")
        {
            return;
        }
        var QuerySQL, arrResult;
//        QuerySQL = "select count(*) from LWNotePad where OtherNo = '" + sOtherNo + "'";
        
    var sqlid7="WFEdorManuUWInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(sOtherNo);//指定传入的参数
	QuerySQL = mySql7.getString();
        
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询记事本信息出现异常！ ");
            return;
        }
        if (arrResult != null)
        {
            try
            {
                document.getElementsByName("NotePadButton")[0].value = "记事本查看 （共" + arrResult[0][0] + "条）)";
            }
            catch (ex) {alert(ex.message)}
        }
        else
        {
            try
            {
                document.getElementsByName("NotePadButton")[0].value = " 记事本查看 ";
            }
            catch (ex) {alert(ex.message)}
        }
    } //arrResult != null
}

//初始化保全项目代码
function initEdorCode() {
    var strSql = "";
//  var strSql = "select distinct b.EdorCode, b.EdorName from LMEdorItem b where b.appobj = 'I'  and edorcode in ('HI','SC','NS','AA','IO','AA','BC','PM','FM','PA','IC','RE','BM','AE') order by EdorCode"
  
    var sqlid8="WFEdorManuUWInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara("1");//指定传入的参数
	strSql = mySql8.getString();
  
  var strResult = easyQueryVer3(strSql);
  //alert(strResult);
  if (strResult) {
    document.all("EdorType").CodeData = strResult;
    document.all("EdorType2").CodeData = strResult;
    
  }
}
