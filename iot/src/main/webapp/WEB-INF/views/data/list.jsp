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
			console.log( response );
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
//유기동물목록 조회
function animal_list( pageNo ){
	var animal = new Object();
	animal.pageNo = pageNo;
	animal.rows = $('#pageList').val();
	
	$.ajax({
		url: 'data/animal/list',
		data: JSON.stringify(animal),
		type: 'post',
		contentType: 'application/json',
		success: function( response ){
			console.log(response);
			if( viewType=='list' )  animal_list_data( $(response.item), true );
			else					animal_grid_data( $(response.item), true );
			makePage( response.count, pageNo );
			
		},error: function(req, text){
			alert(text+':'+req.status);
		}		
		
	});
}

function animal_list_data(data, type){
	
}
function animal_grid_data(data, type){
	
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
	pharmacy_list( $(this).data('page') );

}).on('change', '#pageList', function(){
	pageList = $(this).val();
	pharmacy_list(1);

}).on('click', '.grid-view', function(){
	//목록형태로 보여지고 있는 경우
	if( viewType=='list' ){
		viewType='grid';
		pharmacy_grid_data( $('.pharmacy tr'), false );
	}

}).on('click', '.list-view', function(){
	//그리드형태로 보여지고 있는 경우
	if( viewType=='grid' ){
		viewType='list';        
		pharmacy_list_data( $('.pharmacy li'), false );
	}
	
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






