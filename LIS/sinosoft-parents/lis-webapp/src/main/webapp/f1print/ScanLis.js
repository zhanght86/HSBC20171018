

var arrDataSet 
var showInfo;
var mDebug="0";
var FlagDel;
var turnPage = new turnPageClass();


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
			var iHeight=250;     //�������ڵĸ߶�; 
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

function getcodedata2()
{
//	var sql="select subtype,subtypename from es_doc_def where busstype  like 'TB%'";
	
	var sqlid0="ScanLisSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.ScanLisSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	var sql=mySql0.getString();
	
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("��ѯ���ִ���:",sql);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    document.all("SubType").CodeData=tCodeData;
	
}

//������ʼ���ڽ��в�ѯ��Ҫ�����ڷ�Χ�ڵ����κ���
function PrintNoticeBill()
{
    //if((fm.ManageCom.value=="")||(fm.ManageCom.value=="null"))
    //{
    //	alert("��ѡ��������!!");
    //	return ;
    //}
    if(fm.ManageCom.value!=null&&fm.ManageCom.value!=""){
      //if(fm.ManageCom.value.length<4){
      //   alert("�������ӦΪ4-8λ��");
      //   return false;
      //}
    }else{
      alert("��¼����������");
      return false;
    }
    
    if((fm.IssueStartDate.value=="")||(fm.IssueStartDate.value=="null"))
    {
    	alert("��ѡ����ʼ����!!");
    	return ;
    }
    
    if((fm.IssueEndDate.value=="")||(fm.IssueEndDate.value=="null"))
    {
    	alert("��ѡ���������!!");
    	return ;
    }
    
    //if((fm.BusiType.value=="")||(fm.BusiType.value=="null"))
    //{
    //	alert("��ѡ��ҵ������!!");
    //	return ;
    //}
    
    //if((fm.BusiPaperType.value=="")||(fm.BusiPaperType.value=="null"))
    //{
    //	alert("��ѡ��֤����!!");
    //	return ;
   // }
    
    
    	var i = 0;
    	//var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    	
    	fm.action = './ScanLisSave.jsp';
    	fm.target="f1print";
    	document.getElementById("fm").submit(); //�ύ
    	
}

function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	//var sDate = document.all('StartDate').value;
	//var eDate = document.all('EndDate').value;
	
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
	
	if(ManageCom!=null&&ManageCom!=""){
	//if(ManageCom.length<4){
	//	alert("�������ӦΪ4λ��8λ��");
	//	return;
	//}  
	}else{
	   alert("��¼����������");
	   return false;
	}
	
	if(fm.IssueStartDate.value==null||fm.IssueStartDate.value==""
	      ||fm.IssueEndDate.value==null||fm.IssueEndDate.value==""){
	   alert("��ʼ���ڻ��������û��¼�룡");
	   return false;
	}
	//if(document.all('PrtNo').value=="")
	//{
	// if (sDate == "")
	//  {
	//		alert("������¼����ʼ���ڣ�");
	//		return;
	//  }
	 // if (eDate == "")
	 // {
	//		alert("������¼���������ڣ�");
	//		return;
  	//}
  //}
	initPolGrid();
	// ��дSQL���
	//var strSQL = " select a.PrtNo,(select RiskName from LMRiskApp where RiskCode=a.RiskCode) riskname,(select nvl(codename,a.salechnl) from ldcode where codetype='salechnl' and code=a.salechnl) salechnl ,a.managecom,(select Name from LAAgent where agentcode=a.agentcode) agentname,a.insuredname insuredname,a.appntname appntname,(select decode(min(c.makedate),null,'��ɨ���',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(a.prtno)) ScanDate,(select decode(max(d.makedate),null,'δ��',to_char(max(d.makedate),'yyyy-mm-dd')) from lcuwsub d where d.ProposalNo=a.ProposalNo and uwflag='8') UWNoticeDate,(select decode(max(e.makedate),null,'δ��',to_char(max(e.makedate),'yyyy-mm-dd')) from LCPENotice e where e.ProposalNo = a.ProposalNo) HealthDate,(select decode(max(f.makedate),null,'δ��',to_char(max(f.makedate),'yyyy-mm-dd')) from LCRReport f where f.polno = a.ProposalNo) SurveyDate,getQuestSendDate(a.PolNo,'2') AgentQuestSendDate,getQuestSendDate(a.PolNo,'4') ManageQuestSendDate"
