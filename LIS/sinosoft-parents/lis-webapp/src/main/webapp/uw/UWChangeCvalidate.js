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



/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 
 //�ύ�����水ť��Ӧ����
function submitForm()
{
  var tInsuredNo = fm.InsuredNo.value;
  if(tInsuredNo!=""){
      if(checkDate(tContNo,tInsuredNo)==false)
        {
          return false;
        }
      }else
         alert("û�б������˱��룡");
  /*if(specifyDisplay()==false)
     return false;*/
  if(fm.CvalidateConfirm.value==null||fm.CvalidateConfirm.value==""){
	  alert("��¼���Ƿ�ָ����Ч���ڣ�");
	  return false;
  }
  if(checkAddPrem()==false)
    return false;  
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
  
  var tCvalidate = fm.Cvalidate.value ;
 
  if(tCvalidate == "")
    { 
      alert("��������Ч����");      
      
    }
  fm.action= "./UWChangeCvalidateChk.jsp";
  document.getElementById("fm").submit(); //�ύ
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
	var showStr="�����ɹ���";
  	showInfo.close();
  	alert(showStr);
  	initForm();
  	//updateCvalidate();
  	
    //ִ����һ������
  }
}

function updateCvalidate(){
   //��ʱ�Ѿ��ύ�ɹ� ����������Ϣ�����е���Ч����Ӧ��polapplydate ��Ϊ ���� cvalidate
   
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
  //var aSQL=" select a.contno,a.appntname,a.appntbirthday,a.insuredname,a.insuredbirthday,a.polapplydate,"
  //        +" nvl((select enteraccdate from ljtempfee where tempfeeno = a.tempfeeno),'1900-01-01'), a.insuredno"
  //        +" from lccont a where 1 = 1"
  //        +" and ContNo = '"+tContNo+"'";         
//  var aSQL = " select distinct(a.insuredno),a.contno,a.appntname,b.appntbirthday, a.insuredname,"
//            +" b.insuredbirthday, b.polapplydate,(select makedate from es_doc_main where doccode=b.prtno and subtype='UA001'),(case when (select max(enteraccdate) from ljtempfee where otherno = b.contno) is not null then to_char((select max(enteraccdate) from ljtempfee where otherno = b.contno),'yyyy-mm-dd') else '' end),b.cvalidate"
//            +"  from lcpol a,lccont b"
//            +" where a.contno = b.contno and b.contno ='"+tContNo+"'"
            
            var sqlid1="UWChangeCvalidateSql0";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(tContNo);//ָ������Ĳ���
        	strSql=mySql1.getString();
        	
  turnPage.queryModal(strSql, ContGrid);
  //Ĭ��ѡ��һ��
  if(ContGrid.canSel==1&&ContGrid.mulLineCount>1)
 {
 			document.all('ContGridSel')[0].checked=true;
 }
 if(ContGrid.canSel==1&&ContGrid.mulLineCount==1)
 {
 			document.all('ContGridSel').checked=true;
 }
}
function specifyvalidate(tContNO,tInsuredNo){
   //�ж��Ƿ����
  var tNowInsuredNo="";
  if(tInsuredNo=="1"){
  tNowInsuredNo = fm.InsuredNo.value;
  }else{
     tNowInsuredNo = tInsuredNo;
  }
  // var tSQL = "select distinct(specifyvalidate) from lcpol where contno ='"+tContNO+"' and insuredno = '"+tNowInsuredNo+"'";
   
    var sqlid2="UWChangeCvalidateSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNO);//ָ������Ĳ���
	mySql2.addSubPara(tNowInsuredNo);//ָ������Ĳ���
	strSql=mySql2.getString();
	
   var tSpecifyvalidate = easyExecSql(strSql);
   if(tSpecifyvalidate!=""){
   fm.Specifyvalidate.value =tSpecifyvalidate;
   fm.CvalidateConfirm.value = tSpecifyvalidate;
   if(tSpecifyvalidate =="Y"){
        fm.Flag.value ="2"
      	document.all('Button1').disabled='';
      	//divChangeResult.style.display= "";
      	//var tLCCSpecSQL ="select a.spectype,a.speccode,a.speccontent,a.specreason,a.serialno from lccspec a where contno  ='"+tContNo+"' and speccode = 'hs001'";
      	
      	var sqlid3="UWChangeCvalidateSql2";
     	var mySql3=new SqlClass();
     	mySql3.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
     	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
     	mySql3.addSubPara(tContNo);//ָ������Ĳ���
     	strSql=mySql3.getString();
     	
      	var arrResult1 = easyExecSql(strSql);
      	if(arrResult1!=null){
      	fm.SpecType.value = arrResult1[0][0];
     	fm.CvalidateIdea.value =arrResult1[0][2];
     	fm.SpecCode.value = arrResult1[0][1];
     	fm.SpecReason.value = arrResult1[0][3];
      	fm.SerialNo.value = arrResult1[0][4];
      	fm.Operate.value="UPDATE";//LCCSpec�����Ѿ���һ����ͬ��Լ
      	}else{
      	fm.SpecType.value = "";
     	fm.CvalidateIdea.value ="";
     	fm.SpecCode.value = "";
     	fm.SpecReason.value = "";
      	fm.SerialNo.value = "";
      	}
  	 }else{
     	 document.all('Button1').disabled='true';
     	 divChangeResult.style.display= "none";
     	 
     	 //var tLCCSpecTempletSQL = "select templetcode,specreason,speccontent,temptype from lccspectemplet where templetcode = 'hs001'";
     	 
     	var sqlid4="UWChangeCvalidateSql3";
     	var mySql4=new SqlClass();
     	mySql4.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
     	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
     	strSql=mySql4.getString();
     	
     	 var arrResult = easyExecSql(strSql);
     	 if(arrResult!=null){
     	 fm.SpecType.value = arrResult[0][3];
     	 fm.CvalidateIdea.value =arrResult[0][2];
     	 fm.SpecCode.value = arrResult[0][0];
     	 fm.SpecReason.value = arrResult[0][1];
     	 //fm.SerialNo.value = arrReault[0][5];
     	 }
     	 //m.CvalidateIdea.value =fm.Date.value;
  	 }
   }
}

