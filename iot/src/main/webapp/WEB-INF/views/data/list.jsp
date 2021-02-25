<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#map {
	position:absolute;  width:800px; height:600px;
	left:50%; top:50%; transform:translate(-50%, -50%) ;
	border:2px solid #666; display:none;
}
.page_next { background:url('imgs/page_next.jpg') center no-repeat; }
.page_last { background:url('imgs/page_last.jpg') center no-repeat;}
.page_first { background:url('imgs/page_first.jpg') center no-repeat;}
.page_prev { background:url('imgs/page_prev.jpg') center no-repeat;}

.marker { font-weight:bold; }

.list-view, .grid-view { font-size:25px; color:#3367d6; 
	padding-top:3px; padding-left:5px }
	
#data-list .pharmacy li div:first-child { height:25px; }
#data-list .pharmacy li div:last-child { font-size:14px;}
table.animal img { width:100px; height:100px; }

ul.animal img { width:100%; height:100%; } 
ul.animal li div.li-top { height:65% }
ul.animal li div.li-top > div { float:left; padding:0; height:100%; }
ul.animal li div.li-top > div:first-child { width:40%; }
ul.animal li > div:nth-child(2) { font-size:14px }
.spec { width:60%; }
.spec div { height:25% !important; padding:0 0 0 10px !important; }
.spec div:nth-child(3) {  }



</style>
</head>
<body>
<h3>공공데이터</h3>
<div class='btnSet dataOption'>
	<a class='btn-fill'>약국조회</a>
	<a class='btn-empty'>유기동물조회</a>
</div>
<div id='list-top'>
	<ul class='common'>
		<li><select id='pageList' class='w-px80'>
			<option value='10'>10개씩</option>
			<option value='20'>20개씩</option>
			<option value='30'>30개씩</option>
			</select>
		</li>
		<li class='list-view'>
			<i class='fas fa-list' style='vertical-align:top;'></i></li>
		<li class='grid-view'>
			<i class='fas fa-th' style='vertical-align:top;'></i></li>
	</ul>
</div>
<div style='margin:20px 0 auto' id='data-list'></div>
<div class='btnSet'>
	<div class='page_list'></div>
</div>

<div id='popup-background'></div>
<div id='map'></div>

<div class="loading"><img src="imgs/loading.gif"/></div>
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCsrerDHJrp9Wu09Ij7MUELxCTPiYfxfBI"></script>
<script type="text/javascript">
// pharmacy_list( 1 );
$(function(){
 	$('.dataOption a:eq(1)').trigger('click');
})

function pharmacy_list( page ){
	$('.loading').css('display', 'block');
	$.ajax({
		url: 'data/pharmacy',
		data: { pageNo:page, rows:pageList },
		success: function( response ){
			$('.loading').css('display', 'none');
			if( viewType=='list' ){
				pharmacy_list_data( $(response.item), true );
			}else{
				pharmacy_grid_data( $(response.item), true );
			}
			makePage( response.count, page );
			
		},error: function(req, text){
			$('.loading').css('display', 'none');
			alert(text+':'+req.status);
		}
	});
	
}

function animal_sido(){
	$.ajax({
		url: 'data/animal/sido',
		success: function(response){
			var tag 
			= '<ul class="animal-top">'
				+ '<li><select id="sido" class="w-px120">'
					+ '<option value="">시도 선택</option>';
			$(response.item).each(function(){
				tag += '<option value="'+ this.orgCd +'">'+ this.orgdownNm +'</option>';
			});
			tag += '</select></li></ul>';
			$('#list-top').prepend(tag);
			animal_type();
			
		},error: function(req,text){
			alert(text+':'+req.status);
		}
	});
}

//축종
function animal_type(){
	var tag 
		= '<li>'
		+ '<select id="upkind" class="w-px80">'
		+ '<option value="">축종 선택</option>'
		+ '<option value="417000">개</option>'
		+ '<option value="422400">고양이</option>'
		+ '<option value="429900">기타</option>'
		+ '</select> '
		+ '</li>';
	$('#sido').closest('li').after(tag);
}

//축종에 따른 품종 조회
function animal_kind(){
	$('#kind').remove();
	if( $('#upkind').val()=='' ) return;
	
	$.ajax({
		url: 'data/animal/animal_kind',
		data: { upkind:$('#upkind').val() },
		success: function(response){
			var tag = '<li>'
				+ '<select id="kind" class="w-px200">'
				+ "<option value=''>품종 선택</option>";
				$(response.item).each(function(){
					tag += '<option value="'+ this.kindCd +'">'+ this.KNm +'</option>';
				});
			tag += '</select></li>';
			$('#upkind').closest('li').after(tag);
			
		},error: function(req, text){
			alert(text+':'+req.status);
		}	
	});
}

