<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDRiskDefiInit.jsp
  //�����ܣ���Ʒ������Ϣ¼��
  //�������ڣ�2009-3-12
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*"%>
<script type="text/javascript">
var tResourceName = "productdef.PDRiskDefiInputSql";
function initForm(){
	//IE11-Element
	try{
		var tAllElements = document.getElementsByTagName("*");
		for(var i=0;i<tAllElements.length;i++){
			var tElement = tAllElements[i];
			if(tElement.name!=null && tElement.name!=""){
				if(tElement.id!=null && tElement.id==""){
					tElement.id = tElement.name;
				}
			}
		}
	}catch(e){
		
	}
	
	try{
	    isshowbutton();
		document.getElementById("RiskCode").value = "<%=request.getParameter("riskcode")%>";

		document.getElementById("RequDate").value = "<%=request.getParameter("requdate")%>";
		document.getElementById("MissionID").value = "<%=request.getParameter("missionid")%>";
    	document.getElementById("SubMissionID").value = "<%=request.getParameter("submissionid")%>";
		document.getElementById("ActivityID").value = "<%=request.getParameter("activityid")%>";
		fmF.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";	
		
		if(fmF.all("IsReadOnly").value == '1'){
			document.getElementById('copyproductbutton').disabled = true;
		}
		
		//updateDisplayState();

		initMulline9Grid();
		//queryMulline9Grid();
		
		
		initMulline11Grid();
	//	queryMulline11Grid();
	
		
		initMulline12Grid(); 
		
		initMulline13Grid();
	//	fmF.all("riskType").value = '';
		initMulline14Grid();

		//queryMulline12Grid();

		// fm.btnRiskAccountDefi.style.display = "none";
		// fm.btnIssueQuery.disabled = false;
		// updateDisplay();

		initRisk();
		queryMulline14Grid();
		initRadio();
		//initCheckBox(); 
		initRiskPay();
		queryRiskPay();
		
		//�����������ʽ��ҽ���շ���
		if(document.getElementById('riskType').value=='L')
		{
			document.getElementById('MedicareDefi').style.display = "none";
		}
		else
		{
			document.getElementById('MedicareDefi').style.display = "";
		}
			if(document.getElementById('BonusFlag').value=='0')
		{
			document.getElementById('BonusDefi').style.display = "none";
		}
		else
		{
			document.getElementById('BonusDefi').style.display = "";
		}
	
	}catch(ex){
		myAlert("initForm()�������ִ���");
	}
}

function updateDisplay()
{
	var paras = new Array();
	paras[0] = new Array();
	paras[0][0] = "RiskCode"; // sql�����"@@"�м�Ĳ�������
	paras[0][1] = fm.all("RiskCode").value; // ����ֵ
			
	// pageNo:ҳ��ı�ţ���Ψһȷ����ҳ��; eleType:ҪУ���Ԫ�ص����ͣ���Ϊ��; paras:sql����в������ƺ�ֵ�Ķ�ά����
	customDisplay(fm.PageNo.value, "button", paras);
}

function updateDisplayState()
{
 // rowCount:��ʾ���ֶ�����
 //var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 
 var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql16";
	mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.all("tableName").value);//ָ������Ĳ���
	var sql = mySql1.getString();
	
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:�������Ӧ��selectcode������
 //sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 
 	sqlid1 = "PDRiskDefiInputSql17";
	mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.all("tableName").value);//ָ������Ĳ���
	sql = mySql1.getString();
	
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 
 
 
 for(var i = 0; i < rowCount; i++)
 {

     if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("riskcode").value,null,"readonly"); 		 
	 }
	 else
	 {
	 	 var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where RiskCode = '"+fm.RiskCode.value +"'";
   	 var tContent = easyExecSql(tDefaultValueSQL);
   	 
   	 var cData = null;
   	 if(tContent!=null&&tContent[0][0]!="null")
   	 {
   	 	 cData = tContent[0][0];
   	 }
   	 
     Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
    }
  }
}


