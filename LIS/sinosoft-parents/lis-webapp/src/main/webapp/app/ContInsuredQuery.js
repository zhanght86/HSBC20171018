//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClick();
	top.close();
}
//
//// ���ݷ��ظ�����
//function getQueryDetail()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼��" );
//	else
//	{
//	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
////		parent.VD.gVSwitch.deleteVar("PayNo");				
////		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
//		
//		if (cPayNo == "")
//		    return;
//		    
//		  var cIncomeType = PolGrid.getRowColData(tSel - 1,3);
//		  var cSumActuPayMoney = 	PolGrid.getRowColData(tSel - 1,4);
//		  //alert(cSumActuPayMoney);
//		  if (cIncomeType==0||cIncomeType==1||cIncomeType==2) {
//		  //var urlstr1="../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo+ "&SumActuPayMoney=" + cSumActuPayMoney;
//		  //alert(urlstr1); 
//				window.open("../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
//			}	
//			else {
//				//var urlstr1="../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo+ "&SumActuPayMoney=" + cSumActuPayMoney;
//		  	//alert(urlstr1); 
//				window.open("../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
//							
//			}
//	}
//}



function easyQueryClick()
{
//	alert(1);
	// ��ʼ�����
	initPolGrid();
	//alert(tPrtNo);
	// ��дSQL���
	var strSQL = "";
	
		 var sqlid1="ContInsuredQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tPrtNo);//ָ������Ĳ���
	    strSQL=mySql1.getString();	
	
//	strSQL = 
//	" select prtno,"+
//       " insuredno,"+
//       " name,"+
//       " case when sex = '0' then '��' when sex = '1' then 'Ů' else 'δ֪' end,"+
//       " birthday,"+
//       " (select codename"+
//          " from ldcode"+
//         " where codetype = 'idtype'"+
//           " and code = idtype),"+
//       " idno,prtno"+
//  " from lcinsured"+
//  " where prtno='"+tPrtNo+"'"; 
  // alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("���ݿ���û���������������ݣ�");
    //alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}

// �������ѯ
function PremQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = PolGrid.getRowColData(tSel - 1,1);
		
		if (cContNo == ""||cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PremQueryMain.jsp?ContNo=" + cContNo+ "&PolNo=" + cPolNo + "&IsCancelPolFlag=0");										
	}
}

function SpecQuery(){
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
		  var strSQL = "";
			var ContNo = "";
		//	strSQL = "select contno from lccont where proposalcontno = '"+PolGrid.getRowColData(tSel - 1,8)+"'";
		
		var sqlid2="ContInsuredQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(PolGrid.getRowColData(tSel - 1,8));//ָ������Ĳ���
	    strSQL=mySql2.getString();	
		
//			strSQL = "select contno from lccont where prtno = '"+PolGrid.getRowColData(tSel - 1,8)+"'";
			var arrResult=easyExecSql(strSQL);  
	    try{ContNo = arrResult[0][0];}catch(ex){}
	    var PrtNo = PolGrid.getRowColData(tSel - 1,8);				
			var tInsuredNo = PolGrid.getRowColData(tSel - 1,2);	
		if (PrtNo == "" || ContNo == "") return;
			
	 // window.open("../uw/UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&QueryFlag=1");  
	  window.open("../uw/UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&InsuredNo="+tInsuredNo+"&QueryFlag=2");  	
	
  }										
	
	
}
function AddPremQuery(){
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var cInsuredNo = PolGrid.getRowColData(tSel - 1,2);	
	    var	PrtNo = PolGrid.getRowColData(tSel - 1,8);			
		
		if (PrtNo == "" || ContNo == "") return;
			
	  window.open("../uw/UWManuAddMain.jsp?ContNo="+ContNo+"&InsuredNo="+cInsuredNo+"&QueryFlag=1"); 
  }										
	
	
}


//�����ۼ�
function amntAccumulate(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }
	window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,2));
}

//�ѳб���ѯ
function queryProposal(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,2));
}


//δ�б���ѯ
function queryNotProposal(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,2));
}

//���������ѯ
function queryClaim(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,2));
}

//������ȫ��ѯ
function queryEdor(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/EdorQueryMain.jsp?ContNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,9)+"&CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,2));
}

//������֪��ѯ
function queryHealthImpart(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,2));
}


