//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""	// "1--¼�룬"2"--��ѯ
var mGrpFlag = ""; 	//���˼����־,"0"��ʾ����,"1"��ʾ����.
var cflag = "WB";        //�ļ���¼��λ��

var arrGrpRisk = null;
var turnPage = new turnPageClass();
window.onfocus = myonfocus;
var hiddenBankInfo = "";
 

/*********************************************************************
 *  ����LoadFlag����һЩFlag����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function convertFlag( cFlag )
{
  //alert("cFlag:" + cFlag);
	if( cFlag == "1" || cFlag == "99" || cFlag == "8")		// ����Ͷ����ֱ��¼��
	{
		mCurOperateFlag = "1";
		mGrpFlag = "0";
	}
	if( cFlag == "2" || cFlag == "7" || cFlag == "9")		// �����¸���Ͷ����¼��
	{
		mCurOperateFlag = "1";
		mGrpFlag = "1";
	}
	if( cFlag == "3" )		// ����Ͷ������ϸ��ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "0";
	}
	if( cFlag == "4" )		// �����¸���Ͷ������ϸ��ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "1";
	}
	if( cFlag == "5" )		// ����Ͷ�������˲�ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "3";
	}
}

 
/**
 * �������֤�����ȡ��������
 */
function grpGetBirthdayByIdno() {
  var id = document.all("IDNo").value;
  
  if (document.all("IDType").value == "0") {
    if (id.length == 15) {
      id = id.substring(6, 12);
      id = "19" + id; 
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      document.all("Birthday").value = id; 
    } 
    else if (id.length == 18) {
      id = id.substring(6, 14);
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      document.all("Birthday").value = id; 
    }     
  }
}

/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm()
{
	//var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
	
	  var sqlid1="WbProposalInputSql1";
	  var mySql1=new SqlClass();
	  var PrtNo1 = fm.PrtNo.value;
	  mySql1.setResourceName("app.WbProposalInputSql");
	  mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	  mySql1.addSubPara(PrtNo1);//ָ���������
	  var sSQL = mySql1.getString();
	  
	  var arrCount=easyExecSql(sSQL); 
  if(fm.DealType.value != "03" && arrCount==null)
  {
   	alert("��δ����ɹ�,������������ [�������] ȷ�ϣ�");
   	return;
  }
	fm.WorkFlowFlag.value = ActivityID ;				
	fm.MissionID.value = MissionID;
	fm.SubMissionID.value = SubMissionID;
	fm.ProposalContNo.value = prtNo;
	
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	//�Զ��ø���״̬
  var strDate = Date.parse(new Date());
  var urlStr1 = "./ProposalAutoApprove.jsp?prtNo=" + prtNo + "&DealType="+fm.DealType.value+"&strDate="+strDate;
  var sFeatures = "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable=0";
  //var strResult = window.showModalDialog(urlStr1, "", sFeatures);  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	var strResult = window.open (urlStr1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	strResult.focus();
	lockScreen('lkscreen');  
	fm.action = "./BPOInputConfirm.jsp";
    document.getElementById("fm").submit(); //�ύ
} 

//�������ѯ
function QuestQuery()
{
	var tProposalNo = document.all('ProposalNo').value;  //Ͷ��������
	if(tProposalNo == "" || tProposalNo=="null")
	{
	    alert("���ȱ���Ͷ����!");
	}
	else
	{
	    window.open("../uw/QuestQueryMain.jsp?ContNo="+prtNo+"&Flag=1","window1");
	}
}
 
function showSaleChnl() {
  showCodeList('SaleChnl',[this]);
}

//��ѯ�н�Ͷ�����Ĵ������
function showAgentSaleChnl() {
   var tRiskSql = "1 and OtherSign =#21# ";
   //alert("tRiskSql: "+tRiskSql);
   showCodeList('SaleChnl',[this],null,null,tRiskSql,'1',1);
}

function quaryagentgroup()
{
	var tAgentCode = document.all('AgentCode').value;
	
	if( tAgentCode!=null && tAgentCode!="" )
	{
		//var sql="select agentgroup from laagent where agentcode='"+tAgentCode+"'";
		
		  var sqlid2="WbProposalInputSql2";
		  var mySql2=new SqlClass();
		  mySql2.setResourceName("app.WbProposalInputSql");
		  mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
		  mySql2.addSubPara(tAgentCode);//ָ���������
		  var sql = mySql2.getString();
		 
		  var tResult = easyExecSql(sql, 1, 1, 1); 
		if(tResult!=null)
		{
			document.all('AgentGroup').value=tResult[0][0];
		}
	}	
} 

//У��Ͷ������
function checkapplydate() 
{
	if(trim(fm.PolApplyDate.value)==""){return ;}
    else if (fm.PolApplyDate.value.length == 8)
	{
		if(fm.PolApplyDate.value.indexOf('-')==-1)
		{
			var Year =     fm.PolApplyDate.value.substring(0,4);
			var Month =    fm.PolApplyDate.value.substring(4,6);
			var Day =      fm.PolApplyDate.value.substring(6,8);
			fm.PolApplyDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("�������Ͷ����������!");
			fm.PolApplyDate.value = "";
			fm.PolApplyDate.focus();
			return;
			}
		}else{
			alert("�������Ͷ�����ڸ�ʽ����!");
			fm.PolApplyDate.value = "";
			fm.PolApplyDate.focus();
			return;
		}
	}
	else if(fm.PolApplyDate.value.length == 10)
	{
		var Year =     fm.PolApplyDate.value.substring(0,4);
		var Month =    fm.PolApplyDate.value.substring(5,7);
		var Day =      fm.PolApplyDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ����������!");
		fm.PolApplyDate.value = "";
		fm.PolApplyDate.focus();
		return;
		}
	}
	else
	{
		alert("�������Ͷ����������!");
        fm.PolApplyDate.value = "";
        fm.PolApplyDate.focus();
        return;
	}
}
 
function quaryOccupationType()
{
	
	//alert("1");
	//Ͷ����
	if(document.all('AppntOccupationCode').value!=null && document.all('AppntOccupationCode').value!="")
	{
		//var Sql="select  OccupationType,OccupationName from LDOccupation where 1=1 "
	//	+"and occupationcode='"+document.all('AppntOccupationCode').value+"' ";
		
		  var sqlid3="WbProposalInputSql3";
		  var mySql3=new SqlClass();
		  var AppntOccupationCode3 = document.all('AppntOccupationCode').value;
		  mySql3.setResourceName("app.WbProposalInputSql");
		  mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
		  mySql3.addSubPara(AppntOccupationCode3);//ָ���������
		  var Sql = mySql3.getString();
		  
		  var tResult = easyExecSql(Sql, 1, 1, 1); 
	
		if(tResult!=null)
	    {                
		  document.all('AppntOccupationType').value=tResult[0][0];
		  //document.all('AppntOccupationCodeName').value=tResult[0][1];
		 }
     }
  //������
  for (var i=1 ;i<=3; i++)
  {
  		if(document.all('OccupationCode'+i).value!=null && document.all('OccupationCode'+i).value!="")
		{
//			 var Sql1="select  OccupationType,OccupationName from LDOccupation where 1=1 "
//			 +"and occupationcode='"+document.all('OccupationCode'+i).value+"' ";
			 
			  var sqlid4="WbProposalInputSql4";
			  var mySql4=new SqlClass();
			  var OccupationCode4 = document.all('OccupationCode'+i).value;
			  mySql4.setResourceName("app.WbProposalInputSql");
			  mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
			  mySql4.addSubPara(OccupationCode4);//ָ���������
			  var Sql1 = mySql4.getString();
			 
			  var tResult1 = easyExecSql(Sql1, 1, 1, 1); 
	  
		   if(tResult1!=null)
		   {
		  	document.all('OccupationType'+i).value=tResult1[0][0];
		  	//document.all('OccupationCodeName'+i).value=tResult[0][1];
		   }
		}
  }  
}

//���¼��Ĺ�������Ƿ���ȷ
function checkInputSalechnl()
{
  var arrSaleChnl = new Array(); 
 // var sql = "select 1 from ldcode where codetype = 'salechnl' and code='"+document.all("SaleChnl").value+"'"; 
  
  var sqlid5="WbProposalInputSql5";
  var mySql5=new SqlClass();
  var SaleChnl5 = document.all("SaleChnl").value;
  mySql5.setResourceName("app.WbProposalInputSql");
  mySql5.setSqlId(sqlid5);//ָ��ʹ��SQL��id
  mySql5.addSubPara(SaleChnl5);//ָ���������
  var sql = mySql5.getString();
  
  arrSaleChnl = easyExecSql( sql , 1, 0);
  //alert(arrSaleChnl);	
  if (arrSaleChnl == null)	
  {
   alert("��������¼�����!");
   return false
  }
  	
  return true;	
}


