//�������ƣ�UWManuSpec.js
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var turnPage_OldRisk = new turnPageClass();
var turnPage_AddFee = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var operate = "";
var proposalno = "";
var serialno = "";

//�ύ�����水ť��Ӧ����
function submitForm(tflag)
{
	if(!checkUWState('�޸���Լ��Ϣ'))
		return false;
		
	fm.action="./UWChangeRiskPlanChk.jsp";
	if(tflag=="1")
	   {
	   	var tSpecType=fm.HealthSpecTemp.value;
	   	var tSpecCode=fm.SpecTemp.value;
	   	var tRemark=fm.Remark.value;
	   	if(trim(tSpecType)=="null"||trim(tSpecType)==null||trim(tSpecType)=="")
	   	{
	   		alert("����ϵͳ���벻��Ϊ��!");
	   		return false;
	   		}
	   	if(trim(tSpecCode)=="null"||trim(tSpecCode)==null||trim(tSpecCode)=="")
	   	{
	   		alert("��Լ���ݱ��벻��Ϊ��!");
	   		return false;
	   		}
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("��Լ���ݲ���Ϊ��!");
	   		return false;
	   		}
	     var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("��ѡ���·����!");
	   		return false;
	   	}
	    fm.operate.value = "INSERT"
	    //alert(operate);
	    //return;
	   }
	else if(tflag=="2")   
		 {
		 	var tRemark=fm.Remark.value;
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("��Լ���ݲ���Ϊ��!");
	   		return false;
	   	}
	 	 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("��ѡ��Ҫ�޸ĵ���Լ��Ϣ��");
		 	  	return;
		 	  } 
		 	  //alert(UWSpecGrid.getRowColData(tSelNo,3));
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="2"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("�ѷ��ͻ����ѻ���״̬�����޸ģ�");
		 	    return false;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
     // alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);
			fm.operate.value = "UPDATE";
			fm.SpecCode.value=UWSpecGrid.getRowColData(tSelNo,9);
			fm.SpecType.value=UWSpecGrid.getRowColData(tSelNo,8);;
			//alert(operate);
			//return;
		 }
  else if(tflag=="3")
  	 {
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("��ѡ��Ҫɾ������Լ��Ϣ��");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)!="x") {
		 	    alert("�ѷ���״̬����ɾ����");
		 	    return false;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "DELETE";
  	  //alert(operate);
  	  //return;
     }
      else
  	 {  	    
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("��ѡ��Ҫ�޸��·���ǵ���Լ��Ϣ��");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("ֻ����δ���ͻ����ѻ���״̬�����޸��·���ǣ�");
		 	    return;	
		 	  }
		 	   var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("��ѡ���·����!");
	   		return false;
	   	}
		 	  if (!confirm('ȷ���޸�?'))
			{
			return false;
			}
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "UPDATE";
  	  //alert(operate);
  	  //return;
     }
	   
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="�����ɹ���";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //ִ����һ������
  }
  initUWSpecGrid();
  initPolAddGrid();
  initCancleGiven(mContNo,mInsuredNo);
  QueryPolSpecGrid(mContNo,mInsuredNo);
  queryRiskAddFee(mContNo,mInsuredNo);
  initOldRislPlanGrid('');
  queryOldRiskPlan();
  onRiskTabChange(1);
  //alert('1');
  //tongmeng 2009-03-10 modify
  //ˢ���ⲿ��������
  top.opener.getInsuredDetail();
  //alert('2');
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="�����ɹ���";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //ִ����һ������
    top.window.close();
  }
 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
{
	unlockScreen('lkscreen');  
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="�����ɹ���";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //ִ����һ������
  }
  //initUWSpecGrid();
 // QueryPolSpecGrid(mContNo,mInsuredNo);
  //queryRiskAddFee(mContNo,mInsuredNo);
  //initOldRislPlanGrid('');
  //queryOldRiskPlan();
  //onRiskTabChange(1);
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


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}

