//更新记录： 
//更新人:  
//更新日期:  2008-9-17 
//更新原因/内容：

var showInfo;
var turnPage = new turnPageClass();
var tResourceName = 'ibrms.RuleQuerySql';
// ycf add
function easyQuery(flag) {
	switch (flag) {
	case 2: {
		CopyQuery();
		break;
	}// 复制
	case 3: {
		TestQuery();
		break;
	}// 测试
	case 4: {
		ModifyQuery();
		break;
	}// 修改
	case 5: {
		ApproveQuery();
		break;
	}// 审批
	case 6: {
		DeployQuery();
		break;
	}// 部署
	case 7: {
		DropQuery();
		break;
	}// 作废
	case 8: {
		CopyQuery();
		break;
	}// 查询
	case 9: {
		PrintQuery();
		break;
	}// 打印

	}
}

function DropQuery() {
/*	var sqlStr = "select t.rulename, t.business,t.templatelevel,t.startdate,t.enddate,t.creator,t.state,t.description,t.id ,'LRTemplate' from LRTemplate t where 1=1 "
			+ getWherePart("Business", "Business")
			+ getWherePart("RuleStartDate", "RuleStartDate")
			+ getWherePart("RuleEndDate", "RuleEndDate")
			+ getWherePart("State", "IbrmsState");

	if (fm.RuleName.value.trim() != null && fm.RuleName.value.trim() != '') {
		sqlStr += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
	}
	if (fm.description.value.trim() != null
			&& fm.description.value.trim() != '') {
		sqlStr += " and description like '%" + fm.description.value.trim()
				+ "%'";
	}
	sqlStr += " order by rulename ";
	*/
	//alert(sqlStr);
		var sqlid1="RuleQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all("Business").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("IbrmsState").value);
		mySql1.addSubPara(document.all("RuleName").value);
		mySql1.addSubPara(document.all("description").value);
		
	  var strSql=mySql1.getString();	
	
	
	turnPage.queryModal(strSql, QueryGrpGrid);
}

function TestQuery(){
	/*var sqlStr = "select t.rulename, t.business,t.templatelevel,t.startdate,t.enddate,t.creator,t.state,t.description,t.id ,'LRTemplatet' from LRTemplatet t where state = '1' "
			+ getWherePart("Business", "Business")
			+ getWherePart("RuleStartDate", "RuleStartDate")
			+ getWherePart("RuleEndDate", "RuleEndDate");
			//+ getWherePart("State", "IbrmsState");

	if (fm.RuleName.value.trim() != null && fm.RuleName.value.trim() != '') {
		sqlStr += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
	}
	if (fm.description.value.trim() != null
			&& fm.description.value.trim() != '') {
		sqlStr += " and description like '%" + fm.description.value.trim()
				+ "%'";
	}
	sqlStr += " order by id DESC ";
	*/
		var sqlid2="RuleQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(document.all("Business").value);//指定传入的参数
		mySql2.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql2.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		//mySql2.addSubPara(document.all("IbrmsState").value);
		mySql2.addSubPara(document.all("RuleName").value);
		mySql2.addSubPara(document.all("description").value);
		
	  var strSql2=mySql2.getString();	
	  
	// alert(sqlStr);
	turnPage.queryModal(strSql2, QueryGrpGrid);
}

function DeployQuery() {
/*	var sqlStr = "select t.rulename, t.business,t.templatelevel,t.startdate,t.enddate,t.creator,t.state,t.description,t.id ,'LRTemplatet' from LRTemplatet t where 1=1 "
			+ getWherePart("Business", "Business")
			+ getWherePart("RuleStartDate", "RuleStartDate")
			+ getWherePart("RuleEndDate", "RuleEndDate")
			+ getWherePart("State", "IbrmsState");

	if (fm.RuleName.value.trim() != null && fm.RuleName.value.trim() != '') {
		sqlStr += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
	}
	if (fm.description.value.trim() != null
			&& fm.description.value.trim() != '') {
		sqlStr += " and description like '%" + fm.description.value.trim()
				+ "%'";
	}
	sqlStr += " order by rulename ";
	*/
		var sqlid3="RuleQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all("Business").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("IbrmsState").value);
		mySql3.addSubPara(document.all("RuleName").value);
		mySql3.addSubPara(document.all("description").value);
		
	  var strSq3=mySql3.getString();	
	  
	//alert(sqlStr);
	turnPage.queryModal(strSq3, QueryGrpGrid);
}

