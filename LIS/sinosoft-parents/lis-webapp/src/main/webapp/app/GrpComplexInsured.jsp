<script>
    try{
        tInsLoadFlag  = LoadFlag;
        tEdorAcceptNo = EdorAcceptNo;
        tBQFlag       =  BQFlag;
    }
    catch(ex){}
</script>
    <DIV id=DivLCInsuredButton STYLE="display:''">
        <!-- 被保人信息部分 -->
        <table>
            <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);"></td>
                <td class= titleImg><Input class= mulinetitle value="第一被保险人资料"  name=InsuredSequencename id=InsuredSequencename readonly >（客户号：<Input class= common name=InsuredNo id=InsuredNo ><INPUT id="butBack" VALUE="查  询" class=cssButton TYPE=button onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
                    <Div  id= "divSamePerson" style="display:none">
                        <font color=red>如投保人为被保险人本人，可免填本栏，请选择
                            <INPUT TYPE="checkbox" NAME="SamePersonFlag" onclick="isSamePerson();">
                        </font>
                    </div>
                </td>
            </tr>
        </table>
    
    </DIV>
    <DIV id=DivRelation class=maxbox1 STYLE="display:''">
        <table  class= common>  
            <TR  class= common>  
    	        <TD  class= title>客户内部号码</TD>             
                <TD  class= input><input class="codeno" name="SequenceNoCode" id=SequenceNoCode  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" id=SequenceNoName readonly="true"></TD>              
    	        <TD  class= title>与第一被保险人关系</TD>             
                <TD  class= input><Input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured  elementtype=nacessary verify="主被保险人关系|code:Relation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" id=RelationToMainInsuredName readonly="readonly"  ></TD>  
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>                     
            </TR> 
        </table> 
    </DIV>
    <div id="divContType" style = "display:''">  
        <table  class= common>
            <TR  class= common>   
                <TD CLASS=title>保单类型标记</td>
                <TD  class="input"><Input NAME=PolTypeFlag id=PolTypeFlag VALUE="0" CLASS=codeno style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="保单类型标记" ><input name="PolTypeFlagName" id=PolTypeFlagName class="codename" readonly="readonly"></TD>
				<td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>   
				<td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>   
            </TR> 
         </table> 
    </div>        
    <DIV id=DivLCInsured STYLE="display:''">
        <DIV id=DivLCBasicInfo STYLE="display:''">   
            <table  class= common>
                <TR  class= common>        
                    <TD  class= title>姓名</TD>
                    <TD  class="input"><Input class="common wid" id=Name name=Name onblur="if(tInsLoadFlag=='2')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="被保险人姓名|notnull&len<=20" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();"></TD>
                    <TD  class="title">性别</TD>
                    <TD  class="input"><Input class="codeno" id=Sex name=Sex verify="被保险人性别|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName id=SexName readonly="readonly" elementtype=nacessary></TD>
                    <TD  class= title>证件类型</TD>
                    <TD  class= input><Input class="codeno" name="IDType" id=IDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" value="0"  ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName id=IDTypeName readonly=true></TD>
                </TR>
                <TR  class= common>
                    <TD  class= title>证件号码</TD>
                    <TD  class= input><!--<Input class=common name="IDNo"   aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getallinfo();"></TD>-->
                    	 <Input class=common name="IDNo"  aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="if (checkidtype() == true){if(tInsLoadFlag=='2')confirmSecondInput(this,'onblur');} else {return;}" verify="被保险人证件号码|NOTNULL&len<=20"  elementtype=nacessary >
                    <TD  class= title>职业代码</TD>
                    <TD  class= input><Input class="codeno" name="OccupationCode" id=OccupationCode verify="职业代码|CODE:OccupationCode"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" >
					<input class=codename name=AppntOccupationCodeName id=AppntOccupationCodeName  onblur="getdetailwork();"></TD>
                </TR>
            </Table>
        </Div>	
    
        <DIV id=DivGrpNoname STYLE="display:''">        
            <table  class="common">        
                <TR class="common">
                    <TD  class= title><DIV id=DivGrpNoname1 STYLE="display:''">职业类别</div></TD>
                    <TD  class= input id="OccupationTypeID"><DIV id=DivGrpNoname2 STYLE="display:''"><Input class="code" name="OccupationType" id=OccupationType  verify="被保险人职业类别|NOTNUlL&code:OccupationType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="OccupationTypeDBLClick();" onkeyup="return OccupationTypeKeyUP();" elementtype=nacessary></div></TD>      
                    <TD CLASS=title ><DIV id=DivGrpNoname3 STYLE="display:''">被保人人数</div></TD>
                    <TD CLASS=input><DIV id=DivGrpNoname4 STYLE="display:''"><Input NAME=InsuredPeoples VALUE="" readonly=true CLASS="wid common" verify="被保人人数|INT&value>=0" ></div></TD>
                    <TD CLASS=title><DIV id=DivGrpNoname5 STYLE="display:''">工作证号</div></TD>
                    <TD CLASS=input><DIV id=DivGrpNoname6 STYLE="display:''"><Input NAME=WorkNo VALUE="" CLASS="wid common" ></div></TD>
                    </div> 
        	    </TR>  
            </table>
        </Div>
        <DIV id=DivAddress STYLE="display:''">   
            <table  class= common>   	
                <TR  class= common>
                    <TD  class= title>出生日期</TD>
                    <TD  class= input id="BirthdayID"><input class="common wid" dateFormat="short" onblur=" checkinsuredbirthday(); getAge();" name="Birthday" id=Birthday verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' elementtype=nacessary></TD>
                    <TD CLASS=title>被保人年龄</TD>
                    <TD CLASS=input ><Input NAME=InsuredAppAge id=InsuredAppAge VALUE="" readonly=true CLASS="wid common" verify="被保人年龄|num" ></TD>
                    <TD class=title>社会保障号</TD>
        			<TD class=input><Input name="SocialInsuNo" id=SocialInsuNo class="wid common"></TD>
                  </TR>  
                
                <TR class=common>
        			<TD class=title>入司时间</TD>
        			<TD class=input><Input name="JoinCompanyDate" class="coolDatePicker" dateFormat="short" onClick="laydate({elem: '#JoinCompanyDate'});" id="JoinCompanyDate"><span class="icon"><a onClick="laydate({elem: '#JoinCompanyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        			<TD class=title>工资</TD>
        			<TD class=input><Input name="Salary" id=Salary class="wid common"></TD>
        			<DIV id="divExecuteCom" style="display:none">
            			<TD  class= title>处理机构</TD>
                        <TD  class= input><Input class=codeno name=ExecuteCom id=ExecuteCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true></TD>
                    </DIV>
                </TR>
            </TABLE>
        </Div>    
    </DIV>
    <DIV id="divListState" style="display:none">
        <table class= common>
            <TR class= common>
    			<TD  class= title>是否激活</TD>
                <TD  class= input><Input class=codeno name=ListState id=ListState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  CodeData="0|^0|否^1|是" ondblclick="showCodeListEx('ListState',[this,ListStateName],[0,1]);" onkeyup="showCodeListKeyEx('ListState',[this,ListStateName],[0,1]);"><input class=codename name=ListStateName id=ListStateName readonly=true elementtype=nacessary></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </TR>
        </table>
    </DIV>
    <DIV id="divContPlan" style="display:''" >
        <TABLE class= common>
            <TR class= common>
	            <TD  class= title>保险计划</TD>
                <TD  class= input><Input class="code" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);"></TD>
                <TD  class= title>份数</TD>
                <TD  class= input><Input class="wid common" name="Mult" value="1" verify="份数|INT&value>=1"></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </TR>
        </TABLE>
    </DIV>
    <DIV id=DivClaim STYLE="display:none"> 
        <table  class= common> 
            <TR  class= common>
                <TD  class= title>联系地址</TD>
                <TD  class= input  colspan="3"><Input class= common3 name="PostalAddress" id=PostalAddress  verify="被保险人联系地址|len<=80" ></TD>
                <TD  class= title>邮政编码</TD>
                <TD  class= input><Input class="wid common" name="ZipCode" id=ZipCode  MAXLENGTH="6" verify="被保险人邮政编码|zipcode" ></TD>
            </TR>  
            <TR  class= common>  
                <TD  class= title>地址代码</TD>                  
                <TD  class= input><Input class="codeno" name="InsuredAddressNo" id=InsuredAddressNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress();"><input class=codename id=InsuredAddressNoName name=InsuredAddressNoName readonly=true></TD>
                <TD  class= title>理赔金帐户</TD>                  
                <TD  class= input><Input class="code" name="AccountNo" id=AccountNo  CodeData="0|^1|缴费帐户^2|其它" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();"></TD>
                <TD  class= title>移动电话</TD>
                <TD  class= input><Input class="wid common" id=Mobile name="Mobile" verify="被保险人移动电话|len<=15" ></TD>
                <TD  class= title>办公电话</TD>
                <TD  class= input><Input class="wid common" id=GrpPhone name="GrpPhone"  ></TD>          
            </TR>         
            <TR  class= common>  
            	<TD class=title>住宅电话</TD>
            	<TD class=input><Input name="HomePhone" id=HomePhone class="wid common"></TD>			
                <TD  class= title>电子邮箱</TD>
                <TD  class= input><Input class="wid common" id=EMail name="EMail" verify="被保险人电子邮箱|len<=20&Email" ></TD>
            </TR> 
            <TR CLASS=common>
        	    <TD CLASS=title>开户银行</TD>
        	    <TD CLASS=input COLSPAN=1 ><Input NAME=BankCode id=BankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" ></TD>
        	    <TD CLASS=title >户名</TD>
        	    <TD CLASS=input COLSPAN=1  ><Input NAME=AccName id=AccName VALUE="" CLASS="wid common" MAXLENGTH=20 verify="户名|len<=20" ></TD>
                <TD CLASS=title width="109" >账号</TD>
        	    <TD CLASS=input COLSPAN=1  ><Input NAME=BankAccNo id=BankAccNo class="code" VALUE="" CLASS="wid common" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|len<=40" onfocus="getdetail();"></TD>
    	    </TR>   
        </Table>          
    </DIV>
