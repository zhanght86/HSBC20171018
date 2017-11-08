//程序名称：WFEdorConfirm.js
//程序功能：保全工作流-保全确认
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mFlag = "0";
var sqlresourcename = "bq.WFEdorConfirmInputSql";

/*********************************************************************
 *  查询工作流共享池
 *  描述:查询工作流共享池
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickAll()
{
	// 初始化表格
	initAllGrid();

	// 书写SQL语句
	var strSQL = "";
/*	
	strSQL =  " select missionprop1, missionprop2, " 
			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
			+ " missionprop11, "
			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
			+ " createoperator, a.makedate, missionid, submissionid, activityid, "
			+ " (select codename from ldcode where code=c.uwstate and codetype='edorcontuw'),"
			+ " c.uwstate ,"
			+ " (select decode(count(*),0,'同意','不同意') from lpcuwmaster d4  where d4.edoracceptno=b.edoracceptno and customeridea='1'),b.operator,c.edorappdate,(select count(*) from ldcalendar where commondate > c.edorappdate and commondate <= '"+curDay+"' and workdateflag = 'Y')"
			+ " from lwmission a,lpedorapp b,lpedormain c where 1=1  and a.missionprop1 = b.edoracceptno and b.edoracceptno=c.edoracceptno "
			+ " and activityid = '0000000009' "  //工作流保全-保全确认
			+ " and processid = '0000000000' "
			+ " and defaultoperator is null "
			+ getWherePart('missionprop1', 'EdorAcceptNoSearch')
			+ getWherePart('missionprop2', 'OtherNo')
			+ getWherePart('missionprop3', 'OtherNoType')	
			+ getWherePart('missionprop4', 'EdorAppName')
			+ getWherePart('missionprop5', 'AppType')
			+ getWherePart('missionprop7', 'ManageCom','like')	
			+ getWherePart('a.MakeDate', 'MakeDate')		
			+ " and missionprop7 like '" + manageCom + "%%'"				
			+ " order by c.edorappdate,a.MakeDate, a.MakeTime ";
*/	
	var sqlid1="WFEdorConfirmInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(curDay);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAcceptNoSearch'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNoType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAppName'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('AppType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('MakeDate'))[0].value);
	mySql1.addSubPara(manageCom);
	strSQL=mySql1.getString();
		
	turnPage.queryModal(strSQL, AllGrid);// mySql.getString()就可以获得对应的SQL。
	HighlightAllRow();
	return true;
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,17);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

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
function HighlightAllRow(){
		for(var i=0; i<AllGrid.mulLineCount; i++){
  	var days = AllGrid.getRowColData(i,17);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

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
	//alert("a");
	initSelfGrid();
	

	// 书写SQL语句
	var strSQL = "";
/*	
	strSQL =  " select missionprop1, missionprop2, " 
			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
			+ " missionprop11, "
			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
			+ " missionprop7, "
			+ " a.createoperator, a.makedate, a.missionid, submissionid, activityid ,(select codename from ldcode where code=c.uwstate and codetype='edorcontuw'), "
			+ " c.uwstate,b.EdorAppName,(select paytodate from lcpol where contno=b.otherno and polno=mainpolno and rownum=1), "
			//+ " (select appntno from lccont where contno=b.otherno) 投保人 ,"
			//+ " (select InsuredNo from lccont where contno=b.otherno) 第一被保人 ,"
			//+ " ''  第二被保人 ,'' 第三被保人,(select edortype from lpedoritem c where c.edoracceptno=b.edoracceptno and rownum=1), "
			//+ " to_char(sysdate,'yyyy-mm-dd') 转核日期,'' 最后回复日期,'' VIP客户,'' 星级业务员,"
			+ " (select decode(count(*),0,'同意','不同意') from lpcuwmaster d4  where d4.edoracceptno=b.edoracceptno and customeridea='1'),"
			+ " (select count(*)  from lpcuwmaster d4  where d4.edoracceptno=b.edoracceptno and customeridea='1'), c.edorappdate,(select count(*) from ldcalendar where commondate > c.edorappdate and commondate <= '"+curDay+"' and workdateflag = 'Y')"
			// modify by jiaqiangli 2009-04-29 取lpedormain的核保结论
			+ " from lwmission a,lpedorapp b,lpedormain c where 1=1  and a.missionprop1 = b.edoracceptno and b.edoracceptno=c.edoracceptno "
			+ " and activityid = '0000000009' "  //工作流保全-保全确认
			+ " and processid = '0000000000' "
			+ " and defaultoperator ='" + operator + "'"
			+ " order by a.MakeDate, a.MakeTime ";
*/	
	var sqlid2="WFEdorConfirmInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(curDay);//指定传入的参数
	mySql2.addSubPara(operator);
	strSQL=mySql2.getString();
	
	turnPage2.queryModal(strSQL, SelfGrid);
	HighlightSelfRow();
	return true;
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
	mFlag = "1";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	
	
	var uWstate = PrivateWorkPoolGrid.getRowColData(selno,18);
	
	//add by jiaqiangli 2009-04-03 保全确认条件
	var tCustomerIdea = PrivateWorkPoolGrid.getRowColData(selno,10);
	// uWstate 为合同核保结论
	//	Edorappstate	1	不同意
	//	Edorappstate	3	拒保
	//	Edorappstate	9	同意
	if (document.all("AppUWState").value != null && document.all("AppUWState").value != '9') {
		alert("只有保全核保结论为同意的才可以提交保全确认！");
		return;
	}
	else {
		// 此分支判断可能不存在，人工核保可能会校验住，但为了保险起见还是加上
		if (uWstate != null && uWstate == '1') {
			alert("保全核保结论为同意但合同核保结论为拒保的不可以提交保全确认！");
			return;
		}
		// 判断合同核保结论
		if (uWstate != null && uWstate == '4') {
			if (tCustomerIdea != null && tCustomerIdea == '1') {
				alert("只有客户意见为同意的才可以提交保全确认！");
				return;
			}
		}
	}
	//add by jiaqiangli 2009-04-03 保全确认条件 

	if(uWstate=='1')
	{
		alert("核保结论为拒保，不允许保全确认，只能操作保全终止！");
		return;
	}
	
	if(!checkprint()) return;

	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 加锁
	lockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 加锁
	
	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
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
	
	mFlag = "1";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	var tActivityID = SelfGrid.getRowColData(selno, 11);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	
	
	var uWstate = SelfGrid.getRowColData(selno,13);
	
	//add by jiaqiangli 2009-04-03 保全确认条件
	var tCustomerIdea = SelfGrid.getRowColData(selno,17);
	// uWstate 为合同核保结论
	//	Edorappstate	1	不同意
	//	Edorappstate	3	拒保
	//	Edorappstate	9	同意
	if (document.all("AppUWState").value != null && document.all("AppUWState").value != '9') {
		alert("只有保全核保结论为同意的才可以提交保全确认！");
		return;
	}
	else {
		// 此分支判断可能不存在，人工核保可能会校验住，但为了保险起见还是加上
		if (uWstate != null && uWstate == '1') {
			alert("保全核保结论为同意但合同核保结论为拒保的不可以提交保全确认！");
			return;
		}
		// 判断合同核保结论
		if (uWstate != null && uWstate == '4') {
			if (tCustomerIdea != null && tCustomerIdea == '1') {
				alert("只有客户意见为同意的才可以提交保全确认！");
				return;
			}
		}
	}
	//add by jiaqiangli 2009-04-03 保全确认条件 

	if(uWstate=='1')
	{
		alert("核保结论为拒保，不允许保全确认，只能操作保全终止！");
		return;
	}
	
	if(!checkprint()) return;

	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 加锁
	lockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 加锁
	
	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
}
*/
function submitUW()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}
	
	mFlag = "0";
	var tCustomerIdea = PrivateWorkPoolGrid.getRowColData(selno,10)
