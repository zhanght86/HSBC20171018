/*********************************************************************
 *  �������ƣ�InsuredUWInfo.js
 *  �����ܣ��˹��˱���������Ϣҳ�溯��
 *  �������ڣ�2005-01-06 11:10:36
 *  ������  ��HYQ
 *  ����ֵ��  ��
 *  ���¼�¼��  ������    ��������     ����ԭ��/����
 *********************************************************************
 */

var arrResult1 = new Array();
var arrResult2 = new Array();
var arrResult3 = new Array();
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var temp = new Array();

/*********************************************************************
 *  ��ѯ�˱���������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryInsuredInfo()
{
	
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();
	var arrReturn3 = new Array();
//	var tSql1 = "select InsuredNo,name,sex,birthday,NativePlace,RelationToMainInsured,RelationToAppnt,OccupationCode,OccupationType from LCInsured t where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and insuredno ='"+InsuredNo+"'"
//							;
//  alert(tSql1);
	   var mySql=new SqlClass();
		 mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
		 mySql.setSqlId("InsuredUWInfoInputSql1");//ָ��ʹ�õ�Sql��id
		 mySql.addSubPara(ContNo);
		 mySql.addSubPara(InsuredNo);
		 var tSql1 = mySql.getString();
	turnPage1.strQueryResult  = easyQueryVer3(tSql1, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult) {
    alert("��������Ϣ��ѯʧ��!");
    return "";
  }
//  var tSql2 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
//                            + " and ImpartParamName = 'Stature'"
//                            + " and contno='"+ContNo+"'"
//							+ " and customerno ='"+InsuredNo+"'"
//							;
//	alert(tSql2);			
  
   mySql=new SqlClass();
	 mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId("InsuredUWInfoInputSql2");//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(ContNo);
	 mySql.addSubPara(InsuredNo);
	 var tSql2 = mySql.getString();
  
  //�˴� ��ֵ  ʲô��; ������ �������¸�ֵ���˴������ã���
//							document.all('Stature').value = tSql2;
// var tSql3 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
//                            + " and ImpartParamName = 'Avoirdupois'"
//                            + " and contno='"+ContNo+"'"
//							+ " and customerno ='"+InsuredNo+"'"
//							;
// alert(tSql);	
 mySql=new SqlClass();
 mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
 mySql.setSqlId("InsuredUWInfoInputSql3");//ָ��ʹ�õ�Sql��id
 mySql.addSubPara(ContNo);
 mySql.addSubPara(InsuredNo);
 var tSql3 = mySql.getString();					

  //��ѯ�ɹ������ַ��������ض�ά����
  arrResult1 = decodeEasyQueryResult(turnPage1.strQueryResult);	

  document.all('InsuredNo').value = arrResult1[0][0];
  document.all('Name').value = arrResult1[0][1];
  document.all('Sex').value = arrResult1[0][2];
  quickGetName('Sex',fm.Sex,fm.SexName);
  document.all('InsuredAppAge').value = calAge(arrResult1[0][3]);
  document.all('NativePlace').value = arrResult1[0][4];
  quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);
  document.all('TheNativePlace').value = arrResult1[0][4];
  document.all('RelationToMainInsured').value = arrResult1[0][5];
  quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
  document.all('RelationToAppnt').value = arrResult1[0][6];
  quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
  document.all('TheRelationToAppnt').value = arrResult1[0][6];
  document.all('OccupationCode').value = arrResult1[0][7];
  quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
  document.all('OccupationType').value = arrResult1[0][8];
  quickGetName('OccupationType',fm.OccupationType,fm.OccupationTypeName);
  
  turnPage2.strQueryResult  = easyQueryVer3(tSql2, 1, 0, 1);  
  arrResult2 = decodeEasyQueryResult(turnPage2.strQueryResult);	
 
   if (!turnPage2.strQueryResult)
   { 
     
   	 document.all('Stature').value = ' ';
   }
   else
   {
  document.all('Stature').value = arrResult2[0][0];
}

  turnPage3.strQueryResult  = easyQueryVer3(tSql3, 1, 0, 1);  
  arrResult3 = decodeEasyQueryResult(turnPage3.strQueryResult);
  if(!turnPage3.strQueryResult)
  {
  	document.all('Avoirdupois').value = ' ';
}
else
{
  document.all('Avoirdupois').value = arrResult3[0][0];
}

  if(turnPage2.strQueryResult)
  {
  	document.all("BMI").value = arrResult3[0][0]/((arrResult2[0][0]/100)*(arrResult2[0][0]/100));
  }
	else
	{
		document.all("BMI").value = "0";
	}
	
	
  if(arrResult1[0][5]=="00")
  {  	    
  	 MainInsured.style.display = "";
  	 MainInsuredInput.style.display = "";
  	 
  }
  else
	{
	
  	 MainAppnt.style.display = "";
  	 MainAppntInput.style.display = "";
	}
	
  //��ѯ�����˵���ߡ����أ�������ʾ
  
//  var strSql = "select case impartparamname"
//         + " when 'stature' then"
//         + " concat(impartparam , 'cm')"
//         + " when 'avoirdupois' then"
//         + " concat(impartparam , 'kg')"
//         + " end"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and customernotype = '1'"
//         + " and impartcode = '000'"
//         + " and impartver = '02'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+ContNo+"'"
//         + " and customerno='"+InsuredNo+"'"
//         + " order by impartparamno ";
  
  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql4");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(ContNo);
  mySql.addSubPara(InsuredNo);
  var strSql = mySql.getString();		
  var arr=easyExecSql(strSql);
	if(arr!=null){
		document.all('Stature').value=arr[0][0];
		document.all('Weight').value=arr[1][0];
	}
	
	
  return true;
 
}

/*********************************************************************
 *  ��ѯ�˱�������������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryRiskInfo()
{
	var tSql = "";
//  tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and insuredno ='"+InsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode"
//							;
 var mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql5");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(ContNo);
  mySql.addSubPara(InsuredNo);
  tSql = mySql.getString();							
						
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	if(!turnPage.strQueryResult)
	{
	
//		var aSql = " select * from LCInsuredRelated where 1=1 "
//		          + " and polno in (select polno from lcpol where contno = '"+ContNo+"')";
		  mySql=new SqlClass();
		  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
		  mySql.setSqlId("InsuredUWInfoInputSql6");//ָ��ʹ�õ�Sql��id
		  mySql.addSubPara(ContNo);
		  var aSql = mySql.getString();		
	
		 turnPage1.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1); 
		 if(!turnPage1.strQueryResult)
		 {
		 
//		 	 tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
//							+ " and contno='"+ContNo+"'"
//							+ " and standbyflag1 ='"+InsuredNo+"'"
//							+ " and lcpol.riskcode = lmrisk.riskcode"
//							;
			  mySql=new SqlClass();
			  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
			  mySql.setSqlId("InsuredUWInfoInputSql7");//ָ��ʹ�õ�Sql��id
			  mySql.addSubPara(ContNo);
			  mySql.addSubPara(InsuredNo);
			  tSql= mySql.getString();					
		}
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);      
	}
	

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��������Ϣ��ѯʧ��!");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = RiskGrid ;    

  //����SQL���
  turnPage.strQuerySql     = tSql ; 

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  if(arrDataSet.length!=0)
  {
	  for(i=0;i<arrDataSet.length;i++)
	  {
	  	 temp[i] = arrDataSet[i][2];
	  }
  }
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;  					
}

/*********************************************************************
 *  ������ϲ�ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showHealthQ()
{

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

  
  var cContNo = fm.ContNo.value;

  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  
  if (cContNo!= ""  )
  {     
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo+"&Flag=2","window1");
  	showInfo.close();
  	
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}       

/*********************************************************************
 *  ������鱨���ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function RReportQuery()
{
  cContNo = fm.ContNo.value;  //��������
  var cPrtNo = fm.PrtNo.value;

  //var tSel = PolAddGrid.getSelNo();
  //if (tSel == 0 || tSel == null)
  //{
  // 	alert("����ѡ�񱣵�!");  	
	//return;
  //}
  //var cNo = PolAddGrid.getRowColData(tSel - 1, 1);

  
  if (cContNo != "")
  {			
	window.open("./RReportQueryMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo);
  }
  else
  {  	
  	alert("����ѡ�񱣵�!");  	
  }	
}

/*********************************************************************
 *  ����Ͷ����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showApp(cindex)
{
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

  var cInsureNo = fm.InsuredNo.value;
  window.open("../uw/UWAppMain.jsp?ContNo="+ContNo+"&CustomerNo="+cInsureNo+"&type=2");
  	showInfo.close();
}         

/*********************************************************************
 *  �������¼��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showHealth()
{
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

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}           

/*********************************************************************
 *  ������鱨��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showRReport()
{
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

  cProposalNo=fm.ContNo.value;
  alert(cProposalNo);
  var cMissionID =fm.MissionID.value; 
  var cSubMissionID =fm.SubMissionID.value; 
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);	
  if (cProposalNo != ""  && cMissionID != "")
  {
  	window.open("./UWManuRReportMain.jsp?ContNo="+cProposalNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window1");  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}         
   
/*********************************************************************
 *  ��ʾ�Ѿ�¼��ļ�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDisDesb()
{
	for(i=0;i<temp.length;i++)
	{
//	var tSql = "select t.disresult,t.ICDCode,t.DisDesb,a.RiskGrade,a.ObservedTime,a.State,a.uwresult from LCDiseaseResult t,LDUWAddFee a where 1=1"
//							+ " and t.contno='"+ContNo+"'"
//							+ " and t.customerno = '"+InsuredNo+"'"
//							+ " and t.ICDCode = a.ICDCode order by t.disresult";
	  var mySql=new SqlClass();
	  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql.setSqlId("InsuredUWInfoInputSql8");//ָ��ʹ�õ�Sql��id
	  mySql.addSubPara(ContNo);
	  mySql.addSubPara(InsuredNo);
	  var tSql= mySql.getString();	
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  if(i==0)
  {
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  }
 else
 	 {
 	 	 
 		 l = turnPage.arrDataCacheSet[0].length;
 		 for(j=0;j<decodeEasyQueryResult(turnPage.strQueryResult).length;j++)
 		 {  
 		 	  turnPage.arrDataCacheSet[j][l] = decodeEasyQueryResult(turnPage.strQueryResult)[j][6];
 		 }
 	 }
  }
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = DiseaseGrid ;    

  //����SQL���
  turnPage.strQuerySql     = tSql ; 

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;  			
}

/*********************************************************************
 *  ���������ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function ClaimGetQuery()
{
	  var cInsuredNo = fm.InsuredNo.value;				
		if (cInsuredNo == "")
		    return;
		window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo);										

}

/*********************************************************************
 *  �ӷѳб�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function showAdd()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var cInsuredNo = fm.InsuredNo.value;
 
	window.open("./UWManuAddMain.jsp?ContNo="+ContNo+"&InsuredNo="+cInsuredNo,"window1"); 

}

/*********************************************************************
 *  ��Լ�б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function showSpec()
{
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

  if (ContNo != ""&& PrtNo !="" && MissionID != "" )
  { 	
  	window.open("./UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1");  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("���ݴ������!");
  }
}

/*********************************************************************
 *  ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function InitClick(){
	window.close();
}

/*********************************************************************
 *  �ύ�����水ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function submitForm(FlagStr)
{
	if(FlagStr == 1)
	{
		var tSelNo = RiskGrid.getSelNo();
 		if(tSelNo<=0)
 		{
 			alert("��ѡ�����ֱ�����");
 			return;
 		}
 		var polno = RiskGrid.getRowColData(tSelNo - 1,1);
 		var mainpolno = RiskGrid.getRowColData(tSelNo - 1,2);
 		
 		//alert(polno+"------------"+mainpolno);
 	   //tongmeng 2007-10-30 modify
 	   //����Ҳ���Գ���	
 //		if(polno==mainpolno)
 //	   {
// 			if(fm.uwstate.value=='a')
// 			 {
 //			 	alert("���ձ��������³������ۣ�");
// 			 	return;
// 			 	}
// 	   }
 	  if(document.all('uwstate').value=='d')
 	   {
 	  	if(document.all('amnt').value==''||document.all('amnt').value==null)
 	  	 {
 	  		alert("��¼���޶�");
 	  		return;
 	  	 }
 	   }

  	document.all('PolNo').value = RiskGrid.getRowColData(tSelNo - 1,1);	
  	document.all('flag').value = "risk";
	}
	if(FlagStr == 2)
	{
		document.all('flag').value = "Insured";
	}
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
 	 
  fm.action = "./InsuredUWInfoChk.jsp";
  fm.submit(); //�ύ
}

/*********************************************************************
 *  �ύ�����,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function afterSubmit( FlagStr, content )
{
	var flag = document.all('flag').value;
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      

    	showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";
  	showInfo.close();
  	alert(showStr);
  	if(flag=="Insured")
    {
  		parent.close();
  	}
    else
  	{
  		showInsuredResult();
  	}
    //ִ����һ������
  }
}

/*********************************************************************
 *  ȡ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelchk()
{
	document.all('uwstate').value = "";
	document.all('UWIdea').value = "";
}

/*********************************************************************
 *  ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function InitClick(){
	parent.close();
}

/*********************************************************************
 *  ��ͣ�����ˣ����̨�ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function StopInsured()
{
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

	fm.flag.value = "StopInsured";
	
	fm.action = "./InsuredUWInfoChk.jsp";
  fm.submit(); //�ύ
}


/*********************************************************************
 *  ��ʼ���ϱ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initSendTrace()
{
	if(SendFlag =="1")
	{
		//divUWButton1.style.display = "none";
//		divUWButton2.style.display = "none";
	}
}

/*********************************************************************
 *  ����˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */ 
