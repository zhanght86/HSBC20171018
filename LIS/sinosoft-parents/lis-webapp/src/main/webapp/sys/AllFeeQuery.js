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



// ��ѯ��ť
function easyQueryClick()
{
	
//	alert("here");
	// ��ʼ�����

	if((fm.PayNo.value==null||fm.PayNo.value=="")&&(fm.IncomeNo.value==null||fm.IncomeNo.value=="")&&(fm.IncomeType.value==null||fm.IncomeType.value=="")&&(fm.PayDate.value==null||fm.PayDate.value=="")&&(fm.AgentCode.value==null||fm.AgentCode.value==""))
	{
		alert("�޲�ѯ����������������⣬������������һ����ѯ����");
		fm.PayNo.focus();
		return;
		}
	document.all('feequery').disabled = true;
	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	
	/*strSQL = "select PayNo,IncomeNo,IncomeType,SumActuPayMoney,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay where 1=1 "			 
				 + getWherePart( 'PayNo' )
				 + getWherePart( 'IncomeNo' )
				 + getWherePart( 'IncomeType' )
				 + getWherePart( 'PayDate' )				 
				 + getWherePart( 'AgentCode' )*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllFeeQuerySql");
	mySql.setSqlId("AllFeeQuerySql1");
	mySql.addSubPara(fm.PayNo.value ); 
	mySql.addSubPara(fm.IncomeNo.value ); 	
	mySql.addSubPara(fm.IncomeType.value ); 	
	mySql.addSubPara(fm.PayDate.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 				 
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
		//strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		mySql = new SqlClass();
		mySql.setResourceName("sys.AllFeeQuerySql");
		mySql.setSqlId("AllFeeQuerySql2");
		mySql.addSubPara(fm.PayNo.value ); 
		mySql.addSubPara(fm.IncomeNo.value ); 	
		mySql.addSubPara(fm.IncomeType.value ); 	
		mySql.addSubPara(fm.PayDate.value ); 	
		mySql.addSubPara(fm.AgentCode.value ); 	
		mySql.addSubPara(managecomvalue ); 		
	}	
	else
	{
		//strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' )+" and managecom like '"+managecomvalue+"%%'";
		mySql = new SqlClass();
		mySql.setResourceName("sys.AllFeeQuerySql");
		mySql.setSqlId("AllFeeQuerySql3");
		mySql.addSubPara(fm.PayNo.value ); 
		mySql.addSubPara(fm.IncomeNo.value ); 	
		mySql.addSubPara(fm.IncomeType.value ); 	
		mySql.addSubPara(fm.PayDate.value ); 	
		mySql.addSubPara(fm.AgentCode.value ); 	
		mySql.addSubPara(fm.MngCom.value  ); 
		mySql.addSubPara(managecomvalue); 
	}		

	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("�������Ӧ��ʵ����Ϣ�����ʵ��");
     document.all('feequery').disabled = false;
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



// ���ݷ��ظ�����
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ��������ϸ��ť��" );
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
/*			if(cIncomeType==2)
			{
				window.open("../sys/AllFeeQueryPDetail.jsp?PolNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
			}*/
			
			else {
				window.open("../sys/AllFeeQueryPerDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
							
			}
	}
}

function queryAgent()
{
	if(document.all('MngCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('MngCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
	if(document.all('AgentCode').value != "")	 {
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + trim(fm.AgentCode.value) +"'";
    mySql = new SqlClass();
		mySql.setResourceName("sys.AllFeeQuerySql");
		mySql.setSqlId("AllFeeQuerySql4");
		mySql.addSubPara(trim(fm.AgentCode.value) );  
    var arrResult = easyExecSql(mySql.getString());
    if (arrResult != null) {
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
      } 
	}
}


//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  }
}