function specInput(){
     //��ʾԱ����Լ
   if(fm.DivFlag.value == "1"){
   divChangeResult.style.display = "";
   fm.DivFlag.value = "2";
   }else{
   divChangeResult.style.display = "none";
   fm.DivFlag.value = "1";
   }
     //divChangeResult.style.display= "";  
}

function querySpecInput(tContNo){
	//��ʼ��ʱ�ж��Ƿ�¼����Լ
   if(((fm.Flag.value=="1")&&(fm.Specifyvalidate.value="N"))
         ||((fm.Specifyvalidate.value="N")&&(fm.CvalidateIdea.value==""||fm.CvalidateIdea.value==null))){
        //var tquery = "select 1 from es_doc_relation where bussno='"+tContNo+"' and subtype = 'TB2'";
        
    	var sqlid5="UWChangeCvalidateSql4";
     	var mySql5=new SqlClass();
     	mySql5.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
     	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
     	mySql5.addSubPara(tContNo);
     	strSql=mySql5.getString();
        
	    var arrResult1 = easyExecSql(strSql);
	    if(arrResult1!=null){	     	 	
	       fm.Flag.value="2";
	       document.all('Button1').disabled='';
	      divChangeResult.style.display= "";
	      fm.Operate.value = "INSERT";
	     // var tLCCSpecTempletSQL = "select templetcode,specreason,speccontent,temptype from lccspectemplet where templetcode = 'hs001'";

	      var sqlid6="UWChangeCvalidateSql5";
	     	var mySql6=new SqlClass();
	     	mySql6.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
	     	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	     	strSql=mySql6.getString();
	     	
	      var arrResult = easyExecSql(strSql);
	     	 if(arrResult!=null){
	     	 fm.SpecType.value = arrResult[0][3];
	     	 fm.CvalidateIdea.value =arrResult[0][2];
	     	 fm.SpecCode.value = arrResult[0][0];
	     	 fm.SpecReason.value = arrResult[0][1];
	     	 //fm.SerialNo.value = arrReault[0][5];
	     	 }
	    }else{
	       //��¼����Լ  BL�в�����LCCSpec����������� �ؼ����ύ
	       fm.Operate.value = "INSERT&&NOSPEC";
	    }
   } 	
}

