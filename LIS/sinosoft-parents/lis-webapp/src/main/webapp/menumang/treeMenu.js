// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 5 hide 6 showlist 7 check 8 id
/*treeArray�����ݽṹ��ʾ��һ��10�е�����
�����У���������treeArrayʱ������ǽڵ��Ƿ��ѷ��ʹ�
��һ�У��˵��ڵ�ĸ��ڵ�ڵ����
�ڶ��У��ڵ���ӽڵ���
�����У��ڵ������
�����У��ڵ����
�����У���ǽڵ��Ƿ��ڽ����ϱ�ɾ��
�����У��ڵ���ӽڵ��б��Ƿ��
�����У��ڵ��Ƿ�ѡ��
�ڰ��У��ڵ���html�ж�Ӧ��Ԫ�ص�id
�ھ��У��ڵ��Ƿ���Ҷ�ӽڵ�
*/
//��ǽڵ��Ƿ�����

function tagInArray(tArray,id)
{
	for(var i = 0; i < tArray.length; i++) {
		var aryId = "chk_" + tArray[i][8];
		if (aryId != id)
		    continue;
	//	alert("tArray[i][7] = " + tArray[i][7]);
		tArray[i][7] = (tArray[i][7] == 0) ? 1 : 0;

		//�����˽ڵ�������ӽڵ㣬�����ӽڵ���ӽڵ㣬����
		//tArray[i][7]��ֵ������Ϊ�Ƿ�ѡ��
		travels_chk(tArray,i,tArray[i][7]);
	}
}

//�����ڵ�ķǵݹ��㷨��tArrayΪ���������飬startΪ�����Ŀ�ʼλ��
//chkVal��ʾ��chkbox��Ϊ��ֵ
function travels_chk(tArray,start,chkVal)
{
    var stackArray = new Array(); // ģ��ջ�ṹ
    var top = 0; // ָ��ջ����ָ��
    stackArray[top] = start;

    while (top != -1) {
    	var index = stackArray[top]; //ջ��Ԫ�س�ջ
    	top--;
    	if (tArray[index][2] == 0) //Ҷ�ӽڵ㣬û���ӽڵ�
    	    continue;

    	var parentCode = tArray[index][4];
    	for (var i = index + 1; i < tArray.length; i++) {
    	    if (tArray[i][5] == 1) //�˽ڵ㲻�ڽ�����
    		continue;
	    if (tArray[i][1] == parentCode) {
	    	tArray[i][7] = chkVal;
	    	var chkElmID = "chk_" + tArray[i][8];
	    	document.all(chkElmID).checked = chkVal;
	    	top++;              //������ڵ�ѹ��ջ
	  //  	alert("top:" + top);
	    	stackArray[top] = i;
	    }
	}
    }
}


//��Ǹ��ڵ��б��Ƿ��
function tagShowlistInArray(tArray,id)
{
	for(var i = 0; i < tArray.length; i++) {
		var aryId = "fld_" + selectArray[i][8];
		if (aryId != id)
		    continue;

		tArray[i][6] = (tArray[i][6] == 0) ? 1: 0;
	}
}


//���ڵ�nodecode�����νṹ��ȥ�������function��Ŀ����Ҫ�����ӽڵ���
//Ϊ0ʱ�����丸�ڵ�Ҳɾ�������أ�tArray�Ǵ洢���ε����ݽṹ��offset
//�ǽڵ���tArray�е�ƫ����
function removeNode(tArray,offset)
{
//if(tArray.length==offset){ return;}
	//����Ҷ�ӽڵ�
	if (tArray[offset][9] == 0)
	    return;

	//�˽ڵ��ѱ�ɾ��
	if (tArray[offset][5] == 1)
	    return;

	//ɾ���˽ڵ�
	tArray[offset][5] = 1;

	//���ϻ�˷�������ڵ���ӽڵ�����1�����Ϊ0��ɾ�����ڵ�
	var parent = tArray[offset][1];
	//alert("parent:" + parent);

	while (parent != 0) {
		var index = offset - 1;
		for (; index > 0; index--) {
			if (tArray[index][4] != parent)
			    continue;
			break;
		}

		tArray[index][2]--;

		//ɾ�����ڵ�
		if (tArray[index][2] == 0){
		    tArray[index][5] = 1;
		    parent = tArray[index][1];
		} else { //�������ϻ���
			parent = 0;
		}
	}
}

