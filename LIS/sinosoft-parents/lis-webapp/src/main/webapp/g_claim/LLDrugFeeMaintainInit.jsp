<%
/***************************************************************
 * <p>ProName��LLDrugFeeMaintainInit.jsp</p>
 * <p>Title��ҩƷ��Ϣά��</p>
 * <p>Description��ҩƷ��Ϣά��</p>
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
		//restartPermission();
		initDrugFeeMaintainGrid();
		//queryPermissionInfo();
		
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
function initDrugFeeMaintainGrid(){
	
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
		iArray[i][0]="ҩƷ��ˮ��";    			//����                                                     
		iArray[i][1]="40px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";    			//����                                                     
		iArray[i][1]="40px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;     
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="����";    			//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
			                                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
		                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҩƷ����";         		//����                                                     
		iArray[i][1]="100px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������                      
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��Ʒ����";         		//����                                                     
		iArray[i][1]="100px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="����";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="���";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		 
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="�Ը�����";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҽ������";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="�۸�";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������  
      
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҽ�����ͱ���";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������  
      
		DrugFeeMaintainGrid = new MulLineEnter("fm", "DrugFeeMaintainGrid");
		DrugFeeMaintainGrid.mulLineCount = 0;
		DrugFeeMaintainGrid.displayTitle = 1;
		DrugFeeMaintainGrid.locked = 1;
		DrugFeeMaintainGrid.canSel = 1;
		DrugFeeMaintainGrid.canChk = 0;
		DrugFeeMaintainGrid.hiddenSubtraction = 1;
		DrugFeeMaintainGrid.hiddenPlus = 1;
		DrugFeeMaintainGrid.selBoxEventFuncName = "showPermissionInfo";
		DrugFeeMaintainGrid.loadMulLine(iArray);

		}catch(ex){
			alert("��ʼ����Ϣ����");
			return false;
		}
	}
	
</script>
