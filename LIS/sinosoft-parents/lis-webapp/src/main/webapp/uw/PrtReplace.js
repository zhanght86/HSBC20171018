//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;

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


//���������Ĳ�ѯ����
function getQueryDetail_B()
{	
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	  var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		if (cPolNo == "")
			return;
		var GrpPolNo = PolGrid.getRowColData(tSel-1,6);
	    if (GrpPolNo =="00000000000000000000") 
	    {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} 
			else 
			{
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	}
}

function DateCompare(date1,date2)
{
  var strValue=date1.split("-");
  var date1Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);

  strValue=date2.split("-");
  var date2Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);

  if(date1Temp.getTime()==date2Temp.getTime())
    return 0;
  else if(date1Temp.getTime()>date2Temp.getTime())
    return 1;
  else
    return -1;
}

// ������ϸ��ѯ
function PrtReplace()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ���滻ӡˢ��!" );
	else
	{
		var prtNo=PolGrid.getRowColData(tSel - 1,2);
		if (prtNo == "")
		{
			alert("δ��ѯ��Ҫ���滻��ӡˢ��!");
			return;
		}
		var prtNo1=document.all('NewPrtNo').value;
		if(prtNo1=="")
		{
			alert("������Ҫ�����滻��ӡˢ��!");
			return;	
		}
		document.all('OldPrtNoHide').value = prtNo;
		var i = 0;
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();		
		document.getElementById("fm").submit(); //�ύ 	    
	}
}

// ��ѯ��ť
function easyQueryClick()
{    
	// ��ʼ�����
	initPolGrid();

	if((fm.ContNo.value==""||fm.ContNo.value==null)&&(fm.OldPrtNo.value==""||fm.OldPrtNo.value==null)){
		alert("����������ӡˢ���벻��ȫΪ�գ�");
		return false;
	}
	// ��дSQL���
	var strSQL = "";
	
//	strSQL = "select contno,PrtNo,AppntName,(select riskcode from lcpol where contno=a.contno and insuredno=a.insuredno and polno=mainpolno and (risksequence='1' or risksequence='-1')),InsuredName,GrpContNo,CValiDate "
//				 +"from LCCont a where 1=1 and appflag='4'"
//				 //appflag=4 - ��ֹ
//				 + getWherePart( 'ContNo' )
//				 + getWherePart( 'GrpContNo' )
//				 + getWherePart( 'PrtNo','OldPrtNo')
//				 + getWherePart( 'ManageCom','ManageCom','like')
//				 + getWherePart( 'RiskCode' )
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'AppntName' )
//				 + getWherePart( 'InsuredNo' )
//				 + getWherePart( 'InsuredName' )
//				 + " and ManageCom like '" + comCode + "%%'"
//				 + "order by prtno";
	
	   var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
       var  GrpContNo = window.document.getElementsByName(trim("GrpContNo"))[0].value;
       var  OldPrtNo = window.document.getElementsByName(trim("OldPrtNo"))[0].value;
       var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
       var  RiskCode = window.document.getElementsByName(trim("RiskCode"))[0].value;
       var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
       var  AppntName = window.document.getElementsByName(trim("AppntName"))[0].value;
       var  InsuredNo = window.document.getElementsByName(trim("InsuredNo"))[0].value;
       var  InsuredName = window.document.getElementsByName(trim("InsuredName"))[0].value;
	   var  sqlid1="PrtReplaceSql0";
	   var  mySql1=new SqlClass();
	   mySql1.setResourceName("uw.PrtReplaceSql"); //ָ��ʹ�õ�properties�ļ���
	   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	   mySql1.addSubPara(ContNo);//ָ������Ĳ���
	   mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
	   mySql1.addSubPara(OldPrtNo);//ָ������Ĳ���
	   mySql1.addSubPara(ManageCom);//ָ������Ĳ���
	   mySql1.addSubPara(RiskCode);//ָ������Ĳ���
	   mySql1.addSubPara(AgentCode);//ָ������Ĳ���
	   mySql1.addSubPara(AppntName);//ָ������Ĳ���
	   mySql1.addSubPara(InsuredNo);//ָ������Ĳ���
	   mySql1.addSubPara(InsuredName);//ָ������Ĳ���
	   mySql1.addSubPara(comCode);//ָ������Ĳ���
	   strSQL=mySql1.getString();
		 
	//prompt("",strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
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
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //showCodeName();
}



// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ��ӡˢ���滻!" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			//alert(arrReturn);
			//alert("����"+arrReturn);
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
			//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	var tRow = PolGrid.getSelNo();
	//alert(arrGrid);
	//alert("safsf");
	if( tRow == 0 || tRow == null || arrDataSet == null )
		      return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = PolGrid.getRowData(tRow-1);
	//alert(arrSelected[0][1]);
	//tRow = 10 * turnPage.pageIndex + tRow; //10����multiline������
	//������Ҫ���ص�����
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//������Ҫ���ص�����
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
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
		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initForm();
  }
}