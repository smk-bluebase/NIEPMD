<?php
include("config.php");

$db = new DB_Connect();
$con = $db->connect();

$otp = $_POST['otp'];

$sql_query = "SELECT * FROM signup WHERE otp = '".$otp."' LIMIT 1";

$res = mysqli_query($con, $sql_query);

$result = array("status"=>"false");

while($row = mysqli_fetch_array($res)){
    if ($row['doctororpatient'] == 0) {
        $sql_query = "DELETE FROM signup WHERE email = '".$row['email']."'";

        $con->query($sql_query);

        $sql_query = "INSERT INTO users (fullName, email, password) VALUES ('".$row['username']."', '".$row['email']."', '".$row['password']."')";

        $con->query($sql_query);

        $result = array("status"=>"true"); 
    }else {
        $sql_query = "DELETE FROM signup WHERE username = '".$row['username']."'";

        $con->query($sql_query);

        $sql_query = "INSERT INTO doctors (doctorName, docEmail, password) VALUES ('".$row['username']."', '".$row['email']."', '".$row['password']."')";

        $con->query($sql_query);

        $result = array("status"=>"true");
    }
}

echo json_encode([$result]);

mysqli_close($con);
?>