function afterRadioSelect(){
	//-------- add by jucy3
	//������������ˢ�½ɷ����Զ��������
	//����ԭ�����ѡ����������û�ж���ɷ���Ϣ����մ����������
	initPayPlan();
	//-------- end
	var selNo = Mulline9Grid.getSelNo()-1;
	var dutyCode = Mulline9Grid.getRowColData(selNo,4);
	var payplancode = Mulline9Grid.getRowColData(selNo,2);
	/*var sql="select payplancode,payplanname,c.zeroflag, c.calcode,c.cntercalcode,c.othcalcode,c.GracePeriod,c.PayEndYear,c.PayIntv,";
	sql += "c.paystartyear,c.paystartyearflag,(select codename from ldcode where codetype='pd_paystartyearflag' and code=c.paystartyearflag),";
	sql += "c.paystartdatecalref,(select codename from ldcode where codetype='pd_paystdatecalref' and code=c.paystartdatecalref),";
	sql += "c.paystartdatecalmode,(select codename from ldcode where codetype='pd_paystdatecalmode' and code=c.paystartdatecalmode),";
	sql += "c.payendyearflag,(select codename from ldcode where codetype='pd_payendyearflag' and code=c.payendyearflag),";
	sql += "c.payenddatecalmode,(select codename from ldcode where codetype='pd_payenddatecalmode' and code=c.payenddatecalmode),";
	sql += "c.urgepayflag,(select codename from ldcode where codetype='pd_urgepayflag' and code=c.urgepayflag),";
	sql += "c.PayAimClass,(select codename from ldcode where codetype='pd_payaimclass' and code=c.PayAimClass),";
	sql += "c.needacc,(select codename from ldcode where codetype='pd_needacc' and code=c.needacc) from pd_lmduty a,pd_lmdutypay c,pd_lmriskduty b "
	sql +=  "where a.dutycode=b.dutycode and b.dutycode = c.dutycode and a.dutycode = '"+dutyCode+"' and c.payplancode = '"+payplancode+"' ";
*/
	var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql18";
	mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(dutyCode);//ָ������Ĳ���
		mySql1.addSubPara(payplancode);//ָ������Ĳ���
	var sql = mySql1.getString();
	var result = easyExecSql(sql,1,1,1);
	//alert('stop');
	
	//-----add by jucy3---
	//���У�飺���û�ж����������εĽɷ����Σ����³�ʼ��MulLine
	if(result==""||result==null){
		initMulline9Grid();
	}
	//-----end
	
	
	if(result){		
	 	fm.all('payPlanCode').value=result[0][0];
	 	fm.all('payPlanName').value=result[0][1];
	 	fm.all('zeroFlag').value=result[0][2];
	 	fm.all('payCalCode').value = result[0][3];
	 	
	 	fm.all('GracePeriod').value = result[0][6];
	 	fm.all('PayEndYear').value = result[0][7];
	 	fm.all('PayIntv').value = result[0][8];

	 	fm.all('PayStartYear').value = result[0][9];
		fm.all('PayStartYearFlag').value = result[0][10];
		fm.all('PayStartDateCalRef').value = result[0][12];
		fm.all('PayStartDateCalMode').value = result[0][14];
		fm.all('PayStartFlagName').value = result[0][11];
		fm.all('PaySDCalRefName').value = result[0][13];
		fm.all('PaySDCalModeName').value = result[0][15];
		fm.all('PayEndYearFlag').value = result[0][16];
		fm.all('PayEndYearFlagS').value = result[0][17];
		fm.all('PayEndDateCalMode').value = result[0][18];
		fm.all('PayEndDateCalModeS').value = result[0][19];
		fm.all('UrgePayFlag').value = result[0][20];
		fm.all('UrgePayFlagS').value = result[0][21];
		fm.all('PayAimClass').value = result[0][22];
		fm.all('PayAimClassS').value = result[0][23];
		fm.all('needAccPay').value = result[0][24];
		fm.all('needAccPayS').value = result[0][25];
		fm.all('InvestType').value = result[0][26];
		fm.all('PCalCode').value = result[0][27];
		fm.all('RCalPremFlag').value = result[0][28];
		fm.all('RCalPremCode').value = result[0][29];
		/*
	 	sql =  "select trim(codename) from ldcode where trim(code)='"+fm.all('zeroFlag').value+"' and codetype='pd_yesno'";
	 	var resultS =  easyExecSql(sql,1,1,1);
	 	if(resultS){
	 		fm.all('ZeroFlagS').value =resultS[0][0];
	 	}*/
	 	showOneCodeName('pd_yesno', 'zeroFlag', 'ZeroFlagS')
	 	showOneCodeName('pd_yesno', 'RCalPremFlag', 'RCalPremFlagS')
	 	showOneCodeName('pd_investtypes', 'InvestType', 'InvestTypeS')
	 	if (result[0][3] != ""){
			fm.all('payCalType').value= '1';
	 		fm.payCalTypeS.value='�����㱣��';
	 		fm.all('payCalCode').value = result[0][3];
	 		fm.all('payCalCodeBack').value = result[0][4];
	 	}
	 	else if(result[0][4] != "" ){
	 		fm.all('payCalType').value= '2';
	 		fm.payCalTypeS.value='�����㱣��';
	 		fm.all('payCalCode').value = result[0][4];
	 		fm.all('payCalCodeBack').value = result[0][3];
	 	}else if(result[0][5] != "" ){
	 		fm.all('payCalType').value='3';
	 		fm.payCalTypeS.value='�����㱣��';
	 		fm.all('payCalCode').value = result[0][5];
	 		
	 		fm.all('payCalCodeBack').value = result[0][4];
	 	}
 		
 		
 		initDutyPayCalCodeMain(document.getElementById("RiskCode").value,fm.all('payCalCode').value);
 		initDutyPayCalCodeBackMain(document.getElementById("RiskCode").value,fm.all('payCalCodeBack').value);
 		
 		//var checkPayStartYear = fm.all('PayStartYear').value;
 		
 		//if(checkPayStartYear !=null && checkPayStartYear=="0"){
 		//	fm.all("ISPayStartYear")[1].checked = true;
 		//	closePayStartCtrl();
 		//}else{
 			fm.all("ISPayStartYear")[0].checked = true;
 			showPayStartCtrl();
 		//}
	} 
	

}
//--------add by jucy3
//���ӵ����ѡ��ͬ��ˢ����һ�㼶����������򷽷�
//1.�ɷ����Զ�������
function initPayPlan(){
	//fm.all('DutyCodeS').value="";
	fm.all('payPlanName').value="";
	fm.all('zeroFlag').value="";
	fm.all('ZeroFlagS').value="";
	//fm.all('payPlanCode').value="";
	fm.all('GracePeriod').value="";
	fm.all('needAccPay').value="";
	fm.all('needAccPayS').value="";
	fm.all('payCalType').value="";
	fm.all('payCalTypeS').value="";
	fm.all('payCalCode').value="";
	
	fm.all('ISPayStartYear')[0].checked = false;
	fm.all('ISPayStartYear')[1].checked = true;
	fm.all('isAccRela')[0].checked = false;
	fm.all('isAccRela')[1].checked = false;
	
	fm.all('PayEndYear').value="";
	fm.all('PayEndYearFlag').value="";
	fm.all('PayEndYearFlagS').value="";
	fm.all('PayEndDateCalMode').value="";
	fm.all('PayEndDateCalModeS').value="";
	fm.all('PayIntv').value="";
	fm.all('UrgePayFlag').value="";
	fm.all('UrgePayFlagS').value="";
	fm.all('PayAimClass').value="";
	fm.all('PayAimClassS').value="";
	fm.all('InvestType').value="";
	fm.all('InvestTypeS').value="";
	fm.all('RCalPremFlag').value="";
	fm.all('RCalPremFlagS').value="";
	fm.all('PayStartYear').value="";
	fm.all('PayStartYearFlag').value="";
	fm.all('PayStartFlagName').value="";
	fm.all('PayStartDateCalRef').value="";
	fm.all('PaySDCalRefName').value="";
	fm.all('PayStartDateCalMode').value="";
	fm.all('PaySDCalModeName').value="";
	
}
//2.�������Զ�������
function initGetDuty(){

	//fm.all('getDutyCode').value="";
	fm.all('getDutyName').value="";
	fm.all('AddAmntFlag').value="";
	fm.all('AddAmntFlagS').value="";
	fm.all('needAccGet').value="";
	fm.all('needAccGetS').value="";
	fm.all('type').value="";
	fm.all('typeS').value="";
	fm.all('GetType1').value="";
	fm.all('GetType1S').value="";
	fm.all('GetType3').value="";
	fm.all('GetType3S').value="";
	fm.all('getCalType').value="";
	fm.all('getcaltypeS').value="";
	fm.all('getCalCode').value="";
	fm.all('zeroGetFlag').value="";
	fm.all('zeroGetFlagS').value="";
	fm.all('UrgeGetFlag').value="";
	fm.all('UrgeGetFlagS').value="";
	fm.all('NeedCancelAcc').value="";
	fm.all('NeedCancelAccS').value="";
	fm.all('CanGet').value="";
	fm.all('CanGetS').value="";
	fm.all('GetIntv').value="";
	fm.all('GetYear').value="";
	fm.all('GetYearFlag1').value="";
	fm.all('GetYearFlag1S').value="";
	fm.all('StartDateCalRef').value="";
	fm.all('StartDateCalRefS').value="";
	fm.all('StartDateCalMode').value="";
	fm.all('StartDateCalModeS').value="";
	fm.all('GetEndPeriod').value="";
	fm.all('GetEndUnit').value="";
	fm.all('GetEndUnitS').value="";
	fm.all('EndDateCalRef').value="";
	fm.all('EndDateCalRefS').value="";
	fm.all('EndDateCalMode').value="";
	fm.all('EndDateCalModeS').value="";
	fm.all('RCalAmntFlag').value="";
	fm.all('RCalAmntFlagS').value="";
	
	fm.all('IsGetYearFlag')[0].checked = false;
	fm.all('IsGetYearFlag')[1].checked = true;
	fm.all('IsGetEndPeriodFlag')[0].checked = false;
	fm.all('IsGetEndPeriodFlag')[1].checked = true;
	document.getElementById('getInvName').style.display = "none";
	document.getElementById('getInvValue').style.display = "none";
	document.getElementById('GetYearPartId').style.display = "none";
	document.getElementById('GetEndPeriodPartId').style.display = "none";
	
}
//--------end

