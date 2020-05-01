<html>
    <head>
		<style>
			body{
				margin:0px 0px 0px 0px;
			}
		</style>
	</head>
	<body>
	<?php
		$loc=$_GET['loc'];
                $width=$_GET['width'];
                $height=$_GET['height'];
	?>
	<script src="http://en.parkopedia.in/js/embeds/mapView.js" data-Location="<?php echo $loc;?>" data-options="l=1&tc=1&zc=1&country=IN&ts[]=4&ts[]=3&ts[]=2" data-size="<?=$width?>:<?=$height?>" type="text/javascript"></script>
	</body>	
</html>