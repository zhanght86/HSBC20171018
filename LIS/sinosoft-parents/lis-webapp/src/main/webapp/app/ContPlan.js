//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var QueryResult="";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere="";
var tSearch = 0;
window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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
		 	
		var sqlid39="ContQuerySQL39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
		mySql39.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
		mySql39.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	    var strSQL=mySql39.getString();	
			
//			var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//			+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if(!turnPage.strQueryResult)
			{
				alert("����������δ���,���ܶ��Ƹ����ֱ��ռƻ�");
				document.all('MainRiskCode').value = "";
				return false;
			}
		else
			{
			divmainriskname.style.display = '';
			divmainrisk.style.display = '';
			document.all('MainRiskCode').value = "";
		  }
	}}
	//�ж��Ƿ�ѡ�����ײ�
	if (cCodeName=="RiskPlan1"){
		var tRiskPlan1 = document.all('RiskPlan1').value;
		if (tRiskPlan1!="0"){
			divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			ContPlanGrid.lock();
		}
		else{
			divriskcodename.style.display = '';
			divriskcode.style.display = '';
			divcontplanname.style.display = 'none';
			divcontplan.style.display = 'none';
			ContPlanGrid.unLock();
		}
	}
	//�������ײ����ݴ������
	if (cCodeName=="RiskPlan"){
		var tRiskPlan = document.all('RiskPlan').value;
		if (tRiskPlan!=null&&tRiskPlan!=""){
			showPlan();
		}
	}
}

function CheckRisk(mIsShow)
{
	
			var sqlid40="ContQuerySQL40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
		mySql40.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
	    var strSQL=mySql40.getString();	
	
   // var strSQL="select SubRiskFlag from lmriskapp where riskcode='"+document.all('RiskCode').value+"'";
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
	    if (mIsShow == "1")
	    {
			
			var sqlid41="ContQuerySQL41";
			var mySql41=new SqlClass();
			mySql41.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
			mySql41.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
			mySql41.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		    var strSQL=mySql41.getString();	
			
//    		var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//    		+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
    		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("����������δ���,���ܶ��Ƹ����ֱ��ռƻ�");
    			document.all('MainRiskCode').value = "";
    			return false;
    		}
	    }
    	divmainriskname.style.display = '';
    	divmainrisk.style.display = '';
	 }
}