/*********************************************************************
 *  У��Ͷ����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function verifyProposal() {
  var passVerify = true;
  var tflag = false; 
 
//  if(fm.AppntRelationToInsured.value=="00"){
//    if(fm.AppntCustomerNo.value!= fm.CustomerNo.value){		
//      alert("Ͷ�����뱻���˹�ϵΪ���ˣ����ͻ��Ų�һ��");
//      return false;
//    }
//  }  
  ////У��¼��������Ƿ���ҪУ�飬�����Ҫ����true,���򷵻�false
//   if(needVerifyRiskcode("NotVerifyRiskcode")==true)
//   {
		if(verifyInput() == false) passVerify = false;
//		
//		BnfGrid.delBlankLine();
//		
//		if(BnfGrid.checkValue("BnfGrid") == false) passVerify = false;
//		
//		 //У�鵥֤�Ƿ񷢷Ÿ�ҵ��Ա
//		 //if (!verifyPrtNo(document.all("PrtNo").value)) passVerify = false;
//    }
	
	try {
	  var strChkIdNo = "";
    	
		  //��������Ա�У�����֤��
		  if (document.all("AppntIDType").value == "0") 
		    strChkIdNo = chkIdNo(document.all("AppntIDNo").value, document.all("AppntBirthday").value, document.all("AppntSex").value);
		  if (document.all("IDType").value == "0") 
		    strChkIdNo = chkIdNo(document.all("IDNo").value, document.all("Birthday").value, document.all("Sex").value);
		    
		  if (strChkIdNo != "") {
		    alert(strChkIdNo);
		    passVerify = false; 
	  	} 
	   
	  //У��ְҵ��ְҵ����
//	  var arrCode = new Array();
//	  arrCode = verifyCode("ְҵ�����֣�", document.all("AppntWorkType").value, "code:OccupationCode", 1); 
//	  if (arrCode!=true && document.all("AppntOccupationCode").value!=arrCode[0]) {
//	    alert("Ͷ����ְҵ��ְҵ���벻ƥ�䣡");
//	    passVerify = false;
//	  }
//	  arrCode = verifyCode("ְҵ�����֣�", document.all("WorkType").value, "code:OccupationCode", 1); 
//	  if (arrCode!=true && document.all("OccupationCode").value!=arrCode[0]) {
//	    alert("������ְҵ��ְҵ���벻ƥ�䣡");
//	    passVerify = false;
//	  }
	  var occupation =  document.all("AppntOccupationCode").value;
	  if(occupation != null && occupation != "")
	  {
//  	  var tsql = "select * from LDOccupation where OccupationVer='002' and OccupationCode = '" + occupation + "' " ;
	  	
	  	  var sqlid6="WbProposalInputSql6";
	  	  var mySql6=new SqlClass();
	  	  mySql6.setResourceName("app.WbProposalInputSql");
	  	  mySql6.setSqlId(sqlid6);//ָ��ʹ��SQL��id
	  	  mySql6.addSubPara(occupation);//ָ���������
	  	  var tsql = mySql6.getString();
	  	 
		  tResult = decodeEasyQueryResult(easyQueryVer3(tsql));
			if(tResult == null || tResult == "")
			{
				alert("¼���ְҵ���벻�����ݿⶨ��ķ�Χ��");
				return false;
			}  
	  }
	    
	  //У���������
	  var i;
	  var sumLiveBnf = new Array();
	  var sumDeadBnf = new Array();
    	  var bnfIDNo ;
	  for (i=0; i<BnfGrid.mulLineCount; i++) {
	    if (BnfGrid.getRowColData(i, 1) == "0") {
	      if (typeof(sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined") 
	        sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
	      sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 6));
	    }
	    else if (BnfGrid.getRowColData(i, 1) == "1") {
	      if (typeof(sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined") 
	        sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
	      sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 6));
	    }
	    //У�����������֤����
	    if (BnfGrid.getRowColData(i, 3) == "0") 
	    {
	    	bnfIDNo = trim(BnfGrid.getRowColData(i, 4));
				if ((bnfIDNo.length!=15) && (bnfIDNo.length!=18))
				{
				  alert("�����˵����֤��"+bnfIDNo+"λ������!");
			    passVerify = false;
				}
	    }
	  }
	  
	  for (i=0; i<sumLiveBnf.length; i++) {
  	  if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1) {
  	    alert("��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i] + " ������100%�������ύ��");
  	    passVerify = false; 
  	  }
  	  else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1) {
  	    alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i] + " ��С��100%");
  	    passVerify = false;
  	  }
	  } 
	  
	  for (i=0; i<sumDeadBnf.length; i++) {
  	  if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1) {
  	    alert("��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ������100%�������ύ��");
  	    passVerify = false; 
  	  }
  	  else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1) {
  	    alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ��С��100%");
  	    passVerify = false;
  	  }
	  } 
	  
	  if (trim(fm.BankCode.value)=="0101") {
      if (trim(fm.BankAccNo.value).length!=19 || !isInteger(trim(fm.BankAccNo.value))) {
        alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��\n����ͻ���д������һ�����������");
        passVerify = false;
      }
    }
    
    //У��ͻ��Ƿ�����
    if (fm.AppntCustomerNo.value!="" && isDeath(fm.AppntCustomerNo.value)) {
      alert("Ͷ�����Ѿ�������");
      passVerify = false;
    }
    
    if (fm.CustomerNo.value!="" && isDeath(fm.CustomerNo.value)) {
      alert("�������Ѿ�������");
      passVerify = false;
    }
	}
	catch(e) {}
	
	if (!passVerify) {
	  if (!confirm("Ͷ����¼������д����Ƿ�������棿")) return false;
	  else return true;
	}
}

//У��ͻ��Ƿ�����
function isDeath(CustomerNo) {
  //var strSql = "select DeathDate from LDPerson where CustomerNo='" + CustomerNo + "'";
  
  var sqlid7="WbProposalInputSql7";
  var mySql7=new SqlClass();
  mySql7.setResourceName("app.WbProposalInputSql");
  mySql7.setSqlId(sqlid7);//ָ��ʹ��SQL��id
  mySql7.addSubPara(CustomerNo);//ָ���������
  var strSql = mySql7.getString();
 
  var arrResult = easyExecSql(strSql);
  
  if (arrResult == ""||arrResult == null) return false;
  else return true;
}

// У������
function verifyRisk()
{	
	var tPrtNo = fm.PrtNo.value;
	var tPayMode = fm.NewPayMode.value;
	var tPayLocation = fm.PayLocation.value;
	var tPayIntv = fm.PayIntv.value;
	var tPrem = 0;
	var tpayendyear = 0;
	var allprem =0;
	var checkscanx =0;
	var checkscanr =0;
	for(var m=1;m<=3;m++)
	{
		var StrInsur = "";
		if(m==1)
			StrInsur = "������";
		else
			StrInsur = "��"+m+"������";
	    var InsurAge = calAgeNew(document.all("Birthday"+m).value,document.all("PolApplyDate").value);
		for(var n=1;n<=3;n++)
		{
			var arrReturn = new Array();
			var tSel ;
			eval("tSel = Risk"+m+n+"Grid.mulLineCount");
			var riskcode ="";
			var insuYear = "";
			var insuYearFlag = "";
			var prem ="";
		    var addprem ="";		    
			var sql ="";
			for(var i=0;i< tSel;i++)
			{			
			    eval("riskcode = Risk"+m+n+"Grid.getRowColData("+i+", 3)");
			    eval("insuYear = Risk"+m+n+"Grid.getRowColData("+i+", 7)");
			    eval("insuYearFlag = Risk"+m+n+"Grid.getRowColData("+i+", 8)");
			    if( riskcode == "312204" && insuYear == 5 && insuYearFlag =="Y")
			    {
			        if (!confirm(StrInsur+"������"+riskcode+"5���ڲ�Ʒ��ͣ�ۣ��볷�����Ƿ�������棿")) return false;
			    }	
			    if( riskcode == "121501" )//|| riskcode == "112208"
			    {
			        if (!confirm(StrInsur+"������"+riskcode+"��ֹͣ���ۣ��볷�����Ƿ�������棿")) return false;
			    }			    
//			    sql = "select sysvarvalue from ldsysvar where sysvar ='tbcheckscandate'";
//			    sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
			    
			    var sqlid8="WbProposalInputSql8";
			    var mySql8=new SqlClass();
			    mySql8.setResourceName("app.WbProposalInputSql");
			    mySql8.setSqlId(sqlid8);//ָ��ʹ��SQL��id
			    sql = mySql8.getString();
			    
  				var arrResult2 = easyExecSql(sql);				
			    if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
			    {
			    	// У�����Ͳ�Ʒ˵����
	//		    	sql="select count(1) from lmriskapp where RiskType3 in ('2','4') and riskcode='"+riskcode+"'";
			    	
			    	  var sqlid9="WbProposalInputSql9";
			    	  var mySql9=new SqlClass();
			    	  mySql9.setResourceName("app.WbProposalInputSql");
			    	  mySql9.setSqlId(sqlid9);//ָ��ʹ��SQL��id
			    	  mySql9.addSubPara(riskcode);//ָ���������
			    	  sql = mySql9.getString();
			    	
			          var arrResult = easyExecSql(sql);
	//		        sql="select count(1) from es_doc_main where doccode='"+fm.PrtNo.value+"' and subtype='UR211'";
			        
			          var sqlid10="WbProposalInputSql10";
			    	  var mySql10=new SqlClass();
			    	  var PrtNo10 = fm.PrtNo.value;
			    	  mySql10.setResourceName("app.WbProposalInputSql");
			    	  mySql10.setSqlId(sqlid10);//ָ��ʹ��SQL��id
			    	  mySql10.addSubPara(PrtNo10);//ָ���������
			    	  sql = mySql10.getString();
			    	 
				    var arrResult1 = easyExecSql(sql);
				    
			   		if (arrResult!=null && arrResult1!=null && arrResult[0][0]!='0'&& arrResult1[0][0]=='0')
			   		{
			   		    checkscanx =1;			   		    
			   		}
			   		
			   		//У��������Ͷ����ʾ
	//		   		sql="select count(1) from es_doc_main where doccode='"+fm.PrtNo.value+"' and subtype='UR200'";
			   		
			   		  var sqlid11="WbProposalInputSql11";
			    	  var mySql11=new SqlClass();
			    	  var PrtNo11 = fm.PrtNo.value;
			    	  mySql11.setResourceName("app.WbProposalInputSql");
			    	  mySql11.setSqlId(sqlid11);//ָ��ʹ��SQL��id
			    	  mySql11.addSubPara(PrtNo11);//ָ���������
			    	  sql = mySql11.getString();
				      var arrResult4 = easyExecSql(sql);
			   		if(arrResult4!=null && arrResult4[0][0]=='0' &&((parseInt(InsurAge+1)<insuYear && insuYearFlag=='A') || (1<insuYear && insuYearFlag=='Y')))
			   		{
			   			checkscanr =1;			   			
			   		}
			    }			    		        
		        
		        eval("prem = Risk"+m+n+"Grid.getRowColData("+i+", 11)");	
		        eval("addprem = Risk"+m+n+"Grid.getRowColData("+i+", 12)");
		        if(prem!=null && prem!='')
		        	allprem =  parseFloat(allprem) + parseFloat(prem);
		        if(addprem!=null && addprem!='')
		        	allprem =  parseFloat(allprem) + parseFloat(addprem);//�����ӷ�
		        
		        //2010-3-12 ln add �����֤��ɨ�������У�� ȡ��������	
		        if(prem==null || prem=='')
		        	prem='0';
		        if(addprem==null || addprem=='')
		        	addprem='0';
		        eval("payendyear = Risk"+m+n+"Grid.getRowColData("+i+", 9)");	
		        eval("payendyearflag = Risk"+m+n+"Grid.getRowColData("+i+", 10)");		        
		        if(tPayIntv!=null && tPayIntv!='0')//�ڽ�
		        {
		//        	sql="select subriskflag,RiskPeriod from lmriskapp where riskcode='"+riskcode+"'";
		        	
		        	  var sqlid12="WbProposalInputSql12";
			    	  var mySql12=new SqlClass();
			    	  mySql12.setResourceName("app.WbProposalInputSql");
			    	  mySql12.setSqlId(sqlid12);//ָ��ʹ��SQL��id
			    	  mySql12.addSubPara(riskcode);//ָ���������
			    	  sql = mySql12.getString();
			        var subriskflag = easyExecSql(sql);
			        if(subriskflag[0][0]=='S' && subriskflag[0][1]!='L')//��Ϊ���ڸ����� ���ѷ�ʽΪ����
			        {
			        	tPrem =  parseFloat(tPrem) + parseFloat(prem) + parseFloat(addprem);
			        }
			        else
			        {			        	
			        	if(payendyearflag=='Y')
			        		tpayendyear = payendyear;
			        	else
			        		tpayendyear = parseInt(payendyear) -  InsurAge;
			        	if(tPayIntv=='1')
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*12*tpayendyear;
			        	else if(tPayIntv=='3')
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*4*tpayendyear;
			        	else if(tPayIntv=='6')
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*2*tpayendyear;
			        	else
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*tpayendyear;			        		
			        }
		        }
		        else//����
		        	tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem));
			}
			
		}
	}
	if(checkscanx==1)
		if (!confirm("���ڷֺ������ֻ����������֣��ޡ����Ͳ�Ʒ˵���顷���Ƿ�������棿")) return false;
	if(checkscanr==1)
		if (!confirm("Ͷ��������ϸ�ṩ����ȫ���ޡ�������Ͷ����ʾ�����Ƿ�������棿")) return false;
	
	// 2009-3-5 ln add �Ը��պ��н���������ڽ�����ʽУ��
				if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) == "11" || tPrtNo.substring(2,4) == "21"))
				{    
					//alert(allprem);
					if((parseFloat(allprem) - 1000) >0)
				   	{	 		
				   		if((tPayMode!=null && tPayMode!="" && (tPayMode=="0" || tPayMode=="5" || tPayMode=="b"))
					        			  && (tPayLocation!=null && tPayLocation!="" && (tPayLocation=="0" || tPayLocation=="4" || tPayLocation=="5" || tPayLocation=="b")))
					    {    		  
					    }
					    else
					    {
					        if (!confirm("�ס����ڽ���ӦΪ����ת�ˡ��ͻ��Խ����������Ƿ�������棿")) return false;
					    }
				   	}
				}
				
	//2010-3-12 ln add �����֤��ɨ�������У��
//				sql = "select sysvarvalue from ldsysvar where sysvar ='tbcheckhaveiddate'";
//				sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
				
				 var sqlid13="WbProposalInputSql13";
		    	  var mySql13=new SqlClass();
		    	  mySql13.setResourceName("app.WbProposalInputSql");
		    	  mySql13.setSqlId(sqlid13);//ָ��ʹ��SQL��id
		    	  sql = mySql13.getString();
  				var checkdate = easyExecSql(sql);		//alert(checkdate);
 //				sql="select count(1) from es_doc_main where doccode='"+fm.PrtNo.value+"' and subtype='UR208'";
  				
  				 var sqlid14="WbProposalInputSql14";
		    	  var mySql14=new SqlClass();
		    	  var PrtNo14 = fm.PrtNo.value;
		    	  mySql14.setResourceName("app.WbProposalInputSql");
		    	  mySql14.setSqlId(sqlid14);//ָ��ʹ��SQL��id
		    	  mySql14.addSubPara(PrtNo14);//ָ���������
		    	  sql = mySql14.getString();
				var havescan = easyExecSql(sql);		
			    if(checkdate!=null && (calAgeNew(checkdate[0][0],document.all("PolApplyDate").value)>=0)&& havescan!=null && havescan[0][0]=='0' )
			    {
			    	var cPrem = 0;			    	
			    	if(tPayIntv!=null && tPayIntv!='0')//�ڽ�
			    	{
			    		if(tPayMode!=null && tPayMode=='0'&& tPayLocation!=null && tPayLocation=='0')
			    			cPrem = 200000;
			    	    else
			    	    	cPrem = 20000;
			    	}
			    	else//����
			    	{
			    		if(tPayMode!=null && tPayMode=='0')
			    			cPrem = 200000;
			    	    else
			    	    	cPrem = 20000;
			    	}
			        if(tPrem-cPrem>=0)
			        	if (!confirm("�����Ϸ�ϴǮʵʩϸ��涨��δ�ṩ�ͻ���Ч֤����ӡ�����Ƿ�������棿")) return false;  	
				}
				
	return true;
	
}

// У���ѡ��֪�Ƿ�¼��
function verifyImpart()
{
	var tPrtNo = fm.PrtNo.value;
	var InsuSequenceNo = fm.SequenceNo1.value;
	// 2009-3-5 ln add �Ը��պ��н���б�ѡ��֪¼�����У��
	/*if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) == "11" || tPrtNo.substring(2,4) == "21"))
	{  		
		var tSel = ImpartGrid.mulLineCount;
		var impartver ="";
		var impartcode = "";
		var customernotype = "";
		var insuredno ="";	
		var count1 = 0;
		var count2 = 0;
		var count3 = 0;
		var count4 = 0;    
		for(var i=0;i< tSel;i++)
		{			
			impartver = ImpartGrid.getRowColData(i, 1);
			impartcode = ImpartGrid.getRowColData(i, 2);
			customernotype = ImpartGrid.getRowColData(i, 5);
			insuredno = ImpartGrid.getRowColData(i, 6);
			if( impartcode!=null && impartcode == "A0101" )
			{
			    	count1 = 1;
			}	
			if( impartcode!=null && impartcode == "A0120" )
			{
			    count2 = 1;
			}	
			if( impartcode!=null && impartcode == "A0152" )
			{
			    count3 = 1;
			}
			if( impartcode!=null && impartcode == "A0155" )
			{
			    count4 = 1;
			}	
		}
		if(count1 != 1)
			if (!confirm("û��¼���֪(����:A0101)���Ƿ�������棿")) return false;
		if(count2 != 1)
			if (!confirm("û��¼���֪(����:A0120)���Ƿ�������棿")) return false;
		if(count3 != 1)
			if (!confirm("û��¼���֪(����:A0152)���Ƿ�������棿")) return false;
		if(count4 != 1)
			if (!confirm("û��¼���֪(����:A0155)���Ƿ�������棿")) return false;
	}*/
	
	//���б������͵ġ�Ͷ���������ڡ�����¼������֮�󣬱���ʱ���������������ǿ�б���
	if(calAge(document.all("PolApplyDate").value)<0)
		if (!confirm("Ͷ��������������¼�����ڣ��Ƿ�������棿")) return false;	
	
	//var strSql = "select sysvarvalue from ldsysvar where sysvar ='tbstartcheckdate'";
	
	var sqlid15="WbProposalInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName("app.WbProposalInputSql");
	mySql15.setSqlId(sqlid15);//ָ��ʹ��SQL��id
    var strSql = mySql15.getString();
  	var arrResult = easyExecSql(strSql);	
  	
 // 	var strSql1 = "select sysvarvalue from ldsysvar where sysvar ='tbcheckdate'";
  	
  	var sqlid16="WbProposalInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName("app.WbProposalInputSql");
	mySql16.setSqlId(sqlid16);//ָ��ʹ��SQL��id
    var strSql1 = mySql16.getString();
  
  	var arrResult1 = easyExecSql(strSql1);
  	if(arrResult!=null && arrResult[0][0]!='' && arrResult1!=null&& arrResult1[0][0]!='')
  	{
  		if((calAge(arrResult[0][0])>=0) && (calAgeNew(arrResult1[0][0],document.all("PolApplyDate").value)<0))
			if (!confirm("Ͷ��������������"+arrResult1[0][0]+"���Ƿ�������棿")) return false;	
  	}
  	
//	var strSql2 = "select sysvarvalue from ldsysvar where sysvar ='checkPrtNoDate'";
	
	var sqlid17="WbProposalInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName("app.WbProposalInputSql");
	mySql17.setSqlId(sqlid17);//ָ��ʹ��SQL��id
    var strSql2 = mySql17.getString();
    
  	var arrResult2 = easyExecSql(strSql2);
  	var tsubPrtNo = tPrtNo.substring(0,6);
  	var tsubPrtNo2 = tPrtNo.substring(0,4);
  	if(arrResult!=null && arrResult[0][0]!='' && arrResult2!=null && arrResult2[0][0]!=''
  			&& (calAge(arrResult[0][0])>=0)&&(calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
  	{
  		if(tsubPrtNo2!="3110"){
  		if(tsubPrtNo != "861102" && tsubPrtNo != "861602" && tsubPrtNo != "862102" && tsubPrtNo != "861502")
  			if (!confirm("ӡˢ��ǰ��λ����Ϊ861102��861602��862102��861502���Ƿ�������棿")) return false;
  		}
  	}
	
	return true;
	
}

//¼��У��
function checkInput()
{
    if ((fm.AppntRelationToInsured.value!="" && fm.AppntRelationToInsured.value=="00") || (fm.RelationToAppnt1.value!="" && fm.RelationToAppnt1.value=="00")) {
	    if(fm.AppntName.value!=fm.Name1.value)
	    {
	    	//alert("Ͷ���˺ͱ����˹�ϵΪ���ˣ�����������ͬ��");
	    	//return false;
	    }
    }
    //����У��
    if(fm.NewPayMode.value==null || fm.NewPayMode.value=='' ||(fm.NewPayMode.value!='0' && fm.NewPayMode.value!='8'))
    {
    	if((fm.NewBankCode.value!=null && fm.NewBankCode.value!="")
    		||(fm.NewAccName.value!=null && fm.NewAccName.value!="")
    		||(fm.NewBankAccNo.value!=null && fm.NewBankAccNo.value!=""))
    		{
    			alert("���ڽ�����ʽΪ�ջ�Ϊ����ת�ˣ�����¼�������ʻ���Ϣ��");
	   			 return false;
    		}
    }
    else
    {
    	if((fm.NewAccName.value==null || fm.NewAccName.value=="")
    		||(fm.NewAccName.value==null || fm.NewAccName.value=="")
    		||(fm.NewAccName.value==null || fm.NewAccName.value==""))
    		{
    			alert("�����ʻ���Ϣ����¼�룡");
	   			 return false;
    		}
    	
    }
    
    //����У��
    if(fm.PayLocation.value==null || fm.PayLocation.value=='' ||(fm.PayLocation.value!='0' && fm.PayLocation.value!='8'))
    {
    	if((fm.BankCode.value!=null && fm.BankCode.value!="")
    		||(fm.AccName.value!=null && fm.AccName.value!="")
    		||(fm.BankAccNo.value!=null && fm.BankAccNo.value!=""))
    		{
    			alert("���ڽ�����ʽΪ�ջ�Ϊ����ת�ˣ�����¼�������ʻ���Ϣ��");
	   			 return false;
    		}
    }
    else
    {
    	if((fm.BankCode.value==null || fm.BankCode.value=="")
    		||(fm.AccName.value==null || fm.AccName.value=="")
    		||(fm.BankAccNo.value==null || fm.BankAccNo.value==""))
    		{
    			alert("�����ʻ���Ϣ����¼�룡");
	   			 return false;
    		}
    	
    }
    
    if(fm.NewAccName.value!=null && fm.NewAccName.value!="" && fm.AppntName.value!=fm.NewAccName.value)
	    {
	    	
	    	alert("�����ʻ�������ΪͶ����������");
	    	return false;
	    }
	if(fm.AccName.value!=null && fm.AccName.value!="" && fm.AppntName.value!=fm.AccName.value)
	    {
	    	alert("�����ʻ�������ΪͶ����������");
	    	return false;
	    }
}

function checkSaleChnl()
{
	var SaleChnl = trim(document.all('SaleChnl').value);
	var AgentCode = trim(document.all('AgentCode').value);
	var tSubRiskFlag ;   //�����ձ��
	var tResult ; 
	var prtNo = document.all("PrtNo").value;
	var riskType = prtNo.substring(2, 4);
	var AgentCom = trim(document.all('AgentCom').value);
	if(SaleChnl == null || SaleChnl == "")
	{
		alert("û��������������!");
		return false;
	}
	if(SaleChnl == "03" )
	{
		if(AgentCom == "")
		{
			alert("��������Ϊ����ʱ��������������!");
			return false;
		}
	}
	if( riskType=="21" )
	{
		if(AgentCom == "")
		{
			alert("�н�Ͷ������������������!");
			return false;
		}
		
	}
	
	//�н�����Ǩ��һ����ɺ���ҪУ���н�������������н�ͻ�����ƥ��
	//add by weikai
	//alert("AgentCom:"+AgentCom);
	if(AgentCom!=""&&AgentCom!=null)
	{
		if(AgentCom.substring(0,1)=='2')
		{
//			var tAgentCheck_sql="select 1 from laagent a,latree b where a.agentcode=b.agentcode "
//			+" and a.branchtype='3' and b.branchcode='1' and a.agentcode='"+AgentCode+"'";
			
			 var sqlid18="WbProposalInputSql18";
	    	  var mySql18=new SqlClass();
	    	  mySql18.setResourceName("app.WbProposalInputSql");
	    	  mySql18.setSqlId(sqlid18);//ָ��ʹ��SQL��id
	    	  mySql18.addSubPara(AgentCode);//ָ���������
	    	  var tAgentCheck_sql = mySql18.getString();
			
			var AgentCheckFlag = decodeEasyQueryResult(easyQueryVer3(tAgentCheck_sql));
			if(AgentCheckFlag == null || AgentCheckFlag == "")
			{
				alert("�������Ϊ2��ͷ���н��������������˱���Ϊ�н�ͻ�������");
				return false;
			}
		}
	}
	
	if(SaleChnl == "02")
	{
	  if(AgentCom != null && AgentCom != "")
	  {
	  	alert("��������Ϊ����ʱ��������������!");
	  	return false;
	  }
	}
	
  if(AgentCom != null && AgentCom != "")
  {
  //	var tsql = "select * from lacom where agentcom = '" + AgentCom + "' " ;
  	
  	 var sqlid19="WbProposalInputSql19";
	 var mySql19=new SqlClass();
	 mySql19.setResourceName("app.WbProposalInputSql");
	 mySql19.setSqlId(sqlid19);//ָ��ʹ��SQL��id
	 mySql19.addSubPara(AgentCom);//ָ���������
	 var tsql = mySql19.getString();
	 tResult = decodeEasyQueryResult(easyQueryVer3(tsql));
		if(tResult == null || tResult == "")
		{
			alert("¼��Ĵ�������������ݿⶨ��ķ�Χ��");
			return false;
		}  
  }
	
	if((document.all('RiskCode').value == null) && (trim(document.all('RiskCode').value) == ""))
	{
	  alert("���ֱ��벻��Ϊ��!");
	  return false;
	}
	
//	var sql = "SELECT BranchType FROM laagent where agentcode='" + AgentCode + "'";

	var sqlid20 = "WbProposalInputSql20";
	var mySql20 = new SqlClass();
	mySql20.setResourceName("app.WbProposalInputSql");
	mySql20.setSqlId(sqlid20);//ָ��ʹ��SQL��id
	mySql20.addSubPara(AgentCode);//ָ���������
	var sql = mySql20.getString();
	var BranchType = decodeEasyQueryResult(easyQueryVer3(sql));
	if(BranchType == null || BranchType == "")
	{
		alert("�ô����˵�չҵ����δ֪!");
		return false;
	}
	
//	var tRiskSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"+trim(document.all('RiskCode').value)+"'";
	
	var sqlid21 = "WbProposalInputSql21";
	var mySql21 = new SqlClass();
	var RiskCode21 = trim(document.all('RiskCode').value);
	mySql21.setResourceName("app.WbProposalInputSql");
	mySql21.setSqlId(sqlid21);//ָ��ʹ��SQL��id
	mySql21.addSubPara(RiskCode21);//ָ���������
	var tRiskSql = mySql21.getString();
	
        tSubRiskFlag = decodeEasyQueryResult(easyQueryVer3(tRiskSql));
        //alert(tRiskSql);
        //alert(tSubRiskFlag);
        if(tSubRiskFlag == null || tSubRiskFlag == "")
	{
		alert("���������ձ�־δ֪!");
		return false;
	}
	//�����жϱ�������������ҵ��Ա��BranchType
        if(tSubRiskFlag[0][0]=="M")
        {
  //        sql = "select 1 from LDCode1 where codetype='salechnlagentctrl' and code='"+SaleChnl+"' and ( code1='"+BranchType[0][0]+"' or othersign='1')";  //othersign='1' ��ʾ���������ʹ�������У��
         
          var sqlid22 = "WbProposalInputSql22";
      	  var mySql22 = new SqlClass();
      	  mySql22.setResourceName("app.WbProposalInputSql");
      	  mySql22.setSqlId(sqlid22);//ָ��ʹ��SQL��id
      	  mySql22.addSubPara(SaleChnl);//ָ���������
      	  mySql22.addSubPara(BranchType[0][0]);//ָ���������
      	  sql = mySql22.getString();
          
          tResult = decodeEasyQueryResult(easyQueryVer3(sql));
          if(tResult == null || tResult == "")
	  {
		alert("��������������������˲�ƥ��!");
		return false;
	  }       
        }
       else  //�������ж��Ƿ����������������Լ�������һ��
        {
	// sql = "select 1 from LCPol where PolNo='"+trim(document.all('MainPolNo').value)+"' and agentcode='"+AgentCode+"' and salechnl='"+SaleChnl+"'";	 
	
	  var sqlid23 = "WbProposalInputSql23";
 	  var mySql23 = new SqlClass();
 	  var MainPolNo23 = trim(document.all('MainPolNo').value);
 	  mySql23.setResourceName("app.WbProposalInputSql");
 	  mySql23.setSqlId(sqlid23);//ָ��ʹ��SQL��id
 	  mySql23.addSubPara(MainPolNo23);//ָ���������
 	  mySql23.addSubPara(AgentCode);//ָ���������
 	  mySql23.addSubPara(SaleChnl);//ָ���������
 	  sql = mySql23.getString();
	 
	 tResult = decodeEasyQueryResult(easyQueryVer3(sql));
          if(tResult == null || tResult == "")
	  {
		alert("�����������յ���������������˲�ƥ��!");
		return false;
	  }  
	}
        
	return true;
}

function checkComCode()
{
	if((document.all('PrtNo').readOnly == true) && (document.all('PrtNo').value != ""))
	{
		
	//	var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('PrtNo').value) + "'";
		
		  var sqlid24 = "WbProposalInputSql24";
	 	  var mySql24 = new SqlClass();
	 	  var PrtNo24 = trim(document.all('PrtNo').value) ;
	 	  mySql24.setResourceName("app.WbProposalInputSql");
	 	  mySql24.setSqlId(sqlid24);//ָ��ʹ��SQL��id
	 	  mySql24.addSubPara(PrtNo24 );//ָ���������
	 	  var sql = mySql24.getString();
		
		var ManageCom = decodeEasyQueryResult(easyQueryVer3(sql));
		var Managecom2 =trim(document.all('ManageCom').value);	
		if(ManageCom!=null)
		{
		   if(ManageCom[0][0]!=Managecom2)
		    {
			alert("ɨ�����¼���������ͬһ������!");
			return false;
		    }
	    } 
    }
	return true;
}

//��ѯ�������
function initManageCom()
{
	/*(if((document.all('PrtNo').readOnly == true) && (document.all('PrtNo').value != ""))
	{		
		var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('PrtNo').value) + "'";
		var ManageCom = decodeEasyQueryResult(easyQueryVer3(sql));	
		fm.ScanManageCom.value = ManageCom[0][0];
	}*/
	fm.AgentManageCom.value = '';
	fm.AgentManageComName.value = '';
	var tSaleChnl = fm.SaleChnl.value;
    if(tSaleChnl!=null && tSaleChnl != '')
	{
		if(tSaleChnl == '03' || tSaleChnl == '05')
		{
			if(fm.AgentCom.value != null && fm.AgentCom.value != "")
			{
//				var strSql = "select ManageCom,(select name from ldcom where comcode=managecom),branchtype from LACom where AgentCom='" + fm.AgentCom.value +"'";
			  
				  var sqlid25 = "WbProposalInputSql25";
			 	  var mySql25 = new SqlClass();
			 	  var AgentCom25 = fm.AgentCom.value;
			 	  mySql25.setResourceName("app.WbProposalInputSql");
			 	  mySql25.setSqlId(sqlid25);//ָ��ʹ��SQL��id
			 	  mySql25.addSubPara(AgentCom25);//ָ���������
			 	  var strSql = mySql25.getString();
				  var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.AgentManageCom.value = arrResult[0][0];
			      fm.AgentManageComName.value = arrResult[0][1];
			      if(arrResult[0][2]!=null &&��arrResult[0][2]=='6')
			      {
			      	if(document.all('ManageCom').value != null && document.all('ManageCom').value != "")
			      	{
			      		var cManageCom = document.all('ManageCom').value;
			      		var length = cManageCom.length;
			      		for(var i=length;i<8;i++)
			      		{
			      			cManageCom = cManageCom +'0';
			      		}
			      		fm.AgentManageCom.value = cManageCom;
		//	      		var sql = "select name from ldcom where comcode='"+cManageCom+"'";
			      		
			      		 var sqlid26 = "WbProposalInputSql26";
					 	 var mySql26 = new SqlClass();
					 	 mySql26.setResourceName("app.WbProposalInputSql");
					 	 mySql26.setSqlId(sqlid26);//ָ��ʹ��SQL��id
					 	 mySql26.addSubPara(cManageCom);//ָ���������
					 	 var sql = mySql26.getString();
						var arrResult1 = easyExecSql(sql);	
						if(arrResult1 != null)
						{							
			     			fm.AgentManageComName.value = arrResult1[0][0];
						}						
			      	}			      	
			      }
				}
				
			}	
		}
		else
		{
			if(document.all('AgentCode').value != null && document.all('AgentCode').value != "")
			{
	//			var strSql = "select ManageCom,(select name from ldcom where comcode=managecom) from LAAgent where AgentCode='" + fm.AgentCode.value +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
			   
				 var sqlid27 = "WbProposalInputSql27";
			 	 var mySql27 = new SqlClass();
			 	 var AgentCode27 = fm.AgentCode.value;
			 	 mySql27.setResourceName("app.WbProposalInputSql");
			 	 mySql27.setSqlId(sqlid27);//ָ��ʹ��SQL��id
			 	 mySql27.addSubPara(AgentCode27);//ָ���������
			 	 var strSql = mySql27.getString();
				 var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.AgentManageCom.value = arrResult[0][0];
			      fm.AgentManageComName.value = arrResult[0][1];
				}
			}  
		}			
	}	   
}