//시도에 따른 시군구 조회
function animal_sigungu(){
	$('#sigungu, #shelter').remove();
	if( $('#sido').val()=='' ) return;

	$.ajax({
		url: 'data/animal/sigungu',
		data: { sido:$('#sido').val() },
		success: function(response){
			console.log(response);
			if( response.item.length>0 ){ //세종시의 경우 시군구가 없음
				var tag = '<li>'
					+ '<select class="w-px120" id="sigungu">'
					+ '<option value="">시군구 선택</option>';
				$(response.item).each(function(){
					tag += '<option value="'+ this.orgCd +'">'+ this.orgdownNm +'</option>';				
				});	
				tag += '</select></li>';
				$('#sido').closest('li').after(tag);
			}
		},error: function(req, text){
			alert(text+':'+req.status);
		}
	});
}

//시군구에 따른 보호소 조회
function animal_shelter(){
	$('#shelter').remove();
	if( $('#sigungu').length==0 || $('#sigungu').val()=='' ) return;
	$.ajax({
		url: 'data/animal/shelter',
		data: { sido:$('#sido').val(), sigungu:$("#sigungu").val() },
		success: function( response ){
			var tag = '<li><select class="w-px200" id="shelter">'
				+ '<option value="">보호소 선택</option>';
			$(response.item).each(function(){
				tag += '<option value="'+ this.careRegNo +'">'+ this.careNm +'</option>';
			});
			tag += '</select></li>';
			$('#sigungu').closest('li').after(tag);
		},error: function(req, text){
			alert(text+':'+req.status);
		}
	});
	
}

//유기동물목록 조회
function animal_list( pageNo ){
	if( $('#sido').length==0 ) animal_sido(); 
	
	var animal = new Object();
	animal.pageNo = pageNo;
	animal.rows = $('#pageList').val();
	animal.sido = $('#sido').length>0 ? $('#sido').val() : ""; //시도
	animal.upkind = $('#upkind').length>0 ? $('#upkind').val() : ""; //축종:개,고양이,기타
	animal.kind = $('#kind').length>0 ? $('#kind').val() : ""; //품종
	animal.sigungu = $('#sigungu').length>0 ? $('#sigungu').val() : "";  //시군구
	animal.shelter = $('#shelter').length>0 ? $('#shelter').val() : "";  //보호소
	
	$('.loading').css('display', 'block');
	$.ajax({
		url: 'data/animal/list',
		data: JSON.stringify(animal),
		type: 'post',
		contentType: 'application/json',
		success: function( response ){
			if( viewType=='list' )  animal_list_data( $(response.item), true );
			else					animal_grid_data( $(response.item), true );
			makePage( response.count, pageNo );
			$('.loading').css('display', 'none');
			
		},error: function(req, text){
			alert(text+':'+req.status);
			$('.loading').css('display', 'none');
		}		
	});
	
}

