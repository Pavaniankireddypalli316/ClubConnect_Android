<?php
include 'connection.php';
require("validate.php");

$json = file_get_contents('php://input');
$data = json_decode($json, true);

$userId=$_POST['userId'];
$loginqry = "SELECT * FROM facultydetails WHERE facultyid = '$userId'";
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
<!-- 
    //retrieving the cookie here - created in loginprocess.php
//$userId = $_COOKIE['varus_name']; 
//not used the cookie alter method getting the user id from profile.java file
// $response['data']['Name'] = $userObj['username'];
    // $response['data']['mobileNo'] = $userObj['mobileno'];
    // $response['data']['dateofbirth'] = $userObj['dob'];
    // $response['data']['gemail'] = $userObj['email']; -->