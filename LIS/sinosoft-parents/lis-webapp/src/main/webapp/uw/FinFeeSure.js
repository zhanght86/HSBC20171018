//�������ƣ�FinFeeSure.js
//�����ܣ�����ȷ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  initPolGrid();
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
    
    //showInfo.close(); 
    alert(content); 
  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    alert(content);
    
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();  
    
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}



// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	k++;

	if(document.all("PayMode").value=="4" || document.all("PayMode").value=="A" ||document.all("PayMode").value=="B")
	{
		alert("����ȷ�ϲ���ѡ����ֽ��ѷ�ʽ������������");
		document.all("PayMode").focus();
		return;
	}
	// ��дSQL���
	var strSQL = "";
//	strSQL = "select TempFeeNo,ChequeNo,PayMode,PayMoney,AppntName,MakeDate from LJTempFeeClass where "+k+" = "+k+" "
//				 + " and EnterAccDate is null and confmakedate is null  "
//				 + " and ManageCom like '" + fm.comcode.value + "%'"
//				 //��������ת�˲���Ҫ�����̻��̣��Ǳ��չ�˾��Աȥ���в�ѯ���Ƿ��ˣ�Ȼ��ϵͳ��������ȷ�ϡ�
//				// + " and paymode <> '4'"
//				//ֻ����ת��֧Ʊ�ĵ��˵���
//				 + " and paymode='3' "
//				 + getWherePart( 'AppntName')
//				 + getWherePart( 'TempFeeNo')
//				 + getWherePart( 'PayMode')
//				 + getWherePart( 'ChequeNo');
//				// + getWherePart( 'ManageCom','ManageCom','like');
//	if(fm.ManageCom.value!="")
//	{
//		strSQL = strSQL + " and ManageCom like '"+fm.ManageCom	.value+"%'";
//	}
//  if (fm.StartDay.value!="")
//  {
//  	 strSQL = strSQL + " and makedate>='"+fm.StartDay.value+"'";
//  }
//  if (fm.EndDay.value!="")
//  {
//  	 strSQL = strSQL + " and makedate<='"+fm.EndDay.value+"'";
//  }	
  
    var  AppntName1 = window.document.getElementsByName(trim("AppntName"))[0].value;
	var  TempFeeNo1 = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
	var  PayMode1 = window.document.getElementsByName(trim("PayMode"))[0].value;
	var  ChequeNo1 = window.document.getElementsByName(trim("ChequeNo"))[0].value;
     var sqlid1="FinFeeSureSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.FinFeeSureSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(k);//ָ���������
	 mySql1.addSubPara(k);//ָ���������
	 mySql1.addSubPara(fm.comcode.value);//ָ���������
	 mySql1.addSubPara(AppntName1);//ָ���������
	 mySql1.addSubPara(TempFeeNo1);//ָ���������
	 mySql1.addSubPara(PayMode1);//ָ���������
	 mySql1.addSubPara(ChequeNo1);//ָ���������
	 mySql1.addSubPara(fm.ManageCom.value);//ָ���������
	 mySql1.addSubPara(fm.StartDay.value);//ָ���������
	 mySql1.addSubPara(fm.EndDay.value);//ָ���������
	 strSQL = mySql1.getString();
	 
  
  
 // strSQL=wrapSql1('LJTempFeeClass1',
 // 							[fm.AppntName.value,fm.TempFeeNo.value,fm.PayMode.value,
 // 							 fm.ChequeNo.value,fm.ManageCom.value,fm.StartDay.value,
 // 							 fm.comcode.value,fm.EndDay.value]);
  
  //prompt("",strSQL);			 
	//��ѯ���ϼ�
 // var strSql = wrapSql1('LJTempFeeClass2',
 // 							[fm.AppntName.value,fm.TempFeeNo.value,fm.PayMode.value,
 // 							 fm.ChequeNo.value,fm.ManageCom.value,fm.StartDay.value,
 // 							 fm.comcode.value,fm.EndDay.value]);
  							 
  						
	var strSql = "";
