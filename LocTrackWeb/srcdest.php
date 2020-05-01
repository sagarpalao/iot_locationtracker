<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
session_start();
$src=$_POST['src'];
$dest=$_POST['dest'];


include 'sqlconnect.php';

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // prepare sql and bind parameters
    $stmt = $conn->prepare("select bus_no from busstops where stop=:src and bus_no IN (select bus_no from busstops where stop=:dest)");
    $stmt->bindParam(":src",$src);
    $stmt->bindParam(":dest",$dest);
    $stmt->execute();

    $result = $stmt->fetchAll();
    
    foreach($result as $row){
        
    }
    if(isset($row['bus_no'])){
         echo json_encode(array("top" =>$row['bus_no']));
    }
   else{
        echo json_encode(array("top" =>"no"));
   }
    
}
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }
$conn = null;
?>