function afterRadioSelect2(){

	//-------- add by jucy3
	//������������ˢ�½ɷ����Զ��������
	//����ԭ�����ѡ����������û�ж���ɷ���Ϣ����մ����������
	initGetDuty();
	//-------- end
	
	var selNo = Mulline11Grid.getSelNo()-1;
	var dutyCode = Mulline11Grid.getRowColData(selNo,4);
	var getplancode = Mulline11Grid.getRowColData(selNo,2);
	/*var sql = "select c.AddAmntFlag,getDutyCode,getDutyname,c.type,c.calcode,c.cntercalcode,c.othcalcode,c.needacc,(select codename from ldcode where codetype='pd_needacc' and code=c.needacc),c.getyear,c.getendperiod,"
	    sql+="c.GetYearFlag,(select codename from ldcode where codetype='insuyearflag' and code=c.GetYearFlag),"
	    sql+="c.StartDateCalRef,(select codename from ldcode where codetype='startdatecalref' and code=c.StartDateCalRef),"
	    sql+="c.StartDateCalMode,(select codename from ldcode where codetype='startdatecalmode' and code=c.StartDateCalMode),"
	    sql+="c.GetEndUnit,(select codename from ldcode where codetype='insuyearflag' and code=c.GetEndUnit),"
	    sql+="c.EndDateCalRef,(select codename from ldcode where codetype='startdatecalref' and code=c.EndDateCalRef),"
	    sql+="c.EndDateCalMode,(select codename from ldcode where codetype='enddatecalmode' and code=c.EndDateCalMode)"
	    sql +=" from pd_lmduty a, pd_lmdutyget c, pd_lmriskduty b where a.dutycode = b.dutycode and b.dutycode = c.dutycode and a.dutycode = '"+dutyCode+"' and c.getdutycode = '"+getplancode+"'";
  	*/
	var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql19";
	mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(dutyCode);//ָ������Ĳ���
	mySql1.addSubPara(getplancode);//ָ������Ĳ���
	var sql = mySql1.getString();
	var result = easyExecSql(sql,1,1,1);
 

	if(result){
	 	fm.all('AddAmntFlag').value=result[0][0];
	 	/*	 sql =  "select trim(codename) from ldcode where trim(code)='"+fm.all('AddAmntFlag').value+"' and codetype='pd_yesno'";
	 	 	var resultF  = easyExecSql(sql,1,1,1);
	 		if(resultF){
	 			fm.all('AddAmntFlagS').value=resultF[0][0];
	 	}*/
		showOneCodeName('pd_yesno', 'AddAmntFlag', 'AddAmntFlagS');
	 	
	 	fm.all('getDutyCode').value=result[0][1];
	 	document.getElementById('getDutyName').value=result[0][2];
	 	fm.all('type').value=result[0][3];
		/* sql =  "select trim(codename) from ldcode where trim(code)='"+fm.all('type').value+"' and codetype='pd_dutygettype'";
		 var resultS = easyExecSql(sql,1,1,1);
		 		 if(resultS){
		 		fm.all('typeS').value =resultS[0][0];
 		}*/
		showOneCodeName('pd_dutygettype', 'type', 'typeS');
 		
 		if (result[0][4] != ""){
			fm.all('getCalType').value= '1';
			fm.all('getCalCode').value=result[0][4];
			fm.all('getCalCodeBack').value=result[0][5];
			fm.getcaltypeS.value='�����㱣��';
 		}
 		else if(result[0][5] !=""){
 			fm.all('getCalType').value= '2';
 			fm.all('getCalCode').value=result[0][5];
			fm.all('getCalCodeBack').value=result[0][4];
			fm.getcaltypeS.value='�����㱣��';
 		}else if(result[0][6] !=""){
 			//--------add by jucy5 
 			fm.all('getCalType').value= '3';
 			//--------end
 			fm.all('getCalCode').value=result[0][6];
			fm.all('getCalCodeBack').value=result[0][5];
			fm.getcaltypeS.value='�����㱣��';
 		}	
 		fm.all('needAccGet').value=result[0][7];
 		fm.all('needAccGetS').value=result[0][8];
 		//���ݸ�����������/��ʾ�������Ҫ�ص�¼�밴ť
 		/*
 		if(result[0][3]=="0"){
 			document.getElementById('divGetAlive').style.display = "";
 		}else{
 			document.getElementById('divGetAlive').style.display = "none";
 		}*/
 			if(result[0][3]=='0' ){
 			document.getElementById('divGetAlive').style.display = "";
 			//document.getElementById('getInvName').style.display = "";
 			//document.getElementById('getInvValue').style.display = "";
 		}else
 		{
 			document.getElementById('divGetAlive').style.display = "none";
 			document.getElementById('getInvName').style.display = "none";
 			document.getElementById('getInvValue').style.display = "none";
 		}
 		
 		initDutyGetCalCodeMain(document.getElementById("RiskCode").value,fm.all('getCalCode').value);
		initDutyGetCalCodeMainBack(document.getElementById("RiskCode").value,fm.all('getCalCodeBack').value);
		//�ж������ڼ�ȵ�
		
		//document.getElementById("GetYear").value=result[0][9];
		fm.all('GetYear').value=result[0][9];
		if(result[0][9]!=null&&result[0][9]>0)
		{
			//alert(result[0][9]);
			document.getElementById('GetYearPartId').style.display = "";
			fm.all("IsGetYearFlag")[0].checked = true;
		}
		else
		{
			document.getElementById('GetYearPartId').style.display = "none";
			fm.all("IsGetYearFlag")[1].checked = true;
		}
		
		document.getElementById("GetYearFlag1").value=result[0][11];
		document.getElementById("StartDateCalRef").value=result[0][13];
		document.getElementById("StartDateCalMode").value=result[0][15];				
		document.getElementById("GetEndPeriod").value=result[0][10];
		
		if(result[0][10]!=null&&result[0][10]>0)
		{
			document.getElementById('GetEndPeriodPartId').style.display = "";
			fm.all("IsGetEndPeriodFlag")[0].checked = true;
			
		}else{
			document.getElementById('GetEndPeriodPartId').style.display = "none";
			fm.all("IsGetEndPeriodFlag")[1].checked = true;
		}
		
    	document.getElementById("GetEndUnit").value=result[0][17];
		document.getElementById("EndDateCalRef").value=result[0][19];
		document.getElementById("EndDateCalMode").value=result[0][21];
		
		document.getElementById("GetYearFlagS").value=result[0][12];
		document.getElementById("StartDateCalRefS").value=result[0][14];
		document.getElementById("StartDateCalModeS").value=result[0][16];
		
		document.getElementById("GetEndUnitS").value=result[0][18];
		document.getElementById("EndDateCalRefS").value=result[0][20];
		document.getElementById("EndDateCalModeS").value=result[0][22];
		
		document.getElementById("zeroGetFlag").value=result[0][23];
		document.getElementById("UrgeGetFlag").value=result[0][24];
		document.getElementById("GetType1S").value = "";
		document.getElementById("GetType1").value=result[0][25];
		document.getElementById("GetType3").value=result[0][26];
		document.getElementById("GetIntv").value=result[0][27];
		document.getElementById("CanGet").value=result[0][28];
		document.getElementById("NeedCancelAcc").value=result[0][29];
		document.getElementById("PCalCodeAmnt").value=result[0][30];
		document.getElementById("RCalAmntCode").value=result[0][32];
		document.getElementById("RCalAmntFlag").value=result[0][31];
		document.getElementById("zeroGetFlagS").value = "";
		document.getElementById("UrgeGetFlagS").value = "";
		showOneCodeName('pd_yesno', 'zeroGetFlag', 'zeroGetFlagS');
		showOneCodeName('pd_yesno', 'RCalAmntFlag', 'RCalAmntFlagS');
		showOneCodeName('pd_urgegetflag', 'UrgeGetFlag', 'UrgeGetFlagS');
		showOneCodeName('pd_gettype1', 'GetType1', 'GetType1S');
		showOneCodeName('pd_gettype3', 'GetType3', 'GetType3S');
		showOneCodeName('pd_canget', 'CanGet', 'CanGetS');
		showOneCodeName('pd_needcancelacc', 'NeedCancelAcc', 'NeedCancelAccS');
		showOneCodeName('pd_paystartyearflag', 'GetYearFlag1', 'GetYearFlag1S');
		/*  
		''showOneCodeName('pd_needacc', 'needAccGet', 'needAccGetS');
		showOneCodeName('insuyearflag', 'GetYearFlag', 'GetYearFlagS');
		showOneCodeName('startdatecalref', 'StartDateCalRef', 'StartDateCalRefS');
		showOneCodeName('startdatecalmode', 'StartDateCalMode', 'StartDateCalModeS');
		showOneCodeName('insuyearflag', 'GetEndUnit', 'GetEndUnitS');
		showOneCodeName('startdatecalref', 'EndDateCalRef', 'EndDateCalRefS');
		showOneCodeName('enddatecalmode', 'EndDateCalMode', 'EndDateCalModeS');
		*/
	}
	//-------- add by jucy
	//���ӵ�ѡ������������ĳ�ʼ��
	//var checkGetYear = fm.all('GetYear').value;
	//if(checkGetYear!=null && checkGetYear=="0" && checkGetYear!=""){
	//	fm.IsGetYearFlag[1].checked=true;
	//	closeGetYearPart();
	//}else{
		fm.IsGetYearFlag[0].checked=true;
		showGetYearPart();
	//}
	//var checkGetEndPeriod = fm.all('GetEndPeriod').value;
	//if(checkGetEndPeriod!=null && checkGetEndPeriod=="0" &&checkGetEndPeriod!="" ){
	//	fm.IsGetEndPeriodFlag[1].checked=true;
	//	closeGetEndPeriodPart();
	//}else{
		fm.IsGetEndPeriodFlag[0].checked=true;
		showGetEndPeriodPart();
	//}
	//-------- 
	//-------- end
}

