//***********************************************
//�������ƣ�AppntChk.js
//�����ܣ�Ͷ���˲���
//�������ڣ�2002-11-23 17:06:57
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var cflag = "4";

/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit(FlagStr,content)
{
  //alert(FlagStr);
	if (FlagStr == "Fail" )
	{             
	  showInfo.close();  
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
	if (FlagStr == "Succ")
	{
	  showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    fm.button5.disabled='';
		if (fm.SureFlag.value == "Union")
		{
			initForm();	
		}

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


// ��ѯ��ť

function initQuery() 
{
	//fm.action = "./AppntChkQuery.jsp";
	//fm.submit();	
	//��ѯ�������
	//var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
  //                + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;
  //arrResult = easyExecSql(sqlstr,1,0);
  //alert(grpPolNo);
  //alert(contNo);
  //alert(operator);
  //alert(tFlag);
  //alert(tInsuredNo);
  //alert(tInsuredNo);
//  var sql="select customerno,name,idtype,idno,othidtype,othidno,sex,birthday From ldperson where " 
//  			+ "customerno='"+tInsuredNo+"'"; 
  
	var sqlid1="AppntChkSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.AppntChkSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tInsuredNo);//ָ������Ĳ���
	var sql=mySql1.getString();
  
  arrResult = easyExecSql(sql,1,0);
  if (arrResult == null) {  
  	alert("û�в�ѯ����Ӧ�Ŀͻ���Ϣ"); 
  	return;
  }else{
  //OPolGrid.addOne("OPolGrid");                                                  
  OPolGrid.setRowColData(0, 1, arrResult[0][0]); 
  OPolGrid.setRowColData(0, 2, arrResult[0][1]);       
  OPolGrid.setRowColData(0, 3, arrResult[0][2]);
  OPolGrid.setRowColData(0, 4, arrResult[0][3]);
  OPolGrid.setRowColData(0, 5, arrResult[0][4]);
  OPolGrid.setRowColData(0, 6, arrResult[0][5]);            
}
//  var sqlstr="select * from ldperson where "
//	  		+ "Name='"+arrResult[0][1]+"' " 
//	  		+"and Sex='"+arrResult[0][6]+"' "
//	  		+"and Birthday='"+arrResult[0][7]+"' " 
//	  		+"and CustomerNo<>'"+arrResult[0][0]+"'"
//            + " union select * from ldperson where " 
//            +"IDType = '"+arrResult[0][2]+"' " 
//            +"and IDType is not null "
//            +"and IDNo = '"+arrResult[0][3]+"' " 
//            +"and IDNo is not null" 
//            +" and CustomerNo<>'"+arrResult[0][0]+"'" ;
  
	var sqlid1="AppntChkSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.AppntChkSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(arrResult[0][1]);//ָ������Ĳ���
	mySql1.addSubPara(arrResult[0][6]);//ָ������Ĳ���
	mySql1.addSubPara(arrResult[0][7]);//ָ������Ĳ���
	mySql1.addSubPara(arrResult[0][0]);//ָ������Ĳ���
	mySql1.addSubPara(arrResult[0][2]);//ָ������Ĳ���
	mySql1.addSubPara(arrResult[0][3]);//ָ������Ĳ���
	mySql1.addSubPara(arrResult[0][0]);//ָ������Ĳ���
	var sqlstr=mySql1.getString();
  
  arrResult = easyExecSql(sqlstr,1,0);
  //alert(arrResult.length);���ؼ��м�¼
  var i=arrResult.length;
  for(j=0;j<i;j++)
  {
  PolGrid.addOne("PolGrid");  
  PolGrid.setRowColData(j, 1, arrResult[j][0]); 
  PolGrid.setRowColData(j, 2, arrResult[j][1]);       
  PolGrid.setRowColData(j, 3, arrResult[j][4]);
  PolGrid.setRowColData(j, 4, arrResult[j][5]);
  PolGrid.setRowColData(j, 5, arrResult[j][17]);
  PolGrid.setRowColData(j, 6, arrResult[j][18]);   
     
  }
}

function sendCustomerUnionIssue()
{
  
	divCustomerUnionIssue.style.display='';
	fm.button4.disabled='';	
}
function sendCustomerUnionIssue1()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
		alert( "����ѡ����ͬ��Ͷ����" );
		return;
	}	
//�����ж��Ƿ��Ѿ���ӹ������--�ŵ�������Բ��жϣ�Ȼ�󴴽�һ���������ڵ�
//var sqlstr="select * from lcgrpissuepol where grpcontno='"+contNo+"' and issuetype='99'" ;
// arrResult = easyExecSql(sqlstr,1,0);
// if(arrResult!=null)
// {
// 	alert("���Ѿ������������");
// 	return;
//}
  
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
	
	//fm.action =  "./AppntChkUnionQuestInputSave.jsp";
	//alert(contNo);
	//alert(fm.CUIContent.value);
	//alert(fm.CustomerNo_OLD.value);
	//alert(fm.CustomerName.value);
	//alert(fm.CustomerNo_NEW.value);
	fm.ProposalNo.value=contNo;
	fm.submit();
}
function sendCustomerUnionIssue2()
{
	fm.action =  "./AppntChkUnionQuestInputSave2.jsp";
	fm.ProposalNo.value=contNo;
	fm.submit();
}
function customerUnion()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
		alert( "����ѡ����ͬ��Ͷ����" );
		return;
	}	

