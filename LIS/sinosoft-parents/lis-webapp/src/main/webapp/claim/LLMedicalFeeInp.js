var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var mySql = new SqlClass();
var checkAccDate;//����У��ĳ�������,��������������ڶ����ڣ���ȡ������Ǹ�����
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������ҽԺ��Ϣ��ѯ����������������
    �� �� �ˣ�����
    �޸����ڣ�2005.05.13
   =========================================================================
**/

//ѡ���˵�����
function choiseType()
{
	//����
    if (fm.MainFeeType.value == 'A')
	  {
        divMedFee1.style.display="";
        queryGrid1();
    }
    //סԺ
    else if (fm.MainFeeType.value == 'B')
    {
        divMedFee2.style.display="";
        queryGrid2();
    }
    //���ò���
    else if (fm.MainFeeType.value == 'C')
    {
        divMedFee7.style.display="";
        queryGrid7();
    }
    //��������(ԭ�˲�)
    else if (fm.MainFeeType.value == 'L')
    {
        divMedFee3.style.display="";
        queryGrid3();
    }
    //����
    else if (fm.MainFeeType.value == 'T')
    {
        divMedFee5.style.display="";
        queryGrid5();
    }   
    //�籣����������
    else if (fm.MainFeeType.value == 'D')
    {
        divMedFee6.style.display="";
        queryGrid6();
    }
    //MMAҽ���˵���Ϣ¼��
    else if (fm.MainFeeType.value == 'H')
    {
        divMedFee8.style.display="";
        queryGrid8();
    }
}

//��ʾ�����˵�
function showAll()
{
    divMedFee1.style.display="";
    queryGrid1();
    divMedFee2.style.display="";
    queryGrid2();
    divMedFee3.style.display="";
    queryGrid3();
    
    //2008-12-8 zhangzheng ����MSû�����ֲ�Ʒ������,���������ض����������ּ������ض�����¼����
    //divMedFee4.style.display="";
    //queryGrid4();
    
    divMedFee5.style.display="";
    queryGrid5();
    divMedFee6.style.display="";
    queryGrid6();
    divMedFee8.style.display="";
    queryGrid8();
    //alert("fm.GrpFlag.value:"+fm.GrpFlag.value);
    if(fm.GrpFlag.value!=1){//alert(77);
	    divMedFee7.style.display="";
	    queryGrid7();
    }
}

//���������˵�
function showNone()
{
    fm.MainFeeType.value = "";
    fm.MainFeeName.value = "";
    divMedFee1.style.display="none";
    divMedFee2.style.display="none";
    divMedFee3.style.display="none";
    //divMedFee4.style.display="none";
    divMedFee5.style.display="none";
    divMedFee6.style.display="none";
    divMedFee7.style.display="none";
}

//������������
function setOperType()
{
	//�ض�����
    if (fm.OperationType.value == 'D')
    {
        fm.llOperType.value = 'lloperationtype';
    }
    //���ּ���
    if (fm.OperationType.value == 'E')
    {
        fm.llOperType.value = 'lldiseasetype';
    }
    //�ض�����
    if (fm.OperationType.value == 'F')
    {
        fm.llOperType.value = 'llspegivetype';
    }
}

//����Ҫ������
function setFactorType()
{
    if (fm.FactorType.value == 'TP')
    {
        fm.llFactorType.value = 'llfactypesuccor';
    }
    else if (fm.FactorType.value == 'med')
    {
        fm.llFactorType.value = 'llfactypemed';
    }
}

//������Ϣ��ѯ
function queryGrid1()
{
    var tclaimNo = fm.claimNo.value;
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16--------------------------17
   /* var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,"
    	       + " b.SelfAmnt, (select (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end)  from ldperson k where k.customerno=d.customerno) as �籣��־"
    	       + " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) �����¹�����,"
    	       + " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
               + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b ,llcase d ,llregister e where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno   and a.customerno = b.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
               + " and a.customerno = b.customerno"
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'A'"
               + " order by a.MainFeeNo";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql1");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);         
//    prompt("������Ϣ��ѯsql",strSql);
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeClinicInpGrid);
    }
}

//סԺ��Ϣ��ѯ
function queryGrid2()
{
    var tclaimNo = fm.claimNo.value;
    /*var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, b.SelfAmnt,a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo"
    			+ " , (select (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end)  from ldperson k where k.customerno=d.customerno) as �籣��־"
    			+ " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) �����¹����� ,"
    			+ " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
    			+ " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b ,llcase d ,llregister e  where 1=1 "
                + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno  and a.customerno = b.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
                + " and a.HospitalCode = c.HospitalCode "
                + " and a.ClmNo='" + tclaimNo + "'"
                + " and b.FeeItemType = 'B'"
                + " order by a.MainFeeNo";*/
    		mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql2");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);   
    //prompt("סԺ��Ϣ��ѯsql",strSql);
    
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeInHosInpGrid);
    }
}

//�˲���Ϣ��ѯ
function queryGrid3()
{
    var tclaimNo = fm.claimNo.value;
    //-----------------------1---------2---------3-------4-----5----------6--------------7------------8-------9----10-------11---------12-----13
   /* var strSql = " select defotype,defograde,DefoName,defocode,DefoCodeName,deformityrate,appdeformityrate,realrate,clmno,caseno,serialno,customerno,JudgeOrganName,JudgeDate,adjremark"
               + " from LLCaseInfo where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " order by serialno";*/
       		mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql3");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);  
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeCaseInfoGrid);
    }
}

//������Ϣ��ѯ
function queryGrid4()
{
    var tclaimNo = fm.claimNo.value;
    //-------------------------1--------------2-------------3----------4------5-------6------7----8-----9-----10----------11
   /* var strSql = " select operationtype,operationcode,operationname,oplevel,opgrade,opfee,SelfAmnt,mainop,clmno,caseno,serialno,customerno,UnitName,DiagnoseDate"
               + " from LLOperation where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " order by serialno";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql9");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
		
    //prompt("������Ϣ��ѯ",strSql);
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeOperGrid);
    }
}

//������Ϣ��ѯ
function queryGrid5()
{
    var tclaimNo = fm.claimNo.value;
    //------------------------1-----------2--------3----------4----------5------6------7----------8
    /*var strSql = " select factortype,factorcode,factorname,factorvalue,SelfAmnt,clmno,caseno,serialno,customerno,UnitName,StartDate,EndDate,"
    		   + " (select codename from ldcode where codetype='deductreason' and code=adjreason),adjreason,adjremark"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " and FeeItemType = 'T'"
               + " order by serialno";*/
             mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql4");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
    //prompt("������Ϣ��ѯ",strSql);
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeOtherGrid);
    }
}