//��ѯɨ������ʹ�������������
function initScan()
{
	initManageCom();	
	
	//��ѯ��֪����
	//alert(ImpartGrid.mulLineCount);	
	var i=0;
	var strSql="";
	for( ;i<ImpartGrid.mulLineCount; i++)
	{
		var impartver = ImpartGrid.getRowColData(i, 1);
		var impartcode= ImpartGrid.getRowColData(i, 2);
		//alert("impartver:"+impartver+"impartcode:"+impartcode);
		if( impartver!=null && impartver!="" && impartcode!=null && impartcode!="" )
		{
	//		strSql = "select impartcontent from ldimpart where impartver='" + impartver +"' and impartcode='" + impartcode +"'";
		  
			 var sqlid28 = "WbProposalInputSql28";
		 	 var mySql28 = new SqlClass();
		 	
		 	 mySql28.setResourceName("app.WbProposalInputSql");
		 	 mySql28.setSqlId(sqlid28);//ָ��ʹ��SQL��id
		 	 mySql28.addSubPara(impartver);//ָ���������
		 	 mySql28.addSubPara(impartcode);//ָ���������
		 	 strSql = mySql28.getString();
			var arrResult = easyExecSql(strSql);
		    if (arrResult != null) {      
		      ImpartGrid.setRowColData(i, 3, arrResult[0][0]);
			}
		}		
	}
	    
}

//У���������������ֵĹ�ϵ
function checkSalechnlRiskCodeRela()
{
	
	if(document.all('RiskCode').value == "" )
	 {
	 	 alert("���ֱ��벻��Ϊ�գ�");
	 	 return false;
	 }
	
	if(document.all('Salechnl').value == "" )
	 {
	 	 alert("������������Ϊ�գ�");
	 	 return false;
	 }
//	var strSql="select Code from ldcode where CodeType='salechnlriskrela' and Code = '"+document.all('Salechnl').value+"'";
	
	 var sqlid29 = "WbProposalInputSql29";
	 var mySql29 = new SqlClass();
	 var Salechnl29 = document.all('Salechnl').value;
	 mySql29.setResourceName("app.WbProposalInputSql");
	 mySql29.setSqlId(sqlid29);//ָ��ʹ��SQL��id
	 mySql29.addSubPara(Salechnl29);//ָ���������
	 var strSql = mySql29.getString();
	var result = easyExecSql(strSql);
	
	if(result!=null)
	{
//    var strSubSql="select CodeType from ldcode1 where CodeType='salechnlriskrela' and Code = '"+document.all('Salechnl').value+"' and Code1='"+document.all('RiskCode').value+"'";
	
     var sqlid30 = "WbProposalInputSql30";
	 var mySql30 = new SqlClass();
	 var Salechnl30 = document.all('Salechnl').value;
	 var RiskCode30 = document.all('RiskCode').value;
	 mySql30.setResourceName("app.WbProposalInputSql");
	 mySql30.setSqlId(sqlid30);//ָ��ʹ��SQL��id
	 mySql30.addSubPara(Salechnl30);//ָ���������
	 mySql30.addSubPara(RiskCode30);//ָ���������
	 var strSubSql = mySql30.getString();
    var tSubResult = easyExecSql(strSubSql);

    if(tSubResult == null)
     {
     	alert("����������"+document.all('Salechnl').value+" ����������"+document.all('RiskCode').value+"����");
      return false;
     }
	}
	return true;
}

/*********************************************************************
 *  �������Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm() {
	document.all("save").disabled = true;
//	alert("document.all(save).disabled"+document.all("save").disabled);
//	alert("document.all(save).disabled"+document.all("save").src);
	if(document.all("aftersave").value==1)
	{
		return ;
	}
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var passVerify = true;
    var verifyGrade = "1";
 	
 //	var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
 	
 	 var sqlid31 = "WbProposalInputSql31";
	 var mySql31 = new SqlClass();
	 var PrtNo31 = fm.PrtNo.value;
	 mySql31.setResourceName("app.WbProposalInputSql");
	 mySql31.setSqlId(sqlid31);//ָ��ʹ��SQL��id
	 mySql31.addSubPara(PrtNo31);//ָ���������
	 var sSQL = mySql31.getString();
	var arrCount=easyExecSql(sSQL); 
 if (trim(document.all('ProposalNo').value) != "" || arrCount!=null) {
	  alert("��Ͷ�����Ѿ����棬�������ٴα��棬�����½���¼����棡");
	  return false;
	} 
	
	//�ѱ����Ϊ�յļ�¼ȥ��
	clearRiskGridNone();	
 
	// У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
  //try { verifySamePerson(); } catch(e) {}
  	    	 
      	// У��¼������
    //if( verifyProposal() == false ) return;    
       
    for( var i=1 ;i<=3; i++)
	{
	    //var tFlag = 1;
		if(document.getElementById("DivInsuredAll"+i).style.display == "none")
		{
		    if(i==1)
		    {
		         alert("������¼��һ����������Ϣ��");
		         return false;
		    }
			document.all("InsuredNum").value = i-1;
			break;
			//tFlag = 0;//û�б�������Ϣ
		}
		else
			document.all("InsuredNum").value = i;		    
			
		//for( var j=1 ;j<=3 && tFlag == 1; j++)
		for( var j=1 ;j<=3; j++)
		{
			if(document.getElementById("divRisk"+i+j).style.display == "none")
		  	{
		  	    //alert(i);
		  	    //alert(j);
		  	    if(j==1)
			    {
			         var sI = "";
			         if(i==1) 
			         	sI = "������";
			         else  
			         	sI = "��"+i+"������";
			         	
			         alert("��" +sI+ "����¼��һ��������Ϣ��");
			         return false;
			    }
		  		document.all("MainRiskNum"+i).value = j-1;
		  		break;
		  	}
		  	else
		  		document.all("MainRiskNum"+i).value = j;
		}
		/*if(document.all("RelationToMainInsured"+i).value==null || document.all("RelationToMainInsured"+i).value=="") 
		{
			alert("���һ�������˹�ϵ����Ϊ�գ�");
			return false;
		}	
		
		for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
		  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
		    try {
		      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1) + "1";
		       if(document.all(insuredName).value ==null || fm.elements[elementsNum].value == "")
		       
		    }
		    catch (ex) {}
		  }
		}	
	  	*/
	}
    
    /*alert(fm.InsuredNum.value);	
     alert(fm.MainRiskNum1.value);	
      alert(fm.MainRiskNum2.value);	
       alert(fm.MainRiskNum3.value);	
     return false;*/
         
    // У�鱻����ͬͶ���˵Ĺ�ϵ�Ƿ�¼�룬��ͬ����и���
    var tPrtNo = fm.PrtNo.value;
    if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) != "51"))
	{
		if(fm.AppntRelationToInsured.value==null || fm.AppntRelationToInsured.value=="")
    	{
    		alert("Ͷ�����뱻���˵Ĺ�ϵδ¼�룡");
       		passVerify = false;
    	}
	}	
	if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) == "51"))
	{
    	if(((DivInsuredAll1.style.display=="" && fm.RelationToAppnt1.value!=null && fm.RelationToAppnt1.value!=""))
            && ((DivInsuredAll2.style.display=="" && fm.RelationToAppnt2.value!=null && fm.RelationToAppnt2.value!="")||DivInsuredAll2.style.display=="none")
            && ((DivInsuredAll3.style.display=="" && fm.RelationToAppnt3.value!=null && fm.RelationToAppnt3.value!="")||DivInsuredAll3.style.display=="none")) 
        {        	
        }
        else
        {
        	alert("��������Ͷ���˵Ĺ�ϵδ¼���¼�벻��ȫ��");
       		passVerify = false;
        }
	}
    /*if(fm.AppntRelationToInsured.value!=null && fm.AppntRelationToInsured.value!="")
    {}
    else if(((DivInsuredAll1.style.display=="" && fm.RelationToAppnt1.value!=null && fm.RelationToAppnt1.value!=""))
            && ((DivInsuredAll2.style.display=="" && fm.RelationToAppnt2.value!=null && fm.RelationToAppnt2.value!="")||DivInsuredAll2.style.display=="none")
            && ((DivInsuredAll3.style.display=="" && fm.RelationToAppnt3.value!=null && fm.RelationToAppnt3.value!="")||DivInsuredAll3.style.display=="none")) 
        {}
    else
       {
       		alert("Ͷ����ͬ�����˵Ĺ�ϵδ¼���¼�벻��ȫ��");
       		passVerify = false;
       }*/
  
     //����������ڣ�����գ������֤��ȡ
    try {checkBirthday(); } catch(e){}   
    //���������˺��Ƿ�¼��淶
    if(!verifyBankAccNo()) return false;
  
    if(verifyInput() == false) passVerify = false; 
    for( var i=1 ;i<=3; i++)
	{
		if(document.getElementById("DivInsuredAll"+i).style.display == "")
		{
			if(!eval("Bnf"+i+"Grid.checkValue()"))
			{
				passVerify = false;
				break;
			}			
				
		}
	}
 //   sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
     var sqlid32 = "WbProposalInputSql32";
	 var mySql32 = new SqlClass();
	 mySql32.setResourceName("app.WbProposalInputSql");
	 mySql32.setSqlId(sqlid32);//ָ��ʹ��SQL��id
	 sql = mySql32.getString();
    
	var arrResult2 = easyExecSql(sql);		//alert("1136 "+arrResult2);
    if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
    {
    	if(verifyFirstTrialDate() == false) return false;
    }
    if (!passVerify) 
    {
		if (!confirm("Ͷ����¼������д����Ƿ�������棿")) return false;
    }
    
    if(checkInput() == false) return false;
    if(verifyRisk() == false) return false;  
    if(verifyImpart() == false) return false;  
    
		 
	document.all('fmAction').value = "INSERT";
	//alert("mAction"+mAction);
//	alert("OK ����");
//	return;
	
	fm.action="../app/WbProposalSave.jsp"
 
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	lockScreen('lkscreen');   
	document.getElementById("fm").submit(); //�ύ
	document.all("aftersave").value="1";
}

/*  //���ӡ��������ڡ���У�� --2010-03-18 --hanbin   */
function verifyFirstTrialDate() 
{
	  //alert(1);
	  if(fm.FirstTrialDate.value == null || fm.FirstTrialDate.value == "")
	  {
	  	alert("�������ڲ���Ϊ�գ�");
	  	return false;
	  }
	  if(fm.FirstTrialDate.value < fm.PolApplyDate.value)
	  {
	  	 alert("�������ڲ�������Ͷ���������ڣ�");
	  	 return false;
	  }
//	  strSql = "select makedate from es_doc_main a  where a.doccode = '"+fm.PrtNo.value+"' and a.busstype = 'TB' and a.subtype = 'UA001'";
	  var sqlid33 = "WbProposalInputSql33";
	  var mySql33 = new SqlClass();
	  var PrtNo33 = fm.PrtNo.value;
	  mySql33.setResourceName("app.WbProposalInputSql");
	  mySql33.setSqlId(sqlid33);//ָ��ʹ��SQL��id
	  mySql33.addSubPara(PrtNo33);//ָ���������
	  strSql = mySql33.getString();
	  
	  var scanDate = easyExecSql(strSql);
	  if(fm.FirstTrialDate.value >  scanDate)
	  {
	  	 alert("�������ڲ�������ɨ�����ڣ�");
	  	 return false;
	  }
}

