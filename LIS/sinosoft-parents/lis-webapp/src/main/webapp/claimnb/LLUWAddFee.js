//�������ƣ�PEdorUWManuAdd.js
//�����ܣ���ȫ�˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var turnPageF = new turnPageClass();
var mySql = new SqlClass();
var turnPage1 ;
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var spanObj;
var mOperate;
/**=========================================================================
    �޸�״̬���ύǰ������У�����
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/
function beforeSubmit()
{
    var i = 0;
    i = SpecGrid.mulLineCount ; 
    if(i==0)
    {
        alert("δ¼���µĺ˱��ӷ���Ϣ!");
        return 1;
    }
            
    var cPolNo = fm.PolNo2.value ;
    if(cPolNo == null || cPolNo == "")
    {
        alert("δѡ��ӷѵ�Ͷ����!");
        return 1;
    }
       
   
    var tSelNo = SpecGrid.getSelNo();
    if(tSelNo < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return 1;
    }
         
    tSelNo = tSelNo - 1;           
    var tDutuCode     = SpecGrid.getRowColData(tSelNo,1);
    var tPayPlanType  = SpecGrid.getRowColData(tSelNo,2);
    var tPayStartDate = SpecGrid.getRowColData(tSelNo,3);           
    var tPayEndDate   = SpecGrid.getRowColData(tSelNo,4);

    var tAddFeeDirect = SpecGrid.getRowColData(tSelNo,7);
    var tPrem         = SpecGrid.getRowColData(tSelNo,8);           
    var tAddReason    = fm.AddReason.value;    
    if ( tDutuCode == null || tDutuCode ==''){
         alert('[�ӷ�����]����Ϊ�գ�');
        return 1;
    }else if ( tPayPlanType == null || tPayPlanType ==''){
         alert('[�ӷ�ԭ��]����Ϊ�գ�');
        return 1;
    }else if ( tPayStartDate == null || tPayStartDate ==''){
         alert('[��ʼ����]����Ϊ�գ�');
        return 1;
    }else if ( tPayEndDate == null || tPayEndDate ==''){
         alert('[��ֹ����]����Ϊ�գ�');
        return 1;
    }else if ( tAddFeeDirect == null || tAddFeeDirect ==''){
         alert('[�ӷѶ���]����Ϊ�գ�');
        return 1;
    }else if ( tPrem == null || tPrem ==''){
         alert('[�ӷѽ��]����Ϊ�գ�');
        return 1;
    }
    return 0;
}
    
/**=========================================================================
    �޸�״̬���ύ���水ť��Ӧ����
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/
function saveClick()
{

    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='SAVE';
        submitForm();
     }
}

/**=========================================================================
    �޸�״̬���ύɾ����ť��Ӧ����
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/

function deleteClick()
{   

    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='DELETE';
        submitForm();
     }
}

/**=========================================================================
    �޸�״̬���ύ��̨����
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/
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
    fm.action= "./LLUWAddFeeSave.jsp";
    document.getElementById("fm").submit(); //�ύ       
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        if( mOperate == "DELETE")
        {
             SpecGrid.delRadioTrueLine();	
             fm.AddReason.value = "";
        }             
        if( mOperate!= "DOUBLE")
        {
             getPolGridCho();
        }
    }    
    
    mOperate = '';
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
   if(cDebug=="1")
   {
		parent.fraMain.rows = "0,0,50,82,*";
   }
   else 
   {
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



/**=========================================================================
    �޸�״̬���ӷ����ֲ�ѯ��ʼ������
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/
function QueryPolAddGrid(tContNo,tInsuredNo)
{	
    var tSql;
    var arr = new Array;
    
        
    //��ȡԭ������Ϣ
   /* tSql = "select LCPol.PolNo,LCPol.ContNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName,LCPol.standprem from LCPol where 1=1"				 			
			 + "  and LCPol.ContNo ='"+tContNo+"'"
             + "  and LCPol.AppFlag ='1' and LCPol.PolNo = LCPol.MainPolNo"             
			 + "  order by LCPol.polno";	*/		
     mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql1");
	mySql.addSubPara(tContNo);       
    //prompt("�ӷ����ֲ�ѯ��ʼ������",tSql);
    
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolAddGrid);
    }
    else
    {
        initPolAddGrid();
    }
                 
}
	
