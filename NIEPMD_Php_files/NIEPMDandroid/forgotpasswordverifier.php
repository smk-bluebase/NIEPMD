<?php
include("config.php");

$db = new DB_Connect();
$con = $db->connect();

$otp = $_POST['otp'];
$password = $_POST['password'];

$sql_query = "SELECT * FROM forgotpassword WHERE otp = '".$otp."' LIMIT 1";

$res = mysqli_query($con, $sql_query);

$result = array("status"=>"false");

while($row = mysqli_fetch_array($res)){
    if($row['doctororpatient'] == 0){
        $sql_query = "UPDATE users SET `password` =  '".$password."' WHERE email = '".$row['email']."'";

        $con->query($sql_query);
    }else {
        $sql_query = "UPDATE doctors SET `password` = '".$password."' WHERE docEmail = '".$row['email']."'";

        $con->query($sql_query);
    }

    $sql_query = "DELETE FROM forgotpassword WHERE email = '".$row['email']."'";

    $con->query($sql_query);

    $result = array("status"=>"true");
}

echo json_encode([$result]);

mysqli_close($con);
?>