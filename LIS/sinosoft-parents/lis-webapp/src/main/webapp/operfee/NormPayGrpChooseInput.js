
var turnPage = new turnPageClass();
var showInfo;
var importPath;
var importConfigFile;

// ��ͨ�����ѯ
function easyQueryClick() {
/*	
  var strSql = "select SysVar, SysVarType, SysVarValue from ldsysvar where 1=1 "
	       + getWherePart( 'SysVar' )
	       + getWherePart( 'SysVarType' )
	       + getWherePart( 'SysVarValue' );
*/
  var strSql="";
  //strSql="select Prem,PaytoDate,ManageFeeRate,GrpName,GrpContNo from LCGrpPol where GRPCONTNO='"+document.all('GrpPolNo').value+"' and payintv=-1";   
  strSql=wrapSql('LCGrpPol1',[document.all('GrpPolNo').value]);
  var arrResult=easyExecSql(strSql);
  if(arrResult==null)
  {
  alert("�޷��������ļ��屣������ȷ�������ڲ����ڽɷ�");
  NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid"); 
  return false;	
  }
  document.all('SumDuePayMoney').value=arrResult[0][0]||"";
  document.all('PaytoDate').value=arrResult[0][1]||"";
  document.all('ManageFeeRate').value=arrResult[0][2]||"";
  document.all('GrpName').value=arrResult[0][3]||"";  
  //�������屣���� ljspayperson���е�GRPCONTNO����Ϊ��
  document.all('GrpContNo').value = arrResult[0][4];
  
  //strSql = "select LCPrem.ContNo,LCPrem.DutyCode,LCPrem.PayPlanCode,(select payplanname from lmdutypay where payplancode=lcprem.payplancode ),LCPol.InsuredName,LCPrem.Prem,LJSPayPerson.Sumactupaymoney,LJSPayPerson.InputFlag,decode(LCPol.InsuredSex,0,'��',1,'Ů',2,'����',LCPol.InsuredSex),LCPol.InsuredBirthday from LCPrem,LJSPayPerson,LCPol where LCPol.GRPCONTNO='"+document.all('GrpPolNo').value+"' ";
  //strSql =strSql+" and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";			
  //strSql =strSql+" and LCPrem.PolNo=LJSPayPerson.PolNo";
  //strSql =strSql+" and LCPrem.PolNo=LCPol.PolNo";
  //strSql =strSql+" and LCPrem.DutyCode=LJSPayPerson.DutyCode";
  //strSql =strSql+" and LCPrem.PayPlanCode=LJSPayPerson.PayPlanCode";
  //strSql =strSql+" and LCPol.appflag='1'";
  //strSql =strSql+" and LCPol.paytodate<LCPol.payenddate and LJSPayPerson.paytype in ('ZC','TM')";
  //strSql =strSql+" "+getWherePart('lcpol.PolNo','PolNo1','like');   
  
  strSql=wrapSql('LCPrem1',[document.all('GrpPolNo').value,document.all('PolNo1').value]);
  
  /*
  strSql =strSql+" UNION ";
  strSql =strSql+"select LCPrem.PolNo,LCPrem.DutyCode,LCPrem.PayPlanCode,LMDutyPay.PayPlanName,LCPol.InsuredName,LCPrem.Prem,LCPrem.Prem ,'0',decode(LCPol.InsuredSex,0,'��',1,'Ů',2,'����',LCPol.InsuredSex),LCPol.InsuredBirthday,lcpol.SequenceNo from LCPrem,lcpol,LMDutyPay where LCPrem.GrpPolNo='"+document.all('GrpPolNo').value+"' ";
  strSql =strSql+" and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";
  strSql =strSql+" and LCPrem.PolNo=LCPol.PolNo";		
  strSql =strSql+" and LCPrem.PayPlanCode=LMDutyPay.PayPlanCode";
  strSql =strSql+" and 0=(select count(*) from LJSPayPerson where PolNo=LCPrem.PolNo and DutyCode=LCPrem.DutyCode and PayPlanCode=LCPrem.PayPlanCode)";
  strSql =strSql+" and LCPol.appflag='1'";
  strSql =strSql+" and LCPol.paytodate<LCPol.payenddate";
  strSql =strSql+" "+getWherePart('lcpol.PolNo','PolNo1','like');
  */
  //strSql =strSql+" order by 11,5,3 "	
  
    

	//��ѯSQL�����ؽ���ַ���
  //prompt('',strSql);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid");  	
    alert("û�в�ѯ�����ݣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = NormPayGrpChooseGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function fmSubmit()
{
    if(checkValue())
    {   
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

    document.getElementById("fm").submit();    
    }
    	
}

function ToExcel()
{
    if(fmFileImport.GrpPolNo1.value=="")
    {
    	alert("������Ҫ�ŵ���!");
    	return;
    }
    var GrpPolNo1=fmFileImport.GrpPolNo1.value;
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
    
  parent.fraInterface.fmToExcel.action = "./NormPayToExcel.jsp?GrpPolNo1="+GrpPolNo1
  ;
  document.getElementById("fmToExcel").submit();    	
}

function checkValue()
{
   if(!verifyInput())
     return false;
  
   return true;
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
   
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
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
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//    NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid");   
  }
}

function afterSubmit( FlagStr, content ,operator,file1)
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
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
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(operator=="download")
	{
  		parent.fraInterface.fmToExcel.action = "./download.jsp?file="+file1;
  		document.getElementById("fmToExcel").submit(); 
  	}
  }

}



