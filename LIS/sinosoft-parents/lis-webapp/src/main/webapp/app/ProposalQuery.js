//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var arrResult1;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var queryBug = 1;
function easyQueryClick()  
{
	// ��ʼ�����
	initPolGrid();
	//������Ĳ�ѯ��������У��
	if( verifyInput2() == false ) 
	{
		return false;
	}
	//�ж��Ƿ��в�ѯ����������߲�ѯ�ٶ�----added by guanwei
	var i = 0;
	if(document.all("ManageCom").value != "")
	i = i+1;
	if(document.all("PolApplyDate").value != "")
	i = i+1;
	if(document.all("AppntNo").value != "")
	i = i+1;
	if(document.all("AppntName").value != "")
	i = i+1;	
	if(document.all("AppntIDNo").value != "")
	i = i+1;
	if(document.all("InsuredNo").value != "")
	i = i+1;	
	if(document.all("InsuredName").value != "")
	i = i+1;
	if(document.all("InsuredIDNo").value != "")
	i = i+1;
	if(document.all("uwState").value != "")
	i = i+1;
	if(document.all("AgentCode").value != "")
	i = i+1;
	//if(document.all("AgentGroup").value != "")
	//i = i+1;
	if(document.all("uwgrade").value != "")
	i = i+1;
	if(document.all("AppntNo").value != "")
	i = i+1;
	  if (i < 2 && document.all("ContNo").value =="" && document.all("tempfeeno").value == "")
	  {
	  	alert("��ѯ�������㣡����������Ͷ�����Ż������վݺ�/����Э����� �����޷��ṩ��������������������ѯ����");
	  	document.all("ContNo").focus();
	  	return;
	  	}
	//��������<���û��ѡ����������ʹ�õ�½ʱ�ĵ�½����>
	//if(trim(fm.ManageCom.value)=="")
	//{
	//	//alert("��½�������룺"+manageCom);
	//	fm.ManageCom.value=manageCom;
	//}

	if(document.all('ContNo').value!=""&&document.all('tempfeeno').value=="")
	{
		
		var sqlid1="ProposalQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	     var	strSQL=mySql1.getString();	
		
//	  var	strSQL = "select appflag from lccont where proposalcontno = '"+document.all('ContNo').value+"'";
	  arrResult1 = easyExecSql(strSQL,1,0);
	  if(arrResult1!=null&&arrResult1[0][0]=="1")
    	{
		   alert("��Ͷ�����Ѿ�ǩ��!");
		   return;
	  	}
	  else if(arrResult1==null) {
	  	 alert("�޴�Ͷ������Ϣ!");
	  	 return;
	  	}
	 }
	
	if(document.all('ContNo').value==""&&document.all('tempfeeno').value!="")
	{
		
		var sqlid2="ProposalQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(document.all('tempfeeno').value);//ָ������Ĳ���
	     var	strSQL=mySql2.getString();	
		
//	  var strSQL = "select appflag from lccont where contno = (select otherno from ljtempfee where tempfeeno = '"+document.all('tempfeeno').value+"' and rownum = '1')"	
	  arrResult1 = easyExecSql(strSQL,1,0);
	  if(arrResult1!=null&&arrResult1[0][0]=="1")
    	{
		   alert("��Ͷ�����Ѿ�ǩ��!");
		   return;
	  	}
	  else if(arrResult1==null) {
	  	 alert("�޴�Ͷ������Ϣ!");
	  	 return;
	  	}
	}

	if(document.all('ContNo').value!=""&&document.all('tempfeeno').value!="")
	{
		
		var sqlid3="ProposalQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('tempfeeno').value);//ָ������Ĳ���
	     var	strSQL=mySql3.getString();	
		
//		var strSQL = "select appflag from lccont where proposalcontno = '"+document.all('ContNo').value+"' and contno = (select otherno from ljtempfee where tempfeeno = '"+document.all('tempfeeno').value+"' and rownum = '1')"	
	  arrResult1 = easyExecSql(strSQL,1,0);
	  if(arrResult1!=null&&arrResult1[0][0]=="1")
    	{
		   alert("��Ͷ�����Ѿ�ǩ��!");
		   return;
	  	}
	  else if(arrResult1==null) {
	  	 alert("�޴�Ͷ������Ϣ!");
	  	 return;
	  	}
	}
	
	var strOperate="like";
	// ��дSQL���
	
		var sqlid4="ProposalQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('PrtNo').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('PolApplyDate').value);//ָ������Ĳ���
		
		mySql4.addSubPara(document.all('uwState').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('AgentCode').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('AppntIDNo').value);//ָ������Ĳ���
		
		mySql4.addSubPara(document.all('InsuredNo').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('InsuredIDNo').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('AppntName').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('InsuredName').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('uwgrade').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('tempfeeno').value);//ָ������Ĳ���
	
//	var strSql = "select l.ContNo,l.PrtNo,l.AppntName,l.insuredName,l.Operator,l.MakeDate,l.ApproveCode,l.ApproveDate,l.UWOperator,l.UWDate,l.UWFlag,(case UWFlag when '1' then '�ܱ�' "+
//                               " when '2' then '����' "+
//                               " when '4' then '�α�׼��' "+
//                               " when '9' then '��׼��' "+
//                               " when 'a' then '����' "+
//                               " when 'z' then '�˱�����' "+ 
//                               " end ),l.State,appntno from LCCont l where 1=1 and appflag='0'"
//    				+" and grpcontno='00000000000000000000'"
//    				+ getWherePart( 'l.ContNo','ContNo',strOperate)
//    				+ getWherePart( 'l.PrtNo','PrtNo',strOperate)
//    				+ getWherePart( 'l.ManageCom','ManageCom' , strOperate)
//    				+ getWherePart( 'l.PolApplyDate','PolApplyDate')
//    				+ getWherePart( 'l.UWFlag','uwState',strOperate )
//    				+ getWherePart( 'l.AgentCode','AgentCode',strOperate )
//    				+ getWherePart( 'l.AppntNo','AppntNo',strOperate)
//    				+ getWherePart( 'l.appntidno','AppntIDNo',strOperate)
//    				+ getWherePart( 'l.insuredno','InsuredNo',strOperate)
//    				+ getWherePart( 'l.insuredidno','InsuredIDNo',strOperate)
    				//alert(strSql);
    				
//   if(document.all('AppntName').value!="")
//   {
//   	strSql = strSql+" and l.AppntNo in (select CustomerNo from LDPerson where Name= '" + document.all('AppntName').value + "') ";
//   }
//   if(document.all('InsuredName').value!="")
//   {
//   	strSql = strSql+" and l.InsuredNo in (select CustomerNo from LDPerson where Name= '" + document.all('InsuredName').value + "') ";
//   }
//   if(document.all('uwgrade').value!="")
//    {
//    	strSql = strSql+" and exists (select 'x' from lwmission t where t.activityid in (select a.activityid from lwactivity a where a.functionid = '10010028') and trim(t.MissionProp2)=trim(l.ContNo) and t.MissionProp12 >= '"+document.all('uwgrade').value+"')";
//   	}
//   if(document.all('tempfeeno').value!="null"&&document.all('tempfeeno').value!="")
//    {
//    	strSql = strSql+" and exists (select 'x' from ljtempfee j where j.tempfeeno = '"+document.all('tempfeeno').value+"' and j.otherno = l.ContNo)";
//    	}
   var	strSql=mySql4.getString();
    			turnPage.strQueryResult = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޲�ѯ�����");
    return "";
  }
	turnPage.queryModal(strSql, PolGrid);
}