//�籣������������ѯ
function queryGrid6()
{
    var tclaimNo = fm.claimNo.value;
    //------------------------1-----------2--------3----------4----------5------6------7----------8
    /*var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,AdjRemark"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " and FeeItemType = 'D'"
               + " order by serialno";*/
                 mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql5");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeThreeGrid);
    }
}


//���ò�����Ŀ��Ϣ��ѯ
function queryGrid7()
{
    var tclaimNo = fm.claimNo.value;

    /*var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, '',"
    	       + " a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,"
    	       + " b.SelfAmnt, (select (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end)  from ldperson k where k.customerno=d.customerno) as �籣��־"
   			   + " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) �����¹�����,"
   			   + " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
   			   + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b ,llcase d ,llregister e  where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'C'"
               + " order by a.MainFeeNo";*/
       mySql = new SqlClass();        
    	mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql6");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
    //prompt("���ò�����Ŀ��Ϣ��ѯ",strSql)
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeCompensateInpGrid);
    }
}

//MMA�����˵���ѯ
function queryGrid8()
{
    var tclaimNo = fm.claimNo.value;

//    var strSql = " select b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, '',"
//    	       + " a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,"
//    	       + " b.SelfAmnt, (select (case when trim((case when SocialInsuFlag is not null then SocialInsuFlag else '0' end)) = '1' then '��' else '��' end)  from ldperson k where k.customerno=d.customerno) as �籣��־"   
//    	       + " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) �����¹�����,"  
//    	       + " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
//   			   + " from LLFeeMain a,LLCaseReceipt b ,llcase d ,llregister e  where 1=1 "
//               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
//               + " and a.ClmNo='" + tclaimNo + "'"
//               + " and b.FeeItemType = 'H'"
//               + " order by a.MainFeeNo";
   /* mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql6");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);*/
    //prompt("���ò�����Ŀ��Ϣ��ѯ",strSql)
    //��ʾ��������
    
    
    sql_id = "LLMedicalFeeInpSql10";
    my_sql = new SqlClass();
    my_sql.setResourceName("claim.LLMedicalFeeInpInputSql"); //ָ��ʹ�õ�properties�ļ���
    my_sql.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    my_sql.addSubPara(document.all('custNo').value);//ָ������Ĳ���
    my_sql.addSubPara(tclaimNo);//ָ������Ĳ���
    str_sql = my_sql.getString();
    
    var arr = easyExecSql(str_sql);
    if (arr)
    {
        displayMultiline(arr,MedFeeHospitalGrid);
    }
}


//��ѯ�˲��⸶����
function queryDeformityRate()
{
    if(fm.DefoCode.value != "" && fm.DefoCode.value !=null)
    {
       /* var strSql = " select t.deforate from LLParaDeformity t where "
                   + " t.defocode='" + fm.DefoCode.value + "'";*/
        mySql = new SqlClass();
        mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql7");
		mySql.addSubPara(fm.DefoCode.value); 

        //��ʾ��������
        var arr = easyExecSql(mySql.getString());
//        alert(arr)
        if (arr)
        {
            fm.AppDeformityRate.value = arr;
            fm.DeformityRate.value = arr;
            fm.RealRate.value = arr;
        }
    }
}

/*�ύ��̨ǰ��ҳ������У��*/
function beforeClinicSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("ClinicHosID").value == null || trim(document.all("ClinicHosID").value) ==''){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.ClinicHosID.focus();
         return false;
    }else if( document.all("ClinicHosName").value == null || trim(document.all("ClinicHosName").value) == '' ){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.ClinicHosName.focus();
         return false;
    }else if( document.all("ClinicHosGrade").value == null || trim(document.all("ClinicHosGrade").value) == '' ){
         alert('[ҽԺ�ȼ�]����Ϊ�գ�');
         fm.ClinicHosGrade.focus();
         return false;
    }else if( document.all("ClinicStartDate").value == null || trim(document.all("ClinicStartDate").value) == '' ){
         alert('[��ʼ����]����Ϊ�գ�');
         fm.ClinicStartDate.focus();
         return false;
    }else if( document.all("ClinicEndDate").value == null || trim(document.all("ClinicEndDate").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.UClinicEndDate.focus();
         return false;
    }else if( document.all("ClinicMedFeeType").value == null || trim(document.all("ClinicMedFeeType").value) == '' ){
         alert('[���ô���]����Ϊ�գ�');
         fm.ClinicMedFeeType.focus();
         return false;
    }else if( document.all("ClinicMedFeeTypeName").value == null || trim(document.all("ClinicMedFeeTypeName").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.ClinicMedFeeTypeName.focus();
         return false;
    }else if( document.all("ClinicFeeSum").value == null || trim(document.all("ClinicFeeSum").value) == '' ){
         alert('[���ý��]����Ϊ�գ�');
         fm.ClinicFeeSum.focus();
         return false;
    }
    return true;

}

/*[��������]�ύ����̨*/
function submitForm()
{
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.hideOperate.value=mOperate;
    document.getElementById("fm").submit(); //�ύ
}

/*[��������]�������,���������ݷ��غ�ִ�еĲ���*/
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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
        mOperate = '';
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        if ( fm.currentInput.value=="1")//����
        {
            initMedFeeClinicInpGrid(); 
            queryGrid1();
            //��ձ�
//            fm.ClinicMainFeeNo.value = "";
//            fm.ClinicHosID.value = "";
//            fm.ClinicHosName.value = "";
            fm.ClinicMedFeeType.value = "";
            fm.ClinicMedFeeTypeName.value = "";
            fm.ClinicMedFeeSum.value = "";//���ý��
            fm.selfMedFeeSum.value = "";//�Է�/�Ը���Ŀ���
            //fm.ClinicStartDate.value = "";
            //fm.ClinicEndDate.value = "";
            fm.ClinicDayCount1.value = "";//����
            fm.ClinicDayCount2.value = "";//�����¹���������
            //fm.ClinicDayCount3.value = "";
        }
        if ( fm.currentInput.value=="2")//ҽԺ
        {
            initMedFeeInHosInpGrid(); 
            queryGrid2();
            //��ձ�
//            fm.HosMainFeeNo.value = "";
//            fm.InHosHosID.value = "";
//            fm.InHosHosName.value = "";
            fm.InHosMedFeeType.value = "";
            fm.InHosMedFeeTypeName.value = "";
            fm.InHosMedFeeSum.value = "";
            fm.selfInHosFeeSum.value = "";//�Է�/�Ը���Ŀ���
            //fm.InHosStartDate.value = "";
            //fm.InHosEndDate.value = "";
            fm.InHosDayCount1.value = "";
            fm.InHosDayCount2.value = "";
            //fm.InHosDayCount3.value = "";
        }
        if ( fm.currentInput.value=="3")//�˲�
        {
            initMedFeeCaseInfoGrid();
            queryGrid3();
            //��ձ�
            fm.DefoType.value = "";
            fm.DefoTypeName.value = "";
            fm.DefoGrade.value = "";
            fm.DefoGradeName.value = "";
            fm.DefoCode.value = "";
            fm.DefoCodeName.value = "";
            fm.DeformityRate.value = "";
            fm.JudgeOrganName.value = "";
            fm.JudgeDate.value = "";
        }
        if ( fm.currentInput.value=="4")//�����ؼ�
        {
            initMedFeeOperGrid();
            queryGrid4();
            //��ձ�
            fm.OperationType.value = "";
            fm.OperationTypeName.value = "";
            fm.OperationCode.value = "";
            fm.OperationName.value = "";
            fm.UnitName.value = "";
            fm.OpFee.value = "";//���ý��
            fm.selfOpFeeSum.value = "";
            fm.DiagnoseDate.value = "";
        }
        if ( fm.currentInput.value=="5")//����
        {
            initMedFeeOtherGrid();
            queryGrid5();
            //��ձ�
            fm.FactorType.value = "";
            fm.FactorTypeName.value = "";
            fm.FactorCode.value = "";
            fm.FactorName.value = "";
            fm.FactorValue.value = "";
            fm.FactorUnitName.value = "";
            fm.selfFactorFeeSum.value = "";
            //fm.FactorStateDate.value = "";
            //fm.FactorEndDate.value = "";
        }
        if ( fm.currentInput.value=="6")//�籣����������
        {
            initMedFeeThreeGrid();
            queryGrid6();
            //��ձ�
//            fm.FeeThreeType.value = "";
//            fm.FeeThreeTypeName.value = "";
            fm.FeeThreeCode.value = "";
            fm.FeeThreeName.value = "";
            fm.FeeThreeValue.value = "";
            fm.FeeThreeUnitName.value = "";
//            fm.FeeThreeStateDate.value = "";
//            fm.FeeThreeEndDate.value = "";
        }
        
        if ( fm.currentInput.value=="7")//���ò�����Ŀ
        {
        	initMedFeeCompensateInpGrid(); 
            queryGrid7();
            //��ձ�
//            fm.HosMainFeeNo.value = "";
//            fm.InHosHosID.value = "";
//            fm.InHosHosName.value = "";
            fm.MedFeeCompensateType.value = "";
            fm.MedFeeCompensateTypeName.value = "";
            fm.MedFeeCompensateSum.value = "";
            fm.selfMedFeeCompensateFeeSum.value = "";//�Է�/�Ը����
            //fm.InHosStartDate.value = "";
            //fm.InHosEndDate.value = "";
            fm.MedFeeCompensateEndDateInHosDayCount1.value = "";
            fm.MedFeeCompensateEndDateInHosDayCount2.value = "";
            //fm.MedFeeCompensateEndDateInHosDayCount3.value = "";
        }
        if ( fm.currentInput.value=="8")//MMA
        {
        	  initMedFeeHospitalGrid();
            queryGrid8()
            //��ձ�
            fm.HospitalFeeNo.value = "";
            fm.ClinicMedFeeType8.value = "";
            fm.ClinicMedFeeType8Name.value = "";
            fm.ClinicStartDate8.value = "";
            fm.ClinicEndDate8.value = "";
            fm.HospMedFeeSum.value = "";
            fm.ClinicDayCount8.value = "";
            fm.Remark8.value = "";
        }
    }
}