//	if(tCustomerIdea=='0')
//	{
//		alert("客户意见为同意，不允许再次提交核保");
//		return;
//	}
  //alert(tCustomerIdea);

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	
	var uWstate = PrivateWorkPoolGrid.getRowColData(selno,18);
	//add by jiaqiangli 2009-04-03 增加录入强制人工核保原因的判断
	if (document.all("MMUWReason").value == null || trim(document.all("MMUWReason").value) == '') {
		alert("请录入强制人工核保原因！");
		return;
	} 
	
//	edorappstate	1	不同意
//	Edorappstate	3	拒保
//	Edorappstate	9	同意
	//modify by jiaqiangli 2009-03-24 只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保
//	if (document.all("AppUWState").value != null && document.all("AppUWState").value == '1') {
//		alert("只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保!");
//		return;
//	}
//	else if (document.all("AppUWState").value == '9') {
//		if (uWstate != null && uWstate != '' && uWstate != '4') {
//			alert("只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保!");
//			return;	
//		}
//	}
	
	if (document.all("AppUWState").value != null && document.all("AppUWState").value != '1' && document.all("AppUWState").value != '4') {
		alert("只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保!");
		return;
	}
	
	
	
	//tCustomerIdea='1' 表要强制人工核保 
	//正常没有走人工核保的为标准体，保全核保结论应该为同意，此种情况是不允许强制核保的
	tCustomerIdea='1';
	
	//1为不同意
