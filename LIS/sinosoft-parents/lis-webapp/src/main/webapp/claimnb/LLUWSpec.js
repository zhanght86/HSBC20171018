//�������ƣ�LLUWSpec.js
//�����ܣ�������Լ�б�
//�������ڣ�2005-11-04 
//������  �������
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var turnPage1 ;
var turnPage2 ;
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var mOperate ;

//�ύ�����水ť��Ӧ����
function submitForm() {
    var i = 0;
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
    fm.hideOperate.value = mOperate;
    fm.action = "./LLUWSpecSave.jsp";
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
        	 LLUWSpecContGrid.delRadioTrueLine();	
             fm.SpecReason.value = "";
             fm.Remark.value     = "";
        }             
        getPolGridCho();
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
    �޸�״̬����ʼ
    �޸�ԭ�򣺵õ�Ͷ������Ϣ�б�
    �� �� ��: �����
    �޸����ڣ�2005.11.28
   =========================================================================
**/
function QueryPolSpecGrid(tContNo){
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n; 
       //��ȡԭ������Ϣ
   /* strSQL = "select LCPol.ContNo,LCPol.PrtNo,LCPol.PolNo,LCPol.RiskCode,LCPol.RiskVersion,"
           + " LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
		   + " ContNo ='"+tContNo+"' and LCPol.PolNo = LCPol.MainPolNo" 
		   + " order by polno ";	*/		
	
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWSpecInputSql");
	mySql.setSqlId("LLUWSpecSql1");
	mySql.addSubPara(tContNo); 
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
      //��ѯ�ɹ������ַ��������ض�ά����
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
       //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
       turnPage.pageDisplayGrid = LLUWSpecGrid;    
          
      //����SQL���
      turnPage.strQuerySql     = strSQL; 
  
      //���ò�ѯ��ʼλ��
       turnPage.pageIndex       = 0;  
  
      //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
       var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
       //����MULTILINE������ʾ��ѯ���
       displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
 
  return true;	
}
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺲�ѯ�����µ���Լ��Լ��Ϣ
    �� �� ��: �����
    �޸����ڣ�2005.11.28
   =========================================================================
**/
function QueryPolSpecCont()
{
	var strSQL  = "";
    var arr     = new Array;
    var tPolNo  = LLUWSpecGrid.getRowColData(0,3);
    var tContNo = LLUWSpecGrid.getRowColData(0,1);
    var tProposalNo = fm.ProposalNo.value;
    
    //alert(document.all('BatNo').value);
    
    
   /* strSQL      = " select contno,batno,SpecContent,'A' from lluwspecmaster where 1=1"
	            //+ " polno = '"+tPolNo+"' "
	            + " and contno = '"+tContNo+"'"
	            + " and BatNo ='"+document.all('BatNo').value+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWSpecInputSql");
	mySql.setSqlId("LLUWSpecSql2");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(document.all('BatNo').value); 
	arr         =  easyExecSql( mySql.getString() );
	if (arr)
    {
         displayMultiline(arr,LLUWSpecContGrid);
    }
	
    else
	{
		 //��ȡԭ������Ϣ
         /*strSQL = "select '', '',speccontent ,'B' from lcspec where "				 	
		        + "polno = '"+tPolNo+"'";	*/
		 mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql3");
		mySql.addSubPara(tPolNo); 
		 arr    =  easyExecSql( mySql.getString() );
	     if (arr)
         {
              displayMultiline(arr,LLUWSpecContGrid);
         }
         
    }
}
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺲�ѯ�����µ���Լ��Լ��Ϣ
    �� �� ��: �����
    �޸����ڣ�2005.11.28
   =========================================================================
**/
function getPolGridCho()
{
	QueryPolSpecCont();
    var cPolNo  = LLUWSpecGrid.getRowColData(0,3);
    var tcontno = LLUWSpecGrid.getRowColData(0,1);
    var tProposalNo = fm.ProposalNo.value ;
    if(cPolNo != null && cPolNo != "" )
    {
        fm.PolNo.value = cPolNo;
        QuerySpecReason(tProposalNo); 
        QuerySpec(tcontno);
    }	
}


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺲�ѯ�Ѿ�¼����Լ����
    �� �� ��: �����
    �޸����ڣ�2005.11.28
   =========================================================================
**/
function QuerySpec(tcontno)
{
	
	// ��дSQL���
	var strSQL = "";
	/*strSQL     = " select speccontent from LLUWSpecMaster where contno='"+tcontno+"' "
	           //+ " and SerialNo in (select max(SerialNo) from LLUWSpecMaster where contno = '"+tcontno+"')";
    		   + " and BatNo ='"+document.all('BatNo').value+"' "
    		   + "union all "
    		   + " select speccontent from LLUWSpecSub where contno='"+tcontno+"' "
    		   + " and BatNo ='"+document.all('BatNo').value+"' "*/
	 mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql4");
		mySql.addSubPara(tcontno); 
		mySql.addSubPara(document.all('BatNo').value); 
		mySql.addSubPara(tcontno); 
		mySql.addSubPara(document.all('BatNo').value); 
	var i=0;
	var arrResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
	if(arrResult != ""&& arrResult!= null)
	{
	    var test= new Array();
	     test = decodeEasyQueryResult(arrResult);
	
	     for(i=0;i<test.length;i++)
	     {
	      fm.Remark.value= fm.Remark.value+(i+1)+"."+test[i][0];
	     }
	   // fm.Remark.value= arrResult[0][0];
        return true;
    }
        
    else
    {
    	return false;
    } 

}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺲�ѯ�Ѿ�¼����Լԭ��
    �� �� ��: �����
    �޸����ڣ�2005.11.28
   =========================================================================