//У�顾�������ڡ� -2010-03-19 - hanbin
function checkFirstTrialDate()
{
//	sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
	  var sqlid34 = "WbProposalInputSql34";
	  var mySql34 = new SqlClass();
	  mySql34.setResourceName("app.WbProposalInputSql");
	  mySql34.setSqlId(sqlid34);//ָ��ʹ��SQL��id
	  sql = mySql34.getString();
	
	var arrResult2 = easyExecSql(sql);		//alert("1136 "+arrResult2);
    if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
    {
	if(trim(fm.FirstTrialDate.value)==""){return ;}
    else if (fm.FirstTrialDate.value.length == 8)
	{
		if(fm.FirstTrialDate.value.indexOf('-')==-1)
		{ 
			var Year =     fm.FirstTrialDate.value.substring(0,4);
			var Month =    fm.FirstTrialDate.value.substring(4,6);
			var Day =      fm.FirstTrialDate.value.substring(6,8);
			fm.FirstTrialDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			 alert("�������Ͷ����������!");
			fm.FirstTrialDate.value = "";  
			return;
			}
		}else{
			alert("�������Ͷ�����ڸ�ʽ����!");
			fm.FirstTrialDate.value = "";  
			return;
		}
	}
	else if(fm.FirstTrialDate.value.length == 10)
	{
		var Year =     fm.FirstTrialDate.value.substring(0,4);
		var Month =    fm.FirstTrialDate.value.substring(5,7);
		var Day =      fm.FirstTrialDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ����������!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
		return;
		}
	}
	else
	{
		alert("�������Ͷ����������!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
	}
    }

}