//	else {
//		alert("只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保!");
//		return;
//	}
//	if(uWstate=='9')
//	{
//		alert("核保结论为标准体，不允许再次提交核保！");
//		return;
//	}
//	if(uWstate=='2')
//	{
//		alert("核保结论为维持原条件，不允许再次提交核保！");
//		return;
//	}
//	if(uWstate=='1')
//	{
//		alert("核保结论为拒保，不允许再次提交核保！");
//		return;
//	}

	//alert(SelfGrid.colCount);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.OtherNo1.value       = PrivateWorkPoolGrid.getRowColData(selno, 2);
	fm.OtherNoType1.value   = PrivateWorkPoolGrid.getRowColData(selno, 11);
	fm.EdorAppName1.value   = PrivateWorkPoolGrid.getRowColData(selno, 14);
	fm.Apptype1.value       = PrivateWorkPoolGrid.getRowColData(selno, 15);
	fm.ManageCom1.value     = PrivateWorkPoolGrid.getRowColData(selno, 16);
	fm.AppntName1.value     = PrivateWorkPoolGrid.getRowColData(selno, 4);
	fm.PaytoDate1.value     = PrivateWorkPoolGrid.getRowColData(selno, 17);
	//fm.CustomerNo1.value    = SelfGrid.getRowColData(selno, 16);
	//fm.InsuredName1.value   = SelfGrid.getRowColData(selno, 17);
	//fm.InsuredName2.value   = SelfGrid.getRowColData(selno, 18);
	//fm.InsuredName3.value   = SelfGrid.getRowColData(selno, 19);
	//fm.EdorType1.value      = SelfGrid.getRowColData(selno, 20);
	//fm.theCurrentDate1.value= SelfGrid.getRowColData(selno, 21);
	//fm.BackDate1.value      = SelfGrid.getRowColData(selno, 22);
	//fm.VIP1.value           = SelfGrid.getRowColData(selno, 23);
	//fm.StarAgent1.value     = SelfGrid.getRowColData(selno, 24);
	
	
	fm.uWstate1.value       = uWstate;
	fm.CustomerIdea.value   = tCustomerIdea;
	
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	fm.ActivityID.value	= tActivityID;
	
	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||SUBMITUW";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
}
/*
function submitUW()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}
	
	mFlag = "0";
	var tCustomerIdea = SelfGrid.getRowColData(selno,17)
//	if(tCustomerIdea=='0')
//	{
//		alert("客户意见为同意，不允许再次提交核保");
//		return;
//	}
  //alert(tCustomerIdea);

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	
	var uWstate = SelfGrid.getRowColData(selno,13);
	
	//add by jiaqiangli 2009-04-03 增加录入强制人工核保原因的判断
	if (document.all("MMUWReason").value == null || trim(document.all("MMUWReason").value) == '') {
		alert("请录入强制人工核保原因！");
		return;
	} 
	
//	edorappstate	1	不同意
//	Edorappstate	3	拒保
//	Edorappstate	9	同意
	//modify by jiaqiangli 2009-03-24 只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保
	if (document.all("AppUWState").value != null && document.all("AppUWState").value == '1') {
		alert("只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保!");
		return;
	}
	else if (document.all("AppUWState").value == '9') {
		if (uWstate != null && uWstate != '' && uWstate != '4') {
			alert("只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保!");
			return;	
		}
	}
	
	//tCustomerIdea='1' 表要强制人工核保 
	//正常没有走人工核保的为标准体，保全核保结论应该为同意，此种情况是不允许强制核保的
	tCustomerIdea='1';
	
	//1为不同意
//	else {
//		alert("只有保全核保结论为拒保或同意但合同核保结论为次标准体的可以强制提交核保!");
//		return;
//	}
//	if(uWstate=='9')
//	{
//		alert("核保结论为标准体，不允许再次提交核保！");
//		return;
//	}
//	if(uWstate=='2')
//	{
//		alert("核保结论为维持原条件，不允许再次提交核保！");
//		return;
//	}
//	if(uWstate=='1')
//	{
//		alert("核保结论为拒保，不允许再次提交核保！");
//		return;
//	}

	//alert(SelfGrid.colCount);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;
	fm.OtherNo1.value       = SelfGrid.getRowColData(selno, 2);
	fm.OtherNoType1.value   = SelfGrid.getRowColData(selno, 3);
	fm.EdorAppName1.value   = SelfGrid.getRowColData(selno, 14);
	fm.Apptype1.value       = SelfGrid.getRowColData(selno, 5);
	fm.ManageCom1.value     = SelfGrid.getRowColData(selno, 6);
	fm.AppntName1.value     = SelfGrid.getRowColData(selno, 4);
	fm.PaytoDate1.value     = SelfGrid.getRowColData(selno, 15);
	//fm.CustomerNo1.value    = SelfGrid.getRowColData(selno, 16);
	//fm.InsuredName1.value   = SelfGrid.getRowColData(selno, 17);
	//fm.InsuredName2.value   = SelfGrid.getRowColData(selno, 18);
	//fm.InsuredName3.value   = SelfGrid.getRowColData(selno, 19);
	//fm.EdorType1.value      = SelfGrid.getRowColData(selno, 20);
	//fm.theCurrentDate1.value= SelfGrid.getRowColData(selno, 21);
	//fm.BackDate1.value      = SelfGrid.getRowColData(selno, 22);
	//fm.VIP1.value           = SelfGrid.getRowColData(selno, 23);
	//fm.StarAgent1.value     = SelfGrid.getRowColData(selno, 24);
	
	
	fm.uWstate1.value       = uWstate;
	fm.CustomerIdea.value   = tCustomerIdea;
	
	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  
	document.all('fmtransact').value = "INSERT||SUBMITUW";
	fm.action="./WFEdorConfirmSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit(); 
}
*/
/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *  参数  ：  无
 *  返回值：  无
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
	mFlag = "0";
	//begin zbx 20110512
	fm.EdorAcceptNo.value	= PublicWorkPoolGrid.getRowColData(selno, 1);
	//end zbx 20110512
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 22);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 23);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 25);
	fm.ApplyOperator.value=PublicWorkPoolGrid.getRowColData(selno, 11);  //保全申请人
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("选择任务为空！");
	      return;
	}
	if(fm.ApplyOperator.value!=operator) 
	{
		  alert("保全确认人与申请人必须是同一人，请选择您申请的任务进行操作");
	      return false;
	
	}
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	
	fm.action = "../bq/MissionApply.jsp?Confirm=1";
	document.getElementById("fm").submit(); //提交
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
	
	mFlag = "0";
	//begin zbx 20110512
	fm.EdorAcceptNo.value	= AllGrid.getRowColData(selno, 1);
	//end zbx 20110512
	fm.MissionID.value = AllGrid.getRowColData(selno, 9);
	fm.SubMissionID.value = AllGrid.getRowColData(selno, 10);
	fm.ActivityID.value = AllGrid.getRowColData(selno, 11);
	fm.ApplyOperator.value=AllGrid.getRowColData(selno, 15);  //保全申请人
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("选择任务为空！");
	      return;
	}
	if(fm.ApplyOperator.value!=fm.Operator.value) 
	{
		  alert("保全确认人与申请人必须是同一人，请选择您申请的任务进行操作");
	      return false;
	
	}
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	
	fm.action = "../bq/MissionApply.jsp?Confirm=1";
	document.getElementById("fm").submit(); //提交
}

