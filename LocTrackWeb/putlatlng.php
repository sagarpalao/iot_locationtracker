<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

session_start();

include 'PolyTest.php';
include 'AddressTest.php';
include_once './GCM.php';

include 'sqlconnect.php';


try {
    
    $GLOBALS['alert_msg']=true;
    $GLOBALS['alert_bound']=true;
    $GLOBALS['alert_freeze']=true;
    
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // prepare sql and bind parameters
    $stmt = $conn->prepare("select * from location");
    $stmt->execute();
    
    $res=$stmt->fetchAll();
    
    
    foreach($res as $row){
        
    }
    $lat=$row['lat'];
    $long=$row['long'];
    
    $point=$lat.' '.$long;
    
    $stmt = $conn->prepare("select * from poly");
    $stmt->execute();
    
    $res=$stmt->fetchAll();
    $poly=array();
    
    $i=0;
    foreach($res as $row){
        $poly[$i]=$row['lat'].' '.$row['lng'];
        $i++;
    }
    
    $stmt = $conn->prepare("select * from register");
    $stmt->execute();

    $res=$stmt->fetchAll();

    foreach($res as $row){
        $regId = $row['regid'];
    }
    
    $stmt = $conn->prepare("select * from session");
    $stmt->execute();

    $res=$stmt->fetchAll();

    foreach($res as $row){
        $GLOBALS['alert_msg']=$row['alert_msg'];
        $GLOBALS['alert_bound']=$row['alert_bound'];
        $GLOBALS['alert_freeze']=$row['alert_freeze'];
    }
    
    $stmt = $conn->prepare("select * from preference");
    $stmt->execute();
    $res=$stmt->fetch(PDO::FETCH_ASSOC);
    $pref=$res;    
    $stmt = $conn->prepare("select * from freeze");
    $stmt->execute();
    $res=$stmt->fetch(PDO::FETCH_ASSOC);
    $freeze=$res;     
    $pointLocation = new pointLocation();   
    $stat=$pointLocation->pointInPolygon($point, $poly); 
    
    $gcm = new GCM();
    $msg = "location";
    $registatoin_ids = array($regId);
    $message = array("message" => $msg);

    $result = $gcm->send_notification($registatoin_ids, $message);              
    
    if($stat=='inside'){
        $GLOBALS['alert_bound']=true;
    }
    if($stat=='outside'){
        if($pref['boundary']==1){            
            echo $regId;
            $msg = "outside";

            $gcm = new GCM();

            $registatoin_ids = array($regId);
            $message = array("message" => $msg);

            if($GLOBALS['alert_bound']==true){
            $result = $gcm->send_notification($registatoin_ids, $message);
            $GLOBALS['alert_bound']=false;
            }
            echo $result;
        }
    }
    
        if($pref['fence']==1){
            $send=false;
            $stmt = $conn->prepare("select * from yesloc");
            $stmt->execute();
            echo "there";

            $resyes=$stmt->fetchAll();

            $stmt = $conn->prepare("select * from noloc");
            $stmt->execute();

            $resno=$stmt->fetchAll();


            $send=true;

            foreach($resyes as $row){      
               $bound=getbounds($row['addr']);
               print_r($bound);
               echo  '<br><br>';
               if($lat>=$bound['sw_lat'] && $long>=$bound['sw_long'] && $lat<=$bound['ne_lat'] && $long<=$bound['ne_long']){
                   $send=false;
                   break;
               }  
            }

            echo $send;

            if($send==false){
                foreach($resno as $row){      
                    $bound=getbounds($row['addr']);
                    if($lat>=$bound['sw_lat'] && $long>=$bound['sw_long'] && $lat<=$bound['ne_lat'] && $long<=$bound['ne_long']){
                        echo 'i a no';
                        $send=true;
                        break;
                    }  
                }     
            }

            if($send==true){
                echo 'i a no';
                echo $regId;
                $msg = "outside";
                echo '<br><br>okay '.$GLOBALS['alert_bound'].'<br><br>i m here';

                $gcm = new GCM();

                $registatoin_ids = array($regId);
                $message = array("message" => $msg);
                echo $GLOBALS['alert_bound'];
                if($GLOBALS['alert_bound']==true){
                $result = $gcm->send_notification($registatoin_ids, $message);
                $GLOBALS['alert_bound']=false;
                }
                echo $result;
            }
            else{
                 $GLOBALS['alert_bound']=true;
            }
        }
    
    
    if($pref['freeze']==1){
        if($lat!=$freeze['lat'] || $long!=$freeze['lng']){
            echo $regId;
                $msg = "moving";

                $gcm = new GCM();

                $registatoin_ids = array($regId);
                $message = array("message" => $msg);

                if($GLOBALS['alert_freeze']==true){
                    $result = $gcm->send_notification($registatoin_ids, $message);
                    $GLOBALS['alert_freeze']=false;
                }

                //echo $result;
        }
        else{
             $GLOBALS['alert_freeze']=true;
        }
    }
    
    if($pref['message']==1){
        $send=false;
            $stmt = $conn->prepare("select * from geomessage");
            $stmt->execute();
            $resyes=$stmt->fetchAll();
            
            foreach($resyes as $row){ 
               echo 'der';
               $bound=getbounds($row['addr']);
               print_r($bound);
               echo  '<br><br>';
               if($lat>=$bound['sw_lat'] && $long>=$bound['sw_long'] && $lat<=$bound['ne_lat'] && $long<=$bound['ne_long']){
                   echo 'cool';
                   $send=true;
                   break;
               }
          
            }
            echo $send;
            if($send==true){
                $msg=$row['placeid'];
                $gcm = new GCM();

                $registatoin_ids = array($regId);
                $message = array("message" => $msg);
                
                if($GLOBALS['alert_msg']==true){
                    $result = $gcm->send_notification($registatoin_ids, $message);
                    $GLOBALS['alert_msg']=false;
                }
                //echo $result;
            }
            else{
                 $GLOBALS['alert_msg']=true;
            }
    }
    
    $stmt = $conn->prepare("update session set alert_bound=:bound , alert_msg=:msg , alert_freeze=:freeze");
    $stmt->bindParam(':bound', $GLOBALS['alert_bound']);
    $stmt->bindParam(':msg', $GLOBALS['alert_msg']);
    $stmt->bindParam(':freeze', $GLOBALS['alert_freeze']);
     
    //echo $stmt->queryString;
    echo $stmt->execute();
              
}
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }

?>

