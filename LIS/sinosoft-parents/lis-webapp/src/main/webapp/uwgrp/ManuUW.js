//�������ƣ�Underwrite.js
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnConfirmPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var pflag = "1";  //�������� 1.���˵�
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgry.ManuUWInputSql";
// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;

//�ύ�����水ť��Ӧ����
function submitForm(flag)
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;


  if(ReportFlag=="1"&fm.all('uwUpReport').value!="4")
  {
  	alert('�������Ѱ������ϱ���ֻ���ں˱�������ѡ��"�����¼�"��');
   	fm.all('uwUpReport').value="4";
   	fm.all('uwUpReportName').value="�����¼�";
   	return;
  }
  if(ReportFlag=="2"&fm.all('uwUpReport').value!="0")
  {
   	alert('�������Ѱ������ϱ���ֻ���ں˱�������ѡ��"�������"��');
   	fm.all('uwUpReport').value="0";
   	fm.all('uwUpReportName').value="�������";
   	return;

   }


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

  if(flag==1)
  {

    var tUWState = fm.uwState.value;                  //�˱�����
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //�ϱ���־
    var tUWPopedom = fm.uwPopedom.value;              //�˱�ʦ����

    if(tUWState == "")
     {
     	showInfo.close();
      alert("����¼��б��˱�����!");
    	return ;
    }
    if(tUWIdea == "")
     {
      showInfo.close();
      alert("����¼��б��˱����!");
      return ;
    }

    k++;
/*
    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000001110'"
    						+" and missionid = '"+fm.MissionID.value+"'"
    						;
*/
		var sqlid1="ManuUWInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(k);
		mySql1.addSubPara(k);
		mySql1.addSubPara(fm.MissionID.value);
		
    turnConfirmPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    	alert("δ�鵽������SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    //alert();
    fm.action = "./UWManuNormChk.jsp";
    fm.submit(); //�ύ
  	return;
  }
  else if(flag ==2)
  {
    //var tSelNo = PolAddGrid.getSelNo();
    //fm.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
    //fm.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

    var tUWState = fm.uwState.value;                  //�˱�����
    //var tUWDelay = fm.UWDelay.value;
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //�ϱ���־
    var tUWPopedom = fm.uwPopedom.value;              //�˱�ʦ����
    //var tUWPer = fm.uwPer.value;                      //�˱�ʦ


    if(tUWState == "")
     {
     	showInfo.close();
      alert("����¼��б��˱�����!");
    	return ;
    }

  //���Ѱ������ٱ��ϱ���Ҫ¼��˱�����ͺ˱�ʦ
  //  if(tUpReport == "1" || tUpReport == "3"|| tUpReport == "2"|| tUpReport == "4")
  //  {
  //    if (tUWPopedom == "" || tUWPer == "")
  //    {
  //        showInfo.close();
  //        alert("�˱�ʦ����ͺ˱�ʦ���벻��Ϊ��!!!");
  //        return ;
  //    }
  //  }

    if(tUWIdea == "")
     {
      showInfo.close();
      alert("����¼��б��˱����!");
      return ;
    }


    k++;
/*
    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000001110'"
    						+" and missionid = '"+fm.MissionID.value+"'"
    						;
    						*/
var sqlid2="ManuUWInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(k);
		mySql2.addSubPara(k);
		mySql2.addSubPara(fm.MissionID.value);
		
    turnConfirmPage.strQueryResult = easyQueryVer3(mySql2.getString(), 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    	alert("δ�鵽������SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    fm.action = "./UWManuNormChk.jsp";
    fm.submit(); //�ύ
    //alert("submit");
  	return;
  }
  else if(flag==0)
  	{
  	//alert("ok");
  	//return;   
  	//add by yaory for verify 
  	
//strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='"+ActivityID+"' and submissionid='"+SubMissionID+"'";

var sqlid3="ManuUWInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(MissionID);
		mySql3.addSubPara(ActivityID);
		mySql3.addSubPara(SubMissionID);


var DistriMark = easyExecSql(mySql3.getString());//�ٱ���־
//alert(DistriMark);
//strSql = "select * from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"'"; 

var sqlid4="ManuUWInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(MissionID);
		mySql4.addSubPara(SubMissionID);


var rem=easyExecSql(mySql4.getString());	
//alert(rem);
//alert(fm.uwUpReport.value);
//����һ������
//alert(ContNo);
//return;

//strSql="select SuppRiskScore from lcprem where contno='"+ContNo+"' and PayPlanType='01'";

var sqlid5="ManuUWInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(ContNo);


var addremark=easyExecSql(mySql5.getString());
//�ۼƱ���
var peop=PolAddGrid.getRowColData(0,1);
//alert(peop);
//strSql="select healthyamntfb('"+peop+"','','1') from dual";
var sqlid6="ManuUWInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(peop);
		
var life=easyExecSql(mySql6.getString());
//strSql="select healthyamntfb('"+peop+"','','7') from dual";

var sqlid7="ManuUWInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(peop);

var acci=easyExecSql(mySql7.getString());
//strSql="select healthyamntfb('"+peop+"','','2') from dual";

var sqlid8="ManuUWInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(peop);

var nur=easyExecSql(mySql8.getString());
var add=life+acci;
//alert(add);
//alert(nur);
//return;
var re;
var fb="0";
if(add>3000000 || nur>1200000)
{
	fb="1";
}
if(add>500000 || nur>100000)
{
	re="1";
}else{
re="0";
}

//alert(DistriMark);
//alert(addremark);
//alert(fb);
if(rem==null)
{
if(((DistriMark==1) || (addremark!=null && addremark>100 && re==1) || fb=="1") && fm.uwUpReport.value==0 )
{
	
//	var tSql = " select * from lwmission where missionid = '"+MissionID+"' and submissionid ='"+SubMissionID+"'"
	//         + " and activityid in '0000001140'" ;
	      
	      var sqlid9="ManuUWInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(MissionID);  
		mySql9.addSubPara(SubMissionID); 
 turnConfirmPage.strQueryResult = easyQueryVer3(mySql9.getString(), 1, 0, 1);
  
    if (turnConfirmPage.strQueryResult) 
    {
    		alert("��û�д����ٱ��ʱ���");
        fm.ReDistribute.disabled='';	
        showInfo.close();
	      return;
    }         

}
}
    //var tSelNo = PolAddGrid.getSelNo();
    //fm.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
    //fm.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

    var tUWState = fm.uwState.value;                  //�˱�����
    //var tUWDelay = fm.UWDelay.value;
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //�ϱ���־
    var tUWPopedom = fm.uwPopedom.value;              //�˱�ʦ����
    //var tUWPer = fm.uwPer.value;                      //�˱�ʦ


    if(tUWState == "")
     {
     	showInfo.close();
      alert("����¼��б��˱�����!");
    	return ;
    }

    if(tUpReport == "")
     {
     	showInfo.close();
      alert("����ѡ��˱�����!");
    	return ;
    }



    if(tUWIdea == "")
     {
      showInfo.close();
      alert("����¼��б��˱����!");
      return ;
    }

    k++;
/*
    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000001110'"
    						+" and missionid = '"+fm.MissionID.value+"'"
    						;

*/
  
	      var sqlid10="ManuUWInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(k);  
		mySql10.addSubPara(k); 
		mySql10.addSubPara(fm.MissionID.value);  

    turnConfirmPage.strQueryResult = easyQueryVer3(mySql10.getString(), 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    	alert("δ�鵽������SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    
    /*
    var tSql = " select * from lwmission where  1=1 "
             + " and activityid in ('0000001106','0000001107','0000001108','0000001111','0000001112','0000001113','0000001130','0000001280','0000001290','0000001300','0000001140') "
             + " and missionid = '"+fm.MissionID.value+"'"
             + " and submissionid = '"+fm.SubMissionID.value+"'";
     */        
        var sqlid11="ManuUWInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId(sqlid11);
		mySql11.addSubPara(fm.MissionID.value);  
		mySql11.addSubPara(fm.SubMissionID.value); 
             
             
    turnConfirmPage.strQueryResult = easyQueryVer3(mySql11.getString(), 1, 0, 1);
   
    if (turnConfirmPage.strQueryResult) 
    {
    	 showInfo.close();
    	 alert("����֪ͨ��û�лظ�, �������º˱�����")   
    	 return ; 	
    }
	else 
	{
         fm.action = "./UWManuNormChk.jsp";
         fm.submit(); //�ύ		
	}
  
    //alert("submit");
    return;

  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    //var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  {
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  	//var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	//showInfo.close();
  	//alert(content);
  	//parent.close();

    //ִ����һ������
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit3( FlagStr, content )
{
	showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;

  if (FlagStr == "Fail" )
  {
    alert(content);
  }
  else
  {
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
    //ִ����һ������
    	InitClick();
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    showInfo.close();
	alert(content);
     //parent.close();
  }
  else
  {
  	showInfo.close();
	alert(content);
	//easyQueryClick();
  	//parent.close();
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// ��ʼ�����
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
	divLCPol2.style.display= "none";
	divMain.style.display = "none";

  }
  else
  {
  		makeWorkFlow();
  }
}

function afterAddFeeApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// ��ʼ�����
 }
  else
  {
  	  var cPolNo=fm.ProposalNo.value;
	  var cMissionID =fm.MissionID.value;
	  var cSubMissionID =fm.SubMissionID.value;
	  var tSelNo = PolAddGrid.getSelNo()-1;
	  var cNo = PolAddGrid.getRowColData(tSelNo,1);
	  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
	  var cType = PolAddGrid.getRowColData(tSelNo,7);

	 if (cPrtNo != ""&& cNo !="" && cMissionID != "" )
	  {
	  	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&No="+cNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&Type="+cType,"window1",sFeatures);
	  	showInfo.close();
	  }
	  else
	  {
	  	showInfo.close();
	  	alert("����ѡ�񱣵�!");
	  }
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

/*********************************************************************
 *  ѡ��˱����Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {

	    //alert("uwstate" + cCodeName + Field.value);
		if( cCodeName == "uwstate" ) {
			DoUWStateCodeSelect(Field.value);//loadFlag��ҳ���ʼ����ʱ������
		}
}

/*********************************************************************
 *  ���ݲ�ͬ�ĺ˱�����,����ͬ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DoUWStateCodeSelect(cSelectCode) {

	if(trim(cSelectCode) == '6')//�ϱ��˱�
	{
		 uwgrade = fm.all('UWGrade').value;
         appgrade= fm.all('AppGrade').value;
         if(uwgrade==null||uwgrade<appgrade)
         {
         	uwpopedomgrade = appgrade ;
         }
        else
         {
        	uwpopedomgrade = uwgrade ;
         }
        //alert(uwpopedomgrade);
        codeSql="#1# and Comcode like #"+ comcode+"%%#"+" and popedom > #"+uwpopedomgrade+"#"	;
        // alert(codeSql);
	}
	else
	codeSql="";
}


//����Ͷ����Ϣ
function showApp(cindex)
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;

 if (cindex == 1)
	  window.open("../uwgrp/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);
 else
	  window.open("../uwgrp/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures);

showInfo.close();

}

//�����˱���¼
function showOldUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//��ǰ�˱���¼
function showNewUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cContNo=fm.ProposalContNo.value;

  	//window.open("./PUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	window.open("./UWErrMain.jsp?ContNo="+cContNo,"window1",sFeatures);

  	showInfo.close();

}


// ��Ͷ�������������ѯ
function ClaimGetQuery2()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolAddGrid.getRowColData(tSel - 1,2);
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);
	}
}
// ���������ѯ
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cInsuredNo = fm.InsuredNo.value;
		if (cInsuredNo == "")
		    return;
		  window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo,"",sFeatures);
	}
}

//������ϸ��Ϣ
function showPolDetail()
{
  //var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  ////showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //
  //cContNo=fm.ContNo.value;
  //if (cContNo != "")
  //{
  //	var tSelNo = PolAddGrid.getSelNo()-1;
  //	showPolDetailFlag[tSelNo] = 1 ;
  //	mSwitch.deleteVar( "ContNo" );
  //	mSwitch.addVar( "ContNo", "", cContNo );
  //	mSwitch.updateVar("ContNo", "", cContNo);
  //	window.open("../sys/AllProQueryMain.jsp?LoadFlag=3","window1");
  //
  //}
  var cContNo = fm.ProposalContNo.value;
  var cPrtNo = fm.PrtNo.value;
  var BankFlag = fm.all('SaleChnl').value;
  //alert("BankFlag="+BankFlag);

  window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}


//ɨ�����ѯ
function ScanQuery()
{

	  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}


//������ϲ�ѯ
function showHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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


  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

// ��Ŀ��ϸ��ѯ
function ItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ����ϸ��ѯ��ť��" );
	else
	{
	    var cNo = PolAddGrid.getRowColData(tSel - 1,1);
	    var cSumGetMoney = 	PolAddGrid.getRowColData(tSel - 1,8);

		if (cNo == "")
		   {
		   	alert( "����ѡ��һ�������˳б���Ŀ�ļ�¼���ٵ���б���Ŀ��ѯ��ť��" );
		   	return;
		   }
		window.open("../sys/AllPBqItemQueryMain.jsp?No=" + cNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);

	}
}

//�鿴������Ϣ
function Prt()
{
	var tSel = PolAddGrid.getSelNo();
    if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�������鿴��ť��" );
	else
		{
			var cNo = PolAddGrid.getRowColData(tSel - 1,1);
			if (cNo == "")
		   {
			   	alert( "����ѡ��һ�������˳б���Ŀ�ļ�¼���ٵ���б���ϸ��ѯ��ť��" );
			   	return;
		   }
			fm.all('No').value = PolAddGrid.getRowColData(tSel - 1,1);
			fm.all('PolNo').value = PolAddGrid.getRowColData(tSel - 1,2);

			var taction = parent.fraInterface.fm.action;
			var ttarget = parent.fraInterface.fm.target;
			parent.fraInterface.fm.action = "../f1printgrp/EndorsementF1P.jsp";
			parent.fraInterface.fm.target="f1print";
			fm.submit();

			parent.fraInterface.fm.action = taction;
			parent.fraInterface.fm.target=ttarget;
			fm.all('No').value = '';
			fm.all('PolNo').value = '';
		}
}


//�������
function showHealth()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//��Լ�б�
function showSpec()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo=fm.ContNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  tUWIdea = fm.all('UWIdea').value;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cContNo != ""&& cPrtNo !="" && cMissionID != "" )
  {
  	window.open("./UWManuSpecMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//�ӷѳб�
function showAdd()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var cContNo=fm.ContNo.value;
  if (cContNo != "")
  {
	window.open("./UWManuAddMain.jsp?ContNo="+cContNo,"window1",sFeatures);
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
}

//������鱨��
function showRReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo=fm.ContNo.value;

  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  window.open("./UWManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window2",sFeatures);
  showInfo.close();
}

//�˱�������
function showReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo=fm.ContNo.value;
  tUWIdea = fm.all('UWIdea').value;
  if (cContNo != "")
  {
  	window.open("../uwgrp/UWManuReportMain.jsp?ContNo="+cContNo,"window2",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//���˱�֪ͨ��
function SendNotice()
{
  cContNo = fm.ContNo.value;
  fm.uwState.value = "8";

  if (cContNo != "")
  {
	//manuchk();
	 var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

	  var cMissionID =fm.MissionID.value;

	  var cPrtNo = fm.PrtNo.value;

	  fm.PrtNoHide.value = cPrtNo ;
/*
	  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp1 = '" + cPrtNo +"'"
				 + " and LWMission.MissionProp2 = '"+ cContNo + "'"
				 + " and LWMission.ActivityID = '0000001105'"
				 + " and LWMission.ProcessID = '0000000003'"
				 + " and LWMission.MissionID = '" +cMissionID +"'";
				 */
				 var sqlid12="ManuUWInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId(sqlid12);
		mySql12.addSubPara(cPrtNo);  
		mySql12.addSubPara(cContNo); 
		mySql12.addSubPara(cMissionID); 
				 
				 
	turnPage.strQueryResult = easyQueryVer3(mySql12.getString(), 1, 1, 1);

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µĺ˱�֪ͨ��,ԭ�������:1.�ѷ��˱�֪ͨ��,��δ��ӡ.2.δ¼��˱�֪ͨ������.");
        fm.SubNoticeMissionID.value = "";
        return ;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    var tSubNoticeMissionID =   fm.SubNoticeMissionID.value ;
    if (cContNo != "" && cPrtNo != "" && cMissionID != "" && tSubNoticeMissionID != "" )
	  {
	  	fm.action = "./UWManuSendNoticeChk.jsp";
	    fm.submit();
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("����ѡ�񱣵�!");
    }
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

function SendDanNotice()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //������������
  cCode="06";        //��������

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }

}
function SendRanNotice()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //������������
  cCode="00";        //��������

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }

}


function SendAddNotice()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //������������
  cCode="83";        //��������

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }

}

