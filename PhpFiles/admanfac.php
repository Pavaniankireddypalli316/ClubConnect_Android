<?php
include 'connection.php';
require("validate.php");

$json = file_get_contents('php://input');
$data = json_decode($json, true);

$loginqry = "SELECT * FROM facultydetails";
$qry = mysqli_query($conn, $loginqry);

$response = array();

if (mysqli_num_rows($qry) > 0) {
    $userObj = mysqli_fetch_assoc($qry);
    $response['status'] = true;
    $response['message'] = "Data Extracted Successfully";
    $response['data'][]= $userObj; // Place the user object inside an array

    while ($userObj = mysqli_fetch_assoc($qry)) {
        // Add each fetched record to the 'data' array
        $response['data'][] = $userObj;
    }
} else {
    $response['status'] = false;
    $response['message'] = "Data Extraction Failed";
    $response['data'] = array(); // Empty array when no data is found
}
header('Content-Type: application/json; charset=UTF-8');
echo json_encode($response);
mysqli_close($conn);

?>
