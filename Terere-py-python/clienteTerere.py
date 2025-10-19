import sys
import datetime
import configparser
import requests
from requests.structures import CaseInsensitiveDict

# Variables globales para API
api_productos_url_listar = None
api_productos_url_crear = None
api_clientes_url_listar = None
api_clientes_url_crear = None
api_pedidos_url_listar = None
api_pedidos_url_crear = None

archivo_config = 'ConfigFile.properties'

def cargar_variables():
    """Carga las URLs de la API desde el archivo de configuración"""
    config = configparser.RawConfigParser()
    config.read(archivo_config)

    global api_productos_url_listar, api_productos_url_crear
    global api_clientes_url_listar, api_clientes_url_crear
    global api_pedidos_url_listar, api_pedidos_url_crear
    
    api_productos_url_listar = config.get('SeccionApi', 'api_productos_url_listar')
    api_productos_url_crear = config.get('SeccionApi', 'api_productos_url_crear')
    api_clientes_url_listar = config.get('SeccionApi', 'api_clientes_url_listar')
    api_clientes_url_crear = config.get('SeccionApi', 'api_clientes_url_crear')
    api_pedidos_url_listar = config.get('SeccionApi', 'api_pedidos_url_listar')
    api_pedidos_url_crear = config.get('SeccionApi', 'api_pedidos_url_crear')


def obtener_headers():
    """Retorna los headers necesarios para las peticiones"""
    headers = CaseInsensitiveDict()
    headers["Accept"] = "application/json"
    headers["Content-Type"] = "application/json"
    return headers


# ========== OPERACIONES CON PRODUCTOS ==========

def listar_productos():
    """Lista todos los productos disponibles"""
    headers = obtener_headers()
    
    try:
        r = requests.get(api_productos_url_listar, headers=headers, timeout=5)
        if r.status_code == 200:
            productos = r.json()
            print("\n=== LISTADO DE PRODUCTOS ===")
            if not productos:
                print("No hay productos disponibles")
            else:
                for producto in productos:
                    print(f"ID: {producto.get('id')}")
                    print(f"  Nombre: {producto.get('nombre')}")
                    print(f"  Precio: {producto.get('precio')}")
                    print(f"  Categoría: {producto.get('categoria')}")
                    print(f"  Stock: {producto.get('stock')}")
                    print(f"  Descripción: {producto.get('descripcion')}")
                    print("---")
        else:
            print(f"Error {r.status_code}: {r.text}")
    except Exception as e:
        print(f"Error en listar_productos: {str(e)}")


def crear_producto(nombre: str, precio: float, categoria: str, stock: int, descripcion: str = ""):
    """Crea un nuevo producto"""
    headers = obtener_headers()
    
    datos = {
        'nombre': nombre,
        'precio': precio,
        'categoria': categoria,
        'stock': stock,
        'descripcion': descripcion
    }
    
    try:
        r = requests.post(api_productos_url_crear, headers=headers, json=datos, timeout=5)
        if r.status_code == 201:
            print(f"Producto creado exitosamente")
            print(r.json())
        else:
            print(f"Error {r.status_code}: {r.text}")
    except Exception as e:
        print(f"Error en crear_producto: {str(e)}")


# ========== OPERACIONES CON CLIENTES ==========

def listar_clientes():
    """Lista todos los clientes registrados"""
    headers = obtener_headers()
    
    try:
        r = requests.get(api_clientes_url_listar, headers=headers, timeout=5)
        if r.status_code == 200:
            clientes = r.json()
            print("\n=== LISTADO DE CLIENTES ===")
            if not clientes:
                print("No hay clientes registrados")
            else:
                for cliente in clientes:
                    print(f"ID: {cliente.get('id')}")
                    print(f"  Nombre: {cliente.get('nombre')}")
                    print(f"  Email: {cliente.get('email')}")
                    print(f"  Teléfono: {cliente.get('telefono')}")
                    print(f"  Dirección: {cliente.get('direccion')}")
                    print("---")
        else:
            print(f"Error {r.status_code}: {r.text}")
    except Exception as e:
        print(f"Error en listar_clientes: {str(e)}")


