<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

session_start();
$place=$_POST['place'];
$addr=$_POST['addr'];
$placeid=$_POST['placeid'];

include 'sqlconnect.php';

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // prepare sql and bind parameters
    $stmt = $conn->prepare("INSERT INTO noloc (place,addr,placeid) 
    VALUES (:place, :addr, :placeid)");
    $stmt->bindParam(':place', $place);
    $stmt->bindParam(':addr', $addr);
    $stmt->bindParam(':placeid', $placeid);
            
    $stmt->execute();
}
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }
$conn = null;
?>