function animal_list_data(data, type){
	var tag = '<table class="animal">';
	console.log( data.length )
	
	if( (type && data.length==0) 
			||  (!type && data.length==1 && $('.li-top').length==0) ){
		tag += '<tr><td colspan="11">해당 유기동물이 없습니다</td></tr>';
	}else{
		
	if( type ){
		
		data.each(function(){
			
	tag += '<tr><td class="w-px100" rowspan="3"><img src="'+ this.filename +'"/></td>'
		+ '<th class="w-px80">성별</th><td class="w-px60">'+ this.sexCd +'</td>'
		+ '<th class="w-px40">나이</th><td class="w-px100">'+ this.age +'</td>'
		+ '<th class="w-px40">체중</th><td class="w-px80">'+ this.weight +'</td>'
		+ '<th class="w-px40">색상</th><td>'+ this.colorCd +'</td>'
		+ '<th class="w-px60">접수일자</th><td class="w-px100">'+ this.happenDt +'</td>'
		+ '</tr>';
	tag += '<tr><th>특징</th><td colspan="9" class="left">'+ this.specialMark +'</td></tr>'
		+ '<tr><th>발견장소</th><td colspan="8" class="left">'+ this.happenPlace +'</td>'
		+ '<td>'+ this.processState +'</td>'
		+ '</tr>';
	tag += '<tr><td colspan="2">'+ this.careNm +'</td>'
		+ '<td colspan="7" class="left">'+ this.careAddr +'</td>'
		+ '<td colspan="2">'+ this.careTel +'</td>'
		+ '</tr>';
		});
	}else{
		data.each(function(){
		
			tag += '<tr><td class="w-px100" rowspan="3"><img src="'+ $(this).data('filename') +'"/></td>'
			+ '<th class="w-px80">성별</th><td class="w-px60">'+ $(this).data('sexcd') +'</td>'
			+ '<th class="w-px40">나이</th><td class="w-px100">'+ $(this).data('age') +'</td>'
			+ '<th class="w-px40">체중</th><td class="w-px80">'+ $(this).data('weight') +'</td>'
			+ '<th class="w-px40">색상</th><td>'+ $(this).data('colorcd') +'</td>'
			+ '<th class="w-px60">접수일자</th><td class="w-px100">'+ $(this).data('happendt') +'</td>'
			+ '</tr>';
		tag += '<tr><th>특징</th><td colspan="9" class="left">'+ $(this).data('specialmark') +'</td></tr>'
			+ '<tr><th>발견장소</th><td colspan="8" class="left">'+ $(this).data('happenplace') +'</td>'
			+ '<td>'+ $(this).data('processstate') +'</td>'
			+ '</tr>';
		tag += '<tr><td colspan="2">'+ $(this).data('carenm') +'</td>'
			+ '<td colspan="7" class="left">'+ $(this).data('careaddr') +'</td>'
			+ '<td colspan="2">'+ $(this).data('caretel') +'</td>'
			+ '</tr>';
			
		});
	}
	}
	tag += '</table>';
	$('#data-list').html(tag);
	
}
function animal_grid_data(data, type){
	var tag = '<ul class="animal grid">';

	if( ( type && data.length==0 ) || (!type && data.length==1) ){
		tag += '<li style="width:100%; height:32px !important">해당 유기동물이 없습니다</li>';
	}else{	
		
	if( type ){
		
		data.each(function(){
			tag += '<li data-filename="'+ this.filename + '" '
			 + 'data-age="'+ this.age + '" data-sexcd="' + this.sexCd + '" ' 
			 + 'data-weight="'+ this.weight + '" data-colorcd="' + this.colorCd + '" '
			 + 'data-processstate="'+ this.processState + '" '
			 + 'data-carenm="'+ this.careNm + '" '
			 + 'data-happendt="'+ this.happenDt + '" '
			 + 'data-specialmark="'+ this.specialMark + '" '
			 + 'data-happenplace="'+ this.happenPlace + '" '
			 + 'data-careaddr="'+ this.careAddr + '" '
			 + 'data-caretel="'+ this.careTel + '" '					 
			 + '>'
			+ '<div class="li-top">'
				+ '<div><img src="'+ this.filename +'"/></div>'
				+ '<div class="spec">'
					+ '<div><span>'+ this.age +'</span>'
						 + '<span style="float:right">'+ this.sexCd +'</span></div>'
					+ '<div>'+ this.weight +'</div>'	 
					+ '<div>'+ this.colorCd +'</div>'	 
					+ '<div>'+ this.processState +'</div>'	 
				+ '</div>'
			+ '</div>'
			+ '<div><span>'+ this.careNm +'</span>'
				 + '<span style="float:right">'+ this.happenDt +'</span>'
			+ '</div>'
			+ '</li>';
		});
	}else{
		
		var datas;
		data.each(function(){
			var idx = $(this).index()%4;
			if( idx==0 ){
				datas = {};
				datas.filename = $(this).find('img').attr('src');
				datas.age = $(this).children('td:eq(2)').text();
				datas.sexcd = $(this).children('td:eq(1)').text();
				datas.weight = $(this).children('td:eq(3)').text();
				datas.colorcd = $(this).children('td:eq(4)').text();
				datas.happendt = $(this).children('td:eq(5)').text();
			}else if( idx==1 ){
				datas.specialmark = $(this).children('td:eq(0)').text();
			}else if( idx==2 ){
				datas.processstate = $(this).children('td:eq(1)').text();
				datas.happenplace = $(this).children('td:eq(0)').text();
			}else if( idx==3 ){
				datas.carenm = $(this).children('td:eq(0)').text();
				datas.careaddr = $(this).children('td:eq(1)').text();
				datas.caretel = $(this).children('td:eq(2)').text();
				
				tag += '<li data-filename="'+ datas.filename + '" '
					 + 'data-age="'+ datas.age + '" data-sexcd="' + datas.sexcd + '" ' 
					 + 'data-weight="'+ datas.weight + '" data-colorcd="' + datas.colorcd + '" '
					 + 'data-processstate="'+ datas.processstate + '" '
					 + 'data-carenm="'+ datas.carenm + '" '
					 + 'data-happendt="'+ datas.happendt + '" '
					 + 'data-specialmark="'+ datas.specialmark + '" '
					 + 'data-happenplace="'+ datas.happenplace + '" '
					 + 'data-careaddr="'+ datas.careaddr + '" '
					 + 'data-caretel="'+ datas.caretel + '" '					 
					 + '>'
					+ '<div class="li-top">'
						+ '<div><img src="'+ datas.filename +'"/></div>'
						+ '<div class="spec">'
							+ '<div><span>'+ datas.age +'</span>'
								 + '<span style="float:right">'+ datas.sexcd +'</span></div>'
							+ '<div>'+ datas.weight +'</div>'	 
							+ '<div>'+ datas.colorcd +'</div>'	 
							+ '<div>'+ datas.processstate +'</div>'	 
						+ '</div>'
					+ '</div>'
					+ '<div><span>'+ datas.carenm +'</span>'
						 + '<span style="float:right">'+ datas.happendt +'</span>'
					+ '</div>'
					+ '</li>';
			}
		});
	}
	}
	
	tag += '</ul>';
	$('#data-list').html(tag);
	var len = $('.animal li').length;
	$('#data-list .animal').css('height'
			, ((len%5>0 ? 1 :0) + Math.floor(len/5)) 
					* $('.animal li').outerHeight(true) - 20 );
}


