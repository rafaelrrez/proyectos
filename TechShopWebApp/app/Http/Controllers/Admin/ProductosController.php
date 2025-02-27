<?php

namespace App\Http\Controllers\Admin;

use Illuminate\Http\Request;
use App\Models\Productos;
use Session;
use Redirect;
use App\Http\Controllers\Controller;
use App\Http\Requests\ItemCreateRequest;
use App\Http\Requests\ItemUpdateRequest;
use App\Enums\Category;
use Cart;
use Storage;
use DateTime;

class ProductosController extends Controller
{
    // Vista para crear un registro
    public function crear()
    {
        $productos = Productos::take(8)->get();
        $categorias = Category::cases();

        return view('admin.productos.crear', compact('productos', 'categorias'));
    }

    // Vista de detalle del producto en la tienda
    public function showDetails($id)
    {
        $producto = Productos::find($id);
        return view('detail', compact('producto'));
    }

    // Proceso de Creación de un Registro
    public function store(ItemCreateRequest $request)
    {
        // Instanciar el modelo Productos que hace referencia a la tabla 'productos' en la Base de Datos
        $productos = new Productos;

        // Recibir todos los datos del formulario de la vista 'crear.blade.php'
        $productos->nombre = $request->nombre;
        $productos->marca = $request->marca;
        $productos->descripcion = $request->descripcion;
        $productos->categoria = $request->categoria;
        $productos->precio = $request->precio;
        $productos->stock = $request->stock;

        // Almacenar la imagen en la carpeta pública específica
        $productos->img = $request->file('img')->store('/');

        // Guardar la fecha de creación del registro
        $productos->created_at = (new DateTime)->getTimestamp();

        // Insertar todos los datos en la tabla 'productos'
        $productos->save();

        // Redireccionar a la vista principal con un mensaje
        return redirect('admin/productos')->with('message', 'Guardado Satisfactoriamente !');
    }

    // Vista de detalle del producto en el panel de administración
    public function show($id)
    {
        $productos = Productos::find($id);
        return view('admin.productos.detalles', compact('productos'));
    }

    // Vista para actualizar un registro
    public function actualizar($id)
    {
        $productos = Productos::find($id);
        $categorias = Category::cases();
        return view('admin/productos.actualizar', compact('productos', 'categorias'));
    }

    // Actualizar un registro
    public function update(ItemUpdateRequest $request, $id)
    {
        // Recibir todos los datos desde el formulario de actualización
        $productos = Productos::find($id);
        $productos->nombre = $request->nombre;
        $productos->marca = $request->marca;
        $productos->descripcion = $request->descripcion;
        $productos->categoria = $request->categoria;
        $productos->precio = $request->precio;
        $productos->stock = $request->stock;

        // Recibir la imagen desde el formulario de actualización
        if ($request->hasFile('img')) {
            $productos->img = $request->file('img')->store('/');
        }

        // Guardar la fecha de actualización del registro
        $productos->updated_at = (new DateTime)->getTimestamp();

        // Actualizar los datos en la tabla 'productos'
        $productos->save();

        // Mostrar un mensaje y redireccionar a la vista principal
        Session::flash('message', 'Editado Satisfactoriamente !');
        return Redirect::to('admin/productos');
    }

    // Eliminar un Registro
    public function eliminar($id)
    {
        // Indicar el 'id' del registro que se va a eliminar
        $productos = Productos::find($id);

        // Eliminar la imagen de la carpeta 'uploads'
        $imagen = explode(",", $productos->img);
        Storage::delete($imagen);

        // Eliminar el registro de la tabla 'productos'
        Productos::destroy($id);

        // Mostrar un mensaje y redireccionar a la vista principal
        Session::flash('message', 'Eliminado Satisfactoriamente !');
        return Redirect::to('admin/productos');
    }

    // Listar todos los productos en la vista principal
    public function index()
    {
        $productos = Productos::all();
        return view('admin.productos.index', compact('productos'));
    }

    // Agregar un producto al carrito
    public function buy(Request $request)
    {
        $producto_id = $request->input('producto_id');
        $producto_nombre = $request->input('producto_nombre');
        $producto_precio = $request->input('producto_precio');
        $producto_cantidad = $request->input('producto_cantidad');
        $producto_img = $request->input('producto_img');

        Cart::add($producto_id, $producto_nombre, $producto_cantidad, $producto_precio, ['img' => $producto_img])->associate('\App\Models\Productos');
        session()->flash('success_message', 'Item added to Cart');
        return redirect()->route('cart');
    }

    // Mostrar productos por categoría en la vista de la tienda
    public function shopCategory($category = null)
    {
        $query = Productos::query();

        // Si se proporciona una categoría, filtrar por esa categoría
        if ($category) {
            $query->where('categoria', $category);
        }

        $productos = $query->paginate(2);

        return view('shop', compact('productos'));
    }
}
