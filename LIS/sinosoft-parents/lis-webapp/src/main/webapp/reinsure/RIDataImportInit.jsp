<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2008-04-10
%>
<!--�û�У����-->

<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file = "../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>

<%
 	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getAttribute("GI");
	String Operator=tG.Operator;
	String Comcode=tG.ManageCom;
 	String CurrentDate= PubFun.getCurrentDate(); 
%>
<script type="text/javascript">
function initInpBox(){
  try{
    fm.all('EndDate').value= '';
    fm.all('ProcessType').value= '';
    fm.all('ProcessTypeName').value= '';
    divList.style.display='none';
  }catch(ex) {
    myAlert("���г�ʼ���ǳ��ִ���");
  }
}

function initForm(){
  try{
    initInpBox();   
    initAccmulateGrid();
    initTaskListGrid();
  } catch(re){
    myAlert("ReContManageInit.jsp-->"+"��ʼ���������!");
  }
}

//�ۼƷ����б�ĳ�ʼ��
function initAccmulateGrid(){
    var iArray = new Array();   
    try{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="�ۼƷ��ձ���"; 
		iArray[1][1]="70px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="�ۼƷ�������";
		iArray[2][1]="150px";  
		iArray[2][2]=100; 
		iArray[2][3]=0;	
		
		iArray[3]=new Array();
		iArray[3][0]="�㷨����";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=3;	
		
		iArray[4]=new Array();
		iArray[4][0]="״̬";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=3;									
			
	  	AccmulateGrid = new MulLineEnter( "fm" , "AccmulateGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ
	  	AccmulateGrid.mulLineCount = 0;   
	  	AccmulateGrid.displayTitle = 1;
	  	AccmulateGrid.locked = 0;
	  	AccmulateGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	  	AccmulateGrid.canChk = 1; //�Ƿ����Ӷ�ѡ��,1Ϊ��ʾCheckBox�У�0Ϊ����ʾ (ȱʡֵ)
	  	AccmulateGrid.hiddenPlus=1; 
	  	AccmulateGrid.hiddenSubtraction=1;
	  	AccmulateGrid.loadMulLine(iArray); 
	  	
    }catch(ex){
        myAlert(ex);
    }
}

// �����б�ĳ�ʼ��
function initTaskListGrid(){
    var iArray = new Array();   
    try{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="�����������"; 
		iArray[1][1]="70px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="������������";
		iArray[2][1]="150px";  
		iArray[2][2]=100; 
		iArray[2][3]=0;	
		
		iArray[3]=new Array();
		iArray[3][0]="�㷨����";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=3;	
		
		iArray[4]=new Array();
		iArray[4][0]="�㷨ִ��˳��";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=3;	
		
		iArray[5]=new Array();
		iArray[5][0]="�������������";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=3;	
		
		iArray[6]=new Array();
		iArray[6][0]="���㴦����";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=3;									
			
	  	TaskListGrid = new MulLineEnter( "fm" , "TaskListGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ
	  	TaskListGrid.mulLineCount = 0;   
	  	TaskListGrid.displayTitle = 1;
	  	TaskListGrid.locked = 0;
	  	TaskListGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	  	TaskListGrid.canChk = 1; //�Ƿ����Ӷ�ѡ��,1Ϊ��ʾCheckBox�У�0Ϊ����ʾ (ȱʡֵ)
	  	TaskListGrid.hiddenPlus=1; 
	  	TaskListGrid.hiddenSubtraction=1;
	  	TaskListGrid.loadMulLine(iArray); 	  	
    }catch(ex){
        myAlert(ex);
    }
}

</script>