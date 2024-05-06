<?php
include 'connection.php';
require("validate.php");

$json = file_get_contents('php://input');
$data = json_decode($json, true);

$studentId=$_POST['studentid'];
$course_idd=$_POST['courseid'];
$course_namee=$_POST['coursename'];
function getUserDetails($conn, $user)
            {
                $query = "SELECT * FROM studentdetails WHERE studentid = '$user'";
                $response = mysqli_query($conn, $query);
                if(mysqli_num_rows($response) > 0)
                {
                    return $response->fetch_assoc();
                }
                return false;
            }

// Get user details
$userDetails = getUserDetails($conn, $studentId);
//Getting Student Name
$studentname=$userDetails['name'];

//saving the data into the data base
$ans="INSERT INTO `enrolatt` (`name`, `studentid`, `courseid`, `coursename`, `studentstatus`,
`attendedclasses`, `totalclasses`, `coursestatus`, `grade`) 
VALUES ('$studentname', '$studentId', '$course_idd', '$course_namee',
'pending', '0', '0', 'ACTIVE', 'NA')";

$resp=mysqli_query($conn,$ans);

$response = array();

if ($resp==TRUE) {
    $response['status'] = true;
    $response['message'] = "Data Extracted Successfully";
    $response['data'] = array(); // Empty array when no data will be transffered
    
} else {
    $response['status'] = false;
    $response['message'] = "The student not registered till now";
    $response['data'] = array(); // Empty array when no data will be transffered
}
header('Content-Type: application/json; charset=UTF-8');
echo json_encode($response);
mysqli_close($conn);

?>
