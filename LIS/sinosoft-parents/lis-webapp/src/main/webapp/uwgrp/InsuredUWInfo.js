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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.InsuredUWInfoSql";
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
	/*
	var tSql1 = "select InsuredNo,name,sex,birthday,NativePlace,RelationToMainInsured,RelationToAppnt,OccupationCode,OccupationType from LCInsured t where 1=1"
							+ " and contno='"+ContNo+"'"
							+ " and insuredno ='"+InsuredNo+"'"
							;
							
							
	*/						
		var sqlid1="InsuredUWInfoSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ContNo);
			mySql1.addSubPara(InsuredNo);
//  alert(tSql1);
	turnPage1.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult) {
    alert("��������Ϣ��ѯʧ��!");
    return "";
  }
  /*
  var tSql2 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
                            + " and ImpartParamName = 'Stature'"
                            + " and contno='"+ContNo+"'"
							+ " and customerno ='"+InsuredNo+"'"
							;
		*/					
		var sqlid2="InsuredUWInfoSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(ContNo);
		mySql2.addSubPara(InsuredNo);
		var tSql2 =	mySql2.getString();			
//	alert(tSql2);					
							fm.all('Stature').value = tSql2;
 /*
 var tSql3 = "select ImpartParam from LCCustomerImpartParams where 1=1 "
                            + " and ImpartParamName = 'Avoirdupois'"
                            + " and contno='"+ContNo+"'"
							+ " and customerno ='"+InsuredNo+"'"
							;
	*/						
		var sqlid3="InsuredUWInfoSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(ContNo);
		mySql3.addSubPara(InsuredNo);
		var tSql3 =	mySql3.getString();	
