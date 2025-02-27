<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\CartController;
use App\Http\Controllers\LanguageController;
use App\Http\Controllers\Admin\AdminAuthController;
use App\Http\Controllers\Admin\ProductosController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', [AuthController::class, 'index'])->name('index');

Route::get('/login-signup', [AuthController::class, 'showLoginSignupForm'])->name('login-signup');

Route::post('/login', [AuthController::class, 'login'])->name('login');

Route::post('/register', [AuthController::class, 'registerUser'])->name('register');

Route::post('/logout', [AuthController::class, 'logout'])->name('logout');

Route::get('/home', [AuthController::class, 'logados'])->name('logados');

Route::get('/shop', [AuthController::class, 'shop'])->name('shop');

Route::get('/shop/{category?}', [ProductosController::class, 'shopCategory'])->name('shop-category');

Route::get('/cart', [CartController::class, 'cart'])->name('cart');

Route::post('/cart_product', [ProductosController::class, 'buy'])->name('cart_product');

Route::post('/cart_product_remove/{rowId}', [CartController::class, 'removeProductCart'])->name('cart.remove');

Route::post('/cart', [CartController::class, 'checkout'])->name('checkout');

Route::get('product/details/{id}', [ProductosController::class, 'showDetails'])->name('productos/detalles'); 

Route::get('/set_language/{language}', [LanguageController::class, 'set_language'])->name( 'set_language');

Route::group(['prefix' => 'admin', 'namespace' => 'Admin'], function () {
    
    Route::get('/login', [AdminAuthController::class, 'getLogin'])->name('adminLogin');
    Route::post('/login', [AdminAuthController::class, 'postLogin'])->name('adminLoginPost');
    Route::post('/logout', [AdminAuthController::class, 'adminLogout'])->name('adminLogout');
 
    Route::group(['middleware' => 'adminauth'], function () {

        Route::get('/productos', [ProductosController::class, 'index'])->name('admin/productos');

        Route::get('productos/crear', [ProductosController::class, 'crear'])->name('admin/productos/crear');
        Route::put('productos/store', [ProductosController::class, 'store'])->name('admin/productos/store');

        /* Leer */ 
        Route::get('productos/show/{id}', [ProductosController::class, 'show'])->name('admin/productos/detalles'); 

        /* Actualizar */
        Route::get('productos/actualizar/{id}', [ProductosController::class, 'actualizar'])->name('admin/productos/actualizar');
        Route::put('productos/update/{id}', [ProductosController::class, 'update'])->name('admin/productos/update');

        /* Eliminar */
        Route::put('productos/eliminar/{id}', [ProductosController::class, 'eliminar'])->name('admin/productos/eliminar');

    });
});