//	var strSQL = " select a.doccode,a.docid, a.managecom,a.subtype,"
//             + " a.numpages,a.makedate, a.scanoperator,a.docremark "
//             + "   from es_doc_main a where 1=1"
//             + getWherePart('a.Makedate','IssueStartDate','>=')
//             + getWherePart('a.Makedate','IssueEndDate','<=')
//             + getWherePart('a.scanoperator','OperaterNo')
             //+ getWherePart('a.subtype','SubType')
             //+ getWherePart('a.operator','BusiPaperType')
             //+ getWherePart('a.PrtNo','PrtNo')
             //+ getWherePart('a.RiskCode','RiskCode')
             //+ getWherePart('a.InsuredName','InsuredName')
             //+ getWherePart('d.salechnl','SaleChnl')
             //+ getWherePart('a.agentcode','AgentCode')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.ManageCom like '" + comcode + "%%'"
             //+ " order by a.makedate ";
             
           //  alert(fm.ScanType.vlaue);
             /*
             if(fm.SubType.value!=null&&fm.SubType.vlaue!=''){
             alert(1);
               strSQL=strSQL+" and a.busstype like 'TB%' and a.subtype ='"+fm.SubType.value+"'";
             }else{
             alert(2);
               strSQL =strSQL+" and a.busstype like 'TB%'";
             }*/
//              strSQL =strSQL+" and a.busstype like 'TB%'"
//                     + getWherePart('a.subtype','SubType');
      var tScanType = document.all('ScanType').value;
      var tScanTypeid1 ="";
      var tScanTypeid2 ="";
      var tScanTypeid3 ="";
      var tScanTypeid4 ="";
       if(tScanType =="����"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) = '8611' order by a.makedate ";
          tScanTypeid1 ="1";
       }else if(tScanType =="�н�"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) = '8621' order by a.makedate ";
          tScanTypeid2 ="1";
       }else if(tScanType =="����"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) = '8616' order by a.makedate ";
          tScanTypeid3 ="1";
       }else if(tScanType =="����"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) in ('8615','8625','8635') order by a.makedate";
          tScanTypeid4 ="1";
//       }else {
//          strSQL = strSQL+" order by a.makedate ";
       }
       
       
	  	var  IssueStartDate1 = window.document.getElementsByName(trim("IssueStartDate"))[0].value;
	  	var  IssueEndDate1 = window.document.getElementsByName(trim("IssueEndDate"))[0].value;
	  	var  OperaterNo1 = window.document.getElementsByName(trim("OperaterNo"))[0].value;
	  	var  SubType1 = window.document.getElementsByName(trim("SubType"))[0].value;
		var sqlid1="ScanLisSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.ScanLisSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(IssueStartDate1);//ָ������Ĳ���
		mySql1.addSubPara(IssueEndDate1);//ָ������Ĳ���
		mySql1.addSubPara(OperaterNo1);//ָ������Ĳ���
		mySql1.addSubPara(document.all("ManageCom").value );//ָ������Ĳ���
		mySql1.addSubPara(comcode);//ָ������Ĳ���
		mySql1.addSubPara(SubType1);//ָ������Ĳ���
		mySql1.addSubPara(tScanTypeid1);//ָ������Ĳ���
		mySql1.addSubPara(tScanTypeid2);//ָ������Ĳ���
		mySql1.addSubPara(tScanTypeid3);//ָ������Ĳ���
		mySql1.addSubPara(tScanTypeid4);//ָ������Ĳ���
		var strSQL=mySql1.getString();
       
       
    //�ж�����
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, PolGrid);    
	
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

function afterCodeSelect( cCodeName, Field )
{
	if(cCodeName=="busitype"){
    if(Field.value=="TB"){
      document.all("BusiPaperType").CodeData="0^00|ȫ������^01|Ͷ������^02|֪ͨ����^03|������֤��";	
    }
    
		return;
	}
}

