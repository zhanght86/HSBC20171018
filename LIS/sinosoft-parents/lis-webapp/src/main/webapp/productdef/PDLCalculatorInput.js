//�������ƣ�PDLCalculatorInput.js
//������: �ۼ�������
//�������ڣ�2016-5-12
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var Mulline1GridturnPage = new turnPageClass();
var Mulline2GridturnPage=new turnPageClass();
var Mulline3GridturnPage=new turnPageClass();
// ����sql�����ļ�
var tResourceName = "productdef.PDLCalculatorInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function returnParent() {
	top.opener.focus();
	top.close();
}
// ��ѯ���������β㼶���ۼ�����Ϣ
function queryLCalculatorInfo1(){
	
	var riskcode=document.all("RiskCode").value;
	var getdutycode=document.all("GetDutyCode").value;
	
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql2"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara(riskcode);// ָ������Ĳ���
	mySql_0.addSubPara(getdutycode);// ָ������Ĳ���
	var sql = mySql_0.getString();
	// ��ԃ��ԓ�U�N�����е��ۼ�����Ϣ
	Mulline2GridturnPage.pageLineNum = 5;
	Mulline2GridturnPage.queryModal(sql, Mulline2Grid);
	
	if(Mulline2GridturnPage.mulLineCount<=0){
		return false;
	}
}
// ��ѯ���������β㼶�����ۼ�����Ϣ
function queryLCalculatorInfo(){
	document.all("FlagStr").value="0";
	var riskcode=document.all("RiskCode").value;
	var getdutycode=document.all("GetDutyCode").value;
	
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql1"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara(riskcode);// ָ������Ĳ���
	var sql = mySql_0.getString();
	// ��ԃ��ԓ�U�N�����е��ۼ�����Ϣ
	Mulline1GridturnPage.pageLineNum = 5;
	Mulline1GridturnPage.queryModal(sql, Mulline1Grid);
	
	if(Mulline1GridturnPage.mulLineCount<=0){
		return false;
	}
}

