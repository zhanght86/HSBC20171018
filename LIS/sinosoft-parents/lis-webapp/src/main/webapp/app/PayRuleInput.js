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
	PayRuleNewGrid.delBlankLine("PayRuleGrid");
	if(PayRuleNewGrid.mulLineCount==0){
		alert("û��������Ϣ��");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
	initPayRuleGrid();
}

//������һ��
function returnparent(){
	if (AskFlag == 0)
    {
	    window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
	}
    else if (AskFlag == 1)
    {
        //ѯ�۱����ɷѶ���
        window.location.href = "../askapp/AskGrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
    }
}

//�����ύ��ɾ����
function DelContClick(){
    if (confirm("ȷ��Ҫɾ���˽ɷѹ�����") == true)
    {
    	PayRuleNewGrid.delBlankLine("PayRuleGrid");
    //	alert(PayRuleNewGrid.mulLineCount);
    	if(PayRuleNewGrid.mulLineCount==0){
    		alert("û��������Ϣ��");
    		return false;
    	}
    	
    	var tSelNo = PayRuleOldGrid.getSelNo();
    	if (tSelNo <= 0)
    	{
    	    alert("��ѡ����Ҫɾ���Ľɷѹ���");
    	    return;    
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
    	document.getElementById("fm").submit(); //�ύ
    }
}

//����У��
function beforeSubmit(){
	if(document.all('PayRuleCode').value.length==0)
	{
		alert("������Ա�����");
		return false;
	}
	//�жϽɷѹ������������Ƿ�����
	//�õ�Muillen������������
	var rowNum=PayRuleNewGrid.mulLineCount ; //����
        var colNum=PayRuleNewGrid.colCount ;     //����
        //alert(rowNum);
        //alert(colNum);
	for(var i=0;i<rowNum;i++)
	{
		for(var j=1;j<=6;j++)
		{
			
			if(PayRuleNewGrid.getRowColData(i,j)=="")
			{			
				alert("������Ϣ������,��ȷ�����ݵ������ԣ�");
				return false;
			}
		}

		var varParam=PayRuleNewGrid.getRowColData(i,6);
		
		var strParam=new String(varParam);
		var iArray = new Array();
		iArray=strParam.split(',');	
		for(var p=0;p<iArray.length-1;p++)
		{
			strTemp=new String(iArray[p]);
			for(var k=0;k<strTemp.length;k++)
			{
				var aa=strTemp.charAt(k);
				
				if(!(aa>=0&&aa<=9))
				{
					alert("������Ϣ������,�������ݸ�ʽ�Ƿ���ȷ��");
					return false;
				}
			}
		}
		if(!(parseInt(iArray[0])<parseInt(iArray[1])))
		{
			alert("������Ϣ������,�������ݸ�ʽ�Ƿ���ȷ��");
			return false;			
		}
		if(!(iArray[2]>=0))
		{
			alert("������Ϣ������,�������ݸ�ʽ�Ƿ���ȷ��");
			return false;	
		}

	}
	return true;
}

function GrpPerPolDefine(){
	// ��ʼ�����
	var str = "";
	var tGrpContNo = GrpContNo;

	var tImpPayRuleCode = initPayRuleNew(tGrpContNo);
	//��ʼ������
	initPayRuleNewGrid(tImpPayRuleCode);

	divPayRule.style.display= "";
	var strSQL = "";

	//strSQL = "select RiskCode,FactoryType,OtherNo,FactoryCode||to_char(FactorySubCode),CalRemark,Params,FactoryName,trim(FactoryType)||trim(RiskCode),GrpPolNo " 
	//	+ "from LCPayRuleFactory where 1=1 "
	//	+ "and GrpContNo='" +tGrpContNo+ "' "
	//	+ "order by FactoryType, OtherNo,FactoryCode,FactorySubCode ";
	//document.write(strSQL);
	//turnPage = new turnPageClass();
	//turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		//��ѯ�ɹ������ַ��������ض�ά����
		//turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		//turnPage.pageDisplayGrid = PayRuleGrid;
		//����SQL���
		//turnPage.strQuerySql     = strSQL;
		//���ò�ѯ��ʼλ��
		//turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//����MULTILINE������ʾ��ѯ���
		//displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

		//strSQL = "select PayRuleCode,PayRuleName from LCPayRuleFactory where GrpContNo = '" +GrpContNo+ "'";
		//turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
		//turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//document.all('PayRuleCode').value = turnPage.arrDataCacheSet[0][0];
		//document.all('PayRuleName').value = turnPage.arrDataCacheSet[0][1];
	}

		 var sqlid89="ContQuerySQL89";
		var mySql89=new SqlClass();
		mySql89.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql89.setSqlId(sqlid89);//ָ��ʹ�õ�Sql��id
		mySql89.addSubPara(GrpContNo);//ָ������Ĳ���
	    strSQL=mySql89.getString();	


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
	var tImpPayRuleOldCode = initPayRuleOld(tGrpContNo);
	//alert("is me");
	initPayRuleOldGrid(tImpPayRuleOldCode);
	//alert("not here");

	divPayRuleOld.style.display= "";
	var strSQL = "";

		 var sqlid90="ContQuerySQL90";
		var mySql90=new SqlClass();
		mySql90.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql90.setSqlId(sqlid90);//ָ��ʹ�õ�Sql��id
		mySql90.addSubPara(GrpContNo);//ָ������Ĳ���
	    strSQL=mySql90.getString();	

	//strSQL = "select distinct payrulecode,payrulename from lcpayrulefactory a where a.GrpContNo='"+tGrpContNo+"' order by payrulecode"; 
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		turnPage.pageDisplayGrid = PayRuleOldGrid;
		turnPage.strQuerySql     = strSQL;
		turnPage.pageIndex = 0;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
        return ;
}