function SendHealth()
{
	//
	}
//���׽�֪ͨ��
function SendFirstNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="00"; //������������
  cCode="07";        //��������

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//���߰�֪ͨ��
function SendPressNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalNo.value;
  cOtherNoType="00"; //������������
  cCode="06";        //��������

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  	 }
  else

  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
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

var withdrawFlag = false;
//���������ѯ,add by Minim
function withdrawQueryClick() {
  withdrawFlag = true;
  easyQueryClick();
}

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQuery
 *  ����:��ѯ��ʾ�����Ǻ�ͬ����.��ʾ����:��ͬδ�����˹��˱�����״̬Ϊ���˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	// ��ʼ�����

	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
    divLCPol2.style.display= "none";
    divMain.style.display = "none";

	// ��дSQL���
	k++;
	var uwgradestatus = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //��������״̬
	var strSQL = "";
	if (uwgradestatus == "1")//��������
	{/*
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp2','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') = (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	*/
		var sqlid13="ManuUWInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName(sqlresourcename);
		mySql13.setSqlId(sqlid13);
		mySql13.addSubPara(k); 
		mySql13.addSubPara(k); 
		mySql13.addSubPara(fm.QPrtNo.value);  
		mySql13.addSubPara(fm.QAppntName.value); 
		mySql13.addSubPara(fm.QProposalNo.value); 
		mySql13.addSubPara(fm.QManageCom.value);  
		mySql13.addSubPara(mOperate); 
		mySql13.addSubPara(comcode); 
	
	
	
	
	strSQL = mySql13.getString();
	
	
	
	
	}
	else
	  if (uwgradestatus == "2")//�¼�����
	  {
	  /*
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') > (select AppGrade from LCCUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	   */
	   
	   	var sqlid14="ManuUWInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName(sqlresourcename);
		mySql14.setSqlId(sqlid14);
		mySql14.addSubPara(k); 
		mySql14.addSubPara(k); 
		mySql14.addSubPara(fm.QPrtNo.value);  
		mySql14.addSubPara(fm.QAppntName.value); 
		mySql14.addSubPara(fm.QProposalNo.value); 
		mySql14.addSubPara(fm.QManageCom.value);  
		mySql14.addSubPara(mOperate); 
		mySql14.addSubPara(comcode); 
	   
	   strSQL = mySql14.getString();
	   
	   
	   }
	   else //����+�¼�����
	   {
	   /*
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') >= (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	
	*/
	var sqlid15="ManuUWInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName(sqlresourcename);
		mySql15.setSqlId(sqlid15);
		mySql15.addSubPara(k); 
		mySql15.addSubPara(k); 
		mySql15.addSubPara(fm.QPrtNo.value);  
		mySql15.addSubPara(fm.QAppntName.value); 
		mySql15.addSubPara(fm.QProposalNo.value); 
		mySql15.addSubPara(fm.QManageCom.value);  
		mySql15.addSubPara(mOperate); 
		mySql15.addSubPara(comcode); 
	   
	   strSQL = mySql15.getString();
	
	
	
	
	
	
	}

     //alert(strSQL);
     var tOperator = fm.QOperator.value;
	if(state == "1")
	{
	/*
			strSQL = strSQL + " and  LWMission.ActivityStatus = '1'"
		      + " and ( LWMission.DefaultOperator is null or LWMission.DefaultOperator = '" + tOperator + "')";
	*/
	var sqlid16="ManuUWInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename);
		mySql16.setSqlId(sqlid16);
		mySql16.addSubPara(tOperator); 
		
		strSQL = strSQL + " "+mySql16.getString()+" ";
	
	}
	if(state == "2")
	{/*
		strSQL = strSQL + " and  LWMission.ActivityStatus = '3'"
		      + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
		*/      
		      var sqlid17="ManuUWInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName(sqlresourcename);
		mySql17.setSqlId(sqlid17);
		mySql17.addSubPara(tOperator); 
		
		strSQL = strSQL +" "+ mySql17.getString()+" ";
	}
	if(state == "3")
	{/*
		strSQL = strSQL + " and  LWMission.ActivityStatus = '2'"
		    + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
		*/    
		    var sqlid18="ManuUWInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName(sqlresourcename);
		mySql18.setSqlId(sqlid18);
		mySql18.addSubPara(tOperator); 
		
		strSQL = strSQL +" "+ mySql18.getString()+" ";
		    
	}


	if (withdrawFlag) {
	  //strSQL = strSQL + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0') ";
	  /*strSQL = "select LCPol.ProposalNo,LCPol.PrtNo,LMRisk.RiskName,LCPol.AppntName,LCPol.InsuredName "
           + " from LCPol,LCUWMaster,LMRisk where 10=10 "
           + " and LCPol.AppFlag='0'  "
           + " and LCPol.UWFlag not in ('1','2','a','4','9')  "
           + " and LCPol.grppolno = '00000000000000000000' and LCPol.contno = '00000000000000000000' "
           + " and LCPol.ProposalNo = LCPol.MainPolNo  and LCPol.ProposalNo= LCUWMaster.ProposalNo  "
           + " and LCUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') "
           + " and LCPol.ManageCom like '" + manageCom + "%%'"
           + " and LMRisk.RiskCode = LCPol.RiskCode "
           + getWherePart( 'LCUWMaster.Operator','QOperator')
           + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0')";
*/
 var sqlid19="ManuUWInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName(sqlresourcename);
		mySql19.setSqlId(sqlid19);
		mySql19.addSubPara(mOperate); 
mySql19.addSubPara(manageCom);
mySql19.addSubPara(fm.QOperator.value);
strSQL = mySql19.getString();
	  withdrawFlag = false;
	}

	//execEasyQuery( strSQL );
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ���б��˱��ĸ��˵���");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;
}