/*********************************************************************
 *  ɾ���ѱ������Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetCont() {	
//	alert("document.all(save).disabled"+document.all("save").disabled);
//	alert("document.all(save).disabled"+document.all("save").src);
	
//	var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
	var sqlid35 = "WbProposalInputSql35";
	var mySql35 = new SqlClass();
	var PrtNo35 = fm.PrtNo.value;
    mySql35.setResourceName("app.WbProposalInputSql");
	mySql35.setSqlId(sqlid35);//ָ��ʹ��SQL��id
	mySql35.addSubPara(PrtNo35);//ָ���������
	var sSQL = mySql35.getString();
	
	var arrCount=easyExecSql(sSQL); 
	 if (trim(document.all('ProposalNo').value) == "" || arrCount==null) {
		  alert("��Ͷ������δ���棬������ɾ��¼����Ϣ��");
		  return false;
		} 
//	var sSQL1 ="select 1 from lwmission where missionid='"+MissionID+"' and missionprop1='"+fm.PrtNo.value+"' and activityid in (select a.activityid  from lwactivity a where a.functionid in ( '10010016','10010017'))";
	
	var sqlid36 = "WbProposalInputSql36";
	var mySql36 = new SqlClass();
	var PrtNo36 = fm.PrtNo.value;
    mySql36.setResourceName("app.WbProposalInputSql");
	mySql36.setSqlId(sqlid36);//ָ��ʹ��SQL��id
	mySql36.addSubPara(MissionID);//ָ���������
	mySql36.addSubPara(PrtNo36);//ָ���������
	var sSQL1 = mySql36.getString();
	var arrCount1=easyExecSql(sSQL1); 
	 if (arrCount1==null) {
		  alert("��Ͷ�����Ѿ�������ϣ�������ɾ��¼����Ϣ��");
		  return false;
		}
	if(!confirm("ȷ��Ҫɾ���ѱ������ݣ������±�����"))
			{
				return false;
			}
	document.all("fmAction").value = "DELETECONT";
    document.all("deleteCont").disabled = true;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var verifyGrade = "1"; 
	
	fm.action="../app/WbProposalSave.jsp"
 
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit(); //�ύ
} 

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');
	if(fm.fmAction.value!="DELETE")
		document.all("save").disabled=false;
		
	try { showInfo.close(); } catch(e) {}
	if (FlagStr == "Fail" )
	{             
		//var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		alert("����ʧ�ܣ�ԭ���ǣ�" + content); 
		document.all("save").disabled=true; 
		if(fm.fmAction.value == "DELETECONT") 
			document.all("deleteCont").disabled = false;
		//inputQuestButton.style.display="";
	}
	else
	{ 
 
		if(loadFlag == '3'){
		//inputQuestButton.style.display="";
		}		
		  content = "����ɹ���";
		  var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
		  
	    if(fm.fmAction.value == "INSERT")
	    {
	    	document.all("deleteCont").disabled=false;
	    	 //��ʼ��Ͷ�����˿ͻ��� add 2009-2-9 ln 
	//	 	var sSQL ="select appntno from lcappnt where contno='"+fm.ProposalNo.value+"'";
		 	
		 	var sqlid37 = "WbProposalInputSql37";
			var mySql37 = new SqlClass();
			var ProposalNo37 = fm.ProposalNo.value;
		    mySql37.setResourceName("app.WbProposalInputSql");
			mySql37.setSqlId(sqlid37);//ָ��ʹ��SQL��id
			mySql37.addSubPara(ProposalNo37);//ָ���������
			var sSQL = mySql37.getString();
		 	
			var arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{
				fm.AppntNo.value=arrInsuredAdd[0][0];
			}	
	//	    sSQL ="select insuredno from lcinsured where contno='"+fm.ProposalNo.value+"'"
	//		+ " and sequenceno in ('1','-1') ";
		    
		    var sqlid38 = "WbProposalInputSql38";
			var mySql38 = new SqlClass();
			var ProposalNo38 = fm.ProposalNo.value;
		    mySql38.setResourceName("app.WbProposalInputSql");
			mySql38.setSqlId(sqlid38);//ָ��ʹ��SQL��id
			mySql38.addSubPara(ProposalNo38);//ָ���������
		    sSQL = mySql38.getString();
		    
			var arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	    
				fm.CustomerNo1.value=arrInsuredAdd[0][0];		
			}
	
//			sSQL ="select insuredno from lcinsured where contno='"+fm.ProposalNo.value+"'"
//					+ " and sequenceno='2' ";
			
			var sqlid39 = "WbProposalInputSql39";
			var mySql39 = new SqlClass();
			var ProposalNo39 = fm.ProposalNo.value;
		    mySql39.setResourceName("app.WbProposalInputSql");
			mySql39.setSqlId(sqlid39);//ָ��ʹ��SQL��id
			mySql39.addSubPara(ProposalNo39);//ָ���������
		    sSQL = mySql39.getString();
		    
			
			//prompt('',sSQL);
			arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	    
				fm.CustomerNo2.value=arrInsuredAdd[0][0];			
			}
			
//			sSQL ="select insuredno from lcinsured where contno='"+fm.ProposalNo.value+"'"
//					+ " and sequenceno='3' ";
			
			var sqlid40 = "WbProposalInputSql40";
			var mySql40 = new SqlClass();
			var ProposalNo40 = fm.ProposalNo.value;
		    mySql40.setResourceName("app.WbProposalInputSql");
			mySql40.setSqlId(sqlid40);//ָ��ʹ��SQL��id
			mySql40.addSubPara(ProposalNo40);//ָ���������
		    sSQL = mySql40.getString();
			
			//prompt('',sSQL);
			arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	    
				fm.CustomerNo3.value=arrInsuredAdd[0][0];			
			}
			document.all("save").disabled=true;
			queryInsuredInfo();
			queryRiskInfo();
			getDealTypeInfo();
	    }
	    else if(fm.fmAction.value == "DELETECONT")
	    {
	    	document.all('ProposalNo').value ="";
	    	resetForm();	    	
	    }
					
	} 
	mAction = ""; 
}


function afterSubmitConfirm( FlagStr, content )
{
	unlockScreen('lkscreen');  
	try { showInfo.close(); } catch(e) {}
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
		document.all("aftersave").value="0";  
		//inputQuestButton.style.display="";
	}
	else
	{ 
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  		
	  top.opener.unlock(prtNo);
	  top.close();
	} 

}

/*********************************************************************
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick() { 
	var tPrtNo = document.all('PrtNo').value;
	
	if(tPrtNo==null || tPrtNo=="") {
		alert( "ӡˢ����Ϣ��ʧ�����ܽ���ɾ��!" );
	}
	else 
	{
//		var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
		
		var sqlid41 = "WbProposalInputSql41";
		var mySql41 = new SqlClass();
		var PrtNo41 = fm.PrtNo.value;
	    mySql41.setResourceName("app.WbProposalInputSql");
		mySql41.setSqlId(sqlid41);//ָ��ʹ��SQL��id
		mySql41.addSubPara(PrtNo41);//ָ���������
	    var sSQL = mySql41.getString();
		
		var arrCount=easyExecSql(sSQL); 
		if((document.all("ProposalNo").value!=null && document.all("ProposalNo").value != "")|| arrCount!=null)
	    {
	    	alert("���쳣���Ѿ����棬����ɾ����");
	    	return false;
	    }
	    
		if(fm.DealType.value != "03")
		{
			alert("�˱�����������޷�������쳣��������ɾ����");
			return false;
		}
		alert(fm.InsuredNum.value);		
		
		if(!confirm("ȷ��Ҫɾ����"))
		{
			return false;
		}

		var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )	
		{
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			lockScreen('lkscreen');
			document.getElementById("fm").submit(); //�ύ
		}
	}
}           

/*********************************************************************
 *  "����"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetForm()
{
    if(document.all("ProposalNo").value!=null && document.all("ProposalNo").value != "")
    {
    	alert("��Ͷ�����Ѿ����棬�������ò����±��棡");
    	return false;
    }
    
	document.all("save").disabled=false;
	document.all("aftersave").value="0";
	//changeImage(document.all("save"),'../common/images/butSave.gif');
} 

/*********************************************************************
 *  ��ʼ����ť״̬
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initialSave()
{
//    var tSql = " select bussno from bpomissionstate where bussno='"+document.all("PrtNo").value+"' and State is not null and State!='0'";
   
    var sqlid42 = "WbProposalInputSql42";
	var mySql42 = new SqlClass();
	var PrtNo42 = document.all("PrtNo").value;
    mySql42.setResourceName("app.WbProposalInputSql");
	mySql42.setSqlId(sqlid42);//ָ��ʹ��SQL��id
	mySql42.addSubPara(PrtNo42);//ָ���������
    var tSql = mySql42.getString();
	
    
    var arrResult = easyExecSql( tSql );
	if (arrResult != null) 
	{
		document.all("ProposalNo").value = arrResult[0][0];
		//alert(document.all("ProposalNo").value);
		document.all("save").disabled=true;//�ѱ��棬�������±���
		
	} 
	else
	{
		document.all("save").disabled=false;
		document.all("aftersave").value="0";
		//changeImage(document.all("save"),'../common/images/butSave.gif');
	}
	
} 

/*********************************************************************
 *  "ȡ��"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelForm()
{
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
}
 
/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	//if( cDebug == "1" )
		//parent.fraMain.rows = "0,0,50,82,*";
	//else 
		//parent.fraMain.rows = "0,0,80,72,*";
}

           
 /*********************************************************************
 *  Click�¼������������ѯ������Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDuty()
{
	//����������Ӧ�Ĵ���
	cPolNo = fm.ProposalNo.value;
	if( cPolNo == "" )
	{
		alert( "�������ȱ���Ͷ�������ܲ鿴��Ͷ�����������" );
		return false;
	}
	
	var showStr = "���ڲ�ѯ���ݣ������Ժ�......";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
	//showModalDialog( "./ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=18+"cm";      //�������ڵĿ��; 
	var iHeight=14+"cm";     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	showInfo.close();
}           


/*********************************************************************
 *  Click�¼���������������ݽ�����Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalNo.value;
	var prtNo = fm.PrtNo.value;
	
	if( cPolNo == "" )
	{
		alert( "�������ȱ���Ͷ�������ܽ����ݽ�����Ϣ���֡�" );
		return false
	}
	
	showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL&prtNo=" + prtNo );
}           

/*********************************************************************
 *  Click�¼�����˫����Ͷ���˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppnt()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonMain.html" );
	}
} 

//У��Ͷ���˳�������
function checkappntbirthday()
{
	if(fm.AppntBirthday.value.length==8)
	{
		if(fm.AppntBirthday.value.indexOf('-')==-1)
		{
			var Year =     fm.AppntBirthday.value.substring(0,4);
			var Month =    fm.AppntBirthday.value.substring(4,6);
			var Day =      fm.AppntBirthday.value.substring(6,8);
			fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("�������Ͷ���˳�����������!");
			fm.AppntBirthday.value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     fm.AppntBirthday.value.substring(0,4);
		var Month =    fm.AppntBirthday.value.substring(5,7);
		var Day =      fm.AppntBirthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ���˳�����������!");
		fm.AppntBirthday.value = "";
		return;
		}
	}
}


//У�鱻���˳�������
function checkinsuredbirthday(insuredNo)
{
	if(document.all("Birthday"+insuredNo).length==8)
	{
		if(document.all("Birthday"+insuredNo).value.indexOf('-')==-1)
		{
			var Year =     document.all("Birthday"+insuredNo).value.substring(0,4);
			var Month =    document.all("Birthday"+insuredNo).value.substring(4,6);
			var Day =      document.all("Birthday"+insuredNo).value.substring(6,8);
			document.all("Birthday"+insuredNo).value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("������ı����˳�����������!");
			document.all("Birthday"+insuredNo).value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     document.all("Birthday"+insuredNo).value.substring(0,4);
		var Month =    document.all("Birthday"+insuredNo).value.substring(5,7);
		var Day =      document.all("Birthday"+insuredNo).value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("������ı����˳�����������!");
		document.all("Birthday"+insuredNo).value = "";
		return;
		}
	}
}
//Ͷ��������<Ͷ���˱���������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
function getAge()
{
	if(fm.AppntBirthday.value=="")
	{
		return;
	}
	if(fm.AppntBirthday.value.indexOf('-')==-1)
	{
		var Year =fm.AppntBirthday.value.substring(0,4);
		var Month =fm.AppntBirthday.value.substring(4,6);
		var Day =fm.AppntBirthday.value.substring(6,8);
		fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.AppntBirthday.value)<0)
	{
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		fm.AppntAge.value="";
		return;
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	document.all('AppntAge').value=calAge(fm.AppntBirthday.value);
  	return ;
}
//������������<����������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
function getAge2(insuredNo)
{	
	if(document.all("Birthday"+insuredNo).value=="")
	{
		return;
	}
	if(document.all("Birthday"+insuredNo).value.indexOf('-')==-1)
	{
		var Year =     document.all("Birthday"+insuredNo).value.substring(0,4);
		var Month =    document.all("Birthday"+insuredNo).value.substring(4,6);
		var Day =      document.all("Birthday"+insuredNo).value.substring(6,8);
		document.all("Birthday"+insuredNo).value = Year+"-"+Month+"-"+Day;
  	}
  	else
  	{
    	var Year1 =     document.all("Birthday"+insuredNo).value.substring(0,4);
	    var Month1 =    document.all("Birthday"+insuredNo).value.substring(5,7);
	    var Day1 =      document.all("Birthday"+insuredNo).value.substring(8,10);	
	    document.all("Birthday"+insuredNo).value = Year1+"-"+Month1+"-"+Day1;	
	}
	if(calAge(document.all("Birthday"+insuredNo).value)<0)
  	{
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		document.all("Age"+insuredNo).value="";
		return;
    }
    document.all('Age'+insuredNo).value=calAgeNew(document.all("Birthday"+insuredNo).value,document.all("PolApplyDate").value);
  	return ;
} 

function initialAge()
{
	//alert(1);
	if(document.all("AppntBirthday").value!=null && document.all("AppntBirthday").value!='')
		document.all('AppntAge').value=calAge(document.all("AppntBirthday").value);
	else
		document.all('AppntAge').value='0';

	for(var i=1;i<=3;i++)
	{
		if(document.all("Birthday"+i).value!=null && document.all("Birthday"+i).value!=''
			&& document.all("PolApplyDate").value!=null && document.all("PolApplyDate").value!='')
			document.all('Age'+i).value=calAgeNew(document.all("Birthday"+i).value,document.all("PolApplyDate").value);
		else
			document.all('Age'+i).value='0';
	}
			
} 

function checkidtype()
{
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.AppntIDNo.value="";
	}
}    

function checkidtype2(index)
{//alert(index);
	if(document.all("IDType"+index).value==""&&document.all("IDNo"+index).value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		document.all("IDNo"+index).value="";
	}
}      

/*********************************************************************
 *  Click�¼�����˫���������˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsured()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonMain.html" );
	}
}           

    

/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPol(cArr) 
{
	try 
	{
	    try { document.all('PrtNo').value = cArr[6]; } catch(ex) { };
	    try { document.all('ManageCom').value = cArr[12]; } catch(ex) { };
	    try { document.all('SaleChnl').value = cArr[15]; } catch(ex) { };
	    try { document.all('AgentCom').value = cArr[13]; } catch(ex) { };
	    try { document.all('AgentType').value = cArr[14]; } catch(ex) { };
	    try { document.all('AgentCode').value = cArr[87]; } catch(ex) { };
	    try { document.all('AgentGroup').value = cArr[88]; } catch(ex) { };
	    //try { document.all('Handler').value = cArr[82]; } catch(ex) { };
	    //try { document.all('AgentCode1').value = cArr[89]; } catch(ex) { };
	    try { document.all('Remark').value = cArr[90]; } catch(ex) { };
	
	    try { document.all('ContNo').value = cArr[1]; } catch(ex) { };
	    try { document.all('Mult').value = cArr[38]; } catch(ex) { };
	    
	    //try { document.all('Amnt').value = cArr[43]; } catch(ex) { };
	    try { document.all('CValiDate').value = cArr[29]; } catch(ex) { };
	    try { document.all('PolApplyDate').value = cArr[128]; } catch(ex) { };
	    try { document.all('HealthCheckFlag').value = cArr[72]; } catch(ex) { };
	    try { document.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
	    try { document.all('PayLocation').value = cArr[59]; } catch(ex) { };
	    try { document.all('PayMode').value = cArr[58]; } catch(ex) { };
	    try { document.all('BankCode').value = cArr[102]; } catch(ex) { };
	    try { document.all('BankAccNo').value = cArr[103]; } catch(ex) { };
	    try { document.all('AccName').value = cArr[118]; } catch(ex) { };
	    try { document.all('LiveGetMode').value = cArr[98]; } catch(ex) { };
	    try { document.all('BonusGetMode').value = cArr[100]; } catch(ex) { };
	    try { document.all('AutoPayFlag').value = cArr[65]; } catch(ex) { };
	    try { document.all('InterestDifFlag').value = cArr[66]; } catch(ex) { };
     
	    try { document.all('InsuYear').value = cArr[111]; } catch(ex) { };
	    try { document.all('InsuYearFlag').value = cArr[110]; } catch(ex) { };
	    try { document.all('PolTypeFlag').value = cArr[69]; } catch(ex) { };
	    try { document.all('InsuredPeoples').value = cArr[24]; } catch(ex) { };
	    try { document.all('InsuredAppAge').value = cArr[22]; } catch(ex) { };
	    
	    try { document.all('PayIntv').value = cArr[57]; } catch(ex) { };

	    try { document.all('PayEndYear').value = cArr[109]; } catch(ex) { };
	    try { document.all('PayEndYearFlag').value = cArr[108]; } catch(ex) { };	   

	    try { document.all('StandbyFlag1').value = cArr[78]; } catch(ex) { };
	    try { document.all('StandbyFlag2').value = cArr[79]; } catch(ex) { };
	    try { document.all('StandbyFlag3').value = cArr[80]; } catch(ex) { };
	    
	} catch(ex) {
	  alert("displayPol err:" + ex + "\ndata is:" + cArr);
	}
}

/*********************************************************************
 *  �ѱ����е�Ͷ������Ϣ��ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppnt(cArr) 
{
	// ��LCAppntInd��ȡ����
	try { document.all('AppntCustomerNo').value = cArr[1]; } catch(ex) { };
	try { document.all('AppntRelationToInsured').value = cArr[4]; } catch(ex) { };
	try { document.all('AppntPassword').value = cArr[5]; } catch(ex) { };
	try { document.all('AppntName').value = cArr[6]; } catch(ex) { };
	try { document.all('AppntSex').value = cArr[7]; } catch(ex) { };
	try { document.all('AppntBirthday').value = cArr[8]; } catch(ex) { };
	try { document.all('AppntNativePlace').value = cArr[9]; } catch(ex) { };
	try { document.all('AppntNationality').value = cArr[10]; } catch(ex) { };
	try { document.all('AppntMarriage').value = cArr[11]; } catch(ex) { };
	try { document.all('AppntMarriageDate').value = cArr[12]; } catch(ex) { };
	try { document.all('AppntOccupationType').value = cArr[13]; } catch(ex) { };
	try { document.all('AppntStartWorkDate').value = cArr[14]; } catch(ex) { };
	try { document.all('AppntSalary').value = cArr[15]; } catch(ex) { };
	try { document.all('AppntHealth').value = cArr[16]; } catch(ex) { };
	try { document.all('AppntStature').value = cArr[17]; } catch(ex) { };
	try { document.all('AppntAvoirdupois').value = cArr[18]; } catch(ex) { };
	try { document.all('AppntCreditGrade').value = cArr[19]; } catch(ex) { };
	try { document.all('AppntIDType').value = cArr[20]; } catch(ex) { };
	try { document.all('AppntProterty').value = cArr[21]; } catch(ex) { };
	try { document.all('AppntIDNo').value = cArr[22]; } catch(ex) { };
	try { document.all('AppntOthIDType').value = cArr[23]; } catch(ex) { };
	try { document.all('AppntOthIDNo').value = cArr[24]; } catch(ex) { };
	try { document.all('AppntICNo').value = cArr[25]; } catch(ex) { };
	try { document.all('AppntHomeAddressCode').value = cArr[26]; } catch(ex) { };
	try { document.all('AppntHomeAddress').value = cArr[27]; } catch(ex) { };
	try { document.all('AppntPostalAddress').value = cArr[28]; } catch(ex) { };
	try { document.all('AppntZipCode').value = cArr[29]; } catch(ex) { };
	try { document.all('AppntPhone').value = cArr[30]; } catch(ex) { };
	try { document.all('AppntBP').value = cArr[31]; } catch(ex) { };
	try { document.all('AppntMobile').value = cArr[32]; } catch(ex) { };
	try { document.all('AppntEMail').value = cArr[33]; } catch(ex) { };
	try { document.all('AppntJoinCompanyDate').value = cArr[34]; } catch(ex) { };
	try { document.all('AppntPosition').value = cArr[35]; } catch(ex) { };
	try { document.all('AppntGrpNo').value = cArr[36]; } catch(ex) { };
	try { document.all('AppntGrpName').value = cArr[37]; } catch(ex) { };
	try { document.all('AppntGrpPhone').value = cArr[38]; } catch(ex) { };
	try { document.all('AppntGrpAddressCode').value = cArr[39]; } catch(ex) { };
	try { document.all('AppntGrpAddress').value = cArr[40]; } catch(ex) { };
	try { document.all('AppntDeathDate').value = cArr[41]; } catch(ex) { };
	try { document.all('AppntRemark').value = cArr[42]; } catch(ex) { };
	try { document.all('AppntState').value = cArr[43]; } catch(ex) { };
	try { document.all('AppntWorkType').value = cArr[46]; } catch(ex) { };
	try { document.all('AppntPluralityType').value = cArr[47]; } catch(ex) { };
	try { document.all('AppntOccupationCode').value = cArr[48]; } catch(ex) { };
	try { document.all('AppntDegree').value = cArr[49]; } catch(ex) { };
	try { document.all('AppntGrpZipCode').value = cArr[50]; } catch(ex) { };
	try { document.all('AppntSmokeFlag').value = cArr[51]; } catch(ex) { };
	try { document.all('AppntRgtAddress').value = cArr[52]; } catch(ex) { };
	try { document.all('AppntHomeZipCode').value = cArr[53]; } catch(ex) { };
	try { document.all('AppntPhone2').value = cArr[54]; } catch(ex) { };

}

/*********************************************************************
 *  �ѱ����е�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppntGrp( cArr )
{
	// ��LCAppntGrp��ȡ����
	try { document.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
	try { document.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
	try { document.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
	try { document.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
	try { document.all('AppntPassword').value = cArr[4]; } catch(ex) { };
	try { document.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
	try { document.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
	try { document.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
	try { document.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
	try { document.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
	try { document.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
	try { document.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
	try { document.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
	try { document.all('AppntAsset').value = cArr[13]; } catch(ex) { };
	try { document.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
	try { document.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
	try { document.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
	try { document.all('AppntComAera').value = cArr[17]; } catch(ex) { };
	try { document.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
	try { document.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
	try { document.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
	try { document.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
	try { document.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
	try { document.all('AppntFax1').value = cArr[23]; } catch(ex) { };
	try { document.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
	try { document.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
	try { document.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
	try { document.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
	try { document.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
	try { document.all('AppntFax2').value = cArr[29]; } catch(ex) { };
	try { document.all('AppntFax').value = cArr[30]; } catch(ex) { };
	try { document.all('AppntPhone').value = cArr[31]; } catch(ex) { };
	try { document.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
	try { document.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
	try { document.all('AppntEMail').value = cArr[34]; } catch(ex) { };
	try { document.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
	try { document.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
	try { document.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
	try { document.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
	try { document.all('AppntState').value = cArr[39]; } catch(ex) { };
	try { document.all('AppntRemark').value = cArr[40]; } catch(ex) { };
	try { document.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
	try { document.all('AppntOperator').value = cArr[42]; } catch(ex) { };
	try { document.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
	try { document.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
	try { document.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
	try { document.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
	try { document.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
	try { document.all('AppntPK').value = cArr[48]; } catch(ex) { };
	try { document.all('AppntfDate').value = cArr[49]; } catch(ex) { };
	try { document.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
}

/*********************************************************************
 *  �ѱ����еı�����������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolInsured(cArr) 
{
	// ��LCInsured��ȡ����
	try { document.all('CustomerNo').value = cArr[1]; } catch(ex) { };
	try { document.all('SequenceNo').value = cArr[2]; } catch(ex) { };
	try { document.all('InsuredGrade').value = cArr[3]; } catch(ex) { };
	try { document.all('RelationToInsured').value = cArr[4]; } catch(ex) { };
	try { document.all('Password').value = cArr[5]; } catch(ex) { };
	try { document.all('Name').value = cArr[6]; } catch(ex) { };
	try { document.all('Sex').value = cArr[7]; } catch(ex) { };
	try { document.all('Birthday').value = cArr[8]; } catch(ex) { };
	try { document.all('NativePlace').value = cArr[9]; } catch(ex) { };
	try { document.all('Nationality').value = cArr[10]; } catch(ex) { };
	try { document.all('Marriage').value = cArr[11]; } catch(ex) { };
	try { document.all('MarriageDate').value = cArr[12]; } catch(ex) { };
	try { document.all('OccupationType').value = cArr[13]; } catch(ex) { };
	try { document.all('StartWorkDate').value = cArr[14]; } catch(ex) { };
	try { document.all('Salary').value = cArr[15]; } catch(ex) { };
	try { document.all('Health').value = cArr[16]; } catch(ex) { };
	try { document.all('Stature').value = cArr[17]; } catch(ex) { };
	try { document.all('Avoirdupois').value = cArr[18]; } catch(ex) { };
	try { document.all('CreditGrade').value = cArr[19]; } catch(ex) { };
	try { document.all('IDType').value = cArr[20]; } catch(ex) { };
	try { document.all('Proterty').value = cArr[21]; } catch(ex) { };
	try { document.all('IDNo').value = cArr[22]; } catch(ex) { };
	try { document.all('OthIDType').value = cArr[23]; } catch(ex) { };
	try { document.all('OthIDNo').value = cArr[24]; } catch(ex) { };
	try { document.all('ICNo').value = cArr[25]; } catch(ex) { };
	try { document.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
	try { document.all('HomeAddress').value = cArr[27]; } catch(ex) { };
	try { document.all('PostalAddress').value = cArr[28]; } catch(ex) { };
	try { document.all('ZipCode').value = cArr[29]; } catch(ex) { };
	try { document.all('Phone').value = cArr[30]; } catch(ex) { };
	try { document.all('BP').value = cArr[31]; } catch(ex) { };
	try { document.all('Mobile').value = cArr[32]; } catch(ex) { };
	try { document.all('EMail').value = cArr[33]; } catch(ex) { };
	try { document.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
	try { document.all('Position').value = cArr[35]; } catch(ex) { };
	try { document.all('GrpNo').value = cArr[36]; } catch(ex) { };
	try { document.all('GrpName').value = cArr[37]; } catch(ex) { };
	try { document.all('GrpPhone').value = cArr[38]; } catch(ex) { };
	try { document.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
	try { document.all('GrpAddress').value = cArr[40]; } catch(ex) { };
	try { document.all('DeathDate').value = cArr[41]; } catch(ex) { };
	try { document.all('State').value = cArr[43]; } catch(ex) { };
	try { document.all('WorkType').value = cArr[46]; } catch(ex) { };
	try { document.all('PluralityType').value = cArr[47]; } catch(ex) { };
	try { document.all('OccupationCode').value = cArr[48]; } catch(ex) { };
	try { document.all('Degree').value = cArr[49]; } catch(ex) { };
	try { document.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
	try { document.all('SmokeFlag').value = cArr[51]; } catch(ex) { };
	try { document.all('RgtAddress').value = cArr[52]; } catch(ex) { };
	try { document.all('HomeZipCode').value = cArr[53]; } catch(ex) { };
	try { document.all('Phone2').value = cArr[54]; } catch(ex) { };
	return;

}
 

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( mOperate == 1 )	{           // ��ѯ������ϸ
			var tPolNo = arrResult[0][0];
			
			// ��ѯ������ϸ
			queryPolDetail( tPolNo );
		}
		
		if( mOperate == 2 ) {		// Ͷ������Ϣ	  
			var sqlid43 = "WbProposalInputSql43";
			var mySql43 = new SqlClass();
		    mySql43.setResourceName("app.WbProposalInputSql");
			mySql43.setSqlId(sqlid43);//ָ��ʹ��SQL��id
			mySql43.addSubPara(arrQueryResult[0][0]);//ָ���������
		    var sql43 = mySql43.getString();
	//		arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(sql43, 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			   displayAppnt(arrResult[0]);
			}

	  }
	  for( var i=1; i<=3; i++ )
		if( mOperate == "3"+i )	{		// ����������Ϣ
			var sqlid44 = "WbProposalInputSql44";
			var mySql44 = new SqlClass();
		    mySql44.setResourceName("app.WbProposalInputSql");
			mySql44.setSqlId(sqlid44);//ָ��ʹ��SQL��id
			mySql44.addSubPara(arrQueryResult[0][0]);//ָ���������
		    var sql44 = mySql44.getString();
	//	    arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(sql44, 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽����������Ϣ");
			} else {
			   displayInsured(arrResult[0],i);
			}
			
			break;

	  }
		if( mOperate == 4 )	{		// ������������Ϣ
			displaySubInsured(arrResult[0]);
	  }
	}
	mOperate = 0;		// �ָ���̬
	
	emptyUndefined(); 
}

function afterCodeSelect( cCodeName, Field )
{
 	//alert("cCodeName==="+cCodeName);
	 //�Զ���д��������Ϣ
	  var insurNo;
      if (cCodeName == "customertype1") {
        insurNo = 1;
        if (Field.value == "0") {
          //alert("1111111");          
          var index = Bnf1Grid.mulLineCount;
          Bnf1Grid.setRowColData(index-1, 4, document.all("AppntName").value);
          Bnf1Grid.setRowColData(index-1, 5, document.all("AppntIDType").value);
          Bnf1Grid.setRowColData(index-1, 6, document.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //�޸������˹�ϵ����
          Bnf1Grid.setRowColData(index-1, 7,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          Bnf1Grid.setRowColData(index-1, 10, document.all("AppntHomeAddress").value);
          Bnf1Grid.setRowColData(index-1, 11, document.all("AppntIDEndDate").value);
        }
        else if (Field.value == "1") {
        //alert("2222222");
          var index = Bnf1Grid.mulLineCount;
        Bnf1Grid.setRowColData(index-1, 4, document.all("Name" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 5, document.all("IDType" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 6, document.all("IDNo" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 7, "00");
        Bnf1Grid.setRowColData(index-1, 10, document.all("HomeAddress" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 11, document.all("IDEndDate" +insurNo).value);
				//tongmeng 2007-12-28 add
					//��������˲���Ϊ����
		var tempDeadBnf = Bnf1Grid.getRowColData(index-1, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	//Ĭ��Ϊ����������
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
          	alert('��������˲���Ϊ����!');
          	//alert("3333333");
          	Bnf1Grid.setRowColData(index-1, 3, '');
          	Bnf1Grid.setRowColData(index-1, 4, '');
          	Bnf1Grid.setRowColData(index-1, 5, '');
          	Bnf1Grid.setRowColData(index-1, 6, '');
          	Bnf1Grid.setRowColData(index-1, 7, '');
          	Bnf1Grid.setRowColData(index-1, 10, '');
          	Bnf1Grid.setRowColData(index-1, 11, '');
          	return ;
          }
        }
        return;
    }
    else if (cCodeName == "customertype2") {
        insurNo = 2;
        if (Field.value == "0") {
          //alert("1111111");
          var index = Bnf2Grid.mulLineCount;
          Bnf2Grid.setRowColData(index-1, 4, document.all("AppntName").value);
          Bnf2Grid.setRowColData(index-1, 5, document.all("AppntIDType").value);
          Bnf2Grid.setRowColData(index-1, 6, document.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //�޸������˹�ϵ����
          Bnf2Grid.setRowColData(index-1, 7,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          Bnf2Grid.setRowColData(index-1, 10, document.all("AppntHomeAddress").value);
          Bnf2Grid.setRowColData(index-1, 11, document.all("AppntIDEndDate").value);
        }
        else if (Field.value == "1") {
        //alert("2222222");
          var index = Bnf2Grid.mulLineCount;
        Bnf2Grid.setRowColData(index-1, 4, document.all("Name" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 5, document.all("IDType" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 6, document.all("IDNo" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 7, "00");
        Bnf2Grid.setRowColData(index-1, 10, document.all("HomeAddress" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 11, document.all("IDEndDate" +insurNo).value);
				//tongmeng 2007-12-28 add
					//��������˲���Ϊ����
		var tempDeadBnf = Bnf2Grid.getRowColData(index-1, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	//Ĭ��Ϊ����������
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
          	alert('��������˲���Ϊ����!');
          	//alert("3333333");
          	Bnf2Grid.setRowColData(index-1, 3, '');
          	Bnf2Grid.setRowColData(index-1, 4, '');
          	Bnf2Grid.setRowColData(index-1, 5, '');
          	Bnf2Grid.setRowColData(index-1, 6, '');
          	Bnf2Grid.setRowColData(index-1, 7, '');
          	Bnf2Grid.setRowColData(index-1, 10, '');
          	Bnf2Grid.setRowColData(index-1, 11, '');
          	return ;
          }
        }
        return;
    }
    else if (cCodeName == "customertype3") {
        insurNo = 3;
        if (Field.value == "0") {
          //alert("1111111");
          var index = Bnf3Grid.mulLineCount;
          Bnf3Grid.setRowColData(index-1, 4, document.all("AppntName").value);
          Bnf3Grid.setRowColData(index-1, 5, document.all("AppntIDType").value);
          Bnf3Grid.setRowColData(index-1, 6, document.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //�޸������˹�ϵ����
          Bnf3Grid.setRowColData(index-1, 7,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          Bnf3Grid.setRowColData(index-1, 10, document.all("AppntHomeAddress").value);
          Bnf3Grid.setRowColData(index-1, 11, document.all("AppntIDEndDate").value);
        }
        else if (Field.value == "1") {
        //alert("2222222");
          var index = Bnf3Grid.mulLineCount;
        Bnf3Grid.setRowColData(index-1, 4, document.all("Name" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 5, document.all("IDType" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 6, document.all("IDNo" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 7, "00");
        Bnf3Grid.setRowColData(index-1, 10, document.all("HomeAddress" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 11, document.all("IDEndDate" +insurNo).value);
				//tongmeng 2007-12-28 add
					//��������˲���Ϊ����
		var tempDeadBnf = Bnf3Grid.getRowColData(index-1, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	//Ĭ��Ϊ����������
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
          	alert('��������˲���Ϊ����!');
          	//alert("3333333");
          	Bnf3Grid.setRowColData(index-1, 3, '');
          	Bnf3Grid.setRowColData(index-1, 4, '');
          	Bnf3Grid.setRowColData(index-1, 5, '');
          	Bnf3Grid.setRowColData(index-1, 6, '');
          	Bnf3Grid.setRowColData(index-1, 7, '');
          	Bnf3Grid.setRowColData(index-1, 10, '');
          	return ;
          }
        }
        return;
    }
    if (cCodeName == "AgentCom" || cCodeName == "salechnl") {
    	initManageCom();
    }
}

/*********************************************************************
 *  ���ݲ�ѯ���ص���Ϣ��ѯͶ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPolDetail()
{

	emptyForm(); 
	//var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	//parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
	//alert(BussNoType);
	parent.fraTitle.window.location = "./WbProposalQueryDetail.jsp?PrtNo=" + prtNo +"&BussNoType=" + BussNoType;
    //document.all('Name2').value = '�����˶�';
    document.all('BussNoType').value = BussNoType;
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

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

//*************************************************************
//�����˿ͻ��Ų�ѯ��Ť�¼�
function queryInsuredNo(i) {
  if (document.all("CustomerNo"+i).value == "") {
    showInsured1(i);
  //} else if (loadFlag != "1" && loadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  }  else {
	    var sqlid45 = "WbProposalInputSql45";
		var mySql45 = new SqlClass();
		var CustomerNo45 = document.all("CustomerNo"+i).value;
	    mySql45.setResourceName("app.WbProposalInputSql");
		mySql45.setSqlId(sqlid45);//ָ��ʹ��SQL��id
		mySql45.addSubPara(CustomerNo45);//ָ���������
	    var sql45 = mySql45.getString();
  //  arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + document.all("CustomerNo"+i).value + "'", 1, 0);
       arrResult = easyExecSql(sql45, 1, 0);
    if (arrResult == null) {
      alert("δ�鵽����������Ϣ");
      displayInsured(new Array(),i);
      emptyUndefined(); 
    } else {
      displayInsured(arrResult[0],i);
      getAge2(i);
    }
  }
}

//*************************************************************
//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() {
  if (document.all("AppntNo").value == "" ) {
    showAppnt1();
  //} else if (loadFlag != "1" && loadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  } else {
	   var sqlid46 = "WbProposalInputSql46";
		var mySql46 = new SqlClass();
		var AppntNo46 = document.all("AppntNo").value;
	    mySql46.setResourceName("app.WbProposalInputSql");
		mySql46.setSqlId(sqlid46);//ָ��ʹ��SQL��id
		mySql46.addSubPara(AppntNo46);//ָ���������
	    var sql46 = mySql46.getString();
 //   arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
      arrResult = easyExecSql(sql46, 1, 0);
    if (arrResult == null) {
      alert("δ�鵽Ͷ������Ϣ");
      displayAppnt(new Array());
      emptyUndefined(); 
    } else {
      displayAppnt(arrResult[0]);
      getAge();
    }
  }
}

//*************************************************************
//Ͷ�����뱻������ͬѡ����¼�
function isSamePerson() {  
  //��Ӧ��ѡͬһ�˵����
  if (fm.SamePersonFlag1.checked == false) {
    document.all('DivInsuredBase1').style.display = ""; 
    //document.all('RelationToMainInsured1').readonly = false;
    //document.all('RelationToAppnt1').readonly = false; 
  } 
  else 
  {
  	if ((fm.AppntRelationToInsured.value!="" && fm.AppntRelationToInsured.value=="00") || (fm.RelationToAppnt1.value!="" && fm.RelationToAppnt1.value=="00"))//��Ӧ��ͬһ�ˣ��ִ򹳵���� 
	  {
	  	document.all('DivInsuredBase1').style.display = "none";
	  	fm.RelationToAppnt1.value="00";
	  	fm.AppntRelationToInsured.value="00";
	  }
	  else //��Ӧδѡͬһ�ˣ��ִ򹳵����
	  {
	    document.all('DivInsuredBase1').style.display = "";
	    fm.SamePersonFlag1.checked = false;
	    alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
	  }
   }   
  
  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1) + "1";
	      if (document.all('DivInsuredBase1').style.display == "none") {
	        //alert(insuredName);
	        //alert(fm.elements[elementsNum].value);
	        document.all(insuredName).value = fm.elements[elementsNum].value;
	        document.all('Age1').value=calAgeNew(document.all("Birthday1").value,document.all("PolApplyDate").value);
	      }
	      else {
	        document.all(insuredName).value = "";
	      }
	    }
	    catch (ex) {}
	  }
	}

} 

//*************************************************************
//�������˺�һ��ѡ����¼�
function theSameToFirstAccount() {
  //�򹳵����
  if (fm.theSameAccount.checked == true) {
    document.all('BankCode').value = document.all('NewBankCode').value;
    document.all('AccName').value = document.all('NewAccName').value;
    document.all('BankAccNo').value = document.all('NewBankAccNo').value;
  }
  //��Ӧ��ѡ�����
  else if (fm.theSameAccount.checked == false) {
    document.all('BankCode').value = '';
    document.all('AccName').value = '';
    document.all('BankAccNo').value = '';
  }  

} 

//*************************************************************
//Ͷ������������¼�
function initDeleteAppnt() {
  var appntName;
  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {  
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      appntName = fm.elements[elementsNum].name;	        //alert(appntName);
	        //alert(insuredName);
	        document.all(appntName).value = "";
		    }
		    catch (ex) {}
	    
	}
  }

} 

//*************************************************************
//��������������¼�
function initDeleteInsured(sInsuNum) {

  //alert("initDeleteInsured");
  //document.all("RelationToMainInsured"+sInsuNum).value = "";
  document.all("RelationToAppnt"+sInsuNum).value = ""; 
  //document.all("RelationToMainInsuredName"+sInsuNum).value = "";
  document.all("RelationToAppntName"+sInsuNum).value = "";
  
  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {  
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1) + sInsuNum;	        //alert(insuredName);
	        //alert(insuredName);
	        document.all(insuredName).value = "";
		    }
		    catch (ex) {}
	    
	}
  }

} 

//*************************************************************
//����ʱУ��Ͷ�����뱻������ͬѡ����¼�
function verifySamePerson() {
  if (fm.SamePersonFlag.checked == true) {
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
  	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
  	    try {
  	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
  	      if (document.all('DivLCInsured').style.display == "none") {
  	        document.all(insuredName).value = fm.elements[elementsNum].value;
  	      }
  	      else {
  	        document.all(insuredName).value = "";
  	      }
  	    }
  	    catch (ex) {}
  	  }
	  } 
  }
  else if (fm.SamePersonFlag.checked == false) { 

  }  
	
}  


/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt(cArr) 
{
    //���Ͷ������Ϣ
    //initDeleteAppnt();//--���������
	// ��LDPerson��ȡ����
	try { document.all('AppntNo').value = cArr[0]; } catch(ex) { };
	try { document.all('AppntName').value = cArr[1]; } catch(ex) { };
	try { document.all('AppntSex').value = cArr[2]; } catch(ex) { };
	try { document.all('AppntBirthday').value = cArr[3]; } catch(ex) { };
	try { document.all('AppntIDType').value = cArr[4]; } catch(ex) { };
	try { document.all('AppntIDNo').value = cArr[5]; } catch(ex) { };
	try { document.all('AppntRelationToInsured').value = ''; } catch(ex) { };
	try { document.all('AppntAge').value=calAge(cArr[3]) } catch(ex) { };
	try { document.all('AppntNativePlace').value = cArr[7]; } catch(ex) { };
	try { document.all('AppntRgtAddress').value = cArr[9]; } catch(ex) { };
	try { document.all('AppntMarriage').value = cArr[10]; } catch(ex) { };
	try { document.all('AppntPostalAddress').value = ''; } catch(ex) { };
	try { document.all('AppntZipCode').value = ''; } catch(ex) { };
	try { document.all('AppntHomeAddress').value = ''; } catch(ex) { };
	try { document.all('AppntHomeZipCode').value = ''; } catch(ex) { };	
	try { document.all('AppntPhone').value = ''; } catch(ex) { };
	try { document.all('AppntPhone2').value = ''; } catch(ex) { };
	try { document.all('AppntEMail').value = ''; } catch(ex) { };
	try { document.all('AppntGrpName').value = cArr[41]; } catch(ex) { };
	try { document.all('AppntWorkType').value = cArr[27]; } catch(ex) { };
	try { document.all('AppntPluralityType').value = cArr[28]; } catch(ex) { };
	try { document.all('AppntOccupationCode').value = cArr[26]; } catch(ex) { };	
	try { document.all('AppntOccupationType').value = cArr[25]; } catch(ex) { };
	
	//try { document.all('AppntDegree').value = cArr[51]; } catch(ex) { };
	//try { document.all('AppntGrpZipCode').value = cArr[52]; } catch(ex) { };
	//try { document.all('AppntSmokeFlag').value = cArr[53]; } catch(ex) { };
	//try { document.all('AppntRgtAddress').value = cArr[54]; } catch(ex) { };

}
 

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayInsured(cArr,i) 
{
    //��ձ�������Ϣ
    //initDeleteInsured(i); //--�������
	// ��LDPerson��ȡ����
	try { document.all('CustomerNo'+i).value = cArr[0]; } catch(ex) { };
	try { document.all('Name'+i).value = cArr[1]; } catch(ex) { };
	try { document.all('Sex'+i).value = cArr[2]; } catch(ex) { };
	try { document.all('Birthday'+i).value = cArr[3]; } catch(ex) { };
	try { document.all('IDType'+i).value = cArr[4]; } catch(ex) { };
	try { document.all('IDNo'+i).value = cArr[5]; } catch(ex) { };
	//try { document.all('RelationToMainInsured'+i).value = ''; } catch(ex) { };
	try { document.all('RelationToAppnt'+i).value = ''; } catch(ex) { };
	try { document.all('Age'+i).value=calAge(cArr[3]) } catch(ex) { };
	try { document.all('NativePlace'+i).value = cArr[7]; } catch(ex) { };
	try { document.all('RgtAddress'+i).value = cArr[9]; } catch(ex) { };
	try { document.all('Marriage'+i).value = cArr[10]; } catch(ex) { };
	try { document.all('HomeAddress'+i).value = ''; } catch(ex) { };
	try { document.all('HomeZipCode'+i).value = ''; } catch(ex) { };	
	try { document.all('Phone'+i).value = ''; } catch(ex) { };
	try { document.all('Phone2'+i).value = ''; } catch(ex) { };
	try { document.all('GrpName'+i).value = cArr[41]; } catch(ex) { };
	try { document.all('WorkType'+i).value = cArr[27]; } catch(ex) { };
	try { document.all('PluralityType'+i).value = cArr[28]; } catch(ex) { };
	try { document.all('OccupationCode'+i).value = cArr[26]; } catch(ex) { };	
	try { document.all('OccupationType'+i).value = cArr[25]; } catch(ex) { };
	
}

//*********************************************************************
function showAppnt1()
{
//alert(mOperate);
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}           

//*********************************************************************
function showInsured1(i)
{
	if( mOperate == 0 )
	{
		mOperate = "3"+i;
		//alert(mOperate);
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}  

function isSamePersonQuery(SequenceNo) {
  if(SequenceNo == 1)
  { 	  
  	  fm.SamePersonFlag1.checked = true;
	  document.all("divSamePerson1").style.display = "";
	  document.all("DivInsuredBase1").style.display = "none";
	  //document.all('RelationToMainInsured1').readonly = true;
      //document.all('RelationToAppnt1').readonly = true;
	  document.getElementById("img11").src="../common/images/butCollapse.gif";
  }  
}

//�����¼��
function QuestInput()
{
	cProposalNo = fm.ProposalNo.value;  //��������
	
	if(cProposalNo == "")
	{
		alert("����Ͷ�����ţ����ȱ���!");
	}
	else
	
	{	
		window.open("../uw/MSQuestInputMain.jsp?ContNo="+prtNo+"&Flag=0&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID,"window1");
		//window.open("../uw/QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");
	}
}

//��ʾͶ��������
function showAppntAge() {
  var age = calAge(document.all("AppntBirthday").value);
  var today = new Date();
  
  document.all("AppntBirthday").title = "Ͷ���˵����� " + today.toLocaleString() 
                                + " \n������Ϊ��" + age + " ��!";
}

//��ʾ����������
function showAge() {
  var age = calAge(document.all("Birthday").value);
  var today = new Date();
  
  document.all("Birthday").title = "�����˵����� " + today.toLocaleString() 
                           + " \n������Ϊ��" + age + " ��!";
}

//����Ͷ���˳������ڣ�����գ������֤���У�������֤ȡ��ȡ�������ؿո�;
function checkBirthday()
{
	try{
		  var strBrithday = "";
		  if(trim(document.all("AppntBirthday").value)==""||document.all("AppntBirthday").value==null)
		  {
		  	if (trim(document.all("AppntIDType").value) == "0") 
		  	 {
		  	   strBrithday=	getBirthdatByIdNo(document.all("AppntIDNo").value);
		  	   if(strBrithday=="") passVerify=false;
		  	   
	           document.all("AppntBirthday").value= strBrithday;
		  	 }	     
	      }	
	 }
	 catch(e)
	 {
	 	
	 }
}

//���������˺�¼���Ƿ�淶
function verifyBankAccNo()
{
	if(document.all("NewBankAccNo").value!=""&&document.all("NewBankAccNo").value!=null
		  	&&document.all("NewBankCode").value!=""&&document.all("NewBankCode").value!=null)
	{
		  if(!checkBankAccNo(document.all("NewBankCode").value,document.all("NewBankAccNo").value))
		  	  return false;
	}	
	if(document.all("BankAccNo").value!=""&&document.all("BankAccNo").value!=null
		  	&&document.all("BankCode").value!=""&&document.all("BankCode").value!=null)
	{
		  if(!checkBankAccNo(document.all("BankCode").value,document.all("BankAccNo").value))
			  return false;
	}
	return true;
}

//У��¼��������Ƿ���ҪУ�飬�����Ҫ����true,���򷵻�false
//cSysVarΪ���⴦������
function needVerifyRiskcode(cSysVar)
{
  	  
  try { 
  	   var riskcode=document.all("RiskCode").value;
 	   
//       var tSql = "select Sysvarvalue from LDSysVar where Sysvar='"+cSysVar+"'";   
       
        var sqlid47 = "WbProposalInputSql47";
		var mySql47 = new SqlClass();
	    mySql47.setResourceName("app.WbProposalInputSql");
		mySql47.setSqlId(sqlid47);//ָ��ʹ��SQL��id
		mySql47.addSubPara(cSysVar);//ָ���������
	    var tSql = mySql47.getString();
       
       var tResult = easyExecSql(tSql, 1, 1, 1);       
       var strRiskcode = tResult[0][0];
       var strValue=strRiskcode.split("/");
       var i=0;
	   while(i<strValue.length)
	   {
	   	if(riskcode==strValue[i])
	   	{
           return false;
	   	}
	   	i++;
	   }   	   
  	 }
  	catch(e)
  	 {}
  	 
  	return true; 
  	
	
}
  
function queryAgent()
{
   /*
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
	*/
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
//	var strSql = "select AgentCode,Name,AgentGroup,ManageCom,(select name from ldcom where comcode=managecom) from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
   
	var sqlid48 = "WbProposalInputSql48";
	var mySql48 = new SqlClass();
	 mySql48.setResourceName ("app.WbProposalInputSql");
    mySql48.setSqlId(sqlid48);//ָ��ʹ��SQL��id
	mySql48.addSubPara(cAgentCode);//ָ���������
	var strSql = mySql48.getString();
    
	
	var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {      
      //fm.AgentManageComName.value = arrResult[0][4];
      //fm.AgentManageCom.value = arrResult[0][3];
      //fm.ManageComName.value = arrResult[0][4];
      //fm.ManageCom.value = arrResult[0][3];
      fm.AgentGroup.value = arrResult[0][2];
      fm.AgentName.value = arrResult[0][1];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
	
	initManageCom();
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	//fm.AgentGroup.value = arrResult[0][1];
  }
}

