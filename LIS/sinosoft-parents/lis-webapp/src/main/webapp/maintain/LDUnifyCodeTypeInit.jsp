<%
//-------------------------------------------------
//�������ƣ�LDUnifyCodeTypeInit.jsp
//�����ܣ�ϵͳͳһ����ά��
//�������ڣ�2012-01-01
//������  ��������
//�޸���  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//-------------------------------------------------
%>
<script language="JavaScript">
	
/**
 * ��ʼ������
 */
function initForm() {
  try {
  	//��������
  	initCodeTypeInfo();
  	initParam();
    initInpBox();
		initCodeTypeGrid();
    
    //����
    initCodeInfo();
    initCodeGrid();
  }catch(re) {
    alert("LDUnifyCodeTypeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

/**
 * ��ʼ�����洫�����Ĳ���
 */
function initParam() {
	try {
  }catch(re) {
    alert("LDUnifyCodeTypeInit.jsp-->initParam�����з����쳣:��ʼ����������!");
  }
}

/**
 * ��ʼ��ֵ	
 */
function initInpBox() 
{ 
  try { 
	
  }catch(ex) {
    alert("LDUnifyCodeTypeInit.jsp-->InitInpBox�����з����쳣:��ʼ��ֵ����!");
  }      
}                                     

/**
 * ��null���ַ���תΪ��
 */
function nullToEmpty(string)
{
	if((string == "null") || (string == "undefined")) {
		string = "";
	}
	return string;
}

/**
 * ��ť�ĳ�ʼ��
 */
function initButton() {
	try { 
	
	}catch(ex) {
		alert("LDUnifyCodeTypeInit.jsp-->InitInpBox�����з����쳣:��ʼ����ť����!");
	}  
}

/**
 * ��ʼ���������������
 */
function initCodeTypeInfo() {
	
	fm.SysCode.value="";
	fm.SysCodeName.value="";
	fm.CodeType.value="";
	fm.CodeTypeName.value="";
	fm.MaintainFlag.value="";
	fm.MaintainFlagName.value="";
	fm.State.value="";
	fm.StateName.value="";
	fm.CodeTypeDescription.value="";
	divSysCodeReadOnly.style.display = "none";
	divSysCode.style.display="";
	fm.CodeType.readOnly = false;
}

/**
 * ��ʼ�����������
 */
function initCodeInfo() {
	
	fm.SysCode1.value="";
	fm.SysCode1Name.value="";
	fm.CodeType1.value="";
	fm.CodeType1Name.value="";
	fm.State1.value="";
	fm.State1Name.value="";
	fm.Code.value="";
	fm.CodeName.value="";
	fm.CodeAlias.value="";	
	divSysCodeReadOnly1.style.display = "none";
	divCodeTypeReadOnly1.style.display = "none";
	divSysCode1.style.display = "";
	divCodeType1.style.display = "";
	fm.Code.readOnly = false;
}

/**
 * �������͵ĳ�ʼ��
 */
function initCodeTypeGrid() {
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i=0;
	try {
		iArray[i]=new Array();
		iArray[i][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[i][1]="30px";         //�п�
		iArray[i][2]=30;            //�����ֵ
		iArray[i++][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[i]=new Array();
		iArray[i][0]="ϵͳ����";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="��������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;        
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="������������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;        
		iArray[i++][3]=0;        

		iArray[i]=new Array();
		iArray[i][0]="ά����ʶ";         
		iArray[i][1]="100px";        
		iArray[i][2]=100;        
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="״̬";        
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="������������";         
		iArray[i][1]="200px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;      
		
		iArray[i]=new Array();
		iArray[i][0]="������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="��������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;     
	       	
		iArray[i]=new Array();
		iArray[i][0]="ϵͳ����";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();
		iArray[i][0]="ά������";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;  
		
		iArray[i]=new Array();
		iArray[i][0]="״̬����";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;  
		
		CodeTypeInfoGrid = new MulLineEnter( "fm" , "CodeTypeInfoGrid" );

		//��Щ���Ա�����loadMulLineǰ
		CodeTypeInfoGrid.mulLineCount = 0;
		CodeTypeInfoGrid.displayTitle = 1;
		CodeTypeInfoGrid.canSel=1;
		CodeTypeInfoGrid.hiddenPlus = 1;
		CodeTypeInfoGrid.hiddenSubtraction = 1;
		CodeTypeInfoGrid.selBoxEventFuncName = "showCodeTypeInfo";//���RadioBoxʱ��Ӧ�ĺ�����
		CodeTypeInfoGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert("��ʼ��CodeTypeGridʱ����"+ ex);
	}
}

/**
 * ���ݵĳ�ʼ��
 */
function initCodeGrid() {
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i=0;
	try {
		iArray[i]=new Array();
		iArray[i][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[i][1]="30px";         //�п�
		iArray[i][2]=100;            //�����ֵ
		iArray[i++][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[i]=new Array();
		iArray[i][0]="ϵͳ����";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="������������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;        
		iArray[i++][3]=0;                

		iArray[i]=new Array();
		iArray[i][0]="����";        
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="��������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;      
		
		iArray[i]=new Array();
		iArray[i][0]="�������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="״̬";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="��������";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;     
		
		iArray[i]=new Array();
		iArray[i][0]="ϵͳ����";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();
		iArray[i][0]="���ʹ���";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;  
		
		iArray[i]=new Array();
		iArray[i][0]="״̬����";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;   
	       	
		CodeInfoGrid = new MulLineEnter( "fm" , "CodeInfoGrid" );

		//��Щ���Ա�����loadMulLineǰ
		CodeInfoGrid.mulLineCount = 0;
		CodeInfoGrid.displayTitle = 1;
		CodeInfoGrid.canSel=1;
		CodeInfoGrid.hiddenPlus = 1;
		CodeInfoGrid.hiddenSubtraction = 1;
		CodeInfoGrid.selBoxEventFuncName = "showCodeInfo";//���RadioBoxʱ��Ӧ�ĺ�����
		CodeInfoGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert("��ʼ��CodeGridʱ����"+ ex);
	}
}
</script>