function QueryPolSpecGrid(tContNo,tInsuredNo)
{
	// ��ʼ�����
	// ��дSQL���
	//alert("QueryPolSpecGrid"+tContNo);
	var strSQL = "";
	var i, j, m, n;	
	var tProposalContNo;
	//tongmeng 2008-10-08 add
	//���ú�ͬ��Լ��
//				 strSQL = "select a,b,c,case c when 'x' then 'δ����' "
//			                          + " when '0' then '�ѷ���δ��ӡ'"
//			                          + " when '1' then '�Ѵ�ӡδ����'"
//                                + " when '2' then '�ѻ���'"
//                         + " end,"
//                         + " d,e,f,g,h,n,p,q,w,(case w when 'Y' then '�·�' else '���·�' end)"
//                + "   from (select s.speccontent as a,"
//                + "                s.serialno as b,"
//                + "                (case when (select min(stateflag) "
//                + "                            from loprtmanager p"
//                + "                            where p.oldprtseq = s.prtseq) is not null then (select min(stateflag) "
//                + "                            from loprtmanager p"
//                + "                            where p.oldprtseq = s.prtseq)  else 'x' end) as c,"
//                + "                s.proposalcontno as d,"
//                + "                s.serialno as e,"
//                + "                s.customerno f,s.spectype g,s.speccode h,s.specreason n,(concat(concat(s.makedate,' '),s.maketime)) p,s.operator q ,s.needprint w"
//                + "                from lccspec s "
//                + "                where s.contno = '"+tContNo+"' and s.customerno = '"+tInsuredNo+"' and spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp'))";
//               
				     var mySql1=new SqlClass();
				     var sqlid1="UWChangeRiskPlanSql1";
					 mySql1.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
					 mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
					 mySql1.addSubPara(tContNo);//ָ������Ĳ���
					 mySql1.addSubPara(tInsuredNo);//ָ������Ĳ���
					 strSQL=mySql1.getString();
				 
//	prompt("",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = UWSpecGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}


function getSpecGridCho2()
{
    var tSelNo = UWSpecGrid.getSelNo()-1;
	//alert('tSelNo'+tSelNo);
	var proposalcontno = UWSpecGrid.getRowColData(tSelNo,5);
	var serialno = UWSpecGrid.getRowColData(tSelNo,2);
	//var tContent = fm.Remark.value;
//	strSQL = "select spectype ,speccode ,"
//						+ " ( select codename from ldcode where codetype='healthspcetemp' and code=spectype),"
//                         + " ( select noti from LCCSpecTemplet where templetcode=speccode ),"
//	                      +" needprint ,(case needprint when 'Y' then '�·�' else '���·�' end),speccontent"
//                + "     from lccspec  "
//                + "     where proposalcontno = '"+proposalcontno+"' and serialno='"+serialno+"'";
	
	 var mySql2=new SqlClass();
     var sqlid2="UWChangeRiskPlanSql2";
	 mySql2.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	 mySql2.addSubPara(proposalcontno);//ָ������Ĳ���
	 mySql2.addSubPara(serialno);//ָ������Ĳ���
	 var strSQL=mySql2.getString();
     
    var arrResult = easyExecSql(strSQL);
    if(arrResult!=null)
    {
			fm.SpecTemp.value=arrResult[0][1];
			fm.HealthSpecTemp.value=arrResult[0][0];
			fm.SpecTempname.value=arrResult[0][3];
			fm.HealthSpecTempName.value=arrResult[0][2];
			fm.NeedPrintFlag.value=arrResult[0][4];
			fm.IFNeedFlagName.value=arrResult[0][5];
          fm.Remark.value = arrResult[0][6]; 
    }
	
	
  
	
}

function getClickSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   //alert(tManageCom.length);
  // alert(sql_temp);
   showCodeList('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function getClickUpSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   showCodeListKey('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function queryInsuredInfo(tContNo,tInsuredNo)
{
		//����,�Ա�,����,��Ͷ���˹�ϵ,���������˹�ϵ,ְҵ����,ְҵ���,��ְ
//    strSQL =" select name,sex,(select polapplydate from lccont where contno=a.contno),birthday,relationtoappnt,"
//           + " relationtomaininsured,occupationcode,occupationtype, "
//           + " pluralitytype ,(select substr(agentcode,5) from lccont where contno=a.contno)from lcinsured a "
//           + " where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"'";
    var strSQL="";
    var mySql3=new SqlClass();
    var sqlid3="UWChangeRiskPlanSql3";
	 mySql3.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	 mySql3.addSubPara(tContNo);//ָ������Ĳ���
	 mySql3.addSubPara(tInsuredNo);//ָ������Ĳ���
	 strSQL=mySql3.getString();
    
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured(arrResult);
        if(arrResult[0][9]=="999999")
        divOperator.style.display=""; 
    }
    	//��ѯ�б��ƻ�������ۺͼӷ�ԭ�� 2008-12-1 ln add
//    strSQL =" select uwidea from LCIndUWMaster "
//           + " where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"'";
    
    var mySql4=new SqlClass();
    var sqlid4="UWChangeRiskPlanSql4";
	 mySql4.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	 mySql4.addSubPara(tContNo);//ָ������Ĳ���
	 mySql4.addSubPara(tInsuredNo);//ָ������Ĳ���
	 strSQL=mySql4.getString();
     
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        fm.UWIdea.value=arrResult[0][0];
    }
}

