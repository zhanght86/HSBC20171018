<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
              

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  { 
   //var sql="select SpecContent from LCCGrpSpec where ProposalGrpContNo='"+GrpContno+"'";                                  
   //var arrQueryResult = easyExecSql(sql, 1, 0);
   //if(arrQueryResult!=null)
   //{
   //  fm.Content.value=arrQueryResult[0][0];
   //}
  }
  catch(ex)
  {
    alert("��UWManuDateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

                 

function initForm(tGrpContNo,tFlag)
{
  try
  {
	
	initInpBox();	
	initSpecInfoGrid();
	querySpec();
	
	
  }
  catch(re)
  {
    alert("GrpSpecInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initSpecInfoGrid() {
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="���"; 			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";		//�п�
    iArray[0][2]=10;			//�����ֵ
    iArray[0][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

	iArray[1]=new Array();
    iArray[1][0]="��ͬ��"; 		//����
    iArray[1][1]="120px";		//�п�
    iArray[1][2]=40;			//�����ֵ
    iArray[1][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="��Լ����"; 		//����
    iArray[2][1]="300px";		//�п�
    iArray[2][2]=40;			//�����ֵ
    iArray[2][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����Ա"; 	//����
    iArray[3][1]="80px";		//�п�
    iArray[3][2]=30;			//�����ֵ
    iArray[3][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="¼��ʱ��"; 	//����
    iArray[4][1]="80px";		//�п�
    iArray[4][2]=30;			//�����ֵ
    iArray[4][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="��ˮ��"; 	//����
    iArray[5][1]="0px";		//�п�
    iArray[5][2]=30;			//�����ֵ
    iArray[5][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    
    iArray[6]=new Array();
    iArray[6][0]="����Ͷ������"; 	//����
    iArray[6][1]="0px";		//�п�
    iArray[6][2]=30;			//�����ֵ
    iArray[6][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    SpecInfoGrid = new MulLineEnter( "fm" , "SpecInfoGrid" );
    //��Щ���Ա�����loadMulLineǰ
    SpecInfoGrid.mulLineCount = 1;
    SpecInfoGrid.displayTitle = 1;
    SpecInfoGrid.hiddenPlus = 1;
    SpecInfoGrid.canSel=1;
    SpecInfoGrid.hiddenSubtraction = 1;   
    SpecInfoGrid. selBoxEventFuncName ="getSpecContent";
    SpecInfoGrid.loadMulLine(iArray);

  } catch(ex) {
    alert("��ProposalInit.jsp-->initBnfGrid�����з����쳣:��ʼ���������!");
  }
}



</script>


