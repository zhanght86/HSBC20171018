var turnPage = new turnPageClass();

var tResourceName = 'ibrms.RuleSearchSql';
function search() {
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
				+ " union all " + prepareSQL("LRTemplateB") + " order by id  ";*/
		var sqlid2="RuleSearchSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(document.all("Business").value);//指定传入的参数
		mySql2.addSubPara(document.all("RuleName").value);
		mySql2.addSubPara(document.all("description").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		
		mySql2.addSubPara(document.all("Business").value);//指定传入的参数
		mySql2.addSubPara(document.all("RuleName").value);
		mySql2.addSubPara(document.all("description").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		
		mySql2.addSubPara(document.all("Business").value);//指定传入的参数
		mySql2.addSubPara(document.all("RuleName").value);
		mySql2.addSubPara(document.all("description").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		
		sql=mySql2.getString();	

	} else if (State == 0 || State == 1 || State == 2 || State == 3
			|| State == 4) {
		//sql += prepareSQL("LRTemplateT") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid3="RuleSearchSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all("Business").value);//指定传入的参数
		mySql3.addSubPara(document.all("RuleName").value);
		mySql3.addSubPara(document.all("description").value.trim());
		mySql3.addSubPara(document.all("BOM").value.trim());
		mySql3.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		mySql3.addSubPara(document.all("IbrmsState").value);//指定传入的参数
		sql=mySql3.getString();	
	} else if (State == 7) {
		//sql += prepareSQL("LRTemplate") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid4="RuleSearchSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(tResourceName); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(document.all("Business").value);//指定传入的参数
		mySql4.addSubPara(document.all("RuleName").value);
		mySql4.addSubPara(document.all("description").value.trim());
		mySql4.addSubPara(document.all("BOM").value.trim());
		mySql4.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		mySql4.addSubPara(document.all("IbrmsState").value);//指定传入的参数
		sql=mySql4.getString();	
	} else if (State == 9) {
		//sql += prepareSQL("LRTemplateB") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid5="RuleSearchSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(tResourceName); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(document.all("Business").value);//指定传入的参数
		mySql5.addSubPara(document.all("RuleName").value);
		mySql5.addSubPara(document.all("description").value.trim());
		mySql5.addSubPara(document.all("BOM").value.trim());
		mySql5.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		mySql5.addSubPara(document.all("IbrmsState").value);//指定传入的参数
		sql=mySql5.getString();	
	} else {
		alert("状态位选择错误！\n请重新选择状态位！");
		return;
	}
	turnPage.queryModal(sql, QueryGrpGrid);
}

//function prepareSQL(tableName) {
//	var sql = "select rulename, business,templatelevel,startdate,enddate,creator,state,description,id,'"
//			+ tableName + "' from ";
//	sql += tableName + " where 1=1 " + getWherePart("Business", "Business")
//	if (!!fm.RuleName.value.trim()) {
//		sql += " and rulename like '%" + fm.RuleName.value.trim() + "%'";
//	}
//	if (!!fm.description.value.trim()) {
//		sql += " and description like '%" + fm.description.value.trim() + "%'";
//	}
//	if (!!fm.BOM.value.trim()) {
//		sql += " and instr(BOMS,'" + fm.BOM.value.trim() + "')>0";
//		if (!!fm.BOMItem.value.trim()) {
//			sql += " and instr(SQLParameter,'" + fm.BOM.value.trim()+"."+fm.BOMItem.value.trim() + "')>0";
//		}
//	}
//	
//	
//	return sql;
//}

function details() {
	var Selno = QueryGrpGrid.getSelNo() - 1;
	if (Selno >= 0) {
		var LRTemplate_ID = QueryGrpGrid.getRowColData(Selno, 9);
		var LRTemplateName = QueryGrpGrid.getRowColData(Selno, 10);
		if (!!LRTemplate_ID) {
			var url = "./RuleMake.jsp?flag=0" + "&LRTemplate_ID="
					+ LRTemplate_ID + "&LRTemplateName=" + LRTemplateName;
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

function afterCodeSelect(cCodeName, Field) {
	try {
		if (cCodeName == 'BOM') {
			var BOMValue = fm.BOM.value;
			//var fitemsql = "select name,cnname from lrbomitem where bomname='"
			//		+ BOMValue + "'";
			
			var mySql6=new SqlClass();
			mySql6.setResourceName(tResourceName); //指定使用的properties文件名
			mySql6.setSqlId(sqlid6);//指定使用的Sql的id
			mySql6.addSubPara(BOMValue);//指定传入的参数
		  var fitemsql=mySql6.getString();	

			var tem = easyQueryVer3(fitemsql, 1, 1, 1);
			if (tem == false) {
				fm.BOMItem.CodeData = '';
				alert("请先选择BOM");
			} else {
				fm.BOMItem.CodeData = tem;
			}
		}
	} catch (ex) {

	}
}