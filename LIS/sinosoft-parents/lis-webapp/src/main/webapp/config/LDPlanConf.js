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

function afterCodeSelect( cCodeName, Field ){
	//�ж�˫������ִ�е���ʲô��ѯ
	if (cCodeName=="RiskCode1"){
		var tRiskFlag = document.all('RiskFlag').value;
		//���ڸ����ղ���������¼�������ж���������ΪS��ʱ������
		if (tRiskFlag!="S"){
			//divmainriskname.style.display = '';
			//divmainrisk.style.display = '';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
		else{
			//divmainriskname.style.display = '';
			//divmainrisk.style.display = '';
			document.all('MainRiskCode').value = "";
		}
	}
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else {
		parent.fraMain.rows = "0,0,0,82,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}

//���ݲ�ѯ
function AddContClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("�������ײʹ��룡");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('RiskCode').value ==""){
		alert("��ѡ�����֣�");
		document.all('RiskCode').focus();
		return false;
	}
  else{
//  	var risksql="select SubRiskFlag from lmriskapp where riskcode='"+document.all('RiskCode').value+"'"
  	
	var sqlid0="LDPlanConfSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
	var risksql=mySql0.getString();

	  var arrRiskFlag = easyExecSql(risksql);
	  document.all('RiskFlag').value=arrRiskFlag[0][0];
	  if(document.all('RiskFlag').value=="M")
	  {
	  	document.all('MainRiskCode').value=document.all('RiskCode').value;
	  }
  }
	if(document.all('MainRiskCode').value ==""){
		alert("������������Ϣ��");
		//document.all('MainRiskCode').focus();
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
			alert("����ӹ������ֱ����ײ�Ҫ�أ�");
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
	//alert(MainRiskCode);
	// ��дSQL���
	var strSQL = "";
	
	if (QueryCount == 0){
		//��ѯ�������µ����ּ���Ҫ��
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,'','"+MainRiskCode+"',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ "and  a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		
		var sqlid1="LDPlanConfSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(MainRiskCode);//ָ������Ĳ���
		mySql1.addSubPara(getWhere);//ָ������Ĳ���
		mySql1.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
		strSQL=mySql1.getString();
		
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
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,'','',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode  "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ " and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		
		var sqlid2="LDPlanConfSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(getWhere);//ָ������Ĳ���
		mySql2.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
		strSQL=mySql2.getString();
		
		//document.all('ContPlanName').value = strSQL;
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//alert(strSQL);
		//alert(turnPage.strQueryResult);

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
function submitForm(){
	if (!beforeSubmit()){
		return false;
	}
	if(checkexists()==false) return false;
	if(checkCalFactor()==false) return false;
	document.all('mOperate').value = "INSERT||MAIN";
	if (document.all('mOperate').value == "INSERT||MAIN"){
		if (!confirm('�ײ� '+document.all('ContPlanCode').value+' �µ�ȫ������Ҫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����')){
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
function returnparent(){
	parent.close();
}

function checkexists(){
   var tContPlanCode = fm.ContPlanCode.value;
//   var tSql ="select count(1) from ldplan where ContPlanCode='"+tContPlanCode+"'";
   
	var sqlid3="LDPlanConfSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContPlanCodes);//ָ������Ĳ���
	var tSql=mySql3.getString();
   
   var tCount = easyExecSql(tSql);
   if(tCount>0){
       alert("����Ϊ["+tContPlanCode+"]���ײ��Ѿ�����,��鿴���Ѵ��ڵı����ײ͡���");
       return false;
   }
   return true;
}

//�����ύ��ɾ����
function DelContClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("��ѡ��Ҫɾ���ı����ײͣ�");
		document.all('ContPlanCode').focus();
		return false;
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
		alert("���Ȳ�ѯҪ�޸ĵı����ײͣ�");
		return false;
	}
	
	document.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
	}
	if(checkCalFactor()==false) return false;
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

function getRiskCount(){
    var j=0;
    var k=0;
    var tRiskCode=new Array(); 
    for(i=0;i<ContPlanGrid.mulLineCount;i++)
    {
        var tGetCode = ContPlanGrid.getRowColData(i,2);
        if(tRiskCode[k]==tGetCode){
             continue;
        }else{
           tRiskCode[j]=tGetCode;
           k=j;
           j=j+1;
        }
    }
    return tRiskCode;
}
//����У��
function beforeSubmit(){
	if (document.all('ContPlanCode').value == ""){
		alert("�������ײʹ��룡");
		document.all('ContPlanCode').focus();
		return false;
	}
	if (document.all('mOperate').value != "UPDATE||MAIN"){
		if (document.all('MainRiskCode').value == ""){
			alert("���������ձ��룡");
			//document.all('MainRiskCode').focus();
			return false;
		}
	}
	if (ContPlanGrid.mulLineCount == 0){
		alert("�����뱣���ײ���ϸ��Ϣ");
		return false;
	}
	if  (document.all('ManageCom').value == ""){
	    alert("��ѡ��������");
	    return false;
	    }
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	//���Ҫ��ֵ��ϢУ��
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,5);
      	
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
	}
	return true;
}



function initFactoryType(tRiskCode)
{
	// ��дSQL���
	var k=0;
	var strSQL = "";
//	strSQL = "select distinct a.FactoryType,b.FactoryTypeName,concat(a.FactoryType,"+tRiskCode+") from LMFactoryMode a ,LMFactoryType b  where 1=1 "
//		   + " and a.FactoryType= b.FactoryType "
//		   + " and (RiskCode = '"+tRiskCode+"' or RiskCode ='000000' )";
	
	var sqlid4="LDPlanConfSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tRiskCode);//ָ������Ĳ���
	mySql4.addSubPara(tRiskCode);//ָ������Ĳ���
	strSQL=mySql4.getString();
	
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
	var newWindow = window.open("../app/ContPlanNextInput.jsp?GrpContNo="+document.all('GrpContNo').value+"&LoadFlag="+LoadFlag,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

function afterSubmit(FlagStr,content){
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
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
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
		alert("�������ײʹ��룡");
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
//	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
//		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
//		+ "and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode";
	
	var sqlid5="LDPlanConfSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
	strSQL=mySql5.getString();
	
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
	  
		var sqlid6="LDPlanConfSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
		mySql6.addSubPara(cDutyCode);//ָ������Ĳ���
		tSql=mySql6.getString();
	  
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
//	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3,remark,PlanKind1,PlanKind2,PlanKind3,managecom "
//		+ "from LDPlan "
//		+ "where 1=1 "
//		+ " and ContPlanCode <> '00' "
//		+ " and (managecom='86' or( managecom like concat('"+tManageCom+"','%')) or '"+tManageCom+"' like concat(managecom,'%'))"
//		+ " order by ContPlanCode";
	
	var sqlid7="LDPlanConfSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(tManageCom);//ָ������Ĳ���
	mySql7.addSubPara(tManageCom);//ָ������Ĳ���
	strSQL=mySql7.getString();
		
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
}

//��ѡ���������¼�
function ShowContPlan(parm1,parm2){
	
	var selectRowNum = parm1.replace(/spanContPlanCodeGrid/g,"");
	
	if(document.all('InpContPlanCodeGridSel'+selectRowNum).value == '1' ){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		var cContPlanCode = document.all('ContPlanCodeGrid1'+'r'+selectRowNum).value;	//�ƻ�����
		var cContPlanName = document.all('ContPlanCodeGrid2'+'r'+selectRowNum).value;	//�ƻ�����
		var cPlanSql = document.all('ContPlanCodeGrid3'+'r'+selectRowNum).value;	//����˵��
		var cPeoples3 = document.all('ContPlanCodeGrid4'+'r'+selectRowNum).value;	//�ɱ�����
		var cRemark=document.all('ContPlanCodeGrid5'+'r'+selectRowNum).value;//�ر�Լ��
		var cPlanKind1=document.all('ContPlanCodeGrid6'+'r'+selectRowNum).value;//�ײͲ㼶1
		var cPlanKind2=document.all('ContPlanCodeGrid7'+'r'+selectRowNum).value;//�ײͲ㼶2		
        var cPlanKind3=document.all('ContPlanCodeGrid8'+'r'+selectRowNum).value;//�ײͲ㼶3
        var cManageCom=document.all('ContPlanCodeGrid9'+'r'+selectRowNum).value;//�������
		document.all('ContPlanCode').value = cContPlanCode;
		document.all('ContPlanName').value = cContPlanName;
		document.all('PlanSql').value = cPlanSql;
		document.all('Peoples3').value = cPeoples3;
		document.all('Remark').value=cRemark;
		document.all('PlanKind1').value=cPlanKind1;
		document.all('PlanKind2').value=cPlanKind2;
		document.all('PlanKind3').value=cPlanKind3;
		document.all('ManageCom').value=cManageCom;
		//alert(cContPlanCode);

		//��ѯ�������µ����ּ���Ҫ��
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,'',d.MainRiskCode,d.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//			+ "and d.ContPlanCode = '"+document.all('ContPlanCode').value+"'"
//			+ "  order by a.RiskCode,d.MainRiskCode,a.DutyCode";
		
		var sqlid8="LDPlanConfSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(document.all('ContPlanCode').value);//ָ������Ĳ���
		strSQL=mySql8.getString();
		
		//document.all('PlanSql').value = strSQL;
		
		// Update by YaoYi for bug.
		turnPage.queryModal(strSQL, ContPlanGrid);
		
		

		//ԭ���ϲ���ʧ�ܣ��ٺ�
//		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
//		if (!turnPage.strQueryResult) {
//			alert("��ѯʧ�ܣ�");
//			return false;
//		}
		//��ѯ�ɹ������ַ��������ض�ά����
//		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
//		turnPage.pageDisplayGrid = ContPlanGrid;
		//����SQL���
//		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
//		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
//		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
}

function checkCalFactor(){
   //У��ƻ�Ҫ�ص�ֵ�Ƿ���Ϻ�̨����Ҫ��
   var tCalRule="";
   var tCalRuleInfo="";//�жϼ�������Ƿ��¼
   var tFloatRate="";
   var tFloatRateInfo="";//�жϷ����Ƿ��¼
   var tAmnt="";
   var tAmntInfo="";//�жϱ����Ƿ��¼
   var tPrem="";
   var tPremInfo="";//�жϱ����Ƿ��¼
   var tCalFactor="";
   var tRiskCode=getRiskCount();//�õ��ƻ��ж���������� ������ֵı��ϼƻ�Ҫ�ֱ��ÿ�����ֵ�Ҫ�ؽ���У��
   for(j=0;j<tRiskCode.length;j++){
        var tNowRiskCode = tRiskCode[j];
   		var tRiskName = "";
  		 for(i=0;i<ContPlanGrid.mulLineCount;i++)
  		 {
  		  if(tNowRiskCode==ContPlanGrid.getRowColData(i,2)){
  		  tRiskName = ContPlanGrid.getRowColData(i,1);
  		    tCalFactor = ContPlanGrid.getRowColData(i,5);
  	    //alert("tCalFactor:"+tCalFactor);
  		      if(tCalFactor=="CalRule")
   		      {
      		       tCalRule = ContPlanGrid.getRowColData(i,8);
      		       tCalRuleInfo = ContPlanGrid.getRowColData(i,7);
      		       //alert("tCalRule:"+tCalRule);
             //alert("tCalRuleInfo:"+tCalRuleInfo);
     		    }
     		    if(tCalFactor=="Prem")
    		     {
   		          tPrem = ContPlanGrid.getRowColData(i,8);
    		         tPremInfo = ContPlanGrid.getRowColData(i,6);
    		         //alert("tPrem:"+tPrem);
    		     }
    		     if(tCalFactor=="Amnt")
    		     {
    		         tAmnt = ContPlanGrid.getRowColData(i,8);
     	        tAmntInfo = ContPlanGrid.getRowColData(i,6);
             //alert("tAmnt:"+tAmnt);
      		   }
     		    if(tCalFactor=="FloatRate")
      		   {
     		        tFloatRate = ContPlanGrid.getRowColData(i,8);
     		        tFloatRateInfo = ContPlanGrid.getRowColData(i,6);
            // alert("tFloatRate:"+tFloatRate);
      		   }
      
   		 }
   		}
  		//0-������ 1-Լ������ 2-�������ۿ� 3-Լ�����ѱ���
   		if(tCalRuleInfo!=""){
  		 if(tCalRule==""){
  		   alert(tRiskName+"!��¼�������򣡡�"+tCalRuleInfo+"��");
   		   return false;
		   }else{
		     if(tCalRule=="1")
		     {
      //Լ������  ����=����*��������
      		if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
        //�ܹ�¼�뱣����ѡ�����ʱ�Ŷ����ּ������������У��
      			if(tAmnt==""||tPrem==""||tFloatRate==""){
     			    alert(tRiskName+"!��¼�뱣�ѡ�����������ʣ�");
     			    return false;
     			 }else{
     			    var tCalPrem=tAmnt*tFloatRate;
    			     if(tCalPrem!=tPrem){
    			          alert(tRiskName+"!¼��ı���"+tPrem+"���������ı���"+tCalPrem+"����,������¼�룡");
    			          return false;         
    			     }
    		 	 }
    		 	}
   			 }else if(tCalRule=="2"){
   		     //�������ۿ� ֻ¼�뱣���������
   			     if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	     //�ܹ�¼�뱣����ѡ�����ʱ�Ŷ����ּ������������У��
   			     if(tPrem==""){
   	 		      if(tAmnt==""||tFloatRate==""){
   	  		         alert(tRiskName+"!��¼�뱣��������ʣ�");
   	 		          return false;
   	 		      }
   	 		    }else{
   	 		       alert(tRiskName+"!���������ۿۡ�������¼�뱣�ѣ�");
   	 	       return false;
   			     }
   			    }
  		 	 }else if(tCalRule=="3"){
  		 	 if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	 //�ܹ�¼�뱣����ѡ�����ʱ�Ŷ����ּ������������У��
   			      if(tFloatRate==""){
   	 		       if(tAmnt==""||tPrem==""){
   	 		          alert(tRiskName+"!��¼�뱣�ѡ����");
   	 		          return false;
   	 		       }
   	 		     }else{
   			        alert(tRiskName+"!��Լ�����ѱ��������¼�븡�����ʣ�");
   	 		       return false;
   			      }
   	 		    }
 		  	 }else if(tCalRule=="0"){
 		  	 if(tFloatRateInfo!=""){
 	  	 //�ܹ�¼�����ʱ�ڻ�������У��
 		  	      if(tFloatRate!=""){
  		 	        alert(tRiskName+"!�������ʡ�������¼�븡�����ʣ�");
  		 	        return false;
  		 	      }
  		 	      if(tAmnt==""&&tPrem==""){
  		 	        alert(tRiskName+"!�������ʡ�������Ѳ���ͬʱΪ�գ�");
  		 	        return false;
  		 	      }
  		 	     }
 		  	 }else{
  		 	     alert(tRiskName+"!¼��ļ�����򲻺Ϸ�,������¼�룡��"+tCalRuleInfo+"��");
  		 	     return false;
 		  	 }
 		  }
 		  //�������У������ У����������Ҫ��
 		  var tCalRule="";
   		  var tCalRuleInfo="";//�жϼ�������Ƿ��¼
          var tFloatRate="";
          var tFloatRateInfo="";//�жϷ����Ƿ��¼
          var tAmnt="";
          var tAmntInfo="";//�жϱ����Ƿ��¼
          var tPrem="";
          var tPremInfo="";//�жϱ����Ƿ��¼
          var tCalFactor="";
  		 }else{
  		   //У��221702��221703��������ÿ��סԺ��������
  		   if(tNowRiskCode!=""&&(tNowRiskCode=="221702"||tNowRiskCode=="221703")){
  		     var tMult=document.all('InsuMult').value;
  		     if(tMult!=""&&tMult=="1"){
  		       //һ��ʱ ���������30��50
  		       if(tAmnt!="30"&&tAmnt!="50"){
  		         alert(tRiskName+"!��Ͷ������Ϊ 1 ʱ�����ս�ֻ��Ϊ30��50");
  		         return false;
  		       }
  		     }else{
  		       //Ϊ���ʱ У�鱣�ս���Ƿ�Ϊ30��50 ��������
  		       if(Math.round(tAmnt/tMult) == tAmnt/tMult  )
   				{
   					if(tAmnt/tMult == 30  || tAmnt/tMult == 50)
   					{
   					alert(tRiskName+"!��¼��ı���"+tAmnt+"���ܱ���,���ݱ���Ϊ"+tAmnt/tMult+ "Ԫ");
					//if( !confirm(tRiskName+"��¼��ı���"+tAmnt+"���ܱ���,���ݱ���Ϊ"+tAmnt/tMult+ "Ԫ,�Ƿ񱣴�!" ))
					 // return false;
   		 		   }else{
   		  		  	alert(tRiskName+"!���ս���30Ԫ��50Ԫ��������,����!");
						return false;
   		 		   }
   		    
   				}
   				else{
   					alert(tRiskName+"!���ս���30Ԫ��50Ԫ��������,����!");
					return false;
   				}
  		     }
  		   }
  		 }
   }
   return true;
}

function getcodedata2()
{
//	var sql="select RiskCode, RiskName from LMRiskApp where enddate is null  and riskprop='G' "
//	         +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
//	         +" enddate is null  "
//	         +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode)='G' order by RiskCode";
	
//	var sql="select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"+fm.sysdate.value+"') and riskprop in ('G','D') "
//    +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
//    +" startdate<='"+fm.sysdate.value+"' and (enddate is null or enddate>'"+fm.sysdate.value+"') and ManageComGrp='"+tManageCom+"' "
//    +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode) in ('G','D') order by RiskCode";
	
	
	var sqlid9="LDPlanConfSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("config.LDPlanConfSql"); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(fm.sysdate.value);//ָ������Ĳ���
	mySql9.addSubPara(fm.sysdate.value);//ָ������Ĳ���
	mySql9.addSubPara(fm.sysdate.value);//ָ������Ĳ���
	mySql9.addSubPara(tManageCom);//ָ������Ĳ���
	var sql=mySql9.getString();
	
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("��ѯ���ִ���:",sql);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    document.all("RiskCode").CodeData=tCodeData;
	
}

//�ƻ�������޸Ĳ�ѯ״̬����
function ChangePlan(){
	QueryCount = 0;
    initContPlanDutyGrid();
    initContPlanGrid();
}