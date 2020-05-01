<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

session_start();
$l=array(
    array(1914.257735,7309.703963),
    array(19.076220,72.877625),
    array(19.076666,72.877926),
    array(19.076889,72.878119),
    array(19.077072,72.878312),
    array(19.085434,72.887391),
    array(19.0511106,72.8906849)
);


for($i=0;$i<1;$i++){
    sleep(1);
        // Get cURL resource
    echo $l[$i][0].' '.$l[$i][1].'<br>';
    
    $strCookie = 'PHPSESSID=' . $_COOKIE['PHPSESSID'] . '; path=/';
    session_write_close();
    $curl = curl_init();
    
    curl_setopt_array($curl, array(
        CURLOPT_RETURNTRANSFER => 1,
        CURLOPT_URL => 'http://182.237.177.0/LocTrackWeb/addloc.php?lat='.$l[$i][0].'&lng='.$l[$i][1]       
    ));
    curl_setopt($curl, CURLOPT_COOKIESESSION, true);
    curl_setopt( $curl, CURLOPT_COOKIE, $strCookie );
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1); 

    $resp = curl_exec($curl);
    // Close request to clear up some resources
    echo $resp;
    curl_close($curl);
}
?>