//�������ƣ�WithdrawCont.js
//�����ܣ�����
//�������ڣ�2008-10-18 11:10:36
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var strsql;
var showInfo;
var turnPage = new turnPageClass();
var turnPageAll = new turnPageClass();
var turnQuery = new turnPageClass();
var tContType = "1";
//�ύ�����水ť��Ӧ����
function submitForm()
{  
    var SelNo = WihtDContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("��ѡ��Ҫ������Ͷ������");
        return;
    }
    document.all('ContNoH').value = WihtDContGrid.getRowColData( SelNo- 1, 1);
    document.all('PrtNoH').value = WihtDContGrid.getRowColData( SelNo- 1, 2);    
    
	if(document.all('UWWithDReasonCode').value=="")
	{
		alert("����ԭ����Ϊ�գ�");
    	return;
  	}
	if(document.all('UWWithDReasonCode').value=="14"&&(document.all('Content').value==null||document.all('Content').value==""))
	{
		alert("��¼�볷��˵����");
    	return;
  	} 
  	if(document.all('Content').value!="" && document.all('Content').value.length>20)
	{
		alert("����˵��������20�����ڣ�");
    	return;
  	} 
  	//�С���Լ�����ӷѡ����б����۱��������족������������������룭��ȡ���������ѷ���δ�ظ�Ͷ����������ԭ�򲻿�ѡ�񡰿ͻ����볷���� 2008-12-15 ln add
    var sqlid916101523="DSHomeContSql916101523";
var mySql916101523=new SqlClass();
mySql916101523.setResourceName("uw.WithdrawContSql");//ָ��ʹ�õ�properties�ļ���
mySql916101523.setSqlId(sqlid916101523);//ָ��ʹ�õ�Sql��id
mySql916101523.addSubPara(document.all('ContNoH').value);//ָ������Ĳ���
mySql916101523.addSubPara(document.all('ContNoH').value);//ָ������Ĳ���
mySql916101523.addSubPara(document.all('ContNoH').value);//ָ������Ĳ���
mySql916101523.addSubPara(document.all('ContNoH').value);//ָ������Ĳ���
mySql916101523.addSubPara(document.all('ContNoH').value);//ָ������Ĳ���
mySql916101523.addSubPara(document.all('ContNoH').value);//ָ������Ĳ���
var strSQL1=mySql916101523.getString();
    
//    var strSQL1="select 1 from lcuwmaster where contno='"+document.all('ContNoH').value+"' and changepolflag is not null and changepolflag not in ('0','2') "
//                + " union (select 1 from lccspec where contno='" +document.all('ContNoH').value+ "' and (spectype='ch' or spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp')) and prtflag is not null and prtflag <>'2')"
//                //+ " union (select 1 from lcprem where contno='" +document.all('ContNoH').value+ "' and payplancode like '000000%%' and exists (select 1 from lwmission where missionprop2='" +document.all('ContNoH').value+ "' and activityid='0000001100' and missionprop18='4'))"
//                + " union (select 1 from lcprem where contno='" +document.all('ContNoH').value+ "' and payplancode like '000000%%' )"
//                + " union (select 1 from lcpenotice where contno='"+document.all('ContNoH').value+"' and printflag is not null and printflag <>'2' )"
//                + " union (select 1 from lcrreport where contno='"+document.all('ContNoH').value+"'  and replyflag is not null and replyflag <>'2')"
//                + " union (select 1 from lcissuepol where contno='"+document.all('ContNoH').value+"' and issuetype='2302' and state is not null "
//                	+ " and exists(select 1 from LOPRTManager where code='TB90' and otherno=lcissuepol.contno and othernotype='02' and stateflag<>'2'))";
    var arrResult1 = easyExecSql(strSQL1);
		if (arrResult1!=null && document.all('UWWithDReasonCode').value == "00") 
		{
			alert("�гб��ƻ��������졢������֪ͨ��δ�ظ�������ѡ��ͻ����볷����");
			return;
		}	
  	
  	//�ж�֧Ʊ�Ƿ���----add by haopan --2007-2-1
  	var sqlid916111715="DSHomeContSql916111715";
