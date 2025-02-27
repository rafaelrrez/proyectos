<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\User;
use Illuminate\Support\Facades\Hash;
use App\Models\Productos;
use Illuminate\Support\Str;

class AuthController extends Controller
{
    /**
     * Muestra la vista de usuarios logados o el formulario de inicio de sesión.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        // Comprueba si el usuario ya está logado
        if (Auth::check()) {
            $productos = Productos::take(8)->get();

            // Si está logado, redirige a la vista de usuarios logados
            return redirect()->route('logados', compact('productos'));
        }

        $productos = Productos::take(8)->get();

        // Si no está logado, muestra la vista con el formulario de inicio de sesión
        return view('welcome', compact('productos'));
    }

    /**
     * Muestra el formulario de inicio de sesión o registro.
     *
     * @return \Illuminate\Http\Response
     */
    public function showLoginSignupForm()
    {
        // Comprueba si el usuario ya está logado
        if (Auth::check()) {
            $productos = Productos::take(8)->get();

            // Si está logado, redirige a la vista de usuarios logados
            return redirect()->route('logados', compact('productos'));
        }

        // Si no está logado, muestra la vista con el formulario de inicio de sesión y registro
        return view('login_signup');
    }

    /**
     * Procesa el formulario de inicio de sesión.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function login(Request $request)
    {
        // Valida los datos del formulario
        $request->validate([
            'email' => 'required|email',
            'password' => 'required',
        ], [
            'email.required' => 'El campo email es obligatorio.',
            'email.email' => 'Por favor, introduce un email válido.',
            'password.required' => 'El campo contraseña es obligatorio.',
        ]);

        // Almacena las credenciales de email y contraseña
        $credentials = $request->only('email', 'password');

        // Intenta autenticar al usuario
        if (Auth::attempt($credentials)) {
            $productos = Productos::take(8)->get();
            return redirect()->route('logados', compact('productos'))->withSuccess('Logado Correctamente');
        }

        // Si la autenticación falla, redirige al usuario al formulario de inicio de sesión con un mensaje de error
        return redirect()->route('login-signup')->withError('Los datos introducidos no son correctos');
    }

    /**
     * Muestra la vista de usuarios logados.
     *
     * @return \Illuminate\Http\Response
     */
    public function logados()
    {
        if (Auth::check()) {
            $productos = Productos::take(8)->get();
            return view('logados', compact('productos'));
        }

        return redirect("/")->withSuccess('No tienes acceso, por favor inicia sesión');
    }

    /**
     * Cierra la sesión del usuario.
     *
     * @return \Illuminate\Http\Response
     */
    public function logout()
    {
        Auth::logout();
        return redirect('/');
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function registerUser(Request $request)
    {
        $request->validate([
            'name_register' => ['required', 'string', 'max:255'],
            'surname_register' => ['required', 'string', 'max:255'],
            'username_register' => ['required', 'string', 'max:255', 'unique:users,username'],
            'email_register' => ['required', 'email', 'unique:users,email', 'max:250'],
            'password_register' => ['required', 'min:8', 'confirmed'],
            'password_register_confirmation' => ['required'],
        ], [
            'name_register.required' => 'The name field is required.',
            'surname_register.required' => 'The surname field is required.',
            'username_register.required' => 'The username field is required.',
            'username_register.unique' => 'The username is already in use.',
            'email_register.required' => 'The email field is required.',
            'email_register.unique' => 'The email is already in use.',
            'password_register.required' => 'The password field is required.',
            'password_register.min' => 'The password must be at least 8 characters.',
            'password_register.confirmed' => 'The password confirmation does not match.',
            'password_register_confirmation.required' => 'The password confirmation field is required.',
        ]);

        // Crea un nuevo usuario con los datos proporcionados
        $user = User::create([
            'name' => $request->name_register,
            'surname' => $request->surname_register,
            'username' => $request->username_register,
            'email' => $request->email_register,
            'password' => Hash::make($request->password_register),
            'remember_token' => Str::random(10),
        ]);

        Auth::login($user);

        // Redirige a la vista de usuarios logados con un mensaje de éxito
        $productos = Productos::take(8)->get();
        return redirect()->route('logados', compact('productos'));
    }

    /**
     * Muestra la vista de productos.
     *
     * @return \Illuminate\Http\Response
     */
    public function shop()
    {
        $productos = Productos::paginate(2);
        return view('shop', compact('productos'));
    }
}
