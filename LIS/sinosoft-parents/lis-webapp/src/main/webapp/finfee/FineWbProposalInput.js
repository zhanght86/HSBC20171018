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

window.onfocus = myonfocus;
var hiddenBankInfo = "";
 

function quaryagentgroup()
{
	//var sql="select agentgroup from laagent where agentcode='"+document.all('AgentCode').value+"'";
	var sqlid1="FineWbProposalInputSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("finfee.FineWbProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('AgentCode').value);//ָ������Ĳ���
	var sql=mySql1.getString();
	var tResult = easyExecSql(sql, 1, 1, 1); 
	if(tResult!=null)
	{
		document.all('AgentGroup').value=tResult[0][0];
	}
} 
 
function changeOtherNo()
{
    var aOtherNo=document.all('OtherNo').value;	
	var aSelNo=TempGrid.mulLineCount;
	if( aSelNo == 0 || aSelNo == null )
		return;
	else
	{
		while(aSelNo-->0)
		{
		   TempGrid.setRowColData(aSelNo,3,aOtherNo);
		 }
	}
}  

function changeEnterDate()
{
    var aPayDate=document.all('PayDate').value;	
	var aNo=TempClassGrid.mulLineCount;
	if( aNo == 0 || aNo == null )
		return;
	else
	{
		while(aNo-->0)
		{
		   TempClassGrid.setRowColData(aNo,4,aPayDate);
		 }
	}
} 
 
function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
	var sqlid2="FineWbProposalInputSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("finfee.FineWbProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(cAgentCode);//ָ������Ĳ���
	var strSql=mySql2.getString();
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentName.value = arrResult[0][1];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
}

function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
    fm.AgentCode.value = arrResult[0][0];
  }
}

function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
	var sqlid3="FineWbProposalInputSql2";
	var mySql3=new SqlClass();
	mySql3.setResourceName("finfee.FineWbProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(cAgentCode);//ָ������Ĳ���
	mySql3.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
	var strSql=mySql3.getString();
    var arrResult = easyExecSql(strSql);
      // alert(arrResult);
    if (arrResult != null) {
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
}

function checkComCode()
{
	if((document.all('TempFeeNo').readOnly == true) && (document.all('TempFeeNo').value != ""))
	{
		
		//var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('TempFeeNo').value) + "'";
		var sqlid4="FineWbProposalInputSql3";
		var mySql4=new SqlClass();
		mySql4.setResourceName("finfee.FineWbProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(trim(document.all('TempFeeNo').value));//ָ������Ĳ���
		var sql=mySql4.getString();
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


/*********************************************************************
 *  ��������շѵ����ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm() {
	document.all("save").disabled = true;
	//alert(fm.TempFeeNoHide.value);
	//var sSQL ="select 1 from ljtempfee where tempfeeno='"+fm.TempFeeNo.value+"' ";
	var sqlid5="FineWbProposalInputSql4";
	var mySql5=new SqlClass();
	mySql5.setResourceName("finfee.FineWbProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.TempFeeNo.value);//ָ������Ĳ���
	var sSQL=mySql5.getString();
	var arrCount=easyExecSql(sSQL); 
	if(fm.TempFeeNoHide.value !="" || arrCount!=null)
	{
		alert("���շ���Ϣ�Ѿ����棬�����ٴα��棡");
		return;
	}
	//alert("document.all(save).disabled"+document.all("save").disabled);
//	alert("document.all(save).disabled"+document.all("save").src);
	if(document.all("aftersave").value==1)
	{
		return ;
	}
	//���������˺��Ƿ�¼��淶
    if(!verifyBankAccNo()) return false;
	
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var passVerify = true;
    var verifyGrade = "1";
		 
	document.all('fmAction').value = "INSERT";
	//alert("mAction"+mAction);
	
	//return;
 
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockScreen('lkscreen');   
	fm.submit(); //�ύ
	document.all("aftersave").value="1";
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	//document.all("save").disabled=false;
	unlockScreen('lkscreen');
	try { showInfo.close(); } catch(e) {}
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//inputQuestButton.style.display="";
	}
	else
	{ 
 
		if(loadFlag == '3'){
		//inputQuestButton.style.display="";
		}		
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
		  top.opener.easyQueryClick();
   		  top.close();		
	} 
	mAction = ""; 
}

/*********************************************************************
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick() { 
	var tPrtNo = document.all('TempFeeNo').value;
	
	if(tPrtNo==null || tPrtNo=="") {
		alert( "��֤ӡˢ����Ϣ��ʧ�����ܽ���ɾ��!" );
	}
	else 
	{
		if(fm.DealType.value != "03")
		{
			alert("�˲����շѵ���������޷�������쳣��������ɾ����");
			return false;
		}
		
		if(!confirm("ȷ��Ҫɾ����"))
		{
			return false;
		}

		var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )	
		{
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   \var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			lockScreen('lkscreen');
			fm.submit(); //�ύ
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
	document.all("save").disabled=false;
	document.all("aftersave").value="0";
	changeImage(document.all("save"),'../common/images/butSave.gif');
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
 *  ���ݲ�ѯ���ص���Ϣ��ѯͶ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPolDetail( cPolNo )
{

	emptyForm(); 
	//var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	//parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
	parent.fraTitle.window.location = "./FineWbProposalQueryDetail.jsp?TempFeeNo=" + TempFeeNo;

}
           
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

//�����¼��
function QuestInput()
{
	if(inputQuestButton.disabled == true)
	   return;
	cProposalNo = fm.TempFeeNo.value;  //���񵥺���
	if(cProposalNo == "")
	{
		alert("����Ͷ��֤ӡˢ�ţ����ȱ���!");
	}
	else
	{	
		window.open("../uw/QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");
	}
}

//���������˺�¼���Ƿ�淶
function verifyBankAccNo()
{
	var count=TempClassGrid.mulLineCount;
	//alert(count);
	var tBankCode = '';
	var tBankAccNo = '';
	for(;count>0;count--)
	{
		tBankCode = TempClassGrid.getRowColData(count-1,5);
		tBankAccNo = TempClassGrid.getRowColData(count-1,6);
		//alert(tBankCode);
		//alert(tBankAccNo);
		if(tBankCode!=null && tBankCode!='' 
			&& tBankAccNo!=null && tBankAccNo!='')
			{
				if(!checkBankAccNo(tBankCode,tBankAccNo))
			 		return false;
			}
	}
	
	return true;
}
