//�������ƣ�UWModifyFloatRate.js
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2008-11-3 11:10:36
//������  ��liuqh
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
/*
select max(getdutykind)	from lcget where getdutycode in (select othercode from lmdutyctrl"
                       +" where fieldname = 'GetDutyKind' and dutycode = '"+tDutyCode+"' and InpFlag ='Y')
                       
*/

function initFloatRate(){
//  var tSql = " select (select riskname from lmriskapp where "
//            +" riskcode in (select riskcode from lcpol where polno=a.polno)) a,"
//            +" insuyear,floatrate,"
//            +" case "
//            +" when "
//            +" (select count(*) from lmriskdiscount where riskcode in (select riskcode from lcpol where polno =a.polno) and payintv =a.payintv) ='0' "
//            +" then (case when (select floatrate from lmriskdiscount where payintv!='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) is not null then (select floatrate from lmriskdiscount where payintv!='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) else floatrate end) "
//            +" else (case when (select floatrate from lmriskdiscount where payintv ='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) is not null then (select floatrate from lmriskdiscount where payintv ='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) else floatrate end) end ,polno "
//            + ",(select max(getdutykind) from lcget where contno=a.contno and livegettype='0' "
//            + " and getdutycode in (select othercode from lmdutyctrl "
//            +" where fieldname = 'GetDutyKind' and dutycode = a.dutycode and InpFlag ='Y'))"
//            +" from lcduty a where contno='"+contNo+"' and polno in (select polno from lcpol where insuredno = '"+insuredNo+"') order by a";
  
  var sqlid1="UWModifyFloatRateSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWModifyFloatRateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(contNo);//ָ������Ĳ���
	mySql1.addSubPara(insuredNo);//ָ������Ĳ���
	var tSql=mySql1.getString();
	
  var arrResult = easyExecSql(tSql);
  if(arrResult != null){
  turnPage.queryModal(tSql, RiskFloatRateGrid);     
  }
     
}