function checkAddPrem()
{	
	var tContNo = fm.ContNo.value;
	var tSelNo = ContGrid.getSelNo()-1;
	var tInsuredNo = ContGrid.getRowColData(tSelNo,1);
  	var tInsuredBirthday = ContGrid.getRowColData(tSelNo,6);
  	var tAppntBirthday = ContGrid.getRowColData(tSelNo,4);
	var tInsuredAge=calAgeNew(tInsuredBirthday,document.all("Cvalidate").value);
	//var tSQL1 = "select insuredappage from lcpol where ContNo='"+tContNo+"' and insuredno = '"+tInsuredNo+"'";
	
    var sqlid7="UWChangeCvalidateSql6";
   	var mySql7=new SqlClass();
   	mySql7.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
   	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
   	mySql7.addSubPara(tContNo);//ָ������Ĳ���
	mySql7.addSubPara(tInsuredNo);//ָ������Ĳ���
   	strSql=mySql7.getString();
   	
    var arrResult1 = easyExecSql(strSql);
    var tInsuredAge1 ='';
    if(arrResult1!=null)
    {
    	tInsuredAge1=arrResult1[0];
    }	
	//var tSQL = "select 1 from lcprem where polno in (select polno from lcpol where ContNo='"+tContNo+"' and insuredno = '"+tInsuredNo+"') and payplancode like '000000%'";
	//prompt('',tSQL);
	//alert(tInsuredAge);
	//alert(tInsuredAge1);
	
    var sqlid8="UWChangeCvalidateSql7";
   	var mySql8=new SqlClass();
   	mySql8.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
   	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
   	mySql8.addSubPara(tContNo);//ָ������Ĳ���
	mySql8.addSubPara(tInsuredNo);//ָ������Ĳ���
   	strSql=mySql8.getString();
   	
    var arrResult = easyExecSql(strSql);
    if(arrResult!=null && tInsuredAge!=tInsuredAge1){
     	 if (confirm("���������䷢���仯���мӷ���Ϣ���Ƿ������") == true)
    	 	return true;
    	 else
    	 	return false;
    }
    //var tSQL2 = "select cvalidate from lcpol where ContNo='"+tContNo+"' and insuredno = '"+tInsuredNo+"' and riskcode='121301'";
    
    var sqlid9="UWChangeCvalidateSql8";
   	var mySql9=new SqlClass();
   	mySql9.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
   	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
   	mySql9.addSubPara(tContNo);//ָ������Ĳ���
	mySql9.addSubPara(tInsuredNo);//ָ������Ĳ���
   	strSql=mySql9.getString();
   	
    var arrResult2 = easyExecSql(strSql);
    if(arrResult2!=null){
    	 var oldCvalidate = arrResult2[0]+"";
   		 var tAppntAgeOld=calAgeNew(tAppntBirthday,oldCvalidate);
   		 //alert(tAppntAgeOld);
    	 var tAppntAgeNew=calAgeNew(tAppntBirthday,document.all("Cvalidate").value);
    	 //alert(tAppntAgeNew);
    	 if(tAppntAgeOld!=tAppntAgeNew)
    	 {
    	 	if (confirm("Ͷ�������䷢���仯���л�����121301���Ƿ������") == true)
	    	 	return true;
	    	 else
	    	 	return false;
    	 }     	 
    }
    return true;
}

function specifyDisplay(){
   //ȷ�����ж��Ƿ�¼����Լ
   if(((fm.Flag.value=="1")&&(fm.Specifyvalidate.value="N"))
         ||((fm.Specifyvalidate.value="N")&&(fm.CvalidateIdea.value==""||fm.CvalidateIdea.value==null))){
   if (confirm("�Ƿ�¼����Ч�ջ�����Լ��") == true)
    {
       fm.Flag.value="2";
       document.all('Button1').disabled='';
      divChangeResult.style.display= "";
      fm.Operate.value = "INSERT";
      //var tLCCSpecTempletSQL = "select templetcode,specreason,speccontent,temptype from lccspectemplet where templetcode = 'hs001'";
      
      var sqlid10="UWChangeCvalidateSql9";
     	var mySql10=new SqlClass();
     	mySql10.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
     	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
     	strSql=mySql10.getString();
     	
     	 var arrResult = easyExecSql(strSql);
     	 if(arrResult!=null){
     	 fm.SpecType.value = arrResult[0][3];
     	 fm.CvalidateIdea.value =arrResult[0][2];
     	 fm.SpecCode.value = arrResult[0][0];
     	 fm.SpecReason.value = arrResult[0][1];
     	 //fm.SerialNo.value = arrReault[0][5];
     	 }
      	return false;
    }else{
       //��¼����Լ  BL�в�����LCCSpec����������� �ؼ����ύ
       fm.Operate.value = "INSERT&&NOSPEC";
    }
   }
   return true;
}


