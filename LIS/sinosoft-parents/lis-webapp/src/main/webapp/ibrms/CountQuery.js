

// 更新记录：
// 更新人:
// 更新日期: 2008-9-17
// 更新原因/内容：

var showInfo;
var turnPage = new turnPageClass();
var tResourceName = 'ibrms.CountQuerySql';
// 性能查询
function AbilityQuery() {
	
		var sqlid1="CountQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(fm.Business.value);//指定传入的参数
		mySql1.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql1.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		
		var strSql1=mySql1.getString();	
	
	/*
	var sqlStr = " select t.ManageCom, t.business, count(contno), round(avg(time)/1000,1) from lrresultmain t where 1 = 1 "
			+ getWherePart("ManageCom", "ManageCom")
			+ getWherePart("Business", "Business")
			+ getWherePart('MakeDate', 'RuleStartDate', '>=')
			+ getWherePart('MakeDate', 'RuleEndDate', '<=')
	sqlStr += " group by ManageCom,business ";
*/
	turnPage.queryModal(strSql1, AbilityCountGrid);
}

// 性能详细查询
function QueryAbilityDetail() {
	tflag = "3";
	var arrReturn = new Array();
	var tSel = AbilityCountGrid.getSelNo();

	if (tSel == 0 || tSel == null)
		alert("请先选择一条记录");
	else {
		var ManageCom = AbilityCountGrid.getRowColData(tSel - 1, 1);
		var Business = AbilityCountGrid.getRowColData(tSel - 1, 2);
		var RuleStartDate = fm.RuleStartDate.value.trim();
		var RuleEndDate = fm.RuleEndDate.value.trim();
		if (ManageCom == "" && Business == "" && RuleEndDate == ""
				&& RuleEndDate == "")
			return;
		window.open("../ibrms/AbilityDetailCount.jsp?flag = " + tflag
				+ "&Business=" + Business + "&ManageCom=" + ManageCom
				+ "&RuleEndDate=" + RuleEndDate + "&RuleStartDate="
				+ RuleStartDate);
	}
}

// AbilityDetailcount.jsp 页面详细信息查询
function AbilityDetail() {
	/*var sqlStr1 = " select t.ManageCom, t.business, contno, time,t.resultflag,t.makedate from lrresultmain t where 1 = 1 "
			+ getWherePart("ManageCom", "ManageCom")
			+ getWherePart("Business", "Business")
			+ getWherePart('MakeDate', 'RuleStartDate', '>=')
			+ getWherePart('MakeDate', 'RuleEndDate', '<=')
	sqlStr1 += " order by ManageCom,business,time ";
*/
		var sqlid2="CountQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql2.addSubPara(fm.Business.value);//指定传入的参数
		mySql2.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql2.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		
		var strSql2=mySql2.getString();	

	//alert(sqlStr1);
	turnPage.queryModal(strSql2, AbilityDetailGrid);

}
// 性能查询导成EXCEL
function Ability_To_Excel() {

	if (AbilityCountGrid.mulLineCount == 0) {
		alert("请先进行查询.");
		return false;
	} else {
		turnPage.pageIndex = 1;
		turnPage.firstPage();
		easyQueryPrint(2, 'AbilityCountGrid', 'turnPage');
		return true;
	}
}

// 违反排名查询
function BreakQuery() {

	/*var sqlStr = "select t.ManageCom,t.business,c.templateid,(select b.rulename from  lrtemplate b  where b.id=c.templateid) ,count(t.resultflag) from  lrresultmain t ,lrresultdetail c where  1=1 and t.serialno = c.serialno and t.resultflag='0' "
			+ getWherePart("Business", "Business")
			+ getWherePart("RuleStartDate", "RuleStartDate")
			+ getWherePart("RuleEndDate", "RuleEndDate");
	sqlStr += "  group by t.ManageCom, t.business ,c.templateid  order by 5 desc ";*/
		var sqlid3="CountQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		//mySql3.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql3.addSubPara(fm.Business.value);//指定传入的参数
		mySql3.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql3.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		
		var strSql3=mySql3.getString();	
	 //alert(sqlStr);
	turnPage.queryModal(strSql3, BreakCountGrid);
}

// 违反排名详细查询
function BreakDetailQuery() {

	tflag = "4";
	var arrReturn = new Array();
	var tSel = BreakCountGrid.getSelNo();

	if (tSel == 0 || tSel == null)
		alert("请先选择一条记录");
	else {
		var ManageCom = BreakCountGrid.getRowColData(tSel - 1, 1);
		var Business = BreakCountGrid.getRowColData(tSel - 1, 2);
		var templateid = BreakCountGrid.getRowColData(tSel - 1, 3);
		// var Business = BreakCountGrid.getRowColData(tSel - 1,2);
		var RuleStartDate = fm.RuleStartDate.value.trim();
		var RuleEndDate = fm.RuleEndDate.value.trim();
		// alert(ManageCom);
		if (ManageCom == "" && Business == "" && RuleEndDate == ""
				&& RuleEndDate == "")
			return;
		window.open("../ibrms/BreakDetailCount.jsp?flag=" + tflag
				+ "&Business=" + Business + "&ManageCom=" + ManageCom
				+ "&RuleEndDate=" + RuleEndDate + "&RuleStartDate="
				+ RuleStartDate + "&templateid=" + templateid);
	}
}

