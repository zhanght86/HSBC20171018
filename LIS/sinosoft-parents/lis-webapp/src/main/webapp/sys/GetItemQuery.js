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
	//alert("here");
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	if(tIsCancelPolFlag == "0")
	{
	    /*  strSQL = " select a.GrpContNo,a.DutyCode,a.GetDutyCode, b.GetDutyName,a.InsuredNo,"
	       //+ " a.GetIntv,"
	       + " (select c.CodeName from LDCode c where c.codetype ='getintv' and trim(c.code) = trim(a.GetIntv)),"
	       + " a.GetMode,"
	       //+ " (select c.CodeName from LDCode c  where c.codetype ='livegetmode' and trim(c.code) = trim(a.GetMode)),"
	       + " a.GetLimit,a.GetRate,"
	       //+ " a.UrgeGetFlag,"
	       + " decode(a.UrgeGetFlag,'Y','���߸�','N','�����߸�'),"
	       //+ " a.LiveGetType,"
	       + " decode(a.LiveGetType,'0','������ȡ','1','�������'),"
	       + " a.AddRate,"
	       + " a.GetStartDate,a.GetEndDate,"
	       + " a.GettoDate,a.StandMoney,a.ActuGet,"
	       + " a.State,"
	       //+ " a.GetEndState"
	       + " decode(a.LiveGetType,'0','��ȡδ��ֹ','1','��ȡ��ֹ')"
	       + " from LCGet a,LMDutyGet b"
	       + " Where a.ContNo='" + tContNo + "' and a.PolNo='" + tPolNo + "'"
	       + " and a.GetDutyCode = b.GetDutyCode"
	       + " order by GetStartDate asc";	*/			 
	mySql = new SqlClass();
	mySql.setResourceName("sys.GetItemQuerySql");
	mySql.setSqlId("GetItemQuerySql1");
	mySql.addSubPara(tContNo);
	mySql.addSubPara(tPolNo);  
	}
	else
	 if(tIsCancelPolFlag == "1")
	  {//��������
	   /*  strSQL = " select a.PolNo,b.GetDutyName,a.InsuredNo,"
	    	//	+" a.GetIntv,"
	    		+" (select c.CodeName from LDCode c where c.codetype ='getintv' and trim(c.code) = trim(a.GetIntv)),"
	    		+" a.GetStartDate,a.GetEndDate,"
	    		+" a.GettoDate,a.StandMoney,a.ActuGet,"
	    		+" a.GetMode,"
	    		//+" (select c.CodeName from LDCode c  where c.codetype ='livegetmode' and trim(c.code) = trim(a.GetMode)),"
	    		+" a.GetLimit,a.GetRate from LBGet a,LMDutyGet b "
	    		+" Where a.ContNo='" + tContNo + "' and a.PolNo='" + tPolNo + "'"
	    		+" and a.GetDutyCode = b.GetDutyCode"
	    	    +" order by GetStartDate asc";	 */
	mySql = new SqlClass();
	mySql.setResourceName("sys.GetItemQuerySql");
	mySql.setSqlId("GetItemQuerySql2");
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

	/*strSQL = " select a.PolNo,b.GetDutyName,a.InsuredNo,"
	      // + "a.GetIntv,"
	       + " (select c.CodeName from LDCode c where c.codetype ='getintv' and trim(c.code) = trim(a.GetIntv)),"
	       + " a.GetStartDate,a.GetEndDate,"
	       + " a.GettoDate,a.StandMoney,a.ActuGet,"
	      // + " a.GetMode,"
	       + " (select c.CodeName from LDCode c  where c.codetype ='livegetmode' and trim(c.code) = trim(a.GetMode)),"
	       + " a.GetLimit,a.GetRate from LCGet a,LMDutyGet b"
	       + " Where 1=1 "
	       + " and a.GetDutyCode = b.GetDutyCode"
	       + getWherePart('a.ContNo','ContNo')
	       + getWherePart('a.PolNo','PolNo')
	       + " order by GetStartDate asc";*/
	
		mySql = new SqlClass();
	mySql.setResourceName("sys.GetItemQuerySql");
	mySql.setSqlId("GetItemQuerySql3");
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