/*********************************************************************
 *  ִ��multline�ĵ���¼�
 *  ����:�˴����ɹ������ķ���졢�������˱�֪ͨ��Ƚ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryAddClick(parm1,parm2)
{
	// ��дSQL���
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //Ͷ��������״̬
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";

	if(fm.all(parm1).all('InpPolGridSel').value == '1' ){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		tProposalNo = fm.all(parm1).all('PolGrid2').value;
		tPNo = fm.all(parm1).all('PolGrid1').value;
	}

	fm.all('ContNo').value = fm.all(parm1).all('PolGrid2').value;
	fm.all('MissionID').value = fm.all(parm1).all('PolGrid6').value;
	fm.all('PrtNo').value = fm.all(parm1).all('PolGrid1').value;
	fm.all('SubMissionID').value = fm.all(parm1).all('PolGrid7').value;
	var ContNo = fm.all('ContNo').value;

	//alert("contno11="+fm.all('ContNo').value);


	if(state == "1")
	{
		checkDouble(tProposalNo);
	//���ɹ������½ڵ�
	}
	else
		{
			makeWorkFlow();
			}

	// ��ʼ�����
	initPolAddGrid();
	initPolBox();
	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	if (uwgrade == "1")
	{
	/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '����ͣ' end  from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'";
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
				  */
	    var sqlid20="ManuUWInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName(sqlresourcename);
		mySql20.setSqlId(sqlid20);
		mySql20.addSubPara(k); 
        mySql20.addSubPara(k);
        mySql20.addSubPara(ContNo);
	
	strSQL = mySql20.getString();
	
	
	
	}
	else
	{/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '����ͣ' end from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'";
*/
	 var sqlid21="ManuUWInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName(sqlresourcename);
		mySql21.setSqlId(sqlid21);
		mySql21.addSubPara(k); 
        mySql21.addSubPara(k);
        mySql21.addSubPara(ContNo);
	
	strSQL = mySql21.getString();
	
	
	
	}
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ���б��˱��ĸ��˵���");
 		window.location.href("./ManuUWInput.jsp");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("��ѯeasyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

  //alert("contno21="+fm.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+fm.all('PrtNo').value);
  	var arrSelected = new Array();

//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	
	 var sqlid22="ManuUWInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName(sqlresourcename);
		mySql22.setSqlId(sqlid22);
		mySql22.addSubPara(ContNo); 

	
	
	turnPage.strQueryResult  = easyQueryVer3(mySql22.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('ProposalContNo').value = arrSelected[0][0];
	fm.all('PrtNo').value = arrSelected[0][1];
	fm.all('ManageCom').value = arrSelected[0][2];
	fm.all('SaleChnl').value = arrSelected[0][3];
	fm.all('AgentCode').value = arrSelected[0][4];
	fm.all('AgentGroup').value = arrSelected[0][5];
	fm.all('AgentCode1').value = arrSelected[0][6];
	fm.all('AgentCom').value = arrSelected[0][7];
	fm.all('AgentType').value = arrSelected[0][8];
	fm.all('ReMark').value = arrSelected[0][9];
/*
	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag from lcappnt,LDPerson where contno='"+ContNo+"'"
		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	*/
	var sqlid23="ManuUWInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName(sqlresourcename);
		mySql23.setSqlId(sqlid23);
		mySql23.addSubPara(ContNo); 
	
	turnPage.strQueryResult  = easyQueryVer3(mySql23.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('AppntName').value = arrSelected[0][0];
	fm.all('AppntSex').value = arrSelected[0][1];
	fm.all('AppntBirthday').value = arrSelected[0][2];
	fm.all('AppntNo').value = arrSelected[0][3];
	fm.all('AddressNo').value = arrSelected[0][4];
	fm.all('VIPValue').value = arrSelected[0][5];
	fm.all('BlacklistFlag').value = arrSelected[0][6];

  return true;
}


function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initPolGrid();

		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			}// end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//����Ҫ�˹��˱�����
function checkDouble(tProposalNo)
{
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	fm.submit();
}

//ѡ��Ҫ�˹��˱�����
function getPolGridCho()
{
	//setproposalno();
	var cSelNo = PolAddGrid.getSelNo()-1;

	codeSql = "code";
	fm.UWPopedomCode.value = "";
	fm.action = "./ManuUWCho.jsp";
	fm.submit();
}

/*********************************************************************
 *  ���ɹ������������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function makeWorkFlow()
{
	fm.action = "./ManuUWChk.jsp";
	fm.submit();
	}

function checkBackPol(ProposalNo) {
  //var strSql = "select * from LCApplyRecallPol where ProposalNo='" + ProposalNo + "' and InputState='0'";
  
  var sqlid24="ManuUWInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName(sqlresourcename);
		mySql24.setSqlId(sqlid24);
		mySql24.addSubPara(ProposalNo); 
  
  var arrResult = easyExecSql(mySql24.getString());
  //alert(arrResult);

  if (arrResult != null) {
    return false;
  }
  return true;
}

//  ��ʼ���˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ�������
function initFlag(  tlength )
{
	// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
      showPolDetailFlag =  new Array() ;
      showAppFlag = new Array() ;
      showHealthFlag = new Array() ;
      QuestQueryFlag = new Array() ;

     var i=0;
	  for( i = 0; i < tlength ; i++ )
		{
			showPolDetailFlag[i] = 0;
			showAppFlag[i] = 0;
			showHealthFlag[i] = 0;
			QuestQueryFlag[i] = 0;
		}

	}
//�º˱�����
function manuchk()
{

	flag = fm.all('UWState').value;
	var ProposalNo = fm.all('ProposalNo').value;
	var MainPolNo = fm.all('MainPolNoHide').value;

	if (trim(fm.UWState.value) == "") {
    alert("������¼��˱����ۣ�");
    return;
  }

	if (!checkBackPol(ProposalNo)) {
	  if (!confirm("��Ͷ�����г������룬�����´˽�����")) return;
	}

    if (trim(fm.UWState.value) == "6") {
      if(trim(fm.UWPopedomCode.value) !="")
         fm.UWOperater.value = fm.UWPopedomCode.value
      else
         fm.UWOperater.value = operator;
}

    var tSelNo = PolAddGrid.getSelNo()-1;

	if(fm.State.value == "1"&&(fm.UWState.value == "1"||fm.UWState.value == "2"||fm.UWState.value =="4"||fm.UWState.value =="6"||fm.UWState.value =="9"||fm.UWState.value =="a")) {
      if( showPolDetailFlag[tSelNo] == 0 || showAppFlag[tSelNo] == 0 || showHealthFlag[tSelNo] == 0 || QuestQueryFlag[tSelNo] == 0 ){
         var tInfo = "";
         if(showPolDetailFlag[tSelNo] == 0)
            tInfo = tInfo + " [Ͷ������ϸ��Ϣ]";
         if(showAppFlag[tSelNo] == 0)
            tInfo = tInfo + " [����Ͷ����Ϣ]";
         if( PolAddGrid.getRowColData(tSelNo,1,PolAddGrid) == PolAddGrid.getRowColData(tSelNo ,2,PolAddGrid)) {
         	if(showHealthFlag[tSelNo] == 0)
              tInfo = tInfo + " [�������¼��]";
         }
         if(QuestQueryFlag[tSelNo] == 0)
            tInfo = tInfo + " [�������ѯ]";
         if ( tInfo != "" ) {
         	tInfo = "����Ҫ��Ϣ:" + tInfo + " δ�鿴,�Ƿ�Ҫ�¸ú˱�����?";
         	if(!window.confirm( tInfo ))
         	    return;
             }

        }
     }
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

	fm.action = "./UWManuNormChk.jsp";
	fm.submit();

	if (flag != "b"&&ProposalNo == MainPolNo)
	{
		initInpBox();
   		initPolBox();
		initPolGrid();
		initPolAddGrid();
	}
}

//function manuchk()
//{
//
//	flag = fm.all('UWState').value;
//	tUWIdea = fm.all('UWIdea').value;
//
//	//¼��б��ƻ��������Ҫ����������
//	if( flag == "b")
//	{
//		cProposalNo=fm.PolNoHide.value;
//	}
//	else
//	{
//		cProposalNo=fm.ProposalNo.value;
//	}
//
//	//alert("flag:"+flag);
//	if (cProposalNo == "")
//	{
//		alert("����ѡ�񱣵�!");
//	}
//	else
//	{
//		if (flag == "0"||flag == "1"||flag == "4"||flag == "6"||flag == "9"||flag == "b")
//		{
//			showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//		}
//
//		if (flag == "2") //����
//		{
//			//showModalDialog("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//
//		if (flag == "3") //�����б�
//		{
//			//showModalDialog("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//		if (flag == "7") //�����¼��
//		{
//			QuestInput();
//		}
//
//		if (flag != "b")
//		{
//		initInpBox();
//    		initPolBox();
//		initPolGrid();
//		initPolAddGrid();
//		}
//	}
//}

//�����¼��
function QuestInput()
{
	cContNo = fm.ContNo.value;  //��������

	//var strSql = "select * from LCcUWMaster where ContNo='" + cContNo + "' and ChangePolFlag ='1'";
    //alert(strSql);
    
      var sqlid25="ManuUWInputSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName(sqlresourcename);
		mySql25.setSqlId(sqlid25);
		mySql25.addSubPara(cContNo); 
    
    var arrResult = easyExecSql(mySql25.getString());
       //alert(arrResult);
    if (arrResult != null) {
      var tInfo = "�б��ƻ����δ�ظ�,ȷ��Ҫ¼���µ������?";
   if(!window.confirm( tInfo )){
          return;
        }
      }
	window.open("./QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window1",sFeatures);

}

//������ظ�
function QuestReply()
{
	cProposalNo = fm.ProposalNo.value;  //��������

	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestReplyMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);

	initInpBox();
    initPolBox();
	initPolGrid();

}

//�������ѯ
function QuestQuery()
{
	cContNo = fm.ContNo.value;  //��������


  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("./QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }

}

//������鱨���ѯ
function RReportQuery()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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


  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./RReportQueryMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//�������������ѯ
function BackPolQuery()
{
  cProposalNo = fm.ProposalNo.value;  //��������


  if (cProposalNo != "")
  {
	window.open("./BackPolQueryMain.jsp?ProposalNo1="+cProposalNo,sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//�߰쳬ʱ��ѯ
function OutTimeQuery()
{
  cProposalNo = fm.ProposalNo.value;  //��������


  if (cProposalNo != "")
  {
	window.open("./OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo,sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//���ռƻ����
function showChangePlan()
{
	/*
  cProposalNo = fm.ProposalNo.value;  //��������
  cPrtNo = fm.PrtNoHide.value; //ӡˢ��
  var cType = "ChangePlan";
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );

  if (cProposalNo != ""&&cPrtNo != "")
  {
  	 var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
     var arrResult = easyExecSql(strSql);
     if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo ))
         	    return;
      }
    window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=3&Type="+cType+"&prtNo="+cPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

   }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
  */

  var cPrtNo = fm.PrtNo.value;
  
  var strSql="";
 // strSql="select salechnl from lccont where contno='"+cPrtNo+"'";
  
  var sqlid26="ManuUWInputSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName(sqlresourcename);
		mySql26.setSqlId(sqlid26);
		mySql26.addSubPara(cPrtNo); 
  
  arrResult = easyExecSql(mySql26.getString());
  var BankFlag = arrResult[0][0];
  

  window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=25&prtNo="+cPrtNo+"&BankFlag="+BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1",sFeatures);
}

