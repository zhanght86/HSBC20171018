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

var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.UWChangeCvalidateInputSql";

/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 
 //�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  
  var tCvalidate = fm.Cvalidate.value ;
 
  if(tCvalidate == "")
    { 
      alert("��������Ч����");      
      
    }
  fm.action= "./UWChangeCvalidateChk.jsp";
  fm.submit(); //�ύ
}


function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";
  	showInfo.close();
  	alert(showStr);
  	initForm();
  	
  	
    //ִ����һ������
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
/*
  var aSQL=" select ContNo,AppntName,AgentCode,ManageCom,PolApplyDate,Prem,Amnt from LCCont where 1 = 1"
          +" and ContNo = '"+tContNo+"'";         
 
*/
		var sqlid1="UWChangeCvalidateInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
		
  turnPage.queryModal(mySql1.getString(), ContGrid);

}



function queryPersonInfo(){
  //var aSQL = "select a.ContNo,a.Cvalidate from lccont a where a.ContNo='"+tContNo+"'";	
 
 var sqlid2="UWChangeCvalidateInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
 
  var arrResult = easyExecSql(mySql2.getString());
  fm.all('ContNo').value = arrResult[0][0];
  fm.all('Cvalidate').value = arrResult[0][1];
}

function getContDetail(){
	
	
}

function getPolInfo(){
	
/*
  var aSQL = "select distinct a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,"
       +" (select distinct PassFlag from LCUWMaster where polno=a.polno and PassFlag is not null and rownum=1) as newPassFlag,"
       +" d.SuppRiskScore "
	     +" from lcpol a,lmrisk b,lcprem d "
	     +" where a.contno='"+tContNo+"' and b.riskcode=a.riskcode and d.polno=a.polno ";
	     */
	     /*
	var aSQL = "select polno,PolApplyDate,CValiDate,RiskCode,Prem,Amnt from LCPol where 1=1 "
       + " and ContNo = '"+tContNo+"'";
       */
       
var sqlid3="UWChangeCvalidateInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);
 
  turnPage2.queryModal(mySql3.getString(), PolGrid);
	
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
	 window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
}

function showTempFee()
{
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window11",sFeatures);
}

