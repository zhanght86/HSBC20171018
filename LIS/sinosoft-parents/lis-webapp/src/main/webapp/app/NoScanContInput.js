//�������ƣ�ScanContInput.js
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";


//�����롿
function ApplyInput()
{
	var cPrtNo = trim(SelfPoolQueryGrid.getRowColData(0,2));
	var cManageCom = trim(SelfPoolQueryGrid.getRowColData(0,1));
	
	
	if(cPrtNo == ""){
		alert("��¼��Ͷ�����ţ�");
		return;
	}
	//У�������������Ƿ�Ϊ���֣�
	if(isNaN(cPrtNo)){
		alert("Ͷ�����Ų����Ƿ�����!");return false;
	}
	//У�����������ݳ����Ƿ�Ϊ14λ!
	if(cPrtNo.length !=14){
		alert("Ͷ�����ű���Ϊ14λ���֣�");return false;
	}
	if(cManageCom == ""){
		alert("��¼����������");
		return;		
	}
	//if( verifyInput2() == false ) return false;
	
	indx = cManageCom.indexOf(comcode);
	if(indx != 0){
		alert("��û������˹��������Ȩ��");
		return;
	}
	/*if(type=='2'){//���ڼ��屣��������
		if (GrpbeforeSubmit1() == false){
			alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
			return false;		
		}
	}
	else*/ if(type=='1'){//���ڸ��˱���������
		if (beforeSubmit(cPrtNo) == false){
			alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
			return false;		
		}
	}/*else if(type=='5'){ 
		if (TempbeforeSubmit1() == false){
		    alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
		    return false;		
		}
	}*/
	document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	//showInfo.close();
	//alert(FlagStr);
  if(FlagStr == "Fail")
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
  jQuery("#privateSearch").click();
}

