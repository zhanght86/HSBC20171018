//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = "";
var mAction = "";
//window.onfocus=focuswrap;
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
 var turnPage = new turnPageClass();   
 var turnPage1 = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
 turnPage1.pageLineNum = 5;
 var cflag = "5";
 var mWFlag = 0 ;
 var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
/*********************************************************************
 *  ���漯��Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	if(document.all('AgentType').value=="01"){
		
		var sqlid91="ContPolinputSql91";
	    var mySql91=new SqlClass();
	    mySql91.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql91.setSqlId(sqlid91);//ָ��ʹ�õ�Sql��id
	    mySql91.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		var strSQL=mySql91.getString();
		
		//var strSQL ="select branchtype from LATree where AgentCode='"+fm.AgentCode.value+"'";
		var arrResult = easyExecSql(strSQL);
		if(arrResult==null || arrResult[0][0]!="2")
		{
			alert("��ѡ������ҵ��Ա");
			return;
		}
		
	  var AgentCount = MultiAgentGrid.mulLineCount;

    for(i=1;i<=AgentCount;i++)
    {
	    var tempagentcode=MultiAgentGrid.getRowColData(i-1,1);
		
		var sqlid92="ContPolinputSql92";
	    var mySql92=new SqlClass();
	    mySql92.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql92.setSqlId(sqlid92);//ָ��ʹ�õ�Sql��id
	    mySql92.addSubPara(tempagentcode);//ָ������Ĳ���
		var strsql=mySql92.getString();
		
	   // var strsql ="select branchtype from LATree where AgentCode='"+tempagentcode+"'";
	  	var arrResult = easyExecSql(strsql);
	  	if(arrResult==null || arrResult[0][0]!="2")
	  	{
				alert("����ҵ��Ա��Ϣ�е� "+ i +" ��ҵ��Ա��������ҵ��Ա");
				return;
			}
   	}

	 }	
	 if(document.all('AgentType').value=="05")
	 {
	 	
				var sqlid93="ContPolinputSql93";
	    var mySql93=new SqlClass();
	    mySql93.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql93.setSqlId(sqlid93);//ָ��ʹ�õ�Sql��id
	    mySql93.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		var strSQL=mySql93.getString();
		
	 	//var strSQL ="select branchtype from LATree where AgentCode='"+fm.AgentCode.value+"'";
		var arrResult = easyExecSql(strSQL);
		if(arrResult==null || arrResult[0][0]!="1")
		{
			alert("��ѡ�����ҵ��Ա");
			return;
		}
		var AgentCount = 0;
		
		AgentCount=MultiAgentGrid.mulLineCount;
		//alert(AgentCount);
	  if(AgentCount!="0"){
	  alert("ϵͳ�Զ�ƥ��ָ�ɵ�����ҵ��Ա������¼��");
			return;
	  }
	 }
	//alert(1);
	if(document.all('GetFlag').value=="4" && document.all('BankCode').value==""){
		alert("����������ת�ʵ����к͸����е��ʺ�");
		document.all('BankCode').focus();
		return false;
		}		
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //if(document.all('PayDate').value!=null||document.all('PayDate').value!="")
  //if(document.all('CValiDate').value<document.all('PayDate').value) 
    if( verifyInput2() == false ) return false;
  
    //�����н����  
    if (fm.AgentType.value == "02" || fm.AgentType.value == "03" || fm.AgentType.value == "04")
    {
        if (fm.MediAgentCom.value == "null" || fm.MediAgentCom.value == null
            || fm.MediAgentCom.value == "")
        {
            alert("��¼���н������");
            document.all('MediAgentCom').focus();
            return false;
        }
        if (fm.MediAgentCode.value == "null" || fm.MediAgentCode.value == null
            || fm.MediAgentCode.value == "")
        {
            alert("��¼���н����ҵ��Ա��");
            document.all('MediAgentCode').focus();
            return false;
        }
    }
    
//		if(dateDiff(document.all('PolApplyDate').value, document.all('CValiDate').value, "D")<0){
//		alert("Ͷ����������Ӧ�ڱ�����Ч����֮ǰ");
//		document.all('PolApplyDate').focus();	
//		return false;
//		}
//		if(dateDiff(document.all('CValiDate').value,document.all('PayDate').value,"D")>0){
//			  alert("������Ч����Ӧ�ڱ�����������֮��");
//			  document.all('CValiDate').focus();
//			  return false;
//		}
	//ImpartGrid.delBlankLine();  
	if( mAction == "" )
	{
		//showSubmitFrame(mDebug);
		mAction = "INSERT";
		document.all( 'fmAction' ).value = mAction;
		document.all( 'LoadFlag' ).value = LoadFlag;
		//alert("grpContNo=="+document.all('GrpContNo').value);
		
		//if (document.all('ProposalGrpContNo').value != "") {
		//  alert("��ѯ���ֻ�ܽ����޸Ĳ�����");
		//  mAction = "";
		//} else {
			
		  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		  var iWidth=550;      //�������ڵĿ��; 
		  var iHeight=250;     //�������ڵĸ߶�; 
		  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		  showInfo.focus();

		  
		  tAction = fm.action;		  
		  fm.action="../app/ContPolSave.jsp"
		  document.getElementById("fm").submit(); //�ύ
	//	}
	}
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();  
	if( FlagStr == "Fail" )
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
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		showDiv(operateButton, "true"); 
		showDiv(inputButton, "false");
	}
	
	mAction = ""; 
  if(this.ScanFlag == "1"){
    fm.PrtNo.value=prtNo;
    initPlanGrid();
    initPlanRiskGrid();   
    fillPlanGrid();               
  } 
  if(mWFlag == 1 && FlagStr != "Fail")
  {
    window.location.href("./ContPolInput.jsp");
  }
  //location.reload();      
}

function afterSubmit1( FlagStr, content )
{
	showInfo.close();  
	if( FlagStr == "Fail" )
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
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		showDiv(operateButton, "true"); 
		showDiv(inputButton, "false");
		top.close();
	}
	
	mAction = ""; 
  if(this.ScanFlag == "1"){
    fm.PrtNo.value=prtNo;
    initPlanGrid();   
    fillPlanGrid();               
  } 
  if(mWFlag == 1 && FlagStr != "Fail")
  {
    window.location.href("./ContPolInput.jsp");
  }
  //location.reload();      
}

/*********************************************************************
 *  "����"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetForm()
{
	try
	{
		initForm();
		document.all('PrtNo').value = prtNo;
	}
	catch( re )
	{
		alert("��GroupPolInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
	showDiv(operateButton,"true"); 
	showDiv(inputButton,"false"); 
}
 
/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  Click�¼������������ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addClick()
{
		if(document.all('GetFlag').value=="4" && document.all('BankCode').value==""){
		alert("����������ת�ʵ����к͸����е��ʺ�");
		document.all('BankCode').focus();
		return false;
		}	
//	if(dateDiff(document.all('PolApplyDate').value, document.all('CValiDate').value, "D")<0){
//		alert("Ͷ����������Ӧ�ڱ�����Ч����֮ǰ");
//		document.all('PolApplyDate').focus();
//		return false;
//		}
		
//			if(dateDiff(document.all('CValiDate').value,document.all('PayDate').value,"D")>0){
//			  alert("������Ч����Ӧ�ڱ�����������֮��");
//			  document.all('CValiDate').focus();
//			  return false;
//		}
		
//Ա��������=��ְ����+��������+������Ա����
		var intPeoples=parseInt(document.all('Peoples').value);
		var intAppntOnWorkPeoples=parseInt(document.all('AppntOnWorkPeoples').value);
		var intAppntOffWorkPeoples=parseInt(document.all('AppntOffWorkPeoples').value);
		var intAppntOtherPeoples=parseInt(document.all('AppntOtherPeoples').value );
		var intPeoples3=parseInt(document.all('Peoples3').value );
   
  if (intPeoples!=intAppntOnWorkPeoples+intAppntOffWorkPeoples+intAppntOtherPeoples){
  	alert("Ա��������Ӧ��Ϊ"+(intAppntOnWorkPeoples+intAppntOffWorkPeoples+intAppntOtherPeoples));
  	document.all('Peoples').focus();
  	return false;
  	}

//Ա��������Ӧ���ڵ��ڱ�������(��Ա)
  if (intPeoples<intPeoples3){
  	alert("Ա��������Ӧ���ڵ��ڱ�������");
  	document.all('Peoples3').focus();
  	return false;
  	}		
	
	//����������Ӧ�Ĵ���
	showDiv( operateButton, "false" ); 
	showDiv( inputButton, "true" ); 
	
	document.all('RiskCode').value = "";
	
	//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
  if (BQFlag=="2") {
  	
					var sqlid94="ContPolinputSql94";
	    var mySql94=new SqlClass();
	    mySql94.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql94.setSqlId(sqlid94);//ָ��ʹ�õ�Sql��id
	    mySql94.addSubPara(prtNo);//ָ������Ĳ���
		var strSql=mySql94.getString();
	
    //var strSql = "select grppolno, grpno from lcgrppol where prtno='" + prtNo + "' and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
    var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    
    mOperate = 1;
    afterQuery(arrResult);
    
    //strSql = "select GrpNo,GrpName,GrpAddress,Satrap from LDGrp where GrpNo='" + arrResult[0][1] + "'";
    //arrResult = easyExecSql(strSql);
    //mOperate = 2;
    //afterQuery(arrResult);
    
    document.all('RiskCode').value = BQRiskCode;
    document.all('RiskCode').className = "readonly";
    document.all('RiskCode').readOnly = true;
    document.all('RiskCode').ondblclick = "";
  }
	
	document.all('ContNo').value = "";
	document.all('ProposalGrpContNo').value = "";
}           

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick()
{ 
       if(this.ScanFlag == "1"){
	  alert( "��ɨ���¼�벻�����ѯ!" );
          return false;	  
        }
	if( mOperate == 0 )
	{		
		mOperate = 1;
		//cContNo = document.all( 'ContNo' ).value;
		showInfo = window.open("./GroupPolQueryMain.jsp","",sFeatures);
	}
} 


/*********************************************************************
 *  Click�¼�����������޸ġ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function updateClick()
{   
	
	//���ѡ�� " 01-ֱ���Ŷ� "ʱ,����ҵ��Ա����Ϊ ����ҵ��Ա
	if(document.all('AgentType').value=="01")
	{
		var sqlid1="ContPolinputSql1";
	    var mySql1=new SqlClass();
	    mySql1.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	    mySql1.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		var strSQL=mySql1.getString();
		
		//var strSQL ="select branchtype from LATree where AgentCode='"+fm.AgentCode.value+"'";
		var arrResult = easyExecSql(strSQL);
		if(arrResult==null || arrResult[0][0]!="2")
		{
			alert("��ѡ������ҵ��Ա");
			return;
	  }

	  var AgentCount = MultiAgentGrid.mulLineCount;

		for(i=1;i<=AgentCount;i++)
		{
			var tempagentcode=MultiAgentGrid.getRowColData(i-1,1);
			
				var sqlid2="ContPolinputSql2";
			    var mySql2=new SqlClass();
			    mySql2.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
			    mySql2.addSubPara(tempagentcode);//ָ������Ĳ���
				var strsql=mySql2.getString();
			
			//var strsql ="select branchtype from LATree where AgentCode='"+tempagentcode+"'";
			var arrResult = easyExecSql(strsql);
			if(arrResult==null || arrResult[0][0]!="2")
			{
				alert("����ҵ��Ա��Ϣ�е� "+ i +" ��ҵ��Ա��������ҵ��Ա");
				return;
			}
		}

	  
	 }	
	 
	 //���ѡ�� " 05-�������� "ʱ,����ҵ��Ա����Ϊ ����ҵ��Ա
	 if(document.all('AgentType').value=="05")
	 {
	 			var sqlid3="ContPolinputSql3";
			    var mySql3=new SqlClass();
			    mySql3.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
			    mySql3.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
				var strSQL=mySql3.getString();
		
	 	//var strSQL ="select branchtype from LATree where AgentCode='"+fm.AgentCode.value+"'";
		var arrResult = easyExecSql(strSQL);
		if(arrResult==null || arrResult[0][0]!="1")
		{
			alert("��ѡ�����ҵ��Ա");
			return;
		}

	  if(document.all("multiagentflag").checked ==true){
	  alert("ϵͳ�Զ�ƥ��ָ�ɵ�����ҵ��Ա������¼������ҵ��Ա��Ϣ");
			return;
	  }
	 } 
	if(document.all('GetFlag').value=="4" && document.all('BankCode').value==""){
		alert("����������ת�ʵ����к͸����е��ʺ�");
		document.all('BankCode').focus();
		return false;
		}		
		
//	if(dateDiff(document.all('PolApplyDate').value, document.all('CValiDate').value, "D")<0){
//		alert("Ͷ����������Ӧ�ڱ�����Ч����֮ǰ");
//		document.all('PolApplyDate').focus();
//		return false;
//		}
//	 if(dateDiff(document.all('CValiDate').value,document.all('PayDate').value, "D")>0){
//			  alert("������Ч����Ӧ�ڱ�����������֮��");
//			  document.all('CValiDate').focus();
//			  return false;
//		}
	if (fm.AgentType.value == "02" || fm.AgentType.value == "03" || fm.AgentType.value == "04")
    {
        if (fm.MediAgentCom.value == "null" || fm.MediAgentCom.value == null
            || fm.MediAgentCom.value == "")
        {
            alert("��¼���н������");
            document.all('MediAgentCom').focus();
            return false;
        }
        if (fm.MediAgentCode.value == "null" || fm.MediAgentCode.value == null
            || fm.MediAgentCode.value == "")
        {
            alert("��¼���н����ҵ��Ա��");
            document.all('MediAgentCode').focus();
            return false;
        }
    }


//Ա��������=��ְ����+��������+������Ա����
/**********************************
		var intPeoples=parseInt(document.all('Peoples').value);
		var intAppntOnWorkPeoples=parseInt(document.all('AppntOnWorkPeoples').value);
		var intAppntOffWorkPeoples=parseInt(document.all('AppntOffWorkPeoples').value);
		var intAppntOtherPeoples=parseInt(document.all('AppntOtherPeoples').value );
		var intPeoples3=parseInt(document.all('Peoples3').value );
  //alert("intPeoples=="+intPeoples); 
  //alert("intAppntOnWorkPeoples+intAppntOffWorkPeoples+intAppntOtherPeoples==");
  if (intPeoples!=intAppntOnWorkPeoples+intAppntOffWorkPeoples+intAppntOtherPeoples){
  	alert("Ա��������Ӧ��Ϊ"+(intAppntOnWorkPeoples+intAppntOffWorkPeoples+intAppntOtherPeoples));
  	document.all('Peoples').focus();
  	return false;
  	}
*********************************/
//Ա��������Ӧ���ڵ��ڱ�������(��Ա)
 // if (intPeoples<intPeoples3){
 // 	alert("Ա��������Ӧ���ڵ��ڱ�������");
 // 	document.all('Peoples3').focus();
 // 	return false;
 // 	}	
	
	
	var tProposalGrpContNo = "";
	tProposalGrpContNo = document.all( 'ProposalGrpContNo' ).value;
	  if( verifyInput2() == false ) return false;
	//  ImpartGrid.delBlankLine(); 
	if( tProposalGrpContNo == null || tProposalGrpContNo == "" )
	      if(this.ScanFlag == "1"){
	      	alert( "��δ¼������,�������Ӻ�ͬ��Ϣ,�ٽ����޸�!" );
	       }
	       else 
	       {
		alert( "������Ͷ������ѯ�������ٽ����޸�!" );
	       }
	else
	{
		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "UPDATE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="../app/ContPolSave.jsp"
			document.getElementById("fm").submit(); //�ύ
		}
	}
}           

