var mainSQL = '';
var detailSQL = '';
var tResourceName = 'ibrms.RuleResultSql';
function queryMain() {
	//var sql = "select SerialNo,Business,ContNo,round(Time/1000,2),ResultFlag,Operator,MakeDate,MakeTime,ManageCom from LRResultMain where 1=1 ";

	//sql += getWherePart("ManageCom", "ManageCom")
	//		+ getWherePart("Business", "Business");
	try {

		var startDate = fm.RecordStartDate.value.trim();
		var endDate = fm.RecordEndDate.value.trim();
	//	if (!!startDate) {
	//		sql += ' and MakeDate>=' + startDate;
	//	}
	//	if (!!endDate) {
	//		sql += ' and MakeDate>=' + endDate;
	//	}

		var sqlid1="RuleResultSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara( fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara( fm.Business.value);//ָ������Ĳ���
		mySql1.addSubPara( fm.RecordStartDate.value);//ָ������Ĳ���
		mySql1.addSubPara( fm.RecordEndDate.value);//ָ������Ĳ���
		
		var strSql1=mySql1.getString();	
		 
		turnPage.queryModal(strSql1, MainGrid);
		mainSQL = strSql1;
	} catch (e) {
		alert("��ѯ��Ϣ��ȡ����");
	}
}

function queryDetails() {
	var row = MainGrid.getSelNo();
//	var sql = "select SerialNo,No,TemplateId,Version,RuleId,Result from LRResultDetail";
	if (row>0) {
		var serialNo = MainGrid.getRowColData(row-1,1);
		//sql +=" where SerialNo like'"+serialNo+"'";		
		var sqlid2="RuleResultSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(serialNo);//ָ������Ĳ���
	
		var strSql2=mySql2.getString();	
		
		turnPage2.queryModal(strSql2, DetailGrid);
		detailSQL = strSql2;	
		
	}	
	
}

function getData(flag) {
	var sql = '';
	var reData;
	if (flag == 1) {
		sql = mainSQL;
	}
	if (flag == 2) {
		sql = detailSQL;
	}
	var result = easyQueryVer3(sql);
	reData = decodeEasyQueryResult(result);

	return reData;
}

function analyse_data_pol(type) {
	var ob;
	var tp;
	if (type == 'Main'){
		ob = "MainGrid";
		tp = turnPage;
	}else{
		ob = "DetailGrid";
		tp = turnPage2;
	}
	if (tp.queryAllRecordCount == 0) {
		alert("���Ƚ��в�ѯ!");
		return false;
	} else {		
		tp.pageIndex = 1;		
		tp.firstPage();
		easyQueryPrint(2, ob, ob=="MainGrid"?"turnPage":"turnPage2");	
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

	showInfo.focus();
}