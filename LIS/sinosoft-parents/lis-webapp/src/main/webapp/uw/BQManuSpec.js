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
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var operate = "";
var proposalcontno = "";
var serialno = "";

//�ύ�����水ť��Ӧ����
function submitForm(tflag)
{
	if(tflag=="1")
	   {
	   	var tRemark=fm.Remark.value;
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
		 	fm.proposalcontno.value = UWSpecGrid.getRowColData(tSelNo,5);
     // alert(proposalno);
		 	fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);
		 	fm.operate.value = "UPDATE";
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
		 	fm.proposalcontno.value = UWSpecGrid.getRowColData(tSelNo,5);
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
		 	 fm.proposalcontno.value = UWSpecGrid.getRowColData(tSelNo,5);
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  if (FlagStr == "Fail" )
  {                 
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
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	QueryPolSpecGrid(fm.ContNo.value,fm.EdorNo.value,fm.InsuredNo.value);
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
	fm.submit();
}

function QueryPolSpecGrid(tContNo,tEdorNo,tInsuredNo)
{
	// ��ʼ�����
	// ��дSQL���
	//alert("QueryPolSpecGrid"+tContNo);
	var strSQL = "";
	var i, j, m, n;	
       //��ȡԭ������Ϣ
       // strSQL = "select LCPol.ContNo,LCPol.PrtNo,LCPol.PolNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
			 //+ "  ContNo =(select ContNo from LCCont where Prtno = '"+tContNo+"')"
			 //+ "  order by polno ";			
			// strSQL = "select proposalcontno from lccont where contno = '"+tContNo+"'";
			// var arrResult=easyExecSql(strSQL);  
	    // try{tProposalContNo = arrResult[0][0];}catch(ex){}
	
	
//			 strSQL = "select a,b,c,case c when 'x' then 'δ����' "
//			                          + " when '0' then '�ѷ���δ��ӡ'"
//			                          + " when '1' then '�Ѵ�ӡδ����'"
//                                + " when '2' then '�ѻ���'"
//                         + " end,"
//                         + " f,d,e,"
//                         + " case e when 'Y' then '�·�' "
//			                         + " when 'N' then '���·�'"
//                         + " end"
//                + "   from (select s.speccontent as a,"
//                + "                s.serialno as b,"
//                + "                (case when (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) is not null then (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) else 'x' end) as c,"
//                + "                s.serialno as d,"
//                + "                s.needprint as e,proposalcontno f"
//                + "                from lpcspec s "
//                + "                where s.edorno='"+tEdorNo+"' and s.contno = '"+tContNo+"' and s.customerno = '"+tInsuredNo+"' ";
                if(tQueryFlag=="2"){
//                	  strSQL = strSQL + "  )";
                	  
                	   var sqlid1="BQManuSpecSql1";
                	   var mySql1=new SqlClass();
                	   mySql1.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
                	   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
                	   mySql1.addSubPara(tEdorNo);//ָ���������
                	   mySql1.addSubPara(tContNo);//ָ���������
                	   mySql1.addSubPara(tInsuredNo);//ָ���������
                	   strSQL = mySql1.getString();
                	         	  
                	   document.all("divQuery").style.display="none";                	
                }
                else{
//                	strSQL = strSQL + "  and s.spectype='ch')";
                	
                   var sqlid2="BQManuSpecSql2";
             	   var mySql2=new SqlClass();
             	   mySql2.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
             	   mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
             	   mySql2.addSubPara(tEdorNo);//ָ���������
             	   mySql2.addSubPara(tContNo);//ָ���������
             	   mySql2.addSubPara(tInsuredNo);//ָ���������
             	   strSQL = mySql2.getString();
                }
               
    //prompt('',strSQL);
	turnPage.queryModal(strSQL, UWSpecGrid); 
    return true;	
}


function QueryPolSpecCont(tContNo,tEdorNo,tInsuredNo)
{
	//alert("QueryPolSpecCont"+tContNo);
		var strSQL = "";
		//��ѯ��������Ϣ
//		strSQL = "select name, (select max(a.insuredappage) from lppol a where b.edorno=a.edorno and b.contno=a.contno and b.insuredno=a.insuredno) age, managecom , substr(managecom,1,4) from lpinsured b where edorno='"+tEdorNo+"' and contno='"+tContNo+"' and insuredno='"+tInsuredNo+"'";
//		//prompt('',strSQL);
		
	   var sqlid3="BQManuSpecSql3";
  	   var mySql3=new SqlClass();
  	   mySql3.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
  	   mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
  	   mySql3.addSubPara(tEdorNo);//ָ���������
  	   mySql3.addSubPara(tContNo);//ָ���������
  	   mySql3.addSubPara(tInsuredNo);//ָ���������
  	   strSQL = mySql3.getString();
		
		var arr = easyExecSql(strSQL);
		if(arr!=null){
			fm.InsuredName.value = arr[0][0];
			fm.InsuredAge.value = arr[0][1];
			fm.ManageCom.value = arr[0][2];	
		    if(arr[0][3]=="8611"||arr[0][3]=="8631")	
		    	fm.SpecTemp.value = 'ch002';		    	
		    else
		    	fm.SpecTemp.value = 'ch001';
//		    strSQL = "select noti,speccontent from LCCSpecTemplet where temptype='ch' and templetcode='"+fm.SpecTemp.value+"'";
		    
		       var sqlid4="BQManuSpecSql4";
		  	   var mySql4=new SqlClass();
		  	   mySql4.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
		  	   mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		  	   mySql4.addSubPara(fm.SpecTemp.value);//ָ���������
		  	   strSQL = mySql4.getString();
		    
		    var arr1 = easyExecSql(strSQL);
		    if(arr1!=null)
		    {
		    	fm.SpecTempname.value = arr1[0][0];	
		    	fm.Remark.value = arr1[0][1];
		    }		    	
		    
		}
		
/**
		strSQL = "select substr(managecom,1,4) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
		var managecom = easyExecSql(strSQL); 
		
		strSQL = "select substr(managecom,1,6) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
		var managecom2 = easyExecSql(strSQL); 
		
		//alert(managecom);
		if(managecom!=null){
			//����Ǳ��֡��Ϻ������ݵ�Ͷ��������ʾʮ��
		  if(managecom=="8621"||managecom=="8622"){
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype2'"
				 ;	
		  }
		else if (managecom=="8623"){
			 if(managecom2!=null&&managecom2=="862300"){
			 strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype2'"
				 ;		
			 	}
			 else {
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;
			 	}
			}
		  else{
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;	
		  }
		}
	 else{
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;	
	 }
	
       //��ȡԭ������Ϣ
       // strSQL = "select cont from ldcodemod where 1 =1 and codetype='spectype'";		
       
	turnPage.queryModal(strSQL, UWSpecContGrid);   
**/
}
function getPolGridCho()
{
  var tSelNo = UWSpecGrid.getSelNo()-1;
  var cPolNo = UWSpecGrid.getRowColData(tSelNo,3);
  var tcontno = UWSpecGrid.getRowColData(tSelNo,1);
  //alert(tcontno);
 if(cPolNo != null && cPolNo != "" )
  {
    document.all("PolNo").value = cPolNo;
   
    QuerySpecReason(cPolNo);   
    QuerySpec(tcontno);
  }	
}



//��ѯ�Ѿ�¼����Լ����
function QuerySpec(tcontno)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select speccontent from lpcspec where contno ='"+tcontno+"' and SerialNo in (select max(SerialNo) from lcspec where contno ='"+tcontno+"')";
	
	   var sqlid5="BQManuSpecSql5";
	   var mySql5=new SqlClass();
	   mySql5.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
	   mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	   mySql5.addSubPara(tcontno);//ָ���������
	   mySql5.addSubPara(tcontno);//ָ���������
	   strSQL = mySql5.getString();
	
	var arrResult=easyExecSql(strSQL);  
	try{document.all('Remark').value= arrResult[0][0];}catch(ex){};                                                                                          
//	tur   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};                                                                     nPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
//
//  //�ж��Ƿ��ѯ�ɹ�
//  if (!turnPage.strQueryResult) {
//    return "";
//  }
//  
//  //��ѯ�ɹ������ַ��������ض�ά����
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//  
//  fm.Remark.value = turnPage.arrDataCacheSet[0][0];	
  
  return true;	
}


