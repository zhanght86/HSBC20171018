//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var QueryResult="";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere="";
window.onfocus=myonfocus;

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

//�����ύ�����棩
function submitForm(){
	//alert("submitForm start");
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	//alert(AscriptionRuleNewGrid.mulLineCount);
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("û��������Ϣ��");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit(); //�ύ
	initAscriptionRuleGrid();
}

//������һ��
function returnparent(){
	window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
}

//�����ύ��ɾ����
function DelContClick(){
	if (confirm("ȷ��Ҫɾ���˹���������") == true)
    {
    	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
    //	alert(AscriptionRuleNewGrid.mulLineCount);
    	if(AscriptionRuleNewGrid.mulLineCount==0){
    		alert("û��������Ϣ��");
    		return false;
    	}
    
    	document.all('mOperate').value = "DELETE||MAIN";
    	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    	document.getElementById("fm").submit(); //�ύ
    }
}

//����У��
function beforeSubmit(){
	if(document.all('AscriptionRuleCode').value.length==0)
	{
		alert("������Ա�����");
		return false;
	}
	//�жϽɷѹ������������Ƿ�����
	//�õ�Muillen������������
	var rowNum=AscriptionRuleNewGrid.mulLineCount ; //����
        var colNum=AscriptionRuleNewGrid.colCount ;     //����
        //alert(rowNum);
        //alert(colNum);
	for(var i=0;i<rowNum;i++)
	{
		for(var j=1;j<=6;j++)
		{
			
			//alert("("+i+","+j+")  "+AscriptionRuleNewGrid.getRowColData(i,j));
			if(AscriptionRuleNewGrid.getRowColData(i,j)=="")
			{
				alert("������Ϣ������,��ȷ�����ݵ������ԣ�");
				return false;
			}
		}
		
		var varParam=AscriptionRuleNewGrid.getRowColData(i,6);
		//alert("varParam"+varParam);
		var strParam=new String(varParam);
		var iArray = new Array();
		iArray=strParam.split(',');
		//alert(iArray[0]);
		//alert(iArray[1]);
		//alert(iArray[2]);
		for(var p=0;p<iArray.length-1;p++)
		{
			strTemp=new String(iArray[p]);
			for(var k=0;k<strTemp.length;k++)
			{
				var aa=strTemp.charAt(k);
				//alert(aa);
				if(!(aa>=0&&aa<=9))
				{
					alert("������Ϣ������,�������ݸ�ʽ�Ƿ���ȷ��");
					return false;
				}
			}
		}
		if(!(parseInt(iArray[0])<parseInt(iArray[1])))
		{
			alert("Ҫ��ֵ�������,��������ȷ��");
			return false;			
		}
		
		if(!(iArray[2]>=0 && iArray[2]<=1))
		{
			alert("������������С��0����1������ȷ���룡");
			return false;	
		}

	}
	return true;
}

function GrpPerPolDefine(){
	// ��ʼ�����
	//alert("GrpPerPolDefine 1");
	var str = "";
	var tGrpContNo = GrpContNo;

	var tImpAscriptionRuleCode = initAscriptionRuleNew(tGrpContNo);
	//alert(tImpAscriptionRuleCode);
	//��ʼ������
	initAscriptionRuleNewGrid(tImpAscriptionRuleCode);
       // alert("GrpPerPolDefine 3");
	divAscriptionRule.style.display= "";
	var strSQL = "";

		  var sqlid53="ContQuerySQL53";
		var mySql53=new SqlClass();
		mySql53.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql53.setSqlId(sqlid53);//ָ��ʹ�õ�Sql��id
		mySql53.addSubPara(GrpContNo);//ָ������Ĳ���
	    strSQL=mySql53.getString();

	//strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where GrpContNo = '" +GrpContNo+ "'";
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	document.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
	document.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
	document.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
	document.all('AppntNo').value = turnPage.arrDataCacheSet[0][3];
	document.all('GrpName').value = turnPage.arrDataCacheSet[0][4];
	return ;
}

