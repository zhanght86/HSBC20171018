<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04, 1.05, 1.06, 1.07
 * @date     : 2005-12-20, 2005-12-23, 2006-01-13, 2006-02-14. 2006-03-24, 2006-08-15, 2006-10-16, 2006-12-08
 * @direction: ��ȫ��Ŀ���ò��˷���Ϣ������ҳ��
 ******************************************************************************/
%>

    <!-- ���÷�����ϸҳ�� : ��ʼ -->


    <!-- ���˺ϼ��۵�չ�� -->
    <table>
        <tr>
            <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBackFeeTotal)"></td>
            <td class="titleImg">���˷Ѻϼ�</td>
        </tr>
    </table>
    <!-- ���˺ϼ�չ�ֱ�� -->
    <div id="divBackFeeTotal" class=maxbox1 style="display:''">
        <table class="common">
            <tr class="common">
                <td class="title">���˷ѽ��ϼ�</td>
                <td class="input"><input type="text" class="readonly wid" name="BackFeeGetMoney" id=BackFeeGetMoney readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
    </div>
    <div id="divBackFeePolDetailLayer" style="display:none">
        <!-- ���ֲ����۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBackFeePol)"></td>
                <td class="titleImg">���ֲ��˷Ѻϼ�</td>
            </tr>
        </table>
        <!-- ���ֲ���չ�ֱ�� -->
        <div id="divBackFeePol" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanBackFeePolGrid"></span></td>
                </tr>
            </table>
            <!-- ���ֲ��˽����ҳ -->
            <div id="divTurnPageBackFeePol" align="center" style="display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageBackFeePol.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageBackFeePol.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageBackFeePol.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageBackFeePol.lastPage()">
            </div>
        </div>
        <!-- ������ϸ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBackFeeDetail)"></td>
                <td class="titleImg">���˷ѷ�����ϸ</td>
            </tr>
        </table>
        <!-- ������ϸչ�ֱ�� -->
        <div id="divBackFeeDetail" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanBackFeeDetailGrid"></span></td>
                </tr>
            </table>
            <!-- ������ϸ�����ҳ -->
            <div id="divTurnPageBackFeeDetail" align="center" style="display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageBackFeeDetail.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageBackFeeDetail.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageBackFeeDetail.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageBackFeeDetail.lastPage()">
            </div>
        </div>
    </div>

    <!-- ��ȡ���ݵĲ���ֵ -->
    <%
        String sJvBackFeeEdorAcceptNo = request.getParameter("EdorAcceptNo");
        String sJvBackFeeEdorType = request.getParameter("EdorType");
        String sJvBackFeeContNo = request.getParameter("ContNo");
    %>

    <!-- ���ýű���ʼ��ѯ -->

    <script language="JavaScript">

        var BackFeePolGrid;                                 //ȫ�ֱ���, ���ֲ��˷ѺϼƱ��
        var BackFeeDetailGrid;                              //ȫ�ֱ���, ���˷ѷ�����ϸ���
        var turnPageBackFeePol = new turnPageClass();       //ȫ�ֱ���, ���ֲ��˷Ѻϼ�, ��ҳ������
        var turnPageBackFeeDetail = new turnPageClass();    //ȫ�ֱ���, ���˷ѷ�����ϸ, ��ҳ������

        //��ȡǰ̨��������
        var sJsBackFeeEdorAcceptNo, sJsBackFeeEdorType, sJsBackFeeContNo;
        try { sJsBackFeeEdorAcceptNo = top.opener.document.getElementsByName("EdorAcceptNo")[0].value; } catch (ex) {}
        try { sJsBackFeeEdorType = top.opener.document.getElementsByName("EdorType")[0].value; } catch (ex) {}
        try { sJsBackFeeContNo = top.opener.document.getElementsByName("ContNo")[0].value; } catch (ex) {}

        //��Ҫ�õ��Ĳ�ѯ����
        var sBackFeeEdorAcceptNo = (sJsBackFeeEdorAcceptNo != null && sJsBackFeeEdorAcceptNo.trim() != "") ? sJsBackFeeEdorAcceptNo.trim() : "<%=sJvBackFeeEdorAcceptNo%>";
        var sBackFeeEdorType = (sJsBackFeeEdorType != null && sJsBackFeeEdorType.trim() != "") ? sJsBackFeeEdorType.trim() : "<%=sJvBackFeeEdorType%>";
        var sBackFeeContNo = (sJsBackFeeContNo != null && sJsBackFeeContNo.trim() != "") ? sJsBackFeeContNo.trim() : "<%=sJvBackFeeContNo%>";

        initBackFeePolGrid();       //��ʼ�����ֲ��˷ѺϼƱ��
        initBackFeeDetailGrid();    //��ʼ�����˷ѷ�����ϸ���
        queryBackFee();             //ִ�����ֺ���ϸ���˷Ѳ�ѯ

        /**
         * ִ�����ֺ���ϸ���˷Ѳ�ѯ
         */
        function queryBackFee()
        {
            if (sBackFeeEdorAcceptNo == null || trim(sBackFeeEdorAcceptNo) == "")
            {
                alert("���棺�޷���ȡ��ȫ����š���ѯ���˷�ʧ�ܣ� ");
                return;
            }
            if (sBackFeeEdorType == null || trim(sBackFeeEdorType) == "")
            {
                alert("���棺�޷���ȡ��ȫ��Ŀ����ѯ���˷�ʧ�ܣ� ");
                return;
            }
            //�����ѯ����, ִ�в�ѯ
            queryBackFeeTotalMoney();    //���˷ѽ��ϼ�
            queryBackFeePolGrid();       //���ֲ��˷ѺϼƲ�ѯ
            queryBackFeeDetailGrid();    //���˷ѷ�����ϸ��ѯ
            queryBackFeeGrpCont();       //���ո��ò�ѯ���˷�
        }

        /**
         * ���ֲ��˷Ѻϼ� MultiLine �ĳ�ʼ��
         */
        function initBackFeePolGrid()
        {
            var iArray = new Array();                           //������, ���ظ� MultiLine ���

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 30;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 0��ʾ������; 1��ʾ����

                iArray[1] = new Array();
                iArray[1][0] = "���ֺ�";
                iArray[1][1] = "120px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "���ִ���";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "��������";
                iArray[3][1] = "350px";
                iArray[3][2] = 400;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "���˷Ѻϼ�";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                iArray[4][21] = 3;
            }
            catch (ex)
            {
                alert("�� PEdorFeeDetail.jsp --> initBackFeePolGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                BackFeePolGrid = new MulLineEnter("fm", "BackFeePolGrid");
                BackFeePolGrid.mulLineCount = 0;
                BackFeePolGrid.displayTitle = 1;
                BackFeePolGrid.locked = 1;
                BackFeePolGrid.hiddenPlus = 1;
                BackFeePolGrid.hiddenSubtraction = 1;
                BackFeePolGrid.canChk = 0;
                BackFeePolGrid.canSel = 0;
                BackFeePolGrid.chkBoxEventFuncName = "";
                BackFeePolGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                BackFeePolGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PEdorFeeDetail.jsp --> initBackFeePolGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ���˷ѷ�����ϸ MultiLine �ĳ�ʼ��
         */
        function initBackFeeDetailGrid()
        {
            var iArray = new Array();                           //������, ���ظ� MultiLine ���

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 30;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 0��ʾ������; 1��ʾ����

                iArray[1] = new Array();
                iArray[1][0] = "���ֺ�";
                iArray[1][1] = "120px";
                iArray[1][2] = 150;
                iArray[1][3] = 3;

               	if(sBackFeeEdorType=="LR")
				{
					iArray[2] = new Array();
	                iArray[2][0] = "���ִ���";
	                iArray[2][1] = "60px";
	                iArray[2][2] = 150;
	                iArray[2][3] = 3;
	
	                iArray[3] = new Array();
	                iArray[3][0] = "��������";
	                iArray[3][1] = "120px";
	                iArray[3][2] = 200;
	                iArray[3][3] = 3;
				}
				else
				{
					iArray[2] = new Array();
	                iArray[2][0] = "���ִ���";
	                iArray[2][1] = "60px";
	                iArray[2][2] = 150;
	                iArray[2][3] = 0;
	
	                iArray[3] = new Array();
	                iArray[3][0] = "��������";
	                iArray[3][1] = "120px";
	                iArray[3][2] = 200;
	                iArray[3][3] = 0;
				}

                iArray[4] = new Array();
                iArray[4][0] = "��������";
                iArray[4][1] = "110px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "���ý��";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;
            }
            catch (ex)
            {
                alert("�� PEdorFeeDetail.jsp --> initBackFeeDetailGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                BackFeeDetailGrid = new MulLineEnter("fm", "BackFeeDetailGrid");
                BackFeeDetailGrid.mulLineCount = 0;
                BackFeeDetailGrid.displayTitle = 1;
                BackFeeDetailGrid.locked = 1;
                BackFeeDetailGrid.hiddenPlus = 1;
                BackFeeDetailGrid.hiddenSubtraction = 1;
                BackFeeDetailGrid.canChk = 0;
                BackFeeDetailGrid.canSel = 0;
                BackFeeDetailGrid.chkBoxEventFuncName = "";
                BackFeeDetailGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                BackFeeDetailGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PEdorFeeDetail.jsp --> initBackFeeDetailGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ����Ƿ������帴�ø�����Ŀ
         */
        function isGrpBackFeeReuse()
        {
            var sAppObj;
            try
            {
                sAppObj = top.opener.document.getElementsByName("AppObj")[0].value;
            }
            catch (ex) {}
            if (sAppObj != null && sAppObj.trim() == "G")
            {
                return true;
            }
            return false;
        }

        /**
         * ��ѯ���˷ѽ��ϼ�
         */
        function queryBackFeeTotalMoney()
        {
            var QuerySQL, arrResult;
           /*  QuerySQL = "select nvl(sum(GetMoney), 0) "
                     +   "from LPEdorItem "
                     +  "where 1 = 1 "
                     +    "and EdorAcceptNo = '" + sBackFeeEdorAcceptNo + "' "
                     +    "and EdorType = '" + sBackFeeEdorType + "' "; */
            
            var sqlid1 = "PEdorFeeDetailSql1";
        	var mySql1 = new SqlClass();
        	mySql1.setResourceName("bqs.PEdorFeeDetailSql"); // ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(sBackFeeEdorAcceptNo);// ָ������Ĳ���
        	mySql1.addSubPara(sBackFeeEdorType);// ָ������Ĳ���
        	 QuerySQL = mySql1.getString();
            if (isGrpBackFeeReuse())
            {
                QuerySQL += "and ContNo = '" + sBackFeeContNo + "'";
               //var param1 = "and ContNo = '" + sBackFeeContNo + "'";
              // mySql1.addSubPara(param1);// ָ������Ĳ���
            }
           
            //alert(QuerySQL);
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0, "", "", 1);    //ע��˴���6������Ӧ��Ϊ1����ʹ�÷�ҳ���ܣ�������ܻ��ȫ�ֱ��� turnPage ����
            }
            catch (ex)
            {
                alert("���棺��ѯ���˷ѽ��ϼƳ����쳣�� ");
                return;
            }
            if (arrResult != null)
            {
                try
                {
                    document.getElementsByName("BackFeeGetMoney")[0].value = arrResult[0][0];
                }
                catch (ex) {}
            }
        }

        /**
         * ���ֲ��˷Ѻϼ� MultiLine �Ĳ�ѯ
         */
        function queryBackFeePolGrid()
        {
            /* var QuerySQL = "select p, "
                         +        "(select RiskCode from LCPol where PolNo = p), "
                         +        "(select RiskName "
                         +           "from LMRisk "
                         +          "where RiskCode = (select RiskCode from LCPol where PolNo = p)), "
                         +        "q "
                         +   "from (select a.PolNo as p, "
                         +                "sum(GetMoney) as q "
                         +           "from LJSGetEndorse a "
                         +          "where a.EndorsementNo in "
                         +                "(select EdorNo "
                         +                   "from LPEdorItem "
                         +                  "where 1 = 1 "
                         +                    "and EdorAcceptNo = '" + sBackFeeEdorAcceptNo + "') ";
                          */
                        var sqlid2 = "PEdorFeeDetailSql2";
                     	var mySql2 = new SqlClass();
                     	mySql2.setResourceName("bqs.PEdorFeeDetailSql"); // ָ��ʹ�õ�properties�ļ���
                     	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
                     	mySql2.addSubPara(sBackFeeEdorAcceptNo);// ָ������Ĳ���
                     	var QuerySQL = mySql2.getString(); 
            if (isGrpBackFeeReuse())
            {
                QuerySQL += "and ContNo = '" + sBackFeeContNo + "' ";
              // var params = "and ContNo = '" + sBackFeeContNo + "'";
               // mySql2.addSubPara(params);// ָ������Ĳ���
            }
           /*  QuerySQL     +=           "and a.FeeOperationType = '" + sBackFeeEdorType + "' "
                         +          "group by a.PolNo)"; */
            var param = "and a.FeeOperationType = '" + sBackFeeEdorType + "'group by a.PolNo)";
           // mySql2.addSubPara(param);// ָ������Ĳ���
              QuerySQL +=param;
            //alert(QuerySQL);
            try
            {
                turnPageBackFeePol.pageDivName = "divTurnPageBackFeePol";
                turnPageBackFeePol.queryModal(QuerySQL, BackFeePolGrid);
            }
            catch (ex)
            {
                alert("���棺��ѯ���ֲ��˷ѺϼƳ����쳣�� ");
                return;
            }
            //���˷����ֲ����ϸ�����ʾ������
            if (BackFeePolGrid.mulLineCount > 0)
            {
                try
                {
                    document.all("divBackFeePolDetailLayer").style.display = "block";
                }
                catch (ex) {}
            }
            else
            {
                try
                {
                    document.all("divBackFeePolDetailLayer").style.display = "none";
                }
                catch (ex) {}
            }
        }

        /**
         * ���˷ѷ�����ϸ MultiLine �Ĳ�ѯ
         */
        function queryBackFeeDetailGrid()
        {
          /*   var QuerySQL = "select b.p, "
                         +        "(select RiskCode from LCPol where PolNo = b.p), "
                         +        "(select RiskName "
                         +           "from LMRisk "
                         +          "where RiskCode = (select RiskCode from LCPol where PolNo = b.p)), "
                         +        "c.CodeName, "
                         +        "(select CodeName "
                         +           "from LDCode "
                         +          "where CodeType = 'finfeetype' and Code = b.r), "
                         +        "b.s "
                         +   "from (select a.PolNo as p, "
                         +                "substr(a.SubFeeOperationType, 1, 4) as q, "
                         +                "a.FeeFinaType as r, "
                         +                "sum(GetMoney) as s "
                         +           "from LJSGetEndorse a "
                         +          "where 1 = 1 "
                         +            "and a.EndorsementNo in "
                         +                "(select EdorNo "
                         +                   "from LPEdorItem "
                         +                  "where 1 = 1 "
                         +                    "and EdorAcceptNo = '" + sBackFeeEdorAcceptNo + "') "; */
                         
                         
                        var sqlid3 = "PEdorFeeDetailSql3";
                      	var mySql3 = new SqlClass();
                      	mySql3.setResourceName("bqs.PEdorFeeDetailSql"); // ָ��ʹ�õ�properties�ļ���
                      	mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
                      	mySql3.addSubPara(sBackFeeEdorAcceptNo);// ָ������Ĳ���
                      	 var QuerySQL = mySql3.getString();
                         
            if (isGrpBackFeeReuse())
            {
            	
            	//var param2 = "and ContNo = '" + sBackFeeContNo + "' ";
                QuerySQL += "and ContNo = '" + sBackFeeContNo + "' ";
                
               // mySql3.addSubPara(param2);// ָ������Ĳ���
            }
           /*  QuerySQL     +=           "and a.FeeOperationType = '" + sBackFeeEdorType + "' "
                         +          "group by a.PolNo, "
                         +                   "substr(a.SubFeeOperationType, 1, 4), "
                         +                   "a.FeeFinaType) b, "
                         +        "LDCode c "
                         +  "where 1 = 1 "
                         +    "and c.CodeType = 'BQSubFeeType' "
                         +    "and trim(c.Code) = b.q"; */
          var param3 = "and a.FeeOperationType = '" + sBackFeeEdorType + "' group by a.PolNo,substr(a.SubFeeOperationType, 1, 4),a.FeeFinaType) b,LDCode c  where 1 = 1 and c.CodeType = 'BQSubFeeType' and trim(c.Code) = b.q";
         // mySql3.addSubPara(sBackFeeEdorType);// ָ������Ĳ���
            //alert(QuerySQL);
         QuerySQL +=param3;
            try
            {
                turnPageBackFeeDetail.pageDivName = "divTurnPageBackFeeDetail";
                turnPageBackFeeDetail.queryModal(QuerySQL, BackFeeDetailGrid);
            }
            catch (ex)
            {
                alert("���棺��ѯ���˷ѷ�����ϸ�����쳣�� ");
                return;
            }
        }

        /**
         * ��������ո��û�Ҫ��ѯ�����ܲ��˷�
         */
        function queryBackFeeGrpCont()
        {
            if (isGrpBackFeeReuse())
            {
                try
                {
                    top.opener.queryBackFee();
                }
                catch (ex) {}
            }
        }

    </script>

    <!-- ���÷�����ϸҳ�� : ���� -->