function DisplayInsured(arrResult)
{
    try{document.all('InsuredName').value= arrResult[0][0]; }catch(ex){};
    try{document.all('InsuredSex').value= arrResult[0][1]; }catch(ex){};
    try{document.all('RelationToAppnt').value= arrResult[0][4]; }catch(ex){};
    try{document.all('RelationToMainInsured').value= arrResult[0][5]; }catch(ex){};
   // alert(arrResult[0][8]);
    try{document.all('InsuredOccupationCode').value= arrResult[0][6]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][7]; }catch(ex){};
    try{document.all('InsuredPluralityType').value= arrResult[0][8]; }catch(ex){};
    try{document.all('InsuredAge').value= calPolCustomerAge(arrResult[0][3],arrResult[0][2]); }catch(ex){};
    quickGetName('occupationCode',fm.InsuredOccupationCode,fm.InsuredOccupationCodeName);
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
    quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
}

function calPolCustomerAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("����"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("Ͷ������"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//��ǰ�´��ڳ�����
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//��ǰ��С�ڳ�����
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}


var cacheWin=null;
function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObjEx(strCode,showObjc,showObjn);
}

function queryOldRiskPlan()
{
//	var tSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt,mult,prem,"
//	         + " concat(insuyear,insuyearflag),concat(payendyear,payendyearflag),payintv,uwflag,polno,mainpolno,currency "
//	         + " from lcpol a "
//	         + " where contno='"+mContNo+"' and insuredno='"+mInsuredNo+"' order by mainpolno,polno ";
//	
	var mySql5=new SqlClass();
    var sqlid5="UWChangeRiskPlanSql5";
	 mySql5.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	 mySql5.addSubPara(mContNo);//ָ������Ĳ���
	 mySql5.addSubPara(mInsuredNo);//ָ������Ĳ���
	 var tSQL=mySql5.getString();
     
	 turnPage_OldRisk.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage_OldRisk.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage_OldRisk.arrDataCacheSet = decodeEasyQueryResult(turnPage_OldRisk.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage_OldRisk.pageDisplayGrid = OldRislPlanGrid;    
          
  //����SQL���
  turnPage_OldRisk.strQuerySql     = tSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage_OldRisk.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage_OldRisk.getData(turnPage_OldRisk.arrDataCacheSet, turnPage_OldRisk.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage_OldRisk.pageDisplayGrid);
	
}
}