function ApproveQuery() {
	/*var sqlStr = "select t.rulename, t.business,t.templatelevel,t.startdate,t.enddate,t.creator,t.state,t.description,t.id ,'LRTemplateT' from LRTemplatet t where 1=1 "
			+ getWherePart("Business", "Business")
			+ getWherePart("RuleStartDate", "RuleStartDate")
			+ getWherePart("RuleEndDate", "RuleEndDate")
			+ getWherePart("State", "IbrmsState");

	if (fm.RuleName.value.trim() != null && fm.RuleName.value.trim() != '') {
		sqlStr += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
	}
	if (fm.description.value.trim() != null
			&& fm.description.value.trim() != '') {
		sqlStr += " and description like '%" + fm.description.value.trim()
				+ "%'";
	}
	sqlStr += " order by rulename ";
	*/
		var sqlid4="RuleQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(tResourceName); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(document.all("Business").value);//指定传入的参数
		mySql4.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql4.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql4.addSubPara(document.all("IbrmsState").value);
		mySql4.addSubPara(document.all("RuleName").value);
		mySql4.addSubPara(document.all("description").value);
		
		var strSq4=mySql4.getString();	
	// alert(sqlStr);
	turnPage.queryModal(strSq4, QueryGrpGrid);
}

function ModifyQuery() {
	try {
		var State = fm.IbrmsState.value.trim();
	} catch (e) {
		alert("状态位获取时出错！\n建议重新初始化页面或者找管理员！");
	}

	var sql = '';
	if (!(!!State)) {
	//	sql += prepareSQL("LRTemplateT");
	//	sql += " union all "
//				+ prepareSQL("LRTemplate")
//				+ " and not exists (select id from LRTemplateT where LRTemplate.id=LRTemplateT.id )"
//				+ " order by id  ";
		var sqlid5="RuleQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(tResourceName); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(document.all("Business").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleName").value);
		mySql5.addSubPara(document.all("description").value);
		mySql5.addSubPara(document.all("Business").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleName").value);
		mySql5.addSubPara(document.all("description").value);
		
	   sql=mySql5.getString();			
				

	} else if (State == 0 || State == 1 || State == 2 || State == 3
			|| State == 4) {
		//sql += prepareSQL("LRTemplateT") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
			var sqlid3="RuleQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all("Business").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("IbrmsState").value);
		mySql3.addSubPara(document.all("RuleName").value);
		mySql3.addSubPara(document.all("description").value);
		
	   sql=mySql3.getString();	
	  
	} else if (State == 7) {
		//sql += prepareSQL("LRTemplate") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid1="RuleQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all("Business").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("IbrmsState").value);
		mySql1.addSubPara(document.all("RuleName").value);
		mySql1.addSubPara(document.all("description").value);
		
	  sql=mySql1.getString();	
	}
	else if (State == 9) {
		alert("不能对历史规则进行修改！");
		return;
	} else {
		alert("状态位选择错误！\n请重新选择状态位！");
		return;
	}
	turnPage.queryModal(sql, QueryGrpGrid);
}

function PrintQuery()
{
	try {
		var State = fm.IbrmsState.value.trim();
	} catch (e) {
		alert("状态位获取时出错！\n建议重新初始化页面或者找管理员！");
	}

	var sql = '';
	if (!(!!State)) {
		/*sql += prepareSQL("LRTemplateT");
		sql += " union all "
				+ prepareSQL("LRTemplate")
				+ " and not exists (select id from LRTemplateT where LRTemplate.id=LRTemplateT.id )"
				+ " order by id  ";*/
		var sqlid5="RuleQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(tResourceName); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(document.all("Business").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleName").value);
		mySql5.addSubPara(document.all("description").value);
		mySql5.addSubPara(document.all("Business").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleName").value);
		mySql5.addSubPara(document.all("description").value);
		
	   sql=mySql5.getString();			

	} else if (State == 0 || State == 1 || State == 2 || State == 3
			|| State == 4) {
		/*sql += prepareSQL("LRTemplateT") + getWherePart("State", "IbrmsState")
				+ " order by id ";*/
		var sqlid3="RuleQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all("Business").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("IbrmsState").value);
		mySql3.addSubPara(document.all("RuleName").value);
		mySql3.addSubPara(document.all("description").value);
		
	   sql=mySql3.getString();	
	} else if (State == 7) {
		//sql += prepareSQL("LRTemplate") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid1="RuleQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all("Business").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("IbrmsState").value);
		mySql1.addSubPara(document.all("RuleName").value);
		mySql1.addSubPara(document.all("description").value);
		
	  sql=mySql1.getString();	
	}
	else if (State == 9) {
		alert("不能对历史规则进行修改！");
		return;
	} else {
		alert("状态位选择错误！\n请重新选择状态位！");
		return;
	}
	turnPage.queryModal(sql, QueryGrpGrid);
}
//function prepareSQL(tableName) {
//	var sql = "select rulename, business,templatelevel,startdate,enddate,creator,state,description,id,'"+tableName+"' from ";
//	sql += tableName + " where 1=1 " + getWherePart("Business", "Business")
//			+ getWherePart("StartDate", "RuleStartDate")
//			+ getWherePart("EndDate", "RuleEndDate");
//	if (!!fm.RuleName.value.trim()) {
//		sql += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
//	}
//	if (!!fm.description.value.trim()) {
//		sql += " and description like '%" + fm.description.value.trim() + "%'";
//	}
//	return sql;
//}

