<%
/***************************************************************
 * <p>ProName��LLClaimSurveyAppinput.jsp</p>
 * <p>Title������¼��</p>
 * <p>Description������¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<script language="JavaScript">
/**
 * ��ʼ������

 */
function initForm() {
	
	try {
		initParam();
		initInpBox();
		initButton();
		
		initSurveyAttachmentGrid();
		initLLSurveyFeeGrid();
		initLLSurveyCourseGrid();
		QueryInqAppInfo();
		InitQuerySurveyCourse();
		querySurveyAttachment();
		initInqFeeInfo();
		initSurveyFeeQuery();
		queryUwConclusionInfo();
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		 mCurrentDate = nullToEmpty("<%= tCurrentDate %>"); 
		 
		 fm.ClmNo.value=tRgtNo;
		 fm.InqNo.value=tInqNo;
		 fm2.ClmNo.value=tRgtNo;
		 fm2.InqNo.value=tInqNo;
		 fm3.ClmNo.value=tRgtNo;
		 fm3.InqNo.value=tInqNo;
		 
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�

 */
function initInpBox() {
	
	try {
		
		tState = nullToEmpty("<%=tState%>"); 
		if(tState=="1"||tState=="2") {
			divSurveyReviewEntry.style.display="";
		}
		if(tState=="1"||tState=="2") {
			Save1.style.display="none";
			divFJ.style.display="none";
			Save2.style.display="none";
			Save3.style.display="none";
			showYC();
			fm.UpdateCourseButton.disabled=true;
			fm.DeleteCourseButton.disabled=true;
			fm5.AssFeeButton.disabled=true;
			fm5.UpdateFeeButton.disabled=true;
			fm5.DeleteFeeButton.disabled=true;
			fm2.saveAttache.disabled=true;
			fm2.updateAttache.disabled=true;
			fm2.deleteAttache.disabled=true;
			fm2.uploadfile.disabled=true;
			fm2.repalecefile.disabled=true;
			fm2.deletefile.disabled=true;			
			fm3.InqConfirm.disabled=true;
			fm3.ReturnSurveyProcess.disabled=true;	
		}
		if(tState=="2") {

			divSurveyShowReturnView.style.display="none";
		//	divSurveyViewInfo.style.display = "";
			document.getElementById("luruper").style.display="";
			document.getElementById("luruper1").style.display="";	
			document.getElementById("luruper2").style.display="";	
			document.getElementById("luruper3").style.display="";
			document.getElementById("per").style.display="";
			document.getElementById("per1").style.display="";	
			document.getElementById("per2").style.display="";	
			document.getElementById("per3").style.display="";
			document.getElementById("Save4").style.display="";

		}
		if(tState=="1"){
			document.getElementById("luruper").style.display="";
			document.getElementById("luruper1").style.display="";	
			document.getElementById("luruper2").style.display="";	
			document.getElementById("luruper3").style.display="";	
		}
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}

/**
 * ��null���ַ���תΪ��

 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}
// �����������б�ĳ�ʼ��
function initLLSurveyCourseGrid()
{                             
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i]=new Array();
        iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[i][1]="30px";                //�п�
        iArray[i][2]=10;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[i]=new Array();
        iArray[i][0]="�������";             //����
        iArray[i][1]="100px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[i]=new Array();
        iArray[i][0]="���鷽ʽ";             //����
        iArray[i][1]="60px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0; 
        
				iArray[i]=new Array();
        iArray[i][0]="����ص�";             //����
        iArray[i][1]="100px";                //�п�
        iArray[i][2]=200;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[i]=new Array();
        iArray[i][0]="��������";             //����
        iArray[i][1]="80px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

       	iArray[i]=new Array();
        iArray[i][0]="��һ������";             //����
        iArray[i][1]="80px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

       	iArray[i]=new Array();
        iArray[i][0]="�ڶ�������";             //����
        iArray[i][1]="80px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[i]=new Array();
        iArray[i][0]="��������";             //����
        iArray[i][1]="100px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[i]=new Array();
        iArray[i][0]="���鷽ʽ����";             //����
        iArray[i][1]="0px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=3;
        
        iArray[i]=new Array();
        iArray[i][0]="��������������˹�ϵ";             //����
        iArray[i][1]="0px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=3;
        
        iArray[i]=new Array();
        iArray[i][0]="��������������˹�ϵ����";             //����
        iArray[i][1]="0px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i][3]=3;                
                             
        LLSurveyCourseGrid = new MulLineEnter( "fm" , "LLSurveyCourseGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        LLSurveyCourseGrid.mulLineCount = 0;   
        LLSurveyCourseGrid.displayTitle = 1;
        LLSurveyCourseGrid.locked = 1;
        LLSurveyCourseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLSurveyCourseGrid.selBoxEventFuncName = "showLLSurveyCourseGridClick";//���RadioBoxʱ��Ӧ�ĺ�����
        LLSurveyCourseGrid.hiddenPlus=1;
        LLSurveyCourseGrid.hiddenSubtraction=1;
        LLSurveyCourseGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������۱�ĳ�ʼ��
function initLLSurveyFeeGrid()
{                           
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i]=new Array();
        iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[i][1]="30px";                //�п�
        iArray[i][2]=10;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[i]=new Array();
        iArray[i][0]="��������";             //����
        iArray[i][1]="80px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[i]=new Array();
        iArray[i][0]="FeeType";             //�������ͱ���
        iArray[i][1]="0px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=3;       
        
        iArray[i]=new Array();
        iArray[i][0]="���ý��";             //����
        iArray[i][1]="80px";                //�п�
        iArray[i][2]=200;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[i]=new Array();
        iArray[i][0]="����ʱ��";             //����
        iArray[i][1]="80px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0; 

        iArray[i]=new Array();
        iArray[i][0]="�����";             //����
        iArray[i][1]="100px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[i]=new Array();
        iArray[i][0]="��ʽ";             //����
        iArray[i][1]="60px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        
        iArray[i]=new Array();
        iArray[i][0]="PayeeType";             //��ʽ����
        iArray[i][1]="0px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i++][3]=3;   

        iArray[i]=new Array();
        iArray[i][0]="��ע";             //����
        iArray[i][1]="0px";                //�п�
        iArray[i][2]=100;                  //�����ֵ
        iArray[i][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������     
                 
        LLSurveyFeeGrid = new MulLineEnter( "fm5" , "LLSurveyFeeGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        LLSurveyFeeGrid.mulLineCount = 0;   
        LLSurveyFeeGrid.displayTitle = 1;
        LLSurveyFeeGrid.locked = 1;
        LLSurveyFeeGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLSurveyFeeGrid.selBoxEventFuncName = "showLLSurveyFeeClick";//���RadioBoxʱ��Ӧ�ĺ�����
        LLSurveyFeeGrid.hiddenPlus=1;
        LLSurveyFeeGrid.hiddenSubtraction=1;
        LLSurveyFeeGrid.loadMulLine(iArray); 
     
    }
    catch(ex)
    {
        alert(ex);
    }
}

//��ʼ������mulline
function initSurveyAttachmentGrid()	{
	var iArray = new Array();  
	var i=0;    
	try
	{
      iArray[i]=new Array();
      iArray[i][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[i][1]="30px";            		//�п�
      iArray[i][2]=10;            			//�����ֵ
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[i]=new Array();
      iArray[i][0]="�������";         		//����
      iArray[i][1]="0px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[i]=new Array();
      iArray[i][0]="�������";         		//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[i]=new Array();
      iArray[i][0]="��������";         		//����
      iArray[i][1]="150px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=0;  
      
      iArray[i]=new Array();
      iArray[i][0]="�ļ�����";         		//����
      iArray[i][1]="120px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[i]=new Array();
      iArray[i][0]="ԭ����ʶ";         		//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=0;    
      
      iArray[i]=new Array();
      iArray[i][0]="�ϴ�����";         		//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=0;    
      
	    iArray[i]=new Array();
      iArray[i][0]="�ϴ�����";         		//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[i]=new Array();
      iArray[i][0]="�޸�����";         		//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
			iArray[i]=new Array();
      iArray[i][0]="filepath";         		//����
      iArray[i][1]="0px";            		//�п�
      iArray[i][2]=200;            			//�����ֵ
      iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
			iArray[i]=new Array();
      iArray[i][0]="originallogo";         		//����
      iArray[i][1]="0px";            		//�п�
      iArray[i][2]=200;            			//�����ֵ
      iArray[i][3]=3; 

      SurveyAttachmentGrid = new MulLineEnter("fm2","SurveyAttachmentGrid"); 
      //��Щ���Ա�����loadMulLineǰ
      SurveyAttachmentGrid.mulLineCount = 0;   
      SurveyAttachmentGrid.displayTitle = 1;
      SurveyAttachmentGrid.locked = 1;
      SurveyAttachmentGrid.canSel = 1;
      SurveyAttachmentGrid.canChk = 0;
      SurveyAttachmentGrid.hiddenPlus = 1;
      SurveyAttachmentGrid.hiddenSubtraction = 1; 
      SurveyAttachmentGrid.selBoxEventFuncName = "showSurveyAttachment";     
      SurveyAttachmentGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>