//���л���ҳʱ,�������ҵ���߼�
function onRiskTabChange(tPage)
{
	var tOldSelNo = OldRislPlanGrid.getSelNo();
	//����л�����������ּƻ�ʱ,��У���Ƿ��Ѿ�ѡ��������
	if(tPage=='2')
	{
		if(!checkUWState('������ռƻ�'))
			return false;
		
		if(tOldSelNo==null || tOldSelNo<=0)
		{
			alert("����ѡ��һ�����ֺ������б��ƻ������");
			//onRiskTabChange(1);
			return;
		}		
		
		//��ʼ��ʼ��ѡ������ֽ���
		//��ȡSelPolNo 
	    var tSelPolNo = OldRislPlanGrid.getRowColData(tOldSelNo-1,10);  
	    //У��������Ƿ��мӷѣ����������б��ƻ����
//	    var strSQL = "select * from lcprem where polno='"+tSelPolNo+"' and payplancode like '000000%%'";
	    
	    var mySql6=new SqlClass();
	    var sqlid6="UWChangeRiskPlanSql6";
		 mySql6.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
		 mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		 mySql6.addSubPara(tSelPolNo);//ָ������Ĳ���
		 var strSQL=mySql6.getString();
	    
		var arrResult=easyExecSql(strSQL);
		if(arrResult!=null)
		{
			alert("����ɾ�������ּӷ���Ϣ�������б��ƻ������");
			//onRiskTabChange(1);
			return;
		}
		//alert('2');
		// lockScreen('lkscreen');
	//	alert("tOldSelNo:"+tOldSelNo);
		//alert(tSelPolNo);
		//	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1"+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled+"&specScanFlag="+specScanFlag;
		document.getElementById("NewRiskPlan").src = "./UWManuChangeRiskMain.jsp?LoadFlag=25&ContType=1&scantype=scan&hh=1&checktype=1&ContNo="+mContNo+"&PrtNo="+mContNo+"&InsuredChkFlag=true&AppntChkFlag=false&SelPolNo="+tSelPolNo+"&NewChangeRiskPlanFlag=Y&PageFlag=2";
	}
	//����������
	if(tPage =='3'){
			if(!checkUWState('�޸ĸ�������'))
				return false;
			document.getElementById("NewRiskPlan").src = "./UWManuChangeRiskMain.jsp?LoadFlag=25&ContType=1&scantype=scan&hh=1&checktype=1&ContNo="+mContNo+"&PrtNo="+mContNo+"&InsuredChkFlag=true&InsuredNo="+mInsuredNo+"&NewChangeRiskPlanFlag=Y&PageFlag=3";
	}
	//����tab���л�
	if(tPage=='1')
	{
		//
		queryOldRiskPlan();
		tab1c.style.display="";
		tab2c.style.display="none";
		document.getElementById("NewRiskPlan").src = " ";
	}
	else
	{
		tab1c.style.display="none";
		tab2c.style.display="";
	}
	
	//unlockScreen('lkscreen');
}

//tongmeng 2008-10-09 add
//�޸����ֺ˱�����
function makeRiskUWState()
{
	if(!checkUWState('�޸����ֺ˱�����'))
		return false;
		
	//У���Ƿ������ֱ�ѡ��
	var tSelNo = OldRislPlanGrid.getSelNo();
  if(tSelNo<=0)
 	{
 			alert("��ѡ�����ֱ�����");
 			return;
 	}
 //	alert('1');
 		var polno = OldRislPlanGrid.getRowColData(tSelNo - 1,10);
 		var mainpolno = OldRislPlanGrid.getRowColData(tSelNo - 1,11);
 		var uwstate = OldRislPlanGrid.getRowColData(tSelNo - 1,9);
 		fm.PolNo.value=polno;
 		fm.uwstate.value = uwstate;
 		document.all('flag').value = "risk";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./InsuredUWInfoChk.jsp";
	 var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//alert('3');
	document.getElementById("fm").submit();
}

//ln 2008-12-1 add
//�б��ƻ�������ۼ��ӷ�ԭ��¼��
function makeRiskUWIdea()
{	
	if(fm.UWIdea.value.length>255){
		alert("�б��ƻ�������ۼ��ӷ�ԭ��¼�������");
		return;
	}
	if(!checkUWState('�޸ĳб��ƻ�������ۼ��ӷ�ԭ��'))
		return false;
 //	alert('1');
 		document.all('flag').value = "Insured";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./InsuredUWInfoChk.jsp";
	 var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//alert('3');
	document.getElementById("fm").submit();
}

//ln 2009-1-7 add
//ȡ����ǰ������Լ
function cancleGiven1()
{
	fm.action="./CancleGivenChk.jsp";
	 var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //alert('3');
	document.getElementById("fm").submit();
}

//ln 2009-1-7 add
//ȡ����ǰ������Լ
function initCancleGiven(tContNo,tInsuredNo)
{
//	var strSQL = "select 1 from lcpol where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"' "
//			+ " and riskcode in (select riskcode from lmriskapp where cancleforegetspecflag is not null and cancleforegetspecflag='1')"
//	        + " and (cancleforegetspecflag is null or cancleforegetspecflag<>'1')";	      
//	
	var mySql7=new SqlClass();
    var sqlid7="UWChangeRiskPlanSql7";
	 mySql7.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	 mySql7.addSubPara(tContNo);//ָ������Ĳ���
	 mySql7.addSubPara(tInsuredNo);//ָ������Ĳ���
	 var strSQL=mySql7.getString();
	 
    var str  = easyExecSql(strSQL);;
    if(str != null)
    	fm.cancleGiven.disabled = false;
    else
        fm.cancleGiven.disabled = true;
}

