<%
/***************************************************************
 * <p>ProName��LLClaimPreinvestInit.jsp</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
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
		initInvestListGrid();
		initOnInvestGrid();
		initValue();
			
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�

 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		fm.saveButton.disabled=false;
		fm.recoverButton.disabled=true;
		fm.deleteButton.disabled=true;
		fm.doCloseButton.disabled=true;
		fm.doButton.disabled=true;
		
		if(tMode=="0"){
			fm.inqTuenBack.style.display="";
			fm.saveButton.style.display="";
			fm.recoverButton.style.display="";
			fm.deleteButton.style.display="";
			fm.doCloseButton.style.display="";
			fm.doButton.style.display="";
			fm.goBackButton.style.display="";
		}else if(tMode="1"){
			fm.inqTuenBack.style.display="none";
			fm.saveButton.style.display="none";
			fm.recoverButton.style.display="none";
			fm.deleteButton.style.display="none";
			fm.doCloseButton.style.display="none";
			fm.doButton.style.display="none";
			fm.goBackButton.style.display="none";
		}
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
// �ѷ��������Ϣ�б�
function initInvestListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="������ˮ��";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
 
 		iArray[i]=new Array();
    iArray[i][0]="������/������";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢������"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
	
		iArray[i]=new Array();
    iArray[i][0]="�������ͱ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="����ԭ�����"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="����ԭ��"; 
    iArray[i][1]="120px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢�����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
		
		iArray[i]=new Array();
    iArray[i][0]="�Ƿ���ص���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
		
		iArray[i]=new Array();
    iArray[i][0]="���鷢��ʽ����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="���鷢��ʽ"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
		
		iArray[i]=new Array();
    iArray[i][0]="����״̬����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
		
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ر�ԭ�����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="����ر�ԭ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="����Ŀ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="����ƻ�"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="��ע��Ϣ"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    InvestListGrid = new MulLineEnter( "fm" , "InvestListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    InvestListGrid.mulLineCount = 0;   
    InvestListGrid.displayTitle = 1;
    InvestListGrid.locked = 0;
    InvestListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    InvestListGrid.hiddenPlus=1;
    InvestListGrid.selBoxEventFuncName ="getInvestListInfo"; //��Ӧ�ĺ���������������
    InvestListGrid.hiddenSubtraction=1;
    InvestListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}

// ������Ϣ�б�ĳ�ʼ������
function initOnInvestGrid() {
	       
    var iArray = new Array();
    var i=0;
    try{
    
   iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="������ˮ��";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
 
 		iArray[i]=new Array();
    iArray[i][0]="������/������";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢������"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
	
		iArray[i]=new Array();
    iArray[i][0]="�������ͱ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="����ԭ�����"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="����ԭ��"; 
    iArray[i][1]="120px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢�����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

		iArray[i]=new Array();
    iArray[i][0]="���鷢��ʽ����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="���鷢��ʽ"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

		iArray[i]=new Array();
    iArray[i][0]="����״̬����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
		
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ر�ԭ�����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="����ر�ԭ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="����Ŀ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="����ƻ�"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="��ע��Ϣ"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    OnInvestGrid = new MulLineEnter( "fm" , "OnInvestGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    OnInvestGrid.mulLineCount = 0;   
    OnInvestGrid.displayTitle = 1;
    OnInvestGrid.locked = 0;
    OnInvestGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    OnInvestGrid.selBoxEventFuncName ="getOnInvestInfo"; //��Ӧ�ĺ���������������
    OnInvestGrid.hiddenPlus=1;
    OnInvestGrid.hiddenSubtraction=1;
    OnInvestGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->OnInvestGrid");
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
