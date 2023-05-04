import pika, sys
mensaje=""
tipo="gerencia"
if len(sys.argv)>1:
	tipo = sys.argv[1]
if len(sys.argv)>2:
	mensaje = " ".join(sys.argv[2:])
conexion=pika.BlockingConnection(
	pika.ConnectionParameters(host='localhost'))
""" contexto = ssl.create_default_context(cafile="C:/certs/autcert/cacert.pem");
contexto.load_cert_chain("C:/certs/cliente/cert.pem","C:/certs/cliente/privatekey.pem")
ssl_opciones = pika.SSLOptions(contexto, "DESKTOP-IFU5E67");
conexion = pika.BlockingConnection( #Espera parametros que tienen que estar en objeto tipo
    pika.ConnectionParameters(
        port=5671,
        ssl_options=ssl_opciones
    )); """
canal=conexion.channel()
canal.exchange_declare(exchange="mensajes",
	exchange_type="direct")
canal.basic_publish(exchange='mensajes',
	routing_key=tipo,
	body=mensaje)
print(mensaje);
conexion.close()