/*********************************************************************
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick()
{
	var tProposalGrpContNo = "";
	tProposalGrpContNo = document.all( 'ProposalGrpContNo' ).value;
	if( tProposalGrpContNo == null || tProposalGrpContNo == "" )
		alert( "������Ͷ������ѯ�������ٽ���ɾ��!" );
	else
	{
	  if (confirm("��ȷ��Ҫɾ�����ŵ���"))
	  {
		var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="../app/ContPolSave.jsp"
			document.getElementById("fm").submit(); //�ύ
		}
	 }
     }
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

/*********************************************************************
 *  ����������������Ϣ����ťʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function intoPol()
{
	//����������Ӧ�Ĵ���
	tProposalGrpContNo = fm.ProposalGrpContNo.value;
	if( tProposalGrpContNo == "" )
	{
		alert("��������¼�뼯����Ϣ���ܽ��������Ϣ���֡�");
		return false
	}

	//�Ѽ�����Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch;  //���ݴ�
	putGrpPol();
	
	try { goToPic(2) } catch(e) {}
	
	try {
	  parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type;
  }
  catch (e) {
    parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=2&type=" + type;
  }
}           

/*********************************************************************
 *  �Ѽ�����Ϣ�����ڴ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function putGrpPol()
{
	delGrpPolVar();
	addIntoGrpPol();
}

/*********************************************************************
 *  �Ѽ�����Ϣ����ӵ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addIntoGrpPol()
{
	try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
	// body��Ϣ
	try { mSwitch.addVar( "BODY", "", window.document.body.innerHTML ); } catch(ex) { };
	// ������Ϣ
	//��"./AutoCreatLDGrpInit.jsp"�Զ�����
  try { mSwitch.addVar('GrpNo', '', document.all('GrpNo').value); } catch(ex) { };
  try { mSwitch.addVar('PrtNo', '', document.all('PrtNo').value); } catch(ex) { };
  try { mSwitch.addVar('Password', '', document.all('Password').value); } catch(ex) { };
  try { mSwitch.addVar('GrpName', '', document.all('GrpName').value); } catch(ex) { };
  try { mSwitch.addVar('GrpAddressCode', '', document.all('GrpAddressCode').value); } catch(ex) { };
  try { mSwitch.addVar('GrpAddress', '', document.all('GrpAddress').value); } catch(ex) { };
  try { mSwitch.addVar('GrpZipCode', '', document.all('GrpZipCode').value); } catch(ex) { };
  try { mSwitch.addVar('BusinessType', '', document.all('BusinessType').value); } catch(ex) { };
  try { mSwitch.addVar('GrpNature', '', document.all('GrpNature').value); } catch(ex) { };
  try { mSwitch.addVar('Peoples', '', document.all('Peoples').value); } catch(ex) { };
  try { mSwitch.addVar('RgtMoney', '', document.all('RgtMoney').value); } catch(ex) { };
  try { mSwitch.addVar('Asset', '', document.all('Asset').value); } catch(ex) { };
  try { mSwitch.addVar('NetProfitRate', '', document.all('NetProfitRate').value); } catch(ex) { };
  try { mSwitch.addVar('MainBussiness', '', document.all('MainBussiness').value); } catch(ex) { };
  try { mSwitch.addVar('Corporation', '', document.all('Corporation').value); } catch(ex) { };
  try { mSwitch.addVar('ComAera', '', document.all('ComAera').value); } catch(ex) { };
  try { mSwitch.addVar('LinkMan1', '', document.all('LinkMan1').value); } catch(ex) { };
  try { mSwitch.addVar('Department1', '', document.all('Department1').value); } catch(ex) { };
  try { mSwitch.addVar('HeadShip1', '', document.all('HeadShip1').value); } catch(ex) { };
  try { mSwitch.addVar('Phone1', '', document.all('Phone1').value); } catch(ex) { };
  try { mSwitch.addVar('E_Mail1', '', document.all('E_Mail1').value); } catch(ex) { };
  try { mSwitch.addVar('Fax1', '', document.all('Fax1').value); } catch(ex) { };
  try { mSwitch.addVar('LinkMan2', '', document.all('LinkMan2').value); } catch(ex) { };
  try { mSwitch.addVar('Department2', '', document.all('Department2').value); } catch(ex) { };
  try { mSwitch.addVar('HeadShip2', '', document.all('HeadShip2').value); } catch(ex) { };
  try { mSwitch.addVar('Phone2', '', document.all('Phone2').value); } catch(ex) { };
  try { mSwitch.addVar('E_Mail2', '', document.all('E_Mail2').value); } catch(ex) { };
  try { mSwitch.addVar('Fax2', '', document.all('Fax2').value); } catch(ex) { };
  try { mSwitch.addVar('Fax', '', document.all('Fax').value); } catch(ex) { };
  try { mSwitch.addVar('Phone', '', document.all('Phone').value); } catch(ex) { };
  try { mSwitch.addVar('GetFlag', '', document.all('GetFlag').value); } catch(ex) { };
  try { mSwitch.addVar('Satrap', '', document.all('Satrap').value); } catch(ex) { };
  try { mSwitch.addVar('EMail', '', document.all('EMail').value); } catch(ex) { };
  try { mSwitch.addVar('FoundDate', '', document.all('FoundDate').value); } catch(ex) { };
  try { mSwitch.addVar('AppntOnWorkPeoples', '', document.all('AppntOnWorkPeoples').value); } catch(ex) { };
  try { mSwitch.addVar('AppntOffWorkPeoples', '', document.all('AppntOffWorkPeoples').value); } catch(ex) { };
  try { mSwitch.addVar('AppntOtherPeoples', '', document.all('AppntOtherPeoples').value); } catch(ex) { };      
  try { mSwitch.addVar('BankAccNo', '', document.all('BankAccNo').value); } catch(ex) { };
  try { mSwitch.addVar('BankCode', '', document.all('BankCode').value); } catch(ex) { };
  try { mSwitch.addVar('GrpGroupNo', '', document.all('GrpGroupNo').value); } catch(ex) { };
  try { mSwitch.addVar('State', '', document.all('State').value); } catch(ex) { };
  try { mSwitch.addVar('BlacklistFlag', '', document.all('BlacklistFlag').value); } catch(ex) { };
  try { mSwitch.addVar('Currency', '', document.all('Currency').value); } catch(ex) { };

  try { mSwitch.addVar( "ContNo", "", document.all( 'ContNo' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "ProposalGrpContNo", "", document.all( 'ProposalGrpContNo' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "ManageCom", "", document.all( 'ManageCom' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "SaleChnl", "", document.all( 'SaleChnl' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentCom", "", document.all( 'AgentCom' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentCode", "", document.all( 'AgentCode' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentGroup", "", document.all( 'AgentGroup' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentCode1", "", document.all( 'AgentCode1' ).value ); } catch(ex) { };

  try { mSwitch.addVar( "RiskCode", "", document.all( 'RiskCode' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "RiskVersion", "", document.all( 'RiskVersion' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "CValiDate", "", document.all( 'CValiDate' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "PolApplyDate", "", document.all( 'PolApplyDate' ).value ); } catch(ex) { };
  try { mSwitch.addVar('StandbyFlag1', '', document.all('StandbyFlag1').value); } catch(ex) { };
  try { mSwitch.addVar('StandbyFlag2', '', document.all('StandbyFlag2').value); } catch(ex) { };
  try { mSwitch.addVar('StandbyFlag3', '', document.all('StandbyFlag3').value); } catch(ex) { };

}

/*********************************************************************
 *  �Ѽ�����Ϣ�ӱ�����ɾ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delGrpPolVar()
{
	try { mSwitch.deleteVar( "intoPolFlag" ); } catch(ex) { };
	// body��Ϣ
	try { mSwitch.deleteVar( "BODY" ); } catch(ex) { };
	// ������Ϣ
	//��"./AutoCreatLDGrpInit.jsp"�Զ�����
  try { mSwitch.deleteVar('GrpNo'); } catch(ex) { };
  try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
  try { mSwitch.deleteVar('Password'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpAddressCode'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpAddress'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpZipCode'); } catch(ex) { };
  try { mSwitch.deleteVar('BusinessType'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpNature'); } catch(ex) { };
  try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
  try { mSwitch.deleteVar('RgtMoney'); } catch(ex) { };
  try { mSwitch.deleteVar('Asset'); } catch(ex) { };
  try { mSwitch.deleteVar('NetProfitRate'); } catch(ex) { };
  try { mSwitch.deleteVar('MainBussiness'); } catch(ex) { };
  try { mSwitch.deleteVar('Corporation'); } catch(ex) { };
  try { mSwitch.deleteVar('ComAera'); } catch(ex) { };
  try { mSwitch.deleteVar('LinkMan1'); } catch(ex) { };
  try { mSwitch.deleteVar('Department1'); } catch(ex) { };
  try { mSwitch.deleteVar('HeadShip1'); } catch(ex) { };
  try { mSwitch.deleteVar('Phone1'); } catch(ex) { };
  try { mSwitch.deleteVar('E_Mail1'); } catch(ex) { };
  try { mSwitch.deleteVar('Fax1'); } catch(ex) { };
  try { mSwitch.deleteVar('LinkMan2'); } catch(ex) { };
  try { mSwitch.deleteVar('Department2'); } catch(ex) { };
  try { mSwitch.deleteVar('HeadShip2'); } catch(ex) { };
  try { mSwitch.deleteVar('Phone2'); } catch(ex) { };
  try { mSwitch.deleteVar('E_Mail2'); } catch(ex) { };
  try { mSwitch.deleteVar('Fax2'); } catch(ex) { };
  try { mSwitch.deleteVar('Fax'); } catch(ex) { };
  try { mSwitch.deleteVar('Phone'); } catch(ex) { };
  try { mSwitch.deleteVar('GetFlag'); } catch(ex) { };
  try { mSwitch.deleteVar('Satrap'); } catch(ex) { };
  try { mSwitch.deleteVar('EMail'); } catch(ex) { };
  try { mSwitch.deleteVar('FoundDate'); } catch(ex) { };
  try { mSwitch.deleteVar('AppntOnWorkPeoples'); } catch(ex) { };
  try { mSwitch.deleteVar('AppntOffWorkPeoples'); } catch(ex) { };
  try { mSwitch.deleteVar('AppntOtherPeoples'); } catch(ex) { };      
  try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
  try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpGroupNo'); } catch(ex) { };
  try { mSwitch.deleteVar('State'); } catch(ex) { };
  try { mSwitch.deleteVar('BlacklistFlag'); } catch(ex) { };
  try { mSwitch.deleteVar('Currency'); } catch(ex) { };

	mSwitch.deleteVar( "ContNo" );
	mSwitch.deleteVar( "ProposalGrpContNo" );
	mSwitch.deleteVar( "ManageCom" );
	mSwitch.deleteVar( "SaleChnl" );
	mSwitch.deleteVar( "AgentCom" );
	mSwitch.deleteVar( "AgentCode" );
	mSwitch.deleteVar( "AgentCode1" );
	
	mSwitch.deleteVar( "RiskCode" );
	mSwitch.deleteVar( "RiskVersion" );
	mSwitch.deleteVar( "CValiDate" );

}

/*********************************************************************
 *  Click�¼�����˫����Ͷ����λ�ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/GroupMain.html" ,"",sFeatures);
	}
}           
function showAppnt()
{
 if (document.all("GrpNo").value == "" ) { 
 	showAppnt1();
 }
 else
 {
 		 		var sqlid4="ContPolinputSql4";
			    var mySql4=new SqlClass();
			    mySql4.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
			    mySql4.addSubPara(document.all("GrpNo").value );//ָ������Ĳ���
				var strSQL=mySql4.getString();
	arrResult = easyExecSql(strSQL, 1, 0);
 	//arrResult = easyExecSql("select b.CustomerNo,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples,b.vipvalue,b.SocialInsuNo from LDGrp b where  b.CustomerNo='" + document.all("GrpNo").value + "'", 1, 0);			
	 if (arrResult == null) {
            alert("δ�鵽Ͷ����λ��Ϣ");
	  } 
	  else {
	    displayAddress(arrResult[0]);
	  }
 }
 showCodeName();
}

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
  //alert("here:" + arrQueryResult + "\n" + mOperate);
 
	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( mOperate == 1 )	{		// ��ѯ����Ͷ����
			 // alert("arrQueryResult[0][0]"+arrQueryResult[0][0]);
			document.all( 'GrpContNo' ).value = arrQueryResult[0][0];	
			
			 	var sqlid5="ContPolinputSql5";
			    var mySql5=new SqlClass();
			    mySql5.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
			    mySql5.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
				var strSQL5=mySql5.getString();
				
			arrResult = easyExecSql(strSQL5, 1, 0);		
			//arrResult = easyExecSql("select * from LCGrpCont where GrpContNo = '" + arrQueryResult[0][0] + "'", 1, 0);                        
			if (arrResult == null) {
			  //alert("δ�鵽�ŵ���Ϣ");
			} else {
			   displayLCGrpCont(arrResult[0]);
			   var tgrpcontno=arrResult[0][0];
			   fillPlanGrid();
//  alert("3");

			 	var sqlid6="ContPolinputSql6";
			    var mySql6=new SqlClass();
			    mySql6.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
			    mySql6.addSubPara(tgrpcontno);//ָ������Ĳ���
			     mySql6.addSubPara(arrResult[0][0]);//ָ������Ĳ���
				var strSQL6=mySql6.getString();

               arrResult = easyExecSql(strSQL6, 1, 0);
			   //arrResult = easyExecSql("select a.* from LCGrpAddress a where a.AddressNo=(select AddressNo from LCGrpAppnt  where GrpContNo = '" + tgrpcontno + "') and a.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + arrResult[0][0] + "')", 1, 0);
			   if (arrResult == null) {  
			       alert("δ�鵽Ͷ����λ��ַ��Ϣ");
			   
			   }else {
			       displayAddress1(arrResult[0]);
//  alert("3");
			   }
			   
			   	var sqlid7="ContPolinputSql7";
			    var mySql7=new SqlClass();
			    mySql7.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
			    mySql7.addSubPara(tgrpcontno);//ָ������Ĳ���
			     mySql7.addSubPara(arrResult[0][0]);//ָ������Ĳ���
				var strSQL7=mySql7.getString();
			   
			    arrResult = easyExecSql(strSQL7,1,0);
			  // arrResult = easyExecSql("select b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples,b.VIPValue,b.socialinsuno from LDGrp b where b.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + tgrpcontno + "')",1,0);
			    if (arrResult == null) {  
			       alert("δ�鵽Ͷ����λ��Ϣ");
			   
			   }else {
                  //var a=0;
                  //for(a=0;a<=12;a++)
                   //alert(arrResult[0][a]);
			       displayAddress2(arrResult[0]);
//  alert("4");
			   }
			   
			   	var sqlid8="ContPolinputSql8";
			    var mySql8=new SqlClass();
			    mySql8.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
			    mySql8.addSubPara(tgrpcontno);//ָ������Ĳ���
				var strSQL8=mySql8.getString();
				
				arrResult = easyExecSql(strSQL8,1,0);
			   //arrResult = easyExecSql("select c.Name,c.PostalAddress,c.ZipCode,c.Phone from  LCGrpAppnt c where c.GrpContNo = '" + tgrpcontno + "'",1,0);
			    if (arrResult != null) { 
			       displayAddress3(arrResult[0]);
//  alert("5");
			   }			   			   
//////////////////////////////////////////////////////////////

         //��ѯ���ж�ҵ��Ա��Ϣ
		 
		 		var sqlid9="ContPolinputSql9";
			    var mySql9=new SqlClass();
			    mySql9.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
			    mySql9.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
				var muliAgentSQL=mySql9.getString();
		 
//         var muliAgentSQL="select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "+ 
//                          "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d "+ 
//                          "where 1=1 "+
//                          "and a.GrpContNo='" + arrQueryResult[0][0] + "' " +  
//                          "and b.agentgroup=c.agentgroup " +
//                          "and c.agentcode!=trim(a.agentcode) " +
//                          "and d.agentcode=c.agentcode " +
//                          "and d.agentcode!=trim(a.agentcode) " +
//                          "and c.grpcontno=a.GrpContNo " 
                          ;
         //alert("--muliAgentSQL="+muliAgentSQL);
         turnPage.strQueryResult = easyQueryVer3(muliAgentSQL, 1, 0, 1);
        // alert("--turnPage.strQueryResult=="+turnPage.strQueryResult);
         //�ж��Ƿ��ѯ�ɹ�,�ɹ�����ʾ��֪��Ϣ
         if (turnPage.strQueryResult) {
           document.all("multiagentflag").checked = true;
  	       DivMultiAgent.style.display="";	
           //��ѯ�ɹ������ַ��������ض�ά����
           turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
           //���ó�ʼ������MULTILINE����
           turnPage.pageDisplayGrid = MultiAgentGrid;    
           //����SQL���
           turnPage.strQuerySql = muliAgentSQL; 
           //���ò�ѯ��ʼλ��
           turnPage.pageIndex = 0;  
           //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
           arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
           //����MULTILINE������ʾ��ѯ���
           displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
           //��ʾ��ҵ��Ա
           displayMultiAgent();
         }
         else{
           //��ʼ����ҵ��Ա�б�
           //alert("saafas");
           initMultiAgentGrid();
         }
         //alert("here!arrQueryResult[0][0]=="+arrQueryResult[0][0]);
         //��ѯ��ҵ��Ա��Ϣ
		 
		 		 var sqlid10="ContPolinputSql10";
			    var mySql10=new SqlClass();
			    mySql10.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
			    mySql10.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
				var sql10=mySql10.getString();
		 
		  arrResult=easyExecSql(sql10,1,0);
		 
//         arrResult=easyExecSql("select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr " + 
//                               "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d " +
//                               "where 1=1 " +
//                               //modify by ml 20060301 for efficiency (remove trim)
//                               "and a.GrpContNo='" + arrQueryResult[0][0] + "' "+
//                               "and b.agentgroup=c.agentgroup " +
//                               "and c.agentcode=trim(a.agentcode) " +
//                               "and d.agentcode=trim(a.agentcode) " +
//                               "and c.grpcontno=a.GrpContNo",1,0);
         
         if(arrResult==null){
           //alert("δ�õ���ҵ��Ա��Ϣ");
           //return;
         }else{
           displayMainAgent();       //��ʾ��ҵ��Ա����ϸ����
         }
         

/////////////////////////////////////////////////////////////
			}    
		}
		
		if( mOperate == 2 )	{		// Ͷ����λ��Ϣ
		
				 var sqlid11="ContPolinputSql11";
			    var mySql11=new SqlClass();
			    mySql11.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
			    mySql11.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
				var sql11=mySql11.getString();
				
		  arrResult = easyExecSql(sql11, 1, 0);
		  //arrResult = easyExecSql("select b.CustomerNo,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽Ͷ����λ��Ϣ");
			} else {
			   displayAddress(arrResult[0]);
			}
		}
	}
	//turnPage.queryModal("select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where GrpContNo='" + arrQueryResult[0][0] + "'",ImpartGrid);	
	//turnPage.queryModal("select InsuYear,InsuYearFlag,InsuContent,Rate,EnsureContent,Peoples,RecompensePeoples,OccurMoney,RecompenseMoney,PendingMoney,SerialNo from LCHistoryImpart where GrpContNo='"+ arrQueryResult[0][0] + "'",HistoryImpartGrid);
	//turnPage.queryModal("select OcurTime,DiseaseName,DiseasePepoles,CureMoney,Remark,SerialNo from LCDiseaseImpart where GrpContNo='"+ arrQueryResult[0][0] + "'",DiseaseGrid);
	//�ͻ��������
	//turnPage.queryModal("select ServKind,ServDetail,ServChoose,ServRemark from LCGrpServInfo where GrpContNo='" + arrQueryResult[0][0] + "'",ServInfoGrid);	

	mOperate = 0;		// �ָ���̬
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ���ݷ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAddress()
{ 
try {document.all('GrpNo').value= ""; } catch(ex) { };
try {document.all('CustomerNo').value= ""; } catch(ex) { };
try {document.all('GrpAddressNo').value= ""; } catch(ex) { };
try {document.all('GrpAddress').value= ""; } catch(ex) { };
try {document.all('GrpZipCode').value= ""; } catch(ex) { };
try {document.all('LinkMan1').value= ""; } catch(ex) { };
try {document.all('Department1').value=""; } catch(ex) { };
try {document.all('HeadShip1').value= ""; } catch(ex) { };
try {document.all('Phone1').value= ""; } catch(ex) { };
try {document.all('E_Mail1').value= ""; } catch(ex) { };
try {document.all('Fax1').value= ""; } catch(ex) { };
try {document.all('LinkMan2').value= ""; } catch(ex) { };
try {document.all('Department2').value= ""; } catch(ex) { };
try {document.all('HeadShip2').value= ""; } catch(ex) { };
try {document.all('Phone2').value= ""; } catch(ex) { };
try {document.all('E_Mail2').value= ""; } catch(ex) { };
try {document.all('Fax2').value= ""; } catch(ex) { };
try {document.all('Operator').value= ""; } catch(ex) { };
try {document.all('MakeDate').value= ""; } catch(ex) { };
try {document.all('MakeTime').value= ""; } catch(ex) { };
try {document.all('ModifyDate').value= ""; } catch(ex) { };
try {document.all('ModifyTime').value= ""; } catch(ex) { };
try {document.all('AppntVIPValue').value= ""; } catch(ex) { };
try {document.all('BankAccNo1').value= ""; } catch(ex) { }; 
//������ldgrp��
try {document.all('GrpNo').value= arrResult[0][0]; } catch(ex) { };  
try {document.all('GrpName').value= arrResult[0][1];   } catch(ex) { };     
try {document.all('BusinessType').value= arrResult[0][2];} catch(ex) { };        
try {document.all('GrpNature').value= arrResult[0][3]; } catch(ex) { };       
try {document.all('Peoples').value= arrResult[0][4]; } catch(ex) { };       
try {document.all('RgtMoney').value= arrResult[0][5]; } catch(ex) { };       
try {document.all('Asset').value= arrResult[0][6]; } catch(ex) { };       
try {document.all('NetProfitRate').value= arrResult[0][7];} catch(ex) { };        
try {document.all('MainBussiness').value= arrResult[0][8];} catch(ex) { };        
try {document.all('Corporation').value= arrResult[0][9];  } catch(ex) { };      
try {document.all('ComAera').value= arrResult[0][10]; } catch(ex) { };  
try {document.all('Fax').value= arrResult[0][11]; } catch(ex) { };  
try {document.all('Phone').value= arrResult[0][12]; } catch(ex) { };  
try {document.all('FoundDate').value= arrResult[0][13]; } catch(ex) { };  
try {document.all('AppntOnWorkPeoples').value= arrResult[0][14]; } catch(ex) { };
try {document.all('AppntOffWorkPeoples').value= arrResult[0][15]; } catch(ex) { };
try {document.all('AppntOtherPeoples').value= arrResult[0][16]; } catch(ex) { };
try {document.all('AppntVIPValue').value= arrResult[0][17]; } catch(ex) { };
try {document.all('BankAccNo1').value= arrResult[0][18]; } catch(ex) { };           
}
function displayAddress1()
{
 
try {document.all('GrpNo').value= arrResult[0][0]; } catch(ex) { };
try {document.all('CustomerNo').value= arrResult[0][0]; } catch(ex) { };
try {document.all('GrpAddressNo').value= arrResult[0][1]; } catch(ex) { };
try {document.all('GrpAddress').value= arrResult[0][2]; } catch(ex) { };
try {document.all('GrpZipCode').value= arrResult[0][3]; } catch(ex) { };
try {document.all('LinkMan1').value= arrResult[0][4]; } catch(ex) { };
try {document.all('Department1').value= arrResult[0][5]; } catch(ex) { };
try {document.all('HeadShip1').value= arrResult[0][6]; } catch(ex) { };
try {document.all('Phone1').value= arrResult[0][7]; } catch(ex) { };
try {document.all('E_Mail1').value= arrResult[0][8]; } catch(ex) { };
try {document.all('Fax1').value= arrResult[0][9]; } catch(ex) { };
try {document.all('LinkMan2').value= arrResult[0][10]; } catch(ex) { };
try {document.all('Department2').value= arrResult[0][11]; } catch(ex) { };
try {document.all('HeadShip2').value= arrResult[0][12]; } catch(ex) { };
try {document.all('Phone2').value= arrResult[0][13]; } catch(ex) { };
try {document.all('E_Mail2').value= arrResult[0][14]; } catch(ex) { };
try {document.all('Fax2').value= arrResult[0][15]; } catch(ex) { };
try {document.all('Operator').value= arrResult[0][16]; } catch(ex) { };
try {document.all('MakeDate').value= arrResult[0][17]; } catch(ex) { };
try {document.all('MakeTime').value= arrResult[0][18]; } catch(ex) { };
try {document.all('ModifyDate').value= arrResult[0][19]; } catch(ex) { };
try {document.all('ModifyTime').value= arrResult[0][20]; } catch(ex) { };

          
}

function displayAddress2()
{
//������ldgrp��

try {document.all('GrpName').value= arrResult[0][0];} catch(ex) { };   
try {document.all('BusinessType').value= arrResult[0][1];} catch(ex) { };        
try {document.all('GrpNature').value= arrResult[0][2]; } catch(ex) { };       
try {document.all('Peoples').value= arrResult[0][3]; } catch(ex) { };       
try {document.all('RgtMoney').value= arrResult[0][4]; } catch(ex) { };       
try {document.all('Asset').value= arrResult[0][5]; } catch(ex) { };       
try {document.all('NetProfitRate').value= arrResult[0][6];} catch(ex) { };        
try {document.all('MainBussiness').value= arrResult[0][7];} catch(ex) { };        
try {document.all('Corporation').value= arrResult[0][8];  } catch(ex) { };      
try {document.all('ComAera').value= arrResult[0][9]; } catch(ex) { };  
try {document.all('Fax').value= arrResult[0][10]; } catch(ex) { };  
try {document.all('Phone').value= arrResult[0][11]; } catch(ex) { };  
try {document.all('FoundDate').value= arrResult[0][12]; } catch(ex) { };
try {document.all('AppntOnWorkPeoples').value= arrResult[0][13]; } catch(ex) { };
try {document.all('AppntOffWorkPeoples').value= arrResult[0][14]; } catch(ex) { };
try {document.all('AppntOtherPeoples').value= arrResult[0][15]; } catch(ex) { };   
try {document.all('AppntVIPValue').value= arrResult[0][16]; } catch(ex) { };  
try {document.all('BankAccNo1').value= arrResult[0][17]; } catch(ex) { };      
}
function displayAddress3()
{
try {document.all('GrpName').value= arrResult[0][0];} catch(ex) { };   
try {document.all('GrpAddress').value= arrResult[0][1]; } catch(ex) { };
try {document.all('GrpZipCode').value= arrResult[0][2]; } catch(ex) { };
try {document.all('Phone').value= arrResult[0][3]; } catch(ex) { };	
}		
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
  //��"./AutoCreatLDGrpInit.jsp"�Զ�����
  try { document.all('GrpNo').value = arrResult[0][0]; } catch(ex) { };
  try { document.all('Password').value = arrResult[0][1]; } catch(ex) { };
  try { document.all('GrpName').value = arrResult[0][2]; } catch(ex) { };
  try { document.all('GrpAddressCode').value = arrResult[0][3]; } catch(ex) { };
  try { document.all('GrpAddress').value = arrResult[0][4]; } catch(ex) { };
  try { document.all('GrpZipCode').value = arrResult[0][5]; } catch(ex) { };
  try { document.all('GrpNature').value = arrResult[0][7]; } catch(ex) { };
  try { document.all('Peoples').value = arrResult[0][8]; } catch(ex) { };
  try { document.all('RgtMoney').value = arrResult[0][9]; } catch(ex) { };
  try { document.all('Asset').value = arrResult[0][10]; } catch(ex) { };
  try { document.all('NetProfitRate').value = arrResult[0][11]; } catch(ex) { };
  try { document.all('MainBussiness').value = arrResult[0][12]; } catch(ex) { };
  try { document.all('Corporation').value = arrResult[0][13]; } catch(ex) { };
  try { document.all('ComAera').value = arrResult[0][14]; } catch(ex) { };
  try { document.all('LinkMan1').value = arrResult[0][15]; } catch(ex) { };
  try { document.all('Department1').value = arrResult[0][16]; } catch(ex) { };
  try { document.all('HeadShip1').value = arrResult[0][17]; } catch(ex) { };
  try { document.all('Phone1').value = arrResult[0][18]; } catch(ex) { };
  try { document.all('E_Mail1').value = arrResult[0][19]; } catch(ex) { };
  try { document.all('Fax1').value = arrResult[0][20]; } catch(ex) { };
  try { document.all('LinkMan2').value = arrResult[0][21]; } catch(ex) { };
  try { document.all('Department2').value = arrResult[0][22]; } catch(ex) { };
  try { document.all('HeadShip2').value = arrResult[0][23]; } catch(ex) { };
  try { document.all('Phone2').value = arrResult[0][24]; } catch(ex) { };
  try { document.all('E_Mail2').value = arrResult[0][25]; } catch(ex) { };
  try { document.all('Fax2').value = arrResult[0][26]; } catch(ex) { };
  try { document.all('Fax').value = arrResult[0][27]; } catch(ex) { };
  try { document.all('Phone').value = arrResult[0][28]; } catch(ex) { };
  try { document.all('GetFlag').value = arrResult[0][29]; } catch(ex) { };
  try { document.all('Satrap').value = arrResult[0][30]; } catch(ex) { };
  try { document.all('EMail').value = arrResult[0][31]; } catch(ex) { };
  try { document.all('FoundDate').value = arrResult[0][32]; } catch(ex) { };
  try { document.all('BankAccNo').value = arrResult[0][33]; } catch(ex) { };
  try { document.all('BankCode').value = arrResult[0][34]; } catch(ex) { };
  try { document.all('GrpGroupNo').value = arrResult[0][35]; } catch(ex) { };
  try { document.all('State').value = arrResult[0][36]; } catch(ex) { };
  try { document.all('Remark').value = arrResult[0][37]; } catch(ex) { };
  try { document.all('BlacklistFlag').value = arrResult[0][38]; } catch(ex) { };
  try { document.all('Operator').value = arrResult[0][39]; } catch(ex) { };
  try { document.all('MakeDate').value = arrResult[0][40]; } catch(ex) { };
  try { document.all('MakeTime').value = arrResult[0][41]; } catch(ex) { };
  try { document.all('ModifyDate').value = arrResult[0][42]; } catch(ex) { };
  try { document.all('ModifyTime').value = arrResult[0][43]; } catch(ex) { };
  try { document.all('FIELDNUM').value = arrResult[0][44]; } catch(ex) { };
  try { document.all('PK').value = arrResult[0][45]; } catch(ex) { };
  try { document.all('fDate').value = arrResult[0][46]; } catch(ex) { };
  try { document.all('mErrors').value = arrResult[0][47]; } catch(ex) { };
}
function displayLCGrpCont() {
  try {document.all('GrpContNo').value= arrResult[0][0];} catch(ex) { }; 
  try {document.all('ProposalGrpContNo').value= arrResult[0][1];} catch(ex) { }; 
  try {document.all('PrtNo').value= arrResult[0][2];} catch(ex) { }; 
  try {document.all('SaleChnl').value= arrResult[0][3];} catch(ex) { }; 
  try {document.all('ManageCom').value= arrResult[0][4];} catch(ex) { }; 
  try {document.all('MediAgentCom').value= arrResult[0][5];} catch(ex) { }; 
  try {document.all('AgentCom').value= arrResult[0][5];} catch(ex) { }; 
  try {document.all('AgentType').value= arrResult[0][6];} catch(ex) { }; 
  try {document.all('AgentCode').value= arrResult[0][7];} catch(ex) { }; 
  try {document.all('AgentGroup').value= arrResult[0][8];} catch(ex) { }; 
  try {document.all('AgentCode1').value= arrResult[0][9];} catch(ex) { }; 
  try {document.all('Password').value= arrResult[0][10];} catch(ex) { }; 
  try {document.all('Password2').value= arrResult[0][11];} catch(ex) { }; 
  try {document.all('AppntNo').value= arrResult[0][12];} catch(ex) { }; 
  try {document.all('GrpAddressNo').value= arrResult[0][13];} catch(ex) { }; 
  try {document.all('Peoples2').value= arrResult[0][14];} catch(ex) { }; 
  try {document.all('GrpName').value= arrResult[0][15];} catch(ex) { }; 
  try {document.all('BusinessType').value= arrResult[0][16];} catch(ex) { }; 
  try {document.all('GrpNature').value= arrResult[0][17];} catch(ex) { }; 
  try {document.all('RgtMoney').value= arrResult[0][18];} catch(ex) { }; 
  try {document.all('Asset').value= arrResult[0][19];} catch(ex) { }; 
  try {document.all('NetProfitRate').value= arrResult[0][20];} catch(ex) { }; 
  try {document.all('MainBussiness').value= arrResult[0][21];} catch(ex) { }; 
  try {document.all('Corporation').value= arrResult[0][22];} catch(ex) { }; 
  try {document.all('ComAera').value= arrResult[0][23];} catch(ex) { }; 
  try {document.all('Fax').value= arrResult[0][24];} catch(ex) { }; 
  try {document.all('Phone').value= arrResult[0][25];} catch(ex) { }; 
  try {document.all('GetFlag').value= arrResult[0][26];} catch(ex) { }; 
  try {document.all('Satrap').value= arrResult[0][27];} catch(ex) { }; 
  try {document.all('EMail').value= arrResult[0][28];} catch(ex) { }; 
  try {document.all('FoundDate').value= arrResult[0][29];} catch(ex) { }; 
  try {document.all('GrpGroupNo').value= arrResult[0][30];} catch(ex) { }; 
  try {document.all('BankCode').value= arrResult[0][31];} catch(ex) { }; 
  try {document.all('BankAccNo').value= arrResult[0][32];} catch(ex) { };
  try {document.all('AccName').value= arrResult[0][33];} catch(ex) { }; 
  try {document.all('DisputedFlag ').value= arrResult[0][34];} catch(ex) { }; 
  try {document.all('OutPayFlag').value= arrResult[0][35];} catch(ex) { }; 
  try {document.all('GetPolMode').value= arrResult[0][36];} catch(ex) { }; 
  try {document.all('Lang').value= arrResult[0][37];} catch(ex) { }; 
  try {document.all('Currency').value= arrResult[0][38];} catch(ex) { }; 
  try {document.all('LostTimes').value= arrResult[0][39];} catch(ex) { }; 
  try {document.all('PrintCount').value= arrResult[0][40];} catch(ex) { }; 
  try {document.all('RegetDate').value= arrResult[0][41];} catch(ex) { }; 
  try {document.all('LastEdorDate').value= arrResult[0][42];} catch(ex) { }; 
  try {document.all('LastGetDate').value= arrResult[0][43];} catch(ex) { }; 
  try {document.all('LastLoanDate').value= arrResult[0][44];} catch(ex) { }; 
  try {document.all('SpecFlag').value= arrResult[0][45];} catch(ex) { }; 
  try {document.all('GrpSpec').value= arrResult[0][46];} catch(ex) { }; 
  try {document.all('PayMode').value= arrResult[0][47];} catch(ex) { }; 
  try {document.all('SignCom').value= arrResult[0][48];} catch(ex) { }; 
  try {document.all('SignDate').value= arrResult[0][49];} catch(ex) { }; 
  try {document.all('SignTime').value= arrResult[0][50];} catch(ex) { }; 
  try {document.all('CValiDate').value= arrResult[0][51];} catch(ex) { }; 
  try {document.all('PayIntv').value= arrResult[0][52];} catch(ex) { }; 
  try {document.all('ManageFeeRate').value= arrResult[0][53];} catch(ex) { }; 
  try {document.all('ExpPeoples').value= arrResult[0][54];} catch(ex) { }; 
  try {document.all('ExpPremium').value= arrResult[0][55];} catch(ex) { }; 
  try {document.all('ExpAmnt').value= arrResult[0][56];} catch(ex) { }; 
  try {document.all('Peoples').value= arrResult[0][57];} catch(ex) { }; 
  try {document.all('Mult').value= arrResult[0][58];} catch(ex) { }; 
  try {document.all('Prem').value= arrResult[0][59];} catch(ex) { }; 
  try {document.all('Amnt').value= arrResult[0][60];} catch(ex) { }; 
  try {document.all('SumPrem').value= arrResult[0][61];} catch(ex) { }; 
  try {document.all('SumPay').value= arrResult[0][62];} catch(ex) { }; 
  try {document.all('Dif').value= arrResult[0][63];} catch(ex) { }; 
  try {document.all('Remark').value= arrResult[0][64];} catch(ex) { }; 
  try {document.all('StandbyFlag1').value= arrResult[0][65];} catch(ex) { }; 
  try {document.all('StandbyFlag2').value= arrResult[0][66];} catch(ex) { }; 
  try {document.all('StandbyFlag3').value= arrResult[0][67];} catch(ex) { }; 
  try {document.all('InputOperator').value= arrResult[0][68];} catch(ex) { }; 
  try {document.all('InputDate').value= arrResult[0][69];} catch(ex) { }; 
  try {document.all('InputTime').value= arrResult[0][70];} catch(ex) { }; 
  try {document.all('ApproveFlag').value= arrResult[0][71];} catch(ex) { }; 
  try {document.all('ApproveCode').value= arrResult[0][72];} catch(ex) { }; 
  try {document.all('ApproveDate').value= arrResult[0][73];} catch(ex) { }; 
  try {document.all('ApproveTime').value= arrResult[0][74];} catch(ex) { }; 
  try {document.all('UWOperator').value= arrResult[0][75];} catch(ex) { }; 
  try {document.all('UWFlag').value= arrResult[0][76];} catch(ex) { }; 
  try {document.all('UWDate').value= arrResult[0][77];} catch(ex) { }; 
  try {document.all('UWTime').value= arrResult[0][78];} catch(ex) { }; 
  try {document.all('AppFlag').value= arrResult[0][79];} catch(ex) { }; 
  try {document.all('PolApplyDate').value= arrResult[0][80];} catch(ex) { }; 
  try {document.all('CustomGetPolDate').value= arrResult[0][81];} catch(ex) { }; 
  try {document.all('GetPolDate').value= arrResult[0][82];} catch(ex) { }; 
  try {document.all('GetPolTime').value= arrResult[0][83];} catch(ex) { }; 
  try {document.all('State').value= arrResult[0][84];} catch(ex) { }; 
  try {document.all('Operator').value= arrResult[0][85];} catch(ex) { }; 
  try {document.all('MakeDate').value= arrResult[0][86];} catch(ex) { }; 
  try {document.all('MakeTime').value= arrResult[0][87];} catch(ex) { }; 
  try {document.all('ModifyDate').value= arrResult[0][88];} catch(ex) { }; 
  try {document.all('ModifyTime').value= arrResult[0][89];} catch(ex) { };
  try {document.all('EnterKind').value= arrResult[0][90]; } catch(ex) { };
  try {document.all('AmntGrade').value= arrResult[0][91]; } catch(ex) { };
  try {document.all('Peoples3').value= arrResult[0][92]; } catch(ex) { }; 
  try {document.all('OnWorkPeoples').value= arrResult[0][93]; } catch(ex) { };     
  try {document.all('OffWorkPeoples').value= arrResult[0][94]; } catch(ex) { };    
  try {document.all('OtherPeoples').value= arrResult[0][95]; } catch(ex) { };      
  try {document.all('RelaPeoples').value= arrResult[0][96]; } catch(ex) { };       
  try {document.all('RelaMatePeoples').value= arrResult[0][97]; } catch(ex) { };   
  try {document.all('RelaYoungPeoples').value= arrResult[0][98]; } catch(ex) { };  	
  try {document.all('RelaOtherPeoples').value= arrResult[0][99]; } catch(ex) { }; 
  
  try {document.all('MediAgentCode').value= arrResult[0][107];  } catch(ex) { }; 
  	
  try {document.all('ReportNo').value= arrResult[0][113];  } catch(ex) { };
//  try {alert(arrResult[0][114])                   ;  } catch(ex) { };
  try {document.all('ConferNo').value= arrResult[0][114]; } catch(ex) { };
  try {document.all('SignReportNo').value= arrResult[0][115]; } catch(ex) { };
  try {document.all('AgentConferNo').value= arrResult[0][116]; } catch(ex) { };

}
function displayLCGrpPol() {
  //��"./AutoCreatLCGrpPolInit.jsp"�Զ�����
  try { document.all('ContNo').value = arrResult[0][0]; } catch(ex) { };
  try { document.all('ProposalGrpContNo').value = arrResult[0][1]; } catch(ex) { };
  try { document.all('PrtNo').value = arrResult[0][2]; } catch(ex) { };
  try { document.all('KindCode').value = arrResult[0][3]; } catch(ex) { };
  try { document.all('RiskCode').value = arrResult[0][5]; } catch(ex) { };
  try { document.all('RiskVersion').value = arrResult[0][6]; } catch(ex) { };
  try { document.all('SignCom').value = arrResult[0][7]; } catch(ex) { };
  try { document.all('ManageCom').value = arrResult[0][8]; } catch(ex) { };
  try { document.all('AgentCom').value = arrResult[0][9]; } catch(ex) { };
  try { document.all('AgentType').value = arrResult[0][10]; } catch(ex) { };
  try { document.all('SaleChnl').value = arrResult[0][11]; } catch(ex) { };
  try { document.all('Password').value = arrResult[0][12]; } catch(ex) { };
  try { document.all('GrpNo').value = arrResult[0][13]; } catch(ex) { };
  try { document.all('Password2').value = arrResult[0][14]; } catch(ex) { };
  try { document.all('GrpName').value = arrResult[0][15]; } catch(ex) { };
  try { document.all('GrpAddressCode').value = arrResult[0][16]; } catch(ex) { };
  try { document.all('GrpAddress').value = arrResult[0][17]; } catch(ex) { };
  try { document.all('GrpZipCode').value = arrResult[0][18]; } catch(ex) { };
  try { document.all('BusinessType').value = arrResult[0][19]; } catch(ex) { };
  try { document.all('GrpNature').value = arrResult[0][20]; } catch(ex) { };
  try { document.all('Peoples2').value = arrResult[0][21]; } catch(ex) { };
  try { document.all('RgtMoney').value = arrResult[0][22]; } catch(ex) { };
  try { document.all('Asset').value = arrResult[0][23]; } catch(ex) { };
  try { document.all('NetProfitRate').value = arrResult[0][24]; } catch(ex) { };
  try { document.all('MainBussiness').value = arrResult[0][25]; } catch(ex) { };
  try { document.all('Corporation').value = arrResult[0][26]; } catch(ex) { };
  try { document.all('ComAera').value = arrResult[0][27]; } catch(ex) { };
  try { document.all('LinkMan1').value = arrResult[0][28]; } catch(ex) { };
  try { document.all('Department1').value = arrResult[0][29]; } catch(ex) { };
  try { document.all('HeadShip1').value = arrResult[0][30]; } catch(ex) { };
  try { document.all('Phone1').value = arrResult[0][31]; } catch(ex) { };
  try { document.all('E_Mail1').value = arrResult[0][32]; } catch(ex) { };
  try { document.all('Fax1').value = arrResult[0][33]; } catch(ex) { };
  try { document.all('LinkMan2').value = arrResult[0][34]; } catch(ex) { };
  try { document.all('Department2').value = arrResult[0][35]; } catch(ex) { };
  try { document.all('HeadShip2').value = arrResult[0][36]; } catch(ex) { };
  try { document.all('Phone2').value = arrResult[0][37]; } catch(ex) { };
  try { document.all('E_Mail2').value = arrResult[0][38]; } catch(ex) { };
  try { document.all('Fax2').value = arrResult[0][39]; } catch(ex) { };
  try { document.all('Fax').value = arrResult[0][40]; } catch(ex) { };
  try { document.all('Phone').value = arrResult[0][41]; } catch(ex) { };
  try { document.all('GetFlag').value = arrResult[0][42]; } catch(ex) { };
  try { document.all('Satrap').value = arrResult[0][43]; } catch(ex) { };
  try { document.all('EMail').value = arrResult[0][44]; } catch(ex) { };
  try { document.all('FoundDate').value = arrResult[0][45]; } catch(ex) { };
  try { document.all('BankAccNo').value = arrResult[0][46]; } catch(ex) { };
  try { document.all('BankCode').value = arrResult[0][47]; } catch(ex) { };
  try { document.all('GrpGroupNo').value = arrResult[0][48]; } catch(ex) { };
  try { document.all('PayIntv').value = arrResult[0][49]; } catch(ex) { };
  try { document.all('PayMode').value = arrResult[0][50]; } catch(ex) { };
  try { document.all('CValiDate').value = arrResult[0][51]; } catch(ex) { };
  try { document.all('GetPolDate').value = arrResult[0][52]; } catch(ex) { };
  try { document.all('SignDate').value = arrResult[0][53]; } catch(ex) { };
  try { document.all('FirstPayDate').value = arrResult[0][54]; } catch(ex) { };
  try { document.all('PayEndDate').value = arrResult[0][55]; } catch(ex) { };
  try { document.all('PaytoDate').value = arrResult[0][56]; } catch(ex) { };
  try { document.all('RegetDate').value = arrResult[0][57]; } catch(ex) { };
  try { document.all('Peoples').value = arrResult[0][58]; } catch(ex) { };
  try { document.all('Mult').value = arrResult[0][59]; } catch(ex) { };
  try { document.all('Prem').value = arrResult[0][60]; } catch(ex) { };
  try { document.all('Amnt').value = arrResult[0][61]; } catch(ex) { };
  try { document.all('SumPrem').value = arrResult[0][62]; } catch(ex) { };
  try { document.all('SumPay').value = arrResult[0][63]; } catch(ex) { };
  try { document.all('Dif').value = arrResult[0][64]; } catch(ex) { };
  try { document.all('SSFlag').value = arrResult[0][65]; } catch(ex) { };
  try { document.all('PeakLine').value = arrResult[0][66]; } catch(ex) { };
  try { document.all('GetLimit').value = arrResult[0][67]; } catch(ex) { };
  try { document.all('GetRate').value = arrResult[0][68]; } catch(ex) { };
  try { document.all('MaxMedFee').value = arrResult[0][69]; } catch(ex) { };
  try { document.all('ExpPeoples').value = arrResult[0][70]; } catch(ex) { };
  try { document.all('ExpPremium').value = arrResult[0][71]; } catch(ex) { };
  try { document.all('ExpAmnt').value = arrResult[0][72]; } catch(ex) { };
  try { document.all('DisputedFlag').value = arrResult[0][73]; } catch(ex) { };
  try { document.all('BonusRate').value = arrResult[0][74]; } catch(ex) { };
  try { document.all('Lang').value = arrResult[0][75]; } catch(ex) { };
  try { document.all('Currency').value = arrResult[0][76]; } catch(ex) { };
  try { document.all('State').value = arrResult[0][77]; } catch(ex) { };
  try { document.all('LostTimes').value = arrResult[0][78]; } catch(ex) { };
  try { document.all('AppFlag').value = arrResult[0][79]; } catch(ex) { };
  try { document.all('ApproveCode').value = arrResult[0][80]; } catch(ex) { };
  try { document.all('ApproveDate').value = arrResult[0][81]; } catch(ex) { };
  try { document.all('UWOperator').value = arrResult[0][82]; } catch(ex) { };
  try { document.all('AgentCode').value = arrResult[0][83]; } catch(ex) { };
  try { document.all('AgentGroup').value = arrResult[0][84]; } catch(ex) { };
  try { document.all('AgentCode1').value = arrResult[0][85]; } catch(ex) { };
  try { document.all('Remark').value = arrResult[0][86]; } catch(ex) { };
  try { document.all('UWFlag').value = arrResult[0][87]; } catch(ex) { };
  try { document.all('OutPayFlag').value = arrResult[0][88]; } catch(ex) { };
  try { document.all('ApproveFlag').value = arrResult[0][89]; } catch(ex) { };
  try { document.all('EmployeeRate').value = arrResult[0][90]; } catch(ex) { };
  try { document.all('FamilyRate').value = arrResult[0][91]; } catch(ex) { };
  try { document.all('Operator').value = arrResult[0][92]; } catch(ex) { };
  try { document.all('MakeDate').value = arrResult[0][93]; } catch(ex) { };
  try { document.all('MakeTime').value = arrResult[0][94]; } catch(ex) { };
  try { document.all('ModifyDate').value = arrResult[0][95]; } catch(ex) { };
  try { document.all('ModifyTime').value = arrResult[0][96]; } catch(ex) { };
  try { document.all('FIELDNUM').value = arrResult[0][97]; } catch(ex) { };
  try { document.all('PK').value = arrResult[0][98]; } catch(ex) { };
  try { document.all('fDate').value = arrResult[0][99]; } catch(ex) { };
  try { document.all('ManageFeeRate').value = arrResult[0][100]; } catch(ex) { };
  try { document.all('GrpSpec').value = arrResult[0][101]; } catch(ex) { };
  try { document.all('GetPolMode').value = arrResult[0][102]; } catch(ex) { };
  try { document.all('PolApplyDate').value = arrResult[0][103]; } catch(ex) { };
  try { document.all('StandbyFlag1').value = arrResult[0][105]; } catch(ex) { };  
  try { document.all('StandbyFlag2').value = arrResult[0][106]; } catch(ex) { };  
  try { document.all('StandbyFlag3').value = arrResult[0][107]; } catch(ex) { };  

}




/*********************************************************************
 *  Click�¼���������������ݽ�����Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalGrpContNo.value;
	if( cPolNo == "" )
	{
		alert( "�������Ȳ�ѯͶ�������ܽ����ݽ�����Ϣ���֡�" );
		return false
	}
	
	showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=GROUP" ,"",sFeatures);
}  

function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //��������	
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }  
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   
   				 var sqlid12="ContPolinputSql12";
			    var mySql12=new SqlClass();
			    mySql12.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
			    mySql12.addSubPara(cAgentCode);//ָ������Ĳ���
				var strSQL=mySql12.getString();
   
//   	var strSQL = "select a.*,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'"; 

    var arrResult = easyExecSql(strSQL);
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][92];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][5];
  	  fm.AgentManageCom.value = arrResult[0][2];
  	  fm.ManageCom.value = fm.AgentManageCom.value;
  	  showComCodeName();
      
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
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
  	fm.AgentName.value = arrResult[0][3];
  	fm.AgentManageCom.value = arrResult[0][2];
  	fm.AgentGroup.value = arrResult[0][1];
  	fm.BranchAttr.value = arrResult[0][10];
  	fm.ManageCom.value = fm.AgentManageCom.value;
  	showComCodeName();
  }
}
//���ز�ѯ�����������ҵ��Աmultline
function afterQuery3(arrResult)
{	
  if(arrResult!=null)
  {
//		document.all( tField ).all( 'MultiAgentGrid1').value = arrResult[0][0];
//		document.all( tField ).all( 'MultiAgentGrid2').value = arrResult[0][3];
//		document.all( tField ).all( 'MultiAgentGrid3').value = arrResult[0][2];
//		document.all( tField ).all( 'MultiAgentGrid4').value = arrResult[0][10];
//		document.all( tField ).all( 'MultiAgentGrid6').value = arrResult[0][1];
//		document.all( tField ).all( 'MultiAgentGrid7').value = arrResult[0][8];
		
		var tRowNo = MultiAgentGrid.lastFocusRowNo;
		MultiAgentGrid.setRowColData(tRowNo,1,arrResult[0][0]);
        MultiAgentGrid.setRowColData(tRowNo,2,arrResult[0][3]);
        MultiAgentGrid.setRowColData(tRowNo,3,arrResult[0][2]);
        MultiAgentGrid.setRowColData(tRowNo,4,arrResult[0][10]);
        MultiAgentGrid.setRowColData(tRowNo,6,arrResult[0][1]);
        MultiAgentGrid.setRowColData(tRowNo,7,arrResult[0][8]);
  }
 
}

function afterQuery4(arrResult)
{	
  if(arrResult!=null)
  {
		fm.GrpNo.value = arrResult[0][0];
		fm.GrpName.value = arrResult[0][1];
		fm.Asset.value = arrResult[0][2];
		fm.GrpNature.value = arrResult[0][3];
		fm.BusinessType.value = arrResult[0][4];
		fm.Peoples.value = arrResult[0][5];
		fm.Fax.value = arrResult[0][6];
		fm.AppntVIPValue.value = arrResult[0][7];
  }

}

function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==8 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	
	   			var sqlid13="ContPolinputSql13";
			    var mySql13=new SqlClass();
			    mySql13.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
			    mySql13.addSubPara(cAgentCode);//ָ������Ĳ���
			    mySql13.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
				var strSql=mySql13.getString();
	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
}

function afterCodeSelect( cCodeName, Field )
{
    if(cCodeName=="GetGrpAddressNo"){
		
			   			var sqlid14="ContPolinputSql14";
			    var mySql14=new SqlClass();
			    mySql14.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
			    mySql14.addSubPara(fm.GrpAddressNo.value);//ָ������Ĳ���
			    mySql14.addSubPara(fm.GrpNo.value);//ָ������Ĳ���
				var strSQL=mySql14.getString();
		
        //var strSQL="select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b where b.AddressNo='"+fm.GrpAddressNo.value+"' and b.CustomerNo='"+fm.GrpNo.value+"'";
        arrResult=easyExecSql(strSQL);
        try {document.all('GrpAddressNo').value= arrResult[0][0]; } catch(ex) { };
        try {document.all('GrpAddress').value= arrResult[0][1]; } catch(ex) { };
        try {document.all('GrpZipCode').value= arrResult[0][2]; } catch(ex) { };
        try {document.all('LinkMan1').value= arrResult[0][3]; } catch(ex) { };
        try {document.all('Department1').value= arrResult[0][4]; } catch(ex) { };
        try {document.all('HeadShip1').value= arrResult[0][5]; } catch(ex) { };
        try {document.all('Phone1').value= arrResult[0][6]; } catch(ex) { };
        try {document.all('E_Mail1').value= arrResult[0][7]; } catch(ex) { };
        try {document.all('Fax1').value= arrResult[0][8]; } catch(ex) { };
        try {document.all('LinkMan2').value= arrResult[0][9]; } catch(ex) { };
        try {document.all('Department2').value= arrResult[0][10]; } catch(ex) { };
        try {document.all('HeadShip2').value= arrResult[0][11]; } catch(ex) { };
        try {document.all('Phone2').value= arrResult[0][12]; } catch(ex) { };
        try {document.all('E_Mail2').value= arrResult[0][13]; } catch(ex) { };
        try {document.all('Fax2').value= arrResult[0][14]; } catch(ex) { };
    }
    if(cCodeName=="RiskGrp"){
		
				var sqlid15="ContPolinputSql15";
			    var mySql15=new SqlClass();
			    mySql15.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
			    mySql15.addSubPara(Field.value);//ָ������Ĳ���
				var strSql=mySql15.getString();
		
        //var strSql="select risktype3 from lmriskapp where riskcode='"+Field.value+"'";
        var arr=easyExecSql(strSql);
        if(arr=="2"){   //�ֺ���
            divBonusFlag.style.display='';	
        }
        else{
            divBonusFlag.style.display='none';
        }
//        var strSql="select a.PayIntv, b.CodeName from LMRiskPayIntv a,LDCode b where a.ChooseFlag = '1'"
//               + " and b.CodeType = 'payintv' "
//               + " and a.PayIntv = b.Code "
//               + " and riskcode='"+Field.value+"'"
//               + " order by a.PayIntv";
//        var arr=easyExecSql(strSql);
//        if(arr!=""){
//            document.all('PayIntv').value = arr[0][0];	
//        }
    }
    if(cCodeName=="AgentType")
    {
        fm.MediAgentCom.value = "";
        fm.MediAgentComName.value = "";
        fm.MediAgentCode.value = "";
        
        clearMediAgent();
    }
    if(cCodeName == "GrpAgentCom")
    {
        clearMediAgent();
		
				var sqlid16="ContPolinputSql16";
			    var mySql16=new SqlClass();
			    mySql16.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
			    mySql16.addSubPara(fm.MediAgentCom.value);//ָ������Ĳ���
				var tSQL=mySql16.getString();
		
//        var tSQL = "select a.agentcode,c.name,a.agentgroup,"
//                   + "c.ManageCom,b.branchattr,a.rate " 
//                   + "from lacomtoagent a,labranchgroup b,laagent c "
//                   + "where b.agentgroup=a.agentgroup and c.agentcode=a.agentcode "
//                   //+ "and c.agentgroup=a.agentgroup "
//                   + "and a.agentcom='" + fm.MediAgentCom.value + "'"
//                   + " order by a.rate desc";
				   
        var tResult = easyExecSql(tSQL);
        if (tResult != null && tResult.length > 0) {            
            fm.AgentCode.value = tResult[0][0];
            fm.BranchAttr.value = tResult[0][4];
            fm.AgentGroup.value = tResult[0][2];
            fm.AgentName.value = tResult[0][1];
            fm.AgentManageCom.value = tResult[0][3];
            fm.ManageCom.value = fm.AgentManageCom.value;
            showComCodeName();
            if (tResult.length > 1)
            {
                fm.multiagentflag.checked = true;
                DivMultiAgent.style.display="";	
                MultiAgentGrid.clearData("MultiAgentGrid");
                for(i=1; i< tResult.length; i++)
                {
                    MultiAgentGrid.addOne("MultiAgentGrid");
                    MultiAgentGrid.setRowColData(i - 1,1,tResult[i][0]);
                    MultiAgentGrid.setRowColData(i - 1,2,tResult[i][1]);
                    MultiAgentGrid.setRowColData(i - 1,3,tResult[i][3]);
                    MultiAgentGrid.setRowColData(i - 1,4,tResult[i][4]);
                    MultiAgentGrid.setRowColData(i - 1,5,tResult[i][5]);
                    MultiAgentGrid.setRowColData(i - 1,6,tResult[i][2]);
                }
                MultiAgentGrid.delBlankLine();
            }
        }      
    }
}

function checkMainAppender(cRiskCode)
{
	if( isSubRisk( cRiskCode ) == true ) 
	{   // ����
		var tPolNo = getMainRiskNo(cRiskCode);   //����¼�븽�յĴ���,�õ����ձ�������
		if (!checkRiskRelation(tPolNo, cRiskCode)) 
		{
			alert("�����հ�����ϵ������������պŲ��ܴ���������գ�"); 
			return false;
		}
	}
  return true;
}


function isSubRisk(cRiskCode) {
	
					var sqlid17="ContPolinputSql17";
			    var mySql17=new SqlClass();
			    mySql17.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
			    mySql17.addSubPara(cRiskCode);//ָ������Ĳ���
				var tSQL=mySql17.getString();
	
	var arrQueryResult = easyExecSql(tSQL, 1, 0);
//  var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);

	if(arrQueryResult[0] == "S")    //��Ҫת�ɴ�д
		return true;
	if(arrQueryResult[0] == "M")
		return false;

	if (arrQueryResult[0].toUpperCase() == "A")
		if (confirm("�����ּȿ���������,�ֿ����Ǹ���!ѡ��ȷ����������¼��,ѡ��ȡ�����븽��¼��"))
			return false;
		else
			return true;

	return false;
}



function checkRiskRelation(tPolNo, cRiskCode) {
  // �����¸���Ͷ����
  
  				var sqlid18="ContPolinputSql18";
			    var mySql18=new SqlClass();
			    mySql18.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
			    mySql18.addSubPara(tPolNo);//ָ������Ĳ���
			    mySql18.addSubPara(cRiskCode);//ָ������Ĳ���
				var strSql=mySql18.getString();
  
// var strSql = "select RiskCode from LCGrpPol where GrpPolNo = '" + tPolNo 
//               + "' and RiskCode in (select Code1 from LDCode1 where Code = '" + cRiskCode + "' and codetype='grpchkappendrisk')";
  return easyQueryVer3(strSql);
}

function getMainRiskNo(cRiskCode) {
	var urlStr = "../app/MainRiskNoInput.jsp";
	var tPolNo="";
  
    //tPolNo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=310;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	tPolNo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	tPolNo.focus();
    return tPolNo;
}

function grpRiskInfo()
{
	//alert('a');
	//var newWindow = window.open("../app/GroupRisk.jsp","",sFeatures);
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("���뱣���ͬ��Ϣ���ܽ��롲������Ϣ����");    
	}
	delGrpVar();
	addGrpVar();
	showInfo = window.open("../app/GroupRisk.jsp","",sFeatures);
}

function grpInsuInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("���뱣���ͬ��Ϣ���ܡ���ӱ����ˡ���Ϣ��");  
	    return false;  
	}
	//alert("1111"+fm.GrpContNo.value);
	fm.GrpContNo.value=fm.ProposalGrpContNo.value;
	delGrpVar();
	addGrpVar();
	parent.fraInterface.window.location = "../app/ContInsuredInput.jsp?LoadFlag=2&ContType=2&scantype="+ scantype;
}

function grpInsuList()
{  
	
//*******************************//---add by haopan ,¼�뱻����ǰ��У��
	if (prtNo=="")
	{
		prtNo=fm.PrtNo.value;
		//alert("prtNohou="+prtNo);
	}
	
	  				var sqlid19="ContPolinputSql19";
			    var mySql19=new SqlClass();
			    mySql19.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
			    mySql19.addSubPara(prtNo);//ָ������Ĳ���
				var tSQL=mySql19.getString();
	
	//var tSQL="select * from lccontplanrisk where proposalgrpcontno='"+prtNo+"' and (plantype='3' or plantype='6')";
	var arrResult = easyExecSql(tSQL);
	//alert(tSQL);
	if(arrResult==null)
	{
	//alert("����¼��������Ϣ����ܽ���(�������嵥����Ϣ����!");
	//return false;
	  
	  if (!confirm("��ǰδ¼��������Ϣ�ͽ���(�������嵥����Ϣ���棬��ȷ���Ƿ�����������"))
	  {
	  	return false;
	  }
	  
	}
	
		  		var sqlid20="ContPolinputSql20";
			    var mySql20=new SqlClass();
			    mySql20.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
			    mySql20.addSubPara(prtNo);//ָ������Ĳ���
				var tSQL1=mySql20.getString();
	
	//var tSQL1="select * from LCGrpAppnt where prtno='"+prtNo+"'";
	var arrResult1 = easyExecSql(tSQL1);
	if(arrResult1==null)
	{
		alert("��ͬ��Ϣ��δ���棬��������루�������嵥����Ϣ����!");
		return false;
		}
	
  delGrpVar();
  addGrpVar();
  parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;    
    
}

