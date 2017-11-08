	 <script lanaguage="javascript">
function getInsuredOpName()
{
	fm.OccupationCodeName.value = "";
	showOneCodeNameRefresh('OccupationCode',"OccupationCode","OccupationCodeName");
	getdetailwork2();
}	
function onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName)
{
	//fm.OccupationCodeName.value = "";	
	showCodeList('occupationcode',[OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,null,null,null,'240');
	//showOneCodeNameRefresh('occupationcode',OccupationCode,OccupationCodeName);
	//getdetailwork2();
}

function onClickUpInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName)
{
	//fm.OccupationCodeName.value = "";
	showCodeListKey('occupationcode',[OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,null,null,null,'240')
	//showOneCodeNameRefresh('occupationcode',OccupationCode,OccupationCodeName);
	//getdetailwork2();
}	
</script>

<table>
	<tr>
		<td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivInsured);"></td>
		<td class="titleImg">被保人信息</td>
	</tr>
</table>

<!-- 被保人信息部分 -->
<Div  id= "DivInsured" class="maxbox1" style= "display:">
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               被保人列表MultiLine 添加被保人按钮
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    
	<div  id= "divAddDelInsuButton" style= "display:;" align=right>
      <input type="button" class="cssButton" value="添加被保险人" name="addInsured" onclick="addInsuredInput();"> 
      <input type="button" class="cssButton" value="删除被保险人" name="deleteInsured" onclick="deleteInsuredInput();"> 
    </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <!--
        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                               被保人基本信息
        vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    -->
    <%for(int i = 1 ; i<=3 ; i++) 
    { 
       %>
       <Div  id= "DivInsuredAll<%=i%>" style= "display: none">
       <%
       if(i>1){
       %><hr><%
       }%>
       
	<!-- 被保人信息部分 -->
		<Table>
			<TR>
				<TD class="common">
					<img src= "../common/images/butExpand.gif" id=img<%=i%> style= "cursor:hand;" onclick="showPage(this,DivInsured<%=i%>);">
				</TD>
				<td class="titleImg">被保人相关信息</td>
			</TR>
		</Table>
      <div class="maxbox">
		<table>
			<tr>
				<td><img src="../common/images/butExpand.gif" id=img1<%=i%> style="cursor:hand;" OnClick="showPage(this,DivInsuredBase<%=i%>);"></td>				
				<TD class="titleImg">
					<input class="mulinetitle" <% if(i>1){%> value="第<%=i%>被保险人资料" <%} else{%>value="被保险人资料" <%}%> name="InsuredSequencename<%=i%>" readonly >（客户号：<input class="common" width="225px" name="CustomerNo<%=i%>" value="">
					<input id="InsuredButBack<%=i%>" VALUE="查  询" class=cssButton type="button" onclick="queryInsuredNo(<%=i%>);"> 首次投保客户无需填写客户号）
					
					<Div  id= "divSamePerson<%=i%>" <% if(i>1){%>style="display:none" <%} %>>
						<font color="red">如投保人为被保险人本人，可免填本栏，请选择<input type="checkbox" name="SamePersonFlag<%=i%>" onclick="isSamePerson();"></font>
					</Div>
				</TD>
			</tr>
		</table>
        </div>
		<Table  class="common">  
			<TR class="common">  
				<TD  class="title"><font color="maroon" ><b>客户内部号码</b></font></TD>             
				<TD class="input">
						<!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
						<input class="codeno" name="SequenceNo<%=i%>" value=<%=i%> readonly ><Input class="codename" name="SequenceNoName<%=i%>" <%if(i==1){%>value="被保险人"<%}else if(i==2){%>value="第二被保险人"<%}else if(i==3){%>value="第三被保险人"<%}%> elementtype=nacessary readonly="true">
				</TD>
				<!--              
				<TD  class="title">与第一被保险人关系</TD>             
				<TD  class="input">
						<input class="codeno" name="RelationToMainInsured<%=i%>" verify="与第一被保险人关系|CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName<%=i%>], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName<%=i%>], [0,1]);"><Input class="codename" name="RelationToMainInsuredName<%=i%>"  elementtype=nacessary readonly="readonly"  >
				</TD>  
				 --> 
				<TD  class= title>与投保人关系</TD>
				<TD  class= input>
					<Input class="codeno" name="RelationToAppnt<%=i%>"  verify="与投保人关系|CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToAppntName<%=i%>],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName<%=i%>],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName<%=i%>" readonly="readonly" elementtype=nacessary>
				</TD> 
				<TD  class= title><input class="codeno" type=hidden name="RelationToMainInsured<%=i%>" value=""></TD>
				<TD  class= input><input class="codeno" type=hidden name="RelationToMainInsuredName<%=i%>" value=""></TD>
			</TR> 
		</Table> 
	    <Div id=DivInsured<%=i%> style="display:''">
	    <Div id=DivInsuredBase<%=i%> style="display:''">
			<Table  class= common>
				<TR class= common>        
					<TD  class= title>姓名</TD>
					<TD  class= input>
						<Input class="common wid" name=Name<%=i%> onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="被保险人姓名|LEN<=20" verifyorder='2' onblur="getallinfo();">
					</TD>
					<TD  class= title>性别</TD>
					<TD  class= input>
						<Input class="codeno" name=Sex<%=i%> style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="被保险人性别|CODE:Sex" verifyorder='2' onclick="return showCodeList('Sex',[this,SexName<%=i%>],[0,1]);" ondblclick="return showCodeList('Sex',[this,SexName<%=i%>],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName<%=i%>],[0,1]);"><input class=codename name=SexName<%=i%> readonly="readonly" elementtype=nacessary>
					</TD>
					<TD  class= title>出生日期</TD>
					<TD  class= input>
						<input class="common wid" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(<%=i%>); getAge2(<%=i%>);" name="Birthday<%=i%>" verify="被保险人出生日期|DATE" verifyorder='2' >                                                                                                                                                                                          
							</TD>
				</TR>
				<TR class= common>
					<TD CLASS=title>被保人年龄</TD>
					<TD CLASS=input COLSPAN=1>
						<Input NAME=Age<%=i%> verify="被保人年龄|value>=0" verifyorder="2" VALUE="" readonly=true CLASS="common wid" verify="被保人年龄|NUM" >
					</TD>
					<TD  class= title>证件类型</TD>
					<TD  class= input>
						<Input class="codeno" name="IDType<%=i%>" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  verify="被保险人证件类型|CODE:IDType" verifyorder='2' onclick="return showCodeList('IDType',[this,IDTypeName<%=i%>],[0,1]);" ondblclick="return showCodeList('IDType',[this,IDTypeName<%=i%>],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName<%=i%>],[0,1]);" aonblur="getallinfo();" ><input class=codename name=IDTypeName<%=i%> readonly=true elementtype=nacessary>
					</TD>
					<TD  class= title>证件号码</TD>
					<TD  class= input>
						<Input class= "common wid" name="IDNo<%=i%>" onblur="checkidtype2(<%=i%>);if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="被保险人证件号码|LEN<=20" verifyorder='2'>
					</TD>             					
				</TR>
				<TR class= common>
					<TD  class= title>有效期至</TD>
					<TD  class= input>
						<Input class="common wid" name="IDEndDate<%=i%>" >
				    </TD>
					<TD  class= title>国籍</TD>
					<TD  class= input>
						<input class="codeno" name="NativePlace<%=i%>" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="return showCodeList('NativePlace',[this,NativePlaceName<%=i%>],[0,1]);"  ondblclick="return showCodeList('NativePlace',[this,NativePlaceName<%=i%>],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName<%=i%>],[0,1]);"><input class=codename name=NativePlaceName<%=i%> readonly=true>
					</TD>
					<TD  class= title>户籍所在地</TD>
					<TD  class=input >
						<Input class="common wid" name=RgtAddress<%=i%>  >
					</TD>					  
					 </TR>
				<TR class=common>
					<TD  class= title>婚姻状况</TD>
					<TD  class= input>
						<Input class="codeno" name=Marriage<%=i%> verify="被保人婚姻状况|CODE:Marriage"  verifyorder="2" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="return showCodeList('Marriage',[this,MarriageName<%=i%>],[0,1]);" ondblclick="return showCodeList('Marriage',[this,MarriageName<%=i%>],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName<%=i%>],[0,1]);"><input class=codename name=MarriageName<%=i%> readonly=true elementtype=nacessary>
					</TD>
					<TD class= title>住址</TD>
						<TD  class= input  colspan="3"  >
							<!--
							<Input class= common3 name="PostalAddress" elementtype=nacessary verify="被保险人联系地址|NOTNULL&LEN<=80" verifyorder='2' >
						    --> 
							<Input class= common3 name="HomeAddress<%=i%>">
						</TD>						
				</TR>	 
				<TR class= common>
						<TD  class= title>邮政编码</TD>
						<TD  class= input>
							<!--
							<Input class= common name="ZIPCODE" elementtype=nacessary MAXLENGTH="6" verify="被保险人邮政编码|NOTNULL&ZIPCODE" verifyorder='2' >
						    --> 
							<Input class= "common wid" name="HomeZipCode<%=i%>" MAXLENGTH="6" >
						</TD>
						<TD  class= title>联系电话(1)</TD>
						<TD  class= input>
							<Input class= "common wid" name="Phone<%=i%>" verify="被保险人联系电话(1)|LEN<=15" verifyorder='2' >
						</TD>
						<TD  class= title>联系电话(2)</TD>
						<TD  class= input>
							<Input class= "common wid" name="Phone2<%=i%>" verify="被保险人联系电话(2)|LEN<=15" verifyorder='2' >
						</TD>												
				</TR>    
				<TR class= common>  
					<TD  class= title>电子邮箱</TD>
					<TD  class= input>
						<Input class= "common wid" name="EMail<%=i%>" MAXLENGTH=40 verify="被保险人电子邮箱|LEN<=40"  verifyorder="1" >
					</TD> 
					<TD  class= title style="display:none">传真电话</TD>
					<TD  class= input  style="display:none" colspan=3 >
						<Input class= "common wid" name="Fax" verify="被保险人传真电话|LEN<=15&PHONE" verifyorder='2' >
					</TD> 
					<TD  class= title>工作单位</TD>
					<TD  class= input ><Input class= "common wid" name="GrpName<%=i%>" verify="被保人工作单位|LEN<=60"  verifyorder="2"> </TD>										
				</TR>
				<TR class=common>
					<TD  class= title>是否拥有公费医疗、社会医疗保险</TD>
					<TD  class= input>
						<Input class="codeno" name="SocialInsuFlag<%=i%>" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="被保人是否拥有公费医疗、社会医疗保险|CODE:ibrmsflag" verifyorder='1' onclick="return showCodeList('ibrmsflag', [this,SocialInsuFlagName<%=i%>],[0,1]);" ondblclick="return showCodeList('ibrmsflag', [this,SocialInsuFlagName<%=i%>],[0,1]);" onkeyup="return showCodeListKey('ibrmsflag', [this,SocialInsuFlagName<%=i%>],[0,1]);"><Input class=codename name=SocialInsuFlagName<%=i%> readonly=true elementtype=nacessary>
					</TD>
					<TD  class= title>
				        职业
				      </TD>
				      <TD  class= input>
				        <Input class= "common wid" name="WorkType<%=i%>"  value="" >
				      </TD>
				  	<TD  class= title>
				        兼职
				      </TD>
				      <TD  class= input>
				        <Input class= "common wid" name="PluralityType<%=i%>"  value="" >
				      </TD>										
				</TR>
				<TR class=common>
					<TD  class= title>职业编码</TD>
					<TD  class=input>
						<Input class="codeno" name="OccupationCode<%=i%>" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="被保险人职业代码|CODE:OccupationCode" verifyorder='2' onclick="return onClickInsuredOccupation(OccupationCode<%=i%>,OccupationCodeName<%=i%>,OccupationType<%=i%>,OccupationTypeName<%=i%>);" ondblclick="return onClickInsuredOccupation(OccupationCode<%=i%>,OccupationCodeName<%=i%>,OccupationType<%=i%>,OccupationTypeName<%=i%>);" onkeyup="return onClickUpInsuredOccupation(OccupationCode<%=i%>,OccupationCodeName<%=i%>,OccupationType<%=i%>,OccupationTypeName<%=i%>);"><input class=codename name=OccupationCodeName<%=i%> elementtype=nacessary readonly=true>
						<!--<Input type="hidden" name="OccupationType"> -->
					</TD>
					<TD  class= title>职业类别</TD>
					<TD  class= input>
					  <Input class="codeno" name="OccupationType<%=i%>" readonly onkeyup="showCodeListKey('OccupationType',[this,OccupationTypeName<%=i%>],[0,1]);"><input class=codename name=OccupationTypeName<%=i%> readonly=true>
					</TD>
				</TR>
				<TR class= common style="display:none">					
					<TD class=title>驾照类型	</TD>
					<TD  class= input>
						<input class="codeno" name=LicenseType id="LicenseType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="被保险人驾照类型|CODE:LicenseType" verifyorder='2' onclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName readonly=true>
					</TD>
				</TR>               
			</Table>
		  </Div>
		<table>		
		  <tr>
		     <td>&nbsp;&nbsp;</td>
		  </tr>
		  <tr>
		     <td>
				<!-- 险种信息 -->
				<table>
				    <tr>
				        <td class=common>
							    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRisk<%=i%>);">
				    	</td>
				    	<td class= titleImg>
				    			 险种信息
				    	</td>
				    </tr>
		   		</table>		   		
				<Div  id= "divRisk<%=i%>" class="maxbox1" style= "display: ; ">
				    <div  id= "divAddDelRiskButton" style= "display: ;" >
					    <table  class= common > 
					        	<tr  class= common>
					    	  		<td align="left">
								      <input type="button" class="cssButton" value="添加主险" name="addMainRisk<%=i%>" onclick="addMainRiskInput(<%=i%>);"> 
								      <input type="button" class="cssButton" value="删除主险" name="deleteMainRisk<%=i%>" onclick="deleteMainRiskInput(<%=i%>);">
								    </td> 
								</tr>
								<!-- 
								<tr  class= common>
					    	  		<td align="right">
								     &nbsp;
								    </td> 
								</tr>
								-->
						</table>
				    </div>				    
					<!-- 主险信息 -->					
		   			<Div  id= "divRisk<%=i%>1" style= "display:none">
			   			<table class= common>
						    <tr class= common>
						        <TD class=title>主险序号</TD>
								<TD  class= input>
									<input class="readonly wid" name="MainRiskNo<%=i%>1" readonly=true value="1">
								</TD>
								<TD class=title>&nbsp;</TD>
								<TD  class= input>&nbsp;
									
								</TD>
								<TD class=title>&nbsp;</TD>
								<TD  class= input>&nbsp;
									
								</TD>
						    </tr>
			   		    </table>
				    	<table  class= common>
				        	<tr  class= common>
				    	  		<td text-align: left colSpan=1>
									<span id="spanRisk<%=i%>1Grid" >
									</span> 
								</td>
							</tr>
						</table>	
				　　　</Div>					
					<!-- 第二主险信息 -->					
		   			<Div  id= "divRisk<%=i%>2" style= "display:none">
			   			<table class= common>
						    <tr class= common>
						        <TD class=title>主险序号</TD>
								<TD  class= input>
									<input class="readonly wid" name="MainRiskNo<%=i%>2" readonly=true value="2">
								</TD>
								<TD class=title>&nbsp;</TD>
								<TD  class= input>&nbsp;
									
								</TD>
								<TD class=title>&nbsp;</TD>
								<TD  class= input>&nbsp;
									
								</TD>
						    </tr>
			   		    </table>
				    	<table  class= common>
				        	<tr  class= common>
				    	  		<td text-align: left colSpan=1>
									<span id="spanRisk<%=i%>2Grid" >
									</span> 
								</td>
							</tr>
						</table>	
				　　　</Div>
					<!-- 第三主险信息 -->					
		   			<Div  id= "divRisk<%=i%>3" style= "display:none">
			   			<table class= common>
						    <tr class= common>
						        <TD class=title>主险序号</TD>
								<TD  class= input>
									<input class="readonly wid" name="MainRiskNo<%=i%>3" readonly=true value="3">
								</TD>
								<TD class=title>&nbsp;</TD>
								<TD  class= input>&nbsp;
									
								</TD>
								<TD class=title>&nbsp;</TD>
								<TD  class= input>&nbsp;
									
								</TD>
						    </tr>
			   		    </table>
				    	<table  class= common>
				        	<tr  class= common>
				    	  		<td text-align: left colSpan=1>
									<span id="spanRisk<%=i%>3Grid" >
									</span> 
								</td>
							</tr>
						</table>	
				　　　</Div>
			    </Div>
				<!-- 受益人信息部分（列表） -->
			    <table>
			    	<tr>
			        	<td class=common>
						    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf<%=i%>);">
			    		</td>
			    		<td class= titleImg>
			    			 受益人信息
			    		</td>
			    	</tr>
			    </table>
				<Div  id= "divLCBnf<%=i%>" style= "display: ">
			    	<table  class= common>
			        	<tr  class= common>
			    	  		<td text-align: left colSpan=1>
								<span id="spanBnf<%=i%>Grid" >
								</span> 
							</td>
						</tr>
					</table>
				</Div>
				<!-- 生存保险金、年金、红利处理方式信息部分 -->
				<table>
			    	<tr>
			        	<td class=common>
						    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDealType<%=i%>);">
			    		</td>
			    		<td class= titleImg>
			    			 生存保险金、年金、红利处理方式
			    		</td>
			    	</tr>
			    </table>
				<Div  id= "divType<%=i%>" style= "display: none">
					<TABLE class= common>			
					  <TR CLASS=common>
						    <TD CLASS=title>
						      领取(起领)年龄
						    </TD>
						    <TD CLASS=input >
						      <Input NAME=GetYear<%=i%> VALUE="" class="readonly wid">
						    </TD>
						    <TD CLASS=title>
						      起领期间单位 
						    </TD>
						    <TD CLASS=input >
						      <Input NAME=GetYearFlag<%=i%> style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" VALUE="" CLASS=code CodeData="0|^Y|年|^A|岁" onclick="showCodeListEx('GetYearFlag',[this],[0]);" ondblClick="showCodeListEx('GetYearFlag',[this],[0]);" onkeyup="showCodeListKeyEx('GetYearFlag',[this],[0]);"  >
						    </TD>
						    <TD CLASS=title>
						      领取期间
						    </TD>
						    <TD CLASS=input >
						      <Input NAME=GetYears<%=i%>  VALUE="" CLASS="common wid"  >
						    </TD>
					  </TR>			
					  <TR CLASS=common>   
					        <TD CLASS=title>
						      年金领取方式
						    </TD>
						    <TD CLASS=input>
						      <Input NAME=GetDutyKind<%=i%> style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" VALUE="" CLASS=code onclick="return showCodeList('dsgetdutykind',[this],[0]);"  ondblClick="return showCodeList('dsgetdutykind',[this],[0]);" onkeyup="return showCodeListKey('dsgetdutykind',[this],[0]);"   >
						    </TD>
						        <TD CLASS=title>
						      生存金领取方式 
						    </TD>
						    <TD CLASS=input >
						     <Input NAME=LiveGetMode<%=i%> VALUE="" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"   TABINDEX=-1 CLASS=code onclick="return showCodeList('LiveGetMode', [this],[0]);"   ondblclick="return showCodeList('LiveGetMode', [this],[0]);" onkeyup="return showCodeListKey('LiveGetMode', [this],[0]);" verify="生存金领取方式 |code:LiveGetMode" >
						    </TD>
						    <TD CLASS=title>
						      红利领取方式 
						    </TD>
						    <TD CLASS=input>
						      <Input NAME=BonusGetMode<%=i%> VALUE="" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"  TABINDEX=-1 CLASS=code onclick="return showCodeList('BonusGetMode',[this],[0]);"  ondblClick="return showCodeList('BonusGetMode',[this],[0]);" onkeyup="return showCodeListKey('LiveGetMode',[this],[0]);" verify="红利领取方式|code:LiveGetMode" >
						    </TD>		    
					  </TR>		  
				    </TABLE>
		     	 </Div>
		     	 <Div  id= "divLCDealType<%=i%>" style= "display:">
			    	<table  class= common>
			        	<tr  class= common>
			    	  		<td text-align: left colSpan=1>
								<span id="spanDealType<%=i%>Grid" >
								</span> 
							</td>
						</tr>
					</table>
				</Div>
	      	</td></tr></table>     	     
	   </Div>
	   
     </Div>

	<%} %>
	</Div>
    <!--
        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    -->     　
<!-- ************************************以下为备用信息******************************** -->
    <!-- 连带被保人信息部分（列表） -->
	<Div  id= "divLCInsured0" style= "display: none">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
    		</td>
    		<td class= titleImg>
    			 连带被保人信息
    		</td>
    	</tr>
      </table>
	  <Div  id= "divLCInsured2" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSubInsuredGrid" >
					</span> 
				</td>
			</tr>
		</table>
	  </div>
	</div>
    
    <!-- 特约信息部分（列表） -->
    <!--
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
    		</td>
    		<td class= titleImg>
    			 特约信息
    		</td>
    	</tr>
    </table>
    -->
	<Div  id= "divLCSpec1" style= "display:none">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSpecGrid">
					</span> 
				</td>
			</tr>
		</table>
	</div>