function afterRadioM14Select(){
	//-------- add by jucy
	//ѡ��ͬ���������Σ���մ˲㼶�µ����ж�Ӧ������
	initPayPlan();
	initGetDuty();
	//-------- end
	
	document.getElementById("DutyShowPart").style.display = "";
	
	var selNo = Mulline14Grid.getSelNo()-1;
	var dutyCode = Mulline14Grid.getRowColData(selNo,1);
	var dutyName = Mulline14Grid.getRowColData(selNo,2);
	fmA.all('DutyCode').value=dutyCode;
	fmA.all('DutyName').value=dutyName;
	
	fm.all('DutyCodeS').value = dutyCode;
	
	queryMulline11Grid();
	queryMulline9Grid();//���ֽɷ�
	
	//var sqlStr = "select ChoFlag from pd_lmriskduty where dutycode = '" + dutyCode + "' and riskcode = '" + document.getElementById("RiskCode").value + "'";
   
	var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql20";
	mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(dutyCode);//ָ������Ĳ���
		mySql1.addSubPara(document.getElementById("RiskCode").value);//ָ������Ĳ���
	var sqlStr = mySql1.getString();
	
   var result = easyExecSql(sqlStr,1,1,1);
  // alert('1');
	if(result != null){
		fmA.all('choFlag').value=result[0][0];
		if(fmA.all('choFlag').value == ''){
			myAlert(fmA.all('choFlag').value);
		}else if(fmA.all('choFlag').value == 'M'){
			fmA.all('choFlagS').value= '��ѡ';
		}else if(fmA.all('choFlag').value == 'C'){
			fmA.all('choFlagS').value= '��ѡ';
		}
	}
		
		//var sqlStr1 = "select GetYear,BasicCalCode,VPU,InsuYear,PayEndDateCalRef,PayEndDateCalMode,PayEndYearRela,GetYearFlag,GetYearRela,CalMode,AmntFlag,InsuYearFlag,InsuYearRela from pd_lmduty where dutycode = '" + dutyCode + "'";
	var mySql2=new SqlClass();
	var sqlid2 = "PDRiskDefiInputSql21";
	mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(dutyCode);//ָ������Ĳ���
	var sqlStr1 = mySql2.getString();
	var result1 = easyExecSql(sqlStr1,1,1,1);
	var duty = ['GetYear','BasicCalCode','VPU','InsuYear','PayEndDateCalRef','PayEndDateCalMode','PayEndYearRela','GetYearFlag','GetYearRela','CalMode','AmntFlag','InsuYearFlag','InsuYearRela','PCalMode'];
	var codetype = ['GetYear','BasicCalCode','VPU','InsuYear','pd_payenddatecalref','pd_payenddatecalmode','payendyearrela','getyearflag','getyearrela','pd_calmode','pd_amntflag','insuyearflag', 'insuyearrela','pcalmode'];
	var resultS ;
	if(result1){
		fmA.all('GetYear').value=result1[0][0];
		fmA.all('BasicCalCode').value=result1[0][1];
		fmA.all('VPU').value=result1[0][2];
		fmA.all('InsuYear').value=result1[0][3];
		for( var i=4;i<duty.length;i++){
			if(result1[0][i]=='')continue;
			document.getElementById(duty[i]).value= result1[0][i];
			//  var sql = "select trim(codename) from ldcode where trim(code)='"+result1[0][i]+"' and codetype='"+codetype[i]+"'";
			var mySql3=new SqlClass();
			var sqlid3 = "PDRiskDefiInputSql22";
			mySql3.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(result1[0][i]);//ָ������Ĳ���
			mySql3.addSubPara(codetype[i]);//ָ������Ĳ���
			var sql = mySql3.getString();
			resultS = easyExecSql(sql,1,1,1);
			if(resultS){
				document.getElementById(duty[i]+'S').value= resultS[0][0];
			}			   
		}
		
		//-------- add by jucy
		if(result1[0][13] == "Y"){
			fmA.all('PCalMode').value=result1[0][13];
			fmA.all('PCalModeS').value='��Ҫ���л���';
		}else if(result1[0][13] == "N"){
			fmA.all('PCalMode').value=result1[0][13];
			fmA.all('PCalModeS').value='����Ҫ���л���';
		}else{
			fmA.all('PCalMode').value="";
			fmA.all('PCalModeS').value="";
		}
		if(result1[0][15] == "A"){
			fmA.all('DutyPayEndYearFlag').value=result1[0][15];
			fmA.all('DutyPayEndYearFlagS').value='����';
		}else if(result1[0][15] == "D"){
			fmA.all('DutyPayEndYearFlag').value=result1[0][15];
			fmA.all('DutyPayEndYearFlagS').value='��';
		}else if(result1[0][15] == "M"){
			fmA.all('DutyPayEndYearFlag').value=result1[0][15];
			fmA.all('DutyPayEndYearFlagS').value='��';
		}else if(result1[0][15] == "Y"){
			fmA.all('DutyPayEndYearFlag').value=result1[0][15];
			fmA.all('DutyPayEndYearFlagS').value='��';
		}else{
			fmA.all('DutyPayEndYearFlag').value="";
			fmA.all('DutyPayEndYearFlagS').value="";
		}
		fmA.all('DutyPayEndYear').value=result1[0][14];
		//-------- end
		//-------- delete by jucy
		//if(result1[0][7]!=null&&result1[0][7]!=''||result1[0][8]!=null||result1[0][8]!=''){
		//-------- end
	}
	
	//-------- add by jucy
	//���ӵ�ѡ���ʼ������ʾ�������
	var checkGetYear = fmA.all('InsuYear').value;
	//if(checkGetYear!=null && checkGetYear=="0" && checkGetYear!=""){
	//	fmA.IsInsuYear[1].checked=true;
	//	closeDivInsuYear();
	//}else{
		fmA.IsInsuYear[0].checked=true;
		showDivInsuYear();
	//}
	//-------- end 
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ִ���";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�ɷ����δ���";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ɷ���������";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="���δ���";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=3;
		
		iArray[5]=new Array();
		iArray[5][0]="��������";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		/*
		iArray[5]=new Array();
		iArray[5][0]="��ѡ���";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=2;
		iArray[5][10]="pdriskduty";
		iArray[5][11]="0|^M|����^C|��ѡ";
		*/
		
		Mulline9Grid= new MulLineEnter( "fm" , "Mulline9Grid" ); 
		
		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName ="afterRadioSelect";
		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}