//���ռƻ��������¼����ʾ
function showChangeResultView()
{
	var cContNo = fm.ContNo.value;
	//var strSql = "select changepolreason from LCcUWMaster where ContNo='" + cContNo + "' ";
  //  alert(strSql);
  
  var sqlid27="ManuUWInputSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName(sqlresourcename);
		mySql27.setSqlId(sqlid27);
		mySql27.addSubPara(cContNo); 
  
  var arrResult = easyExecSql(mySql27.getString());
 
	fm.ChangeIdea.value = arrResult[0][0];
	//fm.PolNoHide.value = fm.ProposalNo.value;  //��������
	
	divUWResult.style.display= "none";
	divChangeResult.style.display= "";
}


//��ʾ���ռƻ��������
function showChangeResult()
{
	
	fm.uwState.value = "b";
	fm.UWIdea.value = fm.ChangeIdea.value;
	var cContNo = fm.ProposalContNo.value;

	cChangeResult = fm.UWIdea.value;

	
	
	
	//	var strSql = "select * from LCIssuepol where ContNo='" + cContNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
      
      
       var sqlid28="ManuUWInputSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName(sqlresourcename);
		mySql28.setSqlId(sqlid28);
		mySql28.addSubPara(cContNo); 
      
        var arrResult = easyExecSql(mySql28.getString());
        //alert(arrResult);
       if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo )){
       	      HideChangeResult()
         	    return;
          }
       }

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
    
  	fm.action = "./UWManuNormChk.jsp";
  
  	fm.submit(); //�ύ
  
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}