//����Ϊ�ӷѳ�������ú���
// ��ѯ��ť
function initlist(tContNo,tInsuredNo)
{
	// ��дSQL���
	k++;
	var strSQL = "";
//	strSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode,"
//	       + " firstpaydate,payenddate,polno,(select mainpolno from lcpol where polno=a.polno) "
//	       + " from lcduty a,lmriskduty b where a.dutycode=b.dutycode "
//         + " and a.polno in (select polno from lcpol where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"' ) ";
	
	var mySql8=new SqlClass();
    var sqlid8="UWChangeRiskPlanSql8";
	 mySql8.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	 mySql8.addSubPara(tContNo);//ָ������Ĳ���
	 mySql8.addSubPara(tInsuredNo);//ָ������Ĳ���
	 var strSQL=mySql8.getString();
	 
    str  = easyQueryVer3(strSQL, 1, 0, 1);
    return str;

}

function checkUWState(message)
{

//	var tSQL = "select '1' from lwmission w ,lccuwmaster b where w.missionprop1 = '"+document.all('PrtNo').value+"' "
//             + " and w.missionprop1=b.contno and b.uwstate='4'"
//             + " and activityid in (select activityid from lwactivity  where functionid ='10010028') ";
	
	var mySql9=new SqlClass();
    var sqlid9="UWChangeRiskPlanSql9";
	 mySql9.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	 mySql9.addSubPara(document.all('PrtNo').value);//ָ������Ĳ���
	 var strSQL=mySql9.getString();
	
	var arr = easyExecSql( strSQL );
	 if (arr) 
	   {
	         alert("�ú�ͬ��ǰ����4״̬,������"+message+"��");   
	         return false;   
	   }
	return true;
}

function afterCodeSelect( cCodeName, Field ) {
//PlanPay
//alert(cCodeName);
		if(cCodeName == "PlanPay")
		{
				if(Field.value=='02')
				{
						//��õ�ǰ���������ֱ���
						var tSelNo = PolAddGrid.getSelNo()-1;
						var t = PolAddGrid.lastFocusRowNo;
					//	alert('t:'+t+":"+tSelNo);
						
						//alert('tSelNo'+tSelNo);
						if(tSelNo<0)
						{
							/*
							alert('����ѡ��һ����¼!');
							return false;
							*/
							tSelNo = t;
						}
						//111801,111802,121705,121704
						var riskcode = PolAddGrid.getRowColData(tSelNo,1);
						if(riskcode==null||riskcode=='')
						{
							alert('����ѡ������!');
							return false;
						}
						else if(riskcode=='111801'||riskcode=='111802'
							||riskcode=='121705'||riskcode=='121704'
							||riskcode=='121701'||riskcode=='121702'
							//test
							//||riskcode=='112213'
							)
							{
								 PolAddGrid.setRowColData(tSelNo,5,'02',PolAddGrid);
								 //var tSQL = "select medrate from ldoccupation where occupationcode='"+document.all('InsuredOccupationCode').value+"' ";
								 
									var mySql10=new SqlClass();
								    var sqlid10="UWChangeRiskPlanSql10";
									 mySql10.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
									 mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
									 mySql10.addSubPara(document.all('InsuredOccupationCode').value);//ָ������Ĳ���
									 var tSQL=mySql10.getString();								 
								 
								 var tStr1  = easyExecSql(tSQL);
								 //alert(tStr1);
								 if(tStr1!=null)
								 {
								 	 PolAddGrid.setRowColData(tSelNo,6,tStr1[0][0],PolAddGrid);
								 }
								 return ;
							}
				}
		}

		if( cCodeName == "addfeetype" ) {
			
			if(Field.value=='02')
			{
				
			}
		}
}

