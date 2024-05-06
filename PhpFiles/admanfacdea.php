<?php
include 'connection.php'; // Ensure you have the database connection
require("validate.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Check if the 'username' and 'password' keys are set in $_GET
    if (isset($_POST['id'])) {
        $username = validate($_POST['id']);
        //2.create some random integer value to set as password
    $randomNumber = rand(1, 100000000); // Generates a random number between 1 and 100,000,000
    //3. Create SQL Query to Delete student
    $sql = "UPDATE facultydetails SET password='$randomNumber',name='DEACTIVATED' WHERE facultyid='$username'";
    
        $result = $conn->query($sql);

        if ($result==true) {
            // Login successful
            $response = array('success' => true);
            echo json_encode($response);
        } else {
            // Login failed
            $response = array('success' => false);
            echo json_encode($response);
        }
    } else {
        // Handle the case when 'username' or 'password' is not set in $_GET
        $response = array('success' => false, 'error' => 'Username or password not provided');
        echo json_encode($response);
    }
}
?>