// alert(tSql);	
							

  //��ѯ�ɹ������ַ��������ض�ά����
  arrResult1 = decodeEasyQueryResult(turnPage1.strQueryResult);	

  fm.all('InsuredNo').value = arrResult1[0][0];
  fm.all('Name').value = arrResult1[0][1];
  fm.all('Sex').value = arrResult1[0][2];
  fm.all('InsuredAppAge').value = calAge(arrResult1[0][3]);
  fm.all('NativePlace').value = arrResult1[0][4];
  fm.all('TheNativePlace').value = arrResult1[0][4];
  fm.all('RelationToMainInsured').value = arrResult1[0][5];
  fm.all('RelationToAppnt').value = arrResult1[0][6];
  fm.all('TheRelationToAppnt').value = arrResult1[0][6];
  fm.all('OccupationCode').value = arrResult1[0][7];
  fm.all('OccupationType').value = arrResult1[0][8];
  
  turnPage2.strQueryResult  = easyQueryVer3(tSql2, 1, 0, 1);  
  arrResult2 = decodeEasyQueryResult(turnPage2.strQueryResult);	
 
   if (!turnPage2.strQueryResult)
   { 
     
   	 fm.all('Stature').value = ' ';
   }
   else
   {
  fm.all('Stature').value = arrResult2[0][0];
}

  turnPage3.strQueryResult  = easyQueryVer3(tSql3, 1, 0, 1);  
  arrResult3 = decodeEasyQueryResult(turnPage3.strQueryResult);
  if(!turnPage3.strQueryResult)
  {
  	fm.all('Avoirdupois').value = ' ';
}
else
{
  fm.all('Avoirdupois').value = arrResult3[0][0];
}

  if(turnPage2.strQueryResult)
  {
  	fm.all("BMI").value = arrResult3[0][0]/((arrResult2[0][0]/100)*(arrResult2[0][0]/100));
  }
	else
	{
		fm.all("BMI").value = "0";
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
	/*
  tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
							+ " and contno='"+ContNo+"'"
							+ " and insuredno ='"+InsuredNo+"'"
							+ " and lcpol.riskcode = lmrisk.riskcode"
							;
							*/
		var sqlid4="InsuredUWInfoSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(ContNo);
		mySql4.addSubPara(InsuredNo);					
						
	turnPage.strQueryResult  = easyQueryVer3(mySql4.getString(), 1, 0, 1);  
	if(!turnPage.strQueryResult)
	{
	/*
		var aSql = " select * from LCInsuredRelated where 1=1 "
		          + " and polno in (select polno from lcpol where contno = '"+ContNo+"')";
	*/	
	
	var sqlid5="InsuredUWInfoSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(ContNo);

	
		 turnPage1.strQueryResult  = easyQueryVer3(mySql5.getString(), 1, 0, 1); 
		 if(!turnPage1.strQueryResult)
		 {
		 /*
		 	 tSql = "select lcpol.polno,lcpol.MainPolNo,lcpol.riskcode,lmrisk.riskname,lcpol.Prem,lcpol.Amnt,lcpol.CValiDate,lcpol.EndDate,lcpol.PayIntv,lcpol.PayYears from LCPol,lmrisk where 1=1"
							+ " and contno='"+ContNo+"'"
							+ " and standbyflag1 ='"+InsuredNo+"'"
							+ " and lcpol.riskcode = lmrisk.riskcode"
							;
							*/
			var sqlid6="InsuredUWInfoSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(ContNo);	
		mySql6.addSubPara(InsuredNo);	
		tSql =mySql6.getString();
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
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  
  var cContNo = fm.ContNo.value;

  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  
  if (cContNo!= ""  )
  {     
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
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

  //var tSel = PolAddGrid.getSelNo();
  //if (tSel == 0 || tSel == null)
  //{
  // 	alert("����ѡ�񱣵�!");  	
	//return;
  //}
  //var cNo = PolAddGrid.getRowColData(tSel - 1, 1);

  
  if (cContNo != "")
  {			
	window.open("./RReportQueryMain.jsp?ContNo="+cContNo,"",sFeatures);
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
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cInsureNo = fm.InsuredNo.value;
  window.open("../uwgrp/UWAppMain.jsp?ContNo="+ContNo+"&CustomerNo="+cInsureNo+"&type=2","",sFeatures);
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
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
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
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ContNo.value;
  alert(cProposalNo);
  var cMissionID =fm.MissionID.value; 
  var cSubMissionID =fm.SubMissionID.value; 
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);	
  if (cProposalNo != ""  && cMissionID != "")
  {
  	window.open("./UWManuRReportMain.jsp?ContNo="+cProposalNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window1",sFeatures);  	
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
	/*
	var tSql = "select t.disresult,t.ICDCode,t.DisDesb,a.RiskGrade,a.ObservedTime,a.State,a.uwresult from LCDiseaseResult t,LDUWAddFee a where 1=1"
							+ " and t.contno='"+ContNo+"'"
							+ " and t.customerno = '"+InsuredNo+"'"
							+ " and t.ICDCode = a.ICDCode order by t.disresult";
*/
var sqlid7="InsuredUWInfoSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(ContNo);	
		mySql7.addSubPara(InsuredNo);

	turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);  

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
		window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo,"",sFeatures);										

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
 
	window.open("./UWManuAddMain.jsp?ContNo="+ContNo+"&InsuredNo="+cInsuredNo,"window1",sFeatures); 

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
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  if (ContNo != ""&& PrtNo !="" && MissionID != "" )
  { 	
  	window.open("./UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1",sFeatures);  	
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
  	fm.all('PolNo').value = RiskGrid.getRowColData(tSelNo - 1,1);	
  	fm.all('flag').value = "risk";
	}
	if(FlagStr == 2)
	{
		fm.all('flag').value = "Insured";
	}
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
 	 
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
	var flag = fm.all('flag').value;
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
	fm.all('uwstate').value = "";
	fm.all('UWIdea').value = "";
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
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
/*
	strSQL = "select sugpassflag from lcuwmaster where 1=1 "
						+" and contno ='"+ContNo+"'"
						;
						*/
		var sqlid8="InsuredUWInfoSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(ContNo);	

						
	turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);  
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
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.ContNo.value;
  var cInsuredNo = fm.InsuredNo.value;
 
 
	window.open("../uwgrp/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1","",sFeatures);
 
showInfo.close();	
}


//��ѯ�������
function queryRReportResult(){
  alert();
}

//�����ۼ�
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}


//�ѳб���ѯ
function queryProposal(){
	
	window.open("./ProposalQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}


//δ�б���ѯ
function queryNotProposal(){
	
	window.open("./NotProposalQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}

//���������ѯ
function queryClaim(){
	
	window.open("./ClaimQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}

//������ȫ��ѯ
function queryEdor(){
	
	window.open("./EdorQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}


//��ѯ���������ֺ˱�����
function showRiskResult(){
	//alert("here!");
  var arrResult = new Array();
  //alert(RiskGrid.getSelNo());
 // var aSQL = "select passflag,UWIdea from lcuwmaster where ProposalNo='"+RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1)+"'";
 
 var sqlid9="InsuredUWInfoSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(RiskGrid.getRowColData(RiskGrid.getSelNo()-1,1));	
 
  //alert(aSQL);
  arrResult = easyExecSql(mySql9.getString());
	if(arrResult!=null){
		fm.all('uwstate').value=arrResult[0][0];
		fm.all('UWIdea').value=arrResult[0][1];
		return;
	}
	return;
}

//������֪��ѯ
function queryHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+fm.all('InsuredNo').value,"window1",sFeatures);
}  