//���ļ��а����ͻ�����Ҫ����ĺ������¼� 

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.PEdorTypePUInputSql";

function edorTypePTReturn()
{
		initForm();
}

function edorTypePTSave()
{ 
  if (document.all('CalMode').value == 'O') {
  	var nRemainMulti = parseFloat(document.all('RemainMulti').value);
  	var nMulti = parseFloat(document.all('Multi').value);
  	if (nRemainMulti > nMulti) {
  		alert("��������С��ԭ������");
  		return;
  	}
  	if (nRemainMulti <= 0 ) {
  		alert("���������������0��");
  		return;
    }  		
  } else {
    var nRemainAmnt = parseInt(document.all('RemainAmnt').value);
    var nAmnt = parseInt(document.all('Amnt').value);
  	//if (nRemainAmnt > nAmnt) {
  	//	alert("������������С��ԭ���");
  	//	return;
  	//}
  	if (nRemainAmnt <= 0) {
  		alert("���������������0��");
  		return;
  	}  	
  }  
  
  //���ձ�ȫ���ã���һ�ν���ʱ�ṩ¼�빦��
	if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==false) {
	  //����¼������
	  top.opener.GTArr.push(fm.RemainAmnt.value);

	  
	  top.opener.GrpBQ = true;
	  top.opener.pEdorMultiDetail();
	  top.close();
	} 
	//**************************************
	else {
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
    
    document.all('fmtransact').value = "INSERT||MAIN";  	  
    
    fm.submit();
  }

}

function customerQuery()
{	
//	window.open("./LCAppntIndQuery.html");
	window.open("./LCAppntIndQuery.html","",sFeatures);
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
  initLCAppntIndGrid();
 //  showSubmitFrame(mDebug);
  fm.submit(); //�ύ
}


////�ύ�����,���������ݷ��غ�ִ�еĲ���
//function afterSubmit( FlagStr, content,Result )
//{
//  showInfo.close();
//  if (FlagStr == "Fail" )
//  {             
//    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//  }
//  else
//  { 
//  	var tTransact=document.all('fmtransact').value;
////  	alert(tTransact);
//		if (tTransact=="QUERY||MAIN")
//		{
//			var iArray;
//			//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
//  		    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
//			//�����ѯ����ַ���
// 		 	turnPage.strQueryResult  = Result;
//    
// 	 		//��ѯ�ɹ������ַ��������ض�ά����
//	  		var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
////	  		alert(tArr[0]);
////	  		alert(tArr[0].length);
//	  		document.all('Amnt').value = tArr[0][45];
//	  		document.all('Prem').value = tArr[0][42];
//	  		document.all('AppntNo').value = tArr[0][28];
//	  		document.all('AppntName').value = tArr[0][29];
//	  		document.all('Multi').value = tArr[0][40]; 		
//	  		document.all('CalMode').value = tArr[0][134];	  		
//
//	  		//calMode�Ǹ����ڱ������ַ����������ģ������������
//	  		var calMode = tArr[0][134];	 
//	  		//alert(tArr[0][tArr[0].length-2]); 		
//
//	  		if (calMode == 'P') {	  		
//	  			var urlStr="../common/jsp/MessagePage.jsp?picture=S&content= �˱����޷����в����˱�����" ;  
//			    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//	   			initForm();
//	   			return;
//  			} else if (calMode == 'O') {   //���÷����仯���м���
//  			
//  			    //document.all('RemainMulti').value = tArr[0][132];
//  				RemainAmntDiv.style.display = "none";
//  				RemainMultiDiv.style.display = "";  				
//  			} else {                      //���ñ���仯���м���
//  				
//  				//document.all('RemainAmnt').value = tArr[0][132];
//  				RemainMultiDiv.style.display = 'none';	
//  				RemainAmntDiv.style.display = '';					
//  			}
//			
//				//���ձ�ȫ���ã��Զ���д��ֵ�����ύ
//	    	if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
//	    	  fm.RemainAmnt.value = top.opener.GTArr.pop();
//	    	  
//	    	  edorTypePTSave();
//	     	}
//     		//***********************************
//
//		  }
//		  else {
//      
//      //���ձ�ȫ���ã��ύ�ɹ����ٴε��ã���ѭ��
//   		if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
//			  top.opener.PEdorDetail();
//			  top.close();
//			}
//			else {
//			  var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//   		  initForm();
//   		}
//  	}
//  }
//}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  try { showInfo.close(); } catch (e) {}

  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           
       
//---------------------------
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

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{

}

function returnParent()
{
 top.opener.initEdorItemGrid();
 top.opener.getEdorItemGrid();
 top.close();
 top.opener.focus();
}