function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="�ɷѱ���";
		iArray[1][1]="100px";
		iArray[1][2]=80;
		iArray[1][3]=3;
		
		iArray[1]=new Array();
		iArray[1][0]="�ɷѼ��";
		iArray[1][1]="100px";
		iArray[1][2]=80;
		iArray[1][3]=0;

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=0;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline11Grid()
{	

	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ִ���";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�������δ���";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="������������";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="���δ���";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=3;
		
		iArray[5]=new Array();
		iArray[5][0]="��������";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.selBoxEventFuncName ="afterRadioSelect2";

		Mulline11Grid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}


function initMulline12Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ձ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;


		Mulline12Grid= new MulLineEnter( "fmF" , "Mulline12Grid" ); 

		Mulline12Grid.mulLineCount=1;
		Mulline12Grid.displayTitle=1;
		Mulline12Grid.canSel=0;
		Mulline12Grid.canChk=0;
		Mulline12Grid.hiddenPlus=0;
		Mulline12Grid.hiddenSubtraction=0; 
		//Mulline12Grid.selBoxEventFuncName ="afterRadioSelect";
		Mulline12Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline13Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�����ձ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="����������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;


		Mulline13Grid= new MulLineEnter( "fmF" , "Mulline13Grid" ); 

		Mulline13Grid.mulLineCount=1;
		Mulline13Grid.displayTitle=1;
		Mulline13Grid.canSel=0;
		Mulline13Grid.canChk=0;
		Mulline13Grid.hiddenPlus=0;
		Mulline13Grid.hiddenSubtraction=0; 
		//Mulline12Grid.selBoxEventFuncName ="afterRadioSelect";
		Mulline13Grid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}


