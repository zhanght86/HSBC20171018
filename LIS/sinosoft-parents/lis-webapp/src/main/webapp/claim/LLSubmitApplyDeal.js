var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var mySql = new SqlClass();
function easyQueryClick() 
{  	
   /* var strSQL = "";
    strSQL = "select ClmNO,SubNO,SubCount,CustomerNo,Customername,VIPFlag,InitPhase,SubType,SubRCode,SubDesc,SubPer,SubDate,SubDept,SubState,DispDept,DispPer,DispIdea from llsubmitapply"// where SubCount=1 and disptype is null";
           +" where 1=1 and ClmNO='"+document.all('ClmNo').value+"'"
           +" and SubNO='"+document.all('SubNO').value+"'"
           +" and SubCount='"+document.all('SubCount').value+"'"; */
    
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLSubmitApplyDealInputSql");
	mySql.setSqlId("LLSubmitApplyDealSql1");
	mySql.addSubPara(document.all('ClmNo').value ); 
	mySql.addSubPara(document.all('SubNO').value ); 
	mySql.addSubPara(document.all('SubCount').value ); 
    var arr = easyExecSql(mySql.getString() );
    if (arr == null)
    {
	      alert("����������!");
	      return;
    }
    else
    {
    	fm.ClmNo.value=arr[0][0]; //�ⰸ��
        fm.SubNO.value=arr[0][1];  //�ʱ����
        fm.SubCount.value=arr[0][2]; //�ʱ�����
        fm.CustomerNo.value=arr[0][3]; //�����˿ͻ���
        fm.CustomerName.value=arr[0][4];//�����˿ͻ�����
        fm.VIPFlag.value=arr[0][5];   //VIP�ͻ�
        fm.InitPhase.value=arr[0][6]; //����׶�
        fm.SubType.value=arr[0][7];    //�ʱ�����
        fm.SubRCode.value=arr[0][8];  //�ʱ�ԭ��
        fm.SubDesc.value=arr[0][9];  //�ʱ�����
        fm.SubPer.value=arr[0][10];   //�ʱ���
        fm.SubDate.value=arr[0][11];  //�ʱ�����
        fm.SubDept.value=arr[0][12];  //�ʱ�����
        fm.SubState.value=arr[0][13]; //�ʱ�״̬
        fm.DispDept.value=arr[0][14]; //�нӻ�������
        fm.DispPer.value=arr[0][15];  //�н���Ա���    	
        fm.HeadIdea.value=arr[0][16];  //�ܹ�˾�������  -----2005-08-14���
        showOneCodeName('llinitphase','InitPhase','InitPhaseName');    
        showOneCodeName('llsubtype','SubType','SubTypeName');    
        showOneCodeName('llsubstate','SubState','SubStateName');    
        showOneCodeName('station','SubDept','SubDeptName');    
    }
}     
function afterCodeSelect( cName, Filed)   //CodeSelect�������
{
    if(cName=='lldisptype')           //
    {
    	var disType = fm.DispType.value;
        if(disType=="0")       	//�������
        {
 	    	fm.fmtransact.value = "UPDATE||Investgate";   //�޸ļ�¼
      	   document.all('DispIdea').value="";  
      	   DivDispType.style.display="none";  
      	   
      	   document.all('ReportheadSubDesc').value="";  
      	   DivReportheadSubDesc.style.display="none";    
 	    	//�����������ҳ��
 	    	var strUrl="LLInqApplyMain.jsp?claimNo="+fm.ClmNo.value+"&custNo="+fm.CustomerNo.value+"&custName="+fm.CustomerName.value+"&custVip="+fm.VIPFlag.value+"&initPhase=03";
            window.open(strUrl,"�������",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
       if(disType=="1")  //�ʱ��ܹ�˾��ť�����һ����¼��ԭѡ�м�¼�ĳʱ�������"1"��
       {       		
	       fm.fmtransact.value = "INSERT||Reporthead";   //��Ӽ�¼   
      	   
      	   document.all('DispIdea').value="";  
      	   DivDispType.style.display="none";  
      	   
      	   document.all('ReportheadSubDesc').value="";  
      	   DivReportheadSubDesc.style.display="";         	
       }
       if(disType=="2")//�ظ����
       {    
       	    DivReportheadSubDesc.style.display="none";  
       		document.all('ReportheadSubDesc').value="";  
       	
       		fm.fmtransact.value = "UPDATE||Replyport";   //�޸ļ�¼
       		document.all('DispIdea').value="";  
       		DivDispType.style.display="";

       }  
    }
}
/*****************************************************************************
 * ��ʼ��"�ʱ�ȷ��"�ύ��ť
 * �޸�ԭ�򣺼�һ�����ѹ��ܣ����[�ʱ�ȷ��]ʱ��Ҫ������ʾ���Ƿ��ȷ�����飿�� ��,��򿪷���������,
             ��,��򿪷���ʱ�����
 * �޸��ˣ������
 * �޸�ʱ�䣺2005-11-19
 ****************************************************************************/
function Replyport()
{
	
	if(confirm("�Ƿ��ȷ������?"))
  {
	  //  var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
	   mySql = new SqlClass();
	mySql.setResourceName("claim.LLSubmitApplyDealInputSql");
	mySql.setSqlId("LLSubmitApplyDealSql2");
	mySql.addSubPara(fm.RptNo.value );  
	    var JustStateCount=easyExecSql(mySql.getString());
	    if(parseInt(JustStateCount)>0)
	    {      				
	    		alert("�ð����Ѿ�������飬�벻Ҫ�ظ�����!");
	    		return;
	    }    
	    
	     var strUrl="LLInqApplyMain.jsp?claimNo="+fm.ClmNo.value+"&custNo="+fm.CustomerNo.value+"&custName="+fm.CustomerName.value+"&custVip="+fm.VIPFlag.value+"&initPhase=01";
       window.open(strUrl,"�������",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }
  else
  {
  	  	
	    var disType = fm.DispType.value;
	    if(disType=="" || disType==null)
	    {
		      alert("ѡ��ʴ�������");
		      return;
	    }

      if(disType=="0")       	//�������
      {
    	    fm.fmtransact.value = "UPDATE||Investgate";   //�޸ļ�¼
      }
      if(disType=="1")  //�ʱ��ܹ�˾��ť�����һ����¼��ԭѡ�м�¼�ĳʱ�������"1"��
      {       		
          fm.fmtransact.value = "INSERT||Reporthead";   //��Ӽ�¼   
   	      if(fm.ReportheadSubDesc.value=="" ||fm.ReportheadSubDesc.value==null)
   	      {
   	    	    alert("����д�ʱ�ԭ��!");
   	    	    return;
   	      }       
   	     ��
      }
      if(disType=="2")//�ظ����
      {    
   	      if(fm.DispIdea.value=="" ||fm.DispIdea.value==null)
   	      {
   	    	    alert("����д�ظ����!");
   	    	    return;
   	      }
   		    fm.fmtransact.value = "UPDATE||Replyport";   //�޸ļ�¼
      }     	 
	    submitForm();   //�ύҳ������
  }
}


function TurnBack()
{
    var strUrl= "LLSubmitApplyDealMissInput.jsp?";
    location.href=strUrl;
}
  //�ύ����
function submitForm()
{
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
   //  showSubmitFrame(mDebug);
    fm.action = './LLSubmitApplyDealSave.jsp';
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";   
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
    	TurnBack();//[�ɹ��򷵻ض���ҳ��]
    }
    tSaveFlag ="0";
}