function showLCalculatorData(){
	/* Ϊ�˱�������Mulline ͬʱѡ�� */
	initMulline1Grid();
	queryLCalculatorInfo();
	/* ��ȡѡ�ж��к� */
	var selNo = Mulline2Grid.getSelNo();
	/* �ж��Ƿ�ѡ�� */
	if(selNo==0){
		return;
	}
	/* ��ȡ�ۼ������� */
	var  calculatorcode="";
	try{
		var  calculatorcode=Mulline2Grid.getRowColData(selNo-1,1);
	}catch(ex){}
	/* ��ȡ��ϸ��ˮ�� */
	var SerialNo=Mulline2Grid.getRowColData(selNo-1,3);
	document.all("SerialNo").value=SerialNo;
	/* �����ۼ��������ѯ�ۼ���������Ϣ */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql3"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara(calculatorcode);// ָ������Ĳ���
	var sql = mySql_0.getString();
	var result = easyExecSql(sql, 1, 1, 1);
	
	if(result!=null){
		var i=0;
		// ����ѯ���Ľ�����ν��и�ֵ
		try{
			// �ۼ�������
			var calculatorcode=result[0][i];
			document.all("CalculatorCode").value=calculatorcode;
		}catch(ex){}
		try{
			// �ۼ�������
			var calculatorname=result[0][++i];
			document.all("CalculatorName").value=calculatorname;
		}catch(ex){}
		try{
			// �ۼ����㼶
			var ctrlLevel=result[0][++i];
			document.all("CtrlLevel").value=ctrlLevel;
		}catch(ex){}
		try{
			// �ۼ����㼶name
			var ctrlLevelname=result[0][++i];
			document.all("CtrlLevelName").value=ctrlLevelname;
		}catch(ex){}
		try{
			// �ۼ�������
			var calculatortype=result[0][++i];
			document.all("CalculatorType").value=calculatortype;
		}catch(ex){}
		try{
			// �ۼ�������name
			var calculatortypename=result[0][++i];
			document.all("CalculatorTypeName").value=calculatortypename;
		}catch(ex){}
		try{
			// �ۼ���Ҫ������
			var ctrlFactorType=result[0][++i];
			document.all("CtrlFactorType").value=ctrlFactorType;
		}catch(ex){}
		try{
			// �ۼ���Ҫ������name
			var ctrlFactorTypename=result[0][++i];
			document.all("CtrlFactorTypeName").value=ctrlFactorTypename;
		}catch(ex){}
		try{
			// Ҫ��ֵ
			var calculatorvalue=result[0][++i];
			document.all("CtrlFactorValue").value=calculatorvalue;
		}catch(ex){}
		try{
			// Ҫ�ص�λ
			var calculatorUnit=result[0][++i];
			document.all("CtrlFactorUnit").value=calculatorUnit;
		}catch(ex){}
		try{
			// Ҫ�ص�λname
			var calculatorUnitname=result[0][++i];
			document.all("CtrlFactorUnitName").value=calculatorUnitname;
		}catch(ex){}
		try{
			// Ҫ��ֵ���㷽ʽ
			var calmode=result[0][++i];
			document.all("CalMode").value=calmode;
		}catch(ex){}
		try{
			// Ҫ��ֵ���㷽ʽname
			var calmodename=result[0][++i];
			document.all("CalModeName").value=calmodename;
		}catch(ex){}
		try{
			// Ҫ��ֵ���㹫ʽ
			var calcode=result[0][++i];
			document.all("CalCode").value=calcode;
		}catch(ex){}
		try{
			// Ĭ��ֵ
			var tdefault=result[0][++i];
			document.all("DefaultValue").value=tdefault;
		}catch(ex){}
		try{
			// ��Ч����
			var startedate=result[0][++i];
			document.all("StartDate").value=startedate;
		}catch(ex){}
		try{
			// ��Чֹ��
			var enddate=result[0][++i];
			document.all("EndDate").value=enddate;
		}catch(ex){}
		try{
			// ��Чʱ��
			var validperiod=result[0][++i];
			document.all("ValidPeriod").value=validperiod;
		}catch(ex){}
		try{
			// ��Чʱ����λ
			var validperiodunit=result[0][++i];
			document.all("ValidPeriodUnit").value=validperiodunit;
		}catch(ex){}
		try{
			// ��Чʱ����λname
			var validperiodunitname=result[0][++i];
			document.all("ValidPeriodUnitName").value=validperiodunitname;
		}catch(ex){}
		try{
			// �ۼӲ���
			var calorder=result[0][++i];
			document.all("CalOrder").value=calorder;
		}catch(ex){}
		try{
			// ��Чʱ����λ
			var calculatorcodeafter=result[0][++i];
			document.all("CalculatorCodeAfter").value=calculatorcodeafter;
		}catch(ex){}
	}
	
	/* �����ۼ��������ȡ�ۼ����˵���Ϣ */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql4"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara(calculatorcode);// ָ������Ĳ���
	var sql = mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feecode="";
	var feecodename="";
	if(result!=null){
		 feecode=result[0][0];
		feecodename=result[0][1];
	}
	
	document.all("FeeCode").value=feecode;
	document.all("FeeCodeName").value=feecodename;
	// ����riskcode,dutycode,getdutycode,plancode,feecode����ѯ�˵���Ϣ
	var riskcode=document.all("RiskCode").value;
	var dutycode=Mulline2Grid.getRowColData(selNo-1,5);
	var getdutycode=Mulline2Grid.getRowColData(selNo-1,6);
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql5"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara("A");// ָ������Ĳ���
	mySql_0.addSubPara(riskcode);// ָ������Ĳ���
	mySql_0.addSubPara(dutycode);// ָ������Ĳ���
	mySql_0.addSubPara(getdutycode);// ָ������Ĳ���
	mySql_0.addSubPara(feecode);// ָ������Ĳ���
	var sql=mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feetype="";
	var feetypename="";
	var ifselfPayPercent="";
	var ifselfPayPercentname="";
	var selfPayPercent="";
	var IfPayMoney="";
	if(result!=null){
	var feetype=result[0][0];
	var feetypename=result[0][1];
	var ifselfPayPercent=result[0][2];
	var ifselfPayPercentname=result[0][3];
	var selfPayPercent=result[0][4];
	var IfPayMoney=result[0][5];
	}
	
	document.all("ifselfPayPercent").value=ifselfPayPercent;
	document.all("ifselfPayPercentName").value=ifselfPayPercentname;
	document.all("selfPayPercent").value=selfPayPercent;
	document.all("IfPayMoney").value=IfPayMoney;
	document.all("FeeType").value=feetype;
	document.all("FeeTypeName").value=feetypename;
}
function showLCalculatorDataInfo(){
	document.all("FlagStr").value="1";
	/* Ϊ�˱�������Mulline ͬʱѡ�� */
	initMulline2Grid();
	queryLCalculatorInfo1();
	/* ��ȡѡ�ж��к� */
	var selNo = Mulline1Grid.getSelNo();
	/* �ж��Ƿ�ѡ�� */
	if(selNo==0){
		return;
	}
	/* ��ȡ�ۼ������� */
	var  calculatorcode="";
	try{
		var  calculatorcode=Mulline1Grid.getRowColData(selNo-1,1);
	}catch(ex){}
	
	/* �����ۼ��������ѯ�ۼ���������Ϣ */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql3"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara(calculatorcode);// ָ������Ĳ���
	var sql = mySql_0.getString();
	var result = easyExecSql(sql, 1, 1, 1);
	
	if(result!=null){
		var i=0;
		// ����ѯ���Ľ�����ν��и�ֵ
		try{
			// �ۼ�������
			var calculatorcode=result[0][i];
			document.all("CalculatorCode").value=calculatorcode;
		}catch(ex){}
		try{
			// �ۼ�������
			var calculatorname=result[0][++i];
			document.all("CalculatorName").value=calculatorname;
		}catch(ex){}
		try{
			// �ۼ����㼶
			var ctrlLevel=result[0][++i];
			document.all("CtrlLevel").value=ctrlLevel;
		}catch(ex){}
		try{
			// �ۼ����㼶name
			var ctrlLevelname=result[0][++i];
			document.all("CtrlLevelName").value=ctrlLevelname;
		}catch(ex){}
		try{
			// �ۼ�������
			var calculatortype=result[0][++i];
			document.all("CalculatorType").value=calculatortype;
		}catch(ex){}
		try{
			// �ۼ�������name
			var calculatortypename=result[0][++i];
			document.all("CalculatorTypeName").value=calculatortypename;
		}catch(ex){}
		try{
			// �ۼ���Ҫ������
			var ctrlFactorType=result[0][++i];
			document.all("CtrlFactorType").value=ctrlFactorType;
		}catch(ex){}
		try{
			// �ۼ���Ҫ������name
			var ctrlFactorTypename=result[0][++i];
			document.all("CtrlFactorTypeName").value=ctrlFactorTypename;
		}catch(ex){}
		try{
			// Ҫ��ֵ
			var calculatorvalue=result[0][++i];
			document.all("CtrlFactorValue").value=calculatorvalue;
		}catch(ex){}
		try{
			// Ҫ�ص�λ
			var calculatorUnit=result[0][++i];
			document.all("CtrlFactorUnit").value=calculatorUnit;
		}catch(ex){}
		try{
			// Ҫ�ص�λname
			var calculatorUnitname=result[0][++i];
			document.all("CtrlFactorUnitName").value=calculatorUnitname;
		}catch(ex){}
		try{
			// Ҫ��ֵ���㷽ʽ
			var calmode=result[0][++i];
			document.all("CalMode").value=calmode;
		}catch(ex){}
		try{
			// Ҫ��ֵ���㷽ʽname
			var calmodename=result[0][++i];
			document.all("CalModeName").value=calmodename;
		}catch(ex){}
		try{
			// Ҫ��ֵ���㹫ʽ
			var calcode=result[0][++i];
			document.all("CalCode").value=calcode;
		}catch(ex){}
		try{
			// Ĭ��ֵ
			var tdefault=result[0][++i];
			document.all("DefaultValue").value=tdefault;
		}catch(ex){}
		try{
			// ��Ч����
			var startedate=result[0][++i];
			document.all("StartDate").value=startedate;
		}catch(ex){}
		try{
			// ��Чֹ��
			var enddate=result[0][++i];
			document.all("EndDate").value=enddate;
		}catch(ex){}
		try{
			// ��Чʱ��
			var validperiod=result[0][++i];
			document.all("ValidPeriod").value=validperiod;
		}catch(ex){}
		try{
			// ��Чʱ����λ
			var validperiodunit=result[0][++i];
			document.all("ValidPeriodUnit").value=validperiodunit;
		}catch(ex){}
		try{
			// ��Чʱ����λname
			var validperiodunitname=result[0][++i];
			document.all("ValidPeriodUnitName").value=validperiodunitname;
		}catch(ex){}
		try{
			// �ۼӲ���
			var calorder=result[0][++i];
			document.all("CalOrder").value=calorder;
		}catch(ex){}
		try{
			// ��Чʱ����λ
			var calculatorcodeafter=result[0][++i];
			document.all("CalculatorCodeAfter").value=calculatorcodeafter;
		}catch(ex){}
	}
	
	/* �����ۼ��������ȡ�ۼ����˵���Ϣ */
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql4"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara(calculatorcode);// ָ������Ĳ���
	var sql = mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feecode="";
	var feecodename="";
	if(result!=null){
		 feecode=result[0][0];
		 feecodename=result[0][1];
	}
	document.all("FeeCode").value=feecode;
	document.all("FeeCodeName").value=feecodename;
// // ��ԃ��ԓ�U�N�����е��ۼ�����Ϣ
// Mulline3GridturnPage.pageLineNum = 5;
// Mulline3GridturnPage.queryModal(sql, Mulline3Grid);
// if(Mulline3GridturnPage.mulLineCount<=0){
// alert("���ۼ�����û���˵�ά����Ϣ��");
// return false;
// }
	// ����riskcode,dutycode,getdutycode,plancode,feecode����ѯ�˵���Ϣ
	var riskcode=document.all("RiskCode").value;
	var dutycode=Mulline1Grid.getRowColData(selNo-1,4);
	var getdutycode=Mulline2Grid.getRowColData(selNo-1,5);
	var mySql_0 = new SqlClass();
	var sqlid = "PDLCalculatorInputSql5"; // ָ��sqlID
	mySql_0.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql_0.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	mySql_0.addSubPara("A");// ָ������Ĳ���
	mySql_0.addSubPara(riskcode);// ָ������Ĳ���
	mySql_0.addSubPara(dutycode);// ָ������Ĳ���
	mySql_0.addSubPara(getdutycode);// ָ������Ĳ���
	mySql_0.addSubPara(feecode);// ָ������Ĳ���
	var sql = mySql_0.getString();
	var result= easyExecSql(sql, 1, 1, 1);
	var feetype="";
	var feetypename="";
	var ifselfPayPercent="";
	var ifselfPayPercentname=""
	var selfPayPercent="";
	var IfPayMoney="";
	if(result!=null){
	 feetype=result[0][0];
	 feetypename=result[0][1];
	 ifselfPayPercent=result[0][2];
	 ifselfPayPercentname=result[0][3];
	 selfPayPercent=result[0][4];
	 IfPayMoney=result[0][5];
	}
	document.all("ifselfPayPercent").value=ifselfPayPercent;
	document.all("ifselfPayPercentName").value=ifselfPayPercentname;
	document.all("selfPayPercent").value=selfPayPercent;
	document.all("IfPayMoney").value=IfPayMoney;
	document.all("FeeType").value=feetype;
	document.all("FeeTypeName").value=feetypename;
	
	
	alert("�����޸��ۼ�����Ϣ,ֱ�ӵ�����水ť");
}
// ����
function save() {
	var selNo = Mulline2Grid.getSelNo();
	var selNo1 = Mulline1Grid.getSelNo();
	if(selNo>0){
		alert("��ִ���޸Ĳ�����");
		return;
	}
	// �ύ��ʽΪINSERT��
	document.all("operator").value="INSERT";
	if(selNo1>0){
		// Ĭ��Ϊ������Ϣ
		document.getElementById("fm").submit();
	}else{
	submitForm();
	}
}

