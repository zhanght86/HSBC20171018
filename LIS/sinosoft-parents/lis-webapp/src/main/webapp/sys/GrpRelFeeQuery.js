//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
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



// ���ݷ��ظ�����
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
		if (cPayNo == "")
		    return;
		  var cIncomeType = PolGrid.getRowColData(tSel - 1,3);
		  var cSumActuPayMoney = 	PolGrid.getRowColData(tSel - 1,4);
		  if (cIncomeType==1) {
				window.open("../sys/AllFeeQueryGDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}
			else
			if (cIncomeType==2) {
				window.open("../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}	
			else {
				window.open("../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
							
			}
	}
}




function easyQueryClick()
{
	
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	
	/*strSQL = "select a.PayNo,a.IncomeNo,a.IncomeType,a.SumActuPayMoney,b.CurPayToDate,a.ConfDate,a.Operator,a.ManageCom,a.AgentCode,b.paycount from LJAPay a,LJAPayGrp b  where a.IncomeNo='" + tGrpContNo + "' and a.IncomeType='1' "
	       +"and a.PayNo=b.PayNo and b.RiskCode='"+ tRiskCode +"'"
	       +"order by b.paycount ";	*/		 
	//alert(strSQL);
	 mySql = new SqlClass();
	mySql.setResourceName("sys.GrpRelFeeQuerySql");
	mySql.setSqlId("GrpRelFeeQuerySql1");
	mySql.addSubPara(tGrpContNo);  	
	mySql.addSubPara(tRiskCode);  	
	//��ѯSQL�����ؽ���ַ���
  
   turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
    	
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
function goback()
{
  top.close();	
}  
