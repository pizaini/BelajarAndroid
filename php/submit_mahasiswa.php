<?php
include('connection.php');

$id = (int)$_POST['id'];
$nim = $_POST['nim'];
$nama = $_POST['nama'];
$telp = $_POST['telp'];
$alamat = $_POST['alamat'];

$query = 'insert into mahasiswa (nim, nama, telp, alamat) values ("'.$nim.'", "'.$nama.'", "'.$telp.'", "'.$alamat.'")';
if($id > 0){
	$query = 'update mahasiswa set nama = "'.$nama.'", nim = "'.$nim.'", telp = "'.$telp.'", alamat = "'.$alamat.'" where id = '.$id;
}
mysql_query($query) or die(mysql_error());

?>