/**=========================================================================
    �޸�״̬�����������Ϣʱ���¼�
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/    
function getPolGridCho()
{
    //var tSelNo        = PolAddGrid.getSelNo()-1;
    //tRow              = tSelNo;
    var cPolNo2       = PolAddGrid.getRowColData(0,1);   //�õ�������
    var cPolNo        = PolAddGrid.getRowColData(0,1);   //���ֺ�
    var tRiskCode     = PolAddGrid.getRowColData(0,4);   //�õ����ֱ���
    fm.PolNo.value    = cPolNo;
    fm.PolNo2.value   = cPolNo2 ;
    fm.RiskCode.value = tRiskCode;
    var tClmNo        = fm.ClmNo.value;
    if(cPolNo != null && cPolNo != "")
    {
         str = initlist(cPolNo);
         ///if( tSelNo < 0)
         //    alert("��ָ����ţ�");
         //else
             initSpecGrid(str);
//         LLUWPremMaster(cPolNo,cPolNo2);
         QueryLCPrem(cPolNo);
         QueryAddReason(cPolNo2);
         QueryLLUWPremSub();  
    }
       
}

/**=========================================================================
    �޸�״̬����ѯ����ֵ
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
    
    �޸�״̬���õ��ӷѵ���ֹ����
    �޸�ԭ����ǰ��ȡֵ����ȷ
    �� �� �ˣ������
    �޸����ڣ�2005.11.24
   =========================================================================
**/
function initlist(tPolNo)
{   
	
	k++;
    var strSQL3 = "";
   /* strSQL3     = "select dutycode from lcduty where "+k+" = "+k
                + " and polno = '"+tPolNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql2");
	mySql.addSubPara(tPolNo);     

    str4        = easyQueryVer3(mySql.getString(), 1, 0, 1);
    var strSQL  = "";
    var tClmNo  = fm.ClmNo.value;
    //��������
    //strSQL      = "select accdate from llcase where caseno = '"+tClmNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql3");
	mySql.addSubPara(tClmNo);
    var str1    = easyExecSql(mySql.getString());
    var tContNo = fm.ContNo.value;
    //��һ�ν�������
    //var strSQL1  = " select paystartdate from lcprem where contno = '"+tContNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql4");
	mySql.addSubPara(tContNo);
    var str0     = easyExecSql(mySql.getString());
    //�õ��������ں͵�һ�ν������ڵļ���·�
    var str3     = dateDiff(str0[0][0],str1[0][0],'M');
    var arr      = str0[0][0].split('-');
    
    //�õ��·�
    var month    = arr[1].split('0');
    if( month[0] != null&& month[0] != "" )
       var intMonth = parseInt(month[0])+ parseInt(str3)- 1  ;
    if( month[1] != null&& month[1] != "" )
       var intMonth = parseInt(month[1])+ parseInt(str3)- 1  ;
    var payStartDate ="" ;
   
    //��üӷѺ���ʼ����
    if( intMonth <= 12 )
    {
    	 arr[1]       = parseInt(intMonth) + 1 ;
    	 if(arr[1] < 10)
            arr[1] = "0" + arr[1];
    	 arr[2]       = parseInt(arr[2]) ;
         payStartDate = arr[0]+'-'+arr[1]+'-'+arr[2];
    }
    else
    {
        arr[2]      = parseInt(arr[2]) ;
        var intYear = parseInt(intMonth)/12 ;
        arr[0]      = parseInt(arr[0]) + parseInt(intYear) ;
        arr[1]      = parseInt(intMonth) - 12*parseInt(intYear) + 1 ;
        if(arr[1] < 10)
            arr[1] = "0" + arr[1];
        payStartDate = arr[0]+'-'+arr[1]+'-'+arr[2];
    }
    
    //��üӷѺ���ֹ����
    //var strSQL2 = " select payenddate from lcprem where polno = '"+tPolNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql5");
	mySql.addSubPara(tPolNo);
    var endDate = easyExecSql(mySql.getString());
    //�õ��ӷ����ͣ���ֹ���ڵ��ַ���
    str = str4+'|'+payStartDate+'|'+endDate[0][0];
    return str;
   
}

/**=========================================================================
    �޸�״̬����ѯLLUWPremMaster���мӷѵ���Ϣ
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/ 
function LLUWPremMaster(tPolNo,tContNo)
{
   
    // ��дSQL���
    var strSQL = "";
    var i, j, m, n; 
    
    //alert(document.all('BatNo').value);
    
    //��ȡ���μӷ���Ϣ
    /*strSQL   = "select dutycode,payplantype,paystartdate,payenddate,suppriskscore,SecInsuAddPoint,AddFeeDirect,prem from LLUWPremMaster where 1=1 "
             + " and contno ='"+tContNo+"'"
             + " and BatNo ='"+document.all('BatNo').value+"'"
             + " and payplancode like '000000%%'"
             + " and state = '1'"; */
   mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql6");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(document.all('BatNo').value);          
//    prompt("��ȡ���μӷ���Ϣ",strSQL);
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 

    //�ж��Ƿ��ѯ�ɹ�
    if (turnPage.strQueryResult) 
    {  
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
        turnPage.pageDisplayGrid = SpecGrid;    
        //����SQL���
        turnPage.strQuerySql     = strSQL; 
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex       = 0;  
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    //��ȡ�������������
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    return true;    
}


/**=========================================================================
    �޸�״̬����ѯ�Ѿ�¼��ӷ�ԭ��
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/ 
function QueryAddReason(tPolNo)
{
    var tSql;
    var arr = new Array;	
    var tClmNo = fm.ClmNo.value;
        
    //��ѯ�ӷ�ԭ��
   /* tSql = "select changepolreason from llcuwmaster where 1=1 "
        + " and CaseNo='"+tClmNo+"'"
        + " and contno = '"+tPolNo+"'"
        ; */
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql7");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tPolNo);                           
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.AddReason.value  = arr[0][0];
    }    
}

