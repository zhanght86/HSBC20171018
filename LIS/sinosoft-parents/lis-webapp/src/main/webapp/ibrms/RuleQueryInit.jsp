<script language="JavaScript">

	function initInputBox(flag) {
	 switch (flag)
	 {
	    case 2 : {InitCopy();break;} //����
	    case 3 : {InitTest();break;} //����
	    case 4 : {InitModify();break;} //�޸�
	    case 5 : {InitApprove();break;} //����
	    case 6 : {InitDeploy();break;} //����
	    case 7 : {InitDrop();break;} //����
	    case 8 : {InitSelect();break;} //��ѯ
	    case 9 : {InitPrint();break;} //��ӡ
	             
	 } 	
	}

	function initForm(flag) {
		try {
			initInputBox(flag);
			initQueryGrpGrid();	
		} catch (re) {
			alert("RuleQueryInit.jsp InitForm�����з����쳣:��ʼ���������!");
		}
	}

    function InitCopy(){
        var CopyDiv=document.getElementById("CopyDiv");
        CopyDiv.style.display='';
        //initIbrmsState(1);
    }
    
    function InitTest(){
        var TestDiv=document.getElementById("TestDiv");
        TestDiv.style.display='';
        initIbrmsState(1);
    }
    
    function InitModify(){
    	var ModifyDiv=document.getElementById("ModifyDiv");
    	ModifyDiv.style.display='';
    	initIbrmsState(2);
    }

    function InitApprove(){

       var ApproveDiv=document.getElementById("ApproveDiv");
       ApproveDiv.style.display='';
       initIbrmsState(3);
       
	}

    function InitDeploy(){
       var DeployDiv=document.getElementById("DeployDiv");
       DeployDiv.style.display='';
       initIbrmsState(4);     
    }
    
    function InitDrop(){
    
     var DropDiv=document.getElementById("DropDiv");
       DropDiv.style.display='';
       initIbrmsState(7);
    }

    function InitSelect(){
    
      var DivSelect=document.getElementById("DivSelect");
       DivSelect.style.display='';
       
     //   var stateSQL="select code,codename from ldcode where 1=1 and codetype='ibrmsstate'";

	  //  var str=easyQueryVer3(stateSQL);
	  //  var stateArray=decodeEasyQueryResult(str);
	  //  try{
		//    fm.IbrmsState.value=stateArray[0][0];
		   // fm.IbrmsState.readOnly= false ;
		//    fm.ibrmsstateName.value=stateArray[0][1];
		   // fm.ibrmsstateName.readOnly= false;
	  //     }catch(e)
	   //     {
	   //     	alert("״̬��Ϣ��ʼ������");
	   //         }
    
    }	
	
    function InitPrint(){
    	var PrintDiv=document.getElementById("PrintDiv");
    	PrintDiv.style.display='';
    }	
    	
	function initQueryGrpGrid() {

		var iArray = new Array();
        var i11Array="0^3|����ͨ��^4|����δͨ��";
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "��������"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			//iArray[1][4] = "ibrmsresulttype";

			iArray[2] = new Array();
			iArray[2][0] = "ҵ��ģ��"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "���򼶱�"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "��Ч����"; //����
			iArray[4][1] = "80px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 8; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "ʧЧ����"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 8; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "������"; //����
			iArray[6][1] = "80px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[6][4] = "IbrmsResulttype";

			iArray[7] = new Array();
			iArray[7][0] = "״̬"; //����
			iArray[7][1] = "100px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[7][4] = "ibrmsstate";
			
			iArray[8] = new Array();
			iArray[8][0] = "��������"; //����
			iArray[8][1] = "180px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[8][4] = "ibrmsvalid";
		
		    iArray[9] = new Array();
			iArray[9][0] = "id"; //����
			iArray[9][1] = "50px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[9][4] = "IbrmsValid";

			iArray[10] = new Array();
			iArray[10][0] = "��Դ����"; //����
			iArray[10][1] = "100px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
		  iArray[11]=new Array();
			iArray[11][0]="�������"; 
			iArray[11][1]="100px";
			iArray[11][2]=10;
			iArray[11][3]=3;
			iArray[11][10]='PlanState';  //���ô��룺"PlanState"Ϊ�������ݵ����� 
			iArray[11][11]=i11Array; //i11Array �Ǵ���Ҫ������ʾ�Ĵ���
			iArray[11][12]="10";			
			iArray[11][13]="0";
			iArray[11][19]= 3; 

		 iArray[12]=new Array();
			iArray[12][0]="��������"; 
			iArray[12][1]="100px";
			iArray[12][2]=10;
			iArray[12][3]=3;
		
			
			
     QueryGrpGrid = new MulLineEnter( "document" , "QueryGrpGrid" );
     //��Щ���Ա�����loadMulLineǰ
      QueryGrpGrid.mulLineCount = 5;
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

	function initIbrmsState(flag){
		//var stateSQL = "select code,codename from ldcode where code="+flag+" and codetype='ibrmsstate'";
	  var sqlid8="RuleQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(flag);//ָ������Ĳ���
		
		
	  var stateSQL =mySql8.getString();	
		var str = easyQueryVer3(stateSQL);
		var stateArray = decodeEasyQueryResult(str);
		try {
			fm.IbrmsState.value = stateArray[0][0];
			fm.IbrmsState.readOnly = "true";
            //����������
			fm.IbrmsState.ondblclick = " ";
			fm.ibrmsstateName.value = stateArray[0][1];
			fm.ibrmsstateName.readOnly = "true";
		} catch (e) {
			alert("״̬��Ϣ��ʼ������");
		}
	}
</script>
