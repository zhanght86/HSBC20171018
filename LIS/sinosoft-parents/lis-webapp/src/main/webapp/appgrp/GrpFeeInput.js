//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;
var tSearch = 0;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus(){
	if(showInfo!=null){
		try{
			showInfo.focus();
		}
		catch(ex){
			showInfo=null;
		}
	}
}

//�����ύ���޸ģ�
function UptContClick(){
//	if (tSearch == 0){
//		alert("���Ȳ�ѯҪ������Ϣ��");
//		return false;
//	}

	document.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
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
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.submit(); //�ύ
}

function afterCodeSelect( cCodeName, Field ){
	//�ж�˫������ִ�е���ʲô��ѯ
	if (cCodeName=="GrpRisk"){
		var tRiskFlag = document.all('RiskFlag').value;
		//���ڸ����ղ���������¼�������ж���������ΪS��ʱ������
		if (tRiskFlag!="S"){
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
		else{
			divmainriskname.style.display = '';
			divmainrisk.style.display = '';
			document.all('MainRiskCode').value = "";
		}
	}
}

//�ύ�����水ť��Ӧ����
function submitForm(){
	//alert(document.all('mOperate').value);
	if (!beforeSubmit()){
		return false;
	}

	if (!confirm('Ĭ��ֵ�µ�ȫ������Ҫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����')){
		return false;
	}
	
	if (document.all('mOperate').value == ""){
		document.all('mOperate').value = "INSERT||MAIN";
	}

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
function afterSubmit( FlagStr, content ){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		document.all('mOperate').value = "";
	initForm();
	//alert(document.all('mOperate').value);
	QueryCount = 0;	//���³�ʼ����ѯ����
	tSearch = 0;
	}
	//alert(document.all('mOperate').value);
	
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
	if(ContPlanGrid.mulLineCount == 0){
		alert("��ѡ���������Σ�");
		return false;
	}
	
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	//
	var dutycode;
	var hierarhy;
	var flag109;//�Ƿ����109����
	var flagerror;//�������109���ֿ�ѡ���εĵ��δ��ڻ���������Ϊ1
	var duty1;
	var duty2;
	var duty3;
	var duty4;
	//���Ҫ��ֵ��ϢУ��
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,5);
      	dutycode=ContPlanGrid.getRowColData(i,3);
      	hierarhy=ContPlanGrid.getRowColData(i,8);
      	//alert(dutycode);
      	//return;
      	//���Ӷ�109������У��
      	//alert("ok");
      	if(dutycode.substring(0,3)=="109")
      	{
      		flag109="1";
      	}
      	if(dutycode=="109001" && sCalMode=="StandbyFlag1")
      	{
      		duty1=hierarhy;
      		//alert(duty1);
      	}else if(dutycode=="109002" && sCalMode=="StandbyFlag1")
      		{
      			duty2=hierarhy;
      			//alert(duty2);
      		}else if(dutycode=="109003" && sCalMode=="StandbyFlag1")
      			{
      				duty3=hierarhy;
      				//alert(duty3);
      			}else if(dutycode=="109004" && sCalMode=="StandbyFlag1")
      				{
      					duty4=hierarhy;
      					//alert(duty4);
      				}
      	//alert(sCalMode);
      	//����У��
      	if (sCalMode == "Amnt" || sCalMode == "FloatRate" || sCalMode == "InsuYear" || sCalMode == "Mult" || sCalMode == "Prem" || sCalMode == "PayEndYear" || sCalMode == "Count" || sCalMode == "GetLimit" || sCalMode == "GetRate" || sCalMode == "PeakLine")
      	{
			if (sValue!=""){
				if (!isNumeric(sValue)){
					alert("��¼�����֣�");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "CalRule")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="2" && sValue!="3" && sValue!="0"){
					alert("��¼����ȷ�ļ������");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "PayIntv")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="3" && sValue!="6" && sValue!="0" && sValue!="12"){
					alert("��¼����ȷ�Ľ��ѷ�ʽ��");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "EndDate")
      	{
			if (sValue!=""){
				if (!isDate(sValue)){
					alert("��¼����ȷ����ֹ���ڣ�");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		//�����㱣��У��
		//alert(sCalMode);
//      	if (sCalMode == "P"){
//			if (sValue==""){
//				alert("��¼�뱣�ѣ�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue=0){
//				alert("���Ѳ���Ϊ0��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//�����㱣��У��
//      	if (sCalMode == "G"){
//			if (sValue==""){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("�����Ϊ0��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//���������㱣�ѱ���У��
//      	if (sCalMode == "O"){
//			if (sValue==""){
//				alert("��¼��Ҫ��ֵ��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("Ҫ��ֵ����Ϊ0��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//¼�뱣�ѱ���У��
//      	if (sCalMode == "I"){
//			if (sValue!=""){
//				if (!isNumeric(sValue)){
//					alert("��¼�����֣�");
//					ContPlanGrid.setFocus(i,8);
//					return false;
//				}
//			}
//		}
	}
	//���Ӷ�109�Ĵ���
	if(duty1!="")
	{
		if(duty2!="")
		{
			if(duty2>duty1)
			{
				flagerror="1";
			}
		}
		if(duty3!="")
		{
			if(duty3>duty1)
			{
				flagerror="1";
			}
		}
		if(duty4!="")
		{
			if(duty4>duty1)
			{
				flagerror="1";
			}
		}
	}
	if(flagerror=="1")
	{
		alert("109���ֵĿ�ѡ���εĵ��β��ܴ��ڻ�������!");
		return false;
	}
	return true;
}

//Click�¼������������ͼƬʱ�����ú���
function addClick(){
	//����������Ӧ�Ĵ���
	if (document.all('initOperate').value == 'INSERT'){
		mOperate="INSERT||MAIN";
		showDiv(operateButton,"false");
		showDiv(inputButton,"true");
		//document.all('AgentCode').value = '';
		if (document.all('AgentCode').value !='')
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

function RiskAddClick(){
	if(document.all('MainRiskCode').value ==""){
		alert("������������Ϣ��");
		document.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	var GrpContNo = document.all('GrpContNo').value;
	//���¼�����������Ϣ������ʾ�����ٴ�¼��
	for (i=0;i<ContPlanGrid.mulLineCount;i++){
		sValue=ContPlanGrid.getRowColData(i,2)
		if (sValue == document.all('RiskCode').value){
			alert("����ӹ������ֱ��ռƻ�Ҫ�أ�");
			return false;
		}
	}

	var getWhere = "(";
	for (i=0;i<lineCount;i++){
		if (ContPlanDutyGrid.getChkNo(i)){
			//alert(ContPlanDutyGrid.getRowColData(i,1));
			getWhere = getWhere + "'"+ContPlanDutyGrid.getRowColData(i,1)+"',"
		}
	}
	if (getWhere == "("){
		alert("��ѡ��������Ϣ");
		return false;
	}
	getWhere = getWhere.substring(0,getWhere.length-1) + ")"
	var MainRiskCode = document.all('MainRiskCode').value;

	var strSQL = "";

	if (QueryCount == 0){	// ��дSQL���
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//�ж��Ƿ��ѯ�ɹ�
		if (!turnPage.strQueryResult) {
			alert("û�и�����������Ҫ����Ϣ��");
			return false;
		}
		QueryCount = 1;
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = ContPlanGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	else{
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) {
			alert("û�и�����������Ҫ����Ϣ��");
			return false;
		}
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		mulLineCount = ContPlanGrid.mulLineCount;
		//alert(mulLineCount);
		for(i=0; i<turnPage.arrDataCacheSet.length; i++){
			ContPlanGrid.addOne("ContPlanGrid");
			ContPlanGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
			ContPlanGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
			ContPlanGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
			ContPlanGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
			ContPlanGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
			ContPlanGrid.setRowColData(mulLineCount+i,6,turnPage.arrDataCacheSet[i][5]);
			ContPlanGrid.setRowColData(mulLineCount+i,7,turnPage.arrDataCacheSet[i][6]);
			ContPlanGrid.setRowColData(mulLineCount+i,8,turnPage.arrDataCacheSet[i][7]);
			ContPlanGrid.setRowColData(mulLineCount+i,10,turnPage.arrDataCacheSet[i][9]);
			ContPlanGrid.setRowColData(mulLineCount+i,11,turnPage.arrDataCacheSet[i][10]);
			ContPlanGrid.setRowColData(mulLineCount+i,12,MainRiskCode);
			ContPlanGrid.setRowColData(mulLineCount+i,13,turnPage.arrDataCacheSet[i][12]);
		}
	}
	//initContPlanDutyGrid();
}

//���ؼ����ͬ¼�����
function returnparent()
{
	
	var tGrpContNo=document.all("GrpContNo").value;
	var arrResult = easyExecSql("select * from LCGrpCont where Grpcontno= '" + tGrpContNo + "'", 1, 0);                        
            if (arrResult != null) {
            tGrpContNo=arrResult[0][1];
            }
	//if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
	//{
	if(LoadFlag=="16")
	{
		top.close();
}else{
  	location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + tGrpContNo;
  }
	  return;
        //}

}

function QueryDutyClick(){
	if(document.all('RiskCode').value ==""){
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
	initContPlanDutyGrid();
	//��ѯ�������µ����ּ���Ҫ��
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode";
	//document.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
		alert("û�и������µ�������Ϣ��");
		return false;
	}
	//QueryCount = 1;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//tArr = decodeEasyQueryResult(turnPage.strQueryResult);
	//turnPage.arrDataCacheSet = chooseArray(tArr,[0,1,2,3,4,5]);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = ContPlanDutyGrid;

	//����SQL���
	turnPage.strQuerySql = strSQL;

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;

	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//var tArr = new Array();
	//tArr = chooseArray(arrDataSet,[2,3,4,6,8,18,9,10,11]);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	var cDutyCode="";
	var tSql="";
	for(var i=0;i<=ContPlanDutyGrid.mulLineCount-1;i++){
	  cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
	  tSql="select choflag from lmriskduty where riskcode='"+document.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	  var arrResult=easyExecSql(tSql,1,0);
	  //alert("ChoFlag:"+arrResult[0]);
	  if(arrResult[0]=="M"){
	  	 ContPlanDutyGrid.checkBoxSel(i+1);
	  }	
	} 
}

//����Ѷ���
function GrpRiskFeeInfo(){
	parent.fraInterface.window.location = "./RiskFeeInput.jsp?GrpContNo=" + document.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//�ɷѹ�����
function PayRuleInfo(){
	parent.fraInterface.window.location = "./PayRuleInput.jsp?GrpContNo=" + document.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//�����ύ��ɾ����
function DelContClick(){
	document.all('mOperate').value = "DELETE||MAIN";
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
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.submit(); //�ύ
}

function RiskQueryClick(){
	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.MainRiskVersion "
		+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
		+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
		+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
		+ "and d.ContPlanCode = '00' and GrpContNO = '"+document.all('GrpContNo').value+"' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.FactorOrder";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("Ŀǰû���κ������ر���Ϣ��");
		return false;
	}
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//tArr = decodeEasyQueryResult(turnPage.strQueryResult);
	//turnPage.arrDataCacheSet = chooseArray(tArr,[0,1,2,3,4,5]);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = ContPlanGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	QueryCount = 1;
	tSearch = 1;
}