//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;

var turnPage = new turnPageClass();
window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus(){
	if(showInfo!=null){
		try{
			//showInfo.focus();
		}
		catch(ex){
			showInfo=null;
		}
	}
}

//�ύ�����水ť��Ӧ����
function submitForm(){
	//if (!beforeSubmit())
	//{
	//	return false;
	//}

  if(!verifyInput2())
  {
  	return false;
  }

	if (!confirm('ȷ�����Ĳ���')){
		return false;
	}

	fm.all('mOperate').value = "INSERT||MAIN";
	var i = 0;
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

	fm.submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	//Modify by lujun 20060816  
	/*if(typeof(showInfo)=="object")
	{
		showInfo.close();
		if(typeof(showInfo.parent)=="object")
		{
			showInfo.parent.focus();
			if(typeof(showInfo.parent.parent)=="object")
			{
				showInfo.parent.parent.blur();
			}
		}
	}*/
	
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else{
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	easyQueryClick();
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm(){
	try{
		initForm();
	}
	catch(re){
		alert("��LAAgent.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

//ȡ����ť��Ӧ����
function cancelForm(){
	showDiv(operateButton,"true");
	showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit(){
	//������ֹ������ϸ
	var lineCount = 0;
	RiskFeeGrid.delBlankLine("RiskFeeGrid");
	lineCount = RiskFeeGrid.mulLineCount;
	for (i=0;i<lineCount;i++){
		//alert(RiskFeeGrid.getSelNo(i));
		if (RiskFeeGrid.getSelNo(i))
		{
			return true;
		}
	}
	alert("��ѡ�����ֹ�����ã�");
	return false;
}

//Click�¼������������ͼƬʱ�����ú���
function addClick(){
	//����������Ӧ�Ĵ���
	if (fm.all('initOperate').value == 'INSERT'){
		mOperate="INSERT||MAIN";
		showDiv(operateButton,"false");
		showDiv(inputButton,"true");
		//fm.all('AgentCode').value = '';
		if (fm.all('AgentCode').value !='')
			resetForm();
	}
	else
		alert('�ڴ˲���������');
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick(){
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick(){
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick(){
	//if (!beforeSubmit())
	//{
		//return false;
	//}
	fm.all('mOperate').value = "DELETE||MAIN";
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
	fm.submit(); //�ύ
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow){
	if (cShow=="true"){
		cDiv.style.display="";
	}
	else{
		cDiv.style.display="none";
	}
}

function easyQueryClick(){
	divRiskFeeParam.style.display='none';	
	initRiskFeeGrid();
	if (fm.all('RiskCode').value ==""){
		alert("��ѡ��������Ϣ��");
		fm.all('RiskCode').focus();
		return false;
	}
	
	var strSQL = "";
	var rs=fm.all('RiskCode').value;
	if(rs=="139")
	{
		strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,a.FeeCalMode,a.FeeCalCode,a.FeeValue,a.CompareValue,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'�Ѵ�' "
		+ "from LCGrpFee a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.FeeCode = b.FeeCode and a.InsuAccNo = b.InsuAccNo and a.PayPlanCode = b.PayPlanCode "
		+ "and a.GrpPolNO = '"+fm.all('GrpPolNo').value+"' "
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"')) "
		//+ " and b.feecode='390002'"
		;
}else{
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,a.FeeCalMode,a.FeeCalCode,a.FeeValue,a.CompareValue,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'�Ѵ�' "
		+ "from LCGrpFee a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.FeeCode = b.FeeCode and a.InsuAccNo = b.InsuAccNo and a.PayPlanCode = b.PayPlanCode  "
		+ "and a.GrpPolNO = '"+fm.all('GrpPolNo').value+"' "
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"'))";
	}
	//prompt("�Ѵ����Ѳ�ѯ",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		//alert("���ֹ������ϸ��ѯʧ�ܣ�");
		//return false;
	}
	else{
	//	fm.save.disabled=true;
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = RiskFeeGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
  if(rs=="139"){
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,b.FeeCalMode,b.FeeCalCode,b.FeeValue,case when b.CompareValue is null then 0 else b.CompareValue end,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'δ��' "
		+ "from LCGrpPol a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.GrpPolNo = '"+fm.all('GrpPolNo').value+"' and b.payplancode not in "
		+ "(select payplancode from LCGrpFee where GrpPolNo = '"+fm.all('GrpPolNo').value+"')"
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"')) "
		//+ " and b.feecode='390002'"
		;
	}else{
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,b.FeeCalMode,b.FeeCalCode,b.FeeValue,case when b.CompareValue is null then 0 else b.CompareValue end,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'δ��' "
		+ "from LCGrpPol a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		//+ " and b.FeeCode = '000001' "
		+ "and a.GrpPolNo = '"+fm.all('GrpPolNo').value+"' and b.InsuAccNo not in "
		+ "(select InsuAccNo from LCGrpFee where GrpPolNo = '"+fm.all('GrpPolNo').value+"')"
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"'))";
}
	//prompt("δ�����Ѳ�ѯ",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
	}
	else{
	//	fm.save.disabled=false;
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		mulLineCount = RiskFeeGrid.mulLineCount;
		for(i=0; i<turnPage.arrDataCacheSet.length; i++){
			RiskFeeGrid.addOne("RiskFeeGrid");
			RiskFeeGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
			RiskFeeGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
			RiskFeeGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
			RiskFeeGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
			RiskFeeGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
			RiskFeeGrid.setRowColData(mulLineCount+i,6,turnPage.arrDataCacheSet[i][5]);
			RiskFeeGrid.setRowColData(mulLineCount+i,7,turnPage.arrDataCacheSet[i][6]);
			RiskFeeGrid.setRowColData(mulLineCount+i,8,turnPage.arrDataCacheSet[i][7]);
			RiskFeeGrid.setRowColData(mulLineCount+i,9,turnPage.arrDataCacheSet[i][8]);
			RiskFeeGrid.setRowColData(mulLineCount+i,10,turnPage.arrDataCacheSet[i][9]);
			RiskFeeGrid.setRowColData(mulLineCount+i,11,turnPage.arrDataCacheSet[i][10]);
			RiskFeeGrid.setRowColData(mulLineCount+i,12,turnPage.arrDataCacheSet[i][11]);
			RiskFeeGrid.setRowColData(mulLineCount+i,13,turnPage.arrDataCacheSet[i][12]);
			RiskFeeGrid.setRowColData(mulLineCount+i,14,turnPage.arrDataCacheSet[i][13]);
			RiskFeeGrid.setRowColData(mulLineCount+i,15,turnPage.arrDataCacheSet[i][14]);
			RiskFeeGrid.setRowColData(mulLineCount+i,16,turnPage.arrDataCacheSet[i][15]);
		}
	}
}

function returnparent(){
	//Modify By Xuys. �������ڽɷ�ҳ��ĵ���
	if (LoadFlag == '99') //����Ϊ�����ڵ���
	{
		var tGrpContNo = fm.all('GrpContNo').value;
		var strSQL="select GrpPolNo, RiskCode from LCGrpPol where GrpContNo='" + tGrpContNo + "'";
		var result = easyExecSql(strSQL);		
		var tGrpPolNo = result[0][0];
		var tRiskCode = result[0][1];		
		window.location.href = "../acc/EIRenewalInput.jsp?GrpContNo="+GrpContNo+"&RiskCode="+tRiskCode+"&GrpPolNo=" + tGrpPolNo;
	}
	else
	{	
	//txh add ��ʼ����һ�����棻
		var tGrpContNo = fm.all('GrpContNo').value ;
		var strSQL="select RiskCode from LCGrpPol where GrpContNo='" + tGrpContNo + "'";
	  var tempRiskCode = easyExecSql(strSQL);
		window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag+ "&RiskCode=" + tempRiskCode;
	}
}

function QueryRiskFeeParam(parm1,parm2){
	if(fm.all(parm1).all('InpRiskFeeGridSel').value == '1'){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		var cFeeCalMode = fm.all(parm1).all('RiskFeeGrid8').value;	//���㷽ʽ
		var cFeeCode = fm.all(parm1).all('RiskFeeGrid5').value;
		var cInsuAccNo = fm.all(parm1).all('RiskFeeGrid1').value;
		var cPayPlanCode = fm.all(parm1).all('RiskFeeGrid3').value;
		/*
		������ѷ�ʽ
		07-�ֵ�����
		08-�ۼƷֵ�����
		��Ҫ��ʼ������¼�����
		*/
		if(cFeeCalMode == '07' || cFeeCalMode == '08'){
			divRiskFeeParam.style.display='';
			initRiskFeeParamGrid();
			var strSQL = "";
			strSQL = "select FeeMin,FeeMax,FeeRate,FeeID,'�Ѵ�' "
				+ "from LCGrpFeeParam "
				+ "where 1=1 "
				+ "and FeeCode = '"+cFeeCode+"' and InsuAccNo = '"+cInsuAccNo+"' "
				+ "and PayPlanCode = '"+cPayPlanCode+"' "
				+ "and GrpPolNo = '"+fm.all('GrpPolNo').value+"'";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			//�ж��Ƿ��ѯ�ɹ�
			if (!turnPage.strQueryResult) {
			}
			else{
				//��ѯ�ɹ������ַ��������ض�ά����
				turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
				turnPage.pageDisplayGrid = RiskFeeParamGrid;
				//����SQL���
				turnPage.strQuerySql = strSQL;
				//���ò�ѯ��ʼλ��
				turnPage.pageIndex = 0;
				//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
				//����MULTILINE������ʾ��ѯ���
				displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
			}

			strSQL = "select FeeMin,FeeMax,FeeRate,FeeID,'δ��' "
				+ "from LMRiskFeeParam "
				+ "where 1=1 "
				+ "and FeeCode = '"+cFeeCode+"' and InsuAccNo = '"+cInsuAccNo+"' "
				+ "and PayPlanCode = '"+cPayPlanCode+"' and FeeCalMode = '"+cFeeCalMode+"' "
				+ "and FeeID not in (select FeeID from LCGrpFeeParam where GrpPolNo = '"+fm.all('GrpPolNo').value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if (!turnPage.strQueryResult) {
			}
			else{
				turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				mulLineCount = RiskFeeParamGrid.mulLineCount;
				for(i=0; i<turnPage.arrDataCacheSet.length; i++){
					RiskFeeParamGrid.addOne("RiskFeeParamGrid");
					RiskFeeParamGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
				}
			}
		}
		else{
			divRiskFeeParam.style.display='none';
			initRiskFeeParamGrid();
		}
	}
}