function queryAgent2()
{
    /*
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
	*/
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
//	var strSql = "select AgentCode,Name,AgentGroup,ManageCom,(select name from ldcom where comcode=managecom) from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   
	var sqlid49 = "WbProposalInputSql49";
	var mySql49 = new SqlClass();
	var ManageCom49 = document.all('ManageCom').value;
	 mySql49.setResourceName ("app.WbProposalInputSql");
    mySql49.setSqlId(sqlid49);//ָ��ʹ��SQL��id
	mySql49.addSubPara(cAgentCode);//ָ���������
	mySql49.addSubPara(ManageCom49);//ָ���������
	var strSql = mySql49.getString();
	
	var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {      
      //fm.AgentManageComName.value = arrResult[0][4];
      //fm.AgentManageCom.value = arrResult[0][3];
      //fm.ManageComName.value = arrResult[0][4];
      //fm.ManageCom.value = arrResult[0][3];
      fm.AgentGroup.value = arrResult[0][2];
      fm.AgentName.value = arrResult[0][1];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
	
	initManageCom();	
}

//���ӱ�������Ϣ
function addInsuredInput()
{  
  var i ;
  for( i=1 ;i<=3; i++)
  {
  	if(document.getElementById("DivInsuredAll"+i).style.display == "none")
  	{
  		document.getElementById("DivInsuredAll"+i).style.display = "";
  		alert("���ӳɹ���");
  		break;
  	}
  }
  
  queryInsuredInput();
}

//ɾ����������Ϣ
function deleteInsuredInput()
{      
  for( var i=3 ;i>=1; i--)
  {
  	if(document.getElementById("DivInsuredAll"+i).style.display == "")
  	{
  		document.getElementById("DivInsuredAll"+i).style.display = "none";
  		//���ӱ�����¼������������;
  		//�����������Ϣ
  		initDeleteInsured(i);  		
  		//������֡������˵���Ϣ  		
  		for( var j=1 ;j<=3; j++ )
  		{  			
  			if(document.getElementById("divRisk"+i+j).style.display == "")
  			{
  				//alert("Risk" +i +j +"Grid.clearData('Risk" +i +j +"Grid')");
  				eval("Risk" +i +j +"Grid.clearData('Risk" +i +j +"Grid')");
  				eval("Risk" +i +j +"Grid.addOne('Risk" +i +j +"Grid')");
  			}  				
  		}
  		eval("Bnf" +i +"Grid.clearData('Bnf" +i +"Grid')");
  		eval("DealType" +i +"Grid.clearData('DealType" +i +"Grid')");
  		eval("Bnf" +i +"Grid.addOne('Bnf" +i +"Grid')");
  		eval("DealType" +i +"Grid.addOne('DealType" +i +"Grid')");
  		 		
  		alert("ɾ���ɹ���");
  		break;
  	}
  }
  
  queryInsuredInput();
  	
}

//��ѯ��������ɾ��ť״̬
function queryInsuredInput()
{    
  	if((DivInsuredAll3).style.display == "")
  	    document.all("addInsured").disabled = true;
  	else
  	    document.all("addInsured").disabled = false;
  	    
  	if((DivInsuredAll1).style.display == "none")
  	    document.all("deleteInsured").disabled = true; 
  	else
  	    document.all("deleteInsured").disabled = false; 	
}

//��ʼ��xml��ѯ��������ʾ���
function initInsuredInput(sInsuNum)
{    
    var name;
  	for(var i=1;i<=sInsuNum ; i++)
  	{
  	   name ="DivInsuredAll"+i;
  	   document.getElementById(name).style.display = "";
  	}
  	
  	queryInsuredInput(); 	
}

//����������Ϣ
// sRiskNum  ���������
function addMainRiskInput(sRiskNum)
{  
  var i ;
  for( i=1 ;i<=3; i++)
  {
  	if(document.getElementById("divRisk"+sRiskNum+i).style.display == "none")
  	{
  		document.getElementById("divRisk"+sRiskNum+i).style.display = "";
  		alert("���ӳɹ���");
  		break;
  	}
  }
  
  queryMainRiskInput();
}

//ɾ��������Ϣ
// sRiskNum  ���������
function deleteMainRiskInput(sRiskNum)
{      
  var i ;
  for( i=3 ;i>=1; i--)
  {
  	if(document.getElementById("divRisk"+sRiskNum+i).style.display == "")
  	{
  		document.getElementById("divRisk"+sRiskNum+i).style.display = "none";
  		//alert("Risk" +sRiskNum +i +"Grid");
  		//���grid����
  		eval("Risk" +sRiskNum +i +"Grid.clearData('Risk" +sRiskNum +i +"Grid')");
  		eval("Risk" +sRiskNum +i +"Grid.addOne('Risk" +sRiskNum +i +"Grid')");
  		alert("ɾ���ɹ���");
  		break;
  	}
  }
  
  queryMainRiskInput();
  	
}

//��ѯ������ɾ��ť״̬
function queryMainRiskInput()
{    
  for(var i=1 ;i<=3; i++)
  {
     if(document.getElementById("divRisk"+i+3).style.display == "")
  	    document.all("addMainRisk"+i).disabled = true;
  	 else
  	    document.all("addMainRisk"+i).disabled = false;
  	    
  	 if(document.getElementById("divRisk"+i+1).style.display == "none")
  		document.all("deleteMainRisk"+i).disabled = true;
  	 else
  	    document.all("deleteMainRisk"+i).disabled = false;
  	   
  	 if(document.getElementById("divRisk"+i+2).style.display == "none")
  	    document.all("MainRiskNo"+i+"1").value = "-1";
  	 else
  	    document.all("MainRiskNo"+i+"1").value = "1";
  }  
  	 	
}

//��ʼ��xml��ѯ������������ʾ���
function initMainRiskInput(sInsu,sRiskNum)
{
        //alert(sInsu);
        //alert("sRiskNum:"+sRiskNum);     
  		for(var j=1;j<=sRiskNum ; j++)
	  	{
	  	   document.getElementById("divRisk"+sInsu+j).style.display = "";
	  	}
  	
  	queryMainRiskInput(); 	
}

//��ʼ��������Ϣ
function showCodeName()
{
	if(fm.ManageCom.value!="")
	{
//		var strSql = "select Name from LDCom where ComCode= '"+document.all('ManageCom').value+"'";
		
		var sqlid50 = "WbProposalInputSql50";
		var mySql50 = new SqlClass();
		var ManageCom50 = document.all('ManageCom').value;
		 mySql50.setResourceName ("app.WbProposalInputSql");
	    mySql50.setSqlId(sqlid50);//ָ��ʹ��SQL��id
		mySql50.addSubPara(ManageCom50);//ָ���������
		var strSql = mySql50.getString();
		 var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.ManageComName.value = arrResult[0][0];
	      }
	}
	//showOneCodeNameRefresh('comcode','ManageCom','ManageComName');
	if(fm.AgentCom.value!="")
	{
//		var strSql = "select Name from LACom where AgentCom= '"+document.all('AgentCom').value+"'";
		
		var sqlid51 = "WbProposalInputSql51";
		var mySql51 = new SqlClass();
		var AgentCom51 = document.all('AgentCom').value;
		 mySql51.setResourceName ("app.WbProposalInputSql");
	    mySql51.setSqlId(sqlid51);//ָ��ʹ��SQL��id
		mySql51.addSubPara(AgentCom51);//ָ���������
		var strSql = mySql51.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.InputAgentComName.value = arrResult[0][0];
	      }
	}
	//showOneCodeNameRefresh('AgentCom','AgentCom','InputAgentComName');
	/*if(fm.AgentManageCom.value!="")
	{
		var strSql = "select Name from LDCom where ComCode= '"+document.all('AgentManageCom').value+"'";
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.AgentManageComName.value = arrResult[0][0];
	      }
	}*/
	if(fm.AppntOccupationCode.value!="")
	{
//		var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all('AppntOccupationCode').value+"'";
	    
		var sqlid52 = "WbProposalInputSql52";
		var mySql52 = new SqlClass();
		var AppntOccupationCode52 = document.all('AppntOccupationCode').value;
		 mySql52.setResourceName ("app.WbProposalInputSql");
	    mySql52.setSqlId(sqlid52);//ָ��ʹ��SQL��id
		mySql52.addSubPara(AppntOccupationCode52);//ָ���������
		var strSql = mySql52.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.AppntOccupationCodeName.value = arrResult[0][0];
	      }
	}
	
	//showOneCodeNameRefresh('comcode','AgentManageCom','AgentManageComName');
	if(fm.SaleChnl.value!="")
	{
//		var strSql = "select codename from ldcode where codetype = 'salechnl' and code='"+document.all('SaleChnl').value+"'";
	  
		var sqlid53 = "WbProposalInputSql53";
		var mySql53 = new SqlClass();
		var SaleChnl53 = document.all('SaleChnl').value;
		 mySql53.setResourceName ("app.WbProposalInputSql");
	    mySql53.setSqlId(sqlid53);//ָ��ʹ��SQL��id
		mySql53.addSubPara(SaleChnl53);//ָ���������
		var strSql = mySql53.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.SaleChnlName.value = arrResult[0][0];
	      }
	}
	/*if(fm.SaleChnl.value!="")
	showOneCodeNameRefresh('salechnl','SaleChnl','SaleChnlName');*/
	/*if(fm.ScanManageCom.value!="")
	showOneCodeName('comcode','ScanManageCom','ScanManageComName');*/
	if(fm.AppntSex.value!="")
	showOneCodeNameRefresh('Sex','AppntSex','AppntSexName');
	if(fm.AppntRelationToInsured.value!="")
	showOneCodeNameRefresh('Relation','AppntRelationToInsured','RelationToInsuredName');
	if(fm.AppntIDType.value!="")
	showOneCodeNameRefresh('IDType','AppntIDType','AppntIDTypeName');
	if(fm.AppntNativePlace.value!="")
	showOneCodeNameRefresh('NativePlace','AppntNativePlace','AppntNativePlaceName');
	if(fm.AppntMarriage.value!="")
	showOneCodeNameRefresh('Marriage','AppntMarriage','AppntMarriageName');
	//if(fm.AppntOccupationCode.value!="")
	//showOneCodeNameRefresh('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	if(fm.AppntOccupationType.value!="")
	showOneCodeNameRefresh('OccupationType','AppntOccupationType','AppntOccupationTypeName');	
	if(fm.AppntOccupationType.value!="")
	showOneCodeNameRefresh('ibrmsflag','AppntSocialInsuFlag','AppntSocialInsuFlagName');
	
	if(fm.NewPayMode.value!="")
	{
//		var strSql = "select codename from ldcode where codetype = 'paylocation2' and code='"+document.all('NewPayMode').value+"'";
	   
		var sqlid54 = "WbProposalInputSql54";
		var mySql54 = new SqlClass();
		var NewPayMode54 = document.all('NewPayMode').value;
		 mySql54.setResourceName ("app.WbProposalInputSql");
	    mySql54.setSqlId(sqlid54);//ָ��ʹ��SQL��id
		mySql54.addSubPara(NewPayMode54);//ָ���������
		var strSql = mySql54.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.NewPayModeName.value = arrResult[0][0];
	      }
	}
	/*if(fm.NewPayMode.value!="")
	showOneCodeNameRefresh('paylocation2','NewPayMode','NewPayModeName');	*/
	if(fm.NewBankCode.value!="")
	showOneCodeNameRefresh('bank','NewBankCode','AppntBankCodeName');
	if(fm.PayLocation.value!="")
	{
//		var strSql = "select codename from ldcode where codetype = 'paylocation' and code='"+document.all('PayLocation').value+"'";
	   
		var sqlid55 = "WbProposalInputSql55";
		var mySql55 = new SqlClass();
		var PayLocation55 = document.all('PayLocation').value;
		 mySql55.setResourceName ("app.WbProposalInputSql");
	    mySql55.setSqlId(sqlid55);//ָ��ʹ��SQL��id
		mySql55.addSubPara(PayLocation55);//ָ���������
		var strSql = mySql55.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.PayLocationName.value = arrResult[0][0];
	      }
	}	
	/*if(fm.PayLocation.value!="")
	showOneCodeNameRefresh('paylocation','PayLocation','PayLocationName');	*/
	if(fm.BankCode.value!="")
	showOneCodeNameRefresh('bank','BankCode','ReNBankCodeName');	


    for(var i=1; i<=3; i++)
    {
        if(document.getElementById("DivInsuredAll"+i).style.display == "")
	  	{
	  	   // var RelationToMainInsured = "RelationToMainInsured"+i;
	  	    var RelationToAppnt = "RelationToAppnt"+i;
	  	    var Sex = "Sex"+i;
	  	    var IDType = "IDType"+i;
	  	    var NativePlace = "NativePlace"+i;
	  	    var Marriage = "Marriage"+i;
	  	    //var OccupationCode = "OccupationCode"+i;
	  	    var OccupationType = "OccupationType"+i;
	  	    var SocialInsuFlag = "SocialInsuFlag"+i;
	  	    
	  	    //var RelationToMainInsuredName = "RelationToMainInsuredName"+i;
	  	    var RelationToAppntName = "RelationToAppntName"+i;
	  	    var SexName = "SexName"+i;
	  	    var IDTypeName = "IDTypeName"+i;
	  	    var NativePlaceName = "NativePlaceName"+i;
	  	    var MarriageName = "MarriageName"+i;
	  	    //var OccupationCodeName = "OccupationCodeName"+i;
	  	    var OccupationTypeName = "OccupationTypeName"+i;
	  	    //if(document.all("RelationToMainInsured"+i).value!="")
	  		//showOneCodeName('Relation',RelationToMainInsured,RelationToMainInsuredName);
	  		var SocialInsuFlagName = "SocialInsuFlagName"+i;
	  		if(document.all("RelationToAppnt"+i).value!="")
	    	showOneCodeNameRefresh('Relation',RelationToAppnt,RelationToAppntName);
	    	if(document.all("Sex"+i).value!="")
			showOneCodeNameRefresh('Sex',Sex,SexName);
			if(document.all("IDType"+i).value!="")
			showOneCodeNameRefresh('IDType',IDType,IDTypeName);
			if(document.all("NativePlace"+i).value!="")
			showOneCodeNameRefresh('NativePlace',NativePlace,NativePlaceName);
			if(document.all("Marriage"+i).value!="")
			showOneCodeNameRefresh('Marriage',Marriage,MarriageName);
			//if(document.all("OccupationCode"+i).value!="")
			//showOneCodeNameRefresh('occupationcode',OccupationCode,OccupationCodeName);
			if(document.all("OccupationCode"+i).value!="")
			{
//				var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all("OccupationCode"+i).value+"'";
			   
				var sqlid56 = "WbProposalInputSql56";
				var mySql56 = new SqlClass();
				var OccupationCode56 = document.all("OccupationCode"+i).value;
				 mySql56.setResourceName ("app.WbProposalInputSql");
			    mySql56.setSqlId(sqlid56);//ָ��ʹ��SQL��id
				mySql56.addSubPara(OccupationCode56);//ָ���������
				var strSql = mySql56.getString();
				var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      document.all("OccupationCodeName"+i).value = arrResult[0][0];
			      }
			}
			if(document.all("OccupationType"+i).value!="")
			showOneCodeNameRefresh('OccupationType',OccupationType,OccupationTypeName);
			if(document.all("SocialInsuFlag"+i).value!="")
			showOneCodeNameRefresh('ibrmsflag',SocialInsuFlag,SocialInsuFlagName);
	  	}
	  	else
	  	   break ;
    		
    }
}