function initPayRuleNew(tGrpContNo){
	// ��дSQL���
	var k=0;
	var strSQL = "";

	/*strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tGrpContNo+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
	+ " and a.FactoryType= b.FactoryType "
	+ " and (RiskCode =('"+tGrpContNo+"' ) or RiskCode ='000000' )";
	*/
	//alert(GrpContNo);
	
		var sqlid91="ContQuerySQL91";
		var mySql91=new SqlClass();
		mySql91.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql91.setSqlId(sqlid91);//ָ��ʹ�õ�Sql��id
		mySql91.addSubPara(GrpContNo);//ָ������Ĳ���
	    strSQL=mySql91.getString();	
	
	//strSQL = "select a.RiskCode,b.RiskName,a.GrpPolNo from LCGrpPol a,LMRisk b where a.GrpContNo='"+GrpContNo+"' and a.RiskCode = b.RiskCode";
	//document.all('ff').value=strSQL;
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
function initPayRuleOld(tGrpContNo){
	// ��дSQL���
	var k=0;
	var strSQL = "";

		var sqlid92="ContQuerySQL92";
		var mySql92=new SqlClass();
		mySql92.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql92.setSqlId(sqlid92);//ָ��ʹ�õ�Sql��id
		mySql92.addSubPara(tGrpContNo);//ָ������Ĳ���
	    strSQL=mySql92.getString();	


	//strSQL = "select payrulecode,payrulename from lcpayrulefactory where lcpayrulefactory.GrpContNo='tGrpContNo'";
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
//��ѡ���������¼�
function ShowPayRule(parm1,parm2){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		//alert("not me");
		var tGrpContNo= GrpContNo;
		//alert(GrpContNo);
		var cPayRuleCode = document.all(parm1).all('PayRuleOldGrid1').value;	//�ƻ�����
		//alert("cPayRuleCode");
	document.all('PayRuleCode').value = cPayRuleCode;
	document.all('PayRuleName').value = document.all(parm1).all('PayRuleOldGrid2').value;
	document.all('PayRuleCodeOld').value = cPayRuleCode;
	document.all('PayRuleNameOld').value = document.all(parm1).all('PayRuleOldGrid2').value;
               var strSQL="";
         /*
                      strSQL="select PayRulecode,payRulename,(select payplanname from lmdutypay where payplancode=lcpayrulefactory.otherno),Calremark,params from lcpayrulefactory where payrulecode='"+cPayRuleCode+"' and lcpayrulefactory.GrpContNo='"+tGrpContNo+"'";
             
                   //   document.write(strSQL);
                      	turnPage = new turnPageClass();
	        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert("not me");
        
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		turnPage.pageDisplayGrid = PayRuleGrid;
		//����SQL���
		turnPage.strQuerySql     = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
	*/
	
			var sqlid93="ContQuerySQL93";
		var mySql93=new SqlClass();
		mySql93.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql93.setSqlId(sqlid93);//ָ��ʹ�õ�Sql��id
		mySql93.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
		mySql93.addSubPara(cPayRuleCode);//ָ������Ĳ���
	    strSQL=mySql93.getString();	
	
//	strSQL="select riskcode,factorytype,otherno,factorycode||to_char(factorysubcode),calremark,params,factoryname,trim(FactoryType)||trim(RiskCode),grppolno from lcpayrulefactory "
//		+ "where grpcontno='"+document.all('GrpContNo').value+"' "
//		+ "and payrulecode='"+cPayRuleCode+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		turnPage.pageDisplayGrid = PayRuleNewGrid;
		//����SQL���
		turnPage.strQuerySql     = strSQL;
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
		initForm();
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	}
}

function updateClick(){
	PayRuleNewGrid.delBlankLine("PayRuleGrid");
	if(PayRuleNewGrid.mulLineCount==0){
		alert("û��������Ϣ��");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "UPDATE||MAIN";
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
	initPayRuleGrid();
}