function initMulline14Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���δ���";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="50px";
		iArray[2][2]=100;
		iArray[2][3]=0;
	
		iArray[3]=new Array();
		iArray[3][0]="��ѡ���";
		iArray[3][1]="50px";
		iArray[3][2]=100;
		iArray[3][3]=2;
		iArray[3][10]="pdriskduty";
		iArray[3][11]="0|^M|����^C|��ѡ";
		
		Mulline14Grid= new MulLineEnter( "fmA" , "Mulline14Grid" ); 

		Mulline14Grid.mulLineCount=0;
		Mulline14Grid.displayTitle=1;
		Mulline14Grid.canSel=1;
		Mulline14Grid.canChk=0;
		Mulline14Grid.hiddenPlus=1;
		Mulline14Grid.hiddenSubtraction=1;
		Mulline14Grid.selBoxEventFuncName ="afterRadioM14Select";
		Mulline14Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}


var mainSubRiskResult = '';

function initRisk(){
	var riskcode ="<%=request.getParameter("riskcode")%>";
	
	//var sql = "select a.riskname, kindcode,riskProp,RISKTYPE1,RiskTypeDetail,RiskPeriod,PolType,SubRiskFlag,BonusFlag,ListFlag,SignDateCalMode,InsuredFlag,MngCom,RiskType3,NeedGetPolDate,SpecFlag,riskflag,bonusmode,RiskType4,RiskType5,RiskType7,RiskTypeAcc from pd_lmrisk a inner join pd_lmriskapp b on a.riskcode=b.riskcode and a.riskcode='"+"<%=request.getParameter("riskcode")%>"+"'";

 var mySql2=new SqlClass();
	var sqlid2 = "PDRiskDefiInputSql23";
	mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(riskcode);//ָ������Ĳ���
	var sql = mySql2.getString();
	mainSubRiskResult = easyExecSql(sql,1,1,1);

	//var duty = ['RiskName','riskType','riskProp','RISKTYPE1','RiskTypeDetail','RiskPeriod','PolType','SubRiskFlag','BonusFlag','ListFlag','SignDateCalMode','InsuredFlag','MngCom','RiskType3','NeedGetPolDate','SpecFlag','RiskFlag','BonusMode','RiskType4','RiskType5','RiskType7','RiskTypeAcc','SaleFlag','AutoPayType','AutoETIType','RiskType2','RiskType9','CancleForeGetSpecFlag'];
	var duty = ['RiskName','riskType','riskProp','RISKTYPE1','RiskTypeDetail','RiskPeriod','PolType','SubRiskFlag','BonusFlag','ListFlag','SignDateCalMode','InsuredFlag','MngCom','RiskType3','NeedGetPolDate','SpecFlag','RiskFlag','BonusMode','RiskType4','RiskType5','RiskType7','RiskTypeAcc','SaleFlag','AutoPayType','AutoETIType','RiskType2','RiskType9','CancleForeGetSpecFlag'];
	//var codetype = ['pd_yesno','pd_kindcode','pd_riskprop','pd_risktype1','pd_risktypedetail','pd_riskperiod1','pd_poltype','subriskflag1','pd_bonusflag1','pd_listflag','pd_signdatecalmode','pd_insuredflag','pd_mngcom','pd_risktype3','pd_needgetpoldate','pd_specflag','pd_riskflag','bonusgetmode','pd_risktype4','pd_risktype5','pd_risktype71','pd_risktypeacc','pd_saleflag','pd_autopaytype','pd_autoetitype','pd_risktype2','pd_risktype91','pd_nforegetspecflag1'];
	var codetype = ['pd_yesno','pd_kindcode','pd_riskprop','pd_risktype1','pd_risktypedetail','pd_riskperiod','pd_poltype','pd_subriskflag','pd_bonusflag','pd_listflag','pd_signdatecalmode','pd_insuredflag','pd_mngcom','pd_risktype3','pd_needgetpoldate','pd_specflag','pd_riskflag','bonusgetmode','pd_risktype4','pd_risktype5','pd_risktype7','pd_risktypeacc','pd_saleflag','pd_autopaytype','pd_autoetitype','pd_risktype2','pd_risktype91','pd_nforegetspecflag1'];
	var resultS ;
	if(mainSubRiskResult){
		for( var i=0;i<duty.length;i++){
			//alert(duty[i]+":"+mainSubRiskResult[0][i]);
			if(mainSubRiskResult[0][i]!=null&&mainSubRiskResult[0][i]!='')
			{
		   document.getElementById(duty[i]).value= mainSubRiskResult[0][i];   
		   if(i>0&&i<codetype.length){
			   //var sql = "select trim(codename) from ldcode where trim(code)='"+mainSubRiskResult[0][i]+"' and codetype='"+codetype[i]+"'";
			   var mySql3=new SqlClass();
				var sqlid3 = "PDRiskDefiInputSql22";
				mySql3.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
				mySql3.addSubPara(mainSubRiskResult[0][i]);//ָ������Ĳ���
				mySql3.addSubPara(codetype[i]);//ָ������Ĳ���
					var sql = mySql3.getString();
			   resultS = easyExecSql(sql,1,1,1);
			   if(resultS){
			   	document.getElementById(duty[i]+'S').value= resultS[0][0];
			   }
		   }
		  } 
		}
	}	
	//��ʼ����Ʒ�ۿ۱��
//	sql = "select 1 from pd_lmdiscount where riskcode='" + riskcode + "'";
 var mySql4=new SqlClass();
				var sqlid4 = "PDRiskDefiInputSql24";
				mySql4.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
				mySql4.addSubPara(riskcode);//ָ������Ĳ���
			
				sql = mySql4.getString();
	resultS = easyExecSql(sql,1,1,1);
	if(resultS){
		fmF.all('prodDisFlag')[0].checked = true;
		showDivProdDis();
	}
	//alert('stop');
	addMainOrSubRisk(riskcode);
	
	//	var sql = "select RiskShortName,RiskEnName,RiskEnShortName,OrigRiskCode from pd_lmrisk where riskcode='"+"<%=request.getParameter("riskcode")%>"+"'";
var mySql5=new SqlClass();
				var sqlid5 = "PDRiskDefiInputSql25";
				mySql5.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
				mySql5.addSubPara(riskcode);//ָ������Ĳ���
			
				sql = mySql5.getString();

	var result1 = easyExecSql(sql,1,1,1);
	 if(result1){
	document.getElementById("RiskShortName").value =result1[0][0];
	document.getElementById("RiskEnName").value =result1[0][1];
	document.getElementById("RiskEnShortName").value =result1[0][2];
	document.getElementById("OrigRiskCode").value =result1[0][3];

}

	//var sql = "select StartDate,EndDate,MinAppntAge,MaxAppntAge,MaxInsuredAge,MinInsuredAge from pd_lmriskapp where riskcode='"+"<%=request.getParameter("riskcode")%>"+"'";
var mySql6=new SqlClass();
				var sqlid6 = "PDRiskDefiInputSql26";
				mySql6.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
				mySql6.addSubPara(riskcode);//ָ������Ĳ���
			
				sql = mySql6.getString();
	var result1 = easyExecSql(sql,1,1,1);
	 if(result1){
	document.getElementById("StartDate").value =result1[0][0];
	document.getElementById("EndDate").value =result1[0][1];
	document.getElementById("MinAppntAge").value =result1[0][2];
	document.getElementById("MaxAppntAge").value =result1[0][3];
	document.getElementById("MaxInsuredAge").value =result1[0][4];
	document.getElementById("MinInsuredAge").value =result1[0][5];
}
//alert('stop');
	
}

