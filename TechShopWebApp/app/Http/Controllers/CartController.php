<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;
use Cart;


class CartController extends Controller
{

     public function cart()
    {
        return view('cart'); 
    }

    public function removeProductCart($rowId) 
    {
        Cart::remove($rowId);
    
        return redirect()->route('cart')->with('success_message', 'Product removed from cart successfully');
    }

    public function checkout()
    {
        // Verifica si el usuario está autenticado
        if (Auth::check()) {

            // Ejemplo de almacenamiento del carrito junto con el nombre de usuario
            $cartItems = Cart::content();
            $username = Auth::user()->username;

            // Almacena el carrito junto con el nombre de usuario
            Cart::store($username);

            // Vacía el carrito después de almacenarlo
            Cart::destroy();

            // ... otras lógicas que puedas necesitar ...

            session()->flash('success_message', 'Order placed successfully');
            return redirect()->route('cart');
        } else {
            // User not authenticated, redirect the user to the login page
            session()->flash('error_message', 'Please log in to complete the purchase');
            return redirect()->route('cart');
        }
    }
 
}