//��ѯ�Ѿ�¼��ӷ���Լԭ��
function QuerySpecReason(cPolNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select specreason from LCUWMaster where 1=1 "
//			 + " and polno = '"+cPolNo+"'";
	
	   var sqlid6="BQManuSpecSql6";
	   var mySql6=new SqlClass();
	   mySql6.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
	   mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	   mySql6.addSubPara(cPolNo);//ָ���������
	   strSQL = mySql6.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.SpecReason.value = turnPage.arrDataCacheSet[0][0];
  //fm.Reason.value = turnPage.arrDataCacheSet[0][1];
  
  return true;	
}


function getSpecGridCho()
{
	var tContent = fm.Remark.value;
	
	var tSelNo = UWSpecContGrid.getSelNo()-1;
	var tRemark = UWSpecContGrid.getRowColData(tSelNo,1);	
	
	fm.Remark.value = tRemark + tContent; 
}


function getSpecGridCho2()
{
	//var tContent = fm.Remark.value;
	
	var tSelNo = UWSpecGrid.getSelNo()-1;
	var tSerialNo = UWSpecGrid.getRowColData(tSelNo,2);	
	var tProposalContNo = UWSpecGrid.getRowColData(tSelNo,5);
//	var tSQL = "select speccode,speccontent,needPrint,"
//	                                 + " case needPrint when 'Y' then '�·�' "
//			                         + " when 'N' then '���·�'"
//                         + " end from lpcspec where edorno='"+fm.EdorNo.value+"' and proposalcontno='"+tProposalContNo+"' and serialno='"+tSerialNo+"'";
	
	   var sqlid7="BQManuSpecSql7";
	   var mySql7=new SqlClass();
	   mySql7.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
	   mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	   mySql7.addSubPara(fm.EdorNo.value);//ָ���������
	   mySql7.addSubPara(tProposalContNo);//ָ���������
	   mySql7.addSubPara(tSerialNo);//ָ���������
	   var tSQL = mySql7.getString();
	
	var arrResult=easyExecSql(tSQL);  
	//document.all('SpecReason').value= arrResult[0][0]; 
	if(arrResult!=null) 
	{
		fm.SpecTemp.value = arrResult[0][0];
		fm.Remark.value = arrResult[0][1];
		fm.NeedPrintFlag.value = arrResult[0][2];
		fm.IFNeedFlagName.value = arrResult[0][3];		
	}   	
}

