<%
/***************************************************************
 * <p>ProName��LLClaimSurveyAppinput.jsp</p>
 * <p>Title������¼������</p>
 * <p>Description������¼������</p>
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
		
		initDefoGrid();
		
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
		
		fm.DModify.disabled=true;
		fm.DDelete.disabled=true;
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
function initDefoGrid(){
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
			iArray[i]=new Array();
      iArray[i][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��             
      iArray[i][1]="30px";         			//�п�                                                     
      iArray[i][2]=10;          			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲����ͱ���";    			//����                                                     
      iArray[i][1]="40px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
         
			iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲�����";    			//����                                                     
      iArray[i][1]="80px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲з���";         		//����                                                     
      iArray[i][1]="40px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                          
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲з�������";         		//����                                                     
      iArray[i][1]="80px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲м���";         		//����                                                     
      iArray[i][1]="40px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                          
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲м�������";         		//����                                                     
      iArray[i][1]="50px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲д���";         		//����                                                     
      iArray[i][1]="120px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲д�������";         		//����                                                     
      iArray[i][1]="160px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�˲и�������";         			//����                                                     
      iArray[i][1]="50px";            			//�п�                                                     
      iArray[i][2]=100;            			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������    
      
     
      
			DefoGrid = new MulLineEnter("fm", "DefoGrid");
			DefoGrid.mulLineCount = 0;
			DefoGrid.displayTitle = 1;
			DefoGrid.locked = 1;
			DefoGrid.canSel = 1;
			DefoGrid.canChk = 0;
			DefoGrid.hiddenSubtraction = 1;
			DefoGrid.hiddenPlus = 1;
			DefoGrid.selBoxEventFuncName = "showDefoInfo";
			DefoGrid.loadMulLine(iArray);

			
		}catch(ex){
			
			}
	}
</script>