//���ݲ�ѯ
function AddContClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("�����뱣�ϼ���");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('RiskCode').value ==""){
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
	if(document.all('MainRiskCode').value ==""){
		alert("������������Ϣ��");
		document.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	var MainRiskCode = document.all('MainRiskCode').value
	var sRiskCode ="";
	var sMainRiskCode="";
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	//����Ҫ���һ��У�飬�����Ƚ��鷳����ʱ������
	for (i=0;i<ContPlanGrid.mulLineCount;i++){
		sRiskCode=ContPlanGrid.getRowColData(i,2);
		sMainRiskCode=ContPlanGrid.getRowColData(i,12);
		//��Ҫ�ǿ��Ǹ��ջ���ڲ�ͬ�������£������Ҫ˫��У��
		if (sRiskCode == document.all('RiskCode').value && sMainRiskCode == MainRiskCode){
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
	//alert(getWhere);

	// ��дSQL���
	var strSQL = "";

	if (QueryCount == 0){
		//��ѯ�������µ����ּ���Ҫ��
		
			var sqlid42="ContQuerySQL42";
			var mySql42=new SqlClass();
			mySql42.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
			mySql42.addSubPara(MainRiskCode);//ָ������Ĳ���
			mySql42.addSubPara(getWhere);//ָ������Ĳ���
			mySql42.addSubPara(GrpContNo);//ָ������Ĳ���
			mySql42.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		    strSQL=mySql42.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//document.all('PlanSql').value = strSQL;
		//alert(strSQL);
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
		
			var sqlid43="ContQuerySQL43";
			var mySql43=new SqlClass();
			mySql43.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
			mySql43.addSubPara(getWhere);//ָ������Ĳ���
			mySql43.addSubPara(GrpContNo);//ָ������Ĳ���
			mySql43.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		    strSQL=mySql43.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//document.all('ContPlanName').value = strSQL;
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

//�����ύ�����棩
function submitForm()
{
	if (!beforeSubmit())
	{
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
	if (document.all('mOperate').value == "INSERT||MAIN")
	{
		if (!confirm('�ƻ� '+document.all('ContPlanCode').value+' �µ�ȫ������Ҫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����'))
		{
			return false;
		}
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
	document.getElementById("fm").submit(); //�ύ
}

//������һ��
function returnparent()
{
	parent.close();
}

//�����ύ��ɾ����
function DelContClick(){
	//�˷����õ���������Ҫ-1����
	var line = ContPlanCodeGrid.getSelNo();
	if (line == 0)
	{
		alert("��ѡ��Ҫɾ���ļƻ���");
		document.all('ContPlanCode').value = "";
		return false;
	}
	else
	{
		document.all('ContPlanCode').value = ContPlanCodeGrid.getRowColData(line-1,1);
	}
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
	document.getElementById("fm").submit(); //�ύ
}

//�����ύ���޸ģ�
function UptContClick(){
	if (tSearch == 0){
		alert("���Ȳ�ѯҪ�޸ĵı��ϼƻ���");
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
	document.getElementById("fm").submit(); //�ύ
}

//����У��
function beforeSubmit(){
	if (document.all('ContPlanCode').value == ""){
		alert("�����뱣�ϼ���");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('ContPlanCode').value == "00")
	{
		alert("���ϼ�����Ϊ00");
		document.all('ContPlanCode').focus();
		return false;
	}
	if (document.all('mOperate').value != "UPDATE||MAIN"){
		if (document.all('MainRiskCode').value == ""){
			alert("���������ձ��룡");
			document.all('MainRiskCode').focus();
			return false;
		}
	}
	if (ContPlanGrid.mulLineCount == 0){
		alert("�����뱣�ռƻ���ϸ��Ϣ");
		return false;
	}
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	var sCalFactor;
	var sRiskCode;
	var sPrem = "";
	var sAmnt = "";
	var sCount = 0;
	//���Ҫ��ֵ��ϢУ��
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,14);
      	sCalFactor = ContPlanGrid.getRowColData(i,5);
      	sRiskCode = ContPlanGrid.getRowColData(i,2);
      	if (sCalFactor != "PayRuleCode" && sCalFactor != "AscriptionRuleCode")
		{
          	//����У��
          	if (sCalMode == "A"){
          	    if (sCalFactor == "Prem")
          	    {
          	        sCount = sCount + 1;
          	        sPrem = sValue;
          	    }
          	    if (sCalFactor == "Amnt")
          	    {
          	        sCount = sCount + 1;
          	        sAmnt = sValue;
          	    }
          	    if (sCount > 1 && (sPrem == "" || sPrem == 0) && (sAmnt == "" || sAmnt == 0))
          	    {
          	        alert("��¼�뱣����߱��ѣ�");
          	        ContPlanGrid.setFocus(i,8);
          	        return false;
          	    }
    			if (sValue!=""){    			    
    				if (!isNumeric(sValue)){
    					alert("��¼�����֣�");
    					ContPlanGrid.setFocus(i,8);
    					return false;
    				}
    			}
    			
    		}
    		//�����㱣��У��
          	if (sCalMode == "P"){
          	    if (sCalFactor == "Prem")
          	    {
        			if (sValue==""){
        				alert("��¼�뱣�ѣ�");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}
        			if(sValue=0){
        				alert("���Ѳ���Ϊ0��");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}
    		    }    		    
    			if (!isNumeric(sValue)){
    				alert("��¼�����֣�");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    			
    		}
    		//�����㱣��У��
          	if (sCalMode == "G"){
          	    if (sCalFactor == "Amnt")
          	    {
          	    	if (sValue==""){
        				alert("��¼�뱣�");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}        			
        			if(sValue==0){
        				alert("�����Ϊ0��");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}
        		}
        		if (!isNumeric(sValue)){
    				alert("��¼�����֣�");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    		}
    		//���������㱣�ѱ���У��
          	if (sCalMode == "O"){
    			if (sValue==""){
    				alert("��¼��Ҫ��ֵ��");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    			if (!isNumeric(sValue)){
    				alert("��¼�����֣�");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    			if(sValue==0){
    				alert("Ҫ��ֵ����Ϊ0��");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    		}
    		//¼�뱣�ѱ���У��
          	if (sCalMode == "I"){
    			if (sValue!=""){
    				if (!isNumeric(sValue)){
    					alert("��¼�����֣�");
    					ContPlanGrid.setFocus(i,8);
    					return false;
    				}
    			}
    		}
    	}
        else{
            if (sValue==""){
				alert("��¼��Ҫ��ֵ��");
				ContPlanGrid.setFocus(i,8);
				return false;
    		}
            if (sCalFactor == "PayRuleCode")
            {
                if (CheckPayRuleCode(sRiskCode,sValue) == false)
                {
                    ContPlanGrid.setFocus(i,8);
                    return false;
                }
            }
            if (sCalFactor == "AscriptionRuleCode")
            {
                if (CheckAscriptionRuleCode(sRiskCode,sValue) == false)
                {
                    ContPlanGrid.setFocus(i,8);
                    return false;
                }
            }
        }
	}
	return true;
}

function initFactoryType(tRiskCode)
{
	// ��дSQL���
	var k=0;
	var strSQL = "";
	
			var sqlid44="ContQuerySQL44";
			var mySql44=new SqlClass();
			mySql44.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
			mySql44.addSubPara(tRiskCode);//ָ������Ĳ���
		    strSQL=mySql44.getString();	
	
//	strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tRiskCode+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
//		   + " and a.FactoryType= b.FactoryType "
//		   + " and (RiskCode = '"+tRiskCode+"' or RiskCode ='000000' )";
    var str  = easyQueryVer3(strSQL, 1, 0, 1);
    return str;
}

/*********************************************************************
 *  Click�¼�������������ռƻ�ҪԼ¼�롱��ťʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function nextstep()
{
	var newWindow = window.open("../app/ContPlanNextInput.jsp?GrpContNo="+document.all('GrpContNo').value+"&LoadFlag="+LoadFlag,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
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
	}
	else{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initContPlanCodeGrid();
	    initContPlanDutyGrid();
	    initContPlanGrid();
	    easyQueryClick();
	    tSearch = 0;
	    QueryCount = 0;
	}
	document.all('mOperate').value = "";
}

function QueryDutyClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("�����뱣�ϼ���");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('RiskCode').value ==""){
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}

	initContPlanDutyGrid();

	//��ѯ�������µ����ּ���Ҫ��
	
			var sqlid45="ContQuerySQL45";
			var mySql45=new SqlClass();
			mySql45.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
			mySql45.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
		    strSQL=mySql45.getString();	
	
//	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
//		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
//		+ "and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode";
		
	//document.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
		alert("û�и������µ�������Ϣ��");
		return false;
	}
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = ContPlanDutyGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	var cDutyCode="";
	var tSql="";
	for(var i=0;i<=ContPlanDutyGrid.mulLineCount-1;i++){
	  cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
//	  tSql="select choflag from lmriskduty where riskcode='"+document.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	  
		var sqlid0="ContPlanSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("app.ContPlanSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
		mySql0.addSubPara(cDutyCode);//ָ������Ĳ���
		tSql=mySql0.getString();	
	  
	  var arrResult=easyExecSql(tSql,1,0);
	  //alert("ChoFlag:"+arrResult[0]);
	  if(arrResult[0]=="M"){
	  	 ContPlanDutyGrid.checkBoxSel(i+1);
	  }
	}
}

function easyQueryClick(){
	initContPlanCodeGrid();

	//��ѯ�������µ����ּ���Ҫ��
	
			var sqlid46="ContQuerySQL46";
			var mySql46=new SqlClass();
			mySql46.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
			mySql46.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
		    strSQL=mySql46.getString();	
	
//	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3,GrpContNo "
//		+ "from LCContPlan "
//		+ "where 1=1 "
//		+ "and GrpContNo = '"+document.all('GrpContNo').value+"' and ContPlanCode <> '00'"
//		+" order by ContPlanCode";
	//document.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//���û����Ҳ���쳣
	if (!turnPage.strQueryResult) {
		//return false;
	}
	else{
		//QueryCount = 1;
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = ContPlanCodeGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	//����ǰ��Ĳ�ѯʲô�������ִ������Ĳ�ѯ
	//��������������󣬵��²�ѯʧ�ܣ������������
	
				var sqlid47="ContQuerySQL47";
			var mySql47=new SqlClass();
			mySql47.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
			mySql47.addSubPara(GrpContNo);//ָ������Ĳ���
		    strSQL=mySql47.getString();	
	
	//strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where GrpContNo = '" +GrpContNo+ "'";
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);

  if(!turnPage.strQueryResult)
    {
    }
  else
  	{
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  document.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
	  document.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
	  document.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
	  document.all('AppntNo').value = turnPage.arrDataCacheSet[0][3];
	  document.all('GrpName').value = turnPage.arrDataCacheSet[0][4];
  }
}

//��ѡ���������¼�
function ShowContPlan(parm1,parm2){
	if(document.all(parm1).all('InpContPlanCodeGridSel').value == '1'){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		var cContPlanCode = document.all(parm1).all('ContPlanCodeGrid1').value;	//�ƻ�����
		var cContPlanName = document.all(parm1).all('ContPlanCodeGrid2').value;	//�ƻ�����
		var cPlanSql = document.all(parm1).all('ContPlanCodeGrid3').value;	//����˵��
		var cPeoples3 = document.all(parm1).all('ContPlanCodeGrid4').value;	//�ɱ�����
		var cGrpContNo = document.all(parm1).all('ContPlanCodeGrid5').value;	//������
		document.all('ContPlanCode').value = cContPlanCode;
		document.all('ContPlanCode').readOnly=true;
		document.all('ContPlanName').value = cContPlanName;
		document.all('PlanSql').value = cPlanSql;
		document.all('Peoples3').value = cPeoples3;
		//alert(cContPlanCode);
		//var cGrpContNo = document.all('GrpContNo').value;

		//��ѯ�������µ����ּ���Ҫ��
		
			var sqlid48="ContQuerySQL48";
			var mySql48=new SqlClass();
			mySql48.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
			mySql48.addSubPara(document.all('ContPlanCode').value);//ָ������Ĳ���
			mySql48.addSubPara(cGrpContNo);//ָ������Ĳ���
		    strSQL=mySql48.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,"
//		    + "a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,"
//		    + "b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//			+ "and ContPlanCode = '"+document.all('ContPlanCode').value+"'"
//			+ "and GrpContNO = '"+cGrpContNo+"' order by a.RiskCode,d.MainRiskCode,a.DutyCode";
		//document.all('PlanSql').value = strSQL;
		var arrResult=easyExecSql(strSQL);
		if(arrResult!=null)
		{
		    document.all('RiskCode').value=arrResult[0][1];
			document.all('RiskCodeName').value=arrResult[0][0];
			document.all('MainRiskCode').value=arrResult[0][11];
		}
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//ԭ���ϲ���ʧ�ܣ��ٺ�
		if (!turnPage.strQueryResult) {
			alert("��ѯʧ�ܣ�");
			return false;
		}
	  
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult,0,1);
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
		CheckRisk("0");
	}
}

//ѡ�����ײ�
function showPlan(){
	var arrResult  = new Array();
	
				var sqlid48="ContQuerySQL48";
			var mySql48=new SqlClass();
			mySql48.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
			mySql48.addSubPara(document.all('RiskPlan').value);//ָ������Ĳ���
		    strSQL=mySql48.getString();	
	
	//strSQL = "select a.ContPlanCode,a.ContPlanName,a.PlanSql,a.Peoples3,b.RiskCode,b.MainRiskCode from LDPlan a,LDPlanRisk b where a.ContPlanCode = b.ContPlanCode and a.ContPlanCode='"+document.all("RiskPlan").value+"'";

	var cGrpContNo = document.all('GrpContNo').value;

	arrResult =  decodeEasyQueryResult(easyQueryVer3(strSQL, 1, 0, 1));
	if(arrResult==null){
		alert("��ѯ�����ײ�����ʧ�ܣ�");
	}else{
		document.all('ContPlanCode').value = arrResult[0][0];
		document.all('ContPlanName').value = arrResult[0][1];
		document.all('PlanSql').value = arrResult[0][2];
		document.all('Peoples3').value = arrResult[0][3];
		document.all('RiskCode').value = arrResult[0][4];
		document.all('MainRiskCode').value = arrResult[0][5];
	}
	//��ѯ�������µ����ּ���Ҫ��
	
			var sqlid49="ContQuerySQL49";
			var mySql49=new SqlClass();
			mySql49.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
			mySql49.addSubPara(document.all('RiskPlan').value);//ָ������Ĳ���
			mySql49.addSubPara(cGrpContNo);//ָ������Ĳ���
		    strSQL=mySql49.getString();	
	
//	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,e.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
//		+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d,LCGrpPol e "
//		+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//		+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//		+ "and ContPlanCode = '"+document.all('RiskPlan').value+"' "
//		+ "and e.GrpContNO = '"+cGrpContNo+"' and e.RiskCode = d.RiskCode  order by a.RiskCode,d.MainRiskCode,a.DutyCode";

	//document.all('PlanSql').value = strSQL;
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//ԭ���ϲ���ʧ�ܣ��ٺ�
	if (!turnPage.strQueryResult) {
		alert("��ѯʧ�ܣ�");
		return false;
	}
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
	QueryCount = 1;
	tSearch = 1;
}

//�ƻ�������޸Ĳ�ѯ״̬����
function ChangePlan(){
	if(document.all('ContPlanCode').value=="00")
	  {
	  	alert("���ϼ�����Ϊ00");
      document.all('ContPlanCode').focus();
	  	return false;
	  }
	  if(ContPlanCodeGrid.getSelNo()-1>=0)
	  {return true;}
	else
		{
	   QueryCount = 0;
     initContPlanDutyGrid();
     initContPlanGrid();
    }
}

function QueryAskPlanClick()
{
    initContPlanCodeGrid();

	//��ѯ�ñ�����ѯ�۱������ռƻ�
	
				var sqlid50="ContQuerySQL50";
			var mySql50=new SqlClass();
			mySql50.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
			mySql50.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
		    strSQL=mySql50.getString();	
	
//	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3,GrpContNo "
//		+ "from LCContPlan "
//		+ "where 1=1 "
//		+ "and GrpContNo ="
//		+ "(select ReportNo from LCGrpCont where GrpContNo='" + document.all('GrpContNo').value
//		+ "') and ContPlanCode <> '00'"
//		+" order by ContPlanCode";
	turnPage.queryModal(strSQL,ContPlanCodeGrid);	
}

function CheckPayRuleCode(tRiskCode,tPayRuleCode)
{
	
			var sqlid51="ContQuerySQL51";
			var mySql51=new SqlClass();
			mySql51.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
			mySql51.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			mySql51.addSubPara(tRiskCode);//ָ������Ĳ���
			mySql51.addSubPara(tPayRuleCode);//ָ������Ĳ���
		    tStrSQL=mySql51.getString();	
	
//    tStrSQL = "select count(1) from lcpayrulefactory where GrpContNo='" + document.all('GrpContNo').value
//            + "' and RiskCode='" + tRiskCode + "' and PayRuleCode='" + tPayRuleCode + "'";
  	var arrResult = easyExecSql(tStrSQL,1,0);
  	if(!arrResult)
  	{
  	  	alert("���������ֵĽɷѹ��򲻴��ڣ����ȶ���ɷѹ���");
  	  	return false;
  	}
  	var tCount=arrResult[0][0];
  	if (tCount == '0')
  	{
  	    alert("���������ֵĽɷѹ��򲻴��ڣ����ȶ���ɷѹ���");
  	  	return false;
  	}
    return true;       
}

function CheckAscriptionRuleCode(tRiskCode,tAscriptionRuleCode)
{
	
				var sqlid52="ContQuerySQL52";
			var mySql52=new SqlClass();
			mySql52.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
			mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id
			mySql52.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			mySql52.addSubPara(tRiskCode);//ָ������Ĳ���
			mySql52.addSubPara(tAscriptionRuleCode);//ָ������Ĳ���
		    tStrSQL=mySql52.getString();	
	
//    tStrSQL = "select count(1) from lcascriptionrulefactory where GrpContNo='" + document.all('GrpContNo').value
//            + "' and RiskCode='" + tRiskCode + "' and AscriptionRuleCode='" + tAscriptionRuleCode + "'";
  	var arrResult = easyExecSql(tStrSQL,1,0);
  	if(!arrResult)
  	{
  	  	alert("���������ֵĹ������򲻴��ڣ����ȶ����������");
  	  	return false;
  	}
  	var tCount=arrResult[0][0];
  	if (tCount == '0')
  	{
  	    alert("���������ֵĹ������򲻴��ڣ����ȶ����������");
  	  	return false;
  	}
    return true;       
}