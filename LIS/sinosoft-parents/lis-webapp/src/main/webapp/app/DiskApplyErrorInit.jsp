<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() {
    fm.GrpContNo.value = "<%=request.getParameter("GrpContNo")%>"; 
    fm.BatchNo.value = "<%=request.getParameter("BatchNo")%>"; 
    fm.ImportFlag.value = "<%=request.getParameter("ImportFlag")%>"; 
    fm.Remark.value = "<%=request.getParameter("ErrorInfo")%>"; 
}                                     

function initForm() {
  try {
  	initInpBox(); 
  	if (fm.ImportFlag.value == null || fm.ImportFlag.value == "" 
  	     || fm.ImportFlag.value == "null")
  	{
  	    initImportErrorGrid();
  	}
  	else
  	{
  	    initCarImportErrorGrid();
  	} 	
  	easyQuery();
  }
  catch(re) {
    alert("ContGrpInsuredInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initImportErrorGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���������";         		//����
    iArray[1][1]="40px";            		//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������           

    iArray[2]=new Array();
    iArray[2][0]="����������";      //����
    iArray[2][1]="40px";            		//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="������Ϣ";                //����
    iArray[3][1]="280px";            	//�п�
    iArray[3][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  
     
    ImportErrorGrid = new MulLineEnter( "fm" , "ImportErrorGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    ImportErrorGrid.mulLineCount = 0;   
    ImportErrorGrid.displayTitle = 1;
    ImportErrorGrid.hiddenPlus = 1;
    ImportErrorGrid.hiddenSubtraction = 1;
    ImportErrorGrid.canSel = 0;
    ImportErrorGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

function initCarImportErrorGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�������";         		//����
    iArray[1][1]="40px";            		//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������           

    iArray[2]=new Array();
    iArray[2][0]="���ƺ�";      //����
    iArray[2][1]="40px";            		//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="������Ϣ";                //����
    iArray[3][1]="280px";            	//�п�
    iArray[3][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  
     
    ImportErrorGrid = new MulLineEnter( "fm" , "ImportErrorGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    ImportErrorGrid.mulLineCount = 0;   
    ImportErrorGrid.displayTitle = 1;
    ImportErrorGrid.hiddenPlus = 1;
    ImportErrorGrid.hiddenSubtraction = 1;
    ImportErrorGrid.canSel = 0;
    ImportErrorGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
