

// ���¼�¼��
// ������:
// ��������: 2008-9-17
// ����ԭ��/���ݣ�

var showInfo;
var turnPage = new turnPageClass();
var tResourceName = 'ibrms.CountQuerySql';
// ���ܲ�ѯ
function AbilityQuery() {
	
		var sqlid1="CountQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		
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

// ������ϸ��ѯ
function QueryAbilityDetail() {
	tflag = "3";
	var arrReturn = new Array();
	var tSel = AbilityCountGrid.getSelNo();

	if (tSel == 0 || tSel == null)
		alert("����ѡ��һ����¼");
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

// AbilityDetailcount.jsp ҳ����ϸ��Ϣ��ѯ
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
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		
		var strSql2=mySql2.getString();	

	//alert(sqlStr1);
	turnPage.queryModal(strSql2, AbilityDetailGrid);

}
// ���ܲ�ѯ����EXCEL
function Ability_To_Excel() {

	if (AbilityCountGrid.mulLineCount == 0) {
		alert("���Ƚ��в�ѯ.");
		return false;
	} else {
		turnPage.pageIndex = 1;
		turnPage.firstPage();
		easyQueryPrint(2, 'AbilityCountGrid', 'turnPage');
		return true;
	}
}

// Υ��������ѯ
function BreakQuery() {

	/*var sqlStr = "select t.ManageCom,t.business,c.templateid,(select b.rulename from  lrtemplate b  where b.id=c.templateid) ,count(t.resultflag) from  lrresultmain t ,lrresultdetail c where  1=1 and t.serialno = c.serialno and t.resultflag='0' "
			+ getWherePart("Business", "Business")
			+ getWherePart("RuleStartDate", "RuleStartDate")
			+ getWherePart("RuleEndDate", "RuleEndDate");
	sqlStr += "  group by t.ManageCom, t.business ,c.templateid  order by 5 desc ";*/
		var sqlid3="CountQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		//mySql3.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		
		var strSql3=mySql3.getString();	
	 //alert(sqlStr);
	turnPage.queryModal(strSql3, BreakCountGrid);
}

// Υ��������ϸ��ѯ
function BreakDetailQuery() {

	tflag = "4";
	var arrReturn = new Array();
	var tSel = BreakCountGrid.getSelNo();

	if (tSel == 0 || tSel == null)
		alert("����ѡ��һ����¼");
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
		mySql4.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		
		var strSql4=mySql4.getString();	

//	alert(sqlStr1);
	turnPage.queryModal(strSql4, BreakDetailCountGrid);

}
// Υ���ŵ���EXCEL

function Break_To_Excel() {

	if (BreakCountGrid.mulLineCount == 0) {
		alert("���Ƚ��в�ѯ.");
		return false;
	} else {

		turnPage.pageIndex = 1;
		turnPage.firstPage();
		easyQueryPrint(2, 'BreakCountGrid', 'turnPage');

		return true;
	}
}

// ģ���ѯ
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
		alert("״̬λ��ȡʱ����\n�������³�ʼ��ҳ������ҹ���Ա��");
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
		mySql5.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleName.value.trim());//ָ������Ĳ���
		mySql5.addSubPara(fm.description.value.trim());//ָ������Ĳ���
		mySql5.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleName.value.trim());//ָ������Ĳ���
		mySql5.addSubPara(fm.description.value.trim());//ָ������Ĳ���
		mySql5.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.RuleName.value.trim());//ָ������Ĳ���
		mySql5.addSubPara(fm.description.value.trim());//ָ������Ĳ���
		
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
		mySql6.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql6.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql6.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		mySql6.addSubPara(fm.RuleName.value.trim());//ָ������Ĳ���
		mySql6.addSubPara(fm.description.value.trim());//ָ������Ĳ���
		mySql6.addSubPara(fm.IbrmsState.value);//ָ������Ĳ���
		sql=mySql6.getString();	
	} else if (State == 7) {
		//sql += prepareSQL("LRTemplate") + getWherePart("State", "IbrmsState")
	//			+ " order by id ";
		var mySql7=new SqlClass();
		var sqlid7="CountQuerySql7";
		mySql7.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql7.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql7.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		mySql7.addSubPara(fm.RuleName.value.trim());//ָ������Ĳ���
		mySql7.addSubPara(fm.description.value.trim());//ָ������Ĳ���
		mySql7.addSubPara(fm.IbrmsState.value);//ָ������Ĳ���
		sql=mySql7.getString();	
	} else if (State == 9) {
		//sql += prepareSQL("LRTemplateB") + getWherePart("State", "IbrmsState")
		//		+ " order by id ";
		var mySql8=new SqlClass();
		var sqlid8="CountQuerySql8";
		mySql8.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(fm.Business.value);//ָ������Ĳ���
		mySql8.addSubPara(fm.RuleStartDate.value);//ָ������Ĳ���
		mySql8.addSubPara(fm.RuleEndDate.value);//ָ������Ĳ���
		mySql8.addSubPara(fm.RuleName.value.trim());//ָ������Ĳ���
		mySql8.addSubPara(fm.description.value.trim());//ָ������Ĳ���
		mySql8.addSubPara(fm.IbrmsState.value);//ָ������Ĳ���
		sql=mySql8.getString();	
	} else {
		alert("״̬λѡ�����\n������ѡ��״̬λ��");
		return;
	}
	turnPage.queryModal(sql, QueryGrpGrid);
	// alert("sql end:"+sql);
}

// ģ����ϸ��ѯ
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
			alert("����ȡ�Ĺ���ı�ʶΪ�գ����ȡ��ȷ��-->>���� ��ʶ��");
			return;
		}
	} else {
		alert("����û��ѡ��һ�У���ѡ��һ������");
		return;
	}
}


function analyse_data_pol() {

	if (turnPage.queryAllRecordCount == 0) {
		alert("���Ƚ��в�ѯ!");
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
    //urlStr����ӡ����URL�Ͳ�ѯ����
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage;
    //window.showModalDialog(urlStr, win_father, sFeatures);
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1200;      //�������ڵĿ��; 
	var iHeight=800;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

}