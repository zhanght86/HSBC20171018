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
	
  var aSQL="select distinct b.grpcontno,a.grpname,a.cvalidate,a.state from lcgrppol a,lcgrpcont b  where customerno='"+AppntNo+"'and a.grpcontno=b.grpcontno and a.appflag='1'";
  turnPage.queryModal(aSQL, ContGrid);

}



function queryPersonInfo(){
	
  var aSQL = "select appntno,grpname from lcgrpcont where appntno='"+AppntNo+"'";	
  var arrResult = easyExecSql(aSQL);
  fm.all('AppntNo').value = arrResult[0][0];
  fm.all('GrpName').value = arrResult[0][1];

}

function getContDetail(){
	//alert();
	
}

function getPolInfo(){

   
   var aSQL= "select distinct a.grpcontno,a.grpproposalno,b.riskcode,b.riskname,a.state,a.amnt,a.mult,(select distinct PassFlag from LCUWMaster where polno=a.grpcontno and PassFlag is not null and rownum=1) as newPassFlag"
       +" from lcgrppol a, lmrisk b,lcprem d"
       +" where a.grpcontno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'and a.riskcode=b.riskcode";
     
  turnPage2.queryModal(aSQL, PolGrid);
	
}


function contInfoClick(){
  getPolInfo();
  fm.button1.disabled="";
  //�ж��Ƿ��в���ɷ���Ϣ

	  //�ж��Ƿ��к˱�����
	  haveUWResult();
  	
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
	var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
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
	 		 
   // if(PolGrid.getSelNo()=='')
  // {
   //	alert("��ѡ��һ������");
  // 	return;
   // }
   
    var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
    
    window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window1");
}

function showTempFee()
{
	 var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var tAppntName=ContGrid.getRowColData(tsel,2);
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&AppntNo="+AppntNo+"&AppntName="+tAppntName,"window11");
	
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
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./UWQueryMain.jsp?ContNo="+ContNo,"window1"); 
}


