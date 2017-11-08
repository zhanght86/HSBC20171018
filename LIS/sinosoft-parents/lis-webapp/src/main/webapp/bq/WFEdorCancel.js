//程序名称：WFEdorCancel.js
//程序功能：保全工作流-保全撤销
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var turnPageAllGrid = new turnPageClass();
var divTurnPageSelfGrid = new turnPageClass();
var tCustomerEdorFlag = 0;
var tFirstEdorAcceptNo="";
var sqlresourcename = "bq.WFEdorCancelInputSql";

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
            //queryAllGrid();
            //querySelfGrid();
            jQuery("#publicSearch").click();
            jQuery("#privateSearch").click();
        }
        catch (ex) {}
    }
}
function jquerySelfGrid(){
jQuery("#privateSearch").click();
}
/*********************************************************************
 *  查询工作流共享池
 *  描述:查询工作流共享池
 *********************************************************************
 */
function queryAllGrid()
{
    // 书写SQL语句
    var strSQL = "";
/*    strSQL =  " select missionprop1, missionprop2, "
            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
            + " (select appntname from lccont where contno=a.missionprop2), "
            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
            + " missionprop7, "
            + " createoperator, makedate, missionid, submissionid, activityid, "
            + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = a.missionprop1  and rownum = 1) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
            + " from lwmission a where 1=1  "
            + " and activityid = '0000000008' "  //工作流保全-保全撤销
            + " and processid = '0000000000' "
            + " and defaultoperator is null "
            + getWherePart('missionprop1', 'EdorAcceptNo')
            + getWherePart('missionprop2', 'OtherNo')
            + getWherePart('missionprop3', 'OtherNoType')
            + getWherePart('missionprop4', 'EdorAppName')
            + getWherePart('missionprop5', 'AppType')
            + getWherePart('missionprop7', 'ManageCom')
            + getWherePart('MakeDate', 'MakeDate')
            + " and missionprop7 like '" + manageCom + "%%' "
            + " and createoperator = '" + operator + "' "
            //XinYQ added on 2007-05-17 : 逾期终止 核保终止 复核终止 的保全不显示
            + " and not exists (select 'X' from LPEdorApp where 1 = 1 and EdorAcceptNo = a.MissionProp1 and EdorState in ('4', '8', '9'))"
            + " order by (select 1 from lpedorapp where EdorAcceptNo = EdorAcceptNo), (select edorappdate from lpedoritem where EdorAcceptNo = a.missionprop1  and rownum = 1),makedate,maketime";
*/    
    var sqlid1="WFEdorCancelInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(curDay);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNoType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAppName'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('AppType'))[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('MakeDate'))[0].value);
	mySql1.addSubPara(manageCom);
	mySql1.addSubPara(operator);
	strSQL=mySql1.getString();
    
    turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
    turnPageAllGrid.queryModal(strSQL, AllGrid);
    HighlightAllRow();
}

/*********************************************************************
 *  查询我的任务队列
 *  描述: 查询我的任务队列
 *********************************************************************
 */
function querySelfGrid()
{
    // 书写SQL语句
    var strSQL = "";
/*    strSQL =  " select missionprop1, missionprop2, "
            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
            + " (select appntname from lccont where contno=a.missionprop2), "
            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
            + " missionprop7, "
            + " createoperator, makedate, missionid, submissionid, activityid, "
            + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
            + " from lwmission a where 1=1  "
            + " and activityid = '0000000008' "  //工作流保全-保全撤销
            + " and processid = '0000000000' "
            + " and defaultoperator ='" + operator + "'"
            //增加校验，客户层保全，仅仅显示原始受理号记录
            + " and (exists( select 1 from lpedorapp b where b.edoracceptno=a.missionprop1  and b.othernotype='1' "
            + " and (exists(select 1 from lpconttempinfo c where  c.icedoracceptno=a.missionprop1 and c.edoracceptno=c.icedoracceptno) "
            + "      or not exists(select 1 from lpconttempinfo c where  c.icedoracceptno=a.missionprop1)))"
            + " or exists (select 1 from lpedorapp b where  b.edoracceptno=a.missionprop1 and b.othernotype='3' ))"
            + " order by (select 1 from lpedorapp where EdorAcceptNo = a.missionprop1), (select edorappdate from lpedoritem where EdorAcceptNo = a.missionprop1 and rownum = 1),makedate,maketime";
*/    
    var sqlid2="WFEdorCancelInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(curDay);//指定传入的参数
	mySql2.addSubPara(operator);
	strSQL=mySql2.getString();
    
    divTurnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
    divTurnPageSelfGrid.queryModal(strSQL, SelfGrid);
    HighlightSelfRow();
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,13);  	
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
  	var days = AllGrid.getRowColData(i,13);  	
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
  	var days = PrivateWorkPoolGrid.getRowColData(i,13);  	
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
  	var days = SelfGrid.getRowColData(i,13);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