//���ر��ռƻ��������
function HideChangeResult()
{
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}


function cancelchk()
{
	fm.all('uwState').value = "";
	fm.all('UWPopedomCode').value = "";
	fm.all('UWIdea').value = "";
}

function setproposalno()
{
	var count = PolGrid.getSelNo();
	fm.all('ProposalNo').value = PolGrid.getRowColData(count - 1,1,PolGrid);
}

//�����հ�ť���غ���
function hideAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "none";
	parent.fraInterface.divAddButton2.style.display= "none";
	parent.fraInterface.divAddButton3.style.display= "none";
	parent.fraInterface.divAddButton4.style.display= "none";
	parent.fraInterface.divAddButton5.style.display= "none";
	parent.fraInterface.divAddButton6.style.display= "none";
	parent.fraInterface.divAddButton7.style.display= "none";
	parent.fraInterface.divAddButton8.style.display= "none";
	parent.fraInterface.divAddButton9.style.display= "none";
	parent.fraInterface.divAddButton10.style.display= "none";
	//parent.fraInterface.fm.UWState.CodeData = "0|^4|ͨ�ڳб�^9|�����б�";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^4|ͨ��/�����б�^9|�����б�\" ondblclick= \"showCodeListEx('UWState1',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState1',[this,''],[0,1]);\">";


}

