//***********************************************
//�������ƣ�AmntAccumulate.js
//�����ܣ������ۼ�
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
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
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]

	}
	if (FlagStr == "Succ")
	{
  	showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]

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
  var aSQL="select grpcontno,appntno,appntname,cvalidate,prtno From lccont where insuredno='"+customerNo+"' and appflag='1'";	
  //alert(aSQL);
  turnPage.queryModal(aSQL, ContGrid);

}



function queryPersonInfo(){
  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  var arrResult = easyExecSql(aSQL);
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}

function getPolInfo(){
	//alert("ContGrid.getRowColData(0,1)=="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1));
//	var aSQL = "select a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,c.PassFlag,d.SuppRiskScore "
//	+"from lcpol a,lmrisk b,LCUWMaster c,lcprem d "
//	+"where a.contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"' and b.riskcode=a.riskcode and c.polno=a.polno and d.polno=a.polno ";

//  var aSQL = "select distinct a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,"
//       +" (case when exists(select endDate from LmriskApp where RiskCode=b.riskcode)"
//				  +" then '����' else 'ͣ��'"
//				  +" end),"
//       +"a.amnt,a.mult,"
//       +" (select distinct PassFlag from LCUWMaster where polno=a.polno and PassFlag is not null and rownum=1) as newPassFlag,"
//       +" d.SuppRiskScore "
//	     +" from lcpol a,lmrisk b,lcprem d "
//	     +" where a.contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"' and b.riskcode=a.riskcode and d.polno=a.polno ";
  var prtno=ContGrid.getRowColData(ContGrid.getSelNo()-1,5);
  //alert(prtno);
  var aSQL="select grpcontno,polno,cvalidate,riskcode,amnt,prem,uwflag from lcpol where prtno='"+prtno+"' and insuredno='"+customerNo+"'" ;
//  initPolGrid();
//  alert(aSQL);
  turnPage2.queryModal(aSQL, PolGrid);
	
}


function contInfoClick(){
  getPolInfo();
  //fm.button1.disabled="";
  //�ж��Ƿ��в���ɷ���Ϣ

	  //�ж��Ƿ��к˱�����
	  //haveUWResult();
  	
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
	var ContNo=ContGrid.getRowColData(ContGrid.getSelNo()-1,1);
   	var strSQL="select prtno from LCCont where contno='"+ContNo+"'";
   	var arrResult = easyExecSql(strSQL);
   	var proposalcontno=arrResult[0][0];
	var aSQL = "select * from LCCUWMaster where proposalcontno='"+proposalcontno+"'";
  
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
   
   if(ContGrid.getRowColData(ContGrid.getSelNo()-1,1)==''){
   	alert("û���ѳʱ����������ܽ��в�ѯ");
   	return;
   }
   
   var tSql = "select salechnl from lccont where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
   var BankFlag=""
   var brrResult = easyExecSql(tSql);
   if(brrResult!=null)
    {
   	  BankFlag = brrResult[0][0];
   	}
   	//alert("adf"+ContGrid.getRowColData(ContGrid.getSelNo()-1,9));
	 window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,9)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&BankFlag="+BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
} 

function showTempFee() 
{
	 var tsel=ContGrid.getSelNo()-1;	 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	  
   } 
   var tAppntNo=ContGrid.getRowColData(tsel,2);
   var tAppntName=ContGrid.getRowColData(tsel,3);
	 window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&AppntNo="+tAppntNo+"&AppntName="+tAppntName,"window11");
} 

//Ӱ�����ϲ�ѯ
function showImage(){ 
	
	 var tsel=ContGrid.getSelNo()-1;
	 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
} 


//�˱���ѯ
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
   var strSQL="select prtno from LCCont where contno='"+ContNo+"'";
   var arrResult = easyExecSql(strSQL);
   var proposalcontno=arrResult[0][0];
   
	window.open("./UWQueryMain1.jsp?ProposalContNo="+proposalcontno,"window1"); 
}