/*********************************************************************
 *  跳转到具体的业务处理页面
 *  描述: 跳转到具体的业务处理页面
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
    var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
    var tContNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
    var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 19);
    var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 20);
    var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 22);
    
        //如果已经进行保全问题件处理，则不允许进行保全撤销
    var strSQL = "";
//    var strSQL = "select prtseq from loprtmanager where code in ('BQ37') and Stateflag !='2' and otherno = '"+tContNo+"' and standbyflag1='"+tEdorAcceptNo+"'";
    var sResult;
    
    var sqlid3="WFEdorCancelInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(tEdorAcceptNo);
	strSQL=mySql3.getString();
    
    sResult = easyExecSql(strSQL);
    if(sResult !=null){
        alert("保单有未核销的保全审核通知书，不允许进行保全撤销");
        return;
    }
    if(!CustomerBQ_Check2(tEdorAcceptNo))
    {
      alert("判断当前保全是否属于客户层保全项目时失败！");
      return false;
    }
    if(tCustomerEdorFlag>0) //客户层保全
    {   
        //判断是否有核保结论为拒保的，不允许撤销
        var strUwSQL="";
/*        var strUwSQL=" select count(*) from LPAppUWMasterMain a"
	                +" where a.EdorAcceptNo in ( select b.icedoracceptno from lpconttempinfo b where b.edoracceptno='"+tFirstEdorAcceptNo+"' union select '"+tFirstEdorAcceptNo+"' from dual)"
	                +" and a.state='3' "; 
*/	    
	    var checkcount=0;
	    
	    var sqlid4="WFEdorCancelInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(tFirstEdorAcceptNo);//指定传入的参数
		mySql4.addSubPara(tFirstEdorAcceptNo);
		strUwSQL=mySql4.getString();
	    
	    checkcount = easyExecSql(strUwSQL, 1, 1, 1); 
	    if(checkcount>0)
	    {
	        alert("当前保全为客户层保全项，且存在保单保全核保结论为拒保，不允许进行保全撤销");
	        return;
	    }  
	    //判断是否有子任务做过保全确认，有的话不允许撤销
        var strUwSQL2="";
/*        var strUwSQL2=" select count(*) from lpedoritem a"
            +" where a.EdorAcceptNo in ( select b.icedoracceptno from lpconttempinfo b where b.edoracceptno='"+tFirstEdorAcceptNo+"' union select '"+tFirstEdorAcceptNo+"' from dual)"
            +" and a.edorstate='0' "; 
*/	    
	    var sqlid5="WFEdorCancelInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(tFirstEdorAcceptNo);//指定传入的参数
		mySql5.addSubPara(tFirstEdorAcceptNo);
		strUwSQL2=mySql5.getString();
	    
	    var checkcount2=0;
	    checkcount2 = easyExecSql(strUwSQL2, 1, 1, 1); 
	    if(checkcount2>0)
	    {
	        alert("当前保全为客户层保全项，且存在保单已经进行保单确认操作，不允许进行保全撤销");
	        return;
	    }  
    }
    else //普通保全
    {
	    var strUwSQL="";
/*	    var strUwSQL=" select State, "
	                +" (select codename "
	                +" from ldcode "
	                +" where '1242813742000' = '1242813742000' "
	                +" and codetype = 'edorappstate'"
	                +" and code = state), "
	                +" UWIdea "
	                +" from LPAppUWMasterMain "
	                +" where EdorAcceptNo ='"+tEdorAcceptNo+"'"; 
*/	    
	    var sqlid6="WFEdorCancelInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tEdorAcceptNo);//指定传入的参数
		strUwSQL=mySql6.getString();
	    
	    var sUWResult;
	    sUWResult = easyExecSql(strUwSQL); 
	    //alert(sUWResult);
	    if(sUWResult!=null&&sUWResult[0][0]=='3'){
	        alert("保单保全核保结论为拒保，不允许进行保全撤销");
	        return;
	    }      
    }     
    
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID=" + tActivityID + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID;
    //alert(tEdorAcceptNo);
    var newWindow = OpenWindowNew("../bq/EdorCancelFrame.jsp?Interface= ../bq/EdorCancelInput.jsp" + varSrc,"","left");
}

