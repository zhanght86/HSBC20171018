//�������ƣ�EdorUWManuAdd.js
//�����ܣ���ȫ�˹��˱��ӷ�¼��
//�������ڣ�2005-07-16 11:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var spanObj;
var hadAddFee = false;

//�ύ�����水ť��Ӧ����
function submitForm()
{
	
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  var cEdorNo = fm.EdorNo.value;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("δѡ��ӷѵ�Ͷ����!");
  	return;
  }
  
  if(fm.SubMissionID.value == "")
  {
  	alert("��¼��˱�֪ͨ����Ϣ,��δ��ӡ,������¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cEdorNo,cPolNo,cContNo, cMissionID, cSubMissionID);
  	
  	return;
  }
  
  
  var iAddPremCount = SpecGrid.mulLineCount;
  if (iAddPremCount < 1)
  {
  	fm.NoAddPrem.value = "Y";
  }
  
  if (!CalAllAddFee(''))
  {
    return;
  }
  //alert(9);
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.action= "./EdorUWManuAddSave.jsp";
  fm.submit(); //�ύ
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
    //alert(content);
  }
  else
  { 
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
{
    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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


function manuchkspecmain()
{
	fm.submit();
}

// ��ѯ��ť
function initlist(tPolNo, tEdorNo)
{
	// ��дSQL���
	k++;
	var strSQL = "";
//	strSQL = "select dutycode, 'Duty' from LPDuty where "+k+" = "+k
//		+ " and polno = '"+tPolNo+"' and EdorNo = '" + tEdorNo +"'";

	var sqlid1="EdorUWManuAddSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(k);//ָ������Ĳ���
	mySql1.addSubPara(k);//ָ������Ĳ���
	mySql1.addSubPara(tPolNo);//ָ������Ĳ���
	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	strSQL=mySql1.getString();	
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
    return str;
   
}

//��ѯ�Ѿ��ӷ���Ŀ
function QueryGrid(tEdorNo,tEdorType,tPolNo,tPolNo2)
{
	var strSQL = "";
	var i, j, m, n;	
	
	var EdorAppDate = "";
    //
//    strSQL =  " select Edorappdate from LPEdorMain where edoracceptno = '" + fm.EdorAcceptNo.value
//    		+ "' and contno = '" + fm.ContNo.value
//    		+ "' and EdorNo = '" + fm.EdorNo.value + "'";
    var sqlid2="EdorUWManuAddSql2";
    var mySql2=new SqlClass();
    mySql2.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
    mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    mySql2.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
    mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
    mySql2.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
    strSQL=mySql2.getString();   
        var urr = easyExecSql(strSQL);
    if ( urr )  
    {
        urr[0][0]==null||urr[0][0]=='null'?'0':EdorAppDate = urr[0][0];
    }

    //��ȡԭ�ӷ���Ϣ
//	strSQL =   " select dutycode, PayPlanType, '', suppriskscore, SecInsuAddPoint, AddFeeDirect, Prem, PayPlanCode, PayStartDate, PayToDate, PayEndDate "
//	         + " from LPPrem where 1=1 "
//			 + " and payplancode like '000000__'"
//			 + " and ((PayStartDate <= '" + EdorAppDate + "' and PayEndDate >= '" + EdorAppDate + "') "
//			 + " or (PayStartDate >= '" + EdorAppDate + "'))"
//			 + " and PolNo ='"+tPolNo2+"' and edortype = '"+tEdorType+"' and edorno = '"+tEdorNo+"'"
//			 //+ " union "
//			 //+ " select dutycode, PayPlanType, '', suppriskscore, SecInsuAddPoint, AddFeeDirect, Prem, PayPlanCode, PayStartDate, PayToDate, PayEndDate "
//	         //+ " from LPPrem where 1=1 "
//			 //+ " and payplancode like '000000__'"
//			 //+ " and PayStartDate >= (select Edorvalidate from LPEdorMain where EdorNo = '" + fm.EdorNo.value + "')"
//			 //+ " and PolNo ='"+tPolNo2+"' and edortype = '"+tEdorType+"' and edorno = '"+tEdorNo+"'"
//			 ;
			 
	    var sqlid3="EdorUWManuAddSql3";
	    var mySql3=new SqlClass();
	    mySql3.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	    mySql3.addSubPara(EdorAppDate);//ָ������Ĳ���
	    mySql3.addSubPara(EdorAppDate);//ָ������Ĳ���
	    mySql3.addSubPara(EdorAppDate);//ָ������Ĳ���
	    mySql3.addSubPara(tPolNo2);//ָ������Ĳ���
	    mySql3.addSubPara(tEdorType);//ָ������Ĳ���
	    mySql3.addSubPara(tEdorNo);//ָ������Ĳ���
	    strSQL=mySql3.getString();   
	    
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  //�ж��Ƿ��ѯ�ɹ�
    if (turnPage.strQueryResult)
    {
    	hadAddFee = true; 
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
        //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
        turnPage.pageDisplayGrid = SpecGrid;    
          
        //����SQL���
        turnPage.strQuerySql     = strSQL; 
  
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex       = 0;  
  
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //alert(arrDataSet);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    if (!turnPage.strQueryResult)
	{
		hadAddFee = false; 
	}
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
//    strSQL = "select CValiDate, PayToDate, PayEndDate from LPPol where PolNo = '" + tPolNo2 + "' and EdorNo = '" + tEdorNo + "' and EdorType = '" + tEdorType + "'";
	
    var sqlid4="EdorUWManuAddSql4";
    var mySql4=new SqlClass();
    mySql4.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
    mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
    mySql4.addSubPara(tPolNo2);//ָ������Ĳ���
    mySql4.addSubPara(tEdorNo);//ָ������Ĳ���
    mySql4.addSubPara(tEdorType);//ָ������Ĳ���
    strSQL=mySql4.getString();   
    
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

    if (!turnPage.strQueryResult)
    {  
//        strSQL = "select CValiDate, PayToDate, PayEndDate from LCPol where PolNo = '" + tPolNo2 + "'";
        var sqlid6="EdorUWManuAddSql6";
        var mySql6=new SqlClass();
        mySql6.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
        mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
        mySql6.addSubPara(tPolNo2);//ָ������Ĳ���
        strSQL=mySql6.getString();           
	    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
    }

    //�ж��Ƿ��ѯ�ɹ�
    if (turnPage.strQueryResult)
    {  
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        fm.PayStartDate.value = turnPage.arrDataCacheSet[0][0];
        if (turnPage.arrDataCacheSet[0][1] != null && turnPage.arrDataCacheSet[0][1] != '')
        {
            fm.PayToDate.value = turnPage.arrDataCacheSet[0][1];
        }
        fm.PayEndDate.value = turnPage.arrDataCacheSet[0][2];
    }
   
    return true;	
}

//��ѯ������Ϣ
function QueryPolAddGrid(tEdorNo,tContNo,tInsuredNo)
{
//   var strSql = "select EdorNo,EdorType,ContNo,PolNo,prtno,RiskCode,RiskVersion,"
//             + "  AppntName,InsuredName,Prem,MainPolNo from LPPol where "				 			
//			 + "  ContNo ='"+tContNo+"'"+" and EdorNo='"+tEdorNo+"'"
//			 + "  order by polno ";
   var strSql = "";
   var sqlid5="EdorUWManuAddSql5";
   var mySql5=new SqlClass();
   mySql5.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
   mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
   mySql5.addSubPara(tContNo);//ָ������Ĳ���
   mySql5.addSubPara(tEdorNo);//ָ������Ĳ���
   strSql=mySql5.getString();   
   var brr = easyExecSql(strSql);
   
   if(brr)
   {
   	 initPolAddGrid();
   	 turnPage.queryModal(strSql,PolAddGrid);
   	 
   	 divSubmit.style.display = '';
   	 divReturn.style.display = '';
   	 
   }
   else
   {
   	 alert("û��������Ҫ�˱���");
   	 divSubmit.style.display = 'none';
   	 divReturn.style.display = '';
   	 return;
   }
  return true;	
}
	
function getPolGridCho()
{
  var tSelNo = PolAddGrid.getSelNo()-1;
  tRow = tSelNo;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,4);
  var cPolNo = PolAddGrid.getRowColData(tSelNo,4);
  var tEdorType = PolAddGrid.getRowColData(tSelNo,2);
  fm.EdorType.value = tEdorType;
  var tEdorNo = fm.EdorNo.value;
  fm.PolNo.value = cPolNo
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" )
  {
  	divAddInfo.style.display = '';
  	str = initlist(cPolNo2, tEdorNo);
    initUWSpecGrid(str);
    QueryGrid(tEdorNo,tEdorType,cPolNo,cPolNo2);	
    QueryAddReason(tEdorNo,tEdorType,cPolNo);    
  }	
}


//��ѯ�Ѿ�¼��ӷ���Լԭ��
function QueryAddReason(tEdorNo,tEdorType,tPolNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select addpremreason from LPUWMaster where edortype='"+tEdorType
//			 + "' and polno = '"+tPolNo+"' and edorno = '"+tEdorNo+"'";
	
	   var sqlid7="EdorUWManuAddSql7";
	   var mySql7=new SqlClass();
	   mySql7.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	   mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	   mySql7.addSubPara(tEdorType);//ָ������Ĳ���
	   mySql7.addSubPara(tPolNo);//ָ������Ĳ���
	   mySql7.addSubPara(tEdorNo);//ָ������Ĳ���
	   strSQL=mySql7.getString();   	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	fm.AddReason.value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.AddReason.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

//��ʼ���ӷѶ���
function initAddObj(span)
{
	spanObj = span;
	var tSelNo = PolAddGrid.getSelNo()-1; 
    var tRiskCode = PolAddGrid.getRowColData(tSelNo,6);
	var tDutyCode = document.all( spanObj ).all( 'SpecGrid1' ).value;
	var tAddFeeType = document.all(spanObj).all('SpecGrid2').value;
	
    if(tAddFeeType = '01')
    {
//	var srtSql = "select AddFeeObject from LMDutyPayAddFee where 1=1 "
//	           + " and riskcode = '"+tRiskCode+"'"
//	           + " and DutyCode = '"+tDutyCode+"'"
//	           + " and AddFeeType = '01'";
	   var srtSql = "";
	   var sqlid8="EdorUWManuAddSql8";
	   var mySql8=new SqlClass();
	   mySql8.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	   mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	   mySql8.addSubPara(tRiskCode);//ָ������Ĳ���
	   mySql8.addSubPara(tDutyCode);//ָ������Ĳ���
	   srtSql=mySql8.getString();   		
	turnPage.strQueryResult  = easyQueryVer3(srtSql, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
  	document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    document.all( spanObj ).all( 'SpecGrid1' ).value = turnPage.arrDataCacheSet[0][0];  

}
else
	{
		document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
	}
    return true;	
	
}
	


/*********************************************************************
 *  Click�¼�����˫�����ӷѽ�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showUWSpec( span)
{
	spanObj = span;
	
	// ��дSQL���
	var strSQL = "";
	var tRiskCode="";
	var tInsuredSex="";
	var tInsuredAppAge="";
	var tSuppRiskCore="";
	var tAddFeeKind="";
	var tPayEndYear="";
    //У��¼����Ϣ��������
	if(document.all( spanObj ).all( 'SpecGrid1' ).value == ""){
		alert("��¼��ӷ�������Ϣ��");
		return;
	}
	
	if(document.all( spanObj ).all( 'SpecGrid2' ).value == ""){
		alert("��¼��ӷ�ԭ����Ϣ��");
		return;
	}
	else
	tAddFeeKind=document.all( spanObj ).all( 'SpecGrid2' ).value;
	
 	if(document.all( spanObj ).all( 'SpecGrid5' ).value==""){
		alert("��¼��ӷ�������Ϣ��");
		return;
	}
	else
	tSuppRiskCore=document.all( spanObj ).all( 'SpecGrid5' ).value;
	
	
    //�˴���ְҵ�ӷѴ���û��ȷ����
    if(tAddFeeKind=="1"||tAddFeeKind=="3")
    {
//	strSQL = "select AddFeeAMNT("+tAddFeeKind+",riskcode,polno,"+tSuppRiskCore+") from LCpol where polno='"+PolAddGrid.getRowColData(tRow,1)+"'";	
	   var srtSql = "";
	   var sqlid9="EdorUWManuAddSql9";
	   var mySql9=new SqlClass();
	   mySql9.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	   mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	   mySql9.addSubPara(tAddFeeKind);//ָ������Ĳ���
	   mySql9.addSubPara(tSuppRiskCore);//ָ������Ĳ���
	   mySql9.addSubPara(PolAddGrid.getRowColData(tRow,1));//ָ������Ĳ���
	   srtSql=mySql9.getString();  	   
	turnPage.strQueryResult  = easyQueryVer3(srtSql, 1, 0, 1);   

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    {
  	alert("�����ӷ��������ʧ�ܣ�");
    return "";
    }
   
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    document.all( spanObj ).all( 'SpecGrid6' ).value = turnPage.arrDataCacheSet[0][0];	
    return true;	
    }
    else
  	{
    alert("δ����ְҵ�ӷ����㣡");
    return "";
    }
   	
}    

function CalAllAddFee(span)
{
  var tEdorType = fm.EdorType.value;
  var AddCount = SpecGrid.mulLineCount ; 
  if(AddCount == 0)
  {
	  return true;
  }

  for (var j = 0; j < AddCount; j++)
  {

	if (SpecGrid.getRowColData(j, 1) == null || SpecGrid.getRowColData(j, 1) == '')
	{
        alert("����¼�����α���");   
        return false;
	}

	if (SpecGrid.getRowColData(j, 2) == null || SpecGrid.getRowColData(j, 2) == '')
	{
		if (fm.OtherNoType.value == '4') //���岻��¼
		{
			SpecGrid.setRowColData(j, 2, "01")
		}
	  else
	  {
        alert("����¼��ӷ�����");   
        return false;
    }
	}

	if (SpecGrid.getRowColData(j, 2) == '01' && fm.EdorType.value == 'RE')
	{
		SpecGrid.getRowColData(j, 2) == '03';
	}
  
	if (SpecGrid.getRowColData(j, 2) == '02' && fm.EdorType.value == 'RE')
	{
		SpecGrid.getRowColData(j, 2) == '04';
	}

	if (SpecGrid.getRowColData(j, 3) == null || SpecGrid.getRowColData(j, 3) == '')
    {
        alert("����¼��ӷѷ�ʽ");   
        return false;
    }
    if (SpecGrid.getRowColData(j, 3) == '01' ||  tEdorType == "AA" ||  tEdorType == "NS")
    {
        SpecGrid.setRowColData(j, 9, fm.PayStartDate.value); 
    }
    else if (SpecGrid.getRowColData(j, 3) == '02')
    {
        SpecGrid.setRowColData(j, 9, fm.PayToDate.value); 
    }
    else
    {
        alert("��¼����ȷ�ļӷѷ�ʽ��");   
        return false;
    }    


    SpecGrid.setRowColData(j, 10, fm.PayToDate.value); 
    SpecGrid.setRowColData(j, 11, fm.PayEndDate.value); 
    
    fm.GridRow.value = j;
    var sObj = "spanSpecGrid" + (j + 1);
    if (SpecGrid.getRowColData(j, 7) == null || SpecGrid.getRowColData(j, 7) == '')
    {
        alert("��¼����ȷ�ļӷѽ�");
        return false;
    }
  }
  return true;
}

function CalHealthAddFee(span)
{
    //alert(span);
    var spanObj = span;
    var i = SpecGrid.mulLineCount; 
    //alert(i);
    for (var j = 0; j < i; j++)
    {
		if (SpecGrid.getRowColData(j, 2) == null || SpecGrid.getRowColData(j, 2) == '')
		{
			alert("����¼��ӷ�����");   
			return false;
		}
		if (SpecGrid.getRowColData(j, 2) == '01' && fm.EdorType.value == 'RE')
		{
			SpecGrid.setRowColData(j, 2, '03');
		}
  
		if (SpecGrid.getRowColData(j, 2) == '02' && fm.EdorType.value == 'RE')
		{
			SpecGrid.setRowColData(j, 2, '04');
		}
		//alert(hadAddFee);
		if (hadAddFee) //��ѯ��ʼ�����мӷ�
		{
			var sObj = "spanSpecGrid" + (j + 1);
		}
	    else  //��ѯ��ʼ��û�мӷ�
		{
			var sObj = "spanSpecGrid" + (j );
		}
        
        //alert(sObj);
        if (sObj == span)
        {
        	//alert(j );
            fm.GridRow.value = j ;
            break;
        }
    }
	
    var tSelNo = PolAddGrid.getSelNo()-1; 
    var tPolNo = PolAddGrid.getRowColData(tSelNo,4);
    var tMainPolNo = PolAddGrid.getRowColData(tSelNo,11);
    var tPrem ;//= PolAddGrid.getRowColData(tSelNo,10);
    //��������εı���
    var tdutyCode = document.all( spanObj ).all( 'SpecGrid1' ).value;
    var tEdorNo = PolAddGrid.getRowColData(tSelNo, 1);
    var tEdorType = PolAddGrid.getRowColData(tSelNo, 2);

    var tSuppRiskScore = document.all( spanObj ).all( 'SpecGrid4' ).value;
    //����������ж���ӷ��㷨�����ߺ�̨���㣬���û�ж���ӷѼ��㣬����ǰֱ̨�Ӽ��� zhangtao 2007-03-07
//	  var strSQL = "  select 1 from LMDutyPayAddFee where dutycode = '" + tdutyCode + "'";
	   var strSQL = "";
	   var sqlid10="EdorUWManuAddSql10";
	   var mySql10=new SqlClass();
	   mySql10.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	   mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	   mySql10.addSubPara(tdutyCode);//ָ������Ĳ���
	   strSQL=mySql10.getString();  	   
	  var brr = easyExecSql(strSQL);
    if(tPolNo != tMainPolNo && !brr)  //�����ղ���û�ж���ӷ��㷨
    {
//	    strSQL = "  select sum(prem) from lpprem where payplantype = '0' and edorno = '" + tEdorNo + 
//	    			 "' and edortype = '" + tEdorType + 
//	    			 "' and polno = '" + tPolNo +
//	    			 "' and dutycode = '" + tdutyCode + "'";
	       var strSQL = "";
		   var sqlid11="EdorUWManuAddSql11";
		   var mySql11=new SqlClass();
		   mySql11.setResourceName("bq.EdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
		   mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		   mySql11.addSubPara(tEdorNo);//ָ������Ĳ���
		   mySql11.addSubPara(tEdorType);//ָ������Ĳ���
		   mySql11.addSubPara(tPolNo);//ָ������Ĳ���
		   mySql11.addSubPara(tdutyCode);//ָ������Ĳ���
		   strSQL=mySql11.getString();  	   
	    
	    brr = easyExecSql(strSQL);
	    if ( brr ) 
	    {
	        brr[0][0]==null||brr[0][0]=='null'?'0':tPrem = brr[0][0];
	    }
	    else
	    {
	    	alert("������Ѳ�ѯʧ�ܣ�");
	    	return;
	    }
     	var tAddPrem = tSuppRiskScore / 100 * tPrem;
     	tAddPrem = Math.round(tAddPrem * 100) / 100;
     	document.all( spanObj ).all( 'SpecGrid7' ).value = tAddPrem;  	
    }
    else
	{
	    document.all('DutyCode').value = document.all( spanObj ).all( 'SpecGrid1' ).value;
	    document.all('AddFeeType').value = document.all( spanObj ).all( 'SpecGrid2' ).value;
        document.all('SuppRiskScore').value = document.all( spanObj ).all( 'SpecGrid4' ).value;
	    document.all('SecondScore').value = document.all( spanObj ).all( 'SpecGrid5' ).value;
	    document.all('AddFeeObject').value = document.all( spanObj ).all( 'SpecGrid6' ).value;
	    
    	var i = 0;
        var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        fm.action= "./EdorUWCalAddFee.jsp";
        fm.submit(); //�ύ
    }
}
