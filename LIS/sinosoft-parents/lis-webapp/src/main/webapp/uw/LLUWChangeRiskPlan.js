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
var tResourceName="uw.LLUWChangeRiskPlanInputSql";

//�ύ�����水ť��Ӧ����
function submitForm(tflag)
{
	fm.action="./LLUWChangeRiskPlanChk.jsp";
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
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("ֻ����δ���ͻ����ѻ���״̬�����޸ģ�");
		 	    return;	
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
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("ֻ����δ���ͻ����ѻ���״̬����ɾ����");
		 	    return;	
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
  //initCancleGiven(mContNo,mInsuredNo);
  QueryPolSpecGrid(mContNo,mInsuredNo);
  queryRiskAddFee(mContNo,mInsuredNo);
  initOldRislPlanGrid('');
  queryOldRiskPlan();
  onRiskTabChange(1);
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
				 /*strSQL = "select a,b,c,case c when 'x' then 'δ����' "
			                          + " when '0' then '�ѷ���δ��ӡ'"
			                          + " when '1' then '�Ѵ�ӡδ����'"
                                + " when '2' then '�ѻ���'"
                         + " end,"
                         + " d,e,f,g,h,n,p,q,w,decode(w,'Y','�·�','���·�')"
                + "   from (select s.speccontent as a,"
                + "                s.serialno as b,"
                + "                nvl((select stateflag "
                + "                            from loprtmanager p"
                + "                            where p.oldprtseq = s.prtseq),'x') as c,"
                + "                s.proposalcontno as d,"
                + "                s.serialno as e,"
                + "                s.customerno f,s.spectype g,s.speccode h,s.specreason n,(s.makedate||' '||s.maketime) p,s.operator q ,s.needprint w"
                + "                from LLUWSpecMaster s "
                + "                where s.contno = '"+tContNo+"' and s.customerno = '"+tInsuredNo+"' and spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp')"
                + " 			   and s.clmno='"+fm.ClmNo.value+"' and batno='"+fm.BatNo.value+"')";*/
     strSQL = wrapSql(tResourceName,"querysqldes1",[tContNo,tInsuredNo,fm.ClmNo.value,fm.BatNo.value]);          
	
	//prompt("��Լ",strSQL);
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
	/*strSQL = "select spectype ,speccode ,"
						+ " ( select codename from ldcode where codetype='healthspcetemp' and code=spectype),"
                         + " ( select noti from LCCSpecTemplet where templetcode=speccode ),"
	                      +" needprint ,decode(needprint,'Y','�·�','���·�'),speccontent"
                + "     from LLUWSpecMaster  "
                + "     where proposalcontno = '"+proposalcontno+"' and serialno='"+serialno+"'";*/
    strSQL = wrapSql(tResourceName,"querysqldes2",[proposalcontno,serialno]);
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

function queryInsuredInfo(tContNo,tBatNo,tClmNo,tInsuredNo)
{
//	var tInsuredNoSql = "select insuredno from lccont where contno=";
		//����,�Ա�,����,��Ͷ���˹�ϵ,���������˹�ϵ,ְҵ����,ְҵ���,��ְ
    /*strSQL =" select name,sex,(select polapplydate from lccont where contno=a.contno),birthday,relationtoappnt,"
           + " relationtomaininsured,occupationcode,occupationtype, "
           + " pluralitytype ,(select substr(agentcode,5) from lccont where contno=a.contno)from lcinsured a "
           + " where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"'";*/
    strSQL = wrapSql(tResourceName,"querysqldes3",[tContNo,tInsuredNo]);
    var tArrResult=easyExecSql(strSQL);//prompt("",strSQL);
    if(tArrResult!=null)
    {
        DisplayInsured(tArrResult,tContNo,tInsuredNo);
        if(tArrResult[0][9]=="999999")
        divOperator.style.display=""; 
    }
    	//��ѯ�б��ƻ�������ۺͼӷ�ԭ�� 2008-12-1 ln add
    /*strSQL =" select changepolreason from LLCUWMaster "
           + " where contno='"+tContNo+"' and caseno='"+tClmNo+"' and batno='"+tBatNo+"'";*/
    strSQL = wrapSql(tResourceName,"querysqldes4",[tContNo,tClmNo,tBatNo]);
    var arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        fm.UWIdea.value=arrResult[0][0];
    }
}