function CopyQuery() {
	try {
		var State = fm.IbrmsState.value.trim();
	} catch (e) {
		alert("状态位获取时出错！\n建议重新初始化页面或者找管理员！");
	}

	var sql = '';
	if (!(!!State)) {
	/*	sql += prepareSQL("LRTemplateT");
		sql += " union all "
				+ prepareSQL("LRTemplate")
				+ " and not exists (select id from LRTemplateT where LRTemplate.id=LRTemplateT.id )"
				+ " union all " + prepareSQL("LRTemplateB") + " order by id  DESC";
*/
		var sqlid6="RuleQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(tResourceName); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(document.all("Business").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleName").value);
		mySql6.addSubPara(document.all("description").value);
		
		mySql6.addSubPara(document.all("Business").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleName").value);
		mySql6.addSubPara(document.all("description").value);
		
		mySql6.addSubPara(document.all("Business").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql6.addSubPara(document.all("RuleName").value);
		mySql6.addSubPara(document.all("description").value);
		sql=mySql6.getString();	
	} else if (State == 1 || State == 2 || State == 3
			|| State == 4) {
		//sql += prepareSQL("LRTemplateT") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid3="RuleQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all("Business").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql3.addSubPara(document.all("IbrmsState").value);
		mySql3.addSubPara(document.all("RuleName").value);
		mySql3.addSubPara(document.all("description").value);
		
	   sql=mySql3.getString();	
	} else if (State == 7) {
		//sql += prepareSQL("LRTemplate") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid1="RuleQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all("Business").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql1.addSubPara(document.all("IbrmsState").value);
		mySql1.addSubPara(document.all("RuleName").value);
		mySql1.addSubPara(document.all("description").value);
		
	  sql=mySql1.getString();	
	} else if (State == 9) {
		//sql += prepareSQL("LRTemplateB") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid7="RuleQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(tResourceName); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(document.all("Business").value);//指定传入的参数
		mySql7.addSubPara(document.all("RuleStartDate").value);//指定传入的参数
		mySql7.addSubPara(document.all("RuleEndDate").value);//指定传入的参数
		mySql7.addSubPara(document.all("IbrmsState").value);
		mySql7.addSubPara(document.all("RuleName").value);
		mySql7.addSubPara(document.all("description").value);
		
	  sql=mySql7.getString();	
	} else {
		alert("状态位选择错误！\n请重新选择状态位！");
		return;
	}
	turnPage.queryModal(sql, QueryGrpGrid);
}

function RuleDetails() {
	var Selno = QueryGrpGrid.getSelNo()-1;
	if (Selno >=0) {
		var LRTemplate_ID = QueryGrpGrid.getRowColData(Selno, 9);
		var LRTemplateName=QueryGrpGrid.getRowColData(Selno, 10);
		//tongmeng 2010-12-17 add
		var BusinessNo = QueryGrpGrid.getRowColData(Selno, 12);
		//tongmeng 2011-06-13 add
		var RuleName =  QueryGrpGrid.getRowColData(Selno, 1);
		if (!!LRTemplate_ID) {
			var url = "";
			if(BusinessNo!='1')
			{
				 url = "./RuleMake.jsp?flag=0"+"&LRTemplate_ID="
					+ LRTemplate_ID+"&LRTemplateName="+LRTemplateName+"&RuleName="+RuleName;
			}
			else
			{
				url = "./RuleMakeNew.jsp?flag=0"+"&LRTemplate_ID="
					+ LRTemplate_ID+"&LRTemplateName="+LRTemplateName+"&RuleName="+RuleName;
			}
			window.open(url);
		} else {
			alert("您获取的规则的标识为空，请获取正确的-->>规则 标识！");
			return;
		}
	} else {
		alert("您还没有选中一行，请选择一条规则！");
		return;
	}
}