def crear_cliente(nombre: str, email: str, telefono: str, direccion: str):
    """Crea un nuevo cliente"""
    headers = obtener_headers()
    
    datos = {
        'nombre': nombre,
        'email': email,
        'telefono': telefono,
        'direccion': direccion
    }
    
    try:
        r = requests.post(api_clientes_url_crear, headers=headers, json=datos, timeout=5)
        if r.status_code == 201:
            print(f"Cliente creado exitosamente")
            print(r.json())
        elif r.status_code == 400:
            print(f"Error: {r.json().get('mensaje', 'Email ya registrado')}")
        else:
            print(f"Error {r.status_code}: {r.text}")
    except Exception as e:
        print(f"Error en crear_cliente: {str(e)}")


# ========== OPERACIONES CON PEDIDOS ==========

def listar_pedidos():
    """Lista todos los pedidos"""
    headers = obtener_headers()
    
    try:
        r = requests.get(api_pedidos_url_listar, headers=headers, timeout=5)
        if r.status_code == 200:
            pedidos = r.json()
            print("\n=== LISTADO DE PEDIDOS ===")
            if not pedidos:
                print("No hay pedidos registrados")
            else:
                for pedido in pedidos:
                    print(f"ID: {pedido.get('id')}")
                    print(f"  Cliente ID: {pedido.get('clienteId')}")
                    print(f"  Total: {pedido.get('total')}")
                    print(f"  Estado: {pedido.get('estado')}")
                    print("---")
        else:
            print(f"Error {r.status_code}: {r.text}")
    except Exception as e:
        print(f"Error en listar_pedidos: {str(e)}")


def crear_pedido(cliente_id: int, productos_ids: list):
    """Crea un nuevo pedido"""
    headers = obtener_headers()
    
    datos = {
        'clienteId': cliente_id,
        'productosIds': productos_ids
    }
    
    try:
        r = requests.post(api_pedidos_url_crear, headers=headers, json=datos, timeout=5)
        if r.status_code == 201:
            print(f"Pedido creado exitosamente")
            print(r.json())
        else:
            print(f"Error {r.status_code}: {r.text}")
    except Exception as e:
        print(f"Error en crear_pedido: {str(e)}")


# ========== MENU PRINCIPAL ==========

def mostrar_menu():
    """Muestra el menú principal"""
    print("\n" + "="*40)
    print("       CLIENTE TERERÉ-PY")
    print("="*40)
    print("1. Listar Productos")
    print("2. Crear Producto")
    print("3. Listar Clientes")
    print("4. Crear Cliente")
    print("5. Listar Pedidos")
    print("6. Crear Pedido")
    print("0. Salir")
    print("="*40)


def main():
    """Función principal"""
    print("Iniciando Cliente Tereré-PY " + datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    cargar_variables()
    
    while True:
        mostrar_menu()
        opcion = input("Seleccione una opción: ")
        
        if opcion == "1":
            listar_productos()
        
        elif opcion == "2":
            print("\n--- Crear Producto ---")
            nombre = input("Nombre: ")
            precio = float(input("Precio: "))
            categoria = input("Categoría: ")
            stock = int(input("Stock: "))
            descripcion = input("Descripción (opcional): ")
            crear_producto(nombre, precio, categoria, stock, descripcion)
        
        elif opcion == "3":
            listar_clientes()
        
        elif opcion == "4":
            print("\n--- Crear Cliente ---")
            nombre = input("Nombre: ")
            email = input("Email: ")
            telefono = input("Teléfono: ")
            direccion = input("Dirección: ")
            crear_cliente(nombre, email, telefono, direccion)
        
        elif opcion == "5":
            listar_pedidos()
        
        elif opcion == "6":
            print("\n--- Crear Pedido ---")
            cliente_id = int(input("ID del Cliente: "))
            productos_str = input("IDs de Productos (separados por comas): ")
            productos_ids = [int(x.strip()) for x in productos_str.split(",")]
            crear_pedido(cliente_id, productos_ids)
        
        elif opcion == "0":
            print("Finalizando " + datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
            sys.exit(0)
        
        else:
            print("Opción inválida. Intente nuevamente.")


if __name__ == "__main__":
    main()