function clearTree(spanCtrl)
{
	var resultHtml = "";
	document.all(spanCtrl).innerHTML=resultHtml;
}

//�ڽ����ϻ���tArray��������ͼ
// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 
// 5 hide 6 showlist 7 check 8 id
function paintTree(tArray,spanCtrl)
{
        for (var i = 0; i < tArray.length; i++) {
            tArray[i][0] = 0;//ȫ�����Ϊδ����
        }

    var resultHtml = "<ul>\n";
    resultHtml +=createTreeHtml(tArray,0,0);//P270
    resultHtml += "\n</ul>";
    //alert(resultHtml);
    //document.fm.all('tt').value = resultHtml;
    //alert(document.all(spanCtrl).innerHTML);
    document.all(spanCtrl).innerHTML=resultHtml;

    //����д򿪵��б����ڴ�
//	for (var i = 0; i < tArray.length; i++) {
//		if (tArray[i][2] == 0)
//			continue;
//		if (tArray[i][5] == 1)
//			continue;
//		var elmName = "fld_" + tArray[i][8]
//		var sourceIndex = fm.all(elmName).sourceIndex + 1;
//		//var sourceIndex = fm.all(tArray[i][8]).sourceIndex + 1;
//		//alert(elmName);
//		//alert(tArray[i][6]);
//		if (tArray[i][6] == 0) {
//			document.all[sourceIndex].style.display = "none";
//			document.all[sourceIndex+1].style.display = "none";
//		}
//		else{
//			document.all[sourceIndex].style.display = '';
//			document.all[sourceIndex+1].style.display = '';
//		}
//	}
//    alert("over paint");
}

//�ڽ����ϻ���tArray��������ͼ
function paintTree_ReadOnly(tArray,spanCtrl)
{
	//alert("treemenu.js:paintTree_ReadOnly");//2005
	for (var i = 0; i < tArray.length; i++)
	{
		tArray[i][0] = 0;
	}
	var resultHtml = "<ul>\n";
	resultHtml +=createTreeHtml_ReadOnly(tArray,0,0);
	resultHtml += "\n</ul>";
	document.all(spanCtrl).innerHTML=resultHtml;
	//����д򿪵��б����ڴ�
	for (var i = 0; i < tArray.length; i++)
	{
		if (tArray[i][2] == 0)
			continue;
		if (tArray[i][5] == 1)
			continue;
		/*var sourceIndex = document.all(tArray[i][8]).sourceIndex + 1;
		if (tArray[i][6] == 0)
		{
			document.all[sourceIndex].style.display = "none";
		}
		else
		{
			document.all[sourceIndex].style.display = '';
		}*/
	}
}

//���ڵ�nodecode�������νṹ�У����function��Ŀ����Ҫ�ǽ����ڵ�
//Ҳ���ϣ�tArray�Ǵ洢���ε����ݽṹ��offset�ǽڵ���tArray�е�ƫ����
function addNode(tArray,offset)
{
	//alert("treemenu.js:addNodee");//2005
	var kkk = 0;
	if (offset >= tArray.length)
	    return;
	//����Ҷ�ӽڵ�
	if (tArray[offset][9] == 0)
	    return;
	//�˽ڵ��Ѵ���
	if (tArray[offset][5] == 0)
	    return;
	//���Ӵ˽ڵ�
	tArray[offset][5] = 0;
	tArray[offset][7] = 0;
	//���ϻ�˷�������ڵ���ӽڵ�����1�������Ϊ0�����Ӹ��ڵ�
	var parent = tArray[offset][1];

	while (parent != 0) {
		var index = offset - 1;
		for (; index > 0; index--) {
			if (tArray[index][4] != parent)
			    continue;
			break;
		}
		tArray[index][2]++;
                //���Ӹ��ڵ�
		if (tArray[index][2] == 1){
		    tArray[index][5] = 0;
		    tArray[index][7] = 0; //�˴�����Բ�д
		    parent = tArray[index][1];
		} else { //�������ϻ���
			parent = 0;
		}
	}
}

// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 5 hide
function createTreeHtml_ReadOnly(tArray,pos,parent)
{
	//alert("treemenu.js:createTreeHtml_ReadOnly");//2005
	var resultHtml = "";
	if (pos >= tArray.length)
	    return resultHtml;

	for (var i = pos; i < tArray.length ; i++) {
		// �ڵ������ص�
		if(tArray[i][5] == 1)
		    continue;

		//�ڵ��ѷ��ʹ�
		if (tArray[i][0]==1)
		    continue;

		if (tArray[i][1] != parent)
		    continue;

		//��ǽڵ��ѷ��ʹ�
		tArray[i][0] = 1;

		if (tArray[i][9] == 1) { //Ҷ�ӽڵ�
		    var nodeHtml = '<li>' + tArray[i][3] + "</a></li>\n";
		    resultHtml += nodeHtml;
		} else { //��Ҷ�ӽڵ�
			resultHtml = resultHtml + '<li name="foldheader" class=common id =' + tArray[i][8] + '>' + tArray[i][3]  + "</li>\n";
			resultHtml = resultHtml + '<ul name=foldinglist style=display:none style=&{head};>\n';
			resultHtml += createTreeHtml_ReadOnly(tArray,i+1, tArray[i][4]);
			resultHtml += "</ul>\n";
		}
	}

	return resultHtml;
}


// 0:viste 1:parent 2:childnum; 3 nodeName 4 nodecode 5 hide
// 6 showlist 7 check 8 id
function createTreeHtml(tArray,pos,parent)
{
	//alert("treemenu.js:createTreeHtml");//2005
		var resultHtml = "";
		if (pos >= tArray.length)
		    return resultHtml;

		for (var i = pos; i < tArray.length ; i++) {
			// �ڵ������ص�			
			if(tArray[i][5] == 1)
			    continue;
			//�ڵ��ѷ��ʹ�
			if (tArray[i][0]==1)
			    continue;
			if (tArray[i][1] != parent)
			    continue;

			//��ǽڵ��ѷ��ʹ�
			tArray[i][0] = 1;

			if (tArray[i][9] == 1) { //Ҷ�ӽڵ�
			    var nodeHtml = '<li><INPUT TYPE="checkbox" id="chk_' + tArray[i][8]+ '">' + tArray[i][3] + "</a></li>\n";
			    resultHtml += nodeHtml;

			} else { //��Ҷ�ӽڵ�
			//	resultHtml = resultHtml + '<li name="foldheader"  class=common id =' + tArray[i][8] + '>' + tArray[i][3]  + "</li>\n";
			        resultHtml = resultHtml + '<li name="foldheader"  class=common id =fld_' + tArray[i][8] + '>'
			        	+ '<INPUT  TYPE="checkbox" id = chk_' + tArray[i][8] +  ' style=' + '"display:' + "'" + "'" + ' ">' + tArray[i][3]  + "</li>\n";
				resultHtml = resultHtml + '<ul name=foldinglist style=display:none style=&{head};>\n';

				resultHtml += createTreeHtml(tArray,i+1, tArray[i][4]);
				resultHtml += "</ul>\n";
			}
		}
// alert(resultHtml);//2005
	return resultHtml;
}
