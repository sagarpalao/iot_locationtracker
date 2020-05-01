<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
session_start();
$pref=$_POST['pref'];
$value=$_POST['value'];

include 'sqlconnect.php';

$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // prepare sql and bind parameters
    
if($pref=='msg'){
    if($value=='true'){
        $stmt = $conn->prepare("update preference set message=1 ");
        $stmt->execute();
    }
    else{
        $stmt = $conn->prepare("update preference set message=0 ");   
        $stmt->execute();
    }
}
else if($pref=='fence'){
    if($value=='true'){  
        $stmt = $conn->prepare("update preference set fence=1 ");   
        $stmt->execute();
    }
    else{    
        $stmt = $conn->prepare("update preference set fence=0 ");
        $stmt->execute();
        
    }
}
else if($pref=='bound'){
    if($value=='true'){  
        $stmt = $conn->prepare("update preference set boundary=1 ");
        $stmt->execute();
    }
    else{    
        $stmt = $conn->prepare("update preference set boundary=0 ");
        $stmt->execute();
    }
}
else if($pref=='freeze'){
    if($value=='true'){  
        $stmt = $conn->prepare("update preference set freeze=1 ");
        $stmt->execute();
        
        $stmt2 = $conn->prepare("select * from location");
        $stmt2->execute();
        $res=$stmt2->fetchAll();
        
        foreach($res as $row){}
        
        $stmt2 = $conn->prepare("delete from freeze");
        $stmt2->execute();
        
        $stmt2 = $conn->prepare("insert into freeze(lat,lng) values(:lat,:lng) ");
        $stmt2->bindParam(":lat",$row['lat']);
        $stmt2->bindParam(":lng",$row['long']);
        $stmt2->execute();
    }
    else{    
        $stmt = $conn->prepare("update preference set freeze=0 ");
        $stmt->execute();
        $_SESSION['alert_freeze']=true;
    }
}
   
      

?>