var mySql916111715=new SqlClass();
mySql916111715.setResourceName("uw.WithdrawContSql");//ָ��ʹ�õ�properties�ļ���
mySql916111715.setSqlId(sqlid916111715);//ָ��ʹ�õ�Sql��id
mySql916111715.addSubPara(document.all('ContNoH').value);//ָ������Ĳ���
var strSQL=mySql916111715.getString();

  	
//    var strSQL="select * from ljtempfeeclass where paymode='3' and otherno='"+document.all('ContNoH').value+"'  and enteraccdate is null";
    var arrResult = easyExecSql(strSQL);
		if (arrResult!=null) 
		{
			alert("֧Ʊ��δ���ʣ���������г�����");
			return;
		}
	//if(document.all('UWWithDReasonCode').value<"05")
	//{	
		if(!confirm("�������޷��ָ����Ƿ񳷵���"))
		     return;
	//}

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
lockScreen('lkscreen');  
  document.getElementById("fm").submit(); //�ύ 
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showInfo.close();
       
  }
  else
  { 
		var content="�����ɹ���";
  	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	//parent.close();
   // top.close();   
   withdrawQueryClick();
   queryWihtDContInfo("");
  }
}

//����������ѯ
function QueryRecord(){

    var SelNo = WihtDContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("��ѡ��Ҫ��ѯ��Ͷ������");
        return;
    }
    
    var mContNo = WihtDContGrid.getRowColData( SelNo- 1, 1);
    
	window.open("./RecordQueryMain.jsp?ContNo="+mContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//��ʾ������Ͷ������ϸ��Ϣ
function showWihtDContInfo()
{	
	  var tSelNo = WithDContAllGrid.getSelNo();
	  var tContNo = WithDContAllGrid.getRowColData(tSelNo - 1,1);	 	
	 	
	  queryWihtDContInfo(tContNo); 	
      
}

//��ѯ������Ͷ������ϸ��Ϣ
function queryWihtDContInfo(tContNo)
{	 	
	 	if (tContType == "1")
    {
    	var sqlid916111950="DSHomeContSql916111950";
var mySql916111950=new SqlClass();
mySql916111950.setResourceName("uw.WithdrawContSql");//ָ��ʹ�õ�properties�ļ���
mySql916111950.setSqlId(sqlid916111950);//ָ��ʹ�õ�Sql��id
mySql916111950.addSubPara(tContNo);//ָ������Ĳ���
mySql916111950.addSubPara(tContNo);//ָ������Ĳ���
mySql916111950.addSubPara(tContNo);//ָ������Ĳ���
mySql916111950.addSubPara(tContNo);//ָ������Ĳ���
mySql916111950.addSubPara(tContNo);//ָ������Ĳ���
mySql916111950.addSubPara(tContNo);//ָ������Ĳ���
mySql916111950.addSubPara(tContNo);//ָ������Ĳ���
strsql=mySql916111950.getString();
    	
//    	strsql = " select a.contno, a.prtno, a.appntno, a.appntname"
//    	   + " ,nvl((select '��' from LCPENotice where contno='" +tContNo+ "' and rownum=1),'��')"
//    	   + " ,nvl((select '��' from LCRReport where contno='" +tContNo+ "' and rownum=1),'��')"
//	       + " ,nvl((select '��' from lcissuepol where contno='" +tContNo+ "' and rownum=1),'��')"
//	       + " ,nvl((select '��' from lccspec where contno='" +tContNo+ "' and (spectype='ch' or spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp')) and rownum=1),'��')"
//	       + " ,nvl((select '��' from lcprem where contno='" +tContNo+ "' and payplancode like '000000%%' and rownum=1),'��')"
//	       + " ,nvl((select '��' from lcuwmaster where contno='" +tContNo+ "' and changepolflag is not null and changepolflag<>'0' and rownum=1),'��')"
//	       + " from lccont a where a.contno='" +tContNo+ "' ";
    }      
	
  turnPage.queryModal(strsql,WihtDContGrid); 
}

//��ѯ���д�����Ͷ����
function withdrawQueryClick()
{	
    if(document.all('ManageCom').value==null||document.all('ManageCom').value=="" || document.all('ManageCom').value.length!=8)
	{
		alert("�����������ѡ��8λ��");
    	return;
  	}
	// ��дSQL���
    //var tContType = fm.ContType.value;
   // fm.PolType.value = tContType;
    if (tContType == "1")
    {
        var wherePartStr = getWherePart('l.PrtNo', 'PrtNo')
                + getWherePart('l.ManageCom', 'ManageCom', 'like')
                + getWherePart('l.AppntName', 'AppntName')
                + getWherePart('l.InsuredName', 'InsuredName')
                + getWherePart('l.AgentCode', 'AgentCode')
                + getWherePart('l.SaleChnl', 'SaleChnl');
        var sqlid916112252="DSHomeContSql916112252";
				var mySql916112252=new SqlClass();
				mySql916112252.setResourceName("uw.WithdrawContSql");//ָ��ʹ�õ�properties�ļ���
				mySql916112252.setSqlId(sqlid916112252);//ָ��ʹ�õ�Sql��id
				mySql916112252.addSubPara(wherePartStr);//ָ������Ĳ���
				var strSQL=mySql916112252.getString();
        
//        strsql = " select l.ContNo,l.PrtNo,l.AppntName,l.InsuredName,l.AgentCode,l.ManageCom,"
//            + " nvl((select (select codename from ldcode where codetype='uwstatus' and code=MissionProp18) from lwmission where MissionProp1=l.PrtNo and activityid='0000001100' ),'��ǩ��'),"
//            + " l.UWDate "
//        		+ " from LCCont l where 1=1 "
//        		+ " and exists(select 1 from ES_DOC_MAIN m where "
//        		+ " l.prtno=m.doccode and m.subtype='UR201')"
//                + " and l.grpcontno='00000000000000000000' and l.appflag = '0'"
//                + " and ((l.uwflag <> 'a')"
//                + " or (l.uwflag is null))"
//                + " and (("
//                + " (substr(l.state, 0, 4)) <> '1002' and"
//                + " (substr(l.state, 0, 4)) <> '1003') or l.state is null) "
//                + " and not exists(select 1 from lwmission where missionprop1=l.PrtNo and activityid in ('0000001089','0000001090','0000001091'))"//�ų�����������쳣�������˳���Ͷ����
//                + getWherePart('l.PrtNo', 'PrtNo')
//                + getWherePart('l.ManageCom', 'ManageCom', 'like')
//                + getWherePart('l.AppntName', 'AppntName')
//                + getWherePart('l.InsuredName', 'InsuredName')
//                + getWherePart('l.AgentCode', 'AgentCode')
//                + getWherePart('l.SaleChnl', 'SaleChnl')
//                + " group by l.ContNo,l.PrtNo,l.AppntName,l.InsuredName,l.AgentCode,l.ManageCom, l.UWDate,l.MakeDate " 
//                + " order by l.MakeDate ";
    }   
	
  turnPageAll.queryModal(strSQL,WithDContAllGrid);
}
//��ѯҵ��Ա����
function queryAgent()
{	
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
  if(document.all('AgentCode').value == "")	{	  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	else if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length!=10){
	    alert("�����˱���λ������");
    	return;
    }	
    else
    {
      	var sqlid916112726="DSHomeContSql916112726";
var mySql916112726=new SqlClass();
mySql916112726.setResourceName("uw.WithdrawContSql");//ָ��ʹ�õ�properties�ļ���
mySql916112726.setSqlId(sqlid916112726);//ָ��ʹ�õ�Sql��id
mySql916112726.addSubPara(document.all('AgentCode').value);//ָ������Ĳ���
mySql916112726.addSubPara(document.all("ManageCom").value);//ָ������Ĳ���
var strSQL=mySql916112726.getString();
      	
//      	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+document.all('AgentCode').value+"' and a.managecom='"+document.all("ManageCom").value+"'";
	   //alert(strSQL);
	    var arrResult = easyExecSql(strSQL); 
	    //alert(arrResult);
	    if (arrResult != null) {
	    	fm.AgentCode.value = arrResult[0][0];
	  	  fm.AgentName.value = arrResult[0][3];
	      //fm.AgentManageCom.value = arrResult[0][2];
	      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][3]+"]");
	    }
	    else
	    {
	      alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ����߲��ڸù�������£������Ѿ���ְ�� ��ȷ��!");
	      fm.AgentCode.value ="";
	  	  fm.AgentName.value ="";	     
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
	}
}
