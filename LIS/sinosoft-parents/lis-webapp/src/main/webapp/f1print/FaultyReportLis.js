
var arrDataSet 
var showInfo;
var mDebug="0";
var FlagDel;
var turnPage = new turnPageClass();

//�򵥲�ѯ
function easyQuery()
{
    var agentcode =document.all('AgentCode').value;
	var ManageCom = document.all('ManageCom').value;
	var sDate = document.all('StartTime').value;
	var eDate = document.all('EndTime').value;
	/*
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
	*/
	if(ManageCom!=null&&ManageCom!=""){
	//if(ManageCom.length<4){
	//	alert("�������ӦΪ4λ��8λ��");
	//	return;
	//}  
	}else{
	  alert("��¼����������");
	  return ;
	}
	//if(document.all('PrtNo').value=="")
	//{
	 if (sDate == "")
	  {
			alert("�����뿪ʼ���ڣ�");
			return;
	  }
	  if (eDate == "")
	  {
			alert("������������ڣ�");
			return;
  	  }
  //}
	initCodeGrid();
	// ��дSQL���
	//var strSQL = " select a.PrtNo,(select RiskName from LMRiskApp where RiskCode=a.RiskCode) riskname,(select nvl(codename,a.salechnl) from ldcode where codetype='salechnl' and code=a.salechnl) salechnl ,a.managecom,(select Name from LAAgent where agentcode=a.agentcode) agentname,a.insuredname insuredname,a.appntname appntname,(select decode(min(c.makedate),null,'��ɨ���',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(a.prtno)) ScanDate,(select decode(max(d.makedate),null,'δ��',to_char(max(d.makedate),'yyyy-mm-dd')) from lcuwsub d where d.ProposalNo=a.ProposalNo and uwflag='8') UWNoticeDate,(select decode(max(e.makedate),null,'δ��',to_char(max(e.makedate),'yyyy-mm-dd')) from LCPENotice e where e.ProposalNo = a.ProposalNo) HealthDate,(select decode(max(f.makedate),null,'δ��',to_char(max(f.makedate),'yyyy-mm-dd')) from LCRReport f where f.polno = a.ProposalNo) SurveyDate,getQuestSendDate(a.PolNo,'2') AgentQuestSendDate,getQuestSendDate(a.PolNo,'4') ManageQuestSendDate"
//	var strSQL_1 = "select a.prtseq, a.otherno,"
//             + " (select codename from ldcode where codetype ='renoticetype' and code =a.code), "
//             + "  d.issuecont,b.appntname,(select name from laagent where agentcode=a.agentcode),"
//             + "  (select max(riskcode) from lcpol where mainpolno =polno and contno =b.contno),"
//             + " (select codename from ldcode where codetype ='operatepos' and code =d.operatepos),"
//             + "  '',"
//             + "  ''"
//             + " from  loprtmanager a ,lccont b,lcissuepol d where code in ('TB90','TB89','14')"
//             + " and a.otherno=b.prtno and b.contno =d.contno and a.oldprtseq =d.prtseq "
//             //+ " and d.replyresult is null"
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.ManageCom like '" + comcode + "%%'"
//             + getWherePart('a.makedate','StartTime','>=')
//             + getWherePart('a.makedate','EndTime','<=')
             //+ getWherePart('a.backobjtype','BackObj')
            //+ getWherePart('d.operatepos','OperatePos')
             //+ getWherePart('d.errflag','IsRecord');
             //+ getWherePart('a.PrtNo','PrtNo')
             //+ getWherePart('a.RiskCode','RiskCode')
             //+ getWherePart('a.InsuredName','InsuredName')
             //+ getWherePart('d.salechnl','SaleChnl')
             //+ getWherePart('a.agentcode','AgentCode')
//          if(fm.RiskCode.value !=null&&fm.RiskCode.value !=""){
//			   strSQL_1 =strSQL_1+" and (select max(riskcode) from lcpol where mainpolno=polno and contno =b.contno)='"+fm.RiskCode.value+"'";       
//          }
//          if(fm.SaleChnl.value !=null&&fm.SaleChnl.value !=""){
//              strSQL_1 =strSQL_1+ " and b.salechnl='"+fm.SaleChnl.value+"' ";
//          }
          var BackObjid1="";
          var BackObjid2="";
          if(fm.BackObj.value !=null&&fm.BackObj.value!=""){
            if(fm.BackObj.value =="1"){
               BackObjid1=" and d.backobjtype in ('1','2')";
//               strSQL_1 =strSQL_1+" and d.backobjtype in ('1','2')";
            }else{
            	BackObjid2=fm.BackObj.value;
//               strSQL_1 =strSQL_1+" and d.backobjtype ='"+fm.BackObj.value+"'";
            }
          }
          
          var OperatePos1="";
          var OperatePos2="";
          if(fm.OperatePos.value !=null&&fm.OperatePos.value!=""){
             if(fm.OperatePos.value =="1"){
            	 OperatePos1=" and d.operatepos in ('0','1','5')";
//                strSQL_1 =strSQL_1+" and d.operatepos in ('0','1','5')";
             }else{
            	 OperatePos2=" and d.operatepos ='6'";
//                strSQL_1 =strSQL_1+" and d.operatepos ='6'";
             }
          }
          
          var IsRecord1="";
          var IsRecord2="";
          if(fm.IsRecord.value !=null&&fm.IsRecord.value!=""){//������Ƿ�Ǽ������
             if(fm.IsRecord.value =="Y"){
//                strSQL_1 =strSQL_1+" and d.issuetype in (select code from ldcodemod where codetype='Question' and recordquest='Y') ";
            	 IsRecord1=" and d.errflag='Y'";
//            	 strSQL_1 =strSQL_1+" and d.errflag='Y'";
             }else if(fm.IsRecord.value =="N"){
//                strSQL_1 =strSQL_1+" and d.issuetype in (select code from ldcodemod where codetype='Question' and recordquest='N') ";
            	 IsRecord2=" and (d.errflag='N' or d.errflag is null)";
//            	 strSQL_1 =strSQL_1+" and (d.errflag='N' or d.errflag is null)";
             }
          }
          
          var AgentCode1="";
          if(fm.AgentCode.value !=null&&fm.AgentCode.value!=""){
        	  AgentCode1=agentcode;
//                strSQL_1 =strSQL_1+" and b.agentcode ='"+agentcode+"'";
          }
          
          
		  	var  StartTime0 = window.document.getElementsByName(trim("StartTime"))[0].value;
		  	var  EndTime0 = window.document.getElementsByName(trim("EndTime"))[0].value;
			var sqlid0="FaultyReportLisSql0";
			var mySql0=new SqlClass();
			mySql0.setResourceName("f1print.FaultyReportLisSql"); //ָ��ʹ�õ�properties�ļ���
			mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
			mySql0.addSubPara(document.all("ManageCom").value);//ָ������Ĳ���
			mySql0.addSubPara(comcode);//ָ������Ĳ���
			mySql0.addSubPara(StartTime0);//ָ������Ĳ���
			mySql0.addSubPara(EndTime0);//ָ������Ĳ���
			mySql0.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
			mySql0.addSubPara(fm.SaleChnl.value);//ָ������Ĳ���
			mySql0.addSubPara(BackObjid1);//ָ������Ĳ���
			mySql0.addSubPara(BackObjid2);//ָ������Ĳ���
			mySql0.addSubPara(OperatePos1);//ָ������Ĳ���
			mySql0.addSubPara(OperatePos2);//ָ������Ĳ���
			mySql0.addSubPara(IsRecord1);//ָ������Ĳ���
			mySql0.addSubPara(IsRecord2);//ָ������Ĳ���
			mySql0.addSubPara(AgentCode1);//ָ������Ĳ���
			var strSQL_1=mySql0.getString();
    
//    var strSQL_2 ="union"
//    		 + " select c.prtseq, c.grpcontno, '���������', c.issuecont,"
//    		 + " (select appntname from lccont where contno =c.contno),"
//    		 + " (select (select name from laagent where agentcode =e.agentcode) from lccont e where contno =c.contno),"
//    		 + " (select max(riskcode) from lcpol where mainpolno =polno and contno =c.contno),"
//    		 + " (select codename from ldcode where codetype ='operatepos' and code =c.operatepos),"
//    		 + "  '',''"
//    		 + " from lcissuepol c where 1=1"
//    		 + " and c.prtseq not in (select prtseq from loprtmanager)"
//             + " and c.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and c.ManageCom like '" + comcode + "%%'"
//             + getWherePart('c.Makedate','StartTime','>=')
//             + getWherePart('c.Makedate','EndTime','<=')
//             //+ getWherePart('a.backobjtype','BackObj')
//             //+ getWherePart('c.operatepos','OperatePos')
//             //+ getWherePart('c.errflag','IsRecord');
//             //+ getWherePart('a.PrtNo','PrtNo')
//             //+ getWherePart('a.RiskCode','RiskCode')
//             //+ getWherePart('a.InsuredName','InsuredName')
//             //+ getWherePart('d.salechnl','SaleChnl')
//             //+ getWherePart('a.agentcode','AgentCode')
//          if(fm.RiskCode.value !=null&&fm.RiskCode.value !=""){
//			   strSQL_2 =strSQL_2+" and (select max(riskcode) from lcpol where mainpolno=polno and contno =c.contno)='"+fm.RiskCode.value+"'";       
//          }
//          if(fm.SaleChnl.value !=null&&fm.SaleChnl.value !=""){
//              strSQL_2 =strSQL_2+ " and (select salechnl from lccont where contno =c.contno)='"+fm.SaleChnl.value+"' ";
//          }
//          if(fm.BackObj.value !=null&&fm.BackObj.value!=""){
//            if(fm.BackObj.value =="1"){
//               strSQL_2 =strSQL_2+" and c.backobjtype in ('1','2')";
//            }else{
//               strSQL_2 =strSQL_2+" and c.backobjtype ='"+fm.BackObj.value+"'";
//            }
//          }
//          if(fm.OperatePos.value !=null&&fm.OperatePos.value!=""){
//             if(fm.OperatePos.value =="1"){
//                strSQL_2 =strSQL_2+" and c.operatepos in ('1','5')";
//             }else{
//                strSQL_2 =strSQL_2+" and c.operatepos ='6'";
//             }
//          }
//          if(fm.IsRecord.value !=null&&fm.IsRecord.value!=""){
//             if(fm.IsRecord.value =="Y"){
//                strSQL_2 =strSQL_2+" and c.issuetype in (select code from ldcodemod where codetype='Question' and recordquest='Y') ";
//             }else if(fm.IsRecord.value =="N"){
//                strSQL_2 =strSQL_2+" and c.issuetype in (select code from ldcodemod where codetype='Question' and recordquest='N') ";
//             }
//          }
//          if(fm.AgentCode.value !=null&&fm.AgentCode.value!=""){
//                strSQL_2 =strSQL_2+" and (select agentcode from lccont where contno=c.contno) ='"+agentcode+"'";
//          }
          //var strSQL =strSQL_1+strSQL_2;
          var strSQL =strSQL_1;
    //�ж�����
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
}