/*
 * ����У��,У���������ڶ��������ڵ�ǰ���ڣ��ҵ�2�����ڲ������ڵ�1������
 * Date1,����ĵ�һ������,�����ǿ�ʼ����
 * Date2 ����ĵڶ�������,�����ǽ�������
 * tDateName ������������(������,סԺ��)��������ɵ�������ʾ����
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var tStartDate  =  tDate1;//�¹�����
    var tEndDate =  tDate2;//������������

       
    //��鿪ʼ����
    if (tStartDate == null || tStartDate == '')
    {
        alert(tDateName+"��ʼ���ڲ���Ϊ��!");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,tStartDate,'D') > 0)
        {
      		alert(tDateName+"��ʼ���ڲ������ڵ�ǰ����!");
            return false; 
        }
    }
    

    //У���������
    if (tEndDate == null || tEndDate == '')
    {
        alert(tDateName+"�������ڲ���Ϊ��!");
        return false;
    }
    else
    {//alert("mCurrentDate:"+mCurrentDate);
    //alert("tEndDate:"+tEndDate);
    	
       	//�ȽϽ������ں͵�ǰ����
    	if (dateDiff(mCurrentDate,tEndDate,'D') > 0)
        {
        	alert(tDateName+"�������ڲ������ڵ�ǰ����!");
            return false; 
        }

        //�Ƚϳ������ں͵�֤��ʼ����
    	//alert(dateDiff(tStartDate,tEndDate,'D'));
    	if (dateDiff(tStartDate,tEndDate,'D') < 0)
        {
        	alert(tDateName+"��ʼ���ڲ��ܴ��ڽ�������!");
            return false; 
        }
    }
    
    
    //У�鿪ʼ���ںͳ�������
    if(dateDiff(checkAccDate,fm.ClinicStartDate.value,'D')<0)
    {
        if(confirm(tDateName+"��ʼ�������ڳ�������,�Ƿ����!"))
        {
            fm.DealFlag.value = "0";
        }
        else
        {
            return false;
        }
    }
    else
    {
        fm.DealFlag.value = "1";
    }
    
    return true;
}


/*����[����]��ť��Ӧ����*/
function AddClick1()
{
    //�ǿռ���
    if (fm.ClinicMainFeeNo.value == null || fm.ClinicMainFeeNo.value == "")
    {
        alert("�˵��Ų���Ϊ�գ�");
        return;
    }
    if (fm.ClinicHosID.value == null || fm.ClinicHosID.value == "")
    {
        alert("ҽԺ���Ʋ���Ϊ�գ�");
        return;
    }    
    if (fm.ClinicMedFeeType.value == null || fm.ClinicMedFeeType.value == "")
    {
        alert("�������Ͳ���Ϊ�գ�");
        return;
    }
    
	//����У��
	if (checkDate(fm.ClinicStartDate.value,fm.ClinicEndDate.value,"����") == false)
	{
	     return false;
	}    
    
    if (fm.ClinicMedFeeSum.value == null || fm.ClinicMedFeeSum.value == "")
    {
        alert("���ý���Ϊ�գ�");
        return;
    }
    
    if (fm.MedCurrency.value == null || fm.MedCurrency.value == "")
    {
        alert("���ֲ���Ϊ�գ�");
        return;
    }
    
    
    if (fm.selfMedFeeSum.value == null || fm.selfMedFeeSum.value == "")
    {
        alert("�Է�/�Ը�����Ϊ�գ�");
        return;
    }

    if(fm.selfMedFeeSum.value!=0){
	    if (fm.theReason1.value == null || fm.theReason1.value == "")
	    {
	    	alert("�ۼ�ԭ����Ϊ�գ�");
	    	return;
	    }
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "1";
    fm.action = "./LLMedicalFeeInp1Save.jsp";
    submitForm();
    
    //wyc add
    initClick1();
    
}

/*סԺ[����]��ť��Ӧ����*/
function AddClick2()
{

    //�ǿռ���
    if (fm.HosMainFeeNo.value ==  null || fm.HosMainFeeNo.value == "")
    {
        alert("�˵��Ų���Ϊ�գ�");
        return;
    }
    if (fm.InHosHosID.value ==  null || fm.InHosHosID.value == "")
    {
        alert("ҽԺ���Ʋ���Ϊ�գ�");
        return;
    }    
    if (fm.InHosMedFeeType.value ==  null || fm.InHosMedFeeType.value == "")
    {
        alert("�������Ͳ���Ϊ�գ�");
        return;
    }
    
	//����У��
	if (checkDate(fm.InHosStartDate.value,fm.InHosEndDate.value,"סԺ") == false)
	{
	     //return false;
	}    
    
    if (fm.InHosMedFeeSum.value == null || fm.InHosMedFeeSum.value == "")
    {
        alert("���ý���Ϊ�գ�");
        return;
    }
    
    if (fm.InHosMedCurrency.value == null || fm.InHosMedCurrency.value == "")
    {
        alert("���ֲ���Ϊ�գ�");
        return;
    }
    
    if (fm.selfInHosFeeSum.value == null || fm.selfInHosFeeSum.value == "")
    {
        alert("�Է�/�Ը�����Ϊ�գ�");
        return;
    }
    if(fm.selfInHosFeeSum.value != "0"){
	    if (fm.theReason2.value == null || fm.theReason2.value == "")
	    {
	        alert("�ۼ�ԭ����Ϊ�գ�");
	        return;
	    }
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "2";
    fm.action = "./LLMedicalFeeInp2Save.jsp";
    submitForm();
    
    // add wyc 
    initClick2();
}

/*��������(ԭ�˲�)[����]��ť��Ӧ����*/
function AddClick3()
{
    //�ǿռ���
    if (fm.DefoType.value ==  null ||fm.DefoType.value == "")
    {
        alert("����������������ͣ�");
        return;
    }
    if ((fm.DefoGrade.value == "")&&((fm.DefoType.value=='LF')||(fm.DefoType.value=='LZ')))
    {
        alert("�����������������");
        return;
    }
    if (fm.DefoCode.value == "")
    {
        alert("����������������룡");
        return;
    }
    //����ϵͳ����
    if(dateDiff(fm.JudgeDate.value,mCurrentDate,'D') < 0)
    {
        alert("�������ڴ��ڵ�ǰ���ڣ�");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "3";
    fm.action = "./LLMedicalFeeInp3Save.jsp";
    submitForm();
    
    //add wyc
    fm.Remark9.value = "";
}


/*����[����]��ť��Ӧ����*/
function AddClick4()
{
    //�ǿռ���
    if (fm.OperationType.value == null || fm.OperationType.value == "")
    {
        alert("�������Ͳ���Ϊ�գ�");
        return;
    }    
    if (fm.OperationCode.value == null || fm.OperationCode.value == "")
    {
        alert("�������ʹ��벻��Ϊ�գ�");
        return;
    }
    if (fm.UnitName.value == null || fm.UnitName.value == "")
    {
        alert("ҽ�ƻ������Ʋ���Ϊ�գ�");
        return;
    }
    
    if (fm.DiagnoseDate.value == null || fm.DiagnoseDate.value == "")
    {
        alert("ȷ�����ڲ���Ϊ�գ�");
        return;
    }    
    
    if (dateDiff(fm.DiagnoseDate.value,mCurrentDate,'D') < 0)
    {
        alert("ȷ�����ڲ��ܴ��ڵ�ǰ����!");
        return;
    }
    
    if (fm.OpCurrency.value == null || fm.OpCurrency.value == "")
    {
        alert("���ֲ���Ϊ�գ�");
        return;
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "4";
    fm.action = "./LLMedicalFeeInp4Save.jsp";
    submitForm();
}

/*����[����]��ť��Ӧ����*/
function AddClick5()
{
    //�ǿռ���
    if (fm.FactorType.value == null || fm.FactorType.value == "")
    {
        alert("���ַ������Ͳ���Ϊ�գ�");
        return;
    }    
    if (fm.FactorCode.value == null || fm.FactorCode.value == "")
    {
        alert("���ַ��ô��벻��Ϊ�գ�");
        return;
    }

    if (fm.FactorValue.value == null || fm.FactorValue.value == "")
    {
        alert("���ַ��ý���Ϊ�գ�");
        return;
    }
    
    if (fm.FactorCurrency.value == null || fm.FactorCurrency.value == "")
    {
        alert("���ֲ���Ϊ�գ�");
        return;
    }
    
    if (fm.selfFactorFeeSum.value == null || fm.selfFactorFeeSum.value == "")
    {
        alert("�Է�/�Ը�����Ϊ�գ�");
        return;
    }
    if(fm.selfFactorFeeSum.value != "0"){
	    if (fm.theReason4.value == null || fm.theReason4.value == "")
	    {
	    	alert("�ۼ�ԭ����Ϊ�գ�");
	    	return;
	    }
    }
 
	//����У��
	if (checkDate(fm.FactorStateDate.value,fm.FactorEndDate.value,"���ַ���") == false)
	{
	     return false;
	}    
    
    
    if (fm.FactorUnitName.value == null || fm.FactorUnitName.value == "")
    {
        alert("����������Ʋ���Ϊ�գ�");
        return;
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "5";
    fm.action = "./LLMedicalFeeInp5Save.jsp";
    submitForm();
    
    //add wyc
    initClick5();
}

/*�籣����������[����]��ť��Ӧ����*/
function AddClick6()
{
	//�ǿռ���
    if (fm.FeeThreeType.value == "" || fm.FeeThreeType.value == null)
    {
        alert("������������ʹ��룡");
        return;
    }
    if (fm.FeeThreeTypeName.value == "" || fm.FeeThreeTypeName.value == null)
    {
        alert("����������������ƣ�");
        return;
    }
    if (fm.FeeThreeCode.value == "" || fm.FeeThreeCode.value == null)
    {
        alert("��������ô��룡");
        return;
    }
    if (fm.FeeThreeUnitName.value == "" || fm.FeeThreeUnitName.value == null)
    {
        alert("���������������ƣ�");
        return;
    }

    if (fm.FeeThreeCurrency.value == null || fm.FeeThreeCurrency.value == "")
    {
        alert("���ֲ���Ϊ�գ�");
        return;
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "6";
    fm.action = "./LLMedicalFeeInp6Save.jsp";
    submitForm();
    
    //add wyc
    initClick6();
}


/*���ò�����Ŀ[����]��ť��Ӧ����*/
function AddClick7()
{
    //�ǿռ���
    if (fm.CompensateMainFeeNo.value == null || fm.CompensateMainFeeNo.value == "")
    {
        alert("�˵��Ų���Ϊ�գ�");
        return;
    }
    if (fm.MedFeeCompensateHosID.value == null || fm.MedFeeCompensateHosID.value == "")
    {
        alert("ҽԺ���Ʋ���Ϊ�գ�");
        return;
    }    
    if (fm.MedFeeCompensateType.value == null || fm.MedFeeCompensateType.value == "")
    {
        alert("�������Ͳ���Ϊ�գ�");
        return;
    }
    
    //����У��
	if (checkDate(fm.MedFeeCompensateStartDate.value,fm.MedFeeCompensateEndDate.value,"���ò���") == false)
	{
	     return false;
	}    
    
    if (fm.MedFeeCompensateSum.value == null || fm.MedFeeCompensateSum.value == "")
    {
        alert("���ý���Ϊ�գ�");
        return;
    }
    
    if (fm.MedFeeCompensateCurrency.value == null || fm.MedFeeCompensateCurrency.value == "")
    {
        alert("���ֲ���Ϊ�գ�");
        return;
    }
    
    if (fm.selfMedFeeCompensateFeeSum.value == null || fm.selfMedFeeCompensateFeeSum.value == "")
    {
        alert("�Է�/�Ը�����Ϊ�գ�");
        return;
    }
    if(fm.selfMedFeeCompensateFeeSum.value != "0"){
	    if (fm.theReason3.value == null || fm.theReason3.value == "")
	    {
	    	alert("�ۼ�ԭ����Ϊ�գ�");
	    	return;
	    }
    }
 
    mOperate="INSERT";
    fm.currentInput.value = "7";
    fm.action = "./LLMedicalFeeInp7Save.jsp";
    submitForm();
    
    //add wyc
    initClick7();
}

/*MMA�����˵�¼��*/
function AddClick8()
{
    //�ǿ�У��
    if (fm.HospitalFeeNo.value == null || fm.HospitalFeeNo.value == "")
    {
        alert("�˵��Ų���Ϊ��");
        return;
    }
    if (fm.ClinicMedFeeType8.value == null || fm.ClinicMedFeeType8.value == "")
    {
        alert("�������Ͳ���Ϊ��");
        return;
    }    
    
	//����У��
	if (checkDate(fm.ClinicStartDate8.value,fm.ClinicEndDate8.value,"MmaKind") == false)
	{
	     //return false;
	}    
    
    if (fm.HospMedFeeSum.value == null || fm.HospMedFeeSum.value == "")
    {
        alert("���ý���Ϊ��");
        return;
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "8";
    fm.action = "./LLMedicalFeeInp8Save.jsp";
    submitForm();
}


/*����[ɾ��]��ť��Ӧ����*/
function DeleteClick1()
{
    if(MedFeeClinicInpGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "1";
            fm.action = "./LLMedicalFeeInp1Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
    
    // wyc add
    initClick1();
}

/*סԺ[ɾ��]��ť��Ӧ����*/
function DeleteClick2()
{
    if(MedFeeInHosInpGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "2";
            fm.action = "./LLMedicalFeeInp2Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
    
    //add wyc 
    initClick2();
}


/*�˲�[ɾ��]��ť��Ӧ����*/
function DeleteClick3()
{
    if(MedFeeCaseInfoGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "3";
            fm.action = "./LLMedicalFeeInp3Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
    
  //add wyc
    fm.Remark9.value = "";
    
}

/*����[ɾ��]��ť��Ӧ����*/
function DeleteClick4()
{
    if(MedFeeOperGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "4";
            fm.action = "./LLMedicalFeeInp4Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
}

/*����[ɾ��]��ť��Ӧ����*/
function DeleteClick5()
{
    if(MedFeeOtherGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "5";
            fm.action = "./LLMedicalFeeInp5Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
  //add wyc
    initClick5();
}

/*�籣����������[ɾ��]��ť��Ӧ����*/
function DeleteClick6()
{
    if(MedFeeThreeGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "6";
            fm.action = "./LLMedicalFeeInp6Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
    
  //add wyc
    initClick6();
}


/*���ò�����Ŀ[ɾ��]��ť��Ӧ����*/
function DeleteClick7()
{
    if(MedFeeCompensateInpGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "7";
            fm.action = "./LLMedicalFeeInp7Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
    
    //add wyc 
    initClick7();
    
}

/*MMA�����˵�ɾ��*/
function DeleteClick8()
{
    if(MedFeeHospitalGrid.getSelNo() > 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "8";
            fm.action = "./LLMedicalFeeInp8Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼");
        return;
    }
}

/*[������ϢMulLine]�Ĵ�������*/
function getMedFeeClinicInpGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeClinicInpGrid.getSelNo();
    
    //��ֵҽԺ��Ϣ
    fm.ClinicHosID.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,1);             //ҽԺ���
    fm.ClinicHosName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,2);            //ҽԺ����
    fm.ClinicHosGrade.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,3);           //ҽԺ�ȼ�

    //��ֵ������Ϣ
    fm.ClinicStartDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,4);
    fm.ClinicEndDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,5);
    fm.ClinicDayCount1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,6);

    //��ֵ������Ϣ
    fm.ClinicMedFeeType.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,7);
    fm.ClinicMedFeeTypeName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,8);
    fm.MedCurrency.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,9);
    fm.ClinicMedFeeSum.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,10);//���ý��

    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,12);       //�ⰸ��
    fm.caseNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,13);        //�ְ���
    fm.custNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,14);        //�ͻ���
    fm.ClinicMainFeeNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,15);     //�ʵ���
    fm.feeDetailNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,16);   //�ʵ���ϸ��
    
    fm.selfMedFeeSum.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,17); //�Է�/�Ը����
    //alert("�Է�/�Ը����:"+fm.selfMedFeeSum.value);
    
    fm.DealFlag.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,19); //������:1-��������,0-����ǰ����
    //alert("���ô�����:"+fm.DealFlag.value);
    
    fm.ClinicDayCount2.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,20); //�����¹���������
    
    fm.theReason1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,22);
    fm.theReasonName1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,21);
    fm.Remark1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,23);
    //������¼�������־����ʾ����������Աע��
    if (fm.DealFlag.value == "0")
    {
        alert("���˵���ʼ�������ڳ�������,��ע��!");
    }

    
    //���Ĳ�����ť
