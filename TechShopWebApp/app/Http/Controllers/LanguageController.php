<?php

namespace App\Http\Controllers;

use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;

class LanguageController extends Controller
{
    /**
     * Establece el idioma seleccionado en la sesión y redirige de nuevo.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\RedirectResponse
     */
    public function set_language(Request $request): RedirectResponse
    {
        // Llama a la función setLanguageSession con el idioma proporcionado en la ruta
        setLanguageSession($request->route('language'));

        // Redirige de nuevo a la página anterior
        return back();
    }
}
