<%
//�������ƣ�GrpContInsuredCarInit.jsp
//�����ܣ�
//�������ڣ�2006-10-23 11:10:36
//������  �� chenrong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

// ���ؿ�ĳ�ʼ��
function initHiddenBox()
{
    try
    {
        fm.ManageCom.value = nullToEmpty("<%=request.getParameter("ManageCom")%>");
        fm.PrtNo.value = nullToEmpty("<%=request.getParameter("prtNo")%>");
        fm.PolNo.value = nullToEmpty("<%=request.getParameter("polNo")%>");
        fm.ScanType.value = nullToEmpty("<%=request.getParameter("scantype")%>");
        fm.MissionID.value = nullToEmpty("<%=request.getParameter("MissionID")%>");
        fm.SubMissionID.value = nullToEmpty("<%=request.getParameter("SubMissionID")%>");
        fm.ActivityID.value = nullToEmpty("<%=request.getParameter("ActivityID")%>");
        fm.NoType.value = nullToEmpty("<%=request.getParameter("NoType")%>");
        fm.GrpContNo.value = nullToEmpty("<%=request.getParameter("GrpContNo")%>");
    	fm.ScanType.value = nullToEmpty("<%=request.getParameter("scantype")%>");    	    	
    	fm.LoadFlag.value = nullToEmpty("<%=request.getParameter("LoadFlag")%>");    	
    }
    catch(ex)
    {
        alert("GrpContInsuredCarInit.jsp-->initHiddenBox�����з����쳣:��ʼ���������!");
    }
}
                             

function initForm() {
    try {
        initHiddenBox();
        try{document.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};     
        try{document.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){sdfsdfsdf};  
        initInsuredCarGrid();
        initCarPolGrid();
        if (fm.LoadFlag.value == "4" || fm.LoadFlag.value == "16")
        {
            divSaveCarButton.style.display="none"; 
            divNotSaveCarButton.style.display="";
        }
    }
    catch(re) {
    alert(re);
        alert("GrpContInsuredCarInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

// ��ȡ����Ϣ�б�ĳ�ʼ��
var InsuredCarGrid;
function initInsuredCarGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][2]=10;
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���ƺ�";         		//����
    iArray[1][1]="100px";            		//�п�
    iArray[1][2]=20;
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������           

    iArray[2]=new Array();
    iArray[2][0]="��λ��";      //����
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=10;
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="���Ӻ�";                //����
    iArray[3][1]="100px";            	//�п�
    iArray[3][2]=20;
    iArray[3][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="������������";         	//����
    iArray[4][1]="0px";            	//�п�
    iArray[4][2]=20;
    iArray[4][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="���������";         	//����
    iArray[5][1]="100px";            	//�п�
    iArray[5][2]=20;
    iArray[5][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
   
     
    InsuredCarGrid = new MulLineEnter( "fm" , "InsuredCarGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    InsuredCarGrid.mulLineCount = 0;   
    InsuredCarGrid.displayTitle = 1;
    InsuredCarGrid.hiddenPlus = 1;
    InsuredCarGrid.hiddenSubtraction = 1;
    InsuredCarGrid.canSel = 1;
    InsuredCarGrid.selBoxEventFuncName ="queryPolInfo";
    InsuredCarGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

function initCarPolGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][2]=10;
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���ƺ�";         		//����
    iArray[1][1]="80px";            		//�п�
    iArray[1][2]=20;
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������           

    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";      //����
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=10;
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��������";                //����
    iArray[3][1]="120px";            	//�п�
    iArray[3][2]=20;
    iArray[3][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������

    
    iArray[4]=new Array();
    iArray[4][0]="���ѣ�Ԫ��";         	//����
    iArray[4][1]="80px";            	//�п�
    iArray[4][2]=10;
    iArray[4][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
        
    iArray[5]=new Array;
    iArray[5][0]="����(Ԫ)";         	//����
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=20;
    iArray[5][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������

    CarPolGrid = new MulLineEnter( "fm" , "CarPolGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    CarPolGrid.mulLineCount = 0;   
    CarPolGrid.displayTitle = 1;
    CarPolGrid.hiddenPlus = 1;
    CarPolGrid.hiddenSubtraction = 1;
    CarPolGrid.canSel = 0;
    //InsuredCarGrid.selBoxEventFuncName ="getdetail";
    CarPolGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
