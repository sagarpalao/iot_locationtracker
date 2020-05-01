<?php

/* 
* Given longitude and latitude in North America, return the address using The Google Geocoding API V3
*
*/
//session_start();
function getbounds($addr) {
    
$addr=  urlencode($addr);

$url = "http://maps.google.com/maps/api/geocode/json?address=$addr";

// Make the HTTP request
$data = @file_get_contents($url);
// Parse the json response
$jsondata = json_decode($data,true);


//print_r($jsondata);

// If the json data is invalid, return empty array
if (!check_status($jsondata))   return array();

$address = array(
    'ne_lat' => $jsondata["results"][0]["geometry"]["bounds"]["northeast"]["lat"]+0.002,
    'ne_long' => $jsondata["results"][0]["geometry"]["bounds"]["northeast"]["lng"]+0.002,
    'sw_lat' => $jsondata["results"][0]["geometry"]["bounds"]["southwest"]["lat"]-0.002,
    'sw_long' => $jsondata["results"][0]["geometry"]["bounds"]["southwest"]["lng"]-0.002,
);

return $address;
}

function check_status($jsondata) {
    if ($jsondata["status"] == "OK") return true;
    return false;
}