//    fm.saveButton1.disabled = true;
    fm.deleteButton1.disabled = false;
}

/*[סԺ��ϢMulLine]�Ĵ�������*/
function getMedFeeInHosInpGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeInHosInpGrid.getSelNo();

    //��ֵҽԺ��Ϣ
    fm.InHosHosID.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);             //ҽԺ���
    fm.InHosHosName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);            //ҽԺ����
    fm.InHosHosGrade.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);           //ҽԺ�ȼ�

    //��ֵ������Ϣ
    fm.InHosStartDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
    fm.InHosEndDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
    fm.InHosDayCount1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,6);

    //��ֵ������Ϣ
    fm.InHosMedFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,7);
    fm.InHosMedFeeTypeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,8);
    
    fm.InHosMedCurrency.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,9);
    fm.InHosMedFeeSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,10); //���ý��
    fm.selfInHosFeeSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,11); //�Է�/�Ը����  
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);       //�ⰸ��
    fm.caseNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);        //�ְ���
    fm.custNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,14);        //�ͻ���
    
    fm.HosMainFeeNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);     //�ʵ���
    fm.feeDetailNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,16);   //�ʵ���ϸ��
       
    fm.DealFlag.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,18); //������:1-��������,0-����ǰ����
    fm.InHosDayCount2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,19); //�����¹���������
    
    fm.theReason2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21); 
    fm.theReasonName2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,20); 
    fm.Remark2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,22); 
    
    //������¼�������־����ʾ����������Աע��
    if (fm.DealFlag.value == "0")
    {
        alert("���˵���ʼ�������ڳ�������,��ע��!");
    }
}