function QueryBreakDetail() {
	// var sqlStr1 = " select t.ManageCom,t.business, b.id ,(select rulename
	// from lrtemplate where id = b.ruleid) ,c.serialno,c.version from
	// lrresultmain t ,lrresultdetail c where t.business=b.business and 1=1 and
	// t.serialno=c.serialno and t.resultflag='1' "
	// + getWherePart("t.ManageCom","ManageCom")
	// + getWherePart("b.Business","Business")
	// + getWherePart("RuleStartDate", "RuleStartDate")
	// + getWherePart("RuleEndDate", "RuleEndDate");
	// sqlStr1 += " order by t.ManageCom, t.business ";

	var sqlStr1 = "  select t.contno,c.templateid,(select b.rulename from lrtemplate b where '1225686038000' = '1225686038000' and b.id = c.templateid), c.version,c.result, t.makedate, t.maketime  from  lrresultmain t ,lrresultdetail c where  1=1 and t.serialno=c.serialno   and t.resultflag='0'  "
			+ getWherePart("ManageCom", "ManageCom")
			+ getWherePart("Business", "Business")
			+ getWherePart("templateid", "templateid")
			+ getWherePart("RuleStartDate", "RuleStartDate")
			+ getWherePart("RuleEndDate", "RuleEndDate");
	sqlStr1 += "  order by t.contno, t.makedate desc ";

		var sqlid4="CountQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(tResourceName); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql4.addSubPara(fm.Business.value);//指定传入的参数
		mySql4.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql4.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		
		var strSql4=mySql4.getString();	

//	alert(sqlStr1);
	turnPage.queryModal(strSql4, BreakDetailCountGrid);

}
// 违反排导成EXCEL

function Break_To_Excel() {

	if (BreakCountGrid.mulLineCount == 0) {
		alert("请先进行查询.");
		return false;
	} else {

		turnPage.pageIndex = 1;
		turnPage.firstPage();
		easyQueryPrint(2, 'BreakCountGrid', 'turnPage');

		return true;
	}
}

// 模板查询
//function prepareSQL(tableName) {
//	var sql = "select rulename, business,templatelevel,startdate,enddate,creator,state,valid,id,description,'" 
//			+ tableName + "' from ";
//	sql += tableName + " where 1=1 " + getWherePart("Business", "Business")
//			+ getWherePart("RuleStartDate", "RuleStartDate")
//			+ getWherePart("RuleEndDate", "RuleEndDate");
//	if (!!fm.RuleName.value.trim()) {
//		sql += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
//	}
//	if (!!fm.description.value.trim()) {
//		sql += " and description like '%" + fm.description.value.trim() + "%'";
//	}
//	return sql;
//}