//���̵���
function fileImport()
{
if(fmFileImport.FileName.value=="")
  {
  	alert("���ϴ�Excel�ļ���");
  	return ;
  }
  getImportPath();
  getConfigFileName();
// var importConfigFile="ExcelImportNormPayCollConfig.xml";
 
 fmFileImport.ImportConfigFile.value=importConfigFile;

 var GrpPolNo1=fmFileImport.GrpPolNo1.value;
 //var GrpContNo=document.all('GrpContNo').value;
 
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
  
  var importFile = fmFileImport.FileName.value;
  parent.fraInterface.fmFileImport.action = "./FileImportNormPayCollSaveAll.jsp?ImportPath="+importPath
  +"&ImportFile="+importFile
  +"&ImportConfigFile="+importConfigFile
  +"&GrpPolNo1="+GrpPolNo1
  +"&GrpContNo="+GrpPolNo1
  ;
 document.getElementById("fmFileImport").submit(); //�ύ
	
}

function getConfigFileName ()
{
		// ��дSQL���
	var strSQL = "";

	//strSQL = "select SysvarValue from ldsysvar where sysvar ='PrePayPerson_Config'";
	strSQL=wrapSql('LDSysVar',[]);	
			 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	alert("δ�ҵ��ϴ�·�����뵽ldsysvar��鿴sysvar ='PrePayPerson_Config'�����ݼ�¼��Ϣ");
    return;
	}
	
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  importConfigFile = turnPage.arrDataCacheSet[0][0];
}


function getImportPath ()
{
		// ��дSQL���
	var strSQL = "";

//	strSQL = "select SysvarValue from ldsysvar where sysvar ='PrePayPerson'";PrePayPerson_File
	//strSQL = "select SysvarValue from ldsysvar where sysvar ='PrePayPerson_File'";			 
	strSQL=wrapSql('LDSysVar1',[]);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	alert("δ�ҵ��ϴ�·�����뵽ldsysvar��鿴sysvar ='TranDataPath'�����ݼ�¼��Ϣ");
    return;
	}
	
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  importPath = turnPage.arrDataCacheSet[0][0];
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("��LLReport.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}   
        
//�ύ��ǰҳ����
function submitCurData()
{
  if(NormPayGrpChooseGrid.mulLineCount==0)
    alert("��ǰҳû�п����ύ������");
  else{      
      fmSubmit();
      }
}

//�ύ����ѡ�����ݣ���̨������
function verifyChooseRecord()
{
    if(checkValue())
    { 
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
    fmSubmitAll.SubmitGrpPolNo.value=document.all('GrpPolNo').value;
    fmSubmitAll.SubmitPayDate.value=document.all('PayDate').value;
    fmSubmitAll.SubmitManageFeeRate.value=document.all('ManageFeeRate').value;
    fmSubmitAll.SubmitGrpContNo.value=document.all('GrpContNo').value;
         
    document.getElementById("fmSubmitAll").submit();
    }
}

//ֱ���ύ�������ݣ���̨������
function submitCurDataAll()
{
	var GrpPolNo1=fmFileImport.GrpPolNo1.value;
	if(GrpPolNo1=="")
	{
		alert("�������ŵ���!");
		return false;
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
    parent.fraInterface.fmFileImport.action = "./NormPayGrpChooseSaveAll.jsp?GrpPolNo1="+GrpPolNo1;
    document.getElementById("fmFileImport").submit();	
}

//��ѯ����
function queryRecord()
{
   if(checkValue())
   { 
    document.all('PolNo').value=document.all('GrpPolNo').value;
    fmFileImport.GrpPolNo1.value=document.all('GrpPolNo').value;	  
    easyQueryClick();
//    fmQuery.submit();
   }
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	
	try
	{
		arrReturn = getQueryResult();
		top.opener.afterQuery( arrReturn );
	}
	catch(ex)
	{
		alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
	}
	top.close();
}


/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.NormPayGrpChooseInput");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}

