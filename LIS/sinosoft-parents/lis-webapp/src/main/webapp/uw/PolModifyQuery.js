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

// ���ݷ��ظ�����
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		
		if (cPolNo == "")
		    return;
		    
		var GrpPolNo = PolGrid.getRowColData(tSel-1,1);
        
        //alert("dfdf");
        if( tIsCancelPolFlag == "0"){
	    	if (GrpPolNo =="00000000000000000000") {
	    	 	window.open("./AllProQueryMain.jsp?LoadFlag=3","window1");	
		    } else {
			window.open("./AllProQueryMain.jsp?LoadFlag=4");	
		    }
		} else {
		if( tIsCancelPolFlag == "1"){//����������ѯ
			if (GrpPolNo =="00000000000000000000")   {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} else {
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	    } else {
	    	alert("�������ʹ������!");
	    	return ;
	    }
	 }
 }
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
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);	
	    var prtNo=	PolGrid.getRowColData(tSel - 1,2);	    
		if (cPolNo == "")
		    return;
	var NoType = 1;
	
	 var strSql="";
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
    
//    strSql = "select case lmriskapp.riskprop"
//            +" when 'I' then '1'"
//	        +" when 'G' then '2'"
//	        +" when 'Y' then '3'"
//	        +" when 'T' then '5'"
//           + " end"
//           + " from lmriskapp"
//           + " where riskcode in (select riskcode"
//           + " from lcpol"
//           + " where polno = mainpolno"
//           + " and prtno = '"+prtNo+"')"
           
        var  sqlid1="PolModifyQuearySql0";
   	 	var  mySql1=new SqlClass();
   	 	mySql1.setResourceName("uw.PolModifyQuearySql"); //ָ��ʹ�õ�properties�ļ���
   	 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   	 	mySql1.addSubPara(prtNo);//ָ������Ĳ���
   	 	strSql=mySql1.getString();
           
    arrResult = easyExecSql(strSql);
    if(arrResult==null){
    	if(_DBT==_DBO){
//    		strSql = " select * from ("
//                + " select case missionprop5"
//                + " when '05' then '3'"
//                + " when '12' then '3'"
//                + " when '13' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001099'"
//                + " union"
//                + " select case missionprop5"
//                + " when 'TB05' then '3'"
//                + " when 'TB12' then '3'"
//                + " when 'TB06' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001098'"
//                + ") where rownum=1";
    		    var  sqlid2="PolModifyQuearySql1";
    	   	 	var  mySql2=new SqlClass();
    	   	 	mySql2.setResourceName("uw.PolModifyQuearySql"); //ָ��ʹ�õ�properties�ļ���
    	   	 	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    	   	 	mySql2.addSubPara(prtNo);//ָ������Ĳ���
    	   	    mySql2.addSubPara(prtNo);//ָ������Ĳ���
    	   	 	strSql=mySql2.getString();
    	}else if(_DBT==_DBM){
//    		strSql = " select * from ("
//                + " select case missionprop5"
//                + " when '05' then '3'"
//                + " when '12' then '3'"
//                + " when '13' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001099'"
//                + " union"
//                + " select case missionprop5"
//                + " when 'TB05' then '3'"
//                + " when 'TB12' then '3'"
//                + " when 'TB06' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001098'"
//                + ") ax  limit 1";
    		
    		var  sqlid3="PolModifyQuearySql2";
  	   	 	var  mySql3=new SqlClass();
  	   	 	mySql3.setResourceName("uw.PolModifyQuearySql"); //ָ��ʹ�õ�properties�ļ���
  	   	 	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
  	   	 	mySql3.addSubPara(prtNo);//ָ������Ĳ���
  	   	    mySql3.addSubPara(prtNo);//ָ������Ĳ���
  	   	 	strSql=mySql3.getString();
    	}
        
        arrResult = easyExecSql(strSql);               
    }
    var BankFlag = arrResult[0][0];

    
    //var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
        var  sqlid4="PolModifyQuearySql3";
 	 	var  mySql4=new SqlClass();
 	 	mySql4.setResourceName("uw.PolModifyQuearySql"); //ָ��ʹ�õ�properties�ļ���
 	 	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
 	 	mySql4.addSubPara(prtNo);//ָ������Ĳ���
 	    var strSql2=mySql4.getString();
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
    
	  window.open("../uw/PolModifyMain.jsp?LoadFlag=1&prtNo="+prtNo+"&PolNo="+cPolNo+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");    	    
	}
}

// ��ѯ��ť
function easyQueryClick()
{    
	// ��ʼ�����
	initPolGrid();

	if((fm.ContNo.value==""||fm.ContNo.value==null)&&(fm.PrtNo.value==""||fm.PrtNo.value==null)){
		alert("����������ӡˢ���벻��ȫΪ�գ�");
		return false;
	}
	// ��дSQL���
	var strSQL = "";
	
//	strSQL = "select contno,PrtNo,AppntName,"
//	             + " (select riskcode from lcpol where contno=a.contno and insuredno=a.insuredno and polno=mainpolno and (risksequence='1' or risksequence='-1')),"
//	             + " InsuredName,GrpContNo,CValiDate "
//				 +"from LCCont a where 1=1 and appflag='1'"
//				 + getWherePart( 'ContNo' )
//				 + getWherePart( 'GrpContNo' )
//				 + getWherePart( 'PrtNo' )
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
       var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
       var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
       var  RiskCode = window.document.getElementsByName(trim("RiskCode"))[0].value;
       var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
       var  AppntName = window.document.getElementsByName(trim("AppntName"))[0].value;
       var  InsuredNo = window.document.getElementsByName(trim("InsuredNo"))[0].value;
       var  InsuredName = window.document.getElementsByName(trim("InsuredName"))[0].value;
   	   var  sqlid5="PolModifyQuearySql4";
   	   var  mySql5=new SqlClass();
   	   mySql5.setResourceName("uw.PolModifyQuearySql"); //ָ��ʹ�õ�properties�ļ���
   	   mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
   	   mySql5.addSubPara(ContNo);//ָ������Ĳ���
   	   mySql5.addSubPara(GrpContNo);//ָ������Ĳ���
   	   mySql5.addSubPara(PrtNo);//ָ������Ĳ���
   	   mySql5.addSubPara(ManageCom);//ָ������Ĳ���
   	   mySql5.addSubPara(RiskCode);//ָ������Ĳ���
   	   mySql5.addSubPara(AgentCode);//ָ������Ĳ���
 	   mySql5.addSubPara(AppntName);//ָ������Ĳ���
 	   mySql5.addSubPara(InsuredNo);//ָ������Ĳ���
 	   mySql5.addSubPara(InsuredName);//ָ������Ĳ���
 	   mySql5.addSubPara(comCode);//ָ������Ĳ���
   	   strSQL=mySql5.getString();
		 
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
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
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
		//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
		var  sqlid6="PolModifyQuearySql5";
 	 	var  mySql6=new SqlClass();
 	 	mySql6.setResourceName("uw.PolModifyQuearySql"); //ָ��ʹ�õ�properties�ļ���
 	 	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
 	 	mySql6.addSubPara(cAgentCode);//ָ������Ĳ���
 	    var strSql=mySql6.getString();
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