function addMainOrSubRisk(riskcode){
	if(document.getElementById('SubRiskFlag').value == 'S'){
		document.getElementById('SubRiskFlagDiv').style.display = "";
		document.getElementById('MainRiskFlagDiv').style.display = "none";
		//var	sql = "select riskcode from pd_lmriskrela where relariskcode ='"+riskcode+"'";	
		var mySql7=new SqlClass();
		var sqlid7 = "PDRiskDefiInputSql27";
		mySql7.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(riskcode);//ָ������Ĳ���
			
		var	sql = mySql7.getString();
					
		mainSubRiskResult = easyExecSql(sql,1,1,1);
		var result2 ='';
		if(mainSubRiskResult){	
			for(var  i =0; i<mainSubRiskResult.length; i++){
				Mulline12Grid.setRowColData(i,1,mainSubRiskResult[i][0]);
				//	var sql = "select riskname from pd_lmrisk where riskcode ='"+mainSubRiskResult[i][0]+"'";
				var mySql8=new SqlClass();
				var sqlid8 = "PDRiskDefiInputSql28";
				mySql8.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
				mySql8.addSubPara(mainSubRiskResult[i][0]);//ָ������Ĳ���
				var	sql = mySql8.getString();
				result2 = easyExecSql(sql,1,1,1);
				if(result2){
					Mulline12Grid.setRowColData(i,2,result2[0][0]);
				}
				Mulline12Grid.addOne();
			}
			Mulline12Grid.delBlankLine();		
		}
	}
	
	//alert('stop1');
	
	if(document.getElementById('SubRiskFlag').value == 'M' || document.getElementById('SubRiskFlag').value == 'A'){
		document.getElementById('MainRiskFlagDiv').style.display = "";
		document.getElementById('SubRiskFlagDiv').style.display = "none";
		
		//alert('1111')
		//var sql = "select relariskcode from pd_lmriskrela where riskcode ='"+riskcode+"'";
		var mySql9=new SqlClass();
		var sqlid9 = "PDRiskDefiInputSql29";
		mySql9.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(riskcode);//ָ������Ĳ���
		var	sql = mySql9.getString();		
		mainSubRiskResult = easyExecSql(sql,1,1,1);
		if(mainSubRiskResult){	
			for(var  i =0; i<mainSubRiskResult.length; i++){
				Mulline13Grid.setRowColData(i,1,mainSubRiskResult[i][0]);
				//sql = "select riskname from pd_lmrisk where riskcode ='"+mainSubRiskResult[i][0]+"'";
				var mySql8=new SqlClass();
				var sqlid8 = "PDRiskDefiInputSql28";
				mySql8.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
				mySql8.addSubPara(mainSubRiskResult[i][0]);//ָ������Ĳ���
				var	sql = mySql8.getString();
				var result2 = easyExecSql(sql,1,1,1);
				if(result2){					
					Mulline13Grid.setRowColData(i,2,result2[0][0]);
				}
					Mulline13Grid.addOne();
			}		
			Mulline13Grid.delBlankLine();		
		}
	}
		//alert('stop2');
}