/**=========================================================================
    �޸�״̬����˫�����ӷѽ�¼���ʱ�����ú���
    �޸�ԭ��
    �� �� �ˣ����� 
    �޸����ڣ�2005.10.28
    
    �޸�״̬����˫�����ӷѽ�¼���ʱ�����ú���
    �޸�ԭ��ʵ��������ӷѽ�����
    �� �� �ˣ������
    �޸����ڣ�2005.11.08
   =========================================================================
**/
function CalHealthAddFee(span)
{
    spanObj = span;
    var tSelNo11 = SpecGrid.getSelNo();
    mOperate = "DOUBLE";
    if(tSelNo11 < 1)
     {
        alert("��ѡ�ж�Ӧ��ţ�");
        return 1;
     }
   // var tSelNo = PolAddGrid.getSelNo()-1; 
    var tRiskCode = PolAddGrid.getRowColData(0,4);
    var tPolNo = PolAddGrid.getRowColData(0,1);
    var tMainPolNo = PolAddGrid.getRowColData(0,2);
    //��ѯ�������µı�׼����
    /*var tSql = " select sum(standprem) from lcprem where 1 = 1 "
             + " and polno = '"+tPolNo+"' and dutycode='"+document.all( spanObj ).all( 'SpecGrid1' ).value+"' "
             + " and payplancode in (select payplancode from lmdutypayrela where dutycode='"+document.all( spanObj ).all( 'SpecGrid1' ).value+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql8");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);   
	mySql.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
    if (!turnPage.strQueryResult){
       alert("��ѯ�������µı�׼���ѽ��ʧ��")
       return ;
    }
    else{
    	 turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
       var tPrem = turnPage.arrDataCacheSet[0][0];  
    }
  
    var tSuppRiskScore = document.all( spanObj ).all( 'SpecGrid5' ).value;
    //����������Ǹ����գ��жϸø�������û�мӷ��㷨��
   /* var tSql = " select * from LMDutyPayAddFee where 1=1 "
             + " and riskcode = '"+tRiskCode+"'"
             + " and dutycode = '"+document.all( spanObj ).all( 'SpecGrid1' ).value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql9");
	mySql.addSubPara(tRiskCode);  
	mySql.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);       
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
   
    if((tPolNo != tMainPolNo) && !(turnPage.strQueryResult)){     
    
       /*var tSql = " select addpoint from lduwuser where 1 = 1 "
             + " and usercode='"+tOperator+"' and uwtype='1'";*/
       mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql10");
		mySql.addSubPara(tOperator);  
       turnPage.strQueryResult = easyQueryVer3(mySql.getString(), 1, 1, 1);    
      //�ж��Ƿ��ѯ�ɹ�
 
     if (!turnPage.strQueryResult)
     {
       alert("��ѯ�����ռӷ�����Ȩ��������")
       return ;
     }  
     turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
     var RiskScore = turnPage.arrDataCacheSet[0][0];          
   
     if(parseFloat(tSuppRiskScore) > parseFloat(RiskScore) )
     {
        alert("�ӷ�������󣬳����ú˱��ǵĺ˱�Ȩ��")
        return;
     }
     else{
        var tAddPrem = tSuppRiskScore/100*tPrem;
        document.all( spanObj ).all( 'SpecGrid8' ).value = tAddPrem;      
     }
    
   }else{
    document.all('DutyCode').value      = document.all( spanObj ).all( 'SpecGrid1' ).value;
    document.all('AddFeeType').value    = document.all( spanObj ).all( 'SpecGrid2' ).value;
    document.all('SuppRiskScore').value = document.all( spanObj ).all( 'SpecGrid5' ).value;
    document.all('SecondScore').value   = document.all( spanObj ).all( 'SpecGrid6' ).value;
    document.all('AddFeeObject').value  = document.all( spanObj ).all( 'SpecGrid7' ).value;
     
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
	fm.action= "./LLUWAddFeeCalSave.jsp";
    document.getElementById("fm").submit(); //�ύ
   }
}