//	strSql = "select sum(PayMoney) from LJTempFeeClass where "+k+" = "+k+" "
//				 + " and EnterAccDate is null and confmakedate is null  "
//				 + " and ManageCom like '" + fm.comcode.value + "%'"
//				 //��������ת�˲���Ҫ�����̻��̣��Ǳ��չ�˾��Աȥ���в�ѯ���Ƿ��ˣ�Ȼ��ϵͳ��������ȷ�ϡ�
//				// + " and paymode <> '4'"
//				//ֻ����ת��֧Ʊ�ĵ��˵���
//				 + " and paymode='3' "
//				 + getWherePart( 'AppntName')
//				 + getWherePart( 'TempFeeNo')
//				 + getWherePart( 'PayMode')
//				 + getWherePart( 'ChequeNo');
//				// + getWherePart( 'ManageCom','ManageCom','like');
//	if(fm.ManageCom.value!="")
//	{
//		strSql = strSql + " and ManageCom like '"+fm.ManageCom	.value+"%'";
//	}
//  if (fm.StartDay.value!="")
//  {
//	  strSql = strSql + " and makedate>='"+fm.StartDay.value+"'";
//  }
//  if (fm.EndDay.value!="")
//  {
//	  strSql = strSql + " and makedate<='"+fm.EndDay.value+"'";
//  }	
  
    var  AppntName2 = window.document.getElementsByName(trim("AppntName"))[0].value;
	var  TempFeeNo2 = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
	var  PayMode2= window.document.getElementsByName(trim("PayMode"))[0].value;
	var  ChequeNo2 = window.document.getElementsByName(trim("ChequeNo"))[0].value;
     var sqlid2="FinFeeSureSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.FinFeeSureSql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(k);//ָ���������
	 mySql2.addSubPara(k);//ָ���������
	 mySql2.addSubPara(fm.comcode.value);//ָ���������
	 mySql2.addSubPara(AppntName2);//ָ���������
	 mySql2.addSubPara(TempFeeNo2);//ָ���������
	 mySql2.addSubPara(PayMode2);//ָ���������
	 mySql2.addSubPara(ChequeNo2);//ָ���������
	 mySql2.addSubPara(fm.ManageCom.value);//ָ���������
	 mySql2.addSubPara(fm.StartDay.value);//ָ���������
	 mySql2.addSubPara(fm.EndDay.value);//ָ���������
	 strSql = mySql2.getString();
  
  var arrResult = easyExecSql(strSql);
  //fm.sumMoney.value = arrResult;
  if(arrResult!="null" && arrResult!=null && arrResult!="")
  {
  	fm.sumMoney.value = arrResult;
  }
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��δ�������ݣ�");
    fm.sumMoney.value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	 
				 
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  return true;
}

function autochk()
{
  var haveCheck = false;
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getChkNo(i)) {
      haveCheck = true;
      break;
    }
  }
  
  if (haveCheck) {
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
    fm.action="./FinFeeSureChk.jsp";
    fm.submit(); //�ύ
  }
  else {
    alert("����ѡ��һ���ݽ�����Ϣ����ѡ����д򹳣�");
  }
}

function Notchk()
{
  var NotCheck = false;
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getChkNo(i)) {
      NotCheck = true;
      break;
    }
  }
if (NotCheck) {
    var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    fm.action="./FinFeeNoSureChk.jsp";
    //showSubmitFrame(mDebug);
    fm.submit(); //�ύ
  }
  else {
    alert("����ѡ��һ���ݽ�����Ϣ����ѡ����д򹳣�");
  }
}


function insertCurrentDate() {
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getChkNo(i)) {
      PolGrid.setRowColData(i, 7, currentDate);
    }
  }
}


/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql1(sqlId,param){
	var mysql=new SqlClass();
	
	mysql.setResourceName("finfee.FinFeePayInputSql");
	mysql.setSqlId(sqlId);
	
	for(var i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}