function calRiskResult(arrSelected)
{
	var tResult="S";
	var arrSelected =new Array();
	var dFlag = 0 ;
	var pFlag = 0 ;
	var lFlag = 0 ;
	var eFlag = 0 ;
	var rFlag = 0 ;

	for(i=0;i<arrSelected.length;i++)
	{
		if(arrSelected[i]=="D")
		{
			dFlag = 1;
		}
		if(arrSelected[i]=="P")
		{
			pFlag = 1;
		}
		if(arrSelected[i]=="R")
		{
			rFlag = 1;
		}		
		if(arrSelected[i]=="E")
		{
			eFlag = 1;
		}			
		if(arrSelected[i]=="L")
		{
			lFlag = 1;
		}	
	}
	if(dFlag == 1)
	{
		tResult="D";
	}
  else
  {
  	if(pFlag == 1)
  	{
  		tResult="P";
  	}
  	else
  	{
  		if(rFlag == 1)
  		{
  			tResult="R";
  		}
  		else
  		{
  			  	if(eFlag == 1)
  					{
  						tResult="E";
  					}
  					else
  					{
  						  	if(lFlag == 1)
  								{
  									tResult="L";
  								}
  								else
  								{
  									tResult="S";
  								}
  					} 
  		}  		  
  	}         
  }
  	
  return tResult;     
}             
              