function search1()
{
	var tPolProperty = fm.PolProperty.value;
	var mission1="";
	var kindSql="";
	if(tPolProperty!=null||tPolProperty!=""){
		if(trim(tPolProperty)==11){
			mission1="8611";
		}
		if(trim(tPolProperty)==16){
			mission1="8691";
		}
		if(trim(tPolProperty)==21){
			mission1="8671";
		}
	}
	if(mission1!=""){
		kindSql=" and missionprop1 like '"+mission1+"%%'";
	}
	if(trim(tPolProperty)==35){
		kindSql=" and missionprop1 like '8635%%' "
		//" or missionprop1 like '8625%%' or missionprop1 like '8615%%'";
	}
	
	//��������<���û��ѡ����������ʹ�õ�½ʱ�ĵ�½����>
	if(trim(fm.ManageCom.value)==""){
		fm.ManageCom.value=manageCom;
	}

	var strSQL = "";
	var strOperate="like";
	if(type=='1'){
		var tempfeeSQL="";//�����ƴ�� �������� ��ѯ������

		var sqlid77="ContPolinputSql77";
		var mySql77=new SqlClass();
		mySql77.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql77.setSqlId(sqlid77);//ָ��ʹ�õ�Sql��id
		mySql77.addSubPara(operator);//ָ������Ĳ���
		mySql77.addSubPara(kindSql);//ָ������Ĳ���
		mySql77.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		
		mySql77.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql77.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql77.addSubPara(fm.Operator.value);//ָ������Ĳ���
		
		mySql77.addSubPara(trim(comcode));//ָ������Ĳ���
		mySql77.addSubPara(tempfeeSQL);//ָ������Ĳ���
	    strSQL=mySql77.getString();	
	}
	else if (type=='2')
 	{
		var sqlid78="ContPolinputSql78";
		var mySql78=new SqlClass();
		mySql78.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql78.setSqlId(sqlid78);//ָ��ʹ�õ�Sql��id
		mySql78.addSubPara(operator);//ָ������Ĳ���
		mySql78.addSubPara(kindSql);//ָ������Ĳ���
		mySql78.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		
		mySql78.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql78.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql78.addSubPara(fm.Operator.value);//ָ������Ĳ���
		
		mySql78.addSubPara(trim(comcode));//ָ������Ĳ���
	    strSQL=mySql78.getString();	
	}
	else if(type=='5'){
		var sqlid79="ContPolinputSql79";
		var mySql79=new SqlClass();
		mySql79.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql79.setSqlId(sqlid79);//ָ��ʹ�õ�Sql��id
		mySql79.addSubPara(operator);//ָ������Ĳ���
		mySql79.addSubPara(kindSql);//ָ������Ĳ���
		mySql79.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		
		mySql79.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql79.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql79.addSubPara(fm.Operator.value);//ָ������Ĳ���
		
	    strSQL=mySql79.getString();	
	}
	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

function easyQueryClick1()
{
	// ��дSQL���
	var strSQL = "";
	var strOperate="like";
	if(type=='1')
	{
		
		var sqlid80="ContPolinputSql80";
		var mySql80=new SqlClass();
		mySql80.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql80.setSqlId(sqlid80);//ָ��ʹ�õ�Sql��id

		mySql80.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql80.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql80.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql80.addSubPara(trim(comcode));//ָ������Ĳ���
		
	    strSQL=mySql80.getString();	
	}
	else if (type=='2')
 	{
		
		var sqlid81="ContPolinputSql81";
		var mySql81=new SqlClass();
		mySql81.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql81.setSqlId(sqlid81);//ָ��ʹ�õ�Sql��id

		mySql81.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql81.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql81.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql81.addSubPara(trim(comcode));//ָ������Ĳ���
		
	    strSQL=mySql81.getString();	
	}
	else if(type=='5')
	{
		
		var sqlid82="ContPolinputSql82";
		var mySql82=new SqlClass();
		mySql82.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql82.setSqlId(sqlid82);//ָ��ʹ�õ�Sql��id

        mySql82.addSubPara(operator);//ָ������Ĳ���
		mySql82.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql82.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql82.addSubPara(fm.Operator.value);//ָ������Ĳ���
		
	    strSQL=mySql82.getString();	
	}

	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

//����ʼ¼�롿
function GoToInput(){
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<SelfPoolQueryGrid.mulLineCount; i++) {
    if (SelfPoolGrid.getSelNo(i)) { 
      checkFlag = SelfPoolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
    var	prtNo = SelfPoolGrid.getRowColData(checkFlag - 1, 1); 	
    var ManageCom = SelfPoolGrid.getRowColData(checkFlag - 1, 2); 
    var PolApplyDate=SelfPoolGrid.getRowColData(checkFlag-1,3);
    var MissionID = SelfPoolGrid.getRowColData(checkFlag - 1, 6);
    var SubMissionID = SelfPoolGrid.getRowColData(checkFlag - 1, 7);
    var ActivityID = SelfPoolGrid.getRowColData(checkFlag - 1, 9);
	  var NoType = type;
	  
    var strReturn="1";
    if (strReturn == "1") 
     if(type=='1')
     {    		
	 
    	 var sqlid83="ContPolinputSql83";
		var mySql83=new SqlClass();
		mySql83.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql83.setSqlId(sqlid83);//ָ��ʹ�õ�Sql��id

        mySql83.addSubPara(prtNo);//ָ������Ĳ���
	    sql=mySql83.getString();	
	 
     	  //sql = "select missionprop5 from lwmission where activityid = '0000001098' and missionprop1 = '"+prtNo+"'";
    		turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("ɨ���ϴ�����");
    			return;
    		}
    		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);     
         if(turnPage.arrDataCacheSet[0][0]=='05')//����¼��
    		{
    			window.open("./BankContInputNoScanMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);        
    		}else
    		{
    			window.open("./ContInputNoScanMain.jsp?ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);        
    		}
    }
    else if(type=='2')
    {
     	window.open("./ContPolInputNoScanMain.jsp?ScanFlag=0&prtNo="+prtNo+"&PolApplyDate="+PolApplyDate+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);
    }	
    else if(type=='5')
    {
     	window.open("./FirstTrialMain.jsp?prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID, "", sFeatures);
    }	
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
  
}
function beforeSubmit()
{
	var strSQL = "";
	
		 		var sqlid84="ContPolinputSql84";
		var mySql84=new SqlClass();
		mySql84.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql84.setSqlId(sqlid84);//ָ��ʹ�õ�Sql��id

        mySql84.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql84.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}
	
		var sqlid85="ContPolinputSql85";
		var mySql85=new SqlClass();
		mySql85.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql85.setSqlId(sqlid85);//ָ��ʹ�õ�Sql��id

        mySql85.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql85.getString();	
	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
   		var sqlid86="ContPolinputSql86";
		var mySql86=new SqlClass();
		mySql86.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql86.setSqlId(sqlid86);//ָ��ʹ�õ�Sql��id

        mySql86.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
         mySql86.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
         
	    strSQL=mySql86.getString();	
   
//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}	   	
}
function GrpbeforeSubmit()
{
	var strSQL = "";
	
	   		var sqlid87="ContPolinputSql87";
		var mySql87=new SqlClass();
		mySql87.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql87.setSqlId(sqlid87);//ָ��ʹ�õ�Sql��id

        mySql87.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
         
	    strSQL=mySql87.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		  //alert('1');
	    return false;
	}

	   	var sqlid88="ContPolinputSql88";
		var mySql88=new SqlClass();
		mySql88.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql88.setSqlId(sqlid88);//ָ��ʹ�õ�Sql��id

        mySql88.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
        mySql88.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql88.getString();	


//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		  //alert('2');
	    return false;
	}	   	
}
//����Ͷ������ʾ��ʽ
function initTr()
{
	 if(type=='1' || type=='2')
	 {
	 	 document.all['SubTitle'].style.display = '';
	 }
	 else if(type=='5')
	 {
	 	document.all['SubTitle'].style.display = 'none';
	 }
}
function TempbeforeSubmit()
{
	var strSQL = "";
	
		 var sqlid89="ContPolinputSql89";
		var mySql89=new SqlClass();
		mySql89.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql89.setSqlId(sqlid89);//ָ��ʹ�õ�Sql��id

        mySql89.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql89.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}

		 var sqlid90="ContPolinputSql90";
		var mySql90=new SqlClass();
		mySql90.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql90.setSqlId(sqlid90);//ָ��ʹ�õ�Sql��id

        mySql90.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
        mySql90.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql90.getString();	


//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}	 	 
}

function showNotePad()
{

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = GrpGrid.getRowColData(selno, 5);
	var SubMissionID = GrpGrid.getRowColData(selno, 6);
	var ActivityID = GrpGrid.getRowColData(selno, 7);
	var PrtNo = GrpGrid.getRowColData(selno, 1);
	var NoType = type;
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}

function assignPrtNo(param){
	fm.PrtNo.value = SelfPoolQueryGrid.getRowColData(0,2);
}

function afterCodeSelect( cCodeName, Field ){
	if (cCodeName=="station"){
		fm.ManageCom.value = SelfPoolQueryGrid.getRowColData(0,1);
	}
	if(cCodeName == "polproperty"){
		fm.ContType.value = SelfPoolQueryGrid.getRowColData(0,4);
	}
}


