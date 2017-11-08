var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;
var tResourceName="certify.SysCertTakeBackWorkPoolSql";
function easyQueryClick() {
	// 初始化表格
	initGrpGrid();
	//机构控制<如果没有选择管理机构则使用登陆时的登陆机构>
	if(trim(fm.qManageCom.value)== "") {
		alert("请输入管理机构！");
		return false;
	}
//	if(trim(fm.PrtNo.value)==""&&trim(fm.ContNo.value)==""&&trim(fm.AgentCode.value)==""
//		&&trim(fm.qManageCom.value)==""){
//		alert("请至少输入一个查询条件");
//		return false;
//	}
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
//	    strSQL = "select prtno,contno,riskcode,appntno,appntname,managecom,agentcode,"
//	    			+ " (select name from laagent b where agentcode = a.agentcode),"
//	    			+ " (select makedate || '' || maketime from es_doc_main where subtype = 'UR301'"
//	    			+ " and doccode = a.prtno) A"
//	    			+ " from lcpol a where polno=mainpolno"
//	    			+ " and not "
//	    			+ " exists (select 1 from lzsyscertify c where c.certifycode='9995' and stateflag<>'1'"
//	    			+ " and c.certifyno=a.contno)"
//	    			+ " and exists(select 1 from es_doc_main d where d.doccode=a.prtno and d.subtype='UR301'" 
//	    			+ getWherePart('d.makedate','ScanDate',strOperate)
//	    			+" )"
//	    			+ " and grpcontno='00000000000000000000'"
//	    			+ getWherePart('a.prtno','PrtNo',strOperate)
//	    			+ getWherePart('a.contno','ContNo',strOperate)
//	    			+ getWherePart('a.managecom','ManageCom',strOperate)
//	    			+ getWherePart('a.agentcode','AgentCode',strOperate)
//	    			+ " order by A";
	    /*strSQL = "select prtno,contno,"
	    			+ " (select riskcode from lcpol where prtno=a.prtno and mainpolno=polno and rownum=1),"
			 		+ " appntno,appntname,managecom,agentcode,"
			 		+ " (select name from laagent b where 1=1 and agentcode = a.agentcode),"
			 		+ " (select max(to_char(makedate,'yyyy-mm-dd') || ' ' || maketime) from es_doc_main"
			 		+ " where subtype = 'UR301' and doccode = a.prtno) A"
			 		//+ " (select riskcode from lcpol where prtno=a.prtno and mainpolno=polno and rownum=1)"
			 		+ " from lccont a where "
			 		+ " a.conttype='1' and appflag='1' and a.printcount>0"
			 		+ " and a.customgetpoldate is null"
			 		+ " and exists (select 1 from es_doc_main d"
			 		+ " where d.doccode = a.prtno and d.subtype = 'UR301'"
			 		+ getWherePart('d.makedate','ScanDate',strOperate)
			 		+ " )"
			 		+ " and grpcontno = '00000000000000000000'"
			 		+ getWherePart('a.prtno','PrtNo',strOperate)
	    			+ getWherePart('a.contno','ContNo',strOperate)
	    			+ getWherePart('a.managecom','qManageCom',strOperate)
	    			+ getWherePart('a.agentcode','AgentCode',strOperate)
	    			+ "order by A";*/
	    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ScanDate').value,document.all('PrtNo').value
	                                         ,document.all('ContNo').value,document.all('qManageCom').value,document.all('AgentCode').value]);
//	    prompt("",strSQL);
	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

function GoToInput() {
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<GrpGrid.mulLineCount; i++) {
    if (GrpGrid.getSelNo(i)) { 
      checkFlag = GrpGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) {
    var	prtNo = GrpGrid.getRowColData(checkFlag - 1, 1); 	
    var ContNo = GrpGrid.getRowColData(checkFlag - 1, 2); 
    var RiskCode=GrpGrid.getRowColData(checkFlag-1,3);
    var AppntNo = GrpGrid.getRowColData(checkFlag - 1, 4);
    var AppntName = GrpGrid.getRowColData(checkFlag - 1, 5);
    var ManageCom = GrpGrid.getRowColData(checkFlag - 1, 6);
    var AgentCode = GrpGrid.getRowColData(checkFlag - 1, 7);
    var AgentName = GrpGrid.getRowColData(checkFlag - 1, 8);
    var ScanDate = GrpGrid.getRowColData(checkFlag - 1, 9);
    //var sFeatures = "";
    //alert(AgentName);
    window.open("./SysCertTakeBackMain.jsp?prtNo="+prtNo+"&ContNo="+ContNo+"&SubType=TB1026&AgentName="+AgentName,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");        
  }
}
function beforeSubmit()
{
	var strSQL = "";
	/*strSQL = "select missionprop1 from lwmission where 1=1 "
				 //+ " and activityid = '0000001098' "
				 + " and processid = '0000000003'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";*/
	strSQL = wrapSql(tResourceName,"querysqldes2",[fm.PrtNo.value]);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}
	
	/*strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000001062' "
				 + " and processid = '0000000003'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";*/	
	strSQL = wrapSql(tResourceName,"querysqldes3",[fm.PrtNo.value]);
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
	/*strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";*/
	strSQL = wrapSql(tResourceName,"querysqldes4",[fm.PrtNo.value]);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}	   	
}