var mSwitch = parent.VD.gVSwitch;


function queryDetailClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cProposalContNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
  	var cprtno = PolGrid.getRowColData(checkFlag - 1, 2); 	
//  	mSwitch.deleteVar( "fm.value('PrtNo')" );
  	
//  	mSwitch.addVar( "fm.value('PrtNo')", "", cPrtNo);
  	 
    /*var strSql="";
   // strSql="select salechnl,cardflag from lccont where contno='"+cprtno+"'";
    strSql = "select case lmriskapp.riskprop"
           + " when 'I' then"
           + " '1'"
           + " when 'G' then"
           + " '2'"
           + " when 'Y' then"
           + " '3'"
           + " when 'T' then"
           + " '5'"
           + " end"
           + " from lmriskapp"
           + " where riskcode in (select riskcode"
           + " from lcpol"
           + " where polno = mainpolno"
           + " and contno = '"+cprtno+"')";
    arrResult = easyExecSql(strSql);
    var BankFlag = arrResult[0][0];
    //alert("a");
    */
	//ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cprtno.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
    
			var sqlid5="ProposalQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(cprtno);//ָ������Ĳ���
	    strSql=mySql5.getString();	
	
//    strSql="select salechnl,cardflag from lccont where contno='"+cprtno+"'";
    arrResult = easyExecSql(strSql);

    var CardFlag = arrResult[0][1]; 
    if(BankFlag==""||BankFlag==null||CardFlag=="3")
    BankFlag="4";       
  	 
    urlStr = "./ProposalEasyScan.jsp?LoadFlag=6&ContNo="+cProposalContNo+"&prtNo="+cprtno+"&BankFlag="+BankFlag+"&SubType=01" ,"status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";   
  
    window.open(urlStr,"",sFeatures);
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}


function queryMARiskClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cMainPolNo = PolGrid.getRowColData(tSel - 1,1);	
	    var cPrtNo = PolGrid.getRowColData(tSel - 1,2);			

		
		if (cMainPolNo == "")
		    return;
		    
		  window.open("./MainOddRiskQueryMain.jsp?MainPolNo=" + cMainPolNo + "&PrtNo=" + cPrtNo,"",sFeatures);										
	}
}

//ɨ�����ѯ
function ImageQuery() {
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var prtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (prtNo == "") return;
		    
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		window.open("../uw/ImageQueryMain.jsp?ContNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
	}	     
}
//�������ѯ
function PremQuery() {
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var prtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (prtNo == "") return;
	//	alert(prtNo);    
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		window.open("../sys/PolRiskQueryMain.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
	}	     
}


//����������ѯ
function OperationQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var PrtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (PrtNo == "" || ContNo == "") return;
			
	window.open("../sys/RecordQueryMain.jsp?ContType=1&ContNo="+ContNo+"&PrtNo="+PrtNo,"",sFeatures);	
  }										
} 

function InsuredQuery(){
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var PrtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (PrtNo == "" || ContNo == "") return;
			
	  window.open("ContInsuredQueryMain.jsp?prtNo="+PrtNo,"window1",sFeatures); 
  }										
	
	
}
function SpecQuery(){
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var PrtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (PrtNo == "" || ContNo == "") return;
			
	  window.open("../uw/UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&QueryFlag=1","window1",sFeatures);  	
  }										
	
	
}

//�����ۼ�
function amntAccumulate(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//�ѳб���ѯ
function queryProposal(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}


//δ�б���ѯ
function queryNotProposal(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//���������ѯ
function queryClaim(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//������ȫ��ѯ
function queryEdor(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }

	window.open("../uw/EdorQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//��ѯ�ݽ���
function showTempFee() 
{
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }
   var tPrtno =PolGrid.getRowColData(PolGrid.getSelNo() - 1,2);
	 var tAppntNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1,14);
	 var tAppntName = PolGrid.getRowColData(PolGrid.getSelNo() - 1,3);
   //alert (Prtno);
  window.open("../uw/UWTempFeeQryMain.jsp?PrtNo=" + tPrtno + "&AppntNo=" + tAppntNo + "&AppntName=" +tAppntName,"",sFeatures);
} 


//��ѯ�ݽ���
function statequery() 
{
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "����ѡ��һ����¼��" );
	  return;
  }
   var tProposalContno=PolGrid.getRowColData(PolGrid.getSelNo() - 1,1);
	 window.open("../uw/PolStatusMain.jsp?PrtNo="+tProposalContno,"window11",sFeatures);
}


//���PolGrid�����ĺ���

function clickPolGrid(){
  //���ư�ť������	
  ctrlButtonDisabled();
}


