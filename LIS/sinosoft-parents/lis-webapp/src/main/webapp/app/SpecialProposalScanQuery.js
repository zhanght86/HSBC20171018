//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sFeatures2 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
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

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var queryBug = 1;
function ClickQuery() 
{
	// ��ʼ�����
	initPolGrid();

	var tQueryPrtNo = document.all('QueryPrtNo').value;  //ӡˢ��
	
	//alert(tQueryPrtNo);
		
	if(tQueryPrtNo == null || tQueryPrtNo == "")
	{
	  	//����¼��ӡˢ��,�����޷���ѯ;
	  	alert("ӡˢ�Ų���Ϊ�գ�����¼�룡");
    	return false;
	}
	
	//alert(tQueryPrtNo);
	//return false;
  //��ѯ��������Ϣ
  //var strWorkFlowSql = "select missionid from LWMission where Activityid in (select a.activityid  from lwactivity a where a.functionid in ( '10010006','10010020')) and MissionProp1='"+tQueryPrtNo+"'";
	//var sqlresourcename = "app.SpecialProposalScanQuerySql";
	var sqlid1="SpecialProposalScanQuerySql1";
	var mySql0=new SqlClass();
	mySql0.setResourceName("app.SpecialProposalScanQuerySql");
	mySql0.setSqlId(sqlid1);
  mySql0.addSubPara(tQueryPrtNo);//ָ���������
  var strWorkFlowSql = mySql0.getString();
   
  var arrWorkFlowResult = easyExecSql(strWorkFlowSql);
	//alert(arrWorkFlowResult);
	if (arrWorkFlowResult == null) 
	{
  	alert("��Ͷ����δ���������¼���������¼����ϣ����ܽ���¼����ٴ�¼��");
    return false;
  }

  
  //��ѯɨ�����Ϣ
  //var strSCANSql = "select ManageCom,SubType from ES_DOC_MAIN where doccode='"+tQueryPrtNo+"'";
  //var strSCANSql = "select ManageCom from ES_DOC_MAIN where doccode='"+tQueryPrtNo+"'";
//sqlresourcename = "app.SpecialProposalScanQuerySql";
	var sqlid2="SpecialProposalScanQuerySql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.SpecialProposalScanQuerySql");
	mySql1.setSqlId(sqlid2);
  mySql1.addSubPara(tQueryPrtNo);//ָ���������
  var strSCANSql = mySql1.getString();
    var arrSCANResult = easyExecSql(strSCANSql);//prompt("",strSCANSql);
	//alert(arrSCANResult);
	if (arrSCANResult == null) 
	{
  	alert("ɨ�����Ϣ��ѯʧ��");
    return false;
  }
  ManageCom = arrSCANResult[0][0];  //�����Ĺ������  
  //SubType = arrSCANResult[0][1];
  if(SubType == "UA006")
  {
  	SubType = "05";
  }
                                                  