/*[�˲���ϢMulLine]�Ĵ�������*/
function getMedFeeCaseInfoGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeCaseInfoGrid.getSelNo();

    //��ֵ�˲���Ϣ
    fm.DefoType.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,1);             //
    fm.DefoGrade.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,2);            //
    fm.DefoGradeName.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,3);           //
    fm.DefoCode.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,4);
    fm.DefoCodeName.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,5);
    
    fm.DeformityRate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,6);
    fm.AppDeformityRate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,7);
    fm.RealRate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,8);
    
    fm.JudgeOrganName.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,13);
    fm.JudgeDate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,14);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,9);       //�ⰸ��
    fm.caseNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,10);        //�ְ���
    fm.SerialNo3.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,11);     //��ˮ��
    fm.custNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,12);        //�ͻ���
    
    fm.Remark9.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,15);        //�ͻ���

}

/*[������ϢMulLine]�Ĵ�������*/
function getMedFeeOperGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeOperGrid.getSelNo();

    //��ֵ������Ϣ
    fm.OperationType.value = MedFeeOperGrid.getRowColData(tNo - 1,1);      //
    fm.OperationCode.value = MedFeeOperGrid.getRowColData(tNo - 1,2);      //
    fm.OperationName.value = MedFeeOperGrid.getRowColData(tNo - 1,3);      //
    
    fm.OpCurrency.value = MedFeeOperGrid.getRowColData(tNo - 1,6);
    fm.OpFee.value = MedFeeOperGrid.getRowColData(tNo - 1,7);
    fm.selfOpFeeSum.value = MedFeeOperGrid.getRowColData(tNo - 1,8);

    fm.UnitName.value = MedFeeOperGrid.getRowColData(tNo - 1,14);
    //alert("��������:"+fm.UnitName.value);
    
    fm.DiagnoseDate.value = MedFeeOperGrid.getRowColData(tNo - 1,15);
    //alert("ȷ������:"+fm.DiagnoseDate.value);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeOperGrid.getRowColData(tNo - 1,10);       //�ⰸ��
    fm.caseNo.value = MedFeeOperGrid.getRowColData(tNo - 1,11);        //�ְ���
    fm.SerialNo4.value = MedFeeOperGrid.getRowColData(tNo - 1,12);     //��ˮ��
    //alert("�������:"+fm.SerialNo4.value);
    
    fm.custNo.value = MedFeeOperGrid.getRowColData(tNo - 1,13);        //�ͻ���
    //alert("�ͻ���:"+fm.custNo.value);

}

