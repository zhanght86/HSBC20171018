//***********************************************
//�������ƣ�BQBeforeHealthQ.js
//�����ܣ����������Ϣ��ѯ
//�������ڣ�2008-12-15 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();



/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	if (FlagStr == "Fail" )
	{             
  	showInfo.close();  
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
	if (FlagStr == "Succ")
	{
  	showInfo.close();  
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
	
}



/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}



/*********************************************************************
 *  ��ѯ�ѳб�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryCont(){
	
//  var aSQL = " select distinct a.contno,(select AppntNo from lPcont c where c.proposalcontno=a.proposalcontno),a.appname,a.managecom,a.agentcode,a.agentname from LPPENotice a where 1=1"
//           + " and a.CustomerNo = '"+tCustomerNo+"'" 
//          ; 
  
     var sqlid1="BQBeforeHealthQSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.BQBeforeHealthQSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(tCustomerNo);//ָ���������
	 var aSQL = mySql1.getString();
           
 	turnPage.strQueryResult = easyQueryVer3(aSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޼��������Ϣ��");
    return "";
  }
  
  turnPage.queryModal(aSQL, ContGrid);

}

//��ѯ�ͻ���Ϣ
function queryPersonInfo()
{
//    var aSQL = "select CustomerNo, Name from LDPerson where CustomerNo = '" + tCustomerNo + "'";
    
     var sqlid2="BQBeforeHealthQSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.BQBeforeHealthQSql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(tCustomerNo);//ָ���������
	 var aSQL = mySql2.getString();
    
    var arrResult = easyExecSql(aSQL);
    if (arrResult != null)
    {
        document.all('CustomerNo').value = arrResult[0][0];
        document.all('CustomerName').value = arrResult[0][1];
    }
}


function healthInfoClick(){

  var tsel=ContGrid.getSelNo()-1;
  
  if (tsel<0)
  {
  	alert("��ѡ�񱣵���");
  	return;
  	}
	var tFlag="3";
  var ttContNo=ContGrid.getRowColData(tsel,1);
  if(ttContNo!=null && ttContNo != "")
  {
 	window.open("./UWManuHealthQMain.jsp?ContNo="+ttContNo+"&CustomerNo="+tCustomerNo+"&Flag="+tFlag,"window1");
  }
  	
}

//�ж��Ƿ��в���ɷ���Ϣ
function haveFeeInfo(){
	return;
}

//�ж��Ƿ���Ӱ������
function havePicData(){
	return;
}

//�ж��Ƿ��к˱�����
function haveUWResult(){
//	var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
	
	 var RowColData3 = ContGrid.getRowColData(ContGrid.getSelNo()-1,1);
	 var sqlid3="BQBeforeHealthQSql3";
	 var mySql3=new SqlClass();
	 mySql3.setResourceName("uw.BQBeforeHealthQSql");
	 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
	 mySql3.addSubPara(RowColData3);//ָ���������
	 var aSQL = mySql3.getString();
	
  //alert(aSQL);
  var arrResult = easyExecSql(aSQL);
  if(arrResult!=null){
    fm.button4.disabled="";	
    return;
  }
  else{
	fm.button4.disabled="true";	
    return;
  }
	return;
}

function getContDetailInfo(){ 
	
	 var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
   var tContNo = ContGrid.getRowColData(ContGrid.getSelNo()-1,1);
//   var strSQL = "select salechnl,cardflag from lccont where contno='"+tContNo+"'";
   
   var sqlid4="BQBeforeHealthQSql4";
   var mySql4=new SqlClass();
   mySql4.setResourceName("uw.BQBeforeHealthQSql");
   mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
   mySql4.addSubPara(tContNo);//ָ���������
   var strSQL = mySql4.getString();
   
   var arrResult = easyExecSql(strSQL);
   //alert(arrResult[0][0]);
   var tSaleChnl = arrResult[0][0];
   var tCardFlag = arrResult[0][1];
   //tSaleChnl=1;
   //alert(tSaleChnl);
   //alert(tCardFlag);
  if(tSaleChnl=="1"){
  	if(tCardFlag==null||tCardFlag!="3"){
      window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&BankFlag=1&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
    }
    else{
      window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
    }
  }
  else if(tSaleChnl=="3"){
    window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&BankFlag=3&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
  }
//	 window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
	else
	{
		alert("�ù������ڻ��޷�ʹ��!");
	}

}

function showTempFee() 
{
	 var tsel=ContGrid.getSelNo()-1;	 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   } 
   var tAppntNo=ContGrid.getRowColData(tsel,2);
   var tAppntName=ContGrid.getRowColData(tsel,3);
   var tPrtNo = ContGrid.getRowColData(tsel,1);
   if(tPrtNo!="" && tAppntNo!="")
   {
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+tPrtNo+"&AppntNo="+tAppntNo+"&AppntName="+tAppntName,"window11");
   }
} 
 
//Ӱ�����ϲ�ѯ
function showImage(){ 
	
	 var tsel=ContGrid.getSelNo()-1;
	 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
}


//�˱���ѯ
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./UWQueryMain.jsp?ContNo="+ContNo,"window1"); 
}