/*********************************************************************
 *  �ó������˽������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */              
function showInsuredResult()
{
	var arrSelected = new Array();
	var arrResult = new Array();

//	var tSql = "select sugpassflag from lcuwmaster where 1=1 "
//						+" and contno ='"+ContNo+"'"
//						;
	  var mySql=new SqlClass();
	  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql.setSqlId("InsuredUWInfoInputSql9");//ָ��ʹ�õ�Sql��id
	  mySql.addSubPara(ContNo);
	  var tSql= mySql.getString();	
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);

	//�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) { 
		for(j=0;j<arrResult.length;j++)
		{
			arrSelected[j] = arrResult[j][0];
		}
		//fm.SugIndUWFlag.value = calRiskResult(arrSelected);
	}	
}        

//��ѯ�����
function queryHealthReportResult(){
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

  var cContNo=fm.ContNo.value;
  var cInsuredNo = fm.InsuredNo.value;
 
 
	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1");
 
showInfo.close();	
}


//��ѯ�������
function queryRReportResult(){
  alert();
}

//�����ۼ�
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}


//�ѳб���ѯ
function queryProposal(){
	
	window.open("./ProposalQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}


//δ�б���ѯ
function queryNotProposal(){
	
	window.open("./NotProposalQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//���������ѯ
function queryClaim(){
	
	window.open("./ClaimQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//������ȫ��ѯ
function queryEdor(){
	
	window.open("./EdorQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}


//��ѯ���������ֺ˱�����
function showRiskResult(){
	//alert("here!");
  var arrResult = new Array();
  //alert(RiskGrid.getSelNo());
//  var aSQL = "select passflag,UWIdea from lcuwmaster where ProposalNo='"+RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1)+"'";
  var mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql10");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1));
  var aSQL= mySql.getString();	
  //alert(aSQL);
  arrResult = easyExecSql(aSQL);
	if(arrResult!=null){
		document.all('uwstate').value=arrResult[0][0];
		document.all('UWIdea').value=arrResult[0][1];
		return;
	}
	return;
}

//������֪��ѯ
function queryHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}  


var cacheWin=null;

function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObjEx(strCode,showObjc,showObjn);
}


//�����˹��˱����水ť��������ʾ
function ctrlButtonDisabled(tContNo){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;
  
  arrButtonAndSQL[0] = new Array;
  arrButtonAndSQL[0][0] = "Button1";
  arrButtonAndSQL[0][1] = "�����˽�����֪��ѯ";
//  if(_DBT==_DBO){
//	  arrButtonAndSQL[0][2] = "select * from lccustomerimpart a where a.contno = '"+tContNo+"' and a.impartver = '02' and a.impartcode<>'000' and a.customernotype='1' and a.customerno in (select insuredno from lcinsured where contno = '"+tContNo+"') and rownum=1";
//  } else if(_DBT==_DBM){
//	  arrButtonAndSQL[0][2] = "select * from lccustomerimpart a where a.contno = '"+tContNo+"' and a.impartver = '02' and a.impartcode<>'000' and a.customernotype='1' and a.customerno in (select insuredno from lcinsured where contno = '"+tContNo+"') limit 1";
//  }
  var mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql11");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(tContNo);
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[0][2]= mySql.getString();	
  arrButtonAndSQL[1] = new Array;
  arrButtonAndSQL[1][0] = "Button2";
  arrButtonAndSQL[1][1] = "������������ϲ�ѯ";
//  if(_DBT==_DBO){
//	  arrButtonAndSQL[1][2] = "select * from lcpenotice where customerno in (select insuredno from lcinsured where contno='"+tContNo+"') and rownum=1";
//  } else if(_DBT==_DBM){
//	  arrButtonAndSQL[1][2] = "select * from lcpenotice where customerno in (select insuredno from lcinsured where contno='"+tContNo+"') limit 1";
//  }
  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql12");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[1][2]= mySql.getString();	
  
  arrButtonAndSQL[2] = new Array;
  arrButtonAndSQL[2][0] = "Button3";
  arrButtonAndSQL[2][1] = "�����˱����ۼ���ʾ��Ϣ";
  arrButtonAndSQL[2][2] = "";

  arrButtonAndSQL[3] = new Array;
  arrButtonAndSQL[3][0] = "Button4";
  arrButtonAndSQL[3][1] = "�������ѳб�������ѯ";
//  arrButtonAndSQL[3][2] = "select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('1', '4') and a.salechnl in ('1', '3', '01', '03') and b.insuredno in (select insuredno from lcinsured where contno = '"+tContNo+"')";

  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql13");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[3][2]= mySql.getString();	
  
  arrButtonAndSQL[4] = new Array;
  arrButtonAndSQL[4][0] = "Button5";
  arrButtonAndSQL[4][1] = "������δ�б�Ͷ������ѯ";
  arrButtonAndSQL[4][2] = "";

  arrButtonAndSQL[5] = new Array;
  arrButtonAndSQL[5][0] = "Button6";
  arrButtonAndSQL[5][1] = "�����˼�����ȫ��Ϣ��ѯ";
//  arrButtonAndSQL[5][2] = "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno in (select insuredno from lcinsured where contno='"+tContNo+"'))";

  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql14");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[5][2]= mySql.getString();	
  
  arrButtonAndSQL[6] = new Array;
  arrButtonAndSQL[6][0] = "Button7";
  arrButtonAndSQL[6][1] = "�����˼���������Ϣ��ѯ";
//  arrButtonAndSQL[6][2] = "select 1 from llregister a, llcase b where a.rgtno = b.caseno and b.CustomerNo in (select insuredno from lcinsured where contno='"+tContNo+"') union select 1 from llreport a, llsubreport b, ldperson c where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' and b.CustomerNo in (select insuredno from lcinsured where contno='"+tContNo+"')";

  mySql=new SqlClass();
  mySql.setResourceName("uw.InsuredUWInfoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId("InsuredUWInfoInputSql15");//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(tContNo);
  arrButtonAndSQL[6][2]= mySql.getString();	
  
  arrButtonAndSQL[7] = new Array;
  arrButtonAndSQL[7][0] = "Button8";
  arrButtonAndSQL[7][1] = "��Լ�б�¼��";
  arrButtonAndSQL[7][2] = "";

  arrButtonAndSQL[8] = new Array;
  arrButtonAndSQL[8][0] = "AddFee";
  arrButtonAndSQL[8][1] = "�ӷѳб�¼��";
  arrButtonAndSQL[8][2] = "";


/**
  //Ӱ�����ϲ�ѯ
  tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
  arrResult = easyExecSql(tSQL);
  if(arrResult!=null){
    document.all("uwButton4").disabled="";	
  }
  else{
    document.all("uwButton4").disabled="true";	
  }
  
  //�������Ϣ��ѯ
  tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
  arrResult = easyExecSql(tSQL);
  if(arrResult!=null){
    document.all("uwButton4").disabled="";	
  }
  else{
    document.all("uwButton4").disabled="true";	
  }
  **/
  
  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
    	//alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
      }
    }
    else{
      continue;
    }	
  }
    

  
  	
}


function afterCodeSelect( cCodeName, Field )
{
 
	var tCode = document.all('uwstate').value;
	
	if(tCode=="d")
	{
		divamnt.style.display = '';
		queryamnt();

	}
else
	{
		divamnt.style.display = 'none';
		document.all('amnt').value = "";

	}

}

function queryamnt()
{
	var tSQL = "";
	
	}



