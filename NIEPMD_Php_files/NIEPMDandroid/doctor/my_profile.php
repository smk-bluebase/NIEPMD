<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];

$sql_query = "SELECT * FROM doctors WHERE id = '".$id."'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result['doctorDetails'] = array("doctorName"=>$row['doctorName'],
        "doctorSpecialization"=>$row['specilization'],
        "docEmail"=>$row['docEmail'],
        "address"=>$row['address'],
        "consultancyFees"=>$row['docFees'],
        "contactNo"=>$row['contactno']);
}

$sql_query = "SELECT specilization FROM doctorspecilization";

$res = mysqli_query($con, $sql_query);

while($row = mysqli_fetch_array($res)){
    $result['specialization'][] = $row['specilization'];
}

echo json_encode([$result]);

mysqli_close($con);
?>