/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	
	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 解锁
	unlockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 解锁
	
  //alert(11);
	if (FlagStr == "Succ" ||FlagStr=='' || FlagStr==null )	
	{
	  if(mFlag == "1")
	  {
	  var iEdorAcceptNo = fm.EdorAcceptNo.value;
//    var rSQL = "select EdorType from lpedoritem where  EdorAcceptNo = '"+iEdorAcceptNo+"'";
    
    	var rSQL = "";
    	var sqlid11="WFEdorConfirmInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(iEdorAcceptNo);//指定传入的参数
		rSQL = mySql11.getString();
    
    var rResult;
    rResult = easyExecSql(rSQL, 1, 0,"","",1);
    if(rResult[0][0]!='RB'){
	  		  	//  alert(mFlag);
	     if (window.confirm("是否打印本次保全的批单?"))
         {
	         fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
	         fm.target="f1print";
	         document.getElementById("fm").submit();
         }
    }

     }
	 else
	 {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
		
	 }
	  	//刷新查询结果
	  	 // alert(2);
		//easyQueryClickAll();
		jQuery("#publicSearch").click();
		//easyQueryClickSelf();	
		jQuery("#privateSearch").click();
		checkPrintNotice();
		//add by jiaqiangli 2009-04-14 增加打印现金价值提示
		checkPrintCashValueTable();		
	}
    else
    {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }

}