function queryPersonInfo(tContNo,tInsuredNo){
var tNowInsuredNo="";
if(tInsuredNo=="1"){
  tNowInsuredNo = fm.InsuredNo.value;
  }else{
     tNowInsuredNo = tInsuredNo;
  }
  //var aSQL = "select a.ContNo,a.Cvalidate,to_date(concat(concat(substr(a.cvalidate,1,4),'-'),substr(to_char(subdate(a.insuredbirthday,1),'yyyy-mm-dd'),6,5)),'yyyy-mm-dd'),(select proposalcontno from lccont where contno = a.contno) from lcpol a where a.ContNo='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"'";	
  
  var sqlid11="UWChangeCvalidateSql10";
	var mySql11=new SqlClass();
	mySql11.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(tContNo);//ָ������Ĳ���
	mySql11.addSubPara(tNowInsuredNo);//ָ������Ĳ���
	strSql=mySql11.getString();
	
  var arrResult = easyExecSql(strSql);
  //2010-03-08  ���ǵ���������˳�������Ϊ����3��1��ʱ�����SQL���׳��쳣 
  //����һ����ѯ��������SQLû�в�������򽫱����˵ĳ�������-2
  if(arrResult==null){
	  //aSQL = "select a.ContNo,a.Cvalidate,to_date(concat(concat(substr(a.cvalidate,1,4),'-'),substr(to_char(subdate(a.insuredbirthday,2),'yyyy-mm-dd'),6,5)),'yyyy-mm-dd'),(select proposalcontno from lccont where contno = a.contno) from lcpol a where a.ContNo='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"'";
	  
	  var sqlid12="UWChangeCvalidateSql11";
		var mySql12=new SqlClass();
		mySql12.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(tContNo);//ָ������Ĳ���
		mySql12.addSubPara(tNowInsuredNo);//ָ������Ĳ���
		strSql=mySql12.getString();
		
	  arrResult = easyExecSql(strSql);
	  if(arrResult==null){
		  alert("�����µ���Ч����ʱ����");
		  return false;
	  }
  }
  document.all('ContNo').value = arrResult[0][0];
  document.all('CvalidateHide').value = arrResult[0][2];
  document.all('ProposalContNo').value = arrResult[0][3];
  if(fm.Specifyvalidate.value =="N"){
     document.all('Cvalidate').value = arrResult[0][2];
     }else
     document.all('Cvalidate').value ="";
}

function checkDate(tContNo,tInsuredNo){
  //У��ָ����Ч�� ����Ĭ��Ϊ������������ǰһ�죨��/�գ������Ϊ���ꣻ�ɽ�һ���޸ģ�����ָ�����ܳ���61�죬���ָ�����ܳ���27�죻
  var tNowInsuredNo="";
  if(tInsuredNo=="1"){
  tNowInsuredNo = fm.InsuredNo.value;
  }else{
     tNowInsuredNo = tInsuredNo;
     
  }
   var tCvalidate = fm.Cvalidate.value;
   if(tCvalidate==""||tCvalidate==null)
   {
      alert("û��¼��ָ����Ч���ڣ�");
      return false;
   }
   var tCvalidateSql = "";
   if(_DBT==_DBO){
	  // tCvalidateSql = "SELECT to_char(subdate(polapplydate,61),'yyyy-mm-dd'),(case when (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) is not null then (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) else to_char(adddate(now(),30),'yyyy-mm-dd') end),polapplydate FROM lcpol where contno='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"' and rownum = 1 ";
	   
	   var sqlid13="UWChangeCvalidateSql12";
		var mySql13=new SqlClass();
		mySql13.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(tContNo);//ָ������Ĳ���
		mySql13.addSubPara(tNowInsuredNo);//ָ������Ĳ���
		strSql=mySql13.getString();
		
   }else if(_DBT==_DBM){
	   //tCvalidateSql = "SELECT to_char(subdate(polapplydate,61),'yyyy-mm-dd'),(case when (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) is not null then (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) else to_char(adddate(now(),30),'yyyy-mm-dd') end),polapplydate FROM lcpol where contno='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"' limit 1 ";
	   
	   var sqlid14="UWChangeCvalidateSql13";
		var mySql14=new SqlClass();
		mySql14.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(tContNo);//ָ������Ĳ���
		mySql14.addSubPara(tNowInsuredNo);//ָ������Ĳ���
		strSql=mySql14.getString();
		
   }
   var arrResult = easyExecSql(strSql);
   var tStartDate = arrResult[0][0];
   var tEndDate = arrResult[0][1];
   fm.PolApplyDate.value =arrResult[0][2];
   //alert("�ж�ָ������Ч����"+tCvalidate+"�Ƿ����Ҫ�󣡻�����ǰָ�����ܳ���61�죬���ָ�����ܳ���27��!���Ƿ���"+tStartDate+"��"+tEndDate+"֮�䣡");
   if((tCvalidate>tStartDate)&&(tCvalidate<tEndDate)){}else{
      //alert("ָ������Ч����"+tCvalidate+"������Ҫ����ԭ����Чʱ��"+fm.PolApplyDate.value+"�Ļ����ϣ�������ǰָ�����ܳ���61�죬���ָ�����ܳ���27��!��Ӧ����"+tStartDate+"��"+tEndDate+"֮�䣡");
      alert("ָ������Ч����"+tCvalidate+"������Ҫ��Ӧ����"+tStartDate+"��"+tEndDate+"֮�䣡");
      fm.Cvalidate.value="";
      return false;
   }
   return true;
}