**/
function QuerySpecReason(tProposalNo)
{
	var strSQL = "";
//	strSQL = "select specreason from LCUWMaster where 1=1 "
//			 + " and proposalno = '"+tProposalNo+"'";
	/*strSQL = "select changepolreason from llcuwmaster where 1=1 "
		    + " and CaseNo='"+fm.ClmNo.value+"'"
		    + " and contno = '"+fm.ContNo.value+"'"
		    + " and batno = '"+fm.BatNo.value+"'";*/
	mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql5");
		mySql.addSubPara(fm.ClmNo.value); 
		mySql.addSubPara(fm.ContNo.value); 
		mySql.addSubPara(fm.BatNo.value); 
	var tResult = easyExecSql(mySql.getString());
	//prompt("",strSQL);
	if(tResult == null )
	{
		 return "";
	}
	fm.SpecReason.value = tResult[0][0];
    return true;	
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺵õ�һ����Լ��Ϣ
    �� �� ��: �����
    �޸����ڣ�2005.11.28
   =========================================================================
**/
function getSpecGridCho()
{
	var tContent    = fm.Remark.value;
	var tSelNo      = LLUWSpecContGrid.getSelNo()-1;
	var tRemark     = LLUWSpecContGrid.getRowColData(tSelNo,3);	
	var tFlag       = LLUWSpecContGrid.getRowColData(tSelNo,4)
//	if(tFlag == "B")
//	{
	    fm.Remark.value = tRemark ;
	    //+ tContent;
//    }
//    else
//    {
//    	//var tSelNo  = LLUWSpecGrid.getSelNo()-1;
//        var tcontno = LLUWSpecGrid.getRowColData(0,1);
//        var tProposalNo = fm.ProposalNo.value ;
//        if(tProposalNo != null && tProposalNo != "" )
//        {
//            QuerySpecReason(tProposalNo); 
//            QuerySpecContent();
//        }	
//    }
}
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺲�ѯ��Լ����
    �� �� ��: �����
    �޸����ڣ�2005.11.28
   =========================================================================
**/
function QuerySpecContent()
{
	var tSelNo      = LLUWSpecContGrid.getSelNo()-1;
    var tProposalNo = LLUWSpecContGrid.getRowColData(tSelNo,1);
    var tSerialNo   = LLUWSpecContGrid.getRowColData(tSelNo,2);
    /*var tSql        = " select speccontent from lluwspecmaster where 1=1 and "
                    + " contno = '"+tProposalNo+"'" +
                    " and batno = '"+document.all('BatNo').value+"'";*/
  mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql6");
		mySql.addSubPara(tProposalNo); 
		mySql.addSubPara(document.all('BatNo').value); 
    var tResult     = easyExecSql(mySql.getString());
    if( tResult !="" && tResult != null)
    {
    	//fm.Remark.value = tResult[0][0];
    }
    else
    {
    	//fm.Remark.value = "";
    }
}
/**=========================================================================
    �޸�״̬���ύɾ����ť��Ӧ����
    �޸�ԭ��
    �� �� �ˣ������
    �޸����ڣ�2005.11.27
   =========================================================================
**/

function deleteClick()
{   
    var tSelNo  = LLUWSpecContGrid.getSelNo()-1;
    if( tSelNo < 0 )
    {
    	alert("��ѡ��һ����¼��");
    	return ;
    }
    var tSerialNo = LLUWSpecContGrid.getRowColData(tSelNo,2);
    fm.SerialNo.value = tSerialNo;
    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='DELETE';
        submitForm();
    }
    
}

/**=========================================================================
    �޸�״̬���ύ���水ť��Ӧ����
    �޸�ԭ��
    �� �� �ˣ������
    �޸����ڣ�2005.11.27
   =========================================================================
**/
function saveClick()
{
	var tSelNo  = LLUWSpecContGrid.getSelNo()-1;
    if( tSelNo >= 0 )
    {
    	  var tSerialNo     = LLUWSpecContGrid.getRowColData(tSelNo,2);
          if(tSerialNo!=""&&tSerialNo!=null)
          {
               fm.SerialNo.value = tSerialNo;
          }
          else
          {
          	   fm.SerialNo.value = "";
          }

    }
    
    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='SAVE';
        submitForm();
    }
}
/**=========================================================================
    �޸�״̬���ύǰ������У�����
    �޸�ԭ��
    �� �� ��: �����
    �޸����ڣ�2005.11.27
   =========================================================================
**/
function beforeSubmit()
{
    var i           = 0;
    var tRemark     = fm.Remark.value;
    var tSpecReason = fm.SpecReason.value;   
    if ( tRemark == null || tRemark ==''){
         alert('��¼��[�ر�Լ��]��');
        return 1;
    }
    return 0;
}
  