function grpCarList()
{  
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("���뱣���ͬ��Ϣ���ܽ��루�����嵥����Ϣ���棡");  
	    return false;  
	} 
	
    delGrpVar();
    addGrpVar();
    parent.fraInterface.window.location = "../app/GrpContInsuredCarInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;    
    
}

function grpRiskPlanInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("���뱣���ͬ��Ϣ���ܽ��С����ռƻ��ƶ�����");  
	    return false;  
	}
	var newWindow = window.open("../app/ContPlan.jsp?GrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag,"",sFeatures);
}

function focuswrap(){
    myonfocus(showInfo);
}

function delGrpVar(){
    //ɾ���������ڻ����еĸ��˺�ͬ��Ϣ
    try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
    
    //�����ͬ��Ϣ
    try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalGrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
    try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('Password2'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples2'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('BusinessType'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpNature'); } catch(ex) { };
    try { mSwitch.deleteVar('RgtMoney'); } catch(ex) { };
    try { mSwitch.deleteVar('Asset'); } catch(ex) { };
    try { mSwitch.deleteVar('NetProfitRate'); } catch(ex) { };
    try { mSwitch.deleteVar('MainBussiness'); } catch(ex) { };
    try { mSwitch.deleteVar('Corporation'); } catch(ex) { };
    try { mSwitch.deleteVar('ComAera'); } catch(ex) { };
    try { mSwitch.deleteVar('Fax'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('GetFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('Satrap'); } catch(ex) { };
    try { mSwitch.deleteVar('EMail'); } catch(ex) { };
    try { mSwitch.deleteVar('FoundDate'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOnWorkPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOffWorkPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOtherPeoples'); } catch(ex) { };            
    try { mSwitch.deleteVar('GrpGroupNo'); } catch(ex) { };
    try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
    try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AccName'); } catch(ex) { };
    try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
    try { mSwitch.deleteVar('Lang'); } catch(ex) { };
    try { mSwitch.deleteVar('Currency'); } catch(ex) { };
    try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
    try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
    try { mSwitch.deleteVar('RegetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastEdorDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastGetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastLoanDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SpecFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpSpec'); } catch(ex) { };
    try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
    try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
    try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
    try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
    try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageFeeRate'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPremium'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpAmnt'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
    try { mSwitch.deleteVar('Mult'); } catch(ex) { };
    try { mSwitch.deleteVar('Prem'); } catch(ex) { };
    try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPay'); } catch(ex) { };
    try { mSwitch.deleteVar('Dif'); } catch(ex) { };
    try { mSwitch.deleteVar('Remark'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag1'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag2'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag3'); } catch(ex) { };
    try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
    try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
    try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
    try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
    try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
    try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    //����Ͷ������Ϣ
    try { mSwitch.deleteVar('GrpNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntGrade'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('PostalAddress'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpZipCode'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntType'); } catch(ex) { };
    try { mSwitch.deleteVar('RelationToInsured'); } catch(ex) { };

                                                      
}

function addGrpVar(){          
    try { mSwitch.addVar('ContNo','',''); } catch(ex) { };   
    try { mSwitch.addVar('ProposalContNo','',''); } catch(ex) { }; 
    //�����ͬ��Ϣ
    try { mSwitch.addVar('GrpContNo','',fm.GrpContNo.value); } catch(ex) { };
    try { mSwitch.addVar('ProposalGrpContNo','',fm.ProposalGrpContNo.value); } catch(ex) { };
    try { mSwitch.addVar('PrtNo','',fm.PrtNo.value); } catch(ex) { };
    try { mSwitch.addVar('SaleChnl','',fm.SaleChnl.value); } catch(ex) { };
    try { mSwitch.addVar('ManageCom','',fm.ManageCom.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCom','',fm.AgentCom.value); } catch(ex) { };
    try { mSwitch.addVar('AgentType','',fm.AgentType.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCode','',fm.AgentCode.value); } catch(ex) { };
    try { mSwitch.addVar('AgentGroup','',fm.AgentGroup.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCode1','',fm.AgentCode1.value); } catch(ex) { };
    try { mSwitch.addVar('Password','',fm.Password.value); } catch(ex) { };
    try { mSwitch.addVar('Password2','',fm.Password2.value); } catch(ex) { };
    try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
    try { mSwitch.addVar('Addressno','',fm.AddressNo.value); } catch(ex) { };
    try { mSwitch.addVar('Peoples2','',fm.Peoples2.value); } catch(ex) { };
    try { mSwitch.addVar('GrpName','',fm.GrpName.value); } catch(ex) { };
    try { mSwitch.addVar('BusinessType','',fm.BusinessType.value); } catch(ex) { };
    try { mSwitch.addVar('GrpNature','',fm.GrpNature.value); } catch(ex) { };
    try { mSwitch.addVar('RgtMoney','',fm.RgtMoney.value); } catch(ex) { };
    try { mSwitch.addVar('Asset','',fm.Asset.value); } catch(ex) { };
    try { mSwitch.addVar('NetProfitRate','',fm.NetProfitRate.value); } catch(ex) { };
    try { mSwitch.addVar('MainBussiness','',fm.MainBussiness.value); } catch(ex) { };
    try { mSwitch.addVar('Corporation','',fm.Corporation.value); } catch(ex) { };
    try { mSwitch.addVar('ComAera','',fm.ComAera.value); } catch(ex) { };
    try { mSwitch.addVar('Fax','',fm.Fax.value); } catch(ex) { };
    try { mSwitch.addVar('Phone','',fm.Phone.value); } catch(ex) { };
    try { mSwitch.addVar('GetFlag','',fm.GetFlag.value); } catch(ex) { };
    try { mSwitch.addVar('Satrap','',fm.Satrap.value); } catch(ex) { };
    try { mSwitch.addVar('EMail','',fm.EMail.value); } catch(ex) { };
    try { mSwitch.addVar('FoundDate','',fm.FoundDate.value); } catch(ex) { };
    try { mSwitch.addVar('GrpGroupNo','',fm.GrpGroupNo.value); } catch(ex) { };
    try { mSwitch.addVar('BankCode','',fm.BankCode.value); } catch(ex) { };
    try { mSwitch.addVar('BankAccNo','',fm.BankAccNo.value); } catch(ex) { };
    try { mSwitch.addVar('AccName','',fm.AccName.value); } catch(ex) { };
    try { mSwitch.addVar('DisputedFlag','',fm.DisputedFlag.value); } catch(ex) { };
    try { mSwitch.addVar('OutPayFlag','',fm.OutPayFlag.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolMode','',fm.GetPolMode.value); } catch(ex) { };
    try { mSwitch.addVar('Lang','',fm.Lang.value); } catch(ex) { };
    try { mSwitch.addVar('Currency','',fm.Currency.value); } catch(ex) { };
    try { mSwitch.addVar('LostTimes','',fm.LostTimes.value); } catch(ex) { };
    try { mSwitch.addVar('PrintCount','',fm.PrintCount.value); } catch(ex) { };
    try { mSwitch.addVar('RegetDate','',fm.RegetDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastEdorDate','',fm.LastEdorDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastGetDate','',fm.LastGetDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastLoanDate','',fm.LastLoanDate.value); } catch(ex) { };
    try { mSwitch.addVar('SpecFlag','',fm.SpecFlag.value); } catch(ex) { };
    try { mSwitch.addVar('GrpSpec','',fm.GrpSpec.value); } catch(ex) { };
    try { mSwitch.addVar('PayMode','',fm.PayMode.value); } catch(ex) { };
    try { mSwitch.addVar('SignCom','',fm.SignCom.value); } catch(ex) { };
    try { mSwitch.addVar('SignDate','',fm.SignDate.value); } catch(ex) { };
    try { mSwitch.addVar('SignTime','',fm.SignTime.value); } catch(ex) { };
    try { mSwitch.addVar('CValiDate','',fm.CValiDate.value); } catch(ex) { };
    try { mSwitch.addVar('PayIntv','',fm.PayIntv.value); } catch(ex) { };
    try { mSwitch.addVar('ManageFeeRate','',fm.ManageFeeRate.value); } catch(ex) { };
    try { mSwitch.addVar('ExpPeoples','',fm.ExpPeoples.value); } catch(ex) { };
    try { mSwitch.addVar('ExpPremium','',fm.ExpPremium.value); } catch(ex) { };
    try { mSwitch.addVar('ExpAmnt','',fm.ExpAmnt.value); } catch(ex) { };
    try { mSwitch.addVar('Peoples','',fm.Peoples.value); } catch(ex) { };
    try { mSwitch.addVar('Mult','',fm.Mult.value); } catch(ex) { };
    try { mSwitch.addVar('Prem','',fm.Prem.value); } catch(ex) { };
    try { mSwitch.addVar('Amnt','',fm.Amnt.value); } catch(ex) { };
    try { mSwitch.addVar('SumPrem','',fm.SumPrem.value); } catch(ex) { };
    try { mSwitch.addVar('SumPay','',fm.SumPay.value); } catch(ex) { };
    try { mSwitch.addVar('Dif','',fm.Dif.value); } catch(ex) { };
    try { mSwitch.addVar('Remark','',fm.Remark.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag1','',fm.StandbyFlag1.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag2','',fm.StandbyFlag2.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag3','',fm.StandbyFlag3.value); } catch(ex) { };
    try { mSwitch.addVar('InputOperator','',fm.InputOperator.value); } catch(ex) { };
    try { mSwitch.addVar('InputDate','',fm.InputDate.value); } catch(ex) { };
    try { mSwitch.addVar('InputTime','',fm.InputTime.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveFlag','',fm.ApproveFlag.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveCode','',fm.ApproveCode.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveDate','',fm.ApproveDate.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveTime','',fm.ApproveTime.value); } catch(ex) { };
    try { mSwitch.addVar('UWOperator','',fm.UWOperator.value); } catch(ex) { };
    try { mSwitch.addVar('UWFlag','',fm.UWFlag.value); } catch(ex) { };
    try { mSwitch.addVar('UWDate','',fm.UWDate.value); } catch(ex) { };
    try { mSwitch.addVar('UWTime','',fm.UWTime.value); } catch(ex) { };
    try { mSwitch.addVar('AppFlag','',fm.AppFlag.value); } catch(ex) { };
    try { mSwitch.addVar('PolApplyDate','',fm.PolApplyDate.value); } catch(ex) { };
    try { mSwitch.addVar('CustomGetPolDate','',fm.CustomGetPolDate.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolDate','',fm.GetPolDate.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolTime','',fm.GetPolTime.value); } catch(ex) { };
    try { mSwitch.addVar('State','',fm.State.value); } catch(ex) { };
    //����Ͷ������Ϣ

    try { mSwitch.addVar('GrpNo','',fm.GrpNo.value); } catch(ex) { };
    try { mSwitch.addVar('PrtNo','',fm.PrtNo.value); } catch(ex) { };
    try { mSwitch.addVar('AddressNo','',fm.AddressNo.value); } catch(ex) { };
    try { mSwitch.addVar('AppntGrade','',fm.AppntGrade.value); } catch(ex) { };
    try { mSwitch.addVar('GrpName','',fm.Name.value); } catch(ex) { };
    try { mSwitch.addVar('PostalAddress','',fm.PostalAddress.value); } catch(ex) { };
    try { mSwitch.addVar('ZipCode','',fm.ZipCode.value); } catch(ex) { };
    try { mSwitch.addVar('Phone','',fm.Phone.value); } catch(ex) { };
    try { mSwitch.addVar('Password','',fm.Password.value); } catch(ex) { };
    try { mSwitch.addVar('State','',fm.State.value); } catch(ex) { };
    try { mSwitch.addVar('AppntType','',fm.AppntType.value); } catch(ex) { };
    try { mSwitch.addVar('RelationToInsured','',fm.RelationToInsured.value); } catch(ex) { };

}                       
function checkuseronly(comname)
{
	
			  	var sqlid21="ContPolinputSql21";
			    var mySql21=new SqlClass();
			    mySql21.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
			    mySql21.addSubPara(comname);//ָ������Ĳ���
				var tSQL1=mySql21.getString();
	
	arrResult = easyExecSql(tSQL1, 1, 0);
//arrResult = easyExecSql("select * from LDGrp  where GrpName='" + comname + "'", 1, 0);			
	 if (arrResult == null) {
            
	  } 
	  else {      	
	  
	  			var sqlid22="ContPolinputSql22";
			    var mySql22=new SqlClass();
			    mySql22.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
			    mySql22.addSubPara(arrResult[0][0]);//ָ������Ĳ���
				var tSQL1=mySql22.getString();
	         
			 arrResult = easyExecSql(tSQL1, 1, 0);
 	         //arrResult = easyExecSql("select b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='" + arrResult[0][0] + "'", 1, 0);			
	         if (arrResult == null) {
                    alert("δ�鵽Ͷ����λ��Ϣ");
	         } 
	         else {
	         try {document.all('GrpName').value= arrResult[0][0];} catch(ex) { };  
	          try {document.all('BusinessType').value= arrResult[0][1];} catch(ex) { };        
                 try {document.all('GrpNature').value= arrResult[0][2]; } catch(ex) { };       
                 try {document.all('Peoples').value= arrResult[0][3]; } catch(ex) { };       
                 try {document.all('RgtMoney').value= arrResult[0][4]; } catch(ex) { };       
                 try {document.all('Asset').value= arrResult[0][5]; } catch(ex) { };       
                 try {document.all('NetProfitRate').value= arrResult[0][6];} catch(ex) { };        
                 try {document.all('MainBussiness').value= arrResult[0][7];} catch(ex) { };        
                 try {document.all('Corporation').value= arrResult[0][8];  } catch(ex) { };      
                 try {document.all('ComAera').value= arrResult[0][9]; } catch(ex) { };  
                 try {document.all('Fax').value= arrResult[0][10]; } catch(ex) { };  
                 try {document.all('Phone').value= arrResult[0][11]; } catch(ex) { };  
                 try {document.all('FoundDate').value= arrResult[0][12]; } catch(ex) { };
                 try {document.all('GrpNo').value= arrResult[0][13]; } catch(ex) { };
                 try {document.all('AppntAppntOnWorkPeoples').value= arrResult[0][14]; } catch(ex) { };
                 try {document.all('AppntOffWorkPeoples').value= arrResult[0][15]; } catch(ex) { };
                 try {document.all('AppntOtherPeoples').value= arrResult[0][16]; } catch(ex) { };                                                   
	        }
	  }	
}                        
//��������ҳ��ĺ�����δ���õ�
function InputPolicy()
{
	var newWindow = window.open("../app/NewProposal.jsp?RiskCode=111302","',sFeatures");
}
function InputPolicyNoList()
{
	var newWindow = window.open("../app/NewProposal.jsp?NoListFlag=1&RiskCode=111302","",sFeatures);
}

//���һ�����ּ�¼
function addRecord()
{
	  //if( verifyInput2() == false ) return;
	  var tRiskCode = document.all('RiskCode').value ;
	  var tPayIntv = document.all('PayIntv').value ;
	  //var ExpPeoples = document.all('ExpPeoples').value ;	  
	  var tGrpContNo = document.all('GrpContNo').value ;
	  var tExpPrem = document.all('ExpPrem').value;
	  document.all( 'LoadFlag' ).value = LoadFlag ;
	  
	  //��ѯ���ݿ��ж��Ƿ��Ѿ������ͬ��Ϣ
	  
	  	  		var sqlid23="ContPolinputSql23";
			    var mySql23=new SqlClass();
			    mySql23.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
			    mySql23.addSubPara(tGrpContNo);//ָ������Ĳ���
				var aSQL=mySql23.getString();
	  
	 // var aSQL = "select * from lcgrpcont where trim(grpcontno)=trim('"+tGrpContNo+"')";
	  var aResult = easyExecSql(aSQL, 1, 0);
	  
	  if(aResult==null)
	  {	  	
	  	alert("�ŵ���ͬ��Ϣδ���棬������������֡���");
	  	return ;
	  }
	  
	  if(tRiskCode==null ||tRiskCode==""|| tPayIntv==null|| tPayIntv=="")
	  {	  
	  	alert("������Ϣδ����¼�룡");
	  	return ;
	  }
	  
	  if(tExpPrem==null ||tExpPrem==""|| tExpPrem=="null")
	  {	  
	  	alert("��¼�뱣�ѽ�");
	  	document.all('ExpPrem').focus();
	  	document.all('ExpPrem').select();
	  	return ;
	  }
	  if(isNumeric(tExpPrem) == false)
	  {	  
	  	alert("��¼����ȷ�ı��ѽ�");
	  	document.all('ExpPrem').focus();
	  	document.all('ExpPrem').select();
	  	return ;
	  }
/*********
	  if(document.all('ExpPeoples').value != "" && isNaN(parseFloat(document.all('ExpPeoples').value )) && !isNumeric(document.all('ExpPeoples').value ))
	   {	  
	   	alert("��Ϊ'Ԥ������'¼�����֣�");	
	  	return ;
	  }
***********/
    RiskGrid.delBlankLine();
    document.all('fmAction').value="INSERT||GROUPRISK";
    
    var showStr="��������ŵ�������Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
		//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

   
    fm.action="../app/GroupRiskSave.jsp"
    document.getElementById("fm").submit();
}
  
//ɾ��һ�����ּ�¼
  function deleteRecord()
{
  
    RiskGrid.delBlankLine();
    var showStr="����ɾ��������Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

    document.all('fmAction').value="DELETE||GROUPRISK";
    fm.action="../app/GroupRiskSave.jsp"
    document.getElementById("fm").submit();  
    
}


/*********************************************************************
 *  �ŵ�������Ϣ�ĵ�¼��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function grpFeeInput()
{
   	var tGrpContNo = document.all('GrpContNo').value ;
 	  if(tGrpContNo==null ||tGrpContNo=="")
	  {	  	
	  	alert("�ŵ���ͬ��Ϣδ���棬������������֡���");
	  	return ;
	  }
	try { mSwitch.addVar('GrpNo','',fm.GrpNo.value); } catch(ex) { };
	try { mSwitch.addVar('ManageCom','',fm.ManageCom.value); } catch(ex) { };
   //delGrpVar();
   //addGrpVar();
    parent.fraInterface.window.location = "../app/GrpFeeInput.jsp?ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;     
}

/*********************************************************************
 *  ��ʼ��������ʾ������ �����ͱ��ѵ�ͳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function fillriskgrid(){
    if(fm.ProposalGrpContNo.value!="")
    {
        //alert("a--document.all('GrpContNo').value=="+document.all('GrpContNo').value);
        var strSql;
//        var strfenshu;
//        var straa="(select  nvl(sum(c.Mult), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ),";
//        strfenshu="select nvl(sum(c.mult),0) from lcgrppol a ,lcpol c  "
//        				+"where c.grpcontno = a.grpcontno and c.riskcode = a.riskcode and c.grpcontno = '"+document.all('GrpContNo').value+"'";
//        	 var arrResult = easyExecSql(strfenshu);
//        	 //alert(arrResult[0][0]);
//        	 if(arrResult[0][0]=="0")
//        	 {
//        	 	straa="(select  nvl(sum(c.Amnt), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ),";
//        	}
        //alert(1);
        
        // strSql=" select a.riskcode, b.riskname,a.payintv,a.exppeoples,"
        //       +"(select count(c.riskcode) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ),"
        //       +"(select  nvl(sum(c.prem), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ),"
        //       +"(select round((select count(c.riskcode) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode )/(select peoples from LDGrp where CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '"+document.all('GrpContNo').value+"')),2) from dual)" 
        //       +"from lcgrppol a, lmriskapp b Where a.riskcode = b.riskcode and a.grpcontno = '"+document.all('GrpContNo').value+"'" ;
        
		
			  	var sqlid24="ContPolinputSql24";
			    var mySql24=new SqlClass();
			    mySql24.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
			    mySql24.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
				var strSql=mySql24.getString();
		
		
//        strSql=" select a.riskcode, b.riskname,a.payintv,a.exppeoples,"
//        +"((select count(c.riskcode) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode " 
//        +" and c.poltypeflag='0') + "
//        +"(select nvl(sum(i.insuredpeoples),0) from lcpol c,lcinsured i where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode " 
//        +" and i.contno=c.contno and i.insuredno=c.insuredno and c.poltypeflag='1')) as peoples,"
//        +"a.exppremium,"
//        +"(select  nvl(sum(c.prem), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ),"
//        +"a.expamnt,"
//        //+"(select  nvl(sum(c.Amnt), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ),"
//        +"(select  nvl(sum(c.Mult), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ), " 
//        +"(select  nvl(sum(c.Amnt), 0) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode ),"
//        +"(select round(((select count(c.riskcode) from lcpol c where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode " 
//        +" and c.poltypeflag='0') + "
//        +"(select nvl(sum(i.insuredpeoples),0) from lcpol c,lcinsured i where c.grpcontno = '"+document.all('GrpContNo').value+"' and c.riskcode = a.riskcode " 
//        +" and i.contno=c.contno and i.insuredno=c.insuredno and c.poltypeflag='1'))/(select decode(peoples,0,1,peoples) from LDGrp where CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '"+document.all('GrpContNo').value+"')),2) from dual)" 
//        +"from lcgrppol a, lmriskapp b Where a.riskcode = b.riskcode and a.grpcontno = '"+document.all('GrpContNo').value+"' order by a.riskcode" ;
        
        /* strSql="Select a.riskcode,(select riskname from lmriskapp b where b.riskcode = a.riskcode)riskname,"
        +"a.PayIntv, a.ExpPeoples,count(c.riskcode),nvl(sum(c.prem), 0) "
        +"From lcgrppol a, lcpol c "
        +"Where a.grppolno = c.grppolno and a.grpcontno = '"+document.all('GrpContNo').value+"'"
        +"group by a.riskcode, a.payintv, a.exppeoples ";*/
		
			   var sqlid25="ContPolinputSql25";
			    var mySql25=new SqlClass();
			    mySql25.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
			    mySql25.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
				var strSql=mySql25.getString();
		
		var aResult = easyExecSql(strSql, 1, 0);
        //var aResult = easyExecSql("select * from LCGrpCont where GrpContNo = '" + document.all('GrpContNo').value + "'", 1, 0);                        
        // alert("strSql1"+strSql);
        if(aResult==null)
        {
            return;	
        }
        //alert("aResult"+aResult);
        
//        var aResult1= easyExecSql("select name from LCInsured where GrpContNo = '" + document.all('GrpContNo').value + "'", 1, 0);
//        if(aResult1!=null){
//            if(aResult1[0][0]=="������")
//            {
//             strSql = "Select a.riskcode ,b.riskname ,a.PayIntv,"
//                        +"a.ExpPeoples,"
//                       +"(select insuredpeoples from lcinsured where grpcontno=a.grpcontno) as num,"
//                       +"(select insuredpeoples from lcinsured where grpcontno=a.grpcontno)*(select case when  Sum(prem) is null then 0 else Sum(prem)  end From lcpol Where grpcontno=a.Grpcontno )"                  
//                       +" From lcgrppol a,LMRiskApp b " 
//
//            }
//    
//        }		
        //	  		alert(3);    
        //		    alert("strSq3"+strSql); 
        turnPage1.queryModal(strSql, RiskGrid);
    }
    else
    { 
        //alert(2);
        return false;  
    }
  
} 

function getapproveinfo(){
	mOperate=1;
	var approveinfo=new Array(); 
	approveinfo[0]=new Array(); 
	approveinfo[0][0]=polNo;
//alert("1");
	afterQuery(approveinfo);	    
//alert("2");
	queryAgent();
}
/*********************************************************************
 *  ѡ���ŵ��������¼��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpQuestInput()
{   
	var cGrpProposalContNo = fm.ProposalGrpContNo.value;  //���屣������	
	if(cGrpProposalContNo==""||cGrpProposalContNo==null)
	{
  		alert("����ѡ��һ������Ͷ����!");
  		return ;
         }
	window.open("./GrpQuestInputMain.jsp?GrpContNo="+cGrpProposalContNo+"&Flag="+LoadFlag,"",sFeatures);
	
}
/*********************************************************************
 *  ѡ���ŵ�������Ĳ�ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */                        
function GrpQuestQuery(mOperFlag)
{
	var cGrpContNo = fm.GrpContNo.value;  //�ŵ�Ͷ��������
	if(cGrpContNo==""||cGrpContNo==null)
	{
  		alert("����ѡ��һ����������Ͷ����!");
  		return ;
    }
    //alert(mOperFlag);
	window.open("./GrpQuestQueryMain.jsp?GrpContNo="+cGrpContNo+"&Flag="+LoadFlag+"&OperFlag="+mOperFlag,"",sFeatures);
}                        
/*********************************************************************
 *  ����ͨ�����ŵ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function gmanuchk()
{
	
	cProposalGrpContNo = fm.ProposalGrpContNo.value;  //�ŵ�Ͷ��������
	cflag="5";	

	if(MissionID == "null" || SubMissionID == "null")
	{
		fm.MissionID.value = mSwitch.getVar('MissionID');
		fm.SubMissionID.value = mSwitch.getVar('SubMissionID');
	}
	else
	{
		mSwitch.deleteVar("MissionID");
		mSwitch.deleteVar("SubMissionID");
	  mSwitch.addVar("MissionID", "", MissionID);
	  mSwitch.addVar("SubMissionID", "", SubMissionID);
	  mSwitch.updateVar("MissionID", "", MissionID);
	  mSwitch.updateVar("SubMissionID", "", SubMissionID);
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;			
	}
	if( cProposalGrpContNo == null || cProposalGrpContNo == "" )
		alert("��ѡ��������Ͷ�������ٽ��и��˲���");
	else
	{
	  if (confirm("�ò���������ͨ���ñ������µ�����Ͷ����Ϣ,ȷ����"))
	      {
		var i = 0;
		var showStr="���ڸ��˼���Ͷ�����������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��,����Ͷ��������:"+cProposalGrpContNo;
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  fm.action="./GroupPolApproveSave.jsp?ProposalGrpContNo1="+cProposalGrpContNo+"&Flag1=5";
    document.getElementById("fm").submit();
    }
    else
    return false;
	}

}
/*********************************************************************
 *  ������ذ�ť,�رյ�ǰҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function goback()
{  
	
	  if(LoadFlag=='16'){top.close();	}
    if (LoadFlag=='14'||LoadFlag=='23')
    {
          top.opener.querygrp();
    }
    else
    {
    	  top.opener.easyQueryClick();
    }
     top.close();	
}                        
                        
/*********************************************************************
 *  �����޸ĸ��ŵ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function approveupdate()
{
	
	cProposalGrpContNo = fm.ProposalGrpContNo.value;  //�ŵ�Ͷ��������
	cflag="5";	
	if( cProposalGrpContNo == null || cProposalGrpContNo == "" )
		alert("��ѡ��������Ͷ�������ٽ��и����޸�ȷ�ϲ���");
	else
	{
	  if (confirm("�ò�����ʾ���е��޸������,ȷ����"))
	      {
		var i = 0;
		var showStr="���ڸ����޸ļ���Ͷ�����������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��,����Ͷ��������:"+cProposalGrpContNo;
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();		
		  //window.open("./GroupPolApproveSave.jsp?ProposalGrpContNo="+cProposalGrpContNo+"&Flag1=5","windows1");
	        fm.action="./GrpApproveModifyMakeSure.jsp?ProposalGrpContNo="+cProposalGrpContNo+"&Flag1=5";
                document.getElementById("fm").submit();
          }
          else
          return false;
	    //window.close();
	    //document.getElementById("fm").submit(); //�ύ
	}

}                        


/*********************************************************************
 *  �ŵ��ֵ�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */                        
function grpSubContInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("���뱣���ͬ��Ϣ���ܽ��С��ֵ����ơ���");    
		return;
	}
	delGrpVar();
	addGrpVar();
	cGrpContNo = document.all("GrpContNo").value;
	cPrtNo = document.all("PrtNo").value;
	var newWindow = window.open("../app/SubContPolMain.jsp?GrpContNo=" + cGrpContNo + "&PrtNo=" + cPrtNo+"&LoadFlag="+LoadFlag,"",sFeatures);
}



function getdetailaddress(){
	
				   var sqlid26="ContPolinputSql26";
			    var mySql26=new SqlClass();
			    mySql26.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
			    mySql26.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
				var strSQL=mySql26.getString();
	
//var strSQL="select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b where b.AddressNo='"+fm.GrpAddressNo.value+"' and b.CustomerNo='"+fm.GrpNo.value+"'";
  arrResult=easyExecSql(strSQL);
try {document.all('GrpAddressNo').value= arrResult[0][0]; } catch(ex) { };
try {document.all('GrpAddress').value= arrResult[0][1]; } catch(ex) { };
try {document.all('GrpZipCode').value= arrResult[0][2]; } catch(ex) { };
try {document.all('LinkMan1').value= arrResult[0][3]; } catch(ex) { };
try {document.all('Department1').value= arrResult[0][4]; } catch(ex) { };
try {document.all('HeadShip1').value= arrResult[0][5]; } catch(ex) { };
try {document.all('Phone1').value= arrResult[0][6]; } catch(ex) { };
try {document.all('E_Mail1').value= arrResult[0][7]; } catch(ex) { };
try {document.all('Fax1').value= arrResult[0][8]; } catch(ex) { };
try {document.all('LinkMan2').value= arrResult[0][9]; } catch(ex) { };
try {document.all('Department2').value= arrResult[0][10]; } catch(ex) { };
try {document.all('HeadShip2').value= arrResult[0][11]; } catch(ex) { };
try {document.all('Phone2').value= arrResult[0][12]; } catch(ex) { };
try {document.all('E_Mail2').value= arrResult[0][13]; } catch(ex) { };
try {document.all('Fax2').value= arrResult[0][14]; } catch(ex) { };
}                                                                 
                              
/*********************************************************************
 *  �����ͬ��Ϣ¼�����ȷ��
 *  ����  ��  wFlag--��״̬ʱ���ô˺������ߵķ�֧
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{
	mWFlag = 1;
	if (wFlag ==1 ) //¼�����ȷ��
	{
		if (!document.all("PlanFlag").checked){
		//�ж�Ԥ�ڱ��Ѻ�ͳ�Ʊ����Ƿ����
		
				var sqlid27="ContPolinputSql27";
			    var mySql27=new SqlClass();
			    mySql27.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
			    mySql27.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
			     mySql27.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
				var tStr=mySql27.getString();
		
//    		var tStr= "	select riskcode from lcgrppol a where 1=1 "
//    							+" and grpcontno = '"+fm.ProposalGrpContNo.value+"'"
//    							+" and exppremium != "
//    							+ "(select nvl(sum(prem),0) from lcpol "
//    							+ "where grpcontno='" + fm.ProposalGrpContNo.value
//    							+ "' and riskcode=a.riskcode)";
    		tResult = easyExecSql(tStr, 1, 0);                    
    		if (tResult != null && tResult.length > 0) {
    		    var tRiskCode = "";
    		    for (i = 0; i < tResult.length ; i++ ){
    		        if (i == 0)
    		        {
    		            tRiskCode = tResult[i][0];
    		        }
    		        else
    		        {
    		            tRiskCode = tRiskCode + "," + tResult[i][0];
    		        }
    		    }
    		  	if (!confirm("����" + tRiskCode + "Ԥ�ڱ�����ʵ�ʱ���ֵ������ȷ��'¼�����'��")){
    		  	    return;
    		  	}
    		  }
    		  	//�ж�Ԥ�ڱ����ͳ�Ʊ����Ƿ����
    		 var strww;
    		 var tStr;
    		 var tStr1;
    		 var tStr2;
    		 var tRiskCode = "";
    		 //var j;
			 
			 	var sqlid28="ContPolinputSql28";
			    var mySql28=new SqlClass();
			    mySql28.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
			    mySql28.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
				tStr1=mySql28.getString();
			 
    		// tStr1= "select riskcode from lcgrppol where grpcontno = '"+fm.ProposalGrpContNo.value+"'";
    		 var arrResult1 = easyExecSql(tStr1);
    		 //alert(arrResult1);
    		 if (arrResult1 == null) 
    		 {
    		 	
    		}
    		else
    		{
    			//alert("121233");
    			for (j = 0; j < arrResult1.length ; j++ )
    			{
    				tStr2="mult";
					
				    var sqlid29="ContPolinputSql29";
			        var mySql29=new SqlClass();
				    mySql29.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
				    mySql29.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
				    mySql29.addSubPara(arrResult1[j][0]);//ָ������Ĳ���
					strww=mySql29.getString();
					
    				//strww="select nvl(sum(mult),0)  from lcpol where grpcontno = '"+fm.ProposalGrpContNo.value+"' and riskcode ='"+arrResult1[j][0]+"'";
    				var arrResult = easyExecSql(strww);
    				//alert(arrResult1[j]+"==="+arrResult[0][0]);
    				if(arrResult[0][0]=="0")
    				{
    					//alert("111111111");
    					tStr2="Amnt";
    				}
    				//alert(tStr2);
					
					var sqlid30="ContPolinputSql30";
			        var mySql30=new SqlClass();
				    mySql30.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
				    mySql30.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
				    mySql30.addSubPara(arrResult1[j][0]);//ָ������Ĳ���
				    mySql30.addSubPara(tStr2);//ָ������Ĳ���
				    mySql30.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
				    mySql30.addSubPara(arrResult1[j][0]);//ָ������Ĳ���
					tStr=mySql30.getString();
					
//    				tStr= "	select riskcode from lcgrppol a where 1=1 "
//    							+" and grpcontno = '"+fm.ProposalGrpContNo.value+"'"
//    							+" and a.riskcode='"+arrResult1[j][0]+"'"
//    							+" and expamnt != "
//    							+ "(select nvl(sum("+tStr2+"),0) from lcpol "
//    							+ "where grpcontno='" + fm.ProposalGrpContNo.value
//    							+ "' and riskcode="+arrResult1[j][0]+")";
    				tResult = easyExecSql(tStr, 1, 0);                    
    				if (tResult != null && tResult.length > 0) 
    				{
    					if (tRiskCode == "")
    		        		{
    		            		tRiskCode = tResult[0][0];
    		        		}
    		        		else
    		        		{
    		            		tRiskCode = tRiskCode + "," + tResult[0][0];
    		        		}
    				}
    			}//end for
    			if (tRiskCode != "")
    			{
    				if (!confirm("����" + tRiskCode + "Ԥ�ڱ���/������ʵ�ʱ���/����ֵ������ȷ��'¼�����'��"))
    				{
    		  	    		return;
    		  		}
    			}
    		}
    	/*	
    		 tStr= "	select riskcode from lcgrppol a where 1=1 "
    							+" and grpcontno = '"+fm.ProposalGrpContNo.value+"'"
    							+" and expamnt != "
    							+ "(select nvl(sum(mult),0) from lcpol "
    							+ "where grpcontno='" + fm.ProposalGrpContNo.value
    							+ "' and riskcode=a.riskcode)";
    		 strww="select nvl(sum(c.mult),0) from lcgrppol a ,lcpol c  "
        				+"where c.grpcontno = a.grpcontno and c.riskcode = a.riskcode and c.grpcontno = '"+document.all('GrpContNo').value+"'";
        	 var arrResult = easyExecSql(strww);
        	 //alert(arrResult[0][0]);
        	 if(arrResult[0][0]=="0")
        	 {
        	 	tStr= "	select riskcode from lcgrppol a where 1=1 "
    							+" and grpcontno = '"+fm.ProposalGrpContNo.value+"'"
    							+" and expamnt != "
    							+ "(select nvl(sum(Amnt),0) from lcpol "
    							+ "where grpcontno='" + fm.ProposalGrpContNo.value
    							+ "' and riskcode=a.riskcode)";
        	}
    		  	
    		tResult = easyExecSql(tStr, 1, 0);                    
    		if (tResult != null && tResult.length > 0) {
    		    var tRiskCode = "";
    		    for (i = 0; i < tResult.length ; i++ ){
    		        if (i == 0)
    		        {
    		            tRiskCode = tResult[i][0];
    		        }
    		        else
    		        {
    		            tRiskCode = tRiskCode + "," + tResult[i][0];
    		        }
    		    }
    		  	if (!confirm("����" + tRiskCode + "Ԥ�ڱ���/������ʵ�ʱ���/����ֵ������ȷ��'¼�����'��")){
    		  	    return;
    		  	}
    		}	*/	
    	}        
	
					var sqlid31="ContPolinputSql31";
			        var mySql31=new SqlClass();
				    mySql31.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
				    mySql31.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
					var tStr=mySql31.getString();
	
	
//		var tStr= "	select * from lwmission where 1=1 "
//							+" and lwmission.processid = '0000000004'"
//							+" and lwmission.activityid = '0000002001'"
//							+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
		turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
		  if (turnPage.strQueryResult) {
		  	alert("���ŵ���ͬ�Ѿ��������棡");
		  	return;
		  	}
		if(document.all('ProposalGrpContNo').value == "") 
	   {
	   	  alert("�ŵ���ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
	   	  return;
	   }
	   
	   fm.WorkFlowFlag.value=ActivityID;//modify by haopan
//	   if(ScanFlag=="0")
//	   {
//	   		fm.WorkFlowFlag.value = "0000002098";
//	   }
//	   if(ScanFlag=="1")
//	   {
//	   	 fm.WorkFlowFlag.value = "0000002099";
//	   }
  }
  else if (wFlag ==2)//�������ȷ��
  {
  	if(document.all('ProposalGrpContNo').value == "") 
	   {
	   	  alert("δ��ѯ���ŵ���ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000002002";					//�������
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	  else if (wFlag ==3)
  {
  	if(document.all('ProposalGrpContNo').value == "") 
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001002";					//�����޸����
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		 if(document.all('ProposalGrpContNo').value == "") 
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001021";					//�����޸�
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;		
	}
	else
		return;
	
  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./GrpInputConfirm.jsp";
  	document.getElementById("fm").submit(); //�ύ
  /**/
}
  
  
 function showNotePad()
{

//	var selno = SelfGrpGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��һ������");
//	      return;
//	}
	
//var MissionID = SelfGrpGrid.getRowColData(selno, 4);
  var MissionID = document.all.MissionID.value;
//  alert(MissionID);
  
//var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
  var SubMissionID = document.all.SubMissionID.value;
//  alert(SubMissionID);
  
//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	var ActivityID = document.all.ActivityID.value;
//	alert("ActivityID="+ActivityID);
	
//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = document.all('prtNo').value;
//  alert("PrtNo="+ document.all('prtNo').value);
  
	var NoType = 2;
//	alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
} 
  
  

                  
/*********************************************************************
 *  ���뺺���ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showCodeName()
{
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('comcode','AgentManageCom','AppntManageComName');
	showOneCodeName('GrpNature','GrpNature','GrpNatureName');
	showOneCodeName('BusinessType','BusinessType','BusinessTypeName');	
	showOneCodeName('PayMode','GetFlag','GetFlagName');
	showOneCodeName('bank','BankCode','BankCodeName');
	showOneCodeName('RiskGrp','RiskCode','RiskCodeName');
	showOneCodeName('RiskPayIntv','PayIntv','PayIntvName');
	showOneCodeName('AgentType','AgentType','AgentTypeName');
	showOneCodeName('vipvalue','AppntVIPValue','AppntVIPFlagname');
	showOneCodeName('AgentCom','MediAgentCom','MediAgentComName');
	//alert("codename1") 
}

/*********************************************************************
 *  ���뺺���ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showComCodeName()
{
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('comcode','AgentManageCom','AppntManageComName');
	//alert("codename1") 
}

/*********************************************************************
 *  ��ʼ��������MissionID
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initMissionID()
{
	
	if(MissionID == "null" || SubMissionID == "null"||ActivityID=="null")
	{
		MissionID = mSwitch.getVar('MissionID');
		SubMissionID = mSwitch.getVar('SubMissionID');
		ActivityID = mSwitch.getVar('ActivityID');
	}
	else
	{
		mSwitch.deleteVar("MissionID");
		mSwitch.deleteVar("SubMissionID");
		mSwitch.deleteVar("ActivityID");
	  mSwitch.addVar("MissionID", "", MissionID);
	  mSwitch.addVar("SubMissionID", "", SubMissionID);
	  mSwitch.addVar("ActivityID", "", ActivityID);
	  mSwitch.updateVar("MissionID", "", MissionID);
	  mSwitch.updateVar("SubMissionID", "", SubMissionID);
	  mSwitch.updateVar("ActivityID", "", ActivityID);
	  
	}

}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
						var sqlid32="ContPolinputSql32";
			        var mySql32=new SqlClass();
				    mySql32.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
				    mySql32.addSubPara(fm.GrpNo.value);//ָ������Ĳ���
					strsql=mySql32.getString();
	
	
    //strsql = "select AddressNo,GrpAddress from LCGrpAddress where CustomerNo ='"+fm.GrpNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    document.all("GrpAddressNo").CodeData=tCodeData;
}

function grpPersonAge()
{
	//showInfo = window.open("./GrpPersonAgeInput.jsp?GrpContNo="+fm.GrpContNo.value+","",sFeatures);
	var tProposalGrpContNo = "";
	tProposalGrpContNo = document.all( 'ProposalGrpContNo' ).value;
	if( tProposalGrpContNo == null || tProposalGrpContNo == "" )
	{
		alert( "���뱣���ͬ��Ϣ���ܽ��롲������Ϣ����" );
		return false
	}
	delGrpVar();
	addGrpVar();
	
	var cGrpContNo = document.all("GrpContNo").value;
	var	cPrtNo = document.all("PrtNo").value;
	showInfo = window.open("GrpPersonAgeMain.jsp?GrpContNo=" + cGrpContNo + "&PrtNo=" + cPrtNo,"",sFeatures);
}

//��LJTempFee ���еõ�������Ч����
function QueryCValiDate()
{   
	prtNo=document.all('PrtNo').value;
	 //alert("prtNo============="+prtNo);  
	//������Ч��Ϊenteraccdate�ֶ�ֵ�ĵڶ���,����������SQL��ʹ��"+"�Ż�����⣬���� - (-1)���档
	
							var sqlid95="ContPolinputSql95";
			        var mySql95=new SqlClass();
				    mySql95.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql95.setSqlId(sqlid95);//ָ��ʹ�õ�Sql��id
				    mySql95.addSubPara(prtNo);//ָ������Ĳ���
					var strsql95=mySql95.getString();
					
	var brrResult = easyExecSql(strsql95);
	//var brrResult = easyExecSql("select to_date(max(enteraccdate)) - (-1) from ljtempfee where othernotype='4' and otherno='"+prtNo+"'");
	var CValiDate =""; 
	if(brrResult[0][0]=="")
	  { 
	  	var showStr="�޷����ݲ����շ������Զ����ɱ�����Ч���ڣ���ȷ���Ƿ��ѽ��ѻ����ֹ�¼��!";
			var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();			
		  //alert("�޷����ݲ����շ������Զ����ɱ�����Ч���ڣ���ȷ���Ƿ��ѽ��ѻ����ֹ�¼��!"); 
		  return;
		}
  else
    {   
      if(document.all('CValiDate').value==null||document.all('CValiDate').value=="")
  	  try {document.all('CValiDate').value = brrResult[0][0];} catch(ex) { alert("������Ч����������!");}; 
  	} 
  	
}

function QueryChargeDate()
{
    prtNo=document.all('PrtNo').value;
	
					var sqlid33="ContPolinputSql33";
			        var mySql33=new SqlClass();
				    mySql33.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
				    mySql33.addSubPara(prtNo);//ָ������Ĳ���
					var strsql33=mySql33.getString();
	
	var arrResult = easyExecSql(strsql33);
    //var arrResult = easyExecSql("select to_date(max(enteraccdate)) from ljtempfee where othernotype='4' and otherno='"+prtNo+"'");
    var ChargeDate =""; 
    if(arrResult[0][0]=="")
    { 
//        if(document.all('CValiDate').value==null||document.all('CValiDate').value=="")
//        {
//            var brrResult = easyExecSql("select to_date('" + PolApplyDate + "','yyyy-mm-dd') + 1 from dual");
//            document.all('CValiDate').value = brrResult[0];
//        }
        var showStr="�����շ����ڲ����ڣ���ȷ���Ƿ��ѽ���!";
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //alert("�����շ����ڲ����ڣ���ȷ���Ƿ��ѽ���!"); 
        return;
    }
    else
    { 
        try {document.all('PayDate').value = arrResult[0][0];} catch(ex) { alert("����������������!");}; 
//        var brrsResult = easyExecSql("select adddate(max(enteraccdate) , 1) from ljtempfee where othernotype='4' and otherno='"+prtNo+"'");
        
    	var sqlid96="ContPolinputSql96";
    	var mySql96=new SqlClass();
    	mySql96.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql96.setSqlId(sqlid96);//ָ��ʹ�õ�Sql��id
    	mySql96.addSubPara(prtNo);//ָ������Ĳ���
    	var strSql96=mySql96.getString();
    	var brrResult = easyExecSql(strSql96);
        
        if(document.all('CValiDate').value==null||document.all('CValiDate').value=="")
        {
            try {document.all('CValiDate').value = brrResult[0][0];} catch(ex) { alert("������Ч����������!");}; 
        }
    } 
}

function initDetail()
{ 

					var sqlid34="ContPolinputSql34";
			        var mySql34=new SqlClass();
				    mySql34.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
				    mySql34.addSubPara(prtNo);//ָ������Ĳ���
					var strsql34=mySql34.getString();
					
   var arrResult = easyExecSql(strsql34, 1, 0);
  //var arrResult = easyExecSql("select * from LCGrpCont where PrtNo = '" + prtNo + "'", 1, 0);
  if(arrResult==null)
  { 
   return;
  }else
  {
  try {document.all('GrpContNo').value= arrResult[0][0];} catch(ex) {alert(0); }; 
  try {document.all('ProposalGrpContNo').value= arrResult[0][1];} catch(ex) { alert(1);}; 
  try {document.all('PrtNo').value= arrResult[0][2];} catch(ex) { alert(2);}; 
  try {document.all('SaleChnl').value= arrResult[0][3];} catch(ex) { alert(3);}; 
  try {document.all('ManageCom').value= arrResult[0][4];} catch(ex) { alert(4);}; 
  try {document.all('AgentCom').value= arrResult[0][5];} catch(ex) {alert(5); }; 
  try {document.all('AgentType').value= arrResult[0][6];} catch(ex) {alert(6); }; 
  try {document.all('AgentCode').value= arrResult[0][7];} catch(ex) {alert(7); }; 
  try {document.all('AgentGroup').value= arrResult[0][8];} catch(ex) { alert(8);}; 
  try {document.all('AgentCode1').value= arrResult[0][9];} catch(ex) { alert(9);}; 
  try {document.all('GrpNo').value= arrResult[0][12];} catch(ex) { alert(12);}; //changed by tuqiang
  try {document.all('GrpAddressNo').value= arrResult[0][13];} catch(ex) {alert(13); }; 
  try {document.all('GrpName').value= arrResult[0][15];} catch(ex) {alert(15); }; 
  try {document.all('BusinessType').value= arrResult[0][16];} catch(ex) {alert(16); }; 
  try {document.all('GrpNature').value= arrResult[0][17];} catch(ex) { alert(17);}; 
  try {document.all('RgtMoney').value= arrResult[0][18];} catch(ex) { alert(18);}; 
  try {document.all('Asset').value= arrResult[0][19];} catch(ex) { alert(19);}; 
  try {document.all('NetProfitRate').value= arrResult[0][20];} catch(ex) {alert(20); }; 
  try {document.all('MainBussiness').value= arrResult[0][21];} catch(ex) {alert(21); }; 
  try {document.all('Corporation').value= arrResult[0][22];} catch(ex) {alert(22); }; 
  try {document.all('ComAera').value= arrResult[0][23];} catch(ex) { alert(23);}; 
  try {document.all('Fax').value= arrResult[0][24];} catch(ex) {alert(24); }; 
  try {document.all('Phone').value= arrResult[0][25];} catch(ex) { alert(25);}; 
  try {document.all('GetFlag').value= arrResult[0][26];} catch(ex) { alert(26);}; 
  try {document.all('FoundDate').value= arrResult[0][29];} catch(ex) {alert(29); }; 
  try {document.all('BankCode').value= arrResult[0][31];} catch(ex) {alert(31); }; 
  try {document.all('BankAccNo').value= arrResult[0][32];} catch(ex) {alert(32); }; 
  try {document.all('OutPayFlag').value= arrResult[0][35];} catch(ex) {alert(35); }; 
  try {document.all('Currency').value= arrResult[0][38];} catch(ex) {alert(38); };
  try {document.all('CValiDate').value= arrResult[0][51];} catch(ex) { alert(51);}; 
  try {document.all('ExpPeoples').value= arrResult[0][54];} catch(ex) {alert(2); }; 
  try {document.all('Remark').value= arrResult[0][64];} catch(ex) {alert(3); }; 
  }
}
function haveMultiAgent(){
	//alert("aa����"+document.all("multiagentflag").checked);
  if(document.all("multiagentflag").checked){
  	DivMultiAgent.style.display="";	
  }
  else{
    DivMultiAgent.style.display="none";	
  }
	
}                                                                             
//Muline ����Ӷ����� parm1
function queryAgentGrid(Field)
{	
//	alert("Field=="+Field.name);
//	alert(Field.value);
	tField=Field;
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(document.all(Field).all('MultiAgentGrid1').value==""){
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}	

	if(document.all( Field ).all('MultiAgentGrid1').value != "")	 {
	var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value;  //��������
	if(cAgentCode.length!=8){
    	return;
    }
	
						var sqlid35="ContPolinputSql35";
			        var mySql35=new SqlClass();
				    mySql35.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
				    mySql35.addSubPara(cAgentCode);//ָ������Ĳ���
					var strSQL=mySql35.getString();
	
//    var strSQL = "select a.*,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
    var arrResult = easyExecSql(strSQL);
       //alert(arrResult);
    if (arrResult == null) {
        alert("����Ϊ:["+document.all( Field ).all('MultiAgentGrid1').value+"]�Ĵ����˲����ڣ���ȷ��!");
        document.all( Field ).all('MultiAgentGrid2').value = "";
        document.all( Field ).all('MultiAgentGrid3').value = "";
        document.all( Field ).all('MultiAgentGrid4').value = "";
        document.all( Field ).all('MultiAgentGrid5').value = "";
        document.all( Field ).all('MultiAgentGrid6').value = "";
    }
    else
    {
        document.all( Field ).all('MultiAgentGrid2').value = arrResult[0][5];
        document.all( Field ).all('MultiAgentGrid3').value = arrResult[0][2];
        document.all( Field ).all('MultiAgentGrid4').value = arrResult[0][92];
        document.all( Field ).all('MultiAgentGrid6').value = arrResult[0][1];
    }
    
  } 
}


//Muline ����Ӷ����� parm1
function queryAgent1Grid(Field)
{	
//	alert("Field=="+Field.name);
//	alert(Field.value);
    var tRowNo = MultiAgentGrid.lastFocusRowNo;
    if (tRowNo < 0)
        return;
	tField=Field;
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(Field.value==""){
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}	

	if(Field.value != "")	 {
	var cAgentCode = Field.value;  //��������
	if(cAgentCode.length!=8){
    	return;
    }
	
					var sqlid36="ContPolinputSql36";
			        var mySql36=new SqlClass();
				    mySql36.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
				    mySql36.addSubPara(cAgentCode);//ָ������Ĳ���
					var strSQL=mySql36.getString();
	
//    var strSQL = "select a.*,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
    var arrResult = easyExecSql(strSQL);
       //alert(arrResult);
    if (arrResult == null) {
        alert("����Ϊ:["+cAgentCode+"]�Ĵ����˲����ڣ���ȷ��!");
        MultiAgentGrid.setRowColData(tRowNo,2,"");
        MultiAgentGrid.setRowColData(tRowNo,3,"");
        MultiAgentGrid.setRowColData(tRowNo,4,"");
        MultiAgentGrid.setRowColData(tRowNo,6,"");
    }
    else
    {
         MultiAgentGrid.setRowColData(tRowNo,2,arrResult[0][5]);
         MultiAgentGrid.setRowColData(tRowNo,3,arrResult[0][2]);
         MultiAgentGrid.setRowColData(tRowNo,4,arrResult[0][92]);
         MultiAgentGrid.setRowColData(tRowNo,6,arrResult[0][1]);
    }
    
  } 
}

//��ʼ����ҵ��Ա��Ϣ
function displayMainAgent(){
  document.all("BranchAttr").value = arrResult[0][3];
  document.all("AgentName").value = arrResult[0][1];
}                                                                             

function displayMultiAgent(){
   document.all('multiagentflag').checked="true";
   haveMultiAgent();	
}    
//У��Ͷ������
function checkapplydate(){
  if(fm.PolApplyDate.value.length==8){
      if(fm.PolApplyDate.value.indexOf('-')==-1){ 
      	var Year =     fm.PolApplyDate.value.substring(0,4);
      	var Month =    fm.PolApplyDate.value.substring(4,6);
      	var Day =      fm.PolApplyDate.value.substring(6,8);
      	fm.PolApplyDate.value = Year+"-"+Month+"-"+Day;
      	if(Year=="0000"||Month=="00"||Day=="00"){
         	 alert("�������Ͷ����������!");
       	   fm.PolApplyDate.value = ""; 
       	   return;
      	  }
      }
    
  
    } 

    else {var Year =     fm.PolApplyDate.value.substring(0,4);
	    var Month =    fm.PolApplyDate.value.substring(5,7);
	    var Day =      fm.PolApplyDate.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ����������!");
   	   fm.PolApplyDate.value = "";
   	   return;
  	     }
  }
  
    if(document.all('CValiDate').value==null||document.all('CValiDate').value=="")
    {
		
					var sqlid37="ContPolinputSql37";
			        var mySql37=new SqlClass();
				    mySql37.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
				    mySql37.addSubPara( fm.PolApplyDate.value);//ָ������Ĳ���
					var strSQL37=mySql37.getString();
					
		var brrResult = easyExecSql(strSQL36);
        //var brrResult = easyExecSql("select to_date('" + fm.PolApplyDate.value + "','yyyy-mm-dd') + 1 from dual");
        document.all('CValiDate').value = brrResult[0];
    }

}

function checkCValidate(){
  if(fm.CValiDate.value.length==8){
  if(fm.CValiDate.value.indexOf('-')==-1){ 
  	var Year =     fm.CValiDate.value.substring(0,4);
  	var Month =    fm.CValiDate.value.substring(4,6);
  	var Day =      fm.CValiDate.value.substring(6,8);
  	fm.CValiDate.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ����������!");
   	   fm.CValiDate.value = ""; 
   	   return;
  	  }
  }
} 

else {var Year =     fm.CValiDate.value.substring(0,4);
	    var Month =    fm.CValiDate.value.substring(5,7);
	    var Day =      fm.CValiDate.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ����������!");
   	   fm.CValiDate.value = "";
   	   return; 
  	     }
  }

} 

//-----------------------------------Beg
//add By:haopan
//Date:2006-9-28
//��ѯ���±�
function checkNotePad(prtNo,LoadFlag){
	try
	{
		
					var sqlid38="ContPolinputSql38";
			        var mySql38=new SqlClass();
				    mySql38.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
				    mySql38.addSubPara( fm.PolApplyDate.value);//ָ������Ĳ���
					var strSQL=mySql38.getString();
		
	   // var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";	
	    var arrResult = easyExecSql(strSQL);	
	    eval("document.all('NotePadButton"+LoadFlag+"').value='���±��鿴 ����"+arrResult[0][0]+"����'");
	}
	catch(ex){};
	ctrlQuestionBtn();
}
//�ж���û�������,���ư�ť
function ctrlQuestionBtn()
{

if(LoadFlag==2||LoadFlag==4)
{
	
						var sqlid39="ContPolinputSql39";
			        var mySql39=new SqlClass();
				    mySql39.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
				    mySql39.addSubPara( fm.PolApplyDate.value);//ָ������Ĳ���
					var strSQL=mySql39.getString();
	
	//var strSQL="select * from lcgrpissuepol where grpcontno='"+polNo+"' and rownum=1";
	var arrResult = easyExecSql(strSQL);
	if(arrResult!=null)
					{
				
						eval("document.all('qryQuestionBtn"+LoadFlag+"').disabled=''");	
					}
	else
					{
							eval("document.all('qryQuestionBtn"+LoadFlag+"').disabled='true'");	
					
					}
}

}
//Ӱ�����ϲ�ѯ
function showImage(){ 
	
var polNo=fm.PrtNo.value;
	window.open("../uw/ImageQueryMainGrp.jsp?ContNo="+polNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
    
}
//---------------------------------End
                                                                         


//--------------------------Beg
//-------��Ʒ�����ӡ�ɾ������
//-------Add By:Chenrong
//-------Date:2006-11-22 17:30

function addPlanRecord(){
    var tRiskCode = document.all('PlanRiskCode').value ;
    var tGrpContNo = document.all('GrpContNo').value ;
//    var tMult = document.all('Mult').value ;
    
    if(tRiskCode==null ||tRiskCode=="")
    {	  
        alert("��ѡ���Ʒ��ϱ��룡");
        return ;
    }
    
//    if(tMult != null && tMult != "" && tMult != "null")
//    {
//
//        if (isInteger(tMult) == false){
//            alert("��Ʒ��Ϸ���Ϊ�����ͣ�");
//            return ;
//        }
//        if (parseInt(tMult) <= 0)
//        {
//            alert("��Ʒ��Ϸ���Ӧ����0��");
//            return ;
//        }
//    }
    
    document.all('fmAction').value="INSERT||PLANRISK";
    
    var showStr="��������ŵ�������Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    fm.action="../app/GrpPlanRiskSave.jsp"
    document.getElementById("fm").submit();
}

function deletePlanRecord(){
    var tSel = PlanGrid.getSelNo();
    if (tSel <= 0){
        alert("��ѡ��һ��Ʒ�����Ϣ!");
        return;
    }
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    document.all('fmAction').value = "DELETE||PLANRISK";
    fm.action="../app/GrpPlanRiskSave.jsp"
    document.getElementById("fm").submit();
}

/*********************************************************************
 *  ��ʼ��������ʾ������ �����ͱ��ѵ�ͳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function fillPlanGrid(){
    if(fm.ProposalGrpContNo.value!="")
    {
		
								var sqlid40="ContPolinputSql40";
			        var mySql40=new SqlClass();
				    mySql40.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
				    mySql40.addSubPara( fm.GrpContNo.value);//ָ������Ĳ���
				    mySql40.addSubPara( fm.GrpContNo.value);//ָ������Ĳ���
				    mySql40.addSubPara( fm.GrpContNo.value);//ָ������Ĳ���
				    mySql40.addSubPara( fm.GrpContNo.value);//ָ������Ĳ���
				    mySql40.addSubPara( fm.GrpContNo.value);//ָ������Ĳ���
					var strSql=mySql40.getString();
		
//        var strSql; 
//        //������SQL������hint��/*+RULE*/�����Ч��                      
//        strSql=" select /*+RULE*/ a.contplancode, a.contplanname,'0',a.peoples2,"
//        +"(select nvl(sum(i.insuredpeoples),0) from lcinsured i "
//        +"where i.grpcontno = '"
//        +document.all('GrpContNo').value + "' and i.contplancode=a.contplancode)" 
//        +" as peoples,"
//        +"'',"
//        +"(select nvl(sum(prem),0) from lcprem where grpcontno='" + document.all('GrpContNo').value
//        +"' and contno in (select contno from lcinsured where grpcontno='" 
//        + document.all('GrpContNo').value +"' and contplancode=a.contplancode)),"
//        +"round((select nvl(sum(i.insuredpeoples),0) from lcinsured i "
//        +"where i.grpcontno = '"
//        +document.all('GrpContNo').value + "' and i.contplancode=a.contplancode)/decode(a.peoples2,0,1,a.peoples2),2) "
//        +"from lccontplan a Where a.plantype='6' and a.grpcontno = '"
//        +document.all('GrpContNo').value+"' order by a.contplancode" ;
       
        turnPage1.queryModal(strSql, PlanGrid);
        if (PlanGrid.mulLineCount <= 0)
        {
            divGrpPlanRisk.style.display="none";
            divGrpNormalRisk.style.display="";
            document.all("PlanFlag").checked = false;
            fillriskgrid();		
        }
        else{
            divGrpNormalRisk.style.display="none";	
            divGrpPlanRisk.style.display="";	
            document.all("PlanFlag").checked = true;		
//            fm.Mult.value = "1";	
        }
    }
    else
    { 
        return false;  
    }
  
} 

function inputPlan(){
    if(document.all("PlanFlag").checked){
        if (RiskGrid.mulLineCount > 0)
        {
            alert("�Ѿ�¼�볣�գ�������¼���Ʒ�����Ϣ��");
            document.all("PlanFlag").checked = false;
            return;
        }
        divGrpNormalRisk.style.display="none";	
        divGrpPlanRisk.style.display="";
    }
    else{
        if (PlanGrid.mulLineCount > 0)
        {
            alert("�Ѿ�¼���Ʒ�����Ϣ�������ٽ�������������");
            document.all("PlanFlag").checked = true;
            return;
        }
        divGrpPlanRisk.style.display="none";
        divGrpNormalRisk.style.display="";		
    }    
}

function getPlanRiskDetail(parm1,parm2){
    var tContPlanCode=document.all(parm1).all('PlanGrid1').value;
	
									var sqlid41="ContPolinputSql41";
			        var mySql41=new SqlClass();
				    mySql41.setResourceName("app.ContPolinputSql"); //ָ��ʹ�õ�properties�ļ���
				    mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
				    mySql41.addSubPara( fm.PrtNo.value);//ָ������Ĳ���
				    mySql41.addSubPara( fm.PrtNo.value);//ָ������Ĳ���
				    mySql41.addSubPara( tContPlanCode);//ָ������Ĳ���
				    mySql41.addSubPara( fm.PrtNo.value);//ָ������Ĳ���
				    mySql41.addSubPara( tContPlanCode);//ָ������Ĳ���
				    
					mySql41.addSubPara( tContPlanCode);//ָ������Ĳ���
				    mySql41.addSubPara( fm.PrtNo.valuel);//ָ������Ĳ���
				    mySql41.addSubPara( fm.PrtNo.value);//ָ������Ĳ���
				    mySql41.addSubPara( tContPlanCode);//ָ������Ĳ���
					var tSQL=mySql41.getString();
	
//    var tSQL = "select distinct a.riskcode,'',b.dutycode,e.dutyname," 
//             + "(select nvl(sum(prem),0) from lcprem "
//             + "where grpcontno='" + document.all('PrtNo').value 
//             + "' and dutycode=b.dutycode and contno in "
//             + "(select contno from lcinsured where grpcontno='" 
//             + document.all('PrtNo').value +"' and contplancode='" 
//             + tContPlanCode +"')),"
//             + "(select nvl(sum(amnt),0) from lcduty "
//             + "where dutycode=b.dutycode and contno in "
//             + "(select contno from lcinsured where grpcontno='" 
//             + document.all('PrtNo').value +"' and contplancode='" 
//             + tContPlanCode +"'))"
//             + " from lccontplanrisk a,lccontplandutyparam b,"
//             + "lmriskduty d,lmduty e"
//             + " where d.riskcode=a.riskcode"
//             + " and b.dutycode=d.dutycode and e.dutycode=b.dutycode"
//             + " and a.contplancode='" + tContPlanCode 
//             + "' and a.plantype='6' and a.proposalgrpcontno='" 
//             + fm.PrtNo.value + "' and b.grpcontno='" + fm.PrtNo.value 
//             + "' and b.plantype='6' and b.contplancode='" + tContPlanCode + "'";
    turnPage2.queryModal(tSQL, PlanRiskGrid);
}
//--------------------------End

function clearMediAgent()
{
    fm.multiagentflag.checked = false;
    DivMultiAgent.style.display="none";	
    MultiAgentGrid.clearData("MultiAgentGrid");
    fm.AgentCode.value = "";
    fm.BranchAttr.value = "";
    fm.AgentGroup.value = "";
    fm.AgentName.value = "";
    fm.AgentManageCom.value = "";
}