function approve() {
	fm.BtnFlag.value = "4";
	submitForm();
	ApproveQuery();
   // easyQuery();
}

function unapprove() {
	
	fm.BtnFlag.value = "2";
	submitForm();
	ApproveQuery();
//	 easyQuery();
}

function deploy() {
	fm.BtnFlag.value = "7";
	// alert(fm.BtnFlag.value);
	submitForm();
//	easyQuery();
}

function drop() {
	fm.BtnFlag.value = "9"; // alert(BtnFlag);
	submitForm();
	DropQuery();
	// easyQuery();
}

// 提交，保存按钮对应操作
function submitForm(bTest) {
	// 提交前的校验
	// alert(fm.BtnFlag.value);
	if (!beforeSubmit())
		return false;
	if(!bTest)
		fm.action="./RuleQuerySave.jsp";
	//else
		
	if(flag==2||flag==4)
	{
		return false;
	}
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//fm.BtnFlag.value = BtnFlag;
	//if (fm.BtnFlag.value == "") {
	//	alert("操作控制数据丢失！");
	//}
	document.getElementById("fm").submit(); // 提交
//	easyQuery();
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var showStr = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ showStr;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	mOperate = ""; 
	easyQuery(flag); 
}

// 提交前的校验、计算
function beforeSubmit() {
	// 提交前对计划信息MulLine中数据合法性进行校验
	var PrjSelno = QueryGrpGrid.getSelNo();
	if (PrjSelno == 0) {
		alert("请您先选择一条规则!");
		return false;
	}
	return true;
}

function testRule(){
	var Selno = QueryGrpGrid.getSelNo()-1;
	if (Selno >= 0) {
		var LRTemplate_ID = QueryGrpGrid.getRowColData(Selno, 9);
	
		if (!!LRTemplate_ID) {
			var url = "./ruleTest.jsp?template="
					+ LRTemplate_ID+"&javaClass=com.sinosoft.ibrms.RuleUI&method=AutoUWIndUIForTest";

			var win = window.open(url,"_blank", 
		            "resizable=yes,left=0,top=0,width="+(screen.availWidth - 10) +",height=" + (screen.availHeight - 30));
			//window.location.href = url;
			//win.foucs();
		} else {
			alert("您获取的规则的标识为空，请获取正确的-->>规则 标识！");
			return;
		}
	} else {
		alert("您还没有选中一行，请选择一条规则！");
		return;
	}
}
//测试通过
function testApprove(flag){
   var selectNO =  QueryGrpGrid.getSelNo()-1; 
   if(selectNO>=0){
      var LRTemplate_ID = QueryGrpGrid.getRowColData(selectNO, 9);
       fm.TemplateId.value =  LRTemplate_ID;
           
      // document.getElementById("fm").submit();
	   fm.action = "RuleTestApprove.jsp?control="+flag;  
	  // fm.BtnFlag.value = "4";
	 //  alert(4);
	   submitForm(true);
       TestQuery();
       return;
   }else{
       alert("您还没有选中一行，请选择一条规则！");
	   return;
   }  
   
}

function modify() {
	var Selno = QueryGrpGrid.getSelNo()-1;
	if (Selno >= 0) {
		var LRTemplate_ID = QueryGrpGrid.getRowColData(Selno, 9);
		var LRTemplateName=QueryGrpGrid.getRowColData(Selno, 10);
		if (!!LRTemplate_ID) {
			var url = "./RuleInfor.jsp?flag=4"+ "&LRTemplate_ID="
					+ LRTemplate_ID+"&LRTemplateName="+LRTemplateName;
			window.location.href = url;
		} else {
			alert("您获取的规则的标识为空，请获取正确的-->>规则 标识！");
			return;
		}
	} else {
		alert("您还没有选中一行，请选择一条规则！");
		return;
	}
}
function copy()
{
	var Selno = QueryGrpGrid.getSelNo()-1;
	if (Selno >= 0) {
		var LRTemplate_ID = QueryGrpGrid.getRowColData(Selno, 9);
		var LRTemplateName=QueryGrpGrid.getRowColData(Selno, 10);
		if (!!LRTemplate_ID) {
			var url = "./RuleInfor.jsp?flag=2"
				     + "&LRTemplate_ID="+ LRTemplate_ID
				     +"&LRTemplateName="+LRTemplateName;
			//alert("LRTemplateName::"+LRTemplateName);
			window.location.href = url;
		} else {
			alert("您获取的规则的标识为空，请获取正确的-->>规则 标识！");
			return;
		}
	} else {
		alert("您还没有选中一行，请选择一条规则！");
		return;
	}
}