/**=========================================================================
    �޸�״̬����ʼ���ӷѶ���[����]
    �޸�ԭ��
    �� �� �ˣ�����
    �޸����ڣ�2005.10.28
   =========================================================================
**/

//��ʼ���ӷѶ���
function initAddObj(span)
{
	spanObj = span;
	var tRiskCode = PolAddGrid.getRowColData(0,4);
	var tDutyCode = document.all( spanObj ).all( 'SpecGrid1' ).value;
	var tAddFeeType = document.all(spanObj).all('SpecGrid2').value;
	
  if(tAddFeeType = '01')
  {
	/*var srtSql = "select AddFeeObject from LMDutyPayAddFee where 1=1 "
	           + " and riskcode = '"+tRiskCode+"'"
	           + " and DutyCode = '"+tDutyCode+"'"
	           + " and AddFeeType = '01'";*/
	mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql11");
		mySql.addSubPara(tRiskCode);
		mySql.addSubPara(tDutyCode);  
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	document.all( spanObj ).all( 'SpecGrid8' ).value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  document.all( spanObj ).all( 'SpecGrid1' ).value = turnPage.arrDataCacheSet[0][0];  

}
else
	{
		document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
	}
    return true;	
	
}
	


/*********************************************************************
 *  Click�¼�����˫�����ӷѽ�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showUWSpec( span)
{
	spanObj = span;
	
	// ��дSQL���
	var strSQL = "";
	var tRiskCode="";
	var tInsuredSex="";
	var tInsuredAppAge="";
	var tSuppRiskCore="";
	var tAddFeeKind="";
	var tPayEndYear="";
    //У��¼����Ϣ��������
	if(document.all( spanObj ).all( 'SpecGrid1' ).value == ""){
		alert("��¼��ӷ�������Ϣ��");
		return;
	}
	
	if(document.all( spanObj ).all( 'SpecGrid2' ).value == "")
	{
		alert("��¼��ӷ�ԭ����Ϣ��");
		return;
	}
	else
	tAddFeeKind=document.all( spanObj ).all( 'SpecGrid2' ).value;
	
 	if(document.all( spanObj ).all( 'SpecGrid5' ).value=="")
 	{
		alert("��¼��ӷ�������Ϣ��");
		return;
	}
	else
	tSuppRiskCore=document.all( spanObj ).all( 'SpecGrid5' ).value;
	
	
   //�˴���ְҵ�ӷѴ���û��ȷ����
   if(tAddFeeKind=="1"||tAddFeeKind=="3")
   {
 
	   	//׼���ӷ�����Ҫ��	
	  
		//strSQL = "select AddFeeAMNT("+tAddFeeKind+",riskcode,polno,"+tSuppRiskCore+") from LCpol where polno='"+PolAddGrid.getRowColData(0,1)+"'";	
		
			mySql = new SqlClass();
			mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
			mySql.setSqlId("LLUWAddFeeSql12");
			mySql.addSubPara(tAddFeeKind);
			mySql.addSubPara(tSuppRiskCore);
			mySql.addSubPara(PolAddGrid.getRowColData(0,1));
					
		turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
	
	    //�ж��Ƿ��ѯ�ɹ�
	    if (!turnPage.strQueryResult) 
	    {
		  	alert("�����ӷ��������ʧ�ܣ�");
		    return "";
	    }
	   
	    //��ѯ�ɹ������ַ��������ض�ά����
	    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	    document.all( spanObj ).all( 'SpecGrid6' ).value = turnPage.arrDataCacheSet[0][0];	
	    return true;	
    }
    else
  	{
	    alert("δ����ְҵ�ӷ����㣡");
	    return "";
    }
   	
}    

/**=========================================================================
    �޸�״̬����ѯLCPrem���õ������Ľɷ���Ϣ�б�
    �޸�ԭ��
    �� �� �ˣ������
    �޸����ڣ�2005.11.03
   =========================================================================
**/ 
function QueryLCPrem(tPolNo)
{
	turnPage1 = new turnPageClass();

	var strSQL = "";
	/*strSQL = "select dutycode,(case payplantype when '01' then '���ڽ����ӷ�'  "
	       + " when '02' then '����ְҵ�ӷ�'  when '03' then '��Ч�����ӷ�'  when '04' "
	       + " then '��Чְҵ�ӷ�' end),paystartdate,payenddate,suppriskscore,"
	       + " SecInsuAddPoint,(case addfeedirect when '01' then 'Ͷ����' when '02'"
	       + " then '������' when '03' then '�౻������' when '04' then '�ڶ���������' end),"
	       + " prem  from lcprem where polno = '"+tPolNo+"' and payplancode like '000000%%' "
	       + " order by dutycode";*/
		mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql13");
		mySql.addSubPara(tPolNo);
	turnPage1.queryModal(mySql.getString(),LCPremGrid);
}

