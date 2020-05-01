<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

session_start();
$lat=$_GET['lat'];
$lng=$_GET['lng'];
//$placeid=$_POST['placeid'];


$lat= floor($lat / 100) + ($lat - (floor($lat / 100) * 100)) / 60;
$lng= floor($lng / 100) + ($lng - (floor($lng / 100) * 100)) / 60;

include 'sqlconnect.php';

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // prepare sql and bind parameters
    $stmt = $conn->prepare("INSERT INTO location 
    VALUES (:lat,:long)");
    $stmt->bindParam(':lat', $lat);
    $stmt->bindParam(':long', $lng);
    //$stmt->bindParam(':placeid', $placeid);
     
    echo $stmt->queryString;
    echo $stmt->execute();
    
    
    $strCookie = 'PHPSESSID=' . $_COOKIE['PHPSESSID'] . '; path=/';
    session_write_close();
    
    $curl = curl_init();
    
    curl_setopt_array($curl, array(
        CURLOPT_RETURNTRANSFER => 1,
        CURLOPT_URL => 'http://182.237.190.182:8080/LocTrackWeb/putlatlng.php',
        
    ));
    curl_setopt($curl, CURLOPT_COOKIESESSION, true);
    curl_setopt($curl, CURLOPT_COOKIESESSION, true);
    curl_setopt( $curl, CURLOPT_COOKIE, $strCookie );
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    
    $resp = curl_exec($curl);
    echo 'hi';
    echo $resp;
    curl_close($curl);
    
}
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }
$conn = null;
?>