//add by jiaqiangli 2009-03-17 xs需要提示打印现金价值表
 function checkPrintCashValueTable() {
 var tEdorAcceptNo=fm.EdorAcceptNo.value;
 // add by jiaqiangli 2009-04-19
 if (tEdorAcceptNo != null && tEdorAcceptNo != '') {
 // 规格说明书目前只有这四个
   	var sql = "" ;
//   	var sql = " select 1 from dual where exists (select 'X' from lpedoritem " +" where edoracceptno = '" + tEdorAcceptNo + "' and edortype in ( 'PA','FM','PT','XS'))";
    
    var sqlid3="WFEdorConfirmInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tEdorAcceptNo);//指定传入的参数
	sql = mySql3.getString();
    
    var sNeedGetFlag =  easyExecSql(sql, 1, 0);
    if (sNeedGetFlag == null) sNeedGetFlag = "";
    if (sNeedGetFlag=="1") {
          alert("提示：请打印新的现金价值表！");
          document.all("divCashValue").style.display = "";
    }
  }	
}
 
function PrintCashValueTable() {
	 var tEdorAcceptNo=fm.EdorAcceptNo.value;
	   	var sql = "" ;
//	   	var sql = " select contno from lpedoritem " +" where edoracceptno = '" + tEdorAcceptNo + "' and edortype in ( 'PA','FM','PT','XS')";
	    
	    var sqlid4="WFEdorConfirmInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(tEdorAcceptNo);//指定传入的参数
		sql = mySql4.getString();
		
	    var sContNo =  easyExecSql(sql, 1, 0);
	    if (sContNo == null) sContNo = "";
	    
	    fm.ContNo.value = sContNo;
	    fm.action = "./CashValuePrintSave.jsp";
	    fm.target = "f1print";
	    document.getElementById("fm").submit();
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
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
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
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
}

