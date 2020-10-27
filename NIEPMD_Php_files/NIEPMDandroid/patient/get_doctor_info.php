<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$sql_query = "SELECT specilization FROM doctorspecilization";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result['specialization'][] = $row[0];
}

if(!empty($result)){
    $sql_query = "SELECT id, doctorName, specilization, docFees FROM doctors";

    $res = mysqli_query($con, $sql_query);

    while($row = mysqli_fetch_array($res)){
        $result['doctors'][] = array("id"=>$row['id'], "doctorName"=>$row['doctorName'], "specialization"=>$row['specilization'], "docFees"=>$row['docFees']);
    }
}

echo json_encode([$result]);

mysqli_close($con);
?>