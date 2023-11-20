<?php

$host = 'localhost';
$username = 'root';
$password = '';
$database = 'bd_tesis';
$conn = mysqli_connect($host, $username, $password, $database);

if (!$conn) {
    die("Conexión fallida: " . mysqli_connect_error());
}

$id_usuario = $_GET['id_usuario']; // Recibe el UID desde una solicitud GET, asegúrate de sanear y validar los datos

// Consulta SQL para obtener filas por UID
$sql = "SELECT fecha FROM historial WHERE id_usuario = '$id_usuario'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $dates = array();
    while ($row = $result->fetch_assoc()) {
        $dates[] = $row['fecha'];
    }
    echo json_encode($dates); // Devuelve las fechas como JSON
} else {
    echo "No se encontraron filas con el UID proporcionado.";
}

$conn->close();
?>