function TemplateQuery() {
	try {
		var State = fm.IbrmsState.value.trim();
	} catch (e) {
		alert("状态位获取时出错！\n建议重新初始化页面或者找管理员！");
	}

	var sql = '';
	if (!(!!State)) {
		/*sql += "select id,version,rulename,sqlstatement,business,type,state,startdate,enddate,creator,modifier,approver,Deployer,MakeDate,AuthorDate,DeclDate,Valid,State,Description,'LRTemplateT' from LRTemplateT"
				+ " where 1=1 "
				+ getWherePart("Business", "Business")
				+ getWherePart("RuleStartDate", "RuleStartDate")
				+ getWherePart("RuleEndDate", "RuleEndDate");
		if (!!fm.RuleName.value.trim()) {
			sql += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
		}
		if (!!fm.description.value.trim()) {
			sql += " and description like '%" + fm.description.value.trim()
					+ "%'";
		}
		sql += " union all "
			+ "select id,version,rulename,sqlstatement,business,type,state,startdate,enddate,creator,modifier,approver,Deployer,MakeDate,AuthorDate,DeclDate,Valid,State,Description,'LRTemplate' from LRTemplate"
			+ " where 1=1 "
				+ getWherePart("Business", "Business")
				+ getWherePart("RuleStartDate", "RuleStartDate")
				+ getWherePart("RuleEndDate", "RuleEndDate");
		if (!!fm.RuleName.value.trim()) {
			sql += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
		}
		if (!!fm.description.value.trim()) {
			sql += " and description like '%" + fm.description.value.trim()
					+ "%'";
		}	
		sql +=" union all "
			+ "select id,version,rulename,sqlstatement,business,type,state,startdate,enddate,creator,modifier,approver,Deployer,MakeDate,AuthorDate,DeclDate,Valid,State,Description,'LRTemplateB' from LRTemplateB"
			+ " where 1=1 "
				+ getWherePart("Business", "Business")
				+ getWherePart("RuleStartDate", "RuleStartDate")
				+ getWherePart("RuleEndDate", "RuleEndDate");
		if (!!fm.RuleName.value.trim()) {
			sql += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
		}
		if (!!fm.description.value.trim()) {
			sql += " and description like '%" + fm.description.value.trim()
					+ "%'";
		}	
			sql += " order by id  ";
			*/
		var mySql5=new SqlClass();
		var sqlid5="CountQuerySql5";
		mySql5.setResourceName(tResourceName); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(fm.Business.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleName.value.trim());//指定传入的参数
		mySql5.addSubPara(fm.description.value.trim());//指定传入的参数
		mySql5.addSubPara(fm.Business.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleName.value.trim());//指定传入的参数
		mySql5.addSubPara(fm.description.value.trim());//指定传入的参数
		mySql5.addSubPara(fm.Business.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		mySql5.addSubPara(fm.RuleName.value.trim());//指定传入的参数
		mySql5.addSubPara(fm.description.value.trim());//指定传入的参数
		
		sql=mySql5.getString();	

	} else if (State == 1 || State == 2 || State == 3 || State == 4) {
	/*	sql += "select id,version,rulename,sqlstatement,business,type,state,startdate,enddate,creator,modifier,approver,Deployer,MakeDate,AuthorDate,DeclDate,Valid,State,Description,'LRTemplateT' from LRTemplateT"
				+ " where 1=1 "
				+ getWherePart("Business", "Business")
				+ getWherePart("RuleStartDate", "RuleStartDate")
				+ getWherePart("RuleEndDate", "RuleEndDate")
				+ getWherePart("State", "IbrmsState");
				if (!!fm.RuleName.value.trim()) {
			sql += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
		}
		if (!!fm.description.value.trim()) {
			sql += " and description like '%" + fm.description.value.trim()
					+ "%'";
		}
		sql += " order by id ";
				*/
		var mySql6=new SqlClass();
		var sqlid6="CountQuerySql6";
		mySql6.setResourceName(tResourceName); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(fm.Business.value);//指定传入的参数
		mySql6.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql6.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		mySql6.addSubPara(fm.RuleName.value.trim());//指定传入的参数
		mySql6.addSubPara(fm.description.value.trim());//指定传入的参数
		mySql6.addSubPara(fm.IbrmsState.value);//指定传入的参数
		sql=mySql6.getString();	
	} else if (State == 7) {
		//sql += prepareSQL("LRTemplate") + getWherePart("State", "IbrmsState")
	//			+ " order by id ";
		var mySql7=new SqlClass();
		var sqlid7="CountQuerySql7";
		mySql7.setResourceName(tResourceName); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(fm.Business.value);//指定传入的参数
		mySql7.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql7.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		mySql7.addSubPara(fm.RuleName.value.trim());//指定传入的参数
		mySql7.addSubPara(fm.description.value.trim());//指定传入的参数
		mySql7.addSubPara(fm.IbrmsState.value);//指定传入的参数
		sql=mySql7.getString();	
	} else if (State == 9) {
		//sql += prepareSQL("LRTemplateB") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var mySql8=new SqlClass();
		var sqlid8="CountQuerySql8";
		mySql8.setResourceName(tResourceName); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(fm.Business.value);//指定传入的参数
		mySql8.addSubPara(fm.RuleStartDate.value);//指定传入的参数
		mySql8.addSubPara(fm.RuleEndDate.value);//指定传入的参数
		mySql8.addSubPara(fm.RuleName.value.trim());//指定传入的参数
		mySql8.addSubPara(fm.description.value.trim());//指定传入的参数
		mySql8.addSubPara(fm.IbrmsState.value);//指定传入的参数
		sql=mySql8.getString();	
	} else {
		alert("状态位选择错误！\n请重新选择状态位！");
		return;
	}
	turnPage.queryModal(sql, QueryGrpGrid);
	// alert("sql end:"+sql);
}

// 模板详细查询
function RuleDetails() {
	var Selno = QueryGrpGrid.getSelNo()-1;
	if (Selno >=0) {
		var LRTemplate_ID = QueryGrpGrid.getRowColData(Selno, 1);
		var LRTemplateName=QueryGrpGrid.getRowColData(Selno, 20);
		if (!!LRTemplate_ID) {
			var url = "./RuleMake.jsp?flag=0"+"&LRTemplate_ID="
					+ LRTemplate_ID+"&LRTemplateName="+LRTemplateName;
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


function analyse_data_pol() {

	if (turnPage.queryAllRecordCount == 0) {
		alert("请先进行查询!");
		return false;
	} else {
		turnPage.pageIndex = 1;
		turnPage.firstPage();
		easyQueryPrint(2, 'QueryGrpGrid', 'turnPage');
		return true;
	}

}



var sFeatures = "status:0;help:0;close:0;scroll:0;dialogWidth:1200px;dialogHeight:800px;resizable=0";
              + "dialogLeft:0;dialogTop:0;";
var win_father=window;

function easyQueryPrint(vFlag,vMultiline,vTurnPage) {
    //urlStr：打印窗口URL和查询参数
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage;
    //window.showModalDialog(urlStr, win_father, sFeatures);
    var name='提示';   //网页名称，可为空; 
	var iWidth=1200;      //弹出窗口的宽度; 
	var iHeight=800;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

}