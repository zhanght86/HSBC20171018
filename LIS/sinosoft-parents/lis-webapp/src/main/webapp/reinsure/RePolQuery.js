//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";


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



// ���Ѳ�Ѱ
function FeeQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cReinsurItem = PolGrid.getRowColData(tSel - 1,1);
		  var cInsuredYear = PolGrid.getRowColData(tSel - 1,8);
		  window.open("ReEdorQueryPDetail.jsp?PolNo=" + cPolNo + "&ReinsurItem=" + cReinsurItem + "&InsuredYear=" + cInsuredYear);										
	}
}





// ������Ѱ
function GetQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cReinsurItem = PolGrid.getRowColData(tSel - 1,1);
		  var cInsuredYear = PolGrid.getRowColData(tSel - 1,8);
		  window.open("ReClaimQueryPDetail.jsp?PolNo=" + cPolNo + "&ReinsurItem=" + cReinsurItem + "&InsuredYear=" + cInsuredYear);										
	}
}


//���Ĳ��˷Ѳ�ѯ
function EdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/EdorQuery.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName  + "&IsCancelPolFlag=" + tIsCancelPolFlag );										
	}
}



// �潻/����Ѳ�ѯ
function LoLoanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
           // alert("cPolNo"+cPolNo);
		  window.open("../sys/LoLoanQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag);										
	}
}




//��ȫ��ѯ
function PerEdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/AllPBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag );										
	}	
}

// ���ղ�Ѱ
function MainRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo==cMainPolNo)
			myAlert(""+"��ѡ�����һ���������ݣ�����ѡ��һ�����������ݺ��ٵ�����ղ�ѯ��ť��"+"")
		else 
			{
									
				if (cMainPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// ��дSQL���
						var mSQL = "";
						
						//mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";			 
						var mySql100=new SqlClass();
	 						mySql100.setResourceName("reinsure.RePolQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  						mySql100.setSqlId("RePolQuerySql100");//ָ��ʹ�õ�Sql��id
	 		 				mySql100.addSubPara(cMainPolNo);//ָ������Ĳ���
	  						mSQL=mySql100.getString();
						
						//��ѯSQL�����ؽ���ַ���
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
					  //�ж��Ƿ��ѯ�ɹ�
					  if (!turnPage.strQueryResult) {
					  	PolGrid.clearData();
					  	myAlert(""+"���ݿ���û���������������ݣ�"+"");
					    //alert("��ѯʧ�ܣ�");
					    return false;
					  }
					  
					  //��ѯ�ɹ������ַ��������ض�ά����
					  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
					  
					  //���ó�ʼ������MULTILINE����
					  turnPage.pageDisplayGrid = PolGrid;    
					          
					  //����SQL���
					  turnPage.strQuerySql     = mSQL; 
					  
					  //���ò�ѯ��ʼλ��
					  turnPage.pageIndex = 0;  
					  
					  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
					  
					  //����MULTILINE������ʾ��ѯ���
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}	
	}
}
//function MainRiskQuery()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼��" );
//	else
//	{
//	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
//			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
//		
//		if (cPolNo==cMainPolNo)
//			alert("��ѡ�����һ���������ݣ�����ѡ��һ�����������ݺ��ٵ�����ղ�ѯ��ť��")
//		else
//			{
//					
//					parent.VD.gVSwitch.deleteVar("PolNo");				
//					parent.VD.gVSwitch.addVar("PolNo","",cMainPolNo);
//					
//					if (cMainPolNo == "")
//					    return;
//					    
//					var tSQL = "select GrpPolNo from LCPol where PolNo='" + cMainPolNo +"'";
//					var tData = easyExecSql(tSQL,1,0,1);    
//					var GrpPolNo = tData[0][0];
//			
//				    if (GrpPolNo =="00000000000000000000") {
//						window.open("./AllProQueryMain.jsp?LoadFlag=3","window1");	
//					} else {
//						window.open("./AllProQueryMain.jsp?LoadFlag=4");	
//					}
//			}							
//	}
//}


//�����ղ�ѯ
function OddRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo!=cMainPolNo)
			myAlert(""+"��ѡ�����һ�����������ݣ�����ѡ��һ���������ݺ��ٵ�������ղ�ѯ��ť��"+"")
		else
			{
									
				if (cPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// ��дSQL���
						var mSQL = "";
						
						//mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";			 
						var mySql101=new SqlClass();
	 						mySql101.setResourceName("reinsure.RePolQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  						mySql101.setSqlId("RePolQuerySql101");//ָ��ʹ�õ�Sql��id
	 		 				mySql101.addSubPara(cMainPolNo);//ָ������Ĳ���
	 		 				mySql101.addSubPara(cPolNo);//ָ������Ĳ���
	  						mSQL=mySql101.getString();
						//��ѯSQL�����ؽ���ַ���
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
					  //�ж��Ƿ��ѯ�ɹ�
					  if (!turnPage.strQueryResult) {
					  	PolGrid.clearData();
					  	myAlert(""+"���ݿ���û���������������ݣ�"+"");
					    //alert("��ѯʧ�ܣ�");
					    return false;
					  }
					  
					  //��ѯ�ɹ������ַ��������ض�ά����
					  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
					  
					  //���ó�ʼ������MULTILINE����
					  turnPage.pageDisplayGrid = PolGrid;    
					          
					  //����SQL���
					  turnPage.strQuerySql     = mSQL; 
					  
					  //���ò�ѯ��ʼλ��
					  turnPage.pageIndex = 0;  
					  
					  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
					  
					  //����MULTILINE������ʾ��ѯ���
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}	
	}
}

// ������ϸ��ѯ
function PolClick1()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼���ٵ�����ذ�ť��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;		    
	  window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag);	
		
	}
}

// ������ϸ��ѯ
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼���ٵ�����ذ�ť��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;		    
 
		    window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo);	
	}
}

// ���������ѯ
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);										
	}	
}

//ɨ�����ѯ
function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
	}	     
}

//���屣��ɨ�����ѯ
function ScanQuery2()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
	}	     
}

function GoBack(){
	
	top.opener.easyQueryClick();
	top.close();
	
	}