/*********************************************************************
 *  手动终止处理
 *  描述: 手动终止处理
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function doOverDue()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;

	var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}
 /*
function doOverDue()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID;
	fm.SubMissionID.value	= tSubMissionID;

	var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueSave.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}


function doJB()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}
	if(!confirm("确定要进行拒保操作么？")){
		
		return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tContNo = SelfGrid.getRowColData(selno, 2);
	var tMissionID = SelfGrid.getRowColData(selno, 9);
	var tSubMissionID = SelfGrid.getRowColData(selno, 10);
	var tActivityID = SelfGrid.getRowColData(selno, 11);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID; 
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	fm.ContNo.value	= tContNo;

	var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueAndNewItem.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}
*/
function doJB()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}
	if(!confirm("确定要进行拒保操作么？")){
		
		return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tContNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 21);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 24);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	fm.MissionID.value		= tMissionID; 
	fm.SubMissionID.value	= tSubMissionID;
	fm.ActivityID.value	= tActivityID;
	fm.ContNo.value	= tContNo;

	var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
	document.all('fmtransact').value = "INSERT||EDORCONFIRM";
	fm.action="./WFEdorOverDueAndNewItem.jsp";
	fm.target = "fraSubmit";
	document.getElementById("fm").submit();
}
 function checkPrintNotice()
{
	  
	  var tEdorAcceptNo=fm.EdorAcceptNo.value;
	  var sql = ""; 
//   	var sql = " select 1 from dual where exists (select 'X' from LJaGet " +
//   	" where othernotype = '10' and otherno = '" + tEdorAcceptNo + 
//   	"' and sumgetmoney <> 0)";

   	    //begin zbx 20110512
   	    if(tEdorAcceptNo==null || tEdorAcceptNo=="")
   	    {
   	    	return ;
   	    }
   	    //end zbx 20110512
   	    
   		var sqlid5="WFEdorConfirmInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(tEdorAcceptNo);//指定传入的参数
		sql = mySql5.getString();
		
    var sNeedGetFlag =  easyExecSql(sql, 1, 0);
    if (sNeedGetFlag == null) sNeedGetFlag = "";
    if (sNeedGetFlag=="1")
    {
          document.all("divGetNotice").style.display = "";
          alert("请打印付费通知书！");
    }	
}

function GetNotice()
{
    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var strSQL = "";
 //   var strSQL = "select prtseq from loprtmanager where code = 'BQ32' and otherno = '"+tEdorAcceptNo+"'";
    var sResult;
    
   		var sqlid6="WFEdorConfirmInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tEdorAcceptNo);//指定传入的参数
		strSQL = mySql6.getString();
		
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("查询付费通知书信息失败！");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp";
    fm.target = "f1print";
    document.getElementById("fm").submit();
}

function PayNotice()
{
    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var strSQL = "";
//    var strSQL = "select prtseq from loprtmanager where code = 'BQ31' and otherno = '"+tEdorAcceptNo+"'";
    var sResult;
    
    	var sqlid7="WFEdorConfirmInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(tEdorAcceptNo);//指定传入的参数
		strSQL = mySql7.getString();
		
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("查询收费通知书信息失败！");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp";
    fm.target = "f1print";
    document.getElementById("fm").submit();
}
/**
 * 判断是否显示付费通知书
 * XinYQ rewrote on 2007-05-18
 */
 function checkPayNotice()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	//查询保全核保意见
	initUWIdea();
	
    var QuerySQL, arrResult;
