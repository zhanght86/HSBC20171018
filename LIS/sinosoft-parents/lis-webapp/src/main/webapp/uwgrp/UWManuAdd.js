//�������ƣ�PEdorUWManuAdd.js
//�����ܣ���ȫ�˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var spanObj;
var sqlresourcename = "uwgrp.UWManuAddSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
	
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("δѡ��ӷѵ�Ͷ����!");
  	return;
  }
  
  if(fm.SubMissionID.value == "")
  {
  	alert("��¼��˱�֪ͨ����Ϣ,��δ��ӡ,������¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
  	return;
  }

  var i = SpecGrid.mulLineCount ; 
  if(i==0)
  {
  	alert("δ¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
  	return;
  }
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
  fm.action= "./UWManuAddChk.jsp";
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
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
  	//alert(showStr);
  	//parent.close();
  
    //ִ����һ������
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
{
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
  	//parent.close();
  
    //ִ����һ������
  }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
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


function manuchkspecmain()
{
	fm.submit();
}

// ��ѯ��ť
function initlist(tPolNo)
{
	// ��дSQL���
	k++;
	var strSQL = "";
	/*
	strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
		+ " and polno = '"+tPolNo+"'";
	*/	
		var sqlid1="UWManuAddSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(k);
		mySql1.addSubPara(k);
		mySql1.addSubPara(tPolNo);
		
    str  = easyQueryVer3(mySql1.getString(), 1, 0, 1); 
    return str;
   
}

//��ѯ�Ѿ��ӷ���Ŀ
function QueryGrid(tPolNo,tPolNo2)
{
	// ��ʼ�����
	//initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
   //��ȡԭ�ӷ���Ϣ
   /*
	strSQL = "select dutycode,payplantype,paystartdate,payenddate,suppriskscore,SecInsuAddPoint,AddFeeDirect,prem from LCPrem where 1=1 "
			 + " and PolNo ='"+tPolNo2+"'"
			 + " and payplancode like '000000%%'"
			 + " and state = '1'";	
			 */		
	var sqlid2="UWManuAddSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tPolNo2);

	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = SpecGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


  //��ȡ�������������
/*	var tMissionID = fm.MissionID.value;	
	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
				 + " and LWMission.ActivityID = '0000000102'"
				 + " and LWMission.ProcessID = '0000000001'"
				 + " and LWMission.MissionID = '" +tMissionID +"'";				 
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    fm.SubMissionID.value = "";
    return ;
  }  */
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  return true;	
}

function QueryPolAddGrid(tContNo,tInsuredNo)
{
	// ��ʼ�����
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
	
   //��ȡԭ������Ϣ
   /*
    strSQL = "select LCPol.PolNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName,LCPol.standprem from LCPol where "				 			
			 + "  ContNo ='"+tContNo+"'"
//			 + " and InsuredNo = '"+tInsuredNo+"'"
			 + "  order by polno";	
			 */		
	var sqlid3="UWManuAddSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);
	turnPage.strQueryResult  = easyQueryVer3(mySql3.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}
	
function getPolGridCho()
{
  var tSelNo = PolAddGrid.getSelNo()-1;
  tRow = tSelNo;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = PolAddGrid.getRowColData(tSelNo,1);
  var tRiskCode = PolAddGrid.getRowColData(tSelNo,4);
  fm.PolNo.value = cPolNo
  fm.PolNo2.value = cPolNo2 ;
  fm.RiskCode.value = tRiskCode;
 
  if(cPolNo != null && cPolNo != "" )
  {
  	str = initlist(cPolNo2);
    initUWSpecGrid(str);
    QueryGrid(cPolNo,cPolNo2);	
    QueryAddReason(cPolNo);
    fm.all('spanSpecGrid').all('SpecGridSel').checked="true";

   // initAddObj();
  }	
}


//��ѯ�Ѿ�¼��ӷ���Լԭ��
function QueryAddReason(tPolNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
	/*
	strSQL = "select addpremreason from LCUWMaster where 1=1 "
			 + " and polno = '"+tPolNo+"'"
			 */
	var sqlid4="UWManuAddSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tPolNo);
		
	turnPage.strQueryResult  = easyQueryVer3(mySql4.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	fm.AddReason.value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.AddReason.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

//��ʼ���ӷѶ���
function initAddObj(span)
{
	spanObj = span;
	var tSelNo = PolAddGrid.getSelNo()-1; 
  var tRiskCode = PolAddGrid.getRowColData(tSelNo,4);
	var tDutyCode = fm.all( spanObj ).all( 'SpecGrid1' ).value;
	var tAddFeeType = fm.all(spanObj).all('SpecGrid2').value;
	
  if(tAddFeeType = '01')
  {/*
	var srtSql = "select AddFeeObject from LMDutyPayAddFee where 1=1 "
	           + " and riskcode = '"+tRiskCode+"'"
	           + " and DutyCode = '"+tDutyCode+"'"
	           + " and AddFeeType = '01'";
	*/
	
	var sqlid5="UWManuAddSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tRiskCode);
		mySql5.addSubPara(tDutyCode);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql5.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	fm.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.all( spanObj ).all( 'SpecGrid1' ).value = turnPage.arrDataCacheSet[0][0];  

}
else
	{
		fm.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
	}
    return true;	
	
}
	


/*********************************************************************
 *  Click�¼�����˫�����ӷѽ�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showUWSpec( span)
{
	spanObj = span;
	
	// ��дSQL���
	var strSQL = "";
	var tRiskCode="";
	var tInsuredSex="";
	var tInsuredAppAge="";
	var tSuppRiskCore="";
	var tAddFeeKind="";
	var tPayEndYear="";
    //У��¼����Ϣ��������
	if(fm.all( spanObj ).all( 'SpecGrid1' ).value == ""){
		alert("��¼��ӷ�������Ϣ��");
		return;
	}
	
	if(fm.all( spanObj ).all( 'SpecGrid2' ).value == ""){
		alert("��¼��ӷ�ԭ����Ϣ��");
		return;
	}
	else
	tAddFeeKind=fm.all( spanObj ).all( 'SpecGrid2' ).value;
	
 	if(fm.all( spanObj ).all( 'SpecGrid5' ).value==""){
		alert("��¼��ӷ�������Ϣ��");
		return;
	}
	else
	tSuppRiskCore=fm.all( spanObj ).all( 'SpecGrid5' ).value;
	
	
//�˴���ְҵ�ӷѴ���û��ȷ����
 alert("tAddFeeKind:"+tAddFeeKind)
   //alert("riskcode:"+riskcode)
  // alert("polno:"+polno)
   alert("tSuppRiskCore:"+tSuppRiskCore)
   if(tAddFeeKind=="1"||tAddFeeKind=="3")
   {
 
   	//׼���ӷ�����Ҫ��	
   //alert(PolAddGrid.getRowColData(tRow,1));
   
  
	//strSQL = "select AddFeeAMNT("+tAddFeeKind+",riskcode,polno,"+tSuppRiskCore+") from LCpol where polno='"+PolAddGrid.getRowColData(tRow,1)+"'";	
	
	var sqlid6="UWManuAddSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tAddFeeKind);
		mySql6.addSubPara(tSuppRiskCore);
		mySql6.addSubPara(PolAddGrid.getRowColData(tRow,1));
	
	turnPage.strQueryResult  = easyQueryVer3(mySql6.getString(), 1, 0, 1);   

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    {
  	alert("�����ӷ��������ʧ�ܣ�");
    return "";
    }
   
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.all( spanObj ).all( 'SpecGrid6' ).value = turnPage.arrDataCacheSet[0][0];	
    return true;	
   }
   else
  	{
    alert("δ����ְҵ�ӷ����㣡");
    return "";
    }
   	
}    

function CalHealthAddFee(span)
{
	spanObj = span;
	
  var tSelNo = PolAddGrid.getSelNo()-1; 
  var tRiskCode = PolAddGrid.getRowColData(tSelNo,4);
  var tPolNo = PolAddGrid.getRowColData(tSelNo,1);
  var tMainPolNo = PolAddGrid.getRowColData(tSelNo,2);
  //var tPrem = PolAddGrid.getRowColData(tSelNo,8);
  //��ѯ�������µı�׼����
  /*
  var tSql = " select sum(standprem) from lcprem where 1 = 1 "
           + " and trim(polno) = '"+tPolNo+"' and trim(dutycode)='"+fm.all( spanObj ).all( 'SpecGrid1' ).value+"' "
           + " and trim(payplancode) in (select trim(payplancode) from lmdutypayrela where dutycode='"+fm.all( spanObj ).all( 'SpecGrid1' ).value+"')";
   */  
     
     var sqlid7="UWManuAddSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tPolNo);
		mySql7.addSubPara(fm.all( spanObj ).all( 'SpecGrid1' ).value);
		mySql7.addSubPara(fm.all( spanObj ).all( 'SpecGrid1' ).value);
           
  turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);   
  
  if (!turnPage.strQueryResult)
   {
    alert("��ѯ�������µı�׼���ѽ��ʧ��")
    return ;
    }
    
  else
  	{
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
    var tPrem = turnPage.arrDataCacheSet[0][0];          
  	}
  
  var tSuppRiskScore = fm.all( spanObj ).all( 'SpecGrid5' ).value;
  //����������Ǹ����գ��жϸø�������û�мӷ��㷨��
  /*
  var tSql = " select * from LMDutyPayAddFee where 1=1 "
           + " and riskcode = '"+tRiskCode+"'"
           + " and dutycode = '"+fm.all( spanObj ).all( 'SpecGrid1' ).value+"'";
   */    
       var sqlid8="UWManuAddSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(tRiskCode);
		mySql8.addSubPara(fm.all( spanObj ).all( 'SpecGrid1' ).value);

       
           
 turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);   
 
        
  if((tPolNo != tMainPolNo) && !(turnPage.strQueryResult))
  {  	
  	/*
  	var tSql = " select addpoint from lduwuser where 1 = 1 "
  	         + " and usercode='"+tOperator+"' and uwtype='1'";
  	 */        
  	          var sqlid9="UWManuAddSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(tOperator);

  	         
   turnPage.strQueryResult = easyQueryVer3(mySql9.getString(), 1, 1, 1);    
  //�ж��Ƿ��ѯ�ɹ�
 
  if (!turnPage.strQueryResult)
   {
    alert("��ѯ�����ռӷ�����Ȩ��������")
    return ;
    }  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
    var RiskScore = turnPage.arrDataCacheSet[0][0];          
   
    if(parseFloat(tSuppRiskScore) > parseFloat(RiskScore) )
    {
    	alert("�ӷ�������󣬳����ú˱��ǵĺ˱�Ȩ��")
    	return;
    }
  else
  	{
  		
  	var tAddPrem = tSuppRiskScore/100*tPrem;
  	fm.all( spanObj ).all( 'SpecGrid8' ).value = tAddPrem;  	
    }
  	
  }
 
else
	{
	fm.all('DutyCode').value = fm.all( spanObj ).all( 'SpecGrid1' ).value;
	fm.all('AddFeeType').value = fm.all( spanObj ).all( 'SpecGrid2' ).value;
  fm.all('SuppRiskScore').value = fm.all( spanObj ).all( 'SpecGrid5' ).value;
	fm.all('SecondScore').value = fm.all( spanObj ).all( 'SpecGrid6' ).value;
	fm.all('AddFeeObject').value = fm.all( spanObj ).all( 'SpecGrid7' ).value;
	 
	var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
  

  fm.action= "./UWCalHealthAddFeeChk.jsp";
  fm.submit(); //�ύ
}
}