function queryAgent()
{
	//add by wangyc
	//reutrn;
	
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
//   	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' and a.managecom='"+document.all("ManageCom").value+"'";
   
	var sqlid1="FaultyReportLisSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("f1print.FaultyReportLisSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(cAgentCode);//ָ������Ĳ���
	mySql1.addSubPara(document.all("ManageCom").value);//ָ������Ĳ���
	var strSQL=mySql1.getString();
   	
   	//alert(strSQL);
    var arrResult = easyExecSql(strSQL); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][8];
    	//fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][3]+"]");
    }
    else
    {
      fm.AgentCode.value ="";
    	//fm.BranchAttr.value ="";
    	//fm.AgentGroup.value ="";
  	  fm.AgentName.value ="";
     alert("����Ϊ:["+cAgentCode+"]��ҵ��Ա�����ڣ����߲��ڸù�������£������Ѿ���ְ�� ��ȷ��!");
    }
	}
}

function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
	fm.AgentCode.value = arrResult[0][0];
	//fm.BranchAttr.value = arrResult[0][10];
	//fm.AgentGroup.value = arrResult[0][1];
	fm.AgentName.value = arrResult[0][3];
	//fm.AgentManageCom.value = arrResult[0][2];
	//fm.ManageCom.value=arrResult[0][2];
	//showOneCodeName('comcode','ManageCom','ManageComName');
	//showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	}
}

