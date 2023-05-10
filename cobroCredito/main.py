import MySQLdb
import serial
from datetime import datetime

conexion = MySQLdb.connect(
    host="localhost",
    user="root",
    passwd="",
    db="bd_tesis"
)

cursor = conexion.cursor()
puerto = serial.Serial('COM3', 9600)

while True:
    datos = puerto.readline().decode().strip()

    if datos:
        consulta = f"SELECT id, nombre, credito FROM usuario WHERE uid = '{datos}'"
        cursor.execute(consulta)
        resultado = cursor.fetchone()

        if resultado is None:
            print(f"Error: La UID {datos} no está registrada en la base de datos.")
        else:
            id_usuario = resultado[0]
            nombre_usuario = resultado[1]
            credito = resultado[2]
            nuevo_credito = credito - 9
            fecha = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            consulta = f"UPDATE usuario SET credito = {nuevo_credito}, fecha = '{fecha}' WHERE uid = '{datos}'"
            cursor.execute(consulta)
            conexion.commit()
            print(f"Se realizo el cobro al usuario {nombre_usuario} con ID {id_usuario}, credito actual: {nuevo_credito}, fecha: {fecha}")

cursor.close()
conexion.close()