// ѡ��һ�������޸�
function update(){
	// У���Ƿ�ѡ��һ������,ֻ���޸ı��������β㼶���ۼ�����Ϣ
	 var selNo = Mulline2Grid.getSelNo();
	 var selNo1 = Mulline1Grid.getSelNo();
	/* ���޸ĵ�ʱ�򣬲������޸Ĳ㼶������޸�����ɾ���ò㼶���ۼ��� */
	if(!checkctrllevel(Mulline1Grid.getRowColData(selNo1-1,1),document.all("CtrlLevel").value)){
		alert("�������޸Ĳ㼶��");
		return;
	}
	 if(selNo1>0){
		 alert("ֻ���޸ı��������β㼶���ۼ�����Ϣ");
		 return;
	 }
	if(selNo == 0)
	{
		alert("��ѡ��һ�����������β㼶����Ϣ��¼�ٽ����޸Ĳ�����");
		return;
	}
		
	// �ύ��ʽΪUPDATE��
	document.all("operator").value="UPDATE";
	submitForm();	
		
	
}
// ѡ��һ������ɾ��
function del(){
	// У���Ƿ�ѡ��һ������
	 var selNo = Mulline2Grid.getSelNo();
		if(selNo == 0)
		{
			alert("��ѡ��һ�����������εļ�¼��ɾ��");
			return;
		}
		// �ύ��ʽΪDELETEETE��
		document.all("operator").value="DELETE";
		submitForm();	
}
/* �����ύ */
function submitForm()
{
if(document.all("IsReadOnly").value == "1")
  {
  	alert("����Ȩִ�д˲���");
  	return;
  }
  
	if(!checkNullable())
	{
		return false;
	}
  lockPage("���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
  
  document.getElementById("fm").submit();
}

/* У���һ�κ͵ڶ���ѡ�е��ۼ����㼶 */
function checkctrllevel(calculatorcode,ctrllevel){
	var sql="select sign(a.ctrllevel-"+ctrllevel+") from Pd_Lcalculatormain a where calculatorcode='"+calculatorcode+"'";
	var sql = sql.toString();
	var result = easyExecSql(sql, 1, 1, 1);
	var result = result[0][0];
	if(result!=0){
		return false;
	}else{
		return true;
	}
}
// У���Ƿ�����˵���Ϣ
function checkFeeCodeInfo(calculatorcode){
	var tcalculatorcode=calculatorcode;
	var mySql = new SqlClass();
	var sqlid = "PDLCalculatorInputSql7"; // ָ��sqlID
	mySql.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
	// mySql.addSubPara(triskcode);//ָ������Ĳ���
	mySql.addSubPara(tcalculatorcode);// ָ������Ĳ���
	var sql = mySql.getString();
	var result = easyExecSql(sql, 1, 1, 1);
	if(result<=0){
		return false;
	}else{
		return true;
	}
}
/* �㷨������� */
function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CalCode').value = tCalCode;
}
// �����ύ��������
function afterSubmit( FlagStr, content )
{
unLockPage();
if (FlagStr == "Fail" )
{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
var iWidth=550;      // �������ڵĿ��;
var iHeight=350;     // �������ڵĸ߶�;
var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();
// [end]
}
else
{
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
var iWidth=550;      // �������ڵĿ��;
var iHeight=350;     // �������ڵĸ߶�;
var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();
if(document.all("operator").value!="DELETE" && document.all("CalMode").value=="3")
{
	InputCalCodeDefFace('02');
	}
	// else
	{
		initForm(); 
	}
	if(document.all("operator").value=="DELETE" || document.all("CalMode").value!="3")
	{
		// initGetClaimDetail();
	}
}
}
// ����У��
function checkNullable()
{
  if(!verifyInput())
  {
  	return false;
  }
  
  return true;
}