//	divCustomerUnion.style.display="";
	
	var CustomerNo_OLD = OPolGrid.getRowColData(0, 1);
	var CustomerNo_NEW = PolGrid.getRowColData(selno, 1);
	var CustomerName = PolGrid.getRowColData(selno, 2);
	
	fm.CustomerNo_OLD.value = CustomerNo_OLD;
	fm.CustomerNo_NEW.value = CustomerNo_NEW;
	fm.CustomerName.value = CustomerName;
}

function customerUnionSubmit()
{

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
	
	fm.SureFlag.value = "Union";
	
	fm.action =  "./AppntChkCustomerUnionSave.jsp";
	fm.submit();	
}

function showCustomerUnionIssueSend()
{ 

//	strSql =" select cont from lis.ldcodemod where codetype = 'Question' and code = '99' ";
	
	var sqlid1="AppntChkSql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.AppntChkSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara("1");//ָ������Ĳ���
	var sqlstr=mySql1.getString();
	  
	var brr = easyExecSql(strSql );
	if ( brr )
	{
		brr[0][0]==null||brr[0][0]=='null'?'0':fm.CUIContent.value  = brr[0][0];		
	}	
	
	divCustomerUnionIssue.style.display = "";
	divBtCustomerUnion.style.display = "none";
	divBtCustomerUnionSendIssue.style.display = "";

}

function showCustomerUnion()
{
	divCustomerUnionIssue.style.display = "none";
	divBtCustomerUnion.style.display = "";
	divBtCustomerUnionSendIssue.style.display = "none";	
}
function exchangeCustomerNo()
{
	var exchangeValue="";
	for(i = 0; i < fm.exchangeRadio.length; i++)
	{   
		if(fm.exchangeRadio[i].checked)
		{ 
			exchangeValue = fm.exchangeRadio[i].value;
			break;                         
		}
	}		

	if(exchangeValue == "1")
	{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}	
			
		var CustomerNo_OLD = OPolGrid.getRowColData(0, 1);
		var CustomerNo_NEW = PolGrid.getRowColData(selno, 1);
		var CustomerName = PolGrid.getRowColData(selno, 2);
		
		fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_NEW;
		
	}
	if(exchangeValue == "-1")
	{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}	
			
		var CustomerNo_NEW = OPolGrid.getRowColData(0, 1);
		var CustomerNo_OLD = PolGrid.getRowColData(selno, 1);
		var CustomerName = PolGrid.getRowColData(selno, 2);
		
		fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_NEW;
			
	}
}

function sendNotice()
{
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
		
	
	fm.action =  "./AppntNoticeSave.jsp";
	fm.submit();	
	
}