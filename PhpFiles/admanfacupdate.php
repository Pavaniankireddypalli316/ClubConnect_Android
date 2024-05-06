<?php
// Include the database connection file (replace with your actual database connection code)
include 'connection.php';
require("validate.php");

// Check if the request is a POST request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get the JSON data from the request body
    $json_data = file_get_contents("php://input");

    // Parse the JSON data into an associative array
    $data = json_decode($json_data, true);

    // Check if the JSON data was successfully parsed
    if ($data !== null) {
        // Extract data from the JSON
        $field0 = $data['field0'];
        $field1 = $data['field1'];
        $field2 = $data['field2'];
        $field3 = $data['field3'];
        $field4 = $data['field4'];
        $field5 = $data['field5'];

        // Perform the database update (replace with your database update code)
        $sql = "UPDATE facultydetails SET name='$field0', contact = '$field1', dob = '$field2',email='$field3',address='$field4' where facultyid='$field5' ";
        $stmt=mysqli_query($conn, $sql);
        echo json_encode(array("status"=> "success","message"=> "Data updated succesfully"));

    } else {
        $response = array('message' => 'Invalid JSON data');
        echo json_encode($response);
        
    }
} else {
    $response = array('message' => 'Invalid request method');
    echo json_encode($response);
}
?>
