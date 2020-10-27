<?php
define("DB_HOST", "localhost");
define("DB_USER", "root");
define("DB_PASSWORD", "");
define("DB_DATABASE", "hms");

class DB_Connect{
 
    // Connecting to database
    public function connect() {        
        // connecting to mysql
        $con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
 
        // return database handler
        return $con;
    }
 
}

?>