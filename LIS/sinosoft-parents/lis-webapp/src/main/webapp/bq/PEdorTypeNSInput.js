//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

var PolNo = "";
var prtNo = "";
var str_sql = "",sql_id = "", my_sql = "";
var mSwitch = parent.VD.gVSwitch;

//��ʼ��������Ϣ����¼����Ϣ
function initQuery() {	
//  var strSql = "select prtno from lcpol where PolNo='" + document.all('PolNo').value + "'";
  sql_id = "PEdorTypeNSInputSql20";
  my_sql = new SqlClass();
  my_sql.setResourceName("bq.PEdorTypeNSInputSql"); //ָ��ʹ�õ�properties�ļ���
  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
  my_sql.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
  str_sql = my_sql.getString();
	prtNo = easyExecSql(str_sql);	
  
//	var strSql = "select code, codename from ldcode1 where code1 in (select distinct riskcode from lcpol where mainpolno=polno and PrtNo='" + prtNo + "')";
	 sql_id = "PEdorTypeNSInputSql21";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeNSInputSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(prtNo);//ָ������Ĳ���
	  str_sql = my_sql.getString();
	fm.RiskCode.CodeData = easyQueryVer3(str_sql);	
	
  LPInsuredGrid.clearData("LPInsuredGrid");
//	var strSql = "select distinct riskcode, ProposalNo from lcpol where PrtNo='" + prtNo
//	           + "' and riskcode in (select riskcode from lmriskapp where SubRiskFlag='S') and appflag='2' order by proposalno";
	sql_id = "PEdorTypeNSInputSql22";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeNSInputSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(prtNo);//ָ������Ĳ���
	  str_sql = my_sql.getString();
	turnPage.pageLineNum = 500;
	turnPage.queryModal(strSql, LPInsuredGrid);  
  	
	PolNo = document.all('PolNo').value;
}

//������¼�����
function afterCodeSelect(cCodeName, Field) {
  if (LPInsuredGrid.mulLineCount >= 500) {
    alert("���������������Χ������������һ����ȫ��Ŀ��");
    return;
  }
  
  if (cCodeName == "EdorRisk") {
		showInfo = window.open("./ProposalMain.jsp?risk="+Field.value+"&prtNo="+prtNo+"&loadFlag=8"+"&polNo="+fm.PolNo.value);
  }
}

//��������
function edorSave() {
  if (LPInsuredGrid.mulLineCount == 0) {
    alert("����¼�����ܱ������룡");
    return;
  }
  
  for (i=0; i<LPInsuredGrid.mulLineCount; i++) {
    if (LPInsuredGrid.getRowColData(i, 2) == "") {
      alert("����������е�����¼����ܱ������룡");
      return;
    }
  }
  
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  document.all('fmtransact').value = "INSERT";
  fm.submit();
}

//��ʾ�ύ���
function afterSubmit( FlagStr, content ) {
	showInfo.close();
           
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//���أ��رյ�ǰ����
function returnParent() {
  top.close();
}
