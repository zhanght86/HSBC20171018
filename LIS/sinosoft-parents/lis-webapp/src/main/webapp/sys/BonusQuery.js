//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
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


function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	if(tIsCancelPolFlag == "0")
	{
	     /* strSQL = " select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','�ѷ���','0','�Ѽ���'),BONUSCOEF,SGETDATE,"
             +" decode(BONUSGETMODE,'1','�ۼ���Ϣ','2','�ֽ�','3','�ֽ����ڱ���','5','�����'),aGETDATE, "
             +" (case when a.bonusgetmode = '3' and a.BONUSFLAG='1' then a.bonusmoney ELSE 0 END ),"
             +" (case when a.bonusgetmode = '1' and a.BONUSFLAG='1' then (select sum(money) from lcinsureacctrace d where d.insuaccno = '000001'and d.polno = a.polno and paydate<=a.aGETDATE)  ELSE 0 END),"
             +" nvl((select sum(amnt) from lcduty r  where r.polno = a.polno and length(dutycode) = 10 and substr(firstpaydate, 0, 4) = (a.FiscalYear+1)),0),"
             +" (case when a.BONUSFLAG='1' then (select sum(amnt) from lcduty r where r.polno = a.polno) else 0 end) from LOBonusPol a "
	     		 + " Where a.ContNo='" + tContNo + "' and a.PolNo='" + tPolNo + "'"
	     		 + " order by MakeDate asc,MakeTime asc";   */  
	   mySql = new SqlClass();
	mySql.setResourceName("sys.BonusQuerySql");
	mySql.setSqlId("BonusQuerySql1");
	mySql.addSubPara(tContNo);   	
	mySql.addSubPara(tPolNo); 	  				 
	}
	else
	 if(tIsCancelPolFlag == "1")
	 {//�������� 
	    /*  strSQL =" select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','�ѷ���','0','�Ѽ���'),BONUSCOEF,SGETDATE,"
             +" decode(BONUSGETMODE,'1','�ۼ���Ϣ','2','�ֽ�','3','�ֽ����ڱ���','5','�����'),aGETDATE, "
             +" (case when a.bonusgetmode = '3' and a.BONUSFLAG='1' then a.bonusmoney ELSE 0 END ),"
             +" (case when a.bonusgetmode = '1' and a.BONUSFLAG='1' then (select sum(money) from lcinsureacctrace d where d.insuaccno = '000001'and d.polno = a.polno and paydate<=a.aGETDATE)  ELSE 0 END),"
             +" nvl((select sum(amnt) from lcduty r  where r.polno = a.polno and length(dutycode) = 10 and substr(firstpaydate, 0, 4) = (a.FiscalYear+1)),0),"
             +" ((select sum(amnt) from lcduty r where r.polno = a.polno)) from LPBonusPol a "
	     		   + " Where a.ContNo='" + tContNo + "' and a.PolNo='" + tPolNo + "'"
	     		   + " order by MakeDate asc,MakeTime asc";  */   	
	 mySql = new SqlClass();
	mySql.setResourceName("sys.BonusQuerySql");
	mySql.setSqlId("BonusQuerySql2");
	mySql.addSubPara(tContNo);   	
	mySql.addSubPara(tPolNo); 	
	 
	 }
	else
    {
	    alert("�������ʹ������!");
	    return;
	}
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult)
  {
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

// ��ѯ��ť
function QueryClick()
{
	var strSQL="";

	     /* strSQL = " select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','�ѷ���','0','�Ѽ���'),BONUSCOEF,SGETDATE,"
             +" decode(BONUSGETMODE,'1','�ۼ���Ϣ','2','�ֽ�','3','�ֽ����ڱ���','5','�����'),aGETDATE, "
             +" (case when a.bonusgetmode = '3' and a.BONUSFLAG='1' then a.bonusmoney ELSE 0 END )  from LOBonusPol a "
	     		   + " Where 1=1"
	      		+ getWherePart('a.ContNo','ContNo')
	      		+ getWherePart('a.PolNo','PolNo')
	       		+ " order by MakeDate,MakeTime asc";*/
	 mySql = new SqlClass();
	mySql.setResourceName("sys.BonusQuerySql");
	mySql.setSqlId("BonusQuerySql3");
	mySql.addSubPara(fm.ContNo.value);   	
	mySql.addSubPara(fm.PolNo.value); 
	   //��ѯSQL�����ؽ���ַ���
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  		//�ж��Ƿ��ѯ�ɹ�
       if (!turnPage.strQueryResult)
  	   {
  		   PolGrid.clearData();
       	   alert("δ�鵽�ñ������ָ�������Ϣ��");
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
  		arrGrid = arrDataSet;
  		//����MULTILINE������ʾ��ѯ���
  		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}

function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClickSelf();
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