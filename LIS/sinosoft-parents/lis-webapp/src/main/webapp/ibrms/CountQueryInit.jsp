<script language="JavaScript">

	function initInputBox(flag) {
	 switch (flag)
	 { 
	    case 2 : {InitCopy();break;} //����
	    case 3 : {InitAbilityDetail();break;} //����
	    case 4 : {InitModify();break;} //�޸�
	    case 5 : {InitApprove();break;} //����
	    case 6 : {InitDeploy();break;} //����
	    case 7 : {InitDrop();break;} //����
	    case 8 : {InitSelect();break;} //��ѯ
	    case 9 : {InitPrint();break;} //��ӡ
	             
	 } 	
	}
//Υ��������ϸҳ���ʼ����Ϣ
	function InitBreakDetail(){

      document.all('ManageCom').value = ManageCom;
      document.all('Business').value = Business;
      document.all('RuleStartDate').value = RuleStartDate;
      document.all('RuleEndDate').value = RuleEndDate;
      document.all('templateid').value = templateid;
      

      //var stateSQL="select name from ldcom where comcode = " +ManageCom;
	    //var str=easyQueryVer3(stateSQL);
	   // var stateArray=decodeEasyQueryResult(str);
	    showOneCodeName('comcode','ManageCom','ManageComName');
	    showOneCodeName('ibrmsbusiness','Business','ibrmsbusinessName');
	    /*
	    try{
		    fm.ManageComName.value=stateArray[0][0];
		    fm.ManageComName.readOnly="true";
	        }catch(e)
	        {
	        	alert("������Ϣ��ʼ������");
	            }
      */
     /*  var stateSQL1="select CodeName from ldcode  where 1 = 1 and codetype = 'ibrmsbusiness' and code = "+Business+ " order by Code ";
	    var str=easyQueryVer3(stateSQL1);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.ibrmsbusinessName.value=stateArray[0][0];
		    fm.ibrmsbusinessName.readOnly="true";
	        }catch(e)
	        {
	        	alert("ҵ���ʼ������");
	            }
	            */
}
	
	
	