/*    QuerySQL = "select nvl(SumDuePayMoney, 0) "
             +   "from LJSPay  a "
             +  "where 1 = 1 "
             +     getWherePart("OtherNo", "EdorAcceptNo")
             +    " and OtherNoType = '10' "
             +    " and not exists (select 'X' from ljtempfee b where b.otherno='"+tEdorAcceptNo+"' and a.otherno=b.otherno)";
*/
    //alert(QuerySQL);
    try
    {
    	var sqlid8="WFEdorConfirmInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);//指定传入的参数
		mySql8.addSubPara(tEdorAcceptNo);//指定传入的参数
		QuerySQL = mySql8.getString();
		
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保全补退费信息出现异常！ ");
        return;
    }
    if (arrResult != null && arrResult[0][0] != "")
    {
        try
        {
            var sGetMoney = arrResult[0][0];
            var fGetMoney = parseFloat(sGetMoney);
            if (fGetMoney > 0)
            {
                document.all("divPayNotice").style.display = "block";
            } 
        }
        catch (ex) {}
    } //arrResult != null
    else
    {
    	document.all("divPayNotice").style.display = "none";
    }
    
    HighlightSelfRow();
}
 /*
function checkPayNotice()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	mFlag = "";

	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	fm.EdorAcceptNo.value	= tEdorAcceptNo;
	//查询保全核保意见
	initUWIdea();
	
    var QuerySQL, arrResult;
/*    QuerySQL = "select nvl(SumDuePayMoney, 0) "
             +   "from LJSPay  a "
             +  "where 1 = 1 "
             +     getWherePart("OtherNo", "EdorAcceptNo")
             +    " and OtherNoType = '10' "
             +    " and not exists (select 'X' from ljtempfee b where b.otherno='"+tEdorAcceptNo+"' and a.otherno=b.otherno)";
*/
    //alert(QuerySQL);
    /*
    try
    {
    	var sqlid8="WFEdorConfirmInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
		mySql8.addSubPara(tEdorAcceptNo);//指定传入的参数
		QuerySQL = mySql8.getString();
		
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保全补退费信息出现异常！ ");
        return;
    }
    if (arrResult != null && arrResult[0][0] != "")
    {
        try
        {
            var sGetMoney = arrResult[0][0];
            var fGetMoney = parseFloat(sGetMoney);
            if (fGetMoney > 0)
            {
                document.all("divPayNotice").style.display = "block";
            } 
        }
        catch (ex) {}
    } //arrResult != null
    else
    {
    	document.all("divPayNotice").style.display = "none";
    }
    
    HighlightSelfRow();
}*/

function initUWIdea(){
	var tSql="";
//	var tSql="select State,(select codename from ldcode where codetype='edorappstate' and code=state),UWIdea from LPAppUWMasterMain where EdorAcceptNo='"+fm.EdorAcceptNo.value+"'";
	
		var sqlid9="WFEdorConfirmInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
		tSql = mySql9.getString();
	
	var brr = easyExecSql(tSql);

    if ( brr )
    {
        fm.AppUWState.value = brr[0][0];
        fm.AppUWStateName.value = brr[0][1];
        fm.AppUWIdea.value = brr[0][2];
    }
}

function checkprint(){
	var tSql="";
//	var tSql="select nvl(count(*),'0') from LOPRTManager where otherno=(select contno from LPEdorMain where EdorAcceptNo='"+fm.EdorAcceptNo.value+"') and code='BQ84' and stateflag='0'";
	
		var sqlid10="WFEdorConfirmInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
		tSql = mySql10.getString();
	
	var brr = easyExecSql(tSql);

    if ( brr[0][0]>0 )
    {
        alert("该批单有未打印的拒保通知书，请先打印再进行确认！");
        return false;
    }
    
    return true;
}