function DisplayInsured(tArrResult,tContNo,tInsuredNo)
{
    try{document.all('InsuredName').value= tArrResult[0][0]; }catch(ex){};
    try{document.all('InsuredSex').value= tArrResult[0][1]; }catch(ex){};
    try{document.all('RelationToAppnt').value= tArrResult[0][4]; }catch(ex){};
    try{document.all('RelationToMainInsured').value= tArrResult[0][5]; }catch(ex){};
   // alert(arrResult[0][8]);
    try{document.all('InsuredOccupationCode').value= tArrResult[0][6]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= tArrResult[0][7]; }catch(ex){};
    try{document.all('InsuredPluralityType').value= tArrResult[0][8]; }catch(ex){};
    try{document.all('InsuredAge').value= calPolCustomerAge(tArrResult[0][3],tArrResult[0][2]); }catch(ex){};
    quickGetName('occupationCode',fm.InsuredOccupationCode,fm.InsuredOccupationCodeName);
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
    quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
    /*var strSql = "select a.Name"
        +" ,(select occupationcode||'-'||occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode))"
        +" ,(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType))"
        +" ,a.InsuredNo "
            +" from lcinsured a where 1=1"
            +" and a.ContNo='"+tContNo+"'"
            +" and a.InsuredNo='"+tInsuredNo+"'";*/
	var strSql = wrapSql(tResourceName,"querysqldes5",[tContNo,tInsuredNo]);
	//prompt('',strSql);
	brr = easyExecSql(strSql, 1, 0,"","",1);
	if (brr)
	{
	  //brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredName.value  = brr[0][0];        
	  brr[0][1]==null||brr[0][1]=='null'?'0':fm.InsuredOccupationCodeName.value  = brr[0][1];
	  brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredOccupationTypeName.value  = brr[0][2];
	  //brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredNo.value  = brr[0][3];
	}
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
	var tClmNo = fm.ClmNo.value;
	/*var tSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt,mult,prem,"
	         + " insuyear||insuyearflag,payendyear||payendyearflag,payintv,b.passflag,a.polno,mainpolno "
	         + " from lcpol a,lluwmaster b "
	         + " where a.contno='"+mContNo+"' and a.insuredno='"+mInsuredNo
	         + "' and a.polno=b.polno"
   			 + " and b.caseno='"+tClmNo+"' and a.appflag='1' order by mainpolno,polno ";*/
     //prompt("",tSQL);
	 var tSQL = wrapSql(tResourceName,"querysqldes6",[mContNo,mInsuredNo,tClmNo]);
	 turnPage_OldRisk.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage_OldRisk.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage_OldRisk.arrDataCacheSet = decodeEasyQueryResult(turnPage_OldRisk.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage_OldRisk.pageDisplayGrid = OldRislPlanGrid;    
          
  //����SQL���
  turnPage_OldRisk.strQuerySql     = strSQL; 
  
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
	var tClmNo = fm.ClmNo.value;
	//����л�����������ּƻ�ʱ,��У���Ƿ��Ѿ�ѡ��������
	if(tPage=='2')
	{
		if(tOldSelNo==null || tOldSelNo<=0)
		{
			alert("����ѡ��һ�����ֺ������б��ƻ������");
			//onRiskTabChange(1);
			return;
		}		
		
		//��ʼ��ʼ��ѡ������ֽ���
		//��ȡSelPolNo 
	    var tSelPolNo = OldRislPlanGrid.getRowColData(tOldSelNo-1,11);  
	    //У��������Ƿ��мӷѣ����������б��ƻ����
	    /*var strSQL = "select * from LLUWPremMaster where polno='"+tSelPolNo
	    				+ "' and payplancode like '000000%%'"
	    				+ " and clmno='"+tClmNo+"'";*/
		var strSQL = wrapSql(tResourceName,"querysqldes7",[tSelPolNo,tClmNo]);
		var arrResult=easyExecSql(strSQL);
		if(arrResult!=null)
		{
			alert("����ɾ�������ּӷ���Ϣ�������б��ƻ������");
			//onRiskTabChange(1);
			return;
		}
	//	alert("tOldSelNo:"+tOldSelNo);
		//alert(tSelPolNo);
		//	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1"+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled+"&specScanFlag="+specScanFlag;
		document.getElementById("NewRiskPlan").src = "./UWManuChangeRiskMain.jsp?LoadFlag=25&ContType=1&scantype=scan&hh=1&checktype=1&ContNo="+mContNo+"&PrtNo="+mContNo+"&InsuredChkFlag=true&AppntChkFlag=false&SelPolNo="+tSelPolNo+"&NewChangeRiskPlanFlag=Y&PageFlag=2";
	}
	//����������
	if(tPage =='3'){
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
}

//tongmeng 2008-10-09 add
//�޸����ֺ˱�����
function makeRiskUWState()
{
	//У���Ƿ������ֱ�ѡ��
	var tSelNo = OldRislPlanGrid.getSelNo();
  if(tSelNo<=0)
 	{
 			alert("��ѡ�����ֱ�����");
 			return;
 	}
 //	alert('1');
 		var polno = OldRislPlanGrid.getRowColData(tSelNo - 1,11);
 		var mainpolno = OldRislPlanGrid.getRowColData(tSelNo - 1,12);
 		var uwstate = OldRislPlanGrid.getRowColData(tSelNo - 1,10);
 		fm.PolNo.value=polno;
 		fm.uwstate.value = uwstate;
 		document.all('flag').value = "risk";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./LLInsuredUWInfoChk.jsp";
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
 //	alert('1');
 		document.all('flag').value = "Insured";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./LLInsuredUWInfoChk.jsp";
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
	//var strSQL = "select 1 from lcpol where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"' "
			//+ " and riskcode in (select riskcode from lmriskapp where cancleforegetspecflag is not null and cancleforegetspecflag='1')"
	        //+ " and cancleforegetspecflag is null or cancleforegetspecflag<>'1'";	       
    var strSQL = wrapSql(tResourceName,"querysqldes8",[tContNo,tInsuredNo]);
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
	/*strSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode,"
	       + " firstpaydate,payenddate,polno,(select mainpolno from lcpol where polno=a.polno) "
	       + " from lcduty a,lmriskduty b where a.dutycode=b.dutycode "
         + " and polno in (select polno from lcpol where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"' and appflag='1') "*/
    strSQL = wrapSql(tResourceName,"querysqldes9",[tContNo,tInsuredNo]);
    str  = easyQueryVer3(strSQL, 1, 0, 1);
    return str;

}

function afterCodeSelect( cCodeName, Field ) {

		if( cCodeName == "addfeetype" ) {
			
			if(Field.value=='02')
			{
				
			}
		}
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
							//||riskcode=='112212'
							)
							{
								 PolAddGrid.setRowColData(tSelNo,5,'02',PolAddGrid);
								 //var tSQL = "select medrate from ldoccupation where occupationcode='"+document.all('InsuredOccupationCode').value+"' ";
								 var tSQL = wrapSql(tResourceName,"querysqldes10",[document.all('InsuredOccupationCode').value]);
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

}

//�ӷѱ���
function addFeeSave()
{
    var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("��ѡ����Ҫ����ļӷѼ�¼!");
		 return false;
	}
	
	var tAddFeeType = PolAddGrid.getRowColData(tSelNo,5);
    var tAddFeeNum = PolAddGrid.getRowColData(tSelNo,6);
    
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
		       		 //alert("�˵����ٱ��ʱ���");
		       		 //document.all('UpReporFlag').value = "Y";
		       }
    
	fm.action="./LLUWManuChangeRiskAddChk.jsp";
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
	var tClmNo=fm.ClmNo.value;
	var tBatNo=fm.BatNo.value;
	/*var tSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode, "
	         + " a.payplantype,a.addfeedirect,a.suppriskscore,a.prem,'',a.paystartdate,a.payenddate,'',a.polno,"
	         + " c.mainpolno,a.payplancode,a.addform "
	         + " from LLUWPremMaster a,lmriskduty b,lcpol c  where a.dutycode=b.dutycode and a.polno=c.polno"
	         + " and c.contno='"+tContNo+"' and c.insuredno='"+tInsuredNo+"' "
	         + " and a.payplancode like '000000%%' and a.clmno='"+tClmNo+"'"
	         + " and batno='"+tBatNo+"'";*/
	 var tSQL = wrapSql(tResourceName,"querysqldes11",[tContNo,tInsuredNo,tClmNo,tBatNo]);
	 turnPage_AddFee.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);          
	//�ж��Ƿ��ѯ�ɹ�
  if (turnPage_AddFee.strQueryResult) {  
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
}
}

