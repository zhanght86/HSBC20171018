
<%
//�������ƣ�CtrlClaimInit.jsp
//�����ܣ�
//�������ڣ�2005-11-17
//������  ��Sujie
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  	try
  	{

		    if(GrpContNo == "null"){
		    document.all('GrpContNo').value = "";
		    }else{
		    document.all('GrpContNo').value = GrpContNo ;
		    }
		    fm.DutyCode.value="";				
				document.all('OccupationType').value = "";
				document.all('OccupationTypeName').value = "";
		    //document.all('CtrlProp').value = "";
		    
				document.all('ContPlanCode').value = "";
		    document.all('RiskCode').value = "";
		    
		    document.all('DutyCode').value = "";
		    document.all('GetDutyCode').value = "";
		    
		    document.all('AddressNoName').value = "";
		    document.all('DutyCodeName').value = "";
		    document.all('GetDutyCodeName').value = "";
		    document.all('CtrlPropName').value = "";
				
				
				fm.CtrlBatchNo.value="";
				
				
				fm.ObserveDate.value="";
				fm.Exempt.value="";
				fm.ExemptDate.value="";
				fm.TotalLimit.value="";

				fm.ClaimRate.value="";

				fm.StartPay.value="";
				fm.EndPay.value="";
			if(LPFlag==1){
				divRiskPlanSave.style.display = "none";
			}
				
		 }
	  catch(ex)
	  {
		    alert("��CtrlClaimInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	  }
}

function initForm()
{
	  try
	  {
	    	initInpBox();
		    initCtrlClaimCodeGrid();
		    QueryForm();
		    //initDutyCtrllevelCodeGrid();
		    //initMultbegin();
	  }
	  catch(re)
	  {
	     	alert("CtrlClaimInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	  }
}

//������Ƴ�ʼ��
function initCtrlClaimCodeGrid() {
	var iArray = new Array();
	try {
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�ŵ���";
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="ְҵ���";
		iArray[2][1]="60px";
		iArray[2][2]=150;
		iArray[2][3]=0;
			
		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="80px";
		iArray[3][2]= 60;
		iArray[3][3]= 0;
		
		iArray[4]=new Array();
		iArray[4][0]="���ϼƻ�����";
		iArray[4][1]="80px";
		iArray[4][2]= 60;
		iArray[4][3]= 0;
		
		iArray[5]=new Array();
		iArray[5][0]="���ֱ���";
		iArray[5][1]="80px";
		iArray[5][2]= 60;
		iArray[5][3]= 0;
		
		iArray[6]=new Array();
		iArray[6][0]="�������α���";
		iArray[6][1]="80px";
		iArray[6][2]= 60;
		iArray[6][3]= 0;
		
		iArray[7]=new Array();
		iArray[7][0]="�������α���";
		iArray[7][1]="100px";
		iArray[7][2]= 60;
		iArray[7][3]= 0;
				
			
		iArray[8]=new Array();
		iArray[8][0]="���Ʋ��";
		iArray[8][1]="0px";
		iArray[8][2]= 60;
		iArray[8][3]= 0;
				
		iArray[9]=new Array();
		iArray[9][0]="�������κ�";
		iArray[9][1]="0px";
		iArray[9][2]= 60;
		iArray[9][3]= 0;
				
		iArray[10]=new Array();
		iArray[10][0]="�۲���";
		iArray[10][1]="100px";
		iArray[10][2]= 60;
		iArray[10][3]= 0;
		
		iArray[11]=new Array();
		iArray[11][0]="�����";
		iArray[11][1]="50px";
		iArray[11][2]= 60;
		iArray[11][3]= 0;
		
		iArray[12]=new Array();
		iArray[12][0]="��������";
		iArray[12][1]="100px";
		iArray[12][2]= 60;
		iArray[12][3]= 0;
		
		iArray[13]=new Array();
		iArray[13][0]="���⸶�޶�";
		iArray[13][1]="100px";
		iArray[13][2]= 60;
		iArray[13][3]= 0;
		
		iArray[14]=new Array();
		iArray[14][0]="�⸶����";
		iArray[14][1]="100px";
		iArray[14][2]= 60;
		iArray[14][3]= 0;
		
		iArray[15]=new Array();
		iArray[15][0]="����";
		iArray[15][1]="0px";
		iArray[15][2]= 60;
		iArray[15][3]= 0;
		
		iArray[16]=new Array();
		iArray[16][0]="�ⶥ��";
		iArray[16][1]="0px";
		iArray[16][2]= 60;
		iArray[16][3]= 0;
		
		iArray[17]=new Array();
		iArray[17][0]="��ע";
		iArray[17][1]="100px";
		iArray[17][2]= 60;
		iArray[17][3]= 0;
		
		CtrlClaimCodeGrid = new MulLineEnter( "document" , "CtrlClaimCodeGrid" );
		//��Щ���Ա�����loadMulLineǰ
		CtrlClaimCodeGrid.mulLineCount = 5;
		CtrlClaimCodeGrid.displayTitle = 1;
		CtrlClaimCodeGrid.hiddenPlus = 1;
		CtrlClaimCodeGrid.hiddenSubtraction = 1;
		CtrlClaimCodeGrid.canSel=1;
		CtrlClaimCodeGrid.selBoxEventFuncName = "ShowCtrlClaim";
		CtrlClaimCodeGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
