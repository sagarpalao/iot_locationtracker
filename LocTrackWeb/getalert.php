<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
session_start();
$placeid=$_GET['pid'];
//$placeid="ChIJj7q7Oj635zsRFuC_tjGQDL8";

include 'sqlconnect.php';

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // prepare sql and bind parameters
    $stmt = $conn->prepare("select * from geomessage where placeid=:pid");
    $stmt->bindParam(':pid', $placeid);
    $stmt->execute();

    $result = $stmt->fetchAll();
    echo json_encode(array("top" =>$result));
    
}
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }
$conn = null;
?>