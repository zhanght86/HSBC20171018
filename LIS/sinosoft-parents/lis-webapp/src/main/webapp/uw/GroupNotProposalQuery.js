//***********************************************
//�������ƣ�GroupNotProposalQuery.js
//�����ܣ�����δ�б�������ѯ
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";


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
	
	var sqlid826142040="DSHomeContSql826142040";
var mySql826142040=new SqlClass();
mySql826142040.setResourceName("uw.GroupNotProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826142040.setSqlId(sqlid826142040);//ָ��ʹ�õ�Sql��id
mySql826142040.addSubPara(AppntNo);//ָ������Ĳ���
var aSQL=mySql826142040.getString();
	
//  var aSQL="select distinct b.grpcontno,a.grpname,a.cvalidate,"
//  +" decode(nvl(substr(trim(a.state),4,1),'z'),'z','������','1','��������','����' ),"
//   +" a.prtno from lcgrppol a,lcgrpcont b  where customerno='"+AppntNo+"'and a.grpcontno=b.grpcontno and a.appflag='0'";
  turnPage.queryModal(aSQL, ContGrid);
  


}



function queryPersonInfo(){

  var sqlid826142132="DSHomeContSql826142132";
var mySql826142132=new SqlClass();
mySql826142132.setResourceName("uw.GroupNotProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826142132.setSqlId(sqlid826142132);//ָ��ʹ�õ�Sql��id
mySql826142132.addSubPara(AppntNo);//ָ������Ĳ���
var aSQL=mySql826142132.getString();
  
//  var aSQL = "select appntno,grpname from lcgrpcont where appntno='"+AppntNo+"'";	
  var arrResult = easyExecSql(aSQL);
  document.all('AppntNo').value = arrResult[0][0];
  document.all('GrpName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}

function getPolInfo(){
	
	var sqlid826142228="DSHomeContSql826142228";
var mySql826142228=new SqlClass();
mySql826142228.setResourceName("uw.GroupNotProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826142228.setSqlId(sqlid826142228);//ָ��ʹ�õ�Sql��id
mySql826142228.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//ָ������Ĳ���
var aSQL=mySql826142228.getString();
  
//  var aSQL= "select distinct a.grpcontno,a.grpproposalno,b.riskcode,b.riskname,"
//	   +"(case when exists(select endDate from LmriskApp where RiskCode=b.riskcode)then '����' else 'ͣ��' end),"
//	   +"a.amnt,a.mult,"
//	   +" ( select codename from ldcode where codetype='tdhbconlusion' and code in (select"
//	  	+" distinct PassFlag from lcguwmaster where grppolno=a.grppolno and PassFlag is not null"
//	   +" and rownum=1) )as newPassFlag"
//	       +" from lcgrppol a, lmrisk b"
//	       +" where a.grpcontno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'and a.riskcode=b.riskcode";
  turnPage2.queryModal(aSQL, PolGrid);
	
}


function contInfoClick(){
  getPolInfo();
  document.getElementById("fm").button1.disabled="";
  //�ж��Ƿ��в���ɷ���Ϣ
haveFeeInfo();
	  //�ж��Ƿ��к˱�����
	  //haveUWResult();
  	
}

//�ж��Ƿ��в���ɷ���Ϣ
function haveFeeInfo(){
	var sqlid826142320="DSHomeContSql826142320";
var mySql826142320=new SqlClass();
mySql826142320.setResourceName("uw.GroupNotProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826142320.setSqlId(sqlid826142320);//ָ��ʹ�õ�Sql��id
mySql826142320.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//ָ������Ĳ���
var fSQL=mySql826142320.getString();
	
//	var fSQL="select * from ljtempfee where otherno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
  var arrResult = easyExecSql(fSQL);
	
	if(arrResult!=null){
    document.getElementById("fm").button3.disabled="";	
    return;
  }
  else{
    document.getElementById("fm").button3.disabled="true";	
    return;
  }
	return;
}

//�ж��Ƿ���Ӱ������
function havePicData(){
	return;
}

//�ж��Ƿ��к˱�����
//function haveUWResult(){
//	var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
////  alert(aSQL);
//  var arrResult = easyExecSql(aSQL);
//  if(arrResult!=null){
//    fm.button4.disabled="";	
//    return;
//  }
//  else{
//    fm.button4.disabled="true";	
//    return;
//  }
//	return;
//}

function getContDetailInfo(){
	 		 
   // if(PolGrid.getSelNo()=='')
  // {
   //	alert("��ѡ��һ������");
  // 	return;
   // }
   
    var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
    
    window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window1",sFeatures);
}

function showTempFee()
{
	 var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
   var tAppntName=ContGrid.getRowColData(tsel,2);
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&AppntNo="+AppntNo+"&AppntName="+tAppntName+"&ContType=2","window11",sFeatures);
	
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
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
    
}
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./UWQueryMain.jsp?ContNo="+ContNo,"window1",sFeatures); 
}

//����������ѯ
function OperationQuery()
{
	
	var arrReturn = new Array();
	var tSel = ContGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else {
	    var ContNo = ContGrid.getRowColData(tSel - 1,1);
	    var PrtNo = ContGrid.getRowColData(tSel - 1,5);				
		
		if (PrtNo == "" || ContNo == "") return;

	window.open("../sys/RecordQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo,"",sFeatures);	
  }										
} 




