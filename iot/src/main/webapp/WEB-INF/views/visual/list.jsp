<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#tabs { 
	overflow: hidden; width: 100%;
	margin: 0; padding: 0; list-style: none; border-bottom: 2px solid #3367d6;
}

#tabs li {
	float: left; width: 100px;
	margin: 0 -1px 0 0;
	line-height: 40px; color: #3367d6; background-color: #fff;
	border: 1px solid #3367d6; border-bottom: none; cursor: pointer;
}

#tabs li.active {
	color: #fff; background-color: #3367d6;
	border: 1px solid #3367d6; border-bottom: none;
	
}

#tabContent > div { display: none; }
#tabContent > div.active { display: block; }

#tabContent { height: 480px; margin: 20px 0; }

.c3-axis ,.c3-chart { font-size: 16px; font-weight: bold; }
.legend ul { display: inline-block; }
.legend ul li { display: block; float: left; height: 20px; }
.legend ul li * { vertical-align: middle; }
.legend .legend-item { width: 15px; height: 15px; display: inline-block; margin: 2px 2px 0; }
.legend ul li:not(:first-child) {
	margin-left: 30px;
}
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.20/c3.min.css">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.20/c3.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/d3/5.16.0/d3.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#tabs li:eq(0)').trigger('click');
	
});
$(document).on('click', '#tabs li', function(){
	$('#tabs li').removeClass();
	$(this).addClass('active');

	var idx = $('#tabs li.active').index();
	$('#tabContent > div').removeClass();
	$('#tabContent > div:eq('+ idx +')').addClass('active');

	if( idx==0 ) department_graph();
	else if(idx==1) hirement_graph();
});
//부서별 인원수
function department_graph(){
	$.ajax({
		url: 'visual/department',
		success: function( response ){
			console.log(response)
			var cnt = ['부서원수'], name = [ '부서명' ];
			var info = [];
			$(response).each(function(){
				
					//선/막대 그래프
				cnt.push( this.count );
				name.push( this.department_name );
				
				//파이/도넛 그래프
				//info.push( new Array(this.department_name, this.count) );
				
			});
 			make_chart([ name, cnt ]);	//기본: 선/막대
// 			make_chart([ ['부서원수', 3, 9, 1, 5, 2] ]);
		//	make_chart(info);	//파이/도넛
			
		},error: function(req, text){
			alert(text+req.status);
		}
	});
}
//년도별 채용인원수
function hirement_graph(){
	
}

function basis_type(datas){	//선그래프
	var chart = c3.generate({
		bindto: '.department .chart',
		data: { columns: datas
				, x: '부서명'
				},
		axis: { x:{ type:'category' } },
		size: { height:400, width:1000 },
		
	});
}
function make_chart(datas){
	//1.선그래프(기본)
	//basis_type(datas);
	//2.파이그래프
// 	pie_type(datas);
	//3.도넛그래프
	//donut_type(datas);
	//4.막대그래프
	bar_type(datas);
}
var colors = [ '#d1bee6', '#acb8e3', '#acdae3', '#ace3d1', '#ace3bc', '#cce3ac', '#e1e3ac', '#e3acd3', '#deace3', '#acaee3', '#cae3ac', '#e3d1ac' ];

function setColor(num){
	if( num <= 10 ) return 0;
	else if( num <= 20 ) return 1;
	else if( num <= 30 ) return 2;
	else if( num <= 40 ) return 3;
	else if( num <= 50 ) return 4;
	else return 5;
}
function bar_type(datas){
	var chart = c3.generate({
		bindto: '.department .chart',
		data: { columns: datas, x:'부서명', type:'bar', labels:true,
// 				color: function(color, data){ return colors[ data.index ] } 
				color: function(color, data){ return colors[ setColor(data.value) ] } 
		},
		axis: { x:{type:'category', tick:{ rotate:60 } } 
				,y:{ label:{ text:'부서원수', position:'outer-middle' } }},
		legend: { hide:true },
		size: { width: 1000, height: 400 },
		bar: { width: 30 },
		grid: { y:{show:true} },
	});
	$('.legend-item').each(function(idx){
		$(this).css('background-color', colors[idx]);
	});
}

function donut_type(datas){
	var chart = c3.generate({
		bindto: '.department .chart',
		data: { columns: datas, type: 'donut' },
		size: { width: 1000, height: 400 },
		donut: { 
			width: 90,
			title: '부서별 사원수',
			label: { 
				format: function(value, ratio, id){
					return (ratio*100).toFixed(1) + '%(' + value + '명)';
				}
			}
		},
	});
}

function pie_type(datas){
	var chart = c3.generate({
		bindto: '.department .chart',
		data: { columns: datas, type: 'pie' },
		size: { width: 1000, height: 400 },
		pie: { 
			label: { 
				format: function(value, ratio, id){
					return (ratio*100).toFixed(1) + '%(' + value + '명)';
				}
			}
		},
	});
}
// department_graph();

</script>

</head>
<body>
<h3>사원정보분석</h3>
<div class="w-pct80" style="margin: 0 auto;">
<ul id="tabs">
	<li class="active">부서원수</li>
	<li>채용인원수</li>
</ul>
<div id="tabContent">
	<div class="active">
		<div class="department">
			<div class="chart"></div>
			<div class="legend">
				<ul>
					<li><span class="legend-item"></span><span>0~10명</span></li>
					<li><span class="legend-item"></span><span>11~20명</span></li>
					<li><span class="legend-item"></span><span>21~30명</span></li>
					<li><span class="legend-item"></span><span>31~40명</span></li>
					<li><span class="legend-item"></span><span>41~50명</span></li>
					<li><span class="legend-item"></span><span>50명 초과</span></li>
				</ul>
			</div>
		</div>
	</div>
	<div>
		<div class="hirement">
			<div class="chart"></div>
		</div>
	</div>
</div>
</div>
</body>
</html>