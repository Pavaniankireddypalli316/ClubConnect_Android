<?php
include 'connection.php';
require("validate.php");

$json = file_get_contents('php://input');
$data = json_decode($json, true);

$userId=$_POST['userId'];
$loginqry = "SELECT * FROM studentdetails WHERE studentid = '$userId'";
$qry = mysqli_query($conn, $loginqry);

$response = array();

if (mysqli_num_rows($qry) > 0) {
    $userObj = mysqli_fetch_assoc($qry);
    $response['status'] = true;
    $response['message'] = "Data Extracted Successfully";
    $response=$userObj;
} else {
    $response['status'] = false;
    $response['message'] = "Data Extraction Failed";
}

header('Content-Type: application/json; charset=UTF-8');
echo json_encode($response);
mysqli_close($conn);
?>
