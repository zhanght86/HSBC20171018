<%
/***************************************************************
 * <p>ProName��LLClaimCustomerInit.jsp</p>
 * <p>Title��ϵͳ����Ͷ���˲�ѯ</p>
 * <p>Description��ϵͳ����Ͷ���˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-04-17
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

		initGrpAppntListGrid();
		initGrpAppntDetailGrid();
		queryGrpAppnt();
		
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
// ��ϽͶ����λ�б�ĳ�ʼ��
function initGrpAppntDetailGrid(){
	
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
      iArray[i][0]="Ͷ���˱���";
      iArray[i][1]="120px";
      iArray[i][2]=120;
      iArray[i++][3]=0;            
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="Ͷ��������";                                                
      iArray[i][1]="180px";                                             
      iArray[i][2]=180;
      iArray[i++][3]=0;           
                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�������";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0; 
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�б�����";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�б�ֹ��";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;      
      
			GrpAppntDetailGrid = new MulLineEnter("fm", "GrpAppntDetailGrid");
			GrpAppntDetailGrid.mulLineCount = 0;
			GrpAppntDetailGrid.displayTitle = 1;
			GrpAppntDetailGrid.locked = 1;
			GrpAppntDetailGrid.canSel = 0;
			GrpAppntDetailGrid.canChk = 0;
			GrpAppntDetailGrid.hiddenSubtraction = 1;
			GrpAppntDetailGrid.hiddenPlus = 1;
			GrpAppntDetailGrid.loadMulLine(iArray);

			
		}catch(ex){}
	}
	
// ������Ϣ�б�ĳ�ʼ��
function initGrpAppntListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
			iArray[i]=new Array();
      iArray[i][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��             
      iArray[i][1]="30px";         			//�п�                                                     
      iArray[i][2]=10;          			//�����ֵ                                                 
      iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      

      iArray[i]=new Array();
      iArray[i][0]="AppntType";
      iArray[i][1]="60px";
      iArray[i][2]=100;
      iArray[i++][3]=3;         
                                                                                                                   
      iArray[i]=new Array();
      iArray[i][0]="Ͷ���˱���";
      iArray[i][1]="120px";
      iArray[i][2]=120;
      iArray[i++][3]=0;            
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="Ͷ��������";                                                
      iArray[i][1]="180px";                                             
      iArray[i][2]=180;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();
      iArray[i][0]="Ͷ��������";
      iArray[i][1]="120px";
      iArray[i][2]=120;
      iArray[i++][3]=0;      
                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�������";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�б�����";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="�б�ֹ��";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;      
      
			GrpAppntListGrid = new MulLineEnter("fm", "GrpAppntListGrid");
			GrpAppntListGrid.mulLineCount = 0;
			GrpAppntListGrid.displayTitle = 1;
			GrpAppntListGrid.locked = 1;
			GrpAppntListGrid.canSel = 1;
			GrpAppntListGrid.canChk = 0;
			GrpAppntListGrid.hiddenSubtraction = 1;
			GrpAppntListGrid.hiddenPlus = 1;
			GrpAppntListGrid.selBoxEventFuncName = "showDetail";
			GrpAppntListGrid.loadMulLine(iArray);

			
		}catch(ex){}
	}	
	
</script>
