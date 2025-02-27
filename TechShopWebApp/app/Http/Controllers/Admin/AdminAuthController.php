<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Session;

class AdminAuthController extends Controller
{
    /**
     * Muestra el formulario de inicio de sesión para el administrador.
     * 
     * @return \Illuminate\Http\Response
     */
    public function getLogin(){
        $user = auth()->guard('admin')->user();

        // Verifica si el usuario es administrador y lo redirige a la página de productos,
        // de lo contrario, muestra el formulario de inicio de sesión.
        if ($user && $user->is_admin == 1) {
            return redirect()->route('admin/productos')->with('success', 'You are logged in successfully.');
        } else {
            return view('admin.auth.login');
        }
    }

    /**
     * Procesa el formulario de inicio de sesión para el administrador.
     * 
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function postLogin(Request $request)
    {
        // Validación de los datos del formulario.
        $this->validate($request, [
            'email' => 'required|email',
            'password' => ['required', 'min:8'],
        ], [
            'email.required' => 'The email field is required.',
            'password.required' => 'The password field is required.',
            'password.min' => 'The password must be at least 8 characters.',
        ]);

        // Intenta autenticar al administrador con las credenciales proporcionadas.
        if(auth()->guard('admin')->attempt(['email' => $request->input('email'), 'password' => $request->input('password')])) {
            $user = auth()->guard('admin')->user();
            
            // Verifica si el usuario autenticado es un administrador y lo redirige a la página de productos.
            if($user->is_admin == 1){
                return redirect()->route('admin/productos')->with('success','You are Logged in sucessfully.');
            }
        } else {
            // Si la autenticación falla, redirige de nuevo al formulario de inicio de sesión con un mensaje de error.
            return redirect()->route('adminLogin')->with('error_message', 'The input data is invalid.');
        }
    }

    /**
     * Cierra la sesión del administrador.
     * 
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function adminLogout(Request $request)
    {
        // Cierra la sesión del administrador.
        auth()->guard('admin')->logout();
        
        // Limpia la sesión y establece un mensaje de éxito.
        Session::flush();
        Session::put('success_message', 'You are logout sucessfully');
        
        // Redirige al formulario de inicio de sesión del administrador.
        return redirect(route('adminLogin'));
    }
}