function InitAbilityDetail(){

      document.all('ManageCom').value = ManageCom;
      document.all('Business').value = Business;
      document.all('RuleStartDate').value = RuleStartDate;
      document.all('RuleEndDate').value = RuleEndDate;
   
      
/*
        var stateSQL="select name from ldcom where comcode = " +ManageCom;
	    var str=easyQueryVer3(stateSQL);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.ManageComName.value=stateArray[0][0];
		    fm.ManageComName.readOnly="true";
	        }catch(e)
	        {
	        	alert("������Ϣ��ʼ������");
	            }
      
        var stateSQL1="select CodeName from ldcode  where 1 = 1 and codetype = 'ibrmsbusiness' and code = "+Business+ " order by Code ";
	    var str=easyQueryVer3(stateSQL1);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.ibrmsbusinessName.value=stateArray[0][0];
		    fm.ibrmsbusinessName.readOnly="true";
	        }catch(e)
	        {
	        	alert("ҵ���ʼ������");
	            }
	            */
	             showOneCodeName('comcode','ManageCom','ManageComName');
	    showOneCodeName('ibrmsbusiness','Business','ibrmsbusinessName');
}


	function initForm(flag) {
		try {
			//alert('1');
			try{
			initInputBox(flag);
			}
			catch(e)
			{
			}
			//alert('2');
			if(flag=='2')
			{
			
			//alert('3');
			try{
			initBreakCountGrid();
			}catch(e)
			{
			}
			}
			if(flag=='21')
			{
				try{
			initBreakDetailCountGrid();
			}
			catch(e)
			{
			}
			}
			
			//alert('4');
			if(flag=='3')
			{
			try{
			initAbilityCountGrid();	
		}catch(e)
			{
			}
			//alert('5');
		/*	try{
			initAbilityDetailGrid();
			}catch(e)
			{
			}
			*/
		}
			//alert('6');
			if(flag=='1')
			{
			try{
			initQueryGrpGrid();		
			}catch(e)
			{
			}
		}
		} catch (re) {
			alert("RuleQueryInit.jsp InitForm�����з����쳣:��ʼ���������!");
		}
	}

  

    //ģ��ͳ�Ʋ�ѯ	
	function initQueryGrpGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "ģ���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			//iArray[1][4] = "ibrmsresulttype";

			iArray[2] = new Array();
			iArray[2][0] = "�汾����"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "ģ����"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "ģ���߼�"; //����
			iArray[4][1] = "80px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "ҵ��ģ��"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "����"; //����
			iArray[6][1] = "30px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[6][4] = "IbrmsResulttype";

			iArray[7] = new Array();
			iArray[7][0] = "״̬"; //����
			iArray[7][1] = "50px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[7][4] = "ibrmsstate";
			
			iArray[8] = new Array();
			iArray[8][0] = "��Ч����"; //����
			iArray[8][1] = "80px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 8; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[8][4] = "IbrmsValid";
		
		    iArray[9] = new Array();
			iArray[9][0] = "ʧЧ����"; //����
			iArray[9][1] = "80px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 8; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[9][4] = "IbrmsValid";
			
		    iArray[10]=new Array();
			iArray[10][0]="������"; 
			iArray[10][1]="30px";
			iArray[10][2]=10;
			iArray[10][3]=0;
			
		    iArray[11]=new Array();
			iArray[11][0]="�޸���"; 
			iArray[11][1]="30px";
			iArray[11][2]=100;
			iArray[11][3]=0;

			iArray[12]=new Array();
			iArray[12][0]="������"; 
			iArray[12][1]="30px";
			iArray[12][2]=100;
			iArray[12][3]=0;

			iArray[13]=new Array();
			iArray[13][0]="������"; 
			iArray[13][1]="30px";
			iArray[13][2]=100;
			iArray[13][3]=0;

			iArray[14]=new Array();
			iArray[14][0]="��������"; 
			iArray[14][1]="80px";
			iArray[14][2]=100;
			iArray[14][3]=8;

			iArray[15]=new Array();
			iArray[15][0]="��������"; 
			iArray[15][1]="80px";
			iArray[15][2]=100;
			iArray[15][3]=8;

			iArray[16]=new Array();
			iArray[16][0]="��������"; 
			iArray[16][1]="80px";
			iArray[16][2]=100;
			iArray[16][3]=8;

			iArray[17]=new Array();
			iArray[17][0]="��Ч��־"; 
			iArray[17][1]="110px";
			iArray[17][2]=100;
			iArray[17][3]=0;

			iArray[18]=new Array();
			iArray[18][0]="״̬"; 
			iArray[18][1]="110px";
			iArray[18][2]=100;
			iArray[18][3]=0;

			iArray[19]=new Array();
			iArray[19][0]="��������"; 
			iArray[19][1]="110px";
			iArray[19][2]=100;
			iArray[19][3]=0;
			
		  iArray[20]=new Array();
			iArray[20][0]="����"; 
			iArray[20][1]="110px";
			iArray[20][2]=100;
			iArray[20][3]=3;
			
      QueryGrpGrid = new MulLineEnter( "fm" , "QueryGrpGrid" );
     //��Щ���Ա�����loadMulLineǰ
      QueryGrpGrid.mulLineCount = 1;
      QueryGrpGrid.displayTitle = 1;
      QueryGrpGrid.hiddenPlus = 1;
      QueryGrpGrid.hiddenSubtraction = 1;
      QueryGrpGrid.canSel= 1;
      QueryGrpGrid.canChk =0;
      QueryGrpGrid.loadMulLine(iArray);
			
			
		} catch (ex) {
			alert(ex);
		}
	}
	//Υ��������ѯ
	function initBreakCountGrid() {

		var iArray = new Array();
		try {  
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "60px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "����"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "180px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "ҵ��ģ��"; //����
			iArray[2][1] = "180px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "ģ����"; //����
			iArray[3][1] = "180px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "��������"; //����
			iArray[4][1] = "180px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "Υ���������"; //����
			iArray[5][1] = "180px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		
			
			
      BreakCountGrid = new MulLineEnter( "fm" , "BreakCountGrid" );
     //��Щ���Ա�����loadMulLineǰ
      BreakCountGrid.mulLineCount = 1;
      BreakCountGrid.displayTitle = 1;
      BreakCountGrid.hiddenPlus = 1;
      BreakCountGrid.hiddenSubtraction = 1;
      BreakCountGrid.canSel= 1;
      BreakCountGrid.canChk =0;
      BreakCountGrid.loadMulLine(iArray);
	
		} catch (ex) {
			alert(ex);
		}
	}
	
	//Υ��������ϸ��ѯ
	function initBreakDetailCountGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "60px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "������"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "ģ���"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "��������"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "�汾��"; //����
			iArray[4][1] = "80px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "ִ�н��"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
			iArray[6] = new Array();
			iArray[6][0] = "ִ������"; //����
			iArray[6][1] = "80px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��7��ʾ���������ص�
		//	iArray[3][7] = "ibrmstemplatelevel";

			iArray[7] = new Array();
			iArray[7][0] = "ִ��ʱ��"; //����
			iArray[7][1] = "80px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[8] = new Array();
			iArray[8][0] = "ִ�е�˳���"; //����
			iArray[8][1] = "80px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
      BreakDetailCountGrid = new MulLineEnter( "fm" , "BreakDetailCountGrid" );
     //��Щ���Ա�����loadMulLineǰ
      BreakDetailCountGrid.mulLineCount = 1;
      BreakDetailCountGrid.displayTitle = 1;
      BreakDetailCountGrid.hiddenPlus = 1;
      BreakDetailCountGrid.hiddenSubtraction = 1;
      BreakDetailCountGrid.canSel= 0;
      BreakDetailCountGrid.canChk =0;
      BreakDetailCountGrid.loadMulLine(iArray);
 
	
		} catch (ex) {
			alert(ex);
		}
	}
	
	function initAbilityCountGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "60px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "����"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "180px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "ҵ��ģ��"; //����
			iArray[2][1] = "180px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "У�鱣������"; //����
			iArray[3][1] = "180px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "ƽ������ʱ��(��λ:s)"; //����
			iArray[4][1] = "180px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "id"; //����
			iArray[5][1] = "180px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 3;
			
      AbilityCountGrid = new MulLineEnter( "fm" , "AbilityCountGrid" );
     //��Щ���Ա�����loadMulLineǰ
      AbilityCountGrid.mulLineCount = 1;
      AbilityCountGrid.displayTitle = 1;
      AbilityCountGrid.hiddenPlus = 1;
      AbilityCountGrid.hiddenSubtraction = 1;
      AbilityCountGrid.canSel= 1;
      AbilityCountGrid.canChk =0;
      AbilityCountGrid.loadMulLine(iArray);
			
			
		} catch (ex) {
			alert(ex);
		}
	}
	
	//������ϸ��ѯ
	function initAbilityDetailGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "60px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "����"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "180px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "ҵ��ģ��"; //����
			iArray[2][1] = "180px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "У�鱣����"; //����
			iArray[3][1] = "180px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "����ʱ��(��λ:ms)"; //����
			iArray[4][1] = "180px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "������"; //����
			iArray[5][1] = "180px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 3;
			iArray[5][4] = "ibrmsflag";
			
			iArray[6] = new Array();
			iArray[6][0] = "��������"; //����
			iArray[6][1] = "180px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0;
      AbilityDetailGrid = new MulLineEnter( "fm" , "AbilityDetailGrid" );
     //��Щ���Ա�����loadMulLineǰ
      AbilityDetailGrid.mulLineCount = 1;
      AbilityDetailGrid.displayTitle = 1;
      AbilityDetailGrid.hiddenPlus = 1;
      AbilityDetailGrid.hiddenSubtraction = 1;
      AbilityDetailGrid.canSel= 0;
      AbilityDetailGrid.canChk =0;
      AbilityDetailGrid.loadMulLine(iArray);
			
			
		} catch (ex) {
			alert(ex);
		}
	}
		
	
</script>