/*[������ϢMulLine]�Ĵ�������*/
function getMedFeeOtherGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeOtherGrid.getSelNo();

    //��ֵ������Ϣ
    fm.FactorType.value = MedFeeOtherGrid.getRowColData(tNo - 1,1);      //
    fm.FactorCode.value = MedFeeOtherGrid.getRowColData(tNo - 1,2);      //
    fm.FactorName.value = MedFeeOtherGrid.getRowColData(tNo - 1,3);      //
    fm.FactorCurrency.value = MedFeeOtherGrid.getRowColData(tNo - 1,4);
    fm.FactorValue.value = MedFeeOtherGrid.getRowColData(tNo - 1,5);//���ý��
    fm.selfFactorFeeSum.value = MedFeeOtherGrid.getRowColData(tNo - 1,6);//�Է�/�Ը����
    //alert("fm.selfFactorFeeSum.value"+fm.selfFactorFeeSum.value);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,7);       //�ⰸ��
    fm.caseNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,8);        //�ְ���
    //alert("fm.caseNo.value"+fm.caseNo.value);
    fm.SerialNo5.value = MedFeeOtherGrid.getRowColData(tNo - 1,9);     //��ˮ��
    fm.custNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,10);        //�ͻ���
    //alert("fm.custNo.value"+fm.custNo.value);
    
    
    fm.FactorUnitName.value = MedFeeOtherGrid.getRowColData(tNo - 1,11);
    showOneCodeName('llfactype','FactorType','FactorTypeName');
    fm.FactorStateDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,12);
    fm.FactorEndDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,13);

    fm.theReason4.value = MedFeeOtherGrid.getRowColData(tNo - 1,15);
    fm.theReasonName4.value = MedFeeOtherGrid.getRowColData(tNo - 1,14);
    fm.Remark4.value = MedFeeOtherGrid.getRowColData(tNo - 1,16);
    //alert("fm.FactorEndDate.value"+fm.FactorEndDate.value);
    


}

/*[�籣����������MulLine]�Ĵ�������*/
function getMedFeeThreeGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeThreeGrid.getSelNo();

    //��ֵ������Ϣ
    fm.FeeThreeType.value = MedFeeThreeGrid.getRowColData(tNo - 1,1);      //
    fm.FeeThreeCode.value = MedFeeThreeGrid.getRowColData(tNo - 1,2);      //
    fm.FeeThreeName.value = MedFeeThreeGrid.getRowColData(tNo - 1,3);      //
    fm.FeeThreeCurrency.value = MedFeeThreeGrid.getRowColData(tNo - 1,4);
    fm.FeeThreeValue.value = MedFeeThreeGrid.getRowColData(tNo - 1,5);
    
    fm.FeeThreeUnitName.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
    fm.AdjRemark.value = MedFeeThreeGrid.getRowColData(tNo - 1,11);