function initSpecIdea(){
   //��ʼ��proposalno
   //var tSql ="select proposalno from lcpol where contno ='"++"' and insuredno ='"++"' ";
   //��ʼ����ͬ�µ�Ա����Լ��Ϣ
        //var tLCCSpecSQL ="select a.spectype,a.speccode,a.speccontent,a.specreason,a.serialno from lccspec a where contno  ='"+contNo+"' and speccode = 'yg001'";
      	
        var sqlid2="UWModifyFloatRateSql2";
    	var mySql2=new SqlClass();
    	mySql2.setResourceName("uw.UWModifyFloatRateSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    	mySql2.addSubPara(contNo);//ָ������Ĳ���
    	var tLCCSpecSQL=mySql2.getString();
    	
        var arrResult1 = easyExecSql(tLCCSpecSQL);
      	if(arrResult1!=null){
      	fm.SpecType.value = arrResult1[0][0];
     	fm.FloatRateIdea.value =arrResult1[0][2];
     	fm.SpecCode.value = arrResult1[0][1];
     	fm.SpecReason.value = arrResult1[0][3];
      	fm.SerialNo.value = arrResult1[0][4];
      	fm.SpecOperate.value="UPDATE";//LCCSpec�����Ѿ���һ����ͬ��Լ
      	}else{
      	divChangeResult.style.display= "none";
      	fm.Button1.disabled = 'true';
      	fm.SpecType.value = "";
     	fm.FloatRateIdea.value ="";
     	fm.SpecCode.value = "";
     	fm.SpecReason.value = "";
      	fm.SerialNo.value = "";
      	}      	
      	
   //var tSpecSQL="  select speccontent from lccspec where contno='"+contNo+"' and spectype = 'yg' ";
   //var tSpecContent =easyExecSql(tSpecSQL);
   //if(tSpecContent==""||tSpecContent ==null){
   //   fm.Button1.disabled = 'true';
   //}else{
   //fm.FloatRateIdea.value = tSpecContent;
   //}
}

function submitForm(){
     //һЩ�ж�     
     if(Speccheck()==false)
       return false;
     if(!checkFLoatRate())
        return false;
        /*
     var tDutyCodeSQL =  "select dutycode from lcget where contno = '"+contNo+"' and rownum ='1'";
     var tDutyCode = easyExecSql(tDutyCodeSQL);
     if(tDutyCode !="")
     {
       var tDutyKindSQL = " select max(getdutykind)	from lcget where getdutycode in (select othercode from lmdutyctrl"
                       +" where fieldname = 'GetDutyKind' and dutycode = '"+tDutyCode+"' and InpFlag ='Y')"
                       +" and contno = '"+contNo+"'";
       var tArr = easyExecSql(tDutyKindSQL);
       if(tArr!=null)
       {
       	 fm.GetDutyKind.value = tArr[0][0];
      }
     }*/
     //alert("fm.GetDutyKind.value:"+fm.GetDutyKind.value);
     //if(fm.GetDutyKind.value ==""||fm.GetDutyKind.value==null)
    //  return false;
//     var tSamePersonSql = "select count(*) from lcpol a where a.insuredno=a.appntno and polno='"+polNo+"'";
     
     var sqlid3="UWModifyFloatRateSql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("uw.UWModifyFloatRateSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
 	mySql3.addSubPara(polNo);//ָ������Ĳ���
 	var tSamePersonSql=mySql3.getString();
 	
     var tSameFlag = easyExecSql(tSamePersonSql);
     if(tSameFlag == "0"){
     //����ͬһ����
     fm.SamePersonFlag.value = "0";
     }else{
     fm.SamePersonFlag.value = "1";
     }
     fm.ContNo.value = contNo;
     fm.PolNo.value = polNo;
     lockScreen('lkscreen');
     var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     fm.submit(); //�ύ
}

function Speccheck(){
    //�ж��Ƿ�¼����Լ
    if((fm.FloatRateIdea.value==""||fm.FloatRateIdea.value==null)&&fm.SpecFlag.value=="1"){
      if(confirm("�Ƿ�¼��Ա����Լ��")==true){
        fm.Button1.disabled = '';
       fm.SpecFlag.value = "2";
//       var tSpecTempletSQL = "select a.templetcode,a.temptype,a.specreason,a.speccontent from lccspectemplet a  where a.templetcode='yg001'";
       
       var sqlid4="UWModifyFloatRateSql4";
    	var mySql4=new SqlClass();
    	mySql4.setResourceName("uw.UWModifyFloatRateSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
    	var tSpecTempletSQL=mySql4.getString();
    	
       var arrResult = easyExecSql(tSpecTempletSQL);
       if(arrResult!=null){
       fm.SpecCode.value =arrResult[0][0];
       fm.SpecType.value =arrResult[0][1];
       fm.SpecReason.value =arrResult[0][2];
       fm.FloatRateIdea.value =arrResult[0][3];
       fm.SpecOperate.value = "INSERT";
       }else{
           alert("��ʼ��Ա����Լ���������Ϣ����");
       }
       //fm.all('Button1').disabled=''
       return false;
    }else{
       fm.Button1.disable = "true";
       fm.SpecFlag.value = "1";
       fm.SpecOperate.value = "INSERT&&NOSPEC";
     }
     }
     return true;
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
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	try {
		if(showInfo!=null)
		{
 	showInfo.close();
}
}
catch(e)
{
	}
  if (FlagStr == "Fail" )
  {         
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
   
    alert(content);
   // parent.close();
  }
  else 
  { 
	var showStr="�����ɹ���";  	
  	//showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //ִ����һ������
  }
  unlockScreen('lkscreen');
  initFloatRate();
  initSpecIdea();
  
}

function checkFLoatRate(){
   var k=0;
   for(i=0;i<RiskFloatRateGrid.mulLineCount;i++)
   {
    if(RiskFloatRateGrid.getChkNo(i))
    {
       var tOldFloatrate = RiskFloatRateGrid.getRowColData(i,3);
       var tNewFloatrate = RiskFloatRateGrid.getRowColData(i,4);
       if(tOldFloatrate == tNewFloatrate)
       {
         alert("��["+(i+1)+"]�еĸ���������ԭ���ĸ���������ͬ,���в��ᱻ�޸ģ�");
       }
       k++;
     }   
   }
   if(k==0)
   {
      alert("��ѡ��һ����¼��");
      return false;
   }
   return true;
}

function returnParent(){
  top.close();
	
}

//tongmeng 2009-05-09 add
//ɾ��Ա����Լ
function deleteSpec()
{
	 fm.SpecOperate.value = "DELETE&&YGSPEC";
	 lockScreen('lkscreen');
	 fm.submit();
}