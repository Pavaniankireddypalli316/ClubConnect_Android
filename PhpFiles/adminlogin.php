<?php
include 'connection.php'; // Ensure you have the database connection
require("validate.php");
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = validate($_POST['username']);
    $password =validate($_POST['password']);

    // Perform your login verification against the database
    // Example: Check if username and password match a record in the database
    // IMPORTANT: Use prepared statements for security

    // Example query (replace with your actual query and connection code)
    $sql = "SELECT * FROM admin WHERE username='$username' AND Password='$password'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // Login successful
        $response = array('success' =>true);
        echo json_encode($response);
    } else {
        // Login failed
      $response = array('success' =>false);
        echo json_encode($response);
    }
}
?>