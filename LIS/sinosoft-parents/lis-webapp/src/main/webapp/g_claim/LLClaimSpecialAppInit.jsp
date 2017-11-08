<%
/***************************************************************
 * <p>ProName��LLClaimSpecialInput.js</p>
 * <p>Title��������������</p>
 * <p>Description��������������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
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

		initMainCaseGrid();
		initSelfCaseGrid();

		querySelf();
		
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
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseAppSql");
		tSQLInfo.setSqlId("LLClaimCaseAppSql3");
		tSQLInfo.addSubPara(mManageCom);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("����������ʧ�ܣ�");
			return false;
		} else {
			var tComGrade = tArr[0][0];
			if (tComGrade!=null && tComGrade=="01") {//һ������������������
				fm.AddCase.style.display = "none";
			}
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
// ��ʼ��������Ϣ�б�
function initMainCaseGrid() {
		
		turnPage1 = new turnPageClass();
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������    

    iArray[i]=new Array();
    iArray[i][0]="���κ�";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="ʱЧ����(������)"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    MainCaseGrid = new MulLineEnter( "fm" , "MainCaseGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    MainCaseGrid.mulLineCount = 0;   
    MainCaseGrid.displayTitle = 1;
    MainCaseGrid.locked = 0;
    MainCaseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    MainCaseGrid.hiddenPlus=1;
    MainCaseGrid.hiddenSubtraction=1;
    MainCaseGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}

// ��ʼ�����˳��°�����Ϣ�б�
function initSelfCaseGrid() {
	   
	  turnPage2 = new turnPageClass();
    var iArray = new Array();
    var i=0;
    try{
    	
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������    

    iArray[i]=new Array();
    iArray[i][0]="���κ�";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i][3]=0;
    iArray[i++][7]="enterCase";
    
    iArray[i]=new Array();
    iArray[i][0]="������";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="ʱЧ����(������)"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    SelfCaseGrid = new MulLineEnter( "fm" , "SelfCaseGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfCaseGrid.mulLineCount = 0;   
    SelfCaseGrid.displayTitle = 1;
    SelfCaseGrid.locked = 0;
    SelfCaseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    //SelfCaseGrid.selBoxEventFuncName ="selfLLReport"; //��Ӧ�ĺ���������������
    SelfCaseGrid.hiddenPlus=1;
    SelfCaseGrid.hiddenSubtraction=1;
    SelfCaseGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->SelfCaseGrid");
    }
}
</script>