function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

    showInfo.close();
    if (FlagStr == "Fail" )
    {
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
    else
    {
    	showDiv(inputButton,"false");
    }
}
//������ʼ���ڽ��в�ѯ��Ҫ�����ڷ�Χ�ڵ����κ���
function PrintNoticeBill()
{

    //if((fm.IssueDate.value==""))
    //{
    //	alert("�����뷢������!!!");
    //		return;
    //}
    var ManageCom =fm.ManageCom.value;
    var sDate = document.all('StartTime').value;
	var eDate = document.all('EndTime').value;
    if(ManageCom!=null&&ManageCom!=""){
	//if(ManageCom.length<4){
	//	alert("�������ӦΪ4λ��8λ��");
	//	return;
	//}  
	}else{
	  alert("��¼����������");
	  return ;
	}
	//if(document.all('PrtNo').value=="")
	//{
	 if (sDate == "")
	  {
			alert("�����뿪ʼ���ڣ�");
			return;
	  }
	  if (eDate == "")
	  {
			alert("������������ڣ�");
			return;
  	  }

    
 
    	
    	var i = 0;
    	//var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    	
    	fm.action = './FaultyReportLisSave.jsp';
    	fm.target="f1print";
    	document.getElementById("fm").submit(); //�ύ
    	
    
}


function afterQuery(arrResult)
{
  
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
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
    else
    {
  		parent.fraMain.rows = "0,0,0,0,*";
    }
}
function queryCom()
{
	showInfo = window.open("../certify/AgentTrussQuery.html");
	}
function afterQuery(arrResult)
{
  if(arrResult!=null)
  { 	
	    fm.AgentGroup.value = arrResult[0][3];
	    
	}
}
