//***********************************************

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
		var iHeight=350;     //�������ڵĸ߶�; 
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
	
	var sqlid826110639="DSHomeContSql826110639";
var mySql826110639=new SqlClass();
mySql826110639.setResourceName("uw.GroupProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826110639.setSqlId(sqlid826110639);//ָ��ʹ�õ�Sql��id
mySql826110639.addSubPara(AppntNo);//ָ������Ĳ���
var aSQL=mySql826110639.getString();
	
//  var aSQL="select distinct b.grpcontno,a.grpname,a.cvalidate, "
//  				 +" (case  when b.appflag='1'  then '�б�' "
//          +" when b.appflag='4' then '��ֹ' "
//          +" else 'δ֪' end), "
//    			+" a.prtno from lcgrppol a,lcgrpcont b where customerno='"+AppntNo+"' and a.grpcontno=b.grpcontno  and a.appflag='1'";
  turnPage.queryModal(aSQL, ContGrid);

}



function queryPersonInfo(){
	
	var sqlid826110725="DSHomeContSql826110725";
var mySql826110725=new SqlClass();
mySql826110725.setResourceName("uw.GroupProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826110725.setSqlId(sqlid826110725);//ָ��ʹ�õ�Sql��id
mySql826110725.addSubPara(AppntNo);//ָ������Ĳ���
var aSQL=mySql826110725.getString();
	
//  var aSQL = "select appntno,grpname from lcgrpcont where appntno='"+AppntNo+"'";	
  var arrResult = easyExecSql(aSQL);
  document.all('AppntNo').value = arrResult[0][0];
  document.all('GrpName').value = arrResult[0][1];

}

function getContDetail(){
	//alert();
	
}

function getPolInfo(){
	
		var sqlid826111058="DSHomeContSql826111058";
var mySql826111058=new SqlClass();
mySql826111058.setResourceName("uw.GroupProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826111058.setSqlId(sqlid826111058);//ָ��ʹ�õ�Sql��id
mySql826111058.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//ָ������Ĳ���
var aSQL=mySql826111058.getString();

   
//	   var aSQL= "select distinct a.grpcontno,a.grpproposalno,b.riskcode,b.riskname,"
//	   +"(case when exists(select endDate from LmriskApp where RiskCode=b.riskcode)then '����' else 'ͣ��' end),"
//	   +"a.amnt,a.mult,"
//	   +" ( select codename from ldcode where codetype='tdhbconlusion' and code in (select"
//	  	+" distinct PassFlag from lcguwmaster where grppolno=a.grppolno and PassFlag is not null"
//	   +" and rownum=1) )as newPassFlag"
//	       +" from lcgrppol a, lmrisk b,lcprem d"
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
	var sqlid826112058="DSHomeContSql826112058";
var mySql826112058=new SqlClass();
mySql826112058.setResourceName("uw.GroupProposalQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826112058.setSqlId(sqlid826112058);//ָ��ʹ�õ�Sql��id
mySql826112058.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//ָ������Ĳ���
var fSQL=mySql826112058.getString();
	
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
//  //alert(aSQL);
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
     retutn;	 
   }
   var tAppntName=ContGrid.getRowColData(tsel,2);
   //alert(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&AppntNo="+AppntNo+"&AppntName="+tAppntName+"&ContType=2","window11",sFeatures);
	
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
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
    
}
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);

	window.open("./UWQueryMain.jsp?ContNo="+ContNo+"&ContType=2","window1",sFeatures); 
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