function clearRiskGridNone()
{
  for(var m = 1;m<=3;m++)
  	for(var n =1;n<=3;n++)
  	{
  		  eval("Risk"+m+n+"Grid.checkValue('Risk"+m+n+"Grid')");
		  var num ;
		  eval("num = Risk"+m+n+"Grid.mulLineCount");
		  if(num < 1 )
		  {
		  	document.getElementById("divRisk"+m+n).style.display = "none" ;
		  }
  	}  
}

//��ѯ��ͬ���������ݿ����Ϣ
//
function queryAllContInfo()
{
	emptyForm();
	queryAppntInfo();
    queryContNo();   
    initManageCom();
    queryInsuredInfo();
    queryRiskInfo();    
    getLCBnfInfo();
    getDealTypeInfo();
	getImpartInfo();//��֪
	showCodeName();
}

//����ѯ��ͬ�Լ��ɷ���Ϣ��������
function queryContNo()
{
//	var sSQL ="select NewBankCode,NewAccName,NewBankAccNo,BankCode,AccName,BankAccNo"
//	        +" ,'' "
//	         //�����޸ĵ����� 
//	        +" ,prtno,polapplydate"
//	        +" ,(select managecom from es_doc_main where doccode=l.prtno and subtype='UA001' and rownum=1)"
//	        +" ,agentcom,agentcode"
//	        +" ,managecom,agentgroup,salechnl,remark"
//	        +" ,NewPayMode,PayLocation,AutoPayFlag,RnewFlag,GetPolMode,OutPayFlag,PayIntv"
//	        +" ,(select a.name from LAAgent a where a.AgentCode=l.agentcode)"
//	        +" ,(select a.name from LACom a where a.AgentCom=l.agentcom)"
//	       //--�����޸ĵ�����
//	        +" ,signname "
//	        +" ,firsttrialdate "
//	        +" ,xqremindflag "
//			+" from lccont l where contno='"+fm.ProposalNo.value+"'";
	//prompt('',sSQL);
	var sqlid57 = "WbProposalInputSql57";
	var mySql57 = new SqlClass();
	var ProposalNo57 = fm.ProposalNo.value;
	 mySql57.setResourceName ("app.WbProposalInputSql");
    mySql57.setSqlId(sqlid57);//ָ��ʹ��SQL��id
	mySql57.addSubPara(ProposalNo57);//ָ���������
	var sSQL = mySql57.getString();
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{
		fm.NewBankCode.value=arrInsuredAdd[0][0];
		fm.NewAccName.value=arrInsuredAdd[0][1];
		fm.NewBankAccNo.value=arrInsuredAdd[0][2];
		fm.BankCode.value=arrInsuredAdd[0][3];
		fm.AccName.value=arrInsuredAdd[0][4];
		fm.BankAccNo.value=arrInsuredAdd[0][5];
		//fm.SelPolNo.value=arrInsuredAdd[0][6];
		fm.PrtNo.value=arrInsuredAdd[0][7];
		fm.PolApplyDate.value=arrInsuredAdd[0][8];
		fm.ManageCom.value=arrInsuredAdd[0][9];
		fm.AgentCom.value=arrInsuredAdd[0][10];
		fm.AgentCode.value=arrInsuredAdd[0][11];
		//fm.AgentManageCom.value=arrInsuredAdd[0][12];
		fm.AgentGroup.value=arrInsuredAdd[0][13];
		fm.SaleChnl.value=arrInsuredAdd[0][14];
		fm.Remark.value=arrInsuredAdd[0][15];
		fm.NewPayMode.value=arrInsuredAdd[0][16];
		fm.PayLocation.value=arrInsuredAdd[0][17];
		fm.AutoPayFlag.value=arrInsuredAdd[0][18];
		fm.RnewFlag.value=arrInsuredAdd[0][19];
		fm.GetPolMode.value=arrInsuredAdd[0][20];
		fm.OutPayFlag.value=arrInsuredAdd[0][21];
		fm.PayIntv.value=arrInsuredAdd[0][22];
		fm.AgentName.value=arrInsuredAdd[0][23];	
		//fm.InputAgentComName.value=arrInsuredAdd[0][24];
		fm.SignName.value=arrInsuredAdd[0][25];
		
		fm.FirstTrialDate.value=arrInsuredAdd[0][26];
		fm.XQremindFlag.value=arrInsuredAdd[0][27];
		showOneCodeNameRefresh('XQremindFlag','XQremindFlag','XQremindFlagName');
	}
}

//����ѯͶ������Ϣ��������
function queryAppntInfo()
{
    //alert(fm.ProposalNo.value);
//	var sSQL ="select a.AppntName,a.idtype,a.idno, b.postaladdress,b.zipcode,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.appntno,a.addressno,a.RelationToInsured"
//			//�����޸ĵ����� 
//	        +" ,a.appntsex,a.appntbirthday,a.NativePlace,a.RgtAddress,a.Marriage,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--�����޸ĵ�����
//			+ " from lcappnt a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.appntno and b.addressno=a.addressno";
	var sqlid58 = "WbProposalInputSql58";
	var mySql58 = new SqlClass();
	var ProposalNo58 = fm.ProposalNo.value;
	 mySql58.setResourceName ("app.WbProposalInputSql");
    mySql58.setSqlId(sqlid58);//ָ��ʹ��SQL��id
	mySql58.addSubPara(ProposalNo58);//ָ���������
	var sSQL = mySql58.getString();
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{
		fm.AppntName.value=arrInsuredAdd[0][0];
		fm.AppntIDType.value=arrInsuredAdd[0][1];
		fm.AppntIDNo.value=arrInsuredAdd[0][2];
		fm.AppntPostalAddress.value=arrInsuredAdd[0][3];
		fm.AppntZipCode.value=arrInsuredAdd[0][4];
		fm.AppntHomeAddress.value=arrInsuredAdd[0][5];		
		fm.AppntHomeZipCode.value=arrInsuredAdd[0][6];
		fm.AppntPhone.value=arrInsuredAdd[0][7];
		fm.AppntPhone2.value=arrInsuredAdd[0][8];
		fm.AppntEMail.value=arrInsuredAdd[0][9];
		fm.AppntGrpName.value=arrInsuredAdd[0][10];
		fm.AppntNo.value=arrInsuredAdd[0][11];
		//fm.AppntAddressNo.value=arrInsuredAdd[0][12];
		//alert(fm.AppntAddressNo.value);
		fm.AppntRelationToInsured.value=arrInsuredAdd[0][13];
		fm.AppntSex.value=arrInsuredAdd[0][14];
		fm.AppntBirthday.value=arrInsuredAdd[0][15];
		document.all('AppntAge').value=calAge(fm.AppntBirthday.value);
		fm.AppntNativePlace.value=arrInsuredAdd[0][16];
		fm.AppntRgtAddress.value=arrInsuredAdd[0][17];
		fm.AppntMarriage.value=arrInsuredAdd[0][18];
		fm.AppntPluralityType.value=arrInsuredAdd[0][19];
		fm.AppntOccupationCode.value=arrInsuredAdd[0][20];
		fm.AppntOccupationType.value=arrInsuredAdd[0][21];
		fm.AppntIDEndDate.value=arrInsuredAdd[0][22];
		fm.AppntSocialInsuFlag.value=arrInsuredAdd[0][23];
	}
}

