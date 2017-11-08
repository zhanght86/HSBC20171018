var turnPage = new turnPageClass();

var tResourceName = 'ibrms.RuleSearchSql';
function search() {
	try {
		var State = fm.IbrmsState.value.trim();
	} catch (e) {
		alert("״̬λ��ȡʱ����\n�������³�ʼ��ҳ������ҹ���Ա��");
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
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(document.all("Business").value);//ָ������Ĳ���
		mySql2.addSubPara(document.all("RuleName").value);
		mySql2.addSubPara(document.all("description").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		
		mySql2.addSubPara(document.all("Business").value);//ָ������Ĳ���
		mySql2.addSubPara(document.all("RuleName").value);
		mySql2.addSubPara(document.all("description").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim());
		mySql2.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		
		mySql2.addSubPara(document.all("Business").value);//ָ������Ĳ���
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
		mySql3.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(document.all("Business").value);//ָ������Ĳ���
		mySql3.addSubPara(document.all("RuleName").value);
		mySql3.addSubPara(document.all("description").value.trim());
		mySql3.addSubPara(document.all("BOM").value.trim());
		mySql3.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		mySql3.addSubPara(document.all("IbrmsState").value);//ָ������Ĳ���
		sql=mySql3.getString();	
	} else if (State == 7) {
		//sql += prepareSQL("LRTemplate") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid4="RuleSearchSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(document.all("Business").value);//ָ������Ĳ���
		mySql4.addSubPara(document.all("RuleName").value);
		mySql4.addSubPara(document.all("description").value.trim());
		mySql4.addSubPara(document.all("BOM").value.trim());
		mySql4.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		mySql4.addSubPara(document.all("IbrmsState").value);//ָ������Ĳ���
		sql=mySql4.getString();	
	} else if (State == 9) {
		//sql += prepareSQL("LRTemplateB") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var sqlid5="RuleSearchSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(document.all("Business").value);//ָ������Ĳ���
		mySql5.addSubPara(document.all("RuleName").value);
		mySql5.addSubPara(document.all("description").value.trim());
		mySql5.addSubPara(document.all("BOM").value.trim());
		mySql5.addSubPara(document.all("BOM").value.trim()+'.'+document.all("BOMItem").value.trim());
		mySql5.addSubPara(document.all("IbrmsState").value);//ָ������Ĳ���
		sql=mySql5.getString();	
	} else {
		alert("״̬λѡ�����\n������ѡ��״̬λ��");
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
			alert("����ȡ�Ĺ���ı�ʶΪ�գ����ȡ��ȷ��-->>���� ��ʶ��");
			return;
		}
	} else {
		alert("����û��ѡ��һ�У���ѡ��һ������");
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
			mySql6.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
			mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
			mySql6.addSubPara(BOMValue);//ָ������Ĳ���
		  var fitemsql=mySql6.getString();	

			var tem = easyQueryVer3(fitemsql, 1, 1, 1);
			if (tem == false) {
				fm.BOMItem.CodeData = '';
				alert("����ѡ��BOM");
			} else {
				fm.BOMItem.CodeData = tem;
			}
		}
	} catch (ex) {

	}
}