//    showOneCodeName('llfactype','FeeThreeType','FeeThreeTypeName');
//    fm.FeeThreeStateDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    fm.FeeThreeEndDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,11);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,6);       //�ⰸ��
    fm.caseNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,7);        //�ְ���
    fm.SerialNo5.value = MedFeeThreeGrid.getRowColData(tNo - 1,8);     //��ˮ��
    fm.custNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,9);        //�ͻ���

}


/*[���ò�����ĿMulLine]�Ĵ�������*/
function getMedFeeCompensateInpGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeCompensateInpGrid.getSelNo();

    //��ֵҽԺ��Ϣ
    fm.MedFeeCompensateHosID.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,1);             //ҽԺ���
    fm.MedFeeCompensateHosName.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,2);            //ҽԺ����
    fm.InHosHosGrade.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,3);           //ҽԺ�ȼ�
    
    //��ֵ������Ϣ
    fm.MedFeeCompensateStartDate.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,4);
    fm.MedFeeCompensateEndDate.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,5);
    fm.MedFeeCompensateEndDateInHosDayCount1.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,6);

    //��ֵ������Ϣ
    fm.MedFeeCompensateType.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,7);
    fm.MedFeeCompensateTypeName.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,8);
    fm.MedFeeCompensateCurrency.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,9);
    fm.MedFeeCompensateSum.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,10);

    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,12);       //�ⰸ��
    fm.caseNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,13);        //�ְ���
    fm.custNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,14);        //�ͻ���
    fm.CompensateMainFeeNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,15);     //�ʵ���
    fm.feeDetailNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,16);   //�ʵ���ϸ��
    

    fm.selfMedFeeCompensateFeeSum.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,17); //�Է�/�Ը����  
    fm.DealFlag.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,19); //������:1-��������,0-����ǰ����
    fm.MedFeeCompensateEndDateInHosDayCount2.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,20); //�����¹���������
    
    fm.theReason3.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,22); //
    fm.theReasonName3.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,21); //
    fm.Remark3.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,23); //

        //������¼�������־����ʾ����������Աע��
    if (fm.DealFlag.value == "0")
    {
        alert("���˵���ʼ�������ڳ�������,��ע��!");
    }
}

/*MMA�Ĵ�������*/
function getMedFeeHospitalGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeHospitalGrid.getSelNo();
    
    //��ֵ������Ϣ
    fm.ClinicStartDate8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,1);
    fm.ClinicEndDate8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,2);
    fm.ClinicDayCount8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,3);

    //��ֵ������Ϣ
    fm.ClinicMedFeeType8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,4);
    fm.ClinicMedFeeType8Name.value = MedFeeHospitalGrid.getRowColData(tNo - 1,5);

    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,8);       //�ⰸ��
    fm.caseNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,9);        //�ְ���
    fm.custNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,10);        //�ͻ���
    fm.HospitalFeeNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,11);     //�ʵ���
    

    fm.HospMedFeeSum.value = MedFeeHospitalGrid.getRowColData(tNo - 1,6); //�Է�/�Ը����  
    fm.DealFlag.value = MedFeeHospitalGrid.getRowColData(tNo - 1,15); //������:1-��������,0-����ǰ����
    fm.ClinicDayCount8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,16); //�����¹���������
    fm.Remark8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,19); //
    
    //������¼�������־����ʾ����������Աע��
    if (fm.DealFlag.value == "0")
    {
        alert("���˵���ʼ�������ڳ�������,��ע��!");
    }
}

/*�ύ��̨ǰ��ҳ������У��*/
function beforeInHosSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("InHosHosID").value == null || trim(document.all("InHosHosID").value) ==''){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.InHosHosID.focus();
         return false;
    }else if( document.all("InHosHosName").value == null || trim(document.all("InHosHosName").value) == '' ){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.InHosHosName.focus();
         return false;
    }else if( document.all("InHosHosGrade").value == null || trim(document.all("InHosHosGrade").value) == '' ){
         alert('[ҽԺ�ȼ�]����Ϊ�գ�');
         fm.InHosHosGrade.focus();
         return false;
    }else if( document.all("InHosStartDate").value == null || trim(document.all("InHosStartDate").value) == '' ){
         alert('[��ʼ����]����Ϊ�գ�');
         fm.InHosStartDate.focus();
         return false;
    }else if( document.all("InHosEndDate").value == null || trim(document.all("InHosEndDate").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.UInHosEndDate.focus();
         return false;
    }else if( document.all("InHosMedFeeType").value == null || trim(document.all("InHosMedFeeType").value) == '' ){
         alert('[���ô���]����Ϊ�գ�');
         fm.InHosMedFeeType.focus();
         return false;
    }else if( document.all("InHosMedFeeTypeName").value == null || trim(document.all("InHosMedFeeTypeName").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.InHosMedFeeTypeName.focus();
         return false;
    }else if( document.all("InHosFeeSum").value == null || trim(document.all("InHosFeeSum").value) == '' ){
         alert('[���ý��]����Ϊ�գ�');
         fm.InHosFeeSum.focus();
         return false;
    }
    return true;
}


//ҽԺģ����ѯ
function showHospital(tCode,tName)
{
	var strUrl="LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�˲д����ѯ
function showDefo(tCode,tName)
{
	var strUrl="LLColQueryDefoMain.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ѯ����ҽԺ
function queryHospitalxx(tCaseNo,tCustNo)
{
	//var strSql =" select hospitalcode from llcase where caseno ='"+tCaseNo+"' and customerno ='"+tCustNo+"'" ;
	     mySql = new SqlClass();   
	        mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql8");
		mySql.addSubPara(tCaseNo); 
		mySql.addSubPara(tCustNo); 
	//prompt("ҽ�Ƶ�֤���������ʼ����ѯ",strSql);
	var tICDName = easyExecSql(mySql.getString());
	if (tICDName!=null && tICDName != "")
	{
		fm.ClinicHosID.value = tICDName;
		showOneCodeName('commendhospital','ClinicHosID','ClinicHosName');//����ҽԺ
		fm.InHosHosID.value = tICDName;
		showOneCodeName('commendhospital','InHosHosID','InHosHosName');//סԺҽԺ
		
		fm.MedFeeCompensateHosID.value = tICDName;
		showOneCodeName('commendhospital','MedFeeCompensateHosID','MedFeeCompensateHosName');//���ò�����ĿҽԺ
	}
}


//2008-10-17 zhangzheng ��������������Ӧ����:����������ѡ��ΪLF:�˲�,LZ:����ʱ����Ҫѡ��˫����������ѡ�������������������Ҫѡ��
function respondDefoGrade()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
        alert('����ѡ�������������!');
        return false;
	}
	//LZ;����,LF:�˲���������
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
	    return showCodeList('lldefograde',[fm.DefoGrade,fm.DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');
	}
	else
	{
		alert("������������:"+fm.DefoType.value+"-"+fm.DefoTypeName.value+"����ѡ�������������,��ֱ��ѡ�������������!");
		return false;
	}
}


//2008-10-17 zhangzheng ��������������Ӧ����:����������ѡ��ΪLF:�˲�,LZ:����ʱ����Ҫѡ��˫����������ѡ�����������������ֱ��ѡ�����ı�����������
function respondDefoCode()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
		
        alert('����ѡ�������������!');
        return false;
	}
	//LZ;����,LF:�˲���������
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
		if(fm.DefoGrade.value == null || trim(fm.DefoGrade.value) ==''){
			
			alert('����ѡ�������������!');
	        return false;
		}
		else
		{
			//ͬʱ���������������ͺͱ������������ѯ
			return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','300');
		}
	}
	//��������
	else
	{
		//ֻ���������������Ͳ�ѯ,������������Ĭ��Ϊ1
		return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,'1','1','300');
	}
}