function RadioSelected()
{
	 var tSelNo = SpecGrid.getSelNo();
     if(tSelNo < 1)
     {
         alert("��ѡ��һ�м�¼��");
         return 1;
     }
}

//�ӷѹ켣��Ϣ��ѯ 2006-02-09 Add by zhaorx
function QueryLLUWPremSub()
{
	//var turnPageF = new turnPageClass();
	var tClmNoF = fm.ClmNo.value;
	var tPolNoF = PolAddGrid.getRowColData(0,1);
	
	/*var tSQLF = " select batno,dutycode,(case payplantype when '01' then '���ڽ����ӷ�' when '02' then '����ְҵ�ӷ�' when '03' then '��Ч�����ӷ�' when '04' then '��Чְҵ�ӷ�' end),"
              + " paystartdate,payenddate,suppriskscore,SecInsuAddPoint, "
              + " (case addfeedirect when '01' then 'Ͷ����' when '02' then '������' when '03' then '�౻������' when '04' then '�ڶ���������' end),"
              + " prem from lluwpremsub where 1=1 and polno = '"+tPolNoF+"' and clmno = '"+tClmNoF+"' order by batno ";*/
		mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql14");
		mySql.addSubPara(tPolNoF);
		mySql.addSubPara(tClmNoF);
	turnPageF.queryModal(mySql.getString(),LLUWPremSubGrid);
}