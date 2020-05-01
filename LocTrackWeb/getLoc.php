<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

header('Content-Type: application/json');
session_start();
include 'sqlconnect.php';

@$con=mysql_connect($servername,$username,$password);
mysql_select_db($dbname,$con);

$qry="select * from location";
$result=mysql_query($qry);

for($i=0;$i<mysql_num_rows($result);$i++){
    $row=  mysql_fetch_array($result);
}

$lat=$row[0];
$long=$row[1];
$time=microtime(true)*1000;


$latlong=array('lat'=> $lat, 'long' => $long , 'time' => $time); 

echo json_encode($latlong);

?>