//�ӷѱ���
function addFeeSave()
{
	if(!checkUWState('�޸ļӷ���Ϣ'))
		return false;
   
    var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("��ѡ����Ҫ����ļӷѼ�¼!");
		 return false;
	}
	
	var tAddFeeType = PolAddGrid.getRowColData(tSelNo,5);
    var tAddFeeNum = PolAddGrid.getRowColData(tSelNo,6);
    var tAddFeeMoney = PolAddGrid.getRowColData(tSelNo,7);
    var tAddFeeCur = PolAddGrid.getRowColData(tSelNo,15);
     if(tAddFeeType=='' || tAddFeeType == null || tAddFeeType =="null"){
    	alert("��ѡ��ӷѷ�ʽ!");
    	return false;
    }
    if(tAddFeeMoney!=''&&tAddFeeMoney<0)
    {
    	 alert("�ӷѽ���Ϊ��!");
    	 return false;
    }
    if(tAddFeeMoney=='' || tAddFeeMoney == null || tAddFeeMoney =="null"){
    	alert("��¼��ӷѽ��!");
    	return false;
    }
    if(tAddFeeCur=='' || tAddFeeCur == null || tAddFeeCur =="null"){
    	alert("��ѡ�����!");
    	return false;
    }
  //�ӷѷ�ʽ
    if((tAddFeeType == "01") && ((tInsuredSumLifeAmnt-300000>0) && (tAddFeeNum-150>0)
		       || ((tInsuredSumHealthAmnt-200000>0) && (tAddFeeNum-125>0))))	
	/*
    alert(tAddFeeType);
    alert(tAddFeeNum);
    alert("tInsuredSumLifeAmnt"+tInsuredSumLifeAmnt);
    alert("tInsuredSumHealthAmnt"+tInsuredSumHealthAmnt);
    if((tAddFeeType == "01") && ((parseFloat(tInsuredSumLifeAmnt)-100000>0) && (parseFloat(tAddFeeNum)-150>0)
		       || ((parseFloat(tInsuredSumHealthAmnt)-100000>0) && (parseFloat(tAddFeeNum)-125>0))))  //������*/
		       {
		       		 alert("�˵����ٱ��ʱ���");
		       		 document.all('UpReporFlag').value = "Y";
		       }
    
	fm.action="./UWManuChangeRiskAddChk.jsp";
	var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit();
}

//��ʼ����ѯ�ӷ�����
function queryRiskAddFee(tContNo,tInsuredNo)
{
//	var tSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode, "
//	         + " a.payplantype,a.addfeedirect,a.suppriskscore,a.prem,'',a.paystartdate,a.payenddate,'',a.polno,"
//	         + " c.mainpolno,a.payplancode,a.currency "
//	         + " from lcprem a,lmriskduty b,lcpol c  where a.dutycode=b.dutycode and a.polno=c.polno"
//	         + " and c.contno='"+tContNo+"' and c.insuredno='"+tInsuredNo+"' "
//	         + " and a.payplancode like '000000%%' ";
	
	var mySql11=new SqlClass();
    var sqlid11="UWChangeRiskPlanSql11";
	 mySql11.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	 mySql11.addSubPara(tContNo);//ָ������Ĳ���
	 mySql11.addSubPara(tInsuredNo);//ָ������Ĳ���
	 var tSQL=mySql11.getString();								 
 
	
	// turnPage_AddFee.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);          
	//�ж��Ƿ��ѯ�ɹ�
	turnPage_AddFee.queryModal(tSQL,PolAddGrid,0,0,100);
  /*if (turnPage_AddFee.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage_AddFee.arrDataCacheSet = decodeEasyQueryResult(turnPage_AddFee.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage_AddFee.pageDisplayGrid = PolAddGrid;    
          
  //����SQL���
  turnPage_AddFee.strQuerySql     = tSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage_AddFee.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage_AddFee.getData(turnPage_AddFee.arrDataCacheSet, turnPage_AddFee.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage_AddFee.pageDisplayGrid);

}*/
}