//	var strSql = "select a.doccode, a.numpages, b.defaultoperator, a.InputStartDate, a.InputStartTime, a.ManageCom,a.makedate,a.maketime "
//					+ "from ES_DOC_MAIN a,LWMission b " 
//					+ "where "+queryBug + "=" + queryBug
//					+ " and a.subtype='UA001' "
//					//+ " and (a.InputState='0' or a.InputState is null ) "
//					+ " and a.doccode=b.missionprop1 "
//				//	+ " and b.activityid = '0000001094'"
//			   + " and (b.defaultoperator is null or b.defaultoperator ='" + operator + "') "					
//					//+ "and exists(select 1 from ldcode1 b where substr(a.doccode,3,2)=trim(b.code1) and b.codetype='scaninput' and b.code='proposal' )"
//					//+ "and exists(select 1 from ldcode1 c where substr(a.doccode,3,2)=trim(c.code) and c.codetype='scaninputctrl' and trim(c.code1)='" + manageCom + "' and a.managecom like trim(c.comcode)||'%%')"
//					+ "and exists(select 1 from BPOALLOTRATE where a.managecom like concat(trim(BPOALLOTRATE.MANAGECOM),'%%' ))"
//					+ getWherePart("b.missionprop1","QueryPrtNo")
//					+ " order by a.modifydate asc,a.modifytime asc";
//	
	var  QueryPrtNo = window.document.getElementsByName(trim("QueryPrtNo"))[0].value;
	var sqlid3="SpecialProposalScanQuerySql3";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.SpecialProposalScanQuerySql");
	mySql2.setSqlId(sqlid3); //ָ��ʹ��SQL��id
	mySql2.addSubPara(operator);//ָ���������
	mySql2.addSubPara(QueryPrtNo);//ָ���������
	var strSql = mySql2.getString();
	
  //alert(strSql);
  //prompt("",strSql);
  
  
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  
  //����һ�����ݵĽ�������ַ���:Ŀ���ǵ�ͨ�������У��֮�󣬽���ʾ�����ʾ��multiline��
  var backQueryResult=turnPage.strQueryResult;
  //alert("��ѯ�Ѿ�����ĵ��ӵĽ����= "+backQueryResult);
  
  var backSql=strSql;
  //alert("���ݵ�sql= "+backSql);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {  
    //���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
    PolGrid.clearData();  
    alert("û�з�������������");
    return false;
  }
  else
  {
  	  //�ж������������Ѿ���Ϊ����ʽ�����������������Ҫ�����ݵ��뵽����ҵ��ϵͳ����������ʾ����¼��
  	 // strSql="select 1 from LCCont where prtno='"+tQueryPrtNo+"'"
     //        +"  union all"
     //        +"  select 1 from BPOMissionState where BussNo='"+tQueryPrtNo+"' and  BussNoType='TB' and State <>'2'";   //��������ɾ�����쳣��
      //alert("����У���sql= "+strSql);
      //prompt("����У���sql",strSql);
    // strSql="select 1 from lwmission where  activityid in (select a.activityid  from lwactivity a where a.functionid in ( '10010006','10010020')) and missionprop1='"+tQueryPrtNo+"' ";
  
    var sqlid4="SpecialProposalScanQuerySql4";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("app.SpecialProposalScanQuerySql");
 	mySql3.setSqlId(sqlid4); //ָ��ʹ��SQL��id
 	mySql3.addSubPara(tQueryPrtNo);//ָ���������
 	var strSql = mySql3.getString();
      //��ѯSQL�����ؽ���ַ���
      turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
		//prompt("",strSql);
      //�ж��Ƿ��ѯ�ɹ�
      if (turnPage.strQueryResult)
      {  
          //û�з��������ļ�¼��֤��ͨ��У�飬��������ʾ�ڽ�����
          //�����ݵı������л�ԭ
          strSql=backSql;
          //alert("��ԭ��sql= "+strSql);
          turnPage.strQueryResult=backQueryResult;
          //alert("��ԭ�Ĳ�ѯ���= "+turnPage.strQueryResult);
          
          //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
				  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
				  
				  //��ѯ�ɹ������ַ��������ض�ά����
				  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				  
				  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
				  turnPage.pageDisplayGrid = PolGrid;    
				          
				  //����SQL���
				  turnPage.strQuerySql     = strSql; 
				  
				  //���ò�ѯ��ʼλ��
				  turnPage.pageIndex       = 0;  
				  
				  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
				  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
				  
				  //����MULTILINE������ʾ��ѯ���
				  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
				  
				  //�����Ƿ���ʾ��ҳ��ť
				  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) 
				  {
				      try { window.divPage.style.display = ""; } catch(ex) { }
				  } 
				  else 
				  {
				      try { window.divPage.style.display = "none"; } catch(ex) { }
				  }
      }
      else
      {
      		alert("��ӡˢ�Ŵ���ı����Ѿ�¼�����������Ѿ������ݵ��뵽����ҵ��ϵͳ�У�����Ҫ����¼��");
      		//prompt("","��ӡˢ�Ŵ���ı����Ѿ�¼�����������Ѿ������ݵ��뵽����ҵ��ϵͳ�У�����Ҫ����¼��");
      		return false;
      }
  }  
}


