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

	if((fm.ActuGetNo.value==null||fm.ActuGetNo.value=="")&&(fm.OtherNo.value==null||fm.OtherNo.value=="")&&(fm.OtherNoType.value==null||fm.OtherNoType.value=="")&&(fm.ShouldDate.value==null||fm.ShouldDate.value=="")&&(fm.AgentCode.value==null||fm.AgentCode.value==""))
	{
		alert("�޲�ѯ����������������⣬������������һ����ѯ����");
		fm.ActuGetNo.focus();
		return;
		}
	document.all('getquery').disabled = true;
	initPolGrid();
	// ��дSQL���
	/*var strSQL = "";
	
	strSQL = "select ActuGetNo,OtherNo,OtherNoType,SumGetMoney,ShouldDate,ConfDate,Operator,ManageCom,AgentCode from LJAGet where 1=1 "			 
				 + getWherePart( 'ActuGetNo' )
				 + getWherePart( 'OtherNo' )
				 + getWherePart( 'OtherNoType' )
				 + getWherePart( 'ShouldDate' )				 
				 + getWherePart( 'AgentCode' )*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllGetQuerySql");
	mySql.setSqlId("AllGetQuerySql1");
	mySql.addSubPara(fm.ActuGetNo.value ); 	
	mySql.addSubPara(fm.OtherNo.value ); 	
	mySql.addSubPara(fm.OtherNoType.value ); 	
	mySql.addSubPara(fm.ShouldDate.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 	
	mySql.addSubPara(fm.MngCom.value ); 

	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("û�з���Ҫ������ݣ����ʵ��");
    document.all('getquery').disabled = false;
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
	    var cActuGetNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cActuGetNo == "")
		    return;
		    
		  var cOtherNoType = PolGrid.getRowColData(tSel - 1,3);
		  var cSumGetMoney = 	PolGrid.getRowColData(tSel - 1,4);

		  if (cOtherNoType==1||cOtherNoType==2) {
				window.open("../sys/AllGetQueryDrawDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}	
			else if (cOtherNoType==10){
				window.open("../sys/AllGetQueryEdorDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");							
			}
			else if (cOtherNoType==4){
				window.open("../sys/AllGetQueryTempDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}
			else if (cOtherNoType==5){
				window.open("../sys/AllGetQueryClaimDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}
			else if (cOtherNoType==6){
				window.open("../sys/AllGetQueryOtherDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}
			else if (cOtherNoType==7){
				window.open("../sys/AllGetQueryBonusDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}
			else
				{
					alert("��ʱ��֧�ָ����͵ĸ�����ϸ��ѯ�����ʵ��");
					return ;}
			
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
	mySql.setResourceName("sys.AllGetQuerySql");
	mySql.setSqlId("AllGetQuerySql4");
	mySql.addSubPara(trim(fm.AgentCode.value)); 	 
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