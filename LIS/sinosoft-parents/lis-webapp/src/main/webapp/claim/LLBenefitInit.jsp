<!--�û�У����-->
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	//=======================BGN========================
	String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//String tContNo = request.getParameter("ContNo"); 
	String tCustNo = request.getParameter("CustomerNo"); 
	//=======================END========================
%>
<script type="text/javascript">
var mCurrentDate = "";
//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam(){
	mCurrentDate="<%= CurrentDate %>";
    fm.CustNo.value = nullToEmpty("<%= tCustNo %>");
    
}

//��null���ַ���תΪ��
function nullToEmpty(string){
	if ((string == "null") || (string == "undefined")){
		string = "";
	}
	return string;
}

function initForm(){
    try{
        initParam();
        initBox();                   	//��ʼ�����
        initPaidDutyGrid();		//�б��˺���Ϣ
        initPaidFeeGrid();			//�����˺���Ϣ
        initQuery();					//��ʼ����ѯ
    }catch(re){
        alert("LLMedicalFeeInpInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

/**=========================================================================
    ҳ���ʼ��
   =========================================================================
*/
function initBox(){
  	try{
  	}catch(ex){
    	alert("LLMedicalFeeInpInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  	}
}

/**=========================================================================
    ���⸶������������
   =========================================================================
*/
function initPaidDutyGrid(){
    var iArray = new Array();
    try{  
    	var i=0;
    	
    	iArray[i]=new Array();
        iArray[i][0]="���";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="������";
        iArray[i][1]="100px";
        iArray[i][2]=10;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="���ֱ���";
        iArray[i][1]="90px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="��������";
        iArray[i][1]="150px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="�����⸶����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 

        iArray[i]=new Array();
        iArray[i][0]="������������";
        iArray[i][1]="120px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="���������⸶����";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        iArray[i]=new Array();
        iArray[i][0]="�˵������ܽ��";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        iArray[i]=new Array();
        iArray[i][0]="���⸶���";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="ʣ���⸶���";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
       /* 
        iArray[i]=new Array();
        iArray[i][0]="Ԥ��Ȩ�ܽ��";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="��Ԥ��Ȩ���";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="Ԥ��Ȩʣ����";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="Ԥ��Ȩ�ܴ���";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="��Ԥ��Ȩ����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="Ԥ��Ȩʣ�����";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="Ԥ��Ȩ������";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="��Ԥ��Ȩ����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="Ԥ��Ȩʣ������";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        */
        iArray[i]=new Array();
        iArray[i][0]="���α���";
        iArray[i][1]="0px";
        iArray[i][2]=140;
        iArray[i++][3]=3;  
        
        iArray[i]=new Array();
        iArray[i][0]="�������α���";
        iArray[i][1]="0px";
        iArray[i][2]=140;
        iArray[i++][3]=3;  
        
        PaidDutyGrid = new MulLineEnter("fm","PaidDutyGrid");
        PaidDutyGrid.mulLineCount = 0;
        PaidDutyGrid.displayTitle = 1;
        PaidDutyGrid.locked = 0;
//      PaidDutyGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        PaidDutyGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        PaidDutyGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        PaidDutyGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        PaidDutyGrid.selBoxEventFuncName = "getPaidDutyGrid"; //��������
//      PaidDutyGrid.selBoxEventFuncParm ="";          //����        
        PaidDutyGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}

/**=========================================================================
    ���⸶�˵�����
   =========================================================================
*/
function initPaidFeeGrid(){
    var iArray = new Array();
    try{
    	var i=0;
    	iArray[i]=new Array();
        iArray[i][0]="���";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="�˵�����";
        iArray[i][1]="150px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="��������";
        iArray[i][1]="70px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="�˵��⸶����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="�˵�������";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        /*
        iArray[i]=new Array();
        iArray[i][0]="��Ԥ��Ȩ���";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        */
        iArray[i]=new Array();
        iArray[i][0]="ʣ���⸶���";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="�⸶��������";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="���⸶����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        /*
        iArray[i]=new Array();
        iArray[i][0]="��Ԥ��Ȩ����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
         */ 
        iArray[i]=new Array();
        iArray[i][0]="ʣ���⸶����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="�⸶��������";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="���⸶����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        /*
        iArray[i]=new Array();
        iArray[i][0]="��Ԥ��Ȩ����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        */  
        iArray[i]=new Array();
        iArray[i][0]="ʣ���⸶����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        PaidFeeGrid = new MulLineEnter("fm","PaidFeeGrid");
        PaidFeeGrid.mulLineCount = 0;
        PaidFeeGrid.displayTitle = 1;
        PaidFeeGrid.locked = 0;
//      PaidFeeGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        PaidFeeGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        PaidFeeGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        PaidFeeGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        PaidFeeGrid.selBoxEventFuncName = "getPaidFeeGrid"; //��������
//      PaidFeeGrid.selBoxEventFuncParm ="";          //����        
        PaidFeeGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}
</script>