function initpolno(tContNo,tInsuredNo,tEdorNo)
{
	var strSQL = "";

	if(_DBT==_DBO){
//		strSQL = "select mainpolno from LPPol where 1=1 "
//			 + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and insuredno='"+tInsuredNo+"' and rownum = 1";
		
		   var sqlid8="BQManuSpecSql8";
		   var mySql8=new SqlClass();
		   mySql8.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
		   mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		   mySql8.addSubPara(tEdorNo);//ָ���������
		   mySql8.addSubPara(tContNo);//ָ���������
		   mySql8.addSubPara(tInsuredNo);//ָ���������
		   strSQL = mySql8.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select mainpolno from LPPol where 1=1 "
//			 + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and insuredno='"+tInsuredNo+"' limit 0,1";
		
		   var sqlid9="BQManuSpecSql9";
		   var mySql9=new SqlClass();
		   mySql9.setResourceName("uw.BQManuSpecSql");//ָ��ʹ�õ�properties�ļ���
		   mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		   mySql9.addSubPara(tEdorNo);//ָ���������
		   mySql9.addSubPara(tContNo);//ָ���������
		   mySql9.addSubPara(tInsuredNo);//ָ���������
		   strSQL = mySql9.getString();
	}
	

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	
    return "";
  }
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.PolNo.value = turnPage.arrDataCacheSet[0][0];
  //alert(fm.PolNo.value);

}