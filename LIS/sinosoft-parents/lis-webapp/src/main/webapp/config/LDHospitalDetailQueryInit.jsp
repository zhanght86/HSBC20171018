<%
//�������ƣ�LDUWUserQueryInit.jsp
//�����ܣ���������
//�������ڣ�2005-01-24 18:15:01
//������  ��ctrHTML
//������  ��  
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>                  
<script language="JavaScript">
function initInpBox() { 
  try {     
    document.all('UserCode').value = "";
   // document.all('UWType').value = "";
    // document.all('UpUserCode').value = "";
     document.all('UpUWPopedom').value = "";
    //document.all('OtherUserCode').value = "";
    //document.all('OtherUpUWPopedom').value = "";
    //document.all('UWBranchCode').value = "";
    document.all('UWPopedom').value = "";
    document.all('AddPoint').value = "";
  
    //document.all('UserState').value = "";
    document.all('edpopedom').value = "";
    //document.all('ValidStartDate').value = "";
    //document.all('ValidEndDate').value = "";
     
    document.all('ClaimPopedom').value = "";
    
    //document.all('UpUserCode').value = "";
   
    //document.all('Remark').value = "";
    document.all('Operator').value = "";
    document.all('ManageCom').value = "";
    document.all('MakeDate').value = "";
    
    document.all('MakeTime').value = "";
    document.all('ModifyDate').value = "";
    document.all('ModifyTime').value = "";
  }
  catch(ex) {
    alert("��LDUWUserQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                    
function initForm() {
  try {
   // initInpBox();
    initLDHospitalGrid();  
  }
  catch(re) {
    alert("LDUWUserQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initLDHospitalGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         		//����
    iArray[0][1]="30px";         		//����
    iArray[0][3]=0;         		//����
    iArray[0][4]="station";         		//����
    
    iArray[1]=new Array();
		iArray[1][0]="���ҽԺ����";  
		iArray[1][1]="80px";  
		iArray[1][3]=0;
	
		iArray[2]=new Array();
		iArray[2][0]="���ҽԺ����";  
		iArray[2][1]="80px";  
		iArray[2][3]=0;       

		iArray[3]=new Array();
		iArray[3][0]="���ҽԺ����";  
		iArray[3][1]="80px";  
		iArray[3][3]=0;       
 
  	iArray[4]=new Array();
		iArray[4][0]="��������";  
		iArray[4][1]="80px";  
		iArray[4][3]=0;       

  	iArray[5]=new Array();
		iArray[5][0]="Э����ǩ������";  
		iArray[5][1]="80px";  
		iArray[5][3]=0;       
     
  	iArray[6]=new Array();
		iArray[6][0]="��ϸ��ַ";  
		iArray[6][1]="80px";  
		iArray[6][3]=0;       

 
       

    
    LDHospitalGrid = new MulLineEnter( "document" , "LDHospitalGrid" ); 
    //��Щ���Ա�����loadMulLineǰ

    LDHospitalGrid.mulLineCount = 5;   
    LDHospitalGrid.displayTitle = 1;
    LDHospitalGrid.hiddenPlus = 1;
    LDHospitalGrid.hiddenSubtraction = 1;
    LDHospitalGrid.canSel = 1;
    LDHospitalGrid.canChk = 0;
    //LDUWUserGrid.selBoxEventFuncName = "showOne";

		LDHospitalGrid.loadMulLine(iArray);  
    //��Щ����������loadMulLine����
    //LDUWUserGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