//2008-10-17 zhangzheng ˫������������Ӧ����
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//������������
    if(cCodeName=="lldefotype"){
    	if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF'){
    		//�������������Ͳ���LZ;����,LF:�˲���������ʱ����������������Ҫ¼�룬��Ҫ���
    		return true;
    	}
    	else{
    		//������������Ҫ�����������������
    		fm.DefoGrade.value='';
    		fm.DefoGradeName.value='';
    	}
        return true;
    }
    
    //����//���ñ��ֽ����ʾ����
    if(cCodeName=="currency"){
    	
    	if(fm.MedCurrency.value!=null && fm.MedCurrency.value!="")
    	{
	    	fm.ClinicMedFeeSum.moneytype=fm.MedCurrency.value;
	    	fm.selfMedFeeSum.moneytype=fm.MedCurrency.value;
    	}
    	if(fm.InHosMedCurrency.value!=null && fm.InHosMedCurrency.value!="")
    	{
    		fm.InHosMedFeeSum.moneytype=fm.InHosMedCurrency.value;
    		fm.selfInHosFeeSum.moneytype=fm.InHosMedCurrency.value;
    	}
    	if(fm.MedFeeCompensateCurrency.value!=null && fm.MedFeeCompensateCurrency.value!="")
	    {
	    	fm.MedFeeCompensateSum.moneytype=fm.MedFeeCompensateCurrency.value;
	    	fm.selfMedFeeCompensateFeeSum.moneytype=fm.MedFeeCompensateCurrency.value;
	    }
	    if(fm.OpCurrency.value!=null && fm.OpCurrency.value!="")
	    {
	    	fm.OpFee.moneytype=fm.OpCurrency.value;
	    	fm.selfOpFeeSum.moneytype=fm.OpCurrency.value;
	    }
	    if(fm.FactorCurrency.value!=null && fm.FactorCurrency.value!="")
	    {
	    	fm.FactorValue.moneytype=fm.FactorCurrency.value;
	    	fm.selfFactorFeeSum.moneytype=fm.FactorCurrency.value;
	    }
	    if(fm.FeeThreeCurrency.value!=null && fm.FeeThreeCurrency.value!="")
	    	fm.FeeThreeValue.moneytype=fm.FeeThreeCurrency.value;
    }	
	
}

// wyc add ��Ե�����ӣ�ɾ��ҳ�治��ʼ�������ӳ�ʼ��
function initClick1(){
	fm.ClinicMainFeeNo.value = "";  //��ʼ���˵���
	fm.ClinicHosID.value = "";      //ҽԺ����  
	fm.ClinicHosName.value = ""; 
	fm.ClinicMedFeeType.value = ""; //�������� 
	fm.ClinicMedFeeTypeName.value = "";
	fm.ClinicStartDate.value = "";  //��ʼʱ��
	fm.ClinicEndDate.value = "";    //����ʱ��
	fm.ClinicDayCount1.value = "";  //����
	fm.MedCurrency.value = "";      //���� 
	fm.MedCurrencyName.value = ""; 
	fm.ClinicMedFeeSum.value = "";  //���ý��
	fm.selfMedFeeSum.value = "";    //�Է�/�Ը����
	fm.ClinicDayCount2.value = "";  //�����¹���������
	fm.theReason1.value = "";       //�ۼ�ԭ�� 
	fm.theReasonName1.value = "";  
	fm.Remark1.value = "";          //��ע
	
}

function initClick2(){
	fm.HosMainFeeNo.value = "";  //��ʼ���˵���
	fm.InHosHosID.value = "";      //ҽԺ����  
	fm.InHosHosName.value = ""; 
	fm.InHosMedFeeType.value = ""; //��������
	fm.InHosMedFeeTypeName.value = "";
	fm.InHosStartDate.value = "";  //��ʼʱ��
	fm.InHosEndDate.value = "";    //����ʱ��
	fm.InHosDayCount1.value = "";  //����
	fm.InHosMedCurrency.value = "";      //���� 
	fm.InHosMedCurrencyName.value = ""; 
	fm.InHosMedFeeSum.value = "";  //���ý��
	fm.selfInHosFeeSum.value = "";    //�Է�/�Ը����
	fm.InHosDayCount2.value = "";  //�����¹���������
	fm.theReason2.value = "";       //�ۼ�ԭ�� 
	fm.theReasonName2.value = "";  
	fm.Remark2.value = "";          //��ע
	
}

function initClick7(){
	fm.CompensateMainFeeNo.value = "";  //��ʼ���˵���
	fm.MedFeeCompensateHosID.value = "";      //ҽԺ����  
	fm.MedFeeCompensateHosName.value = ""; 
	fm.MedFeeCompensateType.value = ""; //��������
	fm.MedFeeCompensateTypeName.value = "";
	fm.MedFeeCompensateStartDate.value = "";  //��ʼʱ��
	fm.MedFeeCompensateEndDate.value = "";    //����ʱ��
	fm.MedFeeCompensateEndDateInHosDayCount1.value = "";  //����
	fm.MedFeeCompensateCurrency.value = "";      //���� 
	fm.MedFeeCompensateCurrencyName.value = ""; 
	fm.MedFeeCompensateSum.value = "";  //���ý��
	fm.selfMedFeeCompensateFeeSum.value = "";    //�Է�/�Ը����
	fm.MedFeeCompensateEndDateInHosDayCount2.value = "";  //�����¹���������
	fm.theReason3.value = "";       //�ۼ�ԭ�� 
	fm.theReasonName3.value = "";  
	fm.Remark3.value = "";          //��ע
}

function initClick5(){
	fm.FactorStateDate.value = "";  //��ʼ����
	fm.FactorEndDate.value = "";  //��������
	fm.theReason4.value = "";  //�ۼ�ԭ��
	fm.theReasonName4.value = ""; 
	fm.Remark4.value = "";          //��ע
}

function initClick6(){
	fm.FeeThreeType.value = "";   //��������
	fm.FeeThreeTypeName.value = ""; 
	fm.AdjRemark.value = "";          //��ע
}

// end 



