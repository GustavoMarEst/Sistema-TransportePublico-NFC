<?php
// Establecer la conexión con la base de datos
$host = 'localhost';
$username = 'root';
$password = '';
$database = 'bd_tesis';
$conn = mysqli_connect($host, $username, $password, $database);

if (!$conn) {
    die("Conexión fallida: " . mysqli_connect_error());
}

$id = $_GET['id'];

$sql = "SELECT credito, fecha FROM usuario WHERE id = '$id'";

$resultado = mysqli_query($conn, $sql);
$array_resultado = array();

if (mysqli_num_rows($resultado) > 0) {
    while($fila = mysqli_fetch_assoc($resultado)) {
        $array_resultado[] = $fila;
    }
}

$json_resultado = json_encode($array_resultado);

echo $json_resultado;

mysqli_close($conn);
?>
