<!--�û�У����-->
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%

	//=======================BGN========================
	String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	String tRgtNo = request.getParameter("RgtNo"); //�ⰸ��
	String tContNo = request.getParameter("ContNo"); 
	String tCustomerNo = request.getParameter("CustomerNo"); 
	System.out.println("xxx"+tCustomerNo);
	//=======================END========================
%>

<script type="text/javascript">
var mCurrentDate = "";
//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam(){
	<%
	System.out.println("zzz"+tCustomerNo);

	%>
	mCurrentDate=<%=CurrentDate%>;

    fm.RgtNo.value = nullToEmpty(<%=tRgtNo%>);
   
    fm.ContNo.value = nullToEmpty(<%=tContNo%>);
    
    fm.CustNo.value = nullToEmpty(<%=tCustomerNo%>);
   
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
        initThisDutyGrid();		//�б��˺���Ϣ
        initThisFeeGrid();			//�����˺���Ϣ
        initQuery();			//��ʼ����ѯ
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
function initThisDutyGrid(){
    var iArray = new Array();
    try{ 
    	var i=0;
    	iArray[i]=new Array();
        iArray[i][0]="���";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="��������";
        iArray[i][1]="120px";
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
        iArray[i][0]="���⸶���";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="ʣ���⸶���";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="�˵�ʵ�ʽ��";
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
        iArray[i][0]="�Ƿ���Ԥ��Ȩ";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="Ԥ��Ȩ���";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        */
        iArray[i]=new Array();
        iArray[i][0]="�Ƿ�۳��Ը�����";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="�Ը�����";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
       
        iArray[i]=new Array();
        iArray[i][0]="�����";
        iArray[i][1]="60px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="�����⸶���";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="��������";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=3; 
       
        ThisDutyGrid = new MulLineEnter("fm","ThisDutyGrid");
        ThisDutyGrid.mulLineCount = 0;
        ThisDutyGrid.displayTitle = 1;
        ThisDutyGrid.locked = 0;
//      ThisDutyGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ThisDutyGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        ThisDutyGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ThisDutyGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        ThisDutyGrid.selBoxEventFuncName = "getThisDutyGrid"; //��������
//      ThisDutyGrid.selBoxEventFuncParm ="";          //����        
        ThisDutyGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}

/**=========================================================================
    ���⸶�˵�����
   =========================================================================
*/
function initThisFeeGrid(){
    var iArray = new Array();
    try{
    	var i= 0 ;
    	iArray[i]=new Array();
        iArray[i][0]="���";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="�˵�����";
        iArray[i][1]="130px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="��������";
        iArray[i][1]="70px";
        iArray[i][2]=140;
        iArray[i++][3]=0;        
        
        iArray[i]=new Array();
        iArray[i][0]="�˵��⸶����";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="ʣ���⸶���";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="�˵�ʵ�ʽ��";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="�⸶��������";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="ʣ�����";
        iArray[i][1]="70px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        iArray[i]=new Array();
        iArray[i][0]="�����⸶����";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="�⸶��������";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="ʣ���⸶����";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="�����⸶����";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="ÿ���⸶�������";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="ÿ���⸶�̶����";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        /*
        iArray[i]=new Array();
        iArray[i][0]="�Ƿ�Ԥ��Ȩ��ʶ";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        */
        iArray[i]=new Array();
        iArray[i][0]="���κ�����";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        ThisFeeGrid = new MulLineEnter("fm","ThisFeeGrid");
        ThisFeeGrid.mulLineCount = 0;
        ThisFeeGrid.displayTitle = 1;
        ThisFeeGrid.locked = 0;
//      ThisFeeGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ThisFeeGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        ThisFeeGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ThisFeeGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        ThisFeeGrid.selBoxEventFuncName = "getThisFeeGrid"; //��������
//      ThisFeeGrid.selBoxEventFuncParm ="";          //����        
        ThisFeeGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}
</script>

