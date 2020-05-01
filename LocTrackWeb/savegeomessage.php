<?php
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
session_start();
$pid=$_POST['placeid'];
$place=$_POST['place'];
$msg=$_POST['message'];
$addr=$_POST['addr'];


include 'sqlconnect.php';

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    //$url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=".$pid."&key=AIzaSyAnPSyj8YymWm1X7zWmXD5UHTzlMWjwNkE";

    // Make the HTTP request
    //$data = @file_get_contents($url);
    // Parse the json response
    //$jsondata = json_decode($data,true);
    
    //print_r($jsondata);
    //$photoref=$jsondata["result"]["reference"];
    
    //echo $photoref;
    
    /*$url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=".$photoref."&key=AIzaSyDFOus2FkIVRktRCg2gMtscciCe44FKdMQ";
    //$url="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyDFOus2FkIVRktRCg2gMtscciCe44FKdMQ";

    echo $url;

    $furl = false;
    // First check response headers
    $headers = get_headers($url);
    
    print_r($headers);
    // Test for 301 or 302
    if(preg_match('/^HTTP\/\d\.\d\s+(301|302)/',$headers[0]))
        {
        foreach($headers as $value)
            {
            if(substr(strtolower($value), 0, 9) == "location:")
                {
                $furl = trim(substr($value, 9, strlen($value)));
                }
            }
        }
    // Set final URL
    $furl = ($furl) ? $furl : $url;
    
    echo $furl;
  
    //echo "<img src='$url' />";*/
    
    //@file_put_contents("map.jpg", $imageContent);
    // prepare sql and bind parameters
    $stmt = $conn->prepare("INSERT INTO geomessage (placeid,place,addr,message) 
    VALUES (:placeid, :place, :addr, :message)");
    $stmt->bindParam(':placeid', $pid);
    $stmt->bindParam(':place', $place);
    $stmt->bindParam(':addr', $addr);
    $stmt->bindParam(':message', $msg);
    //$stmt->bindParam(':url', $furl);
           
    $stmt->execute();
    
}
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }
$conn = null;

?>
