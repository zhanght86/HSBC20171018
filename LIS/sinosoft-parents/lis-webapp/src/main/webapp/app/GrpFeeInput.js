//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;
var tSearch = 0;
var turnPage = new turnPageClass();
var queryPubAccFlag = false;
window.onfocus=myonfocus;
var mSwitch = parent.VD.gVSwitch;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
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
	if (tSearch == 0){
		alert("���Ȳ�ѯҪ������Ϣ��");
		return false;
	}

	document.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.action = "GrpFeeSave.jsp";
	document.getElementById("fm").submit(); //�ύ
}

function afterCodeSelect( cCodeName, Field ){
	//�ж�˫������ִ�е���ʲô��ѯ
	if (cCodeName=="GrpRisk"){
	    initContPlanGrid();
	    initContPlanDutyGrid();
		var tRiskFlag = document.all('RiskFlag').value;
		//���ڸ����ղ���������¼�������ж���������ΪS��ʱ������
		if (tRiskFlag!="S"){
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
	    else
	    {
			
			var sqlid65="ContQuerySQL65";
		    var mySql65=new SqlClass();
		    mySql65.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql65.setSqlId(sqlid65);//ָ��ʹ�õ�Sql��id
		    mySql65.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
		    mySql65.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var strSQL=mySql65.getString();	
			
//			var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//			+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if(!turnPage.strQueryResult)
			{
				alert("������������Ϣδ¼��,�ʲ��ܽ��и�������Ϣ¼��");
				document.all('MainRiskCode').value = "";
				return false;
			}
		    else
			{
			    divmainriskname.style.display = '';
			    divmainrisk.style.display = '';
			    document.all('MainRiskCode').value = "";
		    }
		}
	}
}
function CheckRisk()
  {
  	
			var sqlid66="ContQuerySQL66";
		    var mySql66=new SqlClass();
		    mySql66.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql66.setSqlId(sqlid66);//ָ��ʹ�õ�Sql��id
		    mySql66.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var strSQL=mySql66.getString();	
	
  	//var strSQL="select subriskflag from lmriskapp where riskcode='"+document.all('RiskCode').value+"'";
  	var arrResult = easyExecSql(strSQL,1,0);
  	if(!arrResult)
  	  {
  	  	return false;
  	  }
  	var tRiskFlag=arrResult[0][0];
  	if (tRiskFlag!="S")
  	{
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
	 else{
	 	
			var sqlid67="ContQuerySQL67";
		    var mySql67=new SqlClass();
		    mySql67.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql67.setSqlId(sqlid67);//ָ��ʹ�õ�Sql��id
		    mySql67.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
		    mySql67.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var strSQL=mySql67.getString();	
		
//			var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//			+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if(!turnPage.strQueryResult)
			{
				alert("������������Ϣδ¼��,�ʲ��ܽ��и�������Ϣ¼��");
				document.all('MainRiskCode').value = "";
				return false;
			}
		else
			{
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

	
	document.all('mOperate').value = "INSERT||MAIN";

	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "GrpFeeSave.jsp";
	document.getElementById("fm").submit(); //�ύ	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function Submit( FlagStr, content ){
	showInfo.close();
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
	//alert(document.all('mOperate').value);
	document.all('mOperate').value = "";
	initForm();
	//alert(document.all('mOperate').value);
	QueryCount = 0;	//���³�ʼ����ѯ����
	tSearch = 0;
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
	var sInsuYearFlag = "";
	var sInsuYear = "";
	var sPremToAmnt = "";
	var sCalRule = "";
	var sFloatRate = "";
	//���Ҫ����������У����Ϣ
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,5);
      	if (sValue == "InsuYearFlag"){
      		sInsuYearFlag = ContPlanGrid.getRowColData(i,8);
      	}
      	if (sValue == "InsuYear"){
      		if (sInsuYearFlag != ""){
      			sInsuYear = ContPlanGrid.getRowColData(i,8);
      			if (!isNumeric(sInsuYear)){
      				alert("�����ڼ�¼������Ӧ����д���֣�");
      				ContPlanGrid.setFocus(i,8);
      				return false;
      			}
      			sInsuYearFlag = "";
      		}
      	}
      	if (sValue == "CalRule"){
      		sCalRule = ContPlanGrid.getRowColData(i,8);      		
      		if (sCalRule != ""){
          		for(var j=0;j<lineCount;j++){
          		    tValue = ContPlanGrid.getRowColData(j,5);
          		    if (tValue == "FloatRate"){          		        		
        	  			tFloatRate = ContPlanGrid.getRowColData(j,8);
        	      		if (sCalRule == "0"||sCalRule == "3"){
        	      			if (tFloatRate != "" && tFloatRate != null && tFloatRate != "null"){
        	      				alert("��ǰ������򣬲�Ӧ��¼�����/�ۿ���Ϣ��");
        	      				ContPlanGrid.setFocus(j + 1,8);
        	      				return false;
        	      			}
        	      		}
        	 			else{
        	      			if (!isNumeric(tFloatRate)){
        	      				alert("��¼�����/�ۿ���Ϣ��");
        	      				ContPlanGrid.setFocus(j + 1,8);
        	      				return false;
        	      			}
        	      			if (parseFloat(tFloatRate)>1 || parseFloat(tFloatRate) <= 0){
        	      				alert("����/�ۿ���Ϣ¼��������¼��0��1�����֣�");
        	      				ContPlanGrid.setFocus(j + 1,8);
        	      				return false;
        	      			}
        	  			}
        	  			sCalRule = "";
        	  			break;
    	  		    }
          	    }
          	}
      	}
	}
	
	
	
	
	
	
	
		
	
	//�����LMDuty���ж����˸����ε����ֵı��������Ҫͨ��������������ֱ��¼�룬��У�鲻��ͬʱ¼������εı��Ѻͱ���
	//��������ContPlanGrid
	//��¼��ǰ��������εı���
	var curDutyCode=ContPlanGrid.getRowColData(0,3);
	
	var curDutyName=ContPlanGrid.getRowColData(0,4);
	
	//��¼��ǰ����������Ƿ�¼���˱���
	var inputedPrem=false;
	//��¼��ǰУ��������Ƿ�¼���˱���
	var inputedAmnt=false;
	//��¼�Ƿ��������ж����˱������
	var canDefinePremAmnt=false;

	
	for(var i=0;i<lineCount;i++){

	//��ѯ��LMDuty��
	
			var sqlid68="ContQuerySQL68";
		    var mySql68=new SqlClass();
		    mySql68.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql68.setSqlId(sqlid68);//ָ��ʹ�õ�Sql��id
		    mySql68.addSubPara(ContPlanGrid.getRowColData(i,3));//ָ������Ĳ���
	        var strQuerySql=mySql68.getString();	
	
	//var strQuerySql=" select calmode from lmduty where dutycode='"+ContPlanGrid.getRowColData(i,3)+"'";
	var resultSet=easyExecSql(strQuerySql,1,0,1);
	
	//�����Ƿ���ͬʱ¼�뱣��ѣ������ͬʱ¼�룬ѭ����ǰֱ����һ������
	if(resultSet=="I"){
	while(ContPlanGrid.getRowColData(++i,3)==curDutyCode){
	}
	//�ϲ�forѭ����Ҳ��i��1�������˳���ȥ1ʹ��ÿ�ж���������
	i--;
	}
	
	//�����Ƿ�¼���˱������
	if(trim(ContPlanGrid.getRowColData(i,5))=="Prem"||trim(ContPlanGrid.getRowColData(i,6))=="����"){
		canDefinePremAmnt=true;
		if(ContPlanGrid.getRowColData(i,8)){
		inputedPrem=true;
	}
 }
	else if(trim(ContPlanGrid.getRowColData(i,5))=="Amnt"||trim(ContPlanGrid.getRowColData(i,6))=="����"){
		canDefinePremAmnt=true;
		if(ContPlanGrid.getRowColData(i,8)){
		inputedAmnt=true;
	}
 }
	
	if((i<lineCount-1&&(ContPlanGrid.getRowColData(i,3)!=ContPlanGrid.getRowColData(i+1,3)))||(i==lineCount-1)){
	//ִ��У��
	if(canDefinePremAmnt&&((inputedPrem&&inputedAmnt)||(!inputedPrem&&!inputedAmnt))){
	alert(curDutyName+"�������еı��ѱ������¼����ֻ��¼��һ�");
	return false;
	}
	inputedAmnt=false;
	inputedPrem=false;
	canDefinePremAmnt=false;
	curDutyCode=ContPlanGrid.getRowColData(i,3);
	}
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
	if(document.all('MainRiskCode').value =="")
	{
		alert("������������Ϣ��");
		document.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	var GrpContNo = document.all('GrpContNo').value;
	//���¼�����������Ϣ������ʾ�����ٴ�¼��
	for (i=0;i<ContPlanGrid.mulLineCount;i++)
	{
		sValue=ContPlanGrid.getRowColData(i,2)
		if (sValue == document.all('RiskCode').value)
		{
			alert("����ӹ������ֱ��ռƻ�Ҫ�أ�");
			return false;
		}
	}

	var getWhere = "(";
	for (i=0;i<lineCount;i++)
	{
		if (ContPlanDutyGrid.getChkNo(i))
		{
			//alert(ContPlanDutyGrid.getRowColData(i,1));
			getWhere = getWhere + "'"+ContPlanDutyGrid.getRowColData(i,1)+"',"
		}
	}
	if (getWhere == "(")
	{
		alert("��ѡ��������Ϣ");
		return false;
	}
	getWhere = getWhere.substring(0,getWhere.length-1) + ")"
	var MainRiskCode = document.all('MainRiskCode').value;

	var strSQL = "";
	
				var sqlid69="ContQuerySQL69";
		    var mySql69=new SqlClass();
		    mySql69.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql69.setSqlId(sqlid69);//ָ��ʹ�õ�Sql��id
		    mySql69.addSubPara(GrpContNo);//ָ������Ĳ���
		    mySql69.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        strSQL=mySql69.getString();	
	
//	strSQL = "select payintv from lcgrppol where grpcontno='" + GrpContNo 
//	       + "' and riskcode='" + fm.RiskCode.value + "'";
	var tPayIntv = easyExecSql(strSQL);
	if (tPayIntv == null || tPayIntv.length == 0)
	{
	    alert("�������ֲ�ѯʧ�ܣ�");
	    return false;
	}
	
	strSQL = "";
	if (QueryCount == 0)
	{	// ��дSQL���
	
			var sqlid70="ContQuerySQL70";
		    var mySql70=new SqlClass();
		    mySql70.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql70.setSqlId(sqlid70);//ָ��ʹ�õ�Sql��id
		    mySql70.addSubPara(MainRiskCode);//ָ������Ĳ���
		    mySql70.addSubPara(getWhere);//ָ������Ĳ���
		    mySql70.addSubPara(GrpContNo);//ָ������Ĳ���
		    mySql70.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        strSQL=mySql70.getString();	
	
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,"
//		    + "case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
		turnPage.queryModal(strSQL,ContPlanGrid);
		//�ж��Ƿ��ѯ�ɹ�
		if (ContPlanGrid.mulLineCount <= 0) 
		{
			alert("û�и�����������Ҫ����Ϣ��");
			return false;
		}
	}
	else
	{
		
		    var sqlid71="ContQuerySQL71";
		    var mySql71=new SqlClass();
		    mySql71.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql71.setSqlId(sqlid71);//ָ��ʹ�õ�Sql��id
		    mySql71.addSubPara(getWhere);//ָ������Ĳ���
		    mySql71.addSubPara(GrpContNo);//ָ������Ĳ���
		    mySql71.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        strSQL=mySql71.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
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
	
	
	//����Ǹ�������ȡ���ձ����ڼ�Ҫ��ֵ��������������Ϣû��¼�룬ȡ���ݿ���Ĭ��ֵ	
	var tInsuYear = "";
	
    var tInsuYearFlag = "";

	if (document.all('RiskFlag').value == "S"){
	
			 var sqlid72="ContQuerySQL72";
		    var mySql72=new SqlClass();
		    mySql72.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql72.setSqlId(sqlid72);//ָ��ʹ�õ�Sql��id
		    mySql72.addSubPara(GrpContNo);//ָ������Ĳ���
		    mySql72.addSubPara(fm.MainRiskCode.value);//ָ������Ĳ���
	         var tStrSql=mySql72.getString();	
	
//        var tStrSql = "select calfactorvalue from lccontplandutyparam where grpcontno='"
//	
//         + GrpContNo + "' and riskcode='" 
//	
//         + document.all('MainRiskCode').value + "' and calfactor='InsuYear'";
	
        tResult = easyExecSql(tStrSql, 1, 0);
	
        if (tResult != null && tResult.length > 0){
	
            tInsuYear = tResult[0][0];
	
        }
		
        	var sqlid73="ContQuerySQL73";
		    var mySql73=new SqlClass();
		    mySql73.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql73.setSqlId(sqlid73);//ָ��ʹ�õ�Sql��id
		    mySql73.addSubPara(GrpContNo);//ָ������Ĳ���
		    mySql73.addSubPara(fm.MainRiskCode.value);//ָ������Ĳ���
	         var tStrSql=mySql73.getString();	
	
//        var tStrSql = "select calfactorvalue from lccontplandutyparam where grpcontno='"
//	
//         + GrpContNo + "' and riskcode='" 
//	
//         + document.all('MainRiskCode').value + "' and calfactor='InsuYearFlag'";
	
        tResult = easyExecSql(tStrSql, 1, 0);
	
        if (tResult != null && tResult.length > 0){
	
            tInsuYearFlag = tResult[0][0];
	
        }
	
    }
	
	//ѭ������Ҫ����Ա����ڼ䡢�ɷѷ�ʽ��ֵ
	lineCount = ContPlanGrid.mulLineCount;
    
	var sValue;
   
	for(var i=0;i<lineCount;i++){
    
      	sValue = ContPlanGrid.getRowColData(i,5);
  
      	if (tInsuYear != null && tInsuYear != "" && tInsuYear != "null")
      	{
          	if (sValue == "InsuYear"){
    
          		ContPlanGrid.setRowColData(i,8,tInsuYear);
    
          	}

        }  
        if (tInsuYearFlag != null && tInsuYearFlag != "" && tInsuYearFlag != "null")
      	{  
          	if (sValue == "InsuYearFlag"){
    
          		ContPlanGrid.setRowColData(i,8,tInsuYearFlag);
    
          	}
 
        }
      	
      	if (sValue == "PayIntv"){
 
      		ContPlanGrid.setRowColData(i,8,tPayIntv[0][0]);
   
     	}
     
    }
	
	//initContPlanDutyGrid();
	//initContPlanDutyGrid();
}

//���ؼ����ͬ¼�����
function returnparent()
{
	
	var tGrpContNo=document.all("GrpContNo").value;
//	var arrResult = easyExecSql("select * from LCGrpCont where Grpcontno= '" + tGrpContNo + "'", 1, 0);                        
//    if (arrResult != null) {
//        tGrpContNo=arrResult[0][1];
//    }
    //alert("A" + LoadFlag + "A");
    if (LoadFlag=="c")
    {
    	parent.close();
    }
    else
  	{
    	//if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    	//{
  	    location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + tGrpContNo+"&scantype="+scantype;
	    return;
        //}
    }

}

function QueryDutyClick(){
	if(document.all('RiskCode').value ==""){
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
	initContPlanDutyGrid();
	//��ѯ�������µ����ּ���Ҫ��
	
	        var sqlid74="ContQuerySQL74";
		    var mySql74=new SqlClass();
		    mySql74.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql74.setSqlId(sqlid74);//ָ��ʹ�õ�Sql��id
		    mySql74.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	       strSQL=mySql74.getString();	
	
//	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
//		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
//		+ "and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode";
	//document.all('ContPlanName').value = strSQL;
	turnPage.queryModal(strSQL,ContPlanDutyGrid);
	if (ContPlanDutyGrid.mulLineCount <= 0) 
	{
		alert("û�и������µ�������Ϣ��");
		return false;
	}

	var cDutyCode = "";
	var tSql = "";
	for(var i = 0;i <= ContPlanDutyGrid.mulLineCount-1; i++)
	{
	    cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
		
			var sqlid75="ContQuerySQL75";
		    var mySql75=new SqlClass();
		    mySql75.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql75.setSqlId(sqlid75);//ָ��ʹ�õ�Sql��id
		    mySql75.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		    mySql75.addSubPara(cDutyCode);//ָ������Ĳ���
	        tSql=mySql75.getString();	
		
	   // tSql="select choflag from lmriskduty where riskcode='"+document.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	    var arrResult=easyExecSql(tSql,1,0);
	    //alert("ChoFlag:"+arrResult[0]);
	    if(arrResult[0]=="M")
	    {
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
    if (confirm("ȷ��Ҫɾ����������Ϣ��") == true)
    {
    	document.all('mOperate').value = "DELETE||MAIN";
    	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    	QueryCount = 0;	//���³�ʼ����ѯ����
    	fm.action = "GrpFeeSave.jsp";
    	document.getElementById("fm").submit(); //�ύ
    }
}

function RiskQueryClick(){
    if(document.all('RiskCode').value =="" || document.all('RiskCode').value=="null"){
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
	var resultFlag = 0;
	
			var sqlid76="ContQuerySQL76";
		    var mySql76=new SqlClass();
		    mySql76.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql76.setSqlId(sqlid76);//ָ��ʹ�õ�Sql��id
		    mySql76.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		    mySql76.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        strSQL=mySql76.getString();	
	
//	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.MainRiskVersion "
//		+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
//		+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//		+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//		+ "and d.ContPlanCode = '00' and GrpContNO = '"+document.all('GrpContNo').value + "' "
//		+ "and d.RiskCode='" + document.all('RiskCode').value
//		+ "' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.FactorOrder";
	turnPage.pageLineNum = 20;
	turnPage.queryModal(strSQL,ContPlanGrid);
	//�ж��Ƿ��ѯ�ɹ�
	if (!ContPlanGrid.mullLineCount <=0) {
		//resultFlag = 1;
		alert("Ŀǰû���κ������ر���Ϣ��");
		return false;
	}
	QueryCount = 1;
	tSearch = 1;
	//divPubAccGrid.style.display="none";
}


function afterSubmit(FlagStr,content){
	showInfo.close();
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
	    RiskQueryClick();
	}
	else{
		content = "��������Լ�ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    //RiskQueryClick();
	    if (document.all('mOperate').value == "DELETE||MAIN")
	    {
	       initContPlanGrid(); 
	    }        
	}	
	//initForm();
	if (fm.mOperate.value == "DELETE||PUBACC")
	{
	    divPubAccGrid.style.display="none";
	}
	document.all('mOperate').value = "";	
	divPubAmntGrid.style.display="none";
	//initContPlanGrid();
}

//-----------------------------------------Beg
//---------�ʻ�����-----------
//---------Add By��ChenRong
//---------Date:2006.5.17 11:45

//��ʼ�������ʻ���Ϣ
function ShowPubAcc()
{
	if(document.all('RiskCode').value =="")
	{
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
	showDivPubAccGrid(document.all('RiskCode').value);
}

function showDivPubAccGrid(tRiskCode)
{
    PubAccGrid.clearData();
    //�Ȳ�ѯ�ѱ���Ĺ����ʻ���Ϣ�����������ʾ ��ѯ�����ʻ�
	
				var sqlid77="ContQuerySQL77";
		    var mySql77=new SqlClass();
		    mySql77.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql77.setSqlId(sqlid77);//ָ��ʹ�õ�Sql��id
		    mySql77.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		    mySql77.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	         var tSql =mySql77.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,p.Prem,"
//             + "s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo,i.Name,"
//             + "p.amnt from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//             + "where c.GrpContNo = '"+document.all('GrpContNo').value
//             + "'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode "
//             + "and lmduty.dutycode=d.dutycode and c.contno=d.contno and i.contno=d.contno "
//             + "and p.riskcode= '"+document.all('RiskCode').value
//             + "'  and d.dutycode=LMRiskDuty.dutycode  and LMRiskDuty.specflag='Y'";
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    
    //alert(turnPage.strQueryResult);
	//�ж��Ƿ��ѯ�ɹ�
    if(turnPage.strQueryResult)
    {
    	alert("���Ѿ�������������µĹ����ʻ���Ϣ����ѡ���ѯ�����ʻ�����!");
    	return false;
    }
	
			var sqlid78="ContQuerySQL78";
		    var mySql78=new SqlClass();
		    mySql78.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql78.setSqlId(sqlid78);//ָ��ʹ�õ�Sql��id
		    mySql78.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	         var strSql =mySql78.getString();	
	
//    var strSql = "select distinct LMrisk.RiskName,b.DutyName,'',0,"
//        + "LMrisk.RiskCode,a.DutyCode "
//		+ "from LMrisk,LMRiskDuty a, LMDuty b "
//		+ "where a.DutyCode = b.DutyCode and a.specflag='Y'"//���жϹ����ʻ�������
//		+ "and LMrisk.RiskCode = '"+document.all('RiskCode').value
//		+"' and a.RiskCode = LMrisk.RiskCode order by a.DutyCode";

    turnPage.queryModal(strSql, PubAccGrid);
    if (PubAccGrid.mulLineCount <= 0) 
    {
    	alert("������û����Ҫ����Ĺ����ʻ���Ϣ��");
    	return false;
    }
    for(var i = 0; i < PubAccGrid.mulLineCount; i++)  
    {
        PubAccGrid.setRowColData(i,3,"�����ʻ�");    //�����ʻ�����
        PubAccGrid.setRowColData(i,7,"Y");
    }	
    divPubAccGrid.style.display="";
}

//��ѯ�����ʻ���Ϣ
function QureyPubAcc()
{
	PubAccQueryClick();
}

function PubAccQueryClick()
{
    if(document.all('RiskCode').value =="")
	{
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
    initPubAccGrid();
    //PubAccGrid.delBlankLine();
    var tResultFlag = 0;
	
				var sqlid79="ContQuerySQL79";
		    var mySql79=new SqlClass();
		    mySql79.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql79.setSqlId(sqlid79);//ָ��ʹ�õ�Sql��id
		    mySql79.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		    mySql79.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var tSql =mySql79.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,i.Name,p.Prem,"
//               + "s.riskcode,d.dutycode,p.AutoPubAccFlag,p.InsuredNo,"
//               + "p.ContNo,p.GrpContNo,"
//               + "'',p.polno from lcinsured i,lccont c,lcpol p,"
//               + "lmrisk s,lcduty d,lmduty,LMRiskDuty "
//               + "where c.GrpContNo = '"+document.all('GrpContNo').value
//               + "'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode"
//               + " and lmduty.dutycode=d.dutycode and c.contno=d.contno"
//               + " and i.contno=d.contno "
//               + "and p.riskcode= '"+document.all('RiskCode').value
//               + "' and d.dutycode=LMRiskDuty.dutycode and LMRiskDuty.specflag='Y'";
    turnPage.pageLineNum = 10;
    turnPage.queryModal(tSql,PubAccGrid);
    
	//�ж��Ƿ��ѯ�ɹ�
	if(PubAccGrid.mulLineCount <= 0)
	{
		alert("û����Ӧ�Ĺ����ʻ���Ϣ!");
		return false;
	}
	queryPubAccFlag = true;
	
	divPubAccGrid.style.display="";   
    
    if(this.LoadFlag=="4"||this.LoadFlag=="16")
    {
        PubAccSave.style.display="none";
    }
    else
    {
        PubAccSave.style.display=""; 
    }
    
}

//�����ʻ����
function CompPubAcc()
{
	//���У����Ϣ
    if(queryPubAccFlag == false)
    {
    	alert("���Ȳ�ѯ�����ʻ���");
    }

	var tResultFlag = 0;
	
			var sqlid80="ContQuerySQL80";
		    var mySql80=new SqlClass();
		    mySql80.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql80.setSqlId(sqlid80);//ָ��ʹ�õ�Sql��id
		    mySql80.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		    mySql80.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var tSql =mySql80.getString();	
	
//        var tSql = "select s.riskname,lmduty.dutyname,p.Prem,'0',s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo,i.Name,p.amnt,p.polno from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//                 + "where c.GrpContNo = '"+document.all('GrpContNo').value+"'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode and lmduty.dutycode=d.dutycode and c.contno=d.contno and i.contno=d.contno "
//                 + "and p.riskcode= '"+document.all('RiskCode').value+"' and d.dutycode=LMRiskDuty.dutycode and LMRiskDuty.specflag='Y'";
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    //alert(turnPage.strQueryResult);
    if(!turnPage.strQueryResult)
    {
    	alert("û�й����ʻ���Ϣ�����ȶ��幫���ʻ���");
    	return false;
    }
	if (!PubAccGrid.getSelNo())
	{
		alert("��ѡ�����ֹ����ʻ���");
	        return false;
	}
	else
	{
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
        fm.action="CompPubAccSave.jsp";
        document.getElementById("fm").submit(); //�ύ
	}
}

//�ύ�����湫���ʻ���ť��Ӧ����
function SavePubAcc()
{    
	if (!beforeSubmit2()){
		return false;
	}
	//alert(riskcode);
	var tRow = PubAccGrid.getSelNo();
	//�Ȳ�ѯ�ѱ���Ĺ����ʻ���Ϣ�����������ʾ��ѯ�����ʻ�
	
			var sqlid81="ContQuerySQL81";
		    var mySql81=new SqlClass();
		    mySql81.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql81.setSqlId(sqlid81);//ָ��ʹ�õ�Sql��id
		    mySql81.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,5));//ָ������Ĳ���
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,8));//ָ������Ĳ���
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,9));//ָ������Ĳ���
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,12));//ָ������Ĳ���
	        var tSql =mySql81.getString();	
	
//    var tSql = "select p.Prem,"
//             + "p.InsuredNo,p.ContNo,p.GrpContNo,"
//             + "p.amnt from lcpol p,LMRiskDuty "
//             + "where p.GrpContNo = '"+document.all('GrpContNo').value
//             + "' and poltypeflag='2' "
//             + "and LMRiskDuty.riskcode=p.riskcode and LMRiskDuty.specflag='Y'"
//             + "and p.riskcode= '"+PubAccGrid.getRowColData(tRow-1,5)
//             + "' and p.InsuredNo='" + PubAccGrid.getRowColData(tRow-1,8)
//             + "' and p.contno='" + PubAccGrid.getRowColData(tRow-1,9)
//             + "' and p.polno='" +  PubAccGrid.getRowColData(tRow-1,12) + "'";
    turnPage.pageLineNum = 10;
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    //alert(turnPage.strQueryResult);
	//�ж��Ƿ��ѯ�ɹ�
    if(turnPage.strQueryResult)
    {
    	alert("���Ѿ�������������µĹ����ʻ���Ϣ����ѡ���޸Ļ�ɾ������!");
    	return false;
    }
    
	if (!confirm('�����ʻ���ȫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����'))
	{
		return;
	}
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.mOperate.value="INSERT||PUBACC";   
    fm.action="PubAccSave.jsp"; 
	document.getElementById("fm").submit(); //�ύ
}

//�ύ�����湫���ʻ���ť��Ӧ����
function UpdatePubAcc()
{    
	if (!beforeSubmit2()){
		return false;
	}
    
	if (!confirm('�����ʻ���ȫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����'))
	{
		return;
	}	    
    fm.action="PubAccSave.jsp";
    //alert(fm.AppntNo.value);   
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.mOperate.value="UPDATE||PUBACC";
    fm.action="PubAccSave.jsp";        
	document.getElementById("fm").submit(); //�ύ
}


function beforeSubmit2()
{
	//���У����Ϣ
	PubAccGrid.delBlankLine("PubAccGrid");
	var sValue;
	var lineCount = PubAccGrid.mulLineCount;
	var riskcode = document.all('RiskCode').value;
	//alert(riskcode);
	var tRow = PubAccGrid.getSelNo();

	//alert(PubAccGrid.getSelNo(i));
	if( tRow == 0 || tRow == null )
	{
		alert("��ѡ�����ֹ����ʻ���");
        return false;
	}
	
	sValue = PubAccGrid.getRowColData(tRow-1,4);
    if (!isNumeric(sValue))
    {
        alert("�ɷѽ��¼������Ӧ����д���֣�");
        PubAccGrid.setFocus(tRow-1,4);
        return false;
    }
    sValue = PubAccGrid.getRowColData(tRow-1,7);
    if(riskcode=="221704")
    {
    	if (sValue!="Y"&&sValue!="N")
        {
           	alert("��ѡ���Զ�Ӧ�ù����ʻ���ǣ�");
            PubAccGrid.setFocus(tRow-1,7);
            return false;
        }
    }
    return true;
	
}

//ɾ����ѡ�����ʻ�
function DelPubAcc()
{
	if(!confirm("ȷ��ɾ����"))
	{
	    return ;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.mOperate.value = "DELETE||PUBACC";
	fm.action="PubAccDelSave.jsp";
	document.getElementById("fm").submit();
}

//��ʼ�����������б�
function ShowPubAmnt()
{
	if(document.all('RiskCode').value =="")
	{
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
	showDivPubAmntGrid(document.all('RiskCode').value);
}

function showDivPubAmntGrid(tRiskCode)
{
    initPubAmntGrid();
    PubAmntGrid.clearData();
    //�Ȳ�ѯ�ѱ���Ĺ���������Ϣ�����������ʾ ��ѯ��������
	
				var sqlid82="ContQuerySQL82";
		    var mySql82=new SqlClass();
		    mySql82.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql82.setSqlId(sqlid82);//ָ��ʹ�õ�Sql��id
		    mySql82.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		    mySql82.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var tSql =mySql82.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,p.Prem,s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo,i.Name,p.amnt from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//             + "where c.GrpContNo = '"+document.all('GrpContNo').value+"'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode and lmduty.dutycode=d.dutycode and c.contno=d.contno "
//             + "and i.contno=d.contno and p.riskcode= '" +document.all('RiskCode').value+ "' and LMRiskDuty.specflag='A' and LMRiskDuty.dutycode=d.dutycode";
    turnPage.pageLineNum = 10;
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
    if(turnPage.strQueryResult)
    {
    	alert("���Ѿ�������������µĹ���������Ϣ����ѡ�� ��ѯ�������� ����!");
    	return false;
    }
	
			var sqlid83="ContQuerySQL83";
		    var mySql83=new SqlClass();
		    mySql83.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql83.setSqlId(sqlid83);//ָ��ʹ�õ�Sql��id
		    mySql83.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var tSql =mySql83.getString();	
	
//    var strSql = "select distinct LMrisk.RiskName,b.DutyName,b.DutyName,'0','0',LMrisk.RiskCode,a.DutyCode,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName"
//		+ "from LMrisk,LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode and a.specflag='A'"//���жϹ������������
//		+ "and LMrisk.RiskCode = '"+document.all('RiskCode').value
//		+ "' and a.RiskCode = LMrisk.RiskCode order by a.DutyCode";

    turnPage.pageLineNum = 10;
    turnPage.queryModal(strSql,PubAmntGrid);
    
    if (PubAmntGrid.mulLineCount <= 0) 
    {
    	alert("������û����Ҫ����Ĺ���������Ϣ��");
    	return false;
    }
    divPubAmntGrid.style.display="";
}

//��ѯ�Ѷ���Ĺ�������
function PubAmntQueryClick()
{
	if(document.all('RiskCode').value =="")
	{
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
    initPubAmntGrid();
    PubAmntGrid.delBlankLine();
    var tResultFlag = 0;
	
			var sqlid84="ContQuerySQL84";
		    var mySql84=new SqlClass();
		    mySql84.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql84.setSqlId(sqlid84);//ָ��ʹ�õ�Sql��id
		    mySql84.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	        var tSql =mySql84.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,i.Name,p.Amnt,p.Prem,s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//               + "where c.GrpContNo = '"+document.all('GrpContNo').value+"'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode and lmduty.dutycode=d.dutycode and c.contno=d.contno "
//               + "and i.contno=d.contno and p.riskcode= '"+document.all('RiskCode').value+"' and LMRiskDuty.specflag='A' and d.dutycode=LMRiskDuty.dutycode";
    turnPage.pageLineNum = 10;
    turnPage.queryModal(tSql,PubAmntGrid);

	//�ж��Ƿ��ѯ�ɹ�
	if(PubAmntGrid.mulLineCount <= 0)
	{
		alert("û����Ӧ�Ĺ���������Ϣ!");
		return false;
	}
    divPubAmntGrid.style.display="";
    if(this.LoadFlag=="4"||this.LoadFlag=="16")
    {
        PubAmntSave.style.display="none";
    }
    else
    {
        PubAmntSave.style.display="";
    }
    
}

//�ύ�����湫���ʻ���ť��Ӧ����
function SavePubAmnt(){
	if (!beforeSubmit3()){
		return false;
	}
	if (!confirm('���������ȫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����'))
	{
		return;
	}

	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="PubAmntSave.jsp";
        
	document.getElementById("fm").submit(); //�ύ
	//PubAccQueryClick();
}
function beforeSubmit3()
{
	//���У����Ϣ
	var lineCount = 0;
	var sValue;
	PubAmntGrid.delBlankLine("PubAmntGrid");
	lineCount = PubAmntGrid.mulLineCount;
	var tRow = PubAmntGrid.getSelNo();
	//alert(PubAmntGrid.getSelNo(i));
	if (tRow == 0 || tRow == null)
	{
		alert("��ѡ�����ֹ������");
        return false;
	}
	
	sValue = PubAmntGrid.getRowColData(tRow - 1,4);
	//alert(sValue);
    if (sValue!=""&&!isNumeric(sValue))
    {
        alert("����¼������Ӧ����д���֣�");
        PubAmntGrid.setFocus(tRow - 1,4);
        return false;
    }
	sValue = PubAmntGrid.getRowColData(tRow - 1,5);
	if (sValue!=""&&!isNumeric(sValue))
    {
        alert("����¼������Ӧ����д���֣�");
        PubAmntGrid.setFocus(tRow - 1,5);
        return false;
	}

    return true;
}

//ɾ����ѡ��������
function DelPubAmnt()
{
	var lineCount = 0;
	PubAmntGrid.delBlankLine("PubAmntGrid");
	lineCount = PubAmntGrid.mulLineCount;
	var tRow = PubAmntGrid.getSelNo();
			//alert(PubAmntGrid.getSelNo(i));
	if (tRow == 0 || tRow == null)
	{
		alert("��ѡ��Ҫɾ�������ֹ������");
                return false;
	}

	if(!confirm("ȷ��ɾ����"))
	{
	    return ;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action="PubAmntDelSave.jsp";
	document.getElementById("fm").submit();
}

function feeRecord()
{
    showInfo = window.open("./LMChargeRatemain.jsp?Interface=LMChargeRateInput.jsp?GrpContNo="+fm.GrpContNo.value+"&RiskCode="+fm.RiskCode.value,"",'top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
}

function AscriptionRuleInfo()
{
	parent.fraInterface.window.location = "./AscriptionRuleInput.jsp?GrpContNo=" + document.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//�˻�������
function GrpAccTrigger()
{
	parent.fraInterface.window.location= "./LCGrpAccTriggerInput.jsp?GrpContNo=" + document.all('GrpContNo').value + "&LoadFlag=" + LoadFlag;
}

function afterAccSubmit( FlagStr, content ){
	showInfo.close();
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
	//alert(document.all('mOperate').value);	
	//initForm();
	document.all('mOperate').value = "";
	fm.action = "";
	PubAccQueryClick();
} 

function afterAmntSubmit( FlagStr, content ){
	showInfo.close();
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
	//alert(document.all('mOperate').value);
	initForm();
	document.all('mOperate').value = "";
	PubAmntQueryClick();
}
	

function afterCompAcc( FlagStr, content, SumAccBala){
	showInfo.close();
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
	for(var i=0;i<PubAccGrid.mulLineCount;i++)
    {
    	//alert(PubAccGrid.getSelNo(i));
    	if (PubAccGrid.getSelNo(i))
    	{
            PubAccGrid.setRowColData(PubAccGrid.getSelNo(i)-1,11,SumAccBala);//�ʻ����
        }
    }
}

function grpRiskPlanInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("���뱣���ͬ��Ϣ���ܽ��С����ռƻ��ƶ�����");  
	    return false;  
	}
	var newWindow = window.open("../app/ContPlan.jsp?GrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag,"",sFeatures);
}

function grpInsuList()
{  
	
//alert(prtNo);
//var prtNo ="<%=request.getParameter("prtNo")%>";
//var polNo ="<%=request.getParameter("polNo")%>";
//var scantype ="<%=request.getParameter("scantype")%>";
//var MissionID ="<%=request.getParameter("MissionID")%>";
//var ManageCom ="<%=request.getParameter("ManageCom")%>";
//var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
//var ActivityID = "<%=request.getParameter("ActivityID")%>";
//var NoType = "<%=request.getParameter("NoType")%>";
//var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
	var tSQL="select * from lccontplanrisk where proposalgrpcontno='"+prtNo+"'";
	var arrResult = easyExecSql(tSQL);
	//alert(tSQL);
	if(arrResult==null)
	{
	alert("����¼��������Ϣ����ܽ���(�������嵥����Ϣ����!");
	return false;
	}
	
			var sqlid85="ContQuerySQL85";
		    var mySql85=new SqlClass();
		    mySql85.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		    mySql85.setSqlId(sqlid85);//ָ��ʹ�õ�Sql��id
		    mySql85.addSubPara(prtNo);//ָ������Ĳ���
	        var tSQL1 =mySql85.getString();	
	
	//var tSQL1="select * from LCGrpAppnt where prtno='"+prtNo+"'";
	var arrResult1 = easyExecSql(tSQL1);
	if(arrResult1==null)
	{
		alert("��ͬ��Ϣ��δ���棬��������루�������嵥����Ϣ����!");
		return false;
		}
	try { mSwitch.addVar('ProposalGrpContNo','',fm.ProposalGrpContNo.value); } catch(ex) { };
	try { mSwitch.addVar('ManageCom','',fm.ManageCom.value); } catch(ex) { };
	try { mSwitch.addVar('GrpContNo','',fm.GrpContNo.value); } catch(ex) { };
  //delGrpVar();
  //addGrpVar();
  parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;    
    
}
//-----------------------------------------End