//�Զ�����ӷ���Ϣ
function AutoCalAddFee(span)
{
	spanObj = span;
    var CalAddFeeNum = spanObj.replace(/spanPolAddGrid/,"");
	
  //alert(document.all( spanObj ).all( 'PolAddGrid4' ).value);
  //�����ж���û�мӷ��㷨
  //���ֱ���
  var tRiskCode = document.all( 'PolAddGrid1'+"r" + CalAddFeeNum ).value;
  // alert(tRiskCode);
  //�ӷ����
  var tAddFeeType = document.all( 'PolAddGrid4'+"r" + CalAddFeeNum ).value;
  //�ӷѷ�ʽ
  var tAddFeeMethod = document.all( 'PolAddGrid5'+"r" + CalAddFeeNum ).value;
  //���α���
  var tDutyCode = document.all( 'PolAddGrid3'+"r" + CalAddFeeNum ).value;
  
  if(tRiskCode==null||tRiskCode=='')
  {
  	alert("��ѡ������!");
  	return;
  }
  if(tAddFeeType==null||tAddFeeType=='')
  {
  	alert("��ѡ��ӷ����!");
  	return;
  }
  if(tAddFeeMethod==null||tAddFeeMethod=='')
  {
  	alert("��ѡ��ӷѷ�ʽ!");
  	return;
  }
  
 //  var tSelNo = PolAddGrid.lastFocusRowNo;
 //  alert(tSelNo);
 //alert(mInsuredNo);

  var tSQL = " select 1 from LMDutyPayAddFee where "
           + " riskcode='"+tRiskCode+"' and dutycode='"+tDutyCode+"' and addfeetype='"+tAddFeeType+"' "
           + " and  addfeeobject='"+tAddFeeMethod+"'";
  var mySql12=new SqlClass();
  var sqlid12="UWChangeRiskPlanSql12";
	 mySql12.setResourceName("uw.UWChangeRiskPlanSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	 mySql12.addSubPara(tRiskCode);//ָ������Ĳ���
	 mySql12.addSubPara(tDutyCode);//ָ������Ĳ���
	 mySql12.addSubPara(tAddFeeType);//ָ������Ĳ���
	 mySql12.addSubPara(tAddFeeMethod);//ָ������Ĳ���
	 var tSQL=mySql12.getString();			
	 
   var arrRes =  easyQueryVer3(tSQL, 1, 0, 1);
   //alert("arrRes:"+arrRes);
   if(arrRes==null||!arrRes)
   {
   	  alert("������������мӷѣ����߸�����û�д˼ӷ�����!");
   	  document.all( 'PolAddGrid7'+"r" + CalAddFeeNum ).value='0';
   	  return;
   }
   //׼������
  var tPolNo =  document.all( 'PolAddGrid12'+"r" + CalAddFeeNum ).value;
  var tAddFeePoint = document.all( 'PolAddGrid6'+"r" + CalAddFeeNum ).value;
  if(tAddFeeMethod=='01'&&(tAddFeePoint==null||tAddFeePoint<='0'))
  {
  	alert("��EMֵ�ӷ�,������ӷ�����!");
  	return;
  }
 // if(tAddFeeMethod!='01' && !(tAddFeePoint==null||tAddFeePoint==''))
 // {
 // 	 alert("�����ӷѷ�ʽ,�벻Ҫ¼��ӷ�����!");
 // 	 return;
 // }
  var i = 0;
  var showStr="���ڼ���ӷ����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  lockScreen('lkscreen');  
   //�ύ���ݿ�
  fm.action= "./UWCalHealthAddFeeChk.jsp?AddFeePolNo="+tPolNo+"&AddFeeRiskCode="+tRiskCode+"&AddFeeDutyCode="+tDutyCode+"&AddFeeType="+tAddFeeType+"&AddFeeMethod="+tAddFeeMethod+"&AddFeeInsuredNo="+mInsuredNo+"&AddFeePoint="+tAddFeePoint;
  document.getElementById("fm").submit(); //�ύ
}


//�ӷ�ɾ��
//created by guanwei
function deleteData()
{ 
	if(!checkUWState('�޸ļӷ���Ϣ'))
		return false;
	   
	var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("��ѡ����Ҫɾ���ļӷѼ�¼!");
		 return false;
	}
	
	var tPolNo = PolAddGrid.getRowColData(tSelNo,12);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tPayPlanCode = PolAddGrid.getRowColData(tSelNo,14);
	if(tPayPlanCode==null||tPayPlanCode=='')
	{
		 alert("�ü�¼��û�б���,��ˢ��ҳ�����²�ѯ!");
		 return false;
	}
	//initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./UWManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
    document.getElementById("fm").submit();
  
  //var cPolNo= fm.PolNo2.value ;   
  //alert(cPolNo);
  
}