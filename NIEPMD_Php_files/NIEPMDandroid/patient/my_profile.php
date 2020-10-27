<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];

$sql_query = "SELECT * FROM users WHERE id = '$id'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result[] = array("fullName"=>$row['fullName'],
        "address"=>$row['address'],
        "city"=>$row['city'],
        "gender"=>$row['gender'],
        "email"=>$row['email'],
        "regDate"=>$row['regDate'],
        "updationDate"=>$row['updationDate']);
}

echo json_encode($result);

mysqli_close($con);
?>