var prtNo = "";
function scanApplyClick() {
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	prtNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
  	
  	//��¼�����������ŤתΪ���Ᵽ��¼��ڵ�
  	var strDate = Date.parse(new Date());
  	//var strWorkFlowSql = "select missionid,submissionid,activityid from LWMission where Activityid in (select a.activityid  from lwactivity a where a.functionid = '10010006') and MissionProp1='"+prtNo+"'";
    
  	var sqlid5=("SpecialProposalScanQuerySql5");
    var mySql4=new SqlClass();
 	mySql4.setResourceName("app.SpecialProposalScanQuerySql");
 	mySql4.setSqlId(sqlid5); //ָ��ʹ��SQL��id
 	mySql4.addSubPara(prtNo);//ָ���������
 	var strWorkFlowSql = mySql4.getString();
 	
  	var arrWorkFlowResult = easyExecSql(strWorkFlowSql);
	  //alert("1: "+arrWorkFlowResult);
	  if (arrWorkFlowResult != null) 
	  {
	  	MissionID = arrWorkFlowResult[0][0];
      SubMissionID = arrWorkFlowResult[0][1];
      ActivityID = arrWorkFlowResult[0][2];
    	var urlStr1 = "./BPOInputConfirm1.jsp?PrtNo=" + prtNo + "&SpecType=1&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&WorkFlowFlag="+ActivityID+"&strDate="+strDate;
      //alert("2: "+urlStr1);
      var sFeatures1 = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
      //window.showModalDialog(urlStr1, "", sFeatures1);
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
      var iWidth=400;      //�������ڵĿ��; 
      var iHeight=200;     //�������ڵĸ߶�; 
      var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
      var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
      showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

      showInfo.focus();
    }
  		
  	//��ѯ���������Ᵽ��¼��ڵ�
  	//var strWorkFlowSql1 = "select missionid,submissionid,activityid,missionprop17,defaultoperator from LWMission where Activityid='0000001094' and MissionProp1='"+prtNo+"'";
    var sqlid6=("SpecialProposalScanQuerySql6");
    var mySql5=new SqlClass();
    mySql5.setResourceName("app.SpecialProposalScanQuerySql");
 	mySql5.setSqlId(sqlid6); //ָ��ʹ��SQL��id
 	mySql5.addSubPara(prtNo);//ָ���������
 	var strWorkFlowSql1 = mySql5.getString();
  	var arrWorkFlowResult1 = easyExecSql(strWorkFlowSql1);
	  //alert(arrWorkFlowResult1);
	  if (arrWorkFlowResult1 == null) 
	  {
    	alert("��Ͷ��������������Ͷ����¼���������");
      return false;
    }
      
    MissionID = arrWorkFlowResult1[0][0];
    SubMissionID = arrWorkFlowResult1[0][1];
    ActivityID = arrWorkFlowResult1[0][2];
  	SubType = arrWorkFlowResult1[0][3];
  	var operator1 = arrWorkFlowResult1[0][4];
  	//alert(SubType);
  	var urlStr;
  	var sFeatures;
  	var strReturn;
  	if(operator1!=null && operator1!='')
  	{
  		if(operator1!=operator)
  		{//��������
  			alert("��Ͷ�����Ѿ���"+operator1+"���룬�����ٴ����룡");
  			return false;
  		}
  		else
  		{//������������
  			strReturn = '1';
  		}
  		
  	}
  	else
  	{//����
  		urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state+"&strDate="+strDate;
	    //prompt("urlStr",urlStr);
	    sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1;";
	    //�����ӡˢ��
	    //strReturn = window.showModalDialog(urlStr, "", sFeatures);	 
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=400;      //�������ڵĿ��; 
		var iHeight=200;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		strReturn = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		strReturn.focus();		
  	}		
  	
  	//lockScreen('lkscreen');  
    //��ɨ���¼�����
    //sFeatures = "";
    //alert("SubType: "+SubType);
    sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;";
    if (strReturn == "1") 
    
      window.open("./DSContInputScanMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&scantype=bposcan&InputTime=0", "", sFeatures+sFeatures2);
          
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }

}

function afterInput() {
	// ��ʼ�����
	     initPolGrid();

 //���ý��и�У�� 2009-2-13 ln delete
  /*
  //¼����ɣ�ѯ���Ƿ���ɸñ���  
  var completeFlag = window.confirm("��ӡˢ�Ŷ�Ӧ�ı���¼���Ƿ�ȫ����ɣ�\nѡ���ǽ����ٲ�ѯ����ӡˢ�ţ�");  
  
  if (completeFlag)
   {
     var strSql = "select PrtNo from LCPol where PrtNo='" + prtNo + "'";
  
   
     var isComplete = easyExecSql(strSql);
    
     if (isComplete) {    
       var state = "1";
       var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;
       var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
       window.showModalDialog(urlStr, "", sFeatures);
       // ��ʼ�����
	     initPolGrid();
     }
     else {
       alert("��Ͷ������ӡˢ�ţ�" + prtNo + "����û�н���¼�룬���ܹر�ɨ�����");
     }
   }*/
  return true;
}

