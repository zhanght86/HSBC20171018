//WriteToFileInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

var showInfo;
var mDebug="0";
var tSelNo = "";
var filePath = "";


var tResourceName="bank.WriteToFileInputSql";
var tResourceSQL1="WriteToFileInputSql1";
var tResourceSQL2="WriteToFileInputSql2";
var tResourceSQL3="WriteToFileInputSql3";
var tResourceSQL4="WriteToFileInputSql4"; 

//�ύ�����水ť��Ӧ����
function submitForm() {
  //if(verifyInput() == false) return false;  
  
  if (BankGrid.getSelNo()) { 
    document.all("serialNo").value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);
    document.all("fmtransact").value = "create"
    
    var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./WriteToFileSave.jsp";
    document.getElementById("fm").submit(); //�ύ
  }
  else {
    alert("����ѡ��һ�����κ���Ϣ��"); 
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ) {
  try { showInfo.close(); } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=300;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  easyQueryClick();
}

//ѡ������ļ�����
function fileDownload() {
  if (BankGrid.getSelNo()) { 
    //alert(fileUrl);
    //fileUrl.href = filePath + BankGrid.getRowColData(BankGrid.getSelNo()-1, 2);
    //fileUrl.click();
    document.all('Url').value = filePath;
    document.all('fmtransact').value = "download";
    fm.action="./WriteToFileSave.jsp";
    document.getElementById("fm").submit();
  }
  else {
    alert("����ѡ��һ�����κ���Ϣ�������ض�Ӧ���ļ���"); 
  } 
}

function afterSubmit2( FlagStr, content )
{
	unlockScreen('lkscreen');  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  try { showInfo.close(); } catch(e) {}
	alert(content);
  easyQueryClick();
  fm.sendBankFileButton.disabled = false;
}
function sendBankFile() 
{
  if (BankGrid.getSelNo()) 
  { 
    //alert("sendBankFile");
    document.all("serialNo").value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);
    document.all('fmtransact').value = "send";
    
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    lockScreen('lkscreen');
    fm.sendBankFileButton.disabled = true;
    fm.action="./SendBankFileSave.jsp";
    document.getElementById("fm").submit();
  }
  else 
  {
    alert("����ѡ��һ�����κ���Ϣ"); 
  } 
}

function viewBatDetail() 
{
  if (BankGrid.getSelNo()) 
  { 
   //alert("viewBatDetail");
   window.open("BankSerDetailMain.jsp?SerialNo="+BankGrid.getRowColData(BankGrid.getSelNo()-1, 1));
  }
  else 
  {
    alert("����ѡ��һ�����κ���Ϣ"); 
  } 
}

function initBankFileDealDiv()
{
	if(tBankFlag == "1")  //������ʱ�ͨ�����������ۣ���ʾ��Ӧ�İ�ť
	{
		DivBankFileDeal.style.display="none";
		DivYBTBankFileDeal.style.display="";
		fm.YBTBankCode.value = tYBTBankCode;
	}
	
}

//�ύ���Զ������ļ�����
function downAfterSubmit(cfilePath,cflag) {
 //���ڷ��������۴����������ļ�
 //alert("BankFlag: "+tBankFlag);
 if(tBankFlag != "1")
 {
	filePath = cfilePath;
	
	if (cflag == 0)
	{
		if(BankGrid.getRowColData(BankGrid.getSelNo()-1, 2)==""){
			alert("No File");
			return;
		}
		fileUrl.href = filePath + BankGrid.getRowColData(BankGrid.getSelNo()-1, 2);
 	}
	else
	{
  	fileUrl.href = filePath + turnPage.arrDataCacheSet[tSelNo][1];
	}
	//alert(fileUrl.href);
  fileUrl.click();
 }
}

//��ȡ�ļ�����·��
function getFilePath() {
  //var strSql = "select SysVarValue from LDSysVar where SysVar = 'DisplayBankFilePath'";
  
   var sqlid1=tResourceSQL1;
	var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara("");
  var strSql = mySql1.getString(); 
 	//alert(strSql);
  filePath = easyExecSql(strSql);
  
  document.all('Url').value = filePath;
  
  document.all('fmtransact').value = "";
  //alert(filePath);
}

// ��ѯ��ť
function easyQueryClick() {
	// ��дSQL���
	//var strSql = "select SerialNo, OutFile from LYBankLog where InFile is null and ComCode like '" + comCode + "%%'"
	//           + tBankDataSQL
	//           + " and LogType='" + dealType + "'"
	//			     + getWherePart('BankCode')
	//			     + getWherePart('StartDate')
	//			     +" order by SerialNo desc";
	//alert('1');
	//alert(tBankDataSQL);
	 var tComCode = comCode;
   var tDealType = dealType;
   var tBankCode = document.all("BankCode").value;
   var tStartDate = document.all("StartDate").value;
   //var tBankDataSQL = tBankDataSQL
  
  // alert('2');
    var sqlid1="WriteToFileInputSql2";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(tComCode);//ָ������Ĳ���
   // alert('3');
    mySql1.addSubPara(tBankDataSQL);//ָ������Ĳ���
   // alert('4');
    mySql1.addSubPara(tDealType);//ָ������Ĳ���
    mySql1.addSubPara(tBankCode);//ָ������Ĳ���
    mySql1.addSubPara(tStartDate);//ָ������Ĳ���
    
         
//	alert('no save11121');
	    var tempSQL2 =mySql1.getString();	
//alert(tempSQL2);
	turnPage.queryModal(tempSQL2, BankGrid);
}

//
function showStatistics(parm1, parm2) {
  //var strSql = "select Totalmoney, Totalnum from lybanklog where SerialNo = '" 
  //           + BankGrid.getRowColData(BankGrid.getSelNo() - 1, 1) 
  //           + "'";
 //  var strSql = wrapSql(tResourceName,tResourceSQL3,[BankGrid.getRowColData(BankGrid.getSelNo() - 1, 1)]); 			             
  //alert(easyExecSql(strSql));      
  
   var sqlid1=tResourceSQL3;
	var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(BankGrid.getRowColData(BankGrid.getSelNo() - 1, 1));
  var strSql = mySql1.getString(); 
  
  
  var arrResult = easyExecSql(strSql);
  
  document.all("TotalMoney").value = arrResult[0][0];
  document.all("TotalNum").value = arrResult[0][1];
  
  tSelNo = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
}