function GrpPerPolDefineOld(){
	// ��ʼ�����
	var str = "";
	var tGrpContNo = GrpContNo;
	var tImpAscriptionRuleOldCode = initAscriptionRuleOld(tGrpContNo);
	//alert("is me");
	initAscriptionRuleOldGrid(tImpAscriptionRuleOldCode);
	//alert("not here");

	divAscriptionRuleOld.style.display= "";
	var strSQL = "";


		  var sqlid54="ContQuerySQL54";
		var mySql54=new SqlClass();
		mySql54.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql54.setSqlId(sqlid54);//ָ��ʹ�õ�Sql��id
		mySql54.addSubPara(tGrpContNo);//ָ������Ĳ���
	    strSQL=mySql54.getString();

	//strSQL = "select distinct Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory a where a.GrpContNo='"+tGrpContNo+"' order by Ascriptionrulecode"; 
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		turnPage.pageDisplayGrid = AscriptionRuleOldGrid;
		turnPage.strQuerySql     = strSQL;
		turnPage.pageIndex = 0;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
        return ;
}


function initAscriptionRuleNew(tGrpContNo){
	// ��дSQL���
	var k=0;
	var strSQL = "";

	//strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tGrpContNo+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
	//+ " and a.FactoryType= b.FactoryType "
	//+ " and (RiskCode =('"+tGrpContNo+"' ) or RiskCode ='000000' )";
	
	//alert(GrpContNo);
	
			  var sqlid55="ContQuerySQL55";
		var mySql55=new SqlClass();
		mySql55.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql55.setSqlId(sqlid55);//ָ��ʹ�õ�Sql��id
		mySql55.addSubPara(GrpContNo);//ָ������Ĳ���
	    strSQL=mySql55.getString();
	
	//strSQL = "select a.RiskCode,b.RiskName,a.GrpPolNo from LCGrpPol a,LMRisk b where a.GrpContNo='"+GrpContNo+"' and a.RiskCode = b.RiskCode";
	//document.all('ff').value=strSQL;
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
function initAscriptionRuleOld(tGrpContNo){
	// ��дSQL���
	var k=0;
	var strSQL = "";

		var sqlid56="ContQuerySQL56";
		var mySql56=new SqlClass();
		mySql56.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql56.setSqlId(sqlid56);//ָ��ʹ�õ�Sql��id
		mySql56.addSubPara(tGrpContNo);//ָ������Ĳ���
	    strSQL=mySql56.getString();

	//strSQL = "select Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory where lcAscriptionrulefactory.GrpContNo='tGrpContNo'";
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
//��ѡ���������¼�
function ShowAscriptionRule(parm1,parm2){
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
	var tGrpContNo= GrpContNo;
	//alert(GrpContNo);
	var cAscriptionRuleCode = document.all(parm1).all('AscriptionRuleOldGrid1').value;	//�ƻ�����
	document.all('AscriptionRuleCode').value = cAscriptionRuleCode;
	document.all('AscriptionRuleName').value = document.all(parm1).all('AscriptionRuleOldGrid2').value;
	document.all('AscriptionRuleCodeOld').value = cAscriptionRuleCode;
	document.all('AscriptionRuleNameOld').value = document.all(parm1).all('AscriptionRuleOldGrid2').value;
	//alert("cAscriptionRuleCode");
    var strSQL = "";
     
	 		var sqlid57="ContQuerySQL57";
		var mySql57=new SqlClass();
		mySql57.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql57.setSqlId(sqlid57);//ָ��ʹ�õ�Sql��id
		mySql57.addSubPara( document.all('GrpContNo').value);//ָ������Ĳ���
		mySql57.addSubPara( cAscriptionRuleCode);//ָ������Ĳ���
	    strSQL=mySql57.getString();
	        
//	strSQL = "select riskcode,factorytype,otherno,factorycode||to_char(factorysubcode),calremark,params,factoryname,trim(FactoryType)||trim(RiskCode),grppolno from lcascriptionrulefactory "
//		   + "where grpcontno='"
//		   + document.all('GrpContNo').value + "' "
//		   + "and ascriptionrulecode='" + cAscriptionRuleCode + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		turnPage.pageDisplayGrid = AscriptionRuleNewGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
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
		initForm();
	}
}

function updateClick(){
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	if(AscriptionRuleNewGrid.mulLineCount == 0)
	{
		alert("û��������Ϣ��");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "UPDATE||MAIN";
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
	initAscriptionRuleGrid();
}