//���ƽ��水ť��������ʾ
function ctrlButtonDisabled(){
	var tContNo = PolGrid.getRowColData(PolGrid.getSelNo()-1,1);
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;
  
  		var sqlid6="ProposalQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tContNo);//ָ������Ĳ���
	    var strSql6=mySql6.getString();	
  
    	var sqlid7="ProposalQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(tContNo);//ָ������Ĳ���
	    var strSql7=mySql7.getString();	
  
      	var sqlid8="ProposalQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(tContNo);//ָ������Ĳ���
	    var strSql8=mySql8.getString();	
  
    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "Button1";
    arrButtonAndSQL[0][1] = "Ͷ�����ѳб�������ѯ";
    arrButtonAndSQL[0][2] = strSql6;//"select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('1', '4') and b.insuredno in (select appntno from lcappnt where contno = '"+tContNo+"')";

    arrButtonAndSQL[1] = new Array;
    arrButtonAndSQL[1][0] = "Button2";
    arrButtonAndSQL[1][1] = "Ͷ����δ�б�������ѯ";
    arrButtonAndSQL[1][2] = strSql7//"select * from lccont a, lcinsured b where a.contno = b.contno  and a.appflag  in('0','2') and b.insuredno in (select appntno from lcappnt where contno='"+tContNo+"')";

    arrButtonAndSQL[2] = new Array;
    arrButtonAndSQL[2][0] = "Button3";
    arrButtonAndSQL[2][1] = "Ͷ���˼�����ȫ��ѯ";
    arrButtonAndSQL[2][2] =strSql8;// "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno = (select appntno from lcappnt where contno='"+tContNo+"'))";

      	var sqlid9="ProposalQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(tContNo);//ָ������Ĳ���
		mySql9.addSubPara(tContNo);//ָ������Ĳ���
	    var strSql9=mySql9.getString();	

    arrButtonAndSQL[3] = new Array;
    arrButtonAndSQL[3][0] = "Button4";
    arrButtonAndSQL[3][1] = "Ͷ���˼��������ѯ";
    arrButtonAndSQL[3][2] =strSql9;// "select 1 from llregister a, llcase b where a.rgtno = b.caseno and b.CustomerNo in (select appntno from lcappnt where contno='"+tContNo+"') union select 1 from llreport a, llsubreport b, ldperson c where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' and b.CustomerNo in (select appntno from lcappnt where contno='"+tContNo+"')";

    arrButtonAndSQL[4] = new Array;
    arrButtonAndSQL[4][0] = "Button5";
    arrButtonAndSQL[4][1] = "Ͷ������ϸ";
    arrButtonAndSQL[4][2] = "";

      	var sqlid10="ProposalQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(tContNo);//ָ������Ĳ���
	    var strSql10=mySql10.getString();	

    arrButtonAndSQL[5] = new Array;
    arrButtonAndSQL[5][0] = "Button6";
    arrButtonAndSQL[5][1] = "Ͷ����Ӱ���ѯ";
    arrButtonAndSQL[5][2] =strSql10;// "select * from es_doc_relation where bussno='"+tContNo+"' and bussnotype='11' and rownum=1";

    arrButtonAndSQL[6] = new Array;
    arrButtonAndSQL[6][0] = "Button7";
    arrButtonAndSQL[6][1] = "�����˲�ѯ";
    arrButtonAndSQL[6][2] = "";

    arrButtonAndSQL[7] = new Array;
    arrButtonAndSQL[7][0] = "Button8";
    arrButtonAndSQL[7][1] = "�������ѯ";
    arrButtonAndSQL[7][2] = "";

    arrButtonAndSQL[8] = new Array;
    arrButtonAndSQL[8][0] = "Button9";
    arrButtonAndSQL[8][1] = "����������ѯ";
    arrButtonAndSQL[8][2] = "";

      	var sqlid11="ProposalQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(tContNo);//ָ������Ĳ���
	    var strSql11=mySql11.getString();	

    arrButtonAndSQL[9] = new Array;
    arrButtonAndSQL[9][0] = "Button10";
    arrButtonAndSQL[9][1] = "�ݽ��Ѳ�ѯ";
    arrButtonAndSQL[9][2] =strSql11;// "select * from ljtempfee where otherno='"+tContNo+"' and rownum=1";

    arrButtonAndSQL[10] = new Array;
    arrButtonAndSQL[10][0] = "Button11";
    arrButtonAndSQL[10][1] = "״̬��ѯ";
    arrButtonAndSQL[10][2] = "";
    

  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
      }
    }
    else{
      continue;
    }	
  }
  	
}

