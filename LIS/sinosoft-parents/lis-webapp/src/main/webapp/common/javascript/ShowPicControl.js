<!--
//ProposalShowPic.js

var w	//ͼƬ�Ŀ��
var h	//ͼƬ�ĸ߶ȣ��ɲ�ʹ��
var b_img	//�Ŵ�ͼƬ�Ĵ���
var s_img	//��СͼƬ�Ĵ���
b_img = 0
s_img = 0

//�õ�ʹ���е�ͼƬ��ʵ�ʿ�ȣ���λ�����أ�
//if (typeof(window.top.fraPic) == "object") 
//window.top.fraPic.window.location = "../app/testPropersal/pic.html";
//w = window.top.fraPic.service.width;
//alert("wControl:"+w);
w = 800;

//ͼƬ�Ķ�������
pic_name = new Array(3);
pic_place = 0;
pic_name[0] = "./p1.JPG";
pic_name[1] = "./p2.JPG";
pic_name[2] = "./p3.JPG";
//app/testPropersal/

function document_onkeydown() {
	//ȥ��ֵ
	var keycode = event.keyCode
	//var key = event
    //var realkey = String.fromCharCode(event.keyCode)
    //alert("keycode: " + keycode + "\nrealkey: " + realkey)

	//�ж��Ƿ���PageUp
	if (keycode == "33") {
		//ѭ��ͼƬ����
		for (var i=pic_name.length; i >= 0; i--){
			//�ҵ���һ�����������Ķ�����
			if (i < pic_place){
				//�ı���ҳ��ͼƬ����
				window.top.fraPic.service.src = pic_name[i];
				//���¶�λ��ѡͼƬ���ڶ��е�λ��
				pic_place = i;
				//�ж�ѭ��
				break;
			}
		}
	}
	//�ж��Ƿ���PageDown
	if (keycode == "34") {
		for (var i=0; i < pic_name.length; i++){
			if (i > pic_place){
				window.top.fraPic.service.src = pic_name[i];
				pic_place = i;
				break;
			}
		}
	}
	//�ж��Ƿ���С�����ϵ�*
	if (keycode == "106") {
		//��ԭͼƬ��ʵ�ʴ�С�������Ŵ����С������Ϊ0
		window.top.fraPic.service.width = w
		s_img = 0;
		b_img = 0;
	}
	
	//�ж��Ƿ���С���̵�+��ͬʱ��סctrl
	if ((keycode == "107")&&(event.ctrlKey == true)) {
		//�ж��Ŵ������Ŀǰ����Ϊ6�Σ����޸�
		//alert("ctrl+" + b_img+ "\n" + w + "\n" + (w / (1 + 0.2 * (s_img - 1))) );
		if (b_img <= 5){
			//�ж��Ƿ���С��
			if (s_img != 0){
				window.top.fraPic.service.width = w / (1 + 0.2 * (s_img - 1))
				s_img = s_img - 1;
			}
			else{
				b_img = b_img + 1;
				window.top.fraPic.service.width = w * (1 + 0.2 * b_img)
			}
		}
	}
	
	//�ж��Ƿ���С���̵�-��ͬʱ��סctrl�����ɸı䵽��ԭͼ��С
	if ((keycode == "109")&&(event.ctrlKey == true)) {
		if (b_img != 0){
			window.top.fraPic.service.width = w * (1 + 0.2 * (b_img - 1))
			b_img = b_img - 1;
		}
	}
	
	//�ж��Ƿ���С����/���ɸı䵽��ԭͼ��С���������-������ѡһʹ��
	if (keycode == "111") {
		//�ж���С������Ŀǰ����Ϊ6�Σ����޸�
		if (s_img <= 5){
			if (b_img != 0){
				window.top.fraPic.service.width = w * (1 + 0.2 * (b_img - 1))
				b_img = b_img - 1;
			}	else {
				s_img = s_img + 1;
				window.top.fraPic.service.width = w / (1 + 0.2 * s_img)
			}
		}
	}
	
	//�ж��Ƿ���С����`����ʾ������ͼƬ�壨Minim���Ӹ÷�����
	if (keycode == "192") {
	  if (top.fraTalkCol.cols == "0, *") {
	    top.fraTalkCol.cols = "50%, *";
	  } else {
	    top.fraTalkCol.cols = "0, *";
	  }
	}
	
	//alert("keycode:" + keycode);
}

//-->