function personQuery()
{
    //window.open("./LCPolQuery.html");
//    window.open("./LPTypeIAPersonQuery.html");
    window.open("./LPTypeIAPersonQuery.html","",sFeatures);
}

function afterPersonQuery(arrResult)
{
    if (arrResult == null ||arrResult[0] == null || arrResult[0][0] == "" )
        return;

    //ѡ����һ��Ͷ����,��ʾ��ϸϸ��
    document.all("QueryCustomerNo").value = arrResult[0][0];
    
//    var strSql = "select * from ldperson where customerNo = " + arrResult[0][0];
    
    var strSql = "";
	var sqlid1="PEdorTypePUInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(arrResult[0][0]);//ָ������Ĳ���
	strSql=mySql1.getString();
    
	//��ѯSQL�����ؽ���ַ���
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

 //   alert(turnPage.strQueryResult);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {  
    	//���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
      	VarGrid.clearData('VarGrid');  
      	alert("��ѯʧ�ܣ�");
      	return false;
  	}

  
 	 //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
    //��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    fillPersonDetail();
    
  divLPAppntIndDetail.style.display = "";
    
    	
	
}

function fillPersonDetail()
{
	try {
		document.all("AppntCustomerNo").value = turnPage.arrDataCacheSet[0][0];
		document.all("AppntName").value = turnPage.arrDataCacheSet[0][2];
		document.all("AppntSex").value = turnPage.arrDataCacheSet[0][3];
		document.all("AppntBirthday").value = turnPage.arrDataCacheSet[0][4];
		
		document.all("AppntIDType").value = turnPage.arrDataCacheSet[0][16];
		document.all("AppntIDNo").value = turnPage.arrDataCacheSet[0][18];
		document.all("AppntNativePlace").value = turnPage.arrDataCacheSet[0][5];
		
		document.all("AppntPostalAddress").value = turnPage.arrDataCacheSet[0][24];
		document.all("AppntZipCode").value = turnPage.arrDataCacheSet[0][25];
		document.all("AppntHomeAddress").value = turnPage.arrDataCacheSet[0][23];
		document.all("AppntHomeZipCode").value = turnPage.arrDataCacheSet[0][22];
		
		document.all("AppntPhone").value = turnPage.arrDataCacheSet[0][26];
		document.all("AppntPhone2").value = turnPage.arrDataCacheSet[0][56];
		document.all("AppntMobile").value = turnPage.arrDataCacheSet[0][28];
		document.all("AppntEMail").value = turnPage.arrDataCacheSet[0][29];
		document.all("AppntGrpName").value = turnPage.arrDataCacheSet[0][38];
		
		document.all("AppntWorkType").value = turnPage.arrDataCacheSet[0][48];
		document.all("AppntPluralityType").value = turnPage.arrDataCacheSet[0][49];
		document.all("AppntOccupationCode").value = turnPage.arrDataCacheSet[0][50];
		document.all("AppntOccupationType").value = turnPage.arrDataCacheSet[0][9];
    } catch(ex) {
    	alert("��PEdorTypePT.js-->fillPersonDetail�����з����쳣:��ʼ���������!");
  	}      
}
function edorTypePUSave()
{
	if (document.all('PageIsFormating').value == "Y")
	{
		alert("��������ˢ�£����Եȣ�");
		return;
	}
	
	//����У��
	if (!verifyInput2())
	{
		return false;
	}
	
	if (document.all('IninSuccessFlag').value != "1")
	{
		if (document.all('IninSuccessFlag').value == "0")
		{
			alert("ҳ���ʼ��δ��ɣ����ܱ��棡");
		}
		if (document.all('IninSuccessFlag').value == "2")
		{
			alert("����������ʧ�ܣ����ܱ��棡");
		}
		return;
	}
	
	document.all('fmtransact').value ="INSERT||MAIN";  
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action="./PEdorTypePUSubmit.jsp"
	fm.submit();
}
function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}
function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
//	var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
    
    var strSQL = "";
	var sqlid2="PEdorTypePUInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tEdortype);//ָ������Ĳ���
	strSQL=mySql2.getString();
    
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //ְҵ���
}