function getContDetail(){
	
	
}

function initPolInfo(){
   //��ʼ��Ϊһ�屻��������Ϣ
   if(ContGrid.mulLineCount>0){
   var tFirstInsuredNo = ContGrid.getRowColData(0,1);
//   var tPolInfoSQL =" select a.polno,a.riskcode,(select riskname from lmrisk where riskcode =a.riskcode),"
//            +" a.amnt,a.mult,a.prem,"
//            +" a.cvalidate,a.payyears, a.years,a.cvalidate from lcpol a where 1=1"
//            +" and a.ContNo = '"+tContNo+"' and a.insuredno ='"+tFirstInsuredNo+"' ";
   
    var sqlid15="UWChangeCvalidateSql14";
	var mySql15=new SqlClass();
	mySql15.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
	mySql15.addSubPara(tContNo);//ָ������Ĳ���
	mySql15.addSubPara(tFirstInsuredNo);//ָ������Ĳ���
	strSql=mySql15.getString();
	
    turnPage.queryModal(strSql,PolGrid);
   fm.InsuredNo.value = tFirstInsuredNo;
   }else{
     alert("û��Ͷ����������Ϣ��");
   }
}

function getPolInfo(){
  var tSelNo = ContGrid.getSelNo()-1;
  var tInsuredNo = ContGrid.getRowColData(tSelNo,1);
//  var aSQL = " select a.polno,a.riskcode,(select riskname from lmrisk where riskcode =a.riskcode),"
//            +"  a.amnt,a.mult,a.prem,"
//            +" a.cvalidate,a.payyears, a.years from lcpol a where 1=1"
//            +" and a.ContNo = '"+tContNo+"' and a.insuredno ='"+tInsuredNo+"' ";
  
  var sqlid16="UWChangeCvalidateSql15";
	var mySql16=new SqlClass();
	mySql16.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
	mySql16.addSubPara(tContNo);//ָ������Ĳ���
	mySql16.addSubPara(tInsuredNo);//ָ������Ĳ���
	strSql=mySql16.getString();
	
  turnPage2.queryModal(strSql, PolGrid);
  fm.InsuredNo.value =tInsuredNo;
  fm.Flag.value ="1"
  //�ı�Ĭ��ָ����Ч��
   specifyvalidate(tContNo,tInsuredNo);
	queryPersonInfo(tContNo,tInsuredNo)
   //checkDate(tContNo,tInsuredNo);
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
	//var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
  //alert(aSQL);
	
	  var sqlid17="UWChangeCvalidateSql16";
		var mySql17=new SqlClass();
		mySql17.setResourceName("uw.UWChangeCvalidateSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//ָ������Ĳ���
		strSql=mySql17.getString();
		
  var arrResult = easyExecSql(strSql);
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
	 window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
}

function showTempFee()
{
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window11");
}