//��ʾ���ذ�ť
function showAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "";
	parent.fraInterface.divAddButton2.style.display= "";
	parent.fraInterface.divAddButton3.style.display= "";
	parent.fraInterface.divAddButton4.style.display= "";
	parent.fraInterface.divAddButton5.style.display= "";
	parent.fraInterface.divAddButton6.style.display= "";
	parent.fraInterface.divAddButton7.style.display= "";
	parent.fraInterface.divAddButton8.style.display= "";
	parent.fraInterface.divAddButton9.style.display= "";
	parent.fraInterface.divAddButton10.style.display= "";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^1|�ܱ�^2|����^4|ͨ��/�����б�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����\" ondblclick= \"showCodeListEx('UWState',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState',[this,''],[0,1]);\">";
	//parent.fraInterface.fm.UWState.CodeData = "0|^1|�ܱ�^2|����^4|ͨ�ڳб�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����";
}

function showNotePad() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWNotePadMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&OperatePos=3", "window1",sFeatures);
  }
}

function InitClick(){
	location.href  = "./ManuUWAll.jsp?type=1";
}

/*********************************************************************
 *  ���뱻������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredInfo()
{
	  var tSelNo = PolAddGrid.getSelNo();
		var tContNo = fm.ContNo.value;
		var tInsuredNo = PolAddGrid.getRowColData(tSelNo - 1,1);
		var tMissionID = fm.MissionID.value;
		var tSubMissionID = fm.SubMissionID.value;
		var tPrtNo = fm.PrtNo.value;
		var tSendFlag = fm.SendFlag.value;
		window.open("./InsuredUWInfoMain.jsp?ScanFlag=0&ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&PrtNo="+tPrtNo+"&SendFlag="+tSendFlag, "", sFeatures);
}

/*********************************************************************
 *  ����ϵͳ��
 *  ����:�˴����ɹ������ķ���졢�������˱�֪ͨ��Ƚ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initUW()
{
	//alert("ok");
        //add by yaory for init �� �� �� �� button
//        alert(MissionID);
//        alert(SubMissionID);
//       alert(ActivityID);
//strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001100' and submissionid='"+SubMissionID+"'";
//var DistriMark = easyExecSql(strSql);
//alert(strSql);
//alert(DistriMark);
//alert("ok");

//
//strSql="select healthyamntfb('"+peop+"','','1') from dual";
//var life=easyExecSql(strSql);
//strSql="select healthyamntfb('"+peop+"','','7') from dual";
//var acci=easyExecSql(strSql);
//strSql="select healthyamntfb('"+peop+"','','2') from dual";
//var nur=easyExecSql(strSql);
//var add=life+acci;
////alert(add);
////alert(nur);
////return;
//var re;
//var fb="0";
//if(add>3000000 || nur>1200000)
//{
//	fb="1";
//}
//if(add>500000 || nur>100000)
//{
//	re="1";
//}else{
//re="0";
//}
//alert("ok");
//alert(strSql);
//if(DistriMark!="1")
//{
//   //fm.ReDistribute.style.display='none';
//   fm.ReDistribute.disabled=true;
//}
	// ��дSQL���
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //Ͷ��������״̬
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";

	fm.all('ContNo').value = ContNo;
	fm.all('MissionID').value = MissionID;
	fm.all('PrtNo').value = PrtNo;
	fm.all('SubMissionID').value = SubMissionID;
  makeWorkFlow();

	// ��ʼ�����
	initPolAddGrid();
	initPolBox();
	initSendTrace();

	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";

	if (uwgrade == "1")
	{
	/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'"
				  + " order by LCInsured.SequenceNo "
				  ;
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
				  
		*/		  
				    var sqlid29="ManuUWInputSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName(sqlresourcename);
		mySql29.setSqlId(sqlid29);
		mySql29.addSubPara(k); 
		mySql29.addSubPara(k);
		mySql29.addSubPara(ContNo);
		strSQL =mySql29.getString();		  
				  
	}
	else
	{
	/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'"
				  + " order by LCInsured.SequenceNo "
				  ;
		*/		  
				  
				    var sqlid30="ManuUWInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName(sqlresourcename);
		mySql30.setSqlId(sqlid30);
		mySql30.addSubPara(k); 
		mySql30.addSubPara(k);
		mySql30.addSubPara(ContNo);
		strSQL =mySql30.getString();	

	}

	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��δͨ������Լ�˱��ĸ��˺�ͬ����");
    return;
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("��ѯeasyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

  //alert("contno21="+fm.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+fm.all('PrtNo').value);
  	var arrSelected = new Array();

	//strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	
	var sqlid31="ManuUWInputSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName(sqlresourcename);
		mySql31.setSqlId(sqlid31);

		mySql31.addSubPara(ContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql31.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('ProposalContNo').value = arrSelected[0][0];
	fm.all('PrtNo').value = arrSelected[0][1];
	fm.all('ManageCom').value = arrSelected[0][2];
	fm.all('SaleChnl').value = arrSelected[0][3];
	fm.all('AgentCode').value = arrSelected[0][4];
	fm.all('AgentGroup').value = arrSelected[0][5];
	fm.all('AgentCode1').value = arrSelected[0][6];
	fm.all('AgentCom').value = arrSelected[0][7];
	fm.all('AgentType').value = arrSelected[0][8];
	fm.all('ReMark').value = arrSelected[0][9];

	//strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag,ldperson.OccupationCode,ldperson.OccupationType,lcappnt.NativePlace from lcappnt,LDPerson where contno='"+ContNo+"'"
	//	+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	
	var sqlid32="ManuUWInputSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName(sqlresourcename);
		mySql32.setSqlId(sqlid32);
		mySql32.addSubPara(ContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql32.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('AppntName').value = arrSelected[0][0];
	fm.all('AppntSex').value = arrSelected[0][1];
	fm.all('AppntBirthday').value = calAge(arrSelected[0][2]);
	fm.all('AppntNo').value = arrSelected[0][3];
	fm.all('AddressNo').value = arrSelected[0][4];
	fm.all('VIPValue').value = arrSelected[0][5];
	fm.all('BlacklistFlag').value = arrSelected[0][6];

	fm.all('OccupationCode').value = arrSelected[0][7];
	fm.all('OccupationType').value = arrSelected[0][8];
	fm.all('NativePlace').value = arrSelected[0][9];


	//��ѯ���µĺ˱����ۣ����������ʾ
	//strSQL="select PassFlag,UWIdea from lccuwmaster where ContNo='"+ContNo+"'";
	
	var sqlid33="ManuUWInputSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName(sqlresourcename);
		mySql33.setSqlId(sqlid33);
		mySql33.addSubPara(ContNo);
	
	var arr=easyExecSql(mySql33.getString());
	//alert(strSQL);
	//alert(arr);
	//alert(arr[0][1]);
	if(arr!=null){
		fm.all('uwState').value=arr[0][0];
		fm.all('UWIdea').value=arr[0][1];
	}

	//�жϺ˱��ϱ�������ʾ����Ĳ�ͬdiv

  if(ReportFlag==""||ReportFlag==null){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		fm.button1.style.display="";
		fm.button2.style.display="none";
  }
	else if(ReportFlag=="1"){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		//���Ѱ����˱�����Ĭ��Ϊ�����¼�
		fm.all('uwUpReport').value="4";
		fm.button1.style.display="none";
		fm.button2.style.display="";
	}
	else if(ReportFlag=="2"){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		fm.all('uwUpReport').value="0";
		fm.button1.style.display="";
		fm.button2.style.display="none";
	}
	
	//�ٱ��ж� yaory
//strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='"+ActivityID+"' and submissionid='"+SubMissionID+"'";

var sqlid34="ManuUWInputSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName(sqlresourcename);
		mySql34.setSqlId(sqlid34);
		mySql34.addSubPara(MissionID);
		mySql34.addSubPara(ActivityID);
		mySql34.addSubPara(SubMissionID);

var DistriMark = easyExecSql(mySql34.getString());//�ٱ���־
//alert(DistriMark);
//strSql = "select * from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"'"; 

var sqlid35="ManuUWInputSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName(sqlresourcename);
		mySql35.setSqlId(sqlid35);
		mySql35.addSubPara(MissionID);
		mySql35.addSubPara(SubMissionID);


var rem=easyExecSql(mySql35.getString());	
//alert(rem);
//alert(fm.uwUpReport.value);
//����һ������
//alert(ContNo);
//return;
//strSql="select SuppRiskScore from lcprem where contno='"+ContNo+"' and PayPlanType='01'";

var sqlid36="ManuUWInputSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName(sqlresourcename);
		mySql36.setSqlId(sqlid36);
		mySql36.addSubPara(ContNo);


var addremark=easyExecSql(mySql36.getString());
//�ۼƱ���
var peop=PolAddGrid.getRowColData(0,1);

//strSql="select healthyamntfb('"+peop+"','','1') from dual";

var sqlid37="ManuUWInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName(sqlresourcename);
		mySql37.setSqlId(sqlid37);
		mySql37.addSubPara(peop);
		
var life=easyExecSql(mySql37.getString());
//strSql="select healthyamntfb('"+peop+"','','7') from dual";

var sqlid38="ManuUWInputSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName(sqlresourcename);
		mySql38.setSqlId(sqlid38);
		mySql38.addSubPara(peop);

var acci=easyExecSql(mySql38.getString());
//strSql="select healthyamntfb('"+peop+"','','2') from dual";

var sqlid39="ManuUWInputSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName(sqlresourcename);
		mySql39.setSqlId(sqlid39);
		mySql39.addSubPara(peop);

var nur=easyExecSql(mySql39.getString());
var add=life+acci;
//alert(add);
//alert(nur);
//return;
var re;
var fb="0";
if(add>3000000 || nur>1200000)
{
	fb="1";
}
if(add>500000 || nur>100000)
{
	re="1";
}else{
re="0";
}
//alert(DistriMark);
//alert(addremark);
//alert(fb);
//alert(rem);
if(rem==null)
{
	//alert("okk");
if(DistriMark==1 || (addremark!=null && addremark>100 && re==1) || fb=="1")
{
	
  fm.ReDistribute.disabled='';	
	
}else{
   fm.ReDistribute.disabled=true;
}
}else{

	fm.ReDistribute.disabled=true;
}

  return true;
}

/*********************************************************************
 *  ��ʼ���Ƿ��ϱ��˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initSendTrace()
{
	var tSql = "";
/*
	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
					+ " and otherno = '"+ContNo+"'"
					+ " and othernotype = '1'"
					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+ContNo+"')"
					;
	*/
	var sqlid40="ManuUWInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName(sqlresourcename);
		mySql40.setSqlId(sqlid40);
		mySql40.addSubPara(ContNo);
		mySql40.addSubPara(ContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql40.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {
		fm.all('SendFlag').value =arrSelected[0][0];
		fm.all('SendUWFlag').value =arrSelected[0][1];
		fm.all('SendUWIdea').value =arrSelected[0][2];
		DivLCSendTrance.style.display="";
	}

    divUWSave.style.display = "";
    divUWAgree.style.display = "none";

    /**
	if(fm.all('SendFlag').value == '0')
	{
		divUWSave.style.display = "";
		divUWAgree.style.display = "none";
	}
	else if(fm.all('SendFlag').value == '1')
	{
		divUWAgree.style.display = "";
		divUWSave.style.display = "none";
		//divUWButton2.style.display = "none";
		tSql = "select sugpassflag,suguwidea from lccuwmaster where 1=1 "
					+ " and contno = '"+ContNo+"'"
					;
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

		fm.all('uwState').value = arrSelected[0][0];
		fm.all('UWIdea').value = arrSelected[0][1];
	}
    **/
}

/*********************************************************************
 *  ��֪����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ImpartToICD()
{
  var cContNo = fm.ContNo.value;//PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);
  var PrtNo = fm.PrtNo.value;

  if (cContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/ImaprtToICDMain.jsp?ContNo="+cContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
  else {
  	alert("����ѡ�񱣵�!");
  }
}
//�������֪ͨ��
function SendIssue()
{
  cContNo = fm.ContNo.value;

  if (cContNo != "")
  {
	//manuchk();
	 var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

	  var cMissionID =fm.MissionID.value;

	  var cPrtNo = fm.PrtNo.value;

	  fm.PrtNoHide.value = cPrtNo ;

	 // strsql = "select * from lcissuepol where contno = '" +cContNo+ "' and backobjtype = '3' and (state = '0' or state is null)";

var sqlid41="ManuUWInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName(sqlresourcename);
		mySql41.setSqlId(sqlid41);
		mySql41.addSubPara(cContNo);

	  turnPage.strQueryResult = easyQueryVer3(mySql41.getString(), 1, 1, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µ������֪ͨ��");
        fm.SubNoticeMissionID.value = "";
        return ;
    }

   // strsql = "select submissionid from lwmission where missionprop2 = '" +cContNo+ "'and missionprop1 = '"+cPrtNo+"' and activityid = '0000001022'";
   
   var sqlid42="ManuUWInputSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName(sqlresourcename);
		mySql42.setSqlId(sqlid42);
		mySql42.addSubPara(cContNo);
			mySql42.addSubPara(cPrtNo);
   
    turnPage.strQueryResult = easyQueryVer3(mySql42.getString(), 1, 1, 1);

        if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µ������֪ͨ��");
        fm.SubNoticeMissionID.value = "";
        return ;
    }

    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    fm.action = 'UWManuSendIssueCHK.jsp';
    fm.submit();
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}
//���������
function QuestBack()
{
		cContNo = fm.ContNo.value;  //��������

  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("./UWManuQuestQMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

/*********************************************************************
 *  ��ʾ�˱����𡢺˱�ʦ
 *  add by xutao 2005-04-13
 *
 *********************************************************************
 */
function showUpDIV(){

    if (fm.uwUpReport.value == '1' || fm.uwUpReport.value == '3')
    {
        //��ʾ
        //divUWup.style.display="";

    }
    else if (fm.uwUpReport.value == '2' || fm.uwUpReport.value == '4')
    {   //����
        //divUWup.style.display="none";
        //fm.uwPopedom.value = "";
        //fm.uwPopedomname.value = "";
        //fm.uwPer.value = "";
        //fm.uwPername.value = "";
    }

}
/*********************************************************************
 *  ��ʾ���շ���Ϣ
 *  add by heyq 2005-04-13
 *
 *********************************************************************
 */
function showTempFee()
{
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+fm.PrtNo.value+"&AppntNo="+fm.all('AppntNo').value+"&AppntName="+fm.all('AppntName').value,"window11",sFeatures);
}

function SendAllNotice()
{

	window.open("./SendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1",sFeatures);
}

//��ѯ�����
function queryHealthReportResult(){
	var tContNo = fm.ContNo.value;
	var tFlag = "1";

 window.open("./UWManuHealthQMain.jsp?ContNo="+tContNo+"&Flag="+tFlag,"window1",sFeatures);
}


//��ѯ�������
function queryRReportResult(){

	var tContNo = fm.ContNo.value;
	var tFlag = "1";

 window.open("./RReportQueryMain.jsp?ContNo="+tContNo+"&Flag="+tFlag,"window1",sFeatures);

}

//�����ۼ�
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//�ѳб���ѯ
function queryProposal(){

	window.open("./ProposalQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}


//δ�б���ѯ
function queryNotProposal(){

	window.open("./NotProposalQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//���������ѯ
function queryClaim(){

	window.open("./ClaimQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//������ȫ��ѯ
function queryEdor(){

	window.open("./EdorQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//�ͻ�Ʒ�ʹ���
function CustomerQuality() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWCustomerQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}

//ҵ��ԱƷ�ʹ���
function AgentQuality() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWAgentQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}

//������֪��ѯ
function queryHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//����������ѯ
function QueryRecord(){

	window.open("./RecordQueryMain.jsp?ContNo="+fm.all('ContNo').value,"window1",sFeatures);
}

function showBeforeHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;


	window.open("../uwgrp/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);

showInfo.close();

}

//�˱���ѯ
function UWQuery(){

	window.open("./UWQueryMain.jsp?ContNo="+fm.all('ContNo').value,"window1",sFeatures);
}

/*********************************************************************
 *  �޸ı�����Ч����
 *  add by zhangxing 2005-06-28
 *
 *********************************************************************
 */
 function ChangeCvalidate()
 {
 	var tContNo = fm.ContNo.value;

 window.open("./UWChangeCvalidateMain.jsp?ContNo="+tContNo,"window1",sFeatures);

}


/*********************************************************************
 *  �ٱ��б�¼��
 *  add by zhangxing 2005-06-28
 *
 *********************************************************************
 */
 function UWUpReport()
 {
 		var tContNo = fm.ContNo.value;
 		var tMissionID = fm.MissionID.value;
 		var tSubMissionID = fm.SubMissionID.value;

 window.open("./UWManuUpReportMain.jsp?ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID,"window1",sFeatures);
 }
 
 
 /*********************************************************************
 *  �ٱ���Ϣ�ظ�
 *  add by zhangxing 2005-08-10
 *
 *********************************************************************
 */
function showUpReportReply()
{
	  var tContNo = fm.ContNo.value;
 		var tMissionID = fm.MissionID.value;
 		var tSubMissionID = fm.SubMissionID.value;
 		var tFlag = "1";
 		var tPrtNo = fm.PrtNo.value;
 	
	window.open("./UWManuUpReportReplyMain.jsp?ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Flag="+tFlag,"window1",sFeatures);
}
