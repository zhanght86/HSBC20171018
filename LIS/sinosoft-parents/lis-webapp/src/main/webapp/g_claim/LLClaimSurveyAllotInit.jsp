<%
/***************************************************************
 * <p>PreName��LLClaimSurveyAllotInput.jsp</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-21
 ****************************************************************/
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
/**
 * ��ʼ������

 */
function initForm() {
	
	try {
		initParam();
		initInpBox();
		initButton();

    initSurveyTaskGrid();
    initSurveyTask1Grid();
		limitSurveyDept();//��ѯȨ��

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


function initSurveyTaskGrid() {
  var iArray = new Array();
  var  i=0;
  try
  {
  	
    iArray[i]=new Array();
    iArray[i][0]="���";               	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";                //�п�
    iArray[i][2]=8;                  	//�����ֵ
    iArray[i++][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[i]=new Array();
    iArray[i][0]="�������";          
    iArray[i][1]="80px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=3;  

    iArray[i]=new Array();
    iArray[i][0]="������";            
    iArray[i][1]="120px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

    iArray[i]=new Array();
    iArray[i][0]="��������";        
    iArray[i][1]="60px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

        
		
		iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";          
    iArray[i][1]="120px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=0;       

 /*  iArray[i]=new Array();
    iArray[i][0]="�����˿ͻ���";          
    iArray[i][1]="0px";              
    iArray[i][2]=300;                 
    iArray[i++][3]=3; */

    iArray[i]=new Array();
    iArray[i][0]="����������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="��������";          
    iArray[i][1]="60px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="����ԭ��";          
    iArray[i][1]="180px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢�����";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ���ص���";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;
    
    SurveyTaskGrid = new MulLineEnter("fm","SurveyTaskGrid");
    SurveyTaskGrid.mulLineCount =0;
    SurveyTaskGrid.displayTitle = 1;
    SurveyTaskGrid.locked = 1;
    SurveyTaskGrid.canSel =1;
    SurveyTaskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    SurveyTaskGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    SurveyTaskGrid.selBoxEventFuncName = "SurveyTaskGridSelClick";
    SurveyTaskGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}


function initSurveyTask1Grid() {
  var iArray = new Array();
  var  i=0;
  try
  {
    iArray[i]=new Array();
    iArray[i][0]="���";               	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";                //�п�
    iArray[i][2]=8;                  	//�����ֵ
    iArray[i++][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������

	iArray[i]=new Array();
    iArray[i][0]="�������";            
    iArray[i][1]="80px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=3; 

    iArray[i]=new Array();
    iArray[i][0]="������";            
    iArray[i][1]="120px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

    iArray[i]=new Array();
    iArray[i][0]="��������";        
    iArray[i][1]="60px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   
       
		
	iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";          
    iArray[i][1]="120px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=0;       
/*
    iArray[i]=new Array();
    iArray[i][0]="�����˿ͻ���";          
    iArray[i][1]="0px";              
    iArray[i][2]=300;                 
    iArray[i++][3]=3;                   
*/
    iArray[i]=new Array();
    iArray[i][0]="����������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="��������";          
    iArray[i][1]="60px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="����ԭ��";          
    iArray[i][1]="180px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢�����";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
		iArray[i]=new Array();
    iArray[i][0]="�������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=3; 

    SurveyTask1Grid = new MulLineEnter("fm","SurveyTask1Grid");
    SurveyTask1Grid.mulLineCount =0;
    SurveyTask1Grid.displayTitle = 1;
    SurveyTask1Grid.locked = 1;
    SurveyTask1Grid.canSel =1;
    SurveyTask1Grid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    SurveyTask1Grid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    SurveyTask1Grid.selBoxEventFuncName = "SurveyTaskGridSelClick1";
    SurveyTask1Grid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}

</script>