//��ʼ����ѡ��
function initRadio(){
	//document.all("GetFlag")[0].checked=true;
	initISPayStartYear();
	//var selectSQL = "select getflag, RnewFlag, InsuAccFlag,ChoDutyFlag,CPayFlag,RinsFlag from pd_lmrisk where riskcode = '" + document.getElementById("RiskCode").value +"'";
	var mySql9=new SqlClass();
			var sqlid9 = "PDRiskDefiInputSql30";
				mySql9.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
				mySql9.addSubPara(document.getElementById("RiskCode").value);//ָ������Ĳ���
			var	selectSQL = mySql9.getString();		
	
	
	var crr = easyExecSql(selectSQL);
	if(crr != null){
		var GetFlag = crr[0][0];
		
		//getFlag ����������
		if(GetFlag == 'N'){
			document.all("GetFlag")[1].checked=true;
		}else if(GetFlag == 'Y'){
			document.all("GetFlag")[0].checked=true;
		}else{
	
		}
		
		//UWFlag �������
		var RnewFlag = crr[0][1];
		if(RnewFlag == 'N'){
			document.all("RnewFlag")[1].checked=true;
		}else if(RnewFlag == 'Y'){
			document.all("RnewFlag")[0].checked=true;
		}else{
		
		}
		
		//InsuAccFlag �˻������ֱ��
		var InsuAccFlag = crr[0][2];
		if(InsuAccFlag == 'N'){
			document.all("InsuAccFlag")[1].checked=true;
		}else if(InsuAccFlag == 'Y'){
			document.all("InsuAccFlag")[0].checked=true;
			showDivAccInsu();
		}else{
		
		}
		
			//ChoDutyFlag ���ο�ѡ���
		var ChoDutyFlag = crr[0][3];
		if(ChoDutyFlag == 'N'){
			document.all("ChoDutyFlag")[1].checked=true;
		}else if(ChoDutyFlag == 'Y'){
			document.all("ChoDutyFlag")[0].checked=true;
		}else{
		
		}
		
		//CPayFlag �����շѱ��
		var CPayFlag = crr[0][4];
		if(CPayFlag == 'N'){
			document.all("CPayFlag")[1].checked=true;
		}else if(CPayFlag == 'Y'){
			document.all("CPayFlag")[0].checked=true;
		}else{
		
		}
		
		//RinsFlag �ֱ����
		var RinsFlag = crr[0][5];
		if(RinsFlag == 'N'){
			document.all("RinsFlag")[1].checked=true;
		}else if(RinsFlag == 'Y'){
			document.all("RinsFlag")[0].checked=true;
		}else{
		
		}
		
	
		//alert('stop1');
		//var selectSQL = "select AutoPayFlag,InvestFlag,CutAmntStopPay,loanflag,MortagageFlag from pd_lmriskapp where riskcode = '" + document.getElementById("RiskCode").value + "'";	
		
		var mySql10=new SqlClass();
			var sqlid10 = "PDRiskDefiInputSql31";
				mySql10.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
				mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
				mySql10.addSubPara(document.getElementById("RiskCode").value);//ָ������Ĳ���
			var selectSQL1 = mySql10.getString();		

		var crr3 = easyExecSql(selectSQL1);
		//	alert('stop2');
		//var sel = "select lifetype from pd_lmrisk where riskcode = '" + document.getElementById("RiskCode").value + "'";
		//var crr1 = easyExecSql(sel);
		//var Lifttype = crr1[0][0];

		var AutoPayFlag = crr3[0][0];
		//alert(AutoPayFlag);
		if(AutoPayFlag == 'Y'){
			document.all("AutoPayFlag")[0].checked=true;
		}else{
			document.all("AutoPayFlag")[1].checked=true;
		}
		
		var InvestFlag = crr3[0][1];
		if(InvestFlag == "Y"){
			document.all("InvestFlag")[0].checked=true;
		}else if(InvestFlag == "N"){
			document.all("InvestFlag")[1].checked=true;
		}else{
		
		}
		
		var CutAmntStopPay = crr3[0][2];
		if(CutAmntStopPay == 'Y'){
			document.all("CutAmntStopPay")[0].checked=true;
		}else if(CutAmntStopPay == 'N'){
			document.all("CutAmntStopPay")[1].checked=true;
		}else{
		
		}		
		
		var LoanFlag = crr3[0][3];
		//alert(LoanFlag);
		if(LoanFlag == 'Y'){
			document.all("LoanFlag")[0].checked=true;
		}else if(LoanFlag == 'N'){
			document.all("LoanFlag")[1].checked=true;
		}
		//MortagageFlag
		var MortagageFlag = crr3[0][4];
		//alert(LoanFlag);
		if(MortagageFlag == 'Y'){
			document.all("MortagageFlag")[0].checked=true;
		}else if(MortagageFlag == 'N'){
			document.all("MortagageFlag")[1].checked=true;
		}
		var AutoETIFlag= crr3[0][5];
		if(AutoETIFlag == 'Y'){
			document.all("AutoETIFlag")[0].checked=true;
		}else if(AutoETIFlag == 'N'){
			document.all("AutoETIFlag")[1].checked=true;
		}
		var AutoCTFlag= crr3[0][6];
		if(AutoCTFlag == 'Y'){
			document.all("AutoCTFlag")[0].checked=true;
		}else if(AutoCTFlag == 'N'){
			document.all("AutoCTFlag")[1].checked=true;
		}
		var NonParFlag= crr3[0][7];
		if(NonParFlag == 'Y'){
			document.all("NonParFlag")[0].checked=true;
		}else if(NonParFlag == 'N'){
			document.all("NonParFlag")[1].checked=true;
		}
		var BackDateFlag= crr3[0][8];
		if(BackDateFlag == 'Y'){
			document.all("BackDateFlag")[0].checked=true;
		}else if(BackDateFlag == 'N'){
			document.all("BackDateFlag")[1].checked=true;
		}
	}
}

function initCheckBox(){
	var riskCode = document.getElementById("RiskCode").value ;
	
	var sql = "select count(*) from pd_lmriskpayintv where riskcode = '" + riskCode + "'";
	var crr = easyExecSql(sql, 1, 1, 1);
	var resultLen = crr[0][0];
	
	sql = "select payintv from pd_lmriskpayintv where riskcode = '" + riskCode + "'";
	var codeSelect = "select count(trim(CodeName)) from ldcode where 1 = 1 and codetype = 'pd_payintv' order by Code";
	var crr3 = easyExecSql(codeSelect, 1, 1, 1);
	var selectLen = crr3[0][0];
	
	codeSelect = "select trim(Code), trim(CodeName), trim(CodeAlias), trim(ComCode), trim(OtherSign) from ldcode where 1 = 1 and codetype = 'pd_payintv' order by to_number(Code)";
	var crr2 = easyExecSql(codeSelect, 1, 1, 1);
	
	var crr1 = easyExecSql(sql, 1, 1, 1);
	for(var i = 0; i < resultLen; i ++ ){
		var result = crr1[i][0];
		for(var j = 0; j < selectLen; j ++){
			if(result == crr2[j][0]){
				document.all("payintv")[j].checked=true;
			}
		}
	}
}

function initCalmode(){
	var sqlStr = "select codename from ldcode where codetype = 'calmodesel'";
	var crr = easyExecSql(sqlStr, 1, 1, 1);
	if(crr)
	{
		var result = crr[0][0];
	
		var resultLen = result.length;
	
		if(resultLen < 6){
			for(var i = resultLen ; i < 6; i ++){
				result = '0' + result;
			}
		}
	
		fm.all('payCalCode').value = result;
	}
}

function initGetCalMode(){
	var sqlStr = "select codename from ldcode where codetype = 'calmodegetsel'";
	var crr1 = easyExecSql(sqlStr, 1, 1, 1);
	if(crr1)
	{
		var result1 = crr1[0][0];
	
		fm.all('getCalCode').value = result1;
	}
}

function initISPayStartYear(){
  fm.all('ISPayStartYear')[0].checked=false;
  fm.all('ISPayStartYear')[1].checked=true;
  fm.all('PayStartYear').value="";
  fm.all('PayStartYearFlag').value="";
  fm.all('PayStartFlagName').value="";
  fm.all('PayStartDateCalRef').value="";
  fm.all('PaySDCalRefName').value="";
  fm.all('PayStartDateCalMode').value="";
  fm.all('PaySDCalModeName').value="";
  closePayStartCtrl();
}

function initRiskPay(){
	fmP.all('GracePeriod2').value="";
	fmP.all('GracePeriodUnit').value="";
	fmP.all('GPeriodUnitName').value="";
	//-------- update by jucy 
	//GRACEDATECALMODE ���������¼�룬ϵͳ�Զ��洢Ϊ0
	fmP.all('GraceDateCalMode').value="0";
	fmP.all('GDateCalModeName').value="�Լ���Ϊ׼";
	//-------- end
	fmP.all('GraceCalCode').value="";
	fmP.all('OverdueDeal').value="";
	fmP.all('OverdueDealName').value="";
	fmP.all('UrgePayFlag').value="";
	fmP.all('UrgePayFlagName').value="";
}
</script>
