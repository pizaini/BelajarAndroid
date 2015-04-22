<?php
include('connection.php');
$id = (int)$_GET['id'];
$query = 'delete from mahasiswa where id = '.$id;
$result = mysql_query($query) or die(mysql_error());
if(mysql_affected_rows() > 0){
	echo 'deleted id = '.$id;
}else{
	echo 'NOT founnd id = '.$id;
}
?>