function initGetDutyCode(cObjCode,cObjName)
{
    condition = " 1 and RiskCode = #00145000#" ;
    showCodeList('getdutykind',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function keyUpGetDutyCode(cObjCode,cObjName)
{
    condition = " 1 and RiskCode = #00145000#" ;
    showCodeListKey('getdutykind',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function initInsuYear(cObjCode,cObjName)
{
		if (document.all('RiskCode').value == "00607000" || document.all('RiskCode').value == "00627000")
		{
      condition = " 1 and RiskCode = #00147000#" ;
    }
    else
  	{
  		condition = " 1 and RiskCode = #00147000# and ParamsCode>=10" ;
  	}
    showCodeList('insuyear',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function keyUpInsuYear(cObjCode,cObjName)
{
		if (document.all('RiskCode').value == "00607000" || document.all('RiskCode').value == "00627000")
		{
      condition = " 1 and RiskCode = #00147000#" ;
    }
    else
  	{
  		condition = " 1 and RiskCode = #00147000# and ParamsCode>=10" ;
  	}
    showCodeListKey('insuyear',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function afterCodeSelect(cCodeName, Field)
{
	try 
	{
		document.all('PageIsFormating').value = "Y";
		queryNewData();
	} 
	catch(ex) 
	{
		alert("��afterCodeSelect�з����쳣��");
	}
}

function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}


function queryNewData()
{
	try
	{
		if (document.all('SpecialRiskFlag').value == "Y")
		{
			if (document.all('InsuYear').value == null || document.all('InsuYear').value == "")
			{
				if (document.all('GetDutyKind').value == null || document.all('GetDutyKind').value == "")
				{
					//return;
					//֤�����������ֵĽ����ʼ����ѯ�����ߺ�ֻ̨��ѯ������Ϣ
					document.all('SpecialRiskInitFlag').value = "1";
				}
			}
		}
		
    var showStr="���ڽ��м��㣬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

		fm.action="./PEdorTypePUCount.jsp";
		fm.submit();
  }
  catch(ex)
  {
    alert(ex);
  }
}

//��ѯ���ʼ��MulLine
function afterCount(tFlagStr,tContent)
{
	try { showInfo.close(); } catch (e) {}
		//��ʼ��MulLine
		if (tFlagStr == "Success")
		{
			document.all('IninSuccessFlag').value = "1";
			initPolGrid();
			displayPolGrid();
			if (PolGrid.mulLineCount > 1) //����һ�в���ʾ
			{
				divMultiPol.style.display = ''; 
			}
		    else
		    {
		    	divMultiPol.style.display = 'none'; 
		    }
		    		
			getPolInfo(0); //Ĭ����ʾ��һ�У����գ�
    	}
	    else  //����ʧ��
	    {
	    	divMultiPol.style.display = 'none'; 
	    	document.all('tatolMoney').value = "";
	    	document.all('NewRiskCode').value = "";
	    	document.all('NewRiskName').value = "";
	    	document.all('NewAmnt').value = "";
	    	document.all('FinaleBonus').value = "";
	    	document.all('AddUpBonus').value = "";
	    	document.all('IninSuccessFlag').value = "2";

		    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;  
		    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
    	}
    document.all('PageIsFormating').value = "";
    document.all('SpecialRiskInitFlag').value = "";
}

function displayPolGrid()
{

	if (fm.StrPolGrid.value == null || fm.StrPolGrid.value.trim == "null")
	{
		return;
	}
	turnPage.strQueryResult = fm.StrPolGrid.value;
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid = PolGrid;    
    turnPage.pageIndex = 0;  
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}

function getSelNo()
{
    var tSelNo = PolGrid.getSelNo()-1;
    getPolInfo(tSelNo);
}

function getPolInfo(tSelNo)
{
	document.all('RiskCode').value = PolGrid.getRowColData(tSelNo, 2);
	document.all('RiskName').value = PolGrid.getRowColData(tSelNo, 3);
	
	document.all('PaytoDate').value = PolGrid.getRowColData(tSelNo, 10);
	document.all('Prem').value = PolGrid.getRowColData(tSelNo, 11);
	document.all('Amnt').value = PolGrid.getRowColData(tSelNo, 12);
	document.all('Mult').value = PolGrid.getRowColData(tSelNo, 13);
	
	document.all('AddUpBonus').value = PolGrid.getRowColData(tSelNo, 6);
	document.all('FinaleBonus').value = PolGrid.getRowColData(tSelNo, 7);
	document.all('tatolMoney').value = PolGrid.getRowColData(tSelNo, 8);
	
	if (document.all('SpecialRiskInitFlag').value != "1")//�����������ֵĿ���ֱ�Ӽ�������
	{
		document.all('NewRiskCode').value = PolGrid.getRowColData(tSelNo, 4);
		document.all('NewRiskName').value = PolGrid.getRowColData(tSelNo, 5);
		document.all('NewAmnt').value = PolGrid.getRowColData(tSelNo, 9);
	}
    		
}