//���ƽ��水ť��������ʾ
function ctrlButtonDisabled(){
	var tContNo=tPrtNo;
	
  var tSQL = "";
  
  		var sqlid3="ContInsuredQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(tPrtNo);//ָ������Ĳ���
	    tSQL=mySql3.getString();	
  
//  tSQL = "select contno from lccont where prtno='"+tPrtNo+"' and conttype='1'";
  var contno = easyExecSql(tSQL);
  
  var arrResult;
  var arrButtonAndSQL = new Array;
  
    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "Button1";
    arrButtonAndSQL[0][1] = "�������ѳб�������ѯ";
//    arrButtonAndSQL[0][2] = "select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('1', '4') and b.insuredno in (select insuredno from lcinsured where prtno = '"+tContNo+"')";

	var sqlid4="ContInsuredQuerySql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	var strSql4=mySql4.getString();
	arrButtonAndSQL[0][2] = strSql4 
    
    arrButtonAndSQL[1] = new Array;
    arrButtonAndSQL[1][0] = "Button2";
    arrButtonAndSQL[1][1] = "������δ�б�������ѯ";
//    arrButtonAndSQL[1][2] = "select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('0','2') and b.insuredno in (select insuredno from lcinsured where prtno = '"+tContNo+"')";

	var sqlid5="ContInsuredQuerySql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tContNo);//ָ������Ĳ���
	var strSql5=mySql5.getString();
	arrButtonAndSQL[1][2] = strSql5 
    
    arrButtonAndSQL[2] = new Array;
    arrButtonAndSQL[2][0] = "Button3";
    arrButtonAndSQL[2][1] = "�����˼�����ȫ��ѯ";
//    arrButtonAndSQL[2][2] = "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno in (select insuredno from lcinsured where prtno='"+tContNo+"'))";

	var sqlid6="ContInsuredQuerySql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(tContNo);//ָ������Ĳ���
	var strSql6=mySql6.getString();
	arrButtonAndSQL[2][2] = strSql6 
    
    arrButtonAndSQL[3] = new Array;
    arrButtonAndSQL[3][0] = "Button4";
    arrButtonAndSQL[3][1] = "�����˼��������ѯ";
//    arrButtonAndSQL[3][2] = "select 1 from llregister a, llcase b where a.rgtno = b.caseno and b.CustomerNo in (select insuredno from lcinsured where prtno='"+tContNo+"') union select 1 from llreport a, llsubreport b, ldperson c where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' and b.CustomerNo in (select insuredno from lcinsured where prtno='"+tContNo+"')";

	var sqlid7="ContInsuredQuerySql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(tContNo);//ָ������Ĳ���
	mySql7.addSubPara(tContNo);//ָ������Ĳ���
	var strSql7=mySql7.getString();
	arrButtonAndSQL[3][2] = strSql7 
    
    arrButtonAndSQL[4] = new Array;
    arrButtonAndSQL[4][0] = "Button5";
    arrButtonAndSQL[4][1] = "�����˱����ۼ�";
    arrButtonAndSQL[4][2] = "";

    arrButtonAndSQL[5] = new Array;
    arrButtonAndSQL[5][0] = "Button6";
    arrButtonAndSQL[5][1] = "�����˽�����֪��ѯ";
//    arrButtonAndSQL[5][2] = " select 1 from lccustomerimpart a where a.contno = '"+tContNo+"' "
//                          + " and a.impartver in ('101','A01','B01','C01','D01') and a.customernotype='1' "
//                          + " and a.customerno in (select insuredno from lcinsured where contno = '"+tContNo+"')";

	var sqlid8="ContInsuredQuerySql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(tContNo);//ָ������Ĳ���
	mySql8.addSubPara(tContNo);//ָ������Ĳ���
	var strSql8=mySql8.getString();
	arrButtonAndSQL[5][2] = strSql8 
    
    arrButtonAndSQL[6] = new Array;
    arrButtonAndSQL[6][0] = "Button7";
    arrButtonAndSQL[6][1] = "�ӷ���Ϣ";
    if(_DBT==_DBO){
//    	 arrButtonAndSQL[6][2] = "select 1 from lcprem where contno in (select contno from lccont where prtno='"+tContNo+"' " +
//    	 		"and conttype='1') and payplancode like '000000%%' and rownum=1 ";
    	 
		var sqlid9="ContInsuredQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(tContNo);//ָ������Ĳ���
		var strSql9=mySql9.getString();
		arrButtonAndSQL[6][2] = strSql9 
    	 
    }else if(_DBT==_DBM)
    {
//    	 arrButtonAndSQL[6][2] = "select 1 from lcprem where contno in (select contno from lccont where prtno='"+tContNo+"' " +
//    	 		"and conttype='1') and payplancode like '000000%%' limit 1 ";
    	 
 		var sqlid10="ContInsuredQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(tContNo);//ָ������Ĳ���
		var strSql10=mySql10.getString();
		arrButtonAndSQL[6][2] = strSql10 
    }

    arrButtonAndSQL[7] = new Array;
    arrButtonAndSQL[7][0] = "Button8";
    arrButtonAndSQL[7][1] = "��Լ��Ϣ";
    //arrButtonAndSQL[7][2] = "select 1 from lcspec where contno in ('"+tContNo+"','"+contno+"') and rownum=1 union select 1 from lccspec where contno in ('"+tContNo+"','"+contno+"') and rownum=1 ";
    if(_DBT==_DBO){
//    	 arrButtonAndSQL[7][2] = "select 1 from lcspec where contno in ('"+tContNo+"','"+contno+"')" +
//    	 		" and rownum=1 union select 1 from lccspec where contno in ('"+tContNo+"','"+contno+"') ";
    	 
  		var sqlid11="ContInsuredQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(tContNo);//ָ������Ĳ���
		mySql11.addSubPara(contno);//ָ������Ĳ���
		mySql11.addSubPara(tContNo);//ָ������Ĳ���
		mySql11.addSubPara(contno);//ָ������Ĳ���
		var strSql11=mySql11.getString();
		arrButtonAndSQL[7][2] = strSql11 
    	 
   }else if(_DBT==_DBM)
   {
//	   arrButtonAndSQL[7][2] = "select 1 from lcspec where contno in ('"+tContNo+"','"+contno+"') limit 1 union select 1 " +
//	   		"from lccspec where contno in ('"+tContNo+"','"+contno+"') ";
	   
 		var sqlid12="ContInsuredQuerySql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.ContInsuredQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(tContNo);//ָ������Ĳ���
		mySql12.addSubPara(contno);//ָ������Ĳ���
		mySql12.addSubPara(tContNo);//ָ������Ĳ���
		mySql12.addSubPara(contno);//ָ������Ĳ���
		var strSql12=mySql12.getString();
		arrButtonAndSQL[7][2] = strSql12 
		
   }
   
  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
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