/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *********************************************************************
 */
 function applyMission()
{
    var selno = PublicWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要申请的任务！");
          return;
    }
   // alert(PublicWorkPoolGrid.getRowData(selno));
    fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 19);
    fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 20);
    fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 22);
    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("选择任务为空！");
          return;
    }
    var tEdorAcceptNo = PublicWorkPoolGrid.getRowColData(selno, 1);
    //增加客户层校验，如果是客户层保全，受理号拆分后必须所有任务都进行过自动核保才允许进行人工核保。
    if(!CustomerBQ_Check1(tEdorAcceptNo))
    {
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
}
//校验客户层保全是否有处于人工核保的子任务，如果有，不允许申请撤销
function CustomerBQ_Check1( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		var sqlid7="WFEdorCancelInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(xEdorAcceptNo);//指定传入的参数
		sql1=mySql7.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   if(CustomerBQ_flag>0) //说明是客户层保全
   {
       //判断当前受理号是否是原始受理号，客户层保全只允许以原始受理号进行撤销
        var checkcount2=0;
	    var sql2="";
//	    var sql2=" select count(*) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"' ";
	    
	    var sqlid8="WFEdorCancelInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(xEdorAcceptNo);//指定传入的参数
		sql2=mySql8.getString();
	    
	    checkcount2= easyExecSql(sql2, 1, 1, 1); 
	    if(checkcount2>0)
	    {//说明已做了受理号拆分，判断当前受理号是否是原是受理号
	      var sql3="";
//	      var sql3=" select distinct(edoracceptno) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"'  ";
	      
	      var sqlid9="WFEdorCancelInputSql9";
		  var mySql9=new SqlClass();
		  mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
		  mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		  mySql9.addSubPara(xEdorAcceptNo);//指定传入的参数
		  sql3=mySql9.getString();
	      
	      tFirstEdorAcceptNo= easyExecSql(sql3, 1, 1, 1); 
	      if(tFirstEdorAcceptNo!=xEdorAcceptNo)
	      {
	         alert("当前保全为客户层保全项，只能以原始受理号（"+tFirstEdorAcceptNo+"）进行保全撤销");
	         return;
	      }
	    }
	    else  //还没做受理号拆分，当前受理号就是原始受理号
	    {
	       tFirstEdorAcceptNo=xEdorAcceptNo;
	    }
	    
     var sql4="";
/*     var sql4="select count(*) from lwmission where defaultoperator is not null "
      +" and activityid in ('0000000005','0000000007') and missionprop1 in "
      +" (select a.icedoracceptno from lpconttempinfo a where a.edoracceptno='"+tFirstEdorAcceptNo+"')";
*/     
     
     	  var sqlid10="WFEdorCancelInputSql10";
		  var mySql10=new SqlClass();
		  mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
		  mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		  mySql10.addSubPara(tFirstEdorAcceptNo);//指定传入的参数
		  sql4=mySql10.getString();
     
     var all_flag= 0;
     all_flag= easyExecSql(sql4, 1, 1, 1); 
     if(all_flag>0)
     {
        alert("此保全为客户层保全，目前仍有子任务处于人工核保状态，无法撤销。");
        return false;
     }
   }
   return true;
}
function CustomerBQ_Check2( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		  var sqlid11="WFEdorCancelInputSql11";
		  var mySql11=new SqlClass();
		  mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
		  mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		  mySql11.addSubPara(xEdorAcceptNo);//指定传入的参数
		  sql1=mySql11.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   tCustomerEdorFlag =CustomerBQ_flag;
   return true;
}
 
 /*
function applyMission()
{
    var selno = AllGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要申请的任务！");
          return;
    }
    fm.MissionID.value = AllGrid.getRowColData(selno, 9);
    fm.SubMissionID.value = AllGrid.getRowColData(selno, 10);
    fm.ActivityID.value = AllGrid.getRowColData(selno, 11);
    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("选择任务为空！");
          return;
    }
    var tEdorAcceptNo = AllGrid.getRowColData(selno, 1);
    //增加客户层校验，如果是客户层保全，受理号拆分后必须所有任务都进行过自动核保才允许进行人工核保。
    if(!CustomerBQ_Check1(tEdorAcceptNo))
    {
      return;
    }
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fm.action = "../bq/MissionApply.jsp";
    document.getElementById("fm").submit(); //提交
}
//校验客户层保全是否有处于人工核保的子任务，如果有，不允许申请撤销
function CustomerBQ_Check1( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		var sqlid7="WFEdorCancelInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(xEdorAcceptNo);//指定传入的参数
		sql1=mySql7.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   if(CustomerBQ_flag>0) //说明是客户层保全
   {
       //判断当前受理号是否是原始受理号，客户层保全只允许以原始受理号进行撤销
        var checkcount2=0;
	    var sql2="";
//	    var sql2=" select count(*) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"' ";
	    
	    var sqlid8="WFEdorCancelInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(xEdorAcceptNo);//指定传入的参数
		sql2=mySql8.getString();
	    
	    checkcount2= easyExecSql(sql2, 1, 1, 1); 
	    if(checkcount2>0)
	    {//说明已做了受理号拆分，判断当前受理号是否是原是受理号
	      var sql3="";
//	      var sql3=" select distinct(edoracceptno) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"'  ";
	      
	      var sqlid9="WFEdorCancelInputSql9";
		  var mySql9=new SqlClass();
		  mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
		  mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		  mySql9.addSubPara(xEdorAcceptNo);//指定传入的参数
		  sql3=mySql9.getString();
	      
	      tFirstEdorAcceptNo= easyExecSql(sql3, 1, 1, 1); 
	      if(tFirstEdorAcceptNo!=xEdorAcceptNo)
	      {
	         alert("当前保全为客户层保全项，只能以原始受理号（"+tFirstEdorAcceptNo+"）进行保全撤销");
	         return;
	      }
	    }
	    else  //还没做受理号拆分，当前受理号就是原始受理号
	    {
	       tFirstEdorAcceptNo=xEdorAcceptNo;
	    }
	    
     var sql4="";
/*     var sql4="select count(*) from lwmission where defaultoperator is not null "
      +" and activityid in ('0000000005','0000000007') and missionprop1 in "
      +" (select a.icedoracceptno from lpconttempinfo a where a.edoracceptno='"+tFirstEdorAcceptNo+"')";
*/     
     /*
     	  var sqlid10="WFEdorCancelInputSql10";
		  var mySql10=new SqlClass();
		  mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
		  mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		  mySql10.addSubPara(tFirstEdorAcceptNo);//指定传入的参数
		  sql4=mySql10.getString();
     
     var all_flag= 0;
     all_flag= easyExecSql(sql4, 1, 1, 1); 
     if(all_flag>0)
     {
        alert("此保全为客户层保全，目前仍有子任务处于人工核保状态，无法撤销。");
        return false;
     }
   }
   return true;
}

//校验是否是客户层保全
function CustomerBQ_Check2( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		  var sqlid11="WFEdorCancelInputSql11";
		  var mySql11=new SqlClass();
		  mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
		  mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		  mySql11.addSubPara(xEdorAcceptNo);//指定传入的参数
		  sql1=mySql11.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   tCustomerEdorFlag =CustomerBQ_flag;
   return true;
}


/*********************************************************************
 *  打开工作流记事本查看页面
 *  描述: 打开工作流记事本查看页面
 *********************************************************************
 */
 function showNotePad()
{
    var selno = PrivateWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }

    var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 19);
    var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 20);
    var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 22);
    var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"","left");
}
 /*
function showNotePad()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }

    var MissionID = SelfGrid.getRowColData(selno, 9);
    var SubMissionID = SelfGrid.getRowColData(selno, 10);
    var ActivityID = SelfGrid.getRowColData(selno, 11);
    var OtherNo = SelfGrid.getRowColData(selno, 1);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"","left");
}
*/