var pageList = 10, blockPage = 10;
function makePage(totalList, curPage){
	var page = pageVO(totalList, curPage);
	var tag='';

	if( page.curBlock > 1 ){
		tag += '<a class="page_first" data-page="1">처음</a>'
			+ '<a class="page_prev" data-page="'
						+ (page.beginPage-blockPage) +'" >이전</a>';
	}
	
	for(var no=page.beginPage; no<=page.endPage; no++){
		if(no==curPage) tag += '<span class="page_on">'+ no +'</span>';
		else tag += '<a class="page_off" data-page="'+ no +'" >'+ no +'</a>'; 
	}

	if( page.curBlock < page.totalBlock ){
		tag += '<a class="page_next" data-page="'+ (page.endPage+1) +'">다음</a>'
			+ '<a class="page_last" data-page="'+ page.totalPage +'">마지막</a>';
	}
	
	$('.page_list').html(tag);
}

function pageVO(totalList, curPage){
	var page = new Object();
	page.totalPage = parseInt(totalList / pageList)
					+  (totalList % pageList == 0 ? 0 : 1) ;
	page.totalBlock = parseInt(page.totalPage / blockPage)
	                + (page.totalPage % blockPage==0 ? 0 : 1);
    page.curBlock = parseInt(curPage / blockPage)
    				+ (curPage % blockPage==0 ? 0 : 1);
	page.endPage = page.curBlock * blockPage;
	page.beginPage = page.endPage - (blockPage-1);
	if( page.endPage > page.totalPage )  page.endPage = page.totalPage;
	return page;
}

$(document)
.on('click', '.show', function(){
	$('#map, #popup-background').css('display', 'block');

	var xy = { lat: Number($(this).data('y')), lng: Number($(this).data('x')) };
	var id = new google.maps.Map(document.getElementById("map"), {
	    center: xy,
	    zoom: 15,
  	});

	//약국의 위치를 알수 있도록 마커를 표현
// 	new google.maps.Marker({
// 		map: id,   position: xy, title: $(this).text(),
// 	});

	var info = new google.maps.InfoWindow();	
	info.setOptions({
		content: '<div class="marker">'+ $(this).text() +'</div>'
	});
	info.open(id, new google.maps.Marker({
		map:id, position: xy	
	}));
	
}).on('click', '#popup-background', function(){
	$('#map, #popup-background').css('display', 'none');

}).on('click', '.page_list a', function(){
	if( $('.pharmacy').length>0 ) pharmacy_list( $(this).data('page') );
	else if( $('.animal').length>0 ) animal_list( $(this).data('page') );

}).on('change', '#pageList', function(){
	pageList = $(this).val();
	if( $('.pharmacy').length>0 ) pharmacy_list(1);
	else if( $('.animal').length>0 ) animal_list(1); 

}).on('click', '.grid-view', function(){
	//목록형태로 보여지고 있는 경우
	if( viewType=='list' ){
		viewType='grid';
		if( $('.pharmacy').length>0 ) pharmacy_grid_data( $('.pharmacy tr'), false );
		else if( $('.animal').length>0 ) animal_grid_data( $('.animal tr'), false ); 
	}

}).on('click', '.list-view', function(){
	//그리드형태로 보여지고 있는 경우
	if( viewType=='grid' ){
		viewType='list';        
		if( $('.pharmacy').length>0 ) pharmacy_list_data( $('.pharmacy li'), false );
		else if( $('.animal').length>0 ) animal_list_data( $('.animal li'), false );
	}

}).on('change', '#sido', function(){ //시도변경
	animal_list( 1 );
	animal_sigungu();
	
}).on('change', '#upkind', function(){ //축종변경
	animal_list( 1 );
	//축종에 따른 품종을 조회
	animal_kind();
	
}).on('change', '#kind', function(){ //품종변경
	animal_list( 1 );
	
}).on('change', '#sigungu', function(){ //시군구변경
	animal_list(1);
	animal_shelter();
	
}).on('change', '#shelter', function(){ //보호소변경
	animal_list(1);
	
});