//����ѯ��������Ϣ��������
function queryInsuredInfo()
{
//	var sSQL ="select a.Name,a.idtype,a.idno,b.postaladdress,b.zipcode,b.mobile,b.phone,b.email,b.grpname,a.insuredno,a.addressno,a.RelationToAppnt"
//			//�����޸ĵ����� 
//	        +" ,a.customerseqno,a.RelationToMainInsured,a.RelationToAppnt,a.Sex,a.Birthday,a.NativePlace,a.RgtAddress"
//	        +" ,a.Marriage,a.WorkType,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--�����޸ĵ�����
//			+ " from lcinsured a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.insuredno and sequenceno in ('1','-1') and b.addressno=a.addressno";
//	//prompt('',sSQL);
	var sqlid59 = "WbProposalInputSql59";
	var mySql59 = new SqlClass();
	var ProposalNo59 = fm.ProposalNo.value;
	 mySql59.setResourceName ("app.WbProposalInputSql");
    mySql59.setSqlId(sqlid59);//ָ��ʹ��SQL��id
	mySql59.addSubPara(ProposalNo59);//ָ���������
	var sSQL = mySql59.getString();
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{	    
		fm.Name1.value=arrInsuredAdd[0][0];
		fm.IDType1.value=arrInsuredAdd[0][1];
		fm.IDNo1.value=arrInsuredAdd[0][2];
		fm.HomeAddress1.value=arrInsuredAdd[0][3];		
		fm.HomeZipCode1.value=arrInsuredAdd[0][4];
		fm.Phone1.value=arrInsuredAdd[0][5];
		fm.Phone21.value=arrInsuredAdd[0][6];
		fm.EMail1.value=arrInsuredAdd[0][7];
		fm.GrpName1.value=arrInsuredAdd[0][8];
		fm.CustomerNo1.value=arrInsuredAdd[0][9];
		//fm.AddressNo1.value=arrInsuredAdd[0][10];
		//alert(fm.AddressNo1.value);
		fm.RelationToAppnt1.value=arrInsuredAdd[0][11];
		
		//fm.SequenceNoCode1.value=arrInsuredAdd[0][12];
		fm.RelationToMainInsured1.value=arrInsuredAdd[0][13];
		//fm.RelationToAppnt1.value=arrInsuredAdd[0][14];
		fm.Sex1.value=arrInsuredAdd[0][15];
		fm.Birthday1.value=arrInsuredAdd[0][16];	
		document.all('Age1').value=calAgeNew(document.all("Birthday1").value,document.all("PolApplyDate").value);	
		fm.NativePlace1.value=arrInsuredAdd[0][17];
		fm.RgtAddress1.value=arrInsuredAdd[0][18];
		fm.Marriage1.value=arrInsuredAdd[0][19];
		fm.WorkType1.value=arrInsuredAdd[0][20];
		fm.PluralityType1.value=arrInsuredAdd[0][21];		
		fm.OccupationCode1.value=arrInsuredAdd[0][22];
		fm.OccupationType1.value=arrInsuredAdd[0][23];
		fm.IDEndDate1.value=arrInsuredAdd[0][24];
		fm.SocialInsuFlag1.value=arrInsuredAdd[0][25];
		(DivInsuredAll1).style.display = "";
	}
	
//	sSQL ="select a.Name,a.idtype,a.idno,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.insuredno,a.addressno,a.RelationToAppnt"
//			//�����޸ĵ����� 
//	        +" ,a.customerseqno,a.RelationToMainInsured,a.RelationToAppnt,a.Sex,a.Birthday,a.NativePlace,a.RgtAddress"
//	        +" ,a.Marriage,a.WorkType,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--�����޸ĵ�����
//			+ " from lcinsured a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.insuredno and sequenceno='2' and b.addressno=a.addressno";
//	//prompt('',sSQL);
	var sqlid60 = "WbProposalInputSql60";
	var mySql60 = new SqlClass();
	var ProposalNo60 = fm.ProposalNo.value;
	 mySql60.setResourceName ("app.WbProposalInputSql");
    mySql60.setSqlId(sqlid60);//ָ��ʹ��SQL��id
	mySql60.addSubPara(ProposalNo60);//ָ���������
	sSQL = mySql60.getString();
	arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{	    
		fm.Name2.value=arrInsuredAdd[0][0];
		fm.IDType2.value=arrInsuredAdd[0][1];
		fm.IDNo2.value=arrInsuredAdd[0][2];
		fm.HomeAddress2.value=arrInsuredAdd[0][3];		
		fm.HomeZipCode2.value=arrInsuredAdd[0][4];
		fm.Phone2.value=arrInsuredAdd[0][5];
		fm.Phone22.value=arrInsuredAdd[0][6];
		//fm.EMail2.value=arrInsuredAdd[0][7];
		fm.GrpName2.value=arrInsuredAdd[0][8];
		fm.CustomerNo2.value=arrInsuredAdd[0][9];
		//fm.AddressNo2.value=arrInsuredAdd[0][10];
		//alert(fm.AddressNo2.value);
		fm.RelationToAppnt2.value=arrInsuredAdd[0][11];
		
		//fm.SequenceNoCode2.value=arrInsuredAdd[0][12];
		fm.RelationToMainInsured2.value=arrInsuredAdd[0][13];
		//fm.RelationToAppnt2.value=arrInsuredAdd[0][14];
		fm.Sex2.value=arrInsuredAdd[0][15];
		fm.Birthday2.value=arrInsuredAdd[0][16];	
		document.all('Age2').value=calAgeNew(document.all("Birthday2").value,document.all("PolApplyDate").value);	
		fm.NativePlace2.value=arrInsuredAdd[0][17];
		fm.RgtAddress2.value=arrInsuredAdd[0][18];
		fm.Marriage2.value=arrInsuredAdd[0][19];
		fm.WorkType2.value=arrInsuredAdd[0][20];
		fm.PluralityType2.value=arrInsuredAdd[0][21];		
		fm.OccupationCode2.value=arrInsuredAdd[0][22];
		fm.OccupationType2.value=arrInsuredAdd[0][23];
		fm.IDEndDate2.value=arrInsuredAdd[0][24];
		fm.SocialInsuFlag2.value=arrInsuredAdd[0][25];
		(DivInsuredAll2).style.display = "";
	}
	
//	sSQL ="select a.Name,a.idtype,a.idno,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.insuredno,a.addressno,a.RelationToAppnt"
//			//�����޸ĵ����� 
//	        +" ,a.customerseqno,a.RelationToMainInsured,a.RelationToAppnt,a.Sex,a.Birthday,a.NativePlace,a.RgtAddress"
//	        +" ,a.Marriage,a.WorkType,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--�����޸ĵ�����
//			+ " from lcinsured a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.insuredno and sequenceno='3' and b.addressno=a.addressno";
//	//prompt('',sSQL);
	var sqlid61 = "WbProposalInputSql61";
	var mySql61 = new SqlClass();
	var ProposalNo61 = fm.ProposalNo.value;
	 mySql61.setResourceName ("app.WbProposalInputSql");
    mySql61.setSqlId(sqlid61);//ָ��ʹ��SQL��id
	mySql61.addSubPara(ProposalNo61);//ָ���������
	sSQL = mySql61.getString();
	arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{	    
		fm.Name3.value=arrInsuredAdd[0][0];
		fm.IDType3.value=arrInsuredAdd[0][1];
		fm.IDNo3.value=arrInsuredAdd[0][2];
		fm.HomeAddress3.value=arrInsuredAdd[0][3];		
		fm.HomeZipCode3.value=arrInsuredAdd[0][4];
		fm.Phone3.value=arrInsuredAdd[0][5];
		fm.Phone23.value=arrInsuredAdd[0][6];
		//fm.EMail3.value=arrInsuredAdd[0][7];
		fm.GrpName3.value=arrInsuredAdd[0][8];
		fm.CustomerNo3.value=arrInsuredAdd[0][9];
		//fm.AddressNo3.value=arrInsuredAdd[0][10];
		//alert(fm.AddressNo3.value);
		fm.RelationToAppnt3.value=arrInsuredAdd[0][11];
		
		//fm.SequenceNoCode3.value=arrInsuredAdd[0][12];
		fm.RelationToMainInsured3.value=arrInsuredAdd[0][13];
		//fm.RelationToAppnt3.value=arrInsuredAdd[0][14];
		fm.Sex3.value=arrInsuredAdd[0][15];
		fm.Birthday3.value=arrInsuredAdd[0][16];	
		document.all('Age3').value=calAgeNew(document.all("Birthday3").value,document.all("PolApplyDate").value);	
		fm.NativePlace3.value=arrInsuredAdd[0][17];
		fm.RgtAddress3.value=arrInsuredAdd[0][18];
		fm.Marriage3.value=arrInsuredAdd[0][19];
		fm.WorkType3.value=arrInsuredAdd[0][20];
		fm.PluralityType3.value=arrInsuredAdd[0][21];		
		fm.OccupationCode3.value=arrInsuredAdd[0][22];
		fm.OccupationType3.value=arrInsuredAdd[0][23];
		fm.IDEndDate3.value=arrInsuredAdd[0][24];
		fm.SocialInsuFlag3.value=arrInsuredAdd[0][25];
		(DivInsuredAll3).style.display = "";
	}
}

//���������Ϣ
function queryRiskInfo()
{
	var tContNo=document.all('ProposalNo').value;
	for(var i=1;i<=3;i++)
	{
		var StrSequenceno =  " and sequenceno ='"+i+"' ";
		if(i == 1)
			StrSequenceno =  " and sequenceno in ('1','-1') ";
		for(var j=1;j<=3;j++)
		{
			var StrRiskSequenceno =  " and risksequence ='"+j+"' ";
			if(j == 1)
				StrRiskSequenceno =  " and risksequence in ('1','-1') ";
//			var sSQL="select '',risksequence,riskcode,(select riskname from lmriskapp where riskcode=a.riskcode)"
//				+" ,amnt,mult,insuyear,insuyearflag,payendyear,payendyearflag,standprem"
//				+" ,(select decode(sum(prem),null,'',sum(prem)) from lcprem where polno=a.polno and AddFeeDirect='03' and payplancode like '000000%%')"
//				+ ",inputprem"
//				+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno "+StrSequenceno+") "+StrRiskSequenceno+" order by polno ";
//			//prompt('',sSQL);
			var sqlid62 = "WbProposalInputSql62";
			var mySql62 = new SqlClass();
			 mySql62.setResourceName ("app.WbProposalInputSql");
		    mySql62.setSqlId(sqlid62);//ָ��ʹ��SQL��id
			mySql62.addSubPara(tContNo);//ָ���������
			mySql62.addSubPara(tContNo);//ָ���������
			mySql62.addSubPara(StrSequenceno);//ָ���������
			mySql62.addSubPara(StrRiskSequenceno);//ָ���������
			var sSQL = mySql62.getString();
			var arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	
				document.all("MainRiskNo"+i+j).value = arrInsuredAdd[0][1];			
				//alert("turnPage.queryModal(sSQL,Risk"+i+"1Grid)");
				eval("turnPage.queryModal(sSQL,Risk"+i+j+"Grid)");
				document.getElementById("divRisk"+i+j).style.display = "";
			} 	
		}		
	}
	queryMainRiskInput();
	
}

//�����������Ϣ
function getLCBnfInfo()
{
//	var tContNo=document.all('ProposalNo').value;
//		var sSQL="select '',(select risksequence from lcpol where polno=a.polno),bnftype"
//		   // + " ,(select codename from ldcode where codetype='bnftype' and code=bnftype)"
//		    +" ,name,idtype,idno,relationtoinsured,bnflot,bnfgrade"
//			+" ,PostalAddress,IDExpDate,''"
//			+" from lcbnf a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno in ('1','-1')) order by bnfno ";
////		prompt('',sSQL);
		var sqlid63 = "WbProposalInputSql63";
		var mySql63 = new SqlClass();
		 mySql63.setResourceName ("app.WbProposalInputSql");
	    mySql63.setSqlId(sqlid63);//ָ��ʹ��SQL��id
		mySql63.addSubPara(tContNo);//ָ���������
		mySql63.addSubPara(tContNo);//ָ���������
		var sSQL = mySql63.getString();
		turnPage.queryModal(sSQL,Bnf1Grid);
		
//		sSQL="select '',(select risksequence from lcpol where polno=a.polno),bnftype"
//		   // + " ,(select codename from ldcode where codetype='bnftype' and code=bnftype)"
//		    +" ,name,idtype,idno,relationtoinsured,bnflot,bnfgrade"
//			+" ,PostalAddress,IDExpDare,''"
//			+" from lcbnf a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='2') order by bnfno ";
//		
		var sqlid64 = "WbProposalInputSql64";
		var mySql64 = new SqlClass();
		 mySql64.setResourceName ("app.WbProposalInputSql");
	    mySql64.setSqlId(sqlid64);//ָ��ʹ��SQL��id
		mySql64.addSubPara(tContNo);//ָ���������
		mySql64.addSubPara(tContNo);//ָ���������
		sSQL = mySql64.getString();
		turnPage.queryModal(sSQL,Bnf2Grid);
		
//		sSQL="select '',(select risksequence from lcpol where polno=a.polno),bnftype"
//		   // + " ,(select codename from ldcode where codetype='bnftype' and code=bnftype)"
//		    +" ,name,idtype,idno,relationtoinsured,bnflot,bnfgrade"
//			+" ,PostalAddress,IDExpDare,''"
//			+" from lcbnf a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='3') order by bnfno ";
//		
		var sqlid65 = "WbProposalInputSql65";
		var mySql65 = new SqlClass();
		 mySql65.setResourceName ("app.WbProposalInputSql");
	    mySql65.setSqlId(sqlid65);//ָ��ʹ��SQL��id
		mySql65.addSubPara(tContNo);//ָ���������
		mySql65.addSubPara(tContNo);//ָ���������
		sSQL = mySql65.getString();
		turnPage.queryModal(sSQL,Bnf3Grid);
}

//��ô���ʽ��Ϣ
function getDealTypeInfo()
{
	var tContNo=document.all('ProposalNo').value;
//		var sSQL="select '',risksequence,getyear,getyearflag,'' "
//			+" ,(select max(getdutykind) from lcget where polno=a.polno and livegettype='0' and (getdutykind is not null or getdutykind<>'')),LiveGetMode,BonusGetMode "
//			+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno in ('1','-1')) and polno=mainpolno order by risksequence";
//		//prompt('',sSQL);
		var sqlid66 = "WbProposalInputSql66";
		var mySql66 = new SqlClass();
		 mySql66.setResourceName ("app.WbProposalInputSql");
	    mySql66.setSqlId(sqlid66);//ָ��ʹ��SQL��id
		mySql66.addSubPara(tContNo);//ָ���������
		mySql66.addSubPara(tContNo);//ָ���������
		var sSQL = mySql66.getString();
		turnPage.queryModal(sSQL,DealType1Grid);
		
//		sSQL="select '',risksequence,getyear,getyearflag,'' "
//			+" ,'',LiveGetMode,BonusGetMode "
//			+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='2') and polno=mainpolno order by risksequence ";
		
		var sqlid67 = "WbProposalInputSql67";
		var mySql67 = new SqlClass();
		 mySql67.setResourceName ("app.WbProposalInputSql");
	    mySql67.setSqlId(sqlid67);//ָ��ʹ��SQL��id
		mySql67.addSubPara(tContNo);//ָ���������
		mySql67.addSubPara(tContNo);//ָ���������
		sSQL = mySql67.getString();
		turnPage.queryModal(sSQL,DealType2Grid);
//		sSQL="select '',risksequence,getyear,getyearflag,'' "
//			+" ,'',LiveGetMode,BonusGetMode "
//			+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='3') and polno=mainpolno order by risksequence ";
		
		var sqlid68 = "WbProposalInputSql68";
		var mySql68 = new SqlClass();
		 mySql68.setResourceName ("app.WbProposalInputSql");
	    mySql68.setSqlId(sqlid68);//ָ��ʹ��SQL��id
		mySql68.addSubPara(tContNo);//ָ���������
		mySql68.addSubPara(tContNo);//ָ���������
		sSQL = mySql68.getString();
		turnPage.queryModal(sSQL,DealType3Grid);

}

//��ø�֪��Ϣ
function getImpartInfo()
{
	var tContNo=document.all('ProposalNo').value;
//	var sSQL="select impartver,impartcode,impartcontent,impartparammodle,customernotype,decode(customernotype,'1',(select decode(sequenceno,'-1','',sequenceno) from lcinsured where contno=a.contno and insuredno=a.customerno),'')"	   
//		+" from lccustomerimpart a where contno='"+tContNo+"' order by customernotype ";
//	//prompt('',sSQL);
	
	var sqlid69 = "WbProposalInputSql69";
	var mySql69 = new SqlClass();
	 mySql69.setResourceName ("app.WbProposalInputSql");
    mySql69.setSqlId(sqlid69);//ָ��ʹ��SQL��id
	mySql69.addSubPara(tContNo);//ָ���������
	var sSQL = mySql69.getString();
	turnPage.queryModal(sSQL,ImpartGrid,null,null,100);
	
}




