//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
//  var i = 0;
//  var showStr="����׼����ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //fm.hideOperate.value=mOperate;
	if (fm.fmtransact.value=="")
  {
    alert("�����������ݶ�ʧ��");
  }
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
   
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //ִ����һ������
  }
}


//����ƾ֤��ӡ
function GPrint()
{ var tSel = PolGrid.getSelNo();	
 if( tSel == 0 || tSel == null )
		{
			alert( "����ѡ��һ����¼���ٵ������ƾ֤��ӡ��ť��" );
			return;
		}
  fm.action="GetCredenceF1PSave.jsp";
  fm.target="f1print";
  fm.fmtransact.value="PRINT";
  submitForm();
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

// ��ѯ��ť
function easyQueryClick()
{
//	alert("here");
	// ��ʼ�����
	initPolGrid();
	if( (fm.ActuGetNo.value == null || fm.ActuGetNo.value == "")
	&& (fm.OtherNo.value == null || fm.OtherNo.value == "")
	&& (fm.ShouldDate.value == null || fm.ShouldDate.value == "")
	&& (fm.AgentCode.value == null || fm.AgentCode.value == "")){
		alert("ʵ������/��������/Ӧ������/�����˱��� ��������дһ����в�ѯ");
		return false;
		}
	// ��дSQL���
	var strSQL = "";
	
	//zy 2009-07-14 17:31 ������ѯ�߼�
	//strSQL = "select ActuGetNo,OtherNo,OtherNoType,SumGetMoney,ShouldDate,ConfDate,Operator,ManageCom,AgentCode from LJAGet where OtherNoType in ('1','2','3','4','5','6','7','9','10') "			 
//	strSQL = "select ActuGetNo,OtherNo,OtherNoType,SumGetMoney,ShouldDate,ConfDate,Operator,ManageCom,AgentCode from LJAGet where 1=1 "			 
//				 + getWherePart( 'ActuGetNo' )
//				 + getWherePart( 'OtherNo' )
//				 + getWherePart( 'OtherNoType' )
//				 + getWherePart( 'ShouldDate' )				 
//				 + getWherePart( 'AgentCode' )
	var MngCom1="";
	var MngCom2="";
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
//		strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		MngCom1=managecomvalue;
	}	
	else
	{
//		strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' );
		MngCom2=window.document.getElementsByName(trim("MngCom"))[0].value;
	}
//		strSQL = strSQL +" and ActuGetNo not in (select otherno from LOPRTManager2 )"	
	//alert(strSQL);
	
	  	var  ActuGetNo0 = window.document.getElementsByName(trim("ActuGetNo"))[0].value;
	  	var  OtherNo0 = window.document.getElementsByName(trim("OtherNo"))[0].value;
	  	var  OtherNoType0 = window.document.getElementsByName(trim("OtherNoType"))[0].value;
	  	var  ShouldDate0 = window.document.getElementsByName(trim("ShouldDate"))[0].value;
	  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
		var sqlid0="GetCredenceInputSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("f1print.GetCredenceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(ActuGetNo0);//ָ������Ĳ���
		mySql0.addSubPara(OtherNo0);//ָ������Ĳ���
		mySql0.addSubPara(OtherNoType0);//ָ������Ĳ���
		mySql0.addSubPara(ShouldDate0);//ָ������Ĳ���
		mySql0.addSubPara(AgentCode0);//ָ������Ĳ���
		mySql0.addSubPara(MngCom1);//ָ������Ĳ���
		mySql0.addSubPara(MngCom2);//ָ������Ĳ���
		strSQL=mySql0.getString();
		
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("��ѯʧ�ܣ�");
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

//// ���ݷ��ظ�����
//function getQueryDetail()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼���ٵ��������ϸ��ť��" );
//	else
//	{
//	    var cActuGetNo = PolGrid.getRowColData(tSel - 1,1);				
////		parent.VD.gVSwitch.deleteVar("PayNo");				
////		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
//		
//		if (cActuGetNo == "")
//		    return;
//		    
//		  var cOtherNoType = PolGrid.getRowColData(tSel - 1,3);
//		  var cSumGetMoney = 	PolGrid.getRowColData(tSel - 1,4);
//		  //alert(cActuGetNo);
//		  //alert(cOtherNoType);
//		  //alert(cSumGetMoney);
//		  if (cOtherNoType==0||cOtherNoType==1||cOtherNoType==2) {
//				window.open("../sys/AllGetQueryDrawDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}	
//			else if (cOtherNoType==3){
//				window.open("../sys/AllGetQueryEdorDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);							
//			}
//			else if (cOtherNoType==4){
//				window.open("../sys/AllGetQueryTempDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==5){
//				window.open("../sys/AllGetQueryClaimDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==6){
//				window.open("../sys/AllGetQueryOtherDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==7){
//				window.open("../sys/AllGetQueryBonusDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			
//	}
//}

//�ݽ����˷Ѵ�ӡ����ƾ֤ʱʹ��
function initWithdraw() {
  try {
    //�����ѯ����ַ���
    turnPage.strQueryResult  = top.prtData;
    
    //ʹ��ģ������Դ������д�ڲ��֮ǰ
    turnPage.useSimulation   = 1;  
      
    //����ַ��������ض�ά����
    var tArr                 = decodeEasyQueryResult(turnPage.strQueryResult);
    
    //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
    var filterArray          = new Array(0, 1, 2, 5, 7, 9, 18, 26, 29); 
    
    //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    
    //���˶�ά���飬ʹ֮��MULTILINEƥ��
    turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
    
    //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    turnPage.pageDisplayGrid = PolGrid;             
    
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex       = 0;  
    
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  }
  catch(e) {
    alert("��ʼ���˷ѱ�����Ϣʧ��"); 
  } 
}

//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //��������	
//		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
		var sqlid1="GetCredenceInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.GetCredenceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(cAgentCode);//ָ������Ĳ���
		var strSql=mySql1.getString();
		
		var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) 
    {
			fm.AgentCode.value = arrResult[0][0];
			alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
			fm.AgentCode.value="";
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
	}	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