//그리드 --> 목록형태로 변경
function pharmacy_list_data( data, type ){
	var tag = '<table class="pharmacy">'
		+ '<tr><th class="w-px200">약국명</th>'
		+ '<th class="w-px140">전화번호</th><th>주소</th></tr>';
		
	if( type ){ //API요청
		data.each(function(){
			tag += '<tr><td><a class="show" data-x="'+ this.XPos +'" data-y="'+ this.YPos +'" >'+ this.yadmNm +'</a></td>'
				+ '<td>'+ this.telno +'</td>'
				+ '<td class="left">'+ this.addr +'</td>'
				+ '</tr>';
		});
	
	}else{ //화면만 변경
		data.each(function(){
			var $a = $(this).find('.show');
			tag += '<tr>'
				+ '<td><a class="show" '
					+ 'data-x="'+ $a.data('x') +'" data-y="'+ $a.data('y') +'" >'
					+ $(this).children('div:eq(0)').text() +'</a></td>'
				+ '<td>'+ $(this).children('div:eq(1)').text() +'</td>'
				+ '<td class="left">'+ $(this).children('div:eq(2)').text() +'</td>'
				+ '</tr>';
		});			
	}
	tag += '</table>';
	$('#data-list').html( tag );
	
}

var viewType = 'list';

//목록 --> 그리드형태로 변경
function pharmacy_grid_data( data, type ){
	var tag = '<ul class="pharmacy grid">';
	if( type ){
		data.each(function(){
			tag += '<li><div><a class="show" '
				+ 'data-x="'+ this.XPos +'" data-y="'+ this.YPos +'" >'
				+ this.yadmNm +'</a></div>'
			+ '<div>'+ this.telno +'</div>'
			+ '<div>'+ this.addr +'</div>'
			+ '</li>';
		});
	}else{
		data.each(function(){
			if( $(this).index()>0 ){
				var $a = $(this).find('.show');	
				tag += '<li><div><a class="show" '
						+ 'data-x="'+ $a.data('x') +'" data-y="'+ $a.data('y') +'" >'
						+ $(this).children('td:eq(0)').text() +'</a></div>'
					+ '<div>'+ $(this).children('td:eq(1)').text() +'</div>'
					+ '<div>'+ $(this).children('td:eq(2)').text() +'</div>'
					+ '</li>';
			}
		});
	}
	tag += '</ul>';
	$('#data-list').html(tag);
	var len = $('.pharmacy li').length;
	$('#data-list .pharmacy').css('height'
			, ((len%5>0 ? 1 :0) + Math.floor(len/5)) 
					* $('.pharmacy li').outerHeight(true) - 20 );
}






$('.dataOption a').click(function(){
	$('.dataOption a').removeClass();
	$('.dataOption a').addClass('btn-empty');
	$(this).removeClass('btn-empty').addClass('btn-fill');
	var idx = $(this).index();
	if( idx==0 ) pharmacy_list(1);
	else animal_list( 1 );
	
// 	if( $(this).hasClass('btn-empty') ){
// 		$('.dataOption a').removeClass();
// 		$(this).addClass('btn-fill');
// 		var idx = $(this).index();
// 		$('.dataOption a:not(:eq('+ idx +'))').addClass('btn-empty');
// 	}
});




</script>

</body>
</html>