/* �����˵���Ϣ */
function button1(){
	var riskcode=document.all("RiskCode").value;
	var tgetdutycode=document.all("GetDutyCode").value;
	 showInfo = window.open("./PDLDPlanFeeRelaMain.jsp?riskcode=" + document.all("RiskCode").value + "&getdutycode="+tgetdutycode);
}

// ��������
function clickUpload(){
    if(fm1.all('FileName').value == ""){
        alert("�ļ�����Ϊ��");
        return;
    }
    var i = 0;
    var ImportFile = document.all("FileName").value.toLowerCase();
    ImportPath = "/lis/Ldsysvar/";
    lockPage("���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
    fm1.action = "./PDLCalculatorInputULSave.jsp?ImportPath=" + ImportPath
            + "&ImportFile=" + ImportFile + "&transact=INSERT";
    fm1.submit(); // �ύ
}

function checkCalMode(){
	/*���Ҫ�صļ��㷽ʽΪ1����ʾĬ��ֵ���ۼ�����Ҫ��ֵһ��*/
	var calmode=document.all("CalMode").value;
	if(""==calmode){
		alert("��ѡ��Ҫ��ֵ�ļ��㷽ʽ��");
		return;
	}else{
		if("1"==calmode){
			if(""==document.all("CtrlFactorValue").value){
				alert("������Ҫ��ֵ��");
			}else{
				document.all("DefaultValue").value=document.all("CtrlFactorValue").value;
			}
		}else{
			document.all("DefaultValue").value="";
		}
	}
}