//�Զ�����ӷ���Ϣ
function AutoCalAddFee(span)
{
	spanObj = span;

  
  //alert(document.all( spanObj ).all( 'PolAddGrid4' ).value);
  //�����ж���û�мӷ��㷨
  //���ֱ���
  var tRiskCode = document.all( spanObj ).all( 'PolAddGrid1' ).value;
  // alert(tRiskCode);
  //�ӷ����
  var tAddFeeType = document.all( spanObj ).all( 'PolAddGrid4' ).value;
  //�ӷѷ�ʽ
  var tAddFeeMethod = document.all( spanObj ).all( 'PolAddGrid5' ).value;
  //���α���
  var tDutyCode = document.all( spanObj ).all( 'PolAddGrid3' ).value;
  
  var tBatNo= fm.BatNo.value;
  var tClmNo = fm.ClmNo.value;
  
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

  /*var tSQL = " select 1 from LMDutyPayAddFee where "
           + " riskcode='"+tRiskCode+"' and dutycode='"+tDutyCode+"' and addfeetype='"+tAddFeeType+"' "
           + " and  addfeeobject='"+tAddFeeMethod+"'";*/
   var tSQL = wrapSql(tResourceName,"querysqldes12",[tRiskCode,tDutyCode,tAddFeeType,tAddFeeMethod]);
   var arrRes =  easyQueryVer3(tSQL, 1, 0, 1);
   //prompt("",tSQL);
   if(arrRes==null||!arrRes)
   {
   	  alert("������������мӷѣ����߸�����û�д˼ӷ�����!");
   	  document.all( spanObj ).all( 'PolAddGrid7' ).value='0';
   	  return;
   }
   //׼������
  var tPolNo =  document.all( spanObj ).all( 'PolAddGrid12' ).value;
  var tAddFeePoint = document.all( spanObj ).all( 'PolAddGrid6' ).value;
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

   //�ύ���ݿ�
  fm.action= "./LLUWCalHealthAddFeeChk.jsp?AddFeePolNo="+tPolNo+"&AddFeeRiskCode="+tRiskCode+"&AddFeeDutyCode="+tDutyCode+"&AddFeeType="+tAddFeeType+"&AddFeeMethod="+tAddFeeMethod+"&AddFeeInsuredNo="+mInsuredNo+"&AddFeePoint="+tAddFeePoint+"&ClmNo="+tClmNo+"&BatNo="+tBatNo;
  document.getElementById("fm").submit(); //�ύ
}


//�ӷ�ɾ��
//created by guanwei
function deleteData()
{ 
	var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("��ѡ����Ҫɾ���ļӷѼ�¼!");
		 return false;
	}
	
	var tPolNo = PolAddGrid.getRowColData(tSelNo,13);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tPayPlanCode = PolAddGrid.getRowColData(tSelNo,15);
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
    fm.action = "./LLUWManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
    document.getElementById("fm").submit();
  
  //var cPolNo= fm.PolNo2.value ;   
  //alert(cPolNo);
  
}